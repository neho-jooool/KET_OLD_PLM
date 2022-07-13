package ext.ket.sample.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.sample.entity.SampleRequestDTO;
import ext.ket.sample.service.SampleDBService;
import ext.ket.sample.service.SampleHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.EjsConverUtil;

@Controller
@RequestMapping("/sample/*")
public class SampleRequestController {

    @Inject
    private SampleDBService sampleDBService;

    @RequestMapping("/sampleRequestList")
    public void sampleRequestList() throws Exception {
    }

    @RequestMapping("/sampleReceptionViewForm")
    public void sampleReceptionViewForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartReceptionList = SampleHelper.service.samplePartReceptionListOnlyView(sampleRequestDTO);
	List<Map<String, Object>> samplePartReceptionCheckList = SampleHelper.service.samplePartReceptionListView(sampleRequestDTO);

	if ((sampleRequestDTO.getSampleRequestStateCode().equals("REQUEST") || sampleRequestDTO.getSampleRequestStateCode().equals(
	        "REREQUEST"))
	        && samplePartReceptionCheckList.size() != 0) {
	    // 요청 상태에 불출리스트가 있으면 수정할수있도록
	    sampleRequestDTO.setReceptionUpdateFlag(true);
	}

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartReceptionList);
    }

    @RequestMapping("/sampleRequestCreateForm")
    public void sampleRequestCreateForm() throws Exception {
    }

    @RequestMapping("/reRequsetCreateForm")
    public void reRequsetCreateForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartList = SampleHelper.service.reRequsetCreateSamplePartListView(sampleRequestDTO);

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartList);
    }

    @RequestMapping("/reRequsetCreate")
    public void reRequsetCreate(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.sampleRequestSave(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n parent.location.href='/plm/ext/sample/sampleRequstMainForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleRequestUpdateForm")
    public void sampleRequestUpdateForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartList = SampleHelper.service.samplePartListView(sampleRequestDTO);

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartList);
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketSampleRequest));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketSampleRequest));
    }

    @RequestMapping("/sampleRequestViewForm")
    public void sampleRequestViewForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {

	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartList = SampleHelper.service.samplePartListView(sampleRequestDTO);

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartList);
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketSampleRequest));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketSampleRequest));
    }

    @RequestMapping("/printSampleRequestViewForm")
    public void printSampleRequestViewForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {

	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartList = SampleHelper.service.samplePartListView(sampleRequestDTO);

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartList);
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketSampleRequest));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketSampleRequest));
    }

    @RequestMapping("/sampleRequestUpdate")
    public void sampleRequestUpdate(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.sampleRequestUpdate(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search(); } catch(error){ } " + "\n"
	        + "\n parent.location.href='/plm/ext/sample/sampleRequestViewForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleDelete")
    public void sampleDelete(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	sampleRequestDTO = SampleHelper.service.sampleDelete(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "삭제되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n" + "\n parent.parent.window.close();"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;

    }

    @RequestMapping("/sampleReceptionUpdate")
    public void sampleReceptionUpdate(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.samplePartListUpdate(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n parent.location.href='/plm/ext/sample/sampleReceptionUpdateForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/reRequest")
    public void reRequest(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	sampleRequestDTO = SampleHelper.service.reRequest(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "재요청되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n parent.location.href='/plm/ext/sample/sampleReceptionViewForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleComplete")
    public void sampleComplete(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.sampleComplete(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "완료 되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/sample/sampleReceptionViewForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleWorkComplete")
    public void sampleWorkComplete(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.samplePartWorkComplete(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "작업완료 되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/sample/sampleReceptionViewForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleReception")
    public void sampleReception(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.samplePartReception(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "접수되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/sample/sampleReceptionUpdateForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleReceptionUpdateForm")
    public void sampleReceptionUpdateForm(SampleRequestDTO sampleRequestDTO, Model model) throws Exception {
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);

	List<Map<String, Object>> samplePartReceptionList = SampleHelper.service.samplePartReceptionListView(sampleRequestDTO);

	if ((sampleRequestDTO.getSampleRequestStateCode().equals("REQUEST") || sampleRequestDTO.getSampleRequestStateCode().equals(
	        "REREQUEST"))
	        && samplePartReceptionList.size() != 0) {
	    // 요청 상태에 불출리스트가 있으면 수정할수있도록
	    sampleRequestDTO.setReceptionUpdateFlag(true);
	    sampleRequestDTO.setPartListSize(samplePartReceptionList.size());

	    if (samplePartReceptionList.get(0).get("receptionDate").equals(null)
		    || samplePartReceptionList.get(0).get("receptionDate").equals("")) {
		sampleRequestDTO.setReceptionButtonFlag(true);
	    }
	}

	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("samplePartList", samplePartReceptionList);
    }

    @RequestMapping("/sampleRequstMainForm")
    public void sampleRequstMainForm(SampleRequestDTO sampleRequestDTO, String isIframe, Model model) throws Exception {
	if (!StringUtil.checkNull(sampleRequestDTO.getOid()).equals("")) {
	    KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(sampleRequestDTO.getOid());
	    sampleRequestDTO = sampleRequestDTO.SampleRequestDTO(sampleRequestDTO, ketSampleRequest);
	}
	model.addAttribute("sampleRequest", sampleRequestDTO);
	model.addAttribute("isIframe", isIframe);
    }

    @RequestMapping("/sampleRequestCreate")
    public void sampleRequestCreate(SampleRequestDTO sampleRequestDTO, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {
	sampleRequestDTO = SampleHelper.service.sampleRequestSave(sampleRequestDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
	        + "\n try{ parent.parent.opener.sampleRequest.search();  } catch(error){ } " + "\n"
	        + "\n parent.location.href='/plm/ext/sample/sampleRequestViewForm.do?oid=" + sampleRequestDTO.getOid() + "';"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/sampleRequestFindPagingList")
    @ResponseBody
    public Map<String, Object> sampleRequestFindPagingList(SampleRequestDTO sampleRequestDTO) throws Exception {
	//
	// if (sampleRequestDTO.getPage() == 0) {
	// SessionUtil.removeAttribute("pageSessionId");
	// }
	// // methodserver로 부터 데이터를 조회한다.
	// PageControl pageControl = SampleHelper.service.sampleRequestFindPaging(sampleRequestDTO);
	// SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	//
	// // DTO 객체로 변환 한다
	// QueryResult queryResult = pageControl.getResult();
	// List<SampleRequestDTO> sampleRequestDTOList = new ArrayList<SampleRequestDTO>();
	// while (queryResult.hasMoreElements()) {
	// Object[] objArr = (Object[]) queryResult.nextElement();
	// sampleRequestDTOList.add(new SampleRequestDTO((KETSampleRequest) objArr[0]));
	// }
	// return EjsConverUtil.convertToDto(sampleRequestDTOList, pageControl);

	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = sampleDBService.sampleRequsetfindPagingSql(sampleRequestDTO);

	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);

    }

    @RequestMapping("/projectInfo")
    @ResponseBody
    public List<Map<String, String>> projectInfo(String partOid) throws Exception {
	List<Map<String, String>> rslt = SampleHelper.service.projectInfo(partOid);
	return rslt;
    }

    @RequestMapping("/projectPMUserInfo")
    @ResponseBody
    public Map<String, String> projectPMUserInfo(String pjtoid) throws Exception {
	Map<String, String> rslt = SampleHelper.service.projectPMUserInfo(pjtoid);
	return rslt;
    }

}
