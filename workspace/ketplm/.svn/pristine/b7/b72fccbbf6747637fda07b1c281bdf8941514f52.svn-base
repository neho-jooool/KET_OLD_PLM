package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pom.PersistenceException;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.KeywordExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
//import e3ps.doc.DocCodeType;
//import e3ps.doc.JELDocument;
//import e3ps.doc.beans.DocCodeTypeHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskDependencyLink;
import e3ps.project.outputtype.ProjectOutPutType;
import ext.ket.shared.log.Kogger;

public class ProjectMigrationHelper implements RemoteAccess {
	
	public static final ProjectMigrationHelper manager = new ProjectMigrationHelper();
	private ProjectMigrationHelper() {}
	
	public static E3PSProject createJELProject(Hashtable hash) {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"createJELProject",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (E3PSProject) obj;
		}
			
		String projectType = (String) hash.get("projectType");							//프로젝트 타입 [1: 수주/견적, 2:개발]
		String projectNo = (String) hash.get("projectNo");								//프로젝트 번호
		String projectName = (String) hash.get("projectName");							//프로젝트 이름
		String planStartDate = (String) hash.get("planStartDate");						//계획 시작일자
		String planEndDate = (String) hash.get("planEndDate");							//계획 종료일자
		String execStartDate =  (String) hash.get("execStartDate");						//실제 시작일자
		String execEndDate = (String) hash.get("execEndDate");							//실제 종료일자
		String projectDesc = (String) hash.get("projectDesc");							//프로젝트 설명
		ProjectOutPutType outputType = (ProjectOutPutType) hash.get("outputType");		//산출물인증타입
		String pmId = (String) hash.get("pmId");										//PM ID
		String regDate = (String) hash.get("regDate");									//등록일자
		String modifyDate = (String) hash.get("modifyDate");							//수정일자
	   
		String projectDivisionCode = (String) hash.get("divisioncode");					//JELProjectDivisionCodeLink
		String projectLevelCode = (String) hash.get("levelcode");						//JELProjectLevelCodeLink
		String projectProductCode = (String) hash.get("productcode");					//JELProjectProductCodeLink
		String projectCustomerCode = (String) hash.get("customercode");					//JELProjectCustomerCodeLink
		String projectDevcompanyCode = (String) hash.get("devcompanycode");				//JELProjectDevCompanyCodeLink
		String projectMakecompanyCode = (String) hash.get("makecompanycode");			//JELProjectMakeCompanyCodeLink
		String projectModelCode = (String) hash.get("modelcode");						//JELProjectModelCodeLink
		
		Calendar tempCal = Calendar.getInstance();
		E3PSProject project = null;
	   
		Transaction transaction = new Transaction();
		
