package ext.ket.dms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.dms.entity.KETSGDocument;
import ext.ket.dms.entity.KETSGDocumentDTO;
import ext.ket.dms.officeDocConvert.service.OfficeDocConvertHelper;
import ext.ket.dms.service.KETDocumentHelper;
import ext.ket.dms.util.KETDocumentUtil;
import ext.ket.issue.controller.IssueController;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 9. 20. 오후 12:05:31
 * @Pakage ext.ket.dms.controller
 * @class DocumentController
 *********************************************************/
@Controller
@RequestMapping("/dms/*")
public class DocumentController {

	private static final Logger LOGGER = Logger.getLogger(IssueController.class);

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2018. 10. 10. 오후 12:20:20
	 * @method redirectSG
	 * @param docNo
	 * @return String
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/redirectSG/{docNo}/{cmd}")
	public String redirectSG(@PathVariable String docNo, @PathVariable String cmd, HttpServletRequest req) throws Exception {

		LOGGER.info("redirectSG ### docNo ###### " + docNo);

		KETSGDocument sgDoc = KETDocumentUtil.manager.getLastestSGDocumnent(docNo);
		String redirectURL = "redirect:" + req.getScheme() + "://" + req.getServerName();

		if (sgDoc != null) {
			if ("VIEW".equals(cmd)) {

				redirectURL += "/plm/ext/dms/viewSGDocumentPopup?oid=" + CommonUtil.getOIDString(sgDoc);

			} else {
				ArrayList<ContentDTO> contentList = KETContentHelper.manager.getSecondaryContents(sgDoc);

				if (contentList.size() > 0) {
					redirectURL += contentList.get(0).getDownURLStr();
				} else {
					ContentDTO primaryDTO = KETContentHelper.manager.getPrimaryContent(sgDoc);

					if (primaryDTO != null) {
						redirectURL += primaryDTO.getDownURLStr();
					}
				}
			}
		}

		LOGGER.info("redirectURL ###### " + redirectURL);

		return redirectURL;
	}

	/**
	 * <pre>
	 * @description 시스템 가이드 이력 조회
	 * @author dhkim
	 * @date 2018. 10. 4. 오후 12:16:11
	 * @method viewSGDocHistoryPopup
	 * @param model
	 * @param reqMap
	 * @return Model
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/viewSGDocHistoryPopup")
	public Model viewSGDocHistoryPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
		String oid = (String) reqMap.get("oid");

		KETSGDocument sgDoc = (KETSGDocument) CommonUtil.getObject(oid);

		List<KETSGDocumentDTO> branchList = KETDocumentUtil.manager.getBranchSGDocumnent(sgDoc);

		model.addAttribute("branchList", branchList);

		return model;
	}

	/**
	 * <pre>
	 * @description 시스템 가이드 목록 화면
	 * @author dhkim
	 * @date 2018. 9. 20. 오후 12:05:34
	 * @method sgDocumentList
	 * @param model
	 * @param reqMap
	 * @return Model
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/sgDocumentList")
	public Model sgDocumentList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
		return model;
	}

	@ResponseBody
	@RequestMapping("/findPagingList")
	public Map<String, Object> findPagingList(KETSGDocumentDTO reqDto) throws Exception {

		if (reqDto.getPage() == 0) {
			SessionUtil.removeAttribute("pageSessionId");
		}

		PageControl pageControl = KETDocumentHelper.service.findPagingList(reqDto);
		SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

		// DTO 객체로 변환 한다
		QueryResult qr = pageControl.getResult();

		List<KETSGDocumentDTO> dtoList = new ArrayList<KETSGDocumentDTO>();

		while (qr.hasMoreElements()) {
			Object[] objArr = (Object[]) qr.nextElement();
			KETSGDocument obj = (KETSGDocument) objArr[0];
			KETSGDocumentDTO dto = new KETSGDocumentDTO(obj);
			dtoList.add(dto);
		}

		return EjsConverUtil.convertToDto(dtoList, pageControl);
	}

	/**
	 * <pre>
	 * @description 시스템 가이드 저장 화면
	 * @author dhkim
	 * @date 2018. 9. 20. 오후 3:37:33
	 * @method saveSGDocumentPopup
	 * @param model
	 * @param reqMap
	 * @return Model
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/saveSGDocumentPopup")
	public Model saveSGDocumentPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

		String oid = (String) reqMap.get("oid");
		boolean isModify = false;

		if (StringUtil.checkString(oid)) {
			KETSGDocument doc = (KETSGDocument) CommonUtil.getObject(oid);
			KETSGDocumentDTO dto = new KETSGDocumentDTO(doc);
			model.addAttribute("dto", dto);
			model.addAttribute("oid", oid);
			isModify = true;
		}

		model.addAttribute("isModify", isModify);

		return model;
	}

	/**
	 * <pre>
	 * @description 시스템 가이드 저장 처리
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:55:12
	 * @method saveSGDocument
	 * @param dto
	 * @param reqMap
	 * @return Map<String,Object>
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping("/saveSGDocument")
	public Map<String, Object> saveSGDocument(KETSGDocumentDTO dto, @RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			System.out.println("pFile Check###################" + dto.getPrimaryFile());

			resMap = KETDocumentHelper.service.saveSGDocument(dto, reqMap);

			// office to image call
			String oid = (String) resMap.get("oid");
			OfficeDocConvertHelper.service.docConvertCall(oid);

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
	 * @description 시스템 가이드 상세 화면
	 * @author dhkim
	 * @date 2018. 9. 20. 오후 3:37:45
	 * @method viewSGDocumentPopup
	 * @param model
	 * @param reqMap
	 * @return Model
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/viewSGDocumentPopup")
	public Model viewSGDocumentPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

		String oid = (String) reqMap.get("oid");

		KETSGDocument doc = (KETSGDocument) CommonUtil.getObject(oid);
		KETSGDocumentDTO dto = new KETSGDocumentDTO(doc);
		model.addAttribute("dto", dto);

		return model;
	}

	/**
	 * <pre>
	 * @description 시스템 가이드 개정 처리
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:55:26
	 * @method reviseSGDocument
	 * @param reqMap
	 * @return Map<String,Object>
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping("/reviseSGDocument")
	public Map<String, Object> reviseSGDocument(@RequestBody Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			resMap = KETDocumentHelper.service.reviseSGDocument(reqMap);

			resMap.put("message", "개정이 완료되었습니다.");
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
	 * @description 시스템 가이드 삭제 처리
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:55:34
	 * @method deleteSGDocument
	 * @param reqMap
	 * @return Map<String,Object>
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping("/deleteSGDocument")
	public Map<String, Object> deleteSGDocument(@RequestBody Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			resMap = KETDocumentHelper.service.deleteSGDocument(reqMap);

			resMap.put("message", "삭제가 완료되었습니다.");
			resMap.put("result", true);

		} catch (Exception e) {
			e.printStackTrace();
			resMap.put("result", false);
			resMap.put("message", e.getLocalizedMessage());
		}

		return resMap;
	}
}
