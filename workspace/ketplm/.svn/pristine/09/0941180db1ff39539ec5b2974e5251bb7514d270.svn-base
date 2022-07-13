package ext.ket.project.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ews.dao.PartnerDao;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectOutputData;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.material.MoldMaterial;
import e3ps.sap.BudgetExpenseInterface;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.bom.query.KETProjectBomQueryBean;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.cost.controller.CostController;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.dto.PartDieProjectHelpDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.project.product.service.KETProjectHelper;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.ReflectUtil;
import ext.ket.sysif.sap.service.SapService;

@Controller
@RequestMapping("/project/product/*")
public class ProductController {

    @Inject
    private SapService service;

    /**
     * 부품에 맵핑된 dieNo 리스트를 반환한다.
     * 
     * @param partNo
     * @return
     * @throws Exception
     * @메소드명 : getDieNoByPartNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/getDieNoByPartNo")
    @ResponseBody
    public List<String> getDieNoByPartNo(String partNo) throws Exception {
	List<String> dieNoList = new ArrayList<String>();
	WTPart wtPart = PartBaseHelper.service.getLatestPart(partNo);
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETHalbPartDieSetPartLink> dieList = query.queryForListLinkByRoleA(WTPartMaster.class, KETHalbPartDieSetPartLink.class,
	        (WTPartMaster) wtPart.getMaster());

	for (KETHalbPartDieSetPartLink link : dieList) {
	    WTPartMaster wtPartMast = link.getDieSet();
	    WTPart diePart = (WTPart) ObjectUtil.getLatestObject(wtPartMast);
	    dieNoList.add(diePart.getNumber());
	}

	return dieNoList;
    }

    /**
     * DieNo의 부품 속성정보를 반환 한다.
     * 
     * @param dieNo
     * @return
     * @throws Exception
     * @메소드명 : getPartAttributeByDieNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/getPartAttributeByDieNo")
    @ResponseBody
    public Map<String, String> getPartAttributeByDieNo(String partNo, String dieNo) throws Exception {
	Map<String, String> partAttrMap = new HashMap<String, String>();
	if (!StringUtil.checkString(dieNo)) {
	    return null;
	}
	PartDieProjectHelpDTO dto = PartBaseHelper.service.getPartDieInfoForSync(partNo, dieNo);
	if (dto != null) {
	    partAttrMap.put("itemType", dto.getMoldPartClassificType());
	    // 구분
	    NumberCode moldType = NumberCodeHelper.manager.getNumberCodeName("MOLDTYPE", dto.getMoldProps());
	    partAttrMap.put("moldType", moldType != null ? moldType.getCode() : "");
	    partAttrMap.put("moldTypeName", StringUtil.checkNull(dto.getMoldProps()));
	    // Cavity
	    partAttrMap.put("cavity", StringUtil.checkNull(dto.getMoldSpCavityStd()));
	    // Cycle Time
	    partAttrMap.put("cycleTime", StringUtil.checkNull(dto.getMoldSpObjectiveCT()));
	    // 제작처 사내 구분
	    partAttrMap.put("making", StringUtil.checkNull(dto.getMoldSpMContractSAt()));
	    // 제작처
	    PartnerDao partnerDao = new PartnerDao();
	    if ("사내".equals(dto.getMoldSpMContractSAt())) { // 사내는 NumberCode
		NumberCode numberCode = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", dto.getMoldSpDieWhere());
		String makingPlaceName = numberCode != null ? numberCode.getName() : "";
		partAttrMap.put("makingPlace", CommonUtil.getOIDString(numberCode));
		partAttrMap.put("makingPlaceName", dto.getMoldSpMContractSAt() + "/" + makingPlaceName);
	    } else if (StringUtil.checkString(dto.getMoldSpDieWhere())) {
		partAttrMap.put("makingPlace", dto.getMoldSpDieWhere());
		partAttrMap.put(
		        "makingPlaceName",
		        StringUtil.checkNull(dto.getMoldSpMContractSAt()) + "/"
		                + StringUtil.checkNull(partnerDao.ViewPartnerName(dto.getMoldSpDieWhere())));
	    }
	    // 생산처 사내 구분
	    partAttrMap.put("productionPlace", StringUtil.checkNull(dto.getPartSpManufAt()));
	    // 생산처
	    if ("사내".equals(dto.getPartSpManufAt())) {
		NumberCode numberCode = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", dto.getPartSpManufacPlace());
		String purchasePlaceName = numberCode != null ? numberCode.getName() : "";
		partAttrMap.put("purchasePlace", StringUtil.checkNull(CommonUtil.getOIDString(numberCode)));
		partAttrMap.put("purchasePlaceName", dto.getPartSpManufAt() + "/" + purchasePlaceName);
	    } else if (StringUtil.checkString(dto.getPartSpManufacPlace())) {
		partAttrMap.put("purchasePlace", dto.getPartSpManufacPlace());
		partAttrMap.put(
		        "purchasePlaceName",
		        StringUtil.checkNull(dto.getPartSpManufAt()) + "/"
		                + StringUtil.checkNull(partnerDao.ViewPartnerName(dto.getPartSpManufacPlace())));
	    }
	    // 원재료(meterial) - oid
	    MoldMaterial material = (MoldMaterial) CommonUtil.getObject(dto.getPartSpMaterialInfo());
	    partAttrMap.put("materials", StringUtil.checkNull(dto.getPartSpMaterialInfo()));
	    // 원재료(명)
	    partAttrMap.put("tempmaterials", material != null ? material.getGrade() : "");
	    // 원재료 특성(부품) 수지-Color, 비철-도금
	    NumberCode property = NumberCodeHelper.manager.getNumberCode("SPECCOLOR", dto.getPartSpColor());
	    if (property == null) {
		property = NumberCodeHelper.manager.getNumberCode("SPECPLATING", dto.getPartSpPlating());
	    }
	    partAttrMap.put("poidvalue", property != null ? CommonUtil.getOIDString(property) : "");
	    // 원재료
	    partAttrMap.put("height", "");
	    // 원재료
	    partAttrMap.put("wide", "");
	    // 원재료
	    partAttrMap.put("etc", "");
	}
	ReflectUtil.toString(partAttrMap);
	return partAttrMap;
    }

    @RequestMapping("/getChildPartByProducts")
    @ResponseBody
    public Map<String, Object> getChildPartByProducts(String[] pNums) throws Exception {
	List<String> productPartNoList = new ArrayList<String>();
	for (int i = 0; i < pNums.length; i++) {
	    productPartNoList.add(pNums[i]);
	}
	KETProjectBomQueryBean queryBean = new KETProjectBomQueryBean();
	List<Map<String, String>> orgPartlist = new ArrayList<Map<String, String>>();
	List<Map<String, String>> newPartlist = new ArrayList<Map<String, String>>();
	List<String> as = new ArrayList<String>(); // 중복키구분용 리스트

	for (int i = 0; i < pNums.length; i++) {
	    orgPartlist.addAll(queryBean.getPartList(pNums[i]));
	}
	for (Map<String, String> data : orgPartlist) {
	    String partNo = (String) data.get("partNo");

	    if (!as.contains(partNo)) {
		as.add(partNo);

		newPartlist.add(data);

	    }

	}
	return EjsConverUtil.convertToDto(newPartlist);
    }

    /**
     * 프로젝트 완료시 투자오더를 체크한다
     * 
     * @param partNo
     * @return
     * @throws Exception
     * @메소드명 : checkCostByDieInfo
     * @작성자 : 황정태
     * @작성일 : 2016. 04
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/checkCostByDieInfo")
    @ResponseBody
    public Map<String, String> checkCostByDieInfo(String oid) throws Exception {
	/*
	 * 체크대상 : 해당 프로젝트 (Pilot, 양산) 의 제품정보에 반제품에 연결된 금형프로젝트 존재시 투자오더 체크대상임
	 */
	List<String> dieNoList = new ArrayList<String>();

