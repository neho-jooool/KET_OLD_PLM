package e3ps.wfm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.enterprise.RevisionControlled;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.session.SessionHelper;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.wfm.WFMHelper;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.util.ClassifyPBOUtil;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.cost.entity.CostReport;
import ext.ket.shared.log.Kogger;

public class WorkspaceServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String cmd = req.getParameter("cmd");
	Kogger.debug(getClass(), "=====> WorkspaceServlet: cmd=[" + cmd + "]");

	if ("searchWaitingAppr".equals(cmd)) { // 결재대기함 검색
	    searchWaitingAppr(req, res);
	} else if ("searchRunningAppr".equals(cmd)) { // 결재진행함 검색
	    searchRunningAppr(req, res);
	} else if ("searchCompletedAppr".equals(cmd)) { // 결재완료함 검색
	    searchCompletedAppr(req, res);
	} else if ("searchTempAppr".equals(cmd)) { // 임시저장함 검색
	    searchTempAppr(req, res);
	} else if ("searchRefDoc".equals(cmd)) { // 참조문서함 검색
	    searchRefDoc(req, res);
	}

    }

    private static String removeToken(String text, String tokenRegex) {
	if (StringUtil.checkString(text)) {
	    text = text.replaceAll(tokenRegex, "");
	}
	return text;
    }

    /**
     * 결재대기함 검색
     * 
     * @param req
     * @param res
     */
    private void searchWaitingAppr(HttpServletRequest req, HttpServletResponse res) {
	try {
	    KETParamMapUtil map = KETParamMapUtil.getMap(req);

	    QuerySpec qs = getQuerySpecForWaitingAppr(map);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    int rowCount = 1;

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    while (qr.hasMoreElements()) {
		String retUrl = "#";
		Object[] obj = (Object[]) qr.nextElement();
		WorkItem item = (WorkItem) obj[0];
		Object pbo = item.getPrimaryBusinessObject().getObject();
		String title = ClassifyPBOUtil.extractPBOInfo(pbo).get("title").toString();
		String creator = ClassifyPBOUtil.extractPBOInfo(pbo).get("creator").toString();
		String type = ClassifyPBOUtil.extractPBOInfo(pbo).get("type").toString();
		String status = ClassifyPBOUtil.extractPBOInfo(pbo).get("state").toString();
		status = removeToken(status, "&nbsp;");
		WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
		WTUser lastApprover = (WTUser) WorkflowSearchHelper.manager.getLastUser((Persistable) pbo);

		String step;
		if (pbo instanceof KETDevelopmentRequest) {
		    KETDevelopmentRequest devReq = (KETDevelopmentRequest) pbo;
		    step = devReq.getDevelopmentStep();
		} else {
		    step = "";
		}

		String taskName;
		if (activity.getName().equals("수행담당자")) {
		    taskName = ClassifyPBOUtil.extractPBOInfo(pbo).get("task").toString();
		    retUrl = ClassifyPBOUtil.getTaskUrl(pbo);
		} else {
		    if ("R".equals(step) && "개발검토의뢰문서".equals(type)) {
			taskName = "개발검토의뢰";
		    } else if ("D".equals(step) && "개발검토의뢰문서".equals(type)) {
			taskName = "개발의뢰" + activity.getName();
		    } else {
			taskName = activity.getName();
		    }
		}
		// Kogger.debug(getClass(), lastApprover);
		if (lastApprover != null) {
		    String ownerID = item.getOwnership().getOwner().getName();
		    // Kogger.debug(getClass(), ownerID);
		    if (ownerID.equals(lastApprover.getName())) {
			taskName = "승인";
		    }
		}
		// 추가 된 부분 기안자랑 다른 부분 수정
		People apUser = new People();
		String appUser = "&nbsp";
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		if (appMaster != null) {
		    apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());

		    if (apUser != null) {
			appUser = apUser.getName();
		    }
		}

		String viewTaskUrl;
		if (taskName.equals("담당자지정")) {
		    viewTaskUrl = "/plm/jsp/project/Wf_Assign.jsp?oid=" + item.toString();
		} else if (activity.getName().equals("수행담당자")) {
		    viewTaskUrl = retUrl;
		} else {
		    viewTaskUrl = "/plm/jsp/wfm/ReviewTask.jsp?oid=" + item.toString();
		}
		String viewStatusUrl = "javascript:viewHistory('" + pbo.toString() + "');";

		strbuf.append("<I ");
		strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strbuf.appendRepl(" RowNum=\"" + rowCount++ + "\"");
		strbuf.appendRepl(" TaskType=\"" + type + "\"");
		strbuf.appendRepl(" TaskName=\"")
			.appendContent(taskName)
			.appendRepl(
				"\"" + " TaskNameOnClick=\"location.href='" + viewTaskUrl + "'\"" + " TaskNameHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
					+ "'>\" TaskNameHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Title=\"").appendContent(title).appendRepl("\"");
		strbuf.appendRepl(" Status=\"" + status + "\"" + " StatusOnClick=\"" + viewStatusUrl + "\"" + " StatusHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			+ "'>\" StatusHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Creator=\"" + ((appMaster != null) ? appUser : creator) + "\"");
		strbuf.appendRepl(" ArriveDate=\"" + DateUtil.getDateString(item.getPersistInfo().getCreateStamp(), "d") + "\"");
		strbuf.append("/>");
	    }

	    req.setAttribute("searchCondition", map);    // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString());        // 검색 결과 데이터

	    gotoResult(req, res, "/jsp/wfm/WorkitemList.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * @param _map
     * @return
     * @throws Exception
     */
    private QuerySpec getQuerySpecForWaitingAppr(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	WTPrincipal user = SessionHelper.manager.getPrincipal();

	QuerySpec spec = new QuerySpec();
	int itemIdx = spec.addClassList(WorkItem.class, true);

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { itemIdx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class, WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"), new int[] { itemIdx });
	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"), true), new int[] { itemIdx });

	Kogger.debug(getClass(), "WorkspaceServlet.getQuerySpecForWaitingAppr: spec=\n" + spec);

	return spec;
    }

    /**
     * 결재진행함 검색
     * 
     * @param req
     * @param res
     */
    private void searchRunningAppr(HttpServletRequest req, HttpServletResponse res) {
	try {
	    KETParamMapUtil rmap = KETParamMapUtil.getMap(req);

	    List vec = getListForRunningAppr(rmap);
	    int vecSize = vec.size();
	    int rowCount = 1;

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    for (int i = 0; i < vecSize; ++i) {
		HashMap map = (HashMap) vec.get(i);
		Persistable pr = (Persistable) map.get("pbo");
		String prOid = pr.toString();
		String classStr = map.get("className").toString();
		String time = map.get("createTime").toString();
		String title = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("title").toString());
		String state = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("state").toString());
		state = removeToken(state, "&nbsp;");
		String type = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("type").toString());
		String creator = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("creator").toString());
		String createDate = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("ctime").toString());
		String reqDate;
		if (classStr.equals("wt.workflow.work.WorkItem")) {
		    reqDate = time.substring(0, 10);
		} else {
		    reqDate = "";
		}

		if (pr instanceof KETMoldChangeActivity) {
		    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) pr;
		    prOid = moldCA.getMoldECO().toString();
		} else if (pr instanceof KETProdChangeActivity) {
		    KETProdChangeActivity prodCA = (KETProdChangeActivity) pr;
		    prOid = prodCA.getProdECO().toString();
		}

		// 추가 된 부분 기안자랑 다른 부분 수정
		People apUser = new People();
		String appUser = "&nbsp";
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pr);
		if (appMaster != null) {
		    apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());

		    if (apUser != null) {
			appUser = apUser.getName();
		    }
		}
		// 2013.1.14 shkim
		Vector docVec = null;
		if (pr instanceof KETWfmMultiApprovalRequest) {
		    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(prOid);
		    // Vector epmVec = WorkflowSearchHelper.manager.getEPMList(multiReq);
		    docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
		    // out.println(docVec.size());
		}

		String viewTitleUrl;
		if (docVec != null && docVec.size() != 0) {
		    viewTitleUrl = "javascript:openView2('" + prOid + "');";
		} else {
		    viewTitleUrl = "javascript:openView('" + prOid + "');";
		}
		String viewStatusUtl = "javascript:viewHistory('" + prOid + "');";

		strbuf.append("<I ");
		strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strbuf.appendRepl(" RowNum=\"" + rowCount + "\"");
		strbuf.appendRepl(" TaskType=\"" + type + "\"");
		strbuf.appendRepl(" Title=\"")
			.appendContent(title)
			.appendRepl(
				"\"" + " TitleOnClick=\"" + viewTitleUrl + "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" TitleHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Status=\"" + state + "\"" + " StatusOnClick=\"" + viewStatusUtl + "\"" + " StatusHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			+ "'>\" StatusHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Creator=\"" + ((appMaster != null) ? appUser : creator) + "\"");
		strbuf.appendRepl(" CreateDate=\"" + createDate + "\"");
		strbuf.appendRepl(" ArriveDate=\"" + reqDate + "\"");
		strbuf.append("/>");
	    }

	    req.setAttribute("searchCondition", rmap);    // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString());        // 검색 결과 데이터

	    gotoResult(req, res, "/jsp/wfm/RunningWorkList.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private List getListForRunningAppr(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	String predate = map.getString("predate");
	String postdate = map.getString("postdate");

	String aliasName1 = "pbo";
	String aliasName2 = "obj";
	String aliasName3 = "createTime";

	WTPrincipal user = SessionHelper.manager.getPrincipal();

	QueryResult result = null;
	QuerySpec masterspec = new QuerySpec();
	QuerySpec itemspec = new QuerySpec();

	Class masterClass = KETWfmApprovalMaster.class;
	Class itemClass = WorkItem.class;

	int masterIdx = masterspec.appendClassList(masterClass, false);
	int itemIdx = itemspec.appendClassList(itemClass, false);

	ClassAttribute masterpbo = new ClassAttribute(masterClass, "businessobjectRef.key.classname");
	masterpbo.setColumnAlias(aliasName1);

	ClassAttribute masterobj = new ClassAttribute(masterClass, "thePersistInfo.theObjectIdentifier.classname");
	masterobj.setColumnAlias(aliasName2);

	// TimeStamp의 select 시 기본 포맷은 TO_CHAR 이므로 Sorting 시 오류 발생
	// SQLFunction을 이용하여 TO_DATE로 변경하여줌
	ClassAttribute masterstamp_s = new ClassAttribute(masterClass, "thePersistInfo.createStamp");
	SQLFunction masterstamp = SQLFunction.newSQLFunction(SQLFunction.TO_DATE_FUNCTION, masterstamp_s);
	masterstamp.setColumnAlias(aliasName3);

	masterspec.appendSelect(masterpbo, new int[] { masterIdx }, false);
	masterspec.appendSelect(masterobj, new int[] { masterIdx }, false);
	masterspec.appendSelect(masterstamp, new int[] { masterIdx }, false);

	if (masterspec.getConditionCount() > 0)
	    masterspec.appendAnd();
	masterspec.appendWhere(new SearchCondition(masterClass, "owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { masterIdx });

	if (masterspec.getConditionCount() > 0)
	    masterspec.appendAnd();
	masterspec.appendWhere(new SearchCondition(masterClass, KETWfmApprovalMaster.START_FLAG, SearchCondition.IS_TRUE));

	if (!predate.equals("")) {
	    if (masterspec.getConditionCount() > 0)
		masterspec.appendAnd();
	    masterspec.appendWhere(new SearchCondition(masterClass, "thePersistInfo.createStamp", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate2(predate)), new int[] { masterIdx });
	}

	if (!postdate.equals("")) {
	    if (masterspec.getConditionCount() > 0)
		masterspec.appendAnd();
	    masterspec.appendWhere(new SearchCondition(masterClass, "thePersistInfo.createStamp", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil.convertEndDate2(postdate)), new int[] { masterIdx });
	}

	masterspec.appendOrderBy(new OrderBy(masterstamp, true), new int[] { masterIdx });

	ClassAttribute itempbo = new ClassAttribute(itemClass, "primaryBusinessObject.key.classname");
	itempbo.setColumnAlias(aliasName1);

	ClassAttribute itemobj = new ClassAttribute(itemClass, "thePersistInfo.theObjectIdentifier.classname");
	itemobj.setColumnAlias(aliasName2);

	ClassAttribute itemstamp_s = new ClassAttribute(itemClass, "thePersistInfo.createStamp");
	SQLFunction itemstamp = SQLFunction.newSQLFunction(SQLFunction.TO_DATE_FUNCTION, itemstamp_s);
	itemstamp.setColumnAlias(aliasName3);

	itemspec.appendSelect(itempbo, new int[] { itemIdx }, false);
	itemspec.appendSelect(itemobj, new int[] { itemIdx }, false);
	itemspec.appendSelect(itemstamp, new int[] { masterIdx }, false);

	if (itemspec.getConditionCount() > 0)
	    itemspec.appendAnd();
	itemspec.appendWhere(new SearchCondition(itemClass, "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { itemIdx });

	if (itemspec.getConditionCount() > 0)
	    itemspec.appendAnd();
	itemspec.appendWhere(new SearchCondition(itemClass, "status", SearchCondition.EQUAL, "COMPLETED"), new int[] { itemIdx });

	if (!predate.equals("")) {
	    if (itemspec.getConditionCount() > 0)
		itemspec.appendAnd();
	    itemspec.appendWhere(new SearchCondition(itemClass, "thePersistInfo.createStamp", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate2(predate)), new int[] { itemIdx });
	}

	if (!postdate.equals("")) {
	    if (itemspec.getConditionCount() > 0)
		itemspec.appendAnd();
	    itemspec.appendWhere(new SearchCondition(itemClass, "thePersistInfo.createStamp", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil.convertEndDate2(postdate)), new int[] { itemIdx });
	}

	itemspec.appendOrderBy(new OrderBy(itemstamp, true), new int[] { itemIdx });

	CompoundQuerySpec spec = new CompoundQuerySpec();
	spec.setSetOperator(SetOperator.UNION);
	spec.addComponent(masterspec);
	spec.addComponent(itemspec);
	spec.setUseComponentOrderBy(true);// 테이블 모두 aliasName3으로 sorting 가능하게 함

	Kogger.debug(getClass(), "WorkspaceServlet.getListForRunningAppr: spec=\n" + spec);

	result = PersistenceHelper.manager.find(spec);

	Vector pboVec = new Vector();
	Vector vec = new Vector();

	if (result.size() > 0) {
	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		Persistable ipr = (Persistable) CommonUtil.getObject(obj[0].toString());

		if (ipr != null) {
		    String istate = ParamUtil.checkStrParameter(ClassifyPBOUtil.extractPBOInfo(ipr).get("state").toString(), "");
		    istate = removeToken(istate, "&nbsp;");

		    if ((!istate.equals("")) && (!istate.equals("승인완료")) && (!istate.equals("취소됨")) && (!istate.equals("작업중")) && (!istate.equals("진행")) && (!istate.equals("완료"))
			    && (!istate.equals("폐기")) && (!istate.equals("중지"))) {
			if (!pboVec.contains(ipr)) {
			    HashMap resultMap = new HashMap();
			    pboVec.addElement(ipr);
			    resultMap.put("pbo", ipr);
			    resultMap.put("className", obj[1].toString());
			    resultMap.put("createTime", obj[2].toString());
			    vec.add(resultMap);
			}
		    }
		}
	    }
	}

	return vec;
    }

    /**
     * 결재완료함 검색
     * 
     * @param req
     * @param res
     */
    private void searchCompletedAppr(HttpServletRequest req, HttpServletResponse res) {
	try {
	    KETParamMapUtil rmap = KETParamMapUtil.getMap(req);

	    List vec = getListForCompletedAppr(rmap);
	    int vecSize = vec.size();
	    int rowCount = 1;

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    for (int i = 0; i < vecSize; ++i) {
		HashMap map = (HashMap) vec.get(i);
		Persistable pr = (Persistable) map.get("pbo");
		String classStr = map.get("className").toString();
		String time = map.get("createTime").toString();
		String title = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("title").toString());
		String state = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("state").toString());
		state = removeToken(state, "&nbsp;");
		String type = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("type").toString());
		String creator = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("creator").toString());
		String createDate = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("ctime").toString());
		String reqDate;
		if (classStr.equals("wt.workflow.work.WorkItem")) {
		    reqDate = time.substring(0, 10);
		} else {
		    reqDate = "";
		}

		// 추가 된 부분 기안자랑 다른 부분 수정
		People apUser = new People();
		String appUser = "&nbsp";
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pr);
		if (appMaster != null) {
		    apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());

		    if (apUser != null) {
			appUser = apUser.getName();
		    }
		}

		// 2013.1.29 shkim
		Vector docVec = null;
		if (pr instanceof KETWfmMultiApprovalRequest) {
		    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(pr.toString());
		    // Vector epmVec = WorkflowSearchHelper.manager.getEPMList(multiReq);
		    docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
		    // out.println(docVec.size());
		}

		String viewTitleUrl;
		if (docVec != null && docVec.size() != 0) {
		    viewTitleUrl = "javascript:openView2('" + pr.toString() + "');";
		} else {
		    viewTitleUrl = "javascript:openView('" + pr.toString() + "');";
		}
		String viewStatusUtl = "javascript:viewHistory('" + pr.toString() + "');";

		strbuf.append("<I ");
		strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strbuf.appendRepl(" RowNum=\"" + rowCount + "\"");
		strbuf.appendRepl(" TaskType=\"" + type + "\"");
		strbuf.appendRepl(" Title=\"")
			.appendContent(title)
			.appendRepl(
				"\"" + " TitleOnClick=\"" + viewTitleUrl + "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" TitleHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Status=\"" + state + "\"" + " StatusOnClick=\"" + viewStatusUtl + "\"" + " StatusHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			+ "'>\" StatusHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Creator=\"" + ((appMaster != null) ? appUser : creator) + "\"");
		strbuf.appendRepl(" CreateDate=\"" + createDate + "\"");
		strbuf.appendRepl(" ArriveDate=\"" + reqDate + "\"");
		strbuf.append("/>");
	    }

	    req.setAttribute("searchCondition", rmap);    // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString());        // 검색 결과 데이터

	    gotoResult(req, res, "/jsp/wfm/CompletedWorkList.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private List getListForCompletedAppr(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	String predate = map.getString("predate");
	String postdate = map.getString("postdate");

	String aliasName1 = "pbo";
	String aliasName2 = "obj";
	String aliasName3 = "createTime";

	WTPrincipal user = SessionHelper.manager.getPrincipal();
	QueryResult result = null;
	QuerySpec masterspec = new QuerySpec();
	QuerySpec itemspec = new QuerySpec();
	Class masterClass = KETWfmApprovalMaster.class;
	Class itemClass = WorkItem.class;
	int masterIdx = masterspec.appendClassList(masterClass, false);
	int itemIdx = itemspec.appendClassList(itemClass, false);

	ClassAttribute masterpbo = new ClassAttribute(masterClass, "businessobjectRef.key.classname");
	masterpbo.setColumnAlias(aliasName1);
	ClassAttribute masterobj = new ClassAttribute(masterClass, "thePersistInfo.theObjectIdentifier.classname");
	masterobj.setColumnAlias(aliasName2);
	ClassAttribute masterstamp_s = new ClassAttribute(masterClass, "thePersistInfo.createStamp");
	SQLFunction masterstamp = SQLFunction.newSQLFunction(SQLFunction.TO_DATE_FUNCTION, masterstamp_s);
	masterstamp.setColumnAlias(aliasName3);

	masterspec.appendSelect(masterpbo, new int[] { masterIdx }, false);
	masterspec.appendSelect(masterobj, new int[] { masterIdx }, false);
	masterspec.appendSelect(masterstamp, new int[] { masterIdx }, false);

	if (masterspec.getConditionCount() > 0)
	    masterspec.appendAnd();
	masterspec.appendWhere(new SearchCondition(masterClass, "owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { masterIdx });
	if (masterspec.getConditionCount() > 0)
	    masterspec.appendAnd();
	masterspec.appendWhere(new SearchCondition(masterClass, KETWfmApprovalMaster.START_FLAG, SearchCondition.IS_TRUE));
	if (!predate.equals("")) {
	    if (masterspec.getConditionCount() > 0)
		masterspec.appendAnd();
	    masterspec.appendWhere(new SearchCondition(masterClass, "thePersistInfo.createStamp", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate2(predate)), new int[] { masterIdx });
	}
	if (!postdate.equals("")) {
	    if (masterspec.getConditionCount() > 0)
		masterspec.appendAnd();
	    masterspec.appendWhere(new SearchCondition(masterClass, "thePersistInfo.createStamp", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil.convertEndDate2(postdate)), new int[] { masterIdx });
	}
	masterspec.appendOrderBy(new OrderBy(masterstamp, false), new int[] { masterIdx });
	// masterspec.appendOrderBy(new OrderBy( masterstamp, true), new int[]{masterIdx});

	ClassAttribute itempbo = new ClassAttribute(itemClass, "primaryBusinessObject.key.classname");
	itempbo.setColumnAlias(aliasName1);
	ClassAttribute itemobj = new ClassAttribute(itemClass, "thePersistInfo.theObjectIdentifier.classname");
	itemobj.setColumnAlias(aliasName2);
	ClassAttribute itemstamp_s = new ClassAttribute(itemClass, "thePersistInfo.createStamp");
	SQLFunction itemstamp = SQLFunction.newSQLFunction(SQLFunction.TO_DATE_FUNCTION, itemstamp_s);
	itemstamp.setColumnAlias(aliasName3);

	itemspec.appendSelect(itempbo, new int[] { itemIdx }, false);
	itemspec.appendSelect(itemobj, new int[] { itemIdx }, false);
	itemspec.appendSelect(itemstamp, new int[] { itemIdx }, false);

	if (itemspec.getConditionCount() > 0)
	    itemspec.appendAnd();
	itemspec.appendWhere(new SearchCondition(itemClass, "ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { itemIdx });
	if (itemspec.getConditionCount() > 0)
	    itemspec.appendAnd();
	itemspec.appendWhere(new SearchCondition(itemClass, "status", SearchCondition.EQUAL, "COMPLETED"), new int[] { itemIdx });
	if (!predate.equals("")) {
	    if (itemspec.getConditionCount() > 0)
		itemspec.appendAnd();
	    itemspec.appendWhere(new SearchCondition(itemClass, "thePersistInfo.createStamp", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate2(predate)), new int[] { itemIdx });
	}
	if (!postdate.equals("")) {
	    if (itemspec.getConditionCount() > 0)
		itemspec.appendAnd();
	    itemspec.appendWhere(new SearchCondition(itemClass, "thePersistInfo.createStamp", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil.convertEndDate2(postdate)), new int[] { itemIdx });
	}
	itemspec.appendOrderBy(new OrderBy(itemstamp, false), new int[] { itemIdx });
	// itemspec.appendOrderBy(new OrderBy( itemstamp, true), new int[]{itemIdx});

	CompoundQuerySpec spec = new CompoundQuerySpec();
	spec.setSetOperator(SetOperator.UNION);
	spec.addComponent(masterspec);
	spec.addComponent(itemspec);
	spec.appendOrderBy(new OrderBy(itemstamp, true));
	spec.setUseComponentOrderBy(true);// 테이블 모두 aliasName3 내림차순으로 sorting으로 가능하게 함

	Kogger.debug(getClass(), "WorkspaceServlet.getListForCompletedAppr: spec=\n" + spec);

	result = PersistenceHelper.manager.find(spec);

	Vector pboVec = new Vector();
	Vector vec = new Vector();
	if (result.size() > 0) {
	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		Persistable ipr = (Persistable) CommonUtil.getObject(obj[0].toString());
		if (ipr != null) {
		    String istate = ParamUtil.checkStrParameter(ClassifyPBOUtil.extractPBOInfo(ipr).get("state").toString(), "");
		    istate = removeToken(istate, "&nbsp;");
		    if ((istate.equals("승인완료")) || (istate.equals("취소됨")) || (istate.equals("진행")) || (istate.equals("완료")) || (istate.equals("폐기")) || (istate.equals("중지"))) {
			if (!pboVec.contains(ipr)) {
			    HashMap resultMap = new HashMap();
			    pboVec.addElement(ipr);
			    resultMap.put("pbo", ipr);
			    resultMap.put("className", obj[1].toString());
			    resultMap.put("createTime", obj[2].toString());
			    vec.add(resultMap);
			}
		    }
		}
	    }
	}

	return vec;
    }

    /**
     * 임시저장함 검색
     * 
     * @param req
     * @param res
     */
    private void searchTempAppr(HttpServletRequest req, HttpServletResponse res) {
	try {
	    KETParamMapUtil map = KETParamMapUtil.getMap(req);

	    QuerySpec qs = getQuerySpecForTempAppr(map);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    int rowCount = 1;

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    while (qr.hasMoreElements()) {
		Object[] obj = (Object[]) qr.nextElement();
		KETWfmApprovalMaster master = (KETWfmApprovalMaster) obj[0];
		String title = "";
		String state = "";
		String type = "";
		Object target = null;
		if (master != null) {
		    target = master.getBusinessobjectRef().getObject();
		    if (target.toString().substring(0, 2).equals("OR"))
			target = master.getPbo();
		    title = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(target).get("title").toString());
		    state = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(target).get("state").toString());
		    state = removeToken(state, "&nbsp;");
		    type = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(target).get("type").toString());
		}

		Persistable p = master.getPbo();
		String version = "";
		if (p instanceof RevisionControlled) {
		    RevisionControlled rc = (RevisionControlled) p;
		    version = rc.getVersionIdentifier().getValue();
		}

		String viewTitleUrl = "javascript:goPage('" + target.toString() + "');";
		String viewStatusUtl = "javascript:viewHistory('" + target.toString() + "');";

		strbuf.append("<I ");
		strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strbuf.appendRepl(" RowNum=\"" + rowCount + "\"");
		strbuf.appendRepl(" TaskType=\"" + type + "\"");
		strbuf.appendRepl(" Title=\"")
			.appendContent(title)
			.appendRepl(
				"\"" + " TitleOnClick=\"" + viewTitleUrl + "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" TitleHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Version=\"" + version + "\"");
		strbuf.appendRepl(" Status=\"" + state + "\"" + " StatusOnClick=\"" + viewStatusUtl + "\"" + " StatusHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			+ "'>\" StatusHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Creator=\"" + master.getOwner().getFullName() + "\"");
		strbuf.appendRepl(" CreateDate=\"" + DateUtil.getDateString(master.getPersistInfo().getCreateStamp(), "d") + "\"");
		strbuf.append("/>");
	    }

	    req.setAttribute("searchCondition", map);    // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString());        // 검색 결과 데이터

	    gotoResult(req, res, "/jsp/wfm/TempApprovalList.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private QuerySpec getQuerySpecForTempAppr(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	String predate = map.getString("predate");
	String postdate = map.getString("postdate");

	WTPrincipal user = SessionHelper.manager.getPrincipal();

	QuerySpec spec = new QuerySpec();
	int masterIdx = spec.addClassList(KETWfmApprovalMaster.class, true);

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, "owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)), new int[] { masterIdx });
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, KETWfmApprovalMaster.START_FLAG, SearchCondition.IS_FALSE));
	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, "pboReference.key.classname", SearchCondition.NOT_NULL, true));
	if (!predate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, "thePersistInfo.createStamp", ">", DateUtil.convertStartDate2(predate)), new int[] { masterIdx });
	}
	if (!postdate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(postdate)), new int[] { masterIdx });
	}
	spec.appendOrderBy(new OrderBy(new ClassAttribute(KETWfmApprovalMaster.class, "thePersistInfo.createStamp"), true), new int[] { masterIdx });

	Kogger.debug(getClass(), "WorkspaceServlet.getQuerySpecForTempAppr: spec=\n" + spec);

	return spec;
    }

    /**
     * 참조문서함 검색
     * 
     * @param req
     * @param res
     */
    private void searchRefDoc(HttpServletRequest req, HttpServletResponse res) {
	try {
	    KETParamMapUtil rmap = KETParamMapUtil.getMap(req);

	    List vec = getListForRefDoc(rmap);
	    int vecSize = vec.size();
	    int rowCount = 1;

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    for (int i = 0; i < vecSize; ++i) {
		Persistable pr = (Persistable) vec.get(i);
		String title = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("title").toString());
		String state = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("state").toString());
		state = removeToken(state, "&nbsp;");
		String type = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("type").toString());
		String creator = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("creator").toString());
		String createDate = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pr).get("ctime").toString());
		String lastDate = WorkflowSearchHelper.manager.getLastApprovalDate(pr);
		lastDate = lastDate.substring(0, 10);

		// 추가 된 부분 기안자랑 다른 부분 수정
		People apUser = new People();
		String appUser = "&nbsp";
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pr);
		if (appMaster != null) {
		    apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());

		    if (apUser != null) {
			appUser = apUser.getName();
		    }
		}

		String viewTitleUrl = "javascript:openView('" + pr.toString() + "');";
		
		if(pr instanceof CostReport){
		    viewTitleUrl = getCostReportOid(pr);
		}
		
		String viewStatusUtl = "javascript:viewHistory('" + pr.toString() + "');";

		strbuf.append("<I ");
		strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		strbuf.appendRepl(" RowNum=\"" + rowCount + "\"");
		strbuf.appendRepl(" TaskType=\"" + type + "\"");
		strbuf.appendRepl(" Title=\"")
			.appendContent(title)
			.appendRepl(
				"\"" + " TitleOnClick=\"" + viewTitleUrl + "\"" + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" TitleHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Status=\"" + state + "\"" + " StatusOnClick=\"" + viewStatusUtl + "\"" + " StatusHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			+ "'>\" StatusHtmlPostfix=\"</font>\"");
		strbuf.appendRepl(" Creator=\"" + ((appMaster != null) ? appUser : creator) + "\"");
		strbuf.appendRepl(" CreateDate=\"" + createDate + "\"");
		strbuf.appendRepl(" ArriveDate=\"" + lastDate + "\"");
		strbuf.append("/>");
	    }

	    req.setAttribute("searchCondition", rmap);    // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString());        // 검색 결과 데이터

	    gotoResult(req, res, "/jsp/wfm/ReferenceWorkList.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private List getListForRefDoc(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	String predate = map.getString("predate");
	String postdate = map.getString("postdate");
	String filterClass = map.getString("filterClass");

	QueryResult result = WFMHelper.manager.getRefWorklistQuery(predate, postdate, filterClass);

	Vector vec = new Vector();
	if (result.size() > 0) {
	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		KETWfmApprovalMaster master = (KETWfmApprovalMaster) obj[0];
		Persistable pbo = null;
		try {
		    pbo = master.getBusinessobjectRef().getObject();
		    if (pbo == null)
			pbo = master.getPbo();
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		if (pbo != null)
		    vec.addElement(pbo);
	    }
	}
	return vec;
    }
    
    private String getCostReportOid(Persistable pr){
	CostReport report = (CostReport) pr;
	String taskOid = CommonUtil.getOIDString(report.getTask());
	String viewTitleUrl = "javascript:openCostReport('" + taskOid + "');";
	return viewTitleUrl;
    }

}
