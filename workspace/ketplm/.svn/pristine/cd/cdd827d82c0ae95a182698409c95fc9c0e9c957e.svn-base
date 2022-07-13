package e3ps.project.beans;

import e3ps.project.TemplateTask;

public class TaskSeqComparator implements java.util.Comparator{
	
	private boolean isAsc;
	public TaskSeqComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public TaskSeqComparator(){
	}
	
	public int compare(Object o1, Object o2) {
		TemplateTask t1 = null;
		TemplateTask t2 = null;
		
		
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
		
		int result = t1.getTaskSeq() - t2.getTaskSeq();
		
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
		
	}
	
}

