<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="ext.ket.dqm.entity.KETDqmHeader"%>
<%@page import="ext.ket.dqm.entity.KETDqmRaise"%>
<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page
    import="wt.fc.*,
          wt.org.*,
          wt.vc.*,
          wt.vc.wip.*,
          java.util.*,
          e3ps.ecm.entity.*,
          ext.ket.project.trycondition.entity.*,
          java.text.*"%>
<%@page
    import="e3ps.groupware.company.*,
          e3ps.project.*,
          e3ps.project.beans.*,
          ext.ket.project.task.entity.*,
          ext.ket.project.task.service.*,
          e3ps.common.util.*"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.enterprise.RevisionControlled"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="wt.project.Role"%>
<%@page import="e3ps.common.obj.ObjectData"%>
<%@page import="e3ps.ecm.beans.EcmSearchHelper"%><%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.dms.entity.KETStandardTemplate"%>
<%@page import="ext.ket.shared.content.entity.*"%>
<%@page import="ext.ket.project.atft.service.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%--로그 설정 임포트 끝--%>

<%
    String trustProgressRate = "";
    String assessProgressRate = "";
    String mainScheduleName = "";
    String pjtLatestOid = "";
    String taskTypeCateGory = "";
    try {
		// 포함한 페이지에 정의됨
		//   String oid =  request.getParameter("oid");
		//   String oldOid =  request.getParameter("compareTaskOid");

		task = (E3PSTask) CommonUtil.getObject(oid);
		if(StringUtils.isNotEmpty(task.getMainScheduleCode()) ){
		    NumberCode num = NumberCodeHelper.manager.getNumberCode("MAINSCHEDULECODE", task.getMainScheduleCode());
		    if(num != null){
			  mainScheduleName = num.getName();
		    }
		}
		if(StringUtils.isNotEmpty(task.getTaskTypeCategory()) ){
            NumberCode num = NumberCodeHelper.manager.getNumberCode("TASKTYPE", task.getTaskTypeCategory());
            if(num != null){
        	   taskTypeCateGory = num.getName();
            }
        }
		
		E3PSProject project = (E3PSProject) task.getProject();
		pjtLatestOid = CommonUtil.getOIDString(E3PSProjectHelper.service.getProjectByProjectNo(project.getPjtNo()));
		//   //out.println(project.getPjtType());
		String projectOid = CommonUtil.getOIDString(project);
		WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		String taskTypeName = task.getTaskType();
		String taskCategory = task.getTaskTypeCategory();
		if (taskTypeName == null || "".equals(taskTypeName))
		    taskTypeName = "일반";

		// [START] [PLM System 1차개선] [수정] 선후행 관계 체크하여 산출물 진행률 입력 가능 여부 판단(FS는 입력 불가, SS는 입력 가능), 2013-08-13, BoLee
		boolean dependCheck = false;

		QueryResult dependListResult = TaskDependencyHelper.manager.getDependTaskList(task);
		TaskDependencyLink dependLink = null;
		E3PSTask ancestorTask = null;
		E3PSTaskData dependTaskData = null;
		ExtendScheduleData taskSchedule = null;

		while (dependListResult.hasMoreElements()) {

		    dependLink = (TaskDependencyLink) dependListResult.nextElement();
		    ancestorTask = (E3PSTask) dependLink.getDependTarget();

		    dependTaskData = new E3PSTaskData(ancestorTask);

		    taskSchedule = (ExtendScheduleData) task.getTaskSchedule().getObject();

		    // 선행 Task가 진행중(0)이고 선후행 관계가 FS일 경우 산출물 진행률 입력 불가
		    if (dependTaskData.taskState == 0
			    && (DateUtil.getTimeFormat(taskSchedule.getPlanStartDate(), "yyyy-MM-dd")).compareTo(DateUtil.getTimeFormat(dependTaskData.taskPlanEndDate, "yyyy-MM-dd")) > 0) {

			dependCheck = true;
		    }
		}
		// [END] [PLM System 1차개선] [수정] 선후행 관계 체크하여 산출물 진행률 입력 가능 여부 판단(FS는 입력 불가, SS는 입력 가능), 2013-08-13, BoLee

		boolean isDate = false;//실제시작일 여부
		ExtendScheduleData testSchedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
		String ExeDate = request.getParameter("ExeDate");
		if (testSchedule.getExecStartDate() != null || ExeDate != null) {
		    isDate = true;
		}
		//태스크 링크 정보 수정
		String cmd = request.getParameter("cmd");
		if ("linkModify".equals(cmd)) {
		    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
		    if (dependList != null) {
			Vector v = new Vector();
			while (dependList.hasMoreElements()) {
			    TaskDependencyLink link = (TaskDependencyLink) dependList.nextElement();
			    String linkOid = CommonUtil.getOIDString(link);
			    long loid = CommonUtil.getOIDLongValue(linkOid);
			    String delayTime = request.getParameter(String.valueOf(loid));
			    link.setDelayDuration(Integer.parseInt(delayTime));
			    v.add(link);
			}
			ProjectTaskHelper.manager.updateTaskFromDependencyLink(task, v);
		    }
		}
		try {
		    String addTask = request.getParameter("addTask");
		    if (addTask != null && addTask.length() > 0) {
			TemplateTask dependTask = (TemplateTask) CommonUtil.getObject(addTask);
			if (dependTask != null)
			    TaskDependencyHelper.manager.setDependTask(task, dependTask);
		    }

		    String deleteTask = request.getParameter("deleteTask");
		    if (deleteTask != null && deleteTask.length() > 0) {
			TaskDependencyLink link = (TaskDependencyLink) CommonUtil.getObject(deleteTask);
			if (link != null)
			    TaskDependencyHelper.manager.deleteDependTask(task, link);
		    }
		} catch (Exception ex) {
		    Kogger.error(ex);
		}

		// 진행현황 % 입력
		String completion = request.getParameter("setCompletion");
		//   TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);
		if (completion != null) {
		    try {
			int com = StringUtil.parseInt(completion, 0);
			if (com != 100) {
			    task.setTaskCompletion(com);
			}
			if (buttonAuth.isCompletionButton || CommonUtil.isAdmin()) {
			    try {
				task = (E3PSTask) ProjectTaskHelper.updateCompletion(task);
			    } catch (Exception ex) {
				Kogger.error(ex);
			    }
			    task = (E3PSTask) PersistenceHelper.manager.refresh(task);

			    buttonAuth = new TaskViewButtonAuth(task);

			}

		    } catch (Exception e) {
			Kogger.error(e);
		    }
		}

		// Admin 권한 실제 시작일 / 실제 종료일 설정

		String toDay = DateUtil.getToDay("yyyy-MM-dd");
		if ("ExeDate".equals(ExeDate)) {
		    String taskExecStartDate = request.getParameter("taskExecStartDate");
		    String taskExecEndDate = request.getParameter("taskExecEndDate");
		    Calendar tempCal = Calendar.getInstance();
		    ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
		    boolean isException = false;
		    String message = "";
		    if (taskExecStartDate != null && taskExecStartDate.length() > 0) {
			if (schedule.getPlanStartDate() != null) {
			    isException = true;
			    message = messageService.getString("e3ps.message.ket_message", "03366")/*이미 실제 시작일이 입력 되어 있습니다.*/;
			} else if (toDay.compareTo(taskExecStartDate) < 0) {
			    isException = true;
			    message = messageService.getString("e3ps.message.ket_message", "03367")/*미래 일자는 시작일이 될 수 없습니다.*/;
			    return;
			}
		    }
		    // 실제 시작일
		    if (taskExecStartDate != null && taskExecStartDate.length() > 0) {
			tempCal.setTime(DateUtil.parseDateStr((String) taskExecStartDate));
			schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		    } else {
			schedule.setExecStartDate(null);
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		    }
		    // 실제 종료일
		    if (taskExecEndDate != null && taskExecEndDate.length() > 0) {
			tempCal.setTime(DateUtil.parseDateStr((String) taskExecEndDate));
			schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		    } else {
			schedule.setExecEndDate(null);
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		    }
		    try {
			task = (E3PSTask) ProjectTaskHelper.updateCompletion(task);
		    } catch (Exception ex) {
			Kogger.error(ex);
		    }
		    task = (E3PSTask) PersistenceHelper.manager.refresh(task);
		    buttonAuth = new TaskViewButtonAuth(task);
		}

		// 종료 버튼 클릭시 종료일 설정
		String TaskAuto = request.getParameter("TaskAuto");
		if ("Auto".equals(TaskAuto)) {

		    String TaskAutoStartDate = request.getParameter("TaskAutoStartDate");
		    String TaskAutoEndDate = request.getParameter("TaskAutoEndDate");
		    String AutoType = request.getParameter("AutoType");
		    Calendar tempCal = Calendar.getInstance();

		    ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();

		    if (AutoType.equals("start")) {
			if (TaskAutoStartDate != null && TaskAutoStartDate.length() > 0) {
			    tempCal.setTime(DateUtil.parseDateStr((String) TaskAutoStartDate));
			    schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
			}
		    } else {
			if (TaskAutoEndDate != null && TaskAutoEndDate.length() > 0) {
			    tempCal.setTime(DateUtil.parseDateStr((String) TaskAutoEndDate));
			    schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
			}
		    }
		    try {
			task = (E3PSTask) ProjectTaskHelper.updateCompletion(task);
		    } catch (Exception ex) {
			Kogger.error(ex);
		    }
		    task = (E3PSTask) PersistenceHelper.manager.refresh(task);
		    buttonAuth = new TaskViewButtonAuth(task);
		}

		// Tean 가져오기
		String teamName = "";
		String pjtType = "";
		if (project != null) {
		    
		    if(project instanceof WorkProject){
			    teamName = ProjectHelper.getProjectTeam(5);
		    }else{
			    teamName = ProjectHelper.getProjectTeam(project.getPjtType());
		    }
		    
		    pjtType = "" + project.getPjtType();
		}
		TeamTemplate tempTeam = null;
		//   tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);
		if (project instanceof ReviewProject) {
		    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
		} else if (project instanceof ProductProject) {
		    if ("자동차 사업부".equals(((ProductProject) project).getTeamType())) {

			tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

		    } else if ("KETS".equals(((ProductProject) project).getTeamType()) || "KETS_PMO".equals(((ProductProject) project).getTeamType())) {

			tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

		    } else {

			tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");

		    }
		} else if (project instanceof MoldProject) {
		    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
		} else if (project instanceof WorkProject) {
		    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
		
	    }

		Vector vecTeamStd = new Vector();
		if (tempTeam != null) {
		    vecTeamStd = tempTeam.getRoles();
		}

		// 실제 시작일 / 실제 종료일 가져오기
		E3PSTaskData taskData = new E3PSTaskData(task);
		E3PSTaskAccessData accessData = taskData.getAccessState();
		String taskExecStartDateStr = "";
		String taskExecEndDateStr = "";
		if (taskData.taskExecStartDate != null) {
		    taskExecStartDateStr = DateUtil.getDateString(taskData.taskExecStartDate, "D");
		}
		if (taskData.taskExecEndDate != null) {
		    taskExecEndDateStr = DateUtil.getDateString(taskData.taskExecEndDate, "D");
		}

		/*
		  ####################  권한 설정  ####################
		 */

		boolean isEdit = false;
		boolean isElectron = false;
		boolean isPmo = CommonUtil.isMember("자동차PMO");
		boolean isPmo2 = CommonUtil.isMember("전자PMO");
		boolean isPmo3 = CommonUtil.isMember("KETS_PMO");
        
		//   boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
		//   boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin
		boolean isPM = ProjectUserHelper.manager.isPM(project); //isPM
		boolean isPL = ProjectUserHelper.manager.isPL(project); //isPL
		boolean isProjectContinue = (project.getPjtState() == ProjectStateFlag.PROJECT_STATE_PROGRESS); //Project 상태
		boolean canManage = isPM; //Manager
		boolean isContinue = accessData.isContinue(); //true : 진행 , false : 태스크 진행 안됨
		boolean isChild = ProjectTaskHelper.isChild(task);
		boolean isSapTask = false;
		boolean isPurchesInfo = false;
		E3PSProjectData projectData = new E3PSProjectData(project);
		if (projectData.teamType.equals("전자 사업부") || projectData.teamType.equals("전자사업부")) {
		    isElectron = true;
		}
		//Task 책임자  설정
		if (buttonAuth != null) {
		    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
		    while (masterList.hasMoreElements()) {
			Object o[] = (Object[]) masterList.nextElement();
			TaskMemberLink link = (TaskMemberLink) o[0];
			PeopleData data = new PeopleData(link.getMember().getMember());
    			if (wtuser.getName().equals(data.id)) {
    			    isEdit = true;
    			}
		    }
		}
		if (buttonAuth.isLatest && (CommonUtil.isAdmin() || buttonAuth.isPM || CommonUtil.isBizAdmin())) {
		    isEdit = true;
		}
		
		String isTaskCompleteDrYn = "OK";
		
		if("6".equals(task.getTaskTypeCategory()) || "MC042".equals(task.getMainScheduleCode())){
		    if(!ProjectTaskHelper.manager.isDR6taskCompleteCheck(task)){
			    isTaskCompleteDrYn = "NO";
		    }
		}
		
		String isTaskCompleteTryYn = "OK";
		String TryParentTaskName = "";
		
		//상황실때문에 수정됨.. ATFT 탭이 완료되지 않으면 해당 TASK(All-Tool 공정점검(조립) or Full-Tool 공정점검(조립)) 는 완료못하도록 2017.01.05 by 황정태
		String isTaskCompleteYn = "OK";
		String isATFTMsg = "";
		List<Map<String, Object>> returnObjList = null;
		
		String atftTaskName = "";
		
		E3PSTask parentTask = (E3PSTask)task.getParent();
		
		if(parentTask != null){
		    atftTaskName = parentTask.getTaskName();
		    TryParentTaskName = atftTaskName;
		}
		
		if( "양산검증단계".equals(atftTaskName) && "금형Try_[양산검증_01]".equals(task.getTaskName()) ){
		    isTaskCompleteTryYn = "NO";
		}
		
		if((StringUtils.containsIgnoreCase(atftTaskName, "ALL") || StringUtils.containsIgnoreCase(atftTaskName, "FULL")) && StringUtils.containsIgnoreCase(atftTaskName, "TOOL") && StringUtils.containsIgnoreCase(atftTaskName, "점검")){
		    
		    
		    QueryResult taskQr = null;
		    
		    if(parentTask != null){
			    taskQr = ProjectTaskHelper.manager.getChild(parentTask);
		    }
		    
	        
		    boolean checkFlag = true;
	        int chckCnt = 0;
	        
		    while (taskQr != null && taskQr.hasMoreElements()) {
	            Object[] obj = (Object[]) taskQr.nextElement();
	            E3PSTask checkTask = (E3PSTask) obj[0];
	            String checkTaskName = checkTask.getTaskName();
	            
 	            if(!((StringUtils.containsIgnoreCase(checkTaskName, "ALL") || StringUtils.containsIgnoreCase(checkTaskName, "FULL")) && StringUtils.containsIgnoreCase(checkTaskName, "TOOL") && StringUtils.containsIgnoreCase(checkTaskName, "점검")) ){
	        	   continue;
	            }
 	           chckCnt++;
	            
	            if(CommonUtil.getOIDString(task).equals(CommonUtil.getOIDString(checkTask))){
	        	    continue;
	            }
	            if(checkTask.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE){//ATFT 관련 TASK가 지금 완료처리하려고 하는 TASK하나만 남았을때를 체크하기 위함
	        	    checkFlag = false;
	        	    break;
	            }
	        }
		    
		    checkFlag = chckCnt > 0 ;//점검대상 Task가 하나도 없다면 체크하지 않도록 하기 위함
		    
		    if(checkFlag){
			
		    
			        returnObjList = AtftHelper.service.lastestAtftList(project.getPjtNo());
		            String p1_state = "";
		            String p2_state = "";
		            
		            String p1_desision = "";
		            String p2_desision = "";
		                
		            String atftLevel = "";
		            
		            for(Map<String, Object> atft : returnObjList){
		            
		                p1_desision = (String)atft.get("P1_DESISION");
		                p2_desision = (String)atft.get("P2_DESISION");
		                
		                p1_state = (String)atft.get("P1_STATE");
		                p2_state = (String)atft.get("P2_STATE");
		                atftLevel = (String)atft.get("ATFT1LEVEL");
		                
		                if(StringUtils.containsIgnoreCase(atftTaskName, "ALL") && !p1_state.equals("APPROVED")){
		                    isTaskCompleteYn = "NO";
		                    break;
		                }
		                
		                if(StringUtils.containsIgnoreCase(atftTaskName, "FULL") && !p2_state.equals("APPROVED")){
		                    isTaskCompleteYn = "NO";
		                    break;    
		                }
		                
		                if(StringUtils.containsIgnoreCase(atftTaskName, "ALL") && "NG".equals(p1_desision) ){
		                    isTaskCompleteYn = "NO";
		                    break;
		                }
		                if(StringUtils.containsIgnoreCase(atftTaskName, "FULL") && ("NG".equals(p2_desision) || ("ATFT4".equals(atftLevel) && "".equals(p2_desision) )) ){
		                    isTaskCompleteYn = "NO";
		                    break;
		                }
		                
		            }
		            
		            if(returnObjList == null || returnObjList.size() == 0){
		                isTaskCompleteYn = "NO";
		            }
		            
		            if("NO".equals(isTaskCompleteYn) && StringUtils.containsIgnoreCase(atftTaskName, "ALL")){
		                isATFTMsg = "ATFT P1/PPV에 점검에 NG건이 존재하거나 점검 미수행 중입니다.";
		            }
		            
		            if("NO".equals(isTaskCompleteYn) && StringUtils.containsIgnoreCase(atftTaskName, "FULL")){
		                isATFTMsg = "ATFT P2/MVBs에 점검에 NG건이 존재하거나 점검 미수행 중입니다.";
		            }
		    }
		    
		}
		
		//사용자가 Task의 책임자인지 확인
    boolean isTaskMaster = false;
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    //Kogger.debug(">>>>>> e333333333 : " + masterList.size());
    boolean isRole = (task.getPersonRole() != null && task.getPersonRole().length() > 0);
    if (masterList != null) {
    
        while (masterList.hasMoreElements()) {
        Object o[] = (Object[]) masterList.nextElement();
        TaskMemberLink link = (TaskMemberLink) o[0];
        PeopleData data = new PeopleData(link.getMember().getMember());
    
        //          if(!isRole && buttonAuth != null && (buttonAuth.isTaskMasterModify || isAdmin)){
    
        if (wtuser.equals(data.wtuser)) {
            isTaskMaster = true;
            break;
        }
        //          }
        }
    }
    
    //사용자가 Task의 참여자인지 확인
    boolean isTaskMember = false;
    QueryResult memberlist = TaskUserHelper.manager.getMember(task);
    if (memberlist != null) {
        while (memberlist.hasMoreElements()) {
        Object o[] = (Object[]) memberlist.nextElement();
        TaskMemberLink link = (TaskMemberLink) o[0];
        PeopleData data = new PeopleData(link.getMember().getMember());
        //         if(buttonAuth != null && buttonAuth.isTaskMemberModify || isAdmin){
            if (wtuser.equals(data.wtuser)) {
                isTaskMember = true;
                break;
            }
        //         }
        }
    }
%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<%@include file="/jsp/common/top_include.jsp"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.ellipsis {
	border: 0;
	padding: 0 0 0 3px;
	margin: 0;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}
-->
</style>
<script type="text/javascript">
var isTaskCompleteYn = '<%=isTaskCompleteYn%>';
var isATFTMsg = '<%=isATFTMsg%>';
var isTaskCompleteDrYn = '<%=isTaskCompleteDrYn%>';
var isTaskCompleteTryYn = '<%=isTaskCompleteTryYn%>';
</script>
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<%@include file="/jsp/common/multicombo.jsp"%>

<script language="JavaScript"><!--

  var ajax = new sack();

  function purchase(projectNo){
    var url="/plm/jsp/project/SapPurchaseInterfaceView.jsp?projectNo="+projectNo;
    openOtherName(url,"purchase","1000","600","status=no,scrollbars=yes,resizable=yes");
  }


  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","490","status=no,scrollbars=no,resizable=yes");
  }

  function openCompleteReson(oid){
    var url="/plm/jsp/project/EtcOutputReasonView.jsp?oid="+oid;
    openOtherName(url,"Reson","700","600","status=no,scrollbars=no,resizable=yes");
  }
  
  function openECO(oid, type){
    var url="";
    if(type=='PROD') {
        url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid="+oid;
    }else {
        url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid="+oid;
    }
    openOtherName(url,"eco","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }
  function openDQM(oid, type){
    var url="/plm/ext/dqm/dqmMainForm.do?type=view&oid="+oid;
    openOtherName(url,"dqm","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }

  function openTry(oid, type){
      
    var url="";
    if(type=='MOLD') {
        url = "/plm/ext/project/trycondition/viewTryMoldForm.do?oid=" + oid;
    }else {
        url = "/plm/ext/project/trycondition/viewTryPressForm.do?oid=" + oid;
    }
    openOtherName(url,"TRY","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }

  function openTRY(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+'&popup=popup', '',1150,800);
        //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=ext.ket.project.gate.entity.AssessSheet:100000231419&popup=popup', '',1150,800);
        //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=ext.ket.project.gate.entity.GateAssessResult:100000361325&popup=popup', '',1150,800);
    }
  
  function taskComplete(v){
    if ( v == '<%=ProjectStateFlag.TASK_STATE_COMPLETE%>' ) {
      if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03337") %><%--종료된 Task는 재진행 할수 없습니다.\n종료하시겠습니까?--%>") ) {
        var url = "/plm/jsp/project/TaskStateCheck.jsp?state="+v+"&oid=<%=oid%>";
        newWin = window.open(url,"compWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
        newWin.resizeTo(800,600);
        newWin.focus();
      }
    }
  }
  function setTargetValue(){
    if( checkField(document.forms[0].targetValue, "Current Status") ){
        return;
    }
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?setTargetValue=save";
    document.forms[0].method = "post";
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
  }

  // Task 진척률 입력
  function setCompletion(){
      
    if(checkStartEndDate()){

      alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
      document.forms[0].taskExecStartDate.focus();
      return;
    }

    var v = document.forms[0].completion.value;

    if(parseInt(v) == 100){

        // [START] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee
        <%
            if ( !isAdmin && dependCheck ) {
        %>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
        return;
        <%
            }
        %>
        // [END] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee

      
      inputEtc();
    }else if(!isNotNumData(v) && parseInt(v)<100){

      disabledAllBtn();
      showProcessing();
//       document.forms[0].action = "/plm/jsp/project/TaskView.jsp?setCompletion="+v;
//       document.forms[0].method = "post";
//       document.forms[0].submit();
      goReloadPage();
      
    }else {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00008") %><%--{0} 입력값이 잘못되었습니다\n입력값은 0~100 사이어야 합니다--%>");
      document.forms[0].completion.focus();
      return;
    }



  }
  function reasonPopup(){
      var url = "/plm/jsp/project/PopupReason.jsp?oid=<%=oid%>&cmd=update";
        //etcData = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:200px; center:yes");
        openOtherName(url, "", 500, 300, "status=yes,scrollbars=no,resizable=yes");
  }
  
  function updataTask(){
<%
    if(task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE) {
%>
    alert("태스크 완료 상태일때는 수정할 수 없습니다");
    return;
<%
    }
%>
      var url="/plm/jsp/project/TaskUpdate.jsp?oid=<%=oid%>";
    openSameName(url,"500","520","status=no,scrollbars=no,resizable=yes");
  }

  function editTask(oid){
    var url = "/plm/jsp/project/manage/ManageProjectTaskFrm.jsp?oid="+oid;
    openOtherName(url,"manageTask","900","700","status=1,scrollbars=no,resizable=1");
  }

  //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
  //산출물 정의  관련 시작 .............................................................................
  function outputPage(opid, isview) {

	var taskType = "<%=taskTypeName%>";
	if("COST" == taskType && isview != 'true'){
		if(!confirm("해당 Task는 별도의 산출물 없이 [원가 요청서] 버튼을 클릭하여\r\nTask를 종료할 수 있습니다.\r\n그럼에도 산출물을 등록하시겠습니까?")){
			return;
		}
	}
    var modalHeight = '450';
    var modalWidth = '620';

    var url = "/plm/jsp/project/ProjectOutputCreatePage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;

    if(isview == 'true') {
      url = "/plm/jsp/project/ProjectOutputViewPage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;
      modalWidth = '680';
      modalHeight = '570';
    }
    //openOtherName(url,"manageTask","600","360","status=1,scrollbars=no,resizable=1");

//      attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth:"+modalWidth+"; dialogHeight:" +modalHeight+ "; center:yes");
//    attache = window.open(url,"output","help=no; resizable=yes; status=no; scroll=no; dialogWidth:300; dialogHeight:250; center:yes");
    openOtherName(url,"manageTask",modalWidth,modalHeight,"status=1,scrollbars=no,resizable=1");
    if(typeof attache == "undefined" || attache == null) {
      attache = false;
      return;
    }

//     if(isview == 'true') {
//       return;
//     }


    //onProgressBar();

//     document.forms[0].exedate.value = "exedate";
<%--     document.forms[0].action = "/plm/jsp/project/taskview.jsp?oid=<%=oid%>"; --%>
//     document.forms[0].submit();

    //goReloadPage();
    
//     var param = "command=searchOutput";
<%--     param += "&oid=<%=oid%>"; --%>
//     var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
//     postCallServer(url, param, setOutputTable, true);
  }


  function setOutputTable(req) {
    var xmlDoc = req.responseXML;
    
    var msg = xmlDoc.getElementsByTagName("l_message")[0];
    
    var errorMessage = xmlDoc.getElementsByTagName("l_errorMessage")[0];

<%--     document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
    goReloadPage();

    //if(msg == 'false') {
    //  alert(errorMessage);
    //  if(errorMessage != ""){
    //    alert(errorMessage);
    //  }else{
    //    alert("다시 시도하시기 바랍니다.");
    //  }
    //  return;
    //}

    //onProgressBar();

    //var param = "command=searchTaskCompletions";
    //param += "&oid=<%=oid%>";
    //var url = "/plm/jsp/project/ProjectTaskAjaxAction.jsp";
    //postCallServer(url, param, setTaskProgress, true);

  }

  function registryOutput(v){
    var url = "/plm/jsp/project/ProjectOutputRegistry.jsp?oid="+v;
    openOtherName(url,"window","850","750","status=no,scrollbars=no,resizable=yes");
    //newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=820,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(800,600);
    //newWin.focus();
  }


  function inputTryCreate(opid) {
     if(checkStartEndDate() && <%=!buttonAuth.isChild %>){
         alert('<%=messageService.getString("e3ps.message.ket_message", "07284") %><%--실제 시작일을 먼저 입력 하셔야 합니다--%>');
         document.forms[0].taskExecStartDate.focus();
         return;
     }
     TryCondition.goCreate(opid);
  }
  
  
  function inputOutputResult(opid, otype, pjtDwg){
      var rstWidth = "880";
      var rstHeight = "550";

    if(checkStartEndDate() && <%=!buttonAuth.isChild %>){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02057") %><%--실제 시작일을 입력 하셔야 \n 산출물을 등록 할 수 있습니다--%>');
      return;
    }
    if(otype=="DOC"){
        rstHeight = "850";
    <%
      if(!project.getPjtTypeName().equals("금형"))
      {
    %>
      var url = "/plm/jsp/dms/CreateDocument.jsp?taskOid=<%=oid%>&projectOid=" + opid;
    <%
      }
      else
      {
    %>
    
      var url = "/plm/jsp/dms/CreateDocument.jsp?taskOid=<%=oid%>&projectOid=" + opid;
    <%
      }
    %>
    }else if(otype=="DWG"){
        rstHeight = "750";
        rstWidth = "1024";
        var isCustomer = "N";
        var url = "";
        if("<%=projectOid%>".indexOf('ProductProject') > 0){
            url ="/plm/jsp/edm/CreateEPMDocument.jsp?isCustomer=Y&outputOid=" + opid;
            if(pjtDwg == "true"){
                url ="/plm/jsp/edm/CreateEPMDocument.jsp?isCustomer=Y&outputOid=" + opid + "&pjtDwg=2";
            }else if(pjtDwg == "false"){
                url ="/plm/jsp/edm/CreateEPMDocument.jsp?isCustomer=Y&outputOid=" + opid + "&pjtDwg=1";
            }      
        }else{
            url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid;
            if(pjtDwg == "true"){
                url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid + "&pjtDwg=2";
            }else if(pjtDwg == "false"){
                url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid + "&pjtDwg=1";
            }      
        }
    } 
    // ECO 직접작성
    else if(otype=="ECO"){
        <%
          // 제품 ECO 직접작성일경우
          if("제품도출도".equals(task.getTaskName()) || !project.getPjtTypeName().equals("금형"))
          {
        %>
          var url = "/plm/jsp/ecm/CreateProdEcoForm.jsp?projectOid=<%=projectOid %>&projectOutputOid="+ opid;
        <%
          }
          // 금형 ECO 직접작성일경우
          else
          {
        %>
          var url = "/plm/jsp/ecm/CreateMoldEcoForm.jsp?projectOid=<%=projectOid %>&projectOutputOid="+ opid;
        <%
          }
        %>
        
        var windowWin = window.open(url,otype,"width=1024,height=768,status=no,scrollbars=no,resizable=no");
        windowWin.focus();
        
        return;
    }
    // 품질문제 직접작성
    else if(otype=="QLP"){
        
        var url = "/plm/ext/dqm/dqmMainForm.do?type=create&pjtoid=<%=projectOid%>&outputOid=" + opid;
          
        var windowWin = window.open(url,otype,"width=1024,height=768,status=no,scrollbars=no,resizable=no");
        windowWin.focus();
        
        return;
    }
    else if(otype=="ETC"){
      var url = "/plm/jsp/project/EtcOutputReason.jsp?oid=" + opid +"&completed=completed&targetType=task";
      openOtherName(url,"window","700","600","status=no,scrollbars=no,resizable=yes");
      return;
    }

    //OLD - Start
    //attache = window.showModalDialog(url, window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=820px; dialogHeight:800px; center:yes");
    //if(typeof attache == "undefined" || attache == null) {
    //  attache = false;
    //}
    //OLD - End

    //NEW - Start
    getOpenWindow2(url,"",rstWidth,rstHeight);
    //NEW - End

    //onProgressBar();

    //var param = "command=searchOutput";
<%--     //param += "&oid=<%=oid%>"; --%>
    //var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    //postCallServer(url, param, setOutputTable, true);
<%--     //document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
    //document.forms[0].submit();
  }

  function etcComplete(opid){

    var url = "/plm/jsp/project/EtcOutputReason.jsp?oid=" + opid + "&completed=completed";
    openOtherName(url,"window","500","300","status=no,scrollbars=no,resizable=yes");
    return;
  }

  function registryLinkOutput(oid){
    var url = "/plm/jsp/project/ProjectOutputDocRegistryLinkForm.jsp?oid="+oid;
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=yes,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(700, 500);
    newWin.focus();
  }
  var publicOpId = '';
  
  function selectModalDialog(attache){
	  
	  if(typeof attache == "undefined" || attache == null) {
		  attache = false;
          return;
      }
      var opid = publicOpId;
      var trArr = attache[0];
      var ecoOid = trArr[0];
      var ecoNumber = trArr[1];

      $.ajax( {
          url : "/plm/ext/project/task/linkProjectOutputEcoProduct.do?outputOid="+opid+"&ecoOid="+ecoOid,   //String ecoOid, String outputOid 파라미터 필요함(ProjectTaskCompController)
          type : "POST",
          data : "", //$('[name=TaskViewForm]').serialize(),
          async : false,
          success: function(data) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
              goReloadPage();
          },
          fail : function(){
              alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
              hideProcessing();
          }
      });
  }
  
  
  function epmFnCallBack(returnValue){
	
	  if ( typeof returnValue == "undefined" || returnValue == null ) {
          return;
      }
      
      var oidRtn = '';
      if(returnValue!=null) {
          var arrRtn = returnValue[0];
          oidRtn = arrRtn[0];
          //alert('oidRtn:'+oidRtn);
      }

      $.ajax( {
          url : "/plm/ext/project/task/linkProjectOutputDwgProduct.do?outputOid="+publicOpId+"&epmOid="+oidRtn,   //String ecoOid, String outputOid 파라미터 필요함(ProjectTaskCompController)
          type : "POST",
          data : "", //$('[name=TaskViewForm]').serialize(),
          async : false,
          success: function(data) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
              goReloadPage();
              //opener.location.href='/plm/jsp/project/TaskGateView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
              //self.close();
          },
          fail : function(){
              alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
              hideProcessing();
          }
      });
  }
  
  function inputLinkOutput(opid,otype){

    if(checkStartEndDate() && <%=!buttonAuth.isChild %>){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02058") %><%--실제 시작일을 입력 하셔야 \n 산출물을 링크 등록 할 수 있습니다--%>');
      return;
    }

    publicOpId = opid;
    //alert(otype);
    if(otype=="DOC"){
        //var url = "/plm/jsp/project/ProjectOutputResultLink.jsp?oid=" + opid;
      //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=720px; dialogHeight:585px; center:yes");
      
      
        var url = "/plm/ext/wfm/workspace/linkProjectDocumentPopup.do?outputoid=" + opid + "&returnTargetUrl=/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=CommonUtil.getOIDString(task) %>";
        //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=1000px; dialogHeight:800px; center:yes");
        
        attache = window.open(url,"docLink","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=925,height=715");

        
      if(typeof attache == "undefined" || attache == null) {
        attache = false;
        return;
      }

    }else if(otype=="DWG"){
<%--       var url = "/plm/jsp/project/ProjectOutputDwgResultLink.jsp?oid=" + opid + "&returnTargetUrl=/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=CommonUtil.getOIDString(task) %>";  --%>
//       //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=750px; dialogHeight:620px; center:yes");
//       attache = window.open(url,"dwgLink","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=750,height=615");
//       if(typeof attache == "undefined" || attache == null) {
//         attache = false;
//         return;
//       }

      var url="/plm/servlet/e3ps/EDMServlet?command=openSearchPopup&mode=s&modal=&fncall=epmFnCallBack";
      window.open(url,"epmFnCallBack","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=1020,height=700");


    }else if(otype=="ECO"){
//      if(document.forms[0].dieNumber.value == ''){
        <%--       alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %>Die No를 입력하세요'); --%>
//             document.forms[0].dieNumber.focus();
//             return;
//           }

        //연계ECO 검색 팝업 호출
        var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
        //url += "&obj=prodMoldCls^0&obj=mode^single";
        
        <%
        // 제품 ECO일 경우
        if("제품도출도".equals(task.getTaskName()) || !project.getPjtTypeName().equals("금형"))
        {
        %>
            //프로젝트 상관없이 제품,금형 ECO 검색되어지게 수정
            /* url += "&obj=prodMoldCls^1"; */
            url += "&obj=prodMoldCls^3";
        <%   
        }
        // 금형 ECO일 경우
        else {
        %>
            //프로젝트 상관없이 제품,금형 ECO 검색되어지게 수정
            /* url += "&obj=prodMoldCls^2"; */
            url += "&obj=prodMoldCls^3";
        <%    
        }
        %>
        
        url += "&obj=mode^single";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550";
        window.open(url,'taskViewCommonEco',opts);
        
      

    }else if(otype=="QLP"){

    	
        var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle='1'&mode='actionSearch'&callBackFn=selectDqmOutputFunc&pjtno=<%=project.getPjtNo()%>";
        
        getOpenWindow2(url,'searchDqmPopup', 800, 600);

        
    }
    //var url = "/plm/jsp/project/ProjectOutputResultLink.jsp?oid=" + opid;
    //openOtherName(url,"window","690","605","status=no,scrollbars=yes,resizable=yes");

    //onProgressBar();

    var param = "command=searchOutput";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);

<%--     //document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>

  }

  
  
  <%--관련품질문제--%>
  function selectDqmOutputFunc(objArr){
      
      
      if(objArr.length == 0) {
          return;
      }

      var trArr;
      for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];

