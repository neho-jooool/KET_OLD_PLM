package e3ps.load.project;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
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
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProjectScheduleHelper;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.shared.log.Kogger;

public class CompletedTaskDataLoader implements wt.method.RemoteAccess, java.io.Serializable {
	
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
			Kogger.error(CompletedTaskDataLoader.class, e);
		}
	}
	
	public static boolean loadFile(String fileName) throws Exception {
			
		String filePath = EXCELFILE + SEPARATOR + fileName;
		File dataFile = new File(filePath);
		if(!dataFile.exists()) {
			Kogger.debug(CompletedTaskDataLoader.class, "File not found!!!");
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
		for(int i = startRow; i < sheet.getRows(); i++){
			Cell[] cell = sheet.getRow(i);
			Object obj = checkCellData(cell, i, checkH, projectH);
			if(obj instanceof StringBuffer){
				StringBuffer sb = (StringBuffer)obj;
				Kogger.debug(CompletedTaskDataLoader.class, sb.toString());
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
			String projectNo = (String)map.get("projectNo");
			String mileStone = (String)map.get("mileStone");
			String des = (String)map.get("des");
			String taskName = (String)map.get("taskName");
			
			
			
			Timestamp startPlan = (Timestamp)map.get("startPlan");
			Timestamp endPlan = (Timestamp)map.get("endPlan");
			
			E3PSProject project = (E3PSProject)projectH.get(projectNo);
			ExtendScheduleData data = null;
			E3PSTask task = null;
			
			task = MoldProjectHelper.getTask(project, taskName);
			
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
			data.setExecStartDate(startPlan);
			data.setExecEndDate(endPlan);
			
			data = (ExtendScheduleData)PersistenceHelper.manager.save(data);
			
			task.setTaskName(taskName);
			task.setTaskDesc(des);
			task.setTaskSchedule(ObjectReference.newObjectReference(data));
			task.setProject(project);
			task.setTaskSeq(maxSeq);
			task.setTaskNo(String.valueOf(maxSeq));
			
			if(mileStone.equals("Y")){
				task.setMileStone(true);
			}else{
				task.setMileStone(false);
			}
			PersistenceHelper.manager.save(task);
		}
	}
	
	public static Object checkCellData(Cell[] cell, int row, Hashtable checkH, Hashtable projectH)throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("엑셀 " + (row + 1)+ " 라인");
		HashMap map = new HashMap();
		
		boolean isError = false;
		int columnIndex = 0;
		
		String projectNo = JExcelUtil.getContent(cell, columnIndex++).trim();
		String taskName = JExcelUtil.getContent(cell, columnIndex++).trim();
		String des = JExcelUtil.getContent(cell, columnIndex++).trim();
		String mileStone = JExcelUtil.getContent(cell, columnIndex++).trim();
		String endStr = JExcelUtil.getContent(cell, columnIndex).trim();
		
		Timestamp endPlan = JExcelUtil.getTimestamp(cell, columnIndex++);
		Kogger.debug(CompletedTaskDataLoader.class, "mileStone = " + mileStone + " endStr =" + endStr);
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
		map.put("taskName", taskName);
		map.put("projectNo", projectNo);
		map.put("mileStone", mileStone);
		map.put("des", des);
		
		if(endPlan == null){
			isError = true;
			sb.append(" 종료일 정보가 맞지 않습니다.");
		}else{
			map.put("endPlan", endPlan);
		}
		
		if(checkH.containsKey(projectNo)){
			Timestamp startPlan = (Timestamp)checkH.get(projectNo);
			if(startPlan.after(endPlan)){
				isError = true;
				sb.append(" 종료일  정보가 시작일보다 빠릅니다.");
			}
			map.put("startPlan", startPlan);
		}else{
			if(project != null){
				ExtendScheduleData sdata = (ExtendScheduleData)project.getPjtSchedule().getObject();
				map.put("startPlan", sdata.getPlanStartDate());
			}
		}
		
		if(endPlan != null){
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(endPlan.getTime());
			c.add(Calendar.DATE, 1);
			Timestamp timestamp = new Timestamp(c.getTimeInMillis());
			checkH.put(projectNo, timestamp);
		}
		
		
		
		if(isError){
			return sb;
		}else{
			return map;
		}
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
		
		loadFile("test2.xls");
	}
	
}
