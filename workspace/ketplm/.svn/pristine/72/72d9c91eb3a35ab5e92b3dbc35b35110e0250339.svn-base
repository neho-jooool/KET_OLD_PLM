package e3ps.part.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.inf.container.WTContainerHelper;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.PageControl;
import e3ps.part.dao.KETNewPartListDao;
import e3ps.part.entity.KETNewPartList;
import e3ps.part.entity.KETNewPartListTypeLink;
import e3ps.part.service.KETNewPartListHelper;
import ext.ket.shared.log.Kogger;

public class KETNewPartListServlet extends CommonServlet {

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//커넥션
	Connection conn = null;
	try {

	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    String sUser = user.getFullName();

	    String cmd = StringUtil.checkNull(request.getParameter("cmd"));

	    Hashtable<String, String> hash = new Hashtable<String, String>();
	    boolean result;

	    WTUser currentUser = (WTUser) SessionHelper.manager.getPrincipal();
	    WTGroup wtSysGroup = OrganizationServicesHelper.manager.getGroup("Administrators", WTContainerHelper.service.getExchangeContainer().getContextProvider());
	    boolean isSysAdmin = OrganizationServicesHelper.manager.isMember(wtSysGroup, currentUser);
	    WTGroup wtBizGroup = OrganizationServicesHelper.manager.getGroup("Business Administrators", WTContainerHelper.service.getExchangeContainer().getContextProvider());
	    boolean isBizAdmin = OrganizationServicesHelper.manager.isMember(wtBizGroup, currentUser);

	    // 로그인한 사용자가  Admin Group 에 속해 있는지 체크
	    String adminFlag = "disabled";

	    if (isSysAdmin || isBizAdmin) {
		adminFlag = "able";
	    }


	    if ("create".equals(cmd)) {
		hash.put("partNumber", request.getParameter("partCode") + request.getParameter("partNumber"));
		hash.put("partName", request.getParameter("partName"));
		hash.put("rawMaterial", request.getParameter("rawMaterial"));
		hash.put("customer", request.getParameter("customer"));
		hash.put("register", sUser);
		hash.put("description", request.getParameter("description"));
		hash.put("newparttypeReference", request.getParameter("select0")); // 신규부품유형
		hash.put("newchetypeReference", request.getParameter("select2")); // 채번구분

		result = KETNewPartListHelper.service.createNewPartList(hash);

		alertFn(response, "등록이 완료되었습니다", "parent.doSelect()");

	    }
	    else if ("select".equals(cmd)) {
		// 커넥션 생성
		conn = PlmDBUtil.getConnection();

		KETNewPartListDao ketNewPartListDao = new KETNewPartListDao(conn);

		Kogger.debug(getClass(), "###########################" + request.getParameter("select0"));
		Kogger.debug(getClass(), "###########################" + request.getParameter("select2"));
		hash.put("newparttypeReference", request.getParameter("select0")); // 신규부품유형
		hash.put("newchetypeReference", request.getParameter("select2")); // 채번구분

		//검색 페이지
		int page = StringUtil.getIntParameter(StringUtil.checkNull(request.getParameter("page")), 1);
		//페이지 당 목록수
		int perPage = StringUtil.getIntParameter(StringUtil.checkNull(request.getParameter("perPage")), 6);

		int startRow = (page - 1) * perPage + 1;
		int endRow = page * perPage;

		hash.put("startRow", startRow + ""); // 채번구분
		hash.put("endRow", endRow + ""); // 채번구분

		String newparttypeReference = request.getParameter("select0") + "";
		String newchetypeReference = "NEWPRODUCTTYPE";

		if ("PT001".equals(newparttypeReference)) {
		    newchetypeReference = "NEWPRODUCTTYPE";
		}
		else {
		    newchetypeReference = "NEWDIETYPE";
		}

		NumberCode partListTypeCode2 = NumberCodeHelper.manager.getNumberCode(newchetypeReference, (String) request.getParameter("select2"));
		String spartListTypeCode2 = NumberCodeHelper.manager.getNumberCodeOid(newchetypeReference, (String) request.getParameter("select2"));

		Kogger.debug(getClass(), "넘버 코드는 = " + partListTypeCode2);
		hash.put("spartListTypeCode2", spartListTypeCode2.substring(31));

		Hashtable<String, KETNewPartList> hashList = ketNewPartListDao.getKETNewPartListDao(hash);

		//목록 결과 갯수
		int listCnt = PersistenceHelper.manager.navigate(partListTypeCode2, "newpartlist", KETNewPartListTypeLink.class).size();
		//페이지 정보
		PageControl pageControl = new PageControl(page, 10, perPage, listCnt);
		pageControl.setPostMethod();
		pageControl.setHref("/plm/servlet/e3ps/KETNewPartListServlet");

		request.setAttribute("condition", hash);
		request.setAttribute("partList", hashList);
		request.setAttribute("adminFlag", adminFlag);
		//페이지 정보 셋팅
		request.setAttribute("control", pageControl);

		ketNewPartListDao = null;
		gotoResult(request, response, "/jsp/part/CreatePartList.jsp");

	    }
	    else if ("selectPop".equals(cmd)) {
		// 커넥션 생성
		conn = PlmDBUtil.getConnection();

		KETNewPartListDao ketNewPartListDao = new KETNewPartListDao(conn);

		hash.put("newparttypeReference", request.getParameter("select0")); // 신규부품유형
		hash.put("newchetypeReference", request.getParameter("select2")); // 채번구분

		//검색 페이지
		int page = StringUtil.getIntParameter(StringUtil.checkNull(request.getParameter("page")), 1);
		//페이지 당 목록수
		int perPage = StringUtil.getIntParameter(StringUtil.checkNull(request.getParameter("perPage")), 10);

		int startRow = (page - 1) * perPage + 1;
		int endRow = page * perPage;

		hash.put("startRow", startRow + ""); // 채번구분
		hash.put("endRow", endRow + ""); // 채번구분

		String newparttypeReference = request.getParameter("select0") + "";
		String newchetypeReference = "NEWPRODUCTTYPE";

		if ("PT001".equals(newparttypeReference)) {
		    newchetypeReference = "NEWPRODUCTTYPE";
		}
		else {
		    newchetypeReference = "NEWDIETYPE";
		}

		NumberCode partListTypeCode2 = NumberCodeHelper.manager.getNumberCode(newchetypeReference, (String) request.getParameter("select2"));
		String spartListTypeCode2 = NumberCodeHelper.manager.getNumberCodeOid(newchetypeReference, (String) request.getParameter("select2"));

		hash.put("spartListTypeCode2", spartListTypeCode2.substring(31));

		Hashtable<String, KETNewPartList> hashList = ketNewPartListDao.getKETNewPartListPopDao(hash);

		//목록 결과 갯수
		int listCnt = hashList.size();
		//페이지 정보
		PageControl pageControl = new PageControl(page, 10, perPage, listCnt);
		pageControl.setPostMethod();
		pageControl.setHref("/plm/servlet/e3ps/KETNewPartListServlet");

		request.setAttribute("condition", hash);
		request.setAttribute("partList", hashList);
		request.setAttribute("adminFlag", adminFlag);
		//페이지 정보 셋팅
		request.setAttribute("control", pageControl);

		ketNewPartListDao = null;
		gotoResult(request, response, "/jsp/part/CreatePart_pop2.jsp?select=no");

	    }
	    else if ("update".equals(cmd)) {

		hash.put("oId", request.getParameter("oId"));
		hash.put("partNumber", request.getParameter("partCode") + request.getParameter("partNumber"));
		hash.put("partName", request.getParameter("partName"));
		hash.put("rawMaterial", request.getParameter("rawMaterial"));
		hash.put("customer", request.getParameter("customer"));
		hash.put("modifier", sUser);
		hash.put("description", request.getParameter("description"));
		hash.put("del", request.getParameter("del"));

		result = KETNewPartListHelper.service.updateNewPartList(hash);

		alertFn(response, "수정이 완료되었습니다", "parent.doSelect()");

	    }
	    else if ("deleteReal".equals(cmd)) {

		String oIdList[] = request.getParameterValues("checkbox");
		boolean isDelete = true;

		for (int ii = 0; ii < oIdList.length; ii++) {
		    isDelete = KETNewPartListHelper.service.deleteRealNewPartList(oIdList[ii]);
		}

		if (isDelete) {
		    alertFn(response, "정상적으로 처리되었습니다.", "parent.doSelect()");
		}
		else {
		    alertFn(response, "실패하였습니다.", "parent.doSelect()");
		}
	    }
	    else if ("delete".equals(cmd)) {

		String oIdList[] = request.getParameterValues("checkbox");
		boolean isDelete = true;

		for (int ii = 0; ii < oIdList.length; ii++) {
		    isDelete = KETNewPartListHelper.service.deleteNewPartList(oIdList[ii]);
		}

		if (isDelete) {
		    alertFn(response, "정상적으로 처리되었습니다.", "parent.doSelect()");
		}
		else {
		    alertFn(response, "실패하였습니다.", "parent.doSelect()");
		}
	    }
	    else if ("excel".equals(cmd)) {
		// 커넥션 생성
		conn = PlmDBUtil.getConnection();

		KETNewPartListDao ketNewPartListDao = new KETNewPartListDao(conn);

		Kogger.debug(getClass(), "###########################" + request.getParameter("select0"));
		Kogger.debug(getClass(), "###########################" + request.getParameter("select2"));
		hash.put("newparttypeReference", request.getParameter("select0")); // 신규부품유형
		hash.put("newchetypeReference", request.getParameter("select2")); // 채번구분

		//검색 페이지
		int page = StringUtil.getIntParameter(StringUtil.checkNull(request.getParameter("page")), 1);
		//페이지 당 목록수
		int perPage = StringUtil.getIntParameter("9999", 6);

		int startRow = (page - 1) * perPage + 1;
		int endRow = page * perPage;

		hash.put("startRow", startRow + ""); // 채번구분
		hash.put("endRow", endRow + ""); // 채번구분

		String newparttypeReference = request.getParameter("select0") + "";
		String newchetypeReference = "NEWPRODUCTTYPE";

		if ("PT001".equals(newparttypeReference)) {
		    newchetypeReference = "NEWPRODUCTTYPE";
		}
		else {
		    newchetypeReference = "NEWDIETYPE";
		}

		NumberCode partListTypeCode2 = NumberCodeHelper.manager.getNumberCode(newchetypeReference, (String) request.getParameter("select2"));
		String spartListTypeCode2 = NumberCodeHelper.manager.getNumberCodeOid(newchetypeReference, (String) request.getParameter("select2"));

		Kogger.debug(getClass(), "넘버 코드는 = " + partListTypeCode2);
		hash.put("spartListTypeCode2", spartListTypeCode2.substring(31));

		Hashtable<String, KETNewPartList> hashList = ketNewPartListDao.getKETNewPartListDao(hash);

		//목록 결과 갯수
		int listCnt = PersistenceHelper.manager.navigate(partListTypeCode2, "newpartlist", KETNewPartListTypeLink.class).size();
		//페이지 정보
		PageControl pageControl = new PageControl(page, 10, perPage, listCnt);
		pageControl.setPostMethod();
		pageControl.setHref("/plm/servlet/e3ps/KETNewPartListServlet");

		request.setAttribute("condition", hash);
		request.setAttribute("partList", hashList);
		request.setAttribute("adminFlag", adminFlag);
		//페이지 정보 셋팅
		request.setAttribute("control", pageControl);

		ketNewPartListDao = null;
		gotoResult(request, response, "/jsp/part/CreatePartExcel.jsp");
	    }
	} catch (WTException e1) {
	    Kogger.error(getClass(), e1);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (conn != null) {
		//커넥션 종료
		PlmDBUtil.close(conn);
	    }
	}
    }

    private String replaceMsg(String msg) {
	msg = msg.replaceAll("\"", "&quot;");
	return msg.replaceAll("\n", " ");
    }

    protected void alertFn(HttpServletResponse res, String msg, String fn) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");\n   " + fn + ";" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
