package e3ps.wfm.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TgStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.groupware.company.People;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import ext.ket.shared.log.Kogger;

public class MultiApprovalServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String cmd = req.getParameter("cmd");
	if (cmd == null)
	    cmd = req.getParameter("Cmd");
	Kogger.debug(getClass(), "=====> MultiApprovalServlet: cmd=[" + cmd + "]");

	if ("openSearch".equals(cmd)) {
	    openSearch(req, res);
	} else if ("search".equals(cmd)) {
	    search(req, res);
	} else if ("searchGridData".equals(cmd)) {
	    searchGrid(req, res, false);
	} else if ("searchGridPage".equals(cmd)) {
	    searchGrid(req, res, true);
	} else if ("excel".equals(cmd)) {
	    excel(req, res);
	}
    }

    private void searchGrid(HttpServletRequest req, HttpServletResponse res, boolean isPageNotData) {
	try {

	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPageNotData, paramMap);

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // [Start] 결과 내 재검색
	    boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	    List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchMultiApproval", searchInResult, req);
	    conditionList.add(paramMap);
	    QuerySpec querySpec = getQuerySpecForSearchSIR(conditionList, pager);

	    if (isPageNotData) {

		PagingQueryResult pagingQueryResult = null;
		// String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
		if (pager.getCurrentPageNo() == 0) {
		    pagingQueryResult = PagingSessionHelper.openPagingSession(0, pager.getPageSize(), querySpec);
		    PagingSessionHelper.closePagingSession(pagingQueryResult.getPagingSession());
		} else {
		    pagingQueryResult = PagingSessionHelper.openPagingSession(pager.getPagingStartNo(), pager.getPageSize(), querySpec);
		    PagingSessionHelper.closePagingSession(pagingQueryResult.getPagingSession());
		    // pagingQueryResult = PagingSessionHelper.fetchPagingSession(pager.getPagingStartNo(), pager.getPageSize(),
		    // Long.parseLong(pageSessionId));
		}

		TgStringBuffer strbuf = new TgStringBuffer();
		int totalCount = pagingQueryResult.getTotalSize();
		int startNo = pager.getRowNumStartNo(totalCount);
		while (pagingQueryResult.hasMoreElements()) {

		    Object[] obj = (Object[]) pagingQueryResult.nextElement();

		    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj[0];
		    String reqDate = DateUtil.getDateString(multiReq.getRequestDate(), "d");

		    String viewUrl = "/plm/jsp/wfm/ViewMultiApproval.jsp?oid=" + multiReq.toString();

		    strbuf.append("<I ");
		    strbuf.append(" NoColor=\"2\" CanDelete=\"0\"");
		    strbuf.append(" DocNo=\"" + startNo-- + "\"");
		    strbuf.append(" ReqName=\"")
			    .appendRepl(multiReq.getReqName())
			    .append("\" ReqNameOnClick=\"location.href='" + viewUrl + "'\"" + " ReqNameHtmlPrefix=\"<font color='"
			            + PropertiesUtil.getSearchGridLinkColor() + "'>\" ReqNameHtmlPostfix=\"</font>\"");
		    strbuf.append(" Creator=\"" + multiReq.getOwner().getFullName() + "\"");
		    strbuf.append(" CreateDate=\"" + reqDate + "\"");
		    strbuf.append(" Status=\"" + multiReq.getLifeCycleState().getDisplay(messageService.getLocale()) + "\"");
		    strbuf.append("/>");
		}

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strbuf.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		QueryResult queryResult = PersistenceHelper.manager.find(querySpec);
		int totalCount = queryResult.size();

		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void search(HttpServletRequest req, HttpServletResponse res) {

	try {

	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

	    gotoResult(req, res, "/jsp/wfm/SearchMultiApproval.jsp");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    // private void search(HttpServletRequest req, HttpServletResponse res) {
    // try {
    // FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
    // KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
    //
    // KETMessageService messageService = KETMessageService.getMessageService(req);
    //
    // // [Start] 결과 내 재검색
    // /*
    // * QuerySpec qs = getQuerySpecForSearch(map); // 기존 검색 주석처리
    // */
    // boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
    // List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchMultiApproval", searchInResult, req);
    // conditionList.add(paramMap);
    // QuerySpec qs = getQuerySpecForSearchSIR(conditionList);
    // // [End] 결과 내 재검색
    //
    // QueryResult qr = PersistenceHelper.manager.find(qs);
    // int qrSize = qr.size();
    //
    // TreeGridStringBuffer strbuf = new TreeGridStringBuffer();
    //
    // while (qr.hasMoreElements()) {
    // Object[] obj = (Object[]) qr.nextElement();
    // KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj[0];
    // String reqDate = DateUtil.getDateString(multiReq.getRequestDate(), "d");
    //
    // String viewUrl = "/plm/jsp/wfm/ViewMultiApproval.jsp?oid=" + multiReq.toString();
    //
    // strbuf.append("<I ");
    // strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
    // strbuf.appendRepl(" DocNo=\"" + (qrSize--) + "\"");
    // strbuf.appendRepl(" ReqName=\"")
    // .appendContent(multiReq.getReqName())
    // .appendRepl(
    // "\" ReqNameOnClick=\"location.href='" + viewUrl + "'\"" + " ReqNameHtmlPrefix=\"<font color='"
    // + PropertiesUtil.getSearchGridLinkColor() + "'>\" ReqNameHtmlPostfix=\"</font>\"");
    // strbuf.appendRepl(" Creator=\"" + multiReq.getOwner().getFullName() + "\"");
    // strbuf.appendRepl(" CreateDate=\"" + reqDate + "\"");
    // strbuf.appendRepl(" Status=\"" + multiReq.getLifeCycleState().getDisplay(messageService.getLocale()) + "\"");
    // strbuf.append("/>");
    // }
    //
    // req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
    // req.setAttribute("tgData", strbuf.toString()); // 검색 결과 데이터
    //
    // gotoResult(req, res, "/jsp/wfm/SearchMultiApproval.jsp");
    // } catch (Exception e) {
    // Kogger.error(getClass(), e);
    // }
    // }

    private void openSearch(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    gotoResult(req, res, "/jsp/wfm/SearchMultiApproval.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private QuerySpec getQuerySpecForSearch(Map _map) throws Exception {
	KETParamMapUtil map = KETParamMapUtil.getMap(_map);

	String reqName = map.getString("reqName");
	String[] creatorAry = map.getStringArray("creator");
	String reqStartDate = map.getString("reqStartDate");
	String reqEndDate = map.getString("reqEndDate");
	String[] stateAry = map.getStringArray("state");

	QuerySpec spec = new QuerySpec();
	Class targetClass = KETWfmMultiApprovalRequest.class;
	Class nameClass = People.class;

	int reqMultiIdx = spec.appendClassList(targetClass, true);

	/*
	 * int idx_link = spec.appendClassList(KETWfmMultiReqDocLink.class, false); ClassAttribute ca = new ClassAttribute(targetClass,
	 * WTAttributeNameIfc.ID_NAME); ClassAttribute ca2 = new ClassAttribute(KETWfmMultiReqDocLink.class, "roleAObjectRef.key.id");
	 * SearchCondition sc2 = new SearchCondition(ca, "=", ca2); //sc2.setFromIndicies(new int[]{reqMultiIdx, idx_link}, 0);
	 * //sc2.setOuterJoin(1); spec.appendWhere(sc2, new int[]{reqMultiIdx, idx_link}); ///out.println(spec);
	 */
	SearchCondition sc2 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, "=", "EPM");
	spec.appendWhere(sc2, new int[] { reqMultiIdx });
	spec.appendOr();
	SearchCondition sc3 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, true);
	spec.appendWhere(sc3, new int[] { reqMultiIdx });
	// spec.appendAnd();

	if (StringUtil.checkString(reqName)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    /*
	     * reqName = StringUtil.changeString(reqName, "*", "%"); spec.appendWhere(new
	     * SearchCondition(targetClass,"reqName",SearchCondition.LIKE, reqName),new int[]{reqMultiIdx});
	     */
	    KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "reqName", reqName, true);
	}
	if (creatorAry != null && creatorAry.length > 0) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    /*
	     * spec.appendWhere(new SearchCondition(targetClass,"owner.key.id",SearchCondition.EQUAL,
	     * CommonUtil.getOIDLongValue(creator)),new int[]{reqMultiIdx});
	     */
	    String[] idAry = new String[creatorAry.length];
	    for (int i = 0; i < creatorAry.length; ++i) {
		idAry[i] = String.valueOf(CommonUtil.getOIDLongValue(creatorAry[i]));
	    }
	    KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "owner.key.id", idAry, false);
	}
	if (stateAry != null && stateAry.length > 0) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    /*
	     * spec.appendWhere(new SearchCondition(targetClass,"state.state",SearchCondition.EQUAL, State.toState(state)),new
	     * int[]{reqMultiIdx});
	     */
	    KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "state.state", stateAry, false);
	}
	if (StringUtil.checkString(reqStartDate)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(targetClass, "requestDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil
		            .convertStartDate2(reqStartDate)), new int[] { reqMultiIdx });
	}
	if (StringUtil.checkString(reqEndDate)) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(targetClass, "requestDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil
		            .convertEndDate2(reqEndDate)), new int[] { reqMultiIdx });
	}
	/*
	 * if(sort.equals(""))spec.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, "thePersistInfo.createStamp"),true), new
	 * int[]{reqMultiIdx}); else if(sort.equals("userName")){ int peopleIdx = spec.appendClassList(nameClass,true);
	 * if(spec.getConditionCount()>0) spec.appendAnd(); SearchCondition sc = new SearchCondition( new ClassAttribute(targetClass,
	 * "owner.key.id"), "=", new ClassAttribute(nameClass,"userReference.key.id"));
	 * 
	 * spec.appendWhere(sc , new int[] {reqMultiIdx , peopleIdx});
	 * 
	 * spec.appendOrderBy(new OrderBy(new ClassAttribute(nameClass, "name"),Boolean.parseBoolean(orderType)), new int[]{peopleIdx});
	 * 
	 * } else{ OrderBy order = new OrderBy(new ClassAttribute(targetClass, sort),Boolean.parseBoolean(orderType));
	 * if(sort.equals("state.state")) order.setLocale(Locale.KOREA); spec.appendOrderBy(order , new int[]{reqMultiIdx}); }
	 */
	spec.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, "thePersistInfo.createStamp"), true), new int[] { reqMultiIdx });
	// out.println(spec);

	Kogger.debug(getClass(), "MultiApprovalServlet.getQuerySpecForSearch: spec=\n" + spec);

	return spec;
    }

    /*
     * 결과내 재검색용(Search In Result)
     */
    private QuerySpec getQuerySpecForSearchSIR(List<Map> mapList, TgPagingControl pager) throws Exception {

	QuerySpec spec = new QuerySpec();
	Class targetClass = KETWfmMultiApprovalRequest.class;
	Class nameClass = People.class;

	int reqMultiIdx = spec.appendClassList(targetClass, true);

	SearchCondition sc2 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, "=", "EPM");
	spec.appendWhere(sc2, new int[] { reqMultiIdx });
	spec.appendOr();
	SearchCondition sc3 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, true);
	spec.appendWhere(sc3, new int[] { reqMultiIdx });
	// spec.appendAnd();

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String reqName = paramMap.getString("reqName");
	    if (StringUtil.checkString(reqName)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "reqName", reqName, true);
	    }
	}

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String[] creatorAry = paramMap.getStringArray("creator");
	    if (creatorAry != null && creatorAry.length > 0) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		String[] idAry = new String[creatorAry.length];
		for (int j = 0; j < creatorAry.length; ++j) {
		    idAry[j] = String.valueOf(CommonUtil.getOIDLongValue(creatorAry[j]));
		}
		KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "owner.key.id", idAry, false);
	    }
	}

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String[] stateAry = paramMap.getStringArray("state");
	    if (stateAry != null && stateAry.length > 0) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		KETQueryUtil.setQuerySpecForMultiSearch(spec, targetClass, reqMultiIdx, "state.state", stateAry, false);
	    }
	}

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String reqStartDate = paramMap.getString("reqStartDate");
	    if (StringUtil.checkString(reqStartDate)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(
		        new SearchCondition(targetClass, "requestDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil
		                .convertStartDate2(reqStartDate)), new int[] { reqMultiIdx });
	    }
	}

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String reqEndDate = paramMap.getString("reqEndDate");
	    if (StringUtil.checkString(reqEndDate)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(
		        new SearchCondition(targetClass, "requestDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil
		                .convertEndDate2(reqEndDate)), new int[] { reqMultiIdx });
	    }
	}

	if (pager != null) {
	    String sortCol = pager.getSortCol();
	    if ("ReqName".equalsIgnoreCase(sortCol)) {
		spec.appendOrderBy(
		        new OrderBy(new ClassAttribute(targetClass, KETWfmMultiApprovalRequest.REQ_NAME), pager.getSortTypeForWcQuery()),
		        new int[] { reqMultiIdx });
	    } else if ("Creator".equalsIgnoreCase(sortCol)) {
		spec.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, "owner.key.id"), true), new int[] { reqMultiIdx });
	    } else if ("CreateDate".equalsIgnoreCase(sortCol)) {
		spec.appendOrderBy(
		        new OrderBy(new ClassAttribute(targetClass, KETWfmMultiApprovalRequest.REQUEST_DATE), pager.getSortTypeForWcQuery()),
		        new int[] { reqMultiIdx });
	    } else if ("Status".equalsIgnoreCase(sortCol)) {
		spec.appendOrderBy(
		        new OrderBy(new ClassAttribute(targetClass, KETWfmMultiApprovalRequest.LIFE_CYCLE_STATE), pager
		                .getSortTypeForWcQuery()), new int[] { reqMultiIdx });
	    } else {
		spec.appendOrderBy(
		        new OrderBy(new ClassAttribute(targetClass, KETWfmMultiApprovalRequest.CREATE_TIMESTAMP), pager
		                .getSortTypeForWcQuery()), new int[] { reqMultiIdx });
	    }
	} else {
	    spec.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, KETWfmMultiApprovalRequest.CREATE_TIMESTAMP), true),
		    new int[] { reqMultiIdx });
	}

	Kogger.debug(getClass(), "MultiApprovalServlet.getQuerySpecForSearchSIR: spec=\n" + spec);

	return spec;
    }

    private void excel(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과 내 재검색
	    /*
	     * QuerySpec qs = getQuerySpecForSearch(map); // 기존 검색 주석처리
	     */
	    boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	    List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchMultiApproval", searchInResult, req);
	    conditionList.add(paramMap);
	    QuerySpec qs = getQuerySpecForSearchSIR(conditionList, null);
	    // [End] 결과 내 재검색

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("result", qr);

	    gotoResult(req, res, "/jsp/wfm/SearchMultiApprovalExcel.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
