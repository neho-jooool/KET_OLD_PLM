package e3ps.project.beans;

import e3ps.common.util.DateUtil;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateTask;

public class TaskPlanComparator implements java.util.Comparator{
	
	private boolean isAsc;
	public TaskPlanComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public TaskPlanComparator(){
	}
	
	public int compare(Object o1, Object o2) {
		TemplateTask t1 = null;
		TemplateTask t2 = null;
		
		String planStart1 = "";
		String planStart2 = "";
		
		String planEnd1 = "";
		String planEnd2 = "";
		
		
		
		
		
		if(o1 instanceof TemplateTask){
			
			t1 = (TemplateTask)o1;
		}
		if(o1 instanceof Object[]){
			Object oo[] = (Object[])o1;
			t1 = (TemplateTask)oo[0];
		}
		if(o2 instanceof TemplateTask){
			t2 = (TemplateTask)o2;
		}
		
		if(o2 instanceof Object[]){
			Object oo[] = (Object[])o2;
			t2 = (TemplateTask)oo[0];
		}
		
		
		if(o1 instanceof TemplateProjectTreeNode){
			TemplateProjectTreeNode node = (TemplateProjectTreeNode)o1;
			TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
			t1 = (TemplateTask)td.getData();
		}
		if(o1 instanceof ProjectTreeNode){
			ProjectTreeNode node = (ProjectTreeNode)o1;
			ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
			t1 = (TemplateTask)td.getData();
		}
		
		if(o2 instanceof TemplateProjectTreeNode){
			TemplateProjectTreeNode node = (TemplateProjectTreeNode)o2;
			TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
			t2 =  (TemplateTask)td.getData();
		}
		
		if(o2 instanceof ProjectTreeNode){
			ProjectTreeNode node = (ProjectTreeNode)o2;
			ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
			t2 = (TemplateTask)td.getData();
		}
		
		int seq1 = t1.getTaskSeq();
		int seq2 = t2.getTaskSeq();
		//seq1 = "" + t1.getTaskSeq();
		//seq2 = "" + t2.getTaskSeq();
		
		
		ScheduleData sd1 = (ScheduleData) t1.getTaskSchedule().getObject();
		
		planStart1 = DateUtil.getTimeFormat(sd1.getPlanStartDate(), "yyyyMMdd");
		planEnd1 = DateUtil.getTimeFormat(sd1.getPlanEndDate(), "yyyyMMdd");
		
		
		ScheduleData sd2 = (ScheduleData) t2.getTaskSchedule().getObject();
		
		planStart2 = DateUtil.getTimeFormat(sd2.getPlanStartDate(), "yyyyMMdd");
		planEnd2 = DateUtil.getTimeFormat(sd2.getPlanEndDate(), "yyyyMMdd");
		
		/*
		int result = planStart1.compareTo(planStart2);
		if(result == 0){
    		result = planEnd1.compareTo(planEnd2);
    	}
		*/
		int result = seq1 - seq2;
		
		
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
		
	}
	
}
