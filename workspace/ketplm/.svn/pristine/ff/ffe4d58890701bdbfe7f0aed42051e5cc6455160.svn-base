package ext.ket.edm.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import wt.fc.QueryResult;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETDrawingDownHis;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.service.DrawingDistFindService;
import ext.ket.edm.service.DrawingDistHelper;
import ext.ket.edm.service.base.EpmBaseHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : DrawingDistReqController
 * @작성자 : KKW
 * @작성일 : 2014. 7. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */

@Controller
@RequestMapping("/edm/*")
public class DrawingDistReqController {

    @Inject
    private DrawingDistFindService drawingDistFindService;

    @RequestMapping("/drawingDownHistry")
    public void drawingDownHistry(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	List<Map<String, Object>> drawingDownHisList = DrawingDistHelper.service.getDrawingDownHisry(ketDrawingDistReqDTO);
	model.addAttribute("drawingDistRequestOid", ketDrawingDistReqDTO.getDrawingDistRequestOid());
	model.addAttribute("drawingDownHisList", drawingDownHisList);
    }

    @RequestMapping("/downloadReasonForm")
    public void downloadReasonForm(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	ketDrawingDistReqDTO.setOid(ketDrawingDistReqDTO.getDrawingDistRequestOid());
	ketDrawingDistReqDTO = DrawingDistHelper.service.getDistDeptList(ketDrawingDistReqDTO);

	model.addAttribute("distDeptName", ketDrawingDistReqDTO.getDistDeptName());
	model.addAttribute("fileName", ketDrawingDistReqDTO.getFileName());
	model.addAttribute("drawingDistRequestOid", ketDrawingDistReqDTO.getDrawingDistRequestOid());
    }

    @RequestMapping("/drawingDistRequestSearchList")
    public void drawingDistRequestSearchList() throws Exception {
    }

    @RequestMapping("/drawingDistRequestUpdateForm")
    public void drawingDistRequestUpdateForm(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	ketDrawingDistReqDTO = DrawingDistHelper.service.detailView(ketDrawingDistReqDTO);
	List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.getDistReqEpmDocList(ketDrawingDistReqDTO);
	List<Map<String, Object>> distReqWtDocList = DrawingDistHelper.service.getDistReqWtDocList(ketDrawingDistReqDTO);
	ketDrawingDistReqDTO = DrawingDistHelper.service.getDistDeptList(ketDrawingDistReqDTO);
	List<Map<String, Object>> drawingDownHisList = DrawingDistHelper.service.getDrawingDownHisry(ketDrawingDistReqDTO);

	model.addAttribute("drawingDistRequestOid", ketDrawingDistReqDTO.getOid());
	model.addAttribute("drawingDownHisList", drawingDownHisList);
	model.addAttribute("ketDrawingDistRequest", ketDrawingDistReqDTO);
	model.addAttribute("distReqEpmDocList", distReqEpmDocList);
	model.addAttribute("distReqWtDocList", distReqWtDocList);
    }

