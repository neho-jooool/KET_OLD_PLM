package ext.ket.project.atft.migration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleManaged;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.code.NumberCode;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.WCUtil;
import e3ps.edm.util.VersionHelper;
import e3ps.project.ProductProject;
import ext.ket.project.atft.entity.KETATFTMainSheet;
import ext.ket.project.atft.entity.KETATFTSheetLink;
import ext.ket.project.atft.entity.KETATFTSheetTemplate;
import ext.ket.project.atft.entity.KETProjectATFTSheetLink;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

//windchill ext.ket.project.atft.migration.atftDataLoad A12B051,A12B052,M11B001,M08B001,A14T007 C
//실앻시 ,를 구분자로 프로젝트번호를 파라미터로, 두번째 구분자는 C는 생성, D는 삭제
public class atftDataLoad implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */

    public static atftDataLoad manager = new atftDataLoad();

    public atftDataLoad() {

    }

    // windchill ext.ket.devloptest.SalesProjectDataLoad
    public static void main(String[] args) {

	try {

	    String pjtNo = null;
	    String gubun = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else {
		pjtNo = args[0];
		gubun = args[1];
	    }

	    Kogger.debug(atftDataLoad.class, "@start");
	    atftDataLoad.manager.saveFromExcel(pjtNo, gubun);
	    Kogger.debug(atftDataLoad.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(atftDataLoad.class, e);
	}
    }

    public void saveFromExcel(String pjtNo, String gubun) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { pjtNo, gubun };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(pjtNo, gubun);
	}
    }

    public void executeMigration(String pjtNo, String gubun) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    if ("C".equals(gubun)) {
		create(pjtNo);
	    }

	    if ("D".equals(gubun)) {
		delete();
	    }

	    if ("R".equals(gubun)) {
		revise();
	    }

	    if ("U".equals(gubun)) {
		update();
	    }

	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void delete() throws Exception {
	// String oid = "ext.ket.sales.entity.KETSalesProject:993211725";
	// KETSalesProject sproject = (KETSalesProject) CommonUtil.getObject(oid);

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETATFTMainSheet> KETATFTMainSheetList = query.queryForListByOneClass(KETATFTMainSheet.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		// TODO Auto-generated method stub

	    }
	});

	for (KETATFTMainSheet list : KETATFTMainSheetList) {
	    PersistenceHelper.manager.delete(list);
	}

    }

    public void revise() throws Exception {

	/*
	 * String oid = "ext.ket.sales.entity.KETSalesProject:993212320"; Versioned newVs = null; Versioned vs = (Versioned)
	 * CommonUtil.getObject(oid);
	 * 
	 * if (!VersionHelper.isLatestRevision(vs)) { throw new Exception("최신 버전이 아닙니다!"); } String lifecycle = ((LifeCycleManaged)
	 * vs).getLifeCycleName(); Folder folder = FolderHelper.service.getFolder((FolderEntry) vs); newVs =
	 * VersionControlHelper.service.newVersion(vs); FolderHelper.assignLocation((FolderEntry) newVs, folder); KETSalesProject sproject =
	 * (KETSalesProject) newVs;
	 * 
	 * // LifeCycleHelper.setLifeCycle(sproject, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));
	 * // // Lifecycle sproject = (KETSalesProject) PersistenceHelper.manager.save(sproject);
	 */
    }

    public void update() throws Exception {
	/*
	 * String oid = "ext.ket.sales.entity.KETSalesProject:993212320"; KETSalesProject sproject = (KETSalesProject)
	 * CommonUtil.getObject(oid);
	 * 
	 * sproject = (KETSalesProject) ObjectUtil.checkout(sproject); sproject.setProjectName("테스트당...");
	 * 
	 * PersistenceHelper.manager.save(sproject);
	 * 
	 * sproject = (KETSalesProject) ObjectUtil.checkin(sproject);
	 */
    }

    public void macroSheetTemplateSave(NumberCode num, String classification, String sortNo) throws Exception {
	KETATFTSheetTemplate sheetTemplate = null;

	sheetTemplate = sheetTemplate.newKETATFTSheetTemplate();
	sheetTemplate.setAtftCode(num);
	sheetTemplate.setClassification(classification);
	sheetTemplate.setSortNo(sortNo);

	PersistenceHelper.manager.save(sheetTemplate);
    }

    public void sheetTemplateAttributeCreate(NumberCode num, String attr) throws Exception {
	String attrs[] = attr.split(",");
	String attrInfo[] = null;
	for (int i = 0; i < attrs.length; i++) {
	    attrInfo = attrs[i].split(":");
	    this.macroSheetTemplateSave(num, attrInfo[0], attrInfo[1]);
	}
    }

    public void createSheetTemplate() throws Exception {

	/************************************************
	 * KETATFTSheetTemplate 등록 시작
	 *************************************************/

	String productAttribute = "치수:1,외관:2,기능/성능:3,신뢰성:4";
	String processAttribute = "사출(C/T):1,프레스(SPM):2,조립(T/T):3";
	String qualityAttribute = "Fool-Proof:1,공정불량:2,협력사:3,작업자/검사자:4,JIG준비\r\n(취출/조립/검사):5";
	String massAttribute = "개발BOM:1,양산BOM:2,판매가:3,구매가:4,임가공가:5,바코드사양:6,포장사양:7,생산처:8,ERP검사계획:9";

	QuerySpec select = new QuerySpec(NumberCode.class);
	select.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "ATFTCATEGORY"), new int[] { 0 });
	SearchUtil.setOrderBy(select, NumberCode.class, 0, "sorting", false);
	QueryResult result = PersistenceHelper.manager.find(select);
	NumberCode code = null;

	String attrTemp = "";

	while (result.hasMoreElements()) {

	    code = (NumberCode) result.nextElement();
	    if (StringUtils.contains(code.getName(), "제품")) {
		attrTemp = productAttribute;
	    } else if (StringUtils.contains(code.getName(), "공정조건")) {
		attrTemp = processAttribute;
	    } else if (StringUtils.contains(code.getName(), "품질확보")) {
		attrTemp = qualityAttribute;
	    } else if (StringUtils.contains(code.getName(), "양산")) {
		attrTemp = massAttribute;
	    }
	    this.sheetTemplateAttributeCreate(code, attrTemp);
	}

    }

    public QueryResult getProjectLastList(String pjtNo) throws Exception {

	QuerySpec query = new QuerySpec();

	int idx = query.appendClassList(ProductProject.class, true);
	SearchCondition sc = null;
	SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	query.appendWhere(sc0, new int[] { idx });
	query.appendAnd();
	// { "A12B051", "A12B052" };
	String[] pjtNos = pjtNo.split(",");
	query.appendWhere(new SearchCondition(ProductProject.class, "master>pjtNo", pjtNos, true, false), new int[] { idx });
	StatementSpec Lastquery = query;
	QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	return qr;
    }

    public KETATFTMainSheet createProjectSheet(ProductProject project, String sheetDivision) throws Exception {// KETATFTMainSheet 생성후 프로젝트와
	                                                                                                       // KETATFTMainSheet를
	                                                                                                       // 연결한다(KETProjectATFTSheetLink)

	String folderPath = "/Default/자동차사업부/초기유동/";

	KETATFTMainSheet atftSheet = null;
	KETProjectATFTSheetLink projectSheetLink = null;

	String tempDate = DateUtil.getDateString(new Date(), "all");

	String number = project.getPjtNo() + "-" + tempDate.substring(2, 4) + "-";
	number = number + ManageSequence.getSeqNo(number, "000", "WTDOCUMENTMASTER", "WTDOCUMENTNUMBER") + "-" + sheetDivision;

	atftSheet = KETATFTMainSheet.newKETATFTMainSheet();
	atftSheet.setName(project.getPjtNo());
	atftSheet.setNumber(number);
	atftSheet.setSheetDivision(sheetDivision);

	WTContainerRef containerRef = WCUtil.getWTContainerRef();

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) atftSheet, folder);

	atftSheet = (KETATFTMainSheet) PersistenceHelper.manager.save(atftSheet);

	projectSheetLink = KETProjectATFTSheetLink.newKETProjectATFTSheetLink(atftSheet, project);

	PersistenceHelper.manager.save(projectSheetLink);

	return atftSheet;
    }

    public void createSheetLink(List<KETATFTSheetTemplate> list, KETATFTMainSheet atftSheet) throws Exception {

	KETATFTSheetLink SheetLink = null;

	String desision = "OK";

	for (KETATFTSheetTemplate sheetTemplate : list) {
	    String attr = sheetTemplate.getAtftCode().getName() + " ＝ 판정근거 Test data : " + "[" + sheetTemplate.getClassification() + "] "
		    + RandomStringUtils.randomAlphanumeric(10);
	    SheetLink = KETATFTSheetLink.newKETATFTSheetLink(atftSheet, sheetTemplate);
	    SheetLink.setBasis(attr);
	    SheetLink.setDesision(desision);

	    SheetLink = (KETATFTSheetLink) PersistenceHelper.manager.save(SheetLink);

	    if (desision.equals("OK")) {
		desision = "NG";
	    } else {
		desision = "OK";
	    }
	}

    }

    public String pjtNoModify(String pjtNo) throws Exception {

	QuerySpec query = new QuerySpec(KETProjectATFTSheetLink.class);

	StatementSpec Lastquery = query;
	QueryResult qr = PersistenceHelper.manager.find(Lastquery);
	KETProjectATFTSheetLink sheetLink = null;
	String dupPjtNo = "";

	while (qr.hasMoreElements()) {
	    sheetLink = (KETProjectATFTSheetLink) qr.nextElement();
	    dupPjtNo += sheetLink.getProject().getPjtNo() + ",";
	}

	dupPjtNo = StringUtils.removeEnd(dupPjtNo, ",");

	String pjtNos[] = pjtNo.split(",");

	String targetPjtNos = "";

	for (int i = 0; i < pjtNos.length; i++) {

	    if (!StringUtils.contains(dupPjtNo, pjtNos[i])) {
		targetPjtNos += pjtNos[i] + ",";
	    }
	}
	if (StringUtils.isNotEmpty(targetPjtNos)) {
	    targetPjtNos = StringUtils.removeEnd(targetPjtNos, ",");
	}

	return targetPjtNos;
    }

    public void create(String pjtNo) throws Exception {

	String targetPjtNos = pjtNoModify(pjtNo);

	if (StringUtils.isNotEmpty(targetPjtNos)) {
	    System.out.println("targetPjtNos : "+targetPjtNos);
	    QuerySpec query = new QuerySpec(KETATFTSheetTemplate.class);

	    StatementSpec Lastquery = query;
	    QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	    QueryResult pjtqr = null;

	    if (qr.size() == 0) {// 최초 1회만 teamplate생성
		this.createSheetTemplate();
		qr = PersistenceHelper.manager.find(Lastquery);
	    }

	    KETATFTSheetTemplate sheetTemp = null;
	    List<KETATFTSheetTemplate> sheetTempateList = new ArrayList<KETATFTSheetTemplate>();

	    while (qr.hasMoreElements()) {
		sheetTemp = (KETATFTSheetTemplate) qr.nextElement();
		sheetTempateList.add(sheetTemp);
	    }

	    ProductProject project = null;

	    pjtqr = this.getProjectLastList(targetPjtNos);// 파라미터로 넘겨받은 ProjectLisk 가져오기

	    KETATFTMainSheet atftSheet = null;

	    while (pjtqr.hasMoreElements()) {
		Object[] po = (Object[]) pjtqr.nextElement();
		project = (ProductProject) po[0];

		atftSheet = this.createProjectSheet(project, "P1");// 프로젝트와 ATFT 연결
		this.createSheetLink(sheetTempateList, atftSheet);// 생성된 ATFT 에 대해 template의 attribute들을 연결해준다
		System.out.println("pjtno = " + project.getPjtNo());
		atftSheet = this.createProjectSheet(project, "P2");
		this.createSheetLink(sheetTempateList, atftSheet);
	    }
	}

    }
}