//         alert('output:'+publicOpId+', trArr.OID:'+trArr.OID);
        $.ajax( {
            url : "/plm/ext/project/task/linkProjectOutputQLP.do?outputOid="+publicOpId+"&dqmHeaderOid="+trArr.OID,   //String ecoOid, String outputOid 파라미터 필요함(ProjectTaskCompController)
            type : "POST",
            data : "", //$('[name=TaskViewForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
                goReloadPage();
                //opener.location.href='/plm/jsp/project/TaskGateView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
                //self.close();
            },
            fail : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
                hideProcessing();
            }
        });
      }    
  }

  
  
  function updateGateTarget(cb, outputOid) {
      
    //alert(outputOid);
    //alert('checked:'+cb.checked+',value:'+cb.value+',output:'+outputOid);
    var gCheckList = document.getElementsByName('gatechecks');
    
    var tgtStr = '';
    if(cb.checked) {
        tgtStr = '1';
    }else {
        tgtStr = '0';
    }


    $.ajax( {
        url : "/plm/ext/project/task/updateProjectOutputGateCheck.do?outputOid="+outputOid+"&gateStr="+tgtStr,   //String ecoOid, String outputOid 파라미터 필요함(ProjectTaskCompController)
        type : "POST",
        data : "", //$('[name=TaskViewForm]').serialize(),
        async : false,
        success: function(data) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
        },
        fail : function(){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
        }
    });
  }
  
  function deleteOutput(opid, isPrimary){
      
   <%--  if(checkStartEndDate()){
        alert('<%=messageService.getString("e3ps.message.ket_message", "07284") %>실제 시작일을 먼저 입력 하셔야 합니다');
        document.forms[0].taskExecStartDate.focus();
        return;
    } --%>

    <%if(!CommonUtil.isAdmin()){%>
    if(isPrimary=='필수') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07286")%><%--필수 산출물은 삭제할 수 없습니다--%>');
        return;
    }
    <%}%>
    
    if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "01918") %><%--산출물을 삭제 합니다.\n등록된 산출물의 경우 프로젝트에서는 삭제되지만,\n문서관리에서 남아 있습니다.\n삭제하시겠습니까?--%>") ) {
      return;
    }
  
    var param = "command=deleteOutput";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
    
    showProcessing(); //Progressbar 보이기
    setTimeout('goReloadPage()',2000);
    
