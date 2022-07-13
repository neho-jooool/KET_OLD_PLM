package ext.ket.part.spec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkInputDTO;
import ext.ket.part.spec.service.PartSpecHelper;
import ext.ket.shared.log.Kogger;

/**
 * Part 모듈 controller
 * 
 * @클래스명 : PartSpcController
 * @작성자 : sw775.park
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/part/spec/*")
public class PartSpecController {

    // 분류체계 부품 속성 리스트 정보가져오기 - 기등록된 정보
    @RequestMapping("/searchSpecPopup")
    public void searchSpecPopup(@RequestParam(value = "oid") String clazOid, Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part Spec Start  ############");
	Kogger.debug(getClass(), "###########################################");

	List<PartClassAttrLinkDTO> selectedList = PartSpecHelper.service.searchSelectedList(clazOid);

	model.addAttribute("resultList", selectedList);
	model.addAttribute("oid", clazOid);

    }

    // 부품 전체 속성리스트 정보가져오기 - 기존 것은 체크 함
    @RequestMapping("/searchPartAttrPopup")
    public void searchPartAttrPopup(@RequestParam(value = "attrOidArray") String attrOidArray, Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part Attr Start  ############");
	Kogger.debug(getClass(), "###########################################");

	List<PartAttributeDTO> fullList = PartSpecHelper.service.searchPartAttrList(attrOidArray);
	model.addAttribute("resultList", fullList);

    }

    // 분류체계 부품 속성 추가 삭제 정보 저장
    @RequestMapping("/savePartSpecAttrList")
    public void savePartSpecAttrList(@ModelAttribute PartClassAttrLinkInputDTO linkData, SessionStatus status, @RequestParam(value = "oid") String clazOid) throws Exception {

	Kogger.debug(getClass(), "==========================================");// ,@RequestBody ClazXmlBodyObject body
	Kogger.debug(getClass(), "==========   saveClazDataXml    ==========");
	Kogger.debug(getClass(), "==========================================");

	try {

	    Kogger.debug(getClass(), linkData);

	    if (linkData == null) {
		linkData = new PartClassAttrLinkInputDTO();
	    }

	    List<PartClassAttrLinkDTO> list = linkData.getPartClassAttrLinkDTOs();
	    if (list == null)
		list = new ArrayList<PartClassAttrLinkDTO>();

	    PartSpecHelper.service.savePartClazAttrLinkList(list, clazOid);
	    status.setComplete();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
}
