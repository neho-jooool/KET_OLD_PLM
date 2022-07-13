package e3ps.common.util;

import java.util.List;
import java.util.Map;

/**
 * [PLM System 1차개선]
 * 클래스명 : KETGridUtil
 * 설명 : KET EJS TreeGrid 관련 Utility
 * 작성자 : BoLee
 * 작성일자 : 2013. 6. 21.
 */

public final class KETGridUtil {

    /**
     * Grid Key Enumeration Type 생성
     *
     * @param paramList
     * @param isContainSpace
     * @return String
     */
    public static String getKeyEnum(List<KETParamMapUtil> paramList, boolean isContainSpace) {

        StringBuffer returnBuff = new StringBuffer();

        // 첫번째 값으로 공백 포함
        if ( isContainSpace ) {
            returnBuff.append( "|" );
        }

        for ( KETParamMapUtil codeMap : paramList ) {

            returnBuff.append( "|" + codeMap.getString("key") );
        }

        return returnBuff.toString();
    }

    /**
     * Grid Value Enumeration Type 생성
     *
     * @param paramList
     * @param isContainSpace
     * @return String
     */
    public static String getValueEnum(List<KETParamMapUtil> paramList, boolean isContainSpace) {

        StringBuffer returnBuff = new StringBuffer();

        // 첫번째 값으로 공백 포함
        if ( isContainSpace ) {
            returnBuff.append( "|" );
        }

        for ( KETParamMapUtil codeMap : paramList ) {

            returnBuff.append( "|" + codeMap.getString("value") );
        }

        return returnBuff.toString();
    }

    /**
     * 공통코드로부터 Grid Key Enumeration Type 생성
     *
     * @param paramList
     * @param isContainSpace
     * @return String
     */
    public static String getKeyEnumFromNumCode(List<Map<String, Object>> paramList, boolean isContainSpace) {

        StringBuffer returnBuff = new StringBuffer();

        // 첫번째 값으로 공백 포함
        if ( isContainSpace ) {
            returnBuff.append( "|" );
        }

        for ( Map<String, Object> codeMap : paramList ) {

            returnBuff.append( "|" + codeMap.get("code") );
        }

        return returnBuff.toString();
    }

    /**
     * 공통코드로부터 Grid Value Enumeration Type 생성
     *
     * @param paramList
     * @param isContainSpace
     * @return String
     */
    public static String getValueEnumFromNumCode(List<Map<String, Object>> paramList, boolean isContainSpace) {

        StringBuffer returnBuff = new StringBuffer();

        // 첫번째 값으로 공백 포함
        if ( isContainSpace ) {
            returnBuff.append( "|" );
        }

        for ( Map<String, Object> codeMap : paramList ) {

            returnBuff.append( "|" + codeMap.get("value") );
        }

        return returnBuff.toString();
    }
}
