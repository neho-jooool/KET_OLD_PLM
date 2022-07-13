package e3ps.project.beans;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import e3ps.project.E3PSProject;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class DefaultProjectTreeNode extends DefaultMutableTreeNode {
    /**
     *
     */
    private static final long      serialVersionUID = -8712810323176852871L;
    private Vector                 preTasks         = new Vector();
    private Vector                 afterTasks       = new Vector();
    private TemplateTask           copyTask;


    public static final int        DELTE            = -1;
    public static final int        MODIFY           = 1;
    public static final int        ADD              = 2;
    public static final int        DEFAULT          = 0;
    private DefaultProjectTreeNode compareTreeNode;
    private TemplateTask           compareTask;
    private Hashtable              delayDurH        = new Hashtable();

    /*
     * Start [PLM System 1차개선] 수정내용 : 임시저장필드 추가, 2013. 07. 16, 김무준
     */
    public String                  tmpName          = null;
    public String                  tmpKey           = null;
    public String                  tmpId            = null;
    public String                  tmpPlanStartDate = null;
    public String                  tmpPlanEndDate   = null;
    /*
     * End [PLM System 1차개선] 수정내용 : 임시저장필드 추가, 2013. 07. 16, 김무준
     */

    private int                    compareResult    = ADD;

    public int getCompareResult() {
	return compareResult;
    }

    private void setCompareNode(DefaultProjectTreeNode node) {
	this.compareTreeNode = node;
    }

    public void setCompareResult(int compareResult) {
	this.compareResult = compareResult;
    }

    public void setCompareTask(TemplateTask compareTask) {
	this.compareTask = compareTask;
    }

    public TemplateTask getCompareTask() {
	return this.compareTask;
    }

    public DefaultProjectTreeNode(Object userObject) {
	super(userObject);
    }


    public void removeAllPreTask() {

	for (int i = 0; i < preTasks.size(); i++) {
	    DefaultProjectTreeNode node = (DefaultProjectTreeNode) preTasks.get(i);
	    node.removeAfterTask(this);
	}
	preTasks.clear();
	delayDurH.clear();
    }

    public void removeAllAfterTask() {
	for (int i = 0; i < afterTasks.size(); i++) {
	    DefaultProjectTreeNode node = (DefaultProjectTreeNode) afterTasks.get(i);
	    node.removePreTask(this);
	}
	afterTasks.clear();
    }

    public void removePreTask(DefaultProjectTreeNode node) {
	preTasks.remove(node);
	delayDurH.remove(node);
    }

    private void removeAfterTask(DefaultProjectTreeNode node) {
	afterTasks.remove(node);
    }

    private void addAfterTask(DefaultProjectTreeNode node) {
	afterTasks.add(node);
    }

    public void addPreTask(DefaultProjectTreeNode node) {

	preTasks.add(node);
	delayDurH.put(node, new Integer(0));
	node.addAfterTask(this);
    }

    public void addPreTask(DefaultProjectTreeNode node, int delayDuration) {
	preTasks.add(node);
	delayDurH.put(node, new Integer(delayDuration));
	node.addAfterTask(this);
    }

    public int getPreTaskDelayDuration(DefaultProjectTreeNode node) {

	Integer integer = (Integer) delayDurH.get(node);

	if (integer != null) {
	    return integer.intValue();
	}
	else {
	    return -1;
	}

    }

    public Vector getPreTasks() {
	return preTasks;
    }

    public Vector getAfterTasks() {
	return afterTasks;
    }

    public void setCopyTask(TemplateTask copyTask) {
	this.copyTask = copyTask;
    }

    public TemplateTask getCopyTask() {
	return copyTask;
    }

    public void validate_completion() {


    }

    public void validate_planSchedule() {
    }

    public void validate_dependency() {
    }

    public void validate_stdTime() {

	if (getChildCount() == 0) {
	    return;
	}

	int sum_stdWork = 0;

	TreeNodeData treeData = (TreeNodeData) getUserObject();

	for (int i = 0; i < getChildCount(); i++) {
	    DefaultProjectTreeNode childNode = (DefaultProjectTreeNode) getChildAt(i);
	    TreeNodeData child = (TreeNodeData) childNode.getUserObject();
	    if (!child.isNotPlanTask()) {
		int stdWork = child.getStdWork();
		sum_stdWork += stdWork;

	    }
	}
	treeData.setStdWork(sum_stdWork);

    }

    public static DefaultProjectTreeNode getLoadTree(TemplateProject fromTemplate) {

	DefaultProjectTreeNode node = null;
	try {
	    if (fromTemplate instanceof E3PSProject) {
		ProjectTreeModel model = new ProjectTreeModel((E3PSProject) fromTemplate);
		model.setTree();
		model.checkDependencySchedule();
		model.checkAllSchedule();
		node = model.getRoot();
	    }
	    else {
		TemplateProjectModel model = new TemplateProjectModel(fromTemplate);
		model.setTree();
		model.checkDependencySchedule();
		model.checkAllSchedule();
		node = model.getRoot();
	    }

	} catch (Exception e) {
	    Kogger.error(DefaultProjectTreeNode.class, e);
	}
	return node;
    }

    public Object tempObj;

    public void setTempObj(Object tempObj, boolean isParentApprove) {
	this.tempObj = tempObj;
	if (isParentApprove) {
	    DefaultProjectTreeNode pnode = (DefaultProjectTreeNode) this.getParent();
	    if (pnode != null) {
		pnode.setTempObj(tempObj, isParentApprove);
	    }
	}
    }
}
