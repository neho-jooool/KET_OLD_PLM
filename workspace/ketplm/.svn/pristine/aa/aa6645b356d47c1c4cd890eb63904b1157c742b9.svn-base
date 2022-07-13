package ext.ket.wfm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.edm.dao.EPMDocumentDao;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.project.dao.IssueDao;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.common.files.util.DownloadView;
import ext.ket.cost.entity.CostLogistics;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.wfm.entity.MyDocumentDTO;
import ext.ket.wfm.entity.MyProjectDTO;
import ext.ket.wfm.entity.MyTaskDTO;
import ext.ket.wfm.service.KETWorkflowHelper;
import ext.ket.wfm.service.KETWorkspaceHelper;

public class WorkSpaceUtil {
    public static final WorkSpaceUtil manager = new WorkSpaceUtil();

    public List<Map<String, Object>> getTotalWorkList(Map<String, Object> reqMap) throws Exception {
	String oid = (String) reqMap.get("userOid");
	WTUser targetUser = (WTUser) CommonUtil.getObject(oid);
	List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();

	People targetPeple = null;
	String retire = (String)reqMap.get("retireCheck");
	boolean goFlag = true;
	
	String dateFrom = (String) reqMap.get("createDateFrom");
	String dateTo = (String) reqMap.get("createDateTo");
	
	if (targetUser != null) {

	    PeopleData peo = new PeopleData(targetUser);

	    targetPeple = peo.people;
	    if("on".equals(retire) && !peo.isDiable){
		goFlag = false;
	    }
	    if(goFlag){
		this.extractWorkbyPeople(targetPeple, totalList);
	    }
	    

	} else {
	    Department dept = (Department) CommonUtil.getObject((String) reqMap.get("deptCode"));

	    QueryResult qr = null;

	    if (dept != null) {
		
		if("on".equals(retire)){
		    qr = DepartmentHelper.manager.getDepartmentPeople(dept, "", true, dateFrom, dateTo);    
		}else{
		    qr = DepartmentHelper.manager.getDepartmentPeople(dept, "", false);
		}
			
		
		while (qr.hasMoreElements()) {
		    Object[] data = (Object[]) qr.nextElement();
		    People pdata = (People) data[0];
		    if(pdata == null){
			continue;
		    }

		    this.extractWorkbyPeople(pdata, totalList);
		}
	    } else {
		if ("on".equals(retire)) {

		    QuerySpec qs = new QuerySpec(People.class);

		    qs.appendWhere(new SearchCondition(People.class, "isDisable", SearchCondition.IS_TRUE), new int[] { 0 });

		    ext.ket.shared.util.SearchUtil.appendTimeFrom(qs, People.class, "retrireDate", DateUtil.convertStartDate2(dateFrom), 0);
		    ext.ket.shared.util.SearchUtil.appendTimeTo(qs, People.class, "retrireDate", DateUtil.convertEndDate2(dateTo), 0);

		    qr = PersistenceHelper.manager.find(qs);

		    while (qr.hasMoreElements()) {
		    People pdata = (People) qr.nextElement();
			if (pdata == null) {
			    continue;
			}

			this.extractWorkbyPeople(pdata, totalList);
		    }

		}
	    }
	    
	    
	    
	}

	return totalList;

    }

