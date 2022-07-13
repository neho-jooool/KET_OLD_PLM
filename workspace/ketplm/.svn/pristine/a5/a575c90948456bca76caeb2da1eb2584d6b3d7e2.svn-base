package e3ps.groupware.board;

import java.sql.Timestamp;
import java.util.Vector;

import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.board.beans.HelpBoardUtil;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : HelpBoardData.java
 * 설명 : HelpBoard 객체 wrapper
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public abstract class HelpBoardData {

    // 상태
    public static final String STATE_REGISTERED = "REGST"; // 등록
    public static final String STATE_PROCESSING = "PROCS"; // 처리중/진행
    public static final String STATE_ACCEPTED = "ACEPT"; // 접수
    public static final String STATE_COMPLETED = "COMPL"; // 완료
    public static final String STATE_DEFERRED = "DEFER"; // 보류

    protected HelpBoard boardObj = null;
    private String oid = "";
//    private String title = "";
    private String writer = "";
    private String department = "";
    private String writeDate = "";
    private String writeListDate = "";
    private boolean isAttach = false;
    private boolean isNew = false;
    private Vector attachList = new Vector();
//    private int readCount = 0;
    private String webEditor = "";
    private String webEditorText = "";
    private boolean deleted = false;
//    private boolean preferred = false;
//    private int depth = 0;
    private boolean hasParent = false;

    public abstract HelpBoard getBoardObj();

    /**
     * @param boardObj
     * @param type
     *            : 내부 Attr을 어떻게 구성해 줄것인가에 대한 type(0:simple-리스트에 필요한 정보,
     *            1:all-모든 정보)
     */
    public HelpBoardData(HelpBoard boardObj, int type) {
        if (boardObj == null)
            return;
        this.boardObj = boardObj;

        oid = CommonUtil.getOIDString(boardObj);
        Timestamp timeStamp = boardObj.getPersistInfo().getCreateStamp();
        writeDate = DateUtil.getDateToWeb(timeStamp);
        writer = boardObj.getOwner().getFullName();
        try {
            department = new PeopleData(boardObj.getOwner().getPrincipal()).departmentName;
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        isNew = HelpBoardUtil.isNewDoc(timeStamp);
        attachList = ContentUtil.getSecondaryContents(boardObj);
        if (attachList.size() != 0)
            isAttach = true;
        deleted = (boardObj.getDeleted() != 0);
//        preferred = (boardObj.getPreferred() != 0);

        if (type == 1) {
            writeDate = DateUtil.getDateString(timeStamp, "d");
            webEditor = (String) boardObj.getWebEditor();
            webEditorText = (String) boardObj.getWebEditorText();
        }

        HelpBoard parent = null;
        try {
            parent = (HelpBoard) boardObj.getParent();
        }
        catch(Exception e) {
            Kogger.debug(getClass(), e.toString());
        }
        hasParent = (parent != null);

    }

    public String getOid() {
        return oid;
    }

    public String getTitle() {
        return boardObj.getTitle();
    }

    public String getWriter() {
        return writer;
    }

    public String getDepartment() {
        return department;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public String getWriteListDate() {
        return writeListDate;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public boolean isNew() {
        return isNew;
    }

    public Vector getAttachList() {
        return attachList;
    }

    public int getReadCount() {
        return boardObj.getReadCount();
    }

    public String getWebEditor() {
        return webEditor;
    }

    public String getWebEditorText() {
        return webEditorText;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isPreferred() {
        return (boardObj.getPreferred() == 1);
    }

    public int getDepth() {
        return boardObj.getDepth();
    }

    public boolean isHasParent() {
        return hasParent;
    }

    /**
     * 조회수 증가
     */
    public void increaseReadCount() {
        try {
            boardObj = HelpBoardUtil.increaseReadCount(boardObj);
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

}
