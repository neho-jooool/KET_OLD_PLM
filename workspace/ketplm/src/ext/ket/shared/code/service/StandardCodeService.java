package ext.ket.shared.code.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.ExternalTableExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableColumn;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.cost.service.CostHelper;
import ext.ket.shared.code.entity.CarNumberCodeDTO;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.ComparatorUtil;

/**
 * @클래스명 : StandardCodeService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardCodeService extends StandardManager implements CodeService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2943330690132205392L;

    public static StandardCodeService newStandardCodeService() throws WTException {
	StandardCodeService instance = new StandardCodeService();
	instance.initialize();
	return instance;
    }

    @Override
    protected synchronized void performStartupProcess() throws ManagerException {
	try {
	    System.out.print("ext.ket.shared.code.service.StandardCodeService - loadCache : NUMBERCODE....");
	    loadNumberCode();
	    System.out.println("Done");
	    System.out.print("ext.ket.shared.code.service.StandardCodeService - loadCache : CARNUMBERCODE....");
	    loadCarNumberCode();
	    System.out.println("Done");

	    System.out.print("ext.ket.cost.service.StandardCostService - loadCache : COSTPARTTYPEINFO....");
	    CostHelper.service.loadCostPartTypeInfo();
	    System.out.println("Done");

	    System.out.print("ext.ket.cost.service.StandardCostService - loadCache : COSTFORMULA....");
	    CostHelper.service.loadCostFormula();
	    System.out.println("Done");

	} catch (Exception e) {
	    System.err.println(e);
	}
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getNumberCodeValue(String codeType, String code, String locale) throws Exception {

	String rtnValue = "";
	QuerySpec spec = new QuerySpec();
	ExternalTableExpression tableExpression = new ExternalTableExpression("NUMBERCODEVALUE");
	int v_idx = spec.appendFrom(tableExpression);
	TableColumn v_lang = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "LANG");
	TableColumn v_value = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "VALUE");
	TableColumn v_code = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODE");
	TableColumn v_codetype = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODETYPE");
	TableColumn v_abbr = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "ABBR");
	v_lang.setJavaType(String.class);
	v_value.setJavaType(String.class);
	v_abbr.setJavaType(String.class);
	spec.appendSelect(v_lang, new int[] { v_idx }, false);
	spec.appendSelect(v_value, new int[] { v_idx }, false);
	spec.appendSelect(v_abbr, new int[] { v_idx }, false);
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_code, "=", new ConstantExpression(code)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_codetype, "=", new ConstantExpression(codeType)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_lang, "=", new ConstantExpression(locale)), new int[] { v_idx });
	if (spec.isAdvancedQuery())
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    rtnValue = String.valueOf(objArr[1]);
	}

	return rtnValue;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getNumberCodeDescription(String codeType, String code, String locale) throws Exception {

	String rtnValue = "";
	QuerySpec spec = new QuerySpec();
	ExternalTableExpression tableExpression = new ExternalTableExpression("NUMBERCODEVALUE");
	int v_idx = spec.appendFrom(tableExpression);
	TableColumn v_lang = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "LANG");
	TableColumn v_value = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "DESCRIPTION");
	TableColumn v_code = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODE");
	TableColumn v_codetype = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODETYPE");
	TableColumn v_abbr = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "ABBR");
	v_lang.setJavaType(String.class);
	v_value.setJavaType(String.class);
	v_abbr.setJavaType(String.class);
	spec.appendSelect(v_lang, new int[] { v_idx }, false);
	spec.appendSelect(v_value, new int[] { v_idx }, false);
	spec.appendSelect(v_abbr, new int[] { v_idx }, false);
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_code, "=", new ConstantExpression(code)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_codetype, "=", new ConstantExpression(codeType)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_lang, "=", new ConstantExpression(locale)), new int[] { v_idx });
	if (spec.isAdvancedQuery())
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    rtnValue = String.valueOf(objArr[1]);
	}

	return rtnValue;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getNumberCodeCodeByName(String codeType, String name, String locale) throws Exception {

	String rtnValue = "";
	QuerySpec spec = new QuerySpec();
	ExternalTableExpression tableExpression = new ExternalTableExpression("NUMBERCODEVALUE");
	int v_idx = spec.appendFrom(tableExpression);
	TableColumn v_lang = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "LANG");
	TableColumn v_code = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODE");
	TableColumn v_name = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "NAME");
	TableColumn v_codetype = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODETYPE");
	TableColumn v_abbr = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "ABBR");
	v_lang.setJavaType(String.class);
	v_code.setJavaType(String.class);
	v_abbr.setJavaType(String.class);
	spec.appendSelect(v_lang, new int[] { v_idx }, false);
	spec.appendSelect(v_code, new int[] { v_idx }, false);
	spec.appendSelect(v_abbr, new int[] { v_idx }, false);
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_name, "=", new ConstantExpression(name)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_codetype, "=", new ConstantExpression(codeType)), new int[] { v_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_lang, "=", new ConstantExpression(locale)), new int[] { v_idx });
	if (spec.isAdvancedQuery())
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	if (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    rtnValue = String.valueOf(objArr[1]);
	}

	return rtnValue;
    }

    @SuppressWarnings("deprecation")
    public void loadDocumentCategoryCode() throws Exception {
	String codetype = "DOCUMENTCATEGORY";

	Map<String, List<NumberCodeDTO>> map = new HashMap<String, List<NumberCodeDTO>>();

	QuerySpec spec = new QuerySpec();
	ExternalTableExpression tableExpression = new ExternalTableExpression("NUMBERCODEVALUE");
	int v_idx = spec.appendFrom(tableExpression);
	TableColumn v_lang = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "LANG");
	TableColumn v_value = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "VALUE");
	TableColumn v_code = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODE");
	TableColumn v_codetype = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODETYPE");

	v_lang.setJavaType(String.class);
	v_value.setJavaType(String.class);
	v_code.setJavaType(String.class);
	spec.appendSelect(v_lang, new int[] { v_idx }, false);
	spec.appendSelect(v_code, new int[] { v_idx }, false);
	spec.appendSelect(v_value, new int[] { v_idx }, false);

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(v_codetype, "=", new ConstantExpression(codetype)), new int[] { v_idx });

	if (spec.isAdvancedQuery())
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);

	if (result != null && result.size() > 0) {
	    HashMap<String, NumberCodeDTO> hash = new HashMap<String, NumberCodeDTO>();
	    while (result.hasMoreElements()) {
		Object[] objArr = (Object[]) result.nextElement();

		String lang = (String) objArr[0];
		String key = (String) objArr[1];
		String value = (String) objArr[2];
		// added by tklee

		// Kogger.debug(getClass(), key);
		Object obj = hash.get(key);
		if (obj != null) {
		    NumberCodeDTO dto = (NumberCodeDTO) obj;
		    if ("ko".equals(lang))
			dto.setValueKO(value);
		    else if ("en".equals(lang))
			dto.setValueEN(value);
		    else if ("zh_CN".equals(lang))
			dto.setValueZH(value);
		    else if ("ja".equals(lang))
			dto.setValueJA(value);

		} else {
		    NumberCodeDTO dto = new NumberCodeDTO(objArr);
		    if ("ko".equals(lang))
			dto.setValueKO(value);
		    else if ("en".equals(lang))
			dto.setValueEN(value);
		    else if ("zh_CN".equals(lang))
			dto.setValueZH(value);
		    else if ("ja".equals(lang))
			dto.setValueJA(value);
		    hash.put(key, dto);

		}
	    }

	    if (hash != null && hash.size() > 0) {
		Iterator<String> iterator = hash.keySet().iterator();
		while (iterator.hasNext()) {
		    String key = iterator.next();
		    NumberCodeDTO dto = hash.get(key);
		    if (map.containsKey(codetype)) {
			ArrayList<NumberCodeDTO> list = (ArrayList<NumberCodeDTO>) map.get(codetype);
			list.add(dto);
		    } else {
			ArrayList<NumberCodeDTO> list = new ArrayList<NumberCodeDTO>();
			list.add(dto);
			map.put(codetype, list);
		    }
		}
	    }
	}
	Iterator<String> iterator = map.keySet().iterator();
	while (iterator.hasNext()) {
	    String key = iterator.next();
	    CacheManager.updateByKey(key, map.get(key));
	}

    }

    @Override
    public void loadNumberCode() throws Exception {

	SessionContext sessionContext = SessionContext.newContext();
	Map<String, List<NumberCodeDTO>> map = new HashMap<String, List<NumberCodeDTO>>();
	try {
	    SessionHelper.manager.setAdministrator();

	    QuerySpec spec = new QuerySpec();
	    int c_idx = spec.appendClassList(NumberCode.class, true);
	    ClassAttribute c_code = new ClassAttribute(NumberCode.class, NumberCode.CODE);
	    ClassAttribute c_codetype = new ClassAttribute(NumberCode.class, NumberCode.CODE_TYPE);
	    // ClassAttribute c_sort = new ClassAttribute(NumberCode.class, NumberCode.SORTING);
	    ExternalTableExpression tableExpression = new ExternalTableExpression("NUMBERCODEVALUE");
	    int v_idx = spec.appendFrom(tableExpression);
	    TableColumn v_lang = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "LANG");
	    TableColumn v_value = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "VALUE");
	    TableColumn v_code = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODE");
	    TableColumn v_codetype = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "CODETYPE");
	    TableColumn v_abbr = new TableColumn(spec.getFromClause().getAliasAt(v_idx), "ABBR");
	    v_lang.setJavaType(String.class);
	    v_value.setJavaType(String.class);
	    v_abbr.setJavaType(String.class);
	    spec.appendSelect(v_lang, new int[] { v_idx }, false);
	    spec.appendSelect(v_value, new int[] { v_idx }, false);
	    spec.appendSelect(v_abbr, new int[] { v_idx }, false);
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(c_code, "=", v_code), new int[] { c_idx, v_idx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(c_codetype, "=", v_codetype), new int[] { c_idx, v_idx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(NumberCode.class, NumberCode.DISABLED, SearchCondition.IS_FALSE), new int[] { c_idx });
	    // spec.appendOrderBy(new OrderBy(c_sort, false), new int[] { c_idx });
	    spec.appendOrderBy(new OrderBy(c_codetype, true), new int[] { c_idx });
	    spec.appendOrderBy(new OrderBy(c_code, true), new int[] { c_idx });
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    // Kogger.debug(getClass(), spec);

	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (result != null && result.size() > 0) {
		HashMap<String, NumberCodeDTO> hash = new HashMap<String, NumberCodeDTO>();
		while (result.hasMoreElements()) {
		    Object[] objArr = (Object[]) result.nextElement();
		    NumberCode code = (NumberCode) objArr[0];
		    String key = String.valueOf(CommonUtil.getOIDLongValue(code));
		    String lang = (String) objArr[1];
		    String value = (String) objArr[2];
		    // added by tklee
		    String abbr = (String) objArr[3];
		    // Kogger.debug(getClass(), key);
		    Object obj = hash.get(key);
		    if (obj != null) {
			NumberCodeDTO dto = (NumberCodeDTO) obj;
			if ("ko".equals(lang))
			    dto.setValueKO(value);
			else if ("en".equals(lang))
			    dto.setValueEN(value);
			else if ("zh_CN".equals(lang))
			    dto.setValueZH(value);
			else if ("ja".equals(lang))
			    dto.setValueJA(value);
			// added by tklee
			dto.setAbbr(abbr);
		    } else {
			NumberCodeDTO dto = new NumberCodeDTO(code);
			if ("ko".equals(lang))
			    dto.setValueKO(value);
			else if ("en".equals(lang))
			    dto.setValueEN(value);
			else if ("zh_CN".equals(lang))
			    dto.setValueZH(value);
			else if ("ja".equals(lang))
			    dto.setValueJA(value);
			hash.put(key, dto);

			// added by tklee
			dto.setAbbr(abbr);
		    }
		}
		if (hash != null && hash.size() > 0) {
		    Iterator<String> iterator = hash.keySet().iterator();
		    while (iterator.hasNext()) {
			String key = iterator.next();
			NumberCodeDTO dto = hash.get(key);
			String codetype = dto.getCodetype().toString();
			if (map.containsKey(codetype)) {
			    ArrayList<NumberCodeDTO> list = (ArrayList<NumberCodeDTO>) map.get(codetype);
			    list.add(dto);
			} else {
			    ArrayList<NumberCodeDTO> list = new ArrayList<NumberCodeDTO>();
			    list.add(dto);
			    map.put(codetype, list);
			}
		    }
		}
	    }
	    Iterator<String> iterator = map.keySet().iterator();
	    while (iterator.hasNext()) {
		String key = iterator.next();
		CacheManager.updateByKey(key, map.get(key));
	    }
	    loadDocumentCategoryCode();
	} catch (Exception e) {
	    throw e;
	} finally {
	    SessionContext.setContext(sessionContext);
	}
    }

    @Override
    public void loadCarNumberCode() throws Exception {

	SessionContext sessionContext = SessionContext.newContext();
	try {
	    SessionHelper.manager.setAdministrator();

	    QuerySpec spec = new QuerySpec();
	    int c_idx = spec.appendClassList(NumberCode.class, true);
	    int o_idx = spec.appendClassList(OEMProjectType.class, false);

	    spec.appendSelect(new ClassAttribute(OEMProjectType.class, OEMProjectType.CODE), new int[] { o_idx }, false);
	    spec.appendSelect(new ClassAttribute(OEMProjectType.class, OEMProjectType.NAME), new int[] { o_idx }, false);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(OEMProjectType.class, "makerReference.key.id")),
		    new int[] { c_idx, o_idx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(OEMProjectType.class, OEMProjectType.OEM_TYPE, "=", "CARTYPE"), new int[] { o_idx });
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(OEMProjectType.class, OEMProjectType.IS_DISABLED, SearchCondition.IS_FALSE), new int[] { o_idx });

	    spec.appendOrderBy(new OrderBy(new ClassAttribute(NumberCode.class, NumberCode.SORTING), true), new int[] { c_idx });
	    spec.appendOrderBy(new OrderBy(new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id"), true), new int[] { c_idx });

	    // Kogger.debug(getClass(), spec);

	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);

	    ArrayList<CarNumberCodeDTO> list = null;
	    if (result != null && result.size() > 0) {
		list = new ArrayList<CarNumberCodeDTO>();
		while (result.hasMoreElements()) {
		    Object[] objArr = (Object[]) result.nextElement();
		    NumberCode numberCode = (NumberCode) objArr[0];
		    String code = (String) objArr[1];
		    String value = (String) objArr[2];
		    CarNumberCodeDTO dto = new CarNumberCodeDTO();
		    dto.setNumberCode(numberCode);
		    dto.setCode(code);
		    dto.setName(value);
		    list.add(dto);
		}
	    }
	    if (list != null) {
		CacheManager.updateByKey(CacheManager.CARNUMBERCODECACHE, list);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    SessionContext.setContext(sessionContext);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getNumberCodeList(Map<String, Object> parameter) throws Exception {

	Locale locale = (Locale) parameter.get("locale");
	String code = StringUtil.checkNull((String) parameter.get("code"));
	String name = StringUtil.checkNull((String) parameter.get("name"));
	String codeType = StringUtil.checkNull((String) parameter.get("codeType"));
	String oid = StringUtil.checkNull((String) parameter.get("oid"));
	String myCode = StringUtil.checkNull((String) parameter.get("myCode"));
	String exclude = StringUtil.checkNull((String) parameter.get("exclude"));
	String depthLevel = StringUtil.checkNull((String) parameter.get("depthLevel"));
	String venderCode = StringUtil.checkNull((String) parameter.get("venderCode"));
	String description = StringUtil.checkNull((String) parameter.get("description"));

	ArrayList<Map<String, Object>> maps = null;

	ArrayList<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codeType);
	ArrayList<NumberCodeDTO> dtos = new ArrayList<NumberCodeDTO>();
	Iterator<NumberCodeDTO> iterator = (arrayList != null) ? arrayList.iterator() : null;
	while (iterator != null && iterator.hasNext()) {
	    NumberCodeDTO dto = iterator.next();
	    if (StringUtil.checkString(code)) {
		String[] strings = code.split(",");
		for (String string : strings) {
		    if (string.equals(dto.getPcode())) {
			dtos.add(dto);
		    }
		}
	    } else if (StringUtil.checkString(name)) {
		if (name.equals(dto.getDisplay(locale))) {
		    dtos.add(dto);
		}
	    } else if (StringUtil.checkString(oid)) {
		if (oid.equals(dto.getOid())) {
		    dtos.add(dto);
		}
	    } else if (StringUtil.checkString(myCode)) {
		if (myCode.equals(dto.getCode())) {
		    dtos.add(dto);
		}
	    } else if (StringUtil.checkString(venderCode)) {
		if (venderCode.equals(dto.getVendorCode())) {
		    dtos.add(dto);
		}
	    } else if (StringUtil.checkString(description)) {
		if (description.equals(dto.getDescription())) {
		    dtos.add(dto);
		}
	    } else {
		if (StringUtil.checkString(exclude)) {
		    if (!exclude.equals(dto.getCode())) {
			dtos.add(dto);
		    }
		}
		if (StringUtil.checkString(depthLevel) && "child".equals(depthLevel)) {
		    if (dto.getParentCode() != null)
			dtos.add(dto);
		} else {
		    if (dto.getParentCode() == null)
			dtos.add(dto);
		}
	    }

	} // while (iterator.hasNext()) {
	Collections.sort(dtos, ComparatorUtil.NUMBERCODESORT);
	Iterator<NumberCodeDTO> iterator2 = dtos.iterator();
	maps = new ArrayList<Map<String, Object>>();
	while (iterator2.hasNext()) {
	    NumberCodeDTO codeDTO = iterator2.next();
	    HashMap<String, Object> hash = new HashMap<String, Object>();
	    hash.put("codeType", StringUtil.checkNull(codeDTO.getCodetype().toString()));
	    hash.put("code", StringUtil.checkNull(codeDTO.getCode()));
	    hash.put("value", StringUtil.checkNull(codeDTO.getDisplay(locale)));
	    hash.put("description", StringUtil.checkNull(codeDTO.getDescription()));
	    hash.put("sorting", StringUtil.checkNull(codeDTO.getSorting()));
	    hash.put("lang", StringUtil.checkNull(locale + ""));
	    hash.put("vendercode", StringUtil.checkNull(codeDTO.getVendorCode()));
	    hash.put("ida2a2", StringUtil.checkNull(codeDTO.getLoid()));
	    hash.put("oid", StringUtil.checkNull(codeDTO.getOid()));
	    hash.put("oid2", StringUtil.checkNull(codeDTO.getParentOid()));
	    hash.put("abbr", StringUtil.checkNull(codeDTO.getAbbr()));
	    maps.add(hash);
	}
	return maps;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getCarNumberCodeList(Map<String, Object> parameter) throws Exception {

	String carCategory = StringUtil.checkNull((String) parameter.get("carCategory"));
	ArrayList<CarNumberCodeDTO> numberCodeDTOs = (ArrayList<CarNumberCodeDTO>) CacheManager.getItem(CacheManager.CARNUMBERCODECACHE);

	ArrayList<Map<String, Object>> arrayList = null;
	if (numberCodeDTOs != null && numberCodeDTOs.size() > 0) {
	    arrayList = new ArrayList<Map<String, Object>>();
	    for (CarNumberCodeDTO dto : numberCodeDTOs) {
		if (dto.getNumberCode().getCode().equals(carCategory)) {
		    HashMap<String, Object> hashMap = new HashMap<String, Object>();
		    hashMap.put("code", StringUtil.checkNull(dto.getCode()));
		    hashMap.put("value", StringUtil.checkNull(dto.getName()));
		    arrayList.add(hashMap);
		}
	    }
	}
	return arrayList;
    }

    @Override
    public PageControl findPaging(NumberCodeDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public NumberCodeDTO save(NumberCodeDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public NumberCodeDTO modify(NumberCodeDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public NumberCodeDTO delete(NumberCodeDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public List<NumberCodeDTO> find(NumberCodeDTO paramObject) throws Exception {
	return null;
    }
}