    public void extractWorkbyPeople(People pdata, List<Map<String, Object>> totalList) throws Exception {

	Connection conn = null;

	int taskCnt = 0;
	int pjtCnt = 0;
	int todoCnt = 0;
	int docCnt = 0;
	int issueCnt = 0;
	int partCnt = 0;
	int epmCnt = 0;
	int workCnt = 0;
	int ecmCnt = 0;

	SessionContext current = null;
	String userId = "";
	String deptName = "";
	String userName = "";
	String oid = "";
	
	boolean goFlag = true;
	
	try {

	    conn = PlmDBUtil.getConnection();

	    current = SessionContext.getContext();

	    userId = pdata.getId();
	    if(pdata.getDepartment() != null){
		deptName = pdata.getDepartment().getName();
	    }
	    
	    userName = pdata.getName();
	    oid = CommonUtil.getOIDString(pdata.getUser());
	    
	    try{
		SessionHelper.manager.setPrincipal(userId);
	    }catch(Exception e){
		
		goFlag = false;
		System.out.println("오류 발생 USER -----> : " + userId);
	    }
	    
	    if (goFlag) {
		// 1. Task 건수 조회 Start
		MyTaskDTO taskDto = new MyTaskDTO();

		NumberCode code = NumberCodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PROGRESS");

		String SearchPjtState = CommonUtil.getOIDLongValue2Str(code);
		code = NumberCodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PLANCHANGE");

		SearchPjtState = SearchPjtState + "," + CommonUtil.getOIDLongValue2Str(code);

		code = NumberCodeHelper.manager.getNumberCode("MYTASKCOMPLETE", "0");

		String searchState = CommonUtil.getOIDLongValue2Str(code);

		taskDto.setSearchPjtState(SearchPjtState);// 프로젝트 상태 (진행 / 일정변경)
		taskDto.setSearchState(searchState);// Task 상태 (진행)
		taskDto.setCommand("listMyTask");
		taskDto.setOnlyResult(true);
		List<BaseDTO> list = KETWorkspaceHelper.service.find(taskDto);
		if (list != null) {
		    taskDto = (MyTaskDTO) ((BaseDTO) list.get(0));
		    taskCnt = taskDto.getTotalSize();
		}
		// Task 건수 조회 End

		// 2. Proeject 건수 조회 Start
		MyProjectDTO pjtDto = new MyProjectDTO();

		// code = NumberCodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PMOINWORK");
		// SearchPjtState = SearchPjtState + "," + CommonUtil.getOIDLongValue2Str(code);
		pjtDto.setSearchPjtState(SearchPjtState);// 프로젝트 상태 (진행 / 일정변경)
		pjtDto.setCommand("listMyProject");
		pjtDto.setOnlyResult(true);
		// pjtDto.setIsPm("Y"); 프로젝트 PM만 SELECT 하려면 주석풀것
		list = KETWorkspaceHelper.service.find(pjtDto);

		if (list != null && list.size() > 0) {
		    pjtDto = (MyProjectDTO) ((BaseDTO) list.get(0));
		    pjtCnt = pjtDto.getTotalSize();
		}
		// 2. Proeject 건수 조회 End

		// 3. To-do 건수 조회 Start
		todoCnt = KETWorkspaceHelper.service.getFilterUncompletedTodoCount();
		// 3. To-do 건수 조회 End

		// 4. 문서 건수 조회 Start
		MyDocumentDTO docDto = new MyDocumentDTO();
		docDto.setOnlyResult(true);
		docDto.setState("INWORK,REWORK");
		docDto.setVersion("671979013");
		docDto.setCommand("listMyDocument");
		list = KETWorkspaceHelper.service.find(docDto);
		if (list != null && list.size() > 0) {
		    docDto = (MyDocumentDTO) ((BaseDTO) list.get(0));
		    docCnt = docDto.getTotalSize();
		}

		// 4. 문서 건수 조회 End

		// 5. issue 건수 조회 Start

		IssueDao dao = new IssueDao(conn);

		List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("myIssueList", "all");
		paramMap.put("user", oid);
		paramMap.put("category", "0");
		conditionList.add(paramMap);
		List<Map<String, Object>> issueList = dao.searchIssueList(conditionList);
		if (issueList != null && list.size() > 0) {
		    issueCnt = issueList.size();
		}
		// 5. issue 건수 조회 End

		// 6. part 건수 조회 Start
		// PartSearchMainDTO partDto = new PartSearchMainDTO();
		// partDto.setCreatorOid(oid);
		// partDto.setPartStateCode("INWORK");
		// partDto.setPartLatestReVision(true);
		// partDto.setOnlyResult(true);
		//
		// PageControl pageControl = PartBaseHelper.service.searchMainPartList(partDto, "-1");
		// if(pageControl != null){
		// partCnt = pageControl.getTotalCount();
		// }

		// 6. part 건수 조회 End

		// 7. 도면 건수 조회 Start
		EPMDocumentDao epmDao = new EPMDocumentDao();

		List<Map> epmList = new ArrayList<Map>();
		Map epmMap = new HashMap();
		epmMap.put("creator", oid);
		epmMap.put("latest", "ok");
		String state[] = { "INWORK", "REJECTED", "REWORK" };
		epmMap.put("state", state);
		epmList.add(epmMap);

		epmCnt = epmDao.searchEPMDocumentCount(epmList);

		// 7. 도면 건수 조회 End

		// 8. 결재대기함 조회 Start

		workCnt = KETWorkflowHelper.service.getFilterUncompletedWorkItemCount();

		// 8. 결재대기함 조회 End

		// 9. ECM 건수 조회 Start

		ecmCnt = EcmSearchHelper.manager.getEcoListByUserCount();

		// 9. ECM 건수 조회 End

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("deptName", deptName);
		dataMap.put("userName", userName);
		dataMap.put("task", taskCnt);
		dataMap.put("project", pjtCnt);
		dataMap.put("todo", todoCnt);
		dataMap.put("doc", docCnt);
		dataMap.put("issue", issueCnt);
		dataMap.put("part", partCnt);
		dataMap.put("epm", epmCnt);
		dataMap.put("workitem", workCnt);
		dataMap.put("ecm", ecmCnt);
		String retire = "";
		String retireDate = "";
		if (pdata.isIsDisable()) {
		    retire = "퇴사";
		    retireDate = DateUtil.getTimeFormat(pdata.getRetrireDate() , "yyyy-MM-dd");
		}
		dataMap.put("retire", retire);
		dataMap.put("retireDate", retireDate);
		totalList.add(dataMap);
	    }
	    

	} catch (Exception e) {
	    
	    e.printStackTrace();
	    throw e;

	} finally {
	    SessionContext.setContext(current);
	    PlmDBUtil.close(conn);
	}
    }

