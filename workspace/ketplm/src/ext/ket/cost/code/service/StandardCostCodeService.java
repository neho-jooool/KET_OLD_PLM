package ext.ket.cost.code.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.code.sap.ErpBomInterface;
import ext.ket.cost.code.sap.PurchasePriceInterface;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostCurrencyInfo;
import ext.ket.cost.entity.CostEtcInvest;
import ext.ket.cost.entity.CostEtcInvestLink;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostInvestInfo;
import ext.ket.cost.entity.CostLogistics;
import ext.ket.cost.entity.CostMaterialInfo;
import ext.ket.cost.entity.CostMaterialLink;
import ext.ket.cost.entity.CostPacking;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.CostPartTypeCodeMaster;
import ext.ket.cost.entity.CostQuantity;
import ext.ket.cost.entity.CostReduceCode;
import ext.ket.cost.entity.CostReduceLink;
import ext.ket.cost.entity.CostWorkTimeConfig;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.entity.PurchasePrice;
import ext.ket.cost.entity.costAnalisysLink;
import ext.ket.cost.entity.costPackingPartLink;
import ext.ket.cost.entity.costPartQtyLink;
import ext.ket.cost.entity.partTypeCodeLink;
import ext.ket.cost.service.CostCacheManager;
import ext.ket.cost.service.CostHelper;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostCalculatorUtil;
import ext.ket.cost.util.CostTreeUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.util.ComparatorUtil;

/*********************************************************
 * @description
 * @author 황정태
 * @date 2017. 11. 17. 오전 10:52:12
 * @Pakage ext.ket.cost.code.service
 * @class StandardCostCodeService
 *********************************************************/
