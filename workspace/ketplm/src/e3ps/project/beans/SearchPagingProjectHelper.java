package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.E3PSClassTableExpression;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.log.Kogger;

public class SearchPagingProjectHelper implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    // static long cabinetOid = -1;
    // private static final String DOC_MASTER_ID = "docMasterId";

    public static QueryResult find(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.project.beans.SearchPagingProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	QueryResult qr = null;
	try {
	    qr = PersistenceHelper.manager.find(getE3PSProjectQuerySpec(map));
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return qr;
    }

    public static QueryResult find(HashMap<String, String> map, List<Map<String, String>> conditionList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, List.class };
	    Object args[] = new Object[] { map, conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.project.beans.SearchPagingProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	QueryResult qr = null;
	try {
	    qr = PersistenceHelper.manager.find(getE3PSProjectQuerySpec(map, conditionList));
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return qr;
    }

    public static QueryResult findMy(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("findMy", "e3ps.project.beans.SearchPagingProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	QueryResult qr = null;
	try {
	    qr = PersistenceHelper.manager.find(getMyE3PSProjectQuerySpec(map));
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return qr;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // / openPagingSession
    // ////////////////////////////////////////////////////////////////////////////

    public static PagingQueryResult openPagingSession(HashMap map, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class, int.class };
	    Object args[] = new Object[] { map, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession", "e3ps.project.beans.SearchPagingProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    QuerySpec qs = getE3PSProjectQuerySpec(map);
	    result = openPagingSession(qs, start, size);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // openPagingSession2
    // Sang Min, Kim
    // smkim@e3ps.com
    // 추가 My 프로젝트 목록
    // ////////////////////////////////////////////////////////////////////////////

    public static PagingQueryResult openPagingSession2(HashMap map, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class, int.class };
	    Object args[] = new Object[] { map, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession2",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    StatementSpec qs = getMyE3PSProjectQuerySpec(map);
	    result = openPagingSession(qs, start, size);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // openPagingSession3
    // Sang Min, Kim
    // smkim@e3ps.com
    // 추가 My 태스크 목록
    // ////////////////////////////////////////////////////////////////////////////

    public static PagingQueryResult openPagingSession3(HashMap map, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, int.class, int.class };
	    Object args[] = new Object[] { map, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession3",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    QuerySpec qs = getMyTaskQuerySpec(map);
	    result = openPagingSession(qs, start, size);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult openPagingSession(StatementSpec spec, int start, int size) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { StatementSpec.class, int.class, int.class };
	    Object args[] = new Object[] { spec, new Integer(start), new Integer(size) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("openPagingSession", "e3ps.project.beans.SearchPagingProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.openPagingSession(start, size, spec);

	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    public static PagingQueryResult fetchPagingSession(int start, int size, long sessionId) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { int.class, int.class, long.class };
	    Object args[] = new Object[] { new Integer(start), new Integer(size), new Long(sessionId) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("fetchPagingSession",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (PagingQueryResult) obj;
	}
	PagingQueryResult result = null;
	try {
	    result = PagingSessionHelper.fetchPagingSession(start, size, sessionId);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getE3PSProjectQuerySpec
    // Sang Min, Kim
    // smkim@e3ps.com
    // 수정
    // ////////////////////////////////////////////////////////////////////////////

    public static QuerySpec getE3PSProjectQuerySpec(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getE3PSProjectQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec mainSpec = null;
	try {

	    // Attribute Setting
	    String cmd = (String) map.get("command");
	    String initType = (String) map.get("initType");
	    String pjtType = (String) map.get("pjtType");
	    if (cmd == null)
		cmd = "";
	    mainSpec = new QuerySpec();
	    Class main_target = E3PSProject.class;
	    /*
	     * if (pjtType.equals("3")) { main_target = MoldProject.class; }
	     */
	    int main_idx = mainSpec.addClassList(main_target, true);

	    // ///////////////////////////////////////////////////////////////////
	    // Sub Query Start mainSpec
	    // ///////////////////////////////////////////////////////////////////

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }

	    QuerySpec spec = null;
	    spec = new QuerySpec();

	    if (!spec.isAdvancedQueryEnabled()) {
		spec.setAdvancedQueryEnabled(true);
	    }

	    Class target = E3PSProject.class;
	    // pjtType(프로젝트종류 - 검토, 제품, 금형)
	    if (pjtType.equals("0") || pjtType.equals("1")) {
		target = ReviewProject.class;
	    }

	    if (pjtType.equals("2") || pjtType.equals("4")) {
		target = ProductProject.class;
	    }

	    if (pjtType.equals("3")) {
		target = MoldProject.class;
	    }

	    int idx_target = spec.addClassList(target, false);

	    spec.setDistinct(true);

	    ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("projectId");
	    spec.appendSelect(ca, true);

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    long lperistable = CommonUtil.getOIDLongValue(wtuser);

	    /*
	     * Department dept = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(wtuser); ArrayList childList = null; if(dept
	     * != null) { childList = e3ps.groupware.company.DepartmentHelper.manager.getChildDeptTree(dept); } if(childList == null) {
	     * childList = new ArrayList(); } childList.add(0, dept);
	     */

	    String pjtNo = (String) map.get("pjtNo"); // pjtNo(프로젝트 NO)
	    Kogger.debug(SearchPagingProjectHelper.class, "ptjNo 확인 == " + pjtNo);
	    String pjtName = (String) map.get("pjtName"); // pjtName(프로젝트 명)
	    if (StringUtil.checkString(cmd)) {
		if (cmd.equalsIgnoreCase("search") || cmd.equalsIgnoreCase("select")) {
		    boolean addedCondition = false;
		    // 프로젝트 NO
		    if (StringUtil.checkString(pjtNo)) {
			KETQueryUtil.setQuerySpecForMultiSearch(spec, target, idx_target, E3PSProject.PJT_NO, pjtNo, true);

			// pjtNo = StringUtil.changeString(pjtNo, "*", "%");
			// setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NO, pjtNo.trim() );

			addedCondition = true;
		    }
		    // 프로젝트 명
		    if (StringUtil.checkString(pjtName)) {
			if (addedCondition) {
			    // AND
			    spec.appendAnd();

			}

			KETQueryUtil.setQuerySpecForMultiSearch(spec, target, idx_target, E3PSProject.PJT_NAME, pjtName, true);

			// pjtName = StringUtil.changeString(pjtName, "*", "%");
			// setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NAME, pjtName.trim() );
		    }

		    // 프로젝트 상태
		    /**
		     * 2011 - 05 - 18 프로젝트 상태값(지연)으로 검색 기능 추가....
		     */
		    String pjtState = (String) map.get("pjtState");
		    if (StringUtil.checkString(pjtState)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			/*
		         * 진행중 상태이며.. 지연중인것으로 검색...
		         */
			if (pjtState.equals("UNDERREVIEW")) {
			    Hashtable h = ProjectHelper.APPROVALSTATE;
			    Enumeration e = h.keys();
			    spec.appendOpenParen();
			    boolean start = true;
			    while (e.hasMoreElements()) {
				if (!start) {
				    spec.appendOr();
				} else {
				    start = false;
				}
				String key = (String) e.nextElement();
				SearchCondition where = new SearchCondition(target, "state.state", SearchCondition.EQUAL, key);
				spec.appendWhere(where, new int[] { idx_target });
			    }
			    spec.appendCloseParen();
			} else if ("delay".equals(pjtState)) {
			    spec.appendWhere(new SearchCondition(target, "state.state", "=", "PROGRESS"), new int[] { idx_target });
			    spec.appendAnd();
			    spec.appendWhere(new SearchCondition(target, "pjtState", "=", ProjectStateFlag.PROJECT_STATE_DELAY),
				    new int[] { idx_target });
			} else {
			    spec.appendOpenParen();
			    StringTokenizer pjtStateToken = new StringTokenizer(pjtState, ", ");
			    while (pjtStateToken.hasMoreTokens()) {

				String state = pjtStateToken.nextToken();
				if (!state.equals("delay")) {
				    spec.appendWhere(new SearchCondition(target, "state.state", SearchCondition.EQUAL, state),
					    new int[] { idx_target });
				} else if (state.equals("delay")) {
				    spec.appendOpenParen();
				    spec.appendWhere(new SearchCondition(target, "state.state", "=", "PROGRESS"), new int[] { idx_target });
				    spec.appendAnd();
				    spec.appendWhere(new SearchCondition(target, "pjtState", "=", ProjectStateFlag.PROJECT_STATE_DELAY),
					    new int[] { idx_target });
				    spec.appendCloseParen();
				}
				if (pjtStateToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();

			    // SearchCondition where = new SearchCondition(target, "state.state" , SearchCondition.EQUAL, pjtState);
			    // spec.appendWhere(where, new int[]{idx_target});
			}

		    }

		    // PM
		    String setPm = (String) map.get("setPm");
		    if (StringUtil.checkString(setPm)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			Class<ProjectMemberLink> prjectMember = ProjectMemberLink.class;
			int idx_Member = spec.appendClassList(prjectMember, false);

			ClassAttribute ca1 = null;
			ClassAttribute ca2 = null;

			ca1 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
			ca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
			SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			sc.setFromIndicies(new int[] { idx_target, idx_Member }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { idx_target, idx_Member });

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
			        ProjectUserHelper.PM);
			spec.appendWhere(sc, new int[] { idx_Member });

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer setPmToken = new StringTokenizer(setPm, ", ");
			while (setPmToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(setPmToken.nextToken())), new int[] { idx_Member });
			    if (setPmToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// long pmUser = 0;
			// if ( setPm != null && setPm.length() > 0 ) {
			// pmUser = CommonUtil.getOIDLongValue(setPm);
			// }

			// sc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, pmUser);
			// spec.appendWhere(sc , new int[]{idx_Member});
		    }

		    // 개발담당부서 검토
		    String rdevDeptOid = (String) map.get("rdevDeptOid");
		    if (StringUtil.checkString(rdevDeptOid)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer rdevDeptOidToken = new StringTokenizer(rdevDeptOid, ", ");
			while (rdevDeptOidToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(target, "devDeptReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(rdevDeptOidToken.nextToken())), new int[] { idx_target });
			    if (rdevDeptOidToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// if ( spec.getConditionCount() > 0)
			// spec.appendAnd();
			// long deptid = CommonUtil.getOIDLongValue(rdevDeptOid);
			// spec.appendWhere(new SearchCondition(target, "devDeptReference.key.id","=", deptid), new int[] {idx_target} );
		    }

		    // 개발담당부서 제품
		    String pdevDeptOid = (String) map.get("pdevDeptOid");
		    if (StringUtil.checkString(pdevDeptOid)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

			ClassAttribute mca1 = null;
			ClassAttribute mca2 = null;

			mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
			mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
			SearchCondition msc = new SearchCondition(mca1, "=", mca2);
			msc.setFromIndicies(new int[] { idx_target, linkIndex }, 0);
			msc.setOuterJoin(0);
			spec.appendWhere(msc, new int[] { idx_target, linkIndex });

			spec.appendAnd();

			// long deptid = CommonUtil.getOIDLongValue(pdevDeptOid);
			// spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=", deptid), new
			// int[] {linkIndex});

			spec.appendOpenParen();
			StringTokenizer pdevDeptOidToken = new StringTokenizer(pdevDeptOid, ", ");
			while (pdevDeptOidToken.hasMoreTokens()) {
			    spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id",
				    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pdevDeptOidToken.nextToken())),
				    new int[] { linkIndex });
			    if (pdevDeptOidToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();
		    }

		    String developDateType = (String) map.get("developDateType"); // 개발시작일/개발완료일 Combo
		    String planStartStartDate = (String) map.get("planStartStartDate"); // planStartStartDate(계획 시작일자 - 시작)
		    String planStartEndDate = (String) map.get("planStartEndDate"); // planStartEndDate(계획 시작일자 - 끝)
		    String planEndStartDate = (String) map.get("planEndStartDate"); // planEndStartDate(계획 종료일자 - 시작)
		    String planEndEndDate = (String) map.get("planEndEndDate"); // planEndEndDate(계획 종료일자 - 끝)

		    // 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
		    // 개발시작일/개발완료일 Combo가 Null 아니면
		    if (StringUtil.checkString(developDateType)) {

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			Class<ExtendScheduleData> schedule = ExtendScheduleData.class;
			int idx_schedule = spec.appendClassList(schedule, false);

			ClassAttribute ca1 = null;
			ClassAttribute ca2 = null;

			ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
			ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
			SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			sc.setFromIndicies(new int[] { idx_target, idx_schedule }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { idx_target, idx_schedule });

			if (developDateType.equals("DEVELOPDATESTART")) {
			    if (planStartStartDate != null && planStartStartDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
				        new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE,
				        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			    if (planStartEndDate != null && planStartEndDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
				        convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			} else if (developDateType.equals("DEVELOPDATEEND")) {
			    if (planStartStartDate != null && planStartStartDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
				        new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
				        convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			    if (planStartEndDate != null && planStartEndDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			}
		    } else {
			// 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
			if ((planStartStartDate != null && planStartStartDate.length() > 0)
			        || (planStartEndDate != null && planStartEndDate.length() > 0)
			        || (planEndStartDate != null && planEndStartDate.length() > 0)
			        || (planEndEndDate != null && planEndEndDate.length() > 0)) {

			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    Class<ExtendScheduleData> schedule = ExtendScheduleData.class;
			    int idx_schedule = spec.appendClassList(schedule, false);

			    ClassAttribute ca1 = null;
			    ClassAttribute ca2 = null;

			    ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
			    ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
			    SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			    sc.setFromIndicies(new int[] { idx_target, idx_schedule }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { idx_target, idx_schedule });

			    if (planStartStartDate != null && planStartStartDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
				        new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE,
				        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			    if (planStartEndDate != null && planStartEndDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
				        convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			    if (planEndStartDate != null && planEndStartDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
				        new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL,
				        convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			    if (planEndEndDate != null && planEndEndDate.length() > 0) {
				Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				        planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
				spec.appendWhere(sc, new int[] { idx_schedule });
			    }
			}
		    }

		    /*
	             * 기준정보 검색 항목 v 제품구분 v 개발구분 v 조립구분 v Rank 대표차종 v 납입처 v 고객사 제작구분 금형구분 금형분류
	             */

		    // Rank
		    String rank = (String) map.get("RANK");
		    if (rank != null && rank.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer rankToken = new StringTokenizer(rank, ", ");
			while (rankToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(target, "rankReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", rankToken.nextToken()))),
				    new int[] { idx_target });
			    if (rankToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// setNumberCodeQuery(spec, target, idx_target, "rankReference.key.id", "RANK", rank );
		    }

		    // 제품구분
		    String productType = (String) map.get("PRODUCTTYPE");
		    if (productType != null && productType.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer productTypeToken = new StringTokenizer(productType, ", ");
			while (productTypeToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(target, "productTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("PRODUCTTYPE",
				                    productTypeToken.nextToken()))), new int[] { idx_target });
			    if (productTypeToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// setNumberCodeQuery(spec, target, idx_target, "productTypeReference.key.id", "PRODUCTTYPE", PRODUCTTYPE );
		    }

		    // 조립구분
		    String assembledType = (String) map.get("ASSEMBLEDTYPE");
		    if (assembledType != null && assembledType.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer assembledTypeToken = new StringTokenizer(assembledType, ", ");
			while (assembledTypeToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(target, "assembledTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("ASSEMBLEDTYPE",
				                    assembledTypeToken.nextToken()))), new int[] { idx_target });
			    if (assembledTypeToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// setNumberCodeQuery(spec, target, idx_target, "assembledTypeReference.key.id", "ASSEMBLEDTYPE", assembledType );
		    }

		    // 개발구분
		    String developType = (String) map.get("DEVELOPENTTYPE");
		    if (developType != null && developType.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer developTypeToken = new StringTokenizer(developType, ", ");
			while (developTypeToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(target, "developentTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
				                    developTypeToken.nextToken()))), new int[] { idx_target });
			    if (developTypeToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// setNumberCodeQuery(spec, target, idx_target, "developentTypeReference.key.id", "DEVELOPENTTYPE", developType );
		    }

		    // 시리즈
		    String series = (String) map.get("series");
		    if (series != null && series.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer seriesToken = new StringTokenizer(series, ", ");
			while (seriesToken.hasMoreTokens()) {
			    spec.appendWhere(new SearchCondition(target, "series", SearchCondition.EQUAL, seriesToken.nextToken()),
				    new int[] { idx_target });
			    if (seriesToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();
		    }

		    // 방수여부
		    String sealed = (String) map.get("sealed");
		    if (sealed != null && sealed.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer sealedToken = new StringTokenizer(sealed, ", ");
			while (sealedToken.hasMoreTokens()) {
			    spec.appendWhere(new SearchCondition(target, "waterPoof", SearchCondition.EQUAL, sealedToken.nextToken()),
				    new int[] { idx_target });
			    if (sealedToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();
		    }

		    // 대표 차종
		    String carTypeInfo = (String) map.get("carTypeInfo");
		    if (carTypeInfo != null && carTypeInfo.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			// carTypeInfo = StringUtil.changeString(carTypeInfo, "*", "%");

			Class<OEMProjectType> oemLink = OEMProjectType.class;
			int idx_oem = spec.appendClassList(oemLink, false);
			SearchCondition where = null;
			where = new SearchCondition(new ClassAttribute(target, "oemTypeReference.key.id"), "=", new ClassAttribute(oemLink,
			        wt.util.WTAttributeNameIfc.ID_NAME));
			where.setFromIndicies(new int[] { idx_target, idx_oem }, 0);
			where.setOuterJoin(0);
			spec.appendWhere(where, new int[] { idx_target, idx_oem });

			spec.appendAnd();

			// * 검색, 콤마검색
			KETQueryUtil.setQuerySpecForMultiSearch(spec, oemLink, idx_oem, "name", carTypeInfo, true);
			// spec.appendWhere(new SearchCondition(oemLink, "name", SearchCondition.LIKE, carTypeInfo), new int[] {idx_oem} );
		    }

		    // 멀티 기준 정보(최종 사용처)
		    String customerEvent = (String) map.get("CUSTOMEREVENT1");
		    if (customerEvent != null && customerEvent.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			// NumberCode customer = NumberCodeHelper.manager.getNumberCode("CUSTOMEREVENT", customerEvent);
			// if ( customer != null ) {

			Class<ProjectCustomereventLink> linkClass = ProjectCustomereventLink.class;
			int idx_dcLink = spec.appendClassList(linkClass, false);
			SearchCondition where = null;
			where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
			        new ClassAttribute(linkClass, "roleBObjectRef.key.id"));
			where.setFromIndicies(new int[] { idx_target, idx_dcLink }, 0);
			where.setOuterJoin(0);
			spec.appendWhere(where, new int[] { idx_target, idx_dcLink });

			spec.appendAnd();

			spec.appendOpenParen();
			StringTokenizer customerEventToken = new StringTokenizer(customerEvent, ", ");
			while (customerEventToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(linkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
				            .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("CUSTOMEREVENT",
				                    customerEventToken.nextToken()))), new int[] { idx_dcLink });
			    if (customerEventToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();

			// where = new SearchCondition(linkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL,
			// customer.getPersistInfo().getObjectIdentifier().getId());
			// spec.appendWhere(where, new int[]{idx_dcLink});
			// }
		    }
		    // 멀티 기준 정보(고객처) -Text 변경
		    String subcontractor = (String) map.get("SUBCONTRACTOR1");
		    if (subcontractor != null && subcontractor.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			// subcontractor = StringUtil.changeString(subcontractor, "*", "%");
			Class<ProjectSubcontractorLink> scLink = ProjectSubcontractorLink.class;
			int idx_sc = spec.appendClassList(scLink, false);
			int idx_nc = spec.appendClassList(NumberCode.class, false);

			SearchCondition where = null;
			where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
			        new ClassAttribute(scLink, "roleBObjectRef.key.id"));
			where.setFromIndicies(new int[] { idx_target, idx_sc }, 0);
			where.setOuterJoin(0);
			spec.appendWhere(where, new int[] { idx_target, idx_sc });

			spec.appendAnd();

			SearchCondition where2 = null;
			where2 = new SearchCondition(new ClassAttribute(NumberCode.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
			        new ClassAttribute(scLink, "roleAObjectRef.key.id"));
			where2.setFromIndicies(new int[] { idx_nc, idx_sc }, 0);
			where2.setOuterJoin(2);
			spec.appendWhere(where2, new int[] { idx_nc, idx_sc });

			spec.appendAnd();

			// * 검색, 콤마검색
			KETQueryUtil.setQuerySpecForMultiSearch(spec, NumberCode.class, idx_nc, "name", subcontractor, true);

			// where = new SearchCondition(NumberCode.class, "name",SearchCondition.LIKE, subcontractor );
			// spec.appendWhere(where, new int[]{idx_nc});
		    }

		    // pName, pNumber
		    String pName = StringUtil.checkNull((String) map.get("pName"));
		    String pNum = StringUtil.checkNull((String) map.get("pNum"));
		    if (pName.length() > 0 || pNum.length() > 0) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			// Table Class 가 잘못 된 것 같은데..
			// Class ppClass = ProjectProductInfoLink.class;

			Class<ProductInfo> ppClass = ProductInfo.class;
			int idx_pp = spec.appendClassList(ppClass, false);
			SearchCondition where = null;
			where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
			        new ClassAttribute(ppClass, "projectReference.key.id"));
			where.setFromIndicies(new int[] { idx_target, idx_pp }, 0);
			where.setOuterJoin(2);
			spec.appendWhere(where, new int[] { idx_target, idx_pp });

			if (pNum.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, ppClass, idx_pp, "pNum", pName, true);

			    // pName = StringUtil.changeString(pNum, "*", "%");
			    // where = new SearchCondition(ppClass, "pName",SearchCondition.LIKE, pNum);
			    // spec.appendWhere(where, new int[]{idx_pp});
			}

			if (pName.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, ppClass, idx_pp, "pName", pName, true);

			    // pNum = StringUtil.changeString(pNum, "*", "%");
			    // where = new SearchCondition(ppClass, "pName",SearchCondition.LIKE, pName);
			    // spec.appendWhere(where, new int[]{idx_pp});
			}
		    }

		    /*
	             * 1,0 : 개발 검토 (자동차/전자) 2,4 : 제품 (자동차/전자) 3 : 금형 사업부(dtype) 1 : 자동차 2 : 전자
	             */

		    String partNo = (String) map.get("partNo");
		    String partName = (String) map.get("partName");
		    if ((partNo != null && partNo.length() > 0) || (partName != null && partName.length() > 0)) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			spec.appendOpenParen();
			spec.appendOpenParen();

			int idx1 = spec.addClassList(MoldItemInfo.class, false);
			ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
			ClassAttribute ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
			SearchCondition sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[] { idx1, idx_target }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { idx1, idx_target });

			if (partNo != null && partNo.length() > 0) {

			    spec.appendAnd();
			    // ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
			    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
			    // partNo = StringUtil.changeString(partNo, "*", "%");
			    // ColumnExpression ce = ConstantExpression.newExpression(partNo);
			    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
			    // spec.appendWhere(dsc, new int[] {idx1 });

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NO, partNo, false);

			    // setUpperNoneLikeCondition(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NO, partNo.trim() );
			}

			if (partName != null && partName.length() > 0) {
			    spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil
				    .setQuerySpecForMultiSearch(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NAME, partName, false);

			    // ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
			    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
			    // partName = StringUtil.changeString(partName, "*", "%");
			    // ColumnExpression ce = ConstantExpression.newExpression(partName);
			    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
			    // spec.appendWhere(dsc, new int[] {idx1 });

			    // setUpperNoneLikeCondition(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NAME, partName.trim() );
			}

			spec.appendCloseParen();

			spec.appendOr();

			spec.appendOpenParen();

			idx1 = spec.addClassList(ProductInfo.class, false);
			ca0 = new ClassAttribute(ProductInfo.class, ProductInfo.PROJECT_REFERENCE + ".key.id");
			ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[] { idx1, idx_target }, 0);
			sc.setOuterJoin(0);
			spec.appendWhere(sc, new int[] { idx1, idx_target });

			if (partNo != null && partNo.length() > 0) {
			    spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductInfo.class, idx1, ProductInfo.P_NUM, partNo, false);

			    // ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NUM);
			    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
			    // partNo = StringUtil.changeString(partNo, "*", "%");
			    // ColumnExpression ce = ConstantExpression.newExpression(partNo);
			    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
			    // spec.appendWhere(dsc, new int[] {idx1 });
			}

			if (partName != null && partName.length() > 0) {
			    spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductInfo.class, idx1, ProductInfo.P_NAME, partName, false);

			    // ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NAME);
			    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
			    // partName = StringUtil.changeString(partName, "*", "%");
			    // ColumnExpression ce = ConstantExpression.newExpression(partName);
			    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
			    // spec.appendWhere(dsc, new int[] {idx1 });
			}

			spec.appendCloseParen();
			spec.appendCloseParen();
		    }

		    String dType = (String) map.get("dType");
		    if (StringUtil.checkString(pjtType)) {

			// [START] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현
			/*
		         * 아래 if절 모두 해당되지 않을 경우가 있을 경우 AND 가 붙으면 안된다. 각각 if절 안에서 조건절 만들기 직전으로 코드를 삽입한다.
		         */
			// if(spec.getConditionCount() > 0) spec.appendAnd();
			// [END] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현

			if (pjtType.equals("0") || pjtType.equals("1")) {

			    // [START] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현
			    /*
		             * 아래 if절 모두 해당되지 않을 경우가 있을 경우 AND 가 붙으면 안된다. 각각 if절 안에서 조건절 만들기 직전으로 코드를 삽입한다.
		             */
			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }
			    // [END] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현

			    if (dType != null && dType.length() > 0) {
				if (dType.equals("1")) {
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")),
					    new int[] { idx_target });
				} else {
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("0")),
					    new int[] { idx_target });
				}
			    } else {
				spec.appendOpenParen();
				spec.appendWhere(
				        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("0")),
				        new int[] { idx_target });
				spec.appendOr();
				spec.appendWhere(
				        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")),
				        new int[] { idx_target });
				spec.appendCloseParen();
			    }
			} else if (pjtType.equals("2") || pjtType.equals("4")) {

			    // [START] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현
			    /*
		             * 아래 if절 모두 해당되지 않을 경우가 있을 경우 AND 가 붙으면 안된다. 각각 if절 안에서 조건절 만들기 직전으로 코드를 삽입한다.
		             */
			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }
			    // [END] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현

			    if (dType != null && dType.length() > 0) {
				if (dType.equals("1")) {
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")),
					    new int[] { idx_target });
				} else {
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")),
					    new int[] { idx_target });
				}
			    } else {
				spec.appendOpenParen();
				spec.appendWhere(
				        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")),
				        new int[] { idx_target });
				spec.appendOr();
				spec.appendWhere(
				        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")),
				        new int[] { idx_target });
				spec.appendCloseParen();
			    }
			} else if (pjtType.equals("3")) {

			    // [START] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현
			    /*
		             * 아래 if절 모두 해당되지 않을 경우가 있을 경우 AND 가 붙으면 안된다. 각각 if절 안에서 조건절 만들기 직전으로 코드를 삽입한다.
		             */
			    if (spec.getConditionCount() > 0) {
				spec.appendAnd();
			    }
			    // [END] [PLM System 1차 고도화] 기존 에러 처리, 2014-06-09, 김태현

			    spec.appendWhere(
				    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("3")),
				    new int[] { idx_target });

			    int index_item = spec.addClassList(MoldItemInfo.class, false);
			    int index_productProject = spec.addClassList(ProductProject.class, false);
			    ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
			    ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);
			    ClassAttribute ca2 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
			    ClassAttribute ca3 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);

			    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
			    sc.setFromIndicies(new int[] { idx_target, index_item }, 0);
			    sc.setOuterJoin(0);
			    spec.appendAnd();
			    spec.appendWhere(sc, new int[] { idx_target, index_item });

			    sc = new SearchCondition(ca2, "=", ca3);
			    sc.setFromIndicies(new int[] { index_item, index_productProject }, 0);
			    sc.setOuterJoin(0);
			    spec.appendAnd();
			    spec.appendWhere(sc, new int[] { index_item, index_productProject });

			    String dieNo = (String) map.get("dieNo");
			    String devDeptOid = (String) map.get("devDeptOid");
			    String productpjtNo = (String) map.get("productpjtNo");
			    String itemType = (String) map.get("itemType");
			    String moldProductType = (String) map.get("moldProductType");
			    String productPartNo = (String) map.get("productPartNo");
			    String productName = (String) map.get("productName");
			    String making = (String) map.get("making");
			    String moldType = (String) map.get("moldType");
			    String outsourcing = (String) map.get("outsourcing");
			    String setProductPm = (String) map.get("setProductPm");
			    String setMoldCharger = (String) map.get("setMoldCharger");
			    String carTypeInfo2 = (String) map.get("carTypeInfo2");
			    String developType2 = (String) map.get("DEVELOPENTTYPE2");

			    if (dieNo != null && dieNo.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.DIE_NO, dieNo,
				        false);

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }
				// ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// dieNo = StringUtil.changeString(dieNo, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(dieNo);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {index_item });

				// setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.DIE_NO, dieNo.trim() );
			    }

			    if (devDeptOid != null && devDeptOid.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

				ClassAttribute mca1 = null;
				ClassAttribute mca2 = null;

				mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
				mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
				SearchCondition msc = new SearchCondition(mca1, "=", mca2);
				msc.setFromIndicies(new int[] { index_productProject, linkIndex }, 0);
				msc.setOuterJoin(0);
				spec.appendWhere(msc, new int[] { index_productProject, linkIndex });

				spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer devDeptOidToken = new StringTokenizer(devDeptOid, ", ");
				while (devDeptOidToken.hasMoreTokens()) {
				    spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id",
					    SearchCondition.EQUAL, CommonUtil.getOIDLongValue(devDeptOidToken.nextToken())),
					    new int[] { linkIndex });
				    if (devDeptOidToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// long deptid = CommonUtil.getOIDLongValue(devDeptOid);
				// spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=",
				// deptid), new int[] {linkIndex});
			    }

			    if (productpjtNo != null && productpjtNo.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductProject.class, index_productProject,
				        ProductProject.PJT_NO, productpjtNo, true);

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }
				// ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// productpjtNo = StringUtil.changeString(productpjtNo, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(productpjtNo);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {index_productProject});
				Kogger.debug(SearchPagingProjectHelper.class, "productpjtNo == " + productpjtNo);
				// setUpperNoneLikeCondition(spec, ProductProject.class, index_productProject, ProductProject.PJT_NO,
				// productpjtNo.trim() );

			    }
			    // Part Name
			    if (productName != null && productName.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NAME,
				        productName, false);

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }
				// ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// productName = StringUtil.changeString(productName, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(productName);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {index_item });
				// Kogger.debug(getClass(), "productName == " + productName);
				// setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NAME,
				// productName.trim() );

			    }
			    // Part No
			    if (productPartNo != null && productPartNo.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NO,
				        productPartNo, false);

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }

				// ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// productPartNo = StringUtil.changeString(productPartNo, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(productPartNo);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {index_item });
				// Kogger.debug(getClass(), "productPartNo == " + productPartNo);
				// setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NO,
				// productPartNo.trim() );
			    }

			    // 금형구분
			    if (itemType != null && itemType.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer itemTypeToken = new StringTokenizer(itemType, ", ");
				while (itemTypeToken.hasMoreTokens()) {
				    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, SearchCondition.EQUAL,
					    itemTypeToken.nextToken()), new int[] { index_item });
				    if (itemTypeToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }
				// spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", itemType), new
				// int[]{index_item});
			    }

			    // 금형분류
			    if (moldProductType != null && moldProductType.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer moldProductTypeToken = new StringTokenizer(moldProductType, ", ");
				while (moldProductTypeToken.hasMoreTokens()) {
				    spec.appendWhere(
					    new SearchCondition(MoldItemInfo.class, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id",
					            SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                    .getNumberCode("MOLDPRODUCTSTYPE", moldProductTypeToken.nextToken()))),
					    new int[] { index_item });
				    if (moldProductTypeToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id",
				// "MOLDPRODUCTSTYPE", moldProductType );
			    }

			    // 제작구분
			    if (making != null && making.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer makingToken = new StringTokenizer(making, ", ");
				while (makingToken.hasMoreTokens()) {
				    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, SearchCondition.EQUAL,
					    makingToken.nextToken()), new int[] { index_item });
				    if (makingToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// if(spec.getConditionCount() > 0) {
				// spec.appendAnd();
				// }
				// spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, "=", making), new
				// int[]{index_item});
			    }

			    // 금형유형
			    if (moldType != null && moldType.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer moldTypeToken = new StringTokenizer(moldType, ", ");
				while (moldTypeToken.hasMoreTokens()) {
				    spec.appendWhere(
					    new SearchCondition(MoldItemInfo.class, MoldItemInfo.MOLD_TYPE_REFERENCE + ".key.id",
					            SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                    .getNumberCode("MOLDTYPE", moldTypeToken.nextToken()))),
					    new int[] { index_item });
				    if (moldTypeToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.MOLD_TYPE_REFERENCE + ".key.id",
				// "MOLDTYPE", moldType );
			    }

			    // 제작처
			    if (outsourcing != null && outsourcing.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// ClassAttribute mca = new ClassAttribute(MoldProject.class, MoldProject.OUT_SOURCING);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// outsourcing = StringUtil.changeString(outsourcing, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(outsourcing);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx_target});

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldProject.class, idx_target, MoldProject.OUT_SOURCING,
				        outsourcing, true);
			    }

			    // Kogger.debug(getClass(), "carTypeInfo2 = " + carTypeInfo2);
			    if (carTypeInfo2 != null && carTypeInfo2.length() > 0) {
				if (spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				}
				// ClassAttribute mca = new ClassAttribute(OEMProjectType.class, OEMProjectType.NAME);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// carTypeInfo2 = StringUtil.changeString(carTypeInfo2, "*", "%");
				// carTypeInfo2 = carTypeInfo2.toUpperCase();
				Class<OEMProjectType> oemLink = OEMProjectType.class;
				int idx_oem = spec.appendClassList(oemLink, false);
				SearchCondition where = null;
				where = new SearchCondition(new ClassAttribute(ProductProject.class, "oemTypeReference.key.id"), "=",
				        new ClassAttribute(oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
				where.setFromIndicies(new int[] { index_productProject, idx_oem }, 0);
				where.setOuterJoin(0);
				spec.appendAnd();
				spec.appendWhere(where, new int[] { index_productProject, idx_oem });

				spec.appendAnd();
				KETQueryUtil.setQuerySpecForMultiSearch(spec, OEMProjectType.class, idx_oem, OEMProjectType.NAME,
				        carTypeInfo2, true);

				// ColumnExpression ce = ConstantExpression.newExpression(carTypeInfo2);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx_oem});

			    }

			    // 개발담당자
			    if (setProductPm != null && setProductPm.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				Class<ProjectMemberLink> prjectMember = ProjectMemberLink.class;
				int idx_Member = spec.appendClassList(prjectMember, false);

				ClassAttribute mca1 = null;
				ClassAttribute mca2 = null;

				mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
				mca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
				SearchCondition msc = new SearchCondition(mca1, "=", mca2);
				msc.setFromIndicies(new int[] { index_productProject, idx_Member }, 0);
				msc.setOuterJoin(0);
				spec.appendWhere(msc, new int[] { index_productProject, idx_Member });

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				msc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
				        ProjectUserHelper.PM);
				spec.appendWhere(msc, new int[] { idx_Member });

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer setProductPmToken = new StringTokenizer(setProductPm, ", ");
				while (setProductPmToken.hasMoreTokens()) {
				    spec.appendWhere(new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL,
					    CommonUtil.getOIDLongValue(setProductPmToken.nextToken())), new int[] { idx_Member });
				    if (setProductPmToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// if(spec.getConditionCount() > 0)
				// spec.appendAnd();
				//
				// long userId = CommonUtil.getOIDLongValue(setProductPm);
				// msc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, userId);
				// spec.appendWhere(msc , new int[]{idx_Member});
			    }

			    if (developType2 != null && developType2.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				spec.appendOpenParen();
				StringTokenizer developTypeToken = new StringTokenizer(developType2, ", ");
				while (developTypeToken.hasMoreTokens()) {
				    spec.appendWhere(
					    new SearchCondition(ProductProject.class, "developentTypeReference.key.id",
					            SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                    .getNumberCode("DEVELOPENTTYPE", developTypeToken.nextToken()))),
					    new int[] { index_productProject });
				    if (developTypeToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// setNumberCodeQuery(spec, ProductProject.class, index_productProject, "developentTypeReference.key.id",
				// "DEVELOPENTTYPE", developType2);

			    }

			}
		    }
		}
	    }

	    /*
	     * 최신 프로젝트
	     */

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { idx_target });
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	    // ///////////////////////////////////////////////////////////////////
	    // Sub Query End mainSpec
	    // ///////////////////////////////////////////////////////////////////

	    SubSelectExpression subfrom = new SubSelectExpression(spec);
	    subfrom.setFromAlias(new String[] { "B0" }, 0);
	    int sub_pjt_index = mainSpec.appendFrom(subfrom);

	    if (mainSpec.getConditionCount() > 0)
		mainSpec.appendAnd();

	    SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		    new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
	    sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	    sc.setOuterJoin(0);
	    mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	    int sortIdx = 0;
	    String sort = (String) map.get("sortKey");
	    String isDesc = (String) map.get("dsc");
	    // Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);

	    if ((sort != null) && (sort.trim().length() > 0)) {
		if ((isDesc == null) || (isDesc.trim().length() == 0)) {
		    isDesc = "FALSE";
		}

		if (sort.equals(E3PSProject.PJT_NAME) || sort.equals("state.state") || sort.equals(E3PSProject.PJT_NO)) {
		    SearchUtil.setOrderBy(mainSpec, target, main_idx, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		}

		if (sort.equals("planEndDate") || sort.equals("planStartDate")) {
		    if (mainSpec.getConditionCount() > 0)
			mainSpec.appendAnd();

		    Class<ExtendScheduleData> schedule2 = ExtendScheduleData.class;
		    int idx_schedule2 = mainSpec.appendClassList(schedule2, false);

		    ClassAttribute ca1 = null;
		    ClassAttribute ca2 = null;

		    ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
		    ca2 = new ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME);
		    SearchCondition sc2 = new SearchCondition(ca1, "=", ca2);
		    sc2.setFromIndicies(new int[] { main_idx, idx_schedule2 }, 0);
		    sc2.setOuterJoin(0);
		    mainSpec.appendWhere(sc2, new int[] { main_idx, idx_schedule2 });

		    SearchUtil.setOrderBy(mainSpec, schedule2, idx_schedule2, sort, "m_Sort" + sortIdx++,
			    "TRUE".equals(isDesc.toUpperCase()));
		}

		if (sort.equals("moldPartName") || sort.equals("moldPartNo") || sort.equals("making")) {
		    int index_item = mainSpec.addClassList(MoldItemInfo.class, false);
		    // int index_productProject = mainSpec.addClassList(ProductProject.class, false);

		    ClassAttribute ca0 = new ClassAttribute(MoldProject.class, MoldProject.MOLD_INFO_REFERENCE + ".key.id");
		    ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);

		    SearchCondition msc = new SearchCondition(ca0, "=", ca1);
		    msc.setFromIndicies(new int[] { main_idx, index_item }, 0);
		    msc.setOuterJoin(0);

		    // Kogger.debug(getClass(), "index_item === " + index_item);

		    if (mainSpec.getConditionCount() > 0) {
			mainSpec.appendAnd();
		    }

		    mainSpec.appendWhere(msc, new int[] { main_idx, index_item });

		    String sortKey = "";
		    if (sort.equals("moldPartNo")) {
			sortKey = MoldItemInfo.PART_NO;
		    } else if (sort.equals("moldPartName")) {
			sortKey = MoldItemInfo.PART_NAME;
		    } else if (sort.equals("making")) {
			sortKey = MoldItemInfo.MAKING;
		    }

		    SearchUtil.setOrderBy(mainSpec, MoldItemInfo.class, index_item, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(isDesc.toUpperCase()));
		}
	    }

	    // 디폴트 생성 일자
	    SearchUtil.setOrderBy(mainSpec, target, main_idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "modifyStamp", true);
	    Kogger.debug(SearchPagingProjectHelper.class, mainSpec);

	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}

	// Kogger.debug(getClass(), "project Search spec ===>"+mainSpec);
	return mainSpec;
    }

    public static QuerySpec getE3PSProjectQuerySpec(HashMap<String, String> map, List<Map<String, String>> conditionList)
	    throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class, List.class };
	    Object args[] = new Object[] { map, conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getE3PSProjectQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec mainSpec = null;
	try {

	    // Attribute Setting
	    String cmd = (String) map.get("command");
	    String initType = (String) map.get("initType");
	    String pjtType = (String) map.get("pjtType");
	    if (cmd == null)
		cmd = "";
	    mainSpec = new QuerySpec();
	    Class main_target = E3PSProject.class;
	    if (pjtType.equals("3")) {
		main_target = MoldProject.class;
	    }
	    int main_idx = mainSpec.addClassList(main_target, true);

	    // ///////////////////////////////////////////////////////////////////
	    // Sub Query Start mainSpec
	    // ///////////////////////////////////////////////////////////////////

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }

	    QuerySpec spec = null;
	    spec = new QuerySpec();

	    if (!spec.isAdvancedQueryEnabled()) {
		spec.setAdvancedQueryEnabled(true);
	    }

	    Class target = E3PSProject.class;
	    // pjtType(프로젝트종류 - 검토, 제품, 금형)
	    if (pjtType.equals("0") || pjtType.equals("1")) {
		target = ReviewProject.class;
	    }

	    if (pjtType.equals("2") || pjtType.equals("4")) {
		target = ProductProject.class;
	    }

	    if (pjtType.equals("3")) {
		target = MoldProject.class;
	    }

	    int idx_target = spec.addClassList(target, false);

	    spec.setDistinct(true);

	    ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("projectId");
	    spec.appendSelect(ca, true);

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    long lperistable = CommonUtil.getOIDLongValue(wtuser);

	    /*
	     * Department dept = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(wtuser); ArrayList childList = null; if(dept
	     * != null) { childList = e3ps.groupware.company.DepartmentHelper.manager.getChildDeptTree(dept); } if(childList == null) {
	     * childList = new ArrayList(); } childList.add(0, dept);
	     */

	    if (StringUtil.checkString(cmd)) {
		if (cmd.equalsIgnoreCase("search") || cmd.equalsIgnoreCase("select") || cmd.equalsIgnoreCase("excelDownProject")) {

		    // List..
		    for (Map<String, String> condistion : conditionList) {
			// 프로젝트 NO
			if (StringUtil.checkString(condistion.get("pjtNo"))) {
			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, target, idx_target, E3PSProject.PJT_NO, condistion.get("pjtNo"),
				    true);

			    // String pjtNo = StringUtil.changeString(condistion.get("pjtNo"), "*", "%");
			    // setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NO, pjtNo.trim() );
			}

			// 프로젝트 명
			if (StringUtil.checkString(condistion.get("pjtName"))) {
			    if (spec.getConditionCount() > 0) spec.appendAnd();
			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, target, idx_target, E3PSProject.PJT_NAME,
				    condistion.get("pjtName"), true);

			    // String pjtName = StringUtil.changeString(condistion.get("pjtName"), "*", "%");
			    // setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NAME, pjtName.trim() );
			}

			// 프로젝트 상태
			/**
			 * 2011 - 05 - 18 프로젝트 상태값(지연)으로 검색 기능 추가....
			 */
			String pjtState = condistion.get("pjtState");
			if (StringUtil.checkString(pjtState)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    /*
		             * 진행중 상태이며.. 지연중인것으로 검색...
		             */
			    if (pjtState.equals("UNDERREVIEW")) {
				Hashtable h = ProjectHelper.APPROVALSTATE;
				Enumeration e = h.keys();
				spec.appendOpenParen();
				boolean start = true;
				while (e.hasMoreElements()) {
				    if (!start) {
					spec.appendOr();
				    } else {
					start = false;
				    }
				    String key = (String) e.nextElement();
				    SearchCondition where = new SearchCondition(target, "state.state", SearchCondition.EQUAL, key);
				    spec.appendWhere(where, new int[] { idx_target });
				}
				spec.appendCloseParen();
			    } else if ("delay".equals(pjtState)) {
				spec.appendWhere(new SearchCondition(target, "state.state", "=", "PROGRESS"), new int[] { idx_target });
				spec.appendAnd();
				spec.appendWhere(new SearchCondition(target, "pjtState", "=", ProjectStateFlag.PROJECT_STATE_DELAY),
				        new int[] { idx_target });
			    } else {
				spec.appendOpenParen();
				StringTokenizer pjtStateToken = new StringTokenizer(pjtState, ", ");
				while (pjtStateToken.hasMoreTokens()) {

				    String state = pjtStateToken.nextToken();
				    if (!state.equals("delay")) {
					spec.appendWhere(new SearchCondition(target, "state.state", SearchCondition.EQUAL, state),
					        new int[] { idx_target });
				    } else if (state.equals("delay")) {
					spec.appendOpenParen();
					spec.appendWhere(new SearchCondition(target, "state.state", "=", "PROGRESS"),
					        new int[] { idx_target });
					spec.appendAnd();
					spec.appendWhere(
					        new SearchCondition(target, "pjtState", "=", ProjectStateFlag.PROJECT_STATE_DELAY),
					        new int[] { idx_target });
					spec.appendCloseParen();
				    }
				    if (pjtStateToken.hasMoreTokens())
					spec.appendOr();
				}
				spec.appendCloseParen();

				// SearchCondition where = new SearchCondition(target, "state.state" , SearchCondition.EQUAL, pjtState);
				// spec.appendWhere(where, new int[]{idx_target});
			    }
			}

			// PM
			String setPm = condistion.get("setPm");
			if (StringUtil.checkString(setPm)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    Class<ProjectMemberLink> prjectMember = ProjectMemberLink.class;
			    int idx_Member = spec.appendClassList(prjectMember, false);

			    ClassAttribute ca1 = null;
			    ClassAttribute ca2 = null;

			    ca1 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
			    ca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
			    SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			    sc.setFromIndicies(new int[] { idx_target, idx_Member }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { idx_target, idx_Member });

			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
				    ProjectUserHelper.PM);
			    spec.appendWhere(sc, new int[] { idx_Member });

			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer setPmToken = new StringTokenizer(setPm, ", ");
			    while (setPmToken.hasMoreTokens()) {
				spec.appendWhere(new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL,
				        CommonUtil.getOIDLongValue(setPmToken.nextToken())), new int[] { idx_Member });
				if (setPmToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();

			    // long pmUser = 0;
			    // if ( setPm != null && setPm.length() > 0 ) {
			    // pmUser = CommonUtil.getOIDLongValue(setPm);
			    // }

			    // sc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, pmUser);
			    // spec.appendWhere(sc , new int[]{idx_Member});
			}

			// 개발담당부서 검토
			String rdevDeptOid = condistion.get("rdevDeptOid");
			if (StringUtil.checkString(rdevDeptOid)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer rdevDeptOidToken = new StringTokenizer(rdevDeptOid, ", ");
			    while (rdevDeptOidToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "devDeptReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(rdevDeptOidToken.nextToken())), new int[] { idx_target });
				if (rdevDeptOidToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();

			    // if ( spec.getConditionCount() > 0)
			    // spec.appendAnd();
			    // long deptid = CommonUtil.getOIDLongValue(rdevDeptOid);
			    // spec.appendWhere(new SearchCondition(target, "devDeptReference.key.id","=", deptid), new int[] {idx_target}
			    // );
			}

			// 개발담당부서 제품
			String pdevDeptOid = condistion.get("pdevDeptOid");
			if (StringUtil.checkString(pdevDeptOid)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

			    ClassAttribute mca1 = null;
			    ClassAttribute mca2 = null;

			    mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
			    mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
			    SearchCondition msc = new SearchCondition(mca1, "=", mca2);
			    msc.setFromIndicies(new int[] { idx_target, linkIndex }, 0);
			    msc.setOuterJoin(0);
			    spec.appendWhere(msc, new int[] { idx_target, linkIndex });

			    spec.appendAnd();

			    // long deptid = CommonUtil.getOIDLongValue(pdevDeptOid);
			    // spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=", deptid),
			    // new int[] {linkIndex});

			    spec.appendOpenParen();
			    StringTokenizer pdevDeptOidToken = new StringTokenizer(pdevDeptOid, ", ");
			    while (pdevDeptOidToken.hasMoreTokens()) {
				spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id",
				        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pdevDeptOidToken.nextToken())),
				        new int[] { linkIndex });
				if (pdevDeptOidToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			String developDateType = condistion.get("developDateType"); // 개발시작일/개발완료일 Combo
			String planStartStartDate = condistion.get("planStartStartDate"); // planStartStartDate(계획 시작일자 - 시작)
			String planStartEndDate = condistion.get("planStartEndDate"); // planStartEndDate(계획 시작일자 - 끝)
			String planEndStartDate = condistion.get("planEndStartDate"); // planEndStartDate(계획 종료일자 - 시작)
			String planEndEndDate = condistion.get("planEndEndDate"); // planEndEndDate(계획 종료일자 - 끝)

			// 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
			// 개발시작일/개발완료일 Combo가 Null 아니면
			if (StringUtil.checkString(developDateType)) {

			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    Class<ExtendScheduleData> schedule = ExtendScheduleData.class;
			    int idx_schedule = spec.appendClassList(schedule, false);

			    ClassAttribute ca1 = null;
			    ClassAttribute ca2 = null;

			    ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
			    ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
			    SearchCondition sc = new SearchCondition(ca1, "=", ca2);
			    sc.setFromIndicies(new int[] { idx_target, idx_schedule }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { idx_target, idx_schedule });

			    if (developDateType.contains("DEVELOPDATESTART")) {
				if (planStartStartDate != null && planStartStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planStartEndDate != null && planStartEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
			    }
			    if (developDateType.contains("DEVELOPDATEEND")) {
				if (planStartStartDate != null && planStartStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planStartEndDate != null && planStartEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
			    }
			    if (developDateType.contains("STARTDATE")) {
				if (planStartStartDate != null && planStartStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.EXEC_START_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planStartEndDate != null && planStartEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.EXEC_START_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
			    }
			    if (developDateType.contains("ENDDATE")) {
				if (planStartStartDate != null && planStartStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.EXEC_END_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planStartEndDate != null && planStartEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.EXEC_END_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
			    }
			} else {
			    // 계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
			    if ((planStartStartDate != null && planStartStartDate.length() > 0)
				    || (planStartEndDate != null && planStartEndDate.length() > 0)
				    || (planEndStartDate != null && planEndStartDate.length() > 0)
				    || (planEndEndDate != null && planEndEndDate.length() > 0)) {

				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				Class<ExtendScheduleData> schedule = ExtendScheduleData.class;
				int idx_schedule = spec.appendClassList(schedule, false);

				ClassAttribute ca1 = null;
				ClassAttribute ca2 = null;

				ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
				ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
				SearchCondition sc = new SearchCondition(ca1, "=", ca2);
				sc.setFromIndicies(new int[] { idx_target, idx_schedule }, 0);
				sc.setOuterJoin(0);
				spec.appendWhere(sc, new int[] { idx_target, idx_schedule });

				if (planStartStartDate != null && planStartStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planStartEndDate != null && planStartEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planEndStartDate != null && planEndStartDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
					    new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE,
					    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
				if (planEndEndDate != null && planEndEndDate.length() > 0) {
				    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
					    planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN,
					    convertDate);
				    spec.appendWhere(sc, new int[] { idx_schedule });
				}
			    }
			}

			/*
		         * 기준정보 검색 항목 v 제품구분 v 개발구분 v 조립구분 v Rank 대표차종 v 납입처 v 고객사 제작구분 금형구분 금형분류
		         */

			// Rank
			// Rank
			String rank = condistion.get("rank");
			if (rank != null && rank.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer rankToken = new StringTokenizer(rank, ", ");
			    while (rankToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "rankReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("RANK", rankToken.nextToken()))),
				        new int[] { idx_target });
				if (rankToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}
			// 개발구분
			if ((Object) target instanceof ProductProject) {
			    String developType = condistion.get("developType");
			    if (developType != null && developType.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();
        
        			    spec.appendOpenParen();
        			    StringTokenizer developTypeToken = new StringTokenizer(developType, ", ");
        			    while (developTypeToken.hasMoreTokens()) {
        				spec.appendWhere(
        				        new SearchCondition(target, "developentTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
        				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE",
        				                        developTypeToken.nextToken()))), new int[] { idx_target });
        				if (developTypeToken.hasMoreTokens())
        				    spec.appendOr();
        			    }
        			    spec.appendCloseParen();
			    }
			}

			// 개발단계
			String process = condistion.get("process");
			if (process != null && process.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer processToken = new StringTokenizer(process, ", ");
			    while (processToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "processReference.key.id", SearchCondition.EQUAL,
				                CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode("PROCESS",
				                        processToken.nextToken()))), new int[] { idx_target });
				if (processToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}
			// 관리제품군
			String manageProductType = condistion.get("manageProductType");
			if (manageProductType != null && manageProductType.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer manageProductTypeToken = new StringTokenizer(manageProductType, ", ");
			    while (manageProductTypeToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "manageProductTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("MANAGEPRODUCTTYPE",
				                        manageProductTypeToken.nextToken()))), new int[] { idx_target });
				if (manageProductTypeToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			/*
		         * // 1.제품구분 String productTypeLevel2 = condistion.get("productTypeLevel2"); if
		         * (StringUtil.checkString(productTypeLevel2)) { String[] productTypeLevel2s = productTypeLevel2.split(","); if
		         * (spec.getConditionCount() > 0) spec.appendAnd();
		         * 
		         * spec.appendOpenParen();
		         * 
		         * StringTokenizer productTypeLevel2Token = new StringTokenizer(productTypeLevel2, ", "); while
		         * (productTypeLevel2Token.hasMoreTokens()) { spec.appendWhere( new SearchCondition(target, "productTypeLevel2",
		         * SearchCondition.EQUAL, CommonUtil .getOIDLongValue(productTypeLevel2Token.nextToken())), new int[] { idx_target
		         * }); if (productTypeLevel2Token.hasMoreTokens()) spec.appendOr(); } spec.appendCloseParen();
		         * 
		         * }
		         */

			// 제품구분
			String productTypeLevel2 = condistion.get("productTypeLevel2");
			if (StringUtil.checkString(productTypeLevel2)) {
			    String[] productTypeLevel2_ = productTypeLevel2.split(",");
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();
			    spec.appendWhere(new SearchCondition(target, "productTypeLevel2", productTypeLevel2_, true, false),
				    new int[] { idx_target });
			}

			// 개발목적1
			String developePurpose1 = condistion.get("developePurpose1");
			if (developePurpose1 != null && developePurpose1.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer developePurpose1Token = new StringTokenizer(developePurpose1, ", ");
			    while (developePurpose1Token.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "mainGoalTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL",
				                        developePurpose1Token.nextToken()))), new int[] { idx_target });
				if (developePurpose1Token.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}
			// 개발목적2
			String developePurpose2 = condistion.get("developePurpose2");
			if (developePurpose2 != null && developePurpose2.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer developePurpose2Token = new StringTokenizer(developePurpose2, ", ");
			    while (developePurpose2Token.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "subGoalTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL",
				                        developePurpose2Token.nextToken()))), new int[] { idx_target });
				if (developePurpose2Token.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}
			// 1. 개발유형
			String developPatternType = condistion.get("developPatternType");
			if (StringUtil.checkString(developPatternType)) {
//			    String[] developPatternType_ = developPatternType.split(",");
//			    
//			    long[] developPattern = new long[developPatternType_.length];
//			    for (int i = 0; i < developPatternType_.length; i++) {
//				
//				developPattern[i] = CommonUtil.getOIDLongValue( NumberCodeHelper.manager.getNumberCode("DEVELOPPATTERN",developPatternType_[i]) );
//			    }
//			    
//			    if (spec.getConditionCount() > 0)
//				spec.appendAnd();
//			    spec.appendWhere(new SearchCondition(target, "developPatternReference.key.id", developPattern, false), new int[] { idx_target });
			    
			    developPatternType = StringUtils.replace(developPatternType, ",", "|");
			    
			    String alias = spec.getFromClause().getAliasAt(idx_target);
		            String subQs = "(SELECT IDA2A2 FROM PRODUCTPROJECT WHERE REGEXP_LIKE(DEVELOPEDTYPE,'"+developPatternType+"') AND LASTEST = 1 AND CHECKOUTSTATE <> 'c/o')";
		            
		            KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
		            KeywordExpression kexp2 = new KeywordExpression(subQs);
		            SearchCondition sc0 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
		            if (spec.getConditionCount() > 0) spec.appendAnd();
		            spec.appendWhere(sc0, new int[] { idx_target });
			    
			}
			
			
			// 생산처
			String manufacPlace = condistion.get("manufacPlace");
			if (StringUtil.checkString(manufacPlace)) {

			    manufacPlace = StringUtils.replace(manufacPlace, ",", "|");

			    String alias = spec.getFromClause().getAliasAt(idx_target);
			    String subQs = "(SELECT IDA2A2 FROM PRODUCTPROJECT WHERE REGEXP_LIKE(manufacPlace,'" + manufacPlace + "') AND LASTEST = 1 AND CHECKOUTSTATE <> 'c/o')";

			    KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
			    KeywordExpression kexp2 = new KeywordExpression(subQs);
			    SearchCondition sc0 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();
			    spec.appendWhere(sc0, new int[] { idx_target });

			}
			
			
			
			
			String oemOids = condistion.get("oemOids");
			
			 //파생차종
			if (StringUtil.checkString(oemOids)) {
	            String[] oemOidArr = oemOids.split(",");
	            
	            String longOids = "";
	            for(String oemOid : oemOidArr) longOids += String.valueOf(CommonUtil.getOIDLongValue(oemOid)) + ",";
	            longOids = StringUtils.removeEnd(longOids, ",");
	            
	            
	            String alias = spec.getFromClause().getAliasAt(idx_target);
	            String subQs = "(SELECT IDA3B5 FROM PROJECTOEMTYPELINK WHERE IDA3A5 IN(" + longOids + "))";
	            
	            KeywordExpression kexp = new KeywordExpression(alias + ".IDA2A2");
	            KeywordExpression kexp2 = new KeywordExpression(subQs);
	            SearchCondition sc0 = new SearchCondition(kexp, SearchCondition.IN, kexp2);
	            if (spec.getConditionCount() > 0) spec.appendAnd();
	            spec.appendWhere(sc0, new int[] { idx_target });
	            
	        }

			// 설계구분
			String designType = condistion.get("designType");
			if (designType != null && designType.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer designTypeToken = new StringTokenizer(designType, ", ");
			    while (designTypeToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "designTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("DESIGNTYPE",
				                        designTypeToken.nextToken()))), new int[] { idx_target });
				if (designTypeToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			// 조립구분
			String assembledType = condistion.get("assembledType");
			if (assembledType != null && assembledType.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer assembledTypeToken = new StringTokenizer(assembledType, ", ");
			    while (assembledTypeToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(target, "assembledTypeReference.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("assembledType",
				                        assembledTypeToken.nextToken()))), new int[] { idx_target });
				if (assembledTypeToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			// 시리즈
			String series = condistion.get("series");
			if (series != null && series.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer seriesToken = new StringTokenizer(series, ", ");
			    while (seriesToken.hasMoreTokens()) {
				spec.appendWhere(new SearchCondition(target, "series", SearchCondition.EQUAL, seriesToken.nextToken()),
				        new int[] { idx_target });
				if (seriesToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			// 방수여부
			String sealed = condistion.get("sealed");
			if (sealed != null && sealed.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer sealedToken = new StringTokenizer(sealed, ", ");
			    while (sealedToken.hasMoreTokens()) {
				spec.appendWhere(new SearchCondition(target, "waterPoof", SearchCondition.EQUAL, sealedToken.nextToken()),
				        new int[] { idx_target });
				if (sealedToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();
			}

			// 대표 차종
			String carTypeInfo = condistion.get("carTypeInfo");
			if (!pjtType.equals("3") && carTypeInfo != null && carTypeInfo.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // carTypeInfo = StringUtil.changeString(carTypeInfo, "*", "%");

			    Class<OEMProjectType> oemLink = OEMProjectType.class;
			    int idx_oem = spec.appendClassList(oemLink, false);
			    SearchCondition where = null;
			    where = new SearchCondition(new ClassAttribute(target, "oemTypeReference.key.id"), "=", new ClassAttribute(
				    oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
			    where.setFromIndicies(new int[] { idx_target, idx_oem }, 0);
			    where.setOuterJoin(0);
			    spec.appendWhere(where, new int[] { idx_target, idx_oem });

			    spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, oemLink, idx_oem, "name", carTypeInfo, true);
			    // spec.appendWhere(new SearchCondition(oemLink, "name", SearchCondition.LIKE, carTypeInfo), new int[] {idx_oem}
			    // );
			}

			// 멀티 기준 정보(최종 사용처)
			String customerEvent = condistion.get("CUSTOMEREVENT1");
			if (customerEvent != null && customerEvent.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // NumberCode customer = NumberCodeHelper.manager.getNumberCode("CUSTOMEREVENT", customerEvent);
			    // if ( customer != null ) {

			    Class<ProjectCustomereventLink> linkClass = ProjectCustomereventLink.class;
			    int idx_dcLink = spec.appendClassList(linkClass, false);
			    SearchCondition where = null;
			    where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
				    new ClassAttribute(linkClass, "roleBObjectRef.key.id"));
			    where.setFromIndicies(new int[] { idx_target, idx_dcLink }, 0);
			    where.setOuterJoin(0);
			    spec.appendWhere(where, new int[] { idx_target, idx_dcLink });

			    spec.appendAnd();

			    spec.appendOpenParen();
			    StringTokenizer customerEventToken = new StringTokenizer(customerEvent, ", ");
			    while (customerEventToken.hasMoreTokens()) {
				spec.appendWhere(
				        new SearchCondition(linkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
				                .getOIDLongValue(NumberCodeHelper.manager.getNumberCode("CUSTOMEREVENT",
				                        customerEventToken.nextToken()))), new int[] { idx_dcLink });
				if (customerEventToken.hasMoreTokens())
				    spec.appendOr();
			    }
			    spec.appendCloseParen();

			    // where = new SearchCondition(linkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL,
			    // customer.getPersistInfo().getObjectIdentifier().getId());
			    // spec.appendWhere(where, new int[]{idx_dcLink});
			    // }
			}

			// 멀티 기준 정보(고객처) -Text 변경
			String subcontractor = condistion.get("SUBCONTRACTOR1");
			if (subcontractor != null && subcontractor.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // subcontractor = StringUtil.changeString(subcontractor, "*", "%");
			    Class<ProjectSubcontractorLink> scLink = ProjectSubcontractorLink.class;
			    int idx_sc = spec.appendClassList(scLink, false);
			    int idx_nc = spec.appendClassList(NumberCode.class, false);

			    SearchCondition where = null;
			    where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
				    new ClassAttribute(scLink, "roleBObjectRef.key.id"));
			    where.setFromIndicies(new int[] { idx_target, idx_sc }, 0);
			    where.setOuterJoin(0);
			    spec.appendWhere(where, new int[] { idx_target, idx_sc });

			    spec.appendAnd();

			    SearchCondition where2 = null;
			    where2 = new SearchCondition(new ClassAttribute(NumberCode.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
				    new ClassAttribute(scLink, "roleAObjectRef.key.id"));
			    where2.setFromIndicies(new int[] { idx_nc, idx_sc }, 0);
			    where2.setOuterJoin(2);
			    spec.appendWhere(where2, new int[] { idx_nc, idx_sc });

			    spec.appendAnd();

			    // * 검색, 콤마검색
			    KETQueryUtil.setQuerySpecForMultiSearch(spec, NumberCode.class, idx_nc, "name", subcontractor, true);

			    // where = new SearchCondition(NumberCode.class, "name",SearchCondition.LIKE, subcontractor );
			    // spec.appendWhere(where, new int[]{idx_nc});
			}

			// pName, pNumber
			String pName = StringUtil.checkNull(condistion.get("pName"));
			String pNum = StringUtil.checkNull(condistion.get("pNum"));
			if (pName.length() > 0 || pNum.length() > 0) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    // Table Class 가 잘못 된 것 같은데..
			    // Class ppClass = ProjectProductInfoLink.class;

			    Class<ProductInfo> ppClass = ProductInfo.class;
			    int idx_pp = spec.appendClassList(ppClass, false);
			    SearchCondition where = null;
			    where = new SearchCondition(new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME), "=",
				    new ClassAttribute(ppClass, "projectReference.key.id"));
			    where.setFromIndicies(new int[] { idx_target, idx_pp }, 0);
			    where.setOuterJoin(2);
			    spec.appendWhere(where, new int[] { idx_target, idx_pp });

			    if (pNum.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, ppClass, idx_pp, "pNum", pName, true);

				// pName = StringUtil.changeString(pNum, "*", "%");
				// where = new SearchCondition(ppClass, "pName",SearchCondition.LIKE, pNum);
				// spec.appendWhere(where, new int[]{idx_pp});
			    }

			    if (pName.length() > 0) {
				if (spec.getConditionCount() > 0)
				    spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, ppClass, idx_pp, "pName", pName, true);

				// pNum = StringUtil.changeString(pNum, "*", "%");
				// where = new SearchCondition(ppClass, "pName",SearchCondition.LIKE, pName);
				// spec.appendWhere(where, new int[]{idx_pp});
			    }
			}

			/*
		         * 1,0 : 개발 검토 (자동차/전자) 2,4 : 제품 (자동차/전자) 3 : 금형 사업부(dtype) 1 : 자동차 2 : 전자
		         */
			String partNo = condistion.get("partNo");
			String partName = condistion.get("partName");
			if ((partNo != null && partNo.length() > 0) || (partName != null && partName.length() > 0)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();

			    spec.appendOpenParen();
			    spec.appendOpenParen();

			    int idx1 = spec.addClassList(MoldItemInfo.class, false);
			    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
			    ClassAttribute ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
			    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
			    sc.setFromIndicies(new int[] { idx1, idx_target }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { idx1, idx_target });

			    if (partNo != null && partNo.length() > 0) {

				spec.appendAnd();
				// ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// partNo = StringUtil.changeString(partNo, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(partNo);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx1 });

				// * 검색, 콤마검색
				KETQueryUtil
				        .setQuerySpecForMultiSearch(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NO, partNo, false);

				// setUpperNoneLikeCondition(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NO, partNo.trim() );
			    }

			    if (partName != null && partName.length() > 0) {
				spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NAME, partName,
				        false);

				// ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// partName = StringUtil.changeString(partName, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(partName);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx1 });

				// setUpperNoneLikeCondition(spec, MoldItemInfo.class, idx1, MoldItemInfo.PART_NAME, partName.trim() );
			    }

			    spec.appendCloseParen();

			    spec.appendOr();

			    spec.appendOpenParen();

			    idx1 = spec.addClassList(ProductInfo.class, false);
			    ca0 = new ClassAttribute(ProductInfo.class, ProductInfo.PROJECT_REFERENCE + ".key.id");
			    ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
			    sc = new SearchCondition(ca0, "=", ca1);
			    sc.setFromIndicies(new int[] { idx1, idx_target }, 0);
			    sc.setOuterJoin(0);
			    spec.appendWhere(sc, new int[] { idx1, idx_target });

			    if (partNo != null && partNo.length() > 0) {
				spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductInfo.class, idx1, ProductInfo.P_NUM, partNo, false);

				// ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NUM);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// partNo = StringUtil.changeString(partNo, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(partNo);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx1 });
			    }

			    if (partName != null && partName.length() > 0) {
				spec.appendAnd();

				// * 검색, 콤마검색
				KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductInfo.class, idx1, ProductInfo.P_NAME, partName, false);

				// ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NAME);
				// SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				// partName = StringUtil.changeString(partName, "*", "%");
				// ColumnExpression ce = ConstantExpression.newExpression(partName);
				// SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				// spec.appendWhere(dsc, new int[] {idx1 });
			    }

			    spec.appendCloseParen();
			    spec.appendCloseParen();
			}

			String dType = condistion.get("dType");
			if (StringUtil.checkString(pjtType)) {
			    if (spec.getConditionCount() > 0)
				spec.appendAnd();
			    if (pjtType.equals("0") || pjtType.equals("1")) {
				if (dType != null && dType.length() > 0) {
				    if (dType.equals("1")) {
					spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer
					                .parseInt("1")), new int[] { idx_target });
				    } else {
					spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer
					                .parseInt("0")), new int[] { idx_target });
				    }
				} else {
				    spec.appendOpenParen();
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("0")),
					    new int[] { idx_target });
				    spec.appendOr();
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")),
					    new int[] { idx_target });
				    spec.appendCloseParen();
				}
			    } else if (pjtType.equals("2") || pjtType.equals("4")) {
				if (dType != null && dType.length() > 0) {
				    if (dType.equals("1")) {
					spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer
					                .parseInt("2")), new int[] { idx_target });
				    } else {
					spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer
					                .parseInt("4")), new int[] { idx_target });
				    }
				} else {
				    spec.appendOpenParen();
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")),
					    new int[] { idx_target });
				    spec.appendOr();
				    spec.appendWhere(
					    new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")),
					    new int[] { idx_target });
				    spec.appendCloseParen();
				}
			    } else if (pjtType.equals("3")) {
				if (dType.equals("1")) {
				    spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("3")),
					        new int[] { idx_target });
				}else{
				    spec.appendWhere(
					        new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")),
					        new int[] { idx_target });
				}
				
				
				int index_item = spec.addClassList(MoldItemInfo.class, false);
				int index_productProject = spec.addClassList(ProductProject.class, false);
				ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
				ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);
				ClassAttribute ca2 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
				ClassAttribute ca3 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);

				SearchCondition sc = new SearchCondition(ca0, "=", ca1);
				sc.setFromIndicies(new int[] { idx_target, index_item }, 0);
				sc.setOuterJoin(0);
				spec.appendAnd();
				spec.appendWhere(sc, new int[] { idx_target, index_item });

				sc = new SearchCondition(ca2, "=", ca3);
				sc.setFromIndicies(new int[] { index_item, index_productProject }, 0);
				sc.setOuterJoin(0);
				spec.appendAnd();
				spec.appendWhere(sc, new int[] { index_item, index_productProject });
				
				
				if (carTypeInfo != null && carTypeInfo.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // carTypeInfo = StringUtil.changeString(carTypeInfo, "*", "%");

				    Class<OEMProjectType> oemLink = OEMProjectType.class;
				    int idx_oem = spec.appendClassList(oemLink, false);
				    SearchCondition where = null;
				    where = new SearchCondition(new ClassAttribute(ProductProject.class, "oemTypeReference.key.id"), "=", new ClassAttribute(
					    oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
				    where.setFromIndicies(new int[] { index_productProject, idx_oem }, 0);
				    where.setOuterJoin(0);
				    spec.appendWhere(where, new int[] { index_productProject, idx_oem });

				    spec.appendAnd();

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, oemLink, idx_oem, "name", carTypeInfo, true);
				    // spec.appendWhere(new SearchCondition(oemLink, "name", SearchCondition.LIKE, carTypeInfo), new int[] {idx_oem}
				    // );
				}
				

				String dieNo = condistion.get("dieNo");
				String devDeptOid = condistion.get("devDeptOid");
				String productpjtNo = condistion.get("productpjtNo");
				String itemType = condistion.get("itemType");
				String moldProductType = condistion.get("moldProductType");
				String productPartNo = condistion.get("productPartNo");
				String productName = condistion.get("productName");
				String making = condistion.get("making");
				String moldType = condistion.get("moldType");
				String outsourcing = condistion.get("outsourcing");
				String setProductPm = condistion.get("setProductPm");
				String setMoldCharger = condistion.get("setMoldCharger");
				String carTypeInfo2 = condistion.get("carTypeInfo2");
				String developType2 = condistion.get("DEVELOPENTTYPE2");
				String isPurchase = condistion.get("isPurchase");
				
				if (dieNo != null && dieNo.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.DIE_NO,
					    dieNo, false);

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }
				    // ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // dieNo = StringUtil.changeString(dieNo, "*", "%");
				    // ColumnExpression ce = ConstantExpression.newExpression(dieNo);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {index_item });

				    // setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.DIE_NO, dieNo.trim() );
				}

				if (devDeptOid != null && devDeptOid.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

				    ClassAttribute mca1 = null;
				    ClassAttribute mca2 = null;

				    mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
				    mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
				    SearchCondition msc = new SearchCondition(mca1, "=", mca2);
				    msc.setFromIndicies(new int[] { index_productProject, linkIndex }, 0);
				    msc.setOuterJoin(0);
				    spec.appendWhere(msc, new int[] { index_productProject, linkIndex });

				    spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer devDeptOidToken = new StringTokenizer(devDeptOid, ", ");
				    while (devDeptOidToken.hasMoreTokens()) {
					spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id",
					        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(devDeptOidToken.nextToken())),
					        new int[] { linkIndex });
					if (devDeptOidToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // long deptid = CommonUtil.getOIDLongValue(devDeptOid);
				    // spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=",
				    // deptid), new int[] {linkIndex});
				}

				if (productpjtNo != null && productpjtNo.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, ProductProject.class, index_productProject,
					    ProductProject.PJT_NO, productpjtNo, true);

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }
				    // ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // productpjtNo = StringUtil.changeString(productpjtNo, "*", "%");
				    // ColumnExpression ce = ConstantExpression.newExpression(productpjtNo);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {index_productProject});
				    Kogger.debug(SearchPagingProjectHelper.class, "productpjtNo == " + productpjtNo);
				    // setUpperNoneLikeCondition(spec, ProductProject.class, index_productProject, ProductProject.PJT_NO,
				    // productpjtNo.trim() );

				}
				// Part Name
				if (productName != null && productName.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NAME,
					    productName, false);

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }
				    // ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // productName = StringUtil.changeString(productName, "*", "%");
				    // ColumnExpression ce = ConstantExpression.newExpression(productName);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {index_item });
				    // Kogger.debug(getClass(), "productName == " + productName);
				    // setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NAME,
				    // productName.trim() );

				}
				// Part No
				if (productPartNo != null && productPartNo.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NO,
					    productPartNo, false);

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }

				    // ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // productPartNo = StringUtil.changeString(productPartNo, "*", "%");
				    // ColumnExpression ce = ConstantExpression.newExpression(productPartNo);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {index_item });
				    // Kogger.debug(getClass(), "productPartNo == " + productPartNo);
				    // setUpperNoneLikeCondition(spec, MoldItemInfo.class, index_item, MoldItemInfo.PART_NO,
				    // productPartNo.trim() );
				}

				// 금형구분
				if (itemType != null && itemType.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer itemTypeToken = new StringTokenizer(itemType, ", ");
				    while (itemTypeToken.hasMoreTokens()) {
					spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE,
					        SearchCondition.EQUAL, itemTypeToken.nextToken()), new int[] { index_item });
					if (itemTypeToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }
				    // spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", itemType), new
				    // int[]{index_item});
				}
				

				// 금형분류
				if (moldProductType != null && moldProductType.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer moldProductTypeToken = new StringTokenizer(moldProductType, ", ");
				    while (moldProductTypeToken.hasMoreTokens()) {
					spec.appendWhere(
					        new SearchCondition(MoldItemInfo.class, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id",
					                SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                        .getNumberCode("MOLDPRODUCTSTYPE", moldProductTypeToken.nextToken()))),
					        new int[] { index_item });
					if (moldProductTypeToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.PRODUCT_TYPE_REFERENCE +
				    // ".key.id", "MOLDPRODUCTSTYPE", moldProductType );
				}

				// 제작구분
				if (making != null && making.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer makingToken = new StringTokenizer(making, ", ");
				    while (makingToken.hasMoreTokens()) {
					spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING,
					        SearchCondition.EQUAL, makingToken.nextToken()), new int[] { index_item });
					if (makingToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // if(spec.getConditionCount() > 0) {
				    // spec.appendAnd();
				    // }
				    // spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, "=", making), new
				    // int[]{index_item});
				}

				// 금형유형
				if (moldType != null && moldType.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer moldTypeToken = new StringTokenizer(moldType, ", ");
				    while (moldTypeToken.hasMoreTokens()) {
					spec.appendWhere(
					        new SearchCondition(MoldItemInfo.class, MoldItemInfo.MOLD_TYPE_REFERENCE + ".key.id",
					                SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                        .getNumberCode("MOLDTYPE", moldTypeToken.nextToken()))),
					        new int[] { index_item });
					if (moldTypeToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.MOLD_TYPE_REFERENCE +
				    // ".key.id", "MOLDTYPE", moldType );
				}

				// 제작처
				if (outsourcing != null && outsourcing.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    // ClassAttribute mca = new ClassAttribute(MoldProject.class, MoldProject.OUT_SOURCING);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // outsourcing = StringUtil.changeString(outsourcing, "*", "%");
				    // ColumnExpression ce = ConstantExpression.newExpression(outsourcing);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {idx_target});

				    // * 검색, 콤마검색
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, MoldProject.class, idx_target, MoldProject.OUT_SOURCING,
					    outsourcing, true);
				}

				// Kogger.debug(getClass(), "carTypeInfo2 = " + carTypeInfo2);
				if (carTypeInfo2 != null && carTypeInfo2.length() > 0) {
				    if (spec.getConditionCount() > 0) {
					// spec.appendAnd();
				    }
				    // ClassAttribute mca = new ClassAttribute(OEMProjectType.class, OEMProjectType.NAME);
				    // SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
				    // carTypeInfo2 = StringUtil.changeString(carTypeInfo2, "*", "%");
				    // carTypeInfo2 = carTypeInfo2.toUpperCase();
				    Class<OEMProjectType> oemLink = OEMProjectType.class;
				    int idx_oem = spec.appendClassList(oemLink, false);
				    SearchCondition where = null;
				    where = new SearchCondition(new ClassAttribute(ProductProject.class, "oemTypeReference.key.id"), "=",
					    new ClassAttribute(oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
				    where.setFromIndicies(new int[] { index_productProject, idx_oem }, 0);
				    where.setOuterJoin(0);
				    spec.appendAnd();
				    spec.appendWhere(where, new int[] { index_productProject, idx_oem });

				    spec.appendAnd();
				    KETQueryUtil.setQuerySpecForMultiSearch(spec, OEMProjectType.class, idx_oem, OEMProjectType.NAME,
					    carTypeInfo2, true);

				    // ColumnExpression ce = ConstantExpression.newExpression(carTypeInfo2);
				    // SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				    // spec.appendWhere(dsc, new int[] {idx_oem});

				}

				// 개발담당자
				if (setProductPm != null && setProductPm.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    Class<ProjectMemberLink> prjectMember = ProjectMemberLink.class;
				    int idx_Member = spec.appendClassList(prjectMember, false);

				    ClassAttribute mca1 = null;
				    ClassAttribute mca2 = null;

				    mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
				    mca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
				    SearchCondition msc = new SearchCondition(mca1, "=", mca2);
				    msc.setFromIndicies(new int[] { index_productProject, idx_Member }, 0);
				    msc.setOuterJoin(0);
				    spec.appendWhere(msc, new int[] { index_productProject, idx_Member });

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    msc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
					    ProjectUserHelper.PM);
				    spec.appendWhere(msc, new int[] { idx_Member });

				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer setProductPmToken = new StringTokenizer(setProductPm, ", ");
				    while (setProductPmToken.hasMoreTokens()) {
					spec.appendWhere(new SearchCondition(prjectMember, "roleBObjectRef.key.id", SearchCondition.EQUAL,
					        CommonUtil.getOIDLongValue(setProductPmToken.nextToken())), new int[] { idx_Member });
					if (setProductPmToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // if(spec.getConditionCount() > 0)
				    // spec.appendAnd();
				    //
				    // long userId = CommonUtil.getOIDLongValue(setProductPm);
				    // msc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, userId);
				    // spec.appendWhere(msc , new int[]{idx_Member});
				}

				if (developType2 != null && developType2.length() > 0) {
				    if (spec.getConditionCount() > 0)
					spec.appendAnd();

				    spec.appendOpenParen();
				    StringTokenizer developTypeToken = new StringTokenizer(developType2, ", ");
				    while (developTypeToken.hasMoreTokens()) {
					spec.appendWhere(
					        new SearchCondition(ProductProject.class, "developentTypeReference.key.id",
					                SearchCondition.EQUAL, CommonUtil.getOIDLongValue(NumberCodeHelper.manager
					                        .getNumberCode("DEVELOPENTTYPE", developTypeToken.nextToken()))),
					        new int[] { index_productProject });
					if (developTypeToken.hasMoreTokens())
					    spec.appendOr();
				    }
				    spec.appendCloseParen();

				    // setNumberCodeQuery(spec, ProductProject.class, index_productProject,
				    // "developentTypeReference.key.id", "DEVELOPENTTYPE", developType2);

				}
				
				// 구매품 여부
                if("Y".equals(isPurchase)) {
                    if (spec.getConditionCount() > 0) spec.appendAnd();
                    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, SearchCondition.EQUAL, "구매품"), new int[] { index_item });
                }else{
                    if (spec.getConditionCount() > 0) spec.appendAnd();
                    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, SearchCondition.NOT_EQUAL , "구매품"), new int[] { index_item });
                }
				
			    }
			}
		    }
		}
	    }

	    /*
	     * 최신 프로젝트
	     */

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { idx_target });
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	    // ///////////////////////////////////////////////////////////////////
	    // Sub Query End mainSpec
	    // ///////////////////////////////////////////////////////////////////

	    SubSelectExpression subfrom = new SubSelectExpression(spec);
	    subfrom.setFromAlias(new String[] { "B0" }, 0);
	    int sub_pjt_index = mainSpec.appendFrom(subfrom);

	    if (mainSpec.getConditionCount() > 0)
		mainSpec.appendAnd();

	    SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		    new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
	    sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	    sc.setOuterJoin(0);
	    mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	    int sortIdx = 0;
	    String sort = (String) map.get("sortKey");
	    String isDesc = (String) map.get("dsc");
	    // Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);

	    if ((sort != null) && (sort.trim().length() > 0)) {
		if ((isDesc == null) || (isDesc.trim().length() == 0)) {
		    isDesc = "FALSE";
		}

		if (sort.equals(E3PSProject.PJT_NAME) || sort.equals("state.state") || sort.equals(E3PSProject.PJT_NO)) {
		    SearchUtil.setOrderBy(mainSpec, target, main_idx, sort, "m_Sort" + sortIdx++, "TRUE".equals(isDesc.toUpperCase()));
		}

		if (sort.equals("planEndDate") || sort.equals("planStartDate")) {
		    if (mainSpec.getConditionCount() > 0)
			mainSpec.appendAnd();

		    Class<ExtendScheduleData> schedule2 = ExtendScheduleData.class;
		    int idx_schedule2 = mainSpec.appendClassList(schedule2, false);

		    ClassAttribute ca1 = null;
		    ClassAttribute ca2 = null;

		    ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
		    ca2 = new ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME);
		    SearchCondition sc2 = new SearchCondition(ca1, "=", ca2);
		    sc2.setFromIndicies(new int[] { main_idx, idx_schedule2 }, 0);
		    sc2.setOuterJoin(0);
		    mainSpec.appendWhere(sc2, new int[] { main_idx, idx_schedule2 });

		    SearchUtil.setOrderBy(mainSpec, schedule2, idx_schedule2, sort, "m_Sort" + sortIdx++,
			    "TRUE".equals(isDesc.toUpperCase()));
		}

		if (sort.equals("moldPartName") || sort.equals("moldPartNo") || sort.equals("making")) {
		    int index_item = mainSpec.addClassList(MoldItemInfo.class, false);
		    // int index_productProject = mainSpec.addClassList(ProductProject.class, false);

		    ClassAttribute ca0 = new ClassAttribute(MoldProject.class, MoldProject.MOLD_INFO_REFERENCE + ".key.id");
		    ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);

		    SearchCondition msc = new SearchCondition(ca0, "=", ca1);
		    msc.setFromIndicies(new int[] { main_idx, index_item }, 0);
		    msc.setOuterJoin(0);

		    // Kogger.debug(getClass(), "index_item === " + index_item);

		    if (mainSpec.getConditionCount() > 0) {
			mainSpec.appendAnd();
		    }

		    mainSpec.appendWhere(msc, new int[] { main_idx, index_item });

		    String sortKey = "";
		    if (sort.equals("moldPartNo")) {
			sortKey = MoldItemInfo.PART_NO;
		    } else if (sort.equals("moldPartName")) {
			sortKey = MoldItemInfo.PART_NAME;
		    } else if (sort.equals("making")) {
			sortKey = MoldItemInfo.MAKING;
		    }

		    SearchUtil.setOrderBy(mainSpec, MoldItemInfo.class, index_item, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(isDesc.toUpperCase()));
		}
	    }

	    // 디폴트 생성 일자
	    SearchUtil.setOrderBy(mainSpec, target, main_idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "modifyStamp", true);
	    Kogger.debug(SearchPagingProjectHelper.class, mainSpec);

	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}

	// Kogger.debug(getClass(), "project Search spec ===>"+mainSpec);
	return mainSpec;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMyE3PSProjectQuerySpec
    // Sang Min, Kim
    // smkim@e3ps.com
    // 추가
    // ////////////////////////////////////////////////////////////////////////////

    public static StatementSpec getMyE3PSProjectQuerySpec(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyE3PSProjectQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (StatementSpec) obj;
	}

	CompoundQuerySpec compound = null;
	try {
	    // Kogger.debug(getClass(), "##################################################################");

	    Class targetClass[] = { ReviewProject.class, ProductProject.class, MoldProject.class };
	    compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION);
	    compound.setAdvancedQueryEnabled(true);

	    Vector attribute = new Vector();

	    ClassAttribute ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.OID_CLASSNAME);
	    ca.setColumnAlias("OID_CLASSNAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("ID_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_TYPE_NAME);
	    ca.setColumnAlias("PJT_TYPE_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NO);
	    ca.setColumnAlias("PJT_NO");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NAME);
	    ca.setColumnAlias("PJT_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE);
	    ca.setColumnAlias("PLAN_START_DATE");
	    attribute.add(new ClassAttributeData(ca, 1));

	    ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE);
	    ca.setColumnAlias("PLAN_END_DATE");
	    attribute.add(new ClassAttributeData(ca, 1));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.LIFE_CYCLE_STATE);
	    ca.setColumnAlias("LIFE_CYCLE_STATE");
	    attribute.add(new ClassAttributeData(ca, 0));

	    QuerySpec subQuery = myProjectSubQuery(map);

	    for (int i = 0; i < targetClass.length; i++) {
		QuerySpec mainSpec = new QuerySpec();
		mainSpec.setAdvancedQueryEnabled(true);
		Class main_target = targetClass[i];
		// int main_idx = 0;

		E3PSClassTableExpression tableExpression = new E3PSClassTableExpression(main_target);
		// cc.setAccessControlRequired(false);
		int main_idx = mainSpec.appendFrom(tableExpression);
		int idx_schedule = mainSpec.appendClassList(ExtendScheduleData.class, false);

		ClassAttribute ca1 = null;
		ClassAttribute ca2 = null;

		ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
		ca2 = new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME);
		SearchCondition sc1 = new SearchCondition(ca1, "=", ca2);
		sc1.setFromIndicies(new int[] { main_idx, idx_schedule }, 0);
		sc1.setOuterJoin(0);
		mainSpec.appendWhere(sc1, new int[] { main_idx, idx_schedule });

		for (int j = 0; j < attribute.size(); j++) {
		    ClassAttributeData attrData = (ClassAttributeData) attribute.get(j);
		    mainSpec.appendSelect(attrData.ca, new int[] { attrData.index }, false);
		}

		// mainSpec.addClassList(main_target, true);

		if (!mainSpec.isAdvancedQueryEnabled()) {
		    mainSpec.setAdvancedQueryEnabled(true);
		}
		// ////////// Sub Query Start mainSpec

		SubSelectExpression subfrom = new SubSelectExpression(subQuery);
		subfrom.setFromAlias(new String[] { "B0" }, 0);
		// subfrom.set

		int sub_pjt_index = mainSpec.appendFrom(subfrom);

		if (mainSpec.getConditionCount() > 0) {
		    mainSpec.appendAnd();
		}

		SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		        new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
		sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
		sc.setOuterJoin(0);
		mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

		compound.addComponent(mainSpec);

	    }
	    String sortKey = ParamUtil.getStrParameter((String) map.get("sortKey"));
	    String dsc = ParamUtil.getStrParameter((String) map.get("dsc"));
	    if ((dsc == null) || (dsc.trim().length() == 0)) {
		dsc = "FALSE";
	    }
	    ClassAttribute sortCa = null;
	    for (int i = 0; i < attribute.size(); i++) {
		ClassAttributeData attrData = (ClassAttributeData) attribute.get(i);

		if (sortKey.equals(attrData.ca.getColumnAlias())) {
		    sortCa = attrData.ca;
		    break;
		}
	    }
	    if (sortCa != null) {
		OrderBy orderby = new OrderBy(sortCa, "TRUE".equals(dsc.toUpperCase()));
		compound.appendOrderBy(orderby);
	    }

	    /*
	     * PagingQueryResult rs = PagingSessionHelper.openPagingSession (1 ,10 ,compound ); //PagingQueryResult rs =
	     * SearchPagingProjectHelper.openPagingSession(compound, 0, 10); //QueryResult rs = PersistenceHelper.manager.find(compound);
	     * while(rs.hasMoreElements()){ Object o[] = (Object[])rs.nextElement(); Kogger.debug(getClass(), "ggg = " + o.length); for(int
	     * i = 0; i < o.length; i++){ Kogger.debug(getClass(), o[i].getClass().getName() + " == " + o[i]); } }
	     */

	    /*
	     * String sortKey = ParamUtil.getStrParameter((String)map.get("sortKey")); String dsc =
	     * ParamUtil.getStrParameter((String)map.get("dsc")); //Kogger.debug(getClass(), "sortKey==>"+ sortKey);
	     * //Kogger.debug(getClass(), "dsc==>"+ dsc); int sortIdx = 0; if((sortKey != null) && (sortKey.trim().length() > 0)) { if((dsc
	     * == null) || (dsc.trim().length() == 0)) { dsc = "FALSE"; } if(sortKey.equals(E3PSProject.PJT_NAME) ||
	     * sortKey.equals("state.state") || sortKey.equals(E3PSProject.PJT_NO)){ SearchUtil.setOrderBy( mainSpec, E3PSProject.class,
	     * idx_target, sortKey, "m_Sort"+ sortIdx++ , "TRUE".equals(dsc.toUpperCase())); } if(sortKey.equals("planEndDate") ||
	     * sortKey.equals("planStartDate") ){ if(mainSpec.getConditionCount() > 0) mainSpec.appendAnd(); Class schedule2 =
	     * ExtendScheduleData.class; int idx_schedule2 = mainSpec.appendClassList(schedule2, false); ClassAttribute ca1 = null;
	     * ClassAttribute ca2 = null; ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id"); ca2 = new
	     * ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME); SearchCondition sc3 = new SearchCondition(ca1, "=", ca2);
	     * sc3.setFromIndicies(new int[]{main_idx, idx_schedule2}, 0); sc3.setOuterJoin(2); mainSpec.appendWhere(sc3, new
	     * int[]{main_idx, idx_schedule2}); SearchUtil.setOrderBy(mainSpec, schedule2, idx_schedule2, sortKey, "m_Sort"+ sortIdx++ ,
	     * "TRUE".equals(dsc.toUpperCase())); } //if(sortKey.equals(MoldItemInfo.DIE_NO) ){ //}
	     * //if(sortKey.equals(wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME) ){ // SearchUtil.setOrderBy(spec, E3PSProject.class,
	     * idx_target, wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME, "modifyStamp", true); //} } //Kogger.debug(getClass(), mainSpec);
	     * SearchUtil.setOrderBy(mainSpec, target, main_idx, wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME, "modifyStamp", false);
	     */
	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}

	return compound;
    }

    private static QuerySpec myProjectSubQuery(HashMap map) throws Exception {
	QuerySpec spec = null;
	spec = new QuerySpec();
	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	}

	Class target = E3PSProject.class;
	int idx_target = spec.addClassList(target, false);

	spec.setDistinct(true);
	ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
	ca.setColumnAlias("projectId");
	spec.appendSelect(ca, true);

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	if ((String) map.get("user") != null && ((String) map.get("user")).length() > 0)
	    wtuser = (WTUser) CommonUtil.getObject((String) map.get("user"));
	long lperistable = CommonUtil.getOIDLongValue(wtuser);

	Class linkClass = ProjectMemberLink.class;
	int idx_memberLink = spec.addClassList(linkClass, false);
	ClassAttribute cat1 = null;
	ClassAttribute cat2 = null;

	cat1 = new ClassAttribute(linkClass, "roleAObjectRef.key.id");
	cat2 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
	SearchCondition sc2 = new SearchCondition(cat1, SearchCondition.EQUAL, cat2);
	sc2.setFromIndicies(new int[] { idx_memberLink, idx_target }, 0);
	sc2.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	spec.appendWhere(sc2, new int[] { idx_memberLink, idx_target });

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(linkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, lperistable),
	        new int[] { idx_memberLink });

	String command = StringUtil.checkNull((String) map.get("command"));
	String searchPjtName = StringUtil.checkNull((String) map.get("searchPjtName"));
	String searchPjtNo = StringUtil.checkNull((String) map.get("searchPjtNo"));
	String searchPjtState = StringUtil.checkNull((String) map.get("searchPjtState"));
	String searchPjtType = StringUtil.checkNull((String) map.get("searchPjtType"));
	if (command.length() > 0) {

	    // 프로젝트
	    if (searchPjtName.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName),
		        new int[] { idx_target });
		searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
	    }
	    // 프로젝트 번호
	    if (searchPjtNo.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo),
		        new int[] { idx_target });
		searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
	    }

	    // 프로젝트 타입
	    if (searchPjtType.length() > 0) {
		if (searchPjtType != null && searchPjtType.trim().length() > 0 && !searchPjtType.equalsIgnoreCase("null")) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    StringTokenizer pjtTypeToken = new StringTokenizer(searchPjtType, ",");
		    while (pjtTypeToken.hasMoreTokens()) {
			spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_TYPE_NAME, SearchCondition.EQUAL,
			        pjtTypeToken.nextToken()), new int[] { idx_target });
			if (pjtTypeToken.hasMoreTokens())
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		}
	    }

	    // 프로젝트 상태
	    if (searchPjtState.length() > 0) {
		if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendOpenParen();
		    StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
		    while (pjtStateToken.hasMoreTokens()) {
			spec.appendWhere(
			        new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, pjtStateToken.nextToken()),
			        new int[] { idx_target });
			if (pjtStateToken.hasMoreTokens())
			    spec.appendOr();
		    }
		    spec.appendCloseParen();
		}
	    }

	    String planStartStartDate = StringUtil.checkNull((String) map.get("planStartStartDate"));
	    String planStartEndDate = StringUtil.checkNull((String) map.get("planStartEndDate"));
	    String planEndStartDate = StringUtil.checkNull((String) map.get("planEndStartDate"));
	    String planEndEndDate = StringUtil.checkNull((String) map.get("planEndEndDate"));

	    if ((planStartStartDate != null && planStartStartDate.length() > 0)
		    || (planStartEndDate != null && planStartEndDate.length() > 0)
		    || (planEndStartDate != null && planEndStartDate.length() > 0)
		    || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		int schedIndex = spec.appendClassList(ExtendScheduleData.class, false);
		SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id"), "=",
		        new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
		exsc.setFromIndicies(new int[] { idx_target, schedIndex }, 0);
		exsc.setOuterJoin(0);
		spec.appendWhere(exsc, new int[] { idx_target, schedIndex });

		if (planStartStartDate != null && planStartStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
			    new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,
			    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planStartEndDate != null && planStartEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
			    convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndStartDate != null && planEndStartDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate, new ParsePosition(0))
			    .getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE,
			    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}
		if (planEndEndDate != null && planEndEndDate.length() > 0) {
		    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(planEndEndDate + ":23-59-59",
			    new ParsePosition(0)).getTime()));

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();

		    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN,
			    convertDate);
		    spec.appendWhere(exsc, new int[] { schedIndex });
		}

	    }

	}

	// //////////Sub Query End mainSpec
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { idx_target });
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	return spec;
    }

    private static QuerySpec myProjectSubQuery(List<Map<String, Object>> conditionList) throws Exception {
	QuerySpec spec = null;
	spec = new QuerySpec();
	if (!spec.isAdvancedQueryEnabled()) {
	    spec.setAdvancedQueryEnabled(true);
	}

	Class target = E3PSProject.class;
	int idx_target = spec.addClassList(target, false);

	spec.setDistinct(true);
	ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
	ca.setColumnAlias("projectId");
	spec.appendSelect(ca, true);

	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    if ((String) map.get("user") != null && ((String) map.get("user")).length() > 0)
		wtuser = (WTUser) CommonUtil.getObject((String) map.get("user"));
	    long lperistable = CommonUtil.getOIDLongValue(wtuser);

	    Class linkClass = ProjectMemberLink.class;
	    int idx_memberLink = spec.addClassList(linkClass, false);
	    ClassAttribute cat1 = null;
	    ClassAttribute cat2 = null;

	    cat1 = new ClassAttribute(linkClass, "roleAObjectRef.key.id");
	    cat2 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
	    SearchCondition sc2 = new SearchCondition(cat1, SearchCondition.EQUAL, cat2);
	    sc2.setFromIndicies(new int[] { idx_memberLink, idx_target }, 0);
	    sc2.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	    spec.appendWhere(sc2, new int[] { idx_memberLink, idx_target });

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(linkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, lperistable),
		    new int[] { idx_memberLink });

	    String command = StringUtil.checkNull((String) map.get("command"));
	    String searchPjtName = StringUtil.checkNull((String) map.get("searchPjtName"));
	    String searchPjtNo = StringUtil.checkNull((String) map.get("searchPjtNo"));
	    String searchPjtState = StringUtil.checkNull((String) map.get("searchPjtState"));
	    String searchPjtType = StringUtil.checkNull((String) map.get("searchPjtType"));
	    if (command.length() > 0) {

		// 프로젝트
		if (searchPjtName.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		    spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName),
			    new int[] { idx_target });
		    searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
		}
		// 프로젝트 번호
		if (searchPjtNo.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		    spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo),
			    new int[] { idx_target });
		    searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
		}

		// 프로젝트 타입
		if (searchPjtType.length() > 0) {
		    if (searchPjtType != null && searchPjtType.trim().length() > 0 && !searchPjtType.equalsIgnoreCase("null")) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();
			spec.appendOpenParen();
			StringTokenizer pjtTypeToken = new StringTokenizer(searchPjtType, ",");
			while (pjtTypeToken.hasMoreTokens()) {
			    spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_TYPE_NAME, SearchCondition.EQUAL,
				    pjtTypeToken.nextToken()), new int[] { idx_target });
			    if (pjtTypeToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();
		    }
		}

		// 프로젝트 상태
		if (searchPjtState.length() > 0) {
		    if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
			if (spec.getConditionCount() > 0)
			    spec.appendAnd();
			spec.appendOpenParen();
			StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
			while (pjtStateToken.hasMoreTokens()) {
			    spec.appendWhere(
				    new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, pjtStateToken.nextToken()),
				    new int[] { idx_target });
			    if (pjtStateToken.hasMoreTokens())
				spec.appendOr();
			}
			spec.appendCloseParen();
		    }
		}

		String planStartStartDate = StringUtil.checkNull((String) map.get("planStartStartDate"));
		String planStartEndDate = StringUtil.checkNull((String) map.get("planStartEndDate"));
		String planEndStartDate = StringUtil.checkNull((String) map.get("planEndStartDate"));
		String planEndEndDate = StringUtil.checkNull((String) map.get("planEndEndDate"));

		if ((planStartStartDate != null && planStartStartDate.length() > 0)
		        || (planStartEndDate != null && planStartEndDate.length() > 0)
		        || (planEndStartDate != null && planEndStartDate.length() > 0)
		        || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    int schedIndex = spec.appendClassList(ExtendScheduleData.class, false);
		    SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id"), "=",
			    new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
		    exsc.setFromIndicies(new int[] { idx_target, schedIndex }, 0);
		    exsc.setOuterJoin(0);
		    spec.appendWhere(exsc, new int[] { idx_target, schedIndex });

		    if (planStartStartDate != null && planStartStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
			        new ParsePosition(0)).getTime()));

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,
			        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			spec.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planStartEndDate != null && planStartEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
			        convertDate);
			spec.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planEndStartDate != null && planEndStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
			        new ParsePosition(0)).getTime()));

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE,
			        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			spec.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planEndEndDate != null && planEndEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			if (spec.getConditionCount() > 0)
			    spec.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN,
			        convertDate);
			spec.appendWhere(exsc, new int[] { schedIndex });
		    }
		}
	    }
	}

	// //////////Sub Query End mainSpec
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { idx_target });
	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });

	return spec;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMyTaskQuerySpec
    // Sang Min, Kim
    // smkim@e3ps.com
    // 추가
    // ////////////////////////////////////////////////////////////////////////////

    public static QuerySpec getMyTaskQuerySpec(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyE3PSProjectQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec mainSpec = null;
	try {
	    // Kogger.debug(getClass(), "##################################################################");
	    mainSpec = new QuerySpec();
	    Class main_target = E3PSTask.class;
	    int main_idx = mainSpec.addClassList(main_target, true);

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }
	    // ////////// Sub Query Start mainSpec
	    QuerySpec qs = new QuerySpec();
	    /*
	     * Class target = E3PSProject.class; int idx_target = qs.addClassList(target, false); qs.setDistinct(true); ClassAttribute ca =
	     * new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME); ca.setColumnAlias("projectId");
	     * qs.appendSelect(ca, true);
	     */

	    if (!qs.isAdvancedQueryEnabled()) {
		qs.setAdvancedQueryEnabled(true);
	    }

	    int taskMemberlink_idx = qs.appendClassList(TaskMemberLink.class, false);
	    int projectMemberlink_idx = qs.appendClassList(ProjectMemberLink.class, false);

	    int project_idxx = qs.appendClassList(E3PSProject.class, false);
	    int task_idxx = qs.appendClassList(E3PSTask.class, false);
	    int schedIndex = qs.appendClassList(ExtendScheduleData.class, false);
	    // int master_idxx = qs.appendClassList(E3PSProjectMaster.class, false);

	    // ClassInfo classinfo = WTIntrospector.getClassInfo(TaskMemberLink.class);
	    // DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    // BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();

	    // String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    // String objid = basetableinfo.getColumnDescriptor(wt.util.WTAttributeNameIfc.ID_NAME).getColumnName();

	    qs.setDistinct(true);
	    /*
	     * KeywordExpression ke = new KeywordExpression(qs.getFromClause().getAliasAt(task_idxx) + "." + objname + "||':'||" +
	     * qs.getFromClause().getAliasAt(task_idxx) + "." + objid); ke.setColumnAlias("taskOid"); qs.appendSelect(ke, new
	     * int[]{task_idxx}, false);
	     */
	    ClassAttribute ca = new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("taskOid");
	    qs.appendSelect(ca, true);

	    SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
		    ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    exsc.setFromIndicies(new int[] { task_idxx, schedIndex }, 0);
	    exsc.setOuterJoin(0);
	    qs.appendWhere(exsc, new int[] { task_idxx, schedIndex });
	    /*
	     * qs.appendAnd(); SearchCondition mastersc = new SearchCondition(new ClassAttribute(E3PSProject.class,
	     * "masterReference.key.id"), "=", new ClassAttribute(E3PSProjectMaster.class, wt.util.WTAttributeNameIfc.ID_NAME));
	     * mastersc.setFromIndicies(new int[]{project_idxx, master_idxx}, 0); mastersc.setOuterJoin(0); qs.appendWhere(mastersc, new
	     * int[]{project_idxx, master_idxx});
	     */

	    qs.appendAnd();
	    SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    tpsc.setFromIndicies(new int[] { task_idxx, project_idxx }, 0);
	    tpsc.setOuterJoin(0);
	    qs.appendWhere(tpsc, new int[] { task_idxx, project_idxx });
	    qs.appendAnd();
	    SearchCondition ttl_sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
	    ttl_sc.setFromIndicies(new int[] { task_idxx, taskMemberlink_idx }, 0);
	    ttl_sc.setOuterJoin(0);
	    qs.appendWhere(ttl_sc, new int[] { task_idxx, taskMemberlink_idx });

	    // roleBObjectRef.key.id
	    qs.appendAnd();
	    SearchCondition ptsc = new SearchCondition(new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id"), "=",
		    new ClassAttribute(ProjectMemberLink.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    ptsc.setFromIndicies(new int[] { taskMemberlink_idx, projectMemberlink_idx }, 0);
	    ptsc.setOuterJoin(0);
	    qs.appendWhere(ptsc, new int[] { taskMemberlink_idx, projectMemberlink_idx });

	    String searchPjtName = StringUtil.checkNull((String) map.get("searchPjtName"));
	    String searchTaskName = StringUtil.checkNull((String) map.get("searchTaskName"));
	    String searchState = StringUtil.checkNull((String) map.get("searchState"));
	    String searchPjtState = StringUtil.checkNull((String) map.get("searchPjtState"));
	    String searchPjtNo = StringUtil.checkNull((String) map.get("searchPjtNo"));
	    String command = StringUtil.checkNull((String) map.get("command"));
	    String getUser = StringUtil.checkNull((String) map.get("getUser"));
	    String notEqualState = StringUtil.checkNull((String) map.get("notEqualState"));

	    WTUser wtuser = null;
	    if (getUser.length() > 0) {
		wtuser = (WTUser) CommonUtil.getObject(getUser);
	    } else {
		wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    }
	    long oidValue_t = CommonUtil.getOIDLongValue(wtuser);

	    // 태스크 책임자/ 구성원
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue_t),
		        new int[] { projectMemberlink_idx });
	    }

	    if (command.length() > 0) {

		// 프로젝트
		if (searchPjtName.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
		    qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName),
			    new int[] { project_idxx });
		    searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
		}
		// 프로젝트 NO
		if (searchPjtNo.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
		    qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo),
			    new int[] { project_idxx });
		    searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
		}

		// 태스크
		if (searchTaskName.length() > 0) {
		    if (qs.getConditionCount() > 0) {
			qs.appendAnd();
		    }
		    searchTaskName = StringUtil.changeString(searchTaskName, "*", "%");
		    qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, SearchCondition.LIKE, searchTaskName),
			    new int[] { task_idxx });
		    searchTaskName = StringUtil.changeString(searchTaskName, "%", "*");

		}
		// 태스크 완료 여부
		if (searchState != null && searchState.trim().length() > 0 && !searchState.equalsIgnoreCase("null")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    StringTokenizer stateTypeToken = new StringTokenizer(searchState, ",");
		    while (stateTypeToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(E3PSTask.class, "taskState", SearchCondition.EQUAL, Integer.parseInt(stateTypeToken
			                .nextToken())), new int[] { task_idxx });
			if (stateTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		// 취소된 프로젝트 조회대상제외
		if (notEqualState.length() > 0) {
		    qs.appendAnd();
		    qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", SearchCondition.NOT_EQUAL, "WITHDRAWN"),
			    new int[] { project_idxx });
		}

		// 프로젝트 상태
		if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
		    while (pjtStateToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, pjtStateToken.nextToken()),
			        new int[] { project_idxx });
			if (pjtStateToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		// 30일 이내 결과 False
		boolean checked30 = false;
		String predate = StringUtil.checkNull((String) map.get("predate"));
		String postdate = StringUtil.checkNull((String) map.get("postdate"));
		Timestamp todayTime = new Timestamp(System.currentTimeMillis());
		Timestamp limitedTime = new Timestamp(System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L));
		if (predate.length() > 0) {
		    predate = DateUtil.getTimeFormat(limitedTime, "yyyy/MM/dd");
		}
		if (postdate.length() > 0) {
		    postdate = DateUtil.getTimeFormat(todayTime, "yyyy/MM/dd");
		}
		if (checked30) {
		    qs.appendAnd();

		    qs.appendWhere(new SearchCondition(E3PSProject.class, "thePersistInfo.createStamp", ">=", limitedTime),
			    new int[] { project_idxx });
		    if (predate != null && predate.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendWhere(
			        new SearchCondition(ExtendScheduleData.class, "planEndDate", ">=",
			                DateUtil.convertStartDate(predate.trim())), new int[] { schedIndex });
		    }
		    if (postdate != null && postdate.length() > 0) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendWhere(
			        new SearchCondition(ExtendScheduleData.class, "planStartDate", "<",
			                DateUtil.convertEndDate(postdate.trim())), new int[] { schedIndex });
		    }
		}

		String planStartStartDate = StringUtil.checkNull((String) map.get("planStartStartDate"));
		String planStartEndDate = StringUtil.checkNull((String) map.get("planStartEndDate"));
		String planEndStartDate = StringUtil.checkNull((String) map.get("planEndStartDate"));
		String planEndEndDate = StringUtil.checkNull((String) map.get("planEndEndDate"));

		if ((planStartStartDate != null && planStartStartDate.length() > 0)
		        || (planStartEndDate != null && planStartEndDate.length() > 0)
		        || (planEndStartDate != null && planEndStartDate.length() > 0)
		        || (planEndEndDate != null && planEndEndDate.length() > 0)) {

		    if (planStartStartDate != null && planStartStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
			        new ParsePosition(0)).getTime()));

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,
			        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			qs.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planStartEndDate != null && planStartEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN,
			        convertDate);
			qs.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planEndStartDate != null && planEndStartDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
			        new ParsePosition(0)).getTime()));

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE,
			        SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			qs.appendWhere(exsc, new int[] { schedIndex });
		    }
		    if (planEndEndDate != null && planEndEndDate.length() > 0) {
			Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
			        planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			if (qs.getConditionCount() > 0)
			    qs.appendAnd();

			exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN,
			        convertDate);
			qs.appendWhere(exsc, new int[] { schedIndex });
		    }
		}
	    }
	    // 최신 프로젝트
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { project_idxx });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { project_idxx });

	    // ////////// Sub Query End mainSpec
	    SubSelectExpression subfrom = new SubSelectExpression(qs);
	    subfrom.setFromAlias(new String[] { "B0" }, project_idxx);
	    // subfrom.set

	    int sub_pjt_index = mainSpec.appendFrom(subfrom);
	    if (mainSpec.getConditionCount() > 0)
		mainSpec.appendAnd();
	    SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		    new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".taskOid"));
	    sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
	    sc.setOuterJoin(0);
	    mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

	    int project_idxx2 = mainSpec.appendClassList(E3PSProject.class, false);
	    int master_idxx2 = mainSpec.appendClassList(E3PSProjectMaster.class, false);
	    int schedIndex2 = mainSpec.appendClassList(ExtendScheduleData.class, false);

	    if (mainSpec.getConditionCount() > 0) {
		mainSpec.appendAnd();
	    }
	    SearchCondition mastersc2 = new SearchCondition(new ClassAttribute(E3PSProject.class, "masterReference.key.id"), "=",
		    new ClassAttribute(E3PSProjectMaster.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    mastersc2.setFromIndicies(new int[] { project_idxx2, master_idxx2 }, 0);
	    mastersc2.setOuterJoin(0);
	    mainSpec.appendWhere(mastersc2, new int[] { project_idxx2, master_idxx2 });
	    mainSpec.appendAnd();
	    SearchCondition tpsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    tpsc2.setFromIndicies(new int[] { main_idx, project_idxx2 }, 0);
	    tpsc2.setOuterJoin(0);
	    mainSpec.appendWhere(tpsc2, new int[] { main_idx, project_idxx2 });
	    mainSpec.appendAnd();

	    SearchCondition exsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
		    ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    exsc2.setFromIndicies(new int[] { main_idx, schedIndex2 }, 0);
	    exsc2.setOuterJoin(0);
	    mainSpec.appendWhere(exsc2, new int[] { main_idx, schedIndex2 });

	    String sortKey = ParamUtil.getStrParameter((String) map.get("sortKey"));
	    String dsc = ParamUtil.getStrParameter((String) map.get("dsc"));
	    // Kogger.debug(getClass(), "sortKey==>"+ sortKey);
	    // Kogger.debug(getClass(), "dsc==>"+ dsc);

	    int sortIdx = 0;
	    if ((sortKey != null) && (sortKey.trim().length() > 0)) {
		if ((dsc == null) || (dsc.trim().length() == 0)) {
		    dsc = "FALSE";
		}

		if (sortKey.equals(E3PSProjectMaster.PJT_NAME) || sortKey.equals(E3PSProjectMaster.PJT_NO)
		        || sortKey.equals(E3PSProjectMaster.PJT_TYPE_NAME)) {
		    // Kogger.debug(getClass(), "sorkKey = " + sortKey + "gggg = " + "TRUE".equals(dsc.toUpperCase()));
		    // int master_idxx2 = mainSpec.appendClassList(E3PSProjectMaster.class, false);
		    SearchUtil.setOrderBy(mainSpec, E3PSProjectMaster.class, master_idxx2, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(dsc.toUpperCase()));
		}

		if (sortKey.equals("planEndDate") || sortKey.equals("planStartDate")) {
		    SearchUtil.setOrderBy(mainSpec, ExtendScheduleData.class, schedIndex2, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(dsc.toUpperCase()));
		}

		if (sortKey.equals(E3PSTask.TASK_NAME)) {
		    SearchUtil.setOrderBy(mainSpec, E3PSTask.class, main_idx, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(dsc.toUpperCase()));
		}

		if (sortKey.equals(E3PSTask.TASK_STATE)) {
		    SearchUtil.setOrderBy(mainSpec, E3PSTask.class, main_idx, sortKey, "m_Sort" + sortIdx++,
			    "TRUE".equals(dsc.toUpperCase()));
		}

		if (sortKey.equals(MoldItemInfo.DIE_NO)) {

		}
		if (sortKey.equals(wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME)) {
		    SearchUtil.setOrderBy(mainSpec, E3PSProject.class, project_idxx2, wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME,
			    "modifyStamp", true);
		}
	    }

	    SearchUtil.setOrderBy(mainSpec, E3PSTask.class, main_idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
	    // SearchUtil.setOrderBy(mainSpec, E3PSTask.class, main_idx, wt.util.WTAttributeNameIfc.MODIFY_STAMP_NAME, "modifyStamp",
	    // false);
	    // Kogger.debug(getClass(), mainSpec);

	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}

	return mainSpec;
    }

    private static QuerySpec getNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value) {
	try {
	    SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return spec;
    }

    private static boolean isProjectUser(WTUser wtuser, int memberType) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class peopleProjectLinkClass = ProjectMemberLink.class;
	    int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

	    qs.appendWhere(
		    new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType),
		    peopleProjectLinkClassPos);
	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(wtuser);
	    qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    peopleProjectLinkClassPos);
	    QueryResult queryresult = PersistenceHelper.manager.find(qs);
	    if (queryresult != null && queryresult.size() > 0) {
		return true;
	    }
	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return false;
    }

    // My_Project 가져오기
    public static QueryResult myProject() {

	QueryResult qr = null;
	QuerySpec spec = null;
	try {

	    spec = new QuerySpec();

	    Class target = E3PSProject.class;
	    int idx_target = spec.addClassList(target, true);
	    Class wtuserClass = WTUser.class;
	    Class linkClass = ProjectMemberLink.class;
	    int idx_wtuserClass = spec.addClassList(wtuserClass, false);
	    int idx_linkClass = spec.addClassList(linkClass, false);

	    spec.appendJoin(idx_linkClass, ProjectMemberLink.PROJECT_ROLE, idx_target);
	    spec.appendJoin(idx_linkClass, ProjectMemberLink.MEMBER_ROLE, idx_wtuserClass);
	    spec.appendWhere(new SearchCondition(linkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
		    ProjectUserHelper.MEMBER), new int[] { idx_linkClass });
	    spec.appendAnd();
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    long lperistable = CommonUtil.getOIDLongValue(wtuser);
	    spec.appendWhere(new SearchCondition(wtuserClass, wt.util.WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, lperistable),
		    new int[] { idx_wtuserClass });

	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { idx_target });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx_target });
	    Kogger.debug(SearchPagingProjectHelper.class, "My_Project : " + spec);

	    qr = PersistenceHelper.manager.find(spec);

	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}

	return qr;
    }

    public static void setUpperNLikeCondition(QuerySpec spec, Class target, int index, String column, String value) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { QuerySpec.class, Class.class, int.class, String.class, String.class };
	    Object args[] = new Object[] { spec, target, new Integer(index), column, value };
	    try {
		wt.method.RemoteMethodServer.getDefault().invoke("setUpperNLikeCondition", "e3ps.project.beans.SearchPagingProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    }
	    return;
	}

	try {
	    if (value != null && (value.trim()).length() > 0) {
		if (!spec.isAdvancedQueryEnabled()) {
		    spec.setAdvancedQueryEnabled(true);
		}

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		ClassAttribute ca = new ClassAttribute(target, column);
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
		ColumnExpression ce = ConstantExpression.newExpression("%" + value.toUpperCase() + "%");
		SearchCondition sc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		spec.appendWhere(sc, new int[] { index });
	    }
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
    }

    public static void setUpperNoneLikeCondition(QuerySpec spec, Class target, int index, String column, String value) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { QuerySpec.class, Class.class, int.class, String.class, String.class };
	    Object args[] = new Object[] { spec, target, new Integer(index), column, value };
	    try {
		wt.method.RemoteMethodServer.getDefault().invoke("setUpperNoneLikeCondition",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    }
	    return;
	}

	try {
	    if (value != null && (value.trim()).length() > 0) {
		if (!spec.isAdvancedQueryEnabled()) {
		    spec.setAdvancedQueryEnabled(true);
		}

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		ClassAttribute ca = new ClassAttribute(target, column);
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
		ColumnExpression ce = ConstantExpression.newExpression(value.toUpperCase());
		SearchCondition sc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce);
		spec.appendWhere(sc, new int[] { index });
	    }
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
    }

    public static void SearchProjectNo(QuerySpec qs, int idx, String pjtNo) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { QuerySpec.class, int.class, String.class, String.class };
	    Object args[] = new Object[] { qs, new Integer(idx), pjtNo };
	    try {
		wt.method.RemoteMethodServer.getDefault().invoke("SearchProjectNo", "e3ps.project.beans.SearchPagingProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
	    }
	    return;
	}

	try {
	    if (pjtNo != null && (pjtNo.trim()).length() > 0) {
		if (!qs.isAdvancedQueryEnabled()) {
		    qs.setAdvancedQueryEnabled(true);
		}

		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		int idxpt = qs.appendClassList(E3PSProject.class, false);
		SearchCondition sc = new SearchCondition(new ClassAttribute(ProductInfo.class, "projectReference.key.id"), "=",
		        new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
		qs.appendWhere(sc, new int[] { idx, idxpt });

		qs.appendAnd();

		SearchCondition sc2 = new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, "=", pjtNo);
		qs.appendWhere(sc2, new int[] { idxpt });
	    }
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
    }

    private static void setNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value) throws WTException {
	if ((value == null) || (value.trim().length() == 0)) {
	    return;
	}
	SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
    }

    public static QueryResult getMyTaskList(List<Map<String, Object>> conditionList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { List.class };
	    Object args[] = new Object[] { conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyTaskList", "e3ps.project.beans.SearchPagingProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}
	QueryResult result = null;
	try {
	    StatementSpec qs = getMyTaskQuerySpec(conditionList);
	    result = PersistenceHelper.manager.find(qs);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    public static QuerySpec getMyTaskQuerySpec(List<Map<String, Object>> conditionList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { List.class };
	    Object args[] = new Object[] { conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyTaskQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QuerySpec) obj;
	}

	QuerySpec mainSpec = null;
	try {
	    mainSpec = new QuerySpec();
	    Class main_target = E3PSTask.class;
	    int main_idx = mainSpec.addClassList(main_target, true);

	    if (!mainSpec.isAdvancedQueryEnabled()) {
		mainSpec.setAdvancedQueryEnabled(true);
	    }
	    // ////////// Sub Query Start mainSpec
	    QuerySpec qs = new QuerySpec();

	    if (!qs.isAdvancedQueryEnabled()) {
		qs.setAdvancedQueryEnabled(true);
	    }

	    int taskMemberlink_idx = qs.appendClassList(TaskMemberLink.class, false);
	    int projectMemberlink_idx = qs.appendClassList(ProjectMemberLink.class, false);

	    int project_idxx = qs.appendClassList(E3PSProject.class, false);
	    int task_idxx = qs.appendClassList(E3PSTask.class, false);
	    int schedIndex = qs.appendClassList(ExtendScheduleData.class, false);
	    qs.setDistinct(true);
	    ClassAttribute ca = new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("taskOid");
	    qs.appendSelect(ca, true);

	    SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
		    ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    exsc.setFromIndicies(new int[] { task_idxx, schedIndex }, 0);
	    exsc.setOuterJoin(0);
	    qs.appendWhere(exsc, new int[] { task_idxx, schedIndex });

	    qs.appendAnd();
	    SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    tpsc.setFromIndicies(new int[] { task_idxx, project_idxx }, 0);
	    tpsc.setOuterJoin(0);
	    qs.appendWhere(tpsc, new int[] { task_idxx, project_idxx });
	    qs.appendAnd();
	    SearchCondition ttl_sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
	    ttl_sc.setFromIndicies(new int[] { task_idxx, taskMemberlink_idx }, 0);
	    ttl_sc.setOuterJoin(0);
	    qs.appendWhere(ttl_sc, new int[] { task_idxx, taskMemberlink_idx });

	    qs.appendAnd();
	    SearchCondition ptsc = new SearchCondition(new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id"), "=",
		    new ClassAttribute(ProjectMemberLink.class, wt.util.WTAttributeNameIfc.ID_NAME));
	    ptsc.setFromIndicies(new int[] { taskMemberlink_idx, projectMemberlink_idx }, 0);
	    ptsc.setOuterJoin(0);
	    qs.appendWhere(ptsc, new int[] { taskMemberlink_idx, projectMemberlink_idx });

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		String searchPjtName = StringUtil.checkNull((String) map.get("searchPjtName"));
		String searchTaskName = StringUtil.checkNull((String) map.get("searchTaskName"));
		String searchState = StringUtil.checkNull((String) map.get("searchState"));
		String searchPjtState = StringUtil.checkNull((String) map.get("searchPjtState"));
		String searchPjtNo = StringUtil.checkNull((String) map.get("searchPjtNo"));
		String command = StringUtil.checkNull((String) map.get("command"));
		String getUser = StringUtil.checkNull((String) map.get("getUser"));
		String notEqualState = StringUtil.checkNull((String) map.get("notEqualState"));

		WTUser wtuser = null;
		if (getUser.length() > 0) {
		    wtuser = (WTUser) CommonUtil.getObject(getUser);
		} else {
		    wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		}
		long oidValue_t = CommonUtil.getOIDLongValue(wtuser);

		// 태스크 책임자/ 구성원
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue_t),
			    new int[] { projectMemberlink_idx });
		}

		if (command.length() > 0) {
		    // 프로젝트
		    if (searchPjtName.length() > 0) {
			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			searchPjtName = StringUtil.changeString(searchPjtName, "*", "%");
			qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NAME, SearchCondition.LIKE, searchPjtName),
			        new int[] { project_idxx });
			searchPjtName = StringUtil.changeString(searchPjtName, "%", "*");
		    }
		    // 프로젝트 NO
		    if (searchPjtNo.length() > 0) {
			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			searchPjtNo = StringUtil.changeString(searchPjtNo, "*", "%");
			qs.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.LIKE, searchPjtNo),
			        new int[] { project_idxx });
			searchPjtNo = StringUtil.changeString(searchPjtNo, "%", "*");
		    }
		    // 태스크
		    if (searchTaskName.length() > 0) {
			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			searchTaskName = StringUtil.changeString(searchTaskName, "*", "%");
			qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, SearchCondition.LIKE, searchTaskName),
			        new int[] { task_idxx });
			searchTaskName = StringUtil.changeString(searchTaskName, "%", "*");

		    }
		    // 태스크 완료 여부
		    if (searchState != null && searchState.trim().length() > 0 && !searchState.equalsIgnoreCase("null")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendOpenParen();
			StringTokenizer stateTypeToken = new StringTokenizer(searchState, ",");
			while (stateTypeToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(E3PSTask.class, "taskState", SearchCondition.EQUAL, Integer.parseInt(stateTypeToken
				            .nextToken())), new int[] { task_idxx });
			    if (stateTypeToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }
		    // 취소된 프로젝트 조회대상제외
		    if (notEqualState.length() > 0) {
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", SearchCondition.NOT_EQUAL, "WITHDRAWN"),
			        new int[] { project_idxx });
		    }
		    // 프로젝트 상태
		    if (searchPjtState != null && searchPjtState.trim().length() > 0 && !searchPjtState.equalsIgnoreCase("null")) {
			if (qs.getConditionCount() > 0)
			    qs.appendAnd();
			qs.appendOpenParen();
			StringTokenizer pjtStateToken = new StringTokenizer(searchPjtState, ",");
			while (pjtStateToken.hasMoreTokens()) {
			    qs.appendWhere(
				    new SearchCondition(E3PSProject.class, "state.state", SearchCondition.EQUAL, pjtStateToken.nextToken()),
				    new int[] { project_idxx });
			    if (pjtStateToken.hasMoreTokens())
				qs.appendOr();
			}
			qs.appendCloseParen();
		    }

		    String planStartStartDate = StringUtil.checkNull((String) map.get("planStartStartDate"));
		    String planStartEndDate = StringUtil.checkNull((String) map.get("planStartEndDate"));
		    String planEndStartDate = StringUtil.checkNull((String) map.get("planEndStartDate"));
		    String planEndEndDate = StringUtil.checkNull((String) map.get("planEndEndDate"));

		    if ((planStartStartDate != null && planStartStartDate.length() > 0)
			    || (planStartEndDate != null && planStartEndDate.length() > 0)
			    || (planEndStartDate != null && planEndStartDate.length() > 0)
			    || (planEndEndDate != null && planEndEndDate.length() > 0)) {

			if (planStartStartDate != null && planStartStartDate.length() > 0) {
			    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planStartStartDate,
				    new ParsePosition(0)).getTime()));

			    if (qs.getConditionCount() > 0)
				qs.appendAnd();

			    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,
				    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			    qs.appendWhere(exsc, new int[] { schedIndex });
			}
			if (planStartEndDate != null && planStartEndDate.length() > 0) {
			    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				    planStartEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			    if (qs.getConditionCount() > 0)
				qs.appendAnd();

			    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE,
				    SearchCondition.LESS_THAN, convertDate);
			    qs.appendWhere(exsc, new int[] { schedIndex });
			}
			if (planEndStartDate != null && planEndStartDate.length() > 0) {
			    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(planEndStartDate,
				    new ParsePosition(0)).getTime()));

			    if (qs.getConditionCount() > 0)
				qs.appendAnd();

			    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE,
				    SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
			    qs.appendWhere(exsc, new int[] { schedIndex });
			}
			if (planEndEndDate != null && planEndEndDate.length() > 0) {
			    Timestamp convertDate = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").parse(
				    planEndEndDate + ":23-59-59", new ParsePosition(0)).getTime()));

			    if (qs.getConditionCount() > 0)
				qs.appendAnd();

			    exsc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE,
				    SearchCondition.LESS_THAN, convertDate);
			    qs.appendWhere(exsc, new int[] { schedIndex });
			}
		    }
		}
		// 최신 프로젝트
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		        new int[] { project_idxx });

		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		        new int[] { project_idxx });

		// ////////// Sub Query End mainSpec
		SubSelectExpression subfrom = new SubSelectExpression(qs);
		subfrom.setFromAlias(new String[] { "B0" }, project_idxx);
		// subfrom.set

		int sub_pjt_index = mainSpec.appendFrom(subfrom);
		if (mainSpec.getConditionCount() > 0)
		    mainSpec.appendAnd();
		SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSTask.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		        new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".taskOid"));
		sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
		sc.setOuterJoin(0);
		mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

		int project_idxx2 = mainSpec.appendClassList(E3PSProject.class, false);
		int master_idxx2 = mainSpec.appendClassList(E3PSProjectMaster.class, false);
		int schedIndex2 = mainSpec.appendClassList(ExtendScheduleData.class, false);

		if (mainSpec.getConditionCount() > 0) {
		    mainSpec.appendAnd();
		}
		SearchCondition mastersc2 = new SearchCondition(new ClassAttribute(E3PSProject.class, "masterReference.key.id"), "=",
		        new ClassAttribute(E3PSProjectMaster.class, wt.util.WTAttributeNameIfc.ID_NAME));
		mastersc2.setFromIndicies(new int[] { project_idxx2, master_idxx2 }, 0);
		mastersc2.setOuterJoin(0);
		mainSpec.appendWhere(mastersc2, new int[] { project_idxx2, master_idxx2 });
		mainSpec.appendAnd();
		SearchCondition tpsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=",
		        new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
		tpsc2.setFromIndicies(new int[] { main_idx, project_idxx2 }, 0);
		tpsc2.setOuterJoin(0);
		mainSpec.appendWhere(tpsc2, new int[] { main_idx, project_idxx2 });
		mainSpec.appendAnd();

		SearchCondition exsc2 = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=",
		        new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
		exsc2.setFromIndicies(new int[] { main_idx, schedIndex2 }, 0);
		exsc2.setOuterJoin(0);
		mainSpec.appendWhere(exsc2, new int[] { main_idx, schedIndex2 });
	    }
	    SearchUtil.setOrderBy(mainSpec, E3PSTask.class, main_idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return mainSpec;
    }

    public static QueryResult getMyProjectList(List<Map<String, Object>> conditionList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { List.class };
	    Object args[] = new Object[] { conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyProjectList", "e3ps.project.beans.SearchPagingProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}
	QueryResult result = null;
	try {
	    StatementSpec qs = getMyE3PSProjectQuerySpec(conditionList);
	    Kogger.debug(SearchPagingProjectHelper.class, "");
	    Kogger.debug(SearchPagingProjectHelper.class, "");
	    Kogger.debug(SearchPagingProjectHelper.class, "");
	    Kogger.debug(SearchPagingProjectHelper.class, "");
	    Kogger.debug(SearchPagingProjectHelper.class, qs.toString());
	    result = PersistenceHelper.manager.find(qs);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return result;
    }

    public static StatementSpec getMyE3PSProjectQuerySpec(List<Map<String, Object>> conditionList) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { List.class };
	    Object args[] = new Object[] { conditionList };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getMyE3PSProjectQuerySpec",
		        "e3ps.project.beans.SearchPagingProjectHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(SearchPagingProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (StatementSpec) obj;
	}

	CompoundQuerySpec compound = null;
	try {
	    Class targetClass[] = { ReviewProject.class, ProductProject.class, MoldProject.class };
	    compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION);
	    compound.setAdvancedQueryEnabled(true);

	    Vector attribute = new Vector();

	    ClassAttribute ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.OID_CLASSNAME);
	    ca.setColumnAlias("OID_CLASSNAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, WTAttributeNameIfc.ID_NAME);
	    ca.setColumnAlias("ID_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_TYPE_NAME);
	    ca.setColumnAlias("PJT_TYPE_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NO);
	    ca.setColumnAlias("PJT_NO");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.PJT_NAME);
	    ca.setColumnAlias("PJT_NAME");
	    attribute.add(new ClassAttributeData(ca, 0));

	    ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE);
	    ca.setColumnAlias("PLAN_START_DATE");
	    attribute.add(new ClassAttributeData(ca, 1));

	    ca = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE);
	    ca.setColumnAlias("PLAN_END_DATE");
	    attribute.add(new ClassAttributeData(ca, 1));

	    ca = new ClassAttribute(E3PSProject.class, E3PSProject.LIFE_CYCLE_STATE);
	    ca.setColumnAlias("LIFE_CYCLE_STATE");
	    attribute.add(new ClassAttributeData(ca, 0));

	    QuerySpec subQuery = myProjectSubQuery(conditionList);

	    for (int i = 0; i < targetClass.length; i++) {
		QuerySpec mainSpec = new QuerySpec();
		mainSpec.setAdvancedQueryEnabled(true);
		Class main_target = targetClass[i];

		E3PSClassTableExpression tableExpression = new E3PSClassTableExpression(main_target);
		int main_idx = mainSpec.appendFrom(tableExpression);
		int idx_schedule = mainSpec.appendClassList(ExtendScheduleData.class, false);

		ClassAttribute ca1 = null;
		ClassAttribute ca2 = null;

		ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
		ca2 = new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME);
		SearchCondition sc1 = new SearchCondition(ca1, "=", ca2);
		sc1.setFromIndicies(new int[] { main_idx, idx_schedule }, 0);
		sc1.setOuterJoin(0);
		mainSpec.appendWhere(sc1, new int[] { main_idx, idx_schedule });

		for (int j = 0; j < attribute.size(); j++) {
		    ClassAttributeData attrData = (ClassAttributeData) attribute.get(j);
		    mainSpec.appendSelect(attrData.ca, new int[] { attrData.index }, false);
		}

		// mainSpec.addClassList(main_target, true);

		if (!mainSpec.isAdvancedQueryEnabled()) {
		    mainSpec.setAdvancedQueryEnabled(true);
		}
		// ////////// Sub Query Start mainSpec

		SubSelectExpression subfrom = new SubSelectExpression(subQuery);
		subfrom.setFromAlias(new String[] { "B0" }, 0);
		// subfrom.set

		int sub_pjt_index = mainSpec.appendFrom(subfrom);

		if (mainSpec.getConditionCount() > 0) {
		    mainSpec.appendAnd();
		}

		SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME), "=",
		        new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
		sc.setFromIndicies(new int[] { main_idx, sub_pjt_index }, 0);
		sc.setOuterJoin(0);
		mainSpec.appendWhere(sc, new int[] { main_idx, sub_pjt_index });

		compound.addComponent(mainSpec);
	    }
	} catch (QueryException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (WTException e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	} catch (Exception e) {
	    Kogger.error(SearchPagingProjectHelper.class, e);
	}
	return compound;
    }
}
