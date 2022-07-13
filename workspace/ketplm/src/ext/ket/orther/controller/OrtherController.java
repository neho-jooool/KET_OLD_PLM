package ext.ket.orther.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.project.ProductProject;
import e3ps.project.ProjectIssue;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.outputtype.ModelPlan;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.orther.entity.dto.OrtherDto;
import ext.ket.orther.service.OrtherHelper;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : OrtherController
 * @작성자 : 황정태
 * @작성일 : 2016. 03. 04.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/orther/*")
public class OrtherController {

    @RequestMapping("/ortherRedirect")
    public String ortherRedirect(String viewtype, String parameter, Model model) throws Exception {

	if (viewtype.equals("DQM")) {
	    KETDqmDTO paramObject = new KETDqmDTO();
	    paramObject.setProblemNo(parameter);

	    QueryResult qr = null;

	    if (StringUtils.isNotEmpty(parameter)) {
		qr = DqmHelper.service.getSearchList(paramObject);
	    }

	    String dqmOid = "";
	    String dqmStateCode = "";

	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		KETDqmHeader ketDqmHeader = (KETDqmHeader) objArr[0];
		dqmOid = CommonUtil.getOIDString(ketDqmHeader);
		dqmStateCode = ketDqmHeader.getDqmStateCode();
	    }

	    model.addAttribute("dqmOid", dqmOid);
	    model.addAttribute("dqmStateCode", dqmStateCode);
	}

	if (viewtype.equals("ECO")) {
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
	    String ecoId = parameter.replace("ECO-", "");
	    WTChangeOrder2 wtChangeOrder2 = prodEcoBeans.getEcoByNo(ecoId);
	    String EcoOid = null;
	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {

		KETProdChangeOrder prodEco = (KETProdChangeOrder) wtChangeOrder2;
		EcoOid = CommonUtil.getOIDString(prodEco);
	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {

		KETMoldChangeOrder moldEco = (KETMoldChangeOrder) wtChangeOrder2;
		EcoOid = CommonUtil.getOIDString(moldEco);
	    }

	    model.addAttribute("EcoOid", EcoOid);
	}

	if (viewtype.equals("ECR")) {
	    WTChangeRequest2 ecr = null;
	    String ecrId = parameter.replace("ECR-", "");
	    String EcrOid = null;
	    try {

		QuerySpec spec = new QuerySpec(KETProdChangeRequest.class);
		SearchUtil.appendLIKE(spec, KETProdChangeRequest.class, KETProdChangeRequest.ECR_ID, ecrId, 0);
		QueryResult result = PersistenceHelper.manager.find(spec);
		if (result != null && result.hasMoreElements()) {
		    ecr = (WTChangeRequest2) result.nextElement();
		    KETProdChangeRequest prodEcr = (KETProdChangeRequest) ecr;
		    EcrOid = CommonUtil.getOIDString(prodEcr);
		}

	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }

	    model.addAttribute("EcrOid", EcrOid);

	}

	if (viewtype.equals("PROJECT") || viewtype.equals("PROJECT_ATFT") || viewtype.equals("ISSUE")) {
	    model.addAttribute("pjtNo", parameter);
	}

	if (viewtype.equals("PART")) {
	    model.addAttribute("partNo", parameter);
	}

	if (viewtype.equals("DIVIDEND")) {
	    model.addAttribute("emp_no", parameter);
	}

	if (viewtype.equals("ECO_LIST")) {
	    model.addAttribute("divisionFlag", parameter);
	}

	if (viewtype.equals("MODEL_PLAN")) {

	    String carTypeOid = "";

	    ProductProject pjt = (ProductProject) ProjectHelper.manager.getProject(parameter);
	    KETSalesProject salesPjt = null;

	    if (pjt == null) {
		salesPjt = this.getSalesProject(parameter);

		if (salesPjt != null) {
		    carTypeOid = CommonUtil.getOIDString(salesPjt.getOemType());
		    model.addAttribute("tabIndex", "1");
		}

	    } else {
		carTypeOid = CommonUtil.getOIDString(pjt.getOemType());
		model.addAttribute("tabIndex", "2");
	    }

	    ModelPlan modelPlan = null;

	    if (StringUtils.isNotEmpty(carTypeOid)) {
		modelPlan = ProgramHelper.service.getModelPlanByCarType(carTypeOid);
		if (modelPlan != null) {
		    model.addAttribute("modelPlanOid", CommonUtil.getFullOIDString(modelPlan));
		} else {
		    model.addAttribute("modelPlanOid", "");
		}

	    } else {
		model.addAttribute("modelPlanOid", "");
	    }

	}

	if (viewtype.equals("SALES_LIST")) {

	    if (StringUtils.isEmpty(parameter)) {

		model.addAttribute("paramCheck", "NG");

	    } else {

		String parameters[] = parameter.split("\\^");

		if (parameters.length == 6) {

		    String createDateFrom = parameters[0]; // 시작년월
		    String salesStateName = parameters[1]; // 프로젝트 상태
		    String mainCategoryName = parameters[2]; // 제품군 대분류
		    String nationName = parameters[3]; // 지역(국가)
		    String failtypecode = parameters[4]; // 실패유형
		    String obtainCompany = parameters[5]; // 수주사

		    if (StringUtils.isNotEmpty(createDateFrom) && !"null".equals(createDateFrom)) {
			model.addAttribute("createDateFrom", createDateFrom);// 시작년월 2017-02
		    }

		    NumberCode num = null;

		    if (StringUtils.isNotEmpty(salesStateName) && !"null".equals(salesStateName)) {
			num = NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", salesStateName);

			if (num != null) {
			    model.addAttribute("salesStateName", salesStateName);
			    model.addAttribute("salesStateOid", CommonUtil.getOIDLongValue2Str(num));// 프로젝트 상태
			}

		    }

		    if (StringUtils.isNotEmpty(mainCategoryName) && !"null".equals(mainCategoryName)) {
			num = NumberCodeHelper.manager.getNumberCode("SALESMAINCATEGORY", mainCategoryName);

			if (num != null) {
			    model.addAttribute("mainCategoryName", CommonUtil.getOIDLongValue2Str(num));// 제품군 대분류
			}

		    }

		    if (StringUtils.isNotEmpty(nationName) && !"null".equals(nationName)) {
			num = NumberCodeHelper.manager.getNumberCode("SALESNATION", nationName);

			if (num != null) {
			    model.addAttribute("nationName", CommonUtil.getOIDLongValue2Str(num));// 국가
			}

		    }

		    if (StringUtils.isNotEmpty(failtypecode) && !"null".equals(failtypecode)) {
			num = NumberCodeHelper.manager.getNumberCode("SALESFAILTYPE", failtypecode);

			if (num != null) {
			    model.addAttribute("failtypecode", CommonUtil.getOIDLongValue2Str(num));// 실패유형
			}

		    }

		    if (StringUtils.isNotEmpty(obtainCompany) && !"null".equals(obtainCompany)) {
			num = NumberCodeHelper.manager.getNumberCode("SALESCOMPETITOR", obtainCompany);

			if (num != null) {
			    model.addAttribute("obtainCompany", CommonUtil.getOIDLongValue2Str(num));// 수주사
			}

		    }
		    model.addAttribute("paramCheck", "OK");
		} else {
		    model.addAttribute("paramCheck", "NG");
		}

	    }

	}

	if (viewtype.equals("AEGIS_ISSUE")) {
	    String oid = "e3ps.project.ProjectIssue:" + parameter;
	    ProjectIssue issue = (ProjectIssue) CommonUtil.getObject(oid);

	    if (issue == null) {
		oid = "";
	    }

	    model.addAttribute("oid", oid);
	}

	if (viewtype.equals("SALES_PROJECT")) {
	    String oid = "";
	    KETSalesProject pjt = this.getSalesProject(parameter);

	    if (pjt != null) {
		oid = CommonUtil.getOIDString(pjt);
		model.addAttribute("oid", oid);
	    }
	}

	if (viewtype.equals("AEGIS_INVEST")) {
	    // KETInvestMaster invest = InvestUtil.manager.getInvestMasterByReqNo(parameter);

	    String oid = "ext.ket.invest.entity.KETInvestMaster:" + parameter;
	    KETInvestMaster invest = (KETInvestMaster) CommonUtil.getObject(oid);

	    if (invest == null) {
		oid = "";
	    } else {
		oid = "ext.ket.invest.entity.KETInvestMaster:" + parameter;
	    }

	    model.addAttribute("oid", oid);
	}

	model.addAttribute("viewtype", viewtype);
	model.addAttribute("parameter", parameter);

	return "noExtends:/main/ortherRedirect";
    }

    @RequestMapping("/dividend/viewDividendForm")
    public void viewDividendForm(String emp_no, Model model) {

    }

    @RequestMapping("/file/decryptOldDrmFile")
    public void decryptOldDrmFile(Model model) {
    }

    @ResponseBody
    @RequestMapping("/file/fileUpload")
    public Map<String, Object> fileUpload(MultipartHttpServletRequest mtfRequest) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    List<MultipartFile> fileList = mtfRequest.getFiles("files");

	    String downloadUrl = OrtherHelper.service.decryptMarkAnyDrmFile(fileList);

	    resMap.put("downloadLink", downloadUrl);
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @RequestMapping("/dividend/dividendSave")
    @ResponseBody
    public String dividendSave(@ModelAttribute OrtherDto dto, Model model) throws Exception {
	try {

	    OrtherHelper.service.dividendSave(dto);
	    return "S";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    public KETSalesProject getSalesProject(String pjtNo) throws Exception {

	QuerySpec spec = new QuerySpec(KETSalesProject.class);

	spec.appendWhere(
	        new SearchCondition(KETSalesProject.class, KETSalesProject.PROJECT_NO, SearchCondition.EQUAL, pjtNo.toUpperCase()),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(
	        new SearchCondition(KETSalesProject.class, "iterationInfo.latest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	KETSalesProject project = null;
	if (rs.hasMoreElements()) {
	    project = (KETSalesProject) rs.nextElement();
	}

	return project;
    }

    @RequestMapping("/updateConvertFiles2PLM")
    @ResponseBody
    public String updateConvertFiles2PLM(HttpServletRequest request) throws Exception {

	try {
	    String str;
	    StringBuffer paramData = new StringBuffer();
	    BufferedReader br = null;

	    br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	    while ((str = br.readLine()) != null) {
		paramData.append(str);
	    }

	    JSONObject obj = (JSONObject) JSONValue.parse(paramData.toString());

	    OrtherHelper.service.updateConvertFiles2PLM(obj);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}
	return "Success";
    }

}
