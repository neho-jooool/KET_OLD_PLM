package e3ps.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import wt.method.RemoteAccess;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.RelationalExpression;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import e3ps.common.query.LoggableStatement;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : KETQueryUtil.java
 * 설명 : KET Query 유틸리티
 * 작성일자 : 2013. 06. 21
 * 작성자 : 김무준
 */

public class KETQueryUtil implements RemoteAccess {

    /**
     * @param columnName
     *            - 컬럼명
     * @param keywords
     *            - ','로 구분된 다중검색어. '*'(like) 지원. (예: "abcd, *efg, hijk*, *lmn*op*,...")
     * @param ignoreCase
     *            - 대소문자 구분 여부
     * @return SQL 쿼리문
     */
    public static String getSqlQueryForMultiSearch(String columnName, String keywords, boolean ignoreCase) {
	KeywordLists lists = keywordsToLists(keywords, ignoreCase);
	return getSqlQueryForMultiSearch(columnName, lists.likeKeywordList, lists.equalKeywordList, ignoreCase);
    }

    /**
     * @param columnName
     *            - 컬럼명
     * @param keywordAry
     *            - 다중검색어 배열. '*'(like) 지원.
     * @param ignoreCaseAll
     *            - 대소문자 구분 여부
     * @return SQL 쿼리문
     */
    public static String getSqlQueryForMultiSearch(String columnName, String[] keywordAry, boolean ignoreCaseAll) {
	KeywordLists lists = keywordArrayToLists(keywordAry, ignoreCaseAll);
	return getSqlQueryForMultiSearch(columnName, lists.likeKeywordList, lists.equalKeywordList, ignoreCaseAll);
    }

    public static String getSqlQueryForMultiSearch(String columnName, String[] keywordAry, boolean ignoreCaseColumn, boolean ignorecaseValue) {
	KeywordLists lists = keywordArrayToLists(keywordAry, ignorecaseValue);
	return getSqlQueryForMultiSearch(columnName, lists.likeKeywordList, lists.equalKeywordList, ignoreCaseColumn);
    }

    private static String getSqlQueryForMultiSearch(String columnName, List<String> likeKeywordList, List<String> equalKeywordList, boolean ignoreCase) {
	StringBuilder sb = new StringBuilder();
	
	// 무조건 대소문자를 가리지 않게한다.
	/*
	if (ignoreCase == true) columnName = "upper(" + columnName + ")";
	*/
	
	boolean appended = false;

	sb.append("( ");

	if (likeKeywordList != null && likeKeywordList.size() > 0) {
	    for (int i = 0; i < likeKeywordList.size(); ++i) {
		String likeKeyword = likeKeywordList.get(i);
		if (i > 0) {
		    sb.append(" or ");
		}
		
		if(ignoreCase){ // 대소문자를 무시한다는 의미이므로 => 대소문자와 무관하게 검색이 되어야 함. likeKeyword는 이미 대문자로 변경되었음.
		    sb.append("UPPER(" + columnName + ")").append(" like '").append(makeSqlLikeCondition(likeKeyword)).append("' ");
		}else{
		    sb.append(columnName).append(" like '").append(makeSqlLikeCondition(likeKeyword)).append("' ");
		}
		
		if (appended == false) appended = true;
	    }
	}

	if (equalKeywordList != null && equalKeywordList.size() > 0) {
	    if (appended == true) {
		sb.append(" or ");
	    }

	    if (equalKeywordList.size() == 1) {
		if(ignoreCase){ // 대소문자를 무시한다는 의미이므로 => 대소문자와 무관하게 검색이 되어야 함. likeKeyword는 이미 대문자로 변경되었음.
		    sb.append("UPPER(" + columnName + ")").append(" = '").append(equalKeywordList.get(0)).append("' ");
		}else{
		    sb.append(columnName).append(" = '").append(equalKeywordList.get(0)).append("' ");
		}
	    }
	    else {
		if(ignoreCase){ // 대소문자를 무시한다는 의미이므로 => 대소문자와 무관하게 검색이 되어야 함. equalKeywordList는 이미 대문자로 변경되었음.
		    sb.append("UPPER(" + columnName + ")").append(" in (");
		}else{
		    sb.append(columnName).append(" in (");
		}
		    
		for (int i = 0; i < equalKeywordList.size(); ++i) {
		    String equalKeyword = equalKeywordList.get(i);
		    if (i > 0) {
			sb.append(", ");
		    }
		    sb.append("'").append(equalKeyword).append("'");
		}
		sb.append(") ");
	    }
	}

	sb.append(") ");

	return sb.toString();
    }

