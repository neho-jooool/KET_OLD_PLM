package ext.ket.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.PageControl;
import ext.ket.sample.entity.Sample;
import ext.ket.sample.entity.SampleDTO;
import ext.ket.sample.service.SampleHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * Sample 모듈 controller
 * 
 * @클래스명 : SampleController
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/sample/*")
public class SampleController {
    /**
     * 저장
     * 
     * @param sample
     * @return
     * @throws Exception
     * @메소드명 : create
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/create")
    public String create(SampleDTO sampleDTO) throws Exception {
	sampleDTO.setName(SessionHelper.manager.getPrincipal().getName());
	SampleHelper.service.save(sampleDTO);
	return "redirect:/ext/sample/list.do";
    }

    /**
     * 등록 페이지
     * 
     * @param sample
     * @throws Exception
     * @메소드명 : createForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createForm")
    public void createForm(SampleDTO sampleDTO) throws Exception {
    }

    /**
     * 삭제
     * 
     * @param sample
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/delete")
    public String delete(SampleDTO sampleDTO) throws Exception {
	SampleHelper.service.delete(sampleDTO);
	return "redirect:/ext/sample/list.do";
    }

    /**
     * Grid를 위한 JSON 데이터
     * 
     * @param sampleDTO
     * @return
     * @throws Exception
     * @메소드명 : findList
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findList")
    @ResponseBody
    public Map<String, Object> findList(SampleDTO sampleDTO) throws Exception {
	// methodserver로 부터 데이터를 조회한다.
	List<Sample> sampleList = SampleHelper.service.find(sampleDTO);
	// DTO 객체로 변환
	List<SampleDTO> sampleDTOList = new ArrayList<SampleDTO>();
	for (Sample vo : sampleList) {
	    sampleDTOList.add(new SampleDTO(vo));
	}
	return EjsConverUtil.convertToDto(sampleDTOList);
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
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 5.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(SampleDTO sampleDTO) throws Exception {

	if (sampleDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = SampleHelper.service.findPaging(sampleDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();
	List<SampleDTO> sampleDTOList = new ArrayList<SampleDTO>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    sampleDTOList.add(new SampleDTO((Sample) objArr[0]));
	}
	return EjsConverUtil.convertToDto(sampleDTOList, pageControl);
    }

    /**
     * 검색 화면
     * 
     * @throws Exception
     * @메소드명 : list
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/list")
    public void list() throws Exception {
    }

    /**
     * 수정된 내용 저장
     * 
     * @param sampleDTO
     * @throws Exception
     * @메소드명 : update
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/update")
    public String update(String oid, SampleDTO sampleDTO) throws Exception {

	SampleHelper.service.modify(sampleDTO);
	return "redirect:/ext/sample/list.do";
    }

    /**
     * 수정 화면
     * 
     * @param oid
     * @param model
     * @throws Exception
     * @메소드명 : updateForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateForm")
    public void updateForm(String oid, Model model) throws Exception {
	Sample sample = (Sample) CommonUtil.getObject(oid);
	model.addAttribute("sample", new SampleDTO(sample));
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(sample));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(sample));
    }
}