public class StandardCostCodeService extends StandardManager implements CostCodeService, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(StandardCostCodeService.class);

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 11. 17. 오후 2:42:44
     * @method newStandardCostBomService
     * @return StandardCostBomService
     * @throws WTException
     * </pre>
     */
    public static StandardCostCodeService newStandardCostCodeService() throws WTException {
	StandardCostCodeService instance = new StandardCostCodeService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<Map<String, Object>> getCostPartTypeLinkList() throws Exception {
	// TODO Auto-generated method stub
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append("SELECT A.NAME AS NAME                                                                                         \n");
	    sb.append("     , A.CODE AS CODE                                                                                         \n");
	    sb.append("     , A.CLASSNAMEA2A2 || ':' || A.IDA2A2 OID                                                                 \n");
	    sb.append("     , DECODE( A.IDA3PARENTREFERENCE, 0, '', A.CLASSNAMEA2A2 || ':' || A.IDA3PARENTREFERENCE ) PARENTOID      \n");
	    sb.append("     , B.CLASSNAMEA2A2||':'||B.IDA2A2 AS id                                                                   \n");
	    sb.append("     , (SELECT IDA2A2 FROM NUMBERCODE CODE WHERE CODE.IDA2A2 = B.IDA3B4) mftfactory1                          \n");
	    sb.append("     , (SELECT LISTAGG(NBC.IDA2A2,';') WITHIN GROUP(ORDER BY NBC.IDA2A2)                                      \n");
	    sb.append("          FROM PARTTYPECODELINK PSL, NUMBERCODE NBC                                                           \n");
	    sb.append("         WHERE PSL.IDA3B5 = NBC.IDA2A2                                                                        \n");
	    sb.append("           AND PSL.IDA3A5 = B.IDA2A2) AS mftfactory2                                                          \n");
	    sb.append("     , (SELECT LTRIM (SYS_CONNECT_BY_PATH (TREE.NAME, ' / '), ' / ') AS TREEFULLPATH FROM COSTPARTTYPE TREE   \n");
	    sb.append("         WHERE CONNECT_BY_ISLEAF = 1                                                                          \n");
	    sb.append("           AND TREE.IDA2A2 = B.IDA3A4                                                                         \n");
	    sb.append("         START WITH TREE.IDA3PARENTREFERENCE = 0                                                              \n");
	    sb.append("       CONNECT BY PRIOR TREE.IDA2A2 = TREE.IDA3PARENTREFERENCE) AS TREEFULLPATH                               \n");
	    sb.append("  FROM COSTPARTTYPE A, CostPartTypeCodeMaster B                                                               \n");
	    sb.append(" WHERE A.IDA2A2 = B.IDA3A4                                                                                    \n");
	    sb.append(" ORDER BY B.SORTLOCATION \n");

	    String query = sb.toString();

	    tempList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    CostPartTypeCodeMaster codeMaster = (CostPartTypeCodeMaster) CommonUtil.getObject(rs.getString("ID"));

		    Map<String, Object> costPartTypeVO = new HashMap<String, Object>();
		    try {
			costPartTypeVO = CostUtil.manager.converObjectToMap(codeMaster);

		    } catch (Exception e) {
			e.printStackTrace();
		    }

		    costPartTypeVO.put("id", rs.getString("ID"));
		    costPartTypeVO.put("partTypeName", StringUtil.checkNull(rs.getString("NAME")));
		    costPartTypeVO.put("code", StringUtil.checkNull(rs.getString("CODE")));
		    costPartTypeVO.put("partTypeOid", StringUtil.checkNull(rs.getString("OID")));
		    costPartTypeVO.put("partTypeParentOid", StringUtil.checkNull(rs.getString("PARENTOID")));
		    costPartTypeVO.put("treeFullFath", StringUtil.checkNull(rs.getString("TREEFULLPATH")));
		    costPartTypeVO.put("mftFactory1", StringUtil.checkNull(rs.getString("mftfactory1")));
		    costPartTypeVO.put("mftFactory2", StringUtil.checkNull(rs.getString("mftfactory2")));
		    costPartTypeVO.put("deleteFlag", "0");

		    costPartTypeVO.put("partTypeNameIcon", "/plm/portal/images/icon_5.png");
		    costPartTypeVO.put("partTypeNameIconAlign", "Right");
		    costPartTypeVO.put("partTypeNameOnClickSideIcon", "javascript:openGridPopup(Row);");
		    costPartTypeVO.put("partTypeNameButton", "/plm/portal/images/icon_delete.gif");
		    costPartTypeVO.put("partTypeNameOnClickSideButton", "javasciprt:deletePopupValue(Row);");

		    return costPartTypeVO;
		}
	    });
	} catch (Exception e) {
	    throw e;
	} finally {

	}

	/*
	 * Map<String, Object> dataMap= null;
	 * 
	 * ArrayList<Map<String, Object>> partTypeList = new ArrayList<Map<String, Object>>(); for(Map<String, Object> data : tempList){
	 * ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); list.add(data);
	 * 
	 * dataMap = new HashMap<String, Object>(); dataMap.put("Items", list); partTypeList.add(dataMap); }
	 */

	return tempList;

    }

    public void setSortLocation(List<Map<String, Object>> items, List<String> list) {
	for (Map<String, Object> item : items) {
	    list.add((String) item.get("id"));
	}
    }

    @Override
    public Map<String, Object> saveCostPartTypeLink(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");
	Transaction transaction = new Transaction();
	Map<String, Object> sortTemp = null;
	try {

	    transaction.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");
		List<String> sortList = new ArrayList<String>();
		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");
		    String partTypeOid = (String) data.get("partTypeOid");

		    String mftFactory1 = "e3ps.common.code.NumberCode:" + (String) data.get("mftFactory1");

		    String mftFactory2 = (String) data.get("mftFactory2");

		    NumberCode num = null;

		    CostPartTypeCodeMaster codeMaster = null;

		    CostPartType costPartType = null;

		    partTypeCodeLink costPartTypeLink = null;

		    if (id.indexOf(CostPartTypeCodeMaster.class.getName()) >= 0) {
			codeMaster = (CostPartTypeCodeMaster) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    codeMaster = CostPartTypeCodeMaster.newCostPartTypeCodeMaster();
			}
			// costPartTypeLink = partTypeCodeLink.newpartTypeCodeLink(costPartType, code);
		    }

		    if (!deleted) {

			num = (NumberCode) CommonUtil.getObject(mftFactory1);
			costPartType = (CostPartType) CommonUtil.getObject(partTypeOid);

			codeMaster.setCostpartType(costPartType);
			codeMaster.setManufactureCode(num);
			codeMaster.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, codeMaster);

			String moldFromSign = (String) data.get("moldFromSign");
			codeMaster.setMoldFromSign(transCharacter(moldFromSign));

			String moldToSign = (String) data.get("moldToSign");
			codeMaster.setMoldToSign(transCharacter(moldToSign));

			String tonFromSign = (String) data.get("tonFromSign");
			codeMaster.setTonFromSign(transCharacter(tonFromSign));

			String tonToSign = (String) data.get("tonToSign");
			codeMaster.setTonToSign(transCharacter(tonToSign));

			String quantityMaxFromSign = (String) data.get("quantityMaxFromSign");
			codeMaster.setQuantityMaxFromSign(transCharacter(quantityMaxFromSign));

			String quantityMaxToSign = (String) data.get("quantityMaxToSign");
			codeMaster.setQuantityMaxToSign(transCharacter(quantityMaxToSign));

			codeMaster = (CostPartTypeCodeMaster) PersistenceHelper.manager.save(codeMaster);
		    }

		    sortTemp = new HashMap<String, Object>();
		    String oid = CommonUtil.getOIDString(codeMaster);
		    sortTemp.put(id, oid);

		    this.setSortLocation(items, sortList);

		    String split_data[] = mftFactory2.split(";");

		    if (!deleted) {

			QueryResult qr = PersistenceHelper.manager.navigate(codeMaster, "manufactureCode2", partTypeCodeLink.class, false);

			while (qr.hasMoreElements()) {
			    partTypeCodeLink codeLink = (partTypeCodeLink) qr.nextElement();
			    PersistenceHelper.manager.delete(codeLink);
			}

			for (String mft : split_data) {
			    num = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + mft);
			    costPartTypeLink = partTypeCodeLink.newpartTypeCodeLink(codeMaster, num);
			    costPartTypeLink.setCodeType(num.getCodeType().toString());
			    costPartTypeLink = (partTypeCodeLink) PersistenceHelper.manager.save(costPartTypeLink);
			}
		    } else {

			if (codeMaster != null) {
			    PersistenceHelper.manager.delete(codeMaster);
			}

		    }

		    // costPartType = (CostPartType) PersistenceHelper.manager.save(costPartType);

		}

		// 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		for (int i = 0; i < sortList.size(); i++) {
		    String itemId = sortList.get(i);

		    if (itemId.indexOf(CostPartTypeCodeMaster.class.getName()) < 0) {
			itemId = (String) sortTemp.get(itemId);
		    }

		    CostPartTypeCodeMaster master = (CostPartTypeCodeMaster) CommonUtil.getObject(itemId);
		    if (master != null) {
			master.setSortLocation(i);
			PersistenceHelper.manager.save(master);
		    }

		}

	    }

	    transaction.commit();
	    transaction = null;

	    CostHelper.service.loadCostPartTypeInfo();

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

    public List<Map<String, Object>> getQueryCostFactoryTreeInfoByPartType(String costPartTypeOid) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

	try {
	    // 부품Type 별로 생산지 정보 가져올 때 1레벨 중복을 막기 위해 group by 추가함
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT LVL,PARTTYPEOID,PARTTYPENAME,NUMBERCODENAME, NUMBERCODEOID,NUMBERCODE_PARENT_OID,NUMBERCODEOIDLONG,SORTING  \n");
	    sb.append("   FROM ( \n");
	    sb.append(" SELECT LEVEL AS LVL, CONNECT_BY_ISLEAF AS LEAF, PARTTYPEOID, PARTTYPENAME, NUMBERCODENAME, NUMBERCODEOID, NUMBERCODEOIDLONG, OID, PARENTOID, (SELECT IDA2A2 FROM NUMBERCODE WHERE IDA2A2 = N_OID) AS NUMBERCODE_PARENT_OID,SORTING  \n");
	    sb.append("   FROM                                                                                                             \n");
	    sb.append(" (                                                                                                                  \n");
	    sb.append("  SELECT A.IDA3A4 AS PARTTYPEOID,                                              \n");
	    sb.append("        (SELECT NAME FROM COSTPARTTYPE WHERE IDA2A2 = A.IDA3A4) AS PARTTYPENAME,                                    \n");
	    sb.append("        (SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = B.IDA3B5) AS  NUMBERCODENAME,                                   \n");
	    sb.append("        (SELECT SORTING FROM NUMBERCODE WHERE IDA2A2 = B.IDA3B5) AS  SORTING,                                       \n");
	    sb.append("         'e3ps.common.code.NumberCode:'||B.IDA3B5 AS NUMBERCODEOID,                                                 \n");
	    sb.append("         B.IDA3B5 AS NUMBERCODEOIDLONG,                                                 \n");
	    sb.append("         'ext.ket.cost.entity.CostPartTypeCodeMaster:'||B.IDA2A2 AS OID,                                            \n");
	    sb.append("         'ext.ket.cost.entity.CostPartTypeCodeMaster:'||A.IDA2A2 AS PARENTOID,                                       \n");
	    sb.append("         (SELECT IDA3A3 FROM NUMBERCODE WHERE IDA2A2 = B.IDA3B5)  AS N_OID                                          \n");
	    sb.append("    FROM COSTPARTTYPECODEMASTER A , PARTTYPECODELINK B                                                              \n");
	    sb.append("   WHERE 1=1                                                                                                        \n");
	    sb.append("     AND IDA3A4 in (" + costPartTypeOid
		    + ")                                                                                         \n");
	    sb.append("     AND A.IDA2A2 = B.IDA3A5                                                                                        \n");
	    sb.append("     UNION ALL                                                                                                      \n");
	    sb.append("  SELECT DISTINCT                                                                                                   \n");
	    sb.append("         A.IDA3A4 AS PARTTYPEOID,                                              \n");
	    sb.append("         (SELECT NAME FROM COSTPARTTYPE WHERE IDA2A2 = A.IDA3A4) AS PARTTYPENAME,                                   \n");
	    sb.append("         (SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = A.IDA3B4) AS  NUMBERCODENAME,                                  \n");
	    sb.append("        (SELECT SORTING FROM NUMBERCODE WHERE IDA2A2 = A.IDA3B4) AS  SORTING,                                       \n");
	    sb.append("         'e3ps.common.code.NumberCode:'||A.IDA3B4 AS NUMBERCODEOID,                                                 \n");
	    sb.append("         A.IDA3B4 AS NUMBERCODEOIDLONG,                                                 \n");
	    sb.append("         'ext.ket.cost.entity.CostPartTypeCodeMaster:'||A.IDA2A2 AS OID ,                                           \n");
	    sb.append("         '0' AS PARENTOID,                                                                                           \n");
	    sb.append("         (SELECT IDA3A3 FROM NUMBERCODE WHERE IDA2A2 = A.IDA3B4)  AS N_OID                                          \n");
	    sb.append("    FROM COSTPARTTYPECODEMASTER A                                                                                   \n");
	    sb.append("   WHERE 1=1                                                                                                        \n");
	    sb.append("     AND IDA3A4 in (" + costPartTypeOid
		    + ")                                                                                         \n");
	    sb.append(" )                                                                                                                  \n");
	    sb.append("   START WITH PARENTOID = '0'                                                                                       \n");
	    sb.append("   CONNECT BY PRIOR OID = PARENTOID ORDER SIBLINGS BY PARENTOID )                                                   \n");
	    sb.append("   GROUP BY PARTTYPEOID,PARTTYPENAME,NUMBERCODENAME,NUMBERCODEOID,NUMBERCODE_PARENT_OID,NUMBERCODEOIDLONG,LVL,SORTING ORDER BY SORTING    \n");

	    String query = sb.toString();

	    tempList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> costPartTypeVO = new HashMap<String, Object>();

		    costPartTypeVO.put("level", rs.getString("LVL"));
		    // costPartTypeVO.put("leaf", StringUtil.checkNull(rs.getString("LEAF")));
		    costPartTypeVO.put("partTypeOid", StringUtil.checkNull(rs.getString("PARTTYPEOID")));
		    costPartTypeVO.put("partTypeName", StringUtil.checkNull(rs.getString("PARTTYPENAME")));
		    costPartTypeVO.put("numberCodeName", StringUtil.checkNull(rs.getString("NUMBERCODENAME")));
		    costPartTypeVO.put("numberCodeOid", StringUtil.checkNull(rs.getString("NUMBERCODEOID")));
		    // costPartTypeVO.put("oid", StringUtil.checkNull(rs.getString("OID")));
		    // costPartTypeVO.put("parentOid", StringUtil.checkNull(rs.getString("PARENTOID")));
		    costPartTypeVO.put("numberOidLong", StringUtil.checkNull(rs.getString("NUMBERCODEOIDLONG")));
		    costPartTypeVO.put("numberParentOid", StringUtil.checkNull(rs.getString("NUMBERCODE_PARENT_OID")));

		    return costPartTypeVO;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return tempList;
    }

    @Override
    public List<Map<String, Object>> getCostFactoryTreeInfoByPartType(String costPartTypeOid) throws Exception {

	return getQueryCostFactoryTreeInfoByPartType(costPartTypeOid);
    }

    @SuppressWarnings("static-access")
    @Override
    public void purchasePriceSapInterface() throws Exception {
	// TODO Auto-generated method stub
	Transaction transaction = new Transaction();
	try {
	    transaction.start();

	    QuerySpec qs = new QuerySpec();
	    qs.addClassList(PurchasePrice.class, true);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    PurchasePrice purchasePrice = null;

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		purchasePrice = (PurchasePrice) o[0];
		PersistenceHelper.manager.delete(purchasePrice);
	    }

	    @SuppressWarnings({ "unchecked", "unused" })
	    ArrayList<HashMap<String, Object>> purchaseList = PurchasePriceInterface.manager.getpurchaseInfo("");

	    for (HashMap<String, Object> map : purchaseList) {
		purchasePrice = PurchasePrice.newPurchasePrice();
		purchasePrice.setOwner(SessionHelper.manager.getPrincipalReference());
		CostUtil.manager.convertMapToObject(map, purchasePrice);
		PersistenceHelper.manager.save(purchasePrice);
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

    }

    @Override
    public List<Map<String, Object>> getCostGridData(Map<String, Object> reqMap) throws Exception {

	String dataType = (String) reqMap.get("DATATYPE");

	List<Map<String, Object>> rootList = new ArrayList<Map<String, Object>>();

	QueryResult qr = null;

	if ("COSTREDUCE".equals(dataType)) {
	    qr = getCostReduceQuery(reqMap);
	} else if ("COSTLogitics".equals(dataType)) {
	    qr = getCostLogiticsQuery(reqMap);
	} else if ("COSTPacking".equals(dataType)) {
	    qr = getCostPackingQuery(reqMap);
	} else if ("COSTMaterial".equals(dataType)) {
	    qr = getCostMaterialQuery(reqMap);
	} else if ("COSTEtcInvest".equals(dataType)) {
	    qr = getCostEtcInvestQuery(reqMap);
	} else if ("COSTQty".equals(dataType)) {
	    qr = getCostQtyQuery(reqMap);
	}

	if (qr != null) {

	    while (qr.hasMoreElements()) {
		Persistable obj = null;
		if ("COSTQty".equals(dataType)) {
		    Object[] o = (Object[]) qr.nextElement();
		    obj = (Persistable) o[0];
		} else {
		    obj = (Persistable) qr.nextElement();
		}

		Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(obj);
		dataMap.put("id", CommonUtil.getOIDString(obj));
		dataMap.put("deleteFlag", "0");

		Set<String> st = dataMap.keySet();
		Iterator<String> it = st.iterator();
		while (it.hasNext()) {
		    String key = (String) it.next();
		    String value = (String) dataMap.get(key);
		    if (StringUtils.contains(value, "NumberCode") && StringUtils.contains(key, "Reference")) {// NumberCode를 long값으로 변경
			key = StringUtils.removeEnd(key, "Reference");
			dataMap.put(key, CommonUtil.getOIDLongValue2Str(value));
		    }
		}

		if ("COSTMaterial".equals(dataType)) {

		    WTPart part = PartBaseHelper.service.getLatestPart((String) dataMap.get("partNo"));

		    dataMap.put("partNoIcon", "/plm/portal/images/icon_5.png");
		    dataMap.put("partNoIconAlign", "Right");
		    dataMap.put("partNoOnClickSideIcon", "javascript:openGridPopup(Row);");
		    dataMap.put("partNoButton", "/plm/portal/images/icon_delete.gif");
		    dataMap.put("partNoOnClickSideButton", "javasciprt:deletePopupValue(Row);");

		    if (part == null) {
			dataMap.put("partNameCanEdit", "1");
			dataMap.put("materialPriceCanEdit", "1");
			dataMap.put("partNoSuggestType", "1");
			dataMap.put("partNoSuggestMin", "3");
		    }

		} else if ("COSTQty".equals(dataType)) {
		    dataMap.put("sortNo", dataMap.get("year"));
		}

		rootList.add(dataMap);
	    }

	    if ("COSTQty".equals(dataType)) {
		Collections.sort(rootList, ComparatorUtil.SALESMANGESORT);
	    }

	}
	return rootList;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostReduceQuery(Map<String, Object> reqMap) throws Exception {

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostReduceCode.class);

	SearchUtil.setOrderBy(qs, CostReduceCode.class, 0, CostReduceCode.SORTING_ORDER1, false);
	// SearchUtil.setOrderBy(qs, CostReduceCode.class, 0, CostReduceCode.SORTING_ORDER2 , false);

	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostLogiticsQuery(Map<String, Object> reqMap) throws Exception {

	String searchType = (String) reqMap.get("SEARCHTYPE");

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostLogistics.class);

	qs.appendWhere(new SearchCondition(CostLogistics.class, "datatype", SearchCondition.EQUAL, searchType), new int[] { 0 });

	SearchUtil.setOrderBy(qs, CostLogistics.class, 0, CostLogistics.SORTING_ORDER1, false);
	// SearchUtil.setOrderBy(qs, CostLogistics.class, 0, CostLogistics.SORTING_ORDER2, false);
	// SearchUtil.setOrderBy(qs, CostLogistics.class, 0, CostLogistics.SORTING_ORDER3, false);

	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostPackingQuery(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");

	CostPart part = (CostPart) CommonUtil.getObject(oid);

	QueryResult qr = PersistenceHelper.manager.navigate(part, "costPacking", costPackingPartLink.class, true);

	return qr;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostMaterialQuery(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");

	CostPart part = (CostPart) CommonUtil.getObject(oid);

	QueryResult qr = PersistenceHelper.manager.navigate(part, "material", CostMaterialLink.class, true);

	return qr;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostEtcInvestQuery(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");

	CostPart part = (CostPart) CommonUtil.getObject(oid);

	QueryResult qr = PersistenceHelper.manager.navigate(part, "etcInvest", CostEtcInvestLink.class, true);

	return qr;
    }

    @SuppressWarnings("deprecation")
    private QueryResult getCostQtyQuery(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	int version = Integer.parseInt(StringUtil.checkNullZero((String) reqMap.get("version")));

	QueryResult qr = null;

	QuerySpec qs = new QuerySpec();

	if (StringUtil.checkString(oid)) {
	    int idx = qs.appendClassList(CostQuantity.class, true);
	    int idx2 = qs.appendClassList(CostPart.class, false);
	    qs.setAdvancedQueryEnabled(true);

	    ClassAttribute ca0 = new ClassAttribute(CostQuantity.class, CostQuantity.COST_PART_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    ClassAttribute ca1 = new ClassAttribute(CostPart.class, WTAttributeNameIfc.ID_NAME);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx2 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(CostPart.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(oid)),
		    new int[] { idx2 });

	    if (version >= 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(CostQuantity.class, CostQuantity.VERSION, SearchCondition.EQUAL, version),
		        new int[] { idx });
	    } else {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(
		        new SearchCondition(CostQuantity.class, CostQuantity.VERSION, SearchCondition.EQUAL, Integer
		                .parseInt(QtyMaxVersion(oid))), new int[] { idx });
	    }
	}
	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    @Override
    public Map<String, Object> saveCostReduceCode(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");
	Transaction transaction = new Transaction();
	try {

	    transaction.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		for (Map<String, Object> data : changes) {

		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    CostReduceCode reduce = null;

		    if (id.indexOf(CostReduceCode.class.getName()) >= 0) {
			reduce = (CostReduceCode) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    reduce = CostReduceCode.newCostReduceCode();
			}
		    }

		    if (!deleted) {
			String factory = "e3ps.common.code.NumberCode:" + (String) data.get("factory");
			String costMaking = "e3ps.common.code.NumberCode:" + (String) data.get("costMaking");
			NumberCode num = (NumberCode) CommonUtil.getObject(costMaking);

			reduce.setCostMaking(num);
			reduce.setSortingOrder1(Integer.parseInt(num.getSorting()));

			num = (NumberCode) CommonUtil.getObject(factory);
			reduce.setSortingOrder2(Integer.parseInt(num.getSorting()));
			reduce.setFactory(num);

			reduce.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, reduce);
			reduce = (CostReduceCode) PersistenceHelper.manager.save(reduce);
		    }

		    if (reduce != null && deleted) {
			PersistenceHelper.manager.delete(reduce);
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

    @Override
    public Map<String, Object> saveCostLogiticsCode(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");
	String dataType = (String) reqMap.get("DATATYPE");
	Map<String, Object> sortTemp = null;
	Transaction transaction = new Transaction();
	try {

	    transaction.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");
		List<String> sortList = new ArrayList<String>();

		for (Map<String, Object> data : changes) {

		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    CostLogistics logitics = null;

		    if (id.indexOf(CostLogistics.class.getName()) >= 0) {
			logitics = (CostLogistics) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    logitics = CostLogistics.newCostLogistics();
			}
		    }

		    if (!deleted) {
			// String factory1 = "e3ps.common.code.NumberCode:" + (String) data.get("factory1");
			// String factory2 = "e3ps.common.code.NumberCode:" + (String) data.get("factory2");
			// String factory3 = "e3ps.common.code.NumberCode:" + (String) data.get("factory3");
			// NumberCode num = (NumberCode) CommonUtil.getObject(factory1);
			//
			// logitics.setFactory1(num);
			// logitics.setSortingOrder1(Integer.parseInt(num.getSorting()));
			//
			// num = (NumberCode) CommonUtil.getObject(factory2);
			// logitics.setFactory2(num);
			// logitics.setSortingOrder2(Integer.parseInt(num.getSorting()));
			//
			// num = (NumberCode) CommonUtil.getObject(factory3);
			// if (num != null) {
			// logitics.setFactory3(num);
			// logitics.setSortingOrder3(Integer.parseInt(num.getSorting()));
			// }

			logitics.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, logitics);
			logitics.setDatatype(dataType);
			logitics = (CostLogistics) PersistenceHelper.manager.save(logitics);
		    }

		    sortTemp = new HashMap<String, Object>();
		    String oid = CommonUtil.getOIDString(logitics);
		    sortTemp.put(id, oid);

		    this.setSortLocation(items, sortList);

		    if (logitics != null && deleted) {
			PersistenceHelper.manager.delete(logitics);
		    }
		}

		// 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		for (int i = 0; i < sortList.size(); i++) {
		    String itemId = sortList.get(i);

		    if (itemId.indexOf(CostLogistics.class.getName()) < 0) {
			itemId = (String) sortTemp.get(itemId);
		    }

		    CostLogistics logitics = (CostLogistics) CommonUtil.getObject(itemId);
		    if (logitics != null) {
			logitics.setSortingOrder1(i);
			PersistenceHelper.manager.save(logitics);
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

    @Override
    public Map<String, Object> saveCostPacking(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");

	String formDataStr = (String) reqMap.get("formdata");
	Transaction transaction = new Transaction();
	// ProductMaster master = null;
	CostPart part = null;
	try {

	    transaction.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    // master = (ProductMaster)CommonUtil.getObject((String)formData.get("productOid"));
	    part = (CostPart) CommonUtil.getObject((String) formData.get("partOid"));
	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		List<NumberCode> list = NumberCodeHelper.manager.getNumberCodeList("MONETARYUNIT");

		for (Map<String, Object> data : changes) {

		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    CostPacking packing = null;

		    if (id.indexOf(CostPacking.class.getName()) >= 0) {
			packing = (CostPacking) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    packing = CostPacking.newCostPacking();
			}
		    }

		    if (!deleted) {

			// if(master != null){
			// packing.setPackingMaster(master);
			// }

			packing.setCostPart(part);

			String packingCode = "e3ps.common.code.NumberCode:" + (String) data.get("packingCode");
			String currencyCode = (String) data.get("currencyNumberCode");

			NumberCode num = (NumberCode) CommonUtil.getObject(packingCode);

			packing.setPackingCode(num);

			for (NumberCode n : list) {
			    if (n.getName().equals(currencyCode)) {
				num = n;
				break;
			    }
			}

			packing.setCurrencyNumberCode(num);

			packing.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, packing);

			packing = (CostPacking) PersistenceHelper.manager.save(packing);
		    }

		    if (packing != null && deleted) {
			PersistenceHelper.manager.delete(packing);
		    }

		}

	    }

	    // if(master != null){
	    // CostUtil.manager.convertMapToObject(formData, master);//포장수량(Box), 포장수량(Pallet), 개별포장비 합계 저장
	    // }
	    CostUtil.manager.convertMapToObject(formData, part);// 포장수량(Box), 포장수량(Pallet), 개별포장비 합계 저장

	    PersistenceHelper.manager.save(part);

	    // PersistenceHelper.manager.save(master);

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

    public ProductMaster getMyProductMaster(CostPart part) {
	CostPart tempPart = part;
	ProductMaster master = null;
	try {

	    while (tempPart != null) {
		master = tempPart.getMaster();
		tempPart = (CostPart) tempPart.getParent();
	    }
	} catch (Exception e) {
	    System.out.println("master를 찾을 수 없습니다");
	}

	return master;
    }

    public PartList getMyPartList(CostPart part) {
	CostPart tempPart = part;
	ProductMaster master = null;

	while (tempPart != null) {
	    master = tempPart.getMaster();
	    tempPart = (CostPart) tempPart.getParent();
	}
	PartList partlist = master.getPartList();
	return partlist;
    }

    public CostPart getRootPart(CostPart part) {
	CostPart tempPart = part;

	CostPart parent = null;

	while (tempPart != null) {
	    parent = tempPart;
	    tempPart = (CostPart) tempPart.getParent();
	}

	return parent;
    }

    @Override
    @Transactional
    public Map<String, Object> saveCostMaterial(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");

	String formDataStr = (String) reqMap.get("formdata");

	CostPart part = null;

	try {

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    part = (CostPart) CommonUtil.getObject((String) formData.get("partOid"));

	    PartList partList = getMyPartList(part);
	    String partListOid = (String) CommonUtil.getOIDString(partList);
	    String version = Integer.toString(part.getVersion());

	    String scrapCostExpr = "";
	    String scrapRecycleExpr = "";
	    String sapPriceExpr = "";

	    String scrapCost = "0";
	    String scrapRecycle = "0";
	    String sapPrice = "0";

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		for (Map<String, Object> data : changes) {

		    CostMaterialInfo material = null;
		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    String oldPartNo = "";

		    if (id.indexOf(CostMaterialInfo.class.getName()) >= 0) {
			material = (CostMaterialInfo) CommonUtil.getObject(id);
			oldPartNo = material.getPartNo();
		    } else {
			if (!deleted) {
			    material = CostMaterialInfo.newCostMaterialInfo();
			}
		    }

		    CostMaterialLink link = null;

		    if (!deleted) {

			material.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, material);

			if (!material.getPartNo().equals(oldPartNo) || id.indexOf(CostMaterialInfo.class.getName()) < 0) {// 최초 등록시에만
				                                                                                          // SAP으로부터 단가정보를
				                                                                                          // 끌어오고, 이후는 사용자가
				                                                                                          // 입력한 값으로 저장한다.
				                                                                                          // 이경무
				                                                                                          // 과장 요청
			    String rate = material.getRate();

			    List<Map<String, Object>> materialList = this.getMaterialInfoBySap(material.getPartNo(), part, rate);// 수지원재료
			    // 단가세팅

			    if (materialList.size() > 0) {
				Map<String, Object> materialMap = (Map<String, Object>) materialList.get(0);
				sapPrice = StringUtil.checkNullZero((String) materialMap.get("costprice"));

				material.setMaterialPrice(sapPrice);
				material.setErpCheck(true);

			    } else {
				material.setErpCheck(false);
				// material.setMaterialPrice("0");
			    }
			}

			material = (CostMaterialInfo) PersistenceHelper.manager.save(material);

			if (id.indexOf(CostMaterialInfo.class.getName()) < 0 && !deleted) {
			    link = CostMaterialLink.newCostMaterialLink(material, part);
			    link = (CostMaterialLink) PersistenceHelper.manager.save(link);
			}

		    }

		    if (material != null && deleted) {

			QueryResult qr = PersistenceHelper.manager.navigate(material, "costPart", CostMaterialLink.class, false);

			while (qr.hasMoreElements()) {
			    CostMaterialLink codeLink = (CostMaterialLink) qr.nextElement();
			    PersistenceHelper.manager.delete(codeLink);
			}

			PersistenceHelper.manager.delete(material);
		    }

		}

		QueryResult qr = PersistenceHelper.manager.navigate(part, "material", CostMaterialLink.class, true);

		// int check = 0;

		String partNo = "";
		String partName = "";
		String grade = "";
		String color = "";

		BigDecimal checkVal = new BigDecimal("0.0");
		BigDecimal lastVal = new BigDecimal("0.0");

		while (qr.hasMoreElements()) {
		    CostMaterialInfo material = (CostMaterialInfo) qr.nextElement();
		    // String rate = material.getRate();
		    //
		    // List<Map<String, Object>> materialList = this.getMaterialInfoBySap(material.getPartNo(), part, rate);// 수지원재료 단가세팅
		    //
		    // if (materialList.size() > 0) {
		    // Map<String, Object> materialMap = (Map<String, Object>) materialList.get(0);
		    // sapPrice = StringUtil.checkNullZero((String) materialMap.get("costprice"));
		    //
		    // material.setMaterialPrice(sapPrice);
		    // material.setErpCheck(true);
		    //
		    // } else {
		    // material.setErpCheck(false);
		    // //material.setMaterialPrice("0");
		    // }

		    // CSInfo csInfo = CostUtil.manager.getLatestCSInfo("PROFIT");
		    //
		    // if (csInfo != null) {// 스크랩 판매비, 재생비 세팅
		    // List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
		    // for (CSInfoItemDTO dto : csInfoItemList) {
		    // if ("RESINS001".equals(dto.getValue1())) {// 스크랩 판매비
		    // scrapCost = dto.getValue2();
		    //
		    // } else if ("RESINS002".equals(dto.getValue1())) {// 스크랩 재생비
		    // scrapRecycle = dto.getValue2();
		    //
		    // }
		    // }
		    //
		    // }
		    // String expression = scrapCost + "*" + rate;
		    //
		    // scrapCost = (String) CostCalculatorUtil.eval(expression);
		    // material.setScrapCost(scrapCost);
		    //
		    // expression = scrapRecycle + "*" + rate;
		    // scrapRecycle = (String) CostCalculatorUtil.eval(expression);
		    // material.setScrapRecycle(scrapRecycle);

		    // material = (CostMaterialInfo) PersistenceHelper.manager.save(material);

		    // scrapCostExpr += material.getScrapCost() + "+";
		    // scrapRecycleExpr += material.getScrapRecycle() + "+";
		    sapPriceExpr += material.getMaterialPrice() + "+";
		    lastVal = new BigDecimal(material.getRate());
		    if (lastVal.compareTo(checkVal) == 1) {
			checkVal = lastVal;
			partNo = material.getPartNo();
			partName = material.getPartName();
			grade = material.getGrade();
			color = material.getColor();
		    }
		}

		part.setSujiPartNo(partNo);
		part.setSujiPartName(partName);
		part.setSujiColor(color);
		part.setSujiGrade(grade);

		// scrapCostExpr = StringUtils.removeEnd(scrapCostExpr, "+");
		// scrapRecycleExpr = StringUtils.removeEnd(scrapRecycleExpr, "+");
		sapPriceExpr = StringUtils.removeEnd(sapPriceExpr, "+");

		// part.setSujiScrapCost((String) CostCalculatorUtil.eval(scrapCostExpr));
		// part.setSujiScrapRecycle((String) CostCalculatorUtil.eval(scrapRecycleExpr));
		part.setMtrSujiPrice((String) CostCalculatorUtil.eval(sapPriceExpr));

		PersistenceHelper.manager.save(part);

	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	}

	return resMap;
    }

    @Override
    public List<Map<String, Object>> getMaterialInfoBySap(String partNo, CostPart part, String rate) throws Exception {
	// TODO Auto-generated method stub
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> materialList = new ArrayList<Map<String, Object>>();

	// partListOid = CommonUtil.getOIDLongValue2Str(partListOid);

	int version = part.getVersion();
	int subVersion = part.getSubVersion();
	// String pjtNo[] = part.getPartNo().split("-");
	String pjtNo = part.getProject().getPjtNo();
	ProductMaster master = getMyProductMaster(part);
	Long masterOid = CommonUtil.getOIDLongValue(master);

	try {
	    // 환율정보는 부품별 생성된 개별 기준정보가 있으면 COSTCURRENCYINFO에서 끌어온다

	    StringBuffer sb = new StringBuffer();

	    sb.append("SELECT PRICE,                                                                                                                                                                              \n");
	    sb.append("       CURRENCY,                                                                                                                                                                           \n");
	    sb.append("       PARTNO,                                                                                                                                                                             \n");
	    sb.append("       PER,                                                                                                                                                                                \n");
	    sb.append("       PURCHASEORG,                                                                                                                                                                        \n");
	    sb.append("       SUPPLIERCODE,                                                                                                                                                                       \n");
	    sb.append("       SUPPLIERNAME,                                                                                                                                                                       \n");
	    sb.append("       UNIT,                                                                                                                                                                                                   \n");
	    sb.append("       VALIDDATE,                                                                                                                                                                          	              \n");
	    sb.append("       PURCHASEORDERDATE,                                                                                                                                                                  		      \n");
	    // sb.append("       DECODE(UNIT,'G',(DECODE(PARTLIST_CURRENCY,'',CURRENCYVALUE,PARTLIST_CURRENCY) * PRICE), ((PRICE/1000) * DECODE(PARTLIST_CURRENCY,'',CURRENCYVALUE,PARTLIST_CURRENCY))) * "+
	    // rate + " AS COSTPRICE  \n");
	    sb.append("       (DECODE(PARTLIST_CURRENCY,'',CURRENCYVALUE,PARTLIST_CURRENCY) * PRICE) * " + rate + " AS COSTPRICE  \n");
	    sb.append("  FROM (                                                                                                                                                                                                       \n");
	    sb.append("SELECT MAX (PRICE) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS PRICE,                                                                                                       \n");
	    sb.append("       MAX (CURRENCY) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS CURRENCY,                                                                                                 \n");
	    sb.append("       MAX (PARTNO) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS PARTNO,                                                                                                     \n");
	    sb.append("       MAX (PER) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS PER,                                                                                                           \n");
	    sb.append("       MAX (PURCHASEORG) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS PURCHASEORG,                                                                                           \n");
	    sb.append("       MAX (SUPPLIERCODE ) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS SUPPLIERCODE,                                                                                        \n");
	    sb.append("       MAX (SUPPLIERNAME ) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS SUPPLIERNAME,                                                                                        \n");
	    sb.append("       MAX (UNIT ) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS UNIT,                                                                                                        \n");
	    sb.append("       MAX (TO_CHAR(VALIDDATE,'YYYYMMDD')) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS VALIDDATE,                                                                           \n");
	    sb.append("       MAX (TO_CHAR(PURCHASEORDERDATE,'YYYYMMDD')) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS PURCHASEORDERDATE,                                                           \n");
	    sb.append("       MAX((SELECT VALUE2 FROM CSINFOITEM A, NUMBERCODE B                                                                                                                               \n");
	    sb.append("             WHERE A.ITEMTYPE = 'EXRATECOST'                                                                                                                                     \n");
	    sb.append("               AND B.CODETYPE = 'MONETARYUNIT'                                                                                                                                   \n");
	    sb.append("               AND VALUE1 = B.CODE                                                                                                                                               \n");
	    sb.append("               AND B.NAME = (SELECT MAX (CURRENCY) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS CURRENCY  FROM PurchasePrice WHERE PARTNO = '"
		    + partNo
		    + "' GROUP BY PARTNO)                                                                                                                                             \n");
	    sb.append("               AND A.IDA3A4 = (SELECT IDA2A2 FROM CSINFO WHERE INFOTYPE = 'EXRATE' AND VERSION = (SELECT MAX(VERSION) FROM CSINFO WHERE INFOTYPE = 'EXRATE')))) AS CURRENCYVALUE, \n");
	    sb.append("       MAX((SELECT VALUE2 FROM COSTCURRENCYINFO WHERE VERSION = "
		    + version
		    + " AND SUBVERSION = "
		    + subVersion
		    + " AND IDA3A4 = "
		    + masterOid
		    + " AND PJTNO = '"
		    + pjtNo
		    + "' AND NAME = (SELECT MAX (CURRENCY) KEEP (DENSE_RANK FIRST ORDER BY PURCHASEORDERDATE DESC) AS CURRENCY  FROM PurchasePrice WHERE PARTNO = '"
		    + partNo + "' GROUP BY PARTNO))) AS PARTLIST_CURRENCY                                   \n");
	    sb.append("  FROM PurchasePrice WHERE 1 = 1                                                                                                                                                           \n");

	    if (StringUtils.isNotEmpty(partNo)) {
		sb.append("    AND PARTNO = '" + partNo + "'   \n");
	    }
	    sb.append("  GROUP BY PARTNO                                                                                                                                             \n");
	    sb.append(")                                                                                                                                                                                          \n");

	    String query = sb.toString();

	    materialList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> materialMap = new HashMap<String, Object>();

		    materialMap.put("price", rs.getString("PRICE"));
		    materialMap.put("currency", StringUtil.checkNull(rs.getString("CURRENCY")));
		    materialMap.put("partno", StringUtil.checkNull(rs.getString("PARTNO")));
		    materialMap.put("per", StringUtil.checkNull(rs.getString("PER")));
		    materialMap.put("purchaseorg", StringUtil.checkNull(rs.getString("PURCHASEORG")));
		    materialMap.put("suppliercode", StringUtil.checkNull(rs.getString("SUPPLIERCODE")));
		    materialMap.put("suppliername", StringUtil.checkNull(rs.getString("SUPPLIERNAME")));
		    materialMap.put("unit", StringUtil.checkNull(rs.getString("UNIT")));
		    materialMap.put("validdate", StringUtil.checkNull(rs.getString("VALIDDATE")));
		    materialMap.put("purchaseorderdate", StringUtil.checkNull(rs.getString("PURCHASEORDERDATE")));
		    materialMap.put("costprice", StringUtil.checkNull(rs.getString("COSTPRICE")));

		    return materialMap;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return materialList;
    }

    private void deleteInvestInfoEtc(CostEtcInvest etc) throws Exception {

	String etcOid = CommonUtil.getOIDString(etc);

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInvestInfo.class);

	qs.appendWhere(new SearchCondition(CostInvestInfo.class, "etcInvestOid", SearchCondition.EQUAL, etcOid), new int[] { 0 });

	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    CostInvestInfo invest = (CostInvestInfo) qr.nextElement();

	    QueryResult qr2 = PersistenceHelper.manager.navigate(invest, "costPart", CostReduceLink.class, false);

	    while (qr2.hasMoreElements()) {

		CostReduceLink link = (CostReduceLink) qr2.nextElement();

		PersistenceHelper.manager.delete(link);

	    }

	    PersistenceHelper.manager.delete(invest);
	}
    }

    @Override
    public Map<String, Object> saveCostEtcInvest(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");

	String formDataStr = (String) reqMap.get("formdata");

	CostPart part = null;

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    part = (CostPart) CommonUtil.getObject((String) formData.get("partOid"));

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		for (Map<String, Object> data : changes) {

		    CostEtcInvest etcInvest = null;
		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    if (id.indexOf(CostEtcInvest.class.getName()) >= 0) {
			etcInvest = (CostEtcInvest) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    etcInvest = CostEtcInvest.newCostEtcInvest();
			}
		    }

		    CostEtcInvestLink link = null;
		    if (!deleted) {

			etcInvest.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, etcInvest);

			etcInvest = (CostEtcInvest) PersistenceHelper.manager.save(etcInvest);

			if (id.indexOf(CostEtcInvest.class.getName()) < 0 && !deleted) {
			    link = CostEtcInvestLink.newCostEtcInvestLink(etcInvest, part);
			    link = (CostEtcInvestLink) PersistenceHelper.manager.save(link);
			}

			CostUtil.manager.convertMapToObject(formData, part);// 지급액 합계, 양산 투자비 합계, 양산 수량 합계 업데이트
			PersistenceHelper.manager.save(part);

		    }

		    if (etcInvest != null && deleted) {

			QueryResult qr = PersistenceHelper.manager.navigate(etcInvest, "costPart", CostEtcInvestLink.class, false);

			while (qr.hasMoreElements()) {
			    CostEtcInvestLink codeLink = (CostEtcInvestLink) qr.nextElement();
			    PersistenceHelper.manager.delete(codeLink);
			}

			deleteInvestInfoEtc(etcInvest);// 기타투자비 관련 감가비 정보 삭제

			PersistenceHelper.manager.delete(etcInvest);
		    }

		}

		QueryResult qr = PersistenceHelper.manager.navigate(part, "etcInvest", CostEtcInvestLink.class, true);

		int nCheck = 0;
		int pCheck = 0;

		String itemName = "";
		String Mfactory = "";
		String Nfactory = "";
		NumberCode num = null;

		while (qr.hasMoreElements()) {
		    CostEtcInvest etc = (CostEtcInvest) qr.nextElement();
		    if (nCheck == 0) {
			itemName = etc.getItemName();
		    }

		    if (nCheck == 0) {
			Nfactory = etc.getEtcNfactory(); // 신규 제작처
			if (StringUtil.checkString(Nfactory)) {
			    num = CodeHelper.manager.getNumberCode("COSTMAKING", Nfactory);
			    Nfactory = num.getName();
			}
		    }

		    if (pCheck == 0) {
			Mfactory = etc.getEtcPfactory(); // 양산 제작처

			if (StringUtil.checkString(Mfactory)) {
			    num = CodeHelper.manager.getNumberCode("COSTMAKING", Mfactory);
			    Mfactory = num.getName();
			}
		    }

		    if (StringUtil.checkString(etc.getEtcPfactory())) {
			pCheck++;
		    }

		    if (StringUtil.checkString(etc.getEtcNfactory())) {
			nCheck++;
		    }

		}

		part.setItemName("");
		part.setEtcNPFactory("");
		part.setEtcMPFactory("");

		if (StringUtils.isNotEmpty(Nfactory)) {
		    if (nCheck > 1) {
			nCheck = nCheck - 1;
			itemName += " 외 " + nCheck + "건 ";
			Nfactory += " 외 " + nCheck + "건 ";
		    }
		    part.setItemName(itemName);
		    part.setEtcNPFactory(Nfactory);
		}

		if (StringUtils.isNotEmpty(Mfactory)) {
		    if (pCheck > 1) {
			pCheck = pCheck - 1;
			if (nCheck < 1) {
			    itemName += " 외 " + pCheck + "건 ";
			}
			Mfactory += " 외 " + pCheck + "건 ";
		    }
		    part.setItemName(itemName);
		    part.setEtcMPFactory(Mfactory);
		}

		PersistenceHelper.manager.save(part);

	    }

	    part = transferInvest(part);// 감가비 이관 및 재계산

	    QueryResult qr = PersistenceHelper.manager.navigate(part, "etcInvest", CostEtcInvestLink.class, true);

	    if (qr.size() < 1) {
		part.setItemName("");
		part.setEtcNPFactory("");
		part.setEtcMPFactory("");
		part.setEtcNIC("0");
		part.setEtcNPay("0");
		part.setEtcMPIC("0");
		part.setEtcMPQMax("0");
		part.setEtcMPQTotal("0");
	    }

	    PersistenceHelper.manager.save(part);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    @Override
    public List<CostQuantity> getLastestCostQuantity(CostPart part) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostQuantity.class, true);
	qs.addClassList(costPartQtyLink.class, true);
	qs.appendWhere(new SearchCondition(CostQuantity.class, CostQuantity.LASTEST, SearchCondition.EQUAL, "1"), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.navigate(part, costPartQtyLink.COST_QUANTITY_ROLE, qs, true);

	List<CostQuantity> targetList = new ArrayList<CostQuantity>();

	while (qr.hasMoreElements()) {
	    CostQuantity qty = (CostQuantity) qr.nextElement();
	    targetList.add(qty);
	}

	Collections.sort(targetList, year);
	return targetList;
    }

    public static final Comparator<Object> year = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    CostQuantity qty1 = (CostQuantity) obj1;
	    CostQuantity qty2 = (CostQuantity) obj2;

	    int x = (int) Integer.parseInt(qty1.getYear());
	    int y = (int) Integer.parseInt(qty2.getYear());

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };

    @Override
    public CostPart udtQtyInfoMySubTree(CostPart part, boolean subAll, boolean isSave) throws Exception {
	// ProductMaster 하위 부품들을 찾아 물량 정보 세팅

	Map<String, Object> formData = new HashMap<String, Object>();

	// formData.put("quantityAvg", part.getQuantityAvg());
	// formData.put("quantityMax", part.getQuantityMax());
	// formData.put("quantityTotal", part.getQuantityTotal());
	formData.put("sopYear", part.getSopYear());
	formData.put("payPlace", part.getPayPlace());

	CostPart rootPart = getRootPart(part);

	QueryResult qr = PersistenceHelper.manager.navigate(rootPart, "costQuantity", costPartQtyLink.class, true);

	while (qr.hasMoreElements()) {

	    CostQuantity qty = (CostQuantity) qr.nextElement();

	    if ("1".equals(qty.getLastest())) {
		formData.put(qty.getYear() + "myQty", qty.getQuantity());
	    }

	}
	formData.put("subAll", subAll);
	formData.put("isSave", isSave);

	part = udtQtyInfo(part, formData);

	return part;
    }

    public void calcQty(CostPart part, Map<String, Object> formData) throws Exception {
	Set<String> st = formData.keySet();
	Iterator<String> it = st.iterator();

	String expr = "";
	int cnt = 0;

	Map<String, Object> dataMap = new HashMap<String, Object>();

	while (it.hasNext()) {
	    String key = (String) it.next();
	    if (key.endsWith("myQty")) {
		String value = (String) formData.get(key);
		expr += "(" + part.getUs() + "*" + value + ")+";
		dataMap.put(cnt + "culcQty", (String) CostCalculatorUtil.eval("(" + part.getUs() + "*" + value + ")"));
		cnt++;
	    }
	}

	BigDecimal maxCheckVal = new BigDecimal("0.0");
	BigDecimal maxLastVal = new BigDecimal("0.0");

	String quantityMax = "";
	String quantityTotal = (String) CostCalculatorUtil.eval(StringUtils.removeEnd(expr, "+"));
	String quantityAvg = "";

	st = dataMap.keySet();
	it = st.iterator();

	int size = 0;

	while (it.hasNext()) {
	    String key = (String) it.next();
	    if (key.endsWith("culcQty")) {
		size++;
		String value = (String) dataMap.get(key);

		maxLastVal = new BigDecimal(value);

		if (maxLastVal.compareTo(maxCheckVal) == 1) {
		    maxCheckVal = maxLastVal;
		    quantityMax = value;
		}
	    }

	}

	expr = quantityTotal + "/" + Integer.toString(size);

	quantityAvg = (String) CostCalculatorUtil.eval(expr);

	formData.put("quantityAvg", quantityAvg);
	formData.put("quantityMax", quantityMax);
	formData.put("quantityTotal", quantityTotal);
    }

    public CostPart udtQtyInfo(CostPart part, Map<String, Object> formData) throws Exception {
	ArrayList<CostPart> targetList = new ArrayList<CostPart>();

	boolean subAll = (boolean) formData.get("subAll");
	boolean isSave = (boolean) formData.get("isSave");

	if (subAll) {
	    DefaultMutableTreeNode node = CostTreeUtil.manager.getCostTree(part);

	    costTreeSetting(node, targetList);
	} else {
	    targetList.add(part);
	}

	for (CostPart target : targetList) {
	    calcQty(target, formData);
	    part = (CostPart) CostUtil.manager.convertMapToObject(formData, target);// 물량 Total, 물량평균, 물량 max

	    if (isSave) {
		part = (CostPart) PersistenceHelper.manager.save(target);
	    }
	}

	part = syncPakingByPart(part, isSave);// 물량정보 변경에 따른 포장비 업데이트

	return part;
    }

    @Override
    @Transactional
    public Map<String, Object> saveCostQty(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");

	String formDataStr = (String) reqMap.get("formdata");
	String version = "";
	CostPart part = null;
	String lastest = "0";

	try {

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    lastest = StringUtil.checkNullZero((String) formData.get("lastest"));

	    part = (CostPart) CommonUtil.getObject((String) formData.get("productOid"));

	    QueryResult qr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);

	    if ("1".equals(lastest)) {
		while (qr.hasMoreElements()) {

		    CostQuantity qty = (CostQuantity) qr.nextElement();

		    qty.setLastest("0");
		    PersistenceHelper.manager.save(qty);

		}
	    }

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		for (Map<String, Object> data : changes) {

		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    if (qr == null || qr.size() < 1) {// 최초생성이면 최종안으로 채택
			data.put("lastest", "1");
		    }

		    CostQuantity qty = null;

		    if (id.indexOf(CostQuantity.class.getName()) >= 0) {
			qty = (CostQuantity) CommonUtil.getObject(id);
			version = String.valueOf(qty.getVersion());

		    } else {
			if (!deleted) {
			    qty = CostQuantity.newCostQuantity();
			    version = (String) formData.get("version");
			}
		    }

		    if (!deleted) {

			qty.setCostPart(part);
			qty.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, qty);
			qty.setVersion(Integer.parseInt(version));
			qty.setLastest(lastest);
			qty = (CostQuantity) PersistenceHelper.manager.save(qty);
		    }

		    if (qty != null && deleted) {
			PersistenceHelper.manager.delete(qty);
		    }

		}

	    }
	    part.setUs((String) formData.get("prodUS"));
	    if ("1".equals(lastest)) {
		CostUtil.manager.convertMapToObject(formData, part);// 물량 Total, 물량평균, 물량 max, 제품u/s, sop년도, 판매목표가
	    }

	    part = (CostPart) PersistenceHelper.manager.save(part);

	    qr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);

	    if (StringUtils.isEmpty(version)) {
		version = (String) formData.get("version");
	    }

	    String tempYears = "";

	    while (qr.hasMoreElements()) {
		CostQuantity qty = (CostQuantity) qr.nextElement();

		if (String.valueOf(qty.getVersion()).equals(version)) {
		    qty.setSalesTargetGb((String) formData.get("salesTargetGb"));// 판매목표가 구분 세팅
		    qty.setSalesTargetCostExpr((String) formData.get("salesTargetCostExpr"));// 판매목표가 세팅
		    qty.setPayPlace((String) formData.get("payPlace"));// 완제품입고
		    qty.setSopYear((String) formData.get("sopYear"));// SOP
		    qty.setDeliverName((String) formData.get("deliverName"));// 납입처
		    qty.setDisposableCr((String) formData.get("disposableCr"));// CR적용율
		    qty.setDisposableApplyYear((String) formData.get("disposableApplyYear"));// CR적용년도
		    PersistenceHelper.manager.save(qty);
		}

		tempYears += qty.getYear() + ";";
	    }

	    if ("1".equals(lastest)) {

		qr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);

		while (qr.hasMoreElements()) {
		    CostQuantity qty = (CostQuantity) qr.nextElement();

		    if (String.valueOf(qty.getVersion()).equals(version)) {
			qty.setLastest("1");
			PersistenceHelper.manager.save(qty);
		    }
		}

		udtQtyInfoMySubTree(part, true, true);
	    }

	    // if ("1".equals(formData.get("lastest"))) {

	    // qr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);
	    //
	    // while (qr.hasMoreElements()) {
	    // CostQuantity qty = (CostQuantity) qr.nextElement();
	    // if (String.valueOf(qty.getVersion()).equals(version)) {
	    // qty.setLastest("1");
	    // PersistenceHelper.manager.save(qty);
	    // }else{
	    // qty.setLastest("0");
	    // PersistenceHelper.manager.save(qty);
	    // }
	    // }

	    // ProductMaster 하위 부품들을 찾아 물량 정보 세팅
	    // udtQtyInfo(part, formData);

	    // }

	    // 예상판매량 년차 sync
	    tempYears = StringUtils.removeEnd(tempYears, ";");

	    String years[] = tempYears.split(";");

	    qr = PersistenceHelper.manager.navigate(part, "costAnalysis", costAnalisysLink.class, true);

	    while (qr.hasMoreElements()) {

		boolean isDelete = true;

		CostAnalysis obj = (CostAnalysis) qr.nextElement();

		for (String year : years) {
		    if (String.valueOf(obj.getYear()).equals(year)) {
			isDelete = false;
			break;
		    }
		}

		if (isDelete) {
		    PersistenceHelper.manager.delete(obj);
		}

	    }

	} catch (Exception e) {
	    throw e;
	}

	return resMap;
    }

    @Override
    @Transactional
    public Map<String, Object> costQtyCaseCreate(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	QueryResult qr = getCostQtyQuery(reqMap);
	String version = this.QtyMaxVersion((String) reqMap.get("oid"));
	version = Integer.toString(Integer.parseInt(version) + 1);

	CostQuantity newQty = null;

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostQuantity qty = (CostQuantity) o[0];

	    newQty = (CostQuantity) HistoryHelper.duplicate(qty);
	    newQty.setLastest("0");
	    newQty.setVersion(Integer.parseInt(version));
	    PersistenceHelper.manager.save(newQty);
	}

	reqMap.put("version", version);

	return reqMap;
    }

    @Override
    public String QtyMaxVersion(String ProductOid) throws Exception {
	QuerySpec query = new QuerySpec();
	query.setAdvancedQueryEnabled(true);

	int idxA = query.appendClassList(CostQuantity.class, false);
	ClassAttribute ver = new ClassAttribute(CostQuantity.class, "version");

	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, ver));
	query.appendWhere(
	        new SearchCondition(CostQuantity.class, CostQuantity.COST_PART_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	                SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ProductOid)), new int[] { idxA });

	query.appendSelect(max, false);

	QueryResult qr = PersistenceServerHelper.manager.query(query);
	String maxVer = "0";
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    if (obj[0] != null) {
		maxVer = String.valueOf(obj[0]);
	    }
	}
	if (StringUtils.isEmpty(maxVer)) {
	    maxVer = "0";
	}

	return maxVer;

    }

    @Override
    @Transactional
    public Map<String, Object> costQtyCaseDelete(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	QueryResult qr = getCostQtyQuery(reqMap);

	CostQuantity newQty = null;

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostQuantity qty = (CostQuantity) o[0];

	    PersistenceHelper.manager.delete(qty);
	}

	String oid = (String) reqMap.get("oid");
	String version = (String) reqMap.get("version");
	// if("0".equals(version)){//모두 삭제되면 master 리셋
	// ProductMaster master = (ProductMaster)CommonUtil.getObject(oid);
	// master.setPayPlace("");
	// master.setProdUS("");
	// master.setSopYear("");
	// master.setQuantityAvg("");
	// master.setQuantityMax("");
	// master.setQuantityTotal("");
	// PersistenceHelper.manager.save(master);
	// }

	return reqMap;
    }

    public boolean checkStd(String std) throws Exception {
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine engine = mgr.getEngineByName("javascript");

	String check = engine.eval(std).toString();
	return Boolean.valueOf(check);
    }

    public CostPart setSujiCost2Part(CostPart part) throws Exception {
	String scrapCost = "0";
	String scrapRecycle = "0";

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo("PROFIT");

	String factory = ((NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + part.getMftFactory1())).getCode();

	if (csInfo != null) {// 스크랩 판매비, 재생비 세팅
	    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
	    for (CSInfoItemDTO dto : csInfoItemList) {

		if (factory.equals(dto.getValue3Code().getCode())) {
		    if ("RESINS001".equals(dto.getValue1())) {// 스크랩 판매비
			scrapCost = dto.getValue2();

		    } else if ("RESINS002".equals(dto.getValue1())) {// 스크랩 재생비
			scrapRecycle = dto.getValue2();

		    }
		}

	    }

	}
	part.setSujiScrapCost(scrapCost);
	part.setSujiScrapRecycle(scrapRecycle);

	return part;
    }

    public void udtPartCodeMaster(CostPart part, List<Map<String, Object>> codeList) throws Exception {

	if (part != null) {

	    part = setSujiCost2Part(part);// 수지 스크랩 판매비, 스크랩 재생비 설정

	    /**
	     * 부품Type, 생산처1, 생산처2, cv 범위, ton 범위를 체크해서 기준정보를 세팅한다
	     * 
	     * 1. 부품Type, 생산처1, 생산처2 는 필수체크 요소 2. cv범위가 설정되어 있고 ton범위도 설정되어 있으면 둘다 체크 3. cv범위만 설정되어 있으면 cv범위만 체크(ton 체크안함) 4. ton범위만 설정되어
	     * 있으면 ton범위만 체크(cv 체크안함)
	     * 
	     */

	    String partType = part.getPartType(); // ext.ket.cost.entity.CostPartType:993741522
	    String partMft1 = part.getMftFactory1(); // 993721815
	    String partMft2 = part.getMftFactory2(); // e3ps.common.code.NumberCode:993721821;e3ps.common.code.NumberCode:993721817
	    partMft2 = CommonUtil.getOIDLongValue2Str(partMft2);// 993721817
	    CostPartTypeCodeMaster master = null;
	    System.out.println("기준정보 동기화 대상 partNo : " + part.getPartNo() + "partType : " + partType + " partMft1 : " + partMft1
		    + "partMft2 : " + partMft2);
	    F1: for (Map<String, Object> list : codeList) {
		String stdPartType = CommonUtil.getOIDLongValue2Str((String) list.get("partTypeOid")); // 993741520
		String stdMft1 = (String) list.get("mftFactory1"); // 993721815

		if (partType.equals(stdPartType) && partMft1.equals(stdMft1)) {// 부품 type과 생산처1 비교

		    String stdMft2[] = ((String) list.get("mftFactory2")).split(";");

		    for (String splitMft2 : stdMft2) {// 생산처2 비교
			if (splitMft2.equals(partMft2)) {
			    master = (CostPartTypeCodeMaster) CommonUtil.getObject((String) list.get("id"));

			    String Moldstd = "";
			    String Tonstd = "";
			    String QuantityMaxStd = "";
			    String cv = part.getCvMold();
			    String ton = part.getFacilities();
			    String quantityMax = part.getQuantityMax();

			    boolean isMoldCheck = false;
			    boolean isTonCheck = false;
			    boolean isQuantityMaxCheck = false;

			    String moldLeftSign = master.getMoldFromSign();
			    String moldRightSign = master.getMoldToSign();
			    String tonLeftSign = master.getTonFromSign();
			    String tonRightSign = master.getTonToSign();
			    String quantityMaxLeftSign = master.getQuantityMaxFromSign();
			    String quantityMaxRightSign = master.getQuantityMaxToSign();

			    String moldLeftVal = master.getMoldFromVal();
			    String moldRightVal = master.getMoldToVal();
			    String tonLeftVal = master.getTonFromVal();
			    String tonRightVal = master.getTonToVal();
			    String quantityMaxLeftVal = master.getQuantityMaxFromVal();
			    String quantityMaxRightVal = master.getQuantityMaxToVal();

			    if (StringUtils.isNotEmpty(moldLeftSign)) {

				Moldstd = "if (" + moldLeftVal + moldLeftSign + cv;

				if (StringUtils.isNotEmpty(moldRightSign)) {
				    Moldstd += " && " + cv + moldRightSign + moldRightVal;
				}
				Moldstd += "){ true; } else { false; }";

				isMoldCheck = true;

			    } else {

				if (StringUtils.isNotEmpty(moldRightSign)) {
				    Moldstd = "if (" + cv + moldRightSign + moldRightVal + "){ true; } else { false; }";

				    isMoldCheck = true;
				}

			    }

			    if (StringUtils.isNotEmpty(tonLeftSign)) {

				Tonstd = "if (" + tonLeftVal + tonLeftSign + ton;

				if (StringUtils.isNotEmpty(tonRightSign)) {
				    Tonstd += " && " + ton + tonRightSign + tonRightVal;
				}
				Tonstd += "){ true; } else { false; }";

				isTonCheck = true;

			    } else {

				if (StringUtils.isNotEmpty(tonRightSign)) {
				    Tonstd = "if (" + ton + tonRightSign + tonRightVal + "){ true; } else { false; }";

				    isTonCheck = true;
				}

			    }

			    if (StringUtils.isNotEmpty(quantityMaxLeftSign)) {

				QuantityMaxStd = "if (" + quantityMaxLeftVal + quantityMaxLeftSign + quantityMax;

				if (StringUtils.isNotEmpty(quantityMaxRightSign)) {
				    QuantityMaxStd += " && " + quantityMax + quantityMaxRightSign + quantityMaxRightVal;
				}
				QuantityMaxStd += "){ true; } else { false; }";

				isQuantityMaxCheck = true;

			    } else {

				if (StringUtils.isNotEmpty(quantityMaxRightSign)) {
				    Tonstd = "if (" + quantityMax + quantityMaxRightSign + quantityMaxRightVal
					    + "){ true; } else { false; }";

				    isQuantityMaxCheck = true;
				}

			    }

			    if (!isMoldCheck && !isTonCheck && !isQuantityMaxCheck) {// cv, ton, QuantityMax 체크할필요없음
				break F1;
			    } else {
				if (isMoldCheck && isTonCheck && isQuantityMaxCheck) {// cv, ton, QuantityMax 셋 다 체크필요
				    if (checkStd(Moldstd) && checkStd(Tonstd) && checkStd(QuantityMaxStd)) {
					break F1;
				    } else {
					master = null;
				    }
				} else {
				    boolean isBreak = true;

				    if (isMoldCheck) {// cv 체크필요
					if (!checkStd(Moldstd)) {
					    isBreak = false;
					}
				    }
				    if (isTonCheck) {// ton 체크필요
					if (!checkStd(Tonstd)) {
					    isBreak = false;
					}
				    }
				    if (isQuantityMaxCheck) {// QuantityMax 체크필요
					if (!checkStd(QuantityMaxStd)) {
					    isBreak = false;
					}
				    }

				    if (isBreak) {
					System.out.println("기준정보 일치 ====================================================");

					System.out.println("partNo : " + part.getPartNo());
					System.out.println("Moldstd : " + Moldstd);
					System.out.println("Tonstd : " + Tonstd);
					System.out.println("QuantityMaxStd : " + QuantityMaxStd);
					System.out.println("isMoldCheck : " + isMoldCheck);
					System.out.println("isTonCheck : " + isTonCheck);
					System.out.println("isQuantityMaxCheck : " + isQuantityMaxCheck);

					System.out.println("기준정보 일치 ====================================================");
					break F1;
				    } else {
					master = null;
				    }
				}
			    }

			    System.out.println("기준정보 불일치 ====================================================");

			    System.out.println("partNo : " + part.getPartNo());
			    System.out.println("Moldstd : " + Moldstd);
			    System.out.println("Tonstd : " + Tonstd);
			    System.out.println("QuantityMaxStd : " + QuantityMaxStd);
			    System.out.println("isMoldCheck : " + isMoldCheck);
			    System.out.println("isTonCheck : " + isTonCheck);
			    System.out.println("isQuantityMaxCheck : " + isQuantityMaxCheck);

			    System.out.println("기준정보 불일치 ====================================================");

			}
		    }
		}

	    }

	    if (master != null) {
		Map<String, Object> result = CostUtil.manager.converObjectToMap(master);
		// value.put("", master.getAddManHourRate()); //추가공수효율 //costpart가 아닌 별도 테이블 에서 관리

		CostUtil.manager.convertMapToObject(result, part);
		PersistenceHelper.manager.save(part);

	    }

	    syncLogitics(part);// 관세, 물류비 세팅

	}
    }

    public void costTreeSetting(DefaultMutableTreeNode parent, ArrayList<CostPart> targetList) throws Exception {

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();

	    if (data instanceof CostPart) {
		targetList.add((CostPart) data);
	    }

	    costTreeSetting(child, targetList);
	}
    }

    public ArrayList<CostPart> getTargetList(String partListOid, String version, String subVersion) throws Exception {

	if (StringUtils.isEmpty(partListOid)) {
	    throw new Exception("partListOid 가 null입니다.");
	} else if (null == (PartList) CommonUtil.getObject(partListOid)) {
	    throw new Exception(partListOid + " 의 Object는 null입니다.");
	} else if (StringUtils.isEmpty(version)) {
	    throw new Exception("version이 null입니다.");
	}

	Map<String, Object> reqMap = new HashMap<String, Object>();

	reqMap.put("DATATYPE", "COSTPART");
	reqMap.put("partListOid", partListOid);
	reqMap.put("version", version);
	reqMap.put("subVersion", subVersion);

	List<DefaultMutableTreeNode> nodeList = CostTreeUtil.manager.getCostTreeList(reqMap);

	ArrayList<CostPart> targetList = new ArrayList<CostPart>();

	for (DefaultMutableTreeNode node : nodeList) {

	    Persistable data = (Persistable) node.getUserObject();
	    if (data instanceof CostPart) {
		targetList.add((CostPart) data);
	    }

	    costTreeSetting(node, targetList);

	}

	return targetList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void syncPartByCodeMaster(String partListOid, String version, String subVersion, String partOid) throws Exception {

	List<CostPart> targetList = null;

	CostPart part = (CostPart) CommonUtil.getObject(partOid);

	Map<String, String> costCurrency = this.getCostCurrency(part);

	if (part != null) {
	    targetList = CostTreeUtil.manager.getCostPartList(part);
	} else {
	    targetList = getTargetList(partListOid, version, subVersion);
	}

	List<Map<String, Object>> codeList = CostCacheManager.getCostPTItem("PARTTYPELINKLIST");

	if (codeList == null) {
	    codeList = getCostPartTypeLinkList();
	    CostCacheManager.updateCostPTByKey("PARTTYPELINKLIST", codeList);
	}

	// List<Map<String, Object>> codeList = getCostPartTypeLinkList();

	List<Map<String, Object>> workTimeList = this.getCostWorkTimeList();

	Transaction transaction = new Transaction();

	String attrLocked = part.getAttrLocked();
	boolean locked = false;
	String lockAttr[] = null;

	if (StringUtils.isNotEmpty(attrLocked)) {// 원가산출작업창 헤더에서 기준정보동기화 대상 컬럼에 lock체크가 되어 있는 경우 해당 컬럼은 동기화 대상에 포함하지 않는 로직
	    attrLocked = StringUtils.remove(attrLocked, "Locked");
	    lockAttr = attrLocked.split(",");
	    locked = true;
	}

	try {
	    transaction.start();

	    for (CostPart target : targetList) {

		Map<String, Object> lockMap = null;

		if (locked) {
		    lockMap = syncBlocking(lockAttr, target);
		}
		target = CostHelper.service.setCostPartCSInfo(target, costCurrency); // 비철원재료 기준 정보 동기화
		target = workHdyUpdate(target, true, workTimeList); // 조업도(시간,일,월,적용년도)
		udtPartCodeMaster(target, codeList);

		if (locked) {
		    CostUtil.manager.convertMapToObject(lockMap, target);
		    PersistenceHelper.manager.save(target);
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
    }

    public Map<String, Object> syncBlocking(String lockAttr[], CostPart part) throws Exception {// lock이 걸린 컬럼이 있으면 해당 컬럼에 대한 값은 원복해준다
	Map<String, Object> lockMap = new HashMap<String, Object>();

	for (String attr : lockAttr) {

	    String value = (String) ObjectUtil.manager.callGetter(part, attr);
	    lockMap.put(attr, value);
	}
	return lockMap;
    }

    /*
     * public Map<String, Object> converObjectToMapOnlyUnit(Object obj, String type1, String type2) throws Exception {
     * 
     * Map<String, Object> result = new HashMap<String, Object>();
     * 
     * try { Method[] methods = obj.getClass().getMethods();
     * 
     * for (Method method : methods) {
     * 
     * if (method.getName().indexOf("get") == 0) { String key = method.getName().substring(3); key = key.substring(0, 1).toLowerCase() +
     * key.substring(1); Object value = method.invoke(obj);
     * 
     * if (value != null && !StringUtils.contains(key.toLowerCase() , type1) && StringUtils.contains(key.toLowerCase() , type2)) {
     * result.put(key, String.valueOf(value)); } } } } catch (Exception e) { e.printStackTrace(); }
     * 
     * return result; }
     */

    public CostPart syncPakingByPart(CostPart part, boolean isSave) throws Exception {

	// String partListOid = CommonUtil.getOIDString(this.getMyPartList(part));
	// String version = Integer.toString(part.getVersion());

	QueryResult qr = getCostCurrencyQuery(part);

	Map<String, Object> dataMap = null;
	ArrayList<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];
	    dataMap = CostUtil.manager.converObjectToMap(currency);
	    codeList.add(dataMap);
	}

	part = udtPacking(part, codeList, isSave);

	return part;
    }

    public CostPart udtPacking(CostPart part, ArrayList<Map<String, Object>> codeList, boolean isSave) throws Exception {
	Map<String, Object> reqMap = new HashMap<String, Object>();
	reqMap.put("oid", CommonUtil.getOIDString(part));

	QueryResult qr = getCostPackingQuery(reqMap);
	String cu_val = ""; // 환율
	String packquantitySum = "";// 포장재합계
	String packUnitPriceSum = "";// 개별포장비 합계
	String packUnitPrice = "";// 개별포장비
	String expr = ""; // 수식
	String qty = "";
	String haveUnit = "";

	ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

	while (qr.hasMoreElements()) {
	    CostPacking pack = (CostPacking) qr.nextElement();
	    String c_code = pack.getCurrencyNumberCode().getCode();

	    for (Map<String, Object> map : codeList) {
		if (c_code.equals((String) map.get("value1"))) {
		    cu_val = (String) map.get("value2");
		    pack.setCurrencyCode(cu_val);
		    expr = "(" + pack.getPackingPrice() + "*" + cu_val + ")*" + pack.getPackQuantity();// 포장재합계 = (포장재단가*환율) * 포장재수량

		    packquantitySum = (String) CostCalculatorUtil.eval(expr);
		    pack.setPackquantitySum(packquantitySum);

		    String loss = "1";

		    NumberCode num = pack.getPackingCode();

		    if ("PACK014".equals(num.getCode())) {// 라벨
			loss = "1.03";
		    }

		    if ("/Pallet".equals(pack.getPackunit())) {
			qty = (String) CostCalculatorUtil.eval(part.getPackBoxUnit() + "*" + part.getPackPalletUnit());
		    } else {
			qty = part.getPackBoxUnit();
		    }

		    // var result = ( fixed( fixed((fixed(ProductMaxQty) * fixed(1))) / calcObj.month ) / fixed((fixed(qty) /
		    // fixed(calcObj.packQuantity)) ) * fixed((fixed(calcObj.haveMonth) / fixed(calcObj.collectRate))) );
		    // result = roundUp(result,-1);

		    expr = "(" + StringUtil.checkNullZero(part.getQuantityMax()) + "/12)/ (" + qty + "/" + pack.getPackQuantity() + ") * (" + pack.getHaveMonth()+ "/" + pack.getCollectRate() + ")";// 보유수량 재계산
		    System.out.println("보유수량 재계산 Expr : "+expr);
		    haveUnit = (String) CostCalculatorUtil.eval(expr);

		    BigDecimal bd = new BigDecimal(String.valueOf(haveUnit));
		    bd = bd.setScale(-1, BigDecimal.ROUND_UP);

		    haveUnit = bd.toPlainString().toString();
		    pack.setHaveUnit(haveUnit);

		    if ("회수".equals(pack.getCollectOption())) {

			expr = "(((" + pack.getPackingPrice() + "*" + cu_val + ")*" + pack.getHaveUnit() + ")/" + part.getQuantityTotal()
			        + ")*" + loss; // 개별포장비 = (((포장재단가 *
			                       // 환율)*보유수량)/전체물량)*loss

		    } else if ("미회수".equals(pack.getCollectOption())) {

			if ("/Pallet".equals(pack.getPackunit())) {
			    // expr = "(" + packquantitySum + "/" + part.getPackPalletUnit() + ")*" + loss;
			    // 수식변경 : 2018.12.28 이경무 과장 요청 - (포장재단가 / (포장수량BOX * 포장수량Pallet)) * loss
			    expr = "(" + packquantitySum + "/" + "(" + part.getPackBoxUnit() + "*" + part.getPackPalletUnit() + "))*"
				    + loss;
			} else {
			    expr = "(" + packquantitySum + "/" + part.getPackBoxUnit() + ")*" + loss;
			}
		    }

		    packUnitPrice = (String) CostCalculatorUtil.eval(expr);
		    pack.setPackUnitPrice(packUnitPrice);

		    PersistenceHelper.manager.save(pack);

		    packUnitPriceSum += packUnitPrice + "+";

		}
	    }

	}
	expr = StringUtils.removeEnd(packUnitPriceSum, "+");

	packUnitPriceSum = (String) CostCalculatorUtil.eval(expr);
	part.setPackUnitPriceSum(packUnitPriceSum);// 개별포장비 합계

	if (isSave) {
	    part = (CostPart) PersistenceHelper.manager.save(part);
	}

	return part;
    }

    public CostPart currencyKeyMapping(CostPart part, ArrayList<Map<String, Object>> codeList) throws Exception {
	Map<String, Object> currencyKey = CostUtil.manager.converObjectToMap(part);

	Map<String, Object> target = new HashMap<String, Object>();

	Set<String> st = currencyKey.keySet();
	Iterator<String> it = st.iterator();

	while (it.hasNext()) {
	    String key = (String) it.next();

	    String value = (String) currencyKey.get(key);

	    for (Map<String, Object> map : codeList) {
		if (StringUtil.checkNull(value).equals((String) map.get("value1"))) {

		    target.put(key + "Currency", (String) map.get("value2"));

		    break;
		}
	    }
	}

	CostUtil.manager.convertMapToObject(target, part);

	return part;
    }

    public void updatePartCurrency(CostPart part, ArrayList<Map<String, Object>> codeList) throws Exception {

	// 1. 부품 Type별 기준정보 동기화 Start
	// Map<String, Object> currencyKey = CostUtil.manager.converObjectToMap(part);
	//
	// Map<String, Object> target = new HashMap<String, Object>();
	//
	// Set<String> st = currencyKey.keySet();
	// Iterator<String> it = st.iterator();
	//
	// while (it.hasNext()) {
	// String key = (String) it.next();
	//
	// String value = (String) currencyKey.get(key);
	// if ("outMonetaryUnit".equals(key)) {
	// System.out.println("!!!value == " + value);
	// }
	// for (Map<String, Object> map : codeList) {
	// if (StringUtil.checkNull(value).equals((String) map.get("value1"))) {
	//
	// target.put(key + "Currency", (String) map.get("value2"));
	//
	// break;
	// }
	// }
	// }
	//
	// CostUtil.manager.convertMapToObject(target, part);
	part = currencyKeyMapping(part, codeList);

	System.out.println("partNo = " + part.getPartNo() + " oid = " + CommonUtil.getOIDLongValue(part));
	part = (CostPart) PersistenceHelper.manager.save(part);

	// 부품 Type별 기준정보 동기화 End

	// 2. 포장비 동기화 Start

	part = udtPacking(part, codeList, true);

	// 포장비 동기화 End

	// 3. 수지 원자재 sap 단가 동기화 Start
	PartList partlist = this.getMyPartList(part);
	String partListOid = CommonUtil.getOIDString(partlist);
	String version = Integer.toString(part.getVersion());
	String sapPrice = "";
	String sapPriceExpr = "";

	QueryResult qr = PersistenceHelper.manager.navigate(part, "material", CostMaterialLink.class, true);

	while (qr.hasMoreElements()) {
	    CostMaterialInfo material = (CostMaterialInfo) qr.nextElement();

	    List<Map<String, Object>> materialList = this.getMaterialInfoBySap(material.getPartNo(), part, material.getRate());// 수지원재료 단가
		                                                                                                               // 세팅

	    if (materialList.size() > 0) {
		Map<String, Object> materialMap = (Map<String, Object>) materialList.get(0);
		sapPrice = (String) materialMap.get("costprice");
		material.setMaterialPrice(sapPrice);

		PersistenceHelper.manager.save(material);

		sapPriceExpr += sapPrice + "+";
	    } else {
		sapPriceExpr += material.getMaterialPrice() + "+";
	    }
	}

	sapPriceExpr = StringUtils.removeEnd(sapPriceExpr, "+");

	part.setMtrSujiPrice((String) CostCalculatorUtil.eval(sapPriceExpr));

	PersistenceHelper.manager.save(part);

	// 수지 원자재 sap 단가 동기화 End

    }

    @Override
    public void syncPartByMyCurrency(CostPart part) throws Exception {
	// ArrayList<CostPart> targetList = getTargetList(partListOid, version);

	// QueryResult qr = getCostCurrencyQuery(partListOid, version);

	QueryResult qr = getCostCurrencyQuery(part);

	Map<String, Object> dataMap = null;
	ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];
	    dataMap = CostUtil.manager.converObjectToMap(currency);
	    list.add(dataMap);
	}

	ArrayList<CostPart> targetList = getMyChildPartList(part);
	targetList.add(part);

	for (CostPart target : targetList) {
	    updatePartCurrency(target, list);
	}
    }

    @Override
    public void refreshCurrencyInfo(String partOid) throws Exception {

	Transaction trx = null;
	try {
	    trx = new Transaction();
	    trx.start();

	    CostPart part = (CostPart) CommonUtil.getObject(partOid);

	    int version = part.getVersion();
	    int subVersion = part.getSubVersion();
	    String pjtNo = part.getProject().getPjtNo();

	    if (part == null) {
		throw new Exception("part 가 null입니다.");
	    }

	    ProductMaster master = getMyProductMaster(part);

	    CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");
	    CostCurrencyInfo currency = null;

	    Map<String, Object> dataMap = null;
	    ArrayList<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();

	    QueryResult qr = getCostCurrencyQuery(part);

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		currency = (CostCurrencyInfo) o[0];
		dataMap = CostUtil.manager.converObjectToMap(currency);
		codeList.add(dataMap);
	    }

	    if (csInfo != null) {
		List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
		for (CSInfoItemDTO dto : csInfoItemList) {

		    int checkCnt = 0;

		    for (Map<String, Object> map : codeList) {

			if (map.get("value1").equals(dto.getValue1())) {
			    checkCnt++;
			}
		    }

		    if (checkCnt == 0) {
			currency = CostCurrencyInfo.newCostCurrencyInfo();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemType", dto.getItemType());
			map.put("value1", dto.getValue1());
			map.put("name", dto.getValue1CodeName());
			map.put("value2", dto.getValue2());
			map.put("sort", dto.getObject().getSort());
			map.put("version", version);
			map.put("subVersion", subVersion);
			map.put("pjtNo", pjtNo);
			currency.setMaster(master);
			// currency.setPartList(pList);
			currency.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(map, currency);
			PersistenceHelper.manager.save(currency);
		    }

		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    @Override
    public void createCurrencyInfo(CostPart part) throws Exception {

	// PartList pList = (PartList) CommonUtil.getObject(partListOid);

	// if (StringUtils.isEmpty(partListOid)) {
	// throw new Exception("partListOid 가 null입니다.");
	// } else if (null == pList) {
	// throw new Exception(partListOid + " 의 Object는 null입니다.");
	// } else if (StringUtils.isEmpty(version)) {
	// throw new Exception("version이 null입니다.");
	// }
	int version = part.getVersion();
	int subVersion = part.getSubVersion();
	String pjtNo = part.getProject().getPjtNo();

	if (part == null) {
	    throw new Exception("part 가 null입니다.");
	}
	Transaction trx = null;
	try {
	    trx = new Transaction();
	    trx.start();

	    ProductMaster master = getMyProductMaster(part);
	    QueryResult qr = getCostCurrencyQuery(part);

	    if (qr.size() == 0) {
		CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

		CostCurrencyInfo currency = null;

		if (csInfo != null) {
		    List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
		    for (CSInfoItemDTO dto : csInfoItemList) {
			currency = CostCurrencyInfo.newCostCurrencyInfo();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemType", dto.getItemType());
			map.put("value1", dto.getValue1());
			map.put("name", dto.getValue1CodeName());
			map.put("value2", dto.getValue2());
			map.put("sort", dto.getObject().getSort());
			map.put("version", version);
			map.put("subVersion", subVersion);
			map.put("pjtNo", pjtNo);
			currency.setMaster(master);
			// currency.setPartList(pList);
			currency.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(map, currency);
			PersistenceHelper.manager.save(currency);
		    }

		}

		syncPartByMyCurrency(part);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	// TODO Auto-generated method stub

    }

    /**
     * <pre>
     * @description 환율정보 가져오기 (환율코드, 값)
     * @author Administrator
     * @date 2019. 5. 9. 오후 2:58:14
     * @method getCostCurrency
     * @param part
     * @return Map<String,String>
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, String> getCostCurrency(CostPart part) throws Exception {

	Map<String, String> map = new HashMap<String, String>();

	int version = part.getVersion();
	int subVersion = part.getSubVersion();
	// String[] pjtNo = part.getPartNo().split("-");
	String pjtNo = part.getProject().getPjtNo();
	ProductMaster master = getMyProductMaster(part);

	QueryResult qr = null;

	if (master == null) {
	    return map;
	}

	Long masterOid = CommonUtil.getOIDLongValue(master);

	QuerySpec qs = new QuerySpec();

	int idx = qs.appendClassList(CostCurrencyInfo.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.VERSION, SearchCondition.EQUAL, version),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.SUB_VERSION, SearchCondition.EQUAL, subVersion),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.PJT_NO, SearchCondition.EQUAL, pjtNo),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, "masterReference.key.id", SearchCondition.EQUAL, masterOid),
	        new int[] { idx });

	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currencyInfo = (CostCurrencyInfo) o[0];
	    map.put(currencyInfo.getValue1(), currencyInfo.getValue2());
	}

	return map;
    }

    @Override
    public QueryResult getCostCurrencyQuery(CostPart part) throws Exception {
	int version = part.getVersion();
	int subVersion = part.getSubVersion();
	String pjtNo = part.getProject().getPjtNo();
	ProductMaster master = getMyProductMaster(part);

	System.out.println("VERSION ###" + version + "." + subVersion);

	QueryResult qr = null;

	if (master == null) {
	    return qr;
	}
	Long masterOid = CommonUtil.getOIDLongValue(master);

	QuerySpec qs = new QuerySpec();

	int idx = qs.appendClassList(CostCurrencyInfo.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.VERSION, SearchCondition.EQUAL, version),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.SUB_VERSION, SearchCondition.EQUAL, subVersion),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, CostCurrencyInfo.PJT_NO, SearchCondition.EQUAL, pjtNo),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostCurrencyInfo.class, "masterReference.key.id", SearchCondition.EQUAL, masterOid),
	        new int[] { idx });

	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    @Override
    public Map<String, String> getMyExRate2Code(String partOid) throws Exception {// 부품별 환율정보
	// TODO Auto-generated method stub

	// CostPart의 버전에 맞는 개별 기준정보(CostCurrencyInfo)가 있는지 확인하고 있다면 개별 기준정보 사용

	CostPart part = (CostPart) CommonUtil.getObject(partOid);

	CostPart tempPart = part;
	ProductMaster master = null;

	while (tempPart != null) {
	    master = tempPart.getMaster();
	    tempPart = (CostPart) tempPart.getParent();
	}
	PartList partlist = master.getPartList();

	Map<String, String> map = new HashMap<String, String>();

	// QueryResult qr = PersistenceHelper.manager.navigate(partlist, "costCurrency", costCurrencyLink.class, true);

	QueryResult qr = getCostCurrencyQuery(part);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];

	    map.put(currency.getValue1(), currency.getValue2());

	}

	if (qr.size() == 0) {
	    CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

	    if (csInfo != null) {
		List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
		for (CSInfoItemDTO dto : csInfoItemList) {
		    map.put(dto.getValue1(), dto.getValue2());
		}

	    }
	}

	return map;
    }

    @Override
    public Map<String, String> getMyExRate(String partOid) throws Exception {// 부품별 환율정보
	// TODO Auto-generated method stub

	// CostPart의 버전에 맞는 개별 기준정보(CostCurrencyInfo)가 있는지 확인하고 있다면 개별 기준정보 사용

	CostPart part = (CostPart) CommonUtil.getObject(partOid);

	CostPart tempPart = part;
	ProductMaster master = null;

	while (tempPart != null) {
	    master = tempPart.getMaster();
	    tempPart = (CostPart) tempPart.getParent();
	}
	PartList partlist = master.getPartList();

	String eNumExRate = "";
	String eNumKeysExRate = "";

	// QueryResult qr = PersistenceHelper.manager.navigate(partlist, "costCurrency", costCurrencyLink.class, true);

	QueryResult qr = getCostCurrencyQuery(part);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];

	    // if(currency.getVersion().equals(part.getVersion()) ){
	    // eNumExRate += "|"+currency.getName();
	    // eNumKeysExRate += "|"+currency.getValue2();
	    // }
	    eNumExRate += "|" + currency.getName();
	    eNumKeysExRate += "|" + currency.getValue2();

	}

	if (qr.size() == 0) {
	    CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

	    if (csInfo != null) {
		List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
		for (CSInfoItemDTO dto : csInfoItemList) {
		    eNumExRate += "|" + dto.getValue1CodeName();
		    eNumKeysExRate += "|" + dto.getValue2();
		}

	    }
	    eNumExRate = StringUtils.removeStart(eNumExRate, "|");
	    eNumExRate = "||" + eNumExRate;

	    eNumKeysExRate = StringUtils.removeStart(eNumKeysExRate, "|");
	    eNumKeysExRate = "||" + eNumKeysExRate;
	}

	// eNumExRate = StringUtils.removeStart(eNumExRate, "|");
	// eNumExRate = "|" + eNumExRate;

	// eNumKeysExRate = StringUtils.removeStart(eNumKeysExRate, "|");
	// eNumKeysExRate = "|" + eNumKeysExRate;

	Map<String, String> map = new HashMap<String, String>();

	map.put("eNumExRate", eNumExRate);
	map.put("eNumKeysExRate", eNumKeysExRate);

	return map;
    }

    public void childTreeSetting(ArrayList<Map<String, Object>> attrData, Map<String, Object> childMap) {
	for (Map<String, Object> All : attrData) {
	    ArrayList<Map<String, Object>> childList = (ArrayList<Map<String, Object>>) All.get("children");

	    if (childList != null && childMap.get("parent") != null && childList.get(0) != null) {
		if (childMap.get("parent").equals(childList.get(0).get("key"))) {
		    ArrayList<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
		    child.add(childMap);
		    childList.get(0).put("children", child);
		} else {
		    childTreeSetting(childList, childMap);
		}
	    }
	}
    }

    public void costFomulaTreeSetting(DefaultMutableTreeNode parent, ArrayList<Map<String, Object>> attrData) throws Exception {

	Map<String, Object> node = new HashMap<String, Object>();

	ArrayList<Map<String, Object>> children = null;

	CostFormula cf = (CostFormula) parent.getUserObject();

	String id = CommonUtil.getOIDString(cf);
	String title = cf.getName();
	node.put("key", id);
	node.put("title", title);
	node.put("expanded", true);
	node.put("children", children);
	node.put("level", parent.getLevel());

	if (cf.getParent() != null && ((CostFormula) cf.getParent()).getName().equals("총원가")) {

	    attrData.add(node);
	}

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    if (!"총원가".equals(cf.getName())) {
		Persistable data = (Persistable) child.getUserObject();

		CostFormula childFomula = (CostFormula) data;
		Map<String, Object> childMap = new HashMap<String, Object>();
		childMap.put("key", CommonUtil.getOIDString(childFomula));
		childMap.put("title", childFomula.getName());
		childMap.put("expanded", true);
		childMap.put("children", children);
		childMap.put("level", child.getLevel());

		String parentOid = CommonUtil.getOIDString((CostFormula) childFomula.getParent());
		childMap.put("parent", parentOid);

		if (parentOid.equals(node.get("key"))) {
		    children = new ArrayList<Map<String, Object>>();
		    children.add(childMap);
		    node.put("children", children);
		} else {
		    attrData.add(node);

		}
		childTreeSetting(attrData, childMap);
	    }

	    costFomulaTreeSetting(child, attrData);
	}
    }

    @Override
    public ArrayList<Map<String, Object>> getCostFomulaTreeList() throws Exception {

	Map<String, Object> reqMap = new HashMap<String, Object>();

	reqMap.put("DATATYPE", "COSTFORMULA");
	reqMap.put("deptType", "AUTO");

	List<DefaultMutableTreeNode> nodeList = CostTreeUtil.manager.getCostTreeList(reqMap);

	ArrayList<Map<String, Object>> attrData = new ArrayList<Map<String, Object>>();

	for (DefaultMutableTreeNode node : nodeList) {

	    // Persistable data = (Persistable) node.getUserObject();

	    costFomulaTreeSetting(node, attrData);

	}

	return attrData;
    }

    public void updateRelatedObjectByPart(CostPart oldPart, CostPart newPart) throws Exception {
	/************************************************
	 * 
	 * 
	 * part case 생성에 따른 연관 object copy Start
	 * 
	 * 
	 *************************************************/

	/***************************************************/
	// 1. 예상판매량 복사Start

	QueryResult qr = PersistenceHelper.manager.navigate(oldPart, "costQuantity", costPartQtyLink.class, true);

	while (qr.hasMoreElements()) {

	    CostQuantity oldObj = (CostQuantity) qr.nextElement();
	    CostQuantity newObj = (CostQuantity) HistoryHelper.duplicate(oldObj);
	    newObj.setCostPart(newPart);
	    PersistenceHelper.manager.save(newObj);

	}
	// 1. 예상판매량 복사End
	/***************************************************/

	/***************************************************/

	// 2. 포장비 복사Start

	qr = PersistenceHelper.manager.navigate(oldPart, "costPacking", costPackingPartLink.class, true);

	while (qr.hasMoreElements()) {

	    CostPacking oldObj = (CostPacking) qr.nextElement();
	    CostPacking newObj = (CostPacking) HistoryHelper.duplicate(oldObj);
	    newObj.setCostPart(newPart);
	    PersistenceHelper.manager.save(newObj);

	}

	// 2. 포장비 복사End
	/***************************************************/

	/***************************************************/

	// 3. 기타 투자비 복사Start

	qr = PersistenceHelper.manager.navigate(oldPart, "etcInvest", CostEtcInvestLink.class, true);

	Map<String, String> etcMap = new HashMap<String, String>();

	while (qr.hasMoreElements()) {

	    CostEtcInvest oldObj = (CostEtcInvest) qr.nextElement();
	    CostEtcInvest newObj = (CostEtcInvest) HistoryHelper.duplicate(oldObj);

	    newObj = (CostEtcInvest) PersistenceHelper.manager.save(newObj);

	    CostEtcInvestLink link = CostEtcInvestLink.newCostEtcInvestLink(newObj, newPart);
	    PersistenceHelper.manager.save(link);

	    String oldEtcOid = CommonUtil.getOIDString(oldObj);
	    String newEtcOid = CommonUtil.getOIDString(newObj);

	    etcMap.put(oldEtcOid, newEtcOid);

	}

	// 3. 기타 투자비 복사End
	/***************************************************/

	/***************************************************/
	// 4. 수지원재료 복사Start

	qr = PersistenceHelper.manager.navigate(oldPart, "material", CostMaterialLink.class, true);

	while (qr.hasMoreElements()) {

	    CostMaterialInfo oldObj = (CostMaterialInfo) qr.nextElement();
	    CostMaterialInfo newObj = (CostMaterialInfo) HistoryHelper.duplicate(oldObj);

	    newObj = (CostMaterialInfo) PersistenceHelper.manager.save(newObj);

	    CostMaterialLink link = CostMaterialLink.newCostMaterialLink(newObj, newPart);
	    PersistenceHelper.manager.save(link);

	}

	// 4. 수지원재료 복사End

	/***************************************************/

	/***************************************************/
	// 5. 감가비 복사Start

	// 5. 감가비 복사Start

	qr = PersistenceHelper.manager.navigate(oldPart, "reduce", CostReduceLink.class, true);

	List<HashMap<String, Object>> parentlist = new ArrayList<HashMap<String, Object>>();

	List<CostInvestInfo> childList = new ArrayList<CostInvestInfo>();

	HashMap<String, Object> parentMap = null;

	while (qr.hasMoreElements()) {

	    CostInvestInfo oldObj = (CostInvestInfo) qr.nextElement();
	    CostInvestInfo newObj = (CostInvestInfo) HistoryHelper.duplicate(oldObj);

	    if ("etc".equals(oldObj.getInvestType())) {
		newObj.setEtcInvestOid(etcMap.get(oldObj.getEtcInvestOid()));
	    }

	    newObj = (CostInvestInfo) PersistenceHelper.manager.save(newObj);

	    if (oldObj.getParent() == null) {// old Parernt객체를 Key로, new Parernt 객체를 value로 담는다
		parentMap = new HashMap<String, Object>();
		parentMap.put(CommonUtil.getOIDString(oldObj), newObj);
		parentlist.add(parentMap);
	    } else {// 자식 리스트를 만든다
		childList.add(newObj);
	    }

	    CostReduceLink link = CostReduceLink.newCostReduceLink(newObj, newPart);
	    PersistenceHelper.manager.save(link);

	}

	for (CostInvestInfo child : childList) {// 예전 Parernt를 참조하고 있는 new객체에 대해 변경된 new Parernt객체를 넣어준다
	    for (HashMap<String, Object> pMap : parentlist) {
		String parentOldKey = CommonUtil.getOIDString(child.getParent());
		CostInvestInfo newParent = (CostInvestInfo) pMap.get(parentOldKey);

		if (newParent != null) {
		    child.setParent(newParent);
		    PersistenceHelper.manager.save(child);
		}
	    }
	}

	// 5. 감가비 복사End

	/***************************************************/

	/***************************************************/
	// 6. 매출액 및 영업이익 복사 Start

	qr = PersistenceHelper.manager.navigate(oldPart, "costAnalysis", costAnalisysLink.class, true);

	while (qr.hasMoreElements()) {

	    CostAnalysis oldObj = (CostAnalysis) qr.nextElement();
	    CostAnalysis newObj = (CostAnalysis) HistoryHelper.duplicate(oldObj);

	    newObj = (CostAnalysis) PersistenceHelper.manager.save(newObj);

	    costAnalisysLink link = costAnalisysLink.newcostAnalisysLink(newPart, newObj);
	    PersistenceHelper.manager.save(link);

	}

	// 6. 매출액 및 영업이익 복사 End

	/***************************************************/

	/************************************************
	 * 
	 * 
	 * part case 생성에 따른 연관 object copy End
	 * 
	 * 
	 *************************************************/

	/***************************************************/
    }

    @Override
    public void copyRelatedObject(List<Map<String, String>> targetList) throws Exception {

	CostPart oldPart = null;
	CostPart newPart = null;

	try {

	    for (Map<String, String> partMap : targetList) {
		Set<String> st = partMap.keySet();
		Iterator<String> it = st.iterator();

		String oldPartOid = "";
		String newPartOid = "";

		while (it.hasNext()) {
		    oldPartOid = (String) it.next();
		    newPartOid = (String) partMap.get(oldPartOid);
		}

		oldPart = (CostPart) CommonUtil.getObject(oldPartOid);

		if (oldPart == null) {
		    throw new Exception("oldPart Object가 null입니다.");
		}

		newPart = (CostPart) CommonUtil.getObject(newPartOid);

		if (newPart == null) {
		    throw new Exception("newPart Object가 null입니다.");
		}

		updateRelatedObjectByPart(oldPart, newPart);

	    }

	} catch (Exception e) {
	    throw e;
	}
    }

    @Override
    public void deleteRelatedObject(CostPart part) throws Exception {
	/************************************************
	 * 
	 * 
	 * part case 삭제에 따른 연관 object 삭제 Start
	 * 
	 * 
	 *************************************************/

	/***************************************************/
	// 1. 예상판매량 삭제Start

	QueryResult qr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);

	while (qr.hasMoreElements()) {

	    CostQuantity obj = (CostQuantity) qr.nextElement();

	    PersistenceHelper.manager.delete(obj);

	}
	// 1. 예상판매량 삭제End
	/***************************************************/

	/***************************************************/

	// 2. 포장비 삭제Start

	qr = PersistenceHelper.manager.navigate(part, "costPacking", costPackingPartLink.class, true);

	while (qr.hasMoreElements()) {

	    CostPacking obj = (CostPacking) qr.nextElement();

	    PersistenceHelper.manager.delete(obj);

	}

	// 2. 포장비 삭제End
	/***************************************************/

	/***************************************************/

	// 3. 기타 투자비 삭제Start

	qr = PersistenceHelper.manager.navigate(part, "etcInvest", CostEtcInvestLink.class, false);

	while (qr.hasMoreElements()) {

	    CostEtcInvestLink link = (CostEtcInvestLink) qr.nextElement();
	    CostEtcInvest obj = link.getEtcInvest();

	    PersistenceHelper.manager.delete(link);
	    PersistenceHelper.manager.delete(obj);

	}

	// 3. 기타 투자비 삭제End
	/***************************************************/

	/***************************************************/
	// 4. 수지원재료 삭제Start

	qr = PersistenceHelper.manager.navigate(part, "material", CostMaterialLink.class, false);

	while (qr.hasMoreElements()) {

	    CostMaterialLink link = (CostMaterialLink) qr.nextElement();
	    CostMaterialInfo obj = link.getMaterial();

	    PersistenceHelper.manager.delete(link);
	    PersistenceHelper.manager.delete(obj);

	}

	// 4. 수지원재료 삭제End

	/***************************************************/

	/***************************************************/
	// 5. 감가비 삭제Start

	qr = PersistenceHelper.manager.navigate(part, "reduce", CostReduceLink.class, false);

	while (qr.hasMoreElements()) {

	    CostReduceLink link = (CostReduceLink) qr.nextElement();
	    CostInvestInfo obj = link.getReduce();

	    PersistenceHelper.manager.delete(link);
	    PersistenceHelper.manager.delete(obj);

	}

	// 5. 감가비 삭제End

	/***************************************************/

	/***************************************************/
	// 6. 매출액 및 영업이익 삭제 Start

	qr = PersistenceHelper.manager.navigate(part, "costAnalysis", costAnalisysLink.class, true);

	while (qr.hasMoreElements()) {

	    CostAnalysis obj = (CostAnalysis) qr.nextElement();

	    PersistenceHelper.manager.delete(obj);

	}

	// 6. 매출액 및 영업이익 삭제 End

	// 7. 환율정보 삭제 Start
	qr = this.getCostCurrencyQuery(part);

	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostCurrencyInfo currency = (CostCurrencyInfo) o[0];

		PersistenceHelper.manager.delete(currency);

	    }
	}

	qr = PersistenceHelper.manager.navigate(part, "costAnalysis", costAnalisysLink.class, true);

	while (qr.hasMoreElements()) {

	    CostAnalysis obj = (CostAnalysis) qr.nextElement();

	    PersistenceHelper.manager.delete(obj);

	}

	// 7. 환율정보 삭제 End

	/***************************************************/

	/************************************************
	 * 
	 * 
	 * part case 삭제에 따른 연관 object 삭제 End
	 * 
	 * 
	 *************************************************/

	/***************************************************/
    }

    /**
     * <pre>
     * @description 환율정보 복사
     * @author dhkim
     * @date 2019. 5. 10. 오후 2:16:13
     * @method copyCostCurrencyInfo
     * @param oldPart
     * @param newPart
     * @return List<CostCurrencyInfo>
     * @throws Exception
     * </pre>
     */
    @Override
    public List<CostCurrencyInfo> copyCostCurrencyInfo(CostPart oldPart, CostPart newPart) throws Exception {

	QueryResult qr = CostCodeHelper.service.getCostCurrencyQuery(oldPart);
	List<CostCurrencyInfo> list = new ArrayList<CostCurrencyInfo>();

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];
	    currency = (CostCurrencyInfo) HistoryHelper.duplicate(currency);

	    // String[] pjtNo = newPart.getPartNo().split("-");
	    String pjtNo = newPart.getProject().getPjtNo();
	    currency.setPjtNo(pjtNo);
	    currency.setVersion(newPart.getVersion());
	    currency.setSubVersion(newPart.getSubVersion());
	    currency.setMaster(newPart.getMaster());
	    currency.setOwner(SessionHelper.manager.getPrincipalReference());
	    currency = (CostCurrencyInfo) PersistenceHelper.manager.save(currency);
	    list.add(currency);
	}

	return list;
    }

    public String culcCurrency(String cost, String unit) throws Exception {// 환율계산
	System.out.println(cost + "*" + unit);
	return (String) CostCalculatorUtil.eval(cost + "*" + unit);
    }

    public String culcReduce(String investCost, String investPay) throws Exception {// 감가액 계산
	return (String) CostCalculatorUtil.eval(investCost + "-" + investPay);
    }

    public void setNormalReduceQty(Map<String, Object> dataMap) throws Exception {// 일반감가수량 계산
	String expr = (String) dataMap.get("moldShot") + "*" + (String) dataMap.get("cv") + "*" + (String) dataMap.get("investUnit"); // 일반감가수량
	                                                                                                                              // (moldShot
	                                                                                                                              // *
	                                                                                                                              // c/v
	                                                                                                                              // *
	                                                                                                                              // 벌수)

	if ("equip".equals((String) dataMap.get("investType"))) {// 설비 = 생산량(표준) * 조업도(시간) * 조업도(일) * 년조업도(월) * 적용년도 * line수
	    expr = (String) dataMap.get("outputExpr") + "*" + (String) dataMap.get("workHour") + "*" + (String) dataMap.get("workDay")
		    + "*" + (String) dataMap.get("workYear") + "*" + (String) dataMap.get("capaYear") + "*"
		    + (String) dataMap.get("investUnit");
	}
	String normalReduceQty = (String) CostCalculatorUtil.eval((expr));

	dataMap.put("normalReduceQty", normalReduceQty);// 일반감가수량
    }

    public void setMoldShot(Map<String, Object> dataMap) throws Exception {// moldShot 세팅
	// String moldShot = StringUtils.isNotEmpty((String)dataMap.get("facilities")) ? "0" : "1000000";// 설비Ton이 입력되어 있으면 Press 아니면 mold
	String moldShot = StringUtil.checkNullZero((String) dataMap.get("facilities"));// 설비Ton이 입력되어 있으면 Press 아니면 mold

	String partTypeCode = (String) dataMap.get("partTypeCode");

	if ("PRESS".equals(partTypeCode)) {// Press 일때 설비 Ton으로 moldShot 설정
	    if (Integer.parseInt((String) dataMap.get("facilities")) <= 80) {
		moldShot = "50000000";
	    }
	    if (Integer.parseInt((String) dataMap.get("facilities")) > 80) {
		moldShot = "15000000";
	    }
	} else if ("MOLD".equals(partTypeCode)) {
	    moldShot = "1000000";
	}

	dataMap.put("moldShot", moldShot);
    }

    public void setEachReduceCost(Map<String, Object> dataMap) throws Exception {// 개별감가비계산

	String reduceCode = (String) dataMap.get("reduceCode");
	String investReduceCost = (String) dataMap.get("investReduceCost");// 감가액
	String salesReduceQty = (String) dataMap.get("salesReduceQty");// 판매감가수량
	String normalReduceQty = (String) dataMap.get("normalReduceQty");// 일반감가수량
	String tariffPrice = (String) dataMap.get("tariffPrice");// 관세 & 물류비
	String mtnExpence = (String) dataMap.get("mtnExpence");// 관리비
	String investUnit = (String) dataMap.get("investUnit");// 벌수

	NumberCode num = null;
	if (StringUtils.isNotEmpty(reduceCode)) {
	    num = CodeHelper.manager.getNumberCode("COSTREDUCEOPTION", reduceCode);
	}

	if (num != null) {
	    if (num.getName().equals("지급") || num.getName().equals("범용")) {
		dataMap.put("eachReduceCost", "0");
	    } else {
		if (num.getName().equals("판매")) {
		    dataMap.put(
			    "eachReduceCost",
			    (String) CostCalculatorUtil.eval("((" + investReduceCost + "*" + investUnit + ") * (1+" + tariffPrice
			            + ") * (1+" + mtnExpence + "))" + "/" + salesReduceQty));// 개별감가비(((감가액*벌수)*(1+관세&물류비)*(1+관리비))/판매감가수량)
		} else if (num.getName().equals("일반")) {
		    dataMap.put(
			    "eachReduceCost",
			    (String) CostCalculatorUtil.eval("((" + investReduceCost + "*" + investUnit + ") * (1+" + mtnExpence + "))"
			            + "/" + normalReduceQty));// 개별감가비(((감가액*벌수)*(1+관세&물류비)*(1+관리비))/일반감가수량)
		}
	    }
	}

    }

    public void setReduceStdCode(Map<String, Object> dataMap) {// 감가비 기준정보 금형,설비,기타 세팅(관세&물류비,관리비)

	String investType = (String) dataMap.get("investType");
	String factory = (String) dataMap.get("factory");// 제작처
	String mftFactory1 = (String) dataMap.get("mftFactory1");// part 생산국

	String moldTariffPrice = "";
	String facTariffPrice = "";
	String etcTariffPrice = "";
	String moldMtnExpence = "";
	String facMtnExpence = "";
	String etcMtnExpence = "";

	ArrayList<CostReduceCode> reduceList = (ArrayList<CostReduceCode>) dataMap.get("reduceList");

	for (CostReduceCode costReduceCode : reduceList) {

	    String c_factory = ((NumberCode) costReduceCode.getCostMaking()).getCode();
	    String c_mft1 = CommonUtil.getOIDLongValue2Str(((NumberCode) costReduceCode.getFactory()));

	    if ((factory + mftFactory1).equals((c_factory + c_mft1))) {// 기준정보의 제작처,생산국 일치 판단
		moldTariffPrice = costReduceCode.getMoldTariffPrice();// 금형-관세,물류비
		facTariffPrice = costReduceCode.getFacTariffPrice();// 설비-관세,물류비
		etcTariffPrice = costReduceCode.getEtcTariffPrice();// 기타-관세,물류비

		moldMtnExpence = costReduceCode.getMoldMtnExpence();// 금형관리비
		facMtnExpence = costReduceCode.getFacMtnExpence();// 설비관리비
		etcMtnExpence = costReduceCode.getEtcMtnExpence();// 기타관리비
		break;
	    }
	}

	if ("mold".equals(investType)) {
	    dataMap.put("tariffPrice", StringUtil.checkReplaceStr(moldTariffPrice, "0")); // 금형-관세,물류비
	    dataMap.put("mtnExpence", StringUtil.checkReplaceStr(moldMtnExpence, "0")); // 금형관리비
	} else if ("equip".equals(investType)) {
	    dataMap.put("tariffPrice", StringUtil.checkReplaceStr(facTariffPrice, "0")); // 설비-관세,물류비
	    dataMap.put("mtnExpence", StringUtil.checkReplaceStr(facMtnExpence, "0")); // 설비관리비
	} else if ("etc".equals(investType)) {
	    dataMap.put("tariffPrice", StringUtil.checkReplaceStr(etcTariffPrice, "0")); // 기타-관세,물류비
	    dataMap.put("mtnExpence", StringUtil.checkReplaceStr(etcMtnExpence, "0")); // 기타관리비
	}
    }

    public void setReduceStdList(Map<String, Object> dataMap) throws Exception {// 감가비 기준정보 리스트 세팅

	QueryResult qr = getCostReduceQuery(dataMap);
	ArrayList<CostReduceCode> reduceList = new ArrayList<CostReduceCode>();

	while (qr.hasMoreElements()) {
	    CostReduceCode reduce = (CostReduceCode) qr.nextElement();
	    reduceList.add(reduce);
	}
	dataMap.put("reduceList", reduceList);
    }

    public void setMoldReduceData(Map<String, Object> dataMap, CostPart part) throws Exception {

	String investType = (String) dataMap.get("investType");// 투자비 구분(금형,설비,기타)
	String costType = (String) dataMap.get("costType");// 신규,양산 구분
	Map<String, String> cMap = getMyExRate2Code(CommonUtil.getOIDString(part));// 환율 정보 구하기

	String investCost = ""; // 투자비
	String investPay = "0"; // 지급액
	String investReduceCost = ""; // 감가액
	String factory = ""; // 제작처
	String mftFactory1 = part.getMftFactory1();// 생산국

	String massQty = "0";// 양산수량
	String totalQty = part.getQuantityTotal();// 판매수량 - 제품 예상판매량 합계
	String addQty = "0";

	String salesReduceQty = ""; // 판매감가수량
	String trxCode = (String) dataMap.get("trxCode");

	if ("mold".equals(investType)) {// 금형 투자비

	    if ("N".equals(costType)) {// 신규
		investCost = culcCurrency(part.getMoldNIC(), (String) cMap.get(part.getMoldNICMUnit())); // 신규 투자비 (투자비 * 환율)
		factory = part.getMoldNFactory();// 금형 신규 제작처
		dataMap.put("nFactory", factory);
		investPay = culcCurrency(part.getMoldNPay(), (String) cMap.get(part.getMoldNPayMUnit())); // 신규 지급액 (지급액 * 환율)
	    } else if ("M".equals(costType)) {// 양산
		investCost = culcCurrency(part.getMoldMPIC(), (String) cMap.get(part.getMoldMPICMUnit())); // 양산 투자비 (투자비 * 환율)
		factory = part.getMoldMPFactory();// 금형 양산 제작처
		massQty = part.getMoldMPQTotal();// 양산수량
		dataMap.put("mFactory", factory);
	    }

	    investReduceCost = culcReduce(investCost, investPay); // 감가액(투자비-지급액)

	    dataMap.put("itemName", "");
	} else if ("equip".equals(investType)) {// 설비 투자비

	    if ("N".equals(costType)) {// 신규
		investCost = culcCurrency(part.getFacNIC(), (String) cMap.get(part.getFacNICMUnit())); // 신규 투자비 (투자비 * 환율)
		factory = part.getFacNFactory();// 설비 신규 제작처
		dataMap.put("nFactory", factory);
		investPay = culcCurrency(part.getFacNPay(), (String) cMap.get(part.getFacNPayMUnit())); // 신규 지급액 (지급액 * 환율)
	    } else if ("M".equals(costType)) {// 양산

		factory = part.getFacMPFactory();// 설비 양산 제작처
		massQty = part.getFacMPQTotal();// 양산수량
		dataMap.put("mFactory", factory);
		investCost = culcCurrency(part.getFacMPIC(), (String) cMap.get(part.getFacMPICMUnit())); // 양산 투자비 (투자비 * 환율)
	    }

	    investReduceCost = culcReduce(investCost, investPay); // 감가액(투자비-지급액)

	    dataMap.put("itemName", "");
	} else if ("etc".equals(investType)) {// 기타 투자비

	    CostEtcInvest costEtcInvest = (CostEtcInvest) dataMap.get("costEtcInvest");

	    if ("N".equals(costType)) {
		investCost = culcCurrency(costEtcInvest.getEtcNcost(), (String) cMap.get(costEtcInvest.getEtcNunit_1())); // 신규 투자비 (투자비 *
		                                                                                                          // 환율)
		factory = costEtcInvest.getEtcNfactory(); // 설비 신규 제작처

		dataMap.put("nFactory", factory);
		investPay = culcCurrency(costEtcInvest.getEtcNpay(), (String) cMap.get(costEtcInvest.getEtcNunit_2())); // 신규 지급액 (지급액 * 환율)
	    } else if ("M".equals(costType)) {
		investCost = culcCurrency(costEtcInvest.getEtcPcost(), (String) cMap.get(costEtcInvest.getEtcPunit())); // 양산 투자비 (투자비 * 환율)
		factory = costEtcInvest.getEtcPfactory(); // 설비 양산 제작처
		massQty = costEtcInvest.getEtcTotalQty();// 양산수량

		dataMap.put("mFactory", factory);
	    }

	    investReduceCost = culcReduce(investCost, investPay); // 감가액(투자비-지급액)
	    dataMap.put("itemName", costEtcInvest.getItemName());

	}

	if ("Update".equals(trxCode)) {
	    // CostInvestInfo investInfo = (CostInvestInfo) dataMap.get("investInfo");
	    if ("equip".equals(investType)) {
		investReduceCost = (String) dataMap.get("investReduceCost");// 감가비는 감가비 팝업을 통해 세팅된 값을 건드리면 안되므로..
	    }
	    addQty = (String) dataMap.get("addQty");// 추가수량은 감가비팝업을 통해서만 값이 세팅되므로..
	    // investCost = (String)dataMap.get("investCost");
	    // investPay = (String)dataMap.get("investPay");
	    // massQty = (String)dataMap.get("massQty");
	    // dataMap.put("reduceCode", investInfo.getReduceCode());// 감가 옵션 코드(일반/판매/지급/범용)
	}

	salesReduceQty = (String) CostCalculatorUtil.eval(massQty + "+" + totalQty + "+" + addQty); // 판매감가수량

	dataMap.put("factory", factory); // 제작처
	dataMap.put("mftFactory1", mftFactory1); // part 생산국

	setReduceStdCode(dataMap);// 감가비 기준정보 금형,설비,기타 세팅(관세&물류비,관리비)

	dataMap.put("investCost", investCost); // 투자비
	dataMap.put("investPay", investPay); // 지급액
	dataMap.put("investReduceCost", investReduceCost); // 감가액(투자비-지급액)

	dataMap.put("salesQty", totalQty);// 판매수량
	dataMap.put("massQty", massQty);// 양산수량

	dataMap.put("salesReduceQty", salesReduceQty);// 판매감가수량

	Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);

	CostCalculateUtil.manager.staticCacluate(partMap);// 임시 capa 계산

	String capa = (String) partMap.get("capa");

	CostPartType partType = (CostPartType) CommonUtil.getObject("ext.ket.cost.entity.CostPartType:" + part.getPartType());

	if ("etc".equals(investType)) {
	    capa = "1.0000";
	}
	dataMap.put("capaRate", capa);

	if (partType.isCapaCfg()) {
	    capa = "1.0000";
	}
	// String investUnit = (String) CostCalculatorUtil.eval("Math.ceil(" + capa + ")");// 벌수
	dataMap.put("investUnit", capa);

	dataMap.put("facilities", part.getFacilities());

	dataMap.put("partTypeCode", partType.getCode());

	setMoldShot(dataMap);// moldShot 세팅

	String cv = StringUtil.checkNullZero(part.getCvMold());
	dataMap.put("cv", cv);

	dataMap.put("workHour", part.getWorkHour());
	dataMap.put("workDay", part.getWorkDay());
	dataMap.put("workYear", part.getWorkYear());
	dataMap.put("capaYear", part.getCapaYear());
	dataMap.put("outputExpr", part.getOutputExpr());

	setNormalReduceQty(dataMap);// 일반감가수량 계산

	if (!"Update".equals(trxCode)) {
	    List<NumberCode> CodeList = (List<NumberCode>) dataMap.get("CodeList");

	    NumberCode NOMAL = null;
	    NumberCode SALES = null;

	    for (NumberCode cd : CodeList) {
		if (cd.getName().equals("일반")) {
		    NOMAL = cd;
		} else if (cd.getName().equals("판매")) {
		    SALES = cd;
		}
	    }

	    String normalReduceQty = (String) dataMap.get("normalReduceQty");
	    String reduceCode = "";

	    if ((investCost).equals(investPay)) {// 지급

		reduceCode = NOMAL.getCode();
		dataMap.put("reduceCode", reduceCode);// 감가 옵션 코드(일반/판매/지급/범용)

	    } else {

		String std = "if (" + StringUtil.checkNullZero(normalReduceQty) + " <= 0){ true; } else { false; }";

		if (checkStd(std)) {// 일반감가수량이 0 이면 감가비 계산이 안되므로 무조건 감가 옵션코드를 [판매] 로 지정한다 2018.10.31 이경무 과장 요청

		    reduceCode = SALES.getCode();
		    dataMap.put("reduceCode", reduceCode);

		} else {
		    BigDecimal sales = new BigDecimal(salesReduceQty);
		    BigDecimal normal = new BigDecimal(normalReduceQty);

		    if (normal.compareTo(sales) == 1) {// 판매
			reduceCode = SALES.getCode();
			dataMap.put("reduceCode", reduceCode);// 감가 옵션 코드(일반/판매/지급/범용)
		    }

		    if (sales.compareTo(normal) == 1) {// 일반
			reduceCode = NOMAL.getCode();
			dataMap.put("reduceCode", reduceCode);// 감가 옵션 코드(일반/판매/지급/범용)
		    }

		    if (StringUtils.isEmpty((String) dataMap.get("reduceCode"))) {// 판매감가수량과 일반감가수량이 똑같은 경우는 판매로 설정한다 2019.04.04 이경무 과장 요청
			reduceCode = SALES.getCode();
			dataMap.put("reduceCode", reduceCode);// 감가 옵션 코드(일반/판매/지급/범용)
		    }
		}

	    }

	    if ("etc".equals(investType)) {// 기타 투자비는 옵션이 무조건 [판매]
		reduceCode = SALES.getCode();
		dataMap.put("reduceCode", reduceCode);// 감가 옵션 코드(일반/판매/지급/범용)
	    }
	}

	System.out.println("================ 감가비 Start ====================");
	System.out.println("================             ====================");
	System.out.println("partNo : " + part.getPartNo());
	System.out.println("trxCode :" + trxCode);
	System.out.println("investType :" + investType);
	System.out.println("투자비 :" + investCost);
	System.out.println("지급액 :" + investPay);
	System.out.println("감가액 :" + investReduceCost);
	System.out.println("capa :" + capa);
	System.out.println("일반감가수량 :" + (String) dataMap.get("normalReduceQty"));
	System.out.println("판매감가수량 :" + salesReduceQty);
	System.out.println("reduceCode :" + (String) dataMap.get("reduceCode"));
	System.out.println("================             ====================");
	System.out.println("================ 감가비 End ====================");

	setEachReduceCost(dataMap);// 개별감가비 계산

    }

    public void reduceCodeSet(Map<String, Object> dataMap) {
	List<NumberCode> CodeList = NumberCodeHelper.manager.getNumberCodeList("COSTREDUCEOPTION");// 감가비 코드(일반, 판매, 범용, 지급)

	dataMap.put("CodeList", CodeList);
    }

    public void createInvestInfo(Map<String, Object> dataMap, CostPart part) throws Exception {
	setMoldReduceData(dataMap, part);

	String reduceCode = (String) dataMap.get("reduceCode");

	if (StringUtils.isNotEmpty(reduceCode)) {
	    CostInvestInfo costInvestInfo = (CostInvestInfo) dataMap.get("investInfo");

	    // NumberCode option = CodeHelper.manager.getNumberCode("COSTREDUCEOPTION", (String)dataMap.get("reduceCode"));

	    if (costInvestInfo == null) {
		costInvestInfo = CostInvestInfo.newCostInvestInfo();
	    }
	    dataMap.remove("owner");
	    CostUtil.manager.convertMapToObject(dataMap, costInvestInfo);

	    // 감가액이 0일시 지금으로 변경
	    String investReduceCost = StringUtil.checkReplaceStr(costInvestInfo.getInvestReduceCost(), "0");

	    if (0 == Double.valueOf(investReduceCost)) {
		costInvestInfo.setReduceCode("REDU004");
	    }

	    // costInvestInfo.setReduceOption(option);
	    PersistenceHelper.manager.save(costInvestInfo);
	    if (!"Update".equals((String) dataMap.get("trxCode"))) {
		CostReduceLink link = CostReduceLink.newCostReduceLink(costInvestInfo, part);
		PersistenceHelper.manager.save(link);
	    }
	}

    }

    @SuppressWarnings("unused")
    public CostPart udtReduceCosttoPart(CostPart part) throws Exception {
	QueryResult qr = PersistenceHelper.manager.navigate(part, "reduce", CostReduceLink.class, true);

	String exprSum = "";
	String moldExpr = "";
	String equipExpr = "";
	String etcExpr = "";

	while (qr.hasMoreElements()) {

	    CostInvestInfo invest = (CostInvestInfo) qr.nextElement();
	    exprSum += invest.getEachReduceCost() + "+";

	    switch (invest.getInvestType()) {
	    case "mold":
		moldExpr += invest.getEachReduceCost() + "+";
		break;

	    case "equip":
		equipExpr += invest.getEachReduceCost() + "+";
		break;

	    case "etc":
		etcExpr += invest.getEachReduceCost() + "+";
		break;

	    default:
		break;
	    }

	}
	exprSum = StringUtils.removeEnd(exprSum, "+");
	moldExpr = StringUtils.removeEnd(moldExpr, "+");
	equipExpr = StringUtils.removeEnd(equipExpr, "+");
	etcExpr = StringUtils.removeEnd(etcExpr, "+");

	part.setMoldReducePrice((String) CostCalculatorUtil.eval(moldExpr)); // 금형감가비 합계
	part.setFacReducePrice((String) CostCalculatorUtil.eval(equipExpr)); // 설비감가비 합계
	part.setEtcReducePrice((String) CostCalculatorUtil.eval(etcExpr)); // 기타감가비 합계
	part.setReduceCostExpr((String) CostCalculatorUtil.eval(exprSum));// 감가비합계

	return part;
	// PersistenceHelper.manager.save(part);
    }

    @Override
    public CostPart transferInvest(CostPart part) throws Exception {// 투자비 데이터를 감가비계산을 위한 테이블로 분산 이관

	// Persistable obj = (Persistable)CommonUtil.getObject(oid);
	// ArrayList<CostPart> targetList = null;
	//
	// //1. 전달된 oid가 partlist 일 경우 : partlist의 해당 버전의 모든 costpart에 대해 작업
	// //2. 전달된 oid가 costpart 일 경우 : 전달된 costpart만 단일 작업
	//
	// if(obj instanceof PartList){
	// targetList = getTargetList(oid,version);
	// }else if(obj instanceof CostPart){
	// targetList = new ArrayList<CostPart>();
	// targetList.add((CostPart)obj);
	// }
	//

	Map<String, Object> dataMap = new HashMap<String, Object>();

	setReduceStdList(dataMap);// 감가비 기준정보 리스트 세팅
	reduceCodeSet(dataMap);// 감가비 코드 세팅(일반, 판매, 범용, 지급)

	ArrayList<CostReduceCode> reduceList = (ArrayList<CostReduceCode>) dataMap.get("reduceList");
	List<NumberCode> CodeList = (List<NumberCode>) dataMap.get("CodeList");

	List<CostEtcInvest> costEtcInvestTemp = null;
	List<CostEtcInvest> costEtcInvestTemp2 = null;
	List<CostEtcInvest> costEtcInvest = null;

	List<String> checkList = new ArrayList<String>();

	QueryResult qr = PersistenceHelper.manager.navigate(part, "reduce", CostReduceLink.class, false);

	dataMap.put("oid", CommonUtil.getOIDString(part));
	QueryResult etcQr = getCostEtcInvestQuery(dataMap);
	costEtcInvest = new ArrayList<CostEtcInvest>();
	costEtcInvestTemp = new ArrayList<CostEtcInvest>();
	costEtcInvestTemp2 = new ArrayList<CostEtcInvest>();
	while (etcQr.hasMoreElements()) {
	    CostEtcInvest etc = (CostEtcInvest) etcQr.nextElement();
	    costEtcInvestTemp.add(etc);
	}

	if (qr.size() > 0) {
	    while (qr.hasMoreElements()) {

		// 투자비 없는 감가비 삭제 start
		CostReduceLink link = (CostReduceLink) qr.nextElement();
		CostInvestInfo invest = link.getReduce();
		String InvestType = invest.getInvestType();// 투자비 구분
		String costType = invest.getCostType(); // 양산,신규 구분 : 신규(N) 양산(M)

		System.out.println(" ************* 감가비 삭제 여부 판단할 항목 확인 start  **************");
		System.out.println(" *****************************************************************");

		System.out.println("partNo : " + part.getPartNo());
		System.out.println("investOid : " + CommonUtil.getOIDString(invest));
		System.out.println("InvestType : " + InvestType);
		System.out.println("costType : " + costType);
		System.out.println("MoldNIC : " + part.getMoldNIC());
		System.out.println("MoldMPIC : " + part.getMoldMPIC());
		System.out.println("FacNIC : " + part.getFacNIC());
		System.out.println("FacMPIC : " + part.getFacMPIC());

		System.out.println(" *****************************************************************");
		System.out.println(" ************* 감가비 삭제 여부 판단할 항목 확인 end  **************");
		boolean delFlag = false;

		if ("0".equals(part.getMoldNIC()) && "mold".equals(InvestType) && costType.equals("N")) {
		    delFlag = true;
		}

		if ("0".equals(part.getMoldMPIC()) && "mold".equals(InvestType) && costType.equals("M")) {
		    delFlag = true;
		}

		if ("0".equals(part.getFacNIC()) && "equip".equals(InvestType) && costType.equals("N")) {
		    delFlag = true;
		}

		if ("0".equals(part.getFacMPIC()) && "equip".equals(InvestType) && costType.equals("M")) {
		    delFlag = true;
		}

		if (delFlag) {
		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(invest);

		    // 투자비 없는 감가비 삭제 end
		} else {
		    dataMap = CostUtil.manager.converObjectToMap(invest);

		    dataMap.put("reduceList", reduceList);
		    dataMap.put("CodeList", CodeList);
		    dataMap.put("trxCode", "Update"); // 신규입력이 아닌 update
		    dataMap.put("investInfo", invest); // 신규입력이 아닌 update
		    dataMap.put("costEtcInvest", (CostEtcInvest) CommonUtil.getObject(invest.getEtcInvestOid())); // costEtcInvest 객체
		    createInvestInfo(dataMap, part);

		    if ("etc".equals(invest.getInvestType())) {
			costEtcInvestTemp2.add((CostEtcInvest) CommonUtil.getObject(invest.getEtcInvestOid()));
		    }

		    checkList.add(invest.getInvestType() + invest.getCostType());
		}

	    }
	}

	// for(CostEtcInvest cei : costEtcInvestTemp) {
	// costEtcInvest.add(cei);
	// }

	dataMap = new HashMap<String, Object>();

	/******************************************************************************************
	 * 
	 * 1. 금형 신규 투자비 Create
	 * 
	 * /
	 ******************************************************************************************/
	dataMap.put("reduceList", reduceList);
	dataMap.put("CodeList", CodeList);
	dataMap.put("investType", "mold"); // 투자비 구분 : 금형
	dataMap.put("costType", "N"); // 양산,신규 구분 : 신규(N) 양산(M)

	boolean goFlag = true;

	if (!"0".equals(part.getMoldNIC())) {

	    for (String check : checkList) {
		if ("moldN".equals(check)) {
		    goFlag = false;
		    break;
		}
	    }
	    if (goFlag) {
		createInvestInfo(dataMap, part);
	    }
	}

	goFlag = true;

	/******************************************************************************************
	 * 
	 * 2. 금형 양산 투자비 Create
	 * 
	 * /
	 ******************************************************************************************/
	dataMap = new HashMap<String, Object>();
	dataMap.put("reduceList", reduceList);
	dataMap.put("CodeList", CodeList);
	dataMap.put("investType", "mold"); // 투자비 구분 : 금형
	dataMap.put("costType", "M"); // 양산,신규 구분 : 신규(N) 양산(M)

	if (!"0".equals(part.getMoldMPIC())) {

	    for (String check : checkList) {
		if ("moldM".equals(check)) {
		    goFlag = false;
		    break;
		}
	    }

	    if (goFlag) {
		createInvestInfo(dataMap, part);
	    }
	}

	goFlag = true;

	/******************************************************************************************
	 * 
	 * 3. 설비 신규 투자비 Create
	 * 
	 * /
	 ******************************************************************************************/
	dataMap = new HashMap<String, Object>();
	dataMap.put("reduceList", reduceList);
	dataMap.put("CodeList", CodeList);
	dataMap.put("investType", "equip"); // 투자비 구분 : 설비
	dataMap.put("costType", "N"); // 양산,신규 구분 : 신규(N) 양산(M)

	if (!"0".equals(part.getFacNIC())) {
	    for (String check : checkList) {
		if ("equipN".equals(check)) {
		    goFlag = false;
		    break;
		}
	    }

	    if (goFlag) {
		createInvestInfo(dataMap, part);
	    }
	}

	goFlag = true;

	/******************************************************************************************
	 * 
	 * 4. 설비 양산 투자비 Create
	 * 
	 * /
	 ******************************************************************************************/
	dataMap = new HashMap<String, Object>();
	dataMap.put("reduceList", reduceList);
	dataMap.put("CodeList", CodeList);
	dataMap.put("investType", "equip"); // 투자비 구분 : 설비
	dataMap.put("costType", "M"); // 양산,신규 구분 : 신규(N) 양산(M)

	if (!"0".equals(part.getFacMPIC())) {
	    for (String check : checkList) {
		if ("equipM".equals(check)) {
		    goFlag = false;
		    break;
		}
	    }

	    if (goFlag) {
		createInvestInfo(dataMap, part);
	    }
	}

	/******************************************************************************************
	 * 
	 * 기타 투자비(신규,양산) Create
	 * 
	 * /
	 ******************************************************************************************/

	for (CostEtcInvest source : costEtcInvestTemp) {

	    boolean isEtcNew = true;
	    String newEtcOid = CommonUtil.getOIDString(source);

	    for (CostEtcInvest target : costEtcInvestTemp2) {

		String oldEtcOid = CommonUtil.getOIDString(target);
		if (oldEtcOid.equals(newEtcOid)) {
		    isEtcNew = false;
		}
	    }
	    if (isEtcNew) {
		costEtcInvest.add(source);
	    }

	}
	for (CostEtcInvest etc : costEtcInvest) {

	    // 1.기타 투자비 신규 Start
	    dataMap = new HashMap<String, Object>();
	    dataMap.put("reduceList", reduceList);
	    dataMap.put("CodeList", CodeList);
	    dataMap.put("oid", CommonUtil.getOIDString(part));

	    dataMap.put("investType", "etc"); // 투자비 구분 : 금형
	    dataMap.put("costType", "N"); // 양산,신규 구분 : 신규(N) 양산(M)
	    dataMap.put("costEtcInvest", etc); // 기타투자비 객체
	    dataMap.put("etcInvestOid", CommonUtil.getOIDString(etc));

	    if (!"0".equals(etc.getEtcNcost())) {
		createInvestInfo(dataMap, part);
	    }
	    // 1.기타 투자비 신규 End

	    // 2.기타 투자비 양산 Start
	    dataMap = new HashMap<String, Object>();
	    dataMap.put("reduceList", reduceList);
	    dataMap.put("CodeList", CodeList);
	    dataMap.put("oid", CommonUtil.getOIDString(part));

	    dataMap.put("investType", "etc"); // 투자비 구분 : 금형
	    dataMap.put("costType", "M"); // 양산,신규 구분 : 신규(N) 양산(M)
	    dataMap.put("costEtcInvest", etc); // 기타투자비 객체
	    dataMap.put("etcInvestOid", CommonUtil.getOIDString(etc));

	    if (!"0".equals(etc.getEtcPcost())) {
		createInvestInfo(dataMap, part);
	    }
	    // 2.기타 투자비 양산 End
	}

	part = udtReduceCosttoPart(part);// 감가비 합계 반영

	return part;

    }

    public List<CostEtcInvest> getEtcInvestCost(CostPart part) throws Exception {
	Map<String, Object> dataMap = new HashMap<String, Object>();

	dataMap.put("oid", CommonUtil.getOIDString(part));
	QueryResult qr = getCostEtcInvestQuery(dataMap);
	List<CostEtcInvest> costEtcInvestList = new ArrayList<CostEtcInvest>();

	while (qr.hasMoreElements()) {
	    CostEtcInvest etc = (CostEtcInvest) qr.nextElement();
	    costEtcInvestList.add(etc);
	}
	return costEtcInvestList;

    }

    public CostPart getMyParent(CostPart part) {
	CostPart tempPart = part;
	CostPart parent = null;

	while (tempPart != null) {
	    parent = tempPart;
	    tempPart = (CostPart) tempPart.getParent();

	}

	return parent;
    }

    public ArrayList<CostPart> getMyChildPartList(CostPart part) throws Exception {
	ArrayList<CostPart> targetList = new ArrayList<CostPart>();

	DefaultMutableTreeNode node = CostTreeUtil.manager.getCostTree(part);

	costTreeSetting(node, targetList);

	return targetList;
    }

    public String getCalcInvetExpr(CostPart part, Map<String, String> cMap) throws Exception {
	String expr = "";
	List<CostEtcInvest> etcInvestList = null;

	expr += culcCurrency(part.getMoldNIC(), (String) cMap.get(part.getMoldNICMUnit())) + "+"; // 금형 신규 투자비 (투자비 * 환율)

	expr += culcCurrency(part.getFacNIC(), (String) cMap.get(part.getFacNICMUnit())) + "+"; // 설비 신규 투자비 (투자비 * 환율)

	etcInvestList = getEtcInvestCost(part);

	for (CostEtcInvest etc : etcInvestList) {
	    expr += culcCurrency(etc.getEtcNcost(), (String) cMap.get(etc.getEtcNunit_1())) + "+"; // 기타 신규 투자비 (투자비 * 환율)
	}

	return expr;
    }

    public CostPart calcNinvestUpdate(CostPart part, boolean isSave) throws Exception {// 1레벨 part에 자신 포함 모든 하위파트의 신규 투자비를 합산하여 더해준다

	ArrayList<CostPart> targetList = null;

	Map<String, String> cMap = getMyExRate2Code(CommonUtil.getOIDString(part));// 환율 정보 구하기

	targetList = getMyChildPartList(part);

	String investCost = "";

	for (CostPart childpart : targetList) {
	    investCost += getCalcInvetExpr(childpart, cMap);
	}

	investCost += getCalcInvetExpr(part, cMap);

	investCost = StringUtils.removeEnd(investCost, "+");
	part.setProductNInvestTotal((String) CostCalculatorUtil.eval(investCost));

	if (isSave) {
	    part = (CostPart) PersistenceHelper.manager.save(part);
	}

	return part;

    }
    
    @Override
    public void NinvestPartiotionUpdate(CostPart rootPart) throws Exception{//1레벨 part 투자비 분할
	
	ArrayList<CostPart> targetList = getMyChildPartList(rootPart);
	targetList.add(rootPart);
	
	Map<String, Object> caseTotal = new HashMap<String, Object>();
	caseTotal.put("moldinvestPriceTotal", "0");
	caseTotal.put("pressinvestPriceTotal", "0");
	caseTotal.put("equipinvestPriceTotal", "0");
	caseTotal.put("purchaseinvestPriceTotal", "0");
	caseTotal.put("etcinvestPriceTotal", "0");
	caseTotal.put("investPriceTotal", "0");
	
	Map<String, Object> reqMap = new HashMap<String, Object>();
	reqMap.put("costType", "N");

	for (CostPart part : targetList) {
	    String oid = (String) CommonUtil.getOIDString(part);
	    reqMap.put("oid", oid);

	    QueryResult qr = CostUtil.manager.getCostInvestInfoQuery(reqMap);

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostInvestInfo ciInfo = (CostInvestInfo) o[0];

		String typeOid = CostPartType.class.getName() + ":" + part.getPartType();

		CostPartType cpType = (CostPartType) CommonUtil.getObject(typeOid);

		String investType = ciInfo.getInvestType();
		String investCost = ciInfo.getInvestCost();
		String investUnit = ciInfo.getInvestUnit();
		String exp = investCost + "*" + investUnit;
		String investPrice = (String) CostCalculateUtil.manager.eval(exp);

		if ("mold".equals(investType)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCode("MOLDDIVISION", part.getMoldMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }
		}

		if ("equip".equals(investType)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCode("FACDIVISION", part.getFacMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }
		}

		if ("mold".equals(investType) && "PRESS".equals(cpType.getCode())) {
		    investType = "press";
		}else if ("mold".equals(investType) && "MOLD".equals(cpType.getCode())) {
		    investType = "mold";
		}else if ("mold".equals(investType) && "구매".equals(cpType.getCode())) {
		    investType = "purchase";
		}else if("etc".equals(investType)){
		    
		}else if("equip".equals(investType)){
		    
		}else{
		    continue;
		}

		String typeInvestPriceTotal = (String) caseTotal.get(investType + "investPriceTotal");
		String investPriceTotal = (String) caseTotal.get("investPriceTotal");

		exp = investPrice + "+" + typeInvestPriceTotal;
		typeInvestPriceTotal = (String) CostCalculateUtil.manager.eval(exp);

		caseTotal.put(investType + "investPriceTotal", typeInvestPriceTotal);

		exp = investPrice + "+" + investPriceTotal;
		investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);

		caseTotal.put("investPriceTotal", investPriceTotal);
	    }
	}
	
	CostUtil.manager.convertMapToObject(caseTotal, rootPart);
	
    }

    @Override
    public void initCost(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String partListOid = (String) reqMap.get("partListOid");
	String taskOid = (String) reqMap.get("taskOid");
	String version = (String) reqMap.get("version");
	String subVersion = (String) reqMap.get("subVersion");

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String taskType = task.getTaskType();
	String category = task.getTaskTypeCategory();

	CostPart part = (CostPart) CommonUtil.getObject(oid);

	if (StringUtils.isEmpty(partListOid)) {
	    ProductMaster master = part.getMaster();
	    PartList partList = master.getPartList();
	    partListOid = CommonUtil.getOIDString(partList);
	}
	boolean goFlag = false;

	// 1. 검토프로젝트 원가산출요청 task를 통해 접속이면 Task가 완료가 아닐 때 기준정보동기화/감가비 초기화/재산출을 진행
	// 2. 검토프로젝트 원가산출 task를 통해 접속이면 Task가 완료가 아닐 때 최초 1회만 기준정보동기화/감가비 초기화/재산출을 진행
	if (task != null && !"Y".equals(part.getInitFlag()) && task.getTaskState() != 5 && task.getProject() instanceof ReviewProject) {
	    if ("COST".equals(taskType) && ("COST015".equals(category) || "COST013".equals(category))) {
		goFlag = true;
	    }
	}

	if (goFlag) {
	    this.syncPartByCodeMaster(partListOid, version, subVersion, oid);// 기준정보 동기화

	    part = (CostPart) CommonUtil.getObject(oid);

	    Transaction trx = new Transaction();

	    try {

		trx.start();

		QueryResult qr = PersistenceHelper.manager.navigate(part, "reduce", CostReduceLink.class, false);

		while (qr.hasMoreElements()) {// 감가비 삭제

		    CostReduceLink link = (CostReduceLink) qr.nextElement();
		    CostInvestInfo invest = link.getReduce();

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(invest);

		}

		trx.commit();
		trx = null;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (trx != null) {
		    trx.rollback();
		    trx = null;
		}
	    }

	    Map<String, Object> dataMap = new HashMap<String, Object>();
	    dataMap.put("oid", oid);

	    CostHelper.service.reCalculateCostPart(dataMap);// 재산출

	    if ("COST015".equals(category)) {// 원가산출task 일때
		part = (CostPart) CommonUtil.getObject(oid);

		part.setInitFlag("Y");
		PersistenceHelper.manager.save(part);
	    }

	} else {

	}

    }

    public CostPart workHdyUpdate(CostPart part, boolean isSave, List<Map<String, Object>> workTimeList) throws Exception {// 조업도(시간,일,월,적용년도)

	// String partMft2 = part.getMftFactory2();
	// NumberCode num = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:"+partMft2);
	// String codeType = num.getCodeType().toString();
	// String code = num.getCode();
	//
	// String workHour = StringUtil.defaultIfEmpty(StringUtil.checkNull(CodeHelper.service.getNumberCodeDescription(codeType, code,
	// "ko")),"0");
	// String workDay = StringUtils.defaultIfEmpty(StringUtil.checkNull(CodeHelper.service.getNumberCodeDescription(codeType, code,
	// "en")), "0");
	// String workYear = StringUtils.defaultIfEmpty(StringUtil.checkNull(CodeHelper.service.getNumberCodeDescription(codeType, code,
	// "zh_CN")), "0");
	//
	// Map<String, Object> map = NumberCodeUtil.getNumberCodeValueMap(codeType, code, "ko");
	// String capaYear = StringUtils.defaultIfEmpty((String) map.get("abbr"), "0");

	String partType = part.getPartType(); // ext.ket.cost.entity.CostPartType:993741522

	String partMft1 = part.getMftFactory1();
	String partMft2 = part.getMftFactory2();

	// List<Map<String, Object>> workTimeList = this.getCostWorkTimeList();

	String workHour = "0";
	String workDay = "0";
	String workYear = "0";
	String capaYear = "0";

	if (StringUtils.isNotEmpty(partMft1) && StringUtils.isNotEmpty(partMft2)) {

	    BRK: for (Map<String, Object> work : workTimeList) {
		String partTypeOid = (String) work.get("partTypeOid");
		String mftfactory1 = (String) work.get("mftFactory1");
		String mftfactory2 = (String) work.get("mftFactory2");
		String worktimeoid = (String) work.get("worktimeoid");

		CostWorkTimeConfig workTime = (CostWorkTimeConfig) CommonUtil.getObject(worktimeoid);

		if (workTime != null && (CostPartType.class.getName() + ":" + partType).equals(partTypeOid)) {
		    if (StringUtils.isNotEmpty(mftfactory1)) {
			String splitMft1[] = mftfactory1.split(";");

			for (String mft_1 : splitMft1) {
			    if (mft_1.equals(partMft1)) {

				if (StringUtils.isNotEmpty(mftfactory2)) {
				    String splitMft2[] = mftfactory2.split(";");
				    for (String mft_2 : splitMft2) {// 생산처2 비교
					if (mft_2.equals(partMft2)) {
					    workHour = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkHour()), "0");
					    workDay = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkDay()), "0");
					    workYear = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkYear()), "0");
					    capaYear = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getCapaYear()), "0");
					    break BRK;
					}
				    }
				} else {// 생산처2가 없으면 전체적용으로 판단한다
				    workHour = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkHour()), "0");
				    workDay = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkDay()), "0");
				    workYear = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getWorkYear()), "0");
				    capaYear = StringUtil.defaultIfEmpty(StringUtil.checkNull(workTime.getCapaYear()), "0");
				    break BRK;
				}

			    }
			}
		    }
		}
	    }

	    part.setWorkHour(workHour);
	    part.setWorkDay(workDay);
	    part.setWorkYear(workYear);
	    part.setCapaYear(capaYear);

	    if (isSave) {
		part = (CostPart) PersistenceHelper.manager.save(part);
	    }

	}

	return part;

    }

    @Override
    public CostPart syncCost(CostPart part) throws Exception {// 통합 동기화

	if (!PersistenceHelper.isPersistent(part)) {
	    return part;
	}

	// Persistable obj = (Persistable) CommonUtil.getObject(oid);
	// ArrayList<CostPart> targetList = null;

	// 1. 전달된 oid가 partlist 일 경우 : partlist의 해당 버전의 모든 costpart에 대해 작업
	// 2. 전달된 oid가 costpart 일 경우 : 전달된 costpart만 단일 작업
	//
	// if (obj instanceof PartList) {
	// targetList = getTargetList(oid, version);
	// } else if (obj instanceof CostPart) {
	// targetList = new ArrayList<CostPart>();
	// targetList.add((CostPart) obj);
	// }

	// for (CostPart part : targetList) {
	// if (part.getParent() == null) {
	// // 1. 신규 투자비 합계 update
	// calcNinvestUpdate(part);
	// }
	// /**
	// * 2. 물량 관련 업데이트
	// * udtQtyInfoMySubTree (1레벨 부품의 최종안으로 선택된 물량정보를 찾는다.)
	// * ㄴudtQtyInfo (하위 부품list 루프를 돌면서 calcQty 호출하여 물량재계산)
	// * ㄴsyncPackingByPart (물량정보 변경에 따른 포장비 업데이트)
	// * ㄴudtPacking (포장비 환율 적용하여 재계산)
	// */
	// udtQtyInfoMySubTree(part);
	// }

	QueryResult qr = getCostCurrencyQuery(part);

	Map<String, Object> dataMap = null;

	ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currency = (CostCurrencyInfo) o[0];
	    dataMap = CostUtil.manager.converObjectToMap(currency);
	    list.add(dataMap);
	}

	part = currencyKeyMapping(part, list); // 환율정보 mapping

	part = calcNinvestUpdate(part, false); // 신규 투자비 합계 update

	// /**
	// * 2. 물량 관련 업데이트
	// * udtQtyInfoMySubTree (1레벨 부품의 최종안으로 선택된 물량정보를 찾는다.)
	// * ㄴudtQtyInfo (하위 부품list 루프를 돌면서 calcQty 호출하여 물량재계산)
	// * ㄴsyncPackingByPart (물량정보 변경에 따른 포장비 업데이트)
	// * ㄴudtPacking (포장비 환율 적용하여 재계산)
	// */
	part = udtQtyInfoMySubTree(part, false, false);

	part = transferInvest(part);// 감가비 이관 및 재계산

	part = configStdOption(part); // 산출옵션 적용

	// part = configRnd(part); // r&d 비율 초기화 적용
	return part;

    }

    public CostPart configRnd(CostPart part) throws Exception {

	CostPart tempPart = part;
	CostPart parent = null;

	boolean isRndOption = false;

	while (tempPart != null) {
	    parent = tempPart;
	    tempPart = (CostPart) tempPart.getParent();

	    if (tempPart != null) {
		if (StringUtils.isNotEmpty(tempPart.getPartType())) {
		    String partType = "ext.ket.cost.entity.CostPartType:" + tempPart.getPartType();
		    CostPartType type = (CostPartType) CommonUtil.getObject(partType);

		    if ("Insert(수평)".equals(type.getName()) || "Insert(수직)".equals(type.getName()) || "TML(복합)".equals(type.getName())) {
			isRndOption = true;
		    }
		}

	    }
	}

	if (isRndOption) {// 상위 부모 중 하나라도 부품타입이 Insert(수평), Insert(수직), TML(복합) 가 있다면 R&D 비율을 0으로 만들어준다. rndExpr 을 0처리하기 위한 꼼수
	    part.setRnd("0");
	}

	return part;
    }

    public CostPart configStdOption(CostPart part) throws Exception {

	String std[] = (part.getCalcOptionMaterial() + ";" + part.getCalcOptionManage()).split(";");
	NumberCode num = null;

	boolean packOption = false;
	boolean apUnitOption = false;
	boolean coManageOption = false;
	boolean subCostOption = false;
	boolean rndOption = false;
	boolean subCostAllTotalOption = false;

	for (String option : std) {
	    num = CodeHelper.manager.getNumberCode("CALCULATIONSTD", option);
	    if (num != null) {
		if ("포장비".equals(num.getName())) {
		    packOption = true;
		}
		if ("후도금".equals(num.getName())) {
		    apUnitOption = true;
		}
		if ("지정품".equals(num.getName())) {
		    subCostOption = true;
		}
		if ("일관비".equals(num.getName())) {
		    coManageOption = true;
		}
		if ("R&D율".equals(num.getName())) {
		    rndOption = true;
		}

		if ("부품합계".equals(num.getName())) {
		    subCostAllTotalOption = true;
		}

	    }
	}

	if (packOption) {// 포장비 옵션 세팅
	    part.setPackUnitPriceOption(part.getPackUnitPriceSum());

	    // 포장비 옵션 선택시 포장비 수식 호출하여 포장비 재계산(털어내기)
	    Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
	    String exp = (String) CostCalculateUtil.manager.getAttrCaluateFormula(partMap, "packUnitPriceSum").get("resultFormula");

	    if (StringUtils.isNotEmpty(exp)) {// 없는 수식을 호출한다면 처리하지 않기 위해
		String packUnitPriceSum = (String) CostCalculateUtil.manager.eval(exp);
		part.setPackUnitPriceSum(packUnitPriceSum);
	    }

	} else {
	    part.setPackUnitPriceOption("0");
	}

	if (apUnitOption) {// 후도금비 옵션 세팅
	    String exp = part.getApUnitCost() + "*" + part.getApMonetaryUnitCurrency(); // 후도금단가 * 후도금 환율
	    String apUnitCostOption = (String) CostCalculateUtil.manager.eval(exp);
	    part.setApUnitCostOption(apUnitCostOption);
	} else {
	    part.setApUnitCostOption("0");
	}

	if (coManageOption) {// 일반관리비율 옵션 세팅
	    part.setCoManageRateOption(part.getCoManageRate());
	} else {
	    part.setCoManageRateOption("0");
	}

	if (subCostOption) {// 지정품합계 옵션 세팅
	    part.setSubCostExceptTotalOption(part.getSubCostExceptTotal());
	} else {
	    part.setSubCostExceptTotalOption("0");
	}

	if (rndOption) {// R&D율 옵션 세팅
	    part.setRndOption(part.getRnd());
	} else {
	    part.setRndOption("0");
	}

	if (subCostAllTotalOption) {// 부품합계 옵션 세팅
	    part.setSubCostAllTotalOption(part.getSubCostAllTotal());
	} else {
	    part.setSubCostAllTotalOption("0");
	}

	return part;
    }

    public CostPart logiticsPartRtn(CostPart part, CostLogistics obj) throws Exception {
	part.setPartLtRate(obj.getRate()); // 관세율
	part.setPartLtCost(obj.getCost()); // 물류비
	part.setP_pallet_container(obj.getP_container()); // 부품_pallet/Container
	return part;
    }

    public void syncLogitics(CostPart part) throws Exception {
	Map<String, Object> reqMap = new HashMap<String, Object>();

	reqMap.put("SEARCHTYPE", "M"); // 원재료관세,물류비

	QueryResult qr = this.getCostLogiticsQuery(reqMap);
	CostPart parent = null;

	// ***** 원재료 관세, 물류비 세팅 시작 *******************************//
	// while (qr.hasMoreElements()) {// 원재료 관세, 물류비 세팅
	//
	// boolean isCorrect = false;
	//
	// CostLogistics obj = (CostLogistics) qr.nextElement();
	//
	// String L_fac1 = CommonUtil.getOIDLongValue2Str(obj.getFactory1());
	// String L_fac2 = CommonUtil.getOIDLongValue2Str(obj.getFactory2());
	// String L_fac3 = CommonUtil.getOIDLongValue2Str(obj.getFactory3());
	//
	// String part_fac1 = part.getCostDeliver();
	//
	// NumberCode num = CodeHelper.manager.getNumberCode("MFTFACTORY", part_fac1);
	//
	// if (num != null) {
	// part_fac1 = CommonUtil.getOIDLongValue2Str(num);
	// }
	//
	// String part_fac2 = part.getMftFactory1();
	//
	// if ((L_fac1 + L_fac2).equals(part_fac1 + part_fac2)) {// 1. part의 원재료 입고처(중국,국내) , part의 생산국(중국,국내) 와 기준정보의 1단계,2단계 비교
	//
	// parent = (CostPart) part.getParent();
	//
	// if (parent != null) {
	// if (parent.getMftFactory1().equals(L_fac3)) {// 2. part의 부모의 생산국과 기준정보의 3단계 비교
	// isCorrect = true;
	// }
	// } else {
	// // parent = getRootPart(part);
	// String payPlace = "";
	// if (StringUtils.isNotEmpty(part.getPayPlace())) {
	// payPlace = CommonUtil.getOIDLongValue2Str(part.getPayPlace());
	// }
	// if (payPlace.equals(L_fac3)) { // 3. 부모가 없다면 root이므로 제품이라는 뜻 자신의 납입처와 기준정보의 3단계 비교
	// isCorrect = true;
	// }
	// }
	//
	// }
	//
	// if (isCorrect) {
	// part.setMtrLtRate(obj.getRate()); // 관세율
	// part.setMtrLtCost(obj.getCost()); // 물류비
	//
	// PersistenceHelper.manager.save(part);
	// }
	//
	// }

	F1: while (qr.hasMoreElements()) {// 원재료 관세, 물류비 세팅
	    CostLogistics obj = (CostLogistics) qr.nextElement();
	    String mft1 = obj.getMft1();
	    String mft2 = obj.getMft2();
	    String mft3 = obj.getMft3();

	    String part_deliver = part.getCostDeliver();// part의 원재료입고처
	    NumberCode num = NumberCodeHelper.manager.getNumberCode("MFTFACTORY", part_deliver);
	    if (num != null) {
		part_deliver = CommonUtil.getOIDLongValue2Str(num);
	    }

	    String part_fac1 = part.getMftFactory1();
	    String part_fac2 = part.getMftFactory2();

	    if (mft1.equals(part_deliver) && mft2.equals(part_fac1)) {
		if (StringUtils.isNotEmpty(mft3)) {
		    String logiticsMft3[] = mft3.split(";");
		    for (String mft3_ : logiticsMft3) {
			if (mft3_.equals(part_fac2)) {
			    part.setMtrLtRate(obj.getRate()); // 관세율
			    part.setMtrLtCost(obj.getCost()); // 물류비
			    part.setM_pallet_container(obj.getP_container()); // 원재료_pallet/Container
			    PersistenceHelper.manager.save(part);
			    break F1;
			}

		    }
		}
	    }
	}

	// ***** 원재료 관세, 물류비 세팅 종료 *******************************//

	// ***** 부품 관세, 물류비 세팅 시작 *******************************//
	parent = (CostPart) part.getParent();
	reqMap.put("SEARCHTYPE", "P"); // 부품 관세,물류비

	qr = this.getCostLogiticsQuery(reqMap);

	F2: while (qr.hasMoreElements()) {// 부품 관세, 물류비 세팅
	    CostLogistics obj = (CostLogistics) qr.nextElement();
	    String mft1 = obj.getMft1();
	    String mft2 = obj.getMft2();
	    String mft3 = obj.getMft3();
	    String mft4 = obj.getMft4();

	    String part_fac1 = part.getMftFactory1();
	    String part_fac2 = part.getMftFactory2();
	    // if (StringUtils.isNotEmpty(payPlace)) {
	    // payPlace = CommonUtil.getOIDLongValue2Str(payPlace);
	    // }

	    String parent_part_fac1 = "";
	    String parent_part_fac2 = "";

	    if (part.getParent() != null) {
		parent_part_fac1 = ((CostPart) part.getParent()).getMftFactory1();
		parent_part_fac2 = ((CostPart) part.getParent()).getMftFactory2();
	    } else {
		QueryResult QuantityQr = PersistenceHelper.manager.navigate(part, "costQuantity", costPartQtyLink.class, true);

		while (QuantityQr.hasMoreElements()) {

		    CostQuantity qty = (CostQuantity) QuantityQr.nextElement();

		    if ("1".equals(qty.getLastest())) {
			parent_part_fac1 = CommonUtil.getOIDLongValue2Str(qty.getPayPlace());
			parent_part_fac2 = "Empty";
			break;
		    }

		}
	    }

	    if (mft1.equals(part_fac1)) {// 기준정보 생산국과 부품의 생산국 비교

		boolean onelevelFlag = false;

		if (StringUtils.isNotEmpty(mft1) && mft1.equals(part_fac1)) {// 기준정보 부품생산지와 부품의 생산지 비교
		    String logiticsMft2[] = mft2.split(";");

		    for (String mft2_ : logiticsMft2) {// 기준정보 부품 생산지와 부품의 생산지 비교

			if (mft2_.equals(part_fac2)) {
			    onelevelFlag = true;
			    break;
			}

		    }
		}

		if (onelevelFlag && mft3.equals(parent_part_fac1)) {// 기준정보 상위부품 생산국과 상위부품 생산지 비교
		    if ("Empty".equals(parent_part_fac2)) {// 상위부품이 없을 때 예상판매량 최종안의 입고처만 비교한다 생산지 비교안함
			part = logiticsPartRtn(part, obj);
			PersistenceHelper.manager.save(part);
			break F2;
		    } else {
			String logiticsMft4[] = mft4.split(";");
			for (String mft4_ : logiticsMft4) {// 기준정보 상위부품 생산지와 부품의 생산지 비교

			    if (mft4_.equals(parent_part_fac2)) {

				part = logiticsPartRtn(part, obj);
				PersistenceHelper.manager.save(part);
				break F2;
			    }

			}
		    }

		}
	    }
	}

	// parent = null;
	//
	// // ***** 부품 관세, 물류비 세팅 시작 *******************************//
	// while (qr.hasMoreElements()) {// 부품 관세, 물류비 세팅
	// CostPart root = null;
	//
	// boolean isCorrect = false;
	//
	// CostLogistics obj = (CostLogistics) qr.nextElement();
	//
	// String L_fac1 = CommonUtil.getOIDLongValue2Str(obj.getFactory1());
	// String L_fac2 = CommonUtil.getOIDLongValue2Str(obj.getFactory2());
	// String L_fac3 = "";
	//
	// if (obj.getFactory3() != null) {
	// L_fac3 = CommonUtil.getOIDLongValue2Str(obj.getFactory3());
	// }
	//
	// String part_fac1 = part.getMftFactory1();
	//
	// if ((L_fac1).equals(part_fac1)) {// 1. part의 생산국(중국,국내) 과 기준정보의 1단계 비교
	//
	// parent = (CostPart) part.getParent();
	//
	// if (parent != null) {
	//
	// if (parent.getMftFactory1().equals(L_fac2)) {// 2. part의 부모의 생산국과 기준정보의 2단계 비교
	//
	// root = (CostPart) parent.getParent();
	//
	// if (root != null) {
	//
	// if (root.getMftFactory1().equals(L_fac3)) { // 3. 부모의 부모(제품) 와 기준정보의 3단계 비교
	// isCorrect = true;
	// }
	//
	// } else {
	//
	// String payPlace = "";
	// if (StringUtils.isNotEmpty(parent.getPayPlace())) {
	// payPlace = CommonUtil.getOIDLongValue2Str(parent.getPayPlace());
	// }
	//
	// if (payPlace.equals(L_fac2)) { // 3.부모(1레벨 제품)와 기준정보의 2단계 비교
	// isCorrect = true;
	// }
	// }
	// }
	//
	// } else {
	//
	// String payPlace = "";
	// if (StringUtils.isNotEmpty(part.getPayPlace())) {
	// payPlace = CommonUtil.getOIDLongValue2Str(part.getPayPlace());
	// }
	//
	// if (payPlace.equals(L_fac2)) { // 3. 부모가 없다면 root이므로 제품이라는 뜻 자신의 납입처와 기준정보의 2단계 비교
	// isCorrect = true;
	// }
	// }
	//
	// }
	//
	// if (isCorrect) {
	// part.setPartLtRate(obj.getRate()); // 관세율
	// part.setPartLtCost(obj.getCost()); // 물류비
	//
	// PersistenceHelper.manager.save(part);
	// }
	//
	// }

	// ***** 원재료 관세, 물류비 세팅 종료 *******************************//

    }

    public String transCharacter(String sing) {
	if (StringUtils.contains(sing, "gt")) {
	    if (StringUtils.contains(sing, "=")) {
		sing = ">=";
	    } else {
		sing = ">";
	    }
	} else if (StringUtils.contains(sing, "lt")) {
	    if (StringUtils.contains(sing, "=")) {
		sing = "<=";
	    } else {
		sing = "<";
	    }
	}
	return sing;
    }

    @Override
    public boolean isFirstCostRequset(String ProjectOid, String taskOid) throws Exception {
	// TODO Auto-generated method stub

	E3PSProject project = (E3PSProject) CommonUtil.getObject(ProjectOid);

	String targetPjt = "";
	String targetTaskOid = CommonUtil.getOIDLongValue2Str(taskOid);

	if (project instanceof ReviewProject) {
	    targetPjt = "ReviewProject";

	} else if (project instanceof ProductProject) {
	    targetPjt = "ProductProject";
	    return false;
	}

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT TASKNAME,TASKTYPE,TASKTYPECATEGORY,IDA2A2		              \n");
	    sb.append("   FROM (SELECT T.* FROM E3PSTASK T," + targetPjt + " P,EXTENDSCHEDULEDATA S         \n");
	    sb.append("           WHERE T.IDA3B4 = P.IDA2A2                                           \n");
	    sb.append("             AND T.IDA3A4 = S.IDA2A2                                           \n");
	    sb.append("         START WITH T.IDA3PARENTREFERENCE = 0                                  \n");
	    sb.append("             AND P.IDA2A2 = " + CommonUtil.getOIDLongValue(ProjectOid) + "         \n");
	    sb.append("         CONNECT BY PRIOR T.IDA2A2 = T.IDA3PARENTREFERENCE                     \n");
	    sb.append("         ORDER SIBLINGS BY T.TASKSEQ)                                          \n");

	    String query = sb.toString();

	    taskList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> taskMap = new HashMap<String, Object>();

		    taskMap.put("TASKNAME", rs.getString("TASKNAME"));
		    taskMap.put("TASKTYPE", StringUtil.checkNull(rs.getString("TASKTYPE")));
		    taskMap.put("TASKTYPECATEGORY", StringUtil.checkNull(rs.getString("TASKTYPECATEGORY")));
		    taskMap.put("IDA2A2", StringUtil.checkNull(rs.getString("IDA2A2")));

		    return taskMap;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	boolean rtnFlag = false;
	boolean isFirst = false;

	// 원가산출요청이 해당 검토프로젝트에서 최초인지 판단
	for (Map<String, Object> task : taskList) {
	    // String taskName = (String)task.get("TASKNAME");
	    String taskType = (String) task.get("TASKTYPE");
	    String category = (String) task.get("TASKTYPECATEGORY");
	    String ida2a2 = (String) task.get("IDA2A2");

	    if (!isFirst && taskType.toUpperCase().equals("COST") && "COST013".equals(category)) {
		isFirst = true;
		if (ida2a2.equals(targetTaskOid)) {
		    rtnFlag = true;
		    break;
		}
	    }

	    if (isFirst) {
		break;
	    }

	}

	return rtnFlag;
    }

    @Override
    public String getDrStepByTask(String ProjectOid, String taskOid) throws Exception {
	// TODO Auto-generated method stub

	E3PSProject project = (E3PSProject) CommonUtil.getObject(ProjectOid);

	String targetPjt = "";
	String targetTaskOid = CommonUtil.getOIDLongValue2Str(taskOid);

	if (project instanceof ReviewProject) {
	    targetPjt = "ReviewProject";
	    return "DR0";
	} else if (project instanceof ProductProject) {
	    targetPjt = "ProductProject";
	}

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT TASKNAME,TASKTYPE,TASKTYPECATEGORY,IDA2A2		              \n");
	    sb.append("   FROM (SELECT T.* FROM E3PSTASK T," + targetPjt + " P,EXTENDSCHEDULEDATA S         \n");
	    sb.append("           WHERE T.IDA3B4 = P.IDA2A2                                           \n");
	    sb.append("             AND T.IDA3A4 = S.IDA2A2                                           \n");
	    sb.append("         START WITH T.IDA3PARENTREFERENCE = 0                                  \n");
	    sb.append("             AND P.IDA2A2 = " + CommonUtil.getOIDLongValue(ProjectOid) + "         \n");
	    sb.append("         CONNECT BY PRIOR T.IDA2A2 = T.IDA3PARENTREFERENCE                     \n");
	    sb.append("         ORDER SIBLINGS BY T.TASKSEQ)                                          \n");

	    String query = sb.toString();

	    taskList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> taskMap = new HashMap<String, Object>();

		    taskMap.put("TASKNAME", rs.getString("TASKNAME"));
		    taskMap.put("TASKTYPE", StringUtil.checkNull(rs.getString("TASKTYPE")));
		    taskMap.put("TASKTYPECATEGORY", StringUtil.checkNull(rs.getString("TASKTYPECATEGORY")));
		    taskMap.put("IDA2A2", StringUtil.checkNull(rs.getString("IDA2A2")));

		    return taskMap;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	String DrStep = "DR1";
	// 해당 task의 DR단계 찾기
	for (Map<String, Object> task : taskList) {
	    // String taskName = (String)task.get("TASKNAME");
	    String taskType = (String) task.get("TASKTYPE");
	    String category = (String) task.get("TASKTYPECATEGORY");
	    String ida2a2 = (String) task.get("IDA2A2");

	    String categoryNumber = StringUtil.getNumber(category);

	    if (taskType.toUpperCase().equals("DR") && !"-".equals(category) && StringUtils.isNotEmpty(category)
		    && StringUtils.isNotEmpty(categoryNumber)) {
		DrStep = taskType.toUpperCase() + category;
	    }

	    if (ida2a2.equals(targetTaskOid)) {
		break;
	    }
	}

	return DrStep;
    }

    @Override
    public List<Map<String, Object>> getCompareCostBasicInfo(CostPart beforePart, CostPart afterPart) throws Exception {
	// TODO Auto-generated method stub

	int before_version = beforePart.getVersion();
	int before_subVersion = beforePart.getSubVersion();
	// String before_pjtNo[] = beforePart.getPartNo().split("-");
	String before_pjtNo = beforePart.getProject().getPjtNo();
	ProductMaster before_master = getMyProductMaster(beforePart);
	Long before_masterOid = CommonUtil.getOIDLongValue(before_master);

	int after_version = afterPart.getVersion();
	int after_subVersion = afterPart.getSubVersion();
	// String after_pjtNo[] = afterPart.getPartNo().split("-");
	String after_pjtNo = afterPart.getProject().getPjtNo();
	ProductMaster after_master = getMyProductMaster(afterPart);
	Long after_masterOid = CommonUtil.getOIDLongValue(after_master);

	Long before_partOid = CommonUtil.getOIDLongValue(beforePart);
	Long after_partOid = CommonUtil.getOIDLongValue(afterPart);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> ResultList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT '환율' AS DIVISION, NAME,BEFORE_VAL,AFTER_VAL,(AFTER_VAL-BEFORE_VAL) AS CHANGE_VAL 																						\n");
	    sb.append("   FROM (                                                                                                                                                    \n");
	    sb.append("         SELECT NAME, (SELECT VALUE2 FROM COSTCURRENCYINFO WHERE VERSION = " + before_version + " AND SUBVERSION = "
		    + before_subVersion + " AND PJTNO = '" + before_pjtNo + "' AND IDA3A4 = " + before_masterOid
		    + " AND VALUE1 = A.VALUE1) AS BEFORE_VAL, VALUE2 AS AFTER_VAL       \n");
	    sb.append("           FROM (                                                                                                                                            \n");
	    sb.append("                 SELECT NAME,VALUE1,VALUE2                                                                                                                   \n");
	    sb.append("                   FROM COSTCURRENCYINFO                                                                                                                     \n");
	    sb.append("                  WHERE VERSION = " + after_version + " AND SUBVERSION = " + after_subVersion + " AND PJTNO = '"
		    + after_pjtNo + "' AND IDA3A4 = " + after_masterOid + " \n");
	    sb.append("                  MINUS                                                                                                                                      \n");
	    sb.append("                 SELECT NAME,VALUE1,VALUE2 FROM COSTCURRENCYINFO                                                                                             \n");
	    sb.append("                  WHERE VERSION = " + before_version + " AND SUBVERSION = " + before_subVersion + " AND PJTNO = '"
		    + before_pjtNo + "' AND IDA3A4 = " + before_masterOid + " \n");
	    sb.append("                ) A                                                                                                                                          \n");
	    sb.append("        )                                                                                                                                                    \n");
	    sb.append("  UNION ALL                                                                                                                                                      \n");
	    sb.append(" SELECT '예상판매량' AS DIVISION, NAME,BEFORE_VAL,AFTER_VAL,(AFTER_VAL-BEFORE_VAL) AS CHANGE_VAL                                                                                       \n");
	    sb.append("   FROM (                                                                                                                                                    \n");
	    sb.append("         SELECT 'Total' AS NAME, QUANTITYTOTAL AS BEFORE_VAL,(SELECT QUANTITYTOTAL FROM COSTPART WHERE IDA2A2 = "
		    + after_partOid + ") AS AFTER_VAL                     \n");
	    sb.append("           FROM COSTPART                                                                                                                                     \n");
	    sb.append("          WHERE IDA2A2 = "
		    + before_partOid
		    + "                                                                                                                         \n");
	    sb.append("      UNION ALL                                                                                                                                              \n");
	    sb.append("         SELECT 'Max' AS NAME, QUANTITYMAX AS BEFORE_VAL, (SELECT QUANTITYMAX FROM COSTPART WHERE IDA2A2 = "
		    + after_partOid + ") AS AFTER_VAL                          \n");
	    sb.append("           FROM COSTPART                                                                                                                                     \n");
	    sb.append("          WHERE IDA2A2 = "
		    + before_partOid
		    + "                                                                                                                        \n");
	    sb.append("        )                                                                                                                                                    \n");

	    String query = sb.toString();

	    ResultList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> resultMap = new HashMap<String, Object>();

		    resultMap.put("DIVISION", rs.getString("DIVISION"));
		    resultMap.put("NAME", rs.getString("NAME"));
		    resultMap.put("BEFORE_VAL", StringUtil.checkNull(rs.getString("BEFORE_VAL")));
		    resultMap.put("AFTER_VAL", StringUtil.checkNull(rs.getString("AFTER_VAL")));
		    resultMap.put("CHANGE_VAL", StringUtil.checkNull(rs.getString("CHANGE_VAL")));

		    return resultMap;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return ResultList;
    }

    @Override
    public List<Map<String, Object>> getCostWorkTimeList() throws Exception {
	// TODO Auto-generated method stub
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT A.NAME AS NAME                                                                                           ");
	    sb.append(" 	  , A.CODE AS CODE                                                                                          ");
	    sb.append(" 	  , B.partTypeOid OID                                                                  ");
	    sb.append("       , DECODE( A.IDA3PARENTREFERENCE, 0, '', A.CLASSNAMEA2A2 || ':' || A.IDA3PARENTREFERENCE ) PARENTOID       ");
	    sb.append("       , B.CLASSNAMEA2A2||':'||B.IDA2A2 AS id                                                                    ");
	    sb.append("       , B.MFTFACTORY1 mftfactory1                                                                               ");
	    sb.append("       , B.MFTFACTORY2 AS mftfactory2                                                                            ");
	    sb.append("       , (SELECT LTRIM (SYS_CONNECT_BY_PATH (TREE.NAME, ' / '), ' / ') AS TREEFULLPATH FROM COSTPARTTYPE TREE    ");
	    sb.append("           WHERE CONNECT_BY_ISLEAF = 1                                                                           ");
	    sb.append("             AND TREE.CLASSNAMEA2A2||':'||TREE.IDA2A2 = B.PARTTYPEOID                                                                     ");
	    sb.append("           START WITH TREE.IDA3PARENTREFERENCE = 0                                                               ");
	    sb.append("         CONNECT BY PRIOR TREE.IDA2A2 = TREE.IDA3PARENTREFERENCE) AS TREEFULLPATH                                ");
	    sb.append("       , B.WORKDAY                                                                                               ");
	    sb.append("       , B.WORKHOUR                                                                                              ");
	    sb.append("       , B.WORKYEAR                                                                                              ");
	    sb.append("       , B.CAPAYEAR                                                                                              ");
	    sb.append("       , B.CLASSNAMEA2A2||':'||B.IDA2A2 AS WORKTIMEOID                                                                                              ");
	    sb.append("  FROM COSTPARTTYPE A, COSTWORKTIMECONFIG B                                                                      ");
	    sb.append(" WHERE A.CLASSNAMEA2A2||':'||A.IDA2A2 = B.PARTTYPEOID                                                                               ");
	    sb.append(" ORDER BY B.SORTLOCATION                                                                                         ");

	    String query = sb.toString();

	    tempList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    CostWorkTimeConfig workTime = (CostWorkTimeConfig) CommonUtil.getObject(rs.getString("ID"));

		    Map<String, Object> costPartTypeVO = new HashMap<String, Object>();
		    try {
			costPartTypeVO = CostUtil.manager.converObjectToMap(workTime);

		    } catch (Exception e) {
			e.printStackTrace();
		    }

		    costPartTypeVO.put("id", rs.getString("ID"));
		    costPartTypeVO.put("partTypeName", StringUtil.checkNull(rs.getString("NAME")));
		    costPartTypeVO.put("partTypeNameRowSpan", 2);
		    costPartTypeVO.put("partTypeOid", StringUtil.checkNull(rs.getString("OID")));
		    costPartTypeVO.put("partTypeParentOid", StringUtil.checkNull(rs.getString("PARENTOID")));
		    costPartTypeVO.put("treeFullFath", StringUtil.checkNull(rs.getString("TREEFULLPATH")));
		    costPartTypeVO.put("mftFactory1", StringUtil.checkNull(rs.getString("mftfactory1")));
		    costPartTypeVO.put("mftFactory2", StringUtil.checkNull(rs.getString("mftfactory2")));
		    costPartTypeVO.put("worktimeoid", StringUtil.checkNull(rs.getString("WORKTIMEOID")));
		    costPartTypeVO.put("deleteFlag", "0");

		    costPartTypeVO.put("partTypeNameIcon", "/plm/portal/images/icon_5.png");
		    costPartTypeVO.put("partTypeNameIconAlign", "Right");
		    costPartTypeVO.put("partTypeNameOnClickSideIcon", "javascript:openGridPopup(Row);");
		    costPartTypeVO.put("partTypeNameButton", "/plm/portal/images/icon_delete.gif");
		    costPartTypeVO.put("partTypeNameOnClickSideButton", "javasciprt:deletePopupValue(Row);");

		    return costPartTypeVO;
		}
	    });
	} catch (Exception e) {
	    throw e;
	} finally {

	}

	/*
	 * Map<String, Object> dataMap= null;
	 * 
	 * ArrayList<Map<String, Object>> partTypeList = new ArrayList<Map<String, Object>>(); for(Map<String, Object> data : tempList){
	 * ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); list.add(data);
	 * 
	 * dataMap = new HashMap<String, Object>(); dataMap.put("Items", list); partTypeList.add(dataMap); }
	 */

	return tempList;

    }

    @Override
    public Map<String, Object> saveCostWorkTime(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");
	Transaction transaction = new Transaction();
	Map<String, Object> sortTemp = null;
	try {

	    transaction.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");
		List<String> sortList = new ArrayList<String>();
		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("id");

		    CostWorkTimeConfig workTime = null;

		    if (id.indexOf(CostWorkTimeConfig.class.getName()) >= 0) {
			workTime = (CostWorkTimeConfig) CommonUtil.getObject(id);
		    } else {
			if (!deleted) {
			    workTime = CostWorkTimeConfig.newCostWorkTimeConfig();
			}
			// costPartTypeLink = partTypeCodeLink.newpartTypeCodeLink(costPartType, code);
		    }

		    if (!deleted) {

			workTime.setOwner(SessionHelper.manager.getPrincipalReference());
			CostUtil.manager.convertMapToObject(data, workTime);

			workTime = (CostWorkTimeConfig) PersistenceHelper.manager.save(workTime);
		    }

		    sortTemp = new HashMap<String, Object>();
		    String oid = CommonUtil.getOIDString(workTime);
		    sortTemp.put(id, oid);

		    this.setSortLocation(items, sortList);

		    if (deleted) {
			if (workTime != null) {
			    PersistenceHelper.manager.delete(workTime);
			}

		    }

		    // costPartType = (CostPartType) PersistenceHelper.manager.save(costPartType);

		}

		// 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		for (int i = 0; i < sortList.size(); i++) {
		    String itemId = sortList.get(i);

		    if (itemId.indexOf(CostWorkTimeConfig.class.getName()) < 0) {
			itemId = (String) sortTemp.get(itemId);
		    }

		    CostWorkTimeConfig worktime = (CostWorkTimeConfig) CommonUtil.getObject(itemId);
		    if (worktime != null) {
			worktime.setSortLocation(i);
			PersistenceHelper.manager.save(worktime);
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

    @Override
    public boolean isOpenBomEditor(String taskOid) throws Exception {
	// TODO Auto-generated method stub

	// task에서 bomeditor를 열때 해당 task(개발검토bom,원가산출요청 - 해당 task 2개는 새로운 버전의 costpart를 생성할 우려가있기때문이다 )의 상위 task 중 완료되지 않은 원가Task가 있는지 판단하는
	// 로직
	// 해당 task의 costversion 과 partlist의 마지막 verion을 비교하여 최신버전이 아니라면 해당 로직이 동작한다
	E3PSTask source = (E3PSTask) CommonUtil.getObject(taskOid);

	String taskCategory = source.getTaskTypeCategory();
	boolean isOpen = true;

	if ("COST001".equals(taskCategory) || "COST013".equals(taskCategory)) {

	    E3PSProject project = (E3PSProject) source.getProject();
	    String ProjectOid = CommonUtil.getOIDString(project);

	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	    PartList partList = CostUtil.manager.getPartList(pjtMaster);

	    int lastestVersion = 0;

	    if (partList != null) {

		lastestVersion = partList.getLastestVersion();

		// if (Integer.parseInt(source.getCostVersion()) < lastestVersion) {

		String targetPjt = "";
		String targetTaskOid = CommonUtil.getOIDLongValue2Str(taskOid);

		if (project instanceof ReviewProject) {
		    targetPjt = "ReviewProject";
		} else if (project instanceof ProductProject) {
		    targetPjt = "ProductProject";
		}

		DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();

		try {

		    StringBuffer sb = new StringBuffer();

		    sb.append(" SELECT TASKNAME,TASKTYPE,TASKTYPECATEGORY,TASKSTATE,IDA2A2		              \n");
		    sb.append("   FROM (SELECT T.* FROM E3PSTASK T," + targetPjt + " P,EXTENDSCHEDULEDATA S         \n");
		    sb.append("           WHERE T.IDA3B4 = P.IDA2A2                                           \n");
		    sb.append("             AND T.IDA3A4 = S.IDA2A2                                           \n");
		    sb.append("         START WITH T.IDA3PARENTREFERENCE = 0                                  \n");
		    sb.append("             AND P.IDA2A2 = " + CommonUtil.getOIDLongValue(ProjectOid) + "         \n");
		    sb.append("         CONNECT BY PRIOR T.IDA2A2 = T.IDA3PARENTREFERENCE                     \n");
		    sb.append("         ORDER SIBLINGS BY T.TASKSEQ)                                          \n");

		    String query = sb.toString();

		    taskList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

			    Map<String, Object> taskMap = new HashMap<String, Object>();

			    taskMap.put("TASKNAME", rs.getString("TASKNAME"));
			    taskMap.put("TASKTYPE", StringUtil.checkNull(rs.getString("TASKTYPE")));
			    taskMap.put("TASKTYPECATEGORY", StringUtil.checkNull(rs.getString("TASKTYPECATEGORY")));
			    taskMap.put("TASKSTATE", StringUtil.checkNull(rs.getString("TASKSTATE")));
			    taskMap.put("IDA2A2", StringUtil.checkNull(rs.getString("IDA2A2")));

			    return taskMap;
			}
		    });

		} catch (Exception e) {
		    throw e;
		} finally {

		}

		for (Map<String, Object> task : taskList) {
		    // String taskName = (String)task.get("TASKNAME");
		    String taskType = (String) task.get("TASKTYPE");
		    // String category = (String) task.get("TASKTYPECATEGORY");
		    String ida2a2 = (String) task.get("IDA2A2");
		    String taskState = (String) task.get("TASKSTATE");
		    if (ida2a2.equals(targetTaskOid)) {
			break;
		    } else {
			if (taskType.toUpperCase().equals("COST") && !"5".equals(taskState)) {
			    isOpen = false;
			    break;
			}
		    }
		}
		// }
	    } else {
		// 검토프로젝트일 때만 체크 (제품프로젝트는 원가요청서부터 출발하므로)
		if (project != null && project instanceof ReviewProject && "COST013".equals(taskCategory)) {
		    isOpen = false;
		}
	    }
	}

	return isOpen;
    }

    public List<Map<String, Object>> createErpBom(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> erpBomList = new ArrayList<Map<String, Object>>();

	String leftPartOid = (String) reqMap.get("leftPartOid");
	CostPart rootPart = (CostPart) CommonUtil.getObject(leftPartOid);

	List<CostPart> partList = CostTreeUtil.manager.getCostPartList(rootPart);

	Random gen = new Random();

	int cnt = 1;
	System.out.println("############## ERP BOM TEST DATA SETTING START ########################");
	for (CostPart part : partList) {

	    part.setPartName("양산품" + cnt++);

	    Map<String, Object> erpBomMap = CostUtil.manager.converObjectToMap(part);
	    erpBomMap.put("realPartNo", part.getPartNo());

	    CostPart parent = (CostPart) part.getParent();

	    if (parent == null) {
		erpBomMap.put("parentPartNo", "");
	    } else {
		erpBomMap.put("parentPartNo", parent.getPartNo());
	    }

	    // 0 ~ 5.999999
	    float genFloatValue = gen.nextFloat() * 6;
	    erpBomMap.put("assyLossRate", String.valueOf(genFloatValue));

	    // 1~5
	    int genIntValue = gen.nextInt(5) + 1;
	    erpBomMap.put("us", String.valueOf(genIntValue));

	    // 100~600
	    genIntValue = gen.nextInt(500) + 100;
	    erpBomMap.put("pUnitCost", String.valueOf(genIntValue));

	    // 1~10
	    genIntValue = gen.nextInt(10) + 1;
	    erpBomMap.put("worker", String.valueOf(genIntValue));

	    genFloatValue = gen.nextFloat() * 500;
	    erpBomMap.put("outUnitCost", String.valueOf(genFloatValue));
	    System.out.println("#################################################################");
	    System.out.println("############ realPartNo   : " + erpBomMap.get("realPartNo"));
	    System.out.println("############ assyLossRate : " + erpBomMap.get("assyLossRate"));
	    System.out.println("############ us			  : " + erpBomMap.get("us"));
	    System.out.println("############ pUnitCost	  : " + erpBomMap.get("pUnitCost"));
	    System.out.println("############ worker		  : " + erpBomMap.get("worker"));
	    System.out.println("############ outUnitCost  : " + erpBomMap.get("outUnitCost"));
	    erpBomList.add(erpBomMap);

	}
	System.out.println("############## ERP BOM TEST DATA SETTING END ########################");

	return erpBomList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CostPart> erpBom2CostPart(Map<String, Object> paramMap) throws Exception {

	String partNo = (String) paramMap.get("partNo");
	List<CostPart> partList = (List<CostPart>) paramMap.get("partList");

	List<Map<String, Object>> erpBomList = ErpBomInterface.getErpBomInfo(paramMap);

	List<CostPart> dataList = new ArrayList<CostPart>();

	Map<String, CostPart> plmPartMap = new HashMap<String, CostPart>();

	for (CostPart plmPart : partList) {
	    plmPartMap.put(plmPart.getRealPartNo(), plmPart);
	}
	CostPart rootPlmPart = plmPartMap.get(partNo);
	rootPlmPart.setSortLocation(0);
	int formulaVersion = rootPlmPart.getFormulaVersion();

	if (erpBomList.size() == 0 || erpBomList == null) {
	    return dataList;
	}
	// List<Map<String, Object>> erpBomList = createErpBom(paramMap);

	Map<String, DefaultMutableTreeNode> parentMap = new HashMap<String, DefaultMutableTreeNode>();

	ObjectUtil.manager.convertMapToObject(erpBomList.get(0), rootPlmPart);

	DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootPlmPart);

	parentMap.put(rootPlmPart.getRealPartNo(), root);

	int sortLocation = 1;

	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumIntegerDigits(3);

	for (Map<String, Object> erpBom : erpBomList) {

	    partNo = (String) erpBom.get("realPartNo");

	    if (partNo.equals(rootPlmPart.getRealPartNo())) {
		continue;
	    }

	    String parentPartNo = (String) erpBom.get("parentPartNo");

	    CostPart part = plmPartMap.get(partNo);

	    if (part != null) {

		CostPartType partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + part.getPartType());

		String cv = (String) erpBom.get("cv");
		String ctSpm = "0";

		String needChild = partType.getChildCfg();

		CostPartType parentPartType = (CostPartType) partType.getParent();
		String pPartTypeName = parentPartType.getName().toUpperCase();

		if ("MOLD".equals(pPartTypeName)) {
		    ctSpm = (String) erpBom.get("ct");
		}

		if ("PRESS".equals(pPartTypeName)) {
		    ctSpm = (String) erpBom.get("spm");
		}

		if ("필수".equals(needChild)) {
		    part.setCvAssemble(cv);
		    part.setCtSPMAssemble(ctSpm);
		} else {
		    part.setCvMold(cv);
		    part.setCtSPMMold(ctSpm);
		}

	    } else {
		part = CostPart.newCostPart();
	    }

	    ObjectUtil.manager.convertMapToObject(erpBom, part);

	    part.setFormulaVersion(formulaVersion);
	    part.setSortLocation(sortLocation++);
	    DefaultMutableTreeNode child = new DefaultMutableTreeNode(part);
	    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) parentMap.get(parentPartNo);
	    part.setParent((CostPart) parent.getUserObject());

	    parent.add(child);
	    parentMap.put(partNo, child);
	}

	dataList = CostUtil.manager.syncCostPart(root, false);

	// CostPart data = (CostPart) root.getUserObject();
	// dataList.add(data);
	//
	// CostTreeUtil.manager.costPartSetting(root, dataList);

	for (CostPart part : dataList) {

	    if (part.getParent() == null) {
		System.out.println("realPartNo : " + part.getRealPartNo());
	    } else {
		System.out.println("realPartNo : " + part.getRealPartNo() + " parent : " + ((CostPart) part.getParent()).getRealPartNo());
	    }

	    System.out.println("assyLossRate : " + part.getAssyLossRate());
	    System.out.println("us : " + part.getUs());
	    System.out.println("pUnitCost : " + part.getPUnitCost());
	    System.out.println("worker : " + part.getWorker());
	    System.out.println("outUnitCost : " + part.getOutUnitCost());

	    System.out.println("materialCostExpr : " + part.getMaterialCostExpr());
	    System.out.println("laborCostExpr : " + part.getLaborCostExpr());
	    System.out.println("expenseExpr : " + part.getExpenseExpr());
	    System.out.println("manageCostExpr : " + part.getManageCostExpr());
	    System.out.println("reduceCostExpr : " + part.getReduceCostExpr());
	    System.out.println("totalCostExpr : " + part.getTotalCostExpr());

	}

	return dataList;
    }

    @Override
    @Transactional
    // 사용안함.. 환율정보는 PARTLIST별로 관리된다.
    public void copyCostCurrencyInfo(String partListOid, String version) throws Exception {

	// PartList partList = (PartList) CommonUtil.getObject(partListOid);
	//
	// if (partList == null) {
	// throw new Exception(partListOid + " 의 Object는 null입니다.");
	// }
	//
	// QueryResult qr = getCostCurrencyQuery(partListOid, version);
	//
	// int newVersion = Integer.parseInt(version) + 1;
	//
	// while (qr.hasMoreElements()) {
	// Object[] o = (Object[]) qr.nextElement();
	// CostCurrencyInfo oldCurrency = (CostCurrencyInfo) o[0];
	// CostCurrencyInfo newCurrency = (CostCurrencyInfo) HistoryHelper.duplicate(oldCurrency);
	// newCurrency.setVersion(newVersion);
	// PersistenceHelper.manager.save(newCurrency);
	// }
    }

}