<%--     document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
//     document.forms[0].submit();
    //goReloadPage();
  }

  function deleteDepart(opid){
    if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "03310") %><%--부서를 삭제 시겠습니까?--%>") ) {
      return;
    }

    var param = "command=refDeptDelete";
    param += "&oid=<%=oid%>";
    param += "&taskviewoid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setDepartTable, true);
  }

  function setDepartTable(){
    alert("depart delete");
  }

  // 산출물 진척률 입력
  function outputProgress(objkey, objType) {

    //var oprogress = document.forms[0].outputCompletion;
    var oprogress = document.getElementsByName("outputCompletion");
    var oprogresskey = document.getElementsByName("progresskey");

    if(checkStartEndDate()){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
      document.forms[0].taskExecStartDate.focus();
      return;
    }

    if(oprogress == null || oprogress == 'undefined') {
      return;
    }

    var oprogressvalue = 0;
    var len = oprogress.length;
//     alert('len:'+len +':'+oprogresskey.length+', objkey:'+objkey+', oprogress.progresskey:'+oprogress.progresskey +', oprogress.value:'+oprogress[0].value);
    if(len) {
      for(var i = 0; i < len; i++) {
        if(oprogresskey[i].value == objkey) {
          oprogressvalue = oprogress[i].value;
          break;
        }
      }
    }
    else {
      if(oprogresskey[0].value == objkey) {
        oprogressvalue = oprogress[0].value;

      }
    }
    if(oprogressvalue > 100) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02732") %><%--진행율을 100미만으로 입력하시기 바랍니다--%>');
      return;
    }
    //if(check){

    if( oprogressvalue == 100 ){

        // [START] [PLM System 1차개선] [수정] FS 선행 Task 미완료 시 후행 Task 완료(산출물 진척률 100% 입력) 불가, 2013-08-23, BoLee
        <%
            if ( dependCheck ) {
        %>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
        return;
        <%
            }
        %>
        // [END] [PLM System 1차개선] [수정] FS 선행 Task 미완료 시 후행 Task 완료(산출물 진척률 100% 입력) 불가, 2013-08-23, BoLee

      if(objType == "ETC"){
        etcComplete(objkey);
        return;
      }else{
        alert("<%=messageService.getString("e3ps.message.ket_message", "01720") %><%--산출물 등록 및 결재 승인됨 상태가 되면 자동 입력됩니다--%>");
        return;
      }

    }
    //}

    var param = "command=inputOutputProgress";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(objkey);
    param += "&outputCompletion=" + encodeURIComponent(oprogressvalue);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    //alert(document.forms[0].outputCompletion.value + ','+ oprogress.value+ ','+ oprogressvalue+'param:'+param);
    postCallServer(url, param, setOutputTable, true);
    
    showProcessing();
    //alert('2');
    //location.reload();
    goReloadPage();
    
  }


  function setTaskProgress(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

<%--     document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
    goReloadPage();

    /*
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_taskCompletion = showElements[0].getElementsByTagName("l_taskCompletion");
    var l_taskPlanStartDate = showElements[0].getElementsByTagName("l_taskPlanStartDate");
    var l_taskPlanEndDate = showElements[0].getElementsByTagName("l_taskPlanEndDate");



    ltaskCompletion = decodeURIComponent(l_taskCompletion[0].text);
    ltaskPlanStartDate = decodeURIComponent(l_taskPlanStartDate[0].text);
    ltaskPlanEndDate = decodeURIComponent(l_taskPlanEndDate[0].text);


    var startTD = document.getElementById("taskPlanStartDateTD");
    var endTD = document.getElementById("taskPlanEndDateTD");
    var completionBarTD = document.getElementById("taskCompletionBarTD");
    var completionTD = document.getElementById("taskCompletionTD");

    var bathtmlstr = "";
    bathtmlstr += "<table border=0 width=100% cellpadding=0 cellspacing=0>";
    bathtmlstr += "<tr>";
    bathtmlstr += "<td align=right width='"+ltaskCompletion+"%'><img src=\"/plm/portal/icon/bar_arrow1.gif\"></td>";
    bathtmlstr += "<td align=left width='(100-"+ltaskCompletion+")%'><img src=\"/plm/portal/icon/bar_arrow2.gif\"></td>";
    bathtmlstr += "</tr>";
    bathtmlstr += "<tr height=10>";

    bathtmlstr += "<td ";
    if(ltaskCompletion != 0) {
      bathtmlstr += "background=\"/plm/portal/icon/bar_full.gif\"";
    }
    bathtmlstr += "></td>";

    bathtmlstr += "<td ";
    if(ltaskCompletion != 100) {
      bathtmlstr += "background=\"/plm/portal/icon/bar_blank.gif\"";
    }

    bathtmlstr += "></td>";
    bathtmlstr += "</tr>";
    bathtmlstr += "</table>";

    startTD.innerText = "(" + ltaskPlanStartDate + ")";
    endTD.innerText = "(" + ltaskPlanEndDate + ")";
    completionTD.innerHTML = "<b>" + ltaskCompletion + "%</b>";
    completionBarTD.innerHTML = bathtmlstr;
    */
  }


//체크박스 관련
/*function allCheckNCancel()
{
  var checkBoxAll = document.getElementById("allCheck");
  var allCheckBox = document.forms[0].LID;
  if(checkBoxAll == null || checkBoxAll == undefined || allCheckBox == null || allCheckBox == undefined)
  return;

  for(var i = 0; i < allCheckBox.length; i++)
  {
  if(checkBoxAll.checked)
  allCheckBox[i].checked = true;
  else
  allCheckBox[i].checked = false;
  }
}

function checkNCancelAllCheckBox()
{
  var checkBoxAll = document.getElementById("allCheck");
  var allCheckBox = document.forms[0].LID;
  if(checkBoxAll == null || checkBoxAll == undefined || allCheckBox == null || allCheckBox == undefined)
  return;

  var bCheck = true;
  for(var i = 0; i < allCheckBox.length; i++)
  {
  if(!allCheckBox[i].checked)
  bCheck = false;
  }

  if(!checkBoxAll.checked && bCheck)
  checkBoxAll.checked = true;
  else
  checkBoxAll.checked = false;
}
*/

function allapprovalPage(cbox){
  var checkStates = document.forms[0].checkState;
  
  var checkval = false;
  var selectList = "";
  var totalSelectOids = "";

  if(cbox == null || cbox == "undefinded") {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
    return;
  }

  selectList = getSelectedList(cbox);
  if(selectList == "") {
    alert('<%=messageService.getString("e3ps.message.ket_message", "03136") %><%--하나 이상의 산출물을 체크해야 합니다--%>');
    return;
  }
  var leng = cbox.length;
  if(leng){
    for(var i=0; i<leng; i++) {
      if(cbox[i].checked == true) {
        if(cbox[i].value == '') {
          alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
          return;
        }else{
            if(checkStates[i].value=='INWORK' || checkStates[i].value=='REWORK') {
                
                totalSelectOids = totalSelectOids + 'pboOids=' + cbox[i].value + '&';
            }else {
                alert("<%=messageService.getString("e3ps.message.ket_message", "07132") %><%--산출물의 상태가 작업중이거나 재작업중인 경우만 결재가 가능합니다.--%>");
                return;
            }
          checkval = true;
        }
      }
    }
  }else{
    if(cbox.checked == true) {
      if(cbox.value == '') {
          
        alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
        return;
      }else{
          if(checkStates.value=='INWORK' || checkStates.value=='REWORK') {
              totalSelectOids = totalSelectOids + 'pboOids=' + cbox.value + '&';
              checkval = true;
          }else {
              alert("<%=messageService.getString("e3ps.message.ket_message", "07132") %><%--산출물의 상태가 작업중이거나 재작업중인 경우만 결재가 가능합니다.--%>");
              return;
          }
      }
    }
  }
  
  if(checkval) {
<%--     var url = "/plm/jsp/project/allapprovalPage.jsp?taskOid=<%=oid%>&" + selectList; --%>
//     newWin = window.open(url,"approvalWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
//     newWin.resizeTo(850,600);
//     newWin.focus();
    
    var params = totalSelectOids;
    var addr = "/plm/jsp/wfm/RequestApproval.jsp?" + params + "&popup=popup&mode=goTaskPage";
    var topener = window.open(addr, "approval", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
    topener.focus();
    
  }
}


//산출물 관련 끝 ................................................
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

// ###################     이슈  등록      ########################
  function IssueCreate(){
    var url="/plm/jsp/project/IssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"IssueCreate","870","800","status=no,scrollbars=yes,resizable=yes");
  }


  function onDeleteTableRow(tableid, chk_name, chk_value) {
    targetTable = document.getElementById(tableid);
    var chkTag = document.all.item(chk_name);
    alert(chk_name);
    var chkLen = chkTag.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        if(chkTag[i].value == chk_value) {
          targetTable.deleteRow(i+1);
          return;
        }
      }
    }else {
      if(chkTag.value == chk_value) {
        targetTable.deleteRow(1);
        return;
      }
    }
  }


  function onDeleteDepartTableRow(tableid, cmdstr, objid) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = cmdstr;

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(objid);
    //alert(param);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);
  }

  function onMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    if(msg == 'false' && showElements[0].getElementsByTagName("l_result") != null && showElements[0].getElementsByTagName("l_result")[0].text != ""){
      alert(showElements[0].getElementsByTagName("l_result")[0].text);
      return;
    }

    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    if(targetTableId == "refDeptTable") {
      acceptDept(xmlDoc);
    }else{
      acceptMember(xmlDoc);
    }
  }

  function IssueAdd(issueOid){
  var url="/plm/jsp/project/IssueCreate.jsp?oid=<%=oid%>&cmd=modify&issueOid="+issueOid;
  openOtherName(url,"IssueCreate","870","500","status=no,scrollbars=yes,resizable=yes");
  }


  function issueDelete(issueOid){
    document.forms[0].cmd.value="delete";
    document.forms[0].issueOid.value = issueOid;
    ajax.requestFile = "/plm/jsp/project/ProjectIssueAjaxAction.jsp";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = reload;
    ajax.runAJAX();
  }

  function reload()
  {
    eval(ajax.response);
    enabledAllBtn();
  }

  //function viewIssue(oid) {
    //var url = "/plm/jsp/equipment/equipmentView.jsp?oid=" + oid;
    //openSameName(url,"870","650","status=no,scrollbars=yes,resizable=yes");
  //}

