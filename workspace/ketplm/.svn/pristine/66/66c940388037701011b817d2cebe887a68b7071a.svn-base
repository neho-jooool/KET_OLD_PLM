package e3ps.common.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * [PLM System 1차개선]
 * 파일명 : SqlRowMap.java
 * 설명 : sql 쿼리 결과 row를 저장하기 위한 Map
 * 작성일자 : 2013. 07. 01
 * 작성자 : 김무준
 */

public class SqlRowMap implements Map<String, Object>, Serializable {

    private Map<String, Object> map = new HashMap<String, Object>();

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
    public Set<Entry<String, Object>> entrySet() {
        // TODO Auto-generated method stub
        return map.entrySet();
    }
    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * 컬럼명이 대문자로 저장되므로 key를 대문자로 변경하여 get() 호출
     * @param key - 컬럼명
     * @return 컬럼값
     */
    private Object getWithUpperKey(String key) {
        return get(key.toUpperCase());
    }

    public String getString(String key) {
        return (String) getWithUpperKey(key);
    }
    public String toString(String key) {
        Object o = getWithUpperKey(key);
        return ((o!=null)?String.valueOf(o):null);
    }

    public String getStringNotNull(String key, String defaultValue) {
        String str = getString(key);
        if (KETStringUtil.isNull(str)) {
            str = defaultValue;
        }
        return str;
    }
    public String getStringNotNull(String key) {
        return getStringNotNull(key, "");
    }

    public Integer getInt(String key) {
        return (Integer) getWithUpperKey(key);
    }

    public Timestamp getTimestamp(String key) {
        return (Timestamp) getWithUpperKey(key);
    }

    public byte[] getBytes(String key) {
        return (byte[]) getWithUpperKey(key);
    }

}
