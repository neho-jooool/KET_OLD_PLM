package ext.ket.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import e3ps.common.util.StringUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 5. 25. 오전 10:09:50
 * @Pakage ext.ket.common.util
 * @class ObjectUtil
 *********************************************************/
public class ObjectUtil {

    public static final ObjectUtil manager = new ObjectUtil();

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ObjectUtil.class);

    /**
     * <pre>
     * @description OBJECT TO MAP CONVERT
     * @author dhkim
     * @date 2017. 11. 17. 오후 1:49:59
     * @method converObjectToMap
     * @param obj
     * @return Map<String,Object>
     * </pre>
     */
    public Map<String, Object> converObjectToMap(Object obj) throws Exception {

	Map<String, Object> result = new HashMap<String, Object>();
	try {
	    Method[] methods = obj.getClass().getMethods();

	    for (Method method : methods) {
		if (method.getName().indexOf("get") == 0 || method.getName().indexOf("is") == 0) {
		    String key = method.getName().substring(3);

		    if (method.getName().indexOf("is") == 0) {
			key = method.getName().substring(2);
		    }

		    key = key.substring(0, 1).toLowerCase() + key.substring(1);
		    Object value = method.invoke(obj);

		    if (value != null) {
			result.put(key, String.valueOf(value));
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    public Map<String, Object> converObjectToMap2(Object obj, Map<String, Object> result) throws Exception {

	try {
	    if (result == null) {
		result = new HashMap<String, Object>();
	    }
	    Method[] methods = obj.getClass().getMethods();

	    for (Method method : methods) {
		if (method.getName().indexOf("get") == 0 || method.getName().indexOf("is") == 0) {
		    String key = method.getName().substring(3);

		    if (method.getName().indexOf("is") == 0) {
			key = method.getName().substring(2);
		    }

		    key = key.substring(0, 1).toLowerCase() + key.substring(1);
		    Object value = method.invoke(obj);

		    if (value != null) {
			result.put(key, String.valueOf(value));
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description MAP TO OBJECT CONVERT
     * @author dhkim
     * @date 2017. 11. 17. 오후 1:49:37
     * @method convertMapToObject
     * @param map
     * @param objClass
     * @return Object
     * </pre>
     */
    public Object convertMapToObject(Map<String, Object> map, Object object) throws Exception {

	map.remove("conceptualClassname");
	map.remove("class");
	map.remove("persistInfo");
	map.remove("masterReference");
	map.remove("parentReference");
	map.remove("classInfo");
	map.remove("master");

	String keyAttribute = null;
	String setMethodString = "set";
	String methodString = null;
	Iterator<String> itr = map.keySet().iterator();
	while (itr.hasNext()) {
	    keyAttribute = itr.next();
	    methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

	    try {
		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i <= methods.length - 1; i++) {

		    if (methodString.equals(methods[i].getName())) {
			Object data = map.get(keyAttribute);

			// LOGGER.info("keyAttribute #### " + keyAttribute);
			// LOGGER.info("data #### " + data);

			methods[i].invoke(object, data);
		    }
		}
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
		// LOGGER.info("######## CONVERTMAPTOOBJECT NON SETTING ########" + methodString);
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}
	return object;
    }

    /**
     * 
     * 
     * @param rs
     * @return
     * @throws Exception
     * @메소드명 : rsColumnNameToList
     * @작성자 : 황정태
     * @작성일 : 2021. 1. 22.
     * @설명 : rs columname 반환
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<String> rsColumnNameToList(ResultSet rs) throws Exception {
	List<String> list = new ArrayList<String>();
	ResultSetMetaData metaData = rs.getMetaData();
	int sizeOfColumn = metaData.getColumnCount();

	for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++) {
	    list.add(metaData.getColumnName(indexOfcolumn + 1));
	}

	return list;
    }

    /**
     * 
     * 
     * @param rs
     * @return
     * @throws Exception
     * @메소드명 : rsToList
     * @작성자 : 황정태
     * @작성일 : 2019. 5. 2.
     * @설명 : ResultSet을 list로 반환
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<Map<String, Object>> rsToList(ResultSet rs) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// ResultSet 의 MetaData를 가져온다.

	ResultSetMetaData metaData = rs.getMetaData();

	// ResultSet 의 Column의 갯수를 가져온다.

	int sizeOfColumn = metaData.getColumnCount();

	Map<String, Object> map;

	String column;

	// rs의 내용을 돌려준다.

	while (rs.next())

	{

	    // 내부에서 map을 초기화

	    map = new HashMap<String, Object>();

	    // Column의 갯수만큼 회전

	    for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++)

	    {

		column = metaData.getColumnName(indexOfcolumn + 1);

		// map에 값을 입력 map.put(columnName, columnName으로 getString)

		switch (metaData.getColumnType(indexOfcolumn + 1)) {

		case Types.NUMERIC:
		    if (rs.getString(column) != null && StringUtils.contains(rs.getString(column), ".")) {
			map.put(column, String.valueOf(rs.getFloat(column)));
		    } else {
			map.put(column, String.valueOf(rs.getInt(column)));
		    }

		    break;

		default:
		    map.put(column, rs.getString(column));
		    break;
		}

	    }

	    // list에 저장

	    list.add(map);

	}
	return list;
    }

    /**
     * 
     * 
     * @param obj
     * @param fieldName
     * @return
     * @throws Exception
     * @메소드명 : callGetter
     * @작성자 : 황정태
     * @작성일 : 2019. 5. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Object callGetter(Object obj, String fieldName) throws Exception {
	PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());

	return pd.getReadMethod().invoke(obj);

    }

    /**
     * 
     * 
     * @param map
     * @param object
     * @return
     * @throws Exception
     * @메소드명 : diffWithMapToObject
     * @작성자 : user
     * @작성일 : 2020. 2. 12.
     * @설명 : Object 와 map의 값이 다를 경우에만 convert한다. 비교결과 다른게 없으면 null을 리턴한다
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */

    public Object diffWithMapToObject(Map<String, Object> map, Object object) throws Exception {

	map.remove("conceptualClassname");
	map.remove("class");
	map.remove("persistInfo");
	map.remove("masterReference");
	map.remove("parentReference");
	map.remove("classInfo");
	map.remove("master");

	String keyAttribute = null;
	String setMethodString = "set";
	String methodString = null;
	Iterator<String> itr = map.keySet().iterator();

	boolean isNotEqualValue = false;

	while (itr.hasNext()) {
	    keyAttribute = itr.next();
	    methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

	    try {
		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i <= methods.length - 1; i++) {

		    if (methodString.equals(methods[i].getName())) {

			Object data = map.get(keyAttribute);

			Object data2 = this.callGetter(object, keyAttribute);

			String value = StringUtil.defaultIfEmpty((String) data, "");
			String value2 = StringUtil.defaultIfEmpty((String) data2, "");

			if (!value.equals(value2)) {
			    isNotEqualValue = true;
			}

			// LOGGER.info("keyAttribute #### " + keyAttribute);
			// LOGGER.info("data #### " + data);

			methods[i].invoke(object, data);
		    }
		}
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// LOGGER.info("######## CONVERTMAPTOOBJECT NON SETTING ########" + methodString);
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}
	if (!isNotEqualValue) {
	    return null;
	}
	return object;
    }

    public Map<String, Object> converMapKeyChangeToObject(Object obj, Map<String, Object> map) throws Exception {

	Map<String, Object> result = new HashMap<String, Object>();
	try {
	    Method[] methods = obj.getClass().getMethods();

	    for (Method method : methods) {
		if (method.getName().indexOf("get") == 0 || method.getName().indexOf("is") == 0) {
		    String key = method.getName().substring(3);

		    if (method.getName().indexOf("is") == 0) {
			key = method.getName().substring(2);
		    }

		    key = key.substring(0, 1).toLowerCase() + key.substring(1);

		    Iterator<String> iteratorKey = map.keySet().iterator();

		    while (iteratorKey.hasNext()) {

			String mapKey = iteratorKey.next();
			if ((mapKey.toLowerCase()).equals(key.toLowerCase())) {
			    result.put(key, map.get(mapKey));
			}
		    }

		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    public HashMap<String, Object> httpReqConvertMap(HttpServletRequest request) {

	HashMap<String, Object> hmap = new HashMap<String, Object>();
	String key;

	Enumeration<?> enum_ = request.getParameterNames();

	while (enum_.hasMoreElements()) {
	    key = (String) enum_.nextElement();
	    if (request.getParameterValues(key).length > 1) {
		hmap.put(key, request.getParameterValues(key));
	    } else {
		hmap.put(key, request.getParameter(key));
	    }

	}

	return hmap;
    }

}
