package ext.ket.project.purchase.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProductProjectHelper;
import e3ps.project.beans.ProjectHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.project.purchase.entity.KETPurchaseProject;
import ext.ket.project.purchase.util.purchaseUtil;
import ext.ket.shared.util.EjsConverUtil;

public class StandardKETPurchaseService extends StandardManager implements KETPurchaseService {

    private static final long serialVersionUID = 1L;
    private purchaseUtil pcsUtil = new purchaseUtil();

    public static StandardKETPurchaseService newStandardKETPurchaseService() throws WTException {
	StandardKETPurchaseService instance = new StandardKETPurchaseService();
	instance.initialize();
	return instance;
    }

    @Override
    public Map<String, Object> addPartList(Map<String, Object> reqMap) throws Exception {

	String pjtNo = (String) reqMap.get("pjtNo");
	ProductProject pjt = (ProductProject) ProjectHelper.getProject(pjtNo);
	List<Map<String, Object>> addPartList = new ArrayList<Map<String, Object>>();

	if (pjt != null) {
	    QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo(pjt);

	    List<Map<String, Object>> dataList = null;
	    Map<String, Object> paramMap = new HashMap<String, Object>();

	    while (rt.hasMoreElements()) {

		Object[] obj = (Object[]) rt.nextElement();
		MoldItemInfo miInfo = (MoldItemInfo) obj[0];
		String shrinkage = miInfo.getShrinkage();
		if (StringUtils.isEmpty(shrinkage) || !"신규".equals(shrinkage)) {
		    continue;
		}
		String partNo = miInfo.getPartNo();
		paramMap.put("partNo", partNo);
		dataList = pcsUtil.getResultList(pcsUtil.getMainFindPagingListQuery(paramMap, false, false));
		if (dataList.size() > 0) {
		    continue;
		}
		String partName = miInfo.getPartName();
		String partType = "";
		if (miInfo.getProductType() != null) {
		    partType = miInfo.getProductType().getName();
		}
		String itemType = miInfo.getItemType();
		String dieNo = miInfo.getDieNo();

		String outSourcing = pcsUtil.getOutSourcingByPart(partNo, null, miInfo);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("CanDelete", "0");
		dataMap.put("CanSelect", "1");
		dataMap.put("partNo", partNo);
		dataMap.put("partName", partName);
		dataMap.put("partType", partType);
		dataMap.put("itemType", itemType);
		dataMap.put("dieNo", dieNo);
		dataMap.put("outSourcing", outSourcing);
		addPartList.add(dataMap);

	    }
	}
	return EjsConverUtil.convertToDto(addPartList);
    }

    @Override
    public void outSourcingSync(String partNo, WTPart part, MoldItemInfo miInfo) throws Exception {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("partNo", partNo);
	List<Map<String, Object>> dataList = pcsUtil.getResultList(pcsUtil.getMainFindPagingListQuery(paramMap, false, false));
	for (Map<String, Object> map : dataList) {
	    String oid = (String) map.get("OID");
	    KETPurchaseProject pjt = (KETPurchaseProject) CommonUtil.getObject(oid);
	    if (pjt != null) {
		String outSourcing = pcsUtil.getOutSourcingByPart(partNo, part, miInfo);
		pjt.setOutSourcing(outSourcing);
		PersistenceServerHelper.manager.update(pjt);
	    }
	}
    }

