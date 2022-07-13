package e3ps.groupware.board.dao;

import java.sql.Connection;
import java.util.Map;

import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.board.beans.HelpBoardUtil;

/*
 * [PLM System 1차개선]
 * 파일명 : QnaBoardDao.java
 * 설명 : Q&A Board용 DAO
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class QnaBoardDao extends HelpBoardDao {

    public QnaBoardDao(Connection conn) {
        super(conn);
    }

    /**
     * 게시판 테이블 명 반환
     * @return 게시판 테이블 명
     */
    @Override
    protected String getBoardTableName() {
        return "QnaBoard";
    }

    /**
     * 게시판 검색 쿼리 생성
     * @param hash - 검색조건
     * @param isCnt - 게시판 건수(답변 제외) 검색 여부
     * @return 게시판 검색 쿼리
     */
    @Override
    protected String getSearchBoardQuery(Map<String, Object> _map, boolean isCnt) {
        KETParamMapUtil map = KETParamMapUtil.getMap(_map);
        String title = map.getString("title");
        String creator = map.getString("creator");
        String predate = map.getString("predate");
        String postdate = map.getString("postdate");
        String webEditorText = map.getString("webEditorText");
        // MultiCombo
        String[] stateAry = map.getStringArray("state");
        String[] classificationAry = map.getStringArray("classification");

        String lang = map.getString("userlang");

        StringBuilder qrybuf = new StringBuilder();

        qrybuf.append(" select                             \n");

        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   count(1) as cnt          \n");
        }
        else {              // 게시판 검색
            qrybuf.append("   a1.classnamea2a2||':'||a1.ida2a2 as docOid                    \n");
            qrybuf.append("   , a1.classification            \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE_PARENT('QNATYPE', a1.classification, '" + lang + "') as classification1Value   \n"); // 분류1 value
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('QNATYPE', a1.classification, '" + lang + "') as classificationValue   \n"); // 분류2 value
            qrybuf.append("   , a1.title                    \n");
            qrybuf.append("   , a1.state                    \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('QNASTATUS', a1.state, '" + lang + "') as stateValue   \n"); // 상태 value
            qrybuf.append("   , a1.createstampa2 as writeDate            \n");
            qrybuf.append("   , a1.readcount                \n");
            qrybuf.append("   , a1.classnamekeyparentreference||':'||a1.ida3parentreference as parentOid            \n");
            qrybuf.append("   , a1.depth                \n");
            qrybuf.append("   , a1.preferred                \n");
            qrybuf.append("   , a2.name as writer                        \n");
        }

        qrybuf.append(" from QnaBoard a1, People a2        \n");
        qrybuf.append(" where                            \n");
        qrybuf.append("   a1.classnamekeyowner = a2.classnamekeyb4 and a1.ida3owner = a2.ida3b4                    \n");

        if (StringUtil.checkString(title)) {
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("a1.title", title)).append("        \n");
        }
        if (StringUtil.checkString(creator)) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a2.id", creator, false)).append("        \n");
        }
        if (StringUtil.checkString(predate)) {
            qrybuf.append("   and a1.createstampa2 > to_date('").append(predate).append(" 000000', 'YYYY-MM-DD HH24MISS')    \n");
        }
        if (StringUtil.checkString(postdate)) {
            qrybuf.append("   and a1.createstampa2 < to_date('").append(postdate).append(" 235959', 'YYYY-MM-DD HH24MISS')    \n");
        }
        if (StringUtil.checkString(webEditorText)) {
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("utl_raw.cast_to_varchar2(dbms_lob.substr(a1.webEditorText, 1500))", webEditorText)).append("        \n");
        }

        if (stateAry != null && stateAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.state", stateAry, false)).append("        \n");
        }
        if (classificationAry != null && classificationAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.classification", classificationAry, false)).append("        \n");
        }

        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   and a1.depth = 0          \n");
            qrybuf.append("   and a1.preferred = 0          \n");
        }
        else {              // 게시판 검색
            qrybuf.append(" order by a1.preferred desc, decode(a1.ida3parentreference, 0, a1.ida2a2+1, a1.ida3parentreference) desc, a1.createstampa2 desc        \n");
        }

        return qrybuf.toString();
    }

}
