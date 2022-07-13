package ext.ket.dashboard.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.web.PageControl;
import e3ps.cost.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.PeopleData;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateTask;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProductProjectHelper;
import e3ps.project.beans.ProjectIssueData;
import e3ps.project.beans.ProjectOutputData;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.sap.ProductPrice;
import e3ps.project.sap.SAPMoldPrice;
import ext.ket.dashboard.entity.DashBoardDTO;
import ext.ket.dashboard.entity.DevTypeDTO;
import ext.ket.dashboard.entity.ProductProjectDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.project.program.service.StandardProgramService;
import ext.ket.project.task.entity.AssessTaskResultDTO;
import ext.ket.project.task.entity.TrustTaskResultDTO;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.dao.CommonDao;
import ext.ket.shared.log.Kogger;

@Service
public class DashBoardService {

    @Inject
    private CommonDao dao;

    @SuppressWarnings({ "unchecked" })
    public Map<String, Object> ganttChart(DashBoardDTO dashBoardDTO) throws Exception {

	// 기간 Type 설정
	String termType = dashBoardDTO.getTermType();
	int termLength = 0;
	int calMonth = 0;
	int startMonth = 0;
	int endMonth = 0;
	int year = 0;
	int startYear = 0;
	int endYear = 0;
	boolean beforeYear = false;
	boolean afterYear = false;
	int day = 0;

	if ("M".equals(termType)) {
	    termLength = 12;
	    calMonth = 1;
	    year = dashBoardDTO.getYear();
	    String planStartDate = String.valueOf(year) + "/01/01";
	    String planEndDate = String.valueOf(year) + "/12/31";
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date to = transFormat.parse(planStartDate);
	    dashBoardDTO.setPlanStartDate(to);
	    transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    to = transFormat.parse(planEndDate);
	    dashBoardDTO.setPlanEndDate(to);
	} else if ("W".equals(termType)) {
	    termLength = 3;
	    year = dashBoardDTO.getYear();
	    calMonth = dashBoardDTO.getMonth() - 1;

	    if (calMonth == 0) {
		startMonth = 12;
		startYear = year - 1;
		beforeYear = true;
	    }

	    if (calMonth == 11) {
		endMonth = 1;
		endYear = year + 1;
		afterYear = true;
	    }

	    day = 0;
	    String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	    switch (calMonth + 2) {
	    case 1:
	    case 3:
	    case 5:
	    case 7:
	    case 8:
	    case 10:
	    case 12:
		day = 31; // 31일까지 있는 달
		break;
	    case 4:
	    case 6:
	    case 9:
	    case 11:
		day = 30; // 30일까지 있는 달
		break;
	    case 2: // 평년 2월은 28일, 윤년은 29일
		day = yoonDal.equals("28") ? 28 : 29;
		break;
	    default: // 1~12월의 유효성검사
		Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    }
	    String planStartDate = "";
	    String planEndDate = "";
	    if (beforeYear) {
		planStartDate = String.valueOf(startYear) + "/" + String.valueOf(startMonth) + "/01";
	    } else {
		planStartDate = String.valueOf(year) + "/" + String.valueOf(calMonth) + "/01";
	    }

	    if (afterYear) {
		planEndDate = String.valueOf(endYear) + "/" + String.valueOf(endMonth) + "/" + day;
	    } else {
		planEndDate = String.valueOf(year) + "/" + String.valueOf(calMonth + 2) + "/" + day;
	    }
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date to = transFormat.parse(planStartDate);
	    dashBoardDTO.setPlanStartDate(to);
	    transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    to = transFormat.parse(planEndDate);
	    dashBoardDTO.setPlanEndDate(to);
	} else if ("D".equals(termType)) {
	    termLength = 1;
	    year = dashBoardDTO.getYear();
	    calMonth = dashBoardDTO.getMonth();
	    day = 0;
	    String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	    switch (calMonth) {
	    case 1:
	    case 3:
	    case 5:
	    case 7:
	    case 8:
	    case 10:
	    case 12:
		day = 31; // 31일까지 있는 달
		break;
	    case 4:
	    case 6:
	    case 9:
	    case 11:
		day = 30; // 30일까지 있는 달
		break;
	    case 2: // 평년 2월은 28일, 윤년은 29일
		day = yoonDal.equals("28") ? 28 : 29;
		break;
	    default: // 1~12월의 유효성검사
		Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    }
	    String planStartDate = String.valueOf(year) + "/" + String.valueOf(calMonth) + "/01";
	    String planEndDate = String.valueOf(year) + "/" + String.valueOf(calMonth) + "/" + day;
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date to = transFormat.parse(planStartDate);
	    dashBoardDTO.setPlanStartDate(to);
	    transFormat = new SimpleDateFormat("yyyy/MM/dd");
	    to = transFormat.parse(planEndDate);
	    dashBoardDTO.setPlanEndDate(to);
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	if (!"".equals(dashBoardDTO.getDieNo3())) {
	    String dieNo = KETQueryUtil.getSqlQueryForMultiSearch("INFO.DIENO", dashBoardDTO.getDieNo3(), true);
	    dashBoardDTO.setDieNo3(dieNo);
	}
	int totalCount = dao.getTotalRecordCount("mold.totalPageCount", dashBoardDTO);
	List<DashBoardDTO> chartData = dao.findPaging("mold.pagingList", dashBoardDTO, dashBoardDTO.getPage(), 25);
	List<DashBoardDTO> holiDayData = dao.find("mold.workingDayList", dashBoardDTO);
	// List<DashBoardDTO> weekData = dao.find("mold.weekList", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, String> chartAttr = new HashMap<String, String>();
	chartAttr.put("canvasbgcolor", "FFFFFF");
	chartAttr.put("canvasbgangle", "90");
	chartAttr.put("dateformat", "yyyy/mm/dd");
	chartAttr.put("ganttlinecolor", "0372AB");
	chartAttr.put("ganttlinealpha", "8");
	chartAttr.put("gridbordercolor", "0372AB");
	chartAttr.put("gridBorderAlpha", "8");
	chartAttr.put("canvasbordercolor", "ffffff");
	chartAttr.put("showshadow", "0");
	chartAttr.put("taskbarfillmix", "{light-60}");
	chartAttr.put("showFullDataTable", "0");
	chartAttr.put("animation", "0");
	chartAttr.put("chartLeftMargin", "0");
	// chartAttr.put("chartRightMargin", "0");
	chartAttr.put("chartTopMargin", "0");
	chartAttr.put("chartBottomMargin", "10");
	chartAttr.put("baseFont", "NanumGothic, Nanumgo, NanumBold, Dotum");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1;
	categoriesAttr1.put("bgcolor", "FFFFFF");
	categoriesAttr1.put("headerbgcolor", "FFFFFF");
	categoriesAttr1.put("fontcolor", "123456");
	categoriesAttr1.put("category", categoryAttr);
	// categoriesAttr1.put("font", "NanumGothic, Nanumgo, NanumBold, Dotum");
	// categoriesAttr1.put("fontSize", "11");

	// 날짜값 파라미터 받아서 처리
	for (int month = 1; month <= termLength; month++) {

	    year = dashBoardDTO.getYear();

	    day = 0;
	    String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	    if (beforeYear) {
		calMonth = startMonth;
	    }
	    switch (calMonth) {
	    case 1:
	    case 3:
	    case 5:
	    case 7:
	    case 8:
	    case 10:
	    case 12:
		day = 31; // 31일까지 있는 달
		break;
	    case 4:
	    case 6:
	    case 9:
	    case 11:
		day = 30; // 30일까지 있는 달
		break;
	    case 2: // 평년 2월은 28일, 윤년은 29일
		day = yoonDal.equals("28") ? 28 : 29;
		break;
	    default: // 1~12월의 유효성검사
		Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    }
	    Kogger.debug(getClass(), year + "/" + month + "/" + day);
	    categoryAttr1 = new HashMap<String, String>();
	    if (!"W".equals(termType)) {
		Calendar cal = Calendar.getInstance();

		int currentMonth = cal.get(cal.MONTH) + 1;

		categoryAttr1.put("start", year + "/" + calMonth + "/" + 1);
		categoryAttr1.put("end", year + "/" + calMonth + "/" + day);
		categoryAttr1.put("name", calMonth + "월");
		categoryAttr1.put("bgColor", "e2edf4");
		if (currentMonth == calMonth) {
		    categoryAttr1.put("fontColor", "#95BAF9");
		}
		categoryAttr.add(categoryAttr1);
	    }
	    if ("W".equals(termType)) {

		Calendar cal = Calendar.getInstance();

		int currentMonth = cal.get(cal.MONTH) + 1;

		if (beforeYear) {
		    categoryAttr1.put("start", startYear + "/" + startMonth + "/" + 1);
		    categoryAttr1.put("end", startYear + "/" + startMonth + "/" + day);
		    categoryAttr1.put("name", startMonth + "월");
		    categoryAttr1.put("bgColor", "e2edf4");
		    if (currentMonth == startMonth) {
			categoryAttr1.put("fontColor", "#95BAF9");
		    }
		    beforeYear = false;
		    calMonth = 0;
		} else {
		    if (afterYear && (month == termLength)) {
			categoryAttr1.put("start", endYear + "/" + endMonth + "/" + 1);
			categoryAttr1.put("end", endYear + "/" + endMonth + "/" + 31);
			categoryAttr1.put("name", endMonth + "월");
			categoryAttr1.put("bgColor", "#e2edf4");
			if (currentMonth == endMonth) {
			    categoryAttr1.put("fontColor", "#95BAF9");
			}
		    } else {
			categoryAttr1.put("start", year + "/" + calMonth + "/" + 1);
			categoryAttr1.put("end", year + "/" + calMonth + "/" + day);
			categoryAttr1.put("name", calMonth + "월");
			categoryAttr1.put("bgColor", "e2edf4");
			if (currentMonth == calMonth) {
			    categoryAttr1.put("fontColor", "#95BAF9");
			}
		    }
		}
		categoryAttr.add(categoryAttr1);
	    }

	    if (!"D".equals(termType)) {
		calMonth++;
	    }
	}

	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	// 해당월 주차 표시 (아직미정)
	// if ("W".equals(termType)) {
	// categoriesAttr1 = new HashMap<String, Object>();
	// categoryAttr = new ArrayList<Map<String, String>>();
	// for (int count = 0; count < weekData.size(); count++) {
	// categoryAttr1 = new HashMap<String, String>();
	// categoryAttr1.put("start", weekData.get(count).getWeekStart());
	// categoryAttr1.put("end", weekData.get(count).getWeekEnd());
	// categoryAttr1.put("name", weekData.get(count).getWeek());
	// categoryAttr.add(categoryAttr1);
	// }
	// categoriesAttr1.put("category", categoryAttr);
	// categoriesAttr.add(categoriesAttr1);
	// }
	// 해당월 일 표시
	if ("D".equals(termType)) {
	    categoriesAttr1 = new HashMap<String, Object>();
	    categoryAttr = new ArrayList<Map<String, String>>();
	    String holiDay = "";
	    for (int count = 1; count <= day; count++) {
		categoryAttr1 = new HashMap<String, String>();
		categoryAttr1.put("start", year + "/" + calMonth + "/" + count);
		holiDay = String.valueOf(calMonth) + String.valueOf(count);
		if (count == day) {
		    categoryAttr1.put("end", year + "/" + (calMonth + 1) + "/" + 1);
		} else {
		    categoryAttr1.put("end", year + "/" + calMonth + "/" + (count + 1));
		}
		categoryAttr1.put("name", String.valueOf(count));

		Calendar cal = Calendar.getInstance();

		String today = String.valueOf(cal.get(cal.MONTH) + 1) + String.valueOf(cal.get(cal.DATE));

		// 휴일 빨간색 처리 or 오늘 날짜 빨간색 처리
		for (int length = 0; length < holiDayData.size(); length++) {
		    if (holiDay.equals(holiDayData.get(length).getStartDate())) {
			categoryAttr1.put("fontColor", "#FA7E9A");
		    } else if (holiDay.equals(today)) {
			categoryAttr1.put("bgColor", "#95BAF9");
		    }
		}

		categoryAttr.add(categoryAttr1);
	    }
	    categoriesAttr1.put("category", categoryAttr);
	    categoriesAttr.add(categoriesAttr1);
	}

	Map<String, Object> processesAttr = new HashMap<String, Object>();
	List<Map<String, String>> processAttr = new ArrayList<Map<String, String>>();
	Map<String, String> processAttr1;
	processesAttr.put("headertext", "M/P");
	processesAttr.put("isbold", "0");
	processesAttr.put("fontcolor", "123456");
	processesAttr.put("headerbgcolor", "e2edf4");
	processesAttr.put("bgcolor", "FFFFFF");
	processesAttr.put("width", "40");
	processesAttr.put("process", processAttr);
	processesAttr.put("headerFontSize", 10);
	// 쿼리에서 파라미터값 처리
	char type = 'A';
	String beforePjtNum = "";
	String afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();
	    if (!beforePjtNum.equals(afterPjtNum)) {
		processAttr1 = new HashMap<String, String>();
		processAttr1.put("name", chartData.get(length).getMoldType());
		processAttr1.put("id", Character.toString(type));
		processAttr.add(processAttr1);
		type++;
		afterPjtNum = beforePjtNum;
	    }
	}

	List<Map<String, Object>> datatableAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datatableAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> datacolumnAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datacolumnAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> textAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "내/외");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	datacolumnAttr1.put("width", "40");
	datacolumnAttr1.put("headerFontSize", 10);
	datacolumnAttr1.put("headerLink", "Javascript:sorting('MAKING');");
	// datacolumnAttr1.put("font", "NanumGothic");

	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();
	    if (!beforePjtNum.equals(afterPjtNum)) {

		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", chartData.get(length).getMaking());
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}

	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datacolumnAttr1 = new HashMap<String, Object>();
	textAttr = new ArrayList<Map<String, Object>>();
	textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "유형");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	datacolumnAttr1.put("width", "40");
	datacolumnAttr1.put("headerFontSize", 10);
	datacolumnAttr1.put("headerLink", "Javascript:sorting('MOLDCATEGORY');");
	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();

	    if (!beforePjtNum.equals(afterPjtNum)) {
		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", chartData.get(length).getMoldCategory());
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}
	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datacolumnAttr1 = new HashMap<String, Object>();
	textAttr = new ArrayList<Map<String, Object>>();
	textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "Die.No.");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	// datacolumnAttr1.put("width", "100");
	datacolumnAttr1.put("fontcolor", "4398BC");
	datacolumnAttr1.put("link", "");
	datacolumnAttr1.put("headerFontSize", 10);
	datacolumnAttr1.put("headerLink", "Javascript:sorting('DIENO');");
	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();

	    if (!beforePjtNum.equals(afterPjtNum)) {
		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", chartData.get(length).getDieNo());
		textAttr1.put("link", "JavaScript:linkPopUp('" + chartData.get(length).getOid() + "');");
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}
	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datacolumnAttr1 = new HashMap<String, Object>();
	textAttr = new ArrayList<Map<String, Object>>();
	textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "Part No.");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	// datacolumnAttr1.put("width", "90");
	datacolumnAttr1.put("fontcolor", "4398BC");
	datacolumnAttr1.put("link", "");
	datacolumnAttr1.put("headerFontSize", 10);
	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();

	    if (!beforePjtNum.equals(afterPjtNum)) {
		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", chartData.get(length).getPartNo());
		textAttr1.put("link", "JavaScript:openViewPart('" + chartData.get(length).getPartNo() + "');");
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}
	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datacolumnAttr1 = new HashMap<String, Object>();
	textAttr = new ArrayList<Map<String, Object>>();
	textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "Part Name");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	// datacolumnAttr1.put("width", "110");
	datacolumnAttr1.put("align", "left");
	// datacolumnAttr1.put("fontcolor", "0000FF");
	datacolumnAttr1.put("headerFontSize", 10);
	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();

	    if (!beforePjtNum.equals(afterPjtNum)) {
		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", chartData.get(length).getPjtName());
		// textAttr1.put("link", "JavaScript:test('" + chartData.get(length).getPjtName() + "');");
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}
	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datacolumnAttr1 = new HashMap<String, Object>();
	textAttr = new ArrayList<Map<String, Object>>();
	textAttr1 = new HashMap<String, Object>();

	datacolumnAttr1.put("headertext", "상태");
	datacolumnAttr1.put("isbold", "0");
	datacolumnAttr1.put("bgcolor", "000000");
	datacolumnAttr1.put("headerbgcolor", "e2edf4");
	datacolumnAttr1.put("bgcolor", "FFFFFF");
	// datacolumnAttr1.put("width", "30");
	datacolumnAttr1.put("align", "center");
	// datacolumnAttr1.put("fontcolor", "0000FF");
	datacolumnAttr1.put("width", 40);
	datacolumnAttr1.put("headerFontSize", 10);
	datacolumnAttr1.put("headerLink", "Javascript:sorting('STATE');");
	// 쿼리에서 파라미터값 처리
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();

	    if (!beforePjtNum.equals(afterPjtNum)) {
		textAttr1 = new HashMap<String, Object>();
		textAttr1.put("label", State.toState(chartData.get(length).getState()).getDisplay());
		// textAttr1.put("link", "JavaScript:test('" + chartData.get(length).getState() + "');");
		textAttr.add(textAttr1);

		afterPjtNum = beforePjtNum;
	    }
	}
	datacolumnAttr1.put("text", textAttr);
	datacolumnAttr.add(datacolumnAttr1);

	datatableAttr1.put("headerbgcolor", "0372AB");
	datatableAttr1.put("datacolumn", datacolumnAttr);
	datatableAttr.add(datatableAttr1);

	List<Map<String, Object>> tasksAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> tasksAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> taskAttr = new ArrayList<Map<String, String>>();
	Map<String, String> taskAttr1;

	// 쿼리에서 파라미터 처리
	type = 'A';
	beforePjtNum = "";
	afterPjtNum = "";
	for (int length = 0; length < chartData.size(); length++) {
	    beforePjtNum = chartData.get(length).getDieNo();
	    if (length == 0) {
		taskAttr1 = new HashMap<String, String>();
		taskAttr1.put("name", chartData.get(length).getTaskName());
		taskAttr1.put("processid", Character.toString(type));

		DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date start = new Date();
		start = chartData.get(length).getPlanStartDate();
		String startDate = sdFormat.format(start);
		Date end = new Date();
		end = chartData.get(length).getPlanEndDate();
		String endDate = sdFormat.format(end);
		taskAttr1.put("start", startDate);
		taskAttr1.put("end", endDate);
		taskAttr1.put("bordercolor", chartData.get(length).getColor());
		taskAttr1.put("color", chartData.get(length).getColor());
		taskAttr1.put("tooltext",
		        chartData.get(length).getTaskName() + ", " + startDate + " - " + sdFormat.format(getPreviousDate(end)));
		taskAttr.add(taskAttr1);
		afterPjtNum = beforePjtNum;
	    } else if (beforePjtNum.equals(afterPjtNum)) {
		taskAttr1 = new HashMap<String, String>();
		taskAttr1.put("name", chartData.get(length).getTaskName());
		taskAttr1.put("processid", Character.toString(type));

		DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date start = new Date();
		start = chartData.get(length).getPlanStartDate();
		String startDate = sdFormat.format(start);
		Date end = new Date();
		end = chartData.get(length).getPlanEndDate();
		String endDate = sdFormat.format(end);
		taskAttr1.put("start", startDate);
		taskAttr1.put("end", endDate);
		taskAttr1.put("bordercolor", chartData.get(length).getColor());
		taskAttr1.put("color", chartData.get(length).getColor());
		taskAttr1.put("tooltext",
		        chartData.get(length).getTaskName() + ", " + startDate + " - " + sdFormat.format(getPreviousDate(end)));
		taskAttr.add(taskAttr1);
		afterPjtNum = beforePjtNum;

	    } else {
		type++;
		taskAttr1 = new HashMap<String, String>();
		taskAttr1.put("name", chartData.get(length).getTaskName());
		taskAttr1.put("processid", Character.toString(type));

		DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date start = new Date();
		start = chartData.get(length).getPlanStartDate();
		String startDate = sdFormat.format(start);
		Date end = new Date();
		end = chartData.get(length).getPlanEndDate();
		String endDate = sdFormat.format(end);
		taskAttr1.put("start", startDate);
		taskAttr1.put("end", endDate);
		taskAttr1.put("bordercolor", chartData.get(length).getColor());
		taskAttr1.put("color", chartData.get(length).getColor());
		taskAttr1.put("tooltext",
		        chartData.get(length).getTaskName() + ", " + startDate + " - " + sdFormat.format(getPreviousDate(end)));
		taskAttr.add(taskAttr1);
		afterPjtNum = beforePjtNum;

	    }
	}

	tasksAttr1.put("task", taskAttr);
	tasksAttr.add(tasksAttr1);

	List<Map<String, Object>> legendAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> legendAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> itemAttr = new ArrayList<Map<String, String>>();
	Map<String, String> itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "금형설계");
	itemAttr1.put("color", "17375E");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "외주금형제작");
	itemAttr1.put("color", "cc5c00");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "부품가공");
	itemAttr1.put("color", "3373c3");
	itemAttr.add(itemAttr1);
	// itemAttr1 = new HashMap<String, String>();
	// itemAttr1.put("label", "금형입고");
	// itemAttr1.put("color", "e39859");
	// itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "금형조립");
	itemAttr1.put("color", "6b9cd9");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "금형Try");
	itemAttr1.put("color", "50691a");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "디버깅");
	itemAttr1.put("color", "91ac57");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "금형검수");
	itemAttr1.put("color", "b3c987");
	itemAttr.add(itemAttr1);
	itemAttr1 = new HashMap<String, String>();
	itemAttr1.put("label", "완료");
	itemAttr1.put("color", "9b9b9b");
	itemAttr.add(itemAttr1);
	legendAttr1.put("item", itemAttr);
	legendAttr.add(legendAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("processes", processesAttr);
	data.put("datatable", datatableAttr);
	data.put("tasks", tasksAttr);
	data.put("legend", legendAttr);
	data.put("totalPageCount", totalCount);
	return data;

    }

    public static Date getPreviousDate(Date date) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);

	cal.add(Calendar.DATE, -1);

	return cal.getTime();
    }

    public List<DashBoardDTO> mainScheduleProgressPjtCount(DashBoardDTO dashBoardDTO) throws Exception {

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> mainScheduleData = dao.find("mold.mainScheduleProjectCount", dashBoardDTO);

	return mainScheduleData;
    }

    public Map<String, Object> multiColumChart(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	dashBoardDTO.setStartDate(start);
	dashBoardDTO.setEndDate(end);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> moldPressData = dao.find("mold.moldPressData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Mold");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Press");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	int moldCompleteCount = 0, moldProcessCount = 0, pressCompleteCount = 0, pressProcessCount = 0;
	int totalMoldCount = 0, totalPressCount = 0;

	for (int length = 0; length < moldPressData.size(); length++) {
	    if ("Mold".equals(moldPressData.get(length).getMoldType()) && "COMPLETED".equals(moldPressData.get(length).getState())) {
		moldCompleteCount = moldPressData.get(length).getNum();
	    } else if ("Press".equals(moldPressData.get(length).getMoldType()) && "COMPLETED".equals(moldPressData.get(length).getState())) {
		pressCompleteCount = moldPressData.get(length).getNum();
	    } else if ("Mold".equals(moldPressData.get(length).getMoldType()) && "PROGRESS".equals(moldPressData.get(length).getState())) {
		moldProcessCount = moldPressData.get(length).getNum();
	    } else if ("Press".equals(moldPressData.get(length).getMoldType()) && "PROGRESS".equals(moldPressData.get(length).getState())) {
		pressProcessCount = moldPressData.get(length).getNum();
	    }
	}

	totalMoldCount = moldCompleteCount + moldProcessCount;
	totalPressCount = pressCompleteCount + pressProcessCount;

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	datasetAttr1.put("seriesName", "전체");
	datasetAttr1.put("color", "5ba6b9");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalMoldCount);
	setAttr1.put("link", "Javascript:linkPopUp4('T','M')");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalPressCount);
	setAttr1.put("link", "Javascript:linkPopUp4('T','P')");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "완료");
	datasetAttr1.put("color", "89c211");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldCompleteCount);
	setAttr1.put("link", "Javascript:linkPopUp4('C','M')");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressCompleteCount);
	setAttr1.put("link", "Javascript:linkPopUp4('C','P')");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "진행");
	datasetAttr1.put("color", "6482c0");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("link", "Javascript:linkPopUp4('P','M')");
	setAttr1.put("color", "6482c0");
	setAttr1.put("value", moldProcessCount);
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressProcessCount);
	setAttr1.put("link", "Javascript:linkPopUp4('P','P')");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> stackColumChart(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> yearData = dao.find("mold.lastAndthisYear", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "이월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "신규");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	for (int length = 0; length < yearData.size(); length++) {
	    if ("Press".equals(yearData.get(length).getMoldType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('" + yearData.get(length).getType() + "','"
		        + yearData.get(length).getMoldType() + "');");
		dataAttr.add(dataAttr1);
	    }
	}

	datasetAttr1.put("seriesname", "Press");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FF0000");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();
	for (int length = 0; length < yearData.size(); length++) {
	    if ("Mold".equals(yearData.get(length).getMoldType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('" + yearData.get(length).getType() + "','"
		        + yearData.get(length).getMoldType() + "');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "Mold");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> doughnutChart(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	@SuppressWarnings("unchecked")
	List<ProductProjectDTO> outsourcingMoldData = dao.find("mold.outsourcingMoldData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("pieRadius", "80");
	// chartAttr.put("showValues", "1");
	// chartAttr.put("animation", "0");
	// chartAttr.put("showPercentValues", "0");
	// chartAttr.put("pieYScale", "80");
	chartAttr.put("showBorder", "0");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("plotBorderAlpha", "0");
	// chartAttr.put("toolTipBorderThickness", "0");
	// chartAttr.put("legendShadow", "0");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("enableRotation", "1");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("chartTopMargin", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "ff0000");
	// dataAttr.add(dataAttr1);

	for (int length = 0; length < outsourcingMoldData.size(); length++) {
	    dataAttr1 = new HashMap<String, Object>();
	    dataAttr1.put("label", outsourcingMoldData.get(length).getOutsourcing());
	    dataAttr1.put("value", outsourcingMoldData.get(length).getNum());
	    // dataAttr1.put("link", "Javascript:linkPopUp('Mold','" + outsourcingMoldData.get(length).getOutsourcing() + "');");
	    dataAttr.add(dataAttr1);
	}

	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    public Map<String, Object> doughnutChart1(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	@SuppressWarnings("unchecked")
	List<ProductProjectDTO> outsourcingPressData = dao.find("mold.outsourcingPressData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, String> chartAttr = new HashMap<String, String>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("pieRadius", "80");
	// chartAttr.put("showValues", "1");
	// chartAttr.put("animation", "0");
	// chartAttr.put("showPercentValues", "0");
	// chartAttr.put("pieYScale", "80");
	chartAttr.put("showBorder", "0");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("enableRotation", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("enableRotation", "1");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("chartTopMargin", "0");
	// chartAttr.put("toolTipBorderThickness", "0");
	// chartAttr.put("legendShadow", "0");
	// chartAttr.put("legendBorderAlpha", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "00ff00");
	// dataAttr.add(dataAttr1);

	for (int length = 0; length < outsourcingPressData.size(); length++) {
	    dataAttr1 = new HashMap<String, Object>();
	    dataAttr1.put("label", outsourcingPressData.get(length).getOutsourcing());
	    dataAttr1.put("value", outsourcingPressData.get(length).getNum());
	    // dataAttr1.put("link", "Javascript:linkPopUp('Press','" + outsourcingPressData.get(length).getOutsourcing() + "');");
	    dataAttr.add(dataAttr1);
	}

	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    public List<ProductProjectDTO> productPjtOverallStatus() {

	@SuppressWarnings("unchecked")
	List<ProductProjectDTO> productPjtData = dao.find("product.productPjtOverallStatus");
	List<ProductProjectDTO> productPjtData1 = new ArrayList<ProductProjectDTO>();

	String beforePjtNo = "";
	String afterPjtNo = "";
	String beforeDetailCar = "";
	String afterDetailCar = "";
	String beforeProgress = "";
	String afterProgress = "";
	String customer = "";
	String cartype = "";
	String detailCar = "";
	String proto = "";
	String p1 = "";
	String p2 = "";
	String sop = "";
	int progressCount = 0;
	int completedCount = 0;
	int withdrawnCount = 0;
	int moldCount = 0;
	int pressCount = 0;
	int goodsCount = 0;
	int issueCount = 0;
	int total = 0;

	for (int count = 0; count < productPjtData.size(); count++) {
	    @SuppressWarnings("unused")
	    int length = 0;
	    beforeDetailCar = productPjtData.get(count).getDetailCar();
	    beforePjtNo = productPjtData.get(count).getPjtNO();
	    if (count == 0) {
		if (count == 0 && productPjtData.get(count).getState().equals("PROGRESS")) {
		    beforeProgress = productPjtData.get(count).getState();
		    progressCount++;
		    issueCount = productPjtData.get(count).getIssueCount();

		} else if (count == 0 && productPjtData.get(count).getState().equals("COMPLETED")) {
		    beforeProgress = productPjtData.get(count).getState();
		    completedCount++;
		    issueCount = productPjtData.get(count).getIssueCount();
		} else if (count == 0 && productPjtData.get(count).getState().equals("WITHDRAWN")) {
		    beforeProgress = productPjtData.get(count).getState();
		    withdrawnCount++;
		    issueCount = productPjtData.get(count).getIssueCount();
		}

		customer = productPjtData.get(count).getCustomer();
		cartype = productPjtData.get(count).getCarType();
		detailCar = productPjtData.get(count).getDetailCar();
		proto = (productPjtData.get(count).getProto() == null) ? "" : productPjtData.get(count).getProto();
		p1 = (productPjtData.get(count).getP1() == null) ? "" : productPjtData.get(count).getP1();
		p2 = (productPjtData.get(count).getP2() == null) ? "" : productPjtData.get(count).getP2();
		sop = (productPjtData.get(count).getSop() == null) ? "" : productPjtData.get(count).getSop();

		if (productPjtData.get(count).getItemType().equals("Mold")) {
		    moldCount += productPjtData.get(count).getItemCount();
		} else if (productPjtData.get(count).getItemType().equals("Press")) {
		    pressCount += productPjtData.get(count).getItemCount();
		} else {
		    goodsCount += productPjtData.get(count).getItemCount();
		}

	    } else if (beforeDetailCar.equals(afterDetailCar)) {
		if (productPjtData.get(count).getState().equals("PROGRESS")) {
		    beforeProgress = productPjtData.get(count).getState();

		    if (afterProgress.equals(beforeProgress) && !beforePjtNo.equals(afterPjtNo)) {
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("COMPLETED")) {
			completedCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("WITHDRAWN")) {
			withdrawnCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("PROGRESS")) {
			progressCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    }
		} else if (productPjtData.get(count).getState().equals("COMPLETED")) {
		    beforeProgress = productPjtData.get(count).getState();

		    if (afterProgress.equals(beforeProgress) && !beforePjtNo.equals(afterPjtNo)) {
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("PROGRESS")) {
			progressCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("WITHDRAWN")) {
			withdrawnCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("COMPLETED")) {
			completedCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    }
		} else if (productPjtData.get(count).getState().equals("WITHDRAWN")) {
		    beforeProgress = productPjtData.get(count).getState();

		    if (afterProgress.equals(beforeProgress) && !beforePjtNo.equals(afterPjtNo)) {
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("COMPLETED")) {
			completedCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("PROGRESS")) {
			progressCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!beforePjtNo.equals(afterPjtNo) && !afterProgress.equals(beforeProgress)
			    && productPjtData.get(count).getState().equals("PROGRESS")) {
			withdrawnCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    }
		}

		customer = productPjtData.get(count).getCustomer();
		cartype = productPjtData.get(count).getCarType();
		detailCar = productPjtData.get(count).getDetailCar();
		proto = (productPjtData.get(count).getProto() == null) ? "" : productPjtData.get(count).getProto();
		p1 = (productPjtData.get(count).getP1() == null) ? "" : productPjtData.get(count).getP1();
		p2 = (productPjtData.get(count).getP2() == null) ? "" : productPjtData.get(count).getP2();
		sop = (productPjtData.get(count).getSop() == null) ? "" : productPjtData.get(count).getSop();

		if (productPjtData.get(count).getItemType().equals("Mold")) {
		    moldCount += productPjtData.get(count).getItemCount();
		} else if (productPjtData.get(count).getItemType().equals("Press")) {
		    pressCount += productPjtData.get(count).getItemCount();
		} else {
		    goodsCount += productPjtData.get(count).getItemCount();
		}

		if (count == (productPjtData.size() - 1)) {
		    ProductProjectDTO dto = new ProductProjectDTO();
		    total = completedCount + progressCount + withdrawnCount;
		    dto.setCustomer(customer);
		    dto.setCarType(cartype);
		    dto.setDetailCar(detailCar);
		    dto.setProto(proto);
		    dto.setP1(p1);
		    dto.setP2(p2);
		    dto.setSop(sop);
		    dto.setTotal(total);
		    dto.setCompleted(completedCount);
		    dto.setProgress(progressCount);
		    dto.setWithdrawn(withdrawnCount);
		    dto.setMoldCount(moldCount);
		    dto.setPressCount(pressCount);
		    dto.setGoodsCount(goodsCount);
		    dto.setIssueCount(issueCount);

		    productPjtData1.add(dto);
		}

	    } else {

		length++;

		ProductProjectDTO dto = new ProductProjectDTO();
		total = completedCount + progressCount + withdrawnCount;
		dto.setCustomer(customer);
		dto.setCarType(cartype);
		dto.setDetailCar(detailCar);
		dto.setProto(proto);
		dto.setP1(p1);
		dto.setP2(p2);
		dto.setSop(sop);
		dto.setTotal(total);
		dto.setCompleted(completedCount);
		dto.setProgress(progressCount);
		dto.setWithdrawn(withdrawnCount);
		dto.setMoldCount(moldCount);
		dto.setPressCount(pressCount);
		dto.setGoodsCount(goodsCount);
		dto.setIssueCount(issueCount);

		productPjtData1.add(dto);

		progressCount = 0;
		completedCount = 0;
		withdrawnCount = 0;
		moldCount = 0;
		pressCount = 0;
		goodsCount = 0;
		issueCount = 0;
		total = 0;

		if (productPjtData.get(count).getState().equals("PROGRESS")) {
		    beforeProgress = productPjtData.get(count).getState();
		    progressCount++;
		    issueCount += productPjtData.get(count).getIssueCount();

		    if (afterProgress.equals(beforeProgress)) {
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("COMPLETED")) {
			completedCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("WITHDRAWN")) {
			withdrawnCount++;
			issueCount += productPjtData.get(count).getIssueCount();
		    }
		} else if (productPjtData.get(count).getState().equals("COMPLETED")) {
		    beforeProgress = productPjtData.get(count).getState();
		    completedCount++;
		    issueCount += productPjtData.get(count).getIssueCount();

		    if (afterProgress.equals(beforeProgress)) {
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("PROGRESS")) {
			progressCount++;
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("WITHDRAWN")) {
			withdrawnCount++;
		    }
		} else if (productPjtData.get(count).getState().equals("WITHDRAWN")) {
		    beforeProgress = productPjtData.get(count).getState();
		    withdrawnCount++;
		    issueCount += productPjtData.get(count).getIssueCount();

		    if (afterProgress.equals(beforeProgress)) {
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("COMPLETED")) {
			completedCount++;
		    } else if (!afterProgress.equals(beforeProgress) && productPjtData.get(count).getState().equals("PROGRESS")) {
			progressCount++;
		    }
		}

		customer = productPjtData.get(count).getCustomer();
		cartype = productPjtData.get(count).getCarType();
		detailCar = productPjtData.get(count).getDetailCar();
		proto = (productPjtData.get(count).getProto() == null) ? "" : productPjtData.get(count).getProto();
		p1 = (productPjtData.get(count).getP1() == null) ? "" : productPjtData.get(count).getP1();
		p2 = (productPjtData.get(count).getP2() == null) ? "" : productPjtData.get(count).getP2();
		sop = (productPjtData.get(count).getSop() == null) ? "" : productPjtData.get(count).getSop();

		if (productPjtData.get(count).getItemType().equals("Mold")) {
		    moldCount += productPjtData.get(count).getItemCount();
		} else if (productPjtData.get(count).getItemType().equals("Press")) {
		    pressCount += productPjtData.get(count).getItemCount();
		} else {
		    goodsCount += productPjtData.get(count).getItemCount();
		}
	    }
	    afterDetailCar = beforeDetailCar;
	    afterPjtNo = beforePjtNo;
	    afterProgress = beforeProgress;

	}

	return productPjtData1;
    }

    public Map<String, Object> multiColumChart1(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	dashBoardDTO.setStartDate(start);
	dashBoardDTO.setEndDate(end);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> inOutData = dao.find("mold.makingTypeData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "사내");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "외주");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	int inCompleteCount = 0, inProcessCount = 0, outCompleteCount = 0, outProcessCount = 0;
	int totalInCount = 0, totalOutCount = 0;

	for (int length = 0; length < inOutData.size(); length++) {
	    if ("사내".equals(inOutData.get(length).getMaking()) && "COMPLETED".equals(inOutData.get(length).getState())) {
		inCompleteCount = inOutData.get(length).getNum();
	    } else if ("사내".equals(inOutData.get(length).getMaking()) && "PROGRESS".equals(inOutData.get(length).getState())) {
		inProcessCount = inOutData.get(length).getNum();
	    } else if ("외주".equals(inOutData.get(length).getMaking()) && "COMPLETED".equals(inOutData.get(length).getState())) {
		outCompleteCount = inOutData.get(length).getNum();
	    } else if ("외주".equals(inOutData.get(length).getMaking()) && "PROGRESS".equals(inOutData.get(length).getState())) {
		outProcessCount = inOutData.get(length).getNum();
	    }
	}
	totalInCount = inCompleteCount + inProcessCount;
	totalOutCount = outCompleteCount + outProcessCount;

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	datasetAttr1.put("seriesName", "전체");
	datasetAttr1.put("color", "5ba6b9");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalInCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('T','I');");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalOutCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('T','O');");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "완료");
	datasetAttr1.put("color", "89c211");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", inCompleteCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('C','I');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", outCompleteCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('C','O');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "진행");
	datasetAttr1.put("color", "6482c0");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", inProcessCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('P','I');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", outProcessCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('P','O');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> multiColumChart2(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	dashBoardDTO.setStartDate(start);
	dashBoardDTO.setEndDate(end);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> startMassData = dao.find("mold.startMassData", dashBoardDTO);

	// int startTotal = 0;
	// int massTotal = 0;
	// int mold = 0;
	// int press = 0;

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "시작");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "양산");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	int startCompleteCount = 0, massCompleteCount = 0, startProcessCount = 0, massProcessCount = 0;
	int totalStartCount = 0, totalMassCount = 0;

	for (int length = 0; length < startMassData.size(); length++) {
	    if (("시작".equals(startMassData.get(length).getMoldCategory()) || "시작Mo".equals(startMassData.get(length).getMoldCategory()) || "시작Fa"
		    .equals(startMassData.get(length).getMoldCategory())) && "COMPLETED".equals(startMassData.get(length).getState())) {
		startCompleteCount += startMassData.get(length).getNum();
	    } else if (("시작".equals(startMassData.get(length).getMoldCategory())
		    || "시작Mo".equals(startMassData.get(length).getMoldCategory()) || "시작Fa".equals(startMassData.get(length)
		    .getMoldCategory())) && "PROGRESS".equals(startMassData.get(length).getState())) {
		startProcessCount += startMassData.get(length).getNum();
	    } else if (("양산".equals(startMassData.get(length).getMoldCategory())
		    || "양산Mo".equals(startMassData.get(length).getMoldCategory()) || "양산Fa".equals(startMassData.get(length)
		    .getMoldCategory())) && "COMPLETED".equals(startMassData.get(length).getState())) {
		massCompleteCount += startMassData.get(length).getNum();
	    } else if (("양산".equals(startMassData.get(length).getMoldCategory())
		    || "양산Mo".equals(startMassData.get(length).getMoldCategory()) || "양산Fa".equals(startMassData.get(length)
		    .getMoldCategory())) && "PROGRESS".equals(startMassData.get(length).getState())) {
		massProcessCount += startMassData.get(length).getNum();
	    }
	}
	totalStartCount = startCompleteCount + startProcessCount;
	totalMassCount = massCompleteCount + massProcessCount;

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	datasetAttr1.put("seriesName", "전체");
	datasetAttr1.put("color", "5ba6b9");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalStartCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('T','S');");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", totalMassCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('T','M');");
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "완료");
	datasetAttr1.put("color", "89c211");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", startCompleteCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('C','S');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", massCompleteCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('C','M');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "진행");
	datasetAttr1.put("color", "6482c0");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", startProcessCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('P','S');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", massProcessCount);
	setAttr1.put("link", "JavaScript:linkPopUp3('P','M');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> stackColumChart1(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> yearData = dao.find("mold.completedProgressData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "완료");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "진행");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	int completeMoldCount = 0, processMoldCount = 0, completePressCount = 0, processPressCount = 0;

	for (int length = 0; length < yearData.size(); length++) {
	    if ("COMPLETED".equals(yearData.get(length).getState()) && "Mold".equals(yearData.get(length).getMoldType())) {
		completeMoldCount = yearData.get(length).getNum();
	    } else if ("PROGRESS".equals(yearData.get(length).getState()) && "Mold".equals(yearData.get(length).getMoldType())) {
		processMoldCount = yearData.get(length).getNum();
	    } else if ("COMPLETED".equals(yearData.get(length).getState()) && "Press".equals(yearData.get(length).getMoldType())) {
		completePressCount = yearData.get(length).getNum();
	    } else if ("PROGRESS".equals(yearData.get(length).getState()) && "Press".equals(yearData.get(length).getMoldType())) {
		processPressCount = yearData.get(length).getNum();
	    }
	}

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completePressCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('C','P');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processPressCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('P','P');");
	dataAttr.add(dataAttr1);

	datasetAttr1.put("seriesname", "Press");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FF0000");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeMoldCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('C','M');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processMoldCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('P','M');");
	dataAttr.add(dataAttr1);

	datasetAttr1.put("seriesname", "Mold");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;

    }

    public Map<String, Object> multiColumChart3(DashBoardDTO dashBoardDTO) throws Exception {

	int total1 = 0;
	int total2 = 0;
	int total3 = 0;
	int total4 = 0;

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	dashBoardDTO.setStartDate(start);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	@SuppressWarnings("unchecked")
	List<DashBoardDTO> annualStartState = dao.find("mold.annualStartState", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> categoryAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoryAttr1;

	for (int length = 3; length >= 0; length--) {
	    categoryAttr1 = new HashMap<String, Object>();
	    categoryAttr1.put("label", year - length);
	    categoryAttr.add(categoryAttr1);
	}
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	datasetAttr1.put("seriesName", "Mold");
	datasetAttr1.put("color", "63a2c8");

	int moldyear = 0;
	int moldyear1 = 0;
	int moldyear2 = 0;
	int moldyear3 = 0;
	int pressyear = 0;
	int pressyear1 = 0;
	int pressyear2 = 0;
	int pressyear3 = 0;

	for (int length = 0; length < annualStartState.size(); length++) {
	    if ("Mold".equals(annualStartState.get(length).getMoldType()) && "1".equals(annualStartState.get(length).getType())) {
		moldyear = annualStartState.get(length).getNum();
	    } else if ("Mold".equals(annualStartState.get(length).getMoldType()) && "2".equals(annualStartState.get(length).getType())) {
		moldyear1 = annualStartState.get(length).getNum();
	    } else if ("Mold".equals(annualStartState.get(length).getMoldType()) && "3".equals(annualStartState.get(length).getType())) {
		moldyear2 = annualStartState.get(length).getNum();
	    } else if ("Mold".equals(annualStartState.get(length).getMoldType()) && "4".equals(annualStartState.get(length).getType())) {
		moldyear3 = annualStartState.get(length).getNum();
	    }
	}

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldyear3);
	setAttr1.put("color", "63a2c8");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldyear2);
	setAttr1.put("color", "63a2c8");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldyear1);
	setAttr1.put("color", "63a2c8");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldyear);
	setAttr1.put("color", "63a2c8");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "Press");
	datasetAttr1.put("color", "abcce0");
	for (int length = 0; length < annualStartState.size(); length++) {
	    if ("Press".equals(annualStartState.get(length).getMoldType()) && "1".equals(annualStartState.get(length).getType())) {
		pressyear = annualStartState.get(length).getNum();
	    } else if ("Press".equals(annualStartState.get(length).getMoldType()) && "2".equals(annualStartState.get(length).getType())) {
		pressyear1 = annualStartState.get(length).getNum();
	    } else if ("Press".equals(annualStartState.get(length).getMoldType()) && "3".equals(annualStartState.get(length).getType())) {
		pressyear2 = annualStartState.get(length).getNum();
	    } else if ("Press".equals(annualStartState.get(length).getMoldType()) && "4".equals(annualStartState.get(length).getType())) {
		pressyear3 = annualStartState.get(length).getNum();
	    }
	}

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressyear3);
	setAttr1.put("color", "abcce0");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressyear2);
	setAttr1.put("color", "abcce0");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressyear1);
	setAttr1.put("color", "abcce0");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", pressyear);
	setAttr1.put("color", "abcce0");
	setAttr.add(setAttr1);
	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "계");
	datasetAttr1.put("color", "5ba6b9");
	for (int length = 0; length < annualStartState.size(); length++) {
	    if ("1".equals(annualStartState.get(length).getType())) {
		total1 += annualStartState.get(length).getNum();
	    } else if ("2".equals(annualStartState.get(length).getType())) {
		total2 += annualStartState.get(length).getNum();
	    } else if ("3".equals(annualStartState.get(length).getType())) {
		total3 += annualStartState.get(length).getNum();
	    } else {
		total4 += annualStartState.get(length).getNum();
	    }
	}

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", total4);
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", total3);
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", total2);
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", total1);
	setAttr1.put("color", "5ba6b9");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<ProductProjectDTO> moldMakeSituation(DashBoardDTO boardDTO) {

	int year = 0;
	int month = 0;

	if (boardDTO.getYear() == 0) {
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    year = cal.get(cal.YEAR);
	    month = cal.get(cal.MONTH) + 1;
	} else {
	    year = Integer.valueOf(boardDTO.getYearSetting());
	    month = boardDTO.getMonthSetting();
	}

	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "-" + month + "-01";
	boardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	boardDTO.setEndDate(endDate);
	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	String currentDate1 = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth) + "-" + String.valueOf(currentDay);
	boardDTO.setCurrentDate(currentDate1);
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    boardDTO.setType("case1");
	} else {
	    boardDTO.setType("case2");
	}

	// 사내/외주제작 MoldCategory별 PJT수
	List<DashBoardDTO> pjtCountData = dao.find("mold.moldcategoryPjtCount", boardDTO);
	// 사내/외주제작 MoldCategory별 PJT 지연수
	List<DashBoardDTO> pjtDelayCountDate = dao.find("mold.moldcategoryDelayPjtCount", boardDTO);
	// 금형제작 단계별 TASK수
	List<DashBoardDTO> stepMoldTaskData = dao.find("mold.stepMoldTaskCount", boardDTO);
	// 금형제작 단계별 TASK 지연수
	List<DashBoardDTO> stepMoldTaskDelayData = dao.find("mold.stepMoldTaskDelayCount", boardDTO);
	// 이슈 수
	List<DashBoardDTO> issueCount = dao.find("mold.moldIssueCount", boardDTO);
	// TASK -디버깅4차이상 통합수
	List<DashBoardDTO> fourDebugingCount = dao.find("mold.moldTaskDebugingFourAbove", boardDTO);

	List<DashBoardDTO> fourDebugingDelayCount = dao.find("mold.moldTaskDebugingFourAboveDelayCount", boardDTO);

	List<ProductProjectDTO> temp = Count(pjtCountData); // 과제수

	List<ProductProjectDTO> temp_ = Count(pjtDelayCountDate); // 과제 지연수

	List<ProductProjectDTO> temp1 = stepMoldTaskCount(stepMoldTaskData); // Task수

	List<ProductProjectDTO> temp2 = stepMoldTaskCount(stepMoldTaskDelayData); // Delay Task수

	List<ProductProjectDTO> temp3 = Count(issueCount); // 이슈수

	List<ProductProjectDTO> temp4 = Count(fourDebugingCount); // Debugig4이상 Task수

	List<ProductProjectDTO> temp5 = Count(fourDebugingDelayCount);

	List<ProductProjectDTO> data = new ArrayList<ProductProjectDTO>();

	ProductProjectDTO dto;

	for (int length = 0; length < 4; length++) {
	    dto = new ProductProjectDTO();
	    if (length == 0) {
		dto.setState("시작금형");
		dto.setPjtCount(temp.get(0).getStartMold());
		dto.setPjtDelayCount(temp_.get(0).getStartMold());
		dto.setIssueCount(temp3.get(0).getStartMold());
		dto.setDebuging4(temp4.get(0).getStartMold());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(0).getStartMold());
		dto.setDebugingDelay4(temp5.get(0).getStartMold());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(0).getStartMold());
	    } else if (length == 1) {
		dto.setState("시작Mo/Fa");
		dto.setPjtCount(temp.get(0).getStartMoFa());
		dto.setPjtDelayCount(temp_.get(0).getStartMoFa());
		dto.setIssueCount(temp3.get(0).getStartMoFa());
		dto.setDebuging4(temp4.get(0).getStartMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(0).getStartMoFa());
		dto.setDebugingDelay4(temp5.get(0).getStartMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(0).getStartMoFa());
	    } else if (length == 2) {
		dto.setState("양산금형");
		dto.setPjtCount(temp.get(0).getProduction());
		dto.setPjtDelayCount(temp_.get(0).getProduction());
		dto.setIssueCount(temp3.get(0).getProduction());
		dto.setDebuging4(temp4.get(0).getProduction());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(0).getProduction());
		dto.setDebugingDelay4(temp5.get(0).getProduction());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(0).getProduction());
	    } else {
		dto.setState("양산Mo/Fa");
		dto.setPjtCount(temp.get(0).getProductionMoFa());
		dto.setPjtDelayCount(temp_.get(0).getProductionMoFa());
		dto.setIssueCount(temp3.get(0).getProductionMoFa());
		dto.setDebuging4(temp4.get(0).getProductionMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(0).getProductionMoFa());
		dto.setDebugingDelay4(temp5.get(0).getProductionMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(0).getProductionMoFa());
	    }
	    dto.setType("사내");
	    dto.setBegin(temp1.get(length).getBegin());
	    dto.setPlan(temp1.get(length).getPlan());
	    dto.setProcess(temp1.get(length).getProcess());
	    dto.setFirstTry(temp1.get(length).getFirstTry());
	    dto.setDebuging1(temp1.get(length).getDebuging1());
	    dto.setDebuging2(temp1.get(length).getDebuging2());
	    dto.setDebuging3(temp1.get(length).getDebuging3());
	    dto.setMoldtransfer(temp1.get(length).getMoldtransfer());

	    dto.setBeginDelay(temp2.get(length).getBegin());
	    dto.setPlanDelay(temp2.get(length).getPlan());
	    dto.setProcessDelay(temp2.get(length).getProcess());
	    dto.setFirstTryDelay(temp2.get(length).getFirstTry());
	    dto.setDebugingDelay1(temp2.get(length).getDebuging1());
	    dto.setDebugingDelay2(temp2.get(length).getDebuging2());
	    dto.setDebugingDelay3(temp2.get(length).getDebuging3());
	    dto.setMoldtransferDelay(temp2.get(length).getMoldtransfer());

	    data.add(dto);
	}

	dto = new ProductProjectDTO();
	dto.setState("소계");
	dto.setType("사내");
	dto.setPjtCount(temp.get(0).getStartMold() + temp.get(0).getStartMoFa() + temp.get(0).getProduction()
	        + temp.get(0).getProductionMoFa());
	dto.setPjtDelayCount(temp_.get(0).getStartMold() + temp_.get(0).getStartMoFa() + temp_.get(0).getProduction()
	        + temp_.get(0).getProductionMoFa());
	dto.setBegin(temp1.get(0).getBegin() + temp1.get(1).getBegin() + temp1.get(2).getBegin() + temp1.get(3).getBegin());
	dto.setPlan(temp1.get(0).getPlan() + temp1.get(1).getPlan() + temp1.get(2).getPlan() + temp1.get(3).getPlan());
	dto.setProcess(temp1.get(0).getProcess() + temp1.get(1).getProcess() + temp1.get(2).getProcess() + temp1.get(3).getProcess());
	dto.setFirstTry(temp1.get(0).getFirstTry() + temp1.get(1).getFirstTry() + temp1.get(2).getFirstTry() + temp1.get(3).getFirstTry());
	dto.setDebuging1(temp1.get(0).getDebuging1() + temp1.get(1).getDebuging1() + temp1.get(2).getDebuging1()
	        + temp1.get(3).getDebuging1());
	dto.setDebuging2(temp1.get(0).getDebuging2() + temp1.get(1).getDebuging2() + temp1.get(2).getDebuging2()
	        + temp1.get(3).getDebuging2());
	dto.setDebuging3(temp1.get(0).getDebuging3() + temp1.get(1).getDebuging3() + temp1.get(2).getDebuging3()
	        + temp1.get(3).getDebuging3());
	dto.setMoldtransfer(temp1.get(0).getMoldtransfer() + temp1.get(1).getMoldtransfer() + temp1.get(2).getMoldtransfer()
	        + temp1.get(3).getMoldtransfer());
	dto.setTotal(temp1.get(0).getTotal() + temp1.get(1).getTotal() + temp1.get(2).getTotal() + temp1.get(3).getTotal()
	        + temp4.get(0).getStartMold() + temp4.get(0).getStartMoFa() + temp4.get(0).getProduction()
	        + temp4.get(0).getProductionMoFa());
	dto.setBeginDelay(temp2.get(0).getBegin() + temp2.get(1).getBegin() + temp2.get(2).getBegin() + temp2.get(3).getBegin());
	dto.setPlanDelay(temp2.get(0).getPlan() + temp2.get(1).getPlan() + temp2.get(2).getPlan() + temp2.get(3).getPlan());
	dto.setProcessDelay(temp2.get(0).getProcess() + temp2.get(1).getProcess() + temp2.get(2).getProcess() + temp2.get(3).getProcess());
	dto.setFirstTryDelay(temp2.get(0).getFirstTry() + temp2.get(1).getFirstTry() + temp2.get(2).getFirstTry()
	        + temp2.get(3).getFirstTry());
	dto.setDebugingDelay1(temp2.get(0).getDebuging1() + temp2.get(1).getDebuging1() + temp2.get(2).getDebuging1()
	        + temp2.get(3).getDebuging1());
	dto.setDebugingDelay2(temp2.get(0).getDebuging2() + temp2.get(1).getDebuging2() + temp2.get(2).getDebuging2()
	        + temp2.get(3).getDebuging2());
	dto.setDebugingDelay3(temp2.get(0).getDebuging3() + temp2.get(1).getDebuging3() + temp2.get(2).getDebuging3()
	        + temp2.get(3).getDebuging3());
	dto.setMoldtransferDelay(temp2.get(0).getMoldtransfer() + temp2.get(1).getMoldtransfer() + temp2.get(2).getMoldtransfer()
	        + temp2.get(3).getMoldtransfer());
	dto.setDelaytotal(temp2.get(0).getTotal() + temp2.get(1).getTotal() + temp2.get(2).getTotal() + temp2.get(3).getTotal()
	        + temp5.get(0).getStartMold() + temp5.get(0).getStartMoFa() + temp5.get(0).getProduction()
	        + temp5.get(0).getProductionMoFa());
	dto.setIssueCount(temp3.get(0).getStartMold() + temp3.get(0).getStartMoFa() + temp3.get(0).getProduction()
	        + temp3.get(0).getProductionMoFa());
	dto.setDebuging4(temp4.get(0).getStartMold() + temp4.get(0).getStartMoFa() + temp4.get(0).getProduction()
	        + temp4.get(0).getProductionMoFa());
	dto.setDebugingDelay4(temp5.get(0).getStartMold() + temp5.get(0).getStartMoFa() + temp5.get(0).getProduction()
	        + temp5.get(0).getProductionMoFa());
	data.add(dto);

	for (int length = 4; length < 8; length++) {
	    dto = new ProductProjectDTO();
	    if (length == 4) {
		dto.setState("시작금형");
		dto.setPjtCount(temp.get(1).getStartMold());
		dto.setPjtDelayCount(temp_.get(1).getStartMold());
		dto.setIssueCount(temp3.get(1).getStartMold());
		dto.setDebuging4(temp4.get(1).getStartMold());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(1).getStartMold());
		dto.setDebugingDelay4(temp5.get(1).getStartMold());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(1).getStartMold());
	    } else if (length == 5) {
		dto.setState("시작Mo/Fa");
		dto.setPjtCount(temp.get(1).getStartMoFa());
		dto.setPjtDelayCount(temp_.get(1).getStartMoFa());
		dto.setIssueCount(temp3.get(1).getStartMoFa());
		dto.setDebuging4(temp4.get(1).getStartMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(1).getStartMoFa());
		dto.setDebugingDelay4(temp5.get(1).getStartMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(1).getStartMoFa());
	    } else if (length == 6) {
		dto.setState("양산금형");
		dto.setPjtCount(temp.get(1).getProduction());
		dto.setPjtDelayCount(temp_.get(1).getProduction());
		dto.setIssueCount(temp3.get(1).getProduction());
		dto.setDebuging4(temp4.get(1).getProduction());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(1).getProduction());
		dto.setDebugingDelay4(temp5.get(1).getProduction());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(1).getProduction());
	    } else {
		dto.setState("양산Mo/Fa");
		dto.setPjtCount(temp.get(1).getProductionMoFa());
		dto.setPjtDelayCount(temp_.get(1).getProductionMoFa());
		dto.setIssueCount(temp3.get(1).getProductionMoFa());
		dto.setDebuging4(temp4.get(1).getProductionMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp4.get(1).getProductionMoFa());
		dto.setDebugingDelay4(temp5.get(1).getProductionMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp5.get(1).getProductionMoFa());
	    }
	    dto.setType("외주");
	    dto.setBegin(temp1.get(length).getBegin());
	    dto.setPlan(temp1.get(length).getPlan());
	    dto.setProcess(temp1.get(length).getProcess());
	    dto.setFirstTry(temp1.get(length).getFirstTry());
	    dto.setDebuging1(temp1.get(length).getDebuging1());
	    dto.setDebuging2(temp1.get(length).getDebuging2());
	    dto.setDebuging3(temp1.get(length).getDebuging3());
	    dto.setMoldtransfer(temp1.get(length).getMoldtransfer());

	    dto.setBeginDelay(temp2.get(length).getBegin());
	    dto.setPlanDelay(temp2.get(length).getPlan());
	    dto.setProcessDelay(temp2.get(length).getProcess());
	    dto.setFirstTryDelay(temp2.get(length).getFirstTry());
	    dto.setDebugingDelay1(temp2.get(length).getDebuging1());
	    dto.setDebugingDelay2(temp2.get(length).getDebuging2());
	    dto.setDebugingDelay3(temp2.get(length).getDebuging3());
	    dto.setMoldtransferDelay(temp2.get(length).getMoldtransfer());

	    data.add(dto);
	}

	dto = new ProductProjectDTO();
	dto.setState("소계");
	dto.setType("외주");
	dto.setPjtCount(temp.get(1).getStartMold() + temp.get(1).getStartMoFa() + temp.get(1).getProduction()
	        + temp.get(1).getProductionMoFa());
	dto.setPjtDelayCount(temp_.get(1).getStartMold() + temp_.get(1).getStartMoFa() + temp_.get(1).getProduction()
	        + temp_.get(1).getProductionMoFa());
	dto.setBegin(temp1.get(4).getBegin() + temp1.get(5).getBegin() + temp1.get(6).getBegin() + temp1.get(7).getBegin());
	dto.setPlan(temp1.get(4).getPlan() + temp1.get(5).getPlan() + temp1.get(6).getPlan() + temp1.get(7).getPlan());
	dto.setProcess(temp1.get(4).getProcess() + temp1.get(5).getProcess() + temp1.get(6).getProcess() + temp1.get(7).getProcess());
	dto.setFirstTry(temp1.get(4).getFirstTry() + temp1.get(5).getFirstTry() + temp1.get(6).getFirstTry() + temp1.get(7).getFirstTry());
	dto.setDebuging1(temp1.get(4).getDebuging1() + temp1.get(5).getDebuging1() + temp1.get(6).getDebuging1()
	        + temp1.get(7).getDebuging1());
	dto.setDebuging2(temp1.get(4).getDebuging2() + temp1.get(5).getDebuging2() + temp1.get(6).getDebuging2()
	        + temp1.get(7).getDebuging2());
	dto.setDebuging3(temp1.get(4).getDebuging3() + temp1.get(5).getDebuging3() + temp1.get(6).getDebuging3()
	        + temp1.get(7).getDebuging3());
	dto.setMoldtransfer(temp1.get(4).getMoldtransfer() + temp1.get(5).getMoldtransfer() + temp1.get(6).getMoldtransfer()
	        + temp1.get(7).getMoldtransfer());
	dto.setTotal(temp1.get(4).getTotal() + temp1.get(5).getTotal() + temp1.get(6).getTotal() + temp1.get(7).getTotal()
	        + temp4.get(1).getStartMold() + temp4.get(1).getStartMoFa() + temp4.get(1).getProduction()
	        + temp4.get(1).getProductionMoFa());
	dto.setBeginDelay(temp2.get(4).getBegin() + temp2.get(5).getBegin() + temp2.get(6).getBegin() + temp2.get(7).getBegin());
	dto.setPlanDelay(temp2.get(4).getPlan() + temp2.get(5).getPlan() + temp2.get(6).getPlan() + temp2.get(7).getPlan());
	dto.setProcessDelay(temp2.get(4).getProcess() + temp2.get(5).getProcess() + temp2.get(6).getProcess() + temp2.get(7).getProcess());
	dto.setFirstTryDelay(temp2.get(4).getFirstTry() + temp2.get(5).getFirstTry() + temp2.get(6).getFirstTry()
	        + temp2.get(7).getFirstTry());
	dto.setDebugingDelay1(temp2.get(4).getDebuging1() + temp2.get(5).getDebuging1() + temp2.get(6).getDebuging1()
	        + temp2.get(7).getDebuging1());
	dto.setDebugingDelay2(temp2.get(4).getDebuging2() + temp2.get(5).getDebuging2() + temp2.get(6).getDebuging2()
	        + temp1.get(7).getDebuging2());
	dto.setDebugingDelay3(temp2.get(4).getDebuging3() + temp2.get(5).getDebuging3() + temp2.get(6).getDebuging3()
	        + temp2.get(7).getDebuging3());
	dto.setMoldtransferDelay(temp2.get(4).getMoldtransfer() + temp2.get(5).getMoldtransfer() + temp2.get(6).getMoldtransfer()
	        + temp2.get(7).getMoldtransfer());
	dto.setDelaytotal(temp2.get(4).getTotal() + temp2.get(5).getTotal() + temp2.get(6).getTotal() + temp2.get(7).getTotal()
	        + temp5.get(1).getStartMold() + temp5.get(1).getStartMoFa() + temp5.get(1).getProduction()
	        + temp5.get(1).getProductionMoFa());
	dto.setIssueCount(temp3.get(1).getStartMold() + temp3.get(1).getStartMoFa() + temp3.get(1).getProduction()
	        + temp3.get(1).getProductionMoFa());
	dto.setDebuging4(temp4.get(1).getStartMold() + temp4.get(1).getStartMoFa() + temp4.get(1).getProduction()
	        + temp4.get(1).getProductionMoFa());
	dto.setDebugingDelay4(temp5.get(1).getStartMold() + temp5.get(1).getStartMoFa() + temp5.get(1).getProduction()
	        + temp5.get(1).getProductionMoFa());
	data.add(dto);

	for (int length = 0; length < 4; length++) {
	    dto = new ProductProjectDTO();
	    if (length == 0) {
		dto.setState("시작금형");
		dto.setPjtCount(temp.get(0).getStartMold() + temp.get(1).getStartMold());
		dto.setPjtDelayCount(temp_.get(0).getStartMold() + temp_.get(1).getStartMold());
		dto.setIssueCount(temp3.get(0).getStartMold() + temp3.get(1).getStartMold());
		dto.setDebuging4(temp4.get(0).getStartMold() + temp4.get(1).getStartMold());
		dto.setTotal(temp1.get(length).getTotal() + temp1.get(length + 4).getTotal() + temp4.get(0).getStartMold()
		        + temp4.get(1).getStartMold());
		dto.setDebugingDelay4(temp5.get(0).getStartMold() + temp5.get(1).getStartMold());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp2.get(length + 4).getTotal() + temp5.get(0).getStartMold()
		        + temp5.get(1).getStartMold());
	    } else if (length == 1) {
		dto.setState("시작Mo/Fa");
		dto.setPjtCount(temp.get(0).getStartMoFa() + temp.get(1).getStartMoFa());
		dto.setPjtDelayCount(temp_.get(0).getStartMoFa() + temp_.get(1).getStartMoFa());
		dto.setIssueCount(temp3.get(0).getStartMoFa() + temp3.get(1).getStartMoFa());
		dto.setDebuging4(temp4.get(0).getStartMoFa() + temp4.get(1).getStartMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp1.get(length + 4).getTotal() + temp4.get(0).getStartMoFa()
		        + temp4.get(1).getStartMoFa());
		dto.setDebugingDelay4(temp5.get(0).getStartMoFa() + temp5.get(1).getStartMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp2.get(length + 4).getTotal() + temp5.get(0).getStartMoFa()
		        + temp5.get(1).getStartMoFa());
	    } else if (length == 2) {
		dto.setState("양산금형");
		dto.setPjtCount(temp.get(0).getProduction() + temp.get(1).getProduction());
		dto.setPjtDelayCount(temp_.get(0).getProduction() + temp_.get(1).getProduction());
		dto.setIssueCount(temp3.get(0).getProduction() + temp3.get(1).getProduction());
		dto.setDebuging4(temp4.get(0).getProduction() + temp4.get(1).getProduction());
		dto.setTotal(temp1.get(length).getTotal() + temp1.get(length + 4).getTotal() + temp4.get(0).getProduction()
		        + temp4.get(1).getProduction());
		dto.setDebugingDelay4(temp5.get(0).getProduction() + temp5.get(1).getProduction());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp2.get(length + 4).getTotal() + temp5.get(0).getProduction()
		        + temp5.get(1).getProduction());
	    } else {
		dto.setState("양산Mo/Fa");
		dto.setPjtCount(temp.get(0).getProductionMoFa() + temp.get(1).getProductionMoFa());
		dto.setPjtDelayCount(temp_.get(0).getProductionMoFa() + temp_.get(1).getProductionMoFa());
		dto.setIssueCount(temp3.get(0).getProductionMoFa() + temp3.get(1).getProductionMoFa());
		dto.setDebuging4(temp4.get(0).getProductionMoFa() + temp4.get(1).getProductionMoFa());
		dto.setTotal(temp1.get(length).getTotal() + temp1.get(length + 4).getTotal() + temp4.get(0).getProductionMoFa()
		        + temp4.get(1).getProductionMoFa());
		dto.setDebugingDelay4(temp5.get(0).getProductionMoFa() + temp5.get(1).getProductionMoFa());
		dto.setDelaytotal(temp2.get(length).getTotal() + temp2.get(length + 4).getTotal() + temp5.get(0).getProductionMoFa()
		        + temp5.get(1).getProductionMoFa());
	    }
	    dto.setType("전체");
	    dto.setBegin(temp1.get(length).getBegin() + temp1.get(length + 4).getBegin());
	    dto.setPlan(temp1.get(length).getPlan() + temp1.get(length + 4).getPlan());
	    dto.setProcess(temp1.get(length).getProcess() + temp1.get(length + 4).getProcess());
	    dto.setFirstTry(temp1.get(length).getFirstTry() + temp1.get(length + 4).getFirstTry());
	    dto.setDebuging1(temp1.get(length).getDebuging1() + temp1.get(length + 4).getDebuging1());
	    dto.setDebuging2(temp1.get(length).getDebuging2() + temp1.get(length + 4).getDebuging2());
	    dto.setDebuging3(temp1.get(length).getDebuging3() + temp1.get(length + 4).getDebuging3());
	    dto.setMoldtransfer(temp1.get(length).getMoldtransfer() + temp1.get(length + 4).getMoldtransfer());
	    // dto.setTotal(temp1.get(length).getTotal() + temp1.get(length + 4).getTotal());
	    dto.setBeginDelay(temp2.get(length).getBegin() + temp2.get(length + 4).getBegin());
	    dto.setPlanDelay(temp2.get(length).getPlan() + temp2.get(length + 4).getPlan());
	    dto.setProcessDelay(temp2.get(length).getProcess() + temp2.get(length + 4).getProcess());
	    dto.setFirstTryDelay(temp2.get(length).getFirstTry() + temp2.get(length + 4).getFirstTry());
	    dto.setDebugingDelay1(temp2.get(length).getDebuging1() + temp2.get(length + 4).getDebuging1());
	    dto.setDebugingDelay2(temp2.get(length).getDebuging2() + temp2.get(length + 4).getDebuging2());
	    dto.setDebugingDelay3(temp2.get(length).getDebuging3() + temp2.get(length + 4).getDebuging3());
	    dto.setMoldtransferDelay(temp2.get(length).getMoldtransfer() + temp2.get(length + 4).getMoldtransfer());

	    data.add(dto);
	}

	dto = new ProductProjectDTO();
	dto.setState("소계");
	dto.setType("전체");
	dto.setPjtCount(temp.get(0).getStartMold() + temp.get(0).getStartMoFa() + temp.get(0).getProduction()
	        + temp.get(0).getProductionMoFa() + temp.get(1).getStartMold() + temp.get(1).getStartMoFa() + temp.get(1).getProduction()
	        + temp.get(1).getProductionMoFa());
	dto.setPjtDelayCount(temp_.get(0).getStartMold() + temp_.get(0).getStartMoFa() + temp_.get(0).getProduction()
	        + temp_.get(0).getProductionMoFa() + temp_.get(1).getStartMold() + temp_.get(1).getStartMoFa()
	        + temp_.get(1).getProduction() + temp_.get(1).getProductionMoFa());
	dto.setBegin(temp1.get(0).getBegin() + temp1.get(1).getBegin() + temp1.get(2).getBegin() + temp1.get(3).getBegin()
	        + temp1.get(4).getBegin() + temp1.get(5).getBegin() + temp1.get(6).getBegin() + temp1.get(7).getBegin());
	dto.setPlan(temp1.get(0).getPlan() + temp1.get(1).getPlan() + temp1.get(2).getPlan() + temp1.get(3).getPlan()
	        + temp1.get(4).getPlan() + temp1.get(5).getPlan() + temp1.get(6).getPlan() + temp1.get(7).getPlan());
	dto.setProcess(temp1.get(0).getProcess() + temp1.get(1).getProcess() + temp1.get(2).getProcess() + temp1.get(3).getProcess()
	        + temp1.get(4).getProcess() + temp1.get(5).getProcess() + temp1.get(6).getProcess() + temp1.get(7).getProcess());
	dto.setFirstTry(temp1.get(0).getFirstTry() + temp1.get(1).getFirstTry() + temp1.get(2).getFirstTry() + temp1.get(3).getFirstTry()
	        + temp1.get(4).getFirstTry() + temp1.get(5).getFirstTry() + temp1.get(6).getFirstTry() + temp1.get(7).getFirstTry());
	dto.setDebuging1(temp1.get(0).getDebuging1() + temp1.get(1).getDebuging1() + temp1.get(2).getDebuging1()
	        + temp1.get(3).getDebuging1() + temp1.get(4).getDebuging1() + temp1.get(5).getDebuging1() + temp1.get(6).getDebuging1()
	        + temp1.get(7).getDebuging1());
	dto.setDebuging2(temp1.get(0).getDebuging2() + temp1.get(1).getDebuging2() + temp1.get(2).getDebuging2()
	        + temp1.get(3).getDebuging2() + temp1.get(4).getDebuging2() + temp1.get(5).getDebuging2() + temp1.get(6).getDebuging2()
	        + temp1.get(7).getDebuging2());
	dto.setDebuging3(temp1.get(0).getDebuging3() + temp1.get(1).getDebuging3() + temp1.get(2).getDebuging3()
	        + temp1.get(3).getDebuging3() + temp1.get(4).getDebuging3() + temp1.get(5).getDebuging3() + temp1.get(6).getDebuging3()
	        + temp1.get(7).getDebuging3());
	dto.setMoldtransfer(temp1.get(0).getMoldtransfer() + temp1.get(1).getMoldtransfer() + temp1.get(2).getMoldtransfer()
	        + temp1.get(3).getMoldtransfer() + temp1.get(4).getMoldtransfer() + temp1.get(5).getMoldtransfer()
	        + temp1.get(6).getMoldtransfer() + temp1.get(7).getMoldtransfer());
	dto.setTotal(temp1.get(0).getTotal() + temp1.get(1).getTotal() + temp1.get(2).getTotal() + temp1.get(3).getTotal()
	        + temp4.get(0).getStartMold() + temp4.get(0).getStartMoFa() + temp4.get(0).getProduction()
	        + temp4.get(0).getProductionMoFa() + temp1.get(4).getTotal() + temp1.get(5).getTotal() + temp1.get(6).getTotal()
	        + temp1.get(7).getTotal() + temp4.get(1).getStartMold() + temp4.get(1).getStartMoFa() + temp4.get(1).getProduction()
	        + temp4.get(1).getProductionMoFa());
	dto.setBeginDelay(temp2.get(0).getBegin() + temp2.get(1).getBegin() + temp2.get(2).getBegin() + temp2.get(3).getBegin()
	        + temp2.get(4).getBegin() + temp2.get(5).getBegin() + temp2.get(6).getBegin() + temp2.get(7).getBegin());
	dto.setPlanDelay(temp2.get(0).getPlan() + temp2.get(1).getPlan() + temp2.get(2).getPlan() + temp2.get(3).getPlan()
	        + temp2.get(4).getPlan() + temp2.get(5).getPlan() + temp2.get(6).getPlan() + temp2.get(7).getPlan());
	dto.setProcessDelay(temp2.get(0).getProcess() + temp2.get(1).getProcess() + temp2.get(2).getProcess() + temp2.get(3).getProcess()
	        + temp1.get(4).getProcess() + temp1.get(5).getProcess() + temp1.get(6).getProcess() + temp1.get(7).getProcess());
	dto.setFirstTryDelay(temp2.get(0).getFirstTry() + temp2.get(1).getFirstTry() + temp2.get(2).getFirstTry()
	        + temp2.get(3).getFirstTry() + temp2.get(4).getFirstTry() + temp2.get(5).getFirstTry() + temp2.get(6).getFirstTry()
	        + temp2.get(7).getFirstTry());
	dto.setDebugingDelay1(temp2.get(0).getDebuging1() + temp2.get(1).getDebuging1() + temp2.get(2).getDebuging1()
	        + temp2.get(3).getDebuging1() + temp2.get(4).getDebuging1() + temp2.get(5).getDebuging1() + temp2.get(6).getDebuging1()
	        + temp2.get(7).getDebuging1());
	dto.setDebugingDelay2(temp2.get(0).getDebuging2() + temp2.get(1).getDebuging2() + temp2.get(2).getDebuging2()
	        + temp2.get(3).getDebuging2() + temp2.get(4).getDebuging2() + temp2.get(5).getDebuging2() + temp2.get(6).getDebuging2()
	        + temp2.get(7).getDebuging2());
	dto.setDebugingDelay3(temp2.get(0).getDebuging3() + temp2.get(1).getDebuging3() + temp2.get(2).getDebuging3()
	        + temp2.get(3).getDebuging3() + temp2.get(4).getDebuging3() + temp2.get(5).getDebuging3() + temp2.get(6).getDebuging3()
	        + temp2.get(7).getDebuging3());
	dto.setMoldtransferDelay(temp2.get(0).getMoldtransfer() + temp2.get(1).getMoldtransfer() + temp2.get(2).getMoldtransfer()
	        + temp2.get(3).getMoldtransfer() + temp2.get(4).getMoldtransfer() + temp2.get(5).getMoldtransfer()
	        + temp2.get(6).getMoldtransfer() + temp2.get(7).getMoldtransfer());
	dto.setDelaytotal(temp2.get(0).getTotal() + temp2.get(1).getTotal() + temp2.get(2).getTotal() + temp2.get(3).getTotal()
	        + temp2.get(4).getTotal() + temp2.get(5).getTotal() + temp2.get(6).getTotal() + temp2.get(7).getTotal()
	        + temp5.get(0).getStartMold() + temp5.get(0).getStartMoFa() + temp5.get(0).getProduction()
	        + temp5.get(0).getProductionMoFa() + temp5.get(1).getStartMold() + temp5.get(1).getStartMoFa()
	        + temp5.get(1).getProduction() + temp5.get(1).getProductionMoFa());
	dto.setIssueCount(temp3.get(0).getStartMold() + temp3.get(0).getStartMoFa() + temp3.get(0).getProduction()
	        + temp3.get(0).getProductionMoFa() + temp3.get(1).getStartMold() + temp3.get(1).getStartMoFa()
	        + temp3.get(1).getProduction() + temp3.get(1).getProductionMoFa());
	dto.setDebuging4(temp4.get(0).getStartMold() + temp4.get(0).getStartMoFa() + temp4.get(0).getProduction()
	        + temp4.get(0).getProductionMoFa() + temp4.get(1).getStartMold() + temp4.get(1).getStartMoFa()
	        + temp4.get(1).getProduction() + temp4.get(1).getProductionMoFa());
	dto.setDebugingDelay4(temp5.get(0).getStartMold() + temp5.get(0).getStartMoFa() + temp5.get(0).getProduction()
	        + temp5.get(0).getProductionMoFa() + temp5.get(1).getStartMold() + temp5.get(1).getStartMoFa()
	        + temp5.get(1).getProduction() + temp5.get(1).getProductionMoFa());
	data.add(dto);

	return data;
    }

    public List<ProductProjectDTO> Count(List<DashBoardDTO> pjtCountData) {

	List<ProductProjectDTO> pjtcount = new ArrayList<ProductProjectDTO>();
	ProductProjectDTO data = new ProductProjectDTO();
	int startMold = 0;
	int startMoFa = 0;
	int production = 0;
	int productionMoFa = 0;

	int startMold1 = 0;
	int startMoFa1 = 0;
	int production1 = 0;
	int productionMoFa1 = 0;

	for (int length = 0; length < pjtCountData.size(); length++) {
	    if ("사내".equals(pjtCountData.get(length).getMaking())) {
		if ("시작".equals(pjtCountData.get(length).getMoldCategory())) {
		    startMold = pjtCountData.get(length).getNum();
		} else if ("시작Mo".equals(pjtCountData.get(length).getMoldCategory())
		        || "시작Fa".equals(pjtCountData.get(length).getMoldCategory())) {
		    startMoFa += pjtCountData.get(length).getNum();
		} else if ("양산".equals(pjtCountData.get(length).getMoldCategory())) {
		    production += pjtCountData.get(length).getNum();
		} else if ("양산Mo".equals(pjtCountData.get(length).getMoldCategory())
		        || "양산Fa".equals(pjtCountData.get(length).getMoldCategory())) {
		    productionMoFa += pjtCountData.get(length).getNum();
		}
	    } else if ("외주".equals(pjtCountData.get(length).getMaking())) {
		if ("시작".equals(pjtCountData.get(length).getMoldCategory())) {
		    startMold1 = pjtCountData.get(length).getNum();
		} else if ("시작Mo".equals(pjtCountData.get(length).getMoldCategory())
		        || "시작Fa".equals(pjtCountData.get(length).getMoldCategory())) {
		    startMoFa1 += pjtCountData.get(length).getNum();
		} else if ("양산".equals(pjtCountData.get(length).getMoldCategory())) {
		    production1 = pjtCountData.get(length).getNum();
		} else if ("양산Mo".equals(pjtCountData.get(length).getMoldCategory())
		        || "양산Fa".equals(pjtCountData.get(length).getMoldCategory())) {
		    productionMoFa1 += pjtCountData.get(length).getNum();
		}
	    }
	}

	data.setStartMold(startMold);
	data.setStartMoFa(startMoFa);
	data.setProduction(production);
	data.setProductionMoFa(productionMoFa);
	pjtcount.add(data);
	data = new ProductProjectDTO();
	data.setStartMold(startMold1);
	data.setStartMoFa(startMoFa1);
	data.setProduction(production1);
	data.setProductionMoFa(productionMoFa1);
	pjtcount.add(data);
	return pjtcount;
    }

    public List<ProductProjectDTO> stepMoldTaskCount(List<DashBoardDTO> stepMoldTaskData) {

	int begin1 = 0, plan1 = 0, process1 = 0, firstTry1 = 0, debuging1_1 = 0, debuging1_2 = 0, debuging1_3 = 0, moldtransfer1 = 0;
	int begin2 = 0, plan2 = 0, process2 = 0, firstTry2 = 0, debuging2_1 = 0, debuging2_2 = 0, debuging2_3 = 0, moldtransfer2 = 0;
	int begin3 = 0, plan3 = 0, process3 = 0, firstTry3 = 0, debuging3_1 = 0, debuging3_2 = 0, debuging3_3 = 0, moldtransfer3 = 0;
	int begin4 = 0, plan4 = 0, process4 = 0, firstTry4 = 0, debuging4_1 = 0, debuging4_2 = 0, debuging4_3 = 0, moldtransfer4 = 0;

	int begin1_ = 0, plan1_ = 0, process1_ = 0, firstTry1_ = 0, debuging1_1_ = 0, debuging1_2_ = 0, debuging1_3_ = 0, moldtransfer1_ = 0;
	int begin2_ = 0, plan2_ = 0, process2_ = 0, firstTry2_ = 0, debuging2_1_ = 0, debuging2_2_ = 0, debuging2_3_ = 0, moldtransfer2_ = 0;
	int begin3_ = 0, plan3_ = 0, process3_ = 0, firstTry3_ = 0, debuging3_1_ = 0, debuging3_2_ = 0, debuging3_3_ = 0, moldtransfer3_ = 0;
	int begin4_ = 0, plan4_ = 0, process4_ = 0, firstTry4_ = 0, debuging4_1_ = 0, debuging4_2_ = 0, debuging4_3_ = 0, moldtransfer4_ = 0;

	for (int length = 0; length < stepMoldTaskData.size(); length++) {
	    if ("사내".equals(stepMoldTaskData.get(length).getMaking())) {
		if ("시작".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin1 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형설계".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan1 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process1 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry1 = stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_1 = stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_2 = stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_3 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer1 = stepMoldTaskData.get(length).getNum();
		    }
		} else if ("시작Mo".equals(stepMoldTaskData.get(length).getMoldCategory())
		        || "시작Fa".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin2 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형설계".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan2 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process2 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry2 += stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_1 += stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_2 += stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_3 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer2 += stepMoldTaskData.get(length).getNum();
		    }
		} else if ("양산".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin3 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형설계".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan3 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process3 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry3 = stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_1 = stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_2 = stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_3 = stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer3 = stepMoldTaskData.get(length).getNum();
		    }
		} else if ("양산Mo".equals(stepMoldTaskData.get(length).getMoldCategory())
		        || "양산Fa".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin4 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형설계".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan4 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process4 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry4 += stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_1 += stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_2 += stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_3 += stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer4 += stepMoldTaskData.get(length).getNum();
		    }
		}
	    } else {
		if ("시작".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("외주금형제작".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_2_ = stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging1_3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer1_ = stepMoldTaskData.get(length).getNum();
		    }
		} else if ("시작Mo".equals(stepMoldTaskData.get(length).getMoldCategory())
		        || "시작Fa".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("외주금형제작".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_1_ += stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging2_3_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer2_ += stepMoldTaskData.get(length).getNum();
		    }
		} else if ("양산".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("외주금형제작".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_1_ = stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_2_ = stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging3_3_ = stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer3_ = stepMoldTaskData.get(length).getNum();
		    }
		} else if ("양산Mo".equals(stepMoldTaskData.get(length).getMoldCategory())
		        || "양산Fa".equals(stepMoldTaskData.get(length).getMoldCategory())) {
		    if ("제품도출도".equals(stepMoldTaskData.get(length).getTaskName())) {
			begin4_ += stepMoldTaskData.get(length).getNum();
		    } else if ("외주금형제작".equals(stepMoldTaskData.get(length).getTaskName())) {
			plan4_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형부품".equals(stepMoldTaskData.get(length).getTaskName())) {
			process4_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형Try_[T0]".equals(stepMoldTaskData.get(length).getTaskName())) {
			firstTry4_ += stepMoldTaskData.get(length).getNum();
		    } else if ("1 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_1_ += stepMoldTaskData.get(length).getNum();
		    } else if ("2 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_2_ += stepMoldTaskData.get(length).getNum();
		    } else if ("3 차 디버깅".equals(stepMoldTaskData.get(length).getTaskName())) {
			debuging4_3_ += stepMoldTaskData.get(length).getNum();
		    } else if ("금형이관단계".equals(stepMoldTaskData.get(length).getTaskName())) {
			moldtransfer4_ += stepMoldTaskData.get(length).getNum();
		    }
		}
	    }
	}

	List<ProductProjectDTO> stepMoldTaskCount = new ArrayList<ProductProjectDTO>();
	ProductProjectDTO data = new ProductProjectDTO();
	data.setBegin(begin1);
	data.setPlan(plan1);
	data.setProcess(process1);
	data.setFirstTry(firstTry1);
	data.setDebuging1(debuging1_1);
	data.setDebuging2(debuging1_2);
	data.setDebuging3(debuging1_3);
	data.setMoldtransfer(moldtransfer1);
	data.setTotal(begin1 + plan1 + process1 + firstTry1 + debuging1_1 + debuging1_2 + debuging1_3 + moldtransfer1);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin2);
	data.setPlan(plan2);
	data.setProcess(process2);
	data.setFirstTry(firstTry2);
	data.setDebuging1(debuging2_1);
	data.setDebuging2(debuging2_2);
	data.setDebuging3(debuging2_3);
	data.setMoldtransfer(moldtransfer2);
	data.setTotal(begin2 + plan2 + process2 + firstTry2 + debuging2_1 + debuging2_2 + debuging2_3 + moldtransfer2);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin3);
	data.setPlan(plan3);
	data.setProcess(process3);
	data.setFirstTry(firstTry3);
	data.setDebuging1(debuging3_1);
	data.setDebuging2(debuging3_2);
	data.setDebuging3(debuging3_3);
	data.setMoldtransfer(moldtransfer3);
	data.setTotal(begin3 + plan3 + process3 + firstTry3 + debuging3_1 + debuging3_2 + debuging3_3 + moldtransfer3);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin4);
	data.setPlan(plan4);
	data.setProcess(process4);
	data.setFirstTry(firstTry4);
	data.setDebuging1(debuging4_1);
	data.setDebuging2(debuging4_2);
	data.setDebuging3(debuging4_3);
	data.setMoldtransfer(moldtransfer4);
	data.setTotal(begin4 + plan4 + process4 + firstTry4 + debuging4_1 + debuging4_2 + debuging4_3 + moldtransfer4);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin1_);
	data.setPlan(plan1_);
	data.setProcess(process1_);
	data.setFirstTry(firstTry1_);
	data.setDebuging1(debuging1_1_);
	data.setDebuging2(debuging1_2_);
	data.setDebuging3(debuging1_3_);
	data.setMoldtransfer(moldtransfer1_);
	data.setTotal(begin1_ + plan1_ + process1_ + firstTry1_ + debuging1_1_ + debuging1_2_ + debuging1_3_ + moldtransfer1_);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin2_);
	data.setPlan(plan2_);
	data.setProcess(process2_);
	data.setFirstTry(firstTry2_);
	data.setDebuging1(debuging2_1_);
	data.setDebuging2(debuging2_2_);
	data.setDebuging3(debuging2_3_);
	data.setMoldtransfer(moldtransfer2_);
	data.setTotal(begin2_ + plan2_ + process2_ + firstTry2_ + debuging2_1_ + debuging2_2_ + debuging2_3_ + moldtransfer2_);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin3_);
	data.setPlan(plan3_);
	data.setProcess(process3_);
	data.setFirstTry(firstTry3_);
	data.setDebuging1(debuging3_1_);
	data.setDebuging2(debuging3_2_);
	data.setDebuging3(debuging3_3_);
	data.setMoldtransfer(moldtransfer3_);
	data.setTotal(begin3_ + plan3_ + process3_ + firstTry3_ + debuging3_1_ + debuging3_2_ + debuging3_3_ + moldtransfer3_);
	stepMoldTaskCount.add(data);
	data = new ProductProjectDTO();
	data.setBegin(begin4_);
	data.setPlan(plan4_);
	data.setProcess(process4_);
	data.setFirstTry(firstTry4_);
	data.setDebuging1(debuging4_1_);
	data.setDebuging2(debuging4_2_);
	data.setDebuging3(debuging4_3_);
	data.setMoldtransfer(moldtransfer4_);
	data.setTotal(begin4_ + plan4_ + process4_ + firstTry4_ + debuging4_1_ + debuging4_2_ + debuging4_3_ + moldtransfer4_);
	stepMoldTaskCount.add(data);
	return stepMoldTaskCount;
    }

    @SuppressWarnings("unchecked")
    public PageControl getProjectTaskReport(DashBoardDTO dashBoardDTO) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}
	String startDate = year + "-" + month + "-01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	if ("시작금형".equals(dashBoardDTO.getState()) || "양산금형".equals(dashBoardDTO.getState())) {
	    String[] state = new String[1];
	    state[0] = dashBoardDTO.getState().substring(0, 2);
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("시작Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "시작Mo";
	    state[1] = "시작Fa";
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("양산Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "양산Mo";
	    state[1] = "양산Fa";
	    dashBoardDTO.setvMoldCategory(state);
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	List<DashBoardDTO> projectTaskInfo = null;
	int totalCount = 0;
	if ("4 차 디버깅".equals(dashBoardDTO.getStep())) {
	    totalCount = dao.getTotalRecordCount("mold.projectTaskReportDebugfourAboveTotalCount", dashBoardDTO);
	    projectTaskInfo = dao.findPaging("mold.projectTaskReportDebugfourAbove", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("total".equals(dashBoardDTO.getStep())) {
	    if ("".equals(dashBoardDTO.getType())) {
		totalCount = dao.getTotalRecordCount("mold.projectTaksReportTotalCount1", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaksReportTotal1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
		dashBoardDTO.setDebuging("debuging");
	    } else {
		totalCount = dao.getTotalRecordCount("mold.projectTaksReportTotalCount", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaksReportTotal", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
		dashBoardDTO.setDebuging("debuging");
	    }
	} else {
	    if ("금형설계,외주금형제작".equals(dashBoardDTO.getStep())) {
		totalCount = dao.getTotalRecordCount("mold.projectTaskReportTotalCount1", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaskReport1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.projectTaskReportTotalCount", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaskReport", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, projectTaskInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);

	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl projectTaskDelayReport(DashBoardDTO dashBoardDTO) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}
	String startDate = year + "-" + month + "-01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	if ("시작금형".equals(dashBoardDTO.getState()) || "양산금형".equals(dashBoardDTO.getState())) {
	    String[] state = new String[1];
	    state[0] = dashBoardDTO.getState().substring(0, 2);
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("시작Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "시작Mo";
	    state[1] = "시작Fa";
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("양산Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "양산Mo";
	    state[1] = "양산Fa";
	    dashBoardDTO.setvMoldCategory(state);
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	List<DashBoardDTO> projectTaskInfo = null;
	int totalCount = 0;
	if ("4 차 디버깅".equals(dashBoardDTO.getStep())) {
	    totalCount = dao.getTotalRecordCount("mold.projectTaskReportDelayDebugfourAboveTotalCount", dashBoardDTO);
	    projectTaskInfo = dao.findPaging("mold.projectTaskReportDelayDebugfourAbove", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("total".equals(dashBoardDTO.getStep())) {
	    if ("".equals(dashBoardDTO.getType())) {
		totalCount = dao.getTotalRecordCount("mold.projectTaksDelayReportTotalCount1", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaksDelayReportTotal1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
		dashBoardDTO.setDebuging("debuging");
	    } else {
		totalCount = dao.getTotalRecordCount("mold.projectTaksDelayReportTotalCount", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaksDelayReportTotal", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
		dashBoardDTO.setDebuging("debuging");
	    }
	} else {
	    if ("금형설계,외주금형제작".equals(dashBoardDTO.getStep())) {
		totalCount = dao.getTotalRecordCount("mold.projectTaskDelayReportTotalCount1", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaskDelayReport1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.projectTaskDelayReportTotalCount", dashBoardDTO);
		projectTaskInfo = dao.findPaging("mold.projectTaskDelayReport", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, projectTaskInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);

	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl getProjectIssueReport(DashBoardDTO dashBoardDTO) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "-" + month + "-01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	if ("시작금형".equals(dashBoardDTO.getState()) || "양산금형".equals(dashBoardDTO.getState())) {
	    String[] state = new String[1];
	    state[0] = dashBoardDTO.getState().substring(0, 2);
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("시작Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "시작Mo";
	    state[1] = "시작Fa";
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("양산Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "양산Mo";
	    state[1] = "양산Fa";
	    dashBoardDTO.setvMoldCategory(state);
	}

	if (dashBoardDTO.getSortName().startsWith("-")) {
	    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
	    dashBoardDTO.setSortType("desc");
	} else {
	    dashBoardDTO.setSortType("asc");
	}

	int totalCount = dao.getTotalRecordCount("mold.projectIssueReportTotalCount", dashBoardDTO);
	List<DashBoardDTO> projectIssueInfo = dao.findPaging("mold.projectIssueReport", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, projectIssueInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);

	return pageControl;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageControl getProjectReport(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}
	String startDate = year + "-" + month + "-01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	if ("시작금형".equals(dashBoardDTO.getState()) || "양산금형".equals(dashBoardDTO.getState())) {
	    String[] state = new String[1];
	    state[0] = dashBoardDTO.getState().substring(0, 2);
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("시작Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "시작Mo";
	    state[1] = "시작Fa";
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("양산Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "양산Mo";
	    state[1] = "양산Fa";
	    dashBoardDTO.setvMoldCategory(state);
	}

	int totalCount = dao.getTotalRecordCount("mold.projectReportTotalCount", dashBoardDTO);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	List<DashBoardDTO> projectInfo = dao.findPaging("mold.projectReport", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < projectInfo.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = projectInfo.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    projectInfo.get(index).setBudgetCost(totalPlanPrice / 1000);
	    projectInfo.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, projectInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);

	return pageControl;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageControl projectDelayReport(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}
	String startDate = year + "-" + month + "-01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	if ("시작금형".equals(dashBoardDTO.getState()) || "양산금형".equals(dashBoardDTO.getState())) {
	    String[] state = new String[1];
	    state[0] = dashBoardDTO.getState().substring(0, 2);
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("시작Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "시작Mo";
	    state[1] = "시작Fa";
	    dashBoardDTO.setvMoldCategory(state);
	} else if ("양산Mo/Fa".equals(dashBoardDTO.getState())) {
	    String[] state = new String[2];
	    state[0] = "양산Mo";
	    state[1] = "양산Fa";
	    dashBoardDTO.setvMoldCategory(state);
	}

	int totalCount = dao.getTotalRecordCount("mold.projectDelayReportTotalCount", dashBoardDTO);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	List<DashBoardDTO> projectInfo = dao.findPaging("mold.projectDelayReport", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < projectInfo.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = projectInfo.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    projectInfo.get(index).setBudgetCost(totalPlanPrice / 1000);
	    projectInfo.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, projectInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);

	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public List<DevTypeDTO> getTypeByDevMakeReport(DashBoardDTO dashBoardDTO, Model model) {

	model.addAttribute("typeDivide", dashBoardDTO.getTypeDivide());

	int sujuReviewProCount = 0, sujuProductProCount = 0, sujuMoldProCount = 0;
	int sujuReviewComCount = 0, sujuProductComCount = 0, sujuMoldComCount = 0;
	int yenguReviewProCount = 0, yenguProductProCount = 0, yenguMoldProCount = 0;
	int yenguReviewComCount = 0, yenguProductComCount = 0, yenguMoldComCount = 0;
	int junReviewProCount = 0, junProductProCount = 0, junMoldProCount = 0;
	int junReviewComCount = 0, junProductComCount = 0, junMoldComCount = 0;
	int chogaReviewProCount = 0, chogaProductProCount = 0, chogaMoldProCount = 0;
	int chogaReviewComCount = 0, chogaProductComCount = 0, chogaMoldComCount = 0;
	int sujuReviewTotalCount = 0, sujuProductTotalCount = 0, sujuMoldTotalCount = 0;
	int yenguReviewTotalCount = 0, yenguProductTotalCount = 0, yenguMoldTotalCount = 0;
	int junReviewTotalCount = 0, junProductTotalCount = 0, junMoldTotalCount = 0;
	int chogaReviewTotalCount = 0, chogaProductTotalCount = 0, chogaMoldTotalCount = 0;
	int reviewTotalCount = 0, productTotalCount = 0, moldTotalCount = 0;
	int reviewComTotalCount = 0, productComTotalCount = 0, moldComTotalCount = 0;
	int reviewProTotalCount = 0, productProTotalCount = 0, moldProTotalCount = 0;

	Calendar oCalendar = Calendar.getInstance();

	String yearType = String.valueOf(dashBoardDTO.getYear());
	int year = 0;
	if (yearType.equals("0")) {
	    year = oCalendar.get(Calendar.YEAR);
	} else {
	    year = dashBoardDTO.getYear();
	}

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> findList = dao.find("mold.typeByDevMakeReportList", dashBoardDTO);

	if (findList != null) {
	    for (int index = 0; index < findList.size(); index++) {
		if ("검토".equals(findList.get(index).getType())) {
		    if ("COMPLETED".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuReviewComCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguReviewComCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junReviewComCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaReviewComCount = findList.get(index).getNum();
			}
		    } else if ("PROGRESS".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuReviewProCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguReviewProCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junReviewProCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaReviewProCount = findList.get(index).getNum();
			}
		    }
		} else if ("제품".equals(findList.get(index).getType())) {
		    if ("COMPLETED".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuProductComCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguProductComCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junProductComCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaProductComCount = findList.get(index).getNum();
			}
		    } else if ("PROGRESS".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuProductProCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguProductProCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junProductProCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaProductProCount = findList.get(index).getNum();
			}
		    }
		} else if ("금형".equals(findList.get(index).getType())) {
		    if ("COMPLETED".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuMoldComCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguMoldComCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junMoldComCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaMoldComCount = findList.get(index).getNum();
			}
		    } else if ("PROGRESS".equals(findList.get(index).getState())) {
			if ("수주개발".equals(findList.get(index).getDevType())) {
			    sujuMoldProCount = findList.get(index).getNum();
			} else if ("연구".equals(findList.get(index).getDevType())) {
			    yenguMoldProCount = findList.get(index).getNum();
			} else if ("전략개발".equals(findList.get(index).getDevType())) {
			    junMoldProCount = findList.get(index).getNum();
			} else if ("4M".equals(findList.get(index).getDevType())) {
			    chogaMoldProCount = findList.get(index).getNum();
			}
		    }
		}
	    }

	    sujuReviewTotalCount = sujuReviewComCount + sujuReviewProCount;
	    sujuProductTotalCount = sujuProductComCount + sujuProductProCount;
	    sujuMoldTotalCount = sujuMoldComCount + sujuMoldProCount;

	    junReviewTotalCount = junReviewComCount + junReviewProCount;
	    junProductTotalCount = junProductComCount + junProductProCount;
	    junMoldTotalCount = junMoldComCount + junMoldProCount;

	    yenguReviewTotalCount = yenguReviewComCount + yenguReviewProCount;
	    yenguProductTotalCount = yenguProductComCount + yenguProductProCount;
	    yenguMoldTotalCount = yenguMoldComCount + yenguMoldProCount;

	    chogaReviewTotalCount = chogaReviewComCount + chogaReviewProCount;
	    chogaProductTotalCount = chogaProductComCount + chogaProductProCount;
	    chogaMoldTotalCount = chogaMoldComCount + chogaMoldProCount;

	    reviewTotalCount = sujuReviewTotalCount + junReviewTotalCount + yenguReviewTotalCount + chogaReviewTotalCount;
	    productTotalCount = sujuProductTotalCount + junProductTotalCount + yenguProductTotalCount + chogaProductTotalCount;
	    moldTotalCount = sujuMoldTotalCount + junMoldTotalCount + yenguMoldTotalCount + chogaMoldTotalCount;

	    reviewComTotalCount = sujuReviewComCount + junReviewComCount + yenguReviewComCount + chogaReviewComCount;
	    productComTotalCount = sujuProductComCount + junProductComCount + yenguProductComCount + chogaProductComCount;
	    moldComTotalCount = sujuMoldComCount + junMoldComCount + yenguMoldComCount + chogaMoldComCount;

	    reviewProTotalCount = sujuReviewProCount + junReviewProCount + yenguReviewProCount + chogaReviewProCount;
	    productProTotalCount = sujuProductProCount + junProductProCount + yenguProductProCount + chogaProductProCount;
	    moldProTotalCount = sujuMoldProCount + junMoldProCount + yenguMoldProCount + chogaMoldProCount;
	}

	List<DevTypeDTO> list = new ArrayList<DevTypeDTO>();
	DevTypeDTO data = new DevTypeDTO();
	data.setDevType("수주개발");
	data.setReviewTotalCount(sujuReviewTotalCount);
	data.setReviewComTotalCount(sujuReviewComCount);
	data.setReviewProTotalCount(sujuReviewProCount);

	data.setProductTotalCount(sujuProductTotalCount);
	data.setProductComTotalCount(sujuProductComCount);
	data.setProductProTotalCount(sujuProductProCount);

	data.setMoldTotalCount(sujuMoldTotalCount);
	data.setMoldComTotalCount(sujuMoldComCount);
	data.setMoldProTotalCount(sujuMoldProCount);
	list.add(data);

	data = new DevTypeDTO();
	data.setDevType("전략개발");
	data.setReviewTotalCount(junReviewTotalCount);
	data.setReviewComTotalCount(junReviewComCount);
	data.setReviewProTotalCount(junReviewProCount);

	data.setProductTotalCount(junProductTotalCount);
	data.setProductComTotalCount(junProductComCount);
	data.setProductProTotalCount(junProductProCount);

	data.setMoldTotalCount(junMoldTotalCount);
	data.setMoldComTotalCount(junMoldComCount);
	data.setMoldProTotalCount(junMoldProCount);
	list.add(data);

	data = new DevTypeDTO();
	data.setDevType("연구");
	data.setReviewTotalCount(yenguReviewTotalCount);
	data.setReviewComTotalCount(yenguReviewComCount);
	data.setReviewProTotalCount(yenguReviewProCount);

	data.setProductTotalCount(yenguProductTotalCount);
	data.setProductComTotalCount(yenguProductComCount);
	data.setProductProTotalCount(yenguProductProCount);

	data.setMoldTotalCount(yenguMoldTotalCount);
	data.setMoldComTotalCount(yenguMoldComCount);
	data.setMoldProTotalCount(yenguMoldProCount);
	list.add(data);

	data = new DevTypeDTO();
	data.setDevType("4M");
	data.setReviewTotalCount(chogaReviewTotalCount);
	data.setReviewComTotalCount(chogaReviewComCount);
	data.setReviewProTotalCount(chogaReviewProCount);

	data.setProductTotalCount(chogaProductTotalCount);
	data.setProductComTotalCount(chogaProductComCount);
	data.setProductProTotalCount(chogaProductProCount);

	data.setMoldTotalCount(chogaMoldTotalCount);
	data.setMoldComTotalCount(chogaMoldComCount);
	data.setMoldProTotalCount(chogaMoldProCount);
	list.add(data);

	data = new DevTypeDTO();
	data.setDevType("전체");
	data.setReviewTotalCount(reviewTotalCount);
	data.setReviewComTotalCount(reviewComTotalCount);
	data.setReviewProTotalCount(reviewProTotalCount);

	data.setProductTotalCount(productTotalCount);
	data.setProductComTotalCount(productComTotalCount);
	data.setProductProTotalCount(productProTotalCount);

	data.setMoldTotalCount(moldTotalCount);
	data.setMoldComTotalCount(moldComTotalCount);
	data.setMoldProTotalCount(moldProTotalCount);
	list.add(data);

	return list;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> electornStackColum(DashBoardDTO dashBoardDTO) {
	/* 쿼리 작업 */
	List<DashBoardDTO> qulityProblemData = dao.find("mold.qulityProblemDataInfo", dashBoardDTO);

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	// chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	// chartAttr.put("borderAlpha", "20");
	// chartAttr.put("showCanvasBorder", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("plotSpacePercent", "65");
	chartAttr.put("showValues", "0");
	chartAttr.put("showLegend", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("labelDisplay", "WRAP");
	// chartAttr.put("showSum", "0");
	// chartAttr.put("valueFontColor", "#ffffff");
	// chartAttr.put("showXAxisLine", "1");
	// chartAttr.put("xAxisLineColor", "#999999");
	// chartAttr.put("divlineColor", "#999999");
	// chartAttr.put("showHoverEffect", "1");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = null;

	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "기능");
	categoryAttr.add(categoryAttr1);

	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "외관");
	categoryAttr.add(categoryAttr1);

	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "조립");
	categoryAttr.add(categoryAttr1);

	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "관리");
	categoryAttr.add(categoryAttr1);

	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	int fun1 = 0, fun2 = 0, fun3 = 0;
	int out1 = 0, out2 = 0, out3 = 0;
	int jo1 = 0, jo2 = 0, jo3 = 0;
	int manage1 = 0, manage2 = 0, manage3 = 0;
	/* 사내,고객,외주 값 */
	for (int index = 0; index < qulityProblemData.size(); index++) {
	    if ("기능불량".equals(qulityProblemData.get(index).getType())) {
		if ("사내".equals(qulityProblemData.get(index).getOccurName())) {
		    fun1 = qulityProblemData.get(index).getNum();
		} else if ("고객".equals(qulityProblemData.get(index).getOccurName())) {
		    fun2 = qulityProblemData.get(index).getNum();
		} else if ("외주".equals(qulityProblemData.get(index).getOccurName())) {
		    fun3 = qulityProblemData.get(index).getNum();
		}
	    } else if ("외관불량".equals(qulityProblemData.get(index).getType())) {
		if ("사내".equals(qulityProblemData.get(index).getOccurName())) {
		    out1 = qulityProblemData.get(index).getNum();
		} else if ("고객".equals(qulityProblemData.get(index).getOccurName())) {
		    out2 = qulityProblemData.get(index).getNum();
		} else if ("외주".equals(qulityProblemData.get(index).getOccurName())) {
		    out3 = qulityProblemData.get(index).getNum();
		}
	    } else if ("조립불량".equals(qulityProblemData.get(index).getType())) {
		if ("사내".equals(qulityProblemData.get(index).getOccurName())) {
		    jo1 = qulityProblemData.get(index).getNum();
		} else if ("고객".equals(qulityProblemData.get(index).getOccurName())) {
		    jo2 = qulityProblemData.get(index).getNum();
		} else if ("외주".equals(qulityProblemData.get(index).getOccurName())) {
		    jo3 = qulityProblemData.get(index).getNum();
		}
	    } else if ("관리불량".equals(qulityProblemData.get(index).getType())) {
		if ("사내".equals(qulityProblemData.get(index).getOccurName())) {
		    manage1 = qulityProblemData.get(index).getNum();
		} else if ("고객".equals(qulityProblemData.get(index).getOccurName())) {
		    manage2 = qulityProblemData.get(index).getNum();
		} else if ("외주".equals(qulityProblemData.get(index).getOccurName())) {
		    manage3 = qulityProblemData.get(index).getNum();
		}
	    }
	}

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("color", "6482c0");

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", fun1);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('F','I')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", out1);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('O','I')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", jo1);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('J','I')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", manage1);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('M','I')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesname", "사내");
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", fun2);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('F','C')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", out2);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('O','C')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", jo2);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('J','C')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", manage2);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('M','C')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesname", "고객");
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", fun3);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('F','O')");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", out3);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('O','O')");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", jo3);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('J','O')");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", manage3);
	dataAttr1.put("link", "JavaScript:devQulityProblemPopup('M','O')");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesname", "외주");
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> electornStackColum1(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> yearData = dao.find("mold.eletronCompletedProgressData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "완료");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "진행");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','S');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','S');");
		dataAttr.add(dataAttr1);
	    }
	}

	// datasetAttr1.put("seriesname", "수주");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FF0000");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','J');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','J');");
		dataAttr.add(dataAttr1);
	    }
	}
	// datasetAttr1.put("seriesname", "전략");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','Y');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','Y');");
		dataAttr.add(dataAttr1);
	    }
	}
	// datasetAttr1.put("seriesname", "연구");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','C');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','C');");
		dataAttr.add(dataAttr1);
	    }
	}
	// datasetAttr1.put("seriesname", "추가");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    /*
     * public Map<String, Object> electornStackColum2(DashBoardDTO dashBoardDTO) { List<DashBoardDTO> yearData =
     * dao.find("mold.eletronLastAndThisYear", dashBoardDTO);
     * 
     * Map<String, Object> data = new HashMap<String, Object>(); Map<String, Object> chartAttr = new HashMap<String, Object>();
     * chartAttr.put("caption", ""); chartAttr.put("animation", "0"); chartAttr.put("bgcolor", "ffffff"); chartAttr.put("yAxisMaxValue",
     * "450"); chartAttr.put("yAxisMinValue", "0"); chartAttr.put("xAxisName", "7월");
     * 
     * List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>(); Map<String, Object> categoriesAttr1 = new
     * HashMap<String, Object>(); List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>(); Map<String, String>
     * categoryAttr1 = new HashMap<String, String>(); categoryAttr1.put("label", ""); categoryAttr.add(categoryAttr1); categoryAttr1 = new
     * HashMap<String, String>(); categoryAttr1.put("label", ""); categoryAttr.add(categoryAttr1); categoryAttr1 = new HashMap<String,
     * String>(); categoryAttr1.put("label", ""); categoryAttr.add(categoryAttr1); categoryAttr1 = new HashMap<String, String>();
     * categoryAttr1.put("label", ""); categoryAttr.add(categoryAttr1); categoriesAttr1.put("category", categoryAttr);
     * categoriesAttr.add(categoriesAttr1);
     * 
     * List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>(); Map<String, Object> datasetAttr1 = new HashMap<String,
     * Object>(); List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>(); Map<String, Object> dataAttr1;
     * 
     * for (int length = 0; length < yearData.size(); length++) { if ("lastYear".equals(yearData.get(length).getType()) &&
     * "수주개발".equals(yearData.get(length).getDevType())) { dataAttr1 = new HashMap<String, Object>(); dataAttr1.put("value",
     * yearData.get(length).getNum()); dataAttr.add(dataAttr1); } else if ("thisYear".equals(yearData.get(length).getType()) &&
     * "수주개발".equals(yearData.get(length).getDevType())) { dataAttr1 = new HashMap<String, Object>(); dataAttr1.put("value",
     * yearData.get(length).getNum()); dataAttr.add(dataAttr1); } }
     * 
     * datasetAttr1.put("seriesname", "지연"); datasetAttr1.put("data", dataAttr); datasetAttr1.put("color", "FF0000");
     * datasetAttr.add(datasetAttr1);
     * 
     * datasetAttr1 = new HashMap<String, Object>(); dataAttr = new ArrayList<Map<String, Object>>();
     * 
     * for (int length = 0; length < yearData.size(); length++) { if ("lastYear".equals(yearData.get(length).getType()) &&
     * "전략개발".equals(yearData.get(length).getDevType())) { dataAttr1 = new HashMap<String, Object>(); dataAttr1.put("value",
     * yearData.get(length).getNum()); dataAttr.add(dataAttr1); } else if ("thisYear".equals(yearData.get(length).getType()) &&
     * "전략개발".equals(yearData.get(length).getDevType())) { dataAttr1 = new HashMap<String, Object>(); dataAttr1.put("value",
     * yearData.get(length).getNum()); dataAttr.add(dataAttr1); } } // datasetAttr1.put("seriesname", "진행"); datasetAttr1.put("data",
     * dataAttr); datasetAttr.add(datasetAttr1);
     * 
     * data.put("chart", chartAttr); data.put("categories", categoriesAttr); data.put("dataset", datasetAttr);
     * 
     * Kogger.debug(getClass(), data); return data; }
     */

    @SuppressWarnings("unchecked")
    public PageControl getWorkTimeTeamReportList(DashBoardDTO dashBoardDTO) throws Exception {

	int totalCount = 0;
	List<DashBoardDTO> workTimeReportInfo = null;

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("departName".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("departName");
		} else if ("duty".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("duty");
		} else if ("userName".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("userName");
		}

		dashBoardDTO.setSortType("desc");
	    } else {
		if ("departName".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("departName");
		} else if ("duty".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("duty");
		} else if ("userName".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("userName");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Calendar c = Calendar.getInstance();
	String currentDate = formatter.format(c.getTime());

	dashBoardDTO.setCurrentDate(currentDate);
	String deptCode = null;
	Department dept = null;

	if (dashBoardDTO.getDeptCode() != "null" && dashBoardDTO.getDeptCode() != null
	        && StringUtils.isNotEmpty(dashBoardDTO.getDeptCode())) {
	    deptCode = dashBoardDTO.getDeptCode();
	    dept = (Department) CommonUtil.getObject(deptCode);
	    deptCode = dept.getCode();
	} else {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData peopleData = new PeopleData(user);
	    dept = (Department) peopleData.department;
	    deptCode = dept.getCode();

	}
	dashBoardDTO.setDeptCode(deptCode);

	if (dashBoardDTO.getUserCode() != "null" && dashBoardDTO.getUserCode() != null
	        && StringUtils.isNotEmpty(dashBoardDTO.getUserCode())) {
	    String WtUserOid = CommonUtil.getOIDLongValue2Str(dashBoardDTO.getUserCode());
	    dashBoardDTO.setUserCode(WtUserOid);
	}

	if (dashBoardDTO.getDuty() != null) {
	    String vDuty[] = dashBoardDTO.getDuty().split(",");
	    dashBoardDTO.setVduty(vDuty);
	}

	totalCount = dao.getTotalRecordCount("mold.workTimeTeamReportListTotalCount", dashBoardDTO);
	workTimeReportInfo = dao.findPaging("mold.workTimeTeamReportList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	// if (dashBoardDTO.getMoldType().equals("review")) {
	// if (dashBoardDTO.getDivision().equals("C")) {
	// dashBoardDTO.setPjtType("1");
	// } else if (dashBoardDTO.getDivision().equals("E")) {
	// dashBoardDTO.setPjtType("0");
	// }
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListReviewTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListReview", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// } else if (dashBoardDTO.getMoldType().equals("product")) {
	// if (dashBoardDTO.getDivision().equals("C")) {
	// dashBoardDTO.setPjtType("2");
	// } else if (dashBoardDTO.getDivision().equals("E")) {
	// dashBoardDTO.setPjtType("4");
	// }
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListProductTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListProduct", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// } else if (dashBoardDTO.getMoldType().equals("mold")) {
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListMoldTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListMold", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// }
	// }
	// }

	// for (int index = 0; index < workTimeReportInfo.size(); index++) {
	// List<String> workTimeList = new ArrayList<String>();
	// workTimeList = ProjectTaskCompHelper.service.getProjectWorkTimeSumList(workTimeReportInfo.get(index).getOid());
	// if (workTimeList != null) {
	// workTimeReportInfo.get(index).setExpectTime(workTimeList.get(0));
	// workTimeReportInfo.get(index).setRealTime(workTimeList.get(1));
	// }
	//
	// }

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, workTimeReportInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl getWorkTimeReportList(DashBoardDTO dashBoardDTO) throws Exception {

	int totalCount = 0;
	List<DashBoardDTO> workTimeReportInfo = null;

	if (dashBoardDTO.getDevType() != null) {
	    String vDevType[] = dashBoardDTO.getDevType().split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getState() != null) {
	    String vState[] = dashBoardDTO.getState().split(",");
	    dashBoardDTO.setvState(vState);
	}

	if (dashBoardDTO.getLastCompany() != null) {
	    String vLastCompany[] = dashBoardDTO.getLastCompany().split(",");
	    dashBoardDTO.setvLastCompany(vLastCompany);
	}

	if (dashBoardDTO.getRank() != null) {
	    String vRank[] = dashBoardDTO.getRank().split(",");
	    dashBoardDTO.setvRank(vRank);
	}

	if (dashBoardDTO.getMoldType1() != null) {
	    String vMoldType[] = dashBoardDTO.getMoldType1().split(",");
	    dashBoardDTO.setvMoldType(vMoldType);
	}

	if (dashBoardDTO.getDieCategory() != null) {
	    String vDieCategory[] = dashBoardDTO.getDieCategory().split(",");
	    dashBoardDTO.setvDieCategory(vDieCategory);
	}

	if (dashBoardDTO.getMoldCategory() != null) {
	    String vMoldCategory[] = dashBoardDTO.getMoldCategory().split(",");
	    dashBoardDTO.setvMoldCategory(vMoldCategory);
	}

	if (dashBoardDTO.getMaking() != null) {
	    String vMaking[] = dashBoardDTO.getMaking().split(",");
	    dashBoardDTO.setvMaking(vMaking);
	}

	if (!"".equals(StringUtil.checkNull(dashBoardDTO.getPjtNo()))) {
	    String pjtNo = KETQueryUtil.getSqlQueryForMultiSearch("MASTER.PJTNO", dashBoardDTO.getPjtNo(), true);
	    dashBoardDTO.setPjtNo(pjtNo);
	}

	if (!"".equals(StringUtil.checkNull(dashBoardDTO.getPjtName()))) {
	    String pjtName = KETQueryUtil.getSqlQueryForMultiSearch("MASTER.PJTNAME", dashBoardDTO.getPjtName(), true);
	    dashBoardDTO.setPjtName(pjtName);
	}

	// if (dashBoardDTO.getInitMoldType() != null && dashBoardDTO.getMoldType() == null) {
	// if (dashBoardDTO.getInitMoldType() != null) {
	// if (dashBoardDTO.getInitMoldType().equals("review")) {
	// if (dashBoardDTO.getInitDivision().equals("C")) {
	// dashBoardDTO.setPjtType("1");
	// } else {
	// dashBoardDTO.setPjtType("0");
	// }
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListReviewTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListReview", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// } else if (dashBoardDTO.getInitMoldType().equals("product")) {
	// if (dashBoardDTO.getInitDivision().equals("C")) {
	// dashBoardDTO.setPjtType("2");
	// } else {
	// dashBoardDTO.setPjtType("4");
	// }
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListProductTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListProduct", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// } else if (dashBoardDTO.getInitMoldType().equals("mold")) {
	// totalCount = dao.getTotalRecordCount("mold.workTimeReportListMoldTotalCount", dashBoardDTO);
	// workTimeReportInfo = dao.findPaging("mold.workTimeReportListMold", dashBoardDTO, dashBoardDTO.getPage() + 1,
	// dashBoardDTO.getFormPage());
	// }
	// }
	// }

	// if (dashBoardDTO.getInitMoldType() != null && dashBoardDTO.getMoldType() != null) {
	// if (dashBoardDTO.getMoldType() != null) {
	if (dashBoardDTO.getMoldType().equals("review")) {
	    if (dashBoardDTO.getDivision().equals("C")) {
		dashBoardDTO.setPjtType("1");
	    } else if (dashBoardDTO.getDivision().equals("E")) {
		dashBoardDTO.setPjtType("0");
	    }
	    totalCount = dao.getTotalRecordCount("mold.workTimeReportListReviewTotalCount", dashBoardDTO);
	    workTimeReportInfo = dao.findPaging("mold.workTimeReportListReview", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if (dashBoardDTO.getMoldType().equals("product")) {
	    if (dashBoardDTO.getDivision().equals("C")) {
		dashBoardDTO.setPjtType("2");
	    } else if (dashBoardDTO.getDivision().equals("E")) {
		dashBoardDTO.setPjtType("4");
	    }
	    totalCount = dao.getTotalRecordCount("mold.workTimeReportListProductTotalCount", dashBoardDTO);
	    workTimeReportInfo = dao.findPaging("mold.workTimeReportListProduct", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if (dashBoardDTO.getMoldType().equals("mold")) {
	    totalCount = dao.getTotalRecordCount("mold.workTimeReportListMoldTotalCount", dashBoardDTO);
	    workTimeReportInfo = dao.findPaging("mold.workTimeReportListMold", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}
	// }
	// }

	for (int index = 0; index < workTimeReportInfo.size(); index++) {
	    List<String> workTimeList = new ArrayList<String>();
	    workTimeList = ProjectTaskCompHelper.service.getProjectWorkTimeSumList(workTimeReportInfo.get(index).getOid());
	    if (workTimeList != null) {
		workTimeReportInfo.get(index).setExpectTime(workTimeList.get(0));
		workTimeReportInfo.get(index).setRealTime(workTimeList.get(1));
	    }

	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, workTimeReportInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public List<ProductProjectDTO> getCarTypeByDevMakeReportList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	} else {
	    dashBoardDTO.setSortName("sop");
	    dashBoardDTO.setSortType("asc");
	}

	List<ProductProjectDTO> data = dao.find("mold.carTypeByDevMakeReportData", dashBoardDTO);

	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yy.MM.dd", Locale.KOREA);
	Date currentTime = new Date();
	String mTime = mSimpleDateFormat.format(currentTime).replace(".", "");
	int today = Integer.parseInt(mTime);

	int model = 0, proto = 0, p1 = 0, p2 = 0, m = 0, sop = 0;
	List<Integer> currentState = new ArrayList<Integer>();
	List<Integer> nextState = new ArrayList<Integer>();

	String currentDate = "";
	String currnetDate1 = "";
	String nextDate = "";
	String nextDate1 = "";

	for (int index = 0; index < data.size(); index++) {
	    if (data.get(index).getModel() != null) {
		model = Integer.parseInt(data.get(index).getModel().replace("/", ""));
	    }
	    if (today > model) {
		currentState.add(model);
	    } else {
		nextState.add(model);
	    }
	    if (data.get(index).getProto() != null) {
		proto = Integer.parseInt(data.get(index).getProto().replace("/", ""));
	    }
	    if (today > proto) {
		currentState.add(proto);
	    } else {
		nextState.add(proto);
	    }
	    if (data.get(index).getP1() != null) {
		p1 = Integer.parseInt(data.get(index).getP1().replace("/", ""));
	    }
	    if (today > p1) {
		currentState.add(p1);
	    } else {
		nextState.add(p1);
	    }
	    if (data.get(index).getP2() != null) {
		p2 = Integer.parseInt(data.get(index).getP2().replace("/", ""));
	    }
	    if (today > p2) {
		currentState.add(p2);
	    } else {
		nextState.add(p2);
	    }
	    if (data.get(index).getM() != null) {
		m = Integer.parseInt(data.get(index).getM().replace("/", ""));
	    }
	    if (today > m) {
		currentState.add(m);
	    } else {
		nextState.add(m);
	    }
	    if (data.get(index).getSop() != null) {
		sop = Integer.parseInt(data.get(index).getSop().replace("/", ""));
	    }
	    if (today > sop) {
		currentState.add(sop);
	    } else {
		nextState.add(sop);
	    }
	    int max = 0;
	    if (currentState.size() > 0 && currentState != null) {
		max = Collections.max(currentState);
		String maxDate = String.valueOf(max);
		if (!maxDate.equals("0")) {
		    if (maxDate.length() < 6) {
			maxDate = "0" + maxDate;
		    }
		    currentDate = maxDate.substring(0, 2) + "-" + maxDate.substring(2, 4) + "-" + maxDate.substring(4, 6);
		    currnetDate1 = maxDate.substring(0, 2) + "-" + maxDate.substring(2, 4);
		    data.get(index).setCurrentDate1("20" + currnetDate1);
		    data.get(index).setCurrentDate("20" + currentDate);
		    data.get(index).setToday(today);
		    data.get(index).setSopDay(Integer.parseInt(maxDate));
		} else {
		    data.get(index).setCurrentDate("-");
		    data.get(index).setSopDay(0);
		}

		if (max == model) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("MODEL");
		    }
		} else if (max == proto) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("PROTO");
		    }
		} else if (max == p1) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("P1");
		    }
		} else if (max == p2) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("P2");
		    }
		} else if (max == m) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("M");
		    }
		} else if (max == sop) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("SOP");
		    }
		}
	    } else {
		data.get(index).setCurrentDate("-");
		data.get(index).setSopDay(0);
	    }
	    int min = 0;
	    if (nextState.size() > 0 && nextState != null) {
		min = Collections.min(nextState);
		String minDate = String.valueOf(min);
		if (!minDate.equals("0")) {
		    if (minDate.length() < 6) {
			minDate = "0" + minDate;
		    }
		    nextDate = minDate.substring(0, 2) + "-" + minDate.substring(2, 4) + "-" + minDate.substring(4, 6);
		    nextDate1 = minDate.substring(0, 2) + "-" + minDate.substring(2, 4);
		    data.get(index).setNextDate("20" + nextDate);
		    data.get(index).setNextDate1("20" + nextDate1);
		} else {
		    data.get(index).setNextDate("-");
		}
		if (min == model) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("MODEL");
		    }
		} else if (min == proto) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("PROTO");
		    }
		} else if (min == p1) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("P1");
		    }
		} else if (min == p2) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("P2");
		    }
		} else if (min == m) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("M");
		    }
		} else if (min == sop) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("SOP");
		    }
		}
	    } else {
		data.get(index).setNextDate("-");
	    }

	    currentState.clear();
	    nextState.clear();
	    model = 0;
	    proto = 0;
	    p1 = 0;
	    p2 = 0;
	    m = 0;
	    sop = 0;
	    currentDate = "";
	    nextDate = "";
	}

	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getChangeMultiColumOverallStatusChartData(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> completeIssueData = dao.find("mold.completeIssueData", dashBoardDTO);
	List<DashBoardDTO> notCompleteIssueData = dao.find("mold.notCompleteIssueData", dashBoardDTO);

	int moldCount = 0, scheduleCount = 0, costCount = 0, etcCount = 0, productCount = 0, qualityCount = 0, customerCount = 0;
	int moldCount_ = 0, scheduleCount_ = 0, costCount_ = 0, etcCount_ = 0, productCount_ = 0, qualityCount_ = 0, customerCount_ = 0;

	if (completeIssueData.size() > 0 && completeIssueData != null) {
	    for (int index = 0; index < completeIssueData.size(); index++) {
		if ("MOLD".equals(completeIssueData.get(index).getType())) {
		    moldCount = completeIssueData.get(index).getNum();
		} else if ("SCHEDULE".equals(completeIssueData.get(index).getType())) {
		    scheduleCount = completeIssueData.get(index).getNum();
		} else if ("COST".equals(completeIssueData.get(index).getType())) {
		    costCount = completeIssueData.get(index).getNum();
		} else if ("ETC".equals(completeIssueData.get(index).getType())) {
		    etcCount = completeIssueData.get(index).getNum();
		} else if ("PRODUCT".equals(completeIssueData.get(index).getType())) {
		    productCount = completeIssueData.get(index).getNum();
		} else if ("QUALITY".equals(completeIssueData.get(index).getType())) {
		    qualityCount = completeIssueData.get(index).getNum();
		} else if ("CUSTOMER".equals(completeIssueData.get(index).getType())) {
		    customerCount = completeIssueData.get(index).getNum();
		}
	    }
	}

	if (notCompleteIssueData.size() > 0 && notCompleteIssueData != null) {
	    for (int index = 0; index < notCompleteIssueData.size(); index++) {
		if ("MOLD".equals(notCompleteIssueData.get(index).getType())) {
		    moldCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("SCHEDULE".equals(notCompleteIssueData.get(index).getType())) {
		    scheduleCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("COST".equals(notCompleteIssueData.get(index).getType())) {
		    costCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("ETC".equals(notCompleteIssueData.get(index).getType())) {
		    etcCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("PRODUCT".equals(notCompleteIssueData.get(index).getType())) {
		    productCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("QUALITY".equals(notCompleteIssueData.get(index).getType())) {
		    qualityCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("CUSTOMER".equals(notCompleteIssueData.get(index).getType())) {
		    customerCount_ = notCompleteIssueData.get(index).getNum();
		}
	    }
	}

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("showLegend", "0");
	chartAttr.put("showValues", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("labelDisplay", "WRAP");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();

	categoryAttr1.put("label", "제품");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "금형");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "고객");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "품질");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "비용");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "일정");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "기타");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr1.put("font", "Malgun Gothic");
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "미완료");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("color", "89c211");

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", productCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','PRODUCT');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", moldCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','MOLD');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", customerCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','CUSTOMER');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", qualityCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','QUALITY');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", costCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','COST');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", scheduleCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','SCHEDULE');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", etcCount);
	dataAttr1.put("link", "JavaScript:linkPopUp2('Y','ETC');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesName", "완료");
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("color", "6482c0");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", productCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','PRODUCT');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", moldCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','MOLD');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", customerCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','CUSTOMER');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", qualityCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','QUALITY');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", costCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','COST');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", scheduleCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','SCHEDULE');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", etcCount_);
	dataAttr1.put("link", "JavaScript:linkPopUp2('N','ETC');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesName", "미완료");
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getProjectCard(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	List<DashBoardDTO> oid = dao.find("mold.projectOidData", dashBoardDTO);
	List<DashBoardDTO> data = dao.find("mold.projectCardBasicInfoData", dashBoardDTO);
	dashBoardDTO.setOid(oid.get(0).getOid());
	List<DashBoardDTO> gateResult = dao.find("mold.getProjectGateDataInfo", dashBoardDTO);

	if (gateResult.size() > 0) {
	    model.addAttribute("geteResult", StringUtil.checkNull(gateResult.get(0).getCurrentTaskName()));
	    model.addAttribute("geteResult1", StringUtil.checkNull(gateResult.get(0).getCurrentGate()));
	    model.addAttribute("geteResult2", StringUtil.checkNull(gateResult.get(0).getCurrentGateDate()));
	} else {
	    model.addAttribute("geteResult", "");
	    model.addAttribute("geteResult1", "");
	    model.addAttribute("geteResult2", "");
	}

	for (int index = 0; index < data.size(); index++) {
	    List<String> workTimeList = new ArrayList<String>();
	    workTimeList = ProjectTaskCompHelper.service.getProjectWorkTimeSumList(oid.get(0).getOid());
	    if (workTimeList != null) {
		data.get(index).setExpectTime(workTimeList.get(0));
	    }
	}
	model.addAttribute("productOid", oid.get(0).getOid());
	model.addAttribute("taskName", oid.get(0).getTaskName());
	model.addAttribute("type", oid.get(0).getType());
	model.addAttribute("displayName", oid.get(0).getDisplayName());
	model.addAttribute("projectType", data.get(0).getItDivision());

	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getCarSchedule(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> data = dao.find("mold.projectCardCarScheduleData", dashBoardDTO);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getMileStone(DashBoardDTO dashBoardDTO) throws Exception {
	List<DashBoardDTO> data = dao.find("mold.projectCardMileStoneData", dashBoardDTO);

	for (int index = 0; index < data.size(); index++) {
	    String oid = data.get(index).getOid();
	    E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	    E3PSTaskData taskData = new E3PSTaskData(task);
	    int state = taskData.getStateBarType();
	    if (state == 1) {
		data.get(index).setState("G");
	    } else if (state == 3) {
		data.get(index).setState("B");
	    } else if (state == 4) {
		data.get(index).setState("Y");
	    } else if (state == 5) {
		data.get(index).setState("R");
	    }
	}
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getTaskReport(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> data = dao.find("mold.projectTaskReportData", dashBoardDTO);
	return data;
    }

    public List<DashBoardDTO> getCostData(DashBoardDTO dashBoardDTO) throws Exception {

	String oid = dashBoardDTO.getOid();
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	float targetCost1 = 0;
	float targetCost2 = 0;
	float targetCost3 = 0;
	float targetCost4 = 0;
	float budget1 = 0;
	float budget2 = 0;
	float budget3 = 0;
	float budget4 = 0;
	float resultsCost1 = 0;
	float resultsCost2 = 0;
	float resultsCost3 = 0;
	float resultsCost4 = 0;
	float balanceCost1 = 0;
	float balanceCost2 = 0;
	float balanceCost3 = 0;
	float balanceCost4 = 0;

	java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
	int count = 0;
	QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	while (rtCost.hasMoreElements()) {
	    Object[] obj = (Object[]) rtCost.nextElement();
	    CostInfo costInfo = (CostInfo) obj[0];

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    if (costInfo.getOrderInvest() != null) {
		Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		if (totalPriceObj != null)
		    budget = totalPriceObj.longValue(); // 예산
		if (initExpenseObj != null)
		    executionCost = initExpenseObj.longValue(); // 초기집행가
		if (addExpenseObj != null)
		    editCost = addExpenseObj.longValue(); // 추가집행가
		if (totalExpenseObj != null)
		    totalExpense = totalExpenseObj.longValue(); // 총집행가
		balanceCost = budget - totalExpense; // 잔액
	    } else {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
		    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
		    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		totalExpense = executionCost + editCost; // 총집행가
		balanceCost = budget - totalExpense; // 잔액
	    }

	    if ("금형".equals(costInfo.getCostType())) {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 목표
		budget1 = budget1 + budget; // 예산
		resultsCost1 = resultsCost1 + executionCost + editCost; // 실적
		balanceCost1 = balanceCost1 + balanceCost; // 잔액
	    } else if ("설비".equals(costInfo.getCostType())) {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		budget2 = budget2 + budget;
		resultsCost2 = resultsCost2 + executionCost + editCost;
		balanceCost2 = balanceCost2 + balanceCost;
	    } else if ("JIG".equals(costInfo.getCostType())) {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		budget4 = budget4 + budget;
		resultsCost4 = resultsCost4 + executionCost + editCost;
		balanceCost4 = balanceCost4 + balanceCost;
	    } else {
		if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
		    targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		budget3 = budget3 + budget;
		resultsCost3 = resultsCost3 + executionCost + editCost;
		balanceCost3 = balanceCost3 + balanceCost;
	    }
	}

	DecimalFormat format = new DecimalFormat(".##");

	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO data1 = new DashBoardDTO();
	data1.setType("금형");
	data1.setTargetCost((float) (Math.round(Float.valueOf(format.format(targetCost1 / 1000000)) * 10) / 10.0));
	data1.setBudgetCost((float) (Math.round(Float.valueOf(format.format(budget1 / 1000000)) * 10) / 10.0));
	data1.setResultCost((float) (Math.round(Float.valueOf(format.format(resultsCost1 / 1000000)) * 10) / 10.0));
	data1.setBalanceCost((float) (Math.round(Float.valueOf(format.format(balanceCost1 / 1000000)) * 10) / 10.0));
	data.add(data1);
	data1 = new DashBoardDTO();
	data1.setType("설비");
	data1.setTargetCost((float) (Math.round(Float.valueOf(format.format(targetCost2 / 1000000)) * 10) / 10.0));
	data1.setBudgetCost((float) (Math.round(Float.valueOf(format.format(budget2 / 1000000)) * 10) / 10.0));
	data1.setResultCost((float) (Math.round(Float.valueOf(format.format(resultsCost2 / 1000000)) * 10) / 10.0));
	data1.setBalanceCost((float) (Math.round(Float.valueOf(format.format(balanceCost2 / 1000000)) * 10) / 10.0));
	data.add(data1);
	data1 = new DashBoardDTO();
	data1.setType("기타");
	data1.setTargetCost((float) (Math.round(Float.valueOf(format.format((targetCost3 + targetCost4) / 1000000)) * 10) / 10.0));
	data1.setBudgetCost((float) (Math.round(Float.valueOf(format.format((budget3 + budget4) / 1000000)) * 10) / 10.0));
	data1.setResultCost((float) (Math.round(Float.valueOf(format.format((resultsCost3 + resultsCost4) / 1000000)) * 10) / 10.0));
	data1.setBalanceCost((float) (Math.round(Float.valueOf(format.format((balanceCost3 + balanceCost4) / 1000000)) * 10) / 10.0));
	data.add(data1);
	data1 = new DashBoardDTO();
	data1.setType("계");
	data1.setTargetCost((float) (Math.round(Float.valueOf(format
	        .format((targetCost1 + targetCost2 + targetCost3 + targetCost4) / 1000000)) * 10) / 10.0));
	data1.setBudgetCost((float) (Math.round(Float.valueOf(format.format((budget1 + budget2 + budget3 + budget4) / 1000000)) * 10) / 10.0));
	data1.setResultCost((float) (Math.round(Float.valueOf(format
	        .format((resultsCost1 + resultsCost2 + resultsCost3 + resultsCost4) / 1000000)) * 10) / 10.0));
	data1.setBalanceCost((float) (Math.round(Float.valueOf(format
	        .format((balanceCost1 + balanceCost2 + balanceCost3 + balanceCost4) / 1000000)) * 10) / 10.0));
	data.add(data1);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getIssueData(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> info = dao.find("mold.getIssueData", dashBoardDTO);
	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO data1 = new DashBoardDTO();
	data1.setType("미완료");
	for (int index = 0; index < info.size(); index++) {
	    if (info.get(index).getIsDone().equals("진행")) {
		data1.setNum(info.get(index).getNum());
	    } else {
		data1.setNum(0);
	    }
	}
	data.add(data1);
	data1 = new DashBoardDTO();
	data1.setType("완료");
	for (int index = 0; index < info.size(); index++) {
	    if (info.get(index).getIsDone().equals("완료")) {
		data1.setNum(info.get(index).getNum());
	    } else {
		data1.setNum(0);
	    }
	}
	data.add(data1);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getProductInfoData(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	List<DashBoardDTO> partData = dao.find("mold.getProductPartData", dashBoardDTO);
	List<DashBoardDTO> itemData = dao.find("mold.getProductItemData", dashBoardDTO);

	if (partData.size() > 0) {
	    model.addAttribute("partOid", partData.get(0).getOid());
	}
	model.addAttribute("partData", partData);
	model.addAttribute("itemData", itemData);
	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO data1 = new DashBoardDTO();
	data1.setType("Part");
	data1.setNum(partData.size());
	data.add(data1);
	data1 = new DashBoardDTO();
	data1.setType("Item");
	data1.setNum(itemData.size());
	data.add(data1);
	return data;
    }

    @SuppressWarnings("unused")
    public List<DashBoardDTO> getProduceAndMakeData(DashBoardDTO dashBoardDTO, Model model) {
	List<DashBoardDTO> assem = dao.find("mold.getAssemblyData", dashBoardDTO);
	List<DashBoardDTO> assemData = dao.find("mold.getAssemblyDataInfo", dashBoardDTO);
	List<DashBoardDTO> produce = dao.find("mold.getProduceData", dashBoardDTO);
	List<DashBoardDTO> make = dao.find("mold.getMakeData", dashBoardDTO);
	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO data1 = new DashBoardDTO();
	data1.setType("조립처");
	if (assem.size() == 0 || assem == null) {
	    data1.setCompany(0);
	    data1.setOutCompany(0);
	} else {
	    for (int index = 0; index < assem.size(); index++) {
		if (assem.get(index).getType() != null) {
		    if (assem.get(index).getType().equals("사내")) {
			data1.setCompany(assem.get(index).getNum());
		    }
		    if (assem.get(index).getType().equals("외주")) {
			data1.setOutCompany(assem.get(index).getNum());
		    }
		}
	    }
	}
	data1.setTotal(String.valueOf(data1.getCompany() + data1.getOutCompany()));
	data.add(data1);
	model.addAttribute("assemData", assemData);
	data1 = new DashBoardDTO();
	data1.setType("생산처");
	int inValue = 0;
	int outValue = 0;
	if (produce.size() == 0) {
	    data1.setCompany(0);
	    data1.setOutCompany(0);
	} else {
	    for (int index = 0; index < produce.size(); index++) {
		if (produce.get(index).getProductionPlace().equals("사내")) {
		    inValue++;
		}
		if (produce.get(index).getProductionPlace().equals("외주")) {
		    outValue++;
		}
	    }
	}
	data1.setCompany(inValue);
	data1.setOutCompany(outValue);
	data1.setTotal(String.valueOf(inValue + outValue));
	data.add(data1);
	model.addAttribute("produceData", produce);
	data1 = new DashBoardDTO();
	data1.setType("제작처");
	int inMake = 0;
	int outMake = 0;
	if (make.size() == 0) {
	    data1.setCompany(0);
	    data1.setOutCompany(0);
	} else {
	    for (int index = 0; index < make.size(); index++) {
		if (make.get(index).getMaking().equals("사내")) {
		    inMake++;
		}
		if (make.get(index).getMaking().equals("외주")) {
		    outMake++;
		}
	    }
	}
	data1.setCompany(inMake);
	data1.setOutCompany(outMake);
	data1.setTotal(String.valueOf(inMake + outMake));
	data.add(data1);
	model.addAttribute("makeData", make);
	return data;
    }

    public Map<String, Object> getChangeStackColumOverallStatusChartData2(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> totalData = dao.find("mold.getCompleteSchedule", dashBoardDTO);
	List<DashBoardDTO> delayData = dao.find("mold.getDelaySchedule", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("xAxisName", month + "월");
	chartAttr.put("formatNumberScale", "0");
	// chartAttr.put("yAxisMaxValue", "450");
	// chartAttr.put("yAxisMinValue", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	int su = 0, su_ = 0, totalsu = 0;
	int jen = 0, jen_ = 0, totaljen = 0;
	int yen = 0, yen_ = 0, totalyen = 0;
	int cho = 0, cho_ = 0, totalcho = 0;

	for (int length = 0; length < totalData.size(); length++) {
	    if ("수주개발".equals(totalData.get(length).getType())) {
		su = totalData.get(length).getNum();
	    } else if ("전략개발".equals(totalData.get(length).getType())) {
		jen = totalData.get(length).getNum();

	    } else if ("연구개발".equals(totalData.get(length).getType())) {
		yen = totalData.get(length).getNum();
	    } else if ("추가금형".equals(totalData.get(length).getType())) {
		cho = totalData.get(length).getNum();
	    }
	}

	for (int length = 0; length < delayData.size(); length++) {
	    if ("수주개발".equals(delayData.get(length).getType())) {
		su_ = delayData.get(length).getNum();
	    } else if ("전략개발".equals(delayData.get(length).getType())) {
		jen_ = delayData.get(length).getNum();

	    } else if ("연구개발".equals(delayData.get(length).getType())) {
		yen_ = delayData.get(length).getNum();
	    } else if ("추가금형".equals(delayData.get(length).getType())) {
		cho_ = delayData.get(length).getNum();
	    }
	}

	totalsu = su - su_;
	totaljen = jen - jen_;
	totalyen = yen - yen_;
	totalcho = cho - cho_;

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalsu);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totaljen);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalcho);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalyen);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	// datasetAttr1.put("seriesname", "전체");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FFFF00");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", su_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", jen_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", cho_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", yen_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	// datasetAttr1.put("seriesname", "지연");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getChangeMultiColumOverallStatusChartData1(DashBoardDTO dashBoardDTO) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	List<DashBoardDTO> afterMonthData = null;
	List<DashBoardDTO> twoAfterMonthData = null;

	for (int index = 0; index < 2; index++) {
	    month++;
	    switch (month) {
	    case 1:
	    case 3:
	    case 5:
	    case 7:
	    case 8:
	    case 10:
	    case 12:
		day = 31; // 31일까지 있는 달
		break;
	    case 4:
	    case 6:
	    case 9:
	    case 11:
		day = 30; // 30일까지 있는 달
		break;
	    case 2: // 평년 2월은 28일, 윤년은 29일
		day = yoonDal.equals("28") ? 28 : 29;
		break;
	    default: // 1~12월의 유효성검사
		Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    }
	    String startDate = "";
	    String endDate = "";

	    if (index == 0) {
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		afterMonthData = dao.find("mold.getCompleteScheduleAfterMonth", dashBoardDTO);
	    } else {
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		twoAfterMonthData = dao.find("mold.getCompleteScheduleTwoAfterMonth", dashBoardDTO);
	    }
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", (month - 1) + "월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", month + "월");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	int su = 0, su_ = 0;
	int jen = 0, jen_ = 0;
	int yen = 0, yen_ = 0;
	int cho = 0, cho_ = 0;

	for (int length = 0; length < afterMonthData.size(); length++) {
	    if ("수주개발".equals(afterMonthData.get(length).getType())) {
		su = afterMonthData.get(length).getNum();
	    } else if ("전략개발".equals(afterMonthData.get(length).getType())) {
		jen = afterMonthData.get(length).getNum();
	    } else if ("추가금형".equals(afterMonthData.get(length).getType())) {
		cho = afterMonthData.get(length).getNum();
	    } else if ("연구개발".equals(afterMonthData.get(length).getType())) {
		yen = afterMonthData.get(length).getNum();
	    }
	}

	for (int length = 0; length < twoAfterMonthData.size(); length++) {
	    if ("수주개발".equals(twoAfterMonthData.get(length).getType())) {
		su_ = twoAfterMonthData.get(length).getNum();
	    } else if ("전략개발".equals(twoAfterMonthData.get(length).getType())) {
		jen_ = twoAfterMonthData.get(length).getNum();
	    } else if ("추가금형".equals(twoAfterMonthData.get(length).getType())) {
		cho_ = twoAfterMonthData.get(length).getNum();
	    } else if ("연구개발".equals(twoAfterMonthData.get(length).getType())) {
		yen_ = twoAfterMonthData.get(length).getNum();
	    }
	}

	// datasetAttr1.put("seriesName", "수주");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", su);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','S','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", su_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','S','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	// datasetAttr1.put("seriesName", "전략");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", jen);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','J','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", jen_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','J','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	// datasetAttr1.put("seriesName", "추가");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", cho);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','C','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", cho_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','C','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	// datasetAttr1.put("seriesName", "연구");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", yen);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','Y','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", yen_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','Y','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getChangeMultiColumOverallStatusChartData2(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String pattern = "######.##";
	DecimalFormat dformat = new DecimalFormat(pattern);

	List<DashBoardDTO> costData = dao.find("mold.getCostProudctCarData", dashBoardDTO);

	for (int index = 0; index < costData.size(); index++) {

	    float targetCost1 = 0;
	    float targetCost2 = 0;
	    float targetCost3 = 0;
	    float targetCost4 = 0;
	    float budget1 = 0;
	    float budget2 = 0;
	    float budget3 = 0;
	    float budget4 = 0;
	    float resultsCost1 = 0;
	    float resultsCost2 = 0;
	    float resultsCost3 = 0;
	    float resultsCost4 = 0;
	    float balanceCost1 = 0;
	    float balanceCost2 = 0;
	    float balanceCost3 = 0;
	    float balanceCost4 = 0;
	    float totalbudget = 0;
	    float totalresult = 0;

	    int count = 0;

	    String oid = costData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		if ("금형".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost1 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 목표
		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		    costData.get(index).setBudgetCost(budget1 / 1000);
		    costData.get(index).setResultCost(resultsCost1 / 1000);
		    balanceCost1 += balanceCost; // 잔액
		} else if ("설비".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost2 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget2 += budget;
		    resultsCost2 += executionCost + editCost;
		    costData.get(index).setBudgetCost1(budget2 / 1000);
		    costData.get(index).setResultCost1(resultsCost2 / 1000);
		    balanceCost2 += balanceCost;
		} else if ("JIG".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost4 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget4 += budget;
		    resultsCost4 += executionCost + editCost;
		    costData.get(index).setBudgetCost3(budget4 / 1000);
		    costData.get(index).setResultCost3(resultsCost4 / 1000);
		    balanceCost4 += balanceCost;
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost3 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget3 += budget;
		    resultsCost3 += executionCost + editCost;
		    costData.get(index).setBudgetCost3(budget3 / 1000);
		    costData.get(index).setResultCost3(resultsCost3 / 1000);
		    balanceCost3 += balanceCost;
		}
		totalbudget = budget1 + budget2 + budget3 + budget4;
		totalresult = resultsCost1 + resultsCost2 + resultsCost3 + resultsCost4;
		costData.get(index).setTotalBudget(totalbudget);
		costData.get(index).setTotalResult(totalresult);
	    }

	}

	float suBudgetTotal = 0, suResultTotal = 0;
	float jeBudgetTotal = 0, jeResultTotal = 0;
	float yeBudgetTotal = 0, yeResultTotal = 0;
	float cuBudgetTotal = 0, cuResultTotal = 0;

	for (int index = 0; index < costData.size(); index++) {
	    if ("수주개발".equals(costData.get(index).getDevType())) {
		suBudgetTotal += costData.get(index).getTotalBudget();
		suResultTotal += costData.get(index).getTotalResult();
	    } else if ("전략개발".equals(costData.get(index).getDevType())) {
		jeBudgetTotal += costData.get(index).getTotalBudget();
		jeResultTotal += costData.get(index).getTotalResult();
	    } else if ("연구개발".equals(costData.get(index).getDevType())) {
		yeBudgetTotal += costData.get(index).getTotalBudget();
		yeResultTotal += costData.get(index).getTotalResult();
	    } else if ("추가금형".equals(costData.get(index).getDevType())) {
		cuBudgetTotal += costData.get(index).getTotalBudget();
		cuResultTotal += costData.get(index).getTotalResult();
	    }
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("showLegend", "0");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "수주");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "전략");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "연구");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "추가");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	DecimalFormat format = new DecimalFormat(".##");

	// datasetAttr1.put("seriesName", "예산");
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(suBudgetTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('S');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(jeBudgetTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('J');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(yeBudgetTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('Y');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(cuBudgetTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('C');");
	setAttr1.put("color", "6482c0");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr1.put("seriesName", "예산");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	// datasetAttr1.put("seriesName", "실적");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(suResultTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('S');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(jeResultTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('J');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(yeResultTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('Y');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", Math.round(Float.valueOf(format.format(cuResultTotal / 100000000)) * 10) / 10.0);
	setAttr1.put("link", "JavaScript:linkPopup5('C');");
	setAttr1.put("color", "89c211");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr1.put("seriesName", "실적");
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings({ "unchecked" })
    public Map<String, Object> getChangeMultiColumOverallStatusChartData3(DashBoardDTO dashBoardDTO, Model model) {

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> ecrData = dao.find("mold.getChangeDesignECR", dashBoardDTO);
	List<DashBoardDTO> ecoData = dao.find("mold.getChangeDesignECO", dashBoardDTO);
	List<DashBoardDTO> ecnData = dao.find("mold.getChangeDesignECN", dashBoardDTO);
	List<DashBoardDTO> ecnDelayData = dao.find("mold.getChangeDesignDelayECN", dashBoardDTO);
	int delayCount = ecnDelayData.size();

	int completeCountEcr = 0, processCountEcr = 0, totalCountEcr = 0;
	int completeCountEco = 0, processCountEco = 0, totalCountEco = 0;
	int completeCountEcn = 0, processCountEcn = 0, totalCountEcn = 0;

	if (ecrData.size() > 0 || ecrData != null) {
	    for (int index = 0; index < ecrData.size(); index++) {
		if ("진행".equals(ecrData.get(index).getState())) {
		    processCountEcr = ecrData.get(index).getNum();
		} else {
		    completeCountEcr = ecrData.get(index).getNum();
		}
	    }
	}

	totalCountEcr = completeCountEcr + processCountEcr;

	if (ecoData.size() > 0 || ecrData != null) {
	    for (int index = 0; index < ecoData.size(); index++) {
		if ("진행".equals(ecoData.get(index).getState())) {
		    processCountEco = ecoData.get(index).getNum();
		} else {
		    completeCountEco = ecoData.get(index).getNum();
		}
	    }
	}

	totalCountEco = completeCountEco + processCountEco;

	if (ecnData.size() > 0 || ecnData != null) {
	    for (int index = 0; index < ecnData.size(); index++) {
		if ("활동수행".equals(ecnData.get(index).getStateApproName())) {
		    processCountEcn = ecnData.get(index).getNum();
		} else {
		    completeCountEcn = ecnData.get(index).getNum();
		}
	    }
	}

	totalCountEcn = completeCountEcn + processCountEcn;

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("showLegend", "0");
	chartAttr.put("plotSpacePercent", "65");
	chartAttr.put("showValues", "0");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "ECR");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "ECO");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "ECN");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "전체");

	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", totalCountEcr);
	// setAttr1.put("link", "JavaScript:linkPopup6();");
	// setAttr1.put("color", "5babd4");
	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", totalCountEco);
	// setAttr1.put("link", "JavaScript:linkPopup7();");
	// setAttr1.put("color", "5babd4");
	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", totalCountEcn);
	// setAttr1.put("link", "JavaScript:linkPopup8();");
	// setAttr1.put("color", "5babd4");
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("set", setAttr);
	// datasetAttr.add(datasetAttr1);

	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();

	// datasetAttr1.put("seriesName", "완료");

	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", completeCountEcr);
	// setAttr1.put("link", "JavaScript:linkPopup6('C');");
	setAttr1.put("color", "89c211");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeCountEcr);
	dataAttr1.put("link", "JavaScript:linkPopup6('C');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeCountEco);
	dataAttr1.put("link", "JavaScript:linkPopup7('C');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeCountEcn);
	dataAttr1.put("link", "JavaScript:linkPopup8('C');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", completeCountEco);
	// setAttr1.put("link", "JavaScript:linkPopup7('C');");
	// setAttr1.put("color", "f2b579");
	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", completeCountEcn);
	// setAttr1.put("link", "JavaScript:linkPopup8('C');");
	// setAttr1.put("color", "f2b579");
	// setAttr.add(setAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesname", "완료");
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();

	// datasetAttr1.put("seriesName", "진행");

	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", processCountEcr);
	// setAttr1.put("link", "JavaScript:linkPopup6('P');");
	setAttr1.put("color", "6482c0");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processCountEcr);
	dataAttr1.put("link", "JavaScript:linkPopup6('P');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processCountEco);
	dataAttr1.put("link", "JavaScript:linkPopup7('P');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processCountEcn);
	dataAttr1.put("link", "JavaScript:linkPopup8('P');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesname", "진행");
	setAttr.add(setAttr1);

	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", processCountEco);
	// setAttr1.put("link", "JavaScript:linkPopup7('P');");
	// setAttr1.put("color", "aecd79");
	// setAttr.add(setAttr1);
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("value", processCountEcn);
	// setAttr1.put("link", "JavaScript:linkPopup8('P');");
	// setAttr1.put("color", "aecd79");
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("set", setAttr);
	// datasetAttr.add(datasetAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);
	data.put("delayCount", delayCount);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectReview(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectReviewTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByTotalProjectReviewList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectReview(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectReviewTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByCompleteProjectReviewList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectReview(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectReviewTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByProcessProjectReviewList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectProduct(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectProductTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByTotalProjectProductList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    completeProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    completeProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectProduct(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectProductTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByCompleteProjectProductList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    completeProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    completeProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectProduct(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectProductTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByProcessProjectProductList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    completeProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    completeProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectMold(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectMoldTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByTotalProjectMoldList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    completeProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    completeProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectMold(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectMoldTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByCompleteProjectMoldList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    completeProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    completeProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectMold(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectMoldTotalCount", dashBoardDTO);
	List<DashBoardDTO> completeProjectReviewData = dao.findPaging("mold.typeByProcessProjectMoldList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < completeProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = completeProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    completeProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    completeProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, completeProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectTotal(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByTotalProjectTotalList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectTotal1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectTotalCount1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByTotalProjectTotalList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByTotalProjectTotal2(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByTotalProjectTotalCount2", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByTotalProjectTotalList2", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectTotal(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByCompleteProjectTotalList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectTotal1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectTotalCount1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByCompleteProjectTotalList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByCompleteProjectTotal2(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByCompleteProjectTotalCount2", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByCompleteProjectTotalList2", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectTotal(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByProcessProjectTotalList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectTotal1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectTotal1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByProcessProjectTotalList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl typeByProcessProjectTotal2(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.typeByProcessProjectTotal2", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.typeByProcessProjectTotalList2", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl newAndoldProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year - 1) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.newAndoldProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.newAndoldProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl newAndoldProjectList1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year - 1) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.newAndoldProjectListTotalCount1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.newAndoldProjectList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl completeAndprocessProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.completeAndprocessProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.completeAndprocessProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl completeAndprocessProjectList1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year - 1) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.completeAndprocessProjectListTotalCount1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.completeAndprocessProjectList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl completeAndnotcompleteProjectList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	int totalCount = dao.getTotalRecordCount("mold.completeAndnotcompleteProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.completeAndnotcompleteProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl completeAndnotcompleteProjectList1(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	int totalCount = dao.getTotalRecordCount("mold.completeAndnotcompleteProjectListTotalCount1", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.completeAndnotcompleteProjectList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl afterAndtwoafterProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";
	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}
	String startDate = String.valueOf(year) + "/" + month + "/01";
	String endDate = String.valueOf(year) + "/" + month + "/" + day;
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.afterAndtwoafterProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.afterAndtwoafterProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl lastYearMoldProjectProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if ("car".equals(division)) {
	    dashBoardDTO.setPjtType("3");
	} else if ("elect".equals(division)) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.lastYearMoldProjectProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.lastYearMoldProjectProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl thisYearMoldProjectProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if ("car".equals(division)) {
	    dashBoardDTO.setPjtType("3");
	} else if ("elect".equals(division)) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.thisYearMoldProjectProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.thisYearMoldProjectProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl makingTypeProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount;
	List<DashBoardDTO> totalProjectReviewData;

	if ("COMPLETED".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.makingTypeProjectListTotalCount1", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.makingTypeProjectList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("PROGRESS".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.makingTypeProjectListTotalCount2", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.makingTypeProjectList2", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.makingTypeProjectListTotalCount", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.makingTypeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl startMassTypeProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount;
	List<DashBoardDTO> totalProjectReviewData;

	if ("COMPLETED".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.startMassTypeProjectListTotalCount1", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.startMassTypeProjectList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("PROGRESS".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.startMassTypeProjectListTotalCount2", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.startMassTypeProjectList2", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.startMassTypeProjectListTotalCount", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.startMassTypeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl moldPressTypeProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount;
	List<DashBoardDTO> totalProjectReviewData;

	if ("COMPLETED".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.moldPressTypeProjectListTotalCount1", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.moldPressTypeProjectList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("PROGRESS".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.moldPressTypeProjectListTotalCount2", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.moldPressTypeProjectList2", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.moldPressTypeProjectListTotalCount", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.moldPressTypeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl completeProcessTypeProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "-01-01";
	String endDate = String.valueOf(year) + "-12-31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount;
	List<DashBoardDTO> totalProjectReviewData;

	if ("PROGRESS".equals(dashBoardDTO.getType())) {
	    totalCount = dao.getTotalRecordCount("mold.completeProcessTypeProjectListTotalCount1", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.completeProcessTypeProjectList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.completeProcessTypeProjectListTotalCount", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.completeProcessTypeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());

	}
	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public List<ProductProjectDTO> getMoldManufactureList(DashBoardDTO dashBoardDTO, Model model) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	List<ProductProjectDTO> moldProjectCountData = dao.find("mold.moldManufactureProjectList", dashBoardDTO); // 업체별 금형프로젝트 수
	List<ProductProjectDTO> moldProjectDelayCountData = dao.find("mold.moldManufactureDelayProjectList", dashBoardDTO); // 업체별 금형프로젝트
	                                                                                                                    // 수(지연)
	List<ProductProjectDTO> moldProjectTaskCountData = dao.find("mold.moldManufactureTaskProjectList", dashBoardDTO); // 업체별 금형프로젝트 수 중에
	                                                                                                                  // TASK별 수
	List<ProductProjectDTO> moldProjectTaskDelayCountData = dao.find("mold.moldManufactureTaskDelayProjectList", dashBoardDTO); // 업체별
	                                                                                                                            // 금형프로젝트
	                                                                                                                            // 수 중에
	                                                                                                                            // TASK별
	                                                                                                                            // 수(지연)
	List<ProductProjectDTO> moldProjectTaskDebugCountData = dao.find("mold.moldManufactureTaskDebugProjectList", dashBoardDTO); // 업체별
	                                                                                                                            // 금형프로젝
	                                                                                                                            // 수 중에
	                                                                                                                            // TASK중
	                                                                                                                            // 디버깅4차이상
	                                                                                                                            // 수
	List<ProductProjectDTO> moldProjectTaskDebugDelayCountData = dao
	        .find("mold.moldManufactureTaskDebugDelayProjectList", dashBoardDTO); // 업체별
	                                                                              // 금형프로젝
	                                                                              // 수
	                                                                              // 중에
	                                                                              // TASK중
	                                                                              // 디버깅4차이상
	                                                                              // 수
	List<ProductProjectDTO> moldProjectIssueCountData = dao.find("mold.moldManufactureIssueProjectList", dashBoardDTO); // 업체별 금형프로젝트 수
	                                                                                                                    // 중에 이슈
	                                                                                                                    // 수

	List<ProductProjectDTO> data = new ArrayList<ProductProjectDTO>();
	ProductProjectDTO data1;

	if (moldProjectCountData.size() > 0 && moldProjectCountData != null) {
	    for (int index = 0; index < moldProjectCountData.size(); index++) {
		data1 = new ProductProjectDTO();
		data1.setItemType(moldProjectCountData.get(index).getItemType());
		data1.setOutsourcing(moldProjectCountData.get(index).getOutsourcing());
		data1.setProjectCount(moldProjectCountData.get(index).getNum());
		data.add(data1);
	    }
	}

	if (moldProjectDelayCountData.size() > 0 && moldProjectDelayCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectDelayCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectDelayCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectDelayCountData.get(index1).getItemType())) {
			data.get(index).setProjectDelayCount(moldProjectDelayCountData.get(index1).getNum());
		    }
		}
	    }
	}

	if (moldProjectTaskCountData.size() > 0 && moldProjectTaskCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectTaskCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectTaskCountData.get(index1).getItemType())) {
			if ("외주금형제작".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setPlan(moldProjectTaskCountData.get(index1).getNum());
			} else if ("금형Try_[T0]".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setFirstTry(moldProjectTaskCountData.get(index1).getNum());
			} else if ("1 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setDebuging1(moldProjectTaskCountData.get(index1).getNum());
			} else if ("2 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setDebuging2(moldProjectTaskCountData.get(index1).getNum());
			} else if ("3 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setDebuging3(moldProjectTaskCountData.get(index1).getNum());
			} else if ("금형이관단계".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    data.get(index).setMoldtransfer(moldProjectTaskCountData.get(index1).getNum());
			}
		    }
		}
	    }
	}

	if (moldProjectTaskDelayCountData.size() > 0 && moldProjectTaskDelayCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDelayCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectTaskDelayCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectTaskDelayCountData.get(index1).getItemType())) {
			if ("외주금형제작".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setPlanDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("금형Try_[T0]".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setFirstTryDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("1 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setDebugingDelay1(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("2 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setDebugingDelay2(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("3 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setDebugingDelay3(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("금형이관단계".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    data.get(index).setMoldtransferDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			}
		    }
		}
	    }
	}

	if (moldProjectTaskDebugCountData.size() > 0 && moldProjectTaskDebugCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDebugCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectTaskDebugCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectTaskDebugCountData.get(index1).getItemType())) {
			data.get(index).setDebuging4(moldProjectTaskDebugCountData.get(index1).getNum());
		    }
		}
	    }
	}

	if (moldProjectTaskDebugDelayCountData.size() > 0 && moldProjectTaskDebugDelayCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDebugDelayCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectTaskDebugDelayCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectTaskDebugDelayCountData.get(index1).getItemType())) {
			data.get(index).setDebugingDelay4(moldProjectTaskDebugDelayCountData.get(index1).getNum());
		    }
		}
	    }
	}

	if (moldProjectIssueCountData.size() > 0 && moldProjectIssueCountData != null) {
	    for (int index = 0; index < data.size(); index++) {
		for (int index1 = 0; index1 < moldProjectIssueCountData.size(); index1++) {
		    if (data.get(index).getOutsourcing().equals(moldProjectIssueCountData.get(index1).getOutsourcing())
			    && data.get(index).getItemType().equals(moldProjectIssueCountData.get(index1).getItemType())) {
			data.get(index).setIssueCount(moldProjectIssueCountData.get(index1).getNum());
		    }
		}
	    }
	}

	for (int index = 0; index < data.size(); index++) {
	    data.get(index).setTaskTotalCount(
		    data.get(index).getPlan() + data.get(index).getFirstTry() + data.get(index).getDebuging1()
		            + data.get(index).getDebuging2() + data.get(index).getDebuging3() + data.get(index).getDebuging4()
		            + data.get(index).getMoldtransfer());
	    data.get(index).setTaskTotalDelayCount(
		    data.get(index).getPlanDelay() + data.get(index).getFirstTryDelay() + data.get(index).getDebugingDelay1()
		            + data.get(index).getDebugingDelay2() + data.get(index).getDebugingDelay3()
		            + data.get(index).getDebugingDelay4() + data.get(index).getMoldtransferDelay());
	}

	ProductProjectDTO totalData = new ProductProjectDTO();
	int totalProjectCount = 0, totalProjectDelayCount = 0, taskTotalCount = 0, taskTotalDelayCount = 0, totalPlan = 0, totalDelayPlan = 0;
	int totalTry = 0, totalDelayTry = 0, totalDebug1 = 0, totalDebugDelay1 = 0, totalDebug2 = 0, totalDebugDelay2 = 0;
	int totalDebug3 = 0, totalDebugDelay3 = 0, totalDebug4 = 0, totalDebugDelay4 = 0, totalTranfer = 0, totalTranferDelay = 0;
	int totalIssueCount = 0;
	for (int index = 0; index < data.size(); index++) {
	    totalProjectCount += data.get(index).getProjectCount();
	    totalProjectDelayCount += data.get(index).getProjectDelayCount();
	    taskTotalCount += data.get(index).getTaskTotalCount();
	    taskTotalDelayCount += data.get(index).getTaskTotalDelayCount();
	    totalPlan += data.get(index).getPlan();
	    totalDelayPlan += data.get(index).getPlanDelay();
	    totalTry += data.get(index).getFirstTry();
	    totalDelayTry += data.get(index).getFirstTryDelay();
	    totalDebug1 += data.get(index).getDebuging1();
	    totalDebugDelay1 += data.get(index).getDebugingDelay1();
	    totalDebug2 += data.get(index).getDebuging2();
	    totalDebugDelay2 += data.get(index).getDebugingDelay2();
	    totalDebug3 += data.get(index).getDebuging3();
	    totalDebugDelay3 += data.get(index).getDebugingDelay3();
	    totalDebug4 += data.get(index).getDebuging4();
	    totalDebugDelay4 += data.get(index).getDebugingDelay4();
	    totalTranfer += data.get(index).getMoldtransfer();
	    totalTranferDelay += data.get(index).getMoldtransferDelay();
	    totalIssueCount += data.get(index).getIssueCount();
	}
	totalData.setProjectCount(totalProjectCount);
	totalData.setProjectDelayCount(totalProjectDelayCount);
	totalData.setTaskTotalCount(taskTotalCount);
	totalData.setTaskTotalDelayCount(taskTotalDelayCount);
	totalData.setPlan(totalPlan);
	totalData.setPlanDelay(totalDelayPlan);
	totalData.setFirstTry(totalTry);
	totalData.setFirstTryDelay(totalDelayTry);
	totalData.setDebuging1(totalDebug1);
	totalData.setDebugingDelay1(totalDebugDelay1);
	totalData.setDebuging2(totalDebug2);
	totalData.setDebugingDelay2(totalDebugDelay2);
	totalData.setDebuging3(totalDebug3);
	totalData.setDebugingDelay3(totalDebugDelay3);
	totalData.setDebuging4(totalDebug4);
	totalData.setDebugingDelay4(totalDebugDelay4);
	totalData.setMoldtransfer(totalTranfer);
	totalData.setMoldtransferDelay(totalTranferDelay);
	totalData.setIssueCount(totalIssueCount);
	totalData.setMoldTypeCheck(dashBoardDTO.getMoldTypeCheck());
	model.addAttribute("totalData", totalData);
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<ProductProjectDTO> getMoldManufactureProjectList(DashBoardDTO dashBoardDTO, Model model) {

	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);
	if (dashBoardDTO.getOutsourcing().equals("-")) {
	    dashBoardDTO.setOutsourcing("EX");
	}
	List<ProductProjectDTO> moldProjectListData = dao.find("mold.moldManufactureProjectListSetting", dashBoardDTO); // 해당업체 금형 프로젝트 리스트
	// List<ProductProjectDTO> moldProjectDelayCountData = dao.find("mold.moldManufactureDelayProjectList", dashBoardDTO); // 업체별 금형프로젝트
	// // 수(지연)
	List<ProductProjectDTO> moldProjectTaskCountData = dao.find("mold.moldManufactureTaskProjectListSetting", dashBoardDTO); // 해당업체 금형
	                                                                                                                         // 프로젝트 리스트
	                                                                                                                         // 수 중에
	                                                                                                                         // TASK별 수
	List<ProductProjectDTO> moldProjectTaskDelayCountData = dao.find("mold.moldManufactureTaskDelayProjectListSetting", dashBoardDTO); // 해당업체
	                                                                                                                                   // 금형프로젝트
	                                                                                                                                   // 수
	                                                                                                                                   // 중에
	                                                                                                                                   // TASK별
	                                                                                                                                   // 수(지연)

	List<ProductProjectDTO> moldProjectTaskDebugCountData = dao.find("mold.moldManufactureTaskDebugProjectListSetting", dashBoardDTO); // 해당업체
	                                                                                                                                   // 금형프로젝수
	                                                                                                                                   // 중에
	                                                                                                                                   // TASK중
	                                                                                                                                   // 디버깅4차이상
	                                                                                                                                   // 수

	List<ProductProjectDTO> moldProjectTaskDebugDelayCountData = dao.find("mold.moldManufactureTaskDebugDelayProjectListSetting",
	        dashBoardDTO); // 해당업체 금형프로젝 수 중에 TASK중 디버깅4차이상 수(지연)

	List<ProductProjectDTO> moldProjectIssueCountData = dao.find("mold.moldManufactureIssueProjectListSetting", dashBoardDTO); // 업체별
	                                                                                                                           // 금형프로젝트
	                                                                                                                           // 수 중에
	                                                                                                                           // 이슈 수

	if (moldProjectListData.size() > 0 && moldProjectListData != null) {
	    for (int index = 0; index < moldProjectListData.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskCountData.size(); index1++) {
		    if (moldProjectListData.get(index).getPjtNO().equals(moldProjectTaskCountData.get(index1).getPjtNO())) {
			if ("외주금형제작".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setPlan(moldProjectTaskCountData.get(index1).getNum());
			} else if ("금형Try_[T0]".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setFirstTry(moldProjectTaskCountData.get(index1).getNum());
			} else if ("1 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebuging1(moldProjectTaskCountData.get(index1).getNum());
			} else if ("2 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebuging2(moldProjectTaskCountData.get(index1).getNum());
			} else if ("3 차 디버깅".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebuging3(moldProjectTaskCountData.get(index1).getNum());
			} else if ("금형이관단계".equals(moldProjectTaskCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setMoldtransfer(moldProjectTaskCountData.get(index1).getNum());
			}
		    }
		}
	    }
	}

	if (moldProjectListData.size() > 0 && moldProjectListData != null) {
	    for (int index = 0; index < moldProjectListData.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDelayCountData.size(); index1++) {
		    if (moldProjectListData.get(index).getPjtNO().equals(moldProjectTaskDelayCountData.get(index1).getPjtNO())) {
			if ("외주금형제작".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setPlanDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("금형Try_[T0]".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setFirstTryDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("1 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebugingDelay1(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("2 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebugingDelay2(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("3 차 디버깅".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setDebugingDelay3(moldProjectTaskDelayCountData.get(index1).getNum());
			} else if ("금형이관단계".equals(moldProjectTaskDelayCountData.get(index1).getTaskName())) {
			    moldProjectListData.get(index).setMoldtransferDelay(moldProjectTaskDelayCountData.get(index1).getNum());
			}
		    }
		}
	    }
	}

	if (moldProjectListData.size() > 0 && moldProjectListData != null) {
	    for (int index = 0; index < moldProjectListData.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDebugCountData.size(); index1++) {
		    if (moldProjectListData.get(index).getPjtNO().equals(moldProjectTaskDebugCountData.get(index1).getPjtNO())) {
			moldProjectListData.get(index).setDebuging4(moldProjectTaskDebugCountData.get(index1).getNum());
		    }
		}
	    }
	}

	if (moldProjectListData.size() > 0 && moldProjectListData != null) {
	    for (int index = 0; index < moldProjectListData.size(); index++) {
		for (int index1 = 0; index1 < moldProjectTaskDebugDelayCountData.size(); index1++) {
		    if (moldProjectListData.get(index).getPjtNO().equals(moldProjectTaskDebugDelayCountData.get(index1).getPjtNO())) {
			moldProjectListData.get(index).setDebugingDelay4(moldProjectTaskDebugDelayCountData.get(index1).getNum());
		    }
		}
	    }
	}

	if (moldProjectListData.size() > 0 && moldProjectListData != null) {
	    for (int index = 0; index < moldProjectListData.size(); index++) {
		for (int index1 = 0; index1 < moldProjectIssueCountData.size(); index1++) {
		    if (moldProjectListData.get(index).getPjtNO().equals(moldProjectIssueCountData.get(index1).getPjtNO())) {
			moldProjectListData.get(index).setIssueCount(moldProjectIssueCountData.get(index1).getNum());
		    }
		}
	    }
	}

	for (int index = 0; index < moldProjectListData.size(); index++) {
	    moldProjectListData.get(index).setTaskTotalCount(
		    moldProjectListData.get(index).getPlan() + moldProjectListData.get(index).getFirstTry()
		            + moldProjectListData.get(index).getDebuging1() + moldProjectListData.get(index).getDebuging2()
		            + moldProjectListData.get(index).getDebuging3() + moldProjectListData.get(index).getDebuging4()
		            + moldProjectListData.get(index).getMoldtransfer());
	    moldProjectListData.get(index).setTaskTotalDelayCount(
		    moldProjectListData.get(index).getPlanDelay() + moldProjectListData.get(index).getFirstTryDelay()
		            + moldProjectListData.get(index).getDebugingDelay1() + moldProjectListData.get(index).getDebugingDelay2()
		            + moldProjectListData.get(index).getDebugingDelay3() + moldProjectListData.get(index).getDebugingDelay4()
		            + moldProjectListData.get(index).getMoldtransferDelay());
	}

	return moldProjectListData;
    }

    @SuppressWarnings("unchecked")
    public PageControl moldTypeProjectList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	if (dashBoardDTO.getOutsourcing().equals("-")) {
	    dashBoardDTO.setOutsourcing("EX");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldTypeProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldTypeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl moldDebugTypeProjectList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	if (dashBoardDTO.getOutsourcing().equals("-")) {
	    dashBoardDTO.setOutsourcing("EX");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldDebugTypeProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldDebugTypeProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl moldIssueProjectList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldIssueProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldIssueProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl moldProjectTotalStepList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	String startDate1 = year + "" + month + "01";
	int sDate = Integer.valueOf(startDate1);

	Calendar cal = java.util.Calendar.getInstance();
	int currentYear = cal.get(cal.YEAR);
	int currentMonth = cal.get(cal.MONTH) + 1;
	int currentDay = cal.get(cal.DATE);
	String currentDate = currentYear + "" + currentMonth + "" + currentDay;
	int cDate = Integer.valueOf(currentDate);

	if (cDate >= sDate) {
	    dashBoardDTO.setCommand("case1");
	} else {
	    dashBoardDTO.setCommand("case2");
	}

	if (dashBoardDTO.getOutsourcing().equals("-")) {
	    dashBoardDTO.setOutsourcing("EX");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldProjectTotalStepListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldProjectTotalStepList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings({ "unchecked" })
    public List<DashBoardDTO> getProgramBasicInfo(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	List<DashBoardDTO> programBasicInfoData = dao.find("mold.programBasicInfoData", dashBoardDTO);
	StandardProgramService programService = new StandardProgramService();
	List<ProgramProjectDTO> list = programService.findProjectByProgram(programBasicInfoData.get(0).getOid());
	int projectCount = list.size();
	programBasicInfoData.get(0).setProjectCount(projectCount);
	model.addAttribute("programNo", programBasicInfoData.get(0).getProgramNo());
	return programBasicInfoData;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getCarNameScheduleInfo(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> carNameScheduleInfoData = dao.find("mold.carNameScheduleInfoData", dashBoardDTO);
	return carNameScheduleInfoData;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getCustomScheduleInfo(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> customScheduleInfoData = dao.find("mold.customScheduleInfoData", dashBoardDTO);
	return customScheduleInfoData;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getProductAndMakeInfo(DashBoardDTO dashBoardDTO, Model model) {
	// List<DashBoardDTO> data1 = dao.find("mold.normalAndsystemAssemblyInfoData", dashBoardDTO);
	// List<DashBoardDTO> data2 = dao.find("mold.normalSingleInfoData", dashBoardDTO);
	// List<DashBoardDTO> data3 = dao.find("mold.systemSingleInfoData", dashBoardDTO);
	// List<DashBoardDTO> data4 = dao.find("mold.normalGoodsInfoData", dashBoardDTO);
	// List<DashBoardDTO> data5 = dao.find("mold.systemGoodsInfoData", dashBoardDTO);

	List<DashBoardDTO> data1 = dao.find("mold.programCardAssembly", dashBoardDTO);
	List<DashBoardDTO> assemData = dao.find("mold.programCardAssemblyData", dashBoardDTO);
	List<DashBoardDTO> data2 = dao.find("mold.programCardProd", dashBoardDTO);
	List<DashBoardDTO> produceData = dao.find("mold.programCardProdData", dashBoardDTO);
	List<DashBoardDTO> data3 = dao.find("mold.programCardMake", dashBoardDTO);
	List<DashBoardDTO> makeData = dao.find("mold.programCardMakeData", dashBoardDTO);

	int assemblyIn = 0, assemblyOut = 0;
	int prodIn = 0, prodOut = 0;
	int makeIn = 0, makeOut = 0;
	int assemblyTotal = 0, prodTotal = 0, makeTotal = 0;

	for (int index = 0; index < data1.size(); index++) {
	    if (data1.get(index).getType() != null) {
		if ("사내".equals(data1.get(index).getType())) {
		    assemblyIn = data1.get(index).getNum();
		} else {
		    assemblyOut = data1.get(index).getNum();
		}
	    }
	}

	assemblyTotal = assemblyIn + assemblyOut;
	model.addAttribute("assemData", assemData);

	for (int index = 0; index < data2.size(); index++) {
	    if (data2.get(index).getProductionPlace() != null) {
		if ("사내".equals(data2.get(index).getProductionPlace())) {
		    prodIn = data2.get(index).getNum();
		} else {
		    prodOut = data2.get(index).getNum();
		}
	    }
	}

	prodTotal = prodIn + prodOut;
	model.addAttribute("produceData", produceData);
	for (int index = 0; index < data3.size(); index++) {
	    if (data3.get(index).getMaking() != null) {
		if ("사내".equals(data3.get(index).getMaking())) {
		    makeIn = data3.get(index).getNum();
		} else {
		    makeOut = data3.get(index).getNum();
		}
	    }
	}

	makeTotal = makeIn + makeOut;
	model.addAttribute("makeData", makeData);

	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO info = new DashBoardDTO();
	info.setType("조립처");
	info.setTypeByIn(assemblyIn);
	info.setTypeByOut(assemblyOut);
	info.setTypeByTotal(assemblyTotal);
	data.add(info);

	info = new DashBoardDTO();
	info.setType("생산처");
	info.setTypeByIn(prodIn);
	info.setTypeByOut(prodOut);
	info.setTypeByTotal(prodTotal);
	data.add(info);

	info = new DashBoardDTO();
	info.setType("제작처");
	info.setTypeByIn(makeIn);
	info.setTypeByOut(makeOut);
	info.setTypeByTotal(makeTotal);
	data.add(info);

	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getProgramByProjectListInfo(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> listInfo = dao.find("mold.programByProjectListInfoData", dashBoardDTO);
	List<DashBoardDTO> itemInfo = dao.find("mold.programByProjectItemInfoData", dashBoardDTO);
	List<DashBoardDTO> taskCompleteInfo = dao.find("mold.programByProjectTaskCompleteInfoData", dashBoardDTO);
	List<DashBoardDTO> taskProcessInfo = dao.find("mold.programByProjectTaskProcessInfoData", dashBoardDTO);
	List<DashBoardDTO> taskProcessDelayInfo = dao.find("mold.programByProjectTaskProcessDelayInfoData", dashBoardDTO);

	for (int index = 0; index < listInfo.size(); index++) {
	    for (int index1 = 0; index1 < itemInfo.size(); index1++) {
		if (listInfo.get(index).getPjtNo().equals(itemInfo.get(index1).getPjtNo())) {
		    if ("Mold".equals(itemInfo.get(index1).getItemType())) {
			listInfo.get(index).setMoldCount(itemInfo.get(index1).getNum());
		    } else if ("Press".equals(itemInfo.get(index1).getItemType())) {
			listInfo.get(index).setPressCount(itemInfo.get(index1).getNum());
		    } else if ("구매품".equals(itemInfo.get(index1).getItemType())) {
			listInfo.get(index).setGoodsCount(itemInfo.get(index1).getNum());
		    }
		}
	    }
	}

	for (int index = 0; index < listInfo.size(); index++) {
	    for (int index1 = 0; index1 < taskCompleteInfo.size(); index1++) {
		if (listInfo.get(index).getPjtNo().equals(taskCompleteInfo.get(index1).getPjtNo())) {
		    listInfo.get(index).setTaskCompleteCount(taskCompleteInfo.get(index1).getNum());
		}
	    }
	}

	for (int index = 0; index < listInfo.size(); index++) {
	    for (int index1 = 0; index1 < taskProcessInfo.size(); index1++) {
		if (listInfo.get(index).getPjtNo().equals(taskProcessInfo.get(index1).getPjtNo())) {
		    listInfo.get(index).setTaskProcessCount(taskProcessInfo.get(index1).getNum());
		}
	    }
	}

	for (int index = 0; index < listInfo.size(); index++) {
	    for (int index1 = 0; index1 < taskProcessDelayInfo.size(); index1++) {
		if (listInfo.get(index).getPjtNo().equals(taskProcessDelayInfo.get(index1).getPjtNo())) {
		    listInfo.get(index).setTaskProcessDelayCount(taskProcessDelayInfo.get(index1).getNum());
		}
	    }
	}

	for (int index = 0; index < listInfo.size(); index++) {
	    listInfo.get(index).setTaskTotalCount(listInfo.get(index).getTaskCompleteCount() + listInfo.get(index).getTaskProcessCount());
	}
	return listInfo;
    }

    public Map<String, Object> getChangePieChartData(DashBoardDTO dashBoardDTO) {

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	// 컬럼 | 형태로 바꿔주는 작업
	String str[] = dashBoardDTO.getvDevPattern();
	String tempStr = "";
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (str.length == 1) {
		    tempStr = str[index];
		} else {
		    if (str.length != index + 1) {
			tempStr += str[index] + "|";
		    } else {
			tempStr += str[index];
		    }
		}
	    }
	}
	dashBoardDTO.setDevPattern(tempStr);

	List<DashBoardDTO> list;

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeProjectListReviewCount", dashBoardDTO);
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeProjectListProductCount", dashBoardDTO);
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeProjectListMoldCount", dashBoardDTO);
	} else {
	    list = dao.find("mold.teamWorkTimeProjectListCount", dashBoardDTO);
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("pieRadius", "30");
	chartAttr.put("showValues", "1");
	chartAttr.put("animation", "0");
	chartAttr.put("showPercentValues", "0");
	chartAttr.put("pieYScale", "80");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("toolTipBorderThickness", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("enableRotation", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("interactiveLegend", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "ff0000");
	// dataAttr.add(dataAttr1);
	int state1 = 0, state2 = 0, state3 = 0, state4 = 0, state5 = 0, state6 = 0, state7 = 0, state8 = 0, state9 = 0;

	for (int index = 0; index < list.size(); index++) {
	    if ("계획".equals(list.get(index).getState())) {
		state1 = list.get(index).getNum();
	    } else if ("진행".equals(list.get(index).getState())) {
		state2 = list.get(index).getNum();
	    } else if ("완료".equals(list.get(index).getState())) {
		state3 = list.get(index).getNum();
	    } else if ("지연".equals(list.get(index).getState())) {
		state4 = list.get(index).getNum();
	    }

	}
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "계획");
	dataAttr1.put("value", state1);
	dataAttr1.put("link", "Javascript:linkPopUp('PLAN','" + dashBoardDTO.getPjtType() + "')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "진행");
	dataAttr1.put("value", state2);
	dataAttr1.put("link", "Javascript:linkPopUp('PROGRESS','" + dashBoardDTO.getPjtType() + "')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "완료");
	dataAttr1.put("value", state3);
	dataAttr1.put("link", "Javascript:linkPopUp('COMPLETED','" + dashBoardDTO.getPjtType() + "')");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "지연");
	dataAttr1.put("value", state4);
	dataAttr1.put("link", "Javascript:linkPopUp('DELAY','" + dashBoardDTO.getPjtType() + "')");
	dataAttr1.put("color", "f46060");
	dataAttr.add(dataAttr1);

	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    public Map<String, Object> getChangePieChartData1(DashBoardDTO dashBoardDTO) {
	// List<DashBoardDTO> list = dao.find("mold.teamWorkTimeTaskListCount", dashBoardDTO);
	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	// 컬럼 | 형태로 바꿔주는 작업
	String str[] = dashBoardDTO.getvDevPattern();
	String tempStr = "";
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (str.length == 1) {
		    tempStr = str[index];
		} else {
		    if (str.length != index + 1) {
			tempStr += str[index] + "|";
		    } else {
			tempStr += str[index];
		    }
		}
	    }
	}
	dashBoardDTO.setDevPattern(tempStr);
	
	List<DashBoardDTO> totalProjectReviewData;
/*	List<DashBoardDTO> taskPlan;
	List<DashBoardDTO> taskProcess;
	List<DashBoardDTO> taskDelay;
	List<DashBoardDTO> taskComplete;

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    taskPlan = dao.find("mold.teamWorkTimeTaskPlanReviewCount", dashBoardDTO);
	    taskProcess = dao.find("mold.teamWorkTimeTaskProcessReviewCount", dashBoardDTO);
	    taskDelay = dao.find("mold.teamWorkTimeTaskDelayReviewCount", dashBoardDTO);
	    taskComplete = dao.find("mold.teamWorkTimeTaskCompleteReviewCount", dashBoardDTO);
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    taskPlan = dao.find("mold.teamWorkTimeTaskPlanProductCount", dashBoardDTO);
	    taskProcess = dao.find("mold.teamWorkTimeTaskProcessProductCount", dashBoardDTO);
	    taskDelay = dao.find("mold.teamWorkTimeTaskDelayProductCount", dashBoardDTO);
	    taskComplete = dao.find("mold.teamWorkTimeTaskCompleteProductCount", dashBoardDTO);
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    taskPlan = dao.find("mold.teamWorkTimeTaskPlanMoldCount", dashBoardDTO);
	    taskProcess = dao.find("mold.teamWorkTimeTaskProcessMoldCount", dashBoardDTO);
	    taskDelay = dao.find("mold.teamWorkTimeTaskDelayMoldCount", dashBoardDTO);
	    taskComplete = dao.find("mold.teamWorkTimeTaskCompleteMoldCount", dashBoardDTO);
	} else {
	    taskPlan = dao.find("mold.teamWorkTimeTaskPlanCount", dashBoardDTO);
	    taskProcess = dao.find("mold.teamWorkTimeTaskProcessCount", dashBoardDTO);
	    taskDelay = dao.find("mold.teamWorkTimeTaskDelayCount", dashBoardDTO);
	    taskComplete = dao.find("mold.teamWorkTimeTaskCompleteCount", dashBoardDTO);
	}
	*/
	int taskPlanSize = 0;
	int taskProcessSize = 0;
	int taskDelaySize = 0;
	int taskCompleteSize = 0;
	
	dashBoardDTO.setStatus("");
	
	if ("review".equals(dashBoardDTO.getPjtType())) {
	    dashBoardDTO.setPjtTypeName("REVIEWPROJECT");
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,30000);
	} else if("product".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("PRODUCTPROJECT");
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,30000);
	} else if("mold".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("MOLDPROJECT");
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,30000);
	} else{
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskAllList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,30000);
	}
	
	for(DashBoardDTO dto : totalProjectReviewData){
	    String state = dto.getState();
	    
	    if("계획".equals(state)){
		taskPlanSize++;
	    }else if("진행".equals(state)){
		taskProcessSize++;
	    }else if("지연".contains(state) || "선행지연".contains(state) ){
		taskDelaySize++;
	    }else if("완료".equals(state)){
		taskCompleteSize++;
	    }
	}
	
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("pieRadius", "30");
	chartAttr.put("showValues", "1");
	chartAttr.put("animation", "0");
	chartAttr.put("showPercentValues", "0");
	chartAttr.put("pieYScale", "80");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("toolTipBorderThickness", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("enableRotation", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("interactiveLegend", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "ff0000");
	// dataAttr.add(dataAttr1);
	int state1 = 0, state2 = 0, state3 = 0, state4 = 0;

	// for (int index = 0; index < list.size(); index++) {
	// if ("계획".equals(list.get(index).getTaskState())) {
	// state1 = list.get(index).getNum();
	// } else if ("진행".equals(list.get(index).getTaskState())) {
	// state2 = list.get(index).getNum();
	// } else if ("완료".equals(list.get(index).getTaskState())) {
	// state3 = list.get(index).getNum();
	// } else if ("지연".equals(list.get(index).getTaskState())) {
	// state4 = list.get(index).getNum();
	// }
	//
	// }

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "계획");
	dataAttr1.put("value", taskPlanSize);
	dataAttr1.put("link", "Javascript:linkPopUp1('PLAN')");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "진행");
	dataAttr1.put("value", taskProcessSize);
	dataAttr1.put("link", "Javascript:linkPopUp1('PROGRESS')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "완료");
	dataAttr1.put("value", taskCompleteSize);
	dataAttr1.put("link", "Javascript:linkPopUp1('COMPLETED')");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "지연");
	dataAttr1.put("value", taskDelaySize);
	dataAttr1.put("link", "Javascript:linkPopUp1('DELAY')");
	dataAttr1.put("color", "f46060");
	dataAttr.add(dataAttr1);
	
	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getChangePieChartData2(DashBoardDTO dashBoardDTO) {

	// 컬럼 | 형태로 바꿔주는 작업
	String str[] = dashBoardDTO.getvDevPattern();
	String tempStr = "";
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (str.length == 1) {
		    tempStr = str[index];
		} else {
		    if (str.length != index + 1) {
			tempStr += str[index] + "|";
		    } else {
			tempStr += str[index];
		    }
		}
	    }
	}
	dashBoardDTO.setDevPattern(tempStr);

	List<DashBoardDTO> list;

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeIssueReviewListCount", dashBoardDTO);
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeIssueProductListCount", dashBoardDTO);
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeIssueMoldListCount", dashBoardDTO);
	} else {
	    list = dao.find("mold.teamWorkTimeIssueListCount", dashBoardDTO);
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("pieRadius", "30");
	chartAttr.put("showValues", "1");
	chartAttr.put("animation", "0");
	chartAttr.put("showPercentValues", "0");
	chartAttr.put("pieYScale", "80");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("toolTipBorderThickness", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("enableRotation", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("interactiveLegend", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "ff0000");
	// dataAttr.add(dataAttr1);
	int state1 = 0, state2 = 0;

	for (int index = 0; index < list.size(); index++) {
	    if ("1".equals(list.get(index).getType())) {
		state1 = list.get(index).getNum();
	    } else if ("0".equals(list.get(index).getType())) {
		state2 = list.get(index).getNum();
	    }
	}
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "완료");
	dataAttr1.put("value", state1);
	dataAttr1.put("link", "Javascript:linkPopup2('Completed')");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "미완료");
	dataAttr1.put("value", state2);
	dataAttr1.put("link", "Javascript:linkPopup2('InCompleted')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    public Map<String, Object> getChangePieChartData3(DashBoardDTO dashBoardDTO) {

	// 컬럼 | 형태로 바꿔주는 작업
	String str[] = dashBoardDTO.getvDevPattern();
	String tempStr = "";
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (str.length == 1) {
		    tempStr = str[index];
		} else {
		    if (str.length != index + 1) {
			tempStr += str[index] + "|";
		    } else {
			tempStr += str[index];
		    }
		}
	    }
	}
	dashBoardDTO.setDevPattern(tempStr);

	List<DashBoardDTO> list;

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeQulityProblemReviewCount", dashBoardDTO);
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeQulityProblemProductCount", dashBoardDTO);
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    list = dao.find("mold.teamWorkTimeQulityProblemMoldCount", dashBoardDTO);
	} else {
	    list = dao.find("mold.teamWorkTimeQulityProblemCount", dashBoardDTO);
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("pieRadius", "30");
	chartAttr.put("showValues", "1");
	chartAttr.put("animation", "0");
	chartAttr.put("showPercentValues", "0");
	chartAttr.put("pieYScale", "80");
	chartAttr.put("showLegend", "1");
	chartAttr.put("legendPosition", "RIGHT");
	chartAttr.put("showLabels", "0");
	chartAttr.put("enableSmartLabels", "0");
	chartAttr.put("toolTipBorderThickness", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("legendBorderAlpha", "0");
	chartAttr.put("use3DLighting", "0");
	chartAttr.put("showShadow", "0");
	chartAttr.put("enableRotation", "0");
	chartAttr.put("legendShadow", "0");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");
	chartAttr.put("interactiveLegend", "0");

	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;
	// dataAttr1.put("value", "35");
	// dataAttr1.put("color", "ff0000");
	// dataAttr.add(dataAttr1);
	int state1 = 0, state2 = 0, state3 = 0;

	for (int index = 0; index < list.size(); index++) {
	    if ("완료".equals(list.get(index).getDqmStateName())) {
		state1 = list.get(index).getNum();
	    } else if ("진행".equals(list.get(index).getDqmStateName())) {
		state2 = list.get(index).getNum();
	    } else if ("지연".equals(list.get(index).getDqmStateName())) {
		state3 = list.get(index).getNum();
	    }

	}
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "완료");
	dataAttr1.put("value", state1);
	dataAttr1.put("link", "Javascript:linkPopup3('Completed')");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "진행");
	dataAttr1.put("value", state2);
	dataAttr1.put("link", "Javascript:linkPopup3('Progress')");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("label", "지연");
	dataAttr1.put("value", state3);
	dataAttr1.put("link", "Javascript:linkPopup3('Delay')");
	dataAttr1.put("color", "f46060");
	dataAttr.add(dataAttr1);

	data.put("chart", chartAttr);
	data.put("data", dataAttr);

	return data;
    }

    @SuppressWarnings("unchecked")
    public PageControl teamWorkTimeReportProjectList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("ExecEndDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("ExecEndDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate1 = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate1);

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListReviewTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectReviewList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProductTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProductList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListMoldTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectMoldList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl getCarDivisionProjectReport(DashBoardDTO dashBoardDTO, Model md) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("sopDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("sop");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("sopDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("sop");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate1 = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate1);

	int totalCount = dao.getTotalRecordCount("mold.carDivisionProjectReportDataCount", dashBoardDTO);
	List<ProductProjectDTO> data = dao.findPaging("mold.carDivisionProjectReportData", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	md.addAttribute("carTypeCount", data.size());
	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yy.MM.dd", Locale.KOREA);
	Date currentTime = new Date();
	String mTime = mSimpleDateFormat.format(currentTime).replace(".", "");
	int today = Integer.parseInt(mTime);

	int model = 0, proto = 0, p1 = 0, p2 = 0, m = 0, sop = 0;
	List<Integer> currentState = new ArrayList<Integer>();
	List<Integer> nextState = new ArrayList<Integer>();

	String currentDate = "";
	String nextDate = "";
	for (int index = 0; index < data.size(); index++) {
	    if (data.get(index).getModel() != null) {
		model = Integer.parseInt(data.get(index).getModel().replace("/", ""));
	    }
	    if (today > model) {
		currentState.add(model);
	    } else {
		nextState.add(model);
	    }
	    if (data.get(index).getProto() != null) {
		proto = Integer.parseInt(data.get(index).getProto().replace("/", ""));
	    }
	    if (today > proto) {
		currentState.add(proto);
	    } else {
		nextState.add(proto);
	    }
	    if (data.get(index).getP1() != null) {
		p1 = Integer.parseInt(data.get(index).getP1().replace("/", ""));
	    }
	    if (today > p1) {
		currentState.add(p1);
	    } else {
		nextState.add(p1);
	    }
	    if (data.get(index).getP2() != null) {
		p2 = Integer.parseInt(data.get(index).getP2().replace("/", ""));
	    }
	    if (today > p2) {
		currentState.add(p2);
	    } else {
		nextState.add(p2);
	    }
	    if (data.get(index).getM() != null) {
		m = Integer.parseInt(data.get(index).getM().replace("/", ""));
	    }
	    if (today > m) {
		currentState.add(m);
	    } else {
		nextState.add(m);
	    }
	    if (data.get(index).getSop() != null) {
		sop = Integer.parseInt(data.get(index).getSop().replace("/", ""));
	    }
	    if (today > sop) {
		currentState.add(sop);
	    } else {
		nextState.add(sop);
	    }
	    int max = 0;
	    if (currentState.size() > 0 && currentState != null) {
		max = Collections.max(currentState);
		String maxDate = String.valueOf(max);
		if (!maxDate.equals("0")) {
		    if (maxDate.length() < 6) {
			maxDate = "0" + maxDate;
		    }
		    currentDate = maxDate.substring(0, 2) + "-" + maxDate.substring(2, 4) + "-" + maxDate.substring(4, 6);
		    data.get(index).setCurrentDate("20" + currentDate);
		    data.get(index).setToday(today);
		    data.get(index).setSopDay(Integer.parseInt(maxDate));
		} else {
		    data.get(index).setCurrentDate("-");
		    data.get(index).setSopDay(0);
		}

		if (max == model) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("MODEL");
		    }
		} else if (max == proto) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("PROTO");
		    }
		} else if (max == p1) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("P1");
		    }
		} else if (max == p2) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("P2");
		    }
		} else if (max == m) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("M");
		    }
		} else if (max == sop) {
		    if (max == 0) {
			data.get(index).setCurrentStep("");
		    } else {
			data.get(index).setCurrentStep("SOP");
		    }
		}
	    } else {
		data.get(index).setCurrentDate("-");
		data.get(index).setSopDay(0);
	    }
	    int min = 0;
	    if (nextState.size() > 0 && nextState != null) {
		min = Collections.min(nextState);
		String minDate = String.valueOf(min);
		if (!minDate.equals("0")) {
		    if (minDate.length() < 6) {
			minDate = "0" + minDate;
		    }
		    nextDate = minDate.substring(0, 2) + "-" + minDate.substring(2, 4) + "-" + minDate.substring(4, 6);
		    data.get(index).setNextDate("20" + nextDate);
		} else {
		    data.get(index).setNextDate("-");
		}
		if (min == model) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("MODEL");
		    }
		} else if (min == proto) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("PROTO");
		    }
		} else if (min == p1) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("P1");
		    }
		} else if (min == p2) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("P2");
		    }
		} else if (min == m) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("M");
		    }
		} else if (min == sop) {
		    if (min == 0) {
			data.get(index).setNextStep("");
		    } else {
			data.get(index).setNextStep("SOP");
		    }
		}
	    } else {
		data.get(index).setNextDate("-");
	    }

	    data.get(index).setSopDate("20" + data.get(index).getSop());

	    currentState.clear();
	    nextState.clear();
	    model = 0;
	    proto = 0;
	    p1 = 0;
	    p2 = 0;
	    m = 0;
	    sop = 0;
	    currentDate = "";
	    nextDate = "";
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, data, dashBoardDTO.getFormPage(), dashBoardDTO.getFormPage(),
	        totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl productCompleteProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productCompleteProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productCompleteProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl productProcessProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productProcessProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productProcessProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl productDelayProjecttList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productDelayProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productDelayProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public Map<String, Object> electornStackColum_(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> yearData = dao.find("mold.eletronLastAndThisYear", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "이월-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "신규-전자");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	for (int length = 0; length < yearData.size(); length++) {
	    if ("lastYear".equals(yearData.get(length).getType()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','S');");
		dataAttr.add(dataAttr1);
	    } else if ("thisYear".equals(yearData.get(length).getType()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','S');");
		dataAttr.add(dataAttr1);
	    }
	}

	datasetAttr1.put("seriesname", "수주");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FF0000");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("lastYear".equals(yearData.get(length).getType()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','J');");
		dataAttr.add(dataAttr1);
	    } else if ("thisYear".equals(yearData.get(length).getType()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','J');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "전략");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("lastYear".equals(yearData.get(length).getType()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','Y');");
		dataAttr.add(dataAttr1);
	    } else if ("thisYear".equals(yearData.get(length).getType()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','Y');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "연구");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("lastYear".equals(yearData.get(length).getType()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','C');");
		dataAttr.add(dataAttr1);
	    } else if ("thisYear".equals(yearData.get(length).getType()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','C');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "추가");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> electornStackColum1_(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> yearData = dao.find("mold.eletronCompletedProgressData", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "완료-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "진행-전자");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','S');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "수주개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','S');");
		dataAttr.add(dataAttr1);
	    }
	}

	datasetAttr1.put("seriesname", "수주");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FF0000");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','J');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "전략개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','J');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "전략");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','Y');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "연구개발".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','Y');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "연구");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	for (int length = 0; length < yearData.size(); length++) {
	    if ("완료".equals(yearData.get(length).getStateState()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('C','C');");
		dataAttr.add(dataAttr1);
	    } else if ("진행".equals(yearData.get(length).getStateState()) && "추가금형".equals(yearData.get(length).getDevType())) {
		dataAttr1 = new HashMap<String, Object>();
		dataAttr1.put("value", yearData.get(length).getNum());
		dataAttr1.put("link", "JavaScript:linkPopUp1('P','C');");
		dataAttr.add(dataAttr1);
	    }
	}
	datasetAttr1.put("seriesname", "추가");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> getChangeMultiColumOverallStatusChartData_(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> completeIssueData = dao.find("mold.completeIssueData", dashBoardDTO);
	List<DashBoardDTO> notCompleteIssueData = dao.find("mold.notCompleteIssueData", dashBoardDTO);

	int moldCount = 0, scheduleCount = 0, costCount = 0, etcCount = 0, productCount = 0, qualityCount = 0, customerCount = 0;
	int moldCount_ = 0, scheduleCount_ = 0, costCount_ = 0, etcCount_ = 0, productCount_ = 0, qualityCount_ = 0, customerCount_ = 0;

	if (completeIssueData.size() > 0 && completeIssueData != null) {
	    for (int index = 0; index < completeIssueData.size(); index++) {
		if ("MOLD".equals(completeIssueData.get(index).getType())) {
		    moldCount = completeIssueData.get(index).getNum();
		} else if ("SCHEDULE".equals(completeIssueData.get(index).getType())) {
		    scheduleCount = completeIssueData.get(index).getNum();
		} else if ("COST".equals(completeIssueData.get(index).getType())) {
		    costCount = completeIssueData.get(index).getNum();
		} else if ("ETC".equals(completeIssueData.get(index).getType())) {
		    etcCount = completeIssueData.get(index).getNum();
		} else if ("PRODUCT".equals(completeIssueData.get(index).getType())) {
		    productCount = completeIssueData.get(index).getNum();
		} else if ("QUALITY".equals(completeIssueData.get(index).getType())) {
		    qualityCount = completeIssueData.get(index).getNum();
		} else if ("CUSTOMER ".equals(completeIssueData.get(index).getType())) {
		    customerCount = completeIssueData.get(index).getNum();
		}
	    }
	}

	if (notCompleteIssueData.size() > 0 && notCompleteIssueData != null) {
	    for (int index = 0; index < notCompleteIssueData.size(); index++) {
		if ("MOLD".equals(notCompleteIssueData.get(index).getType())) {
		    moldCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("SCHEDULE".equals(notCompleteIssueData.get(index).getType())) {
		    scheduleCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("COST".equals(notCompleteIssueData.get(index).getType())) {
		    costCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("ETC".equals(notCompleteIssueData.get(index).getType())) {
		    etcCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("PRODUCT".equals(notCompleteIssueData.get(index).getType())) {
		    productCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("QUALITY".equals(notCompleteIssueData.get(index).getType())) {
		    qualityCount_ = notCompleteIssueData.get(index).getNum();
		} else if ("CUSTOMER ".equals(notCompleteIssueData.get(index).getType())) {
		    customerCount_ = notCompleteIssueData.get(index).getNum();
		}
	    }
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "제품-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "금형-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "고객-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "품질-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "비용-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "일정-전자");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "기타-전자");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	datasetAttr1.put("seriesName", "미완료");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", productCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','PRODUCT');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','MOLD');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", customerCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','CUSTOMER');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", qualityCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','QUALITY');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", costCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','COST');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", scheduleCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','SCHEDULE');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", etcCount_);
	setAttr1.put("link", "JavaScript:linkPopUp2('N','ETC');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	datasetAttr1.put("seriesName", "완료");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", productCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','PRODUCT');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", moldCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','MOLD');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", customerCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','CUSTOMER');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", qualityCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','QUALITY');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", costCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','COST');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", scheduleCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','SCHEDULE');");
	setAttr.add(setAttr1);

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", etcCount);
	setAttr1.put("link", "JavaScript:linkPopUp2('Y','ETC');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getChangeStackColumOverallStatusChartData2_(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String endDate = year + "-" + month + "-" + day;
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> totalData = dao.find("mold.getCompleteSchedule", dashBoardDTO);
	List<DashBoardDTO> delayData = dao.find("mold.getDelaySchedule", dashBoardDTO);

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("xAxisName", month + "월");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");
	// chartAttr.put("yAxisMaxValue", "450");
	// chartAttr.put("yAxisMinValue", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	int su = 0, su_ = 0, totalsu = 0;
	int jen = 0, jen_ = 0, totaljen = 0;
	int yen = 0, yen_ = 0, totalyen = 0;
	int cho = 0, cho_ = 0, totalcho = 0;

	for (int length = 0; length < totalData.size(); length++) {
	    if ("수주개발".equals(totalData.get(length).getType())) {
		su = totalData.get(length).getNum();
	    } else if ("전략개발".equals(totalData.get(length).getType())) {
		jen = totalData.get(length).getNum();

	    } else if ("연구개발".equals(totalData.get(length).getType())) {
		yen = totalData.get(length).getNum();
	    } else if ("추가금형".equals(totalData.get(length).getType())) {
		cho = totalData.get(length).getNum();
	    }
	}

	for (int length = 0; length < delayData.size(); length++) {
	    if ("수주개발".equals(delayData.get(length).getType())) {
		su_ = delayData.get(length).getNum();
	    } else if ("전략개발".equals(delayData.get(length).getType())) {
		jen_ = delayData.get(length).getNum();

	    } else if ("연구개발".equals(delayData.get(length).getType())) {
		yen_ = delayData.get(length).getNum();
	    } else if ("추가금형".equals(delayData.get(length).getType())) {
		cho_ = delayData.get(length).getNum();
	    }
	}

	totalsu = su - su_;
	totaljen = jen - jen_;
	totalyen = yen - yen_;
	totalcho = cho - cho_;

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalsu);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totaljen);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalcho);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", totalyen);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	datasetAttr1.put("seriesname", "전체");
	datasetAttr1.put("data", dataAttr);
	datasetAttr1.put("color", "FFFF00");
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	dataAttr = new ArrayList<Map<String, Object>>();

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", su_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", jen_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", cho_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", yen_);
	dataAttr1.put("link", "JavaScript:linkPopUp4('','');");
	dataAttr.add(dataAttr1);

	datasetAttr1.put("seriesname", "지연");
	datasetAttr1.put("data", dataAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public Map<String, Object> getChangeMultiColumOverallStatusChartData1_(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	List<DashBoardDTO> afterMonthData = null;
	List<DashBoardDTO> twoAfterMonthData = null;

	for (int index = 0; index < 2; index++) {
	    month++;
	    switch (month) {
	    case 1:
	    case 3:
	    case 5:
	    case 7:
	    case 8:
	    case 10:
	    case 12:
		day = 31; // 31일까지 있는 달
		break;
	    case 4:
	    case 6:
	    case 9:
	    case 11:
		day = 30; // 30일까지 있는 달
		break;
	    case 2: // 평년 2월은 28일, 윤년은 29일
		day = yoonDal.equals("28") ? 28 : 29;
		break;
	    default: // 1~12월의 유효성검사
		Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    }
	    String startDate = "";
	    String endDate = "";

	    if (index == 0) {
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		afterMonthData = dao.find("mold.getCompleteScheduleAfterMonth", dashBoardDTO);
	    } else {
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		twoAfterMonthData = dao.find("mold.getCompleteScheduleTwoAfterMonth", dashBoardDTO);
	    }
	}

	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();
	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", (month - 1) + "월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", month + "월");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;

	int su = 0, su_ = 0;
	int jen = 0, jen_ = 0;
	int yen = 0, yen_ = 0;
	int cho = 0, cho_ = 0;

	for (int length = 0; length < afterMonthData.size(); length++) {
	    if ("수주개발".equals(afterMonthData.get(length).getType())) {
		su = afterMonthData.get(length).getNum();
	    } else if ("전략개발".equals(afterMonthData.get(length).getType())) {
		jen = afterMonthData.get(length).getNum();
	    } else if ("추가금형".equals(afterMonthData.get(length).getType())) {
		cho = afterMonthData.get(length).getNum();
	    } else if ("연구개발".equals(afterMonthData.get(length).getType())) {
		yen = afterMonthData.get(length).getNum();
	    }
	}

	for (int length = 0; length < twoAfterMonthData.size(); length++) {
	    if ("수주개발".equals(twoAfterMonthData.get(length).getType())) {
		su_ = twoAfterMonthData.get(length).getNum();
	    } else if ("전략개발".equals(twoAfterMonthData.get(length).getType())) {
		jen_ = twoAfterMonthData.get(length).getNum();
	    } else if ("추가금형".equals(twoAfterMonthData.get(length).getType())) {
		cho_ = twoAfterMonthData.get(length).getNum();
	    } else if ("연구개발".equals(twoAfterMonthData.get(length).getType())) {
		yen_ = twoAfterMonthData.get(length).getNum();
	    }
	}

	datasetAttr1.put("seriesName", "수주");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", su);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','S','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", su_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','S','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	datasetAttr1.put("seriesName", "전략");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", jen);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','J','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", jen_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','J','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	datasetAttr1.put("seriesName", "추가");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", cho);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','C','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", cho_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','C','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();

	datasetAttr1.put("seriesName", "연구");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", yen);
	setAttr1.put("link", "JavaScript:linkPopUp3('ONE','Y','" + (month - 1) + "');");
	setAttr.add(setAttr1);
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("value", yen_);
	setAttr1.put("link", "JavaScript:linkPopUp3('TWO','Y','" + (month) + "');");
	setAttr.add(setAttr1);

	datasetAttr1.put("set", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);

	Kogger.debug(getClass(), data);
	return data;
    }

    public PageControl moldCompleteProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldCompleteProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldCompleteProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldProcessProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldProcessProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldProcessProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldDelayProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldDelayProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldDelayProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl issueProjectList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.issueProjectListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.issueProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public Map<String, Object> msStackedColumn3DSetting(DashBoardDTO dashBoardDTO) {

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Q1");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Q2");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Q3");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "Q4");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "목표");

	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Processed Food");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "30");
	dataAttr1.put("link", "Javascript:test()");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "26");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "33");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Un-Processed Food");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "21");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "28");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "39");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "41");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	// // datasetAttr1 = new HashMap<String, Object>();
	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Un-Processed Food");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "21");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "28");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "39");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "41");
	// dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);

	// datasetAttr1 = new HashMap<String, Object>();
	datasetAttr1 = new HashMap<String, Object>();
	setAttr = new ArrayList<Map<String, Object>>();
	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Electronics");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "27");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "25");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "28");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "26");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Apparels");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "17");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "15");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "18");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", "16");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	// // datasetAttr1 = new HashMap<String, Object>();
	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Apparels");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "17");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "15");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "18");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", "16");
	// dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);
	Kogger.debug(getClass(), datasource);
	return data;
    }

    @SuppressWarnings("unchecked")
    public PageControl teamWorkTimeReportTaskList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (!"product".equals(dashBoardDTO.getPjtType()) && dashBoardDTO.getSortName().indexOf("period") != -1) {
		dashBoardDTO.setSortName("-displayPlanEndDate");
	    }
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("ExecEndDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("ExecEndDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	/*
	 * String startDate = dashBoardDTO.getStartDate().substring(0, 4) + "-" + dashBoardDTO.getStartDate().subSequence(4, 6) + "-" +
	 * dashBoardDTO.getStartDate().substring(6, 8); dashBoardDTO.setStartDate(startDate); String endDate =
	 * dashBoardDTO.getEndDate().substring(0, 4) + "-" + dashBoardDTO.getEndDate().subSequence(4, 6) + "-" +
	 * dashBoardDTO.getEndDate().substring(6, 8); dashBoardDTO.setEndDate(endDate);
	 */

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}
	
	
	if ("review".equals(dashBoardDTO.getPjtType())) {
	    dashBoardDTO.setPjtTypeName("REVIEWPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListTotal_new", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if("product".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("PRODUCTPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListTotal_new", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if("mold".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("MOLDPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListTotal_new", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else{
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskAllListTotal_new", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskAllList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

/*	if ("review".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListReviewTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskReviewList", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProductTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProductList", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListMoldTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskMoldList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}*/
	
	for (DashBoardDTO dto : totalProjectReviewData) {
		String rev = "";
		if(StringUtils.isEmpty(dto.getType())){
		    continue;
		}
		if ("GATE".equals(dto.getType())) {
		    try {
			int gate_rev = ProjectTaskCompHelper.service.getMaxGateDegree(dto.getmOid());
			rev = Integer.toString(gate_rev);
			Hashtable haTotalResult = ProjectTaskCompHelper.service.getGateAssessResultInfoList(dto.getmOid(), false, gate_rev);
			String resultTotalStr = StringUtil.checkNull((String) haTotalResult.get("resultTotalStr"));
			dto.setTaskResultAndon(resultTotalStr);
			if(StringUtils.isNotEmpty(rev)){
			    rev = rev+"차";
			}
			dto.setTaskRev(rev);
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}

		if ("DR".equals(dto.getType())) {

		    String drApprovalResult = "";
		    try {
			List<AssessTaskResultDTO> taskAssessResultList = ProjectTaskCompHelper.service.getOnlyTaskAssessResultByTask(dto.getmOid());
			if (taskAssessResultList.size() > 0) {
			    for (int i = 0; i < taskAssessResultList.size(); i++) {
				AssessTaskResultDTO gDto = taskAssessResultList.get(i);
				if (StringUtils.isNotEmpty(gDto.getAssResult())) {
				    rev = gDto.getRev()+"차";
				    drApprovalResult = gDto.getAssResult();
				}
			    }
			    
			    if("NONE".equals(drApprovalResult)){
				
			    }else if("GOOD".equals(drApprovalResult)){
				dto.setTaskResultAndon("G");
			    }else if("조건부승인".equals(drApprovalResult)){
				dto.setTaskResultAndon("Y");
			    }else if("NG".equals(drApprovalResult)){
				dto.setTaskResultAndon("R");
			    }
			    dto.setTaskRev(rev);
			}
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		
		if ("신뢰성평가".equals(dto.getType())) {
		    try {
	                List<TrustTaskResultDTO> taskLevelResult = ProjectTaskCompHelper.service.getTaskTrustLevelList(dto.getmOid());
	                String result = "";
	                if (taskLevelResult.size() > 0) {
			    for (int i = 0; i < taskLevelResult.size(); i++) {
				TrustTaskResultDTO gDto = taskLevelResult.get(i);
				rev = gDto.getRev()+"차";
				result = gDto.getAssResult();

			    }
			    
			    if("OK".equals(result)){
				result = "G";
			    }
			    if("NG".equals(result)){
				result = "R";
			    }
			    
			    dto.setTaskResultAndon(result);
			    dto.setTaskRev(rev);
			}
	                
                    } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                    }
		}
	    }

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl teamWorkTimeReportIssueList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueReviewListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueReviewList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueProductListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueProductList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueMoldListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueMoldList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	if (totalProjectReviewData.size() > 0) {
	    for (int index = 0; index < totalProjectReviewData.size(); index++) {
		String oid = totalProjectReviewData.get(index).getmOid();
		Object obj = CommonUtil.getObject(oid);
		ProjectIssue issue = null;
		ProjectIssueData data = null;
		issue = (ProjectIssue) obj;
		data = new ProjectIssueData(issue);
		totalProjectReviewData.get(index).setAttchCount(data.questionAttacheVec.size());
	    }
	}
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> msStackedColumn2DSetting1(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();

	String start = String.valueOf(year) + "/01/01";
	String end = String.valueOf(year) + "/12/31";
	Date to = null;
	Date from = null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	to = (Date) format.parse(start);
	dashBoardDTO.setPlanStartDate(to);
	from = (Date) format.parse(end);
	dashBoardDTO.setPlanEndDate(from);

	dashBoardDTO.setStartDate(start);
	dashBoardDTO.setEndDate(end);

	String division = dashBoardDTO.getDivision();

	if (division.equals("car")) {
	    dashBoardDTO.setPjtType("3");
	} else if (division.equals("elect")) {
	    dashBoardDTO.setPjtType("4");
	} else {
	    dashBoardDTO.setPjtType("");
	}

	List<DashBoardDTO> yearData = dao.find("mold.lastAndthisYear", dashBoardDTO);
	List<DashBoardDTO> yearData1 = dao.find("mold.completedProgressData", dashBoardDTO);

	int thisVal = 0, thisVal1 = 0, lastVal = 0, lastVal1 = 0;

	for (int index = 0; index < yearData.size(); index++) {
	    if ("thisYear".equals(yearData.get(index).getType()) && "Press".equals(yearData.get(index).getMoldType())) {
		thisVal = yearData.get(index).getNum();
	    } else if ("lastYear".equals(yearData.get(index).getType()) && "Press".equals(yearData.get(index).getMoldType())) {
		lastVal = yearData.get(index).getNum();
	    } else if ("thisYear".equals(yearData.get(index).getType()) && "Mold".equals(yearData.get(index).getMoldType())) {
		thisVal1 = yearData.get(index).getNum();
	    } else if ("lastYear".equals(yearData.get(index).getType()) && "Mold".equals(yearData.get(index).getMoldType())) {
		lastVal1 = yearData.get(index).getNum();
	    }
	}

	int completeMoldCount = 0, processMoldCount = 0, completePressCount = 0, processPressCount = 0;

	for (int length = 0; length < yearData1.size(); length++) {
	    if ("COMPLETED".equals(yearData1.get(length).getState()) && "Mold".equals(yearData1.get(length).getMoldType())) {
		completeMoldCount = yearData1.get(length).getNum();
	    } else if ("PROGRESS".equals(yearData1.get(length).getState()) && "Mold".equals(yearData1.get(length).getMoldType())) {
		processMoldCount = yearData1.get(length).getNum();
	    } else if ("COMPLETED".equals(yearData1.get(length).getState()) && "Press".equals(yearData1.get(length).getMoldType())) {
		completePressCount = yearData1.get(length).getNum();
	    } else if ("PROGRESS".equals(yearData1.get(length).getState()) && "Press".equals(yearData1.get(length).getMoldType())) {
		processPressCount = yearData1.get(length).getNum();
	    }
	}

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("showValues", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("formatNumberScale", "0");
	chartAttr.put("baseFontSize", "12");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "이월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "신규");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "완료");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "진행");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "목표");

	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("seriesname", "Press");
	setAttr1.put("color", "abcce0");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", lastVal);
	dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','Press');");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr.add(setAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", thisVal);
	dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','Press');");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completePressCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('C','P');");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processPressCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('P','P');");
	dataAttr1.put("color", "abcce0");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	setAttr1.put("seriesname", "Mold");
	setAttr1.put("color", "63a2c8");
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", lastVal1);
	dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','Mold');");
	dataAttr1.put("color", "63a2c8");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", thisVal1);
	dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','Mold');");
	dataAttr1.put("color", "63a2c8");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeMoldCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('C','M');");
	dataAttr1.put("color", "63a2c8");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processMoldCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('P','M');");
	dataAttr1.put("color", "63a2c8");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Press");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", completePressCount);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('C','P');");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", processPressCount);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('P','P');");
	// dataAttr.add(dataAttr1);
	// // dataAttr1 = new HashMap<String, Object>();
	// // dataAttr1.put("value", "28");
	// // dataAttr.add(dataAttr1);
	// // dataAttr1 = new HashMap<String, Object>();
	// // dataAttr1.put("value", "26");
	// // dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "Mold");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", completeMoldCount);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('C','M');");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", processMoldCount);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('P','M');");
	// dataAttr.add(dataAttr1);
	// // dataAttr1 = new HashMap<String, Object>();
	// // dataAttr1.put("value", "18");
	// // dataAttr.add(dataAttr1);
	// // dataAttr1 = new HashMap<String, Object>();
	// // dataAttr1.put("value", "16");
	// // dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);
	Kogger.debug(getClass(), datasource);
	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> msStackedColumn2DSetting(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year - 1) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> yearData = dao.find("mold.eletronLastAndThisYear", dashBoardDTO);

	List<DashBoardDTO> yearData1 = dao.find("mold.eletronCompletedProgressData", dashBoardDTO);

	int lastCount = 0, thisCount = 0;

	for (int index = 0; index < yearData.size(); index++) {
	    if ("lastYear".equals(yearData.get(index).getType())) {
		lastCount = yearData.get(index).getNum();
	    } else if ("thisYear".equals(yearData.get(index).getType())) {
		thisCount = yearData.get(index).getNum();
	    }
	}

	int completeCount = 0, processCount = 0;

	for (int index = 0; index < yearData1.size(); index++) {
	    if ("완료".equals(yearData1.get(index).getStateState())) {
		completeCount = yearData1.get(index).getNum();
	    } else if ("진행".equals(yearData1.get(index).getStateState())) {
		processCount = yearData1.get(index).getNum();
	    }
	}

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	// chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	// chartAttr.put("borderAlpha", "20");
	// chartAttr.put("showCanvasBorder", "0");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("plotSpacePercent", "65");
	chartAttr.put("showValues", "0");
	chartAttr.put("showLegend", "0");
	chartAttr.put("formatNumberScale", "0");
	// chartAttr.put("showSum", "0");
	// chartAttr.put("valueFontColor", "#ffffff");
	// chartAttr.put("showXAxisLine", "1");
	// chartAttr.put("xAxisLineColor", "#999999");
	// chartAttr.put("divlineColor", "#999999");
	// chartAttr.put("showHoverEffect", "1");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "이월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "신규");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "완료");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", "진행");
	categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "목표");

	setAttr1 = new HashMap<String, Object>();
	dataAttr1 = new HashMap<String, Object>();

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", lastCount);
	dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','C','" + year + "');");
	dataAttr1.put("color", "b5b5b5");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", thisCount);
	dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','C','" + year + "');");
	dataAttr1.put("color", "faaf20");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", completeCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('C','C','" + year + "');");
	dataAttr1.put("color", "89c211");
	dataAttr.add(dataAttr1);
	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", processCount);
	dataAttr1.put("link", "JavaScript:linkPopUp1('P','C','" + year + "');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);
	setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "추가");
	setAttr.add(setAttr1);

	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", lastY);
	// dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','Y');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", thisY);
	// dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','Y');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", completeY);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('C','Y');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", processY);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('P','Y');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "연구");
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", lastJ);
	// dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','J');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", thisJ);
	// dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','J');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", completeJ);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('C','J');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", processJ);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('P','J');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "전략");
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	//
	// dataAttr1.put("value", lastS);
	// dataAttr1.put("link", "JavaScript:linkPopUp('lastYear','S');");
	// dataAttr1.put("color", "5babd4");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", thisS);
	// dataAttr1.put("link", "JavaScript:linkPopUp('thisYear','S');");
	// dataAttr1.put("color", "5babd4");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", completeS);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('C','S');");
	// dataAttr1.put("color", "5babd4");
	// dataAttr.add(dataAttr1);
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", processS);
	// dataAttr1.put("link", "JavaScript:linkPopUp1('P','S');");
	// dataAttr1.put("color", "5babd4");
	// dataAttr.add(dataAttr1);
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "수주");
	// setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);
	Kogger.debug(getClass(), datasource);
	return data;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> msStackedColumn2DSetting2(DashBoardDTO dashBoardDTO) {
	int year = 0;
	int month = 0;
	int day = 0;
	int currentMonth = 0;
	int oneAfterMonth = 0;
	int twoAfterMonth = 0;
	int currentYear = 0;
	int oneAfterYear = 0;
	int twoAfterYear = 0;
	// int year = dashBoardDTO.getYear();
	// int month = dashBoardDTO.getMonth();
	// int day = 0;
	// String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	List<DashBoardDTO> totalData = null;
	List<DashBoardDTO> delayData = null;
	List<DashBoardDTO> afterMonthData = null;
	List<DashBoardDTO> twoAfterMonthData = null;

	Calendar calendar = Calendar.getInstance();

	for (int index = 0; index < 3; index++) {

	    year = calendar.get(Calendar.YEAR);
	    month = calendar.get(Calendar.MONTH) + 1;
	    day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	    // switch (month) {
	    // case 1:
	    // case 3:
	    // case 5:
	    // case 7:
	    // case 8:
	    // case 10:
	    // case 12:
	    // day = 31; // 31일까지 있는 달
	    // break;
	    // case 4:
	    // case 6:
	    // case 9:
	    // case 11:
	    // day = 30; // 30일까지 있는 달
	    // break;
	    // case 2: // 평년 2월은 28일, 윤년은 29일
	    // day = yoonDal.equals("28") ? 28 : 29;
	    // break;
	    // default: // 1~12월의 유효성검사
	    // Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	    // }
	    String startDate = "";
	    String endDate = "";

	    if (index == 0) {
		currentMonth = month;
		currentYear = year;
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		totalData = dao.find("mold.getCompleteSchedule", dashBoardDTO);
		delayData = dao.find("mold.getDelaySchedule", dashBoardDTO);
	    } else if (index == 1) {
		oneAfterMonth = month;
		oneAfterYear = year;
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		afterMonthData = dao.find("mold.getCompleteScheduleAfterMonth", dashBoardDTO);
	    } else if (index == 2) {
		twoAfterMonth = month;
		twoAfterYear = year;
		startDate = year + "-" + month + "-01";
		dashBoardDTO.setStartDate(startDate);
		endDate = year + "-" + month + "-" + day;
		dashBoardDTO.setEndDate(endDate);
		twoAfterMonthData = dao.find("mold.getCompleteScheduleTwoAfterMonth", dashBoardDTO);
	    }

	    calendar.setTime(calendar.getTime());
	    calendar.add(Calendar.MONTH, 1);
	}

	int currentMonthCount = 0;
	int currentMonthDelayCount = 0;
	int currentMonthTotal = 0;

	for (int length = 0; length < totalData.size(); length++) {
	    currentMonthCount = totalData.get(length).getNum();
	}

	for (int length = 0; length < delayData.size(); length++) {
	    currentMonthDelayCount = delayData.get(length).getNum();
	}

	currentMonthTotal = currentMonthCount - currentMonthDelayCount;

	int afterMonthCount = 0;
	int twoAfterMonthCount = 0;

	for (int length = 0; length < afterMonthData.size(); length++) {
	    afterMonthCount = afterMonthData.get(length).getNum();
	}

	for (int length = 0; length < twoAfterMonthData.size(); length++) {
	    twoAfterMonthCount = twoAfterMonthData.get(length).getNum();
	}

	Map<String, Object> datasource = new HashMap<String, Object>();
	Map<String, Object> data = new HashMap<String, Object>();
	Map<String, Object> chartAttr = new HashMap<String, Object>();

	chartAttr.put("caption", "");
	chartAttr.put("animation", "0");
	chartAttr.put("bgcolor", "ffffff");
	chartAttr.put("canvasBgColor", "ffffff");
	chartAttr.put("canvasBorderAlpha", "0");
	chartAttr.put("usePlotGradientColor", "0");
	chartAttr.put("showAlternateHGridColor", "0");
	chartAttr.put("showBorder", "0");
	chartAttr.put("divLineIsDashed", "1");
	chartAttr.put("plotBorderAlpha", "10");
	chartAttr.put("showValues", "0");
	chartAttr.put("showLegend", "0");
	chartAttr.put("plotSpacePercent", "65");
	chartAttr.put("formatNumberScale", "0");

	List<Map<String, Object>> categoriesAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> categoriesAttr1 = new HashMap<String, Object>();
	List<Map<String, String>> categoryAttr = new ArrayList<Map<String, String>>();
	Map<String, String> categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", currentMonth + "월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", oneAfterMonth + "월");
	categoryAttr.add(categoryAttr1);
	categoryAttr1 = new HashMap<String, String>();
	categoryAttr1.put("label", twoAfterMonth + "월");
	categoryAttr.add(categoryAttr1);
	// categoryAttr1 = new HashMap<String, String>();
	// categoryAttr1.put("label", "추가");
	// categoryAttr.add(categoryAttr1);
	categoriesAttr1.put("category", categoryAttr);
	categoriesAttr.add(categoriesAttr1);

	List<Map<String, Object>> datasetAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> datasetAttr1 = new HashMap<String, Object>();
	List<Map<String, Object>> setAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> setAttr1;
	List<Map<String, Object>> dataAttr = new ArrayList<Map<String, Object>>();
	Map<String, Object> dataAttr1;

	// datasetAttr1.put("seriesName", "목표");

	setAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "이월");

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", currentMonthCount);
	dataAttr1.put("link", "JavaScript:linkPopUp4('A','S','" + currentMonth + "','" + currentYear + "');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", afterMonthCount);
	dataAttr1.put("link", "JavaScript:linkPopUp3('ONE','S','" + oneAfterMonth + "','" + oneAfterYear + "');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", twoAfterMonthCount);
	dataAttr1.put("link", "JavaScript:linkPopUp3('TWO','S','" + twoAfterMonth + "','" + twoAfterYear + "');");
	dataAttr1.put("color", "6482c0");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesName", "진행");
	setAttr.add(setAttr1);

	dataAttr = new ArrayList<Map<String, Object>>();
	setAttr1 = new HashMap<String, Object>();
	// setAttr1.put("seriesname", "신규");

	dataAttr1 = new HashMap<String, Object>();
	dataAttr1.put("value", currentMonthDelayCount);
	dataAttr1.put("link", "JavaScript:linkPopUp4('B','S','" + currentMonth + "','" + currentYear + "');");
	dataAttr1.put("color", "f46060");
	dataAttr.add(dataAttr1);

	setAttr1.put("data", dataAttr);
	setAttr1.put("seriesName", "지연");
	setAttr.add(setAttr1);

	datasetAttr1.put("dataset", setAttr);
	datasetAttr.add(datasetAttr1);

	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "완료");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", totaljen);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('A','J','" + (month - 3) + "');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", jen1);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('ONE','J','" + (month - 2) + "');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", jen1_);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('TWO','J','" + (month - 1) + "');");
	// dataAttr1.put("color", "f2b579");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "전략");
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "진행");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", jen_);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('B','J','" + (month - 3) + "');");
	// dataAttr1.put("color", "f67575");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "전략-지연");
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);
	//
	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "완료");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", totalyen);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('A','Y','" + (month - 3) + "');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", yen1);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('ONE','Y','" + (month - 2) + "');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", yen1_);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('TWO','Y','" + (month - 1) + "');");
	// dataAttr1.put("color", "aecd79");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "연구");
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "진행");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", yen_);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('B','Y','" + (month - 3) + "');");
	// dataAttr1.put("color", "f67575");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "연구-지연");
	// setAttr.add(setAttr1);
	//
	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);
	//
	// datasetAttr1 = new HashMap<String, Object>();
	// setAttr = new ArrayList<Map<String, Object>>();
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "완료");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", totalcho);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('A','C','" + (month - 3) + "');");
	// dataAttr1.put("color", "9090db");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", cho1);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('ONE','C','" + (month - 2) + "');");
	// dataAttr1.put("color", "9090db");
	// dataAttr.add(dataAttr1);
	//
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", cho1_);
	// dataAttr1.put("link", "JavaScript:linkPopUp3('TWO','C','" + (month - 1) + "');");
	// dataAttr1.put("color", "9090db");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "추가");
	// setAttr.add(setAttr1);
	//
	// dataAttr = new ArrayList<Map<String, Object>>();
	// setAttr1 = new HashMap<String, Object>();
	// // setAttr1.put("seriesname", "진행");
	// dataAttr1 = new HashMap<String, Object>();
	// dataAttr1.put("value", cho_);
	// dataAttr1.put("link", "JavaScript:linkPopUp4('B','C','" + (month - 3) + "');");
	// dataAttr1.put("color", "f67575");
	// dataAttr.add(dataAttr1);
	//
	// setAttr1.put("data", dataAttr);
	// setAttr1.put("seriesName", "추가-지연");
	// setAttr.add(setAttr1);

	// datasetAttr1.put("dataset", setAttr);
	// datasetAttr.add(datasetAttr1);

	data.put("chart", chartAttr);
	data.put("categories", categoriesAttr);
	data.put("dataset", datasetAttr);
	datasource.put("dataSource", data);
	Kogger.debug(getClass(), datasource);
	return data;
    }

    @SuppressWarnings("unchecked")
    public DashBoardDTO getEcoInfoData(DashBoardDTO dashBoardDTO, Model model) {
	List<DashBoardDTO> info = dao.find("mold.ecoInfoData", dashBoardDTO);
	model.addAttribute("partInfo", info);
	DashBoardDTO data = new DashBoardDTO();
	int complete = 0, process = 0;
	for (int index = 0; index < info.size(); index++) {
	    if ("완료".equals(info.get(index).getType())) {
		complete++;

	    } else {
		process++;
	    }
	}
	data.setEcoComplete(complete);
	data.setEcoProcess(process);
	return data;
    }

    @SuppressWarnings("unchecked")
    public DashBoardDTO getOutputInfoData(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> info = dao.find("mold.outputInfoData", dashBoardDTO);
	DashBoardDTO data = new DashBoardDTO();
	int dqmProcessCount = 0, dqmCompleteCount = 0;
	for (int index = 0; index < info.size(); index++) {
	    if ("진행".equals(info.get(index).getDqmStateName())) {
		dqmProcessCount = info.get(index).getNum();
	    } else if ("완료".equals(info.get(index).getDqmStateName())) {
		dqmCompleteCount = info.get(index).getNum();
	    }
	}

	data.setDqmProcessCount(dqmProcessCount);
	data.setDqmCompleteCount(dqmCompleteCount);
	data.setPjtNo(dashBoardDTO.getPjtNo());
	return data;
    }

    @SuppressWarnings("unchecked")
    public List<DashBoardDTO> getOutputTypeList(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> info = dao.find("mold.outputInfoData", dashBoardDTO);
	List<DashBoardDTO> data = new ArrayList<DashBoardDTO>();
	DashBoardDTO data1;
	for (int index = 0; index < info.size(); index++) {
	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(info.get(index).getOid());
	    ProjectOutputData outputData = new ProjectOutputData(output);
	    String oid = CommonUtil.getOIDString(outputData.task);
	    if ("registry".equals(dashBoardDTO.getType())) {
		if (outputData.isRegistry) {
		    String docOid = CommonUtil.getOIDString(outputData.currentDocument);
		    String docOid1 = CommonUtil.getOIDString(outputData.LastDocument);
		    String outOid = CommonUtil.getOIDString(output);
		    data1 = new DashBoardDTO();
		    data1.setTaskName(outputData.task.getTaskName());
		    data1.setObjType(outputData.objType);
		    data1.setName(outputData.name);
		    data1.setIsRegistry(outputData.isRegistry);
		    if (outputData.currentDocument == null) {
			data1.setVersion("");
			data1.setUserName("");
		    } else {
			data1.setVersion(outputData.currentDocument.getVersionDisplayIdentifier().toString());
			data1.setUserName(outputData.currentDocument.getCreatorFullName());
		    }
		    if (outputData.LastDocument == null) {
			data1.setLastVersion("");
		    } else {
			data1.setLastVersion(outputData.LastDocument.getVersionDisplayIdentifier().toString());
		    }
		    data1.setOid(oid);
		    data1.setDocOid(docOid);
		    data1.setDocOid1(docOid1);
		    data1.setOutOid(outOid);
		    data.add(data1);
		}
	    } else if ("define".equals(dashBoardDTO.getType())) {
		if (!outputData.isRegistry) {
		    data1 = new DashBoardDTO();
		    data1.setTaskName(outputData.task.getTaskName());
		    data1.setObjType(outputData.objType);
		    data1.setName(outputData.name);
		    data1.setIsRegistry(outputData.isRegistry);
		    data1.setVersion("");
		    data1.setLastVersion("");
		    data1.setUserName("");
		    data1.setOid(oid);
		    data.add(data1);
		}
	    }
	}
	return data;
    }

    @SuppressWarnings("unchecked")
    public PageControl projectCardIssueList(DashBoardDTO dashBoardDTO) {

	if ("완료".equals(dashBoardDTO.getType())) {
	    dashBoardDTO.setType("1");
	} else if ("미완료".equals(dashBoardDTO.getType())) {
	    dashBoardDTO.setType("0");
	}

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectCardIssueListTotalCount", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectCardIssueList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl teamWorkTimeReportQualityList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("requestDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("requestDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityReviewListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityReviewList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityProductListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityProductList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityMoldListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityMoldList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	} else {
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListTotal", dashBoardDTO);
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl teamWorkTimeReportPeopleList(DashBoardDTO dashBoardDTO) {

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportPeopleListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportPeopleList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	// TASK계획
	List<DashBoardDTO> taskPlan = dao.find("mold.teamWorkTimeTaskPlan", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskPlan.size() > 0) {
		for (int index1 = 0; index1 < taskPlan.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(taskPlan.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setDisplayTaskPlan(String.valueOf(taskPlan.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskPlan(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskPlan(String.valueOf(0));
	    }
	}

	// TASK 진행
	List<DashBoardDTO> taskProcess = dao.find("mold.teamWorkTimeTaskProcess", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskProcess.size() > 0) {
		for (int index1 = 0; index1 < taskProcess.size(); index1++) {

		    if (totalProjectReviewData.get(index).getmOid().equals(taskProcess.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setDisplayTaskProcess(String.valueOf(taskProcess.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskProcess(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskProcess(String.valueOf(0));
	    }
	}

	// TASK 지연
	List<DashBoardDTO> taskDelay = dao.find("mold.teamWorkTimeTaskDelay", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskDelay.size() > 0) {
		for (int index1 = 0; index1 < taskDelay.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(taskDelay.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setDisplayTaskDelay(String.valueOf(taskDelay.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskDelay(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskDelay(String.valueOf(0));
	    }
	}

	// TASK 완료
	List<DashBoardDTO> taskComplete = dao.find("mold.teamWorkTimeTaskComplete", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskComplete.size() > 0) {
		for (int index1 = 0; index1 < taskComplete.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(taskComplete.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setDisplayTaskComplete(String.valueOf(taskComplete.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskComplete(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskComplete(String.valueOf(0));
	    }
	}

	// PROJECT 계획
	List<DashBoardDTO> pjtPlan = dao.find("mold.teamWorkTimePjtPlan", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtPlan.size() > 0) {
		for (int index1 = 0; index1 < pjtPlan.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(pjtPlan.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setPjtPlanNum((pjtPlan.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtPlanNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtPlanNum(0);
	    }
	}

	// PROJECT 진행
	List<DashBoardDTO> pjtProcess = dao.find("mold.teamWorkTimePjtProcess", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtProcess.size() > 0) {
		for (int index1 = 0; index1 < pjtProcess.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(pjtProcess.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setPjtProcessNum((pjtProcess.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtProcessNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtProcessNum(0);
	    }
	}

	// PROJECT 완료
	List<DashBoardDTO> pjtComplete = dao.find("mold.teamWorkTimePjtComplete", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtComplete.size() > 0) {
		for (int index1 = 0; index1 < pjtComplete.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(pjtComplete.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setPjtCompleteNum((pjtComplete.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtCompleteNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtCompleteNum(0);
	    }
	}
	// PROJECT 지연
	List<DashBoardDTO> pjtDelay = dao.find("mold.teamWorkTimePjtDelay", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtDelay.size() > 0) {
		for (int index1 = 0; index1 < pjtDelay.size(); index1++) {
		    if (totalProjectReviewData.get(index).getmOid().equals(pjtDelay.get(index1).getUserName())) {
			totalProjectReviewData.get(index).setPjtDelayNum((pjtDelay.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtDelayNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtDelayNum(0);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamWorkTimeReportTeamList(DashBoardDTO dashBoardDTO) {

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportTeamListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTeamList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	// TASK계획
	List<DashBoardDTO> taskPlan = dao.find("mold.teamWorkTimeByTeamTaskPlan", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskPlan.size() > 0) {
		for (int index1 = 0; index1 < taskPlan.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(taskPlan.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setDisplayTaskTeamPlan(String.valueOf(taskPlan.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskTeamPlan(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskTeamPlan(String.valueOf(0));
	    }
	}

	// TASK 진행
	List<DashBoardDTO> taskProcess = dao.find("mold.teamWorkTimeByTeamTaskProcess", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskProcess.size() > 0) {
		for (int index1 = 0; index1 < taskProcess.size(); index1++) {

		    if (totalProjectReviewData.get(index).getDisplayCode().equals(taskProcess.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setDisplayTaskTeamProcess(String.valueOf(taskProcess.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskTeamProcess(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskTeamProcess(String.valueOf(0));
	    }
	}

	// TASK 지연
	List<DashBoardDTO> taskDelay = dao.find("mold.teamWorkTimeByTeamTaskDelay", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskDelay.size() > 0) {
		for (int index1 = 0; index1 < taskDelay.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(taskDelay.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setDisplayTaskTeamDelay(String.valueOf(taskDelay.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskTeamDelay(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskTeamDelay(String.valueOf(0));
	    }
	}

	// TASK 완료
	List<DashBoardDTO> taskComplete = dao.find("mold.teamWorkTimeTaskComplete", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (taskComplete.size() > 0) {
		for (int index1 = 0; index1 < taskComplete.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(taskComplete.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setDisplayTaskTeamComplete(String.valueOf(taskComplete.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setDisplayTaskTeamComplete(String.valueOf(0));
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setDisplayTaskTeamComplete(String.valueOf(0));
	    }
	}

	// PROJECT 계획
	List<DashBoardDTO> pjtPlan = dao.find("mold.teamWorkTimeByTeamPjtPlan", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtPlan.size() > 0) {
		for (int index1 = 0; index1 < pjtPlan.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(pjtPlan.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setPjtTeamPlanNum((pjtPlan.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtTeamPlanNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtTeamPlanNum(0);
	    }
	}

	// PROJECT 진행
	List<DashBoardDTO> pjtProcess = dao.find("mold.teamWorkTimeByTeamPjtProcess", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtProcess.size() > 0) {
		for (int index1 = 0; index1 < pjtProcess.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(pjtProcess.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setPjtTeamProcessNum((pjtProcess.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtTeamProcessNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtTeamProcessNum(0);
	    }
	}

	// PROJECT 완료
	List<DashBoardDTO> pjtComplete = dao.find("mold.teamWorkTimeByTeamPjtComplete", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtComplete.size() > 0) {
		for (int index1 = 0; index1 < pjtComplete.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(pjtComplete.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setPjtTeamCompleteNum((pjtComplete.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtTeamCompleteNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtTeamCompleteNum(0);
	    }
	}

	// PROJECT 지연
	List<DashBoardDTO> pjtDelay = dao.find("mold.teamWorkTimeByTeamPjtDelay", dashBoardDTO);

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (pjtDelay.size() > 0) {
		for (int index1 = 0; index1 < pjtDelay.size(); index1++) {
		    if (totalProjectReviewData.get(index).getDisplayCode().equals(pjtDelay.get(index1).getDisplayCode())) {
			totalProjectReviewData.get(index).setPjtTeamDelayNum((pjtDelay.get(index1).getNum()));
			break;
		    } else {
			totalProjectReviewData.get(index).setPjtTeamDelayNum(0);
		    }
		}
	    } else {
		totalProjectReviewData.get(index).setPjtTeamDelayNum(0);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl programProcessReportList(DashBoardDTO dashBoardDTO) throws Exception {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.programProcessReportListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.programProcessReportList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	List<DashBoardDTO> data = dao.find("mold.programEventList", dashBoardDTO);
	List<DashBoardDTO> data1 = dao.find("mold.programItemTypeList", dashBoardDTO);

	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yy.MM.dd", Locale.KOREA);
	Date currentTime = new Date();
	String mTime = mSimpleDateFormat.format(currentTime).replace(".", "");
	int today = Integer.parseInt(mTime);
	List<Integer> beforeState = new ArrayList<Integer>();
	List<Integer> currentState = new ArrayList<Integer>();
	List<Integer> nextState = new ArrayList<Integer>();
	List<String> beforeStateType = new ArrayList<String>();
	List<String> currentStateType = new ArrayList<String>();
	List<String> nextStateType = new ArrayList<String>();

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    for (int index1 = 0; index1 < data.size(); index1++) {
		if (totalProjectReviewData.get(index).getProgramNo().equals(data.get(index1).getProgramNo())) {

		    SimpleDateFormat transFormat = new SimpleDateFormat("yy.MM.dd", Locale.KOREA);
		    Date day = data.get(index1).getOemEndDate();
		    String day_ = transFormat.format(day).replace(".", "");
		    int temp = Integer.parseInt(day_);
		    if (today > temp) {
			currentState.add(temp);
			currentStateType.add(data.get(index1).getType());
		    } else {
			nextState.add(temp);
			nextStateType.add(data.get(index1).getType());
		    }

		}
	    }

	    Integer max = null;
	    String currentDate = null;
	    if (currentState.size() > 0) {
		max = Collections.max(currentState);
		String maxDate = String.valueOf(max);
		currentDate = maxDate.substring(0, 2) + "-" + maxDate.substring(2, 4) + "-" + maxDate.substring(4, 6);
		// totalProjectReviewData.get(index).setCurrentDate(currentDate);
	    }

	    Integer min = null;
	    String nextDate = null;
	    if (nextState.size() > 0) {
		min = Collections.min(nextState);
		String minDate = String.valueOf(min);

		nextDate = minDate.substring(0, 2) + "-" + minDate.substring(2, 4) + "-" + minDate.substring(4, 6);
		// totalProjectReviewData.get(index).setNextDate(nextDate);
	    }
	    for (int index2 = 0; index2 < currentState.size(); index2++) {
		if (max == currentState.get(index2).intValue()) {
		    totalProjectReviewData.get(index).setCurrentType(currentDate + "/" + currentStateType.get(index2).toString());
		}
	    }

	    for (int index3 = 0; index3 < nextState.size(); index3++) {
		if (min == nextState.get(index3).intValue()) {
		    totalProjectReviewData.get(index).setNextType(nextDate + "/" + nextStateType.get(index3).toString());
		}
	    }

	    for (int index4 = 0; index4 < currentState.size(); index4++) {
		if (max > currentState.get(index4).intValue()) {
		    beforeState.add(currentState.get(index4).intValue());
		    beforeStateType.add(currentStateType.get(index4).toString());
		}
	    }

	    Integer beforeMax = null;
	    String beforeDate = null;
	    if (beforeState.size() > 0) {
		beforeMax = Collections.max(beforeState);
		String maxDate = String.valueOf(beforeMax);
		beforeDate = maxDate.substring(0, 2) + "-" + maxDate.substring(2, 4) + "-" + maxDate.substring(4, 6);
		// totalProjectReviewData.get(index).setCurrentDate(currentDate);
	    }

	    for (int index5 = 0; index5 < beforeState.size(); index5++) {
		if (beforeMax == beforeState.get(index5).intValue()) {
		    totalProjectReviewData.get(index).setBeforeType(beforeDate + "/" + beforeStateType.get(index5).toString());
		}
	    }

	    beforeState.clear();
	    currentState.clear();
	    nextState.clear();
	    beforeStateType.clear();
	    currentStateType.clear();
	    nextStateType.clear();

	    currentDate = "";
	    nextDate = "";
	    beforeDate = "";

	}

	for (int index6 = 0; index6 < totalProjectReviewData.size(); index6++) {
	    for (int index7 = 0; index7 < data1.size(); index7++) {
		if (totalProjectReviewData.get(index6).getProgramNo().equals(data1.get(index7).getProgramNo())) {
		    if ("Mold".equals(data1.get(index7).getItemType())) {
			totalProjectReviewData.get(index6).setMoldCount(data1.get(index7).getNum());
		    } else {
			totalProjectReviewData.get(index6).setPressCount(data1.get(index7).getNum());
		    }
		}
	    }
	}

	StandardProgramService programService = new StandardProgramService();
	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    List<ProgramProjectDTO> list = programService.findProjectByProgram(totalProjectReviewData.get(index).getOid());
	    int projectCount = list.size();
	    totalProjectReviewData.get(index).setProjectCount(projectCount);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl projectPopupListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectPopupListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectPopupListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl assemblyNormalProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.assemblyNormalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.assemblyNormalProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl singleNormalProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.singleNormalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.singleNormalProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl singleSystemProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.singleSystemProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.singleSystemProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl singleTotalProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.singleTotalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.singleTotalProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl goodsNormalProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.goodsNormalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.goodsNormalProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl goodsSystemProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.goodsSystemProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.goodsSystemProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl goodsTotalProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.goodsTotalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.goodsTotalProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl prodDevCostReportProjectList(DashBoardDTO dashBoardDTO) throws Exception {

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodDevCostReportProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodDevCostReportProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    float targetCost1 = 0;
	    float targetCost2 = 0;
	    float targetCost3 = 0;
	    float targetCost4 = 0;
	    float budget1 = 0;
	    float budget2 = 0;
	    float budget3 = 0;
	    float budget4 = 0;
	    float resultsCost1 = 0;
	    float resultsCost2 = 0;
	    float resultsCost3 = 0;
	    float resultsCost4 = 0;
	    float balanceCost1 = 0;
	    float balanceCost2 = 0;
	    float balanceCost3 = 0;
	    float balanceCost4 = 0;
	    float totalbudget = 0;
	    float totalresult = 0;

	    int count = 0;

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		float budget = 0; // 예산
		float executionCost = 0; // 초기집행가
		float editCost = 0; // 추가집행가
		float totalExpense = 0; // 총집행가
		float balanceCost = 0; // 잔액

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		if ("금형".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost1 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 목표
		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
		    balanceCost1 += balanceCost; // 잔액
		} else if ("설비".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost2 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget2 += budget;
		    resultsCost2 += executionCost + editCost;
		    totalProjectReviewData.get(index).setBudgetCost1(budget2 / 1000);
		    totalProjectReviewData.get(index).setResultCost1(resultsCost2 / 1000);
		    balanceCost2 += balanceCost;
		} else if ("JIG".equals(costInfo.getCostType())) {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost4 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget4 += budget;
		    resultsCost4 += executionCost + editCost;
		    totalProjectReviewData.get(index).setBudgetCost3(budget4 / 1000);
		    totalProjectReviewData.get(index).setResultCost3(resultsCost4 / 1000);
		    balanceCost4 += balanceCost;
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			targetCost3 += Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
		    budget3 += budget;
		    resultsCost3 += executionCost + editCost;
		    totalProjectReviewData.get(index).setBudgetCost3(budget3 / 1000);
		    totalProjectReviewData.get(index).setResultCost3(resultsCost3 / 1000);
		    balanceCost3 += balanceCost;
		}
		totalbudget = budget1 + budget2 + budget3 + budget4;
		totalresult = resultsCost1 + resultsCost2 + resultsCost3 + resultsCost4;
		totalProjectReviewData.get(index).setTotalBudget(totalbudget / 1000);
		totalProjectReviewData.get(index).setTotalResult(totalresult / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl ecrProjectList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.ecrProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.ecrProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl ecoProjectList(DashBoardDTO dashBoardDTO) {

	String dataType = dashBoardDTO.getDataType();

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}
	int totalCount = 0;
	PageControl pageControl = null;

	if ("AEGIS".equals(dataType)) {
	    if (!"C".equals(dashBoardDTO.getDivisionFlag()) && !"E".equals(dashBoardDTO.getDivisionFlag())) {
		dashBoardDTO.setDivisionFlag("");
	    }

	    totalCount = dao.getTotalRecordCount("mold.ecoProjectListTotal_2", dashBoardDTO);

	    List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.ecoProjectList_2", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());

	    pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
		    dashBoardDTO.getFormPage(), totalCount);

	} else {
	    totalCount = dao.getTotalRecordCount("mold.ecoProjectListTotal", dashBoardDTO);
	    List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.ecoProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	    pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
		    dashBoardDTO.getFormPage(), totalCount);
	}

	return pageControl;
    }

    @SuppressWarnings("unchecked")
    public PageControl ecnProjectList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "-01-01";
	String endDate = String.valueOf(year) + "-12-31";

	// Timestamp sDate = DateUtil.getTimestampFormat(startDate, "yyyy-MM-dd");
	// Timestamp eDate = DateUtil.getTimestampFormat(endDate, "yyyy-MM-dd");
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.ecnProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.ecnProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl currentSettingList(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.currentSettingListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.currentSettingList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessPlanDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessPlanDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessPlanDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessTeamPlanDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessTeamPlanDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessTeamPlanDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessTeamDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessTeamDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessTeamDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessCompleteDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessCompleteDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessCompleteDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessCompleteTeamDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessCompleteTeamDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessCompleteTeamDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessDelayDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessDelayDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessDelayDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskProcessDelayTeamDataList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamTaskProcessDelayTeamDataListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamTaskProcessDelayTeamDataList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldTypeListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldTypeListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldTypeListSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public int getEcnDelayCount(DashBoardDTO dashBoardDTO) {

	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> ecnDelayData = dao.find("mold.getChangeDesignDelayECN", dashBoardDTO);
	return ecnDelayData.size();
    }

    public List<ProductProjectDTO> getCarTypeBasicInfo(DashBoardDTO dashBoardDTO, Model model) {
	List<ProductProjectDTO> basicData = dao.find("mold.getCarTypeBasicInfoData", dashBoardDTO);
	String sopDate = "20" + basicData.get(0).getSop();
	basicData.get(0).setSop(sopDate);
	return basicData;
    }

    public List<DashBoardDTO> getCarTypeScheduleInfo(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> scheduleData = dao.find("mold.getCarTypeScheduleInfoData", dashBoardDTO);
	return scheduleData;
    }

    public List<DashBoardDTO> getDevTypeInfo(DashBoardDTO dashBoardDTO, Model model) {
	List<DashBoardDTO> devTypeData = dao.find("mold.getDevTypeInfoData", dashBoardDTO);
	List<DashBoardDTO> devTypeData1 = dao.find("mold.getDevTypeInfoData1", dashBoardDTO);
	int suCount = 0;
	int jenCount = 0;
	int yenCount = 0;
	int cuCount = 0;
	int totalCount = 0;
	for (int index = 0; index < devTypeData.size(); index++) {
	    if (devTypeData.get(index).getDevType() != null) {
		if ("수주개발".equals(devTypeData.get(index).getDevType())) {
		    suCount = devTypeData.get(index).getNum();
		} else if ("전략개발".equals(devTypeData.get(index).getDevType())) {
		    jenCount = devTypeData.get(index).getNum();
		} else if ("연구".equals(devTypeData.get(index).getDevType())) {
		    yenCount = devTypeData.get(index).getNum();
		} else if ("4M".equals(devTypeData.get(index).getDevType())) {
		    cuCount = devTypeData.get(index).getNum();
		}
	    }
	}
	totalCount = suCount + jenCount + yenCount + cuCount;
	model.addAttribute("suCount", suCount);
	model.addAttribute("jenCount", jenCount);
	model.addAttribute("yenCount", yenCount);
	model.addAttribute("cuCount", cuCount);
	model.addAttribute("totalCount", totalCount);
	model.addAttribute("dashBoardDTO", dashBoardDTO);

	int suSCount = 0;
	int suDCount = 0;
	int jenSCount = 0;
	int jenDCount = 0;
	int yenSCount = 0;
	int yenDCount = 0;
	int cuSCount = 0;
	int cuDCount = 0;
	int sTotalCount = 0;
	int dTotalCount = 0;

	for (int index = 0; index < devTypeData1.size(); index++) {
	    if ("수주개발".equals(devTypeData1.get(index).getDevType()) && "Mold".equals(devTypeData1.get(index).getItemType())) {
		suSCount += devTypeData1.get(index).getNum();
	    } else if ("수주개발".equals(devTypeData1.get(index).getDevType()) && "Press".equals(devTypeData1.get(index).getItemType())) {
		suSCount += devTypeData1.get(index).getNum();
	    } else if ("수주개발".equals(devTypeData1.get(index).getDevType()) && "구매품".equals(devTypeData1.get(index).getItemType())) {
		suDCount = devTypeData1.get(index).getNum();
	    } else if ("전략개발".equals(devTypeData1.get(index).getDevType()) && "Mold".equals(devTypeData1.get(index).getItemType())) {
		jenSCount += devTypeData1.get(index).getNum();
	    } else if ("전략개발".equals(devTypeData1.get(index).getDevType()) && "Press".equals(devTypeData1.get(index).getItemType())) {
		jenSCount += devTypeData1.get(index).getNum();
	    } else if ("전략개발".equals(devTypeData1.get(index).getDevType()) && "구매품".equals(devTypeData1.get(index).getItemType())) {
		jenDCount = devTypeData1.get(index).getNum();
	    } else if ("연구".equals(devTypeData1.get(index).getDevType()) && "Mold".equals(devTypeData1.get(index).getItemType())) {
		yenSCount += devTypeData1.get(index).getNum();
	    } else if ("연구".equals(devTypeData1.get(index).getDevType()) && "Press".equals(devTypeData1.get(index).getItemType())) {
		yenSCount += devTypeData1.get(index).getNum();
	    } else if ("연구".equals(devTypeData1.get(index).getDevType()) && "구매품".equals(devTypeData1.get(index).getItemType())) {
		yenDCount = devTypeData1.get(index).getNum();
	    } else if ("4M".equals(devTypeData1.get(index).getDevType()) && "Mold".equals(devTypeData1.get(index).getItemType())) {
		cuSCount += devTypeData1.get(index).getNum();
	    } else if ("4M".equals(devTypeData1.get(index).getDevType()) && "Press".equals(devTypeData1.get(index).getItemType())) {
		cuSCount += devTypeData1.get(index).getNum();
	    } else if ("4M".equals(devTypeData1.get(index).getDevType()) && "구매품".equals(devTypeData1.get(index).getItemType())) {
		cuDCount = devTypeData1.get(index).getNum();
	    }
	}

	sTotalCount = suSCount + jenSCount + yenCount + cuCount;
	dTotalCount = suDCount + jenDCount + yenCount + cuCount;
	model.addAttribute("suSCount", suSCount);
	model.addAttribute("suDCount", suDCount);
	model.addAttribute("jenSCount", jenSCount);
	model.addAttribute("jenDCount", jenDCount);
	model.addAttribute("yenSCount", yenSCount);
	model.addAttribute("yenDCount", yenDCount);
	model.addAttribute("cuSCount", cuSCount);
	model.addAttribute("cuDCount", cuDCount);
	model.addAttribute("sTotalCount", sTotalCount);
	model.addAttribute("dTotalCount", dTotalCount);
	return devTypeData;
    }

    public List<DashBoardDTO> getMoldCategoryInfo(DashBoardDTO dashBoardDTO, Model model) {
	List<DashBoardDTO> moldCategoryData = dao.find("mold.getMoldCategoryInfo", dashBoardDTO);

	int newMoldCount = 0;
	int modifyMoldCount = 0;
	int newPressCount = 0;
	int modifyPressCount = 0;
	int moldTotalCount = 0;
	int pressTotalCount = 0;
	int totalCount = 0;
	for (int index = 0; index < moldCategoryData.size(); index++) {
	    if ("Mold".equals(moldCategoryData.get(index).getItemType())
		    && (!"시작Mo".equals(moldCategoryData.get(index).getMoldCategory()) && !"양산Mo".equals(moldCategoryData.get(index)
		            .getMoldCategory()))) {
		newMoldCount += moldCategoryData.get(index).getNum();
	    } else if ("Mold".equals(moldCategoryData.get(index).getItemType())
		    && "시작Mo".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyMoldCount += moldCategoryData.get(index).getNum();
	    } else if ("Mold".equals(moldCategoryData.get(index).getItemType())
		    && "양산Mo".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyMoldCount += moldCategoryData.get(index).getNum();
	    } else if ("Mold".equals(moldCategoryData.get(index).getItemType())
		    && "기타".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyMoldCount += moldCategoryData.get(index).getNum();
	    } else if ("Press".equals(moldCategoryData.get(index).getItemType())
		    && (!"시작Mo".equals(moldCategoryData.get(index).getMoldCategory()) && !"양산Mo".equals(moldCategoryData.get(index)
		            .getMoldCategory()))) {
		newPressCount += moldCategoryData.get(index).getNum();
	    } else if ("Press".equals(moldCategoryData.get(index).getItemType())
		    && "시작Mo".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyPressCount += moldCategoryData.get(index).getNum();
	    } else if ("Press".equals(moldCategoryData.get(index).getItemType())
		    && "양산Mo".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyPressCount += moldCategoryData.get(index).getNum();
	    } else if ("Press".equals(moldCategoryData.get(index).getItemType())
		    && "기타".equals(moldCategoryData.get(index).getMoldCategory())) {
		modifyPressCount += moldCategoryData.get(index).getNum();
	    }
	}

	moldTotalCount = newMoldCount + modifyMoldCount;
	pressTotalCount = newPressCount + modifyPressCount;
	totalCount = moldTotalCount + pressTotalCount;

	model.addAttribute("newMoldCount", newMoldCount);
	model.addAttribute("modifyMoldCount", modifyMoldCount);
	model.addAttribute("newPressCount", newPressCount);
	model.addAttribute("modifyPressCount", modifyPressCount);
	model.addAttribute("moldTotalCount", moldTotalCount);
	model.addAttribute("pressTotalCount", pressTotalCount);
	model.addAttribute("allTotalCount", totalCount);

	return moldCategoryData;
    }

    public List<DashBoardDTO> getCarTypeProjectListInfo(DashBoardDTO dashBoardDTO) {

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	List<DashBoardDTO> projectListData = dao.find("mold.getCarTypeProjectListInfo", dashBoardDTO);
	List<DashBoardDTO> moldItemTypeData = dao.find("mold.getMoldItemTypeInfo", dashBoardDTO);
	List<DashBoardDTO> taskCompleteData = dao.find("mold.getTaskCompleteInfo", dashBoardDTO);
	List<DashBoardDTO> taskProcessData = dao.find("mold.getTaskProcessInfo", dashBoardDTO);
	List<DashBoardDTO> taskDelayData = dao.find("mold.getTaskDelayInfo", dashBoardDTO);

	int mold = 0;
	int press = 0;
	int goods = 0;
	for (int index = 0; index < projectListData.size(); index++) {
	    for (int index1 = 0; index1 < moldItemTypeData.size(); index1++) {
		if (projectListData.get(index).getPjtNo().equals(moldItemTypeData.get(index1).getPjtNo())) {
		    if ("Mold".equals(moldItemTypeData.get(index1).getItemType())) {
			mold = moldItemTypeData.get(index1).getNum();
			projectListData.get(index).setMoldCount(mold);
		    } else if ("Press".equals(moldItemTypeData.get(index1).getItemType())) {
			press = moldItemTypeData.get(index1).getNum();
			projectListData.get(index).setPressCount(press);
		    } else if ("구매품".equals(moldItemTypeData.get(index1).getItemType())) {
			goods = moldItemTypeData.get(index1).getNum();
			projectListData.get(index).setGoodsCount(goods);
		    }
		}
	    }
	}

	int complete = 0;
	for (int index = 0; index < projectListData.size(); index++) {
	    for (int index1 = 0; index1 < taskCompleteData.size(); index1++) {
		if (projectListData.get(index).getPjtNo().equals(taskCompleteData.get(index1).getPjtNo())) {
		    complete = taskCompleteData.get(index1).getNum();
		    projectListData.get(index).setTaskCompleteCount(complete);
		}
	    }
	}

	int process = 0;
	for (int index = 0; index < projectListData.size(); index++) {
	    for (int index1 = 0; index1 < taskProcessData.size(); index1++) {
		if (projectListData.get(index).getPjtNo().equals(taskProcessData.get(index1).getPjtNo())) {
		    process = taskProcessData.get(index1).getNum();
		    projectListData.get(index).setTaskProcessCount(process);
		}
	    }
	}

	int delay = 0;
	for (int index = 0; index < projectListData.size(); index++) {
	    for (int index1 = 0; index1 < taskDelayData.size(); index1++) {
		if (projectListData.get(index).getPjtNo().equals(taskDelayData.get(index1).getPjtNo())) {
		    delay = taskDelayData.get(index1).getNum();
		    projectListData.get(index).setTaskDelayNum(delay);
		}
	    }
	}

	for (int index = 0; index < projectListData.size(); index++) {
	    projectListData.get(index).setTotalCount(
		    projectListData.get(index).getTaskCompleteCount() + projectListData.get(index).getTaskProcessCount());
	}
	return projectListData;
    }

    public PageControl carTypeAssemProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.carTypeAssemProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.carTypeAssemProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl carTypeSingleProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.carTypeSingleProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.carTypeSingleProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldCategoryDataProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldCategoryDataProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldCategoryDataProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldCategoryDataProjectList1(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldCategoryDataProjectList1Total", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldCategoryDataProjectList1", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public List<DashBoardDTO> getProgramCustomSchdule(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> data = dao.find("mold.getProgramCustomSchduleData", dashBoardDTO);
	return data;
    }

    public PageControl teamIssueListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamIssueListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamIssueListSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamIssueTeamListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamIssueTeamListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamIssueTeamListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectProcessListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamProjectProcessListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectProcessListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectTeamProcessListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamProjectTeamProcessListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectTeamProcessListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		if (project != null) {

		    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		    while (rtCost.hasMoreElements()) {
			Object[] obj = (Object[]) rtCost.nextElement();
			CostInfo costInfo = (CostInfo) obj[0];

			if (costInfo.getOrderInvest() != null) {
			    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			    if (totalPriceObj != null)
				budget = totalPriceObj.longValue(); // 예산
			    if (initExpenseObj != null)
				executionCost = initExpenseObj.longValue(); // 초기집행가
			    if (addExpenseObj != null)
				editCost = addExpenseObj.longValue(); // 추가집행가
			    if (totalExpenseObj != null)
				totalExpense = totalExpenseObj.longValue(); // 총집행가
			    balanceCost = budget - totalExpense; // 잔액
			} else {
			    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
				executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
				editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			    totalExpense = executionCost + editCost; // 총집행가
			    balanceCost = budget - totalExpense; // 잔액
			}

			budget1 += budget; // 예산
			resultsCost1 += executionCost + editCost; // 실적
		    }

		    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
		}
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectProcessCompleteListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamProjectProcessCompleteListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectProcessCompleteListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectProcessTeamCompleteListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamProjectProcessTeamCompleteListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectProcessTeamCompleteListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectPlanListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamProjectPlanListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectPlanListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectTeamPlanListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamProjectTeamPlanListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectTeamPlanListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		if (project != null) {
		    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		    while (rtCost.hasMoreElements()) {
			Object[] obj = (Object[]) rtCost.nextElement();
			CostInfo costInfo = (CostInfo) obj[0];

			if (costInfo.getOrderInvest() != null) {
			    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			    if (totalPriceObj != null)
				budget = totalPriceObj.longValue(); // 예산
			    if (initExpenseObj != null)
				executionCost = initExpenseObj.longValue(); // 초기집행가
			    if (addExpenseObj != null)
				editCost = addExpenseObj.longValue(); // 추가집행가
			    if (totalExpenseObj != null)
				totalExpense = totalExpenseObj.longValue(); // 총집행가
			    balanceCost = budget - totalExpense; // 잔액
			} else {
			    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
				executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
				editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			    totalExpense = executionCost + editCost; // 총집행가
			    balanceCost = budget - totalExpense; // 잔액
			}

			budget1 += budget; // 예산
			resultsCost1 += executionCost + editCost; // 실적
		    }

		    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
		}
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectDelayListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamProjectDelayListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectDelayListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectTeamDelayListSetting(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.teamProjectTeamDelayListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamProjectTeamDelayListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {
	    if (!"검토".equals(totalProjectReviewData.get(index).getPjtTypeName())) {
		long budget1 = 0;
		long budget2 = 0;
		long budget3 = 0;
		long budget4 = 0;
		long resultsCost1 = 0;
		long resultsCost2 = 0;
		long resultsCost3 = 0;
		long resultsCost4 = 0;

		int count = 0;

		long budget = 0; // 예산
		long executionCost = 0; // 초기집행가
		long editCost = 0; // 추가집행가
		long totalExpense = 0; // 총집행가
		long balanceCost = 0; // 잔액

		String oid = totalProjectReviewData.get(index).getOid();
		E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

		QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		while (rtCost.hasMoreElements()) {
		    Object[] obj = (Object[]) rtCost.nextElement();
		    CostInfo costInfo = (CostInfo) obj[0];

		    if (costInfo.getOrderInvest() != null) {
			Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
			Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
			Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
			Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
			Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

			if (totalPriceObj != null)
			    budget = totalPriceObj.longValue(); // 예산
			if (initExpenseObj != null)
			    executionCost = initExpenseObj.longValue(); // 초기집행가
			if (addExpenseObj != null)
			    editCost = addExpenseObj.longValue(); // 추가집행가
			if (totalExpenseObj != null)
			    totalExpense = totalExpenseObj.longValue(); // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    } else {
			if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

			if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
			else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

			if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

			totalExpense = executionCost + editCost; // 총집행가
			balanceCost = budget - totalExpense; // 잔액
		    }

		    budget1 += budget; // 예산
		    resultsCost1 += executionCost + editCost; // 실적
		}

		totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
		totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl currentSettingList1(DashBoardDTO dashBoardDTO) throws Exception {
	int year = dashBoardDTO.getYear();
	int month = dashBoardDTO.getMonth();
	int day = 0;
	String yoonDal = (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? "29" : "28") : "29") : "28";

	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
	    day = 31; // 31일까지 있는 달
	    break;
	case 4:
	case 6:
	case 9:
	case 11:
	    day = 30; // 30일까지 있는 달
	    break;
	case 2: // 평년 2월은 28일, 윤년은 29일
	    day = yoonDal.equals("28") ? 28 : 29;
	    break;
	default: // 1~12월의 유효성검사
	    Kogger.debug(getClass(), "1~12 사이의 월을 입력하세요.");
	}

	String startDate = year + "/" + month + "/01";
	dashBoardDTO.setStartDate(startDate);
	String endDate = year + "/" + month + "/" + day;
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.currentSettingList1Total", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.currentSettingList1", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl quiltyProblemList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.quiltyProblemListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.quiltyProblemList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl productCompleteTaskSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productCompleteTaskSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productCompleteTaskSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl productProcessTaskSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.productProcessTaskSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productProcessTaskSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl productDelayTaskSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.productDelayTaskSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productDelayTaskSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl itemMoldListPopupSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.itemMoldListPopupSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.itemMoldListPopupSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl itemGoodsListPopupSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.itemGoodsListPopupSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.itemGoodsListPopupSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public List<DashBoardDTO> getProgramMoldReportInfo(DashBoardDTO dashBoardDTO, Model model) {
	List<DashBoardDTO> data = dao.find("mold.programMoldReportInfo", dashBoardDTO);
	List<DashBoardDTO> data1 = dao.find("mold.programMoldReportInfo1", dashBoardDTO);

	int moldInCount = 0, moldOutCount = 0, moldInChinaCount = 0;
	int pressInCount = 0, pressOutCount = 0, pressInChinaCount = 0;

	if (data.size() > 0) {
	    for (int index = 0; index < data.size(); index++) {
		if ("Mold".equals(data.get(index).getItemType())) {
		    if ("사내".equals(data.get(index).getMaking())) {
			moldInCount = data.get(index).getNum();
		    } else {
			moldOutCount = data.get(index).getNum();
		    }
		} else if ("Press".equals(data.get(index).getItemType())) {
		    if ("사내".equals(data.get(index).getMaking())) {
			pressInCount = data.get(index).getNum();
		    } else {
			pressOutCount = data.get(index).getNum();
		    }
		}
	    }
	}

	if (data1.size() > 0) {
	    for (int index = 0; index < data1.size(); index++) {
		if ("Mold".equals(data1.get(index).getItemType())) {
		    if ("사내".equals(data1.get(index).getMaking())) {
			moldInChinaCount = data1.get(index).getNum();
		    }
		} else if ("Press".equals(data1.get(index).getItemType())) {
		    if ("사내".equals(data1.get(index).getMaking())) {
			pressInChinaCount = data1.get(index).getNum();
		    }
		}
	    }
	}

	model.addAttribute("moldInCount", moldInCount);
	model.addAttribute("moldOutCount", moldOutCount);

	model.addAttribute("pressInCount", pressInCount);
	model.addAttribute("pressOutCount", pressOutCount);

	model.addAttribute("moldInChinaCount", moldInChinaCount);
	model.addAttribute("pressInChinaCount", pressInChinaCount);

	return data;
    }

    public PageControl moldReportProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldReportProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldReportProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldTotalReportProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldTotalReportProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldTotalReportProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldReportChinaProjectList(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldReportChinaProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldReportChinaProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl devQuiltyProblemList(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayCompleteDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("COMPLETEDATE");
		} else if ("displayOccurDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("OCCURDATE");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayCompleteDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("COMPLETEDATE");
		} else if ("displayOccurDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("OCCURDATE");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.devQuiltyProblemListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.devQuiltyProblemList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl carTaskCompleteSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.carTaskCompleteSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.carTaskCompleteSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl carTaskProcessSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planEndDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planEndDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	int totalCount = dao.getTotalRecordCount("mold.carTaskProcessSettingTotal", dashBoardDTO);

	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.carTaskProcessSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl carTaskTotalSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.carTaskTotalSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.carTaskTotalSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl productTotalProjectList(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productTotalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productTotalProjectList", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl moldTotalProjectList(DashBoardDTO dashBoardDTO) throws Exception {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.moldTotalProjectListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.moldTotalProjectList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getmOid();
	    MoldProject moldProject = (MoldProject) CommonUtil.getObject(oid);

	    MoldItemInfo info = moldProject.getMoldInfo();
	    CostInfo costInfo = info.getCostInfo();

	    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    boolean isOrder = false;
	    String orderNumber = null;
	    String targetCost = "";
	    long initPrice = 0;
	    long totalPrice = 0;
	    long initPlanPrice = 0;
	    long addPlanPrice = 0;
	    long totalPlanPrice = 0;

	    float initmoldTotalPrice = 0;
	    float debugingTotalPrice = 0;
	    float moldTotalPrice = 0;
	    if (costInfo != null) {
		orderNumber = costInfo.getOrderInvest();

	    }
	    boolean isTotal = false;
	    // orderNumber = "402937";
	    Vector priceV = new Vector();
	    if (orderNumber != null && orderNumber.length() > 0) {
		isOrder = true;

		Hashtable hash = ProductPrice.getPrice(orderNumber);
		Long longValue = (Long) hash.get(ProductPrice.INITPRICE);
		if (longValue != null) {
		    initPlanPrice = longValue.longValue();
		}
		longValue = (Long) hash.get(ProductPrice.ADDPRICE);
		if (longValue != null) {
		    addPlanPrice = longValue.longValue();
		}

		longValue = (Long) hash.get(ProductPrice.TOTALPRICE);
		if (longValue != null) {
		    totalPlanPrice = longValue.longValue();
		}

		priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
		int initMold = 0;
		Integer integer = 0;
		if (priceV.size() > 0) {

		    Hashtable hhh = (Hashtable) priceV.get(0);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			initMold = integer.intValue();

		    }
		}

		initmoldTotalPrice = initMold;

		int debugingtotal = 0;
		for (int i = 1; i < priceV.size(); i++) {
		    Hashtable hhh = (Hashtable) priceV.get(i);
		    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

		    if (integer != null) {
			debugingtotal += integer.intValue();
		    }
		}

		debugingTotalPrice = debugingtotal;
		moldTotalPrice = initMold + debugingtotal;

		if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
		    isTotal = true;
		}
	    } else {
		orderNumber = "-";
	    }

	    totalProjectReviewData.get(index).setBudgetCost(totalPlanPrice / 1000);
	    totalProjectReviewData.get(index).setResultCost(moldTotalPrice / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl productTotalTaskSetting(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.productTotalTaskSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.productTotalTaskSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public List<DashBoardDTO> getProjectGateData(DashBoardDTO dashBoardDTO) {
	List<DashBoardDTO> data = dao.find("mold.getProjectGateDataInfo", dashBoardDTO);
	return data;
    }

    public PageControl teamQulityListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("requestDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("requestDater");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamQulityListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamQulityListSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamQulityTeamListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("requestDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("requestDater");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.teamQulityTeamListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.teamQulityTeamListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public List<DashBoardDTO> getProdTypeByDevMakeReprtList(DashBoardDTO dashBoardDTO) {

	Calendar oCalendar = Calendar.getInstance();

	String yearType = String.valueOf(dashBoardDTO.getYear());
	int year = 0;
	if (yearType.equals("0")) {
	    year = oCalendar.get(Calendar.YEAR);
	} else {
	    year = dashBoardDTO.getYear();
	}

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	List<DashBoardDTO> data = dao.find("mold.getProdTypeByDevMakeReprtList", dashBoardDTO);

	return data;
    }

    public PageControl prodTypeReviewTotalSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeReviewTotalSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeReviewTotalSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	// for (int index = 0; index < totalProjectReviewData.size(); index++) {
	//
	// long budget1 = 0;
	// long budget2 = 0;
	// long budget3 = 0;
	// long budget4 = 0;
	// long resultsCost1 = 0;
	// long resultsCost2 = 0;
	// long resultsCost3 = 0;
	// long resultsCost4 = 0;
	//
	// int count = 0;
	//
	// long budget = 0; // 예산
	// long executionCost = 0; // 초기집행가
	// long editCost = 0; // 추가집행가
	// long totalExpense = 0; // 총집행가
	// long balanceCost = 0; // 잔액
	//
	// String oid = totalProjectReviewData.get(index).getOid();
	// E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	//
	// java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
	//
	// QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	// while (rtCost.hasMoreElements()) {
	// Object[] obj = (Object[]) rtCost.nextElement();
	// CostInfo costInfo = (CostInfo) obj[0];
	//
	// if (costInfo.getOrderInvest() != null) {
	// Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
	// Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
	// Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
	// Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
	// Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);
	//
	// if (totalPriceObj != null)
	// budget = totalPriceObj.intValue(); // 예산
	// if (initExpenseObj != null)
	// executionCost = initExpenseObj.intValue(); // 초기집행가
	// if (addExpenseObj != null)
	// editCost = addExpenseObj.intValue(); // 추가집행가
	// if (totalExpenseObj != null)
	// totalExpense = totalExpenseObj.intValue(); // 총집행가
	// balanceCost = budget - totalExpense; // 잔액
	// } else {
	// if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
	// budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산
	//
	// if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
	// executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
	// else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
	// executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가
	//
	// if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
	// editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가
	//
	// totalExpense = executionCost + editCost; // 총집행가
	// balanceCost = budget - totalExpense; // 잔액
	// }
	//
	// budget1 += budget; // 예산
	// resultsCost1 += executionCost + editCost; // 실적
	// }
	//
	// totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	// totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	// }

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeReviewCompSetting(DashBoardDTO dashBoardDTO) {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeReviewCompSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeReviewCompSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeReviewProcSetting(DashBoardDTO dashBoardDTO) {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeReviewProcSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeReviewProcSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeProdTotalSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeProdTotalSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeProdTotalSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeProdCompSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeProdCompSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeProdCompSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeProdProcSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeProdProcSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeProdProcSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeMoldTotalSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeMoldTotalSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeMoldTotalSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeMoldCompSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeMoldCompSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeMoldCompSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeMoldProcSetting(DashBoardDTO dashBoardDTO) throws Exception {

	Calendar oCalendar = Calendar.getInstance();

	int year = 0;
	year = oCalendar.get(Calendar.YEAR);

	String startDate = String.valueOf(year) + "/01/01";
	String endDate = String.valueOf(year) + "/12/31";
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeMoldProcSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeMoldProcSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());

	for (int index = 0; index < totalProjectReviewData.size(); index++) {

	    long budget1 = 0;
	    long budget2 = 0;
	    long budget3 = 0;
	    long budget4 = 0;
	    long resultsCost1 = 0;
	    long resultsCost2 = 0;
	    long resultsCost3 = 0;
	    long resultsCost4 = 0;

	    int count = 0;

	    long budget = 0; // 예산
	    long executionCost = 0; // 초기집행가
	    long editCost = 0; // 추가집행가
	    long totalExpense = 0; // 총집행가
	    long balanceCost = 0; // 잔액

	    String oid = totalProjectReviewData.get(index).getOid();
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

	    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
	    while (rtCost.hasMoreElements()) {
		Object[] obj = (Object[]) rtCost.nextElement();
		CostInfo costInfo = (CostInfo) obj[0];

		if (costInfo.getOrderInvest() != null) {
		    Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
		    Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
		    Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
		    Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
		    Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

		    if (totalPriceObj != null)
			budget = totalPriceObj.longValue(); // 예산
		    if (initExpenseObj != null)
			executionCost = initExpenseObj.longValue(); // 초기집행가
		    if (addExpenseObj != null)
			editCost = addExpenseObj.longValue(); // 추가집행가
		    if (totalExpenseObj != null)
			totalExpense = totalExpenseObj.longValue(); // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		} else {
		    if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			budget = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 예산

		    if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
		    else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
			executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

		    if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
			editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

		    totalExpense = executionCost + editCost; // 총집행가
		    balanceCost = budget - totalExpense; // 잔액
		}

		budget1 += budget; // 예산
		resultsCost1 += executionCost + editCost; // 실적
	    }

	    totalProjectReviewData.get(index).setBudgetCost(budget1 / 1000);
	    totalProjectReviewData.get(index).setResultCost(resultsCost1 / 1000);
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodIssueProjectSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayCreateDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("createDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodIssueProjectSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodIssueProjectSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl prodTypeQuiltyListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.prodTypeQuiltyListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.prodTypeQuiltyListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl projectItemListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectItemListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectItemListSetting", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl projectTaskTotalSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectTaskTotalSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectTaskTotalSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl projectTaskCompleteSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectTaskCompleteSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectTaskCompleteSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl projectTaskProcessSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectTaskProcessSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectTaskProcessSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl ecnProjectDelayList(DashBoardDTO dashBoardDTO) {
	int year = dashBoardDTO.getYear();
	String startDate = String.valueOf(year) + "-01-01";
	String endDate = String.valueOf(year) + "-12-31";

	// Timestamp sDate = DateUtil.getTimestampFormat(startDate, "yyyy-MM-dd");
	// Timestamp eDate = DateUtil.getTimestampFormat(endDate, "yyyy-MM-dd");
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setEndDate(endDate);

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.ecnProjectDelayListTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.ecnProjectDelayList", dashBoardDTO, dashBoardDTO.getPage() + 1,
	        dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl projectCardQuiltyListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = dao.getTotalRecordCount("mold.projectCardQuiltyListSettingTotal", dashBoardDTO);
	List<DashBoardDTO> totalProjectReviewData = dao.findPaging("mold.projectCardQuiltyListSetting", dashBoardDTO,
	        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamProjectListSetting(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("ExecEndDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanStartDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planStartDate");
		} else if ("displayPlanEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("ExecEndDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate1 = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate1);

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData = null;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("review".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListReviewPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectReviewPlanList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListReviewProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectReviewProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListReviewDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectReviewDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListReviewCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectReviewCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProductPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProductPlanList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProductProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProductProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProductDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProductDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProductCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProductCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListMoldPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectMoldPlanList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListMoldProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectMoldProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListMoldDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectMoldDelayList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListMoldCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectMoldCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectPlanList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectProgressList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectDelayList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeProjectListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeProjectCompletedList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    }
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamTaskListSetting(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	java.util.Calendar cal = java.util.Calendar.getInstance();

	// 현재 년도, 월, 일
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);

	String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	dashBoardDTO.setCurrentDate(currentDate);

	/*
	 * String startDate = dashBoardDTO.getStartDate().substring(0, 4) + "-" + dashBoardDTO.getStartDate().subSequence(4, 6) + "-" +
	 * dashBoardDTO.getStartDate().substring(6, 8); dashBoardDTO.setStartDate(startDate); String endDate =
	 * dashBoardDTO.getEndDate().substring(0, 4) + "-" + dashBoardDTO.getEndDate().subSequence(4, 6) + "-" +
	 * dashBoardDTO.getEndDate().substring(6, 8); dashBoardDTO.setEndDate(endDate);
	 */

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData = null;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	/*if ("review".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListReviewPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskReviewPlanList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListReviewProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskReviewProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListReviewDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskReviewDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListReviewCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskReviewCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else if ("product".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProductPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProductPlanList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProductProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProductProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProductDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProductDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProductCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProductCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else if ("mold".equals(dashBoardDTO.getPjtType())) {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListMoldPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskMoldPlanList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListMoldProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskMoldProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListMoldDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskMoldDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListMoldCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskMoldCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	} else {
	    if ("PLAN".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListPlanTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskPlanList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("DELAY".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskDelayList", dashBoardDTO, dashBoardDTO.getPage() + 1,
		        dashBoardDTO.getFormPage());
	    } else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportTaskCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }
	}*/
	
	
	if ("PLAN".equals(dashBoardDTO.getStatus())) {
	    dashBoardDTO.setStatus("계획");
	} else if ("PROGRESS".equals(dashBoardDTO.getStatus())) {
	    dashBoardDTO.setStatus("진행");
	} else if ("DELAY".equals(dashBoardDTO.getStatus())) {
	    dashBoardDTO.setStatus("지연");
	} else if ("COMPLETED".equals(dashBoardDTO.getStatus())) {
	    dashBoardDTO.setStatus("완료");
	}
	
	if ("review".equals(dashBoardDTO.getPjtType())) {
	    dashBoardDTO.setPjtTypeName("REVIEWPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListPopup_new_total", dashBoardDTO);
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if("product".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("PRODUCTPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListPopup_new_total", dashBoardDTO);
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else if("mold".equals(dashBoardDTO.getPjtType())){
	    dashBoardDTO.setPjtTypeName("MOLDPROJECT");
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskListPopup_new_total", dashBoardDTO);
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());
	} else{
	    totalCount = dao.getTotalRecordCount("mold.teamWorkTimeTaskAllListPopup_new_total", dashBoardDTO);
	    
	    totalProjectReviewData = dao.findPaging("mold.teamWorkTimeTaskAllList_new", dashBoardDTO, dashBoardDTO.getPage() + 1,
		    dashBoardDTO.getFormPage());
	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamIssueTotalListSetting(DashBoardDTO dashBoardDTO) {

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		dashBoardDTO.setSortType("desc");
	    } else {
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData = null;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("Completed".equals(dashBoardDTO.getStatus())) {
	    if ("review".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueReviewListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueCompletedReviewList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("product".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueProductListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueCompletedProductList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("mold".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueMoldListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueCompletedMoldList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueListCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }

	} else if ("InCompleted".equals(dashBoardDTO.getStatus())) {
	    if ("review".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueReviewListInCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueInCompletedReviewList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("product".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueProductListInCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueInCompletedProductList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("mold".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueMoldListInCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueInCompletedMoldList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeIssueListInCompletedTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportIssueInCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }

	}

	if (totalProjectReviewData.size() > 0) {
	    for (int index = 0; index < totalProjectReviewData.size(); index++) {
		String oid = totalProjectReviewData.get(index).getmOid();
		Object obj = CommonUtil.getObject(oid);
		ProjectIssue issue = null;
		ProjectIssueData data = null;
		issue = (ProjectIssue) obj;
		data = new ProjectIssueData(issue);
		totalProjectReviewData.get(index).setAttchCount(data.questionAttacheVec.size());
	    }
	}
	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public PageControl teamQualityTotalListSetting(DashBoardDTO dashBoardDTO) {
	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("requestDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayOccurDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("occurDate");
		} else if ("displayCompleteDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("completeDate");
		} else if ("displayRequestDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("requestDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	int totalCount = 0;
	List<DashBoardDTO> totalProjectReviewData = null;

	if (dashBoardDTO.getvDevType() != null) {
	    String temp[] = dashBoardDTO.getvDevType();
	    String vDevType[] = temp[0].split(",");
	    dashBoardDTO.setvDevType(vDevType);
	}

	if (dashBoardDTO.getvDevPattern() != null) {
	    String temp[] = dashBoardDTO.getvDevPattern();
	    String vDevPattern[] = temp[0].split(",");
	    dashBoardDTO.setvDevPattern(vDevPattern);

	    // 컬럼 | 형태로 바꿔주는 작업
	    String str[] = dashBoardDTO.getvDevPattern();
	    String tempStr = "";
	    if (str != null) {
		for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		    if (str.length == 1) {
			tempStr = str[index];
		    } else {
			if (str.length != index + 1) {
			    tempStr += str[index] + "|";
			} else {
			    tempStr += str[index];
			}
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(tempStr);
	}

	if ("Completed".equals(dashBoardDTO.getStatus())) {
	    if ("review".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityCompletedReviewListTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityCompletedReviewList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("product".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityCompletedProductListTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityCompletedProductList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("mold".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityCompletedMoldListTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityCompletedMoldList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityCompletedListTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityCompletedList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }

	} else if ("Progress".equals(dashBoardDTO.getStatus())) {
	    if ("review".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListProgressReviewTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityProgressReviewList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("product".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListProgressProductTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityProgressProudctList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("mold".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListProgressMoldTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityProgressMoldList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListProgressTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityProgressList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }

	} else if ("Delay".equals(dashBoardDTO.getStatus())) {
	    if ("review".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListDelayReviewTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityDelayReviewList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("product".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListDelayProductTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityDelayProudctList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else if ("mold".equals(dashBoardDTO.getPjtType())) {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListDelayMoldTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityDelayMoldList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    } else {
		totalCount = dao.getTotalRecordCount("mold.teamWorkTimeReportQualityListDelayTotal", dashBoardDTO);
		totalProjectReviewData = dao.findPaging("mold.teamWorkTimeReportQualityDelayList", dashBoardDTO,
		        dashBoardDTO.getPage() + 1, dashBoardDTO.getFormPage());
	    }

	}

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, totalProjectReviewData, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public PageControl getEtcReportList(DashBoardDTO dashBoardDTO) throws Exception {

	int totalCount = 0;
	List<DashBoardDTO> workTimeReportInfo = null;

	if (dashBoardDTO.getSortName() != null && dashBoardDTO.getSortName() != "") {
	    if (dashBoardDTO.getSortName().startsWith("-")) {
		if ("displayPlanEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName().substring(1))) {
		    dashBoardDTO.setSortName("ExecEndDate");
		} else {
		    dashBoardDTO.setSortName(dashBoardDTO.getSortName().substring(1));
		}
		dashBoardDTO.setSortType("desc");
	    } else {
		if ("displayPlanEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("planEndDate");
		} else if ("displayExecEndDate".equals(dashBoardDTO.getSortName())) {
		    dashBoardDTO.setSortName("ExecEndDate");
		}
		dashBoardDTO.setSortType("asc");
	    }
	}

	if (dashBoardDTO.getProcessCode() != null) {
	    String vProcessCode[] = dashBoardDTO.getProcessCode().split(",");
	    dashBoardDTO.setvProcessCode(vProcessCode);
	}
	
	if (dashBoardDTO.getTrustCode() != null) {
	    String vTrustCode[] = dashBoardDTO.getTrustCode().split(",");
	    dashBoardDTO.setvTrustCode(vTrustCode);
	}

	totalCount = dao.getTotalRecordCount("mold.trustReportListTotalCount", dashBoardDTO);
	workTimeReportInfo = dao.findPaging("mold.trustReportList", dashBoardDTO, dashBoardDTO.getPage() + 1,dashBoardDTO.getFormPage());

	PageControl pageControl = new PageControl(dashBoardDTO.getPage() + 1, workTimeReportInfo, dashBoardDTO.getFormPage(),
	        dashBoardDTO.getFormPage(), totalCount);
	return pageControl;
    }

}
