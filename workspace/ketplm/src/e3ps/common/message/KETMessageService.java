package e3ps.common.message;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import wt.org.WTUser;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : KETMessageService.java
 * 설명 : 다국어 처리 모듈
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class KETMessageService implements Serializable {

    private static final long	       serialVersionUID    = 4109281814651032032L;

    private static final String	     KEY_MESSAGE_SERVICE = "messageService";

    private static XMLResourceBundleControl INSTANCE	    = new XMLResourceBundleControl();

    private Locale			  locale	      = null;

    public KETMessageService() {
	setLocale(Locale.KOREAN);
    }

    /**
     * @param locale
     *            - 객체 생성 시 초기화할 Locale
     */
    public KETMessageService(Locale locale) {
	if (locale == null) {
	    locale = Locale.KOREAN; // default: 한국어
	    Kogger.debug(getClass(), "KETMessageService: locale is null! (init to " + locale + ")");
	}
	setLocale(locale);
    }

    /**
     * [START] singleton 기능 추가
     */
    public static KETMessageService service = null;

    // 맨처음 한번 실행(필수)
    public static void initService(String language) {
	// Locale lo = getUserLocale(nationalCode);
	Locale lo = chooseLocale(language);
	service = new KETMessageService(lo);
    }

    /**
     * 리소스 정보를 가져올때 Windchill SessionContext에서 가져올수 있도록 변경
     * 
     * @param baseName
     * @param locale
     * @return
     * @메소드명 : getBundle
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private static ResourceBundle getBundle(String baseName, Locale locale) {

	SessionContext context = SessionContext.getContext();
	service = (KETMessageService) context.get(KEY_MESSAGE_SERVICE);
	if (service == null) {
	    service = new KETMessageService(locale);
	    service.locale = locale;
	    context.put(KEY_MESSAGE_SERVICE, service);
	}

	return ResourceBundle.getBundle(baseName, locale, service.INSTANCE);
    }

    /**
     * [END] singleton 기능 추가
     */

    /**
     * @param locale
     *            - 설정할 Locale
     * @return 변경되었는지 여부 (변경되었으면 true)
     */
    public synchronized boolean setLocale(Locale locale) {
	if (locale == null) {
	    return false;
	} else if (this.locale != null && this.locale.getLanguage().equalsIgnoreCase(locale.getLanguage())) {
	    return false;
	} else {
	    // Kogger.debug(getClass(), "# KETMessageService.setLocale: [" + ((this.locale!=null)?this.locale.getLanguage():"null") + "] => [" + locale.getLanguage() + "]");
	    // Kogger.debug(getClass(), "# KETMessageService.setLocale: [" + this.locale + "] => [" + locale + "]");
	    /*
	     * setLocale 호출 시에 People의 nationalCode를 업데이트 한다.
	     */
	    this.locale = locale;
	    /*
	     * try {
	     * People people = PeopleHelper.manager.getPeople(SessionHelper.manager.getPrincipal().getName());
	     * people.setNationalCode(locale.toString());
	     * PersistenceHelper.manager.save(people);
	     * } catch (WTException e) {
	     * Kogger.error(getClass(), e);
	     * } catch (WTPropertyVetoException e) {
	     * Kogger.error(getClass(), e);
	     * }
	     */
	    return true;
	}
    }

    public Locale getLocale() {
	return locale;
    }

    /**
     * 로케일 비교
     * 
     * @param locale
     *            - 비교할 Locale
     * @return 동일한 로케일이면 true.
     */
    public boolean isSameLanguage(Locale locale) {
	return (locale != null && this.locale != null && this.locale.getLanguage().equalsIgnoreCase(locale.getLanguage()));
    }

    private String getMessageResource(String baseName, String key) {
	String ret = null;
	try {
	    ResourceBundle bundle = getBundle(baseName, locale);
	    ret = bundle.getString(key);
	} catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    // for simple message
	    StringBuilder sb = new StringBuilder();
	    sb.append(e.toString());
	    Throwable cause = e.getCause();
	    if (cause != null) {
		sb.append("\n Caused By : ").append(cause.toString());
	    }
	    Kogger.debug(getClass(), sb.toString());

	    ret = key;
	}
	return ret;
    }

    /**
     * @param baseName
     *            - the base name of the resource bundle, a fully qualified class name
     * @param key
     *            - bundle key, the key for the desired string
     * @return the string for the given key
     */
    public String getString(String baseName, String key) {
	String str = getMessageResource(baseName, key);
	if (canHaveArguments(str) == true) {
	    str = format(str);
	}
	return str;
    }

    /**
     * (java.text.MessageFormat 참고)
     * 
     * @param baseName
     *            - the base name of the resource bundle, a fully qualified class name
     * @param key
     *            - bundle key, the key for the desired string(
     * @param arguments
     *            - an array of objects to be formatted and substituted.
     * @return the string for the given key and arguments
     */
    public String getString(String baseName, String key, Object... arguments) {
	String str = getMessageResource(baseName, key);
	if (canHaveArguments(str) == true) {
	    str = format(str, arguments);
	}
	return str;
    }

    private static boolean canHaveArguments(String str) {
	return (str != null && str.length() > 0 && str.indexOf("{") != -1 && str.indexOf("}") != -1);
    }

    private static String format(String pattern, Object... arguments) {
	MessageFormat mf = new MessageFormat(pattern);
	int cnt = mf.getFormatsByArgumentIndex().length;
	Object[] argAry = new Object[cnt];
	for (int i = 0; i < cnt; ++i) {
	    if (arguments != null && i < arguments.length) {
		argAry[i] = arguments[i];
	    } else {
		argAry[i] = ""; // default
	    }
	}
	StringBuffer ret = mf.format(argAry, new StringBuffer(), null);
	return ret.toString();
    }

    /**
     * 공통코드 리스트 구함
     * 
     * @param baseName
     *            - the base name of the resource bundle, a fully qualified class name
     * @param masterKey
     *            - 상위 마스터 key
     * @return masterKey에 해당하는 하위 요소 리스트
     */
    public List<CodeInfo> getCodeInfoList(String baseName, String masterKey) {
	List<CodeInfo> list = new ArrayList<CodeInfo>();
	try {
	    String masterKeyFilter = masterKey + "[";
	    int masterKeyFilterLen = masterKeyFilter.length();
	    ResourceBundle bundle = getBundle(baseName, locale);
	    Set<String> keysOrdered = new TreeSet<String>(bundle.keySet());
	    for (String key : keysOrdered) {
		if (key.startsWith(masterKeyFilter) && key.length() > masterKeyFilterLen) {
		    String code = getCodeFromKey(key, masterKeyFilter, masterKeyFilterLen);
		    String label = bundle.getString(key);
		    Kogger.debug(getClass(), "key=" + key + ",code=" + code + ",label=" + label);
		    list.add(new CodeInfo(key, masterKey, code, label));
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return list;
    }

    /**
     * bundle key에서 code 값 추출 (내부용)
     * 
     * @param key
     *            - bundle key
     * @param masterKeyFilter
     * @param masterKeyFilterLen
     * @return
     */
    private static String getCodeFromKey(String key, String masterKeyFilter, int masterKeyFilterLen) {
	String ret = null;
	int dotIdx = key.indexOf(".", masterKeyFilterLen);
	ret = key.substring(dotIdx + 1);
	return ret;
    }

    /**
     * 해당 code 값에 대한 공통코드 정보 구함
     * 
     * @param baseName
     *            - the base name of the resource bundle, a fully qualified class name
     * @param masterKey
     *            - 상위 마스터 key
     * @param code
     *            - code 값
     * @return 해당 code 값에 대한 CodeInfo 객체
     */
    public CodeInfo getCodeInfo(String baseName, String masterKey, String code) {
	if (code == null)
	    return null;

	try {
	    String masterKeyFilter = masterKey + "[";
	    int masterKeyFilterLen = masterKeyFilter.length();
	    ResourceBundle bundle = getBundle(baseName, locale);
	    Set<String> keys = bundle.keySet();
	    for (String key : keys) {
		if (key.startsWith(masterKeyFilter) && key.length() > masterKeyFilterLen) {
		    String tcode = getCodeFromKey(key, masterKeyFilter, masterKeyFilterLen);
		    if (tcode.equals(code)) {
			String label = bundle.getString(key);
			return new CodeInfo(key, masterKey, code, label);
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    /**
     * 해당 code 값에 대한 label 구함
     * 
     * @param baseName
     *            - the base name of the resource bundle, a fully qualified class name
     * @param masterKey
     *            - 상위 마스터 key
     * @param code
     *            - code 값
     * @return 해당 code 값에 대한 label
     */
    public String getLabel(String baseName, String masterKey, String code) {
	CodeInfo codeInfo = getCodeInfo(baseName, masterKey, code);
	if (codeInfo != null) {
	    return codeInfo.label;
	}
	return "";
    }

    /*
     * 공통코드 관련 정보 저장용
     */
    public static class CodeInfo {
	public String key       = null;
	public String masterKey = null;
	public String code      = null;
	public String label     = null;

	public CodeInfo(String key, String masterKey, String code, String label) {
	    this.key = key;
	    this.masterKey = masterKey;
	    this.code = code;
	    this.label = label;
	}

	public String toString() {
	    return "code=[" + code + "], label=[" + label + "] (key=" + key + ")(masterKey=" + masterKey + ")";
	}
    }

    /**
     * 세션에서 서비스 객체 구함
     * 
     * @param session
     *            - 현 사용자 세션
     * @return 저장되었던 메시지 서비스 객체. 없으면 null.
     */
    public static KETMessageService getMessageService(HttpSession session) {
	return (KETMessageService) session.getAttribute(KEY_MESSAGE_SERVICE);
    }

    public static KETMessageService getMessageService(HttpServletRequest request) {
	HttpSession session = request.getSession();
	KETMessageService ketMessageService = getMessageService(session);
	if(ketMessageService == null){
	    ketMessageService = getMessageService();
	}
	return ketMessageService;
    }

    /**
     * Controller, Service에서 request없이 메세지 서비스를 사용하기 위한 메소드
     * 
     * @return
     * @메소드명 : getMessageService
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public static KETMessageService getMessageService() {

	SessionContext context = SessionContext.getContext();
	KETMessageService messageService = (KETMessageService) context.get(KEY_MESSAGE_SERVICE);
	if (messageService == null) {
	    messageService = service;
	    if (messageService == null) {
		try {
		    messageService = new KETMessageService(getUserLocale((WTUser) SessionHelper.manager.getPrincipal()));
		} catch (WTException e) {
		    Kogger.error(KETMessageService.class, e);
		}
	    }
	}
	return messageService;
    }

    /**
     * 세션에 서비스 객체 저장
     * 
     * @param session
     *            - 현 사용자 세션
     * @param messageService
     *            - 저장할 메시지 서비스 객체
     */
    public static void setMessageService(HttpSession session, KETMessageService messageService) throws Exception {
	session.setAttribute(KEY_MESSAGE_SERVICE, messageService);
	// SessionUtil.setAttribute(KEY_MESSAGE_SERVICE, messageService);
    }

    /**
     * 사용자 정보로부터 로케일 정보 얻음
     * 
     * @param user
     *            - 사용자 객체
     * @return 사용자 정보에 저장된 로케일 정보. default=Locale.KOREAN
     */
    public static Locale getUserLocale(WTUser user) {
	People people = PeopleHelper.manager.getPeople(user.getName());
	return getUserLocale(people);
    }

    public static Locale getUserLocale(People people) {
	String nationalCode = (people != null) ? people.getNationalCode() : null;
	return getUserLocale(nationalCode);
    }

    public static Locale getUserLocale(String nationalCode) {
	Locale ret = null;
	if ("ko".equalsIgnoreCase(nationalCode)) {
	    ret = Locale.KOREAN;
	} else if ("en".equalsIgnoreCase(nationalCode)) {
	    ret = Locale.ENGLISH;
	} else if ("zh_CN".equalsIgnoreCase(nationalCode)) {
	    // ret = Locale.CHINESE; // _zh_CN.rbInfo
	    ret = Locale.CHINA;
	} else {
	    ret = Locale.KOREAN; // default: 한국어
	    Kogger.debug(KETMessageService.class, "KETMessageService.getUserLocale: unknown nationalCode: [" + nationalCode + "] => default:" + ret);
	}
	return ret;
    }

    private static Locale chooseLocale(String language) {
	Locale lo;
	if (Locale.KOREAN.getLanguage().equals(language)) {
	    lo = Locale.KOREAN;
	} else if (Locale.ENGLISH.getLanguage().equals(language)) {
	    lo = Locale.ENGLISH;
	} else if (Locale.CHINA.getLanguage().equals(language)) {
	    lo = Locale.CHINA;
	} else {
	    lo = Locale.KOREAN; // default
	}
	return lo;
    }

}
