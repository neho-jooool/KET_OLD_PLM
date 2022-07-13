package ext.ket.part.base.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import wt.change2.WTChangeOrder2;
import wt.content.ContentHolder;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.session.SessionHelper;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.groupware.company.Department;
import e3ps.project.E3PSProject;
import ext.ket.bom.query.KETCad2BomQueryBean;
import ext.ket.common.util.ObjectUtil;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.service.DrawingDistHelper;
import ext.ket.edm.service.PlmHpIfCtl;
import ext.ket.material.service.MaterialHelper;
import ext.ket.part.base.controller.view.CatalogueExcelView;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.PartBaseDao;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartMassPlan;
import ext.ket.part.entity.dto.KETMassPartDTO;
import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.entity.dto.PartCatalogueDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartMatchingDTO;
import ext.ket.part.entity.dto.PartNamingDTO;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.naming.service.PartNamingcHelper;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.spec.service.PartSpecHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : PartBaseController
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/part/base/*")
public class PartBaseController {

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 16. 오전 10:53:10
     * @method getLastestPart
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getLastestPart")
    public Map<String, Object> getLastestPart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    WTPart part = PartBaseHelper.service.getLatestPart((String) reqMap.get("partNo"));

	    if (part != null) {
		PartBaseDTO dto = new PartBaseDTO();
		dto.setPartOid(CommonUtil.getOIDString(part));
		dto = PartBaseHelper.service.viewDetailPart(dto, getLocale());

		List<PartClassAttrLinkDTO> list = dto.getPartClassAttrLinkDTOList();

		dto.setPartClassAttrLinkDTOList(null);
		resMap = ObjectUtil.manager.converObjectToMap(dto);

		for (PartClassAttrLinkDTO attrLink : list) {
		    PartAttributeDTO attrDTO = attrLink.getPartAttributeDTO();

		    if (attrDTO != null) {
			String attrCode = attrDTO.getAttrCode();
			String attrCodeType = attrDTO.getAttrCodeType();
			String codeValue = (String) resMap.get(attrCode);

			if (StringUtil.checkString(attrCodeType)) {
			    NumberCode code = NumberCodeHelper.manager.getNumberCode(attrCodeType, codeValue);

			    if (code != null) {
				resMap.put(attrCode + "Name", code.getName());
			    }

			} else if ("spMaterialInfo".equals(attrCode) || "spMaterNotFe".equals(attrCode)) {

			    resMap.put(attrCode + "Name", PartBaseHelper.service.getMaterialInfo(codeValue));

			} else if ("spManufacPlace".equals(attrCode) || "spDieWhere".equals(attrCode)) {

			    resMap.put(attrCode + "Name", PartBaseHelper.service.getManufacPlaceName(codeValue));

			} else if ("spMTerminal".equals(attrCode) || "spMConnector".equals(attrCode) || "spMClip".equals(attrCode)
			        || "spMCover".equals(attrCode) || "spMRH".equals(attrCode) || "spMWireSeal".equals(attrCode)
			        || "spMatchBulb".equals(attrCode)) {

			    PartBaseDao dao = new PartBaseDao();
			    resMap.put(attrCode + "Name", dao.getALink(codeValue, "openViewPart"));

			} else {

			    resMap.put(attrCode, codeValue);

			}

		    }
		}

		resMap.put("oid", CommonUtil.getOIDString(part));
		resMap.put("result", true);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    // 부품 검색화면
    @RequestMapping("/listPart")
    public void listPart(Model model, HttpServletRequest request) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############ part list search  ############");
	Kogger.debug(getClass(), "###########################################");

	// model.addAttribute("isAdmin", PartBaseHelper.service.isAdmin());

	String serverDomainName = request.getServerName(); // plmapdev.ket.com
	if (serverDomainName != null && serverDomainName.indexOf("plmapdev") != -1) {
	    model.addAttribute("isSpecAdmin", true);
	} else {
	    boolean isSpecAdmin = PartBaseHelper.service.isMember("사양관리") || PartBaseHelper.service.isMember("KETS_PMO");
	    model.addAttribute("isSpecAdmin", isSpecAdmin);
	}

	// wcadmin - Catalogue Export
	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	if (user != null) {
	    String id = user.getAuthenticationName();
	    if ("wcadmin".equals(id)) {
		model.addAttribute("isWcadmin", true);
	    } else {
		model.addAttribute("isWcadmin", false);
	    }
	} else {
	    model.addAttribute("isWcadmin", false);
	}
    }

    // 부품 검색화면
    @RequestMapping("/listPartPopup")
    public void listPartPopup(
	    @RequestParam(value = "mode", required = true) String mode, // [multi|one](legacy), [m|s] 가능
	    @RequestParam(value = "fncall", required = false) String fncall, // Opener 리턴 함수 지정
	    @RequestParam(value = "modal", required = true) String modal, // Modal 여부 지정
	    @RequestParam(value = "fromPage", required = false) String fromPage,
	    @RequestParam(value = "pType", required = false) String pType, // 외부에서 interface되는 partType
	    @RequestParam(value = "number", required = false) String number, @RequestParam(value = "name", required = false) String name,
	    @RequestParam(value = "projectNo", required = false) String projectNo, // 프로젝트 번호(','로 연결하여 복수개넘김)
	    Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############ part list PopUp   ############");
	Kogger.debug(getClass(), "###########################################");

	String multi = ("multi".equalsIgnoreCase(mode) || "m".equalsIgnoreCase(mode)) ? "M" : "S"; // default: Single

	// 부품 유형 고정 Flag ex) : 제품으로 fix, 금형으로 fix 등에 사용
	if (org.apache.commons.lang.StringUtils.isEmpty(pType)) { // 1) 외부에서 부품유형을 정의 안함
	    pType = "A"; // 기본적으로 전체를 선택하도록 처리
	} else { // 외부에서 부품유형을 정의해서 바로 호출 경우 - 부품유형이 고정됨
	}

	model.addAttribute("mode", multi);
	model.addAttribute("modal", modal);
	model.addAttribute("fncall", fncall);
	model.addAttribute("fromPage", fromPage);
	model.addAttribute("partType", pType.toUpperCase());
	model.addAttribute("number", number);
	model.addAttribute("name", name);
	model.addAttribute("projectNo", projectNo);

	Kogger.debug(getClass(), "@@@@@@@@@@@@@ mode: [" + mode + "](multi=" + multi + ")" + ",  fncall: [" + fncall + "], modal: ["
	        + modal + "], fromPage: [" + fromPage + "], pType: [" + pType + "], projectNo: [" + projectNo + "]");

    }

    // 부품 검색시에 분류체계를 여러 개 선택 함에 따라 속성을 mix해서 가져옴
    @RequestMapping("/registPropsMix")
    public void registPropsMix(@RequestParam(value = "oid") String clazOid, Model model) throws Exception {

	List<PartClassAttrLinkDTO> list = PartBaseHelper.service.getPartPropsMix(clazOid, getLocale());
	model.addAttribute("resultList", list);
    }

    // 부품 검색 Grid 용
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(@RequestParam(value = "pageTotalSize") String pageTotalSize,
	    PartSearchMainDTO partSearchMainDTO) throws Exception {

	Kogger.debug(getClass(), "# pageTotalSize:" + pageTotalSize);

	PageControl pageControl = PartBaseHelper.service.searchMainPartList(partSearchMainDTO, pageTotalSize);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    // 부품 상세 조회 화면
    @RequestMapping("/viewDetailPartForm")
    public void viewDetailPartForm(@ModelAttribute PartBaseDTO partBaseDTO, Model model, HttpServletRequest request) throws Exception {

	String departmentName = null;
	try {

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    Department department = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {

		e3ps.groupware.company.People people = (e3ps.groupware.company.People) qr.nextElement();
		department = people.getDepartment();
		departmentName = department.getName();
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	if ((departmentName != null && (departmentName.startsWith("연구개발") && departmentName.endsWith("팀")))
	        || (departmentName != null && (departmentName.startsWith("커넥터") && departmentName.endsWith("팀")))
	        || (departmentName != null && (departmentName.startsWith("커넥터") && departmentName.endsWith("본부")))
	        || (departmentName != null && (departmentName.startsWith("커넥터") && departmentName.endsWith("센터")))) {
	    model.addAttribute("isDevNResearchTeam", true);
	} else {
	    model.addAttribute("isDevNResearchTeam", false);
	}

	PartBaseDTO dto = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());
	model.addAttribute("baseDto", dto);

	// 연구개발x팀
	// 로그인 사용자 정보 (OID)
	model.addAttribute("isAdmin", PartBaseHelper.service.isAdmin());

	WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();

	model.addAttribute("isPartModify", user.getName().equals("supersonic") || user.getName().equals("mino0206"));// 최희락 대리, 전자사업부 이민호
	                                                                                                             // part 수정권한 부여

	String serverDomainName = request.getServerName(); // plmapdev.ket.com
	boolean isSpecAdmin = false;
	if (serverDomainName != null && serverDomainName.indexOf("plmapdev") != -1) {
	    isSpecAdmin = true;
	    model.addAttribute("isSpecAdmin", isSpecAdmin);
	} else {
	    isSpecAdmin = PartBaseHelper.service.isMember("사양관리"); // || PartBaseHelper.service.isMember("KETS_PMO");
	    model.addAttribute("isSpecAdmin", isSpecAdmin);
	}

	// 반제품 등록 버튼 안보이게
	if ("F".equals(dto.getSpPartType()) && isSpecAdmin) {
	    String halbPartNumber = "H" + dto.getPartNumber();
	    WTPartMaster halbMast = PartBaseHelper.service.getMaster(halbPartNumber);
	    if (halbMast == null) {
		model.addAttribute("hasHalbPartOfFert", false);
	    } else {
		model.addAttribute("hasHalbPartOfFert", true);
	    }
	} else {
	    model.addAttribute("hasHalbPartOfFert", false);
	}

	// 상품 등록 버튼 활성화 조건 : 분류체계코드가 Z10 으로 시작(jsp에서 체크함), 제품인 경우, 해당 품번에 상품이 없는 경우.(현재는 전자사업부 전장IT WIRING Assy에 한해 적용함) 전자사업부 박주미 요청 by 황정태
	// 2015.07.14
	if ("F".equals(dto.getSpPartType()) && isSpecAdmin) {
	    String hawaPartNumber = "K" + dto.getPartNumber();
	    WTPartMaster hawaMast = PartBaseHelper.service.getMaster(hawaPartNumber);
	    if (hawaMast == null) {
		model.addAttribute("hasHawaPartOfFert", false);
	    } else {
		model.addAttribute("hasHawaPartOfFert", true);
	    }
	} else {
	    model.addAttribute("hasHawaPartOfFert", false);
	}

	if (StringUtils.isEmpty(dto.getEcoNo())) {
	    model.addAttribute("isEcoOwner", false);
	} else {
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
	    String ecoId = dto.getEcoNo().replace("ECO-", "");
	    boolean isEcoOwner = prodEcoBeans.isWorkerEcoPart(ecoId, dto.getPartNumber(), dto.getPartRevision());
	    model.addAttribute("isEcoOwner", isEcoOwner);
	}
	boolean isKetn = false;
	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(dto.getPartClazOid());
	if (claz != null && "N".equals(claz.getDivisionFlag())) {
	    isKetn = true;
	}

	model.addAttribute("isKetn", isKetn);

	// Added by jinkim 151204
	WTPart wtpart = (WTPart) CommonUtil.getObject(dto.getPartOid());
	ContentHolder holder = (ContentHolder) wtpart;
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(holder));

    }

    // 부품 PartList 조회 화면
    @RequestMapping("/viewPartListForm")
    public void viewPartListForm(@ModelAttribute PartListDTO partListDTO, Model model) throws Exception {

	PartListDTO resultList = PartBaseHelper.service.viewPartList(partListDTO, getLocale());
	model.addAttribute("result", resultList);
    }

    // 부품 등록 화면
    @RequestMapping("/registPartForm")
    public void registPartForm(@RequestParam(value = "partProdOid", required = false) String partProdOid,
	    @RequestParam(value = "projectOid", required = false) String projectOid,
	    @RequestParam(value = "moldDieNo", required = false) String moldDieNo, Model model) throws Exception {

	if (StringUtils.isNotEmpty(projectOid)) {
	    E3PSProject e3psProject = (E3PSProject) CommonUtil.getObject(projectOid);
	    String devLevel = PartBaseHelper.service.getProjectDevLevel(e3psProject);
	    model.addAttribute("spPartDevLevel", devLevel);
	    model.addAttribute("projectNo", e3psProject.getPjtNo());
	    model.addAttribute("projectName", e3psProject.getPjtName());
	} else {
	    model.addAttribute("spPartDevLevel", "");
	    model.addAttribute("projectNo", "");
	    model.addAttribute("projectName", "");
	}

	// 반제품의 개발단계, 부품명 정보 입력
	if (StringUtils.isNotEmpty(partProdOid)) {
	    WTPartMaster halbPartMast = (WTPartMaster) CommonUtil.getObject(partProdOid);
	    WTPart halbPart = PartBaseHelper.service.getLatestPart(halbPartMast);
	    model.addAttribute("partName", halbPart.getName());
	    String spPartDevLevel = PartSpecGetter.getPartSpec(halbPart, PartSpecEnum.SpPartDevLevel);
	    model.addAttribute("spPartDevLevel", spPartDevLevel);
	    model.addAttribute("partProdNumber", halbPart.getNumber());
	    model.addAttribute("partProdOid", partProdOid);
	}

	// 금형부품 선택되도록 하는 로직
	if (StringUtils.isNotEmpty(moldDieNo)) {
	    model.addAttribute("moldDieNo", moldDieNo);
	    KETPartClassification moldClaz = PartClazHelper.service.getPartClassificationByClazCode("T");
	    model.addAttribute("moldDieNoClazOid", moldClaz == null ? "" : CommonUtil.getOIDString(moldClaz));
	    model.addAttribute("moldDieNoClazKrName", moldClaz == null ? "" : moldClaz.getClassKrName());
	}
    }

    // 부품 등록시 분류체계에 따라 속성 정보 가져옴( hidden iframe )
    @RequestMapping("/registProps")
    public void registProps(@RequestParam(value = "oid") String clazOid,
	    @RequestParam(required = false, value = "classPartType") String classPartType, Model model) throws Exception {

	List<PartClassAttrLinkDTO> list = PartBaseHelper.service.getPartProps(clazOid, getLocale());
	model.addAttribute("resultList", list);
	KETPartClassification claz = PartBaseHelper.service.getPartClassification(clazOid);

	if (list.size() > 0) {

	    model.addAttribute("numberingCode", list.get(0).getPartClassificationDTO().getNumberingCode());
	    model.addAttribute("partClassificType", StringUtil.checkNull(list.get(0).getPartClassificationDTO().getPartClassificType()));

	    if (StringUtils.isEmpty(classPartType)) {
		model.addAttribute("classPartType", list.get(0).getPartClassificationDTO().getClassPartType());
	    } else {
		model.addAttribute("classPartType", classPartType);
	    }

	} else {

	    model.addAttribute("numberingCode", claz.getNumberingCode());
	    if (StringUtils.isEmpty(classPartType)) {
		model.addAttribute("classPartType", claz.getClassPartType());
	    } else {
		model.addAttribute("classPartType", classPartType);
	    }
	    model.addAttribute("partClassificType", StringUtil.checkNull(claz.getPartClassificType()));
	}

	// 부품명 자동 완성
	model.addAttribute("partNamingOid", PartBaseHelper.service.getPartNamingOid(clazOid));

	// 화면에 고정된 프로젝트 정보 , 중량정보 필수 체크
	boolean[] projectWeightCheck = PartSpecHelper.service.checkProjectWeightEss(clazOid);

	boolean isEsseProject = projectWeightCheck[0];
	boolean isCheckedProject = projectWeightCheck[1];
	boolean isEsseWeight = projectWeightCheck[2];
	boolean isCheckedWeight = projectWeightCheck[3];

	model.addAttribute("isEsseProject", isEsseProject);
	model.addAttribute("isCheckedProject", isCheckedProject);
	model.addAttribute("isEsseWeight", isEsseWeight);
	model.addAttribute("isCheckedWeight", isCheckedWeight);
	model.addAttribute("clazOid", clazOid);
	model.addAttribute("clazDivision", claz.getDivisionFlag());

    }

    // 부품 - 반제품 생성 화면
    @RequestMapping("/registHalbPartForm")
    public void registHalbPartForm(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {
	PartBaseDTO dto = null;
	if ("RK30".equals(partBaseDTO.getNumberingCode())) {
	    dto = PartBaseHelper.service.RK30viewDetailPart(partBaseDTO, getLocale());
	} else {
	    dto = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());
	}
	model.addAttribute("baseDto", dto);

	// 화면에 고정된 프로젝트 정보 , 중량정보 필수 체크
	boolean[] projectWeightCheck = PartSpecHelper.service.checkProjectWeightEss(dto.getPartClazOid());

	boolean isEsseProject = projectWeightCheck[0];
	boolean isCheckedProject = projectWeightCheck[1];
	boolean isEsseWeight = projectWeightCheck[2];
	boolean isCheckedWeight = projectWeightCheck[3];

	model.addAttribute("isEsseProject", isEsseProject);
	model.addAttribute("isCheckedProject", isCheckedProject);
	model.addAttribute("isEsseWeight", isEsseWeight);
	model.addAttribute("isCheckedWeight", isCheckedWeight);
    }

    // 부품 복사 생성 화면
    @RequestMapping("/registCopyPartForm")
    public void registCopyPartForm(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {

	PartBaseDTO dto = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());

	model.addAttribute("baseDto", dto);

	// 화면에 고정된 프로젝트 정보 , 중량정보 필수 체크
	boolean[] projectWeightCheck = PartSpecHelper.service.checkProjectWeightEss(dto.getPartClazOid());

	boolean isEsseProject = projectWeightCheck[0];
	boolean isCheckedProject = projectWeightCheck[1];
	boolean isEsseWeight = projectWeightCheck[2];
	boolean isCheckedWeight = projectWeightCheck[3];

	model.addAttribute("isEsseProject", isEsseProject);
	model.addAttribute("isCheckedProject", isCheckedProject);
	model.addAttribute("isEsseWeight", isEsseWeight);
	model.addAttribute("isCheckedWeight", isCheckedWeight);

    }

    // 부품 채번시, 부품 등록 수정시 No 체크
    @RequestMapping("/existPartNumber")
    @ResponseBody
    public String existWTPartNumber(@RequestParam(value = "partNumber") String partNumber,
	    @RequestParam(value = "partType", required = false) String partType, // [multi|one](legacy),
	                                                                         // [m|s] 가능
	    Model model) throws Exception {

	try {

	    boolean exist = PartBaseHelper.service.existWTPartNumber(partNumber, partType);

	    if (exist)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 부품 채번시, 부품 등록 수정시 No 체크
    @RequestMapping("/existErpPartNumber")
    @ResponseBody
    public String existErpPartNumber(@RequestParam(value = "partNumber") String partNumber, Model model) throws Exception {

	try {

	    boolean exist = PartBaseHelper.service.existErpPartNumber(partNumber);

	    if (exist)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 등록
    @RequestMapping("/insertPart")
    @ResponseBody
    public String insertPart(@ModelAttribute PartBaseDTO partBaseDTO, SessionStatus status, Model model, HttpServletResponse response,
	    HttpServletRequest request) throws Exception {
	try {

	    /*
	     * WTPart part = PartBaseHelper.service.createWTPart(partBaseDTO);
	     * 
	     * status.setComplete(); // part No, revision - WC, revision - NewDefine, Default Unit, Part Name String partOid =
	     * CommonUtil.getOIDString(part); String partNo = part.getNumber(); String wtRevision = part.getVersionIdentifier().getValue();
	     * String ketRevision = StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision)); String defaultUnit
	     * = StringUtil.checkNull(part.getDefaultUnit().toString()); String spMaterDie =
	     * StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterDie)); String spPartType =
	     * StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType)); String partName = part.getName();
	     * 
	     * return partOid + PartUtil.SEPERATER + partNo + PartUtil.SEPERATER + wtRevision + PartUtil.SEPERATER + ketRevision +
	     * PartUtil.SEPERATER + defaultUnit + PartUtil.SEPERATER + spMaterDie + PartUtil.SEPERATER + partName + PartUtil.SEPERATER +
	     * spPartType;
	     */

	    WTPart part = null;
	    part = PartBaseHelper.service.createWTPart(partBaseDTO);

	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\""
		    + "저장되었습니다."
		    + "\");"
		    + "\n try{if(opener && opener.newpartaddFn){var bomParamArray = data.split('↕');opener.newpartaddFn(bomParamArray);}}catch(e){alert(e.message);} "
		    + "\n" + "\n parent.location.href='/plm/jsp/bom/ViewPart.jsp?oid=" + CommonUtil.getOIDString(part) + "';"
		    + "\n </script>";
	    pwriter.println(str);
	    pwriter.close();

	    return "OK";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 - [ERP] 등록
    @RequestMapping("/insertErpPart")
    @ResponseBody
    public String insertErpPart(@ModelAttribute PartBaseDTO partBaseDTO, SessionStatus status, Model model) throws Exception {
	try {

	    WTPart part = PartBaseHelper.service.createWTPartByErp(partBaseDTO);
	    status.setComplete();
	    return CommonUtil.getOIDString(part);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 - 반제품 등록
    @RequestMapping("/insertHalbPart")
    @ResponseBody
    public String insertHalbPart(@ModelAttribute PartBaseDTO partBaseDTO, SessionStatus status, Model model) throws Exception {
	try {

	    WTPart part = PartBaseHelper.service.createWTPartByHalb(partBaseDTO);
	    status.setComplete();
	    return CommonUtil.getOIDString(part);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 복사 등록
    @RequestMapping("/insertCopyPart")
    @ResponseBody
    public String insertCopyPart(@ModelAttribute PartBaseDTO partBaseDTO, SessionStatus status, Model model) throws Exception {
	try {

	    WTPart part = PartBaseHelper.service.createWTPartByCopy(partBaseDTO);
	    status.setComplete();
	    return CommonUtil.getOIDString(part);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 수정 화면
    @RequestMapping("/modifyPartForm")
    public void modifyPartForm(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {

	PartBaseDTO dto = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());
	model.addAttribute("baseDto", dto);

	// Added by jinkim 151204
	WTPart wtpart = (WTPart) CommonUtil.getObject(dto.getPartOid());
	ContentHolder holder = (ContentHolder) wtpart;
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(holder));

    }

    // 부품 결재완료 후 수정 화면
    @RequestMapping("/modifyPartAfterAppForm")
    public void modifyPartAfterAppForm(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {

	PartBaseDTO dto = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());

	model.addAttribute("baseDto", dto);
    }

    // 부품 수정
    @RequestMapping("/updatePart")
    @ResponseBody
    public String updatePart(@ModelAttribute PartBaseDTO partBaseDTO, Model model, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	try {

	    WTPart part = null;
	    part = PartBaseHelper.service.updateWTPart(partBaseDTO);

	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
		    + "\n if(parent){if(parent.opener){try{parent.opener.PartList.reload();}catch(e){}}} " + "\n"
		    + "\n parent.location.href='/plm/jsp/bom/ViewPart.jsp?oid=" + CommonUtil.getOIDString(part) + "';" + "\n </script>";
	    pwriter.println(str);
	    pwriter.close();

	    return "OK";

	    // return CommonUtil.getOIDString(part);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 결재완료 후 수정
    @RequestMapping("/updatePartAfterApp")
    @ResponseBody
    public String updatePartAfterApp(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {
	try {

	    WTPart part = PartBaseHelper.service.updateWTPartAfterApp(partBaseDTO);
	    return CommonUtil.getOIDString(part);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 삭제
    @RequestMapping("/deletePart")
    @ResponseBody
    public String deletePart(@ModelAttribute PartBaseDTO partBaseDTO, Model model) throws Exception {

	try {

	    PartBaseHelper.service.deletePart(partBaseDTO, getLocale());
	    return "OK";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 삭제시 BOM 체크
    @RequestMapping("/existPartBom")
    @ResponseBody
    public String existPartBom(@RequestParam(value = "partNumber") String partNumber, Model model) throws Exception {

	try {

	    KETCad2BomQueryBean bean = new KETCad2BomQueryBean();
	    boolean exist = bean.isUsedBOMComp(partNumber);

	    if (exist)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 부품 삭제시 BOM 체크
    @RequestMapping("/existPartDieHalbLink")
    @ResponseBody
    public String existPartDieHalbLink(@RequestParam(value = "partNumber") String partNumber, Model model) throws Exception {

	try {

	    boolean exist = PartBaseHelper.service.existPartDieHalbLink(partNumber);

	    if (exist)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 부품 사용안함 상태로 변경 : ERP 포함
    @RequestMapping("/notUsePart")
    @ResponseBody
    public String notUsePart(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {

	try {

	    String flag = null;

	    try {
		flag = PartBaseHelper.service.notUsePart(partOid);
	    } catch (Exception e) {
		flag = "E";
	    }

	    if ("Y".equals(flag)) {
		Kogger.debug(getClass(), "부품 사용안함에 성공했습니다.");
	    } else if ("N".equals(flag)) {
		Kogger.debug(getClass(), "부품 사용안함에 실패했습니다.");
	    } else if ("E".equals(flag)) {
		Kogger.debug(getClass(), "부품 사용않함 상태 변경 중 에러가 발생했습니다.");
	    } else if ("O".equals(flag)) {
		Kogger.debug(getClass(), "이미 삭제된 부품입니다.");
	    } else if ("W".equals(flag)) {
		Kogger.debug(getClass(), "ERP에 존재하지 않는 부품입니다.");
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 부품명 자동완성 화면 호출
    @RequestMapping("/genNamePopup")
    public void genNamePopup(@RequestParam(value = "oid") String partNamingOid, Model model) throws Exception {

	PartNamingDTO partNamingDTO = null;

	if (StringUtils.isEmpty(partNamingOid)) {
	    partNamingDTO = new PartNamingDTO();
	} else {
	    partNamingDTO = PartNamingcHelper.service.searchSelectedPartNaming(partNamingOid, getLocale());
	}

	model.addAttribute("result", partNamingDTO);
    }

    // 부품 마이그레이션 등록 화면
    @RequestMapping("/migPartFromErpForm")
    public void migPartFromErpForm(@RequestParam(value = "partNumber") String partNumber, Model model) throws Exception {

	PartBaseDTO dto = PartBaseHelper.service.migPartFromErpForm(partNumber, getLocale());
	model.addAttribute("baseDto", dto);
	model.addAttribute("partProdOid", "");
    }

    // 부품 조회 화면
    @RequestMapping("/viewPartByNo")
    public String viewPartByNo(@RequestParam(value = "partNumber") String partNumber) throws Exception {

	WTPart part = PartBaseHelper.service.getLatestPart(partNumber);
	String oid = CommonUtil.getOIDString(part);
	return "/jsp/bom/ViewPart.jsp?poid=" + oid;
    }

    // 도면 조회 화면
    @RequestMapping("/viewEpmByNo")
    public String viewEpmByNo(@RequestParam(value = "epmNumber") String epmNumber) throws Exception {

	EPMDocument epm = PartBaseHelper.service.getLatestEPM(epmNumber);
	String oid = CommonUtil.getOIDString(epm);
	return "/jsp/edm/ViewEPMDocumentFw.jsp?oid=" + oid;

    }

    // 프로젝트의 개발단계 가져오기
    @RequestMapping("/getProjectDevLevel")
    @ResponseBody
    public String getProjectDevLevel(@RequestParam(value = "projectNumber") String projectNumber, SessionStatus status, Model model)
	    throws Exception {
	try {

	    String devLevel = PartBaseHelper.service.getProjectDevLevel(projectNumber);
	    status.setComplete();
	    return devLevel;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // Die - Halb Link 생성
    @RequestMapping("/addDieHalbLink")
    @ResponseBody
    public String addDieHalbLink(@RequestParam(value = "dieOid") String dieOid, @RequestParam(value = "halbOid") String halbOid, Model model)
	    throws Exception {

	try {

	    boolean success = PartBaseHelper.service.addDieHalbLink(dieOid, halbOid);

	    if (success)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // Die - Halb Link 삭제
    @RequestMapping("/deleteDieHalbLink")
    @ResponseBody
    public String deleteDieHalbLink(@RequestParam(value = "dieOid") String dieOid, @RequestParam(value = "halbOid") String halbOid,
	    Model model) throws Exception {

	try {

	    boolean success = PartBaseHelper.service.deleteDieHalbLink(dieOid, halbOid);

	    if (success)
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 원재료 Type - Select Box 용
    @RequestMapping("/getMaterialTypeList")
    @ResponseBody
    public Map<String, Object> getMaterialTypeList(@RequestParam(value = "clazOid") String clazOid,
	    @RequestParam(value = "spMaterMaker") String spMaterMaker, Model model) throws Exception {

	Map<String, Object> map = PartBaseHelper.service.getMaterialTypeList(clazOid, spMaterMaker);
	return map;
    }

    // 재질(Grade) - Select Box 용
    @RequestMapping("/getMaterialInfoList")
    @ResponseBody
    public Map<String, Object> getMaterialInfoList(@RequestParam(value = "clazOid") String clazOid,
	    @RequestParam(value = "spMaterMaker") String spMaterMaker, @RequestParam(value = "spMaterType") String spMaterType, Model model)
	    throws Exception {

	Map<String, Object> map = PartBaseHelper.service.getMaterialInfoList(clazOid, spMaterMaker, spMaterType);
	return map;
    }

    // 부품 결재 조회 화면
    @RequestMapping("/viewWFHistory")
    public String viewWFHistory(@RequestParam(value = "ecoId") String ecoId, Model model) throws Exception {

	ProdEcoBeans beans = new ProdEcoBeans();
	String oid = "";
	if (ecoId != "") {
	    WTChangeOrder2 eco = beans.getEcoByNo(ecoId);
	    oid = CommonUtil.getOIDString(eco);
	} else {
	    oid = "";
	}
	return "/jsp/wfm/ApprovalHistory.jsp?pboOid=" + oid;
    }

    // Halb의 Die List
    @RequestMapping("/getDieListOfHalb")
    @ResponseBody
    public Map<String, Object> getDieListOfHalb(@RequestParam(value = "halbOid") String halbOid, Model model) throws Exception {

	Map<String, Object> map = PartBaseHelper.service.getDieListOfHalb(halbOid);
	return map;
    }

    // Die의 상세 정보
    @RequestMapping("/getDieDetailOfHalb")
    @ResponseBody
    public Map<String, Object> getDieDetailOfHalb(@RequestParam(value = "dieOid") String dieOid, Model model) throws Exception {

	Map<String, Object> map = PartBaseHelper.service.getDieDetailOfHalb(dieOid, getLocale());
	return map;
    }

    // ECM의 매칭 부품 상세 조회 화면
    @RequestMapping("/viewMatchingPartPopup")
    public void viewMatchingPartPopup(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {

	List<PartMatchingDTO> dtoList = PartBaseHelper.service.getMatchingPartList(partOid);
	model.addAttribute("resultList", dtoList);

    }

    // ECM의 매칭 부품 개수 반환
    @RequestMapping("/matchingPartCount")
    @ResponseBody
    public String matchingPartCount(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {

	try {

	    int count = PartBaseHelper.service.getMatchingPartCount(partOid);

	    return String.valueOf(count);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 부품 검색 결과 Excel 저장
    @RequestMapping("/catalogueList")
    public ModelAndView catalogueList(PartSearchMainDTO partSearchMainDTO, ModelMap model) throws Exception {

	List<PartCatalogueDTO> list = PartBaseHelper.service.getCatalogueList(partSearchMainDTO);
	return new ModelAndView(new CatalogueExcelView(list));
    }

    // 도면 부품 연계 화면 로딩
    @RequestMapping("/connEpmPartPopup")
    public void connEpmPartPopup(@RequestParam(value = "epmOid") String epmOid, Model model) throws Exception {

	EPMDocument empDoc = (EPMDocument) CommonUtil.getObject(epmOid);

	model.addAttribute("epmNo", empDoc.getNumber());
	HashMap ibaValues = EDMAttributes.getIBAValues(empDoc, Locale.KOREAN);
	String epmVer = (ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION) == null) ? "&nbsp;" : (String) ibaValues
	        .get(EDMHelper.IBA_MANUFACTURING_VERSION);
	model.addAttribute("epmVer", epmVer);
	model.addAttribute("epmName", empDoc.getName());
	model.addAttribute("epmOid", epmOid);
    }

    // 도면 부품 연계 등록
    @RequestMapping("/saveEpmPartPopup")
    @ResponseBody
    public String saveEpmPartPopup(@RequestParam(value = "epmOid") String epmOid, @RequestParam(value = "partOid") String partOid,
	    @RequestParam(value = "referenceType") String referenceType, @RequestParam(value = "requied") String requied,
	    @RequestParam(value = "ecad") String ecad, SessionStatus status, Model model) throws Exception {
	try {

	    boolean result = PartBaseHelper.service.connEpm2Part(epmOid, partOid, referenceType, requied, ecad);
	    status.setComplete();

	    return result ? "Succes" : "Fail";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 도면 (부품화면) viewable 재생성
    @RequestMapping("/regeneateVW")
    @ResponseBody
    public String regeneateVW(@RequestParam(value = "oid") String oid, SessionStatus status, Model model) throws Exception {
	try {

	    boolean result = PartBaseHelper.service.regenerateViewable(oid);
	    status.setComplete();

	    return result ? "Succes" : "Fail";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    private Locale getLocale() throws Exception {
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	KETMessageService messageService = KETMessageService.getMessageService(request);
	if (messageService == null) {
	    WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();
	    messageService = new KETMessageService(KETMessageService.getUserLocale(usr));
	    KETMessageService.setMessageService((HttpSession) request.getSession(), messageService);
	}
	return messageService.getLocale();
    }

    // 대표금형에 반제품, 원자재 연결시 연결 가능여부 판단. ERP의 자재그룹코드로 체크한다. 2015.03.27 황정태
    @RequestMapping("/getPermissionPartErp")
    @ResponseBody
    public String getPermissionPartErp(@RequestParam(value = "partnumber") String partnumber, Model model) throws Exception {

	try {
	    String NotpermissionErpProdCodeRange = "201010,201020,201030,201040,201050,201080,201090,201095,202010,202020,202030,202040,202050,202090,202091,601000,602000";
	    WTPart part = PartBaseHelper.service.getLatestPart(partnumber);

	    KETPartClassification claz = PartUtil.getPartClassification(part);
	    String ErpProdCode = claz.getErpProdCode();
	    String Ispermission = "Y";
	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    if (!"H".equals(partType) && !"R".equals(partType) && !"W".equals(partType)) {
		Ispermission = "N";
	    }
	    if (!"H".equals(partType) && NotpermissionErpProdCodeRange.indexOf(ErpProdCode) != -1) {
		Ispermission = "N";
	    }
	    return Ispermission;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "N";
	}
    }

    // KETN 부품일때 ERP에 자재마스터 생성
    @RequestMapping("/ketnPartSend")
    @ResponseBody
    public String ketnPartSend(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {
	// -. ERP에 존재하는 부품인가
	boolean isExisErp = false;
	boolean isKetn = false;
	WTPart part = (WTPart) CommonUtil.getObject(partOid);
	try {
	    ErpPartHandler erpPartHandler = new ErpPartHandler();

	    isExisErp = erpPartHandler.existErpPart(part.getNumber());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "E";
	}
	if (isExisErp) {
	    return "S";
	}

	try {// 사업부가 KETN인가
	    KETPartClassification claz = PartUtil.getPartClassification(part);
	    if ("N".equals(claz.getDivisionFlag())) {
		isKetn = true;
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "E";
	}

	if (!isKetn) {
	    return "X";
	}

	try {
	    String flag = null;
	    boolean infResult = false;
	    try {
		infResult = KETBomHelper.service.getBomInterface3(partOid)[0];
	    } catch (Exception e) {
		flag = "E";
	    }

	    if (infResult) {
		flag = "Y";
	    } else {
		flag = "E";
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 진행중인 ECO 번호 확인(Part 삭제시 체크)
    @RequestMapping("/ecoUsePart")
    @ResponseBody
    public String ecoUsePart(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {

	try {
	    WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);
	    String wtRevision = wtPart.getVersionIdentifier().getValue();

	    ArrayList<WTChangeOrder2> relatedEcoList = EcmUtil.getECOexistEcaBomLink(wtPart.getNumber(), wtRevision);
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
	    int relatedEcoListSize = (relatedEcoList != null) ? relatedEcoList.size() : 0;
	    String ecoId = "";
	    String ecoIds = "";
	    if (relatedEcoListSize > 0) {
		for (int j = 0; j < relatedEcoListSize; j++) {
		    State ecoState = null;
		    // SAP I/F의 성공여부
		    boolean isSucessSapInterface = true;

		    wt.change2.WTChangeOrder2 wtChangeOrder2 = relatedEcoList.get(j);
		    if (wtChangeOrder2 instanceof e3ps.ecm.entity.KETProdChangeOrder) {
			e3ps.ecm.entity.KETProdChangeOrder eco = (e3ps.ecm.entity.KETProdChangeOrder) wtChangeOrder2;
			ecoState = eco.getLifeCycleState();
			ecoId = eco.getEcoId();
			// isSucessSapInterface = prodEcoBeans.isSucessSapInterface(ecoId);
			//
			// if (ecoState.equals(State.toState("APPROVED"))) {
			// if (isSucessSapInterface) {
			// continue;
			// }
			// }
		    } else if (wtChangeOrder2 instanceof e3ps.ecm.entity.KETMoldChangeOrder) {
			e3ps.ecm.entity.KETMoldChangeOrder eco = (e3ps.ecm.entity.KETMoldChangeOrder) wtChangeOrder2;
			ecoState = eco.getLifeCycleState();
			ecoId = eco.getEcoId();
			// isSucessSapInterface = prodEcoBeans.isSucessSapInterface(ecoId);
			//
			// if (ecoState.equals(State.toState("APPROVED"))) {
			// if (isSucessSapInterface) {
			// continue;
			// }
			// }
		    }

		    ecoIds += ecoId + ", ";

		}

		if (!ecoIds.equals("") && ecoIds.lastIndexOf(", ") > -1) {
		    ecoIds = ecoIds.substring(0, ecoIds.lastIndexOf(", "));

		}

	    }

	    return ecoIds;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 홈페이지 일괄 인터페이스
    @RequestMapping("/plm2hompageMulti")
    @ResponseBody
    public String plm2hompageMulti(Model model) throws Exception {

	String filePath = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\trans\\edm\\transHompage.xlsx";

	ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();

	int failCnt = 0;
	int successCnt = 0;

	String flag = null;
	try {

	    for (int i = startRowNo; i <= rowSize; i++) {
		excel.setRow(i);
		String distOid = excel.getStrValue(0);

		// // 부품정보를 먼저 홈페이지로 이관
		// WTPart part = PartBaseHelper.service.getLatestPart(partNo);
		// String partOid = CommonUtil.getOIDString(part);
		// System.out.println("partOid : " + partOid);
		// if (StringUtils.isEmpty(partOid)) {
		// Kogger.debug(getClass(), "##################################################");
		// Kogger.debug(getClass(), "### 존재하지 않는 PartNO : " + partNo);
		// Kogger.debug(getClass(), "##################################################");
		// continue;
		// }

		KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();
		distOid = CommonUtil.getOIDLongValue2Str(distOid);
		paramObject.setOid(distOid);
		// paramObject.setDirect2Hompage("Y");
		// paramObject.setPartOid(partOid);

		List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.sendHpList(paramObject);

		// distReqEpmDocList 정보로 mssql 에 저장 후 리턴
		PlmHpIfCtl send = new PlmHpIfCtl();
		boolean bSuccess = send.savePlmHpIf(distReqEpmDocList);

		if (!bSuccess) {
		    System.out.println("##################################################");
		    System.out.println("### 부품정보 홈페이지 이관 실패 : " + distOid);
		    System.out.println("##################################################");
		    failCnt++;
		} else {
		    successCnt++;
		}
	    }

	    if (failCnt > 0) {
		flag = "N";
	    } else {
		flag = "Y";
	    }

	    if ("Y".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관이 성공했습니다. (성공 : " + successCnt + " 건, 실패 : " + failCnt + " 건");
	    } else if ("N".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관이 실패했습니다. (성공 : " + successCnt + " 건, 실패 : " + failCnt + " 건");
	    } else if ("E".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관 중 에러가 발생했습니다.");
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 홈페이지 인터페이스
    @RequestMapping("/plm2hompage")
    @ResponseBody
    public String plm2hompage(@RequestParam(value = "partOid") String partOid, Model model) throws Exception {

	try {

	    String flag = null;

	    try {
		KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();

		paramObject.setDirect2Hompage("Y");
		paramObject.setPartOid(partOid);

		List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.sendHpList(paramObject);

		// distReqEpmDocList 정보로 mssql 에 저장 후 리턴
		PlmHpIfCtl send = new PlmHpIfCtl();
		boolean bSuccess = send.savePlmHpIf(distReqEpmDocList);

		if (!bSuccess) {
		    flag = "N";
		} else {
		    flag = "Y";
		}
	    } catch (Exception e) {
		e.printStackTrace();
		flag = "E";
	    }

	    if ("Y".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관이 성공했습니다.");
	    } else if ("N".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관이 실패했습니다.");
	    } else if ("E".equals(flag)) {
		Kogger.debug(getClass(), "부품 홈페이지 이관 중 에러가 발생했습니다.");
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    // 시리즈 설명 값 가져오기 (용도 세팅하기 위함) 2016.07.29 황정태
    @RequestMapping("/getNumberCodeDescription")
    @ResponseBody
    public String getNumberCodeDescription(@RequestParam(value = "code") String code, Model model) throws Exception {

	try {

	    String Description = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpSeries.getAttrCodeType(), code, Locale.KOREA);

	    if ("standard".equals(Description))
		return "Y";
	    else
		return "N";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 홈페이지 인터페이스
    @RequestMapping("/scrapAutoGen")
    @ResponseBody
    public String scrapAutoGen(@RequestParam(value = "oid") String oid, Model model) throws Exception {

	try {

	    String flag = null;
	    PartBaseDTO partBaseDTO = new PartBaseDTO();
	    partBaseDTO.setPartOid(oid);
	    partBaseDTO = PartBaseHelper.service.viewDetailPart(partBaseDTO, getLocale());
	    String partNumber = "S" + partBaseDTO.getPartNumber().substring(1);
	    try {
		boolean exist = PartBaseHelper.service.existWTPartNumber(partNumber, "");

		if (exist) {
		    flag = "N";
		} else {
		    PartBaseHelper.service.scrapAutoGen(partBaseDTO);

		    flag = "Y";
		}
	    } catch (Exception e) {
		flag = "E";
	    }

	    if ("Y".equals(flag)) {
		Kogger.debug(getClass(), "스크랩 생성 성공.");
	    } else if ("N".equals(flag)) {
		Kogger.debug(getClass(), "스크랩이 이미 존재합니다.");
	    } else if ("E".equals(flag)) {
		Kogger.debug(getClass(), "스크랩 생성 중 에러가 발생했습니다.");
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}

    }

    @RequestMapping("/getPartSpec")
    @ResponseBody
    public String getPartSpec(String partOid, String attrCode, Model model) throws Exception {

	try {
	    WTPart part = (WTPart) CommonUtil.getObject(partOid);
	    String spec = PartSpecGetter.getPartSpec(part, PartSpecEnum.toPartSpecEnumByAttrCode(attrCode));
	    return spec;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "N";
	}
    }

    @RequestMapping("/partMassPlanList")
    public Model partMassPlanList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	model.addAttribute("isAdmin", CommonUtil.isAdmin());
	return model;
    }

    @ResponseBody
    @RequestMapping("/findPartMassPagingList")
    public Map<String, Object> findPartMassPagingList(KETMassPartDTO dto) throws Exception {

	if (dto.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = PartBaseHelper.service.findPartMassPagingList(dto);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETMassPartDTO> massDTOList = new ArrayList<KETMassPartDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    KETPartMassPlan massPlanPart = (KETPartMassPlan) objArr[0];

	    KETMassPartDTO massDto = new KETMassPartDTO(massPlanPart);
	    String createDate = String.valueOf(objArr[1]);
	    String modifyDate = String.valueOf(objArr[2]);
	    String newPart = String.valueOf(objArr[3]);

	    // materDTO.setCreateDate(createDate);
	    massDto.setModifyDate(modifyDate);
	    massDto.setCreateDate(createDate);
	    if("Y".equals(newPart)){
		massDto.setNewPart("신제품");
	    }
	    
	    massDTOList.add(massDto);
	}

	return EjsConverUtil.convertToDto(massDTOList, pageControl);
    }

    @ResponseBody
    @RequestMapping("/migPartMass")
    public Map<String, Object> migPartMass(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    PartBaseHelper.service.migPartMass();

	    resMap.put("message", "마이그레이션이 완료되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/partMassSync")
    public Map<String, Object> partMassSync(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    PartBaseHelper.service.partMassSync();

	    resMap.put("message", "양산정보 동기화 작업이 끝났습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
    
    @ResponseBody
    @RequestMapping("/saveMassPart")
    public Map<String, Object> saveMassPart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	
	try {
	    resMap = PartBaseHelper.service.saveMassPart(reqMap);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

}
