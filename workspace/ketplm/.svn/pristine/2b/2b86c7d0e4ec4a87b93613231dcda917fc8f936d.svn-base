package e3ps.groupware.board.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.groupware.board.beans.HelpBoardUtil;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : HelpBoardDao.java
 * 설명 : HelpDesk Board용 공통 DAO
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public abstract class HelpBoardDao {

    protected Connection conn = null;

    public HelpBoardDao(Connection conn){
        this.conn = conn;
    }

    /**
     * 게시판 테이블 명 반환
     * @return 게시판 테이블 명
     */
    protected abstract String getBoardTableName();
    /**
     * 게시판 검색 쿼리 생성
     * @param hash - 검색조건
     * @param isCnt - 게시판 건수(답변 제외) 검색 여부
     * @return 게시판 검색 쿼리
     */
    protected abstract String getSearchBoardQuery(Map<String, Object> map, boolean isCnt);

    /**
     * 게시판 검색
     * @param hash - 검색조건
     * @return 결과 리스트 (SqlRowMap 참고)
     */
    public List<SqlRowMap> searchBoard(Map<String, Object> map) {
        List<SqlRowMap> list = null;

        try {
            String query = getSearchBoardQuery(map, false);
            Kogger.debug(getClass(), this.getClass().getSimpleName() + ".searchBoard: query=" + query);

            list = KETQueryUtil.getSqlResultList(query, conn);
            Kogger.debug(getClass(), this.getClass().getSimpleName() + ".searchBoard: list size=" + list.size());
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }

        return list;
    }

    /**
     * 게시판 건수(답변 제외 : depth=0) 검색
     * @param hash - 검색조건
     * @return int
     */
    public int searchBoardCount(Map<String, Object> map) {
        int cnt = 0;
        List<SqlRowMap> list = null;

        try {
            String query = getSearchBoardQuery(map, true);
            Kogger.debug(getClass(), this.getClass().getSimpleName() + ".searchBoardCount: query=" + query);

            list = KETQueryUtil.getSqlResultList(query, conn);
            for (SqlRowMap record : list) {
                cnt = record.getInt("cnt");
            }
            Kogger.debug(getClass(), this.getClass().getSimpleName() + ".searchBoardCount: count=" + cnt);
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }

        return cnt;
    }

    /**
     * 해당 글의 하위 글들(답변 등)의 OID 구함
     * @param oid - 해당 글 OID
     * @return 하위 글 OID 리스트
     */
    public List<String> getChildOidList(String oid) {
        List<String> list = new ArrayList<String>();

        String[] oary = HelpBoardUtil.separateOid(oid);

        StringBuilder qrybuf = new StringBuilder();
        qrybuf.append(" select a1.classnamea2a2||':'||a1.ida2a2 as oid		\n");
        qrybuf.append(" from ").append(getBoardTableName()).append(" a1		\n");
        qrybuf.append(" where		\n");
        qrybuf.append("   a1.classnamekeyparentreference = '").append(oary[0]).append("'		\n");
        qrybuf.append("   and a1.ida3parentreference = ").append(oary[1]).append("		\n");
        qrybuf.append(" order by a1.createstampa2 ");

        LoggableStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = new LoggableStatement( conn, qrybuf.toString() );
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String coid = rs.getString(1);
                list.add(coid);
            }
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            HelpBoardUtil.statementRsClose(rs, pstmt);
        }

        return list;
    }
    public String getFirstChildOid(String oid) {
        String ret = null;
        List<String> list = getChildOidList(oid);
        if (list.size() > 0) {
            ret = list.get(0); // order by a1.createstampa2
        }
        return ret;
    }
    public String getLastChildOid(String oid) {
        String ret = null;
        List<String> list = getChildOidList(oid);
        if (list.size() > 0) {
            ret = list.get(list.size()-1); // order by a1.createstampa2
        }
        return ret;
    }

}