// ################################# 이슈 관리  #############################################
  //function IssueErrorCreate(){
    //var url="/plm/jsp/equipment/equipmentCreate.jsp?oid=<%//=oid%>";
    //openOtherName(url,"IssueCreate","870","700","status=no,scrollbars=yes,resizable=yes");
  //}


  //function IssueAdd(issueOid){
    //var url="/plm/jsp/equipment/UnitErrorAssignWorkComfirm.jsp?oid=" + issueOid;
    //openOtherName(url,"IssueCreate","900","700","status=no,scrollbars=yes,resizable=yes");
  //}

  //Veiw 권한 부서 가져오기 시작 ........................................................................................
  function addDepartment(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?isModal=true&isOpen=false&invokeMethod=openerCall&mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(attacheDept[0][0]);

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, true);

  }


  function changeDeptRow(){
<%

    if (!isAdmin && task.getTaskState()==ProjectStateFlag.TASK_STATE_COMPLETE){ //TASK_STATE_COMPLETE = 5;         //TASK 종료 상태
    
%>
alert('태스크 종료시 책임자Role을 변경할 수 없습니다');
return;
<%
        }

%>

      
    var param = "command=changeDeptRow";


    param += "&oid=<%=oid%>";
    param += "&role=" + document.forms[0].deptRole.value;

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    postCallServer(url, param, onMessage, true);
    setTimeout('document.location.reload()',1000);
    
  }

  function onMessage() {
<%--     document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
    goReloadPage();
  }

  function openerCall(oArr) {

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(oArr[0][0]);

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    postCallServer(url, param, onMessage, true);

  }

  function acceptDept(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");
    var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");

    var targetTable = document.getElementById(targetTableId);
    var len = targetTable.rows.length;
    for(var i=len; i > 1; i--) {
      targetTable.deleteRow(i-1);
    }

    for(var i = 0; i < l_oid.length; i++) {
      //loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      //lcode = decodeURIComponent(l_code[i].text);
      llinkOid = decodeURIComponent(l_linkOid[i].text);

      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = lname;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"', '"+targetLinkMsg+"', '" + llinkOid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }
  }

  function addIssue() {
    var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"addIssue","760","700","status=no,scrollbars=yes,resizable=yes");
  }

  function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","760","700","status=no,scrollbars=yes,resizable=yes");
  }

  function setOEMSave(){

    //if( document.forms[0].oemType.value ==''){
    //  alert("OEM 타입을 선택해 주십시오.");
    //  return;
    //}

    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oem=oemSave";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }

  function onKeyDownHandler(event)
  {
      if (event.keyCode == 13) {
          document.forms[0].taskExecStartDateHidden.focus();
      }
  }

  function setExeDateSave() {
      
      // [PLM System 1차개선] 실제 시작일자에 날짜를 입력하지 않았을 경우 alert message 표시, 2013-08-15, BoLee
      if ( document.getElementById("taskExecStartDate").value == "" ) {
          alert("<%=messageService.getString("e3ps.message.ket_message", "02347") %><%--일자를 입력해주세요--%>");
          document.getElementById("taskExecStartDate").focus();
          return;
      }

      if(checkToday()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01447") %><%--미래일자를 실제 시작일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecStartDate").value = "";
      return;
    }

    if(confirm(document.forms[0].taskExecStartDate.value + "<%=messageService.getString("e3ps.message.ket_message", "03270") %><%--{0}을 실제 시작일로 입력하시겠습니까?--%>")){
      //document.forms[0].method = "post";
      document.forms[0].ExeDate.value = "ExeDate";
        goReloadPage();
        showProcessing();
    }else{
      alert('<%=messageService.getString("e3ps.message.ket_message", "02386") %><%--입력을 취소했습니다--%>');
      document.forms[0].taskExecStartDate.value = "";
    }
  }
  function setExeDateSave2(){
    if(checkToday2()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01448") %><%--미래일자를 실제 종료일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecEndDate").value = "";
      return;
    }
    //if(confirm(document.forms[0].taskExecEndDate.value + "을 실제 종료일로 입력하시겠습니까?")){
      document.forms[0].ExeDate.value = "ExeDate";
        goReloadPage();
    //}else{
    //  alert("입력을 취소했습니다.");
    //  document.forms[0].taskExecEndDate.value = "";
    //}
  }

  function setExeDateSave3(){
    if(checkToday2()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01447") %><%--미래일자를 실제 시작일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecStartDate").value = "";
      return;
    }
    //if(confirm(document.forms[0].taskExecStartDate.value + "을 실제 시작일로 입력하시겠습니까?")){
      document.forms[0].ExeDate.value = "ExeDate";
      //document.forms[0].method = "post";
<%--       document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>"; --%>
//       document.forms[0].submit();
      goReloadPage();
    //}else{
    //  alert("입력을 취소했습니다.");
    //  document.forms[0].taskExecEndDate.value = "";
    //}
  }

  function checkToday(){
    obj = document.getElementById("taskExecStartDate");
    var now = new Date();

      var today = now.getFullYear()+'-'+fncLPAD((now.getMonth()+1))+'-'+fncLPAD(now.getDate());
      if(today < obj.value) {
        return true;
    }
    return false;
  }
  function checkToday2(){
    obj = document.getElementById("taskExecEndDate");
    var now = new Date();

      var today = now.getYear()+'-'+fncLPAD((now.getMonth()+1))+'-'+fncLPAD(now.getDate());

      if(today < obj.value) {
        return true;
    }
    return false;
  }
  function fncLPAD(num)
  {
      if(num<10) return '0'+num;
      else return ''+num;
  }

  // 실제 시작일 ,실제 종료일
  function autoTask(arg){
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?TaskAuto=Auto&AutoType="+arg;
    document.forms[0].method = "post";
    document.forms[0].submit();

  }



  function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
  }

  function checkStartEndDate(){
    var form = document.forms[0];
    //if(form.taskExecEndDate.value == ''){
    //  return true;
    //}
	<%if("".equals(taskExecStartDateStr)){%>
	return true;
    <%}%>
    //if(form.taskExecStartDate.value == ''){
    //  return true;
    //}
    return false;
  }

  function doViewDoc(_oid){
	  
	  if (_oid.indexOf('KETProjectDocument') > 0) {
		  url = "/plm/jsp/dms/ViewDocument.jsp?oid="+_oid;
	      
	      var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
	      leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
	      toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

	      var rest = "width=" + (screen.availWidth * 0.9) + ",height="
	              + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
	              + toppos;

	      window.open(url, '', (opts + rest));  
	  }else{
		  openView(_oid, null, null, false);	  
	  }
	  
    
  }
  function doViewEpm(dieNo){
    var url = "/plm/jsp/project/ProjectOutputDwgDieNoLink.jsp?dieNo="+dieNo;
    openOtherName(url, "popup", 660, 530, "status=yes,scrollbars=no,resizable=yes");
  }

  function inputEtc(){

    var url = "/plm/jsp/project/PopupReason.jsp?oid=<%=oid%>";
    //etcData = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:200px; center:yes");
    openOtherName(url, "", 500, 300, "status=yes,scrollbars=no,resizable=yes");

    /*
    if(typeof etcData == "undefined" || etcData == null) {
      return false;
    }else{
      document.forms[0].compReason.value = etcData;
      return true;
    }*/
  }

  function ViewDoc(oid){
    openView(oid, 870, null, null);
  }

  function ViewReason(){
    var url="/plm/jsp/project/TaskReasonView.jsp?oid=<%=oid%>";
    openOtherName(url,"Reason","500","300","status=no,scrollbars=no,resizable=yes");
  }

  function viewTry(oid){
      
    if(checkStartEndDate()){

        alert('<%=messageService.getString("e3ps.message.ket_message", "07284") %><%--실제 시작일을 먼저 입력 하셔야 합니다--%>');
        document.forms[0].taskExecStartDate.focus();
        return;
    }

    openWindow('/plm/jsp/project/trySchdule/TryViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
  }

  function viewMoldPart(oid){
    var url = "/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid="+oid+"&popup=popup";
    //openWindow('/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid='+oid+"&popup=popup", '',820,800);
    openOtherName(url,"","840","800","scrollbars=yes,resizable=yes,top=200,left=250,center=yes");
  }

  function savePoint(){
    document.forms[0].action = "/plm/jsp/project/TaskViewAction.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }

  // 설계변경 상세조회 팝업
  function viewEcoPop(ecoOid, ecoType){
    if(ecoOid.indexOf("Prod") > -1){
      ecoType = "P";
    }
    var url = "";
    //alert(ecoType);
    if (ecoType != null && ecoType == "P") {// 제품
      url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + ecoOid;
    }else {                  // 금형
      url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + ecoOid;
    }

    openOtherName(url,"","1024","768","scrollbars=yes,resizable=yes,top=200,left=250,center=yes");
  }

  function isValidDate(obj, maxLength) {
      var retVal = true;
      var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
     //document.forms[0].duration.value = "";

    if(obj.value == "") {
      return;
    }

    val=obj.value;
    re=/[^0-9]/gi;
    obj.value=val.replace(re,"");

      var inputDate = funcReplaceStrAll(obj.value,  '<%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00039") %><%--{0}월--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>', '');

      var yyyy = inputDate.substring(0, 4);
      var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
      var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

      if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
      if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
      if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

    if(inputDate.length == 8) {
        inputDate = inputDate.substring(0, 8); //미봉책
    }else{
      alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
      return;
    }

    obj.value = yyyy+ "-" +mm+ "-" +dd;
      return true;
  }
  function viewErrMsg(obj,msg) {
    alert(obj.value + " " + msg);
  }
  //********************************************************************
  //문자열 일괄전환 함수
  function funcReplaceStrAll(org_str, find_str, replace_str) {
      var pos = org_str.indexOf(find_str);
      while(pos != -1) {
          pre_str  = org_str.substring(0, pos);
          post_str = org_str.substring(pos + find_str.length, org_str.length);
          org_str  = pre_str + replace_str + post_str;
          pos = org_str.indexOf(find_str);
      }
      return org_str;
  }

  //*******************************************************************
  //년월 입력시 마지막 일자
  function  getEndOfMonthDay( yy, mm ) {
      var max_days=0;
      if(mm == 1) {
          max_days = 31 ;
      } else if(mm == 2) {
          if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
          else                                                         max_days = 28;
      }
      else if (mm == 3)   max_days = 31;
      else if (mm == 4)   max_days = 30;
      else if (mm == 5)   max_days = 31;
      else if (mm == 6)   max_days = 30;
      else if (mm == 7)   max_days = 31;
      else if (mm == 8)   max_days = 31;
      else if (mm == 9)   max_days = 30;
      else if (mm == 10)  max_days = 31;
      else if (mm == 11)  max_days = 30;
      else if (mm == 12)  max_days = 31;
      else                return '';

      return max_days;
  }

  function viewProjectTask(taskComp) {
      var url="/plm/ext/project/task/completeProjectTaskViewForm.do?oid=<%=oid%>";
      openOtherName(url,"purchase","780","630","status=no,scrollbars=yes,resizable=yes");
  }
  
  function completeProjectTask(taskComp) {
      // [START] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee
<%--       alert('<%=isAdmin%>, <%=dependCheck%>, <%=dependTaskData.taskState%>'); --%>

	<%
    String EDITMODE = "";
    
    if(!(isTaskMaster || isAdmin) || buttonAuth.isComplated)                           EDITMODE = "VIEW";
    else if("COST001".equals(taskCategory))          EDITMODE = "TREEEDIT";
	
	if("COST".equals(task.getTaskType())){%>
	
		var param = {
				EDITMODE : "<%=EDITMODE%>",
				taskOid : "<%=oid%>",
				projectOid : "<%=projectOid%>"
		}
		var isError = true;
		
		$.ajax({
			type : "POST",
			url : "/plm/ext/cost/bomEditorNeedCheck",
			data : JSON.stringify(param),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(res){
				if(res.message){
					alert(res.message);
				}
				if(res.result){
					window.console.log(res);
					isError = !res.checkResult;
				}
			},
			error : function(res){
				window.console.log(res);
				alert("ERROR : " + res);
			}
		});
		
		if(isError){
			return;
		}
		
	<%}%>


var planWorkTime = '<%=task.getPlanWorkTime()%>';

if(planWorkTime == '' || planWorkTime <= 0){
    alert("계획공수가 입력되지 않았습니다.");
    return;
}

<%-- <%if(isProtoGate1Check && !isProtoGate1Pass){%>
alert("Proto Gate1이 완료되지 않았습니다.\r\n선 종결 후 진행하시기 바랍니다.");
return;
<%}%> --%>

<%
//DR태스크의 경우 점수를 계산하여 0점이상되어야 완료처리가 가능하도록 수정조치



if("DR".equals(taskTypeName) && (DR_PASS_CHECK.equals("NG") || DR_PASS_CHECK.equals("NONE")) ) {

%>
  alert("아래의 경우 완료 할 수 없습니다.\r\n\r\n1.판정결과가 없는 경우\r\n2.판정결과가 NG인 경우\r\n3.목표값이 0 또는 설정되지 않은 경우"); 
  return;
<%
}
%>


	  <%
		if ( !isAdmin && dependTaskData!=null && dependTaskData.taskState != 5) {
	  %>
	      alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
	      return;
      <%
		}
      %>
      
      <%
          if ( !isAdmin && dependCheck ) {
      %>
      alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
      return;
      <%
          }
      %>
      // [END] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee
      
      var outputRstCnt = '';//aa
      var kqisCnt = 0;
      var isKqisTrust = document.getElementById("kqisList");
      if(isKqisTrust){
    	  kqisCnt = document.getElementById("kqisList").rows.length;
      }
      <%
        ProjectOutput outputTrust = null;
        int outputTrustRsltRatio = 0;
        int outputTrustRsltSum = 0;
        int continueCount = 0;
        int listSize = 0;
        String trustLastNgCnt = "";
        int outputRstCntInt = 0;
        String rstNgCnt = "";
        int essentialCount = 0; 
        String NgReason = "";
        String taskTrustTypeName = taskTypeName;
    
        if("신뢰성평가".equals(taskTrustTypeName) ) {
            List<TrustTaskResultDTO>  taskTrustResultList = ProjectTaskCompHelper.service.getTaskTrustResultList(oid);
            trustProgressRate = "0";
            if(taskTrustResultList!=null) {
            	outputRstCntInt = taskTrustResultList.size();
                if(taskTrustResultList!=null && taskTrustResultList.size()>0) {
            	  for(int a=0;a<taskTrustResultList.size();a++) {
            	      TrustTaskResultDTO gDto2 = taskTrustResultList.get(a);
                      rstNgCnt = gDto2.getNgCnt();
                      NgReason = gDto2.getNgReason();
//             	      Kogger.debug(">>>>>>>>>>gDto2.getOutputOid():"+gDto2.getOutputOid());
            	      ProjectOutput po = (ProjectOutput) CommonUtil.getObject(gDto2.getOutputOid() ) ;
            	      if(po!=null) {
    	        	      if (po.getCompletion() < 0 || !po.isIsPrimary()) {
    	                      continueCount++;
    	                      continue;
    	                  }else{
    	                      essentialCount++;
    	                  }
    	                  outputTrustRsltSum += po.getCompletion();
            	      }else {
            		      continueCount++;
                      }
            	  }
                  if ((taskTrustResultList.size() - continueCount) > 0) outputTrustRsltRatio = outputTrustRsltSum / (taskTrustResultList.size() - continueCount);
                  else  outputTrustRsltRatio = 100;       //필수산출물이 없는경우
                  trustProgressRate = ""+outputTrustRsltRatio; 
                    
                }
            }
            if(taskTrustResultList == null || outputRstCntInt == 0){
        	    taskTrustTypeName = "";
            }
        }else if("DR".equals(taskTypeName) && project instanceof ProductProject) {
            QueryResult assessQr = ProjectTaskCompHelper.service.getOutPutByTask(task);
            assessProgressRate = "0";
            if(assessQr!=null) {
                outputRstCntInt = assessQr.size();
                while (assessQr.hasMoreElements()) {
                    Object o[] = (Object[]) assessQr.nextElement();
                    AssessProjectOutputLink link = (AssessProjectOutputLink) o[0];
                    
                    ProjectOutput po = link.getOutput();
                    
                    if (po.getCompletion() < 0 || !po.isIsPrimary()) {
                        continueCount++;
                        continue;
                    }else{
                        essentialCount++;
                    }
                    outputTrustRsltSum += po.getCompletion();
                }
        
                if ((assessQr.size() - continueCount) > 0) outputTrustRsltRatio = outputTrustRsltSum / (assessQr.size() - continueCount);
                else  outputTrustRsltRatio = 100;       //필수산출물이 없는경우
                assessProgressRate = ""+outputTrustRsltRatio;
            }
            
        }else {
	        QueryResult qr2 = ProjectOutputHelper.manager.getTaskOutput(task);
	        if(qr2!=null) {
	            outputRstCntInt = qr2.size();
		        while (qr2.hasMoreElements()) {
		            Object o[] = (Object[]) qr2.nextElement();
		            ProjectOutput po = (ProjectOutput) o[0];
		            if (po.getCompletion() < 0 || !po.isIsPrimary()) {
		                continueCount++;
		                continue;
		            }else{
		        	    essentialCount++;
		            }
		            outputTrustRsltSum += po.getCompletion();
		        }
		
		        if ((qr2.size() - continueCount) > 0) outputTrustRsltRatio = outputTrustRsltSum / (qr2.size() - continueCount);
		        else  outputTrustRsltRatio = 100;       //필수산출물이 없는경우
	        }
        }
    
      %>
        outputRstCnt = <%=outputRstCntInt%>;
        taskComp = <%=outputTrustRsltRatio%>;
        
        
        <%
        if(!"Gate".equalsIgnoreCase(taskTypeName) && !"COST".equals(task.getTaskType()) && essentialCount < 1) {
        %>
        if(outputRstCnt > 0){
        	alert("<%=messageService.getString("e3ps.message.ket_message", "07340") %><%--필수 산출물이 없는 경우 완료할 수 없습니다--%>");
            return;	
        }
        
        <%
        }
        %>

		<%
			if("신뢰성평가".equals(taskTrustTypeName) && !"0".equals(rstNgCnt)) {
			    
			if("DV".equals(taskCategory)){
			    
			    if(StringUtil.checkNull(NgReason).equals("")){
        %>
                  alert("신뢰성시험(DV)최종 차수의 결과값이 NG 입니다.\r\nNG사유 및 항목에 추후수행 계획을 입력바랍니다.");
                  
                  return;
        <%      }
			}else{
		%>        
		          alert("<%=messageService.getString("e3ps.message.ket_message", "07236") %><%--신뢰성 평가 최종차수의 결과가 OK인 경우 완료 가능합니다--%>"); 
                  return;
		<%
			}
		%>
	   <%
	        }
		
		    if("신뢰성평가".equals(taskTrustTypeName) && (outputRstCntInt==0 )) {
	   %>
		   alert("<%=messageService.getString("e3ps.message.ket_message", "07288") %><%--산출물이 없는 경우 완료할 수 없습니다--%>");
		   return;
	   <%
			}
	   %>


      <%
	      if(!"Gate".equals(taskTypeName) && outputTrustRsltRatio!=100) {
	  %>
	    var comMessage = "";
	  <%
          if("신뢰성평가".equals(taskTrustTypeName) && !"DV".equals(taskCategory)) {
      %>
            comMessage += "<%=messageService.getString("e3ps.message.ket_message", "07236") %><%--신뢰성 평가 최종차수의 결과가 OK인 경우 완료 가능합니다--%>\n";
      <%
          }
      %>
            if(isKqisTrust && outputRstCnt < 1){//신뢰성평가 task이면서 기존 차수관리가 진행되지 않았을 경우
            	
            }else{
                comMessage += "<%=messageService.getString("e3ps.message.ket_message", "07289") %><%--필수 산출물이 모두 100%가 되지 않아 완료할 수 없습니다--%>";
                alert(comMessage); 
                return;	
            }
            		
            
	        
	  <%
	      }
	  %>
      
      <%
      //Gate 태스크인 경우 평가결과가 진행중일때는 완료할 수 없다
      if("Gate".equals(taskTypeName)) {
         String resultState = "";
         String resultTotalStr = "";
         QueryResult qur = PersistenceHelper.manager.navigate(task, "assess", ext.ket.project.gate.entity.GateAssRsltTaskLink.class);
         ext.ket.project.gate.entity.GateAssessResult gateRslt = null;
         if (qur.hasMoreElements()) {
            gateRslt = (ext.ket.project.gate.entity.GateAssessResult) qur.nextElement();
            if (gateRslt != null) {
                resultState = gateRslt.getState().toString();
            }
         }
         if(!"APPROVED".equals(resultState)) {
          
  %>
      alert("<%=messageService.getString("e3ps.message.ket_message", "07243") %><%--평가결과 승인완료 후 완료 가능합니다--%>");
      return;
      
      
  <%
         }
         
         try {
             
             int gate_rev_ = ProjectTaskCompHelper.service.getMaxGateDegree(oid);
             List<String> gateTotalOutputSum = new ArrayList<String>();
             Hashtable gateResultTotalHash = new Hashtable();
             //평가결과(모든 결과를 Hashtable에 담는다)
             gateResultTotalHash = ProjectTaskCompHelper.service.getGateAssessResultInfoList(oid, true, gate_rev_);
             
             if(gateResultTotalHash!=null) {
                  resultTotalStr = StringUtil.checkNull((String)gateResultTotalHash.get("resultTotalStr")) ;
             }
         }catch(Exception e) {
             Kogger.error(e);
         }
         
         if("R".equals(resultTotalStr)){
%>
            alert("평가결과가 불합격인 경우 완료 할 수 없습니다.\r\n차수생성을 진행하시기 바랍니다.");
            return;
<%
         }
         
%>

<%--              alert("<%=messageService.getString("e3ps.message.ket_message", "07243") %>평가결과 승인완료 후 완료 가능합니다"); --%>
//              return;
<%
      }else {
  %>
        if(outputRstCnt>0 && taskComp<100){

          alert('<%=messageService.getString("e3ps.message.ket_message", "07174") %><%--필수 산출물이 모두 승인완료 되어야 완료 가능합니다--%>');
          return;
        }
        
        if(isKqisTrust){
        	
        	if(kqisCnt < 1){
        		alert('연계 등록된 신뢰성 평가결과 의뢰서(KQIS)가 없습니다.\n현재 화면에 보이는 의뢰서 등록 버튼을 클릭하여 의뢰서를\n링크등록하시기 바랍니다.');
        		return;
        	}
        	if(!kqisTrustCheck()){
        		alert('연계 등록된 신뢰성 평가결과 의뢰서(KQIS) 목록 중\n진행단계가 [완료] 가 아닌 건이 존재합니다.');
                return;
        	}
        }
<%
      }
%>
       
      if(checkStartEndDate()){

        alert('<%=messageService.getString("e3ps.message.ket_message", "02060") %><%--실제 시작일을 입력 하셔야 \n 태스크를 완료 할 수 있습니다--%>');
        document.forms[0].taskExecStartDate.focus();
        return;
      }
      
      if(isTaskCompleteYn != 'OK'){
    	alert(isATFTMsg+"\r\nATFT 최종완료 후 Task 완료 가능합니다.");
    	return;
      }
      
      if(isTaskCompleteDrYn != 'OK'){
          alert("양산최종평가(DR6) Task는 Gate가 모두 종료되어야 완료 가능합니다.");
          return;
      }
      
      if(isTaskCompleteTryYn != 'OK'){
          alert("Try결과서가 Try조건표로 등록되지 않았습니다.");
          return;
      }
      
      var url="/plm/ext/project/task/completeProjectTaskForm.do?oid=<%=oid%>";
      openOtherName(url,"purchase","780","630","status=no,scrollbars=yes,resizable=yes");
  }
  

  function goReloadPage() {
      document.forms[0].method = "post";
      document.forms[0].submit();
  }
  
  function goTaskPage() {
      parent.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=popup';
  }
  
  function feedbackAfterPopup() {
	  goReloadPage();
  }
  
  function SetCompNum(idx) {
//       alert ("The Unicode key code of the released key: " + idx);
      
      var chkAll = document.getElementsByName('outputCompletion');
      var len = chkAll.length;
      if (len > 1) {
    	  var idxVal = chkAll[idx].value;
    	  if(idxVal==100 || idxVal>100) {
    		  alert('<%=messageService.getString("e3ps.message.ket_message", "02732") %><%--진행율을 100미만으로 입력하시기 바랍니다--%>');
    		  $('[name=outputCompletion]')[idx].value = 0;
    		  return;
    	  }
      } else if ( len == 1 ) {
//         alert('idx:'+idx+',len:'+len+',chkAll.value:'+document.getElementsByName('outputCompletion')+',chkAll:'+$('[name=outputCompletion]').value);
          var idxVal = $('[name=outputCompletion]').val();

           alert('idx:'+idx+',idxVal:'+idxVal);
          if(idxVal==100 || idxVal>100) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "02732") %><%--진행율을 100미만으로 입력하시기 바랍니다--%>');
              $('[name=outputCompletion]').val(0);
              return;
          }
      }
  }
  
  function openInvestPopUp(){
	  getOpenWindow2("/plm/ext/invest/saveInvestPopup?taskOid=<%=oid %>", "InvestPopup", "1280", "720");  
  }

  function checkCostRequest(){
	  var msg = '';

      $.ajax({
          type: 'post',
          url : '/plm/ext/cost/checkCostRequest.do?taskOid='+'<%=oid%>',
          async : false,
          cache:false,
          success : function(result){
              if(result.msg != ''){
                  msg = result.msg;
              }
          }
      });
      
      return msg;
      
  }
  
  function openBomEditor(mode){
	 <%
      if ( (dependTaskData!=null && dependTaskData.taskState != 5) || dependCheck ) {
	  %>
	  alert("선행타스크가 완료되지 않았습니다.");
	  return;
	  <%
	      }
	  %>
	  
	  var msg = checkCostRequest();

      
      if(msg != ''){
          alert(msg);
          return;
      }
	  
	  
      getOpenWindow2("/plm/ext/cost/costBomEditor?isPopup=true&FILTERMODE=true&taskOid=<%=oid %>&EDITMODE=" + mode, "COSTBOMEDITOR", "1280", "720");
  }
  
  
  function openCostCaseList(){
	  <%
      if ( (dependTaskData!=null && dependTaskData.taskState != 5) || dependCheck ) {
      %>
      alert("선행타스크가 완료되지 않았습니다.");
      return;
      <%
          }
      %>
      
      var msg = checkCostRequest();

      
      if(msg != ''){
          alert(msg);
          return;
      }
      
      getOpenWindow2("/plm/ext/cost/costCaseListPopup?taskOid=<%=oid %>", "COSTCASE", "1280", "720");
      
  }
  
  function openRealPartNo(){
	  
	  <%
      if ( (dependTaskData!=null && dependTaskData.taskState != 5) || dependCheck ) {
      %>
      alert("선행타스크가 완료되지 않았습니다.");
      return;
      <%
          }
      %>
     
      
      getOpenWindow2("/plm/ext/cost/costRealPartNoPopup?&taskOid=<%=oid %>", "COSTREALPARTNO", "500", "100");
	  
  }


