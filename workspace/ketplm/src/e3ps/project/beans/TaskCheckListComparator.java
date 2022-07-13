package e3ps.project.beans;

import wt.fc.QueryResult;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.ScheduleData;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TaskCheckListComparator implements java.util.Comparator{

	private boolean isAsc;
	public TaskCheckListComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public TaskCheckListComparator(){
	}
	
	public int compare(Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
		Object o1[] = (Object[])arg1;
		Object o2[] = (Object[])arg2;
		
		TemplateTask task1 = (TemplateTask)o1[0];
		TemplateTask task2 = (TemplateTask)o2[0];
		
		
		String projectName1 = task1.getProject().getPjtName();
		String projectName2 = task2.getProject().getPjtName();
		String taskMasterUser1 = "";
		String taskMasterUser2 = "";
		
		
		QueryResult qr_task_mater1 = TaskUserHelper.manager.getMaster(task1);
		QueryResult qr_task_mater2 = TaskUserHelper.manager.getMaster(task2);
		
		try{
		if(qr_task_mater1.hasMoreElements()){
			Object object[]=(Object[])qr_task_mater1.nextElement();
			TaskMemberLink link = (TaskMemberLink)object[0];
			PeopleData data = new PeopleData(link.getMember().getMember());
			taskMasterUser1 = data.name;
		}
		
		if(qr_task_mater2.hasMoreElements()){
			Object object[]=(Object[])qr_task_mater2.nextElement();
			TaskMemberLink link = (TaskMemberLink)object[0];
			PeopleData data = new PeopleData(link.getMember().getMember());
			taskMasterUser2 = data.name;
		}
		
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		
		if(projectName1.compareTo(projectName2) != 0){
			return projectName1.compareTo(projectName2);
		}
		
		if(taskMasterUser1.compareTo(taskMasterUser2) != 0){
			return taskMasterUser1.compareTo(taskMasterUser2);
		}
		
		
		String planStart1 = "";
		String planStart2 = "";
		
		String planEnd1 = "";
		String planEnd2 = "";
		
		ScheduleData sd1 = (ScheduleData) task1.getTaskSchedule().getObject();
		
		planStart1 = DateUtil.getTimeFormat(sd1.getPlanStartDate(), "yyyyMMdd");
		planEnd1 = DateUtil.getTimeFormat(sd1.getPlanEndDate(), "yyyyMMdd");
		
		
		ScheduleData sd2 = (ScheduleData) task2.getTaskSchedule().getObject();
		
		planStart2 = DateUtil.getTimeFormat(sd2.getPlanStartDate(), "yyyyMMdd");
		planEnd2 = DateUtil.getTimeFormat(sd2.getPlanEndDate(), "yyyyMMdd");
		
		int result = planStart1.compareTo(planStart2);
		if(result == 0){
			
    		result = planEnd1.compareTo(planEnd2);
    	}
		
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
	}
}
