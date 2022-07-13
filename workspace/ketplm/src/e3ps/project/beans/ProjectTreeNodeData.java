package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.Hashtable;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import ext.ket.shared.log.Kogger;

public class ProjectTreeNodeData implements  TreeNodeData{
	/**
	 *
	 */
	private static final long serialVersionUID = -3750410880918591378L;

	public int level;

	public String oid;

	private Object data;

	boolean isScheduleChange;

	boolean isTaskOrProjectChange;

	ExtendScheduleData schedule;

	private boolean isNotPlanTask = false;

	private boolean isERPTask = false;

	int initStdWork;

	public ProjectTreeNodeData(int level, long id, Hashtable tasks)throws Exception{
		this.level = level;
		this.oid = E3PSTask.class.getName() + ":" + id;

		if(tasks.containsKey(oid)){
			data = tasks.get(oid);
			//Kogger.debug(getClass(), "containsKey=== >" + oid);
		}else{
			data = new ReferenceFactory().getReference(oid).getObject();
			//Kogger.debug(getClass(), "not .l...containsKey=== >" + oid);
		}
		init();
	}

	public ProjectTreeNodeData(int level, long id) throws Exception {
		this.level = level;
		this.oid = E3PSTask.class.getName() + ":" + id;
		data = new ReferenceFactory().getReference(oid).getObject();
		init();
	}

	public ProjectTreeNodeData(E3PSTask task) throws Exception {

		this.oid = CommonUtil.getOIDString(task);
		data = task;
		init();
	}

	public Object getData(){
		return data;
	}

	public ProjectTreeNodeData(E3PSProject project) {
		this.level = 0;
		this.data = project;
		this.oid = project.getPersistInfo().getObjectIdentifier().toString();
		init();
	}

	void init(){

		if (data instanceof E3PSProject) {
			E3PSProject pro = (E3PSProject) data;
			schedule = (ExtendScheduleData) pro.getPjtSchedule().getObject();
		} else {
			E3PSTask tas = (E3PSTask) data;
			schedule = (ExtendScheduleData) tas.getTaskSchedule().getObject();
			initStdWork = schedule.getStdWork();
		}
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

	public String getName(){
		if (data instanceof E3PSProject) {
			E3PSProject pro = (E3PSProject) data;
			return pro.getPjtName();
		} else {
			E3PSTask tas = (E3PSTask) data;
			return tas.getTaskName();
		}
	}

	public String toString() {
		StringBuffer ss = new StringBuffer();
		for (int i = 0; i < level; i++) {
			ss.append("..");
		}

		if (data instanceof E3PSProject) {
			E3PSProject pro = (E3PSProject) data;
			ss.append(pro.getPjtName());
		} else {
			E3PSTask tas = (E3PSTask) data;
			ss.append(tas.getTaskName());
		}
		return ss.toString();
	}

	public int getDuration() {
		return DateUtil.getDuration(getPlanStartDate(), getPlanEndDate()) + 1;
	}

	public Timestamp getPlanStartDate() {
		return getSchedule().getPlanStartDate();
	}

	public Timestamp getPlanEndDate() {
		return getSchedule().getPlanEndDate();
	}

	public Timestamp getExecStartDate() {
		return getSchedule().getExecStartDate();
	}

	public Timestamp getExecEndDate() {
		return getSchedule().getExecEndDate();
	}

	public void setMovePlanStartDate(Timestamp startDate){
		Timestamp ost = getPlanStartDate();

		long moveLength = ost.getTime() - startDate.getTime();
		Timestamp est = getPlanEndDate();
		long nest = est.getTime() - moveLength;
		est = new Timestamp(nest);

		setPlanStartDate(startDate);
		//setExecStartDate(startDate);
		setPlanEndDate(est);
	}


	public void setPlanStartDate(Timestamp startDate){
		if(getSchedule().getPlanEndDate() == null || getSchedule().getPlanStartDate().getTime() != startDate.getTime()){

			try {
				getSchedule().setPlanStartDate(startDate);
				getSchedule().setDuration(getDuration());
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
		}
	}

	public void setPlanEndDate(Timestamp endDate){


		if(getSchedule().getPlanEndDate() == null || getSchedule().getPlanEndDate().getTime() != endDate.getTime()){


			try {
				getSchedule().setPlanEndDate(endDate);
				getSchedule().setDuration(getDuration());
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
		}
	}

	public void setExecStartDate(Timestamp execStartDate){


		if(getSchedule().getExecStartDate() == null &&  execStartDate == null){
			return;
		}

		if(execStartDate == null || getSchedule().getExecStartDate() == null){
			try {
				getSchedule().setExecStartDate(execStartDate);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
			return;
		}

		if(getSchedule().getExecStartDate().getTime() != execStartDate.getTime()){
			try {
				getSchedule().setExecStartDate(execStartDate);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
		}
	}

	public void setExecEndDate(Timestamp execEndDate){
		if(getSchedule().getExecEndDate() == null &&  execEndDate == null){
			return;
		}

		if(execEndDate == null || getSchedule().getExecEndDate() == null){
			try {
				getSchedule().setExecEndDate(execEndDate);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
			return;
		}

		if(getSchedule().getExecEndDate().getTime() != execEndDate.getTime()){
			try {
				getSchedule().setExecEndDate(execEndDate);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
			isScheduleChange = true;
		}
	}


	public void setState(int state){

		if(getState() != state){

			if (data instanceof E3PSProject) {
				E3PSProject pro = (E3PSProject) data;
				try {
					pro.setPjtState(state);
				} catch (WTPropertyVetoException e) {

					Kogger.error(getClass(), e);
				}
			} else {
				E3PSTask tas = (E3PSTask) data;
				try {
					tas.setTaskState(state);
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
				}
			}
			isTaskOrProjectChange = true;

		}

	}

	public void setCompletion(int completion){

		if(getCompletion() != completion){
			if (data instanceof E3PSProject) {
				E3PSProject pro = (E3PSProject) data;
				try {
					pro.setPjtCompletion(completion);
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
				}
			} else {
				E3PSTask tas = (E3PSTask) data;
				try {
					tas.setTaskCompletion(completion);
				} catch (WTPropertyVetoException e) {
					Kogger.error(getClass(), e);
				}
			}


			isTaskOrProjectChange = true;

		}

	}



	public int getState() {

		if (data instanceof E3PSProject) {
			E3PSProject pro = (E3PSProject) data;
			return pro.getPjtState();
		} else {
			E3PSTask tas = (E3PSTask) data;
			return tas.getTaskState();
		}
	}

	public int getCompletion() {

		if (data instanceof E3PSProject) {
			E3PSProject pro = (E3PSProject) data;
			return pro.getPjtCompletion();
		} else {
			E3PSTask tas = (E3PSTask) data;
			return tas.getTaskCompletion();
		}
	}

	public ExtendScheduleData getSchedule() {
		return schedule;
	}

	public boolean persist()throws Exception{

		isScheduleChange = (isScheduleChange || initStdWork != getStdWork());
		if(isScheduleChange){

			schedule = (ExtendScheduleData)PersistenceHelper.manager.save(schedule);
			//Kogger.debug(getClass(), "udate schedule " + toString());
		}

		if(isTaskOrProjectChange){
			//Kogger.debug(getClass(), "udate task " + toString());
			data = PersistenceHelper.manager.save((Persistable)data);
		}

		return isScheduleChange || isTaskOrProjectChange;

	}

	public boolean isNotPlanTask() {
		// TODO Auto-generated method stub
		return isNotPlanTask;
	}

	public boolean isERPTask(){
		return isERPTask;
	}

}
