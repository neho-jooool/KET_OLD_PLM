package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TemplateProjectTreeNodeData implements TreeNodeData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 370663161079281237L;

	private  Timestamp initStartPlanDate;
	public int level;
	public String oid;
	protected Object data;
	public TemplateProjectTreeNodeData parent;
	ScheduleData schedule ;
	int initDuration;
	int initStdWork;	//표준공수
	
	private boolean isNotPlanTask = false;
	
	public ArrayList children = new ArrayList();
	
	public TemplateProjectTreeNodeData(int level, long id, Timestamp startPlanDate) throws Exception {
		
		this.level = level;
		this.oid = TemplateTask.class.getName() + ":" + id;
		data = new ReferenceFactory().getReference(oid).getObject();
		this.initStartPlanDate = startPlanDate;
		init();
	}
	
	public TemplateProjectTreeNodeData(int level, String id, Timestamp startPlanDate) throws Exception {
		
		this.level = level;
		this.oid = id;
		data = new ReferenceFactory().getReference(oid).getObject();
		this.initStartPlanDate = startPlanDate;
		init();
	}
	
	public TemplateProjectTreeNodeData(TemplateTask task) throws Exception {	
		this.oid = CommonUtil.getOIDString(task);
		data = task;
		init();
	}

	public TemplateProjectTreeNodeData(TemplateProject project, Timestamp startPlanDate) {
		this.level = 0;
		this.data = project;
		this.oid = project.getPersistInfo().getObjectIdentifier().toString();
		this.initStartPlanDate = startPlanDate;
		init();
	}
	
	public void init(){
		if (data instanceof TemplateProject) {
			TemplateProject pro = (TemplateProject) data;
			schedule = (ScheduleData) pro.getPjtSchedule().getObject();
		} else {
			TemplateTask tas = (TemplateTask) data;
			schedule = (ScheduleData) tas.getTaskSchedule().getObject();
			
		}
		if(initStartPlanDate == null){
			initStartPlanDate = DateUtil.convertDate(DateUtil.getToDay());  
		}
		
		setPlanStartDate(initStartPlanDate);
		initDuration = schedule.getDuration();
		initStdWork = schedule.getStdWork();
		
	}
	
	public String toString() {
		StringBuffer ss = new StringBuffer();
		for (int i = 0; i < level; i++) {
			ss.append("..");
		}

		if (data instanceof TemplateProject) {
			TemplateProject pro = (TemplateProject) data;
			ss.append(pro.getPjtName());
		} else {
			TemplateTask tas = (TemplateTask) data;
			ss.append(tas.getTaskName());
		}
		return ss.toString();
	}
		
	public int getStdWork() {
		return getSchedule().getStdWork();
	}
	
	public void setStdWork(int stdWork) {	
		try {
			getSchedule().setStdWork(stdWork);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}
	}

	public int getDuration() {
		return getSchedule().getDuration();
	}
	
	public Timestamp getPlanStartDate() {
		/*
		if(getSchedule().getPlanStartDate() == null){	
			setPlanStartDate(initStartPlanDate);
		}*/
		
		return getSchedule().getPlanStartDate();
	}

	public Timestamp getPlanEndDate() {
		/*if(getSchedule().getPlanEndDate() == null){		
			setPlanStartDate(initStartPlanDate);
		}*/
		return getSchedule().getPlanEndDate();
	}
	
	
	
	public int getCalculationDuration() {
		return DateUtil.getDuration(schedule.getPlanStartDate(), schedule.getPlanEndDate()) + 1;	
	}
	
	public void setPlanStartDate(Timestamp startDate){
		try {
			long oneDay = 24*60*60*1000;
			long endTime = startDate.getTime() + ((getDuration() - 1) * oneDay);
			Timestamp est = new Timestamp(endTime);
			getSchedule().setPlanStartDate(startDate);
			setPlanEndDate(est);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}
	}
	
	public void setPlanEndDate(Timestamp endDate){
			try {
				getSchedule().setPlanEndDate(endDate);
				
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			setDuration(getCalculationDuration());
	}
	
	
	
	public void setDuration(int duration){
		if(getDuration() != duration){
			try {
				getSchedule().setDuration(duration);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
		}
	}

	public ScheduleData getSchedule() {
		return schedule;
	}
	
	public void persist()throws Exception{
//		if(isScheduleChange){
//			Kogger.debug(getClass(), "ScheduleChanged....");
//			schedule = (ExtendScheduleData)PersistenceHelper.manager.save(schedule); 
//		} Kogger.debug(getClass(), getSchedule().getPlanStartDate() + " "  + startDate);
//		Kogger.debug(getClass(), toString()  + " duration = " + getDuration() + "  " + getSchedule().getPlanStartDate() );
		
		if(initDuration != getCalculationDuration() || initStdWork != getStdWork()){
			schedule = (ScheduleData)PersistenceHelper.manager.save(schedule);
		}
	}
	
	public Object getData(){
		return data;
	}

	public boolean isNotPlanTask() {
		// TODO Auto-generated method stub
		return isNotPlanTask;
	}
}
