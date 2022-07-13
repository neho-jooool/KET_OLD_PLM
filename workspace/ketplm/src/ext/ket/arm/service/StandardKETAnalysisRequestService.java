package ext.ket.arm.service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.ui.Model;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisCustDeptLink;
import ext.ket.arm.entity.KETAnalysisDivisionLink;
import ext.ket.arm.entity.KETAnalysisMasterInfoLink;
import ext.ket.arm.entity.KETAnalysisProjectLink;
import ext.ket.arm.entity.KETAnalysisRequestDTO;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.SessionUtil;

public class StandardKETAnalysisRequestService extends StandardManager implements KETAnalysisRequestService {
    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : newStandardSampleService
     * @작성자 : hooni
     * @작성일 : 2014. 10. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardKETAnalysisRequestService newStandardKETAnalysisRequestService() throws WTException {
	StandardKETAnalysisRequestService instance = new StandardKETAnalysisRequestService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<KETAnalysisRequestMaster> find(KETAnalysisRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PageControl findPaging(KETAnalysisRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	QuerySpec query = getListQuery(paramObject);
	if (query.isAdvancedQuery()) {
	    if (!query.isAdvancedQueryEnabled())
		query.setAdvancedQueryEnabled(true);
	}
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
    public KETAnalysisRequestMaster save(KETAnalysisRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String folderPath = "/Default/자동차사업부/문서/해석의뢰문서/의뢰문서";

	KETAnalysisRequestMaster analysis = null;
	analysis = KETAnalysisRequestMaster.newKETAnalysisRequestMaster();

	LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_DRR_LC", WCUtil.getPDMLinkProduct()
	        .getContainerReference());
	LifeCycleHelper.setLifeCycle(analysis, LCtemplate);

	analysis.setName(paramObject.getAnalysisTitle());
	analysis.setNumber(getNewAnalysisNo(paramObject));
	// 해석의뢰 Numbering
	analysis.setAnalysisRequestNo(getNewAnalysisNo(paramObject));
	// 프로젝트 번호
	analysis.setProjectNo(paramObject.getProjectNo());
	// 해석의뢰 제목
	analysis.setAnalysisTitle(paramObject.getAnalysisTitle());
	// 작성자
	WTPrincipal principal = SessionHelper.manager.getPrincipal();
	WTUser clientUser = (WTUser) principal;
	analysis.setKetClientUser(clientUser);
	// 작성자 부서
	PeopleData clientPeople = new PeopleData(clientUser);
	analysis.setKetClientDepartment(clientPeople.department);
	// 프로젝트No
	analysis.setProjectNo(paramObject.getProjectNo());
	// 고객담당자
	analysis.setCustomerChargeName(paramObject.getCustomerChargeName());
	// 차종
	analysis.setCarType((OEMProjectType) CommonUtil.getObject(paramObject.getCarType()));
	// 개발단계
	analysis.setProcess((NumberCode) CommonUtil.getObject(paramObject.getProcess()));
	// 해석용도
	analysis.setUsage((NumberCode) CommonUtil.getObject(paramObject.getAnalysisUse()));
	// 해석의견
	analysis.setEtcComment(paramObject.getAnalysisComment());
	// 해석담당자
	// analysis.setKetChargeUser((WTUser) CommonUtil.getObject(paramObject.getKetChargeUser()));
	// 해석담당부서
	// PeopleData chargePeople = new PeopleData((WTUser) CommonUtil.getObject(paramObject.getKetChargeUser()));
	// analysis.setKetChargeDepartment(chargePeople.department);
	// 의뢰부품
	analysis.setPartNo(paramObject.getPartNo());
	// System.out.println("Project No ::: " + paramObject.getProjectNo());
	// System.out.println("해석의뢰 제목 ::: " + paramObject.getAnalysisTitle());
	// System.out.println("작성자 ::: " + clientUser);
	// System.out.println("작성부서 ::: " + clientPeople.department);
	// System.out.println("해석담당자 ::: " + (WTUser) CommonUtil.getObject(paramObject.getKetChargeUser()));
	// System.out.println("해석담당부서 ::: " + chargePeople.department);
	// System.out.println("고객담당자 ::: " + paramObject.getCustomerChargeName());
	// System.out.println("고객처 ::: " + paramObject.getCustomerDepartment());
	// System.out.println("차종 ::: " + (OEMProjectType) CommonUtil.getObject(paramObject.getCarType()));
	// System.out.println("개발단계 ::: " + paramObject.getProcess());
	// System.out.println("의뢰부품 ::: " + paramObject.getPartNo());
	// System.out.println("해석용도 ::: " + paramObject.getAnalysisUse());
	// System.out.println("해석구분 ::: " + paramObject.getAnalysisDivision());

	// System.out.println("제품구분 ::::" + paramObject.getProjectOid());

	// 해석의뢰목적
	analysis.setAnalysisObjectComment(paramObject.getAnalysisObjectComment());
	// 설계목표&설계기준
	analysis.setAnalysisTargetComment(paramObject.getAnalysisTargetComment());

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) analysis, folder);

	analysis = (KETAnalysisRequestMaster) PersistenceHelper.manager.save(analysis);
	analysis = (KETAnalysisRequestMaster) PersistenceHelper.manager.refresh(analysis);

	// 제품구분
	if (!"".equals(paramObject.getProjectOid())) {
	    E3PSProject project = null;
	    project = (E3PSProject) CommonUtil.getObject(paramObject.getProjectOid());
	    KETAnalysisProjectLink Alink = KETAnalysisProjectLink.newKETAnalysisProjectLink(project, analysis);
	    PersistenceHelper.manager.save(Alink);
	}

	// 고객처
	if (paramObject.getCustomerDepartment() != null) {
	    String[] cupt = paramObject.getCustomerDepartment().split(",");
	    NumberCode customerDepartment = null;
	    for (int i = 0; i < cupt.length; i++) {
		customerDepartment = (NumberCode) CommonUtil.getObject(cupt[i].toString());
		if (customerDepartment != null) {
		    KETAnalysisCustDeptLink plink = KETAnalysisCustDeptLink.newKETAnalysisCustDeptLink(customerDepartment, analysis);
		    PersistenceHelper.manager.save(plink);
		}
	    }
	}

	// 해석구분
	String[] divArr = paramObject.getAnalysisDivision().split(",");
	NumberCode divNum = null;
	for (int i = 0; i < divArr.length; i++) {
	    divNum = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + divArr[i].toString());
	    if (divNum != null) {
		KETAnalysisDivisionLink dlink = KETAnalysisDivisionLink.newKETAnalysisDivisionLink(divNum, analysis);
		PersistenceHelper.manager.save(dlink);
	    }
	}

