package ext.ket.shared.log;

import wt.method.AuthenticationException;
import wt.method.RemoteAccess;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import ext.ket.shared.log4j.ExLogger;
import ext.ket.shared.log4j.KetLogger;

public class Kogger implements RemoteAccess {

    public static final String EMPTY = "";
    public static final String DELIM = " / ";
    public static final String NULLUSER = "User-Null";
    public static final String NULLSOURCE = "Source-Null";
    public static final String NULLNO = "ObjNo-Null";
    public static final String NULLOID = "ObjOid-Null";
    
    // 사용자별 로그 추적 제어
    public static boolean useUserFilter = false;
    public static String filteringUserId = null;
    
    /**
     * 로그의 종료
     */
    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int ERROR = 2;
    
    public static final int BIZ = 3;
    public static final int PATH = 4;
    private static boolean isRealServer = false;
    
    static{

	try {
	    
	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");
	    // 운영서버 hostName.equals("plm.ket.com")
	    if ("plmapdev.ket.com".equals(hostName)) {
		isRealServer = true;
	    } else {
		isRealServer = false;
	    }
	    
	} catch (Exception e) {
	}
    }

    /**
     * 빈 로그
     * 
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 15.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void debug() {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String message = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void info() {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String message = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error() {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String message = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    /**
     * 단순 message
     * 
     * @param message
     * @메소드명 : debug
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void debug(Object message) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void info(Object message) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(Object message) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(Throwable t) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	String message = null;
	Class claz = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    /**
     * 업무로그 시작 / 끝
     * 
     * @param message
     * @메소드명 : biz
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void biz(String message) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(BIZ, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    /**
     * 메소드 실행 위치정보나 JSP 실행위치정보
     * 
     * @param message
     * @메소드명 : path
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 19.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static void path(String message) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	Class claz = null;
	Throwable t = null;
	executeLog(PATH, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

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
    public static void debug(String jspSource, String objectNo, String ObjectOid, int message) {

	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, String.valueOf(message), t, jspSource);
    }
    
    public static void debug(String jspSource, String objectNo, String ObjectOid, String message) {

	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void debug(String jspSource, String objectNo, String ObjectOid, Object message) {
	
	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void info(String jspSource, String objectNo, String ObjectOid, String message) {

	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void info(String jspSource, String objectNo, String ObjectOid, Object message) {
	
	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(String jspSource, String objectNo, String ObjectOid, Object message) {

	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void error(String jspSource, String objectNo, String ObjectOid, String message) {
	
	String methodName = null;
	Class claz = null;
	Throwable t = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(String jspSource, String objectNo, String ObjectOid, Throwable t) {

	String methodName = null;
	String message = null;
	Class claz = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(String jspSource, String objectNo, String ObjectOid, Object message, Throwable t) {

	String methodName = null;
	Class claz = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    /**
     * java 용 단순
     * 
     */
    public static void debug(Class claz, int message) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	Throwable t = null;
	String jspSource = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void debug(Class claz, Object message) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	Throwable t = null;
	String jspSource = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void info(Class claz, Object message) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	Throwable t = null;
	String jspSource = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void error(Class claz, Throwable t) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String message = null;
	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void error(Class claz, Object message) {

	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	Throwable t = null;
	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    public static void error(Class claz, Object message, Throwable t) {
	
	String methodName = null;
	String objectNo = null;
	String ObjectOid = null;
	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    /**
     * java 용 복합
     * 
     */
    public static void debug(Class claz, String methodName, String objectNo, String ObjectOid, Object message) {

	Throwable t = null;
	String jspSource = null;
	executeLog(DEBUG, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void info(Class claz, String methodName, String objectNo, String ObjectOid, Object message) {

	Throwable t = null;
	String jspSource = null;
	executeLog(INFO, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, Object message) {

	Throwable t = null;
	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, Throwable t) {

	String message = null;
	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }

    public static void error(Class claz, String methodName, String objectNo, String ObjectOid, Object message, Throwable t) {

	String jspSource = null;
	executeLog(ERROR, claz, methodName, objectNo, ObjectOid, message, t, jspSource);
    }
    
    // 실제 로그 처리
    public static void executeLog(int logLevel, Class claz, String methodName, String objectInfo, String oidInfo, Object message, Throwable t, String jspSource){

	// 속도를 생각해서 운영서버이고 특정 사용자 디버깅이 아니면 아무일도 하지 않는다. 
	if( logLevel == DEBUG && isRealServer && !useUserFilter){
	    System.out.println("============== Debugging 중지 =============");
	    return;
	}
	
	// 사용자 정보
	String[] userInfo = getUserInfo();
	String userId = userInfo[0];
	String userName = userInfo[1];
	
	// 소스 정보
	String sourceInfo = null;
	if(claz != null){
	    sourceInfo = (claz == null ? NULLSOURCE : claz.getName() + "::" + (methodName == null ? EMPTY : methodName));
	}else if(jspSource != null){
	    sourceInfo = jspSource; 
	}
	
	// 로그타입별로 분기
	if(logLevel == BIZ){ // ==== 업무로그
	    
	    writeBizLog(KetLogger.ket, userId, userName, message); // KET 기본 로그
	    
	    if(useUserFilter && ( userId.equals("Administrator") // 사용자 로그
		    || userId.equals(filteringUserId) ) ){
		writeBizLog(KetLogger.migration, userId, userName, message);
	    }
	    
	}else if(logLevel == PATH){ // ==== Class내 method 정보 위치
	    
	    writePathLog(KetLogger.ket, userId, userName, message); // KET 기본 로그
	    
	    if(useUserFilter && ( userId.equals("Administrator")  // 사용자 로그
		    || userId.equals(filteringUserId) ) ){
		writePathLog(KetLogger.migration, userId, userName, message);
	    }
	    
	}else{ // ==== 일반적인 경우
	    
	    writeGeneralLog(KetLogger.ket, logLevel, userId, userName, message, sourceInfo, objectInfo, oidInfo, t ); // KET 기본 로그
	    
	    if(useUserFilter && ( userId.equals("Administrator")  // 사용자 로그
		    || userId.equals(filteringUserId) ) ){
		writeGeneralLog(KetLogger.migration, logLevel, userId, userName, message, sourceInfo, objectInfo, oidInfo, t );
	    }
	}
	
    }
    
    // biz log
    private static void writeBizLog(ExLogger logger, String userId, String userName, Object message){
	
	logger.info("==========biz-flag==========");
	logger.info("=== " + userId + DELIM + userName + DELIM + message);
	logger.info("==========biz-flag==========");
    }
    
    // path log
    private static void writePathLog(ExLogger logger, String userId, String userName, Object message){
	
	logger.info("---------method-flag---------");
	logger.info("--- " + userId + DELIM + userName + DELIM + message);
	logger.info("---------method-flag---------");
    }
    
    // general log
    private static void writeGeneralLog(ExLogger logger, int logLevel, String userId, String userName, Object message, String sourceInfo, String objectInfo, String oidInfo, Throwable t ){
	
	if(logLevel == DEBUG){

	    logger.debug(getLogMessage( userId, userName, message, sourceInfo, objectInfo, oidInfo ));

	} else if (logLevel == INFO) {

	    logger.info(getLogMessage( userId, userName, message, sourceInfo, objectInfo, oidInfo ));

	} else if (logLevel == ERROR) {
	    
	    if(t == null){
		logger.error(getLogMessage( userId, userName, message, sourceInfo, objectInfo, oidInfo ));
	    }else{
		logger.error(getLogMessage( userId, userName, message, sourceInfo, objectInfo, oidInfo ), t);
	    }
	}
    }
    
    private static String getLogMessage( String userId, String userName, Object message, String sourceInfo, String objectInfo, String oidInfo ){
	
	return userId + DELIM + userName + 
	(sourceInfo == null ? EMPTY : DELIM + sourceInfo) +
	(objectInfo == null ? EMPTY : DELIM + objectInfo) +
	(oidInfo == null ? EMPTY : DELIM + oidInfo) +
	(message == null ? EMPTY : DELIM + message);

    }

    /**
     * 사용자 정보 처리
     * 
     * @return
     * @메소드명 : getUserInfo
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 15.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static String[] getUserInfo() {

	WTUser user = null;
	try {
	    user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	} catch (AuthenticationException e) {
	} catch (WTException e) {
	} catch (Exception e) {
	}
	String userId = (user != null) ? user.getName() : NULLUSER;
	String userName = (user != null) ? user.getFullName() : NULLUSER;

	return new String[] { userId, userName };
    }
    
    /**
     * 사용자 추적하는 용도
     * 
     * @param userId
     * @throws WTException
     * @메소드명 : setLoggingUser
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 20.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static void setLoggingUserFwd(String userId) throws WTException {

	if (!wt.method.RemoteMethodServer.ServerFlag) {
	
	    try {

		Class[] argTypes = { String.class };
		Object[] args = { userId };
		wt.method.RemoteMethodServer.getDefault().invoke("setLoggingUserConfig", "ext.ket.shared.log.Kogger", null, argTypes, args);

	    } catch (java.lang.reflect.InvocationTargetException e) {
	    } catch (java.rmi.RemoteException rme) {
	    }
	    
	}else{
	    
	    setLoggingUserConfig(userId);
	}

    }
    
    // log 추적할 사용자 정보
    public static void setLoggingUserConfig(String userId) throws WTException {
	
	if(userId == null){
	    useUserFilter = false;
	    filteringUserId = null;
	}else{
	    useUserFilter = true;
	    filteringUserId = userId;
	}
    }
    
    /**
     * Test 용
     * 
     * @param args
     * @메소드명 : main
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 15.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static void main(String[] args) {

    }

}
