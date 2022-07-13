/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : WFBomViewServlet.java
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 23.
 */
package e3ps.bom.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.part.WTPart;
import e3ps.bom.dao.WFBomViewDao;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : WFBomViewServlet
 * 설명 : 결재대상 BOM 구조 서블릿
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 23.
 */
public class WFBomViewServlet extends CommonServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String cmd = ParamUtil.getParameter(req, "cmd");
	Kogger.debug(getClass(), "cmd : " + cmd);
	if (cmd.equals("search")) {
	    search(req, res);
	} else if (cmd.equals("bomeco_search")) {
	    bomeco_search(req, res);
	}
    }

    /**
     * 함수명 : search
     * 설명 : 결재대상 BOM 구조 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res
     *            작성자 : 신대범
     *            작성일자 : 2010. 11. 23.
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	Kogger.debug(getClass(), "----------------------서블릿시작");
	Connection conn = null;
	try {
	    // Form 데이터 받기
	    ServletContext context = getServletContext();
	    FormUploader uploader = FormUploader.newFormUploader(req, res, context);
	    Hashtable hash = uploader.getFormParameters();

	    conn = PlmDBUtil.getConnection();
	    WFBomViewDao wfbomviewDao = new WFBomViewDao(conn);

	    ArrayList list = wfbomviewDao.ViewWFBomList(hash);

	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	    String strType = "";
	    try {
		String headCode = wfbomviewDao.ViewWFBomHeadStr((String) hash.get("wtcode"));
		WTPart part = kh.searchItem(headCode);
		strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // BOMFlag
		hash.put("part_type", strType);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    req.setAttribute("condition", hash);
	    req.setAttribute("list", list);
	    gotoResult(req, res, "/jsp/bom/WFBomView.jsp");

	    Kogger.debug(getClass(), "----------------------서블릿종료");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : bomeco_search
     * 설명 : 결재대상 BOMECO 구조 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res
     *            작성자 : 신대범
     *            작성일자 : 2010. 12. 06.
     */
    private void bomeco_search(HttpServletRequest req, HttpServletResponse res) {

	Kogger.debug(getClass(), "----------------------서블릿시작2");
	Connection conn = null;
	try {
	    // Form 데이터 받기
	    ServletContext context = getServletContext();
	    FormUploader uploader = FormUploader.newFormUploader(req, res, context);
	    Hashtable hash = uploader.getFormParameters();

	    conn = PlmDBUtil.getConnection();
	    WFBomViewDao wfbomviewDao = new WFBomViewDao(conn);

	    ArrayList list = wfbomviewDao.ViewWFBomEcoList(hash);

	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	    String strType = "";
	    try {
		Hashtable ht = wfbomviewDao.ViewWFBomEcoHeadStr((String) hash.get("wtcode"));
		String headCode = (String) ht.get("headitem");
		WTPart part = kh.searchItem(headCode);
		strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);  // BOMFlag
		hash.put("part_type", strType);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    req.setAttribute("condition", hash);
	    req.setAttribute("list", list);
	    gotoResult(req, res, "/jsp/bom/WFBomEcoView.jsp");

	    Kogger.debug(getClass(), "----------------------서블릿종료");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }
}
