package ext.ket.part.naming.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.entity.dto.PartNamingDTO;
import ext.ket.part.naming.service.PartNamingcHelper;
import ext.ket.shared.log.Kogger;

@Controller
@RequestMapping("/part/naming/*")
public class PartNamingController {

    // Admin - Naming Left 리스트 화면
    @RequestMapping("/viewPartNamingListForm")
    public void viewPartNamingListForm(Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part naming Start  ##########");
	Kogger.debug(getClass(), "###########################################");

	List<PartNamingDTO> fullList = PartNamingcHelper.service.searchFullList(getLocale());
	model.addAttribute("resultList", fullList);

    }
    
    // Admin - Naming Right 상세 리스트 화면
    @RequestMapping("/viewPartNamingDetailListForm")
    public void viewPartNamingDetailListForm(Model model) throws Exception {
	
	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part naming Start  ##########");
	Kogger.debug(getClass(), "###########################################");
	
	List<PartNamingDTO> fullList = PartNamingcHelper.service.searchFullList(getLocale());
	model.addAttribute("resultList", fullList);
	
    }

    // Admin - 선택 Naming 조회 화면
    @RequestMapping("/viewPartNamingForm")
    public void viewSelectedPartNamingForm(@RequestParam(value = "partNamingOid") String partNamingOid, Model model) throws Exception {

	Kogger.debug(getClass(), "##############################################");
	Kogger.debug(getClass(), "############# Selecte Part naming ############");
	Kogger.debug(getClass(), "##############################################");

	PartNamingDTO partNamingDTO = PartNamingcHelper.service.searchSelectedPartNaming(partNamingOid, getLocale());
	model.addAttribute("result", partNamingDTO);

    }
    
    // Admin - 선택 Naming 등록, 수정 화면
    @RequestMapping("/createPartNamingForm")
    public void createPartNamingForm(@RequestParam(required = false, value="partNamingOid") final String partNamingOid, Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part Naming Create ##########");
	Kogger.debug(getClass(), "############# Part Naming Modify ##########");
	Kogger.debug(getClass(), "###########################################");
	
	PartNamingDTO partNamingDTO = null;
	
	if(StringUtils.isEmpty(partNamingOid)){
	    partNamingDTO = new PartNamingDTO();
	}else{
	    partNamingDTO = PartNamingcHelper.service.searchSelectedPartNaming(partNamingOid, getLocale());
	}
	
	model.addAttribute("result", partNamingDTO);
	
    }
    
    // Admin - Naming 저장시 호출
    @RequestMapping("/savePartNaming")
    public String createPartNaming(@ModelAttribute PartNamingDTO partNamingDTO, SessionStatus status) throws Exception {

	Kogger.debug(getClass(), "==========================================");
	Kogger.debug(getClass(), "==========   savePartNaming     ==========");
	Kogger.debug(getClass(), "==========================================");

	try {

	    Kogger.debug(getClass(), partNamingDTO);

	    String oid = PartNamingcHelper.service.savePartNaming(partNamingDTO);
	    status.setComplete();
	    
	    return "redirect:/ext/part/naming/viewPartNamingForm.do?partNamingOid="+oid;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    private Locale getLocale() throws Exception{
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	KETMessageService messageService = KETMessageService.getMessageService(request);
	if(messageService == null){
	    WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();
	    messageService = new KETMessageService(KETMessageService.getUserLocale(usr));
	    KETMessageService.setMessageService((HttpSession)request.getSession(), messageService);
	}
	return messageService.getLocale();
    }
    
    // Admin - Naming 삭제후 리스트 화면
    @RequestMapping("/deletePartNamingForm")
    public String deletePartNamingForm(@RequestParam(value = "partNamingOid") String partNamingOid, SessionStatus status) throws Exception {

	Kogger.debug(getClass(), "==========================================");
	Kogger.debug(getClass(), "==========  delete PartNaming   ==========");
	Kogger.debug(getClass(), "==========================================");

	try {

	    PartNamingcHelper.service.deletePartNaming(partNamingOid);
	    status.setComplete();
	    
	    return "redirect:/ext/part/naming/viewPartNamingDetailListForm.do";

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    
}
