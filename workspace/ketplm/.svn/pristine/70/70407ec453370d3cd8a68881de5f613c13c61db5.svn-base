package e3ps.groupware.board.servlet;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import wt.fc.PersistenceHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.groupware.board.HelpBoard;
import e3ps.groupware.board.HelpBoardData;
import e3ps.groupware.board.ImproveBoard;
import e3ps.groupware.board.beans.HelpBoardUtil;
import e3ps.groupware.board.dao.HelpBoardDao;
import e3ps.groupware.board.dao.ImproveBoardDao;

/*
 * [PLM System 1차개선]
 * 파일명 : ManageImproveBoardServlet.java
 * 설명 : 시스템개선요청 Board용 공통 servlet
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ManageImproveBoardServlet extends ManageHelpBoardServlet {

    /**
     * 게시판 테이블 명 반환
     * 
     * @return
     */
    @Override
    protected String getBoardTableName() {
	return "ImproveBoard";
    }

    /**
     * servlet 자신의 경로 반환
     * 
     * @return
     */
    @Override
    protected String getServletPath() {
	return "/plm/servlet/e3ps/ManageImproveBoardServlet";
    }

    @Override
    protected String getViewPagePath(String oid) {
	ImproveBoard board = (ImproveBoard) CommonUtil.getObject(oid);
	if (board.getParent() != null) {
	    return "/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=" + oid;
	} else {
	    String childOid = getLastChildOid(oid);
	    if (childOid != null) {
		return "/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=" + childOid;
	    } else {
		return "/plm/jsp/groupware/help/improve/ViewImproveBoard_q.jsp?oid=" + oid;
	    }
	}
    }

    /**
     * 검색결과를 나타낼 페이지 경로 반환
     * 
     * @return
     */
    @Override
    protected String getBoardListPagePath() {
	return "/jsp/groupware/help/improve/SearchImproveBoard.jsp";
    }

    /**
     * 게시판 DAO 반환
     * 
     * @param conn
     *            - JDBC 연결 객체
     * @return
     */
    @Override
    protected HelpBoardDao getDao(Connection conn) {
	return new ImproveBoardDao(conn);
    }

    /**
     * 게시판 글 객체 생성 및 request로부터 값 설정
     * 
     * @param reqParam
     *            - request 파라미터
     * @return 게시판 글 객체
     * @throws WTPropertyVetoException
     * @throws WTException
     */
    @Override
    protected HelpBoard newBoard(Map<String, Object> _map) throws WTPropertyVetoException, WTException {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);
	ImproveBoard newBoard = ImproveBoard.newImproveBoard();

	String classification = map.getString("classification");
	String state = map.getString("state");
	String division = map.getString("division");

	if (StringUtil.checkString(classification)) {
	    newBoard.setClassification(classification);
	}
	if (StringUtil.checkString(state)) {
	    newBoard.setState(state);
	}
	if (StringUtil.checkString(division)) {
	    newBoard.setDivision(division);
	}

	String poid = map.getString("poid");
	if (StringUtil.isEmpty(poid) == false && HelpBoardUtil.isValidOid(poid)) {
	    ImproveBoard parent = (ImproveBoard) CommonUtil.getObject(poid);
	    if (parent != null) {
		if (StringUtil.checkString(state))
		    parent.setState(state);
		int preferred = map.getInt("preferred");
		parent.setPreferred(preferred);
		parent = (ImproveBoard) PersistenceHelper.manager.save(parent);

		newBoard.setClassification(parent.getClassification()); // 질문과 동일한 분류 가짐 (검색 결과 포함 위해)
		newBoard.setParent(parent);
		newBoard.setDepth(parent.getDepth() + 1);
	    }
	}

	return newBoard;
    }

    @Override
    protected void modifyBoard(HelpBoard hboard, Map<String, Object> _map) throws WTPropertyVetoException, WTException {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);
	ImproveBoard board = (ImproveBoard) hboard;

	String classification = map.getString("classification");
	String state = map.getString("state");
	String division = map.getString("division");

	if (StringUtil.checkString(classification)) {
	    board.setClassification(classification);
	}
	if (StringUtil.checkString(state)) {
	    board.setState(state);
	}
	if (StringUtil.checkString(division)) {
	    board.setDivision(division);
	}

	String poid = map.getString("poid");
	if (StringUtil.isEmpty(poid) == false && HelpBoardUtil.isValidOid(poid)) {
	    ImproveBoard parent = (ImproveBoard) CommonUtil.getObject(poid);
	    if (parent != null) {
		if (StringUtil.checkString(classification)) {
		    parent.setClassification(classification);
		}
		if (StringUtil.checkString(state)) {
		    parent.setState(state);
		}
		// if (StringUtil.checkString(division)) {
		// parent.setDivision(division);
		// }
		int preferred = map.getInt("preferred");
		parent.setPreferred(preferred);
		parent = (ImproveBoard) PersistenceHelper.manager.save(parent);
	    }
	}
    }

    @Override
    protected void processParentAfterDeleteBoard(HelpBoard _parent) throws WTPropertyVetoException, WTException {
	// 처리 삭제 시 질문등록 상태로 변경
	ImproveBoard parent = (ImproveBoard) _parent;
	parent.setState(HelpBoardData.STATE_REGISTERED);
	parent.setPreferred(0);
	parent = (ImproveBoard) PersistenceHelper.manager.save(parent);
    }

    @Override
    protected void appendAllGridRow(List<SqlRowMap> recordList, int cnt, TreeGridStringBuffer strBuffer) throws Exception {

	for (SqlRowMap record : recordList) {
	    String parentOid = record.getString("parentOid");
	    boolean hasParent = HelpBoardUtil.isValidOid(parentOid);

	    int docNumber = cnt;
	    String title = record.getString("title");
	    // String classification = (hasParent)?"":record.getString("classification");
	    // String state = (hasParent)?"":record.getString("state");
	    // String division = record.getString("division");
	    String writer = record.getString("writer");
	    Timestamp writeDate = record.getTimestamp("writeDate");

	    String classification1Value = record.getStringNotNull("classification1Value");
	    String classificationValue = record.getStringNotNull("classificationValue");
	    String stateValue = record.getStringNotNull("stateValue");
	    String divisionValue = record.getStringNotNull("divisionValue");

	    int readCount = record.getInt("readCount");
	    int depth = record.getInt("depth");
	    boolean preferred = (record.getInt("preferred") == 1);
	    String docOid = record.getString("docOid");
	    String viewJsp;
	    if (hasParent) {
		viewJsp = "/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=" + docOid + "&from=list";
	    } else {
		String childOid = getLastChildOid(docOid);
		if (childOid != null) {
		    viewJsp = "/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=" + childOid + "&from=list";
		} else {
		    viewJsp = "/plm/jsp/groupware/help/improve/ViewImproveBoard_q.jsp?oid=" + docOid + "&from=list";
		}
	    }
	    String indent = "";
	    String replyIcon = "";
	    boolean isNew = HelpBoardUtil.isNewDoc(writeDate);
	    String newIcon = (isNew) ? "&nbsp;<img src='" + e3ps.common.web.E3PSWebFlag.ICONURL + "/i_new.gif'/>" : "";

	    // 시스템 개선 요청 처리일 경우 데이터 처리
	    if (depth > 0) {
		for (int i = 1; i < depth; ++i) {
		    indent = "&nbsp;&nbsp;";
		}
		replyIcon = "&nbsp;<img src='/plm/portal/images/img_default/button/icnrply1.gif'/>&nbsp;";
		title = "Re: " + title;
		classification1Value = "";
		classificationValue = "";
		// divisionValue = "";
		stateValue = "";
	    }

	    String docNumberStr;
	    if (preferred) {
		title = "<b>" + title + "</b>";
	    }

	    if (depth > 0 || preferred) {
		docNumberStr = "";
	    } else {
		docNumberStr = String.valueOf(docNumber);
		cnt--;
	    }

	    String titleFinal = indent + replyIcon + title + newIcon;

	    strBuffer.append("<I ");
	    strBuffer.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
	    strBuffer.appendRepl(" DocNumber=\"" + docNumberStr + "\"");
	    strBuffer.appendRepl(" Classification1=\"" + classification1Value + "\"");
	    strBuffer.appendRepl(" Classification=\"" + classificationValue + "\"");
	    strBuffer.appendRepl(" Division=\"" + divisionValue + "\"");
	    strBuffer
		    .appendRepl(" Title=\"")
		    .appendContent(titleFinal)
		    .appendRepl(
		            "\"" + " TitleCanSelect=\"2\"" + " TitleType=\"Html\"" + " TitleOnClick=\"javascript:go_to('" + viewJsp
		                    + "');\"" + " TitleExportValue=\"")
		    .appendContent(record.getString("title"))
		    .appendRepl(
		            "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
		                    + "'>\" TitleHtmlPostfix=\"</font>\"");
	    strBuffer.appendRepl(" Status=\"" + stateValue + "\"");
	    strBuffer.appendRepl(" Writer=\"" + writer + "\"");
	    strBuffer.appendRepl(" WriteDate=\"" + DateUtil.getDateString(writeDate, "d") + "\"");
	    strBuffer.appendRepl(" ReadCount=\"" + readCount + "\"");
	    strBuffer.append("/>");
	}
    }

}
