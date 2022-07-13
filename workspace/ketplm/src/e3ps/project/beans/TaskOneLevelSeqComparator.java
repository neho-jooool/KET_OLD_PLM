package e3ps.project.beans;

import e3ps.project.TemplateTask;

public class TaskOneLevelSeqComparator implements java.util.Comparator{
	
	private boolean isAsc;
	public TaskOneLevelSeqComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public TaskOneLevelSeqComparator(){
	}
	
	public int compare(Object o1, Object o2) {
		TemplateTask t1 = (TemplateTask)o1;
		TemplateTask t2 = (TemplateTask)o2;
		
		int result = t1.getTaskSeq() - t2.getTaskSeq();
		
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
		
	}
	
}

