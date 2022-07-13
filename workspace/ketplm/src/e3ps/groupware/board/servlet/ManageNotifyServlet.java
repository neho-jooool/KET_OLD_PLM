/**
 * @(#)    ManageNotifyServlet.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify
 *
 */

package e3ps.groupware.board.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.board.Notify;
import e3ps.groupware.board.NotifyData;
import e3ps.groupware.board.NotifyHelper;
import e3ps.groupware.board.beans.HelpBoardUtil;
import ext.ket.shared.log.Kogger;

public class ManageNotifyServlet extends CommonServlet {
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String command = req.getParameter("command");
	String from = req.getParameter("from");
	Kogger.debug(getClass(), "=====> ManageNotifyServlet: command=[" + command + "], from=[" + from + "]");

	if (command == null || command.equals(""))
	    command = "search";

	if (command.equals("search")) {
	    /*
	     * Start [PLM System 1차개선] 수정내용 : 검색결과 Grid 적용, 2013. 06. 14, 김무준
	     */
	    /*
	     * req.setAttribute("control", getPageControl(req,res) ); gotoResult(req, res, "/jsp/groupware/board/notifylist.jsp");
	     */
	    search(req, res);
	    /*
	     * End [PLM System 1차개선] 수정내용 : 다국어 처리, 2013. 06. 13, 김무준
	     */
	} else if (command.equals("create")) {
	    String oid = create(req, res);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		if (oid != null) {
		    // res.sendRedirect("/plm/servlet/e3ps/ManageNotifyServlet"); // 검색 페이지로 이동(기존)
		    res.sendRedirect("/plm/jsp/groupware/board/notifyview.jsp?oid=" + oid); // 생성 후 상세조회 페이지로 이동하도록 수정
		}
	    }
	} else if (command.equals("update")) {
	    String oid = update(req, res);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		if (oid != null) {
		    // res.sendRedirect("/plm/servlet/e3ps/ManageNotifyServlet"); // 검색 페이지로 이동(기존)
		    res.sendRedirect("/plm/jsp/groupware/board/notifyview.jsp?oid=" + oid); // 수정 후 상세조회 페이지로 이동하도록 수정
		}
	    }
	} else if (command.equals("delete")) {
	    delete(req);
	    if ("main".equals(from)) {
		closeNreload(res);
	    } else {
		// res.sendRedirect("/plm/servlet/e3ps/ManageNotifyServlet");
	    }
	}
    }

    public String create(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    // 첨부파일 용량 체크
	    // if (HelpBoardUtil.isOverAttachLimit(uploader.getFiles())) {
	    // HelpBoardUtil.histroyBack(res, "각 첨부파일의 용량은 " + HelpBoardUtil.ATTACH_SIZE_LIMIT_MB + " MB를 초과할 수 없습니다.");
	    // return null;
	    // }

	    String title = map.getString("title");
	    String deadline = map.getString("deadline");
	    // String content = map.getString("content");
	    String webEditor = map.getString("webEditor");
	    String webEditorText = map.getString("webEditorText");
	    String type = map.getString("type");
	    if (type == null)
		type = "text";
	    int preferred = map.getInt("preferred");

	    Notify newNotify = Notify.newNotify();
	    newNotify.setTitle(title);

	    if (deadline != null && deadline.length() > 0)
		newNotify.setDeadline(DateUtil.convertEndDate2(deadline));

	    // newNotify.setContents(content);
	    newNotify.setWebEditor(webEditor);
	    newNotify.setWebEditorText(webEditorText);
	    newNotify.setPreferred(preferred);
	    newNotify.setOwner(SessionHelper.manager.getPrincipalReference());
	    newNotify = (Notify) PersistenceHelper.manager.save(newNotify);

	    newNotify = (Notify) E3PSContentHelper.service.attach(newNotify, uploader.getFiles());
	    //연구기획팀 요청에 따라 공지사항 자동삭제하는 스케줄러 기능은 주석처리한다 2015.06.25 황정태
	    //NotifyHelper.createSchedule(newNotify); 

	    oid = CommonUtil.getOIDString(newNotify);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    public String update(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    // 첨부파일 용량 체크
	    // if (HelpBoardUtil.isOverAttachLimit(uploader.getFiles())) {
	    // HelpBoardUtil.histroyBack(res, "각 첨부파일의 용량은 " + HelpBoardUtil.ATTACH_SIZE_LIMIT_MB + " MB를 초과할 수 없습니다.");
	    // return null;
	    // }

	    String title = map.getString("title");
	    String deadline = map.getString("deadline");
	    // String content = map.getString("content");
	    String webEditor = map.getString("webEditor");
	    String webEditorText = map.getString("webEditorText");
	    String type = map.getString("type");
	    oid = map.getString("oid");
	    if (type == null)
		type = "text";
	    int preferred = map.getInt("preferred");

	    Notify notify = (Notify) CommonUtil.getObject(oid);

	    notify.setTitle(title);
	    // notify.setContents(content);
	    notify.setWebEditor(webEditor);
	    notify.setWebEditorText(webEditorText);
	    notify.setPreferred(preferred);

	    if (deadline != null && deadline.length() > 0) {
		notify.setDeadline(DateUtil.convertEndDate2(deadline));
	    } else {
		notify.setDeadline(null);
	    }

	    notify = (Notify) PersistenceHelper.manager.modify(notify);
	    //연구기획팀 요청에 따라 공지사항 자동삭제하는 스케줄러 기능은 주석처리한다 2015.06.25 황정태
	    //NotifyHelper.updateSchedule(notify);

	    notify = (Notify) HelpBoardUtil.updateAttachFiles(notify, map, uploader.getFiles());

	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    public void delete(HttpServletRequest req) {
	String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
	Notify notify = (Notify) CommonUtil.getObject(oid);
	try {
	    //연구기획팀 요청에 따라 공지사항 자동삭제하는 스케줄러 기능은 주석처리한다 2015.06.25 황정태
	    //NotifyHelper.deleteSchedule(notify);
	    PersistenceHelper.manager.delete(notify);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용 수정일자 : 2013. 06. 14 수정자 : 김무준
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {
	Connection conn = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    String title = map.getString("title");
	    String creator = map.getString("creator");
	    String predate = map.getString("predate");
	    String postdate = map.getString("postdate");

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();

	    // 검색 쿼리
	    StringBuilder qrybuf = new StringBuilder();
	    qrybuf.append(" select a1.classnamea2a2||':'||a1.ida2a2 as docOid                            \n");
	    qrybuf.append(" from Notify a1, People a2        \n");
	    qrybuf.append(" where                            \n");
	    qrybuf.append("   a1.classnamekeyowner = a2.classnamekeyb4 and a1.ida3owner = a2.ida3b4                    \n");

	    if (StringUtil.checkString(title)) {
		// qrybuf.append("   and upper(a1.title) like '").append(HelpBoardUtil.makeQueryForLike(title.toUpperCase())).append("'        \n");
		qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("a1.title", title)).append("        \n");
	    }
	    if (StringUtil.checkString(creator)) {
		// qrybuf.append("   and a2.id = '").append(creator).append("'                \n");
		qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a2.id", creator, false)).append("        \n");
	    }
	    if (StringUtil.checkString(predate)) {
		qrybuf.append("   and a1.createstampa2 > to_date('").append(predate).append(" 000000', 'YYYY-MM-DD HH24MISS')    \n");
	    }
	    if (StringUtil.checkString(postdate)) {
		qrybuf.append("   and a1.createstampa2 < to_date('").append(postdate).append(" 235959', 'YYYY-MM-DD HH24MISS')    \n");
	    }

	    qrybuf.append(" order by a1.preferred desc, a1.createstampa2 desc        \n");
	    Kogger.debug(getClass(), "ManageNotifyServlet.search: qry=" + qrybuf.toString());

	    // 목록 결과
	    List<SqlRowMap> recordList = KETQueryUtil.getSqlResultList(qrybuf.toString(), conn);
	    // Kogger.debug(getClass(), "ManageNotifyServlet.search: recordList=" + recordList);

	    int listCount = recordList.size();

	    TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();
	    for (SqlRowMap record : recordList) {
		String docOid = record.getString("docOid");
		Notify note = (Notify) CommonUtil.getObject(docOid);
		NotifyData data = new NotifyData(note, 0);
		String title2 = data.title;
		int docNumber = (listCount--);

		String attachIcon = (data.isAttache) ? "<img src='" + e3ps.common.web.E3PSWebFlag.ICONURL + "/b-attach.gif'>" : "";
		String viewJsp = "/plm/jsp/groupware/board/notifyview.jsp?oid=" + docOid + "&from=list";
		String newIcon = (data.isNew) ? "&nbsp;<img src='" + e3ps.common.web.E3PSWebFlag.ICONURL + "/i_new.gif' border='0'>" : "";

		String docNumberStr;
		if (data.isPreferred()) {
		    title2 = "<b>" + title2 + "</b>";
		    docNumberStr = "";
		} else {
		    docNumberStr = String.valueOf(docNumber);
		}

		String titleFinal = title2 + newIcon;

		strBuffer.append("<I ");
		strBuffer.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strBuffer.appendRepl(" DocNo=\"" + docNumberStr + "\"");
		strBuffer.appendRepl(" AttachIcon=\"" + attachIcon + "\"" + " AttachIconType=\"Html\"");
		strBuffer
		        .appendRepl(" Title=\"")
		        .appendContent(titleFinal)
		        .appendRepl(
		                "\"" + " TitleCanSelect=\"2\"" + " TitleType=\"Html\"" + " TitleOnClick=\"javascript:go_to('" + viewJsp
		                        + "');\"" + " TitleExportValue=\"")
		        .appendContent(data.title)
		        .appendRepl(
		                "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
		                        + "'>\" TitleHtmlPostfix=\"</font>\"");
		strBuffer.appendRepl(" WriteDate=\"" + DateUtil.getDateString(note.getPersistInfo().getCreateStamp(), "d") + "\"");
		strBuffer.appendRepl(" LimitDate=\""
		        + ((note.getDeadline() == null) ? "" : DateUtil.getDateString(note.getDeadline(), "d")) + "\"");
		strBuffer.appendRepl(" WriterDept=\"" + data.department + "\"");
		strBuffer.appendRepl(" Writer=\"" + data.writer + "\"");
		strBuffer.appendRepl(" ReadCount=\"" + data.readCount + "\"");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/groupware/board/notifylist.jsp");
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
