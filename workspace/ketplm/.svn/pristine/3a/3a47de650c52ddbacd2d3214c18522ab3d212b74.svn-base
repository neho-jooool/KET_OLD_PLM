package e3ps.common.util;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import e3ps.common.code.NumberCodeDao;

/*
 * [PLM System 1차개선]
 * 파일명   : NumverCodeUtil.java
 * 설명     : NumverCodeUtil
 * 작성일자 : 2013. 07. 04
 * 작성자   : 김종호
 */
public class NumberCodeUtil {

    /**
     * 함수명 : getNumberCodeValue 설명 : CodeType, Code로 NumberCodeValue Table의 한국어 Value 값 추출
     * 
     * @param codeType
     * @param code
     * @return NumberCodeValue의 한국어 Value
     * @throws Exception
     */
    public static String getNumberCodeValue(String codeType, String code) throws Exception {
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao numberCodeDao = new NumberCodeDao(conn);

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("codeType", codeType);
	    parameter.put("code", code);
	    parameter.put("locale", "ko");

	    return numberCodeDao.getNumberCodeValue(parameter);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : getNumberCodeValue 설명 : CodeType, Code, Locale로 NumberCodeValue Table의 Value 값 추출
     * 
     * @param codeType
     * @param code
     * @param locale
     * @return NumberCodeValue의 사용자 Locale Value
     * @throws Exception
     */
    public static String getNumberCodeValue(String codeType, String code, String locale) throws Exception {
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao numberCodeDao = new NumberCodeDao(conn);

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("codeType", codeType);
	    parameter.put("code", code);
	    parameter.put("locale", locale);

	    return numberCodeDao.getNumberCodeValue(parameter);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : getNumberCodeValue_Oid 설명 : CodeType, Oid, Locale로 NumberCodeValue Table의 Value 값 추출
     * 
     * @param codeType
     * @param oid
     * @param locale
     * @return NumberCodeValue의 사용자 Locale Value
     * @throws Exception
     */
    public static String getNumberCodeValue_Oid(String codeType, String oid, String locale) throws Exception {
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao numberCodeDao = new NumberCodeDao(conn);

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("codeType", codeType);
	    parameter.put("oid", oid);
	    parameter.put("locale", locale);

	    return numberCodeDao.getNumberCodeValue_Oid(parameter);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    public static Map<String, Object> getNumberCodeValueMap(String codeType, String code, String locale) throws Exception {
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao numberCodeDao = new NumberCodeDao(conn);

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("codeType", codeType);
	    parameter.put("code", code);
	    parameter.put("locale", locale);

	    return numberCodeDao.getNumberCodeValueMap(parameter);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    public static String getNumberCodeMap(String codeType, String code) throws Exception {
	Connection conn = null;
	try {
	    conn = PlmDBUtil.getConnection();
	    NumberCodeDao numberCodeDao = new NumberCodeDao(conn);

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    parameter.put("codeType", codeType);
	    parameter.put("code", code);

	    return numberCodeDao.getNumberCodeMap(parameter);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }
}
