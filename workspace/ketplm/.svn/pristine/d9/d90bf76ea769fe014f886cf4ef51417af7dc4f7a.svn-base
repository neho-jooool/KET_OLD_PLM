package e3ps.project.trySchdule.beans;

import java.sql.Timestamp;
import java.util.Hashtable;

import wt.fc.QueryResult;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskUserHelper;
import e3ps.project.machine.MoldMachine;
import e3ps.project.material.MoldMaterial;
import e3ps.project.trySchdule.TrySchdule;
import ext.ket.shared.log.Kogger;

public class TrySchduleData {
	
	public final static String MOLDPLANER_ROLE = "Team_MOLD01";
	public final static String MOLDMAKER_ROLE ="Team_MOLD03";
	
	
	public String dieNo;
	public String partNumber;
	public String tryType;
	
	/*금형설계*/
	public String moldPlanerName;
	public WTUser moldPlaner;
	
	/*금형조립*/
	public String moldMakerName;
	public WTUser moldMaker; 
	
	/*제품설계*/
	public String projectPlanerName;
	public WTUser projectPlaner;
	
	/*품명*/
	public String partName;
	
	/*원재료명*/
	public String materialName;
	public MoldMaterial material;
	
	/*원재료 추가정보 */
	public NumberCode property;
	public String thickness;
	public String width;
	
	/*제작처*/
	public String outsourcingName;
	public String outsourcing;
	
	/*CAV수*/
	public String cavSu;
	
	/*설비톤*/
	public String ton;
	
	/*수량*/
	public String quantity;
		
	/*내용*/
	public String des;
	
	/*상태*/
	public int state;
	
	/*의뢰자*/
	public String requestorName;
	public WTUser requestor;
	
	/*제작자*/
	public String creatorName;
	public WTUser creator;
	
	/*계획 or 실제 시작일*/
	public String dayString;
	public Timestamp day;
	
	/*try 장소*/
	public String tryPlace;
	
	/*oid*/
	public String oid;
	
	/* Try 확정 여부 */
	public boolean isTryPlan;
	
	/* Try 완료 여부*/	
	public boolean isCompleted;
	
	/* MoldProject */
	public MoldProject project;
	Object schduleData;
	
	public String shortType;
	
	
	public TrySchduleData(TrySchdule schdule){
		schduleData = schdule;
		
		oid = CommonUtil.getOIDString(schdule);
		
		try {
			project = TrySchduleHelper.getFromMaster(schdule.getMoldMaster());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		}
		
		MoldItemInfo mInfo = null;
		
		if(project != null){
			
			mInfo = project.getMoldInfo();
			Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);
			
			moldPlaner = (WTUser)userH.get(MOLDPLANER_ROLE);
			
			if(moldPlaner != null){
				
				moldPlanerName = moldPlaner.getFullName();
			
			}
			
			moldMaker = (WTUser)userH.get(MOLDMAKER_ROLE);
			
			if(moldMaker != null){
				
				moldMakerName = moldMaker.getFullName();
			
			}
	
			outsourcing = project.getOutSourcing();
			
			if(outsourcing != null){
				
				outsourcingName = outsourcing;
			
			}
			
			
		}
		
		if(mInfo != null){
			
			dieNo = mInfo.getDieNo();
			partNumber = mInfo.getPartNo();
			partName = mInfo.getPartName();
			ProductProject productProject = mInfo.getProject();
			projectPlaner = ProjectUserHelper.manager.getPM(productProject);
			
			if(projectPlaner != null){
				projectPlanerName = projectPlaner.getFullName();
			}
			
			cavSu = mInfo.getCVPitch();
			
			
		}
		
		material = schdule.getMaterial();
		
		property = schdule.getProperty();
		
		String propertyName = "";
		
		if(property != null){
			propertyName = property.getName();
		}
		
		if(material != null){
			
			materialName = material.getMaterialType().getName() + "(" + material.getGrade() + ")_" + propertyName;
		
		}
		
		
		
		thickness = schdule.getThickness();
		
		width = schdule.getWidth();
		
		tryType = schdule.getTryType();
		shortType = getShortType(tryType);
		
		ton = schdule.getTon();
		
		quantity = schdule.getQuantity();
		
		day = schdule.getPlanDate();
		
		dayString = DateUtil.getDateString(day, "d");
		
		des = schdule.getDes();
		
		tryPlace = schdule.getTryPlace();
		
		state = TryPlanData.getTryState(schdule);
		
		isTryPlan = schdule.isTryPlan();
		
		isCompleted = schdule.isCompleted();
		
		WTPrincipalReference temp = null;
		
		if(schdule.getRequester() != null){
			temp = schdule.getRequester();
			requestor = (WTUser)temp.getObject();
			
			if(requestor != null){
				requestorName = requestor.getFullName();
			}
		}
		