    public static String makeSqlLikeCondition(String keyword) {
	String cond = keyword.replaceAll("\\*", "%");
	return cond;
    }

    /**
     * @param qs
     *            - QuerySpec 객체
     * @param targetClass
     *            - 검색어로 검색할 대상 객체 클래스
     * @param targetIndex
     * @param attributeName
     *            - targetClass의 속성명
     * @param keywords
     *            - ','로 구분된 다중검색어. '*'(like) 지원.
     * @param ignoreCase
     *            - 대소문자 구분 여부
     * @throws wt.query.QueryException
     */
    public static void setQuerySpecForMultiSearch(QuerySpec qs, Class<?> targetClass, int targetIndex, String attributeName, String keywords, boolean ignoreCase) throws wt.query.QueryException {
	KeywordLists lists = keywordsToLists(keywords, ignoreCase);
	setQuerySpecForMultiSearch(qs, targetClass, targetIndex, attributeName, lists.likeKeywordList, lists.equalKeywordList, ignoreCase);
    }

    /**
     * @param qs
     *            - QuerySpec 객체
     * @param targetClass
     *            - 검색어로 검색할 대상 객체 클래스
     * @param targetIndex
     * @param attributeName
     *            - targetClass의 속성명
     * @param keywordAry
     *            - 다중검색어 배열. '*'(like) 지원.
     * @param ignoreCase
     *            - 대소문자 구분 여부
     * @throws wt.query.QueryException
     */
    public static void setQuerySpecForMultiSearch(QuerySpec qs, Class<?> targetClass, int targetIndex, String attributeName, String[] keywordAry, boolean ignoreCase) throws wt.query.QueryException {
	KeywordLists lists = keywordArrayToLists(keywordAry, ignoreCase);
	setQuerySpecForMultiSearch(qs, targetClass, targetIndex, attributeName, lists.likeKeywordList, lists.equalKeywordList, ignoreCase);
    }

    private static void setQuerySpecForMultiSearch(QuerySpec qs, Class<?> targetClass, int targetIndex, String attributeName, List<String> likeKeywordList, List<String> equalKeywordList,
	    boolean ignoreCase) throws wt.query.QueryException {
	RelationalExpression leftHandSide = null;
	ClassAttribute targetClassAttr = new ClassAttribute(targetClass, attributeName);
	if (ignoreCase == true) {
	    leftHandSide = SQLFunction.newSQLFunction(SQLFunction.UPPER, targetClassAttr);
	}
	else {
	    leftHandSide = targetClassAttr;
	}
	int[] targetIndexAry = new int[] { targetIndex };
	boolean appended = false;

	qs.appendOpenParen();

	if (likeKeywordList != null && likeKeywordList.size() > 0) {
	    for (int i = 0; i < likeKeywordList.size(); ++i) {
		String likeKeyword = likeKeywordList.get(i);
		if (i > 0) {
		    qs.appendOr();
		}

		SearchCondition sc = new SearchCondition(leftHandSide, SearchCondition.LIKE, ConstantExpression.newExpression((makeSqlLikeCondition(likeKeyword))));
		qs.appendWhere(sc, targetIndexAry);

		if (appended == false) appended = true;
	    }
	}

	if (equalKeywordList != null && equalKeywordList.size() > 0) {
	    if (appended == true) {
		qs.appendOr();
	    }

	    SearchCondition sc;
	    if (equalKeywordList.size() == 1) {
		sc = new SearchCondition(leftHandSide, SearchCondition.EQUAL, ConstantExpression.newExpression(equalKeywordList.get(0)));
	    }
	    else {
		sc = new SearchCondition(leftHandSide, SearchCondition.IN, new ArrayExpression(equalKeywordList.toArray()));
	    }
	    qs.appendWhere(sc, targetIndexAry);
	}

	qs.appendCloseParen();
    }

    // (for 다중 검색)
    private static class KeywordLists {
	List<String> likeKeywordList  = null;
	List<String> equalKeywordList = null;
    }