-->
</script>
<%@include file="/jsp/common/processing.html"%>
<script src="/plm/portal/js/script.js"></script>
<style type="text/css">
body {
	margin-left: 12px;
	margin-top: 15px;
	margin-right: 0px;
	margin-bottom: 5px;
	overflow-x: auto;
	overflow-y: auto;
	scrollbar-highlight-color: #f4f6fb;
	scrollbar-3dlight-color: #c7d0e6;
	scrollbar-face-color: #f4f6fb;
	scrollbar-shadow-color: #f4f6fb;
	scrollbar-darkshadow-color: #c7d0e6;
	scrollbar-track-color: #f4f6fb;
	scrollbar-arrow-color: #607ddb;
}
</style>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    
                    <%if("투자오더발행".equals(task.getTaskName()) && CommonUtil.isMember("고객직접투자비등록관리") && (isTaskMaster || isAdmin)){ %>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="#" onclick="javascript:openInvestPopUp();" class="btn_blue">고객투자비 등록</a>
                                </td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                    <td align="right">&nbsp;</td>
                    <%} %>
                	<%if("COST".equals(task.getTaskType()) && buttonAuth.isLatest && (project.getState().toString().equals("PROGRESS") || project.getState().toString().equals("COMPLETED"))){ %>
                	<td align="right">&nbsp;</td>
                	 <%
                     if((isTaskMaster || isAdmin) && !buttonAuth.isComplated && !task.isCostRequest() &&
                             project instanceof ProductProject && "COST013".equals(task.getTaskTypeCategory())){
                     %>
                	<!--<td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="#" onclick="javascript:openRealPartNo();" class="btn_blue">품번 연계</a>
                                </td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                    <td align="right">&nbsp;</td>-->
                    <%} %>

                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                	<a href="#" onclick="javascript:openBomEditor('<%=EDITMODE %>');" class="btn_blue">원가 요청서</a>
                               	</td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
         
                    <td align="right">&nbsp;</td>
                    
                    <%if((isTaskMaster || isAdmin) && "COST015".equals(task.getTaskTypeCategory()) ){ %>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="#" onclick="javascript:openCostCaseList();" class="btn_blue">원가 산출</a>
                                </td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                    <td align="right">&nbsp;</td>
                    <%} %>
                    
                    <%} %>
                    <td>
                        <%
                        //buttonAuth.isComplated : 태스크가 완료된 경우
                        //buttonAuth.isTaskInfoModify = isTaskMaster(PM이 있는 프로젝트) && isLatest(최신 프로젝트);
                        //isAdmin : Administrators권한
                        //isPMOMember : PMO그룹에 속한자
                        //isPmo : 자동차PMO
                        //isPmo2 : 전자PMO
                        //if(!buttonAuth.isComplated && (buttonAuth.isTaskInfoModify || isAdmin || isPmo || isPmo2 || isEdit)){ 
                        if(!buttonAuth.isComplated && isAdmin){ //관리자만 수정이 가능 
                            
                            if(pjtLatestOid.equals(projectOid)) { %> <%--                   <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:updataTask();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02926") %>태스크 수정</a></span><span class="pro-cell b-right"></span></span></span> --%>

                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                    onclick="javascript:updataTask();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02926") %><%--태스크 수정--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table> <% }
                    }
                    %>
                    </td>
                    <td align="right">&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                    onclick="javascript:parent.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<!-- TASK 기본정보 -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm2"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <COL width="15%">
    <COL width="18%">
    <COL width="15%">
    <COL width="18%">
    <COL width="15%">
    <COL width="18%">

    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
        <td class="tdwhiteL">&nbsp;<%=taskData.taskName%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165") %><%--계획{0}시작일자--%></td>
        <td class="tdwhiteL"><%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>&nbsp;</td>

        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166") %><%--계획{0}종료일자--%></td>
        <td class="tdwhiteL"><%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>&nbsp;</td>
    </tr>
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02921") %><%--태스크 기간--%></td>
        <td class="tdwhiteL">&nbsp;<%=taskData.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
        </td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
        <td class="tdwhiteL">
            <% if(!isDate && (isPM || isAdmin|| isPmo  || isPmo2 || isPmo3  || isTaskMaster || isTaskMember)) { %> 
            	<% if(taskData.taskCompletion==100 && task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE ) {%>
            		<%=(taskData.taskExecStartDate==null)?"-":DateUtil.getDateString(taskData.taskExecStartDate, "D")%> 
            		<input type="hidden" name="taskExecStartDate" value="<%=taskExecStartDateStr%>"> 
            	<%}else{%> 
            		<input type="text" id="taskExecStartDate" name="taskExecStartDate" class="txt_field" style="width: 70%" value="<%=DateUtil.getDateString(taskData.taskExecStartDate, "D")%>" onChange="javascript:setExeDateSave();" onKeyDown="javascript:onKeyDownHandler(event)" /> <!-- <input type="text" id="taskExecStartDateHidden" name="taskExecStartDateHidden" style="width: 0; border: none; border-right: 0px; border-top: 0px; boder-left: 0px; boder-bottom: 0px;" /> --> 
            	<%}%> 
            <% }else {
	            	if(taskData.taskCompletion==100 && task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE ){%> <%-- <%=taskExecStartDateStr %> --%>
	                	<%=(taskData.taskExecStartDate==null)?"-":DateUtil.getDateString(taskData.taskExecStartDate, "D")%>
	              <%}else{ %>                  
	            		<input type="text" id="taskExecStartDate" name="taskExecStartDate" class="txt_field" style="width: 70%" value="<%=DateUtil.getDateString(taskData.taskExecStartDate, "D")%>" onChange="javascript:setExeDateSave();" onKeyDown="javascript:onKeyDownHandler(event)" />
	            		<%-- <input type="hidden" name="taskExecStartDate" value="<%=taskExecStartDateStr%>">--%> 
	              <%}
               }
            %> 
        </td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
        <td class="tdwhiteL"><%=taskExecEndDateStr%><input type=hidden name=taskExecEndDate size="12" value="<%=taskExecEndDateStr%>">
            &nbsp;</td>
    </tr>
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03128") %><%--필수 / Type--%></td>
        <td class="tdwhiteL">
            <%if(task.isOptionType()) {
                     out.print("Y / ");
                    }else{
                    out.print("N / ");
                    }
                    %><%=task.getTaskType() == null?messageService.getString("e3ps.message.ket_message", "02345")/*일반*/:task.getTaskType()%> <%=(("COST".equals(task.getTaskType()) || "Gate".equals(task.getTaskType()) || "신뢰성평가".equals(task.getTaskType()) || ("DR".equals(task.getTaskType()) && !"-".equals(task.getTaskTypeCategory()))) && !StringUtil.isEmpty(task.getTaskTypeCategory()))?"/ "+task.getTaskTypeCategory():"" %>
        </td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09519") %><%--주요일정코드--%></td>
        <td class="tdwhiteL">
        <% if((isPM || isAdmin) && buttonAuth.isLatest && (project.getState().toString().equals("PROGRESS") || project.getState().toString().equals("COMPLETED")) ) { %>
        <ket:select id="mainScheduleCode" name="mainScheduleCode" className="fm_jmp" style="width: 200px;" codeType="MAINSCHEDULECODE" multiple='multiple' useCodeValue="true" useNullValue="true" value="<%=StringUtil.checkNull(task.getMainScheduleCode()) %>" /> 
        <%}else{ %>
        <%=mainScheduleName%>
        <%} %>
        </td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
        <td class="tdwhiteL" title="<%=task.getTaskDesc()==null?"":task.getTaskDesc()%>">&nbsp;<a href="#" onclick="javascript:ViewReason();"> <%=(taskData.taskDesc.trim()=="")?"":taskData.taskDesc.trim()%></a>
        </td>
    </tr>
    <%if(project.getPjtType() == 3 && task.getNcha() > 0 && task.isDebug_n()){%>
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03349") %><%--디버깅 사유--%></td>
        <td class="tdwhiteL">
            <%
                  int reason = 0;
                  try{
                    reason = Integer.parseInt(task.getReason());
                  }catch(Exception e){
      
                  }
                  switch(reason){
                  case 1:
                    out.print(messageService.getString("e3ps.message.ket_message", "00850")/*고객요청*/);
                    break;
                  case 2:
                    out.print(messageService.getString("e3ps.message.ket_message", "03027")/*품질문제*/);
                    break;
                  case 3:
                    out.print(messageService.getString("e3ps.message.ket_message", "02601") /*제품설계변경*/);
                    break;
                  case 4:
                    out.print(messageService.getString("e3ps.message.ket_message", "01063")/*금형보완*/);
                    break;
                  case 5:
                    out.print(messageService.getString("e3ps.message.ket_message", "01136")/*기타*/);
                    break;
                  case 6:
                    out.print(messageService.getString("e3ps.message.ket_message", "01784")/*생산성{0} 향상*/);
                    break;
                  default:
                    out.print("&nbsp;");
                    break;
                  }
      
                  %>
        </td>

        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03350") %><%--특이사항--%></td>
        <td class="tdwhiteL0" colspan=3><textarea name="detail" style="width: 100%" rows="3" class="fm_area"
                onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=task.getSpecial()==null?"":task.getSpecial() %></textarea>
        </td>
    </tr>
    <%}%>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space20"></td>
    </tr>
