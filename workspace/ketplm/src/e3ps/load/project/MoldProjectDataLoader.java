package e3ps.load.project;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.WCUtil;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.machine.MoldMachine;
import e3ps.project.machine.load.MachineLoad;
import ext.ket.shared.log.Kogger;

public class MoldProjectDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean     SERVER    = wt.method.RemoteMethodServer.ServerFlag;

    //private static String FILESERVER = "\\\\192.168.17.13";
    private static String    EXCELFILE = "";

    private static String    SEPARATOR = File.separator;
    private static Hashtable machinType_mold;
    private static Hashtable machinType_press;
    private static Hashtable machinTon;
    private static Hashtable machinMaker_mold;
    private static Hashtable machinMaker_press;
    private static Hashtable ranks;
    private static String    logFile   = "moldProjectLoader.log";

    static {


	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\project";
	} catch (Exception e) {
	    Kogger.error(MoldProjectDataLoader.class, e);
	}
    }

    public static boolean loadFile(String fileName) throws Exception {


	String filePath = EXCELFILE + SEPARATOR + fileName;
	File dataFile = new File(filePath);
	if (!dataFile.exists()) {
	    Kogger.debug(MoldProjectDataLoader.class, "File not found!!!");
	    return false;
	}

	logFile = EXCELFILE + SEPARATOR;
	int pidx = fileName.lastIndexOf(".");
	if (pidx > 0) {
	    logFile += fileName.substring(0, pidx);
	}
	else {
	    logFile = fileName;
	}
	logFile += ".txt";

	machinType_mold = MachineLoad.getHashNumberCode("MACHINETYPE", "Mold");
	machinType_press = MachineLoad.getHashNumberCode("MACHINETYPE", "Press");
	machinTon = MachineLoad.getHashNumberCode("MACHINETON");
	machinMaker_mold = MachineLoad.getHashNumberCode("MACHINEMAKER", "Mold");
	machinMaker_press = MachineLoad.getHashNumberCode("MACHINEMAKER", "Press");
	ranks = getRank();

	Workbook wb = Workbook.getWorkbook(dataFile);
	Sheet sheets[] = wb.getSheets();
	Sheet sheet = sheets[0];
	int startRow = 2;
	int lastRow = sheet.getRows();
	Vector datas = new Vector();

	boolean result = true;
	for (int i = startRow; i < lastRow; i++) {
	    Cell[] cell = sheet.getRow(i);
	    //Object obj = checkMachineData(cell, i);
	    String dieNo = JExcelUtil.getContent(cell, 0).trim();
	    if (dieNo == null || dieNo.length() == 0) {
		break;
	    }
	    Object obj = checkCellData(cell, i);
	    if (obj instanceof StringBuffer) {
		StringBuffer sb = (StringBuffer) obj;
		Kogger.debug(MoldProjectDataLoader.class, sb.toString());
		//log(sb.toString());
		result = false;
	    }
	    else {
		//Kogger.debug(getClass(), obj);
		datas.add(obj);
	    }
	}

	if (result) {
	    upload(datas);
	}

	//Kogger.debug(getClass(), "검색 완료");
	return result;
    }

    public static void upload(Vector datas) throws Exception {
	for (int i = 0; i < datas.size(); i++) {
	    HashMap map = (HashMap) datas.get(i);
	    MoldItemInfo moldItemInfo = (MoldItemInfo) map.get("moldItemInfo");

	    MoldProject project = MoldProjectHelper.getMoldProject(moldItemInfo);
	    ExtendScheduleData data = null;
	    if (project == null) {
		project = MoldProject.newMoldProject();

		String lifecycle = "KET_MOLD_PMS_LC";


		FolderHelper.assignLocation((FolderEntry) project, FolderHelper.service.getFolder("/Default/프로젝트", WCUtil.getWTContainerRef()));
		LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));


		data = ExtendScheduleData.newExtendScheduleData();
	    }
	    else {
		data = (ExtendScheduleData) project.getPjtSchedule().getObject();
	    }

	    NumberCode rankCode = (NumberCode) map.get("rank");
	    project.setMoldInfo(moldItemInfo);
	    project.setRank(rankCode);
	    project.setPjtNo(moldItemInfo.getDieNo());
	    project.setPjtName(moldItemInfo.getPartName());
	    String proPc = (String) map.get("proPC");
	    String scraPC = (String) map.get("scraPC");
	    String outSourcing = (String) map.get("outSourcing");
	    String special = (String) map.get("special");
	    MoldMachine moldMachine = (MoldMachine) map.get("moldMachine");
	    String etc = (String) map.get("etc");

	    project.setProductWeight(proPc);
	    project.setScrapWeight(scraPC);
	    project.setOutSourcing(outSourcing);
	    project.setSpecialSpec(special);
	    project.setMoldMachine(moldMachine);
	    project.setRemark(etc);

	    project.setPjtType(3);

	    Timestamp startPlane = (Timestamp) map.get("startPlane");
	    Timestamp endPlane = (Timestamp) map.get("endPlane");
	    String state = (String) map.get("state");

	    data.setPlanStartDate(startPlane);

	    State lifeCycleState = null;
	    if (state.equals("완료")) {
		//Kogger.debug(getClass(), "state = " + state);
		data.setPlanEndDate(endPlane);
		data.setExecStartDate(startPlane);
		data.setExecEndDate(endPlane);
		lifeCycleState = wt.lifecycle.State.toState("COMPLETED");

	    }
	    else {
		if (endPlane != null) {
		    data.setPlanEndDate(endPlane);
		}
		lifeCycleState = wt.lifecycle.State.toState("PROGRESS");
	    }

	    data = (ExtendScheduleData) PersistenceHelper.manager.save(data);
	    project.setPjtSchedule(ObjectReference.newObjectReference(data));

	    project = (MoldProject) PersistenceHelper.manager.save(project);

	    WTUser pm = (WTUser) map.get("pmId");
	    if (pm != null) {
		ProjectUserHelper.manager.setPM(project, pm, 0);
	    }
	    project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, lifeCycleState, true);

	}
    }

    public static Object checkMachineData(Cell[] cell, int row) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append("엑셀 " + (row + 1) + " 라인");
	HashMap map = new HashMap();

	boolean isError = false;
	int columnIndex = 0;

	String dieNo = JExcelUtil.getContent(cell, columnIndex++).trim();
	String type = JExcelUtil.getContent(cell, columnIndex++).trim();
	String ton = JExcelUtil.getContent(cell, columnIndex++).trim();
	String maker = JExcelUtil.getContent(cell, columnIndex++).trim();
	String model = JExcelUtil.getContent(cell, columnIndex++).trim();

	if (type != null && type.length() > 0) {
	    String gubun = "";
	    NumberCode ntype = null;
	    NumberCode nton = null;
	    NumberCode nmaker = null;

	    nton = (NumberCode) machinTon.get(ton);
	    if (dieNo.startsWith("1")) {
		gubun = "Press";
		ntype = (NumberCode) machinType_press.get(type);
		nmaker = (NumberCode) machinMaker_press.get(maker);

	    }
	    else {
		gubun = "Mold";
		ntype = (NumberCode) machinType_mold.get(type);
		nmaker = (NumberCode) machinMaker_mold.get(maker);
	    }

	    if (ntype == null) {
		isError = true;
		sb.append(" 설비 Type 가 기준데이터와 맞지 않습니다.");
	    }

	    if (nton == null) {
		isError = true;
		sb.append(" Ton(형 체력)가 기준데이터와 맞지 않습니다.");
	    }

	    if (nmaker == null) {
		isError = true;
		sb.append(" Maker가 기준데이터와 맞지 않습니다.");
	    }

	    if (ntype != null && nton != null && nmaker != null) {
		MoldMachine moldMachine = MachineLoad.checkCodeData(gubun, ntype, nton, nmaker, model);
		if (moldMachine == null) {
		    isError = true;
		    sb.append(" 기계설비 정보가 적합하지 않습니다.");
		}
		//Kogger.debug(getClass(), "ok......");
		//sb.append(" 설비 정보 등록");
		//map.put("moldMachine", moldMachine);	
	    }
	}
	if (isError) {
	    return sb;
	}
	else {
	    return (row + 1) + "라인 정보 확인 = " + dieNo;
	}
    }

    public static Object checkCellData(Cell[] cell, int row) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append("엑셀 " + (row + 1) + " 라인");
	HashMap map = new HashMap();

	boolean isError = false;
	int columnIndex = 0;

	String projectNo = JExcelUtil.getContent(cell, columnIndex++).trim();
	String dieNo = JExcelUtil.getContent(cell, columnIndex++).trim();
	String rank = JExcelUtil.getContent(cell, columnIndex++).trim();
	String proPC = JExcelUtil.getContent(cell, columnIndex++).trim();
	String scraPC = JExcelUtil.getContent(cell, columnIndex++).trim();
	String pmId = JExcelUtil.getContent(cell, columnIndex++).trim();
	columnIndex++;
	String making = JExcelUtil.getContent(cell, columnIndex++).trim();
	String outSourcing = JExcelUtil.getContent(cell, columnIndex++).trim();
	String special = JExcelUtil.getContent(cell, columnIndex++).trim();
	String type = JExcelUtil.getContent(cell, columnIndex++).trim();
	String ton = JExcelUtil.getContent(cell, columnIndex++).trim();
	String maker = JExcelUtil.getContent(cell, columnIndex++).trim();
	String model = JExcelUtil.getContent(cell, columnIndex++).trim();
	String etc = JExcelUtil.getContent(cell, columnIndex++).trim();

	Timestamp startPlane = JExcelUtil.getTimestamp(cell, columnIndex++, "yy/MM/dd");

	String endPlaneStr = cell[columnIndex].getContents();
	Timestamp endPlane = JExcelUtil.getTimestamp(cell, columnIndex++, "yy/MM/dd");


	String state = JExcelUtil.getContent(cell, columnIndex++).trim();

	Kogger.debug(MoldProjectDataLoader.class, "ProjectNo == " + projectNo);
	Kogger.debug(MoldProjectDataLoader.class, "dieNo == " + dieNo);
	MoldItemInfo moldItemInfo = getMoldItemInfo(projectNo, dieNo);
	if (moldItemInfo == null) {
	    isError = true;
	    sb.append(" 프로젝트 번호에 해당되는 Die No가 맞지 않습니다.");
	}
	map.put("moldItemInfo", moldItemInfo);

	if (rank != null && rank.length() > 0) {
	    NumberCode rankCode = (NumberCode) ranks.get(rank);
	    if (rankCode == null) {
		isError = true;
		sb.append(" Rank 정보가 맞지 않습니다.");
	    }
	    map.put("rank", rankCode);
	}


	map.put("proPC", proPC);
	map.put("scraPC", scraPC);

	if (pmId != null && pmId.length() > 0) {
	    WTPrincipal principal = OrganizationServicesHelper.manager.getAuthenticatedUser(pmId);
	    if (principal == null) {
		isError = true;
		sb.append(" PM ID가 맞지 않습니다.");
	    }
	    map.put("pmId", principal);
	}

	map.put("making", making);
	map.put("outSourcing", outSourcing);
	map.put("special", special);

	if (type != null && type.length() > 0) {
	    String gubun = "";
	    NumberCode ntype = null;
	    NumberCode nton = null;
	    NumberCode nmaker = null;

	    nton = (NumberCode) machinTon.get(ton);
	    if (dieNo.startsWith("1")) {
		gubun = "Press";
		ntype = (NumberCode) machinType_press.get(type);
		nmaker = (NumberCode) machinMaker_press.get(maker);

	    }
	    else {
		gubun = "Mold";
		ntype = (NumberCode) machinType_mold.get(type);
		nmaker = (NumberCode) machinMaker_mold.get(maker);
	    }

	    if (ntype == null) {
		isError = true;
		sb.append(" 설비 Type 가 기준데이터와 맞지 않습니다.");
	    }

	    if (nton == null) {
		isError = true;
		sb.append(" Ton(형 체력)가 기준데이터와 맞지 않습니다.");
	    }

	    if (nmaker == null) {
		isError = true;
		sb.append(" Maker가 기준데이터와 맞지 않습니다.");
	    }

	    if (ntype != null && nton != null && nmaker != null) {
		MoldMachine moldMachine = MachineLoad.checkCodeData(gubun, ntype, nton, nmaker, model);
		if (moldMachine == null) {
		    isError = true;
		    sb.append(" 기계설비 정보가 적합하지 않습니다.");
		}
		map.put("moldMachine", moldMachine);
	    }
	}



	map.put("etc", etc);

	if (startPlane == null) {
	    isError = true;
	    sb.append(" 계획 시작일이 적합하지 않습니다.");
	}
	map.put("startPlane", startPlane);
	boolean isCompleted = state.equals("완료");
	boolean isProgress = state.equals("진행");


	if (isCompleted || isProgress) {
	    if (isCompleted) {
		if (endPlane == null) {
		    isError = true;
		    sb.append(" 종료일이 적합하지 않습니다.");
		}
		map.put("endPlane", endPlane);
	    }
	    else {
		if (endPlaneStr != null && endPlaneStr.length() > 0 && endPlane == null) {
		    isError = true;
		    sb.append(" 종료일이 적합하지 않습니다.");
		}
	    }
	}
	else {
	    isError = true;
	    sb.append(" 상태가 적합하지 않습니다.");
	}

	map.put("state", state);

	if (isError) {
	    return sb;
	}
	else {
	    return map;
	}
    }


    /*public static void log(String msg) {
    	try {
    		LogToFile logger = new LogToFile(logFile,true);
    		logger.log(msg);
    	} catch (IOException e) {
    		Kogger.error(getClass(), e);
    	}
    }*/

    public static MoldItemInfo getMoldItemInfo(String projectNo, String dieNo) throws Exception {

	QuerySpec spec = new QuerySpec();
	int index1 = spec.addClassList(MoldItemInfo.class, true);
	int index2 = spec.addClassList(ProductProject.class, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id"), "=", new ClassAttribute(ProductProject.class,
	        "thePersistInfo.theObjectIdentifier.id"));
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { index1, index2 });


	ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

	dieNo = dieNo.toUpperCase();
	ColumnExpression ce = ConstantExpression.newExpression(dieNo);
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce), new int[] { index1 });

	ca0 = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

	projectNo = projectNo.toUpperCase();
	ce = ConstantExpression.newExpression(projectNo);
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce), new int[] { index2 });

	MoldItemInfo moldItemInfo = null;
	QueryResult rs = PersistenceHelper.manager.find(spec);
	if (rs.hasMoreElements()) {
	    Object[] o = (Object[]) rs.nextElement();
	    moldItemInfo = (MoldItemInfo) o[0];
	}

	return moldItemInfo;
    }

    public static Hashtable getRank() {

	NumberCode rank = null;
	Hashtable hash = new Hashtable();

	Vector parent = NumberCodeHelper.manager.getNumberCodeLevelType("RANK", "금형");

	for (int i = 0; i < parent.size(); i++) {
	    rank = (NumberCode) parent.get(i);
	    hash.put(rank.getName(), rank);
	}
	return hash;
    }

    public static void main(String args[]) throws Exception {
	//		//Kogger.debug(getClass(), "tttt..ggggggggggggggggggggggggg");
	//		wt.method.RemoteMethodServer.getDefault().setUserName("wcadmin");
	//		wt.method.RemoteMethodServer.getDefault().setPassword("wcadmin");
	//		
	//		//loadFile("test.xls");
	//		loadFile("moldProjectLoad.xls");

	wt.method.RemoteMethodServer.getDefault().setUserName(args[1]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[2]);
	loadFile(args[0]);

    }

}
