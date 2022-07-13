package e3ps.project.beans;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import e3ps.common.util.DateUtil;
import e3ps.project.TemplateProject;
import ext.ket.shared.log.Kogger;

public class ProjectTreeNode extends DefaultProjectTreeNode {

    public ProjectTreeNode(Object userObject) {
	super(userObject);
    }

    public void sort(java.util.Comparator c) {
	if (getChildCount() > 0) {
	    Collections.sort(this.children, c);
	}
    }

    private Timestamp getToday() {
	Date todayDate = null;
	try {

	    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
	    todayDate = format.parse(DateUtil.getToDay());
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}
	Timestamp todayTimestamp = new Timestamp(todayDate.getTime());
	return todayTimestamp;
    }

    public void validate_execDate() {

	Timestamp todayTimestamp = getToday();

	ProjectTreeNodeData treeData = (ProjectTreeNodeData) this.getUserObject();

	if (treeData.isERPTask()) {
	    return;
	}

	if (getChildCount() == 0) {
	    return;
	}

	if (getChildCount() == 0) {

	    if (getPreTasks().size() > 0) {

		if (treeData.getState() != ProjectStateFlag.TASK_STATE_COMPLETE) {

		    Vector pnodes = getPreTasks();
		    boolean isPreComplated = true;

		    Timestamp maxExecTimestamp = null;

		    for (int j = 0; j < pnodes.size(); j++) {
			ProjectTreeNode pre_node = (ProjectTreeNode) pnodes.get(j);
			ProjectTreeNodeData ppdata = (ProjectTreeNodeData) pre_node.getUserObject();
			if (ppdata.getState() != ProjectStateFlag.TASK_STATE_COMPLETE) {
			    isPreComplated = false;
			    break;
			}

			Timestamp ed = ppdata.getExecEndDate();

			Timestamp endDataWithDelayDuration = null;

			if (ed != null) {
			    int delayDay = getPreTaskDelayDuration(pre_node);
			    endDataWithDelayDuration = DateUtil.getDelayTime(ed, delayDay);
			}
			if (endDataWithDelayDuration != null
			        && (maxExecTimestamp == null || maxExecTimestamp.before(endDataWithDelayDuration))) {
			    maxExecTimestamp = endDataWithDelayDuration;
			}
		    }

		    if (!isPreComplated) {

			treeData.setExecStartDate(null);

		    } else {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(maxExecTimestamp.getTime());
			c.add(Calendar.DATE, 1);
			Timestamp timestamp = new Timestamp(c.getTimeInMillis());
			treeData.setExecStartDate(timestamp);
		    }
		}
	    } else {

		if (treeData.getState() != ProjectStateFlag.TASK_STATE_COMPLETE) {

		    treeData.setExecStartDate(treeData.getPlanStartDate());

		}
	    }

	    return;
	}

	Timestamp minExecTimestamp = null;
	Timestamp maxExecTimestamp = null;

	boolean isCompleted = true;

	for (int i = 0; i < getChildCount(); i++) {
	    ProjectTreeNode childNode = (ProjectTreeNode) getChildAt(i);
	    ProjectTreeNodeData child = (ProjectTreeNodeData) childNode.getUserObject();

	    if (child.isNotPlanTask()) {
		continue;
	    }

	    Timestamp sd = child.getExecStartDate();
	    Timestamp ed = child.getExecEndDate();

	    if (sd != null && (minExecTimestamp == null || minExecTimestamp.after(sd))) {
		minExecTimestamp = sd;
	    }

	    if (ed != null && (maxExecTimestamp == null || maxExecTimestamp.before(ed))) {
		maxExecTimestamp = ed;
	    }

	    if (ProjectStateFlag.TASK_STATE_COMPLETE != child.getState()) {
		isCompleted = false;
	    }
	}

	int state = ProjectStateFlag.TASK_STATE_PROGRESS;

	if (isCompleted) {
	    // Kogger.debug(getClass(), "isCompleted=" + treeData.getName());
	    state = ProjectStateFlag.TASK_STATE_COMPLETE;
	}

	if (treeData.getState() != state) {
	    /*
	     * treeData.setExecEndDate(todayTimestamp);
	     * 
	     * Timestamp stamp = treeData.getExecStartDate();
	     * 
	     * if(stamp != null && stamp.after(todayTimestamp)){ treeData.setExecStartDate(todayTimestamp); }
	     */

	    treeData.setState(state);
	}

	// if(minExecTimestamp != null){
	treeData.setExecStartDate(minExecTimestamp);
	// }

	if (isCompleted && maxExecTimestamp != null) {
	    treeData.setExecEndDate(maxExecTimestamp);
	} else {
	    // Kogger.debug(getClass(), "isCompleted not=" + treeData.getName());
	    treeData.setExecEndDate(null);
	}
    }

