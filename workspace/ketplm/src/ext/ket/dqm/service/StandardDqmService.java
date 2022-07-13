package ext.ket.dqm.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import wt.content.ContentRoleType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamHelper;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import e3ps.bom.service.KETPartService;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.qms.control.QmsCtl;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.dqm.entity.KETActionDesignReflectLink;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmActionCause;
import ext.ket.dqm.entity.KETDqmActionCauseLink;
import ext.ket.dqm.entity.KETDqmActionDesignReflect;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmRaiseSeries;
import ext.ket.dqm.entity.KETDqmRaiseSeriesLink;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.SessionUtil;

/**
 * 
 * 
 * @클래스명 : StandardDqmService
 * @작성자 : KKW
 * @작성일 : 2014. 7. 11.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardDqmService extends StandardManager implements DqmService {

    private static final long serialVersionUID = 1L;

    public static StandardDqmService newStandardDqmService() throws WTException {
	StandardDqmService instance = new StandardDqmService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<KETDqmHeader> find(KETDqmDTO ketDqmDTO) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PageControl findPaging(KETDqmDTO paramObject) throws Exception {
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

    private QuerySpec getQuery(KETDqmDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
	int idxRaise = query.appendClassList(KETDqmRaise.class, true);
	int idxClose = query.appendClassList(KETDqmClose.class, true);
	int idxUser = query.appendClassList(WTUser.class, false);
	int idxPart = query.appendClassList(WTPart.class, false);

	ClassAttribute caHeaderRaise = new ClassAttribute(KETDqmHeader.class, "raiseReference.key.id");
	ClassAttribute caRaiseHeader = new ClassAttribute(KETDqmRaise.class, "thePersistInfo.theObjectIdentifier.id");

	ClassAttribute caRaiseUserRef = new ClassAttribute(KETDqmRaise.class, "creator.key.id");
	ClassAttribute caRaisePartRef = new ClassAttribute(KETDqmRaise.class, "partReference.key.id");

	ClassAttribute caHeaderClose = new ClassAttribute(KETDqmHeader.class, "closeReference.key.id");
	ClassAttribute caCloseHeader = new ClassAttribute(KETDqmClose.class, "thePersistInfo.theObjectIdentifier.id");

	ClassAttribute caUser = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caPart = new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id");

	// TableColumn tc = new TableColumn(query.getFromClause().getAliasAt(idxRaise), "");
	// tc.setColumnAlias("createStampA2");
	// query.appendSelect(tc, new int[] { idxRaise }, false);

	// query.appendSelect(new ClassAttribute(KETDqmHeader.class, "thePersistInfo.theObjectIdentifier.id"), new int[] { idxHeaer },
	// false);
	// query.appendSelect(new ClassAttribute(KETDqmRaise.class, "thePersistInfo.createStamp"), new int[] { idxRaise }, false);

	sc = new SearchCondition(caRaiseUserRef, SearchCondition.EQUAL, caUser);
	sc.setFromIndicies(new int[] { idxRaise, idxUser }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idxRaise, idxUser });
	query.appendAnd();

	sc = new SearchCondition(caHeaderRaise, SearchCondition.EQUAL, caRaiseHeader);
	sc.setFromIndicies(new int[] { idxHeaer, idxRaise }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idxHeaer, idxRaise });

	query.appendAnd();
	sc = new SearchCondition(caHeaderClose, SearchCondition.EQUAL, caCloseHeader);
	sc.setFromIndicies(new int[] { idxHeaer, idxClose }, 0);
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idxHeaer, idxClose });

	query.appendAnd();
	sc = new SearchCondition(caRaisePartRef, SearchCondition.EQUAL, caPart);
	sc.setFromIndicies(new int[] { idxRaise, idxPart }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idxRaise, idxPart });

	int idxAction = 0;
	int idxActionUser = 0;
	boolean actionFlag = false;

	if (!StringUtil.isTrimToEmpty(paramObject.getMode())) {
	    if (paramObject.getMode().equals("actionSearch")) {
		actionFlag = true;
		idxAction = query.appendClassList(KETDqmAction.class, true);

		ClassAttribute caHeaderAction = new ClassAttribute(KETDqmHeader.class, "actionReference.key.id");
		ClassAttribute caActionHeader = new ClassAttribute(KETDqmAction.class, "thePersistInfo.theObjectIdentifier.id");

		sc = new SearchCondition(caHeaderAction, SearchCondition.EQUAL, caActionHeader);
		query.appendAnd();
		query.appendWhere(sc, new int[] { idxHeaer, idxAction });

		idxActionUser = query.appendClassList(WTUser.class, false);

		ClassAttribute caActionUser = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
		ClassAttribute caActionUserRef = new ClassAttribute(KETDqmAction.class, "userReference.key.id");

		query.appendAnd();
		sc = new SearchCondition(caActionUserRef, SearchCondition.EQUAL, caActionUser);
		sc.setFromIndicies(new int[] { idxAction, idxActionUser }, 0);
		sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
		query.appendWhere(sc, new int[] { idxAction, idxActionUser });

	    }
	}

	if (actionFlag == false
	        && (!StringUtil.isTrimToEmpty(paramObject.getCauseCode()) || !StringUtil.isTrimToEmpty(paramObject.getActionUserName()))) {
	    actionFlag = true;
	    idxAction = query.appendClassList(KETDqmAction.class, true);

	    ClassAttribute caHeaderAction = new ClassAttribute(KETDqmHeader.class, "actionReference.key.id");
	    ClassAttribute caActionHeader = new ClassAttribute(KETDqmAction.class, "thePersistInfo.theObjectIdentifier.id");

	    sc = new SearchCondition(caHeaderAction, SearchCondition.EQUAL, caActionHeader);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idxHeaer, idxAction });

	    idxActionUser = query.appendClassList(WTUser.class, false);

	    ClassAttribute caActionUser = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
	    ClassAttribute caActionUserRef = new ClassAttribute(KETDqmAction.class, "userReference.key.id");

	    query.appendAnd();
	    sc = new SearchCondition(caActionUserRef, SearchCondition.EQUAL, caActionUser);
	    sc.setFromIndicies(new int[] { idxAction, idxActionUser }, 0);
	    sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	    query.appendWhere(sc, new int[] { idxAction, idxActionUser });

	}

	// 완료일은 header state 가 종결인 리스트중 modifydate 가 같은걸로 리스트업 하면 된다.

	// int idxRaiseLink = query.appendClassList(RaiseRole.class, true);

	// int idxAction = query.appendClassList(KETDqmAction.class, true);
	// int idxActionLink = query.appendClassList(ActionRole.class, true);
	//
	// int idxClose = query.appendClassList(KETDqmClose.class, true);
	// int idxCloseLink = query.appendClassList(CloseRole.class, true);
	//
	// query.appendJoin(idxRaiseLink, "roleA", idxHeaer);
	// query.appendJoin(idxRaiseLink, "roleB", idxRaise);
	//
	// query.appendJoin(idxActionLink, "roleA", idxHeaer);
	// query.appendJoin(idxActionLink, "roleB", idxAction);
	//
	// query.appendJoin(idxCloseLink, "roleA", idxHeaer);
	// query.appendJoin(idxCloseLink, "roleB", idxClose);

	if (!StringUtil.isTrimToEmpty(paramObject.getProblem())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmHeader.class, idxHeaer, KETDqmHeader.PROBLEM, paramObject.getProblem(),
		    true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getProblemNo())) {
	    query.appendAnd();

	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmHeader.class, idxHeaer, KETDqmHeader.PROBLEM_NO,
		    paramObject.getProblemNo(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDefectDivCode())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.DEFECT_DIV_CODE,
		    paramObject.getDefectDivCode(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDefectTypeCode())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.DEFECT_TYPE_CODE,
		    paramObject.getDefectTypeCode(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getPjtno())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.PJTNO, paramObject.getPjtno(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getOccurCode())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.OCCUR_CODE, paramObject.getOccurCode(),
		    true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getOccurPlaceName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.OCCUR_PLACE_NAME,
		    paramObject.getOccurPlaceName(), true);
	}
	// if (!StringUtil.isTrimToEmpty(paramObject.getRaiserUserOid())) {
	// query.appendAnd();
	// sc = new SearchCondition(KETDqmRaise.class, "userReference.key.id", SearchCondition.EQUAL, Long.parseLong(paramObject
	// .getRaiserUserOid().split(":")[1]));
	// query.appendWhere(sc, new int[] { idxRaise });
	// }
	if (!StringUtil.isTrimToEmpty(paramObject.getActionDeptName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.ACTION_DEPT_NAME,
		    paramObject.getActionDeptName(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getRaiserUserName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, WTUser.class, idxUser, WTUser.FULL_NAME, paramObject.getRaiserUserName(), true);
	}

	// KETS 사업부 관련 필터링 추가
	CommonUtil.ketsUserListWhereStrQs(query, WTUser.class, idxUser, "thePersistInfo.theObjectIdentifier.id", true);

	if (!StringUtil.isTrimToEmpty(paramObject.getPartCategoryName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.PART_CATEGORY_NAME,
		    paramObject.getPartCategoryName(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getCustomerName())) {
	    // attribute 하나에 , 형태로 데이타가 들어가 있으므로 강제로 like 검색하도록 바꿔줌
	    String customerName = paramObject.getCustomerName().replace(",", "*,*");
	    customerName = "*" + customerName + "*";
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.CUSTOMER_NAME, customerName, true);
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getIssueCode())) {// 문제점 구분
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.ISSUE_CODE, paramObject.getIssueCode(),
		    true);
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getOccurPointCode())) {// 발생시점
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.OCCUR_POINT_CODE,
		    paramObject.getOccurPointCode(), true);
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getRelatedPart())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, WTPart.class, idxPart, WTPart.NUMBER, paramObject.getRelatedPart(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getCauseCode())) {
	    String[] causeCodeArr = paramObject.getCauseCode().split(",");
	    query.appendAnd();
	    query.appendOpenParen();
	    for (int i = 0; i < causeCodeArr.length; i++) {
		if (i != 0) {
		    query.appendOr();
		}
		query.appendWhere(new SearchCondition(KETDqmAction.class, "causeCode", SearchCondition.LIKE, "%" + causeCodeArr[i] + "%"),
		        new int[] { idxAction });
	    }
	    query.appendCloseParen();
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getActionUserName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, WTUser.class, idxActionUser, WTUser.FULL_NAME, paramObject.getActionUserName(),
		    true);
	}

	if (!StringUtil.isTrimToEmpty(paramObject.getCartypeName())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.CARTYPE_NAME,
		    paramObject.getCartypeName(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getApplyArea1())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.APPLY_AREA1,
		    paramObject.getApplyArea1(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getApplyArea2())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.APPLY_AREA2,
		    paramObject.getApplyArea2(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getApplyArea3())) {
	    query.appendAnd();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmRaise.class, idxRaise, KETDqmRaise.APPLY_AREA3,
		    paramObject.getApplyArea3(), true);
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDqmStateName())) {

	    query.appendAnd();
	    query.appendOpenParen();
	    KETQueryUtil.setQuerySpecForMultiSearch(query, KETDqmHeader.class, idxHeaer, KETDqmHeader.DQM_STATE_NAME,
		    paramObject.getDqmStateName(), true);

	    if (query.getConditionCount() > 0 && StringUtils.contains(paramObject.getDqmStateName(), "지연")) {
		query.appendOr();
		Calendar c = Calendar.getInstance();
		String toDayStr = DateUtil.getDateString(c.getTime(), "d");
		Timestamp stamp = DateUtil.getTimestampFormat(toDayStr, "yyyy-MM-dd");

		query.appendWhere(new SearchCondition(KETDqmRaise.class, "requestDate", "<", stamp), new int[] { idxRaise });
		List<String> arr = new ArrayList<String>();
		arr.add("RAISEINWORK"); // 등록중
		arr.add("ACTIONAPPROVED"); // 검토확인
		arr.add("END");// 종결
		arr.add("RAISEREJECTED"); // 등록중 상태에서 반려됨

		ClassAttribute dqmStateName = new ClassAttribute(KETDqmHeader.class, "dqmStateCode");
		// Object aa[] = arr.toArray();
		query.appendAnd();
		query.appendWhere(new SearchCondition(dqmStateName, SearchCondition.NOT_IN, new ArrayExpression(arr.toArray())),
		        new int[] { idxHeaer });

	    }

	    query.appendCloseParen();
	}
	if (StringUtil.checkString(paramObject.getCreateStartDate())) {
	    query.appendAnd();
	    Timestamp stamp = DateUtil.convertStartDate2(paramObject.getCreateStartDate());
	    query.appendWhere(new SearchCondition(KETDqmRaise.class, "thePersistInfo.createStamp", ">=", stamp), new int[] { idxRaise });
	}
	if (StringUtil.checkString(paramObject.getCreateEndDate())) {
	    query.appendAnd();
	    Timestamp stamp = DateUtil.convertStartDate2(paramObject.getCreateEndDate());
	    query.appendWhere(new SearchCondition(KETDqmRaise.class, "thePersistInfo.createStamp", "<", stamp), new int[] { idxRaise });
	}
	if (StringUtil.checkString(paramObject.getCompStartDate())) {
	    query.appendAnd();
	    Timestamp stamp = DateUtil.convertStartDate2(paramObject.getCompStartDate());
	    query.appendWhere(new SearchCondition(KETDqmClose.class, KETDqmClose.REVIEW_DATE, ">=", stamp), new int[] { idxClose });
	}
	if (StringUtil.checkString(paramObject.getCompEndDate())) {
	    query.appendAnd();
	    Timestamp stamp = DateUtil.convertStartDate2(paramObject.getCompEndDate());
	    query.appendWhere(new SearchCondition(KETDqmClose.class, KETDqmClose.REVIEW_DATE, "<", stamp), new int[] { idxClose });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    String checkName = paramObject.getSortName();

	    if (checkName.equals("problem") || checkName.equals("-problem")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName.substring(1), "problem", true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName, "problem", false);
		}
	    } else if (checkName.equals("problemNo") || checkName.equals("-problemNo")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName.substring(1), "problemNo", true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName, "problemNo", false);
		}
	    } else if (checkName.equals("relatedPart") || checkName.equals("-relatedPart")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, WTPart.class, idxPart, WTPart.NUMBER, checkName.substring(1), true);
		} else {
		    SearchUtil.setOrderBy(query, WTPart.class, idxPart, WTPart.NUMBER, checkName, false);
		}
	    } else if (checkName.equals("reviewDate") || checkName.equals("-reviewDate")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmClose.class, idxClose, checkName.substring(1), "reviewDate", true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmClose.class, idxClose, checkName, "reviewDate", false);
		}
	    } else if (checkName.equals("dqmStateName") || checkName.equals("-dqmStateName")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName.substring(1), "dqmStateName", true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, checkName, "dqmStateName", false);
		}
	    } else if (checkName.equals("thePersistInfo.createStamp") || checkName.equals("-thePersistInfo.createStamp")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmRaise.class, idxRaise, checkName.substring(1), "createDate", true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmRaise.class, idxRaise, checkName, "createDate", false);
		}
	    } else {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    SearchUtil.setOrderBy(query, KETDqmRaise.class, idxRaise, checkName.substring(1), paramObject.getSortName()
			    .substring(1), true);
		} else {
		    SearchUtil.setOrderBy(query, KETDqmRaise.class, idxRaise, checkName, checkName, false);
		}
	    }
	} else {
	    SearchUtil.setOrderBy(query, KETDqmHeader.class, idxHeaer, KETDqmHeader.PROBLEM_NO, true);
	}
	Kogger.debug(getClass(), "query >> " + query);

	return query;
    }

    @Override
    public KETDqmHeader save(KETDqmDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public void deleteDQMWorkItemByInwork(KETDqmHeader ketDqmHeader) throws Exception {// 최초 등록된 to-do를 종료
	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_INWORK");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}
    }

    @Override
    public void createDQMWorkItemByInwork(KETDqmHeader ketDqmHeader) throws Exception {// 최초 등록시 to-do 등록
	KETDqmTodoAtivity ketDqmTodoAtivity = KETDqmTodoAtivity.newKETDqmTodoAtivity();
	ketDqmTodoAtivity.setHeader(ketDqmHeader);
	ketDqmTodoAtivity.setTaskCode("DQM_INWORK");
	ketDqmTodoAtivity.setTaskName("ISSUE 등록");

	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String lcName = "KET_ECA_LC";
	LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmTodoAtivity,
	        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	String folderPath = "/Default/자동차사업부/초기유동/";

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) ketDqmTodoAtivity, folder);

	ketDqmTodoAtivity = (KETDqmTodoAtivity) PersistenceHelper.manager.save(ketDqmTodoAtivity);

	Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
	WfTemplateObject paramWfTemplateObject = null;

	while (allTemplates.hasMoreElements()) {
	    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

	    if (wfTemplate.getName().equals("KET_TODO_WF")) {
		paramWfTemplateObject = wfTemplate;
	    }
	}

	Team team = (Team) ketDqmTodoAtivity.getTeamId().getObject();
	WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
	        ketDqmTodoAtivity.getContainerReference());
	ProcessData data = process.getContext();
	WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketDqmTodoAtivity);
	data.setValue("charge", (WTUser) ketDqmHeader.getRaise().getCreator().getPrincipal());
	WfEngineHelper.service.startProcess(process, data, 1);
    }

    @Override
    public KETDqmDTO saveRaise(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmHeader ketDqmHeader = KETDqmHeader.newKETDqmHeader();
	KETDqmRaise ketDqmRaise = KETDqmRaise.newKETDqmRaise();

	ketDqmHeader.setProblemNo(getProblemNo());
	ketDqmHeader.setProblem(paramObject.getProblem());

	ketDqmRaise.setPjtno(paramObject.getPjtno());
	ketDqmRaise.setPjtname(paramObject.getPjtname());

	ketDqmRaise.setCustomerName(paramObject.getCustomerName());
	ketDqmRaise.setCustomerCode(paramObject.getCustomerCode());

	ketDqmRaise.setCartypeName(paramObject.getCartypeName());
	ketDqmRaise.setCartypeCode(paramObject.getCartypeCode());
	ketDqmRaise.setProblemTypeName(paramObject.getProblemTypeName());

	ketDqmRaise.setUrgencyCode(paramObject.getUrgencyCode());
	ketDqmRaise.setUrgencyName(CodeHelper.manager.getCodeValue("EMERGENCYPOSITION", paramObject.getUrgencyCode()));

	ketDqmRaise.setImportanceCode(paramObject.getImportanceCode());// 중요도 코드
	ketDqmRaise.setImportanceName(CodeHelper.manager.getCodeValue("EMERGENCYPOSITION", paramObject.getImportanceCode())); // 중요도 명칭

	ketDqmRaise.setIssueCode(paramObject.getIssueCode()); // 문제점구분 코드
	ketDqmRaise.setIssueName(CodeHelper.manager.getCodeValue("DQMISSUETYPE", paramObject.getIssueCode())); // 문제점구분 명칭

	ketDqmRaise.setOccurPointCode(paramObject.getOccurPointCode());// 발생시점 코드
	ketDqmRaise.setOccurPointName(CodeHelper.manager.getCodeValue("DQMOCCURPOINT", paramObject.getOccurPointCode()));// 발생시점 명칭

	ketDqmRaise.setPartCategoryName(paramObject.getPartCategoryName());
	ketDqmRaise.setPartCategoryCode(paramObject.getPartCategoryCode());

	ketDqmRaise.setOccurDivCode(paramObject.getOccurDivCode());
	ketDqmRaise.setOccurDivName(CodeHelper.manager.getCodeValue("OCCURTYPE", paramObject.getOccurDivCode()));

	ketDqmRaise.setOccurPlaceName(paramObject.getOccurPlaceName());

	ketDqmRaise.setOccurStepCode(paramObject.getOccurStepCode());
	if (StringUtil.checkString(paramObject.getOccurStepCode())) {
	    ketDqmRaise.setOccurStepName(CodeHelper.manager.getCodeValue("DQMDEVSTEP", paramObject.getOccurStepCode()));
	}

	ketDqmRaise.setOccurCode(paramObject.getOccurCode());
	ketDqmRaise.setOccurName(CodeHelper.manager.getCodeValue("OCCURPLACE", paramObject.getOccurCode()));

	ketDqmRaise.setApplyArea1(paramObject.getApplyArea1());
	ketDqmRaise.setApplyArea2(paramObject.getApplyArea2());
	ketDqmRaise.setApplyArea3(paramObject.getApplyArea3());

	ketDqmRaise.setDefectDivCode(paramObject.getDefectDivCode());
	ketDqmRaise.setDefectDivName(CodeHelper.manager.getCodeValue("PROBLEMDIVTYPE", paramObject.getDefectDivCode()));
	ketDqmRaise.setDefectTypeCode(paramObject.getDefectTypeCode());
	ketDqmRaise.setDefectTypeName(CodeHelper.manager.getCodeValue("PROBLEMDIVTYPE", paramObject.getDefectTypeCode()));

	if (!(paramObject.getRelatedPartOid().equals("") || paramObject.getRelatedPartOid().equals(null))) {
	    WTPart wtPart = (WTPart) CommonUtil.getObject(paramObject.getRelatedPartOid());
	    ketDqmRaise.setPart(wtPart);
	}

	// 로직이 변경되어 제기자가 직접 종결담당자를 지정한다 2021.10.01
	WTUser wtCloseUser = (WTUser) CommonUtil.getObject(paramObject.getCloserOid());
	ketDqmRaise.setCloseUser(wtCloseUser);

	// 로직이 변경되어 품질문제 등록시 제기자가 검토자를 지정한다 2021.10.01 (기존에는 PM이 TO-DO에서 지정했음)
	WTUser wtActionUser = (WTUser) CommonUtil.getObject(paramObject.getActionUserOid());
	setDqmRaiseActionUser(ketDqmRaise, wtActionUser);

	if (!(paramObject.getPjtoid().equals("") || paramObject.getPjtoid().equals(null))) {
	    ProductProject productProject = (ProductProject) CommonUtil.getObject(paramObject.getPjtoid());
	    ketDqmRaise.setProductProject(productProject);
	    E3PSProjectData projectData = new E3PSProjectData(productProject);
	    WTUser wtPmUser = projectData.pjtPm;
	    // WTUser wtCloseUser = projectData.pjtQA;
	    // if (wtCloseUser == null && "전자 사업부".equals(productProject.getTeamType())) {
	    // wtCloseUser = projectData.pjtQC;
	    // }
	    ketDqmRaise.setPmUser(wtPmUser);
	    // if (wtCloseUser != null) {
	    // ketDqmRaise.setCloseUser(wtCloseUser);
	    // }
	}
	WTUser createUser = (WTUser) SessionHelper.manager.getPrincipal();
	setDqmRaiseCreatorUser(ketDqmRaise, createUser);

	// if (!(paramObject.getRaiserUserOid().equals("") || paramObject.getRaiserUserOid().equals(null))) {
	// WTUser wtRaiseUser = (WTUser) CommonUtil.getObject(paramObject.getRaiserUserOid());
	// ketDqmRaise.setUser(wtRaiseUser);
	// } else {
	// WTUser wtRaiseUser = (WTUser) SessionHelper.manager.getPrincipal();
	// ketDqmRaise.setUser(wtRaiseUser);
	// }

	if (!(paramObject.getOccurDate().equals("") || paramObject.getOccurDate().equals(null))) {
	    ketDqmRaise.setOccurDate(DateUtil.getTimestampFormat(paramObject.getOccurDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getRequestDate().equals("") || paramObject.getRequestDate().equals(null))) {
	    ketDqmRaise.setRequestDate(DateUtil.getTimestampFormat(paramObject.getRequestDate(), "yyyy-MM-dd"));
	}

	ketDqmRaise.setActionDeptName(paramObject.getActionDeptName());
	ketDqmRaise.setWebEditor(paramObject.getWebEditor());
	ketDqmRaise.setWebEditorText(paramObject.getWebEditorText());

	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String lcName = "KET_COMMON_LC";
	LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmRaise, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	String folderPath = "/Default/자동차사업부/초기유동/";

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) ketDqmRaise, folder);

	ketDqmRaise = (KETDqmRaise) TeamHelper.setTeamTemplate(ketDqmRaise, TeamHelper.service.getTeamTemplate("Default"));

	ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.save(ketDqmRaise);

	// 시리즈 등록
	if (StringUtil.checkString(paramObject.getSeries())) {
	    String[] seriesArr = paramObject.getSeries().split(",");
	    for (int i = 0; i < seriesArr.length; i++) {
		KETDqmRaiseSeries ketDqmRaiseSeries = KETDqmRaiseSeries.newKETDqmRaiseSeries();
		ketDqmRaiseSeries.setCode(seriesArr[i]);
		NumberCode nc = CodeHelper.manager.getNumberCode("SPECSERIES", seriesArr[i]);
		ketDqmRaiseSeries.setName(CodeHelper.manager.getCodeValue("SPECSERIES", seriesArr[i]));
		ketDqmRaiseSeries.setOid(CommonUtil.getOIDString(nc));
		ketDqmRaiseSeries = (KETDqmRaiseSeries) PersistenceHelper.manager.save(ketDqmRaiseSeries);

		KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = KETDqmRaiseSeriesLink
		        .newKETDqmRaiseSeriesLink(ketDqmRaise, ketDqmRaiseSeries);
		ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) PersistenceHelper.manager.save(ketDqmRaiseSeriesLink);
	    }
	}

	KETContentHelper.service.updateContent(ketDqmRaise, paramObject);

	ketDqmHeader.setRaise(ketDqmRaise);

	ketDqmHeader.setDqmStateCode("RAISEINWORK");
	ketDqmHeader.setDqmStateName("등록중");

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.save(ketDqmHeader);

	paramObject.setOid(CommonUtil.getOIDString(ketDqmHeader));
	paramObject.setRaiseOid(CommonUtil.getOIDString(ketDqmRaise));

	if (!(paramObject.getOutputOid().equals("") || paramObject.getOutputOid().equals(null))) {
	    ProjectOutput projectOutput = (ProjectOutput) CommonUtil.getObject(paramObject.getOutputOid());
	    ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.refresh(ketDqmRaise);
	    ProjectTaskCompHelper.service.updateDqmProjectOutputLink(ketDqmRaise, projectOutput);
	}
	createDQMWorkItemByInwork(ketDqmHeader);// 등록중 TO-DO 생성 2021.10.06
	return paramObject;
    }

    public String getProblemNo() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();

	    sql.append("SELECT    'P'").append("|| TO_CHAR (SYSDATE, 'YYMM')").append("|| TRIM(TO_CHAR(SEQ_PROBLEM_NUM.NEXTVAL,'000'))")
		    .append("FROM DUAL");

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

    @Override
    public KETDqmHeader modify(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETDqmHeader delete(KETDqmDTO paramObject) throws Exception {
	return null;
    }

    @Override
    public void deleteRaise(KETDqmDTO paramObject) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	deleteDQMWorkItemByInwork(ketDqmHeader);// 등록중 to-do 종료
	KETDqmRaise ketDqmRaise = (KETDqmRaise) CommonUtil.getObject(paramObject.getRaiseOid());

	PersistenceHelper.manager.delete(ketDqmHeader);
	PersistenceHelper.manager.delete(ketDqmRaise);
    }

    @Override
    public void deleteAction(KETDqmDTO paramObject) throws Exception {
	KETDqmAction ketDqmAction = (KETDqmAction) CommonUtil.getObject(paramObject.getActionOid());

	PersistenceHelper.manager.delete(ketDqmAction);
    }

    @Override
    public KETDqmDTO modifyRaise(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	// KETContentHelper.service.updateContent(ketDqmRaise, paramObject);

	// WorkInProgressHelper.service.checkout(ketDqmHeader, WorkInProgressHelper.service.getCheckoutFolder(), "");
	// modify = (PastProblemManagement) WorkInProgressHelper.service.workingCopyOf(modify);

	ketDqmHeader.setProblem(paramObject.getProblem());

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	ketDqmRaise.setPjtno(paramObject.getPjtno());
	ketDqmRaise.setPjtname(paramObject.getPjtname());

	ketDqmRaise.setCustomerName(paramObject.getCustomerName());
	ketDqmRaise.setCustomerCode(paramObject.getCustomerCode());

	ketDqmRaise.setCartypeName(paramObject.getCartypeName());
	ketDqmRaise.setCartypeCode(paramObject.getCartypeCode());
	ketDqmRaise.setProblemTypeName(paramObject.getProblemTypeName());

	ketDqmRaise.setUrgencyCode(paramObject.getUrgencyCode());
	ketDqmRaise.setUrgencyName(CodeHelper.manager.getCodeValue("EMERGENCYPOSITION", paramObject.getUrgencyCode()));

	ketDqmRaise.setImportanceCode(paramObject.getImportanceCode());// 중요도 코드
	ketDqmRaise.setImportanceName(CodeHelper.manager.getCodeValue("EMERGENCYPOSITION", paramObject.getImportanceCode())); // 중요도 명칭

	ketDqmRaise.setIssueCode(paramObject.getIssueCode()); // 문제점구분 코드
	ketDqmRaise.setIssueName(CodeHelper.manager.getCodeValue("DQMISSUETYPE", paramObject.getIssueCode())); // 문제점구분 명칭

	ketDqmRaise.setOccurPointCode(paramObject.getOccurPointCode());// 발생시점 코드
	ketDqmRaise.setOccurPointName(CodeHelper.manager.getCodeValue("DQMOCCURPOINT", paramObject.getOccurPointCode()));// 발생시점 명칭

	ketDqmRaise.setPartCategoryName(paramObject.getPartCategoryName());
	ketDqmRaise.setPartCategoryCode(paramObject.getPartCategoryCode());

	ketDqmRaise.setOccurDivCode(paramObject.getOccurDivCode());
	ketDqmRaise.setOccurDivName(CodeHelper.manager.getCodeValue("OCCURTYPE", paramObject.getOccurDivCode()));

	ketDqmRaise.setOccurPlaceName(paramObject.getOccurPlaceName());

	ketDqmRaise.setOccurStepCode(paramObject.getOccurStepCode());
	if (StringUtil.checkString(paramObject.getOccurStepCode())) {
	    ketDqmRaise.setOccurStepName(CodeHelper.manager.getCodeValue("DQMDEVSTEP", paramObject.getOccurStepCode()));
	}

	ketDqmRaise.setOccurCode(paramObject.getOccurCode());
	ketDqmRaise.setOccurName(CodeHelper.manager.getCodeValue("OCCURPLACE", paramObject.getOccurCode()));

	ketDqmRaise.setActionDeptName(paramObject.getActionDeptName());
	ketDqmRaise.setWebEditor(paramObject.getWebEditor());
	ketDqmRaise.setWebEditorText(paramObject.getWebEditorText());

	ketDqmRaise.setApplyArea1(paramObject.getApplyArea1());
	ketDqmRaise.setApplyArea2(paramObject.getApplyArea2());
	ketDqmRaise.setApplyArea3(paramObject.getApplyArea3());

	ketDqmRaise.setDefectDivCode(paramObject.getDefectDivCode());
	ketDqmRaise.setDefectDivName(CodeHelper.manager.getCodeValue("PROBLEMDIVTYPE", paramObject.getDefectDivCode()));
	ketDqmRaise.setDefectTypeCode(paramObject.getDefectTypeCode());
	ketDqmRaise.setDefectTypeName(CodeHelper.manager.getCodeValue("PROBLEMDIVTYPE", paramObject.getDefectTypeCode()));

	if (!(paramObject.getRelatedPartOid().equals("") || paramObject.getRelatedPartOid().equals(null))) {
	    WTPart wtPart = (WTPart) CommonUtil.getObject(paramObject.getRelatedPartOid());
	    ketDqmRaise.setPart(wtPart);
	}

	if (!(paramObject.getPjtoid().equals("") || paramObject.getPjtoid().equals(null))) {
	    ProductProject productProject = (ProductProject) CommonUtil.getObject(paramObject.getPjtoid());
	    ketDqmRaise.setProductProject(productProject);
	    E3PSProjectData projectData = new E3PSProjectData(productProject);
	    WTUser wtPmUser = projectData.pjtPm;
	    ketDqmRaise.setPmUser(wtPmUser);
	}

	// 로직이 변경되어 제기자가 직접 종결담당자를 지정한다 2021.10.01
	WTUser wtCloseUser = (WTUser) CommonUtil.getObject(paramObject.getCloserOid());
	ketDqmRaise.setCloseUser(wtCloseUser);

	// 로직이 변경되어 품질문제 등록시 제기자가 검토자를 지정한다 2021.10.01 (기존에는 PM이 TO-DO에서 지정했음)
	WTUser wtActionUser = (WTUser) CommonUtil.getObject(paramObject.getActionUserOid());

	setDqmRaiseActionUser(ketDqmRaise, wtActionUser);

	// if (!(paramObject.getRaiserUserOid().equals("") || paramObject.getRaiserUserOid().equals(null))) {
	// WTUser wtRaiseUser = (WTUser) CommonUtil.getObject(paramObject.getRaiserUserOid());
	// ketDqmRaise.setUser(wtRaiseUser);
	// } else {
	// WTUser wtRaiseUser = (WTUser) SessionHelper.manager.getPrincipal();
	// ketDqmRaise.setUser(wtRaiseUser);
	// }

	if (!(paramObject.getOccurDate().equals("") || paramObject.getOccurDate().equals(null))) {
	    ketDqmRaise.setOccurDate(DateUtil.getTimestampFormat(paramObject.getOccurDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getRequestDate().equals("") || paramObject.getRequestDate().equals(null))) {
	    ketDqmRaise.setRequestDate(DateUtil.getTimestampFormat(paramObject.getRequestDate(), "yyyy-MM-dd"));
	}

	ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.modify(ketDqmRaise);

	// 시리즈 삭제
	QueryResult qrSeries = PersistenceHelper.manager.navigate(ketDqmRaise, "dqmRaiseSeries", KETDqmRaiseSeriesLink.class, false);
	while (qrSeries.hasMoreElements()) {
	    KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) qrSeries.nextElement();
	    KETDqmRaiseSeries ketDqmRaiseSeries = (KETDqmRaiseSeries) ketDqmRaiseSeriesLink.getDqmRaiseSeries();

	    PersistenceHelper.manager.delete(ketDqmRaiseSeriesLink);
	    PersistenceHelper.manager.delete(ketDqmRaiseSeries);
	}

	// 시리즈 등록
	if (StringUtil.checkString(paramObject.getSeries())) {
	    String[] seriesArr = paramObject.getSeries().split(",");
	    for (int i = 0; i < seriesArr.length; i++) {
		KETDqmRaiseSeries ketDqmRaiseSeries = KETDqmRaiseSeries.newKETDqmRaiseSeries();
		ketDqmRaiseSeries.setCode(seriesArr[i]);
		NumberCode nc = CodeHelper.manager.getNumberCode("SPECSERIES", seriesArr[i]);
		ketDqmRaiseSeries.setName(CodeHelper.manager.getCodeValue("SPECSERIES", seriesArr[i]));
		ketDqmRaiseSeries.setOid(CommonUtil.getOIDString(nc));
		ketDqmRaiseSeries = (KETDqmRaiseSeries) PersistenceHelper.manager.save(ketDqmRaiseSeries);

		KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = KETDqmRaiseSeriesLink
		        .newKETDqmRaiseSeriesLink(ketDqmRaise, ketDqmRaiseSeries);
		ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) PersistenceHelper.manager.save(ketDqmRaiseSeriesLink);
	    }
	}

	KETContentHelper.service.updateContent(ketDqmRaise, paramObject);

	/*
	 * if (WorkInProgressHelper.isCheckedOut(modify)) { // KETEarlyWarning checkin modify = (PastProblemManagement)
	 * WorkInProgressHelper.service.checkin(modify, ""); }
	 */
	return paramObject;
    }

    @Override
    public void actionComplete(KETDqmDTO paramObject) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());

	ketDqmHeader.setDqmStateCode("ACTIONAPPROVED");
	ketDqmHeader.setDqmStateName("검토확인");

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

	// todo 생성
	KETWfmHelper.service.createWorkItem(ketDqmHeader);
    }

    @Override
    public KETDqmDTO saveAction(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());

	KETDqmAction ketDqmAction = KETDqmAction.newKETDqmAction();

	ketDqmAction.setImproveWebEditor(paramObject.getImproveWebEditor());
	ketDqmAction.setImproveWebEditorText(paramObject.getImproveWebEditorText());

	ketDqmAction.setCauseWebEditor(paramObject.getCauseWebEditor());
	ketDqmAction.setCauseWebEditorText(paramObject.getCauseWebEditorText());

	// 이전 사용했던 원인 코드 현재는 사용 안함(링크로 바뀜 검색사용위해 넣어줌)
	ketDqmAction.setCauseCode(paramObject.getCauseCode());

	ketDqmAction.setDuplicateReportCode(paramObject.getDuplicateReportCode());
	ketDqmAction.setDuplicateReportName(paramObject.getDuplicateReportName());
	ketDqmAction.setDuplicateYn(paramObject.getDuplicateYn());

	if (!(paramObject.getToDate().equals("") || paramObject.getToDate().equals(null))) {
	    ketDqmAction.setToDate(DateUtil.getTimestampFormat(paramObject.getToDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getDrawingOutDate().equals("") || paramObject.getDrawingOutDate().equals(null))) {
	    ketDqmAction.setDrawingOutDate(DateUtil.getTimestampFormat(paramObject.getDrawingOutDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getMoldModifyDate().equals("") || paramObject.getMoldModifyDate().equals(null))) {
	    ketDqmAction.setMoldModifyDate(DateUtil.getTimestampFormat(paramObject.getMoldModifyDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getTrustTestDate().equals("") || paramObject.getTrustTestDate().equals(null))) {
	    ketDqmAction.setTrustTestDate(DateUtil.getTimestampFormat(paramObject.getTrustTestDate(), "yyyy-MM-dd"));
	}

	if (!(paramObject.getActionUserOid().equals("") || paramObject.getActionUserOid().equals(null))) {
	    WTUser wtActionUser = (WTUser) CommonUtil.getObject(paramObject.getActionUserOid());
	    ketDqmAction.setUser(wtActionUser);
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    ketDqmRaise = setDqmRaiseActionUser(ketDqmRaise, wtActionUser);
	    PersistenceHelper.manager.modify(ketDqmRaise);
	}

	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String lcName = "KET_COMMON_LC";
	LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmAction, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	String folderPath = "/Default/자동차사업부/초기유동/";

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) ketDqmAction, folder);

	ketDqmAction = (KETDqmAction) TeamHelper.setTeamTemplate(ketDqmAction, TeamHelper.service.getTeamTemplate("Default"));

	ketDqmAction = (KETDqmAction) PersistenceHelper.manager.save(ketDqmAction);

	// 원인 등록
	if (StringUtil.checkString(paramObject.getCauseCode())) {
	    String[] causeArr = paramObject.getCauseCode().split(",");
	    for (int i = 0; i < causeArr.length; i++) {
		KETDqmActionCause ketDqmActionCause = KETDqmActionCause.newKETDqmActionCause();
		ketDqmActionCause.setCode(causeArr[i]);
		NumberCode nc = CodeHelper.manager.getNumberCode("PROBLEMTEAM", causeArr[i]);
		ketDqmActionCause.setName(CodeHelper.manager.getCodeValue("PROBLEMTEAM", causeArr[i]));
		ketDqmActionCause.setOid(CommonUtil.getOIDString(nc));
		ketDqmActionCause = (KETDqmActionCause) PersistenceHelper.manager.save(ketDqmActionCause);

		KETDqmActionCauseLink ketdqmActionCauseLink = KETDqmActionCauseLink.newKETDqmActionCauseLink(ketDqmAction,
		        ketDqmActionCause);
		ketdqmActionCauseLink = (KETDqmActionCauseLink) PersistenceHelper.manager.save(ketdqmActionCauseLink);
	    }
	}

	KETContentHelper.service.updateContent(ketDqmAction, paramObject);

	ketDqmHeader.setAction(ketDqmAction);

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "REVIEW_USER_CHOISE");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

	// todo 생성
	KETWfmHelper.service.createWorkItem(ketDqmHeader);

	paramObject.setActionOid(CommonUtil.getOIDString(ketDqmAction));

	return paramObject;
    }

    @Override
    public KETDqmDTO modifyAction(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());

	KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	ketDqmAction.setImproveWebEditor(paramObject.getImproveWebEditor());
	ketDqmAction.setImproveWebEditorText(paramObject.getImproveWebEditorText());

	ketDqmAction.setCauseWebEditor(paramObject.getCauseWebEditor());
	ketDqmAction.setCauseWebEditorText(paramObject.getCauseWebEditorText());

	// 종결에서 검토로 변경
	if (StringUtil.checkString(paramObject.getValidationDate())) {
	    ketDqmAction.setValidationDate(DateUtil.getTimestampFormat(paramObject.getValidationDate(), "yyyy-MM-dd"));
	}
	ketDqmAction.setProblemReflectYn(paramObject.getProblemReflectYn());
	ketDqmAction.setDesignChangeYn(paramObject.getDesignChangeYn());

	// 이전 사용했던 원인 코드 현재는 사용 안함(링크로 바뀜 검색에서는 사용위해 넣어줌)
	ketDqmAction.setCauseCode(paramObject.getCauseCode());

	ketDqmAction.setDuplicateReportCode(paramObject.getDuplicateReportCode());
	ketDqmAction.setDuplicateReportName(paramObject.getDuplicateReportName());
	ketDqmAction.setDuplicateYn(paramObject.getDuplicateYn());

	ketDqmAction.setToDate(null);
	ketDqmAction.setDrawingOutDate(null);
	ketDqmAction.setMoldModifyDate(null);
	ketDqmAction.setTrustTestDate(null);
	ketDqmAction.setExecEndDate(null);

	if (!(paramObject.getToDate().equals("") || paramObject.getToDate().equals(null))) {
	    ketDqmAction.setToDate(DateUtil.getTimestampFormat(paramObject.getToDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getDrawingOutDate().equals("") || paramObject.getDrawingOutDate().equals(null))) {
	    ketDqmAction.setDrawingOutDate(DateUtil.getTimestampFormat(paramObject.getDrawingOutDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getMoldModifyDate().equals("") || paramObject.getMoldModifyDate().equals(null))) {
	    ketDqmAction.setMoldModifyDate(DateUtil.getTimestampFormat(paramObject.getMoldModifyDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getTrustTestDate().equals("") || paramObject.getTrustTestDate().equals(null))) {
	    ketDqmAction.setTrustTestDate(DateUtil.getTimestampFormat(paramObject.getTrustTestDate(), "yyyy-MM-dd"));
	}
	if (!(paramObject.getExecEndDate().equals("") || paramObject.getExecEndDate().equals(null))) {
	    ketDqmAction.setExecEndDate(DateUtil.getTimestampFormat(paramObject.getExecEndDate(), "yyyy-MM-dd"));
	}

	ketDqmAction.setMainSubject(paramObject.getMainSubject());

	boolean userChangeFlag = false;
	if (!(paramObject.getActionUserOid().equals("") || paramObject.getActionUserOid().equals(null))) {
	    WTUser wtActionUser = (WTUser) CommonUtil.getObject(paramObject.getActionUserOid());

	    if (!wtActionUser.equals(ketDqmAction.getUser())) {
		ketDqmAction.setUser(wtActionUser);
		KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
		ketDqmRaise = setDqmRaiseActionUser(ketDqmRaise, wtActionUser);
		PersistenceHelper.manager.modify(ketDqmRaise);
		userChangeFlag = true;
	    }
	}

	ketDqmAction.setReviewDate(DateUtil.getCurrentTimestamp());

	ketDqmAction = (KETDqmAction) PersistenceHelper.manager.modify(ketDqmAction);

	// 설계반영여부 삭제
	QueryResult qrDesignReflect = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionDesignReflect",
	        KETActionDesignReflectLink.class, false);
	while (qrDesignReflect.hasMoreElements()) {
	    KETActionDesignReflectLink ketActionDesignReflectLink = (KETActionDesignReflectLink) qrDesignReflect.nextElement();
	    KETDqmActionDesignReflect ketDqmActionDesignReflect = (KETDqmActionDesignReflect) ketActionDesignReflectLink
		    .getDqmActionDesignReflect();

	    PersistenceHelper.manager.delete(ketActionDesignReflectLink);
	    PersistenceHelper.manager.delete(ketDqmActionDesignReflect);
	}

	// 설계반영여부 등록
	if (StringUtil.checkString(paramObject.getDesignReflect())) {
	    String[] designReflectArr = paramObject.getDesignReflect().split(",");
	    for (int i = 0; i < designReflectArr.length; i++) {
		KETDqmActionDesignReflect ketDqmActionDesignReflect = KETDqmActionDesignReflect.newKETDqmActionDesignReflect();
		ketDqmActionDesignReflect.setCode(designReflectArr[i]);
		NumberCode nc = CodeHelper.manager.getNumberCode("DESIGNREFLECT", designReflectArr[i]);
		ketDqmActionDesignReflect.setName(CodeHelper.manager.getCodeValue("DESIGNREFLECT", designReflectArr[i]));
		ketDqmActionDesignReflect.setOid(CommonUtil.getOIDString(nc));
		ketDqmActionDesignReflect = (KETDqmActionDesignReflect) PersistenceHelper.manager.save(ketDqmActionDesignReflect);

		KETActionDesignReflectLink ketActionDesignReflectLink = KETActionDesignReflectLink.newKETActionDesignReflectLink(
		        ketDqmAction, ketDqmActionDesignReflect);
		ketActionDesignReflectLink = (KETActionDesignReflectLink) PersistenceHelper.manager.save(ketActionDesignReflectLink);
	    }
	}

	// 원인삭제
	QueryResult qrCause = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionCause", KETDqmActionCauseLink.class, false);
	while (qrCause.hasMoreElements()) {
	    KETDqmActionCauseLink ketDqmActionCauseLink = (KETDqmActionCauseLink) qrCause.nextElement();
	    KETDqmActionCause ketDqmActionCause = (KETDqmActionCause) ketDqmActionCauseLink.getDqmActionCause();

	    PersistenceHelper.manager.delete(ketDqmActionCauseLink);
	    PersistenceHelper.manager.delete(ketDqmActionCause);
	}

	// 원인 등록
	if (StringUtil.checkString(paramObject.getCauseCode())) {
	    String[] causeArr = paramObject.getCauseCode().split(",");
	    for (int i = 0; i < causeArr.length; i++) {
		KETDqmActionCause ketDqmActionCause = KETDqmActionCause.newKETDqmActionCause();
		ketDqmActionCause.setCode(causeArr[i]);
		NumberCode nc = CodeHelper.manager.getNumberCode("PROBLEMTEAM", causeArr[i]);
		ketDqmActionCause.setName(CodeHelper.manager.getCodeValue("PROBLEMTEAM", causeArr[i]));
		ketDqmActionCause.setOid(CommonUtil.getOIDString(nc));
		ketDqmActionCause = (KETDqmActionCause) PersistenceHelper.manager.save(ketDqmActionCause);

		KETDqmActionCauseLink ketdqmActionCauseLink = KETDqmActionCauseLink.newKETDqmActionCauseLink(ketDqmAction,
		        ketDqmActionCause);
		ketdqmActionCauseLink = (KETDqmActionCauseLink) PersistenceHelper.manager.save(ketdqmActionCauseLink);
	    }
	}

	if (userChangeFlag) {
	    // todo 종료
	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	    sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmHeader));
	    query.appendWhere(sc, new int[] { idxTodoActivity });
	    query.appendAnd();
	    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");

	    query.appendWhere(sc, new int[] { idxTodoActivity });

	    QueryResult qr = PersistenceHelper.manager.find(query);
	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
		WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	    }

	    // todo 생성
	    KETWfmHelper.service.createWorkItem(ketDqmHeader);
	}

	KETContentHelper.service.updateContent(ketDqmAction, paramObject);

	/*
	 * if (WorkInProgressHelper.isCheckedOut(modify)) { // KETEarlyWarning checkin modify = (PastProblemManagement)
	 * WorkInProgressHelper.service.checkin(modify, ""); }
	 */
	return paramObject;
    }

    @Override
    public KETDqmDTO modifyClose(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());

	KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	ketDqmClose.setValidationCheck(paramObject.getValidationCheck());

	if (StringUtil.checkString(paramObject.getApplyExpectDate())) {
	    ketDqmClose.setApplyExpectDate(DateUtil.getTimestampFormat(paramObject.getApplyExpectDate(), "yyyy-MM-dd"));
	}
	// 종결에서 검토로 변경
	// if (StringUtil.checkString(paramObject.getValidationDate())) {
	// ketDqmClose.setValidationDate(DateUtil.getTimestampFormat(paramObject.getValidationDate(), "yyyy-MM-dd"));
	// }
	//
	// ketDqmClose.setProblemReflectYn(paramObject.getProblemReflectYn());

	ketDqmClose.setReviewRsltCode(paramObject.getReviewRsltCode());

	ketDqmClose.setReviewCheckCode(paramObject.getReviewCheckCode());

	ketDqmClose.setWebEditor(paramObject.getReviewWebEditor());
	ketDqmClose.setWebEditorText(paramObject.getReviewWebEditorText());

	ketDqmClose = (KETDqmClose) PersistenceHelper.manager.modify(ketDqmClose);

	KETContentHelper.service.updateContent(ketDqmClose, paramObject);

	/*
	 * if (WorkInProgressHelper.isCheckedOut(modify)) { // KETEarlyWarning checkin modify = (PastProblemManagement)
	 * WorkInProgressHelper.service.checkin(modify, ""); }
	 */
	return paramObject;
    }

    @Override
    public KETDqmDTO saveClose(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmClose ketDqmClose = KETDqmClose.newKETDqmClose();

	if (StringUtil.checkString(paramObject.getApplyExpectDate())) {
	    ketDqmClose.setApplyExpectDate(DateUtil.getTimestampFormat(paramObject.getApplyExpectDate(), "yyyy-MM-dd"));
	}
	// 종결에서 검토로 변경
	// if (StringUtil.checkString(paramObject.getValidationDate())) {
	// ketDqmClose.setValidationDate(DateUtil.getTimestampFormat(paramObject.getValidationDate(), "yyyy-MM-dd"));
	// }
	//
	// ketDqmClose.setProblemReflectYn(paramObject.getProblemReflectYn());

	ketDqmClose.setReviewRsltCode(paramObject.getReviewRsltCode());

	ketDqmClose.setReviewCheckCode(paramObject.getReviewCheckCode());

	ketDqmClose.setValidationCheck(paramObject.getValidationCheck());

	ketDqmClose.setWebEditor(paramObject.getReviewWebEditor());
	ketDqmClose.setWebEditorText(paramObject.getReviewWebEditorText());

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	ketDqmClose.setUser(wtuser);

	// 검토일자는 종결시에 자동 입력
	// ketDqmClose.setReviewDate(DateUtil.getTimestampFormat(paramObject.getReviewDate(), "yyyy-MM-dd"));

	ketDqmClose = (KETDqmClose) PersistenceHelper.manager.save(ketDqmClose);

	KETContentHelper.service.updateContent(ketDqmClose, paramObject);

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	ketDqmHeader.setClose(ketDqmClose);

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	paramObject.setCloseOid(CommonUtil.getOIDString(ketDqmClose));

	return paramObject;
    }

    @Override
    public void deleteClose(KETDqmDTO paramObject) throws Exception {
	KETDqmClose ketDqmClose = (KETDqmClose) CommonUtil.getObject(paramObject.getCloseOid());
	PersistenceHelper.manager.delete(ketDqmClose);
    }

    @Override
    public void saveReason(KETDqmDTO paramObject) throws Exception {
	KETDqmClose ketDqmClose = KETDqmClose.newKETDqmClose();

	// ketDqmClose.setReviewRsltCode(paramObject.getReviewRsltCode());
	ketDqmClose.setWebEditor(paramObject.getWebEditor());
	ketDqmClose.setWebEditorText(paramObject.getWebEditorText());

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	ketDqmClose.setUser(wtuser);

	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	ketDqmClose.setReviewDate(timestamp);
	ketDqmClose.setReviewRsltCode("REJECT");

	ketDqmClose = (KETDqmClose) PersistenceHelper.manager.save(ketDqmClose);

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	ketDqmHeader.setClose(ketDqmClose);
	ketDqmHeader.setDqmStateCode("END");
	ketDqmHeader.setDqmStateName("종결");

	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	query.appendOpenParen();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");
	query.appendWhere(sc, new int[] { idxTodoActivity });

	query.appendOr();

	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "REVIEW_USER_CHOISE");
	query.appendWhere(sc, new int[] { idxTodoActivity });

	query.appendCloseParen();

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

	// 메일 발송
	List<WTUser> toUserList = new ArrayList<WTUser>();
	toUserList.add((WTUser) ketDqmHeader.getRaise().getCreator().getPrincipal());
	if (ketDqmHeader.getAction() != null) {
	    if (ketDqmHeader.getAction().getUser() != null) {
		toUserList.add(ketDqmHeader.getAction().getUser());
	    }
	}

	// 프로젝트 CFT 멤버 메일 발송 추가
	QueryResult list = ProjectUserHelper.manager.getAllProjectUser(ProjectHelper.getProject(ketDqmHeader.getRaise().getPjtno()));
	while (list.hasMoreElements()) {
	    Object[] objArr = (Object[]) list.nextElement();
	    ProjectMemberLink link = (ProjectMemberLink) objArr[0];
	    WTUser user = link.getMember();
	    toUserList.add(user);
	}

	KETMailHelper.service.sendFormMail("08039", "DQMNoticeMail.html", ketDqmHeader, (WTUser) SessionHelper.manager.getPrincipal(),
	        toUserList);

	ProjectTaskCompHelper.service.completeDqmProjectOutput(ketDqmHeader.getRaise());

    }

    @Override
    public void reReview(KETDqmDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	ketDqmHeader.setDqmStateCode("ACTIONINWORK");
	ketDqmHeader.setDqmStateName("담당자접수");
	ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	// 재검토시 담당자접수상태로 만들어 결재를 다시 받는 프로세스는 주석처리한다 2021.10.06
	// KETDqmAction ketDqmAction = ketDqmHeader.getAction();
	// LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ketDqmAction, State.toState("INWORK"));
	// WfProcess process = (WfProcess) LifeCycleHelper.service.getCurrentWorkflow((LifeCycleManaged) ketDqmAction).getObject();
	// PersistenceHelper.manager.modify(ketDqmAction);

	KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	if (ketDqmClose != null && !ketDqmClose.equals(null)) {
	    PersistenceHelper.manager.delete(ketDqmClose);
	}

	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_CLOSE");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

	// todo 생성
	KETWfmHelper.service.createWorkItem(ketDqmHeader);

	// 결재를 받지 않고 개선안진행 할 수 있는 상태로 변경
	ketDqmHeader.setDqmStateCode("ACTIONPROGRESS");
	ketDqmHeader.setDqmStateName("개선안진행");
	PersistenceHelper.manager.modify(ketDqmHeader);

    }

    @Override
    public void close(KETDqmDTO paramObject) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(paramObject.getOid());
	ketDqmHeader.setDqmStateCode("END");
	ketDqmHeader.setDqmStateName("종결");
	// 검토일자는 종결시에 자동 입력

	KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	ketDqmClose.setReviewDate(new Timestamp(System.currentTimeMillis()));

	PersistenceHelper.manager.modify(ketDqmClose);
	PersistenceHelper.manager.modify(ketDqmHeader);

	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_CLOSE");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

	// 메일 발송
	List<WTUser> toUserList = new ArrayList<WTUser>();
	toUserList.add((WTUser) ketDqmHeader.getRaise().getCreator().getPrincipal());
	toUserList.add(ketDqmHeader.getAction().getUser());

	// 프로젝트 CFT 멤버 메일 발송 추가
	QueryResult list = ProjectUserHelper.manager.getAllProjectUser(ProjectHelper.getProject(ketDqmHeader.getRaise().getPjtno()));
	while (list.hasMoreElements()) {
	    Object[] objArr = (Object[]) list.nextElement();
	    ProjectMemberLink link = (ProjectMemberLink) objArr[0];
	    WTUser user = link.getMember();
	    toUserList.add(user);
	}

	KETMailHelper.service.sendFormMail("08039", "DQMNoticeMail.html", ketDqmHeader, (WTUser) SessionHelper.manager.getPrincipal(),
	        toUserList);

	ProjectTaskCompHelper.service.completeDqmProjectOutput(ketDqmHeader.getRaise());

	QmsCtl qms = new QmsCtl();
	qms.InsertPLMEcoPjt(paramObject.getOid(), "C");
    }

    @Override
    public String migration(List<HashMap<String, String>> paramList) throws Exception {
	String rslt = "";
	String rsltsys = "";
	// INDEX1 No 1
	// INDEX2 문제점* 060110 WP 6M Assy Clip 삽입 이탈력 발생
	// INDEX3 Project No* A13B033
	// INDEX4 고객사 유라코퍼에이션
	// INDEX5 차종 UM
	// INDEX6 불량구분* 관리불량
	// INDEX7 불량유형* 오부착
	// INDEX8 긴급도* 상
	// INDEX9 적용부위1 인라인
	// INDEX10 적용부위2
	// INDEX11 적용부위3
	// INDEX12 1 Level 일반 Connector
	// INDEX13 2 Level W to W Connector
	// INDEX14 3 Level HSG류 (단품)
	// INDEX15 관련부품* H675922-5
	// INDEX16 발생구분* 신규
	// INDEX17 발생단계 Pilot
	// INDEX18 발생처* 금형
	// INDEX19 발생일 20130715
	// INDEX20 발생장소 신뢰성평가실
	// INDEX21 제기자 김주수
	// INDEX22 요청기한 20130802
	// INDEX23 상세내용 Clip 삽입력 과다
	// ---------------------------------------
	// INDEX24 원인(구분) 설계
	// INDEX25 원인(상세내용) Clip 삽입부 텐션 과다
	// INDEX26 개선대책 Clip 삽입부 두께 축소
	// INDEX27 중복 NO
	// INDEX28 중복문제보고
	// INDEX29 도면출도 20130716
	// INDEX30 금형수정 20130725
	// INDEX31 T/O 20130726
	// INDEX32 신뢰성시험 20130803
	// INDEX33 관련ECR
	// INDEX34 과거차문제점반영 NO
	// INDEX35 검토자 송영찬
	// INDEX36 첨부파일
	// -----------------------------------------
	// INDEX37 검토결과* 종결
	// INDEX38 적용예정일 20130805
	// INDEX39 유효성검증확인 검증완료
	// INDEX40 과거차문제점반영 NO
	// INDEX41 검토의견* 신뢰성평가 완료(합격)
	// INDEX42 첨부파일

	for (int i = 0; i < paramList.size(); i++) {
	    HashMap<String, String> param = paramList.get(i);
	    KETDqmHeader ketDqmHeader = KETDqmHeader.newKETDqmHeader();
	    KETDqmRaise ketDqmRaise = KETDqmRaise.newKETDqmRaise();

	    // INDEX1 No 1
	    // INDEX2 문제점* 060110 WP 6M Assy Clip 삽입 이탈력 발생
	    // INDEX3 Project No* A13B033
	    // INDEX4 고객사 유라코퍼에이션
	    // INDEX5 차종 UM
	    // INDEX6 불량구분* 관리불량
	    // INDEX7 불량유형* 오부착
	    // INDEX8 긴급도* 상
	    // INDEX9 적용부위1 인라인
	    // INDEX10 적용부위2
	    // INDEX11 적용부위3
	    // INDEX12 1 Level 일반 Connector
	    // INDEX13 2 Level W to W Connector
	    // INDEX14 3 Level HSG류 (단품)
	    // INDEX15 관련부품* H675922-5
	    // INDEX16 발생구분* 신규
	    // INDEX17 발생단계 Pilot
	    // INDEX18 발생처* 금형
	    // INDEX19 발생일 20130715
	    // INDEX20 발생장소 신뢰성평가실
	    // INDEX21 제기자 김주수
	    // INDEX22 요청기한 20130802
	    // INDEX23 상세내용 Clip 삽입력 과다

	    ketDqmHeader.setProblemNo(getProblemNo());
	    ketDqmHeader.setProblem(param.get("INDEX2"));

	    ketDqmRaise.setPjtno(param.get("INDEX3"));
	    // ketDqmRaise.setPjtname(paramObject.getPjtname());

	    ketDqmRaise.setCustomerName(param.get("INDEX4"));
	    // ketDqmRaise.setCustomerName(paramObject.getCustomerCode());

	    ketDqmRaise.setCartypeName(param.get("INDEX5"));
	    // ketDqmRaise.setCartypeCode(paramObject.getCartypeCode());

	    // ketDqmRaise.setProblemTypeName(paramObject.getProblemTypeName());

	    ketDqmRaise.setUrgencyCode(CodeHelper.manager.getCodeCodeByValue("EMERGENCYPOSITION", param.get("INDEX8")));
	    ketDqmRaise.setUrgencyName(param.get("INDEX8"));

	    ketDqmRaise.setPartCategoryName(param.get("INDEX14"));
	    // ketDqmRaise.setPartCategoryCode(paramObject.getPartCategoryCode());

	    ketDqmRaise.setOccurDivName(param.get("INDEX16"));
	    ketDqmRaise.setOccurDivCode(CodeHelper.manager.getCodeCodeByValue("OCCURTYPE", param.get("INDEX16")));

	    ketDqmRaise.setOccurPlaceName(param.get("INDEX20"));

	    ketDqmRaise.setOccurStepName(param.get("INDEX17"));
	    if (StringUtil.checkString(param.get("INDEX17"))) {
		ketDqmRaise.setOccurStepCode(CodeHelper.manager.getCodeCodeByValue("DEVYN", param.get("INDEX17")));
	    }

	    ketDqmRaise.setOccurCode(CodeHelper.manager.getCodeCodeByValue("OCCURPLACE", param.get("INDEX18")));
	    ketDqmRaise.setOccurName(param.get("INDEX18"));

	    ketDqmRaise.setApplyArea1(param.get("INDEX9"));
	    ketDqmRaise.setApplyArea2(param.get("INDEX10"));
	    ketDqmRaise.setApplyArea3(param.get("INDEX11"));

	    ketDqmRaise.setDefectDivCode(CodeHelper.manager.getCodeCodeByValue("PROBLEMDIVTYPE", param.get("INDEX6")));
	    ketDqmRaise.setDefectDivName(param.get("INDEX6"));
	    ketDqmRaise.setDefectTypeCode(CodeHelper.manager.getCodeCodeByValue("PROBLEMDIVTYPE", param.get("INDEX7")));
	    ketDqmRaise.setDefectTypeName(param.get("INDEX7"));

	    if (!(param.get("INDEX15").equals("") || param.get("INDEX15").equals(null))) {
		WTPart wtpart = ServiceFactory.getService(KETPartService.class).getPart(StringUtil.trim(param.get("INDEX15")));
		ketDqmRaise.setPart(wtpart);
	    }

	    if (!(param.get("INDEX3").equals("") || param.get("INDEX3").equals(null))) {
		ProductProject productProject = (ProductProject) ProjectHelper.getProject(param.get("INDEX3"));
		ketDqmRaise.setProductProject(productProject);
		E3PSProjectData projectData = new E3PSProjectData(productProject);
		WTUser wtPmUser = projectData.pjtPm;

		ketDqmRaise.setPjtname(projectData.pjtName);
		ketDqmRaise.setPmUser(wtPmUser);
	    }

	    if (!(param.get("INDEX21").equals("") || param.get("INDEX21").equals(null))) {
		ketDqmRaise.setActionDeptName(param.get("INDEX21"));
		WTUser wtRaiseUser = KETObjectUtil.getUser(param.get("INDEX21"));
		ketDqmRaise.setUser(wtRaiseUser);
	    } else {
		WTUser wtRaiseUser = (WTUser) SessionHelper.manager.getPrincipal();
		ketDqmRaise.setUser(wtRaiseUser);
	    }

	    if (!(param.get("INDEX19").equals("") || param.get("INDEX19").equals(null))) {
		double tempDouble = Double.valueOf(param.get("INDEX19"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);

		ketDqmRaise.setOccurDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }

	    if (!(param.get("INDEX22").equals("") || param.get("INDEX22").equals(null))) {
		double tempDouble = Double.valueOf(param.get("INDEX22"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);

		ketDqmRaise.setRequestDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }

	    // ketDqmRaise.setActionDeptName(paramObject.getActionDeptName());
	    ketDqmRaise.setWebEditor(param.get("INDEX23"));
	    ketDqmRaise.setWebEditorText(param.get("INDEX23"));

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = "KET_COMMON_LC";
	    LifeCycleHelper
		    .setLifeCycle((LifeCycleManaged) ketDqmRaise, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    String folderPath = "/Default/자동차사업부/초기유동/";

	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketDqmRaise, folder);

	    ketDqmRaise = (KETDqmRaise) TeamHelper.setTeamTemplate(ketDqmRaise, TeamHelper.service.getTeamTemplate("Default"));

	    ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.save(ketDqmRaise);

	    // LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ketDqmRaise, State.toState("APPROVED"), true);

	    ketDqmHeader.setRaise(ketDqmRaise);

	    ketDqmHeader.setDqmStateCode("END");
	    ketDqmHeader.setDqmStateName("종결");

	    ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.save(ketDqmHeader);

	    // INDEX24 원인(구분) 설계
	    // INDEX25 원인(상세내용) Clip 삽입부 텐션 과다
	    // INDEX26 개선대책 Clip 삽입부 두께 축소
	    // INDEX27 중복 NO
	    // INDEX28 중복문제보고
	    // INDEX29 도면출도 20130716
	    // INDEX30 금형수정 20130725
	    // INDEX31 T/O 20130726
	    // INDEX32 신뢰성시험 20130803
	    // INDEX33 관련ECR
	    // INDEX34 과거차문제점반영 NO
	    // INDEX35 검토자 송영찬
	    // INDEX36 첨부파일

	    KETDqmAction ketDqmAction = KETDqmAction.newKETDqmAction();

	    ketDqmAction.setImproveWebEditor(param.get("INDEX26"));
	    ketDqmAction.setImproveWebEditorText(param.get("INDEX26"));

	    ketDqmAction.setCauseWebEditor(param.get("INDEX25"));
	    ketDqmAction.setCauseWebEditorText(param.get("INDEX25"));

	    // TODO 코드값
	    QuerySpec queryReportCode = new QuerySpec();
	    SearchCondition scReportCode = null;
	    int idxHeader = queryReportCode.appendClassList(KETDqmHeader.class, true);

	    scReportCode = new SearchCondition(KETDqmHeader.class, KETDqmHeader.PROBLEM_NO, SearchCondition.EQUAL, param.get("INDEX28"));

	    queryReportCode.appendWhere(scReportCode, new int[] { idxHeader });

	    QueryResult qrReportCode = PersistenceHelper.manager.find(queryReportCode);
	    while (qrReportCode.hasMoreElements()) {
		Object[] tempObj = (Object[]) qrReportCode.nextElement();
		KETDqmHeader ketDqmHeaderSrch = (KETDqmHeader) tempObj[0];
		ketDqmAction.setDuplicateReportCode(CommonUtil.getOIDString(ketDqmHeaderSrch));
	    }

	    ketDqmAction.setDuplicateReportName(param.get("INDEX28"));
	    ketDqmAction.setDuplicateYn(param.get("INDEX27"));

	    if (StringUtil.checkString(param.get("INDEX31"))) {
		double tempDouble = Double.valueOf(param.get("INDEX31"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);
		ketDqmAction.setToDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }
	    if (StringUtil.checkString(param.get("INDEX29"))) {
		double tempDouble = Double.valueOf(param.get("INDEX29"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);
		ketDqmAction.setDrawingOutDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }
	    if (StringUtil.checkString(param.get("INDEX30"))) {
		double tempDouble = Double.valueOf(param.get("INDEX30"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);
		ketDqmAction.setMoldModifyDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }
	    if (StringUtil.checkString(param.get("INDEX32"))) {
		double tempDouble = Double.valueOf(param.get("INDEX32"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);
		ketDqmAction.setTrustTestDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }

	    if (StringUtil.checkString(param.get("INDEX35"))) {
		WTUser wtActionUser = KETObjectUtil.getUser(param.get("INDEX35"));
		setDqmRaiseActionUser(ketDqmHeader.getRaise(), wtActionUser);
	    } else {
		WTUser wtActionUser = (WTUser) SessionHelper.manager.getPrincipal();
		setDqmRaiseActionUser(ketDqmHeader.getRaise(), wtActionUser);
	    }

	    containerRef = WCUtil.getWTContainerRef();
	    lcName = "KET_COMMON_LC";

	    LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmAction,
		    LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    FolderHelper.assignLocation((FolderEntry) ketDqmAction, folder);

	    ketDqmAction = (KETDqmAction) TeamHelper.setTeamTemplate(ketDqmAction, TeamHelper.service.getTeamTemplate("Default"));

	    if (!(param.get("INDEX24").equals("") || param.get("INDEX24").equals(null))) {
		String causeCode = "";
		String nameArr[] = param.get("INDEX24").split(",");
		for (int j = 0; j < nameArr.length; j++) {
		    if (causeCode != "") {
			causeCode += ", ";
		    }
		}
		ketDqmAction.setCauseCode(causeCode);
	    }

	    ketDqmAction = (KETDqmAction) PersistenceHelper.manager.save(ketDqmAction);

	    if (!(param.get("INDEX24").equals("") || param.get("INDEX24").equals(null))) {
		String causeCode = "";
		String nameArr[] = param.get("INDEX24").split(",");
		for (int j = 0; j < nameArr.length; j++) {
		    if (causeCode != "") {
			causeCode += ", ";
		    }
		    causeCode += CodeHelper.manager.getCodeCodeByValue("PROBLEMTEAM", nameArr[j].trim());

		    KETDqmActionCause ketDqmActionCause = KETDqmActionCause.newKETDqmActionCause();
		    ketDqmActionCause.setCode(causeCode);
		    NumberCode nc = CodeHelper.manager.getNumberCode("PROBLEMTEAM", causeCode);
		    ketDqmActionCause.setName(CodeHelper.manager.getCodeValue("PROBLEMTEAM", causeCode));
		    ketDqmActionCause.setOid(CommonUtil.getOIDString(nc));
		    ketDqmActionCause = (KETDqmActionCause) PersistenceHelper.manager.save(ketDqmActionCause);

		    KETDqmActionCauseLink ketdqmActionCauseLink = KETDqmActionCauseLink.newKETDqmActionCauseLink(ketDqmAction,
			    ketDqmActionCause);
		    ketdqmActionCauseLink = (KETDqmActionCauseLink) PersistenceHelper.manager.save(ketdqmActionCauseLink);
		}

		ketDqmAction.setCauseCode(causeCode);
	    }

	    ketDqmAction = (KETDqmAction) PersistenceHelper.manager.modify(ketDqmAction);

	    // todo 종료
	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	    sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmHeader));
	    query.appendWhere(sc, new int[] { idxTodoActivity });
	    query.appendAnd();
	    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "REVIEW_USER_CHOISE");

	    query.appendWhere(sc, new int[] { idxTodoActivity });

	    QueryResult qr = PersistenceHelper.manager.find(query);
	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
		Kogger.debug(getClass(), ketDqmTodoAtivity + "REVIEW_USER_CHOISE TODO 완료 처리");
		WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	    }

	    ketDqmHeader.setAction(ketDqmAction);

	    ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	    // LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ketDqmAction, State.toState("APPROVED"), true);

	    if (StringUtil.checkString(param.get("INDEX36"))) {
		String filePath = param.get("INDEX36");

		filePath = filePath.replace("\\", "/");

		File file = new File(filePath);

		if (file.exists()) {
		    Path path = Paths.get(filePath);
		    String name = file.getName();
		    String originalFileName = file.getName();
		    String contentType = "text/plain";
		    byte[] content = null;
		    try {
			content = Files.readAllBytes(path);
		    } catch (final IOException e) {
		    }
		    MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

		    UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);

		    String roleType = StringUtil.checkNull(uploadedFile.getContentRoleType());
		    ContentRoleType role = ContentRoleType.toContentRoleType(roleType);

		    Transaction trx = new Transaction();
		    try {
			trx.start();
			KETContentHelper.service.attache(ketDqmAction, uploadedFile, role);
			trx.commit();
			trx = null;
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			trx.rollback();
		    } finally {
			if (trx != null) {
			    trx = null;
			}
		    }
		}
	    }

	    // INDEX37 검토결과* 종결
	    // INDEX38 적용예정일 20130805
	    // INDEX39 유효성검증확인 검증완료
	    // INDEX40 과거차문제점반영 NO
	    // INDEX41 검토의견* 신뢰성평가 완료(합격)
	    // INDEX42 첨부파일

	    KETDqmClose ketDqmClose = KETDqmClose.newKETDqmClose();

	    if (StringUtil.checkString(param.get("INDEX38"))) {
		double tempDouble = Double.valueOf(param.get("INDEX38"));
		DecimalFormat df = new DecimalFormat("#.#");

		String tempString = df.format(tempDouble);
		ketDqmClose.setApplyExpectDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
		ketDqmClose.setReviewDate(DateUtil.getTimestampFormat(tempString, "yyyyMMdd"));
	    }

	    if (StringUtil.checkString(param.get("INDEX39"))) {
		ketDqmClose.setValidationCheck(param.get("INDEX39"));
	    }

	    ketDqmClose.setProblemReflectYn(param.get("INDEX40"));

	    ketDqmClose.setReviewRsltCode(CodeHelper.manager.getCodeCodeByValue("DQMREVIEWRESULT", param.get("INDEX37")));

	    // ketDqmClose.setReviewCheckCode(paramObject.getReviewCheckCode());

	    ketDqmClose.setWebEditor(param.get("INDEX41"));
	    ketDqmClose.setWebEditorText(param.get("INDEX41"));

	    ketDqmClose.setUser(ketDqmRaise.getPmUser());

	    // 검토일자는 종결시에 자동 입력
	    // ketDqmClose.setReviewDate(DateUtil.getTimestampFormat(paramObject.getReviewDate(), "yyyy-MM-dd"));

	    ketDqmClose = (KETDqmClose) PersistenceHelper.manager.save(ketDqmClose);

	    // todo 종료
	    query = new QuerySpec();
	    sc = null;
	    idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	    sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmHeader));
	    query.appendWhere(sc, new int[] { idxTodoActivity });
	    query.appendAnd();
	    query.appendOpenParen();
	    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");
	    query.appendWhere(sc, new int[] { idxTodoActivity });

	    query.appendOr();

	    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "REVIEW_USER_CHOISE");
	    query.appendWhere(sc, new int[] { idxTodoActivity });

	    query.appendCloseParen();

	    qr = PersistenceHelper.manager.find(query);
	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
		Kogger.debug(getClass(), ketDqmTodoAtivity + "REVIEW_USER_CHOISE DQM_ACTION TODO 완료 처리");
		WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	    }

	    if (StringUtil.checkString(param.get("INDEX42"))) {
		String filePath = param.get("INDEX42");

		filePath = filePath.replace("\\", "/");

		File file = new File(filePath);

		if (file.exists()) {
		    Path path = Paths.get(filePath);
		    String name = file.getName();
		    String originalFileName = file.getName();
		    String contentType = "text/plain";
		    byte[] content = null;
		    try {
			content = Files.readAllBytes(path);
		    } catch (final IOException e) {
		    }
		    MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

		    UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);

		    String roleType = StringUtil.checkNull(uploadedFile.getContentRoleType());
		    ContentRoleType role = ContentRoleType.toContentRoleType(roleType);

		    Transaction trx = new Transaction();
		    try {
			trx.start();
			KETContentHelper.service.attache(ketDqmClose, uploadedFile, role);
			trx.commit();
			trx = null;
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			trx.rollback();
		    } finally {
			if (trx != null) {
			    trx = null;
			}
		    }
		}
		// KETContentHelper.service.updateContent(ketDqmRaise, paramObject);
	    }

	    ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.refresh(ketDqmHeader);
	    ketDqmHeader.setClose(ketDqmClose);
	    ketDqmHeader.setDqmStateCode("END");
	    ketDqmHeader.setDqmStateName("종결");

	    ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);

	    // todo 종료
	    query = new QuerySpec();
	    sc = null;
	    idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	    sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmHeader));
	    query.appendWhere(sc, new int[] { idxTodoActivity });
	    query.appendAnd();
	    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_CLOSE");

	    query.appendWhere(sc, new int[] { idxTodoActivity });
	    qr = PersistenceHelper.manager.find(query);
	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
		Kogger.debug(getClass(), ketDqmTodoAtivity + "DQM_CLOSE TODO 완료 처리");
		WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	    }
	    rsltsys += i + "번 " + param.get("INDEX2") + " 라인 입력 성공\n";

	    rslt += i + "번 " + param.get("INDEX2") + " 라인 입력 성공<br>";
	}

	Kogger.debug(getClass(), "migration Result >> \n " + rsltsys);

	return rslt;

    }

    @Override
    public void dqmTodoComplete() throws Exception {
	// todo 종료
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);
	int idxHeader = query.appendClassList(KETDqmHeader.class, false);

	ClassAttribute caTodo = new ClassAttribute(KETDqmTodoAtivity.class, "headerReference.key.id");
	ClassAttribute caHeader = new ClassAttribute(KETDqmHeader.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(caTodo, SearchCondition.EQUAL, caTodo);
	sc.setFromIndicies(new int[] { idxTodoActivity, idxHeader }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { idxTodoActivity, idxHeader });
	query.appendAnd();

	sc = new SearchCondition(KETDqmHeader.class, KETDqmHeader.DQM_STATE_CODE, SearchCondition.EQUAL, "END");
	query.appendWhere(sc, new int[] { idxHeader });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    Kogger.debug(getClass(), ketDqmTodoAtivity + "TODO 완료 처리");
	    WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
	}

    }

    @Override
    public QueryResult getSearchList(KETDqmDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);

	List<KETDqmHeader> headerList = new ArrayList<KETDqmHeader>();

	QueryResult qr = PersistenceHelper.manager.find(query);

	return qr;
    }

    @Override
    public void deleteAll() throws Exception {
	QuerySpec query = new QuerySpec();
	query.appendClassList(KETDqmHeader.class, true);

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

	    if (ketDqmHeader.getClose() != null) {
		PersistenceHelper.manager.delete(ketDqmHeader.getClose());
	    }
	    if (ketDqmHeader.getAction() != null) {
		PersistenceHelper.manager.delete(ketDqmHeader.getAction());
	    }
	    PersistenceHelper.manager.delete(ketDqmHeader.getRaise());
	    ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.refresh(ketDqmHeader);
	    PersistenceHelper.manager.delete(ketDqmHeader);
	}

	query = new QuerySpec();
	query.appendClassList(KETDqmTodoAtivity.class, true);

	qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity kettodoAtivity = (KETDqmTodoAtivity) tempObj[0];
	    PersistenceHelper.manager.delete(kettodoAtivity);
	}

    }

    public boolean isCloser(KETDqmRaise ketDqmRaise, WTUser wtUser) throws Exception {
	boolean isCloser = false;

	WTUser wtCloseUser = ketDqmRaise.getCloseUser();

	if (wtCloseUser != null && wtCloseUser.equals(wtUser)) {
	    isCloser = true;
	}
	/*
	 * if ((ketDqmRaise.getProductProject()) != null) { ProductProject productProject = (ProductProject)ketDqmRaise.getProductProject();
	 * 
	 * E3PSProjectData projectData = new E3PSProjectData(productProject); WTUser wtCloseUser = projectData.pjtQA; if(wtCloseUser == null
	 * && "전자 사업부".equals(productProject.getTeamType())){ wtCloseUser = projectData.pjtQC; }
	 * 
	 * if(wtCloseUser != null && wtCloseUser.equals(wtUser)){ isCloser = true; }else{ isCloser = false; }
	 * 
	 * }else{ isCloser = false; }
	 */

	return isCloser;
    }

    @Override
    public boolean authCheck(String checkOid, String type) throws Exception {
	WTUser wtUser = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	PeopleData peopleData = new PeopleData(wtUser);
	String authInDeptNameArr[] = { "커넥터개발1팀", "커넥터개발2팀", "커넥터개발3팀", "커넥터개발4팀", "전장부품개발팀", "커넥터선행개발팀", "커넥터양산개선팀", "프레스금형개발팀",
	        "사출금형개발팀", "전자개발팀", "전자금형개발팀", "시작개발팀", "생산기술개발1팀", "생산기술개발2팀", "친환경모듈개발1팀", "친환경모듈개발2팀", "친환경모듈개발3팀", "중국친환경모듈개발팀",
	        "친환경부품 Gr.", "연구개발1팀", "연구개발2팀", "선행품질관리팀", "선행품질보증2팀", "전자품질관리팀", "선행품질보증1팀", "선행품질보증2팀" };

	Department department = peopleData.department;

	if (department == null) {
	    return false;
	}
	String pmDepartmentName = "";

	if (CommonUtil.isMember("자동차사업부_임원", wtUser) || CommonUtil.isMember("전자사업부_임원", wtUser) || CommonUtil.isMember("개발품질문제", wtUser)
	        || CommonUtil.isMember("자동차사업부_품질", wtUser) || CommonUtil.isMember("자동차사업부_제품설계", wtUser)
	        || CommonUtil.isMember("자동차사업부_금형설계", wtUser)) {
	    return true;
	}

	for (int i = 0; i < authInDeptNameArr.length; i++) {
	    if (department.getName().equals(authInDeptNameArr[i])) {
		// 개발, 선행품질 조직들은 모두 조회 가능함 .. 글로벌 사업부 마케팅팀에서 품질그룹인원의 권한요청이 있었음 때문에 별도 그룹(개발품질문제) 생성하여 관리하기로 함 2017.06.09 by 황정태
		return true;
	    }
	}

	if (type.equals("raise")) {
	    KETDqmRaise ketDqmRaise = (KETDqmRaise) CommonUtil.getObject(checkOid);
	    if (this.isCloser(ketDqmRaise, wtUser)) {// 종결담당자면 조회가능 2016.08.08 황정태 수정
		return true;
	    }
	    KETDqmHeader ketDqmHeader = null;

	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmRaise));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		ketDqmHeader = (KETDqmHeader) tempObj[0];
	    }

	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	    WTUser pmUser = ketDqmRaise.getPmUser();
	    PeopleData pmPeopleData = new PeopleData(pmUser);
	    pmDepartmentName = pmPeopleData.departmentName;

	    if (department.getName().equals(pmDepartmentName)) {
		// PM이 속한 부서는 조회가능
		return true;
	    }

	    WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
	    if (createUser.equals(wtUser)) {
		// 작성자 조회가능
		return true;
	    }

	    // 결재선 체크 플래그
	    boolean approvFlag = false;

	    if (ketDqmAction != null && !ketDqmAction.equals(null)) {
		if (ketDqmAction.getUser() != null && !ketDqmAction.getUser().equals(null)) {
		    if (ketDqmAction.getUser().equals(wtUser)) {
			// 검토자 조회가능
			return true;
		    }
		}

		// 제기 결재선 확인
		WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmAction)));
		KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
		// out.println(master.getPbo().toString()); 결재객체oid확인
		if (master != null) {
		    QuerySpec inquery = new QuerySpec();
		    SearchCondition insc = null;

		    int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		    insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
			    CommonUtil.getOIDLongValue(master));
		    inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		    SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory,
			    "thePersistInfo.theObjectIdentifier.id", true);

		    QueryResult inqr = PersistenceHelper.manager.find(inquery);

		    while (inqr.hasMoreElements()) {
			Object[] tempObj = (Object[]) inqr.nextElement();
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

			WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
			if (apprvUser.equals(wtUser)) {
			    // 결재선 중 자기자신 있으면
			    approvFlag = true;
			    break;
			}
		    }

		}

		// 리턴
		if (approvFlag) {
		    return true;
		}
	    }

	    // 제기 결재선 확인
	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmRaise)));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {
		QuerySpec inquery = new QuerySpec();
		SearchCondition insc = null;

		int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id",
		        true);

		QueryResult inqr = PersistenceHelper.manager.find(inquery);

		while (inqr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) inqr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

		    WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
		    if (apprvUser.equals(wtUser)) {
			// 결재선 중 자기자신 있으면
			approvFlag = true;
			break;
		    }
		}

	    }

	    // 리턴
	    if (approvFlag) {
		return true;
	    }

	} else if (type.equals("action")) {
	    KETDqmAction ketDqmAction = (KETDqmAction) CommonUtil.getObject(checkOid);
	    KETDqmHeader ketDqmHeader = null;

	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmAction));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		ketDqmHeader = (KETDqmHeader) tempObj[0];
		CommonUtil.getOIDString(ketDqmHeader);
	    }

	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	    if (this.isCloser(ketDqmRaise, wtUser)) {// 종결담당자면 조회가능 2016.08.08 황정태 수정
		return true;
	    }

	    WTUser pmUser = ketDqmRaise.getPmUser();
	    PeopleData pmPeopleData = new PeopleData(pmUser);
	    pmDepartmentName = pmPeopleData.departmentName;

	    if (department.getName().equals(pmDepartmentName)) {
		// PM이 속한 부서는 조회가능
		return true;
	    }

	    WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
	    if (createUser.equals(wtUser)) {
		// 작성자 조회가능
		return true;
	    }

	    // 결재선 체크 플래그
	    boolean approvFlag = false;

	    if (ketDqmAction != null && !ketDqmAction.equals(null)) {
		if (ketDqmAction.getUser() != null && !ketDqmAction.getUser().equals(null)) {
		    if (ketDqmAction.getUser().equals(wtUser)) {
			// 검토자 조회가능
			return true;
		    }
		}

		// 제기 결재선 확인
		WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmAction)));
		KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
		// out.println(master.getPbo().toString()); 결재객체oid확인
		if (master != null) {
		    QuerySpec inquery = new QuerySpec();
		    SearchCondition insc = null;

		    int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		    insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
			    CommonUtil.getOIDLongValue(master));
		    inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		    SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory,
			    "thePersistInfo.theObjectIdentifier.id", true);

		    QueryResult inqr = PersistenceHelper.manager.find(inquery);

		    while (inqr.hasMoreElements()) {
			Object[] tempObj = (Object[]) inqr.nextElement();
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

			WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
			if (apprvUser.equals(wtUser)) {
			    // 결재선 중 자기자신 있으면
			    approvFlag = true;
			    break;
			}
		    }

		}

		// 리턴
		if (approvFlag) {
		    return true;
		}
	    }

	    // 제기 결재선 확인
	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmRaise)));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {
		QuerySpec inquery = new QuerySpec();
		SearchCondition insc = null;

		int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id",
		        true);

		QueryResult inqr = PersistenceHelper.manager.find(inquery);

		while (inqr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) inqr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

		    WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
		    if (apprvUser.equals(wtUser)) {
			// 결재선 중 자기자신 있으면
			approvFlag = true;
			break;
		    }
		}

	    }

	    // 리턴
	    if (approvFlag) {
		return true;
	    }

	} else if (!checkOid.equals("") && !checkOid.equals(null)) {

	    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(checkOid);
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    if (this.isCloser(ketDqmRaise, wtUser)) {// 종결담당자면 조회가능 2016.08.08 황정태 수정
		return true;
	    }
	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	    WTUser pmUser = ketDqmRaise.getPmUser();
	    PeopleData pmPeopleData = new PeopleData(pmUser);
	    pmDepartmentName = pmPeopleData.departmentName;

	    if (department.getName().equals(pmDepartmentName)) {
		// PM이 속한 부서는 조회가능
		return true;
	    }

	    WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
	    if (createUser.equals(wtUser)) {
		// 작성자 조회가능
		return true;
	    }

	    // 결재선 체크 플래그
	    boolean approvFlag = false;

	    if (ketDqmAction != null && !ketDqmAction.equals(null)) {
		if (ketDqmAction.getUser() != null && !ketDqmAction.getUser().equals(null)) {
		    if (ketDqmAction.getUser().equals(wtUser)) {
			// 검토자 조회가능
			return true;
		    }
		}

		// 제기 결재선 확인
		WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmAction)));
		KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
		// out.println(master.getPbo().toString()); 결재객체oid확인
		if (master != null) {
		    QuerySpec inquery = new QuerySpec();
		    SearchCondition insc = null;

		    int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		    insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
			    CommonUtil.getOIDLongValue(master));
		    inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		    SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory,
			    "thePersistInfo.theObjectIdentifier.id", true);

		    QueryResult inqr = PersistenceHelper.manager.find(inquery);

		    while (inqr.hasMoreElements()) {
			Object[] tempObj = (Object[]) inqr.nextElement();
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

			WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
			if (apprvUser.equals(wtUser)) {
			    // 결재선 중 자기자신 있으면
			    approvFlag = true;
			    break;
			}
		    }

		}

		// 리턴
		if (approvFlag) {
		    return true;
		}
	    }

	    // 제기 결재선 확인
	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(ketDqmRaise)));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {
		QuerySpec inquery = new QuerySpec();
		SearchCondition insc = null;

		int idxApprovalHistory = inquery.appendClassList(KETWfmApprovalHistory.class, true);

		insc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		inquery.appendWhere(insc, new int[] { idxApprovalHistory });

		SearchUtil.setOrderBy(inquery, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id",
		        true);

		QueryResult inqr = PersistenceHelper.manager.find(inquery);

		while (inqr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) inqr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

		    WTUser apprvUser = (WTUser) history.getOwner().getPrincipal();
		    if (apprvUser.equals(wtUser)) {
			// 결재선 중 자기자신 있으면
			approvFlag = true;
			break;
		    }
		}

	    }

	    // 리턴
	    if (approvFlag) {
		return true;
	    }
	}

	return false;
    }

    @Override
    public void dqmDelayMailSend(String IsMailsend) throws Exception {
	// TODO Auto-generated method stub

	/*
	 * 개발검토문제 지연 대상으로 메일을 발송한다. 2015.10.15 by 황정태 메일 수신자 : 1. 반려됨, 등록중, 제기승인 일때는 작성자와 팀장 및 상위부서장 모두 2. 반려됨, 등록중, 제기승인이 아니고 지연일 때, 검토자가
	 * 지정되어있지 않을 경우 PM, 팀장 및 상위부서장 모두 3. 그 외 지연중일때 검토자와 팀장 및 상위부서장에게 메일발송
	 */
	String ProblemNo = null;
	KETDqmDTO paramObject = new KETDqmDTO();
	paramObject.setDqmStateName("지연");
	// paramObject.setProblemNo("P1504003");

	QueryResult queryResult = this.getSearchList(paramObject);

	// PM, 검토자, 검토자의 센터장 & 팀장

	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    KETDqmHeader ketDqmHeder = (KETDqmHeader) objArr[0];
	    KETDqmRaise kETDqmRaise = (KETDqmRaise) objArr[1];
	    KETDqmAction ketDqmAction = ketDqmHeder.getAction();

	    ProblemNo = ketDqmHeder.getProblemNo();
	    // KETDqmDTO rsltKETDqmDTO = new KETDqmDTO();
	    // rsltKETDqmDTO = rsltKETDqmDTO.KETDqmDTOGrid((KETDqmHeader) objArr[0], rsltKETDqmDTO);

	    // PM, 검토자, 검토자의 센터장 & 팀장
	    PeopleData pm_user = null;
	    PeopleData action_user = null;
	    PeopleData raise_user = null;

	    String pmuseroid = null;
	    String actionuseroid = null;
	    String raiseuseroid = null;
	    if (kETDqmRaise.getPmUserReference() != null) {
		pmuseroid = CommonUtil.getOIDString(kETDqmRaise.getPmUser());// PM
	    }

	    // String actionuseroid = CommonUtil.getOIDString(kETDqmRaise.getUser());// 검토자
	    if (ketDqmAction != null) {
		actionuseroid = CommonUtil.getOIDString(ketDqmAction.getUser());// 검토자
	    }

	    if (kETDqmRaise.getCreator() != null) {
		raiseuseroid = CommonUtil.getOIDString((WTUser) kETDqmRaise.getCreator().getPrincipal());// 작성자
	    }

	    PeopleData ChiefUser = null;

	    String state = ketDqmHeder.getDqmStateCode();
	    if (state.equals("RAISEREJECTED") || state.equals("RAISEINWORK") || state.equals("RAISEUNDERREVIEW")) { // 반려됨, 등록중, 제기승인 일때는
		                                                                                                    // 작성자 에게 메일을 보낸다
		if (StringUtils.isNotEmpty(raiseuseroid)) {
		    raise_user = new PeopleData(raiseuseroid);
		    ChiefUser = raise_user;
		}
	    } else {
/*		if (StringUtils.isNotEmpty(pmuseroid)) {
		    pm_user = new PeopleData(pmuseroid);
		    ChiefUser = pm_user;// 검토자가 지정이 안되어 있을 경우를 대비
		}*/

		if (StringUtils.isNotEmpty(actionuseroid)) {
		    action_user = new PeopleData(actionuseroid);
		    ChiefUser = action_user;
		}
	    }

	    List<WTUser> listToUser = new ArrayList<WTUser>();
	    // PM, 검토자, 검토자의 센터장 & 팀장
	    // 임시로 모든 배치 메일을 한사람에게 보내기
	    listToUser = new ArrayList<WTUser>();
	    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");
	    // listToUser.add(wtUserFrom);
	    if (pm_user != null && !pm_user.isDiable) {
		listToUser.add(pm_user.wtuser); // PM
		Kogger.debug(getClass(), "[ " + ProblemNo + " ] PM : " + pm_user.name + "(" + pm_user.id + ")");
	    }

	    if (action_user != null && !action_user.isDiable) {
		listToUser.add(action_user.wtuser); // 검토자
		Kogger.debug(getClass(), "[ " + ProblemNo + " ] 검토자 : " + action_user.name + "(" + action_user.id + ")");
	    }

	    if (raise_user != null && !raise_user.isDiable) {
		listToUser.add(raise_user.wtuser); // 작성자
		Kogger.debug(getClass(), "[ " + ProblemNo + " ] 작성자 : " + raise_user.name + "(" + raise_user.id + ")");
	    }

	    // List<WTUser> listChiefUser = new ArrayList<WTUser>();
	    // 팀장조회, 부서장 조회(센터장, 본부장)
	    // List<WTUser> listOfficerUser = new ArrayList<WTUser>();
	    ArrayList<Department> OfficerList = new ArrayList<Department>();
	    Department depart = null;

	    try {
		if (PeopleHelper.manager.getChiefUser(ChiefUser.department) != null) {
		    WTUser chief = PeopleHelper.manager.getChiefUser(ChiefUser.department);
		    if (!chief.isDisabled()) {
			listToUser.add(chief);// 팀장
			Kogger.debug(getClass(), "[ " + ProblemNo + " ] 팀장 : " + chief.getFullName() + "(" + chief.getName() + ")");
		    }
		}

		OfficerList = DepartmentHelper.manager.getParentsList(ChiefUser.department, OfficerList);

		if (OfficerList != null && OfficerList.size() > 0) {
		    for (int j = 0; j < OfficerList.size(); j++) {
			depart = (Department) OfficerList.get(j);
			if (depart.getParent() == null) {
			    continue;
			}

			if (PeopleHelper.manager.getChiefUser(depart) != null) {
			    WTUser chief = PeopleHelper.manager.getChiefUser(depart);
			    if (!chief.isDisabled()) {
				listToUser.add(chief);// 부서장(센터장, 본부장)
				Kogger.debug(getClass(),
				        "[ " + ProblemNo + " ] 부서장(센터장, 본부장) : " + chief.getFullName() + "(" + chief.getName() + ")");
			    }
			}
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    if (listToUser.size() > 0 && "1".equals(IsMailsend)) {
		KETMailHelper.service.sendFormMail("08129", "NoticeMailLine2.html", ketDqmHeder, wtUserFrom, listToUser);
	    }
	}
    }

    @Override
    public KETDqmRaise setDqmRaiseCreatorUser(KETDqmRaise obj, WTUser user) throws Exception {

	Map<String, String> info = getUserInfo(user);
	String userName = info.get("userName");
	String deptCode = info.get("deptCode");
	String deptName = info.get("deptName");

	obj.setCreatorUserName(userName);
	obj.setCreatorDeptCode(deptCode);
	obj.setCreatorDeptName(deptName);

	return obj;
    }

    @Override
    public KETDqmRaise setDqmRaiseActionUser(KETDqmRaise obj, WTUser user) throws Exception {

	Map<String, String> info = getUserInfo(user);
	String userName = info.get("userName");
	String deptCode = info.get("deptCode");
	String deptName = info.get("deptName");
	obj.setActionUser(user);
	obj.setActionUserName(userName);
	obj.setActionDeptCode(deptCode);
	obj.setActionUserDeptName(deptName);

	return obj;

    }

    private Map<String, String> getUserInfo(WTUser user) throws Exception {
	Map<String, String> info = new HashMap<String, String>();
	PeopleData peopleData = new PeopleData(user);
	info.put("userName", user.getFullName());
	info.put("deptCode", peopleData.department.getCode());
	info.put("deptName", peopleData.departmentName);
	return info;
    }

    @Override
    public List<KETDqmDTO> findDqmByProject(String pjtNo) throws Exception {
	KETDqmDTO paramObject = new KETDqmDTO();
	paramObject.setPjtno(pjtNo);
	QueryResult qr = getSearchList(paramObject);

	List<KETDqmDTO> list = new ArrayList<KETDqmDTO>();

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    KETDqmHeader ketDqmHeader = (KETDqmHeader) objArr[0];
	    KETDqmRaise ketDqmRasie = (KETDqmRaise) objArr[1];
	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
	    KETDqmClose ketDqmClose = (KETDqmClose) objArr[2];
	    KETDqmDTO gridDto = new KETDqmDTO();

	    gridDto.setIssueName(ketDqmRasie.getIssueName());// 문제점구분
	    gridDto.setOccurStepName(ketDqmRasie.getOccurStepName());// 발생단계
	    gridDto.setOccurPointName(ketDqmRasie.getOccurPointName());// 발생시점
	    gridDto.setProblem(ketDqmHeader.getProblem());// 문제내용
	    gridDto.setDqmStateName(ketDqmHeader.getDqmStateName());// 상태
	    gridDto.setDqmStateCode(ketDqmHeader.getDqmStateCode());// 상태
	    gridDto.setOccurDate(DateUtil.getDateString(ketDqmRasie.getOccurDate(), "date"));// 발생일
	    gridDto.setRequestDate(DateUtil.getDateString(ketDqmRasie.getRequestDate(), "date"));// 완료목표일
	    gridDto.setRaiseCreateUserDept(ketDqmRasie.getCreatorDeptName());// 작성부서
	    gridDto.setActionUserName(ketDqmRasie.getActionUserName());// 검토자
	    gridDto.setActionDeptName(ketDqmRasie.getActionUserDeptName());// 검토부서
	    gridDto.setOid(CommonUtil.getOIDString(ketDqmHeader));
	    list.add(gridDto);
	}

	return list;

    }
}
