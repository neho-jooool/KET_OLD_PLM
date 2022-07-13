/**
 * @(#)	ManageCommentServlet
import e3ps.servlet.common.CommonServlet;
.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.3
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify	
 *
 */

package e3ps.groupware.board.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.board.Board;
import e3ps.groupware.board.BoardComment;
import ext.ket.shared.log.Kogger;

public class ManageCommentServlet extends CommonServlet {
    public void doService(HttpServletRequest req, HttpServletResponse res)
		throws ServletException,IOException {
        String command = ParamUtil.checkStrParameter(req.getParameter("command"));
		if ( command.equals("") ) command = "search";
		String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
		String coid = ParamUtil.checkStrParameter(req.getParameter("coid"));		
		
		if ( command.equals("create") ) create(req);
		else if ( command.equals("delete") ) delete(req);
		res.sendRedirect("/Windchill/jsp/groupware/board/boardview.jsp?oid="+oid+"&coid="+coid);
	}
	
	public void create(HttpServletRequest req) {
		String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
		String contents = ParamUtil.checkStrParameter(req.getParameter("contents"));
		try {
			WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
			
			BoardComment comment = BoardComment.newBoardComment();
			comment.setContents(contents);
			comment.setBoard((Board)CommonUtil.getObject(oid));
			comment.setOwner(SessionHelper.manager.getPrincipalReference());
			PersistenceHelper.manager.save(comment);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}
	}
	
	public void delete(HttpServletRequest req) {
		String commentoid = ParamUtil.checkStrParameter(req.getParameter("commentoid"));
		BoardComment comment = (BoardComment)CommonUtil.getObject(commentoid);
		try {
			PersistenceHelper.manager.delete(comment);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	}
}
