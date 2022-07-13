package e3ps.groupware.board.servlet;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.groupware.board.HelpBoard;
import e3ps.groupware.board.ManualBoard;
import e3ps.groupware.board.ManualBoardData;
import e3ps.groupware.board.beans.HelpBoardUtil;
import e3ps.groupware.board.dao.HelpBoardDao;
import e3ps.groupware.board.dao.ManualBoardDao;

/*
 * [PLM System 1차개선]
 * 파일명 : ManageManualBoardServlet.java
 * 설명 : PLM Manual Board용 공통 servlet
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ManageManualBoardServlet extends ManageHelpBoardServlet {

    /**
     * 게시판 테이블 명 반환
     * 
     * @return
     */
    @Override
    protected String getBoardTableName() {
	return "ManualBoard";
    }

    /**
     * servlet 자신의 경로 반환
     * 
     * @return
     */
    @Override
    protected String getServletPath() {
	return "/plm/servlet/e3ps/ManageManualBoardServlet";
    }

    @Override
    protected String getViewPagePath(String oid) {
	return "/plm/jsp/groupware/help/manual/ViewManualBoard.jsp?oid=" + oid;
    }

    /**
     * 검색결과를 나타낼 페이지 경로 반환
     * 
     * @return
     */
    @Override
    protected String getBoardListPagePath() {
	return "/jsp/groupware/help/manual/SearchManualBoard.jsp";
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
	return new ManualBoardDao(conn);
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
	ManualBoard newBoard = ManualBoard.newManualBoard();

	String classification = map.getString("classification");
	String classification2 = map.getString("classification3");
	
	String url = map.getString("url");

	if (StringUtil.checkString(classification))
	    newBoard.setClassification(classification);
	newBoard.setUrl(url);
	if (StringUtil.checkString(classification2))
	    newBoard.setClassification2(classification2);
	return newBoard;
    }

    @Override
    protected void modifyBoard(HelpBoard board, Map<String, Object> _map) throws WTPropertyVetoException, WTException {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);
	ManualBoard mboard = (ManualBoard) board;

	String classification = map.getString("classification");
	String classification2 = map.getString("classification3");
	String url = map.getString("url");

	if (StringUtil.checkString(classification))
	    mboard.setClassification(classification);
	if (StringUtil.checkString(classification2))
	    mboard.setClassification2(classification2);
	mboard.setUrl(url); // 삭제 가능
    }

    @Override
    protected void appendAllGridRow(List<SqlRowMap> recordList, int cnt, TreeGridStringBuffer strBuffer) throws Exception {
	int listCount = recordList.size();

	for (SqlRowMap record : recordList) {
	    int docNumber = (listCount--);
	    String classification = record.getString("classification");
	    String title = record.getString("title");
	    String writer = record.getString("writer");
	    Timestamp writeDate = record.getTimestamp("writeDate");

	    int readCount = record.getInt("readCount");
	    boolean preferred = (record.getInt("preferred") == 1);
	    String docOid = record.getString("docOid");
	    String viewJsp = "/plm/jsp/groupware/help/manual/ViewManualBoard.jsp?oid=" + docOid + "&from=list";
	    boolean isNew = HelpBoardUtil.isNewDoc(writeDate);
	    String newIcon = (isNew) ? "&nbsp;<img src='" + e3ps.common.web.E3PSWebFlag.ICONURL + "/i_new.gif'/>" : "";

	    String docNumberStr;
	    if (preferred) {
		title = "<b>" + title + "</b>";
		docNumberStr = "";
	    } else {
		docNumberStr = String.valueOf(docNumber);
	    }

	    String titleFinal = title + newIcon;

	    strBuffer.append("<I ");
	    strBuffer.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
	    strBuffer.appendRepl(" DocNumber=\"" + docNumberStr + "\"");
	    strBuffer.appendRepl(" Classification=\"" + ManualBoardData.classificationToString(classification) + "\"");
	    strBuffer.appendRepl(" Classification1=\"" + StringUtil.checkNull(record.getString("classification1Value")) + "\"");
	    strBuffer.appendRepl(" Classification2=\"" + StringUtil.checkNull(record.getString("classification2Value")) + "\"");
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
	    strBuffer.appendRepl(" Writer=\"" + writer + "\"");
	    strBuffer.appendRepl(" WriteDate=\"" + DateUtil.getDateString(writeDate, "d") + "\"");
	    strBuffer.appendRepl(" ReadCount=\"" + readCount + "\"");
	    strBuffer.append("/>");
	}
    }

}
