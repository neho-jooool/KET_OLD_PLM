package ext.ket.edm.cad2bom.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.vc.VersionControlHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

final public class Cad2BomUtil {
    
    ////////////////////////////////////////////////////
    // 금형쪽 부품인지 제품쪽 부품인지 판단한다.
    ////////////////////////////////////////////////////
    public static String getDieCodeTypeStr(String epmNo) throws Exception {
	if(epmNo == null) return null;
	
	String code = epmNo;
	if(epmNo.indexOf("-") != -1){
	    code = code.substring(0, code.indexOf("-"));
	}
	
	return code;
    }
    
    public static boolean isDieNo(String epmNo) throws Exception{

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    if(epmNo == null) return false;
	    
	    List aBind = new ArrayList();
	    aBind.add(epmNo);
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT M.IDA2A2 \n");
	    sb.append(" FROM WTPARTMASTER M, WTPART P \n");
	    sb.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	    sb.append(" AND P.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND P." + PartSpecEnum.SpPartType.getColumnName() + " = 'D' \n");
	    sb.append(" AND M.WTPARTNUMBER = ? \n");

	    String sSql = sb.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);
	    
	    return (count != 0);

	} catch (Exception e) {
	    Kogger.error(Cad2BomUtil.class, e);
	    throw e;
	}
    }
    
    ////////////////////////////////////////////////////
    // 금형쪽 부품일 경우 - 해당 부품을 가져온다.
    ////////////////////////////////////////////////////
    public static String getMoldPartNoTypeByRemoveSuffix(EPMDocument sourceEpmDoc, String epmNo) throws Exception {
	if(epmNo == null) return null;
	
	String cadName = sourceEpmDoc.getCADName();
	String code = epmNo;
	if (cadName.toUpperCase().endsWith("-TOP.PRT")) { // DIE NO로 추정
	    if (epmNo.indexOf("-") != -1) {
		code = code.substring(0, code.indexOf("-"));
	    }
	    return code;
	} else {

	    if (epmNo.endsWith("_PRT") || epmNo.endsWith("_DWG") || epmNo.endsWith("_PLS")) {
		code = code.substring(0, code.length() - 4);
	    }

	    return code;
	}
    }
    
    
    public static String getWTPartOidByMold(String epmNo) throws Exception{
	
	try {
	    
	    if(epmNo == null) return null;
	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    List aBind = new ArrayList();
	    aBind.add(epmNo);
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT P.CLASSNAMEA2A2||':'||P.IDA2A2 OID \n");
	    sb.append(" FROM WTPARTMASTER M, WTPART P \n");
	    sb.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	    sb.append(" AND P.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND P." + PartSpecEnum.SpPartType.getColumnName() + " IN ( 'D', 'M' ) \n");
	    sb.append(" AND M.WTPARTNUMBER = ? \n");
	    sb.append(" AND P.STATECHECKOUTINFO != 'wrk' \n");
	    sb.append(" AND P.VERSIONLEVELA2VERSIONINFO = ( SELECT MAX(VERSIONLEVELA2VERSIONINFO) \n");
	    sb.append("                                  FROM WTPART I \n");
	    sb.append("                                  WHERE I.IDA3MASTERREFERENCE = P.IDA3MASTERREFERENCE \n");
	    sb.append("                                  AND I.STATECHECKOUTINFO != 'wrk' \n");
	    sb.append("                                  AND I.LATESTITERATIONINFO = 1 ) \n");
	    
	    String sSql = sb.toString();
	    String oid = oDaoManager.queryForObject(sSql, aBind, new RowSetStrategy<String>() {
		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("OID");
	            return ret;
                }
	    });
	    
	    return oid;
	    
	} catch (Exception e) {
	    Kogger.error(Cad2BomUtil.class, e);
	    throw e;
	}
    }
    
    ////////////////////////////////////////////////////
    // 제품쪽 부품일 경우 - 해당 부품을 가져온다.
    ////////////////////////////////////////////////////
    public static String getProdPartNoTypeByChangePrefix(String epmNo) throws Exception {
	
	if(epmNo == null) return null;
	
	String code = epmNo;
	EPMNumberProdType epmType = EPMNumberProdType.getMatchEpm(code);
	
	if(epmType == null) return null;
	
	PartNumberProdType partType1 = epmType.getPartNumberType();
	String newCode1 = null;
	String epmCode = epmType.toString().replace("_", "");
	if(partType1 != null){	    
	    String partCode = partType1.toString().replace("_", "");
	    newCode1 = code.substring(epmCode.length());
	    
	    if(newCode1.endsWith("_3D"))
		newCode1 = newCode1.substring(0, newCode1.length()-3);
	    
	    newCode1 = partCode + newCode1;
	    return newCode1;
	}
	
	return null;
    }
    
    public static String getWTPartOidByProd(String epmNo) throws Exception{
	
	try {
	    
	    if(epmNo == null) return null;
	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    List aBind = new ArrayList();
	    aBind.add(epmNo);
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT P.CLASSNAMEA2A2||':'||P.IDA2A2 OID \n");
	    sb.append(" FROM WTPARTMASTER M, WTPART P \n");
	    sb.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	    sb.append(" AND P.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND P." + PartSpecEnum.SpPartType.getColumnName() + " IN ( 'F', 'H', 'R', 'K', 'S', 'W' ) \n");
	    sb.append(" AND M.WTPARTNUMBER = ? \n");
	    sb.append(" AND P.STATECHECKOUTINFO != 'wrk' \n");
	    sb.append(" AND P.VERSIONLEVELA2VERSIONINFO = ( SELECT MAX(VERSIONLEVELA2VERSIONINFO) \n");
	    sb.append("                                  FROM WTPART I \n");
	    sb.append("                                  WHERE I.IDA3MASTERREFERENCE = P.IDA3MASTERREFERENCE \n");
	    sb.append("                                  AND I.STATECHECKOUTINFO != 'wrk' \n");
	    sb.append("                                  AND I.LATESTITERATIONINFO = 1 ) \n");
	    
	    String sSql = sb.toString();
	    String oid = oDaoManager.queryForObject(sSql, aBind, new RowSetStrategy<String>() {
		@Override
                public String mapRow(ResultSet oResultSet) throws SQLException {
		    String ret = oResultSet.getString("OID");
	            return ret;
                }
	    });
	    
	    return oid;
	    
	} catch (Exception e) {
	    Kogger.error(Cad2BomUtil.class, e);
	    throw e;
	}
    }
    
    ////////////////////////////////////////////////////
    // 제품쪽이든지 금형쪽이든지간에 3D 번호로 해당 부품을 가져오기
    ////////////////////////////////////////////////////
    public static WTPart getPartBy3DNo(EPMDocument sourceEpmDoc, String threeDNo, boolean isMoldSideCad) throws Exception{
	
	// 금형인지, 제품쪽인지 - EPM의 Die No로 먼저 판단
	String code = threeDNo;
	String oid = null;
	if(isMoldSideCad){
	    // 금형 부품 찾기
	    oid = Cad2BomUtil.getWTPartOidByMold(Cad2BomUtil.getMoldPartNoTypeByRemoveSuffix(sourceEpmDoc, code));
	}else{
	    // Prod 부품 찾기
	    oid = Cad2BomUtil.getWTPartOidByProd(Cad2BomUtil.getProdPartNoTypeByChangePrefix(code));
	}
	
	if(oid == null){
	    return null;
	}else{
	    return (WTPart)CommonUtil.getObject(oid);
	}
    }
    
    
    public static WTPart getProdFertPart(EPMDocument sourceEpmDoc, boolean isMoldSideCad) throws Exception{
	WTPart topPart = Cad2BomUtil.getPartBy3DNo(sourceEpmDoc, sourceEpmDoc.getNumber(), isMoldSideCad);
	if (topPart != null) {
	    Kogger.debug(Cad2BomUtil.class, "#topPart:" + topPart.getNumber());
	    String partType = PartSpecGetter.getPartSpec(topPart, PartSpecEnum.SpPartType);
	    Kogger.debug(Cad2BomUtil.class, "#topPart: partType" + partType);

	    // 반제품일 경우
	    if ("H".equals(partType)) {
		// H를 제외하고 해당 FERT를 찾는다.
		String prodPartNumber = topPart.getNumber().substring(1);
		Kogger.debug(Cad2BomUtil.class, "#prodPartNumber:" + prodPartNumber);
		WTPart prodPart = Cad2BomUtil.getFertPart(prodPartNumber);
		if (prodPart != null) {
		    Kogger.debug(Cad2BomUtil.class, "#finde prodPartNumber:" + prodPart.getNumber());
		    return prodPart;
		}
	    }
	}
	
	return null;
    }
	
    public static WTPart getFertPart(String _prodPartNumber) throws Exception{
	
	final String prodPartNumber = _prodPartNumber;
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	WTPart prodPart = query.queryForFirstByOneClass(WTPart.class, new QueryStrategy() {
	    
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendWhere(new SearchCondition(class1, WTPart.NUMBER, SearchCondition.EQUAL, prodPartNumber), new int[]{index});
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, PartSpecEnum.SpPartType.getQuerySpecCode(), SearchCondition.EQUAL, "F"), new int[]{index});
	    }
	});

	if(prodPart != null)
	    return (WTPart) ObjectUtil.getLatestObject((WTPartMaster)prodPart.getMaster());
	else 
	    return null;
    }
    
    public static WTPart getBeforeApprovedPart(WTPart inworkPart) throws Exception{
	
	if(inworkPart == null) return null;
	
	String inworkVersion = inworkPart.getVersionIdentifier().getValue();
	Kogger.debug(Cad2BomUtil.class, " 넘어온 하위 BOM 버전:" + inworkVersion);
	
	if("0".equals(inworkVersion)) return null;
	    
	String version = String.valueOf( Integer.parseInt(inworkVersion) - 1 );
		
	RevisionControlled rc = null;
	QueryResult qr = VersionControlHelper.service.allVersionsOf((WTPartMaster)inworkPart.getMaster());
	Map<String, WTPart> map = new HashMap<String, WTPart>();
	while (qr.hasMoreElements()) {
	    RevisionControlled obj = ((RevisionControlled) qr.nextElement());
	    if (obj.getVersionIdentifier().getValue().equals(version)){
		map = null;
		return (WTPart)obj;
	    }
	    map.put(obj.getVersionIdentifier().getValue(),(WTPart) obj);
	}
	
	Iterator it = map.entrySet().iterator();
	int k = 0;
	while(it.hasNext()){
	    String verStr = (String)it.next();
	    int ver = Integer.parseInt(verStr);
	    
	    if(ver > k) k = ver;
	}
	
	Kogger.debug(Cad2BomUtil.class, " 선택된 하위 BOM 버전:" + k);
	
	return map.get(k+"");
	
    }
    
    public static void main(String[] args) throws Exception {
	String code = "12345678-123";
	String code1 = "12345678_PLS";
	String code2 = "JB510015";
	
	String epmCode = "JB51";
	String partCode = "H51";
	    
	code2 = code2.substring(epmCode.length());
	code2 = partCode + code2;
	
	Kogger.debug(Cad2BomUtil.class, code);
	Kogger.debug(Cad2BomUtil.class, code1);
	Kogger.debug(Cad2BomUtil.class, code2);
    }
    
    
}
