package ext.ket.common.dashboard.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import e3ps.common.message.KETMessageService;
import ext.ket.common.dashboard.entity.dto.KETMailReceiveDTO;
import ext.ket.common.dashboard.service.KETMailReceiveHelper;
import ext.ket.shared.log.Kogger;

@Controller
@RequestMapping("/common/dashboard/*")
public class KETMailReceiveController {
    // Admin - 종합현황 메일 수신 설정 Left 리스트 화면
    @RequestMapping("/viewMailReceiveListForm")
    public void viewMailReceiveListForm(Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# DashBoard MailReceiveList Start  ##########");
	Kogger.debug(getClass(), "###########################################");

	List<KETMailReceiveDTO> fullList = KETMailReceiveHelper.service.searchFullList();
	model.addAttribute("resultList", fullList);

    }

    // Admin - 종합현황 메일 수신 설정 상세 리스트 화면
    @RequestMapping("/viewMailReceiveDetailListForm")
    public void viewMailReceiveDetailListForm(@RequestParam(value = "oid") String oid, Model model) throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############# Part naming Start  ##########");
	Kogger.debug(getClass(), "###########################################");

	List<KETMailReceiveDTO> fullList = null;
	if (StringUtils.isEmpty(oid)) {
	    fullList = KETMailReceiveHelper.service.searchFullList();
	} else {
	    fullList = KETMailReceiveHelper.service.searchFullList(oid);
	}

	model.addAttribute("resultList", fullList);

    }

    // 종합현황 메일 수신 저장
    @RequestMapping("/save")
    public void save(KETMailReceiveDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {
	String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454"); /* "저장되었습니다." */
	try {
	    KETMailReceiveHelper.service.save(dto);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    msg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
	}

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
	        + "\n parent.parent.location.href='/plm/ext/common/dashboard/viewMailReceiveListForm.do'" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;

    }

    // 종합현황 메일 수신 삭제
    @RequestMapping("/delete")
    public void delete(@RequestParam(value = "oid") String oid, SessionStatus status, HttpServletResponse response) throws Exception {
	String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "05121");/* "정상적으로 삭제되었습니다." */
	try {
	    KETMailReceiveHelper.service.delete(oid);
	    status.setComplete();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    msg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
	}

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
	        + "\n parent.parent.location.href='/plm/ext/common/dashboard/viewMailReceiveListForm.do'" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;

    }
    
    //메일 발송
    @RequestMapping("/mailSend")
    @ResponseBody
    public String mailSend(KETMailReceiveDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {
	String msg = "";
	try {
	    //KETMailReceiveHelper.service.dashboardSendMail();
	    KETMailReceiveHelper.service.pjtMainScheduleSendMail();
	    msg = "S";
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    msg = "E";
	}
	return msg;

    }
}