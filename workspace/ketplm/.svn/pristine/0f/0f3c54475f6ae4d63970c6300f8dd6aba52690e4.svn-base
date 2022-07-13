package ext.ket.material.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.material.MoldMaterial;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.PurchasePrice;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.invest.entity.KETInvestDateHistory;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssuePartLink;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.material.entity.KETPartMaterialDTO;
import ext.ket.material.entity.KETPartMaterialLink;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.sales.entity.KETSalesCompanyLink;
import ext.ket.shared.util.SessionUtil;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;

public class StandardMaterialService extends StandardManager implements MaterialService, Serializable {
    private static final long serialVersionUID = 1L;

    public static StandardMaterialService newStandardMaterialService() throws WTException {
	StandardMaterialService instance = new StandardMaterialService();
	instance.initialize();
	return instance;
    }

    @Override
    public PageControl findPagingList(KETPartMaterialDTO mater) throws Exception {
	// TODO Auto-generated method stub

	QuerySpec query = getMaterialQuery(mater);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(mater.getFormPage(), mater.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(mater.getFormPage(), mater.getFormPage(), mater.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    @Override
    public KETPartMaterialDTO getMaterialDtoByOid(KETPartMaterialDTO mater) throws Exception {

	QueryResult qr = PersistenceHelper.manager.find(this.getMaterialQuery(mater));
	MoldMaterial material = null;
	KETPartMaterialDTO materDTO = null;
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    material = (MoldMaterial) o[0];

	    materDTO = new KETPartMaterialDTO(material);
	    String createDate = String.valueOf(o[1]);
	    String modifyDate = String.valueOf(o[2]);

	    materDTO.setCreateDate(createDate);
	    materDTO.setModifyDate(modifyDate);
	}

	return materDTO;
    }

    public QuerySpec getMaterialQuery(KETPartMaterialDTO mater) throws Exception {
	QuerySpec qs = new QuerySpec();

	int index1 = qs.addClassList(MoldMaterial.class, true);

	qs.setAdvancedQueryEnabled(true);

	ClassAttribute createStampA2 = new ClassAttribute(KETInvestDateHistory.class, "thePersistInfo.createStamp");

	SQLFunction createStamp = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, createStampA2,
	        ConstantExpression.newExpression("YYYY-MM-DD"));
	createStamp.setColumnAlias("createDate");
	qs.appendSelect(createStamp, false);

	ClassAttribute modifyStampA2 = new ClassAttribute(KETInvestDateHistory.class, "thePersistInfo.modifyStamp");

	SQLFunction modifyStamp = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, modifyStampA2,
	        ConstantExpression.newExpression("YYYY-MM-DD"));
	createStamp.setColumnAlias("modifyDate");
	qs.appendSelect(modifyStamp, false);

	String gubun = mater.getGubun();
	
	if (StringUtils.isNotEmpty(mater.getOid())) {
	    MoldMaterial material = (MoldMaterial)CommonUtil.getObject(mater.getOid());
	    
	    if(material != null){
		gubun = material.getMaterial();
	    }
	}
	
	if (StringUtils.isNotEmpty(gubun)) {

	    qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", gubun), new int[] { index1 });

	    String spMaterMaker = mater.getSpMaterMaker();

	    if ("수지".equals(gubun)) {
		spMaterMaker = mater.getSpMaterMaker2();
	    }

	    NumberCode code = null;

	    if (StringUtils.isNotEmpty(spMaterMaker)) {
		
		String arr[] = spMaterMaker.split(",");
		
		long arrLongOid[] = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
		    
		    code = NumberCodeHelper.manager.getNumberCode("MATERIALMAKER", arr[i]);
		    long id = CommonUtil.getOIDLongValue(code);
		    
		    arrLongOid[i] = id;
		}
		
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", arrLongOid, false), new int[] { index1 });
		}
	    }

	    String spMaterType = mater.getSpMaterType();

