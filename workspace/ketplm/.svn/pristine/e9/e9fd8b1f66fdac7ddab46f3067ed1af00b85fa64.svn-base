package ext.ket.project.gate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.project.gate.entity.TemplateAssessSheetDTO;
import ext.ket.project.gate.entity.TemplateGateCheckSheet;
import ext.ket.project.gate.entity.TemplateGateCheckSheetDTO;
import ext.ket.project.gate.service.TemplateAssessSheetHelper;
import ext.ket.project.gate.service.TemplateGateCheckSheetHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

@Controller
@RequestMapping("/project/gate/*")
public class TemplateAssessSheetController {

    /**
     * 평가시트 저장(생성) : 평가시트정보(TemplateAssessSheetDTO)를 받아서 신규 평가시트 생성 createGateCheckSheetList 함수로 대체됨
     * 
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : create
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTemplateAssess")
    public String create(TemplateAssessSheetDTO dto) throws Exception {
	TemplateAssessSheetHelper.service.save(dto);

	return "redirect:/ext/project/gate/listTemplateAssess.do";
    }

    /**
     * 평가항목 저장(생성) : Assess평가시트 생성 후 그리드에 추가된 평가항목을 생성. 평가항목 저장 화면에서 저장버튼 클릭시
     * 
     * @param gridData
     *            : 그리드에 입력된 평가항목의 정보를 XML로 받는다
     * @param dto
     *            Assess평가시트(TemplateAssessSheetDTO) 각 항목의 입력정보를 받는다
     * @return
     * @throws Exception
     * @메소드명 : createTemplateGateCheckSheetList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTemplateGateCheckSheetList")
    @ResponseBody
    public Map<String, Object> createTemplateGateCheckSheetList(String gridData, TemplateAssessSheetDTO dto) throws Exception {

	List<TemplateGateCheckSheetDTO> listTmplGateDto = new ArrayList<TemplateGateCheckSheetDTO>();
	TemplateAssessSheet sheetRst = null;
	if (gridData != null) {
	    // Assess평가시트 생성
	    sheetRst = TemplateAssessSheetHelper.service.save(dto);
	    SessionUtil.setAttribute("assessSheetOid", CommonUtil.getOIDString(sheetRst));

	    listTmplGateDto = getJSONToGateCheckList(gridData);
	}

	int ordNo = 1;
	if (listTmplGateDto != null && listTmplGateDto.size() > 0) {
	    for (TemplateGateCheckSheetDTO templateGateCheckSheetDTO : listTmplGateDto) {

		TemplateGateCheckSheetHelper.service.saveProjectGate(sheetRst.getPersistInfo().getObjectIdentifier().toString(), ordNo,
		        templateGateCheckSheetDTO);
		ordNo++;
	    }
	}

	return EjsConverUtil.sendResult("0");

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
     * @메소드명 : updateTemplateGateCheckSheetList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTemplateGateCheckSheetList")
    @ResponseBody
    public Map<String, Object> updateTemplateGateCheckSheetList(String gridData, TemplateAssessSheetDTO dto) throws Exception {

	List<TemplateGateCheckSheetDTO> listTmplGateDto = new ArrayList<TemplateGateCheckSheetDTO>();
	TemplateAssessSheet sheetRst = null;

	// Assess평가시트 수정
	sheetRst = TemplateAssessSheetHelper.service.modify(dto);
	if (gridData != null) {
	    listTmplGateDto = getJSONToGateCheckList(gridData);

	}

	String delOidsStr = dto.getDelOids();
	String delOidsAr[] = null;
	if (delOidsStr != null && delOidsStr.length() > 0) {
	    delOidsAr = delOidsStr.split(",");
	}
	if (delOidsAr != null) {
	    for (int d = 0; d < delOidsAr.length; d++) {
		TemplateGateCheckSheet tmplGateCheckSheet = (TemplateGateCheckSheet) CommonUtil.getObject(delOidsAr[d]);
		TemplateGateCheckSheetHelper.service.deleteGateCheckSheet(tmplGateCheckSheet);
	    }
	}

	int ordNo = 1;
	if (listTmplGateDto != null && listTmplGateDto.size() > 0) {
	    for (TemplateGateCheckSheetDTO templateGateCheckSheetDTO : listTmplGateDto) {
		Kogger.debug(getClass(), "dAss getCheckSheetName:" + templateGateCheckSheetDTO.getCheckSheetName());

		if (StringUtil.isEmpty(templateGateCheckSheetDTO.getOid())) {
		    TemplateGateCheckSheetHelper.service.saveProjectGate(sheetRst.getPersistInfo().getObjectIdentifier().toString(), ordNo,
			    templateGateCheckSheetDTO);
		} else {
		    TemplateGateCheckSheetHelper.service.updateProjectGate(sheetRst.getPersistInfo().getObjectIdentifier().toString(),
			    ordNo, templateGateCheckSheetDTO);
		}
		ordNo++;
	    }
	}

	return EjsConverUtil.sendResult("0");
    }

    /**
     * 표준평가템플릿에서 활성/비활성 변화
     * 
     * @param gridData
     *            : 그리드에 입력된 평가항목의 정보를 XML로 받는다
     * @param dto
     *            Assess평가시트(TemplateAssessSheetDTO) 각 항목의 변경정보를 받는다
     * @return
     * @throws Exception
     * @메소드명 : updateTemplateAssessActive
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTemplateAssessActive")
    @ResponseBody
    public Map<String, Object> updateTemplateAssessActive(String activeState, TemplateAssessSheetDTO dto) throws Exception {

	// Assess평가시트 수정
	TemplateAssessSheet modify = (TemplateAssessSheet) CommonUtil.getObject(dto.getOid());
	modify.setActive(activeState);
	modify = (TemplateAssessSheet) PersistenceHelper.manager.modify(modify);

	return EjsConverUtil.sendResult("0");
    }

    /**
     * GRID에 추가된 평가항목(TemplateGateCheckSheet)의 JSON String정보를 받아서 평가항목 리스트 객체(List<TemplateGateCheckSheetDTO>) 형태로 변환하는 함수
     * 
     * @param jsonInfoStr
     *            : 평가항목(TemplateGateCheckSheet)의 JSON String정보
     * @return
     * @throws Exception
     * @메소드명 : getJSONToGateCheckList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TemplateGateCheckSheetDTO> getJSONToGateCheckList(String jsonInfoStr) throws Exception {

	List<TemplateGateCheckSheetDTO> listTmplGateDto = new ArrayList<TemplateGateCheckSheetDTO>();
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
			TemplateGateCheckSheetDTO gateCheckDTO = new TemplateGateCheckSheetDTO();
			JSONObject listItemJson = jsonItemArray.getJSONObject(j);
			if (!listItemJson.isNull("oid"))
			    gateCheckDTO.setOid((String) listItemJson.get("oid"));
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

    /**
     * 평가 항목 단순 저장(TemplateGateCheckSheetDTO 정보를 받아서 평가항목 객체 생성처리) 현재 평가항목 임시생성기능에서 처리 - 삭제대상
     * 
     * @param TemplateGateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : createTemplateGateCheck
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTemplateGateCheck")
    public String createTemplateGateCheck(TemplateGateCheckSheetDTO dto) throws Exception {
	TemplateGateCheckSheetHelper.service.save(dto);
	return "redirect:/ext/project/gate/listTemplateAssess.do";
    }

    /**
     * 평가시트 저장 팝업화면 호출시 createTemplateAssessForm.jsp 페이지로 이동하기위한 함수
     * 
     * @param TemplateAssessSheetDTO
     * @throws Exception
     * @메소드명 : createTemplateAssessForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTemplateAssessForm")
    public void createTemplateAssessForm(TemplateAssessSheetDTO dto) throws Exception {
    }

    /**
     * 평가항목 저장 팝업화면 호출시 createTemplateGateCheckForm.jsp 페이지로 이동하기위한 함수
     * 
     * @param TemplateGateCheckSheetDTO
     * @throws Exception
     * @메소드명 : createTemplateGateCheckForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTemplateGateCheckForm")
    public void createTemplateGateCheckForm(TemplateGateCheckSheetDTO dto) throws Exception {
    }

    /**
     * 평가시트삭제 : 하위에 평가항목정보(TemplateGateCheckSheet)가 있으면 평가항목과의 링크정보(TmplAssShtTmplGateChkLink)까지 삭제후 평가시트(TemplateAssessSheet)을 삭제한다 평가시트
     * 리스트(listTemplateAssess.jsp)에서 하나를 선택후 삭제버튼을 눌러 선택된 평가시트를 삭제한다
     * 
     * @param oid
     *            평가시트 ASSESS OID
     * @param TemplateAssessSheetDTO
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/deleteTemplateAssess")
    public String delete(String oid, TemplateAssessSheetDTO dto) throws Exception {
	TemplateAssessSheet tmplAssSheet = (TemplateAssessSheet) CommonUtil.getObject(oid);
	dto = new TemplateAssessSheetDTO(tmplAssSheet);
	TemplateAssessSheetHelper.service.delete(dto);
	return "redirect:/ext/project/gate/listTemplateAssess.do?isSearch=Y";
	// return
	// "redirect:/ext/project/gate/findPagingListTemplateAssess.do?isSearch=Y";
    }

    /**
     * 평가항목삭제 : 평가항목정보(TemplateGateCheckSheet)과 평가항목과의 링크정보(TmplAssShtTmplGateChkLink)까지 삭제 평가시트 수정화면(updateTemplateAssessSheetForm.jsp)에서
     * 하나를 선택후 삭제버튼을 눌러 선택된 평가시트를 삭제한다
     * 
     * @param oid
     *            평가항목 GateCheckSheet OID
     * @param TemplateAssessSheetDTO
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/deleteTemplateGateCheck")
    public String deleteTemplateGateCheck(String oid, String assOid) throws Exception {
	TemplateGateCheckSheet tmplGateCheckSheet = (TemplateGateCheckSheet) CommonUtil.getObject(oid);
	TemplateGateCheckSheetDTO dtoGate = new TemplateGateCheckSheetDTO(tmplGateCheckSheet);
	TemplateGateCheckSheetHelper.service.delete(dtoGate);
	return "redirect:/ext/project/gate/updateTemplateAssessForm.do?oid=" + assOid;
    }

    /**
     * 수정된 내용 저장
     * 
     * @param oid
     *            , TemplateAssessSheetDTO
     * @throws Exception
     * @메소드명 : update
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTemplateAssess")
    public String update(String oid, TemplateAssessSheetDTO dto) throws Exception {
	Kogger.debug(getClass(), "oid:" + oid);
	TemplateAssessSheetHelper.service.modify(dto);
	return "redirect:/ext/project/gate/listTemplateAssess.do?isSearch=Y";
	// return
	// "redirect:/ext/project/gate/findPagingListTemplateAssess.do?isSearch=Y";
    }

    /**
     * 평가항목 수정 팝업화면 호출시 updateTemplateAssessForm.jsp 페이지로 이동하기위한 함수
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
     * 
     */
    @RequestMapping("/updateTemplateAssessForm")
    public void updateForm(String oid, Model model) throws Exception {
	Kogger.debug(getClass(), "--updateForm oid:" + oid);
	TemplateAssessSheet sheet = (TemplateAssessSheet) CommonUtil.getObject(oid);

	model.addAttribute("templateAssessSheet", new TemplateAssessSheetDTO(sheet));
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
    }

