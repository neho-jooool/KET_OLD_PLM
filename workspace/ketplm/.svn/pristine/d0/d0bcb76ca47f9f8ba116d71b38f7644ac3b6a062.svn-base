package ext.ket.cost.util;

import java.io.File;
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

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostAttribute;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostInterfaceChildHistory;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.CostQuantity;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.entity.costPartQtyLink;
import ext.ket.cost.entity.manage.BomEditorACL;
import ext.ket.cost.entity.manage.BomEditorACLFromType;
import ext.ket.cost.service.CostCacheManager;
import ext.ket.cost.service.CostHelper;
import ext.ket.shared.code.service.CodeHelper;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 3. 6. 오전 9:18:40
 * @Pakage ext.ket.cost.util
 * @class CostTreeUtil
 *********************************************************/
public class CostTreeUtil {

    public static final CostTreeUtil manager = new CostTreeUtil();

    private static final Logger LOGGER = Logger.getLogger(CostTreeUtil.class);

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 4. 5. 오전 10:34:35
     * @method bomEditorNeedCheck
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> bomEditorNeedCheck(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String taskOid = (String) reqMap.get("taskOid");
	String EDITMODE = (String) reqMap.get("EDITMODE");

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();
	String taskTypeCategory = task.getTaskTypeCategory();

	List<CostPart> cpList = getCostPartList(reqMap);

	// 예상판매량, 포장사양, 기타투자비, 원가산출요청, 원가산출, 판매목표가 등록, 보고서 등록이 아닐 경우 PART FILTERING
	if (!("TREEEDIT".equals(EDITMODE) || "COST002".equals(taskTypeCategory) || "COST010".equals(taskTypeCategory)
	        || "COST011".equals(taskTypeCategory) || "COST013".equals(taskTypeCategory) || "COST014".equals(taskTypeCategory)
	        || "COST015".equals(taskTypeCategory) || "COST016".equals(taskTypeCategory))) {

	    // for(CostPart part : cpList) {
	    //
	    // String partTypeOid = part.getPartType();
	    //
	    // if (StringUtil.checkString(partTypeOid)) {
	    // CostPartType partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);
	    // String partTypeTaskCode = StringUtil.checkNull(partType.getTaskCode());
	    //
	    // if(StringUtil.checkString(partTypeTaskCode) && partTypeTaskCode.indexOf(taskTypeCategory) < 0){
	    //
	    // LOGGER.info("check skip part ####" + CommonUtil.getOIDString(part));
	    // cpList.remove(part);
	    // }
	    // }
	    // }

	    for (Iterator<CostPart> iter = cpList.iterator(); iter.hasNext();) {
		CostPart part = iter.next();
		String partTypeOid = part.getPartType();

		if (StringUtil.checkString(partTypeOid)) {
		    CostPartType partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);
		    String partTypeTaskCode = StringUtil.checkNull(partType.getTaskCode());

		    if (StringUtil.checkString(partTypeTaskCode) && partTypeTaskCode.indexOf(taskTypeCategory) < 0) {

			LOGGER.info("check skip part ####" + CommonUtil.getOIDString(part));
			iter.remove();
		    }
		}
	    }
	}

	reqMap.put("CPLIST", cpList);

	boolean isError = false;

	if ("COST016".equals(taskTypeCategory)) { // 원가 보고서
	    E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

	    String projectOid = CommonUtil.getOIDString(master);
	    String version = task.getCostVersion();

	    CostReport report = CostUtil.manager.getCostReport(projectOid, version);
	    String message = "";

	    boolean isOpen = true;

	    if (report == null) {
		isOpen = false;
		message = "원가보고서가 작성되지 않았습니다.";
	    } else {
		String state = report.getState().toString();
		if (!state.equals("APPROVED")) {
		    message = "원가보고서의 결재가 완료되지 않았습니다.";
		    isOpen = false;
		}
	    }

	    if (!isOpen) {
		resMap.put("checkResult", false);
		resMap.put("message", message);
		isError = true;
	    }

	}

	if ("COST013".equals(taskTypeCategory)) { // 원가 산출 요청

	    if (!(project instanceof ReviewProject)) {

		String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

		if (!("DR0".equals(drNo) || "DR1".equals(drNo))) {
		    isError = CostUtil.manager.checkRealPartNo(reqMap, resMap, isError);
		}
	    }

	    if (!task.isCostRequest()) {
		resMap.put("checkResult", false);
		resMap.put("message", "원가요청서가 확인되지 않았습니다.");
		isError = true;
	    }
	}

	if ("COST013".equals(taskTypeCategory) || "COST002".equals(taskTypeCategory) || "COST014".equals(taskTypeCategory)) { // 원가 산출 요청 OR
		                                                                                                              // 예상판매량 OR
		                                                                                                              // 판매목표가 등록
	    boolean lastestCheck = false;
	    boolean salesTargetCheck = true;

	    int checkCnt = 0;
	    String checkPartNo = "";
	    for (CostPart cp : cpList) {
		checkCnt++;
		if (cp.getParent() == null) {
		    lastestCheck = false;
		    QueryResult qr = PersistenceHelper.manager.navigate(cp, "costQuantity", costPartQtyLink.class, true);

		    while (qr.hasMoreElements()) {

			CostQuantity qty = (CostQuantity) qr.nextElement();

			if ("1".equals(qty.getLastest())) {
			    Double salesTargetCost = Double.parseDouble(StringUtil.checkNullZero(qty.getSalesTargetCostExpr()));

			    if (salesTargetCost > 0 && StringUtils.isEmpty(cp.getSalesTargetGb())) {
				salesTargetCheck = false;
				lastestCheck = true;
				break;
			    }

			    lastestCheck = true;
			    break;
			}

		    }

		    if (!lastestCheck || !salesTargetCheck) {
			checkPartNo = cp.getPartNo();
			break;
		    }

		} else {
		    lastestCheck = true;
		}
	    }

	    if (project instanceof ProductProject || "COST002".equals(taskTypeCategory)) {// 개발프로젝트의 경우 실질적으로 원가요청을 개발팀에서 진행하므로 판가에 대해서 체크하지
		                                                                          // 않음
		salesTargetCheck = true;
	    }

	    if (checkCnt < 1) {
		resMap.put("checkResult", false);
		resMap.put("message", "원가요청서가 작성되지 않았습니다.");
		isError = true;
	    } else if (!lastestCheck) {
		resMap.put("checkResult", false);
		resMap.put("message", checkPartNo + " 의 예상판매량 최종안이 없습니다.");
		isError = true;
	    } else if (!salesTargetCheck) {
		resMap.put("checkResult", false);
		resMap.put("message", checkPartNo + " 의 판매가에 대한 목표/확정 구분이 없습니다.");
		isError = true;
	    }
	}

	if ("COST015".equals(taskTypeCategory) && !isError) {

	    // List<String> bPartNoList = new ArrayList<String>();
	    // List<String> lPartNoList = new ArrayList<String>();

	    HashMap<String, String> originalPartNoMap = new HashMap<String, String>();

	    HashMap<String, String> bPartNoMap = new HashMap<String, String>();
	    HashMap<String, String> lPartNoMap = new HashMap<String, String>();

	    List<CostPart> lastCpList = new ArrayList<CostPart>();

	    for (CostPart cp : cpList) {

		String partType = cp.getPartType();

		if (StringUtil.checkString(partType)) {

		    if (cp.isLastest()) {
			lastCpList.add(cp);
		    }
		    // CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partType);

		    // String taskCode = cpType.getTaskCode();
		    // if(!StringUtil.checkString(taskCode) || taskCode.indexOf(taskTypeCategory) >= 0) {
		    if (cp.getParent() == null) {

			if (cp.getSubVersion() == 0) {
			    originalPartNoMap.put(cp.getPartNo(), cp.getPartNo());
			}

			if (cp.getSubVersion() != 0) {
			    // bPartNoList.add(cp.getPartNo());
			    bPartNoMap.put(cp.getPartNo(), cp.getPartNo());
			}

			if (cp.isLastest() && cp.getSubVersion() != 0) {
			    // lPartNoList.add(cp.getPartNo());
			    lPartNoMap.put(cp.getPartNo(), cp.getPartNo());
			}
		    }
		    // }
		}
	    }

	    cpList = lastCpList;

	    String lastCaseCheckMsg = "";

	    Set<String> st = originalPartNoMap.keySet();
	    Iterator<String> it = st.iterator();
	    while (it.hasNext()) {
		String key = (String) it.next();
		if (!lPartNoMap.containsKey(key)) {
		    lastCaseCheckMsg += key + ",";
		}
	    }

	    if (lPartNoMap.size() != bPartNoMap.size() || StringUtils.isNotEmpty(lastCaseCheckMsg)) {
		lastCaseCheckMsg = "제품 " + lastCaseCheckMsg;
		st = bPartNoMap.keySet();
		it = st.iterator();
		while (it.hasNext()) {
		    String key = (String) it.next();
		    if (!lPartNoMap.containsKey(key)) {
			lastCaseCheckMsg += key + ",";
		    }
		}

		/*
	         * for(String partNo : bPartNoList) { if(!lPartNoList.contains(partNo)) { message += partNo + ","; } }
	         */

		lastCaseCheckMsg = lastCaseCheckMsg.substring(0, lastCaseCheckMsg.lastIndexOf(","));
		lastCaseCheckMsg += "  의 최종안이 없습니다.";

		resMap.put("checkResult", false);
		resMap.put("message", lastCaseCheckMsg);
		isError = true;
	    } else if (bPartNoMap.size() == 0) {
		String message = "복사본이 생성되지 않았습니다.";

		resMap.put("checkResult", false);
		resMap.put("message", message);
		isError = true;
	    }
	}

	if ("COST016".equals(taskTypeCategory) && !isError) {// 원가보고서

	    List<CostPart> lastCpList = new ArrayList<CostPart>();

	    for (CostPart cp : cpList) {
		if (cp.isLastest()) {
		    lastCpList.add(cp);
		}
	    }
	    cpList = lastCpList;

	    if (project instanceof ProductProject) {

		String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

		if (!("DR0".equals(drNo) || "DR1".equals(drNo))) {

		    String msg = CostUtil.manager.getCheckRealPartNoMsg(cpList);

		    if (StringUtils.isNotEmpty(msg)) {
			msg = StringUtils.removeEnd(msg, ",");
			resMap.put("checkResult", false);
			resMap.put("message", "실제 품번 연동 작업이 필요합니다." + msg);
			isError = true;
		    }

		}

	    }
	}

	E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	PartList partList = CostUtil.manager.getPartList(pjtMaster);

	int lastestVersion = 0;

	if (partList != null) {

	    lastestVersion = partList.getLastestVersion();

	    if (Integer.parseInt(task.getCostVersion()) != lastestVersion) {

		resMap.put("checkResult", false);
		resMap.put("message", "현재 요청서의 버전이 최신이 아닙니다.");
		isError = true;
	    }
	}

	if (cpList.size() == 0) {
	    resMap.put("checkResult", false);
	    resMap.put("message", "PartList가 존재하지 않습니다.");
	    isError = true;
	}

	LOGGER.info("bomEditorNeedCheck checkResult###### " + resMap.get("checkResult"));