    @RequestMapping("/drawingDistRequestUpdate")
    public void drawingDistRequestUpdate(KETDrawingDistReqDTO ketDrawingDistReqDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	KETDrawingDistRequest ketDrawingDistRequest = DrawingDistHelper.service.modify(ketDrawingDistReqDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");" + "\n parent.opener.drawingDistRequest.search();"
	        + "\n" + "\n parent.location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=" + ketDrawingDistRequest.toString()
	        + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
    }

    @RequestMapping("/drawingDistRequestViewForm")
    public void drawingDistRequestViewForm(KETDrawingDistReqDTO ketDrawingDistReqDTO, String isIframe, Model model) throws Exception {
	ketDrawingDistReqDTO = DrawingDistHelper.service.detailView(ketDrawingDistReqDTO);
	List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.getDistReqEpmDocList(ketDrawingDistReqDTO);
	List<Map<String, Object>> distReqWtDocList = DrawingDistHelper.service.getDistReqWtDocList(ketDrawingDistReqDTO);
	ketDrawingDistReqDTO = DrawingDistHelper.service.getDistDeptList(ketDrawingDistReqDTO);
	List<Map<String, Object>> drawingDownHisList = DrawingDistHelper.service.getDrawingDownHisry(ketDrawingDistReqDTO);

	model.addAttribute("drawingDistRequestOid", ketDrawingDistReqDTO.getOid());
	model.addAttribute("drawingDownHisList", drawingDownHisList);
	model.addAttribute("ketDrawingDistRequest", ketDrawingDistReqDTO);
	model.addAttribute("distReqEpmDocList", distReqEpmDocList);
	model.addAttribute("distReqWtDocList", distReqWtDocList);
	// 결재대상화면 여부 파라미터 추가
	model.addAttribute("isIframe", Boolean.parseBoolean(StringUtil.checkReplaceStr(isIframe, "false")));
    }

    @RequestMapping("/drawingDistRequestDelete")
    public void drawingDistRequestDelete(KETDrawingDistReqDTO ketDrawingDistReqDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	DrawingDistHelper.service.delete(ketDrawingDistReqDTO);
	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "삭제되었습니다." + "\");" + "\n parent.opener.drawingDistRequest.search();"
	        + "\n" + "\n parent.window.close(); \n </script>";
	pwriter.println(str);
	pwriter.close();
    }

    @RequestMapping("/drawingDistRequestCreateForm")
    public void drawingDistRequestCreateForm() throws Exception {
    }

    @RequestMapping("/drawingDistTotalDown")
    public void drawingDistTotalDown(String oid, HttpServletResponse response, HttpServletRequest request) throws Exception {
	String totalDownUrl = DrawingDistHelper.service.drawingDistTotalDown(oid);

	if (totalDownUrl.equals("") || totalDownUrl == null) {
	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\"" + "다운로드할 파일이 없습니다." + "\"); \n "
		    + " setTimeout(function(){parent.location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=" + oid + "';}, 500);"
		    + "\n </script>";
	    pwriter.println(str);
	    pwriter.close();
	} else {
	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    // parent.window.open(downUrl,"download","width=0, height=0");
	    str = "\n <script language=\"javascript\"> \n parent.window.open('" + totalDownUrl
		    + "',\"totalDownload\",\"width=0, height=0\");" + "\n "
		    + " setTimeout(function(){parent.location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=" + oid + "';}, 1000);"
		    + "\n </script>";

	    pwriter.println(str);
	    pwriter.close();
	}

	return;
    }

    @RequestMapping("/drawingDistRequestCreate")
    public void drawingDistRequestCreate(KETDrawingDistReqDTO ketDrawingDistReqDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	KETDrawingDistRequest ketDrawingDistRequest = DrawingDistHelper.service.save(ketDrawingDistReqDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");" + "\n parent.opener.drawingDistRequest.search();"
	        + "\n" + "\n parent.location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=" + ketDrawingDistRequest.toString()
	        + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/drawingDistRequestReApproved")
    public void drawingDistRequestReApproved(KETDrawingDistReqDTO ketDrawingDistReqDTO, HttpServletResponse response,
	    HttpServletRequest request) throws Exception {
	KETDrawingDistRequest ketDrawingDistRequest = DrawingDistHelper.service.ReApproved(ketDrawingDistReqDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "재승인(TEST용)되었습니다." + "\");"
	        + "\n parent.opener.drawingDistRequest.search();" + "\n"
	        + "\n parent.location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid=" + ketDrawingDistRequest.toString() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {
	if (ketDrawingDistReqDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = DrawingDistHelper.service.findPaging(ketDrawingDistReqDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();
	List<KETDrawingDistRequest> list = new ArrayList<KETDrawingDistRequest>();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    list.add((KETDrawingDistRequest) objArr[0]);
	}
	List<KETDrawingDistReqDTO> ketDrawingDistReqDTOList = new ArrayList<KETDrawingDistReqDTO>();
	for (KETDrawingDistRequest vo : list) {
	    ketDrawingDistReqDTOList.add(new KETDrawingDistReqDTO(vo));
	}
	return EjsConverUtil.convertToDto(ketDrawingDistReqDTOList, pageControl);
    }

    @RequestMapping("/saveReason")
    @ResponseBody
    public String saveReason(KETDrawingDistReqDTO ketDrawingDistReqDTO, Model model) throws Exception {
	KETDrawingDownHis ketDrawingDownHis = DrawingDistHelper.service.saveReason(ketDrawingDistReqDTO);
	return "OK";
    }

    @RequestMapping("/findPagingObjectList")
    @ResponseBody
    public Map<String, Object> findPagingObjectList(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {

	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = drawingDistFindService.findPagingSql(ketDrawingDistReqDTO);

	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    @RequestMapping("/saveEdmBatch")
    public void saveEdmBatch(Model model) throws Exception {

	String divisionCode = "CA";
	if (CommonUtil.isMember("전자사업부")) {
	    divisionCode = "ER";
	}

	Map<String, Object> parameter = new HashMap<String, Object>();
	parameter.put("locale", Locale.KOREAN);
	parameter.put("codeType", "DIVISIONTYPE");
	parameter.put("myCode", divisionCode);

	List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

	String businessTypeOid = "";
	for (Map<String, Object> mapIn : codeList) {
	    businessTypeOid = (String) mapIn.get("ida2a2");
	}
	model.addAttribute("manageType", "MOLD_DRAWING");
	model.addAttribute("businessType", businessTypeOid);
    }

    @ResponseBody
    @RequestMapping("/uploadBatchMold")
    private Map<String, Object> uploadBatchMold(@RequestParam Map<String, Object> reqMap, MultipartHttpServletRequest filelist) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    reqMap.put("fileList", filelist.getFiles("files"));
	    reqMap.put("templateFile", filelist.getFile("templatefile"));

	    resMap = EpmBaseHelper.service.uploadBatchMold(reqMap);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }
}
