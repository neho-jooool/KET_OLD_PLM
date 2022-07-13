package e3ps.load.project;

import java.io.File;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTProperties;
import e3ps.common.util.JExcelUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.beans.ProjectScheduleHelper;
import e3ps.project.beans.ProjectStateFlag;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.shared.log.Kogger;

public class ProgressTaskDataLoader {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	//private static String FILESERVER = "\\\\192.168.17.13";
	private static String EXCELFILE = "";
	private static String SEPARATOR = File.separator;
	private static String logFile = "moldProjectLoader.log";
	
	static {
		
		
		try {
			String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
			EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\project";
		}
		catch(Exception e) {
			Kogger.error(ProgressTaskDataLoader.class, e);
		}
	}
	
	public static boolean loadFile(String fileName) throws Exception {
			
		String filePath = EXCELFILE + SEPARATOR + fileName;
		File dataFile = new File(filePath);
		if(!dataFile.exists()) {
			Kogger.debug(ProgressTaskDataLoader.class, "File not found!!!");
			return false;
		}
		
		logFile = EXCELFILE + SEPARATOR;
		int pidx = fileName.lastIndexOf(".");
		if(pidx > 0) {
			logFile += fileName.substring(0, pidx);
		} else {
			logFile = fileName;
		}
		logFile += ".txt";
		
		Workbook wb = Workbook.getWorkbook(dataFile);
		Sheet sheets[] = wb.getSheets();
		Sheet sheet = sheets[0];
		int startRow = 3;
		
		Vector datas = new Vector();
		
		
		boolean result = true;
		Hashtable checkH = new Hashtable();
		Hashtable projectH = new Hashtable();
		Hashtable taskH = new Hashtable();
		for(int i = startRow; i < 7; i++){
			Cell[] cell = sheet.getRow(i);
			Object obj = checkCellData(cell, i, projectH, taskH);
			if(obj instanceof StringBuffer){
				StringBuffer sb = (StringBuffer)obj;
				Kogger.debug(ProgressTaskDataLoader.class, sb.toString());
				//log(sb.toString());
				result = false;
			}else{
				datas.add(obj);
			}
		}
		
		if(result){
			upload(datas, projectH);
		}
		
		Enumeration e = projectH.keys();
		while(e.hasMoreElements()){
			Object key = e.nextElement();
			E3PSProject project = (E3PSProject)projectH.get(key);
			ProjectScheduleHelper.manager.post_modify_Schedule(project, false);
		}
		
		return result;
	}
	
	public static void upload(Vector datas, Hashtable projectH)throws Exception{
		for(int i = 0; i < datas.size(); i++){
			HashMap map = (HashMap)datas.get(i);
			
	/*		map.put("taskType", taskType);
			map.put("taskName", taskName);
			map.put("projectNo", projectNo);
			map.put("mileStone", mileStone);
			map.put("des", des);
			map.put("taskId", taskId);
			map.put("parentTaskId", parentTaskId);
			map.put("startPlan", startPlan);
			map.put("endPlan", endPlan);
			map.put("exStartPlan", exStartPlan);
			map.put("exEndPlan", exEndPlan);
			map.put("parentTaskId", parentTaskId);
			*/
			
			String projectNo = (String)map.get("projectNo");
			String mileStone = (String)map.get("mileStone");
			String des = (String)map.get("des");
			String taskName = (String)map.get("taskName");
			
			String taskId = (String)map.get("taskId");
			String parentTaskId = (String)map.get("parentTaskId");
			String taskType = (String)map.get("taskType");
			
			
			Timestamp startPlan = (Timestamp)map.get("startPlan");
			Timestamp endPlan = (Timestamp)map.get("endPlan");
			
			Timestamp exStartPlan = (Timestamp)map.get("exStartPlan");
			Timestamp exEndPlan = (Timestamp)map.get("exEndPlan");
			
			E3PSProject project = (E3PSProject)projectH.get(projectNo);
			
			E3PSTask parentTask = getTask(project, "C" + parentTaskId);
			
			
			ExtendScheduleData data = null;
			E3PSTask task = null;
			
			task = getTask(project, "C" + taskId);
			
			int maxSeq = 0;
			
			if(task == null){
				task = E3PSTask.newE3PSTask();
				data = ExtendScheduleData.newExtendScheduleData();
				maxSeq = ProjectTaskHelper.getMaxSeq(null, project);
				
			}else{
				maxSeq = task.getTaskSeq();
				data = (ExtendScheduleData)task.getTaskSchedule().getObject();
			}
			
			data.setPlanStartDate(startPlan);
			data.setPlanEndDate(endPlan);
			data.setExecStartDate(exStartPlan);
			data.setExecEndDate(exEndPlan);
			if(exEndPlan != null){
				task.setTaskCompletion(100);
				task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
			}
			
			data = (ExtendScheduleData)PersistenceHelper.manager.save(data);
			
			task.setTaskName(taskName);
			task.setTaskDesc(des);
			task.setTaskSchedule(ObjectReference.newObjectReference(data));
			task.setProject(project);
			if(parentTask != null){
				task.setParent(parentTask);
			}
			task.setTaskSeq(maxSeq);
			task.setTaskNo("C" + taskId);
			if(taskType == null || taskType.length() == 0){
				taskType = "일반";
			}
			task.setTaskType(taskType);
			
			
			if(mileStone.equals("Y")){
				task.setMileStone(true);
			}else{
				task.setMileStone(false);
			}
			PersistenceHelper.manager.save(task);
		}
	}
	
	public static Object checkCellData(Cell[] cell, int row, Hashtable projectH, Hashtable taskH)throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("엑셀 " + (row + 1)+ " 라인");
		HashMap map = new HashMap();
		