    /**
     * 평가항목 보기 팝업화면 호출시 viewTemplateAssessForm.jsp 페이지로 이동하기위한 함수
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : viewTemplateAssessForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewTemplateAssessForm")
    public void viewTemplateAssessForm(String oid, Model model) throws Exception {
	TemplateAssessSheet sheet = (TemplateAssessSheet) CommonUtil.getObject(oid);

	List<TemplateGateCheckSheet> tmplGateCheckList = TemplateGateCheckSheetHelper.service.findGateCheckSheet(oid, null);
	model.addAttribute("templateAssessSheet", new TemplateAssessSheetDTO(sheet));
	model.addAttribute("templateGateCheckList", tmplGateCheckList);
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
    }

    /**
     * 평가항목 보기 팝업화면 호출시 viewTemplateAssessForm.jsp 페이지로 이동하기위한 함수
     * 
     * @param oid
     *            , model
     * @param model
     * @throws Exception
     * @메소드명 : viewTemplateAssessDTOForm
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewTemplateAssessDTOForm")
    public String viewTemplateAssessDTOForm(TemplateAssessSheetDTO dto, Model model) throws Exception {

	TemplateAssessSheet sheet = TemplateAssessSheetHelper.service.findTemplateAssess(dto);

	return "redirect:/ext/project/gate/viewTemplateAssessForm.do?oid=" + CommonUtil.getOIDString(sheet);
    }

    /**
     * 평가항목 수정 팝업화면 호출시 updateTemplateGateCheckForm.jsp 페이지로 이동하기위한 함수
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
     * 
     */
    @RequestMapping("/updateTemplateGateCheckForm")
    public void updateTemplateGateCheckForm(String oid, Model model) throws Exception {
	TemplateGateCheckSheet sheet = (TemplateGateCheckSheet) CommonUtil.getObject(oid);
	model.addAttribute("templateGateCheckSheet", new TemplateGateCheckSheetDTO(sheet));
	// model.addAttribute("primaryFile",
	// KETContentHelper.manager.getPrimaryContent(sheet));
	// model.addAttribute("secondaryFiles",
	// KETContentHelper.manager.getSecondaryContents(sheet));
    }

