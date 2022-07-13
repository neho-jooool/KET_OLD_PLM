package e3ps.load.project;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
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
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProductProject;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.beans.OEMTypeHelper;
import e3ps.project.beans.ProductHelper;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.log.Kogger;

public class ProductProjectDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean  SERVER    = wt.method.RemoteMethodServer.ServerFlag;

    //private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";

    // 192.168.16.103
    //데이터정리양식_프로젝트.xls

    private static String SEPARATOR = File.separator;

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\project";
	} catch (Exception e) {
	    Kogger.error(ProductProjectDataLoader.class, e);
	}
    }

    private static String logFile   = "productprojectlogfile.log";

    public static boolean loadFile(String fileName) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(ProductProjectDataLoader.class, "File not found!!!");
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

	    Workbook workbook = Workbook.getWorkbook(dataFile);
	    Sheet sheets[] = workbook.getSheets();

	    Vector lds = new Vector();

	    if (sheets.length > 0) {
		Sheet sheet = null;
		for (int i = 0; i < 1; i++) {
		    sheet = sheets[i];

		    int rows = sheet.getRows();

		    Cell hdrs[] = sheet.getRow(1);
		    //System.out.print("#################################################################################################Loader Attr Start");
		    //Kogger.debug(getClass(), );
		    for (int k = 0; k < hdrs.length; k++) {
			Cell hdr = hdrs[k];
			System.out.print("[" + hdr.getContents() + "]");
		    }
		    Kogger.debug(ProductProjectDataLoader.class, "");
		    for (int k = 3; k < rows; k++) {
			Cell cell[] = sheet.getRow(k);
			for (int a = 0; a < cell.length; a++) {
			    System.out.print("<" + cell[a].getContents() + ">");
			}
			ProductProjectData data = (ProductProjectData) getRowData(sheet.getRow(k), k + 1, sheet.getName());


			if (data != null) {
			    //if(!data.isValidate) {
			    //System.out.print("[["+ rdata.msg +"]]");
			    //log(rdata.msg);
			    //Kogger.debug(getClass(), "여기 실행됨?? <<<");
			    //} else {
			    Kogger.debug(ProductProjectDataLoader.class, "여기 실행되어야함...");
			    Vector v = new Vector();
			    v.add(data);

			    Vector v0 = (Vector) save(v);
			    for (int m = 0; m < v0.size(); m++) {
				ProductProjectData rdata0 = (ProductProjectData) v0.get(m);
				if (!(rdata0.isLoadCompleted)) {
				    System.out.print("[[" + rdata0.msg + "]]");
				    //log(rdata0.msg);
				}
			    }
			    //}
			}

		    }
		    //Kogger.debug(getClass(), );
		    //System.out.print("#################################################################################################Loader Attr END ");
		}
	    }
	    workbook.close();
	    workbook = null;
	} catch (Exception e) {
	    Kogger.error(ProductProjectDataLoader.class, e);
	    throw new WTException("file name = " + fileName);
	}
	return true;
    }

    /*	
    	public static void log(String msg) {
    		try {
    			LogToFile logger = new LogToFile(logFile,true);
    			logger.log(msg);
    		} catch (IOException e) {
    			Kogger.error(getClass(), e);
    		}
    	}	
    */
    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	ProductProjectData data = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }

	    sb.append("\n############## >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< ##############");
	    data = new ProductProjectData(cells, rownum);

	    E3PSProject project = null;

	    if (data.projectNo.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Project No : 입력값이 없습니다.");
	    }
	    else {
		project = ProjectHelper.getProject(data.projectNo.toUpperCase());
		if (project != null) {
		    data.isValidate = true;
		    sb.append("\n").append("- Project No : 이미 등록된 번호 입니다.[" + data.projectNo + "]");
		}
	    }
	    data.isExist = (project == null) ? false : true;

	    if (data.projectName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Project Name : 입력값이 없습니다.");
	    }
	    if (data.teamType.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- 사업부 구분 : 입력값이 없습니다.");
	    }

	    if ("자동차 사업부".equals(data.teamType)) {
		if (data.devDeptName.length() == 0) {
		    data.isValidate = false;
		    sb.append("\n").append("- 개발부서 : 입력값이 없습니다.");
		}
	    }
	    if ("전자 사업부".equals(data.teamType)) {
		if (data.pmID.length() == 0) {
		    data.isValidate = false;
		    sb.append("\n").append("- 개발 담당자 ID : 입력값이 없습니다.");
		}
	    }
	    if (data.partNo.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Part No : 입력값이 없습니다.");
	    }
	    if (data.producttype.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- 제품구분 : 입력값이 없습니다.");
	    }
	    if (data.rank.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Rank : 입력값이 없습니다.");
	    }
	    //			if("자동차 사업부".equals(data.teamType)) {
	    //				if(data.model.length() == 0) {
	    //					data.isValidate = false;
	    //					sb.append("\n").append("- 대표차종 : 입력값이 없습니다.");
	    //				}
	    //			}
	    if (data.process.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Process : 입력값이 없습니다.");
	    }
	    if (data.planStartDate == null) {
		data.isValidate = false;
		sb.append("\n").append("- 개발시작일 : 입력값이 없습니다.");
	    }
	    /*			if(data.customereventName.length() == 0) {
	    				data.isValidate = false;
	    				sb.append("\n").append("- 최종 사용처 : 입력값이 없습니다.");
	    			}
	    			if(data.subcontractorName.length() == 0) {
	    				data.isValidate = false;
	    				sb.append("\n").append("- 개발검토 납입처 : 입력값이 없습니다.");
	    			}
	    */if (data.state.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- 상태 : 입력값이 없습니다.");
	    }

	} catch (Exception e) {
	    Kogger.error(ProductProjectDataLoader.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
    }


    public static ProductProjectData save(ProductProjectData data) throws WTException {
	try {
	    //if(!data.isValidate) {
	    //	return data;
	    //}
	    Kogger.debug(ProductProjectDataLoader.class, "############################ Save Start");
	    Kogger.debug(ProductProjectDataLoader.class, data.projectNo);
	    Kogger.debug(ProductProjectDataLoader.class, data.projectName);
	    Kogger.debug(ProductProjectDataLoader.class, data.salesID);
	    Kogger.debug(ProductProjectDataLoader.class, data.teamType);
	    Kogger.debug(ProductProjectDataLoader.class, data.devDeptName);
	    Kogger.debug(ProductProjectDataLoader.class, data.pmID);
	    Kogger.debug(ProductProjectDataLoader.class, data.partNo);
	    Kogger.debug(ProductProjectDataLoader.class, data.producttype);
	    Kogger.debug(ProductProjectDataLoader.class, data.rank);
	    Kogger.debug(ProductProjectDataLoader.class, data.model);
	    Kogger.debug(ProductProjectDataLoader.class, data.assembledtype);
	    Kogger.debug(ProductProjectDataLoader.class, data.process);
	    Kogger.debug(ProductProjectDataLoader.class, data.developenttype);
	    Kogger.debug(ProductProjectDataLoader.class, data.designType);
	    Kogger.debug(ProductProjectDataLoader.class, data.planStartDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.planEndDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.customereventName);
	    Kogger.debug(ProductProjectDataLoader.class, data.subcontractorName);
	    Kogger.debug(ProductProjectDataLoader.class, data.costsDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.state);
	    Kogger.debug(ProductProjectDataLoader.class, "############################ Save End");


	    String stateCheck = "";
	    if (data.state.equals("진행")) {
		stateCheck = "PROGRESS";
	    }
	    else {
		stateCheck = "COMPLETED";
	    }

	    ProductProject project = null;
	    Calendar tempCal = Calendar.getInstance();

	    project = ProductProject.newProductProject();

	    ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
	    if (data.planStartDate != null && data.planEndDate != null) {
		int tempDuration = DateUtil.getDaysFromTo(DateUtil.getDateString(data.planEndDate, "d"), DateUtil.getDateString(data.planStartDate, "d"));
		schedule.setDuration(tempDuration);
	    }
	    schedule.setScheduleHistory(0);

	    if (data.planStartDate != null) {
		tempCal.setTime(DateUtil.parseDateStr(DateUtil.getDateString(data.planStartDate, "d")));
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    if (data.planEndDate != null) {
		tempCal.setTime(DateUtil.parseDateStr(DateUtil.getDateString(data.planEndDate, "d")));
		schedule.setPlanEndDate(new Timestamp(tempCal.getTime().getTime()));
	    }
	    schedule.setExecWork(0);
	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    String lifecycle = "KET_PRODUCT_PMS_LC";
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    FolderHelper.assignLocation((FolderEntry) project, FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef()));
	    LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
	    LifeCycleHelper.setLifeCycle(project, lct);
	    //	       LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), true);

	    project.setPjtNo(data.projectNo.trim());
	    project.setPjtSeq(0);
	    project.setPjtHistory(0);
	    project.setPjtCompletion(0);
	    project.setLastest(true);
	    project.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    if (StringUtil.checkString(data.projectName.trim())) {
		project.setPjtName(StringUtil.checkNull(data.projectName.trim()));
	    }
	    project.setPjtState(1);
	    if (data.teamType.equals("자동차 사업부")) {
		project.setPjtType(2);
	    }
	    else {
		project.setPjtType(4);
	    }

	    project.setTeamType(data.teamType);
	    project.setPartNo(data.partNo);
	    project.setCostsDate(data.costsDate);

	    //Number Code
	    NumberCode producttype2Level = null;
	    NumberCode rank2Level = null;

	    if (data.teamType.equals("자동차 사업부")) {
		producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "자동차", data.producttype.substring(6));
		rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "자동차", data.rank.substring(6));
	    }
	    else {
		producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "전자", data.producttype.substring(5));
		rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "전자", data.rank.substring(5));
	    }

	    if (producttype2Level != null) {
		project.setProductType(producttype2Level);
	    }
	    if (rank2Level != null) {
		project.setRank(rank2Level);
	    }
	    NumberCode assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
	    if (assembledtype1Level != null) {
		project.setAssembledType(assembledtype1Level);
	    }
	    NumberCode process = NumberCodeHelper.manager.getNumberCodeName("PROCESS", data.process);
	    if (process != null) {
		project.setProcess(process);
	    }
	    NumberCode developentType = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
	    if (developentType != null) {
		project.setDevelopentType(developentType);
	    }
	    NumberCode designType = NumberCodeHelper.manager.getNumberCodeName("DESIGNTYPE", data.designType);
	    if (designType != null) {
		project.setDesignType(designType);
	    }

	    QueryResult modelQr = OEMTypeHelper.getOEMProjectTypeName("CARTYPE", data.model);
	    if (modelQr != null) {
		if (modelQr.hasMoreElements()) {
		    OEMProjectType modelType = (OEMProjectType) modelQr.nextElement();
		    if (modelType != null) {
			project.setOemType(modelType);
		    }
		}
	    }

	    if (data.assemblyPlace1 != null) {
		if ("사내".equals(data.assemblyPlace1)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCodeName("PRODUCTIONDEPT", data.assemblyPlace2);
		    if (code != null) project.setAssemblyPlace(code);
		}
		else {
		    //   				   PartnerDao pDao = new PartnerDao();
		    //   				   String proteamNo = pDao.ViewPartnerNo(data.assemblyPlace2);
		    String proteamNo = ProductHelper.ViewPartnerNo(data.assemblyPlace2);
		    if (proteamNo != null && proteamNo.length() > 0) project.setPartnerNo(proteamNo);
		}
	    }

	    //Development Department
	    People devPeople = PeopleHelper.manager.getPeople(data.pmID);
	    WTUser wtdevUser = null;
	    if (devPeople != null) {
		PeopleData pData = new PeopleData(devPeople);
		wtdevUser = (WTUser) pData.wtuser;
	    }
	    if (wtdevUser != null) ProjectUserHelper.manager.setPM(project, wtdevUser, 0);

	    // salesUser
	    People salesPeople = PeopleHelper.manager.getPeople(data.salesID);
	    if (salesPeople != null) {
		PeopleData pData = new PeopleData(salesPeople);
		WTUser wtsalesUser = (WTUser) pData.wtuser;
		if (wtsalesUser != null) {
		    project.setBusiness(wtsalesUser);
		}
	    }

	    //save
	    project = (ProductProject) PersistenceHelper.manager.save(project);

	    Department depart = DepartmentHelper.manager.getDepartmentObj(data.devDeptName);
	    if (depart != null) {
		ArrayList list = new ArrayList();
		list.add(depart);
		boolean flag = ProjectHelper.addRefDepartment(project, list);
	    }

	    String ce = data.customereventName;
	    String sc = data.subcontractorName;
	    StringTokenizer cetoken = new StringTokenizer(ce, ",");
	    int token1 = 0;
	    String ceValue[] = new String[cetoken.countTokens()];
	    NumberCode nc = null;
	    while (cetoken.hasMoreElements()) {
		ceValue[token1] = cetoken.nextToken();
		nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", ceValue[token1]);
		if (nc != null) {
		    ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(nc, project);
		    PersistenceHelper.manager.save(plink);
		}
		else {
		    //        		   throw new Exception("최종 사용처 "+ ceValue[token1]+" 정보가 없습니다.");
		}
		token1++;
	    }

	    StringTokenizer sctoken = new StringTokenizer(sc, ",");
	    int token2 = 0;
	    String scValue[] = new String[sctoken.countTokens()];
	    while (sctoken.hasMoreElements()) {
		scValue[token2] = sctoken.nextToken();
		nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", scValue[token2]);
		if (nc != null) {
		    ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(nc, project);
		    PersistenceHelper.manager.save(plink);
		}
		else {
		    //        		   throw new Exception("개발검토 의뢰처  "+ scValue[token2]+" 정보가 없습니다.");
		}
		token2++;
	    }
	    //project = (ReviewProject) PersistenceHelper.manager.refresh(project);

	    project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), false);

	} catch (Exception e) {
	    Kogger.error(ProductProjectDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static ProductProjectData update(ProductProjectData data) throws WTException {
	try {
	    /*
	    if(!data.isValidate) {
	    	return data;
	    }
	    */
	    Kogger.debug(ProductProjectDataLoader.class, "############################ update Start");
	    Kogger.debug(ProductProjectDataLoader.class, data.projectNo);
	    Kogger.debug(ProductProjectDataLoader.class, data.projectName);
	    Kogger.debug(ProductProjectDataLoader.class, data.salesID);
	    Kogger.debug(ProductProjectDataLoader.class, data.teamType);
	    Kogger.debug(ProductProjectDataLoader.class, data.devDeptName);
	    Kogger.debug(ProductProjectDataLoader.class, data.pmID);
	    Kogger.debug(ProductProjectDataLoader.class, data.partNo);
	    Kogger.debug(ProductProjectDataLoader.class, data.producttype);
	    Kogger.debug(ProductProjectDataLoader.class, data.rank);
	    Kogger.debug(ProductProjectDataLoader.class, data.model);
	    Kogger.debug(ProductProjectDataLoader.class, data.assembledtype);
	    Kogger.debug(ProductProjectDataLoader.class, data.process);
	    Kogger.debug(ProductProjectDataLoader.class, data.developenttype);
	    Kogger.debug(ProductProjectDataLoader.class, data.designType);
	    Kogger.debug(ProductProjectDataLoader.class, data.planStartDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.planEndDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.customereventName);
	    Kogger.debug(ProductProjectDataLoader.class, data.subcontractorName);
	    Kogger.debug(ProductProjectDataLoader.class, data.costsDate);
	    Kogger.debug(ProductProjectDataLoader.class, data.state);
	    Kogger.debug(ProductProjectDataLoader.class, "############################ update End");

	    String stateCheck = "";
	    if (data.state.equals("진행")) {
		stateCheck = "PROGRESS";
	    }
	    else {
		stateCheck = "COMPLETED";
	    }

	    ProductProject project = null;
	    Calendar tempCal = Calendar.getInstance();

	    E3PSProject e3ps = ProjectHelper.getProject(data.projectNo.toUpperCase());
	    Object obj = e3ps;
	    project = (ProductProject) obj;

	    ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
	    if (data.planStartDate != null && data.planEndDate != null) {
		int tempDuration = DateUtil.getDaysFromTo(DateUtil.getDateString(data.planEndDate, "d"), DateUtil.getDateString(data.planStartDate, "d"));
		schedule.setDuration(tempDuration);
	    }
	    schedule.setScheduleHistory(0);

	    if (data.planStartDate != null) {
		tempCal.setTime(DateUtil.parseDateStr(DateUtil.getDateString(data.planStartDate, "d")));
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    if (data.planEndDate != null) {
		tempCal.setTime(DateUtil.parseDateStr(DateUtil.getDateString(data.planEndDate, "d")));
		schedule.setPlanEndDate(new Timestamp(tempCal.getTime().getTime()));
	    }

	    schedule.setExecWork(0);
	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    String lifecycle = "KET_PRODUCT_PMS_LC";

	    project.setPjtNo(data.projectNo.trim());
	    project.setPjtSeq(0);
	    project.setPjtHistory(0);
	    project.setPjtCompletion(0);
	    project.setLastest(true);
	    project.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    if (StringUtil.checkString(data.projectName.trim())) {
		project.setPjtName(StringUtil.checkNull(data.projectName.trim()));
	    }
	    project.setPjtState(1);
	    if (data.teamType.equals("자동차 사업부")) {
		project.setPjtType(2);
	    }
	    else {
		project.setPjtType(4);
	    }

	    project.setTeamType(data.teamType);
	    project.setPartNo(data.partNo);
	    project.setCostsDate(data.costsDate);

	    //Number Code
	    NumberCode producttype2Level = null;
	    NumberCode rank2Level = null;

	    if (data.teamType.equals("자동차 사업부")) {
		producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "자동차", data.producttype.substring(6));
		rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "자동차", data.rank.substring(6));
	    }
	    else {
		producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "전자", data.producttype.substring(5));
		rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "전자", data.rank.substring(5));
	    }

	    if (producttype2Level != null) {
		project.setProductType(producttype2Level);
	    }
	    if (rank2Level != null) {
		project.setRank(rank2Level);
	    }
	    NumberCode assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
	    if (assembledtype1Level != null) {
		project.setAssembledType(assembledtype1Level);
	    }
	    NumberCode process = NumberCodeHelper.manager.getNumberCodeName("PROCESS", data.process);
	    if (process != null) {
		project.setProcess(process);
	    }
	    NumberCode developentType = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
	    if (developentType != null) {
		project.setDevelopentType(developentType);
	    }
	    NumberCode designType = NumberCodeHelper.manager.getNumberCodeName("DESIGNTYPE", data.designType);
	    if (designType != null) {
		project.setDesignType(designType);
	    }

	    QueryResult modelQr = OEMTypeHelper.getOEMProjectTypeName("CARTYPE", data.model);
	    if (modelQr != null) {
		if (modelQr.hasMoreElements()) {
		    OEMProjectType modelType = (OEMProjectType) modelQr.nextElement();
		    if (modelType != null) {
			project.setOemType(modelType);
		    }
		}
	    }

	    if (data.assemblyPlace1 != null) {
		if ("사내".equals(data.assemblyPlace1)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCodeName("PRODUCTIONDEPT", data.assemblyPlace2);
		    if (code != null) project.setAssemblyPlace(code);
		}
		else {
		    //	   				   PartnerDao pDao = new PartnerDao();
		    //	   				   String proteamNo = pDao.ViewPartnerNo(data.assemblyPlace2);
		    String proteamNo = ProductHelper.ViewPartnerNo(data.assemblyPlace2);
		    if (proteamNo != null && proteamNo.length() > 0) project.setPartnerNo(proteamNo);
		}
	    }

	    //Development Department
	    People devPeople = PeopleHelper.manager.getPeople(data.pmID);
	    WTUser wtdevUser = null;
	    if (devPeople != null) {
		PeopleData pData = new PeopleData(devPeople);
		wtdevUser = (WTUser) pData.wtuser;
	    }
	    if (wtdevUser != null) ProjectUserHelper.manager.setPM(project, wtdevUser, 0);

	    // salesUser
	    People salesPeople = PeopleHelper.manager.getPeople(data.salesID);
	    if (salesPeople != null) {
		PeopleData pData = new PeopleData(salesPeople);
		WTUser wtsalesUser = (WTUser) pData.wtuser;
		if (wtsalesUser != null) {
		    project.setBusiness(wtsalesUser);
		}
	    }

	    QueryResult departmentQr = ProjectUserHelper.manager.getViewDepartmentLink(project, null);
	    if (departmentQr.hasMoreElements()) {
		Object[] objDept = (Object[]) departmentQr.nextElement();
		ProjectViewDepartmentLink link = (ProjectViewDepartmentLink) objDept[0];
		PersistenceHelper.manager.delete(link);
	    }
	    Department depart = DepartmentHelper.manager.getDepartmentObj(data.devDeptName);
	    if (depart != null) {
		ArrayList list = new ArrayList();
		list.add(depart);
		boolean flag = ProjectHelper.addRefDepartment(project, list);
	    }

	    //save
	    project = (ProductProject) PersistenceHelper.manager.save(project);

	    String ce = data.customereventName;
	    String sc = data.subcontractorName;
	    StringTokenizer cetoken = new StringTokenizer(ce, ",");
	    int token1 = 0;
	    String ceValue[] = new String[cetoken.countTokens()];
	    NumberCode nc = null;
	    QueryResult result = null;
	    QuerySpec qs = new QuerySpec();
	    int index = qs.appendClassList(ProjectCustomereventLink.class, true);
	    qs.appendWhere(new SearchCondition(ProjectCustomereventLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(project)), new int[] { index });
	    result = PersistenceHelper.manager.find(qs);

	    while (result.hasMoreElements()) {
		Object[] object = (Object[]) result.nextElement();
		ProjectCustomereventLink link = (ProjectCustomereventLink) object[0];
		if (link != null) {
		    PersistenceHelper.manager.delete(link);
		}
	    }

	    while (cetoken.hasMoreElements()) {
		ceValue[token1] = cetoken.nextToken();
		nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", ceValue[token1]);
		if (nc != null) {
		    ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(nc, project);
		    PersistenceHelper.manager.save(plink);
		}
		else {
		    //	        		   throw new Exception("최종 사용처 "+ ceValue[token1]+" 정보가 없습니다.");
		}
		token1++;
	    }

	    StringTokenizer sctoken = new StringTokenizer(sc, ",");
	    int token2 = 0;
	    String scValue[] = new String[sctoken.countTokens()];
	    QueryResult result2 = null;
	    QuerySpec qs2 = new QuerySpec();
	    int index2 = qs2.appendClassList(ProjectSubcontractorLink.class, true);
	    qs2.appendWhere(new SearchCondition(ProjectSubcontractorLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(project)), new int[] { index2 });
	    result2 = PersistenceHelper.manager.find(qs2);

	    while (result2.hasMoreElements()) {
		Object[] object = (Object[]) result2.nextElement();
		ProjectSubcontractorLink link = (ProjectSubcontractorLink) object[0];
		if (link != null) {
		    PersistenceHelper.manager.delete(link);
		}
	    }

	    while (sctoken.hasMoreElements()) {
		scValue[token2] = sctoken.nextToken();
		nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", scValue[token2]);
		if (nc != null) {
		    ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(nc, project);
		    PersistenceHelper.manager.save(plink);
		}
		else {
		    //	        		   throw new Exception("개발검토 의뢰처  "+ scValue[token2]+" 정보가 없습니다.");
		}
		token2++;
	    }

	    project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), false);

	} catch (Exception e) {
	    Kogger.error(ProductProjectDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static Object save(Vector data) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Vector.class };
	    Object args[] = new Object[] { data };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", ProductProjectDataLoader.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProductProjectDataLoader.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProductProjectDataLoader.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Transaction trx = new Transaction();

	Vector r = new Vector();
	try {
	    trx.start();

	    for (int i = 0; i < data.size(); i++) {
		ProductProjectData ppData = (ProductProjectData) data.get(i);

		Kogger.debug(ProductProjectDataLoader.class, "=========여기 실행되냐>");

		try {
		    if (ppData.isExist) {
			Kogger.debug(ProductProjectDataLoader.class, "=========여기 update()");
			ppData = update(ppData);
		    }
		    else {
			Kogger.debug(ProductProjectDataLoader.class, "=========여기 save()");
			ppData = save((ProductProjectData) data.get(i));
		    }
		    ppData.isLoadCompleted = true;
		} catch (Exception e) {
		    ppData.isLoadCompleted = false;
		    ppData.msg += "\n" + e.getLocalizedMessage();
		    throw new Exception(e);
		}

		r.add(ppData);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(ProductProjectDataLoader.class, e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return r;
    }


    public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	ProductProjectDataLoader loader = new ProductProjectDataLoader();
	//		String type = "전자";
	//		loader.loadFile("제품프로젝트_V1.0_전자_101120.xls", type);
	wt.method.RemoteMethodServer.getDefault().setUserName(args[1]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[2]);
	loader.loadFile(args[0]);

    }

}

class ProductProjectData implements Serializable {

    /**
	 * 
	 */
    /*	private static final long serialVersionUID = 1L;
    */
    public int       rownum;
    public String    projectNo;
    public String    projectName;
    public String    salesID;
    public String    teamType;
    public String    devDeptName;
    public String    pmID;
    public String    partNo;
    public String    producttype;
    public String    rank;
    public String    model;
    public String    assembledtype;
    public String    process;
    public String    developenttype;
    public String    assemblyPlace1;
    public String    assemblyPlace2;
    public String    designType;
    public Timestamp planStartDate;
    public Timestamp planEndDate;
    public String    customereventName;
    public String    subcontractorName;
    public Timestamp costsDate;
    public String    state;

    public String    msg;

    public boolean   isExist         = false;
    /*public boolean isDocNumberSizeValid = true; */
    public boolean   isValidate      = true;
    /*public boolean isRepPartExist = true;
    */
    public boolean   isLoadCompleted = false;

    public ProductProjectData(Cell[] cells, int rownum) {
	this.rownum = rownum;

	int colIdex = -1;
	projectNo = JExcelUtil.getContent(cells, ++colIdex);
	projectName = JExcelUtil.getContent(cells, ++colIdex);
	salesID = JExcelUtil.getContent(cells, ++colIdex);
	teamType = JExcelUtil.getContent(cells, ++colIdex);
	devDeptName = JExcelUtil.getContent(cells, ++colIdex);
	pmID = JExcelUtil.getContent(cells, ++colIdex);
	partNo = JExcelUtil.getContent(cells, ++colIdex);
	producttype = JExcelUtil.getContent(cells, ++colIdex);
	rank = JExcelUtil.getContent(cells, ++colIdex);
	model = JExcelUtil.getContent(cells, ++colIdex);
	assembledtype = JExcelUtil.getContent(cells, ++colIdex);
	process = JExcelUtil.getContent(cells, ++colIdex);
	developenttype = JExcelUtil.getContent(cells, ++colIdex);
	assemblyPlace1 = JExcelUtil.getContent(cells, ++colIdex);
	assemblyPlace2 = JExcelUtil.getContent(cells, ++colIdex);
	designType = JExcelUtil.getContent(cells, ++colIdex);
	planStartDate = JExcelUtil.getTimestamp(cells, ++colIdex, "yy/MM/dd");

	planEndDate = JExcelUtil.getTimestamp(cells, ++colIdex, "yy/MM/dd");


	customereventName = JExcelUtil.getContent(cells, ++colIdex);
	subcontractorName = JExcelUtil.getContent(cells, ++colIdex);

	costsDate = JExcelUtil.getTimestamp(cells, ++colIdex, "yy/MM/dd");

	state = JExcelUtil.getContent(cells, ++colIdex);
    }

}
