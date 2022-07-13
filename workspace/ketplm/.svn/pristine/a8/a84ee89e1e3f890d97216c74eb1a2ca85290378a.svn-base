package e3ps.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.web.WebUtil;
import ext.ket.shared.log.Kogger;


/*
 * [PLM System 1차개선]
 * 파일명 : KETParamMap.java
 * 설명 : Request Parameter 처리용 Map
 * 작성일자 : 2013. 06. 21
 * 작성자 : 김무준
 */

public class KETParamMapUtil implements Map<String, Object>, Serializable {

    private static final long   serialVersionUID = -8160165973948913229L;

    private Map<String, Object> map              = null;

    public KETParamMapUtil() {
	map = new HashMap<String, Object>();
    }

    /**
     * Constructs a new hashtable with the same mappings as the given Map.
     * 
     * @param another
     *            - the map whose mappings are to be placed in this map.
     */
    private KETParamMapUtil(Map another) {
	map = new HashMap<String, Object>(another);
    }

    @Override
    public int size() {
	return map.size();
    }

    @Override
    public boolean isEmpty() {
	return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
	return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
	return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
	return map.get(key);
    }

    @Override
    public Object put(String key, Object value) {
	return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
	return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
	map.putAll(m);
    }

    @Override
    public void clear() {
	map.clear();
    }

    @Override
    public Set<String> keySet() {
	return map.keySet();
    }

    @Override
    public Collection<Object> values() {
	return map.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
	return map.entrySet();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	for (Map.Entry<String, Object> entry : map.entrySet()) {
	    String key = entry.getKey();
	    Object value = entry.getValue();

	    if (sb.length() > 0) sb.append(", ");

	    sb.append(key).append("=");
	    if (value instanceof String[]) {
		String[] sary = (String[]) value;
		sb.append("[");
		for (int i = 0; i < sary.length; ++i) {
		    if (i > 0) sb.append(", ");
		    sb.append(sary[i]);
		}
		sb.append("]");
	    }
	    else {
		sb.append(String.valueOf(value));
	    }
	}

	return sb.toString();
    }

    public Object getObject(String key) {
	return get(key);
    }

    /**
     * @param key
     * @param defaultVal
     *            - null 이거나 변환 실패 시 사용할 기본값
     * @return int 타입의 값 반환
     */
    public int getInt(String key, int defaultVal) {
	Object val = get(key);
	if (val == null) {
	    return defaultVal;
	}
	else if (val instanceof String) {
	    return StringUtil.parseInt(((String) val).trim(), defaultVal);
	}
	else {
	    return StringUtil.parseInt(String.valueOf(val).trim(), defaultVal);
	}
    }

    public int getInt(String key) {
	return getInt(key, 0);
    }

    /**
     * @param key
     * @param defaultVal
     *            - null 이거나 변환 실패 시 사용할 기본값
     * @return long 타입의 값 반환
     */
    public long getLong(String key, long defaultVal) {
	Object val = get(key);
	if (val == null) {
	    return defaultVal;
	}
	else if (val instanceof String) {
	    try {
		return Long.parseLong(((String) val).trim());
	    } catch (Exception e) {
		return defaultVal;
	    }
	}
	else {
	    try {
		return Long.parseLong(String.valueOf(val).trim());
	    } catch (Exception e) {
		return defaultVal;
	    }
	}
    }

    public long getLong(String key) {
	return getLong(key, 0);
    }

    /**
     * @param key
     * @param defaultVal
     *            - null 이거나 변환 실패 시 사용할 기본값
     * @return String 타입의 값 반환. (null checked, trimed) String[] 일 경우 ','로 구분된 문자열 반환.
     */
    public String getString(String key, String defaultVal) {
	Object val = get(key);
	if (val == null) {
	    return defaultVal;
	}
	else if (val instanceof String) {
	    return ((String) val).trim();
	}
	else if (val instanceof String[]) {
	    StringBuilder sb = new StringBuilder();
	    for (String str : (String[]) val) {
		if (str != null) {
		    if (sb.length() > 0) sb.append(",");
		    sb.append(str.trim());
		}
	    }
	    return sb.toString();
	}
	else if (val instanceof List) {
	    StringBuilder sb = new StringBuilder();
	    for (Object v : (List) val) {
		if (v != null) {
		    if (sb.length() > 0) sb.append(",");
		    sb.append(String.valueOf(v).trim());
		}
	    }
	    return sb.toString();
	}
	else {
	    return String.valueOf(val).trim();
	}
    }

