/**
 * @(#)	ProjectIssueHelper.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.project.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTUser;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.StringSearch;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.IssueType;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectIssueAnswer;
import e3ps.project.ProjectMemberLink;
import e3ps.project.QuestionAnswerLink;
import ext.ket.shared.log.Kogger;

public class ProjectIssueHelper {
    public static final int CONTINUE = 0;
    public static final int COMPLETE = 1;

    public static final ProjectIssueHelper manager = new ProjectIssueHelper();

    private ProjectIssueHelper() {
    }

    public QuerySpec getSearch(Hashtable hash) {
	try {
	    QuerySpec qs = new QuerySpec();

	    Class target = ProjectIssue.class;
	    int idx_target = qs.addClassList(target, true);
	    int idx_project = qs.addClassList(E3PSProject.class, false);

	    ClassAttribute ca0 = new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id");
	    ClassAttribute ca1 = new ClassAttribute(target, "projectReference.key.id");
	    SearchCondition sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_project, idx_target }, 0);
	    try {
		sc0.setOuterJoin(0);
	    } catch (WTPropertyVetoException e) {
		Kogger.error(getClass(), e);
	    }
	    qs.appendWhere(sc0, new int[] { idx_project, idx_target });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { idx_project });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { idx_project });

	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	    String command = null;
	    if (hash != null) {
		command = (String) hash.get("command");
	    }

	    if (StringUtil.checkString(command) && "search".equals(command)) {
		String myIssueList = (String) hash.get("myIssueList");
		String myAnswerList = (String) hash.get("myAnswerList");

		if ((StringUtil.checkString(myIssueList)) && (StringUtil.checkString(myAnswerList))) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    // MY 제기이슈 || MY 담당이슈
		    qs.appendOpenParen();
		    qs.appendWhere(new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendOr();
		    qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendCloseParen();
		} else if ((StringUtil.checkString(myIssueList)) && ("all".equals(myIssueList))) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    // MY 제기이슈 || MY 담당이슈
		    qs.appendOpenParen();
		    qs.appendWhere(new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendOr();
		    qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendCloseParen();
		} else {
		    if (StringUtil.checkString(myIssueList)) {
			// MY 제기이슈
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendWhere(
			        new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			        new int[] { idx_target });
		    } else if (StringUtil.checkString(myAnswerList)) {
			// MY 담당이슈
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendWhere(
			        new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			        new int[] { idx_target });
		    }
		}

		// 이슈 완료여부
		String category = (String) hash.get("category");
		String tmp = null;
		boolean b_tmp = false;

		if (category.equals("pre")) { // 미완료
		    tmp = SearchCondition.IS_FALSE;
		    b_tmp = false;
		} else if (category.equals("post")) { // 완료
		    tmp = SearchCondition.IS_TRUE;
		    b_tmp = true;
		}

		if (StringUtil.checkString(category)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "isDone", tmp, b_tmp), new int[] { idx_target });
		}

		// 제기 일자(시작 ~ 끝)
		String startCreateDate = (String) hash.get("startCreateDate");
		String endCreateDate = (String) hash.get("endCreateDate");

		if (startCreateDate != null && startCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(startCreateDate, new ParsePosition(0))
			    .getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			    convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}
		if (endCreateDate != null && endCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(endCreateDate + ":23-59-59",
			    new ParsePosition(0)).getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}

	    } else {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendOpenParen();

		// manager [manager.key.id]
		qs.appendWhere(new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
		        new int[] { idx_target });

		qs.appendOr();

		// owner [owner.key.id]
		qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
		        new int[] { idx_target });

		qs.appendCloseParen();
	    }

	    if (hash != null) {
		int sortIdx = 0;
		String sort = (String) hash.get("sortKey");
		String isDesc = (String) hash.get("dsc");
		// Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);

		if ((sort != null) && (sort.trim().length() > 0)) {
		    if ((isDesc == null) || (isDesc.trim().length() == 0)) {
			isDesc = "FALSE";
		    }

		    if (sort.equals(E3PSProject.PJT_NO) || sort.equals(E3PSProject.PJT_NAME)) {
			SearchUtil.setOrderBy(qs, E3PSProject.class, idx_project, sort, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    } else if (sort.equals("owner") || sort.equals("manager")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			int idx_user = qs.appendClassList(WTUser.class, false);

			ClassAttribute ca = null;
			ClassAttribute ca2 = null;

			ca = new ClassAttribute(ProjectIssue.class, sort + ".key.id");
			ca2 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
			SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
			sc2.setFromIndicies(new int[] { idx_target, idx_user }, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[] { idx_target, idx_user });

			SearchUtil.setOrderBy(qs, WTUser.class, idx_user, WTUser.NAME, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    } else {
			SearchUtil.setOrderBy(qs, target, idx_target, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		    }
		}
	    } else
		SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    // Kogger.debug(getClass(), "getSearchIssue[QuerySpec]>>>> "+qs);

	    return qs;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    public QueryResult getSearchIssue(Hashtable hash) {
	try {
	    QuerySpec qs = getSearch(hash);

	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    public QuerySpec getSearchTotal(Hashtable hash) {
	try {
	    QuerySpec qs = new QuerySpec();

	    Class target = ProjectIssue.class;
	    int idx_target = qs.addClassList(target, true);
	    int idx_project = qs.addClassList(E3PSProject.class, false);

	    ClassAttribute ca0 = new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id");
	    ClassAttribute ca1 = new ClassAttribute(target, "projectReference.key.id");
	    SearchCondition sc0 = new SearchCondition(ca0, "=", ca1);
	    sc0.setFromIndicies(new int[] { idx_project, idx_target }, 0);
	    sc0.setOuterJoin(0);
	    qs.appendWhere(sc0, new int[] { idx_project, idx_target });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { idx_project });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { idx_project });

	    String command = (String) hash.get("command");
	    if (StringUtil.checkString(command) && "search".equals(command)) {
		// 프로젝트 번호
		String projectNo = (String) hash.get("projectNo");
		if (projectNo != null && projectNo.length() > 0) {
		    StringSearch stringsearch = new StringSearch("pjtNo");
		    stringsearch.setValue(projectNo.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(E3PSProject.class), new int[] { idx_project });
		}

		// 이슈 완료여부
		String category = (String) hash.get("category");
		String tmp = null;
		boolean b_tmp = false;

		if (category.equals("pre")) { // 미완료
		    tmp = SearchCondition.IS_FALSE;
		    b_tmp = false;
		} else if (category.equals("post")) { // 완료
		    tmp = SearchCondition.IS_TRUE;
		    b_tmp = true;
		}

		if (StringUtil.checkString(category)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "isDone", tmp, b_tmp), new int[] { idx_target });
		}

		// 프로젝트 명
		String projectName = (String) hash.get("projectName");
		if (projectName != null && projectName.length() > 0) {
		    StringSearch stringsearch = new StringSearch("pjtName");
		    stringsearch.setValue(projectName.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(E3PSProject.class), new int[] { idx_project });
		}

		String manager = (String) hash.get("manager");
		if (StringUtil.checkString(manager)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(manager)),
			    new int[] { idx_target });
		}

		// 제목
		String subject = (String) hash.get("subject");
		if (subject != null && subject.length() > 0) {
		    StringSearch stringsearch = new StringSearch("subject");
		    stringsearch.setValue(subject.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
		}

		String owner = (String) hash.get("owner");
		if (StringUtil.checkString(owner)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(owner)),
			    new int[] { idx_target });
		}

		// 제기 일자(시작 ~ 끝)
		String startCreateDate = (String) hash.get("startCreateDate");
		String endCreateDate = (String) hash.get("endCreateDate");

		if (startCreateDate != null && startCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(startCreateDate, new ParsePosition(0))
			    .getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			    convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}
		if (endCreateDate != null && endCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(endCreateDate + ":23-59-59",
			    new ParsePosition(0)).getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}

		String type = (String) hash.get("type");
		if (type != null && type.length() > 0) {
		    e3ps.project.IssueType it = IssueType.toIssueType(type);
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "issueType", SearchCondition.EQUAL, it), new int[] { idx_target });
		}

		String urgency = (String) hash.get("urgency");
		if (urgency != null && urgency.length() > 0) {
		    StringSearch stringsearch = new StringSearch("urgency");
		    stringsearch.setValue(urgency.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
		}

		String importance = (String) hash.get("importance");
		if (importance != null && importance.length() > 0) {
		    StringSearch stringsearch = new StringSearch("importance");
		    stringsearch.setValue(importance.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
		}

		String orgName = (String) hash.get("orgName");
		if (orgName != null && orgName.length() > 0) {

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    int idxUser = qs.appendClassList(WTUser.class, false);
		    int idxPeo = qs.appendClassList(People.class, false);
		    int idxDep = qs.appendClassList(Department.class, false);

		    ClassAttribute ca = new ClassAttribute(target, "manager.key.id");
		    ClassAttribute ca2 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");

		    SearchCondition sc = new SearchCondition(ca, "=", ca2);

		    qs.appendWhere(sc, new int[] { idx_target, idxUser });
		    qs.appendAnd();

		    ClassAttribute ca3 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
		    ClassAttribute ca4 = new ClassAttribute(People.class, "userReference.key.id");

		    SearchCondition sc3 = new SearchCondition(ca3, "=", ca4);

		    qs.appendWhere(sc3, new int[] { idxUser, idxPeo });
		    qs.appendAnd();

		    ClassAttribute ca5 = new ClassAttribute(People.class, "departmentReference.key.id");
		    ClassAttribute ca6 = new ClassAttribute(Department.class, "thePersistInfo.theObjectIdentifier.id");

		    SearchCondition sc4 = new SearchCondition(ca5, "=", ca6);

		    qs.appendWhere(sc4, new int[] { idxPeo, idxDep });
		    qs.appendAnd();

		    SearchCondition sc5 = new SearchCondition(Department.class, "name", "=", orgName);

		    qs.appendWhere(sc5, new int[] { idxDep });

		}

	    }

	    if (hash != null) {
		int sortIdx = 0;
		String sort = (String) hash.get("sortKey");
		String isDesc = (String) hash.get("dsc");
		// Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);

		if ((sort != null) && (sort.trim().length() > 0)) {
		    if ((isDesc == null) || (isDesc.trim().length() == 0)) {
			isDesc = "FALSE";
		    }

		    if (sort.equals(E3PSProject.PJT_NO) || sort.equals(E3PSProject.PJT_NAME)) {
			SearchUtil.setOrderBy(qs, E3PSProject.class, idx_project, sort, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    } else if (sort.equals("owner") || sort.equals("manager")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			int idx_user = qs.appendClassList(WTUser.class, false);

			ClassAttribute ca = null;
			ClassAttribute ca2 = null;

			ca = new ClassAttribute(ProjectIssue.class, sort + ".key.id");
			ca2 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
			SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
			sc2.setFromIndicies(new int[] { idx_target, idx_user }, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[] { idx_target, idx_user });

			SearchUtil.setOrderBy(qs, WTUser.class, idx_user, WTUser.NAME, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    } else {
			SearchUtil.setOrderBy(qs, target, idx_target, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		    }

		}
	    } else
		SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    return qs;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    public QuerySpec getSearchProjectView(Hashtable hash) {
	try {
	    QuerySpec qs = new QuerySpec();

	    Class target = ProjectIssue.class;
	    int idx_target = qs.addClassList(target, true);

	    String projectOid = (String) hash.get("projectOid");
	    if (projectOid != null && projectOid.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(
		        new SearchCondition(target, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(projectOid)), new int[] { idx_target });
	    }

	    String command = (String) hash.get("command");
	    if (StringUtil.checkString(command) && "search".equals(command)) {
		// 이슈 완료여부
		String category = (String) hash.get("category");
		String tmp = null;
		boolean b_tmp = false;

		if (category.equals("pre")) { // 미완료
		    tmp = SearchCondition.IS_FALSE;
		    b_tmp = false;
		} else if (category.equals("post")) { // 완료
		    tmp = SearchCondition.IS_TRUE;
		    b_tmp = true;
		}

		if (StringUtil.checkString(category)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "isDone", tmp, b_tmp), new int[] { idx_target });
		}

		String manager = (String) hash.get("manager");
		if (StringUtil.checkString(manager)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(manager)),
			    new int[] { idx_target });
		}

		// 제목
		String subject = (String) hash.get("subject");
		if (subject != null && subject.length() > 0) {
		    StringSearch stringsearch = new StringSearch("subject");
		    stringsearch.setValue(subject.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
		}

		String owner = (String) hash.get("owner");
		if (StringUtil.checkString(owner)) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(owner)),
			    new int[] { idx_target });
		}

		// 제기 일자(시작 ~ 끝)
		String startCreateDate = (String) hash.get("startCreateDate");
		String endCreateDate = (String) hash.get("endCreateDate");

		if (startCreateDate != null && startCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(startCreateDate, new ParsePosition(0))
			    .getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
			    convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}
		if (endCreateDate != null && endCreateDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(endCreateDate + ":23-59-59",
			    new ParsePosition(0)).getTime()));

		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    SearchCondition sc = new SearchCondition(target, ProjectIssue.CREATE_DATE, SearchCondition.LESS_THAN, convertDate);
		    qs.appendWhere(sc, new int[] { idx_target });
		}

	    }

	    if (hash != null) {
		int sortIdx = 0;
		String sort = (String) hash.get("sortKey");
		String isDesc = (String) hash.get("dsc");
		// Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);

		if ((sort != null) && (sort.trim().length() > 0)) {
		    if ((isDesc == null) || (isDesc.trim().length() == 0)) {
			isDesc = "FALSE";
		    }

		    if (sort.equals("owner") || sort.equals("manager")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			int idx_user = qs.appendClassList(WTUser.class, false);

			ClassAttribute ca = null;
			ClassAttribute ca2 = null;

			ca = new ClassAttribute(ProjectIssue.class, sort + ".key.id");
			ca2 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
			SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
			sc2.setFromIndicies(new int[] { idx_target, idx_user }, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[] { idx_target, idx_user });

			SearchUtil.setOrderBy(qs, WTUser.class, idx_user, WTUser.NAME, "m_Sort" + sortIdx++,
			        "TRUE".equals(isDesc.toUpperCase()));
		    } else {
			SearchUtil.setOrderBy(qs, target, idx_target, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		    }

		}
	    } else
		SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    return qs;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

	return null;
    }

    public QueryResult getProjectIssue(E3PSProject project) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectIssue.class;
	    int mainClassPos = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(project);
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue), mainClassPos);
	    // qs.appendAnd();
	    // qs.appendWhere(new SearchCondition(mainClass, "taskReference.key.id", SearchCondition.EQUAL, (long) 0), mainClassPos);
	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getProjectIssue(Long[] oids) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectIssue.class;
	    int mainClassPos = qs.addClassList(mainClass, true);
	    // long oidValue = CommonUtil.getOIDLongValue(project);
	    // qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue), mainClassPos);

	    // Long[] oids = {oidValue, reviewOid};
	    qs.appendWhere(new SearchCondition(new ClassAttribute(ProjectIssue.class, "projectReference.key.id"), SearchCondition.IN,
		    new ArrayExpression(oids)), new int[] { mainClassPos });

	    // qs.appendAnd();
	    // qs.appendWhere(new SearchCondition(mainClass, "taskReference.key.id", SearchCondition.EQUAL, (long) 0), mainClassPos);
	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskIssue(E3PSTask task) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class mainClass = ProjectIssue.class;
	    int mainClassPos = qs.addClassList(mainClass, true);
	    long oidValue = CommonUtil.getOIDLongValue(task.getProject());
	    qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", SearchCondition.EQUAL, oidValue), mainClassPos);
	    qs.appendAnd();
	    oidValue = CommonUtil.getOIDLongValue(task);
	    qs.appendWhere(new SearchCondition(mainClass, "taskReference.key.id", SearchCondition.EQUAL, oidValue), mainClassPos);

	    // Kogger.debug(getClass(), "getTaskIssue<<<< "+qs);

	    return PersistenceHelper.manager.find(qs);
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public ProjectIssueAnswer createProjectIssueAnswer(ProjectIssue _issue, ProjectIssueAnswer _issueAnswer) {
	try {
	    // _issueAnswer = (ProjectIssueAnswer)PersistenceHelper.manager.save(_issueAnswer);

	    QuestionAnswerLink link = QuestionAnswerLink.newQuestionAnswerLink(_issueAnswer, _issue);
	    PersistenceHelper.manager.save(link);
	    // ProjectHelper.manager.projectSendMail(_issue, "issueAnswer");
	    // 이슈 답변 등록 메일 발송
	    // WTUser from = (WTUser) _issue.getManager().getPrincipal();
	    // List<WTUser> to = new ArrayList<WTUser>();
	    // to.add((WTUser) _issue.getOwner().getPrincipal());
	    // KETMailHelper.service.sendFormMail("08018", "NoticeMailLine3.html", _issue, from, to);

	    return _issueAnswer;
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return _issueAnswer;
    }

    public ProjectIssue createProjectIssue(ProjectIssue _issue) {
	try {
	    _issue = (ProjectIssue) PersistenceHelper.manager.save(_issue);
	    // ProjectHelper.manager.projectSendMail(_issue, "issue");
	    // 이슈 등록 메일 공지 발송
	    // WTUser from = (WTUser) _issue.getOwner().getPrincipal();
	    // List<WTUser> to = getIssueMailingList(_issue);
	    // KETMailHelper.service.sendFormMail("08017", "NoticeMailLine3.html", _issue, from, to);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return _issue;
    }

    public ArrayList<WTUser> getIssueMailingList(ProjectIssue issue) throws Exception {

	ArrayList<WTUser> list = new ArrayList<WTUser>();

	ProjectIssueData data = new ProjectIssueData(issue);
	WTUser manager = (WTUser) issue.getManager().getPrincipal();
	list.add(manager);
	E3PSProject project = issue.getProject();
	if (project != null) {
	    // PM
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    if (!list.contains(pm))
		list.add(pm);
	    // CFT
	    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) obj[0];
		WTUser member = link.getMember();
		if (!list.contains(member))
		    list.add(member);
	    }
	    qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.MEMBER);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) obj[0];
		WTUser member = link.getMember();
		if (!list.contains(member))
		    list.add(member);
	    }
	    qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.TASK_MEMBER);
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) obj[0];
		WTUser member = link.getMember();
		if (!list.contains(member))
		    list.add(member);
	    }
	}

	if (data.importance != null) {
	    // data.urgency
	    if ("상".equals(data.importance) && "상".equals(data.urgency)) {
		// 임원
		WTGroup group = null;
		if (CommonUtil.isMember("전자사업부"))
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
		else
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = OrganizationServicesHelper.manager.members(group);
		while (enumeration.hasMoreElements()) {
		    WTUser executive = (WTUser) enumeration.nextElement();
		    if (!list.contains(executive))
			list.add(executive);
		}
		// 담당부서 팀장
		PeopleData peopleData = new PeopleData(manager);
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(peopleData.department);
		if (chiefUser != null) {
		    if (!list.contains(chiefUser))
			list.add(chiefUser);
		}
	    } else if ("상".equals(data.importance) && "중".equals(data.urgency)) {
		// 임원
		WTGroup group = null;
		if (CommonUtil.isMember("전자사업부"))
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
		else
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = OrganizationServicesHelper.manager.members(group);
		while (enumeration.hasMoreElements()) {
		    WTUser executive = (WTUser) enumeration.nextElement();
		    if (!list.contains(executive))
			list.add(executive);
		}
	    } else if ("상".equals(data.importance) && "하".equals(data.urgency)) {
		// 담당부서 팀장
		PeopleData peopleData = new PeopleData(manager);
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(peopleData.department);
		if (chiefUser != null) {
		    if (!list.contains(chiefUser))
			list.add(chiefUser);
		}
	    } else if ("중".equals(data.importance) && "상".equals(data.urgency)) {
		// 임원
		WTGroup group = null;
		if (CommonUtil.isMember("전자사업부"))
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
		else
		    group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = OrganizationServicesHelper.manager.members(group);
		while (enumeration.hasMoreElements()) {
		    WTUser executive = (WTUser) enumeration.nextElement();
		    if (!list.contains(executive))
			list.add(executive);
		}
		// 담당부서 팀장
		PeopleData peopleData = new PeopleData(manager);
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(peopleData.department);
		if (chiefUser != null) {
		    if (!list.contains(chiefUser))
			list.add(chiefUser);
		}
	    } else if ("중".equals(data.importance) && "중".equals(data.urgency)) {
		// 담당부서 팀장
		PeopleData peopleData = new PeopleData(manager);
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(peopleData.department);
		if (chiefUser != null) {
		    if (!list.contains(chiefUser))
			list.add(chiefUser);
		}
	    } else if ("중".equals(data.importance) && "하".equals(data.urgency)) {
	    } else if ("하".equals(data.importance) && "상".equals(data.urgency)) {
		// 담당부서 팀장
		PeopleData peopleData = new PeopleData(manager);
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(peopleData.department);
		if (chiefUser != null) {
		    if (!list.contains(chiefUser))
			list.add(chiefUser);
		}
	    } else if ("하".equals(data.importance) && "중".equals(data.urgency)) {
	    } else if ("하".equals(data.importance) && "하".equals(data.urgency)) {
	    }
	}

	return list;
    }

    public WTGroup getWTGroupObj(String groupName) throws Exception {
	WTGroup group = null;
	QuerySpec query = null;
	QueryResult qr = null;

	query = new QuerySpec();
	int idx = query.addClassList(WTGroup.class, true);
	query.appendWhere(new SearchCondition(WTGroup.class, "name", "=", groupName), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	Object[] obj = null;
	if (qr.hasMoreElements()) {
	    obj = (Object[]) qr.nextElement();
	    group = (WTGroup) obj[0];
	}

	return group;
    }

    public ProjectIssue modifyProjectIssue(ProjectIssue _issue) {
	try {
	    return (ProjectIssue) PersistenceHelper.manager.modify(_issue);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return _issue;
    }

    public void deleteProjectIssue(String oid) {
	// Kogger.debug(getClass(), "delectProjectIssue!!!!!!!");

	try {
	    Object obj = CommonUtil.getObject(oid);
	    ProjectIssue issue = null;
	    ProjectIssueAnswer answer = null;
	    if (obj instanceof ProjectIssue) {
		issue = (ProjectIssue) obj;
		Vector answerVec = getIssueAnswer(issue);
		for (int i = 0; i < answerVec.size(); i++) {
		    ProjectIssueAnswerData answerData = (ProjectIssueAnswerData) answerVec.get(i);
		    // Kogger.debug(getClass(), "ProjectIssueAnswer ["+CommonUtil.getOIDString(answerData.answer)+"] Delete!!!!");
		    PersistenceHelper.manager.delete(answerData.answer);
		}
		// Kogger.debug(getClass(), "ProjectIssue ["+CommonUtil.getOIDString(issue)+"] Delete!!!");
		PersistenceHelper.manager.delete(issue);
	    } else if (obj instanceof ProjectIssueAnswer) {
		answer = (ProjectIssueAnswer) obj;
		issue = ProjectIssueHelper.manager.getIssue(answer);

		// Kogger.debug(getClass(), "ProjectIssueAnswer ["+CommonUtil.getOIDString(answer)+"] Delete!!!!");
		PersistenceHelper.manager.delete(answer);
		// Kogger.debug(getClass(), "ProjectIssue ["+CommonUtil.getOIDString(issue)+"] Delete!!!");
		PersistenceHelper.manager.modify(issue);
	    }

	    ProjectHelper.projectSendMail(issue, "issueDelete"); // 이슈를 삭제 하면 메일 발송
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void deleteProjectIssue(E3PSProject project) {
	QueryResult qr = getProjectIssue(project);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    ProjectIssue issue = (ProjectIssue) objArr[0];
	    deleteProjectIssue(CommonUtil.getOIDString(issue));
	}
    }

    public void deleteTaskIssue(E3PSTask task) {
	QueryResult qr = getTaskIssue(task);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    ProjectIssue issue = (ProjectIssue) objArr[0];
	    deleteProjectIssue(CommonUtil.getOIDString(issue));
	}
    }

    public Vector getIssueAnswer(ProjectIssue issue) {
	Vector returnVec = new Vector();

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(issue, "answer", QuestionAnswerLink.class);
	    // Kogger.debug(getClass(), "getIssueAnswer>>> "+qr.size());

	    while (qr.hasMoreElements()) {
		ProjectIssueAnswer answer = (ProjectIssueAnswer) qr.nextElement();
		ProjectIssueAnswerData data = new ProjectIssueAnswerData(answer);
		returnVec.add(data);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return returnVec;
    }

    public Vector getIssueAnswerReference(ProjectIssueAnswer answer) {
	Vector returnVec = new Vector();
	QuerySpec qs = null;
	QueryResult qr = null;
	try {
	    qs = new QuerySpec();
	    long idLong = CommonUtil.getOIDLongValue(answer);
	    Class target = ProjectIssueAnswer.class;
	    int idx = qs.appendClassList(target, true);
	    qs.appendWhere(new SearchCondition(target, "dependencyAnswerReference.key.id", "=", idLong), new int[] { idx });
	    qr = PersistenceHelper.manager.find(qs);
	    Object[] obj = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		ProjectIssueAnswer refanswer = (ProjectIssueAnswer) obj[0];
		ProjectIssueAnswerData data = new ProjectIssueAnswerData(refanswer);
		returnVec.add(data);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return returnVec;
    }

    public ProjectIssue getIssue(ProjectIssueAnswer answer) {
	ProjectIssue issue = null;
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(answer, "question", QuestionAnswerLink.class);
	    // Kogger.debug(getClass(), "getIssue>>> "+qr.size());

	    while (qr.hasMoreElements()) {
		issue = (ProjectIssue) qr.nextElement();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return issue;
    }

    public String getState(int state) {
	switch (state) {
	case ProjectIssueHelper.CONTINUE: // '\0'
	    return "진행";
	case ProjectIssueHelper.COMPLETE: // '\001'
	    return "완료";
	}
	return "";
    }

    public Vector getIssueLink(ProjectIssue issue, ProjectIssueAnswer answer) {
	Vector vec = null;
	QuerySpec qs = null;
	QueryResult qr = null;

	try {
	    qs = new QuerySpec();

	    Class target = QuestionAnswerLink.class;
	    int idx_target = qs.addClassList(target, true);

	    // issue [roleBObjectRef.key.id]
	    SearchUtil.appendEQUAL(qs, target, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(issue), idx_target);
	    // answer [roleAObjectRef.key.id]
	    SearchUtil.appendEQUAL(qs, target, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(issue), idx_target);

	    qr = PersistenceHelper.manager.find(qs);
	    vec = new Vector();
	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		QuestionAnswerLink link = (QuestionAnswerLink) obj[0];

		vec.add(link);
	    }

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return vec;
    }

    public static void excelDown(Hashtable map, OutputStream out) throws Exception {

	// Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "ProjectIssueList_" + formatter.format(date) + ".xls";

	// WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableWorkbook workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));
	WritableSheet sheet = workbook.createSheet("ProjectIssueList", 1);
	WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	int row = 0;
	int column = 0;

	String titles[] = new String[] { "No", "Project No", "Project Name", "구분", "제목", "긴급도", "중요도", "제기자", "제기일자", "담당자", "해결방안등록일자",
	        "완료일자", "완료여부" };

	for (int i = 0; i < titles.length; i++) {
	    sheet.addCell(new Label(i, row, titles[i], titleformat));
	}
	QuerySpec qs = ProjectIssueHelper.manager.getSearchTotal(map);

	QueryResult rs = PersistenceHelper.manager.find(qs);

	boolean isCheck = false;

	while (rs.hasMoreElements()) {
	    ++row;
	    int columnIndex = 0;

	    Object[] o = (Object[]) rs.nextElement();
	    ProjectIssue issue = (ProjectIssue) o[0];
	    ProjectIssueData data = new ProjectIssueData(issue);

	    String answerdate = null;
	    Vector answerVec = ProjectIssueHelper.manager.getIssueAnswer(issue);
	    for (int i = 0; i < answerVec.size(); i++) {
		ProjectIssueAnswerData answerData = (ProjectIssueAnswerData) answerVec.get(i);
		answerdate = DateUtil.getDateString(answerData.answerDate, "date");
	    }

	    String pjtNoPrint = data.project.getPjtNo();
	    String pjtNamePrint = data.project.getPjtName();
	    String issueTypePrint = data.issueType;
	    String subjectPrint = data.subject;
	    String urgencyPrint = data.urgency;
	    String importancePrint = data.importance;

	    String questionUserPrint = data.questionUser.name;
	    String questionDatePrint = DateUtil.getDateString(data.questionDate, "d");
	    String managerUserPrint = data.managerUser.name;
	    String isDonePrint = issue.isIsDone() ? "완료" : data.stateProgress2;
	    String endDate = DateUtil.getDateString(data.planDoneDate, "d");

	    /*
	     * Kogger.debug(getClass(), "no=="+pjtNoPrint); Kogger.debug(getClass(), "name=="+pjtNamePrint); Kogger.debug(getClass(),
	     * "구분=="+issueTypePrint); Kogger.debug(getClass(), "제목=="+subjectPrint); Kogger.debug(getClass(), "긴급도=="+urgencyPrint);
	     * Kogger.debug(getClass(), "중요도=="+importancePrint); Kogger.debug(getClass(), "제기자=="+questionUserPrint);
	     * Kogger.debug(getClass(), "제기일자=="+questionDatePrint); Kogger.debug(getClass(), "담당자=="+managerUserPrint);
	     * Kogger.debug(getClass(), "완료여부=="+isDonePrint);
	     */

	    sheet.addCell(new Label(columnIndex++, row, Integer.toString(row)));
	    sheet.addCell(new Label(columnIndex++, row, pjtNoPrint));
	    sheet.addCell(new Label(columnIndex++, row, pjtNamePrint));
	    sheet.addCell(new Label(columnIndex++, row, issueTypePrint));
	    sheet.addCell(new Label(columnIndex++, row, subjectPrint));
	    sheet.addCell(new Label(columnIndex++, row, urgencyPrint));
	    sheet.addCell(new Label(columnIndex++, row, importancePrint));
	    sheet.addCell(new Label(columnIndex++, row, questionUserPrint));
	    sheet.addCell(new Label(columnIndex++, row, questionDatePrint));
	    sheet.addCell(new Label(columnIndex++, row, managerUserPrint));
	    sheet.addCell(new Label(columnIndex++, row, answerdate));
	    sheet.addCell(new Label(columnIndex++, row, endDate));
	    sheet.addCell(new Label(columnIndex++, row, isDonePrint));

	}
	workbook.write();
	workbook.close();

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), "");

	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    fin.read(b);
	    out.write(b, 0, ifilesize);

	    out.flush();
	    out.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(ProjectIssueHelper.class, wte);
	}
    }

    private static WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    if (color != null)
		format.setBackground(color);

	    format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(ProjectIssueHelper.class, e);
	}
	return format;
    }

}
