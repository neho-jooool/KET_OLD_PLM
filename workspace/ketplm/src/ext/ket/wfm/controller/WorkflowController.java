package ext.ket.wfm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.beans.DMSUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ReviewProject;
import e3ps.project.WorkProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.cancel.CancelProject;
import e3ps.project.cancel.ProjectCancelLink;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.common.util.ObjectUtil;
import ext.ket.project.cost.entity.KETCostRate;
import ext.ket.project.cost.entity.KETProjectCostLink;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAssRsltPjtLink;
import ext.ket.project.gate.entity.GateAssRsltTaskLink;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;
import ext.ket.shared.util.TimerUtil;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;
import ext.ket.wfm.entity.WorkItemDTO;
import ext.ket.wfm.service.KETWorkflowHelper;

/**
 * @클래스명 : WorkflowController
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */

@Controller
@RequestMapping("/wfm/workflow/*")
public class WorkflowController {

	/**
	 * @throws Exception
	 * @메소드명 : listWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 7. 4.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listWorkItem")
	public void listWorkItem(Model model) throws Exception {

		model.addAttribute("typeList", KETWorkflowHelper.manager.getTypeList());
	}

	/**
	 * @throws Exception
	 * @메소드명 : listInProgressWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listInProgressWorkItem")
	public void listInProgressWorkItem(Model model) throws Exception {

		model.addAttribute("typeList", KETWorkflowHelper.manager.getTypeList());
	}

	/**
	 * @throws Exception
	 * @메소드명 : listCompletedWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listCompletedWorkItem")
	public void listCompletedWorkItem(Model model) throws Exception {

		model.addAttribute("typeList", KETWorkflowHelper.manager.getTypeList());
	}

	/**
	 * @throws Exception
	 * @메소드명 : listTempWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listTempWorkItem")
	public void listTempWorkItem(Model model) throws Exception {

		model.addAttribute("typeList", KETWorkflowHelper.manager.getTypeList());
	}

	/**
	 * @throws Exception
	 * @메소드명 : listReceiptWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 10. 17.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listReceiptWorkItem")
	public void listReceiptWorkItem(Model model) throws Exception {

		model.addAttribute("typeList", KETWorkflowHelper.manager.getTypeList());
	}

	/**
	 * @throws Exception
	 * @메소드명 : listReferenceItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listReferenceItem")
	public void listReferenceItem() throws Exception {

	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : workItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 6. 30.
	 * @설명 : 결재대기함 리스트
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listWorkItemData")
	@ResponseBody
	public Map<String, Object> listWorkItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		TimerUtil timer = new TimerUtil(getClass().getName());
		timer.setStartPoint("listWorkItemData Query");
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		timer.setEndPoint();
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		timer.setStartPoint("create workitem dto");
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			list.add(new WorkItemDTO((WorkItem) objArr[0]));
		}
		timer.setEndPoint();
		timer.display();
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : inProgressWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 6. 30.
	 * @설명 : 결재진행함 리스트
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listInProgressWorkItemData")
	@ResponseBody
	public Map<String, Object> listInProgressWorkItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		TimerUtil timer = new TimerUtil(getClass().getName());
		timer.setStartPoint("listInProgressWorkItemData Query");
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		timer.setEndPoint();
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		timer.setStartPoint("create workitem dto");
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], false));
		}
		timer.setEndPoint();
		timer.display();
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : completedWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 6. 30.
	 * @설명 : 결재완료함 리스트
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listCompletedWorkItemData")
	@ResponseBody
	public Map<String, Object> listCompletedWorkItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		TimerUtil timer = new TimerUtil(getClass().getName());
		timer.setStartPoint("listCompletedWorkItemData Query");
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		timer.setEndPoint();
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		timer.setStartPoint("create workitem dto");
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], true));
		}
		timer.setEndPoint();
		timer.display();
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : listTempWorkItemData
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 7. 8.
	 * @설명 : 임시저장함
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listTempWorkItemData")
	@ResponseBody
	public Map<String, Object> listTempWorkItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			try {
				list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], false));
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : listReceiptWorkItemData
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 10. 17.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listReceiptWorkItemData")
	@ResponseBody
	public Map<String, Object> listReceiptWorkItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		TimerUtil timer = new TimerUtil(getClass().getName());
		timer.setStartPoint("listReceiptWorkItemData Query");
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		timer.setEndPoint();
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		timer.setStartPoint("create workitem dto");
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], true));
		}
		timer.setEndPoint();
		timer.display();
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * @param workItemDTO
	 * @return
	 * @throws Exception
	 * @메소드명 : listReferenceItemData
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 7. 8.
	 * @설명 : 참조문서함
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/listReferenceItemData")
	@ResponseBody
	public Map<String, Object> listReferenceItemData(WorkItemDTO workItemDTO) throws Exception {

		if (workItemDTO.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}
		PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		QueryResult queryResult = pageControl.getResult();
		List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
		while (queryResult.hasMoreElements()) {
			Object[] objArr = (Object[]) queryResult.nextElement();
			list.add(new WorkItemDTO((WorkItem) objArr[0]));
		}
		return EjsConverUtil.convertToDto(list, pageControl);
	}

	/**
	 * 일괄승인 코멘트 입력 팝업
	 * 
	 * @param workItemoids
	 * @param model
	 * @throws Exception
	 * @메소드명 : doBatchCompleteWorkItemForm
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 11. 5.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	@RequestMapping("/doBatchCompleteWorkItemForm")
	public void doBatchCompleteWorkItemForm(String[] workItemoids, Model model) throws Exception {

		model.addAttribute("workItemoids", workItemoids);
	}

	/**
	 * @param workItemoids
	 * @return
	 * @throws Exception
	 * @메소드명 : doBatchCompleteWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 8. 19.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/doBatchCompleteWorkItem")
	@ResponseBody
	public boolean doBatchCompleteWorkItem(@RequestParam(value = "workItemoids[]") String[] workItemoids, String comments) throws Exception {

		return KETWorkflowHelper.service.doBatchCompleteWorkItem(workItemoids, comments);
	}

	/**
	 * @param pbooid
	 * @return
	 * @throws Exception
	 * @메소드명 : doCancelApproval
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/doCancelApproval")
	@ResponseBody
	public String doCancelApproval(String pbooid) throws Exception {

		String msgcode = "05108";
		boolean flag = KETWorkflowHelper.service.isCancelApproval(pbooid);
		if (flag) {
			boolean result = KETWorkflowHelper.service.doCancelApproval(pbooid);
			if (!result)
				msgcode = "05109";
		} else {
			msgcode = "05168";
		}
		return msgcode;
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 * @메소드명 : doRequestDistribute
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/doRequestDistribute")
	@ResponseBody
	public boolean doRequestDistribute(HttpServletRequest request) throws Exception {
	    	HashMap<String, Object> reqMap = ObjectUtil.manager.httpReqConvertMap(request);
		return KETWorkflowHelper.service.doRequestDistribute(reqMap);
	}

	/**
	 * 결재수행 팝업
	 * 
	 * @param request
	 * @param model
	 * @throws Exception
	 * @메소드명 : doPerformApprovalPopup
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/doPerformApprovalPopup")
	public void doPerformApprovalPopup(HttpServletRequest request, Model model) throws Exception {

		String oid = request.getParameter("oid");
		WorkItem item = (WorkItem) CommonUtil.getObject(oid);
		WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
		Persistable obj = item.getPrimaryBusinessObject().getObject();
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(obj);
		String iframeHeight = "400px";
		boolean conAccept = false;
		boolean isDRR = false;
		if (obj instanceof KETProjectDocument) {
			KETProjectDocument projDoc = (KETProjectDocument) obj;
			conAccept = DMSUtil.isDrCheckOfDoc(projDoc);
		}
		if (obj instanceof KETDevelopmentRequest || obj instanceof KETAnalysisRequestMaster) {
			if ("의뢰서접수".equals(activity.getName())) {
				isDRR = true;
				iframeHeight = "500px";
			}
		}
		WorkItemDTO dto = new WorkItemDTO(item);
		boolean receiver = false;
		boolean confirmReject = false;
		boolean noWorkflow = false;
		if (activity.getName().equals("수신확인")) {
			receiver = true;
			iframeHeight = "500px";
		}
		if (activity.getName().equals("반려확인")) {
			confirmReject = true;
			iframeHeight = "500px";
		}
		// 결재선정보
		Vector<KETWfmApprovalHistory> lineVec = null;

		if (obj instanceof GateAssessResult) {
			int gateSeq = 0;

			GateAssessResult gateAssRslt = (GateAssessResult) obj;
			E3PSTask gate = ProjectTaskCompHelper.service.getTask(gateAssRslt);

			gateSeq = ProjectTaskCompHelper.service.getMaxGateDegree(CommonUtil.getOIDString(gate));
			lineVec = WorkflowSearchHelper.manager.getApprovalHistory(appMaster, gateSeq);

		} else {
			lineVec = WorkflowSearchHelper.manager.getApprovalHistory(appMaster);
		}

		Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
		ArrayList<ArrayList<KETWfmApprovalHistoryDTO>> lineArr = new ArrayList<ArrayList<KETWfmApprovalHistoryDTO>>();
		int split_cnt = 0;
		int cnt = 0;
		ArrayList<KETWfmApprovalHistoryDTO> historyDTOs = new ArrayList<KETWfmApprovalHistoryDTO>();

		boolean activated = false;
		int riskCheckCnt = 0;
		int commentCnt = 0;
		for (KETWfmApprovalHistory history : lineVec) {
			cnt++;
			String activityName = history.getActivityName();
			KETWfmApprovalHistoryDTO historyDTO = new KETWfmApprovalHistoryDTO(history);
			if (StringUtil.checkString(historyDTO.getActivateStyle())) {
				if (!activated) {
					activated = true;
				} else {
					historyDTO.setActivateStyle("");
				}
			}
			if (!(KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName) || KETWfmApprovalHistoryDTO.DISTRIBUTE
					.equals(activityName))) {
				historyDTOs.add(historyDTO);
				split_cnt++;
			}
			if (KETWfmApprovalHistoryDTO.REWORK.equals(activityName)) {
				historyDTOs.clear();
				split_cnt = 0;
				historyDTOs.add(historyDTO);
				split_cnt++;
			}
			if (split_cnt == 6) {
				lineArr.add(historyDTOs);
				historyDTOs = new ArrayList<KETWfmApprovalHistoryDTO>();
				split_cnt = 0;
			}
			
			if (lineVec.size() == cnt)
				lineArr.add(historyDTOs);

		}
		
		
		for(ArrayList<KETWfmApprovalHistoryDTO> KETWfmApprovalHistoryDTO : lineArr){
		    
		    for(KETWfmApprovalHistoryDTO checkDto : KETWfmApprovalHistoryDTO){
			if (checkDto.isRiskCheck()) {
				riskCheckCnt++;
			}
			if (StringUtil.checkString(checkDto.getComment())) {
				commentCnt++;
			}
		    }
		}

		// 배포처
		int recipient_cnt = 0;
		int reference_cnt = 0;
		ArrayList<KETWfmApprovalHistory> recipientArr = KETWorkflowHelper.manager.getRecipientHistory(appMaster);
		ArrayList<KETWfmApprovalHistory> referenceArr = KETWorkflowHelper.manager.getReferenceHistory(appMaster);
		if (recipientArr != null)
			recipient_cnt = recipientArr.size();
		if (referenceArr != null)
			reference_cnt = referenceArr.size();

		// 결재대상 조회 URL
		String pboViewUrl = KETWorkflowHelper.manager.getPboViewUrl(item);
		dto.setPboViewUrl(pboViewUrl);
		Kogger.debug(getClass(), pboViewUrl);

		model.addAttribute("commentCnt", commentCnt);
		model.addAttribute("riskCheckCnt", riskCheckCnt);
		model.addAttribute("conAccept", conAccept);
		model.addAttribute("isDRR", isDRR);
		model.addAttribute("receiver", receiver);
		model.addAttribute("confirmReject", confirmReject);
		model.addAttribute("noWorkflow", noWorkflow);
		model.addAttribute("iframeHeight", iframeHeight);
		model.addAttribute("workItemDTO", dto);
		model.addAttribute("lineArr", lineArr);
		model.addAttribute("recipient_cnt", recipient_cnt);
		model.addAttribute("reference_cnt", reference_cnt);
		boolean isProjectPBO = obj instanceof E3PSProject;
		if (obj instanceof ProductProject) {
			ProductProject pjt = (ProductProject) obj;
			String state = pjt.getState().toString();
			if ("CANCELING".equals(state)) {
				isProjectPBO = false;
			} else if (ProjectHelper.isStopProject(pjt)) {
				isProjectPBO = false;
			}
		}

		model.addAttribute("isProjectPBO", isProjectPBO);
	}

	/**
	 * 배포처조회 팝업
	 * 
	 * @param oid
	 * @param model
	 * @throws Exception
	 * @메소드명 : doViewDistributePopup
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/doViewDistributePopup")
	public void doViewDistributePopup(String oid, Model model) throws Exception {

		Persistable per = CommonUtil.getObject(oid);
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(per);
		ArrayList<KETWfmApprovalHistory> recipientArr = new ArrayList<KETWfmApprovalHistory>();
		ArrayList<HashMap<String, String>> recipientArrL = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> recipientArrR = new ArrayList<HashMap<String, String>>();
		ArrayList<KETWfmApprovalHistory> referenceArr = new ArrayList<KETWfmApprovalHistory>();
		ArrayList<HashMap<String, String>> referenceArrL = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> referenceArrR = new ArrayList<HashMap<String, String>>();
		boolean availableDistribute = false;
		if (appMaster != null) {
			recipientArr = KETWorkflowHelper.manager.getRecipientHistory(appMaster);
			referenceArr = KETWorkflowHelper.manager.getReferenceHistory(appMaster);
			availableDistribute = (appMaster.getCompletedDate() != null);
		}
		int recipient_cnt = recipientArr.size();
		int reference_cnt = referenceArr.size();
		PeopleData pdata = null;
		if (recipient_cnt > 0) {
			int cnt = 1;
			for (KETWfmApprovalHistory history : recipientArr) {
				pdata = new PeopleData(history.getOwner().getPrincipal());
				HashMap<String, String> recipient = new HashMap<String, String>();
				recipient.put("name", pdata.name);
				recipient.put("deptName", pdata.departmentName);
				recipient.put("duty", pdata.duty);
				if (cnt % 2 == 0) {
					recipientArrR.add(recipient);
				} else {

					recipientArrL.add(recipient);
				}
				cnt++;
			}
		}
		if (reference_cnt > 0) {
			int cnt = 1;
			for (KETWfmApprovalHistory history : referenceArr) {
				pdata = new PeopleData(history.getOwner().getPrincipal());
				HashMap<String, String> reference = new HashMap<String, String>();
				reference.put("name", pdata.name);
				reference.put("deptName", pdata.departmentName);
				reference.put("duty", pdata.duty);
				if (cnt % 2 == 0)
					referenceArrR.add(reference);
				else
					referenceArrL.add(reference);
				cnt++;
			}
		}
		model.addAttribute("oid", oid);
		model.addAttribute("availableDistribute", availableDistribute);
		model.addAttribute("recipientArrL", recipientArrL);
		model.addAttribute("recipientArrR", recipientArrR);
		model.addAttribute("referenceArrL", referenceArrL);
		model.addAttribute("referenceArrR", referenceArrR);
		model.addAttribute("recipient_cnt", recipient_cnt);
		model.addAttribute("reference_cnt", reference_cnt);
	}

	/**
	 * 수신자변경 화면
	 * 
	 * @param workItemoids
	 * @param model
	 * @throws Exception
	 * @메소드명 : delegateWorkItemForm
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/delegateWorkItemForm")
	public void delegateWorkItemForm(String[] workItemoids, Model model) throws Exception {

		model.addAttribute("workItemoids", workItemoids);
	}

	/**
	 * 수신자변경
	 * 
	 * @param workItemoids
	 * @param delegateUser
	 * @return
	 * @throws Exception
	 * @메소드명 : delegateWorkItem
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 2.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/delegateWorkItem")
	@ResponseBody
	public String delegateWorkItem(@RequestParam(value = "workItemoids[]") String[] workItemoids, String delegateUser) throws Exception {

		// KETMessageService messageService = KETMessageService.getMessageService();
		String msg = "";
		try {
			WTPrincipal newOwner = (WTPrincipal) CommonUtil.getObject(delegateUser);
			for (String workItemoid : workItemoids) {
				WorkItem workItem = (WorkItem) CommonUtil.getObject(workItemoid);
				// 수신인으로 등록되어 있는지 체크
				WTObject pbo = (WTObject) workItem.getPrimaryBusinessObject().getObject();
				ArrayList<WTUser> receiverList = KETMailHelper.service.getReceiverUser(pbo);
				if (receiverList.contains(newOwner)) {
					msg = "05161";
					break;
				} else {
					KETWorkflowHelper.service.delegateWorkItem(workItem, newOwner);
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 프로젝트 관련 객체의 결재대상 정보 페이지 유형 명 : 목표승인, 평가결과, 검토프로젝트, 제품프로젝트, 금형프로젝트, 개발원가/수익률
	 * 
	 * @param workItemoid
	 * @param model
	 * @throws Exception
	 * @메소드명 : projectObjectPBOForm
	 * @작성자 : Jason, Han
	 * @작성일 : 2014. 9. 24.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 */
	@RequestMapping("/projectObjectPBOForm")
	public void projectObjectPBOForm(String workItemoid, Model model) throws Exception {

		WorkItem workItem = (WorkItem) CommonUtil.getObject(workItemoid);
		WorkItemDTO workItemDTO = new WorkItemDTO(workItem);
		Persistable pbo = workItem.getPrimaryBusinessObject().getObject();
		KETMessageService messageService = KETMessageService.getMessageService();
		WfAssignedActivity activity = (WfAssignedActivity) workItem.getSource().getObject();
		WfProcess process = activity.getParentProcess();
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
			isCanWF = "KET_CAN_WF_NEW".equals(template.getName());

		E3PSProject project = null;
		E3PSProjectData projectData = null;
		String pboOid = CommonUtil.getOIDString(pbo);
		String taskType = "";
		String title = "";
		String pjtOid = "";
		String pjtNo = "";
		String pjtPM = "";
		String pjtCreateDate = "";
		String pjtName = "";
		String pjtCustomer = "";
		String pjtSubcontractor = "";
		String pjtDevType = "";
		String pjtCarType = "";
		String iFrameSrc = "";
		String iFrameHeight = "";
		ArrayList<Persistable> oidList = null;

		if (pbo instanceof AssessSheet) {
			oidList = new ArrayList<Persistable>();
			AssessSheet sheet = (AssessSheet) pbo;
			QueryResult qr = PersistenceHelper.manager.navigate(sheet, ProjectAssSheetLink.PROJECT_ROLE, ProjectAssSheetLink.class);
			while (qr.hasMoreElements()) {
				project = (E3PSProject) qr.nextElement();
				oidList.add(project);
			}
			Collections.sort(oidList, ComparatorUtil.OIDSORT);
			if (oidList.size() > 0)
				project = (E3PSProject) oidList.get(0);
			iFrameSrc = "/plm/ext/project/gate/updateProjectAssessForm.do?designType=B&pjtOid=" + CommonUtil.getOIDString(project);
			iFrameHeight = "450px";
		} else if (pbo instanceof GateAssessResult) {
			oidList = new ArrayList<Persistable>();
			GateAssessResult result = (GateAssessResult) pbo;
			QueryResult qr = PersistenceHelper.manager.navigate(result, GateAssRsltPjtLink.PROJECT_ROLE, GateAssRsltPjtLink.class);
			while (qr != null && qr.hasMoreElements()) {
				project = (E3PSProject) qr.nextElement();
				oidList.add(project);
			}
			Collections.sort(oidList, ComparatorUtil.OIDSORT);
			if (oidList.size() > 0)
				project = (E3PSProject) oidList.get(0);
			E3PSTask task = null;
			oidList = new ArrayList<Persistable>();
			qr = PersistenceHelper.manager.navigate(result, GateAssRsltTaskLink.TASK_ROLE, GateAssRsltTaskLink.class);
			while (qr != null && qr.hasMoreElements()) {
				task = (E3PSTask) qr.nextElement();
				oidList.add(task);
			}
			Collections.sort(oidList, ComparatorUtil.OIDSORT);
			if (oidList.size() > 0)
				task = (E3PSTask) oidList.get(0);
			if (task != null)
				project = (E3PSProject) task.getProject();
			iFrameSrc = "/plm/jsp/project/TaskGateViewInner.jsp?oid=" + CommonUtil.getOIDString(task);
			iFrameHeight = "400px";
		} else if (pbo instanceof ReviewProject) {
			project = (E3PSProject) pbo;
			if (!isCanWF) {
				iFrameSrc = "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search&isIframe=true&oid=" + CommonUtil.getOIDString(project.getMaster()) + "&ptype=R&type=L";
				iFrameHeight = "600px";
			} else {
				E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
				QueryResult qr = PersistenceHelper.manager.navigate(master, ProjectCancelLink.CANCLE_ROLE, ProjectCancelLink.class, true);
				if (qr != null && qr.hasMoreElements()) {
					CancelProject cancelProject = (CancelProject) qr.nextElement();
					iFrameSrc = "/plm/jsp/project/CancelHistoryViewInner.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(project) + "&pcsOid="
							+ CommonUtil.getOIDString(cancelProject);
				}
				iFrameHeight = "300px";
			}
		} else if (pbo instanceof ProductProject) {
			project = (E3PSProject) pbo;
			if (!isCanWF) {
				iFrameSrc = "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search&isIframe=true&oid=" + CommonUtil.getOIDString(project.getMaster()) + "&ptype=P&type=L";

				ProjectChangeStop ps = ProjectHelper.getStopProject(project);
				if (ps != null && "중지검토".equals(ps.getChangeType())) {
					iFrameSrc = "/plm/jsp/project/StopHistoryViewInner.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(project) + "&pcsOid=" + CommonUtil.getOIDString(ps);
				}
				iFrameHeight = "600px";
			} else {
				E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
				QueryResult qr = PersistenceHelper.manager.navigate(master, ProjectCancelLink.CANCLE_ROLE, ProjectCancelLink.class, true);
				if (qr != null && qr.hasMoreElements()) {
					CancelProject cancelProject = (CancelProject) qr.nextElement();
					iFrameSrc = "/plm/jsp/project/CancelHistoryViewInner.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(project) + "&pcsOid="
							+ CommonUtil.getOIDString(cancelProject);
				}
				iFrameHeight = "600px";
			}
		} else if (pbo instanceof MoldProject) {
			project = (E3PSProject) pbo;
			if (!isCanWF) {
				iFrameSrc = "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search&isIframe=true&oid=" + CommonUtil.getOIDString(project.getMaster()) + "&ptype=M&type=L";
				iFrameHeight = "300px";
			} else {
				E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
				QueryResult qr = PersistenceHelper.manager.navigate(master, ProjectCancelLink.CANCLE_ROLE, ProjectCancelLink.class, true);
				if (qr != null && qr.hasMoreElements()) {
					CancelProject cancelProject = (CancelProject) qr.nextElement();
					iFrameSrc = "/plm/jsp/project/CancelHistoryView.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(project) + "&pcsOid="
							+ CommonUtil.getOIDString(cancelProject);
				}
				iFrameHeight = "300px";
			}
		} else if (pbo instanceof KETCostRate) {
			KETCostRate costRate = (KETCostRate) pbo;
			QueryResult qr = PersistenceHelper.manager.navigate(costRate, KETProjectCostLink.PROJECT_ROLE, KETProjectCostLink.class);
			if (qr != null && qr.hasMoreElements()) {
				project = (E3PSProject) qr.nextElement();
			}
			// TODO 원가관리 조회화면 정의되어있지 않음. 차후 구현
		} else if (pbo instanceof WorkProject) {
			project = (E3PSProject) pbo;
			if (!isCanWF) {
				iFrameSrc = "/plm/servlet/e3ps/ProjectScheduleHistoryServlet?cmd=search&isIframe=true&oid=" + CommonUtil.getOIDString(project.getMaster()) + "&ptype=W&type=L";
				iFrameHeight = "600px";
			} else {
				E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
				QueryResult qr = PersistenceHelper.manager.navigate(master, ProjectCancelLink.CANCLE_ROLE, ProjectCancelLink.class, true);
				if (qr != null && qr.hasMoreElements()) {
					CancelProject cancelProject = (CancelProject) qr.nextElement();
					iFrameSrc = "/plm/jsp/project/CancelHistoryViewInner.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(project) + "&pcsOid="
							+ CommonUtil.getOIDString(cancelProject);
				}
				iFrameHeight = "300px";
			}
		}

		taskType = workItemDTO.getTaskType();
		title = workItemDTO.getTitle();
		if (project != null) {
			projectData = new E3PSProjectData(project);
			pjtOid = CommonUtil.getOIDString(project);
			pjtNo = projectData.ViewpjtNo;
			pjtPM = projectData.pjtPmName;
			pjtCreateDate = DateUtil.getDateString(projectData.pjtCreateDate, "d");
			pjtName = projectData.pjtName;
			if (project instanceof MoldProject) {
				MoldProject moldProject = (MoldProject) project;
				MoldItemInfo itemInfo = moldProject.getMoldInfo();
				if (itemInfo != null) {
					ProductProject productProject = itemInfo.getProject();
					projectData = new E3PSProjectData(productProject);
					pjtDevType = productProject.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", productProject
							.getDevelopentType().getCode(), messageService.getLocale().toString()));
				} else {
					pjtDevType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType()
							.getCode(), messageService.getLocale().toString()));
				}

			} else {
				pjtDevType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType()
						.getCode(), messageService.getLocale().toString()));
			}
			if (projectData.customereventVec.size() > 0) {
				for (int i = 0; i < projectData.customereventVec.size(); i++) {
					NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
					String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
					if (i != 0) {
						pjtCustomer += ",";
					}
					pjtCustomer += masterName;
				}
			}
			if (projectData.subcontractorVec.size() > 0) {
				for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
					NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
					String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
					if (i != 0) {
						pjtSubcontractor += ",";
					}
					pjtSubcontractor += masterName;
				}
			}
			pjtCarType = projectData.representModel;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pboOid", pboOid);
		map.put("taskType", taskType);
		map.put("title", title);
		map.put("pjtOid", pjtOid);
		map.put("pjtNo", pjtNo);
		map.put("pjtPM", pjtPM);
		map.put("pjtCreateDate", pjtCreateDate);
		map.put("pjtName", pjtName);
		map.put("pjtCustomer", pjtCustomer);
		map.put("pjtDevType", pjtDevType);
		map.put("pjtCarType", pjtCarType);
		map.put("pjtSubcontractor", pjtSubcontractor);
		map.put("iFrameSrc", iFrameSrc);
		map.put("iFrameHeight", iFrameHeight);

		model.addAttribute("pjtInfo", map);
	}
}
