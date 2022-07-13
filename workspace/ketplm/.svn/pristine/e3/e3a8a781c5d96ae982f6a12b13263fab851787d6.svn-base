package e3ps.project.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.pds.DatabaseInfoUtilities;
import e3ps.common.db.DBCPManager;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.TaskDependencyLink;
//import e3ps.sap.PJTInfoERPInterface;
import ext.ket.shared.log.Kogger;

public class ProjectTreeModel implements RemoteAccess {

	public ProjectTreeNode root;
	
	public ArrayList levelList;
	
	private long rootId;
	public E3PSProject project;
	
	HashMap map = new HashMap();
	
	private boolean isRootProject;
	
	boolean isReverse;
	
	
	Hashtable tasks = new Hashtable();

	public ProjectTreeModel(E3PSProject project) throws Exception {
		
		isRootProject = true;

		this.project = project;
		rootId = project.getPersistInfo().getObjectIdentifier().getId();
		root = new ProjectTreeNode(new ProjectTreeNodeData(project));
		
		
		
		levelList = new ArrayList();
		ArrayList list = new ArrayList();
		
		list.add(root);
		levelList.add(list);
				
	}
	
	public ProjectTreeModel(E3PSTask task)throws Exception{
		
		this(task, false);
	}
	
	public ProjectTreeModel(E3PSTask task, boolean isReverse)throws Exception{
		this.isReverse = isReverse;
		
		rootId = task.getPersistInfo().getObjectIdentifier().getId();
		this.project = (E3PSProject)task.getProject();
		if(isReverse){
			if(task.getParent() != null){
				rootId = task.getParent().getPersistInfo().getObjectIdentifier().getId();
		
			}
		}
		
		
		root = new ProjectTreeNode(new ProjectTreeNodeData(task));
		
		levelList = new ArrayList();
		ArrayList list = new ArrayList();
		
		list.add(root);
		levelList.add(list);
	}
    
	public void setTree() throws Exception {

		String tableName = "";
		ClassInfo classinfo = WTIntrospector.getClassInfo(E3PSTask.class);
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
				classinfo, E3PSTask.TASK_SEQ);
		
		long id = project.getPersistInfo().getObjectIdentifier().getId();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		long start = System.currentTimeMillis();
		try {
			con = DBCPManager.getConnection("plm");
			
			String sql = "select level, " + taskOIDColumnName + " from " + tableName + " start with " + parentKeyColumnName + " = 0 and " 
			+ projectOIDColumnName + "= ?  connect by prior " + taskOIDColumnName + " = " + parentKeyColumnName + " order siblings by  " + taskSeqColumnName;
			HashMap temp = new HashMap();
			temp.put("0", root);
			if(!isRootProject){
				sql = "select level, " + taskOIDColumnName + " from " + tableName + " start with " + parentKeyColumnName + " = ? " +
						" connect by prior " + taskOIDColumnName + " = " + parentKeyColumnName + " order siblings by  " + taskSeqColumnName;
				if(isReverse){
					sql = "select level, " + taskOIDColumnName + " from " + tableName + " start with " + taskOIDColumnName + " = ? " +
					" connect by prior " + parentKeyColumnName + " = " + taskOIDColumnName;
					temp = new HashMap();
				}
			}
			
			st = con.prepareStatement(sql);
			
			st.setLong(1, rootId);

			rs = st.executeQuery();
			
			TaskPlanComparator comparator = new TaskPlanComparator();
			
			while (rs.next()) {	
				int level = rs.getInt(1);
				long ida = rs.getLong(2);
				
				ProjectTreeNodeData td = new ProjectTreeNodeData(level, ida);
				ProjectTreeNode node = new ProjectTreeNode(td);
				
				ArrayList list = new ArrayList();
				if (levelList.size() > level) {
					list = (ArrayList) levelList.get(level);
					list.add(node);
				} else {
					list.add(node);
					levelList.add(level, list);
				}
				
				map.put(td.oid, node);
				
				ProjectTreeNode pnode = (ProjectTreeNode) temp.get(Integer.toString(level - 1));
				if(pnode != null){
					pnode.add(node);
				}else{
					root.add(node);
				}
				
				ProjectTreeNode pre_parentNode = (ProjectTreeNode)temp.get(Integer.toString(level));
				if(pre_parentNode != null){
					pre_parentNode.sort(comparator);
				}
				
				temp.put(Integer.toString(level), node);

			}
			root.sort(comparator);
			
			sort(root, comparator);
			
			write(root);

			
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
		
		//Kogger.debug(getClass(), "tree time = " + (System.currentTimeMillis() - start));
	}
	
