package ext.ket.project.task.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.gate.entity.GateAssessResultDTO;
import ext.ket.project.purchase.util.purchaseUtil;
import ext.ket.project.task.entity.ProjectTaskDTO;
import ext.ket.project.task.entity.TrustTaskResultDTO;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;

@Controller
@RequestMapping("/project/task/*")
public class ProjectTaskCompController {

    @ResponseBody
    @RequestMapping("/saveKqisTrust")
    public Map<String, Object> saveKqisTrust(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    ProjectTaskCompHelper.service.saveKqisTrust(reqMap);
	    resMap.put("message", "저장되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {

	    resMap.put("result", false);
	    resMap.put("message", "저장 중 오류가 발생했습니다.");
	    e.printStackTrace();
	}

	return resMap;
    }

    @RequestMapping("/addKqisSearchForm")
    public void addKqisSearchForm(String oid, Model model) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);

	model.addAttribute("pjtNo", task.getProject().getPjtNo());
	model.addAttribute("taskOid", oid);
    }

    @ResponseBody
    @RequestMapping("/addKqisSearchList")
    public Map<String, Object> addKqisSearchList(@RequestParam Map<String, Object> reqMap) throws Exception {
	return ProjectTaskCompHelper.service.addKqisSearchList(reqMap);
    }

    @ResponseBody
    @RequestMapping("/getKQISTrustListQuery")
    public Map<String, Object> getKQISTrustListQuery(@RequestBody Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> kqisList = new ArrayList<Map<String, Object>>();
	Map<String, Object> resMap = new HashMap<String, Object>();
	try {
	    purchaseUtil pcsUtil = new purchaseUtil();

	    String taskOid = ((String) reqMap.get("taskOid"));

	    E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	    List<ProjectOutput> list = ProjectOutputHelper.manager.getTaskOutputByKqisTrust(task);
	    String adminNos = "";
	    for (ProjectOutput outPut : list) {
		adminNos += outPut.getOutputName() + ",";
	    }
	    adminNos = StringUtils.removeEnd(adminNos, ",");
	    reqMap.put("adminNos", adminNos);
	    kqisList = pcsUtil.getKQISResultList(pcsUtil.getKQISTrustListQuery(reqMap));
	    for (Map<String, Object> obj : kqisList) {
		String ADMIN_NO = (String) obj.get("ADMIN_NO");
		String CODE = (String) obj.get("CODE");
		for (ProjectOutput outPut : list) {
		    String adminNo = outPut.getOutputName();
		    String outPutOid = CommonUtil.getOIDString(outPut);
		    if (ADMIN_NO.equals(adminNo)) {
			String url = "http://kqis.connector.co.kr/Measure/MeasureTestRequestRead.aspx?gw=Y&id=" + pcsUtil.getAccountNo()
			        + "&code=" + CODE;
			obj.put("OID", outPutOid);
			obj.put("URL", url);
			break;
		    }
		}
	    }
	    resMap.put("kqisList", kqisList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}
	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getKQISFastListQuery")
    public Map<String, Object> getKQISFastListQuery(@RequestBody Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> kqisList = new ArrayList<Map<String, Object>>();
	Map<String, Object> resMap = new HashMap<String, Object>();
	try {
	    purchaseUtil pcsUtil = new purchaseUtil();

	    String pjtNo = ((String) reqMap.get("pjtNo"));

	    kqisList = pcsUtil.getKQISResultList(pcsUtil.getKQISFastListQuery(pjtNo));
	    for (Map<String, Object> obj : kqisList) {

		String DOC_STEP = (String) obj.get("DOC_STEP");
		String CODE = (String) obj.get("CODE");

		String url = "http://kqis.connector.co.kr/OldCar/OldCarCtRead.aspx?gw=Y&id=" + pcsUtil.getAccountNo() + "&DOC_STEP="
		        + DOC_STEP + "&code=" + CODE;
		obj.put("URL", url);
	    }
	    resMap.put("kqisList", kqisList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}
	return resMap;
    }

    /**
     * 프로젝트 테스크 완료 화면
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : completeProjectTaskForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/completeProjectTaskForm")
    public void completeProjectTaskForm(String oid, Model model) throws Exception {
    }

    /**
     * 프로젝트 테스크 완료 조회 화면
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : completeProjectTaskViewForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/completeProjectTaskViewForm")
    public void completeProjectTaskViewForm(String oid, Model model) throws Exception {
    }

    /**
     * Gate 테스크의 평가결과 첨부파일 관리화면
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : updateGateResultAttatchForm
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateGateResultAttatchForm")
    public void updateGateResultAttatchForm(String oid, Model model) throws Exception {
	GateAssessResult gateAssResult = ProjectTaskCompHelper.service.getGateAssessResultObj(oid);
	if (gateAssResult != null) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(gateAssResult));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(gateAssResult));
	}
    }

    /**
     * 신뢰도 평가태스크의 차수정보를 팝업으로해서 관리하는 화면으로 이동
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : addTaskOutputLevelForm
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 07.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/addTaskOutputLevelForm")
    public void addTaskOutputLevelForm(String oid, Model model) throws Exception {
	List<TrustTaskResultDTO> taskLevelResult = ProjectTaskCompHelper.service.getTaskTrustLevelList(oid);
	if (taskLevelResult != null) {
	    model.addAttribute("taskLevelResult", taskLevelResult);
	}
    }

    /**
     * 프로젝트 테스크 완료팝업에서 완료처리
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : completeProjectTaskSchedule
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/completeProjectTaskSchedule")
    public String completeProjectTaskSchedule(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);

	ProjectTaskCompHelper.service.completeProjectTaskSchedule(dto);
	return "redirect:/ext/project/task/completeProjectTaskForm.do?oid=" + oid + "&isSuccess=Y";
    }

    /**
     * 프로젝트 테스크 완료팝업에서 저장처리
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : saveCompleteProjectTaskSchedule
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveCompleteProjectTaskSchedule")
    public String saveCompleteProjectTaskSchedule(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	E3PSProject project = (E3PSProject) task.getProject();

	ProjectTaskCompHelper.service.saveCompleteProjectTaskSchedule(dto);
	return "redirect:/ext/project/task/completeProjectTaskForm.do?oid=" + oid + "&isSuccess=Y";
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : saveTaskOutputResult
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveTaskOutputResult")
    @ResponseBody
    public String saveTaskOutputResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	// E3PSProject project = (E3PSProject) task.getProject();

	ProjectTaskCompHelper.service.saveTaskOutputResult(oid, dto);

	// return "redirect:/ext/project/task/completeProjectTaskForm.do?oid=" + oid;
	return oid;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : saveTaskGateCheckResult
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveTaskGateCheckResult")
    @ResponseBody
    public String saveTaskGateCheckResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	// E3PSProject project = (E3PSProject) task.getProject();

	ProjectTaskCompHelper.service.saveTaskGateCheckResult(oid, dto);

	// return "redirect:/ext/project/task/completeProjectTaskForm.do?oid=" + oid;
	return oid;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : saveTaskQualityResult
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveTaskQualityResult")
    @ResponseBody
    public String saveTaskQualityResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	// E3PSProject project = (E3PSProject) task.getProject();

	ProjectTaskCompHelper.service.saveTaskQualityResult(oid, dto);

	// return "redirect:/ext/project/task/completeProjectTaskForm.do?oid=" + oid;
	return oid;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 한꺼번에 저장처리
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 : saveTaskRelatedAllResult
     * @작성자 : jackey88
     * @작성일 : 2014. 11. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveTaskRelatedAllResult")
    @ResponseBody
    public String saveTaskRelatedAllResult(String oid, ProjectTaskDTO dto) throws Exception {

	ProjectTaskCompHelper.service.saveTaskRelatedAllResult(oid, dto);

	return oid;
    }

    @RequestMapping("/createDegreeTaskRelatedAllResult")
    @ResponseBody
    public int createDegreeTaskRelatedAllResult(String oid) throws Exception {

	int rev = ProjectTaskCompHelper.service.createDegreeTaskRelatedAllResult(oid);

	return rev;
    }

    /**
     * 신뢰성 Task 차수 결과 저장
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : saveTaskTrustResult
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveTaskTrustResult")
    public String saveTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	E3PSProject project = (E3PSProject) task.getProject();

	ProjectTaskCompHelper.service.saveTaskTrustResult(oid, dto);

	return "redirect:/ext/project/task/addTaskOutputLevelForm.do?oid=" + oid;
    }

    /**
     * 신뢰성 Task 차수 결과 삭제
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : deleteTaskTrustResult
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 08.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/deleteTaskTrustResult")
    public String deleteTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception {

	ProjectTaskCompHelper.service.deleteTaskTrustResult(oid, dto);

	return "redirect:/ext/project/task/addTaskOutputLevelForm.do?oid=" + oid;
    }

    /**
     * 산출물과 ECO 링크
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : linkProjectOutputEcoProduct
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/linkProjectOutputEcoProduct")
    @ResponseBody
    public boolean linkProjectOutputEcoProduct(String ecoOid, String outputOid) throws Exception {

	boolean flag = true;
	try {
	    outputOid = java.net.URLDecoder.decode(outputOid == null ? "" : outputOid, "euc-kr");
	    ecoOid = java.net.URLDecoder.decode(ecoOid == null ? "" : ecoOid, "euc-kr");

	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOid);
	    // KETDocumentOutputLink link = KETDocumentOutputLink.newKETDocumentOutputLink(document, output);
	    // PersistenceHelper.manager.save(link);

	    E3PSTask task = (E3PSTask) output.getTask();

	    WTChangeOrder2 changeOrder = null;
	    // WTChangeOrder2Master m = (WTChangeOrder2Master) document.getMaster();

	    if (ecoOid != null && ecoOid.indexOf("KETProdChangeOrder") > 0) {
		// 제품ECO
		KETProdChangeOrder document = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
		// ProjectOutputHelper.manager.registryProjectOutput(output, document);
		if (document.getState().toString().equals("APPROVED")) {
		    output.setCompletion(100);
		    output = (ProjectOutput) PersistenceHelper.manager.modify(output);
		}

		QueryResult qr = PersistenceHelper.manager.navigate(output, KETProdChangeOrderOutputLink.CHANGE_ROLE,
		        KETProdChangeOrderOutputLink.class, false);
		while (qr.hasMoreElements()) {
		    KETProdChangeOrderOutputLink outputLink = (KETProdChangeOrderOutputLink) qr.nextElement();

		    // 링크 삭제(링크만)
		    PersistenceHelper.manager.delete(outputLink);
		}

		KETProdChangeOrderOutputLink link = KETProdChangeOrderOutputLink.newKETProdChangeOrderOutputLink(document, output);
		PersistenceHelper.manager.save(link);
	    } else if (ecoOid != null && ecoOid.indexOf("KETMoldChangeOrder") > 0) {
		// 금형ECO
		KETMoldChangeOrder moldDocument = (KETMoldChangeOrder) CommonUtil.getObject(ecoOid);
		// ProjectOutputHelper.manager.registryProjectOutput(output, moldDocument);
		if (moldDocument.getState().toString().equals("APPROVED")) {
		    output.setCompletion(100);
		    output = (ProjectOutput) PersistenceHelper.manager.modify(output);
		}

		QueryResult qr = PersistenceHelper.manager.navigate(output, KETMoldChangeOrderOutputLink.CHANGE_ROLE,
		        KETMoldChangeOrderOutputLink.class, false);

		while (qr.hasMoreElements()) {
		    KETMoldChangeOrderOutputLink outputLink = (KETMoldChangeOrderOutputLink) qr.nextElement();

		    // 링크 삭제(링크만)
		    PersistenceHelper.manager.delete(outputLink);
		}

		KETMoldChangeOrderOutputLink link = KETMoldChangeOrderOutputLink.newKETMoldChangeOrderOutputLink(moldDocument, output);
		PersistenceHelper.manager.save(link);
	    }
	    ProjectTaskHelper.updateCompletionFromOutput(task);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }

    /**
     * 산출물과 도면 링크
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : linkProjectOutputDwgProduct
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/linkProjectOutputDwgProduct")
    @ResponseBody
    public boolean linkProjectOutputDwgProduct(String epmOid, String outputOid) throws Exception {

	boolean flag = true;
	try {
	    outputOid = java.net.URLDecoder.decode(outputOid == null ? "" : outputOid, "euc-kr");
	    epmOid = java.net.URLDecoder.decode(epmOid == null ? "" : epmOid, "euc-kr");

	    ReferenceFactory rf = new ReferenceFactory();
	    EPMDocument epmObj = (EPMDocument) CommonUtil.getObject(epmOid);
	    ;
	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOid);
	    E3PSTask task = (E3PSTask) output.getTask();
	    // output = (ProjectOutput)rf.getReference(outputOid).getObject();
	    // epmObj = (EPMDocument)rf.getReference(epmOid).getObject();

	    if (output != null && epmObj != null) {
		ProjectOutputHelper.manager.registryProjectOutput(output, epmObj);

		ProjectTaskHelper.updateCompletionFromOutput(task);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }

    /**
     * 산출물과 품질문제 링크
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : linkProjectOutputQLP
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/linkProjectOutputQLP")
    @ResponseBody
    public boolean linkProjectOutputQLP(String dqmHeaderOid, String outputOid) throws Exception {

	boolean flag = true;
	String dqmState = "";
	try {
	    outputOid = java.net.URLDecoder.decode(outputOid == null ? "" : outputOid, "euc-kr");
	    dqmHeaderOid = java.net.URLDecoder.decode(dqmHeaderOid == null ? "" : dqmHeaderOid, "euc-kr");

	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOid);
	    E3PSTask task = (E3PSTask) output.getTask();

	    KETDqmHeader headerObj = (KETDqmHeader) CommonUtil.getObject(dqmHeaderOid);

	    KETDqmRaise raiseObj = headerObj.getRaise();

	    if (headerObj != null) {
		dqmState = headerObj.getDqmStateCode();
		if ("END".equals(dqmState)) { // 품질문제가 종결인 경우 100%완료처리
		    output.setCompletion(100);
		    output = (ProjectOutput) PersistenceHelper.manager.modify(output);
		}
		ProjectTaskCompHelper.service.updateDqmProjectOutputLink(raiseObj, output);

	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }

    /**
     * 테스크 결과에 첨부될 파일 저장
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : updateGateResultAttatch
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateGateResultAttatch")
    public String updateGateResultAttatch(String oid, GateAssessResultDTO dto) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	GateAssessResult gateAssRslt = ProjectTaskCompHelper.service.getGateAssessResultObj(oid);
	gateAssRslt.setPassComment(dto.getPassComment());
	PersistenceServerHelper.manager.update(gateAssRslt);
	gateAssRslt = (GateAssessResult) PersistenceHelper.manager.refresh(gateAssRslt);
	KETContentHelper.service.updateContent(gateAssRslt, dto);

	return "redirect:/ext/project/task/updateGateResultAttatchForm.jsp?oid=" + oid + "&isSuccess=Y";
    }

    /**
     * 산출물 GateTemplate체크 변경
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : updateProjectOutputGateCheck
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateProjectOutputGateCheck")
    @ResponseBody
    public boolean updateProjectOutputGateCheck(String outputOid, String gateStr) throws Exception {

	boolean flag = true;
	try {
	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOid);

	    E3PSTask task = (E3PSTask) output.getTask();

	    if ("1".equals(gateStr)) {
		output.setSubjectType("대상");
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    } else if ("0".equals(gateStr)) {
		output.setSubjectType("비대상");
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }

    /**
     * 프로젝트 Gate 테스크 평가결과 초기화
     * 
     * @param oid
     *            : Project OID
     * @param model
     * @throws Exception
     * @메소드명 :
     * @작성자 : jackey88
     * @작성일 : 2014. 11. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/initiateGateResultLink")
    @ResponseBody
    public void initiateGateResultLink(String outputLinkDiv, String gateLinkDiv, String dqmLinkDiv) throws Exception {

	ProjectTaskCompHelper.service.initiateGateResultLink(outputLinkDiv, gateLinkDiv, dqmLinkDiv);

    }
}
