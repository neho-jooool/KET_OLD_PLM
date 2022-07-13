package ext.ket.dashboard.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.beans.ProjectHelper;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.dashboard.entity.DashBoardDTO;
import ext.ket.dashboard.service.DashBoardService;
import ext.ket.dashboard.util.DashBoardUtil;
import ext.ket.dashboard.util.KETDashBoardExcelUtil;
import ext.ket.shared.util.EjsConverUtil;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.session.SessionHelper;

/**
 * 
 * 
 * @클래스명 : DashBoardController
 * @작성자 : jyp
 * @작성일 : 2014. 6. 13.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/dashboard/*")
public class DashBoardController {

    @Inject
    private DashBoardService boardService;
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오후 12:06:31
     * @method createPMScheduleListExcel
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/createPMScheduleListExcel")
    public Map<String, Object> createPMScheduleListExcel(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) {
        
        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            
            reqMap.put("state", req.getParameterValues("state"));
            reqMap.put("process", req.getParameterValues("process"));
            reqMap.put("devPurpose1", req.getParameterValues("devPurpose1"));
            reqMap.put("devPurpose2", req.getParameterValues("devPurpose2"));
            reqMap.put("devDateType", req.getParameterValues("devDateType"));
            
            resMap = DashBoardUtil.manager.createPMScheduleListExcel(reqMap);
            resMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오전 10:43:42
     * @method projectMainSchedule
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception 
     * </pre>
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/projectMainSchedule")
    public Model projectMainSchedule(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
        
        KETMessageService ms = KETMessageService.getMessageService();
        
        List<Map<String, String>> stateList = new ArrayList<Map<String, String>>();
        Vector states = ProjectHelper.getSearchState(ProjectHelper.PRODUCTPROJECT_STATE);
        
        for ( int i = 0; i < states.size(); i++ ) {
            State lcState = (State)states.get(i);
            String key = lcState.toString();
            String display = lcState.getDisplay(ms.getLocale());
            
            if ( key.equals("UNDERREVIEW") ) {
                display = ms.getString("e3ps.message.ket_message", "00786");    //결재중
            }
            
            Map<String, String> stateMap = new HashMap<String, String>();
            
            stateMap.put("key", key);
            stateMap.put("display", display);
            stateList.add(stateMap);
        }
        
        Map<String, String> stateMap = new HashMap<String, String>();
        stateMap.put("key", "delay");
        stateMap.put("display", ms.getString("e3ps.message.ket_message", "02703")); //지연
        stateList.add(stateMap);
        
        model.addAttribute("stateList", stateList);
        
        FileContentUtil.manager.saveDownloadHistory("프로젝트 주요일정", "");
        
        return model;
    }
    
    @ResponseBody
    @RequestMapping("/findPMSchedulePagingList")
    public Map<String, Object> findPMSchedulePagingList(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) throws Exception {
        
        reqMap.put("state", req.getParameterValues("state"));
        reqMap.put("process", req.getParameterValues("process"));
        reqMap.put("devPurpose1", req.getParameterValues("devPurpose1"));
        reqMap.put("devPurpose2", req.getParameterValues("devPurpose2"));
        reqMap.put("devDateType", req.getParameterValues("devDateType"));
        
        return DashBoardUtil.manager.findPMSchedulePagingList(reqMap);
    }

    @RequestMapping("/moldGanttChart")
    public void moldGanttChart(DashBoardDTO dashBoardDTO) throws Exception {

    }

    @RequestMapping("/changeGanttChartData")
    @ResponseBody
    public Map<String, Object> changeGanttChartData(DashBoardDTO dashBoardDTO) throws Exception {
	return boardService.ganttChart(dashBoardDTO);
    }

    @RequestMapping("/mainScheduleProgressPjtCount")
    public void mainScheduleProgressPjtCount(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("searchDate", dashBoardDTO.getSearchDate());
	model.addAttribute("mainProgressData", boardService.mainScheduleProgressPjtCount(dashBoardDTO));
    }

    @RequestMapping("/moldMultiColumChart")
    public void moldMultiColumChart() throws Exception {

    }

    @RequestMapping("/moldMultiColumChart1Popup")
    public void moldMultiColumChart1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("searchDate", dashBoardDTO.getSearchDate());
	model.addAttribute("division", dashBoardDTO.getDivision());
    }

    @RequestMapping("/changeMultiColumChartData3")
    @ResponseBody
    public Map<String, Object> changeMultiColumChartData3(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.multiColumChart3(dashBoardDTO);
    }

    @RequestMapping("/changeMultiColumChartData")
    @ResponseBody
    public Map<String, Object> changeMultiColumChartData(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.multiColumChart(dashBoardDTO);
    }

    @RequestMapping("/changeStackColumChartData")
    @ResponseBody
    public Map<String, Object> changeStackColumChartData(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.stackColumChart(dashBoardDTO);
    }

    @RequestMapping("/changeStackColumChartData1")
    @ResponseBody
    public Map<String, Object> changeStackColumChartData1(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.stackColumChart1(dashBoardDTO);
    }

    @RequestMapping("/moldDoughnutChart")
    public String moldDoughnutChart(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	// model.addAttribute("searchDate", dashBoardDTO.getSearchDate());
	return "dashboard/moldDoughnutChartPopup";
    }

    @RequestMapping("/moldManufactureList")
    public void moldManufactureList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("moldManufactureData", boardService.getMoldManufactureList(dashBoardDTO, model));
    }

    @RequestMapping("/changeDoughnutChartData")
    @ResponseBody
    public Map<String, Object> changeDoughnutChartData(DashBoardDTO boardDTO) throws Exception {

	return boardService.doughnutChart(boardDTO);
    }

    @RequestMapping("/changeDoughnutChartData1")
    @ResponseBody
    public Map<String, Object> changeDoughnutChartData1(DashBoardDTO boardDTO) throws Exception {

	return boardService.doughnutChart1(boardDTO);
    }

    @RequestMapping("/changeMultiColumChartData1")
    @ResponseBody
    public Map<String, Object> changeMultiColumChartData1(DashBoardDTO dashBoardDTO) throws Exception {
	return boardService.multiColumChart1(dashBoardDTO);
    }

    @RequestMapping("/changeMultiColumChartData2")
    @ResponseBody
    public Map<String, Object> changeMultiColumChartData2(DashBoardDTO dashBoardDTO) throws Exception {
	return boardService.multiColumChart2(dashBoardDTO);
    }

    @RequestMapping("/productPjtOverallStatus")
    public void productPjtOverallStatus(Model model) throws Exception {
	model.addAttribute("productPjtOverallData", boardService.productPjtOverallStatus());
    }

    @RequestMapping("/moldMakeSituationPopup")
    public void moldMakeSituation(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("yearSetting", dashBoardDTO.getYearSetting());
	model.addAttribute("monthSetting", dashBoardDTO.getMonthSetting());
	model.addAttribute("divisionType", dashBoardDTO.getDivision());
	model.addAttribute("moldType", dashBoardDTO.getMoldType());
	model.addAttribute("completeType", dashBoardDTO.getUseCompleteType());
	model.addAttribute("stepMoldData", boardService.moldMakeSituation(dashBoardDTO));

    }

    @RequestMapping("/moldMakeSituationStatePopup")
    public void moldMakeSituationStatePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/excelDown")
    public void excelDown(HttpServletResponse response) throws Exception {

	KETDashBoardExcelUtil util = new KETDashBoardExcelUtil();

	ArrayList excelList = new ArrayList();
	ArrayList keyList = new ArrayList();
	keyList.add(new String("srcpartNo"));
	keyList.add(new String("srclvl"));
	keyList.add(new String("srcpartName"));
	keyList.add(new String("srcqty"));
	keyList.add(new String("srcunit"));
	keyList.add(new String("srcrev"));
	keyList.add(new String("srcreftop"));
	keyList.add(new String("srcrefbtm"));
	keyList.add(new String("srcmaterial"));
	keyList.add(new String("srchardnessFrom"));
	keyList.add(new String("srchardnessTo"));
	keyList.add(new String("srcdesignDate"));
	keyList.add(new String("srcparentNo"));
	keyList.add(new String("srcpver"));

	keyList.add(new String("despartNo"));
	keyList.add(new String("deslvl"));
	keyList.add(new String("despartName"));
	keyList.add(new String("desqty"));
	keyList.add(new String("desunit"));
	keyList.add(new String("desrev"));
	keyList.add(new String("desreftop"));
	keyList.add(new String("desrefbtm"));
	keyList.add(new String("desmaterial"));
	keyList.add(new String("deshardnessFrom"));
	keyList.add(new String("deshardnessTo"));
	keyList.add(new String("desdesignDate"));
	keyList.add(new String("desparentNo"));
	keyList.add(new String("despver"));

	Hashtable hdata = new Hashtable();

	hdata.put("srcpartNo", "부품번호");
	hdata.put("srclvl", "레벨");
	hdata.put("srcpartName", "부품명");
	hdata.put("srcqty", "수량");
	hdata.put("srcunit", "기본단위");
	hdata.put("srcrev", "버전");
	hdata.put("srcreftop", "Ref No (TOP)");
	hdata.put("srcrefbtm", "Ref No (BOTTOM)");
	hdata.put("srcmaterial", "재질");
	hdata.put("srchardnessFrom", "경도(From)");
	hdata.put("srchardnessTo", "경도(To)");
	hdata.put("srcdesignDate", "설계일자");
	hdata.put("srcparentNo", "모부품");
	hdata.put("srcpver", "모버전");

	hdata.put("despartNo", "부품번호");
	hdata.put("deslvl", "레벨");
	hdata.put("despartName", "부품명");
	hdata.put("desqty", "수량");
	hdata.put("desunit", "기본단위");
	hdata.put("desrev", "버전");
	hdata.put("desreftop", "Ref No (TOP)");
	hdata.put("desrefbtm", "Ref No (BOTTOM)");
	hdata.put("desmaterial", "재질");
	hdata.put("deshardnessFrom", "경도(From)");
	hdata.put("deshardnessTo", "경도(To)");
	hdata.put("desdesignDate", "설계일자");
	hdata.put("desparentNo", "모부품");
	hdata.put("despver", "모버전");

	excelList.add(hdata);
	String fileName = "newData";
	util.createExcelFile(keyList, excelList, fileName, response);

    }

    @RequestMapping("/projectReportSetting")
    public String projectReportSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/projectReportPopup";
    }

    @RequestMapping("/projectReport")
    @ResponseBody
    public Map<String, Object> projectReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.getProjectReport(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectDelayReportSetting")
    public String projectDelayReportSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/projectDelayReportPopup";
    }

    @RequestMapping("/projectDelayReport")
    @ResponseBody
    public Map<String, Object> projectDelayReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectDelayReport(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectTaskReportSetting")
    public String projectTaskReportSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/projectTaskReportPopup";
    }

    @RequestMapping("/projectTaskReport")
    @ResponseBody
    public Map<String, Object> projectTaskReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	// model.addAttribute("taskReportInfo", boardService.getProjectTaskReport(dashBoardDTO));
	// model.addAttribute("debuging", dashBoardDTO.getDebuging());
	PageControl pageControl = boardService.getProjectTaskReport(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectTaskDelayReportSetting")
    public String projectTaskDelayReportSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/projectTaskDelayReportPopup";
    }

    @RequestMapping("/projectTaskDelayReport")
    @ResponseBody
    public Map<String, Object> projectTaskDelayReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	// model.addAttribute("taskReportInfo", boardService.getProjectTaskReport(dashBoardDTO));
	// model.addAttribute("debuging", dashBoardDTO.getDebuging());
	PageControl pageControl = boardService.projectTaskDelayReport(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectIssueReportSetting")
    public String projectIssueReportSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/projectIssueReportPopup";
    }

    @RequestMapping("/projectIssueReport")
    @ResponseBody
    public Map<String, Object> projectIssueReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	// model.addAttribute("issueReportInfo", boardService.getProjectIssueReport(dashBoardDTO));
	PageControl pageControl = boardService.getProjectIssueReport(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/overallStatus")
    public void overallStatus(DashBoardDTO dashBoardDTO, Model model) throws Exception {

	String divisionCode = "";
	if (CommonUtil.isMember("전자사업부")) {
	    divisionCode = "eletron	";
	} else if (CommonUtil.isMember("자동차사업부")) {
	    divisionCode = "car";
	}
	dashBoardDTO.setDivision(divisionCode);

	// dashBoardDTO.setStartDate("2014-01-01");
	// dashBoardDTO.setEndDate("2014-12-31");
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	// model.addAttribute("delayCount", boardService.getEcnDelayCount(dashBoardDTO));
	// model.addAttribute("typeByDevMakeReportList", boardService.getTypeByDevMakeReport(dashBoardDTO));
	// model.addAttribute("carTypeByDevMakeReportList", boardService.getCarTypeByDevMakeReportList(dashBoardDTO));
    }

    @RequestMapping("/typeByDevMakeReportData")
    public void typeByDevMakeReportData(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("typeByDevMakeReportList", boardService.getTypeByDevMakeReport(dashBoardDTO, model));
    }

    @RequestMapping("/carTypeByDevMakeReportData")
    public void carTypeByDevMakeReportData(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("carTypeByDevMakeReportList", boardService.getCarTypeByDevMakeReportList(dashBoardDTO));
    }

    @RequestMapping("/prodTypeByDevMakeReprtData")
    public void prodTypeByDevMakeReprtData(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("prodTypeByDevMakeReprtList", boardService.getProdTypeByDevMakeReprtList(dashBoardDTO));
    }

    @RequestMapping("/electronStackColumChart")
    public void electronStackColumChart() throws Exception {

    }

    @RequestMapping("/changeStackColumOverallStatusChartData")
    @ResponseBody
    public Map<String, Object> changeStackColumOverallStatusChartData(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.electornStackColum(dashBoardDTO);
    }

    @RequestMapping("/changeStackColumOverallStatusChartData_")
    @ResponseBody
    public Map<String, Object> changeStackColumOverallStatusChartData_(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.electornStackColum_(dashBoardDTO);
    }

    @RequestMapping("/changeStackColumOverallStatusChartData1")
    @ResponseBody
    public Map<String, Object> changeStackColumOverallStatusChartData1(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.electornStackColum1(dashBoardDTO);
    }

    @RequestMapping("/changeStackColumOverallStatusChartData1_")
    @ResponseBody
    public Map<String, Object> changeStackColumOverallStatusChartData1_(DashBoardDTO dashBoardDTO) throws Exception {

	return boardService.electornStackColum1_(dashBoardDTO);
    }

    /*
     * @RequestMapping("/electronChangeStackColumChartData2")
     * 
     * @ResponseBody public Map<String, Object> electronChangeStackColumChartData2(DashBoardDTO dashBoardDTO) throws Exception {
     * 
     * return boardService.electornStackColum2(dashBoardDTO); }
     */

    @RequestMapping("/workTimeTeamReport")
    public void workTimeTeamReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String projectType = "";
	if (dashBoardDTO.getPjtType() == null) {
	    projectType = "PRODUCTPROJECT";
	} else {
	    projectType = dashBoardDTO.getPjtType();
	}

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Calendar c = Calendar.getInstance();
	String currentDate = formatter.format(c.getTime());
	c.add(Calendar.DATE, -10);
	// c.add(Calendar.YEAR, -100);

	String startDate = formatter.format(c.getTime());
	dashBoardDTO.setStartDate(startDate);
	dashBoardDTO.setCurrentDate(currentDate);
	c.add(Calendar.DATE, 20);
	// c.add(Calendar.YEAR, 200);
	String endDate = formatter.format(c.getTime());
	dashBoardDTO.setEndDate(endDate);
	
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	model.addAttribute("pjtType", projectType);
    }

    @RequestMapping("/workTimeTeamReportList")
    @ResponseBody
    public Map<String, Object> workTimeTeamReportList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.getWorkTimeTeamReportList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/workTimeReport")
    public void workTimeReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String projectType = "";
	if (dashBoardDTO.getMoldType() == null) {
	    projectType = "review";
	} else {
	    projectType = dashBoardDTO.getMoldType();
	}

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Calendar c = Calendar.getInstance();
	String endDate = formatter.format(c.getTime());
	dashBoardDTO.setEndDate(endDate);
	c.add(Calendar.MONTH, -3);
	String startDate = formatter.format(c.getTime());
	dashBoardDTO.setStartDate(startDate);
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	model.addAttribute("moldType", projectType);
    }

    @RequestMapping("/workTimeReportList")
    @ResponseBody
    public Map<String, Object> workTimeReportList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.getWorkTimeReportList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReort")
    public void teamWorkTimeReort(DashBoardDTO dashBoardDTO, Model model, String type) throws Exception {
	
	if(StringUtils.isNotEmpty(type) && "mail".equals(type)){//메일 링크 찍어서 들어올때
	    dashBoardDTO.setPjtType("product");
	}
	
	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	
	
	String departName = DepartmentHelper.manager.getDepartmentName(user);
	String deptCode = "";
	// 팀이 있는 경우 조회 아니면 조회안됨
	if (DepartmentHelper.manager.getDepartment(user) != null) {
	    deptCode = StringUtil.checkNull(DepartmentHelper.manager.getDepartment(user).getCode());
	}
	dashBoardDTO.setDepartName(departName);
	dashBoardDTO.setDeptCode(deptCode);
	
	PeopleData peo = new PeopleData(user);
	
	String dutyCode = peo.dutycode;
	String dutyName = peo.duty;
	
	if("사장".equals(dutyName) || "4_A30".equals(dutyCode)){
	    dashBoardDTO.setDeptCode("10190");
	    dashBoardDTO.setDepartName("한국단자공업㈜");
	}

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Calendar c = Calendar.getInstance();
	// c.add(Calendar.DATE, (Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK))); // 이번주 월요일 구하기
	c.add(Calendar.DATE, -10);

	String startDate = formatter.format(c.getTime());
	dashBoardDTO.setStartDate(startDate);

	// c.add(Calendar.DATE, (Calendar.FRIDAY - c.get(Calendar.DAY_OF_WEEK))); 이번주 금요일 구하기
	c.add(Calendar.DATE, 20);
	// c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

	String endDate = formatter.format(c.getTime());
	dashBoardDTO.setEndDate(endDate);

	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/changePieChartData")
    @ResponseBody
    public Map<String, Object> changePieChartData(DashBoardDTO dashBoardDTO, Model model) throws Exception {

	return boardService.getChangePieChartData(dashBoardDTO);
    }

    @RequestMapping("/changePieChartData1")
    @ResponseBody
    public Map<String, Object> changePieChartData1(DashBoardDTO dashBoardDTO, Model model) throws Exception {

	return boardService.getChangePieChartData1(dashBoardDTO);
    }

    @RequestMapping("/changePieChartData2")
    @ResponseBody
    public Map<String, Object> changePieChartData2(DashBoardDTO dashBoardDTO, Model model) throws Exception {

	return boardService.getChangePieChartData2(dashBoardDTO);
    }

    @RequestMapping("/changePieChartData3")
    @ResponseBody
    public Map<String, Object> changePieChartData3(DashBoardDTO dashBoardDTO, Model model) throws Exception {

	return boardService.getChangePieChartData3(dashBoardDTO);
    }

    @RequestMapping("/teamWorkTimeReportProjectList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReportTaskList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportTaskList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportTaskList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReportIssueList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportIssueList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportIssueList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReportQualityList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportQualityList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportQualityList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReportPeopleList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportPeopleList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportPeopleList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamWorkTimeReportTeamList")
    @ResponseBody
    public Map<String, Object> teamWorkTimeReportTeamList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamWorkTimeReportTeamList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectCard")
    public String projectCard(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("projectBasicInfoData", boardService.getProjectCard(dashBoardDTO, model));
	model.addAttribute("carScheduleData", boardService.getCarSchedule(dashBoardDTO));
	model.addAttribute("programCustomScheduleData", boardService.getProgramCustomSchdule(dashBoardDTO));
	model.addAttribute("mileStoneData", boardService.getMileStone(dashBoardDTO));
	model.addAttribute("taskReportData", boardService.getTaskReport(dashBoardDTO));

	model.addAttribute("produceAndMakeInfo", boardService.getProduceAndMakeData(dashBoardDTO, model));
	model.addAttribute("costData", boardService.getCostData(dashBoardDTO));
	model.addAttribute("issueData", boardService.getIssueData(dashBoardDTO));
	model.addAttribute("productInfoData", boardService.getProductInfoData(dashBoardDTO, model));
	model.addAttribute("ecoCount", boardService.getEcoInfoData(dashBoardDTO, model));
	model.addAttribute("outputInfoData", boardService.getOutputInfoData(dashBoardDTO));
	model.addAttribute("projectGateData", boardService.getProjectGateData(dashBoardDTO));
	return "dashboard/projectCardPopup";
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeMultiColumOverallStatusChartData(boardDTO);
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData_")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData_(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeMultiColumOverallStatusChartData_(boardDTO);
    }

    @RequestMapping("/changeStackColumOverallStatusChartData2")
    @ResponseBody
    Map<String, Object> changeStackColumOverallStatusChartData2(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeStackColumOverallStatusChartData2(boardDTO);
    }

    @RequestMapping("/changeStackColumOverallStatusChartData2_")
    @ResponseBody
    Map<String, Object> changeStackColumOverallStatusChartData2_(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeStackColumOverallStatusChartData2_(boardDTO);
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData1")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData1(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeMultiColumOverallStatusChartData1(boardDTO);
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData1_")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData1_(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeMultiColumOverallStatusChartData1_(boardDTO);
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData2")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData2(DashBoardDTO boardDTO) throws Exception {

	return boardService.getChangeMultiColumOverallStatusChartData2(boardDTO);
    }

    @RequestMapping("/changeMultiColumOverallStatusChartData3")
    @ResponseBody
    public Map<String, Object> changeMultiColumOverallStatusChartData3(DashBoardDTO boardDTO, Model model) throws Exception {
	// model.addAttribute("delayCount", boardService.getEcnDelayCount(boardDTO));
	return boardService.getChangeMultiColumOverallStatusChartData3(boardDTO, model);
    }

    @RequestMapping("/typeByTotalProjectReviewSetting")
    public String typeByTotalProjectReviewSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectReviewPopup";
    }

    @RequestMapping("/typeByTotalProjectReview")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectReview(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectReview(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectReviewSetting")
    public String typeByCompleteProjectReviewSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectReviewPopup";
    }

    @RequestMapping("/typeByCompleteProjectReview")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectReview(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectReview(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeBypProcessProjectReviewSetting")
    public String typeBypProcessProjectReviewSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectReviewPopup";
    }

    @RequestMapping("/typeByProcessProjectReview")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectReview(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectReview(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByTotalProjectProductSetting")
    public String typeByTotalProjectProductSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectProductPopup";
    }

    @RequestMapping("/typeByTotalProjectProduct")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectProduct(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectProduct(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectProductSetting")
    public String typeByCompleteProjectProductSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectProductPopup";
    }

    @RequestMapping("/typeByCompleteProjectProduct")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectProduct(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectProduct(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByProcessProjectProductSetting")
    public String typeByProcessProjectProductSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectProductPopup";
    }

    @RequestMapping("/typeByProcessProjectProduct")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectProduct(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectProduct(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByTotalProjectMoldSetting")
    public String typeByTotalProjectMoldSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectMoldPopup";
    }

    @RequestMapping("/typeByTotalProjectMold")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectMold(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectMold(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectMoldSetting")
    public String typeByCompleteProjectMoldSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectMoldPopup";
    }

    @RequestMapping("/typeByCompleteProjectMold")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectMold(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectMold(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByProcessProjectMoldSetting")
    public String typeByProcessProjectMoldSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectMoldPopup";
    }

    @RequestMapping("/typeByProcessProjectMold")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectMold(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectMold(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByTotalProjectTotalSetting")
    public String typeByTotalProjectTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectTotalPopup";
    }

    @RequestMapping("/typeByTotalProjectTotal")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectTotal(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectTotal(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByTotalProjectTotalSetting1")
    public String typeByTotalProjectTotalSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectTotal1Popup";
    }

    @RequestMapping("/typeByTotalProjectTotal1")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectTotal1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectTotal1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByTotalProjectTotalSetting2")
    public String typeByTotalProjectTotalSetting2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByTotalProjectTotal2Popup";
    }

    @RequestMapping("/typeByTotalProjectTotal2")
    @ResponseBody
    public Map<String, Object> typeByTotalProjectTotal2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByTotalProjectTotal2(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectTotalSetting")
    public String typeByCompleteProjectTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectTotalPopup";
    }

    @RequestMapping("/typeByCompleteProjectTotal")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectTotal(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectTotal(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectTotalSetting1")
    public String typeByCompleteProjectTotalSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectTotal1Popup";
    }

    @RequestMapping("/typeByCompleteProjectTotal1")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectTotal1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectTotal1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByCompleteProjectTotalSetting2")
    public String typeByCompleteProjectTotalSetting2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByCompleteProjectTotal2Popup";
    }

    @RequestMapping("/typeByCompleteProjectTotal2")
    @ResponseBody
    public Map<String, Object> typeByCompleteProjectTotal2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByCompleteProjectTotal2(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByProcessProjectTotalSetting")
    public String typeByProcessProjectTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectTotalPopup";
    }

    @RequestMapping("/typeByProcessProjectTotal")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectTotal(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectTotal(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByProcessProjectTotalSetting1")
    public String typeByProcessProjectTotalSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectTotal1Popup";
    }

    @RequestMapping("/typeByProcessProjectTotal1")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectTotal1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectTotal1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/typeByProcessProjectTotalSetting2")
    public String typeByProcessProjectTotalSetting2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/typeByProcessProjectTotal2Popup";
    }

    @RequestMapping("/typeByProcessProjectTotal2")
    @ResponseBody
    public Map<String, Object> typeByProcessProjectTotal2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.typeByProcessProjectTotal2(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/newAndoldSetting")
    public String newAndoldSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/newAndoldSettingPopup";
    }

    @RequestMapping("/newAndoldProjectList")
    @ResponseBody
    Map<String, Object> newAndoldProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.newAndoldProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/newAndoldSetting1")
    public String newAndoldSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/newAndoldSetting1Popup";
    }

    @RequestMapping("/newAndoldProjectList1")
    @ResponseBody
    Map<String, Object> newAndoldProjectList1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.newAndoldProjectList1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/completeAndprocessSetting")
    public String completeAndprocessSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/completeAndprocessSettingPopup";
    }

    @RequestMapping("/completeAndprocessProjectList")
    @ResponseBody
    Map<String, Object> completeAndprocessProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.completeAndprocessProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/completeAndprocessSetting1")
    public String completeAndprocessSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/completeAndprocessSetting1Popup";
    }

    @RequestMapping("/completeAndprocessProjectList1")
    @ResponseBody
    Map<String, Object> completeAndprocessProjectList1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.completeAndprocessProjectList1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/completeAndnotCompleteSetting")
    public String completeAndnotCompleteSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/completeAndnotCompleteSettingPopup";
    }

    @RequestMapping("/completeAndnotcompleteProjectList")
    @ResponseBody
    Map<String, Object> completeAndnotcompleteProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.completeAndnotcompleteProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/completeAndnotCompleteSetting1")
    public String completeAndnotCompleteSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/completeAndnotCompleteSetting1Popup";
    }

    @RequestMapping("/completeAndnotcompleteProjectList1")
    @ResponseBody
    Map<String, Object> completeAndnotcompleteProjectList1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.completeAndnotcompleteProjectList1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/afterAndtwoafterSetting")
    public String afterAndtwoafterSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/afterAndtwoafterSettingPopup";
    }

    @RequestMapping("/afterAndtwoafterProjectList")
    @ResponseBody
    Map<String, Object> afterAndtwoafterProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.afterAndtwoafterProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/lastYearMoldAndPressSetting")
    public String lastYearMoldAndPressSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/lastYearMoldAndPressSettingPopup";
    }

    @RequestMapping("/lastYearMoldProjectProjectList")
    @ResponseBody
    Map<String, Object> lastYearMoldProjectProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.lastYearMoldProjectProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/thisYearMoldAndPressSetting")
    public String thisYearMoldAndPressSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/thisYearMoldAndPressSettingPopup";
    }

    @RequestMapping("/thisYearMoldProjectProjectList")
    @ResponseBody
    Map<String, Object> thisYearMoldProjectProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.thisYearMoldProjectProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/makingDataTypeSetting")
    String makingDataTypeSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/makingDataTypeSettingPopup";
    }

    @RequestMapping("/makingTypeProjectList")
    @ResponseBody
    Map<String, Object> makingTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.makingTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/startMassTypeSetting")
    String startMassTypeSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/startMassTypeSettingPopup";
    }

    @RequestMapping("/startMassTypeProjectList")
    @ResponseBody
    Map<String, Object> startMassTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.startMassTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldPressTypeSetting")
    String moldPressTypeSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/moldPressTypeSettingPopup";
    }

    @RequestMapping("/moldPressTypeProjectList")
    @ResponseBody
    Map<String, Object> moldPressTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldPressTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/completeProcessTypeSetting")
    String completeProcessTypeSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/completeProcessTypeSettingPopup";
    }

    @RequestMapping("/completeProcessTypeProjectList")
    @ResponseBody
    Map<String, Object> completeProcessTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.completeProcessTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/manufactureProjectListSettingPopup")
    public void manufactureProjectListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("manufactureProjectList", boardService.getMoldManufactureProjectList(dashBoardDTO, model));
    }

    @RequestMapping("/moldTypeProjectListSettingPopup")
    public void moldTypeProjectListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldTypeProjectList")
    @ResponseBody
    Map<String, Object> moldTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldTypeDebugProjectListSettingPopup")
    public void moldTypeDebugProjectListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldDebugTypeProjectList")
    @ResponseBody
    Map<String, Object> moldDebugTypeProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldDebugTypeProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldIssueProjectListSettingPopup")
    public void moldIssueProjectListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldIssueProjectList")
    @ResponseBody
    Map<String, Object> moldIssueProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldIssueProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldProjectTotalStepListSettingPopup")
    public void moldProjectTotalStepListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldProjectTotalStepList")
    @ResponseBody
    Map<String, Object> moldProjectTotalStepList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldProjectTotalStepList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/programCard")
    public String programCard(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("programBasicInfoData", boardService.getProgramBasicInfo(dashBoardDTO, model));
	model.addAttribute("carNameScheduleInfoData", boardService.getCarNameScheduleInfo(dashBoardDTO));
	model.addAttribute("customScheduleInfoData", boardService.getCustomScheduleInfo(dashBoardDTO));
	model.addAttribute("productAndmakeInfoData", boardService.getProductAndMakeInfo(dashBoardDTO, model));
	model.addAttribute("programByProjectListInfoData", boardService.getProgramByProjectListInfo(dashBoardDTO));
	model.addAttribute("programMoldReportData", boardService.getProgramMoldReportInfo(dashBoardDTO, model));
	return "dashboard/programCardPopup";
    }

    @RequestMapping("/carTypeCard")
    public void carTypeCard(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	model.addAttribute("carTypeBasicInfoData", boardService.getCarTypeBasicInfo(dashBoardDTO, model));
	model.addAttribute("carTypeScheduleInfoData", boardService.getCarTypeScheduleInfo(dashBoardDTO));
	model.addAttribute("devTypeInfoData", boardService.getDevTypeInfo(dashBoardDTO, model));
	model.addAttribute("moldCategoryInfoData", boardService.getMoldCategoryInfo(dashBoardDTO, model));
	model.addAttribute("carTypeProjectListInfoData", boardService.getCarTypeProjectListInfo(dashBoardDTO));
    }

    @RequestMapping("/carTypeCardPopup")
    public void carTypeCardPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carDivisionProjectReportPopup")
    @ResponseBody
    Map<String, Object> carDivisionProjectReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.getCarDivisionProjectReport(dashBoardDTO, model);
	// model.addAttribute("carDivisionProjectData", boardService.getCarDivisionProjectReport(dashBoardDTO, model));
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carDivisionProjectPopup")
    public void carDivisionProjectReportPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/productCompleteProjectSettingPopup")
    public void productCompleteProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productCompleteProjectList")
    @ResponseBody
    Map<String, Object> productCompleteProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productCompleteProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productProcessProjectSettingPopup")
    public void productProcessProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productDelayProjectSettingPopup")
    public void productDelayProjectSettingPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productProcessProjectList")
    @ResponseBody
    Map<String, Object> productProcessProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productProcessProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productDelayProjecttList")
    @ResponseBody
    Map<String, Object> productDelayProjecttList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productDelayProjecttList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldCompleteProjectSettingPopup")
    public void moldCompleteProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldCompleteProjectList")
    @ResponseBody
    Map<String, Object> moldCompleteProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldCompleteProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldProcessProjectSettingPopup")
    public void moldProcessProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldProcessProjectList")
    @ResponseBody
    Map<String, Object> moldProcessProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldProcessProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldDelayProjectSettingPopup")
    public void moldDelayProjectSettingPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldDelayProjectList")
    @ResponseBody
    Map<String, Object> moldDelayProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldDelayProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/issueProjectSettingPopup")
    public void issueProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/issueProjectList")
    @ResponseBody
    Map<String, Object> issueProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.issueProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/msStackedColumn3D")
    public void msStackedColumn3D() throws Exception {

    }

    @RequestMapping("/msStackedColumn2DSetting")
    @ResponseBody
    Map<String, Object> msStackedColumn2DSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	return boardService.msStackedColumn2DSetting(dashBoardDTO);
    }

    @RequestMapping("/msStackedColumn2DSetting1")
    @ResponseBody
    Map<String, Object> msStackedColumn2DSetting1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	return boardService.msStackedColumn2DSetting1(dashBoardDTO);
    }

    @RequestMapping("/msStackedColumn2DSetting2")
    @ResponseBody
    Map<String, Object> msStackedColumn2DSetting2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	return boardService.msStackedColumn2DSetting2(dashBoardDTO);
    }

    @RequestMapping("/outputTypeListPopup")
    public void outputTypeListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("outputData", boardService.getOutputTypeList(dashBoardDTO));
    }

    @RequestMapping("/projectCardIssuePopup")
    public void projectCardIssuePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectCardIssueList")
    @ResponseBody
    Map<String, Object> projectCardIssueList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectCardIssueList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectCardQulityPopup")
    public void projectCardQulityPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectCardQuiltyListSetting")
    @ResponseBody
    Map<String, Object> projectCardQuiltyListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectCardQuiltyListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/programProcessReport")
    public void programProcessReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	if (dashBoardDTO.getType() == null || dashBoardDTO.getType() == "") {
	    dashBoardDTO.setType("CA");
	}
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/programProcessReportList")
    @ResponseBody
    Map<String, Object> programProcessReportList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.programProcessReportList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectListPopup")
    public void projectListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectPopupListSetting")
    @ResponseBody
    Map<String, Object> projectPopupListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectPopupListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/assemblyNormalPopup")
    public void assemblyNormalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/assemblyNormalProjectList")
    @ResponseBody
    Map<String, Object> assemblyNormalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.assemblyNormalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/singleNormalPopup")
    public void singleNormalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/singleNormalProjectList")
    @ResponseBody
    Map<String, Object> singleNormalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.singleNormalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/singleSystemPopup")
    public void singleSystemPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/singleSystemProjectList")
    @ResponseBody
    Map<String, Object> singleSystemProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.singleSystemProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/singleTotalPopup")
    public void singleTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/singleTotalProjectList")
    @ResponseBody
    Map<String, Object> singleTotalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.singleTotalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/goodsNormalPopup")
    public void goodsNormalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/goodsNormalProjectList")
    @ResponseBody
    Map<String, Object> goodsNormalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.goodsNormalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/goodsSystemPopup")
    public void goodsSystemPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/goodsSystemProjectList")
    @ResponseBody
    Map<String, Object> goodsSystemProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.goodsSystemProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/goodsTotalPopup")
    public void goodsTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/goodsTotalProjectList")
    @ResponseBody
    Map<String, Object> goodsTotalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.goodsTotalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodDevCostReportPopup")
    public void prodDevCostReportPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodDevCostReportProjectList")
    @ResponseBody
    Map<String, Object> prodDevCostReportProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodDevCostReportProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/ecrProjectListPopup")
    public void ecrProjectListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/ecrProjectList")
    @ResponseBody
    Map<String, Object> ecrProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.ecrProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/ecoProjectListPopup")
    public void ecoProjectListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/ecoProjectList")
    @ResponseBody
    Map<String, Object> ecoProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.ecoProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/ecnProjectListPopup")
    public void ecnProjectListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/ecnProjectListDelayPopup")
    public void ecnProjectListDelayPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/ecnProjectDelayList")
    @ResponseBody
    Map<String, Object> ecnProjectDelayList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.ecnProjectDelayList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/ecnProjectList")
    @ResponseBody
    Map<String, Object> ecnProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.ecnProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/currentSettingPopup")
    public void currentSettingPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/currentSettingList")
    @ResponseBody
    Map<String, Object> currentSettingList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.currentSettingList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/currentSetting1Popup")
    public void currentSetting1Popup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/currentSettingList1")
    @ResponseBody
    Map<String, Object> currentSettingList1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.currentSettingList1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessPlanPopup")
    public void teamTaskProcessPlanPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessTeamPlanPopup")
    public void teamTaskProcessTeamPlanPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessPlanDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessPlanDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessPlanDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessTeamPlanDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessTeamPlanDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessTeamPlanDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessPopup")
    public void teamTaskProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessTeamProcessPopup")
    public void teamTaskProcessTeamProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessTeamDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessTeamDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessTeamDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessDelayPopup")
    public void teamTaskProcessDelayPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessTeamDelayPopup")
    public void teamTaskProcessTeamDelayPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessDelayDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessDelayDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessDelayDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessDelayTeamDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessDelayTeamDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessDelayTeamDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessCompletePopup")
    public void teamTaskProcessCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessTeamCompletePopup")
    public void teamTaskProcessTeamCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamTaskProcessCompleteDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessCompleteDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessCompleteDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskProcessCompleteTeamDataList")
    @ResponseBody
    Map<String, Object> teamTaskProcessCompleteTeamDataList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskProcessCompleteTeamDataList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldTypeListPopup")
    public void moldTypeListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldTypeListSetting")
    @ResponseBody
    Map<String, Object> moldTypeListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldTypeListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carTypePopup")
    public void carTypePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carTypeAssemProjectList")
    @ResponseBody
    Map<String, Object> carTypeAssemProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.carTypeAssemProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carType1Popup")
    public void carTypePopup1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carTypeSingleProjectList")
    @ResponseBody
    Map<String, Object> carTypeSingleProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.carTypeSingleProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carType2Popup")
    public void carTypePopup2(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldCategoryDataProjectList")
    @ResponseBody
    Map<String, Object> moldCategoryDataProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldCategoryDataProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carType3Popup")
    public void carTypePopup3(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldCategoryDataProjectList1")
    @ResponseBody
    Map<String, Object> moldCategoryDataProjectList1(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldCategoryDataProjectList1(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamIssueListPopup")
    public void teamIssueListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamIssueTeamListPopup")
    public void teamIssueTeamListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamIssueListSetting")
    @ResponseBody
    Map<String, Object> teamIssueListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamIssueListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamIssueTeamListSetting")
    @ResponseBody
    Map<String, Object> teamIssueTeamListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamIssueTeamListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectProcessPopup")
    public void teamProjectProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectTeamProcessPopup")
    public void teamProjectTeamProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectProcessListSetting")
    @ResponseBody
    Map<String, Object> teamProjectProcessListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectProcessListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectTeamProcessListSetting")
    @ResponseBody
    Map<String, Object> teamProjectTeamProcessListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectTeamProcessListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectProcessCompletePopup")
    public void teamProjectProcessCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectProcessTeamCompletePopup")
    public void teamProjectProcessTeamCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectProcessCompleteListSetting")
    @ResponseBody
    Map<String, Object> teamProjectProcessDelayListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectProcessCompleteListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectProcessTeamCompleteListSetting")
    @ResponseBody
    Map<String, Object> teamProjectProcessTeamCompleteListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectProcessTeamCompleteListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectPlanPopup")
    public void teamProjectPlanPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectTeamPlanPopup")
    public void teamProjectTeamPlanPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectPlanListSetting")
    @ResponseBody
    Map<String, Object> teamProjectPlanListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectPlanListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectTeamPlanListSetting")
    @ResponseBody
    Map<String, Object> teamProjectTeamPlanListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectTeamPlanListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectDelayPopup")
    public void teamProjectDelayPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectTeamDelayPopup")
    public void teamProjectTeamDelayPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamProjectDelayListSetting")
    @ResponseBody
    Map<String, Object> teamProjectDelayListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectDelayListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectTeamDelayListSetting")
    @ResponseBody
    Map<String, Object> teamProjectTeamDelayListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectTeamDelayListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/quiltyProblemSettingPopup")
    public void quiltyProblemSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/quiltyProblemList")
    @ResponseBody
    Map<String, Object> quiltyProblemList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.quiltyProblemList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productCompleteTaskPopup")
    public void productCompleteTaskPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productCompleteTaskSetting")
    @ResponseBody
    Map<String, Object> productCompleteTaskSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productCompleteTaskSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productProcessTaskPopup")
    public void productProcessTaskPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productProcessTaskSetting")
    @ResponseBody
    Map<String, Object> productProcessTaskSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productProcessTaskSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productDelayTaskPopup")
    public void productDelayTaskPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productDelayTaskSetting")
    @ResponseBody
    Map<String, Object> productDelayTaskSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productDelayTaskSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/itemMoldListPopup")
    public void itemMoldListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/itemMoldListPopupSetting")
    @ResponseBody
    Map<String, Object> itemMoldListPopupSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.itemMoldListPopupSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/itemGoodsListPopup")
    public void itemGoodsListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/itemGoodsListPopupSetting")
    @ResponseBody
    Map<String, Object> itemGoodsListPopupSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.itemGoodsListPopupSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldReportPopup")
    public void moldReportPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldReportProjectList")
    @ResponseBody
    Map<String, Object> moldReportProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldReportProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldTotalReportPopup")
    public void moldTotalReportPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldTotalReportProjectList")
    @ResponseBody
    Map<String, Object> moldTotalReportProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldTotalReportProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldReportChinaPopup")
    public void moldReportChinaPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldReportChinaProjectList")
    @ResponseBody
    Map<String, Object> moldReportChinaProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldReportChinaProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/devQulityProblemPopup")
    public void devQulityProblemPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/devQuiltyProblemList")
    @ResponseBody
    Map<String, Object> devQuiltyProblemList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.devQuiltyProblemList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carTaskCompletePopup")
    public void carTaskCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carTaskCompleteSetting")
    @ResponseBody
    Map<String, Object> carTaskCompleteSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.carTaskCompleteSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carTaskProcessPopup")
    public void carTaskProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carTaskProcessSetting")
    @ResponseBody
    Map<String, Object> carTaskProcessSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.carTaskProcessSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/carTaskTotalPopup")
    public void carTaskTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/carTaskTotalSetting")
    @ResponseBody
    Map<String, Object> carTaskTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.carTaskTotalSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productTotalProjectSettingPopup")
    public void productTotalProjectSettingPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productTotalProjectList")
    @ResponseBody
    Map<String, Object> productTotalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productTotalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/moldTotalProjectSettingPopup")
    public void moldTotalProjectProjectSettingPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/moldTotalProjectList")
    @ResponseBody
    Map<String, Object> moldTotalProjectList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.moldTotalProjectList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/productTotalTaskPopup")
    public void productTotalTaskPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/productTotalTaskSetting")
    @ResponseBody
    Map<String, Object> productTotalTaskSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.productTotalTaskSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamQulityListPopup")
    public void teamQulityListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamQulityTeamListPopup")
    public void teamQulityTeamListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/teamQulityListSetting")
    @ResponseBody
    Map<String, Object> teamQulityListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamQulityListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamQulityTeamListSetting")
    @ResponseBody
    Map<String, Object> teamQulityTeamListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamQulityTeamListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeReviewTotalPopup")
    public void prodTypeReviewTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/prodTypeReviewTotalSetting")
    @ResponseBody
    Map<String, Object> prodTypeReviewTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeReviewTotalSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeReviewCompPopup")
    public void prodTypeReviewCompPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/prodTypeReviewCompSetting")
    @ResponseBody
    Map<String, Object> prodTypeReviewCompSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeReviewCompSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeReviewProcPopup")
    public void prodTypeReviewProcPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/prodTypeReviewProcSetting")
    @ResponseBody
    Map<String, Object> prodTypeReviewProcSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeReviewProcSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeProdTotalPopup")
    public void prodTypeProdTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodTypeProdTotalSetting")
    @ResponseBody
    Map<String, Object> prodTypeProdTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeProdTotalSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeProdCompPopup")
    public void prodTypeProdCompPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/prodTypeProdCompSetting")
    @ResponseBody
    Map<String, Object> prodTypeProdCompSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeProdCompSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeProdProcPopup")
    public void prodTypeProdProcPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {

    }

    @RequestMapping("/prodTypeProdProcSetting")
    @ResponseBody
    Map<String, Object> prodTypeProdProcSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeProdProcSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeMoldTotalPopup")
    public void prodTypeMoldTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodTypeMoldTotalSetting")
    @ResponseBody
    Map<String, Object> prodTypeMoldTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeMoldTotalSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeMoldCompPopup")
    public void prodTypeMoldCompPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodTypeMoldCompSetting")
    @ResponseBody
    Map<String, Object> prodTypeMoldCompSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeMoldCompSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeMoldProcPopup")
    public void prodTypeMoldProcPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodTypeMoldProcSetting")
    @ResponseBody
    Map<String, Object> prodTypeMoldProcSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeMoldProcSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeIssuePopup")
    public void prodTypeIssuePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodIssueProjectSetting")
    @ResponseBody
    Map<String, Object> prodIssueProjectSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodIssueProjectSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/prodTypeQulityPopup")
    public void prodTypeQulityPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/prodTypeQuiltyListSetting")
    @ResponseBody
    Map<String, Object> prodTypeQuiltyListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.prodTypeQuiltyListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectItemPopup")
    public void projectItemPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectItemListSetting")
    @ResponseBody
    Map<String, Object> projectItemListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectItemListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectTaskTotalPopup")
    public void projectTaskTotalPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectTaskTotalSetting")
    @ResponseBody
    Map<String, Object> projectTaskTotalSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectTaskTotalSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectTaskCompletePopup")
    public void projectTaskCompletePopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectTaskCompleteSetting")
    @ResponseBody
    Map<String, Object> projectTaskCompleteSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectTaskCompleteSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/projectTaskProcessPopup")
    public void projectTaskProcessPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	model.addAttribute("dashBoardDTO", dashBoardDTO);
    }

    @RequestMapping("/projectTaskProcessSetting")
    @ResponseBody
    Map<String, Object> projectTaskProcessSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.projectTaskProcessSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamProjectListPopup")
    public String teamProjectListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String temp = "";
	String str[] = dashBoardDTO.getvDevType();
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevType().length; index++) {
		if (str.length == 1) {
		    temp += str[index];
		} else {
		    if (str.length != index + 1) {
			temp += str[index] + ",";
		    } else {
			temp += str[index];
		    }
		}
	    }
	    dashBoardDTO.setDevType(temp);
	}
	String temp1 = "";
	String str1[] = dashBoardDTO.getvDevPattern();
	if (str1 != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (str1.length == 1) {
		    temp1 += str1[index];
		} else {
		    if (str1.length != index + 1) {
			temp1 += str1[index] + ",";
		    } else {
			temp1 += str1[index];
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(temp1);
	}

	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/teamProjectListPopup";
    }

    @RequestMapping("/teamProjectListSetting")
    @ResponseBody
    Map<String, Object> teamProjectListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamProjectListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamTaskListPopup")
    public String teamTaskListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String temp = "";
	String str[] = dashBoardDTO.getvDevType();
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevType().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp += str[index];
		} else {
		    if (dashBoardDTO.getvDevType().length != index + 1) {
			temp += str[index] + ",";
		    } else {
			temp += str[index];
		    }
		}
	    }
	    dashBoardDTO.setDevType(temp);
	}
	String temp1 = "";
	String str1[] = dashBoardDTO.getvDevPattern();
	if (str1 != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp1 += str1[index];
		} else {
		    if (dashBoardDTO.getvDevPattern().length != index + 1) {
			temp1 += str1[index] + ",";
		    } else {
			temp1 += str1[index];
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(temp1);
	}

	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/teamTaskListPopup";
    }

    @RequestMapping("/teamTaskListSetting")
    @ResponseBody
    Map<String, Object> teamTaskListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamTaskListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamIssueTotalListPopup")
    public String teamIssueTotalListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String temp = "";
	String str[] = dashBoardDTO.getvDevType();
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevType().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp += str[index];
		} else {
		    if (dashBoardDTO.getvDevType().length != index + 1) {
			temp += str[index] + ",";
		    } else {
			temp += str[index];
		    }
		}
	    }
	    dashBoardDTO.setDevType(temp);
	}
	String temp1 = "";
	String str1[] = dashBoardDTO.getvDevPattern();
	if (str1 != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp1 += str1[index];
		} else {
		    if (dashBoardDTO.getvDevPattern().length != index + 1) {
			temp1 += str1[index] + ",";
		    } else {
			temp1 += str1[index];
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(temp1);
	}

	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/teamIssueTotalListPopup";
    }

    @RequestMapping("/teamIssueTotalListSetting")
    @ResponseBody
    Map<String, Object> teamIssueTotalListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamIssueTotalListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/teamQulityTotalListPopup")
    public String teamQulityTotalListPopup(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	String temp = "";
	String str[] = dashBoardDTO.getvDevType();
	if (str != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevType().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp += str[index];
		} else {
		    if (dashBoardDTO.getvDevType().length != index + 1) {
			temp += str[index] + ",";
		    } else {
			temp += str[index];
		    }
		}
	    }
	    dashBoardDTO.setDevType(temp);
	}
	String temp1 = "";
	String str1[] = dashBoardDTO.getvDevPattern();
	if (str1 != null) {
	    for (int index = 0; index < dashBoardDTO.getvDevPattern().length; index++) {
		if (dashBoardDTO.getvDevType().length == 1) {
		    temp1 += str1[index];
		} else {
		    if (dashBoardDTO.getvDevPattern().length != index + 1) {
			temp1 += str1[index] + ",";
		    } else {
			temp1 += str1[index];
		    }
		}
	    }
	    dashBoardDTO.setDevPattern(temp1);
	}

	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/teamQulityTotalListPopup";
    }

    @RequestMapping("/teamQualityTotalListSetting")
    @ResponseBody
    Map<String, Object> teamQualityTotalListSetting(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.teamQualityTotalListSetting(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }
    
    @RequestMapping("/ecoProjectListPopup2")
    public String ecoProjectListPopup2(@RequestParam(value = "divisionFlag") String divisionFlag,DashBoardDTO dashBoardDTO, Model model) throws Exception {
	
	dashBoardDTO.setDivisionFlag(divisionFlag);
	dashBoardDTO.setDataType("AEGIS");
	
	model.addAttribute("dashBoardDTO", dashBoardDTO);
	return "dashboard/ecoProjectListPopup";
    }
    
    
    @RequestMapping("/etcReport")
    public void etcReport(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	
    }
    
    @RequestMapping("/etcReportList")
    @ResponseBody
    public Map<String, Object> etcReportList(DashBoardDTO dashBoardDTO, Model model) throws Exception {
	PageControl pageControl = boardService.getEtcReportList(dashBoardDTO);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }
}
