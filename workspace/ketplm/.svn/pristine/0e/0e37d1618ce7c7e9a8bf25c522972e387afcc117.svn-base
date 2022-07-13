package ext.ket.cost.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;
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
import wt.fc.collections.WTArrayList;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.TaskUserHelper;
import e3ps.project.beans.TaskViewButtonAuth;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.cost.code.sap.ErpProductCostInterface;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.CostTrackingDTO;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.entity.costErpInterfaceDTO;
import ext.ket.cost.entity.manage.BomEditorACL;
import ext.ket.cost.entity.manage.BomEditorACLFromType;
import ext.ket.cost.service.CostHelper;
import ext.ket.cost.util.BomEditorACLUtil;
import ext.ket.cost.util.BomEditorColumnInfo;
import ext.ket.cost.util.CostBomEditorColumnExcelUtil;
import ext.ket.cost.util.CostBomUploadUtil;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostExcelLoadUtil;
import ext.ket.cost.util.CostTreeUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 29. 오후 1:28:58
 * @Pakage ext.ket.project.cost.controller
 * @class CostController
 *********************************************************/
@Controller
@RequestMapping("/cost/*")
public class CostController {

    private static final Logger LOGGER = Logger.getLogger(CostController.class);

    @ResponseBody
    @RequestMapping("/updateCostHistoryLatest")
    public Map<String, Object> updateCostHistoryLatest(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## updateCostHistoryLatest call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    erpIf.updateLatest(erpIf.setListByJsonStr(reqMap));

	    resMap.put("message", "최종 전송대상 플래그를 업데이트 하였습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/costProductHistoryCreate")
    public Map<String, Object> costProductHistoryCreate(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reSendErp call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    erpIf.costProductHistoryCreate(reqMap);

	    resMap.put("message", "전송대상 데이터가 집계되었습니다.ERP 이관을 하려면 전송 버튼을 실행하세요.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/costProductSendErp")
    public Map<String, Object> costProductSendErp(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reSendErp call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();

	    erpIf.costProductSendErp(erpIf.getCostSendTargetList());

	    resMap.put("message", "ERP일괄전송되었습니다.전송내역을 확인하세요.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/reSendErp")
    public Map<String, Object> reSendErp(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reSendErp call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    List<CostInterfaceHistory> list = erpIf.setListByJsonStr(reqMap);
	    String message = erpIf.getMessageSendErpCheck(list);

	    if (StringUtils.isNotEmpty(message)) {

		resMap.put("message", message);
		resMap.put("result", false);

	    } else {
		erpIf.costProductSendErp(list);
		resMap.put("message", "재전송처리 되었습니다. 전송내역을 확인하세요.");
		resMap.put("result", true);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/deleteErpPart")
    public Map<String, Object> deleteErpPart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reCalaulate call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    erpIf.deleteErpPart(erpIf.setListByJsonStr(reqMap));

	    resMap.put("message", "처리되었습니다.전송내역을 확인하세요.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/reCalaulate")
    public Map<String, Object> reCalaulate(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reCalaulate call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    erpIf.reCalaulate(erpIf.setListByJsonStr(reqMap));

	    resMap.put("message", "재계산처리되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/reCalaulateAll")
    public Map<String, Object> reCalaulateAll(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reCalaulate call ##############");
	    ErpProductCostInterface erpIf = new ErpProductCostInterface();
	    erpIf.reCalaulate(erpIf.getCostIFhistoryListAll());

	    resMap.put("message", "재계산처리되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @RequestMapping("/findPagingInterfaceList")
    @ResponseBody
    public Map<String, Object> findPagingInterfaceList(costErpInterfaceDTO interfaceDTO) throws Exception {

	if (interfaceDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = CostHelper.service.findInterfacePaging(interfaceDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	LOGGER.info(qr.size());

	List<costErpInterfaceDTO> interfaceDTOList = new ArrayList<costErpInterfaceDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    costErpInterfaceDTO rsInterfaceDTO = new costErpInterfaceDTO((CostInterfaceHistory) objArr[0]);
	    interfaceDTOList.add(rsInterfaceDTO);
	}

	return EjsConverUtil.convertToDto(interfaceDTOList, pageControl);
    }

    @RequestMapping("/costErpInterfaceDetail")
    public Model costErpInterfaceDetail(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	FileContentUtil.manager.saveDownloadHistory("ERP인터페이스세부내역",
	        ((CostInterfaceHistory) CommonUtil.getObject((String) reqMap.get("oid"))).getRealPartNo());
	model.addAttribute("oid", (String) reqMap.get("oid"));
	return model;
    }

    @RequestMapping("/costErpInterfaceList")
    public Model costErpInterfaceList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	FileContentUtil.manager.saveDownloadHistory("ERP인터페이스리스트", "");
	return model;
    }

    @ResponseBody
    @RequestMapping("/reCalcReduceCost")
    public Map<String, Object> reCalcReduceCost(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reCalcReduceCost call ##############");
	    CostHelper.service.reCalcReduceCost(reqMap);

	    resMap.put("message", "감가비 재계산 및 재산출이 완료되었습니다.");
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getCostInfoList")
    public Map<String, Object> getCostInfoList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	List<Map<String, Object>> costList = null;
	try {

	    String partNos = "";
	    String productProjectOids = "";
	    String reviewProjectOids = "";
	    String productProjectOidPartNos = "";

	    String pjtNos[] = (StringUtil.checkNull((String) reqMap.get("pjtNos"))).split(",");

	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();

	    for (String pjtNo : pjtNos) {

		E3PSProject project = ProjectHelper.manager.getProject(pjtNo);

		if (project instanceof ProductProject) {
		    ProductProject pjt = (ProductProject) project;
		    String productOid = CommonUtil.getOIDLongValue2Str((E3PSProjectMaster) pjt.getMaster());

		    productProjectOids += "'" + productOid + "',";

		    if (pjt.getDevRequest() != null) {
			// ReviewProject rp = (ReviewProject) CommonUtil.getObject(pjt.getDevRequest().getProjectOID());
			String reviewPjtNos[] = pjt.getReviewPjtNo().split(",");

			for (String RpjtNo : reviewPjtNos) {
			    E3PSProject rp = ProjectHelper.getProject(RpjtNo);
			    if (rp != null) {
				reviewProjectOids += "'" + CommonUtil.getOIDLongValue2Str((E3PSProjectMaster) rp.getMaster()) + "',";
			    }
			}

		    }

		    QueryResult qr = PersistenceHelper.manager.navigate(pjt, ProjectProductInfoLink.PRODUCT_INFO_ROLE,
			    ProjectProductInfoLink.class, false);

		    while (qr.hasMoreElements()) {
			ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement();
			ProductInfo productInfo = projectProductInfoLink.getProductInfo();
			String partNo = productInfo.getPNum();
			String partName = productInfo.getPName();
			if (partNo.startsWith("H")) {
			    partNo = StringUtils.removeStart(partNo, "H");
			}
			Map<String, Object> partMap = new HashMap<String, Object>();
			partMap.put("partNo", partNo);
			partMap.put("partName", partName);
			partMap.put("pjtNo", pjt.getPjtNo());
			partList.add(partMap);
			partNos += "'" + partNo + "',";
			productProjectOidPartNos += "'" + productOid + partNo + "'," + "'" + productOid + "H" + partNo + "',";

		    }

		}

	    }

	    partNos = StringUtils.removeEnd(partNos, ",");
	    productProjectOids = StringUtils.removeEnd(productProjectOids, ",");
	    reviewProjectOids = StringUtils.removeEnd(reviewProjectOids, ",");
	    productProjectOidPartNos = StringUtils.removeEnd(productProjectOidPartNos, ",");

	    paramMap.put("partList", partList);
	    paramMap.put("partNos", partNos);
	    paramMap.put("productProjectOids", productProjectOids);
	    paramMap.put("reviewProjectOids", reviewProjectOids);
	    paramMap.put("productProjectOidPartNos", productProjectOidPartNos);

	    costList = CostUtil.manager.getCostInfoList(paramMap);

	    /*
	     * KETProjectDocument doc = (KETProjectDocument)CommonUtil.getObject((String) reqMap.get("oid"));
	     * 
	     * QueryResult qr = PersistenceHelper.manager.navigate(doc, "output", KETDocumentOutputLink.class);
	     * 
	     * 
	     * 
	     * 
	     * while(qr.hasMoreElements()){
	     * 
	     * ProjectOutput po = (ProjectOutput) qr.nextElement(); ProductProject pjt = (ProductProject)po.getTask().getProject();
	     * 
	     * QueryResult qr2 = PersistenceHelper.manager.navigate(pjt,
	     * ProjectProductInfoLink.PRODUCT_INFO_ROLE,ProjectProductInfoLink.class, false);
	     * 
	     * productProjectOids += "'"+CommonUtil.getOIDLongValue2Str((E3PSProjectMaster)pjt.getMaster()) + "',";
	     * 
	     * if(pjt.getDevRequest() != null){ ReviewProject rp = (ReviewProject)
	     * CommonUtil.getObject(pjt.getDevRequest().getProjectOID()); reviewProjectOids +=
	     * "'"+CommonUtil.getOIDLongValue2Str((E3PSProjectMaster)rp.getMaster()) + "',"; }
	     * 
	     * while (qr2.hasMoreElements()) { ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr2.nextElement();
	     * ProductInfo productInfo = projectProductInfoLink.getProductInfo(); String partNo = productInfo.getPNum(); String partName =
	     * productInfo.getPName(); if(partNo.startsWith("H")){ partNo = StringUtils.removeStart(partNo, "H"); } Map<String, Object>
	     * partMap = new HashMap<String, Object>(); partMap.put("partNo", partNo); partMap.put("partName", partName);
	     * partList.add(partMap); partNos += "'"+partNo + "',"; } } partNos = StringUtils.removeEnd(partNos, ","); productProjectOids =
	     * StringUtils.removeEnd(productProjectOids, ","); reviewProjectOids = StringUtils.removeEnd(reviewProjectOids, ",");
	     * 
	     * paramMap.put("partList", partList); paramMap.put("partNos", partNos); paramMap.put("productProjectOids", productProjectOids);
	     * paramMap.put("reviewProjectOids", reviewProjectOids);
	     * 
	     * costList = CostUtil.manager.getCostInfoList(paramMap);
	     */

	    resMap.put("costList", costList);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @메소드명 : costExcelCreate
     * @작성자 : 황정태
     * @작성일 : 2019. 6. 17.
     * @설명 : 원가보고서 excel 다운로드 (암호화)
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/costExcelCreate")
    public void costExcelCreate(@RequestParam Map<String, Object> reqMap, HttpServletResponse response) {

	File destFile = null;
	File encFile = null;
	try {

	    // file path
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";

	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }

	    // file name
	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sFileName = "cost_" + ff.format(new Date()) + ".xls";

	    destFile = new File(downPath + sFileName);

	    String binaryFile = (String) reqMap.get("costExcel");

	    byte[] buff = binaryFile.getBytes();

	    FileOutputStream fos = null;

	    fos = new FileOutputStream(destFile);
	    fos.write(buff);
	    fos.close();

	    if (DRMHelper.useDrm) {
		encFile = DRMHelper.encryptFile(destFile);
	    }

	    FileInputStream fin = null;

	    int ifilesize = 0;

	    if (DRMHelper.useDrm) {
		fin = new FileInputStream(encFile);
		ifilesize = (int) encFile.length();
	    } else {
		fin = new FileInputStream(destFile);
		ifilesize = (int) destFile.length();
	    }

	    byte b[] = new byte[ifilesize];

	    response.setContentLength(ifilesize);
	    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    ServletOutputStream oout = response.getOutputStream();
	    fin.read(b);
	    oout.write(b, 0, ifilesize);
	    oout.close();
	    fin.close();

	} catch (Exception e) {
	    e.printStackTrace();

	} finally {
	    if (destFile.isFile()) {
		destFile.delete();
	    }
	    if (encFile.isFile()) {
		encFile.delete();
	    }
	}

    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 14. 오후 2:25:30
     * @method bomCopyAdd
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/bomCopyAdd")
    public Map<String, Object> bomCopyAdd(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.bomCopyAdd(reqMap);

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
     * @date 2019. 4. 30. 오후 4:13:14
     * @method copyCasePartPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/copyCasePartPopup")
    public Model copyCasePartPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String taskOid = (String) reqMap.get("taskOid");
	CostPart part = (CostPart) CommonUtil.getObject(oid);
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	List<Map<String, Object>> list = CostHelper.service.getProjectPartMapList(part.getPartNo(), (E3PSProject) task.getProject());

	model.addAttribute("oid", oid);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("list", list);
	return model;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:12:04
     * @method createBomUploadExcel
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/createBomUploadExcel")
    public Map<String, Object> createBomUploadExcel(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostBomUploadUtil.manager.createBomUploadExcel(reqMap);

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
     * @author Administrator
     * @date 2019. 4. 15. 오후 5:25:16
     * @method loadChartData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/loadChartData")
    public Map<String, Object> loadChartData(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostUtil.manager.loadChartData(reqMap);

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
     * @date 2019. 4. 15. 오후 1:27:12
     * @method viewCostChart
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/viewCostChart")
    public Model viewCostChart(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	String oid = (String) reqMap.get("oid");
	String productNo = (String) reqMap.get("productNo");
	model.addAttribute("oid", oid);
	model.addAttribute("productNo", productNo);
	return model;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 15. 오후 1:27:05
     * @method costHistoryData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/costHistoryData")
    public Map<String, Object> costHistoryData(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    List<Object> body = new ArrayList<Object>();

	    List<Map<String, Object>> data = CostUtil.manager.getCostHistoryData(reqMap);
	    body.add(data);

	    result.put("Body", body);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 15. 오후 1:27:31
     * @method viewCostHistoryPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/viewCostHistoryPopup")
    public String viewCostHistoryPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	reqMap.put("EDITMODE", "COSTHISTORY");
	String auth = CostUtil.manager.getCostAuthInfo(reqMap);

	model.addAttribute("pjtOid", oid);

	if ("ERROR".equals(auth)) {

	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");
	    if ("plmapwas".equals(hostName)) {
		hostName = "plm.ket.com";
	    } else {
		hostName = "plmapdev.ket.com";
	    }
	    model.addAttribute("hostName", hostName);
	    FileContentUtil.manager.saveDownloadHistory("원가요청서 조회(공통권한)", ((E3PSProject) CommonUtil.getObject(oid)).getPjtNo());
	    return "/cost/costReqHistoryPopup";
	}

	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	String pjtNo = "'" + project.getPjtNo() + "'";

	List<CostReport> reportList = CostUtil.manager.getCostReportList(project.getPjtNo());

	if (project instanceof ProductProject && project.getDevRequest() != null && StringUtil.checkString(project.getReviewPjtNo())) {
	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    for (String RpjtNo : pjtNos) {
		E3PSProject rp = ProjectHelper.getProject(RpjtNo);
		pjtNo += ",'" + rp.getPjtNo() + "'";
		reportList.addAll(CostUtil.manager.getCostReportList(rp.getPjtNo()));
	    }

	}

	List<Map<String, String>> productList = CostUtil.manager.getCostProductList(pjtNo);

	String allPartNo = "'";

	for (Map<String, String> map : productList) {
	    allPartNo += map.get("partNo") + "','";
	}
	allPartNo = StringUtils.removeEnd(allPartNo, ",'");

	model.addAttribute("allPartNo", allPartNo);
	model.addAttribute("productList", productList);
	model.addAttribute("oid", oid);

	if (reportList.size() == 0) {
	    model.addAttribute("ERROR", "원가 이력이 없습니다.");
	} else {
	    model.addAttribute("EDITMODE", auth);
	    FileContentUtil.manager.saveDownloadHistory("원가이력분석도구 검색", allPartNo);
	}

	return "/cost/viewCostHistoryPopup.jsp?oid=" + oid;
    }

    /**
     * <pre>
     * @description 수식 속성 체크
     * @author dhkim
     * @date 2018. 10. 25. 오전 9:33:18
     * @method checkFormula
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/checkFormula")
    public Map<String, Object> checkFormula(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostCalculateUtil.manager.checkFormula(reqMap);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 제품 원가 수식버전 변경 처리
     * @author dhkim
     * @date 2018. 9. 14. 오전 10:16:56
     * @method changeFormulaVersion
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/changeFormulaVersion")
    public Map<String, Object> changeFormulaVersion(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.changeFormulaVersion(reqMap);

	    resMap.put("message", "버전 변경이 완료되었습니다.");
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
     * @description 개정취소 처리
     * @author dhkim
     * @date 2018. 10. 17. 오후 2:04:16
     * @method reviseCancel
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/reviseCancel")
    public Map<String, Object> reviseCancel(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    LOGGER.info("########################## reviseCancel call ##############");
	    resMap = CostHelper.service.reviseCancel(reqMap);

	    resMap.put("message", "개정취소가 완료되었습니다.");
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
     * @description 원가 수식 개정 처리
     * @author dhkim
     * @date 2018. 9. 14. 오전 10:16:31
     * @method reviseCostFormula
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/reviseCostFormula")
    public Map<String, Object> reviseCostFormula(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.reviseCostFormula(reqMap);

	    resMap.put("version", String.valueOf(CostUtil.manager.getLastestFormulaVersion()));
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
     * @date 2018. 5. 18. 오전 10:51:47
     * @method costRealPartNoPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costRealPartNoPopup")
    public Model costRealPartNoPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String taskOid = (String) reqMap.get("taskOid");
	model.addAttribute("taskOid", taskOid);

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();

	if (project.getDevRequest() == null || !StringUtil.checkString(project.getDevRequest().getProjectOID())
	        || StringUtils.isEmpty(project.getReviewPjtNo())) {
	    model.addAttribute("ERROR", "검토 프로젝트가 없습니다.");
	} else {
	    // E3PSProject reviewPjt = (E3PSProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    boolean isCompleted = true;
	    for (String pjtNo : pjtNos) {
		E3PSProject reviewPjt = ProjectHelper.getProject(pjtNo);
		// reviewPjt = ProjectHelper.getProject(reviewPjt.getPjtNo());
		String state = reviewPjt.getState().toString();
		if (!state.equals("COMPLETED")) {
		    isCompleted = false;
		    break;
		}

	    }
	    if (!isCompleted) {
		model.addAttribute("ERROR", "검토 프로젝트가 완료되지 않았습니다.");
	    }
	}

	return model;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 5. 18. 오후 1:46:39
     * @method saveRealPartNo
     * @param reqMap
     * @param file
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveRealPartNo")
    public Map<String, Object> saveRealPartNo(@RequestParam Map<String, Object> reqMap, @RequestParam("uploadFilePartNo") MultipartFile file) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    File uploadFile = new File(DownloadView.TEMPPATH + File.separator + file.getOriginalFilename());
	    file.transferTo(uploadFile);

	    CostExcelLoadUtil.manager.saveRealPartNo(reqMap, uploadFile);

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
     * @date 2018. 5. 18. 오전 10:51:34
     * @method createRealPartNoFormExcel
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/createRealPartNoFormExcel")
    public Map<String, Object> createRealPartNoFormExcel(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    LOGGER.info("########################## createRealPartNoFormExcel call ##############");
	    resMap = CostExcelLoadUtil.createRealPartNoFormExcel(reqMap);
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
     * @description 원가동인별 분석 목록
     * @author dhkim
     * @date 2018. 4. 17. 오후 5:00:35
     * @method getAnalysisList
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getAnalysisList")
    public Map<String, Object> getAnalysisList(@RequestParam Map<String, Object> reqMap) {

	LOGGER.info("########################## getAnalysisList call ##############");

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    resMap = CostUtil.manager.getAnalysisList(reqMap);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return resMap;
    }

    /**
     * <pre>
     * @description DR 단계별 원가 변동 분석
     * @author dhkim
     * @date 2018. 4. 17. 오후 5:00:39
     * @method getCompareDRInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getCompareDRInfo")
    public Map<String, Object> getCompareDRInfo(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    LOGGER.info("########################## getCompareDRInfo call ##############");
	    resMap = CostUtil.manager.getCompareDRInfo(reqMap);
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
     * @description 수익율 분석 화면
     * @author dhkim
     * @date 2018. 4. 17. 오후 5:00:42
     * @method costProfitAnalysis
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/costProfitAnalysis")
    public Model costProfitAnalysis(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	LOGGER.info("############# Call ProjectCostTrackingList ####################");

	String partOid = (String) reqMap.get("partOid");
	String rPartOid = (String) reqMap.get("rPartOid");

	CostPart part = (CostPart) CommonUtil.getObject((String) reqMap.get("partOid"));

	if (StringUtil.checkString(rPartOid)) {
	    E3PSProjectMaster pjtMaster = part.getProject();
	    E3PSProject project = ProjectHelper.manager.getLastProject(pjtMaster);
	    if (project instanceof ReviewProject) {
		part = (CostPart) CommonUtil.getObject(rPartOid);
	    }
	}

	FileContentUtil.manager.saveDownloadHistory("수익율 분석", part.getPartNo() + " / " + part.getProject().getPjtNo());

	CostTrackingDTO rsTrackingDTO = new CostTrackingDTO(part);

	model.addAttribute("trackingDTO", rsTrackingDTO);
	model.addAttribute("lpartOid", partOid);
	model.addAttribute("rPartOid", rPartOid);

	return model;
    }

    /**
     * <pre>
     * @description 수익율 추적관리 검색 목록
     * @author dhkim
     * @date 2018. 4. 17. 오후 5:00:45
     * @method findPagingList
     * @param trackingDTO
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(CostTrackingDTO trackingDTO) throws Exception {

	if (trackingDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = CostHelper.service.findPaging(trackingDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	LOGGER.info(qr.size());

	List<CostTrackingDTO> trackingDTOList = new ArrayList<CostTrackingDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    CostTrackingDTO rsTrackingDTO = new CostTrackingDTO((CostPart) objArr[0]);
	    trackingDTOList.add(rsTrackingDTO);
	}

	return EjsConverUtil.convertToDto(trackingDTOList, pageControl);
    }

    /**
     * <pre>
     * @description 수익율 추적관리 검색 화면
     * @author dhkim
     * @date 2018. 4. 17. 오후 5:00:54
     * @method ProjectCostTrackingList
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/ProjectCostTrackingList")
    public Model ProjectCostTrackingList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	String costPartNos = StringUtils.removeEnd((String) reqMap.get("costPartNos"), ",");
	String pjtNos = StringUtils.removeEnd((String) reqMap.get("pjtNos"), ",");

	LOGGER.info("############# Call ProjectCostTrackingList ####################");

	model.addAttribute("isAuth", CommonUtil.isMember("원가관리") || CommonUtil.isMember("원가조회"));
	model.addAttribute("costPartNos", costPartNos);
	model.addAttribute("pjtNos", pjtNos);
	return model;
    }

    /**
     * <pre>
     * @description 보고서 정보 저장
     * @author dhkim
     * @date 2018. 3. 22. 오후 2:09:02
     * @method saveCostReport
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCostReport")
    public Map<String, Object> saveCostReport(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## saveCostReport call ##############");

	    CostReport cr = CostHelper.service.saveCostReport(reqMap);

	    resMap.put("message", "저장되었습니다.");
	    resMap.put("result", true);
	    resMap.put("releaseStep", cr.getReleaseStep());
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 원가 보고서 화면
     * @author dhkim
     * @date 2018. 3. 15. 오후 3:40:04
     * @method costReportPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costReportPopup")
    public Model costReportPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String taskOid = (String) reqMap.get("taskOid");
	LOGGER.info("############# Call costReportPopup ####################");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	if (!task.isCostRequest()) {
	    task.setCostRequest(true);
	    PersistenceHelper.manager.save(task);
	}
	E3PSProject project = (E3PSProject) task.getProject();
	String caseOrder = StringUtil.checkReplaceStr((String) reqMap.get("caseOrder"), "1");

	E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

	String projectOid = CommonUtil.getOIDString(master);
	String version = task.getCostVersion();
	CostReport report = CostUtil.manager.getCostReport(projectOid, version);
	reqMap.put("reportOid", CommonUtil.getOIDString(report));
	Map<String, Object> reportData = CostUtil.manager.getReportData(reqMap);
	String auth = CostUtil.manager.getCostAuthInfo(reqMap);

	if ("ERROR".equals(auth)) {
	    boolean authCheck = false;
	    String docOid = StringUtil.checkNull((String) reqMap.get("docOid"));
	    KETProjectDocument doc = (KETProjectDocument) CommonUtil.getObject(docOid);

	    if (doc != null) {
		WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();

		if (loginUser.getName().equals(doc.getCreator().getName())) {
		    authCheck = true;
		} else {
		    authCheck = WorkflowSearchHelper.manager.userInApproval(doc, loginUser.getName());
		}

	    }

	    if (authCheck) {
		model.addAttribute("EDITMODE", "VIEW");
	    } else {
		model.addAttribute("ERROR", "권한이 없습니다.");
	    }

	} else {

	    boolean isCreator = false;

	    if (report == null) {
		report = CostHelper.service.saveCostReport(reqMap);
	    }

	    WTUser creator = (WTUser) report.getCreator().getPrincipal();
	    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	    isCreator = creator.getName().equals(sessionUser.getName());

	    model.addAttribute("reportData", reportData);
	    model.addAttribute("report", report);
	    model.addAttribute("task", task);
	    model.addAttribute("taskOid", taskOid);
	    model.addAttribute("reportOid", CommonUtil.getOIDString(report));
	    model.addAttribute("project", project);
	    model.addAttribute("caseOrder", caseOrder);
	    model.addAttribute("isCreator", isCreator);
	    model.addAttribute("EDITMODE", auth);

	}
	model.addAttribute("authProjectCheckOid", (String) reqMap.get("authProjectCheckOid"));

	return model;
    }

    @RequestMapping("/costReportExcelPopup")
    public Model costReportExcelPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String taskOid = (String) reqMap.get("taskOid");
	LOGGER.info("############# Call costReportPopup ####################");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();

	E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

	String projectOid = CommonUtil.getOIDString(master);
	String version = task.getCostVersion();

	CostReport report = CostUtil.manager.getCostReport(projectOid, version);

	reqMap.put("reportOid", CommonUtil.getOIDString(report));
	Map<String, Object> reportData = CostUtil.manager.getReportData(reqMap);

	model.addAttribute("reportData", reportData);
	model.addAttribute("report", report);
	model.addAttribute("task", task);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("reportOid", CommonUtil.getOIDString(report));
	model.addAttribute("project", project);

	String auth = CostUtil.manager.getCostAuthInfo(reqMap);

	if ("ERROR".equals(auth)) {
	    model.addAttribute("ERROR", "권한이 없습니다.");
	} else {
	    model.addAttribute("EDITMODE", auth);
	}

	return model;
    }

    /**
     * <pre>
     * @description 투자비 정보
     * @author dhkim
     * @date 2018. 3. 12. 오전 11:30:59
     * @method costInvestGridData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/costInvestGridData")
    public Map<String, Object> costInvestGridData(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> result = new HashMap<String, Object>();

	try {

	    List<Object> body = new ArrayList<Object>();

	    List<Map<String, Object>> data = CostUtil.manager.costInvestGridData(reqMap);
	    body.add(data);

	    result.put("Body", body);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description 원가 재산출
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:40:01
     * @method reCalculateCostPart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/reCalculateCostPart")
    public Map<String, Object> reCalculateCostPart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## reCalculateCostPart call ##############");
	    resMap = CostHelper.service.reCalculateCostPart(reqMap);

	    resMap.put("message", "재산출이 완료되었습니다.");
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
     * @description 원가 산출 수식 디버깅
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:39:59
     * @method getAttrCaluateFormula
     * @param reqMap
     * @return Map<String,String>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/getAttrCaluateFormula")
    public Map<String, Object> getAttrCaluateFormula(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## getAttrCaluateFormula call ##############");

	    String code = (String) reqMap.get("code");
	    CostPart part = (CostPart) CommonUtil.getObject((String) reqMap.get("oid"));
	    Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);

	    // 속성값 직접 설정#######################################
	    Map<String, Object> directAttr = (Map<String, Object>) reqMap.get("directAttr");
	    if (directAttr != null) {
		Set<String> st = directAttr.keySet();

		Iterator<String> it = st.iterator();

		while (it.hasNext()) {
		    String key = it.next();
		    String value = String.valueOf(directAttr.get(key));
		    partMap.put(key, value);
		}
	    }
	    // 속성값 직접 설정#######################################

	    resMap = CostCalculateUtil.manager.getAttrCaluateFormula(partMap, code);

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
     * @description 원가 BOM 필수값 체크
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:40:06
     * @method bomEditorNeedCheck
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/bomEditorNeedCheck")
    public Map<String, Object> bomEditorNeedCheck(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## bomEditorNeedCheck call ##############");

	    resMap = CostTreeUtil.manager.bomEditorNeedCheck(reqMap);

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
     * @description 원가 제품 및 부품 구조 체크
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:40:10
     * @method treePartTypeCheck
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/treePartTypeCheck")
    public Map<String, Object> treePartTypeCheck(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("########################## treePartTypeCheck call ##############");

	    resMap = CostTreeUtil.manager.treePartTypeCheck(reqMap);

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
     * @description BOM EXCEL 업로드
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:40:14
     * @method bomExcelUpload
     * @param reqMap
     * @param file
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/bomExcelUpload")
    public Map<String, Object> bomExcelUpload(@RequestParam Map<String, Object> reqMap, @RequestParam("uploadFile") MultipartFile file) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    LOGGER.info("file ==== " + file.getInputStream());

	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	    String tmpFileName = sdf.format(cal.getTime());

	    File uploadFile = new File(DownloadView.TEMPPATH + File.separator + tmpFileName + "_" + file.getOriginalFilename());
	    file.transferTo(uploadFile);
	    LOGGER.info("uploadFile ==== " + uploadFile.getAbsolutePath());
	    CostBomUploadUtil.manager.bomExcelUpload(reqMap, uploadFile);

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
     * @description 감가비 화면
     * @author dhkim
     * @date 2018. 2. 23. 오후 3:19:11
     * @method costReductionPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costReductionPopup")
    public Model costReductionPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	String oid = reqMap.get("oid");
	String taskOid = reqMap.get("taskOid");
	model.addAttribute("oid", oid);

	Map<String, Object> isEditCheckMap = isEditPart(taskOid, oid);

	boolean isBasePart = false;

	if ((boolean) isEditCheckMap.get("isBasePart")) {
	    isBasePart = true;
	} else {
	    isBasePart = (boolean) isEditCheckMap.get("isTaskComplete");
	}

	model.addAttribute("isBasePart", isBasePart);

	Map<String, String> codeMap = NumberCodeHelper.manager.getNumberCode("COSTREDUCEOPTION");
	Set<String> st = codeMap.keySet();
	Iterator<String> it = st.iterator();

	String enumNames = "|";
	String enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    if ("REDU004".equals(code)) {
		continue;
	    }
	    enumNames += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("reduceCodeEnumNames", enumNames);
	model.addAttribute("reduceCodeEnumKeys", enumKeys);

	return model;
    }

    /**
     * <pre>
     * @description 투자비 정보 저장
     * @author dhkim
     * @date 2018. 2. 23. 오후 3:19:37
     * @method saveCostInvestInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCostInvestInfo")
    public Map<String, Object> saveCostInvestInfo(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, Object> ioMap = new HashMap<String, Object>();
	try {

	    CostHelper.service.saveCostInvestInfo(reqMap);

	    ioMap.put("Result", 0);
	    ioMap.put("Reload", 1);

	} catch (Exception e) {

	    ioMap.put("Result", -1);
	    e.printStackTrace();
	}

	resMap.put("IO", ioMap);

	return resMap;
    }

    /**
     * <pre>
     * @description 감가비 목록 (금형/설비/기타)
     * @author dhkim
     * @date 2018. 2. 23. 오후 3:19:41
     * @method costReductionGridData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/costReductionGridData")
    public Map<String, Object> costReductionGridData(@RequestParam Map<String, Object> reqMap) {

	LOGGER.info("########################## costReductionGridData call ##############");

	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    List<Object> body = new ArrayList<Object>();

	    List<Map<String, Object>> data = CostUtil.manager.costReductionGridData(reqMap);
	    body.add(data);

	    result.put("Body", body);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description CASE 생성
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:34:16
     * @method createCasePart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/createCasePart")
    public Map<String, Object> createCasePart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.createCasePart(reqMap);

	    resMap.put("message", "생성되었습니다.");
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
     * @description CASE 삭제
     * @author dhkim
     * @date 2018. 10. 30. 오후 3:22:32
     * @method deleteCasePart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/deleteCasePart")
    public Map<String, Object> deleteCasePart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.deleteCasePart(reqMap);

	    resMap.put("message", "삭제되었습니다.");
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
     * @description CASE 정보 저장
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:34:33
     * @method saveCasePart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCasePart")
    public Map<String, Object> saveCasePart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.saveCasePart(reqMap);

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
     * @description 환율/비철원재료/범용&기계감가 표준 정보 저장
     * @author dhkim
     * @date 2018. 1. 30. 오후 2:58:31
     * @method saveCostPartInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCostPartInfo")
    public Map<String, Object> saveCostPartInfo(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.saveCostPartInfo(reqMap);

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
     * @description 환율/물량/수지원재료/비철원재료/범용&기계감가 표준 정보
     * @author dhkim
     * @date 2018. 1. 25. 오전 9:16:52
     * @method getCostPartInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getCostPartInfo")
    public Map<String, Object> getCostPartInfo(@RequestBody Map<String, Object> reqMap) {

	LOGGER.info("######## CALL GET COST PART INFO ########");

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    resMap = CostUtil.manager.getCostPartInfo(reqMap);

	    resMap.put("result", true);
	} catch (Exception e) {
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 재료비/노무비/경비/관리비/CAPA 정보 화면
     * @author dhkim
     * @date 2018. 1. 30. 오후 3:14:41
     * @method costCalculateGrid
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costCalculateGrid")
    public Model costCalculateGrid(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	String taskOid = reqMap.get("taskOid");
	String oid = reqMap.get("oid");
	String costType = reqMap.get("COSTTYPE");
	model.addAttribute("oid", oid);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("COSTTYPE", costType);

	Map<String, Object> isEditCheckMap = isEditPart(taskOid, oid);

	boolean isBasePart = false;

	if ((boolean) isEditCheckMap.get("isBasePart")) {
	    isBasePart = true;
	} else {
	    isBasePart = (boolean) isEditCheckMap.get("isTaskComplete");
	}

	model.addAttribute("isBasePart", isBasePart);

	Map<String, String> codeMap = NumberCodeHelper.manager.getNumberCode("MOLDDIVISION");
	Set<String> st = codeMap.keySet();
	Iterator<String> it = st.iterator();

	String enumName = "|";
	String enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("mDivisionNames", enumName);
	model.addAttribute("mDivisionKeys", enumKeys);

	codeMap = NumberCodeHelper.manager.getNumberCode("FACDIVISION");
	st = codeMap.keySet();
	it = st.iterator();

	enumName = "|";
	enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("fDivisionNames", enumName);
	model.addAttribute("fDivisionKeys", enumKeys);

	List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("CALCULATIONSTD");

	enumName = "|";
	enumKeys = "|";

	Map<String, String> childEnumProp = new HashMap<String, String>();

	for (NumberCode code : codeList) {

	    if (code.getParent() != null) {

		String pCode = code.getParent().getCode();

		String childEnum = childEnumProp.get("Enum" + pCode);
		String childEnumKeys = childEnumProp.get("EnumKeys" + pCode);

		if (childEnum != null) {
		    childEnum += "|" + code.getName();
		    childEnumKeys += "|" + code.getCode();
		} else {
		    childEnum = "|" + code.getName();
		    childEnumKeys = "|" + code.getCode();
		}

		childEnumProp.put("Enum" + pCode, childEnum);
		childEnumProp.put("EnumKeys" + pCode, childEnumKeys);

	    } else {
		if (costType.equals(code.getDescription())) {
		    enumName += "|" + code.getName();
		    enumKeys += "|" + code.getCode();
		}
	    }
	}

	model.addAttribute("calcStdNames", enumName);
	model.addAttribute("calcStdKeys", enumKeys);
	model.addAttribute("calcOptionProp", new JSONObject(childEnumProp));
	return model;
    }

    /**
     * <pre>
     * @description 투자비 정보 화면
     * @author dhkim
     * @date 2018. 3. 12. 오전 11:03:05
     * @method costInvestGrid
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costInvestGrid")
    public Model costInvestGrid(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	String taskOid = (String) reqMap.get("taskOid");
	String oid = (String) reqMap.get("oid");
	String EDITMODE = (String) reqMap.get("EDITMODE");
	String caseOrder = (String) reqMap.get("caseOrder");

	model.addAttribute("oid", oid);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("EDITMODE", EDITMODE);
	model.addAttribute("caseOrder", caseOrder);

	List<NumberCode> costReduceOption = NumberCodeHelper.manager.getNumberCodeList("COSTREDUCEOPTION");

	String reduceCodeKeys = "|";
	String reduceCodeNames = "|";

	for (NumberCode code : costReduceOption) {
	    reduceCodeKeys += "|" + code.getCode();
	    reduceCodeNames += "|" + code.getName();
	}

	model.addAttribute("reduceCodeEnum", reduceCodeNames);
	model.addAttribute("reduceCodeEnumKeys", reduceCodeKeys);

	return model;
    }

    public Map<String, Object> isEditPart(String taskOid, String oid) {

	CostPart part = (CostPart) CommonUtil.getObject(oid);
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	Map<String, Object> resMap = new HashMap<String, Object>();

	boolean isBasePart = part.getSubVersion() == 0;
	boolean isTaskComplete = task.getTaskState() == 5;

	resMap.put("isBasePart", isBasePart);
	resMap.put("isTaskComplete", isTaskComplete);

	return resMap;
    }

    /**
     * <pre>
     * @description 원가 산출창 화면
     * @author dhkim
     * @date 2018. 1. 30. 오후 3:14:24
     * @method costPartCalculatePopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costPartCalculatePopup")
    public Model costPartCalculatePopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String taskOid = (String) reqMap.get("taskOid");
	String oid = (String) reqMap.get("oid");
	CostPart part = (CostPart) CommonUtil.getObject(oid);

	ProductMaster master = part.getMaster();

	PartList partList = master.getPartList();
	String partListOid = CommonUtil.getOIDString(partList);
	reqMap.put("partListOid", partListOid);
	model.addAttribute("oid", oid);
	model.addAttribute("part", part);
	model.addAttribute("partListOid", partListOid);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("version", CostUtil.manager.getLastestFormulaVersion(true));

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	if (task.getTaskState() != 5) {// 방어코드
	    CostCodeHelper.service.createCurrencyInfo(part);
	}
	if (!task.isCostRequest()) {
	    task.setCostRequest(true);
	    PersistenceHelper.manager.save(task);
	}

	TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);
	boolean isEdit = buttonAuth.isComplated;
	if (!isEdit) {// task가 완료되지 않았다면 해당 버전의 보고서 완료여부를 체크한다
	    CostReport report = CostUtil.manager.getCostReport(CommonUtil.getOIDString(task.getProject()),
		    String.valueOf(part.getVersion()));
	    if (report != null) {
		String state = report.getState().toString();
		isEdit = state.equals("APPROVED");
	    }
	}
	model.addAttribute("taskComplated", isEdit);

	// CostCodeHelper.service.initCost(reqMap);

	List<Map<String, String>> monetaryUnit = NumberCodeHelper.manager.getNumberCodeMapList("MONETARYUNIT");
	List<Map<String, String>> scenario = NumberCodeHelper.manager.getNumberCodeMapList("SCENARIO");

	model.addAttribute("monetaryUnit", monetaryUnit);
	model.addAttribute("scenario", scenario);

	Map<String, Object> isEditCheckMap = isEditPart(taskOid, oid);

	boolean isBasePart = (boolean) isEditCheckMap.get("isBasePart");
	boolean isTaskComplete = (boolean) isEditCheckMap.get("isTaskComplete");

	if (isTaskComplete) {
	    isBasePart = true;
	}

	model.addAttribute("isBasePart", isBasePart);
	model.addAttribute("isTaskComplete", isTaskComplete);

	return model;
    }

    /**
     * <pre>
     * @description 원가 산출 CASE 목록
     * @author dhkim
     * @date 2018. 10. 30. 오후 2:12:46
     * @method getCaseList
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getCaseList")
    public Map<String, Object> getCaseList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    String oid = (String) reqMap.get("oid");
	    CostPart part = (CostPart) CommonUtil.getObject(oid);
	    ProductMaster master = part.getMaster();

	    reqMap.remove("oid");
	    reqMap.put("masterOid", CommonUtil.getOIDString(master));
	    reqMap.put("version", String.valueOf(part.getVersion()));
	    reqMap.put("DATATYPE", "COSTPART");
	    List<Map<String, Object>> caseList = CostHelper.service.getCostRootMapList(reqMap);

	    resMap.put("caseList", caseList);
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
     * @description 비철 원가기준정보 저장 화면
     * @author dhkim
     * @date 2018. 1. 3. 오전 10:22:31
     * @method saveNMetalCSIPopup
     * @param model
     * @return Model
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/saveNMetalCSIPopup")
    public Model saveNMetalCSIPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
	LOGGER.info("######## CALL NON METAL COST STANDARD INFO POPUP ########");

	List<Map<String, String>> rawMaterial = NumberCodeHelper.manager.getNumberCodeMapList("RAWMATERIAL");
	List<Map<String, String>> scenario = NumberCodeHelper.manager.getNumberCodeMapList("SCENARIO");
	List<Map<String, String>> rawMatThickness = NumberCodeHelper.manager.getNumberCodeMapList("RAWMATTHICKNESS");
	List<Map<String, String>> rawMatWidth = NumberCodeHelper.manager.getNumberCodeMapList("RAWMATWIDTH");
	List<NumberCode> tempFac = NumberCodeHelper.manager.getTopNumberCode(NumberCodeType.toNumberCodeType("MFTFACTORY"));
	List<Map<String, String>> moneytaryUnit = NumberCodeHelper.manager.getNumberCodeMapList("MONETARYUNIT");

	List<Map<String, String>> plating = NumberCodeHelper.manager.getNumberCodeChildMapList("PLATING", "PTG");
	List<Map<String, String>> factory = new ArrayList<Map<String, String>>();
	for (NumberCode fac : tempFac) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("id", CommonUtil.getOIDString(fac));
	    map.put("code", fac.getCode());
	    map.put("name", fac.getName());
	    factory.add(map);
	}

	model.addAttribute("MONETARYUNIT", new JSONArray(moneytaryUnit));
	model.addAttribute("RAWMATERIAL", rawMaterial);
	model.addAttribute("SCENARIO", new JSONArray(scenario));
	model.addAttribute("RAWMATTHICKNESS", new JSONArray(rawMatThickness));
	model.addAttribute("RAWMATWIDTH", new JSONArray(rawMatWidth));
	model.addAttribute("PLATING", new JSONArray(plating));
	model.addAttribute("FACTORY", new JSONArray(factory));

	String oid = reqMap.get("oid");
	String rMatCode = reqMap.get("rMatCode");
	model.addAttribute("rMatCode", rMatCode);

	if (StringUtil.checkString(oid)) {
	    CSInfo csInfo = (CSInfo) CommonUtil.getObject(oid);

	    int version = csInfo.getVersion();
	    String date = DateUtil.getDateString(csInfo.getPersistInfo().getCreateStamp(), "d");
	    model.addAttribute("version", version + " (" + date + ")");
	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    model.addAttribute("csInfo", csInfo);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	}

	return model;
    }

    /**
     * <pre>
     * @description 비철 원가기준정보 상세화면
     * @author dhkim
     * @date 2018. 1. 4. 오후 2:07:23
     * @method viewNMetalCSIPopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/viewNMetalCSIPopup")
    public Model viewNMetalCSIPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
	LOGGER.info("######## CALL NON METAL COST STANDARD INFO POPUP ########");

	String oid = reqMap.get("oid");
	String rMatCode = StringUtil.checkNull(reqMap.get("rMatCode"));

	List<Map<String, String>> rMatCodeList = NumberCodeHelper.manager.getNumberCodeMapList("RAWMATERIAL");
	model.addAttribute("rMatCodeList", rMatCodeList);

	if (!StringUtil.checkString(rMatCode) && rMatCodeList.size() > 0) {
	    rMatCode = rMatCodeList.get(0).get("code");
	}

	String infoType = "NMETAL" + rMatCode;

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);
	if (csInfo != null) {
	    model.addAttribute("latestOid", CommonUtil.getOIDString(csInfo));
	}

	if (StringUtil.checkString(oid)) {
	    csInfo = (CSInfo) CommonUtil.getObject(oid);
	    rMatCode = csInfo.getRMatCode();
	}

	model.addAttribute("rMatCode", rMatCode);

	if (csInfo != null) {

	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);

	    NumberCode code = CodeHelper.manager.getNumberCode("RAWMATERIAL", csInfo.getRMatCode());

	    if (code != null) {
		csInfo.setRMatCode(code.getName());
	    }

	    List<CSInfo> infoList = CostUtil.manager.getAllCSInfo(infoType);

	    List<Map<String, String>> versionList = new ArrayList<Map<String, String>>();

	    for (CSInfo info : infoList) {

		Map<String, String> ver = new HashMap<String, String>();

		String date = DateUtil.getDateString(info.getPersistInfo().getCreateStamp(), "d");

		ver.put("version", info.getVersion() + " (" + date + ")");

		ver.put("oid", CommonUtil.getOIDString(info));

		versionList.add(ver);
	    }
	    model.addAttribute("versionList", versionList);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	    model.addAttribute("oid", CommonUtil.getOIDString(csInfo));
	}

	model.addAttribute("csInfo", csInfo);

	return model;
    }

    /**
     * <pre>
     * @description 원가기준정보 저장/개정
     * @author dhkim
     * @date 2018. 1. 4. 오후 2:07:44
     * @method saveCSInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCSInfo")
    public Map<String, Object> saveCSInfo(@RequestBody Map<String, Object> reqMap) {

	LOGGER.info("######## CALL SAVE COST STANDARD INFO ########");

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    String mode = (String) reqMap.get("mode");

	    if ("SAVE".equals(mode)) {
		resMap = CostHelper.service.saveCSInfo(reqMap);
	    } else if ("REVISE".equals(mode)) {
		resMap = CostHelper.service.reviseCSInfo(reqMap);
	    }

	    resMap.put("result", true);
	} catch (Exception e) {
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 원가기준정보 삭제 
     * @author dhkim
     * @date 2018. 1. 4. 오후 2:08:03
     * @method deleteCSInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/deleteCSInfo")
    public Map<String, Object> deleteCSInfo(@RequestBody Map<String, Object> reqMap) {

	LOGGER.info("######## CALL DELETE COST STANDARD INFO ########");

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    String infoType = (String) reqMap.get("infoType");
	    CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);
	    resMap = CostHelper.service.deleteCSInfo(csInfo);

	    resMap.put("result", true);
	} catch (Exception e) {
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 수지 원가기준정보 저장 화면
     * @author dhkim
     * @date 2018. 1. 4. 오후 2:08:21
     * @method profitCSIPopup
     * @param model
     * @return Model
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/saveProfitCSIPopup")
    public Model saveProfitCSIPopup(Model model) throws Exception {
	LOGGER.info("######## CALL PROFIT COST STANDARD INFO POPUP ########");

	List<Map<String, String>> profit = NumberCodeHelper.manager.getNumberCodeMapList("RESINSDIVISION");

	List<NumberCode> tempFac = NumberCodeHelper.manager.getTopNumberCode(NumberCodeType.toNumberCodeType("MFTFACTORY"));
	List<Map<String, String>> factory = new ArrayList<Map<String, String>>();
	for (NumberCode fac : tempFac) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("id", CommonUtil.getOIDString(fac));
	    map.put("code", fac.getCode());
	    map.put("name", fac.getName());
	    factory.add(map);
	}

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo("PROFIT");

	if (csInfo != null) {
	    int version = csInfo.getVersion();
	    String date = DateUtil.getDateString(csInfo.getPersistInfo().getCreateStamp(), "d");
	    model.addAttribute("version", version + " (" + date + ")");
	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    model.addAttribute("csInfo", csInfo);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	}
	model.addAttribute("FACTORY", new JSONArray(factory));
	model.addAttribute("PROFIT", new JSONArray(profit));
	return model;
    }

    /**
     * <pre>
     * @description 수지 원가기준정보 상세 화면
     * @author dhkim
     * @date 2018. 1. 5. 오전 11:58:33
     * @method viewProfitCSIPopup
     * @param model
     * @return
     * @throws Exception Model
     * </pre>
     */
    @RequestMapping("/viewProfitCSIPopup")
    public Model viewProfitCSIPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
	LOGGER.info("######## CALL PROFIT COST STANDARD INFO POPUP ########");

	String oid = reqMap.get("oid");

	String infoType = "PROFIT";

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);

	if (csInfo != null) {
	    model.addAttribute("latestOid", CommonUtil.getOIDString(csInfo));
	}

	if (StringUtil.checkString(oid)) {
	    csInfo = (CSInfo) CommonUtil.getObject(oid);
	}

	if (csInfo != null) {

	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    List<CSInfo> infoList = CostUtil.manager.getAllCSInfo(infoType);

	    List<Map<String, String>> versionList = new ArrayList<Map<String, String>>();

	    for (CSInfo info : infoList) {

		Map<String, String> ver = new HashMap<String, String>();

		String date = DateUtil.getDateString(info.getPersistInfo().getCreateStamp(), "d");

		ver.put("version", info.getVersion() + " (" + date + ")");

		ver.put("oid", CommonUtil.getOIDString(info));

		versionList.add(ver);
	    }
	    model.addAttribute("versionList", versionList);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	    model.addAttribute("oid", CommonUtil.getOIDString(csInfo));
	}

	model.addAttribute("csInfo", csInfo);

	return model;
    }

    /**
     * <pre>
     * @description 환율정보 저장 화면
     * @author dhkim
     * @date 2018. 1. 4. 오후 2:08:21
     * @method saveExRateCSIPopup
     * @param model
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/saveExRateCSIPopup")
    public Model saveExRateCSIPopup(Model model) throws Exception {
	LOGGER.info("######## CALL PROFIT COST STANDARD INFO POPUP ########");

	List<Map<String, String>> monetaryUnit = NumberCodeHelper.manager.getNumberCodeMapList("MONETARYUNIT");

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

	if (csInfo != null) {
	    int version = csInfo.getVersion();
	    String date = DateUtil.getDateString(csInfo.getPersistInfo().getCreateStamp(), "d");
	    model.addAttribute("version", version + " (" + date + ")");
	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    model.addAttribute("csInfo", csInfo);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	}

	model.addAttribute("MONETARYUNIT", new JSONArray(monetaryUnit));
	return model;
    }

    /**
     * <pre>
     * @description 환율정보 상세 화면
     * @author dhkim
     * @date 2018. 1. 5. 오전 11:58:33
     * @method viewExRateCSIPopup
     * @param model
     * @return
     * @throws Exception Model
     * </pre>
     */
    @RequestMapping("/viewExRateCSIPopup")
    public Model viewExRateCSIPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
	LOGGER.info("######## CALL PROFIT COST STANDARD INFO POPUP ########");

	String oid = reqMap.get("oid");

	String infoType = "EXRATE";

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);
	if (csInfo != null) {
	    model.addAttribute("latestOid", CommonUtil.getOIDString(csInfo));
	}

	if (StringUtil.checkString(oid)) {
	    csInfo = (CSInfo) CommonUtil.getObject(oid);
	}

	if (csInfo != null) {

	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    List<CSInfo> infoList = CostUtil.manager.getAllCSInfo(infoType);

	    List<Map<String, String>> versionList = new ArrayList<Map<String, String>>();

	    for (CSInfo info : infoList) {

		Map<String, String> ver = new HashMap<String, String>();

		String date = DateUtil.getDateString(info.getPersistInfo().getCreateStamp(), "d");

		ver.put("version", info.getVersion() + " (" + date + ")");

		ver.put("oid", CommonUtil.getOIDString(info));

		versionList.add(ver);
	    }
	    model.addAttribute("versionList", versionList);
	    model.addAttribute("csInfoItemList", csInfoItemList);
	    model.addAttribute("oid", CommonUtil.getOIDString(csInfo));
	}

	model.addAttribute("csInfo", csInfo);

	return model;
    }

    /**
     * <pre>
     * @description 원가 속성 선택 팝업 화면
     * @author dhkim
     * @date 2017. 12. 13. 오후 3:33:14
     * @method selectCostAttrPopup
     * @param model
     * @return
     * @throws Exception Model
     * </pre>
     */
    @RequestMapping("/selectCostAttrPopup")
    public Model selectCostAttrPopup(Model model) throws Exception {
	LOGGER.info("######## CALL SELECTCOSTATTR POPUP ########");
	return model;
    }

    /**
     * <pre>
     * @description 원가 속성 선택 화면
     * @author dhkim
     * @date 2017. 12. 13. 오후 3:30:35
     * @method selectCostAttribute
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/selectCostAttribute")
    public Model selectCostAttribute(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	LOGGER.info("######## CALL SELECT COST ATTRIBUTE ########");

	List<Map<String, String>> list = CostUtil.manager.getCostAttrMapList(reqMap);

	model.addAttribute("attrList", list);
	model.addAttribute("useType", reqMap.get("useType"));
	model.addAttribute("isAddOption", reqMap.get("isAddOption"));
	return model;
    }

    /**
     * <pre>
     * @description 원가 BOM 저장
     * @author dhkim
     * @date 2017. 11. 30. 오후 1:53:25
     * @method saveCostPart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCostPart")
    public Map<String, Object> saveCostPart(@RequestParam Map<String, Object> reqMap) {

	System.out.println("saveCostPart Controller Start");
	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, Object> ioMap = new HashMap<String, Object>();

	try {

	    resMap = CostHelper.service.saveCostPart(reqMap);

	    ioMap.put("Result", 0);
	    ioMap.put("Reload", 1);

	} catch (Exception e) {
	    // LOGGER.error("saveCostPart ################ " + e.getLocalizedMessage());
	    ioMap.put("Result", -1);
	    e.printStackTrace();
	}
	System.out.println("saveCostPart Controller End");
	resMap.put("IO", ioMap);

	return resMap;
    }

    /**
     * <pre>
     * @description 원가 BOM 정보
     * @author dhkim
     * @date 2017. 12. 8. 오후 12:12:08
     * @method costBomEditorData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/costTreeGridData")
    public Map<String, Object> costTreeGridData(@RequestParam Map<String, Object> reqMap) {

	// LOGGER.info("########################## costTreeGridData call
	// ##############");

	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    List<Object> body = new ArrayList<Object>();
	    CostUtil.manager.setCalcAuth(reqMap);
	    List<Map<String, Object>> data = CostTreeUtil.manager.getTreeGridData(reqMap);
	    body.add(data);
	    result.put("result", true);
	    result.put("Body", body);

	} catch (Exception e) {
	    result.put("message", e.getLocalizedMessage());
	    result.put("result", false);
	    e.printStackTrace();
	}

	return result;
    }

    @ResponseBody
    @RequestMapping("/costInterfaceTreeGridData")
    public Map<String, Object> costInterfaceTreeGridData(@RequestParam Map<String, Object> reqMap) {

	// LOGGER.info("########################## costTreeGridData call
	// ##############");

	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    List<Object> body = new ArrayList<Object>();
	    CostUtil.manager.setCalcAuth(reqMap);
	    List<Map<String, Object>> data = CostTreeUtil.manager.getInterfaceTreeGridData(reqMap);
	    body.add(data);
	    result.put("result", true);
	    result.put("Body", body);

	    CostTreeUtil.manager.gridFootAdd(result, data, reqMap);

	} catch (Exception e) {
	    result.put("message", e.getLocalizedMessage());
	    result.put("result", false);
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description 원가산출결과 정보
     * @author dhkim
     * @date 2018. 1. 30. 오전 9:53:04
     * @method getCostCalcResult
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getCostCalcResult")
    public Map<String, Object> getCostCalcResult(@RequestBody Map<String, Object> reqMap) {

	LOGGER.info("########################## getCostCalcResult call ##############");

	Map<String, Object> resMap = new HashMap<String, Object>();
	try {

	    // CostCodeHelper.service.initCost(reqMap);
	    resMap = CostTreeUtil.manager.getCostPartMap(reqMap);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 매출액 및 영업이익 정보
     * @author dhkim
     * @date 2018. 2. 23. 오후 3:20:16
     * @method saveCostAnalysisInfo
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveCostAnalysisInfo")
    public Map<String, Object> saveCostAnalysisInfo(@RequestBody Map<String, Object> reqMap) {

	LOGGER.info("########################## saveCostAnalysisInfo call ##############");

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    boolean isSave = (boolean) reqMap.get("isSave");

	    resMap = CostHelper.service.saveCostAnalysisInfo(reqMap);

	    resMap.put("result", true);

	    if (isSave) {
		resMap.put("message", "저장되었습니다.");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 수식 입력창 화면
     * @author dhkim
     * @date 2017. 11. 29. 오후 1:38:00
     * @method costCalculatior
     * @param model
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/costCalculatorPopup")
    public Model costCalculatorPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
	return model;
    }

    /**
     * <pre>
     * @description 수식관리 화면
     * @author dhkim
     * @date 2017. 12. 8. 오후 12:12:37
     * @method costFormulaEditorPopup
     * @param model
     * @return Model
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/costFormulaEditorPopup")
    public Model costFormulaEditorPopup(Model model) throws Exception {

	LOGGER.info("######## CALL COSTFORMULAEDITOR ########");

	List<NumberCode> calcStdList = NumberCodeHelper.manager.getTopNumberCode(NumberCodeType.toNumberCodeType("CALCULATIONSTD"));

	String eNum = "|";
	String eNumKeys = "|";

	for (NumberCode calcStd : calcStdList) {
	    eNumKeys += "|" + calcStd.getCode();
	    eNum += "|" + calcStd.getName();
	}

	model.addAttribute("calcStdEnum", eNum);
	model.addAttribute("calcStdEnumKeys", eNumKeys);
	model.addAttribute("version", CostUtil.manager.getLastestFormulaVersion());

	boolean isCostManager = CommonUtil.isMember("원가관리", (WTUser) SessionHelper.manager.getPrincipal());

	model.addAttribute("auth", isCostManager);

	return model;
    }

    /**
     * <pre>
     * @description 원가 수식 연산 구조 저장
     * @author dhkim
     * @date 2017. 12. 8. 오후 12:13:04
     * @method saveFormulaTree
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/saveCostFormula")
    public Map<String, Object> saveCostFormula(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, Object> ioMap = new HashMap<String, Object>();
	try {
	    resMap = CostHelper.service.saveCostFormula(reqMap);

	    Map<String, String> updateList = (Map<String, String>) resMap.get("updateList");
	    resMap.clear();
	    Set<String> st = updateList.keySet();
	    Iterator<String> it = st.iterator();

	    while (it.hasNext()) {
		String oid = it.next();
		String formulaKeys = updateList.get(oid);
		CostFormula formula = (CostFormula) CommonUtil.getObject(oid);

		formula.setFormulaKeys(formulaKeys);
		PersistenceHelper.manager.save(formula);
	    }

	    ioMap.put("Result", 0);
	    ioMap.put("Reload", 1);

	    CostHelper.service.loadCostFormula();

	} catch (Exception e) {
	    LOGGER.error("saveCostFormula ################ " + e.getLocalizedMessage());
	    ioMap.put("Result", -1);
	    e.printStackTrace();
	}

	resMap.put("IO", ioMap);

	return resMap;
    }

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @메소드명 : saveCostPartType
     * @작성자 : 황정태
     * @작성일 : 2017. 12. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @ResponseBody
    @RequestMapping("/saveCostPartType")
    public Map<String, Object> saveCostPartType(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, Object> ioMap = new HashMap<String, Object>();
	try {
	    resMap = CostHelper.service.saveCostPartType(reqMap);

	    ioMap.put("Result", 0);
	    ioMap.put("Reload", 1);

	} catch (Exception e) {
	    LOGGER.error("saveCostPartType ################ " + e.getLocalizedMessage());
	    ioMap.put("Result", -1);
	    e.printStackTrace();
	}

	resMap.put("IO", ioMap);

	return resMap;
    }

    /**
     * khkim
     * 
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData(String code) throws Exception {
	System.out.println("getData code= " + code);
	Map<String, Object> pos = new HashMap<String, Object>();

	List<BomEditorColumnInfo> list = new ArrayList<BomEditorColumnInfo>();

	if (StringUtil.isEmpty(code)) {
	    return pos;
	}

	list = CostBomEditorColumnExcelUtil.getColumnInfos();

	Map<String, BomEditorACL> aclMap = BomEditorACLUtil.manager.getAclMap(code);

	for (BomEditorColumnInfo info : list) {
	    BomEditorACL acl = aclMap.get(info.getColumKey());
	    if (acl != null) {
		info.setView(acl.isViewable());
		info.setEditor(acl.isEditor());
	    }
	}

	pos.put("Items", list);
	// for()

	List<Map<String, Object>> bList = new ArrayList<Map<String, Object>>();
	bList.add(pos);

	Map<String, Object> body = new HashMap<String, Object>();
	body.put("Body", bList);

	return body;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/saveBomAcL")
    public Map<String, Object> saveBomAcL(@RequestParam Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();
	ObjectMapper mapper = new ObjectMapper();
	String code = (String) reqMap.get("code");

	System.out.println("treeDataStr====== " + code);
	Map<String, Object> ioMap = new HashMap<String, Object>();
	if (StringUtil.isEmpty(code)) {
	    ioMap.put("Result", -1);
	    resMap.put("IO", ioMap);
	    return null;
	}
	String treeDataStr = (String) reqMap.get("treeData");
	Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	});

	List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
	List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");
	WTArrayList wtlist = new WTArrayList();
	for (Map<String, Object> map : items) {

	    String columKey = (String) map.get("columKey");
	    String displayName = (String) map.get("displayName");
	    String description = (String) map.get("description");
	    String view = (String) map.get("view");
	    String editor = (String) map.get("editor");

	    boolean viewable = "1".equals(view);
	    boolean editable = "1".equals(editor);
	    ;

	    BomEditorACL acl = BomEditorACL.newBomEditorACL();
	    acl.setColumnKey(columKey);
	    acl.setDisplayName(displayName);
	    acl.setDescription(description);
	    acl.setViewable(viewable);
	    acl.setEditor(editable);
	    acl.setTaskCode(code);
	    wtlist.add(acl);
	}
	BomEditorACLUtil.manager.deleteAll(code);

	if (wtlist.size() > 0) {

	    PersistenceHelper.manager.save(wtlist);
	}

	ioMap.put("Result", 0);
	ioMap.put("Reload", 1);
	resMap.put("IO", ioMap);

	return resMap;
    }

    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/manage/costBomACLPopup")
    public Model costBomACLPopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	NumberCode costTypeCode = CodeHelper.manager.getNumberCode("TASKTYPE", "COST");

	ArrayList<NumberCode> list = NumberCodeHelper.manager.getChildNumberCode2(costTypeCode, "");
	System.out.println("costType list = " + list.size());
	Map<String, String> map = new HashMap<String, String>();
	for (NumberCode numberCode : list) {
	    if (numberCode.isDisabled()) {
		continue;
	    }

	    String code = numberCode.getCode();
	    String display = numberCode.getName();
	    map.put(code, display);
	}

	model.addAttribute("typeList", map);

	return model;
    }

    @RequestMapping("/manage/costPartTypeACL")
    public Model costPartTypeACL(Model model, @RequestParam Map<String, String> reqMap) throws Exception {

	List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");

	JSONArray mftFactoryList = new JSONArray();

	for (NumberCode code : codeList) {

	    JSONObject obj = new JSONObject();

	    obj.put("code", CommonUtil.getOIDLongValue(code));
	    obj.put("name", code.getName());

	    if (code.getParent() != null) {
		obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
	    }

	    mftFactoryList.put(obj);
	}

	Map<String, String> monetaryMap = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT");

	Set<String> st = monetaryMap.keySet();
	Iterator<String> it = st.iterator();

	String monetary = "";
	while (it.hasNext()) {
	    monetary = "|" + it.next() + monetary;
	}
	model.addAttribute("monetary", monetary);
	model.addAttribute("mftFactoryList", mftFactoryList.toString());

	return model;
    }

    @ResponseBody
    @RequestMapping("/costPartTypeLinkList")
    public Map<String, Object> costPartTypeLinkList(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    List<Object> body = new ArrayList<Object>();

	    List<Map<String, Object>> data = CostCodeHelper.service.getCostPartTypeLinkList();
	    // List<Map<String, Object>> data = CostCacheManager.getCostPTItem("PARTTYPELINKLIST");

	    for (Map<String, Object> map : data) {

		String mftfactorys = (String) map.get("mftFactory2");

		if (mftfactorys != null && mftfactorys.length() > 0) {
		    StringTokenizer st = new StringTokenizer(mftfactorys, ";");
		    int index = 2;
		    while (st.hasMoreElements()) {
			String oid = st.nextToken();
			String key = "mftFactory" + index;
			map.put(key, oid);
			index++;
		    }

		}

	    }

	    body.add(data);

	    result.put("Body", body);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    @RequestMapping("/getDataFromType")
    @ResponseBody
    public Map<String, Object> getDataFromType(String typeId) throws Exception {

	System.out.println("getData code= " + typeId);

	Map<String, Object> pos = new HashMap<String, Object>();

	List<BomEditorColumnInfo> list = new ArrayList<BomEditorColumnInfo>();

	if (StringUtil.isEmpty(typeId)) {
	    return pos;
	}

	StringTokenizer st = new StringTokenizer(typeId, "*");

	if (st.countTokens() < 3) {
	    return pos;
	}

	list = CostBomEditorColumnExcelUtil.getColumnInfosforType();

	Map<String, BomEditorACLFromType> aclMap = BomEditorACLUtil.manager.getAclMapForType(typeId);

	for (BomEditorColumnInfo info : list) {
	    BomEditorACLFromType acl = aclMap.get(info.getColumKey());
	    if (acl != null) {
		info.setNecessary(acl.isNecessary());
		info.setDisabled(acl.isDisabled());
	    }
	}

	pos.put("Items", list);
	// for()

	List<Map<String, Object>> bList = new ArrayList<Map<String, Object>>();
	bList.add(pos);

	Map<String, Object> body = new HashMap<String, Object>();
	body.put("Body", bList);

	return body;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/saveBomAcLForType")
    public Map<String, Object> saveBomAcLForType(@RequestParam Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();
	ObjectMapper mapper = new ObjectMapper();
	String typeId = (String) reqMap.get("typeId");

	System.out.println("typeId  ====== " + typeId);
	Map<String, Object> ioMap = new HashMap<String, Object>();
	if (StringUtil.isEmpty(typeId)) {
	    ioMap.put("Result", -1);
	    resMap.put("IO", ioMap);
	    return null;
	}
	String treeDataStr = (String) reqMap.get("treeData");
	Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	});

	List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
	List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");
	WTArrayList wtlist = new WTArrayList();
	for (Map<String, Object> map : items) {

	    String columKey = (String) map.get("columKey");
	    String displayName = (String) map.get("displayName");
	    String description = (String) map.get("description");
	    String necessary = (String) map.get("necessary");
	    // String disabled = (String)map.get("disabled");

	    boolean necessaryable = "1".equals(necessary);
	    // boolean isDisabled = "1".equals(disabled);;

	    BomEditorACLFromType acl = BomEditorACLFromType.newBomEditorACLFromType();
	    acl.setColumnKey(columKey);
	    acl.setDisplayName(displayName);
	    acl.setDescription(description);
	    acl.setNecessary(necessaryable);
	    ;
	    // acl.setDisabled(isDisabled);
	    acl.setTypeId(typeId);
	    wtlist.add(acl);
	}
	BomEditorACLUtil.manager.deleteAllForType(typeId);

	if (wtlist.size() > 0) {

	    PersistenceHelper.manager.save(wtlist);
	}

	ioMap.put("Result", 0);
	ioMap.put("Reload", 1);
	resMap.put("IO", ioMap);

	return resMap;
    }

    @RequestMapping("/checkCostRequest")
    @ResponseBody
    public Map<String, String> checkCostRequest(String taskOid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	boolean isOpen = true;
	String msg = "";
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String taskCategory = task.getTaskTypeCategory();

	if ("COST014".equals(taskCategory) || "COST015".equals(taskCategory) || "COST016".equals(taskCategory)) {// 판매목표가 등록,원가산출,보고서 등록

	    QueryResult qr = ProjectTaskHelper.manager.getChildList(task.getProject());
	    while (qr.hasMoreElements()) {
		E3PSTask checkTask = (E3PSTask) qr.nextElement();

		if ("COST013".equals(StringUtil.checkNull(checkTask.getTaskTypeCategory())) && checkTask.getTaskState() != 5
		        && task.getCostVersion().equals(checkTask.getCostVersion())) {
		    isOpen = false;
		    break;
		}
	    }
	}

	if (isOpen) {
	    isOpen = CostCodeHelper.service.isOpenBomEditor(taskOid);
	    msg = "상위 원가 Task 중 완료되지 않은 Task가 있거나\r\n[개발검토BOM] Task가 진행되지 않았습니다.";
	} else {
	    msg = "원가요청서가 완료되지 않았습니다.";
	}

	if (!isOpen) {
	    datas.put("msg", msg);
	} else {
	    datas.put("msg", "");

	    E3PSProject pjt = (E3PSProject) task.getProject();
	    if (pjt instanceof ProductProject) {
		if (pjt.getDevRequest() == null || (pjt.getDevRequest() != null && pjt.getDevRequest().getProjectOID() == null)
		        || StringUtils.isEmpty(pjt.getReviewPjtNo())) {
		    datas.put("msg", "연결된 검토프로젝트가 없습니다.");
		} else {
		    // E3PSProject ReviewProject = (E3PSProject) CommonUtil.getObject(pjt.getDevRequest().getProjectOID());

		    String pjtNos[] = pjt.getReviewPjtNo().split(",");

		    boolean isCompleted = true;
		    boolean isReport = true;
		    for (String pjtNo : pjtNos) {
			E3PSProject ReviewProject = ProjectHelper.getProject(pjtNo);
			if (!ReviewProject.getState().toString().equals("COMPLETED")) {
			    isCompleted = false;
			    break;
			}

			String reportCheckMsg = this.checkProjectReport(CommonUtil.getOIDString(ReviewProject)).get("msg");

			if (StringUtils.isNotEmpty(reportCheckMsg)) {
			    isReport = false;
			    break;
			}

		    }

		    // ReviewProject = ProjectHelper.manager.getProject(ReviewProject.getPjtNo());

		    if (!isCompleted) {
			datas.put("msg", "검토프로젝트가 완료되지 않았습니다.");
		    } else {
			if (!isReport) {
			    datas.put("msg", "연결된 검토프로젝트의 보고서가 없거나 보고서가 승인되지 않았습니다.");
			}
		    }

		}
	    }
	}

	if ("COST013".equals(taskCategory) && !task.isCostRequest() && task.getTaskState() != 5) {

	    boolean isCostRequestEvent = false;

	    QueryResult qr = ProjectTaskHelper.manager.getChildList(task.getProject());

	    while (qr.hasMoreElements()) {
		E3PSTask checkTask = (E3PSTask) qr.nextElement();

		if ("COST013".equals(StringUtil.checkNull(checkTask.getTaskTypeCategory())) && checkTask.getTaskState() == 5) {
		    isCostRequestEvent = true;
		    break;
		}
	    }

	    if (isCostRequestEvent) {
		PartList partList = CostUtil.manager.getPartList((E3PSProjectMaster) task.getProject().getMaster());
		if (partList != null) {

		    int lastestVersion = partList.getLastestVersion();
		    int reportVersion = CostUtil.manager.getLastestReportVersion(task.getProject().getPjtNo(), true);

		    if (lastestVersion != reportVersion) {
			datas.put("msg", "이전에 발생한 원가요청서의 보고서가 없거나 승인상태가 아닙니다.\r\n먼저 이전에 발생한 원가요청서에 대한 보고서 작성 및 승인절차를 진행하십시오.");
		    }
		}
	    }

	}

	return datas;
    }

    @RequestMapping("/checkProjectReport")
    @ResponseBody
    public Map<String, String> checkProjectReport(String projectOid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();
	String message = "";
	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	PartList partList = CostUtil.manager.getPartList(pjtMaster);

	if (partList != null) {
	    int lastestVersion = 0;

	    if (partList != null) {

		lastestVersion = partList.getLastestVersion();
	    }

	    String masterOid = CommonUtil.getOIDString(pjtMaster);

	    CostReport report = CostUtil.manager.getCostReport(masterOid, String.valueOf(lastestVersion));

	    if (report == null) {

		message = "원가보고서가 작성되지 않았습니다.";
	    } else {
		String state = report.getState().toString();

		if (!state.equals("APPROVED")) {
		    message = "원가보고서의 결재가 완료되지 않았습니다.";

		}
	    }
	} else {

	    if (project instanceof ReviewProject) {
		ReviewProject rProject = (ReviewProject) project;
		String division = rProject.getAttr1();

		if (!"전자사업부".equals(division)) {
		    message = "원가산출을 위한 PartList가 존재하지 않습니다.";
		}
	    }

	}

	datas.put("msg", message);

	return datas;
    }

    @RequestMapping("/checkDevRequest")
    @ResponseBody
    public Map<String, String> checkDevRequest(String drOid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();
	String message = "";

	KETDevelopmentRequest d_request = (KETDevelopmentRequest) CommonUtil.getObject(drOid);
	String prodProjNo = "";

	QueryResult qrPrj = ProjectHelper.getDevRequestProject(d_request);
	if (qrPrj != null && qrPrj.size() > 0) {
	    while (qrPrj.hasMoreElements()) {
		Object[] objArr2 = (Object[]) qrPrj.nextElement();
		E3PSProject proj = (E3PSProject) objArr2[0];

		if ("D".equals(d_request.getDevelopmentStep())) {
		    E3PSProject project = (E3PSProject) ProjectHelper.getProject(proj.getPjtNo());

		    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

		    PartList partList = CostUtil.manager.getPartList(pjtMaster);

		    if (partList != null) {
			prodProjNo += pjtMaster.getPjtNo() + ",";
		    }

		}

	    }
	}
	String pjt[] = prodProjNo.split(",");

	pjt = org.springframework.util.StringUtils.removeDuplicateStrings(pjt);

	prodProjNo = StringUtils.join(pjt, ",");

	if (StringUtils.isNotEmpty(prodProjNo)) {
	    message = prodProjNo + " 의 원가요청이 이미 진행 중입니다.\r\n검토프로젝트번호를 변경하거나 의뢰서를 삭제할수없습니다.";
	}

	datas.put("msg", message);

	return datas;
    }

    @RequestMapping("/costCaseListPopup")
    public Model costCaseListPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	LOGGER.info("######## CALL costCaseListPopup ########");

	String taskOid = (String) reqMap.get("taskOid");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	CostUtil.manager.taskVersionSync(task);

	E3PSProject project = (E3PSProject) task.getProject();

	E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	PartList partList = CostUtil.manager.getPartList(pjtMaster);

	reqMap.put("partListOid", CommonUtil.getOIDString(partList));
	reqMap.put("version", task.getCostVersion());
	reqMap.put("DATATYPE", "COSTPART");
	reqMap.put("orderByPartNo", "OK");

	List<Persistable> rootList = CostHelper.service.getCostRootList(reqMap);

	List<Map<String, Object>> caseList = new ArrayList<Map<String, Object>>();

	for (Persistable obj : rootList) {
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(obj);
	    dataMap.put("id", CommonUtil.getOIDString(obj));
	    caseList.add(dataMap);
	}

	String projectOid = CommonUtil.getOIDString(pjtMaster);
	String version = task.getCostVersion();
	CostReport report = CostUtil.manager.getCostReport(projectOid, version);

	String ProductCanAdd = "";

	if (report == null || (report != null && "INWORK".equals(report.getState().toString()))) {
	    ProductCanAdd = "OK";
	}
	model.addAttribute("ProductCanAdd", ProductCanAdd);
	model.addAttribute("caseList", caseList);
	model.addAttribute("taskOid", taskOid);

	return model;
    }

    @SuppressWarnings("static-access")
    @RequestMapping("/costReqHistoryPopup")
    public Model costReqHistoryPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	LOGGER.info("######## CALL costReqHistoryPopup ########");

	String oid = (String) reqMap.get("pjtOid");

	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);

	E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	List<Map<String, Object>> caseList = new ArrayList<Map<String, Object>>();

	String partListOid = "";

	if (project instanceof ProductProject && project.getDevRequest() != null
	        && StringUtil.checkString(project.getDevRequest().getProjectOID())) {
	    // ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());

	    String ProductPjtListOid = CommonUtil.getOIDString(CostUtil.manager.getPartList(pjtMaster));

	    String RpjtNos[] = project.getReviewPjtNo().split(",");
	    for (String rPjtNo : RpjtNos) {
		E3PSProject rp = ProjectHelper.getProject(rPjtNo);
		PartList partList = CostUtil.manager.getPartList((E3PSProjectMaster) rp.getMaster());
		partListOid += CommonUtil.getOIDString(partList) + ",";
	    }

	    if (StringUtils.isNotEmpty(ProductPjtListOid)) {
		partListOid = partListOid + ProductPjtListOid;
	    } else {
		partListOid = StringUtils.removeEnd(partListOid, ",");
	    }

	} else {
	    partListOid = CommonUtil.getOIDString(CostUtil.manager.getPartList(pjtMaster));
	}
	reqMap.put("partListOid", partListOid);

	CostUtil.manager.costReqListDataBuilder(reqMap, caseList);

	model.addAttribute("caseList", caseList);

	return model;
    }

    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/costBomEditor")
    public Model costBomEditor(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	LOGGER.info("######## CALL COSTBOMEDITOR ########");

	String taskOid = (String) reqMap.get("taskOid");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();
	String oid = (String) reqMap.get("oid");
	String isPopup = (String) reqMap.get("isPopup");
	String EDITMODE = (String) reqMap.get("EDITMODE");
	String taskTypeCategory = task.getTaskTypeCategory();
	String EDITTYPE = (String) reqMap.get("EDITTYPE");

	String subVersion = StringUtil.checkNullZero((String) reqMap.get("subVersion"));

	model.addAttribute("EDITTYPE", EDITTYPE);
	model.addAttribute("oid", oid);
	model.addAttribute("isPopup", isPopup);
	model.addAttribute("taskOid", taskOid);
	model.addAttribute("isAdmin", CommonUtil.isAdmin());
	model.addAttribute(
	        "costCalcResult",
	        "COST013".equals(taskTypeCategory) || "COST014".equals(taskTypeCategory) || "COST015".equals(taskTypeCategory)
	                || "COST016".equals(taskTypeCategory));

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	boolean isTaskMaster = false;
	QueryResult masterList = TaskUserHelper.manager.getMaster(task);
	if (masterList != null) {
	    while (masterList.hasMoreElements()) {
		Object o[] = (Object[]) masterList.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		PeopleData data = new PeopleData(link.getMember().getMember());

		if (user.equals(data.wtuser)) {
		    isTaskMaster = true;
		    break;
		}
	    }
	}

	model.addAttribute("isRealPart",
	        (isTaskMaster || CommonUtil.isAdmin()) && project instanceof ProductProject && "COST013".equals(task.getTaskTypeCategory()));

	if ("COST001".equals(task.getTaskTypeCategory())) {
	    if (!task.isCostRequest()) {

		task.setCostRequest(true);
		PersistenceHelper.manager.save(task);

		if (task.getParent() != null) {

		    E3PSTask parentTask = (E3PSTask) task.getParent();

		    if (!parentTask.isCostRequest()) {
			parentTask.setCostRequest(true);
			PersistenceHelper.manager.save(parentTask);
		    }
		}
	    }

	}

	if ("COST013".equals(task.getTaskTypeCategory())) { // 원가 산출 요청

	    if (!(project instanceof ReviewProject)) {
		if (StringUtils.isEmpty(project.getReviewPjtNo())) {
		    model.addAttribute("ERROR", "검토 프로젝트가 없습니다.");
		    return model;
		} else {
		    String pjtNos[] = project.getReviewPjtNo().split(",");
		    boolean isCompleted = true;
		    for (String pjtNo : pjtNos) {
			E3PSProject reviewPjt = ProjectHelper.getProject(pjtNo);
			String state = reviewPjt.getState().toString();
			if (!state.equals("COMPLETED")) {
			    isCompleted = false;
			    break;
			}
		    }

		    if (!isCompleted) {
			model.addAttribute("ERROR", "검토 프로젝트가 완료되지 않았습니다.");
			return model;
		    }

		}

		// String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

		// if(!("DR0".equals(drNo) || "DR1".equals(drNo)) && !CostUtil.manager.checkRealPartNo(reqMap) ) {
		// model.addAttribute("ERROR", "실제 품번 연동 작업이 필요합니다.");
		// return model;
		// }
	    }

	    boolean isFirstReq = task.isCostRequest();

	    if (!isFirstReq && "0".equals(task.getCostVersion()) && project instanceof ReviewProject) {

		if (CostCodeHelper.service.isFirstCostRequset(CommonUtil.getOIDString(project), taskOid)) {
		    isFirstReq = true;
		    task.setCostRequest(true);
		    task = (E3PSTask) PersistenceHelper.manager.save(task);
		}
	    }
	    // if ("0".equals(task.getCostVersion())) {
	    // isFirstReq = CostCodeHelper.service.isFirstCostRequset(CommonUtil.getOIDString(project), taskOid);
	    //
	    // if(!isFirstReq || !(project instanceof ReviewProject)){
	    // task = CostHelper.service.copyNewVersionPart(task);
	    // }
	    // }

	    if (!isFirstReq) {
		task = CostHelper.service.copyNewVersionPart(task);
		isFirstReq = task.isCostRequest();
	    }

	    // if(project instanceof ProductProject && isFirstReq){ //모든 요청서에서 구조 편집이 가능하도록변경 2019.01.31 이경무 과장 요청

	    if (task.getTaskState() != 5) {
		EDITMODE = "TREEEDIT";
		reqMap.put("EDITMODE", EDITMODE);
	    }
	    // }

	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	    PartList partList = CostUtil.manager.getPartList(pjtMaster);

	    reqMap.put("partListOid", CommonUtil.getOIDString(partList));

	    reqMap.put("DATATYPE", "COSTPART");

	    if (task.getTaskState() != 5) {
		List<Persistable> list = CostHelper.service.getCostRootList(reqMap);

		for (Persistable obj : list) {
		    reqMap.put("oid", CommonUtil.getOIDString(obj));
		    reqMap.put("version", String.valueOf(((CostPart) obj).getVersion()));
		    reqMap.put("subVersion", String.valueOf(((CostPart) obj).getSubVersion()));
		    CostCodeHelper.service.createCurrencyInfo((CostPart) obj);
		    // CostCodeHelper.service.initCost(reqMap);
		}

		for (Persistable obj : list) {
		    reqMap.put("oid", CommonUtil.getOIDString(obj));
		    reqMap.put("version", String.valueOf(((CostPart) obj).getVersion()));
		    reqMap.put("subVersion", String.valueOf(((CostPart) obj).getSubVersion()));
		    // CostCodeHelper.service.createCurrencyInfo((CostPart) obj);
		    CostCodeHelper.service.initCost(reqMap);
		}
	    }

	} else {
	    // 개발검토BOM task 가 아니고 미완료 task 인 경우 최신 taskversion 으로 업데이트 한다
	    CostUtil.manager.taskVersionSync(task);

	    if ("COST015".equals(task.getTaskTypeCategory()) && task.getTaskState() != 5) {// 미완료 원가산출 Task인 경우 initFlag를 Y로 업데이트
		E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

		PartList partList = CostUtil.manager.getPartList(pjtMaster);

		reqMap.put("partListOid", CommonUtil.getOIDString(partList));

		reqMap.put("DATATYPE", "COSTPART");
		List<Persistable> list = CostHelper.service.getCostRootList(reqMap);
		for (Persistable obj : list) {
		    CostPart part = (CostPart) obj;
		    if (!"Y".equals(part.getInitFlag())) {
			part.setInitFlag("Y");
			PersistenceHelper.manager.save(part);
		    }
		}
	    }
	}

	boolean FILTERMODE = Boolean.parseBoolean(String.valueOf(reqMap.get("FILTERMODE")));

	// 개발검토BOM, 예상판매량, 포장사양, 기타투자비, 원가산출요청, 원가산출, 판매목표가 등록, 보고서 등록일 경우 FILTER 해제
	if ("TREEEDIT".equals(EDITMODE) || "COST001".equals(taskTypeCategory) || "COST002".equals(taskTypeCategory)
	        || "COST010".equals(taskTypeCategory) || "COST011".equals(taskTypeCategory) || "COST013".equals(taskTypeCategory)
	        || "COST014".equals(taskTypeCategory) || "COST015".equals(taskTypeCategory) || "COST016".equals(taskTypeCategory))
	    FILTERMODE = false;

	model.addAttribute("FILTERMODE", FILTERMODE);
	model.addAttribute("EDITMODE", EDITMODE);

	Map<String, Object> bomACL = BomEditorACLUtil.manager.getAclDataMap(task.getTaskTypeCategory());
	model.addAttribute("bomACL", new JSONObject(bomACL));
	Map<String, Map<String, BomEditorACLFromType>> typeACL = BomEditorACLUtil.manager.getAclMapForTypeAll(true);
	model.addAttribute("typeACL", new JSONObject(typeACL));

	model.addAttribute("task", task);
	model.addAttribute("version", task.getCostVersion());
	model.addAttribute("projectOid", CommonUtil.getOIDString(task.getProject()));

	// if (oid == null) {
	// model.addAttribute("subVersion", subVersion);
	// }
	CostPart part = (CostPart) CommonUtil.getObject(oid);
	if (part != null) {
	    subVersion = String.valueOf(part.getSubVersion());
	} else {

	    String projectOid = CommonUtil.getOIDString(task.getProject());
	    String version = task.getCostVersion();

	    CostReport report = CostUtil.manager.getCostReport(projectOid, version);

	    if (report == null) {

	    } else {
		String state = report.getState().toString();
		if (state.equals("APPROVED")) {
		    subVersion = "";
		    model.addAttribute("lastest", "true");
		}
	    }

	}
	model.addAttribute("subVersion", subVersion);

	editorMapSet(model);

	String auth = CostUtil.manager.getCostAuthInfo(reqMap);
	if ("ERROR".equals(auth)) {
	    model.addAttribute("ERROR", "권한이 없습니다.");
	} else {
	    model.addAttribute("EDITMODE", auth);
	}

	return model;
    }

    public void editorMapSet(Model model) throws Exception {
	Map<String, String> codeMap = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT");

	Set<String> st = codeMap.keySet();
	Iterator<String> it = st.iterator();

	String enumName = "|";
	String enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	LOGGER.info(enumName);
	model.addAttribute("monetaryNames", enumName);
	model.addAttribute("monetaryKeys", enumKeys);

	codeMap = NumberCodeHelper.manager.getNumberCode("MOLDDIVISION");
	st = codeMap.keySet();
	it = st.iterator();

	enumName = "|";
	enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("mDivisionNames", enumName);
	model.addAttribute("mDivisionKeys", enumKeys);

	codeMap = NumberCodeHelper.manager.getNumberCode("FACDIVISION");
	st = codeMap.keySet();
	it = st.iterator();

	enumName = "|";
	enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("fDivisionNames", enumName);
	model.addAttribute("fDivisionKeys", enumKeys);

	codeMap = NumberCodeHelper.manager.getNumberCode("COSTMAKING");
	st = codeMap.keySet();
	it = st.iterator();

	enumName = "|";
	enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("makingNames", enumName);
	model.addAttribute("makingKeys", enumKeys);

	codeMap = NumberCodeHelper.manager.getNumberCode("RAWMATERIAL");
	st = codeMap.keySet();
	it = st.iterator();

	enumName = "|";
	enumKeys = "|";

	while (it.hasNext()) {
	    String code = it.next();
	    enumName += "|" + codeMap.get(code);
	    enumKeys += "|" + code;
	}

	model.addAttribute("rMaterialNames", enumName);
	model.addAttribute("rMaterialKeys", enumKeys);

	// 선도금사양
	List<Map<String, String>> codeMapList = NumberCodeHelper.manager.getNumberCodeChildMapList("PLATING", "PTG");

	enumName = "|";
	enumKeys = "|";

	for (Map<String, String> code : codeMapList) {
	    enumName += "|" + code.get("name");
	    enumKeys += "|" + code.get("code");
	}

	model.addAttribute("prePlatingNames", enumName);
	model.addAttribute("prePlatingKeys", enumKeys);

	// 후도금사양
	codeMapList = NumberCodeHelper.manager.getNumberCodeChildMapList("PLATING", "AP");

	enumName = "|";
	enumKeys = "|";

	for (Map<String, String> code : codeMapList) {
	    enumName += "|" + code.get("name");
	    enumKeys += "|" + code.get("code");
	}

	model.addAttribute("apPlatingNames", enumName);
	model.addAttribute("apPlatingKeys", enumKeys);

	List<NumberCode> codeList = NumberCodeHelper.manager.getTopNumberCode(NumberCodeType.toNumberCodeType("MFTFACTORY"));

	enumName = "|";
	enumKeys = "|";

	for (NumberCode code : codeList) {
	    enumName += "|" + code.getName();
	    enumKeys += "|" + code.getCode();
	}

	model.addAttribute("costDeliverNames", enumName);
	model.addAttribute("costDeliverKeys", enumKeys);
    }

    /**
     * 
     * 
     * @param model
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : costRequestList
     * @작성자 : admin
     * @작성일 : 2020. 8. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    @RequestMapping("/costRequestList")
    public String costRequestList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	LOGGER.info("######## CALL costRequestList ########");

	String taskOid = (String) reqMap.get("taskOid");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();
	String oid = (String) reqMap.get("oid");
	String isPopup = (String) reqMap.get("isPopup");
	final String taskTypeCategory = "COST013";

	model.addAttribute("oid", oid);
	model.addAttribute("isPopup", isPopup);
	model.addAttribute("isAdmin", CommonUtil.isAdmin());
	model.addAttribute("costCalcResult", false);

	model.addAttribute("FILTERMODE", false);

	Map<String, Object> bomACL = BomEditorACLUtil.manager.getAclDataMap(taskTypeCategory);

	ArrayList<Department> OfficerList = new ArrayList<Department>();

	final String purchaseCode = "10076";

	Department checkDept = DepartmentHelper.manager.getDepartment(purchaseCode);// 구매본부
	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	PeopleData pd = new PeopleData(wtuser);
	Department userDept = pd.department;
	ArrayList childDept = new ArrayList();
	childDept = DepartmentHelper.manager.getAllChildList(checkDept, childDept);
	boolean isPurchaseDept = purchaseCode.equals(userDept.getCode());

	if (!isPurchaseDept) {
	    for (Object obj : childDept) {// 접속자의 부서가 구매본부 하위부서에 해당하는지
		Department dp = (Department) obj;
		if (dp.getCode().equals(userDept.getCode())) {
		    isPurchaseDept = true;
		    break;
		}
	    }
	}

	// if (!isPurchaseDept) { //costBomEditor.jsp 에서 isPurchaseDept 로 컨트롤 하는 것으로 변경했기 때문에 주석처리함.. 아래처럼 컬럼숨기기를 하면 excel 내려받기할때 숨김항목을
	// 활성화할수있는 약점이 있기 때문
	// Set<String> st = bomACL.keySet();
	// Iterator<String> it = st.iterator();
	//
	// while (it.hasNext()) {
	//
	// String key = it.next();
	//
	// boolean goFlag = true;
	// switch (key) {
	//
	// case "tos": // 구매/도금/외주 - 선적조건
	// goFlag = false;
	// break;
	//
	// case "duty": // 구매/도금/외주 - 관세(%)
	// goFlag = false;
	// break;
	//
	// case "distributionCost": // 구매/도금/외주 - 물류비(%)
	// goFlag = false;
	// break;
	//
	// case "pUnitCost": // 구매/도금/외주 - 구매단가
	// goFlag = false;
	// break;
	//
	// case "pMonetaryUnit": // 구매/도금/외주 - 화폐단위
	// goFlag = false;
	// break;
	//
	// case "prePlatingCost": // 구매/도금/외주 - 선도금단가
	// goFlag = false;
	// break;
	//
	// case "prePlatingUnit": // 구매/도금/외주 - 화폐단위
	// goFlag = false;
	// break;
	//
	// case "apUnitCost": // 구매/도금/외주 - 후도금단가
	// goFlag = false;
	// break;
	//
	// case "apMonetaryUnit": // 구매/도금/외주 - 화폐단위
	// goFlag = false;
	// break;
	//
	// case "outUnitCost": // 구매/도금/외주 - 외주단가
	// goFlag = false;
	// break;
	//
	// case "outMonetaryUnit": // 구매/도금/외주 - 화폐단위
	// goFlag = false;
	// break;
	//
	// default:
	// break;
	// }
	// if (!goFlag) {
	// ((Map<String, Object>) bomACL.get(key)).put("editor", "false");
	// ((Map<String, Object>) bomACL.get(key)).put("viewable", "false");
	// }
	//
	// }
	// }

	model.addAttribute("isPurchaseDept", isPurchaseDept);
	model.addAttribute("bomACL", new JSONObject(bomACL));
	Map<String, Map<String, BomEditorACLFromType>> typeACL = BomEditorACLUtil.manager.getAclMapForTypeAll(true);
	model.addAttribute("typeACL", new JSONObject(typeACL));

	// model.addAttribute("task", task);

	model.addAttribute("projectOid", CommonUtil.getOIDString(project));

	CostPart part = (CostPart) CommonUtil.getObject(oid);

	model.addAttribute("version", part.getVersion());
	model.addAttribute("subVersion", part.getSubVersion());
	model.addAttribute("EDITMODE", "VIEW_COMMON");
	model.addAttribute("taskOid", taskOid);

	editorMapSet(model);

	return "/cost/costBomEditor";
    }
}