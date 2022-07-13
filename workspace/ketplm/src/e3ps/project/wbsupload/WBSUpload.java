package e3ps.project.wbsupload;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.project.Role;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.TemplateProjectData;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class WBSUpload implements Serializable, RemoteAccess {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static final int titleRowIndex = 0;
    public static final int exStartRowIndex = 1;
    public static final int exStartColumnIndex = 1;

    public static void createWBS(TemplateProject project, File file) throws Exception {
	Workbook wb = null;
	
	if(DRMHelper.useDrm){
	    file = DRMHelper.Decryptupload(file, file.getName());    
	}
	
	try {

	    wb = Workbook.getWorkbook(file);
	} catch (Exception ex) {
	    throw new Exception("엑셀 양식이 적합하지 않습니다.");
	}
	Sheet[] sheets = wb.getSheets();
	Sheet sheet = sheets[0];
	int rows = sheet.getRows();
	int idIndex = checkIDIndex(sheet);
	if (idIndex < 0) {
	    throw new Exception("엑셀 양식이 적합하지 않습니다.");
	}
	int excelLine = 0;
	Hashtable roleH = getRoleHash(project);
	roleH.put("PM", "PM");
	roleH.put("금형개발", "PM");

	StringBuffer sb = new StringBuffer();
	Hashtable h = new Hashtable();
	for (int i = exStartRowIndex; i < rows; i++) {
	    excelLine = i + 1;

	    Cell[] cell = sheet.getRow(i);
	    String taskName = null;
	    int level = -1;
	    // 컬럼 정보를 가져온다.
	    for (int j = exStartColumnIndex; j < idIndex; j++) {
		String content = JExcelUtil.getContent(cell, j).trim();
		if ((taskName != null && taskName.length() > 0) && (content == null || content.trim().length() == 0)) {
		    break;
		} else {
		    level = (j - exStartColumnIndex) + 1;
		    taskName = content;
		}
	    }
	    if (taskName == null || taskName.length() == 0) {
		break;
	    }

	    Object parent = h.get(new Integer(level - 1));
	    if (parent == null && level == 1) {
		parent = project;
	    }
	    if (parent == null) {
		throw new Exception("엑셀 라인 " + excelLine + " : " + "부모태스크가 존재하지 않습니다.");
	    }

	    String taskNo = JExcelUtil.getContent(cell, idIndex).trim();
	    // String durationString = JExcelUtil.getContent(cell, idIndex + 3).trim(); // 기간
	    int duration = 1;
	    //
	    // if (durationString.length() > 0) {
	    // try {
	    // duration = Integer.parseInt(durationString);
	    // } catch (Exception ex) {
	    // if (sb.length() > 0) {
	    // sb.append("\\n");
	    // }
	    // sb.append("엑셀 라인 " + excelLine + " : " + "기간  " + durationString + " 가 적합하지 않습니다.");
	    // }
	    // }

	    boolean isOption = false; // 필수
	    boolean isMileStone = false; // 마일스톤여부
	    // 필수
	    if (cell.length > idIndex + 5) {
		String option_str = JExcelUtil.getContent(cell, idIndex + 5).trim();
		if ("Y".equalsIgnoreCase(option_str)) {
		    isOption = true;
		}
	    }
	    // 마일스톤
	    if (cell.length > idIndex + 6) {
		String mileStone_str = JExcelUtil.getContent(cell, idIndex + 6).trim();
		if ("Y".equalsIgnoreCase(mileStone_str)) {
		    isMileStone = true;
		}
	    }

	    /* 2014.09.12 추가 */
	    Timestamp planStartDate = null; // 계획시작일
	    Timestamp planEndDate = null; // 계획완료일
	    // 계획시작일
	    if (cell.length > idIndex + 2) {
		if (cell[idIndex + 2].getType().equals(CellType.DATE)) {
		    DateCell dc = (DateCell) cell[idIndex + 2];
		    planStartDate = new Timestamp(dc.getDate().getTime());
		    String[] temp = planStartDate.toString().split("-", 0);
		    if (temp[0].length() > 4) {
			throw new Exception("날짜형식(YYYY-MM-DD)에 맞게 기입하세요.");
		    }
		}
		// throw new Exception("날짜형식(YYYY-MM-DD)에 맞게 기입하세요.");
	    }
	    // 계획완료일
	    if (cell.length > idIndex + 3) {
		if (cell[idIndex + 3].getType().equals(CellType.DATE)) {
		    DateCell dc = (DateCell) cell[idIndex + 3];
		    planEndDate = new Timestamp(dc.getDate().getTime());
		    String[] temp = planEndDate.toString().split("-", 0);
		    if (temp[0].length() > 4) {
			throw new Exception("날짜형식(YYYY-MM-DD)에 맞게 기입하세요.");
		    }
		}
		// throw new Exception("날짜형식(YYYY-MM-DD)에 맞게 기입하세요.");
	    }
	    // Role
	    String roleDisplay = JExcelUtil.getContent(cell, idIndex + 4).trim();
	    String roleKey = "";
	    if (roleDisplay.length() > 0) {
		roleKey = (String) roleH.get(roleDisplay);
		if (roleKey == null) {
		    if (sb.length() > 0) {
			sb.append("\\n");
		    }
		    sb.append("엑셀 라인 " + excelLine + " : " + "Role  " + roleDisplay + " 가 적합하지 않습니다.");
		    // throw new Exception("Role  " + roleDisplay + " 가 적합하지 않습니다.");
		}

	    }
	    Map<String, String> extCellMap = new HashMap<String, String>();
	    extCellMap.put("taskType", JExcelUtil.getContent(cell, idIndex + 7).trim());// Task종류
	    extCellMap.put("taskTypeCategory", JExcelUtil.getContent(cell, idIndex + 8).trim());// 유형
	    extCellMap.put("drValue", JExcelUtil.getContent(cell, idIndex + 9).trim());// DR값
	    extCellMap.put("planWorkTime", JExcelUtil.getContent(cell, idIndex + 10).trim());// 계획작업시간(hr)
	    extCellMap.put("newType", JExcelUtil.getContent(cell, idIndex + 11).trim());// 신규
	    extCellMap.put("modify", JExcelUtil.getContent(cell, idIndex + 12).trim());// Modify
	    extCellMap.put("common", JExcelUtil.getContent(cell, idIndex + 13).trim());// 공통
	    extCellMap.put("mdraw", JExcelUtil.getContent(cell, idIndex + 14).trim());// 기구
	    extCellMap.put("hw", JExcelUtil.getContent(cell, idIndex + 15).trim());// H/W
	    extCellMap.put("sw", JExcelUtil.getContent(cell, idIndex + 16).trim());// S/W
	    extCellMap.put("m", JExcelUtil.getContent(cell, idIndex + 17).trim());// M
	    extCellMap.put("p", JExcelUtil.getContent(cell, idIndex + 18).trim());// P
	    extCellMap.put("buy", JExcelUtil.getContent(cell, idIndex + 19).trim());// 구매
	    extCellMap.put("system", JExcelUtil.getContent(cell, idIndex + 20).trim());// 설비

	    TemplateTask task = createTask(parent, taskName, taskNo, roleKey, duration, isOption, isMileStone, planStartDate, planEndDate, extCellMap);
	    h.put(level, task);
	    // 선행Task
	    String preTaskIds = JExcelUtil.getContent(cell, idIndex + 1).trim();
	    if (preTaskIds.length() > 0) {
		StringTokenizer st = new StringTokenizer(preTaskIds, ",");
		while (st.hasMoreTokens()) {
		    String preTaskId = st.nextToken();
		    if (preTaskId.trim().length() > 0) {
			TemplateTask preTask = ProjectTaskHelper.getTaskFromTaskNo(project, preTaskId.trim());
			if (preTask == null) {

			    if (sb.length() > 0) {
				sb.append("\\n");
			    }
			    sb.append("엑셀 라인 " + excelLine + " : " + "선행태스크 ID " + preTaskId + " 가 존재하지 않습니다.");

			    // throw new Exception("선행태스크 ID " + preTaskId + " 가 적합하지 않습니다.");
			}
			if (preTask != null) {
			    if (!ProjectTaskHelper.isChild(preTask)) {

				TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(task, preTask);
				PersistenceHelper.manager.save(link);
			    } else {
				if (sb.length() > 0) {
				    sb.append("\\n");
				}
				sb.append("엑셀 라인 " + excelLine + " : " + "선행태스크 ID " + preTaskId + " 가 적합하지 않습니다.");
			    }
			}
		    }

		}
	    }

	}
	if (sb.length() > 0) {
	    throw new Exception(sb.toString());
	}

    }

    public static int checkIDIndex(Sheet sheet) {
	Cell[] cell = sheet.getRow(titleRowIndex);
	int index = -1;
	for (int i = 0; i < cell.length; i++) {
	    String content = e3ps.common.util.JExcelUtil.getContent(cell, i).trim();
	    if ("ID".equals(content)) {
		index = i;
		break;
	    }
	}
	return index;
    }

    public static void load(int type, File file) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { int.class, File.class };
	    Object args[] = new Object[] { type, file };
	    try {
		RemoteMethodServer.getDefault().invoke("load", WBSUpload.class.getName(), null, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(WBSUpload.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(WBSUpload.class, e);
		throw new WTException(e);
	    }
	}

	Hashtable hash = new Hashtable();
	if (type == 1) {
	    String name = "Pilot Project WBS";
	    hash.put("NAME", name);
	    hash.put("PTTYPE", "1");
	    hash.put("PLANTYPE", "true");
	    hash.put("ASSEMBLING", "true");
	    hash.put("DEVELOPTYPE", "2");
	}
	// TemplateProject project = null;
	Transaction transaction = new Transaction();

	try {
	    transaction.start();
	    TemplateProject project = createTemplateProject(hash);
	    createWBS(project, file);
	    // transaction.commit();
	    // transaction = null;

	} catch (Exception e) {
	    Kogger.error(WBSUpload.class, e);
	    Kogger.debug(WBSUpload.class, e.getLocalizedMessage());
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    public static TemplateProject createTemplateProject(Hashtable hash) throws Exception {

	String name = (String) hash.get("NAME"); // Template Name
	String durationStr = (String) hash.get("DURATION"); // Template Duration String Value
	int duration = StringUtil.parseInt(durationStr, 1); // Template Duration
	String description = (String) hash.get("DESCRIPTION"); // Template Description
	String ptType = (String) hash.get("PTTYPE");
	String planType = (String) hash.get("PLANTYPE");
	String assembling = (String) hash.get("ASSEMBLING");
	String developType = (String) hash.get("DEVELOPTYPE");
	String makeType = (String) hash.get("MAKETYPE");
	String productType = (String) hash.get("PRODUCTTYPE");

	TemplateProjectData oldPJTData = null;
	ProductTemplateProject ptProject = null;
	MoldTemplateProject mtProject = null;
	ElectronTemplateProject etProject = null;
	TemplateProject tProject = null;

	ScheduleData schedule = ScheduleData.newScheduleData();
	// 1.1 Duration Setting
	if (oldPJTData != null) {
	    schedule.setDuration(oldPJTData.tempDuration);
	} else {
	    schedule.setDuration(duration);
	}
	// 1.2 ScheduleData Object Save
	schedule = (ScheduleData) PersistenceHelper.manager.save(schedule);

	if ("1".equals(ptType)) { // ProductTemplateProject
	    ptProject = ProductTemplateProject.newProductTemplateProject();

	    // 2.1 Template Sequence Setting
	    ptProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

	    // 2.2 Template Lifecycle Setting
	    Folder folder = null;
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    Kogger.debug(WBSUpload.class, "########## folderPath=" + folderPath);
	    folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
	    Kogger.debug(WBSUpload.class, "########## folder=" + folder);

	    // 2.3 FolderHelper.assignFolder(project, folder);
	    FolderHelper.assignLocation((FolderEntry) ptProject, folder);

	    // 2.4m LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

	    // 2.5 Name Setting
	    ptProject.setPjtName(StringUtil.checkNull(name.trim()));

	    // 2.6 Desc Setting
	    if (description != null) {
		ptProject.setPjtDesc(StringUtil.checkNull(description.trim()));
	    } else {
		ptProject.setPjtDesc("");
	    }

	    // 2.7 ScheduleData Object
	    ptProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));
	    ptProject.setPjtType(2); // ??

	    ptProject.setPlanType(Boolean.valueOf(planType).booleanValue());
	    ptProject.setAssembling(Boolean.valueOf(assembling).booleanValue());
	    if (developType.length() > 0)
		ptProject.setDevelopType(Integer.parseInt(developType));

	    // 2.8 TemplateProject Object Save
	    ptProject = (ProductTemplateProject) PersistenceHelper.manager.save(ptProject);

	    // 3. TemplateProject Object Copy & TemplateTask Object Copy

	} else if ("2".equals(ptType)) { // MoldTemplateProject
	    mtProject = MoldTemplateProject.newMoldTemplateProject();

	    // 2.1 Template Sequence Setting
	    mtProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

	    // 2.2 Template Lifecycle Setting
	    Folder folder = null;
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    Kogger.debug(WBSUpload.class, "########## folderPath=" + folderPath);
	    folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
	    Kogger.debug(WBSUpload.class, "########## folder=" + folder);

	    // 2.3 FolderHelper.assignFolder(project, folder);
	    FolderHelper.assignLocation((FolderEntry) mtProject, folder);
	    // 2.4m LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

	    // 2.5 Name Setting
	    mtProject.setPjtName(StringUtil.checkNull(name.trim()));
	    // 2.6 Desc Setting
	    if (description != null) {
		mtProject.setPjtDesc(StringUtil.checkNull(description.trim()));
	    } else {
		mtProject.setPjtDesc("");
	    }
	    // 2.7 ScheduleData Object
	    mtProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    mtProject.setPjtType(3); // ??

	    if (makeType.length() > 0)
		mtProject.setMakeType(Integer.parseInt(makeType));

	    // 2.8 TemplateProject Object Save
	    mtProject = (MoldTemplateProject) PersistenceHelper.manager.save(mtProject);

	} else if ("3".equals(ptType)) { // ElectronTemplateProject
	    etProject = ElectronTemplateProject.newElectronTemplateProject();

	    // 2.1 Template Sequence Setting
	    etProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

	    // 2.2 Template Lifecycle Setting
	    Folder folder = null;
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    Kogger.debug(WBSUpload.class, "########## folderPath=" + folderPath);
	    folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
	    Kogger.debug(WBSUpload.class, "########## folder=" + folder);

	    // 2.3 FolderHelper.assignFolder(project, folder);
	    FolderHelper.assignLocation((FolderEntry) etProject, folder);
	    // 2.4m LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

	    // 2.5 Name Setting
	    etProject.setPjtName(StringUtil.checkNull(name.trim()));
	    // 2.6 Desc Setting
	    if (description != null) {
		etProject.setPjtDesc(StringUtil.checkNull(description.trim()));
	    } else {
		etProject.setPjtDesc("");
	    }
	    // 2.7 ScheduleData Object
	    etProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    etProject.setPjtType(4); // ??

	    if (productType.length() > 0)
		etProject.setProductType(Integer.parseInt(productType));

	    // 2.8 TemplateProject Object Save
	    etProject = (ElectronTemplateProject) PersistenceHelper.manager.save(etProject);

	} else if ("4".equals(ptType) || "5".equals(ptType)) { // TemplateProject
	    tProject = TemplateProject.newTemplateProject();

	    // 2.1 Template Sequence Setting
	    tProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

	    // 2.2 Template Lifecycle Setting
	    Folder folder = null;
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    // Kogger.debug(getClass(), "########## folderPath="+folderPath);
	    folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
	    // Kogger.debug(getClass(), "########## folder="+folder);

	    // 2.3 FolderHelper.assignFolder(project, folder);
	    FolderHelper.assignLocation((FolderEntry) tProject, folder);
	    // 2.4m LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

	    // 2.5 Name Setting
	    tProject.setPjtName(StringUtil.checkNull(name.trim()));
	    // 2.6 Desc Setting
	    if (description != null) {
		tProject.setPjtDesc(StringUtil.checkNull(description.trim()));
	    } else {
		tProject.setPjtDesc("");
	    }
	    // 2.7 ScheduleData Object
	    tProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    // Kogger.debug(getClass(), "########## folderfolder="+Integer.parseInt(ptType));
	    if ("4".equals(ptType))
		tProject.setPjtType(1); // ??
	    else
		tProject.setPjtType(0); // ??

	    // 2.8 TemplateProject Object Save
	    tProject = (TemplateProject) PersistenceHelper.manager.save(tProject);
	    // Kogger.debug(getClass(), "########## folderfolder="+tProject);

	}

	if ("1".equals(ptType)) {
	    return ptProject;
	} else if ("2".equals(ptType)) {
	    return mtProject;
	} else if ("3".equals(ptType)) { // ElectronTemplateProject
	    return etProject;
	} else if ("4".equals(ptType) || "5".equals(ptType)) { // TemplateProject
	    return tProject;
	} else {
	    return null;
	}

    }

    public static TemplateTask createTask(Object parentObj, String taskName, String taskNo, String rolekey, int duration, boolean option, boolean mileStone, Timestamp planStartDate,
	    Timestamp planEndDate, Map<String, String> extCellMap) throws Exception {
	TemplateTask task = null;

	// task.setTaskSeq(cseq);

	TemplateTask parentTask = null;
	task = TemplateTask.newTemplateTask();
	if (parentObj instanceof TemplateProject) {
	    task.setProject((TemplateProject) parentObj);
	}

	if (parentObj instanceof TemplateTask) {
	    parentTask = (TemplateTask) parentObj;
	    task.setProject(parentTask.getProject());
	    task.setParent(parentTask);
	}

	TemplateTask alreadyTask = ProjectTaskHelper.manager.getTaskFromTaskNo(task.getProject(), taskNo);
	if (alreadyTask != null) {
	    throw new Exception("태스크의  ID가 중복 되었습니다.");
	}

	ScheduleData schedule = ScheduleData.newScheduleData();
	schedule.setDuration(duration);
	schedule.setPlanStartDate(planStartDate);
	schedule.setPlanEndDate(planEndDate);
	// schedule.setStdWork(stdWork);
	schedule = (ScheduleData) PersistenceHelper.manager.save(schedule);

	task.setTaskSchedule(ObjectReference.newObjectReference(schedule));
	task.setTaskName(taskName);
	task.setPersonRole(rolekey);
	if (parentTask == null) {

	    if (ProjectTaskHelper.isChildName(task.getProject(), taskName)) {
		throw new Exception("이미 " + taskName + " 이름을 가진 테스크가 존재합니다. ");
	    }
	} else {
	    if (ProjectTaskHelper.isChildName(parentTask, taskName)) {
		throw new Exception("이미 " + taskName + " 이름을 가진 테스크가 존재합니다. ");
	    }
	}

	int maxSeq = ProjectTaskHelper.getMaxSeq((TemplateTask) task.getParent(), task.getProject());
	int drValue = StringUtil.parseInt(extCellMap.get("drValue"), 0);
	if (drValue > 100) {
	    drValue = 100;
	} else if (drValue < 0) {
	    drValue = 0;
	}
	task.setTaskSeq(maxSeq);
	task.setTaskNo(taskNo);
	task.setOptionType(option);
	task.setMileStone(mileStone);
	String taskType = (String) extCellMap.get("taskType");
	String taskTypeCategory = (String) extCellMap.get("taskTypeCategory");
	if("원가".equals(taskType)){
	    taskType = "COST";
	    NumberCode num = NumberCodeHelper.manager.getNumberCodeName("TASKTYPE", taskTypeCategory);
	    
	    if(num != null){
		taskTypeCategory = num.getCode();
	    }
	}
	task.setTaskType(taskType);
	task.setTaskTypeCategory(taskTypeCategory);
	task.setDrValue(drValue + "");
	task.setPlanWorkTime(parseFloat(extCellMap.get("planWorkTime")));
	task.setModifyType(parseIntYn(extCellMap.get("modify")));
	task.setNewType(parseIntYn(extCellMap.get("newType")));
	task.setCommon(parseInt(extCellMap.get("common"), 0));
	task.setMdraw(parseInt(extCellMap.get("mdraw"), 0));
	task.setHw(parseInt(extCellMap.get("hw"), 0));
	task.setSw(parseInt(extCellMap.get("sw"), 0));
	task.setM(parseInt(extCellMap.get("m"), 0));
	task.setP(parseInt(extCellMap.get("p"), 0));
	task.setBuy(parseInt(extCellMap.get("buy"), 0));
	task.setSystem(parseInt(extCellMap.get("system"), 0));
	task = (TemplateTask) PersistenceHelper.manager.save(task);
	return task;
    }

    public static float parseFloat(String str) throws Exception {
	if (!StringUtil.checkString(str)) {
	    return 0;
	}
	float returnFloat = 0;
	try {
	    returnFloat = Float.parseFloat(str);
	    if (returnFloat < 0) {
		return 0;
	    }
	} catch (Exception e) {
	    return returnFloat;
	}
	return returnFloat;
    }

    public static int parseInt(String str, int defaultData) throws Exception {
	if (!StringUtil.checkString(str)) {
	    return defaultData;
	}
	if ("○".equals(str)) {
	    return 1;
	} else if ("●".equals(str)) {
	    return 2;
	} else {
	    return defaultData;
	}
    }

    public static int parseIntYn(String str) throws Exception {
	if (!StringUtil.checkString(str)) {
	    return 0;
	}
	if ("Y".equals(str)) {
	    return 1;
	} else {
	    return 0;
	}
    }

    public static void main(String args[]) throws Exception {

	RemoteMethodServer server = RemoteMethodServer.getDefault();
	server.setUserName("wcadmin");
	server.setPassword("wcadmin");
	File file = new File("C://Documents and Settings/khkim/바탕 화면/wbs/03. Pilot Project WBS_R2.xls");
	Kogger.debug(WBSUpload.class, "filePath = " + file.getAbsolutePath());

	load(1, file);

    }

    public static Hashtable getRoleHash(TemplateProject project) throws Exception {
	int pjtType = project.getPjtType();
	
	if("work".equals(project.getMoldType())){
	    pjtType = 5;
	}
	String teamName = ProjectHelper.getProjectTeam(pjtType);

	TeamTemplate tempTeam = null;

	tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);

	Vector vecTeamStd = new Vector();

	if (tempTeam != null) {
	    vecTeamStd = tempTeam.getRoles();
	}

	// Collections.sort(vecTeamStd, new RoleComparator());

	Hashtable roleHash = new Hashtable();
	for (int i = 0; i < vecTeamStd.size(); i++) {

	    Role role = (Role) vecTeamStd.get(i);
	    String roleName_en = role.toString();
	    String displayName = role.getDisplay(Locale.KOREA);
	    roleHash.put(displayName, role.toString());

	}
	return roleHash;
    }
}
