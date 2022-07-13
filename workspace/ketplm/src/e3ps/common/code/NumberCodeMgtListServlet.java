/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : NumberCodeMgtListServlet.java
 * 작성자 : 남현승
 * 작성일자 : 2010. 9. 7.
 */
package e3ps.common.code;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : NumberCodeMgtListServlet 설명 : 협력사 정보 조회 서블릿 작성자 : 남현승 작성일자 : 2013. 07. 05.
 */
public class NumberCodeMgtListServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	Kogger.debug(getClass(), "----------------------최종사용,고객처,납입처,생산처\n 서블릿시작");

	// 최종사용,고객처,납입처,생산처
	search(req, res);

	Kogger.debug(getClass(), "---------------------- 최종사용,고객처,납입처,생산처 \n 서블릿종료");
    }

    /**
     * 함수명 : search 설명 : 최종사용,고객처,납입처,생산처
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 남현승 작성일자 : 2013. 07. 05.
     */
    @SuppressWarnings("unchecked")
    private void search(HttpServletRequest req, HttpServletResponse res) {

	KETMessageService messageService = KETMessageService.getMessageService(req);

	// 커넥션
	StringBuffer strBuffer = new StringBuffer();
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	Hashtable map = uploader.getFormParameters();

	String codetype = StringUtil.trimToEmpty(req.getParameter("codetype"));
	String parentOid = StringUtil.checkNull(req.getParameter("parentOid"));
	String numberCode = StringUtil.trimToEmpty(req.getParameter("numberCode"));

	String depth = StringUtil.trimToEmpty(req.getParameter("depth"));
	String designTeam = StringUtil.trimToEmpty(req.getParameter("designTeam"));
	String command = StringUtil.trimToEmpty(req.getParameter("command"));
	String mode = StringUtil.trimToEmpty(req.getParameter("mode"));
	String invokeMethod = StringUtil.trimToEmpty(req.getParameter("invokeMethod"));
	String valueField = StringUtil.trimToEmpty(req.getParameter("valueField"));
	String displayField = StringUtil.trimToEmpty(req.getParameter("displayField"));

	String sc_type = req.getParameter("sc_type");
	String sc_name = req.getParameter("sc_name");
	String sc_value = req.getParameter("sc_value");
	String disable = req.getParameter("disable");

	if (depth != null && depth.length() == 0)
	    depth = "0";

	if (sc_type == null) {
	    sc_type = "";
	}
	if (sc_name == null) {
	    sc_name = "";
	}
	if (sc_value == null) {
	    sc_value = "";
	}
	if (disable == null) {
	    disable = "";
	}

	if (parentOid != null && parentOid.length() == 0 && numberCode.length() > 0) {
	    Kogger.debug(getClass(), "parentOid.length() == 0  && numberCode.length() > 0   ");
	    NumberCode defaultCode = NumberCodeHelper.manager.getNumberCode(codetype, numberCode);
	    if (defaultCode != null && defaultCode.getParent() != null) {
		Kogger.debug(getClass(), "defaultCode != null && defaultCode.getParent() != null");
		parentOid = (defaultCode.getParent()).getPersistInfo().getObjectIdentifier().getStringValue();
	    }
	}

	Connection conn = null;
	List<Map<String, Object>> conditionList = null;
	conditionList = new ArrayList<Map<String, Object>>();

	try {

	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao dao = new NumberCodeDao(conn);

	    map.put("type", codetype);
	    if (sc_type.length() > 0)
		map.put("wctype", sc_type);
	    if (sc_name.length() > 0)
		map.put("code", sc_name);
	    if (sc_value.length() > 0)
		map.put("name", sc_value);
	    if (designTeam.length() > 0)
		map.put("vender", designTeam);
	    if (disable.length() > 0)
		map.put("disable", disable);

	    if (parentOid.equals("null") || parentOid == null || parentOid.equals("")) {
		map.put("isParent", "false");
	    } else {
		map.put("isParent", "true");
	    }

	    if ((parentOid.equals("null") || parentOid == null || parentOid.equals("")) && codetype.equals("CUSTOMEREVENT")) {
		// 초기 검색 불가하도록 셋팅함.
		map.put("name", "d가");
	    } else if (parentOid.length() == 0) {

	    } else {
		map.put("parent", parentOid);
	    }

	    map.put("lang", messageService.getLocale());
	    conditionList.add(map);

	    List<Map<String, Object>> list = dao.getNumberCodeListPopup(conditionList);

	    for (Map<String, Object> codelist : list) {

		String description = StringUtil.checkNull((String) codelist.get("desc"));
		String descriptionLeft = StringUtil.getLeft(description, 13);

		String div = (String) ((codelist.get("vendercode") == null) ? "" : codelist.get("vendercode"));

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" oid=&quot;" + codelist.get("oid") + "&quot;"); // oid
		strBuffer.append(" codelong=&quot;" + codelist.get("ida2a2") + "&quot;"); // codelong
		strBuffer.append(" Code=&quot;" + codelist.get("code") + "&quot;"); // 이름
		strBuffer.append(" Name=&quot;" + codelist.get("name") + "&quot;"); // 코드
		strBuffer.append(" Description=&quot;" + descriptionLeft + "&quot;"); // 설명
		strBuffer.append(" Division=&quot;" + div + "&quot;"); // 사업부
		strBuffer.append("/>");
	    }

	    // 검색조건 셋팅
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건

	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	    req.setAttribute("codetype", codetype);
	    req.setAttribute("parentOid", parentOid);
	    req.setAttribute("depth", depth);
	    req.setAttribute("command", command);
	    req.setAttribute("mode", mode);
	    req.setAttribute("invokeMethod", invokeMethod);
	    req.setAttribute("valueField", valueField);
	    req.setAttribute("displayField", displayField);
	    req.setAttribute("designTeam", designTeam);
	    req.setAttribute("pnumbercodetype", codetype);
	    req.setAttribute("disable", disable);

	    // 검색화면으로 이동
	    gotoResult(req, res, "/jsp/common/code/NumberCodeMgtList.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

}
