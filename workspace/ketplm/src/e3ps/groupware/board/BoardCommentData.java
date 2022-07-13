/**
 * @(#)	CommentData.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.3
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify	
 *
 */

package e3ps.groupware.board;

import java.sql.Timestamp;

import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.shared.log.Kogger;

public class BoardCommentData {
	public BoardComment comment;
	public Board board;
	public String oid = "";
	public String writer = "";
	public String writeDate = "";
	public String contents = "";
	public boolean canDelete = false;
	
	public BoardCommentData(BoardComment _comment) throws Exception {
		this.comment = _comment;
		this.board = _comment.getBoard();
		this.oid = CommonUtil.getOIDString(_comment);
		this.writer = _comment.getOwner().getFullName(); 
		Timestamp timeStamp = _comment.getPersistInfo().getCreateStamp();
		this.writeDate = DateUtil.getDateString(timeStamp,"all");
		this.contents = (String)_comment.getContents();
		this.canDelete = ( this.canDelete() || CommonUtil.isAdmin() );
	}
	
	private boolean canDelete() {
		try {
			return this.comment.equals(SessionHelper.manager.getPrincipalReference());
		} catch (WTException e) {
			Kogger.error(getClass(), e);
			return false;
		}
	}
}