	    if (StringUtils.isNotEmpty(spMaterType)) {
		
		
		String arr[] = spMaterType.split(",");
		
		long arrLongOid[] = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
		    
		    code = NumberCodeHelper.manager.getNumberCode("MATERIALTYPE", arr[i]);
		    long id = CommonUtil.getOIDLongValue(code);
		    
		    arrLongOid[i] = id;
		}
		
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id", arrLongOid, false), new int[] { index1 });
		}
	    }

	    String materInfo = mater.getSpMaterNotFe();

	    if ("수지".equals(gubun)) {
		materInfo = mater.getSpMaterialInfo();
	    }

	    String oid = mater.getOid();

	    if (StringUtils.isNotEmpty(oid)) {
		materInfo = CommonUtil.getOIDLongValue2Str(oid);
	    }

	    if (StringUtils.isNotEmpty(materInfo)) {
		
		String arr[] = materInfo.split(",");
		long arrLongOid[] = new long[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
		    
		    arrLongOid[i] = Long.parseLong(arr[i]);
		}
		
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(MoldMaterial.class, WTAttributeNameIfc.ID_NAME, arrLongOid, false), new int[] { index1 });
		}

	    }

	}
	
	// 소팅로직
	if (!StringUtil.isTrimToEmpty(mater.getSortName())) {

	    String checkName = mater.getSortName();
	    Class sortClass = MoldMaterial.class;
	    int sortIdx = index1;
	    String sortName = "";

	    String tempCheckName = checkName;

	    if (!checkName.startsWith("-")) {
		tempCheckName = "-" + checkName;
	    }

	    switch (tempCheckName.substring(1)) {
	    case "spec_no":
		sortName = MoldMaterial.SPEC_NO;
		break;
	    case "type":
		sortName = MoldMaterial.MATERIAL_TYPE_REFERENCE+ "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    case "maker":
		sortName = MoldMaterial.MATERIAL_MAKER_REFERENCE+ "." + WTAttributeNameIfc.REF_OBJECT_ID;
		break;
	    case "grade":
		sortName = MoldMaterial.GRADE;
		break;
	    case "melt_point":
		sortName = MoldMaterial.MELT_POINT;
		break;
	    case "m_index":
		sortName = MoldMaterial.M_INDEX;
		break;
	    case "s_gravity":
		sortName = MoldMaterial.S_GRAVITY;
		break;
	    case "m_shrinkage":
		sortName = MoldMaterial.M_SHRINKAGE;
		break;
	    case "absorb":
		sortName = MoldMaterial.ABSORB;
		break;
	    case "t_strength":
		sortName = MoldMaterial.T_STRENGTH;
		break;
	    case "elong":
		sortName = MoldMaterial.ELONG;
		break;
	    case "f_stringth":
		sortName = MoldMaterial.F_STRINGTH;
		break;
	    case "f_modulus":
		sortName = MoldMaterial.F_MODULUS;
		break;
	    case "i_strength":
		sortName = MoldMaterial.I_STRENGTH;
		break;
	    case "h_dis_temp_18":
		sortName = MoldMaterial.H_DIS_TEMP_18;
		break;
	    case "h_dis_temp_4":
		sortName = MoldMaterial.H_DIS_TEMP_4;
		break;
	    case "flammability":
		sortName = MoldMaterial.FLAMMABILITY;
		break;
	    case "s_flow":
		sortName = MoldMaterial.S_FLOW;
		break;
	    case "memo":
		sortName = MoldMaterial.MEMO;
		break;
	    case "temper":
		sortName = MoldMaterial.TEMPER;
		break;
	    case "m_elasticity":
		sortName = MoldMaterial.M_ELASTICITY;
		break;
	    case "e_conductivity":
		sortName = MoldMaterial.E_CONDUCTIVITY;
		break;
	    case "hardness":
		sortName = MoldMaterial.HARDNESS;
		break;
	    case "strength":
		sortName = MoldMaterial.STRENGTH;
		break;
	    case "formability":
		sortName = MoldMaterial.FORMABILITY;
		break;
	    case "residual_stress":
		sortName = MoldMaterial.RESIDUAL_STRESS;
		break;
	    case "t_soft":
		sortName = MoldMaterial.T_SOFT;
		break;
	    case "t_melt":
		sortName = MoldMaterial.T_MELT;
		break;
	    case "t_conductivity":
		sortName = MoldMaterial.T_CONDUCTIVITY;
		break;
	    case "r_y":
		sortName = MoldMaterial.R_Y;
		break;
	    case "r_m":
		sortName = MoldMaterial.R_M;
		break;
	    case "r_d":
		sortName = MoldMaterial.R_D;
		break;
	    case "createDate":
		sortName = "thePersistInfo.createStamp";
		break;
	    case "modifyDate":
		sortName = "thePersistInfo.modifyStamp";
		break;
	    case "price":
		sortName = MoldMaterial.PRICE;
		break;

	    default:
		break;
	    }
	    
	    if ("비철".equals(gubun) && "name".equals(tempCheckName.substring(1))) {
		sortName = MoldMaterial.GRADE;
	    }
	    
	    if (checkName.startsWith("-")) {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, true);
	    } else {
		SearchUtil.setOrderBy(qs, sortClass, sortIdx, sortName, false);
	    }
	}else{
	    SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, MoldMaterial.MATERIAL_MAKER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID , false);
	    SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, MoldMaterial.MATERIAL_TYPE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID , false);
	    SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, MoldMaterial.GRADE , false);
	}

	return qs;
    }

    @Override
    public Map<String, Object> saveMaterial(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String jsonDataStr = (String) reqMap.get("jsonData");

	Transaction transaction = new Transaction();

	try {

	    transaction.start();

	    List<Map<String, Object>> jsonData = mapper.readValue(jsonDataStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    if (jsonData != null) {

		for (Map<String, Object> data : jsonData) {

		    String oid = (String) data.get("oid");
		    String price = StringUtils.remove((String) data.get("price"), ",");
		    
		    data.put("price", price);
		    
		    MoldMaterial material = (MoldMaterial) CommonUtil.getObject(oid);

		    if(ObjectUtil.manager.diffWithMapToObject(data, material) != null){
			material = (MoldMaterial) PersistenceHelper.manager.save(material);
		    }
		    
		}

	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return resMap;
    }
    
    public Map<String, String> getPurchasePriceMap() throws Exception{
	
	Map<String, String> priceMap = new HashMap<String, String>();
	
	String infoType = "EXRATE";
	CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);

	List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	
	QuerySpec qs = new QuerySpec();
	qs.addClassList(PurchasePrice.class, true);
	QueryResult qr = PersistenceHelper.manager.find(qs);
	PurchasePrice purchasePrice = null;

	while (qr.hasMoreElements()) {
	    
	    Object[] o = (Object[]) qr.nextElement();
	    purchasePrice = (PurchasePrice) o[0];
	    String price = purchasePrice.getPrice();
	    
	    for(CSInfoItemDTO dto : csInfoItemList){
		if(dto.getValue1CodeName().equals(purchasePrice.getCurrency())){
		    String exp = price + "*" +dto.getValue2();
		    price = StringUtils.removeEnd((String)CostCalculateUtil.manager.eval(exp),".0000");
		}
	    }
	    
	    priceMap.put(purchasePrice.getPartNo(), price);
	}
	
	return priceMap;
    }

    @Override
    public void syncMaterialForPartLink() throws Exception {

	Transaction transaction = new Transaction();

	try {

	    transaction.start();

	    QuerySpec qs = new QuerySpec();
	    qs.addClassList(KETPartMaterialLink.class, true);

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		KETPartMaterialLink link = (KETPartMaterialLink) o[0];
		PersistenceHelper.manager.delete(link);
	    }

	    List<Map<String, Object>> list = this.getWTpartList();
	    Map<String, String> priceMap = getPurchasePriceMap();
	    
	    for (Map<String, Object> map : list) {

		String partOid = (String) map.get("PARTOID");
		WTPart part = (WTPart) CommonUtil.getObject(partOid);
		String materialOid = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterialInfo);

		if (StringUtils.isEmpty(materialOid)) {
		    materialOid = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterNotFe);
		}
		
		String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		
		PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
		partType = ptype.getDesc();

		if (StringUtils.isNotEmpty(materialOid)) {

		    MoldMaterial materailObj = (MoldMaterial) CommonUtil.getObject("e3ps.project.material.MoldMaterial:" + materialOid);

		    if (materailObj != null) {

			KETPartMaterialLink link = KETPartMaterialLink.newKETPartMaterialLink(materailObj, (WTPartMaster) part.getMaster());
			link.setPartName(part.getName());
			link.setPartNo(part.getNumber());
			link.setPartType(partType);
			String price = priceMap.get(link.getPartNo());
			if(StringUtils.isNotEmpty(price)){
			    
			    link.setPrice(priceMap.get(link.getPartNo()));
			    
			}
			
			PersistenceHelper.manager.save(link);
		    }
		}
	    }
	    
	    updatePrice();
	    

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

    }
    
    
    public void updatePrice() throws Exception {

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();
	    sql.append("UPDATE MoldMaterial A SET PRICE = (SELECT MAX(TO_NUMBER(PRICE)) FROM KETPARTMATERIALLINK B WHERE B.IDA3A5 = A.IDA2A2)");

	    stat.executeUpdate(sql.toString());

	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
    }

    public List<Map<String, Object>> getWTpartList() throws Exception {

	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT 'wt.part.WTPart:'||P.IDA2A2 AS PARTOID                             \n");
	    sb.append("   FROM WTPARTMASTER M                                                     \n");
	    sb.append("      , WTPART P                                                           \n");
	    sb.append("      ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO \n");
	    sb.append("           FROM WTPART I, WTPARTMASTER J                                   \n");
	    sb.append("          WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                           \n");
	    sb.append("            AND I.LATESTITERATIONINFO = 1                                  \n");
	    sb.append("            AND I.STATECHECKOUTINFO != 'wrk'                               \n");
	    sb.append("            AND PTC_STR_3TYPEINFOWTPART NOT IN ('D','M')                   \n");
	    sb.append("       GROUP BY J.IDA2A2                                                   \n");
	    sb.append("      ) MAXVERPART                                                         \n");
	    sb.append("  WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                   \n");
	    sb.append("    AND P.LATESTITERATIONINFO = 1                                          \n");
	    sb.append("    AND P.STATECHECKOUTINFO NOT IN ('wrk')                                 \n");
	    sb.append("    AND M.IDA2A2 = MAXVERPART.IDA2A2                                       \n");
	    sb.append("    AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO         \n");
	    sb.append("    AND PTC_STR_3TYPEINFOWTPART IN ('H','F','W','R')                           \n");
	    sb.append("    AND (PTC_STR_49TYPEINFOWTPART IS NOT NULL OR PTC_STR_50TYPEINFOWTPART IS NOT NULL) \n");

	    rs = stat.executeQuery(sb.toString());

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return list;
    }

    @Override
    public List<Map<String, Object>> getMaterialPartMapList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();
	String oid = (String) reqMap.get("oid");

	MoldMaterial material = (MoldMaterial)CommonUtil.getObject(oid);

	QueryResult qr = getPartLinkList(material);

	while (qr.hasMoreElements()) {
	    KETPartMaterialLink link = (KETPartMaterialLink) qr.nextElement();
	    Map<String, Object> partMap = new HashMap<String, Object>();
	    partMap.put("partNo", link.getPartNo() );
	    partMap.put("partName", link.getPartName());
	    partMap.put("partType", link.getPartType());
	    String sortOption = "";
	    
	    switch (link.getPartType()) {
	    case "원자재":
		sortOption = "가";
		break;
	    case "반제품":
		sortOption = "나";
		break;
	    case "제품":
		sortOption = "다";
		break;
	    case "상품":
		sortOption = "라";
		break;
		
	    default:
		break;
	    }
	    partMap.put("sortOption", sortOption);
	    partMap.put("price", link.getPrice());
	    partList.add(partMap);
	}

	Collections.sort(partList, new Comparator<Object>() {

	    @Override
	    public int compare(Object obj1, Object obj2) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;

		int ret = ((String) map1.get("sortOption")).compareTo((String) map2.get("sortOption"));

		return ret;
	    }
	});

	return partList;
    }

    public QueryResult getPartLinkList(MoldMaterial mater) throws Exception {

	return PersistenceHelper.manager.navigate(mater, KETPartMaterialLink.PART_ROLE , KETPartMaterialLink.class, false);
    }
}