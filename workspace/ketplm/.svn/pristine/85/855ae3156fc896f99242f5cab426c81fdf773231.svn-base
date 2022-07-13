package ext.ket.edm.service;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.content.StreamData;
import wt.content.Streamed;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.fv.FileFolder;
import wt.fv.FvItem;
import wt.fv.FvMount;
import wt.fv.StandardFvService;
import wt.fv.StoredItem;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.util.WTException;
import e3ps.common.content.ContentInfo;
import e3ps.common.content.ContentUtil;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.query.SearchUtil;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMPartHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.entity.KETDrawingDistDept;
import ext.ket.edm.entity.KETDrawingDistDeptLink;
import ext.ket.edm.entity.KETDrawingDistDocLink;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistFileTypeLink;
import ext.ket.edm.entity.KETDrawingDistMoldEOLink;
import ext.ket.edm.entity.KETDrawingDistProdEOLink;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETDrawingDownHis;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStampDocItem;
import ext.ket.edm.entity.KETStampDocLink;
import ext.ket.edm.entity.KETStampEpmLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.entity.KETStampingItem;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.stamping.jms.StampingQueueSender;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dao.CommonDao;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;
import ext.ket.shared.util.SessionUtil;

public class StandardDrawingDistService extends StandardManager implements DrawingDistService {
    private static final long serialVersionUID = 1L;

    @Inject
    private CommonDao dao;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : StandardDrawingDistService
     * @작성자 : tklee
     * @작성일 : 2014. 7. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardDrawingDistService newStandardDrawingDistService() throws WTException {
	StandardDrawingDistService instance = new StandardDrawingDistService();
	instance.initialize();
	return instance;
    }

    /*
     * KETDrawingDistRequest 삭제
     */
    @Override
    public KETDrawingDistRequest delete(KETDrawingDistReqDTO paramObject) throws Exception {

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getOid());

	// delete 배포처
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistDept> deptList = query.queryForListAByRoleB(KETDrawingDistDept.class, KETDrawingDistDeptLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);
	deletePersisableList(deptList);

	// delete 배포포멧
	List<KETDrawingDistFileType> fileTypeList = query.queryForListAByRoleB(KETDrawingDistFileType.class,
	        KETDrawingDistFileTypeLink.class, KETDrawingDistRequest.class, drawingDistReq);
	deletePersisableList(fileTypeList);

	// delete eo list

	// delete doc list

	// delete stamping

	// delte epm list

