package ext.ket.edm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import e3ps.common.web.PageControl;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.entity.dto.KETMaterialDTO;
import ext.ket.edm.service.DrawingDistHelper;
import ext.ket.edm.service.PlmHpIfService;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : PlmHpIfController
 * @작성자 : KKW
 * @작성일 : 2014. 7. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */

@Controller
@RequestMapping("/edm/*")
public class PlmHpIfController {

    @Inject
    private PlmHpIfService plmHpIfService;

    @RequestMapping("/plmHpIfList")
    public void plmHpIfList(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	if ("Y".equals(ketDrawingDistReqDTO.getSearchCheck())) {
	    List<Map<String, Object>> plmHpIfList = DrawingDistHelper.service.getPlmHpIfList(ketDrawingDistReqDTO);
	    KETDrawingDistReqDTO retDto = new KETDrawingDistReqDTO();
	    retDto = ketDrawingDistReqDTO;
	    retDto.setResultMapList(plmHpIfList);

	    // System.out.println("by hooni :::::::::::::::::::::: " + retDto.getResultMapList());

	    model.addAttribute("resultMapList", retDto.getResultMapList());
	    model.addAttribute("partNo", StringUtils.stripToEmpty(retDto.getPartNo()));
	    model.addAttribute("ecoNo", StringUtils.stripToEmpty(retDto.getEcoNo()));
	    model.addAttribute("stampstatus", StringUtils.stripToEmpty(retDto.getStampstatus()));
	    model.addAttribute("hpstatus", StringUtils.stripToEmpty(retDto.getHpstatus()));
	    model.addAttribute("divisionflag", StringUtils.stripToEmpty(retDto.getDivisionflag()));
	    model.addAttribute("createStartDate", StringUtils.stripToEmpty(retDto.getCreateStartDate()));
	    model.addAttribute("createEndDate", StringUtils.stripToEmpty(retDto.getCreateEndDate()));
	}
    }

    @RequestMapping("/hpIfList")
    public void hpIfList(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {

    }

    @RequestMapping("/updateHpIfFilePopup")
    public void updateHpIfFilePopup(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	model.addAttribute("oid", ketDrawingDistReqDTO.getOid());

	String oid = ketDrawingDistReqDTO.getOid();
	String partOid = StringUtils.trimToEmpty(ketDrawingDistReqDTO.getPartOid());

	List<Map<String, Object>> plmHpIfList = DrawingDistHelper.service.getPlmHpIfListByOid(oid, partOid);
	KETDrawingDistReqDTO dto = new KETDrawingDistReqDTO();
	dto.setResultMapList(plmHpIfList);

	model.addAttribute("resultMapList", dto.getResultMapList());
	model.addAttribute("oid", ketDrawingDistReqDTO.getOid());
	model.addAttribute("partOid", ketDrawingDistReqDTO.getPartOid());

    }

    @RequestMapping("/addMaterialPopup")
    public void addMaterialPopup(KETMaterialDTO ketMaterialDTO, Model model) throws Exception {
	model.addAttribute("partOid", ketMaterialDTO.getPartOid());

	ketMaterialDTO.setMaterialDTOList((List<KETMaterialDTO>) plmHpIfService.getMaterialList(ketMaterialDTO));

	model.addAttribute("materialDTOList", ketMaterialDTO.getMaterialDTOList());

    }

    @RequestMapping("/searchPlmHpIfList")
    @ResponseBody
    public List<Map<String, Object>> searchPlmHpIfList(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	KETDrawingDistReqDTO retDto = new KETDrawingDistReqDTO();
	List<Map<String, Object>> plmHpIfList = DrawingDistHelper.service.getPlmHpIfList(ketDrawingDistReqDTO);
	retDto.setResultMapList(plmHpIfList);

	return retDto.getResultMapList();
    }

    @RequestMapping("/saveHpIfFile")
    public void saveHpIfFile(HttpServletRequest request, KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {

	// FormUploader uploader = FormUploader.newFormUploader(request);
	// KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	boolean bSuccess = DrawingDistHelper.service.saveHpIfFile(ketDrawingDistReqDTO);

	if (bSuccess)
	    ketDrawingDistReqDTO.setDownFlag("Y");
	else
	    ketDrawingDistReqDTO.setDownFlag("N");

	model.addAttribute("bSuccess", ketDrawingDistReqDTO.getDownFlag());

    }

    @RequestMapping("/saveMaterial")
    @ResponseBody
    public String saveMaterial(KETMaterialDTO materialDTO, Model model) throws Exception {
	plmHpIfService.save(materialDTO);

	return "OK";

    }

    @RequestMapping("/reStamping")
    @ResponseBody
    public String reStamping(KETDrawingDistReqDTO drawingDistReqDTO, Model model) throws Exception {
	DrawingDistHelper.service.reStamping(drawingDistReqDTO.getOid());

	return "OK";

    }

    @RequestMapping("/sendHp")
    @ResponseBody
    public String sendHp(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {

	// 홈페이지의 데이터를 최종 이관하면
	if (ketDrawingDistReqDTO.getOid().lastIndexOf(",") == -1) {
	    ketDrawingDistReqDTO.setOid(ketDrawingDistReqDTO.getOid());
	} else {
	    ketDrawingDistReqDTO.setOid(ketDrawingDistReqDTO.getOid().substring(0, ketDrawingDistReqDTO.getOid().lastIndexOf(",")));
	}

	boolean bSendHp = DrawingDistHelper.service.sendHp(ketDrawingDistReqDTO);

	if (bSendHp)
	    return "OK";
	else
	    return "FAIL";

    }

    @RequestMapping("/sendPartAttr")
    @ResponseBody
    public String sendPartAttr() throws Exception {

	boolean bSendHp = DrawingDistHelper.service.sendPartAttr();

	if (bSendHp)
	    return "OK";
	else
	    return "FAIL";

    }

    /**
     * Grid의 Server paging 방식을 이용한 JSON 데이터
     * 
     * @param sample
     * @param page
     * @param formPage
     * @return
     * @throws Exception
     * @메소드명 : findPagingList
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    /*@RequestMapping("/hpFindPagingList")
    @ResponseBody
    public Map<String, Object> hpFindPagingList(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {
	if (ketDrawingDistReqDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = DrawingDistHelper.service.hpFindPaging(ketDrawingDistReqDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();
	List<KETDrawingDistReqDTO> distDTOList = new ArrayList<KETDrawingDistReqDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    distDTOList.add(new KETDrawingDistReqDTO((KETDrawingDistRequest) objArr[0]));

	    System.out.println("by hooni ::: hpFindPagingList ??? ");
	}
	return EjsConverUtil.convertToDto(distDTOList, pageControl);
    }*/

}