	public void sort(ProjectTreeNode node, Comparator c){
		node.sort(c);
		
		for(int i = 0; i < node.getChildCount(); i++){
			ProjectTreeNode childNode = (ProjectTreeNode)node.getChildAt(i);
			sort(childNode, c);
			
		}
	}
	
	public static ProjectTreeNode  getLoadTree(E3PSProject project){
		ProjectTreeNode node = null;
		try {
			ProjectTreeModel model = new ProjectTreeModel(project);
			model.setTree();
			//model.checkDependencySchedule(); 
			model.checkAllSchedule();
			node = model.getRoot();	
		} catch (Exception e) {
			
			Kogger.error(ProjectTreeModel.class, e);
		}
		return node;
	}
	
	public ProjectTreeNode getRoot(){
		return root;
	}
	
	public void write(ProjectTreeNode node){
		
		//Kogger.debug(getClass(), node.toString());
		
		for(int i = 0; i < node.getChildCount(); i++){
			write((ProjectTreeNode)node.getChildAt(i));
		}
	}
	
	public void setDependency()throws Exception{
		
		for (int i =0; i < levelList.size() ; i++) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
				ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
				
				if(!(td.getData() instanceof E3PSTask)){
					continue;
				}
				
				E3PSTask task = (E3PSTask)td.getData();
				QueryResult qr = PersistenceHelper.manager.navigate(task,
						TaskDependencyLink.DEPEND_TARGET_ROLE,
						TaskDependencyLink.class, false);
				
				while (qr.hasMoreElements()) {
					
					TaskDependencyLink link = (TaskDependencyLink)qr.nextElement();
					E3PSTask preTask = (E3PSTask)link.getDependTarget();// 선행테스크;
					
				//	Kogger.debug(getClass(), preTask.getTaskName() + "  " + task.getTaskName());
					String oid = CommonUtil.getOIDString(preTask);
					ProjectTreeNode preTreeNode = (ProjectTreeNode)map.get(oid);
					node.addPreTask(preTreeNode, link.getDelayDuration());
				}
			}
		}
		
	}

	public void updateSchedule(boolean isDependencyCheck) throws Exception {
		if(isDependencyCheck){
			checkDependencySchedule();
		} // 후행 테스크 먼저 시작일 정리  
		checkAllSchedule(); // 시작 종료일 상위 테스크 정리
		//checkStdWork(); //표준 공수 정리
		checkCompletion(); // 완료여부 완료율 정리
		checkExecDate();
		persist();
	}
	
	public void updateCompletion()throws Exception{
		//checkDependencySchedule();
		
		checkCompletion();
		checkExecDate();
		boolean isupdate = persist();
		Config conf = ConfigImpl.getInstance();				
		boolean isERPCheck = conf.getBoolean("ERPCHECK");
		
		try{
			if(isupdate){
				//Kogger.debug(getClass(), "ProjectTreeModel[updateCompletion]>>>>sendExdateAndCompletion");
//				try{
//				if(!PJTInfoERPInterface.sendProjectInfoToSap(project, System.out, false)){
//					Kogger.debug(getClass(), "updateCompletion error...");
//					
//					if(isERPCheck){
//						throw new Exception(" ERP error ");
//					}
//					
//				}
//				}catch(Exception ex){
//					Kogger.error(getClass(), ex);
//				}
			}
		}catch(Exception ex){
			Kogger.error(getClass(), ex);
		    
			if(isERPCheck){
				//throw new Exception(" ERP error ");
			}
			
		}
	}
	
	private boolean persist()throws Exception{
		boolean isupdate = false;
		for (int i =0; i < levelList.size() ; i++) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
				ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
				if(td.persist()){
					isupdate = true;
				}
			}
		}
		
		return isupdate;
		
	}
	
	private void checkExecDate() throws Exception {
			
			for (int i = levelList.size() - 1; i >= 0; i--) {
				ArrayList list = (ArrayList) levelList.get(i);
				for (int j = 0; j < list.size(); j++) {
					ProjectTreeNode node = (ProjectTreeNode)list.get(j);
					node.validate_execDate();
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

	private void checkCompletion() throws Exception {
		
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
				node.validate_completion();
			}
		}
		
		/*
		for (int i = levelList.size() - 1;  i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
			
				TreeData td = (TreeData) node.getUserObject();

				if (td.children.size() == 0) {
					continue; // WorkTask 마지막꺼는 체크 하지 말자.
				}

				boolean changeFlag = false;

				boolean nstate = true;

				int sum_duration = 0;
				int sum_completion = 0;

				for (int k = 0; k < td.children.size(); k++) {
					TreeData child = (TreeData) td.children.get(k);
					if (ProjectStateFlag.TASK_STATE_COMPLETE  != child
							.getState()) {
						nstate = false;
					}
					int duration = child.getDuration();
					sum_duration += duration;
					int comp = child.task.getTaskCompletion();
					sum_completion += (duration * comp);
				}

				int ncomp = 0;
				if (sum_completion > 0) {
					ncomp = sum_completion / sum_duration;
				}

				int dd = td.getCompletion();
				
				if (ncomp != dd) {
					if (td.task != null)
						td.task.setTaskCompletion(ncomp);
					else
						((JELProject) td.data).setPjtCompletion(ncomp);

					changeFlag = true;
				}

				int cc = td.getState();
				int nss = ProjectStateFlag.TASK_STATE_COMPLETE;
				if (!nstate) {
					nss = ProjectStateFlag.TASK_STATE_PROGRESS;
				}
				if (nss != cc) {
					if (td.task != null)
						td.task.setTaskState(nss);
					else
						((JELProject) td.data).setPjtState(nss);
					changeFlag = true;
				}

				if (changeFlag) {
					if (td.task != null) {
						td.task = (JELTask) PersistenceHelper.manager
								.modify(td.task);
						td.data = td.task;
					} else {
						td.data = PersistenceHelper.manager
								.modify((JELProject) td.data);
					}
				}
			}
		}*/
	}

	public void checkDependencySchedule() throws Exception {
		
		setDependency(); //선후행 노드 관계 정리
		
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
				node.validate_dependency();
			}
		}
	}

	public void checkAllSchedule() throws Exception {
		
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				ProjectTreeNode node = (ProjectTreeNode)list.get(j);
				node.validate_planSchedule();
			}
		}