    public Map<String, Object> createWorkListExcel(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> totalList = this.getTotalWorkList(reqMap);

	LinkedHashMap<String, Object> cols = new LinkedHashMap<String, Object>();
	cols.put("deptName", "부서");
	cols.put("userName", "이름");
	cols.put("workitem", "결재대기함");
	cols.put("todo", "TO-DO");
	cols.put("task", "Task");
	cols.put("project", "Project");
	cols.put("doc", "Document");
	cols.put("issue", "Issue");
	cols.put("epm", "도면");
	cols.put("ecm", "ECM");
	cols.put("retire", "퇴사여부");
	cols.put("retireDate", "퇴사일");

	Map<String, Object> resMap = new HashMap<String, Object>();

	XSSFWorkbook wb = new XSSFWorkbook();

	Font font = wb.createFont();
	font.setBoldweight((short) 1000);
	CellStyle style = wb.createCellStyle();
	style.setFont(font);
	style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	style.setWrapText(true);

	XSSFSheet sheet = wb.createSheet("WORK_LIST");
	sheet.setDefaultColumnWidth(12);

	int rowCnt = 0;
	XSSFRow header1 = sheet.createRow(rowCnt++);
	XSSFRow header2 = sheet.createRow(rowCnt++);

	Set<String> st = cols.keySet();

	Iterator<String> it = st.iterator();
	int i = 0;
	
	String headerKey[];
	
	while (it.hasNext()) {
	    String key = (String) it.next();
	    String label = (String) cols.get(key);

	    XSSFCell cell = header1.createCell(i);
	    cell.setCellValue(label);
	    cell.setCellStyle(style);
	    cell = header2.createCell(i);
	    cell.setCellStyle(style);

	    sheet.addMergedRegion(new CellRangeAddress(header1.getRowNum(), header2.getRowNum(), i, i));
	    i++;
	}

	i = 0;
	it = st.iterator();

	DataFormat poiFormat = wb.createDataFormat();

	style = wb.createCellStyle();
	style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	style.setWrapText(true);

	CellStyle dateStyle = wb.createCellStyle();
	dateStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	dateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	dateStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

	CellStyle numStyle = wb.createCellStyle();
	numStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	numStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	numStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

	for (Map<String, Object> data : totalList) {
	    XSSFRow row = sheet.createRow(rowCnt++);

	    while (it.hasNext()) {
		String key = (String) it.next();
		String value = String.valueOf(data.get(key));
		
		XSSFCell cell = row.createCell(i);
		cell.setCellStyle(style);

		if (StringUtil.checkString(value)) {

		    try {

			int num = Integer.parseInt(value);
			numStyle.setDataFormat(poiFormat.getFormat("0"));
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellStyle(numStyle);
			cell.setCellValue(num);

		    } catch (NumberFormatException ex) {

			try {

			    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			    dateStyle.setDataFormat(poiFormat.getFormat("yyyy-MM-dd"));
			    cell.setCellStyle(dateStyle);
			    cell.setCellValue(date);

			} catch (ParseException e) {

			    cell.setCellType(Cell.CELL_TYPE_STRING);
			    cell.setCellStyle(style);
			    cell.setCellValue(value);

			}
		    }

		}
		i++;
	    }
	    row.setHeight((short) 700);
	    i=0;
	    it = st.iterator();
	}

	Calendar cal = Calendar.getInstance();
	String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());

	String fileName = "WORK_LIST" + currentTime + ".xlsx";
	FileOutputStream fos = new FileOutputStream(DownloadView.TEMPPATH + File.separator + fileName);
	wb.write(fos);
	fos.close();

	String downloadLink = "/plm/ext/download?path=/TEMP/" + fileName;
	resMap.put("downloadLink", downloadLink);

	return resMap;

    }
}
