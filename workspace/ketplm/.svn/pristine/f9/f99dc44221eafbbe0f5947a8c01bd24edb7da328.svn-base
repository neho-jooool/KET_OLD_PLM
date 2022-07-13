package ext.ket.dms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.multipart.MultipartFile;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.message.KETMessageService;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.ProductInfo;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMasterHistoryLink;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.util.ObjectUtil;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.ReflectUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : ProjectDocumentController
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/dms/*")
public class ProjectDocumentController {

    @RequestMapping("/savePjtDocument")
    public void savePjtDocument() {

    }

    @RequestMapping("/pPapDocSearchList")
    public Model pPapDocSearchList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	try {

	    String tempCategoryName = (String) reqMap.get("categoryNameMessage");
	    String[] categoryNameArr = null;

	    if (StringUtil.checkString(tempCategoryName)) {
		categoryNameArr = tempCategoryName.trim().split(",");

		List<String> categoryList = new ArrayList<String>();
		for (int i = 0; i < categoryNameArr.length; i++) {
		    String categoryName = categoryNameArr[i];
		    categoryList.add(categoryName);
		}
		model.addAttribute("categoryList", categoryList);
	    }

	    List<Map<String, Object>> bomList = KETProjectDocumentHelper.manager.pPapDocSearchList(reqMap);
	    model.addAttribute("bomList", bomList);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return model;
    }

    @RequestMapping("/pPapDocSearchView")
    public void pPapDocSearchView(Model model, String pjtOid) throws Exception {

	String partNos = "";

	if (StringUtils.isNotEmpty(pjtOid)) {// 제품프로젝트 Gate 타스크에서 팝업 발생시 projectoid가 넘어온다
	    QuerySpec qs = new QuerySpec();

	    int idxpi = qs.appendClassList(ProductInfo.class, true);

	    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(pjtOid));
	    qs.appendWhere(cs, new int[] { idxpi });

	    QueryResult qrpi = PersistenceHelper.manager.find(qs);

	    while (qrpi.hasMoreElements()) {
		Object o[] = (Object[]) qrpi.nextElement();
		ProductInfo pi = (ProductInfo) o[0];
		partNos += pi.getPNum() + ",";
	    }
	    partNos = StringUtils.removeEnd(partNos, ",");
	}

	model.addAttribute("partNos", partNos);

	model.addAttribute("AUTH", CommonUtil.isMember("PPAP_관리"));
    }

    // 품질 표준 문서 리스트
    @RequestMapping("/qualityListDocument")
    public void qualityListDocument() throws Exception {
    }

    @RequestMapping("/listProjectDocument")
    public void listProjectDocument() throws Exception {
    }

    @RequestMapping("/listProjectDocumentData")
    @ResponseBody
    public Map<String, Object> listProjectDocumentData(ProjectDocumentDTO dto) throws Exception {

	ReflectUtil.toString(dto);

	// List<KETProjectDocument> documents = KETProjectDocumentHelper.service.find(dto);
	// List<ProjectDocumentDTO> dtos = new ArrayList<ProjectDocumentDTO>();
	// for (KETProjectDocument document : documents) {
	// ProjectDocumentDTO documentDTO = new ProjectDocumentDTO(document);
	// dtos.add(documentDTO);
	// }

	if (dto.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	PageControl pageControl = KETProjectDocumentHelper.service.findPaging(dto);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	QueryResult queryResult = pageControl.getResult();
	List<ProjectDocumentDTO> dtos = new ArrayList<ProjectDocumentDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    dtos.add(new ProjectDocumentDTO((KETProjectDocument) objArr[0]));
	}
	return EjsConverUtil.convertToDto(dtos, pageControl);
    }

    @RequestMapping("/setDevDocCategory")
    @ResponseBody
    public Map<String, Object> setDevDocCategory(String devDocCagegoryCode) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	QuerySpec spec = new QuerySpec(KETDocumentCategory.class);
	spec.appendWhere(new SearchCondition(KETDocumentCategory.class, KETDocumentCategory.CATEGORY_CODE, "=", devDocCagegoryCode),
	        new int[] { 0 });
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    KETDocumentCategory category = (KETDocumentCategory) result.nextElement();
	    resMap = ObjectUtil.manager.converObjectToMap(category);
	}

	return resMap;
    }

    @RequestMapping("/addProjectProductInfo")
    @ResponseBody
    public List<HashMap<String, String>> addProjectProductInfo(String projectoid) throws Exception {

	List<HashMap<String, String>> list = E3PSProjectHelper.service.getProductInfoWTPart(projectoid);
	return list;
    }

    @RequestMapping("/doValidateDocCategory")
    @ResponseBody
    public boolean doValidateDocCategory(String devDocCagegoryCode) throws Exception {

	return KETProjectDocumentHelper.manager.isLeafDocCategory(devDocCagegoryCode);
    }

    @RequestMapping("/getApprovalLine")
    @ResponseBody
    public String getApprovalLine(String pbooid) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	String rtnHtml = "";
	Persistable persistable = CommonUtil.getObject(pbooid);
	WTObject wtObject = KETCommonHelper.service.getPBO((WTObject) persistable);
	KETWfmApprovalMaster approvalMaster = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(wtObject);
	if (approvalMaster != null) {
	    QueryResult qr = PersistenceHelper.manager.navigate(approvalMaster, KETWfmMasterHistoryLink.HISTORY_ROLE,
		    KETWfmMasterHistoryLink.class, true);
	    if (qr != null) {
		List<KETWfmApprovalHistory> histories = new ArrayList<KETWfmApprovalHistory>();
		while (qr.hasMoreElements()) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) qr.nextElement();
		    if ("수신".equals(history.getActivityName()) || "참조".equals(history.getActivityName())
			    || "추가배포요청".equals(history.getActivityName()))
			continue;
		    histories.add(history);
		}
		Collections.sort(histories, ComparatorUtil.APPROVALLINESORT);
		String[] activityNames = new String[histories.size()];
		String[] activityOwners = new String[histories.size()];
		String[] completedDates = new String[histories.size()];

		for (int i = 0; i < histories.size(); i++) {
		    KETWfmApprovalHistory history = histories.get(i);
		    activityNames[i] = (history.isLast()) ? "승인" : StringUtil.checkReplaceStr(history.getActivityName(), "&nbsp;");
		    if ("반려".equals(history.getDecision()))
			activityNames[i] = "반려";
		    activityOwners[i] = StringUtil.checkReplaceStr(history.getOwner().getFullName(), "&nbsp;");
		    if (history.getDelegate() != null)
			activityOwners[i] = activityOwners[i] + "(" + history.getDelegate() + ")";
		    completedDates[i] = (history.getCompletedDate() != null) ? DateUtil.getDateString(history.getCompletedDate(), "d")
			    : "&nbsp;";
		}

		rtnHtml += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		rtnHtml += "<tr>";
		rtnHtml += "<td align=\"right\">";

		rtnHtml += "<table height=\"90\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"right\">";
		rtnHtml += "  <tr>";
		rtnHtml += "    <td rowspan=\"2\" width=\"25px\" class=\"tdblueM\" style=\"border-color:#779cb4;border-style: solid;border-width: 1px;\">"
		        + messageService.getString("e3ps.message.ket_message", "00755") + "</td>";
		for (int i = 0; i < histories.size(); i++) {
		    rtnHtml += "    <td width=\"70px\" class=\"tdblueM\" style=\"border-color:#779cb4;border-style: solid;border-width: 1px;\">"
			    + activityNames[i] + "</td>";
		}
		rtnHtml += "  </tr>";
		rtnHtml += "  <tr>";
		for (int i = 0; i < histories.size(); i++) {
		    rtnHtml += "    <td class=\"tdwhiteM\" style=\"height:68px;\" style=\"border-color:#779cb4;border-style: solid;border-width: 1px;\">"
			    + activityOwners[i] + "<br/><font size=\"1\">" + completedDates[i] + "</font>&nbsp;</td>";
		}
		rtnHtml += "    </tr>";
		rtnHtml += "  </table>";

		rtnHtml += "  </td>";
		rtnHtml += " </tr>";
		rtnHtml += " </table>";
	    }
	}

	return rtnHtml;
    }

    @RequestMapping("/isDisabledCategory")
    @ResponseBody
    public String isDisabledCategory(String devDocCagegoryCode) throws Exception {
	String msg = "";
	try {
	    msg = KETProjectDocumentHelper.manager.isDisabledCategory(devDocCagegoryCode);
	} catch (Exception e) {
	    msg = "문서분류의 비활성화 여부 확인 중 오류가 발생하였습니다.";
	    return msg;
	}
	return msg;
    }

    @ResponseBody
    @RequestMapping("/chkDupMatPart")
    public Map<String, Object> chkDupMatPart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = KETProjectDocumentHelper.manager.chkDupMatPart(reqMap);

	    resMap.put("result", true);
	} catch (Exception e) {
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	    e.printStackTrace();
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/downloadDocFileZip")
    public Map<String, Object> downloadDocFileZip(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    String downloadLink = KETProjectDocumentHelper.manager.downloadDocFileZip(reqMap);

	    resMap.put("downloadLink", downloadLink);
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getPartNoListFromExcel")
    public Map<String, Object> getPartNoListFromExcel(@RequestParam Map<String, Object> reqMap,
	    @RequestParam("partNoListExcel") MultipartFile file) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    File uploadFile = new File(DownloadView.TEMPPATH + File.separator + file.getOriginalFilename());
	    file.transferTo(uploadFile);

	    String partNoList = KETProjectDocumentHelper.manager.getPartNoListFromExcel(reqMap, uploadFile);

	    resMap.put("partNoList", partNoList);
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/batchRevision")
    public Map<String, Object> batchRevision(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = KETProjectDocumentHelper.manager.batchRevision(reqMap);

	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
}