/*
		for (int i = levelList.size() - 1; i >= 0; i--) {
			ArrayList list = (ArrayList) levelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TreeData td = (TreeData) list.get(j);

				if (td.children.size() == 0)
					continue; // WorkTask 마지막꺼는 체크 하지 말자.

				Timestamp ssd = td.getPlanStartDate();
				Timestamp eed = td.getPlanEndDate();

				boolean changeFlag = false;

				for (int k = 0; k < td.children.size(); k++) {
					TreeData child = (TreeData) td.children.get(k);
					Timestamp sd = child.getPlanStartDate();
					Timestamp ed = child.getPlanEndDate();

					if (ssd.getTime() > sd.getTime()) {
						ssd = sd;
						changeFlag = true;
					}

					if (eed.getTime() < ed.getTime()) {
						eed = ed;
						changeFlag = true;
					}
				}

				if (changeFlag) {
					ExtendScheduleData schedule = td.getSchedule();
					schedule.setPlanStartDate(ssd);
					schedule.setExecStartDate(ssd);
					schedule.setPlanEndDate(eed);
					PersistenceHelper.manager.modify(schedule);
				}
			}
		}*/
	}

	/*public void drawTree() throws Exception {
		for (int i = 0; i < seqList.size(); i++) {
			TreeData data = (TreeData) seqList.get(i);
			Kogger.debug(getClass(), data);
		}
	}*/

	
	public static void main(String args[])throws Exception{
		
		
		//RemoteMethodServer server = RemoteMethodServer.getDefault();
		//server.setUserName("wcadmin");
		//server.setPassword("wcadmin");
		
		//E3PSProject project = (E3PSProject)CommonUtil.getObject("e3ps.project.E3PSProject:10753104");
		
		
		//for(int i = 0; i < 1000; i++){
		
		//ProjectTreeNode node = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
			//Kogger.debug(getClass(), "test..... =  " + i);
		//}
		
		
		
		/*
		Calendar c = Calendar.getInstance();
		Timestamp tsp = new Timestamp(c.getTimeInMillis());
		
		c.add(Calendar.DATE, 30);
		
		Timestamp asp = new Timestamp(c.getTimeInMillis());
		Kogger.debug(getClass(), tsp.before(asp));*/
	}
}



