package ext.ket.arm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.util.CommonUtil;
import ext.ket.arm.entity.KETAnalysisInfoDTO;
import ext.ket.arm.entity.KETAnalysisRequestDTO;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.arm.service.KETAnalysisInfoHelper;

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
@RequestMapping("/arm/info/*")
public class ArmInfoController {
    private KETAnalysisInfoDTO analysisInfoDTO;
    private KETAnalysisRequestDTO analysisRequestDTO;

    /**
     * 등록 페이지
     * 
     * @param analysisDTO
     *            , model
     * @throws Exception
     * @메소드명 : createInfoForm
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 01.
     * @설명 : 등록 페이지 Form
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armInfoCreateForm")
    public void createInfoForm(KETAnalysisRequestDTO analysisDTO, Model model) throws Exception {
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid());
	analysisDTO = analysisDTO.KETAnalysisRequestDTO(analysisDTO, analysis);
	model.addAttribute("analysis", analysisDTO);
	// model.addAttribute("analysis", new KETAnalysisRequestDTO((KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid())));
    }

    /**
     * 저장
     * 
     * @param analysisDTO
     * @return
     * @throws Exception
     * @메소드명 : create
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 08.
     * @설명 : 등록 Process
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armInfoCreate")
    public String create(KETAnalysisInfoDTO analysisInfoDTO, Model model) throws Exception {
	KETAnalysisRequestInfo info = KETAnalysisInfoHelper.service.save(analysisInfoDTO);
	return "redirect:/ext/arm/info/armInfoViewForm.do?oid=" + analysisInfoDTO.getmOid();
    }

    /**
     * 상세화면 조회
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : armInfoViewForm
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 09.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armInfoViewForm")
    public void armInfoViewForm(KETAnalysisInfoDTO analysisInfoDTO, KETAnalysisRequestDTO analysisDTO, Model model) throws Exception {
	KETAnalysisRequestMaster analysis = null;
	KETAnalysisRequestInfo analysisInfo = null;
	if (analysisDTO.getOid().indexOf("KETAnalysisRequestInfo") > 0) {
	    analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(KETAnalysisInfoHelper.service.getAnalysisMasterOid(analysisDTO
		    .getOid()));
	    analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(analysisDTO.getOid());
	} else {
	    analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid());
	    analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(KETAnalysisInfoHelper.service.getAnalysisInfoOid(analysisDTO
		    .getOid()));
	}
	analysisDTO = analysisDTO.KETAnalysisRequestDTO(analysisDTO, analysis);
	analysisInfoDTO = analysisInfoDTO.KETAnalysisInfoDTO(analysisInfoDTO, analysisInfo);
	model.addAttribute("analysis", analysisDTO);
	model.addAttribute("analysisInfo", analysisInfoDTO);

	// model.addAttribute("analysis", new KETAnalysisRequestDTO((KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid())));
    }

    /**
     * 수정 화면
     * 
     * @param
     * @throws Exception
     * @메소드명 : armInfoUpdateForm
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 15.
     * @설명 : 수정 페이지 Form
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armInfoUpdateForm")
    public void armInfoUpdateForm(KETAnalysisInfoDTO analysisInfoDTO, KETAnalysisRequestDTO analysisDTO, Model model) throws Exception {
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid());
	KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(KETAnalysisInfoHelper.service
	        .getAnalysisInfoOid(analysisDTO.getOid()));
	analysisDTO = analysisDTO.KETAnalysisRequestDTO(analysisDTO, analysis);
	analysisInfoDTO = analysisInfoDTO.KETAnalysisInfoDTO(analysisInfoDTO, analysisInfo);
	model.addAttribute("analysis", analysisDTO);
	model.addAttribute("analysisInfo", analysisInfoDTO);

	// model.addAttribute("analysis", new KETAnalysisRequestDTO((KETAnalysisRequestMaster) CommonUtil.getObject(analysisDTO.getOid())));
    }

    /**
     * 수정 저장
     * 
     * @param analysisDTO
     * @return
     * @throws Exception
     * @메소드명 : armMasterUpdate
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 15.
     * @설명 : 등록 Process
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/armInfoUpdate")
    public String armInfoUpdate(KETAnalysisInfoDTO analysisInfoDTO, Model model) throws Exception {
	KETAnalysisRequestInfo info = KETAnalysisInfoHelper.service.modify(analysisInfoDTO);
	return "redirect:/ext/arm/info/armInfoViewForm.do?oid=" + analysisInfoDTO.getmOid();
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
    @RequestMapping("/infoDelete")
    @ResponseBody
    public void infoDelete(KETAnalysisInfoDTO paramObject) throws Exception {
	KETAnalysisInfoHelper.service.delete(paramObject);
    }
}
