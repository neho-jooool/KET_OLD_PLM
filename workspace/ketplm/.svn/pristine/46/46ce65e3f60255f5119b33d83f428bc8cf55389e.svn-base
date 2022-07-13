package ext.ket.arm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.PageControl;
import ext.ket.arm.entity.KETAnalysisRequestDTO;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.arm.service.KETAnalysisRequestHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * Sample 모듈 controller
 * 
 * @클래스명 : ArmController
 * @작성자 : Hooni
 * @작성일 : 2014. 10. 15.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/arm/master/*")
public class ArmController {
    private KETAnalysisRequestDTO KETAnalysisRequestDTO;

    /**
     * 등록 페이지
     * 
     * @param
     * @throws Exception
     * @메소드명 : armMasterCreateForm
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 15.
     * @설명 : 등록 페이지 Form
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armMasterCreateForm")
    public void armMasterCreateForm() throws Exception {
    }

    /**
     * 저장
     * 
     * @param analysisDTO
     * @return
     * @throws Exception
     * @메소드명 : armMasterCreate
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 20.
     * @설명 : 등록 Process
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armMasterCreate")
    public String armMasterCreate(KETAnalysisRequestDTO analysisDTO) throws Exception {
	KETAnalysisRequestMaster master = KETAnalysisRequestHelper.service.save(analysisDTO);
	return "redirect:/ext/arm/master/armMasterViewForm.do?oid=" + CommonUtil.getOIDString(master);
    }

    /**
     * 수정 저장
     * 
     * @param analysisDTO
     * @return
     * @throws Exception
     * @메소드명 : armMasterUpdate
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 24.
     * @설명 : 등록 Process
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armMasterUpdate")
    public String armMasterUpdate(KETAnalysisRequestDTO analysisDTO) throws Exception {
	KETAnalysisRequestMaster master = KETAnalysisRequestHelper.service.modify(analysisDTO);
	return "redirect:/ext/arm/master/armMasterViewForm.do?oid=" + CommonUtil.getOIDString(master);
    }

    /**
     * 상세화면 조회
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : viewArm
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armMasterViewForm")
    public void armMasterViewForm(KETAnalysisRequestDTO analysisDTO, Model model) throws Exception {
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid());
	analysisDTO = analysisDTO.KETAnalysisRequestDTO(analysisDTO, analysis);
	model.addAttribute("analysis", analysisDTO);
	if (analysis != null) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(analysis));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(analysis));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
	// model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(analysis));
	// model.addAttribute("analysis", new KETAnalysisRequestDTO((KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid())));
    }

    /**
     * 수정 페이지
     * 
     * @param
     * @throws Exception
     * @메소드명 : armMasterUpdateForm
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 20.
     * @설명 : 수정 페이지 Form
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armMasterUpdateForm")
    public void armMasterUpdateForm(KETAnalysisRequestDTO paramObject, Model model) throws Exception {
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getOid());
	model.addAttribute("analysis", new KETAnalysisRequestDTO((KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getOid())));
	if (analysis != null) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(analysis));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(analysis));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
    }

    /**
     * 의뢰요청서 삭제
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : deleteProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/masterDelete")
    @ResponseBody
    public void masterDelete(KETAnalysisRequestDTO paramObject) throws Exception {
	KETAnalysisRequestHelper.service.delete(paramObject);
    }

    /**
     * 검색 화면
     * 
     * @throws Exception
     * @메소드명 : amrList
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armList")
    public void amrList(Model model) throws Exception {
    }

    /**
     * 검색 PopUp 화면
     * 
     * @throws Exception
     * @메소드명 : amrList
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/SearchAnalysisPopup")
    public void amrListPopUp(Model model) throws Exception {
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
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(KETAnalysisRequestDTO analysisDTO) throws Exception {

	if (analysisDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = KETAnalysisRequestHelper.service.findPaging(analysisDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();
	List<KETAnalysisRequestDTO> analysisDTOList = new ArrayList<KETAnalysisRequestDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    analysisDTOList.add(new KETAnalysisRequestDTO((KETAnalysisRequestMaster) objArr[0]));
	}
	return EjsConverUtil.convertToDto(analysisDTOList, pageControl);
    }

    /**
     * Grid의 Server paging 방식을 이용한 JSON 데이터
     * 
     * @param sample
     * @param page
     * @param formPage
     * @return
     * @throws Exception
     * @메소드명 : findPopUpPagingList
     * @작성자 : Hooni
     * @작성일 : 2015. 06. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPopUpPagingList")
    @ResponseBody
    public Map<String, Object> findPopUpPagingList(KETAnalysisRequestDTO analysisDTO) throws Exception {
	analysisDTO.setPopup("popup");
	if (analysisDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = KETAnalysisRequestHelper.service.findPaging(analysisDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();
	List<KETAnalysisRequestDTO> analysisDTOList = new ArrayList<KETAnalysisRequestDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    analysisDTOList.add(new KETAnalysisRequestDTO((KETAnalysisRequestMaster) objArr[0]));
	}
	return EjsConverUtil.convertToDto(analysisDTOList, pageControl);
    }

    /**
     * PartNo를 통한 Part 정보 조회
     * 
     * @메소드명 : list
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/getPart")
    @ResponseBody
    public String getPart(String partOid, HttpServletResponse response) throws Exception {
	// ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
	String partInfos4ECM = "";
	String[] partOidArr = partOid.split(",");
	for (int i = 0; i < partOidArr.length; i++) {
	    WTPart part = (WTPart) CommonUtil.getObject(partOidArr[i]);
	    String realPartOid = partOidArr[i];
	    String realPartVersion = part.getVersionIdentifier().getValue();
	    String realPartNumber = part.getNumber();
	    String realPartName = part.getName();
	    if (i != 0) {
		partInfos4ECM += "↕";
	    }
	    partInfos4ECM += realPartOid + "↔" + realPartNumber + "↔" + realPartName + "↔" + realPartVersion;
	}
	return partInfos4ECM;
    }
}