    /**
     * 평가시트 리스트 화면 호출시 listTemplateAssess.jsp 페이지로 이동하기위한 함수
     * 
     * @throws Exception
     * @메소드명 : list
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listTemplateAssess")
    public void list() throws Exception {

    }

    /**
     * 평가시트 리스트 화면 호출시 listTemplateAssess.jsp 페이지로 이동하기위한 함수
     * 
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findListTemplateAssess")
    @ResponseBody
    public Map<String, Object> findList(TemplateAssessSheetDTO assDto) throws Exception {

	List<TemplateAssessSheet> list = TemplateAssessSheetHelper.service.find(assDto);
	List<TemplateAssessSheetDTO> dtoList = new ArrayList<TemplateAssessSheetDTO>();
	for (TemplateAssessSheet vo : list) {
	    dtoList.add(new TemplateAssessSheetDTO(vo));
	}
	return EjsConverUtil.convertToDto(dtoList);
    }

    /**
     * 평가시트 등록 및 수정화면에서 평가시트 OID값을 가지고 하위 평가항목 조회
     * 
     * @param pjtOid
     *            평가시트 OID
     * @param TemplateAssessSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findListTemplateGateCheckSheet")
    @ResponseBody
    public Map<String, Object> findListTemplateGateCheckSheet(String pjtOid, TemplateGateCheckSheetDTO checkSheetDto) throws Exception {

	Kogger.debug(getClass(), "findListTemplateGateCheckSheet pjtOid:" + pjtOid);
	// NumberCode nc = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
	// developentType);

	List<TemplateGateCheckSheet> list = null;
	if (!StringUtil.isEmpty(pjtOid))
	    list = TemplateGateCheckSheetHelper.service.findGateCheckSheet(pjtOid, checkSheetDto);
	else
	    list = null; // TemplateGateCheckSheetHelper.service.find(checkSheetDto);

	List<TemplateGateCheckSheetDTO> dtoList = new ArrayList<TemplateGateCheckSheetDTO>();
	if (list != null) {
	    // int listSize = list.size();
	    int listSize = 1;
	    for (TemplateGateCheckSheet vo : list) {
		TemplateGateCheckSheetDTO dto = new TemplateGateCheckSheetDTO(vo);
		dto.setRowNum(listSize++);
		dtoList.add(dto);
	    }
	}
	return EjsConverUtil.convertToDto(dtoList);
    }

    /**
     * Grid의 Server paging 방식을 이용한 JSON 데이터
     * 
     * @param TemplateAssessSheetDTO
     * @param page
     * @param formPage
     * @return
     * @throws Exception
     * @메소드명 : findPagingList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPagingListTemplateAssess")
    @ResponseBody
    public Map<String, Object> findPagingListTemplateAssess(TemplateAssessSheetDTO assDto) throws Exception {
	if (assDto.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}

	PageControl pageControl = TemplateAssessSheetHelper.service.findPaging(assDto);
	if (pageControl == null)
	    pageControl = new PageControl();

	List<TemplateAssessSheet> list = new ArrayList<TemplateAssessSheet>();
	List<TemplateAssessSheetDTO> dtoList = new ArrayList<TemplateAssessSheetDTO>();
	if (pageControl != null && pageControl.getTotalCount() > 0) {
	    SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	    QueryResult queryResult = pageControl.getResult();
	    while (queryResult.hasMoreElements()) {
		Object[] objArr = (Object[]) queryResult.nextElement();
		list.add((TemplateAssessSheet) objArr[0]);
	    }
	    for (TemplateAssessSheet vo : list) {
		dtoList.add(new TemplateAssessSheetDTO(vo));
	    }
	}

	return EjsConverUtil.convertToDto(dtoList, pageControl);

    }

    /**
     * 프로젝트에 평가시트를 등록하기 위해 평가시트 검색 및 선택하는 createTemplateAssessForm.jsp 페이지로 이동
     * 
     * @param TemplateAssessSheetDTO
     * @throws Exception
     * @메소드명 : selectTemplateAssessForm
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/selectTemplateAssessForm")
    public void selectTemplateAssessForm(TemplateAssessSheetDTO dto) throws Exception {
    }

}