</table>
<!-- space -->

<!-- 진행현황 시작 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02736") %><%--진행현황--%></td>
        <td align="right">&nbsp; <%
                    
                    if(taskData.taskCompletion==100 && task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE && 
                    (isAdmin || (isPmo && projectData.teamType.equals("자동차 사업부")) || (isPmo2 && projectData.teamType.equals("전자 사업부")) || (isPmo3 && projectData.teamType.equals("KETS"))|| isTaskMaster || isTaskMember) ){
                    %> <font color="red">※<%=messageService.getString("e3ps.message.ket_message", "07238") %> <%--완료버튼으로 최종 태스크 완료해주세요. --%>
        </font> <%
                    }
                    %>
        </td>

    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm2"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <COL width="12%">
    <COL width="10%">
    <COL width="15%">
    <COL width="19%">
    <COL width="19%">
    <COL width="25%">
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
        <td class="tdwhiteL" style="text-align: center"><%=taskData.getStateStr()%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
        <td class="tdwhiteL" style="text-align: center">
            <%
//차수가 없는 산출물이 연결되어 있을수 있기 때문에 신뢰성 평가 태스크의 산출물은 차수에 연결된 산출물만 대상으로 진행률을 계산한다    
    if("신뢰성평가".equals(taskTypeName)) {
%> <b id="trustRate"><%=trustProgressRate%> / <%=taskData.getPreferCompStr()%></b> <%
    }else if("DR".equals(taskTypeName)) {
%> <b><%=assessProgressRate%> / <%=taskData.getPreferCompStr()%></b> <%
    }else {
%> <b><%=taskData.taskCompletion%> / <%=taskData.getPreferCompStr()%></b> <%
    }
%>
        </td>

        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07239") %><%--계획/실제작업시간--%></td>
        <td class="tdwhiteL" style="text-align: center">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td><A href="JavaScript:viewProjectTask(<%=taskData.taskCompletion%>);"> <b><%=task.getPlanWorkTime() %> /
                                <%=task.getExecWorkTime()%></b>
                    </A></td>
                    <td>
                        <%
                    if(task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE && !buttonAuth.isComplated && (buttonAuth.isTaskInfoModify 
                	    || isAdmin || (isPmo && projectData.teamType.equals("자동차 사업부")) || (isPmo2 && projectData.teamType.equals("전자 사업부"))|| (isPmo3 && projectData.teamType.equals("KETS")) || isEdit)){ 

                    %> <%

if(pjtLatestOid.equals(projectOid) && project.getState().toString().equals("PROGRESS")) { %>

                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                    onclick="JavaScript:completeProjectTask(<%=taskData.taskCompletion%>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table> <%
                    }
                    %> <%
                    }
                    %>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                    onclick="JavaScript:completeProjectTask(<%=taskData.taskCompletion%>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02446") %></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>
<%
try{
  QueryResult qrChild = ProjectTaskHelper.manager.getChild(task);

  if(qrChild.size() != 0 && false){
    Collections.sort(qrChild.getObjectVectorIfc().getVector(), new TaskPlanComparator());



%>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space20"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "03138") %><%--하위 태스크 진행현황--%></td>
        <td align="right">(<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%>
            <font color="blue"> <b>/</b></font> <%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%>)
        </td>

    </tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm2"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <COL width="15%">

    <COL width="10%">
    <COL width="10%">
    <COL width="10%">

    <COL width="10%">
    <COL width="10%">

    <COL width="15%">
    <COL width="10%">


    <tr>
        <td class="tdblueL"><b><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></b></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03351") %><%--기간/표준공수--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064") %><%--실제시작일--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02067") %><%--실제종료일--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>

    </tr>
</table>

<%
    while(qrChild.hasMoreElements()){
      Object[] taskObj = (Object[]) qrChild.nextElement();
      TemplateTask child = (TemplateTask) taskObj[0];
      E3PSTaskData dependData = new E3PSTaskData( (E3PSTask)child);

      String childtaskExecStartDateStr = "&nbsp;";
      String childtaskExecEndDateStr = "&nbsp;";

      String childPlanStartStr = DateUtil.getDateString(dependData.taskPlanStartDate, "D");
      String childPlanEndStr = DateUtil.getDateString(dependData.taskPlanEndDate, "D");

      if(dependData.taskExecStartDate != null){
        childtaskExecStartDateStr = DateUtil.getDateString(dependData.taskExecStartDate, "D");
      }
      if(dependData.taskExecEndDate != null){
        childtaskExecEndDateStr = DateUtil.getDateString(dependData.taskExecEndDate, "D");
      }

%>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <COL width="15%">

    <COL width="10%">
    <COL width="10%">
    <COL width="10%">

    <COL width="10%">
    <COL width="10%">

    <COL width="15%">
    <COL width="10%">

    <tr>
        <td class="tdwhiteL" title="<%=dependData.taskName%>"><b><%=dependData.taskName%></b></td>
        <td class="tdwhiteL"><%=childPlanStartStr%></td>
        <td class="tdwhiteL"><%=childPlanEndStr%></td>
        <td class="tdwhiteL"><%=dependData.taskDuration %>/<%=dependData.taskStdWork%></td>
        <td class="tdwhiteL"><%=childtaskExecStartDateStr%></td>
        <td class="tdwhiteL"><%=childtaskExecEndDateStr%></td>
        <td class="tdwhiteL"><%=dependData.taskCompletion%> / <%=dependData.getPreferCompStr()%></td>
        <td class="tdwhiteL"><%=dependData.getStateStr()%></td>
    </tr>


</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm1"></td>
    </tr>
</table>
<%
    }
  }
}catch(Exception ee){
    Kogger.error(ee);
}
%>
<!-- space -->

<!-- ####################  부서 권한 추가(태스크 책임자) Start ##################################-->
<%
  if("일반".equals(taskTypeName) || "DR".equals(taskTypeName) || "신뢰성평가".equals(taskTypeName) || "Try".equals(taskTypeName) || "Gate".equals(taskTypeName) || "COST".equals(taskTypeName)) {
%>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space20"></td>
    </tr>
</table>
<!-- space -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02929") %><%--태스크 책임자--%></td>
        <td align="right">&nbsp;</td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm2"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <COL width="15%">
    <COL width="35%">
    <COL width="15%">
    <COL width="35%">
    <tr>
        <%
  HashMap legacyMap = new HashMap();
%>
        <td class="tdblueL">Role</td>
        <td class="tdwhiteL">
            <%
                String deptRole2 = task.getPersonRole();
                Role role2 = null;
                if(deptRole2 != null){
                  role2 = Role.toRole(deptRole2);
                }
                if(buttonAuth.isRoleChangeSelected || isAdmin){
                    if (!isAdmin && task.getTaskState()==ProjectStateFlag.TASK_STATE_COMPLETE ){ //TASK_STATE_COMPLETE = 5;         //TASK 종료 상태
             %> <select name="deptRole" class="fm_jmp" style="width: 200" disabled>
                <%}else {%>
                <select name="deptRole" class="fm_jmp" style="width: 200" onchange='javaScript:changeDeptRow()'>
                    <%}%>
                    <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]
                    </option>
                    <option value="PM" <%if("PM".equals(task.getPersonRole())) {%> selected <%}%>>
                        <%if(project instanceof MoldProject){ %><%=messageService.getString("e3ps.message.ket_message", "03352") %><%--금형개발--%>
                        <%}else{ %>PM<%} %>
                    </option>
                    <%
      
                  if(vecTeamStd!=null){
      
                    Collections.sort(vecTeamStd, new RoleComparator());
      
                    //roleLink = null;
                    for (int i = 0; i < vecTeamStd.size(); i++) {
                      Role role = (Role) vecTeamStd.get(i);
                      String roleKey = role.toString();
                      String selected = "";
                      if(roleKey.equals(deptRole2)){
                        selected = "selected";
                      }
                      String roleName_ko = role.getDisplay(messageService.getLocale());
      
      
      
      
                  %>
                    <option value="<%=roleKey%>" <%=selected%>><%=roleName_ko%></option>
                    <%  }
                  }%>
            </select>

                <%}else{%>

                <%=role2 != null ? ("PM".equals(role2.getDisplay(Locale.KOREA)) && project instanceof MoldProject?messageService.getString("e3ps.message.ket_message", "03352")/*금형개발*/:role2.getDisplay(messageService.getLocale())) : "-"%>
                <%}%>
        
        </td>

        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02929") %><%--태스크 책임자--%></td>
        <td class="tdwhiteL">
            <%
                  String addMaster = request.getParameter("addMaster");
                  String deleteMaster = request.getParameter("deleteMaster");
                  if(addMaster == null){
                    addMaster = "";
                  }
                  if(deleteMaster == null){
                    deleteMaster = "";
                  }
                %> <jsp:include page="/jsp/project/TaskMasterInfo_include.jsp" flush="false">
                <jsp:param name="oid" value="<%=oid%>" />
                <jsp:param name="addMaster" value="<%=addMaster%>" />
                <jsp:param name="deleteMaster" value="<%=deleteMaster%>" />
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02932") %><%--태스크참여자--%></td>
        <td class="tdwhiteL" colspan="3">
            <%
      
                  if(addMaster == null){
                    addMaster = "";
                  }
                  if(deleteMaster == null){
                    deleteMaster = "";
                  }
                %> <jsp:include page="/jsp/project/TaskMemberInfo_include.jsp" flush="false">
                <jsp:param name="oid" value="<%=oid%>" />
                <jsp:param name="addMaster" value="<%=addMaster%>" />
                <jsp:param name="deleteMember" value="<%=deleteMaster%>" />
            </jsp:include>
        </td>
    </tr>
</table>
<%
  }
%>
<!-- ####################  부서 권한 추가(테스크 책임자) end  ##################################-->

<!-- ####################  산출물 관리 start  ##################################-->
<%
int outputRstCnt = 0;

  if(("DR".equalsIgnoreCase(taskTypeName) && ! (project instanceof ProductProject) ) || "일반".equals(taskTypeName) || "Try".equals(taskTypeName) || "COST".equals(task.getTaskType()) || ("Gate".equals(taskTypeName) && projectData.teamType.equals("전자 사업부") ) ) {
%>
<!-- space -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="space20"></td>
    </tr>
</table>
<!-- space -->
<!-- 산출물 시작 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
        <td class="font_03" width="120"><%=messageService.getString("e3ps.message.ket_message", "01718") %><%--산출물 관리--%></td>
        <td valign="bottom">
            <%if("Try".equals(task.getTaskType())){%>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_3.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg3.gif"><a href="#"
                        onclick="javascript:viewTry('<%=oid %>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_4.gif"></td>
                </tr>
            </table> <%} %> <%
                  boolean isCheckMoldProject = false;
                  if(project instanceof MoldProject){
                   if(ProjectHelper.getMoldPartManagerCount((MoldProject)project) > 0){
                     isCheckMoldProject = true;
                   }
                  }
  
                  if("금형부품".equals(task.getTaskName()) && isCheckMoldProject){ %>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_3.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg3.gif"><a href="#"
                        onclick="javascript:viewMoldPart('<%=projectOid %>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01068") %><%--금형부품관리--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_4.gif"></td>
                </tr>
            </table> <%} %>
        </td>
        <td align="right">
            <%
  if((project instanceof WorkProject && isTaskMember) || buttonAuth.isOuputDocAdd || isEdit || (isPmo && projectData.teamType.equals("자동차 사업부")) || (isPmo2 && projectData.teamType.equals("전자 사업부"))|| (isPmo2 && projectData.teamType.equals("KETS"))){
%>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>


                    <%

if(pjtLatestOid.equals(projectOid)) { %>
                    <%
    //산출물들이 각각 100%이면 일괄결재가 없어야함.
    if(taskData.taskCompletion!=100) {
%>
    <!-- 2015.01.14 일괄결재 버튼 안보이게 요청 
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                        href="javascript:allapprovalPage(document.forms[0].LID);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02334") %><%--일괄결재--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="5"></td>
     -->     
                    <%
    }
%>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:outputPage('', 'false');"
                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                <%
    }
%>
            </table> <%}else{ %> &nbsp; <%} %>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm2"></td>
    </tr>
</table>
<%
ProjectOutput output = null;
ProjectOutputData outputData = null;
PeopleData peopleData = null;
WTUser outputUser = null;
String deptName = "";
String outputChargerName = "";
String version = "";
String lastVersion = "";
String versionOid = "";
String lastVersionOid = "";

String state = "";
String stateCode = "";
String modifyDate = "";
String peopleOID = "";

boolean isOwner = false;
boolean isState = false;
Object[] opObj = null;
String opDtAttatchUrl = "";
String subjectCheck = "0";


QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
outputRstCnt = outputQr.size();
int heightInfo = 150;
if(outputRstCnt==0) heightInfo = 30;
else if(outputRstCnt==1) heightInfo = 50;
else if(outputRstCnt==2) heightInfo = 80;
else if(outputRstCnt==3) heightInfo = 110;
else if(outputRstCnt==4) heightInfo = 140;
%>
<div
    style="width:100%;height:<%=heightInfo%>px;overflow-x:hidden;overflow-y:auto;  border:0; border-style:solid; border-color:#EBEBEB; scrollbar-face-color:#ccc;">
    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="outputTable" style="table-layout: fixed;">
        <tr>
            <td class="tdblueM" width="4%"><input type="checkbox" name="allCheck"
                onclick="selectAll(document.forms[0].allCheck, document.forms[0].LID)" /></td>
            <td class="tdblueM" width="14%"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdblueM" width="5%"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
            <td class="tdblueM" width="4%"><%=messageService.getString("e3ps.message.ket_message", "03127") %><%--필수--%></td>
            <td class="tdblueM" width="11%"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
            <td class="tdblueM" width="7%"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdblueM" width="5%"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
            <td class="tdblueM" width="8%"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdblueM" width="9%"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdblueM" width="5%"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
            <td class="tdblueM" width="12%"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
            <td class="tdblueM" width="5%">Gate<%--Gate--%>
                </font></td>
            <td class="tdblueM" width="6%"><%=messageService.getString("e3ps.message.ket_message", "07129") %><%--템플릿--%>
                </font></td>
            <%
  if(isEdit || true) {
%>
            <td class="tdblueM0" width="5%"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
            <%
  }
%>
        </tr>
        <%
