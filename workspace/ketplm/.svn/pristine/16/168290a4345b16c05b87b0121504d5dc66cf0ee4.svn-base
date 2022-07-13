package ext.ket.shared.log;

import ext.ket.shared.log4j.KetLogger;

public class Dogger {

    public static final String EMPTY = Kogger.EMPTY;
    public static final String DELIM = Kogger.DELIM;
    public static final String NULLUSER = Kogger.NULLUSER;
    public static final String NULLSOURCE = Kogger.NULLSOURCE;
    public static final String NULLNO = Kogger.NULLNO;
    public static final String NULLOID = Kogger.NULLOID;

    /**
     * 단순 debugging 용
     * 
     * @param message
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    // public static void debug(Object message) {
    // KetLogger.ket.debug(message);
    // }

    // public static void info(Object message) {
    // KetLogger.ket.info(message);
    // }

    // public static void error(Throwable e) {
    // KetLogger.ket.error(e);
    // String message = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e);
    // }

    /**
     * jsp 용
     * 
     * @param source
     * @param objectNo
     * @param ObjectOid
     * @param message
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    // public static void debug(String source, String objectNo, String ObjectOid, String message) {
    //
    // String[] userInfo = getUserInfo();
    // String userId = userInfo[0];
    // String userName = userInfo[1];
    // String sourceInfo = (source == null ? NULLSOURCE : source);
    // String objectInfo = (objectNo == null ? NULLNO : objectNo);
    // String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);
    //
    // KetLogger.ket.debug(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    // }

    public static void info(String source, String objectNo, String ObjectOid, String message) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (source == null ? NULLSOURCE : source);
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.info(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    }

    public static void error(String source, String objectNo, String ObjectOid, String message) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (source == null ? NULLSOURCE : source);
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    }

    public static void error(String source, String objectNo, String ObjectOid, Throwable t) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (source == null ? NULLSOURCE : source);
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo, t);
    }

    public static void error(String source, String objectNo, String ObjectOid, String message, Throwable t) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (source == null ? NULLSOURCE : source);
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message, t);
    }

    /**
     * java 용
     * 
     * @param claz
     * @param methodName
     * @param objectNo
     * @param ObjectOid
     * @param message
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    // public static void debug(Class claz, String methodName, String objectNo, String ObjectOid, String message) {
    //
    // String[] userInfo = getUserInfo();
    // String userId = userInfo[0];
    // String userName = userInfo[1];
    // String sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
    // String objectInfo = (objectNo == null ? NULLNO : objectNo);
    // String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);
    //
    // KetLogger.ket.debug(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    // }

    public static void info(Class claz, String methodName, String objectNo, String ObjectOid, String message) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.debug(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, String message) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, Throwable t) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo, t);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, String message, Throwable t) {

	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	String sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
	String objectInfo = (objectNo == null ? NULLNO : objectNo);
	String oidInfo = (ObjectOid == null ? NULLOID : ObjectOid);

	KetLogger.ket.error(userId + DELIM + userName + DELIM + sourceInfo + DELIM + objectInfo + DELIM + oidInfo + DELIM + message, t);
    }

    private static String[] getUserInfo() {

	return Kogger.getUserInfo();
    }

    public static void main(String[] args) {

    }

}