    public String getString(String key) {
	return getString(key, "");
    }

    /**
     * @param key
     * @return String 타입인 경우 String[](size=1)에 넣어서 반환, null이면 size=0인 String[] 반환
     */
    public String[] getStringArray(String key) {
	Object val = get(key);
	if (val == null) {
	    return new String[0];
	}
	else if (val instanceof String[]) {
	    return (String[]) val;
	}
	else if (val instanceof List) {
	    return (String[]) ((List) val).toArray(new String[0]);
	}
	else {
	    Kogger.debug(getClass(), "KETParamMapUtil.getStringArray: key=" + key + ", valType=" + val.getClass().getName());
	    String sval = String.valueOf(val).trim();
	    if ((val != null && val.equals("")) || StringUtil.checkString(sval)) {	// if (StringUtil.checkString(sval)) {
		return new String[] { String.valueOf(val).trim() };
	    }
	    else {
		return new String[0]; // 빈문자 경우 null과 동일하게.
	    }
	}
    }

    /**
     * @param key
     * @return String[] 타입인 경우 List에 넣어서 반환, null이면 size=0인 List 반환
     */
    public List getList(String key) {
	Object val = get(key);
	if (val == null) {
	    return new ArrayList();
	}
	else if (val instanceof String[]) {
	    String[] sary = (String[]) val;
	    List list = new ArrayList();
	    for (int i = 0; i < sary.length; ++i) {
		list.add(sary[i]);
	    }
	    return list;
	}
	else if (val instanceof List) {
	    return (List) val;
	}
	else {
	    Kogger.debug(getClass(), "KETParamMapUtil.getList: key=" + key + ", valType=" + val.getClass().getName());
	    String sval = String.valueOf(val).trim();
	    if (StringUtil.checkString(sval)) {
		List list = new ArrayList();
		list.add(sval);
		return list;
	    }
	    else {
		return new ArrayList(); // 빈문자 경우 null과 동일하게.
	    }
	}
    }

    /**
     * KETParamMapUtil 타입 객체를 반환하는 static method
     */
    public static KETParamMapUtil getMap() {
	return new KETParamMapUtil();
    }

    /**
     * Map 타입을 받아 KETParamMapUtil 타입 객체를 반환하는 static method
     */
    public static KETParamMapUtil getMap(Map map) {
	if (map == null) {
	    return new KETParamMapUtil();
	}
	else if (map instanceof KETParamMapUtil) {
	    return (KETParamMapUtil) map;
	}
	else {
	    return new KETParamMapUtil(map);
	}
    }

    /**
     * HttpServletRequest 타입을 받아 KETParamMapUtil 타입 객체를 반환하는 static method
     */
    public static KETParamMapUtil getMap(HttpServletRequest req) {
	return getMap(req.getParameterMap());
    }

    /**
     * FormUploader 타입을 받아 KETParamMapUtil 타입 객체를 반환하는 static method
     */
    public static KETParamMapUtil getMap(FormUploader uploader) {
	HttpServletRequest req = uploader.getRequest();
	if (ServletFileUpload.isMultipartContent(req)) {
	    Hashtable hash = WebUtil.getHttpParams(req);
	    if (hash != null && hash.size() > 0) {
		Hashtable fprop = uploader.getFormParameters();
		if (fprop != null) {
		    for (Object hashKey : hash.keySet()) {
			if (fprop.containsKey(hashKey) == false) { // multipart form params 우선
			    fprop.put(hashKey, hash.get(hashKey));
			}
		    }
		}
		else {
		    uploader.setFormParameters(hash);
		}
	    }
	}
	return getMap(uploader.getFormParameters());
    }

