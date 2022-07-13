package ext.ket.yesone.controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import e3ps.common.util.DateUtil;
import e3ps.cost.util.DBUtil;
import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.entity.KETYesoneDTO;
import ext.ket.yesone.service.YesoneHelper;
import ext.ket.yesone.service.internal.dao.YesoneDao;

/**
 * @클래스명 : YesoneController
 * @작성자 : 황정태
 * @작성일 : 2015. 12. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/yesone/*")
public class YesoneController {

	@RequestMapping("/pdfUpload")
	public void pdfUpload(KETYesoneDTO ketyesonedto, HttpServletResponse response, HttpServletRequest request) throws Exception {

		boolean pdfValid = false;
		String validityMsg = null;
		String returnMsg = null;
		if (StringUtils.isEmpty(ketyesonedto.getEmpNo())) {
			validityMsg = "사번정보가 없습니다.열려진 창을 모두 닫고 재접속바랍니다.";
		}

		if (StringUtils.isNotEmpty(ketyesonedto.getEmpNo())) {
			try {

				if ("Y".equals(ketyesonedto.getDifferentYn())) {
					pdfValid = true;
				} else {
					ketyesonedto = YesoneHelper.service.validityCheckPDF(ketyesonedto);// PDF 검증
					validityMsg = ketyesonedto.getValidityMsg(); // 검증 메세지

					if (StringUtils.isEmpty(validityMsg)) {
						pdfValid = true;
					}
				}

			} catch (Exception e) {

				// ExceptionUtils.getFullStackTrace(e);
				validityMsg = "PDF검증 중 오류가 발생했습니다.";
			}

			try {
				if (!"ok".equals(ketyesonedto.getCreateDto()) && pdfValid) {
					ketyesonedto = YesoneHelper.service.xmlConvert2rdb(ketyesonedto);
					validityMsg = ketyesonedto.getValidityMsg();// 에러 메세지
				}
			} catch (Exception e) {

				ExceptionUtils.getFullStackTrace(e);
				validityMsg = "xml 추출 중 오류가 발생했습니다.";
			}

			returnMsg = "PDF 업로드 완료되었습니다.";

			if (StringUtils.isNotEmpty(validityMsg)) {
				returnMsg = validityMsg;
			}
		}

		String emp_no = ketyesonedto.getEmpNo();

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter pwriter = response.getWriter();

		StringBuilder rtn_msg = new StringBuilder();
		try {

			if (StringUtils.isNotEmpty(validityMsg) && validityMsg.indexOf("_err0101") != -1) {

				validityMsg = validityMsg.substring(0, validityMsg.lastIndexOf("_err0101"));
				validityMsg = "[ " + validityMsg + " ] ⇒";
				rtn_msg.append("\n <script language=\"javascript\">");
				rtn_msg.append("\n   parent.lfn_feedback_reStart('" + validityMsg + "');");
				// rtn_msg.append("\n   parent.hideProcessing();");
				rtn_msg.append("\n </script>");

			} else {

				rtn_msg.append("\n <script language=\"javascript\">\n alert(\"" + returnMsg + "\");");
				rtn_msg.append("\n parent.location.href='/plm/ext/yesone/uploadViewForm.do?emp_no=" + emp_no + "';" + "\n " + "</script>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = "알수없는 오류발생! 관리자에게 문의바랍니다.";
			rtn_msg.append("\n <script language=\"javascript\">\n alert(\"" + returnMsg + "\");");
			rtn_msg.append("\n parent.location.href='/plm/ext/yesone/uploadViewForm.do?emp_no=" + emp_no + "';" + "\n " + "</script>");
		}

		pwriter.println(rtn_msg);
		pwriter.close();
		return;
	}

	@RequestMapping("/uploadViewForm")
	public void uploadViewForm(String emp_no, Model model) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String name = "";
		String chasu = "";

		try {

			KETYesoneBaseDTO baseDto = new KETYesoneBaseDTO();

			String cur_year = Integer.toString(Integer.parseInt(DateUtil.getThisYear()) - 1);
			baseDto.setYear(cur_year);
			baseDto.setEmp_no(emp_no);

			conn = DBUtil.getConnection();

			YesoneDao dao = new YesoneDao(conn, pstmt, rs, baseDto);
			ArrayList<TreeMap<String, String>> CommonCheck = dao.getLoginInfo(baseDto);

			for (TreeMap<String, String> ds : CommonCheck) {

				if ("0".equals(ds.get("REL_CODE"))) {
					name = ds.get("NAME");
					chasu = ds.get("CHASU");
				} else {
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(conn);
		}
		if (StringUtils.isEmpty(chasu)) {
			chasu = "0";
		}
		model.addAttribute("emp_no", emp_no);
		model.addAttribute("name", name);
		model.addAttribute("chasu", chasu);
	}

	@RequestMapping("/yesoneRedirect")
	public String yesoneRedirect(String emp_no, Model model) throws Exception {
		model.addAttribute("emp_no", emp_no);
		return "noExtends:/main/yesoneRedirect";
	}

}
