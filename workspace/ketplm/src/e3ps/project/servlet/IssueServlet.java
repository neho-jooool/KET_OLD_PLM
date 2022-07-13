package e3ps.project.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.StringSearch;
import wt.session.SessionHelper;
import wt.util.WTPropertyVetoException;
import e3ps.common.content.ContentInfo;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.project.E3PSProject;
import e3ps.project.IssueType;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectIssue;
import e3ps.project.ReviewProject;
import e3ps.project.beans.IssueAnswerComparator;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectIssueAnswerData;
import e3ps.project.beans.ProjectIssueData;
import e3ps.project.beans.ProjectIssueHelper;
import e3ps.project.dao.IssueDao;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class IssueServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getStrParameter(req.getParameter("command"));

	// 메뉴에서 클릭시 파라미터 값 전달 되지 않아 처리.
	if (command.equals("")) {
	    command = "search";
	}

	if (command.equalsIgnoreCase("search")) { // 전체 Issue 검색
	    search(req, res);
	} else if (command.equalsIgnoreCase("searchData")) { // 전체 Issue 검색 Data
	    searchGrid(req, res, false);
	} else if (command.equalsIgnoreCase("searchPage")) { // 전체 Issue 검색 Page
	    searchGrid(req, res, true);
	} else if (command.equalsIgnoreCase("excelDown")) { // 전체 Issue 검색 Excel Download
	    excelDownIssue(req, res);
	} else if (command.equalsIgnoreCase("searchMyIssue")) { // My Issue
	    searchMyIssue(req, res);
	} else if (command.equalsIgnoreCase("excelDownMyIssue")) { // My Issue Excel Download
	    excelDownMyIssue(req, res);
	} else if (command.equalsIgnoreCase("searchProjectIssue")) {
	    searchProjectIssue(req, res);
	} else if (command.equalsIgnoreCase("searchProjectIssuePopup")) {
	    searchProjectIssuePopup(req, res);
	} else if (command.equalsIgnoreCase("searchDataByProjectNo")) { // 프로젝트별 이슈
	    searchGridByProject(req, res, false);
	} else if (command.equalsIgnoreCase("searchDataByProjectNoPage")) { // 프로젝트별 이슈
	    searchGridByProject(req, res, true);
	}
    }

    /*
     * [Project 별 이슈 현황 리스트 2017.10.30 by 황정태]
     */
    private void searchGridByProject(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();

	String resultStr = ""; // 검색 결과 제한 건수 초과 여부
	int resultSize = 0; // 검색 결과 표시 건수

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());
	    TgPagingControl pager = new TgPagingControl(false, paramMap);

	    // 다국어처리
	    paramMap.put("locale", messageService.getLocale());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();

	    E3PSProject project = (E3PSProject) CommonUtil.getObject(paramMap.getString("oid"));

	    long oidValue = CommonUtil.getOIDLongValue(project);

	    Long[] oids = new Long[1];
	    oids[0] = oidValue;

	    if (isPaging) {

		// QueryResult qr = ProjectIssueHelper.manager.getProjectIssue(project);

		QuerySpec qs = new QuerySpec();
		Class mainClass = ProjectIssue.class;
		int mainClassPos = qs.addClassList(mainClass, true);

		if (project instanceof ProductProject && project.getDevRequest() != null
		        && StringUtil.checkString(project.getDevRequest().getProjectOID())) {
		    // ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
		    String reviewPjtNos[] = project.getReviewPjtNo().split(",");
		    oids = new Long[reviewPjtNos.length + 1];
		    oids[0] = oidValue;
		    for (int i = 0; i < reviewPjtNos.length; i++) {
			E3PSProject rp = ProjectHelper.getProject(reviewPjtNos[i]);
			oids[i + 1] = CommonUtil.getOIDLongValue(rp);
		    }

		}

		// qs.appendWhere(new SearchCondition(mainClass, "projectReference.key.id", SearchCondition.IN, new ArrayExpression("","")),
		// mainClassPos);
		// Long[] oids = { oidValue, reviewOid };

		qs.appendWhere(new SearchCondition(new ClassAttribute(ProjectIssue.class, "projectReference.key.id"), SearchCondition.IN,
		        new ArrayExpression(oids)), new int[] { mainClassPos });

		if (!StringUtil.isTrimToEmpty(pager.getSortCol())) {
		    String sortName = pager.getSortCol();
		    String sortType = pager.getSortType();

		    if (sortName.equals("IssueType")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.ISSUE_TYPE, false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.ISSUE_TYPE, true);
			}

		    } else if (sortName.equals("Subject")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.SUBJECT, false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.SUBJECT, true);
			}
		    } else if (sortName.equals("Owner")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, "owner.key.id", false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, "owner.key.id", true);
			}
		    } else if (sortName.equals("Manager")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, "manager.key.id", false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, "manager.key.id", true);
			}
		    } else if (sortName.equals("IsDone")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.IS_DONE, false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.IS_DONE, true);
			}
		    } else if (sortName.equals("CreateDate")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.CREATE_DATE, false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.CREATE_DATE, true);
			}
		    } else if (sortName.equals("IsAegis")) {
			if (sortType.equals("ASC")) {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.AEGIS_TRANS, false);
			} else {
			    SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.AEGIS_TRANS, true);
			}
		    } else {
			SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.CREATE_DATE, true);
			SearchUtil.setOrderBy(qs, ProjectIssue.class, mainClassPos, ProjectIssue.IS_DONE, false);

		    }
		} else {

		}

		QueryResult qr = PersistenceHelper.manager.find(qs);

		int rowCount = 1;

		while (qr.hasMoreElements()) {
		    Object[] objArr = (Object[]) qr.nextElement();
		    ProjectIssue issue = (ProjectIssue) objArr[0];
		    ProjectIssueData data = new ProjectIssueData(issue);
		    String pjtType = "";
		    if (issue.getProject() instanceof ProductProject) {
			pjtType = "개발";
		    } else if (issue.getProject() instanceof ReviewProject) {
			pjtType = "검토";
		    }

		    String isDone = "";
		    if (data.isCheck) {
			isDone = "완료";
		    } else {
			isDone = "미완료";
		    }

		    String fileIcon = "";
		    if (data.isQuestionAttache) {
			if (data.questionAttacheVec.size() > 0) {
			    ContentInfo info = (ContentInfo) data.questionAttacheVec.get(0);
			    fileIcon = "<img src='/plm/portal/images/icon_file.gif' border='0'>";
			}
		    }

		    String deleteIcon = "";
		    boolean isAdmin = CommonUtil.isAdmin();
		    if (("개발".equals(pjtType) && (data.isQuestionUser) && !data.isCheck) || isAdmin) {
			deleteIcon = " IssueDel=\"" + "<img src='/plm/portal/images/icon_delete.gif' border='0'>"
			        + "\" IssueDelType=\"Html\"" + " IssueDelOnClick=\"javascript:deleteIssue('" + data.oid + "');\"";
		    } else {
			deleteIcon = " IssueDel=\"&nbsp;\"";
		    }
		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
		    strBuffer.append(" IssueNo=\"" + rowCount + "\"");
		    strBuffer.append(" PjtType=\"" + pjtType + "\"");
		    strBuffer.append(" IssueType=\"" + data.issueType + "\"");
		    strBuffer.append(" Subject=\"" + data.subject + "\"" + " SubjectOnClick=\"javascript:viewIssue('" + data.oid + "');\""
			    + " SubjectHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			    + " SubjectHtmlPostfix=\"</font>\"");
		    strBuffer.append(" Owner=\"" + data.questionUser.name + "\"");
		    strBuffer.append(" OwnerDept=\"" + StringUtil.checkNull(data.questionUser.departmentName) + "\"");
		    strBuffer.append(" CreateDate=\"" + DateUtil.getTimeFormat(data.questionDate, "yy-MM-dd") + "\"");
		    strBuffer.append(" Manager=\"" + data.managerUser.name + "\"");
		    strBuffer.append(" IsAegis=\"" + StringUtil.checkNull(issue.getAegisTrans()) + "\"");
		    strBuffer.append(" IsDone=\"" + isDone + "\"");
		    strBuffer.append(" Document=\"" + fileIcon + "\"");
		    strBuffer.append(deleteIcon);
		    strBuffer.append("/>");

		    rowCount++;

		    Vector answerVec = data.issueAnswer;
		    Collections.sort(answerVec, new IssueAnswerComparator(false));
		    for (int i = 0; i < answerVec.size(); i++) {
			ProjectIssueAnswerData answerData = (ProjectIssueAnswerData) answerVec.get(i);

			String answerSubject = "<img src='/plm/portal/images/icon_re.gif' border='0'>Re )";

			String answerIsDone = "";
			if (data.isCheck) {
			    answerIsDone = "완료";
			} else {
			    answerIsDone = "미완료";
			}

			fileIcon = "";
			if (answerData.isAnswerAttache) {
			    if (answerData.answerAttacheVec.size() > 0) {
				ContentInfo info = (ContentInfo) answerData.answerAttacheVec.get(0);
				fileIcon = "<img src='/plm/portal/images/icon_file.gif' border='0'>";
			    }
			}
			deleteIcon = "";
			if (("개발".equals(pjtType) && answerData.isAnswerUser && !data.isCheck) || isAdmin) {
			    deleteIcon = " IssueDel=\"" + "<img src='/plm/portal/images/icon_delete.gif' border='0'>"
				    + "\" IssueDelType=\"Html\"" + " IssueDelOnClick=\"javascript:deleteIssue('" + answerData.oid + "');\"";
			} else {
			    deleteIcon = " IssueDel=\"&nbsp;\"";
			}
			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" IssueNo=\"" + " " + "\"");
			strBuffer.append(" PjtType=\"" + "" + "\"");
			strBuffer.append(" IssueType=\"" + "" + "\"");
			strBuffer.append(" Subject=\"" + answerSubject + answerData.answerStr + "\""
			        + " SubjectOnClick=\"javascript:viewIssue('" + answerData.oid + "');\""
			        + " SubjectHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			        + " SubjectHtmlPostfix=\"</font>\"");
			strBuffer.append(" Owner=\"" + data.questionUser.name + "\"");
			strBuffer.append(" OwnerDept=\"" + StringUtil.checkNull(answerData.answerUser.departmentName) + "\"");
			strBuffer.append(" CreateDate=\"" + DateUtil.getTimeFormat(answerData.answerDate, "yy-MM-dd") + "\"");
			strBuffer.append(" IsAegis=\"" + StringUtil.checkNull(issue.getAegisTrans()) + "\"");
			strBuffer.append(" Manager=\"" + answerData.answerUser.name + "\"");
			strBuffer.append(" IsDone=\"" + answerIsDone + "\"");
			strBuffer.append(" Document=\"" + fileIcon + "\"");
			strBuffer.append(deleteIcon);
			strBuffer.append("/>");
		    }

		}

		req.setAttribute("tgData", strBuffer.toString());

		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {
		QueryResult qr = ProjectIssueHelper.manager.getProjectIssue(oids);
		System.out.println("qr.size() : " + qr.size());
		req.setAttribute("rootCount", String.valueOf(qr.size() + 1));
		req.setAttribute("pagingLength", String.valueOf(qr.size() + 1));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Issue 검색 서블릿 적용 수정일자 : 2013. 6. 26 수정자 : 김종호
     */
    private void searchGrid(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String resultStr = ""; // 검색 결과 제한 건수 초과 여부
	int resultSize = 0; // 검색 결과 표시 건수

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());
	    // 다국어처리
	    paramMap.put("locale", messageService.getLocale());

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPaging, paramMap); // -->추가

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectIssueTotalSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("projectIssueTotalSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    IssueDao dao = new IssueDao(conn);
	    // 목록 결과
	    List<Map<String, Object>> list = null;// dao.searchIssueList(conditionList);

	    if (isPaging) {

		list = dao.searchIssueGridList(conditionList, pager);

		if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

		    // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		    resultSize = list.size();
		} else {

		    // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		    resultSize = PropertiesUtil.getSearchGridCountLimit();

		    resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		}

		for (int i = 0; i < resultSize; i++) {

		    Map<String, Object> obj = list.get(i);

		    String isDone = "";
		    if (obj.get("isDone").equals("1")) {
			isDone = messageService.getString("e3ps.message.ket_message", "02171");// 완료
		    } else {
			if (obj.get("answerPlanDate") != null && !obj.get("answerPlanDate").toString().equalsIgnoreCase("null")
			        && !obj.get("answerPlanDate").equals("")) {
			    if (DateUtil.getCurrentTimestamp().compareTo(Timestamp.valueOf(obj.get("answerPlanDate").toString())) < 0) {
				// 지연
				isDone = "&lt;font color=&apos;red&apos;&gt;"
				        + messageService.getString("e3ps.message.ket_message", "02703") + "&lt;/font&gt;";
			    } else {
				isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
			    }
			} else {
			    isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
			}
		    }

		    String issueType = "";
		    IssueType[] issueTypeList = IssueType.getIssueTypeSet();
		    for (int j = 0; j < issueTypeList.length; j++) {
			if (issueTypeList[j].getDisplay(Locale.ENGLISH).equals(obj.get("issueType").toString())) {
			    issueType = issueTypeList[j].getDisplay(messageService.getLocale());
			    break;
			}
		    }

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
		    strBuffer.append(" RowNum=\"\"");// rowCount++
		    strBuffer.append(" PjtNo=\"" + TreeGridUtil.replaceContentForI(obj.get("pjtNo").toString()) + "\""
			    + " PjtNoOnClick=\"javascript:openView('" + obj.get("pjtOid") + "');\"" + " PjtNoHtmlPrefix=\"<font color='"
			    + PropertiesUtil.getSearchGridLinkColor() + "'>\"" + " PjtNoHtmlPostfix=\"</font>\"");
		    strBuffer.append(" PjtName=\"" + TreeGridUtil.replaceContentForI(obj.get("pjtName").toString()) + "\"");
		    strBuffer.append(" IssueType=\"" + issueType + "\"");
		    strBuffer.append(" Subject=\"" + TreeGridUtil.replaceContentForI(obj.get("subject").toString()) + "\""
			    + " SubjectOnClick=\"javascript:viewIssue('" + obj.get("issueOid") + "');\""
			    + " SubjectHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			    + " SubjectHtmlPostfix=\"</font>\"");
		    strBuffer.append(" Urgency=\"" + obj.get("urgency") + "\"");
		    strBuffer.append(" Importance=\"" + obj.get("importance") + "\"");
		    strBuffer.append(" OwnerName=\"" + obj.get("ownerName") + "\"");
		    strBuffer.append(" ManagerName=\"" + obj.get("managerName") + "\"");
		    strBuffer.append(" CreateDate=\"" + obj.get("createDate") + "\"");
		    strBuffer.append(" IsDone=\"" + isDone + "\"");
		    strBuffer.append("/>");
		}

		// Kogger.debug(getClass(), strBuffer.toString());
		req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));

		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {
		int totalCount = dao.searchIssueGridListCount(conditionList);

		// Kogger.debug(getClass(), "searchProdGrid while totalCount :" + totalCount);
		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Issue 검색 서블릿 적용 수정일자 : 2013. 6. 26 수정자 : 김종호
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String resultStr = ""; // 검색 결과 제한 건수 초과 여부
	int resultSize = 0; // 검색 결과 표시 건수

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());
	    // 다국어처리
	    paramMap.put("locale", messageService.getLocale());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectIssueTotalSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("projectIssueTotalSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    IssueDao dao = new IssueDao(conn);
	    // 목록 결과
	    List<Map<String, Object>> list = dao.searchIssueList(conditionList);

	    if (!PropertiesUtil.getSearchGridCountLimitFlag() || list.size() <= PropertiesUtil.getSearchGridCountLimit()) {

		// 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		resultSize = list.size();
	    } else {

		// 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		resultSize = PropertiesUtil.getSearchGridCountLimit();

		resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
	    }

	    for (int i = 0; i < resultSize; i++) {

		Map<String, Object> obj = list.get(i);

		String isDone = "";
		if (obj.get("isDone").equals("1")) {
		    isDone = messageService.getString("e3ps.message.ket_message", "02171");// 완료
		} else {
		    if (obj.get("answerPlanDate") != null && !obj.get("answerPlanDate").toString().equalsIgnoreCase("null")
			    && !obj.get("answerPlanDate").equals("")) {
			if (DateUtil.getCurrentTimestamp().compareTo(Timestamp.valueOf(obj.get("answerPlanDate").toString())) < 0) {
			    // 지연
			    isDone = "&lt;font color=&apos;red&apos;&gt;" + messageService.getString("e3ps.message.ket_message", "02703")
				    + "&lt;/font&gt;";
			} else {
			    isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
			}
		    } else {
			isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
		    }
		}

		String issueType = "";
		IssueType[] issueTypeList = IssueType.getIssueTypeSet();
		for (int j = 0; j < issueTypeList.length; j++) {
		    if (issueTypeList[j].getDisplay(Locale.ENGLISH).equals(obj.get("issueType").toString())) {
			issueType = issueTypeList[j].getDisplay(messageService.getLocale());
			break;
		    }
		}

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" PjtNo=&quot;" + TreeGridUtil.replaceContentForI(obj.get("pjtNo").toString()) + "&quot;"
		        + " PjtNoOnClick=&quot;javascript:viewProject(&apos;" + obj.get("pjtOid") + "&apos;);&quot;"
		        + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
		        + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" PjtName=&quot;" + TreeGridUtil.replaceContentForI(obj.get("pjtName").toString()) + "&quot;");
		strBuffer.append(" IssueType=&quot;" + issueType + "&quot;");
		strBuffer.append(" Subject=&quot;" + TreeGridUtil.replaceContentForI(obj.get("subject").toString()) + "&quot;"
		        + " SubjectOnClick=&quot;javascript:viewIssue(&apos;" + obj.get("issueOid") + "&apos;);&quot;"
		        + " SubjectHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
		        + " SubjectHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" Urgency=&quot;" + obj.get("urgency") + "&quot;");
		strBuffer.append(" Importance=&quot;" + obj.get("importance") + "&quot;");
		strBuffer.append(" Owner=&quot;" + obj.get("ownerName") + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + obj.get("createDate") + "&quot;");
		strBuffer.append(" IsDone=&quot;" + isDone + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    req.setAttribute("resultStr", resultStr); // 검색 결과 건수 제한 여부(제한될 경우 T)
	    gotoResult(req, res, "/jsp/project/ProjectIssueTotalList.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    private void excelDownIssue(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;
	String[] dtype2 = null;
	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectIssueTotalSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("projectIssueTotalSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("projectIssueTotalSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = req.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";
	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }
	    String sFileName = "ProjectIssueList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    QuerySpec qs = new QuerySpec();

	    Class<ProjectIssue> target = ProjectIssue.class;
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

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		// 프로젝트 번호
		String projectNo = map.getString("projectNo");
		if (projectNo != null && projectNo.length() > 0) {
		    StringSearch stringsearch = new StringSearch("pjtNo");
		    stringsearch.setValue(projectNo.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(E3PSProject.class), new int[] { idx_project });
		}

		// 이슈 완료여부
		String[] category = map.getStringArray("category");
		if (category != null && category.length > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    KETQueryUtil.setQuerySpecForMultiSearch(qs, target, idx_target, "isDone", category, false);
		}

		// 프로젝트 명
		String projectName = map.getString("projectName");
		if (projectName != null && projectName.length() > 0) {
		    StringSearch stringsearch = new StringSearch("pjtName");
		    stringsearch.setValue(projectName.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(E3PSProject.class), new int[] { idx_project });
		}

		// 담당자
		if (map.getString("manager") != "") {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    StringTokenizer managerTypeToken = new StringTokenizer(map.getString("manager"), ", ");
		    while (managerTypeToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil
			                .getOIDLongValue(managerTypeToken.nextToken())), new int[] { idx_target });
			if (managerTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		// 제목
		String subject = map.getString("subject");
		if (subject != null && subject.length() > 0) {
		    StringSearch stringsearch = new StringSearch("subject");
		    stringsearch.setValue(subject.trim());
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
		}

		// 제기자
		if (map.getString("owner") != "") {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendOpenParen();
		    StringTokenizer ownerTypeToken = new StringTokenizer(map.getString("owner"), ", ");
		    while (ownerTypeToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil
			                .getOIDLongValue(ownerTypeToken.nextToken())), new int[] { idx_target });
			if (ownerTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		// 제기 일자(시작 ~ 끝)
		String startCreateDate = map.getString("startCreateDate");
		String endCreateDate = map.getString("endCreateDate");
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

		// 구분
		String[] type = map.getStringArray("type");
		if (type != null && type.length > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    KETQueryUtil.setQuerySpecForMultiSearch(qs, target, idx_target, "issueType", type, false);
		}

		// 긴급여부
		String[] urgency = map.getStringArray("urgency");
		if (urgency != null && urgency.length > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    KETQueryUtil.setQuerySpecForMultiSearch(qs, ProjectIssue.class, idx_target, "urgency", urgency, false);
		}

		// 중요도
		String[] importance = map.getStringArray("importance");
		if (importance != null && importance.length > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    KETQueryUtil.setQuerySpecForMultiSearch(qs, ProjectIssue.class, idx_target, "importance", importance, false);
		}

		String orgName = map.getString("orgName");
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
		dtype2 = map.getStringArray("dType1");
	    }
	    SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet sheet = workbook.createSheet("ProjectIssueList", 1);
		WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);
		WritableCellFormat fontformat = getCellFontFormat(jxl.format.Colour.RED);

		int row = 0;

		String titles[] = new String[] { "No", "단계", "Project No", "Project Name", "구분", "제목", "Issue내용", "긴급도", "중요도", "제기자",
		        "제기부서", "제기일자", "PM Comment", "Issue해결방안", "담당자", "담당부서", "완료내용등록", "해결방안등록일자", "완료일자", "완료여부" };

		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));
		}

		result = PersistenceHelper.manager.find(qs);

		if (result.size() > 0) {

		    Object resultObj[] = null;

		    while (result.hasMoreElements()) {

			resultObj = (Object[]) result.nextElement();
			ProjectIssue issue = (ProjectIssue) resultObj[0];
			ProjectIssueData data = new ProjectIssueData(issue);

			int dtype = data.project.getPjtType();
			String pjtType = "";
			if (data.project instanceof ProductProject) {
			    dtype = dtype == 2 ? 1 : 2;
			    pjtType = "개발";
			} else if (data.project instanceof ReviewProject) {
			    dtype = dtype == 1 ? 1 : 2;
			    pjtType = "검토";
			} else if (data.project instanceof MoldProject) {
			    dtype = dtype == 3 ? 1 : 2;
			    pjtType = "금형";
			}
			boolean goFlag = false;

			if (dtype2 == null) {
			    goFlag = true;
			}
			for (String type : dtype2) {
			    if (dtype == Integer.parseInt(type)) {
				goFlag = true;
			    }
			}

			if (!goFlag) {
			    continue;
			}

			++row;
			int columnIndex = 0;

			String answer = "";
			String answerdate = null;
			Vector answerVec = ProjectIssueHelper.manager.getIssueAnswer(issue);
			for (int i = 0; i < answerVec.size(); i++) {
			    ProjectIssueAnswerData answerData = (ProjectIssueAnswerData) answerVec.get(i);
			    answerdate = DateUtil.getDateString(answerData.answerDate, "date");
			    answer = answerData.answerStr;
			}

			String pjtNoPrint = data.project.getPjtNo();
			String pjtNamePrint = data.project.getPjtName();
			String issueTypePrint = data.issueType;
			String subjectPrint = data.subject;
			String urgencyPrint = data.urgency;
			String importancePrint = data.importance;

			String questionUserPrint = data.questionUser.name;
			String questionUserDeptPrint = data.questionUser.departmentName;
			String questionDatePrint = DateUtil.getDateString(data.questionDate, "d");
			String managerUserPrint = data.managerUser.name;
			String managerUserDeptPrint = data.managerUser.departmentName;
			String isDonePrint = issue.isIsDone() ? "완료" : "미완료";
			String endDate = DateUtil.getDateString(data.planDoneDate, "d");

			String question = data.question;
			String pmComment = (String) issue.getAegisComment();

			String issueAttr = issue.getIssueAttr1();

			boolean isRedFont = !"하".equals(urgencyPrint) || !"하".equals(importancePrint);

			/*
		         * Kogger.debug(getClass(), "no=="+pjtNoPrint); Kogger.debug(getClass(), "name=="+pjtNamePrint);
		         * Kogger.debug(getClass(), "구분=="+issueTypePrint); Kogger.debug(getClass(), "제목=="+subjectPrint);
		         * Kogger.debug(getClass(), "긴급도=="+urgencyPrint); Kogger.debug(getClass(), "중요도=="+importancePrint);
		         * Kogger.debug(getClass(), "제기자=="+questionUserPrint); Kogger.debug(getClass(), "제기일자=="+questionDatePrint);
		         * Kogger.debug(getClass(), "담당자=="+managerUserPrint); Kogger.debug(getClass(), "완료여부=="+isDonePrint);
		         */

			sheet.addCell(new Label(columnIndex++, row, Integer.toString(row)));
			sheet.addCell(new Label(columnIndex++, row, pjtType));
			sheet.addCell(new Label(columnIndex++, row, pjtNoPrint));
			sheet.addCell(new Label(columnIndex++, row, pjtNamePrint));
			sheet.addCell(new Label(columnIndex++, row, issueTypePrint));
			if (isRedFont) {
			    sheet.addCell(new Label(columnIndex++, row, subjectPrint, fontformat));
			} else {
			    sheet.addCell(new Label(columnIndex++, row, subjectPrint));
			}

			sheet.addCell(new Label(columnIndex++, row, question));
			sheet.addCell(new Label(columnIndex++, row, urgencyPrint));
			sheet.addCell(new Label(columnIndex++, row, importancePrint));
			sheet.addCell(new Label(columnIndex++, row, questionUserPrint)); // 제기자
			sheet.addCell(new Label(columnIndex++, row, questionUserDeptPrint)); // 제기부서
			sheet.addCell(new Label(columnIndex++, row, questionDatePrint)); // 제기일자
			sheet.addCell(new Label(columnIndex++, row, pmComment)); // PM Comment
			sheet.addCell(new Label(columnIndex++, row, answer)); // issue 해결방안
			sheet.addCell(new Label(columnIndex++, row, managerUserPrint)); // 담당자
			sheet.addCell(new Label(columnIndex++, row, managerUserDeptPrint)); // 담당부서
			sheet.addCell(new Label(columnIndex++, row, issueAttr)); // 완료내용등록
			sheet.addCell(new Label(columnIndex++, row, answerdate));
			sheet.addCell(new Label(columnIndex++, row, endDate));
			sheet.addCell(new Label(columnIndex++, row, isDonePrint));

		    }
		    workbook.write();
		    workbook.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		String contentID = sFileName.substring(0, sFileName.lastIndexOf("."));
		excelFileObj = E3PSDRMHelper.downloadExcel(excelFileObj, sFileName, contentID, req.getRemoteAddr());
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		res.setContentLength(ifilesize);
		res.setContentType("application/vnd.ms-excel;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = res.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }
	}
	// catch (ServletException e) {
	// Kogger.error(getClass(), e);
	// }
	catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Issue 검색 서블릿 적용 수정일자 : 2013. 6. 26 수정자 : 김종호
     */
    private void searchMyIssue(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());
	    // 다국어처리
	    paramMap.put("locale", messageService.getLocale());
	    // session 으로 로그인 사용자 찾기
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    paramMap.put("user", CommonUtil.getOIDLongValue(user));

	    // 처음 들어 왔는 경우 체크 - 수신 Issue만 표시 되도록 설정
	    if (!paramMap.getString("searchYN").equals("Y")) {
		// paramMap.put("myAnswerList", "myAnswerList");
		paramMap.put("myIssueList", "all");

		// Issue Type Combo 설정
		paramMap.put("myIssueType", "MyAnswerIssue");
		String category[] = { "0" };
		paramMap.put("category", category);
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("myIssueSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("myIssueSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("myIssueSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("myIssueSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    IssueDao dao = new IssueDao(conn);
	    // 목록 결과
	    List<Map<String, Object>> list = dao.searchIssueList(conditionList);
	    for (int i = 0; i < list.size(); i++) {

		Map<String, Object> obj = list.get(i);

		String isDone = "";
		if (obj.get("isDone").equals("1")) {
		    isDone = messageService.getString("e3ps.message.ket_message", "02171");// 완료
		} else {
		    if (obj.get("answerPlanDate") != null && !obj.get("answerPlanDate").toString().equalsIgnoreCase("null")
			    && !obj.get("answerPlanDate").equals("")) {
			if (DateUtil.getCurrentTimestamp().compareTo(Timestamp.valueOf(obj.get("answerPlanDate").toString())) < 0) {
			    // 지연
			    // isDone = "&lt;font color=&apos;red&apos;&gt;" + messageService.getString("e3ps.message.ket_message", "02703")
			    // + "&lt;/font&gt;";
			    isDone = messageService.getString("e3ps.message.ket_message", "02703");
			} else {
			    isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
			}
		    } else {
			isDone = messageService.getString("e3ps.message.ket_message", "01454");// 미완료
		    }
		}

		String issueType = "";
		IssueType[] issueTypeList = IssueType.getIssueTypeSet();
		for (int j = 0; j < issueTypeList.length; j++) {
		    if (issueTypeList[j].getDisplay(Locale.ENGLISH).equals(obj.get("issueType").toString())) {
			issueType = issueTypeList[j].getDisplay(messageService.getLocale());
			break;
		    }
		}

		String pjtOid = obj.get("pjtOid").toString();
		String taskOid = obj.get("taskOid").toString();
		int type = 0;
		if (taskOid.equals(":0")) {
		    taskOid = pjtOid;
		    if (obj.get("pjtTypeName").equals("금형")) {
			type = 3;
		    }
		    if (obj.get("pjtTypeName").equals("제품")) {
			type = 2;
		    } else {
			type = 1;
		    }
		}

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" PjtNo=&quot;" + obj.get("pjtNo") + "&quot;" + " PjtNoOnClick=&quot;javascript:openView(&apos;" + taskOid
		        + "&apos;);&quot;" + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
		        + "&apos;&gt;&quot;" + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" PjtName=&quot;" + obj.get("pjtName") + "&quot;" + " PjtNameOnClick=&quot;javascript:openView(&apos;"
		        + taskOid + "&apos;);&quot;" + " PjtNameHtmlPrefix=&quot;&lt;font color=&apos;"
		        + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " PjtNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" IssueType=&quot;" + issueType + "&quot;");
		strBuffer.append(" Subject=&quot;" + obj.get("subject") + "&quot;" + " SubjectOnClick=&quot;javascript:viewIssue(&apos;"
		        + obj.get("issueOid") + "&apos;);&quot;" + " SubjectHtmlPrefix=&quot;&lt;font color=&apos;"
		        + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " SubjectHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" Urgency=&quot;" + obj.get("urgency") + "&quot;");
		strBuffer.append(" Importance=&quot;" + obj.get("importance") + "&quot;");
		strBuffer.append(" Owner=&quot;" + obj.get("ownerName") + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + obj.get("createDate") + "&quot;");
		strBuffer.append(" IsDone=&quot;" + isDone + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/projectIssueList.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    private void excelDownMyIssue(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("myIssueSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("myIssueSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("myIssueSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("myIssueSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = req.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";
	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }
	    String sFileName = "MyIssueList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    QuerySpec qs = new QuerySpec();

	    Class<ProjectIssue> target = ProjectIssue.class;
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

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		String myIssueList = map.getString("myIssueList");
		String myAnswerList = map.getString("myAnswerList");
		if ((StringUtil.checkString(myIssueList)) && (StringUtil.checkString(myAnswerList))) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    // MY 제기이슈 || MY 담당이슈
		    qs.appendOpenParen();
		    qs.appendWhere(new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendOr();
		    qs.appendWhere(new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(user)),
			    new int[] { idx_target });
		    qs.appendCloseParen();
		} else if ((StringUtil.checkString(myIssueList)) && ("all".equals(myIssueList))) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
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
		String[] category = map.getStringArray("category");
		if (category != null && category.length > 0) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    KETQueryUtil.setQuerySpecForMultiSearch(qs, target, idx_target, "isDone", category, false);
		}

		// 제기 일자(시작 ~ 끝)
		String startCreateDate = map.getString("startCreateDate");
		String endCreateDate = map.getString("endCreateDate");
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
	    SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet sheet = workbook.createSheet("ProjectIssueList", 1);
		WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

		int row = 0;

		String titles[] = new String[] { "No", "Project No", "Project Name", "구분", "제목", "긴급도", "중요도", "제기자", "제기일자", "담당자",
		        "해결방안등록일자", "완료일자", "완료여부" };

		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));
		}

		// 결과목록 조회
		result = PersistenceHelper.manager.find(qs);

		if (result.size() > 0) {

		    Object resultObj[] = null;

		    while (result.hasMoreElements()) {

			resultObj = (Object[]) result.nextElement();
			ProjectIssue issue = (ProjectIssue) resultObj[0];
			ProjectIssueData data = new ProjectIssueData(issue);

			++row;
			int columnIndex = 0;

			String answerdate = null;
			Vector answerVec = ProjectIssueHelper.manager.getIssueAnswer(issue);
			for (int i = 0; i < answerVec.size(); i++) {
			    ProjectIssueAnswerData answerData = (ProjectIssueAnswerData) answerVec.get(i);
			    answerdate = DateUtil.getDateString(answerData.answerDate, "date");
			}

			sheet.addCell(new Label(columnIndex++, row, Integer.toString(row)));
			sheet.addCell(new Label(columnIndex++, row, data.project.getPjtNo()));
			sheet.addCell(new Label(columnIndex++, row, data.project.getPjtName()));
			sheet.addCell(new Label(columnIndex++, row, data.issueType));
			sheet.addCell(new Label(columnIndex++, row, data.subject));
			sheet.addCell(new Label(columnIndex++, row, data.urgency));
			sheet.addCell(new Label(columnIndex++, row, data.importance));
			sheet.addCell(new Label(columnIndex++, row, data.questionUser.name));
			sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(data.questionDate, "d")));
			sheet.addCell(new Label(columnIndex++, row, data.managerUser.name));
			sheet.addCell(new Label(columnIndex++, row, answerdate));
			sheet.addCell(new Label(columnIndex++, row, DateUtil.getDateString(data.planDoneDate, "d")));
			sheet.addCell(new Label(columnIndex++, row, issue.isIsDone() ? "완료" : data.stateProgress2));
		    }
		    workbook.write();
		    workbook.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		String contentID = sFileName.substring(0, sFileName.lastIndexOf("."));
		excelFileObj = E3PSDRMHelper.downloadExcel(excelFileObj, sFileName, contentID, req.getRemoteAddr());
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		res.setContentLength(ifilesize);
		res.setContentType("application/vnd.ms-excel;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = res.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }
	}
	// catch (ServletException e) {
	// Kogger.error(getClass(), e);
	// }
	catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Issue 검색 서블릿 적용 수정일자 : 2013. 7. 9 수정자 : 김종호
     */
    private void searchProjectIssue(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    QuerySpec qs = new QuerySpec();

	    Class<ProjectIssue> target = ProjectIssue.class;
	    int idx_target = qs.addClassList(target, true);

	    String projectOid = map.getString("projectOid");
	    if (projectOid != null && projectOid.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendWhere(
		        new SearchCondition(target, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(projectOid)), new int[] { idx_target });
	    }

	    // 이슈 완료여부
	    String[] category = map.getStringArray("category");
	    if (category != null && category.length > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		KETQueryUtil.setQuerySpecForMultiSearch(qs, target, idx_target, "isDone", category, false);
	    }

	    String manager = map.getString("manager");
	    if (StringUtil.checkString(manager)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendOpenParen();
		StringTokenizer managerToken = new StringTokenizer(manager, ", ");
		while (managerToken.hasMoreTokens()) {
		    qs.appendWhere(
			    new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(managerToken
			            .nextToken())), new int[] { idx_target });
		    if (managerToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }

	    // 제목
	    String subject = map.getString("subject");
	    if (subject != null && subject.length() > 0) {
		StringSearch stringsearch = new StringSearch("subject");
		stringsearch.setValue(subject.trim());
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
	    }

	    String owner = map.getString("owner");
	    if (StringUtil.checkString(owner)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendOpenParen();
		StringTokenizer ownerToken = new StringTokenizer(owner, ", ");
		while (ownerToken.hasMoreTokens()) {
		    qs.appendWhere(
			    new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ownerToken
			            .nextToken())), new int[] { idx_target });
		    if (ownerToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }

	    // 제기 일자(시작 ~ 끝)
	    String startCreateDate = map.getString("startCreateDate");
	    String endCreateDate = map.getString("endCreateDate");
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

	    SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    // 결과목록 조회
	    result = PersistenceHelper.manager.find(qs);

	    if (result.size() > 0) {

		Object resultObj[] = null;

		while (result.hasMoreElements()) {
		    resultObj = (Object[]) result.nextElement();
		    ProjectIssue issue = (ProjectIssue) resultObj[0];
		    ProjectIssueData data = new ProjectIssueData(issue);

		    String isDone = "";
		    if (issue.isIsDone()) {
			isDone = "완료";
		    } else {
			isDone = "미완료";
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" IssueType=&quot;" + data.issueType + "&quot;");
		    strBuffer.append(" Subject=&quot;" + data.subject + "&quot;" + " SubjectOnClick=&quot;javascript:viewIssue(&apos;"
			    + data.oid + "&apos;);&quot;" + " SubjectHtmlPrefix=&quot;&lt;font color=&apos;"
			    + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
			    + " SubjectHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" Urgency=&quot;" + data.urgency + "&quot;");
		    strBuffer.append(" Importance=&quot;" + data.importance + "&quot;");
		    strBuffer.append(" Owner=&quot;" + data.questionUser.name + "&quot;");
		    strBuffer.append(" CreateDate=&quot;" + DateUtil.getDateString(data.questionDate, "d") + "&quot;");
		    strBuffer.append(" Manager=&quot;" + data.managerUser.name + "&quot;");
		    strBuffer.append(" IsDone=&quot;" + isDone + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/ProjectIssueViewList.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Issue 검색 서블릿 적용 수정일자 : 2013. 7. 9 수정자 : 김종호
     */
    private void searchProjectIssuePopup(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    QuerySpec qs = new QuerySpec();

	    Class<ProjectIssue> target = ProjectIssue.class;
	    int idx_target = qs.addClassList(target, true);

	    String projectOid = map.getString("projectOid");
	    if (projectOid != null && projectOid.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendWhere(
		        new SearchCondition(target, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil
		                .getOIDLongValue(projectOid)), new int[] { idx_target });
	    }

	    // 이슈 완료여부
	    String[] category = map.getStringArray("category");
	    if (category != null && category.length > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		KETQueryUtil.setQuerySpecForMultiSearch(qs, target, idx_target, "isDone", category, false);
	    }

	    String manager = map.getString("manager");
	    if (StringUtil.checkString(manager)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendOpenParen();
		StringTokenizer managerToken = new StringTokenizer(manager, ", ");
		while (managerToken.hasMoreTokens()) {
		    qs.appendWhere(
			    new SearchCondition(target, "manager.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(managerToken
			            .nextToken())), new int[] { idx_target });
		    if (managerToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }

	    // 제목
	    String subject = map.getString("subject");
	    if (subject != null && subject.length() > 0) {
		StringSearch stringsearch = new StringSearch("subject");
		stringsearch.setValue(subject.trim());
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(stringsearch.getSearchCondition(ProjectIssue.class), new int[] { idx_target });
	    }

	    String owner = map.getString("owner");
	    if (StringUtil.checkString(owner)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		qs.appendOpenParen();
		StringTokenizer ownerToken = new StringTokenizer(owner, ", ");
		while (ownerToken.hasMoreTokens()) {
		    qs.appendWhere(
			    new SearchCondition(target, "owner.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ownerToken
			            .nextToken())), new int[] { idx_target });
		    if (ownerToken.hasMoreTokens())
			qs.appendOr();
		}
		qs.appendCloseParen();
	    }

	    // 제기 일자(시작 ~ 끝)
	    String startCreateDate = map.getString("startCreateDate");
	    String endCreateDate = map.getString("endCreateDate");
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

	    SearchUtil.setOrderBy(qs, target, idx_target, "thePersistInfo.modifyStamp", "modifyStamp", true);

	    // 결과목록 조회
	    result = PersistenceHelper.manager.find(qs);

	    if (result.size() > 0) {

		Object resultObj[] = null;

		while (result.hasMoreElements()) {
		    resultObj = (Object[]) result.nextElement();
		    ProjectIssue issue = (ProjectIssue) resultObj[0];
		    ProjectIssueData data = new ProjectIssueData(issue);

		    String isDone = "";
		    if (issue.isIsDone()) {
			isDone = "완료";
		    } else {
			isDone = "미완료";
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" IssueType=&quot;" + data.issueType + "&quot;");
		    strBuffer.append(" Subject=&quot;" + data.subject + "&quot;");
		    strBuffer.append(" Urgency=&quot;" + data.urgency + "&quot;");
		    strBuffer.append(" Importance=&quot;" + data.importance + "&quot;");
		    strBuffer.append(" Owner=&quot;" + data.questionUser.name + "&quot;");
		    strBuffer.append(" CreateDate=&quot;" + DateUtil.getDateString(data.questionDate, "d") + "&quot;");
		    strBuffer.append(" Manager=&quot;" + data.managerUser.name + "&quot;");
		    strBuffer.append(" IsDone=&quot;" + isDone + "&quot;");
		    strBuffer.append(" IssueOid=&quot;" + data.oid + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/ecm/ProjectIssueViewListPopup.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
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
	    Kogger.error(IssueServlet.class, e);
	}
	return format;
    }

    private static WritableCellFormat getCellFontFormat(jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
		    jxl.format.UnderlineStyle.NO_UNDERLINE, color));
	} catch (Exception e) {
	    Kogger.error(IssueServlet.class, e);
	}
	return format;
    }
}
