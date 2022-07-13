package ext.ket.sales.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.common.tag.entity.KETTagLink;
import ext.ket.common.tag.util.TagUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesCSMeetingManage;
import ext.ket.sales.entity.KETSalesCSMeetingManageLink;
import ext.ket.sales.entity.KETSalesCompanyLink;
import ext.ket.sales.entity.KETSalesMemberLink;
import ext.ket.sales.entity.KETSalesPartClassLink;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sales.entity.KETSalesProjectDTO;
import ext.ket.sales.entity.KETSalesProjectLink;
import ext.ket.sales.entity.KETSalesTask;
import ext.ket.sales.entity.KETSalesTaskHistory;
import ext.ket.sales.entity.KETSalesTaskHistoryLink;
import ext.ket.sales.entity.KETSalesTaskLink;
import ext.ket.sales.service.internal.salesDao;
import ext.ket.sales.util.CSProjectSortEnum;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;
import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.ColumnListExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;

/**
 * 
 * 
 * @클래스명 : StandardSalesService
 * @작성자 : 황정태
 * @작성일 : 2016. 10. 13.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardSalesService extends StandardManager implements SalesService {

    private static final long serialVersionUID = 1L;
    private salesDao dao = new salesDao();

    public static StandardSalesService newStandardSalesService() throws WTException {
	StandardSalesService instance = new StandardSalesService();
	instance.initialize();
	return instance;
    }

    @Override
    public String saveSales(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String folderPath = "/Default/자동차사업부/초기유동/";

	/************************************************
	 * KETSalesProject 등록 Start
	 *************************************************/

	KETSalesProject salesPjt = null;

	String ProjectNo = "";

	String oid = paramObject.getOid();

	Transaction transaction = new Transaction();

	try {

	    transaction.start();

	    boolean isNew = true;
	    boolean isTransient = Boolean.parseBoolean(paramObject.getIsTransient());
	    boolean isMainSend = false;
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();

	    if (StringUtils.isNotEmpty(oid)) {
		isMainSend = true;
		isNew = false;
		salesPjt = (KETSalesProject) CommonUtil.getObject(oid);
		if ("R".equals(paramObject.getRevise())) {// 개정
		    Versioned newVs = null;
		    Versioned vs = (Versioned) salesPjt;

		    KETSalesProject latest = this.getLatestSalesProject(salesPjt);

		    if (!oid.equals(CommonUtil.getOIDString(latest))) {
			throw new Exception("최신 버전이 아닙니다!");
		    }

		    String lifecycle = ((LifeCycleManaged) vs).getLifeCycleName();
		    Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
		    newVs = VersionControlHelper.service.newVersion(vs);
		    FolderHelper.assignLocation((FolderEntry) newVs, folder);
		    salesPjt = (KETSalesProject) newVs;
		    salesPjt.setSalesState(NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "I"));// 진행상태로 변경
		} else {
		    if (!isTransient) {
			salesPjt = (KETSalesProject) ObjectUtil.checkout(salesPjt);
		    }
		}

	    } else {
		salesPjt = KETSalesProject.newKETSalesProject();
		Department dept = new PeopleData((wtuser)).department;
		Department parentDept = (Department)dept.getParent();
		salesPjt.setSalesTeam(dept);// 협업팀 수정시 담당부서가 바뀌면 안되므로 최초생성시에만 부서정보를 입력한다
		
		if(parentDept!=null){//종합상황실 인터페이스를 위한 센타 정보 추가
		    salesPjt.setCenterCode(parentDept.getCode());
		}
		
		
	    }

	    String tilte = paramObject.getProjectName();
	    NumberCode temp = null;
	    BigDecimal investSum = new BigDecimal("0");
	    BigDecimal planTotalSum = new BigDecimal("0");
	    BigDecimal planYearSum = new BigDecimal("0");
	    BigDecimal expectTotalSum = new BigDecimal("0");
	    BigDecimal expectYearSum = new BigDecimal("0");
	    String classOid_arr[] = paramObject.getClassOidArr();
	    String mainCheck_arr[] = paramObject.getMainCheck();
	    String invest_arr[] = paramObject.getInvestCost();
	    String planTotal_arr[] = paramObject.getPlanTotal();
	    String planYear_arr[] = paramObject.getPlanYear();
	    String expectTotal_arr[] = paramObject.getExpectSalesTotal();
	    String expectYear_arr[] = paramObject.getExpectSalesYear();
	    String refTag_arr[] = paramObject.getRefTag();

	    if ("ok".equals(paramObject.getBasicEditAuth()) || "ok".equals(paramObject.getSeniorAuth())) {
		if (isNew) {

		    String tempDate = DateUtil.getDateString(new Date(), "all");
		    // OEM + 국가 + 년도
		    ProjectNo = ((NumberCode) CommonUtil.getObject(paramObject.getLastBuyerCode())).getCode() + "-"
			    + ((NumberCode) CommonUtil.getObject(paramObject.getNation())).getCode() + "-" + tempDate.substring(2, 4) + "-";
		    ProjectNo = ProjectNo + ManageSequence.getSeqNo(ProjectNo, "000", "KETSalesProject", KETSalesProject.PROJECT_NO);
		    salesPjt.setProjectNo(ProjectNo);
		    salesPjt.setTitle(tilte);
		    salesPjt.setName(tilte);
		    salesPjt.setNumber(ProjectNo);
		    salesPjt.setSalesState(NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "I"));// 진행
		}

		salesPjt.setProjectName(tilte);

		if (StringUtils.isNotEmpty(paramObject.getSalesRank())) {// 중요도
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getSalesRank());
		    salesPjt.setRankType(temp);
		} else {
		    throw new Exception("중요도 가 누락되었습니다.");
		}

		if (StringUtils.isNotEmpty(paramObject.getDevelopType())) {// 프로젝트 유형
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getDevelopType());
		    salesPjt.setDevType(temp);
		} else {
		    throw new Exception("프로젝트 유형 이 누락되었습니다.");
		}

		salesPjt.setApplyArea(paramObject.getApplyArea());// 적용부

		if (StringUtils.isNotEmpty(paramObject.getModel())) {// 차종
		    OEMProjectType oem = (OEMProjectType) CommonUtil.getObject(paramObject.getModel());
		    salesPjt.setOemType(oem);
		} else {
		    throw new Exception("차종 이 누락되었습니다.");
		}

		if (StringUtils.isNotEmpty(paramObject.getSopDate())) {// sop
		    salesPjt.setSop(DateUtil.getTimestampFormat(paramObject.getSopDate(), "yyyy-MM-dd"));
		}

		if (StringUtils.isNotEmpty(paramObject.getNation())) {// 국가
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getNation());
		    salesPjt.setNationType(temp);
		} else {
		    throw new Exception("국가 가 누락되었습니다.");
		}

		if (StringUtils.isNotEmpty(paramObject.getMainCategory())) {// 제품군대분류
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getMainCategory());
		    salesPjt.setMainCategoryType(temp);
		} else {
		    throw new Exception("제품군대분류 가 누락되었습니다.");
		}
		
		
		salesPjt.setWorkShopYN(paramObject.getWorkShopYN()); //workshop 여부
		

		if (StringUtils.isNotEmpty(paramObject.getWebEditor())) {// 영업목표
		    salesPjt.setWebEditor(paramObject.getWebEditor());
		}

		if (StringUtils.isNotEmpty(paramObject.getWebEditorText())) {
		    salesPjt.setWebEditorText(paramObject.getWebEditorText());
		}

		if (StringUtils.isNotEmpty(paramObject.getFailReason())) {// 실패사유
		    salesPjt.setFailReason(paramObject.getFailReason());
		}

		if (StringUtils.isNotEmpty(paramObject.getObtainCompany())) {// 수주사
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getObtainCompany());

		    salesPjt.setObtainCompanyType(temp);
		}

		if (StringUtils.isNotEmpty(paramObject.getFailtypecode())) {// 실패유형
		    temp = (NumberCode) CommonUtil.getObject(paramObject.getFailtypecode());

		    salesPjt.setFailType(temp);
		}

		if (StringUtils.isNotEmpty(paramObject.getProjectResult())) {// 프로젝트 결과
		    salesPjt.setProjectResult(paramObject.getProjectResult());
		}

		if (StringUtils.isNotEmpty(paramObject.getFailReason())) {
		    salesPjt.setFailReason(paramObject.getFailReason());
		}

		if (StringUtils.isNotEmpty(paramObject.getBigo())) {
		    salesPjt.setBigo(paramObject.getBigo());
		}

		if (isNew) {
		    WTContainerRef containerRef = WCUtil.getWTContainerRef();

		    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
		    FolderHelper.assignLocation((FolderEntry) salesPjt, folder);
		}

		for (int i = 0; i < mainCheck_arr.length; i++) {

		    //if (!"Y".equals(mainCheck_arr[i])) {

			investSum = (new BigDecimal(invest_arr[i]).add(investSum));

			planTotalSum = (new BigDecimal(planTotal_arr[i]).add(planTotalSum));

			planYearSum = (new BigDecimal(planYear_arr[i]).add(planYearSum));

			expectTotalSum = (new BigDecimal(expectTotal_arr[i]).add(expectTotalSum));

			expectYearSum = (new BigDecimal(expectYear_arr[i]).add(expectYearSum));
		    //}

		}

