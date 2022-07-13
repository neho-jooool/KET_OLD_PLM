package ext.ket.part.classify.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETGridUtil;
import e3ps.common.util.KETParamMapUtil;
import ext.ket.edm.stamping.oxm.OxmUnmarshaller;
import ext.ket.part.classify.oxm.ClazOxmParser;
import ext.ket.part.classify.oxm.ClazXmlGridObject;
import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.classify.service.internal.PartClazCompare;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassTreeLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryLinkStrategy;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardPartClazService extends StandardManager implements PartClazService {
    private static final long serialVersionUID = 1L;

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardPartClazService newStandardPartClazService() throws WTException {
	StandardPartClazService instance = new StandardPartClazService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<PartClassificationDTO> searchFullList(boolean withUseOnly) throws Exception {
	List<PartClassificationDTO> fullRecursiveList = getClazFullList(withUseOnly);

	return fullRecursiveList;
    }

    @Override
    public String searchFullListXml(boolean withUseOnly) throws Exception {
	ClazOxmParser parser = new ClazOxmParser();
	String fullXml = parser.getClazXmlObject(searchFullList(withUseOnly));

	Kogger.debug(getClass(), "fullXml:" + fullXml);

	return fullXml;
    }

    @Override
    public String searchClazRoute(String clazOid) throws Exception {
	String fullPath = getClazRoute(clazOid);
	return fullPath;
    }

    @Override
    public KETPartClassification insertMigration(KETPartClassification child, KETPartClassification parent) throws Exception {
	child = (KETPartClassification) PersistenceHelper.manager.save(child);

	if (parent != null) {
	    KETPartClassTreeLink partClassTreeLink = KETPartClassTreeLink.newKETPartClassTreeLink(parent, child);
	    partClassTreeLink = (KETPartClassTreeLink) PersistenceHelper.manager.save(partClassTreeLink);
	}

	return child;
    }

    @Override
    public boolean saveXmlTreeData(String xmlStr) throws Exception {

	boolean ret = false;

	try {

	    OxmUnmarshaller unmarshaller = new OxmUnmarshaller();
	    ClazXmlGridObject grid = unmarshaller.getClazXmlGridObject(xmlStr);
	    PartClazCompare compare = new PartClazCompare();
	    compare.compare(grid);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return ret;

    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // private
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<PartClassificationDTO> getClazFullList(boolean withUseOnly) throws Exception {

	List<PartClassificationDTO> clazFullList = new ArrayList<PartClassificationDTO>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append("SELECT LEVEL LEV     \n");
	    sb.append(", C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID  \n");
	    sb.append(", DECODE( C.IDA3A3, 0, '', C.CLASSNAMEKEYA3 || ':' || C.IDA3A3 ) PARENTOID    \n");
	    sb.append(", DECODE((SELECT COUNT(*) FROM KETPartClassLink WHERE IDA3B5 = C.IDA2A2), 0, '0','1') AS HASPART \n");
	    sb.append(", ( SELECT LISTAGG(A.ATTRNAME, ',') WITHIN GROUP(ORDER BY A.ATTRNAME ) FROM KETPartClassAttrLink L, KETPartAttribute A "
		    + " WHERE L.IDA3A5 = A.IDA2A2  AND L.IDA3B5 = C.IDA2A2 ) partAttributeName \n");
	    sb.append(", SUBSTR(SYS_CONNECT_BY_PATH(C.CLASSKRNAME,'|'), (SELECT LENGTH(C.CLASSKRNAME)  FROM KETPartClassification C WHERE IDA3A3 = 0) + 3) AS TREEFULLPATH \n");
	    sb.append(", CONNECT_BY_ISLEAF AS leaf \n");
	    sb.append(", (SELECT N.NAMINGNAME FROM KETPartClassNamingLink L, KETPartNaming N WHERE L.IDA3A5 = N.IDA2A2 AND L.IDA3B5 = C.IDA2A2) partNamingName \n");
	    sb.append(", (SELECT N.CLASSNAMEA2A2 || ':' || N.IDA2A2 FROM KETPartClassNamingLink L, KETPartNaming N WHERE L.IDA3A5 = N.IDA2A2 AND L.IDA3B5 = C.IDA2A2) partNamingOid \n");
	    sb.append(", C.CLASSCODE        \n");
	    sb.append(", C.CLASSKRNAME       \n");
	    sb.append(", C.CLASSENNAME       \n");
	    sb.append(", C.CLASSZHNAME       \n");
	    sb.append(", C.EPMCODE           \n");
	    sb.append(", C.CLASSPARTTYPE     \n");
	    sb.append(", C.ERPPRODCODE       \n");
	    sb.append(", C.ERPPRODGROUPCODE  \n");
	    sb.append(", C.ERPCLASSNO        \n");
	    sb.append(", C.NUMBERINGCODE     \n");
	    sb.append(", C.NUMBERINGCHARICS  \n");
	    sb.append(", C.NUMBERINGMINNO    \n");
	    sb.append(", C.PARTCLASSIFICTYPE \n");
	    sb.append(", C.divisionFlag  \n");
	    sb.append(", C.catalogueCode \n");
	    sb.append(", C.SORTNO            \n");
	    sb.append(", C.USECLAZ           \n");
	    sb.append(", to_char(C.CREATESTAMPA2,'yyyy-mm-dd') CREATESTAMPA2 \n");
	    sb.append(", C.UPDATESTAMPA2     \n");
	    sb.append(", to_char(C.MODIFYSTAMPA2,'yyyy-mm-dd') MODIFYSTAMPA2 \n");
	    sb.append(", C.IDA2A2            \n");
	    sb.append(", C.IDA3A3            \n");
	    sb.append(", C.isEcoCar           \n");
	    sb.append("  FROM KETPartClassification C \n");
	    sb.append("  WHERE 1 = 1    \n");
	    if (withUseOnly) {
		sb.append("  AND C.USECLAZ = 1  \n");
	    }
	    sb.append("  START WITH IDA3A3 = 0 \n");
	    sb.append("  CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	    sb.append("  ORDER SIBLINGS BY SORTNO \n");

	    String query = sb.toString();

	    clazFullList = oDaoManager.queryForList(query, new RowSetStrategy<PartClassificationDTO>() {

		@Override
		public PartClassificationDTO mapRow(ResultSet rs) throws SQLException {

		    return new PartClazBuilder().buildResultSet2Dto(rs);
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return clazFullList;
    }

    private String getClazRoute(String clzOID) throws Exception {

	List<PartClassificationDTO> clazFullPath = new ArrayList<PartClassificationDTO>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append("SELECT SUBSTR(SYS_CONNECT_BY_PATH(C.CLASSKRNAME,'>'), (SELECT LENGTH(C.CLASSKRNAME)  FROM KETPartClassification C WHERE IDA3A3 = 0) + 3) AS TREEFULLPATH \n");
	    sb.append("  FROM KETPartClassification C \n");
	    sb.append("  WHERE 1 = 1    \n");
	    sb.append("  AND C.IDA2A2 = " + clzOID + " \n");
	    sb.append("  START WITH IDA3A3 = 0 \n");
	    sb.append("  CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	    sb.append("  ORDER SIBLINGS BY SORTNO \n");

	    String query = sb.toString();

	    clazFullPath = oDaoManager.queryForList(query, new RowSetStrategy<PartClassificationDTO>() {
		@Override
		public PartClassificationDTO mapRow(ResultSet rs) throws SQLException {
		    PartClassificationDTO dto = new PartClassificationDTO();
		    dto.setTreeFullPath(rs.getString("TREEFULLPATH"));
		    return dto;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}
	return clazFullPath.get(0).getTreeFullPath();
    }

    @Override
    public String[] getEnumKeyValue(String codeType, java.util.Locale loc) throws Exception {
	String[] enumKeyVal = new String[2];
	Map<String, Object> parameter = new HashMap<String, Object>();
	parameter.put("locale", loc);
	parameter.put("codeType", codeType);
	List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

	KETParamMapUtil enumMap = null;
	ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();

	for (Map<String, Object> item : codeList) {
	    enumMap = KETParamMapUtil.getMap();
	    enumMap.put("key", item.get("code"));
	    enumMap.put("value", item.get("value"));
	    enumList.add(enumMap);
	}

	enumKeyVal[0] = KETGridUtil.getKeyEnum(enumList, true);
	enumKeyVal[1] = KETGridUtil.getValueEnum(enumList, true);

	return enumKeyVal;
    }

    @Override
    public String[] getNamingEnumKeyValue(java.util.Locale loc) throws Exception {
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETPartNaming> list = query.queryForListByOneClass(KETPartNaming.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

	    }
	});

	String[] enumKeyVal = new String[2];
	KETParamMapUtil enumMap = null;
	ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();

	for (KETPartNaming naming : list) {
	    enumMap = KETParamMapUtil.getMap();
	    enumMap.put("key", CommonUtil.getOIDString(naming));
	    enumMap.put("value", naming.getNamingName());
	    enumList.add(enumMap);
	}

	enumKeyVal[0] = KETGridUtil.getKeyEnum(enumList, true);
	enumKeyVal[1] = KETGridUtil.getValueEnum(enumList, true);

	return enumKeyVal;
    }

    // 분류 코드로 분류체계 가져오기 - migration 등에 사용
    @Override
    public KETPartClassification getPartClassificationByClazCode(String clazCode) throws Exception {

	if (clazCode == null)
	    return null;

	final String iClazCode = clazCode;
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	KETPartClassification claz = query.queryForFirstByOneClass(KETPartClassification.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendWhere(new SearchCondition(class1, KETPartClassification.CLASS_CODE, SearchCondition.EQUAL, iClazCode),
		        new int[] { index });
	    }
	});

	return claz;
    }

    @Override
    public KETPartClassification getPartClassification(WTPart wtpart) throws Exception {

	if (wtpart == null)
	    return null;

	return getPartClassification(((WTPartMaster) wtpart.getMaster()));

    }

    // 분류 코드로 분류체계 가져오기 - project 사용
    @Override
    public KETPartClassification getPartClassification(WTPartMaster wtpartMast) throws Exception {

	if (wtpartMast == null)
	    return null;

	KETPartClassification claz = null;
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	KETPartClassLink classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, (wtpartMast));
	if (classLink != null) {
	    claz = classLink.getClassific();
	}

	return claz;
    }

    // 부품에 연결된 분류체계에 연결된 '재질' 속성을 가져온다. - BOM에서 사용 : 재질은 수지, 비철, 금형, 구매품으로 4가지 종류가 있음
    @Override
    public List<PartSpecEnum> getMaterialPartSpecEnum(WTPart wtpart) throws Exception {

	List<PartSpecEnum> list = new ArrayList<PartSpecEnum>();

	if (wtpart != null) {

	    KETPartClassification claz = getPartClassification(wtpart);

	    if (claz != null) {

		SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
		List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class,
		        KETPartClassification.class, claz);

		for (KETPartClassAttrLink link : linkList) {

		    KETPartAttribute attr = link.getAttr();

		    PartSpecEnum partSpecEnum = PartSpecEnum.toPartSpecEnumByAttrCode(attr.getAttrCode());

		    if (partSpecEnum != null) {

			if (partSpecEnum == PartSpecEnum.SpMaterialInfo || partSpecEnum == PartSpecEnum.SpMaterNotFe) {
			    list.add(partSpecEnum);
			}
		    }
		}
	    }
	}

	return list;
    }

    // 수지인지 비철인지 여부를 판단함.
    public boolean isSuji(String clazOid) throws Exception {

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);
	return isSuji(claz);

    }

    // 수지인지 비철인지 여부를 판단함.
    public boolean isSuji(KETPartClassification claz) throws Exception {

	if (claz == null)
	    throw new Exception("분류체계 값은 NULL일 수 없습니다. ");

	QueryResult queryResult = query.getQueryResultByRoleB(KETPartAttribute.class, KETPartClassAttrLink.class,
	        KETPartClassification.class, claz, new QueryLinkStrategy() {

		    @Override
		    public void handleQuery(QuerySpec query, Class classA, int indexA, Class classLink, int indexLink, Class classB,
		            int indexB) throws Exception {

		        if (query.getConditionCount() > 0)
			    query.appendAnd();

		        query.appendWhere(
		                new SearchCondition(classA, "attrCode", SearchCondition.EQUAL, PartSpecEnum.SpMaterialInfo.getAttrCode() /* 수지 */),
		                new int[] { indexA });
		    }

	        });

	if (queryResult != null && queryResult.size() > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    // @Override
    // public KETPartClassification findSameClassification(PartClassificationDTO paramObject) throws Exception {
    // // 동일
    // return null;
    // }
    //
    // @Override
    // public KETPartClassification insertWithParent(PartClassificationDTO paramObject) throws Exception {
    //
    // KETPartClassification child = new KETPartClazBuilder().buildDto2Pers(paramObject);
    // child = (KETPartClassification) PersistenceHelper.manager.save(child);
    //
    // String parentClazOid = paramObject.getParentOid();
    // KETPartClassification parent = (KETPartClassification) CommonUtil.getObject(parentClazOid);
    // KETPartClassTreeLink partClassTreeLink = KETPartClassTreeLink.newKETPartClassTreeLink(parent, child);
    // partClassTreeLink = (KETPartClassTreeLink) PersistenceHelper.manager.save(partClassTreeLink);
    //
    // return child;
    // }
    //
    // @Override
    // public KETPartClassification insertRoot(PartClassificationDTO paramObject) throws Exception {
    //
    // KETPartClassification root = new KETPartClazBuilder().buildDto2Pers(paramObject);
    //
    // root = (KETPartClassification) PersistenceHelper.manager.save(root);
    //
    // return root;
    // }

    // @Override
    // public KETPartClassification insertMigration(KETPartClassification calz, KETPartClassTreeLink link) throws Exception{
    // calz = (KETPartClassification) PersistenceHelper.manager.save(calz);
    //
    // if(link != null){
    // link = (KETPartClassTreeLink) PersistenceHelper.manager.save(link);
    // }
    //
    // return calz;
    // }

}