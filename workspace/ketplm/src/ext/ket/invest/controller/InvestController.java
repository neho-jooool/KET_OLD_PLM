package ext.ket.invest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.content.ContentHolder;
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
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestMasterDTO;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.invest.entity.KETInvestTaskDTO;
import ext.ket.invest.service.InvestHelper;
import ext.ket.invest.util.InvestUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

@Controller
@RequestMapping("/invest/*")
public class InvestController {

    @RequestMapping("/viewInvestDateHistoryPopup")
    public Model viewInvestDateHistoryPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);
	KETInvestMasterDTO imDTO = new KETInvestMasterDTO();

	List<Map<String, Object>> list = InvestUtil.manager.getInvestDateHistoryList(im);
	imDTO.setDateHistoryList(list);

	model.addAttribute("imDTO", imDTO);
	return model;
    }

    @ResponseBody
    @RequestMapping("/deleteInvest")
    public Map<String, Object> deleteInvest(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    InvestHelper.service.deleteInvest(reqMap);

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
     * @description 고객 요구사항 저장 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:32:34
     * @method saveInvestPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/saveInvestPopup")
    public Model saveInvestPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String projectOid = (String) reqMap.get("projectOid");

	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	String creatorName = sessionUser.getFullName();
	String createDate = DateUtil.getCurrentDateString("d");
	Department dept = DepartmentHelper.manager.getDepartment(sessionUser);
	String creatorDeptName = "";
	if (dept != null) {
	    creatorDeptName = dept.getName();
	}

	boolean isModify = false;
	model.addAttribute("oid", oid);

	boolean isManager = false;
	boolean isRegMember = CommonUtil.isMember("고객직접투자비등록관리");

	String sessionOid = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());
	String masterState = "";
	if (StringUtil.checkString(oid)) {

	    KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);
	    KETInvestMasterDTO imDTO = new KETInvestMasterDTO(im);
	    model.addAttribute("imDTO", imDTO);
	    isModify = true;
	    creatorName = imDTO.getCreatorName();
	    createDate = imDTO.getCreateDate();

	    isManager = sessionOid.equals(imDTO.getManagerOid()) || CommonUtil.isAdmin();
	    masterState = im.getInvestState();
	} else {
	    KETInvestMasterDTO imDTO = new KETInvestMasterDTO();
	    // imDTO.setManagerName(creatorName);
	    // imDTO.setManagerOid(CommonUtil.getOIDString(sessionUser));
	    imDTO.setInvestExpense_1("0");
	    imDTO.setInvestExpense_2("0");
	    model.addAttribute("imDTO", imDTO);

	}
	String taskOid = (String) reqMap.get("taskOid");

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	JSONObject obj = new JSONObject();

	if (task != null) {
	    E3PSProject pjt = (E3PSProject) task.getProject();
	    obj.put("pjtNo", pjt.getPjtNo());
	    obj.put("customer", "," + InvestUtil.manager.getSubcontractNames(pjt));
	}

	model.addAttribute("pjtMap", obj);
	model.addAttribute("isManager", isManager);
	model.addAttribute("isRegMember", isRegMember);
	model.addAttribute("isAdmin", CommonUtil.isAdmin());
	model.addAttribute("projectOid", projectOid);
	model.addAttribute("isModify", isModify);
	model.addAttribute("creatorName", creatorName);
	model.addAttribute("createDate", createDate);
	model.addAttribute("creatorDeptName", creatorDeptName);
	model.addAttribute("masterState", masterState);

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 상세 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:32:58
     * @method viewInvestPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/viewInvestPopup")
    public Model viewInvestPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	model.addAttribute("oid", oid);

	KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);
	KETInvestMasterDTO imDTO = new KETInvestMasterDTO(im);
	model.addAttribute("imDTO", imDTO);

	String sessionOid = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());

	boolean isManager = sessionOid.equals(imDTO.getManagerOid()) || CommonUtil.isAdmin();
	model.addAttribute("isManager", isManager);

	boolean isRegMember = CommonUtil.isMember("고객직접투자비등록관리");
	isRegMember = true;
	model.addAttribute("isRegMember", isRegMember);

	reqMap.put("taskCode", "BTYPE");
	List<KETInvestTaskDTO> itList = InvestUtil.manager.getInvestTaskList(reqMap);

	boolean isAllComplete = true;

	for (KETInvestTaskDTO itDTO : itList) {
	    if (!InvestUtil.COMPLETE.equals(itDTO.getInvestState()))
		isAllComplete = false;
	}

	model.addAttribute("isAllComplete", isAllComplete);
	model.addAttribute("isApproveOk", InvestUtil.manager.isApproveOk(im));
	model.addAttribute("itList", itList);

	reqMap.put("taskCode", "ATYPE");
	model.addAttribute("itList2", InvestUtil.manager.getInvestTaskList(reqMap));

	return model;
    }

    /**
     * <pre>
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:34:12
     * @method saveInvestMaster
     * @param investDTO
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveInvestMaster")
    public Map<String, Object> saveInvestMaster(KETInvestMasterDTO investDTO, @RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    String oid = investDTO.getOid();
	    KETInvestMaster im = (KETInvestMaster) CommonUtil.getObject(oid);

	    if ((im != null && !im.getReqNo().equals(investDTO.getReqNo())) || im == null) {
		if (InvestUtil.manager.reqNoDupCheck(investDTO.getReqNo())) {
		    throw new Exception(investDTO.getReqNo() + " 는 이미 존재하는 번호입니다.");
		}
	    }

	    resMap = InvestHelper.service.saveInvestMaster(investDTO, reqMap);

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
     * @method getInvestTaskList
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getInvestTaskList")
    public Map<String, Object> getInvestTaskList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    reqMap.put("taskCode", "BTYPE");
	    List<Map<String, Object>> itList = InvestUtil.manager.getInvestTaskMapList(reqMap);
	    resMap.put("itList", itList);

	    reqMap.put("taskCode", "ATYPE");
	    itList = InvestUtil.manager.getInvestTaskMapList(reqMap);
	    resMap.put("itList2", itList);

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
     * @method investTaskPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/investTaskPopup")
    public Model investTaskPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	boolean isEdit = false;
	boolean isDeptEdit = false;

	if (StringUtil.checkString(oid)) {
	    KETInvestTask it = (KETInvestTask) CommonUtil.getObject(oid);
	    KETInvestTaskDTO itDTO = new KETInvestTaskDTO(it);
	    model.addAttribute("itDTO", itDTO);

	    String sessionOid = CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal());

	    if ((sessionOid.equals(itDTO.getWorkerOid()) || CommonUtil.isAdmin()) && InvestUtil.INPROGRESS.equals(itDTO.getInvestState())) {
		isEdit = true;
	    }

	    Department itDept = DepartmentHelper.manager.getDepartment(it.getDeptCode());
	    Department sessionDept = DepartmentHelper.manager.getDepartment((WTUser) SessionHelper.manager.getPrincipal());
	    isDeptEdit = itDept.equals(sessionDept);
	}

	model.addAttribute("isEdit", isEdit);
	model.addAttribute("isDeptEdit", isDeptEdit);

	return model;
    }

    /**
     * <pre>
     * @description 고객대응관리 검색 목록 화면
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:22
     * @method investMasterList
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/investMasterList")
    public Model investMasterList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	Department dept = new PeopleData((user)).department;

	if (dept != null) {
	    model.addAttribute("deptName", dept.getName());
	    model.addAttribute("deptOid", CommonUtil.getOIDString(dept));
	}

	boolean isRegMember = CommonUtil.isMember("고객직접투자비등록관리");

	model.addAttribute("isRegMember", isRegMember);

	return model;
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
    public Map<String, Object> findPagingList(KETInvestMasterDTO im) throws Exception {

	if (im.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = InvestHelper.service.findPagingList(im);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETInvestMasterDTO> imDTOList = new ArrayList<KETInvestMasterDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    KETInvestMaster iMaster = (KETInvestMaster) objArr[0];

	    KETInvestMasterDTO imDTO = new KETInvestMasterDTO(iMaster);
	    String investStateCnt = imDTO.getInvestStateName();
	    List<KETInvestTask> itList = InvestUtil.manager.getInvestTaskList(iMaster, "BTYPE");
	    String taskDeptCodeNames = "";
	    String workerNames = "";

	    int completeCnt = 0;

	    for (KETInvestTask it : itList) {

		workerNames = it.getWorker().getFullName();
		Department dept = DepartmentHelper.manager.getDepartment(it.getDeptCode());

		if (dept != null) {
		    taskDeptCodeNames += workerNames + "(" + dept.getName() + "),";
		}

		if (InvestUtil.COMPLETE.equals(it.getInvestState())) {
		    completeCnt++;
		}
	    }

	    if (!InvestUtil.INWORK.equals(imDTO.getInvestState()) && !InvestUtil.REGDIT.equals(imDTO.getInvestState())) {
		investStateCnt += "(" + completeCnt + "/" + itList.size() + ")";
	    }

	    taskDeptCodeNames = StringUtils.removeEnd(taskDeptCodeNames, ",");

	    imDTO.setInvestStateCnt(investStateCnt);

	    imDTO.setTaskDeptCodeNames(taskDeptCodeNames);
	    imDTOList.add(imDTO);
	}

	return EjsConverUtil.convertToDto(imDTOList, pageControl);
    }

    /**
     * <pre>
     * @description 세부 요청사항 저장
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:31:54
     * @method saveInvestTask
     * @param itDTO
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveInvestTask")
    public Map<String, Object> saveInvestTask(KETInvestTaskDTO itDTO, @RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = InvestHelper.service.saveInvestTask(itDTO, reqMap);

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
     * @method getInvestTaskList
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getProjectListByDevRequest")
    public Map<String, Object> getProjectListByDevRequest(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    String drNumber = (String) reqMap.get("drNumber");

	    List<Map<String, Object>> projectList = InvestUtil.manager.getProjectListByDrNumber(drNumber);
	    resMap.put("projectList", projectList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/investTaskFileDownload")
    public Map<String, Object> investTaskFileDownload(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    String oid = (String) reqMap.get("oid");

	    KETInvestTask it = (KETInvestTask) CommonUtil.getObject(oid);

	    String downloadUrl = FileContentUtil.manager.getEntitySecondaryFilesZip((ContentHolder) it, it.getSubject(), false);

	    resMap.put("downloadUrl", downloadUrl);
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getProjectInfoByPjtNo")
    public Map<String, Object> getProjectInfoByPjtNo(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap.put("pjtList", InvestUtil.manager.getProjectInfoByPjtNo(reqMap));
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
}
