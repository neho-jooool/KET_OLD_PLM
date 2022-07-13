package e3ps.groupware.board.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.board.beans.HelpBoardUtil;

/*
 * [PLM System 1차개선]
 * 파일명 : ImproveBoardDao.java
 * 설명 : 시스템개선요청 Board용 DAO
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ImproveBoardDao extends HelpBoardDao {

    public ImproveBoardDao(Connection conn) {
        super(conn);
    }

    /**
     * 게시판 테이블 명 반환
     * @return 게시판 테이블 명
     */
    @Override
    protected String getBoardTableName() {
        return "ImproveBoard";
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
        String[] divisionAry = map.getStringArray("division");

        String lang = map.getString("userlang");

        StringBuilder qrybuf = new StringBuilder();
        
        Calendar cal = Calendar.getInstance();
        if(StringUtils.isEmpty(predate) && StringUtils.isEmpty(postdate)){
            postdate =  new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            cal.add(Calendar.MONTH, -3);
            predate =  new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            
        }

        qrybuf.append(" select                          \n");

        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   count(1) as cnt          \n");
        }
        else {              // 게시판 검색
            qrybuf.append("   a1.classnamea2a2||':'||a1.ida2a2 as docOid                    \n");
            qrybuf.append("   , a1.classification           \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE_PARENT('IMPROVETYPE', a1.classification, '" + lang + "') as classification1Value   \n"); // 분류1 value
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('IMPROVETYPE', a1.classification, '" + lang + "') as classificationValue   \n"); // 분류2 value
            qrybuf.append("   , a1.title                    \n");
            qrybuf.append("   , a1.state                    \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('IMPROVESTATUS', a1.state, '" + lang + "') as stateValue   \n"); // 상태 value
            qrybuf.append("   , a1.division                 \n");
            qrybuf.append("   , FN_GET_NUMBERCODEVALUE('IMPROVEITEM', a1.division, '" + lang + "') as divisionValue   \n"); // 구분 value
            qrybuf.append("   , a1.createstampa2 as writeDate           \n");
            qrybuf.append("   , a1.readcount                \n");
            qrybuf.append("   , a1.classnamekeyparentreference||':'||a1.ida3parentreference as parentOid            \n");
            qrybuf.append("   , a1.depth                \n");
            qrybuf.append("   , a1.preferred                \n");
            qrybuf.append("   , a2.name as writer                       \n");
        }

        qrybuf.append(" from ImproveBoard a1, People a2     \n");
        qrybuf.append(" where                           \n");
        qrybuf.append("   a1.classnamekeyowner = a2.classnamekeyb4 and a1.ida3owner = a2.ida3b4                 \n");

        if (StringUtil.checkString(title)) {
//            qrybuf.append("   and upper(a1.title) like '").append(HelpBoardUtil.makeQueryForLike(title.toUpperCase())).append("'       \n");
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("a1.title", title)).append("        \n");
        }
        if (StringUtil.checkString(creator)) {
//            qrybuf.append("   and a2.id = '").append(creator).append("'               \n");
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a2.id", creator, false)).append("        \n");
        }
        if (StringUtil.checkString(predate)) {
            qrybuf.append("   and a1.createstampa2 > to_date('").append(predate).append(" 000000', 'YYYY-MM-DD HH24MISS')    \n");
        }
        if (StringUtil.checkString(postdate)) {
            qrybuf.append("   and a1.createstampa2 < to_date('").append(postdate).append(" 235959', 'YYYY-MM-DD HH24MISS')    \n");
        }
        if (StringUtil.checkString(webEditorText)) {
//            qrybuf.append("   and upper(utl_raw.cast_to_varchar2(dbms_lob.substr(a1.webEditorText, 1500))) like '%").append(webEditorText.toUpperCase()).append("%'        \n");
            qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("utl_raw.cast_to_varchar2(dbms_lob.substr(a1.webEditorText, 1500))", webEditorText)).append("        \n");
        }

        if (stateAry != null && stateAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.state", stateAry, false)).append("        \n");
        }
        if (classificationAry != null && classificationAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.classification", classificationAry, false)).append("        \n");
        }
        if (divisionAry != null && divisionAry.length > 0) {
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("a1.division", divisionAry, false)).append("        \n");
        }

        if ( isCnt ) {      // 게시판 건수(답변 제외) 검색
            qrybuf.append("   and a1.depth = 0          \n");
            qrybuf.append("   and a1.preferred = 0          \n");
        }
        else {              // 게시판 검색
            qrybuf.append(" order by a1.preferred desc, decode(a1.ida3parentreference, 0, a1.ida2a2+1, a1.ida3parentreference) desc, a1.createstampa2 desc     \n");
        }

        return qrybuf.toString();
    }

}
