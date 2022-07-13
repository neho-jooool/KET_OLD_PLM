package ext.ket.shared.log.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.web.PageControl;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.log.entity.dto.EcmLogDTO;
import ext.ket.shared.log.entity.dto.EcmLogSearchDTO;
import ext.ket.shared.log.service.KetLogHelper;
import ext.ket.shared.util.EjsConverUtil;

/**
 * 
 * 
 * @클래스명 : KLogController
 * @작성자 : yjlee
 * @작성일 : 2015. 1. 19.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/klog/*")
public class KLogController {
    

    // 로그 검색화면 조회
    @RequestMapping("/listLog")
    public void listLog(Model model, HttpServletRequest request) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############ Log list search  ############");
	Kogger.debug(getClass(), "###########################################");

	model.addAttribute("isAdmin", PartBaseHelper.service.isAdmin());
    }

    // 로그 검색 Grid 용
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(EcmLogSearchDTO ecmLogSearchDTO, @RequestParam(value = "pageTotalSize") String pageTotalSize) throws Exception {

	PageControl pageControl = KetLogHelper.service.searchEcoLogList(ecmLogSearchDTO, pageTotalSize);
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);

    }

    // 로그 상세 조회 화면
    @RequestMapping("/viewDetailLog")
    public void viewDetailLog(@ModelAttribute EcmLogDTO ecmLogDTO, Model model, HttpServletRequest request) throws Exception {
	
	List<EcmLogDTO> list = KetLogHelper.service.viewDetailEcoLog(ecmLogDTO, getLocale());
	model.addAttribute("result", list);

    }

    // ECO 에러 복구
    @RequestMapping("/recoverEcoError")
    @ResponseBody
    public String recoverEcoError(@ModelAttribute EcmLogDTO ecmLogDTO, Model model) throws Exception {
	try {

	    boolean ecmLog = KetLogHelper.service.recoverEcoError(ecmLogDTO);
	    return String.valueOf(ecmLog);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    // 로그 삭제
    @RequestMapping("/deleteLog")
    @ResponseBody
    public String deleteLog(@ModelAttribute EcmLogDTO ecmLogDTO, Model model) throws Exception {

	try {

	    KetLogHelper.service.deleteLog(ecmLogDTO, getLocale());
	    return "OK";

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

}