		boolean isError = false;
		int columnIndex = 0;
		
		String projectNo = JExcelUtil.getContent(cell, columnIndex++).trim();
		String taskName = JExcelUtil.getContent(cell, columnIndex++).trim();
		String des = JExcelUtil.getContent(cell, columnIndex++).trim();
		
		String taskId = JExcelUtil.getContent(cell, columnIndex++).trim();
		String parentTaskId = JExcelUtil.getContent(cell, columnIndex++).trim();
		String taskType = JExcelUtil.getContent(cell, columnIndex++).trim();
		String mileStone = JExcelUtil.getContent(cell, columnIndex++).trim();
		Timestamp startPlan = JExcelUtil.getTimestamp(cell, columnIndex++);
		Timestamp endPlan = JExcelUtil.getTimestamp(cell, columnIndex++);
	
		String exStartPlan_str = JExcelUtil.getContent(cell, columnIndex).trim();
		Timestamp exStartPlan = JExcelUtil.getTimestamp(cell, columnIndex++);
		
		String exendPlan_str = JExcelUtil.getContent(cell, columnIndex).trim();
		Timestamp exEndPlan = JExcelUtil.getTimestamp(cell, columnIndex++);
		
		String dr = JExcelUtil.getContent(cell, columnIndex++).trim();

		
		E3PSProject project = null;
		if(projectH.containsKey(projectNo)){
			project = (E3PSProject)projectH.get(projectNo);	
		}else{
			project = getProject(projectNo);
			if(project == null){
				isError = true;
				sb.append(" ProjectNo 정보가 맞지 않습니다.");
			}else{
				projectH.put(projectNo, project);
			}
		}
		
		
		if(taskName == null || taskName.length() == 0){
			isError = true;
			sb.append(" 테스크명이 없습니다.");
		}
		
		String key = projectNo + ":" + taskId;
		
		if(taskH.containsKey(key)){
			isError = true;
			sb.append(" 테스크 ID가 유일하지 않습니다.");
		}
		
		if(parentTaskId != null && parentTaskId.length() > 0){
			String parentKey = projectNo + ":" + parentTaskId;
			if(!taskH.containsKey(parentKey)){
				isError = true;
				sb.append(" 부모 테스크ID가 적합하지 않습니다.");
			}
		}
		
		if(taskId == null || taskId.length() == 0){
			isError = true;
		}

		map.put("taskType", taskType);
		map.put("taskName", taskName);
		map.put("projectNo", projectNo);
		map.put("mileStone", mileStone);
		map.put("des", des);
		map.put("taskId", taskId);
		map.put("parentTaskId", parentTaskId);
		
		if(startPlan == null){
			isError = true;
			sb.append(" 시작일 정보가 맞지 않습니다.");
		}else{
			map.put("startPlan", startPlan);
		}
		
		if(endPlan == null){
			isError = true;
			sb.append(" 종료일 정보가 맞지 않습니다.");
		}else{
			map.put("endPlan", endPlan);
		}
		
		if(startPlan.after(endPlan)){
			isError = true;
			sb.append(" 종료일  정보가 시작일보다 빠릅니다.");
		}
		
		if(exStartPlan_str != null && exStartPlan_str.length() > 0){
			if(exStartPlan == null){
				isError = true;
				sb.append(" 실제 시작일 정보가 맞지 않습니다.");
			}
		}
		
		if(exendPlan_str != null && exendPlan_str.length() >0){
			if(exEndPlan == null){
				isError = true;
				sb.append(" 실제 종료일 정보가 맞지 않습니다.");
			}
		}
		
		if(exEndPlan != null){
			if(exStartPlan == null){
				isError = true;
				sb.append(" 실제 시작일 정보가 있어야 합니다.");
			}
		}

		map.put("exStartPlan", exStartPlan);
		map.put("exEndPlan", exEndPlan);
		map.put("dr", dr);
		taskH.put(key, key);
		if(isError){
			return sb;
		}else{
			return map;
		}
	}
	
	public static E3PSTask getTask(E3PSProject project, String taskNo)throws Exception{
		//MoldProjectHelper.getTask(project, taskName);
		
		QuerySpec spec = new QuerySpec(E3PSTask.class);
    	long projectId = project.getPersistInfo().getObjectIdentifier().getId();
    	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[]{0});
    	spec.appendAnd();
    	spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NO, "=", taskNo), new int[]{0});
    	QueryResult rs = PersistenceHelper.manager.find(spec);
    	E3PSTask task = null;
    	if(rs.hasMoreElements()){
    		task = (E3PSTask)rs.nextElement();
    	}
    	
    	return task;
	}
	
	public static E3PSProject getProject(String pjtNo)throws Exception{
		
		QuerySpec spec = new QuerySpec(E3PSProject.class);
		
		spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true),new int[] { 0});

		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] {0});
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		ClassAttribute ca0 = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NO);
    	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
    	
    	pjtNo = pjtNo.toUpperCase();
    	ColumnExpression ce = ConstantExpression.newExpression(pjtNo);
    	spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce) , new int[]{0});
	
		QueryResult rs = PersistenceHelper.manager.find(spec);
		
		E3PSProject project = null;
		
		if(rs.hasMoreElements()){
			project = (E3PSProject)rs.nextElement();
		}
		
		return project;
	}
	
	public static void main(String args[])throws Exception{
		
		wt.method.RemoteMethodServer.getDefault().setUserName("wcadmin");
		wt.method.RemoteMethodServer.getDefault().setPassword("wcadmin");
		
		loadFile("test3.xls");
	}
	
}
