package ext.ket.project.gate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import wt.vc.Versioned;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.beans.E3PSProjectData;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.AssessSheetDTO;
import ext.ket.project.gate.entity.GateAttribute;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.project.gate.entity.RevisedAssessSheetDTO;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.project.gate.service.AssessSheetHelper;
import ext.ket.project.gate.service.GateCheckSheetHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

@Controller
@RequestMapping("/project/gate/*")
public class AssessSheetController {

    /**
     * 저장
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : create
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/createAssess")
    public String create(AssessSheetDTO dto) throws Exception {
	AssessSheetHelper.service.save(dto);
	return "redirect:/ext/project/gate/listAssess.do";
    }

    /**
     * 등록 페이지
     * 
     * @param ProjectTaskDTO
     * @throws Exception
     * @메소드명 : createForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/createAssessForm")
    public void createForm(AssessSheetDTO dto) throws Exception {
    }

    /**
     * 삭제
     * 
     * @param ProjectTaskDTO
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/deleteAssess")
    public String delete(AssessSheetDTO dto) throws Exception {
	AssessSheetHelper.service.delete(dto);
	return "redirect:/ext/project/gate/listAssess.do";
    }

    /**
     * 수정된 내용 저장
     * 
     * @param oid
     *            , AssessSheetDTO
     * @throws Exception
     * @메소드명 : update
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/updateAssess")
    public String updateAssess(String oid, AssessSheetDTO dto) throws Exception {
	AssessSheetHelper.service.modify(dto);
	return "redirect:/ext/project/gate/listAssess.do";
    }

    /**
     * 수정 화면
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : updateForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/updateAssessForm")
    public void updateAssessForm(String oid, Model model) throws Exception {
	AssessSheet sheet = (AssessSheet) CommonUtil.getObject(oid);
	model.addAttribute("assessSheet", new AssessSheetDTO(sheet));
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
    }

    /**
     * 개정 화면
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : reviseAssessSheetForm
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/reviseAssessSheetForm")
    public void reviseAssessSheetForm(String pjtOid, Model model) throws Exception {
	AssessSheet assSheet = AssessSheetHelper.service.getAssessSheetFromPjtOid(pjtOid);
	String assOid = assSheet.getPersistInfo().getObjectIdentifier().toString();
	AssessSheet sheet = (AssessSheet) CommonUtil.getObject(assOid);
	if (sheet != null) {
	    model.addAttribute("assessSheet", new AssessSheetDTO(sheet));
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(sheet));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(sheet));
	}
    }

    /**
     * 개정 이력화면
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : reviseAssessSheetListForm
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/reviseAssessSheetListForm")
    public void reviseAssessSheetListForm(String pjtOid, Model model) throws Exception {
	// AssessSheet assSheet =
	// AssessSheetHelper.service.getAssessSheetFromPjtOid(oid);
	// String assOid =
	// assSheet.getPersistInfo().getObjectIdentifier().toString();
	// AssessSheet sheet = (AssessSheet) CommonUtil.getObject(assOid);
	// if (sheet != null) {
	// model.addAttribute("assessSheet", new AssessSheetDTO(sheet));
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
	// }
    }

    /**
     * 개정이력을 조회 Grid의 Server paging 방식을 이용한 JSON 데이터
     * 
     * @param ProjectTaskDTO
     * @param page
     * @param formPage
     * @return
     * @throws Exception
     * @메소드명 : findListRevisedProjectAssess
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/findListRevisedProjectAssess")
    @ResponseBody
    public Map<String, Object> findListRevisedProjectAssess(AssessSheetDTO assDto) throws Exception {
	if (assDto.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	List<AssessSheet> list = AssessSheetHelper.service.findRevisedProjectAssess(assDto);
	// SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() +
	// "");

	List<RevisedAssessSheetDTO> dtoList = new ArrayList<RevisedAssessSheetDTO>();
	for (AssessSheet vo : list) {
	    dtoList.add(new RevisedAssessSheetDTO(vo));
	}
	return EjsConverUtil.convertToDto(dtoList);

    }

    /**
     * 개정 업데이트
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : reviseAssessSheetInfos
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/reviseAssessSheetInfos")
    public String reviseAssessSheetInfos(AssessSheetDTO dto) throws Exception {

	List<GateCheckSheetDTO> listGateDto = new ArrayList<GateCheckSheetDTO>();
	AssessSheet sheetRst = null;

	// sheetRst = AssessSheetHelper.service.findLinkedAssessSheet(dto.getOid());

	// Hashtable param = new Hashtable();
	Hashtable tempHash = new Hashtable();
	try {

	    // String sfiles = dto.getIsFileDel(); //(String) param.get("isFileDel");
	    // Kogger.debug(getClass(), "delfiles=====>" + sfiles);
	    // tempHash.put("isFileDel", new String(sfiles));

	    String assOid = dto.getOid();
	    tempHash.put("assOid", new String(assOid));
	    sheetRst = (AssessSheet) CommonUtil.getObject(assOid);

	    String tmpStr = "";// dto.getCategoryCode();//(String)
		               // param.get("categoryCode");
	    // tempHash.put("categoryCode", new String(tmpStr));
	    // tmpStr = dto.getOutputOid();//(String) param.get("outputOid");
	    // tempHash.put("outputOid", new String(tmpStr));
	    // Param Hashtable에 데이터를 저장용 임시 Hashtable에 저장한다.
	    // tempHash.put("isBuyerSummit", "");// new String((String)
	    // param.get("isBuyerSummit")));
	    // tmpStr = dto.getDocumentName();// (String) param.get("documentName");
	    // tempHash.put("documentName", new String(tmpStr));
	    // tmpStr = (String) param.get("buyerCodeStr");
	    // tempHash.put("buyerCode", new String(tmpStr));
	    // tmpStr = (String) param.get("dRCheckPoint");
	    // tmpStr = StringUtil.checkNull(tmpStr);
	    // tempHash.put("dRCheckPoint", new String(tmpStr));
	    // tempHash.put("documentDescription", new String((String)
	    // param.get("documentDescription")));
	    // tmpStr = StringUtil.checkNull(new String((String)
	    // param.get("security")));
	    // tempHash.put("security", new String(tmpStr));

	    /*
	     * Start [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */
	    // tempHash.put("webEditor", new String((String) param.get("webEditor")));
	    // tempHash.put("webEditorText", new String((String)
	    // param.get("webEditorText")));
	    /*
	     * End [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */

	    // 화면상의 수정된 부품정보는 Vector로 처리한다. String은 "0"이 입력되어 있음
	    // Object partObj = param.get("partOid");
	    // if (partObj instanceof Vector) {
	    // tempHash.put("partOid", new Vector((Vector) partObj));
	    // } else {
	    // tempHash.put("partOid", new String((String) partObj));
	    // }

	    // Object projObj = param.get("projectOid");
	    // if (projObj instanceof Vector) {
	    // tempHash.put("projectOid", new Vector((Vector) projObj));
	    // } else {
	    // tempHash.put("projectOid", new String((String) projObj));
	    // }
	    // 첨부파일정보는 추가된것과 삭제해야 할것을 구분하여 처리한다.
	    // 삭제정보를 따로 String형태로 받아 삭제해준다.

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	sheetRst = AssessSheetHelper.service.reviseAssessSheetVersion(tempHash, dto);
	Versioned vs = (Versioned) sheetRst;
	String verVal = vs.getVersionIdentifier().getValue();
	String verVal2 = vs.getVersionInfo().toString();
	Kogger.debug(getClass(), "verVal:" + verVal + ", verVal2:" + verVal2);

	return "redirect:/ext/project/gate/reviseAssessSheetForm.do?pjtOid=" + dto.getPjtOid() + "&isSuccess=Y";
    }

    /**
     * 검색 화면
     * 
     * @throws Exception
     * @메소드명 : list
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/listAssess")
    public void list() throws Exception {

    }

    /**
     * Grid를 위한 JSON 데이터
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/findListAssess")
    @ResponseBody
    public Map<String, Object> findList(AssessSheetDTO assDto) throws Exception {

	List<AssessSheet> list = AssessSheetHelper.service.find(assDto);
	List<AssessSheetDTO> dtoList = new ArrayList<AssessSheetDTO>();
	for (AssessSheet vo : list) {
	    dtoList.add(new AssessSheetDTO(vo));
	}
	return EjsConverUtil.convertToDto(dtoList);
    }

    /**
     * 프로젝트 OID를 조건으로 링크된 평가시트의 하위 평가항목 조회
     * 
     * @param pjtOid
     *            프로젝트 OID
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findListGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/findListGateCheckSheet")
    @ResponseBody
    public Map<String, Object> findListGateCheckSheet(String oid, GateCheckSheetDTO checkSheetDto) throws Exception {

	// NumberCode nc = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
	// developentType);

	List<GateCheckSheet> list = null;
	if (!StringUtil.isEmpty(oid))
	    list = AssessSheetHelper.service.findGateCheckSheet(oid, checkSheetDto);
	else
	    list = null; // GateCheckSheetHelper.service.find(checkSheetDto);

	List<GateCheckSheetDTO> dtoList = new ArrayList<GateCheckSheetDTO>();
	if (list != null) {
	    for (GateCheckSheet vo : list) {
		dtoList.add(new GateCheckSheetDTO(vo));
	    }
	}
	return EjsConverUtil.convertToDto(dtoList);
    }

    /**
     * 버전에 해당하는 평가시트의 하위 평가항목 조회
     * 
     * @param oid
     *            : assOid
     * @param versionNo
     *            : 버전정보
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findViewVerGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/findViewVerGateCheckSheet")
    @ResponseBody
    public Map<String, Object> findViewVerGateCheckSheet(String oid, String versionNo, GateCheckSheetDTO checkSheetDto) throws Exception {

	// NumberCode nc = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
	// developentType);

	List<GateCheckSheet> list = null;
	if (!StringUtil.isEmpty(oid))
	    list = AssessSheetHelper.service.findViewVerGateCheckSheet(oid, versionNo, checkSheetDto);
	else
	    list = null; // GateCheckSheetHelper.service.find(checkSheetDto);

	List<GateCheckSheetDTO> dtoList = new ArrayList<GateCheckSheetDTO>();
	if (list != null) {
	    for (GateCheckSheet vo : list) {
		dtoList.add(new GateCheckSheetDTO(vo));
	    }
	}
	return EjsConverUtil.convertToDto(dtoList);
    }

    /**
     * 프로젝트 평가시트 버전별 상세 목표 게이트 화면 호출 함수
     * 
     * @param oid
     *            , versionNo
     * @param model
     * @throws Exception
     * @메소드명 : viewVersionedAssessForm
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/viewVersionedAssessForm")
    public void viewVersionedAssessForm(String oid, String versionNo, Model model) throws Exception {
	AssessSheet assSheet = (AssessSheet) CommonUtil.getObject(oid);
	if (assSheet != null) {
	    String assVersion = assSheet.getVersionIdentifier().getValue(); // assSheet.getIterationIdentifier().getValue()
	    // pjtVersion = assSheet.getVersionIdentifier().getSeries().getValue();
	    String createDate = DateUtil.getDateString(assSheet.getCreateTimestamp(), "d");
	    model.addAttribute("ASS_OID", oid);
	    model.addAttribute("ASS_VERSION", assVersion);
	    model.addAttribute("CREATE_DATE", createDate);
	    model.addAttribute("ASS_STATE", assSheet.getState().toString());
	    model.addAttribute("ASS_STATE_NAME", assSheet.getLifeCycleState().getDisplay(Locale.KOREA));

	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(assSheet));

	}

	List<GateCheckSheet> listGateCheck = AssessSheetHelper.service.findViewVerGateCheckSheet(oid, versionNo, null);

	int gate1Cnt = 0;
	int gate2Cnt = 0;
	int gate3Cnt = 0;
	int gate4Cnt = 0;
	int gate5Cnt = 0;
	int gate6Cnt = 0;
	int gate7Cnt = 0;
	int gate8Cnt = 0;
	int gate9Cnt = 0;
	int gate10Cnt = 0;
	int gate11Cnt = 0;
	int gate12Cnt = 0;
	int gate13Cnt = 0;
	int gate14Cnt = 0;
	int gate15Cnt = 0;
	int gate16Cnt = 0;
	int gate17Cnt = 0;
	int gate18Cnt = 0;
	int gate19Cnt = 0;
	int gate20Cnt = 0;
	if (listGateCheck != null) {
	    for (int i = 0; i < listGateCheck.size(); i++) {
		GateCheckSheet gateCheck = (GateCheckSheet) listGateCheck.get(i);
		GateAttribute gateAttr = (GateAttribute) gateCheck.getAttr();
		// String gName = gateCheck.getCheckSheetName();
		if (gateAttr.isSelect1())
		    gate1Cnt++;
		if (gateAttr.isSelect2())
		    gate2Cnt++;
		if (gateAttr.isSelect3())
		    gate3Cnt++;
		if (gateAttr.isSelect4())
		    gate4Cnt++;
		if (gateAttr.isSelect5())
		    gate5Cnt++;
		if (gateAttr.isSelect6())
		    gate6Cnt++;
		if (gateAttr.isSelect7())
		    gate7Cnt++;
		if (gateAttr.isSelect8())
		    gate8Cnt++;
		if (gateAttr.isSelect9())
		    gate9Cnt++;
		if (gateAttr.isSelect10())
		    gate10Cnt++;
		if (gateAttr.isSelect11())
		    gate11Cnt++;
		if (gateAttr.isSelect12())
		    gate12Cnt++;
		if (gateAttr.isSelect13())
		    gate13Cnt++;
		if (gateAttr.isSelect14())
		    gate14Cnt++;
		if (gateAttr.isSelect15())
		    gate15Cnt++;
		if (gateAttr.isSelect16())
		    gate16Cnt++;
		if (gateAttr.isSelect17())
		    gate17Cnt++;
		if (gateAttr.isSelect18())
		    gate18Cnt++;
		if (gateAttr.isSelect19())
		    gate19Cnt++;
		if (gateAttr.isSelect20())
		    gate20Cnt++;

	    }
	}
	model.addAttribute("GATE1_CNT", "" + gate1Cnt);
	model.addAttribute("GATE2_CNT", "" + gate2Cnt);
	model.addAttribute("GATE3_CNT", "" + gate3Cnt);
	model.addAttribute("GATE4_CNT", "" + gate4Cnt);
	model.addAttribute("GATE5_CNT", "" + gate5Cnt);
	model.addAttribute("GATE6_CNT", "" + gate6Cnt);
	model.addAttribute("GATE7_CNT", "" + gate7Cnt);
	model.addAttribute("GATE8_CNT", "" + gate8Cnt);
	model.addAttribute("GATE9_CNT", "" + gate9Cnt);
	model.addAttribute("GATE10_CNT", "" + gate10Cnt);
	model.addAttribute("GATE11_CNT", "" + gate11Cnt);
	model.addAttribute("GATE12_CNT", "" + gate12Cnt);
	model.addAttribute("GATE13_CNT", "" + gate13Cnt);
	model.addAttribute("GATE14_CNT", "" + gate14Cnt);
	model.addAttribute("GATE15_CNT", "" + gate15Cnt);
	model.addAttribute("GATE16_CNT", "" + gate16Cnt);
	model.addAttribute("GATE17_CNT", "" + gate17Cnt);
	model.addAttribute("GATE18_CNT", "" + gate18Cnt);
	model.addAttribute("GATE19_CNT", "" + gate19Cnt);
	model.addAttribute("GATE20_CNT", "" + gate20Cnt);

    }

    /**
     * GATE/DR 탭화면
     * 
     * @param oid
     *            , versionNo
     * @param model
     * @throws Exception
     * @메소드명 : ViewProjectGateDRForm
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/viewProjectGateDRForm")
    public void viewProjectGateDRForm(String oid, Model model) throws Exception {
	// List<GateTaskOutputDTO> projectTaskTotalList = ProjectTaskCompHelper.service.getProjectTaskTotalList(oid);
	//
	// List<GateTaskOutputDTO> gateTaskOutputList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(oid);
	//
	// if (gateTaskOutputList != null) {
	// for (int i = 0; i < gateTaskOutputList.size(); i++) {
	// GateTaskOutputDTO gateTaskOutDto = gateTaskOutputList.get(i);
	// String taskOid = gateTaskOutDto.getTaskOid();
	// if (projectTaskTotalList != null) {
	// String genFirstStage = "";
	// for (int j = 0; j < projectTaskTotalList.size(); j++) {
	// GateTaskOutputDTO generalTaskImsi = projectTaskTotalList.get(j);
	// String genTaskOid = generalTaskImsi.getTaskOid();
	// String genTaskName = generalTaskImsi.getOutputTaskName();
	// String genTaskType = generalTaskImsi.getOutputTaskType();
	// String genLevel = generalTaskImsi.getOutputLevel();
	// if ("1".equals(genLevel)) {
	// genFirstStage = genTaskName;
	// }
	// if (taskOid != null && taskOid.equals(genTaskOid)) {
	// gateTaskOutDto.setTaskStageName(genFirstStage);
	// }
	// }
	// }
	//
	// Hashtable haTotalResult = ProjectTaskCompHelper.service.getGateAssessResultInfoList("e3ps.project.E3PSTask:" + taskOid,
	// false);
	// gateTaskOutDto.setResultOutputVal((String) haTotalResult.get("resultOutputVal"));
	// gateTaskOutDto.setResultAssVal((String) haTotalResult.get("resultAssVal"));
	// gateTaskOutDto.setResultDqmVal((String) haTotalResult.get("resultDqmVal"));
	// gateTaskOutDto.setResultTotalVal((String) haTotalResult.get("resultTotalVal"));
	// gateTaskOutDto.setResultTotalStr((String) haTotalResult.get("resultTotalStr"));
	//
	// String gateRsltOid = (String) haTotalResult.get("gateResultOid");
	// int recoveryPlanCnt = 0;
	// if (!StringUtil.isEmpty(gateRsltOid)) {
	// gateTaskOutDto.setResultOid("ext.ket.project.gate.entity.GateAssessResult:" + gateRsltOid);
	//
	// GateAssessResult gateAssRsltObj = (GateAssessResult) CommonUtil
	// .getObject("ext.ket.project.gate.entity.GateAssessResult:" + gateRsltOid);
	// String resultTemplateUrl = "";
	//
	// QueryResult qrRs = ContentHelper.service.getContentsByRole((ContentHolder) gateAssRsltObj, ContentRoleType.SECONDARY);
	// while (qrRs.hasMoreElements()) {
	// // ContentHolder holder = (ContentHolder) gateAssRsltObj;
	// // ContentItem fileContent = (ContentItem) qrRs.nextElement();
	// // ContentInfo info3 = ContentUtil.getContentInfo(holder, fileContent);
	// // if (info3 != null && info3.getDownURL() != null)
	// // resultTemplateUrl = info3.getDownURL().toString();
	// recoveryPlanCnt++;
	// }
	// }
	// gateTaskOutDto.setResultAttatchCnt("" + recoveryPlanCnt);
	//
	// }
	// }
	// model.addAttribute("GATE_RESULT_LIST", gateTaskOutputList);

    }

    /**
     * 프로젝트 평가시트 버전별 첨부파일목록 화면 호출 함수
     * 
     * @param oid
     *            , versionNo
     * @param model
     * @throws Exception
     * @메소드명 : viewAttatchedAssessForm
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/viewAttatchedAssessForm")
    public void viewAttatchedAssessForm(String oid, String versionNo, Model model) throws Exception {

	AssessSheet assSheet = (AssessSheet) CommonUtil.getObject(oid);
	if (assSheet != null) {
	    String assVersion = assSheet.getVersionIdentifier().getValue(); // assSheet.getIterationIdentifier().getValue()
	    // pjtVersion = assSheet.getVersionIdentifier().getSeries().getValue();
	    String createDate = DateUtil.getDateString(assSheet.getCreateTimestamp(), "d");
	    model.addAttribute("ASS_OID", oid);
	    model.addAttribute("ASS_VERSION", assVersion);
	    model.addAttribute("CREATE_DATE", createDate);
	    model.addAttribute("ASS_STATE", assSheet.getState().toString());
	    model.addAttribute("ASS_STATE_NAME", assSheet.getLifeCycleState().getDisplay(Locale.KOREA));

	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(assSheet));

	}

    }

    /**
     * Grid의 Server paging 방식을 이용한 JSON 데이터
     * 
     * @param ProjectTaskDTO
     * @param page
     * @param formPage
     * @return
     * @throws Exception
     * @메소드명 : findPagingList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/findPagingListAssess")
    @ResponseBody
    public Map<String, Object> findPagingList(AssessSheetDTO assDto) throws Exception {
	if (assDto.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = AssessSheetHelper.service.findPaging(assDto);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	QueryResult queryResult = pageControl.getResult();
	List<AssessSheet> list = new ArrayList<AssessSheet>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    list.add((AssessSheet) objArr[0]);
	}
	List<AssessSheetDTO> dtoList = new ArrayList<AssessSheetDTO>();
	for (AssessSheet vo : list) {
	    dtoList.add(new AssessSheetDTO(vo));
	}
	return EjsConverUtil.convertToDto(dtoList, pageControl);

    }

    /**
     * 프로젝트 목표 게이트 화면 호출 함수
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : updateForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/updateProjectAssessForm")
    public void updateProjectAssessForm(String pjtOid, Model model) throws Exception {

	HashMap hMap = AssessSheetHelper.service.getProjectAssessHeadInfo(pjtOid, null);

	List<GateCheckSheet> listGateCheck = AssessSheetHelper.service.findGateCheckSheet(pjtOid, null);

	int gate1Cnt = 0;
	int gate2Cnt = 0;
	int gate3Cnt = 0;
	int gate4Cnt = 0;
	int gate5Cnt = 0;
	int gate6Cnt = 0;
	int gate7Cnt = 0;
	int gate8Cnt = 0;
	int gate9Cnt = 0;
	int gate10Cnt = 0;
	int gate11Cnt = 0;
	int gate12Cnt = 0;
	int gate13Cnt = 0;
	int gate14Cnt = 0;
	int gate15Cnt = 0;
	int gate16Cnt = 0;
	int gate17Cnt = 0;
	int gate18Cnt = 0;
	int gate19Cnt = 0;
	int gate20Cnt = 0;
	if (listGateCheck != null) {
	    for (int i = 0; i < listGateCheck.size(); i++) {
		GateCheckSheet gateCheck = (GateCheckSheet) listGateCheck.get(i);
		GateAttribute gateAttr = (GateAttribute) gateCheck.getAttr();
		// String gName = gateCheck.getCheckSheetName();
		if (gateAttr.isSelect1())
		    gate1Cnt++;
		if (gateAttr.isSelect2())
		    gate2Cnt++;
		if (gateAttr.isSelect3())
		    gate3Cnt++;
		if (gateAttr.isSelect4())
		    gate4Cnt++;
		if (gateAttr.isSelect5())
		    gate5Cnt++;
		if (gateAttr.isSelect6())
		    gate6Cnt++;
		if (gateAttr.isSelect7())
		    gate7Cnt++;
		if (gateAttr.isSelect8())
		    gate8Cnt++;
		if (gateAttr.isSelect9())
		    gate9Cnt++;
		if (gateAttr.isSelect10())
		    gate10Cnt++;
		if (gateAttr.isSelect11())
		    gate11Cnt++;
		if (gateAttr.isSelect12())
		    gate12Cnt++;
		if (gateAttr.isSelect13())
		    gate13Cnt++;
		if (gateAttr.isSelect14())
		    gate14Cnt++;
		if (gateAttr.isSelect15())
		    gate15Cnt++;
		if (gateAttr.isSelect16())
		    gate16Cnt++;
		if (gateAttr.isSelect17())
		    gate17Cnt++;
		if (gateAttr.isSelect18())
		    gate18Cnt++;
		if (gateAttr.isSelect19())
		    gate19Cnt++;
		if (gateAttr.isSelect20())
		    gate20Cnt++;

	    }
	}

	if (hMap != null) {

	    model.addAttribute("ASS_OID", hMap.get("ASS_OID"));
	    model.addAttribute("PJT_VERSION", hMap.get("PJT_VERSION"));
	    model.addAttribute("CREATE_DATE", hMap.get("CREATE_DATE"));
	    model.addAttribute("PJT_STATE", hMap.get("PJT_STATE"));
	    model.addAttribute("PJT_STATE_NAME", hMap.get("PJT_STATE_NAME"));
	    model.addAttribute("QUALITY_CNT", hMap.get("QUALITY_CNT"));
	    model.addAttribute("COST_CNT", hMap.get("COST_CNT"));
	    model.addAttribute("DELIVERY_CNT", hMap.get("DELIVERY_CNT"));
	    model.addAttribute("ETC_CNT", hMap.get("ETC_CNT"));
	    model.addAttribute("VEC_CNT", hMap.get("VEC_CNT"));

	    model.addAttribute("GATE1_CNT", "" + gate1Cnt);
	    model.addAttribute("GATE2_CNT", "" + gate2Cnt);
	    model.addAttribute("GATE3_CNT", "" + gate3Cnt);
	    model.addAttribute("GATE4_CNT", "" + gate4Cnt);
	    model.addAttribute("GATE5_CNT", "" + gate5Cnt);
	    model.addAttribute("GATE6_CNT", "" + gate6Cnt);
	    model.addAttribute("GATE7_CNT", "" + gate7Cnt);
	    model.addAttribute("GATE8_CNT", "" + gate8Cnt);
	    model.addAttribute("GATE9_CNT", "" + gate9Cnt);
	    model.addAttribute("GATE10_CNT", "" + gate10Cnt);
	    model.addAttribute("GATE11_CNT", "" + gate11Cnt);
	    model.addAttribute("GATE12_CNT", "" + gate12Cnt);
	    model.addAttribute("GATE13_CNT", "" + gate13Cnt);
	    model.addAttribute("GATE14_CNT", "" + gate14Cnt);
	    model.addAttribute("GATE15_CNT", "" + gate15Cnt);
	    model.addAttribute("GATE16_CNT", "" + gate16Cnt);
	    model.addAttribute("GATE17_CNT", "" + gate17Cnt);
	    model.addAttribute("GATE18_CNT", "" + gate18Cnt);
	    model.addAttribute("GATE19_CNT", "" + gate19Cnt);
	    model.addAttribute("GATE20_CNT", "" + gate20Cnt);
	}
    }

    /**
     * 프로젝트에 평가시트를 등록하기 위해 Project Oid, Assess Oid 값을 받아서 평가시트를 등록하는 함수
     * 
     * @param pjtOid
     *            , tmplAssessOid , model
     * @param model
     * @throws Exception
     * @메소드명 : updateForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/registTemplateAssessForm")
    public String registTemplateAssessForm(String pjtOid, String tmplAssessOid, Model model) throws Exception {
	if (pjtOid != null && pjtOid.indexOf(":") <= 0)
	    pjtOid = "e3ps.project.E3PSProject:" + pjtOid;

	E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtOid);
	E3PSProjectData projectData = new E3PSProjectData(project);

	if (tmplAssessOid != null && tmplAssessOid.indexOf(":") <= 0)
	    tmplAssessOid = "ext.ket.project.gate.entity.TemplateAssessSheet:" + tmplAssessOid;
	TemplateAssessSheet sheet = (TemplateAssessSheet) CommonUtil.getObject(tmplAssessOid);

	List<GateCheckSheet> list = AssessSheetHelper.service.registTemplateAssessSheet(project, sheet);

	List<GateCheckSheetDTO> dtoList = new ArrayList<GateCheckSheetDTO>();
	for (GateCheckSheet vo : list) {
	    dtoList.add(new GateCheckSheetDTO(vo));
	}
	return "redirect:/ext/project/gate/updateProjectAssessForm.do?pjtOid=" + pjtOid;
	// return EjsConverUtil.convertToDto(dtoList);
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
    }

    /**
     * 평가시트 및 체크시트 항목 변경사항 저장
     * 
     * @param gridData
     *            : 그리드에 입력된 평가항목의 정보를 XML로 받는다
     * @param dto
     *            Assess평가시트(TemplateAssessSheetDTO) 각 항목의 변경정보를 받는다
     * @return
     * @throws Exception
     * @메소드명 : updateGateCheckSheetList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/updateGateCheckSheetList")
    @ResponseBody
    public Map<String, Object> updateGateCheckSheetList(String gridData, AssessSheetDTO dto) throws Exception {

	List<GateCheckSheetDTO> listGateDto = new ArrayList<GateCheckSheetDTO>();
	AssessSheet sheetRst = null;

	sheetRst = AssessSheetHelper.service.findLinkedAssessSheet(dto.getPjtOid());

	if (gridData != null) {
	    listGateDto = getJSONToGateCheckList(gridData);

	}

	String delOidsStr = dto.getDelOids();
	String delOidsAr[] = null;
	if (delOidsStr != null && delOidsStr.length() > 0) {
	    delOidsAr = delOidsStr.split(",");
	}
	if (delOidsAr != null) {
	    for (int d = 0; d < delOidsAr.length; d++) {
		GateCheckSheet gateCheckSheet = (GateCheckSheet) CommonUtil.getObject(delOidsAr[d]);
		GateCheckSheetHelper.service.deleteGateCheckSheet(gateCheckSheet);
	    }
	}

	int ordNo = 1;
	if (listGateDto != null && listGateDto.size() > 0) {
	    for (GateCheckSheetDTO gateCheckSheetDTO : listGateDto) {

		if (StringUtil.isEmpty(gateCheckSheetDTO.getOid())) {
		    GateCheckSheetHelper.service.saveProjectGate(sheetRst.getPersistInfo().getObjectIdentifier().toString(), ordNo,
			    gateCheckSheetDTO);
		} else {
		    GateCheckSheetHelper.service.updateProjectGate(sheetRst.getPersistInfo().getObjectIdentifier().toString(), ordNo,
			    gateCheckSheetDTO);
		}
		ordNo++;
	    }
	}

	return EjsConverUtil.sendResult("0");
    }

    /**
     * 평가항목삭제 : 평가항목정보(TemplateGateCheckSheet)과 평가항목과의 링크정보(TmplAssShtTmplGateChkLink)까지 삭제 평가시트 수정화면(updateTemplateAssessSheetForm.jsp)에서
     * 하나를 선택후 삭제버튼을 눌러 선택된 평가시트를 삭제한다
     * 
     * @param oid
     * @param assOid
     * @throws Exception
     * @메소드명 : deleteGateCheck
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/deleteGateCheck")
    public String deleteGateCheck(String oid, String pjtOid) throws Exception {
	GateCheckSheet gateCheckSheet = (GateCheckSheet) CommonUtil.getObject(oid);
	GateCheckSheetDTO dtoGate = new GateCheckSheetDTO(gateCheckSheet);
	GateCheckSheetHelper.service.delete(dtoGate);
	return "redirect:/ext/project/gate/updateProjectAssessForm.do?pjtOid=" + pjtOid;
    }

    /**
     * GRID에 추가된 평가항목(GateCheckSheet)의 JSON String정보를 받아서 평가항목 리스트 객체(List<GateCheckSheetDTO>) 형태로 변환하는 함수
     * 
     * @param jsonInfoStr
     *            : 평가항목(GateCheckSheet)의 JSON String정보
     * @return
     * @throws Exception
     * @메소드명 : getJSONToGateCheckList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public List<GateCheckSheetDTO> getJSONToGateCheckList(String jsonInfoStr) throws Exception {

	List<GateCheckSheetDTO> listTmplGateDto = new ArrayList<GateCheckSheetDTO>();
	// JSONObject json = (JSONObject)JSONParser.parse(gridData);
	// JSONValue jsonBody = json.get("Body");
	JSONObject jsonObject = new JSONObject(jsonInfoStr);
	// JSONObject jsonObject = new JSONObject(IOUtils.toString(in));
	JSONArray jsonArray = jsonObject.getJSONArray("Body");

	if (jsonArray != null && jsonArray.length() > 0) {

	    for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject listJson = jsonArray.getJSONObject(i);
		JSONArray jsonItemArray = listJson.getJSONArray("Items");
		if (jsonItemArray != null && jsonItemArray.length() > 0) {
		    for (int j = 0; j < jsonItemArray.length(); j++) {
			GateCheckSheetDTO gateCheckDTO = new GateCheckSheetDTO();
			JSONObject listItemJson = jsonItemArray.getJSONObject(j);
			if (!listItemJson.isNull("oid"))
			    gateCheckDTO.setOid(getExtStrChange((String) listItemJson.get("oid")));
			if (!listItemJson.isNull("targetType"))
			    gateCheckDTO.setTargetType(getExtStrChange((String) listItemJson.get("targetType")));
			if (!listItemJson.isNull("checkSheetName"))
			    gateCheckDTO.setCheckSheetName(getExtStrChange((String) listItemJson.get("checkSheetName")));
			if (!listItemJson.isNull("achieveBase"))
			    gateCheckDTO.setAchieveBase(getExtStrChange((String) listItemJson.get("achieveBase")));
			if (!listItemJson.isNull("unit"))
			    gateCheckDTO.setUnit(getExtStrChange((String) listItemJson.get("unit")));
			if (!listItemJson.isNull("criteria"))
			    gateCheckDTO.setCriteria(getExtStrChange((String) listItemJson.get("criteria")));
			if (!listItemJson.isNull("select1"))
			    gateCheckDTO.setSelect1(getExtStrChange((String) listItemJson.get("select1")));
			if (!listItemJson.isNull("select2"))
			    gateCheckDTO.setSelect2(getExtStrChange((String) listItemJson.get("select2")));
			if (!listItemJson.isNull("select3"))
			    gateCheckDTO.setSelect3(getExtStrChange((String) listItemJson.get("select3")));
			if (!listItemJson.isNull("select4"))
			    gateCheckDTO.setSelect4(getExtStrChange((String) listItemJson.get("select4")));
			if (!listItemJson.isNull("select5"))
			    gateCheckDTO.setSelect5(getExtStrChange((String) listItemJson.get("select5")));
			if (!listItemJson.isNull("select6"))
			    gateCheckDTO.setSelect6(getExtStrChange((String) listItemJson.get("select6")));
			if (!listItemJson.isNull("select7"))
			    gateCheckDTO.setSelect7(getExtStrChange((String) listItemJson.get("select7")));
			if (!listItemJson.isNull("select8"))
			    gateCheckDTO.setSelect8(getExtStrChange((String) listItemJson.get("select8")));
			if (!listItemJson.isNull("select9"))
			    gateCheckDTO.setSelect9(getExtStrChange((String) listItemJson.get("select9")));
			if (!listItemJson.isNull("select10"))
			    gateCheckDTO.setSelect10(getExtStrChange((String) listItemJson.get("select10")));
			if (!listItemJson.isNull("select11"))
			    gateCheckDTO.setSelect11(getExtStrChange((String) listItemJson.get("select11")));
			if (!listItemJson.isNull("select12"))
			    gateCheckDTO.setSelect12(getExtStrChange((String) listItemJson.get("select12")));
			if (!listItemJson.isNull("select13"))
			    gateCheckDTO.setSelect13(getExtStrChange((String) listItemJson.get("select13")));
			if (!listItemJson.isNull("select14"))
			    gateCheckDTO.setSelect14(getExtStrChange((String) listItemJson.get("select14")));
			if (!listItemJson.isNull("select15"))
			    gateCheckDTO.setSelect15(getExtStrChange((String) listItemJson.get("select15")));
			if (!listItemJson.isNull("select16"))
			    gateCheckDTO.setSelect16(getExtStrChange((String) listItemJson.get("select16")));
			if (!listItemJson.isNull("select17"))
			    gateCheckDTO.setSelect17(getExtStrChange((String) listItemJson.get("select17")));
			if (!listItemJson.isNull("select18"))
			    gateCheckDTO.setSelect18(getExtStrChange((String) listItemJson.get("select18")));
			if (!listItemJson.isNull("select19"))
			    gateCheckDTO.setSelect19(getExtStrChange((String) listItemJson.get("select19")));
			if (!listItemJson.isNull("select20"))
			    gateCheckDTO.setSelect20(getExtStrChange((String) listItemJson.get("select20")));

			if (!listItemJson.isNull("target1") && "1".equals(listItemJson.get("select1")))
			    gateCheckDTO.setTarget1(getExtStrChange((String) listItemJson.get("target1")));
			if (!listItemJson.isNull("target2") && "1".equals(listItemJson.get("select2")))
			    gateCheckDTO.setTarget2(getExtStrChange((String) listItemJson.get("target2")));
			if (!listItemJson.isNull("target3") && "1".equals(listItemJson.get("select3")))
			    gateCheckDTO.setTarget3(getExtStrChange((String) listItemJson.get("target3")));
			if (!listItemJson.isNull("target4") && "1".equals(listItemJson.get("select4")))
			    gateCheckDTO.setTarget4(getExtStrChange((String) listItemJson.get("target4")));
			if (!listItemJson.isNull("target5") && "1".equals(listItemJson.get("select5")))
			    gateCheckDTO.setTarget5(getExtStrChange((String) listItemJson.get("target5")));
			if (!listItemJson.isNull("target6") && "1".equals(listItemJson.get("select6")))
			    gateCheckDTO.setTarget6(getExtStrChange((String) listItemJson.get("target6")));
			if (!listItemJson.isNull("target7") && "1".equals(listItemJson.get("select7")))
			    gateCheckDTO.setTarget7(getExtStrChange((String) listItemJson.get("target7")));
			if (!listItemJson.isNull("target8") && "1".equals(listItemJson.get("select8")))
			    gateCheckDTO.setTarget8(getExtStrChange((String) listItemJson.get("target8")));
			if (!listItemJson.isNull("target9") && "1".equals(listItemJson.get("select9")))
			    gateCheckDTO.setTarget9(getExtStrChange((String) listItemJson.get("target9")));
			if (!listItemJson.isNull("target10") && "1".equals(listItemJson.get("select10")))
			    gateCheckDTO.setTarget10(getExtStrChange((String) listItemJson.get("target10")));
			if (!listItemJson.isNull("target11") && "1".equals(listItemJson.get("select11")))
			    gateCheckDTO.setTarget11(getExtStrChange((String) listItemJson.get("target11")));
			if (!listItemJson.isNull("target12") && "1".equals(listItemJson.get("select12")))
			    gateCheckDTO.setTarget12(getExtStrChange((String) listItemJson.get("target12")));
			if (!listItemJson.isNull("target13") && "1".equals(listItemJson.get("select13")))
			    gateCheckDTO.setTarget13(getExtStrChange((String) listItemJson.get("target13")));
			if (!listItemJson.isNull("target14") && "1".equals(listItemJson.get("select14")))
			    gateCheckDTO.setTarget14(getExtStrChange((String) listItemJson.get("target14")));
			if (!listItemJson.isNull("target15") && "1".equals(listItemJson.get("select15")))
			    gateCheckDTO.setTarget15(getExtStrChange((String) listItemJson.get("target15")));
			if (!listItemJson.isNull("target16") && "1".equals(listItemJson.get("select16")))
			    gateCheckDTO.setTarget16(getExtStrChange((String) listItemJson.get("target16")));
			if (!listItemJson.isNull("target17") && "1".equals(listItemJson.get("select17")))
			    gateCheckDTO.setTarget17(getExtStrChange((String) listItemJson.get("target17")));
			if (!listItemJson.isNull("target18") && "1".equals(listItemJson.get("select18")))
			    gateCheckDTO.setTarget18(getExtStrChange((String) listItemJson.get("target18")));
			if (!listItemJson.isNull("target19") && "1".equals(listItemJson.get("select19")))
			    gateCheckDTO.setTarget19(getExtStrChange((String) listItemJson.get("target19")));
			if (!listItemJson.isNull("target20") && "1".equals(listItemJson.get("select20")))
			    gateCheckDTO.setTarget20(getExtStrChange((String) listItemJson.get("target20")));
			listTmplGateDto.add(gateCheckDTO);

		    }
		}
	    }
	}
	return listTmplGateDto;
    }

    public String getExtStrChange(String tgtStr) {
	String result = "";
	if (!StringUtil.isEmpty(tgtStr)) {
	    result = ReplaceString(tgtStr, "&amp;", "&");
	    result = ReplaceString(result, "&quot;", "\"");

	    result = ReplaceString(result, "&apos;", "'");
	    result = ReplaceString(result, "&lt;", "<");
	    result = ReplaceString(result, "&gt;", ">");
	    result = ReplaceString(result, "<br>", "\r");
	    result = ReplaceString(result, "<p>", "\n");
	}
	return result;
    }

    public String ReplaceString(String Expression, String Pattern, String Rep) {
	if (Expression == null || Expression.equals(""))
	    return "";

	int s = 0;
	int e = 0;
	StringBuffer result = new StringBuffer();

	while ((e = Expression.indexOf(Pattern, s)) >= 0) {
	    result.append(Expression.substring(s, e));
	    result.append(Rep);
	    s = e + Pattern.length();
	}
	result.append(Expression.substring(s));
	return result.toString();
    }

}
