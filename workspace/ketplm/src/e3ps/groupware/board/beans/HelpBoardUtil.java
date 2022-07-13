package e3ps.groupware.board.beans;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.util.WTException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.query.LoggableStatement;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.groupware.board.HelpBoard;
import e3ps.groupware.board.dao.HelpBoardDao;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : HelpBoardUtil.java
 * 설명 : HelpDesk Board용 유틸리티
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class HelpBoardUtil implements RemoteAccess, Serializable {

    final static boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static void statementRsClose(ResultSet rs, LoggableStatement pstmt) {
        if( rs != null )
            try {rs.close();} catch(Exception e) {}

        if( pstmt != null )
            try {pstmt.close();} catch(Exception e) {}
    }

    // 신규 등록 표시 제한시간 (단위:시간)
    private static int NEW_DOC_LIMIT_HOUR;
    static {
        try {
            NEW_DOC_LIMIT_HOUR = ConfigImpl.getInstance().getInt("help.newdoc.limit.hour");
        }
        catch(Exception e) {
            Kogger.debug(HelpBoardUtil.class, "HelpBoardUtil: NEW_DOC_LIMIT_HOUR error: " + e.toString());
            NEW_DOC_LIMIT_HOUR = 24; // 에러 시 default (24시간)
        }
    }

    /**
     * 설정된 시간 제한으로부터 신규 등록 글 여부 판단('new' 아이콘 위해)
     * (위 'NEW_DOC_LIMIT_HOUR' 변수 참고)
     * @param date - 특정 일시 (글 생성일시, maybe)
     * @return 신규 등록 글이면  true
     */
    public static boolean isNewDoc(Date date) {
        Calendar limit = Calendar.getInstance();
        limit.add(Calendar.HOUR, -1 * NEW_DOC_LIMIT_HOUR);
        return (limit.getTime().before(date));
    }

    // 첨부파일 크기 제한 (단위:MB)
    public static int ATTACH_SIZE_LIMIT_MB;
    public static int ATTACH_SIZE_LIMIT_BY; // 계산 위한 Byte 단위
    static {
        try {
            ATTACH_SIZE_LIMIT_MB = ConfigImpl.getInstance().getInt("help.attach.size.limit.mb");
        }
        catch(Exception e) {
            Kogger.debug(HelpBoardUtil.class, "HelpBoardUtil: ATTACH_SIZE_LIMIT_MB error: " + e.toString());
            ATTACH_SIZE_LIMIT_MB = 20; // 에러 시 default (20MB)
        }
        ATTACH_SIZE_LIMIT_BY = ATTACH_SIZE_LIMIT_MB * 1024 * 1024;
    }

    /**
     * 설정된 첨부파일 크기 제한으로부터 초과 여부 판단
     * @param size -  첨부파일 크기
     * @return 제한보다 초과되었으면 true
     */
    public static boolean isOverAttachLimit(int size) {
        return (size > ATTACH_SIZE_LIMIT_BY);
    }
    /**
     * @param files - 첨부파일(e3ps.common.content.uploader.WBFile) 리스트
     * @return 제한 초과 파일이 존재하면 true
     */
    public static boolean isOverAttachLimit(Vector files) {
        if (files != null) {
            for (int i = 0; i < files.size(); ++i) {
                WBFile file = (WBFile) files.get(i);
                if (isOverAttachLimit(file.getSize())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * OID 형식 검사 (<클래스명>:<아이디>)(
     * @param oid
     * @return 정상이면 true
     */
    public static boolean isValidOid(String oid) {
        if (oid.indexOf(":") == -1)
            return false;

        if (oid.startsWith(":"))
            return false;

        return true;
    }

    /**
     * OID로부터 클래스명과 아이디 추출
     * @param oid
     * @return {클래스명, 아이디} 형식의 String 배열. 비정상이면 null.
     */
    public static String[] separateOid(String oid) {
        if (isValidOid(oid) == false)
            return null;
        return oid.split(":");
    }

    /**
     * HelpBoard를 상속받은 board에서 글 삭제 시 하위 글(답변 등) 포함 모두 삭제함.(recursively)
     * @param oid - 삭제할 글의 OID
     * @param dao - 해당 board에 대한 DAO 객체
     * @throws Exception
     */
    public static void deleteBoardWithDescendant(String oid, HelpBoardDao dao) throws Exception {
        HelpBoard board = (HelpBoard) CommonUtil.getObject(oid);
        if (board == null)
            return;

        // descendant 먼저
        List<String> coidlist = dao.getChildOidList(oid);
        for (String coid : coidlist) {
            deleteBoardWithDescendant(coid, dao);
        }

        PersistenceHelper.manager.delete(board);
    }

    /**
     * IO 관련 객체 close
     * @param obj
     */
    public static void closeIO(Object obj) {
        if (obj != null) {
            try {
                if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                }
                else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                }
                else if (obj instanceof Reader) {
                    ((Reader) obj).close();
                }
                else if (obj instanceof Writer) {
                    ((Writer) obj).close();
                }
                else {
                    Kogger.debug(HelpBoardUtil.class, "HelpBoardUtil.closeIO: unchecked obj type=" + obj.getClass().getName());
                }
            }
            catch(Exception e) {}
        }
    }

    public static String makeQueryForLike(String value) {
        value = value.replaceAll("\\*", "%");
        if (value.startsWith("%") == false) value = "%" + value;
        if (value.endsWith("%") == false) value = value + "%";
        return value;
    }

    /**
     * 게시판 제목 및 내용 쿼리용. 항상 upper 및 like 사용.
     * @param columnName
     * @param keywords
     * @return
     */
    public static String getSqlQueryForHelpBoardMultiSearch(String columnName, String keywords) {
        String[] keywordAry = keywords.split(",");
        StringBuilder sb = new StringBuilder();
        columnName = "upper(" + columnName + ")";
        boolean appended = false;

        sb.append("(    \n ");

        for (String keyword : keywordAry) {
            if (appended == true) {
                sb.append(" or ");
            }

            sb.append(columnName).append(" like '").append(makeQueryForLike(keyword.toUpperCase())).append("'        \n");

            if (appended == false) appended = true;
        }

        sb.append(")    \n");

        return sb.toString();
    }

    public static ContentHolder updateAttachFiles(ContentHolder holder, Map params, Vector files) throws Exception {
        KETParamMapUtil map = KETParamMapUtil.getMap(params);

        Vector secondaryDelFileVec = new Vector();
        String delTemp = map.getString("deleteFiles");
        StringTokenizer tokens = new StringTokenizer(KETStringUtil.nullFilter(delTemp), "*");
        while (tokens.hasMoreElements()) {
            secondaryDelFileVec.addElement(tokens.nextElement());
        }

        QueryResult rs = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
        while (rs.hasMoreElements()) {
            ContentItem allContentItem = (ContentItem) rs.nextElement();
            Object allObj = allContentItem;
            String ItemString = allObj.toString();
            for (int i = 0; i < secondaryDelFileVec.size(); i++) {
                if (secondaryDelFileVec.get(i).equals(ItemString)) {
                    holder = E3PSContentHelper.service.delete(holder, allContentItem);
                }
            }
        }

        if (files != null && files.size() > 0) {
            holder = E3PSContentHelper.service.attach(holder, files);
        }

        return holder;
    }

    public static String nullToNbsp(String str) {
        return KETStringUtil.nvl(str, "&nbsp;");
    }

    // 'KETProdEcoServlet.histroyBack()' 참고
    public static void histroyBack(HttpServletResponse res, String msg) {
        PrintWriter out = null;
        try {
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
              String rtn_msg = "";
              rtn_msg = "\n <script language=\"javascript\">"
                      + "\n   alert(\"" + msg + "\");"
                      + "\n   history.back();"
                      + "\n </script>";
              out.println(rtn_msg);
              out.flush();
        }
        catch (Exception e) {
            Kogger.error(HelpBoardUtil.class, e);
        }
        finally {
            closeIO(out);
        }
    }

    /**
     * 조회수 증가
     */
    public static HelpBoard increaseReadCount(HelpBoard boardObj) throws WTException {
        if(!SERVER) {
            Class argTypes[] = new Class[]{HelpBoard.class};
            Object args[] = new Object[]{boardObj};
            Object obj = null;
            try {
                obj = RemoteMethodServer.getDefault().invoke(
                        "increaseReadCount",
                        "e3ps.groupware.board.beans.HelpBoardUtil",
                        null,
                        argTypes,
                        args);
            }
            catch(RemoteException e) {
                Kogger.error(HelpBoardUtil.class, e);
                throw new WTException(e);
            }
            catch(InvocationTargetException e) {
                Kogger.error(HelpBoardUtil.class, e);
                throw new WTException(e);
            }
            return (HelpBoard) obj;
        }

        Transaction trx = new Transaction();
        try {
            trx.start();

            boardObj.setReadCount(boardObj.getReadCount() + 1);
            boardObj = (HelpBoard) PersistenceHelper.manager.save(boardObj);

            trx.commit();
        }
        catch(Exception e) {
            Kogger.error(HelpBoardUtil.class, e);
            trx.rollback();
        }
        finally {
            trx = null;
        }
        return boardObj;
    }

}
