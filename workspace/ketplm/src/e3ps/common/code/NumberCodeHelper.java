/*
 * @(#) NumberCodeHelper.java Create on 2005. 5. 10.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.code.service.CacheManager;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;
import wt.fc.BinaryLink;
import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.StringSearch;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;

// import e3ps.sap.ProcessInterface;
/**
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2005. 5. 10.
 * @since 1.4
 */
public class NumberCodeHelper {
    public static NumberCodeHelper manager = new NumberCodeHelper();

    private NumberCodeHelper() {
    }

    /**
     * 특정 코드 타입에 코드가 code인 NumberCode를 반환
     * 
     * @param codeType
     * @param code
     * @return
     */
    public NumberCode getNumberCode(String codeType, String code) {
	if (code == null) {
	    return null;
	}
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    select.appendAnd();
	    select.appendWhere(new SearchCondition(NumberCode.class, "code", "=", code), new int[] { 0 });
	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
	    QueryResult result = PersistenceHelper.manager.find(select);
	    while (result.hasMoreElements()) {
		return (NumberCode) result.nextElement();
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    /**
     * 특정 코드 타입에 코드가 code인 NumberCode의 oid를 반환
     * 
     * @param codeType
     * @param code
     * @return
     */
    public String getNumberCodeOid(String codeType, String code) {
	NumberCode numberCode = getNumberCode(codeType, code);
	String oid = null;

	if (numberCode != null) {
	    oid = CommonUtil.getFullOIDString(numberCode);
	}

	return oid;
    }

    /**
     * DSCodeList.jsp 에서 사용
     * 
     * @param codeType
     * @return
     */
    public Vector getNumberCodeForQuery(String codeType) {
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
	    QueryResult result = PersistenceHelper.manager.find(select);
	    Vector vec = new Vector();
	    int i = 0;
	    while (result.hasMoreElements()) {
		NumberCode tempCode = (NumberCode) result.nextElement();
		vec.add(i, tempCode);
		i++;
	    }
	    return vec;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }
    
    
    public Vector getNumberCodeForSortingQuery(String codeType) {
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    
	    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(NumberCode.class, "sorting"));
	    OrderBy orderby = new OrderBy(sqlfunction, false);
	    orderby = new OrderBy(sqlfunction, false);

	    select.appendOrderBy(orderby, new int[] { 0 });
	    
	    QueryResult result = PersistenceHelper.manager.find(select);
	    Vector vec = new Vector();
	    int i = 0;
	    while (result.hasMoreElements()) {
		NumberCode tempCode = (NumberCode) result.nextElement();
		vec.add(i, tempCode);
		i++;
	    }
	    return vec;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    /**
     * NumberCode를 HashMap 으로 반환
     * 
     * @param codeType
     * @return key:code,value:code_name
     */
    public Map<String, String> getNumberCode(String codeType) {
    	Map<String, String> map = new HashMap<String, String>();
		try {
		    QuerySpec select = new QuerySpec(NumberCode.class);
		    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
		    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
		    
		    QueryResult result = PersistenceHelper.manager.find(select);
	
		    NumberCode code = null;
		    while (result.hasMoreElements()) {
				code = (NumberCode) result.nextElement();
				map.put(code.getCode(), code.getName());
		    }
		} catch (QueryException e) {
		    Kogger.error(getClass(), e);
		} catch (WTException e) {
		    Kogger.error(getClass(), e);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		return map;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 11. 25. 오전 12:14:03
     * @method getNumberCodeList
     * @param codeType
     * @return ArrayList<NumberCode>
     * </pre>
     */
    public List<NumberCode> getNumberCodeList(String codeType) {
    	List<NumberCode> list = new ArrayList<NumberCode>();
    	try {
    	    QuerySpec select = new QuerySpec(NumberCode.class);
    	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
    	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
    	    QueryResult result = PersistenceHelper.manager.find(select);
    	    NumberCode code = null;
    	    while (result.hasMoreElements()) {
	    		code = (NumberCode) result.nextElement();
	    		list.add(code);
    	    }
    	} catch (QueryException e) {
    	    Kogger.error(getClass(), e);
    	} catch (WTException e) {
    	    Kogger.error(getClass(), e);
    	} catch (Exception e) {
    	    Kogger.error(getClass(), e);
    	}
    	return list;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 4. 오전 10:55:45
     * @method getNumberCodeMapList
     * @param codeType
     * @return List<Map<String,String>>
     * </pre>
     */
    public List<Map<String,String>> getNumberCodeMapList(String codeType) {
    	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	try {
    	    QuerySpec select = new QuerySpec(NumberCode.class);
    	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
    	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
    	    QueryResult result = PersistenceHelper.manager.find(select);
    	    NumberCode code = null;
    	    while (result.hasMoreElements()) {
	    		code = (NumberCode) result.nextElement();
	    		Map<String, String> map = new HashMap<String, String>();
	    		map.put("id", CommonUtil.getOIDString(code));
	    		map.put("code", code.getCode());
	    		map.put("name", code.getName());
	    		
	    		list.add(map);
    	    }
    	} catch (QueryException e) {
    	    Kogger.error(getClass(), e);
    	} catch (WTException e) {
    	    Kogger.error(getClass(), e);
    	} catch (Exception e) {
    	    Kogger.error(getClass(), e);
    	}
    	return list;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 5. 2. 오전 10:30:48
     * @method getNumberCodeChildMapList
     * @param codeType
     * @param pCode
     * @return List<Map<String,String>>
     * </pre>
     */
    public List<Map<String,String>> getNumberCodeChildMapList(String codeType, String pCode) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            
            NumberCode parent = NumberCodeHelper.manager.getNumberCode(codeType, pCode);
            
            QuerySpec select = new QuerySpec(NumberCode.class);
            select.appendWhere(new SearchCondition(NumberCode.class, NumberCode.CODE_TYPE, SearchCondition.EQUAL, codeType), new int[] { 0 });
            if(select.getConditionCount() > 0) select.appendAnd();
            select.appendWhere(new SearchCondition(NumberCode.class, NumberCode.PARENT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
                    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(parent)), new int[] { 0 });
            
            SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
            QueryResult result = PersistenceHelper.manager.find(select);
            NumberCode code = null;
            
            while (result.hasMoreElements()) {
                code = (NumberCode) result.nextElement();
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", CommonUtil.getOIDString(code));
                map.put("code", code.getCode());
                map.put("name", code.getName());
                
                list.add(map);
            }
        } catch (QueryException e) {
            Kogger.error(getClass(), e);
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        return list;
    }
    


    public Vector getNumberCodeLevel(String codeType, String depthLevel) {
	Vector vec = new Vector();
	int i = 0;

	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    if (depthLevel.equals("child")) {
		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.NOT_EQUAL, (long) 0), new int[] { 0 });
	    } else if (depthLevel.equals("top")) {
		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, (long) 0), new int[] { 0 });
	    }
	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
	    QueryResult result = PersistenceHelper.manager.find(select);

	    NumberCode code = null;
	    while (result.hasMoreElements()) {
		code = (NumberCode) result.nextElement();
		if (depthLevel.equals("3")) {
		    if (code.getParent() != null) {
			// Kogger.debug(getClass(), "$"+i+" $$$$$$$$$$$$$$$$$$$$$$$==>"+code.getParent().getName());
			vec.add(i, code);
			i++;
		    }
		} else {
		    vec.add(i, code);
		    i++;
		}
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    public Vector getNumberCodeLevelType(String codeType, String parentName) {
	Vector vec = new Vector();
	int i = 0;
	try {
	    QuerySpec spec = new QuerySpec(NumberCode.class);
	    spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(NumberCode.class, "name", "=", parentName), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    NumberCode pcode = null;
	    if (qr.hasMoreElements()) {
		pcode = (NumberCode) qr.nextElement();
	    }

	    if (qr.size() > 0) {
		QuerySpec select = new QuerySpec(NumberCode.class);
		select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pcode)), new int[] { 0 });
		SearchUtil.setOrderBy(select, NumberCode.class, 0, "name", false);
		QueryResult result = PersistenceHelper.manager.find(select);

		NumberCode code = null;
		while (result.hasMoreElements()) {
		    code = (NumberCode) result.nextElement();
		    vec.add(i, code);
		    i++;
		}
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    // codeType, 부모 name, 자식 name 검색
    public NumberCode getNumberCodeOnlyOneType(String codeType, String parentName, String name) {
	NumberCode code = null;
	try {
	    QuerySpec spec = new QuerySpec(NumberCode.class);
	    spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(NumberCode.class, "name", "=", parentName), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    NumberCode pcode = null;
	    if (qr.hasMoreElements()) {
		pcode = (NumberCode) qr.nextElement();
	    }
	    if (qr.size() > 0) {
		QuerySpec select = new QuerySpec(NumberCode.class);
		select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pcode)), new int[] { 0 });
		select.appendAnd();
		select.appendWhere(new SearchCondition(NumberCode.class, "name", "=", name), new int[] { 0 });

		SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
		QueryResult result = PersistenceHelper.manager.find(select);
		if (result.hasMoreElements()) {
		    code = (NumberCode) result.nextElement();
		}
	    }

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return code;
    }

    // codeType, name 검색
    public NumberCode getNumberCodeName(String codeType, String name) {
	if (name == null) {
	    return null;
	}
	try {
	    QuerySpec select = new QuerySpec(NumberCode.class);
	    select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });
	    select.appendAnd();
	    select.appendWhere(new SearchCondition(NumberCode.class, "name", "=", name), new int[] { 0 });
	    SearchUtil.setOrderBy(select, NumberCode.class, 0, "code", false);
	    QueryResult result = PersistenceHelper.manager.find(select);
	    while (result.hasMoreElements()) {
		return (NumberCode) result.nextElement();
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    public QueryResult getQueryResult(String codeType, String sortType) {
	if (sortType == null)
	    sortType = "code";
	try {
	    QuerySpec spec = new QuerySpec(NumberCode.class);
	    spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });

	    spec.appendAnd();
	    spec.appendOpenParen();
	    spec.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_FALSE), new int[] { 0 });
	    spec.appendOr();
	    spec.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_NULL), new int[] { 0 });
	    spec.appendCloseParen();

	    SearchUtil.setOrderBy(spec, NumberCode.class, 0, sortType, false);
	    return PersistenceHelper.manager.find(spec);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getQueryResult(String codeType, String sortType, boolean isValidate) {
	if (sortType == null)
	    sortType = "code";

	try {
	    QuerySpec spec = new QuerySpec(NumberCode.class);
	    spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codeType), new int[] { 0 });

	    if (isValidate) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		spec.appendOpenParen();
		spec.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_FALSE), new int[] { 0 });
		spec.appendOr();
		spec.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_NULL), new int[] { 0 });
		spec.appendCloseParen();
	    }

	    // order by
	    SearchUtil.setOrderBy(spec, NumberCode.class, 0, sortType, false);

	    return PersistenceHelper.manager.find(spec);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    /**
     * 1Level NumberCode
     * 
     * @param type
     * @return QueryResult
     */
    public static ArrayList getTopNumberCode(NumberCodeType type) throws WTException {
	ArrayList list = new ArrayList();
	try {
	    if (type == null)
		return list;

	    HashMap map = new HashMap();
	    map.put("type", type.toString());
	    map.put("isParent", "false");

	    QueryResult qr = PersistenceHelper.manager.find(getCodeQuerySpec(map));

	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		list.add((NumberCode) obj[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return list;
    }

    public static ArrayList getChildNumberCode(NumberCode numberCode) throws WTException {
	ArrayList list = new ArrayList();

	try {
	    ArrayList<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(numberCode.getCodeType().toString());
	    //iterator 사용시 무한루프가 발생하여 (원재료 type 의경우만 = MATERIALTYPE) 기존 소스 주석처리하고 아래처럼 for문으로 변경함 오류발생원인은 모르겠음 2017.02.10 by 황정태
	    for(NumberCodeDTO dto : arrayList){
		if (StringUtil.checkString(numberCode.getCode())) {
		    String[] strings = numberCode.getCode().split(",");
		    for (String string : strings) {
			if (string.equals(dto.getPcode())) {
			    System.out.println("numbercode === "+dto.getNumbercode().getCode());
			    list.add(dto.getNumbercode());
			}
		    }
		}
	    }
/*	    while (iterator.hasNext()) {
		NumberCodeDTO dto = iterator.next();
		if (StringUtil.checkString(numberCode.getCode())) {
//		    System.out.println("numbercodeVal === "+numberCode.getCode());
		    String[] strings = numberCode.getCode().split(",");
		    
		    for (String string : strings) {
			if (string.equals(dto.getPcode())) {
			    System.out.println("strings === "+strings[0]);
			    System.out.println("dto === "+dto.getPcode());
			    System.out.println("numbercode === "+dto.getNumbercode().getCode());
			    list.add(dto.getNumbercode());
			}
		    }
		}
	    }*/
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	/*
	 * try {
	 * if (numberCode == null)
	 * return list;
	 * HashMap map = new HashMap();
	 * map.put("parent", numberCode);
	 * QueryResult qr = PersistenceHelper.manager.find(getCodeQuerySpec(map));
	 * Object obj[] = null;
	 * while (qr.hasMoreElements()) {
	 * obj = (Object[]) qr.nextElement();
	 * list.add((NumberCode) obj[0]);
	 * }
	 * } catch (Exception e) {
	 * Kogger.error(NumberCodeHelper.class, e);
	 * }
	 */
	return list;
    }

    public static ArrayList getChildNumberCode2(NumberCode numberCode, String vender) throws WTException {
	ArrayList list = new ArrayList();
	try {
	    if (numberCode == null)
		return list;

	    HashMap map = new HashMap();
	    map.put("parent", numberCode);
	    map.put("vender", vender);

	    QueryResult qr = PersistenceHelper.manager.find(getCodeQuerySpec(map));
	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		list.add((NumberCode) obj[0]);

	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return list;
    }

    public static ArrayList getNumberCode(HashMap map) throws WTException {
	ArrayList list = new ArrayList();
	try {
	    QueryResult qr = PersistenceHelper.manager.find(getCodeQuerySpec(map));

	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		list.add((NumberCode) obj[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return list;
    }

    public static PagingQueryResult openPagingSession(HashMap map, int start, int size) throws WTException {
	PagingQueryResult result = null;
	try {
	    result = openPagingSession(getCodeQuerySpec(map), start, size);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult openPagingSession(QuerySpec spec, int start, int size) throws WTException {
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.openPagingSession(start, size, spec);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult fetchPagingSession(int start, int size, long sessionId) throws WTException {
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.fetchPagingSession(start, size, sessionId);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return result;
    }

    // 상위 코드
    public static ArrayList ancestorNumberCode(NumberCode child) throws WTException {
	ArrayList list = new ArrayList();
	try {
	    searchAncestorNumberCode(child, list);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return list;
    }

    public static void searchAncestorNumberCode(NumberCode child, ArrayList list) throws WTException {
	try {
	    if (list == null) {
		list = new ArrayList();
	    }

	    if (child.getParent() != null) {
		list.add(0, child.getParent());
		searchAncestorNumberCode(child.getParent(), list);
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
    }

    // 하위 코드
    public static ArrayList descendantsNumberCode(NumberCode parent) throws WTException {
	return null;
    }

    public static String saveNumberCode(HashMap map) throws WTException {
	NumberCode numberCode = null;
	String msg = "";
	try {
	    ReferenceFactory rf = new ReferenceFactory();

	    String oid = map.get("oid") == null ? "" : ((String) map.get("oid")).trim();
	    String code = map.get("code") == null ? "" : ((String) map.get("code")).trim();
	    String name = map.get("name") == null ? "" : ((String) map.get("name")).trim();
	    String description = map.get("description") == null ? "" : ((String) map.get("description")).trim();
	    String type = map.get("type") == null ? "" : ((String) map.get("type")).trim();
	    String parentOid = map.get("parentOid") == null ? "" : ((String) map.get("parentOid")).trim();
	    String wctype = map.get("wctype") == null ? "" : ((String) map.get("wctype")).trim();
	    String costCode = map.get("costCode") == null ? "" : ((String) map.get("costCode")).trim();
	    String venderCode = map.get("venderCode") == null ? "" : ((String) map.get("venderCode")).trim();
	    String submitCheck = map.get("submitCheck") == null ? "false" : ((String) map.get("submitCheck")).trim();
	    String checkSapSubmit = map.get("checkSapSubmit") == null ? "false" : ((String) map.get("checkSapSubmit")).trim();

	    String sorting = map.get("sorting") == null ? "" : ((String) map.get("sorting")).trim();
	    Boolean disabled = "1".equals(map.get("disabled")) ? true : false;

	    long checkOld_id = 0;
	    long checkNew_id = 0;
	    boolean newNumberCheck = false;
	    boolean sapCheck = false;
	    boolean sapCheckSubmit = false;

	    if (submitCheck.equals("true")) {
		sapCheck = true;
	    }
	    if (checkSapSubmit.equals("true")) {
		sapCheckSubmit = true;
	    }

	    // ProcessInterface pi = new ProcessInterface();
	    HashMap hm_Old = new HashMap();
	    HashMap hm_New = new HashMap();

	    if (oid.length() > 0) {
		numberCode = (NumberCode) rf.getReference(oid).getObject();

		if ("WORKCENTER".equals(type.trim())) {
		    if (numberCode.getWcType() != null) {
			String checkWctype = numberCode.getWcType();
			String zFlag = "";
			if (checkWctype.trim().equals("사내")) {
			    zFlag = "A";
			} else if (checkWctype.trim().equals("사내외주")) {
			    zFlag = "B";
			} else if (checkWctype.trim().equals("사외외주")) {
			    zFlag = "C";
			} else if (checkWctype.trim().equals("해외외주")) {
			    zFlag = "D";
			}

			hm_Old.put("WERKS", numberCode.getParent().getCode());
			hm_Old.put("ARBPL", numberCode.getCode());// 대문자
			hm_Old.put("Z_DATASTA", "D");
			hm_Old.put("Z_FLAG", zFlag);
			hm_Old.put("STEXT", numberCode.getName());
			if (sapCheck) {
			    hm_Old.put("SUBSYS", numberCode.getVenderCode());
			}
		    }
		}
	    }

	    if (numberCode == null) {
		numberCode = NumberCode.newNumberCode();
		newNumberCheck = true;
	    }

	    if (code.length() > 0) {
		numberCode.setCode(code);
	    }

	    if (name.length() > 0) {
		numberCode.setName(name);
	    }

	    NumberCode ncparent = null;

	    if (parentOid != null && parentOid.trim().length() > 0) {
		ncparent = (NumberCode) rf.getReference(parentOid).getObject();
	    }

	    HashMap hm = null;
	    // ##### ERP 전송 - 새로 생성시
	    if (newNumberCheck && "WORKCENTER".equals(type.trim())) {

		if (wctype != null) {
		    HashMap hs_N = new HashMap();
		    String checkWctype = wctype;
		    String zFlag = "";
		    if (checkWctype.trim().equals("사내")) {
			zFlag = "A";
		    } else if (checkWctype.trim().equals("사내외주")) {
			zFlag = "B";
		    } else if (checkWctype.trim().equals("사외외주")) {
			zFlag = "C";
		    } else if (checkWctype.trim().equals("해외외주")) {
			zFlag = "D";
		    }

		    hm_New.put("WERKS", (ncparent != null) ? ncparent.getCode() : "");
		    hm_New.put("ARBPL", code);// 대문자
		    hm_New.put("Z_DATASTA", "N");
		    hm_New.put("Z_FLAG", zFlag);
		    hm_New.put("STEXT", name);

		    if (sapCheck) {
			hm_New.put("SUBSYS", StringUtil.checkNull(venderCode));
		    }

		    Kogger.debug(NumberCodeHelper.class, "@@@@@##### ##### ERP 전송 - 생성@@@@@##### ");
		    // hs_N = pi.WorkCenter(hm_New);
		    msg = "등록 완료 SAP Log : " + hs_N.get("RE").toString() + " ( " + hs_N.get("MSG").toString() + " )";

		    if (hs_N.get("RE").toString().equals("E")) {
			if (sapCheckSubmit) {
			    return msg;
			}
			// throw new Exception("Error Sap 전송 에러");
		    }

		}
	    }

	    // ##### ERP 전송 - Plant 변경시 삭제 후 생성.
	    if (!newNumberCheck && "WORKCENTER".equals(type.trim())) {
		Kogger.debug(NumberCodeHelper.class, "###################################################   ERP Check    ");
		checkOld_id = CommonUtil.getOIDLongValue(numberCode.getParent());
		checkNew_id = CommonUtil.getOIDLongValue(ncparent);

		if (checkOld_id != checkNew_id) {

		    HashMap hs_C = new HashMap();
		    String checkWctype = wctype;
		    String zFlag = "";
		    if (checkWctype.trim().equals("사내")) {
			zFlag = "A";
		    } else if (checkWctype.trim().equals("사내외주")) {
			zFlag = "B";
		    } else if (checkWctype.trim().equals("사외외주")) {
			zFlag = "C";
		    } else if (checkWctype.trim().equals("해외외주")) {
			zFlag = "D";
		    }

		    hm_New.put("WERKS", (ncparent != null) ? ncparent.getCode() : "");
		    hm_New.put("ARBPL", code);// 대문자
		    hm_New.put("Z_DATASTA", "N");
		    hm_New.put("Z_FLAG", zFlag);
		    hm_New.put("STEXT", name);

		    if (sapCheck) {
			hm_New.put("SUBSYS", StringUtil.checkNull(venderCode));
		    }

		    Kogger.debug(NumberCodeHelper.class, "@@@@@##### ERP 전송 - Plant 변경시  삭제  후  생성.@@@@@##### ");
		    // pi.WorkCenter(hm_Old);
		    // hs_C = pi.WorkCenter(hm_New);

		    msg = "수정 완료 SAP Log : " + hs_C.get("RE").toString() + " ( " + hs_C.get("MSG").toString() + " )";
		    if (hs_C.get("RE").toString().equals("E")) {
			// throw new Exception("Error Sap 전송 에러");
			if (sapCheckSubmit) {
			    return msg;
			}
		    }
		} else {
		    HashMap hs_C = new HashMap();
		    String checkWctype = wctype;
		    String zFlag = "";
		    if (checkWctype.trim().equals("사내")) {
			zFlag = "A";
		    } else if (checkWctype.trim().equals("사내외주")) {
			zFlag = "B";
		    } else if (checkWctype.trim().equals("사외외주")) {
			zFlag = "C";
		    } else if (checkWctype.trim().equals("해외외주")) {
			zFlag = "D";
		    }

		    hm_New.put("WERKS", (ncparent != null) ? ncparent.getCode() : "");
		    hm_New.put("ARBPL", code);
		    hm_New.put("Z_DATASTA", "C");
		    hm_New.put("Z_FLAG", zFlag);
		    hm_New.put("STEXT", name);

		    if (sapCheck) {
			hm_New.put("SUBSYS", StringUtil.checkNull(venderCode));
		    }

		    Kogger.debug(NumberCodeHelper.class, "@@@@@##### ERP 전송 -  수정 @@@@@##### ");
		    // hs_C = pi.WorkCenter(hm_New);

		    msg = "수정 완료 SAP Log : " + hs_C.get("RE").toString() + " ( " + hs_C.get("MSG").toString() + " )";
		    if (hs_C.get("RE").toString().equals("E")) {
			// throw new Exception("Error Sap 전송 에러");
			if (sapCheckSubmit) {
			    return msg;
			}
		    }

		}

	    }

	    numberCode.setWcType(wctype);
	    numberCode.setCostCode(costCode);
	    numberCode.setVenderCode(venderCode);
	    numberCode.setDescription(description);

	    numberCode.setSorting(sorting);
	    numberCode.setDisabled(disabled);

	    if (oid.length() == 0 && type.length() > 0) {
		numberCode.setCodeType(NumberCodeType.toNumberCodeType(type));
	    }

	    numberCode = (NumberCode) PersistenceHelper.manager.save(numberCode);

	    if (numberCode.getParent() != null) {

		QueryResult qr = PersistenceHelper.manager.navigate(numberCode, "parent", NCodeNCodeLink.class, false);
		while (qr.hasMoreElements()) {
		    NCodeNCodeLink link = (NCodeNCodeLink) qr.nextElement();
		    PersistenceHelper.manager.delete(link);
		}
	    }

	    numberCode = (NumberCode) PersistenceHelper.manager.refresh(numberCode);

	    NumberCode parent = null;
	    if ((parentOid.trim()).length() > 0) {
		parent = (NumberCode) rf.getReference(parentOid).getObject();
		numberCode.setParent(parent);
		numberCode = (NumberCode) PersistenceHelper.manager.save(numberCode);

	    }
	    if (!"WORKCENTER".equals(numberCode.getCodeType().toString())) {
		if (numberCode == null) {
		    msg = "저장에 실패했습니다. \n 다시 저장하십시오.";
		} else {
		    msg = "저장 되었습니다.";
		}
	    }

	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return msg;
    }

    public static boolean deleteNumberCode(NumberCode code) throws WTException {
	try {
	    ArrayList childs = getChildNumberCode(code);
	    if (childs.size() > 0) {
		return false;
	    }
	    String test = "";
	    
	    QueryResult qr = PersistenceHelper.manager.navigate(code, "ALL", BinaryLink.class, false);
	    int cnt = 0;
	    if (qr.size() == 0) {
		PersistenceHelper.manager.delete(code);
	    } else {
		while (qr.hasMoreElements()) {
		    Object obj = (Object) qr.nextElement();
		    
		    if(!StringUtils.contains(test, obj.getClass().toString())){
			System.out.println(obj.getClass().toString());
		    }
		    test += obj.getClass().toString();
		    
		    if (!(obj instanceof NCodeNCodeLink)) {
			cnt++;
		    }
		}
	    }
	    if(cnt > 0){
		return false; 
	    }

	    if (qr.size() != 0) {
		PersistenceHelper.manager.delete(code);
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	    return false;
	}
	return true;
    }

    public static boolean checkNumberCode(HashMap map) throws WTException {
	try {
	    String name = (String) map.get("name");
	    String code = (String) map.get("code");
	    String type = (String) map.get("type");
	    String parentOid = (String) map.get("parentOid");

	    if (name == null)
		name = "";
	    if (code == null)
		code = "";
	    if (type == null)
		type = "";
	    if (parentOid == null)
		parentOid = "";

	    if (code.length() == 0)
		return false;

	    if (type.length() == 0)
		return false;

	    HashMap smap = new HashMap();
	    smap.put("name", name);
	    smap.put("code", code);
	    smap.put("type", type);
	    if (parentOid.length() > 0) {
		ReferenceFactory rf = new ReferenceFactory();
		NumberCode parent = (NumberCode) rf.getReference(parentOid).getObject();
		smap.put("parent", parent);
	    }

	    QuerySpec qs = getCodeQuerySpec(smap);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    if (qr.hasMoreElements()) {
		return true;
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return false;
    }

    // public static boolean sendStdInfoToERP(NumberCode code, String type) throws WTException {
    // try {
    // ArrayList list = getSubCodeTree(code, type);
    // if(code != null) {
    // list.add(0, code);
    // }
    //
    // ArrayList r_list = new ArrayList();
    // Object objArr[] = null;//[0]:level, [1]:code, [2]:name
    //
    // HashMap c_Map = new HashMap();
    // HashMap l_Map = new HashMap();
    //
    // String tCode = null;
    // Integer lvl = null;
    //
    // NumberCode child = null;
    // NumberCode parent = null;
    // for(int i = 0; i < list.size(); i++) {
    // child = (NumberCode)list.get(i);
    // parent = child.getParent();
    //
    // if(parent == null) {
    // lvl = new Integer(0);
    // tCode = child.getCode();
    // }
    // else {
    // lvl = (Integer)l_Map.get(parent.getCode());
    // tCode = (String)c_Map.get(lvl);
    // tCode += child.getCode();
    //
    // lvl = new Integer(lvl.intValue()+1);
    // }
    //
    // objArr = new Object[3];
    // objArr[0] = String.valueOf(lvl.intValue());
    // objArr[1] = tCode;
    // objArr[2] = child.getName();
    // r_list.add(objArr);
    //
    // l_Map.put(child.getCode(), lvl);
    // c_Map.put(lvl, tCode);
    // }
    //
    // //Kogger.debug(NumberCodeHelper.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    // //for(int i = 0; i < r_list.size(); i++) {
    // //objArr = (Object[])r_list.get(i);
    // //Kogger.debug(NumberCodeHelper.class, (String)objArr[0] + '\t' + (String)objArr[1] + '\t' + (String)objArr[2]);
    // //}
    // //Kogger.debug(NumberCodeHelper.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    //
    // StdInfoInterface iter = new StdInfoInterface();
    // boolean flag = iter.sendProcessInterface(r_list);
    // iter.close();
    //
    // return flag;
    // }
    // catch(Exception e) {
    // Kogger.error(NumberCodeHelper.class, e);
    // return false;
    // }
    // }

    // public static boolean sendStdInfoToERPOne(NumberCode code, String type) throws WTException {
    // try {
    // ArrayList list = getSubCodeTree(code, type);
    // if(code != null) {
    // list.add(0, code);
    // }
    //
    // ArrayList r_list = new ArrayList();
    // Object objArr[] = null;//[0]:level, [1]:code, [2]:name
    //
    // HashMap c_Map = new HashMap();
    // HashMap l_Map = new HashMap();
    //
    // String tCode = null;
    // Integer lvl = null;
    //
    // NumberCode child = null;
    // NumberCode parent = null;
    // for(int i = 0; i < list.size(); i++) {
    // child = (NumberCode)list.get(i);
    // parent = child.getParent();
    //
    // if(parent == null) {
    // lvl = new Integer(0);
    // tCode = child.getCode();
    // }
    // else {
    // lvl = (Integer)l_Map.get(parent.getCode());
    // tCode = (String)c_Map.get(lvl);
    // tCode += child.getCode();
    //
    // lvl = new Integer(lvl.intValue()+1);
    // }
    //
    // objArr = new Object[3];
    // objArr[0] = String.valueOf(lvl.intValue());
    // objArr[1] = tCode;
    // objArr[2] = child.getName();
    // r_list.add(objArr);
    //
    // l_Map.put(child.getCode(), lvl);
    // c_Map.put(lvl, tCode);
    // }
    //
    // //Kogger.debug(NumberCodeHelper.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    // //for(int i = 0; i < r_list.size(); i++) {
    // //objArr = (Object[])r_list.get(i);
    // //Kogger.debug(NumberCodeHelper.class, (String)objArr[0] + '\t' + (String)objArr[1] + '\t' + (String)objArr[2]);
    // //}
    // //Kogger.debug(NumberCodeHelper.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    //
    // StdInfoInterface iter = new StdInfoInterface();
    // boolean flag = iter.sendProcessInterface(r_list);
    // iter.close();
    //
    // return flag;
    // }
    // catch(Exception e) {
    // Kogger.error(NumberCodeHelper.class, e);
    // return false;
    // }
    // }

    public static ArrayList getSubCodeTree(NumberCode parent, String type) throws WTException {
	ArrayList treeList = new ArrayList();
	try {
	    ArrayList list = null;
	    if (parent == null) {
		list = NumberCodeHelper.getTopNumberCode(NumberCodeType.toNumberCodeType(type));
	    } else {
		list = NumberCodeHelper.getChildNumberCode(parent);
	    }

	    if (list != null && list.size() > 0) {
		NumberCode numberCode = null;
		for (int i = 0; i < list.size(); i++) {
		    numberCode = (NumberCode) list.get(i);

		    makeCodeTree(numberCode, treeList);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return treeList;
    }

    public static void makeCodeTree(NumberCode parent, ArrayList list) throws WTException {
	try {
	    if (list == null) {
		list = new ArrayList();
	    }

	    list.add(parent);

	    ArrayList childs = NumberCodeHelper.getChildNumberCode(parent);
	    NumberCode child = null;
	    for (int i = 0; i < childs.size(); i++) {
		child = (NumberCode) childs.get(i);

		makeCodeTree(child, list);
	    }
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
    }

    public static QuerySpec getCodeQuerySpec(HashMap map) throws WTException {

	QuerySpec qs = null;
	try {
	    String type = (String) map.get("type");
	    String wctype = (String) map.get("wctype");
	    String name = (String) map.get("name");
	    String code = (String) map.get("code");
	    String description = (String) map.get("description");
	    NumberCode parent = (NumberCode) map.get("parent");
	    String isParent = (String) map.get("isParent");
	    String nation = (String) map.get("nation");
	    String vender = (String) map.get("vender");

	    Kogger.debug(NumberCodeHelper.class, "parent = " + parent + " \n\n\n\n");

	    qs = new QuerySpec();
	    int i = qs.addClassList(NumberCode.class, true);

	    SearchCondition sc = null;

	    if (nation != null && nation.length() > 0) {
		sc = new SearchCondition(NumberCode.class, "name", "=", nation);
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (wctype != null && wctype.length() > 0) {
		sc = new SearchCondition(NumberCode.class, "wcType", SearchCondition.EQUAL, wctype.trim());
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (name != null && name.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		// sc = new SearchCondition(NumberCode.class, "name", SearchCondition.LIKE, "%"+name+"%");
		// qs.appendWhere(sc, new int[] { i });
		StringSearch stringsearchname = new StringSearch("name");
		stringsearchname.setValue("%" + name.trim() + "%");
		qs.appendWhere(stringsearchname.getSearchCondition(NumberCode.class), new int[] { i });
	    }

	    if (type != null && type.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "codeType", SearchCondition.EQUAL, NumberCodeType.toNumberCodeType(type));
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (code != null && code.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		// sc = new SearchCondition(NumberCode.class, "code", SearchCondition.EQUAL, code);
		// qs.appendWhere(sc, new int[] { i });
		StringSearch stringsearchcode = new StringSearch("code");
		stringsearchcode.setValue("%" + code.trim() + "%");
		qs.appendWhere(stringsearchcode.getSearchCondition(NumberCode.class), new int[] { i });

	    }
	    if (description != null && description.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "description", SearchCondition.LIKE, "%" + description + "%");
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (vender != null && vender.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "venderCode", "=", vender);
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (isParent != null && "false".equals(isParent.toLowerCase())) {
		// Kogger.debug(NumberCodeHelper.class, "isParent Not Null && false");
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "parentReference.key.classname", true);
		qs.appendWhere(sc, new int[] { i });
	    } else {
		// Kogger.debug(NumberCodeHelper.class, "Else");
		if (parent != null) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, parent.getPersistInfo().getObjectIdentifier().getId());
		    qs.appendWhere(sc, new int[] { i });
		}
	    }

	    ClassAttribute ca = new ClassAttribute(NumberCode.class, "code");
	    ca.setColumnAlias("wtsort" + String.valueOf(0));
	    qs.appendSelect(ca, new int[] { i }, false);
	    OrderBy orderby = new OrderBy(ca, false, null);
	    qs.appendOrderBy(orderby, new int[] { i });

	    Kogger.debug(NumberCodeHelper.class, "QuerySpec>>> " + qs);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return qs;
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 최초사용처, 고객처 , 납입처, 생산처 조회
     * 수정일자 : 2013. 7.5
     * 수정자 : 남현승
     */

    public static QuerySpec getCodeQuerySpecPopup(Map map) throws WTException {

	QuerySpec qs = null;
	try {

	    String type = (String) map.get("type");
	    String wctype = (String) map.get("wctype");
	    String name = (String) map.get("name");
	    String code = (String) map.get("code");
	    String description = (String) map.get("description");

	    NumberCode parent = null;
	    Object obj = map.get("parent");
	    if (obj instanceof NumberCode) {
		parent = (NumberCode) map.get("parent");
	    }
	    String isParent = (String) map.get("isParent");
	    String nation = (String) map.get("nation");
	    String vender = (String) map.get("vender");

	    qs = new QuerySpec();
	    int i = qs.addClassList(NumberCode.class, true);

	    SearchCondition sc = null;

	    if (nation != null && nation.length() > 0) {
		sc = new SearchCondition(NumberCode.class, "name", "=", nation);
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (wctype != null && wctype.length() > 0) {
		sc = new SearchCondition(NumberCode.class, "wcType", SearchCondition.EQUAL, wctype.trim());
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (name != null && name.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		// sc = new SearchCondition(NumberCode.class, "name", SearchCondition.LIKE, "%"+name+"%");
		// qs.appendWhere(sc, new int[] { i });
		StringSearch stringsearchname = new StringSearch("name");
		stringsearchname.setValue("%" + name.trim() + "%");
		qs.appendWhere(stringsearchname.getSearchCondition(NumberCode.class), new int[] { i });
	    }

	    if (type != null && type.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "codeType", SearchCondition.EQUAL, NumberCodeType.toNumberCodeType(type));
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (code != null && code.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		// sc = new SearchCondition(NumberCode.class, "code", SearchCondition.EQUAL, code);
		// qs.appendWhere(sc, new int[] { i });
		StringSearch stringsearchcode = new StringSearch("code");
		stringsearchcode.setValue("%" + code.trim() + "%");
		qs.appendWhere(stringsearchcode.getSearchCondition(NumberCode.class), new int[] { i });

	    }
	    if (description != null && description.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "description", SearchCondition.LIKE, "%" + description + "%");
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (vender != null && vender.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "venderCode", "=", vender);
		qs.appendWhere(sc, new int[] { i });
	    }

	    if (isParent != null && "false".equals(isParent.toLowerCase())) {
		Kogger.debug(NumberCodeHelper.class, "isParent Not Null && false");
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "parentReference.key.classname", true);
		qs.appendWhere(sc, new int[] { i });
	    } else {
		// Kogger.debug(NumberCodeHelper.class, "Else");
		if (parent != null) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    sc = new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, parent.getPersistInfo().getObjectIdentifier().getId());
		    qs.appendWhere(sc, new int[] { i });
		}
	    }

	    ClassAttribute ca = new ClassAttribute(NumberCode.class, "code");
	    ca.setColumnAlias("wtsort" + String.valueOf(0));
	    qs.appendSelect(ca, new int[] { i }, false);
	    OrderBy orderby = new OrderBy(ca, false, null);
	    qs.appendOrderBy(orderby, new int[] { i });

	    Kogger.debug(NumberCodeHelper.class, "QuerySpec>>> " + qs);
	} catch (Exception e) {
	    Kogger.error(NumberCodeHelper.class, e);
	}
	return qs;
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : NumberCode Combo 조회
     * 수정일자 : 2013. 6. 18
     * 수정자 : 김종호
     */
    public List<Map<String, Object>> getNumberCodeList(Map<String, Object> parameter) throws Exception {
	// [START][KET PLM 고도화 프로젝트] NumberCode 검색 개선, 2014. 6. 24. Jason, Han
	List<Map<String, Object>> list = CodeHelper.service.getNumberCodeList(parameter);
	// [END][KET PLM 고도화 프로젝트] NumberCode 검색 개선, 2014. 6. 24. Jason, Han
	return list;

	/*
	 * Connection conn = null;
	 * try {
	 * conn = PlmDBUtil.getConnection();
	 * NumberCodeDao numberCodeDao = new NumberCodeDao(conn);
	 * // Locale null 인 경우 한글로 세팅
	 * if (parameter.get("locale") == null) {
	 * parameter.put("locale", "ko");
	 * }
	 * List<Map<String, Object>> list = numberCodeDao.getNumberCodeList(parameter);
	 * return list;
	 * } finally {
	 * PlmDBUtil.close(conn);
	 * }
	 */
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : NumberCode Combo 조회
     * 수정일자 : 2013. 6. 18
     * 수정자 : 김종호
     */
    public List<Map<String, Object>> getCatCategoryList(Map<String, Object> parameter) throws Exception {

	// [START][KET PLM 고도화 프로젝트] NumberCode 검색 개선, 2014. 6. 24. Jason, Han
	List<Map<String, Object>> list = CodeHelper.service.getCarNumberCodeList(parameter);
	// [END][KET PLM 고도화 프로젝트] NumberCode 검색 개선, 2014. 6. 24. Jason, Han
	return list;
	/*
	 * Connection conn = null;
	 * try {
	 * conn = PlmDBUtil.getConnection();
	 * NumberCodeDao numberCodeDao = new NumberCodeDao(conn);
	 * List<Map<String, Object>> list = numberCodeDao.getCarCategoryList(parameter);
	 * return list;
	 * } finally {
	 * PlmDBUtil.close(conn);
	 * }
	 */
    }
}

/*******************************************************************************
 * $Log: not supported by cvs2svn $
 * Revision 1.10 2010/12/16 09:05:33 smkim
 * *** empty log message ***
 * Revision 1.9 2010/11/26 02:05:46 khkim
 * getNumberCodeOid 추가
 * Revision 1.8 2010/11/22 10:08:46 smkim
 * *** empty log message ***
 * Revision 1.7 2010/11/12 02:11:26 smkim
 * *** empty log message ***
 * Revision 1.6 2010/11/11 12:12:28 smkim
 * *** empty log message ***
 * Revision 1.5 2010/11/01 07:41:19 hhkim
 * *** empty log message ***
 * Revision 1.4 2010/11/01 05:28:45 ysjung
 * 소스정리
 * Revision 1.3 2010/11/01 02:23:22 ysjung
 * 자동차 전자 구분 수정
 * Revision 1.2 2010/10/29 01:01:51 smkim
 * *** empty log message ***
 * Revision 1.1 2010/09/10 04:40:54 syoh
 * 최초등록
 * Revision 1.19 2010/01/29 01:40:50 administrator
 * aaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.18 2009/12/30 06:22:18 tsuam
 * 11
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.17 2009/12/29 05:37:34 administrator
 * aaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.16 2009/12/11 08:28:40 administrator
 * AAA
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.15 2009/12/01 14:43:10 administrator
 * aaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.14 2009/11/23 04:29:49 administrator
 * aaaaaaaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.13 2009/11/21 07:26:55 administrator
 * aaaaaaaaaaaaaaaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.12 2009/11/20 09:17:40 administrator
 * aaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.11 2009/11/20 06:46:41 administrator
 * aaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.10 2009/11/12 07:15:22 administrator
 * aaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.9 2009/11/12 07:14:08 administrator
 * aaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.8 2009/11/01 01:17:34 khchoi
 * sendStdInfoToERP & sendStdInfoToERPOne Function 주석처리
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.7 2009/10/28 03:44:19 administrator
 * A
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.6 2009/10/16 04:32:25 administrator
 * aaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.5 2009/10/10 06:57:26 administrator
 * aaaaaaaaaaaaaaaaa
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.4 2009/09/25 08:24:18 khchoi
 * 프로젝트 공용속성 Function 추가
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.3 2009/09/10 10:59:19 khchoi
 * 코드체계분류 기능 변경 (문서관리 속성)
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.2 2009/08/17 09:22:15 khchoi
 * 코드체계분류 기능 변경 (프로젝트 속성)
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.1 2009/08/11 04:16:21 administrator
 * Init Source
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.1 2009/02/25 01:26:32 smkim
 * 최초 작성
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * Revision 1.10 2008/12/23 08:44:35 jspark
 * *** empty log message ***
 * Revision 1.9 2008/11/13 10:31:26 jspark
 * *** empty log message ***
 * Revision 1.8 2008/10/13 10:26:21 smkim
 * *** empty log message ***
 * Revision 1.7 2008/09/05 02:00:13 jspark
 * *** empty log message ***
 * Revision 1.6 2008/08/21 08:05:02 jspark
 * *** empty log message ***
 * Revision 1.5 2008/07/29 01:22:55 jspark
 * *** empty log message ***
 * Revision 1.4 2008/04/15 11:14:09 jspark
 * *** empty log message ***
 * Revision 1.3 2008/03/17 00:32:38 jspark
 * *** empty log message ***
 * Revision 1.2 2008/03/13 12:33:24 jspark
 * *** empty log message ***
 * Revision 1.1 2008/01/29 06:25:03 sjhan
 * 주성 기본 패키지 정리작업 완료
 * Revision 1.1 2008/01/23 09:51:53 sjhan
 * e3ps package 정리 완료본
 * jsp 소스는 확인 후 수정해야 될 필요 있음
 * Revision 1.1 2007/09/27 01:43:28 khchoi
 * [20070927 최강훈]
 * *.java Source 정리
 * Revision 1.1.1.1 2007/04/10 06:40:18 administrator
 * no message
 * Revision 1.1.1.1 2007/02/14 07:53:56 administrator
 * no message
 * Revision 1.3 2006/09/19 06:19:36 shchoi
 * getQueryResult 추가
 * Revision 1.2 2006/06/27 05:16:15 shchoi
 * 코드 정리
 * Revision 1.1 2006/05/09 02:35:03 shchoi
 * *** empty log message ***
 * Revision 1.1 2006/05/09 01:23:59 shchoi
 * *** empty log message ***
 * Revision 1.3 2006/03/15 05:09:07 shchoi
 * *** empty log message ***
 * Revision 1.2 2006/02/24 02:29:14 khchoi
 * *** empty log message ***
 * Revision 1.1 2005/12/09 12:20:32 shchoi
 * *** empty log message ***
 * Revision 1.4 2005/12/06 12:25:15 shchoi
 * null 체크
 * /* Revision 1.3 2005/12/06 01:04:54 shchoi /* getNumberCode(String
 * codeType) 추가 /
 ******************************************************************************/