int checkIdx = 0;
  while ( outputQr.hasMoreElements() ) {
      checkIdx++;
    version = "";
    lastVersion = "";
    lastVersionOid = "";
    versionOid = "";
    state = "";
    stateCode = "";

    opObj = (Object[])outputQr.nextElement();
    output = (ProjectOutput)opObj[0];
    outputUser = output.getOwner() == null? null:(WTUser)output.getOwner().getPrincipal();
    outputData = new ProjectOutputData (output);
    subjectCheck = "0";
    if("대상".equals(output.getSubjectType())) {
        subjectCheck = "1";
    }
    
    String outputNameStrAll = "";
    String outputNameStr = StringUtil.checkNull(outputData.name);
    opDtAttatchUrl = "";
    KETStandardTemplate ketStTmpl = null;
    String outputDocoid = output.getOutputDocOid();
    if(outputDocoid != null && !"".equals(outputDocoid) && !"null".equals(outputDocoid)){
    	ketStTmpl = (KETStandardTemplate)CommonUtil.getObject(output.getOutputDocOid());
    }
    KETDqmRaise dqmRaiseObj = null;
    KETDqmHeader dqmHeaderObj = null;
     
    if (ketStTmpl instanceof FormatContentHolder) {
        FormatContentHolder holder = (FormatContentHolder) ketStTmpl;
        ContentInfo info2 = ContentUtil.getPrimaryContent(holder);
//      ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
        if(info2!=null && info2.getDownURL()!=null)  opDtAttatchUrl = info2.getDownURL().toString();
    }

    if(ketStTmpl!=null && "".equals(opDtAttatchUrl)) {
       QueryResult rs = ContentHelper.service.getContentsByRole((ContentHolder)ketStTmpl, ContentRoleType.SECONDARY);
        while (rs.hasMoreElements()) {
            ContentHolder holder = (ContentHolder)ketStTmpl;
            ContentItem fileContent = (ContentItem) rs.nextElement();
            ContentInfo info3 = ContentUtil.getContentInfo(holder, fileContent);
            if(info3!=null && info3.getDownURL()!=null)  opDtAttatchUrl = info3.getDownURL().toString();
        }
    }
    
    /* 
    ContentDTO conDto = null;
    ContentHolder holder2 = (ContentHolder) ketStTmpl;
    holder2 = ContentHelper.service.getContents(holder2);
    Vector<ContentItem> result = ContentHelper.getContentListAll(holder2);
    Iterator<ContentItem> iter = result.iterator();
    while (iter.hasNext()) {
        ContentItem item = iter.next();
        conDto = new ContentDTO(holder2, item);
        opDtAttatchUrl = conDto.getDownloadURL();
        break;
//      if (item.getRole() == ContentRoleType.SECONDARY) {
//      }
    }
     */
    
    String objType = "";
    if(output.getObjType() != null){
      objType = output.getObjType();
    }

    outputChargerName = "&nbsp;";
    deptName = "&nbsp;";
    version = "&nbsp;";
    lastVersion = "&nbsp;";
    state = "&nbsp;";
    stateCode =  "&nbsp;";
    modifyDate = "&nbsp;";
    peopleOID = "&nbsp;";
    peopleData = null;
    String fileUrl = "";
    String icon = "";
    ContentInfo info = null;
    ContentItem item = null;

    String loginOid = "";
    String security = "";
    Boolean isSecu = false;
    
    if("COST".equals(objType) || "SALES".equals(objType)){
	   objType= "ETC";
    }
    
    boolean isEtc = "ETC".equals(objType);
    
    
    if(outputUser != null) {
      /*
      outputChargerName = outputUser.getFullName();
      peopleData = new PeopleData(outputUser);
      deptName = peopleData.departmentName;
      peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
      */
    }

    WTUser duser = null;
    if(!isEtc && outputData.isRegistry) {
        ObjectData data = null;
        WTUser sessionUser  = (WTUser) SessionHelper.manager.getPrincipal();
        
// 산출물 열람 권한 추가 시작 #####################################
        if(CommonUtil.isMember("자동차PMO", sessionUser)) {
          isSecu = true;
        }else if(CommonUtil.isMember("자동차사업부_임원", sessionUser)) {
          isSecu = true;
        }else if(CommonUtil.isMember("전자사업부_임원", sessionUser)) {
          isSecu = true;
        }

// 산출물 열람 권한 추가 종료 ########################################

        if("ECO".equals(objType)) {
            KETProdChangeOrder prodChangeOrderObj = null;
            KETMoldChangeOrder moldChangeOrderObj = null;
            QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);
        
            while (qr.hasMoreElements()) {
                prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
            }
        
            qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);
        
            while (qr.hasMoreElements()) {
                moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
            }
            
            if(prodChangeOrderObj!=null) {
                versionOid = CommonUtil.getOIDString(outputData.changeOrder);
                lastVersionOid = CommonUtil.getOIDString(prodChangeOrderObj);
                outputNameStr = prodChangeOrderObj.getEcoName();
            }else if(moldChangeOrderObj!=null) {
                versionOid = CommonUtil.getOIDString(outputData.changeOrder);
                lastVersionOid = CommonUtil.getOIDString(moldChangeOrderObj);
                outputNameStr = moldChangeOrderObj.getEcoName();
            }
            state = outputData.changeOrder.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
            stateCode = outputData.changeOrder.getLifeCycleState().toString();
            modifyDate = DateUtil.getDateString(outputData.changeOrder.getPersistInfo().getModifyStamp(), "D");
            modifyDate = modifyDate.substring(2, 10);
            
            data = null;
            outputChargerName = outputData.changeOrder.getCreatorFullName();
            duser = (WTUser)outputData.changeOrder.getCreator().getPrincipal();
            
            loginOid = CommonUtil.getOIDString(sessionUser);
            if(loginOid.equals(outputData.changeOrder.getCreator().toString())){
              isSecu = true;
            }
            if(isSecu==false){
              isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.changeOrder,sessionUser.getName());
            }
            //out.println(isSecu);
        }else if("TRY".equals(objType)) {
            
            KETTryMold moldTryConditionObj = null;
            KETTryPress pressTryConditionObj = null;
            
            QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);
        
            while (qr.hasMoreElements()) {
                moldTryConditionObj = (KETTryMold) qr.nextElement();
            }
        
            qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);
            
            while (qr.hasMoreElements()) {
                pressTryConditionObj = (KETTryPress) qr.nextElement();
            }
            
             outputNameStr = outputData.tryCondition.getName();
            if(moldTryConditionObj!=null) {
                versionOid = CommonUtil.getOIDString(outputData.tryCondition);
                lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
                lastVersion = moldTryConditionObj.getVersionDisplayIdentifier().toString();
            }else if(pressTryConditionObj!=null) {
                versionOid = CommonUtil.getOIDString(outputData.tryCondition);
                lastVersionOid = CommonUtil.getOIDString(pressTryConditionObj);
                lastVersion = pressTryConditionObj.getVersionDisplayIdentifier().toString();
            }
            state = outputData.tryCondition.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
            stateCode = outputData.tryCondition.getLifeCycleState().toString();
            modifyDate = DateUtil.getDateString(outputData.tryCondition.getPersistInfo().getModifyStamp(), "D");
            modifyDate = modifyDate.substring(2, 10);
            
            data = null;
            outputChargerName = outputData.tryCondition.getCreatorFullName();
            duser = (WTUser)outputData.tryCondition.getCreator().getPrincipal();
            
            loginOid = CommonUtil.getOIDString(sessionUser);
            if(loginOid.equals(outputData.tryCondition.getCreator().toString())){
              isSecu = true;
            }
            if(isSecu==false){
              isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.tryCondition,sessionUser.getName());
            }
            
            if( "양산검증단계".equals(atftTaskName) && "금형Try_[양산검증_01]".equals(task.getTaskName()) ){
        	   isTaskCompleteTryYn = (moldTryConditionObj != null || pressTryConditionObj != null) ? "OK" : "NO";
        	   
            }
            %>
            <script type="text/javascript">
            isTaskCompleteTryYn = "<%=isTaskCompleteTryYn%>";
            </script>
            <%
            //out.println(isSecu);
        }else if("QLP".equals(objType)) {
              
        
            ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service.getOutputQLP(output);
            String raiseOidStr =  dqmDto.getRaiseOid();
            dqmRaiseObj = (raiseOidStr==null || "".equals(raiseOidStr))?null:(KETDqmRaise)CommonUtil.getObject(raiseOidStr);
            dqmHeaderObj = (dqmDto==null || "".equals(dqmDto.getOid()))?null:(KETDqmHeader)CommonUtil.getObject(dqmDto.getOid());
            
            if(dqmDto!=null) {
                versionOid = CommonUtil.getOIDString(dqmRaiseObj);
                lastVersionOid = CommonUtil.getOIDString(dqmRaiseObj);
                lastVersion = "";
            }
            
            outputNameStr = dqmHeaderObj.getProblem();
//             state = dqmRaiseObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
//             stateCode = dqmRaiseObj.getLifeCycleState().toString();
            state = dqmHeaderObj.getDqmStateName();
            stateCode = dqmHeaderObj.getDqmStateCode();
            modifyDate = DateUtil.getDateString(dqmRaiseObj.getPersistInfo().getModifyStamp(), "D");
            modifyDate = modifyDate.substring(2, 10);
            
            data = null;
            outputChargerName = dqmRaiseObj.getCreatorFullName();
            duser = (WTUser)dqmRaiseObj.getCreator().getPrincipal();
            
            loginOid = CommonUtil.getOIDString(sessionUser);
            if(loginOid.equals(dqmRaiseObj.getCreator().toString())){
              isSecu = true;
            }
            if(isSecu==false){
              isSecu = WorkflowSearchHelper.manager.userInApproval(dqmRaiseObj, sessionUser.getName());
            }
           
            
        }else if("ETC".equals(objType)) {  //ETC인 경우
            duser = (WTUser)output.getOwner().getPrincipal();
        }else {    //문서, 도면인 경우
           //out.println(outputData.LastDocument);
            if(outputData.LastDocument instanceof KETProjectDocument)
            {
                outputNameStr =  StringUtil.checkNull(((KETProjectDocument)outputData.LastDocument).getTitle() );
            }else if(outputData.LastDocument instanceof EPMDocument) {
                outputNameStr =  StringUtil.checkNull(((EPMDocument)outputData.LastDocument).getName() );
            }

            version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
            versionOid = CommonUtil.getOIDString(outputData.currentDocument);
            lastVersion = outputData.LastDocument.getVersionDisplayIdentifier().toString();
            lastVersionOid = CommonUtil.getOIDString(outputData.LastDocument);
            state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
            stateCode = outputData.currentDocument.getLifeCycleState().toString();
            modifyDate = DateUtil.getDateString(outputData.currentDocument.getPersistInfo().getModifyStamp(), "D");
            modifyDate = modifyDate.substring(2, 10);
            
            data = new ObjectData(outputData.LastDocument); /* 임영근g 최신것으로 바꿔주길 요청 */
            outputChargerName = outputData.currentDocument.getCreatorFullName();
            
            //duser = (WTUser)outputData.currentDocument.getCreator().getPrincipal(); /* 최종작성자로 변경*/
            duser = (WTUser)outputData.LastDocument.getCreator().getPrincipal();
            
            loginOid = CommonUtil.getOIDString(sessionUser);
            if(loginOid.equals(outputData.LastDocument.getIterationInfo().getCreator().toString())){
              isSecu = true;
            }
            if(isSecu==false){
              isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.LastDocument,sessionUser.getName());
            }
        
            //out.println(outputData.LastDocument);
            if(outputData.LastDocument instanceof KETProjectDocument)
            {
            security =  StringUtil.checkNull(((KETProjectDocument)outputData.LastDocument).getSecurity());
              if(!security.equals("S2")){
                isSecu = true;
              }
            }
            //out.println(isSecu);
        }
        
        //out.println(duser);
        if(duser != null){
          //peopleData = new PeopleData(outputUser);
          peopleData = new PeopleData(duser);
          deptName = peopleData.departmentName;
          //out.println(peopleData.name);
          peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
        }

        if(isSecu==false){
          isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject)outputData.project);
        }
         if(isSecu==false){
           isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject)outputData.project);
         }
    
        if(CommonUtil.isAdmin())
        {
          isSecu = true;
        }
    
        if(project.getPjtType() == 3)
        {
          isSecu = true;
        }
    
        if(data!=null) {
            fileUrl = data.getFileUrl();
        
            icon = data.getIcon();
            if(fileUrl == null && data.getSecondary() != null){
                for(int i = 0; i < data.getSecondary().length; i++){
                  Object oo[] = (Object[])data.getSecondary()[i];
                    if(oo == null){
                      continue;
                    }else{
                       fileUrl = (String)oo[0];
                      break;
                    }
                }
            }
        }
    
        FormatContentHolder holder = null;
        if("ECO".equals(objType) || "TRY".equals(objType) || "QLP".equals(objType)) {

        }else {
            holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) outputData.LastDocument);
            item = holder.getPrimary();
            if(holder != null){
                info = ContentUtil.getContentInfo(holder , item);
                if(item != null){
                    icon = info.getIconURLStr();
                    icon = icon.substring(icon.indexOf("<img"));
                }
            }
        }

    
        if(fileUrl == null){
          fileUrl = "";
          icon = "&nbsp;";
        }

    }
    if("ETC".equals(objType)) {  //ETC인 경우
        duser = (WTUser)output.getOwner().getPrincipal();
        //out.println(duser);
        if(duser != null){
          //peopleData = new PeopleData(outputUser);
          peopleData = new PeopleData(duser);
          deptName = peopleData.departmentName;
          //out.println(peopleData.name);
          peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
        }
        /*modifyDate = DateUtil.getDateString(output.getPersistInfo().getCreateStamp(), "D");
        ProjectOutput oldOutPut = (ProjectOutput)CommonUtil.getObject(output.getOldOutputOid());
        boolean isOldOutPut = true;
        while(isOldOutPut){
            if(oldOutPut != null && oldOutPut.getEtcCompletion() != null){
        	   modifyDate = DateUtil.getDateString(oldOutPut.getPersistInfo().getCreateStamp(), "D");
        	   oldOutPut = (ProjectOutput)CommonUtil.getObject(oldOutPut.getOldOutputOid());
            }else{
        	   isOldOutPut = false;
            }
        } */
        if(output.getEtcCompletion() != null){
            modifyDate = DateUtil.getDateString(output.getEtcCompletion(), "D");    
        }else{
            modifyDate = DateUtil.getDateString(output.getPersistInfo().getModifyStamp(), "D");
        }
        
        modifyDate = modifyDate.substring(2, 10);
    }
    //out.println(fileUrl);
    isOwner = false;
    if(outputUser != null && (wtuser.getName()).equals(outputUser.getName())) {
      isOwner = true;
    }

    KETProjectDocument docu = null;
    EPMDocument epm = null;
    FormatContentHolder cholder = null;
    ContentInfo cinfo = null;

    boolean isMoldProject = false;
    if(project instanceof MoldProject) {
      isMoldProject = true;
    }
    
    outputNameStrAll = outputNameStr;
    //outputNameStr = e3ps.common.util.StringUtil.cutStr(outputNameStr, 15);

