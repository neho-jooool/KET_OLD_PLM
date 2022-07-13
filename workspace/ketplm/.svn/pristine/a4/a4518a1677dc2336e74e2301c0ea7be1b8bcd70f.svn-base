package ext.ket.project.program.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramNotifyDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.dto.EjsDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * 프로그램 관리 Controller
 * 
 * @클래스명 : ProgramController
 * @작성자 : sw775.park
 * @작성일 : 2014. 7. 11.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/project/program/*")
public class ProgramController {
    //
    /**
     * 프로그램 이력 비교
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : compareNotifyPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/compareNotifyPopup")
    public void compareNotifyPopup(ProgramNotifyDTO paramObject, Model model) throws Exception {
	List<Map<String, String>> resultList = ProgramHelper.service.compareNotifyHistory(paramObject);
	List<EjsDTO> cols = new ArrayList<EjsDTO>();
	Map<String, String> header = new HashMap<String, String>();
	header.put("CanDelete", "0");
	header.put("Align", "Center");
	header.put("customEventName", "고객 이벤트 명");
	if (resultList != null && resultList.size() > 0) {
	    Map<String, String> map = resultList.get(0);
	    Iterator<String> iterator = map.keySet().iterator();
	    int i = 0;
	    while (iterator.hasNext()) {
		String key = (String) iterator.next();
		if ("NoColor".equals(key)) {
		    continue;
		}
		EjsDTO ejsDTO = new EjsDTO();
		ejsDTO.setWidth("200");
		ejsDTO.setRelWidth("50");
		ejsDTO.setName(key);
		cols.add(ejsDTO);
		if (i != 0) {
		    header.put(key, "버전" + paramObject.getVersions().get((i - 1)));
		}
		i++;
	    }
	}

	ProgramDTO dto = new ProgramDTO();
	dto.setOid(paramObject.getOid());
	this.viewProgram(dto, model);
	model.addAttribute("compareJson", CommonUtil.toJsonString(resultList));
	model.addAttribute("Header", CommonUtil.toJsonString(header));
	model.addAttribute("Cols", CommonUtil.toJsonString(cols));
    }

    /**
     * 프로그램 등록
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : createProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createProgram")
    @ResponseBody
    public Map<String, Object> createProgram(ProgramDTO paramObject) throws Exception {
	KETProgramSchedule ketProgramSchedule = null;
	try {
	    ketProgramSchedule = ProgramHelper.service.save(paramObject);
	} catch (Exception e) {
	    CommonUtil.returnData(false, e.getLocalizedMessage());
	}
	return CommonUtil.returnData(true, new ProgramDTO(ketProgramSchedule));
    }

    /**
     * 프로그램 등록 화면
     * 
     * @메소드명 : createProgramPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 15.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createProgramForm")
    public void createProgramForm(ProgramDTO paramObject) throws Exception {
    }

    /**
     * 프로그램 삭제
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : deleteProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/deleteProgram")
    @ResponseBody
    public void deleteProgram(ProgramDTO paramObject) throws Exception {
	ProgramHelper.service.delete(paramObject);
    }

    /**
     * 차종별 event 일정
     * 
     * @param carTypeOid
     * @return
     * @throws Exception
     * @메소드명 : findEventByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findEventByCarType")
    @ResponseBody
    public Map<String, Object> findEventByCarType(String carTypeOid) throws Exception {
	return EjsConverUtil.convertToDto(ProgramHelper.service.findEventByCarType(carTypeOid));
    }

    /**
     * 프로그램에 등록된 차종별 event 일정
     * 
     * @param programOid
     * @return
     * @throws Exception
     * @메소드명 : findEventByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findEventByProgram")
    @ResponseBody
    public Map<String, Object> findEventByProgram(String programOid) throws Exception {
	return EjsConverUtil.convertToDto(ProgramHelper.service.findEventByProgram(programOid));
    }

    /**
     * 프로그램 검색 paging
     * 
     * @return
     * @메소드명 : findPagingList
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findList")
    @ResponseBody
    public Map<String, Object> findList(ProgramDTO paramObject) throws Exception {
	if (paramObject.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	List<ProgramDTO> programList = new ArrayList<ProgramDTO>();
	for (KETProgramSchedule programSchedule : ProgramHelper.service.find(paramObject)) {
	    programList.add(new ProgramDTO(programSchedule));
	}
	return EjsConverUtil.convertToDto(programList);
    }

    /**
     * 프로그램 검색 paging
     * 
     * @return
     * @메소드명 : findPagingList
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(ProgramDTO paramObject) throws Exception {
	if (paramObject.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = ProgramHelper.service.findPaging(paramObject);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	QueryResult queryResult = pageControl.getResult();
	List<ProgramDTO> programList = new ArrayList<ProgramDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    programList.add(new ProgramDTO((KETProgramSchedule) objArr[0]));
	}
	return EjsConverUtil.convertToDto(programList, pageControl);
    }

    /**
     * 프로그램에 link된 프로젝트 정보
     * 
     * @param programOid
     * @return
     * @throws Exception
     * @메소드명 : findProjectByProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findProjectByProgram")
    @ResponseBody
    public Map<String, Object> findProjectByProgram(String programOid) throws Exception {
	return EjsConverUtil.convertToDto(ProgramHelper.service.findProjectByProgram(programOid));
    }

    /**
     * 프로젝트에 link된 프로그램 정보
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : findProjectByProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findProgramByProject")
    @ResponseBody
    public Map<String, Object> findProgramByProject(String projectOid) throws Exception {
	return EjsConverUtil.convertToDto(ProgramHelper.service.findProgramByProject(projectOid));
    }

    /**
     * 프로젝트에 link된 프로그램의 모든 프로젝트 리스트
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : findProjectByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findProjectByProject")
    @ResponseBody
    public Map<String, Object> findProjectByProject(String projectOid) throws Exception {
	return EjsConverUtil.convertToDto(ProgramHelper.service.findProjectByProject(projectOid));
    }

    /**
     * 프로그램 이력 조회
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : getNotifyHistoryPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/getNotifyHistoryPopup")
    public void getNotifyHistoryPopup(ProgramDTO paramObject, Model model) throws Exception {
	model.addAttribute("historyJson", CommonUtil.toJsonString(ProgramHelper.service.findNotifyHistoryList(paramObject)));
	model.addAttribute("programScheduleOid", paramObject.getOid());
    }

    /**
     * 프로젝트 정보 조회
     * 
     * @메소드명 : list
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/getProject")
    @ResponseBody
    public ProgramProjectDTO getProject(String projectOid, HttpServletResponse response) throws Exception {

	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	ProgramProjectDTO programProjectDTO = new ProgramProjectDTO(project);

	// 고객사
	if (project.getOemType() != null) {
	    String maker = project.getOemType().getMaker().getCode();
	    programProjectDTO.setMaker(maker);

	    String category = project.getOemType().getCode();
	    programProjectDTO.setCategory(category);

	    String domain = "";
	    if (maker.startsWith("10")) {
		domain = "1000";
	    } else {
		domain = "2000";
	    }
	    programProjectDTO.setDomain(domain);
	}

	// ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
	String projectInfos4ECM = "";

	// 프로젝트 상태
	projectInfos4ECM += project.getLifeCycleState().toString() + "↕";

	// 개발단계 = 단계구분
	NumberCode process = project.getProcess();
	String devYnOid = (process != null) ? CommonUtil.getOIDString(process) : "";
	String devYnName = (process != null) ? process.getName() : "";
	String devYnCode = (process != null) ? process.getCode() : "";
	projectInfos4ECM += devYnOid + "↔" + devYnName + "↔" + devYnCode + "↕";

	// 대표 차종
	OEMProjectType oemType = project.getOemType();
	String carTypeOid = (oemType != null) ? CommonUtil.getOIDString(oemType) : "";
	String carTypeName = (oemType != null) ? oemType.getName() : "";
	String carTypeCode = (oemType != null) ? oemType.getCode() : "";
	projectInfos4ECM += carTypeOid + "↔" + carTypeName + "↔" + carTypeCode + "↕";

	// 고객처
	E3PSProjectData projectData = new E3PSProjectData(project);
	String subcontractorOid = "";
	String subcontractorName = "";
	String subcontractorCode = "";
	int subconSize = projectData.subcontractorVec.size();
	for (int i = 0; i < subconSize; i++) {
	    NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
	    subcontractorCode = nc.getCode();
	    if (i < (subconSize - 1)) {
		subcontractorOid += (String) CommonUtil.getOIDString(nc) + ",";
	    } else {
		subcontractorOid += (String) CommonUtil.getOIDString(nc);
	    }
	    if (i < (subconSize - 1)) {
		subcontractorName += NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode()) + ",";
	    } else {
		subcontractorName += NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode());
	    }
	    if (i < (subconSize - 1)) {
		subcontractorCode += subcontractorCode + ",";
	    } else {
		subcontractorCode += subcontractorCode;
	    }
	}
	projectInfos4ECM += subcontractorOid + "↔" + subcontractorName + "↔" + subcontractorCode + "↕";

	// getProductTypeLevel2 제품구분추가 사용 시 2015.02.05
	KETPartClassification partClaz = (KETPartClassification) CommonUtil.getObject(project.getProductTypeLevel2());
	String className = "";
	if (partClaz != null) {
	    className = partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();
	}

	projectInfos4ECM += className + "↕";

	// 제품정보
	KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	// 제품 프로젝트일 경우
	if (project instanceof ProductProject) {
	    QueryResult qr = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE,
		    ProjectProductInfoLink.class, false);
	    while (qr.hasMoreElements()) {
		ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement();
		ProductInfo productInfo = projectProductInfoLink.getProductInfo();

		String pNum = productInfo.getPNum();
		String pName = productInfo.getPName();
		@SuppressWarnings("rawtypes")
		Hashtable partInfo = ketBOMQueryBean.getPartInfo(pNum);
		String relPartVersion = (partInfo != null) ? (String) partInfo.get("rev") : "";
		String relPartOid = (partInfo != null) ? (String) partInfo.get("oid") : "";
		String relPartRevDis = (partInfo != null) ? (String) partInfo.get("revDis") : "";

		projectInfos4ECM += relPartOid + "↔" + pNum + "↔" + pName + "↔" + relPartRevDis + "↕";

	    }
	}
	// 금형 프로젝트일 경우
	else if (project instanceof MoldProject) {
	    MoldItemInfo moldInfo = ((MoldProject) project).getMoldInfo();
	    String pNum = moldInfo.getDieNo();
	    Hashtable partInfo = ketBOMQueryBean.getPartInfo(pNum);
	    String pName = (partInfo != null) ? (String) partInfo.get("partName") : "";
	    String relPartVersion = (partInfo != null) ? (String) partInfo.get("rev") : "";
	    String relPartOid = (partInfo != null) ? (String) partInfo.get("oid") : "";
	    String relPartRevDis = (partInfo != null) ? (String) partInfo.get("revDis") : "";

	    projectInfos4ECM += relPartOid + "↔" + pNum + "↔" + pName + "↔" + relPartRevDis + "↕";

	    /*
	     * ProductProject productProject = moldInfo.getProject(); QueryResult qr = PersistenceHelper.manager.navigate(productProject,
	     * ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class, false); while (qr.hasMoreElements()) {
	     * ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement(); ProductInfo productInfo =
	     * projectProductInfoLink.getProductInfo();
	     * 
	     * String pNum = productInfo.getPNum(); String pName = productInfo.getPName();
	     * 
	     * @SuppressWarnings("rawtypes") Hashtable partInfo = ketBOMQueryBean.getPartInfo(pNum); String relPartVersion = (partInfo !=
	     * null) ? (String) partInfo.get("rev") : ""; String relPartOid = (partInfo != null) ? (String) partInfo.get("oid") : "";
	     * 
	     * projectInfos4ECM += relPartOid + "↔" + pNum + "↔" + pName + "↔" + relPartVersion + "↕";
	     * 
	     * }
	     */

	}

	if (!projectInfos4ECM.equals(""))
	    projectInfos4ECM = projectInfos4ECM.substring(0, projectInfos4ECM.lastIndexOf("↕"));
	programProjectDTO.setProjectInfos4ECM(projectInfos4ECM);

	return programProjectDTO;
    }

    /**
     * 프로그램 Event 일정 변경 체크
     * 
     * @param programDTO
     * @return
     * @throws Exception
     * @메소드명 : hasChangedEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/hasChangedEvent")
    @ResponseBody
    public boolean hasChangedEvent(ProgramDTO programDTO) throws Exception {
	return ProgramHelper.service.hasChangedEvent((KETProgramSchedule) CommonUtil.getObject(programDTO.getOid()), programDTO);
    }

    /**
     * 프로그램 검색 화면
     * 
     * @메소드명 : list
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listProgram")
    public void listProgram() {
    }

    /**
     * 프로그램 검색 팝업 화면
     * 
     * @메소드명 : listProgramPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listProgramPopup")
    public void listProgramPopup() {
    }

    /**
     * 공지내용 조회
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : viewNotifyPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/notifyDetailPopup")
    public void notifyDetailPopup(ProgramDTO paramObject, Model model) throws Exception {
	this.viewProgram(paramObject, model);
    }

    /**
     * 프로그램 변경 공지
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : notifyProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/notifyProgram")
    @ResponseBody
    public ProgramDTO notifyProgram(ProgramDTO paramObject) throws Exception {
	return new ProgramDTO(ProgramHelper.service.createNotice(paramObject));
    }

    /**
     * 프로그램 변경 공지 화면
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : notifyProgramForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/notifyProgramForm")
    public void notifyProgramForm(ProgramDTO paramObject, Model model) throws Exception {
	model.addAttribute("programOid", paramObject.getOid());
    }

    /**
     * 관련 프로젝트
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : saveProjectLink
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveProjectLink")
    @ResponseBody
    public void saveProjectLink(ProgramDTO paramObject) throws Exception {
	ProgramHelper.service.saveProjectLink(paramObject, null);
    }

    /**
     * 프로그램 수정
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : updateProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateProgram")
    @ResponseBody
    public Map<String, Object> updateProgram(ProgramDTO paramObject) {
	String msg = "";
	try {
	    return CommonUtil.returnData(true, new ProgramDTO(ProgramHelper.service.modify(paramObject)));
	} catch (Exception e) {
	    msg = e.getLocalizedMessage();
	}

	return CommonUtil.returnData(false, msg);
    }

    /**
     * 프로그램 수정 화면
     * 
     * @param paramObject
     * @param updateTab
     * @param model
     * @throws Exception
     * @메소드명 : updateProgramForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateProgramForm")
    public void updateProgramForm(ProgramDTO paramObject, Model model) throws Exception {
	model.addAttribute("program", (KETProgramSchedule) CommonUtil.getObject(paramObject.getOid()));
	model.addAttribute("selectTab", paramObject.getSelectTab());
    }

    /**
     * 프로그램 조회
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : viewProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewProgramForm")
    public void viewProgram(ProgramDTO paramObject, Model model) throws Exception {
	ProgramDTO tempDto = new ProgramDTO();
	tempDto.setProgramNo(((KETProgramSchedule) CommonUtil.getObject(paramObject.getOid())).getNumber());
	ProgramDTO dto = null;
	
	for (KETProgramSchedule programSchedule : ProgramHelper.service.find(tempDto)) {//최신 버전 찾기 위함
	    dto = new ProgramDTO(programSchedule);
	}
	
	model.addAttribute("program", dto);
	model.addAttribute("selectTab", paramObject.getSelectTab());
    }

    /**
     * 프로젝트 추가
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : savProgramByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 5.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveProjectLinkByProject")
    @ResponseBody
    public Map<String, Object> saveProjectLinkByProject(ProgramDTO paramObject) {
	String msg = "";
	try {
	    ProgramHelper.service.saveProjectLinkByProject(paramObject);
	    return CommonUtil.returnData(true);
	} catch (Exception e) {
	    msg = e.getLocalizedMessage();
	}
	return CommonUtil.returnData(false, msg);
    }

    @RequestMapping("/changeCheckedEvent")
    @ResponseBody
    public Map<String, Object> changeCheckedEvent(ProgramDTO programDTO) {
	String msg = "";
	try {
	    ProgramHelper.service.updateProgramProjectLinkOfChangedEvent(programDTO);
	    return CommonUtil.returnData(true);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    msg = e.getLocalizedMessage();
	}
	return CommonUtil.returnData(false, msg);
    }

    @RequestMapping("/changeBaseProgram")
    @ResponseBody
    public Map<String, Object> changeBaseProgram(ProgramDTO programDTO) {
	String msg = "";
	try {
	    ProgramHelper.service.updateProgramProjectLinkOfBaseProgram(programDTO);
	    return CommonUtil.returnData(true);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    msg = e.getLocalizedMessage();
	}
	return CommonUtil.returnData(false, msg);
    }
}