    @Override
    public Map<String, Object> findPagingList(Map<String, Object> reqMap) throws Exception {
	//
	int formPage = StringUtil.getIntParameter((String) reqMap.get("formPage"), PageControl.FORMPAGE);
	int currentPage = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("page"), "0"));
	int totalCount = 0;

	List<Map<String, Object>> dataList = null;

	String searchParam = (String) reqMap.get("searchParam");
	if ("main".equals(searchParam)) {

	    dataList = pcsUtil.getResultList(pcsUtil.getMainFindPagingListQuery(reqMap, true, false));

	    for (Map<String, Object> totalMap : dataList) {
		totalCount = Integer.parseInt((String) totalMap.get("TOTALCNT"));
		break;
	    }

	    dataList = getResultListForMain(pcsUtil.getMainFindPagingListQuery(reqMap, false, true));
	} else {
	    dataList = pcsUtil.getResultList(pcsUtil.getMainFindPagingListQuery(reqMap, false, false));
	    dataList = pcsUtil.getConvertList(dataList);
	}

	PageControl pageControl = new PageControl(currentPage + 1, dataList, formPage, formPage, totalCount);

	return EjsConverUtil.convertToDto(dataList, pageControl);

    }

    private List<Map<String, Object>> getResultListForMain(String sql) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = pcsUtil.mainQueryDataSetting(rs);

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

    @Override
    public Map<String, Object> savePurchasePart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub

	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String jsonDataStr = (String) reqMap.get("jsonData");

	Transaction transaction = new Transaction();

	String folderPath = "/Default/자동차사업부/초기유동/";
	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	Map<String, Object> paramMap = new HashMap<String, Object>();
	try {

	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();

	    transaction.start();

	    List<Map<String, Object>> jsonData = mapper.readValue(jsonDataStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    if (jsonData != null) {

		for (Map<String, Object> data : jsonData) {
		    boolean isNew = false;
		    String oid = (String) data.get("oid");
		    String addPart = (String) data.get("addPart");
		    WTUser pm = (WTUser) CommonUtil.getObject((String) data.get("managerOid"));
		    if (pm == null) {
			pm = user;
		    }
		    String pjtNo = (String) data.get("pjtNo");
		    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) ProjectHelper.getProject(pjtNo).getMaster();
		    Department userDept = DepartmentHelper.manager.getDepartment(pm);

		    String managerName = pm.getFullName();
		    String managerCode = pm.getName();
		    String managerDeptName = "";
		    String managerDeptCode = "";

		    if (userDept != null) {
			managerDeptName = userDept.getName();
			managerDeptCode = userDept.getCode();
		    }

		    KETPurchaseProject part = (KETPurchaseProject) CommonUtil.getObject(oid);

		    if (part == null) {
			isNew = true;
			part = KETPurchaseProject.newKETPurchaseProject();
			part.setOutSourcingGubun("기존");
			part.setCreateUser(user);
			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			FolderHelper.assignLocation((FolderEntry) part, folder);
		    }

		    if ("add".equals(addPart)) {
			paramMap.put("partNo", (String) data.get("partNo"));
			dataList = pcsUtil.getResultList(pcsUtil.getMainFindPagingListQuery(paramMap, false, false));
		    }

		    if (dataList.size() > 0) {
			continue;
		    }
		    boolean editAuth = Boolean.valueOf((String) data.get("editAuth"));
		    if (!"add".equals(addPart) && !editAuth) {
			continue;
		    }

		    ObjectUtil.manager.convertMapToObject(pcsUtil.dateMapConvert(data), part);

		    part.setModifyUser(user);
		    part.setPm(pm);
		    part.setDepartment(userDept);
		    part.setProject(pjtMaster);
		    part.setManagerName(managerName);
		    part.setManagerCode(managerCode);
		    part.setManagerDeptName(managerDeptName);
		    part.setManagerDeptCode(managerDeptCode);

		    if (isNew) {
			PersistenceHelper.manager.save(part);
		    } else {
			PersistenceServerHelper.manager.update(part);
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
    public void deletePurchasePart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	KETPurchaseProject purPart = (KETPurchaseProject) CommonUtil.getObject((String) reqMap.get("oid"));
	if (purPart == null) {
	    throw new Exception("객체 [" + (String) reqMap.get("oid") + "] 는 null 입니다");
	}
	PersistenceHelper.manager.delete(purPart);
    }

    @Override
    public Map<String, Object> findDocList(Map<String, Object> reqMap) throws Exception {
	return pcsUtil.getDocList(reqMap);
    }
}