    private static KeywordLists keywordArrayToLists(String[] keywordAry, boolean ignoreCase) {
	KeywordLists lists = new KeywordLists();

	for (String keyword : keywordAry) {
	    keyword = keyword.trim();
	    if (keyword.length() > 0) {
		// ignoreCase : 대소문자를 무시한다는 의미 : 따라서 대소문자 무관하게 검색이 되어야 함.
		// 문자를 미리 대문자로 변경함.
		if (ignoreCase == true) keyword = keyword.toUpperCase();

		if (keyword.indexOf("*") != -1) {
		    if (lists.likeKeywordList == null) {
			lists.likeKeywordList = new ArrayList<String>();
		    }
		    lists.likeKeywordList.add(keyword);
		}
		else {
		    if (lists.equalKeywordList == null) {
			lists.equalKeywordList = new ArrayList<String>();
		    }
		    lists.equalKeywordList.add(keyword);
		}
	    }
	}

	return lists;
    }

    private static KeywordLists keywordsToLists(String keywords, boolean ignoreCase) {
	String[] keywordAry = keywords.split(",");
	return keywordArrayToLists(keywordAry, ignoreCase);
    }

    /**
     * sql 쿼리를 실행하여 결과를 collection으로 반환하는 범용 method. 컬럼값은 type에 따라 다른 형식의 객체로 저장되므로 꺼낼 때 주의. (getObjectByType() 참고)
     * 
     * @param sql
     *            - sql 쿼리
     * @param conn
     *            - JDBC 연결
     * @return <필드명(대문자)>=<값>의 map 형식의 row들을 저장한 list
     * @throws Exception
     */
    public static List<SqlRowMap> getSqlResultList(String sql, Connection conn) throws Exception {
	List<SqlRowMap> list = new ArrayList<SqlRowMap>();

	LoggableStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = new LoggableStatement(conn, sql);
	    rs = pstmt.executeQuery();
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int colCnt = rsmd.getColumnCount();
	    while (rs.next()) {
		SqlRowMap row = new SqlRowMap();
		list.add(row);
		for (int i = 1; i <= colCnt; ++i) {
		    String name = rsmd.getColumnLabel(i);
		    Object value = getObjectByType(rs, rsmd, i);
		    row.put(name.toUpperCase(), value);
		}
	    }
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}

	return list;
    }

    /**
     * 컬럼 type에 따라 다른 형식의 컬럼값 객체 구함 (현재 HelpBoard에서 사용되는 type만 고려)
     * 
     * @param rs
     * @param rsmd
     * @param columnIndex
     *            - 1부터 시작
     * @return 컬럼값 객체
     * @throws SQLException
     * @throws IOException
     */
    private static Object getObjectByType(ResultSet rs, ResultSetMetaData rsmd, int columnIndex) throws SQLException, IOException {
	Object ret = null;

	int sqlType = rsmd.getColumnType(columnIndex);
	if (sqlType == Types.VARCHAR || sqlType == Types.CHAR) {
	    ret = rs.getString(columnIndex);
	}
	else if (sqlType == Types.INTEGER || sqlType == Types.NUMERIC) {
	    ret = rs.getInt(columnIndex);
	}
	else if (sqlType == Types.TIMESTAMP || sqlType == Types.DATE) {
	    ret = rs.getTimestamp(columnIndex);
	}
	else if (sqlType == Types.BLOB) {
	    BufferedInputStream bis = null;
	    try {
		Blob blob = rs.getBlob(columnIndex);
		if (blob != null) {
		    bis = new BufferedInputStream(blob.getBinaryStream());
		    int blen = (int) blob.length();
		    byte[] buf = new byte[blen];
		    int rlen = bis.read(buf, 0, blen);
		    if (blen != rlen) {
			Kogger.debug(KETQueryUtil.class, "KETQueryUtil.getObjectByType: blen=" + blen + ", rlen=" + rlen);
		    }
		    ret = buf;
		}
	    } finally {
		if (bis != null) try {
		    bis.close();
		} catch (Exception e) {
		}
	    }
	}
	else {
	    Kogger.debug(KETQueryUtil.class, "KETQueryUtil.getObjectByType: other sqlType=" + rsmd.getColumnTypeName(columnIndex));
	    ret = rs.getObject(columnIndex);
	}

	return ret;
    }

}