	if (!isError) {
	    resMap = BomEditorACLUtil.manager.checkFromPartTypeResult(cpList, taskTypeCategory);
	    LOGGER.info("checkFromPartTypeResult checkResult###### " + resMap.get("checkResult"));
	}

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 6. 오후 5:41:37
     * @method treePartTypeCheck
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> treePartTypeCheck(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> rows = (Map<String, Object>) reqMap.get("rows");
	Map<String, Object> resMap = new HashMap<String, Object>();
	List<String> errorList = new ArrayList<String>();

	Set<String> st = rows.keySet();
	Iterator<String> it = st.iterator();

	boolean isError = false;
	String message = null;

	// WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	// boolean isCostAdmin = CommonUtil.isMember("원가관리", user);

	while (it.hasNext()) {
	    String id = it.next();

	    Map<String, Object> row = (Map<String, Object>) rows.get(id);
	    System.out.println(row);

	    String partTypeOid = (String) row.get("partType");
	    String level = (String) row.get("Level");
	    boolean hasChild = Boolean.parseBoolean((String) row.get("hasChild"));
	    String parentId = (String) row.get("parent");
	    // boolean typeChanged = Boolean.parseBoolean((String) row.get("typeChanged"));

	    if (!StringUtil.checkString(partTypeOid)) {
		if (message == null)
		    message = "부품구분을 입력하세요.";
		if (!isError)
		    isError = true;
		errorList.add(id);
	    } else {

		CostPartType partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);

		String partTypeName = partType.getName();
		String partTypeCode = partType.getCode();
		String stdLevel = partType.getLvl();
		String lvOption = partType.getLvlOption();
		String needPCode = partType.getParentCfg();
		String needChild = partType.getChildCfg();

		// if ("양산품".equals(partTypeCode) && !isCostAdmin && typeChanged) {
		// if (message == null)
		// message = "양산품은 설계원가팀만 입력가능합니다.";
		// if (!isError)
		// isError = true;
		// errorList.add(id);
		// }

		if (StringUtil.checkString(lvOption)) {

		    String exp = level + lvOption + stdLevel;
		    if ((boolean) CostCalculateUtil.engine.eval(exp)) {
			if (message == null)
			    message = partTypeName + "의 부품 Level이 잘못 설정되었습니다. [" + partTypeName + " " + lvOption + " " + stdLevel
				    + " 이어야 합니다.]";
			if (!isError)
			    isError = true;
			errorList.add(id);
		    }
		}

		if (StringUtil.checkString(needChild)) {

		    if ("필수".equals(needChild) && !hasChild) {
			if (message == null)
			    message = partTypeName + "의 하위부품이 구성되지 않았습니다. 자부품을 추가하세요.";
			if (!isError)
			    isError = true;
			errorList.add(id);
		    } else if ("불가".equals(needChild) && hasChild) {
			if (message == null)
			    message = partTypeName + "의 하위부품 구성이 불가합니다. 자부품을 제거하세요.";
			if (!isError)
			    isError = true;
			errorList.add(id);
		    }
		}

		Map<String, Object> parentRow = (Map<String, Object>) rows.get(parentId);

		if (StringUtil.checkString(needPCode)) {

		    if (parentRow != null) {
			partTypeOid = (String) parentRow.get("partType");

			if (StringUtil.checkString(partTypeOid)) {
			    partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);

			    if (!needPCode.equals(partType.getCode())) {
				if (message == null)
				    message = partTypeName + "의 모부품이 구성되지 않았습니다. (" + needPCode + ")";
				if (!isError)
				    isError = true;
				errorList.add(id);
			    }

			} else {
			    if (message == null)
				message = partTypeName + "의 모부품이 구성되지 않았습니다. (" + needPCode + ")";
			    if (!isError)
				isError = true;
			    errorList.add(id);
			}

		    } else {
			if (message == null)
			    message = partTypeName + "의 모부품이 구성되지 않았습니다. (" + needPCode + ")";
			if (!isError)
			    isError = true;
			errorList.add(id);
		    }
		}
	    }
	}

	resMap.put("errorList", errorList);
	resMap.put("message", message);
	resMap.put("isError", isError);

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 6. 오후 5:41:43
     * @method getGenratePartNo
     * @param partList
     * @return int
     * @throws Exception
     * </pre>
     */
    public int getGenratePartNo(String pjtno) throws Exception {

	int costPartNo = 0;
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    // long listId = CommonUtil.getOIDLongValue(partList);

	    StringBuffer sql = new StringBuffer();

	    // sql.append("SELECT MAX(TO_NUMBER(SUBSTR(PARTNO,-3), '999')) + 1 AS NEWPARTNO FROM ( ");
	    // sql.append("SELECT DISTINCT A0.PARTNO FROM COSTPART A0,PRODUCTMASTER A1, COSTPARTTYPE A2");
	    // sql.append(" WHERE A0.PARTTYPE = A2.IDA2A2 AND A0.PARTNO LIKE CONCAT(SUBSTR(A0.PARTNO,0,INSTR(A0.PARTNO, '-', -1)-1),'%')");
	    // sql.append(" START WITH A0.IDA3A4 = A1.IDA2A2 AND A1.IDA3A4 = " + listId);
	    // sql.append(" CONNECT BY PRIOR A0.IDA2A2 = A0.IDA3PARENTREFERENCE)");

	    sql.append("SELECT MAX(TO_NUMBER(SUBSTR(PARTNO,-3))) + 1 AS NEWPARTNO FROM COSTPART WHERE PARTNO LIKE '" + pjtno + "%'");

	    stat = conn.getConnection().createStatement();

	    rs = stat.executeQuery(sql.toString());

	    if (rs.next()) {
		costPartNo = rs.getInt("NEWPARTNO");
	    }

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

	return costPartNo;
    }

    /**
     * <pre>
     *  @description 하위 CostPart Tree 구조로 가져오기
     *  @author dhkim
     *  @date 2017. 12. 6. 오전 10:17:34
     *  @method getCostTree
     *  @param rootObj
     *  @return DefaultMutableTreeNode
     *  @throws Exception
     * </pre>
     */
    public DefaultMutableTreeNode getCostTree(Persistable rootObj) throws Exception {

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	DefaultMutableTreeNode root = null;
	long rootId = CommonUtil.getOIDLongValue(rootObj);

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    String tableName = rootObj.getClass().getSimpleName().toUpperCase();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT 								 ");
	    sql.append(" CLASSNAMEA2A2 || ':' || IDA2A2 as OID,				 ");
	    sql.append(" CLASSNAMEKEYPARENTREFERENCE || ':' || IDA3PARENTREFERENCE as PID	 ");
	    sql.append(" FROM " + tableName);
	    sql.append(" WHERE 1 = 1								 ");
	    sql.append(" START WITH IDA3PARENTREFERENCE = " + rootId);
	    sql.append(" CONNECT BY PRIOR IDA2A2 = IDA3PARENTREFERENCE ");
	    sql.append(" ORDER SIBLINGS BY SORTLOCATION");

	    rs = stat.executeQuery(sql.toString());

	    Map<String, DefaultMutableTreeNode> parentMap = new HashMap<String, DefaultMutableTreeNode>();

	    String rootOid = CommonUtil.getOIDString(rootObj);

	    root = new DefaultMutableTreeNode(rootObj);

	    parentMap.put(rootOid, root);

	    while (rs.next()) {

		String oid = rs.getString("OID");
		String pid = rs.getString("PID");

		Persistable obj = CommonUtil.getObject(oid);

		DefaultMutableTreeNode child = new DefaultMutableTreeNode(obj);
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) parentMap.get(pid);

		parent.add(child);
		parentMap.put(oid, child);
	    }

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

	return root;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 13. 오후 3:32:57
     * @method getCostPartTreeMap
     * @param part
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getCostPartTreeMap(CostPart part) throws Exception {

	DefaultMutableTreeNode root = getCostTree(part);

	Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
	partMap.put("oid", CommonUtil.getOIDString(part));

	List<Map<String, Object>> cPartList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < root.getChildCount(); i++) {

	    DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);

	    Persistable obj = (Persistable) node.getUserObject();
	    Map<String, Object> cPartMap = CostUtil.manager.converObjectToMap(obj);
	    cPartMap.put("oid", CommonUtil.getOIDString(obj));

	    List<Map<String, Object>> dataList = costPartTreeMapSetting(node);
	    cPartMap.put("cPartList", dataList);
	    cPartList.add(cPartMap);
	}
	partMap.put("cPartList", cPartList);

	return partMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 13. 오후 3:33:26
     * @method costPartTreeMapSetting
     * @param parent
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    private List<Map<String, Object>> costPartTreeMapSetting(DefaultMutableTreeNode parent) throws Exception {

	List<Map<String, Object>> cPartList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < parent.getChildCount(); i++) {

	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("oid", CommonUtil.getOIDString(data));

	    List<Map<String, Object>> dataList = costPartTreeMapSetting(child);
	    dataMap.put("cPartList", dataList);
	    cPartList.add(dataMap);
	}

	return cPartList;
    }

    /**
     * <pre>
     *  @description 
     *  @author dhkim
     *  @date 2017. 12. 5. 오후 5:32:29
     *  @method getCostProductTreeList
     *  @param reqMap
     *  @return
     *  @throws Exception
     * </pre>
     */
    public List<DefaultMutableTreeNode> getCostTreeList(Map<String, Object> reqMap) throws Exception {

	List<Persistable> list = CostHelper.service.getCostRootList(reqMap);

	List<DefaultMutableTreeNode> nodeList = new ArrayList<DefaultMutableTreeNode>();

	for (Persistable root : list) {
	    DefaultMutableTreeNode node = getCostTree(root);
	    nodeList.add(node);
	}

	return nodeList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 30. 오전 10:09:07
     * @method getCostPartMap
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getCostPartMap(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> caMapList = new ArrayList<Map<String, Object>>();
	List<DefaultMutableTreeNode> nodeList = getCostTreeList(reqMap);

	for (DefaultMutableTreeNode node : nodeList) {

	    Persistable data = (Persistable) node.getUserObject();

	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("id", CommonUtil.getOIDString(data));

	    dataList.add(dataMap);

	    costPartMapSetting(node, dataList);

	}

	CostPart part = (CostPart) CommonUtil.getObject((String) reqMap.get("oid"));

	E3PSTask task = (E3PSTask) CommonUtil.getObject((String) reqMap.get("taskOid"));

	String salesTargetCostExpr = StringUtil.checkReplaceStr(part.getSalesTargetCostExpr(), "0");
	String materialCostTotal = "";
	String laborCostTotal = "";
	String expenseTotal = "";
	String mfcCostTotal = "";
	String manageCostTotal = "";
	String reduceCostTotal = "";
	String productCostTotal = "";

	for (Map<String, Object> data : dataList) {

	    String materialCostExpr = (String) data.get("materialCostExpr");
	    String laborCostExpr = (String) data.get("laborCostExpr");
	    String expenseExpr = (String) data.get("expenseExpr");
	    String mfcCostExpr = (String) data.get("mfcCostExpr");
	    String manageCostExpr = (String) data.get("manageCostExpr");
	    String reduceCostExpr = (String) data.get("reduceCostExpr");
	    String totalCostExpr = (String) data.get("totalCostExpr");
	    String us = (String) data.get("us");

	    materialCostExpr = (String) CostCalculateUtil.manager.eval(materialCostExpr + "*" + us);
	    laborCostExpr = (String) CostCalculateUtil.manager.eval(laborCostExpr + "*" + us);
	    expenseExpr = (String) CostCalculateUtil.manager.eval(expenseExpr + "*" + us);
	    mfcCostExpr = (String) CostCalculateUtil.manager.eval(mfcCostExpr + "*" + us);
	    manageCostExpr = (String) CostCalculateUtil.manager.eval(manageCostExpr + "*" + us);
	    reduceCostExpr = (String) CostCalculateUtil.manager.eval(reduceCostExpr + "*" + us);
	    totalCostExpr = (String) CostCalculateUtil.manager.eval(totalCostExpr + "*" + us);

	    materialCostTotal += "+" + materialCostExpr;
	    laborCostTotal += "+" + laborCostExpr;
	    expenseTotal += "+" + expenseExpr;
	    mfcCostTotal += "+" + mfcCostExpr;
	    manageCostTotal += "+" + manageCostExpr;
	    reduceCostTotal += "+" + reduceCostExpr;
	    productCostTotal += "+" + totalCostExpr;

	}
	materialCostTotal = (String) CostCalculateUtil.manager.eval(materialCostTotal);
	laborCostTotal = (String) CostCalculateUtil.manager.eval(laborCostTotal);
	expenseTotal = (String) CostCalculateUtil.manager.eval(expenseTotal);
	mfcCostTotal = (String) CostCalculateUtil.manager.eval(mfcCostTotal);
	manageCostTotal = (String) CostCalculateUtil.manager.eval(manageCostTotal);
	reduceCostTotal = (String) CostCalculateUtil.manager.eval(reduceCostTotal);
	productCostTotal = (String) CostCalculateUtil.manager.eval(productCostTotal);

	String profitRateExpr = "1-(" + productCostTotal + "/" + salesTargetCostExpr + ")";
	profitRateExpr = (String) CostCalculateUtil.manager.eval(profitRateExpr);

	for (Map<String, Object> data : dataList) {

	    String materialCostExpr = (String) data.get("materialCostExpr");
	    String laborCostExpr = (String) data.get("laborCostExpr");
	    String expenseExpr = (String) data.get("expenseExpr");
	    String mfcCostExpr = (String) data.get("mfcCostExpr");
	    String manageCostExpr = (String) data.get("manageCostExpr");
	    String reduceCostExpr = (String) data.get("reduceCostExpr");
	    String totalCostExpr = (String) data.get("totalCostExpr");
	    String us = (String) data.get("us");

	    materialCostExpr = (String) CostCalculateUtil.manager.eval(materialCostExpr + "*" + us);
	    laborCostExpr = (String) CostCalculateUtil.manager.eval(laborCostExpr + "*" + us);
	    expenseExpr = (String) CostCalculateUtil.manager.eval(expenseExpr + "*" + us);
	    mfcCostExpr = (String) CostCalculateUtil.manager.eval(mfcCostExpr + "*" + us);
	    manageCostExpr = (String) CostCalculateUtil.manager.eval(manageCostExpr + "*" + us);
	    reduceCostExpr = (String) CostCalculateUtil.manager.eval(reduceCostExpr + "*" + us);
	    totalCostExpr = (String) CostCalculateUtil.manager.eval(totalCostExpr + "*" + us);

	    data.put("materialCostExpr", materialCostExpr);
	    data.put("laborCostExpr", laborCostExpr);
	    data.put("expenseExpr", expenseExpr);
	    data.put("mfcCostExpr", mfcCostExpr);
	    data.put("manageCostExpr", manageCostExpr);
	    data.put("reduceCostExpr", reduceCostExpr);
	    data.put("totalCostExpr", totalCostExpr);
	    data.put("us", us);

	}

	resMap.put("materialCostTotal", materialCostTotal);
	resMap.put("laborCostTotal", laborCostTotal);
	resMap.put("expenseTotal", expenseTotal);
	resMap.put("mfcCostTotal", mfcCostTotal);
	resMap.put("manageCostTotal", manageCostTotal);
	resMap.put("reduceCostTotal", reduceCostTotal);
	resMap.put("productCostTotal", productCostTotal);
	resMap.put("salesTargetCostExpr", salesTargetCostExpr);
	resMap.put("profitRateExpr", profitRateExpr);

	if (task.getTaskState() == 5) {

	    List<CostQuantity> qtyList = CostCodeHelper.service.getLastestCostQuantity(part);

	    for (int i = 0; i < qtyList.size(); i++) {
		CostQuantity qty = qtyList.get(i);
		String year = qty.getYear();
		CostAnalysis ca = CostUtil.manager.getCostAnalysis(part, year);

		Map<String, Object> caMap = CostUtil.manager.converObjectToMap(ca);
		caMap.put("id", CommonUtil.getOIDString(ca));
		caMapList.add(caMap);
	    }

	} else {

	    CostUtil.manager.convertMapToObject(resMap, part);

	    Transaction trx = new Transaction();

	    try {

		trx.start();

		part = (CostPart) PersistenceHelper.manager.save(part);

		List<CostAnalysis> caList = CostHelper.service.saveCostAnalysis(part);

		for (CostAnalysis ca : caList) {
		    Map<String, Object> caMap = CostUtil.manager.converObjectToMap(ca);
		    caMap.put("id", CommonUtil.getOIDString(ca));
		    caMapList.add(caMap);
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

	resMap.put("dataList", dataList);
	resMap.put("caList", caMapList);

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 30. 오전 10:09:12
     * @method costPartDataSetting
     * @param parent
     * @param dataList
     * @throws Exception
     * </pre>
     */
    private void costPartMapSetting(DefaultMutableTreeNode parent, List<Map<String, Object>> dataList) throws Exception {

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    dataMap.put("id", CommonUtil.getOIDString(data));

	    dataList.add(dataMap);

	    costPartMapSetting(child, dataList);
	}
    }

    /**
     * <pre>
     * @description 하위 부품 전체 가져오기
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:41:04
     * @method getCostPartList
     * @param reqMap
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    public List<CostPart> getCostPartList(Map<String, Object> reqMap) throws Exception {

	reqMap.put("DATATYPE", "COSTPART");
	List<CostPart> dataList = new ArrayList<CostPart>();
	List<DefaultMutableTreeNode> nodeList = getCostTreeList(reqMap);

	for (DefaultMutableTreeNode node : nodeList) {

	    CostPart data = (CostPart) node.getUserObject();

	    dataList.add(data);

	    costPartSetting(node, dataList);

	}
	String noSortOption = StringUtil.checkNull((String) reqMap.get("noSortOption"));

	if (StringUtils.isEmpty(noSortOption)) {
	    Collections.sort(dataList, new Comparator<CostPart>() {
		public int compare(CostPart obj1, CostPart obj2) {

		    int x = (int) obj1.getSortLocation();
		    int y = (int) obj2.getSortLocation();

		    if (x > y)
			return 1;
		    else if (x < y)
			return -1;
		    else
			return 0;
		}
	    });
	}

	return dataList;
    }

    /**
     * <pre>
     * @description  하위 부품 전체 가져오기
     * @author dhkim
     * @date 2018. 3. 5. 오후 7:20:57
     * @method getCostPartList
     * @param part
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    public List<CostPart> getCostPartList(CostPart part) throws Exception {

	DefaultMutableTreeNode root = getCostTree(part);

	List<CostPart> dataList = new ArrayList<CostPart>();

	CostPart data = (CostPart) root.getUserObject();

	dataList.add(data);

	costPartSetting(root, dataList);

	return dataList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:41:20
     * @method costPartSetting
     * @param parent
     * @param dataList
     * @throws Exception void
     * </pre>
     */
    public void costPartSetting(DefaultMutableTreeNode parent, List<CostPart> dataList) throws Exception {

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    CostPart data = (CostPart) child.getUserObject();

	    dataList.add(data);

	    costPartSetting(child, dataList);
	}
    }

    /**
     * <pre>
     *  @description Data를 Grid에서 사용하는 Data Format으로 가공
     *  @author dhkim
     *  @date 2017. 12. 6. 오전 9:13:43
     *  @method getTreeGridData
     *  @param reqMap
     *  @return List<Map<String,Object>>
     *  @throws Exception
     * </pre>
     */
    public List<Map<String, Object>> getTreeGridData(Map<String, Object> reqMap) throws Exception {

	boolean FILTERMODE = Boolean.parseBoolean(String.valueOf(reqMap.get("FILTERMODE")));

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	List<DefaultMutableTreeNode> nodeList = getCostTreeList(reqMap);

	System.out.println("nodeList Size : " + nodeList.size());

	for (DefaultMutableTreeNode node : nodeList) {

	    Persistable data = (Persistable) node.getUserObject();
	    Map<String, Object> dataMap = setCostDataMap(data, reqMap);

	    dataList.add(dataMap);
	    if (FILTERMODE) {
		costTreeGridSettingList(node, dataList, reqMap);
	    } else {
		costTreeGridSetting(node, dataMap, reqMap);
	    }
	}
	return dataList;
    }

    public List<Map<String, Object>> getInterfaceTreeGridData(Map<String, Object> reqMap) throws Exception {
	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	CostInterfaceHistory rootPart = (CostInterfaceHistory) CommonUtil.getObject((String) reqMap.get("oid"));

	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(CostInterfaceChildHistory.class, true);

	qs.appendWhere(new SearchCondition(CostInterfaceChildHistory.class, CostInterfaceChildHistory.PARENT_HISTORY_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(rootPart)), new int[] { idx });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostInterfaceChildHistory.class, CostInterfaceChildHistory.PARENT_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, 0L), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);
	Persistable root = null;
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    root = (Persistable) o[0];
	}
	DefaultMutableTreeNode node = getCostTree(root);
	Persistable data = (Persistable) node.getUserObject();
	Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);

	dataList.add(dataMap);
	costInterfaceTreeGridSetting(node, dataMap);

	return dataList;
    }

    private void costInterfaceTreeGridSetting(DefaultMutableTreeNode parent, Map<String, Object> treeMap) throws Exception {

	ArrayList<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
	String columnKey = "";
	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	    if (StringUtils.isEmpty(columnKey)) {
		Set<String> st = dataMap.keySet();
		Iterator<String> it = st.iterator();

		while (it.hasNext()) {
		    String key = (String) it.next();
		    columnKey += key + ",";
		}
		columnKey = StringUtils.removeEnd(columnKey, ",");
	    }
	    String columnKeys[] = columnKey.split(",");
	    for (String key : columnKeys) {
		dataMap.put(key + "CanEdit", false);
		dataMap.put(key + "CanSelect", false);
	    }

	    childList.add(dataMap);
	    costInterfaceTreeGridSetting(child, dataMap);
	}

	if (childList.size() > 0)
	    treeMap.put("Items", childList);
    }

    public Map<String, Object> calcConvertProductCost(List<Map<String, Object>> data) throws Exception {
	Map<String, Object> convertMap = new HashMap<String, Object>();
	convertMap.put("productMaterialCostSum", "0");
	convertMap.put("productLaborCostExprSum", "0");
	convertMap.put("productRndExprSum", "0");
	convertMap.put("productfacReducePriceSum", "0");
	convertMap.put("productdirectCostSum", "0");
	convertMap.put("productinDirectCostSum", "0");
	convertMap.put("productmoldReducePriceSum", "0");
	convertMap.put("productoutUnitCostSum", "0");
	convertMap.put("productmtrmanageExprSum", "0");
	convertMap.put("productsalesManageCostSum", "0");
	convertMap.put("productscrapSalesCostExprSum", "0");

	costTreeConvert4ErpInterface(data, convertMap);

	String productMaterialCost = (String) convertMap.get("productMaterialCostSum");
	String productLaborCostExpr = (String) convertMap.get("productLaborCostExprSum");
	String productRndExpr = (String) convertMap.get("productRndExprSum");
	String productfacReducePrice = (String) convertMap.get("productfacReducePriceSum");
	String productdirectCost = (String) convertMap.get("productdirectCostSum");
	String productinDirectCost = (String) convertMap.get("productinDirectCostSum");
	String productmoldReducePrice = (String) convertMap.get("productmoldReducePriceSum");
	String productoutUnitCost = (String) convertMap.get("productoutUnitCostSum");
	String productmtrmanageExpr = (String) convertMap.get("productmtrmanageExprSum");
	String productsalesManageCost = (String) convertMap.get("productsalesManageCostSum");
	String productscrapSalesCostExpr = (String) convertMap.get("productscrapSalesCostExprSum");

	convertMap.put("materialCost", erpFormatConvert(productMaterialCost, 3));

	convertMap.put("laborCost", erpFormatConvert(productLaborCostExpr, 3));

	convertMap.put("inDirectLaborCost", erpFormatConvert(productRndExpr, 3));

	convertMap.put("facReducePrice", erpFormatConvert(productfacReducePrice, 3));

	convertMap.put("directCost", erpFormatConvert(productdirectCost, 3));

	convertMap.put("inDirectCost", erpFormatConvert(productinDirectCost, 3));

	convertMap.put("moldReducePrice", erpFormatConvert(productmoldReducePrice, 3));

	convertMap.put("outUnitCost", erpFormatConvert(productoutUnitCost, 3));

	convertMap.put("etcCost", erpFormatConvert(productmtrmanageExpr, 3));

	convertMap.put("salesManageCost", erpFormatConvert(productsalesManageCost, 3));

	convertMap.put("scrapSalesCost", erpFormatConvert(productscrapSalesCostExpr, 3));

	String productTotalCostExpr = (String) CostCalculateUtil.manager.eval(productMaterialCost + "+" + productLaborCostExpr + "+"
	        + productRndExpr + "+" + productfacReducePrice + "+" + productdirectCost + "+" + productinDirectCost + "+"
	        + productmoldReducePrice + "+" + productoutUnitCost + "+" + productmtrmanageExpr + "+" + productsalesManageCost + "-"
	        + productscrapSalesCostExpr);

	convertMap.put("productCostTotal", erpFormatConvert(productTotalCostExpr, 3));

	return convertMap;
    }

    public String erpFormatConvert(String data, int point) {

	if ("0".equals(data) || "0.0".equals(data) || StringUtils.isEmpty(data)) {
	    return "0.00";
	} else {
	    if (data.startsWith(".")) {
		data = "0" + data;
	    }
	    if (StringUtils.contains(data, ".")) {
		int decimalPoint = StringUtils.indexOf(data, ".") + point;
		data = StringUtils.substring(data, 0, decimalPoint);
	    } else {
		return data + ".00";
	    }

	}
	return data;
    }

    public void gridFootAdd(Map<String, Object> result, List<Map<String, Object>> data, Map<String, Object> reqMap) throws Exception {
	List<Object> Foot = new ArrayList<Object>();
	// Map<String, Object> convertMap = calcConvertProductCost(data);
	CostInterfaceHistory rootPart = (CostInterfaceHistory) CommonUtil.getObject((String) reqMap.get("oid"));
	Map<String, Object> convertMap = new HashMap<String, Object>();

	convertMap.put("materialCost", rootPart.getMaterialCost());

	convertMap.put("laborCost", rootPart.getLaborCost());

	convertMap.put("inDirectLaborCost", rootPart.getInDirectLaborCost());

	convertMap.put("facReducePrice", rootPart.getFacReducePrice());

	convertMap.put("directCost", rootPart.getDirectCost());

	convertMap.put("inDirectCost", rootPart.getInDirectCost());

	convertMap.put("moldReducePrice", rootPart.getMoldReducePrice());

	convertMap.put("outUnitCost", rootPart.getOutUnitCost());

	convertMap.put("etcCost", rootPart.getEtcCost());

	convertMap.put("salesManageCost", rootPart.getSalesManageCost());

	convertMap.put("scrapSalesCost", rootPart.getScrapSalesCost());

	convertMap.put("productCostTotal", rootPart.getProductCostTotal());

	convertMap.put("elecCostExpr", "");
	convertMap.put("packUnitPriceSum", "");
	convertMap.put("outUnitCostVal", "");
	convertMap.put("coManageExpr", "");

	convertMap.put("Spanned", 1);
	convertMap.put("partTypeNameSpan", "5");
	convertMap.put("partTypeName", "");

	convertMap.put("partTypeNameAlign", "center");
	convertMap.put("partTypeNameColor", "#E0F2F7");

	convertMap.put("usSpan", "5");
	convertMap.put("usColor", "#E0F2F7");

	convertMap.put("indirectRndRateSpan", "5");
	convertMap.put("indirectRndRateColor", "#E0F2F7");
	convertMap.put("indirectRndRate", "");

	convertMap.put("machineDpcCostExpr", "");
	convertMap.put("machineDpcCostExprColor", "#E0F2F7");
	convertMap.put("facReduceCost", "");
	convertMap.put("facReduceCostColor", "#E0F2F7");

	convertMap.put("elecCostExprSpan", "4");
	convertMap.put("elecCostExprColor", "#E0F2F7");

	convertMap.put("packUnitPriceSumSpan", "10");
	convertMap.put("packUnitPriceSumName", "");
	convertMap.put("packUnitPriceSumColor", "#E0F2F7");

	convertMap.put("outUnitCostValSpan", "4");
	convertMap.put("outUnitCostValName", "");
	convertMap.put("outUnitCostValColor", "#E0F2F7");

	convertMap.put("coManageExprSpan", "2");
	convertMap.put("coManageExprlName", "");
	convertMap.put("coManageExprColor", "#E0F2F7");

	convertMap.put("materialCostColor", "#FFA");

	convertMap.put("laborCostColor", "#FFA");

	convertMap.put("inDirectLaborCostColor", "#FFA");

	convertMap.put("facReducePriceColor", "#FFA");

	convertMap.put("directCostColor", "#FFA");

	convertMap.put("inDirectCostColor", "#FFA");

	convertMap.put("moldReducePriceColor", "#FFA");

	convertMap.put("outUnitCostColor", "#FFA");

	convertMap.put("etcCostColor", "#FFA");

	convertMap.put("salesManageCostColor", "#FFA");

	convertMap.put("scrapSalesCostColor", "#FFA");

	convertMap.put("productCostTotalColor", "#FE9A2E");

	Foot.add(convertMap);
	result.put("Foot", Foot);
    }

    @SuppressWarnings("unchecked")
    private void costTreeConvert4ErpInterface(List<Map<String, Object>> dataList, Map<String, Object> convertMap) throws Exception {

	/*
	 * ERP 전송대상 항목 수식 정리
	 * 
	 * 1.재료비 = 포장비합계 + 원재료비 + 생산Loss + 조립Loss --> materialCost = PACKUNITPRICEOPTION + RAWMATERIALCOSTEXPR + PRODUCTLOSSEXPR +
	 * assyLossExpr
	 * 
	 * 2.간접인건비 = (노무비 * 간전인건비비율(노무비) ) + (R&D비 * 간접인건비비율(R&D)) --> (laborCostExpr+"*"+indirectLabourRate) +
	 * (rndExpr+"*"+indirectRndRate)
	 * 
	 * 3.직접인건비 = 노무비(laborCostExpr) - (노무비 * 간전인건비비율(노무비) )
	 * 
	 * 4.설비감가비 = 설비감가비 + 기계감가 --> facReducePrice + machineDpcCostExpr
	 * 
	 * 5.직접경비 = 전력비 + 타발유 + 금형유지비 + 기타감가비 --> directCost = ELECCOSTEXPR + TABARYUEXPR + MOLDMAINTENANCE + etcReducePrice
	 * 
	 * 6.간접경비 = 개별포장비 합계 + 간접경비 + 간접경비2 + 원재료물류비 + 원재료관세 + 공정물류비 + 공정관세 + 납입물류비 + 납입관세 + 간접경비 R&D (R&D비 - 간접인건비(R&D)) --> inDirectCost =
	 * PACKUNITPRICESUM + INDIRECTCOSTEXPR+ INDIRECTCOST2EXPR + MANAGEMTRLOGISEXPR + MANAGEMTRDUTIEEXPR + MTRLTCOSTEXPR +
	 * MTRLTRATEEXPR+PAYCOSTLTEXPR + PAYRATELTEXPR + (rndExpr - indirectLabourRndCost)
	 * 
	 * 7.금형감가비 = moldReducePrice계
	 * 
	 * 8.외주가공비 = 외주단가 + 후도금단가 + 후도금비(옵션) + 법인간마진비용 --> outUnitCost = OUTUNITCOSTVAL + APUNITCOSTVAL + APUNITCOSTOPTION
	 * +CORPORATEMARGINCOSTEXP
	 * 
	 * 9.기타원가 = 재료관리비 --> etcCost = MTRMANAGEEXPR
	 * 
	 * 10.판관비 = 일반관리비 + 불량비용 --> salesManageCost = COMANAGEEXPR + DEFECTCOSTEXPR
	 * 
	 * 11.스크랩매출 = SCRAPSALESCOSTEXPR (스크랩판매비)
	 * 
	 * 2021.03.15 하위 부품 계산식 추가
	 * 
	 * 12. 재료비(수식) = 재료비-스크랩매출
	 */

	for (Map<String, Object> data : dataList) {
	    String us = (String) data.get("us");
	    String indirectLabourRate = (String) data.get("indirectLabourRate"); // 간접인건비비율(노무비)
	    String indirectRndRate = (String) data.get("indirectRndRate"); // 간접인건비비율(R&D)
	    String laborCostExpr = (String) CostCalculateUtil.manager.eval((String) data.get("laborCostExpr")); // 노무비
	    String rndExpr = (String) CostCalculateUtil.manager.eval((String) data.get("rndExpr")); // R&D비

	    String indirectLabourRndCost = (String) CostCalculateUtil.manager.eval("(" + rndExpr + "*" + indirectRndRate + ")");// 간접인건비(R&D)
	    String indirectLabourCost = (String) CostCalculateUtil.manager.eval("( (" + laborCostExpr + "*" + indirectLabourRate + ") + "
		    + indirectLabourRndCost + ") * " + us);// 간접인건비
	    String directLabourCost = (String) CostCalculateUtil.manager.eval("(" + laborCostExpr + "-(" + laborCostExpr + "*"
		    + indirectLabourRate + ")) * " + us);// 직접인건비
	    String indirectCostRnd = (String) CostCalculateUtil.manager.eval(rndExpr + "-" + indirectLabourRndCost);// 간접경비(R&D)

	    data.put("indirectCostRnd", indirectCostRnd);
	    data.put("indirectLabourRndCost", indirectLabourRndCost);
	    data.put("inDirectLaborCost", indirectLabourCost);

	    // 1.재료비 Start
	    String productMaterialCostSum = (String) convertMap.get("productMaterialCostSum");
	    String rawMaterialCostExpr = (String) data.get("rawMaterialCostExpr");

	    String mftFactory2 = (String) data.get("mftFactory2");
	    String mftFactory2Name = "";
	    if (StringUtil.checkString(mftFactory2)) {
		NumberCode mft2Code = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mftFactory2);
		if (mft2Code != null) {
		    mftFactory2Name = StringUtil.checkNull(mft2Code.getName());
		} else {
		    mftFactory2Name = mftFactory2;
		}

	    }

	    if ("무상".equals(mftFactory2Name.trim())) {
		rawMaterialCostExpr = "0";
	    }

	    String myMaterialCostSum = (String) CostCalculateUtil.manager.eval("(" + rawMaterialCostExpr + "+"
		    + data.get("packUnitPriceOption") + "+" + data.get("productLossExpr") + "+" + data.get("assyLossExpr") + ") * "
		    + data.get("us"));
	    productMaterialCostSum = (String) CostCalculateUtil.manager.eval(productMaterialCostSum + "+" + myMaterialCostSum);
	    data.put("materialCost", myMaterialCostSum);
	    // 1.재료비 End

	    // 2.직접인건비 Start
	    String productLaborCostExprSum = (String) convertMap.get("productLaborCostExprSum");
	    String myLaborCostExpr = directLabourCost;
	    productLaborCostExprSum = (String) CostCalculateUtil.manager.eval(productLaborCostExprSum + "+" + myLaborCostExpr);
	    data.put("laborCost", myLaborCostExpr);
	    // 2.직접인건비 End

	    // 3.간접인건비 Start
	    String productRndExprSum = (String) convertMap.get("productRndExprSum");
	    String myRndExpr = indirectLabourCost;
	    productRndExprSum = (String) CostCalculateUtil.manager.eval(productRndExprSum + "+" + myRndExpr);
	    // 3.간접인건비 End

	    // 4.설비감가비 Start
	    String productfacReducePriceSum = (String) convertMap.get("productfacReducePriceSum");
	    String myFacReducePrice = (String) CostCalculateUtil.manager.eval("("
		    + ((String) data.get("facReducePrice") + "+" + (String) data.get("machineDpcCostExpr")) + ") * " + data.get("us"));
	    productfacReducePriceSum = (String) CostCalculateUtil.manager.eval(productfacReducePriceSum + "+" + myFacReducePrice);
	    data.put("facReduceCost", (String) data.get("facReducePrice"));
	    data.put("facReducePrice", myFacReducePrice);
	    // 4.설비감가비 End

	    // 5.직접경비 Start
	    String productdirectCostSum = (String) convertMap.get("productdirectCostSum");
	    String myDirectCostSum = (String) CostCalculateUtil.manager.eval("(" + data.get("elecCostExpr") + "+" + data.get("tabaryuExpr")
		    + "+" + data.get("moldMaintenance") + "+" + data.get("etcReducePrice") + ") * " + data.get("us"));
	    productdirectCostSum = (String) CostCalculateUtil.manager.eval(productdirectCostSum + "+" + myDirectCostSum);
	    data.put("directCost", myDirectCostSum);
	    // 5.직접경비 End

	    // 6.간접경비 Start
	    String productinDirectCostSum = (String) convertMap.get("productinDirectCostSum");
	    String myProductinDirectCostSum = (String) CostCalculateUtil.manager.eval("(" + data.get("packUnitPriceSum") + "+"
		    + data.get("inDirectCostExpr") + "+" + data.get("inDirectCost2Expr") + "+" + data.get("manageMtrLogisExpr") + "+"
		    + data.get("manageMtrdutieExpr") + "+" + data.get("mtrLtCostExpr") + "+" + data.get("mtrLtRateExpr") + "+"
		    + data.get("payCostLtExpr") + "+" + data.get("payRateLtExpr") + "+" + indirectCostRnd + ") * " + data.get("us"));
	    productinDirectCostSum = (String) CostCalculateUtil.manager.eval(productinDirectCostSum + "+" + myProductinDirectCostSum);
	    data.put("inDirectCost", myProductinDirectCostSum);
	    // 6.간접경비 End

	    // 7.금형감가비 Start
	    String productmoldReducePriceSum = (String) convertMap.get("productmoldReducePriceSum");
	    String myMoldReducePrice = (String) CostCalculateUtil.manager.eval((String) data.get("moldReducePrice") + "*" + data.get("us"));
	    data.put("moldReducePrice", myMoldReducePrice);
	    productmoldReducePriceSum = (String) CostCalculateUtil.manager.eval(productmoldReducePriceSum + "+" + myMoldReducePrice);
	    // 7.금형감가비 End

	    // 8.외주가공비 Start
	    String productoutUnitCostSum = (String) convertMap.get("productoutUnitCostSum");
	    String myoutUnitCost = (String) CostCalculateUtil.manager.eval("(" + data.get("outUnitCostVal") + "+"
		    + data.get("apUnitCostVal") + "+" + data.get("apUnitCostOption") + "+" + data.get("corporateMarginCostExpr") + ") * "
		    + data.get("us"));
	    productoutUnitCostSum = (String) CostCalculateUtil.manager.eval(productoutUnitCostSum + "+" + myoutUnitCost);
	    data.put("outUnitCost", myoutUnitCost);
	    // 8.외주가공비 End

	    // 9.기타원가 Start
	    String productmtrmanageExprSum = (String) convertMap.get("productmtrmanageExprSum");
	    String mymtrManageExpr = (String) CostCalculateUtil.manager.eval((String) data.get("mtrManageExpr") + " * " + data.get("us"));
	    productmtrmanageExprSum = (String) CostCalculateUtil.manager.eval(productmtrmanageExprSum + "+" + mymtrManageExpr);
	    data.put("etcCost", mymtrManageExpr);
	    // 9.기타원가 End

	    // 10.판관비 Start
	    String productsalesManageCostSum = (String) convertMap.get("productsalesManageCostSum");
	    String mysalesManageCost = (String) CostCalculateUtil.manager.eval("(" + data.get("coManageExpr") + "+"
		    + data.get("defectCostExpr") + ") * " + data.get("us"));
	    productsalesManageCostSum = (String) CostCalculateUtil.manager.eval(productsalesManageCostSum + "+" + mysalesManageCost);
	    data.put("salesManageCost", mysalesManageCost);
	    // 10.판관비 End

	    // 11. 스크랩매출 Start
	    String productscrapSalesCostExprSum = (String) convertMap.get("productscrapSalesCostExprSum");
	    String myproductscrapSalesCostExpr = (String) CostCalculateUtil.manager.eval((String) data.get("scrapSalesCostExpr") + " * "
		    + data.get("us"));
	    productscrapSalesCostExprSum = (String) CostCalculateUtil.manager.eval(productscrapSalesCostExprSum + "+"
		    + myproductscrapSalesCostExpr);
	    data.put("scrapSalesCost", myproductscrapSalesCostExpr);
	    // 11.스크랩매출 End

	    // 12. 재료비(수식) Start
	    String myMaterialCostExpr = (String) CostCalculateUtil.manager.eval((String) data.get("materialCost") + "-"
		    + myproductscrapSalesCostExpr);
	    data.put("materialCostExpr", myMaterialCostExpr);
	    // 12. 재료비(수식) End

	    // 13. 가공비(수식) Start
	    String myProcessingCost = (String) CostCalculateUtil.manager.eval(myLaborCostExpr + "+" + myRndExpr + "+" + myFacReducePrice
		    + "+" + myDirectCostSum + "+" + myProductinDirectCostSum + "+" + myMoldReducePrice + "+" + myoutUnitCost + "+"
		    + mymtrManageExpr);
	    data.put("processingCost", myProcessingCost);

	    // 13. 가공비(수식) End

	    // 14.인시당생산량 & 투입입원(계산) 수식 Start
	    personQLH(data);
	    // 14.인시당생산량 & 투입입원(계산) 수식 End
	    String personQLH = (String) data.get("personQLH");

	    // 15. 가격차이-직접인건비 수식 Start
	    String myDfDirectLaborCost = (String) CostCalculateUtil.manager.eval(myLaborCostExpr + "*" + personQLH);
	    data.put("dfDirectLaborCost", myDfDirectLaborCost);
	    // 15. 가격차이-직접인건비 수식 End

	    // 16. 가격차이-간접인건비 수식 Start
	    String myDfInDirectLaborCost = (String) CostCalculateUtil.manager.eval(myRndExpr + "*" + personQLH);
	    data.put("dfInDirectLaborCost", myDfInDirectLaborCost);
	    // 16. 가격차이-간접인건비 수식 End

	    // 16. 가격차이-설비감가비 수식 Start
	    String myDfMachineDep = (String) CostCalculateUtil.manager.eval((String) data.get("outputExpr") + "*" + myFacReducePrice);
	    data.put("dfMachineDep", myDfMachineDep);
	    // 16. 가격차이-설비감가비 수식 End

	    // 17. 가격차이-직접경비 수식 Start
	    String MyDfDirectExpenses = (String) CostCalculateUtil.manager.eval((String) data.get("outputExpr") + "*" + myDirectCostSum);
	    data.put("dfDirectExpenses", MyDfDirectExpenses);
	    // 17. 가격차이-직접경비 수식 End

	    // 18. 가격차이-간접경비 수식 Start
	    String MyDfInDirectExpenses = (String) CostCalculateUtil.manager.eval((String) data.get("outputExpr") + "*"
		    + myProductinDirectCostSum);
	    data.put("dfInDirectExpenses", MyDfInDirectExpenses);
	    // 18. 가격차이-간접경비 수식 End

	    // 18. 가격차이-금형감가 수식 Start
	    String MydfMoldDep = (String) CostCalculateUtil.manager.eval((String) data.get("outputExpr") + "*" + myMoldReducePrice);
	    data.put("dfMoldDep", MydfMoldDep);
	    // 18. 가격차이-금형감가 수식 End

	    // 19.수량차이-CV 수식 Start
	    String cv = data.get("cvAssemble").equals("0") ? (String) data.get("cvMold") : (String) data.get("cvAssemble");
	    data.put("cv", cv);
	    // 19.수량차이-CV 수식 End

	    // 20.수량차이-CT 수식 Start
	    String dfCt = (String) CostCalculateUtil.manager.eval("(3600/" + (String) data.get("outputExpr") + ")*" + cv + "*"
		    + (String) data.get("efficientRate"));
	    data.put("dfCt", dfCt);
	    // 20.수량차이-CT 수식 Start

	    String myTotalCostExpr = (String) CostCalculateUtil.manager.eval(myMaterialCostSum + "+" + myLaborCostExpr + "+" + myRndExpr
		    + "+" + myFacReducePrice + "+" + myDirectCostSum + "+" + myProductinDirectCostSum + "+" + myMoldReducePrice + "+"
		    + myoutUnitCost + "+" + mymtrManageExpr + "+" + mysalesManageCost + "-" + myproductscrapSalesCostExpr);
	    data.put("productCostTotal", myTotalCostExpr);

	    String totalCostExpr = (String) CostCalculateUtil.manager.eval((String) data.get("totalCostExpr") + "*" + us);

	    data.put("orgProductCostTotal", erpFormatConvert(totalCostExpr, 3));

	    convertMap.put("productMaterialCostSum", productMaterialCostSum);
	    convertMap.put("productLaborCostExprSum", productLaborCostExprSum);
	    convertMap.put("productRndExprSum", productRndExprSum);
	    convertMap.put("productfacReducePriceSum", productfacReducePriceSum);
	    convertMap.put("productdirectCostSum", productdirectCostSum);
	    convertMap.put("productinDirectCostSum", productinDirectCostSum);
	    convertMap.put("productmoldReducePriceSum", productmoldReducePriceSum);
	    convertMap.put("productoutUnitCostSum", productoutUnitCostSum);
	    convertMap.put("productmtrmanageExprSum", productmtrmanageExprSum);
	    convertMap.put("productsalesManageCostSum", productsalesManageCostSum);
	    convertMap.put("productscrapSalesCostExprSum", productscrapSalesCostExprSum);

	    if (data.get("Items") != null) {
		costTreeConvert4ErpInterface((ArrayList<Map<String, Object>>) data.get("Items"), convertMap);
	    }
	}

    }

    public void personQLH(Map<String, Object> data) throws Exception {
	String partType = (String) data.get("partType");// 관리대수
	String worker = (String) data.get("worker");// 투입인원
	String unitManage = (String) CostCalculateUtil.manager.eval("1 / " + (String) data.get("unitManage"));// 투입인원

	Map<String, Object> convertWorker = new HashMap<String, Object>();
	convertWorker.put("1149659833", unitManage);// HSG(일반)
	convertWorker.put("1149659834", worker);// HSG(Insert-수평)
	convertWorker.put("1149659835", worker);// HSG(Insert-수직)
	convertWorker.put("1186632523", unitManage);// HSG(Proto)

	convertWorker.put("1149659837", unitManage);// TML(일반)
	convertWorker.put("1149659838", unitManage);// TML(PCB)
	convertWorker.put("1149659839", unitManage);// TML(복합)
	convertWorker.put("1186632525", unitManage);// TML(Proto)

	convertWorker.put("1149659841", worker); // 조립(단순)
	convertWorker.put("1149659842", worker); // 조립(수동)
	convertWorker.put("1149659843", worker); // 조립(반자동)
	convertWorker.put("1149659844", worker); // 조립(자동)
	convertWorker.put("1149659845", worker); // 조립(사출포함)
	convertWorker.put("1149659846", worker); // 조립(분단벤딩)
	convertWorker.put("1149659847", worker); // 조립(SMT)
	convertWorker.put("1149659848", worker); // 조립(W/H)
	convertWorker.put("1186632527", worker); // 조립(Proto)

	data.put("convertWorker", convertWorker.get(partType));
	// 인시당생산량 = 시간당생산량/투입인원
	String personQLH = (String) CostCalculateUtil.manager.eval((String) data.get("outputExpr") + "/"
	        + StringUtil.checkNullZero((String) convertWorker.get(partType)));
	data.put("personQLH", personQLH);
    }

    /**
     * <pre>
     * @description LIST SETTING
     * @author dhkim
     * @date 2018. 5. 9. 오후 3:50:36
     * @method costTreeGridSettingList
     * @param parent
     * @param dataList
     * @param reqMap
     * @throws Exception
     * </pre>
     */
    private void costTreeGridSettingList(DefaultMutableTreeNode parent, List<Map<String, Object>> dataList, Map<String, Object> reqMap)
	    throws Exception {

	ArrayList<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = setCostDataMap(data, reqMap);

	    childList.add(dataMap);
	    costTreeGridSettingList(child, dataList, reqMap);
	}

	if (childList.size() > 0)
	    dataList.addAll(childList);
    }

    /**
     * <pre>
     *  @description TREE STRUCTURE SETTING
     *  @author dhkim
     *  @date 2017. 12. 6. 오전 10:17:48
     *  @method CostPartTreeSetting
     *  @param parent
     *  @param map
     *  @throws Exception
     * </pre>
     */
    private void costTreeGridSetting(DefaultMutableTreeNode parent, Map<String, Object> treeMap, Map<String, Object> reqMap)
	    throws Exception {

	ArrayList<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < parent.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    Persistable data = (Persistable) child.getUserObject();
	    Map<String, Object> dataMap = setCostDataMap(data, reqMap);

	    childList.add(dataMap);
	    costTreeGridSetting(child, dataMap, reqMap);
	}

	if (childList.size() > 0)
	    treeMap.put("Items", childList);
    }

    /**
     * <pre>
     *  @description CostPart -> Map 데이터로 변경 및 기타 값 설정
     *  @author dhkim
     *  @date 2017. 12. 6. 오전 10:22:37
     *  @method setCostPartDataMap
     *  @param data
     *  @return Map<String,Object>
     * </pre>
     * 
     * @param reqMap
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> setCostDataMap(Persistable data, Map<String, Object> reqMap) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject((String) reqMap.get("taskOid"));
	Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(data);
	dataMap.put("id", CommonUtil.getOIDString(data));
	String EDITMODE = (String) reqMap.get("EDITMODE");
	boolean FILTERMODE = Boolean.parseBoolean(String.valueOf(reqMap.get("FILTERMODE")));

	if (data instanceof CostPart) {

	    String taskCode = StringUtil.checkNull(task.getTaskTypeCategory());

	    CostPart part = (CostPart) data;

	    if ("NONPOPUP".equals(EDITMODE)) {// 원가산출화면의 탭에서 아이프레임으로 접속시 활용목적 - 현재 경비 탭의 금형유지비 값복사(moldMaintenance -> moldMaintenanceTemp)를
		                              // 위해 사용하고 있다. 2019.08.26 by 황정태
		dataMap.put("moldMaintenanceTemp", part.getMoldMaintenance());
	    }
	    ProductMaster master = part.getMaster();

	    boolean CALCAUTH = (Boolean) reqMap.get("CALCAUTH");

	    if (CALCAUTH && ("COST015".equals(taskCode) || "COST016".equals(taskCode))) {// 원가산출Task or 원가보고서 에서 접속시 최초버전에
		                                                                         // 대해서는 수정불가

		if (master != null && !"NONPOPUP".equals(EDITMODE) && !"VIEW_COMMON".equals(EDITMODE)) {

		    dataMap.put("partNameButton", "/plm/portal/images/icon_5.png");
		    dataMap.put("partNameOnClickSideButton", "javasciprt:openBomAttrPopup('CALCULATEPART', Row);");

		}

		if (0 == part.getSubVersion()) {
		    EDITMODE = "VIEW";
		}

	    }

	    if ("VIEW_COMMON".equals(EDITMODE)) {
		FILTERMODE = false;
	    }

	    String partNo = part.getPartNo();
	    String partTypeOid = part.getPartType();
	    String partTypeCode = null;

	    if (StringUtil.checkString(partTypeOid)) {

		CostPartType partType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);
		partTypeCode = partType.getCode();

		String partTypeTaskCode = StringUtil.checkNull(partType.getTaskCode());

		if (FILTERMODE && StringUtil.checkString(partTypeTaskCode) && partTypeTaskCode.indexOf(taskCode) < 0) {
		    dataMap.put("Visible", "0");
		    return dataMap;
		}

		String eNum = "";
		String eNumKeys = "";

		Map<String, String> childEnumProp = new HashMap<String, String>();
		// "'" + CommonUtil.getOIDLongValue2Str(paramMap.get("id")) + "'
		// List<Map<String, Object>> list =
		// CostCodeHelper.service.getCostFactoryTreeInfoByPartType(CommonUtil.getOIDLongValue2Str(partTypeOid));
		String typeLongOid = CommonUtil.getOIDLongValue2Str(partTypeOid);
		List<Map<String, Object>> list = CostCacheManager.getCostPTItem(typeLongOid);
		//
		// if (list == null) {
		// list = CostCodeHelper.service.getCostFactoryTreeInfoByPartType(typeLongOid);
		// CostCacheManager.updateCostPTByKey(typeLongOid, list);
		// }

		for (Map<String, Object> mftFactory : list) {

		    String pOid = (String) mftFactory.get("numberParentOid");
		    if (StringUtils.isNotEmpty(pOid)) {

			String childEnum = childEnumProp.get("mftFactory2Enum" + pOid);
			String childEnumKeys = childEnumProp.get("mftFactory2EnumKeys" + pOid);

			if (childEnum != null) {
			    childEnum += "|" + mftFactory.get("numberCodeName");
			    childEnumKeys += "|" + mftFactory.get("numberOidLong");
			} else {
			    childEnum = "|" + mftFactory.get("numberCodeName");
			    childEnumKeys = "|" + mftFactory.get("numberOidLong");
			}

			childEnumProp.put("mftFactory2Enum" + pOid, childEnum);
			childEnumProp.put("mftFactory2EnumKeys" + pOid, childEnumKeys);

		    } else {
			eNum += "|" + (String) mftFactory.get("numberCodeName");
			eNumKeys += "|" + (String) mftFactory.get("numberOidLong");
		    }
		}

		dataMap.put("partTypeCode", partTypeCode);
		dataMap.put("partTypeName", partType.getName());
		dataMap.put("mftFactory1Enum", eNum);
		dataMap.put("mftFactory1EnumKeys", eNumKeys);

		Set<String> st = childEnumProp.keySet();
		Iterator<String> it = st.iterator();

		while (it.hasNext()) {
		    String name = it.next();
		    dataMap.put(name, childEnumProp.get(name));
		}
	    }

	    String imgUrl = "/plm/cost/COSTPARTIMG/" + partNo + ".jpg?" + System.currentTimeMillis();

	    File copyImg = new File(CostPartImgUtil.STOREPATH + partNo + ".jpg");

	    if (!copyImg.exists()) {
		dataMap.put("partForm", "");
	    } else {
		dataMap.put("partForm", "<img src='" + imgUrl + "' style='width:60px;height:25px;margin:0;' />");
	    }

	    dataMap.put("estimated", part.getQuantityTotal());
	    dataMap.put("quantity", part.getPackBoxUnit());

	    if (!"NONPOPUP".equals(EDITMODE) && !"VIEW_COMMON".equals(EDITMODE)) {
		dataMap.put("itemNameButton", "/plm/portal/images/icon_5.png");
		dataMap.put("itemNameOnClickSideButton", "javasciprt:openBomAttrPopup('ETCIC', Row);");

		dataMap.put("quantityButton", "/plm/portal/images/icon_5.png");
		dataMap.put("quantityOnClickSideButton", "javasciprt:openBomAttrPopup('QUANTITY', Row);");

		if (master != null) {
		    dataMap.put("estimatedButton", "/plm/portal/images/icon_5.png");
		    dataMap.put("estimatedOnClickSideButton", "javasciprt:openBomAttrPopup('ESTIMATED', Row);");
		}
	    }
	    if ("VIEW".equals(EDITMODE) || "VIEW_EXCEL".equals(EDITMODE) || "VIEW_COMMON".equals(EDITMODE)) {
		List<BomEditorColumnInfo> columns = CostBomEditorColumnExcelUtil.getColumnInfos();

		for (BomEditorColumnInfo column : columns) {
		    String columnKey = column.getColumKey();
		    dataMap.put(columnKey + "CanEdit", false);
		    dataMap.put(columnKey + "CanSelect", false);
		}
	    } else {

		List<BomEditorACL> popupACL = new ArrayList<BomEditorACL>();

		boolean isPreCostEdit = true;
		boolean isApCostEdit = true;

		boolean isMoldNFactoryEdit = true; // 금형 투자비 - 신규 - 제작처
		boolean isMoldNICEdit = true; // 금형 투자비 - 신규 - 투자비
		boolean isMoldNICMUnitEdit = true; // 금형 투자비 - 신규 - 투자비 화폐단위
		boolean isMoldNPayEdit = true; // 금형 투자비 - 신규 - 지급액
		boolean isMoldNPayMUnitEdit = true; // 금형 투자비 - 신규 - 지급액 화폐단위

		boolean isMoldMPFactoryEdit = true; // 금형 투자비 - 양산 - 제작처
		boolean isMoldMPICEdit = true; // 금형 투자비 - 양산 - 투자비
		boolean isMoldMPICMUnitEdit = true; // 금형 투자비 - 양산 - 투자비 화폐단위
		boolean isMoldMPQTotalEdit = true; // 금형 투자비 - 양산수량 - Total
		boolean isMoldMPQMaxEdit = true; // 금형 투자비 - 양산수량 - MAX

		boolean isFacNFactoryEdit = true; // 설비 투자비 - 신규 - 제작처
		boolean isFacNICEdit = true; // 설비 투자비 - 신규 - 투자비
		boolean isFacNICMUnitEdit = true; // 설비 투자비 - 신규 - 투자비 화폐단위
		boolean isFacNPayEdit = true; // 설비 투자비 - 신규 - 지급액
		boolean isFacNPayMUnitEdit = true; // 설비 투자비 - 신규 - 지급액 화폐단위

		boolean isFacMPFactoryEdit = true; // 설비 투자비 - 양산 - 제작처
		boolean isFacMPICEdit = true; // 설비 투자비 - 양산 - 투자비
		boolean isFacMPICMUnitEdit = true; // 설비 투자비 - 양산 - 투자비 화폐단위
		boolean isFacMPQTotalEdit = true; // 설비 투자비 - 양산수량 - Total
		boolean isFacMPQMaxEdit = true; // 설비 투자비 - 양산수량 - Max

		// if (!"NONPOPUP".equals(EDITMODE) && "양산품".equals(partTypeCode)) {
		if (!"NONPOPUP".equals(EDITMODE)) { // 모든 부품Type에 대해 부품번호 입력가능하도록 변경 2020.04.23
		    dataMap.put("realPartNoIcon", "/plm/portal/images/icon_5.png");
		    dataMap.put("realPartNoIconAlign", "Right");
		    dataMap.put("realPartNoOnClickSideIcon", "javascript:openBomAttrPopup('REALPARTNO', Row);");

		    dataMap.put("realPartNoButton", "/plm/portal/images/icon_delete.gif");
		    dataMap.put("realPartNoOnClickSideButton", "javasciprt:deleteRealPartNo(Row);");
		}

		if (!"CALCULATE".equals(EDITMODE) && !"NONPOPUP".equals(EDITMODE)) {

		    dataMap.put("partTypeNameColor", "#DFE");
		    dataMap.put("mftFactory1Color", "#DFE");
		    dataMap.put("mftFactory2Color", "#DFE");

		    String mftFactory1 = part.getMftFactory1();
		    String mftFactory2 = part.getMftFactory2();

		    String aclKey = CostPartType.class.getName() + ":" + partTypeOid + "*" + mftFactory1 + "*" + mftFactory2;

		    Map<String, BomEditorACLFromType> typeACL = BomEditorACLUtil.manager.getAclMapForType(aclKey);

		    Set<String> st = typeACL.keySet();
		    Iterator<String> it = st.iterator();

		    while (it.hasNext()) {
			String column = it.next();

			BomEditorACLFromType bomACLType = typeACL.get(column);

			if (bomACLType.isNecessary()) {
			    dataMap.put(column + "Color", "#DFE");
			}
		    }

		    Map<String, BomEditorACL> aclMap = BomEditorACLUtil.manager.getAclMap(taskCode);

		    st = aclMap.keySet();
		    it = st.iterator();

		    while (it.hasNext()) {
			String key = it.next();
			BomEditorACL aclData = aclMap.get(key);
			boolean isEdit = aclData.isEditor();

			if ("productWeight".equals(key) || "scrapWeight".equals(key) || "totalWeight".equals(key)) {

			    if ("MOLD".equals(partTypeCode)) {

				if ("scrapWeight".equals(key)) {
				    dataMap.put("scrapWeightCanEdit", isEdit);
				}
				if ("productWeight".equals(key)) {
				    dataMap.put("productWeightCanEdit", isEdit);
				}

			    } else if ("PRESS".equals(partTypeCode)) {

				dataMap.put("scrapWeightCanEdit", false);

				if ("productWeight".equals(key)) {
				    dataMap.put("productWeightCanEdit", isEdit);
				}

			    } else {
				dataMap.put("scrapWeightCanEdit", false);
				dataMap.put("productWeightCanEdit", false);
			    }

			} else if (!"POPUP".equals(aclData.getDescription())) {
			    if (!("etcNPFactory".equals(key) || "etcNIC".equals(key) || "etcNPay".equals(key) || "etcMPFactory".equals(key)
				    || "etcMPIC".equals(key) || "etcMPQTotal".equals(key) || "etcMPQMax".equals(key))) {
				dataMap.put(key + "CanEdit", isEdit);
				dataMap.put(key + "CanSelect", isEdit);
			    }
			    if ("metalPartNo".equals(key)) {
				dataMap.put(key + "Tip", "autoComplete");
			    }
			} else {
			    popupACL.add(aclData);
			}

			if ("prePlatingCost".equals(key))
			    isPreCostEdit = aclData.isEditor();
			if ("apUnitCost".equals(key))
			    isApCostEdit = aclData.isEditor();

			if ("moldNFactory".equals(key))
			    isMoldNFactoryEdit = aclData.isEditor();
			if ("moldNIC".equals(key))
			    isMoldNICEdit = aclData.isEditor();
			if ("moldNICMUnit".equals(key))
			    isMoldNICMUnitEdit = aclData.isEditor();
			if ("moldNPay".equals(key))
			    isMoldNPayEdit = aclData.isEditor();
			if ("moldNPayMUnit".equals(key))
			    isMoldNPayMUnitEdit = aclData.isEditor();

			if ("moldMPFactory".equals(key))
			    isMoldMPFactoryEdit = aclData.isEditor();
			if ("moldMPIC".equals(key))
			    isMoldMPICEdit = aclData.isEditor();
			if ("moldMPICMUnit".equals(key))
			    isMoldMPICMUnitEdit = aclData.isEditor();
			if ("moldMPQTotal".equals(key))
			    isMoldMPQTotalEdit = aclData.isEditor();
			if ("moldMPQMax".equals(key))
			    isMoldMPQMaxEdit = aclData.isEditor();

			if ("facNFactory".equals(key))
			    isFacNFactoryEdit = aclData.isEditor();
			if ("facNIC".equals(key))
			    isFacNICEdit = aclData.isEditor();
			if ("facNICMUnit".equals(key))
			    isFacNICMUnitEdit = aclData.isEditor();
			if ("facNPay".equals(key))
			    isFacNPayEdit = aclData.isEditor();
			if ("facNPayMUnit".equals(key))
			    isFacNPayMUnitEdit = aclData.isEditor();

			if ("facMPFactory".equals(key))
			    isFacMPFactoryEdit = aclData.isEditor();
			if ("facMPIC".equals(key))
			    isFacMPICEdit = aclData.isEditor();
			if ("facMPICMUnit".equals(key))
			    isFacMPICMUnitEdit = aclData.isEditor();
			if ("facMPQTotal".equals(key))
			    isFacMPQTotalEdit = aclData.isEditor();
			if ("facMPQMax".equals(key))
			    isFacMPQMaxEdit = aclData.isEditor();

		    }
		} else {

		    if ("MOLD".equals(partTypeCode)) {
			dataMap.put("scrapWeightCanEdit", true);
			dataMap.put("productWeightCanEdit", true);
		    } else if ("PRESS".equals(partTypeCode)) {
			dataMap.put("scrapWeightCanEdit", false);
			dataMap.put("productWeightCanEdit", true);
		    } else {
			dataMap.put("scrapWeightCanEdit", false);
			dataMap.put("productWeightCanEdit", false);
		    }
		}

		// 선도금 사양이 [Ag도금, Au도금, 기타도금] & 선도금단가 에디터 일 경우 선도금화폐단위, 선도금단가를 파랑색으로 표시한다.
		if (isPreCostEdit) {
		    if ("PTG003".equals(part.getPrePlatingSpec()) || "PTG004".equals(part.getPrePlatingSpec())
			    || "PTG005".equals(part.getPrePlatingSpec())) {
			dataMap.put("prePlatingCostCanEdit", "1");
			dataMap.put("prePlatingCostColor", "#B4EEF2");
			dataMap.put("prePlatingUnitCanEdit", "1");
			dataMap.put("prePlatingUnitColor", "#B4EEF2");
		    } else {
			dataMap.put("prePlatingCostCanEdit", "0");
			dataMap.put("prePlatingUnitCanEdit", "0");
		    }
		}

		// 후도금 사양이 있음 & 후도금단가 에디터 일 경우 후도금화폐단위, 후도금단가를 파랑색으로 표시한다.
		if (isApCostEdit && StringUtil.checkString(part.getApPlatingSpec()) && !"PTG010".equals(part.getApPlatingSpec())) {
		    dataMap.put("apUnitCostCanEdit", "1");
		    dataMap.put("apUnitCostColor", "#B4EEF2");
		    dataMap.put("apMonetaryUnitCanEdit", "1");
		    dataMap.put("apMonetaryUnitColor", "#B4EEF2");
		} else {
		    dataMap.put("apUnitCostCanEdit", "0");
		    dataMap.put("apMonetaryUnitCanEdit", "0");
		}

		/*******************************************************/
		/*******************************************************/
		/*******************************************************/
		/*****  					  ******/
		/*****  					  ******/
		/***** 금형투자비, 설비투자비 관련 항목 하드코딩 Start ******/
		/*****  					  ******/
		/*****  					  ******/
		/*******************************************************/
		/*******************************************************/
		/*******************************************************/

		// 금형투자비, 설비투자비 관련 항목은 기본적으로 설정에 의해 돌아가지만 특정경우에 비활성화할 필요가 있기 때문에 하드코딩한다 costBomEditor.jsp 에서도 해당 항목들은 색상표시 로직을 타지 않게 함

		String moldMftDivision = part.getMoldMftDivision();
		String facMftDivision = part.getFacMftDivision();

		boolean ismMdv = StringUtils.isEmpty(moldMftDivision);
		boolean isfMdv = StringUtils.isEmpty(facMftDivision);

		boolean isMoldMass = "MDV003".equals(moldMftDivision);
		boolean isMoldNew = "MDV001".equals(moldMftDivision) || "MDV004".equals(moldMftDivision);

		boolean isFacMass = "FDV003".equals(facMftDivision);
		boolean isFacNew = "FDV001".equals(facMftDivision) || "FDV004".equals(facMftDivision);

		boolean isPassTaskCode = "COST003".equals(taskCode) || "COST004".equals(taskCode) || "COST005".equals(taskCode); // 예비공법검토서,//
		                                                                                                                 // 사출금형,프레스금형

		boolean isMoldNotApply = "MDV006".equals(moldMftDivision); // 해당없음
		boolean isFacNotApply = "FDV006".equals(facMftDivision); // 해당없음

		if (isMoldNFactoryEdit) {
		    if (isMoldMass || ismMdv || isMoldNotApply) {// 금형 제작구분이 양산이면 or 해당없음
			dataMap.put("moldNFactoryCanEdit", "0");// 금형투자비 신규 제작처 비활성
			dataMap.put("moldNFactoryColor", "");
		    } else {
			dataMap.put("moldNFactoryCanEdit", "1");
			dataMap.put("moldNFactoryColor", "#B4EEF2");
		    }
		}

		if (isMoldNICEdit) {
		    if (isMoldMass || ismMdv || isMoldNotApply) {// 금형 제작구분이 양산 or 해당없음
			dataMap.put("moldNICCanEdit", "0");// 금형투자비 신규 투자비 비활성
			dataMap.put("moldNICColor", "");
		    } else {
			dataMap.put("moldNICCanEdit", "1");
			dataMap.put("moldNICColor", "#B4EEF2");
		    }
		}

		if (isMoldNICMUnitEdit) {
		    if (isMoldMass || ismMdv || isMoldNotApply) {// 금형 제작구분이 양산이면 or 해당없음
			dataMap.put("moldNICMUnitCanEdit", "0");// 금형투자비 신규 투자비 화폐단위 비활성
			dataMap.put("moldNICMUnitColor", "");
		    } else {
			dataMap.put("moldNICMUnitCanEdit", "1");
			dataMap.put("moldNICMUnitColor", "#B4EEF2");
		    }
		}

		if (isMoldNPayEdit) {
		    if (isMoldMass || ismMdv || isMoldNotApply) {// 금형 제작구분이 양산이면 or 해당없음
			dataMap.put("moldNPayCanEdit", "0");// 금형투자비 신규 지급액 비활성
			dataMap.put("moldNPayColor", "");
		    } else {
			dataMap.put("moldNPayCanEdit", "1");
			dataMap.put("moldNPayColor", "#B4EEF2");
		    }
		}

		if (isMoldNPayMUnitEdit) {
		    if (isMoldMass || ismMdv || isMoldNotApply) {// 금형 제작구분이 양산이면 or 해당없음
			dataMap.put("moldNPayMUnitCanEdit", "0");// 금형투자비 신규 지급액화폐단위 비활성
			dataMap.put("moldNPayMUnitColor", "");
		    } else {
			dataMap.put("moldNPayMUnitCanEdit", "1");
			dataMap.put("moldNPayMUnitColor", "#B4EEF2");
		    }
		}

		if (isMoldMPFactoryEdit) {
		    if (isMoldNew || isPassTaskCode || ismMdv || isMoldNotApply) {// 금형 제작구분이 신규 또는 신규(공용)or task코드가
			                                                          // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("moldMPFactoryCanEdit", "0");// 금형투자비 양산 제작처 비활성
			dataMap.put("moldMPFactoryColor", "");
		    } else {
			dataMap.put("moldMPFactoryCanEdit", "1");
			dataMap.put("moldMPFactoryColor", "#B4EEF2");
		    }
		}

		if (isMoldMPICEdit) {
		    if (isMoldNew || isPassTaskCode || ismMdv || isMoldNotApply) {// 금형 제작구분이 신규 또는 신규(공용)or task코드가
			                                                          // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("moldMPICCanEdit", "0");// 금형투자비 양산 투자비 비활성
			dataMap.put("moldMPICColor", "");
		    } else {
			dataMap.put("moldMPICCanEdit", "1");
			dataMap.put("moldMPICColor", "#B4EEF2");
		    }
		}

		if (isMoldMPICMUnitEdit) {
		    if (isMoldNew || isPassTaskCode || ismMdv || isMoldNotApply) {// 금형 제작구분이 신규 또는 신규(공용)or task코드가
			                                                          // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("moldMPICMUnitCanEdit", "0");// 금형투자비 양산 투자비화폐단위 비활성
			dataMap.put("moldMPICMUnitColor", "");
		    } else {
			dataMap.put("moldMPICMUnitCanEdit", "1");
			dataMap.put("moldMPICMUnitColor", "#B4EEF2");
		    }
		}

		if (isMoldMPQTotalEdit) {
		    if (isMoldNew || isPassTaskCode || ismMdv || isMoldNotApply) {// 금형 제작구분이 신규 또는 신규(공용)or task코드가
			                                                          // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("moldMPQTotalCanEdit", "0");// 금형투자비 양산수량 Total 비활성
			dataMap.put("moldMPQTotalColor", "");
		    } else {
			dataMap.put("moldMPQTotalCanEdit", "1");
			dataMap.put("moldMPQTotalColor", "#B4EEF2");
		    }
		}

		if (isMoldMPQMaxEdit) {
		    if (isMoldNew || isPassTaskCode || ismMdv || isMoldNotApply) {// 금형 제작구분이 신규 또는 신규(공용)or task코드가
			                                                          // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("moldMPQMaxCanEdit", "0");// 금형투자비 양산수량 Max 비활성
			dataMap.put("moldMPQMaxColor", "");
		    } else {
			dataMap.put("moldMPQMaxCanEdit", "1");
			dataMap.put("moldMPQMaxColor", "#B4EEF2");
		    }
		}

		if (isFacNFactoryEdit) {
		    if (isFacMass || isfMdv || isFacNotApply) {// 설비 제작구분이 양산이면 or 해당없음
			dataMap.put("facNFactoryCanEdit", "0");// 설비투자비 신규 제작처 비활성
			dataMap.put("facNFactoryColor", "");
		    } else {
			dataMap.put("facNFactoryCanEdit", "1");
			dataMap.put("facNFactoryColor", "#B4EEF2");
		    }
		}

		if (isFacNICEdit) {
		    if (isFacMass || isfMdv || isFacNotApply) {// 설비 제작구분이 양산이면 or 해당없음
			dataMap.put("facNICCanEdit", "0");// 설비투자비 신규 투자비 비활성
			dataMap.put("facNICColor", "");
		    } else {
			dataMap.put("facNICCanEdit", "1");
			dataMap.put("facNICColor", "#B4EEF2");
		    }
		}

		if (isFacNICMUnitEdit) {
		    if (isFacMass || isfMdv || isFacNotApply) {// 설비 제작구분이 양산이면 or 해당없음
			dataMap.put("facNICMUnitCanEdit", "0");// 설비투자비 신규 투자비화폐단위 비활성
			dataMap.put("facNICMUnitColor", "");
		    } else {
			dataMap.put("facNICMUnitCanEdit", "1");
			dataMap.put("facNICMUnitColor", "#B4EEF2");
		    }
		}

		if (isFacNPayEdit) {
		    if (isFacMass || isfMdv || isFacNotApply) {// 설비 제작구분이 양산이면 or 해당없음
			dataMap.put("facNPayCanEdit", "0");// 설비투자비 신규 지급액 비활성
			dataMap.put("facNPayColor", "");
		    } else {
			dataMap.put("facNPayCanEdit", "1");
			dataMap.put("facNPayColor", "#B4EEF2");
		    }
		}

		if (isFacNPayMUnitEdit) {
		    if (isFacMass || isfMdv || isFacNotApply) {// 설비 제작구분이 양산이면 or 해당없음
			dataMap.put("facNPayMUnitCanEdit", "0");// 설비투자비 신규 지급액화폐단위 비활성
			dataMap.put("facNPayMUnitColor", "");
		    } else {
			dataMap.put("facNPayMUnitCanEdit", "1");
			dataMap.put("facNPayMUnitColor", "#B4EEF2");
		    }
		}

		if (isFacMPFactoryEdit) {
		    if (isFacNew || isPassTaskCode || isfMdv || isFacNotApply) {// 설비 제작구분이 신규 또는 신규(공용)or task코드가
			                                                        // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("facMPFactoryCanEdit", "0");// 설비투자비 양산 제작처 비활성
			dataMap.put("facMPFactoryColor", "");
		    } else {
			dataMap.put("facMPFactoryCanEdit", "1");
			dataMap.put("facMPFactoryColor", "#B4EEF2");
		    }
		}

		if (isFacMPICEdit) {
		    if (isFacNew || isPassTaskCode || isfMdv || isFacNotApply) {// 설비 제작구분이 신규 또는 신규(공용)or task코드가
			                                                        // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("facMPICCanEdit", "0");// 설비투자비 양산 투자비 비활성
			dataMap.put("facMPICColor", "");
		    } else {
			dataMap.put("facMPICCanEdit", "1");
			dataMap.put("facMPICColor", "#B4EEF2");
		    }
		}

		if (isFacMPICMUnitEdit) {
		    if (isFacNew || isPassTaskCode || isfMdv || isFacNotApply) {// 설비 제작구분이 신규 또는 신규(공용)or task코드가
			                                                        // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("facMPICMUnitCanEdit", "0");// 설비투자비 양산 투자비화폐단위 비활성
			dataMap.put("facMPICMUnitColor", "");
		    } else {
			dataMap.put("facMPICMUnitCanEdit", "1");
			dataMap.put("facMPICMUnitColor", "#B4EEF2");
		    }
		}

		if (isFacMPQTotalEdit) {
		    if (isFacNew || isPassTaskCode || isfMdv || isFacNotApply) {// 설비 제작구분이 신규 또는 신규(공용)or task코드가
			                                                        // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("facMPQTotalCanEdit", "0");// 설비투자비 양산수량 Total 비활성
			dataMap.put("facMPQTotalColor", "");
		    } else {
			dataMap.put("facMPQTotalCanEdit", "1");
			dataMap.put("facMPQTotalColor", "#B4EEF2");
		    }
		}

		if (isFacMPQMaxEdit) {
		    if (isFacNew || isPassTaskCode || isfMdv || isFacNotApply) {// 설비 제작구분이 신규 또는 신규(공용) or task코드가
			                                                        // 예비공법검토서,사출금형원가정보,프레스금형원가정보 or 해당없음
			dataMap.put("facMPQMaxCanEdit", "0");// 설비투자비 양산수량 Max 비활성
			dataMap.put("facMPQMaxColor", "");
		    } else {
			dataMap.put("facMPQMaxCanEdit", "1");
			dataMap.put("facMPQMaxColor", "#B4EEF2");
		    }
		}

		/*******************************************************/
		/*******************************************************/
		/*******************************************************/
		/*****  					  ******/
		/*****  					  ******/
		/***** 금형투자비, 설비투자비 관련 항목 하드코딩 End ******/
		/*****  					  ******/
		/*****  					  ******/
		/*******************************************************/
		/*******************************************************/
		/*******************************************************/

		// 재료비 산출옵션명
		String calcOptionMaterial = StringUtil.checkNull(part.getCalcOptionMaterial());
		String[] calcOptionMaterials = calcOptionMaterial.split(";");

		for (String coMaterial : calcOptionMaterials) {

		    if (StringUtil.checkString(coMaterial)) {
			NumberCode code = CodeHelper.manager.getNumberCode("CALCULATIONSTD", coMaterial);
			calcOptionMaterial = calcOptionMaterial.replaceAll(coMaterial, code.getName());
		    }
		}

		dataMap.put("coMaterialNames", calcOptionMaterial);

		String calcOptionLabor = StringUtil.checkNull(part.getCalcOptionLabor());

		// 금형준비공수 활성화
		if (calcOptionLabor.indexOf("CALCSTD016") >= 0) {
		    dataMap.put("productHaveMonthCanEdit", "1");
		    dataMap.put("moldReadyTimeCanEdit", "1");
		}

		// 추가공수1 활성화
		if (calcOptionLabor.indexOf("CALCSTD017") >= 0) {
		    dataMap.put("addManHourCt_1CanEdit", "1");
		    dataMap.put("addManHourEff_1CanEdit", "1");
		    dataMap.put("addManHourLb_1CanEdit", "1");
		    dataMap.put("addManHourClb_1CanEdit", "1");
		    dataMap.put("addManHourYear_1CanEdit", "1");
		}

		// 추가공수2 활성화
		if (calcOptionLabor.indexOf("CALCSTD018") >= 0) {
		    dataMap.put("addManHourCt_2CanEdit", "1");
		    dataMap.put("addManHourEff_2CanEdit", "1");
		    dataMap.put("addManHourLb_2CanEdit", "1");
		    dataMap.put("addManHourClb_2CanEdit", "1");
		    dataMap.put("addManHourYear_2CanEdit", "1");
		}

		if (master != null) {
		    dataMap.put("usCanEdit", "0");

		    if (!"NONPOPUP".equals(EDITMODE)) {
			// view 모드에서도 팝업이 가능하도록 주석처리한다. 주석처리한 코드는 위쪽으로 이동함
			// dataMap.put("estimatedButton", "/plm/portal/images/icon_5.png");
			// dataMap.put("estimatedOnClickSideButton", "javasciprt:openBomAttrPopup('ESTIMATED', Row);");

			if ("COST015".equals(taskCode) || "COST016".equals(taskCode)) {
			    dataMap.put("partNameButton", "/plm/portal/images/icon_5.png");
			    dataMap.put("partNameOnClickSideButton", "javasciprt:openBomAttrPopup('CALCULATEPART', Row);");
			}
		    }
		}

		if (!"NONPOPUP".equals(EDITMODE)) {
		    dataMap.put("partTypeNameIcon", "/plm/portal/images/icon_5.png");
		    dataMap.put("partTypeNameIconAlign", "Right");
		    dataMap.put("partTypeNameOnClickSideIcon",
			    "javascript:partTypeRow=Row;SearchUtil.selectOneCostPartTypeWithCallBack('setPartType','onlyLeaf=Y&openAll=N');");
		    dataMap.put("partTypeNameButton", "/plm/portal/images/icon_delete.gif");
		    dataMap.put("partTypeNameOnClickSideButton", "javasciprt:deletePartType(Row);");

		    dataMap.put("sujiPartNoButton", "/plm/portal/images/icon_5.png");
		    dataMap.put("sujiPartNoOnClickSideButton", "javasciprt:openBomAttrPopup('RAWMATERIAL', Row);");
		    // view 모드에서도 팝업이 가능하도록 주석처리한다. 주석처리한 코드는 위쪽으로 이동함
		    // dataMap.put("itemNameButton", "/plm/portal/images/icon_5.png");
		    // dataMap.put("itemNameOnClickSideButton", "javasciprt:openBomAttrPopup('ETCIC', Row);");
		    //
		    // dataMap.put("quantityButton", "/plm/portal/images/icon_5.png");
		    // dataMap.put("quantityOnClickSideButton", "javasciprt:openBomAttrPopup('QUANTITY', Row);");

		    for (BomEditorACL aclData : popupACL) {
			String columnKey = aclData.getColumnKey();

			if (!aclData.isEditor()) {
			    dataMap.put(columnKey + "Icon", null);
			    dataMap.put(columnKey + "IconAlign", null);
			    dataMap.put(columnKey + "OnClickSideIcon", null);
			    dataMap.put(columnKey + "Button", null);
			    dataMap.put(columnKey + "OnClickSideButton", null);
			}
		    }
		}
	    }

	} else if (data instanceof CostFormula) {

	    CostFormula formula = (CostFormula) data;
	    System.out.println("formual 진입.. " + CommonUtil.getOIDString(formula));
	    if (formula.getSortLocation() == 0) {

		dataMap.put("CanDelete", 0);
		dataMap.put("CanDrag", 0);
		dataMap.put("CanCopy", 0);
		dataMap.put("CanCopyPaste", 0);
		dataMap.put("CanEdit", 0);
		dataMap.put("CanExpand", 0);
		dataMap.put("mappingName", "총원가");

	    } else {
		dataMap.put("mappingNameIcon", "/plm/portal/images/icon_5.png");
		dataMap.put("mappingNameIconAlign", "Right");
		dataMap.put("mappingNameOnClickSideIcon", "javascript:openGridPopup(Row, 'mapping');");
		dataMap.put("mappingNameButton", "/plm/portal/images/icon_delete.gif");
		dataMap.put("mappingNameOnClickSideButton", "javasciprt:deletePopupValue(Row, 'mapping');");
	    }
	    dataMap.put("MaxHeight", 20);
	    dataMap.put("MaxHeightAll", 1);
	    dataMap.put("formulaIcon", "/plm/portal/images/icon_5.png");
	    dataMap.put("formulaIconAlign", "Right");
	    dataMap.put("formulaOnClickSideIcon", "javascript:openGridPopup(Row, 'formula');");
	    dataMap.put("formulaButton", "/plm/portal/images/icon_delete.gif");
	    dataMap.put("formulaOnClickSideButton", "javasciprt:deletePopupValue(Row, 'formula');");

	    String partTypeName = "";
	    String mftFactory1Name = "";
	    String mftFactory2Name = "";

	    boolean isBrTag = false;
	    List<Map<String, Object>> pTypeMftFactory = CostUtil.manager.getPartTypeMftFatcory(formula);

	    for (Map<String, Object> map : pTypeMftFactory) {

		if (isBrTag) {
		    partTypeName += "<br>";
		    mftFactory1Name += "<br>";
		    mftFactory2Name += "<br>";
		}

		String partTypeOid = (String) map.get("partType");
		List<String> mftFactory1 = (List<String>) map.get("mftFactory1");
		List<String> mftFactory2 = (List<String>) map.get("mftFactory2");

		CostPartType type = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);
		if (type != null)
		    partTypeName += type.getName();

		boolean isComma = false;

		for (String mft : mftFactory1) {

		    if (isComma) {
			mftFactory1Name += ",";
		    } else {
			isComma = true;
		    }

		    if (StringUtil.checkString(mft)) {
			NumberCode code = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mft);
			mftFactory1Name += code.getName();
		    }
		}

		isComma = false;

		for (String mft : mftFactory2) {

		    if (isComma) {
			mftFactory2Name += ",";
		    } else {
			isComma = true;
		    }

		    if (StringUtil.checkString(mft)) {
			NumberCode code = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mft);
			mftFactory2Name += code.getName();
		    }
		}

		if (isBrTag) {
		    partTypeName += "&nbsp;";
		    mftFactory1Name += "&nbsp;";
		    mftFactory2Name += "&nbsp;";
		}

		isBrTag = true;
	    }

	    dataMap.put("partTypeName", partTypeName);
	    dataMap.put("mftFactory1Name", mftFactory1Name);
	    dataMap.put("mftFactory2Name", mftFactory2Name);

	    if (formula.getMappingCode() != null) {
		CostAttribute attr = (CostAttribute) CommonUtil.getObject(formula.getMappingCode());
		if (attr != null)
		    dataMap.put("mappingName", attr.getName());
	    }
	    System.out.println("formual 종료.. " + CommonUtil.getOIDString(formula));
	}

	return dataMap;
    }
}
