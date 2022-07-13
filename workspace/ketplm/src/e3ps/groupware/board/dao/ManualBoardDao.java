package e3ps.groupware.board.dao;

import java.sql.Connection;
import java.util.Map;

import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.board.beans.HelpBoardUtil;

/*
 * [PLM System 1차개선]
 * 파일명 : ManualBoardDao.java
 * 설명 : PLM Manual Board용 DAO
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ManualBoardDao extends HelpBoardDao {

    public ManualBoardDao(Connection conn) {
        super(conn);
    }

    /**
     * 게시판 테이블 명 반환
     * @return 게시판 테이블 명
     */
    @Override
    protected String getBoardTableName() {
        return "ManualBoard";
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
        String predate = map.getString("predate");
        String postdate = map.getString("postdate");
        String webEditorText = map.getString("webEditorText");
        String lang = map.getString("userlang");
        
        // MultiCombo
        String[] classificationAry = map.getStringArray("classification");
        String[] classificationAry2 = map.getStringArray("classification3");
        StringBuilder qrybuf = new StringBuilder();

        qrybuf.append(" select 							\n");


        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   count(1) as cnt         \n");
        }
        else {              // 게시판 검색
            qrybuf.append("   a1.classnamea2a2||':'||a1.ida2a2 as docOid                    \n");
            qrybuf.append("   , a1.classification           \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE_PARENT('IMPROVETYPE', a1.classification2, '" + lang + "') as classification1Value   \n"); // 분류1 value
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('IMPROVETYPE', a1.classification2, '" + lang + "') as classification2Value   \n"); // 분류2 value
            qrybuf.append("   , a1.title                    \n");
            qrybuf.append("   , a1.createstampa2 as writeDate           \n");
            qrybuf.append("   , a1.readcount                \n");
            qrybuf.append("   , a1.preferred                \n");
            qrybuf.append("   , a2.name as writer                       \n");
        }

        qrybuf.append(" from ManualBoard a1, People a2		\n");
        qrybuf.append(" where							\n");
        qrybuf.append("   a1.classnamekeyowner = a2.classnamekeyb4 and a1.ida3owner = a2.ida3b4					\n");

        if (StringUtil.checkString(title)) {
//            qrybuf.append("   and upper(a1.title) like '").append(HelpBoardUtil.makeQueryForLike(title.toUpperCase())).append("'       \n");
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("a1.title", title)).append("		\n");
        }
        if (StringUtil.checkString(predate)) {
            qrybuf.append("   and a1.createstampa2 > to_date('").append(predate).append(" 000000', 'YYYY-MM-DD HH24MISS')	\n");
        }
        if (StringUtil.checkString(postdate)) {
            qrybuf.append("   and a1.createstampa2 < to_date('").append(postdate).append(" 235959', 'YYYY-MM-DD HH24MISS')	\n");
        }
        if (StringUtil.checkString(webEditorText)) {
//            qrybuf.append("   and upper(utl_raw.cast_to_varchar2(dbms_lob.substr(a1.webEditorText, 1500))) like '%").append(webEditorText.toUpperCase()).append("%'		\n");
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("utl_raw.cast_to_varchar2(dbms_lob.substr(a1.webEditorText, 1500))", webEditorText)).append("		\n");
        }

        if (classificationAry != null && classificationAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.classification", classificationAry, false)).append("		\n");
        }
        
        if (classificationAry2 != null && classificationAry2.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.classification2", classificationAry2, false)).append("		\n");
        }


        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   and a1.depth = 0          \n");
            qrybuf.append("   and a1.preferred = 0          \n");
        }
        else {              // 게시판 검색
            qrybuf.append(" order by a1.preferred desc, a1.createstampa2 desc		\n");
        }

        return qrybuf.toString();
    }

}