	Map<String, String> datas = new HashMap<String, String>();

	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	KETMessageService messageService = KETMessageService.getMessageService();
	String process = project.getProcess() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("PROCESS", project
	        .getProcess().getCode(), messageService.getLocale().toString()));

	if ("Pilot".equals(process) || "양산".equals(process)) {

	} else {
	    datas.put("msg", "");
	    return datas;
	}

	QuerySpec spec = new QuerySpec();
	int idx = spec.addClassList(MoldItemInfo.class, true);
	SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project));
	spec.appendWhere(sc, new int[] { idx });

	spec.appendAnd();
	spec.appendOpenParen();
	sc = new SearchCondition(MoldItemInfo.class, "itemType", SearchCondition.EQUAL, "Press");
	spec.appendWhere(sc, new int[] { idx });
	spec.appendOr();
	sc = new SearchCondition(MoldItemInfo.class, "itemType", SearchCondition.EQUAL, "Mold");
	spec.appendWhere(sc, new int[] { idx });
	spec.appendCloseParen();
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldItemInfo.class, "shrinkage", SearchCondition.EQUAL, "신규"), new int[] { idx });

	e3ps.common.query.SearchUtil.setOrderBy(spec, MoldItemInfo.class, idx, "dieNo", "dieNo", false);

	QueryResult rt = PersistenceHelper.manager.find(spec);

	System.out.println("qry 1 : " + spec.toString());

	String dieNoValue = "";

	boolean check1 = false;
	boolean check2 = false;
	boolean errCheck = false;

	Map<String, Object> paramMap = new HashMap<String, Object>();

	while (rt.hasMoreElements()) {
	    check1 = true;
	    Object[] obj = (Object[]) rt.nextElement();
	    MoldItemInfo miInfo = (MoldItemInfo) obj[0];

	    if (!dieNoValue.equals(miInfo.getDieNo())) {
		QuerySpec specCost = new QuerySpec();
		int idx_Cost = specCost.addClassList(CostInfo.class, true);
		SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(project));
		specCost.appendWhere(scCost, new int[] { idx_Cost });

		specCost.appendAnd();
		scCost = new SearchCondition(CostInfo.class, "costType", SearchCondition.EQUAL, "금형");
		specCost.appendWhere(scCost, new int[] { idx_Cost });

		specCost.appendAnd();
		scCost = new SearchCondition(CostInfo.class, "dieNo", SearchCondition.EQUAL, miInfo.getDieNo());
		specCost.appendWhere(scCost, new int[] { idx_Cost });

		// e3ps.common.query.SearchUtil.setOrderBy(specCost, CostInfo.class, idx_Cost, "dieNo", "dieNo", true);

		QueryResult rtCost = PersistenceHelper.manager.find(specCost);
		System.out.println("qry 2 : " + specCost.toString());
		CostInfo costInfo = null;
		if (rtCost.size() < 1) {
		    errCheck = true;
		}
		if (rtCost.hasMoreElements()) {
		    Object[] costObj = (Object[]) rtCost.nextElement();
		    costInfo = (CostInfo) costObj[0];
		    check2 = true;
		    if (StringUtils.isEmpty(costInfo.getExecutionCost()) || StringUtils.isEmpty(costInfo.getOrderInvest())) {
			errCheck = true;
		    }
		}

		WTPart part = PartBaseHelper.service.getLatestPart(miInfo.getDieNo());
		String SpMProdAt = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMProdAt);
		if ("3".equals(SpMProdAt)) {// 생산구분이 Modify이면
		    errCheck = false;
		}

		if (errCheck) {
		    paramMap.put("dieNo", miInfo.getDieNo());
		    paramMap.put("project", project);

		    if (localMoldCheck(paramMap)) {// 신규 금형 DieNo에 국내 투자오더가 없다면 중국등 투자오더가 있는지 체크
			errCheck = false;
			check2 = true;
		    }
		}
	    }
	    dieNoValue = miInfo.getDieNo();
	}

	if (check1 && !check2) {// 신규 금형프로젝트가 있는데 비용정보가 없는 경우
	    errCheck = true;
	}

	if (StringUtils.isNotEmpty(dieNoValue)) {// 시험금형은 체크안함

	    String dieCheck = String.valueOf(dieNoValue.charAt(1));

	    if ("T".equals(dieCheck)) {
		errCheck = false;
	    }

	}

	if (errCheck) {
	    datas.put("msg", "비용정보가 입력되지 않았습니다.\n비용탭을 확인하여 투자오더등을 입력하십시오.");
	} else {
	    datas.put("msg", "");
	}

	return datas;
    }

    public boolean localMoldCheck(Map<String, Object> paramMap) throws Exception {// 금형(로컬)의 예산정보가 존재하는지 체크
	String dieNo = (String) paramMap.get("dieNo");
	E3PSProject project = (E3PSProject) ((Object) paramMap.get("project"));

	QuerySpec specCost = new QuerySpec();
	int idx_Cost = specCost.addClassList(CostInfo.class, true);
	SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project));
	specCost.appendWhere(scCost, new int[] { idx_Cost });

	specCost.appendAnd();
	scCost = new SearchCondition(CostInfo.class, "costType", SearchCondition.EQUAL, "금형(로컬)");
	specCost.appendWhere(scCost, new int[] { idx_Cost });

	specCost.appendAnd();
	scCost = new SearchCondition(CostInfo.class, "dieNo", SearchCondition.EQUAL, dieNo);
	specCost.appendWhere(scCost, new int[] { idx_Cost });

	QueryResult rtCost = PersistenceHelper.manager.find(specCost);
	String orderNo = "";
	if (rtCost.hasMoreElements()) {
	    Object[] costObj = (Object[]) rtCost.nextElement();
	    CostInfo costInfo = (CostInfo) costObj[0];
	    orderNo = costInfo.getOrderInvest();
	}

	if (StringUtils.isNotEmpty(orderNo)) {
	    return service.ExistBudgetByOrderNo(orderNo);
	}

	return false;

    }

    @RequestMapping("/syncMainSchedule")
    @ResponseBody
    public Map<String, String> syncMainSchedule(String oid, String code) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);

	E3PSProject project = (E3PSProject) task.getProject();

	boolean errCheck = false;

	try {

	    task.setMainScheduleCode(code);
	    PersistenceHelper.manager.save(task);

	    ProjectTaskHelper.manager.mainSchedulUpdate(project, "", true);
	} catch (Exception e) {
	    errCheck = true;
	}

	if (errCheck) {
	    datas.put("msg", "주요 일정 동기화 중 오류가 발생했습니다.\n관리자에게 연락바랍니다.");
	} else {
	    datas.put("msg", "");
	}

	return datas;
    }

    @RequestMapping("/getPjtProcess")
    @ResponseBody
    public Map<String, String> getPjtProcess(String oid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	ProductProject project = (ProductProject) CommonUtil.getObject(oid);

	boolean errCheck = false;
	String process = "";
	try {
	    if (project.getProcess() != null) {
		process = project.getProcess().getCode();
	    }
	} catch (Exception e) {
	    errCheck = true;
	}

	if (errCheck) {
	    datas.put("msg", "알 수 없는 오류가 발생했습니다.\n관리자에게 연락바랍니다.");
	} else {
	    datas.put("msg", process);
	}

	return datas;
    }

    @RequestMapping("/projectComleteCheck")
    @ResponseBody
    public Map<String, String> projectComleteCheck(String oid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	String msg = this.checkCostByDieInfo(oid).get("msg");// 비용체크

	if (StringUtils.isEmpty(msg)) {
	    CostController cost = new CostController();
	    msg = cost.checkProjectReport(oid).get("msg");// 원가요청서 체크
	}

	if (StringUtils.isEmpty(msg)) {
	    msg = this.projectDocCheck(oid).get("msg");// 프로젝트 완료보고 문서 체크
	}

	datas.put("msg", msg);

	return datas;
    }

    public Map<String, String> projectDocCheck(String oid) throws Exception {

	boolean goCheck = false;

	String msg = "";
	Map<String, String> datas = new HashMap<String, String>();

	if (CommonUtil.getObject(oid) instanceof ProductProject) {
	    ProductProject pjt = (ProductProject) CommonUtil.getObject(oid);

	    if (pjt.getPjtType() == 2 && "PC002".equals(pjt.getProcess().getCode())) {// 자동차사업부 , Pilot
		goCheck = true;
	    }
	}

	if (goCheck) {

	    TemplateProject project = (TemplateProject) CommonUtil.getObject(oid);

	    List<E3PSTask> taskList = ProjectTaskHelper.getTaskByProject(project);

	    E3PSTask targetTask = null;
	    KETProjectDocument doc = null;

	    for (E3PSTask checkTask : taskList) {
		String scheduleCode = checkTask.getMainScheduleCode();
		if ("MC044".equals(scheduleCode)) {
		    targetTask = checkTask;
		    break;
		}
	    }

	    if (targetTask == null) {
		msg = "프로젝트 완료 Task가 존재하지 않습니다.";

	    } else if (targetTask.getTaskState() != 5) {

		msg = "프로젝트 완료 Task가 종결되지 않았습니다.";

	    } else {

		QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(targetTask);

		while (outputQr.hasMoreElements()) {

		    Object[] opObj = (Object[]) outputQr.nextElement();
		    ProjectOutput output = (ProjectOutput) opObj[0];
		    ProjectOutputData outputData = new ProjectOutputData(output);

		    if (outputData.LastDocument != null && outputData.LastDocument instanceof KETProjectDocument) {

			KETProjectDocument docu = (KETProjectDocument) outputData.LastDocument;

			QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);

			KETDocumentCategory docCate = null;

			if (r.hasMoreElements()) {
			    docCate = (KETDocumentCategory) r.nextElement();
			}

			if ("PD303148".equals(docCate.getCategoryCode())) {
			    doc = docu;
			    break;
			}

		    }
		}

		if (doc == null) {
		    msg = "프로젝트완료보고(제품) 산출물이 존재하지 않습니다.";
		} else {

		    if (!doc.getState().getState().equals(State.toState("APPROVED"))) {

			msg = "프로젝트완료보고(제품) 문서가 승인되지 않았습니다.";

		    } else if (!WorkflowSearchHelper.manager.userInApproval(doc, "ketcolwj")) {

			msg = "프로젝트완료보고(제품) 문서 승인권자에 이원준(사장)님 이 지정되지 않았습니다.";
		    }
		}
	    }
	}

	datas.put("msg", msg);

	return datas;
    }

    @RequestMapping("/checkPartPlace")
    @ResponseBody
    public Map<String, String> checkPartPlace(String oid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	boolean goCheck = false;

	if (CommonUtil.getObject(oid) instanceof ProductProject) {
	    ProductProject pjt = (ProductProject) CommonUtil.getObject(oid);

	    if (pjt.getPjtType() == 2 && "PC002".equals(pjt.getProcess().getCode()) && isDR3taskCompleteCheck(pjt)) {// 자동차사업부 , Pilot, DR3
		                                                                                                     // 양산계획 TASK 완료
		goCheck = true;
	    }
	}

	String msg = "";

	if (goCheck) {
	    if (!isPartInfoCheck((ProductProject) CommonUtil.getObject(oid))) {
		msg = "생산처가 지정되지 않은 제품이 있습니다.\r\n\r\n제품정보 탭을 클릭하여 생산처를 입력하시기 바랍니다.";
	    }
	}

	datas.put("msg", msg);

	return datas;
    }

    public boolean isDR3taskCompleteCheck(E3PSProject pjt) throws Exception {

	boolean check = false;

	List<E3PSTask> taskList = ProjectTaskHelper.getTaskByProject(pjt);
	for (E3PSTask checkTask : taskList) {

	    if ("3".equals(checkTask.getTaskTypeCategory()) || "MC015".equals(checkTask.getMainScheduleCode())) {
		if (checkTask.getTaskState() == 5) {
		    check = true;
		}
		break;
	    }
	}

	return check;
    }

    public boolean isPartInfoCheck(ProductProject project) throws Exception {

	boolean check = true;

	QuerySpec qs = new QuerySpec();
	int idxpi = qs.appendClassList(ProductInfo.class, true);
	SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
	qs.appendWhere(cs, new int[] { idxpi });
	QueryResult qrpi = PersistenceHelper.manager.find(qs);

	while (qrpi.hasMoreElements()) {
	    Object o[] = (Object[]) qrpi.nextElement();
	    ProductInfo pi = (ProductInfo) o[0];

	    if (pi.getAssemblyPlace() == null && StringUtils.isEmpty(pi.getAssemblyPartnerNo())) {
		check = false;
		break;
	    }
	}

	return check;
    }

    @RequestMapping("/copyProject")
    @ResponseBody
    public Map<String, String> copyProject(String sourceOid, String targetOid) throws Exception {

	Map<String, String> datas = new HashMap<String, String>();

	try {
	    E3PSProject sourcePjt = (E3PSProject) CommonUtil.getObject(sourceOid);
	    E3PSProject targetPjt = (E3PSProject) CommonUtil.getObject(targetOid);

	    WTUser pmUser = ProjectUserHelper.manager.getPM(sourcePjt);

	    sourcePjt = ProjectHelper.wbsCopyProjectTaskByProject(targetPjt, sourcePjt, pmUser);

	    datas.put("msg", "");
	} catch (Exception e) {
	    datas.put("msg", e.getLocalizedMessage());
	}

	return datas;
    }

    @ResponseBody
    @RequestMapping("/getExpenseTotalByProject")
    public Map<String, Object> getExpenseTotalByProject(@RequestBody Map<String, Object> reqMap) {
	Map<String, Object> resMap = null;

	try {
	    resMap = (Map<String, Object>) BudgetExpenseInterface.getTotalExpenseByProject(reqMap);
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return resMap;
    }

    @ResponseBody
    @RequestMapping("/getExpenseListByProject")
    public Map<String, Object> getExpenseListByProject(@RequestParam Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> list = (List<Map<String, Object>>) BudgetExpenseInterface.getBudgetList(reqMap).get("groupingList");
	return EjsConverUtil.convertToDto(list);
    }

    @RequestMapping("/projectExpenseForm")
    public Model projectExpenseForm(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	model.addAttribute("pjtNo", (String) reqMap.get("pjtNo"));
	FileContentUtil.manager.saveDownloadHistory("경비상세조회", (String) reqMap.get("pjtNo"));
	return model;
    }

    @ResponseBody
    @RequestMapping("/getProjectStructureList")
    public Map<String, Object> getProjectStructureList(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    resMap.put("pjtStrList", ProjectHelper.manager.makeProjectStructure(reqMap));
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @RequestMapping("/wareHousingList")
    public Model wareHousingList(@RequestParam Map<String, Object> reqMap, Model model) {

	String pjtNo = (String) reqMap.get("pjtNo");

	try {
	    FileContentUtil.manager.saveDownloadHistory("입고처조회", pjtNo);

	} catch (Exception e) {
	    e.printStackTrace();
	}
	model.addAttribute("pjtNo", pjtNo);
	return model;
    }

    @ResponseBody
    @RequestMapping("/findWareHousingList")
    public Map<String, Object> findWareHousingList(@RequestParam Map<String, Object> reqMap) throws Exception {
	return KETProjectHelper.service.findWareHousingList(reqMap);
    }

}
