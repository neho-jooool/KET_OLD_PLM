package ext.ket.shared.suggest.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.TableColumn;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.WorkProject;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.code.entity.CarNumberCodeDTO;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.code.service.CacheManager;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.suggest.entity.KETSuggestDTO;
import ext.ket.shared.util.SearchUtil;

/**
 * Suggest 기능 구현 서비스
 * 
 * @클래스명 : StandardSuggestService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 1.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETSuggestService extends StandardManager implements KETSuggestService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1218900928679220599L;
    private static int SUGGEST_MAXIMUN_COUNT = 1000;

    public static StandardKETSuggestService newStandardKETSuggestService() throws WTException {
	StandardKETSuggestService instance = new StandardKETSuggestService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<Map<String, String>> find(KETSuggestDTO paramObject) throws Exception {

	List<Map<String, String>> list = null;
	String suggestType = paramObject.getSuggestType();

	if (KETSuggestDTO.SUGGEST_USER.equals(suggestType)) { // 사용자검색
	    list = getUserQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_USER_ALL.equals(suggestType)) { // 사용자검색
	    list = getUserQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_DEPARTMENT.equals(suggestType)) { // 부서검색
	    list = getDepartmentQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_DIENO.equals(suggestType)) { // Die No검색
	    list = getDieNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_REVIEWPROJNO.equals(suggestType)) { // 검토프로젝트검색
	    list = getReviewProjNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PRODUCTPROJNO.equals(suggestType)) { // 제품/금형프로젝트검색
	    list = getProductProjNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_MOLDPROJNO.equals(suggestType)) { // 금형프로젝트검색
	    list = getMoldProjNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_WORKPROJNO.equals(suggestType)) { // 업무프로젝트검색
	    list = getWorkProjNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_CARTYPE.equals(suggestType)) { // 차종
	    list = getCarTypeByOemTypeQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PROJECTDOCTYPE.equals(suggestType)) { // 개발산출물문서분류
	    list = getProjectDocTypeQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_TECHDOCTYPE.equals(suggestType)) { // 기술문서분류
	    list = getTechDocTypeQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_CUSTOMER.equals(suggestType)) { // 고객사
	    list = getCustomerQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PARTNO.equals(suggestType)) { // 부품
	    list = getPartNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_EPMNO.equals(suggestType)) { // 도면
	    list = getEpmNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PARTCLAZ.equals(suggestType)) { // 부품분류
	    list = getPartClazQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PARTCLAZ_2LVL.equals(suggestType)) { // 부품분류 2 Level
	    list = getPartClaz2LvlQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_PRODUCTTYPE.equals(suggestType)) { // 제품분류
	    list = getProductTypeQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_ECONO.equals(suggestType)) { // ECO
	    list = getEcoNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_ECNNO.equals(suggestType)) { // ECN
	    list = getEcnNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_ECRNO.equals(suggestType)) { // ECR
	    list = getEcrNoQuerySpec(paramObject);

	} else if (KETSuggestDTO.SUGGEST_CUSTOMEREVENT.equals(suggestType)) { // 최종사용처
	    list = findByCustomerEvent(paramObject);
	} else if (KETSuggestDTO.SUGGEST_PRODUCTPARTNO.equals(suggestType)) {
	    list = getProductPartNo(paramObject);
	} else if (KETSuggestDTO.SUGGEST_USERDEPT.equals(suggestType)) {
	    list = getUserDeptQuerySpec(paramObject);
	} else if (KETSuggestDTO.SUGGEST_DEPTUSER.equals(suggestType)) {
	    list = getDepartmentUserQuerySpec(paramObject);
	} else if (KETSuggestDTO.SUGGEST_SPSERIES.equals(suggestType)) {
	    list = getSpSeriesQuerySpec(paramObject);
	} else if (KETSuggestDTO.SUGGEST_KETTAG.equals(suggestType)) {
	    list = getKetTagQuerySpec(paramObject);

	}
	return list;
    }

    private List<Map<String, String>> getProductPartNo(KETSuggestDTO paramObject) throws Exception {
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	String queryStr = getProductPartNoQueryByPartNo(paramObject.getInputParam());
	Kogger.debug(getClass(), queryStr);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {
	    list = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, String>>() {
		@Override
		public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		    Map<String, String> partInfo = new HashMap<String, String>();
		    partInfo.put("value", rs.getString("oid"));
		    partInfo.put("label", rs.getString("partnumber"));
		    return partInfo;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return list;
    }

    private String getProductPartNoQueryByPartNo(String partNo) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT m.wtpartnumber as partnumber                                              \n");
	sb.append("     , 'wt.part.WTPart:'||p.ida2a2 as oid                                                                               \n");
	sb.append("  FROM  WTPart p                                                                                      \n");
	sb.append("      , WTPartMaster m                                                                                \n");
	sb.append("      , ( SELECT MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid                      \n");
	sb.append("            FROM WTPart p2, WTPartMaster m2                                           \n");
	sb.append("           WHERE 1=1                                                 \n");
	sb.append("             AND ( m2.wtpartnumber LIKE '" + partNo + "%' )                                                     \n");
	sb.append("             AND P2." + PartSpecEnum.SpPartType.getColumnName()
	        + " NOT IN ( 'D', 'M', 'O' )                                                                       \n");
	sb.append("             AND p2.ida3masterreference = m2.ida2a2                                                   \n");
	sb.append("             AND p2.statecheckoutinfo != 'wrk'                                                        \n");
	sb.append("             AND p2.latestiterationinfo = 1                                                           \n");
	sb.append("           GROUP BY m2.wtpartnumber ) x                                                               \n");
	sb.append("      , phasetemplate ph                                                                              \n");
	sb.append("      , phaselink pl                                                                                  \n");
	sb.append("  WHERE p.ida3masterreference = m.ida2a2                                                              \n");
	sb.append("    AND p.ida2a2 = x.objid                                                                            \n");
	sb.append("    AND pl.ida3a5 = p.ida3a2state                                                                     \n");
	sb.append("    AND pl.ida3b5 = ph.ida2a2                                                                         \n");
	sb.append("    AND ph.phasestate = p.statestate                                                                  \n");
	return sb.toString();
    }

    /**
     * 기술문서 번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getTechDocTypeQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getTechDocTypeQuerySpec(KETSuggestDTO paramObject) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QuerySpec spec = new QuerySpec();
	int idx = spec.appendClassList(KETDocumentCategory.class, true);

	SearchUtil.appendBOOLEAN(spec, KETDocumentCategory.class, KETDocumentCategory.IS_USED, SearchCondition.IS_TRUE, idx);
	SearchUtil.appendGREATER_THAN(spec, KETDocumentCategory.class, KETDocumentCategory.CATEGORY_LEVEL, 3, idx);
	SearchUtil.appendEQUAL(spec, KETDocumentCategory.class, KETDocumentCategory.DOC_TYPE_CODE, "TD", idx, false);
	SearchUtil.appendSLIKE(spec, KETDocumentCategory.class, KETDocumentCategory.CATEGORY_NAME, paramObject.getInputParam(), idx, false);

	Kogger.debug(getClass(), spec);

	QueryResult queryResult = PersistenceHelper.manager.find((StatementSpec) spec);
	while (queryResult.hasMoreElements()) {
	    Object[] objects = (Object[]) queryResult.nextElement();
	    KETDocumentCategory category = (KETDocumentCategory) objects[0];
	    Map<String, String> map = new HashMap<String, String>();
	    // map.put("value", String.valueOf(CommonUtil.getOIDLongValue(category)));
	    map.put("value", category.getCategoryCode());
	    String label = getDocumentCategoryLabel(category, category.getCategoryName());
	    map.put("label", label);
	    list.add(map);
	}

	return list;
    }

    /**
     * 문서라벨 생성 메소드
     * 
     * @param category
     * @param label
     * @return
     * @throws Exception
     * @메소드명 : getDocumentCategoryLabel
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private String getDocumentCategoryLabel(KETDocumentCategory category, String label) throws Exception {

	KETDocumentCategory pcategory = null;

	String pCategoryCode = category.getParentCategoryCode();
	if (!"ROOT".equalsIgnoreCase(pCategoryCode)) {

	    QuerySpec spec = new QuerySpec();
	    int idx = spec.appendClassList(KETDocumentCategory.class, true);
	    SearchUtil.appendEQUAL(spec, KETDocumentCategory.class, KETDocumentCategory.CATEGORY_CODE, category.getParentCategoryCode(),
		    idx, false);
	    SearchUtil.appendBOOLEAN(spec, KETDocumentCategory.class, KETDocumentCategory.IS_USED, SearchCondition.IS_TRUE, idx);

	    Kogger.debug(getClass(), spec);

	    QueryResult queryResult = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (queryResult.hasMoreElements()) {
		Object[] objects = (Object[]) queryResult.nextElement();
		pcategory = (KETDocumentCategory) objects[0];
	    }

	    if (pcategory != null) {
		label = pcategory.getCategoryName() + "/" + label;
		label = getDocumentCategoryLabel(pcategory, label);
	    }
	} else {
	    // if (category.getCategoryCode().startsWith("PD")) {
	    // label = "개발산출문서/" + label;
	    // } else {
	    // label = "기술문서/" + label;
	    // }
	}
	return label;
    }

    /**
     * 개발산출물 번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getProjectDocTypeQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getProjectDocTypeQuerySpec(KETSuggestDTO paramObject) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QuerySpec spec = new QuerySpec();
	int idx = spec.appendClassList(KETDocumentCategory.class, true);

	SearchUtil.appendBOOLEAN(spec, KETDocumentCategory.class, KETDocumentCategory.IS_USED, SearchCondition.IS_TRUE, idx);
	SearchUtil.appendGREATER_THAN(spec, KETDocumentCategory.class, KETDocumentCategory.CATEGORY_LEVEL, 2, idx);
	SearchUtil.appendEQUAL(spec, KETDocumentCategory.class, KETDocumentCategory.DOC_TYPE_CODE, "PD", idx, false);
	SearchUtil.appendSLIKE(spec, KETDocumentCategory.class, KETDocumentCategory.CATEGORY_NAME, paramObject.getInputParam(), idx, false);

	Kogger.debug(getClass(), spec);
	QueryResult queryResult = PersistenceHelper.manager.find((StatementSpec) spec);
	while (queryResult.hasMoreElements()) {
	    Object[] objects = (Object[]) queryResult.nextElement();
	    KETDocumentCategory category = (KETDocumentCategory) objects[0];
	    Map<String, String> map = new HashMap<String, String>();
	    // map.put("value", String.valueOf(CommonUtil.getOIDLongValue(category)));
	    map.put("value", category.getCategoryCode());
	    String label = getDocumentCategoryLabel(category, category.getCategoryName());
	    map.put("label", label);
	    list.add(map);
	}

	return list;
    }

    private List<Map<String, String>> getUserDeptQuerySpec(KETSuggestDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	int u_idx = spec.appendClassList(WTUser.class, false);
	int p_idx = spec.appendClassList(People.class, false);
	int d_idx = spec.appendClassList(Department.class, false);

	TableColumn tc_name = new TableColumn(spec.getFromClause().getAliasAt(u_idx), WTUser.FULL_NAME);
	TableColumn tc_id = new TableColumn(spec.getFromClause().getAliasAt(u_idx), WTUser.NAME);
	TableColumn tc_deptname = new TableColumn(spec.getFromClause().getAliasAt(d_idx), Department.NAME);
	ColumnExpression divide = ConstantExpression.newExpression("/");
	SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.CONCAT, new ColumnExpression[] { tc_name, divide, tc_deptname,
	        divide, tc_id });

	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, u_idx, false);
	spec.appendSelect(sqlFunction, false);
	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, d_idx, false);
	spec.appendSelectAttribute(Department.NAME, d_idx, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        People.class, "userReference.key.id")), new int[] { u_idx, p_idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(new ClassAttribute(Department.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        People.class, "departmentReference.key.id")), new int[] { d_idx, p_idx });
	SearchUtil.appendBOOLEAN(spec, WTUser.class, WTUser.DISABLED, SearchCondition.IS_FALSE, u_idx);
	SearchUtil.appendBOOLEAN(spec, People.class, People.IS_DISABLE, SearchCondition.IS_FALSE, p_idx);
	SearchUtil.appendBOOLEAN(spec, Department.class, Department.ENABLED, SearchCondition.IS_TRUE, d_idx);
	SearchUtil.appendSLIKE(spec, WTUser.class, WTUser.FULL_NAME, paramObject.getInputParam(), u_idx, false);

	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "wt.org.WTUser:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		map.put("subValue", "e3ps.groupware.company.Department:" + String.valueOf(objects[2]));
		map.put("subLabel", String.valueOf(objects[3]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 사용자 검색 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getUserQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getUserQuerySpec(KETSuggestDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();

	String suggestType = paramObject.getSuggestType();
	int u_idx = spec.appendClassList(WTUser.class, false);
	int p_idx = spec.appendClassList(People.class, false);
	int d_idx = spec.appendClassList(Department.class, false);

	TableColumn tc_name = new TableColumn(spec.getFromClause().getAliasAt(u_idx), WTUser.FULL_NAME);
	TableColumn tc_id = new TableColumn(spec.getFromClause().getAliasAt(u_idx), WTUser.NAME);
	TableColumn tc_deptname = new TableColumn(spec.getFromClause().getAliasAt(d_idx), Department.NAME);
	ColumnExpression divide = ConstantExpression.newExpression("/");
	SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.CONCAT, new ColumnExpression[] { tc_name, divide, tc_deptname,
	        divide, tc_id });

	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, u_idx, false);
	spec.appendSelect(sqlFunction, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        People.class, "userReference.key.id")), new int[] { u_idx, p_idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(new ClassAttribute(Department.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        People.class, "departmentReference.key.id")), new int[] { d_idx, p_idx });

	if (!KETSuggestDTO.SUGGEST_USER_ALL.equals(suggestType)) {
	    SearchUtil.appendBOOLEAN(spec, WTUser.class, WTUser.DISABLED, SearchCondition.IS_FALSE, u_idx);
	    SearchUtil.appendBOOLEAN(spec, People.class, People.IS_DISABLE, SearchCondition.IS_FALSE, p_idx);
	    SearchUtil.appendBOOLEAN(spec, Department.class, Department.ENABLED, SearchCondition.IS_TRUE, d_idx);
	}

	SearchUtil.appendSLIKE(spec, WTUser.class, WTUser.FULL_NAME, paramObject.getInputParam(), u_idx, false);

	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "wt.org.WTUser:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 부서검색 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getDepartmentQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getDepartmentUserQuerySpec(KETSuggestDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	int d_idx = spec.appendClassList(Department.class, false);

	spec.appendSelect(new ClassAttribute(Department.class, WTAttributeNameIfc.OID_CLASSNAME), new int[] { d_idx }, false);
	spec.appendSelect(new ClassAttribute(Department.class, WTAttributeNameIfc.ID_NAME), new int[] { d_idx }, false);
	spec.appendSelect(new ClassAttribute(Department.class, Department.NAME), new int[] { d_idx }, false);

	SearchUtil.appendBOOLEAN(spec, Department.class, Department.ENABLED, SearchCondition.IS_TRUE, d_idx);
	SearchUtil.appendSLIKE(spec, Department.class, Department.NAME, paramObject.getInputParam(), d_idx, false);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", objects[0] + ":" + objects[1]);
		map.put("label", String.valueOf(objects[2]));

		WTUser wtUser = (WTUser) PeopleHelper.manager
		        .getChiefUser((Department) CommonUtil.getObject(objects[0] + ":" + objects[1]));
		if (wtUser == null || wtUser.equals(null)) {
		    map.put("subValue", "");
		    map.put("subLabel", "");
		} else {
		    map.put("subValue", CommonUtil.getOIDString(wtUser));
		    map.put("subLabel", wtUser.getFullName());
		}

		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 부서검색 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getDepartmentQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getDepartmentQuerySpec(KETSuggestDTO paramObject) throws Exception {

	QuerySpec spec = new QuerySpec();
	int d_idx = spec.appendClassList(Department.class, false);

	spec.appendSelect(new ClassAttribute(Department.class, WTAttributeNameIfc.OID_CLASSNAME), new int[] { d_idx }, false);
	spec.appendSelect(new ClassAttribute(Department.class, WTAttributeNameIfc.ID_NAME), new int[] { d_idx }, false);
	spec.appendSelect(new ClassAttribute(Department.class, Department.NAME), new int[] { d_idx }, false);

	SearchUtil.appendBOOLEAN(spec, Department.class, Department.ENABLED, SearchCondition.IS_TRUE, d_idx);
	SearchUtil.appendSLIKE(spec, Department.class, Department.NAME, paramObject.getInputParam(), d_idx, false);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", objects[0] + ":" + objects[1]);
		map.put("label", String.valueOf(objects[2]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * Die No Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getDieNoQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @SuppressWarnings("deprecation")
    private List<Map<String, String>> getDieNoQuerySpec(KETSuggestDTO paramObject) throws Exception {
	QuerySpec spec = new QuerySpec();
	int index1 = spec.addClassList(MoldProject.class, false);
	int index2 = spec.addClassList(MoldItemInfo.class, false);

	TableColumn tc1 = new TableColumn(spec.getFromClause().getAliasAt(index1), "CLASSNAMEA2A2");
	TableColumn tc2 = new TableColumn(spec.getFromClause().getAliasAt(index1), "IDA2A2");
	TableColumn tc3 = new TableColumn(spec.getFromClause().getAliasAt(index2), MoldItemInfo.DIE_NO);
	TableColumn tc4 = new TableColumn(spec.getFromClause().getAliasAt(index2), MoldItemInfo.PART_NAME);

	spec.appendSelect(tc1, new int[] { index1 }, false);
	spec.appendSelect(tc2, new int[] { index1 }, false);
	spec.appendSelect(tc3, new int[] { index2 }, false);
	spec.appendSelect(tc4, new int[] { index2 }, false);

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldProject.class, "moldInfoReference.key.id"), "=",
	        new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id"));
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { index1, index2 });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { index1 });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { index1 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.DIE_NO, SearchCondition.LIKE, paramObject.getInputParam()
	        + "%", false), new int[] { index2 });
	spec.appendAnd();
	spec.appendRowNumCondition(SUGGEST_MAXIMUN_COUNT);
	SearchUtil.setOrderBy(spec, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, false);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", objects[0] + ":" + objects[1]);
		map.put("label", String.valueOf(objects[2]));
		map.put("name", String.valueOf(objects[3]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 검토프로젝트번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getReviewProjNoQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getReviewProjNoQuerySpec(KETSuggestDTO paramObject) throws Exception {

	// ReviewProject
	QuerySpec spec = new QuerySpec();
	int m_idx = spec.addClassList(E3PSProjectMaster.class, false);
	int p_idx = spec.addClassList(ReviewProject.class, false);

	spec.appendSelect(new ClassAttribute(ReviewProject.class, WTAttributeNameIfc.ID_NAME), new int[] { p_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO), new int[] { m_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NAME), new int[] { m_idx }, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME), "=",
	        new ClassAttribute(ReviewProject.class, "masterReference.key.id")), new int[] { m_idx, p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(ReviewProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(ReviewProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { p_idx });
	SearchUtil.appendSLIKE(spec, E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO, paramObject.getInputParam(), m_idx, false);
	SearchUtil.setOrderBy(spec, E3PSProjectMaster.class, m_idx, E3PSProjectMaster.PJT_NO, "pjtno", false);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "e3ps.project.ReviewProject:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		map.put("name", String.valueOf(objects[2]));
		list.add(map);
	    }
	}
	return list;
    }

    private List<Map<String, String>> getWorkProjNoQuerySpec(KETSuggestDTO paramObject) throws Exception {

	// WorkProject
	QuerySpec spec = new QuerySpec();
	int m_idx = spec.addClassList(E3PSProjectMaster.class, false);
	int p_idx = spec.addClassList(WorkProject.class, false);

	spec.appendSelect(new ClassAttribute(WorkProject.class, WTAttributeNameIfc.ID_NAME), new int[] { p_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO), new int[] { m_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NAME), new int[] { m_idx }, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME), "=",
	        new ClassAttribute(WorkProject.class, "masterReference.key.id")), new int[] { m_idx, p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { p_idx });
	SearchUtil.appendSLIKE(spec, E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO, paramObject.getInputParam(), m_idx, false);
	SearchUtil.setOrderBy(spec, E3PSProjectMaster.class, m_idx, E3PSProjectMaster.PJT_NO, "pjtno", false);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "e3ps.project.WorkProject:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		map.put("name", String.valueOf(objects[2]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 제품/금형 프로젝트 번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getProductProjNoQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getProductProjNoQuerySpec(KETSuggestDTO paramObject) throws Exception {

	// ProductProject
	QuerySpec spec = new QuerySpec();
	int m_idx = spec.addClassList(E3PSProjectMaster.class, false);
	int p_idx = spec.addClassList(ProductProject.class, false);

	spec.appendSelect(new ClassAttribute(ProductProject.class, WTAttributeNameIfc.ID_NAME), new int[] { p_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO), new int[] { m_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NAME), new int[] { m_idx }, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME), "=",
	        new ClassAttribute(ProductProject.class, "masterReference.key.id")), new int[] { m_idx, p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { p_idx });
	SearchUtil.appendSLIKE(spec, E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO, paramObject.getInputParam(), m_idx, false);
	SearchUtil.setOrderBy(spec, E3PSProjectMaster.class, m_idx, E3PSProjectMaster.PJT_NO, "pjtno", false);
	spec.setDistinct(true);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "e3ps.project.ProductProject:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		map.put("name", String.valueOf(objects[2]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 금형 프로젝트 번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getMoldProjNoQuerySpec
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 9.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private List<Map<String, String>> getMoldProjNoQuerySpec(KETSuggestDTO paramObject) throws Exception {

	// ProductProject
	QuerySpec spec = new QuerySpec();
	int m_idx = spec.addClassList(E3PSProjectMaster.class, false);
	int p_idx = spec.addClassList(MoldProject.class, false);

	spec.appendSelect(new ClassAttribute(MoldProject.class, WTAttributeNameIfc.ID_NAME), new int[] { p_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO), new int[] { m_idx }, false);
	spec.appendSelect(new ClassAttribute(E3PSProjectMaster.class, E3PSProjectMaster.PJT_NAME), new int[] { m_idx }, false);

	spec.appendWhere(new SearchCondition(new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME), "=",
	        new ClassAttribute(MoldProject.class, "masterReference.key.id")), new int[] { m_idx, p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { p_idx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { p_idx });
	SearchUtil.appendSLIKE(spec, E3PSProjectMaster.class, E3PSProjectMaster.PJT_NO, paramObject.getInputParam(), m_idx, false);
	SearchUtil.setOrderBy(spec, E3PSProjectMaster.class, m_idx, E3PSProjectMaster.PJT_NO, "pjtno", false);
	spec.setDistinct(true);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	QueryResult result = PersistenceServerHelper.manager.query(spec);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", "e3ps.project.MoldProject:" + objects[0]);
		map.put("label", String.valueOf(objects[1]));
		map.put("name", String.valueOf(objects[2]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 차종검색 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getCarTypeQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private List<Map<String, String>> getCarTypeQuerySpec(KETSuggestDTO paramObject) throws Exception {

	ArrayList<CarNumberCodeDTO> numberCodeDTOs = (ArrayList<CarNumberCodeDTO>) CacheManager.getItem(CacheManager.CARNUMBERCODECACHE);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	for (CarNumberCodeDTO carNumberCodeDTO : numberCodeDTOs) {
	    String temp = carNumberCodeDTO.getName();
	    if (temp.toUpperCase().startsWith(paramObject.getInputParam().toUpperCase())) {
		NumberCode numberCode = carNumberCodeDTO.getNumberCode();
		String key = CommonUtil.getOIDString(numberCode);
		String value = numberCode.getName() + "/" + carNumberCodeDTO.getName();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", key);
		map.put("label", value);
		list.add(map);
	    }
	}

	return list;
    }

    private List<Map<String, String>> getCarTypeByOemTypeQuerySpec(KETSuggestDTO paramObject) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx4 = qs.appendClassList(OEMProjectType.class, true);
	SearchCondition sc = new SearchCondition(OEMProjectType.class, OEMProjectType.NAME, SearchCondition.LIKE, paramObject
	        .getInputParam().toUpperCase() + "%", false);
	qs.appendWhere(sc, new int[] { idx4 });

	ClassAttribute ca = new ClassAttribute(OEMProjectType.class, "name");
	qs.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 });

	QueryResult qr4 = PersistenceHelper.manager.find((StatementSpec) qs);

	Kogger.debug(getClass(), qs);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	while (qr4.hasMoreElements()) {
	    Object[] o2 = (Object[]) qr4.nextElement();
	    OEMProjectType opt = (OEMProjectType) o2[0];
	    String key = CommonUtil.getOIDString(opt);
	    String value = StringUtil.checkNull(opt.getMaker().getName()) + "/" + opt.getName();
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("value", StringUtil.htmlCharDecode(key));
	    map.put("label", value);
	    list.add(map);
	}

	return list;
    }

    /**
     * 고객사 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getCustomerQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getCustomerQuerySpec(KETSuggestDTO paramObject) throws Exception {

	@SuppressWarnings("unchecked")
	ArrayList<NumberCodeDTO> numberCodeDTOs = (ArrayList<NumberCodeDTO>) CacheManager.getItem("SUBCONTRACTOR");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	for (NumberCodeDTO numberCodeDTO : numberCodeDTOs) {
	    String temp = numberCodeDTO.getDisplay();
	    if (temp.toUpperCase().startsWith(paramObject.getInputParam().toUpperCase())) {
		String key = numberCodeDTO.getOid();
		String value = temp;
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", key);
		map.put("label", value);
		list.add(map);
	    }
	}

	return list;
    }

    /**
     * 부품번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getPartNoQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getPartNoQuerySpec(KETSuggestDTO paramObject) throws Exception {
	try {
	    QuerySpec spec = new QuerySpec();
	    int pm_idx = spec.appendClassList(WTPartMaster.class, false);
	    int p_idx = spec.appendClassList(WTPart.class, false);

	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(WTPart.class,
		    "versionInfo.identifier.versionSortId"));
	    // function.setColumnAlias(spec.getFromClause().getAliasAt(p_idx));

	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(function, new int[] { p_idx }, false);

	    spec.appendWhere(new SearchCondition(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(WTPart.class, "masterReference.key.id")), new int[] { pm_idx, p_idx });

	    // Others도 보여준다.
	    // spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(WTPart.class, PartSpecEnum.SpPartType.getQuerySpecCode(), SearchCondition.NOT_EQUAL,
	    // "O"), new int[] { p_idx });
	    // spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(WTPart.class, PartSpecEnum.SpIsDeleted.getQuerySpecCode(), SearchCondition.NOT_EQUAL,
	    // "Y"), new int[] { p_idx });

	    SearchUtil.appendBOOLEAN(spec, WTPart.class, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);
	    SearchUtil.appendSLIKE(spec, WTPartMaster.class, WTPartMaster.NUMBER, paramObject.getInputParam(), pm_idx, false);

	    PartSpecEnum partSpec = PartSpecEnum.toPartSpecEnumByAttrCode(paramObject.getWtPartColumn());
	    String wtPartColumnValue = paramObject.getWtPartColumnValue();

	    if (partSpec != null && StringUtils.isNotEmpty(partSpec.getQuerySpecCode()) && StringUtils.isNotEmpty(wtPartColumnValue)) {
		SearchUtil.appendEQUAL(spec, WTPart.class, partSpec.getQuerySpecCode(), wtPartColumnValue, p_idx);
	    }

	    String likeValue = paramObject.getWtPartNoLike();
	    if (StringUtils.isNotEmpty(likeValue)) {
		SearchUtil.appendSLIKE(spec, WTPartMaster.class, WTPartMaster.NUMBER, likeValue, pm_idx, false);
	    }

	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTPartMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(WTPartMaster.class, WTPartMaster.NAME), new int[] { pm_idx }, false);

	    SearchUtil.setOrderBy(spec, WTPartMaster.class, pm_idx, WTPartMaster.NUMBER, false);
	    spec.setDistinct(true);

	    // spec.appendAnd();
	    // spec.appendRowNumCondition(SUGGEST_MAXIMUN_COUNT);
	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    Kogger.debug(getClass(), spec);
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    QueryResult result = PersistenceServerHelper.manager.query(spec);
	    if (result != null) {
		while (result.hasMoreElements()) {
		    Object[] objects = (Object[]) result.nextElement();
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("value", String.valueOf(objects[0]));
		    map.put("label", String.valueOf(objects[1]));
		    map.put("name", String.valueOf(objects[2]));
		    list.add(map);
		}
		if (list.size() >= SUGGEST_MAXIMUN_COUNT) {
		    list = list.subList(0, SUGGEST_MAXIMUN_COUNT);
		}
	    }
	    return list;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    /**
     * 부품 분류체계
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getPartClazQuerySpec
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private List<Map<String, String>> getPartClazQuerySpec(KETSuggestDTO paramObject) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	try {

	    String queryStr = getPartClazQueryByClazName(paramObject.getInputParam());
	    // Kogger.debug(getClass(), queryStr);

	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    list = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, String>>() {
		@Override
		public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		    Map<String, String> partInfo = new HashMap<String, String>();
		    partInfo.put("value", rs.getString("OID"));
		    partInfo.put("label", rs.getString("CLASSKRNAME"));
		    return partInfo;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	}

	return list;
    }

    private String getPartClazQueryByClazName(String partClazKrName) throws Exception {

	StringBuffer buffer = new StringBuffer();

	buffer.append("	SELECT OID, CLASSKRNAMEPATH AS CLASSKRNAME \n");
	buffer.append("	FROM \n");
	buffer.append("	( \n");
	buffer.append("	SELECT LEVEL LEV \n");
	buffer.append("	, C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID \n");
	buffer.append("	, CONNECT_BY_ISLEAF AS LEAF  \n");
	buffer.append("	, SUBSTR(SYS_CONNECT_BY_PATH(C.CLASSKRNAME,' / '), (SELECT LENGTH(C.CLASSKRNAME)  FROM KETPartClassification C WHERE IDA3A3 = 0) + 6) AS CLASSKRNAMEPATH \n");
	buffer.append("	, C.CLASSKRNAME \n");
	buffer.append("	, C.CLASSENNAME \n");
	buffer.append("	, C.CLASSZHNAME \n");
	buffer.append("	  FROM KETPartClassification C \n");
	buffer.append("	  WHERE 1 = 1 \n");
	buffer.append("	  AND C.USECLAZ = 1 \n");
	buffer.append("	  START WITH IDA3A3 = 0 \n");
	buffer.append("	  CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	buffer.append("	)WHERE LEAF = 1 \n");
	buffer.append("	AND UPPER(CLASSKRNAME) LIKE '" + partClazKrName.toUpperCase() + "%' \n");
	buffer.append("	ORDER BY CLASSKRNAMEPATH \n");

	return buffer.toString();
    }

    private List<Map<String, String>> getPartClaz2LvlQuerySpec(KETSuggestDTO paramObject) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	try {

	    String queryStr = getPartClazQuery2LvlByClazName(paramObject.getInputParam());
	    // Kogger.debug(getClass(), queryStr);

	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    list = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, String>>() {
		@Override
		public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		    Map<String, String> partInfo = new HashMap<String, String>();
		    partInfo.put("value", rs.getString("OID"));
		    partInfo.put("label", rs.getString("CLASSKRNAME"));
		    return partInfo;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	}

	return list;
    }

    private String getPartClazQuery2LvlByClazName(String partClazKrName) throws Exception {

	StringBuffer buffer = new StringBuffer();

	buffer.append("	SELECT OID, CLASSKRNAMEPATH AS CLASSKRNAME \n");
	buffer.append("	FROM \n");
	buffer.append("	( \n");
	buffer.append("	SELECT LEVEL LEV \n");
	buffer.append("	, C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID \n");
	buffer.append("	, CONNECT_BY_ISLEAF AS LEAF  \n");
	buffer.append("	, SUBSTR(SYS_CONNECT_BY_PATH(C.CLASSKRNAME,' / '), (SELECT LENGTH(C.CLASSKRNAME)  FROM KETPartClassification C WHERE IDA3A3 = 0) + 6) AS CLASSKRNAMEPATH \n");
	buffer.append("	, C.CLASSKRNAME \n");
	buffer.append("	, C.CLASSENNAME \n");
	buffer.append("	, C.CLASSZHNAME \n");
	buffer.append("	  FROM KETPartClassification C \n");
	buffer.append("	  WHERE 1 = 1 \n");
	buffer.append("	  AND C.USECLAZ = 1 \n");
	buffer.append("	  START WITH IDA3A3 = 0 \n");
	buffer.append("	  CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	buffer.append("	)WHERE LEV = 3 \n");
	buffer.append("	AND UPPER(CLASSKRNAME) LIKE '" + partClazKrName.toUpperCase() + "%' \n");
	buffer.append("	ORDER BY CLASSKRNAMEPATH \n");

	return buffer.toString();
    }

    /**
     * 도면번호 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getEpmNoQuerySpec
     * @작성자 : Tklee
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getEpmNoQuerySpec(KETSuggestDTO paramObject) throws Exception {
	try {
	    QuerySpec spec = new QuerySpec();
	    int pm_idx = spec.appendClassList(EPMDocumentMaster.class, false);
	    int p_idx = spec.appendClassList(EPMDocument.class, false);

	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, new ClassAttribute(EPMDocument.class,
		    "versionInfo.identifier.versionSortId"));
	    // function.setColumnAlias(spec.getFromClause().getAliasAt(p_idx));

	    spec.appendSelect(new ClassAttribute(EPMDocumentMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(EPMDocumentMaster.class, EPMDocumentMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendSelect(new ClassAttribute(EPMDocumentMaster.class, EPMDocumentMaster.NAME), new int[] { pm_idx }, false);
	    spec.appendSelect(function, new int[] { p_idx }, false);

	    spec.appendWhere(new SearchCondition(new ClassAttribute(EPMDocumentMaster.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(EPMDocument.class, "masterReference.key.id")), new int[] { pm_idx, p_idx });

	    SearchUtil.appendBOOLEAN(spec, EPMDocument.class, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);
	    SearchUtil.appendSLIKE(spec, EPMDocumentMaster.class, EPMDocumentMaster.NUMBER, paramObject.getInputParam(), pm_idx, false);
	    spec.appendGroupBy(new ClassAttribute(EPMDocumentMaster.class, WTAttributeNameIfc.ID_NAME), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(EPMDocumentMaster.class, EPMDocumentMaster.NUMBER), new int[] { pm_idx }, false);
	    spec.appendGroupBy(new ClassAttribute(EPMDocumentMaster.class, EPMDocumentMaster.NAME), new int[] { pm_idx }, false);

	    SearchUtil.setOrderBy(spec, EPMDocumentMaster.class, pm_idx, EPMDocumentMaster.NUMBER, false);
	    spec.setDistinct(true);
	    // spec.appendAnd();
	    // spec.appendRowNumCondition(SUGGEST_MAXIMUN_COUNT);

	    if (!spec.isAdvancedQueryEnabled())
		spec.setAdvancedQueryEnabled(true);
	    Kogger.debug(getClass(), spec);
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    QueryResult result = PersistenceServerHelper.manager.query(spec);
	    if (result != null) {
		while (result.hasMoreElements()) {
		    Object[] objects = (Object[]) result.nextElement();
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("value", String.valueOf(objects[0]));
		    map.put("label", String.valueOf(objects[1]));
		    map.put("name", String.valueOf(objects[2]));
		    list.add(map);
		}
		if (list.size() >= SUGGEST_MAXIMUN_COUNT) {
		    list = list.subList(0, SUGGEST_MAXIMUN_COUNT);
		}
	    }
	    return list;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    /**
     * 제품분류 Suggest
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getProductTypeQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getProductTypeQuerySpec(KETSuggestDTO paramObject) throws Exception {

	@SuppressWarnings("unchecked")
	ArrayList<NumberCodeDTO> numberCodeDTOs = (ArrayList<NumberCodeDTO>) CacheManager.getItem("PRODCATEGORYCODE");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	for (NumberCodeDTO numberCodeDTO : numberCodeDTOs) {
	    String temp = numberCodeDTO.getDisplay();
	    if (temp.toUpperCase().startsWith(paramObject.getInputParam().toUpperCase())) {
		String key = numberCodeDTO.getLoid();
		NumberCode pcode = numberCodeDTO.getParentCode();
		String value = "";
		if (pcode != null) {
		    value = pcode.getName() + "/" + temp;
		} else {
		    continue;
		    // value = temp;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", key);
		map.put("label", value);
		list.add(map);
	    }
	}

	return list;
    }

    /**
     * ECO No 검색
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getEcoNoQuerySpec
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getEcoNoQuerySpec(KETSuggestDTO paramObject) throws Exception {
	QuerySpec spec = new QuerySpec();
	int eco_idx = spec.appendClassList(KETProdChangeOrder.class, false);

	ColumnExpression five = ConstantExpression.newExpression(5);
	SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.SUB_STRING, new ColumnExpression[] {
	        new ClassAttribute(KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID), five });

	sqlFunction.setColumnAlias("ecoId");

	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, eco_idx, false);
	spec.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, eco_idx, false);
	spec.appendSelect(sqlFunction, new int[] { eco_idx }, false);
	spec.setDistinct(true);
	spec.appendWhere(new SearchCondition(KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID, SearchCondition.LIKE, "ECO-"
	        + paramObject.getInputParam() + "%"), new int[] { eco_idx });
	spec.setAdvancedQueryEnabled(true);
	// SearchUtil.setOrderBy(spec, KETProdChangeOrder.class, eco_idx, KETProdChangeOrder.ECO_ID, true);
	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	/*
	 * List<Map<String, String>> list = new ArrayList<Map<String, String>>(); QueryResult result =
	 * PersistenceServerHelper.manager.query(spec); if (result != null) { while (result.hasMoreElements()) { Object[] objects =
	 * (Object[]) result.nextElement(); Map<String, String> map = new HashMap<String, String>(); map.put("value",
	 * "e3ps.ecm.entity.KETProdChangeOrder:" + String.valueOf(objects[0])); map.put("label", String.valueOf(objects[1])); list.add(map);
	 * } }
	 */
	QuerySpec mold_spec = new QuerySpec();
	int eco_mold_idx = mold_spec.appendClassList(KETMoldChangeOrder.class, false);

	ColumnExpression mold_five = ConstantExpression.newExpression(5);
	SQLFunction mold_sqlFunction = SQLFunction.newSQLFunction(SQLFunction.SUB_STRING, new ColumnExpression[] {
	        new ClassAttribute(KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID), mold_five });

	mold_sqlFunction.setColumnAlias("ecoId");

	mold_spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, eco_mold_idx, false);
	mold_spec.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, eco_mold_idx, false);
	mold_spec.appendSelect(mold_sqlFunction, new int[] { eco_mold_idx }, false);
	mold_spec.setDistinct(true);
	mold_spec.appendWhere(new SearchCondition(KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID, SearchCondition.LIKE, "ECO-"
	        + paramObject.getInputParam() + "%"), new int[] { eco_mold_idx });
	mold_spec.setAdvancedQueryEnabled(true);
	// SearchUtil.setOrderBy(mold_spec, KETMoldChangeOrder.class, eco_mold_idx, KETMoldChangeOrder.ECO_ID, true);
	if (!mold_spec.isAdvancedQueryEnabled())
	    mold_spec.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), mold_spec);
	/*
	 * QueryResult mold_result = PersistenceServerHelper.manager.query(mold_spec); if (mold_result != null) { while
	 * (mold_result.hasMoreElements()) { Object[] objects = (Object[]) mold_result.nextElement(); Map<String, String> map = new
	 * HashMap<String, String>(); map.put("value", "e3ps.ecm.entity.KETMoldChangeOrder:" + String.valueOf(objects[0])); map.put("label",
	 * String.valueOf(objects[1])); list.add(map); } }
	 */
	CompoundQuerySpec compound_spec = new CompoundQuerySpec();

	compound_spec.setSetOperator(SetOperator.UNION);
	compound_spec.addComponent(spec);
	compound_spec.addComponent(mold_spec);

	ClassAttribute prodEcrCa = new ClassAttribute(KETProdChangeOrder.class, "ecoId");
	prodEcrCa.setColumnAlias("ecoId");

	OrderBy prodEcrCaOrderby = new OrderBy(prodEcrCa, false);

	compound_spec.appendOrderBy(prodEcrCaOrderby);

	ClassAttribute moldEcrCa = new ClassAttribute(KETMoldChangeOrder.class, "ecoId");
	moldEcrCa.setColumnAlias("ecoId");

	OrderBy moldEcrCaOrderby = new OrderBy(moldEcrCa, false);

	compound_spec.appendOrderBy(moldEcrCaOrderby);

	Kogger.debug(getClass(), compound_spec);

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	QueryResult compound_result = PersistenceServerHelper.manager.query(compound_spec);
	if (compound_result != null) {
	    while (compound_result.hasMoreElements()) {
		Object[] objects = (Object[]) compound_result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", String.valueOf(objects[1]) + ":" + String.valueOf(objects[0]));
		map.put("label", String.valueOf(objects[2]));
		list.add(map);
	    }
	}

	return list;
    }

    /**
     * ECN No 검색
     * 
     * @param paramObject
     * @return
     * @메소드명 : getEcnNoQuerySpec
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getEcnNoQuerySpec(KETSuggestDTO paramObject) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * ECR No 검색
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getEcrNoQuerySpec
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private List<Map<String, String>> getEcrNoQuerySpec(KETSuggestDTO paramObject) throws Exception {
	// 제품 ECR
	QuerySpec spec = new QuerySpec();
	int ecr_idx = spec.appendClassList(KETProdChangeRequest.class, false);

	ColumnExpression five = ConstantExpression.newExpression(5);
	SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.SUB_STRING, new ColumnExpression[] {
	        new ClassAttribute(KETProdChangeRequest.class, KETProdChangeRequest.ECR_ID), five });

	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, ecr_idx, false);
	spec.appendSelect(sqlFunction, new int[] { ecr_idx }, false);
	spec.setDistinct(true);
	spec.appendWhere(new SearchCondition(KETProdChangeRequest.class, KETProdChangeRequest.ECR_ID, SearchCondition.LIKE, "ECR-"
	        + paramObject.getInputParam() + "%"), new int[] { ecr_idx });
	spec.setAdvancedQueryEnabled(true);
	SearchUtil.setOrderBy(spec, KETProdChangeRequest.class, ecr_idx, KETProdChangeRequest.ECR_ID, true);

	// 금형 ECR
	QuerySpec spec1 = new QuerySpec();
	int ecr_idx1 = spec1.appendClassList(KETMoldChangeRequest.class, false);

	ColumnExpression five1 = ConstantExpression.newExpression(5);
	SQLFunction sqlFunction1 = SQLFunction.newSQLFunction(SQLFunction.SUB_STRING, new ColumnExpression[] {
	        new ClassAttribute(KETMoldChangeRequest.class, KETMoldChangeRequest.ECR_ID), five1 });

	spec1.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, ecr_idx1, false);
	spec1.appendSelect(sqlFunction1, new int[] { ecr_idx1 }, false);
	spec1.setDistinct(true);
	spec1.appendWhere(new SearchCondition(KETMoldChangeRequest.class, KETMoldChangeRequest.ECR_ID, SearchCondition.LIKE, "ECR-"
	        + paramObject.getInputParam() + "%"), new int[] { ecr_idx1 });
	spec1.setAdvancedQueryEnabled(true);
	SearchUtil.setOrderBy(spec1, KETMoldChangeRequest.class, ecr_idx1, KETMoldChangeRequest.ECR_ID, true);

	CompoundQuerySpec query = new CompoundQuerySpec();
	query.setSetOperator(SetOperator.UNION_ALL);
	query.addComponent(spec);
	query.addComponent(spec1);

	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	if (!spec1.isAdvancedQueryEnabled())
	    spec1.setAdvancedQueryEnabled(true);

	Kogger.debug(getClass(), spec);
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	// QueryResult result = PersistenceServerHelper.manager.query(spec);
	QueryResult result = PersistenceServerHelper.manager.query(query);
	if (result != null) {
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", String.valueOf(objects[0]));
		map.put("label", String.valueOf(objects[1]));
		list.add(map);
	    }
	}
	return list;
    }

    /**
     * 최종 사용처
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getCustomerEventQuerySpec
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, String>> findByCustomerEvent(KETSuggestDTO paramObject) throws Exception {
	List<Map<String, String>> codeList = new ArrayList<Map<String, String>>();
	// CachManager에 등록된 코드 cach 정보에서 비교하여 데이터를 추출 한다.
	for (NumberCodeDTO numberCodeDTO : (List<NumberCodeDTO>) CacheManager.getItem("CUSTOMEREVENT")) {
	    Map<String, String> returnMap = new HashMap<String, String>();
	    if (numberCodeDTO.getValueKO().startsWith(paramObject.getInputParam())) {
		returnMap.put("value", numberCodeDTO.getOid());
		returnMap.put("label", numberCodeDTO.getValueKO());
		codeList.add(returnMap);
	    }
	}
	return codeList;
    }

    /**
     * 시리즈 Suggest : 부품 속성
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getSpSeriesQuerySpec
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private List<Map<String, String>> getSpSeriesQuerySpec(KETSuggestDTO paramObject) throws Exception {

	@SuppressWarnings("unchecked")
	ArrayList<NumberCodeDTO> numberCodeDTOs = (ArrayList<NumberCodeDTO>) CacheManager.getItem("SPECSERIES");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	for (NumberCodeDTO numberCodeDTO : numberCodeDTOs) {
	    String temp = numberCodeDTO.getDisplay();
	    if (temp.toUpperCase().startsWith(paramObject.getInputParam().toUpperCase())) {
		String key = numberCodeDTO.getCode();
		String value = temp;

		Map<String, String> map = new HashMap<String, String>();
		map.put("value", key);
		map.put("label", value);
		list.add(map);
	    }
	}

	return list;
    }

    private List<Map<String, String>> getKetTagQuerySpec(KETSuggestDTO paramObject) throws Exception {

	@SuppressWarnings("unchecked")
	ArrayList<NumberCodeDTO> numberCodeDTOs = (ArrayList<NumberCodeDTO>) CacheManager.getItem("KETTAG");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	for (NumberCodeDTO numberCodeDTO : numberCodeDTOs) {
	    String temp = numberCodeDTO.getDisplay();
	    if (temp.toUpperCase().startsWith(paramObject.getInputParam().toUpperCase())) {
		String key = numberCodeDTO.getCode();
		String value = temp;

		Map<String, String> map = new HashMap<String, String>();
		map.put("value", key);
		map.put("label", value);
		list.add(map);
	    }
	}

	return list;
    }

    @Override
    public PageControl findPaging(KETSuggestDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public Map<String, String> save(KETSuggestDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public Map<String, String> modify(KETSuggestDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public Map<String, String> delete(KETSuggestDTO paramObject) throws Exception {
	return null;
    }

}