/*		if (classOid_arr == null) {// Sub 분류체계가 없을때 Main분류체계의 비용을 가져감
		    investSum = (new BigDecimal(invest_arr[0]).add(investSum));

		    planTotalSum = (new BigDecimal(planTotal_arr[0]).add(planTotalSum));

		    planYearSum = (new BigDecimal(planYear_arr[0]).add(planYearSum));

		    expectTotalSum = (new BigDecimal(expectTotal_arr[0]).add(expectTotalSum));

		    expectYearSum = (new BigDecimal(expectYear_arr[0]).add(expectYearSum));
		}*/

		salesPjt.setInvestCost(investSum.toString());
		salesPjt.setPlanTotal(planTotalSum.toString());
		salesPjt.setPlanYear(planYearSum.toString());
		salesPjt.setExpectSalesTotal(expectTotalSum.toString());
		salesPjt.setExpectSalesYear(expectYearSum.toString());

		if (!salesPjt.getSalesState().getName().equals("성공")) {
		    if (!isNew) {
			if (isTransient) {
			    // Iteration 증가 없이 속성 수정
			    PersistenceServerHelper.manager.update(salesPjt);

			    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
			}

		    } else {
			salesPjt = (KETSalesProject) PersistenceHelper.manager.save(salesPjt);
		    }

		    if (!isNew && !"R".equals(paramObject.getRevise()) && !isTransient) {
			salesPjt = (KETSalesProject) ObjectUtil.checkin(salesPjt);
			salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
		    }
		}

		oid = CommonUtil.getOIDString(salesPjt);

		if (!salesPjt.getSalesState().getName().equals("성공")) {
		    // 첨부파일
		    KETContentHelper.service.updateContent(salesPjt, paramObject);
		}
		
		/************************************************
		 * KETSalesProject 등록 End
		 *************************************************/
		
		
		/************************************************
		 * KETSalesProject TAG 등록 start
		 *************************************************/

		List<KETTagLink> tagLinkList = TagUtil.manager.getTagMasterLinkList(salesPjt);

		for (KETTagLink tagLink : tagLinkList) {
		    PersistenceHelper.manager.delete(tagLink);
		}

		if (refTag_arr != null) {
		    for (int i = 0; i < refTag_arr.length; i++) {

			temp = (NumberCode) CommonUtil.getObject(refTag_arr[i]);

			System.out.println("REFTAG #" + temp.getCode());

			KETTagLink link = KETTagLink.newKETTagLink(salesPjt, temp);
			PersistenceHelper.manager.save(link);
		    }
		}
		

		/************************************************
		 * KETSalesProject TAG 등록 End
		 *************************************************/
		
		if (!salesPjt.getSalesState().getName().equals("성공")) {
		    KETSalesCompanyLink CompanyLink = null;

		    if (!isNew && !"R".equals(paramObject.getRevise()) && isTransient) {// 개정 또는 CS회의 대상으로 담는것이 아닌 일반 수정시 관련 데이터 초기화한다
			QueryResult qr = PersistenceHelper.manager.navigate(salesPjt, "company", KETSalesCompanyLink.class, false);
			while (qr.hasMoreElements()) {
			    CompanyLink = (KETSalesCompanyLink) qr.nextElement();
			    PersistenceHelper.manager.delete(CompanyLink);
			}

			KETSalesPartClassLink ClzLink = null;

			qr = PersistenceHelper.manager.navigate(salesPjt, "partClass", KETSalesPartClassLink.class, false);
			while (qr.hasMoreElements()) {
			    ClzLink = (KETSalesPartClassLink) qr.nextElement();
			    PersistenceHelper.manager.delete(ClzLink);
			}
		    }

		    /************************************************
		     * KETSalesCompanyLink 경쟁사 링크 등록 Start
		     *************************************************/
		    if (StringUtils.isNotEmpty(paramObject.getCompetitorCompany())) {// 경쟁사

			String competitor_arr[] = paramObject.getCompetitorCompany().split(",");

			for (int i = 0; i < competitor_arr.length; i++) {
			    temp = (NumberCode) CommonUtil.getObject(competitor_arr[i]);

			    CompanyLink = KETSalesCompanyLink.newKETSalesCompanyLink(salesPjt, temp);
			    CompanyLink.setCodeType("competitor");
			    PersistenceHelper.manager.save(CompanyLink);
			}

			// temp = (NumberCode) CommonUtil.getObject(paramObject.getCompetitorCompany());
			// salesPjt.setCompetitor(temp);
		    }
		    /************************************************
		     * KETSalesCompanyLink 경쟁사 링크 등록 End
		     *************************************************/

		    /************************************************
		     * KETSalesCompanyLink 고객사 링크 등록 Start
		     *************************************************/
		    String customer = paramObject.getCustomerCode();

		    String customer_arr[] = null;

		    if (StringUtils.isNotEmpty(customer)) {// 고객사 링크 등록
			customer_arr = customer.split(",");

			for (int i = 0; i < customer_arr.length; i++) {
			    temp = (NumberCode) CommonUtil.getObject(customer_arr[i]);

			    CompanyLink = KETSalesCompanyLink.newKETSalesCompanyLink(salesPjt, temp);
			    CompanyLink.setCodeType("customer");
			    PersistenceHelper.manager.save(CompanyLink);
			}
		    }
		    /************************************************
		     * KETSalesCompanyLink 고객사 링크 등록 End
		     *************************************************/

		    /************************************************
		     * KETSalesCompanyLink 자동차사 링크 등록 Start
		     *************************************************/
		    String buyer = paramObject.getLastBuyerCode();

		    String buyer_arr[] = null;

		    if (StringUtils.isNotEmpty(buyer)) {// 자동차사 링크 등록
			buyer_arr = buyer.split(",");

			for (int i = 0; i < buyer_arr.length; i++) {
			    temp = (NumberCode) CommonUtil.getObject(buyer_arr[i]);

			    CompanyLink = KETSalesCompanyLink.newKETSalesCompanyLink(salesPjt, temp);
			    CompanyLink.setCodeType("buyer");
			    PersistenceHelper.manager.save(CompanyLink);
			}
		    }
		    /************************************************
		     * KETSalesCompanyLink 자동차사 링크 등록 End
		     *************************************************/

		    /************************************************
		     * KETSalesPartClassLink 등록 Start (분류체계)
		     *************************************************/

		    // Main 등록
		    KETPartClassification partClaz;

		    partClaz = (KETPartClassification) CommonUtil.getObject(paramObject.getPartClazOid());

		    KETSalesPartClassLink MainClzLink = KETSalesPartClassLink.newKETSalesPartClassLink(salesPjt, partClaz);
		    
		    investSum = new BigDecimal("0");
		    planTotalSum = new BigDecimal("0");
		    planYearSum = new BigDecimal("0");
		    expectTotalSum = new BigDecimal("0");
		    expectYearSum = new BigDecimal("0");
		    
		    for (int i = 0; i < mainCheck_arr.length; i++) {

			if ("Y".equals(mainCheck_arr[i])) {

			    investSum = (new BigDecimal(invest_arr[i]));

			    planTotalSum = (new BigDecimal(planTotal_arr[i]));

			    planYearSum = (new BigDecimal(planYear_arr[i]));

			    expectTotalSum = (new BigDecimal(expectTotal_arr[i]));

			    expectYearSum = (new BigDecimal(expectYear_arr[i]));
			    }

		    }

		    MainClzLink.setMainYn("Y");
		    MainClzLink.setInvestCost(investSum.toString());
		    MainClzLink.setPlanTotal(planTotalSum.toString());
		    MainClzLink.setPlanYear(planYearSum.toString());
		    MainClzLink.setExpectSalesTotal(expectTotalSum.toString());
		    MainClzLink.setExpectSalesYear(expectYearSum.toString());

		    PersistenceHelper.manager.save(MainClzLink);

		    // Sub 등록
		    if (classOid_arr != null) {
			for (int i = 0; i < classOid_arr.length; i++) {

			    partClaz = (KETPartClassification) CommonUtil.getObject(classOid_arr[i]);

			    KETSalesPartClassLink ClzLink = KETSalesPartClassLink.newKETSalesPartClassLink(salesPjt, partClaz);

			    ClzLink.setInvestCost(invest_arr[i + 1]);
			    ClzLink.setPlanTotal(planTotal_arr[i + 1]);
			    ClzLink.setPlanYear(planYear_arr[i + 1]);
			    ClzLink.setExpectSalesTotal(expectTotal_arr[i + 1]);
			    ClzLink.setExpectSalesYear(expectYear_arr[i + 1]);

			    PersistenceHelper.manager.save(ClzLink);
			}
		    }

		    /************************************************
		     * KETSalesPartClassLink 등록 End (분류체계)
		     *************************************************/
		}
	    }
	    
	    /************************************************
	     * KETSalesMemberLink 등록 Start (프로젝트 참여자 등록)
	     *************************************************/
	    
	    String membser_arr[] = paramObject.getMembserOids();
	    
	    if(membser_arr != null){
		QueryResult qr = PersistenceHelper.manager.navigate(salesPjt, "member", KETSalesMemberLink.class, false);
        	    
        	while (qr.hasMoreElements()) {
        	    KETSalesMemberLink memberLink = (KETSalesMemberLink) qr.nextElement();
        	    PersistenceHelper.manager.delete(memberLink);
        	}
        	
        	for (int i = 0; i < membser_arr.length; i++) {
        	    WTUser member = (WTUser) CommonUtil.getObject(membser_arr[i]);
        	    KETSalesMemberLink memberLink = KETSalesMemberLink.newKETSalesMemberLink(salesPjt, member);
        	    PersistenceHelper.manager.save(memberLink);
        	}
	    }
	    
	    
	    
	    /************************************************
	     * KETSalesMemberLink 등록 End (프로젝트 참여자 등록)
	     *************************************************/

	    /************************************************
	     * KETSalesTaskLink 등록 Start (영업추진현황)
	     *************************************************/

	    String subject_arr[] = paramObject.getSubject();
	    String plancheck_arr[] = paramObject.getPlanCheck();
	    String planDate_arr[] = paramObject.getPlanDate();
	    String resultDate_arr[] = paramObject.getResultDate();
	    String salsePlan_arr[] = paramObject.getSalsePlan();
	    String userOid_arr[] = paramObject.getTargetPeopleOidAttr();

	    String propelwebEditor_arr[] = paramObject.getPropelwebEditor();
	    String propelwebEditorText_arr[] = paramObject.getPropelwebEditorText();

	    String problemwebEditor_arr[] = paramObject.getProblemwebEditor();
	    String problemwebEditorText_arr[] = paramObject.getProblemwebEditorText();

	    String planwebEditor_arr[] = paramObject.getPlanwebEditor();
	    String planwebEditorText_arr[] = paramObject.getPlanwebEditorText();

	    KETSalesTaskLink taskLink = null;
	    KETSalesTask task = null;
	    PeopleData people = null;

	    String delOids = StringUtils.removeEnd(paramObject.getDelOids(), ",");

	    if (StringUtils.isNotEmpty(delOids)) {
		String del_arr[] = delOids.split(",");

		if (StringUtils.isNotEmpty(delOids)) {
		    for (int i = 0; i < del_arr.length; i++) {// 삭제대상으로 지정된 task 삭제
			task = (KETSalesTask) CommonUtil.getObject(del_arr[i]);
			QueryResult qr = PersistenceHelper.manager.navigate(task, "project", KETSalesTaskLink.class, false);
			while (qr.hasMoreElements()) {
			    taskLink = (KETSalesTaskLink) qr.nextElement();
			    PersistenceHelper.manager.delete(task);
			    PersistenceHelper.manager.delete(taskLink);
			}
		    }
		}
	    }

	    String taskOids[] = paramObject.getTaskOids();
	    
	    NumberCode subjectCode = null;
	    
	    for (int i = 0; i < taskOids.length; i++) {
		if ("new".equals(taskOids[i])) {
		    task = KETSalesTask.newKETSalesTask();
		    if (salesPjt.getSalesState().getName().equals("성공")) {
			task.setTaskType("issue");
		    }
		} else {
		    task = (KETSalesTask) CommonUtil.getObject(taskOids[i]);
		}
		
		subjectCode = NumberCodeHelper.manager.getNumberCode("SUBJECTTYPE", subject_arr[i]);
		if(subjectCode != null){
		    task.setSalesSubjectCode(subjectCode.getCode());
		    task.setSubject(subjectCode.getName());
		}else{
		    task.setSubject(subject_arr[i]);
		}
		
		task.setPlanCheck(plancheck_arr[i]);
		task.setPlanDate(DateUtil.getTimestampFormat(planDate_arr[i], "yyyy-MM-dd"));

		if (!"0".equals(resultDate_arr[i])) {
		    task.setResultDate(DateUtil.getTimestampFormat(resultDate_arr[i], "yyyy-MM-dd"));
		} else {
		    task.setResultDate(null);
		}

		task.setSalesPlan(salsePlan_arr[i]);
		task.setCollaboManager((WTUser) CommonUtil.getObject(userOid_arr[i]));
		people = new PeopleData((WTUser) CommonUtil.getObject(userOid_arr[i]));
		task.setCollaboTeam(people.department);

		task.setPropelwebEditor(propelwebEditor_arr[i]);
		task.setPropelwebEditorText(propelwebEditorText_arr[i]);
		task.setProblemwebEditor(problemwebEditor_arr[i]);
		task.setProblemwebEditorText(problemwebEditorText_arr[i]);
		task.setPlanwebEditor(planwebEditor_arr[i]);
		task.setPlanwebEditorText(planwebEditorText_arr[i]);

		task = (KETSalesTask) PersistenceHelper.manager.save(task);

		if ("new".equals(taskOids[i])) {
		    taskLink = KETSalesTaskLink.newKETSalesTaskLink(salesPjt, task);
		    taskLink = (KETSalesTaskLink) PersistenceHelper.manager.save(taskLink);
		    // this.createTaskHistory("1", task);// step 이력생성
		}

		/*
	         * taskLink = taskLink.newKETSalesTaskLink(salesPjt, task);
	         * 
	         * taskLink = (KETSalesTaskLink) PersistenceHelper.manager.save(taskLink);
	         * 
	         * // 영업추진현황 별 진행현황,문제점,해결방안 Editor 등록 Start issue = issue.newKETSalesTaskIssue();
	         * 
	         * issue.setPropelwebEditor(propelwebEditor_arr[i]); issue.setPropelwebEditorText(propelwebEditorText_arr[i]);
	         * issue.setProblemwebEditor(problemwebEditor_arr[i]); issue.setProblemwebEditorText(problemwebEditorText_arr[i]);
	         * issue.setPlanwebEditor(planwebEditor_arr[i]); issue.setPlanwebEditorText(planwebEditorText_arr[i]);
	         * 
	         * issue = (KETSalesTaskIssue) PersistenceHelper.manager.save(issue);
	         * 
	         * issueLink = issueLink.newKETSalesIssueLink(task, issue);
	         * 
	         * issueLink = (KETSalesIssueLink) PersistenceHelper.manager.save(issueLink);
	         */

		// 영업추진현황 별 진행현황,문제점,해결방안 Editor 등록 End

	    }

	    String[] ProductProjects = paramObject.getProductPjtNos();

	    KETSalesProjectLink ProductProjectLink = null;
	    E3PSProjectMaster pjtMaster = null;
	    ProductProject ProductPjt = null;

	    QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "pjtMaster", KETSalesProjectLink.class, false);

	    List<HashMap<String, KETSalesProjectLink>> targetProjectlist = new ArrayList<HashMap<String, KETSalesProjectLink>>();
	    HashMap<String, KETSalesProjectLink> targetProjectMap = null;

	    while (inQr.hasMoreElements()) {
		ProductProjectLink = (KETSalesProjectLink) inQr.nextElement();

		int deleteFlag = 0;

		if (ProductProjects != null) {
		    for (int i = 0; i < ProductProjects.length; i++) {

			ProductPjt = (ProductProject) ProjectHelper.getProject(ProductProjects[i]);
			String targetProjectNo = ProductPjt.getPjtNo();

			if (ProductProjectLink.getPjtMaster().getPjtNo().equals(targetProjectNo)) {
			    deleteFlag++;
			    break;
			}
		    }
		}

		if (deleteFlag == 0) {

		    PersistenceHelper.manager.delete(ProductProjectLink);

		} else {
		    if (ProductProjects != null) {
			targetProjectMap = new HashMap<String, KETSalesProjectLink>();
			targetProjectMap.put("ProductProjectLink", ProductProjectLink);
			targetProjectlist.add(targetProjectMap);
		    }

		}

	    }

	    KETSalesProjectLink newProjectLink = null;

	    if (ProductProjects != null) {
		for (int i = 0; i < ProductProjects.length; i++) {

		    boolean doInsert = true;

		    ProductPjt = (ProductProject) ProjectHelper.getProject(ProductProjects[i]);
		    String targetProjectNo = ProductPjt.getPjtNo();

		    for (HashMap<String, KETSalesProjectLink> linkList : targetProjectlist) {

			ProductProjectLink = (KETSalesProjectLink) linkList.get("ProductProjectLink");

			if (ProductProjectLink.getPjtMaster().getPjtNo().equals(targetProjectNo)) {
			    doInsert = false;
			    break;
			}
		    }

		    pjtMaster = (E3PSProjectMaster) ProjectHelper.getProject(targetProjectNo).getMaster();

		    if (doInsert) {
			newProjectLink = KETSalesProjectLink.newKETSalesProjectLink(salesPjt, pjtMaster);
			PersistenceHelper.manager.save(newProjectLink);
		    }
		}
	    }

	    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);

	    this.setSortedTask(salesPjt);

	    // 계획 최대일, 계획 최소일, 실적 최대일, 실적 최소일 저장
	    for (int i = 0; i < planDate_arr.length; i++) {
		planDate_arr[i] = StringUtils.remove(planDate_arr[i], "-");
	    }

	    int resultCheck = 0;

	    for (int i = 0; i < resultDate_arr.length; i++) {
		if (!"0".equals(resultDate_arr[i])) {
		    resultCheck++;
		    resultDate_arr[i] = StringUtils.remove(resultDate_arr[i], "-");
		}
	    }

	    int[] planDate_arr_ = (int[]) new IntegerArrayConverter().convert(null, planDate_arr);
	    int[] resultDate_arr_ = {};
	    if (resultCheck > 0) {
		resultDate_arr_ = (int[]) new IntegerArrayConverter().convert(null, resultDate_arr);
	    }

	    Arrays.sort(planDate_arr_);
	    if (resultCheck > 0) {
		Arrays.sort(resultDate_arr_);
	    }

	    String planMin = Integer.toString(planDate_arr_[0]);
	    planMin = planMin.substring(0, 4) + "-" + planMin.substring(4, 6) + "-" + planMin.substring(6, 8);

	    String planMax = Integer.toString(planDate_arr_[planDate_arr_.length - 1]);
	    planMax = planMax.substring(0, 4) + "-" + planMax.substring(4, 6) + "-" + planMax.substring(6, 8);

	    String resultMin = "";

	    String resultMax = "";

	    if (resultCheck > 0) {
		resultMax = Integer.toString(planDate_arr_[resultDate_arr_.length - 1]);

		if (!"0".equals(resultMax)) {
		    resultMax = resultMax.substring(0, 4) + "-" + resultMax.substring(4, 6) + "-" + resultMax.substring(6, 8);
		}

		for (int i = 0; i < resultDate_arr_.length; i++) {
		    if (!"0".equals(Integer.toString(resultDate_arr_[i]))) {
			resultMin = Integer.toString(resultDate_arr_[i]);
			break;
		    }
		}

		resultMin = resultMin.substring(0, 4) + "-" + resultMin.substring(4, 6) + "-" + resultMin.substring(6, 8);

		salesPjt.setStartResultDate((DateUtil.getTimestampFormat(resultMin, "yyyy-MM-dd")));
		salesPjt.setEndResultDate((DateUtil.getTimestampFormat(resultMax, "yyyy-MM-dd")));
	    }

	    salesPjt.setStartPlanDate((DateUtil.getTimestampFormat(planMin, "yyyy-MM-dd")));
	    salesPjt.setEndPlanDate((DateUtil.getTimestampFormat(planMax, "yyyy-MM-dd")));

	    // Iteration 증가 없이 속성 수정
	    PersistenceServerHelper.manager.update(salesPjt);

	    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
	    transaction.commit();
	    transaction = null;

	    try {
		if (isMainSend && "ok".equals(paramObject.getBasicEditAuth())) {// 기본정보 수정시, 작성자의 팀장에게 변경내용 메일발송
		    Department dept = salesPjt.getSalesTeam();

		    WTUser chiefUser = PeopleHelper.manager.getChiefUser(dept);

		    if (chiefUser != null) {
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add(chiefUser);
			KETMailHelper.service.sendFormMail("08135", "SalesProjectChangeNotice.html", salesPjt, wtuser, toUserList);
		    }
		}

		if (isMainSend && "ok".equals(paramObject.getSeniorAuth())) {// 센터장 이상이 기본정보 수정시 작성자에게 알림메일 발송(내용은 없다)
		    List<WTUser> toUserList = new ArrayList<WTUser>();
		    toUserList.add((WTUser) salesPjt.getCreator().getPrincipal());
		    KETMailHelper.service.sendFormMail("08135", "SalesProjectChangeNotice.html", salesPjt, wtuser, toUserList);

		}
	    } catch (Exception e) {
		System.out.println("영업 프로젝트 메일 발송 실패 : [ " + salesPjt.getProjectNo() + "] 의 변경내용 : " + salesPjt.getBigo());
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    transaction.rollback();
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
	return oid;
    }

    @Override
    public KETSalesProjectDTO viewSalesProjectForm(KETSalesProjectDTO salesDto) throws Exception {
	// TODO Auto-generated method stub

	KETSalesProject salesPjt = (KETSalesProject) CommonUtil.getObject(salesDto.getOid());
	WTUser wtuser = (WTUser) salesPjt.getIterationInfo().getCreator().getPrincipal();
	String pmName = wtuser.getFullName();
	String pmOid = CommonUtil.getOIDString(wtuser);
	// Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	String pmDept = salesPjt.getSalesTeam().getName();
	String rev = salesPjt.getVersionInfo().getIdentifier().getValue() + "." + salesPjt.getIterationInfo().getIdentifier().getValue();

	// 기본정보 세팅 Start
	NumberCode num = (NumberCode) salesPjt.getRankType();// 중요도
	String rankName = num.getName();
	String rankOid = CommonUtil.getOIDString(num);

	String customerName = "";
	String lastBuyerName = "";
	String customerOid = "";
	String lastBuyerOid = "";
	String competitorName = "";
	String competitorOid = "";

	KETSalesCompanyLink CompanyLink = null;
	QueryResult qr = PersistenceHelper.manager.navigate(salesPjt, "company", KETSalesCompanyLink.class, false);

	while (qr.hasMoreElements()) {
	    CompanyLink = (KETSalesCompanyLink) qr.nextElement();
	    if ("customer".equals(CompanyLink.getCodeType())) {
		customerName += CompanyLink.getCompany().getName() + ",";
		customerOid = CommonUtil.getOIDString(CompanyLink.getCompany());
	    }
	    if ("buyer".equals(CompanyLink.getCodeType())) {
		lastBuyerName += CompanyLink.getCompany().getName();
		lastBuyerOid = CommonUtil.getOIDString(CompanyLink.getCompany());
	    }

	    if ("competitor".equals(CompanyLink.getCodeType())) {
		competitorName += CompanyLink.getCompany().getName() + ",";
		competitorOid += CommonUtil.getOIDString(CompanyLink.getCompany()) + ",";
	    }
	}
	customerName = StringUtils.removeEnd(customerName, ",");
	customerOid = StringUtils.removeEnd(customerOid, ",");

	competitorName = StringUtils.removeEnd(competitorName, ",");
	competitorOid = StringUtils.removeEnd(competitorOid, ",");

	num = (NumberCode) salesPjt.getDevType(); // 프로젝트유형
	String devTypeName = num.getName();
	String devTypeOid = CommonUtil.getOIDString(num);

	OEMProjectType oem = (OEMProjectType) salesPjt.getOemType(); // 차종
	String oemName = oem.getName();
	String oemOid = CommonUtil.getOIDString(oem);

	/*
	 * num = (NumberCode) salesPjt.getCompetitor(); // 경쟁사 String competitorName = num.getName(); String competitorOid =
	 * CommonUtil.getOIDString(num);
	 */

	num = (NumberCode) salesPjt.getNationType(); // 국가
	String nationName = num.getName();
	String nationOid = CommonUtil.getOIDString(num);

	num = (NumberCode) salesPjt.getMainCategoryType(); // 제품군대분류
	String mainCategoryName = num.getName();
	String mainCategory = CommonUtil.getOIDString(num);

	num = (NumberCode) salesPjt.getSalesState();// 프로젝트 상태
	String salesStateName = "";
	String salesStateOid = "";
	if (num != null) {
	    salesStateName = num.getName();
	    salesStateOid = CommonUtil.getOIDString(num);
	}

	num = (NumberCode) salesPjt.getAfterSalesState();// 결재 후 프로젝트 상태(예정)
	String afterSalesStateName = "";
	String afterSalesStateOid = "";

	if (num != null) {
	    afterSalesStateName = num.getName();
	    afterSalesStateOid = CommonUtil.getOIDString(num);
	}

	String failtypeName = "";
	String failtypecode = "";

	num = (NumberCode) salesPjt.getFailType(); // 실패유형
	if (num != null) {
	    failtypeName = num.getName();
	    failtypecode = CommonUtil.getOIDString(num);
	}

	KETSalesProject latest = this.getLatestSalesProject(salesPjt);

	if (salesDto.getOid().equals(CommonUtil.getOIDString(latest))) {
	    salesDto.setIsLastRev("ok");
	}
	
	String centerCode = salesPjt.getCenterCode();
	
	if(StringUtils.isNotEmpty(centerCode)){
	    Department center = DepartmentHelper.manager.getDepartment(centerCode);
	    if(center != null){
	    	salesDto.setCenterName(center.getName());
	    }
	}
	
	salesDto.setRankName(rankName);// 중요도
	salesDto.setRankOid(rankOid); // 중요도 oid
	salesDto.setCustomerName(customerName);// 고객사
	salesDto.setCustomerOid(customerOid);// 고객사 oid
	salesDto.setLastBuyerName(lastBuyerName);// 자동차사
	salesDto.setLastBuyerOid(lastBuyerOid);// 자동차사 oid
	salesDto.setProjectName(salesPjt.getProjectName());
	salesDto.setDevTypeName(devTypeName);// 프로젝트유형
	salesDto.setDevTypeOid(devTypeOid);// 프로젝트유형 oid
	salesDto.setApplyArea(salesPjt.getApplyArea());// 적용부
	salesDto.setOemName(oemName);// 차종
	salesDto.setOemOid(oemOid);// 차종 oid
	salesDto.setCompetitorName(competitorName);// 경쟁사
	salesDto.setCompetitorOid(competitorOid);// 경쟁사 oid
	salesDto.setNationName(nationName);// 국가
	salesDto.setNationOid(nationOid);// 국가 oid
	salesDto.setMainCategory(mainCategory);// 제품군대분류oid
	salesDto.setMainCategoryName(mainCategoryName);// 제품군대분류명
	salesDto.setWebEditor((String) salesPjt.getWebEditor());// 영업목표
	salesDto.setWebEditorText((String) salesPjt.getWebEditorText());
	salesDto.setPmdept(pmDept);// 작성자
	salesDto.setPmName(pmName);// 작성부서
	salesDto.setPmOid(pmOid);// 작성자 oid
	salesDto.setRev(rev);// rev
	salesDto.setProjectNo(salesPjt.getProjectNo());
	salesDto.setWorkShopYN(salesPjt.getWorkShopYN());

	salesDto.setFailReason_ModifyType(salesPjt.getFailReason());
	if (StringUtils.isNotEmpty(salesPjt.getFailReason())) {
	    salesDto.setFailReason(salesPjt.getFailReason().replaceAll(System.getProperty("line.separator"), "<br>")); // 실패사유
	}

	salesDto.setFailtypecode(failtypecode);
	salesDto.setFailtypeName(failtypeName);

	salesDto.setProjectResult_ModifyType(salesPjt.getProjectResult());

	if (StringUtils.isNotEmpty(salesPjt.getProjectResult())) {
	    salesDto.setProjectResult((salesPjt.getProjectResult().replaceAll(System.getProperty("line.separator"), "<br>"))); // 프로젝트 결과
	}

	salesDto.setBigo_ModifyType(salesPjt.getBigo());
	if (StringUtils.isNotEmpty(salesPjt.getBigo())) {
	    salesDto.setBigo(salesPjt.getBigo().replaceAll(System.getProperty("line.separator"), "<br>")); // 비고
	}
	num = (NumberCode) salesPjt.getObtainCompanyType();// 수주사

	if (num != null) {
	    String ObtainCompanyName = num.getName();
	    String ObtainCompanyOid = CommonUtil.getOIDString(num);
	    salesDto.setObtainCompanyOid(ObtainCompanyOid);
	    salesDto.setObtainCompanyName(ObtainCompanyName);
	}

	KETMessageService messageService = KETMessageService.getMessageService();

	String VrOid = "VR:" + salesPjt.getPersistInfo().getObjectIdentifier().getClassname() + ":" + salesPjt.getBranchIdentifier();

	String stateName = ((KETSalesProject) CommonUtil.getObject(VrOid)).getState().getState().getDisplay(messageService.getLocale());

	salesDto.setStateName(stateName);
	salesDto.setSalesStateName(salesStateName);
	salesDto.setSalesStateOid(salesStateOid);
	salesDto.setAfterSalesStateName(afterSalesStateName);
	salesDto.setAfterSalesStateOid(afterSalesStateOid);

	WTUser wtUser = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();

	if ((wtUser == salesPjt.getCreator().getPrincipal()) || CommonUtil.isAdmin()) {// Admin 또는 작성자일때 기본정보 Edit or 결재요청 권한 ok
	    salesDto.setBasicEditAuth("ok");
	}
	String isSenior = "no";
	if (getProjectEditAuth2Senior(salesPjt)) {// 작성자의 팀장 또는 센터장은 해당 프로젝트의 작성자와 같은 권한(읽기,쓰기)을 준다 2017.03.06 영업기획 김영환 대리 요청
	    salesDto.setSeniorAuth("ok");
	    isSenior = "ok";
	}

	salesDto.setVrOid(VrOid);// 결재요청시 branchId로 움직이기 위함
	salesDto.setCsYN(salesPjt.getCsYn());
	if (salesPjt.getSop() != null) {
	    salesDto.setSopDate(DateUtil.getDateString(salesPjt.getSop(), "date"));// sop
	}
	// 기본정보 세팅 End
	
    //관련 태그 목록 Start
    List<Map<String, String>> refTagList = TagUtil.manager.getTagMasterList(salesPjt);
    salesDto.setRefTagList(refTagList);
    //관련 태그 목록 End

	KETSalesPartClassLink clzLink = null;
	KETPartClassification partClz = null;

	List<Map<String, String>> partClzList = new ArrayList<Map<String, String>>();

	// 분류체계 리스트 세팅 Start

	QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "partClass", KETSalesPartClassLink.class, false);
	while (inQr.hasMoreElements()) {

	    clzLink = (KETSalesPartClassLink) inQr.nextElement();
	    partClz = (KETPartClassification) clzLink.getPartClass();

	    HashMap<String, String> partClzMap = new HashMap<String, String>();

	    partClzMap.put("clzName", partClz.getClassKrName());
	    partClzMap.put("clzOid", CommonUtil.getOIDString(partClz));
	    partClzMap.put("mainYN", clzLink.getMainYn());
	    if ("Y".equals(clzLink.getMainYn())) {
		salesDto.setMainPartClz((String) partClz.getClassKrName());
	    }
	    partClzMap.put("investCost", clzLink.getInvestCost());
	    partClzMap.put("planTotal", clzLink.getPlanTotal());
	    partClzMap.put("planYear", clzLink.getPlanYear());
	    partClzMap.put("expectSalesTotal", clzLink.getExpectSalesTotal());
	    partClzMap.put("expectSalesYear", clzLink.getExpectSalesYear());

	    partClzList.add(partClzMap);

	}

	// 연계제품프로젝트 리스트 세팅 Start

	KETSalesProjectLink projectLink = null;
	E3PSProjectMaster projectMaster = null;

	List<Map<String, String>> ProductProjectList = new ArrayList<Map<String, String>>();
	HashMap<String, String> ProjectMap = null;

	inQr = PersistenceHelper.manager.navigate(salesPjt, "pjtMaster", KETSalesProjectLink.class, false);

	while (inQr.hasMoreElements()) {

	    projectLink = (KETSalesProjectLink) inQr.nextElement();
	    projectMaster = (E3PSProjectMaster) projectLink.getPjtMaster();

	    ProjectMap = new HashMap<String, String>();

	    ProjectMap.put("pjtNo", projectMaster.getPjtNo());
	    ProjectMap.put("pjtName", projectMaster.getPjtName());

	    ProductProjectList.add(ProjectMap);

	}
	salesDto.setProductProjectList(ProductProjectList);

	// 연계제품프로젝트 리스트 세팅 End

	salesDto.setMainExpectSalesTotal(salesPjt.getExpectSalesTotal()); // main 총매출
	salesDto.setMainExpectSalesYear(salesPjt.getExpectSalesYear()); // main 연간매출

	salesDto.setPartClzList(partClzList);
	
	
	// 프로젝트 참여자 리스트 세팅 Start
	
	KETSalesMemberLink memberLink = null;
	WTUser member = null;

	List<Map<String, String>> memberList = new ArrayList<Map<String, String>>();

	

	QueryResult memQr = PersistenceHelper.manager.navigate(salesPjt, "member", KETSalesMemberLink.class, false);
	while (memQr.hasMoreElements()) {

	    memberLink = (KETSalesMemberLink) memQr.nextElement();
	    
	    member = (WTUser)memberLink.getMember();
	    
	    PeopleData pe = new PeopleData(member);

	    HashMap<String, String> memberMap = new HashMap<String, String>();

	    memberMap.put("name", pe.name );
	    memberMap.put("departmentName", pe.departmentName );
	    memberMap.put("duty", pe.duty );
	    memberMap.put("membserOids", CommonUtil.getOIDString(member));

	    memberList.add(memberMap);
	}
	salesDto.setMemberList(memberList);
	
	// 프로젝트 참여자 리스트 세팅 End

	// 영업추진현황 리스트 세팅 Start

	KETSalesTaskLink taskLink = null;
	KETSalesTask task = null;
	KETSalesTaskHistoryLink historyLink = null;
	List<Map<String, String>> salesTaskList = new ArrayList<Map<String, String>>();
	int historyCnt = 0;
	QueryResult inQr2 = PersistenceHelper.manager.navigate(salesPjt, "task", KETSalesTaskLink.class, false);
	// QueryResult inQr3 = null;
	QueryResult inQr3 = null;
	String isTaskEditable = "no";

	while (inQr2.hasMoreElements()) {

	    taskLink = (KETSalesTaskLink) inQr2.nextElement();
	    task = taskLink.getTask();

	    // Task(step) 변경 권한 체크 - 접속유저가 step의 담당자면 해당 step에 대해서, 그리고 작성자, wcadmin, 해당 작성부서의 센터장 이상 권한은 모든 task 수정가능

	    if ((wtUser.getPrincipalIdentifier().equals(salesPjt.getCreator().getPrincipal().getPrincipalIdentifier()))
		    || (wtUser.getPrincipalIdentifier().equals(task.getCollaboManager().getPrincipalIdentifier())) || CommonUtil.isAdmin()
		    || "ok".equals(isSenior)) {
		isTaskEditable = "ok";
		salesDto.setTaskEdit("ok");
	    } else {
		isTaskEditable = "no";
	    }

	    HashMap<String, String> salesTaskMap = new HashMap<String, String>();

	    salesTaskMap.put("subject", task.getSubject().replaceAll(System.getProperty("line.separator"), "<br>"));
	    salesTaskMap.put("subjectCode", task.getSalesSubjectCode());
	    salesTaskMap.put("salesPlan", task.getSalesPlan());
	    salesTaskMap.put("planDate", DateUtil.getDateString(task.getPlanDate(), "date"));
	    salesTaskMap.put("resultDate", DateUtil.getDateString(task.getResultDate(), "date"));
	    salesTaskMap.put("planCheck", task.getPlanCheck());
	    WTUser user = (WTUser) task.getCollaboManager();
	    salesTaskMap.put("userName", user.getFullName());
	    salesTaskMap.put("userOid", CommonUtil.getOIDString(user));
	    Department dept = (Department) task.getCollaboTeam();
	    salesTaskMap.put("deptName", dept.getName());

	    salesTaskMap.put("propelwebEditor", (String) task.getPropelwebEditor());
	    salesTaskMap.put("propelwebEditorText", (String) task.getPropelwebEditorText());
	    salesTaskMap.put("problemwebEditor", (String) task.getProblemwebEditor());
	    salesTaskMap.put("problemwebEditorText", (String) task.getProblemwebEditorText());
	    salesTaskMap.put("planwebEditor", (String) task.getPlanwebEditor());
	    salesTaskMap.put("planwebEditorText", (String) task.getPlanwebEditorText());
	    salesTaskMap.put("tempplanDate", StringUtils.remove(DateUtil.getDateString(task.getPlanDate(), "date"), "-"));
	    salesTaskMap.put("stepNo", task.getStepNo());
	    salesTaskMap.put("taskOid", CommonUtil.getOIDString(task));
	    salesTaskMap.put("isTaskEditable", isTaskEditable);
	    salesTaskMap.put("taskType", task.getTaskType());

	    inQr3 = PersistenceHelper.manager.navigate(task, "Taskhistory", KETSalesTaskHistoryLink.class, false);
	    while (inQr3.hasMoreElements()) {
		historyCnt++;
		historyLink = (KETSalesTaskHistoryLink) inQr3.nextElement();
	    }
	    salesTaskMap.put("historyCnt", Integer.toString(historyCnt));
	    historyCnt = 0;
	    salesTaskList.add(salesTaskMap);
	    /*
	     * inQr3 = PersistenceHelper.manager.navigate(task, "issue", KETSalesIssueLink.class, false);
	     * 
	     * while (inQr3.hasMoreElements()) {
	     * 
	     * issueLink = (KETSalesIssueLink) inQr2.nextElement(); issue = issueLink.getIssue();
	     * 
	     * salesTaskMap.put("propelwebEditor", (String) issue.getPropelwebEditor()); salesTaskMap.put("propelwebEditorText", (String)
	     * issue.getPropelwebEditorText()); salesTaskMap.put("problemwebEditor", (String) issue.getProblemwebEditor());
	     * salesTaskMap.put("problemwebEditorText", (String) issue.getProblemwebEditorText()); salesTaskMap.put("planwebEditor",
	     * (String) issue.getPlanwebEditor()); salesTaskMap.put("planwebEditorText", (String) issue.getPlanwebEditorText()); }
	     */
	}

	Collections.sort(salesTaskList, ComparatorUtil.SALESPROJECTSTEP);

	salesDto.setSalesTaskList(salesTaskList);

	salesDto.setSalesPjt(salesPjt);

	List<Map<String, Object>> taksLineUpList = dao.getTaskLineUpList(CommonUtil.getOIDLongValue2Str(salesDto.getOid()), "");

	if (taksLineUpList.size() == 0) {
	    taksLineUpList = dao.getTaskLineUpList(CommonUtil.getOIDLongValue2Str(salesDto.getOid()), "Y");
	}

	Collections.sort(taksLineUpList, ComparatorUtil.SALESPROJECTSTEP);

	/*
	 * int i=1;
	 * 
	 * boolean isBasic = false; String gubun = ""; String planDate = ""; for(Map<String, Object> basicCheck : taksLineUpList){
	 * 
	 * if(i<=15){ gubun = (String)basicCheck.get("gubun"); if("BASIC".equals(gubun)){ isBasic = true; } }else{
	 * if("BASIC".equals(gubun)){ planDate = (String)basicCheck.get("planDate"); break; } } i++; } if(!isBasic){ Map<String, Object>
	 * taskLineUp = new HashMap<String, Object>(); taskLineUp.put("planDate", planDate);
	 * 
	 * taksLineUpList.set(14,taskLineUp); }
	 */
	salesDto.setTaksLineUpList(taksLineUpList);

	String mainSignal = dao.searchMainCheck(CommonUtil.getOIDLongValue2Str(salesDto.getOid()));

	String temp[] = mainSignal.split("\\|");
	mainSignal = temp[0];
	String ObjectKey = temp[1];
	if (StringUtils.isEmpty(mainSignal) || mainSignal == null || "null".equals(mainSignal)) {
	    mainSignal = "none";
	}
	KETSalesTask TaskEditortarget = (KETSalesTask) CommonUtil.getObject(ObjectKey);

	if (TaskEditortarget != null) {// 실적일자가 기입되지 않은경우
	    salesDto.setPropelwebEditor_1((String) TaskEditortarget.getPropelwebEditor());
	    salesDto.setPropelwebEditorText_1((String) TaskEditortarget.getPropelwebEditorText());
	    salesDto.setProblemwebEditor_1((String) TaskEditortarget.getProblemwebEditor());
	    salesDto.setProblemwebEditorText_1((String) TaskEditortarget.getProblemwebEditorText());
	    salesDto.setPlanwebEditor_1((String) TaskEditortarget.getPlanwebEditor());
	    salesDto.setPlanwebEditorText_1((String) TaskEditortarget.getPlanwebEditorText());
	}
	salesDto.setMainSignal(mainSignal);
	return salesDto;

    }

    @Override
    public KETSalesProjectDTO salesProjectRevise(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String oid = paramObject.getOid();
	Versioned newVs = null;
	Versioned vs = (Versioned) CommonUtil.getObject(oid);

	if (!VersionHelper.isLatestRevision(vs)) {
	    throw new Exception("최신 버전이 아닙니다!");
	}
	String lifecycle = ((LifeCycleManaged) vs).getLifeCycleName();
	Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
	newVs = VersionControlHelper.service.newVersion(vs);
	FolderHelper.assignLocation((FolderEntry) newVs, folder);
	KETSalesProject sproject = (KETSalesProject) newVs;
	sproject.setSalesState(NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "I"));// 진행상태로 변경
	// LifeCycleHelper.setLifeCycle(sproject, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef())); //
	// Lifecycle
	sproject = (KETSalesProject) PersistenceHelper.manager.save(sproject);
	oid = CommonUtil.getOIDString(sproject);
	paramObject.setOid(oid);

	return paramObject;
    }

    @Override
    public List<KETSalesProject> find(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    public StatementSpec getSalesCompanyLinkJoinQuery(QuerySpec spec, KETSalesProjectDTO paramObject) throws Exception {

	String custCompany = paramObject.getCustomerCode();
	String buyerCompany = paramObject.getLastBuyerCode();

	Class<KETSalesCompanyLink> companyLinkClass = KETSalesCompanyLink.class;
	int pl_idx = 0;
	if (StringUtils.isNotEmpty(custCompany) || StringUtils.isNotEmpty(buyerCompany)) {
	    pl_idx = spec.addClassList(companyLinkClass, false);
	}

	if (StringUtils.isNotEmpty(custCompany)) {

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(KETSalesProject.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(companyLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
		    spec.getFromClause().getPosition(KETSalesProject.class), pl_idx });

	    if (StringUtil.checkString(custCompany)) {
		String[] companys = custCompany.split(",");
		if (companys.length > 1) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    for (int i = 0; i < companys.length; i++) {
			spec.appendWhere(
			        new SearchCondition(companyLinkClass, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(companys[i])),
			        new int[] { pl_idx });

			if (i != companys.length - 1)
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		} else {
		    SearchUtil
			    .appendEQUAL(spec, companyLinkClass, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(companys[0]), pl_idx);
		}
	    }
	}

	if (StringUtils.isNotEmpty(buyerCompany)) {

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(KETSalesProject.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(companyLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
		    spec.getFromClause().getPosition(KETSalesProject.class), pl_idx });

	    if (StringUtil.checkString(buyerCompany)) {
		String[] companys = custCompany.split(",");
		if (companys.length > 1) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    for (int i = 0; i < companys.length; i++) {
			spec.appendWhere(
			        new SearchCondition(companyLinkClass, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(companys[i])),
			        new int[] { pl_idx });

			if (i != companys.length - 1)
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		} else {
		    SearchUtil
			    .appendEQUAL(spec, companyLinkClass, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(companys[0]), pl_idx);
		}
	    }
	}

	return spec;
    }

    public StatementSpec getSalesPartLinkJoinQuery(QuerySpec spec, KETSalesProjectDTO paramObject) throws Exception {

	if (StringUtils.isNotEmpty(paramObject.getPartClazOid())) {
	    String partClzOid = paramObject.getPartClazOid();
	    Class<KETSalesPartClassLink> partjLinkClass = KETSalesPartClassLink.class;
	    // Class<KETPartClassification> partClass = KETPartClassification.class;

	    int pl_idx = spec.addClassList(partjLinkClass, false);
	    // int p_idx = spec.addClassList(partClass, false);

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(new ClassAttribute(KETSalesProject.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(partjLinkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)),
		    new int[] { spec.getFromClause().getPosition(KETSalesProject.class), pl_idx });

	    if (StringUtil.checkString(partClzOid)) {
		String[] partClzOids = partClzOid.split(",");
		if (partClzOids.length > 1) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    for (int i = 0; i < partClzOids.length; i++) {
			spec.appendWhere(
			        new SearchCondition(partjLinkClass, "roleBObjectRef.key.id", "=", CommonUtil
			                .getOIDLongValue(partClzOids[i])), new int[] { pl_idx });

			if (i != partClzOids.length - 1)
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		} else {
		    SearchUtil.appendEQUAL(spec, partjLinkClass, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(partClzOids[0]),
			    pl_idx);
		}
	    }

	    /*
	     * if (spec.getConditionCount() > 0) spec.appendAnd(); spec.appendWhere(new SearchCondition(new ClassAttribute(partClass,
	     * WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute( partjLinkClass, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] {
	     * p_idx, pl_idx });
	     */
	    // SearchUtil.appendLIKE(spec, partClass, "classCode", "A", p_idx);
	    // SearchUtil.appendEQUAL(spec, partClass, "classCode", "A", p_idx);
	}

	return spec;
    }

    public QuerySpec getClosePjtQuery(QuerySpec spec) throws Exception {
	StatementSpec Lastquery = spec;
	if (!Lastquery.isAdvancedQueryEnabled()) {
	    Lastquery.setAdvancedQueryEnabled(true);
	}

	QueryResult mainQr = PersistenceHelper.manager.find(Lastquery);
	String pjtNo = "";

	while (mainQr.hasMoreElements()) {

	    boolean projectYN = true;
	    Object[] objArr = (Object[]) mainQr.nextElement();
	    QueryResult inQr = PersistenceHelper.manager
		    .navigate((KETSalesProject) objArr[0], "pjtMaster", KETSalesProjectLink.class, true);
	    if (inQr.size() == 0) {
		projectYN = false;
	    }
	    while (inQr.hasMoreElements()) {
		E3PSProjectMaster master = (E3PSProjectMaster) inQr.nextElement();
		E3PSProject project = ProjectHelper.manager.getProject(master.getPjtNo());
		if (!"COMPLETED".equals(project.getState().toString())) {
		    projectYN = false;
		}
	    }
	    if (!projectYN) {
		pjtNo += ((KETSalesProject) objArr[0]).getProjectNo() + ",";
	    }

	}
	if (StringUtils.isNotEmpty(pjtNo)) {

	    pjtNo = StringUtils.removeEnd(pjtNo, ",");

	    String[] pjtNos = pjtNo.split(",");
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(KETSalesProject.class, KETSalesProject.PROJECT_NO, pjtNos, false, true), new int[] { 0 });

	}
	return spec;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 19. 오전 11:19:27
     * @method getKetTagLinkJoinQuery
     * @param spec
     * @param paramObject
     * @return StatementSpec
     * @throws Exception 
     * </pre>
     */
    public StatementSpec getKetTagLinkJoinQuery(QuerySpec spec, KETSalesProjectDTO paramObject) throws Exception {
        
        String refTag[] = paramObject.getRefTag();

        Class<KETTagLink> linkClass = KETTagLink.class;
        int idx = 0;
        if (refTag != null && refTag.length > 0 && StringUtils.isNotEmpty(refTag[0])) {
            
            NumberCode code = NumberCodeHelper.manager.getNumberCode("KETTAG", refTag[0]);
            
            idx = spec.addClassList(linkClass, false);

            if (spec.getConditionCount() > 0) spec.appendAnd();
            
            spec.appendWhere(new SearchCondition(new ClassAttribute(KETSalesProject.class, WTAttributeNameIfc.ID_NAME), "=",
                new ClassAttribute(linkClass, WTAttributeNameIfc.ROLEA_OBJECT_ID)), new int[] {
            spec.getFromClause().getPosition(KETSalesProject.class), idx });

            SearchUtil.appendEQUAL(spec, linkClass, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(code), idx);
        }

        return spec;
    }
    

    @Override
    public PageControl findPaging(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	QuerySpec query = getMainQuery(paramObject);

	QuerySpec spec1 = (QuerySpec) query.clone();
	spec1 = (QuerySpec) getSalesPartLinkJoinQuery(spec1, paramObject);
	spec1 = (QuerySpec) getSalesCompanyLinkJoinQuery(spec1, paramObject);
	spec1 = (QuerySpec) getKetTagLinkJoinQuery(spec1, paramObject);

	StatementSpec statementSpec = spec1;

	QuerySpec spec = new QuerySpec();

	int idx = 0;

	idx = spec.addClassList(KETSalesProject.class, true);

	TableColumn masterOidAttr = new TableColumn(spec.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");

	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr);

	listExpression.addColumn(versionAttr);

	SubSelectExpression selectExpression = new SubSelectExpression(statementSpec);
	selectExpression.setAccessControlRequired(false);
	spec.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	// latest iteration
	SearchUtil.appendBOOLEAN(spec, KETSalesProject.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);

	if ("Y".equals(paramObject.getCloseProjectYN())) {// 프로젝트 종결 조건 : 수주성공이면서 연계된 개발 프로젝트가 모두 완료된경우 종결로 본다
	    spec = getClosePjtQuery(spec);
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("projectNo") || checkName.equals("-projectNo")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.PROJECT_NO, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.PROJECT_NO, false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("projectName") || checkName.equals("-projectName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.PROJECT_NAME, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.PROJECT_NAME, false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("salesStateName") || checkName.equals("-salesStateName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "salesStateReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "salesStateReference.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("stateName") || checkName.equals("-stateName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.LIFE_CYCLE_STATE, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.LIFE_CYCLE_STATE, false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("pmName") || checkName.equals("-pmName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "iterationInfo.creator.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "iterationInfo.creator.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("createDateFrom") || checkName.equals("-createDateFrom")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.CREATE_TIMESTAMP, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.CREATE_TIMESTAMP, false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("sopDate") || checkName.equals("-sopDate")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.SOP, true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, KETSalesProject.SOP, false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("rankName") || checkName.equals("-rankName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "rankTypeReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "rankTypeReference.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("devTypeName") || checkName.equals("-devTypeName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "devTypeReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "devTypeReference.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("nationName") || checkName.equals("-nationName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "nationTypeReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "nationTypeReference.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("oemName") || checkName.equals("-oemName")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "oemTypeReference.key.id", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "oemTypeReference.key.id", false);
		}
	    }
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();
	    if (checkName.equals("rev") || checkName.equals("-rev")) {
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "versionInfo.identifier.versionId", true);
		} else {
		    SearchUtil.setOrderBy(spec, KETSalesProject.class, idx, "versionInfo.identifier.versionId", false);
		}
	    }
	}

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");

	Kogger.debug(getClass(), spec);

	StatementSpec Lastquery = spec;
	if (!Lastquery.isAdvancedQueryEnabled())
	    Lastquery.setAdvancedQueryEnabled(true);
	if (Lastquery != null) {
	    if (StringUtil.isEmpty(pageSessionId)) {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), Lastquery);
	    } else {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		        pageSessionId);

	    }
	}

	/*
	 * if (StringUtil.isEmpty(pageSessionId)) { pageControl = CommonSearchHelper.find(paramObject.getFormPage(),
	 * paramObject.getFormPage(), spec); } else { pageControl = CommonSearchHelper.find(paramObject.getFormPage(),
	 * paramObject.getFormPage(), paramObject.getPage() + 1, pageSessionId); }
	 */
	return pageControl;
    }

    private QuerySpec getMainQuery(KETSalesProjectDTO paramObject) throws Exception {
	QuerySpec spec = new QuerySpec();

	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETSalesProject> iterClass = KETSalesProject.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);
	// Join WTDocumentMaster - KETSalesProject
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });

	String projectNo = paramObject.getProjectNo();
	if (StringUtils.isNotEmpty(projectNo)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETSalesProject.PROJECT_NO, projectNo, i_idx);
	}

	String projectName = paramObject.getProjectName();
	if (StringUtils.isNotEmpty(projectName)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETSalesProject.PROJECT_NAME, projectName, i_idx);
	}

	// 부서
	String pmdept = paramObject.getPmdept();
	pmdept = StringUtils.remove(pmdept, "e3ps.groupware.company.Department:");
	if (StringUtil.checkString(pmdept)) {
	    String[] dept = pmdept.split(",");

	    long[] dept_ = new long[dept.length];
	    for (int i = 0; i < dept.length; i++) {
		dept_[i] = Long.parseLong(dept[i]);
	    }
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "salesTeamReference.key.id", dept_, false), new int[] { i_idx });
	}

	String rev = paramObject.getRev();// 리비전

	if (StringUtil.checkString(rev)) {

	    if (StringUtils.contains(rev, ".")) {
		String temp[] = rev.split("\\.");
		SearchUtil.appendEQUAL(spec, iterClass, "versionInfo.identifier.versionId", temp[0], i_idx);
		SearchUtil.appendEQUAL(spec, iterClass, "iterationInfo.identifier.iterationId", temp[1], i_idx);
	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "versionInfo.identifier.versionId", rev, i_idx);
	    }

	}

	String rankOid = paramObject.getRankOid();// 중요도
	if (StringUtil.checkString(rankOid)) {
	    String[] rankOids = rankOid.split(",");
	    if (rankOids.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		for (int i = 0; i < rankOids.length; i++) {
		    spec.appendWhere(new SearchCondition(iterClass, "rankTypeReference.key.id", "=", Long.parseLong(rankOids[i])),
			    new int[] { i_idx });

		    if (i != rankOids.length - 1)
			spec.appendOr();
		}
		spec.appendCloseParen();
	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "rankTypeReference.key.id", Long.parseLong(rankOids[0]), i_idx);
	    }
	}

	String developType = paramObject.getDevelopType();// 개발유형
	if (StringUtil.checkString(developType)) {
	    String[] developTypes = developType.split(",");
	    if (developTypes.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		for (int i = 0; i < developTypes.length; i++) {
		    spec.appendWhere(new SearchCondition(iterClass, "devTypeReference.key.id", "=", Long.parseLong(developTypes[i])),
			    new int[] { i_idx });

		    if (i != developTypes.length - 1)
			spec.appendOr();
		}
		spec.appendCloseParen();
	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "devTypeReference.key.id", Long.parseLong(developTypes[0]), i_idx);
	    }
	}

	String applyArea = paramObject.getApplyArea();// 적용부
	if (StringUtils.isNotEmpty(applyArea)) {
	    SearchUtil.appendLIKE(spec, iterClass, KETSalesProject.APPLY_AREA, applyArea, i_idx);
	}

	// sop
	String sopDateFrom = paramObject.getSopStartDate();
	if (StringUtil.checkString(sopDateFrom)) {
	    SearchUtil.appendTimeFrom(spec, iterClass, "sop", DateUtil.convertStartDate2(sopDateFrom), i_idx);
	}
	String sopDateTo = paramObject.getSopEndDate();
	if (StringUtil.checkString(sopDateTo)) {
	    SearchUtil.appendTimeTo(spec, iterClass, "sop", DateUtil.convertEndDate2(sopDateTo), i_idx);
	}

	String nation = paramObject.getNation();// 국가
	if (StringUtil.checkString(nation)) {
	    String[] nations = nation.split(",");
	    if (nations.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		for (int i = 0; i < nations.length; i++) {
		    spec.appendWhere(new SearchCondition(iterClass, "nationTypeReference.key.id", "=", Long.parseLong(nations[i])),
			    new int[] { i_idx });

		    if (i != nations.length - 1)
			spec.appendOr();
		}
		spec.appendCloseParen();
	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "nationTypeReference.key.id", Long.parseLong(nations[0]), i_idx);
	    }
	}
	// 경쟁사
	String competitorCompany = paramObject.getCompetitorCompany();
	if (StringUtil.checkString(competitorCompany)) {
	    String[] competitorCompanys = competitorCompany.split(",");
	    if (competitorCompanys.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		for (int i = 0; i < competitorCompanys.length; i++) {
		    spec.appendWhere(
			    new SearchCondition(iterClass, "competitorReference.key.id", "=", Long.parseLong(competitorCompanys[i])),
			    new int[] { i_idx });

		    if (i != competitorCompanys.length - 1)
			spec.appendOr();
		}
		spec.appendCloseParen();
	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "competitorReference.key.id", Long.parseLong(competitorCompanys[0]), i_idx);
	    }
	}
	// 차종
	String model = paramObject.getModel();
	if (StringUtil.checkString(model)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "oemTypeReference.key.id", "=", CommonUtil.getOIDLongValue(model)),
		    new int[] { i_idx });
	}

	// 작성일자
	String createDateFrom = paramObject.getCreateDateFrom();
	if (StringUtil.checkString(createDateFrom)) {
	    SearchUtil.appendTimeFrom(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertStartDate2(createDateFrom), i_idx);
	}

	// 작성자
	String creator = paramObject.getPmOid();
	if (StringUtil.checkString(creator)) {
	    SearchUtil.appendEQUAL(spec, iterClass, "iterationInfo.creator.key.id", CommonUtil.getOIDLongValue(creator), i_idx);
	}

	String createDateTo = paramObject.getCreateDateTo();
	if (StringUtil.checkString(createDateTo)) {
	    SearchUtil.appendTimeTo(spec, iterClass, "thePersistInfo.createStamp", DateUtil.convertEndDate2(createDateTo), i_idx);
	}

	List<String> arr = new ArrayList<String>();

	// 결재상태
	String state = paramObject.getStateCode();
	if (StringUtil.checkString(state)) {
	    String[] states = state.split(",");
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(iterClass, "state.state", states, true, false), new int[] { i_idx });
	}

	// 프로젝트 상태
	String salesStateOid = paramObject.getSalesStateOid();
	if (StringUtil.checkString(salesStateOid)) {
	    String[] salesStateOids = salesStateOid.split(",");

	    if (salesStateOids.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		long arrLongOid[] = new long[salesStateOids.length];
		for (int i = 0; i < salesStateOids.length; i++) {
		    arrLongOid[i] = Long.parseLong(salesStateOids[i]);
		}
		spec.appendWhere(new SearchCondition(iterClass, "salesStateReference.key.id", arrLongOid, false), new int[] { i_idx });

	    } else {
		SearchUtil.appendEQUAL(spec, iterClass, "salesStateReference.key.id", Long.parseLong(salesStateOids[0]), i_idx);
	    }

	}

	if ("Y".equals(paramObject.getCloseProjectYN())) {// 프로젝트 종결 여부
	    NumberCode num = NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "S");
	    if (num != null) {
		SearchUtil.appendEQUAL(spec, iterClass, "salesStateReference.key.id", CommonUtil.getOIDLongValue(num), i_idx);
	    }
	}

	// 대분류 제품군
	String mainCategory = paramObject.getMainCategory();
	if (StringUtil.checkString(mainCategory)) {
	    String[] mainCategorys = mainCategory.split(",");

	    if (mainCategorys.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		long arrLongOid[] = new long[mainCategorys.length];
		for (int i = 0; i < mainCategorys.length; i++) {
		    arrLongOid[i] = Long.parseLong(mainCategorys[i]);
		}
		spec.appendWhere(new SearchCondition(iterClass, "mainCategoryTypeReference.key.id", arrLongOid, false), new int[] { i_idx });

	    } else {

		SearchUtil.appendEQUAL(spec, iterClass, "mainCategoryTypeReference.key.id", Long.parseLong(mainCategorys[0]), i_idx);
	    }

	}

	// 실패유형
	String failtypecode = paramObject.getFailtypecode();
	if (StringUtil.checkString(failtypecode)) {
	    String[] failtypecodes = failtypecode.split(",");

	    if (failtypecodes.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		long arrLongOid[] = new long[failtypecodes.length];
		for (int i = 0; i < failtypecodes.length; i++) {
		    arrLongOid[i] = Long.parseLong(failtypecodes[i]);
		}
		spec.appendWhere(new SearchCondition(iterClass, "failTypeReference.key.id", arrLongOid, false), new int[] { i_idx });

	    } else {

		SearchUtil.appendEQUAL(spec, iterClass, "failTypeReference.key.id", Long.parseLong(failtypecodes[0]), i_idx);
	    }

	}

	// 수주사
	String obtainCompany = paramObject.getObtainCompany();
	if (StringUtil.checkString(obtainCompany)) {
	    String[] obtainCompanys = obtainCompany.split(",");

	    if (obtainCompanys.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		long arrLongOid[] = new long[obtainCompanys.length];
		for (int i = 0; i < obtainCompanys.length; i++) {
		    arrLongOid[i] = Long.parseLong(obtainCompanys[i]);
		}
		spec.appendWhere(new SearchCondition(iterClass, "obtainCompanyTypeReference.key.id", arrLongOid, false),
		        new int[] { i_idx });

	    } else {

		SearchUtil.appendEQUAL(spec, iterClass, "obtainCompanyTypeReference.key.id", Long.parseLong(obtainCompanys[0]), i_idx);
	    }

	}

	// 수정일자
	String modifyDateFrom = paramObject.getCsStartDate();// dto 변수추가하기 어려운 시점이어서 그냥 csStartDate 씀.. 이지스에서 수주 성공 데이터 조회시 사용
	if (StringUtil.checkString(modifyDateFrom)) {
	    SearchUtil.appendTimeFrom(spec, iterClass, "approveDate", DateUtil.convertStartDate2(modifyDateFrom), i_idx);
	}

	String modifyDateTo = paramObject.getCsEndDate();
	if (StringUtil.checkString(modifyDateTo)) {
	    SearchUtil.appendTimeTo(spec, iterClass, "approveDate", DateUtil.convertEndDate2(modifyDateTo), i_idx);
	}
	
	
	if ("Y".equals(paramObject.getWorkShopYN())) {// WorkShop여부
	    SearchUtil.appendEQUAL(spec, iterClass, "workShopYN", "Y", i_idx);
	}

	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	    TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO");
	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	    spec.appendSelect(function, new int[] { i_idx }, false);
	}

	if (spec.getGroupByClause() == null) {
	    // Group by
	    // spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false);
	    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
	}

	// latest iteration

	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);
	spec.setDescendantQuery(false);

	return spec;
    }

    @Override
    public KETSalesProject save(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETSalesProject modify(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETSalesProject delete(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETSalesProject getLatestSalesProject(KETSalesProject paramObject) throws Exception {
	// TODO Auto-generated method stub
	if (paramObject == null)
	    return null;

	KETSalesProject latest = null;
	QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf(paramObject);

	while (qr.hasMoreElements()) {

	    KETSalesProject obj = ((KETSalesProject) qr.nextElement());

	    if (latest == null || obj.getVersionIdentifier().getSeries().greaterThan(latest.getVersionIdentifier().getSeries())) {
		latest = obj;
	    }
	}

	if (latest != null)
	    return (KETSalesProject) VersionControlHelper.getLatestIteration(latest, false);
	else
	    return latest;

    }

    @Override
    public void copyProjectChild(KETSalesProject source, KETSalesProject target) throws Exception {
	// TODO Auto-generated method stub

	// 1. 고객사,자동차사,경쟁사 복사
	KETSalesCompanyLink CompanyLink = null;

	QueryResult qr = PersistenceHelper.manager.navigate(source, "company", KETSalesCompanyLink.class, false);
	while (qr.hasMoreElements()) {
	    CompanyLink = (KETSalesCompanyLink) qr.nextElement();
	    NumberCode company = CompanyLink.getCompany();

	    KETSalesCompanyLink newLink = KETSalesCompanyLink.newKETSalesCompanyLink(target, company);
	    newLink.setCodeType(CompanyLink.getCodeType());
	    PersistenceServerHelper.manager.insert(newLink);
	}

	// 2. 제품분류 복사
	KETSalesPartClassLink ClzLink = null;

	qr = PersistenceHelper.manager.navigate(source, "partClass", KETSalesPartClassLink.class, false);
	while (qr.hasMoreElements()) {
	    ClzLink = (KETSalesPartClassLink) qr.nextElement();
	    KETPartClassification partClass = ClzLink.getPartClass();

	    KETSalesPartClassLink newLink = KETSalesPartClassLink.newKETSalesPartClassLink(target, partClass);
	    newLink.setExpectSalesTotal(ClzLink.getExpectSalesTotal());
	    newLink.setExpectSalesYear(ClzLink.getExpectSalesYear());
	    newLink.setInvestCost(ClzLink.getInvestCost());
	    newLink.setMainYn(ClzLink.getMainYn());
	    newLink.setPlanTotal(ClzLink.getPlanTotal());
	    newLink.setPlanYear(ClzLink.getPlanYear());

	    PersistenceHelper.manager.save(newLink);

	}

	// 3. Step 복사
	KETSalesTaskLink TaskLink = null;
	KETSalesTaskLink newTaskLink = null;
	KETSalesTask task = null;
	KETSalesTask newTask = null;
	qr = PersistenceHelper.manager.navigate(source, "task", KETSalesTaskLink.class, false);
	while (qr.hasMoreElements()) {
	    TaskLink = (KETSalesTaskLink) qr.nextElement();
	    task = TaskLink.getTask();

	    newTask = KETSalesTask.newKETSalesTask();
	    newTask.setSubject(task.getSubject());
	    newTask.setSalesSubjectCode(task.getSalesSubjectCode());
	    newTask.setPlanCheck(task.getPlanCheck());
	    newTask.setPlanDate(task.getPlanDate());

	    newTask.setResultDate(task.getResultDate());

	    newTask.setSalesPlan(task.getSalesPlan());
	    newTask.setCollaboManager(task.getCollaboManager());
	    newTask.setCollaboTeam(task.getCollaboTeam());

	    newTask.setPropelwebEditor(task.getPropelwebEditor());
	    newTask.setPropelwebEditorText(task.getPropelwebEditorText());
	    newTask.setProblemwebEditor(task.getProblemwebEditor());
	    newTask.setProblemwebEditorText(task.getProblemwebEditorText());
	    newTask.setPlanwebEditor(task.getPlanwebEditor());
	    newTask.setPlanwebEditorText(task.getPlanwebEditorText());
	    newTask.setStepNo(task.getStepNo());

	    PersistenceHelper.manager.save(newTask);

	    newTaskLink = KETSalesTaskLink.newKETSalesTaskLink(target, newTask);
	    PersistenceHelper.manager.save(newTaskLink);

	    this.historyRenew(task, newTask);
	}
	
	// 4. 참여자 복사
	KETSalesMemberLink memLink = null;
	KETSalesMemberLink newMemLink = null;
		
	qr = PersistenceHelper.manager.navigate(source, "member", KETSalesMemberLink.class, false);
	while (qr.hasMoreElements()) {
	    memLink = (KETSalesMemberLink) qr.nextElement();
	    newMemLink = KETSalesMemberLink.newKETSalesMemberLink(target, memLink.getMember());
	    PersistenceHelper.manager.save(newMemLink);
	}
    }

    public void historyRenew(KETSalesTask source, KETSalesTask target) throws Exception {
	// 최신 타스크로 이력관리위해 history 갱신
	QueryResult qr = PersistenceHelper.manager.navigate(source, "Taskhistory", KETSalesTaskHistoryLink.class, false);
	KETSalesTaskHistoryLink historyLink = null;
	while (qr.hasMoreElements()) {
	    historyLink = (KETSalesTaskHistoryLink) qr.nextElement();
	    historyLink.setOrgTask(target);
	    PersistenceHelper.manager.save(historyLink);
	}
    }

    public void setSortedTask(KETSalesProject salesPjt) throws Exception {
	List<Map<String, String>> salesTaskList = new ArrayList<Map<String, String>>();

	QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "task", KETSalesTaskLink.class, false);
	// QueryResult inQr3 = null;

	KETSalesTaskLink taskLink = null;
	KETSalesTask task = null;
	while (inQr.hasMoreElements()) {

	    taskLink = (KETSalesTaskLink) inQr.nextElement();
	    task = taskLink.getTask();

	    HashMap<String, String> salesTaskMap = new HashMap<String, String>();

	    salesTaskMap.put("oid", CommonUtil.getOIDString(task));
	    salesTaskMap.put("tempplanDate", StringUtils.remove(DateUtil.getDateString(task.getPlanDate(), "date"), "-"));
	    salesTaskList.add(salesTaskMap);
	}
	Collections.sort(salesTaskList, ComparatorUtil.SALESPROJECTDATESORT);

	KETSalesTask step_task = null;
	int sortNo = 1;

	for (Map<String, String> step : salesTaskList) {
	    step_task = (KETSalesTask) CommonUtil.getObject((String) step.get("oid"));
	    step_task.setStepNo(Integer.toString(sortNo));
	    PersistenceServerHelper.manager.update(step_task);
	    sortNo++;
	}

    }

    public KETSalesCSMeetingApproval createCSapproval() throws Exception {
	KETSalesCSMeetingApproval manageApproval = KETSalesCSMeetingApproval.newKETSalesCSMeetingApproval();// 결재관련 테이블 생성
	String folderPath = "/Default/자동차사업부/초기유동/";
	WTContainerRef containerRef = WCUtil.getWTContainerRef();

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) manageApproval, folder);
	manageApproval = (KETSalesCSMeetingApproval) PersistenceHelper.manager.save(manageApproval);
	return manageApproval;
    }

    public HashMap<String, Object> getBeforeDeptSort(KETSalesCSMeetingManage manage, String detpSort) throws Exception {

	HashMap<String, Object> rtvl = new HashMap<String, Object>();

	int beforeSort = 0;
	KETSalesCSMeetingApproval manageApproval = null;

	QuerySpec query = new QuerySpec();
	int idx = query.appendClassList(KETSalesCSMeetingApproval.class, true);
	query.appendWhere(new SearchCondition(KETSalesCSMeetingApproval.class, "meetingManageLinkInfo", "=",
	        "ext.ket.sales.entity.KETSalesCSMeetingManage:" + CommonUtil.getOIDLongValue2Str(manage)), new int[] { idx });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETSalesCSMeetingApproval.class, "deptSort", "=", detpSort), new int[] { idx });

	StatementSpec Lastquery = query;
	QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	while (qr.hasMoreElements()) {// 이미 해당부서 데이터가 존재하면 sortNo를 기존데이터에 맞춰서 증가한다
	    Object[] obj = (Object[]) qr.nextElement();
	    manageApproval = (KETSalesCSMeetingApproval) obj[0];
	}

	if (manageApproval != null) {
	    QuerySpec query2 = new QuerySpec();
	    int idx2 = query2.appendClassList(KETSalesCSMeetingManageLink.class, true);
	    query2.appendWhere(
		    new SearchCondition(KETSalesCSMeetingManageLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(manage)),
		    new int[] { idx2 });
	    query2.appendAnd();
	    query2.appendWhere(
		    new SearchCondition(KETSalesCSMeetingManageLink.class, "csMeetingApproveReference.key.id", "=", CommonUtil
		            .getOIDLongValue(manageApproval)), new int[] { idx2 });

	    StatementSpec Lastquery2 = query2;
	    QueryResult qr2 = PersistenceHelper.manager.find(Lastquery2);

	    beforeSort = qr2.size();

	}

	rtvl.put("beforeSort", beforeSort);
	rtvl.put("manageApproval", CommonUtil.getOIDString(manageApproval));

	return rtvl;
    }

    public KETSalesCSMeetingApproval meetingApprovalSetLifeCycle(KETSalesCSMeetingApproval csApprove) throws Exception {

	SessionContext old_session = null;
	old_session = SessionContext.newContext();
	try {
	    SessionHelper.manager.setAdministrator();
	    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		    "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
	    csApprove = (KETSalesCSMeetingApproval) LifeCycleHelper.service.reassign(csApprove, paramLifeCycleTemplateReference);

	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    throw new Exception("KETSalesCSMeetingApproval Life Cycle Reassign Error !! ");
	} finally{
	    SessionContext.setContext(old_session);
	}
	return csApprove;

    }

    @Override
    public void CSMeetingChasuCreate(String arroid, String gubun) throws Exception {

	arroid = StringUtils.removeEnd(arroid, ",");
	String arr[] = arroid.split(",");

	KETSalesProject salesPjt = null;

	KETSalesCSMeetingManage manage = null;
	KETSalesCSMeetingManageLink manageLink = null;
	KETSalesCSMeetingApproval manageApproval = null;
	Transaction transaction = new Transaction();
	int sortNo = 1;
	int beforeSort = 0;
	boolean isNewchasu = false;
	List<Map<String, String>> csmeetingInfo = null;
	try {

	    String classKey = dao.searchCSLastManageInfo("N");
	    if ("Y".equals(gubun) && StringUtils.isEmpty(classKey)) {
		csmeetingInfo = dao.searchCSInfo();
	    }

	    transaction.start();

	    if ("Y".equals(gubun) && StringUtils.isEmpty(classKey)) {// CS회의취소가 아니고 지정이면서 마지막 CS회의가 종료되었다면 새로운 차수를 만든다

		String maxDegree = "";

		for (Map<String, String> info : csmeetingInfo) {
		    maxDegree = info.get("CS_DEGREE");
		}

		String currentYear = DateUtil.getThisYear();
		String currentMonth = DateUtil.getThisMonth();
		WTUser createUser = (WTUser) SessionHelper.manager.getPrincipal();
		/*
	         * Timestamp csStartDate = DateUtil.getTimestampFormat(paramObject.getCsStartDate(), "yyyy-MM-dd"); Timestamp csEndDate =
	         * DateUtil.getTimestampFormat(paramObject.getCsEndDate(), "yyyy-MM-dd");
	         */

		manage = KETSalesCSMeetingManage.newKETSalesCSMeetingManage();
		manage.setDegree(maxDegree);
		manage.setYear(currentYear);
		manage.setMonth(currentMonth);
		manage.setCreateUser(createUser);
		manage.setModifyUser(createUser);

		String folderPath = "/Default/자동차사업부/초기유동/";
		WTContainerRef containerRef = WCUtil.getWTContainerRef();

		SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
		FolderHelper.assignLocation((FolderEntry) manage, folder);
		manage = (KETSalesCSMeetingManage) PersistenceHelper.manager.save(manage);

		isNewchasu = true;

	    }
	    boolean isCSTarget = true;

	    String detpSort = "";
	    HashMap<String, Object> rtvl = null;
	    QuerySpec query = null;
	    String state = "";
	    String approves = "";

	    for (int i = 0; i < arr.length; i++) {
		if ("Y".equals(gubun)) {// 일괄지정

		    // isCSTarget = true;

		    if (!isNewchasu) {// 이미 해당년월에 차수가 존재한다면 마지막 차수 데이터를 가져온다
			classKey = dao.searchCSLastManageInfo("");
			manage = (KETSalesCSMeetingManage) CommonUtil.getObject(classKey);// 마지막 차수 데이터를 가져온다
		    }

		    salesPjt = (KETSalesProject) CommonUtil.getObject(arr[i]);

		    isCSTarget = this.getProjectViewAuthInfo(salesPjt);// 권한 체크

		    /*
	             * WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	             * 
	             * if (user != salesPjt.getIterationInfo().getCreator().getPrincipal()) {// 프로젝트 작성자와 접속자가 동일한지 확인 isCSTarget = false; }
	             * if (!isCSTarget) { PeopleData peoData = new PeopleData(user); Department tartgetDept = peoData.department;// 접속 유저의
	             * 부서
	             * 
	             * if (tartgetDept.getCode().equals(salesPjt.getSalesTeam().getCode())) {// 프로젝트 부서와 접속유저 부서 일치시 ok isCSTarget = true; }
	             * else { ArrayList<Department> childDept = DepartmentHelper.manager.getChildList(salesPjt.getSalesTeam());
	             * 
	             * for (Department child : childDept) {// 프로젝트 작성자의 하위부서와 접속자의 부서가 일치하면 ok (부서장용) if
	             * (tartgetDept.getCode().equals(child.getCode())) { isCSTarget = true; } } }
	             * 
	             * }
	             */

		    state = salesPjt.getLifeCycleState().toString();
		    // 결재완료 후 개정이 진행되지 않기 때문에 아래 로직은 주석처리한다. 현재는 차수마감시 진행 중 프로젝트에 리비전이 발생한다 2017.03.08
		    /*
	             * if (!(state.equals("INWORK") || state.equals("REWORK")) || state.equals("APPROVED")) { // 결재가 진행중인 프로젝트는 제외한다. 결재가
	             * 완료되면 // 버전이 올라가기때문에 데이터가 틀어질수있다 continue; }
	             */

		    QueryResult inQr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

		    while (inQr.hasMoreElements()) {
			manageLink = (KETSalesCSMeetingManageLink) inQr.nextElement();

			String original = CommonUtil.getOIDString(manageLink.getCsProject());
			String target = CommonUtil.getOIDString(salesPjt);

			if (original.equals(target)) {// 이미 지정된 프로젝트는 스킵
			    isCSTarget = false;
			}
		    }

		    if (!isCSTarget) {
			continue;
		    }

		    salesPjt.setCsYn("Y");
		    // Iteration 증가 없이 속성 수정
		    PersistenceServerHelper.manager.update(salesPjt);

		    /*
	             * QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "csDegree", KETSalesCSMeetingManageLink.class,
	             * false);
	             * 
	             * while(inQr.hasMoreElements()){ isCSTarget = false; } if(!isCSTarget){ continue; }
	             */

		    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
		    // CS회의 차수 마감시 이터레이션을 증가하기로 함 여기서는 그냥 수정만함
		    // this.appointCS(source, target);
		    detpSort = this.getCSprojectSortInfo(salesPjt.getSalesTeam());
		    if (StringUtils.isEmpty(detpSort)) {
			throw new Exception(salesPjt.getProjectNo() + " 의 부서SortNo 정보가 없습니다.");
		    }

		    rtvl = this.getBeforeDeptSort(manage, detpSort);// 해당차수에 동일부서의 결재데이터가 생성되었는지 확인하고 있다면 몇개의 부서가 생성됐는지 리턴받고, 결재객체도 리턴받는다
		    beforeSort = (int) rtvl.get("beforeSort");

		    if (i == 0) {
			sortNo = sortNo + beforeSort;
		    }

		    if (beforeSort == 0) {// 신규 결재 데이터 생성필요시
			manageApproval = this.createCSapproval();
			manageApproval = this.meetingApprovalSetLifeCycle(manageApproval);
		    } else {
			manageApproval = (KETSalesCSMeetingApproval) CommonUtil.getObject((String) rtvl.get("manageApproval"));
		    }

		    // KETSalesCSMeetingApproval은 하나의 차수에 엮어있는 여러개의 프로젝트에 대해 부서별로 한개만 있어야한다.그래서 소스가 지저분..

		    if (manageApproval.getState().toString().equals("APPROVED")) {// 동일부서에 대해 추가 재결재가 필요하다면 reassign 해준다,link테이블도 업데이트
			LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
			manageApproval = (KETSalesCSMeetingApproval) LifeCycleHelper.service.reassign(manageApproval,
			        paramLifeCycleTemplateReference);

			query = new QuerySpec();
			int idx = query.appendClassList(KETSalesCSMeetingManageLink.class, true);
			query.appendWhere(new SearchCondition(KETSalesCSMeetingManageLink.class, "csMeetingApproveReference.key.id", "=",
			        CommonUtil.getOIDLongValue(manageApproval)), new int[] { idx });
			StatementSpec Lastquery = query;
			QueryResult qr = PersistenceHelper.manager.find(Lastquery);
			KETSalesCSMeetingManageLink target = null;

			while (qr.hasMoreElements()) {
			    Object[] obj = (Object[]) qr.nextElement();
			    target = (KETSalesCSMeetingManageLink) obj[0];
			    target.setCsState("INWORK");
			    PersistenceHelper.manager.save(target);
			}
		    }

		    manageLink = KETSalesCSMeetingManageLink.newKETSalesCSMeetingManageLink(salesPjt, manage);
		    manageLink.setSortNo(Integer.toString(sortNo));
		    manageLink.setDeptSortNo(detpSort);
		    manageLink.setCsMeetingApprove(manageApproval);
		    PersistenceHelper.manager.save(manageLink);
		    String meetingManageLinkInfo = CommonUtil.getOIDString(manage);
		    manageApproval.setMeetingManageLinkInfo(meetingManageLinkInfo);
		    manageApproval.setDeptSort(detpSort);
		    PersistenceHelper.manager.save(manageApproval);

		    /*
	             * if (newManage == null) {// 이미 해당년월에 차수가 존재한다면
	             * 
	             * rtvl = this.getBeforeDeptSort(manage, detpSort); beforeSort = (int) rtvl.get("rtvl");
	             * 
	             * if (i == 0) { sortNo = sortNo + beforeSort; }
	             * 
	             * if (beforeSort > 0) {// 신규 결재 데이터 생성필요시 manageApproval = this.createCSapproval(); } else { manageApproval =
	             * (KETSalesCSMeetingApproval) CommonUtil.getObject((String) rtvl.get("manageApproval")); }
	             * 
	             * manageLink = KETSalesCSMeetingManageLink.newKETSalesCSMeetingManageLink(salesPjt, manage);
	             * manageLink.setSortNo(Integer.toString(sortNo)); manageLink.setDeptSortNo(detpSort);
	             * manageLink.setCsMeetingApprove(manageApproval); PersistenceHelper.manager.save(manageLink); String
	             * meetingManageLinkInfo = CommonUtil.getOIDString(manage);
	             * manageApproval.setMeetingManageLinkInfo(meetingManageLinkInfo); manageApproval.setDeptSort(detpSort);
	             * PersistenceHelper.manager.save(manageApproval);
	             * 
	             * } else {// 새로운 차수가 생성되었다면 해당 차수에 링크생성 manageApproval = this.createCSapproval(); manageLink =
	             * KETSalesCSMeetingManageLink.newKETSalesCSMeetingManageLink(salesPjt, newManage);
	             * manageLink.setSortNo(Integer.toString(sortNo)); manageLink.setDeptSortNo(detpSort);
	             * manageLink.setCsMeetingApprove(manageApproval); PersistenceHelper.manager.save(manageLink); String
	             * meetingManageLinkInfo = CommonUtil.getOIDString(newManage);
	             * manageApproval.setMeetingManageLinkInfo(meetingManageLinkInfo); manageApproval.setDeptSort(detpSort);
	             * PersistenceHelper.manager.save(manageApproval);
	             * 
	             * }
	             */
		    sortNo++;

		} else {// 일괄취소
		    salesPjt = (KETSalesProject) CommonUtil.getObject(arr[i]);
		    salesPjt.setCsYn("");
		    // Iteration 증가 없이 속성 수정
		    PersistenceServerHelper.manager.update(salesPjt);

		    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
		    QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "csDegree", KETSalesCSMeetingManageLink.class, false);

		    while (inQr.hasMoreElements()) {
			manageLink = (KETSalesCSMeetingManageLink) inQr.nextElement();

			manageApproval = manageLink.getCsMeetingApprove();

			PersistenceHelper.manager.delete(manageLink);

			if (manageApproval != null) {
			    manage = (KETSalesCSMeetingManage) CommonUtil.getObject(manageApproval.getMeetingManageLinkInfo());
			    // approveOid = CommonUtil.getOIDLongValue(manageApproval);
			    approves = CommonUtil.getOIDString(manageApproval) + ",";
			    /*
		             * PersistenceHelper.manager.delete(manageApproval);
		             * 
		             * query = new QuerySpec(); int idx = query.appendClassList(KETSalesCSMeetingManageLink.class, true);
		             * query.appendWhere(new SearchCondition(KETSalesCSMeetingManageLink.class, "csMeetingApproveReference.key.id",
		             * "=", approveOid), new int[] { idx }); StatementSpec Lastquery = query; QueryResult qr =
		             * PersistenceHelper.manager.find(Lastquery); while (qr.hasMoreElements()) { Object[] obj = (Object[])
		             * qr.nextElement(); KETSalesCSMeetingManageLink target = (KETSalesCSMeetingManageLink) obj[0];
		             * target.setCsMeetingApprove(null); PersistenceHelper.manager.save(target); }
		             */
			}

		    }
		}

	    }

	    if (!"Y".equals(gubun) && manage != null) {

		// 일괄취소 일때 순서재정렬
		query = new QuerySpec();
		int idx = query.appendClassList(KETSalesCSMeetingManageLink.class, true);
		query.appendWhere(
		        new SearchCondition(KETSalesCSMeetingManageLink.class, "roleBObjectRef.key.id", "=", CommonUtil
		                .getOIDLongValue(manage)), new int[] { idx });

		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(
		        KETSalesCSMeetingManageLink.class, "deptSortNo"));

		OrderBy orderby = new OrderBy(sqlfunction, false);
		query.appendOrderBy(orderby, new int[] { idx });

		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(KETSalesCSMeetingManageLink.class,
		        "sortNo"));
		orderby = new OrderBy(sqlfunction, false);
		query.appendOrderBy(orderby, new int[] { idx });

		StatementSpec Lastquery = query;
		QueryResult qr = PersistenceHelper.manager.find(Lastquery);
		int cnt = 1;
		while (qr.hasMoreElements()) {
		    Object[] obj = (Object[]) qr.nextElement();
		    manageLink = (KETSalesCSMeetingManageLink) obj[0];
		    manageLink.setSortNo(Integer.toString(cnt));
		    PersistenceHelper.manager.save(manageLink);
		    cnt++;
		}

		// 해당 차수에 해당부서의 데이터가 하나도 없다면 관련 결재데이터는 삭제한다
		approves = StringUtils.removeEnd(approves, ",");

		String approves_[] = approves.split(",");
		TreeSet<String> tSet = new TreeSet<String>();// 중복제거위함
		for (int i = 0; i < approves_.length; i++) {
		    tSet.add(approves_[i]);
		}

		Iterator<String> it = tSet.iterator();

		while (it.hasNext()) {
		    manageApproval = (KETSalesCSMeetingApproval) CommonUtil.getObject((String) it.next());
		    String deptSort = manageApproval.getDeptSort();

		    query = new QuerySpec();
		    idx = query.appendClassList(KETSalesCSMeetingManageLink.class, true);
		    query.appendWhere(new SearchCondition(KETSalesCSMeetingManageLink.class, "csMeetingApproveReference.key.id", "=",
			    CommonUtil.getOIDLongValue(manageApproval)), new int[] { idx });
		    query.appendAnd();
		    query.appendWhere(new SearchCondition(KETSalesCSMeetingManageLink.class, "deptSortNo", "=", deptSort),
			    new int[] { idx });
		    Lastquery = query;
		    qr = PersistenceHelper.manager.find(Lastquery);

		    if (qr.size() == 0) {
			PersistenceHelper.manager.delete(manageApproval);
		    }
		}

		/*
	         * while (qr.hasMoreElements()) { Object[] obj = (Object[]) qr.nextElement(); KETSalesCSMeetingManageLink target =
	         * (KETSalesCSMeetingManageLink) obj[0]; target.setCsMeetingApprove(null); PersistenceHelper.manager.save(target); }
	         */
	    }

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    transaction.rollback();
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    @Override
    public List<Map<String, Object>> getCsprojectSortedList(boolean isFile) throws Exception {
	String classKey = dao.searchCSLastManageInfo("");

	QueryResult qr = this.getCsprojectSortedResult(classKey);

	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	Map<String, Object> rslt = null;

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETSalesCSMeetingManage mng = (KETSalesCSMeetingManage) obj[0];
	    KETSalesCSMeetingManageLink manageLink = (KETSalesCSMeetingManageLink) obj[1];
	    rslt = new HashMap<String, Object>();

	    // mng = (KETSalesCSMeetingManage) manageLink.getCsDegree();
	    rslt.put("oid", CommonUtil.getOIDLongValue2Str(manageLink.getCsProject()));
	    rslt.put("year", mng.getYear());
	    rslt.put("month", mng.getMonth());
	    rslt.put("degree", mng.getDegree());
	    rslt.put("type", "");
	    rslt.put("manageOid", CommonUtil.getOIDLongValue2Str(mng));
	    if (StringUtils.contains(manageLink.getFileSortOption(), "ApplicationData")) {
		rslt.put("firstFile", CommonUtil.getOIDLongValue2Str(manageLink.getFileSortOption()));
	    }
	    rsltList.add(rslt);

	    if (isFile && StringUtils.isNotEmpty(manageLink.getCsFile())) {
		rslt = new HashMap<String, Object>();
		rslt.put("oid", manageLink.getCsFile());
		rslt.put("year", mng.getYear());
		rslt.put("month", mng.getMonth());
		rslt.put("degree", mng.getDegree());
		rslt.put("type", "file");
		rslt.put("manageOid", CommonUtil.getOIDLongValue2Str(mng));
		rsltList.add(rslt);

	    }
	}

	/*
	 * QueryResult inQr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);
	 * 
	 * List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	 * 
	 * KETSalesCSMeetingManageLink manageLink = null; Map<String, Object> rslt = null;
	 * 
	 * while (inQr.hasMoreElements()) { rslt = new HashMap<String, Object>(); manageLink = (KETSalesCSMeetingManageLink)
	 * inQr.nextElement(); KETSalesCSMeetingManage mng = (KETSalesCSMeetingManage)manageLink.getCsDegree(); rslt.put("oid",
	 * CommonUtil.getOIDLongValue2Str(manageLink.getCsProject())); rslt.put("year", mng.getYear()); rslt.put("month", mng.getMonth());
	 * rslt.put("degree", mng.getDegree());
	 * 
	 * String sortNo = manageLink.getSortNo();
	 * 
	 * if (StringUtils.isEmpty(sortNo)) { sortNo = "10000"; }
	 * 
	 * rslt.put("sortNo", manageLink.getDeptSortNo() + manageLink.getSortNo());// 부서별로 순서가정해져있다. CSProjectSortEnum.java 참고
	 * rsltList.add(rslt); }
	 * 
	 * Collections.sort(rsltList, ComparatorUtil.SALESMANGESORT);
	 */

	return rsltList;
    }

    @Override
    public QueryResult getCSlist() throws Exception {
	// TODO Auto-generated method stub
	QuerySpec spec = new QuerySpec();

	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETSalesProject> iterClass = KETSalesProject.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);
	// Join WTDocumentMaster - KETSalesProject
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });

	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	    TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO");
	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	    spec.appendSelect(function, new int[] { i_idx }, false);
	}

	if (spec.getGroupByClause() == null) {
	    // Group by
	    // spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false);
	    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
	}

	// latest iteration

	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);
	spec.setDescendantQuery(false);

	StatementSpec statementSpec = spec;

	QuerySpec spec2 = new QuerySpec();

	int idx = 0;

	idx = spec2.addClassList(KETSalesProject.class, true);

	TableColumn masterOidAttr2 = new TableColumn(spec2.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr = new TableColumn(spec2.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");

	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr2);

	listExpression.addColumn(versionAttr);

	SubSelectExpression selectExpression = new SubSelectExpression(statementSpec);
	selectExpression.setAccessControlRequired(false);
	spec2.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	// latest iteration
	SearchUtil.appendBOOLEAN(spec2, KETSalesProject.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);
	SearchUtil.appendEQUAL(spec2, iterClass, "csYn", "Y", idx);

	StatementSpec Lastquery = spec2;
	if (!Lastquery.isAdvancedQueryEnabled())
	    Lastquery.setAdvancedQueryEnabled(true);

	QueryResult queryResult = PersistenceHelper.manager.find(Lastquery);

	return queryResult;
    }

    @Override
    public String CSDegreeCreate(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	QueryResult queryResult = paramObject.getQueryResult();

	KETSalesCSMeetingManage csManage = null;

	String currentYear = DateUtil.getThisYear();
	String currentMonth = DateUtil.getThisMonth();
	WTUser createUser = (WTUser) SessionHelper.manager.getPrincipal();
	Timestamp csStartDate = DateUtil.getTimestampFormat(paramObject.getCsStartDate(), "yyyy-MM-dd");
	Timestamp csEndDate = DateUtil.getTimestampFormat(paramObject.getCsEndDate(), "yyyy-MM-dd");

	csManage = KETSalesCSMeetingManage.newKETSalesCSMeetingManage();
	String maxDegree = paramObject.getDegree();
	csManage.setDegree(maxDegree);
	csManage.setYear(currentYear);
	csManage.setMonth(currentMonth);
	csManage.setCreateUser(createUser);
	csManage.setModifyUser(createUser);
	csManage.setCsStartDate(csStartDate);
	csManage.setCsEndDate(csEndDate);

	// 첨부파일
	KETContentHelper.service.updateContent(csManage, paramObject);

	PersistenceHelper.manager.save(csManage);

	KETSalesCSMeetingManageLink csManageLink = null;

	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    KETSalesProject sales = (KETSalesProject) objArr[0];
	    csManageLink = KETSalesCSMeetingManageLink.newKETSalesCSMeetingManageLink(sales, csManage);
	    PersistenceHelper.manager.save(csManageLink);
	}

	return CommonUtil.getOIDString(csManage);
    }

    @Override
    public PageControl findPagingCSList(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub

	StatementSpec lastSpec = this.getCSManagelistQuery(paramObject);

	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");

	if (!lastSpec.isAdvancedQueryEnabled())
	    lastSpec.setAdvancedQueryEnabled(true);
	if (lastSpec != null) {
	    if (StringUtil.isEmpty(pageSessionId)) {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), lastSpec);
	    } else {
		pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		        pageSessionId);
	    }
	}

	return pageControl;

    }

    @Override
    public QuerySpec getCSManagelistQuery(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	QuerySpec spec = new QuerySpec();

	Class<KETSalesCSMeetingManage> mainClass = KETSalesCSMeetingManage.class;
	int idx = spec.addClassList(mainClass, true);

	/*
	 * if (StringUtils.isNotEmpty(paramObject.getOid())) { spec.appendWhere(new SearchCondition(mainClass,
	 * "thePersistInfo.theObjectIdentifier.id", "=", CommonUtil.getOIDLongValue(paramObject.getOid())), new int[] { idx }); }
	 */

	if (paramObject.getYears() != null) {
	    String[] years = paramObject.getYears();

	    if (years.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(mainClass, "year", years, true, false), new int[] { idx });

	    } else {
		SearchUtil.appendEQUAL(spec, mainClass, "year", years[0], idx);
	    }

	}

	if (paramObject.getMonths() != null) {
	    String[] months = paramObject.getMonths();

	    if (months.length > 1) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(mainClass, "month", months, true, false), new int[] { idx });

	    } else {
		SearchUtil.appendEQUAL(spec, mainClass, "month", months[0], idx);
	    }

	}

	if (paramObject.getCloseYNs() != null) {
	    String[] closeYNs = paramObject.getCloseYNs();

	    if (closeYNs.length > 1) {
		/*
	         * if (spec.getConditionCount() > 0) spec.appendAnd(); spec.appendWhere(new SearchCondition(mainClass, "csClose", closeYNs,
	         * true, false), new int[] { idx });
	         */

	    } else {

		if (closeYNs[0].equals("Y")) {
		    SearchUtil.appendEQUAL(spec, mainClass, "csClose", closeYNs[0], idx);
		} else {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(mainClass, "csClose", SearchCondition.IS_NULL, true), new int[] { idx });
		}

	    }

	}
	if (StringUtils.isNotEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();

	    if (checkName.equals("year") || checkName.equals("-year")) {
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "year"));
		OrderBy orderby = null;
		if (checkName.startsWith("-")) {
		    orderby = new OrderBy(sqlfunction, true);
		} else {
		    orderby = new OrderBy(sqlfunction, false);
		}
		spec.appendOrderBy(orderby, new int[] { idx });
	    } else if (checkName.equals("month") || checkName.equals("-month")) {
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "month"));
		OrderBy orderby = null;
		if (checkName.startsWith("-")) {
		    orderby = new OrderBy(sqlfunction, true);
		} else {
		    orderby = new OrderBy(sqlfunction, false);
		}
		spec.appendOrderBy(orderby, new int[] { idx });
	    } else if (checkName.equals("degree") || checkName.equals("-degree")) {
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "degree"));
		OrderBy orderby = null;
		if (checkName.startsWith("-")) {
		    orderby = new OrderBy(sqlfunction, true);
		} else {
		    orderby = new OrderBy(sqlfunction, false);
		}
		spec.appendOrderBy(orderby, new int[] { idx });
	    }

	} else {
	    // SearchUtil.setOrderBy(spec, mainClass, idx, "year", true);
	    SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "year"));
	    OrderBy orderby = null;
	    orderby = new OrderBy(sqlfunction, true);
	    spec.appendOrderBy(orderby, new int[] { idx });

	    sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "month"));
	    orderby = null;
	    orderby = new OrderBy(sqlfunction, true);
	    spec.appendOrderBy(orderby, new int[] { idx });

	    sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(mainClass, "degree"));
	    orderby = null;
	    orderby = new OrderBy(sqlfunction, true);
	    spec.appendOrderBy(orderby, new int[] { idx });

	    // SearchUtil.setOrderBy(spec, mainClass, idx, "degree", true);
	}
	Kogger.debug(getClass(), spec);
	return spec;
    }

    @Override
    public void csMeetingClose(KETSalesProjectDTO paramObject) throws Exception {
	//
	/*
	 * 1. 지정된 차수의 데이터를 가져 온 후(KETSalesCSMeetingManage) cs 회의 종료처리 2. 해당 차수의 프로젝트를 가져온다 projectMeetingClose 호출 3. 루프를 돌면서 해당 프로젝트의 이터레이션을
	 * 증가, 관련 테이블 복사 4. 모든 진행 중 프로젝트를 찾아서 프로젝트 별 타스크이력을 생성한다
	 */
	Transaction transaction = new Transaction();
	SessionContext old_session = null;
	try {
	    transaction.start();
	    old_session = SessionContext.newContext();
	    SessionHelper.manager.setAdministrator();

	    this.taskDiffAllAggregation();

	    KETSalesCSMeetingManage manage = paramObject.getCsCloseTartget();

	    manage = (KETSalesCSMeetingManage) PersistenceHelper.manager.refresh(manage);
	    manage.setCsClose("Y");
	    Timestamp csStartDate_ = DateUtil.getTimestampFormat(paramObject.getCsStartDate(), "yyyy-MM-dd");
	    Timestamp csEndDate_ = DateUtil.getTimestampFormat(paramObject.getCsEndDate(), "yyyy-MM-dd");
	    Timestamp csNextStartDate_ = DateUtil.getTimestampFormat(paramObject.getCsNextStartDate(), "yyyy-MM-dd");
	    manage.setNextStartdate(csNextStartDate_);
	    manage.setCsStartDate(csStartDate_);
	    manage.setCsEndDate(csEndDate_);
	    PersistenceHelper.manager.save(manage);
	    // manage = (KETSalesCSMeetingManage) PersistenceHelper.manager.refresh(manage);

	    QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class);
	    this.projectMeetingClose(qr);

	    /*
	     * StatementSpec lastSpec = this.getCSManagelistQuery(paramObject); QueryResult queryResult =
	     * PersistenceHelper.manager.find(lastSpec); Timestamp csNextStartDate_ = null; QueryResult qr = null; while
	     * (queryResult.hasMoreElements()) { Object[] objArr = (Object[]) queryResult.nextElement(); KETSalesCSMeetingManage manage =
	     * (KETSalesCSMeetingManage) objArr[0]; manage = (KETSalesCSMeetingManage) PersistenceHelper.manager.refresh(manage);
	     * manage.setCsClose("Y"); csNextStartDate_ = DateUtil.getTimestampFormat(paramObject.getCsNextStartDate(), "yyyy-MM-dd");
	     * manage.setNextStartdate(csNextStartDate_); PersistenceHelper.manager.save(manage); // manage = (KETSalesCSMeetingManage)
	     * PersistenceHelper.manager.refresh(manage);
	     * 
	     * qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class);
	     * this.projectMeetingClose(qr); }
	     */

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    transaction.rollback();
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(old_session);
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

    }

    public void projectMeetingClose(QueryResult qr) throws Exception {

	KETSalesProject source = null;
	KETSalesProject target = null;
	KETSalesCSMeetingManageLink mngLink = null;
	while (qr.hasMoreElements()) {
	    source = (KETSalesProject) qr.nextElement();
	    source = (KETSalesProject) PersistenceHelper.manager.refresh(source);
	    this.appointCS(source, target);
	}
    }

    public KETSalesProject appointCS(KETSalesProject source, KETSalesProject target) throws Exception {// cs회의 지정시 원본 KETSalesProject
	// 이터레이션증가시키고, 기존 관련 테이블들을 복사한다

	target = (KETSalesProject) ObjectUtil.checkout(source);
	target.setCsYn("");
	target = (KETSalesProject) PersistenceHelper.manager.save(target);
	target = (KETSalesProject) ObjectUtil.checkin(target);
	target = (KETSalesProject) PersistenceHelper.manager.refresh(target);

	this.copyProjectChild(source, target);
	return target;
    }

    public void taskDiffAllAggregation() throws Exception {// 모든 진행 중 프로젝트를 찾고, 해당 프로젝트의 타스크(STEP) 별 문제점,진행현황,해결책 항목을 버전별로 비교하여 상이하면 이력을
	                                                   // 생성한다
	/*
	 * QuerySpec spec = new QuerySpec();
	 * 
	 * Class<WTDocumentMaster> masterClass = WTDocumentMaster.class; Class<KETSalesProject> iterClass = KETSalesProject.class; int m_idx
	 * = spec.addClassList(masterClass, false); int i_idx = spec.addClassList(iterClass, false);
	 * 
	 * ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id"); spec.appendSelect(masterOidAttr, new
	 * int[] { i_idx }, false); // Join WTDocumentMaster - KETSalesProject spec.appendWhere(new SearchCondition(new
	 * ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute( iterClass, "masterReference.key.id")), new
	 * int[] { m_idx, i_idx });
	 * 
	 * NumberCode state = NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "I");
	 * 
	 * SearchUtil.appendEQUAL(spec, iterClass, "salesStateReference.key.id", CommonUtil.getOIDLongValue(state), i_idx);
	 * 
	 * if (!spec.isAdvancedQueryEnabled()) { spec.setAdvancedQueryEnabled(true); TableColumn versionAttr = new
	 * TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO"); SQLFunction function =
	 * SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr); spec.appendSelect(function, new int[] { i_idx }, false); }
	 * 
	 * if (spec.getGroupByClause() == null) { // Group by // spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false);
	 * spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false); }
	 * 
	 * // latest iteration
	 * 
	 * SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);
	 * spec.setDescendantQuery(false); StatementSpec lastSpec = spec; QueryResult qr = PersistenceHelper.manager.find(lastSpec);
	 */

	QuerySpec spec = new QuerySpec();

	Class<WTDocumentMaster> masterClass = WTDocumentMaster.class;
	Class<KETSalesProject> iterClass = KETSalesProject.class;
	int m_idx = spec.addClassList(masterClass, false);
	int i_idx = spec.addClassList(iterClass, false);

	ClassAttribute masterOidAttr = new ClassAttribute(iterClass, "masterReference.key.id");
	spec.appendSelect(masterOidAttr, new int[] { i_idx }, false);
	// Join WTDocumentMaster - KETSalesProject
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        iterClass, "masterReference.key.id")), new int[] { m_idx, i_idx });

	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	    TableColumn versionAttr = new TableColumn(spec.getFromClause().getAliasAt(i_idx), "VERSIONIDA2VERSIONINFO");
	    SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
	    spec.appendSelect(function, new int[] { i_idx }, false);
	}

	if (spec.getGroupByClause() == null) {
	    // Group by
	    // spec.appendGroupBy(classnameAttr, new int[] { i_idx }, false);
	    spec.appendGroupBy(masterOidAttr, new int[] { i_idx }, false);
	}

	// latest iteration

	SearchUtil.appendBOOLEAN(spec, iterClass, "iterationInfo.latest", SearchCondition.IS_TRUE, i_idx);
	spec.setDescendantQuery(false);

	StatementSpec statementSpec = spec;

	QuerySpec spec2 = new QuerySpec();

	int idx = 0;

	idx = spec2.addClassList(KETSalesProject.class, true);

	TableColumn masterOidAttr2 = new TableColumn(spec2.getFromClause().getAliasAt(idx), "IDA3MASTERREFERENCE");
	TableColumn versionAttr = new TableColumn(spec2.getFromClause().getAliasAt(idx), "VERSIONIDA2VERSIONINFO");

	ColumnListExpression listExpression = new ColumnListExpression();
	listExpression.addColumn(masterOidAttr2);

	listExpression.addColumn(versionAttr);

	SubSelectExpression selectExpression = new SubSelectExpression(statementSpec);
	selectExpression.setAccessControlRequired(false);
	spec2.appendWhere(new SearchCondition(listExpression, "IN", selectExpression), new int[] { idx });
	// latest iteration
	SearchUtil.appendBOOLEAN(spec2, KETSalesProject.class, "iterationInfo.latest", SearchCondition.IS_TRUE, idx);
	NumberCode state = NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", "I");
	SearchUtil.appendEQUAL(spec2, iterClass, "salesStateReference.key.id", CommonUtil.getOIDLongValue(state), idx);

	StatementSpec Lastquery = spec2;
	if (!Lastquery.isAdvancedQueryEnabled())
	    Lastquery.setAdvancedQueryEnabled(true);

	QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	QueryResult qr2 = null;
	KETSalesProject salesPjt = null;

	while (qr.hasMoreElements()) {

	    Object[] objArr = (Object[]) qr.nextElement();
	    salesPjt = (KETSalesProject) objArr[0];
	    qr2 = PersistenceHelper.manager.navigate(salesPjt, "task", KETSalesTaskLink.class, false);
	    this.taskHistoryCopy(qr2);
	}
    }

    public void createTaskHistory(String version, KETSalesTask task) throws Exception {
	KETSalesTaskHistory history = null;
	KETSalesTaskHistoryLink historyLink = null;

	history = KETSalesTaskHistory.newKETSalesTaskHistory();
	history.setPropelwebEditor(task.getPropelwebEditor());
	history.setPropelwebEditorText(task.getPropelwebEditorText());
	history.setProblemwebEditor(task.getProblemwebEditor());
	history.setProblemwebEditorText(task.getProblemwebEditorText());
	history.setPlanwebEditor(task.getPlanwebEditor());
	history.setPlanwebEditorText(task.getPlanwebEditorText());
	history.setStepNo(task.getStepNo());
	history.setTaskVersion(version);

	history.setPlanCheck(task.getPlanCheck());
	history.setPlanDate(task.getPlanDate());
	history.setResultDate(task.getResultDate());
	history.setSalesPlan(task.getSalesPlan());
	history.setSubject(task.getSubject());
	history.setCollaboManager(task.getCollaboManager());
	history.setCollaboTeam(task.getCollaboTeam());
	history.setTaskType(task.getTaskType());

	history = (KETSalesTaskHistory) PersistenceHelper.manager.save(history);

	historyLink = KETSalesTaskHistoryLink.newKETSalesTaskHistoryLink(task, history);
	PersistenceHelper.manager.save(historyLink);
    }

    public void taskHistoryCopy(QueryResult qr) throws Exception {

	KETSalesTask task = null;
	KETSalesTaskHistory history = null;

	QueryResult qr2 = null;
	KETSalesTaskLink taskLink = null;

	while (qr.hasMoreElements()) {
	    taskLink = (KETSalesTaskLink) qr.nextElement();
	    task = taskLink.getTask();

	    qr2 = PersistenceHelper.manager.navigate(task, "Taskhistory", KETSalesTaskHistoryLink.class);

	    if (qr2.size() < 1) {// 최초 이력 적재시
		this.createTaskHistory("1", task);

	    } else {// 타스크 이력이 있으면 해당 이력에서 문제점,해결방안,진행현황을 뽑아서 최신 타스크와 비교한다
		boolean isDiff = false;
		String version = "";
		HashMap<String, KETSalesTaskHistory> map = new HashMap<String, KETSalesTaskHistory>();
		while (qr2.hasMoreElements()) {
		    history = (KETSalesTaskHistory) qr2.nextElement();

		    String temp_version = history.getTaskVersion();

		    map.put(temp_version, history);

		    if ("".equals(version)) {
			version = temp_version;
		    } else {
			if (Integer.parseInt(temp_version) > Integer.parseInt(version)) {
			    version = temp_version;
			}
		    }
		}
		history = (KETSalesTaskHistory) map.get(version);

		String history_planText = ((String) history.getPlanwebEditorText())
		        .replaceAll(System.getProperty("line.separator"), "<br>");
		String history_ploblemText = ((String) history.getProblemwebEditorText()).replaceAll(System.getProperty("line.separator"),
		        "<br>");
		String history_plopelText = ((String) history.getPropelwebEditorText()).replaceAll(System.getProperty("line.separator"),
		        "<br>");

		String planText = ((String) task.getPlanwebEditorText()).replaceAll(System.getProperty("line.separator"), "<br>");
		String ploblemText = ((String) task.getProblemwebEditorText()).replaceAll(System.getProperty("line.separator"), "<br>");
		String plopelText = ((String) task.getPropelwebEditorText()).replaceAll(System.getProperty("line.separator"), "<br>");

		String history_planEditor = ((String) history.getPlanwebEditor()).replaceAll(System.getProperty("line.separator"), "<br>");
		String history_ploblemEditor = ((String) history.getProblemwebEditor()).replaceAll(System.getProperty("line.separator"),
		        "<br>");
		String history_plopelEditor = ((String) history.getPropelwebEditor()).replaceAll(System.getProperty("line.separator"),
		        "<br>");

		String planEditor = ((String) task.getPlanwebEditor()).replaceAll(System.getProperty("line.separator"), "<br>");
		String ploblemEditor = ((String) task.getProblemwebEditor()).replaceAll(System.getProperty("line.separator"), "<br>");
		String plopelEditor = ((String) task.getPropelwebEditor()).replaceAll(System.getProperty("line.separator"), "<br>");

		if (StringUtils.isNotEmpty(StringUtils.difference(history_planText, planText))) {
		    isDiff = true;
		} else if (StringUtils.isNotEmpty(StringUtils.difference(history_ploblemText, ploblemText))) {
		    isDiff = true;
		} else if (StringUtils.isNotEmpty(StringUtils.difference(history_plopelText, plopelText))) {
		    isDiff = true;
		} else if (StringUtils.isNotEmpty(StringUtils.difference(history_planEditor, planEditor))) {
		    isDiff = true;
		} else if (StringUtils.isNotEmpty(StringUtils.difference(history_ploblemEditor, ploblemEditor))) {
		    isDiff = true;
		} else if (StringUtils.isNotEmpty(StringUtils.difference(history_plopelEditor, plopelEditor))) {
		    isDiff = true;
		}

		if (isDiff) {
		    this.createTaskHistory(Integer.toString((Integer.parseInt(version) + 1)), task);
		}
	    }
	}
    }

    public void getTaskMaxHistoryVersionQuery(QueryResult qr) throws Exception {

	KETSalesTaskHistoryLink historyLink = null;

	while (qr.hasMoreElements()) {
	    historyLink = (KETSalesTaskHistoryLink) qr.nextElement();
	}

	long ida2a2 = CommonUtil.getOIDLongValue(historyLink.getTaskhistory());

	QuerySpec querySub = new QuerySpec();
	int idxA = querySub.appendClassList(KETSalesTaskHistory.class, false);
	querySub.appendWhere(new SearchCondition(KETSalesTaskHistory.class, "attr1", "=", ida2a2), new int[] { idxA });
	ClassAttribute taskVersion = new ClassAttribute(KETSalesTaskHistory.class, "taskVersion");
	SQLFunction max = SQLFunction.newSQLFunction("MAX", taskVersion);
	querySub.appendSelect(max, false);

	/*
	 * KETSalesTaskHistoryLink historyLink = null; while (qr.hasMoreElements()) { historyLink = (KETSalesTaskHistoryLink)
	 * qr.nextElement(); } long ida2a2 = CommonUtil.getOIDLongValue(historyLink);
	 * 
	 * QuerySpec querySub = new QuerySpec(); int idxA = querySub.appendClassList(KETSalesTaskHistory.class, false);
	 * 
	 * ClassAttribute taskVersion = new ClassAttribute(KETSalesTaskHistory.class, "taskVersion"); SQLFunction max =
	 * SQLFunction.newSQLFunction("MAX", taskVersion);
	 */
    }

    @Override
    public String getCSprojectSortInfo(Department targetDept) {
	CSProjectSortEnum teamSortInfo = null;
	String SortNo = "";
	if (targetDept != null) {
	    teamSortInfo = CSProjectSortEnum.toSortTypeEnumByTeam(targetDept.getName());
	    if (teamSortInfo != null) {
		SortNo = teamSortInfo.getSortNo();
	    }

	}
	return SortNo;
    }

    public List<Map<String, String>> getCSprojectSortInfo() {
	List<Map<String, String>> rsltList = new ArrayList<Map<String, String>>();
	Map<String, String> rslt = null;

	Department salesHeadOffice = DepartmentHelper.manager.getDepartment("10060");// 영업본부
	ArrayList<Department> childDept = DepartmentHelper.manager.getChildList(salesHeadOffice);
	CSProjectSortEnum teamSortInfo = null;

	for (Department child : childDept) {
	    teamSortInfo = CSProjectSortEnum.toSortTypeEnumByTeam(child.getName());

	    rslt = new HashMap<String, String>();
	    rslt.put(child.getCode(), teamSortInfo.getSortNo());

	}
	rsltList.add(rslt);

	return rsltList;
    }

    @Override
    public KETSalesCSMeetingApproval getCSApprovalTarget(KETSalesCSMeetingManage manage, String deptSortNo) throws Exception {
	if ("ALL".equals(deptSortNo)) {
	    deptSortNo = "";
	}
	QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);
	KETSalesCSMeetingManageLink manageLink = null;
	KETSalesCSMeetingApproval csApprove = null;
	String targetSort = deptSortNo;

	if (StringUtils.isEmpty(targetSort)) {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	    PeopleData people = new PeopleData((user));
	    Department dept = (Department) people.department;

	    targetSort = this.getCSprojectSortInfo(dept);
	}

	while (qr.hasMoreElements()) {
	    manageLink = (KETSalesCSMeetingManageLink) qr.nextElement();

	    String deptSort = manageLink.getDeptSortNo();
	    if (targetSort.equals(deptSort)) {
		csApprove = manageLink.getCsMeetingApprove();
	    }
	}

	return csApprove;
    }

    @Override
    public boolean getProjectViewAuthInfo(KETSalesProject pjt) throws Exception {
	// TODO Auto-generated method stub
	if (CommonUtil.isAdmin() || CommonUtil.isMember("공통_임원")) {
	    return true;
	} else {

	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    // WTUser creator = (WTUser) pjt.getIterationInfo().getCreator().getPrincipal();

	    if (user == pjt.getIterationInfo().getCreator().getPrincipal()) {// 프로젝트 작성자와 접속자가 동일하면 ok
		return true;
	    }

	    PeopleData peoData = new PeopleData(user);
	    Department tartgetDept = peoData.department;// 접속 유저의 부서

	    if (CommonUtil.isMember("영업CS관리")) {// 영업기획팀이면 ok
		return true;
	    }

	    // peoData = new PeopleData(creator);//프로젝트 작성자 부서
	    // Department checkDept = peoData.department;
	    Department checkDept = pjt.getSalesTeam(); // 프로젝트 작성자 부서

	    if (tartgetDept.getCode().equals(checkDept.getCode())) {// 프로젝트 작성자의 부서와 접속유저의 부서가 동일하면 ok
		return true;
	    }
	    KETSalesTask task = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(pjt, "task", KETSalesTaskLink.class);

	    while (qr.hasMoreElements()) {// 프로젝트의 Task 담당자 중에서 접속유저가 일치하면 ok
		task = (KETSalesTask) qr.nextElement();

		if (user.getName().equals(task.getCollaboManager().getName())) {
		    return true;
		}
	    }

	    ArrayList<Department> childDept = DepartmentHelper.manager.getChildList(tartgetDept);

	    for (Department child : childDept) {// 접속자의 하위부서 중에 프로젝트 작성자의 부서가 존재하면 ok (센터장 이상임원에게 권한적용위해)
		if (checkDept.getCode().equals(child.getCode())) {
		    return true;
		}
	    }
	    
	    KETSalesMemberLink memLink = null;
	    QueryResult memQr = PersistenceHelper.manager.navigate(pjt, "member", KETSalesMemberLink.class, false);
	    
	    while (memQr.hasMoreElements()) {// 프로젝트의 참여자라면 ok
		memLink = (KETSalesMemberLink) memQr.nextElement();

		if (user.getName().equals(memLink.getMember().getName())) {
		    return true;
		}
	    }
	    
	}
	return false;
    }

    public boolean getProjectEditAuth2Senior(KETSalesProject pjt) throws Exception {// 작성자의 상위부서
	Department checkDept = pjt.getSalesTeam(); // 프로젝트 작성자 부서

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	PeopleData peoData = new PeopleData(user);
	Department tartgetDept = peoData.department;// 접속 유저의 부서

	if (StringUtils.isNotEmpty(peoData.chief)) {
	    if(checkDept.getCode().equals(tartgetDept.getCode())){
		return true;
	    }

	    ArrayList<Department> childDept = DepartmentHelper.manager.getChildList(tartgetDept);

	    for (Department child : childDept) {// 접속자의 하위부서 중에 프로젝트 작성자의 부서가 존재하면 ok (센터장 이상임원에게 권한적용위해)
		if (checkDept.getCode().equals(child.getCode())) {
		    return true;
		}
	    }
	}
	
	if("leesw".equals(user.getName()) && ("EU팀".equals(checkDept.getName()) || "EU/NA팀".equals(checkDept.getName()))){//EU팀 팀장이 없는 관계로 이승우 차장에게 권한부여
	    return true;
	}

	return false;
    }

    @Override
    public void lastProjectDelete(String arroid) throws Exception {
	// TODO Auto-generated method stub
	arroid = StringUtils.removeEnd(arroid, ",");
	String arr[] = arroid.split(",");
	KETSalesProject salesPjt = null;

	for (int i = 0; i < arr.length; i++) {
	    salesPjt = (KETSalesProject) CommonUtil.getObject(arr[i]);
	    PersistenceHelper.manager.delete(salesPjt);

	}

    }

    @Override
    public QueryResult getCsprojectSortedResult(String classKey) throws Exception {
	// TODO Auto-generated method stub

	QuerySpec query = new QuerySpec();

	int idxA = query.appendClassList(KETSalesCSMeetingManage.class, true);
	int idxB = query.appendClassList(KETSalesCSMeetingManageLink.class, true);

	query.appendWhere(new SearchCondition(new ClassAttribute(KETSalesCSMeetingManage.class, WTAttributeNameIfc.ID_NAME), "=",
	        new ClassAttribute(KETSalesCSMeetingManageLink.class, WTAttributeNameIfc.ROLEB_OBJECT_ID)), new int[] { idxA, idxB });
	query.appendAnd();
	query.appendWhere(
	        new SearchCondition(KETSalesCSMeetingManage.class, WTAttributeNameIfc.ID_NAME, "=", CommonUtil.getOIDLongValue(classKey)),
	        new int[] { idxA });

	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(KETSalesCSMeetingManageLink.class,
	        "deptSortNo"));
	OrderBy orderby = new OrderBy(sqlfunction, false);

	query.appendOrderBy(orderby, new int[] { idxB });

	sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(KETSalesCSMeetingManageLink.class, "sortNo"));
	orderby = new OrderBy(sqlfunction, false);

	query.appendOrderBy(orderby, new int[] { idxB });

	QueryResult qr = PersistenceHelper.manager.find(query);
	return qr;
    }

    @Override
    public List<Map<String, String>> csMeetingFileVaultUpload(KETSalesProjectDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	Transaction transaction = new Transaction();

	List<Map<String, String>> rsltList = new ArrayList<Map<String, String>>();

	try {
	    transaction.start();
	    
	    String sortNo[] = paramObject.getSortNo();
	    String oids[] = paramObject.getOids();
	    KETSalesCSMeetingManageLink manageLink = null;

	    KETSalesCSMeetingManage mng = (KETSalesCSMeetingManage) CommonUtil.getObject(paramObject.getOid());

	    this.FileVaultReset(paramObject, mng);// 삭제 대상 파일 제거

	    List<MultipartFile> secondaryFiles = paramObject.getSecondaryFiles();
	    String secondaryFileOids[] = paramObject.getSecondaryFileOids();
	    List<UploadedFile> files = new ArrayList<UploadedFile>();

	    String FileSortNo[] = paramObject.getFileSort();

	    int newFileSize = 0;
	    if (secondaryFiles != null) {
		newFileSize = secondaryFiles.size() - 1;
	    }

	    int newFileCursor = 0;

	    ApplicationData applicationData = null;

	    if (FileSortNo != null) {
		for (int i = 0; i < FileSortNo.length; i++) {

		    if (secondaryFileOids[i].equals("new")) {
			MultipartFile multipartFile = secondaryFiles.get(newFileCursor);
			UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);
			files.add(uploadedFile);
			applicationData = KETContentHelper.service.updateContent(mng, uploadedFile);

			rsltList = addFileList(rsltList, CommonUtil.getOIDString(applicationData), FileSortNo[i]);
			if (newFileCursor < newFileSize) {
			    newFileCursor++;
			}

		    } else {
			rsltList = addFileList(rsltList, secondaryFileOids[i], FileSortNo[i]);

		    }

		}
	    }

	    for (int i = 0; i < sortNo.length; i++) {
		manageLink = (KETSalesCSMeetingManageLink) CommonUtil.getObject(oids[i]);

		// String deptSort = SalesHelper.service.getCSprojectSortInfo(manageLink.getCsProject().getSalesTeam());
		// manageLink.setSortNo(deptSort + sortNo[i]);
		manageLink.setSortNo(sortNo[i]);
		manageLink.setCsFile("");

		for (Map<String, String> rslt : rsltList) {

		    if (rslt.get("sortNo").equals(sortNo[i])) {
			manageLink.setCsFile(rslt.get("appOid"));
		    }
		}

		PersistenceHelper.manager.save(manageLink);
	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    transaction.rollback();
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return rsltList;
    }

    public List<Map<String, String>> addFileList(List<Map<String, String>> rsltList, String appOid, String sortNo) {

	Map<String, String> rslt = new HashMap<String, String>();

	rslt.put("appOid", appOid);
	rslt.put("sortNo", sortNo);

	rsltList.add(rslt);

	return rsltList;
    }

    public void FileVaultReset(KETSalesProjectDTO dto, KETSalesCSMeetingManage manage) throws Exception {
	List<ContentItem> contentItems = new ArrayList<ContentItem>(); // not remove contentitem list

	// getting not remove contentitem list
	String[] cios = dto.getSecondaryFileOids();
	if (cios != null && cios.length > 0) {
	    for (String cio : cios) {
		if (!"new".equals(cio)) {
		    contentItems.add((ContentItem) CommonUtil.getObject(cio));
		}

	    }
	}
	this.contentItemAlldelete(contentItems, manage);
    }

    public void contentItemAlldelete(List<ContentItem> contentItems, ContentHolder holder) throws Exception {
	ArrayList<ContentDTO> secondaryContents = KETContentHelper.manager.getSecondaryContents(holder);
	if (secondaryContents != null && secondaryContents.size() > 0) {
	    for (ContentDTO dto : secondaryContents) {
		ApplicationData existApp = (ApplicationData) CommonUtil.getObject(dto.getContentoid());
		ApplicationData compareApp = null;
		boolean doDelete = true;
		if (contentItems != null && contentItems.size() > 0) {
		    for (ContentItem item : contentItems) {
			compareApp = (ApplicationData) item;
			if (compareApp == null)
			    continue;
			if (existApp.getFileName().equals(compareApp.getFileName()) && existApp.getFileSize() == compareApp.getFileSize()) {
			    doDelete = false;
			    break;
			} else {
			    doDelete = true;
			}
		    }
		}
		if (doDelete) {
		    Kogger.debug(getClass(), "Delete Exist Content Item : " + existApp);
		    holder = KETContentHelper.service.delete(holder, existApp);
		}
	    }
	}
    }

    @Override
    public List<Map<String, String>> salesPresentList() throws Exception {
	// TODO Auto-generated method stub
	List<Map<String, String>> salesPresentList = dao.getSalesPresentConditionInfo();
	return salesPresentList;
    }
}
