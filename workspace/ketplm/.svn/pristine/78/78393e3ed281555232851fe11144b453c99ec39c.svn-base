package ext.ket.material.controller;

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

import wt.fc.QueryResult;
import wt.util.WTAttributeNameIfc;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.project.material.MoldMaterial;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.material.entity.KETPartMaterialDTO;
import ext.ket.material.service.MaterialHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

@Controller
@RequestMapping("/material/*")
public class materialController {
    @RequestMapping("/materialList")
    public Model materialList(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	FileContentUtil.manager.saveDownloadHistory("원재료DB관리", "");
	model.addAttribute("AUTH", CommonUtil.isMember("원재료관리") || CommonUtil.isAdmin());
	return model;
    }
    
    @ResponseBody
    @RequestMapping("/findPagingList")
    public Map<String, Object> findPagingList(KETPartMaterialDTO mater) throws Exception {
	
	if (mater.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	
	PageControl pageControl = MaterialHelper.service.findPagingList(mater);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult qr = pageControl.getResult();

	List<KETPartMaterialDTO> materDTOList = new ArrayList<KETPartMaterialDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    MoldMaterial material = (MoldMaterial) objArr[0];

	    KETPartMaterialDTO materDTO = new KETPartMaterialDTO(material);
	    String createDate = String.valueOf(objArr[1]);
	    String modifyDate = String.valueOf(objArr[2]);
	    
	    materDTO.setCreateDate(createDate);
	    materDTO.setModifyDate(modifyDate);
	    
	    materDTOList.add(materDTO);
	}
	
	
	// 소팅로직
	if (!StringUtil.isTrimToEmpty(mater.getSortName())) {

	    String checkName = mater.getSortName();

	    String sortName = "";

	    String tempCheckName = checkName;

	    if (!checkName.startsWith("-")) {
		tempCheckName = "-" + checkName;
	    }

	    switch (tempCheckName.substring(1)) {
	    case "type":
		sortName = "getType";
		break;
	    case "maker":
		sortName = "getMaker";
		break;
	    default:
		break;
	    }

	    if (checkName.startsWith("-") && StringUtils.isNotEmpty(sortName)) {
		ComparatorUtil.sortListVO(materDTOList, sortName, "DESC"); // 내림차순
	    } else if(StringUtils.isNotEmpty(sortName)){
		ComparatorUtil.sortListVO(materDTOList, sortName, "ASC"); // 오름차순
	    }
	}
			
	return EjsConverUtil.convertToDto(materDTOList, pageControl);
    }
    
    @ResponseBody
    @RequestMapping("/saveMaterial")
    public Map<String, Object> saveMaterial(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	
	try {
	    resMap = MaterialHelper.service.saveMaterial(reqMap);

	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
    
    @RequestMapping("/viewMaterialPopup")
    public Model viewMaterialPopup(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {
	
	String oid = (String) reqMap.get("oid");
	model.addAttribute("oid", oid);
	
	KETPartMaterialDTO paramDto = new KETPartMaterialDTO();
	paramDto.setOid(oid);
	
	KETPartMaterialDTO materDto = MaterialHelper.service.getMaterialDtoByOid(paramDto);
	List<Map<String, Object>> partList = MaterialHelper.service.getMaterialPartMapList(reqMap);
	
	model.addAttribute("partList", partList);
	model.addAttribute("dto", materDto);
	
	return model;
    }
    
    @RequestMapping("/syncMaterialForPartLink")
    @ResponseBody
    public Map<String, Object> syncMaterialForPartLink() throws Exception {
	
	Map<String, Object> resMap = new HashMap<String, Object>();
	
	try{
	    MaterialHelper.service.syncMaterialForPartLink(); 
	    
	    resMap.put("message", "부품정보 수집이 완료되었습니다.");
	    resMap.put("result", true);
	}catch(Exception e){
	    Kogger.error(getClass(), e);
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}
	return resMap;
    }
}