		try {
			transaction.start();
			
			/*
			 * #############################################################################################################
			 * 1. ExtendScheduleData Object Create START
			 * #############################################################################################################
			 */
			
			//1. ExtendScheduleData Object Create
			ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
			
			//1.1 Duration Setting
			if(StringUtil.checkString(planStartDate.trim()) && StringUtil.checkString(planEndDate.trim())) {
				int tempDuration = DateUtil.getDaysFromTo(planEndDate, planStartDate);
				schedule.setDuration(tempDuration);
   		   	}
			
			//1.2 ScheduleHistory (0: 최초생성)
			schedule.setScheduleHistory(0);
			
			//1.3 계획 시작일자 & 실제 시작일자 Setting
			//1.3.1 계획 시작일자 [PLM Attribute]
			tempCal.setTime(DateUtil.parseDateStr(planStartDate));
			schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			//1.3.2 실제 시작일자 [PLM Attribute]
			tempCal.setTime(DateUtil.parseDateStr(execStartDate));
			schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			
			//1.4 계획 종료일자 & 실제 종료일자
			//1.4.1 계획 종료일자 [PLM Attribute]
			tempCal.setTime(DateUtil.parseDateStr(planEndDate));
			schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			//1.4.2 실제 종료일자 [PLM Attribute]
			tempCal.setTime(DateUtil.parseDateStr(execEndDate));
			schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			
			//1.5 실 공수
			schedule.setExecWork(0);
			
			//1.6 ExtendScheduleData Object Save
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
			
			/*
			 * #############################################################################################################
			 * 1. ExtendScheduleData Object Create END
			 * #############################################################################################################
			 */
			
			/*
			 * #############################################################################################################
			 * 2. JELProject Object Create START
			 * #############################################################################################################
			 */
			
			//2. JELProject Object Create
//			project = JELProject.newJELProject();
//			
//			FolderHelper.assignLocation((FolderEntry)project,  FolderHelper.service.getFolder("/Default/Project/project",WCUtil.getWTContainerRef()));
//			LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate("LC_Project", WCUtil.getWTContainerRef()));
//				
//			//2.1 프로젝트 NO Setting
//			project.setPjtNo(projectNo.trim());
//			
//			//2.2 프로젝트 순서 Setting
//			project.setPjtSeq(0);
//			
//			//2.3 프로젝트 이력 (0: 최초생성)
//			project.setPjtHistory(0);
//       		   
//   		   	//2.4 프로젝트 진행률
//			project.setPjtCompletion(100);
//       		   
//			//2.5 최신 프로젝트 Check
//			project.setLastest(true); //List 에 보일경우 (True)
//       		   
//			//2.6 프로젝트 일정
//			project.setPjtSchedule(ObjectReference.newObjectReference(schedule));
			
			//2.7 템플릿
//			TemplateProject templateProject = null;
//			if ( tempid != null && tempid.length() > 0 ) {
//				//2.7.1 템플릿 연결
//				templateProject = (TemplateProject)CommonUtil.getObject(tempid);
//				project.setTemplateCode(templateProject.getPjtNo());
//				//TaskHelper.manager.copyTaskInfo(jelProject ,templateProject);
//				//2.7.2 산출물 인증 타입
//				Kogger.debug(getClass(), "########### templateProject.getOutputType() = " + templateProject.getOutputType().getName());
//				project.setOutputType(outputType);
//	   	   	}
			
			//2.8 프로젝트 명
//			if(StringUtil.checkString(projectName.trim())) {
//				project.setPjtName(StringUtil.checkNull(projectName.trim()));
//			}
//       		   
//   		   	//2.9 프로젝트 설명
//   		   	if(StringUtil.checkString(projectDesc.trim())) {
//   		   		project.setPjtDesc(projectDesc.trim());	
//   		   	}
//       		   
//   		   	//2.10 프로젝트 상태 [PLM Attribute]
//   		   	project.setPjtState(ProjectStateHelper.manager.COMPLETE);
//       		   
//   		   	//2.11 개발유형 [PLM Attribute]
//   		   	if(projectDivisionCode != null && projectDivisionCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("DIVISIONCODE", projectDivisionCode);
//   		   		//Kogger.debug(getClass(), "개발유형["+code.getName()+"]");
//   		   		project.setDivision(code);
//   		   	}
//       		   
//   		   	//2.12 난이도 [PLM Attribute]
//   		   	if(projectLevelCode != null && projectLevelCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("LEVELCODE", projectLevelCode);
//   		   		//Kogger.debug(getClass(), "난이도["+code.getName()+"]");
//   		   		project.setLevel(code);
//   		   	}
//       		   
//   		   	//2.13 제품군 [PLM Attribute]
//   		   	if(projectProductCode != null && projectProductCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("PRODUCTCODE", projectProductCode);
//   		   		//Kogger.debug(getClass(), "제품군["+code.getName()+"]");
//   		   		project.setProduct(code);
//   		   	}
//       		   
//   		   	//2.14 발주처 [PLM Attribute]
//   		   	if(projectCustomerCode != null && projectCustomerCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("CUSTOMERCODE", projectCustomerCode);
//   		   		//Kogger.debug(getClass(), "발주처["+code.getName()+"]");
//   		   		project.setCustomer(code);
//   		   	}
//       		   
//   		   	//2.15 개발조직 [PLM Attribute]
//   		   	if(projectDevcompanyCode != null && projectDevcompanyCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVCOMPANYCODE", projectDevcompanyCode);
//   		   		//Kogger.debug(getClass(), "개발조직["+code.getName()+"]");
//   		   		project.setDevcompany(code);
//   		   	}
//       		   
//   		   	//2.16 생산조직 [PLM Attribute]
//   		   	if(projectMakecompanyCode != null && projectMakecompanyCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("MAKECOMPANYCODE", projectMakecompanyCode);
//   		   		//Kogger.debug(getClass(), "생산조직["+code.getName()+"]");
//   		   		project.setMakecompany(code);
//   		   	}
//       		   
//   		   	//2.17 모델(차종) [PLM Attribute]
//   		   	if(projectModelCode != null && projectModelCode.length() > 0) {
//   		   		NumberCode code = NumberCodeHelper.manager.getNumberCode("MODELCODE", projectModelCode);
//   		   		//Kogger.debug(getClass(), "모델["+code.getName()+"]");
//   		   		project.setModel(code);
//   		   	}
//       		   
//   		   	//2.18 프로젝트 종류   [2: 수주/견적, 1: 개발]
//   		   	project.setPjtType(Integer.parseInt(projectType));
       		   
   		    //2.19 Program 설정
//   		   	if(pmoid != null){
//   		   		ProjectManager pm = (ProjectManager)CommonUtil.getObject(pmoid);
//   		   		project.setManager(pm);
//   		   	}
   		   	
   		   	//2.19 CreateStamp && ModifyStamp Date 변경
   		   	//2.19.1 Create Stamp Setting
   		   	tempCal.setTime(DateUtil.parseDateStr(regDate));
   		   	Timestamp regTS = new java.sql.Timestamp(tempCal.getTime().getTime());
   		   	
   		   	//2.19.2 Modify Stamp Setting
   		   	tempCal.setTime(DateUtil.parseDateStr(modifyDate));
		   	Timestamp modifyTS = new java.sql.Timestamp(tempCal.getTime().getTime());
		   	
		   	//2.19.3 Create Stamp & Modify
//		   	project = (JELProject) PersistenceServerHelper.manager.store(project, regTS, modifyTS);
//		   	Kogger.debug(getClass(), "######################################JELProject[Migration]>>>>>> "+project.getPjtNo());
   		   	
   		   	//2.20 JELProject Save
//   		   	project = (JELProject) PersistenceHelper.manager.save(project);
//   		   	Kogger.debug(getClass(), "######################################JELProject[Migration]>>>>>> "+project.getPjtNo());
   		   	
   		   	//2.21 LifeCycle State Change
//		   	project = (JELProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState("COMPLETED"), true);
   		   	
   		   	/*
			 * #############################################################################################################
			 * 2. JELProject Object Create END
			 * #############################################################################################################
			 */
   		   	
   		   	/*
			 * #############################################################################################################
			 * 3. JELProject Link Create START
			 * #############################################################################################################
			 */
   		   	
//   		   	//3.1 JELProjectDivisionCode Setting(개발유형)
//   		   	if(hash.get("divisioncode") != null) {
//   		   		Vector divisionCodeVec = getParamsVector(hash.get("divisioncode"));
//   		   		String divisionCodeOid = null;
//   		   		for(int i = 0; i < divisionCodeVec.size(); i++) {
//   		   			divisionCodeOid = (String) divisionCodeVec.get(i);
//   		   			NumberCode code = (NumberCode) CommonUtil.getObject(divisionCodeOid);
//   		   			if(code != null) {
//   		   				JELProjectDivisionLink link = JELProjectDivisionLink.newJELProjectDivisionLink(project, code);
//   						   PersistenceHelper.manager.save(link);
//   		   			}
//   		   		}
//   		 	 }
//       		   
//   		   	//3.2 JELProjectLevelCode Setting(난이도)
//   		   	if(hash.get("levelcode") != null) {
//   		   		Vector levelCodeVec = getParamsVector(hash.get("levelcode"));
//   		   		String levelCodeOid = null;
//   		   		for(int i = 0; i < levelCodeVec.size(); i++) {
//   		   			levelCodeOid = (String) levelCodeVec.get(i);
//   		   			NumberCode code = (NumberCode) CommonUtil.getObject(levelCodeOid);
//   		   			if(code != null) {
//   		   				JELProjectLevelLink link = JELProjectLevelLink.newJELProjectLevelLink(project, code);
//   		   				PersistenceHelper.manager.save(link);
//   		   			}
//   		   		}
//   		   	}	
//       		   
//   		   	//3.3 JELProjectProductCode Setting(제품군)
//   		   	if(hash.get("productcode") != null) {
//   		   		Vector productCodeVec = getParamsVector(hash.get("productcode"));
//   		   		String productCodeOid = null;
//   		   		for(int i = 0; i < productCodeVec.size(); i++) {
//   		   			productCodeOid = (String) productCodeVec.get(i);
//   		   			NumberCode code = (NumberCode) CommonUtil.getObject(productCodeOid);
//   		   			if(code != null) {
//   		   				JELProjectProductLink link = JELProjectProductLink.newJELProjectProductLink(project, code);
//   		   				PersistenceHelper.manager.save(link);
//   					  }
//   		   		}
//   		   	}
//       		   
//   		   	//3.4 JELProjectCustomerCode Setting(발주처)
//   		   	if(hash.get("customercode") != null) {
//   		   		Vector customerCodeVec = getParamsVector(hash.get("customercode"));
//   		   		String customerCodeOid = null;
//   		   		for(int i = 0; i < customerCodeVec.size(); i++) {
//   		   			customerCodeOid = (String) customerCodeVec.get(i);
//   		   			NumberCode code = (NumberCode) CommonUtil.getObject(customerCodeOid);
//   		   			if(code != null) {
//   		   				JELProjectCustomerLink link = JELProjectCustomerLink.newJELProjectCustomerLink(project, code);
//   		   				PersistenceHelper.manager.save(link);
//   		   			}
//   		   		}
//   		   	}
//       		   
//   		   	//3.5 JELProjectDevCompanyCode Setting(개발조직)
//   		   	if(hash.get("devcompanycode") != null) {
//   		   		Vector devcompanyCodeVec = getParamsVector(hash.get("devcompanycode"));
//   		   		String devcompanyCodeOid = null;
//   		   		for(int i = 0; i < devcompanyCodeVec.size(); i++) {
//   		   			devcompanyCodeOid = (String) devcompanyCodeVec.get(i);
//   		   			NumberCode code = (NumberCode) CommonUtil.getObject(devcompanyCodeOid);
//   		   			if(code != null) {
//   		   				JELProjectDevCompanyLink link = JELProjectDevCompanyLink.newJELProjectDevCompanyLink(project, code);
//   		   				PersistenceHelper.manager.save(link);
//   		   			}
//   		   		}
//   		   	}
//       		   
//   		   	//3.6 JELProjectMakeCompanyCode Setting(생산조직)
//   		  	if(hash.get("makecompanycode") != null) {
//   		  		Vector makecompanyCodeVec = getParamsVector(hash.get("makecompanycode"));
//   		  		String makecompanyCodeOid = null;
//   		  		for(int i = 0; i < makecompanyCodeVec.size(); i++) {
//   		  			makecompanyCodeOid = (String) makecompanyCodeVec.get(i);
//   		  			NumberCode code = (NumberCode) CommonUtil.getObject(makecompanyCodeOid);
//   		  			if(code != null) {
//   		  				JELProjectMakeCompanyLink link = JELProjectMakeCompanyLink.newJELProjectMakeCompanyLink(project, code);
//   		  				PersistenceHelper.manager.save(link);
//   				 	 }
//   		  		}
//   		  	}
//       		   
//   		  	//3.7 JELProjectModelCode Setting(모델[차종])
//   		  	if(hash.get("modelcode") != null) {
//   		  		Vector modelCodeVec = getParamsVector(hash.get("modelcode"));
//   		  		String modelCodeOid = null;
//   		  		for(int i = 0; i < modelCodeVec.size(); i++) {
//   		  			modelCodeOid = (String) modelCodeVec.get(i);
//   		  			NumberCode code = (NumberCode) CommonUtil.getObject(modelCodeOid);
//   		  			if(code != null) {
//   		  				JELProjectModlLink link = JELProjectModlLink.newJELProjectModlLink(project, code);
//   		  				PersistenceHelper.manager.save(link);
//   		  			}
//   		  		}
//   		  	}
   		  	
   		  	/*
			 * #############################################################################################################
			 * 3. JELProject Link Create END
			 * #############################################################################################################
			 */
   		  	
   		  	/*
			 * #############################################################################################################
			 * 4. PM Setting Create START
			 * #############################################################################################################
			 */
   		  	
	  		People pm = PeopleHelper.manager.getPeople(pmId);
	  		if(pmId != null) {
//	  			ProjectUserHelper.manager.setPM(project, pm.getUser(), 0);
	  		}
   		   	
   		  	/*
			 * #############################################################################################################
			 * 4. PM Setting Create END
			 * #############################################################################################################
			 */
   		  	
   		  	/*
			 * #############################################################################################################
			 * 5. Member Setting Create START
			 * #############################################################################################################
			 */
   		  	
   		  	/*
			 * #############################################################################################################
			 * 5. Member Setting Create END
			 * #############################################################################################################
			 */
   		  	
   		  	/*
			 * #############################################################################################################
			 * 6. HistoryManager Object Create START
			 * #############################################################################################################
			 */
   		  	/*
   		  	Hashtable hashHistory = new Hashtable();
   		  	hashHistory.put("jelProject", project);
   		  	hashHistory.put("historyType", "PROJECTCREATE");
   		  	HistoryManager historyMgmt = JELProjectHelper.service.createHistoryManager(hashHistory);
   		  	*/
   		  	/*
			 * #############################################################################################################
			 * 6. HistoryManager Object Create END
			 * #############################################################################################################
			 */
   		   	
   		  	transaction.commit();
   		  	transaction = null;
		} catch (PersistenceException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTPropertyVetoException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (ParseException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (Exception e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return project;
	}
	
	public static E3PSTask createJELTask(Hashtable hash) {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"createJELTask",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (E3PSTask) obj;
		}
		
		String pjtCode = (String) hash.get("pjtCode");						//프로젝트 코드
		String taskCode = (String) hash.get("taskCode");					//Task 코드
		String taskName = (String) hash.get("taskName");					//Task 이름
		String taskType = (String) hash.get("taskType");					//TaskType(P: LV 1, N: LV 2)
		String planSDate = (String) hash.get("planSDate");					//계획 시작일자
		String planFDate = (String) hash.get("planFDate");					//계획 종료일자
		String execSDate = (String) hash.get("execSDate");					//실제 시작일자
		String execFDate = (String) hash.get("execFDate");					//실제 종료일자
		String taskManager = (String) hash.get("taskManager");				//TASK 책임자
		
//		Kogger.debug(getClass(), "pjtCode>>> "+pjtCode);
//		Kogger.debug(getClass(), "taskCode>>> "+taskCode);
//		Kogger.debug(getClass(), "taskName>>> "+taskName);
//		Kogger.debug(getClass(), "taskType>>> "+taskType);
//		Kogger.debug(getClass(), "planSDate>>> "+planSDate);
//		Kogger.debug(getClass(), "planFDate>>> "+planFDate);
//		Kogger.debug(getClass(), "execSDate>>> "+execSDate);
//		Kogger.debug(getClass(), "execFDate>>> "+execFDate);
//		Kogger.debug(getClass(), "taskManager>>> "+taskManager);
		
		Calendar tempCal = Calendar.getInstance();
		E3PSProject project = null;
		E3PSTask task = null;
		
		Transaction transaction = new Transaction();
		
		try {
			transaction.start();
			
			/*
			 * #############################################################################################################
			 * 1. ExtendScheduleData Object Create START
			 * #############################################################################################################
			 */
			
			//1-1. ExtendScheduleData Object Init
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			
			//1-2. Duration
			if(StringUtil.checkString(planSDate.trim()) && StringUtil.checkString(planFDate.trim())) {
				//계획 시작일자
				tempCal.setTime(DateUtil.parseDateStr(planSDate));
				Timestamp planStartDate = new java.sql.Timestamp(tempCal.getTime().getTime());
				//계획 종료일자
				tempCal.setTime(DateUtil.parseDateStr(planFDate));
				Timestamp planEndDate = new java.sql.Timestamp(tempCal.getTime().getTime());
				
				int tempDuration = DateUtil.getDaysFromTo(DateUtil.getDateString(planEndDate, "d"), DateUtil.getDateString(planStartDate, "d"));
				taskScheduleData.setDuration(tempDuration);
		   	}
			
			//1-3. 표준공수
	        taskScheduleData.setStdWork(0);
	        
	        //1-4. 계획 시작일자
	        tempCal.setTime(DateUtil.parseDateStr(planSDate));
	        taskScheduleData.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	        
	        //1-5. 계획 종료일자
	        tempCal.setTime(DateUtil.parseDateStr(planFDate));
	        taskScheduleData.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	        
	        //1-6. 실제 시작일자
	        tempCal.setTime(DateUtil.parseDateStr(execSDate));
	        taskScheduleData.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	        
	        //1-7. 실제 종료일자
	        tempCal.setTime(DateUtil.parseDateStr(execFDate));
	        taskScheduleData.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	        
	        //1-. ExecWork
	        taskScheduleData.setExecWork(0);
	        
	        //1-. ScheduleHistory
	        taskScheduleData.setScheduleHistory(0);
	        
	        //1-. ProgramManager Start Date
	        tempCal.setTime(DateUtil.parseDateStr(planSDate));
	        
	        //1-. ProgramManager End Date
	        tempCal.setTime(DateUtil.parseDateStr(planFDate));
	        
	        //1-8. ExtendScheduleData Object Save
	        taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);

	        /*
			 * #############################################################################################################
			 * 1. ExtendScheduleData Object Create END
			 * #############################################################################################################
			 */
	        
	        /*
			 * #############################################################################################################
			 * 2. JELTask Object Create START
			 * #############################################################################################################
			 */
	        
	        //2-1. JELTask Object Init
	        task = E3PSTask.newE3PSTask();
	        
	        //2-2. Task 이름
	        task.setTaskName(taskName);
	        
	        //2-3. Task 코드
	        task.setTaskCode(taskCode);
	        
	        //2-. Task Sequence
	        task.setTaskSeq(0);
	        
	        //2-. Task 설명
	        task.setTaskDesc("");
	        
	        //2-. Task 종류    [2: 수주/견적, 1: 개발]
//	        task.setTaskType(1);
//	        
//	        //2-. JELProject
//	        project = getProject(pjtCode);
//	        task.setProject(project);
//	        
//	        //2-. DepartmentRole
//	        task.setDeptRole("");
	        
	        //2-. TaskNo
	        task.setTaskNo("");
	        
	        //2-. 진행률
	        task.setTaskCompletion(100);
	        
	        //2-. TaskHistory
	        task.setTaskHistory(0);
	        
	        //2-. TaskKey
//	        task.setTaskKey("");
	        
	        //2-. TaskWork
//	        task.setTaskWork(null);
	        
	        //2-. OEMType
//	        task.setOemType(null);
	        
	        //2-. Task 책임자/담당부서
	        People manager = PeopleHelper.manager.getPeople(taskManager);
	        if(manager != null) {
	        	task.setDepartment(manager.getDepartment());
	        }
	        
	        //2-. Task 선후행
	        E3PSTask parentTask = null;
	        if(taskType.equals("N")) {
	        	String[] parentCode = tokenStr(taskCode, ".");
	        	parentTask = getParentTask(project, parentCode[0]);
	        	task.setParent(parentTask);
	        }
	        
	        //2-. ExtendScheduleData
	        task.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
	        
	        //2-. State
	        task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
	        
	        //2-. JELTask Object Save
//	        task = (JELTask) PersistenceHelper.manager.save(task);
	        task = (E3PSTask) PersistenceServerHelper.manager.store(task, taskScheduleData.getExecEndDate(), DateUtil.getCurrentTimestamp());
//	        Kogger.debug(getClass(), "####################################JELTask[Migration]>>>>>>>>>> "+task.getTaskName()+"["+((JELProject)task.getProject()).getPjtNo()+"]");
	        
	        /*
			 * #############################################################################################################
			 * 2. JELTask Object Create END
			 * #############################################################################################################
			 */
	        
	        /*
			 * #############################################################################################################
			 * 3. TaskDependencyLink Object Create START
			 * #############################################################################################################
			 */
	        
	        if(parentTask != null) {
	        	TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(task, parentTask);
	        	link.setDelayDuration(0);
	        	
	        	link = (TaskDependencyLink) PersistenceHelper.manager.save(link);
	        }
	    	
	    	/*
			 * #############################################################################################################
			 * 3. TaskDependencyLink Object Create END
			 * #############################################################################################################
			 */
	        
	        transaction.commit();
   		  	transaction = null;
   		  	
   		  	return task;
		} catch (PersistenceException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTPropertyVetoException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (ParseException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return null;
	}
	
	public static ProjectOutput createProjectOutput(HashMap map) {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"createProjectOutput",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (ProjectOutput) obj;
		}
		
//		String oid = (String)map.get("oid");
		String taskCode = (String)map.get("taskCode");
		String pjtCode = (String)map.get("pjtCode");
		String name = (String)map.get("name");
		String description = (String)map.get("description");
//		String docTypeOid = (String)map.get("docTypeOid");
		String role = (String)map.get("role");
		String outputUser = (String)map.get("outputUser");
		String location = "";
		String objType = (String)map.get("objType");
//		String outputtype = (String)map.get("outputtype");
		String isPrimary = (String)map.get("isPrimary");
		String oldDocOid = (String) map.get("oldDocOid");
		String regDate = (String) map.get("regDate");
		
//		Kogger.debug(getClass(), "taskCode<<< "+taskCode);
//		Kogger.debug(getClass(), "pjtCode<<< "+pjtCode);
//		Kogger.debug(getClass(), "name<<< "+name);
//		Kogger.debug(getClass(), "desc<<< "+description);
//		Kogger.debug(getClass(), "outputUser<<< "+outputUser);
//		Kogger.debug(getClass(), "objType<<< "+objType);
//		Kogger.debug(getClass(), "isPrimary<<< "+isPrimary);
//		Kogger.debug(getClass(), "oldDocOid<<< "+oldDocOid);
		
		ProjectOutput output = null;
		
		Transaction transaction = new Transaction();
		
		try {
			transaction.start();
			
			/*
			 * #############################################################################################################
			 * 1. ProjectOutput Object Create START
			 * #############################################################################################################
			 */
			
			//1-1. ProjectOutput Object Init
			output = ProjectOutput.newProjectOutput();
			
			//1-2. ProjectOutput Name
			output.setOutputName(name);
			
			//1-3. ProjectOutput Desc
			output.setOutputDesc(description);
			
			//1-4. ProjectOutput Location
			output.setOutputLocation(location);
			
			//1-5. ProjectOutput Role
			output.setOutputRole(role);
			
			//1-6. JELProject
//			output.setProject(getProject(pjtCode));
			
			//1-7. JELTask
			output.setTask(getParentTask(getProject(pjtCode), taskCode));
			
			//1-8. ObjectType
			output.setObjType(objType);
			
			//1-9. isPrimary
			output.setIsPrimary(isPrimary.equalsIgnoreCase("1"));
			
			//1-10.
			output.setCompletion(-1);
			
			//1-11. ProjectOutput User
			if(StringUtil.checkString(outputUser) && !outputUser.equalsIgnoreCase("outputUser") && !outputUser.equalsIgnoreCase("Admin")) {
				People pm = PeopleHelper.manager.getPeople(outputUser);
				if(pm != null) {
					WTPrincipalReference wtuserRef = WTPrincipalReference.newWTPrincipalReference((WTUser)pm.getUser());
					output.setOwner(wtuserRef);
				} else {
					output.setOwner(WTPrincipalReference.newWTPrincipalReference(SessionHelper.manager.getPrincipal()));
					Kogger.debug(ProjectMigrationHelper.class, "########################ProjectOutput[Migration]>>>>>>>>>>>>>[setOwner]>>>>>>>>>>>> "+((WTUser)SessionHelper.manager.getPrincipal()).getName());
				}
			} else {
				output.setOwner(WTPrincipalReference.newWTPrincipalReference(SessionHelper.manager.getPrincipal()));
				Kogger.debug(ProjectMigrationHelper.class, "########################ProjectOutput[Migration]>>>>>>>>>>>>>[setOwner]>>>>>>>>>>>> "+((WTUser)SessionHelper.manager.getPrincipal()).getName());
			}
			
			//1-12. ProjectOutput Create
//			output = (ProjectOutput)PersistenceHelper.manager.save(output);
			//1-12-1. Create Stamp
			if(StringUtil.checkString(regDate)) {
				Calendar tempCal = Calendar.getInstance();
				tempCal.setTime(DateUtil.parseDateStr(regDate));
	   		   	Timestamp regTS = new java.sql.Timestamp(tempCal.getTime().getTime());
	   		   	
	   		   	output = (ProjectOutput) PersistenceServerHelper.manager.store(output, regTS, DateUtil.getCurrentTimestamp());
			} else {
				output = (ProjectOutput) PersistenceServerHelper.manager.store(output, DateUtil.getCurrentTimestamp(), DateUtil.getCurrentTimestamp());
			}
//			Kogger.debug(getClass(), "##############################ProjectOutput[Migration]>>>>>>>>>>>> "+output.getOutputName()+"["+((JELProject)output.getProject()).getPjtNo()+"]");

			/*
			 * #############################################################################################################
			 * 1. ProjectOutput Object Create END
			 * #############################################################################################################
			 */
			
			/*
			 * #############################################################################################################
			 * 2. OutputDocumentLink Object Create START
			 * #############################################################################################################
			 */
			
			String newDocOid = "";
			if(StringUtil.checkString(oldDocOid)) {
				newDocOid = getDoc(oldDocOid);
				
				if(StringUtil.checkString(newDocOid)) {
					ReferenceFactory rf = new ReferenceFactory();
					RevisionControlled rc = (RevisionControlled) rf.getReference(newDocOid).getObject();
					
					//2-1. OutputDocumentLink Object Create
					OutputDocumentLink link = OutputDocumentLink.newOutputDocumentLink(output, (Master)rc.getMaster());
		            link.setBranchIdentifier(rc.getBranchIdentifier());   
		            link.setDocClassName(rc.getClass().getName());
		            
		            link = (OutputDocumentLink) PersistenceHelper.manager.save(link);
				}
			}
			
			/*
			 * #############################################################################################################
			 * 2. OutputDocumentLink Object Create END
			 * #############################################################################################################
			 */
            
            /*
			 * #############################################################################################################
			 * 3. ProjectOutput(Location) Object Create START
			 * #############################################################################################################
			 */
            
            //3. 
			if(oldDocOid != null) {
				if(newDocOid != null) {
//		            JELDocument doc = (JELDocument) CommonUtil.getObject(newDocOid);
		            
//		            DocCodeType docType = DocCodeTypeHelper.getDocCodeType(doc)[0];
//		            String tLocation = docType.getPath();
//		    		if(StringUtil.checkString(tLocation)) {
//		    			output.setOutputLocation(tLocation);
//		    			output = (ProjectOutput) PersistenceHelper.manager.modify(output);
//			            Kogger.debug(getClass(), "#####################ProjectOutput[Migration : OLD Document Link]>>>>>>>>>> "+output.getOutputName()+"["+((JELProject)output.getProject()).getPjtNo()+"]");
//		    		}
				}
			}
            
            /*
			 * #############################################################################################################
			 * 3. ProjectOutput(Location) Object Create END
			 * #############################################################################################################
			 */
			
			transaction.commit();
			transaction = null;
		} catch (PersistenceException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTPropertyVetoException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (RemoteException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (InvocationTargetException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (ParseException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return null;
	}
	
	public static E3PSProject createProjectResource(Hashtable hash) {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"createProjectResource",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (E3PSProject) obj;
			
		}
		
		String pjtCode = (String) hash.get("pjtCode");
		String memberId = (String) hash.get("memberId");
		String memberRole = (String) hash.get("memberRole");
		
		Transaction transaction = new Transaction();
		
		try {
			transaction.start();
			
			/*
			 * #############################################################################################################
			 * 1. ProjectOutput Object Create START
			 * #############################################################################################################
			 */
			
			//1-1. JELProject
			E3PSProject project = getProject(pjtCode);
			
			//1-2. PM
//			if(memberRole.equalsIgnoreCase("PM")) {
//				ProjectUserHelper.manager.setPM(project, pmUser, 0);
//			}
			
			//1-3. Member
			if(memberRole != null && memberRole.equalsIgnoreCase("Member")) {
				People pm = PeopleHelper.manager.getPeople(memberId);
				if(pm != null) {
//					ProjectUserHelper.manager.setMember(project, pm.getUser(), 0);
				}
			}
			
//			QueryResult result = ProjectUserHelper.manager.getMember(project);
//			while(result.hasMoreElements()) {
//				Object[] obj = (Object[]) result.nextElement();
//				ProjectMemberLink link = (ProjectMemberLink) obj[0];
//				
//				WTUser user = link.getMember();
//				Kogger.debug(getClass(), "#######################Member[Migration]>>>>>>>>>>> "+user.getName()+"["+((JELProject)link.getProject()).getPjtNo()+"]");
//			}
			
			/*
			 * #############################################################################################################
			 * 1. ProjectOutput Object Create START
			 * #############################################################################################################
			 */
			
			transaction.commit();
			transaction = null;
		} catch (PersistenceException e) {
			if ( transaction != null) transaction.rollback();
  		   	transaction = null;
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return null;
	}

	private static E3PSTask getParentTask(E3PSProject project, String taskCode) {
		//select * from jeltask where ida3b4 = '' and taskcode = '
		QuerySpec spec = null;
		QueryResult result = null;
		E3PSTask task = null;
		
		try {
			spec = new QuerySpec();
			
			Class target = E3PSTask.class;
			int idx_target = spec.addClassList(target, true);
			
//			SearchUtil.appendEQUAL(spec, target, "projectReference.key.id", CommonUtil.getOIDLongValue(project), idx_target);
			SearchUtil.appendEQUAL(spec, target, "taskCode", taskCode, idx_target);
//			Kogger.debug(getClass(), "getParentTask[SPEC]>>> "+spec);
			result = PersistenceHelper.manager.find(spec);
			while(result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				task = (E3PSTask) obj[0];
//				Kogger.debug(getClass(), "JELTask<<<<< "+CommonUtil.getOIDString(task));
			}
		} catch (QueryException e) {
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTException e) {
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return task;
	}

	private static Vector getParamsVector(Object obj) {
		Vector vec = new Vector();
		if(obj == null) return vec;
		if(obj instanceof String) {
			vec.add(obj.toString());
		} else if(obj instanceof String[]) {
			String[] strs = (String[])obj;
			for(int i=0; i < strs.length; i++) {
				vec.addElement(strs[i]);
			}
		} else if(obj instanceof Vector) {
			vec = (Vector)obj;
		}
		return vec;
	}
	
	private static String getDoc(String oid) throws RemoteException, InvocationTargetException {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{String.class};
			Object args[] = new Object[]{oid};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getDoc",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (String) obj;
		}
        
        String docOid = "";
        
        try{
//             QuerySpec spec = new QuerySpec(JELDocument.class);
//             spec.appendWhere(new SearchCondition(JELDocument.class, "oldNum", "=", oid), new int[] { 0 });
////             Kogger.debug(getClass(), "getDoc QuerySpec ==>> "+spec);
//             QueryResult rs = PersistenceHelper.manager.find(spec);
//             JELDocument jeldoc = null;
//
//             long maxlong = 0;
//             while(rs.hasMoreElements()){
//            	 jeldoc = (JELDocument)rs.nextElement();
//             }
               
//             docOid = (String)CommonUtil.getOIDString(jeldoc);
//             Kogger.debug(getClass(), "jelDocOid :::>>> "+docOid);
               
        }catch(Exception e){
              Kogger.error(ProjectMigrationHelper.class, e);
              return null;
        }
        
        return docOid;
	}
	
	private static E3PSProject getProject(String code) {
		if(!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{String.class};
			Object args[] = new Object[]{code};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getProject",
						ProjectMigrationHelper.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectMigrationHelper.class, e);
			}
			return (E3PSProject) obj;
		}
		
		/**
		 	 SELECT A0.*
			 FROM JELProject A0,
			 		(
			 		SELECT A1.idA2A2 A2 
			 		FROM JELProject A0, JELProjectMaster A0B, JELProjectMaster A1 
			 		WHERE (A0.idA3B8=A0B.idA2A2) AND ((A1.pjtNo = '20050000'))
			 		) A1
			 WHERE (A0.idA3B8 = A1.A2)
		 */
		QuerySpec subSpec = null;
		QueryResult result = null;
		E3PSProject project = null;
		
		try {
			//Sub QuerySpec
			subSpec = new QuerySpec();
			
//			Class pjtClass = JELProject.class;
			Class pjtMasterClass = E3PSProjectMaster.class;
			
//			int idx_pjtClass = subSpec.addClassList(pjtClass, false);
			int idx_pjtMasterClass = subSpec.addClassList(pjtMasterClass, false);
			
			ClassAttribute ca = new ClassAttribute(pjtMasterClass, "thePersistInfo.theObjectIdentifier.id");
			ca.setColumnAlias("A" + idx_pjtMasterClass + "");
			subSpec.appendSelect(ca, false);
			
			if(subSpec.getConditionCount() > 0) {
				subSpec.appendAnd();
			}
		    subSpec.appendWhere(new SearchCondition(pjtMasterClass, "pjtNo", SearchCondition.EQUAL, code),new int[] { idx_pjtMasterClass});
			
			//Main QuerySpec
			QuerySpec mainSpec = new QuerySpec(E3PSProject.class);
			
			SubSelectExpression subfrom =  new SubSelectExpression(subSpec);
			subfrom.setFromAlias(new String[]{"JELPROJECT0"}, 0);
            int iba_idx = mainSpec.appendFrom(subfrom);
            
            KeywordExpression ke = null;
            
            ca = new ClassAttribute(E3PSProject.class, "masterReference.key.id");
            ke = new KeywordExpression(mainSpec.getFromClause().getAliasAt(iba_idx) + "." + "A0");
            SearchCondition sc = new SearchCondition(ca, "=", ke);
            sc.setFromIndicies(new int[]{0, iba_idx},0);
            sc.setOuterJoin(0);
            mainSpec.appendWhere(sc, new int[]{0, iba_idx});
			
            mainSpec.setAdvancedQueryEnabled(true);
//            Kogger.debug(getClass(), "ProjectMigrationHelper.getProject[mainSpec]>>> "+mainSpec);
			result = PersistenceHelper.manager.find(mainSpec);
//			
			while(result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				project = (E3PSProject) obj[0];
//				Kogger.debug(getClass(), "JELProject>>> "+CommonUtil.getOIDString(project));
			}
		} catch (QueryException e) {
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTException e) {
			Kogger.error(ProjectMigrationHelper.class, e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(ProjectMigrationHelper.class, e);
		}
		
		return project;
	}
	
	private static String[] tokenStr(String str, String delim) {
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] returnStr = new String[st.countTokens()];
		
		int i = 0;
		while(st.hasMoreTokens()) {
			returnStr[i] = st.nextToken();
			i++;
		}
		
		return returnStr;
	}
}
