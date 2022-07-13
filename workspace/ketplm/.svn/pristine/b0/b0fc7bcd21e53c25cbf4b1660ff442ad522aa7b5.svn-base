package e3ps.project.beans;

import java.util.Hashtable;

import wt.fc.EnumeratedTypeUtil;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.project.ExtendScheduleData;

import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;

public class ScheduleHistoryHelper {
	public static final ScheduleHistoryHelper manager = new ScheduleHistoryHelper();
    private ScheduleHistoryHelper() {}
    
//	public HistoryManager saveHistory(JELProject project, String issueType, String desc) {
//		HistoryManager history = null;
//        try {
//        	ExtendScheduleData originalData = (ExtendScheduleData)project.getPjtSchedule().getObject();
//        	
//    		ExtendScheduleData historyData = ExtendScheduleData.newExtendScheduleData();
//    		historyData.setDuration(originalData.getDuration());
//    		historyData.setPlanStartDate(originalData.getPlanStartDate());
//    		historyData.setPlanEndDate(originalData.getPlanEndDate());
//    		historyData.setExecStartDate(originalData.getExecStartDate());
//    		historyData.setExecEndDate(originalData.getExecEndDate());
//    		historyData = (ExtendScheduleData)PersistenceHelper.manager.save(historyData);
//    		
//            HistoryType issue = (HistoryType)EnumeratedTypeUtil.toEnumeratedType(issueType);
//        	history = HistoryManager.newHistoryManager();
//        	history.setHistoryName(project.getPjtName());
////   			history.setCompletion(project.getPjtCompletion());
//   			if ( desc != null ) history.setHistoryDesc(desc);
//   			else  history.setHistoryDesc("");
//   			history.setHistoryType(issue);
////   			history.setProject(project);
////   			history.setSchedule(ObjectReference.newObjectReference(historyData));
//   			history = (HistoryManager)PersistenceHelper.manager.save(history);
//        } catch (Exception e) {
//            Kogger.error(getClass(), e);
//        }
//        return history;
//	}
	

//    public void saveHistory(JELProject project, HistoryManager projectHistory) {
//        try {
//        	ExtendScheduleData originalData = null;
//        	ExtendScheduleData historyData = null;
//            Hashtable totalTask = new Hashtable();
            
//            QueryResult qr = ProjectTaskHelper.manager.getChild(project);
//        	while ( qr != null && qr.hasMoreElements() ) {
//        		Object[] objArr = (Object[])qr.nextElement();		
//        		JELTask task = (JELTask)objArr[0];
//            	originalData = (ExtendScheduleData)task.getTaskSchedule().getObject();
//            	
//        		historyData = ExtendScheduleData.newExtendScheduleData();
//        		historyData.setDuration(originalData.getDuration());
//        		historyData.setPlanStartDate(originalData.getPlanStartDate());
//        		historyData.setPlanEndDate(originalData.getPlanEndDate());
//        		historyData.setExecStartDate(originalData.getExecStartDate());
//        		historyData.setExecEndDate(originalData.getExecEndDate());
//        		historyData = (ExtendScheduleData)PersistenceHelper.manager.save(historyData);
//        		
////        		TaskHistory taskHistory = TaskHistory.newTaskHistory();
////        		taskHistory.setName(task.getName());
////        		taskHistory.setCompletion(task.getCompletion());
////        		taskHistory.setSeq(task.getSeq());
////        		taskHistory.setProjectHistory(projectHistory);
////        		taskHistory.setSchedule(ObjectReference.newObjectReference(historyData));
////        		taskHistory = (TaskHistory)PersistenceHelper.manager.save(taskHistory);    
//        		
//        		QueryResult subQr = ProjectTaskHelper.manager.getChild(task);
//        		while ( subQr != null && subQr.hasMoreElements() ) {
//        			Object[] subObjArr = (Object[])subQr.nextElement();
//        			JELTask childTask = (JELTask)subObjArr[0];
//                	originalData = (ExtendScheduleData)childTask.getTaskSchedule().getObject();
//                	
//            		historyData = ExtendScheduleData.newExtendScheduleData();
//            		historyData.setDuration(originalData.getDuration());
//            		historyData.setPlanStartDate(originalData.getPlanStartDate());
//            		historyData.setPlanEndDate(originalData.getPlanEndDate());
//            		historyData.setExecStartDate(originalData.getExecStartDate());
//            		historyData.setExecEndDate(originalData.getExecEndDate());
//            		historyData = (ExtendScheduleData)PersistenceHelper.manager.save(historyData);
//        			
////        			TaskHistory subTaskHistory = TaskHistory.newTaskHistory();
////        			subTaskHistory.setName(childTask.getName());
////            		subTaskHistory.setCompletion(childTask.getCompletion());
////            		subTaskHistory.setSeq(childTask.getSeq());
////            		subTaskHistory.setProjectHistory(projectHistory);
////            		subTaskHistory.setSchedule(ObjectReference.newObjectReference(historyData));
////            		subTaskHistory.setParent(taskHistory);
////            		subTaskHistory = (TaskHistory)PersistenceHelper.manager.save(subTaskHistory);
//        		}
//        	}
//        } catch (Exception e) {
//            Kogger.error(getClass(), e);
//        }
//	}
}