    //-------------------------------------------------------------------------
    /*
     * 유틸리티
     */

    /**
     * 값 집합에 특정 값 포함 여부 확인 (MultiCombo에서 option 요소의 selected 처리 시 사용)
     * 
     * @param values
     *            - String[] 타입의 값 집합
     * @param obj
     *            - 포함 확인할 값
     * @return 포함되어 있을 경우 true
     */
    public static boolean contains(String[] values, Object obj) {
	if (values != null && values.length > 0) {
	    for (String value : (String[]) values) {
		if (value.equals(obj)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * 첨부파일 목록 확인
     * 
     * @param files
     *            - 첨부파일 리스트
     * @return - logging text
     */
    public static String logAttachFiles(Vector files) {
	StringBuilder sb = new StringBuilder();
	if (files != null) {
	    for (int fileCount = 0; fileCount < files.size(); fileCount++) {
		WBFile file = (WBFile) files.elementAt(fileCount);
		sb.append("file.getName : " + file.getName() + "\nfile.getFieldName : " + file.getFieldName()).append("\n");
	    }
	}
	return sb.toString();
    }

    /**
     * 함수명 : toString 설명 : String [] 을 ,(콤마)구분자 String으로 변환
     * 
     * @param String
     *            []
     * @return 콤마 구분자 String 작성일자 : 2013. 7. 5.
     */
    public static String toString(String[] sary) {
	if (sary == null) return "";

	StringBuilder sb = new StringBuilder();
	if (sary != null) {
	    for (int i = 0; i < sary.length; ++i) {
		if (i > 0) sb.append(", ");
		sb.append(sary[i]);
	    }
	}
	return sb.toString();
    }

    /**
     * 함수명 : OidToString 설명 : 'e3ps.project.product:0001' (oid)를 0001 ,(콤마)구분자 String으로 변환
     * 
     * @param String
     * @return 콤마 구분자 String 작성일자 : 2013. 8. 20.
     */
    public static String OidToString(String str) {
	if (str == null) return "";

	StringBuilder sb = new StringBuilder();
	if (str != null && !str.equals("")) {
	    String[] arr = str.split(", ");
	    for (int i = 0; i < arr.length; ++i) {
		if (i > 0) sb.append(", ");
		sb.append(CommonUtil.getOIDLongValue(arr[i]));
	    }
	}
	return sb.toString();
    }





    private static final String SES_ATTR_KEY_SUFFIX = "ConditionList";

    public static List<Map> getConditionListForSearchInResult(String keyPrefix, boolean searchInResult, HttpServletRequest request) {
	List<Map> conditionList = null;

	HttpSession session = request.getSession();
	String key = keyPrefix + SES_ATTR_KEY_SUFFIX;
	conditionList = (List<Map>) session.getAttribute(key);
	if (conditionList == null) {
	    conditionList = new ArrayList<Map>();
	    session.setAttribute(key, conditionList);
	}

	if (searchInResult == false) {
	    conditionList.clear(); // 이전 검색조건 제거
	}

	removeOtherConditionListsForSearchInResult(keyPrefix, request); // 다른 검색의 conditionList 제거

	return conditionList;
    }

    public static void removeOtherConditionListsForSearchInResult(String keyPrefix, HttpServletRequest request) {
	HttpSession session = request.getSession();
	Enumeration attrKeys = session.getAttributeNames();
	String myKey = keyPrefix + SES_ATTR_KEY_SUFFIX;
	while (attrKeys.hasMoreElements()) {
	    String attrKey = (String) attrKeys.nextElement();
	    if (attrKey.endsWith(SES_ATTR_KEY_SUFFIX) && myKey.equals(attrKey) == false) {
		session.removeAttribute(attrKey);
		Kogger.debug(KETParamMapUtil.class, "removeOtherConditionListsForSearchInResult: '" + attrKey + "' removed.");
	    }
	}
    }

}