/* $Log: not supported by cvs2svn $
/* Revision 1.5  2010/12/01 07:56:10  smkim
/* *** empty log message ***
/*
/* Revision 1.4  2010/11/19 06:44:13  kihkim
/* *** empty log message ***
/*
/* Revision 1.3  2010/11/01 07:49:58  smkim
/* *** empty log message ***
/*
/* Revision 1.2  2010/10/06 02:35:21  smkim
/* *** empty log message ***
/*
/* Revision 1.1  2010/09/10 04:40:50  syoh
/* 최초등록
/*
/* Revision 1.12  2010/01/26 06:25:57  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.11  2010/01/25 10:29:49  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.10  2009/12/23 11:10:28  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.9  2009/12/15 05:32:26  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.8  2009/12/15 01:51:43  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.7  2009/11/09 13:01:18  khchoi
/* 수정
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.6  2009/11/09 06:54:49  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.5  2009/11/09 02:10:57  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.4  2009/10/30 07:51:57  khchoi
/* 모델변경
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.3  2009/09/03 11:27:50  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.2  2009/09/01 10:59:45  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/08/11 04:16:20  administrator
/* Init Source
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/02/25 01:25:14  smkim
/* 최초 작성
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.28  2008/12/08 01:29:19  smkim
/* 수정
/*
/* Revision 1.27  2008/12/06 09:17:12  jspark
/* *** empty log message ***
/*
/* Revision 1.26  2008/12/05 08:34:17  administrator
/* *** empty log message ***
/*
/* Revision 1.25  2008/11/17 07:28:12  smkim
/* *** empty log message ***
/*
/* Revision 1.24  2008/09/19 02:49:12  sjhan
/* *** empty log message ***
/*
/* Revision 1.23  2008/09/17 05:46:59  sjhan
/* *** empty log message ***
/*
/* Revision 1.22  2008/09/16 05:09:49  smkim
/* *** empty log message ***
/*
/* Revision 1.21  2008/09/12 05:39:58  sjhan
/* *** empty log message ***
/*
/* Revision 1.20  2008/09/08 10:45:22  sjhan
/* *** empty log message ***
/*
/* Revision 1.19  2008/09/02 07:13:31  sjhan
/* *** empty log message ***
/*
/* Revision 1.18  2008/09/02 02:17:56  sjhan
/* *** empty log message ***
/*
/* Revision 1.17  2008/09/02 02:14:35  sjhan
/* *** empty log message ***
/*
/* Revision 1.16  2008/09/01 08:08:14  sjhan
/* *** empty log message ***
/*
/* Revision 1.15  2008/08/28 11:38:55  sjhan
/* *** empty log message ***
/*
/* Revision 1.14  2008/08/28 11:05:33  sjhan
/* *** empty log message ***
/*
/* Revision 1.13  2008/08/28 08:12:36  sjhan
/* *** empty log message ***
/*
/* Revision 1.12  2008/08/07 04:08:49  sjhan
/* *** empty log message ***
/*
/* Revision 1.11  2008/08/06 04:47:05  sjhan
/* *** empty log message ***
/*
/* Revision 1.10  2008/07/11 01:14:21  sjhan
/* *** empty log message ***
/*
/* Revision 1.9  2008/07/08 01:31:35  sjhan
/* *** empty log message ***
/*
/* Revision 1.8  2008/07/07 09:09:27  sjhan
/* *** empty log message ***
/*
/* Revision 1.7  2008/07/07 04:23:28  sjhan
/* *** empty log message ***
/*
/* Revision 1.6  2008/07/04 07:10:19  sjhan
/* *** empty log message ***
/*
/* Revision 1.5  2008/07/03 05:19:36  sjhan
/* khkim
/**/