		if(schdule.getCreator() != null){
			temp = schdule.getCreator();
			creator = (WTUser)temp.getObject();
			
			if(creator != null){
				creatorName = creator.getFullName();
			}
		}
	}
	
	public TrySchduleData(E3PSTask task){
		
		schduleData = task;
		oid = CommonUtil.getOIDString(task);
		
		project = (MoldProject)task.getProject();
		MoldItemInfo mInfo = null;
		mInfo = project.getMoldInfo();
		Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);
		
		moldPlaner = (WTUser)userH.get(MOLDPLANER_ROLE);
		
		if(moldPlaner != null){
			
			moldPlanerName = moldPlaner.getFullName();
		
		}
		
		moldMaker = (WTUser)userH.get(MOLDMAKER_ROLE);
		
		if(moldMaker != null){
			
			moldMakerName = moldMaker.getFullName();
		
		}

		outsourcing = project.getOutSourcing();
		
		if(outsourcing != null){
			
			outsourcingName = outsourcing;
		
		}
		
		if(mInfo != null){
			
			dieNo = mInfo.getDieNo();
			partNumber = mInfo.getPartNo();
			partName = mInfo.getPartName();
			ProductProject productProject = mInfo.getProject();
			projectPlaner = ProjectUserHelper.manager.getPM(productProject);
			
			if(projectPlaner != null){
				projectPlanerName = projectPlaner.getFullName();
			}
			
			cavSu = mInfo.getCVPitch();
			
			material = mInfo.getMaterial();
			
			property = mInfo.getProperty();
			String propertyName = "";
			if(property != null){
				propertyName = property.getName();
			}
			
			if(material != null){
				
				materialName = material.getMaterialType().getName() + "(" + material.getGrade() + ")_" + propertyName;
				//materialName = material.getGrade();
			
			}	
		}
		
		if(task.getNcha() > 0){
			tryType = "T" + task.getNcha();
			shortType = tryType;
		}else{
			String taskName = task.getTaskName();
			tryType = getType(taskName);
			shortType = getShortType(tryType);
		}
		
		if(task.getTon() != null && task.getTon().length() > 0){
			ton = task.getTon();
		}else{
			MoldMachine machine = project.getMoldMachine();
			if(machine != null){
				ton = machine.getModel() + "(" + machine.getTon().getName() + ")";
			}
		}
		
		quantity = task.getQuantity();
		
		ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
		
		day = getTryPlanDate(task);
				
		dayString = DateUtil.getDateString(day, "d");
		
		des = task.getTryDes();
		
		tryPlace = task.getTryPlace();
		
		state = TryPlanData.getTryState(task);
		
		isTryPlan = task.isTryPlan();
		
		if(schedule.getExecEndDate() != null){
			isCompleted = true;
		}
		
		QueryResult rs = TaskUserHelper.manager.getMaster(task);
		
		if(rs.hasMoreElements()){
			Object o[] = (Object[])rs.nextElement();
			TaskMemberLink link = (TaskMemberLink)o[0];
			creator = link.getMember().getMember();
			
			if(creator != null){
				creatorName = creator.getFullName();
			}
		}
		
	}
	
	
	public static String getType(String taskName){
		String type = "";
		if(taskName.indexOf("[") > 0 && taskName.indexOf("]") > 0){
			if(taskName.indexOf("]") > taskName.indexOf("[")){
				type = taskName.substring(taskName.indexOf("[") + 1, taskName.indexOf("]"));	
			}
		}
		return type;
	}
	
	public static String getShortType(String type){
		String shortType;
		
		type = type.toUpperCase();
		if(type.startsWith("T0")){
			shortType = "T0";
		}
		else if(type.startsWith("양산검증")){
			shortType = "양검";
		}
		
		else if(type.startsWith("원재료변경")){
			shortType = "원병";
		}
		else if(type.startsWith("SAMPLE")){
			shortType = "S.P";
		}
		else if(type.startsWith("SET-UP")){
			shortType = "S.u";
		}
		else if(type.startsWith("금형개선")){
			shortType = "금개";
		}
		else if(type.startsWith("양산개조")){
			shortType = "양개";
		}else if(type.startsWith("TEST")){
			shortType = "Test";
		}
		else{
			if(type.length() > 2){
				shortType = type.substring(0, 2);
			}else{
				shortType = type;
			}
		}
		return shortType;
	}
	
	public String getDisplayShortType(){
		if(shortType == null || shortType.length() == 0){
			return "_____";
		}
		
		for(int i = 0 ; i < (5 - shortType.length()); i++){
			shortType += "_";
		}
		return shortType;
		
	}
	
	public static Timestamp getTryPlanDate(E3PSTask task){
		
		ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
		if(schedule.getExecEndDate() != null){
			return schedule.getExecEndDate();		
		}if(schedule.getExecStartDate() != null){
			return schedule.getExecStartDate();		
		}else{
			return schedule.getPlanStartDate();
		}
	}
	
	public static void main(String args[])throws Exception{
		Kogger.debug(TrySchduleData.class, getShortType("금형Try[양산검증]"));
	}
}
