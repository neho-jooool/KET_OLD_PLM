/**
 * @(#)	BoardData.java
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
import java.util.ArrayList;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

public class BoardData {
    public static final int NORMAL_BOARD = 1;
    public static final int USER_DELETE = 2;
    public static final int ADMIN_DELETE = 3;

    // type = 0 일때 채워지는 필드.......
    public Board board;
    public BoardCategory boardCategory;
    public int state = 1;
    public String oid = "";
    public String title = "";
    public String writer = "";
    public String department="";
    public String writeDate = "";
    public boolean isAttache = false;
    public boolean isNew = false;
    public Vector attacheList = new Vector();
    public int readCount;
    public int depth;
    public ArrayList commentList = new ArrayList();
    public int commentCount;

    // type = 1  일때 채워지는 필드......
    public String contents = "";
    public boolean canModify = false;
    public String webEditor = "";
    public String webEditorText = "";

    /**
     * @param _board
     * @param type	: 내부 Attr을 어떻게 구성해 줄것인가에 대한 type(0:simple-리스트에 필요한 정보, 1:all-모든 정보)
     * @throws Exception
     */
    public BoardData(Board _board,int type) throws Exception {
        this.board = _board;
        this.boardCategory = _board.getCategory();
        this.oid = CommonUtil.getOIDString(_board);
        this.state = _board.getState();
        this.title = _board.getTitle();
        Timestamp timeStamp = _board.getPersistInfo().getCreateStamp();
        this.writeDate = DateUtil.getDateToWeb(timeStamp);
        this.writer = _board.getOwner().getFullName();
        this.department = new PeopleData(_board.getOwner().getPrincipal()).departmentName;
        this.isNew = DateUtil.isToday(timeStamp);
        this.readCount = _board.getReadCount();
        this.depth = _board.getDepth();
        this.commentList = BoardData.getComment(_board);
        this.commentCount = this.commentList.size();
        this.attacheList = ContentUtil.getSecondaryContents(_board);
        if (this.attacheList.size() != 0) this.isAttache = true;

        if ( type == 1 ) {
            this.writeDate = DateUtil.getDateString(timeStamp,"all");
            this.contents = (String)_board.getContents();
            this.canModify = ( checkOwner() || CommonUtil.isAdmin() );
            this.webEditor = (String)_board.getWebEditor();
            this.webEditorText = (String)_board.getWebEditorText();
        }
    }

    private boolean checkOwner() {
        try {
            return this.board.getOwner().equals(SessionHelper.manager.getPrincipalReference());
        } catch (WTException e) {
            Kogger.error(getClass(), e);
            return false;
        }
    }

    public static ArrayList getComment(Board _board) {
        ArrayList returnData = new ArrayList();
        try {
            QuerySpec query = new QuerySpec();
            int targetIdx = query.addClassList(BoardComment.class, true);
            int linkIdx = query.addClassList(BoardCommentLink.class, true);
            try
            {
                e3ps.common.query.SearchUtil.setOrderBy(query, BoardCommentLink.class, linkIdx, "thePersistInfo.createStamp" ,true);
            }
            catch(WTPropertyVetoException e)
            {
                Kogger.error(BoardData.class, e);
            }

            QueryResult qr = PersistenceHelper.manager.navigate(_board, BoardCommentLink.COMMENT_ROLE, query, false);
//			QueryResult qr = PersistenceHelper.manager.navigate(_board,BoardCommentLink.COMMENT_ROLE,BoardCommentLink.class,true);
            while ( qr.hasMoreElements() ) {
                Object obj = qr.nextElement();
                returnData.add(obj);
            }
        } catch (WTException e) {
            Kogger.error(BoardData.class, e);
        }
        return returnData;
    }

    public void updateReadCount() {
        try {
            this.readCount++;
            this.board.setReadCount(this.readCount);
            this.board = (Board)PersistenceHelper.manager.modify(this.board);
        } catch (WTPropertyVetoException e) {
            Kogger.error(getClass(), e);
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }
    }

    public boolean isPreferred() {
        return (board.getPreferred() == 1);
    }

}
