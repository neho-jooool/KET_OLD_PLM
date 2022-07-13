package ext.ket.issue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.content.ContentHolder;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueMasterDTO;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.issue.entity.KETIssueTaskDTO;
import ext.ket.issue.entity.KETIssueToDoDTO;
import ext.ket.issue.service.IssueHelper;
import ext.ket.issue.util.IssueUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 5. 23. 오전 10:43:46
 * @Pakage ext.ket.issue.controller
 * @class IssueController
 *********************************************************/
@Controller
@RequestMapping("/issue/*")
public class IssueController {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(IssueController.class);

    @RequestMapping("/doReStartTaskForm")
    public Model doReStartTaskForm(Model model) throws Exception {
	return model;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 10. 오전 9:29:19
     * @method deleteIssue
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/deleteIssue")
    public Map<String, Object> deleteIssue(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    IssueHelper.service.deleteIssue(reqMap);

	    resMap.put("message", "삭제가 완료되었습니다.");
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
     * @date 2018. 7. 9. 오전 11:29:54
     * @method issueTaskFileDownload
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/issueTaskFileDownload")
    public Map<String, Object> issueTaskFileDownload(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    String oid = (String) reqMap.get("oid");

	    KETIssueTask it = (KETIssueTask) CommonUtil.getObject(oid);
	    boolean isDecrypt = "REQ001".equals(it.getIssueMaster().getReqCode());// 고객요구 일때 복호화해준다
	    String reqNo = it.getIssueMaster().getReqNo();

	    String downloadUrl = FileContentUtil.manager.getEntitySecondaryFilesZip((ContentHolder) it, reqNo, isDecrypt);

	    resMap.put("downloadUrl", downloadUrl);
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
     * @date 2018. 6. 21. 오전 11:00:48
     * @method changeIssuePBO
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/changeIssuePBO")
    public Map<String, Object> changeIssuePBO(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.changeIssuePBO(reqMap);
	    resMap.put("message", "변경되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/meetingTarget")
    public Map<String, Object> meetingTarget(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.meetingTarget(reqMap);
	    resMap.put("message", "저장되었습니다.");
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
     * @date 2018. 6. 7. 오후 3:00:19
     * @method csIssueList
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/csIssueList")
    public Model csIssueList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	model.addAttribute("oid", reqMap.get("oid"));
	return model;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 7. 오전 10:37:23
     * @method issueTaskHistory
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/itHistoryPopup")
    public Model itHistoryPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	KETIssueTask it = (KETIssueTask) CommonUtil.getObject((String) reqMap.get("oid"));

	List<KETIssueTask> list = IssueUtil.manager.getBranchIssueTask(it);
	List<KETIssueTaskDTO> branchList = new ArrayList<KETIssueTaskDTO>();

	for (KETIssueTask branchIt : list) {
	    KETIssueTaskDTO dto = new KETIssueTaskDTO(branchIt);
	    branchList.add(dto);
	}

	model.addAttribute("branchList", branchList);

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 완료
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:30:26
     * @method completeIssueMaster
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/completeIssueMaster")
    public Map<String, Object> completeIssueMaster(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.completeIssueMaster(reqMap);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 고객대응관리 검색 목록
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:30:45
     * @method findPagingList
     * @param im
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/findPagingList")
    public Map<String, Object> findPagingList(KETIssueMasterDTO im) throws Exception {

	if (im.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = IssueHelper.service.findPagingList(im);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETIssueMasterDTO> imDTOList = new ArrayList<KETIssueMasterDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    KETIssueMaster iMaster = (KETIssueMaster) objArr[0];
	    KETIssueMasterDTO imDTO = new KETIssueMasterDTO(iMaster);

	    List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(iMaster);
	    String tmUserNames = "";
	    String issueStateCnt = imDTO.getIssueStateName();

	    if (!IssueUtil.INWORK.equals(imDTO.getIssueState())) {

		int completeCnt = 0;
		for (KETIssueTask it : itList) {

		    if (IssueUtil.COMPLETE.equals(it.getIssueState()))
			completeCnt++;

		    WTUser worker = it.getWorker();
		    if (worker != null) {
			tmUserNames += worker.getFullName() + ",";
		    }
		}

		issueStateCnt += "(" + completeCnt + "/" + itList.size() + ")";
	    }

	    imDTO.setIssueStateCnt(issueStateCnt);

	    tmUserNames = StringUtils.removeEnd(tmUserNames, ",");
	    imDTO.setTmUserNames(tmUserNames);

	    imDTOList.add(imDTO);
	}

	return EjsConverUtil.convertToDto(imDTOList, pageControl);
    }

    @ResponseBody
    @RequestMapping("/findTaskPagingList")
    public Map<String, Object> findTaskPagingList(KETIssueMasterDTO imDTO) throws Exception {

	if (imDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = IssueHelper.service.findTaskPagingList(imDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETIssueTaskDTO> itDTOList = new ArrayList<KETIssueTaskDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    KETIssueTask it = (KETIssueTask) objArr[0];
	    KETIssueMaster im = (KETIssueMaster) objArr[1];
	    KETIssueTaskDTO itDTO = new KETIssueTaskDTO(it);

	    List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);
	    String issueStateCnt = itDTO.getIssueMaster().getIssueStateName();

	    if (!IssueUtil.INWORK.equals(itDTO.getIssueMaster().getIssueState())) {
		int completeCnt = 0;
		for (KETIssueTask cit : itList) {
		    if (IssueUtil.COMPLETE.equals(cit.getIssueState()))
			completeCnt++;
		}
		issueStateCnt += "(" + completeCnt + "/" + itList.size() + ")";
	    }

	    itDTO.getIssueMaster().setIssueStateCnt(issueStateCnt);

	    itDTOList.add(itDTO);
	}

	return EjsConverUtil.convertToDto(itDTOList, pageControl);
    }

    /**
     * <pre>
     * @description 고객대응관리 To-Do 목록
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:03
     * @method findToDoPagingList
     * @param itd
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/findToDoPagingList")
    public Map<String, Object> findToDoPagingList(KETIssueToDoDTO itd) throws Exception {

	if (itd.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = IssueHelper.service.findToDoPagingList(itd);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETIssueToDoDTO> itdDTOList = new ArrayList<KETIssueToDoDTO>();

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    String oid = (String) obj[0];
	    Persistable per = CommonUtil.getObject(oid);

	    KETIssueToDoDTO itdDTO = new KETIssueToDoDTO(per);

	    itdDTOList.add(itdDTO);
	}

	return EjsConverUtil.convertToDto(itdDTOList, pageControl);
    }

    /**
     * <pre>
     * @description 고객대응관리 검색 목록 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:22
     * @method issueMasterList
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/issueMasterList")
    public Model issueMasterList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	boolean isSalesAuth = false;
	Department dept = new PeopleData((user)).department;
	String divisionFlag = "CA";

	if ((CommonUtil.isAdmin() || CommonUtil.isMember("자동차사업부_영업") || CommonUtil.isMember("영업CS")) && !dept.getName().equals("연구지원팀")) {
	    isSalesAuth = true;
	}

	if (CommonUtil.isMember("전자사업부")) {
	    divisionFlag = "ER";
	}

	if (dept != null) {
	    model.addAttribute("deptName", dept.getName());
	    model.addAttribute("deptOid", CommonUtil.getOIDString(dept));
	}

	model.addAttribute("isSalesAuth", isSalesAuth);
	model.addAttribute("divisionFlag", divisionFlag);

	return model;
    }

    /**
     * <pre>
     * @description 고객대응관리 To-Do 목록 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:43
     * @method issueToDoList
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/issueToDoList")
    public Model issueToDoList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	return model;
    }

    /**
     * <pre>
     * @description 세부 요청사항 저장
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:54
     * @method saveIssueTask
     * @param itDTO
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveIssueTask")
    public Map<String, Object> saveIssueTask(KETIssueTaskDTO itDTO, @RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.saveIssueTask(itDTO, reqMap);

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
     * @description 세부 요청사항 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:32:19
     * @method issueTaskPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/issueTaskPopup")
    public Model issueTaskPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	boolean isEdit = false;
	boolean isDeptEdit = false;

	if (StringUtil.checkString(oid)) {
	    KETIssueTask it = (KETIssueTask) CommonUtil.getObject(oid);
	    KETIssueTaskDTO itDTO = new KETIssueTaskDTO(it);
	    model.addAttribute("itDTO", itDTO);

	    String sessionOid = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());

	    if ((sessionOid.equals(itDTO.getWorkerOid()) || CommonUtil.isAdmin()) && IssueUtil.INPROGRESS.equals(itDTO.getIssueState()) && itDTO.isLastest()) {
		isEdit = true;
	    }

	    Department itDept = DepartmentHelper.manager.getDepartment(it.getDeptCode());
	    Department sessionDept = DepartmentHelper.manager.getDepartment((WTUser) SessionHelper.manager.getPrincipal());
	    isDeptEdit = itDept.equals(sessionDept);

	}

	List<Map<String, Object>> partList = IssueUtil.manager.getIssuePartMapList(reqMap);

	model.addAttribute("partList", partList);
	model.addAttribute("isEdit", isEdit);
	model.addAttribute("isDeptEdit", isDeptEdit);

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 저장 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:32:34
     * @method saveIssuePopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/saveIssuePopup")
    public Model saveIssuePopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String projectOid = (String) reqMap.get("projectOid");

	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();

	String divisionCode = (CommonUtil.isMember("자동차사업부") ? "CA" : (CommonUtil.isMember("전자사업부") ? "ER" : ""));

	String creatorName = sessionUser.getFullName();
	String createDate = DateUtil.getCurrentDateString("d");

	boolean isModify = false;
	model.addAttribute("oid", oid);

	if (StringUtil.checkString(oid)) {

	    KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);
	    KETIssueMasterDTO imDTO = new KETIssueMasterDTO(im);
	    model.addAttribute("imDTO", imDTO);
	    isModify = true;
	    creatorName = imDTO.getCreatorName();
	    createDate = imDTO.getCreateDate();

	} else {

	    KETIssueMasterDTO imDTO = new KETIssueMasterDTO();
	    imDTO.setManagerName(creatorName);
	    imDTO.setManagerOid(CommonUtil.getOIDString(sessionUser));
	    model.addAttribute("imDTO", imDTO);
	}

	model.addAttribute("projectOid", projectOid);
	model.addAttribute("isModify", isModify);
	model.addAttribute("creatorName", creatorName);
	model.addAttribute("createDate", createDate);
	model.addAttribute("divisionCode", divisionCode);

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 상세 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:32:58
     * @method viewIssuePopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/viewIssuePopup")
    public Model viewIssuePopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	model.addAttribute("oid", oid);

	KETIssueMaster im = (KETIssueMaster) CommonUtil.getObject(oid);
	KETIssueMasterDTO imDTO = new KETIssueMasterDTO(im);
	model.addAttribute("imDTO", imDTO);

	String sessionOid = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());

	boolean isManager = sessionOid.equals(imDTO.getManagerOid()) || CommonUtil.isAdmin();
	model.addAttribute("isManager", isManager);

	List<KETIssueTaskDTO> itList = IssueUtil.manager.getIssueTaskList(reqMap);

	List<Map<String, Object>> partList = IssueUtil.manager.getIssuePartMapList(reqMap);

	boolean isAllComplete = true;

	for (KETIssueTaskDTO itDTO : itList) {
	    if (!IssueUtil.COMPLETE.equals(itDTO.getIssueState()))
		isAllComplete = false;
	}

	model.addAttribute("isAllComplete", isAllComplete);
	model.addAttribute("itList", itList);
	model.addAttribute("partList", partList);
	model.addAttribute("meetingAuth", CommonUtil.isMember("전자PMO") || CommonUtil.isAdmin());
	model.addAttribute("meetingTarget", StringUtil.checkNull(im.getMeetingTarget()));

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 설정 프로젝트 정보 조회
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:33:32
     * @method getProjectInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getProjectInfo")
    public Map<String, Object> getProjectInfo(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueUtil.manager.getProjectInfo(reqMap);
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
     * @description 세부 요청사항 목록 조회
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:33:49
     * @method getIssueTaskList
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getIssueTaskList")
    public Map<String, Object> getIssueTaskList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    List<Map<String, Object>> itList = IssueUtil.manager.getIssueTaskMapList(reqMap);
	    resMap.put("itList", itList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getIssuePartList")
    public Map<String, Object> getIssuePartList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    List<Map<String, Object>> partList = IssueUtil.manager.getIssuePartMapList(reqMap);
	    resMap.put("partList", partList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/reStartIssueTask")
    public Map<String, Object> reStartIssueTask(@RequestBody Map<String, Object> reqMap) {
	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.reStartIssueTask(reqMap);

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
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:34:12
     * @method saveIssueMaster
     * @param issueDTO
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveIssueMaster")
    public Map<String, Object> saveIssueMaster(KETIssueMasterDTO issueDTO, @RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.saveIssueMaster(issueDTO, reqMap);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/rejectIssueTask")
    public Map<String, Object> rejectIssueTask(KETIssueTaskDTO issueDTO) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = IssueHelper.service.rejectIssueTask(issueDTO);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
}
