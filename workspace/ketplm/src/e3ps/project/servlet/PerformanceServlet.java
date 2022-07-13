package e3ps.project.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.Performance;
import e3ps.project.ProductProject;
import e3ps.project.Weights;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.PerformanceHelper;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class PerformanceServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getStrParameter(req.getParameter("command"));

	// 메뉴에서 클릭시 파라미터 값 전달 되지 않아 처리.
	if (command.equals("")) {
	    command = "search";
	}

	if (command.equalsIgnoreCase("searchProd")) {
	    searchProd(req, res);
	} else if (command.equalsIgnoreCase("searchProdGridData")) {
	    searchProdGrid(req, res, false);
	} else if (command.equalsIgnoreCase("searchProdGridPage")) {
	    searchProdGrid(req, res, true);
	} else if (command.equalsIgnoreCase("searchMoldGridData")) {
	    searchMoldGrid(req, res, false);
	} else if (command.equalsIgnoreCase("searchMoldGridPage")) {
	    searchMoldGrid(req, res, true);
	} else if (command.equalsIgnoreCase("searchMold")) {
	    searchMold(req, res);
	} else if (command.equalsIgnoreCase("excelDown")) {
	    excelDownPerformance(req, res);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 성과관리 검색 서블릿 적용 수정일자 : 2013. 6. 16 수정자 : 김종호
     */
    private void searchProdGrid(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPaging, paramMap); // -->추가

	    HashMap<String, String> hash = new HashMap<String, String>();
	    hash.put("radioValue", paramMap.getString("radioValue"));
	    hash.put("searchPjtNo", paramMap.getString("searchPjtNo"));
	    hash.put("searchPjtName", paramMap.getString("searchPjtName"));
	    hash.put("searchPart", paramMap.getString("searchPart"));
	    hash.put("searchPm", paramMap.getString("searchPm"));
	    hash.put("searchRank", KETParamMapUtil.toString(paramMap.getStringArray("searchRank")));
	    hash.put("searchDEVELOPENTTYPE", KETParamMapUtil.toString(paramMap.getStringArray("searchDEVELOPENTTYPE")));
	    hash.put("planStartStartDate", paramMap.getString("planStartStartDate"));
	    hash.put("planStartEndDate", paramMap.getString("planStartEndDate"));
	    hash.put("planEndStartDate", paramMap.getString("planEndStartDate"));
	    hash.put("planEndEndDate", paramMap.getString("planEndEndDate"));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("performanceSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("performanceSearchConditionList") != null) {

		    conditionList = (List<Map<String, String>>) session.getAttribute("performanceSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("performanceSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    PageControl pgCon = null;
	    if (isPaging) {
		// List<Map<String, Object>> edmList = performanceDao.searchPerformance(hash, conditionList, pager);
		pgCon = PerformanceHelper.manager.searchPerformancePaging(hash, conditionList, pager);

		// SessionUtil.setAttribute("pageSessionId", pgCon.getSessionId() + "");

		// DTO 객체로 변환 한다
		result = pgCon.getResult();
		// List<Sample> list = new ArrayList<Sample>();
		// while (result.hasMoreElements()) {
		// Object[] objArr = (Object[]) result.nextElement();
		// list.add((Sample) objArr[0]);
		// }

		// QuerySpec qs = null;
		// try {
		// qs = PerformanceHelper.manager.searchPerformance(hash, conditionList);
		// } catch (Exception e) {
		// Kogger.error(getClass(), e);
		// }
		// Kogger.debug(getClass(), "qs >> " + qs);
		// result = PersistenceHelper.manager.find(qs);

		Kogger.debug(getClass(), "searchProdGrid result:" + result.size());
		if (result.size() > 0) {

		    Object resultObj[] = null;
		    Performance pf = null;
		    E3PSProject project = null;
		    // E3PSProjectData data = null;

		    while (result.hasMoreElements()) {

			resultObj = (Object[]) result.nextElement();
			pf = (Performance) resultObj[0];
			String pjtNo = "";
			String pjtName = "";
			String partNo = "";
			String pm = "";
			String rank = "";
			String developeNtType1 = "";
			String developeNtType2 = "";
			String pjtPlanStartDate = "";
			String pjtPlanEndDate = "";

			String delivery = "";
			String cost = "";
			String quality = "";
			String Technical = "";
			String ecoCount = "";
			String totalScoreStr = "";
			String evaluate = "";
			int totalScore = 0;
			// etc
			String productOid = "";
			if (pf != null) {

			    pjtNo = pf.getKeyNo();
			    // Kogger.debug(getClass(), "searchProdGrid while pjtNo :" + pjtNo);
			    project = ProjectHelper.manager.getProject(pjtNo);
			    if (project != null) {
				// [Start]PLM 고도화 1차 수정 sw775.park
				productOid = CommonUtil.getOIDString(project);
				pjtName = StringUtil.checkNull(project.getPjtName());
				partNo = StringUtil.checkNull(((ProductProject) project).getPartNo());
				WTUser pjtPm = ProjectUserHelper.manager.getPM(project);
				if (pjtPm != null) {
				    PeopleData peopleData = new PeopleData(pjtPm);
				    pm = StringUtil.checkNull(peopleData.people.getName());
				} else {
				    pm = "";
				}
				rank = StringUtil.checkNull(project.getRank().getName());
				developeNtType1 = StringUtil.checkNull(project.getDevelopentType().getName());
				if (!developeNtType1.equals("")) {
				    developeNtType2 = developeNtType1.substring(0, 2);
				}
				ExtendScheduleData extendScheduleData = (ExtendScheduleData) project.getPjtSchedule().getObject();
				pjtPlanStartDate = DateUtil.getDateString(extendScheduleData.getPlanStartDate(), "D");
				pjtPlanEndDate = DateUtil.getDateString(extendScheduleData.getPlanEndDate(), "D");
				// [End]PLM 고도화 1차 수정 sw775.park
			    }
			    delivery = StringUtil.checkNull(pf.getScoreDelivery1());
			    cost = StringUtil.checkNull(pf.getScoreCost());
			    quality = StringUtil.checkNull(pf.getScoreQuality());
			    Technical = StringUtil.checkNull(pf.getScoreTechnical());
			    int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
			    ecoCount = "" + ecoCountInt;

			    Weights wg = null;
			    QueryResult wgQr = null;

			    wgQr = PerformanceHelper.manager.searchWeights(0, true, pjtNo);
			    Object[] wgobj = null;
			    if (wgQr.hasMoreElements()) {
				wgobj = (Object[]) wgQr.nextElement();
				wg = (Weights) wgobj[0];
			    }
			    if (wg != null) {
				if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
				        && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0
				        && pf.getScoreCost().length() > 0 && pf.getScoreDelivery1().length() > 0
				        && pf.getScoreTechnical().length() > 0) {
				    totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
					    + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
				    totalScoreStr = "" + totalScore;
				}
				if (totalScore != 0) {
				    if (wg.getTotalS() <= totalScore) {
					evaluate = "S";
				    } else if (wg.getTotalA() <= totalScore) {
					evaluate = "A";
				    } else if (wg.getTotalB() <= totalScore) {
					evaluate = "B";
				    } else if (wg.getTotalC() <= totalScore) {
					evaluate = "C";
				    } else {
					evaluate = "D";
				    }
				}
			    }
			}

			// pjtName = pjtName.replaceAll("'", "&apos;");
			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"\"");
			strBuffer.append(" DevelopType=\"" + developeNtType1 + "\"");
			strBuffer.append(" PjtNo=\"" + pjtNo + "\"" + " PjtNoOnClick=\"javascript:viewProject('" + productOid + "');\""
			        + " PjtNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			        + " PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PjtName=\"" + pjtName + "\"" + " PjtNameOnClick=\"javascript:viewProject('" + productOid
			        + "');\"" + " PjtNameHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			        + " PjtNameHtmlPostfix=\"</font>\"");
			strBuffer.append(" Pm=\"" + pm + "\"");
			strBuffer.append(" Rank=\"" + rank + "\"");
			strBuffer.append(" Quality=\"" + quality + "\"");
			strBuffer.append(" Cost=\"" + cost + "\"");
			strBuffer.append(" Delivery=\"" + delivery + "\"");
			strBuffer.append(" Technical=\"" + Technical + "\"");
			strBuffer.append(" EcoCount=\"" + ecoCount + "\"");
			strBuffer.append(" TotalScore=\"" + totalScore + "\"");
			strBuffer.append(" Evaluate=\"" + evaluate + "\"");
			strBuffer.append("/>");
		    }
		}
		// Kogger.debug(getClass(), "strBuffer.toString() :" + strBuffer.toString());

		req.setAttribute("hashMap", hash); // parameter 정리
		req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));

		// Kogger.debug(getClass(), "searchProdGrid currentPageNo :" + String.valueOf(pager.getCurrentPageNo()));
		// Kogger.debug(getClass(), "searchProdGrid pageSize :" + String.valueOf(pager.getPageSize()));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {
		int totalCount = PerformanceHelper.manager.searchPerformancePagingCount(hash, conditionList, pager);

		// Kogger.debug(getClass(), "searchProdGrid while totalCount :" + totalCount);
		// Kogger.debug(getClass(), "searchProdGrid pagingLength :" + String.valueOf(pager.getPagingLength(totalCount)));
		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	    // 1이면 제품
	    // 3이면 전자
	    // if (paramMap.getString("radioValue").equals("1")) {
	    // gotoResult(req, res, "/jsp/project/performance/ListPerformance1.jsp");
	    // } else if (paramMap.getString("radioValue").equals("3")) {
	    // gotoResult(req, res, "/jsp/project/performance/ListPerformance3.jsp");
	    // }
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 성과관리 검색 서블릿 적용 수정일자 : 2013. 6. 16 수정자 : 김종호
     */
    private void searchProd(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    HashMap<String, String> hash = new HashMap<String, String>();
	    hash.put("radioValue", paramMap.getString("radioValue"));
	    hash.put("searchPjtNo", paramMap.getString("searchPjtNo"));
	    hash.put("searchPjtName", paramMap.getString("searchPjtName"));
	    hash.put("searchPart", paramMap.getString("searchPart"));
	    hash.put("searchPm", paramMap.getString("searchPm"));
	    hash.put("searchRank", KETParamMapUtil.toString(paramMap.getStringArray("searchRank")));
	    hash.put("searchDEVELOPENTTYPE", KETParamMapUtil.toString(paramMap.getStringArray("searchDEVELOPENTTYPE")));
	    hash.put("planStartStartDate", paramMap.getString("planStartStartDate"));
	    hash.put("planStartEndDate", paramMap.getString("planStartEndDate"));
	    hash.put("planEndStartDate", paramMap.getString("planEndStartDate"));
	    hash.put("planEndEndDate", paramMap.getString("planEndEndDate"));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("performanceSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("performanceSearchConditionList") != null) {

		    conditionList = (List<Map<String, String>>) session.getAttribute("performanceSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("performanceSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    QuerySpec qs = null;
	    try {
		qs = PerformanceHelper.manager.searchPerformance(hash, conditionList);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    Kogger.debug(getClass(), "qs >> " + qs);
	    result = PersistenceHelper.manager.find(qs);

	    if (result.size() > 0) {

		Object resultObj[] = null;
		Performance pf = null;
		E3PSProject project = null;
		// E3PSProjectData data = null;

		while (result.hasMoreElements()) {

		    resultObj = (Object[]) result.nextElement();
		    pf = (Performance) resultObj[0];
		    String pjtNo = "";
		    String pjtName = "";
		    String partNo = "";
		    String pm = "";
		    String rank = "";
		    String developeNtType1 = "";
		    String developeNtType2 = "";
		    String pjtPlanStartDate = "";
		    String pjtPlanEndDate = "";

		    String delivery = "";
		    String cost = "";
		    String quality = "";
		    String Technical = "";
		    String ecoCount = "";
		    String totalScoreStr = "";
		    String evaluate = "";
		    int totalScore = 0;
		    // etc
		    String productOid = "";
		    if (pf != null) {
			pjtNo = pf.getKeyNo();
			project = ProjectHelper.manager.getProject(pjtNo);
			if (project != null) {
			    // [Start]PLM 고도화 1차 수정 sw775.park
			    productOid = CommonUtil.getOIDString(project);
			    pjtName = StringUtil.checkNull(project.getPjtName());
			    partNo = StringUtil.checkNull(((ProductProject) project).getPartNo());
			    WTUser pjtPm = ProjectUserHelper.manager.getPM(project);
			    if (pjtPm != null) {
				PeopleData peopleData = new PeopleData(pjtPm);
				pm = StringUtil.checkNull(peopleData.people.getName());
			    } else {
				pm = "";
			    }
			    rank = StringUtil.checkNull(project.getRank().getName());
			    developeNtType1 = StringUtil.checkNull(project.getDevelopentType().getName());
			    if (!developeNtType1.equals("")) {
				developeNtType2 = developeNtType1.substring(0, 2);
			    }
			    ExtendScheduleData extendScheduleData = (ExtendScheduleData) project.getPjtSchedule().getObject();
			    pjtPlanStartDate = DateUtil.getDateString(extendScheduleData.getPlanStartDate(), "D");
			    pjtPlanEndDate = DateUtil.getDateString(extendScheduleData.getPlanEndDate(), "D");
			    // [End]PLM 고도화 1차 수정 sw775.park
			}
			delivery = StringUtil.checkNull(pf.getScoreDelivery1());
			cost = StringUtil.checkNull(pf.getScoreCost());
			quality = StringUtil.checkNull(pf.getScoreQuality());
			Technical = StringUtil.checkNull(pf.getScoreTechnical());
			int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
			ecoCount = "" + ecoCountInt;

			Weights wg = null;
			QueryResult wgQr = null;

			wgQr = PerformanceHelper.manager.searchWeights(0, true, pjtNo);
			Object[] wgobj = null;
			if (wgQr.hasMoreElements()) {
			    wgobj = (Object[]) wgQr.nextElement();
			    wg = (Weights) wgobj[0];
			}
			if (wg != null) {
			    if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
				    && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0
				    && pf.getScoreCost().length() > 0 && pf.getScoreDelivery1().length() > 0
				    && pf.getScoreTechnical().length() > 0) {
				totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
				        + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
				totalScoreStr = "" + totalScore;
			    }
			    if (totalScore != 0) {
				if (wg.getTotalS() <= totalScore) {
				    evaluate = "S";
				} else if (wg.getTotalA() <= totalScore) {
				    evaluate = "A";
				} else if (wg.getTotalB() <= totalScore) {
				    evaluate = "B";
				} else if (wg.getTotalC() <= totalScore) {
				    evaluate = "C";
				} else {
				    evaluate = "D";
				}
			    }
			}
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" DevelopType=&quot;" + developeNtType1 + "&quot;");
		    strBuffer.append(" PjtNo=&quot;" + pjtNo + "&quot;" + " PjtNoOnClick=&quot;javascript:viewProject(&apos;" + productOid
			    + "&apos;);&quot;" + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" PjtName=&quot;" + pjtName + "&quot;" + " PjtNameOnClick=&quot;javascript:viewProject(&apos;"
			    + productOid + "&apos;);&quot;" + " PjtNameHtmlPrefix=&quot;&lt;font color=&apos;"
			    + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
			    + " PjtNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" Pm=&quot;" + pm + "&quot;");
		    strBuffer.append(" Rank=&quot;" + rank + "&quot;");
		    strBuffer.append(" Quality=&quot;" + quality + "&quot;");
		    strBuffer.append(" Cost=&quot;" + cost + "&quot;");
		    strBuffer.append(" Delivery=&quot;" + delivery + "&quot;");
		    strBuffer.append(" Technical=&quot;" + Technical + "&quot;");
		    strBuffer.append(" EcoCount=&quot;" + ecoCount + "&quot;");
		    strBuffer.append(" TotalScore=&quot;" + totalScore + "&quot;");
		    strBuffer.append(" Evaluate=&quot;" + evaluate + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	    // 1이면 제품
	    // 3이면 전자
	    if (paramMap.getString("radioValue").equals("1")) {
		gotoResult(req, res, "/jsp/project/performance/ListPerformance1.jsp");
	    } else if (paramMap.getString("radioValue").equals("3")) {
		gotoResult(req, res, "/jsp/project/performance/ListPerformance3.jsp");
	    }
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 성과관리 검색 서블릿 적용 수정일자 : 2013. 6. 16 수정자 : 김종호
     */
    private void searchMoldGrid(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPaging, paramMap); // -->추가

	    HashMap<String, String> hash = new HashMap<String, String>();
	    hash.put("radioValue", paramMap.getString("radioValue"));
	    hash.put("searchProductPjtNo", paramMap.getString("searchProductPjtNo"));
	    hash.put("searchPm", paramMap.getString("searchPm"));
	    hash.put("searchProductPm", paramMap.getString("searchProductPm"));
	    hash.put("devDeptOid", paramMap.getString("devDeptOid"));
	    hash.put("searchDieNo", paramMap.getString("searchDieNo"));
	    hash.put("searchProductPart", paramMap.getString("searchProductPart"));
	    hash.put("searchProductPartName", paramMap.getString("searchProductPartName"));
	    hash.put("outsourcing", paramMap.getString("outsourcing"));
	    hash.put("planStartStartDate", paramMap.getString("planStartStartDate"));
	    hash.put("planStartEndDate", paramMap.getString("planStartEndDate"));
	    hash.put("planEndStartDate", paramMap.getString("planEndStartDate"));
	    hash.put("planEndEndDate", paramMap.getString("planEndEndDate"));
	    hash.put("searchDEVELOPENTTYPE", KETParamMapUtil.toString(paramMap.getStringArray("searchDEVELOPENTTYPE")));
	    hash.put("moldProductType", KETParamMapUtil.toString(paramMap.getStringArray("moldProductType")));
	    hash.put("moldRank", KETParamMapUtil.toString(paramMap.getStringArray("moldRank")));
	    hash.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("performanceSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("performanceSearchConditionList") != null) {

		    conditionList = (List<Map<String, String>>) session.getAttribute("performanceSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("performanceSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    PageControl pgCon = null;
	    if (isPaging) {
		// List<Map<String, Object>> edmList = performanceDao.searchPerformance(hash, conditionList, pager);
		pgCon = PerformanceHelper.manager.searchPerformancePaging(hash, conditionList, pager);

		// DTO 객체로 변환 한다
		result = pgCon.getResult();

		// QuerySpec qs = null;
		// try {
		// qs = PerformanceHelper.manager.searchPerformance(hash, conditionList);
		// } catch (Exception e) {
		// Kogger.error(getClass(), e);
		// }
		// result = PersistenceHelper.manager.find(qs);

		if (result.size() > 0) {

		    Object resultObj[] = null;
		    Performance pf = null;
		    E3PSProject project = null;
		    ProductProject productProject = null;
		    MoldProject moldProject = null;
		    E3PSProjectData productData = null;
		    E3PSProjectData moldData = null;

		    while (result.hasMoreElements()) {

			resultObj = (Object[]) result.nextElement();
			pf = (Performance) resultObj[0];
			String pjtNo = "";
			String partName = "";
			String partNo = "";
			String dieNo = "";
			String moldPm = "";
			String productPm = "";
			String pmDepart = "";
			String DEVELOPENTTYPE1 = "";
			String productType = "";
			String rankMold = "";
			String makingType = "";
			String outsourcingType = "";
			String pjtPlanStartDate = "";
			String pjtPlanEndDate = "";
			String delivery = "";
			String cost = "";
			String ecoCount = "";
			String totalScoreStr = "";
			String evaluate = "";

			// etc
			String productOid = "";
			String moldOid = "";
			int totalScore = 0;
			if (pf != null) {
			    pjtNo = pf.getKeyNo();
			    project = ProjectHelper.manager.getProject(pjtNo);
			    Object objClass = (Object) project;
			    moldProject = (MoldProject) objClass;
			    if (moldProject != null) {
				moldData = new E3PSProjectData(moldProject);
				moldOid = moldData.e3psPjtOID;
			    }
			    if (moldProject != null) {
				MoldItemInfo moldInfo = moldProject.getMoldInfo();
				if (moldInfo == null) {
				    moldInfo = MoldItemInfo.newMoldItemInfo();
				}

				if (moldInfo != null) {
				    productProject = moldInfo.getProject();
				    if (project != null) {
					productData = new E3PSProjectData(productProject);
					productOid = productData.e3psPjtOID;
					pjtNo = productData.pjtNo;

				    }
				}
				partNo = StringUtil.checkNull(moldInfo.getPartNo());
				dieNo = StringUtil.checkNull(moldInfo.getDieNo());
				partName = StringUtil.checkNull(moldInfo.getPartName());
				if (moldData.pjtPm != null) {
				    moldPm = moldData.pjtPmName;
				    WTUser pmUser = moldData.pjtPm;
				    PeopleData pd = new PeopleData(pmUser);
				    pmDepart = pd.departmentName;
				}
				if (productData.pjtPm != null) {
				    productPm = productData.pjtPmName;
				}

				if (moldProject.getRank() != null) {
				    rankMold = moldProject.getRank().getName();
				}
				if (productProject.getDevelopentType() != null) {
				    DEVELOPENTTYPE1 = productProject.getDevelopentType().getName();
				}
				if (moldInfo.getProductType() != null) {
				    productType = moldInfo.getProductType().getName();
				}
				if (moldInfo.getMaking() != null && moldInfo.getMaking().length() > 0) {
				    makingType = moldInfo.getMaking();
				}
				if (moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0) {
				    outsourcingType = moldProject.getOutSourcing();
				}

				pjtPlanStartDate = DateUtil.getDateString(moldData.pjtPlanStartDate, "D");
				pjtPlanEndDate = DateUtil.getDateString(moldData.pjtPlanEndDate, "D");
			    }
			    int deliveryTotal = 0;
			    if (pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null && pf.getScoreDelivery3() != null) {
				deliveryTotal = Integer.parseInt(pf.getScoreDelivery1()) + +Integer.parseInt(pf.getScoreDelivery2())
				        + Integer.parseInt(pf.getScoreDelivery3());
			    }
			    delivery = "" + deliveryTotal;
			    cost = StringUtil.checkNull(pf.getScoreCost());
			    int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
			    ecoCount = "" + ecoCountInt;

			    Weights wg = null;
			    QueryResult wgQr = null;
			    wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
			    Object[] wgobj = null;
			    if (wgQr.hasMoreElements()) {
				wgobj = (Object[]) wgQr.nextElement();
				wg = (Weights) wgobj[0];
			    }
			    if (wg != null) {
				if (pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null
				        && pf.getScoreDelivery3() != null && pf.getScoreCost().length() > 0
				        && pf.getScoreDelivery1().length() > 0 && pf.getScoreDelivery2().length() > 0
				        && pf.getScoreDelivery3().length() > 0) {

				    totalScore = Integer.parseInt(pf.getScoreCost()) + Integer.parseInt(pf.getScoreDelivery1())
					    + Integer.parseInt(pf.getScoreDelivery2()) + Integer.parseInt(pf.getScoreDelivery3());
				    totalScoreStr = "" + totalScore;
				}
				if (totalScore != 0) {
				    if (wg.getTotalS() <= totalScore) {
					evaluate = "S";
				    } else if (wg.getTotalA() <= totalScore) {
					evaluate = "A";
				    } else if (wg.getTotalB() <= totalScore) {
					evaluate = "B";
				    } else if (wg.getTotalC() <= totalScore) {
					evaluate = "C";
				    } else {
					evaluate = "D";
				    }
				}
			    }
			}

			strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
			strBuffer.append(" RowNum=\"" + rowCount++ + "\"");
			strBuffer.append(" DevelopType=\"" + DEVELOPENTTYPE1 + "\"");
			strBuffer.append(" PjtNo=\"" + pjtNo + "\"" + " PjtNoOnClick=\"javascript:viewProject('" + productOid + "');\""
			        + " PjtNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			        + " PjtNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" DieNo=\"" + dieNo + "\"" + " DieNoOnClick=\"javascript:viewProject('" + moldOid + "');\""
			        + " DieNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\""
			        + " DieNoHtmlPostfix=\"</font>\"");
			strBuffer.append(" PartNo=\"" + partNo + "\"");
			strBuffer.append(" PartName=\"" + partName + "\"");
			strBuffer.append(" RankMold=\"" + rankMold + "\"");
			strBuffer.append(" MoldPm=\"" + moldPm + "\"");
			strBuffer.append(" ProductPm=\"" + productPm + "\"");
			strBuffer.append(" MakingType=\"" + makingType + "\"");
			strBuffer.append(" OutsorcingType=\"" + outsourcingType + "\"");
			strBuffer.append(" Delivery=\"" + delivery + "\"");
			strBuffer.append(" Cost=\"" + cost + "\"");
			strBuffer.append(" EcoCount=\"" + ecoCount + "\"");
			strBuffer.append(" TotalScore=\"" + totalScoreStr + "\"");
			strBuffer.append(" Evaluate=\"" + evaluate + "\"");
			strBuffer.append("/>");
		    }
		}

		req.setAttribute("hashMap", hash); // parameter 정리
		req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));

		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {
		int totalCount = PerformanceHelper.manager.searchPerformancePagingCount(hash, conditionList, pager);

		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	    // req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    // req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    // gotoResult(req, res, "/jsp/project/performance/ListPerformance2.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 성과관리 검색 서블릿 적용 수정일자 : 2013. 6. 16 수정자 : 김종호
     */
    private void searchMold(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    HashMap<String, String> hash = new HashMap<String, String>();
	    hash.put("radioValue", paramMap.getString("radioValue"));
	    hash.put("searchProductPjtNo", paramMap.getString("searchProductPjtNo"));
	    hash.put("searchPm", paramMap.getString("searchPm"));
	    hash.put("searchProductPm", paramMap.getString("searchProductPm"));
	    hash.put("devDeptOid", paramMap.getString("devDeptOid"));
	    hash.put("searchDieNo", paramMap.getString("searchDieNo"));
	    hash.put("searchProductPart", paramMap.getString("searchProductPart"));
	    hash.put("searchProductPartName", paramMap.getString("searchProductPartName"));
	    hash.put("outsourcing", paramMap.getString("outsourcing"));
	    hash.put("planStartStartDate", paramMap.getString("planStartStartDate"));
	    hash.put("planStartEndDate", paramMap.getString("planStartEndDate"));
	    hash.put("planEndStartDate", paramMap.getString("planEndStartDate"));
	    hash.put("planEndEndDate", paramMap.getString("planEndEndDate"));
	    hash.put("searchDEVELOPENTTYPE", KETParamMapUtil.toString(paramMap.getStringArray("searchDEVELOPENTTYPE")));
	    hash.put("moldProductType", KETParamMapUtil.toString(paramMap.getStringArray("moldProductType")));
	    hash.put("moldRank", KETParamMapUtil.toString(paramMap.getStringArray("moldRank")));
	    hash.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, String>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, String>>();
		session.setAttribute("performanceSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("performanceSearchConditionList") != null) {

		    conditionList = (List<Map<String, String>>) session.getAttribute("performanceSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, String>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("performanceSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    QuerySpec qs = null;
	    try {
		qs = PerformanceHelper.manager.searchPerformance(hash, conditionList);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    result = PersistenceHelper.manager.find(qs);

	    if (result.size() > 0) {

		Object resultObj[] = null;
		Performance pf = null;
		E3PSProject project = null;
		ProductProject productProject = null;
		MoldProject moldProject = null;
		E3PSProjectData productData = null;
		E3PSProjectData moldData = null;

		while (result.hasMoreElements()) {

		    resultObj = (Object[]) result.nextElement();
		    pf = (Performance) resultObj[0];
		    String pjtNo = "";
		    String partName = "";
		    String partNo = "";
		    String dieNo = "";
		    String moldPm = "";
		    String productPm = "";
		    String pmDepart = "";
		    String DEVELOPENTTYPE1 = "";
		    String productType = "";
		    String rankMold = "";
		    String makingType = "";
		    String outsourcingType = "";
		    String pjtPlanStartDate = "";
		    String pjtPlanEndDate = "";
		    String delivery = "";
		    String cost = "";
		    String ecoCount = "";
		    String totalScoreStr = "";
		    String evaluate = "";

		    // etc
		    String productOid = "";
		    String moldOid = "";
		    int totalScore = 0;
		    if (pf != null) {
			pjtNo = pf.getKeyNo();
			project = ProjectHelper.manager.getProject(pjtNo);
			Object objClass = (Object) project;
			moldProject = (MoldProject) objClass;
			if (moldProject != null) {
			    moldData = new E3PSProjectData(moldProject);
			    moldOid = moldData.e3psPjtOID;
			}
			if (moldProject != null) {
			    MoldItemInfo moldInfo = moldProject.getMoldInfo();
			    if (moldInfo == null) {
				moldInfo = MoldItemInfo.newMoldItemInfo();
			    }

			    if (moldInfo != null) {
				productProject = moldInfo.getProject();
				if (project != null) {
				    productData = new E3PSProjectData(productProject);
				    productOid = productData.e3psPjtOID;
				    pjtNo = productData.pjtNo;

				}
			    }
			    partNo = StringUtil.checkNull(moldInfo.getPartNo());
			    dieNo = StringUtil.checkNull(moldInfo.getDieNo());
			    partName = StringUtil.checkNull(moldInfo.getPartName());
			    if (moldData.pjtPm != null) {
				moldPm = moldData.pjtPmName;
				WTUser pmUser = moldData.pjtPm;
				PeopleData pd = new PeopleData(pmUser);
				pmDepart = pd.departmentName;
			    }
			    if (productData.pjtPm != null) {
				productPm = productData.pjtPmName;
			    }

			    if (moldProject.getRank() != null) {
				rankMold = moldProject.getRank().getName();
			    }
			    if (productProject.getDevelopentType() != null) {
				DEVELOPENTTYPE1 = productProject.getDevelopentType().getName();
			    }
			    if (moldInfo.getProductType() != null) {
				productType = moldInfo.getProductType().getName();
			    }
			    if (moldInfo.getMaking() != null && moldInfo.getMaking().length() > 0) {
				makingType = moldInfo.getMaking();
			    }
			    if (moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0) {
				outsourcingType = moldProject.getOutSourcing();
			    }

			    pjtPlanStartDate = DateUtil.getDateString(moldData.pjtPlanStartDate, "D");
			    pjtPlanEndDate = DateUtil.getDateString(moldData.pjtPlanEndDate, "D");
			}
			int deliveryTotal = 0;
			if (pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null && pf.getScoreDelivery3() != null) {
			    deliveryTotal = Integer.parseInt(pf.getScoreDelivery1()) + +Integer.parseInt(pf.getScoreDelivery2())
				    + Integer.parseInt(pf.getScoreDelivery3());
			}
			delivery = "" + deliveryTotal;
			cost = StringUtil.checkNull(pf.getScoreCost());
			int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
			ecoCount = "" + ecoCountInt;

			Weights wg = null;
			QueryResult wgQr = null;
			wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
			Object[] wgobj = null;
			if (wgQr.hasMoreElements()) {
			    wgobj = (Object[]) wgQr.nextElement();
			    wg = (Weights) wgobj[0];
			}
			if (wg != null) {
			    if (pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null
				    && pf.getScoreDelivery3() != null && pf.getScoreCost().length() > 0
				    && pf.getScoreDelivery1().length() > 0 && pf.getScoreDelivery2().length() > 0
				    && pf.getScoreDelivery3().length() > 0) {

				totalScore = Integer.parseInt(pf.getScoreCost()) + Integer.parseInt(pf.getScoreDelivery1())
				        + Integer.parseInt(pf.getScoreDelivery2()) + Integer.parseInt(pf.getScoreDelivery3());
				totalScoreStr = "" + totalScore;
			    }
			    if (totalScore != 0) {
				if (wg.getTotalS() <= totalScore) {
				    evaluate = "S";
				} else if (wg.getTotalA() <= totalScore) {
				    evaluate = "A";
				} else if (wg.getTotalB() <= totalScore) {
				    evaluate = "B";
				} else if (wg.getTotalC() <= totalScore) {
				    evaluate = "C";
				} else {
				    evaluate = "D";
				}
			    }
			}
		    }

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" DevelopType=&quot;" + DEVELOPENTTYPE1 + "&quot;");
		    strBuffer.append(" PjtNo=&quot;" + pjtNo + "&quot;" + " PjtNoOnClick=&quot;javascript:viewProject(&apos;" + productOid
			    + "&apos;);&quot;" + " PjtNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " PjtNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" DieNo=&quot;" + dieNo + "&quot;" + " DieNoOnClick=&quot;javascript:viewProject(&apos;" + moldOid
			    + "&apos;);&quot;" + " DieNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " DieNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" PartNo=&quot;" + partNo + "&quot;");
		    strBuffer.append(" PartName=&quot;" + partName + "&quot;");
		    strBuffer.append(" RankMold=&quot;" + rankMold + "&quot;");
		    strBuffer.append(" MoldPm=&quot;" + moldPm + "&quot;");
		    strBuffer.append(" ProductPm=&quot;" + productPm + "&quot;");
		    strBuffer.append(" MakingType=&quot;" + makingType + "&quot;");
		    strBuffer.append(" OutsorcingType=&quot;" + outsourcingType + "&quot;");
		    strBuffer.append(" Delivery=&quot;" + delivery + "&quot;");
		    strBuffer.append(" Cost=&quot;" + cost + "&quot;");
		    strBuffer.append(" EcoCount=&quot;" + ecoCount + "&quot;");
		    strBuffer.append(" TotalScore=&quot;" + totalScoreStr + "&quot;");
		    strBuffer.append(" Evaluate=&quot;" + evaluate + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/performance/ListPerformance2.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void excelDownPerformance(HttpServletRequest req, HttpServletResponse res) throws IOException {

	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	HashMap<String, String> hash = new HashMap<String, String>();
	hash.put("radioValue", paramMap.getString("radioValue"));
	hash.put("searchProductPjtNo", paramMap.getString("searchProductPjtNo"));
	hash.put("searchPm", paramMap.getString("searchPm"));
	hash.put("searchProductPm", paramMap.getString("searchProductPm"));
	hash.put("devDeptOid", paramMap.getString("devDeptOid"));
	hash.put("searchDieNo", paramMap.getString("searchDieNo"));
	hash.put("searchProductPart", paramMap.getString("searchProductPart"));
	hash.put("searchProductPartName", paramMap.getString("searchProductPartName"));
	hash.put("outsourcing", paramMap.getString("outsourcing"));
	hash.put("planStartStartDate", paramMap.getString("planStartStartDate"));
	hash.put("planStartEndDate", paramMap.getString("planStartEndDate"));
	hash.put("planEndStartDate", paramMap.getString("planEndStartDate"));
	hash.put("planEndEndDate", paramMap.getString("planEndEndDate"));
	hash.put("searchDEVELOPENTTYPE", KETParamMapUtil.toString(paramMap.getStringArray("searchDEVELOPENTTYPE")));
	hash.put("moldProductType", KETParamMapUtil.toString(paramMap.getStringArray("moldProductType")));
	hash.put("moldRank", KETParamMapUtil.toString(paramMap.getStringArray("moldRank")));
	hash.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making")));
	hash.put("searchPjtNo", paramMap.getString("searchPjtNo"));
	hash.put("searchPjtName", paramMap.getString("searchPjtName"));
	hash.put("searchPart", paramMap.getString("searchPart"));
	hash.put("searchRank", KETParamMapUtil.toString(paramMap.getStringArray("searchRank")));

	// [Start] 결과내 검색 조건 처리 //
	HttpSession session = req.getSession();
	List<Map<String, String>> conditionList = null;

	if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

	    conditionList = new ArrayList<Map<String, String>>();
	    session.setAttribute("performanceSearchConditionList", conditionList);
	} else {

	    if (session.getAttribute("performanceSearchConditionList") != null) {

		conditionList = (List<Map<String, String>>) session.getAttribute("performanceSearchConditionList");
	    } else {

		conditionList = new ArrayList<Map<String, String>>();
	    }
	}

	conditionList.add(hash);
	session.setAttribute("performanceSearchConditionList", conditionList);
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
	String sFileName = "Performance_" + ff.format(new Date()) + ".xls";

	// 파일명 한글 깨짐 방지
	if (ie) {
	    sFileName = URLEncoder.encode(sFileName, "utf-8");
	} else {
	    sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	}

	// Excel File Object
	File excelFileObj = new File(sFilePath + "/" + sFileName);

	try {
	    WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
	    WritableSheet sheet = workbook.createSheet("Performance", 1);

	    WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	    int row = 0;
	    if (paramMap.getString("radioValue").equals("2")) {
		// 금형
		sheet.setColumnView(0, 12);
		sheet.setColumnView(1, 30);
		sheet.setColumnView(2, 12);
		sheet.setColumnView(3, 12);
		sheet.setColumnView(4, 12);
		sheet.setColumnView(5, 12);
		sheet.setColumnView(6, 15);
		sheet.setColumnView(7, 15);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 15);
		sheet.setColumnView(11, 20);
		sheet.setColumnView(12, 20);
		sheet.setColumnView(13, 10);
		sheet.setColumnView(14, 10);
		sheet.setColumnView(15, 10);
		sheet.setColumnView(16, 10);
		sheet.setColumnView(17, 10);

		String titlesMold[] = new String[] { "Project No", "Project Name", "Part No", "Die No", "금형담당자", "개발담당자", "개발부서", "개발구분",
		        "금형분류", "금형Rank", "제작구분", "제작처", "개발시작일", "개발완료일", "일정", "비용", "설변", "총점", "평가", "제품구분" };
		for (int i = 0; i < titlesMold.length; i++) {
		    sheet.addCell(new Label(i, row, titlesMold[i], titleformat));
		}
	    } else {
		// 제품
		sheet.setColumnView(0, 15);
		sheet.setColumnView(1, 30);
		sheet.setColumnView(2, 15);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 15);
		sheet.setColumnView(5, 15);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 15);
		sheet.setColumnView(11, 15);
		sheet.setColumnView(12, 15);
		sheet.setColumnView(13, 15);
		sheet.setColumnView(14, 15);
		sheet.setColumnView(15, 15);
		sheet.setColumnView(16, 15);
		sheet.setColumnView(17, 15);
		sheet.setColumnView(18, 15);
		sheet.setColumnView(19, 15);

		String titlesProduct[] = new String[] { "Project No", "Project Name", "Part No", "PM", "Rank", "개발구분", "개발시작일", "개발완료일",
		        "일정", "비용", "품질", "기술", "설변", "총점", "평가", "대표차종", "제품구분", "고객" };
		for (int i = 0; i < titlesProduct.length; i++) {
		    sheet.addCell(new Label(i, row, titlesProduct[i], titleformat));
		}
	    }
	    QuerySpec spec = PerformanceHelper.manager.searchPerformance(hash, conditionList);
	    QueryResult rs = PersistenceHelper.manager.find(spec);
	    Performance pf = null;
	    E3PSProject project = null;
	    E3PSProjectData data = null;
	    ProductProject productProject = null;
	    MoldProject moldProject = null;
	    E3PSProjectData productData = null;
	    E3PSProjectData moldData = null;
	    if (paramMap.getString("radioValue").equals("2")) {
		while (rs.hasMoreElements()) {
		    ++row;
		    int columnIndex = 0;
		    Object[] obj = (Object[]) rs.nextElement();
		    pf = (Performance) obj[0];
		    String pjtNo = "";
		    String pjtName = "";
		    String partNo = "";
		    String dieNo = "";
		    String moldPm = "";
		    String productPm = "";
		    String pmDepart = "";
		    String DEVELOPENTTYPE1 = "";
		    String productType = "";
		    String rankMold = "";
		    String makingType = "";
		    String outsourcingType = "";
		    String pjtPlanStartDate = "";
		    String pjtPlanEndDate = "";
		    String delivery = "";
		    String cost = "";
		    String ecoCount = "";
		    String totalScoreStr = "";
		    String evaluate = "";
		    String projectType = "";

		    pjtNo = pf.getKeyNo();
		    project = ProjectHelper.manager.getProject(pjtNo);
		    Object objClass = (Object) project;
		    moldProject = (MoldProject) objClass;
		    if (moldProject != null) {
			moldData = new E3PSProjectData(moldProject);
		    }
		    if (moldProject != null) {
			MoldItemInfo moldInfo = moldProject.getMoldInfo();
			if (moldInfo == null) {
			    moldInfo = MoldItemInfo.newMoldItemInfo();
			}
			if (moldInfo != null) {
			    productProject = moldInfo.getProject();
			    if (project != null) {
				productData = new E3PSProjectData(productProject);
				pjtNo = productData.pjtNo;
				pjtName = productData.pjtName;
			    }
			}
			partNo = StringUtil.checkNull(moldInfo.getPartNo());
			dieNo = StringUtil.checkNull(moldInfo.getDieNo());

			if (moldData.pjtPm != null) {
			    moldPm = moldData.pjtPmName;

			}
			if (productData.pjtPm != null) {
			    productPm = productData.pjtPmName;
			    projectType = productData.producttypeName;
			    pmDepart = productData.department;
			}

			if (moldProject.getRank() != null) {
			    rankMold = moldProject.getRank().getName();
			}
			if (productProject.getDevelopentType() != null) {
			    DEVELOPENTTYPE1 = productProject.getDevelopentType().getName();
			}
			if (moldInfo.getProductType() != null) {
			    productType = moldInfo.getProductType().getName();
			}
			if (moldInfo.getMaking() != null && moldInfo.getMaking().length() > 0) {
			    makingType = moldInfo.getMaking();
			}
			if (moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0) {
			    outsourcingType = moldProject.getOutSourcing();
			}

			pjtPlanStartDate = DateUtil.getDateString(moldData.pjtPlanStartDate, "D");
			pjtPlanEndDate = DateUtil.getDateString(moldData.pjtPlanEndDate, "D");
		    }

		    delivery = StringUtil.checkNull(pf.getScoreDelivery1());
		    cost = StringUtil.checkNull(pf.getScoreCost());
		    // 설계 변경 건수
		    int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
		    ecoCount = "" + ecoCountInt;
		    Weights wg = null;
		    QueryResult wgQr = null;
		    int totalScore = 0;
		    String totalEvaluate = "";
		    wgQr = PerformanceHelper.manager.searchWeights(0, true, pjtNo);
		    Object[] wgobj = null;
		    if (wgQr.hasMoreElements()) {
			wgobj = (Object[]) wgQr.nextElement();
			wg = (Weights) wgobj[0];
		    }
		    if (wg != null) {
			if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
			        && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0 && pf.getScoreCost().length() > 0
			        && pf.getScoreDelivery1().length() > 0 && pf.getScoreTechnical().length() > 0) {
			    totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
				    + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
			    totalScoreStr = "" + totalScore;
			}
			if (totalScore != 0) {
			    if (wg.getTotalS() <= totalScore) {
				evaluate = "S";
			    } else if (wg.getTotalA() <= totalScore) {
				evaluate = "A";
			    } else if (wg.getTotalB() <= totalScore) {
				evaluate = "B";
			    } else if (wg.getTotalC() <= totalScore) {
				evaluate = "C";
			    } else {
				evaluate = "D";
			    }
			}
		    }

		    sheet.addCell(new Label(columnIndex++, row, pjtNo));
		    sheet.addCell(new Label(columnIndex++, row, pjtName));
		    sheet.addCell(new Label(columnIndex++, row, partNo));
		    sheet.addCell(new Label(columnIndex++, row, dieNo));
		    sheet.addCell(new Label(columnIndex++, row, moldPm));
		    sheet.addCell(new Label(columnIndex++, row, productPm));
		    sheet.addCell(new Label(columnIndex++, row, pmDepart));
		    sheet.addCell(new Label(columnIndex++, row, DEVELOPENTTYPE1));
		    sheet.addCell(new Label(columnIndex++, row, productType));
		    sheet.addCell(new Label(columnIndex++, row, rankMold));
		    sheet.addCell(new Label(columnIndex++, row, makingType));
		    sheet.addCell(new Label(columnIndex++, row, outsourcingType));
		    sheet.addCell(new Label(columnIndex++, row, pjtPlanStartDate));
		    sheet.addCell(new Label(columnIndex++, row, pjtPlanEndDate));
		    sheet.addCell(new Label(columnIndex++, row, delivery));
		    sheet.addCell(new Label(columnIndex++, row, cost));
		    sheet.addCell(new Label(columnIndex++, row, ecoCount));
		    sheet.addCell(new Label(columnIndex++, row, totalScoreStr));
		    sheet.addCell(new Label(columnIndex++, row, evaluate));
		    sheet.addCell(new Label(columnIndex++, row, projectType));
		}

	    } else {
		while (rs.hasMoreElements()) {
		    ++row;
		    int columnIndex = 0;

		    Object[] obj = (Object[]) rs.nextElement();
		    pf = (Performance) obj[0];
		    String pjtNo = "";
		    String pjtName = "";
		    String partNo = "";
		    String pm = "";
		    String rank = "";
		    String DEVELOPENTTYPE1 = "";
		    String pjtPlanStartDate = "";
		    String pjtPlanEndDate = "";
		    String delivery = "";
		    String cost = "";
		    String quality = "";
		    String Technical = "";
		    String ecoCount = "";
		    String totalScoreStr = "";
		    String evaluate = "";
		    String representModel = "";
		    String productType = "";
		    String dependenceCar = "";

		    pjtNo = pf.getKeyNo();
		    project = ProjectHelper.manager.getProject(pjtNo);
		    if (project != null) {
			data = new E3PSProjectData(project);
			pjtName = data.pjtName;
			partNo = StringUtil.checkNull(data.partNo);
			pm = data.pjtPmName;
			rank = data.rankName;
			DEVELOPENTTYPE1 = StringUtil.checkNull(data.developenttypeName);
			pjtPlanStartDate = DateUtil.getDateString(data.pjtPlanStartDate, "D");
			pjtPlanEndDate = DateUtil.getDateString(data.pjtPlanEndDate, "D");
			representModel = data.representModel;
			productType = data.producttypeName;
			dependenceCar = data.dependence;
		    }
		    delivery = StringUtil.checkNull(pf.getScoreDelivery1());
		    cost = StringUtil.checkNull(pf.getScoreCost());
		    quality = StringUtil.checkNull(pf.getScoreQuality());
		    Technical = StringUtil.checkNull(pf.getScoreTechnical());
		    // 설계변경 건수
		    int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
		    ecoCount = "" + ecoCountInt;
		    Weights wg = null;
		    QueryResult wgQr = null;
		    int totalScore = 0;
		    String totalEvaluate = "";
		    wgQr = PerformanceHelper.manager.searchWeights(0, true, pjtNo);
		    Object[] wgobj = null;
		    if (wgQr.hasMoreElements()) {
			wgobj = (Object[]) wgQr.nextElement();
			wg = (Weights) wgobj[0];
		    }
		    if (wg != null) {
			if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
			        && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0 && pf.getScoreCost().length() > 0
			        && pf.getScoreDelivery1().length() > 0 && pf.getScoreTechnical().length() > 0) {
			    totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
				    + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
			    totalScoreStr = "" + totalScore;
			}
			if (totalScore != 0) {
			    if (wg.getTotalS() <= totalScore) {
				evaluate = "S";
			    } else if (wg.getTotalA() <= totalScore) {
				evaluate = "A";
			    } else if (wg.getTotalB() <= totalScore) {
				evaluate = "B";
			    } else if (wg.getTotalC() <= totalScore) {
				evaluate = "C";
			    } else {
				evaluate = "D";
			    }
			}
		    }
		    sheet.addCell(new Label(columnIndex++, row, pjtNo));
		    sheet.addCell(new Label(columnIndex++, row, pjtName));
		    sheet.addCell(new Label(columnIndex++, row, partNo));
		    sheet.addCell(new Label(columnIndex++, row, pm));
		    sheet.addCell(new Label(columnIndex++, row, rank));
		    sheet.addCell(new Label(columnIndex++, row, DEVELOPENTTYPE1));
		    sheet.addCell(new Label(columnIndex++, row, pjtPlanStartDate));
		    sheet.addCell(new Label(columnIndex++, row, pjtPlanEndDate));
		    sheet.addCell(new Label(columnIndex++, row, delivery));
		    sheet.addCell(new Label(columnIndex++, row, cost));
		    sheet.addCell(new Label(columnIndex++, row, quality));
		    sheet.addCell(new Label(columnIndex++, row, Technical));
		    sheet.addCell(new Label(columnIndex++, row, ecoCount));
		    sheet.addCell(new Label(columnIndex++, row, totalScoreStr));
		    sheet.addCell(new Label(columnIndex++, row, evaluate));
		    sheet.addCell(new Label(columnIndex++, row, representModel));
		    sheet.addCell(new Label(columnIndex++, row, productType));
		    sheet.addCell(new Label(columnIndex++, row, dependenceCar));
		}
	    }

	    workbook.write();
	    workbook.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
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
	} finally {
	    if (excelFileObj.isFile()) {
		excelFileObj.delete();
	    }
	}
    }

    private WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    if (color != null)
		format.setBackground(color);

	    format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return format;
    }
}