	// 첨부파일 업로드
	KETContentHelper.service.updateContent(analysis, paramObject);

	return analysis;
    }

    @Override
    public KETAnalysisRequestDTO KETAnalysisRequestView(KETAnalysisRequestDTO paramObject, Model model) throws Exception {
	// TODO Auto-generated method stub
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getOid());
	paramObject = paramObject.KETAnalysisRequestDTO(paramObject, analysis);
	model.addAttribute("analysis", paramObject);
	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(analysis));
	return paramObject;
    }

    @Override
    public KETAnalysisRequestMaster modify(KETAnalysisRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub

	KETAnalysisRequestMaster analysis = null;
	Hashtable hash = null;

	analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getOid());

	// 제품구분 삭제
	QueryResult psc = PersistenceHelper.manager.navigate(analysis, KETAnalysisProjectLink.PROJECT_ROLE, KETAnalysisProjectLink.class,
	        false);
	if (psc.hasMoreElements()) {
	    while (psc.hasMoreElements()) {
		KETAnalysisProjectLink plink = (KETAnalysisProjectLink) psc.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}
	// 고객처 기존 삭제
	QueryResult qsc = PersistenceHelper.manager.navigate(analysis, KETAnalysisCustDeptLink.CUST_DEPT_ROLE,
	        KETAnalysisCustDeptLink.class, false);
	if (qsc.hasMoreElements()) {
	    while (qsc.hasMoreElements()) {
		KETAnalysisCustDeptLink plink = (KETAnalysisCustDeptLink) qsc.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}
	// 해석구분 기존 삭제
	QueryResult qsd = PersistenceHelper.manager.navigate(analysis, KETAnalysisDivisionLink.DIVISION_ROLE,
	        KETAnalysisDivisionLink.class, false);
	if (qsd.hasMoreElements()) {
	    while (qsd.hasMoreElements()) {
		KETAnalysisDivisionLink plink = (KETAnalysisDivisionLink) qsd.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	hash = KETObjectUtil.checkOut(analysis);

	if (hash != null) {
	    analysis = (KETAnalysisRequestMaster) hash.get("WorkableObject");
	}
	// 작성자
	WTPrincipal principal = SessionHelper.manager.getPrincipal();
	WTUser clientUser = (WTUser) principal;
	analysis.setKetClientUser(clientUser);
	// 작성자 부서
	PeopleData clientPeople = new PeopleData(clientUser);
	analysis.setKetClientDepartment(clientPeople.department);
	// 프로젝트No
	analysis.setProjectNo(paramObject.getProjectNo());
	// 고객담당자
	analysis.setCustomerChargeName(paramObject.getCustomerChargeName());
	// 차종
	analysis.setCarType((OEMProjectType) CommonUtil.getObject(paramObject.getCarType()));
	// 개발단계
	analysis.setProcess((NumberCode) CommonUtil.getObject(paramObject.getProcess()));
	// 해석용도
	analysis.setUsage((NumberCode) CommonUtil.getObject(paramObject.getAnalysisUse()));
	// 해석의견
	analysis.setEtcComment(paramObject.getAnalysisComment());

	// 의뢰부품
	analysis.setPartNo(paramObject.getPartNo());

	// 해석의뢰목적
	analysis.setAnalysisObjectComment(paramObject.getAnalysisObjectComment());
	// 설계목표&설계기준
	analysis.setAnalysisTargetComment(paramObject.getAnalysisTargetComment());

	analysis = (KETAnalysisRequestMaster) PersistenceHelper.manager.modify(analysis);
	KETObjectUtil.checkIn(analysis);
	analysis = (KETAnalysisRequestMaster) PersistenceHelper.manager.refresh(analysis);

	// 프로젝트저장
	if ("".equals(paramObject.getProjectNo()) || paramObject.getProjectNo() != null) {
	    E3PSProject project = null;

	    project = (E3PSProject) CommonUtil.getObject(paramObject.getProjectOid());
	    KETAnalysisProjectLink Alink = KETAnalysisProjectLink.newKETAnalysisProjectLink(project, analysis);
	    PersistenceHelper.manager.save(Alink);
	}

	// 고객처 등록
	String[] cupt = paramObject.getCustomerDepartment().split(",");
	NumberCode customerDepartment = null;
	for (int i = 0; i < cupt.length; i++) {
	    customerDepartment = (NumberCode) CommonUtil.getObject(cupt[i].toString());
	    if (customerDepartment != null) {
		KETAnalysisCustDeptLink plink = KETAnalysisCustDeptLink.newKETAnalysisCustDeptLink(customerDepartment, analysis);
		PersistenceHelper.manager.save(plink);
	    }
	}

	// 해석구분 등록
	String[] divArr = paramObject.getAnalysisDivision().split(",");
	NumberCode divNum = null;
	for (int i = 0; i < divArr.length; i++) {
	    divNum = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + divArr[i].toString());
	    if (divNum != null) {
		KETAnalysisDivisionLink dlink = KETAnalysisDivisionLink.newKETAnalysisDivisionLink(divNum, analysis);
		PersistenceHelper.manager.save(dlink);
	    }
	}

	// 첨부파일 업로드
	KETContentHelper.service.updateContent(analysis, paramObject);

	return analysis;
    }

    @Override
    public KETAnalysisRequestMaster delete(KETAnalysisRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETAnalysisRequestMaster analysis = null;
	analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getOid());

	// 제품구분 삭제
	QueryResult psc = PersistenceHelper.manager.navigate(analysis, KETAnalysisProjectLink.PROJECT_ROLE, KETAnalysisProjectLink.class,
	        false);
	if (psc.hasMoreElements()) {
	    while (psc.hasMoreElements()) {
		KETAnalysisProjectLink plink = (KETAnalysisProjectLink) psc.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	// 고객처 삭제
	QueryResult qsc = PersistenceHelper.manager.navigate(analysis, KETAnalysisCustDeptLink.CUST_DEPT_ROLE,
	        KETAnalysisCustDeptLink.class, false);
	if (qsc.hasMoreElements()) {
	    while (qsc.hasMoreElements()) {
		KETAnalysisCustDeptLink plink = (KETAnalysisCustDeptLink) qsc.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	// 해석구분 삭제
	QueryResult qsd = PersistenceHelper.manager.navigate(analysis, KETAnalysisDivisionLink.DIVISION_ROLE,
	        KETAnalysisDivisionLink.class, false);
	if (qsd.hasMoreElements()) {
	    while (qsd.hasMoreElements()) {
		KETAnalysisDivisionLink plink = (KETAnalysisDivisionLink) qsd.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	boolean isCheckout = KETObjectUtil.isCheckout((WTObject) analysis);
	if (isCheckout) {
	    analysis = (KETAnalysisRequestMaster) KETObjectUtil.checkIn(analysis);
	    System.out.println("==========>Checkouted");
	    return null;
	}

	// 모든 Revision 삭제
	analysis = (KETAnalysisRequestMaster) PersistenceHelper.manager.delete(analysis);

	// Master 삭제
	PersistenceHelper.manager.delete(analysis.getMaster());

	return null;
    }

    /**
     * 신규 해석의뢰 No 생성
     * 
     * 1. C 2. 년도 구분(2자리) - 14(2014년 등록 프로그램) 3. 일년번호(3자리) - 001 4. 해석구분(5자리)
     * 
     * @return
     * @throws Exception
     * @메소드명 : getNewAnalysisNo
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private String getNewAnalysisNo(KETAnalysisRequestDTO paramObject) throws Exception {
	// 차종기준이 앞에 오도록 추가 필요
	String newAnalysisNo = "C";
	newAnalysisNo += DateUtil.getThisYear().substring(2);
	newAnalysisNo += ManageSequence.getSeqNo(newAnalysisNo, "000", "WTDOCUMENTMASTERKEY", "WTKEY");
	return newAnalysisNo;
    }

    /**
     * 해석의뢰 Master과 연결된 고객처 Name 정보
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getCustomerDeptsNameLink
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getProjectOidLink(String analysisOid) throws Exception {
	String projectOid = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx_link = query.appendClassList(KETAnalysisProjectLink.class, false);

	query.appendSelectAttribute("roleAObjectRef.key.classname", idx_link, false);
	query.appendSelectAttribute("roleAObjectRef.key.id", idx_link, false);
	query.appendWhere(new SearchCondition(KETAnalysisProjectLink.class, "roleBObjectRef.key.id", "=", analysisId),
	        new int[] { idx_link });

	System.out.println("by hooni ::: " + query.toString());

	QueryResult rs = PersistenceHelper.manager.find(query);
	while (rs.hasMoreElements()) {
	    Object[] object = (Object[]) rs.nextElement();
	    projectOid = object[0] + ":" + object[1];
	}
	System.out.println("==========>" + projectOid);
	return projectOid;
    }

    /**
     * 해석의뢰 Master과 연결된 고객처 Name 정보
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getCustomerDeptsNameLink
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getCustomerDeptsNameLink(String analysisOid) throws Exception {
	String customerDepartment = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx_number = query.appendClassList(NumberCode.class, false);
	int idx_link1 = query.appendClassList(KETAnalysisCustDeptLink.class, false);

	query.appendSelectAttribute("name", idx_number, false);

	ClassAttribute ca_number = null;
	ClassAttribute ca_link1 = null;

	ca_number = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca_link1 = new ClassAttribute(KETAnalysisCustDeptLink.class, "roleAObjectRef.key.id"); // numbercode
	sc = new SearchCondition(ca_link1, "=", ca_number);
	query.appendWhere(sc, new int[] { idx_link1, idx_number });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETAnalysisCustDeptLink.class, "roleBObjectRef.key.id", "=", analysisId),
	        new int[] { idx_link1 });

	QueryResult rs = PersistenceHelper.manager.find(query);
	int i = 0;
	while (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    String r_name = (String) objects[0];
	    if (i != 0) {
		customerDepartment += ", ";
	    }
	    customerDepartment += r_name;
	    i++;
	}
	return customerDepartment;
    }

    /**
     * 해석의뢰 Master과 연결된 해석구분 Name 정보
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisDivisionNameLink
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisDivisionNameLink(String analysisOid) throws Exception {
	String analysisDivision = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx_number = query.appendClassList(NumberCode.class, false);
	int idx_link1 = query.appendClassList(KETAnalysisDivisionLink.class, false);

	query.appendSelectAttribute("name", idx_number, false);

	ClassAttribute ca_number = null;
	ClassAttribute ca_link1 = null;

	ca_number = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca_link1 = new ClassAttribute(KETAnalysisDivisionLink.class, "roleAObjectRef.key.id"); // numbercode
	sc = new SearchCondition(ca_link1, "=", ca_number);
	query.appendWhere(sc, new int[] { idx_link1, idx_number });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETAnalysisDivisionLink.class, "roleBObjectRef.key.id", "=", analysisId),
	        new int[] { idx_link1 });
	QueryResult rs = PersistenceHelper.manager.find(query);
	int i = 0;
	while (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    String r_name = (String) objects[0];
	    if (i != 0) {
		analysisDivision += ", ";
	    }
	    analysisDivision += r_name;
	    i++;
	}
	return analysisDivision;
    }

    /**
     * 해석의뢰 Master과 연결된 고객처 정보 Code
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisDivisionCodeLink
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisDivisionCodeLink(String analysisOid) throws Exception {
	String analysisDivision = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx_number = query.appendClassList(NumberCode.class, false);
	int idx_link1 = query.appendClassList(KETAnalysisDivisionLink.class, false);

	query.appendSelectAttribute("thePersistInfo.theObjectIdentifier.id", idx_number, false);

	ClassAttribute ca_number = null;
	ClassAttribute ca_link1 = null;

	ca_number = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca_link1 = new ClassAttribute(KETAnalysisDivisionLink.class, "roleAObjectRef.key.id"); // numbercode
	sc = new SearchCondition(ca_link1, "=", ca_number);
	query.appendWhere(sc, new int[] { idx_link1, idx_number });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETAnalysisDivisionLink.class, "roleBObjectRef.key.id", "=", analysisId),
	        new int[] { idx_link1 });
	QueryResult rs = PersistenceHelper.manager.find(query);
	int i = 0;
	while (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    String r_name = String.valueOf(objects[0]);
	    if (i != 0) {
		analysisDivision += ",";
	    }
	    analysisDivision += r_name;
	    i++;
	}
	return analysisDivision;
    }

    /**
     * 해석의뢰 Master과 연결된 고객처 Code 정보
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getCustomerDeptsCodeLink
     * @작성자 : Hooni
     * @작성일 : 2014. 11. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getCustomerDeptsCodeLink(String analysisOid) throws Exception {
	String customerDepartment = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx_number = query.appendClassList(NumberCode.class, false);
	int idx_link1 = query.appendClassList(KETAnalysisCustDeptLink.class, false);

	query.appendSelectAttribute("thePersistInfo.theObjectIdentifier.id", idx_number, false);

	ClassAttribute ca_number = null;
	ClassAttribute ca_link1 = null;

	ca_number = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	ca_link1 = new ClassAttribute(KETAnalysisCustDeptLink.class, "roleAObjectRef.key.id"); // numbercode
	sc = new SearchCondition(ca_link1, "=", ca_number);
	query.appendWhere(sc, new int[] { idx_link1, idx_number });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETAnalysisCustDeptLink.class, "roleBObjectRef.key.id", "=", analysisId),
	        new int[] { idx_link1 });

	QueryResult rs = PersistenceHelper.manager.find(query);
	int i = 0;
	while (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    String r_name = String.valueOf(objects[0]);
	    if (i != 0) {
		customerDepartment += ",";
	    }
	    customerDepartment += r_name;
	    i++;
	}
	return customerDepartment;
    }

    /**
     * findPaging 검색 Query
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getListQuery
     * @작성자 : Hooni
     * @작성일 : 2014. 10. 31.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getListQuery(KETAnalysisRequestDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	QuerySpec div_query = new QuerySpec();
	QuerySpec cust_query = new QuerySpec();
	SearchCondition sc = null;

	// System.out.println("해석의뢰서NO ::: " + paramObject.getAnalysisRequestNo());
	// System.out.println("프로젝트NO ::: " + paramObject.getProjectNo());
	// System.out.println("해석용도 ::: " + paramObject.getAnalysisUse());
	// System.out.println("해석구분 ::: " + paramObject.getAnalysisDivision());
	// System.out.println("개발단계 ::: " + paramObject.getProcess());
	// System.out.println("고객처 ::: " + paramObject.getCustomerDepartment());
	// System.out.println("고객담당자 ::: " + paramObject.getCustomerChargeName());
	// System.out.println("차종 ::: " + paramObject.getCarType());
	// System.out.println("담당자 ::: " + paramObject.getKetChargeUser());
	// System.out.println("의뢰부서 ::: " + paramObject.getKetClientDepartment());
	// System.out.println("의뢰자 ::: " + paramObject.getKetClientUser());

	System.out.println("popup getListQuery ::: " + paramObject.getPopup());

	int idx_master = query.appendClassList(KETAnalysisRequestMaster.class, true);
	int idx_masterInfo_link = query.appendClassList(KETAnalysisMasterInfoLink.class, true);
	int idx_info = query.appendClassList(KETAnalysisRequestInfo.class, true);
	int idx_wfmMMaster = query.appendClassList(KETWfmApprovalMaster.class, true);
	int idx_wfmMHistory = query.appendClassList(KETWfmApprovalHistory.class, true);
	int idx_wfmIMaster = query.appendClassList(KETWfmApprovalMaster.class, true);
	int idx_wfmIHistory = query.appendClassList(KETWfmApprovalHistory.class, true);

	sc = new SearchCondition(KETAnalysisRequestMaster.class, "iterationInfo.latest", SearchCondition.IS_TRUE, true);
	query.appendWhere(sc, new int[] { idx_master });
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETAnalysisMasterInfoLink.class, "roleBObjectRef.key.id"));
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx_master, idx_masterInfo_link });
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestInfo.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETAnalysisMasterInfoLink.class, "roleAObjectRef.key.id"));
	if (!"popup".equals(paramObject.getPopup())) {
	    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}
	query.appendWhere(sc, new int[] { idx_info, idx_masterInfo_link });
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETWfmApprovalMaster.class, "pboReference.key.id"));
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx_master, idx_wfmMMaster });
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETWfmApprovalMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETWfmApprovalHistory.class, "appMasterReference.key.id"));
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx_wfmMMaster, idx_wfmMHistory });
	query.appendAnd();

	sc = new SearchCondition(KETWfmApprovalHistory.class, "activityName", SearchCondition.EQUAL, "검토");
	if (!"popup".equals(paramObject.getPopup())) {
	    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}
	query.appendWhere(sc, new int[] { idx_wfmMHistory });
	query.appendAnd();

	sc = new SearchCondition(KETWfmApprovalHistory.class, "decision", SearchCondition.EQUAL, "승인");
	if (!"popup".equals(paramObject.getPopup())) {
	    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}
	query.appendWhere(sc, new int[] { idx_wfmMHistory });
	query.appendAnd();
	query.appendOpenParen();
	sc = new SearchCondition(KETWfmApprovalHistory.class, "last", SearchCondition.IS_TRUE);
	query.appendWhere(sc, new int[] { idx_wfmMHistory });
	query.appendOr();
	sc = new SearchCondition(KETWfmApprovalHistory.class, "last", SearchCondition.IS_NULL);
	query.appendWhere(sc, new int[] { idx_wfmMHistory });
	query.appendCloseParen();
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestInfo.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETWfmApprovalMaster.class, "pboReference.key.id"));
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx_info, idx_wfmIMaster });
	query.appendAnd();

	sc = new SearchCondition(new ClassAttribute(KETWfmApprovalMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETWfmApprovalHistory.class, "appMasterReference.key.id"));
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idx_wfmIMaster, idx_wfmIHistory });
	query.appendAnd();

	sc = new SearchCondition(KETWfmApprovalHistory.class, "activityName", SearchCondition.EQUAL, "검토");
	if (!"popup".equals(paramObject.getPopup())) {
	    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}
	query.appendWhere(sc, new int[] { idx_wfmIHistory });
	query.appendAnd();

	sc = new SearchCondition(KETWfmApprovalHistory.class, "decision", SearchCondition.EQUAL, "승인");
	if (!"popup".equals(paramObject.getPopup())) {
	    sc.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}
	query.appendWhere(sc, new int[] { idx_wfmIHistory });
	query.appendAnd();
	query.appendOpenParen();
	sc = new SearchCondition(KETWfmApprovalHistory.class, "last", SearchCondition.IS_TRUE);
	query.appendWhere(sc, new int[] { idx_wfmIHistory });
	query.appendOr();
	sc = new SearchCondition(KETWfmApprovalHistory.class, "last", SearchCondition.IS_NULL);
	query.appendWhere(sc, new int[] { idx_wfmIHistory });
	query.appendCloseParen();

	// 해석구분
	if (!StringUtil.isTrimToEmpty(paramObject.getAnalysisDivision())) {
	    // if (query.getConditionCount() > 0) {
	    // query.appendAnd();
	    // }
	    int idx_num = div_query.appendClassList(NumberCode.class, false);
	    int idx_division = div_query.appendClassList(KETAnalysisDivisionLink.class, false); // 해석구분

	    div_query.appendSelectAttribute("roleBObjectRef.key.id", idx_division, false);

	    ClassAttribute ca_number = null;
	    ClassAttribute ca_divlink = null;

	    ca_number = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
	    ca_divlink = new ClassAttribute(KETAnalysisDivisionLink.class, "roleAObjectRef.key.id");

	    sc = new SearchCondition(ca_number, SearchCondition.EQUAL, ca_divlink);
	    div_query.appendWhere(sc, new int[] { idx_num, idx_division });
	    div_query.appendAnd();
	    sc = new SearchCondition(ca_number, SearchCondition.IN, new ArrayExpression(paramObject.getAnalysisDivision().split(",")));
	    div_query.appendWhere(sc, new int[] { idx_num });
	}
	// 고객처
	if (!StringUtil.isTrimToEmpty(paramObject.getCustomerDepartment())) {
	    // if (query.getConditionCount() > 0) {
	    // query.appendAnd();
	    // }
	    int idx_cust = cust_query.appendClassList(KETAnalysisCustDeptLink.class, false);
	    cust_query.appendSelectAttribute("roleBObjectRef.key.id", idx_cust, false);

	    sc = new SearchCondition(KETAnalysisCustDeptLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getCustomerDepartment()));
	    cust_query.appendWhere(sc, new int[] { idx_cust });
	}

	// 해석의뢰 NO
	if (!StringUtil.isTrimToEmpty(paramObject.getAnalysisRequestNo())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "analysisRequestNo", SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getAnalysisRequestNo(), "*", "%"), false);
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 프로젝트 NO
	if (!StringUtil.isTrimToEmpty(paramObject.getProjectNo())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "projectNo", SearchCondition.LIKE, StringUtil.changeString(
		    paramObject.getProjectNo(), "*", "%"), false);
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 해석용도
	if (!StringUtil.isTrimToEmpty(paramObject.getAnalysisUse())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "usageReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getAnalysisUse()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 개발단계구분
	if (!StringUtil.isTrimToEmpty(paramObject.getProcess())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "processReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getProcess()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 고객담당자
	if (!StringUtil.isTrimToEmpty(paramObject.getCustomerChargeName())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "customerChargeName", SearchCondition.EQUAL,
		    paramObject.getCustomerChargeName());
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 차종
	if (!StringUtil.isTrimToEmpty(paramObject.getCarType())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "carTypeReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getCarType()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 담당자
	if (!StringUtil.isTrimToEmpty(paramObject.getKetChargeUser())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "ketChargeUserReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getKetChargeUser()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 의뢰부서
	if (!StringUtil.isTrimToEmpty(paramObject.getKetClientDepartment())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "ketClientDepartmentReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getKetClientDepartment()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 의뢰자
	if (!StringUtil.isTrimToEmpty(paramObject.getKetClientUser())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETAnalysisRequestMaster.class, "ketClientUserReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(paramObject.getKetClientUser()));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 고객처
	if (!StringUtil.isTrimToEmpty(paramObject.getCustomerDepartment())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestMaster.class, "thePersistInfo.theObjectIdentifier.id"),
		    SearchCondition.IN, new SubSelectExpression(cust_query));
	    query.appendWhere(sc, new int[] { idx_master });
	}
	// 해석구분
	if (!StringUtil.isTrimToEmpty(paramObject.getAnalysisDivision())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(new ClassAttribute(KETAnalysisRequestMaster.class, "thePersistInfo.theObjectIdentifier.id"),
		    SearchCondition.IN, new SubSelectExpression(div_query));
	    query.appendWhere(sc, new int[] { idx_master });
	}

	// 의뢰일자
	// System.out.println("의뢰날짜 ::: " + paramObject.getCreateStartDate() + " ~ " + paramObject.getCreateEndDate());
	String createStartDate = StringUtil.checkNull(paramObject.getCreateStartDate());
	String createEndDate = StringUtil.checkNull(paramObject.getCreateEndDate());

	if (createStartDate.length() > 0 || createEndDate.length() > 0) {

	    if (createStartDate != null && createStartDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(createStartDate, new ParsePosition(0))
		        .getTime()));

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		sc = new SearchCondition(KETWfmApprovalHistory.class, KETWfmApprovalHistory.COMPLETED_DATE,
		        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		query.appendWhere(sc, new int[] { idx_wfmMHistory });
	    }
	    if (createEndDate != null && createEndDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(createEndDate + ":23-59-59",
		        new ParsePosition(0)).getTime()));

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		sc = new SearchCondition(KETWfmApprovalHistory.class, KETWfmApprovalHistory.COMPLETED_DATE, SearchCondition.LESS_THAN,
		        convertDate);
		query.appendWhere(sc, new int[] { idx_wfmMHistory });
	    }
	}

	// 완료일자
	// System.out.println("완료날짜 ::: " + paramObject.getEndStartDate() + " ~ " + paramObject.getEndEndDate());
	String endStartDate = StringUtil.checkNull(paramObject.getCreateStartDate());
	String endEndDate = StringUtil.checkNull(paramObject.getCreateEndDate());

	if (endStartDate.length() > 0 || endEndDate.length() > 0) {

	    if (endStartDate != null && endStartDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(endStartDate, new ParsePosition(0))
		        .getTime()));

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		sc = new SearchCondition(KETWfmApprovalHistory.class, KETWfmApprovalHistory.COMPLETED_DATE,
		        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		query.appendWhere(sc, new int[] { idx_wfmIHistory });
	    }
	    if (endEndDate != null && endEndDate.length() > 0) {
		Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(endEndDate + ":23-59-59",
		        new ParsePosition(0)).getTime()));

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		sc = new SearchCondition(KETWfmApprovalHistory.class, KETWfmApprovalHistory.COMPLETED_DATE, SearchCondition.LESS_THAN,
		        convertDate);
		query.appendWhere(sc, new int[] { idx_wfmIHistory });
	    }
	}

	// Order by
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    System.out.println();
	    if (checkName.equals("analysisRequestNo") || checkName.equals("-analysisRequestNo")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, checkName.substring(1), "analysisRequestNo",
			    true);
		} else {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, checkName, "analysisRequestNo", false);
		}
	    } else if (checkName.equals("analysisUseName") || checkName.equals("-analysisUseName")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, "usageReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, "usageReference.key.id", false);
		}
	    } else if (checkName.equals("analysisTitle") || checkName.equals("-analysisTitle")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, checkName.substring(1), "analysisTitle", true);
		} else {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, checkName, "analysisTitle", false);
		}
	    } else if (checkName.equals("startDate") || checkName.equals("-startDate")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETWfmApprovalHistory.class, idx_wfmMHistory, KETWfmApprovalHistory.COMPLETED_DATE, true);
		} else {
		    SearchUtil.setOrderBy(query, KETWfmApprovalHistory.class, idx_wfmMHistory, KETWfmApprovalHistory.COMPLETED_DATE, false);
		}
	    } else if (checkName.equals("ketClientUserName") || checkName.equals("-ketClientUserName")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, "ketClientUserReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, "ketClientUserReference.key.id", false);
		}
	    } else if (checkName.equals("endDate") || checkName.equals("-endDate")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETWfmApprovalHistory.class, idx_wfmIHistory, KETWfmApprovalHistory.COMPLETED_DATE, true);
		} else {
		    SearchUtil.setOrderBy(query, KETWfmApprovalHistory.class, idx_wfmIHistory, KETWfmApprovalHistory.COMPLETED_DATE, false);
		}
	    } else if (checkName.equals("stateName") || checkName.equals("-stateName")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, KETAnalysisRequestMaster.LIFE_CYCLE_STATE,
			    true);
		    SearchUtil.setOrderBy(query, KETAnalysisRequestInfo.class, idx_info, KETAnalysisRequestInfo.LIFE_CYCLE_STATE, true);
		} else {
		    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, KETAnalysisRequestMaster.LIFE_CYCLE_STATE,
			    false);
		    SearchUtil.setOrderBy(query, KETAnalysisRequestInfo.class, idx_info, KETAnalysisRequestInfo.LIFE_CYCLE_STATE, false);
		}
	    }
	} else {
	    SearchUtil.setOrderBy(query, KETAnalysisRequestMaster.class, idx_master, KETAnalysisRequestMaster.ANALYSIS_REQUEST_NO, false);
	}
	query.setQuerySet(false);
	System.out.println("by hooni arm sql ::: " + query.toString());
	return query;
    }

    /**
     * 해석의뢰 프로젝트 제품구분 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisMasterClassName(String analysisOid) throws Exception {

	String projectOid = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx = query.appendClassList(KETAnalysisProjectLink.class, false);

	query.appendSelectAttribute("roleAObjectRef.key.classname", idx, false);
	query.appendSelectAttribute("roleAObjectRef.key.id", idx, false);

	query.appendWhere(new SearchCondition(KETAnalysisProjectLink.class, "roleBObjectRef.key.id", "=", analysisId), new int[] { idx });
	QueryResult rs = PersistenceHelper.manager.find(query);

	String className = "";

	if (rs.hasMoreElements()) {

	    Object[] objects = (Object[]) rs.nextElement();
	    projectOid = String.valueOf(objects[0] + ":" + objects[1]);

	    E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	    // getProductTypeLevel2 제품구분추가 사용 시 2015.03.12
	    KETPartClassification partClaz = (KETPartClassification) CommonUtil.getObject(project.getProductTypeLevel2());

	    if (partClaz != null) {
		className = partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();
	    }
	}

	return className;
    }

    /**
     * 해석의뢰 담당자 변경(KETWfmApprovalMaster 변경)
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2015. 03. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public KETWfmApprovalMaster approvalMastermodify(String analysisOid, String appUserOid) throws Exception {
	// TODO Auto-generated method stub

	KETWfmApprovalMaster appAnalysis = WorkflowSearchHelper.manager.getMaster((Persistable) CommonUtil.getObject(analysisOid));
	WTUser member = (WTUser) CommonUtil.getObject(appUserOid);
	WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
	WTPrincipalReference pRef = WTPrincipalReference.newWTPrincipalReference(prin);

	Vector<KETWfmApprovalHistory> hisVec = WorkflowSearchHelper.manager.getApprovalHistory(appAnalysis);

	KETWfmApprovalHistory hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
	hisAttr.setActivityName("의뢰접수");
	Timestamp completeDate = DateUtil.getCurrentTimestamp();
	hisAttr.setCompletedDate(completeDate);
	hisAttr.setComments("의뢰접수 담당자변경");
	hisAttr.setDecision("승인");
	hisAttr.setAppMaster(appAnalysis);
	hisAttr.setOwner(pRef); // SessionHelper.manager.getPrincipalReference()
	hisAttr.setSeqNum((hisVec.size()) + 1);
	PersistenceHelper.manager.save(hisAttr);
	// createHistory(hisAttr);

	return appAnalysis;
    }
}