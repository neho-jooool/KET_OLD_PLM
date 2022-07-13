package e3ps.project.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.pds.DatabaseInfoUtilities;
import e3ps.common.db.DBCPManager;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TemplateProjectModel implements RemoteAccess {

	public TemplateProjectTreeNode root;

	public ArrayList levelList;

	public TemplateProject project;

	public Timestamp startPlanDate;

	private HashMap map = new HashMap();

	//private boolean isReverse;

	private boolean isRootTask;

	private long rootId;

	public TemplateProjectModel(TemplateProject project) throws Exception {

		this(project, null);
	}

	public TemplateProjectModel(TemplateTask task)throws Exception{


		isRootTask = true;
		rootId = task.getPersistInfo().getObjectIdentifier().getId();
		//
		this.project = (TemplateProject)task.getProject();


		root = new TemplateProjectTreeNode(new TemplateProjectTreeNodeData(task));

		levelList = new ArrayList();
		ArrayList list = new ArrayList();

		list.add(root);
		levelList.add(list);
	}

	public TemplateProjectModel(TemplateProject project, Timestamp startPlanDate) throws Exception {

		rootId = project.getPersistInfo().getObjectIdentifier().getId();
		this.startPlanDate =  startPlanDate;
		this.project = project;
		root = new TemplateProjectTreeNode(new TemplateProjectTreeNodeData(project, startPlanDate));
		levelList = new ArrayList();
		ArrayList list = new ArrayList();
		list.add(root);
		levelList.add(list);
	}

	public TemplateProjectTreeNode getRoot(){
		return root;
	}

	public void setTree() throws Exception {

		long start = System.currentTimeMillis();


		String tableName = "";
		ClassInfo classinfo = WTIntrospector.getClassInfo(TemplateTask.class);
		boolean isTemplateProject = true;
		if(project instanceof ProductProject){
		    classinfo = WTIntrospector.getClassInfo(E3PSTask.class);
		    isTemplateProject = false;
		}
		if (DatabaseInfoUtilities.isAutoNavigate(classinfo)) {
			tableName = DatabaseInfoUtilities.getBaseTableName(classinfo);
		} else {
			tableName = DatabaseInfoUtilities.getValidTableName(classinfo);
		}

		String parentKeyColumnName = DatabaseInfoUtilities.getValidColumnName(
				classinfo, "parentReference.key.id");
		String projectOIDColumnName = DatabaseInfoUtilities.getValidColumnName(
				classinfo, "projectReference.key.id");
		String taskOIDColumnName = DatabaseInfoUtilities.getValidColumnName(
				classinfo, "thePersistInfo.theObjectIdentifier.id");
		String taskSeqColumnName = DatabaseInfoUtilities.getValidColumnName(
				classinfo, TemplateTask.TASK_SEQ);

		//long id = project.getPersistInfo().getObjectIdentifier().getId();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			con = DBCPManager.getConnection("plm");
			String sql = "select level, " + taskOIDColumnName + " from " + tableName + " start with " + parentKeyColumnName + " = 0 and "
			+ projectOIDColumnName + "= ?  connect by prior " + taskOIDColumnName + " = " + parentKeyColumnName + " order siblings by  " + taskSeqColumnName;

			if(isRootTask){
				sql = "select level, " + taskOIDColumnName + " from " + tableName + " start with " + parentKeyColumnName + " = ? " +
					" connect by prior " + taskOIDColumnName + " = " + parentKeyColumnName + " order siblings by  " + taskSeqColumnName;
			}

			st = con.prepareStatement(sql);
			st.setLong(1, rootId);

			rs = st.executeQuery();
			HashMap temp = new HashMap();
			temp.put("0", root);
			TaskSeqComparator comparator = new TaskSeqComparator();

			while (rs.next()) {
				int level = rs.getInt(1);
				long ida = rs.getLong(2);

				TemplateProjectTreeNodeData td = null;
				
				if(isTemplateProject){
				    td = new TemplateProjectTreeNodeData(level, ida, startPlanDate);
				}else{
				    String id = E3PSTask.class.getName() + ":" + ida;
				    td = new TemplateProjectTreeNodeData(level, id, startPlanDate);
				}
					
					
				TemplateProjectTreeNode node = new TemplateProjectTreeNode(td);

				ArrayList list = new ArrayList();
				if (levelList.size() > level) {
					list = (ArrayList) levelList.get(level);
					list.add(node);
				} else {
					list.add(node);
					levelList.add(level, list);
				}

				map.put(td.oid, node);

				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) temp.get(Integer.toString(level - 1));
				pnode.add(node);

				TemplateProjectTreeNode pre_parentNode = (TemplateProjectTreeNode)temp.get(Integer.toString(level));
				if(pre_parentNode != null){
					pre_parentNode.sort(comparator);
				}
				temp.put(Integer.toString(level), node);
			}

			root.sort(comparator);

			write(root);

			//Kogger.debug(getClass(), "time = " + (System.currentTimeMillis() - start));



		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new Exception(e);
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }

		}
	}


	public void setDependency()throws Exception{

		for (int i =0; i < levelList.size() ; i++) {

			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(j);

				TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();

				if(!(td.getData() instanceof TemplateTask)){
					continue;
				}

				TemplateTask task = (TemplateTask)td.getData();

				QueryResult qr = PersistenceHelper.manager.navigate(task,
						TaskDependencyLink.DEPEND_TARGET_ROLE,
						TaskDependencyLink.class, false);

				while (qr.hasMoreElements()) {

					TaskDependencyLink link = (TaskDependencyLink)qr.nextElement();
					TemplateTask preTask = (TemplateTask) link.getDependTarget(); // 선행테스크;

				//	Kogger.debug(getClass(), preTask.getTaskName() + "  " + task.getTaskName());
					String oid = CommonUtil.getOIDString(preTask);
					TemplateProjectTreeNode preTreeNode = (TemplateProjectTreeNode)map.get(oid);
					node.addPreTask(preTreeNode, link.getDelayDuration());
				}
			}
		}

	}

	// [PLM System 1차개선] checkDependencySchedule() 주석 처리, 2013-08-06, BoLee
	public void updateDuration() throws Exception {

//		checkDependencySchedule();  // [PLM System 1차개선] 선후행 관계 허용 조건 변경으로 주석 처리, 2013-08-06, BoLee
		checkAllSchedule();
		checkStdWork(); //표준 공수 정리
		allPersist();
	}

	public static TemplateProjectTreeNode  getLoadTree(TemplateProject project, Timestamp startPlanDate){
		TemplateProjectTreeNode node = null;
		try {
			TemplateProjectModel model = new TemplateProjectModel(project, startPlanDate);
			model.setTree();
			model.checkDependencySchedule();
			model.checkAllSchedule();
			node = model.getRoot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Kogger.error(TemplateProjectModel.class, e);
		}
		return node;
	}

	public static TemplateProjectTreeNode getLoadTreeNode(TemplateTask task){

		TemplateProjectTreeNode node = null;
		try {
			TemplateProjectModel model = new TemplateProjectModel(task);
			model.setTree();
			//model.checkDependencySchedule();
			//model.checkAllSchedule();
			node = model.getRoot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Kogger.error(TemplateProjectModel.class, e);
		}
		return node;

	}

	public void checkDependencySchedule() throws Exception {
		setDependency();
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(j);
				node.validate_dependency();
			}
		}

	}
	public void checkAllSchedule() throws Exception {

		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(j);
				node.validate_planSchedule();
			}
		}
	}

	private void checkStdWork()throws Exception{
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				DefaultProjectTreeNode node = (DefaultProjectTreeNode)list.get(j);
				node.validate_stdTime();
			}
		}
	}

	private void allPersist()throws Exception{
		for (int i =0; i < levelList.size() ; i++) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(j);
				TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
				td.persist();
			}
		}

	}

	public void write(TemplateProjectTreeNode node){

		//Kogger.debug(getClass(), node.toString());

		for(int i = 0; i < node.getChildCount(); i++){
			write((TemplateProjectTreeNode)node.getChildAt(i));
		}
	}

	public void drawTree() throws Exception {
		/*for (int i = 0; i < seqList.size(); i++) {
			TemplateData data = (TemplateData) seqList.get(i);
			Kogger.debug(getClass(), data);
		}*/
	}

	public void drawTree2(TemplateProjectTreeNodeData data) throws Exception {

		Kogger.debug(getClass(), data);
		for (int i = 0; i < data.children.size(); i++) {
			TemplateProjectTreeNodeData cc = (TemplateProjectTreeNodeData) data.children.get(i);
			drawTree2(cc);
		}
	}

	public static int test(int total){
		Kogger.debug(TemplateProjectModel.class, "call");
		if(total < 3){
			total +=1;
			return test(total);
		}
		return total;


	}

	public static void main(String args[]){
		Kogger.debug(TemplateProjectModel.class, test(1));
	}

}