%>

        <tr>
            <input type="hidden" name="checkState" value="<%=stateCode %>" />
            <%--문서제목--%>
            <%
    if(outputData.document != null) {
        if( "승인완료".equals(state)) { %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" disabled /></td>
            <%      }else if(objType.equals("ETC")){  %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" disabled /></td>
            <%      }else{  %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" value="<%=lastVersionOid%>" /></td>
            <%      }
        
        if(isMoldProject) {
        
           %>
            <td class="tdwhiteL" title="<%=outputNameStrAll%>" style="text-valign: middle">
                <!--                     <div style="width:100%;height:27;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"> -->
                <nobr>
                    <% 
                        if(outputData.document!=null) {
                            if (lastVersionOid != null){
                    %>
                    <a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><div class="ellipsis" style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div></a>
                    <%      }else { %>
                    <a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(outputData.document)%>')"><div
                            class="ellipsis" style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div></a>
                    <%      
                            }
                        }else if(objType.equals("ETC")){  %>
                    <a href="#" onClick="javascript:openCompleteReson('<%=outputData.oid%>');"><div class="ellipsis"
                            style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div> </a>
                    <%  }else { %>
                    <div class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div>
                    <%      } 
                    %>
                </nobr> <!--                     </div> -->
            </td>
            <%
         }else{

             if(lastVersionOid != null) {
               if(outputData.objType == "문서") {
                   docu = (KETProjectDocument)CommonUtil.getObject(lastVersionOid);
                   cholder = (FormatContentHolder)docu;
                   cinfo = ContentUtil.getPrimaryContent(cholder);
               }else if(outputData.objType == "도면") {
                   epm = (EPMDocument)CommonUtil.getObject(lastVersionOid);
                   cholder = (FormatContentHolder)epm;
                   cinfo = ContentUtil.getPrimaryContent(cholder);
               }
       %>
            <!--        cinfo != null ? cinfo.getName() : outputData.name -->
            <td class="tdwhiteL" title="<%=outputNameStrAll%>" style="text-valign: middle"><nobr>
                    <% 
                if(outputData.document!=null) {
                    if (lastVersionOid != null){
            %>
                    <a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><div class="ellipsis" style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div></a>
                    <%      }else { %>
                    <a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(outputData.document)%>')"><div
                            class="ellipsis" style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div></a>
                    <%      
                    }
                }else if(objType.equals("ETC")){  
                
                    String completeReson =  output.getComplete_reason();
                    if(completeReson == null){
                      completeReson = "";
                    }
                    completeReson = completeReson.trim();
                %>
                    <a href="#" onClick="javascript:openCompleteReson('<%=outputData.oid%>');"><div class="ellipsis"
                            style="width: 100%;">
                            <nobr><%=outputNameStr%></nobr>
                        </div> </a>
                    <%if("ECO_No.기입".equals(outputData.name)){
                      String isProduct = "";
                      if(project instanceof ProductProject){
                        isProduct = "P";
                      }
                      String ecoOid = EcmSearchHelper.manager.getEcoObjectOid(completeReson);
                  %>
                    <%if(ecoOid != null && ecoOid.length() > 0){ %>
                    <%if("제품도".equals(taskData.taskName)){ %>
                    <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', 'P');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]
                    </a>
                    <%}else{ %>
                    <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', '');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]
                    </a>
                    <%} %>
                    <%} %>
                    <%} %>


                    <%  }else { %>
                    <div class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div>
                    <%  } 
            %>
                </nobr> <!--                     </div> --></td>
            <%    }
         }
    }else{%>
            <%if( "승인완료".equals(state)) { %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" disabled /></td>
            <%}else if(objType.equals("ETC")) { %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" disabled /></td>
            <%}else{%>
            <%                      if(lastVersionOid!=null){  %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" value="<%=lastVersionOid%>" /></td>
            <%                      }else {  %>
            <td class="tdwhiteL" style="text-valign: middle"><input type="checkbox" name="LID" value="" /></td>
            <%                      }  %>
            <%} %>

            <td class="tdwhiteL" title="<%=outputNameStr%>" style="text-valign: middle" align="center">
                <% 
                        if(outputData.document!=null) {
                            if (lastVersionOid != null){
                    %> <a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><div class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div></a> <%      }else { %> <a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(outputData.document)%>')"><div
                        class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div></a> <%      
                            }
                        }else if(objType.equals("ETC")){  %> <a href="#" onClick="javascript:openCompleteReson('<%=outputData.oid%>');"><div
                        class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div> </a> <%      
                        }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETProdChangeOrder){  %>
                <a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'PROD');"><div class="ellipsis"
                        style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div> </a> <%      
                        }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETMoldChangeOrder){  %>
                <a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'MOLD');"><div class="ellipsis"
                        style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div> </a> <%      
                        }else if(objType.equals("TRY") && outputData.tryCondition!=null && outputData.tryCondition  instanceof KETTryMold) {  %>
                <a href="#" onClick="javascript:openView('<%=outputData.tryCondition%>');"><div class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div> </a> <%      
                        }else if(objType.equals("TRY") && outputData.tryCondition!=null && outputData.tryCondition  instanceof KETTryPress) {  %>
                <a href="#" onClick="javascript:openView('<%=outputData.tryCondition%>');"><div class="ellipsis" style="width: 100%;">
                        <nobr><%=outputNameStr%></nobr>
                    </div> </a> <%      
                        }else if(objType.equals("QLP")) {
                            if(dqmHeaderObj==null) {  %>
                <div class="ellipsis" style="width: 100%;">
                    <nobr><%=outputNameStr%></nobr>
                </div> <%      }else {%> <a href="#" onClick="javascript:openDQM('<%=CommonUtil.getOIDString(dqmHeaderObj)%>');"><div
                        class="ellipsis" style="width: 100%;">
                        <nobr><%=dqmHeaderObj.getProblem() %></nobr>
                    </div> </a> <%      }
                        }else { %>
                <div class="ellipsis" style="width: 100%;">
                    <nobr><%=outputNameStr%></nobr>
                </div> <%      } 
                    %>
            </td>

            <%} %>
            <%--타입--%>
            <td class="tdwhiteL" title="<%=outputData.objType%>" style="text-align: center">
                <% String objTypeStr = StringUtil.checkNull(outputData.objType);
                  if("QLP".equals(objTypeStr)) objTypeStr = "품질";
                  if(objTypeStr.length()>3) objTypeStr = objTypeStr.substring(0, 3)+"..";
                  %> <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=objTypeStr%></a>
            </td>
            <%--필수--%>
            <td class="tdwhiteL" title="<%=outputData.isPrimary%>" style="text-align: center">
                <%   if(StringUtil.isEmpty(outputData.isPrimary)) { %> - <%   }else { %> <nobr><%=outputData.isPrimary%></nobr> <%   } %>
            </td>
            <%--문서분류--%>
            <%if(outputData.objType.equals("문서")){ 
                      String outputLocationStr = outputData.location;
                      if(!StringUtil.isEmpty(outputLocationStr)) outputLocationStr = outputLocationStr.substring(outputLocationStr.lastIndexOf("/"));
                      
                      if(outputLocationStr!=null && outputLocationStr.length()>10) {
                       outputLocationStr = outputLocationStr.substring(0, 10) + "..";
                      }

                  %>
            <td class="tdwhiteL" title="<%=(outputData.location==null)?"":outputData.location%>">
                <div style="width: 90%; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                    <nobr><%=(outputData.location==null)?"-":outputLocationStr%></nobr>
                </div>
            </td>
            <%}else{ %>
            <td class="tdwhiteL" title="">
                <%if(outputData.document != null && project.getPjtType() == 3){%><a href="#"
                onClick="javascript:doViewEpm('<%=project.getPjtNo()%>')">[<%=messageService.getString("e3ps.message.ket_message", "00926") %><%--관련도면--%>]
            </a> <%}else {%>-<%}%> &nbsp;
            </td>
            <%} %>

            <%--최종작성자--%>
            <td class="tdwhitel" title="<%=deptName%>" style="text-align: center">
                <%
    if(peopleData == null) {
%> - <%
    } else {
%> <a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=peopleData.name%></a> <%
    }
%>
            </td>

            <%--버전, 상태, 작성일, 파일--%>
            <%
        if (outputData.isRegistry ) {

            if(objType.equals("ECO") || objType.equals("QLP")){
%>
            <td class="tdwhiteL" style="text-align: center">
                <%   if(StringUtil.isEmpty(lastVersion)) { %> - <%   }else { %> <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font
                    color="blue"><%=lastVersion%></font></a> <%   } %>
            </td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(state))?"-":state%></td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
            <td class="tdwhiteM" style="text-align: center">
                <%   if(StringUtil.isEmpty(icon)) { %> - <%   }else { %> <a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a> <%   } %>
            </td>
            <% 

            }else if(objType.equals("TRY")){
                 KETTryCondition tryCondition = (KETTryCondition)CommonUtil.getObject(lastVersionOid);    
            
%>
            <td class="tdwhiteL" style="text-align: center">
                <%   if(StringUtil.isEmpty(lastVersion)) { %> - <%   }else { %> <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font
                    color="blue"><%=lastVersion%></font></a> <%   } %>

            </td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(state))?"-":state%></td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
            <td class="tdwhiteL" style="text-align: center">
                <%   if(StringUtil.isEmpty(icon)) { %> - <%   }else { %> <a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a> <%   } %>
            </td>
            <% 
            
            }else if(!isEtc){
    
                Iterated pre = VersionControlHelper.service.predecessorOf(outputData.LastDocument);
                String preVer = null;
        
                String preOid = null;
        
                if(preOid == null || preOid.length() == 0){
                  preOid = versionOid;
                }
        
                if(preVer == null || preVer.length() == 0){
                  preVer = version;
                }
                if(pre instanceof RevisionControlled){
                  RevisionControlled preRc = (RevisionControlled)pre;
                  preVer = preRc.getVersionIdentifier().getValue();
                  preOid = preRc.getPersistInfo().getObjectIdentifier().getStringValue();
                }
%>
            <td class="tdwhiteL" style="text-align: center">
                <!-- 최신버전만 보여지도록 이전버전 주석처리 --> <!-- <a href="#" onClick="javascript:ViewDoc('<--%=preOid%>')"><font color="blue" ><--%=preVer%><--%//=version%></font></a>/ -->
                <%   if(StringUtil.isEmpty(lastVersion)) { %> - <%   }else { %> <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font
                    color="blue"><%=lastVersion%></font></a>
            </td>
            <%   } %>

            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(state))?"-":state%></td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
            <td class="tdwhiteL" style="text-align: center">
                <%   if(StringUtil.isEmpty(icon)) { %> - <%   }else { %> <a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a> <%   } %>
            </td>
            <%
            }else if(isEtc){
%>


            <td class="tdwhiteL" style="text-align: center">-</td>
            <td class="tdwhiteL" style="text-align: center">
                <%
if(output.getCompletion()==100) {
%> <%=messageService.getString("e3ps.message.ket_message", "02171") %><%-- 완료 --%> <%
}else {
%> - <%
}
%>
            </td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
            <td class="tdwhiteL" style="text-align: center">-</td>
            <%
            }else{
%>


            <td class="tdwhiteL" style="text-align: center">-</td>
            <td class="tdwhiteL" style="text-align: center">-</td>
            <td class="tdwhiteL" style="text-align: center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
            <td class="tdwhiteL" style="text-align: center">-</td>

            <%
            }
        }
        else {



          int docCreateOrLinkType = buttonAuth.isOutputDocCreateOrLink(output);
          if( docCreateOrLinkType == TaskViewButtonAuth.CREATEORLINK || CommonUtil.isBizAdmin() ) {
    
          if(objType.equals("DOC")){
%>
            <td class="tdwhiteL" colspan="4" style="text-align: center"><span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
            </td>
            <%}else if(objType.equals("ECO")){%>
            <td class="tdwhiteL" colspan="4" style="text-align: center"><span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
            </td>
            <%}else if(objType.equals("QLP")){%>
            <td class="tdwhiteL" colspan="4" style="text-align: center"><span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
            </td>
            <%}else if(objType.equals("ETC")){%>
            <td class="tdwhiteL" colspan="4" style="text-align: center"><span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01172") %><%--내역입력--%></a></span>
            </td>

            <%}else if(isElectron && objType.equals("DWG") && !( pjtType.equals("0")|| pjtType.equals("1") ) ){%>
            <td class="tdwhiteL" colspan="4" style="text-align: center">
                <%-- <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'true');"> <%=messageService.getString("e3ps.message.ket_message", "00997") %>금형</a></span>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'false');"> <%=messageService.getString("e3ps.message.ket_message", "02536") %>제품</a></span> --%>
                <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%></a></span>
            </td>

            <%}else if(StringUtil.isEmpty(objType)){ %>
            <td class="tdwhiteL" colspan="4" style="text-align: center">&nbsp;</td>
            <%}else{ %>
            <td class="tdwhiteL" colspan="4" style="text-align: center">
                <%if("제품도출도".equals(taskData.taskName)){
                  %> <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'false');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <%}else if("TRY".equals(objType)){ //금형Try조건표 직접작성 %> <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputTryCreate('<%=outputData.oid%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <%}else{ %> <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                <%} %> <%if(!"TRY".equals(objType)) {%> <span class="b-small2" style="vertical-align: middle"><a
                    href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
                <%}%>
            </td>
            <%} %>
            <%
      }
      else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_CREATE){
%>
            <td class="tdwhiteL" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></td>
            <%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_TASK_MEMBER){
%>
            <td class="tdwhiteL" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01210") %><%--담당자 지정안됨--%></font></td>
            <%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_PROGRESS){
%>
            <td class="tdwhiteL" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01463") %><%--미진행상태--%></font></td>
            <%
      }else{
%>
            <td class="tdwhiteL" colspan="4"><font color="red">-</font></td>
            <%       }
    }
%>

            <%--진행률--%>
            <td class="tdwhiteL" style="text-align: center">
                <%

    int outputCompletionAuthType = buttonAuth.isOutputCompletion(output);
    if(outputCompletionAuthType == TaskViewButtonAuth.ACTIONCOMPLECTION) {
%> <input type="hidden" name="progresskey" value="<%=outputData.oid%>" /> <input name="outputCompletion" class="txt_field"
                style="width: 30%; text-align: right;" size="3" maxlength="3" value="<%=output.getCompletion()%>"
                progresskey="<%=outputData.oid%>" onkeyup="SetCompNum(<%=(checkIdx-1)%>)" /><b>%</b>&nbsp; <span class="b-small2"
                style="vertical-align: middle"><a href="javascript:outputProgress('<%=outputData.oid%>' , '<%=objType %>');"> <%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%></a></span>
                <%
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOT_PROGRESS){
      out.println(output.getCompletion() + "%");
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTACTIONCOMPLECTION){

      out.println(output.getCompletion() + "%");
    }

    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTEXISTCOMPLETION){

      out.println("-");
      //out.println("comp:"+output.getCompletion()+",isChild:"+buttonAuth.isChild + ", pri: "+output.isIsPrimary());
      
    }
%>
            </td>
            <%--Gate--%>
            <td class="tdwhiteL" style="text-align: center">
                <%
    //if( buttonAuth.isOuputDocAdd || isAdmin || isEdit|| isPmo || isPmo2 || buttonAuth.isPM || isTaskMaster || isTaskMember){
    if( isAdmin || (isPmo && projectData.teamType.equals("자동차 사업부")) || (isPmo2 && projectData.teamType.equals("전자 사업부"))|| (isPmo3 && projectData.teamType.equals("KETS")) || buttonAuth.isPM){
%> <input type="checkbox" name="gatechecks" value="<%=subjectCheck%>" <%if("1".equals(subjectCheck)) out.println("checked");%>
                onClick="updateGateTarget(this, '<%=CommonUtil.getOIDString(output) %>')" /> <%
    }else {
%> <input type="checkbox" name="gatechecks" disabled="true" value="<%=subjectCheck%>"
                <%if("1".equals(subjectCheck)) out.println("checked");%> /> <%
    }
%>
            </td>
            <td class="tdwhiteL" style="text-align: center">
                <%
    if(opDtAttatchUrl!=null && !"".equals(opDtAttatchUrl)) {
%> <a href="javascript:PLM_FILE_DOWNLOAD2('<%=opDtAttatchUrl%>');">Down</a> <%
    }else {
%> - <%
    }
%>

            </td>
            <%
    if( (task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE && buttonAuth.isDeleteOutput(output)) && buttonAuth.isOuputDocAdd || isAdmin || isEdit|| (project instanceof WorkProject && isTaskMember) || isPmo || isPmo2 || isPmo3 || buttonAuth.isPM ){ //buttonAuth.isDeleteOutput(output)) {
%>
            <td class="tdwhiteL" style="text-align: center"><a
                href="JavaScript:deleteOutput('<%=outputData.oid%>', '<%=outputData.isPrimary%>')"><p>
                        <img src="/plm/portal/images/icon_delete.gif" width="13" height="12" border="0">
                    </p></a></td>
            <%
    }else{%>
            <td class="tdwhiteL" style="text-align: center">-</td>
            <%}%>


        </tr>
        <%
  }
%>
    </table>
    <!-- 산출물 끝 -->
    <%
  }
%>
    <input type="hidden" name="outputCnt" value="<%=outputRstCnt%>" />
    <input type="hidden" name="mainScheduleVal" value="<%=task.getMainScheduleCode()%>" />
        <!-- download target iframe -->
        <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>

</div>
<!-- ####################  산출물 관리 end  ##################################-->

<script type="text/javascript">
$(document).ready(function(){
     CalendarUtil.dateInputFormat('taskExecStartDate');
     
     $("#mainScheduleCode").multiselect({
         minWidth: 120,
         multiple: false,
         header: false,
         noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
         checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
         uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
         selectedList: 1
     });
     
     /* $("#mainScheduleCode").change(function(){
    	 
         if(!confirm("sss")){
        	 //e.preventDefault();
        	 $('#mainScheduleCode option').attr('selected', false);
        	 return;
         }
     }); */
     
     $('#mainScheduleCode').change(function() {
    	 var selected = $(this).val();
         var current = $('input[name=mainScheduleVal]').val();

         if($('input[name=mainScheduleVal]').val() != selected){
             if (!confirm('주요일정코드를 변경하시겠습니까?')) {
                 //$("#mainScheduleCode").val(selected).attr("selected", "selected");
                 $("#mainScheduleCode").val(current);
                 $("#mainScheduleCode").multiselect('refresh');
                 return false;
             }else{
             

                 var msg = '';

                 $.ajax({
                     type: 'post',
                     url : '/plm/ext/project/product/syncMainSchedule.do?oid='+'<%=oid%>'+'&code='+selected,
                     async : false,
                     cache:false,
                     success : function(result){
                         if(result.msg != ''){
                             msg = result.msg;
                         }
                     }
                 });

                 
                 if(msg != ''){
                     alert(msg);
                 }else{
                     alert("주요일정코드가 변경되었습니다.");
                 }
             }
         }
             
         $('input[name=mainScheduleVal]').val(selected);
     

     });
     
<% if(!isDate && (isAdmin|| isPmo || isPmo2 || isPmo3 || isTaskMaster || isTaskMember)){
     if(taskData.taskCompletion==100 && task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE ) {
%>
     $('[name=taskExecStartDate]').datepicker("destroy");
<%   }
 }else{
 %>
    // $('[name=taskExecStartDate]').datepicker("destroy");
<%}%>
});
</script>
<%}catch(Exception e){
    e.printStackTrace();
    Kogger.error(e);
}

%>
