package e3ps.groupware.board.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.board.HelpBoard;
import e3ps.groupware.board.ImproveBoard;
import e3ps.groupware.board.beans.HelpBoardUtil;
import e3ps.groupware.board.dao.HelpBoardDao;
import e3ps.groupware.board.service.BoardHelper;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : ManageHelpBoardServlet.java
 * 설명 : HelpDesk Board용 공통 servlet
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public abstract class ManageHelpBoardServlet extends CommonServlet {

    /**
     * 게시판 테이블 명 반환
     * 
     * @return
     */
    protected abstract String getBoardTableName();

    /**
     * servlet 자신의 경로 반환
     * 
     * @return
     */
    protected abstract String getServletPath();

    /**
     * 검색결과를 나타낼 페이지 경로 반환
     * 
     * @return
     */
    protected abstract String getBoardListPagePath();

    /**
     * 상세조회 페이지 경로 반환
     * 
     * @return
     */
    protected abstract String getViewPagePath(String oid);

    /**
     * 게시판 DAO 반환
     * 
     * @param conn
     *            - JDBC 연결 객체
     * @return
     */
    protected abstract HelpBoardDao getDao(Connection conn);

    /**
     * 게시판 글 객체 생성 및 request로부터 값 설정
     * 
     * @param reqParam
     *            - request 파라미터
     * @return 게시판 글 객체
     * @throws WTPropertyVetoException
     * @throws WTException
     */
    protected abstract HelpBoard newBoard(Map<String, Object> map) throws WTPropertyVetoException, WTException;

    protected abstract void modifyBoard(HelpBoard board, Map<String, Object> map) throws WTPropertyVetoException, WTException;

    protected void processParentAfterDeleteBoard(HelpBoard parent) throws WTPropertyVetoException, WTException {
    }

    /**
     * 해당 레코드(row) 정보를 TreeGrid 형식(<I>)으로 변환하여 버퍼에 저장
     * 
     * @param record
     *            - one row
     * @param strBuffer
     *            - 결과 저장 버퍼
     */
    // protected abstract void appendGridRow(SqlRowMap record, TreeGridStringBuffer strBuffer);
    protected abstract void appendAllGridRow(List<SqlRowMap> recordList, int cnt, TreeGridStringBuffer strBuffer) throws Exception;

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String command = req.getParameter("command");
	String from = req.getParameter("from");
	Kogger.debug(getClass(), "=====> " + this.getClass().getSimpleName() + ": command=[" + command + "], from=[" + from + "]");

	if (command == null || command.equals(""))
	    command = "search";

	if (command.equals("search")) {
	    search(req, res);
	} else if (command.equals("create")) {
	    String oid = create(req, res);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		if (oid != null) {
		    // res.sendRedirect(getServletPath()); // 검색 페이지로 이동(기존)
		    res.sendRedirect(getViewPagePath(oid)); // 생성 후 상세조회 페이지로 이동하도록 수정
		}
	    }
	} else if (command.equals("update")) {
	    String oid = update(req, res);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		if (oid != null) {
		    // res.sendRedirect(getServletPath()); // 검색 페이지로 이동(기존)
		    res.sendRedirect(getViewPagePath(oid)); // 수정 후 상세조회 페이지로 이동하도록 수정
		}
	    }
	} else if (command.equals("delete")) {
	    delete(req);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		// res.sendRedirect(getServletPath());
	    }
	}
    }

    /**
     * 게시판 테이블에 글 생성
     * 
     * @param req
     * @param res
     * @return board oid
     */
    public String create(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    // 첨부파일 용량 체크 - Q&A 등록, 시스템 개선 요청 등록에만 적용
	    if (("qna_q".equals(map.getString("module")) || "improve_q".equals(map.getString("module")))
		    && HelpBoardUtil.isOverAttachLimit(uploader.getFiles())) {
		HelpBoardUtil.histroyBack(res, "각 첨부파일의 용량은 " + HelpBoardUtil.ATTACH_SIZE_LIMIT_MB + " MB를 초과할 수 없습니다.");
		return null;
	    }

	    // param 확인용 코드. Kogger.debug(getClass(), paramMap); 로 대체 가능
	    /*
	     * Hashtable param = uploader.getFormParameters(); Enumeration en = param.keys();
	     * Kogger.debug(getClass(), "----------------------------- [PARAMS][create] -----------------------------");
	     * 
	     * while (en.hasMoreElements()) { Object obj = (Object) en.nextElement(); Kogger.debug(getClass(), "obj.toString : " + obj.toString()
	     * + "\n param.get(obj) : " + param.get(obj)); Kogger.debug(getClass(), "----------------------------------------------");
	     * 
	     * } %/
	     * 
	     * // 첨부파일 확인용 코드. Kogger.debug(getClass(), KETParamMapUtil.logAttachFiles(uploader.getFiles())); 로 대체 가능 /% Vector files =
	     * uploader.getFiles(); if (files != null) { for (int fileCount = 0; fileCount < files.size(); fileCount++) { WBFile file =
	     * (WBFile) files.elementAt(fileCount); Kogger.debug(getClass(), "file.getName : " + file.getName() + "\nfile.getFieldName : " +
	     * file.getFieldName()); } }
	     */

	    String title = map.getString("title");
	    String webEditor = map.getString("webEditor");
	    String webEditorText = map.getString("webEditorText");
	    int preferred = ParamUtil.getIntParameter(map.getString("preferred"));

	    HelpBoard newBoard = newBoard(map);
	    newBoard.setTitle(title);
	    newBoard.setWebEditor(webEditor);
	    newBoard.setWebEditorText(webEditorText);
	    newBoard.setPreferred(preferred);
	    newBoard.setOwner(SessionHelper.manager.getPrincipalReference());

	    newBoard = (HelpBoard) PersistenceHelper.manager.save(newBoard);

	    newBoard = (HelpBoard) E3PSContentHelper.service.attach(newBoard, uploader.getFiles());

	    oid = CommonUtil.getOIDString(newBoard);
	    
	    if(newBoard instanceof ImproveBoard && StringUtils.isNotEmpty(((ImproveBoard) newBoard).getDivision())){
		BoardHelper.service.QnAMailSend(oid);
	    }
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    /**
     * 게시판 테이블의 글 수정
     * 
     * @param req
     * @param res
     * @param uploader
     * @return board oid
     */
    public String update(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    // 첨부파일 용량 체크 - Q&A 수정, 시스템 개선 요청 수정에만 적용
	    if (("qna_q".equals(map.getString("module")) || "improve_q".equals(map.getString("module")))
		    && HelpBoardUtil.isOverAttachLimit(uploader.getFiles())) {
		HelpBoardUtil.histroyBack(res, "각 첨부파일의 용량은 " + HelpBoardUtil.ATTACH_SIZE_LIMIT_MB + " MB를 초과할 수 없습니다.");
		return null;
	    }

	    String title = map.getString("title");
	    String webEditor = map.getString("webEditor");
	    String webEditorText = map.getString("webEditorText");
	    String type = map.getString("type");
	    oid = map.getString("oid");
	    if (type == null)
		type = "text";
	    int preferred = ParamUtil.getIntParameter(map.getString("preferred"));

	    HelpBoard board = (HelpBoard) CommonUtil.getObject(oid);

	    if (StringUtil.checkString(title))
		board.setTitle(title); // 답변 경우
	    board.setWebEditor(webEditor);
	    board.setWebEditorText(webEditorText);
	    board.setPreferred(preferred);

	    modifyBoard(board, map);

	    board = (HelpBoard) PersistenceHelper.manager.modify(board);

	    board = (HelpBoard) HelpBoardUtil.updateAttachFiles(board, map, uploader.getFiles());
	    
	    if(board instanceof ImproveBoard && StringUtils.isNotEmpty(((ImproveBoard) board).getDivision())){
		BoardHelper.service.QnAMailSend(oid);
	    }

	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    /**
     * 게시판 테이블의 글 삭제
     * 
     * @param req
     */
    public void delete(HttpServletRequest req) {
	String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
	HelpBoard board = (HelpBoard) CommonUtil.getObject(oid);
	if (board != null) {
	    Connection conn = null;
	    try {
		HelpBoard parent = (HelpBoard) board.getParent();

		// 커넥션 생성
		conn = PlmDBUtil.getConnection();
		HelpBoardDao dao = getDao(conn);

		HelpBoardUtil.deleteBoardWithDescendant(oid, dao);

		if (parent != null) {
		    processParentAfterDeleteBoard(parent);
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    } finally {
		// 커넥션 종료
		PlmDBUtil.close(conn);
	    }
	}
    }

    /**
     * 게시판 테이블 검색
     * 
     * @param req
     * @param res
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {
	Connection conn = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    // Kogger.debug(getClass(), this.getClass().getSimpleName() + ".map=" + map);

	    String lang = map.getString("userlang");
	    if (StringUtil.isEmpty(lang)) {
		KETMessageService messageService = KETMessageService.getMessageService(req);
		lang = (messageService != null) ? messageService.getLocale().toString() : Locale.KOREAN.getLanguage(); // default: ko
		map.put("userlang", lang);
	    }

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();

	    // 목록 결과
	    HelpBoardDao dao = getDao(conn);
	    List<SqlRowMap> recordList = dao.searchBoard(map);
	    int cnt = dao.searchBoardCount(map);

	    TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();

	    appendAllGridRow(recordList, cnt, strBuffer);

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    req.setAttribute("boardCnt", cnt + ""); // 검색 건수(답변 제외)
	    gotoResult(req, res, getBoardListPagePath());
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

    protected String getLastChildOid(String oid) {
	String ret = null;
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    HelpBoardDao dao = getDao(conn);
	    ret = dao.getLastChildOid(oid);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
	return ret;
    }

}
