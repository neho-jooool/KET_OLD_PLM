package ext.ket.cost.service;

import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.pom.Transaction;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItem;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostCurrencyInfo;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostInterfaceHistory;
import ext.ket.cost.entity.CostInvestInfo;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.CostQuantity;
import ext.ket.cost.entity.CostReduceLink;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.CostTrackingDTO;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.PjtMasterPartListLink;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.entity.costErpInterfaceDTO;
import ext.ket.cost.util.CostCalculateUtil;
import ext.ket.cost.util.CostPartImgUtil;
import ext.ket.cost.util.CostTreeUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 17. 오전 10:52:12
 * @Pakage ext.ket.project.cost.service
 * @class StandardCostBomService
 *********************************************************/
public class StandardCostService extends StandardManager implements CostService, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(StandardCostService.class);

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
    public static StandardCostService newStandardCostService() throws WTException {
	StandardCostService instance = new StandardCostService();
	instance.initialize();
	return instance;
    }

    /**
     * <pre>
     * @description  
     * @author Administrator
     * @date 2019. 5. 7. 오후 2:41:36
     * @method bomCopyAdd
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> bomCopyAdd(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String rpNo = (String) reqMap.get("rpNo");
	String taskOid = (String) reqMap.get("taskOid");
	rpNo = rpNo.toUpperCase().trim();
	String[] rpNoArr = rpNo.split(",");

	List<CostPart> list = new ArrayList<CostPart>();

	for (String projectNo : rpNoArr) {

	    if (StringUtil.checkString(projectNo)) {

		E3PSProject project = ProjectHelper.getProject(projectNo);

		if (project == null) {
		    throw new Exception("입력된 프로젝트 (" + projectNo + ")는 존재하지 않습니다.");
		}

		E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

		list.addAll(CostHelper.service.getCostProductList(pjtMaster));
	    }
	}

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	CostHelper.service.copyNewProduct(task, list);

	resMap.put("message", "BOM COPY가 완료되었습니다.");

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 7. 오후 4:31:04
     * @method copyNewProduct
     * @param task
     * @param copyProduct
     * @return E3PSTask
     * @throws Exception
     * </pre>
     */
    @Override
    public E3PSTask copyNewProduct(E3PSTask task, List<CostPart> copyProduct) throws Exception {

	Transaction trx = new Transaction();
	try {

	    trx.start();

	    E3PSProject project = (E3PSProject) task.getProject();
	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();
	    PartList partList = CostUtil.manager.getPartList(pjtMaster);

	    String pjtNo = project.getPjtNo();
	    String drStep = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), CommonUtil.getOIDString(task));
	    String version = StringUtil.checkNullZero(task.getCostVersion());
	    int newPartNo = CostTreeUtil.manager.getGenratePartNo(pjtNo);

	    NumberFormat nf = NumberFormat.getInstance();
	    nf.setMinimumIntegerDigits(3);

	    List<Map<String, String>> targetList = new ArrayList<Map<String, String>>();

	    for (CostPart oldProduct : copyProduct) {

		DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(oldProduct);

		ProductMaster oldMaster = oldProduct.getMaster();
		ProductMaster master = (ProductMaster) HistoryHelper.duplicate(oldMaster);

		CostPart newProduct = (CostPart) HistoryHelper.duplicate(oldProduct);

		master.setMasterOid(CommonUtil.getOIDString(oldMaster));
		master.setPartList(partList);
		master = (ProductMaster) PersistenceHelper.manager.save(master);
		newProduct.setMaster(master);

		newProduct.setProject(pjtMaster);
		newProduct.setVersion(Integer.parseInt(version));
		newProduct.setSubVersion(0);
		newProduct.setLastest(false);
		newProduct.setCaseOrder(null);
		newProduct.setDrStep(drStep);
		newProduct.setSortLocation(newPartNo);
		newProduct.setPartNo(pjtNo + "-" + nf.format(newPartNo++));
		newProduct = (CostPart) PersistenceHelper.manager.save(newProduct);

		Map<String, String> targetMap = new HashMap<String, String>();
		targetMap.put(CommonUtil.getOIDString(oldProduct), CommonUtil.getOIDString(newProduct));
		targetList.add(targetMap);

		for (int i = 0; i < root.getChildCount(); i++) {
		    DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
		    CostPart oldPart = (CostPart) child.getUserObject();
		    CostPart newPart = (CostPart) HistoryHelper.duplicate(oldPart);

		    newPart.setParent(newProduct);
		    newPart.setVersion(newProduct.getVersion());
		    newPart.setSubVersion(0);
		    newPart.setLastest(false);
		    newPart.setCaseOrder(null);
		    newPart.setProject(pjtMaster);
		    newPart.setDrStep(drStep);
		    newPart.setSortLocation(newPartNo);
		    newPart.setPartNo(pjtNo + "-" + nf.format(newPartNo++));
		    newPart = (CostPart) PersistenceHelper.manager.save(newPart);

		    targetMap = new HashMap<String, String>();
		    targetMap.put(CommonUtil.getOIDString(oldPart), CommonUtil.getOIDString(newPart));
		    targetList.add(targetMap);

		    newPartNo = copyChildNewPart(child, newPart, targetList, pjtNo, newPartNo, nf);
		}

		CostCodeHelper.service.copyCostCurrencyInfo(oldProduct, newProduct);
	    }

	    CostCodeHelper.service.copyRelatedObject(targetList);// CostPart 연관 Object copy

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

	return task;
    }

    /**
     * <pre>
     * @description  
     * @author Administrator
     * @date 2019. 5. 7. 오후 3:47:25
     * @method copyChildNewPart
     * @param node
     * @param part
     * @param targetList
     * @param pjtNo
     * @param newPartNo
     * @throws Exception
     * </pre>
     */
    private int copyChildNewPart(DefaultMutableTreeNode node, CostPart part, List<Map<String, String>> targetList, String pjtNo,
	    int newPartNo, NumberFormat nf) throws Exception {

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

	    CostPart oldPart = (CostPart) child.getUserObject();
	    CostPart newPart = (CostPart) HistoryHelper.duplicate(oldPart);

	    newPart.setParent(part);
	    newPart.setVersion(part.getVersion());
	    newPart.setSubVersion(0);
	    newPart.setLastest(false);
	    newPart.setCaseOrder(null);
	    newPart.setProject(part.getProject());
	    newPart.setDrStep(part.getDrStep());
	    newPart.setSortLocation(newPartNo);
	    newPart.setPartNo(pjtNo + "-" + nf.format(newPartNo++));
	    newPart = (CostPart) PersistenceHelper.manager.save(newPart);

	    Map<String, String> targetMap = new HashMap<String, String>();
	    targetMap.put(CommonUtil.getOIDString(oldPart), CommonUtil.getOIDString(newPart));
	    targetList.add(targetMap);

	    newPartNo = copyChildNewPart(child, newPart, targetList, pjtNo, newPartNo, nf);
	}

	return newPartNo;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 4. 30. 오후 5:07:12
     * @method getProjectPartMapList
     * @param partNo
     * @param project
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    @Override
    public List<Map<String, Object>> getProjectPartMapList(String partNo, E3PSProject project) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	List<CostPart> partList = getProjectPartList(partNo, (E3PSProjectMaster) project.getMaster());

	for (CostPart part : partList) {

	    E3PSTask task = CostHelper.service.getPartE3PSTask(part);

	    Map<String, Object> map = CostUtil.manager.converObjectToMap(part);
	    map.put("oid", CommonUtil.getOIDString(part));

	    if (task != null) {
		map.put("taskOid", CommonUtil.getOIDString(task));
	    }
	    list.add(map);

	}

	return list;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2019. 5. 2. 오후 2:34:32
     * @method getPartE3PSTask
     * @param part
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public E3PSTask getPartE3PSTask(CostPart part) throws Exception {

	E3PSProjectMaster master = part.getProject();
	E3PSProject project = E3PSProjectHelper.service.getProjectByProjectNo(master.getPjtNo());
	long pjtOid = CommonUtil.getOIDLongValue(project);

	QuerySpec qs = new QuerySpec();

	int idx = qs.addClassList(E3PSTask.class, true);
	qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, pjtOid), new int[] { idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendWhere(
	        new SearchCondition(E3PSTask.class, E3PSTask.COST_VERSION, SearchCondition.EQUAL, String.valueOf(part.getVersion())),
	        new int[] { idx });

	// 원가 산출 태스크만 가져오기
	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_TYPE_CATEGORY, SearchCondition.EQUAL, "COST015"),
	        new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    return (E3PSTask) o[0];
	}

	return null;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2019. 4. 30. 오후 5:04:07
     * @method getProjectPartList
     * @param partNo
     * @param master
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<CostPart> getProjectPartList(String partNo, E3PSProjectMaster master) throws Exception {

	List<CostPart> list = new ArrayList<CostPart>();

	QuerySpec qs = new QuerySpec();

	int idx = qs.addClassList(CostPart.class, true);
	int idx2 = qs.appendClassList(E3PSProjectMaster.class, false);
	qs.setAdvancedQueryEnabled(true);

	ClassAttribute ca0 = new ClassAttribute(CostPart.class, CostPart.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	ClassAttribute ca1 = new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME);
	SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	sc0.setOuterJoin(0);
	qs.appendWhere(sc0, new int[] { idx, idx2 });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.PART_NO, SearchCondition.EQUAL, partNo), new int[] { idx });

	if (qs.getConditionCount() > 0) {
	    qs.appendAnd();
	}

	qs.appendOpenParen();
	qs.appendWhere(new SearchCondition(E3PSProjectMaster.class, E3PSProjectMaster.PJT_TYPE_NAME, SearchCondition.EQUAL, "검토"),
	        new int[] { idx2 });
	if (qs.getConditionCount() > 0) {
	    qs.appendOr();
	}
	qs.appendWhere(
	        new SearchCondition(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(master)), new int[] { idx2 });
	qs.appendCloseParen();

	SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.VERSION, false);
	SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SUB_VERSION, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPart obj = (CostPart) o[0];
	    list.add(obj);
	}

	return list;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:49:19
     * @method loadCostFormula
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public void loadCostFormula() throws Exception {

	SessionContext sessionContext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();

	    QueryResult qr = null;
	    QuerySpec qs = new QuerySpec();
	    qs.appendClassList(CostFormula.class, true);
	    qr = PersistenceHelper.manager.find(qs);

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostFormula obj = (CostFormula) o[0];
		DefaultMutableTreeNode node = CostTreeUtil.manager.getCostTree(obj);
		CostCacheManager.updateCostFByKey(CommonUtil.getOIDString(obj), node);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    SessionContext.setContext(sessionContext);
	}
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2019. 4. 30. 오후 4:49:34
     * @method loadCostPartTypeInfo
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public void loadCostPartTypeInfo() throws Exception {

	SessionContext sessionContext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    QueryResult qr = null;
	    QuerySpec qs = new QuerySpec();
	    qs.appendClassList(CostPartType.class, true);
	    qr = PersistenceHelper.manager.find(qs);

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostPartType obj = (CostPartType) o[0];
		String partTypeOid = CommonUtil.getOIDLongValue2Str(obj);
		List<Map<String, Object>> list = CostCodeHelper.service.getCostFactoryTreeInfoByPartType(partTypeOid);
		CostCacheManager.updateCostPTByKey(partTypeOid, list);
	    }

	    List<Map<String, Object>> list = CostCodeHelper.service.getCostPartTypeLinkList();
	    CostCacheManager.updateCostPTByKey("PARTTYPELINKLIST", list);

	} catch (Exception e) {
	    throw e;
	} finally {
	    SessionContext.setContext(sessionContext);
	}
    }

    /**
     * <pre>
     * @description 제품 원가 수식버전 변경 처리
     * @author dhkim
     * @date 2018. 9. 14. 오전 10:19:29
     * @method changeFormulaVersion
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> changeFormulaVersion(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();

	try {

	    String oid = (String) reqMap.get("oid");
	    int formulaVersion = Integer.parseInt((String) reqMap.get("formulaVersion"));

	    CostPart product = (CostPart) CommonUtil.getObject(oid);
	    List<CostPart> partList = CostTreeUtil.manager.getCostPartList(product);

	    trx.start();

	    for (CostPart part : partList) {
		part.setFormulaVersion(formulaVersion);
		PersistenceHelper.manager.save(part);
	    }

	    trx.commit();

	    trx.start();

	    List<CostPart> spList = CostUtil.manager.syncCostPart(product);

	    for (CostPart sPart : spList) {
		PersistenceHelper.manager.save(sPart);
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

	return resMap;
    }

    /**
     * <pre>
     * @description 원가 수식 개정 취소 처리
     * @author dhkim
     * @date 2018. 10. 17. 오후 1:37:12
     * @method reviseCancel
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> reviseCancel(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    List<CostFormula> cfList = CostUtil.manager.getCostFormulaList(CostUtil.manager.getLastestFormulaVersion());

	    for (CostFormula cf : cfList) {

		PersistenceHelper.manager.delete(cf);
	    }

	    trx.commit();
	    trx = null;

	    resMap.put("version", CostUtil.manager.getLastestFormulaVersion());

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 원가 수식 개정 처리
     * @author dhkim
     * @date 2018. 8. 22. 오전 11:38:22
     * @method reviseCostFormula
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> reviseCostFormula(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {

	    reqMap.put("version", String.valueOf(CostUtil.manager.getLastestFormulaVersion()));

	    trx.start();

	    List<Persistable> rootList = CostHelper.service.getCostRootList(reqMap);

	    if (rootList.size() != 0) {
		String rootKey = CommonUtil.getOIDString(rootList.get(0));
		DefaultMutableTreeNode root = CostCacheManager.getCostFItem(rootKey);

		if (root == null) {
		    root = CostTreeUtil.manager.getCostTree(rootList.get(0));
		    CostCacheManager.updateCostFByKey(rootKey, root);
		}

		CostFormula rootOldF = (CostFormula) root.getUserObject();
		CostFormula rootNewF = (CostFormula) HistoryHelper.duplicate(rootOldF);

		int formulaVersion = rootOldF.getFormulaVersion();
		formulaVersion++;
		rootNewF.setFormulaVersion(formulaVersion);
		rootNewF.setStatus(CostUtil.FORMULASTATE_INWORK);

		rootNewF = (CostFormula) PersistenceHelper.manager.save(rootNewF);

		Map<String, String> targetMap = new HashMap<String, String>();
		targetMap.put(CommonUtil.getOIDString(rootOldF), CommonUtil.getOIDString(rootNewF));

		copyChildNewVersionFormula(root, rootNewF, targetMap);

		CostUtil.manager.replaceFormula(targetMap, formulaVersion);

		resMap.put("message", "개정되었습니다.");
	    } else {
		resMap.put("message", "개정할 수식이 없습니다.");
	    }

	    trx.commit();
	    trx = null;

	    loadCostFormula();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    private void copyChildNewVersionFormula(DefaultMutableTreeNode node, CostFormula formula, Map<String, String> targetMap)
	    throws Exception {

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

	    CostFormula childOldF = (CostFormula) child.getUserObject();
	    CostFormula childNewF = (CostFormula) HistoryHelper.duplicate(childOldF);

	    childNewF.setParent(formula);
	    childNewF.setFormulaVersion(formula.getFormulaVersion());
	    childNewF.setStatus(formula.getStatus());
	    childNewF = (CostFormula) PersistenceHelper.manager.save(childNewF);

	    targetMap.put(CommonUtil.getOIDString(childOldF), CommonUtil.getOIDString(childNewF));

	    copyChildNewVersionFormula(child, childNewF, targetMap);
	}
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 4. 2. 오후 7:50:21
     * @method findPaging
     * @param paramObject
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public PageControl findPaging(CostTrackingDTO paramObject) throws Exception {
	QuerySpec query = getTrackingQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    @Override
    public PageControl findInterfacePaging(costErpInterfaceDTO paramObject) throws Exception {
	QuerySpec query = getInterfaceHistoryQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    private QuerySpec getInterfaceHistoryQuery(costErpInterfaceDTO paramObject) throws Exception {

	QuerySpec qs = new QuerySpec();

	int idx = qs.appendClassList(CostInterfaceHistory.class, true);

	String pjtNo = paramObject.getPjtNo();
	String partNo = paramObject.getPartNo();
	String drSteps[] = paramObject.getDrSteps();
	String transferFlags[] = paramObject.getTransferFlags();
	String lastest = paramObject.getLastest();
	String gap = paramObject.getGap();

	if (StringUtils.isNotEmpty(pjtNo)) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(
		    new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.PJT_NO, SearchCondition.EQUAL, pjtNo.toUpperCase()),
		    new int[] { idx });
	}

	if (StringUtils.isNotEmpty(partNo)) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(
		    new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.REAL_PART_NO, SearchCondition.EQUAL, partNo
		            .toUpperCase()), new int[] { idx });
	}

	if (drSteps != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.DR_STEP),
		    SearchCondition.IN, new ArrayExpression(drSteps)), new int[] { 0 });
	}

	if (transferFlags != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(new ClassAttribute(CostInterfaceHistory.class, CostInterfaceHistory.TRANSFER_FLAG),
		    SearchCondition.IN, new ArrayExpression(transferFlags)), new int[] { 0 });
	}

	if (StringUtils.isNotEmpty(lastest)) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(
		    new SearchCondition(CostInterfaceHistory.class, CostInterfaceHistory.LASTEST, SearchCondition.IS_TRUE, Boolean
		            .parseBoolean(lastest)), new int[] { idx });
	}

	if (StringUtils.isNotEmpty(gap)) {

	    KeywordExpression kexp = new KeywordExpression("TO_NUMBER(A0.productCostTotal)");
	    KeywordExpression kexp2 = new KeywordExpression("TO_NUMBER(A0.orgProductCostTotal)");

	    SearchCondition sc = new SearchCondition(kexp, SearchCondition.NOT_EQUAL, kexp2);
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(sc, new int[] { idx });
	}

	SearchUtil.setOrderBy(qs, CostInterfaceHistory.class, idx, CostInterfaceHistory.REAL_PART_NO, false);
	SearchUtil.setOrderBy(qs, CostInterfaceHistory.class, idx, CostInterfaceHistory.DR_STEP, false);
	SearchUtil.setOrderBy(qs, CostInterfaceHistory.class, idx, CostInterfaceHistory.VERSION, false);

	return qs;

    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 4. 2. 오후 7:50:24
     * @method getTrackingQuery
     * @param paramObject
     * @return QuerySpec
     * @throws Exception
     * </pre>
     */
    private QuerySpec getTrackingQuery(CostTrackingDTO paramObject) throws Exception {

	QuerySpec qs = new QuerySpec();

	int ii = qs.addClassList(ProductMaster.class, false);
	int jj = qs.addClassList(PartList.class, false);
	int idx = qs.addClassList(CostPart.class, true);
	int idx2 = qs.addClassList(E3PSProjectMaster.class, false);
	int idx3 = qs.addClassList(ProductProject.class, true);
	qs.setAdvancedQueryEnabled(true);

	ClassAttribute ca0 = new ClassAttribute(ProductMaster.class, ProductMaster.PART_LIST_REFERENCE + "."
	        + WTAttributeNameIfc.REF_OBJECT_ID);
	ClassAttribute ca1 = new ClassAttribute(PartList.class, WTAttributeNameIfc.ID_NAME);
	SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { ii, jj }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { ii, jj });

	ca0 = new ClassAttribute(ProductMaster.class, WTAttributeNameIfc.ID_NAME);
	ca1 = new ClassAttribute(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { ii, idx }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { ii, idx });

	if (paramObject.getVersion().equals("671979013")) {
	    ca0 = new ClassAttribute(PartList.class, PartList.LASTEST_VERSION);
	    ca1 = new ClassAttribute(CostPart.class, CostPart.VERSION);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { jj, idx });
	}

	ca0 = new ClassAttribute(CostPart.class, CostPart.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	ca1 = new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME);
	sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { idx, idx2 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.SUB_VERSION, SearchCondition.EQUAL, 0), new int[] { idx });

	ca0 = new ClassAttribute(E3PSProjectMaster.class, WTAttributeNameIfc.ID_NAME);
	ca1 = new ClassAttribute(ProductProject.class, ProductProject.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx2, idx3 }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { idx2, idx3 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx3 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o"),
	        new int[] { idx3 });

	// 프로젝트번호
	if (StringUtil.checkString(paramObject.getPjtNo())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PJT_NO, SearchCondition.EQUAL, paramObject.getPjtNo()),
		    new int[] { idx3 });
	}

	// 프로젝트명
	if (StringUtil.checkString(paramObject.getPjtName())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(ProductProject.class, ProductProject.PJT_NAME, SearchCondition.LIKE, "%" + paramObject.getPjtName()
		            + "%"), new int[] { idx3 });
	}

	// 부품번호
	if (StringUtil.checkString(paramObject.getPartNo())) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.REAL_PART_NO, SearchCondition.EQUAL, paramObject.getPartNo()),
		    new int[] { idx });
	}

	// 차종
	if (StringUtil.checkString(paramObject.getCarTypeCode())) {

	    int idx4 = qs.addClassList(OEMProjectType.class, false);

	    ca0 = new ClassAttribute(ProductProject.class, ProductProject.OEM_TYPE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	    ca1 = new ClassAttribute(OEMProjectType.class, WTAttributeNameIfc.ID_NAME);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx3, idx4 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx3, idx4 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(OEMProjectType.class, OEMProjectType.CODE, SearchCondition.EQUAL, paramObject.getCarTypeCode()),
		    new int[] { idx4 });
	}

	// 개발담당부서
	if (StringUtil.checkString(paramObject.getDevTeamOid())) {

	    int idx5 = qs.addClassList(KETDevelopmentRequest.class, false);

	    ca0 = new ClassAttribute(ProductProject.class, ProductProject.DEV_REQUEST_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	    ca1 = new ClassAttribute(KETDevelopmentRequest.class, WTAttributeNameIfc.ID_NAME);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx3, idx5 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx3, idx5 });

	    QuerySpec subQs = new QuerySpec();
	    subQs.getFromClause().setAliasPrefix("REVIEWPJTOID");

	    subQs.addClassList(ReviewProject.class, false);

	    ca0 = new ClassAttribute(ReviewProject.class, WTAttributeNameIfc.OID_CLASSNAME);
	    ColumnExpression colexp = ConstantExpression.newExpression(":");
	    ca1 = new ClassAttribute(ReviewProject.class, WTAttributeNameIfc.ID_NAME);

	    SQLFunction sf = SQLFunction.newSQLFunction(SQLFunction.CONCAT, ca0, colexp);
	    SQLFunction sf2 = SQLFunction.newSQLFunction(SQLFunction.CONCAT, sf, ca1);
	    subQs.appendSelect(sf2, true);

	    subQs.appendWhere(new SearchCondition(ReviewProject.class, ReviewProject.DEV_DEPT_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(paramObject.getDevTeamOid())),
		    new int[] { 0 });

	    ca0 = new ClassAttribute(KETDevelopmentRequest.class, KETDevelopmentRequest.PROJECT_OID);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, new SubSelectExpression(subQs));
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx5 });
	}

	// 제품개발 ROLE
	if (StringUtil.checkString(paramObject.getDevRoleOid())) {
	    int idx6 = qs.addClassList(ProjectMemberLink.class, false);

	    ca0 = new ClassAttribute(ProductProject.class, WTAttributeNameIfc.ID_NAME);
	    ca1 = new ClassAttribute(ProjectMemberLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx3, idx6 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx3, idx6 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();

	    qs.appendWhere(new SearchCondition(new ClassAttribute(ProjectMemberLink.class, ProjectMemberLink.PJT_ROLE), SearchCondition.IN,
		    new ArrayExpression(new String[] { "Team_PRODUCT01", "Team_REVIEW01" })), new int[] { idx6 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(ProjectMemberLink.class, WTAttributeNameIfc.ROLEB_OBJECT_ID, SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getDevRoleOid())), new int[] { idx6 });
	}

	// 접점고객
	if (StringUtil.checkString(paramObject.getCustomerCode())) {
	    int idx7 = qs.addClassList(ProjectSubcontractorLink.class, false);

	    ca0 = new ClassAttribute(ProductProject.class, WTAttributeNameIfc.ID_NAME);
	    ca1 = new ClassAttribute(ProjectSubcontractorLink.class, WTAttributeNameIfc.ROLEB_OBJECT_ID);
	    sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx3, idx7 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx3, idx7 });

	    String[] customerCode = paramObject.getCustomerCode().split(",");
	    List<Long> customerOids = new ArrayList<Long>();

	    for (String code : customerCode) {
		customerOids.add(CommonUtil.getOIDLongValue(code));
	    }

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(new ClassAttribute(ProjectSubcontractorLink.class, WTAttributeNameIfc.ROLEA_OBJECT_ID),
		    SearchCondition.IN, new ArrayExpression(customerOids.toArray())), new int[] { idx7 });
	}

	if (paramObject.getVersion().equals("671979014")) {
	    String subQs = "(SELECT NVL(REALPARTNO,A2.PARTNO)||max(A2.VERSION) FROM ProductMaster A0,PartList A1,CostPart A2,E3PSProjectMaster A3,ProductProject A4";
	    subQs += " WHERE (A0.idA3A4 = A1.idA2A2) AND (A0.idA2A2 = A2.idA3A4)  AND (A2.idA3B4 = A3.idA2A2) ";

	    if (StringUtil.checkString(paramObject.getPartNo())) {
		subQs += " AND (A2.REALPARTNO = '" + paramObject.getPartNo() + "') ";
	    }

	    if (StringUtil.checkString(paramObject.getPjtNo())) {
		subQs += " AND (A3.PJTNO = '" + paramObject.getPjtNo() + "') ";
	    }
	    subQs += "   AND (A3.idA2A2 = A4.idA3B8) AND (A4.lastest = 1) AND (A4.checkOutState <> 'c/o') AND (A2.subVersion = 0) GROUP BY A3.PJTNO,NVL(REALPARTNO,A2.PARTNO))";

	    KeywordExpression kexp = new KeywordExpression("NVL(REALPARTNO,A2.PARTNO)||A2.VERSION");
	    KeywordExpression kexp2 = new KeywordExpression(subQs);

	    SearchCondition sc = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { idx });
	}

	// 프로젝트번호
	if (StringUtil.checkString(paramObject.getPjtNos())) {
	    String pjtNos[] = paramObject.getPjtNos().split(",");
	    String partNos[] = paramObject.getCostPartNos().split(",");

	    /*
	     * if (qs.getConditionCount() > 0) qs.appendAnd(); qs.appendWhere(new SearchCondition( new ClassAttribute(ProductProject.class,
	     * ProductProject.PJT_NO), SearchCondition.IN, new ArrayExpression(pjtNos)),new int[] { idx3 });
	     */

	    if (pjtNos.length > 1) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendOpenParen();
		for (int i = 0; i < pjtNos.length; i++) {
		    qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PJT_NO, "=", pjtNos[i]), new int[] { idx3 });

		    qs.appendAnd();

		    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.REAL_PART_NO, "=", partNos[i]), new int[] { idx });

		    if (i != pjtNos.length - 1)
			qs.appendOr();
		}
		qs.appendCloseParen();
	    } else {
		SearchUtil.appendEQUAL(qs, ProductProject.class, ProductProject.PJT_NO, pjtNos[0], idx3);
		SearchUtil.appendEQUAL(qs, CostPart.class, CostPart.REAL_PART_NO, partNos[0], idx);
	    }

	}

	SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.REAL_PART_NO, false);

	/*
	 * // 부품번호 if (StringUtil.checkString(paramObject.getCostPartNos())) { String partNos[] = paramObject.getCostPartNos().split(",");
	 * if (qs.getConditionCount() > 0) qs.appendAnd(); qs.appendWhere(new SearchCondition( new ClassAttribute(CostPart.class,
	 * CostPart.REAL_PART_NO), SearchCondition.IN, new ArrayExpression(partNos)),new int[] { idx }); }
	 */

	System.out.println("TRACKING QUERY ############## " + qs);

	return qs;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 3. 19. 오전 9:44:15
     * @method createCostReport
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public CostReport saveCostReport(Map<String, Object> reqMap) throws Exception {

	Transaction trx = new Transaction();

	CostReport cr = null;

	try {

	    trx.start();

	    String reportOid = (String) reqMap.get("reportOid");
	    String taskOid = (String) reqMap.get("taskOid");
	    E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	    E3PSProject project = (E3PSProject) task.getProject();
	    E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

	    if (StringUtils.isEmpty(reportOid)) {
		String oid = (String) reqMap.get("oid");
		if (StringUtils.containsIgnoreCase(oid, "costreport")) {
		    reportOid = (String) reqMap.get("oid");
		}
	    }

	    if (StringUtil.checkString(reportOid)) {
		cr = (CostReport) CommonUtil.getObject(reportOid);
	    } else {
		String projectOid = CommonUtil.getOIDString(master);
		String version = task.getCostVersion();
		cr = CostUtil.manager.getCostReport(projectOid, version);
	    }

	    if (cr == null) {

		String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

		cr = CostReport.newCostReport();
		cr.setProject(master);
		cr.setPjtNo(master.getPjtNo());
		cr.setVersion(task.getCostVersion());
		cr.setStep(drNo);
		cr.setTask(task);

		CostReport oldReport = CostUtil.manager.getPreviousCostReport(project);

		if (oldReport != null) {
		    cr.setPackSpec(oldReport.getPackSpec());
		    cr.setLogisticsFlow(oldReport.getLogisticsFlow());
		}

		WTContainerRef containerRef = WCUtil.getWTContainerRef();
		String folderPath = "/Default/자동차사업부/프로젝트/COST";

		// 결재 관련
		LifeCycleTemplate LCtemplate = LifeCycleHelper.service
		        .getLifeCycleTemplate("KET_COMMON_LC_PMS", WCUtil.getWTContainerRef());
		LifeCycleHelper.setLifeCycle(cr, LCtemplate);

		SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
		FolderHelper.assignLocation((FolderEntry) cr, folder);

		cr = (CostReport) PersistenceHelper.manager.save(cr);

	    } else {

		List<Map<String, Object>> cpList = (List<Map<String, Object>>) reqMap.get("cpList");

		String logisticsFlow = (String) reqMap.get("logisticsFlow");
		String reviewPurpose = (String) reqMap.get("reviewPurpose");
		String note = (String) reqMap.get("note");
		String packSpec = (String) reqMap.get("packSpec");
		String releaseStep = (String) reqMap.get("releaseStep");

		cr.setLogisticsFlow(logisticsFlow);
		cr.setReviewPurpose(reviewPurpose);
		cr.setBigo(note);
		cr.setTask(task);
		cr.setPackSpec(packSpec);
		cr.setReleaseStep(releaseStep);
		cr = (CostReport) PersistenceHelper.manager.modify(cr);

		if (cpList != null) {

		    for (Map<String, Object> partMap : cpList) {

			String oid = (String) partMap.get("oid");
			String reportUS = (String) partMap.get("reportUS");

			CostPart part = (CostPart) CommonUtil.getObject(oid);
			part.setReportUS(reportUS);

			PersistenceHelper.manager.save(part);
		    }
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

	return cr;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 3. 16. 오후 3:04:16
     * @method reCalculateCostPart
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> reCalculateCostPart(Map<String, Object> reqMap) throws Exception {
	LOGGER.info("############# RECALCULATE COST PART #######################");

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    String oid = (String) reqMap.get("oid");
	    CostPart part = (CostPart) CommonUtil.getObject(oid);
	    List<CostPart> spList = CostUtil.manager.syncCostPart(part);
	    /*
	     * Map<String, String> costCurrency = CostCodeHelper.service.getCostCurrency(part);
	     * 
	     * for (CostPart sPart : spList) {
	     * 
	     * sPart = setCostPartCSInfo(sPart, costCurrency); PersistenceHelper.manager.save(sPart); }
	     */

	    for (CostPart sPart : spList) {

		PersistenceHelper.manager.save(sPart);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 3. 9. 오전 10:36:29
     * @method copyNewVersionPart
     * @param task
     * @return E3PSTask
     * @throws Exception
     * </pre>
     */
    public E3PSTask copyNewVersionPart(E3PSTask task) throws Exception {

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    E3PSProject project = (E3PSProject) task.getProject();
	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();
	    String pjtMasterOid = CommonUtil.getOIDString(project.getMaster());
	    boolean isNewPartList = false;

	    String partListOid = "";

	    PartList partList = CostUtil.manager.getPartList(pjtMaster);

	    int lastestVersion = 0;
	    String lastestVersions = "";

	    if (partList != null) {
		partListOid = CommonUtil.getOIDString(partList);
		lastestVersion = partList.getLastestVersion();
		lastestVersions = String.valueOf(lastestVersion);
	    }

	    if (project instanceof ReviewProject) {

	    } else if (partList == null) {
		int maxLastestVersion = 0;
		String pjtNos[] = project.getReviewPjtNo().split(",");

		E3PSProject reviewPjt = ProjectHelper.getProject(pjtNos[0]);
		E3PSProjectMaster reviewPjtMaster = (E3PSProjectMaster) reviewPjt.getMaster();
		pjtMasterOid = CommonUtil.getOIDString(reviewPjtMaster);
		partList = CostUtil.manager.getPartList(reviewPjtMaster);
		partListOid = CommonUtil.getOIDString(partList);

		lastestVersion = partList.getLastestVersion();
		partList = (PartList) HistoryHelper.duplicate(partList);

		partList.setLastestVersion(0);
		partList = (PartList) PersistenceHelper.manager.save(partList);

		PjtMasterPartListLink link = PjtMasterPartListLink.newPjtMasterPartListLink(partList, pjtMaster);
		PersistenceHelper.manager.save(link);

		isNewPartList = true;
		partListOid = "";
		for (String reivewPjtNo : pjtNos) {
		    PartList oldPartList = null;
		    reviewPjt = ProjectHelper.getProject(reivewPjtNo);
		    reviewPjtMaster = (E3PSProjectMaster) reviewPjt.getMaster();
		    pjtMasterOid += CommonUtil.getOIDString(reviewPjtMaster) + ",";
		    oldPartList = CostUtil.manager.getPartList(reviewPjtMaster);
		    partListOid += CommonUtil.getOIDString(oldPartList) + ",";
		    maxLastestVersion = Math.max(maxLastestVersion, oldPartList.getLastestVersion());
		    lastestVersions += String.valueOf(oldPartList.getLastestVersion()) + ",";
		}
		pjtMasterOid = StringUtils.removeEnd(pjtMasterOid, ",");
		partListOid = StringUtils.removeEnd(partListOid, ",");
		lastestVersions = StringUtils.removeEnd(lastestVersions, ",");
		lastestVersion = maxLastestVersion;
	    }
	    List<Persistable> objList = new ArrayList<Persistable>();
	    String partListOidArr[] = partListOid.split(",");
	    String pjtMasterOidArr[] = pjtMasterOid.split(",");
	    String lastestVersionArr[] = lastestVersions.split(",");

	    reqMap.put("DATATYPE", "COSTPART");
	    reqMap.put("lastest", "true");

	    for (int i = 0; i < partListOidArr.length; i++) {
		reqMap.put("partListOid", partListOidArr[i]);
		reqMap.put("version", lastestVersionArr[i]);
		reqMap.put("pjtMasterOid", pjtMasterOidArr[i]);
		objList.addAll(getCostRootList(reqMap));
		// objList = getCostRootList(reqMap);
	    }

	    if (isNewPartList && project instanceof ReviewProject) {
		lastestVersion = 0;
	    } else {
		lastestVersion++;
	    }

	    // List<E3PSTask> taskList = new ArrayList<E3PSTask>();
	    // QueryResult qr = ProjectTaskHelper.manager.getChildList(project);

	    partList.setLastestVersion(lastestVersion);
	    partList = (PartList) PersistenceHelper.manager.save(partList);

	    task.setCostRequest(true);
	    task.setCostVersion(String.valueOf(lastestVersion));
	    task = (E3PSTask) PersistenceHelper.manager.save(task);

	    if (task.getParent() != null) {
		E3PSTask parentTask = (E3PSTask) task.getParent();
		if (!parentTask.isCostRequest()) {
		    parentTask.setCostRequest(true);
		    PersistenceHelper.manager.save(parentTask);
		}
	    }

	    // while (qr.hasMoreElements()) {
	    // E3PSTask updateTask = (E3PSTask) qr.nextElement();
	    // if (updateTask.isLeaf()) {
	    // taskList.add(updateTask);
	    // }
	    // }
	    //
	    // boolean isNextTask = false;
	    String taskOid = CommonUtil.getOIDString(task);
	    //
	    // for (E3PSTask updateTask : taskList) {
	    // String updateTaskOid = CommonUtil.getOIDString(updateTask);
	    //
	    // if (taskOid.equals(updateTaskOid)) {
	    // isNextTask = true;
	    // } else if (isNextTask && "COST013".equals(updateTask.getTaskTypeCategory())) {
	    // break;
	    // }
	    //
	    // if (isNextTask) {
	    // updateTask.setCostVersion(String.valueOf(lastestVersion));
	    // PersistenceHelper.manager.save(updateTask);
	    // }
	    // }

	    String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

	    List<Map<String, String>> targetList = new ArrayList<Map<String, String>>();

	    for (Persistable obj : objList) {

		DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(obj);

		CostPart rootOldPart = (CostPart) root.getUserObject();
		CostPart rootNewPart = (CostPart) HistoryHelper.duplicate(rootOldPart);
		ProductMaster oldMaster = rootOldPart.getMaster();

		rootNewPart.setProject(pjtMaster);
		rootNewPart.setVersion(lastestVersion);
		rootNewPart.setSubVersion(0);
		rootNewPart.setLastest(false);
		rootNewPart.setCaseOrder(null);
		rootNewPart.setDrStep(drNo);

		// if (isNewPartList) {/* 리비전 생성 후 부품 삭제 할때 마스터까지 삭제되는 현상때문에 ProductMaster는 무조건 새로 만듬 20210324 */

		ProductMaster master = (ProductMaster) HistoryHelper.duplicate(oldMaster);

		master.setMasterOid(CommonUtil.getOIDString(oldMaster));
		master.setPartList(partList);
		master = (ProductMaster) PersistenceHelper.manager.save(master);
		rootNewPart.setMaster(master);
		// }

		rootNewPart = (CostPart) PersistenceHelper.manager.save(rootNewPart);

		Map<String, String> targetMap = new HashMap<String, String>();
		targetMap.put(CommonUtil.getOIDString(rootOldPart), CommonUtil.getOIDString(rootNewPart));
		targetList.add(targetMap);

		for (int i = 0; i < root.getChildCount(); i++) {
		    DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
		    CostPart childOldPart = (CostPart) child.getUserObject();
		    CostPart childNewPart = (CostPart) HistoryHelper.duplicate(childOldPart);
		    childNewPart.setParent(rootNewPart);
		    childNewPart.setVersion(lastestVersion);
		    childNewPart.setSubVersion(0);
		    childNewPart.setLastest(false);
		    childNewPart.setCaseOrder(null);
		    childNewPart.setProject(pjtMaster);
		    childNewPart.setDrStep(drNo);
		    childNewPart = (CostPart) PersistenceHelper.manager.save(childNewPart);

		    targetMap = new HashMap<String, String>();
		    targetMap.put(CommonUtil.getOIDString(childOldPart), CommonUtil.getOIDString(childNewPart));
		    targetList.add(targetMap);

		    copyChildNewVersionPart(child, childNewPart, targetList);
		}
		CostCodeHelper.service.copyCostCurrencyInfo(rootOldPart, rootNewPart);
	    }
	    CostCodeHelper.service.copyRelatedObject(targetList);// CostPart 연관 Object copy

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

	return task;
    }

    private void copyChildNewVersionPart(DefaultMutableTreeNode node, CostPart part, List<Map<String, String>> targetList) throws Exception {

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

	    CostPart childOldPart = (CostPart) child.getUserObject();
	    CostPart childNewPart = (CostPart) HistoryHelper.duplicate(childOldPart);
	    childNewPart.setParent(part);
	    childNewPart.setVersion(part.getVersion());
	    childNewPart.setSubVersion(0);
	    childNewPart.setLastest(false);
	    childNewPart.setCaseOrder(null);
	    childNewPart.setProject(part.getProject());
	    childNewPart.setDrStep(part.getDrStep());
	    childNewPart = (CostPart) PersistenceHelper.manager.save(childNewPart);

	    Map<String, String> targetMap = new HashMap<String, String>();
	    targetMap.put(CommonUtil.getOIDString(childOldPart), CommonUtil.getOIDString(childNewPart));
	    targetList.add(targetMap);

	    copyChildNewVersionPart(child, childNewPart, targetList);
	}
    }

    /**
     * <pre>
     * @description COST BOM, 제품 삭제 로직 PARAMETER : partListOid, oid (COSTPART)
     * @author dhkim
     * @date 2018. 2. 26. 오후 1:42:01
     * @method deleteCostPart
     * @param reqMap
     * @throws Exception
     * </pre>
     */
    @Override
    public void deleteCostPart(Map<String, Object> reqMap) throws Exception {

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    String partListOid = (String) reqMap.get("partListOid");
	    List<CostPart> cPartList = CostTreeUtil.manager.getCostPartList(reqMap);

	    Map<String, String> masterMap = new HashMap<String, String>();

	    for (CostPart part : cPartList) {

		// Part 관련객체 삭제
		CostCodeHelper.service.deleteRelatedObject(part);

		ProductMaster master = part.getMaster();

		// part 삭제
		PersistenceHelper.manager.delete(part);

		if (master != null) {
		    masterMap.put(CommonUtil.getOIDString(master), "temp");
		}

	    }

	    // master 삭제
	    Set<String> st = masterMap.keySet();
	    Iterator<String> it = st.iterator();

	    while (it.hasNext()) {
		String masterOid = (String) it.next();
		PersistenceHelper.manager.delete(CommonUtil.getObject(masterOid));
	    }

	    // partList 삭제
	    if (partListOid != null) {
		PersistenceHelper.manager.delete(CommonUtil.getObject(partListOid));
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e.getLocalizedMessage());

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
    }

    /**
     * <pre>
     * @description COST BOM, 제품 삭제 로직 PARAMETER : partListOid, oid (COSTPART)
     * @author dhkim
     * @date 2019. 4. 25. 오전 11:08:42
     * @method deleteCostPartNonTrx
     * @param reqMap
     * @throws Exception
     * </pre>
     */
    @Override
    public void deleteCostPartNonTrx(Map<String, Object> reqMap) throws Exception {

	String partListOid = (String) reqMap.get("partListOid");
	List<CostPart> cPartList = CostTreeUtil.manager.getCostPartList(reqMap);

	Map<String, String> masterMap = new HashMap<String, String>();

	for (CostPart part : cPartList) {

	    // Part 관련객체 삭제
	    CostCodeHelper.service.deleteRelatedObject(part);

	    ProductMaster master = part.getMaster();

	    // part 삭제
	    PersistenceHelper.manager.delete(part);

	    if (master != null) {
		masterMap.put(CommonUtil.getOIDString(master), "temp");
	    }

	}

	// master 삭제
	Set<String> st = masterMap.keySet();
	Iterator<String> it = st.iterator();

	while (it.hasNext()) {
	    String masterOid = (String) it.next();
	    PersistenceHelper.manager.delete(CommonUtil.getObject(masterOid));
	}

	// // partList 삭제
	// if (partListOid != null) {
	// QueryResult qr = PersistenceHelper.manager.navigate(CommonUtil.getObject(partListOid), PjtMasterPartListLink.PROJECT_MASTER_ROLE
	// , PjtMasterPartListLink.class, false);
	// while (qr.hasMoreElements()) {
	// PjtMasterPartListLink link = (PjtMasterPartListLink)qr.nextElement();
	// PersistenceHelper.manager.delete(link);
	// }
	// PersistenceHelper.manager.delete(CommonUtil.getObject(partListOid));
	// }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostInvestInfo(Map<String, Object> reqMap) throws Exception {
	LOGGER.info("############# START SAVE COST INVEST INFO #######################");

	Map<String, Object> resMap = new HashMap<String, Object>();

	String oid = (String) reqMap.get("oid");
	CostPart part = (CostPart) CommonUtil.getObject(oid);

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    ObjectMapper mapper = new ObjectMapper();
	    String gridDataStr = (String) reqMap.get("gridData");

	    Map<String, Object> gridData = mapper.readValue(gridDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (gridData != null) {

		List<Map<String, Object>> changes = (List<Map<String, Object>>) gridData.get("Changes");

		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;
		    // boolean moved = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;
		    // if(data.get("Moved") != null) moved = true;

		    if (added && deleted)
			continue;

		    String id = (String) data.get("id");
		    String parent = (String) data.get("Parent");
		    String cpOid = (String) data.get("cpOid");
		    String reduceCode = (String) data.get("reduceCode");
		    CostInvestInfo investInfo = null;
		    CostInvestInfo pInvestInfo = null;
		    CostPart cp = (CostPart) CommonUtil.getObject(cpOid);

		    if (id.indexOf(CostInvestInfo.class.getName()) >= 0) {
			investInfo = (CostInvestInfo) CommonUtil.getObject(id);

			if (deleted) {
			    PersistenceHelper.manager.delete(investInfo);
			    continue;
			}

		    } else {
			investInfo = CostInvestInfo.newCostInvestInfo();
			pInvestInfo = (CostInvestInfo) CommonUtil.getObject(parent);
			investInfo.setParent(pInvestInfo);
			investInfo.setInvestType(pInvestInfo.getInvestType());
			// investInfo.setCostType(pInvestInfo.getCostType());
		    }

		    CostUtil.manager.convertMapToObject(data, investInfo);

		    if ("REDU003".equals(reduceCode)) {

			Map<String, Object> partMap = CostUtil.manager.converObjectToMap(cp);

			// 기계감가 표준
			String investReduceCost = StringUtil.checkReplaceStr(investInfo.getInvestReduceCost(), "0");
			String workUseHour = StringUtil.checkReplaceStr(investInfo.getWorkUseHour(), "0");
			String workUseDay = StringUtil.checkReplaceStr(investInfo.getWorkUseDay(), "0");
			String workUseYear = StringUtil.checkReplaceStr(investInfo.getWorkUseYear(), "0");
			// String exp = "parseInt((((" + investReduceCost + "*1.05)/(" + workUseHour + "*" + workUseDay + "*" + workUseYear
			// + "))+9)/10)*10";
			// String exp = "parseInt((((" + investReduceCost + ")/(" + workUseHour + "*" + workUseDay + "*" + workUseYear +
			// "))+9)/10)*10";
			// String machineReduceCost = (String) CostCalculateUtil.manager.eval(exp);

			String machineReduceCost = CostCalculateUtil.manager.getMachineReduceCost(investReduceCost, workUseHour,
			        workUseDay, workUseYear);

			partMap.put("machineReduceCost", machineReduceCost);
			investInfo.setMachineReduceCost(machineReduceCost);

			// 범용감가 CT/SPM 값 설정
			partMap.put("facReduceCtSpm", investInfo.getFacReduceCtSpm());

			// 범용감가 생산량 계산
			String exp = (String) CostCalculateUtil.manager.getAttrCaluateFormula(partMap, "facReduceOutputExpr").get(
			        "resultFormula");
			if (StringUtil.checkString(exp)) {
			    String facReduceOutputExpr = (String) CostCalculateUtil.manager.eval(exp);
			    partMap.put("facReduceOutputExpr", facReduceOutputExpr);
			    investInfo.setFacReduceOutputExpr(facReduceOutputExpr);
			}

			// 기계감가 계산
			exp = (String) CostCalculateUtil.manager.getAttrCaluateFormula(partMap, "machineDpcCostExpr").get("resultFormula");
			if (StringUtil.checkString(exp)) {
			    String machineDpcCostExpr = (String) CostCalculateUtil.manager.eval(exp);
			    investInfo.setMachineDpcCostExpr(machineDpcCostExpr);
			}
		    }

		    boolean isPersistent = PersistenceHelper.isPersistent(investInfo);

		    investInfo = (CostInvestInfo) PersistenceHelper.manager.save(investInfo);

		    if (!isPersistent) {
			CostReduceLink link = CostReduceLink.newCostReduceLink(investInfo, cp);
			PersistenceHelper.manager.save(link);
		    }
		}

		// ############### 기계감가 재설정 ###########################################################################################
		List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);
		Map<String, String> calcMRCost = new HashMap<String, String>();
		Map<String, String> calcCTSPM = new HashMap<String, String>();

		for (CostPart cp : cpList) {

		    QueryResult qr = PersistenceHelper.manager.navigate(cp, CostReduceLink.REDUCE_ROLE, CostReduceLink.class, true);
		    String cpOid = CommonUtil.getOIDString(cp);

		    while (qr.hasMoreElements()) {
			CostInvestInfo info = (CostInvestInfo) qr.nextElement();

			if ("REDU003".equals(info.getReduceCode())) {

			    // 기계감가 합산 처리
			    String mrCost = calcMRCost.get(cpOid);

			    if (mrCost == null) {
				mrCost = info.getMachineDpcCostExpr();
			    } else {
				String exp = mrCost + "+" + info.getMachineDpcCostExpr();
				mrCost = (String) CostCalculateUtil.manager.eval(exp);
			    }
			    calcMRCost.put(cpOid, mrCost);

			    // 범용감가 CT/SPM 합산 처리
			    String ctSPM = calcCTSPM.get(cpOid);

			    if (ctSPM == null) {
				ctSPM = info.getFacReduceCtSpm();
			    } else {
				String exp = ctSPM + "+" + info.getFacReduceCtSpm();
				ctSPM = (String) CostCalculateUtil.manager.eval(exp);
			    }
			    calcCTSPM.put(cpOid, ctSPM);

			}
		    }
		}

		for (CostPart cp : cpList) {
		    String cpOid = CommonUtil.getOIDString(cp);
		    String mrCost = StringUtil.checkReplaceStr(calcMRCost.get(cpOid), "0");
		    String cpSPM = StringUtil.checkReplaceStr(calcCTSPM.get(cpOid), "0");
		    cp.setMachineDpcCostExpr(mrCost);
		    cp.setFacReduceCtSpm(cpSPM);
		    PersistenceHelper.manager.save(cp);
		}
		// ############### 기계감가 재설정 ###########################################################################################
	    }

	    trx.commit();

	    trx.start();

	    part = (CostPart) CommonUtil.getObject(oid);

	    List<CostPart> spList = CostUtil.manager.syncCostPart(part);

	    for (CostPart sPart : spList) {
		PersistenceHelper.manager.save(sPart);
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

	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 1. 31. 오후 2:57:32
     * @method createCasePart
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> createCasePart(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    String oid = (String) reqMap.get("oid");
	    String baseOid = (String) reqMap.get("baseOid");
	    int version = Integer.parseInt(StringUtil.checkNullZero((String) reqMap.get("version")));
	    int subVersion = Integer.parseInt(StringUtil.checkNullZero((String) reqMap.get("subVersion")));
	    Persistable obj = CommonUtil.getObject(oid);
	    DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(obj);

	    CostPart rootOldPart = (CostPart) root.getUserObject();
	    CostPart rootNewPart = (CostPart) HistoryHelper.duplicate(rootOldPart);

	    if (StringUtil.checkString(baseOid)) {
		CostPart basePart = (CostPart) CommonUtil.getObject(baseOid);
		rootNewPart.setProject(basePart.getProject());
		rootNewPart.setMaster(basePart.getMaster());

		reqMap.remove("oid");
		reqMap.put("masterOid", CommonUtil.getOIDString(basePart.getMaster()));
		reqMap.put("version", String.valueOf(basePart.getVersion()));
		reqMap.put("DATATYPE", "COSTPART");
		List<Map<String, Object>> caseList = CostHelper.service.getCostRootMapList(reqMap);

		version = basePart.getVersion();
		subVersion = caseList.size();
	    }

	    if (version > 0) {
		rootNewPart.setVersion(version);
	    }
	    rootNewPart.setSubVersion(subVersion);
	    rootNewPart.setCaseOrder(null);
	    rootNewPart.setLastest(false);

	    rootNewPart = (CostPart) PersistenceHelper.manager.save(rootNewPart);

	    List<Map<String, String>> targetList = new ArrayList<Map<String, String>>();

	    Map<String, String> targetMap = new HashMap<String, String>();
	    targetMap.put(CommonUtil.getOIDString(rootOldPart), CommonUtil.getOIDString(rootNewPart));
	    targetList.add(targetMap);

	    resMap.put("oid", CommonUtil.getOIDString(rootNewPart));

	    for (int i = 0; i < root.getChildCount(); i++) {
		DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
		CostPart childOldPart = (CostPart) child.getUserObject();
		CostPart childNewPart = (CostPart) HistoryHelper.duplicate(childOldPart);
		childNewPart.setParent(rootNewPart);
		childNewPart.setProject(rootNewPart.getProject());
		childNewPart.setVersion(rootNewPart.getVersion());
		childNewPart.setSubVersion(subVersion);
		childNewPart.setCaseOrder(null);
		rootNewPart.setLastest(false);
		childNewPart = (CostPart) PersistenceHelper.manager.save(childNewPart);

		targetMap = new HashMap<String, String>();
		targetMap.put(CommonUtil.getOIDString(childOldPart), CommonUtil.getOIDString(childNewPart));
		targetList.add(targetMap);

		createChildCasePart(child, childNewPart, targetList);
	    }
	    CostCodeHelper.service.copyCostCurrencyInfo(rootOldPart, rootNewPart);
	    CostCodeHelper.service.copyRelatedObject(targetList);// CostPart 연관 Object copy

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

	return resMap;
    }

    /**
     * <pre>
     * @description CASE 삭제
     * @author dhkim
     * @date 2018. 10. 30. 오후 3:23:43
     * @method deleteCasePart
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteCasePart(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();

	try {

	    trx.start();

	    List<String> caseList = (List<String>) reqMap.get("caseList");
	    List<String> deleteList = (List<String>) reqMap.get("deleteList");

	    for (String delOid : deleteList) {

		DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(CommonUtil.getObject(delOid));
		CostPart part = (CostPart) root.getUserObject();

		saveAndDeleteCasePart(root, part, true);
		CostCodeHelper.service.deleteRelatedObject(part);
		PersistenceHelper.manager.delete(part);
	    }

	    int subVersion = 0;

	    for (String oid : caseList) {

		DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(CommonUtil.getObject(oid));
		CostPart part = (CostPart) root.getUserObject();
		part.setSubVersion(subVersion);
		saveAndDeleteCasePart(root, part, false);
		subVersion++;

		PersistenceHelper.manager.save(part);
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

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 31. 오후 4:16:22
     * @method setChildCasePart
     * @param node
     * @param part
     * @throws Exception
     * </pre>
     */
    private void createChildCasePart(DefaultMutableTreeNode node, CostPart part, List<Map<String, String>> targetList) throws Exception {

	for (int i = 0; i < node.getChildCount(); i++) {
	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

	    CostPart childOldPart = (CostPart) child.getUserObject();
	    CostPart childNewPart = (CostPart) HistoryHelper.duplicate(childOldPart);
	    childNewPart.setParent(part);
	    childNewPart.setProject(part.getProject());
	    childNewPart.setVersion(part.getVersion());
	    childNewPart.setSubVersion(part.getSubVersion());
	    childNewPart = (CostPart) PersistenceHelper.manager.save(childNewPart);

	    Map<String, String> targetMap = new HashMap<String, String>();
	    targetMap.put(CommonUtil.getOIDString(childOldPart), CommonUtil.getOIDString(childNewPart));
	    targetList.add(targetMap);

	    createChildCasePart(child, childNewPart, targetList);
	}
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 2. 1. 오전 11:55:04
     * @method saveCasePart
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCasePart(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	List<Map<String, String>> saveList = (List<Map<String, String>>) reqMap.get("saveList");

	for (Map<String, String> data : saveList) {

	    String caseOid = data.get("caseOid");
	    String caseNote = data.get("caseNote");
	    String caseOrder = data.get("caseOrder");
	    boolean lastest = "1".equals(caseOrder);

	    CostPart part = (CostPart) CommonUtil.getObject(caseOid);
	    DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(part);

	    if (StringUtil.checkString(caseOrder)) {
		part.setCaseOrder(Integer.parseInt(caseOrder));
	    } else {
		part.setCaseOrder(null);
	    }
	    part.setCaseNote(caseNote);
	    part.setLastest(lastest);

	    saveAndDeleteCasePart(root, part, false);

	    PersistenceHelper.manager.save(part);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 6. 오전 10:30:35
     * @method saveAndDeleteCasePart
     * @param node
     * @param isDelete
     * @param subVersion
     * @throws Exception
     * </pre>
     */
    private void saveAndDeleteCasePart(DefaultMutableTreeNode node, CostPart parentPart, boolean isDelete) throws Exception {

	for (int i = 0; i < node.getChildCount(); i++) {

	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);

	    CostPart part = (CostPart) child.getUserObject();

	    part.setCaseNote(parentPart.getCaseNote());
	    part.setSubVersion(parentPart.getSubVersion());
	    part.setLastest(parentPart.isLastest());
	    part.setCaseOrder(parentPart.getCaseOrder());

	    saveAndDeleteCasePart(child, part, isDelete);

	    if (isDelete) {
		CostCodeHelper.service.deleteRelatedObject(part);
		PersistenceHelper.manager.delete(part);
	    } else {
		PersistenceHelper.manager.save(part);
	    }
	}
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2017. 11. 17. 오전 10:52:14
     * @method saveCostPart
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostPart(Map<String, Object> reqMap) throws Exception {

	LOGGER.info("############# START SAVE COST PART #######################");

	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, CostPart> savePartList = new HashMap<String, CostPart>();

	Transaction trx = new Transaction();
	try {

	    trx.start();
	    ObjectMapper mapper = new ObjectMapper();
	    String EDITMODE = (String) reqMap.get("EDITMODE");
	    String EDITTYPE = (String) reqMap.get("EDITTYPE");
	    String taskOid = (String) reqMap.get("taskOid");
	    String oid = (String) reqMap.get("oid");
	    String treeDataStr = (String) reqMap.get("treeData");
	    int subVersion = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("subVersion"), "0"));
	    boolean isLastest = false;
	    String caseOrder = "";
	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    CostPart newRootPart = null;

	    if (treeData != null) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(3);
		E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
		E3PSProject project = (E3PSProject) task.getProject();
		E3PSProjectMaster pjtMatser = (E3PSProjectMaster) project.getMaster();
		PartList partList = CostUtil.manager.getPartList(pjtMatser);
		String version = StringUtil.checkReplaceStr(task.getCostVersion(), "0");

		int formulaVersion = CostUtil.manager.getLastestFormulaVersion();

		String pjtNo = pjtMatser.getPjtNo();
		int newPartNo = CostTreeUtil.manager.getGenratePartNo(pjtNo);

		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");

		List<String> itemIds = new ArrayList<String>();
		getItemsOidSetting(items, itemIds);

		Map<String, String> costCurrency = new HashMap<String, String>();

		String[] attrLocked = StringUtil.checkReplaceStr((String) reqMap.get("attrLocked"), "").split(",");
		String attrUnLocked = (String) reqMap.get("attrUnLocked");

		if (StringUtil.checkString(oid) && CommonUtil.getObject(oid) != null) {
		    CostPart part = (CostPart) CommonUtil.getObject(oid);
		    version = part.getVersion().toString();
		    subVersion = part.getSubVersion();
		    isLastest = part.isLastest();
		    if (part.getCaseOrder() != null) {
			caseOrder = part.getCaseOrder().toString();
		    }

		    // ######################## 원가산출 기준정보 Lock, UnLock Start ##############################
		    String[] lockList = StringUtil.checkReplaceStr(part.getAttrLocked(), "").split(",");
		    if (lockList != null) {
			String attrLockStr = "";

			// 원가산출 Unlocked
			for (String lock : lockList) {
			    if (attrUnLocked != null && attrUnLocked.indexOf(lock) < 0) {
				attrLockStr += lock + ",";
			    }
			}

			// 원가산출 Locked
			for (String attrLock : attrLocked) {
			    if (attrLockStr != null && attrLockStr.indexOf(attrLock) < 0) {
				attrLockStr += attrLock + ",";
			    }
			}
			attrLockStr = StringUtils.removeEnd(attrLockStr, ",");
			// ######################## 원가산출 기준정보 Lock, UnLock End #################################
			if (StringUtils.isNotEmpty(attrLockStr)) {
			    part.setAttrLocked(attrLockStr);

			}
		    }
		    PersistenceHelper.manager.save(part);

		}

		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;
		    boolean moved = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;
		    if (data.get("Moved") != null)
			moved = true;

		    String id = (String) data.get("id");
		    String parent = (String) data.get("Parent");
		    String level = (String) data.get("Level");
		    CostPart costPart = null;
		    CostPart pCostPart = null;

		    // String partNo = StringUtil.checkNull((String) data.get("partNo"));

		    if (id.indexOf(CostPart.class.getName()) >= 0) {
			costPart = (CostPart) CommonUtil.getObject(id);
		    } else {

			costPart = CostPart.newCostPart();

			// String partType = (String) data.get("partType");
			// String partCode = "";
			//
			// if (StringUtil.checkString(partType)) {
			// CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partType);
			// partCode = cpType.getCode();
			// }

			String costPartNo = pjtNo + "-" + nf.format(newPartNo);
			// partNo = costPartNo;
			data.put("partNo", costPartNo);
			newPartNo++;

			String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

			data.put("drStep", drNo);
			data.put("calcStdMaterial", "CALCSTD001");
			data.put("calcStdLabor", "CALCSTD004");
			data.put("calcStdExpense", "CALCSTD007");
			data.put("calcStdManage", "CALCSTD005");
			data.put("calcStdOutPut", "CALCSTD006");
			data.put("version", Integer.parseInt(version));
			data.put("formulaVersion", formulaVersion);
			if ("0".equals(level)) {

			} else {
			    costPart.setLastest(isLastest);
			    if (StringUtils.isNotEmpty(caseOrder)) {
				costPart.setCaseOrder(Integer.parseInt(caseOrder));
			    }
			}

		    }

		    if (parent != null && parent.indexOf(CostPart.class.getName()) >= 0) {
			pCostPart = (CostPart) CommonUtil.getObject(parent);
		    } else {
			pCostPart = savePartList.get(parent);
		    }

		    if (deleted && PersistenceHelper.isPersistent(costPart)) {

			File copyImg = new File(CostPartImgUtil.STOREPATH + costPart.getPartNo() + ".jpg");

			if (copyImg.isFile()) {
			    copyImg.delete();
			}

			itemIds.remove(id);
			ProductMaster master = costPart.getMaster();

			CostCodeHelper.service.deleteRelatedObject(costPart); // 관련 Object 삭제 2019.01.31

			PersistenceHelper.manager.delete(costPart);

			if (master != null) {// 버전별로 master를 공유해서 쓰기 때문에 참조관계에 있는 part가 있으면 삭제 하지 않는다 2019.01.31
			    List<CostPart> drPartList = CostUtil.manager.getAllVersionLastestPart(master);

			    if (drPartList.size() == 0) {
				PersistenceHelper.manager.delete(master);
			    }

			}
		    }

		    if (deleted)
			continue;

		    CostUtil.manager.convertMapToObject(data, costPart);
		    costPart.setProject(pjtMatser);

		    if ("TREEEDIT".equals(EDITMODE)) {

			costPart.setSortLocation(itemIds.indexOf(id));

			if (pCostPart != null) {

			    costPart.setParent(pCostPart);

			} else if (added || moved) {

			    costPart.setParent(CostPart.newCostPart());

			    ProductMaster master = ProductMaster.newProductMaster();
			    master.setPartList(partList);
			    master = (ProductMaster) PersistenceHelper.manager.save(master);
			    costPart.setMaster(master);
			    costPart.setUs("1");

			}

			costPart.setBomLevel(level);
			if ("0".equals(level)) {
			    costPart.setSubVersion(0);
			} else {
			    costPart.setSubVersion(subVersion);
			}

		    }
		    if ("0".equals(level)) {
			costCurrency = CostCodeHelper.service.getCostCurrency(costPart);
		    }

		    costPart = setCostPartCSInfo(costPart, costCurrency);
		    costPart = (CostPart) PersistenceHelper.manager.save(costPart);
		    savePartList.put(id, costPart);

		    if ("0".equals(level) && "NEWPART".equals(EDITTYPE)) {
			newRootPart = costPart;
		    }

		    if (pCostPart != null && "TREEEDIT".equals(EDITMODE)) {
			ProductMaster master = costPart.getMaster();
			if (master != null)
			    PersistenceHelper.manager.delete(master);
		    }
		}

		if ("TREEEDIT".equals(EDITMODE)) {
		    // 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		    for (int i = 0; i < itemIds.size(); i++) {
			String itemId = itemIds.get(i);

			if (itemId.indexOf(CostPart.class.getName()) < 0)
			    continue;

			CostPart costPart = (CostPart) CommonUtil.getObject(itemId);
			if (savePartList.containsKey(itemId))
			    continue;
			if (i == costPart.getSortLocation())
			    continue;

			costPart.setSortLocation(i);
			PersistenceHelper.manager.save(costPart);
		    }
		}

		if ("NEWPART".equals(EDITTYPE) && newRootPart != null) {
		    Map<String, Object> paramMap = new HashMap<String, Object>();
		    paramMap.put("partListOid", CommonUtil.getOIDString(partList));
		    paramMap.put("subVersion", "0");
		    paramMap.put("DATATYPE", "COSTPART");

		    List<Persistable> rootList = CostHelper.service.getCostRootList(paramMap);
		    int maxSortLocation = rootList.size();
		    newRootPart.setSortLocation(maxSortLocation);
		    PersistenceHelper.manager.save(newRootPart);
		}
	    }

	    trx.commit();

	    trx.start();

	    List<CostPart> rootList = new ArrayList<CostPart>();
	    Set<String> st = savePartList.keySet();
	    Iterator<String> it = st.iterator();
	    while (it.hasNext()) {
		String id = it.next();
		CostPart part = savePartList.get(id);

		part = (CostPart) PersistenceHelper.manager.refresh(part);

		CostPart rootPart = getRootPart(part);

		if (!rootList.contains(rootPart))
		    rootList.add(rootPart);
	    }

	    for (CostPart sRoot : rootList) {

		List<CostPart> spList = CostUtil.manager.syncCostPart(sRoot);

		for (CostPart sPart : spList) {
		    PersistenceHelper.manager.save(sPart);
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

	return resMap;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 13. 오전 11:44:28
     * @method getRootPart
     * @param part
     * @return CostPart
     * </pre>
     */
    private CostPart getRootPart(CostPart part) {

	if (part.getParent() != null) {
	    part = getRootPart((CostPart) part.getParent());
	}
	LOGGER.info("################################# getRootPart #########" + CommonUtil.getOIDString(part));
	return part;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 25. 오전 11:01:11
     * @method setCostPartCSInfo
     * @param part
     * @return CostPart
     * </pre>
     * 
     * @throws WTException
     * @throws Exception
     */
    @Override
    public CostPart setCostPartCSInfo(CostPart part, Map<String, String> costCurrency) throws Exception {

	String platingSpec = part.getPrePlatingSpec();
	String thickness = StringUtil.checkReplaceStr(part.getRMatNMetalT(), "0");
	String width = StringUtil.checkReplaceStr(part.getRMatNMetalW(), "0");

	String rMatName = part.getRMatNMetalName();

	CSInfo csInfo = CostUtil.manager.getLatestCSInfo("NMETAL" + rMatName);

	LOGGER.info("csInfo ==== " + csInfo);

	if (csInfo == null) {
	    return part;
	}
	String metalScenario = StringUtil.checkReplaceStr(part.getMetalScenario(), "SNR002");
	// String scrapInternal = StringUtil.checkReplaceStr(csInfo.getScrapInternal(), "0");
	// String scrapChina = StringUtil.checkReplaceStr(csInfo.getScrapChina(), "0");
	LOGGER.info("csInfo.getImportance() ==== " + csInfo.getImportance());

	part.setMetalScenario(metalScenario);
	part.setMetalImportance(csInfo.getImportance());
	part.setMetalScrapCost("0");
	part.setMetalLmeStd("0");
	part.setMetalPlateCost("0");
	part.setMetalCuttingCost("0");
	part.setMetalTollPrcCost("0");
	part.setMetalScrapCost("0");
	String factory = ((NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + part.getMftFactory1())).getCode();

	List<CSInfoItemDTO> itemList = CostUtil.manager.getCSInfoItemList(csInfo);

	// 비철원재료 단가
	for (CSInfoItemDTO item : itemList) {

	    String extRate = costCurrency.get(item.getmUnit());
	    extRate = StringUtil.checkReplaceStr(extRate, "1");

	    switch (item.getItemType()) {
	    case CostUtil.NMETALCOST: // 비철단가

		if (metalScenario.equals(item.getValue1())) {
		    part.setMetalLmeStd(item.getValue2());

		    String exp = item.getValue3() + "*" + extRate;
		    String value = (String) CostCalculateUtil.manager.eval(exp);
		    // part.setMetalScrapCost(value);
		    part.setMetalLmeCost(value);
		}

		break;
	    case CostUtil.PLATINGCOST: // 도금단가

		NumberCode code = item.getValue1Code();
		if (compareCodeValue(code, thickness)) {

		    String exp = item.getValue2() + "*" + extRate;
		    String value = (String) CostCalculateUtil.manager.eval(exp);

		    part.setMetalPlateCost(value);
		}

		break;
	    case CostUtil.CUTTINGCOST: // 절단비

		NumberCode code1 = item.getValue1Code();
		NumberCode code2 = item.getValue2Code();

		if (compareCodeValue(code1, width) && compareCodeValue(code2, thickness)) {

		    String exp = item.getValue3() + "*" + extRate;
		    String value = (String) CostCalculateUtil.manager.eval(exp);
		    part.setMetalCuttingCost(value);

		}

		break;
	    case CostUtil.SCRAPRECYCLECOST: // 임가공비, De-Tin

		if (StringUtils.isNotEmpty(platingSpec) && (factory.equals(item.getValue4()) && platingSpec.equals(item.getValue1()))) {// part의
			                                                                                                                // 생산국과
			                                                                                                                // 선도금사양이
			                                                                                                                // 기준

		    String exp = item.getValue2() + "*" + extRate;
		    String value = (String) CostCalculateUtil.manager.eval(exp);
		    part.setMetalDeTinCost(value);

		    exp = item.getValue3() + "*" + extRate;
		    value = (String) CostCalculateUtil.manager.eval(exp);
		    part.setMetalTollPrcCost(value);
		}

		break;

	    case CostUtil.SCRAPSALESCOST: // 스크랩 판매비

		// part의 시나리오, 생산국, 선도금사양이 기준
		if (StringUtils.isNotEmpty(platingSpec)
		        && (metalScenario.equals(item.getValue5()) && factory.equals(item.getValue1()) && platingSpec.equals(item
		                .getValue2()))) {

		    String exp = item.getValue3() + "*" + extRate;
		    String value = (String) CostCalculateUtil.manager.eval(exp);
		    part.setMetalScrapCost(value);
		}

		break;
	    }
	}

	String prePlatingCost = StringUtil.checkReplaceStr(part.getPrePlatingCost(), "0");
	String lmeCost = StringUtil.checkReplaceStr(part.getMetalLmeCost(), "0");
	String plateCost = StringUtil.checkReplaceStr(part.getMetalPlateCost(), "0");
	String cuttingCost = StringUtil.checkReplaceStr(part.getMetalCuttingCost(), "0");
	String deTinCost = StringUtil.checkReplaceStr(part.getMetalDeTinCost(), "0");
	String tollPrcCost = StringUtil.checkReplaceStr(part.getMetalTollPrcCost(), "0");

	String exp = "";

	prePlatingCost = (String) CostCalculateUtil.manager.eval(prePlatingCost + "*" + String.valueOf(part.getPrePlatingUnitCurrency()));// 선도금단가
	// *
	// 선도금단가
	// 환율

	if ("RMT005".equals(rMatName) || "RMT006".equals(rMatName) || "RMT009".equals(rMatName)) {// PMC26, PMC90, C194
	    if ("PTG002".equals(platingSpec)) {// Unplate

		exp = lmeCost + "-" + plateCost; // LME단가 + 도금비

	    } else if ("PTG001".equals(platingSpec)) {// Pre-Tin

		exp = lmeCost + "+0";// LME단가

	    } else if ("PTG003".equals(platingSpec) || "PTG004".equals(platingSpec) || "PTG005".equals(platingSpec)) {// Au도금 , Ag도금, 기타도금

		exp = lmeCost + "-" + plateCost + "+" + prePlatingCost; // LME단가 - 도금비 + 선도금단가

	    }
	} else {
	    if ("PTG002".equals(platingSpec)) {// Unplate

		exp = lmeCost + "+" + cuttingCost; // LME단가 + 절단비

	    } else if ("PTG001".equals(platingSpec)) {// Pre-Tin

		exp = lmeCost + "+" + plateCost + "+" + cuttingCost; // LME단가 + 도금비 + 절단비

	    } else if ("PTG003".equals(platingSpec) || "PTG004".equals(platingSpec) || "PTG005".equals(platingSpec)) {// Au도금 , Ag도금, 기타도금

		exp = lmeCost + "+" + prePlatingCost + "+" + cuttingCost; // LME단가 + 선도금단가 + 절단비

	    }
	}

	String mtrMetalPrice = (String) CostCalculateUtil.manager.eval(exp);
	part.setMtrMetalPrice(mtrMetalPrice);

	// 스크랩 재생비 설정

	if ("PTG002".equals(platingSpec)) {// Unplate

	    exp = tollPrcCost + "+" + cuttingCost; // 임가공비 + 절단비

	} else if ("PTG001".equals(platingSpec)) {// Pre-Tin

	    exp = deTinCost + "+" + tollPrcCost + "+" + plateCost + "+" + cuttingCost; // De-Tin + 임가공비 + 도금단가 + 절단비

	} else if ("PTG003".equals(platingSpec) || "PTG004".equals(platingSpec)) {// Au도금 , Ag도금

	    exp = "0";

	} else if ("PTG005".equals(platingSpec)) {// 기타도금

	    exp = deTinCost + "+" + tollPrcCost + "+" + prePlatingCost + "+" + cuttingCost; // De-Tin + 임가공비 + 선도금단가 + 절단비

	}

	String scrapInternal = (String) CostCalculateUtil.manager.eval(exp);

	String partType = part.getPartType();
	String partCode = "";

	if (StringUtil.checkString(partType)) {
	    CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partType);
	    partCode = cpType.getCode();

	}

	part.setMetalScrapRecycle(scrapInternal);// 스크랩 재생비

	if ("MOLD".equals(partCode)) {

	    String productWeight = StringUtil.checkReplaceStr(part.getProductWeight(), "0");
	    String scrapWeight = StringUtil.checkReplaceStr(part.getScrapWeight(), "0");

	    // 제품중량 + 스크랩중량
	    exp = productWeight + "+" + scrapWeight;
	    String totalWeight = (String) CostCalculateUtil.manager.eval(exp);
	    part.setTotalWeight(totalWeight);

	} else if ("PRESS".equals(partCode)) {

	    String rMatNMetalT = StringUtil.checkReplaceStr(part.getRMatNMetalT(), "0");
	    String rMatNMetalW = StringUtil.checkReplaceStr(part.getRMatNMetalW(), "0");
	    String rMatNMetalP = StringUtil.checkReplaceStr(part.getRMatNMetalP(), "0");
	    String metalImportance = StringUtil.checkReplaceStr(part.getMetalImportance(), "0");
	    String productWeight = StringUtil.checkReplaceStr(part.getProductWeight(), "0");

	    // (원재료 두께 x 폭 x 피치 x 비중 (비중은 선택된 원재료 코드의 비철원재료 기준정보로 설정) ) / 1000
	    exp = "(" + rMatNMetalT + "*" + rMatNMetalW + "*" + rMatNMetalP + "*" + metalImportance + ")/1000";
	    String totalWeight = (String) CostCalculateUtil.manager.eval(exp);
	    part.setTotalWeight(totalWeight);

	    // 총중량 - 제품중량
	    exp = totalWeight + "-" + productWeight;
	    String scrapWeight = (String) CostCalculateUtil.manager.eval(exp);
	    part.setScrapWeight(scrapWeight);

	}

	return part;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 1. 25. 오후 3:20:12
     * @method compareCodeValue
     * @param code
     * @param value
     * @return boolean
     * @throws Exception
     * </pre>
     */
    private boolean compareCodeValue(NumberCode code, String value) throws Exception {

	String exp = "";

	String name = code.getName();

	name = name.replaceAll("t", "").trim();

	StringTokenizer stoken = new StringTokenizer(name, "~");

	if (stoken.hasMoreTokens()) {
	    String cmpValue = stoken.nextToken();

	    String cmp = "<=";

	    if (cmpValue.indexOf("이상") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("이상"));
		cmp = "<=";
	    } else if (cmpValue.indexOf("이하") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("이하"));
		cmp = ">=";
	    } else if (cmpValue.indexOf("초과") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("초과"));
		cmp = "<";
	    } else if (cmpValue.indexOf("미만") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("미만"));
		cmp = ">";
	    }

	    exp += cmpValue;
	    exp += cmp;
	    exp += value;
	}

	if (stoken.hasMoreTokens()) {
	    String cmpValue = stoken.nextToken();

	    String cmp = ">=";

	    if (cmpValue.indexOf("이상") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("이상"));
		cmp = "<=";
	    } else if (cmpValue.indexOf("이하") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("이하"));
		cmp = ">=";
	    } else if (cmpValue.indexOf("초과") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("초과"));
		cmp = "<";
	    } else if (cmpValue.indexOf("미만") >= 0) {
		cmpValue = cmpValue.substring(0, cmpValue.indexOf("미만"));
		cmp = ">";
	    }

	    exp += "&&";
	    exp += cmpValue;
	    exp += cmp;
	    exp += value;
	}

	String result = String.valueOf(CostCalculateUtil.engine.eval(exp));
	return Boolean.parseBoolean(result);
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 1. 30. 오후 3:05:55
     * @method saveCostPartInfo
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostPartInfo(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	try {

	    trx.start();
	    String infoType = (String) reqMap.get("infoType");
	    String oid = (String) reqMap.get("oid");

	    if ("exRateInfo".equals(infoType)) {

		List<Map<String, Object>> dataList = (List<Map<String, Object>>) reqMap.get("data");
		Map<String, String> costCurrency = new HashMap<String, String>();
		for (Map<String, Object> data : dataList) {

		    String id = (String) data.get("id");
		    CostCurrencyInfo obj = (CostCurrencyInfo) CommonUtil.getObject(id);
		    CostUtil.manager.convertMapToObject(data, obj);
		    costCurrency.put(obj.getValue1(), obj.getValue2());

		    PersistenceHelper.manager.save(obj);
		}

		CostPart productPart = (CostPart) CommonUtil.getObject(oid);

		List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(productPart);

		for (CostPart cp : cpList) {
		    cp = setCostPartCSInfo(cp, costCurrency);
		    PersistenceHelper.manager.save(cp);
		}

	    } else if ("investInfo".equals(infoType)) {

		List<Map<String, Object>> dataList = (List<Map<String, Object>>) reqMap.get("data");

		Map<String, String> calcMDCost = new HashMap<String, String>();
		Map<String, String> calcCTSPM = new HashMap<String, String>();

		for (Map<String, Object> data : dataList) {
		    String id = (String) data.get("id");
		    String partOid = (String) data.get("partOid");
		    Map<String, Object> partMap = CostUtil.manager.converObjectToMap(CommonUtil.getObject(partOid));

		    CostInvestInfo info = (CostInvestInfo) CommonUtil.getObject(id);
		    CostUtil.manager.convertMapToObject(data, info);

		    // 기계감가 표준
		    String investReduceCost = StringUtil.checkReplaceStr(info.getInvestReduceCost(), "0");
		    String workUseHour = StringUtil.checkReplaceStr(info.getWorkUseHour(), "0");
		    String workUseDay = StringUtil.checkReplaceStr(info.getWorkUseDay(), "0");
		    String workUseYear = StringUtil.checkReplaceStr(info.getWorkUseYear(), "0");
		    // String exp = "parseInt((((" + investReduceCost + "*1.05)/(" + workUseHour + "*" + workUseDay + "*" + workUseYear +
		    // "))+9)/10)*10";
		    // String exp = "parseInt((((" + investReduceCost + ")/(" + workUseHour + "*" + workUseDay + "*" + workUseYear +
		    // "))+9)/10)*10";
		    // String machineReduceCost = (String) CostCalculateUtil.manager.eval(exp);
		    String machineReduceCost = CostCalculateUtil.manager.getMachineReduceCost(investReduceCost, workUseHour, workUseDay,
			    workUseYear);
		    partMap.put("machineReduceCost", machineReduceCost);
		    info.setMachineReduceCost(machineReduceCost);

		    // 범용감가 CT/SPM 값 설정
		    partMap.put("facReduceCtSpm", info.getFacReduceCtSpm());

		    // 범용감가 생산량 계산
		    String exp = (String) CostCalculateUtil.manager.getAttrCaluateFormula(partMap, "facReduceOutputExpr").get(
			    "resultFormula");
		    if (StringUtil.checkString(exp)) {
			String facReduceOutputExpr = (String) CostCalculateUtil.manager.eval(exp);
			partMap.put("facReduceOutputExpr", facReduceOutputExpr);
			info.setFacReduceOutputExpr(facReduceOutputExpr);
		    }

		    // 기계감가 계산
		    exp = (String) CostCalculateUtil.manager.getAttrCaluateFormula(partMap, "machineDpcCostExpr").get("resultFormula");
		    if (StringUtil.checkString(exp)) {
			String machineDpcCostExpr = (String) CostCalculateUtil.manager.eval(exp);
			info.setMachineDpcCostExpr(machineDpcCostExpr);
		    }

		    // 기계감가 합산 처리
		    String mdCost = calcMDCost.get(partOid);

		    if (mdCost == null) {
			mdCost = info.getMachineDpcCostExpr();
		    } else {
			exp = mdCost + "+" + info.getMachineDpcCostExpr();
			mdCost = (String) CostCalculateUtil.manager.eval(exp);
		    }
		    calcMDCost.put(partOid, mdCost);

		    // 범용감가 CT/SPM 합산 처리
		    String ctSPM = calcCTSPM.get(partOid);

		    if (ctSPM == null) {
			ctSPM = info.getFacReduceCtSpm();
		    } else {
			exp = ctSPM + "+" + info.getFacReduceCtSpm();
			ctSPM = (String) CostCalculateUtil.manager.eval(exp);
		    }
		    calcCTSPM.put(partOid, ctSPM);

		    PersistenceHelper.manager.save(info);
		}

		Set<String> st = calcMDCost.keySet();
		Iterator<String> it = st.iterator();

		while (it.hasNext()) {
		    String cpOid = it.next();
		    String mdCost = calcMDCost.get(cpOid);
		    String ctSPM = calcCTSPM.get(cpOid);
		    CostPart part = (CostPart) CommonUtil.getObject(cpOid);
		    part.setMachineDpcCostExpr(mdCost);
		    part.setFacReduceCtSpm(ctSPM);

		    PersistenceHelper.manager.save(part);
		}

	    } else if ("nMetalInfo".equals(infoType)) {

		String metalScenario = (String) reqMap.get("metalScenario");

		CostPart productPart = (CostPart) CommonUtil.getObject(oid);

		List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(productPart);

		Map<String, String> costCurrency = CostCodeHelper.service.getCostCurrency(productPart);

		for (CostPart cp : cpList) {
		    cp.setMetalScenario(metalScenario);
		    cp = setCostPartCSInfo(cp, costCurrency);
		    PersistenceHelper.manager.save(cp);
		}

	    } else if ("nMetalInfoByUserInput".equals(infoType) || "profitInfo".equals(infoType)) {

		List<Map<String, Object>> dataList = (List<Map<String, Object>>) reqMap.get("data");

		for (Map<String, Object> data : dataList) {

		    String id = (String) data.get("id");
		    CostPart obj = (CostPart) CommonUtil.getObject(id);
		    CostUtil.manager.convertMapToObject(data, obj);
		    PersistenceHelper.manager.save(obj);
		}

	    } else {

		Persistable obj = CommonUtil.getObject(oid);

		obj = (Persistable) CostUtil.manager.convertMapToObject(reqMap, obj);
		obj = CostCalculateUtil.manager.calculateTotalCost((CostPart) obj);

		PersistenceHelper.manager.save(obj);
	    }

	    trx.commit();

	    CostPart part = (CostPart) CommonUtil.getObject(oid);

	    if ("exRateInfo".equals(infoType) || "quantityInfo".equals(infoType)) {

		trx.start();

		if ("exRateInfo".equals(infoType)) {

		    CostCodeHelper.service.refreshCurrencyInfo(oid);
		    CostCodeHelper.service.syncPartByMyCurrency(part);

		} else if ("quantityInfo".equals(infoType)) {

		    CostCodeHelper.service.udtQtyInfoMySubTree(part, true, true);

		}
		trx.commit();
	    }

	    trx.start();

	    part = (CostPart) CommonUtil.getObject(oid);

	    List<CostPart> spList = CostUtil.manager.syncCostPart(part);

	    for (CostPart sPart : spList) {
		PersistenceHelper.manager.save(sPart);
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

	// case "exRateInfo" :
	// case "profitInfo" :
	// case "nMetalInfo" :
	// case "reductionInfo" :
	// case "quantityInfo" :

	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2017. 12. 8. 오후 12:14:02
     * @method saveFormula
     * @param reqMap
     * @return
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostFormula(Map<String, Object> reqMap) throws Exception {

	LOGGER.info("############# START SAVE COST FORMULA #######################");

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();

	ObjectMapper mapper = new ObjectMapper();
	// String attrType = (String) reqMap.get("attrType");
	String treeDataStr = (String) reqMap.get("treeData");
	int formulaVersion = Integer.valueOf((String) reqMap.get("version"));
	try {

	    trx.start();

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

		if (body.size() > 0) {
		    items = (List<Map<String, Object>>) body.get(0).get("Items");
		}

		List<String> itemIds = new ArrayList<String>();
		getItemsOidSetting(items, itemIds);
		Map<String, CostFormula> savePartList = new HashMap<String, CostFormula>();
		Map<String, String> updateList = new HashMap<String, String>();

		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;
		    boolean moved = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;
		    if (data.get("Moved") != null)
			moved = true;

		    String id = (String) data.get("id");
		    String parent = (String) data.get("Parent");
		    String formulaKeys = (String) data.get("formulaKeys");

		    CostFormula formula = null;
		    CostFormula pFormula = null;

		    if (id.indexOf(CostFormula.class.getName()) >= 0) {
			formula = (CostFormula) CommonUtil.getObject(id);
		    } else {
			formula = CostFormula.newCostFormula();
			formula.setFormulaVersion(formulaVersion);
		    }

		    if (parent != null && parent.indexOf(CostFormula.class.getName()) >= 0) {
			pFormula = (CostFormula) CommonUtil.getObject(parent);
		    } else {
			pFormula = savePartList.get(parent);
		    }

		    if (deleted && PersistenceHelper.isPersistent(formula)) {
			itemIds.remove(id);
			CostCacheManager.removeCostFByKey(id);
			PersistenceHelper.manager.delete(formula);
			continue;
		    }

		    if (pFormula != null) {
			formula.setParent(pFormula);
		    } else if (added || moved) {
			formula.setParent(CostFormula.newCostFormula());
		    }

		    formula.setSortLocation(itemIds.indexOf(id));
		    CostUtil.manager.convertMapToObject(data, formula);

		    formula = (CostFormula) PersistenceHelper.manager.save(formula);
		    savePartList.put(id, formula);

		    if (formulaKeys.indexOf("TX") >= 0) {
			updateList.put(CommonUtil.getOIDString(formula), formulaKeys);
		    }
		}

		// 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		for (int i = 0; i < itemIds.size(); i++) {
		    String itemId = itemIds.get(i);

		    if (savePartList.containsKey(itemId)) {

			if (itemId.indexOf("TX") >= 0) {
			    CostFormula formula = savePartList.get(itemId);

			    Set<String> st = updateList.keySet();
			    Iterator<String> it = st.iterator();

			    while (it.hasNext()) {
				String oid = it.next();
				String formulaKeys = updateList.get(oid);

				if (formulaKeys.indexOf("TX") >= 0) {
				    formulaKeys = formulaKeys.replaceAll(itemId, CommonUtil.getOIDString(formula));
				    updateList.put(oid, formulaKeys);
				}
			    }
			}
			continue;
		    }

		    CostFormula formula = (CostFormula) CommonUtil.getObject(itemId);

		    if (i == formula.getSortLocation())
			continue;

		    formula.setSortLocation(i);

		    PersistenceHelper.manager.save(formula);
		}

		resMap.put("updateList", updateList);
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

	return resMap;
    }

    @SuppressWarnings("unchecked")
    private void getItemsOidSetting(List<Map<String, Object>> items, List<String> list) throws Exception {

	for (Map<String, Object> item : items) {
	    List<Map<String, Object>> childItems = (List<Map<String, Object>>) item.get("Items");
	    list.add((String) item.get("id"));
	    if (childItems != null) {
		getItemsOidSetting(childItems, list);
	    }
	}
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 1. 31. 오후 1:39:49
     * @method getCostRootMapList
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public List<Map<String, Object>> getCostRootMapList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	List<Persistable> plist = getCostRootList(reqMap);

	for (Persistable obj : plist) {
	    Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(obj);
	    dataMap.put("id", CommonUtil.getOIDString(obj));
	    list.add(dataMap);
	}

	return list;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 1. 31. 오후 1:36:12
     * @method getCostRootList
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public List<Persistable> getCostRootList(Map<String, Object> reqMap) throws Exception {

	String dataType = (String) reqMap.get("DATATYPE");

	List<Persistable> rootList = new ArrayList<Persistable>();

	QueryResult qr = null;

	if ("COSTPART".equals(dataType)) {
	    qr = getCostProductQuery(reqMap);

	} else if ("COSTFORMULA".equals(dataType)) {
	    qr = getCostFormulaQuery(reqMap);

	    if (qr.size() == 0) {
		CostFormula formula = CostFormula.newCostFormula();
		formula.setName("총원가");
		formula.setDeptType((String) reqMap.get("deptType"));
		formula.setSortLocation(0);
		formula.setMappingCode("");
		formula = (CostFormula) PersistenceHelper.manager.save(formula);
		rootList.add(formula);
	    }
	} else if ("COSTPARTTYPE".equals(dataType)) {
	    qr = getCostPartTypeQuery(reqMap);
	}

	if (qr != null) {

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		Persistable obj = (Persistable) o[0];
		rootList.add(obj);
	    }
	}
	return rootList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 8. 오후 2:45:12
     * @method getCostFormulaQuery
     * @param reqMap
     * @return QueryResult
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    private QueryResult getCostFormulaQuery(Map<String, Object> reqMap) throws Exception {

	String deptType = (String) reqMap.get("deptType");
	String version = (String) reqMap.get("version");

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(CostFormula.class, true);

	if (StringUtil.checkString(deptType)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostFormula.class, CostFormula.DEPT_TYPE, SearchCondition.EQUAL, deptType),
		    new int[] { idx });
	}

	if (StringUtil.checkString(version)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(CostFormula.class, CostFormula.FORMULA_VERSION, SearchCondition.EQUAL, Integer.parseInt(version)),
		    new int[] { idx });
	}

	SearchUtil.setOrderBy(qs, CostFormula.class, idx, CostFormula.SORT_LOCATION, false);
	System.out.println("costTreeQuery : " + qs);
	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2019. 5. 7. 오후 4:24:47
     * @method getCostProductList
     * @param master
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<CostPart> getCostProductList(E3PSProjectMaster master) throws Exception {

	List<CostPart> list = new ArrayList<CostPart>();

	PartList partList = CostUtil.manager.getPartList(master);

	if (partList == null) {
	    return list;
	}

	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(CostPart.class, true);
	int idx2 = qs.appendClassList(ProductMaster.class, false);
	qs.setAdvancedQueryEnabled(true);

	ClassAttribute ca0 = new ClassAttribute(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	ClassAttribute ca1 = new ClassAttribute(ProductMaster.class, WTAttributeNameIfc.ID_NAME);
	SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	sc0.setOuterJoin(0);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(sc0, new int[] { idx, idx2 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(ProductMaster.class, ProductMaster.PART_LIST_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(partList)), new int[] { idx2 });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(
	        new SearchCondition(CostPart.class, CostPart.VERSION, SearchCondition.EQUAL, CostUtil.manager.getLastestReportVersion(
	                master.getPjtNo(), true)), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });

	SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SORT_LOCATION, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPart obj = (CostPart) o[0];
	    list.add(obj);
	}

	return list;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 8. 오후 2:44:53
     * @method getCostProductQuery
     * @param reqMap
     * @return QueryResult
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    private QueryResult getCostProductQuery(Map<String, Object> reqMap) throws Exception {

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();

	String taskOid = (String) reqMap.get("taskOid");
	String oid = (String) reqMap.get("oid");
	String partListOid = (String) reqMap.get("partListOid");
	String masterOid = (String) reqMap.get("masterOid");
	String pjtMasterOid = (String) reqMap.get("pjtMasterOid");

	String version = (String) reqMap.get("version");
	String subVersion = (String) reqMap.get("subVersion");
	String lastest = (String) reqMap.get("lastest");
	String caseOrder = (String) reqMap.get("caseOrder");
	String orderByPartNo = (String) reqMap.get("orderByPartNo");
	String orderByVersionDesc = (String) reqMap.get("orderByVersionDesc");
	String noSortOption = (String) reqMap.get("noSortOption");// 보고서 sort용

	if (StringUtil.checkString(taskOid)) {
	    E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	    E3PSProject project = (E3PSProject) task.getProject();
	    E3PSProjectMaster pjtMatser = (E3PSProjectMaster) project.getMaster();

	    pjtMasterOid = CommonUtil.getOIDString(pjtMatser);
	    version = task.getCostVersion();

	    if (project instanceof ReviewProject) {
		PartList partList = CostUtil.manager.getPartList(pjtMatser, true);
		partListOid = CommonUtil.getOIDString(partList);
	    } else {
		PartList partList = CostUtil.manager.getPartList(pjtMatser, false);
		if (partList == null) {
		    return qr;
		} else {
		    partListOid = CommonUtil.getOIDString(partList);
		}
	    }
	}

	int idx = qs.appendClassList(CostPart.class, true);
	if (StringUtil.checkString(partListOid)) {

	    int idx2 = qs.appendClassList(ProductMaster.class, false);
	    qs.setAdvancedQueryEnabled(true);

	    ClassAttribute ca0 = new ClassAttribute(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	    ClassAttribute ca1 = new ClassAttribute(ProductMaster.class, WTAttributeNameIfc.ID_NAME);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx2 });

	    String[] partListOidString = partListOid.split(",");

	    if (partListOidString.length > 1) {

		List<Long> partListOids = new ArrayList<Long>();

		for (String OID : partListOidString) {
		    partListOids.add(CommonUtil.getOIDLongValue(OID));
		}

		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(new ClassAttribute(ProductMaster.class, ProductMaster.PART_LIST_REFERENCE + "."
		        + WTAttributeNameIfc.REF_OBJECT_ID), SearchCondition.IN, new ArrayExpression(partListOids.toArray())),
		        new int[] { idx2 });

	    } else {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductMaster.class, ProductMaster.PART_LIST_REFERENCE + "."
		        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(partListOid)),
		        new int[] { idx2 });
	    }

	}

	if (StringUtil.checkString(oid)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(
		    new SearchCondition(CostPart.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(oid)),
		    new int[] { idx });
	}

	if (StringUtil.checkString(masterOid)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(masterOid)), new int[] { idx });
	}

	if (StringUtil.checkString(pjtMasterOid)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pjtMasterOid)), new int[] { idx });
	}

	if (StringUtil.checkString(version)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.VERSION, SearchCondition.EQUAL, Integer.parseInt(version)),
		    new int[] { idx });
	}

	if (StringUtil.checkString(subVersion)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.SUB_VERSION, SearchCondition.EQUAL, Integer.parseInt(subVersion)),
		    new int[] { idx });
	}

	if (StringUtil.checkString(lastest)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, Boolean.parseBoolean(lastest)),
		    new int[] { idx });
	}

	if (StringUtil.checkString(caseOrder)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.CASE_ORDER, SearchCondition.EQUAL, Integer.parseInt(caseOrder)),
		    new int[] { idx });
	}

	if (StringUtils.isNotEmpty(noSortOption)) {
	    SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.CASE_ORDER, false);
	    SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SORT_LOCATION, false);
	} else {
	    if (StringUtils.isNotEmpty(orderByPartNo)) {
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.PART_NO, false);
	    }
	    if (StringUtils.isNotEmpty(orderByVersionDesc)) {
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.VERSION, true);
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SUB_VERSION, true);
	    } else {
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.VERSION, false);
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SUB_VERSION, false);
		SearchUtil.setOrderBy(qs, CostPart.class, idx, CostPart.SORT_LOCATION, false);
	    }
	}

	// LOGGER.info("qs === " + qs);
	System.out.println("qs === " + qs);
	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : getCostPartTypeQuery
     * @작성자 : 황정
     * @작성일 : 2017. 12. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("deprecation")
    private QueryResult getCostPartTypeQuery(Map<String, Object> reqMap) throws Exception {

	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(CostPartType.class, true);

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPartType.class, CostPartType.PARENT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, Long.parseLong("0")), new int[] { idx });

	SearchUtil.setOrderBy(qs, CostPartType.class, idx, CostPartType.SORT_LOCATION, false);

	qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostPartType(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();

	Transaction trx = new Transaction();
	ObjectMapper mapper = new ObjectMapper();
	// String partListOid = (String) reqMap.get("partListOid");
	String treeDataStr = (String) reqMap.get("treeData");
	try {

	    trx.start();
	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    if (treeData != null) {

		// PartList partList = (PartList) CommonUtil.getObject(partListOid);

		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");
		List<Map<String, Object>> body = (List<Map<String, Object>>) treeData.get("Body");
		List<Map<String, Object>> items = (List<Map<String, Object>>) body.get(0).get("Items");

		List<String> itemIds = new ArrayList<String>();
		getItemsOidSetting(items, itemIds);
		Map<String, CostPartType> savePartList = new HashMap<String, CostPartType>();

		for (Map<String, Object> data : changes) {

		    boolean added = false;
		    // boolean changed = false;
		    boolean deleted = false;
		    boolean moved = false;

		    if (data.get("Added") != null)
			added = true;
		    // if(data.get("Changed") != null) changed = true;
		    if (data.get("Deleted") != null)
			deleted = true;
		    if (data.get("Moved") != null)
			moved = true;

		    String id = (String) data.get("id");
		    String parent = (String) data.get("Parent");

		    CostPartType costPartType = null;
		    CostPartType pCostPartType = null;

		    if (id.indexOf(CostPartType.class.getName()) >= 0) {
			costPartType = (CostPartType) CommonUtil.getObject(id);
		    } else {
			costPartType = CostPartType.newCostPartType();
		    }

		    if (parent != null && parent.indexOf(CostPartType.class.getName()) >= 0) {
			pCostPartType = (CostPartType) CommonUtil.getObject(parent);
		    } else {
			pCostPartType = savePartList.get(parent);
		    }

		    if (deleted && PersistenceHelper.isPersistent(costPartType)) {

			itemIds.remove(id);
			CostCacheManager.removeCostPTByKey(CommonUtil.getOIDLongValue2Str(id));
			PersistenceHelper.manager.delete(costPartType);
			continue;
		    }

		    if (pCostPartType != null) {

			costPartType.setParent(pCostPartType);

		    } else if (added || moved) {

			costPartType.setParent(CostPartType.newCostPartType());
		    }

		    costPartType.setSortLocation(itemIds.indexOf(id));

		    Boolean capaCfg = Boolean.parseBoolean((String) data.get("capaCfg"));

		    data.put("capaCfg", capaCfg);

		    CostUtil.manager.convertMapToObject(data, costPartType);

		    String lvlOption = (String) data.get("lvlOption");

		    if (StringUtils.contains(lvlOption, "gt")) {
			if (StringUtils.contains(lvlOption, "=")) {
			    lvlOption = ">=";
			} else {
			    lvlOption = ">";
			}
		    } else if (StringUtils.contains(lvlOption, "lt")) {
			if (StringUtils.contains(lvlOption, "=")) {
			    lvlOption = "<=";
			} else {
			    lvlOption = "<";
			}
		    }
		    costPartType.setLvlOption(lvlOption);
		    costPartType = (CostPartType) PersistenceHelper.manager.save(costPartType);
		    savePartList.put(id, costPartType);

		}

		// 전체 소팅 로직 (ROW INDEX 가져오는 방법이 없어서 이방식을 사용)
		for (int i = 0; i < itemIds.size(); i++) {
		    String itemId = itemIds.get(i);

		    if (savePartList.containsKey(itemId))
			continue;
		    CostPartType costPartType = (CostPartType) CommonUtil.getObject(itemId);
		    if (i == costPartType.getSortLocation())
			continue;
		    costPartType.setSortLocation(i);
		    PersistenceHelper.manager.save(costPartType);
		}
	    }

	    trx.commit();
	    trx = null;

	    loadCostPartTypeInfo();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return resMap;
    }

    @Override
    public List<Map<String, Object>> getCostPartTypeTreeList() throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> partTypeList = new ArrayList<Map<String, Object>>();

	try {

	    StringBuffer sb = new StringBuffer();

	    sb.append("SELECT LEVEL LEV     \n");
	    sb.append(", C.NAME AS NAME  \n");
	    sb.append(", C.CODE AS CODE  \n");
	    sb.append(", C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID  \n");
	    sb.append(", C.IDA2A2 OID2  \n");
	    sb.append(", DECODE( C.IDA3PARENTREFERENCE, 0, '', C.CLASSNAMEA2A2 || ':' || C.IDA3PARENTREFERENCE ) PARENTOID    \n");
	    sb.append(", CONNECT_BY_ISLEAF AS LEAF \n");
	    sb.append(", LTRIM (SYS_CONNECT_BY_PATH (NAME, ' / '), ' / ') AS TREEFULLPATH \n");
	    sb.append(", LVL AS LVL \n");
	    sb.append(", LVLOPTION AS LVLOPTION \n");
	    sb.append(", CHILDCFG AS CHILDCFG \n");
	    sb.append(", PARENTCFG AS PARENTCFG \n");
	    sb.append("  FROM COSTPARTTYPE C \n");
	    sb.append("  WHERE 1 = 1    \n");
	    sb.append("  START WITH IDA3PARENTREFERENCE = 0 \n");
	    sb.append("  CONNECT BY PRIOR IDA2A2 = IDA3PARENTREFERENCE \n");
	    sb.append("  ORDER SIBLINGS BY SORTLOCATION \n");

	    String query = sb.toString();

	    partTypeList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> costPartTypeVO = new HashMap<String, Object>();

		    costPartTypeVO.put("LEV", StringUtil.checkNull(rs.getString("LEV")));
		    costPartTypeVO.put("NAME", StringUtil.checkNull(rs.getString("NAME")));
		    costPartTypeVO.put("CODE", StringUtil.checkNull(rs.getString("CODE")));
		    costPartTypeVO.put("OID", StringUtil.checkNull(rs.getString("OID")));
		    costPartTypeVO.put("OID2", StringUtil.checkNull(rs.getString("OID2")));
		    costPartTypeVO.put("PARENTOID", StringUtil.checkNull(rs.getString("PARENTOID")));
		    costPartTypeVO.put("LEAF", StringUtil.checkNull(rs.getString("LEAF")));
		    costPartTypeVO.put("TREEFULLPATH", StringUtil.checkNull(rs.getString("TREEFULLPATH")));

		    costPartTypeVO.put("LVL", StringUtil.checkNull(rs.getString("LVL")));
		    costPartTypeVO.put("LVLOPTION", StringUtil.checkNull(rs.getString("LVLOPTION")));
		    costPartTypeVO.put("CHILDCFG", StringUtil.checkNull(rs.getString("CHILDCFG")));
		    costPartTypeVO.put("PARENTCFG", StringUtil.checkNull(rs.getString("PARENTCFG")));

		    return costPartTypeVO;
		}
	    });
	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return partTypeList;
    }

    /**
     * <pre>
     * @description 원가기준정보 저장
     * @author dhkim
     * @date 2018. 1. 3. 오전 10:56:46
     * @method saveCSInfo
     * @param reqMap
     * @return
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCSInfo(Map<String, Object> reqMap) throws Exception {

	String infoType = (String) reqMap.get("infoType");

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();
	try {

	    trx.start();
	    resMap.put("message", "저장되었습니다.");

	    CSInfo csInfo = CostUtil.manager.getLatestCSInfo(infoType);
	    List<CSInfoItemDTO> deleteList = CostUtil.manager.getCSInfoItemList(csInfo);

	    for (CSInfoItemDTO data : deleteList) {
		PersistenceHelper.manager.delete(data.getObject());
	    }

	    if (csInfo == null) {
		csInfo = CSInfo.newCSInfo();
	    }

	    CostUtil.manager.convertMapToObject(reqMap, csInfo);
	    csInfo = (CSInfo) PersistenceHelper.manager.save(csInfo);

	    List<Map<String, Object>> stdInfo1 = (List<Map<String, Object>>) reqMap.get("stdInfo1");
	    List<Map<String, Object>> stdInfo2 = (List<Map<String, Object>>) reqMap.get("stdInfo2");
	    List<Map<String, Object>> stdInfo3 = (List<Map<String, Object>>) reqMap.get("stdInfo3");
	    List<Map<String, Object>> stdInfo4 = (List<Map<String, Object>>) reqMap.get("stdInfo4");
	    List<Map<String, Object>> stdInfo5 = (List<Map<String, Object>>) reqMap.get("stdInfo5");
	    List<Map<String, Object>> stdInfo = new ArrayList<Map<String, Object>>();

	    int sort = 0;

	    if (stdInfo1 != null)
		stdInfo.addAll(stdInfo1);
	    if (stdInfo2 != null)
		stdInfo.addAll(stdInfo2);
	    if (stdInfo3 != null)
		stdInfo.addAll(stdInfo3);
	    if (stdInfo4 != null)
		stdInfo.addAll(stdInfo4);
	    if (stdInfo5 != null)
		stdInfo.addAll(stdInfo5);

	    for (Map<String, Object> infoItem : stdInfo) {

		CSInfoItem csiItem = CSInfoItem.newCSInfoItem();
		CostUtil.manager.convertMapToObject(infoItem, csiItem);

		csiItem.setSort(sort++);
		csiItem.setCSInfo(csInfo);

		PersistenceHelper.manager.save(csiItem);

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

	return resMap;
    }

    /**
     * <pre>
     * @description 원가기준정보 개정
     * @author dhkim
     * @date 2018. 1. 5. 오전 10:34:09
     * @method reviseCSInfo
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> reviseCSInfo(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();

	try {

	    trx.start();
	    resMap.put("message", "개정되었습니다.");

	    CSInfo csInfo = (CSInfo) CommonUtil.getObject(oid);

	    List<CSInfoItemDTO> dataList = CostUtil.manager.getCSInfoItemList(csInfo);

	    int version = csInfo.getVersion() + 1;

	    csInfo = (CSInfo) HistoryHelper.duplicate(csInfo);
	    csInfo.setVersion(version);
	    csInfo = (CSInfo) PersistenceHelper.manager.save(csInfo);

	    resMap.put("oid", CommonUtil.getOIDString(csInfo));

	    for (CSInfoItemDTO data : dataList) {
		CSInfoItem item = (CSInfoItem) HistoryHelper.duplicate(data.getObject());
		item.setCSInfo(csInfo);
		PersistenceHelper.manager.save(item);
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

	return resMap;
    }

    /**
     * <pre>
     * @description 원가기준정보 삭제
     * @author dhkim
     * @date 2018. 1. 5. 오전 10:34:00
     * @method deleteCSInfo
     * @param csInfo
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public Map<String, Object> deleteCSInfo(CSInfo csInfo) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();
	try {

	    trx.start();

	    List<CSInfoItemDTO> deleteList = CostUtil.manager.getCSInfoItemList(csInfo);

	    for (CSInfoItemDTO data : deleteList) {
		PersistenceHelper.manager.delete(data.getObject());
	    }

	    PersistenceHelper.manager.delete(csInfo);

	    resMap.put("message", "삭제되었습니다.");

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
	return resMap;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 2. 7. 오전 10:57:08
     * @method saveCostAnalysis
     * @param part
     * @return
     * @throws Exception
     * </pre>
     */
    @Override
    public List<CostAnalysis> saveCostAnalysis(CostPart part) throws Exception {

	List<CostAnalysis> caList = new ArrayList<CostAnalysis>();
	List<CostQuantity> qtyList = CostCodeHelper.service.getLastestCostQuantity(part);

	String salesTargetCostExpr = part.getSalesTargetCostExpr();
	String productCostTotal = part.getProductCostTotal();
	String reduceCostTotal = part.getReduceCostTotal();
	String productNInvestTotal = part.getProductNInvestTotal();
	String appointTotal = part.getSubCostExceptTotal();
	String appointSales = "0";
	String cr = "0";
	int applyYear = 0;

	String correctPeriod = "0";
	boolean isCorrectCalc = false;

	CostAnalysis caFirst = CostUtil.manager.getCostAnalysis(part, "1");

	if (caFirst != null) {

	    appointSales = caFirst.getAppointSales();
	    cr = caFirst.getCr();
	    applyYear = Integer.parseInt(caFirst.getApplyYear());

	    // CASE 0일 경우 초기값 설정
	    if (part.getSubVersion() == 0) {
		appointTotal = appointSales;
		cr = part.getDisposableCr();
		applyYear = Integer.parseInt(StringUtil.checkReplaceStr(part.getDisposableApplyYear(), "0"));
	    }
	}

	for (int i = 0; i < qtyList.size(); i++) {

	    CostQuantity qty = qtyList.get(i);

	    String year = qty.getYear();
	    String quantity = qty.getQuantity();

	    CostAnalysis ca = CostUtil.manager.getCostAnalysis(part, year);

	    if (ca == null) {
		ca = CostAnalysis.newCostAnalysis();
		ca.setYear(Integer.parseInt(year));
		ca.setCostPart(part);
	    }

	    if (i > 0 && applyYear >= i) {
		String expression = salesTargetCostExpr + "-" + appointSales;
		salesTargetCostExpr = (String) CostCalculateUtil.manager.eval(expression);

		expression = salesTargetCostExpr + "-((" + cr + "/100)*" + salesTargetCostExpr + ")";
		salesTargetCostExpr = (String) CostCalculateUtil.manager.eval(expression);

		expression = salesTargetCostExpr + "+" + appointSales;
		salesTargetCostExpr = (String) CostCalculateUtil.manager.eval(expression);
	    }

	    String expression = salesTargetCostExpr + "*" + quantity;
	    String totalSales = (String) CostCalculateUtil.manager.eval(expression);

	    expression = "+" + quantity + "*(" + salesTargetCostExpr + "-" + productCostTotal + ")";
	    String profitCost = (String) CostCalculateUtil.manager.eval(expression);

	    expression = "+" + quantity + "*" + reduceCostTotal + "+" + profitCost;
	    String cashInFlow = (String) CostCalculateUtil.manager.eval(expression);

	    expression = "+" + profitCost + "/" + totalSales;
	    String profitRateTotal = (String) CostCalculateUtil.manager.eval(expression);

	    if (!isCorrectCalc) {

		expression = productNInvestTotal + "-" + cashInFlow;
		String balance = (String) CostCalculateUtil.manager.eval(expression);

		expression = balance + "<0";
		boolean isCorrect = (boolean) CostCalculateUtil.engine.eval(expression);

		if (isCorrect) {
		    expression = "(" + year + "-1)+" + productNInvestTotal + "/" + cashInFlow;
		    correctPeriod = (String) CostCalculateUtil.manager.eval(expression);
		    isCorrectCalc = true;
		} else {
		    productNInvestTotal = balance;
		}
	    }

	    ca.setSalesTargetCostTotal(salesTargetCostExpr);
	    ca.setProductCostTotal(productCostTotal);
	    ca.setQuantity(quantity);
	    ca.setTotalSales(totalSales);
	    ca.setProfitCost(profitCost);
	    ca.setCashInFlow(cashInFlow);
	    ca.setProfitRateTotal(profitRateTotal);
	    ca.setAppointTotal(appointTotal);
	    ca.setApplyYear(String.valueOf(applyYear));
	    ca.setCr(cr);

	    caList.add(ca);
	}

	for (int i = 0; i < caList.size(); i++) {

	    CostAnalysis ca = caList.get(i);

	    if (!isCorrectCalc) {
		ca.setCorrectPeriod("회수불가");
	    } else {
		ca.setCorrectPeriod(correctPeriod);
	    }

	    ca = (CostAnalysis) PersistenceHelper.manager.save(ca);

	    caList.set(i, ca);
	}

	return caList;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 4. 18. 오후 1:59:50
     * @method saveCostAnalysisInfo
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveCostAnalysisInfo(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Transaction trx = new Transaction();

	try {

	    trx.start();

	    List<CostAnalysis> caObjList = new ArrayList<CostAnalysis>();
	    List<Map<String, Object>> caList = new ArrayList<Map<String, Object>>();
	    CostPart part = (CostPart) CommonUtil.getObject((String) reqMap.get("oid"));
	    boolean isSave = (boolean) reqMap.get("isSave");

	    String salesTargetCostTotal = part.getSalesTargetCostExpr();
	    String reduceCostTotal = part.getReduceCostTotal();
	    String productCostTotal = part.getProductCostTotal();
	    String productNInvestTotal = part.getProductNInvestTotal();
	    String appointTotal = part.getSubCostExceptTotal();

	    List<String> caOidList = (List<String>) reqMap.get("caOidList");
	    String appointSales = StringUtil.checkReplaceStr((String) reqMap.get("appointSales"), "0");
	    String cr = StringUtil.checkReplaceStr((String) reqMap.get("cr"), "0");
	    int applyYear = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("applyYear"), "0"));

	    String correctPeriod = "0";
	    boolean isCorrectCalc = false;

	    for (String caOid : caOidList) {

		CostAnalysis ca = (CostAnalysis) CommonUtil.getObject(caOid);

		String quantity = ca.getQuantity();
		int year = ca.getYear();

		if (year > 1 && (applyYear + 1) >= year) {

		    String expression = salesTargetCostTotal + "-" + appointSales;
		    salesTargetCostTotal = (String) CostCalculateUtil.manager.eval(expression);

		    expression = salesTargetCostTotal + "-((" + cr + "/100)*" + salesTargetCostTotal + ")";
		    salesTargetCostTotal = (String) CostCalculateUtil.manager.eval(expression);

		    expression = salesTargetCostTotal + "+" + appointSales;
		    salesTargetCostTotal = (String) CostCalculateUtil.manager.eval(expression);

		}

		String expression = salesTargetCostTotal + "*" + quantity;
		String totalSales = (String) CostCalculateUtil.manager.eval(expression);

		expression = "+" + quantity + "*(" + salesTargetCostTotal + "-" + productCostTotal + ")";
		String profitCost = (String) CostCalculateUtil.manager.eval(expression);

		expression = "+" + quantity + "*" + reduceCostTotal + "+" + profitCost;
		String cashInFlow = (String) CostCalculateUtil.manager.eval(expression);

		expression = "+" + profitCost + "/" + totalSales;
		String profitRateTotal = (String) CostCalculateUtil.manager.eval(expression);

		if (!isCorrectCalc) {

		    expression = productNInvestTotal + "-" + cashInFlow;

		    String balance = (String) CostCalculateUtil.manager.eval(expression);

		    expression = balance + "<0";
		    boolean isCorrect = (boolean) CostCalculateUtil.engine.eval(expression);

		    if (isCorrect) {
			expression = "(" + year + "-1)+" + productNInvestTotal + "/" + cashInFlow;
			correctPeriod = (String) CostCalculateUtil.manager.eval(expression);
			isCorrectCalc = true;
		    } else {
			productNInvestTotal = balance;
		    }
		}

		ca.setAppointSales(appointSales);
		if (part.getSubVersion() == 0)
		    ca.setAppointTotal(appointSales);
		else
		    ca.setAppointTotal(appointTotal);
		ca.setCr(cr);
		ca.setApplyYear(String.valueOf(applyYear));
		ca.setSalesTargetCostTotal(salesTargetCostTotal);
		ca.setProductCostTotal(productCostTotal);
		ca.setQuantity(quantity);
		ca.setTotalSales(totalSales);
		ca.setProfitCost(profitCost);
		ca.setCashInFlow(cashInFlow);
		ca.setProfitRateTotal(profitRateTotal);

		caObjList.add(ca);

	    }

	    for (CostAnalysis ca : caObjList) {

		if (!isCorrectCalc) {
		    ca.setCorrectPeriod("회수불가");
		} else {
		    ca.setCorrectPeriod(correctPeriod);
		}

		if (isSave) {
		    ca = (CostAnalysis) PersistenceHelper.manager.save(ca);
		}

		Map<String, Object> caMap = CostUtil.manager.converObjectToMap(ca);
		caMap.put("id", CommonUtil.getOIDString(ca));

		caList.add(caMap);
	    }

	    resMap.put("caList", caList);
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

	return resMap;
    }

    @Override
    public void reCalcReduceCost(Map<String, Object> reqMap) throws Exception {

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    List<CostPart> partList = CostTreeUtil.manager.getCostPartList(reqMap);

	    for (CostPart part : partList) {

		QueryResult qr = PersistenceHelper.manager.navigate(part, "reduce", CostReduceLink.class, false);

		while (qr.hasMoreElements()) {
		    CostReduceLink link = (CostReduceLink) qr.nextElement();
		    CostInvestInfo obj = link.getReduce();

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(obj);
		}
	    }

	    this.reCalculateCostPart(reqMap);

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

}
