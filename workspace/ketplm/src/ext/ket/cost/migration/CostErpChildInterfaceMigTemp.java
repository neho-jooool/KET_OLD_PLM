package ext.ket.cost.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.entity.CostInterfaceChildHistory;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.util.CostTreeUtil;
import ext.ket.cost.util.CostUtil;

public class CostErpChildInterfaceMigTemp implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static CostErpChildInterfaceMigTemp manager = new CostErpChildInterfaceMigTemp();

    public CostErpChildInterfaceMigTemp() {

    }

    public static void main(String[] args) {
	try {
	    CostErpChildInterfaceMigTemp.manager.saveFromExcel();
	} catch (Exception e) {

	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

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
	    executeMigration();
	}
    }

    public Map<String, Object> getCodeList() throws Exception {
	Map<String, Object> codeList = new HashMap<String, Object>();

	List<NumberCode> mftCodeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	qs.appendClassList(CostPartType.class, true);
	qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPartType obj = (CostPartType) o[0];
	    codeList.put(CommonUtil.getOIDLongValue2Str(obj), obj.getName());
	}
	for (NumberCode obj : mftCodeList) {
	    codeList.put(CommonUtil.getOIDLongValue2Str(obj), obj.getName());
	}
	return codeList;
    }

    private Map<String, Object> costTreeConvertProduct(String oid) throws Exception {
	Map<String, Object> reqMap = new HashMap<String, Object>();
	reqMap.put("DATATYPE", "COSTPART");
	reqMap.put("oid", oid);

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	List<DefaultMutableTreeNode> nodeList = CostTreeUtil.manager.getCostTreeList(reqMap);

	for (DefaultMutableTreeNode node : nodeList) {

	    Persistable data = (Persistable) node.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("oid", CommonUtil.getOIDString(data));
	    dataList.add(dataMap);
	    costTreeGridSetting(node, dataMap, reqMap);
	}

	Map<String, Object> resMap = new HashMap<String, Object>();

	resMap.put("rootPartData", CostTreeUtil.manager.calcConvertProductCost(dataList));
	resMap.put("partList", dataList);

	return resMap;
    }

    private void costTreeGridSetting(DefaultMutableTreeNode parent, Map<String, Object> treeMap, Map<String, Object> reqMap)
	    throws Exception {

	ArrayList<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("oid", CommonUtil.getOIDString(data));
	    childList.add(dataMap);
	    costTreeGridSetting(child, dataMap, reqMap);
	}

	if (childList.size() > 0)
	    treeMap.put("Items", childList);
    }

    public void executeMigration() throws WTException {
	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    System.out.println("**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    Map<String, Object> codeMap = getCodeList();

	    Map<String, Object> pjtMap = new HashMap<String, Object>();
	    List<Map<String, Object>> pjtList = new ArrayList<Map<String, Object>>();

	    List<CostInterfaceHistory> parentList = new ArrayList<CostInterfaceHistory>();

	    List<String> oidList = new ArrayList<String>();
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333358");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333345");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333346");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1299317569");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285334120");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1299317568");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285334130");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333333");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333334");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333337");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333330");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333331");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333332");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333395");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333508");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1292658866");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333535");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1297387140");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1286830584");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1292658865");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333534");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1286830588");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1290858164");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1287982718");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1287982719");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333370");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333501");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333548");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333307");
	    oidList.add("ext.ket.cost.entity.CostInterfaceHistory:1285333319");

	    for (String oid : oidList) {

		CostInterfaceHistory obj = (CostInterfaceHistory) CommonUtil.getObject(oid);
		parentList.add(obj);

		if ("제품".equals(obj.getPjtType())) {

		    E3PSProject project = ProjectHelper.getProject(obj.getProject().getPjtNo());

		    pjtMap.put("pjtNo", project.getReviewPjtNo());
		    pjtMap.put("step", obj.getDrStep());
		    pjtMap.put("pjtOid", CommonUtil.getOIDLongValue(obj.getProject()));
		    pjtMap.put("partNo", obj.getPartNo());

		    pjtList.add(pjtMap);
		}
	    }

	    for (CostInterfaceHistory obj : parentList) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if ("검토".equals(obj.getPjtType())) {
		    for (Map<String, Object> pjtInfo : pjtList) {
			String reviewPjtNos = (String) pjtInfo.get("pjtNo");
			String partNo = (String) pjtInfo.get("partNo");

			if (reviewPjtNos.contains(obj.getPjtNo()) && obj.getPartNo().equals(partNo)) {
			    paramMap.put("PRODUCTSTEP", pjtInfo.get("step"));
			    paramMap.put("PRODUCTPJTOID", pjtInfo.get("pjtOid"));
			    break;
			}
		    }
		}

		Map<String, Object> resMap = costTreeConvertProduct(CommonUtil.getOIDString(obj.getCostPart()));
		List<Map<String, Object>> convertPartList = (List<Map<String, Object>>) resMap.get("partList");
		try {
		    childPartCreate(convertPartList, null, obj, codeMap, paramMap);

		} catch (Exception e) {
		    obj.setBomTreeCheck(false);
		    PersistenceHelper.manager.save(obj);
		    e.printStackTrace();
		}
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

    public void childPartCreate(List<Map<String, Object>> dataList, CostInterfaceChildHistory parentNewPart,
	    CostInterfaceHistory parentHistory, Map<String, Object> codeMap, Map<String, Object> paramMap) throws Exception {

	Transaction trx = new Transaction();
	try {
	    for (Map<String, Object> data : dataList) {
		String oid = (String) data.get("oid");
		CostPart part = (CostPart) CommonUtil.getObject(oid);

		CostInterfaceChildHistory newProduct = CostInterfaceChildHistory.newCostInterfaceChildHistory();
		data.remove("sortLocation");
		data.remove("version");
		data.remove("projectReference");
		data.remove("project");
		data.remove("parent");

		ObjectUtil.manager.convertMapToObject(data, newProduct);

		newProduct.setCostPart(part);
		newProduct.setSortLocation(part.getSortLocation());

		if (parentNewPart == null) {
		    parentNewPart = newProduct;
		} else {
		    newProduct.setParent(parentNewPart);
		}

		newProduct.setProductCostTotal(CostTreeUtil.manager.erpFormatConvert(newProduct.getProductCostTotal(), 3));
		newProduct = (CostInterfaceChildHistory) PersistenceHelper.manager.save(newProduct);
		updateChildPart(newProduct, parentHistory, codeMap, paramMap);

		if (data.get("Items") != null) {
		    childPartCreate((ArrayList<Map<String, Object>>) data.get("Items"), newProduct, parentHistory, codeMap, paramMap);
		}
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

    }

    private void updateChildPart(CostInterfaceChildHistory child, CostInterfaceHistory history, Map<String, Object> codeList,
	    Map<String, Object> paramMap) throws Exception {

	child.setPjtNo(history.getPjtNo());
	child.setPjtType(history.getPjtType());
	child.setRootPartNo(history.getRealPartNo());
	child.setDrStep(history.getDrStep());
	child.setVersion(history.getVersion());
	child.setParentHistory(history);
	child.setTask(history.getTask());
	child.setProject(history.getProject());
	child.setPartList(history.getPartList());

	String mftFactory1 = child.getMftFactory1();
	String mftFactory2 = child.getMftFactory2();
	String partType = child.getPartType();
	child.setMftFactory1("");
	child.setMftFactory2("");
	child.setPartType("");

	Set<String> st = codeList.keySet();
	Iterator<String> it = st.iterator();

	while (it.hasNext()) {
	    String key = it.next();
	    if (key.equals(mftFactory1)) {
		child.setMftFactory1((String) codeList.get(key));
	    }
	    if (key.equals(mftFactory2)) {
		child.setMftFactory2((String) codeList.get(key));
	    }
	    if (key.equals(partType)) {
		child.setPartType((String) codeList.get(key));
	    }
	}

	if (StringUtils.isEmpty(child.getCompany())) {// 값이 없을 때 생산처2를 세팅
	    child.setCompany(child.getMftFactory2());
	}

	if ("검토".equals(child.getPjtType()) && StringUtils.isEmpty(child.getRealPartNo())) {
	    Long productPjtOid = (Long) paramMap.get("PRODUCTPJTOID");
	    String productStep = (String) paramMap.get("PRODUCTSTEP");

	    List<Map<String, Object>> realPartInfo = getResultList(getRealPartNo(child.getCostPart().getPartNo(), productStep,
		    productPjtOid));
	    String realPartNo = "";
	    if (realPartInfo != null && realPartInfo.size() > 0) {
		realPartNo = (String) realPartInfo.get(0).get("REALPARTNO");
	    }

	    child.setRealPartNo(realPartNo);
	}

	PersistenceHelper.manager.save(child);
    }

    private String getRealPartNo(String partNo, String step, Long pjtOid) {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT DISTINCT REALPARTNO FROM COSTPART WHERE PARTNO ='" + partNo + "' AND REALPARTNO IS NOT NULL AND IDA3B4 = "
	        + pjtOid + " AND DRSTEP = '" + step + "' \n");
	return sql.toString();
    }

    private Map<String, Object> getReviewProjectInfo(List<Map<String, Object>> targetList, String pjtNo) {

	for (Map<String, Object> targetMap : targetList) {
	    if (targetMap.get("PJTNO").equals(pjtNo)) {
		return targetMap;
	    }
	}
	return null;

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