    public void validate_completion() {

//	boolean isCompleted = true;
	int sum_duration = 0;
	float sum_completion = 0;
	float completion = 0;
	ProjectTreeNodeData treeData = (ProjectTreeNodeData) this.getUserObject();
	boolean isProject = (treeData.getData() instanceof TemplateProject);
	// if(isProject){
	// Kogger.debug(getClass(), "Project Child == " + getChildCount());
	// }

	if (treeData.isERPTask()) {
	    return;
	}

	Timestamp todayTimestamp = getToday();

	if (treeData.getCompletion() == 100 && treeData.getExecEndDate() == null
	        && treeData.getState() == ProjectStateFlag.TASK_STATE_COMPLETE) {

	    // int state = ProjectStateFlag.TASK_STATE_COMPLETE;
	    // if(treeData.getState() != state){
	    // treeData.setState(state);
	    treeData.setExecEndDate(todayTimestamp);

	    Timestamp stamp = treeData.getExecStartDate();
	    if (stamp != null && stamp.after(todayTimestamp)) {
		treeData.setExecStartDate(todayTimestamp);
	    }
	    // }
	}

	if (getChildCount() == 0) {
	    return;
	}

	for (int i = 0; i < getChildCount(); i++) {
	    ProjectTreeNode childNode = (ProjectTreeNode) getChildAt(i);
	    ProjectTreeNodeData child = (ProjectTreeNodeData) childNode.getUserObject();
	    if (child.isNotPlanTask()) {
		// Kogger.debug(getClass(), "ggggggggggggg.................kkkkggggggggggggggggggggggggggggggggggggg");
		continue;
	    }

//	    if (ProjectStateFlag.TASK_STATE_COMPLETE != child.getState()) {
//		isCompleted = false;
//	    }
	    int duration = child.getDuration();
	    sum_duration += duration;
	    int comp = child.getCompletion();
	    sum_completion += (duration * comp);
	}

	if (sum_completion > 0) {
	    completion = (sum_completion) / sum_duration;
	    // Kogger.debug(getClass(), "completion = " + completion);
	}

//	if (isProject) {
//	    if(completion == 100 && !isCompleted){//종료되지 않은 Task가 있을 때 프로젝트를 100%로 만드는 것을 방지한다
//		completion = 99;
//	    }
//	    Kogger.debug(getClass(), "Project Completion == " + Math.round((float) completion));
//	}

	treeData.setCompletion(Math.round((float) completion));

    }

    public void validate_planSchedule() {
	Timestamp minPlanTimestamp = null;
	Timestamp maxPlanTimestamp = null;

	for (int i = 0; i < getChildCount(); i++) {
	    ProjectTreeNode childNode = (ProjectTreeNode) getChildAt(i);
	    ProjectTreeNodeData child = (ProjectTreeNodeData) childNode.getUserObject();
	    if (child.isNotPlanTask()) {
		continue;
	    }
	    Timestamp sd = child.getPlanStartDate();
	    Timestamp ed = child.getPlanEndDate();

	    if (minPlanTimestamp == null || minPlanTimestamp.after(sd)) {
		minPlanTimestamp = sd;
	    }

	    if (maxPlanTimestamp == null || maxPlanTimestamp.before(ed)) {
		maxPlanTimestamp = ed;
	    }
	}

	if (minPlanTimestamp != null && maxPlanTimestamp != null) {

	    ProjectTreeNodeData treeData = (ProjectTreeNodeData) this.getUserObject();

	    // Kogger.debug(getClass(), treeData + " " + minTimestamp + "," + maxTimestamp);

	    treeData.setPlanStartDate(minPlanTimestamp);
	    treeData.setPlanEndDate(maxPlanTimestamp);
	}
    }

    public void validate_dependency() {

	Timestamp maxTimestamp = null;
	Vector preTasks = getPreTasks();
	for (int i = 0; i < preTasks.size(); i++) {
	    ProjectTreeNode preNode = (ProjectTreeNode) preTasks.get(i);
	    ProjectTreeNodeData td = (ProjectTreeNodeData) preNode.getUserObject();
	    Timestamp endDate = td.getPlanEndDate();

	    int delayDay = getPreTaskDelayDuration(preNode);

	    Timestamp endDataWithDelayDuration = DateUtil.getDelayTime(endDate, delayDay);

	    if (maxTimestamp == null || maxTimestamp.before(endDataWithDelayDuration)) {
		maxTimestamp = endDataWithDelayDuration;
	    }
	}

	ProjectTreeNodeData treeData = (ProjectTreeNodeData) this.getUserObject();

	if (maxTimestamp != null) {
	    maxTimestamp = new Timestamp(maxTimestamp.getTime() + 24 * 60 * 60 * 1000);
	}

	if (maxTimestamp != null && treeData.getPlanStartDate().getTime() != maxTimestamp.getTime()) {
	    treeData.setMovePlanStartDate(maxTimestamp);
	    Vector afterTasks = getAfterTasks();
	    for (int i = 0; i < afterTasks.size(); i++) {
		ProjectTreeNode afterNode = (ProjectTreeNode) afterTasks.get(i);
		afterNode.validate_dependency();
	    }
	}
    }

}