	return (KETDrawingDistRequest) PersistenceHelper.manager.delete(drawingDistReq);
    }

    /*
     * 전체 리스트
     */
    public List<KETDrawingDistRequest> find(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<KETDrawingDistRequest> list = new ArrayList<KETDrawingDistRequest>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((KETDrawingDistRequest) objArr[0]);
	}
	return list;
    }

    /*
     * Paging 리스트
     */
    public PageControl findPaging(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	QuerySpec query = getQuery(paramObject);
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

    /*
     * Paging 리스트
     */
    public PageControl hpFindPaging(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	QuerySpec query = getHpQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}

	System.out.println("pageControl >>>>>>>>>>>>>>>>>>>>>>>>>>>> " + pageControl);

	return pageControl;
    }

    /**
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : KKW
     * @작성일 : 2014. 7. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getHpQuery(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	QuerySpec hpQuery = new QuerySpec();
	SearchCondition Asc = null;

	int idxDist = hpQuery.appendClassList(KETDrawingDistRequest.class, false);
	int idxStamp = hpQuery.appendClassList(KETStamping.class, false);
	int idxDSLink = hpQuery.appendClassList(KETStampDistLink.class, false);
	int idxPM = hpQuery.appendClassList(WTPartMaster.class, false);
	int idxWP = hpQuery.appendClassList(WTPart.class, false);
	int idxEO = hpQuery.appendClassList(KETProdChangeOrder.class, false);
	int idxDEL = hpQuery.appendClassList(KETDrawingDistProdEOLink.class, false);
	int idxCA = hpQuery.appendClassList(KETPartClassification.class, false);
	int idxCL = hpQuery.appendClassList(KETPartClassLink.class, false);

	ClassAttribute create_date = new ClassAttribute(KETDrawingDistRequest.class, "thePersistInfo.createStamp");
	SQLFunction char_create = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, create_date,
	        ConstantExpression.newExpression("YYYY/MM/DD"));

	ClassAttribute send_date = new ClassAttribute(KETDrawingDistRequest.class, "hpSendDate");
	SQLFunction char_send = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, send_date, ConstantExpression.newExpression("YYYY/MM/DD"));

	hpQuery.appendSelectAttribute("name", idxPM, false);
	hpQuery.appendSelectAttribute("title", idxDist, false);
	hpQuery.appendSelectAttribute("number", idxPM, false);
	hpQuery.appendSelect(char_create, false);
	hpQuery.appendSelect(char_send, false);
	hpQuery.appendSelectAttribute("hpSendStatus", idxDist, false);
	hpQuery.appendSelectAttribute("thePersistInfo.theObjectIdentifier.classname", idxWP, false);
	hpQuery.appendSelectAttribute("thePersistInfo.theObjectIdentifier.id", idxDist, false);
	hpQuery.appendSelectAttribute("stampStatus", idxStamp, false);
	hpQuery.appendSelectAttribute("ecoId", idxEO, false);
	hpQuery.appendSelectAttribute("divisionFlag", idxCA, false);

	Asc = new SearchCondition(KETDrawingDistRequest.class, "distType", "=", "APPROVED");
	hpQuery.appendWhere(Asc, new int[] { idxDist });
	hpQuery.appendAnd();
	Asc = new SearchCondition(KETDrawingDistRequest.class, "backgroundYn", "=", "Y");
	hpQuery.appendWhere(Asc, new int[] { idxDist });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETDrawingDistRequest.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETStampDistLink.class, "roleBObjectRef.key.id"));
	Asc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	hpQuery.appendWhere(Asc, new int[] { idxDist, idxDSLink });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETStampDistLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(
	        KETStamping.class, "thePersistInfo.theObjectIdentifier.id"));
	Asc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	hpQuery.appendWhere(Asc, new int[] { idxDSLink, idxStamp });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETDrawingDistRequest.class, "refPart"), "=", new ClassAttribute(WTPart.class,
	        "thePersistInfo.theObjectIdentifier.id"));
	hpQuery.appendWhere(Asc, new int[] { idxDist, idxWP });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(WTPart.class, "masterReference.key.id"), "=", new ClassAttribute(WTPartMaster.class,
	        "thePersistInfo.theObjectIdentifier.id"));
	hpQuery.appendWhere(Asc, new int[] { idxWP, idxPM });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETDrawingDistRequest.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETDrawingDistProdEOLink.class, "roleBObjectRef.key.id"));
	hpQuery.appendWhere(Asc, new int[] { idxDist, idxDEL });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETProdChangeOrder.class, "thePersistInfo.theObjectIdentifier.id"), "=",
	        new ClassAttribute(KETDrawingDistProdEOLink.class, "roleAObjectRef.key.id"));
	hpQuery.appendWhere(Asc, new int[] { idxEO, idxDEL });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=", new ClassAttribute(
	        KETPartClassLink.class, "roleBObjectRef.key.id"));
	Asc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	hpQuery.appendWhere(Asc, new int[] { idxPM, idxCL });
	hpQuery.appendAnd();
	Asc = new SearchCondition(new ClassAttribute(KETPartClassLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(
	        KETPartClassification.class, "thePersistInfo.theObjectIdentifier.id"));
	Asc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	hpQuery.appendWhere(Asc, new int[] { idxCL, idxCA });

	if ("SUCCESS".equals(paramObject.getStampstatus())) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "stampStatus", "=", paramObject.getStampstatus());
	    hpQuery.appendWhere(Asc, new int[] { idxStamp });
	} else if ("QUEUE".equals(paramObject.getStampstatus())) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "stampStatus", "=", paramObject.getStampstatus());
	    hpQuery.appendWhere(Asc, new int[] { idxStamp });
	} else if ("BEFORE".equals(paramObject.getStampstatus())) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "stampStatus", "=", paramObject.getStampstatus());
	    hpQuery.appendWhere(Asc, new int[] { idxStamp });
	} else if ("FAIL".equals(paramObject.getStampstatus())) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "stampStatus", "=", paramObject.getStampstatus());
	    hpQuery.appendWhere(Asc, new int[] { idxStamp });
	}
	if ("Y".equals(StringUtils.stripToEmpty(paramObject.getHpstatus()))) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "hpSendStatus", "=", paramObject.getHpstatus());
	    hpQuery.appendWhere(Asc, new int[] { idxDist });
	}
	if ("N".equals(StringUtils.stripToEmpty(paramObject.getHpstatus()))) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETStamping.class, "stampStatus", SearchCondition.IS_NULL, true);
	    hpQuery.appendWhere(Asc, new int[] { idxDist });
	}
	if (!"".equals(StringUtils.stripToEmpty(paramObject.getPartNo()))) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(WTPartMaster.class, "number", "=", paramObject.getPartNo());
	    hpQuery.appendWhere(Asc, new int[] { idxPM });
	}
	if (!"".equals(StringUtils.stripToEmpty(paramObject.getEcoNo()))) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETProdChangeOrder.class, "ecoId", "=", paramObject.getEcoNo());
	    hpQuery.appendWhere(Asc, new int[] { idxEO });
	}
	if (!"".equals(StringUtils.stripToEmpty(paramObject.getDivisionflag()))) {
	    hpQuery.appendAnd();
	    Asc = new SearchCondition(KETPartClassification.class, "divisionFlag ", "=", paramObject.getDivisionflag());
	    hpQuery.appendWhere(Asc, new int[] { idxCA });
	}

	System.out.println("========> hpQuery :::::::: " + hpQuery);

	return hpQuery;
    }

    /**
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : KKW
     * @작성일 : 2014. 7. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(KETDrawingDistRequest.class, true);
	query.appendWhere(new SearchCondition(KETDrawingDistRequest.class, RevisionControlled.LATEST_ITERATION, "TRUE"), idx);
	if (!StringUtil.isTrimToEmpty(paramObject.getNumber())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.NUMBER, SearchCondition.LIKE, "%"
		    + paramObject.getNumber() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDistType())) {
	    sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.DIST_TYPE, SearchCondition.EQUAL,
		    paramObject.getDistType(), false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDistSubcontractor())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.DIST_SUBCONTRACTOR, SearchCondition.LIKE, "%"
		    + paramObject.getDistSubcontractor() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}

	// ECO 자동 배포를 위한 컬럼으로 기존 배포인경우에 해당 하는 부분표기 처리
	query.appendAnd();
	sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.BACKGROUND_YN, SearchCondition.EQUAL, null, false);
	query.appendWhere(sc, new int[] { idx });
	//

	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    // '-'내림차순
	    if (paramObject.getSortName().startsWith("-")) {
		SearchUtil.setOrderBy(query, KETDrawingDistRequest.class, idx, paramObject.getSortName().substring(1), true);
	    } else {
		SearchUtil.setOrderBy(query, KETDrawingDistRequest.class, idx, paramObject.getSortName(), false);
	    }
	} else {
	    SearchUtil.setOrderBy(query, KETDrawingDistRequest.class, idx, KETDrawingDistRequest.CREATE_TIMESTAMP, true);
	}
	return query;
    }

    /*
     * 수정
     */
    @Override
    public KETDrawingDistRequest modify(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getOid());

	// 이터레이션 관리 X
	// WorkInProgressHelper.service.checkout(drawingDistReq, WorkInProgressHelper.service.getCheckoutFolder(), "");
	// drawingDistReq = (KETDrawingDistRequest) WorkInProgressHelper.service.workingCopyOf(drawingDistReq);

	drawingDistReq.setTitle(paramObject.getTitle());

	drawingDistReq.setDistType(paramObject.getDistType());
	drawingDistReq.setDistReason(paramObject.getDistReason());
	drawingDistReq.setDistSubcontractor(paramObject.getDistSubcontractor());
	drawingDistReq.setEtcYn(paramObject.getEtcYn());

	drawingDistReq.setWriteDeptEnName(paramObject.getWriteDeptEnName());
	drawingDistReq.setWriteDeptKrName(paramObject.getWriteDeptKrName());
	drawingDistReq.setWriteDeptCode(paramObject.getWriteDeptCode());
	drawingDistReq.setDistDeptName(paramObject.getDistDeptName());

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	String writeDeptKrName = (userDept == null) ? "" : userDept.getName();
	String writeDeptCode = (userDept == null) ? "" : userDept.getCode();

	drawingDistReq.setWriteDeptKrName(writeDeptKrName);

	// 강제 테스트위해 실제 값 랜덤으로 적용
	// String writeDeptEnName = "";
	// String deptEnTempArr[] = { "R&D Center", "Automotive Business Group", "Connector Development Support Office(Group)",
	// "Mold & Die Technology Development Department", "Manufacturing Technology Development Department",
	// "Manufacturing Technology Development Team 1", "Manufacturing Technology Development Team 2", "Electronic Business Group",
	// "Electronic Product Development Team", "Automotive IT Product Development Team", "Mold Development Team(Electronics)",
	// "Module Development Team 1", "Module Development Team 2", "Module Development Team 3", "Module Development Team 4",
	// "Module Development Team 5", "Module Development Team 6", "Product Trial Team 2", "Automotive Business Group" };
	// int idxx = (int) (Math.random() * deptEnTempArr.length);
	// writeDeptEnName = deptEnTempArr[idxx];

	drawingDistReq.setWriteDeptEnName(userDept.getEnName());

	drawingDistReq.setWriteDeptCode(writeDeptCode);
	// 이터레이션 관리 X
	// drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.modify(drawingDistReq);
	// WorkInProgressHelper.service.checkin(drawingDistReq, "");

	PersistenceServerHelper.manager.update(drawingDistReq);

	QuerySpec query = null;
	int idx = 0;
	QueryResult qr = null;
	if (!"Y".equals(drawingDistReq.getBackgroundYn())) {// 홈페이지 전송용이면 배포포맷 변경 없음

	    // 배포포멧 기존 삭제
	    query = new QuerySpec();
	    idx = query.appendClassList(KETDrawingDistFileType.class, true);
	    query.appendWhere(new SearchCondition(KETDrawingDistFileType.class, "distReqReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(drawingDistReq)), new int[] { idx });

	    qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		KETDrawingDistFileType ketDrawingDistFileType = (KETDrawingDistFileType) obj[0];
		PersistenceHelper.manager.delete(ketDrawingDistFileType);
	    }
	    // 배포포멧 기존삭제 끝

	    // 배포 포멧 : PDF,DWG
	    String drawingDistFileTypeArray = paramObject.getDrawingDistFileTypeArray();
	    if (!StringUtils.isEmpty(drawingDistFileTypeArray)) {
		String[] drawingDistFileTypeArrayItems = drawingDistFileTypeArray.split(",");
		for (String fileTypeItem : drawingDistFileTypeArrayItems) {

		    KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();

		    DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);

		    fileTypes.setDistFileType(drawingDistFileType.toString());
		    fileTypes.setDistReq(drawingDistReq);

		    PersistenceHelper.manager.save(fileTypes);
		}
	    }
	}

	// 배포 부서 기존 삭제
	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistDept.class, true);
	query.appendWhere(
	        new SearchCondition(KETDrawingDistDept.class, "distReqReference.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistDept ketDrawingDistDept = (KETDrawingDistDept) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistDept);
	}
	// 배포포멧 기존삭제 끝

	// 배포 부서
	String drawingDistDeptArray = paramObject.getDrawingDistDeptArray();
	String distDeptName = paramObject.getDistDeptName();
	if (!StringUtils.isEmpty(drawingDistDeptArray) && !StringUtils.isEmpty(distDeptName)) {
	    String[] drawingDistDeptArrayItems = drawingDistDeptArray.split(",");
	    String[] distDeptNameItems = distDeptName.split(",");
	    if (drawingDistDeptArrayItems.length == distDeptNameItems.length) {
		for (int i = 0; i < drawingDistDeptArrayItems.length; i++) {
		    KETDrawingDistDept distDept = KETDrawingDistDept.newKETDrawingDistDept();

		    distDept.setDeptCode(drawingDistDeptArrayItems[i]);
		    distDept.setDeptName(distDeptNameItems[i]);
		    distDept.setDistReq(drawingDistReq);
		    PersistenceHelper.manager.save(distDept);
		}
	    }
	}

	// EO 링크 연결 삭제
	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistMoldEOLink.class, true);

	query.appendWhere(
	        new SearchCondition(KETDrawingDistMoldEOLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistMoldEOLink ketDrawingDistMoldEOLink = (KETDrawingDistMoldEOLink) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistMoldEOLink);
	}

	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistProdEOLink.class, true);

	query.appendWhere(
	        new SearchCondition(KETDrawingDistProdEOLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistProdEOLink ketDrawingDistProdEOLink = (KETDrawingDistProdEOLink) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistProdEOLink);
	}
	// EO 링크 연결 삭제

	// EO 링크 연결 저장
	String drawingDistEoArrayOid[] = paramObject.getDrawingDistEoArrayOid().split(",");

	for (int i = 0; i < drawingDistEoArrayOid.length; i++) {
	    if (drawingDistEoArrayOid[i].indexOf("KETMoldChangeOrder") != -1) {
		KETMoldChangeOrder eoMold = (KETMoldChangeOrder) CommonUtil.getObject(drawingDistEoArrayOid[i].trim());
		KETDrawingDistMoldEOLink moldEoLink = KETDrawingDistMoldEOLink.newKETDrawingDistMoldEOLink(eoMold, drawingDistReq);

		PersistenceHelper.manager.save(moldEoLink);
	    } else if (drawingDistEoArrayOid[i].indexOf("KETProdChangeOrder") != -1) {
		KETProdChangeOrder eoProd = (KETProdChangeOrder) CommonUtil.getObject(drawingDistEoArrayOid[i].trim());
		KETDrawingDistProdEOLink prodEoLink = KETDrawingDistProdEOLink.newKETDrawingDistProdEOLink(eoProd, drawingDistReq);

		PersistenceHelper.manager.save(prodEoLink);
	    }
	}

	// 배포 도면 기존 삭제
	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistEpmLink.class, true);
	query.appendWhere(
	        new SearchCondition(KETDrawingDistEpmLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistEpmLink ketDrawingDistEpmLink = (KETDrawingDistEpmLink) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistEpmLink);
	}
	// 배포 도면 기존삭제 끝

	// 배포 도면
	String drawingDisEpmStr = StringUtil.checkNull(paramObject.getDrawingDisEpmArray());
	String sheetTypeStr = StringUtil.checkNull(paramObject.getSheetTypeArray());

	if (!drawingDisEpmStr.equals(null) && !drawingDisEpmStr.equals("")) {
	    String[] drawingDisEpmArray = drawingDisEpmStr.split(",");
	    String[] sheetTypeArr = sheetTypeStr.split(",");

	    for (int i = 0; i < drawingDisEpmArray.length; i++) {
		EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(drawingDisEpmArray[i]);
		KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
		epmLink.setSheetType(sheetTypeArr[i]);

		PersistenceHelper.manager.save(epmLink);
	    }
	}

	// 배포 문서 기존 삭제
	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistDocLink.class, true);
	query.appendWhere(
	        new SearchCondition(KETDrawingDistDocLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistDocLink ketDrawingDistDocLink = (KETDrawingDistDocLink) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistDocLink);
	}
	// 배포 문서 기존삭제 끝

	// 배포 문서
	String drawingDistDocStr = StringUtil.checkNull(paramObject.getDrawingDistDocArray());

	if (!drawingDistDocStr.equals(null) && !drawingDistDocStr.equals("")) {
	    String[] drawingDistDocArray = drawingDistDocStr.split(",");

	    for (int i = 0; i < drawingDistDocArray.length; i++) {
		String[] oidArr = drawingDistDocArray[i].split(":");
		if (oidArr[0].equals("e3ps.dms.entity.KETTechnicalDocument")) {
		    KETTechnicalDocument ketTechnicalDocument = (KETTechnicalDocument) CommonUtil.getObject(drawingDistDocArray[i]);
		    KETDrawingDistDocLink ketDrawingDistDocLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(ketTechnicalDocument,
			    drawingDistReq);

		    PersistenceHelper.manager.save(ketDrawingDistDocLink);
		} else if (oidArr[0].equals("e3ps.dms.entity.KETProjectDocument")) {
		    KETProjectDocument ketProjectDocument = (KETProjectDocument) CommonUtil.getObject(drawingDistDocArray[i]);
		    KETDrawingDistDocLink ketDrawingDistDocLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(ketProjectDocument,
			    drawingDistReq);

		    PersistenceHelper.manager.save(ketDrawingDistDocLink);
		}
	    }
	}

	return drawingDistReq;
    }

    public String getNumber() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();

	    sql.append("SELECT    'D'").append("|| TO_CHAR (SYSDATE, 'YYMM')")
		    .append("|| TRIM(TO_CHAR(SEQ_DRAWINGDISREQUEST_NUM.NEXTVAL,'000'))").append("FROM DUAL");

	    // sql.append("SELECT FN_GET_ECM_NUMBERING(?) FROM DUAL \n");
	    pstmt = con.prepareStatement(sql.toString());
	    // pstmt.setString(1, "ECR");

	    rs = pstmt.executeQuery();
	    String ecrId = "";
	    while (rs.next()) {
		ecrId = rs.getString(1);
	    }

	    return ecrId;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
    }

    public String temp_getDrawingNumber() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet rs = null;
	String ecrId = "";
	System.out.println("getDrawingNumber start");
	try {
	    MethodContext oMethodContext = MethodContext.getContext();
	    WTConnection wtConnection = (WTConnection) oMethodContext.getConnection();
	    oConnection = wtConnection.getConnection();

	    sql.append("SELECT 'D'|| TO_CHAR (SYSDATE, 'YYMM')||TRIM(TO_CHAR(SEQ_DRAWINGDISREQUEST_NUM.NEXTVAL,'000')) OID FROM DUAL");

	    oPstmt = oConnection.prepareStatement(sql.toString());

	    rs = oPstmt.executeQuery();
	    while (rs.next()) {
		ecrId = rs.getString("OID");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    try {
		if (oPstmt != null)
		    oPstmt.close();
		if (rs != null)
		    rs.close();
	    } catch (Exception e) {
	    }
	}
	System.out.println("getDrawingNumber end");
	return ecrId;
    }

    public String getDrawingNumber() throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> drawingNumberList = new ArrayList<Map<String, Object>>();
	String drawingNumber = "";
	try {

	    String queryProjStr = "SELECT 'D'|| TO_CHAR (SYSDATE, 'YYMM')||TRIM(TO_CHAR(SEQ_DRAWINGDISREQUEST_NUM.NEXTVAL,'000')) AS DRAWINGNUMBER FROM DUAL";

	    drawingNumberList = oDaoManager.queryForList(queryProjStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getDrawingNumberResultSet(rs);
		}
	    });
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	for (Map<String, Object> list : drawingNumberList) {
	    drawingNumber = list.get("DRAWINGNUMBER").toString();
	    return drawingNumber;
	}

	return drawingNumber;
    }

    private HashMap<String, Object> getDrawingNumberResultSet(ResultSet rs) throws SQLException {
	HashMap<String, Object> drawingNumberAttr = new HashMap<String, Object>();
	drawingNumberAttr.put("DRAWINGNUMBER", StringUtil.checkNull(rs.getString("DRAWINGNUMBER")));

	return drawingNumberAttr;
    }

    /*
     * 등록
     */
    @Override
    public KETDrawingDistRequest save(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	KETDrawingDistRequest drawingDistReq = KETDrawingDistRequest.newKETDrawingDistRequest();

	drawingDistReq.setNumber(getNumber());
	drawingDistReq.setName(paramObject.getTitle());
	drawingDistReq.setTitle(paramObject.getTitle());
	// drawingDistReq.setDescription(paramObject.getDescription());

	drawingDistReq.setDistType(paramObject.getDistType());
	drawingDistReq.setDistReason(paramObject.getDistReason());
	// drawingDistReq.setDownloadExpireDate(null);
	drawingDistReq.setDistSubcontractor(paramObject.getDistSubcontractor());
	drawingDistReq.setEtcYn(paramObject.getEtcYn());

	drawingDistReq.setDistDeptName(paramObject.getDistDeptName());

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	String writeDeptKrName = (userDept == null) ? "" : userDept.getName();

	// 강제 테스트위해 실제 값 랜덤으로 적용
	// String writeDeptEnName = "";
	// String deptEnTempArr[] = { "R&D Center", "Automotive Business Group", "Connector Development Support Office(Group)",
	// "Mold & Die Technology Development Department", "Manufacturing Technology Development Department",
	// "Manufacturing Technology Development Team 1", "Manufacturing Technology Development Team 2", "Electronic Business Group",
	// "Electronic Product Development Team", "Automotive IT Product Development Team", "Mold Development Team(Electronics)",
	// "Module Development Team 1", "Module Development Team 2", "Module Development Team 3", "Module Development Team 4",
	// "Module Development Team 5", "Module Development Team 6", "Product Trial Team 2", "Automotive Business Group" };
	// int idx = (int) (Math.random() * deptEnTempArr.length);
	// writeDeptEnName = deptEnTempArr[idx];

	String writeDeptCode = (userDept == null) ? "" : userDept.getCode();

	drawingDistReq.setWriteDeptKrName(writeDeptKrName);
	drawingDistReq.setWriteDeptEnName(userDept.getEnName());
	drawingDistReq.setWriteDeptCode(writeDeptCode);
	if ("Y".equals(paramObject.getDirect2Hompage())) {
	    drawingDistReq.setBackgroundYn("Y");
	}

	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String lcName = "KET_COMMON_LC";
	LifeCycleHelper.setLifeCycle((LifeCycleManaged) drawingDistReq, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));
	String folderPath = "";
	if (CommonUtil.isKETSUser()) {
	    folderPath = "/Default/KETS/초기유동/";
	} else {
	    folderPath = "/Default/자동차사업부/초기유동/";
	}

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) drawingDistReq, folder);

	drawingDistReq = (KETDrawingDistRequest) TeamHelper.setTeamTemplate(drawingDistReq, TeamHelper.service.getTeamTemplate("Default"));

	drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.save(drawingDistReq);

	// 배포 포멧 : PDF,DWG
	String drawingDistFileTypeArray = paramObject.getDrawingDistFileTypeArray();

	if ("Y".equals(paramObject.getDirect2Hompage()) && StringUtils.isEmpty(drawingDistFileTypeArray)) {
	    drawingDistFileTypeArray = "PDF,DWG,STEP,IGS,JPG,THREEPDF";
	}

	if (!StringUtils.isEmpty(drawingDistFileTypeArray)) {
	    String[] drawingDistFileTypeArrayItems = drawingDistFileTypeArray.split(",");
	    for (String fileTypeItem : drawingDistFileTypeArrayItems) {

		KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();

		DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);

		fileTypes.setDistFileType(drawingDistFileType.toString());
		fileTypes.setDistReq(drawingDistReq);

		PersistenceHelper.manager.save(fileTypes);
	    }
	}

	// 배포 부서
	String drawingDistDeptArray = paramObject.getDrawingDistDeptArray();
	String distDeptName = paramObject.getDistDeptName();
	if (!StringUtils.isEmpty(drawingDistDeptArray) && !StringUtils.isEmpty(distDeptName)) {
	    String[] drawingDistDeptArrayItems = drawingDistDeptArray.split(",");
	    String[] distDeptNameItems = distDeptName.split(",");
	    if (drawingDistDeptArrayItems.length == distDeptNameItems.length) {
		for (int i = 0; i < drawingDistDeptArrayItems.length; i++) {
		    KETDrawingDistDept distDept = KETDrawingDistDept.newKETDrawingDistDept();

		    distDept.setDeptCode(drawingDistDeptArrayItems[i]);
		    distDept.setDeptName(distDeptNameItems[i]);
		    distDept.setDistReq(drawingDistReq);
		    PersistenceHelper.manager.save(distDept);
		}
	    }
	}

	// EO 링크 연결 저장
	String drawingDistEoArrayOid[] = paramObject.getDrawingDistEoArrayOid().split(",");

	for (int i = 0; i < drawingDistEoArrayOid.length; i++) {
	    if (drawingDistEoArrayOid[i].indexOf("KETMoldChangeOrder") != -1) {
		KETMoldChangeOrder eoMold = (KETMoldChangeOrder) CommonUtil.getObject(drawingDistEoArrayOid[i].trim());
		KETDrawingDistMoldEOLink moldEoLink = KETDrawingDistMoldEOLink.newKETDrawingDistMoldEOLink(eoMold, drawingDistReq);

		PersistenceHelper.manager.save(moldEoLink);
	    } else if (drawingDistEoArrayOid[i].indexOf("KETProdChangeOrder") != -1) {
		KETProdChangeOrder eoProd = (KETProdChangeOrder) CommonUtil.getObject(drawingDistEoArrayOid[i].trim());
		KETDrawingDistProdEOLink prodEoLink = KETDrawingDistProdEOLink.newKETDrawingDistProdEOLink(eoProd, drawingDistReq);

		PersistenceHelper.manager.save(prodEoLink);
	    }
	}

	// 배포 도면
	String drawingDisEpmStr = StringUtil.checkNull(paramObject.getDrawingDisEpmArray());
	String sheetTypeStr = StringUtil.checkNull(paramObject.getSheetTypeArray());

	if (!drawingDisEpmStr.equals(null) && !drawingDisEpmStr.equals("")) {
	    String[] drawingDisEpmArray = drawingDisEpmStr.split(",");
	    String[] sheetTypeArr = sheetTypeStr.split(",");

	    for (int i = 0; i < drawingDisEpmArray.length; i++) {
		EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(drawingDisEpmArray[i]);
		KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
		epmLink.setSheetType(sheetTypeArr[i]);

		PersistenceHelper.manager.save(epmLink);
	    }
	}

	// 배포 문서
	String drawingDistDocStr = StringUtil.checkNull(paramObject.getDrawingDistDocArray());

	if (!drawingDistDocStr.equals(null) && !drawingDistDocStr.equals("")) {
	    String[] drawingDistDocArray = drawingDistDocStr.split(",");

	    for (int i = 0; i < drawingDistDocArray.length; i++) {
		String[] oidArr = drawingDistDocArray[i].split(":");
		if (oidArr[0].equals("e3ps.dms.entity.KETTechnicalDocument")) {
		    KETTechnicalDocument ketTechnicalDocument = (KETTechnicalDocument) CommonUtil.getObject(drawingDistDocArray[i]);
		    KETDrawingDistDocLink ketDrawingDistDocLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(ketTechnicalDocument,
			    drawingDistReq);

		    PersistenceHelper.manager.save(ketDrawingDistDocLink);
		} else if (oidArr[0].equals("e3ps.dms.entity.KETProjectDocument")) {
		    KETProjectDocument ketProjectDocument = (KETProjectDocument) CommonUtil.getObject(drawingDistDocArray[i]);
		    KETDrawingDistDocLink ketDrawingDistDocLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(ketProjectDocument,
			    drawingDistReq);

		    PersistenceHelper.manager.save(ketDrawingDistDocLink);
		}
	    }
	}

	// wt.epm.EPMDocument:57745879
	// wt.epm.EPMDocument:57745880
	// wt.epm.EPMDocument:57745881
	// wt.epm.EPMDocument:57745882
	// wt.epm.EPMDocument:57858427
	// wt.epm.EPMDocument:57875911

	// String drawingDisEpmStr = paramObject.getDrawingDisEpmArray();
	//
	// String[] drawingDisEpmArray = drawingDisEpmStr.split(",");
	//
	// for (int i = 0; i < drawingDisEpmArray.length; i++) {
	// EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(drawingDisEpmArray[i]);
	// KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	//
	// PersistenceHelper.manager.save(epmLink);
	// }

	// EPMDocument distEpm = (EPMDocument) CommonUtil.getObject("wt.epm.EPMDocument:57745879");
	// KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	//
	// PersistenceHelper.manager.save(epmLink);
	//
	// distEpm = (EPMDocument) CommonUtil.getObject("wt.epm.EPMDocument:57745880");
	// epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	//
	// PersistenceHelper.manager.save(epmLink);
	//
	// distEpm = (EPMDocument) CommonUtil.getObject("wt.epm.EPMDocument:57745881");
	// epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	//
	// PersistenceHelper.manager.save(epmLink);

	// e3ps.dms.entity.KETTechnicalDocument:353371678
	// e3ps.dms.entity.KETTechnicalDocument:353371955
	// e3ps.dms.entity.KETTechnicalDocument:353373086
	// e3ps.dms.entity.KETProjectDocument:59027186
	// e3ps.dms.entity.KETProjectDocument:59261387
	// e3ps.dms.entity.KETProjectDocument:59262386

	// WTDocument distDoc = (WTDocument) CommonUtil.getObject("e3ps.dms.entity.KETTechnicalDocument:353371678");
	// KETDrawingDistDocLink docLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);
	//
	// PersistenceHelper.manager.save(docLink);
	//
	// distDoc = (WTDocument) CommonUtil.getObject("e3ps.dms.entity.KETTechnicalDocument:353373086");
	// docLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);
	//
	// PersistenceHelper.manager.save(docLink);
	//
	// distDoc = (WTDocument) CommonUtil.getObject("e3ps.dms.entity.KETProjectDocument:59261387");
	// docLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);
	//
	// PersistenceHelper.manager.save(docLink);
	//
	// distDoc = (WTDocument) CommonUtil.getObject("e3ps.dms.entity.KETProjectDocument:59027186");
	// docLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);
	//
	// PersistenceHelper.manager.save(docLink);
	//
	// distDoc = (WTDocument) CommonUtil.getObject("e3ps.dms.entity.KETProjectDocument:59262386");
	// docLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);
	//
	// PersistenceHelper.manager.save(docLink);

	/* 강제값 세팅(팝업이 안나온관계로) END */

	// // epm
	// EPMDocument distEpm1 = (EPMDocument) CommonUtil.getObject("");
	// KETDrawingDistEpmLink epm1 = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm1, drawingDistReq);

	// // doc
	// WTDocument distDoc1 = (WTDocument) CommonUtil.getObject("");
	// KETDrawingDistDocLink doc1 = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc1, drawingDistReq);

	// 첨부파일 업로드
	// KETContentHelper.service.updateContent(drawingDistReq, paramObject);

	return drawingDistReq;
    }

    private void deletePersisableList(List<? extends Persistable> pers) throws WTException {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	SessionHelper.manager.setAdministrator();

	try {

	    if (pers == null)
		return;

	    for (Persistable per : pers) {
		PersistenceHelper.manager.delete(per);
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	}
    }

    @Override
    public KETDrawingDistReqDTO detailView(KETDrawingDistReqDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDrawingDistRequest ketDrawingDistRequest = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getOid());

	QuerySpec query = new QuerySpec();
	int idx = query.appendClassList(KETDrawingDistFileType.class, true);
	query.appendWhere(
	        new SearchCondition(KETDrawingDistFileType.class, "distReqReference.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(ketDrawingDistRequest)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(query);

	String drawingDistFileTypeArray = "";
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    if (!drawingDistFileTypeArray.equals("")) {
		drawingDistFileTypeArray = drawingDistFileTypeArray + ",";
	    }
	    KETDrawingDistFileType ketDrawingDistFileType = (KETDrawingDistFileType) obj[0];
	    drawingDistFileTypeArray += ketDrawingDistFileType.getDistFileType();
	}

	KETDrawingDistReqDTO ketDrawingDistReqDTO = new KETDrawingDistReqDTO(ketDrawingDistRequest);

	ketDrawingDistReqDTO.setDrawingDistFileTypeArray(drawingDistFileTypeArray);
	// 파일 타입 set

	query = new QuerySpec();
	int idxMoldLink = query.appendClassList(KETDrawingDistMoldEOLink.class, true);
	int idxMoldEco = query.appendClassList(KETMoldChangeOrder.class, true);

	ClassAttribute caMoldLink = new ClassAttribute(KETDrawingDistMoldEOLink.class, "roleAObjectRef.key.id");
	ClassAttribute caMoldEco = new ClassAttribute(KETMoldChangeOrder.class, "thePersistInfo.theObjectIdentifier.id");

	query.appendWhere(new SearchCondition(caMoldLink, SearchCondition.EQUAL, caMoldEco), new int[] { idxMoldLink, idxMoldEco });
	query.appendAnd();
	query.appendWhere(
	        new SearchCondition(KETDrawingDistMoldEOLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(ketDrawingDistRequest)), new int[] { idxMoldLink });

	qr = PersistenceHelper.manager.find(query);

	List<HashMap<String, String>> drawingDistEoInfoList = new ArrayList<HashMap<String, String>>();

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();

	    KETMoldChangeOrder ketMoldChangeOrder = (KETMoldChangeOrder) obj[1];

	    HashMap<String, String> drawingDistEoInfo = new HashMap<String, String>();

	    drawingDistEoInfo.put("drawingDistEoNo", ketMoldChangeOrder.getEcoId().substring(4));
	    drawingDistEoInfo.put("drawingDistEoOid", CommonUtil.getOIDString(ketMoldChangeOrder));

	    drawingDistEoInfoList.add(drawingDistEoInfo);

	}

	query = new QuerySpec();
	int idxProdLink = query.appendClassList(KETDrawingDistProdEOLink.class, true);
	int idxProdEco = query.appendClassList(KETProdChangeOrder.class, true);

	ClassAttribute caProdLink = new ClassAttribute(KETDrawingDistProdEOLink.class, "roleAObjectRef.key.id");
	ClassAttribute caProdEco = new ClassAttribute(KETProdChangeOrder.class, "thePersistInfo.theObjectIdentifier.id");

	query.appendWhere(new SearchCondition(caProdLink, SearchCondition.EQUAL, caProdEco), new int[] { idxProdLink, idxProdEco });
	query.appendAnd();
	query.appendWhere(
	        new SearchCondition(KETDrawingDistProdEOLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(ketDrawingDistRequest)), new int[] { idxProdLink });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();

	    KETProdChangeOrder ketProdChangeOrder = (KETProdChangeOrder) obj[1];

	    HashMap<String, String> drawingDistEoInfo = new HashMap<String, String>();

	    drawingDistEoInfo.put("drawingDistEoNo", ketProdChangeOrder.getEcoId().substring(4));
	    drawingDistEoInfo.put("drawingDistEoOid", CommonUtil.getOIDString(ketProdChangeOrder));

	    drawingDistEoInfoList.add(drawingDistEoInfo);
	}

	if (ketDrawingDistRequest.getCreator() != null && !ketDrawingDistRequest.getCreator().equals(null)) {
	    if (ketDrawingDistRequest.getCreator().equals(SessionHelper.manager.getPrincipalReference())) {
		ketDrawingDistReqDTO.createUserFlag = true;
	    } else {
		ketDrawingDistReqDTO.createUserFlag = false;
	    }
	} else {
	    ketDrawingDistReqDTO.createUserFlag = false;
	}

	ketDrawingDistReqDTO.setDrawingDistEoInfoList(drawingDistEoInfoList);

	PeopleData peopleData = new PeopleData(ketDrawingDistRequest.getCreator().getPrincipal());
	ketDrawingDistReqDTO.setCreateDeptName(peopleData.departmentName);
	ketDrawingDistReqDTO.setCreator(peopleData.name);

	ketDrawingDistReqDTO.setDownloadExpireDate(DateUtil.getDateString(ketDrawingDistRequest.getDownloadExpireDate(), "date"));
	ketDrawingDistReqDTO.setCreateStamp(DateUtil.getDateString(ketDrawingDistRequest.getCreateTimestamp(), "date"));

	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) ketDrawingDistRequest);
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	Object temp = new Object();
	Vector vec = null;
	// out.println(master.getPbo().toString()); 결재객체oid확인
	if (master != null) {

	    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

	    if (vec != null) {
		String activityName = "&nbsp;";

		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    ;

		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
			    if (history.getCompletedDate() != null)
				ketDrawingDistReqDTO.setApproveStamp(DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd"));
			    ketDrawingDistReqDTO.setApprover(ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;"));

			}
		    }

		}
	    }
	}

	Timestamp downloadExpireDate = ketDrawingDistRequest.getDownloadExpireDate();
	ketDrawingDistReqDTO.setDownFlag("N");
	if (downloadExpireDate != null) {
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    if (downloadExpireDate.after(ts)) {
		ketDrawingDistReqDTO.setDownFlag("Y");
	    }
	}

	return ketDrawingDistReqDTO;
    }

    @SuppressWarnings("deprecation")
    @Override
    public KETDrawingDistReqDTO getDistDeptList(KETDrawingDistReqDTO paramObject) throws Exception {
	KETDrawingDistRequest ketDrawingDistRequest = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getOid());

	QuerySpec query = new QuerySpec();
	int idx = query.appendClassList(KETDrawingDistDept.class, true);
	query.appendWhere(
	        new SearchCondition(KETDrawingDistDept.class, "distReqReference.key.id", SearchCondition.EQUAL, CommonUtil
	                .getOIDLongValue(ketDrawingDistRequest)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(query);

	String drawingDistDeptNameArray = "";
	String drawingDistDeptArray = "";
	int i = 0;
	while (qr.hasMoreElements()) {

	    Object[] obj = (Object[]) qr.nextElement();
	    if (i != 0) {
		drawingDistDeptArray += ",";
		drawingDistDeptNameArray += ",";
	    }
	    KETDrawingDistDept ketDrawingDistDept = (KETDrawingDistDept) obj[0];
	    drawingDistDeptArray += ketDrawingDistDept.getDeptCode();
	    drawingDistDeptNameArray += ketDrawingDistDept.getDeptName();
	    i++;
	}

	paramObject.setDrawingDistDeptArray(drawingDistDeptArray);
	paramObject.setDistDeptName(drawingDistDeptNameArray);

	return paramObject;
    }

    @Override
    public List<Map<String, Object>> getDrawingDownHisry(KETDrawingDistReqDTO paramObject) throws Exception {
	String[] oidArr = paramObject.getOid().split(":");

	String queryStr = getSearchDownHisry(oidArr[1]);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> drawingDownHisList = new ArrayList<Map<String, Object>>();

	try {
	    drawingDownHisList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getDownHisResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return drawingDownHisList;
    }

    @Override
    public List<Map<String, Object>> getDistReqEpmDocList(KETDrawingDistReqDTO paramObject) throws Exception {
	String[] oidArr = paramObject.getOid().split(":");

	String queryStr = getSearchEPMDocQueryBySelectIn(oidArr[1]);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> distReqEpmDocList = new ArrayList<Map<String, Object>>();

	try {
	    distReqEpmDocList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getEpmResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return distReqEpmDocList;
    }

    @Override
    public List<Map<String, Object>> getDistReqWtDocList(KETDrawingDistReqDTO paramObject) throws Exception {
	String[] oidArr = paramObject.getOid().split(":");

	String queryProjStr = getSearchProjDocQueryBySelectIn(oidArr[1]);
	String queryTechStr = getSearchTechDocQueryBySelectIn(oidArr[1]);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> distReqWtDocList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> distReqProjDocList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> distReqTechDocList = new ArrayList<Map<String, Object>>();

	try {
	    distReqProjDocList = oDaoManager.queryForList(queryProjStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getProjDocResultSet(rs);
		}
	    });

	    distReqTechDocList = oDaoManager.queryForList(queryTechStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getTechDocResultSet(rs);
		}
	    });

	    distReqWtDocList = distReqProjDocList;

	    for (int i = 0; i < distReqTechDocList.size(); i++) {
		distReqWtDocList.add(distReqTechDocList.get(i));
	    }

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return distReqWtDocList;
    }

    private HashMap<String, Object> getDownHisResultSet(ResultSet rs) throws SQLException {
	HashMap<String, Object> drawingDownHis = new HashMap<String, Object>();
	drawingDownHis.put("distSubContractor", rs.getString("distSubContractor"));
	drawingDownHis.put("creator", rs.getString("creator"));
	drawingDownHis.put("downloadDate", rs.getString("downloadDate"));
	drawingDownHis.put("downloadFileName", rs.getString("downloadFileName"));
	drawingDownHis.put("downloadReason", rs.getString("downloadReason"));

	return drawingDownHis;
    }

    private HashMap<String, Object> getEpmResultSet(ResultSet rs) throws SQLException {

	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	KETMessageService messageService = KETMessageService.getMessageService(request);
	Locale locale = messageService.getLocale();

	HashMap<String, Object> epmDocument = new HashMap<String, Object>();

	epmDocument.put("drawingOid", rs.getString("drawingOid"));
	epmDocument.put("drawingNo", rs.getString("drawingNo")); // DrawingNo < ##sort
	epmDocument.put("drawingName", rs.getString("drawingName")); // DrawingName < ##sort
	epmDocument.put("ver", rs.getString("ver")); // Ver < ##sort
	epmDocument.put("status", State.toState(rs.getString("status")).getDisplay(locale)); // Status
	epmDocument.put("createDate", DateUtil.getDateString(rs.getTimestamp("createDate"), "date")); // CreateDate < ##sort
	epmDocument.put("holderOid", rs.getString("holderOid")); // File
	epmDocument.put("appDataOid", rs.getString("appDataOid")); // File
	epmDocument.put("extension", rs.getString("extension")); // File
	epmDocument.put("creator", rs.getString("creator")); // Creator < sort
	epmDocument.put("cadType", StringUtil.checkNull(rs.getString("cadType"))); // CADType < ##sort
	epmDocument.put("dummy", rs.getString("dummy")); // Dummy

	// epmDocument.put("requestDate", rs.getString("requestDate"));// RequestDate
	// epmDocument.put("approvalDate", rs.getString("approvalDate"));// ApprovalDate

	epmDocument.put("projectNo", StringUtil.checkNull(rs.getString("projectNo")));// ProjectNo
	epmDocument.put("projectName", StringUtil.checkNull(rs.getString("projectName")));// ProjectName
	epmDocument.put("createDeptName", rs.getString("createDeptName"));// CreateDeptName
	epmDocument.put("partNumber", StringUtil.checkNull(rs.getString("partNumber")));// PartNumber
	epmDocument.put("devStage", rs.getString("devStage"));// DevStage
	epmDocument.put("category", rs.getString("category"));// Category
	epmDocument.put("sheetType", rs.getString("sheetType"));// Category
	epmDocument.put("epmLinkOid", rs.getString("epmLinkOid"));

	List<HashMap<String, Object>> epmDocumentFileList = new ArrayList<HashMap<String, Object>>();
	// KETDrawingDistEpmLink ketDrawingDistEpmLink = (KETDrawingDistEpmLink) CommonUtil.getObject(rs.getString("epmLinkOid"));
	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(rs.getString("distOid"));
	String targetDrawingNo = rs.getString("drawingNo");

	try {
	    // 오라클 blob 낭비가 심하여 캐드서버에서 직접 파일을 내려받는 식으로 변경한다 2021-05-03
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	    KETStamping stamping = query.queryForFirstAByRoleB(KETStamping.class, KETStampDistLink.class, KETDrawingDistRequest.class,
		    drawingDistReq);
	    String stampPath = stamping.getStampFilePath();
	    int start = StringUtils.lastIndexOf(stampPath, "\\");

	    String pathName = StringUtils.substring(stampPath, 0, start);

	    List<KETDrawingDistFileType> fileTypeList = query.queryForListAByRoleB(KETDrawingDistFileType.class,
		    KETDrawingDistFileTypeLink.class, KETDrawingDistRequest.class, drawingDistReq);
	    for (KETDrawingDistFileType fileType : fileTypeList) {
		String extension = fileType.getDistFileType().toLowerCase();
		File targetFile = null;
		String fileName = "";

		if ("step".equals(extension)) {
		    extension = "stp";
		}
		if ("cat".equals(extension)) {
		    extension = "CATPart";
		}
		if ("pdf".equals(extension) || "dwg".equals(extension)) {
		    fileName = targetDrawingNo + "_s." + extension;
		} else {
		    fileName = targetDrawingNo + "." + extension;
		}
		targetFile = new File(pathName + "\\" + fileName);
		if (targetFile.exists()) {

		    String icon = ContentUtil.getFileIconUrl(fileName);
		    String realPath = targetFile.getAbsolutePath();
		    realPath = realPath.replaceAll("\\\\", "/");
		    String iconURLStr = "<a href=\"javascript:downloadReason('" + fileName + "', '" + realPath + "');\">";

		    iconURLStr = iconURLStr + "<img src=" + icon + " border=0/>";

		    HashMap<String, Object> epmDocumentFile = new HashMap<String, Object>();
		    epmDocumentFile.put("iconURLStr", iconURLStr);
		    epmDocumentFileList.add(epmDocumentFile);
		}

	    }
	    // QueryResult qr = PersistenceHelper.manager.navigate(ketDrawingDistEpmLink, "stampItem", KETStampEpmLink.class);
	    // if (qr.hasMoreElements()) {
	    // while (qr.hasMoreElements()) {
	    // KETStampingItem ketStampingItem = (KETStampingItem) qr.nextElement();
	    // ContentHolder contentHolder = ContentHelper.service.getContents(ketStampingItem);
	    //
	    // Vector contentList = ContentHelper.getContentListAll(contentHolder);
	    // for (int i = 0; i < contentList.size(); i++) {
	    //
	    // ContentItem contentItem = (ContentItem) contentList.elementAt(i);
	    // Kogger.debug(getClass(), i + " contentItem = " + contentItem);
	    //
	    // if (contentItem instanceof ApplicationData) {
	    //
	    // // ApplicationData appData = (ApplicationData) contentItem;
	    // // String fileName = appData.getFileName();
	    // // String downURL = (ContentHelper.getDownloadURL((ContentHolder) ketStampingItem, appData)).toString();
	    // HashMap<String, Object> epmDocumentFile = new HashMap<String, Object>();
	    //
	    // ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
	    // String icon = info.getIconURLStr();
	    //
	    // icon = icon.substring(icon.indexOf("<img"));
	    //
	    // String iconURLStr = "<a href=\"javascript:downloadReason('" + info.getName() + "', '" + info.getDownURL()
	    // + "');\">";
	    //
	    // iconURLStr = iconURLStr + icon;
	    //
	    // epmDocumentFile.put("iconURLStr", iconURLStr);
	    // // epmDocumentFile.put("icon", icon);
	    // // epmDocumentFile.put("downURL", info.getDownURL());
	    // // epmDocumentFile.put("fileName", info.getName());
	    //
	    // epmDocumentFileList.add(epmDocumentFile);
	    //
	    // }
	    // }
	    // }
	    // }
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (PropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	epmDocument.put("epmDocumentFileList", epmDocumentFileList);// Category

	return epmDocument;
    }

    private String getSearchDownHisry(String drawingDistReqstOid) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT 						    \n");
	sb.append("     TO_CHAR(A.CREATESTAMPA2, 'YYYY-MM-DD') downloadDate \n");
	sb.append("     ,C.NAME creator					    \n");
	sb.append("     ,A.DISTSUBCONTRACTOR distSubContractor					    \n");
	sb.append("     ,A.DOWNLOADFILENAME downloadFileName	            \n");
	sb.append("     ,A.DOWNLOADREASON downloadReason                    \n");
	sb.append(" FROM KETDrawingDownHis A,                               \n");
	sb.append("      WTUser B,                                          \n");
	sb.append("      People C                                           \n");
	sb.append(" WHERE A.IDA3A3 = '" + drawingDistReqstOid + "'		    \n");
	sb.append(" AND A.IDA3B3 = B.IDA2A2        			    \n");
	sb.append(" AND C.IDA3B4 = B.IDA2A2        			    \n");
	sb.append(" ORDER BY A.CREATESTAMPA2 DESC  			    \n");
	return sb.toString();
    }

    private String getSearchEPMDocQueryBySelectIn(String drawingDistReqstOid) throws Exception {

	// [EDM분석-14.06.24 - 도면과 관련된 쿼리 버그 및 성능향상을 위해 수정 TKLEE
	StringBuffer sb = new StringBuffer();

	// SELECT를 서버페이징시에 분해하여 처리함.
	// 1. StringValue를 applicationData, HolderToContent 등을 모두 select로 빼서 처리한다.

	// select된 것을 가져온 후에 in으로 넣어서 처리
	// EPM 기본 정보
	sb.append(" SELECT EPM.CLASSNAMEA2A2 || ':' || EPM.IDA2A2 AS DRAWINGOID \n");
	sb.append(" ,EM.DOCUMENTNUMBER AS DRAWINGNO \n");
	sb.append(" ,EM.NAME AS DRAWINGNAME \n");
	sb.append(" ,EPM.VERSIONIDA2VERSIONINFO AS VER \n");
	sb.append(" ,EPM.STATESTATE AS STATUS \n");
	sb.append(" ,EPM.CREATESTAMPA2 AS CREATEDATE \n");
	sb.append(" ,(SELECT NAME FROM PEOPLE WHERE EPM.IDA3D2ITERATIONINFO = IDA3B4 ) AS CREATOR \n");
	// Vault 관련
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID \n");
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n")
	        .append(" WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID \n");
	sb.append(" ,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1)) \n")
	        .append(" FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE EPM.IDA2A2 = HOLDER.IDA3A5 AND \n")
	        .append(" HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION \n");
	// CadType, Dummy
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'CADAppType') ) AS CADTYPE \n");
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'IsDummyFile') ) AS DUMMY \n");
	// ADDED BY TKLEE NEW COLUMN
	// 부품 번호 : PartNumber
	sb.append(" ,(SELECT LISTAGG(PMASTER.WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY PMASTER.WTPARTNUMBER ) AS PART_NO FROM EPMLINK L, \n")
	        .append(" WTPARTMASTER PMASTER WHERE L.IDA3A5 = EM.IDA2A2 AND L.IDA3B5 = PMASTER.IDA2A2 ) AS PARTNUMBER \n");
	// 도면 구분 : DevStage
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'DevStage') ) AS DEVSTAGE \n");
	// 도면 유형 : Category
	sb.append(" ,(SELECT VALUE FROM STRINGVALUE WHERE EPM.IDA2A2 = IDA3A4 AND IDA3A6 = (SELECT IDA2A2 FROM STRINGDEFINITION D \n")
	        .append(" WHERE D.NAME = 'CADCategory') ) AS CATEGORY \n");
	// 작성부서 : CreateDeptName
	sb.append(
	        " ,(SELECT LISTAGG(CASE WHEN ROWNUM <=1 THEN U.CREATORDEPTNAME ELSE NULL END, ',') WITHIN GROUP(ORDER BY U.CREATESTAMPA2\n")
	        .append(" DESC) FROM EDMUSERDATA U WHERE U.OBJBRANCHID = EPM.BRANCHIDITERATIONINFO ) AS CREATEDEPTNAME \n");
	// 상신일
	// 승인일
	// 프로젝트 번호 : ProjectNo
	sb.append(" ,(SELECT LISTAGG(PM.PJTNO, ',') WITHIN GROUP(ORDER BY PM.PJTNO ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER PM \n")
	        .append(" WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNO \n");
	// 프로젝트 명 : ProjectName
	sb.append(" ,(SELECT LISTAGG(PM.PJTNAME, ',') WITHIN GROUP(ORDER BY PM.PJTNAME ) FROM EPMDOCPROJECTLINK PJT,E3PSPROJECTMASTER \n")
	        .append(" PM WHERE EPM.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5 AND PJT.IDA3A5 = PM.IDA2A2) AS PROJECTNAME \n");

	sb.append("  ,DL.SHEETTYPE AS SHEETTYPE \n");
	sb.append("  ,DL.CLASSNAMEA2A2||':'||DL.IDA2A2 AS EPMLINKOID \n");
	sb.append("  ,DL.CLASSNAMEKEYROLEBOBJECTREF||':'||DL.IDA3B5 AS DISTOID \n");
	// From
	sb.append("  FROM EPMDOCUMENTMASTER EM \n");
	sb.append("      ,EPMDOCUMENT EPM \n");
	sb.append("      ,KETDRAWINGDISTEPMLINK DL \n");
	sb.append("  WHERE 1=1 \n");
	sb.append("  AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE \n");
	sb.append("  AND DL.IDA3A5 = EPM.IDA2A2 \n");
	sb.append("  AND DL.IDA3B5 ='" + drawingDistReqstOid + "' \n");
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Kogger.debug(getClass(), "######## 배포요청서 도면 리스트 검색 쿼리 : \n");

	return sb.toString();
    }

    private String getSearchTechDocQueryBySelectIn(String drawingDistReqstOid) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.OID                                 AS OID          \n");
	sb.append("      ,A.DOCLINKOID      			AS DOCLINKOID    \n");
	sb.append("      ,G.WTDOCUMENTNUMBER                    AS DOCUMENTNO    \n");
	sb.append("      ,A.TITLE                               AS TITLE         \n");
	sb.append("      ,NCV.VALUE                             AS CATEGORYNAME  \n");
	sb.append("      ,A.STATESTATE                          AS STATESTATE    \n");
	sb.append("      ,E.NAME                                AS STATE         \n");
	sb.append("      ,A.VERSIONIDA2VERSIONINFO              AS VER           \n");
	sb.append("      ,F.NAME                                AS CREATORNAME   \n");
	sb.append("      ,A.DEPTNAME                            AS DEPTNAME      \n");
	sb.append("      ,A.CREATESTAMPA2                       AS CREATEDATE    \n");
	sb.append("      ,A.HOLDEROID                           AS HOLDEROID     \n");
	sb.append("      ,A.APPDATAOID                          AS APPDATAOID    \n");
	sb.append("      ,A.EXTENSION                           AS EXTENSION     \n");
	sb.append("      ,A.FILENAME                            AS FILENAME      \n");
	sb.append("  FROM (                                                      \n");
	sb.append("        SELECT DOC.CLASSNAMEA2A2||':'||DOC.IDA2A2 OID         \n");
	sb.append("      	 ,DDC.CLASSNAMEA2A2||':'||DDC.IDA2A2 DOCLINKOID  \n");
	sb.append("              ,DOC.VERSIONIDA2VERSIONINFO                     \n");
	sb.append("              ,DOC.IDA2A2                                     \n");
	sb.append("              ,DOC.IDA3A2STATE                                \n");
	sb.append("              ,DOC.IDA3B2ITERATIONINFO                        \n");
	sb.append("              ,DOC.IDA3MASTERREFERENCE                        \n");
	sb.append("              ,DOC.STATESTATE                                 \n");
	sb.append("              ,DOC.LATESTITERATIONINFO                        \n");
	sb.append("              ,DOC.DIVISIONCODE                               \n");
	sb.append("              ,DOC.TITLE                                      \n");
	sb.append("              ,DOC.DEPTNAME                                   \n");
	sb.append("              ,TO_CHAR(DOC.CREATESTAMPA2, 'YYYY-MM-DD')                   AS CREATESTAMPA2 \n");
	sb.append("              ,holder.CLASSNAMEKEYROLEAOBJECTREF || ':' || holder.IDA3A5  AS HOLDEROID     \n");
	sb.append("              ,holder.CLASSNAMEKEYROLEBOBJECTREF || ':' || holder.IDA3B5  AS APPDATAOID    \n");
	sb.append("              ,REVERSE(SUBSTR(REVERSE(app.filename), 0, INSTR(REVERSE(app.filename), '.', 1, 1)-1)) AS EXTENSION \n");
	sb.append("              ,app.filename AS FILENAME \n");
	sb.append("          FROM KETTechnicalDOCUMENT DOC   \n");
	sb.append("              ,HOLDERTOCONTENT    holder  \n");
	sb.append("              ,APPLICATIONDATA    app     \n");
	sb.append("              ,KETDRAWINGDISTDOCLINK  DDC \n");
	sb.append("         WHERE LATESTITERATIONINFO = 1    \n");
	sb.append("           AND DOC.IDA2A2 = holder.IDA3A5 \n");
	sb.append("           AND holder.IDA3B5 = app.IDA2A2 \n");
	sb.append("           AND app.ROLE = 'PRIMARY'       \n");
	sb.append("           AND DOC.IDA2A2 = DDC.IDA3A5    \n");
	sb.append("           AND DDC.IDA3B5 = '" + drawingDistReqstOid + "' \n");
	sb.append("       ) A,                                    \n");
	sb.append("       KETTechnicalCategoryLink B,             \n");
	sb.append("       KETDOCUMENTCATEGORY      C,             \n");
	sb.append("       PHASELINK                D,             \n");
	sb.append("       PHASETEMPLATE            E,             \n");
	sb.append("       PEOPLE                   F,             \n");
	sb.append("       WTDocumentMaster         G,             \n");
	sb.append("       NUMBERCODEVALUE          NCV            \n");
	sb.append(" WHERE 1=1                                     \n");
	sb.append("   AND A.IDA2A2              = B.IDA3B5        \n");
	sb.append("   AND B.IDA3A5              = C.IDA2A2        \n");
	sb.append("   AND A.IDA3A2STATE         = D.IDA3A5        \n");
	sb.append("   AND D.IDA3B5              = E.IDA2A2        \n");
	sb.append("   AND E.PHASESTATE          = A.STATESTATE    \n");
	sb.append("   AND A.IDA3B2ITERATIONINFO = F.IDA3B4        \n");
	sb.append("   AND A.IDA3MASTERREFERENCE = G.IDA2A2        \n");
	sb.append("   AND C.CATEGORYCODE        =  NCV.CODE       \n");
	sb.append("   AND NCV.LANG              =  'ko'           \n");
	sb.append("   AND ( DIVISIONCODE in ('ER', 'CA') )        \n");
	sb.append("   AND ( E.PHASESTATE = 'APPROVED' )           \n");
	return sb.toString();
    }

    private String getSearchProjDocQueryBySelectIn(String drawingDistReqstOid) throws Exception {
	StringBuffer sb = new StringBuffer();
	sb.append("SELECT                                            \n");
	sb.append("       B.CATEGORYNAME     AS CATEGORYNAME         \n");
	sb.append("      ,B.CREATORNAME      AS CREATORNAME          \n");
	sb.append("      ,B.DOCLINKOID      AS DOCLINKOID            \n");
	sb.append("      ,B.STATE            AS STATE                \n");
	sb.append("      ,A.CLASSNAMEA2A2||':'||A.IDA2A2 AS OID      \n");
	sb.append("      ,A.DOCUMENTNO              AS DOCUMENTNO    \n");
	sb.append("      ,A.TITLE                   AS TITLE         \n");
	sb.append("      ,A.STATESTATE              AS STATESTATE    \n");
	sb.append("      ,A.VERSIONIDA2VERSIONINFO  AS VER           \n");
	sb.append("      ,A.DEPTNAME                AS DEPTNAME      \n");
	sb.append("      ,TO_CHAR(A.CREATESTAMPA2, 'YYYY-MM-DD') AS CREATEDATE  \n");
	sb.append("      ,(SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'BUYERSUMMIT' AND CODE = A.ISBUYERSUMMIT AND LANG = 'ko' ) AS BUYERSUMMIT    \n");
	sb.append("			,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n");
	sb.append("				WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID      \n");
	sb.append("			,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n");
	sb.append("				WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID     \n");
	sb.append("			,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1))                      \n");
	sb.append("				FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE A.IDA2A2 = HOLDER.IDA3A5 AND                         \n");
	sb.append("				HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION   \n");
	sb.append("			,(SELECT APP.FILENAME        \n");
	sb.append("				FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE A.IDA2A2 = HOLDER.IDA3A5 AND                         \n");
	sb.append("				HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS FILENAME   \n");
	sb.append("FROM KETPROJECTDOCUMENT A,                                                                 \n");
	sb.append("(                                                                                          \n");
	sb.append("SELECT * FROM (                                                                            \n");
	sb.append(" SELECT ROWNUM AS NUM, A.* FROM (                                                          \n");
	sb.append("SELECT A.IDA2A2                  AS OID                                                    \n");
	sb.append("      ,DDC.CLASSNAMEA2A2||':'||DDC.IDA2A2	AS DOCLINKOID                                 \n");
	sb.append("      ,NCV.VALUE                 AS CATEGORYNAME                                           \n");
	sb.append("      ,F.NAME                    AS CREATORNAME                                            \n");
	sb.append("      ,E.NAME                    AS STATE                                                  \n");
	sb.append("      ,A.TITLE                   AS TITLE                                                  \n");
	sb.append("  FROM                                                                                     \n");
	sb.append("      KETPROJECTDOCUMENT      A,                                                           \n");
	sb.append("      KETDocumentCategoryLink B,                                                           \n");
	sb.append("      KETDOCUMENTCATEGORY     C,                                                           \n");
	sb.append("      PHASELINK               D,                                                           \n");
	sb.append("      PHASETEMPLATE           E,                                                           \n");
	sb.append("      PEOPLE                  F,                                                           \n");
	sb.append("      WTDocumentMaster        G,                                                           \n");
	sb.append("      NUMBERCODEVALUE         NCV,                                                         \n");
	sb.append("      KETDRAWINGDISTDOCLINK   DDC                                                          \n");
	sb.append(" WHERE A.LATESTITERATIONINFO = 1                                                           \n");
	sb.append("   AND A.IDA2A2 = DDC.IDA3A5				                                      \n");
	sb.append("   AND DDC.IDA3B5 = '" + drawingDistReqstOid + "'         				      \n");
	sb.append("   AND A.IDA2A2              = B.IDA3B5                                                    \n");
	sb.append("   AND B.IDA3A5              = C.IDA2A2                                                    \n");
	sb.append("   AND A.IDA3A2STATE         = D.IDA3A5                                                    \n");
	sb.append("   AND D.IDA3B5              = E.IDA2A2                                                    \n");
	sb.append("   AND E.PHASESTATE          = A.STATESTATE                                                \n");
	sb.append("   AND A.IDA3B2ITERATIONINFO = F.IDA3B4                                                    \n");
	sb.append("   AND A.IDA3MASTERREFERENCE = G.IDA2A2                                                    \n");
	sb.append("   AND C.CATEGORYCODE        =  NCV.CODE                                                   \n");
	sb.append("   AND NCV.LANG              =  'ko'                                                       \n");
	sb.append("   AND ( A.DIVISIONCODE in ('ER', 'CA') )                                                  \n");
	sb.append("   AND ( A.STATESTATE = 'APPROVED' )    ) A                                                \n");
	sb.append(") A WHERE 1=1                                                                              \n");
	sb.append(") B                                                                                        \n");
	sb.append("WHERE A.IDA2A2 = B.OID                                                                     \n");
	return sb.toString();
    }

    private HashMap<String, Object> getProjDocResultSet(ResultSet rs) throws SQLException {

	// HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	// KETMessageService messageService = KETMessageService.getMessageService(request);
	// Locale locale = messageService.getLocale();

	HashMap<String, Object> wtDocument = new HashMap<String, Object>();

	wtDocument.put("oid", rs.getString("oid"));

	String pjtNo = "";

	KETProjectDocument docu = (KETProjectDocument) CommonUtil.getObject(rs.getString("oid"));

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(docu, "output", KETDocumentOutputLink.class);
	    if (qr.hasMoreElements()) {
		while (qr.hasMoreElements()) {
		    ProjectOutput po = (ProjectOutput) qr.nextElement();

		    E3PSTask task = (E3PSTask) po.getTask();
		    E3PSProject project = (E3PSProject) task.getProject();
		    project = ProjectHelper.getLastProject(project.getMaster());
		    if (!pjtNo.equals("")) {
			pjtNo += ", ";
		    }
		    pjtNo += StringUtil.checkNull(project.getPjtNo());
		}
	    }
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}

	wtDocument.put("pjtNo", pjtNo);

	wtDocument.put("documentNo", rs.getString("documentNo"));
	wtDocument.put("title", rs.getString("title"));
	wtDocument.put("categoryName", rs.getString("categoryName"));
	wtDocument.put("stateState", rs.getString("stateState"));
	wtDocument.put("state", rs.getString("state"));
	wtDocument.put("ver", rs.getString("ver"));
	wtDocument.put("creatorName", rs.getString("creatorName"));
	wtDocument.put("deptName", rs.getString("deptName"));
	wtDocument.put("createDate", rs.getString("createDate"));
	wtDocument.put("holderOid", rs.getString("holderOid"));
	wtDocument.put("appdataOid", rs.getString("appdataOid"));
	wtDocument.put("extension", rs.getString("extension"));
	wtDocument.put("fileName", rs.getString("fileName"));

	wtDocument.put("docLinkOid", rs.getString("docLinkOid"));

	List<HashMap<String, Object>> docDocumentFileList = new ArrayList<HashMap<String, Object>>();
	KETDrawingDistDocLink ketDrawingDistDocLink = (KETDrawingDistDocLink) CommonUtil.getObject(rs.getString("docLinkOid"));

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(ketDrawingDistDocLink, "stampDocItem", KETStampDocLink.class);
	    if (qr.hasMoreElements()) {
		while (qr.hasMoreElements()) {
		    KETStampDocItem ketStampDocItem = (KETStampDocItem) qr.nextElement();
		    ContentHolder contentHolder = ContentHelper.service.getContents(ketStampDocItem);

		    Vector contentList = ContentHelper.getContentListAll(contentHolder);
		    for (int i = 0; i < contentList.size(); i++) {

			ContentItem contentItem = (ContentItem) contentList.elementAt(i);
			Kogger.debug(getClass(), i + " contentItem = " + contentItem);

			if (contentItem instanceof ApplicationData) {

			    HashMap<String, Object> docDocumentFile = new HashMap<String, Object>();

			    ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
			    String icon = info.getIconURLStr();

			    icon = icon.substring(icon.indexOf("<img"));

			    String iconURLStr = "<a href=\"javascript:downloadReason('" + info.getName() + "', '" + info.getDownURL()
				    + "');\">";

			    iconURLStr = iconURLStr + icon;

			    docDocumentFile.put("iconURLStr", iconURLStr);

			    docDocumentFileList.add(docDocumentFile);

			}
		    }
		}
	    }
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (PropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}

	wtDocument.put("docDocumentFileList", docDocumentFileList);// Category

	return wtDocument;
    }

    private HashMap<String, Object> getTechDocResultSet(ResultSet rs) throws SQLException {

	// HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	// KETMessageService messageService = KETMessageService.getMessageService(request);
	// Locale locale = messageService.getLocale();

	HashMap<String, Object> wtDocument = new HashMap<String, Object>();

	wtDocument.put("oid", rs.getString("oid"));
	String pjtNo = "";
	wtDocument.put("pjtNo", pjtNo);
	wtDocument.put("documentNo", rs.getString("documentNo"));
	wtDocument.put("title", rs.getString("title"));
	wtDocument.put("categoryName", rs.getString("categoryName"));
	wtDocument.put("stateState", rs.getString("stateState"));
	wtDocument.put("state", rs.getString("state"));
	wtDocument.put("ver", rs.getString("ver"));
	wtDocument.put("creatorName", rs.getString("creatorName"));
	wtDocument.put("deptName", rs.getString("deptName"));
	wtDocument.put("createDate", rs.getString("createDate"));
	wtDocument.put("holderOid", rs.getString("holderOid"));
	wtDocument.put("appdataOid", rs.getString("appdataOid"));
	wtDocument.put("extension", rs.getString("extension"));
	wtDocument.put("fileName", rs.getString("fileName"));

	wtDocument.put("docLinkOid", rs.getString("docLinkOid"));

	List<HashMap<String, Object>> docDocumentFileList = new ArrayList<HashMap<String, Object>>();
	KETDrawingDistDocLink ketDrawingDistDocLink = (KETDrawingDistDocLink) CommonUtil.getObject(rs.getString("docLinkOid"));

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(ketDrawingDistDocLink, "stampDocItem", KETStampDocLink.class);
	    if (qr.hasMoreElements()) {
		while (qr.hasMoreElements()) {
		    KETStampDocItem ketStampDocItem = (KETStampDocItem) qr.nextElement();
		    ContentHolder contentHolder = ContentHelper.service.getContents(ketStampDocItem);
		    //
		    // ContentItem contentItem = ContentHelper.getPrimary((FormatContentHolder) contentHolder);
		    //
		    // if (contentItem instanceof ApplicationData) {
		    //
		    // // ApplicationData appData = (ApplicationData) contentItem;
		    // // String fileName = appData.getFileName();
		    // // String downURL = (ContentHelper.getDownloadURL((ContentHolder) ketStampingItem, appData)).toString();
		    // HashMap<String, Object> docDocumentFile = new HashMap<String, Object>();
		    //
		    // ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
		    // String icon = info.getIconURLStr();
		    //
		    // icon = icon.substring(icon.indexOf("<img"));
		    //
		    // String iconURLStr = "<a href=\"javascript:downloadReason('" + info.getName() + "', '" + info.getDownURL()
		    // + "');\">";
		    //
		    // iconURLStr = iconURLStr + icon;
		    //
		    // docDocumentFile.put("iconURLStr", iconURLStr);
		    // // epmDocumentFile.put("icon", icon);
		    // // epmDocumentFile.put("downURL", info.getDownURL());
		    // // epmDocumentFile.put("fileName", info.getName());
		    //
		    // docDocumentFileList.add(docDocumentFile);
		    //
		    // }

		    // ContentItem temp = ContentHelper.getPrimary((FormatContentHolder) contentHolder);
		    // Vector localVector2 = contentHolder.getContentVector();

		    Vector contentList = ContentHelper.getContentListAll(contentHolder);
		    for (int i = 0; i < contentList.size(); i++) {

			ContentItem contentItem = (ContentItem) contentList.elementAt(i);
			Kogger.debug(getClass(), i + " contentItem = " + contentItem);

			if (contentItem instanceof ApplicationData) {

			    HashMap<String, Object> docDocumentFile = new HashMap<String, Object>();

			    ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
			    String icon = info.getIconURLStr();

			    icon = icon.substring(icon.indexOf("<img"));

			    String iconURLStr = "<a href=\"javascript:downloadReason('" + info.getName() + "', '" + info.getDownURL()
				    + "');\">";

			    iconURLStr = iconURLStr + icon;

			    docDocumentFile.put("iconURLStr", iconURLStr);

			    docDocumentFileList.add(docDocumentFile);

			}
		    }
		}
	    }
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (PropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}

	wtDocument.put("docDocumentFileList", docDocumentFileList);// Category

	return wtDocument;
    }

    /*
     * 등록
     */
    @Override
    public KETDrawingDownHis saveReason(KETDrawingDistReqDTO paramObject) throws Exception {
	Kogger.debug(getClass(), paramObject);

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getDrawingDistRequestOid());
	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();

	KETDrawingDownHis ketDrawingDownHis = KETDrawingDownHis.newKETDrawingDownHis();

	if (!StringUtil.checkNull(paramObject.getDistDeptName()).equals("")
	        && !StringUtil.checkNull(paramObject.getDistSubcontractor()).equals("")) {
	    paramObject.setDistSubcontractor("," + paramObject.getDistSubcontractor());
	}

	ketDrawingDownHis.setDistSubcontractor(StringUtil.checkNull(paramObject.getDistDeptName()) + paramObject.getDistSubcontractor());

	ketDrawingDownHis.setDownloadReason(paramObject.getDownloadReason());
	ketDrawingDownHis.setDistReq(drawingDistReq);
	ketDrawingDownHis.setDownloadDate(new Timestamp(System.currentTimeMillis()));
	ketDrawingDownHis.setDownloadFileName(paramObject.getFileName());
	ketDrawingDownHis.setUser(wtuser);

	return (KETDrawingDownHis) PersistenceHelper.manager.save(ketDrawingDownHis);
    }

    @Override
    public String drawingDistTotalDown(String oid) throws Exception {
	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(oid);
	String downUrl = "";
	try {

	    // 오라클 blob 낭비가 심하여 캐드서버에서 직접 파일을 내려받는 식으로 변경한다 2021-05-03
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	    KETStamping stamping = query.queryForFirstAByRoleB(KETStamping.class, KETStampDistLink.class, KETDrawingDistRequest.class,
		    drawingDistReq);
	    String stampPath = stamping.getStampFilePath();
	    int start = StringUtils.lastIndexOf(stampPath, "\\");

	    String pathName = StringUtils.substring(stampPath, 0, start);

	    File targetFile = new File(pathName + "\\drawing_all.zip");
	    if (targetFile.exists()) {
		pathName = pathName.replaceAll("\\\\", "/");
		downUrl = "/plm/ext/pathDownLoad.do?path=" + pathName + "/drawing_all.zip";
	    }

	    // 아래는 파일다운로드 변경방식을 변경하기 전 소스이다

	    // QueryResult qr = PersistenceHelper.manager.navigate(drawingDistReq, "stamp", KETStampDistLink.class);
	    // if (qr.hasMoreElements()) {
	    // while (qr.hasMoreElements()) {
	    // KETStamping ketStamping = (KETStamping) qr.nextElement();
	    // ContentHolder contentHolder = ContentHelper.service.getContents(ketStamping);
	    //
	    // Vector contentList = ContentHelper.getContentListAll(contentHolder);
	    // for (int i = 0; i < contentList.size(); i++) {
	    // ContentItem contentItem = (ContentItem) contentList.elementAt(i);
	    // if (contentItem instanceof ApplicationData) {
	    // ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
	    // downUrl = info.getDownURL().toString();
	    //
	    // }
	    // }
	    // }
	    // }
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (PropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}

	return downUrl;
    }

    @Override
    public KETDrawingDistRequest ReApproved(KETDrawingDistReqDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(paramObject.getOid());
	if (drawingDistReq.getLifeCycleState().toString().equals("APPROVED")) {
	    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) drawingDistReq, State.toState("UNDERREVIEW"), true);

	}
	LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) drawingDistReq, State.toState("APPROVED"), true);

	return drawingDistReq;
    }

    /*
     * 제품 ECO 승인시 BACKGROUND로 배포괄리 저장처리
     */
    public void backgroundSave(KETProdChangeOrder eco) throws Exception {

	// KETProdChangeOrder eco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
	boolean cudrawing = false;
	boolean sendInfo;
	boolean chkPartNumber = false;
	boolean devlevelCheck = false;

	List checkPartList = new ArrayList();
	String partNumber = "";
	String displayPartNumber = "";
	while (qr.hasMoreElements()) {
	    KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();

	    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
	    if (queryResult.hasMoreElements()) {
		sendInfo = false;
		KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();
		String partOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
		WTPart part = (WTPart) CommonUtil.getObject(partOid);
		part = PartBaseHelper.service.getLatestPart(part.getNumber());
		String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 제품 F, 반제품 H
		String partDevlevel = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartDevLevel); // 개발 : PC003, 양산 : PC004
		String HomepageIF = PartSpecGetter.getPartSpec(part, PartSpecEnum.HomepageIF);

		System.out.println("StandardDrawingDistService ::::::::::::::::::::: partOid :::::::::::: " + partOid);
		System.out.println("StandardDrawingDistService ::::::::::::::::::::: part.getNumber :::::::::::: " + part.getNumber());

		if ("PC004".equals(partDevlevel) && "YES".equals(HomepageIF)) {// 개발단계 : 양산, 홈페이지 전송대상일 경우만.
		    devlevelCheck = true;
		} else {
		    if (eco.getChangeReason().indexOf("REASON_10") != -1 && "YES".equals(HomepageIF)) {
			devlevelCheck = true;
		    } else {
			devlevelCheck = false;
		    }
		}

		WTPart displayPart = null;
		// 제품인경우는 기존 하위 로직을 테우고
		// 반제품인경우에는 제품을 찾아서 그 제품에 도면을 물려 저장한다.
		// 단 두번째 부터의 부품이 값을 이전 부품과 비교 하여 동일한 부품이면 스킵
		if (devlevelCheck) {
		    // if ("F".equals(partType)) { // 제품인경우
		    // displayPart = part;
		    // displayPartNumber = displayPart.getNumber();
		    // String divisionGubun = displayPartNumber.substring(0, 2);
		    //
		    // // 제품번호 앞자리가 KR/K5 가 아니면 반제품을 찾는다. 부품의 속성정보는 반제품으로 구한다.
		    //
		    // if ((!"KR".equals(divisionGubun)) || (!"K5".equals(divisionGubun))) {
		    // String prodPartNumber = "H" + part.getNumber();
		    // WTPart halfPart = PartBaseHelper.service.getLatestPart(prodPartNumber);
		    //
		    // if (halfPart != null) {
		    // part = halfPart;
		    // sendInfo = true;
		    // } else {
		    // sendInfo = false;
		    // }
		    // }
		    // } else if ("H".equals(partType)) { // 반제품인경우 제품을 구한다.
		    //
		    // String prodPartNumber = part.getNumber().substring(1);
		    // WTPart prodPart = PartBaseHelper.service.getLatestPart(prodPartNumber);
		    //
		    // if (prodPart != null) {
		    // displayPart = prodPart;
		    // displayPartNumber = displayPart.getNumber();
		    // sendInfo = true;
		    // } else {
		    // sendInfo = false;
		    // }
		    //
		    // } else {
		    // sendInfo = false;
		    //
		    // }

		    // 기존 로직은 무의미하여 주석처리함. 반제품, 상품, 제품 어느것에도 도면이 연계될 수 있다.
		    // (1)고객제출도가 연계되어 있는 부품을 홈페이지 이관 대상으로 선별하거나, (2)도면을 이관하지 않고 속성정보만 이관하는 부품을 이관대상으로한다

		    String drawingDistFileTypeArray = "";

		    String TwoYN = PartSpecGetter.getPartSpec(part, PartSpecEnum.Homepage2DIF);
		    String ThreeYN = PartSpecGetter.getPartSpec(part, PartSpecEnum.Hompage3DIF);

		    // if ("Y".equals(eco.getCuDrawingChangeYn())) {
		    if ("YES".equals(TwoYN)) {
			drawingDistFileTypeArray = "PDF,DWG,";
		    }
		    if ("YES".equals(ThreeYN)) {
			drawingDistFileTypeArray += "STEP,IGS,JPG,THREEPDF";
		    }
		    // }

		    System.out.println("background drawingDistFileTypeArray: " + drawingDistFileTypeArray);

		    PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
		    WTPart halbPart = null;
		    if (ptype == PartTypeEnum.HALB || ptype == PartTypeEnum.FERT || ptype == PartTypeEnum.HAWA) {

			if (ptype == PartTypeEnum.FERT) {

			    if (!part.getNumber().startsWith("KR") && part.getNumber().startsWith("K")) {

			    } else {
				halbPart = PartBaseHelper.service.getLatestPart("H" + part.getNumber());
			    }

			}
			System.out.println("isEmpty Check ::::::::::::::::::::::: " + StringUtils.isEmpty(drawingDistFileTypeArray));
			if (StringUtils.isEmpty(drawingDistFileTypeArray)) {
			    sendInfo = true;
			} else {
			    StringUtils.removeEnd(drawingDistFileTypeArray, ",");

			    WTPartMaster partMast = (WTPartMaster) part.getMaster();
			    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

			    if (epmLinkList != null && epmLinkList.size() > 0) {
				for (EPMLink link : epmLinkList) {
				    if ("CU_DRAWING".equals(link.getReferenceType())) {
					sendInfo = true;
				    }
				}
			    } else {
				if (halbPart != null) {
				    part = halbPart;
				    partMast = (WTPartMaster) halbPart.getMaster();
				    epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

				    if (epmLinkList != null && epmLinkList.size() > 0) {
					for (EPMLink link : epmLinkList) {
					    if ("CU_DRAWING".equals(link.getReferenceType())) {
						sendInfo = true;
					    }
					}
				    }
				}
			    }
			}
		    }

		    if (sendInfo) {
			displayPart = part;
			displayPartNumber = part.getNumber();

			chkPartNumber = duplicatePart(checkPartList, displayPartNumber);

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> chkPartNumber ::::::::::::: " + chkPartNumber);

			if (chkPartNumber) {

			    // title 은 display part
			    // refPart 는 실제 참조 part
			    // partNumber = part.getNumber 이면 스킵

			    System.out.println("part number: " + displayPart.getNumber());
			    System.out.println("part oid : " + displayPart.getPersistInfo().getObjectIdentifier().getId());

			    KETDrawingDistRequest drawingDistReq = KETDrawingDistRequest.newKETDrawingDistRequest();

			    // 해당된 PART 가 반제품인경우는 하위 부품을 체크 하여 그 부품으로 정보를 변경한다.
			    Kogger.debug(getClass(), "backgroundSave start1");

			    drawingDistReq.setNumber(this.getDrawingNumber());

			    Kogger.debug(getClass(), "backgroundSave end1");

			    drawingDistReq.setName(String.valueOf(eco.getPersistInfo().getObjectIdentifier().getId()));
			    drawingDistReq.setTitle(displayPart.getNumber());
			    drawingDistReq.setDistType("APPROVED");
			    drawingDistReq.setBackgroundYn("Y");
			    drawingDistReq.setRefPart(String.valueOf(part.getPersistInfo().getObjectIdentifier().getId()));
			    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
			    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
			    String writeDeptKrName = (userDept == null) ? "" : userDept.getName();

			    String writeDeptCode = (userDept == null) ? "" : userDept.getCode();

			    drawingDistReq.setWriteDeptKrName(writeDeptKrName);
			    drawingDistReq.setWriteDeptEnName(userDept.getEnName());
			    drawingDistReq.setWriteDeptCode(writeDeptCode);

			    WTContainerRef containerRef = WCUtil.getWTContainerRef();
			    String lcName = "KET_COMMON_LC";
			    LifeCycleHelper.setLifeCycle((LifeCycleManaged) drawingDistReq,
				    LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));
			    String folderPath = "";
			    if (CommonUtil.isKETSUser()) {
				folderPath = "/Default/KETS/초기유동/";
			    } else {
				folderPath = "/Default/자동차사업부/초기유동/";
			    }

			    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			    FolderHelper.assignLocation((FolderEntry) drawingDistReq, folder);

			    drawingDistReq = (KETDrawingDistRequest) TeamHelper.setTeamTemplate(drawingDistReq,
				    TeamHelper.service.getTeamTemplate("Default"));

			    drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.save(drawingDistReq);

			    if (!StringUtils.isEmpty(drawingDistFileTypeArray)) {
				String[] drawingDistFileTypeArrayItems = drawingDistFileTypeArray.split(",");
				for (String fileTypeItem : drawingDistFileTypeArrayItems) {

				    KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();

				    DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);

				    fileTypes.setDistFileType(drawingDistFileType.toString());
				    fileTypes.setDistReq(drawingDistReq);

				    PersistenceHelper.manager.save(fileTypes);
				}
			    }

			    // KETProdECAEpmDocLink
			    KETDrawingDistProdEOLink prodEoLink = KETDrawingDistProdEOLink.newKETDrawingDistProdEOLink(eco, drawingDistReq);
			    PersistenceHelper.manager.save(prodEoLink);
			    // CU도면변경여부 Y 인경우 start
			    // if ("Y".equals(eco.getCuDrawingChangeYn())) {
			    WTPartMaster partMast = (WTPartMaster) part.getMaster();
			    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

			    if (epmLinkList != null && epmLinkList.size() > 0) {
				for (EPMLink link : epmLinkList) {
				    if ("CU_DRAWING".equals(link.getReferenceType())) {
					EPMDocumentMaster epmMast = link.getEpmMaster();
					EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);

					if ("YES".equals(TwoYN) && "YES".equals(ThreeYN)) {

					} else {

					    if ("YES".equals(ThreeYN) && !latestEpmDoc.getCADName().toUpperCase().endsWith(".PRT")) {
						continue;
					    }

					    if ("YES".equals(TwoYN) && latestEpmDoc.getCADName().toUpperCase().endsWith(".PRT")) {
						continue;
					    }
					}

					KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(latestEpmDoc,
					        drawingDistReq);
					epmLink.setSheetType("All");
					epmLink.setRefPart(part.getPersistInfo().getObjectIdentifier().getStringValue());
					PersistenceHelper.manager.save(epmLink);
					cudrawing = true;
				    }
				}
			    }
			    if (cudrawing) {
				try {
				    // stamping 처리하는 부분 추가해야함.
				    Kogger.debug(getClass(), "### OID : " + CommonUtil.getOIDString(drawingDistReq));
				    StampingQueueSender sender = StampingQueueSender.getInstance();
				    sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
				    Kogger.debug(getClass(), "### Queue : Sended");
				} catch (Exception e) {
				    Kogger.error(getClass(), e);
				}

				// 저장된 부품정보를 LIST 담으면
				checkPartList.add(displayPartNumber);

			    }
			    // } // CU도면변경여부 Y 인경우 end
			}
		    }
		}
	    }
	}

    }

    // 전송된 정보(제품번호)를 한번만 보낼수 있게 체크
    private boolean duplicatePart(List chkPartList, String partNumber) throws Exception {
	boolean bchk = true;
	if (chkPartList.size() == 0) {
	    bchk = true;
	} else {
	    for (int i = 0; i < chkPartList.size(); i++) {
		String chkPartNumber = chkPartList.get(i).toString();
		if (partNumber.equals(chkPartNumber))
		    bchk = false;
	    }

	}

	return bchk;
    }

    public void reStamping(String reqOid) throws Exception {

	String req = "ext.ket.edm.entity.KETDrawingDistRequest:" + reqOid;

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(req);
	try {
	    // stamping 처리하는 부분 추가해야함.
	    Kogger.debug(getClass(), "### OID : " + CommonUtil.getOIDString(drawingDistReq));
	    StampingQueueSender sender = StampingQueueSender.getInstance();
	    sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
	    Kogger.debug(getClass(), "### Queue : Sended");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public List<Map<String, Object>> getPlmHpIfListByOid(String oid, String partOid) throws Exception {

	String queryStr = getSearchPlmHpIfListByOid(oid);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> plmHpIfList = new ArrayList<Map<String, Object>>();

	try {
	    plmHpIfList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getPlmHpIfResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return plmHpIfList;
    }

    @Override
    public List<Map<String, Object>> getPlmHpIfList(KETDrawingDistReqDTO paramObject) throws Exception {

	/*
	 * 테스트로 도면유형 알아보기
	 */
	/*
	 * EPMDocument epm = (EPMDocument) CommonUtil.getObject("wt.epm.EPMDocument:992295935");
	 * 
	 * HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	 * 
	 * String category = ""; if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) { category =
	 * EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN); }
	 * 
	 * String cadCategory = (String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY);
	 * 
	 * // 3D 2D 판단하기 String cadName = epm.getCADName(); // em450022_pps_assy.drw
	 * 
	 * Kogger.debug("ViewEPMDocument", null, null, "cadName: " + epm.getCADName()); boolean is3D =
	 * cadName.toUpperCase().endsWith(".PRT") || cadName.toUpperCase().endsWith(".ASM");
	 */
	String queryStr = getSearchPlmHpIfList(paramObject);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> plmHpIfList = new ArrayList<Map<String, Object>>();

	try {
	    plmHpIfList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getPlmHpIfResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return plmHpIfList;
    }

    private HashMap<String, Object> getPlmHpIfResultSet(ResultSet rs) throws SQLException {

	HashMap<String, Object> plmHpIf = new HashMap<String, Object>();
	boolean bimg = false;
	boolean b2d = false;
	boolean bstep = false;
	boolean bigs = false;
	boolean b3d = false;

	plmHpIf.put("ECOID", StringUtil.checkNull(rs.getString("ECOID")));
	plmHpIf.put("DIVISIONFLAG", StringUtil.checkNull(rs.getString("DIVISIONFLAG")));

	plmHpIf.put("HPSENDDATE", StringUtil.checkNull(rs.getString("HPSENDDATE")));
	plmHpIf.put("HPSENDSTATUS", StringUtil.checkNull(rs.getString("HPSENDSTATUS")));
	plmHpIf.put("HPSENDSTATUSNAME", StringUtil.checkNull(rs.getString("HPSENDSTATUSNAME")));

	plmHpIf.put("PARTNAME", rs.getString("PARTNAME"));
	plmHpIf.put("PARTNUMBER", rs.getString("PARTNUMBER"));
	plmHpIf.put("WTPARTNUMBER", rs.getString("WTPARTNUMBER"));
	plmHpIf.put("PARTIDA2A2", rs.getString("PARTIDA2A2"));
	plmHpIf.put("PARTCLASSNAME", rs.getString("PARTCLASSNAME"));
	plmHpIf.put("REQIDA2A2", rs.getString("REQIDA2A2"));
	plmHpIf.put("REQCLASSNAME", rs.getString("REQCLASSNAME"));
	plmHpIf.put("STAMPSTATUS", StringUtil.checkNull(rs.getString("STAMPSTATUS")));
	plmHpIf.put("STAMPSTATUSNAME", StringUtil.checkNull(rs.getString("STAMPSTATUSNAME")));

	// 제품 이미지 가져오기......
	try {
	    WTPart wtpart = (WTPart) CommonUtil.getObject(rs.getString("PARTCLASSNAME") + ":" + rs.getString("PARTIDA2A2"));

	    // 부품의 3d가 N 이면 아래행위를 해라 조건 추가
	    String threedType = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.Hompage3DIF);
	    if (threedType.equals("NO")) {

		// WTPart wtpart = (WTPart) CommonUtil.getObject("wt.part.WTPart:992907668");
		ContentHolder holder = (ContentHolder) wtpart;
		holder = ContentHelper.service.getContents(holder);
		Vector contentVec = ContentHelper.getContentListAll(holder);
		Iterator<ContentItem> iter = contentVec.iterator();
		while (iter.hasNext()) {
		    ContentItem item = iter.next();
		    if (item.getRole() == ContentRoleType.PRIMARY) {

			if (item instanceof ApplicationData) {

			    ContentInfo info = ContentUtil.getContentInfo(holder, item);
			    plmHpIf.put("IMGCONTENTOID", info.getContentOid());
			    plmHpIf.put("IMGDOWNURL", info.getDownURL());
			    plmHpIf.put("IMGDOWNURLSTR", info.getDownURLStr());
			    plmHpIf.put("IMGICONURLSTR", info.getIconURLStr());
			    plmHpIf.put("IMGNAME", info.getName());
			    plmHpIf.put("IMGTYPE", info.getType());
			    plmHpIf.put("IMGBUSINESSTYPE", info.getBusinessType());

			    bimg = true;
			    break;
			}

		    }
		}
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {

	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(StringUtil.checkNull(rs
		    .getString("REQCLASSNAME")) + ":" + StringUtil.checkNull(rs.getString("REQIDA2A2")));

	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    List<KETDrawingDistEpmLink> epmDocLinkList;
	    epmDocLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class, KETDrawingDistRequest.class, drawingDistReq);

	    for (int k = 0; k < epmDocLinkList.size(); k++) {

		KETDrawingDistEpmLink epmDocLink = epmDocLinkList.get(k);
		EPMDocument epmDoc = epmDocLink.getDistEpm();
		QueryResult qr = PersistenceHelper.manager.navigate(epmDocLink, "stampItem", KETStampEpmLink.class);

		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {

			KETStampingItem ketStampingItem = (KETStampingItem) qr.nextElement();
			ContentHolder contentHolder = ContentHelper.service.getContents(ketStampingItem);
			Vector contentList = ContentHelper.getContentListAll(contentHolder);
			for (int i = 0; i < contentList.size(); i++) {
			    ContentItem contentItem = (ContentItem) contentList.elementAt(i);

			    if (contentItem instanceof ApplicationData) {

				ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
				String filename = info.getName();
				System.out.println(filename);
				int j = info.getName().lastIndexOf(".");
				String renameFile = info.getName().substring(0, j);
				// boolean is3D = renameFile.toUpperCase().endsWith("_3D");

				int i3d = renameFile.indexOf("3D");
				boolean is3D = i3d > 0 ? true : false;

				if (is3D) {
				    if (".stp".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("STEPCONTENTOID", info.getContentOid());
					plmHpIf.put("STEPDOWNURL", info.getDownURL());
					plmHpIf.put("STEPDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("STEPICONURLSTR", info.getIconURLStr());
					plmHpIf.put("STEPNAME", info.getName());
					plmHpIf.put("STEPTYPE", info.getType());
					plmHpIf.put("STEPBUSINESSTYPE", info.getBusinessType());

					bstep = true;
					// break;
				    } else if (".pdf".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("PLANE3DCONTENTOID", info.getContentOid());
					plmHpIf.put("PLANE3DDOWNURL", info.getDownURL());
					plmHpIf.put("PLANE3DDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("PLANE3DICONURLSTR", info.getIconURLStr());
					plmHpIf.put("PLANE3DNAME", info.getName());
					plmHpIf.put("PLANE3DTYPE", info.getType());
					plmHpIf.put("PLANE3DBUSINESSTYPE", info.getBusinessType());
					b3d = true;
					// break;
				    } else if (".igs".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("IGSCONTENTOID", info.getContentOid());
					plmHpIf.put("IGSDOWNURL", info.getDownURL());
					plmHpIf.put("IGSDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("IGSICONURLSTR", info.getIconURLStr());
					plmHpIf.put("IGSNAME", info.getName());
					plmHpIf.put("IGSTYPE", info.getType());
					plmHpIf.put("IGSBUSINESSTYPE", info.getBusinessType());
					bigs = true;
					// break;
				    } else if (".jpg".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("IMGCONTENTOID", info.getContentOid());
					plmHpIf.put("IMGDOWNURL", info.getDownURL());
					plmHpIf.put("IMGDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("IMGICONURLSTR", info.getIconURLStr());
					plmHpIf.put("IMGNAME", info.getName());
					plmHpIf.put("IMGTYPE", info.getType());
					plmHpIf.put("IMGBUSINESSTYPE", info.getBusinessType());
					bimg = true;
					// break;
				    }
				} else {

				    if (".pdf".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("PLANECONTENTOID", info.getContentOid());
					plmHpIf.put("PLANEDOWNURL", info.getDownURL());
					plmHpIf.put("PLANEDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("PLANEICONURLSTR", info.getIconURLStr());
					plmHpIf.put("PLANENAME", info.getName());
					plmHpIf.put("PLANETYPE", info.getType());
					plmHpIf.put("PLANEBUSINESSTYPE", info.getBusinessType());
					b2d = true;
					// break;
				    } else if (".igs".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("IGSCONTENTOID", info.getContentOid());
					plmHpIf.put("IGSDOWNURL", info.getDownURL());
					plmHpIf.put("IGSDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("IGSICONURLSTR", info.getIconURLStr());
					plmHpIf.put("IGSNAME", info.getName());
					plmHpIf.put("IGSTYPE", info.getType());
					plmHpIf.put("IGSBUSINESSTYPE", info.getBusinessType());
					bigs = true;
					// break;
				    } else if (".jpg".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("IMGCONTENTOID", info.getContentOid());
					plmHpIf.put("IMGDOWNURL", info.getDownURL());
					plmHpIf.put("IMGDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("IMGICONURLSTR", info.getIconURLStr());
					plmHpIf.put("IMGNAME", info.getName());
					plmHpIf.put("IMGTYPE", info.getType());
					plmHpIf.put("IMGBUSINESSTYPE", info.getBusinessType());
					bimg = true;
				    } else if (".stp".equals(info.getName().substring(j).toLowerCase())) {
					plmHpIf.put("STEPCONTENTOID", info.getContentOid());
					plmHpIf.put("STEPDOWNURL", info.getDownURL());
					plmHpIf.put("STEPDOWNURLSTR", info.getDownURLStr());
					plmHpIf.put("STEPICONURLSTR", info.getIconURLStr());
					plmHpIf.put("STEPNAME", info.getName());
					plmHpIf.put("STEPTYPE", info.getType());
					plmHpIf.put("STEPBUSINESSTYPE", info.getBusinessType());
					bstep = true;
				    }

				}
			    }
			}

		    }
		}

	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	if (bimg == false) {
	    plmHpIf.put("IMGCONTENTOID", "");
	    plmHpIf.put("IMGDOWNURL", "");
	    plmHpIf.put("IMGDOWNURLSTR", "");
	    plmHpIf.put("IMGICONURLSTR", "");
	    plmHpIf.put("IMGNAME", "");
	    plmHpIf.put("IMGTYPE", "");
	    plmHpIf.put("IMGBUSINESSTYPE", "");
	}
	if (b2d == false) {
	    plmHpIf.put("PLANECONTENTOID", "");
	    plmHpIf.put("PLANEDOWNURL", "");
	    plmHpIf.put("PLANEDOWNURLSTR", "");
	    plmHpIf.put("PLANEICONURLSTR", "");
	    plmHpIf.put("PLANENAME", "");
	    plmHpIf.put("PLANETYPE", "");
	    plmHpIf.put("PLANEBUSINESSTYPE", "");
	}
	if (bstep == false) {
	    plmHpIf.put("STEPCONTENTOID", "");
	    plmHpIf.put("STEPDOWNURL", "");
	    plmHpIf.put("STEPDOWNURLSTR", "");
	    plmHpIf.put("STEPICONURLSTR", "");
	    plmHpIf.put("STEPNAME", "");
	    plmHpIf.put("STEPTYPE", "");
	    plmHpIf.put("STEPBUSINESSTYPE", "");

	}
	if (b3d == false) {
	    plmHpIf.put("PLANE3DCONTENTOID", "");
	    plmHpIf.put("PLANE3DDOWNURL", "");
	    plmHpIf.put("PLANE3DDOWNURLSTR", "");
	    plmHpIf.put("PLANE3DICONURLSTR", "");
	    plmHpIf.put("PLANE3DNAME", "");
	    plmHpIf.put("PLANE3DTYPE", "");
	    plmHpIf.put("PLANE3DBUSINESSTYPE", "");

	}
	if (bigs == false) {
	    plmHpIf.put("IGSCONTENTOID", "");
	    plmHpIf.put("IGSDOWNURL", "");
	    plmHpIf.put("IGSDOWNURLSTR", "");
	    plmHpIf.put("IGSICONURLSTR", "");
	    plmHpIf.put("IGSNAME", "");
	    plmHpIf.put("IGSTYPE", "");
	    plmHpIf.put("IGSBUSINESSTYPE", "");
	}

	return plmHpIf;
    }

    private String getSearchPlmHpIfList(KETDrawingDistReqDTO paramObject) throws Exception {
	StringBuilder sb = new StringBuilder();
	sb.append("\n  SELECT ROW_NUMBER() over (ORDER BY REQ.REQIDA2A2 DESC) RN,      ");
	sb.append("\n  	   (SELECT NAME FROM WTPARTMASTER WHERE WTPARTNUMBER = REQ.TITLE) PARTNAME,  ");
	sb.append("\n  	   REQ.TITLE PARTNUMBER,               ");
	sb.append("\n  	   P.IDA2A2 PARTIDA2A2, PM.WTPARTNUMBER,                      ");
	sb.append("\n  	   TO_CHAR(REQ.HPSENDDATE, 'YYYY/MM/DD') HPSENDDATE,           ");
	sb.append("\n  	   CASE WHEN REQ.HPSENDSTATUS = 'Y' THEN '완료'                ");
	sb.append("\n  	   ELSE '대기' END HPSENDSTATUSNAME,                           ");
	sb.append("\n  	   REQ.HPSENDSTATUS,                                           ");
	sb.append("\n         P.CLASSNAMEA2A2 PARTCLASSNAME,                           ");
	sb.append("\n         REQ.REQIDA2A2,REQ.REQCLASSNAME,                          ");
	sb.append("\n         REQ.STAMPSTATUS,                                         ");
	sb.append("\n         REQ.STAMPSTATUSNAME,EO.ECOID,                            ");
	sb.append("\n      CASE WHEN C.DIVISIONFLAG = 'C' THEN '자동차사업부'            ");
	sb.append("\n      ELSE CASE WHEN C.DIVISIONFLAG = 'E' THEN '전자사업부'         ");
	sb.append("\n      ELSE CASE WHEN C.DIVISIONFLAG = 'N' THEN 'KETN'             ");
	sb.append("\n      END END END DIVISIONFLAG                                    ");
	sb.append("\n  FROM WTPARTMASTER PM,  WTPART P, KETPartClassLink CL, KETPartClassification C, ");
	sb.append("\n     (                                                            ");
	sb.append("\n     SELECT A.TITLE, 		                              ");
	sb.append("\n      	 A.IDA2A2 REQIDA2A2,                                  ");
	sb.append("\n      	 TO_NUMBER(A.REFPART) REFPART,                         ");
	sb.append("\n            A.CLASSNAMEA2A2 REQCLASSNAME,                         ");
	sb.append("\n            S.STAMPSTATUS,                                        ");
	sb.append("\n            A.HPSENDSTATUS,                                       ");
	sb.append("\n            A.HPSENDDATE,                                         ");
	sb.append("\n            A.CREATESTAMPA2,                                      ");
	sb.append("\n            CASE WHEN S.STAMPSTATUS = 'FAIL' THEN '실패'          ");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'SUCCESS' THEN '성공'  ");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'BEFORE'  THEN '대기중'");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'QUEUE'   THEN '변환중'");
	sb.append("\n            END END END END STAMPSTATUSNAME                       ");
	sb.append("\n     FROM                                                         ");
	sb.append("\n     KETDrawingDistRequest A, KETStamping S,KETStampDistLink L    ");
	sb.append("\n     WHERE                                                        ");
	sb.append("\n       1=1                                                        ");
	sb.append("\n       AND A.DISTTYPE = 'APPROVED'                                ");
	sb.append("\n       AND A.BackgroundYn = 'Y'                                   ");
	sb.append("\n       AND A.IDA2A2 = L.IDA3B5(+)                                 ");
	sb.append("\n       AND L.IDA3A5 = S.IDA2A2(+)                                 ");
	if ("SUCCESS".equals(paramObject.getStampstatus())) {
	    sb.append("\n    AND S.STAMPSTATUS = '").append(paramObject.getStampstatus()).append("'");
	} else if ("QUEUE".equals(paramObject.getStampstatus())) {
	    sb.append("\n    AND S.STAMPSTATUS = '").append(paramObject.getStampstatus()).append("'");
	} else if ("BEFORE".equals(paramObject.getStampstatus())) {
	    sb.append("\n    AND S.STAMPSTATUS = '").append(paramObject.getStampstatus()).append("'");
	} else if ("FAIL".equals(paramObject.getStampstatus())) {
	    sb.append("\n    AND S.STAMPSTATUS = '").append(paramObject.getStampstatus()).append("'");
	}

	if ("Y".equals(StringUtils.stripToEmpty(paramObject.getHpstatus()))) {
	    sb.append("\n    AND A.HPSENDSTATUS = '").append(paramObject.getHpstatus()).append("'");

	}
	if ("N".equals(StringUtils.stripToEmpty(paramObject.getHpstatus()))) {
	    sb.append("\n    AND A.HPSENDSTATUS IS NULL");

	}

	sb.append("\n     ) REQ,   ");
	sb.append("\n     KETProdChangeOrder EO, KETDrawingDistProdEOLink EOLINK,  ");
	sb.append("\n (select MAX(EO.ECOID)||MAX(TITLE)KEEP(DENSE_RANK FIRST ORDER BY EO.ECOID DESC)      AS key	");
	sb.append("\n	FROM WTPARTMASTER PM,  WTPART P, KETPartClassLink CL, KETPartClassification C, 			");
	sb.append("\n (                                                            					");
	sb.append("\n       SELECT A.TITLE,                                       						");
	sb.append("\n	           A.IDA2A2 REQIDA2A2,                                  				");
	sb.append("\n	           TO_NUMBER(A.REFPART) REFPART,                         				");
	sb.append("\n	            A.CLASSNAMEA2A2 REQCLASSNAME,                         				");
	sb.append("\n	            S.STAMPSTATUS,                                        				");
	sb.append("\n	            A.HPSENDSTATUS,                                       				");
	sb.append("\n	            A.HPSENDDATE,                                         				");
	sb.append("\n	            A.CREATESTAMPA2,                                      				");
	sb.append("\n	            CASE WHEN S.STAMPSTATUS = 'FAIL' THEN '실패'          				");
	sb.append("\n	            ELSE CASE WHEN S.STAMPSTATUS = 'SUCCESS' THEN '성공'  				");
	sb.append("\n	            ELSE CASE WHEN S.STAMPSTATUS = 'BEFORE'  THEN '대기중'				");
	sb.append("\n	            ELSE CASE WHEN S.STAMPSTATUS = 'QUEUE'   THEN '변환중'				");
	sb.append("\n	            END END END END STAMPSTATUSNAME                       				");
	sb.append("\n	     FROM                                                         				");
	sb.append("\n	     KETDrawingDistRequest A, KETStamping S,KETStampDistLink L    				");
	sb.append("\n	     WHERE                                                        				");
	sb.append("\n        1=1                                                        					");
	sb.append("\n          AND A.DISTTYPE = 'APPROVED'                                					");
	sb.append("\n          AND A.BackgroundYn = 'Y'                                   					");
	sb.append("\n          AND A.IDA2A2 = L.IDA3B5(+)                                 					");
	sb.append("\n          AND L.IDA3A5 = S.IDA2A2(+)                                 					");
	sb.append("\n          AND A.HPSENDSTATUS IS NULL										");
	sb.append("\n ) REQ,   												");
	sb.append("\n KETProdChangeOrder EO, KETDrawingDistProdEOLink EOLINK  						");
	sb.append("\n WHERE PM.IDA2A2 = P.IDA3MASTERREFERENCE                          					");
	sb.append("\n   AND P.IDA2A2 = REQ.REFPART                                   					");
	sb.append("\n   AND EO.IDA2A2 = EOLINK.IDA3A5                                 					");
	sb.append("\n   AND REQ.REQIDA2A2 = EOLINK.IDA3B5                                					");
	sb.append("\n   AND PM.IDA2A2 = CL.IDA3B5(+)                                     					");
	sb.append("\n	AND CL.IDA3A5 = C.IDA2A2(+)                        					");
	sb.append("\n	group by TITLE									");
	sb.append("\n	having count(*) > 1) MAX_TAB								");
	sb.append("\n WHERE PM.IDA2A2 = P.IDA3MASTERREFERENCE                          ");
	sb.append("\n 	AND P.IDA2A2 = REQ.REFPART                                   ");
	sb.append("\n 	AND EO.IDA2A2 = EOLINK.IDA3A5                                 ");
	sb.append("\n 	AND REQ.REQIDA2A2 = EOLINK.IDA3B5                                ");
	sb.append("\n   AND PM.IDA2A2 = CL.IDA3B5(+)                                     ");
	sb.append("\n	AND CL.IDA3A5 = C.IDA2A2(+)                                      ");
	sb.append("\n   AND EO.ECOID||REQ.TITLE = MAX_TAB.KEY                            ");
	if (!"".equals(StringUtils.stripToEmpty(paramObject.getPartNo()))) {
	    sb.append("\n      AND PM.WTPARTNUMBER = '").append(paramObject.getPartNo()).append("'");
	}

	if (!"".equals(StringUtils.stripToEmpty(paramObject.getEcoNo()))) {
	    sb.append("\n      AND EO.ECOID = '").append(paramObject.getEcoNo()).append("'");
	}
	if (!"".equals(StringUtils.stripToEmpty(paramObject.getDivisionflag()))) {
	    sb.append("\n      AND C.DIVISIONFLAG = '").append(paramObject.getDivisionflag()).append("'");
	}
	String startDate = StringUtils.stripToEmpty(paramObject.getCreateStartDate()) != "" ? StringUtils.stripToEmpty(paramObject
	        .getCreateStartDate()) : "";
	String endDate = StringUtils.stripToEmpty(paramObject.getCreateEndDate()) != "" ? StringUtils.stripToEmpty(paramObject
	        .getCreateEndDate()) : "";

	if (!"".equals(startDate)) {
	    if (!"".equals(endDate)) {
		sb.append("\n AND TO_CHAR(REQ.CREATESTAMPA2, 'YYYY-MM-DD') BETWEEN '" + startDate + "' AND '" + endDate + "'");
	    } else {
		sb.append("\n AND TO_CHAR(REQ.CREATESTAMPA2, 'YYYY-MM-DD') BETWEEN '" + startDate + "' AND TO_CHAR(SYSDATE, 'YYYY-MM-DD')");
	    }

	}

	return sb.toString();
    }

    private String getSearchPlmHpIfListByOid(String oid) throws Exception {
	StringBuilder sb = new StringBuilder();
	sb.append("\n  SELECT ROW_NUMBER() over (ORDER BY REQ.REQIDA2A2 DESC) RN,      ");
	sb.append("\n  	   (SELECT NAME FROM WTPARTMASTER WHERE WTPARTNUMBER = REQ.TITLE) PARTNAME,  ");
	sb.append("\n  	   REQ.TITLE PARTNUMBER,               ");
	sb.append("\n  	   P.IDA2A2 PARTIDA2A2, PM.WTPARTNUMBER,                      ");
	sb.append("\n  	   TO_CHAR(REQ.HPSENDDATE, 'YYYY/MM/DD') HPSENDDATE,           ");
	sb.append("\n  	   CASE WHEN REQ.HPSENDSTATUS = 'Y' THEN '완료'                ");
	sb.append("\n  	   ELSE '대기' END HPSENDSTATUSNAME,                           ");
	sb.append("\n  	   REQ.HPSENDSTATUS,                                           ");
	sb.append("\n         P.CLASSNAMEA2A2 PARTCLASSNAME,                           ");
	sb.append("\n         REQ.REQIDA2A2,REQ.REQCLASSNAME,                          ");
	sb.append("\n         REQ.STAMPSTATUS,                                         ");
	sb.append("\n         REQ.STAMPSTATUSNAME,EO.ECOID,                            ");
	sb.append("\n      CASE WHEN C.DIVISIONFLAG = 'C' THEN '자동차사업부'            ");
	sb.append("\n      ELSE CASE WHEN C.DIVISIONFLAG = 'E' THEN '전자사업부'         ");
	sb.append("\n      ELSE CASE WHEN C.DIVISIONFLAG = 'N' THEN 'KETN'             ");
	sb.append("\n      END END END DIVISIONFLAG                                    ");
	sb.append("\n  FROM WTPARTMASTER PM,  WTPART P, KETPartClassLink CL, KETPartClassification C, ");
	sb.append("\n     (                                                            ");
	sb.append("\n     SELECT A.TITLE, 		                              ");
	sb.append("\n      	 A.IDA2A2 REQIDA2A2,                                  ");
	sb.append("\n      	 TO_NUMBER(A.REFPART) REFPART,                         ");
	sb.append("\n            A.CLASSNAMEA2A2 REQCLASSNAME,                         ");
	sb.append("\n            S.STAMPSTATUS,                                        ");
	sb.append("\n            A.HPSENDSTATUS,                                       ");
	sb.append("\n            A.HPSENDDATE,                                         ");
	sb.append("\n            CASE WHEN S.STAMPSTATUS = 'FAIL' THEN '실패'          ");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'SUCCESS' THEN '성공'  ");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'BEFORE'  THEN '대기중'");
	sb.append("\n            ELSE CASE WHEN S.STAMPSTATUS = 'QUEUE'   THEN '변환중'");
	sb.append("\n            END END END END STAMPSTATUSNAME                       ");
	sb.append("\n     FROM                                                         ");
	sb.append("\n     KETDrawingDistRequest A, KETStamping S,KETStampDistLink L    ");
	sb.append("\n     WHERE                                                        ");
	sb.append("\n       1=1                                                        ");
	sb.append("\n       AND A.DISTTYPE = 'APPROVED'                                ");
	sb.append("\n       AND A.BackgroundYn = 'Y'                                   ");
	sb.append("\n       AND A.IDA2A2 = L.IDA3B5(+)                                 ");
	sb.append("\n       AND L.IDA3A5 = S.IDA2A2(+)                                 ");
	sb.append("\n     ) REQ, KETProdChangeOrder EO, KETDrawingDistProdEOLink EOLINK  ");
	sb.append("\n WHERE PM.IDA2A2 = P.IDA3MASTERREFERENCE                          ");
	// sb.append("\n 		  AND P.IDA2A2 = REQ.TITLE                                   ");
	sb.append("\n 		  AND P.IDA2A2 = REQ.REFPART                                   ");
	sb.append("\n 		  AND EO.IDA2A2 = EOLINK.IDA3A5                                 ");
	sb.append("\n 		  AND REQ.REQIDA2A2 = EOLINK.IDA3B5                                ");
	sb.append("\n             AND PM.IDA2A2 = CL.IDA3B5(+)                                     ");
	sb.append("\n	 	  AND CL.IDA3A5 = C.IDA2A2(+)                                      ");
	sb.append("\n 		  AND REQ.REQIDA2A2 = ").append(oid);

	return sb.toString();
    }

    public void test() throws Exception {

	// KETProdChangeOrder eco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
	// 배포 객체 정보를 읽어 들여 (KETDrawingDistRequest)
	// 링크된 도면 정보를 찾는다. (KETDrawingDistEpmLink)
	//

	String reqOid = "ext.ket.edm.entity.KETDrawingDistRequest:991599027";

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistEpmLink> disEpmLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class,
	        KETDrawingDistRequest.class, drawingDistReq);

	for (KETDrawingDistEpmLink distEpmlink : disEpmLinkList) {

	    List<KETStampEpmLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampEpmLink.class, KETDrawingDistEpmLink.class,
		    distEpmlink);
	    for (KETStampEpmLink itemLink : stampingItemLinkList) {
		KETStampingItem item = itemLink.getStampItem();

		ContentHolder contentHolder = ContentHelper.service.getContents(item);

		Vector contentList = ContentHelper.getContentListAll(contentHolder);

		for (int i = 0; i < contentList.size(); i++) {

		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);
		    String filePath = "";

		    try {
			ApplicationData appData = (ApplicationData) CommonUtil.getObject(contentItem.getPersistInfo().getObjectIdentifier()
			        .getStringValue());
			Persistable persistable = appData.getStreamData().getObject(); // StoredItem 또는 StreamData
			if (persistable instanceof StoredItem) {
			    StoredItem si = (StoredItem) persistable;
			    FileFolder folder = si.getFolder(); // fvfolder 값
			    FvMount mount = StandardFvService.getLocalMount(folder); // FvFolder에 연결된 fvMount 값
			    String path = mount.getPath(); // FvMount의 local Path
			    long filename = si.getUniqueSequenceNumber(); // FvItem의 10진수 파일명
			    String fn = StoredItem.buildFileName(filename); // 16진수 파일명
			    filePath = path + "\\" + fn;

			} else if (persistable instanceof StreamData) {
			    StreamData sd = (StreamData) persistable;
			    System.out.println("1 " + sd.getType());
			    System.out.println("2 " + sd.getUniqueSequenceNumber());
			    System.out.println("3 " + sd.getStreamId());
			    System.out.println("4 " + sd.getPersistInfo().getObjectIdentifier().getStringValue());
			    System.out.println("5 " + sd.getDisplayType());
			    System.out.println("6 " + sd.getLobLoc());
			    // 로컬에 파일 생성로직하였음.....
			    // 이건 전송시에 기능으로 이동해야함....

			    int j = appData.getFileName().lastIndexOf(".");

			    String renameFile = sd.getPersistInfo().getObjectIdentifier().getId()
				    + appData.getFileName().substring(j).toLowerCase();

			    String tempDir = "F:/temp";
			    // String tempDir = System.getProperty("java.io.tmpdir");

			    InputStream in = sd.retrieveStream();
			    File attachfile = new File(tempDir + File.separator + appData.getFileName()); // 파일 저장 위치
			    FileOutputStream fileOut = new FileOutputStream(attachfile);
			    byte[] buffer = new byte[1024];
			    int c;
			    while ((c = in.read(buffer)) != -1)
				fileOut.write(buffer, 0, c);
			    fileOut.close();

			    File refile = new File(tempDir + File.separator + renameFile); // 파일 저장 위치

			    attachfile.renameTo(refile);

			    /*
		             * Socket s = new Socket("localhost", 3333);
		             * 
		             * BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		             * System.out.println("파일명 : " + attachfile); bw.write(attachfile + "\n"); bw.flush();
		             * 
		             * DataInputStream dis = new DataInputStream(new FileInputStream(attachfile)); DataOutputStream dos = new
		             * DataOutputStream(s.getOutputStream());
		             * 
		             * int b = 0; while ((b = dis.read()) != -1) { dos.writeByte(b); dos.flush();
		             * 
		             * }
		             * 
		             * dis.close(); dos.close(); s.close(); dis = null; dos = null; s = null; System.exit(1);
		             */
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }

		    if (contentItem instanceof ApplicationData) {

			HashMap<String, Object> epmDocumentFile = new HashMap<String, Object>();
			ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
			System.out.println("getName " + info.getName());
			System.out.println("getDownURLStr " + info.getDownURLStr());
			System.out.println("getIconURLStr " + info.getIconURLStr());
			System.out.println("getType " + info.getType());
			System.out.println("getContentOid " + info.getContentOid());
			System.out.println("getDownURL " + info.getDownURL());
			System.out.println("getBusinessType " + info.getBusinessType());
			System.out.println("getClass " + info.getClass());
		    }

		}

	    }
	}

    }

    public boolean sendHp(KETDrawingDistReqDTO paramObject) throws Exception {
	Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());

	System.out.println("## HOMEPAGE SEND start " + new Date());

	boolean ret = false;
	List<Map<String, Object>> distReqEpmDocList = this.sendHpList(paramObject);

	System.out.println("## HOMEPAGE SEND SELECT AND FILE COPY " + new Date());

	// distReqEpmDocList 정보로 mssql 에 저장 후 리턴
	PlmHpIfCtl send = new PlmHpIfCtl();
	boolean bSuccess = send.savePlmHpIf(distReqEpmDocList);

	System.out.println("## HOMEPAGE SEND DATA INSERT  " + new Date());

	if (bSuccess)
	    ret = saveSuccessFlag(distReqEpmDocList);
	// 리스트 의 값으로 if date , if send 상태 save

	Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());

	System.out.println("## PLM SEND STATUS UPDATE  " + new Date());

	System.out.println("## HP elapsed " + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 / 60) + ":"
	        + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 % 60));

	return ret;
    }

    private boolean saveSuccessFlag(List<Map<String, Object>> distReqEpmDocList) throws Exception {

	boolean ret = false;

	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date));

	for (Map<String, Object> sendList : distReqEpmDocList) {
	    String reqStringValue = sendList.get("REQCLASSNAME") + ":" + sendList.get("REQIDA2A2");
	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqStringValue);

	    drawingDistReq.setHpSendStatus("Y");

	    drawingDistReq.setHpSendDate(Timestamp.valueOf(today));

	    PersistenceServerHelper.manager.update(drawingDistReq);

	    ret = true;

	}

	return ret;
    }

    @Override
    public List<Map<String, Object>> sendHpList(KETDrawingDistReqDTO paramObject) throws Exception {
	// String[] oidArr = paramObject.getOid().split("^");

	// String oid = "ext.ket.edm.entity.KETDrawingDistRequest:" + paramObject.getOid();

	String queryStr = getSendHpList(paramObject);

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> drawingDownHisList = new ArrayList<Map<String, Object>>();

	try {
	    drawingDownHisList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getSendHpResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return drawingDownHisList;
    }

    private String returnReqOidList(String reqOid) throws Exception {
	String retReqOid = "";
	if (reqOid.lastIndexOf(",") == -1) {
	    return "'" + reqOid + "'";
	} else {

	    String[] strings = reqOid.split(",");
	    for (String string : strings) {
		retReqOid += "'" + string + "',";
	    }
	    return retReqOid.substring(0, retReqOid.lastIndexOf(","));
	}

    }

    private String checkDocument(String partNumber) {
	String checkPartNumber = "";

	if (partNumber.trim().length() > 0) {
	    if ("7".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "ST" + partNumber.trim();
	    } else if ("6".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "MG" + partNumber.trim();
	    } else if ("5".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "JB" + partNumber.trim();
	    } else if ("4".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "EM" + partNumber.trim();
	    } else {
		checkPartNumber = partNumber.trim();
	    }
	} else {
	    checkPartNumber = partNumber.trim();
	}
	return checkPartNumber;
    }

    private HashMap<String, Object> getSendHpResultSet(ResultSet rs) throws SQLException {

	System.out.println("## PLM DB RESULTS start " + new Date());

	HashMap<String, Object> epmDocument = new HashMap<String, Object>();
	String sPartNumberDrawingNumber = "";
	boolean bAdmin = false;
	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData pData = new PeopleData(wtuser);
	    epmDocument.put("spMasterName", (String) pData.name);
	    epmDocument.put("spMasterEmail", (String) pData.email);
	    bAdmin = true;
	} catch (WTException e) {
	} catch (Exception e) {
	}

	if (bAdmin == false) {
	    epmDocument.put("spMasterName", "엄태훈");
	    epmDocument.put("spMasterEmail", "usol@ket.com");
	}
	String directYn = StringUtil.checkNull(rs.getString("directYn"));// PLM에서 관리자가 설계변경(ECO)없이 속성정보만 이관시 사용하는 플래그

	System.out.println(" ###################################### directYn ################################################# ");
	System.out.println(" ###################################### " + directYn + " ################################################# ");
	System.out.println(" ###################################### directYn ################################################# ");

	epmDocument.put("REQIDA2A2", StringUtil.checkNull(rs.getString("REQIDA2A2")));
	epmDocument.put("REQCLASSNAME", StringUtil.checkNull(rs.getString("REQCLASSNAME")));
	epmDocument.put("spCategoryCode", StringUtil.checkNull(rs.getString("spCategoryCode")));
	epmDocument.put("spClassification", StringUtil.checkNull(rs.getString("spClassification")));
	epmDocument.put("PartName", StringUtil.checkNull(rs.getString("PartName")));
	epmDocument.put("PartNumber", StringUtil.checkNull(rs.getString("PartNumber")));
	epmDocument.put("spProjectNo", StringUtil.checkNull(rs.getString("spProjectNo")));
	epmDocument.put("spEoNo", StringUtil.checkNull(rs.getString("spEoNo")));
	epmDocument.put("spDivision", StringUtil.checkNull(rs.getString("spDivision")));
	epmDocument.put("spPartNameHis", StringUtil.checkNull(rs.getString("spPartNameHis")));
	epmDocument.put("spPartRevision", StringUtil.checkNull(rs.getString("spPartRevision")));
	epmDocument.put("spPartType", StringUtil.checkNull(rs.getString("spPartType")));
	epmDocument.put("spPartDevLevel", StringUtil.checkNull(rs.getString("spPartDevLevel")));
	epmDocument.put("spIsDeleted", StringUtil.checkNull(rs.getString("spIsDeleted")));
	epmDocument.put("spBOMFlag", StringUtil.checkNull(rs.getString("spBOMFlag")));
	epmDocument.put("spWeightUnit", StringUtil.checkNull(rs.getString("spWeightUnit")));
	epmDocument.put("spNetWeight", StringUtil.checkNull(rs.getString("spNetWeight")));
	epmDocument.put("spTotalWeight", StringUtil.checkNull(rs.getString("spTotalWeight")));
	epmDocument.put("spScrabWeight", StringUtil.checkNull(rs.getString("spScrabWeight")));
	epmDocument.put("spIsYazaki", StringUtil.checkNull(rs.getString("spIsYazaki")));
	epmDocument.put("spYazakiNo", StringUtil.checkNull(rs.getString("spYazakiNo")));
	epmDocument.put("spManufAt", StringUtil.checkNull(rs.getString("spManufAt")));
	epmDocument.put("spManufacPlace", StringUtil.checkNull(rs.getString("spManufacPlace")));
	epmDocument.put("spCustomRev", StringUtil.checkNull(rs.getString("spCustomRev")));
	epmDocument.put("spScrabCode", StringUtil.checkNull(rs.getString("spScrabCode")));
	epmDocument.put("spWaterProof", StringUtil.checkNull(rs.getString("spWaterProof")));
	epmDocument.put("spMaterialMark", StringUtil.checkNull(rs.getString("spMaterialMark")));
	epmDocument.put("spColor", StringUtil.checkNull(rs.getString("spColor")));
	epmDocument.put("spColorPurch", StringUtil.checkNull(rs.getString("spColorPurch")));
	epmDocument.put("spColorElec", StringUtil.checkNull(rs.getString("spColorElec")));
	epmDocument.put("spSeries", StringUtil.checkNull(rs.getString("spSeries")));
	epmDocument.put("spSeriesLAMP", StringUtil.checkNull(rs.getString("spSeriesLAMP")));
	epmDocument.put("spSeriesBulb", StringUtil.checkNull(rs.getString("spSeriesBulb")));
	epmDocument.put("spProdSize", StringUtil.checkNull(rs.getString("spProdSize")));
	epmDocument.put("spEnvFriend", StringUtil.checkNull(rs.getString("spEnvFriend")));
	epmDocument.put("spTGenConn", StringUtil.checkNull(rs.getString("spTGenConn")));
	epmDocument.put("spTPCBConn", StringUtil.checkNull(rs.getString("spTPCBConn")));
	epmDocument.put("spTLAMPConn", StringUtil.checkNull(rs.getString("spTLAMPConn")));
	epmDocument.put("spTBulbConn", StringUtil.checkNull(rs.getString("spTBulbConn")));
	epmDocument.put("spTHiVolt", StringUtil.checkNull(rs.getString("spTHiVolt")));
	epmDocument.put("spTCharger", StringUtil.checkNull(rs.getString("spTCharger")));
	epmDocument.put("spT2Charger", StringUtil.checkNull(rs.getString("spT2Charger")));
	epmDocument.put("spT1HiVoltFuz", StringUtil.checkNull(rs.getString("spT1HiVoltFuz")));
	epmDocument.put("spTHiVoltFuse", StringUtil.checkNull(rs.getString("spTHiVoltFuse")));
	epmDocument.put("spTJnB", StringUtil.checkNull(rs.getString("spTJnB")));
	epmDocument.put("spTPRA", StringUtil.checkNull(rs.getString("spTPRA")));
	epmDocument.put("spTGNSS", StringUtil.checkNull(rs.getString("spTGNSS")));
	epmDocument.put("spTComModule", StringUtil.checkNull(rs.getString("spTComModule")));
	epmDocument.put("spTMultiMedUnit", StringUtil.checkNull(rs.getString("spTMultiMedUnit")));
	epmDocument.put("spLanceType", StringUtil.checkNull(rs.getString("spLanceType")));
	epmDocument.put("spNoOfPole", StringUtil.checkNull(rs.getString("spNoOfPole")));
	// 매칭터미널
	if (StringUtil.checkNull(rs.getString("spMTerminal")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMTerminal")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMTerminal")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMTerminal", matchingString);
	} else {
	    epmDocument.put("spMTerminal", "");
	}
	// 매칭커넥터
	if (StringUtil.checkNull(rs.getString("spMConnector")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMConnector")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMConnector")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMConnector", matchingString);
	} else {
	    epmDocument.put("spMConnector", "");
	}
	// 매칭벌브
	if (StringUtil.checkNull(rs.getString("spMatchBulb")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMatchBulb")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMatchBulb")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMatchBulb", matchingString);
	} else {
	    epmDocument.put("spMatchBulb", "");
	}
	// 매칭클립
	if (StringUtil.checkNull(rs.getString("spMClip")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMClip")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMClip")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMClip", matchingString);
	} else {
	    epmDocument.put("spMClip", "");
	}
	// 매칭리어홀더
	if (StringUtil.checkNull(rs.getString("spMRH")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMRH")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMRH")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMRH", matchingString);
	} else {
	    epmDocument.put("spMRH", "");
	}
	// 매칭커버
	if (StringUtil.checkNull(rs.getString("spMCover")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMCover")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMCover")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMCover", matchingString);
	} else {
	    epmDocument.put("spMCover", "");
	}
	// 매칭와이어씰
	if (StringUtil.checkNull(rs.getString("spMWireSeal")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMWireSeal")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMWireSeal")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMWireSeal", matchingString);
	} else {
	    epmDocument.put("spMWireSeal", "");
	}
	epmDocument.put("spLockingType", StringUtil.checkNull(rs.getString("spLockingType")));
	epmDocument.put("spLockTTML", StringUtil.checkNull(rs.getString("spLockTTML")));
	epmDocument.put("spLockTConn", StringUtil.checkNull(rs.getString("spLockTConn")));
	epmDocument.put("spRepresentM", StringUtil.checkNull(rs.getString("spRepresentM")));
	epmDocument.put("spMaterMaker", StringUtil.checkNull(rs.getString("spMaterMaker")));
	epmDocument.put("spMaterType", StringUtil.checkNull(rs.getString("spMaterType")));
	epmDocument.put("spMaterialInfo", StringUtil.checkNull(rs.getString("spMaterialInfo")));
	epmDocument.put("spMaterNotFe", StringUtil.checkNull(rs.getString("spMaterNotFe")));
	epmDocument.put("spMaterDie", StringUtil.checkNull(rs.getString("spMaterDie")));
	epmDocument.put("spMaterPurch", StringUtil.checkNull(rs.getString("spMaterPurch")));
	epmDocument.put("spPlating", StringUtil.checkNull(rs.getString("spPlating")));
	epmDocument.put("spPlatingPurch", StringUtil.checkNull(rs.getString("spPlatingPurch")));
	epmDocument.put("spCustomPartNo", StringUtil.checkNull(rs.getString("spCustomPartNo")));
	epmDocument.put("spPermitVolt", StringUtil.checkNull(rs.getString("spPermitVolt")));
	epmDocument.put("spWireRangeAWG", StringUtil.checkNull(rs.getString("spWireRangeAWG")));
	epmDocument.put("spWireRAWGElec", StringUtil.checkNull(rs.getString("spWireRAWGElec")));
	epmDocument.put("spWireRangeMm", StringUtil.checkNull(rs.getString("spWireRangeMm")));
	epmDocument.put("spTabThickness", StringUtil.checkNull(rs.getString("spTabThickness")));
	epmDocument.put("spMaterThick", StringUtil.checkNull(rs.getString("spMaterThick")));
	epmDocument.put("spBracketSizeD", StringUtil.checkNull(rs.getString("spBracketSizeD")));
	epmDocument.put("spBracketSizeH", StringUtil.checkNull(rs.getString("spBracketSizeH")));
	epmDocument.put("spBracketSizeT", StringUtil.checkNull(rs.getString("spBracketSizeT")));
	epmDocument.put("spRelayCapa", StringUtil.checkNull(rs.getString("spRelayCapa")));
	epmDocument.put("spEResistVal", StringUtil.checkNull(rs.getString("spEResistVal")));
	epmDocument.put("spVoltSensCapa", StringUtil.checkNull(rs.getString("spVoltSensCapa")));
	epmDocument.put("spManufPartNo", StringUtil.checkNull(rs.getString("spManufPartNo")));
	epmDocument.put("spValueValue", StringUtil.checkNull(rs.getString("spValueValue")));
	epmDocument.put("spTolerance", StringUtil.checkNull(rs.getString("spTolerance")));
	epmDocument.put("spManufacturer", StringUtil.checkNull(rs.getString("spManufacturer")));
	epmDocument.put("spRatedVoltage", StringUtil.checkNull(rs.getString("spRatedVoltage")));
	epmDocument.put("spWatt", StringUtil.checkNull(rs.getString("spWatt")));
	epmDocument.put("spOperTemp", StringUtil.checkNull(rs.getString("spOperTemp")));
	epmDocument.put("spStoreTemp", StringUtil.checkNull(rs.getString("spStoreTemp")));
	epmDocument.put("spPackageType", StringUtil.checkNull(rs.getString("spPackageType")));
	epmDocument.put("spMSL", StringUtil.checkNull(rs.getString("spMSL")));
	epmDocument.put("spESD", StringUtil.checkNull(rs.getString("spESD")));
	epmDocument.put("spAECQ", StringUtil.checkNull(rs.getString("spAECQ")));
	epmDocument.put("spCharact1", StringUtil.checkNull(rs.getString("spCharact1")));
	epmDocument.put("spCharact2", StringUtil.checkNull(rs.getString("spCharact2")));
	epmDocument.put("spHoleSize", StringUtil.checkNull(rs.getString("spHoleSize")));
	epmDocument.put("spWireRangeSQ", StringUtil.checkNull(rs.getString("spWireRangeSQ")));
	epmDocument.put("spWATTW", StringUtil.checkNull(rs.getString("spWATTW")));
	epmDocument.put("spSizemm", StringUtil.checkNull(rs.getString("spSizemm")));
	epmDocument.put("spPermitTempC", StringUtil.checkNull(rs.getString("spPermitTempC")));
	epmDocument.put("spIsResin", StringUtil.checkNull(rs.getString("spIsResin")));
	epmDocument.put("spThickness", StringUtil.checkNull(rs.getString("spThickness")));
	epmDocument.put("spHoleShape", StringUtil.checkNull(rs.getString("spHoleShape")));
	epmDocument.put("spCapaEResist", StringUtil.checkNull(rs.getString("spCapaEResist")));
	epmDocument.put("spClothesMater", StringUtil.checkNull(rs.getString("spClothesMater")));
	epmDocument.put("spIsShield", StringUtil.checkNull(rs.getString("spIsShield")));
	epmDocument.put("spESIRApproval", StringUtil.checkNull(rs.getString("spESIRApproval")));
	epmDocument.put("spISIRApproval", StringUtil.checkNull(rs.getString("spISIRApproval")));
	epmDocument.put("spConAppNoESIR", StringUtil.checkNull(rs.getString("spConAppNoESIR")));
	epmDocument.put("spLayer", StringUtil.checkNull(rs.getString("spLayer")));
	epmDocument.put("spOrgDskPartNo", StringUtil.checkNull(rs.getString("spOrgDskPartNo")));
	epmDocument.put("spPCBMaterial", StringUtil.checkNull(rs.getString("spPCBMaterial")));
	epmDocument.put("spTg", StringUtil.checkNull(rs.getString("spTg")));
	epmDocument.put("spCharact", StringUtil.checkNull(rs.getString("spCharact")));
	epmDocument.put("spSurfaceTreat", StringUtil.checkNull(rs.getString("spSurfaceTreat")));
	epmDocument.put("spAppearanProc", StringUtil.checkNull(rs.getString("spAppearanProc")));
	epmDocument.put("spSoldResColor", StringUtil.checkNull(rs.getString("spSoldResColor")));
	epmDocument.put("spInstallPos", StringUtil.checkNull(rs.getString("spInstallPos")));
	epmDocument.put("spSwVersion", StringUtil.checkNull(rs.getString("spSwVersion")));
	epmDocument.put("spUnitUnit", StringUtil.checkNull(rs.getString("spUnitUnit")));
	epmDocument.put("spResUnit", StringUtil.checkNull(rs.getString("spResUnit")));
	epmDocument.put("spINDUnit", StringUtil.checkNull(rs.getString("spINDUnit")));
	epmDocument.put("spCapUnit", StringUtil.checkNull(rs.getString("spCapUnit")));
	epmDocument.put("spRLCPackType", StringUtil.checkNull(rs.getString("spRLCPackType")));
	epmDocument.put("spRLCTolerance", StringUtil.checkNull(rs.getString("spRLCTolerance")));
	epmDocument.put("spFestPrevent", StringUtil.checkNull(rs.getString("spFestPrevent")));
	epmDocument.put("spConnCombDir", StringUtil.checkNull(rs.getString("spConnCombDir")));
	epmDocument.put("spCertified", StringUtil.checkNull(rs.getString("spCertified")));
	epmDocument.put("spPackageSpec", StringUtil.checkNull(rs.getString("spPackageSpec")));
	epmDocument.put("spTabSize", StringUtil.checkNull(rs.getString("spTabSize")));
	epmDocument.put("spSealType", StringUtil.checkNull(rs.getString("spSealType")));
	epmDocument.put("spStudSize", StringUtil.checkNull(rs.getString("spStudSize")));
	epmDocument.put("spPitch", StringUtil.checkNull(rs.getString("spPitch")));
	epmDocument.put("spSoldering", StringUtil.checkNull(rs.getString("spSoldering")));
	epmDocument.put("spConstrucMeth", StringUtil.checkNull(rs.getString("spConstrucMeth")));
	epmDocument.put("spOptimumTemp", StringUtil.checkNull(rs.getString("spOptimumTemp")));
	epmDocument.put("spContactRes", StringUtil.checkNull(rs.getString("spContactRes")));
	epmDocument.put("spInsulatRes", StringUtil.checkNull(rs.getString("spInsulatRes")));
	epmDocument.put("spConntHeight", StringUtil.checkNull(rs.getString("spConntHeight")));
	epmDocument.put("spCableConMeth", StringUtil.checkNull(rs.getString("spCableConMeth")));
	epmDocument.put("spTerminalType", StringUtil.checkNull(rs.getString("spTerminalType")));
	epmDocument.put("spTermBarrelSz", StringUtil.checkNull(rs.getString("spTermBarrelSz")));
	epmDocument.put("spTermPrezType", StringUtil.checkNull(rs.getString("spTermPrezType")));
	epmDocument.put("spGWITMaterAt", StringUtil.checkNull(rs.getString("spGWITMaterAt")));
	epmDocument.put("spInterface", StringUtil.checkNull(rs.getString("spInterface")));
	epmDocument.put("spPinNShape", StringUtil.checkNull(rs.getString("spPinNShape")));
	epmDocument.put("spSUBSuppliy", StringUtil.checkNull(rs.getString("spSUBSuppliy")));
	epmDocument.put("spCodingNColor", StringUtil.checkNull(rs.getString("spCodingNColor")));
	epmDocument.put("spPlant", StringUtil.checkNull(rs.getString("spPlant")));
	epmDocument.put("spPuchaseGroup", StringUtil.checkNull(rs.getString("spPuchaseGroup")));
	epmDocument.put("spDevManNm", StringUtil.checkNull(rs.getString("spDevManNm")));
	epmDocument.put("spDesignManNm", StringUtil.checkNull(rs.getString("spDesignManNm")));
	epmDocument.put("spManufacManNm", StringUtil.checkNull(rs.getString("spManufacManNm")));
	epmDocument.put("spMContractSAt", StringUtil.checkNull(rs.getString("spMContractSAt")));
	epmDocument.put("spMMoldAt", StringUtil.checkNull(rs.getString("spMMoldAt")));
	epmDocument.put("spMMakingAt", StringUtil.checkNull(rs.getString("spMMakingAt")));
	epmDocument.put("spMProdAt", StringUtil.checkNull(rs.getString("spMProdAt")));
	epmDocument.put("spDieWhere", StringUtil.checkNull(rs.getString("spDieWhere")));
	epmDocument.put("spObjectiveCT", StringUtil.checkNull(rs.getString("spObjectiveCT")));
	epmDocument.put("spCavityStd", StringUtil.checkNull(rs.getString("spCavityStd")));
	epmDocument.put("spMountWayApE", StringUtil.checkNull(rs.getString("spMountWayApE")));
	epmDocument.put("spThickWH", StringUtil.checkNull(rs.getString("spThickWH")));
	epmDocument.put("spPropEtc", StringUtil.checkNull(rs.getString("spPropEtc")));
	epmDocument.put("spHighVoltageSensingLimit", StringUtil.checkNull(rs.getString("spHighVoltageSensingLimit")));
	epmDocument.put("spMovVoltage", StringUtil.checkNull(rs.getString("spMovVoltage")));
	epmDocument.put("spToleranceSensing", StringUtil.checkNull(rs.getString("spToleranceSensing")));
	epmDocument.put("spEsirApprove", StringUtil.checkNull(rs.getString("spEsirApprove")));
	epmDocument.put("spMainChipset", StringUtil.checkNull(rs.getString("spMainChipset")));
	epmDocument.put("spFrequency", StringUtil.checkNull(rs.getString("spFrequency")));
	epmDocument.put("spDcpower", StringUtil.checkNull(rs.getString("spDcpower")));
	epmDocument.put("spInterfaceIt", StringUtil.checkNull(rs.getString("spInterfaceIt")));
	epmDocument.put("spFeatures", StringUtil.checkNull(rs.getString("spFeatures")));
	epmDocument.put("spDevSpec", StringUtil.checkNull(rs.getString("spDevSpec")));
	epmDocument.put("spFlameLevel", StringUtil.checkNull(rs.getString("spFlameLevel")));
	epmDocument.put("spERatedVoltage", StringUtil.checkNull(rs.getString("spERatedVoltage")));
	epmDocument.put("spQCNo", StringUtil.checkNull(rs.getString("spQCNo")));
	epmDocument.put("spActuatorLctn", StringUtil.checkNull(rs.getString("spActuatorLctn")));
	epmDocument.put("spMaxFrequency", StringUtil.checkNull(rs.getString("spMaxFrequency")));
	epmDocument.put("spImpedance", StringUtil.checkNull(rs.getString("spImpedance")));
	epmDocument.put("spAssemblyMaterial", StringUtil.checkNull(rs.getString("spAssemblyMaterial")));
	epmDocument.put("spPartStyle", StringUtil.checkNull(rs.getString("spPartStyle")));
	epmDocument.put("spDescription", "");

	// 부품의 속성 정보중 제품사양서정보가 존재하는지 확인후 전송......
	// 부품의 속성중 이미지 정보가 존재하는지 확인후 전송....
	// 나머지 ketDrawingDistEpmLink stampItem 관계를 확인하여 산출물을 전송하고 전송이 성공되면
	// msSQL로 저장한다.
	// 저장이 성공하면 리턴 시킨다.
	// 상위 서비스에선 해당 REQ 객체의 I/F DATE UPDATE 하고 성공 메시지 리턴....
	boolean specdoc = false;
	boolean partImg = false;
	boolean pdf2d = false;
	boolean pdf3d = false;
	boolean step = false;
	boolean igs = false;
	String tempDir = "R:\\Temp";
	String outFolder = "R:\\PLM";
	String chkFolder = "";
	try {
	    // WTPart part = (WTPart) CommonUtil.getObject("wt.part.WTPart:989184307");
	    WTPart part = (WTPart) CommonUtil.getObject("wt.part.WTPart:" + rs.getString("PARTIDA2A2"));
	    /*
	     * String sPartStyle = StringUtil.checkNull(rs.getString("PartNumber")).substring(0, 2); if ("61".equals(sPartStyle))
	     * epmDocument.put("spPartStyle", "Female"); else if ("62".equals(sPartStyle)) epmDocument.put("spPartStyle", "Male"); else if
	     * ("73".equals(sPartStyle)) epmDocument.put("spPartStyle", "Female"); else if ("74".equals(sPartStyle))
	     * epmDocument.put("spPartStyle", "Male"); else epmDocument.put("spPartStyle", "");
	     */

	    String removeHpartNumber = StringUtils.removeStart(StringUtil.checkNull(rs.getString("PartNumber")), "H");
	    String rePartNumber = removeHpartNumber.substring(0, 1);

	    if ("3".equals(rePartNumber))
		sPartNumberDrawingNumber = "WH" + StringUtil.checkNull(removeHpartNumber);
	    else if ("4".equals(rePartNumber))
		sPartNumberDrawingNumber = "EM" + StringUtil.checkNull(removeHpartNumber);
	    else if ("5".equals(rePartNumber))
		sPartNumberDrawingNumber = "JB" + StringUtil.checkNull(removeHpartNumber);
	    else if ("6".equals(rePartNumber))
		sPartNumberDrawingNumber = "MG" + StringUtil.checkNull(removeHpartNumber);
	    else if ("7".equals(rePartNumber))
		sPartNumberDrawingNumber = "ST" + StringUtil.checkNull(removeHpartNumber);
	    else if ("8".equals(rePartNumber))
		sPartNumberDrawingNumber = "PG" + StringUtil.checkNull(removeHpartNumber);
	    else
		sPartNumberDrawingNumber = StringUtil.checkNull(rs.getString("PartNumber"));

	    String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	    List<EPMDocument> linkQrDwg = null;

	    System.out.println("## PLM DB RESULTS start " + new Date());

	    if (!"Y".equals(directYn)) {
		if ("F".equals(partType)) {
		    String prodPartNumber = "H" + part.getNumber();
		    WTPart prodPart = PartBaseHelper.service.getLatestPart(prodPartNumber);

		    linkQrDwg = EDMPartHelper.getReferenceEPMDocsByWTPart(prodPart);
		} else {
		    linkQrDwg = EDMPartHelper.getReferenceEPMDocsByWTPart(part);

		}
	    }
	    // for (int inx = 0; inx < linkQrDwg.size(); inx++) {
	    // EPMDocument epm = linkQrDwg.get(inx);
	    //
	    // if (epm.getNumber().lastIndexOf("_") == -1) {
	    // // epmDocument.put("PartNumberDrawingNumber", epm.getNumber());
	    // sPartNumberDrawingNumber = epm.getNumber();
	    // }
	    // }

	    // WTPart part = (WTPart) CommonUtil.getObject("wt.part.WTPart:" + StringUtil.checkNull(rs.getString("spImpedance")));

	    System.out.println("## PLM DB RESULTS start 제품사양서 " + new Date());

	    QueryResult linkQrDoc = null; // 관련문서

	    if (!"Y".equals(directYn)) {
		linkQrDoc = PersistenceHelper.manager.navigate(part, KETDocumentPartLink.DOCUMENT_ROLE, KETDocumentPartLink.class);
	    }

	    if (linkQrDoc != null && !"Y".equals(directYn)) {

		String docOid = null;
		KETProjectDocument pDoc = null;

		while (linkQrDoc.hasMoreElements()) {
		    pDoc = (KETProjectDocument) linkQrDoc.nextElement();
		    docOid = KETObjectUtil.getOidString(pDoc);
		    InputStream in = null;
		    QueryResult linkDocCategory = null;
		    linkDocCategory = PersistenceHelper.manager.navigate(pDoc, "documentCategory", KETDocumentCategoryLink.class);
		    KETDocumentCategory docCategory = null;
		    while (linkDocCategory.hasMoreElements()) {
			docCategory = (KETDocumentCategory) linkDocCategory.nextElement();
		    }

		    if ("제품사양서".equals(docCategory.getCategoryName())) {

			ContentHolder holder = ContentHelper.service.getContents(pDoc);

			QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
			while (result.hasMoreElements()) {
			    ContentItem item = (ContentItem) result.nextElement();
			    ContentInfo info = ContentUtil.getContentInfo(holder, item);

			    int j = info.getName().lastIndexOf(".");
			    if (item instanceof ApplicationData) {

				ApplicationData appData = (ApplicationData) item;

				Persistable persistable = appData.getStreamData().getObject();

				if (persistable instanceof StoredItem) {

				    try {

					Streamed streamed = (Streamed) appData.getStreamData().getObject();
					FvItem fvItem = (FvItem) streamed;
					FileFolder filefolder = fvItem.getFolder();
					FvMount fvmount = null;
					fvmount = StandardFvService.getLocalMount(filefolder);
					String folderPath = fvmount.getPath();
					// String folderPath = "D:\\temp";
					// String renameFile = part.getNumber() + "_intructions" +
					// info.getName().substring(j).toLowerCase();
					String renameFile = pDoc.getNumber().substring(3) + "_intructions"
					        + info.getName().substring(j).toLowerCase();

					this.isMake(outFolder + "\\" + pDoc.getNumber().substring(3));

					// if (fileIsLive(outFolder + "\\" + pDoc.getNumber().substring(3) + "\\" + fvItem.getName())) {
					fileCopy(outFolder + "\\" + pDoc.getNumber().substring(3) + "\\" + fvItem.getName(), outFolder
					        + "\\" + renameFile);
					epmDocument.put("spInstructionsFile", renameFile);
					specdoc = true;
					// }

				    } catch (Exception e) {

					Kogger.error(getClass(), e);
				    }

				} else if (persistable instanceof StreamData) {
				    try {
					in = ContentServerHelper.service.findContentStream(appData);
					String renameFile = pDoc.getNumber().substring(3) + "_intructions"
					        + info.getName().substring(j).toLowerCase();
					// String renameFile = part.getNumber() + "_intructions" +
					// info.getName().substring(j).toLowerCase();

					// String tempDir = System.getProperty("java.io.tmpdir");
					this.isMake(outFolder + "\\" + pDoc.getNumber().substring(3));

					File attachfile = new File(tempDir + "\\" + info.getName()); // 파일 저장 위치
					FileOutputStream fileOut = new FileOutputStream(attachfile);
					byte[] buffer = new byte[1024];
					int c;
					while ((c = in.read(buffer)) != -1)
					    fileOut.write(buffer, 0, c);
					fileOut.close();

					File refile = new File(outFolder + "\\" + pDoc.getNumber().substring(3) + "\\" + renameFile); // 파일
					                                                                                              // 저장
					                                                                                              // 위치

					attachfile.renameTo(refile);

					epmDocument.put("spInstructionsFile", renameFile);
					specdoc = true;
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				}

			    }

			}
		    }

		}

	    }

	    String threedType = PartSpecGetter.getPartSpec(part, PartSpecEnum.Hompage3DIF);
	    if (threedType.equals("NO") && !"Y".equals(directYn)) {
		for (int inx = 0; inx < linkQrDwg.size(); inx++) {
		    EPMDocument epm = linkQrDwg.get(inx);

		    HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);

		    String category = "";
		    if (ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE) != null) {
			category = EDMEnumeratedTypeUtil.getCADManageType((String) ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE),
			        Locale.KOREAN);

			if ("PRODUCT_DRAWING".equals(category))
			    sPartNumberDrawingNumber = epm.getNumber().substring(3);

		    }

		}

		// sPartNumberDrawingNumber = sPartNumberDrawingNumber.replace("_3D", "");
		System.out.println(sPartNumberDrawingNumber);
		System.out.println("## PLM DB RESULTS START 제품이미지 " + new Date());

		InputStream in = null;
		ContentHolder holder = ContentHelper.service.getContents(part);
		// 차후 all 로 로직 변경해야함... 혹시나..

		QueryResult result = ContentHelper.service.getContentsByRole(holder, ContentRoleType.PRIMARY);
		while (result.hasMoreElements()) {
		    ContentItem item = (ContentItem) result.nextElement();
		    ContentInfo info = ContentUtil.getContentInfo(holder, item);

		    int j = info.getName().lastIndexOf(".");
		    if (item instanceof ApplicationData) {

			ApplicationData appData = (ApplicationData) item;

			Persistable persistable = appData.getStreamData().getObject();

			if (persistable instanceof StoredItem) {

			    try {
				System.out.println("## PLM DB RESULTS START 제품이미지1 " + new Date());
				// Streamed streamed = (Streamed) appData.getStreamData().getObject();
				// FvItem fvItem = (FvItem) streamed;
				// FileFolder filefolder = fvItem.getFolder();
				// FvMount fvmount = null;
				// fvmount = StandardFvService.getLocalMount(filefolder);
				// String folderPath = fvmount.getPath();
				// // String folderPath = "D:\\temp";
				// // String renameFile = part.getNumber() + info.getName().substring(j).toLowerCase();

				StoredItem si = (StoredItem) persistable;
				FileFolder folder = si.getFolder();
				FvMount mount = StandardFvService.getLocalMount(folder);
				String path = mount.getPath();
				long filename = si.getUniqueSequenceNumber();
				String fn = StoredItem.buildFileName(filename);

				String renameFile = sPartNumberDrawingNumber.replace("_3D", "") + "_img"
				        + info.getName().substring(j).toLowerCase();
				String partOutFolder = sPartNumberDrawingNumber.replace("_3D", "");

				System.out.println("## PLM DB RESULTS START 제품이미지1 " + renameFile);
				System.out.println("## PLM DB RESULTS START 제품이미지1 " + renameFile);
				System.out.println("## PLM DB RESULTS START 제품이미지1 " + renameFile);
				System.out.println("## PLM DB RESULTS START 제품이미지1 " + renameFile);

				this.isMake(outFolder + "\\" + partOutFolder);

				System.out.println("## PLM DB RESULTS START 제품이미지 copy start " + new Date());
				// fileCopy(tempDir + "\\" + fvItem.getName(), outFolder + "\\" + sPartNumberDrawingNumber +
				// renameFile);
				fileCopy(path + "\\" + fn, outFolder + "\\" + partOutFolder + "\\" + renameFile);
				epmDocument.put("spImgFile", renameFile);
				partImg = true;
				System.out.println("## PLM DB RESULTS START 제품이미지 copy end " + new Date());

			    } catch (Exception e) {
				Kogger.error(getClass(), e);
			    }

			} else if (persistable instanceof StreamData) {
			    try {
				in = ContentServerHelper.service.findContentStream(appData);
				// String renameFile = part.getNumber() + info.getName().substring(j).toLowerCase();
				String renameFile = sPartNumberDrawingNumber.replace("_3D", "") + "_img"
				        + info.getName().substring(j).toLowerCase();
				String partOutFolder = sPartNumberDrawingNumber.replace("_3D", "");

				this.isMake(outFolder + "\\" + partOutFolder);

				// String tempDir = System.getProperty("java.io.tmpdir");

				File attachfile = new File(tempDir + "\\" + info.getName()); // 파일 저장 위치
				FileOutputStream fileOut = new FileOutputStream(attachfile);
				byte[] buffer = new byte[1024];
				int c;
				while ((c = in.read(buffer)) != -1)
				    fileOut.write(buffer, 0, c);
				fileOut.close();

				File refile = new File(outFolder + "\\" + sPartNumberDrawingNumber + "\\" + renameFile); // 파일 저장 위치

				attachfile.renameTo(refile);

				attachfile.delete();

				epmDocument.put("spImgFile", renameFile);
				partImg = true;
			    } catch (Exception e) {
				Kogger.error(getClass(), e);
			    }
			}

		    }

		}
		System.out.println("## PLM DB RESULTS END 제품이미지 " + new Date());
	    }
	} catch (WTException e) {

	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// 2D, STEP 파일 찾기
	// 현재 한건으로 하위에 로직을 구성하였으나.
	// 여러건인경우로
	// List<KETDrawingDistEpmLink> epmDocLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class,
	// KETDrawingDistRequest.class, drawingDistReq);
	// 를 이용하여 로직 변경해야한다. // 참고 소스 StandardStampingService 의 메소드 makeXmlObjectNDownLoadCadFile

	System.out.println("## PLM DB RESULTS START img, 2D,3D,STEP,IGS " + new Date());

	if (!"Y".equals(directYn)) {
	    try {
		String renameFile = "";
		KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(StringUtil.checkNull(rs
		        .getString("REQCLASSNAME")) + ":" + StringUtil.checkNull(rs.getString("REQIDA2A2")));

		SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
		List<KETDrawingDistEpmLink> epmDocLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class,
		        KETDrawingDistRequest.class, drawingDistReq);

		for (int k = 0; k < epmDocLinkList.size(); k++) {

		    KETDrawingDistEpmLink epmDocLink = epmDocLinkList.get(k);
		    EPMDocument epmDoc = epmDocLink.getDistEpm();

		    QueryResult qr = PersistenceHelper.manager.navigate(epmDocLink, "stampItem", KETStampEpmLink.class);
		    if (qr.hasMoreElements()) {
			while (qr.hasMoreElements()) {

			    KETStampingItem ketStampingItem = (KETStampingItem) qr.nextElement();
			    ContentHolder contentHolder = ContentHelper.service.getContents(ketStampingItem);

			    Vector contentList = ContentHelper.getContentListAll(contentHolder);

			    for (int i = 0; i < contentList.size(); i++) {
				ContentItem contentItem = (ContentItem) contentList.elementAt(i);
				Kogger.debug(getClass(), i + " contentItem = " + contentItem);

				try {
				    ApplicationData appData = (ApplicationData) CommonUtil.getObject(contentItem.getPersistInfo()
					    .getObjectIdentifier().getStringValue());
				    Persistable persistable = appData.getStreamData().getObject(); // StoredItem 또는 StreamData

				    // appData.getFileName() 으로 2d, 3d, step igs 를 구분하여 이름을 리네임 처리한다. 확장자 비교 파일명 문자열에 3d 여부...

				    if (persistable instanceof StreamData) {
					StreamData sd = (StreamData) persistable;

					int j = appData.getFileName().lastIndexOf(".");

					String renameFileName = appData.getFileName().substring(0, j);

					// boolean is3D = renameFileName.toUpperCase().endsWith("_3D");
					int i3d = renameFileName.indexOf("3D");
					boolean is3D = i3d > 0 ? true : false;
					System.out.println("appData.getFileName() : " + appData.getFileName());
					System.out.println("renameFile : " + renameFile);
					System.out.println("is3D : " + is3D);

					if (is3D) {
					    if (".stp".equals(appData.getFileName().substring(j).toLowerCase())) {
						renameFile = epmDoc.getNumber().substring(3)
						        + appData.getFileName().substring(j).toLowerCase();

						System.out.println("stp : " + renameFile);

					    } else if (".pdf".equals(appData.getFileName().substring(j).toLowerCase())) {
						renameFile = epmDoc.getNumber().substring(3)
						        + appData.getFileName().substring(j).toLowerCase();
						System.out.println("3d pdf : " + renameFile);

					    } else if (".igs".equals(appData.getFileName().substring(j).toLowerCase())) {
						renameFile = epmDoc.getNumber().substring(3)
						        + appData.getFileName().substring(j).toLowerCase();

						System.out.println("igs : " + renameFile);

					    } else if (".jpg".equals(appData.getFileName().substring(j).toLowerCase())) {
						renameFile = epmDoc.getNumber().substring(3).replace("_3D", "") + "_img"
						        + appData.getFileName().substring(j).toLowerCase();

						System.out.println("jpg : " + renameFile);
					    }
					} else {
					    if (".pdf".equals(appData.getFileName().substring(j).toLowerCase())) {
						renameFile = epmDoc.getNumber().substring(3) + "_2D"
						        + appData.getFileName().substring(j).toLowerCase();

						System.out.println("2d pdf : " + renameFile);
					    }

					}

					System.out.println("파일확장자 : " + appData.getFileName().substring(j).toLowerCase());
					boolean fileSend = true;

					if (".prt".equals(appData.getFileName().substring(j).toLowerCase())) {
					    fileSend = false;
					} else if (".dwg".equals(appData.getFileName().substring(j).toLowerCase())) {
					    fileSend = false;
					} else {
					    fileSend = true;
					}

					if (!is3D) {
					    fileSend = false;
					}

					if (fileSend) {
					    String makeFolder = "";
					    if (is3D) {
						int l = epmDoc.getNumber().substring(3).lastIndexOf("_3D");
						makeFolder = epmDoc.getNumber().substring(3).substring(0, l);
					    } else {
						makeFolder = epmDoc.getNumber().substring(3);
					    }

					    this.isMake(outFolder + "\\" + makeFolder);

					    InputStream in = sd.retrieveStream();
					    File attachfile = new File(tempDir + "\\" + appData.getFileName()); // 파일 저장
					    // 위치
					    FileOutputStream fileOut = new FileOutputStream(attachfile);
					    byte[] buffer = new byte[1024];
					    int c;
					    while ((c = in.read(buffer)) != -1)
						fileOut.write(buffer, 0, c);
					    fileOut.close();

					    File refile = new File(outFolder + "\\" + makeFolder + "\\" + renameFile); // 파일
					    // 저장
					    // 위치

					    attachfile.renameTo(refile);

					    attachfile.delete();

					}

					if (is3D) {
					    if (".stp".equals(appData.getFileName().substring(j).toLowerCase())) {
						epmDocument.put("spStepFile", renameFile);
						step = true;

					    } else if (".pdf".equals(appData.getFileName().substring(j).toLowerCase())) {
						epmDocument.put("sp3DpdfFile", renameFile);
						pdf3d = true;

					    } else if (".igs".equals(appData.getFileName().substring(j).toLowerCase())) {
						epmDocument.put("spIgsFile", renameFile);
						igs = true;

					    } else if (".jpg".equals(appData.getFileName().substring(j).toLowerCase())) {
						epmDocument.put("spImgFile", renameFile);
						partImg = true;
					    }
					} else {
					    // if (".pdf".equals(appData.getFileName().substring(j).toLowerCase())) {
					    // epmDocument.put("sp2DpdfFile", renameFile);
					    // pdf2d = true;
					    // }

					}

				    }

				} catch (Exception e) {
				    e.printStackTrace();
				}

			    }
			}
		    }

		}

	    } catch (WTException e) {

		Kogger.error(getClass(), e);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	System.out.println("## PLM DB RESULTS END 2D,3D,STEP,IGS " + new Date());
	if (specdoc == false)
	    epmDocument.put("spInstructionsFile", "");
	if (partImg == false)
	    epmDocument.put("spImgFile", "");
	if (pdf2d == false)
	    epmDocument.put("sp2DpdfFile", "");
	if (pdf3d == false)
	    epmDocument.put("sp3DpdfFile", "");
	if (step == false)
	    epmDocument.put("spStepFile", "");
	if (igs == false)
	    epmDocument.put("spIgsFile", "");

	epmDocument.put("sPartNumberDrawingNumber", sPartNumberDrawingNumber.replace("_3D", ""));

	return epmDocument;
    }

    public void isMake(String chkFolder) {
	File dir = new File(chkFolder);
	if (!dir.isDirectory()) {
	    dir.mkdirs();
	}
    }

    public Boolean fileIsLive(String isLivefile) {
	File f1 = new File(isLivefile);
	if (f1.exists())
	    return true;
	else
	    return false;
    }

    public void fileCopy(String inFileName, String outFileName) {
	try {
	    FileInputStream fis = new FileInputStream(inFileName);
	    FileOutputStream fos = new FileOutputStream(outFileName);
	    byte[] buffer = new byte[1024];
	    int readcount = 0;

	    while ((readcount = fis.read(buffer)) != -1) {
		fos.write(buffer, 0, readcount);
	    }
	    fis.close();
	    fos.close();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private String getSendHpList(KETDrawingDistReqDTO paramObject) throws Exception {

	String drawingDistReqstOid = "";

	String distOid = "";
	String titile = "";
	if (StringUtils.isNotEmpty(paramObject.getOid())) {
	    drawingDistReqstOid = returnReqOidList(paramObject.getOid());
	    distOid = "ext.ket.edm.entity.KETDrawingDistRequest:" + paramObject.getOid();
	    System.out.println("distOid : " + distOid);
	    KETDrawingDistRequest dist = (KETDrawingDistRequest) CommonUtil.getObject(distOid);
	    titile = dist.getTitle();
	    titile = StringUtils.removeStart(titile, "CU_");
	    titile = StringUtils.removeEnd(titile, "_3D");
	}

	String directYn = paramObject.getDirect2Hompage();

	StringBuilder sb = new StringBuilder();
	sb.append("\n    SELECT ROW_NUMBER() over (ORDER BY REQ.REQIDA2A2 DESC) RN, ");
	sb.append("\n         P.IDA2A2 PARTIDA2A2, ");
	sb.append("\n         REQ.REQIDA2A2, ");
	sb.append("\n         REQ.REQCLASSNAME, ");
	sb.append("\n         C.CATALOGUECODE spCategoryCode, C.CLASSCODE spClassification,  ");
	if ("Y".equals(directYn)) {

	    if (StringUtils.isNotEmpty(titile)) {
		sb.append("\n  	   PM.NAME as PARTNAME,  ");
		sb.append("\n  	   '" + titile + "' PARTNUMBER,               ");
	    } else {
		sb.append("\n  	   PM.NAME as PARTNAME,  ");
		sb.append("\n  	   PM.WTPARTNUMBER as PARTNUMBER,               ");
	    }
	} else {
	    sb.append("\n  	   (SELECT NAME FROM WTPARTMASTER WHERE WTPARTNUMBER = LTRIM(REQ.TITLE, 'H')) PARTNAME,  ");
	    sb.append("\n  	   REQ.TITLE PARTNUMBER,               ");
	}

	sb.append("\n         PJT.PJTNO spProjectNo, PJT.PJTNAME,PTC_STR_150TYPEINFOWTPART spEoNo, ");
	sb.append("\n         CASE WHEN C.DIVISIONFLAG = 'C' THEN '자동차사업부' ");
	sb.append("\n         ELSE CASE WHEN C.DIVISIONFLAG = 'E' THEN '전자사업부' ");
	sb.append("\n         ELSE CASE WHEN C.DIVISIONFLAG = 'K' THEN 'KETS' ");
	sb.append("\n         ELSE CASE WHEN C.DIVISIONFLAG = 'N' THEN 'KETN' ");
	sb.append("\n         ELSE '' END END END END spDivision, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_1TYPEINFOWTPART), 'n/a') AS spPartNameHis, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_2TYPEINFOWTPART), 'n/a') AS spPartRevision, ");
	sb.append("\n         CASE WHEN PTC_STR_3TYPEINFOWTPART = 'H' THEN '반제품' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'R' THEN '원자재' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'S' THEN '스크랩' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'K' THEN '포장재' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'D' THEN 'Die' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'M' THEN '금형부품' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'W' THEN '상품' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'O' THEN '기타' ");
	sb.append("\n         ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'YES' THEN 'YAZAKI' ");
	sb.append("\n         ELSE '' END END END END END END END END END spPartType, ");
	sb.append("\n         CASE WHEN P.PTC_STR_4TYPEINFOWTPART = 'PC003' THEN '개발단계' ");
	sb.append("\n         ELSE CASE WHEN P.PTC_STR_4TYPEINFOWTPART = 'PC004' THEN '양산단계' ");
	sb.append("\n         ELSE '' END END spPartDevLevel, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_5TYPEINFOWTPART), 'n/a') AS spIsDeleted, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_6TYPEINFOWTPART), 'n/a') AS spBOMFlag, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_7TYPEINFOWTPART), 'n/a') AS spWeightUnit, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_8TYPEINFOWTPART), 'n/a') AS spNetWeight, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_9TYPEINFOWTPART), 'n/a') AS spTotalWeight, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_10TYPEINFOWTPART), 'n/a') AS spScrabWeight, ");
	sb.append("\n         '' AS spIsYazaki, ");
	sb.append("\n         '' AS spYazakiNo, ");
	sb.append("\n         '' AS spManufAt, ");
	sb.append("\n         '' AS spManufacPlace, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_14TYPEINFOWTPART), 'n/a') AS spCustomRev, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_15TYPEINFOWTPART), 'n/a') AS spScrabCode, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SEALED' AND lang='en' AND CODE = P.PTC_STR_16TYPEINFOWTPART) spWaterProof, "); // 방수여부
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECMATERIALMARKING' AND lang='en' AND CODE = P.PTC_STR_17TYPEINFOWTPART) spMaterialMark, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECCOLOR' AND lang='en' AND CODE = P.PTC_STR_18TYPEINFOWTPART) spColor, "); // 색상(color)
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCOLORPURCH' AND CODE = P.PTC_STR_19TYPEINFOWTPART) spColorPurch, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCOLORELEC' AND CODE = P.PTC_STR_20TYPEINFOWTPART) spColorElec, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECSERIES' AND lang='en' AND CODE = P.PTC_STR_21TYPEINFOWTPART) spSeries, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPSERIESLAMP' AND CODE = P.PTC_STR_22TYPEINFOWTPART) spSeriesLAMP, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPSERIESBULB' AND CODE = P.PTC_STR_151TYPEINFOWTPART) spSeriesBulb, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_23TYPEINFOWTPART), 'n/a') AS spProdSize, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPENVFRIEND' AND CODE = P.PTC_STR_24TYPEINFOWTPART) spEnvFriend, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTGENCONN' AND CODE = P.PTC_STR_26TYPEINFOWTPART) spTGenConn, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTPCBCONN' AND CODE = P.PTC_STR_27TYPEINFOWTPART) spTPCBConn, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPTLAMPCONN' AND lang='en' AND CODE = P.PTC_STR_28TYPEINFOWTPART) spTLAMPConn, "); // type(lamp)
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPTBULBCONN' AND lang='en' AND CODE = P.PTC_STR_152TYPEINFOWTPART) spTBulbConn, "); // type(bulb)
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPTHIVOLT' AND lang='en' AND CODE = P.PTC_STR_29TYPEINFOWTPART) spTHiVolt, "); // type(고전압)
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTYPECHARGER' AND CODE = P.PTC_STR_30TYPEINFOWTPART) spTCharger, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPT2CHARGER' AND CODE = P.PTC_STR_153TYPEINFOWTPART) spT2Charger,                   ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTYPEHIGHVOLTAGEFUSE' AND CODE = P.PTC_STR_31TYPEINFOWTPART) spTHiVoltFuse,        ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPT1HIVOLTFUZ' AND CODE = P.PTC_STR_154TYPEINFOWTPART) spT1HiVoltFuz,        ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPTJNB' AND lang='en' AND CODE = P.PTC_STR_32TYPEINFOWTPART) spTJnB, "); // type(j/b)
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTPRA' AND CODE = P.PTC_STR_33TYPEINFOWTPART) spTPRA, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTGNSS' AND CODE = P.PTC_STR_34TYPEINFOWTPART) spTGNSS, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTCOMMODULE' AND CODE = P.PTC_STR_35TYPEINFOWTPART) spTComModule, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTMULTIMEDUNIT' AND CODE = P.PTC_STR_36TYPEINFOWTPART) spTMultiMedUnit, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPLANCETYPE' AND lang='en' AND CODE = P.PTC_STR_37TYPEINFOWTPART) spLanceType, "); // lancetype
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_38TYPEINFOWTPART), 'n/a') AS spNoOfPole, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_39TYPEINFOWTPART), 'n/a') AS spMTerminal, ");
	sb.append("\n         REPLACE(LOWER(P.PTC_STR_40TYPEINFOWTPART), 'n/a') AS spMConnector, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_41TYPEINFOWTPART), 'n/a') AS spMatchBulb, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_42TYPEINFOWTPART), 'n/a') AS spMClip, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_43TYPEINFOWTPART), 'n/a') AS spMRH, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_155TYPEINFOWTPART), 'n/a') AS spMCover, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECLOCKINGTYPE' AND lang='en' AND CODE = P.PTC_STR_44TYPEINFOWTPART) spLockingType, "); // lockingtype
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLOCKTTML' AND CODE = P.PTC_STR_45TYPEINFOWTPART) spLockTTML, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLOCKTCONN' AND CODE = P.PTC_STR_46TYPEINFOWTPART) spLockTConn, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_47TYPEINFOWTPART), 'n/a') AS spMWireSeal, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_48TYPEINFOWTPART), 'n/a') AS spRepresentM, ");
	sb.append("\n         '' AS spMaterMaker, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'MATERIALTYPE' AND lang='en' AND CODE = P.PTC_STR_148TYPEINFOWTPART) spMaterType, "); // 원재료type
	sb.append("\n         '' AS spMaterialInfo, ");
	sb.append("\n         '' AS spMaterNotFe, ");
	sb.append("\n         '' AS spMaterDie, ");
	sb.append("\n         '' AS spMaterPurch, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECPLATING' AND lang='en' AND CODE = P.PTC_STR_53TYPEINFOWTPART) spPlating, "); // 도금
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPPLATINGPURCH' AND CODE = P.PTC_STR_54TYPEINFOWTPART) spPlatingPurch, ");
	sb.append("\n         '' AS spCustomPartNo, ");
	sb.append("\n         REPLACE(LOWER(REPLACE(PTC_STR_56TYPEINFOWTPART, 'A', '')), 'n/a') AS spPermitVolt, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_57TYPEINFOWTPART), 'n/a') AS spWireRangeAWG, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPWIRERAWGELEC' AND lang='en' AND CODE = P.PTC_STR_58TYPEINFOWTPART) spWireRAWGElec, "); // wirerange_awg(전자)
	sb.append("\n         REPLACE(LOWER(PTC_STR_59TYPEINFOWTPART), 'n/a') AS spWireRangeMm, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_60TYPEINFOWTPART), 'n/a') AS spTabThickness, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_61TYPEINFOWTPART), 'n/a') AS spMaterThick, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_62TYPEINFOWTPART), 'n/a') AS spBracketSizeD, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_63TYPEINFOWTPART), 'n/a') AS spBracketSizeH, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_64TYPEINFOWTPART), 'n/a') AS spBracketSizeT, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECRELAYCAPACITY' AND lang='en' AND CODE = P.PTC_STR_65TYPEINFOWTPART) spRelayCapa, "); // relay용량
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECELECTRICRESISTANCEVALUE' AND CODE = P.PTC_STR_66TYPEINFOWTPART) spEResistVal, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECVOLTAGESENSECAPACITY' AND CODE = P.PTC_STR_67TYPEINFOWTPART) spVoltSensCapa, ");
	sb.append("\n         '' AS spManufPartNo, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_69TYPEINFOWTPART), 'n/a') AS spValueValue, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTOLERANCE' AND CODE = P.PTC_STR_69TYPEINFOWTPART) spTolerance, ");
	sb.append("\n         '' AS spManufacturer, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_72TYPEINFOWTPART), 'n/a') AS spRatedVoltage, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_73TYPEINFOWTPART), 'n/a') AS spWatt, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_74TYPEINFOWTPART), 'n/a') AS spOperTemp, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_156TYPEINFOWTPART), 'n/a') AS spStoreTemp, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECPACKAGETYPE' AND lang='en' AND CODE = P.PTC_STR_75TYPEINFOWTPART) spPackageType, "); // packagetype
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMSL' AND CODE = P.PTC_STR_76TYPEINFOWTPART) spMSL, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECESD' AND CODE = P.PTC_STR_77TYPEINFOWTPART) spESD, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPAECQ' AND lang='en' AND CODE = P.PTC_STR_78TYPEINFOWTPART) spAECQ, "); // aec-q
	sb.append("\n         REPLACE(LOWER(PTC_STR_79TYPEINFOWTPART), 'n/a') AS spCharact1, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_80TYPEINFOWTPART), 'n/a') AS spCharact2, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_81TYPEINFOWTPART), 'n/a') AS spHoleSize, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECWIRERANGE' AND lang='en' AND CODE = P.PTC_STR_82TYPEINFOWTPART) spWireRangeSQ, "); // wirerangeSQ
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECWATTW' AND CODE = P.PTC_STR_83TYPEINFOWTPART) spWATTW, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_84TYPEINFOWTPART), 'n/a') AS spSizemm, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_85TYPEINFOWTPART), 'n/a') AS spPermitTempC, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRESIN' AND CODE = P.PTC_STR_86TYPEINFOWTPART) spIsResin, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTHICKNESS' AND CODE = P.PTC_STR_87TYPEINFOWTPART) spThickness, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECHOLESHAPE' AND CODE = P.PTC_STR_88TYPEINFOWTPART) spHoleShape, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_89TYPEINFOWTPART), 'n/a') AS spCapaEResist, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCLOTHESMATERIAL' AND CODE = P.PTC_STR_90TYPEINFOWTPART) spClothesMater, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECISSHIELD' AND CODE = P.PTC_STR_91TYPEINFOWTPART) spIsShield, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECESIRAPPROVAL' AND CODE = P.PTC_STR_92TYPEINFOWTPART) spESIRApproval, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECISIRAPPROVAL' AND CODE = P.PTC_STR_93TYPEINFOWTPART) spISIRApproval, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_94TYPEINFOWTPART), 'n/a') AS spConAppNoESIR, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLAYER' AND CODE = P.PTC_STR_95TYPEINFOWTPART) spLayer, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_96TYPEINFOWTPART), 'n/a') AS spOrgDskPartNo, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECPCBMATERIAL' AND lang='en' AND CODE = P.PTC_STR_97TYPEINFOWTPART) spPCBMaterial, "); // pcb재질
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTG' AND CODE = P.PTC_STR_98TYPEINFOWTPART) spTg, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_79TYPEINFOWTPART), 'n/a') AS spCharact, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSURFACETREATMENT' AND CODE = P.PTC_STR_100TYPEINFOWTPART) spSurfaceTreat, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECAPPEARANCEPROCESS' AND CODE = P.PTC_STR_101TYPEINFOWTPART) spAppearanProc, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSOLDERRESISTORCOLOR' AND CODE = P.PTC_STR_102TYPEINFOWTPART) spSoldResColor, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECINSTALLPOSITION' AND lang='en' AND CODE = P.PTC_STR_103TYPEINFOWTPART) spInstallPos, "); // 장착위치
	sb.append("\n         REPLACE(LOWER(PTC_STR_104TYPEINFOWTPART), 'n/a') AS spSwVersion, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECUNITUNIT' AND lang='en' AND CODE = P.PTC_STR_105TYPEINFOWTPART) spUnitUnit, "); // 단위
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRESUNIT' AND CODE = P.PTC_STR_106TYPEINFOWTPART) spResUnit, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECINDUNIT' AND CODE = P.PTC_STR_107TYPEINFOWTPART) spINDUnit, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCAPUNIT' AND CODE = P.PTC_STR_108TYPEINFOWTPART) spCapUnit, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRLCPACKAGETYPE' AND CODE = P.PTC_STR_109TYPEINFOWTPART) spRLCPackType, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRLCTOLERANCE' AND CODE = P.PTC_STR_110TYPEINFOWTPART) spRLCTolerance, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPFESTPREVENT' AND CODE = P.PTC_STR_111TYPEINFOWTPART) spFestPrevent, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCONNCOMBDIR' AND CODE = P.PTC_STR_112TYPEINFOWTPART) spConnCombDir, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECCERTIFIED' AND lang='en' AND CODE = P.PTC_STR_113TYPEINFOWTPART) spCertified, "); // Certified
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPPACKAGESPEC' AND CODE = P.PTC_STR_114TYPEINFOWTPART) spPackageSpec, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_115TYPEINFOWTPART), 'n/a') AS spTabSize, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECSEALTYPE' AND lang='en' AND CODE = P.PTC_STR_116TYPEINFOWTPART) spSealType, "); // sealtype
	sb.append("\n         REPLACE(LOWER(PTC_STR_117TYPEINFOWTPART), 'n/a') AS spStudSize, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_118TYPEINFOWTPART), 'n/a') AS spPitch, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECSOLDERING' AND lang='en' AND CODE = P.PTC_STR_119TYPEINFOWTPART) spSoldering, "); // 납땜방식
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCONSTRUCMETH' AND CODE = P.PTC_STR_120TYPEINFOWTPART) spConstrucMeth, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_121TYPEINFOWTPART), 'n/a') AS spOptimumTemp, ");
	sb.append("\n         REPLACE(PTC_STR_122TYPEINFOWTPART, 'n/a') AS spContactRes, ");
	sb.append("\n         REPLACE(PTC_STR_123TYPEINFOWTPART, 'n/a') AS spInsulatRes, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_124TYPEINFOWTPART), 'n/a') AS spConntHeight, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECCABLECONMETH' AND lang='en' AND CODE = P.PTC_STR_125TYPEINFOWTPART) spCableConMeth, "); // cable연결방식
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMINALTYPE' AND CODE = P.PTC_STR_126TYPEINFOWTPART) spTerminalType, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMBARRELSZ' AND CODE = P.PTC_STR_127TYPEINFOWTPART) spTermBarrelSz, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMPREZTYPE' AND CODE = P.PTC_STR_128TYPEINFOWTPART) spTermPrezType, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECGWITMATERAT' AND CODE = P.PTC_STR_129TYPEINFOWTPART) spGWITMaterAt, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECINTERFACE' AND lang='en' AND CODE = P.PTC_STR_130TYPEINFOWTPART) spInterface, "); // 인터페이스
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPINNSHAPE' AND CODE = P.PTC_STR_131TYPEINFOWTPART) spPinNShape, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECSUBSUPPLIY' AND lang='en' AND CODE = P.PTC_STR_132TYPEINFOWTPART) spSUBSuppliy, "); // sub공급형태
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCODINGNCOLOR' AND CODE = P.PTC_STR_133TYPEINFOWTPART) spCodingNColor, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPLANT' AND CODE = P.PTC_STR_134TYPEINFOWTPART) spPlant, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPURCHASEGROUP' AND CODE = P.PTC_STR_135TYPEINFOWTPART) spPuchaseGroup, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_136TYPEINFOWTPART), 'n/a') AS spDevManNm, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_137TYPEINFOWTPART), 'n/a') AS spDesignManNm, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_138TYPEINFOWTPART), 'n/a') AS spManufacManNm, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMSELFCONTRACTFLAG' AND CODE = P.PTC_STR_139TYPEINFOWTPART) spMContractSAt, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMMOLDFLAG' AND CODE = P.PTC_STR_140TYPEINFOWTPART) spMMoldAt, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMMAKINGFLAG' AND CODE = P.PTC_STR_141TYPEINFOWTPART) spMMakingAt, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMPRODFLAG' AND CODE = P.PTC_STR_142TYPEINFOWTPART) spMProdAt, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_143TYPEINFOWTPART), 'n/a') AS spDieWhere, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_144TYPEINFOWTPART), 'n/a') AS spObjectiveCT, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_145TYPEINFOWTPART), 'n/a') AS spCavityStd, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPMOUNTWAYAPE' AND lang='en' AND CODE = P.PTC_STR_146TYPEINFOWTPART) spMountWayApE, "); // 실장방식&적용전선
	sb.append("\n         REPLACE(LOWER(PTC_STR_149TYPEINFOWTPART), 'n/a') AS spThickWH, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_157TYPEINFOWTPART), 'n/a') AS spPropEtc, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_158TYPEINFOWTPART), 'n/a') AS spHighVoltageSensingLimit, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_159TYPEINFOWTPART), 'n/a') AS spMovVoltage, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_160TYPEINFOWTPART), 'n/a') AS spToleranceSensing, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_161TYPEINFOWTPART), 'n/a') AS spEsirApprove, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_162TYPEINFOWTPART), 'n/a') AS spMainChipset, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_163TYPEINFOWTPART), 'n/a') AS spFrequency, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_164TYPEINFOWTPART), 'n/a') AS spDcpower, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_165TYPEINFOWTPART), 'n/a') AS spInterfaceIt, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_165TYPEINFOWTPART), 'n/a') AS spFeatures, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_167TYPEINFOWTPART), 'n/a') AS spDevSpec, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMFLAMELEVEL' AND CODE = P.PTC_STR_168TYPEINFOWTPART) spFlameLevel, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_169TYPEINFOWTPART), 'n/a') AS spERatedVoltage, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_170TYPEINFOWTPART), 'n/a') AS spQCNo, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMACLCTNFLAG' AND CODE = P.PTC_STR_171TYPEINFOWTPART) spActuatorLctn, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_172TYPEINFOWTPART), 'n/a') AS spMaxFrequency, ");
	sb.append("\n         REPLACE(LOWER(PTC_STR_173TYPEINFOWTPART), 'n/a') AS spImpedance, ");
	sb.append("\n         (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCONNECTORSTYLE' AND CODE = P.PTC_STR_179TYPEINFOWTPART) spPartStyle, ");
	sb.append("\n         MAT.MATERIAL spAssemblyMaterial, ");
	sb.append("\n         '" + directYn + "' AS directYn ");
	sb.append("\n     FROM WTPARTMASTER PM,  WTPART P , ");
	sb.append("\n        ( ");
	sb.append("\n           SELECT A.TITLE, ");
	sb.append("\n                  TO_NUMBER(A.REFPART) REFPART, ");
	sb.append("\n                  A.IDA2A2 REQIDA2A2, ");
	sb.append("\n                  A.CLASSNAMEA2A2 REQCLASSNAME, ");
	sb.append("\n                  S.STAMPSTATUS, ");
	sb.append("\n                  A.HPSENDSTATUS, ");
	sb.append("\n                  A.HPSENDDATE, ");
	sb.append("\n                  CASE WHEN S.STAMPSTATUS = 'FAIL' THEN '실패' ");
	sb.append("\n                  ELSE CASE WHEN S.STAMPSTATUS = 'SUCCESS' THEN '성공' ");
	sb.append("\n                  ELSE CASE WHEN S.STAMPSTATUS = 'BEFORE'  THEN '대기중' ");
	sb.append("\n                  ELSE CASE WHEN S.STAMPSTATUS = 'QUEUE'   THEN '변환중' ");
	sb.append("\n                  END END END END STAMPSTATUSNAME ");
	sb.append("\n           FROM ");
	sb.append("\n           KETDrawingDistRequest A, KETStamping S,KETStampDistLink L ");
	sb.append("\n           WHERE ");
	sb.append("\n             1=1 ");
	sb.append("\n             AND A.DISTTYPE = 'APPROVED' ");
	sb.append("\n             AND A.BackgroundYn = 'Y' ");
	sb.append("\n             AND S.IDA2A2 = L.IDA3A5 ");
	sb.append("\n             AND L.IDA3B5 = A.IDA2A2 ");
	if ("Y".equals(directYn)) {
	    sb.append("\n             AND A.IDA2A2 = 1 ");
	}
	sb.append("\n        ) REQ   , ");
	sb.append("\n       ( ");
	sb.append("\n       SELECT PJTLINK.BRANCHIDA3B5, PJT.PJTNO, PJT.PJTNAME FROM E3PSProjectMaster PJT, KETPartProjectLink PJTLINK ");
	sb.append("\n       WHERE PJTLINK.IDA3A5 = PJT.IDA2A2 ");
	sb.append("\n       ) PJT , ");
	sb.append("\n       KETPartClassLink CL, KETPartClassification C, ");
	sb.append("\n     ( ");
	sb.append("\n         SELECT WTPARTNUMBER, ");
	sb.append("\n                 LISTAGG(MATERIAL||'^'||MATERIAL_PROPERTIES, '|') WITHIN GROUP (ORDER BY MATERIAL||'^'||MATERIAL_PROPERTIES) AS MATERIAL ");
	sb.append("\n           FROM KETMaterial ");
	sb.append("\n           GROUP BY WTPARTNUMBER ");
	sb.append("\n        ) MAT ");
	sb.append("\n    WHERE PM.IDA2A2 = P.IDA3MASTERREFERENCE ");
	if ("Y".equals(directYn)) {
	    sb.append("\n         AND P.IDA2A2 = REQ.REFPART(+) ");
	} else {
	    sb.append("\n         AND P.IDA2A2 = REQ.REFPART ");
	}
	sb.append("\n         AND P.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5(+) ");
	sb.append("\n         AND CL.IDA3A5 = C.IDA2A2 ");
	sb.append("\n         AND PM.IDA2A2 = CL.IDA3B5 ");
	sb.append("\n         AND PM.WTPARTNUMBER = MAT.WTPARTNUMBER(+) ");

	if ("Y".equals(directYn)) {
	    String partOid = paramObject.getPartOid();
	    sb.append("\n        AND P.IDA2A2 =  " + CommonUtil.getOIDLongValue(partOid) + "   ");
	} else {
	    sb.append("\n        AND REQ.REQIDA2A2 IN ( " + drawingDistReqstOid + "  ) ");
	}

	return sb.toString();
    }

    public boolean sendPartAttr() throws Exception {
	List<Map<String, Object>> partAttrList = this.sendPartAttrList();

	// // distReqEpmDocList 정보로 mssql 에 저장 후 리턴
	PlmHpIfCtl send = new PlmHpIfCtl();
	boolean bSuccess = send.savePartAttrIf(partAttrList);

	return bSuccess;
    }

    @Override
    public List<Map<String, Object>> sendPartAttrList() throws Exception {
	List<Map<String, Object>> partAttrList = new ArrayList<Map<String, Object>>();
	String queryStr = getSendHpPartAttrList();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {
	    partAttrList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getSendHpPartAttrResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return partAttrList;
    }

    private String getSendHpPartAttrList() throws Exception {
	StringBuilder sb = new StringBuilder();
	sb.append("\n    SELECT PC.CATALOGUECODE, PC.CLASSCODE, PA.ATTRCODE, PA.ATTRENAME, PCAL.HPYN, TO_CHAR(NVL(PCAL.INDEXSORT,0)) INDEXSORT  ");
	sb.append("\n    FROM KETPartClassification PC, KETPartClassAttrLink PCAL, KETPartAttribute PA ");
	sb.append("\n    WHERE PC.IDA2A2 = PCAL.IDA3B5 ");
	sb.append("\n          AND PCAL.IDA3A5 = PA.IDA2A2");

	return sb.toString();
    }

    private HashMap<String, Object> getSendHpPartAttrResultSet(ResultSet rs) throws SQLException {
	HashMap<String, Object> partAttr = new HashMap<String, Object>();
	partAttr.put("CLASSCODE", StringUtil.checkNull(rs.getString("CLASSCODE")));
	partAttr.put("ATTRCODE", StringUtil.checkNull(rs.getString("ATTRCODE")));
	partAttr.put("ATTRENAME", StringUtil.checkNull(rs.getString("ATTRENAME")));
	partAttr.put("HPYN", StringUtil.checkNull(rs.getString("HPYN")));
	partAttr.put("INDEXSORT", StringUtil.checkNull(rs.getString("INDEXSORT")));

	return partAttr;
    }

    public boolean saveHpIfFile(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {
	boolean saveType = false;

	if (!ketDrawingDistReqDTO.getOid().equals("")) {

	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject("ext.ket.edm.entity.KETDrawingDistRequest:"
		    + ketDrawingDistReqDTO.getOid());
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    List<KETDrawingDistEpmLink> epmDocLinkList;
	    epmDocLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class, KETDrawingDistRequest.class, drawingDistReq);

	    for (int k = 0; k < epmDocLinkList.size(); k++) {

		KETDrawingDistEpmLink epmDocLink = epmDocLinkList.get(k);
		EPMDocument epmDoc = epmDocLink.getDistEpm();
		QueryResult qr = PersistenceHelper.manager.navigate(epmDocLink, "stampItem", KETStampEpmLink.class);

		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {

			KETStampingItem ketStampingItem = (KETStampingItem) qr.nextElement();
			ContentHolder contentHolder = ContentHelper.service.getContents(ketStampingItem);
			Vector contentList = ContentHelper.getContentListAll(contentHolder);
			for (int i = 0; i < contentList.size(); i++) {
			    ContentItem contentItem = (ContentItem) contentList.elementAt(i);

			    if (contentItem instanceof ApplicationData) {

				ContentInfo info = ContentUtil.getContentInfo(contentHolder, contentItem);
				String filename = info.getName();
				System.out.println(filename);
				int j = info.getName().lastIndexOf(".");
				String renameFile = info.getName().substring(0, j);

				int i3d = renameFile.indexOf("3D");
				boolean is3D = i3d > 0 ? true : false;

				if (is3D) {
				    if (".stp".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFileStepOrg().equals("")
					        && ketDrawingDistReqDTO.getiFileStep() != null) {

					    MultipartFile iFileStep = ketDrawingDistReqDTO.getiFileStep();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileStep, true);

					    // MultipartFile iFileImg = ketDrawingDistReqDTO.getiFile2D();
					    // MultipartFile iFile2D = ketDrawingDistReqDTO.getiFile2D();
					    // MultipartFile iFile3D = ketDrawingDistReqDTO.getiFile3D();
					    // MultipartFile iFileIgs = ketDrawingDistReqDTO.getiFileIgs();

					    updateContent(uploadedFile, contentHolder, "SECONDARY",
						    ketDrawingDistReqDTO.getStepcontentoid());
					    saveType = true;
					}

				    } else if (".pdf".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFile3DOrg().equals("") && ketDrawingDistReqDTO.getiFile3D() != null) {

					    MultipartFile iFile3D = ketDrawingDistReqDTO.getiFile3D();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFile3D, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY",
						    ketDrawingDistReqDTO.getPlane3dcontentoid());
					    saveType = true;

					}

				    } else if (".igs".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFileIgsOrg().equals("") && ketDrawingDistReqDTO.getiFileIgs() != null) {

					    MultipartFile iFileIgs = ketDrawingDistReqDTO.getiFileIgs();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileIgs, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY", ketDrawingDistReqDTO.getIgscontentoid());
					    saveType = true;
					}

				    } else if (".jpg".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFileImgOrg().equals("") && ketDrawingDistReqDTO.getiFileImg() != null) {

					    MultipartFile iFileImg = ketDrawingDistReqDTO.getiFileImg();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileImg, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY", ketDrawingDistReqDTO.getImgcontentoid());
					    saveType = true;
					}
				    }
				} else {
				    if (".pdf".equals(info.getName().substring(j).toLowerCase())) {

					if (!ketDrawingDistReqDTO.getiFile2DOrg().equals("") && ketDrawingDistReqDTO.getiFile2D() != null) {

					    MultipartFile iFile2D = ketDrawingDistReqDTO.getiFile2D();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFile2D, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY",
						    ketDrawingDistReqDTO.getPlanecontentoid());
					    saveType = true;

					}

				    } else if (".igs".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFileIgsOrg().equals("") && ketDrawingDistReqDTO.getiFileIgs() != null) {

					    MultipartFile iFileIgs = ketDrawingDistReqDTO.getiFileIgs();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileIgs, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY", ketDrawingDistReqDTO.getIgscontentoid());
					    saveType = true;

					}

				    } else if (".jpg".equals(info.getName().substring(j).toLowerCase())) {
					if (!ketDrawingDistReqDTO.getiFileImgOrg().equals("") && ketDrawingDistReqDTO.getiFileImg() != null) {

					    MultipartFile iFileImg = ketDrawingDistReqDTO.getiFileImg();
					    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileImg, true);

					    updateContent(uploadedFile, contentHolder, "SECONDARY", ketDrawingDistReqDTO.getImgcontentoid());
					    saveType = true;
					}
				    }
				}
			    }

			}
		    }
		}

	    }

	}

	if (!ketDrawingDistReqDTO.getPartOid().equals("")) {
	    if (ketDrawingDistReqDTO.getiFileImg() != null) {

		WTPart wtpart = (WTPart) CommonUtil.getObject("wt.part.WTPart:" + ketDrawingDistReqDTO.getPartOid());
		String threedType = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.Hompage3DIF);
		if (threedType.equals("NO")) {
		    ContentHolder holder = (ContentHolder) wtpart;
		    holder = ContentHelper.service.getContents(holder);
		    Vector contentVec = ContentHelper.getContentListAll(holder);
		    Iterator<ContentItem> iter = contentVec.iterator();
		    while (iter.hasNext()) {
			ContentItem item = iter.next();
			if (item.getRole() == ContentRoleType.PRIMARY) {

			    if (item instanceof ApplicationData) {

				if (!ketDrawingDistReqDTO.getiFileImgOrg().equals("") && ketDrawingDistReqDTO.getiFileImg() != null) {

				    MultipartFile iFileImg = ketDrawingDistReqDTO.getiFileImg();
				    UploadedFile uploadedFile = UploadedFile.newUploadedFile(iFileImg, true);

				    updateContent(uploadedFile, holder, "PRIMARY", ketDrawingDistReqDTO.getImgcontentoid());
				    saveType = true;
				}

			    }
			}
		    }
		}

	    }
	}
	return saveType;
    }

    public void updateContent(UploadedFile uploadedFile, ContentHolder holder, String fileType, String applicationOid) throws Exception {

	List<ContentItem> contentItems = new ArrayList<ContentItem>(); // not remove contentitem list

	// String filePath = attachFile;
	//
	// filePath = filePath.replace("\\", "/");
	//
	// File file = new File(filePath);
	// Path path = Paths.get(filePath);
	//
	// String name = file.getName();
	// String originalFileName = file.getName();
	// String contentType = "text/plain";
	// byte[] content = null;
	// try {
	// content = Files.readAllBytes(path);
	// } catch (final IOException e) {
	// }
	//
	// if (content == null) {
	// content = getFileContent(file);
	// }

	// MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
	// UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);

	if ("PRIMARY".equals(fileType))
	    this.updateContent(holder, uploadedFile, fileType, contentItems);
	else
	    this.updateContent2(holder, uploadedFile, fileType, contentItems, applicationOid);
    }

    private byte[] getFileContent(File file) throws Exception {

	int fileSize = (int) file.length();
	System.out.println("파일의 사이즈:" + fileSize);

	// 2.파일 사이즈에 해당하는 배열 만들기
	byte[] b = new byte[fileSize];

	FileInputStream fis = new FileInputStream(file);
	int count;
	int pos = 0;
	int size = 10;
	int temp;
	while ((size = fis.read(b, pos, size)) > 0) {
	    pos += size;
	    temp = b.length - pos;
	    if (temp < 10) {
		size = temp;
	    }
	}
	fis.close();
	System.out.println("읽은 바이트 수:" + pos);
	return b;

    }

    public void updateContent(ContentHolder holder, UploadedFile uploadedFile, String fileType, List<ContentItem> contentItems)
	    throws Exception {

	if (uploadedFile != null && uploadedFile.getFile() != null && uploadedFile.getSize() > 0) {
	    ContentRoleType role = ContentRoleType.toContentRoleType(fileType);
	    ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(holder);
	    if (primaryDto != null)
		holder = KETContentHelper.service.delete(holder, (ContentItem) CommonUtil.getObject(primaryDto.getContentoid()));
	    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
	    KETContentHelper.service.attache(holder, uploadedFile, role);
	}
    }

    public void updateContent2(ContentHolder holder, UploadedFile uploadedFile, String fileType, List<ContentItem> contentItems,
	    String applicationOid) throws Exception {

	if (uploadedFile != null && uploadedFile.getFile() != null && uploadedFile.getSize() > 0) {
	    ContentRoleType role = ContentRoleType.toContentRoleType(fileType);
	    ArrayList<ContentDTO> sectDtoList = KETContentHelper.manager.getSecondaryContents(holder);
	    if (sectDtoList != null) {
		for (int i = 0; i < sectDtoList.size(); i++) {
		    ContentDTO secDto = sectDtoList.get(i);

		    if (applicationOid.equals(secDto.getContentoid())) {
			holder = KETContentHelper.service.delete(holder, (ContentItem) CommonUtil.getObject(secDto.getContentoid()));
		    }

		}

	    }
	    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
	    KETContentHelper.service.attache(holder, uploadedFile, role);
	}
    }

}