package e3ps.common.treegrid;

/*
 * [PLM System 1차개선]
 * 파일명 : TreeGridUtil.java
 * 설명 : Tree Grid용 유틸리티
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class TreeGridUtil {

    /**
     * TreeGrid에 전달할 <I> 태그 문자열에 사용하기 위해 특수문자 변환 (", ', <, > 등)
     */
    public static String replaceForI(String str) {
	if (str != null) {
	    str = str.replaceAll("\"", "&quot;");
	    str = str.replaceAll("'", "&apos;");
	    str = str.replaceAll("<", "&amp;lt;");
	    str = str.replaceAll(">", "&amp;gt;");
	    str = str.replaceAll("&nbsp;", "&amp;nbsp;"); // 한 단계 더 가기 위해 &amp; 사용
	}
	return str;
    }

    /**
     * 데이터 자체에 쌍따옴표가 들어가 있을 경우 TreeGrid 에러를 막기 위해 다른 기호로 치환
     */
    public static String replaceContentForI(String str) {
	if (str != null) {
	    // str = str.replaceAll("\"", "&apos;&apos;");
	    str = str.replaceAll("\"", "&Prime;");
	}
	return str;
    }

    /**
     * 데이터 자체에 쌍따옴표가 들어가 있을 경우 TreeGrid 에러를 막기 위해 다른 기호로 치환
     */
    public static String replaceContentForQuotXml(String str) {
	if (str != null) {
	    // str = str.replaceAll("\"", "&apos;&apos;");
	    str = str.replaceAll("\"", "&quot;");
	}
	return str;
    }

    public static String replaceContentForQuotHtml(String str) {
	if (str != null) {
	    // str = str.replaceAll("\"", "&apos;&apos;");
	    str = str.replaceAll("\"", "\'");
	}
	return str;
    }

}
