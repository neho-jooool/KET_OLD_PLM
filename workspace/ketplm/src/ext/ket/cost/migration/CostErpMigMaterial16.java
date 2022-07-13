package ext.ket.cost.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.code.sap.ErpProductCostInterface;
import ext.ket.cost.entity.CostInterfaceChildHistory;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostPart;
import ext.ket.part.base.service.PartBaseHelper;

public class CostErpMigMaterial16 implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static CostErpMigMaterial16 manager = new CostErpMigMaterial16();

    public CostErpMigMaterial16() {

    }

    public static void main(String[] args) {
	try {
	    String flag = args[0];
	    CostErpMigMaterial16.manager.saveFromExcel(flag);
	} catch (Exception e) {

	}
    }

    public void saveFromExcel(String flag) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { flag };

		System.out.println("@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		System.out.println("@		end");

		return;

	    } catch (RemoteException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    executeMigration(flag);
	}
    }

    public void executeMigration(String flag) throws WTException {
	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    ErpProductCostInterface costInterface = new ErpProductCostInterface();

	    if ("1".equals(flag)) {
		costInterface.migChildPartMaterailUpdate();
	    }
	    if ("2".equals(flag)) {
		costInterface.migChildPartERPSend();
	    }

	    if ("3".equals(flag)) {
		findMetalPartAndSave();
		findSujiPartAndSave();
	    }

	    trx.commit();
	    System.out.println("**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public WTPartMaster getWTpart(String partNo) throws WTException {

	if (StringUtils.isEmpty(partNo)) {
	    return null;
	}

	return PartBaseHelper.service.getMaster(partNo);

    }

    public File FileDelAndCreate(String path) throws Exception {

	File file = new File(path);

	if (file.exists()) {
	    file.delete();
	} else {
	    file = new File(path);
	}

	return file;
    }

    public void perform(QueryResult qr, File Sfile, File Ffile, String materType) throws Exception {

	List<Map<String, String>> sList = new ArrayList<Map<String, String>>();
	List<Map<String, String>> fList = new ArrayList<Map<String, String>>();

	while (qr.hasMoreElements()) {

	    WTPart wtpart = null;

	    CostInterfaceChildHistory child = (CostInterfaceChildHistory) qr.nextElement();
	    CostPart part = child.getCostPart();
	    String materialNo = "";
	    if (materType.equals("R1")) {
		materialNo = child.getMetalPartNo();
	    } else if (materType.equals("R2")) {
		materialNo = child.getSujiPartNo();
	    }

	    CostInterfaceHistory parent = child.getParentHistory();
	    String partNo = child.getRealPartNo();

	    if (StringUtils.isNotEmpty(materialNo) && getWTpart(materialNo) == null) {// 소문자로 오기입된 경우 원본유지를 위한 로직
		materialNo = materialNo.toUpperCase();

		if (getWTpart(materialNo) != null) {

		    part.setMetalPartNo("");
		    part.setSujiPartNo("");
		    if (materType.equals("R1")) {
			part.setMetalPartNo(materialNo);
		    } else if (materType.equals("R2")) {
			part.setSujiPartNo(materialNo);
		    }

		    PersistenceHelper.manager.save(part);
		    continue;
		}
	    }

	    if (StringUtils.isNotEmpty(partNo) && parent.isLastest() && getWTpart(materialNo) == null) {// 유효하지 않은 원자재번호일 경우

		if (!partNo.startsWith("H")) {
		    wtpart = PartBaseHelper.service.getLatestPart("H" + partNo);
		}

		if (wtpart == null) {
		    wtpart = PartBaseHelper.service.getLatestPart(partNo);
		}

		String wtPartOid = CommonUtil.getOIDLongValue2Str(wtpart);
		String childOid = CommonUtil.getOIDLongValue2Str(child);
		List<Map<String, String>> partList = findMaterailNoByBom(materType, wtPartOid, partNo, childOid, false);

		if (partList.size() > 1) {
		    List<Map<String, String>> tempList = findMaterailNoByBom(materType, wtPartOid, partNo, childOid, true);
		    if (tempList.size() == 1) {
			partList = tempList;
		    }
		}

		if (partList.size() == 1) {// 매핑 부품이 없거나 2개 이상인 경우 체크 , LOG 기록을 위해 LIST 적재

		    part.setMetalPartNo("");
		    part.setSujiPartNo("");
		    if (materType.equals("R1")) {
			part.setMetalPartNo(partList.get(0).get("materailPartNo"));
		    } else if (materType.equals("R2")) {
			part.setSujiPartNo(partList.get(0).get("materailPartNo"));
		    }

		    PersistenceHelper.manager.save(part);
		    sList.addAll(partList);

		} else {

		    if (partList.size() == 0) {
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("partNo", partNo);
			temp.put("childOid", childOid);
			fList.add(temp);
		    } else {
			fList.addAll(partList);
		    }

		}
	    }
	}
	writeLogFile(sList, Sfile); // 성공 로그
	writeLogFile(fList, Ffile); // 매핑 부품이 없거나 2개 이상인 경우 로그남김
    }

    public void findMetalPartAndSave() throws Exception {
	String Spath = "D://ptc//Windchill_10.2//Windchill//codebase//temp//비철_FailLog.txt";
	String Fpath = "D://ptc//Windchill_10.2//Windchill//codebase//temp//비철_SuccessLog.txt";

	File Ffile = FileDelAndCreate(Spath);
	File Sfile = FileDelAndCreate(Fpath);

	/**
	 * 1. 비철 원자재 name은 있는데 비철번호가 매핑되지 않았거나 유효하지 않는 품번으로 매핑된 원가PART를 찾고, 해당 PART의 개발BOM을 조회하여 비철 원자재를 찾는다. costPart의 비철번호를 save 한다
	 * 
	 * 2. 비철원자재를 못찾거나 또는 비철원자재 번호를 찾았는데 1개 이상이면 로그를 기록한다
	 */

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceChildHistory.class);
	qs.appendWhere(new SearchCondition(CostInterfaceChildHistory.class, CostInterfaceChildHistory.METAL_PART_NAME,
	        SearchCondition.NOT_NULL, true), new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);

	perform(qr, Sfile, Ffile, "R1");

    }

    public void findSujiPartAndSave() throws Exception {
	String Spath = "D://ptc//Windchill_10.2//Windchill//codebase//temp//수지_FailLog.txt";
	String Fpath = "D://ptc//Windchill_10.2//Windchill//codebase//temp//수지_SuccessLog.txt";

	File Ffile = FileDelAndCreate(Spath);
	File Sfile = FileDelAndCreate(Fpath);

	/**
	 * 1. 수지 원자재 NO은 있는데 유효하지 않는 품번으로 매핑된 수지PART를 찾고, 해당 PART의 개발BOM을 조회하여 수지 원자재를 찾는다. costPart의 수지번호를 save 한다
	 * 
	 * 2. 수지원자재를 못찾거나 또는 수지원자재 번호를 찾았는데 1개 이상이면 로그를 기록한다
	 */

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec(CostInterfaceChildHistory.class);
	qs.appendWhere(new SearchCondition(CostInterfaceChildHistory.class, CostInterfaceChildHistory.SUJI_PART_NO,
	        SearchCondition.NOT_NULL, true), new int[] { 0 });
	qr = PersistenceHelper.manager.find(qs);

	perform(qr, Sfile, Ffile, "R2");

    }

    public void writeLogFile(List<Map<String, String>> checkList, File file) {

	BufferedWriter bufferedWriter = null;

	try {

	    bufferedWriter = new BufferedWriter(new FileWriter(file));

	    if (file.isFile() && file.canWrite()) {

		for (Map<String, String> map : checkList) {
		    String childOid = map.get("childOid");
		    String materailPartNo = map.get("materailPartNo");
		    bufferedWriter.write("childOid : " + childOid + "           " + materailPartNo);
		    bufferedWriter.newLine();
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		bufferedWriter.close();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public List<Map<String, String>> findMaterailNoByBom(String partType, String oid, String partNo, String childOid, boolean oneLevel)
	    throws Exception {

	List<Map<String, Object>> targetList = getResultList(getBomQuery(oid, oneLevel));

	List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

	for (Map<String, Object> targetMap : targetList) {
	    String materailPartNo = (String) targetMap.get("ITEMCODE");

	    if (materailPartNo.startsWith(partType)) {

		Map<String, String> partMap = new HashMap<String, String>();
		partMap.put("materailPartNo", materailPartNo);
		partMap.put("partNo", partNo);
		partMap.put("childOid", childOid);
		partMap.put("oid", oid);

		resultList.add(partMap);

	    }

	}

	return resultList;
    }

    private String getBomQuery(String oid, boolean oneLevel) {
	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT BOM.COMPONENT_ITEM_CODE as ITEMCODE FROM                                                                                        ");
	sql.append(" (                                                                                                        ");
	sql.append("     SELECT LEVEL AS LVL, X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,                                                     ");
	sql.append(" 		        X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,                                                  ");
	sql.append(" 		        B.NAME AS COMPONENT_ITEM_NAME,                                                            ");
	sql.append(" 		        A.STATESTATE AS STATUS,    A.IDA3A2STATE AS IDA3A2STATE                                   ");
	sql.append(" 	  FROM ( SELECT B.*,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1               ");
	sql.append(" 	                                       AND P.STATECHECKOUTINFO != 'wrk' AND P.STATESTATE = 'APPROVED' ");
	sql.append(" 		                                   AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID      ");
	sql.append(" 		             FROM KETPARTUSAGELINK B ) X0, WTPART A, WTPARTMASTER B                               ");
	sql.append("     WHERE X0.LAST_WTPART_OID = A.IDA2A2                                                                  ");
	sql.append(" 	    AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                              ");
	sql.append("      START WITH X0.IDA3A5  = '" + oid + "'                                                                ");
	sql.append("      CONNECT BY PRIOR X0.LAST_WTPART_OID =  X0.IDA3A5                                                    ");
	sql.append("      ORDER SIBLINGS BY  X0.ITEMSEQ) BOM , PHASETEMPLATE PH  ,PHASELINK PL                                ");
	sql.append(" WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                                                       ");
	sql.append(" AND PL.IDA3B5 = PH.IDA2A2                                                                                ");
	sql.append(" AND PH.PHASESTATE = BOM.STATUS");
	if (oneLevel) {
	    sql.append(" AND LVL = 1");
	}

	return sql.toString();
    }

    private List<Map<String, Object>> getResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
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
}
