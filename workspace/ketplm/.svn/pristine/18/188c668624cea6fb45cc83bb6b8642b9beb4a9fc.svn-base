package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Vector;

import e3ps.common.util.DateUtil;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TemplateProjectTreeNode extends DefaultProjectTreeNode {
	
	public TemplateProjectTreeNode(Object userObject) {
		super(userObject);
	}
	
	public void sort(java.util.Comparator c){
		if(getChildCount() > 0){
			Collections.sort(this.children, c);
		}
	}
	
	public void validate_planSchedule(){
		Timestamp minTimestamp = null;
		Timestamp maxTimestamp = null;
		TemplateProjectTreeNodeData maxChild = null;
		for(int i = 0; i < getChildCount(); i++){
			TemplateProjectTreeNode childNode = (TemplateProjectTreeNode)getChildAt(i);
			TemplateProjectTreeNodeData child = (TemplateProjectTreeNodeData)childNode.getUserObject();
			if(child.isNotPlanTask()){
				continue;
			}
			
			Timestamp sd = child.getPlanStartDate();
			Timestamp ed = child.getPlanEndDate();

			if (minTimestamp == null || minTimestamp.after(sd)) {
				minTimestamp = sd;
			}

			if (maxTimestamp == null || maxTimestamp.before(ed)) {
				maxTimestamp = ed;
				maxChild = child;
			}
		}
		
		TemplateProjectTreeNodeData node = (TemplateProjectTreeNodeData)getUserObject();
		
		if(node.getData() instanceof TemplateTask){
		
			TemplateTask task = (TemplateTask)node.getData();
			
			if(task.getTaskName().equals("P1")){
				Kogger.debug(getClass(), ((TemplateTask)maxChild.getData()).getTaskName() + " === > " + DateUtil.getDateString(maxTimestamp, "all"));
			}
		}
		
		
		
		
		if(minTimestamp !=null && maxTimestamp != null){
			
			TemplateProjectTreeNodeData treeData = (TemplateProjectTreeNodeData)this.getUserObject();
			treeData.setPlanStartDate(minTimestamp);
			treeData.setPlanEndDate(maxTimestamp);
		}
	}
	
	public void validate_dependency(){
		
		Timestamp maxTimestamp = null;
		Vector preTasks = getPreTasks();
		for(int i = 0; i < preTasks.size(); i++){
			TemplateProjectTreeNode preNode = (TemplateProjectTreeNode)preTasks.get(i);
			TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)preNode.getUserObject();
			Timestamp endDate = td.getPlanEndDate();
			
			int delayDay = getPreTaskDelayDuration(preNode);
			
			Timestamp endDataWithDelayDuration =  DateUtil.getDelayTime(endDate, delayDay);
			
			
			if(maxTimestamp == null || maxTimestamp.before(endDataWithDelayDuration)){
				maxTimestamp = endDataWithDelayDuration;
			}
		}
		
		TemplateProjectTreeNodeData treeData = (TemplateProjectTreeNodeData)this.getUserObject();
		
		if(maxTimestamp != null){
			maxTimestamp = new Timestamp(maxTimestamp.getTime() + 24 * 60 * 60 * 1000);
		}
		
		if (maxTimestamp != null && treeData.getPlanStartDate().getTime() != maxTimestamp.getTime()) {
			
			treeData.setPlanStartDate(maxTimestamp);
			Vector afterTasks = getAfterTasks();
			for(int i = 0; i < afterTasks.size(); i++){
				TemplateProjectTreeNode afterNode = (TemplateProjectTreeNode)afterTasks.get(i);
				afterNode.validate_dependency();
			}
		}
	}
	
}

