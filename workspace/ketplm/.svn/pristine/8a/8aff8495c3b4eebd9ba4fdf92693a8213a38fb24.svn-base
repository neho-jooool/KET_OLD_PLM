package e3ps.ecm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.beans.MoldPlanBeans;
import e3ps.ecm.dao.MoldPlanDao;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.shared.log.Kogger;

public class KETMoldPlanServlet extends CommonServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	RequestDispatcher rd = null;
	FormUploader uploader = null;
	String command = "";
	String planOid = "";
	KETMoldChangePlan plan = null;
	Hashtable<String, String> reqData = new Hashtable<String, String>();
	Vector attachFiles = null;
	KETProjectDocument[] refDocs = null;
	boolean isDelete = false;
	String contentType = req.getContentType();
	command = StringUtil.checkNull(req.getParameter("cmd"));
	if (command.equals("ViewPopup")) {
	    MoldPlanBeans beans2 = new MoldPlanBeans();
	    try {
		planOid = req.getParameter("oid");
		plan = KETEcmHelper.service.getMoldPlan(planOid);
		req.setAttribute("plan", plan);
		ArrayList<Hashtable<String, String>> commentList = beans2.getUserComments(plan.getScheduleId());
		req.setAttribute("commentList", commentList);
		req.setAttribute("prePage", req.getParameter("prePage"));
		rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewMoldPlanPopup.jsp");
		rd.forward(req, res);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	} else {

	    if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {

		uploader = FormUploader.newFormUploader(req, res, getServletContext());
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

		command = paramMap.getString("cmd");
		if (command.equals("Create")) {
		    reqData.put("dieNo", paramMap.getString("die_no"));
		    reqData.put("partNo", StringUtil.checkNull(paramMap.getString("part_no")));
		    reqData.put("partName", StringUtil.checkNull(paramMap.getString("part_name")));

		    reqData.put("type", StringUtil.checkNull(paramMap.getString("type")));
		    reqData.put("prodDeptName", StringUtil.checkNull(paramMap.getString("prod_dept_name")));
		    reqData.put("vendorFlag", StringUtil.checkNull(paramMap.getString("vendor_flag")));
		    reqData.put("vendorCode", StringUtil.checkNull(paramMap.getString("vendor_code")));
		    reqData.put("regBasis", StringUtil.checkNull(paramMap.getString("reg_basis")));
		    reqData.put("basisDate", StringUtil.checkNull(paramMap.getString("basis_date")));
		    reqData.put("prodEcoOwnerOid", StringUtil.checkNull(paramMap.getString("p_owner_oid")));
		    reqData.put("moldEcoOwnerOid", StringUtil.checkNull(paramMap.getString("m_owner_oid")));
		    reqData.put("modifyDesc", StringUtil.checkNull(paramMap.getString("modify_desc")));

		    reqData.put("prodPlanDate", StringUtil.checkNull(paramMap.getString("prod_plan_date")));
		    reqData.put("moldEcoPlanDate", StringUtil.checkNull(paramMap.getString("mold_eco_plan_date")));
		    reqData.put("storePlanDate", StringUtil.checkNull(paramMap.getString("store_plan_date")));
		    reqData.put("workPlanDate", StringUtil.checkNull(paramMap.getString("work_plan_date")));
		    reqData.put("assPlanDate", StringUtil.checkNull(paramMap.getString("ass_plan_date")));
		    reqData.put("tryPlanDate", StringUtil.checkNull(paramMap.getString("try_plan_date")));
		    reqData.put("testPlanDate", StringUtil.checkNull(paramMap.getString("test_plan_date")));
		    reqData.put("approvePlanDate", StringUtil.checkNull(paramMap.getString("approve_plan_date")));

		    reqData.put("measureType", StringUtil.checkNull(paramMap.getString("measure_type")));
		    reqData.put("failAction", StringUtil.checkNull(paramMap.getString("fail_action")));
		    reqData.put("result", StringUtil.checkNull(paramMap.getString("result")));
		    reqData.put("measureDate", StringUtil.checkNull(paramMap.getString("measure_date")));

		    reqData.put("planDesc", StringUtil.checkNull(paramMap.getString("plan_desc")));
		    reqData.put("ATTRIBUTE1", StringUtil.checkNull(paramMap.getString("ATTRIBUTE1")));

		    String[] docOidArr = paramMap.getStringArray("docOid");

		    attachFiles = uploader.getFiles();

		    try {
			MoldPlanBeans beans = new MoldPlanBeans();

			if (docOidArr != null && docOidArr.length > 0) {
			    refDocs = beans.getNewRefDocList(docOidArr);
			}

			reqData.put("scheduleId", beans.getNewScheduleId());

			planOid = KETEcmHelper.service.createMoldPlan(reqData, refDocs, attachFiles);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		} else if (command.equals("Modify")) {
		    reqData.put("oid", paramMap.getString("oid"));

		    reqData.put("dieNo", paramMap.getString("die_no"));
		    reqData.put("partNo", StringUtil.checkNull(paramMap.getString("part_no")));
		    reqData.put("partName", StringUtil.checkNull(paramMap.getString("part_name")));

		    reqData.put("type", StringUtil.checkNull(paramMap.getString("type")));
		    reqData.put("prodDeptName", StringUtil.checkNull(paramMap.getString("prod_dept_name")));
		    reqData.put("vendorFlag", StringUtil.checkNull(paramMap.getString("vendor_flag")));
		    reqData.put("vendorCode", StringUtil.checkNull(paramMap.getString("vendor_code")));
		    reqData.put("regBasis", StringUtil.checkNull(paramMap.getString("reg_basis")));
		    reqData.put("basisDate", StringUtil.checkNull(paramMap.getString("basis_date")));
		    reqData.put("prodEcoOwnerOid", StringUtil.checkNull(paramMap.getString("p_owner_oid")));
		    reqData.put("moldEcoOwnerOid", StringUtil.checkNull(paramMap.getString("m_owner_oid")));
		    reqData.put("modifyDesc", StringUtil.checkNull(paramMap.getString("modify_desc")));

		    reqData.put("prodPlanDate", StringUtil.checkNull(paramMap.getString("prod_plan_date")));
		    reqData.put("prodActualDate", StringUtil.checkNull(paramMap.getString("prod_actual_date")));
		    reqData.put("moldEcoPlanDate", StringUtil.checkNull(paramMap.getString("mold_eco_plan_date")));
		    reqData.put("moldEcoActualDate", StringUtil.checkNull(paramMap.getString("mold_eco_actual_date")));
		    reqData.put("storePlanDate", StringUtil.checkNull(paramMap.getString("store_plan_date")));
		    reqData.put("storeActualDate", StringUtil.checkNull(paramMap.getString("store_actual_date")));
		    reqData.put("workPlanDate", StringUtil.checkNull(paramMap.getString("work_plan_date")));
		    reqData.put("workActualDate", StringUtil.checkNull(paramMap.getString("work_actual_date")));
		    reqData.put("assPlanDate", StringUtil.checkNull(paramMap.getString("ass_plan_date")));
		    reqData.put("assActualDate", StringUtil.checkNull(paramMap.getString("ass_actual_date")));
		    reqData.put("tryPlanDate", StringUtil.checkNull(paramMap.getString("try_plan_date")));
		    reqData.put("tryActualDate", StringUtil.checkNull(paramMap.getString("try_actual_date")));
		    reqData.put("testPlanDate", StringUtil.checkNull(paramMap.getString("test_plan_date")));
		    reqData.put("testActualDate", StringUtil.checkNull(paramMap.getString("test_actual_date")));
		    reqData.put("approvePlanDate", StringUtil.checkNull(paramMap.getString("approve_plan_date")));
		    reqData.put("approveActualDate", StringUtil.checkNull(paramMap.getString("approve_actual_date")));

		    reqData.put("measureType", StringUtil.checkNull(paramMap.getString("measure_type")));
		    reqData.put("failAction", StringUtil.checkNull(paramMap.getString("fail_action")));
		    reqData.put("result", StringUtil.checkNull(paramMap.getString("result")));
		    reqData.put("measureDate", StringUtil.checkNull(paramMap.getString("measure_date")));

		    reqData.put("delFileList", StringUtil.checkNull(paramMap.getString("deleteFileList")));
		    reqData.put("planDesc", StringUtil.checkNull(paramMap.getString("plan_desc")));
		    reqData.put("m_date", StringUtil.checkNull(paramMap.getString("m_date")));
		    reqData.put("m_customer", StringUtil.checkNull(paramMap.getString("m_customer")));

		    attachFiles = uploader.getFiles();

		    String[] docOidArr = paramMap.getStringArray("docOid");

		    try {
			MoldPlanBeans beans = new MoldPlanBeans();

			if (docOidArr != null && docOidArr.length > 0) {
			    refDocs = beans.getNewRefDocList(docOidArr);
			}

			planOid = KETEcmHelper.service.modifyMoldPlan(reqData, refDocs, attachFiles);
		    } catch (WTException e) {
			Kogger.error(getClass(), e);
		    }
		}

		if (planOid.length() > 0) {
		    alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldPlanServlet", planOid, "CN");
		} else {
		    rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateMoldPlan.jsp");
		    rd.forward(req, res);
		}

	    } else {
		command = StringUtil.checkNull(req.getParameter("cmd"));
		if (command.equals("Search")) {

		    Connection conn = null;

		    StringBuffer strBuffer = new StringBuffer();
		    int rowCount = 1;

		    try {

			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			MoldPlanDao dao = new MoldPlanDao(conn);

			// Form 데이터 받기
			FormUploader formUploader = FormUploader.newFormUploader(req, res, getServletContext());
			KETParamMapUtil paramMap = KETParamMapUtil.getMap(formUploader.getFormParameters());

			paramMap.put("planType", KETParamMapUtil.toString(paramMap.getStringArray("planType")));
			paramMap.put("measureType", KETParamMapUtil.toString(paramMap.getStringArray("measureType")));
			paramMap.put("currentProcess", KETParamMapUtil.toString(paramMap.getStringArray("currentProcess")));
			paramMap.put("status", KETParamMapUtil.toString(paramMap.getStringArray("status")));

			// [Start] 결과내 검색 조건 처리 //
			HttpSession session = req.getSession();
			List<Map<String, Object>> conditionList = null;

			if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

			    conditionList = new ArrayList<Map<String, Object>>();
			    session.setAttribute("moldplanSearchConditionList", conditionList);
			} else {

			    if (session.getAttribute("moldplanSearchConditionList") != null) {

				conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("moldplanSearchConditionList");
			    } else {

				conditionList = new ArrayList<Map<String, Object>>();
			    }
			}

			conditionList.add(paramMap);
			session.setAttribute("moldplanSearchConditionList", conditionList);
			// [End] 결과내 검색 조건 처리 //

			// 목록 결과
			List<Map<String, Object>> list = dao.searchMoldPlanList(conditionList);
			for (Map<String, Object> moldPlan : list) {

			    String statusColor = "";
			    if (moldPlan.get("status").equals("지연")) {
				statusColor = "&lt;center&gt;&lt;font color=&apos;red&apos;&gt;" + moldPlan.get("status")
				        + "&lt;/font&gt;&lt;/center&gt;";
			    } else if (moldPlan.get("status").equals("완료")) {
				statusColor = "&lt;center&gt;&lt;font color=&apos;lime&apos;&gt;" + moldPlan.get("status")
				        + "&lt;/font&gt;&lt;/center&gt;";
			    } else if (moldPlan.get("status").equals("진행")) {
				statusColor = "&lt;center&gt;&lt;font color=&apos;blue&apos;&gt;" + moldPlan.get("status")
				        + "&lt;/font&gt;&lt;/center&gt;";
			    } else {
				statusColor = "&lt;center&gt;" + moldPlan.get("status").toString() + "&lt;/center&gt;";
			    }

			    String measureType = "";
			    if (moldPlan.get("measure_type").equals("불합격")) {
				measureType = "&lt;center&gt;&lt;font color=&apos;red&apos;&gt;" + moldPlan.get("measure_type")
				        + "&lt;/font&gt;&lt;/center&gt;";
			    } else if (moldPlan.get("measure_type").equals("합격")) {
				measureType = "&lt;center&gt;&lt;font color=&apos;blue&apos;&gt;" + moldPlan.get("measure_type")
				        + "&lt;/font&gt;&lt;/center&gt;";
			    } else {
				measureType = "&lt;center&gt;" + moldPlan.get("measure_type").toString() + "&lt;/center&gt;";
			    }

			    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot; ");
			    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
			    strBuffer.append(" PlanType=&quot;" + moldPlan.get("plan_type") + "&quot;");
			    strBuffer.append(" DieNo=&quot;" + moldPlan.get("die_no") + "&quot;"
				    + " DieNoOnClick=&quot;javascript:goView(&apos;" + moldPlan.get("oid") + "&apos;);&quot;"
				    + " DieNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
				    + "&apos;&gt;&quot;" + " DieNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
			    strBuffer.append(" PartNo=&quot;" + moldPlan.get("part_no") + "&quot;");
			    strBuffer.append(" PartName=&quot;" + TreeGridUtil.replaceContentForI(moldPlan.get("part_name").toString())
				    + "&quot;");
			    strBuffer.append(" ProdOwner=&quot;" + moldPlan.get("prod_owner") + "&quot;");
			    strBuffer.append(" MoldOwner=&quot;" + moldPlan.get("mold_owner") + "&quot;");
			    strBuffer.append(" CurProc=&quot;" + moldPlan.get("cur_proc") + "&quot;");
			    strBuffer.append(" CurProcPlanDate=&quot;" + moldPlan.get("cur_proc_plan_date") + "&quot;");
			    strBuffer.append(" Status=&quot;" + statusColor + "&quot;");
			    strBuffer.append(" CreateDate=&quot;" + moldPlan.get("createstampa2") + "&quot;");
			    strBuffer.append(" MeasureType=&quot;" + measureType + "&quot;");
			    strBuffer.append("/>");
			}

			req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
			req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
			gotoResult(req, res, "/jsp/ecm/SearchMoldPlan.jsp");
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    } finally {
			PlmDBUtil.close(conn);
		    }
		} else if (command.equals("excelDown")) {

		    excelDown(req, res); // Excel Download
		} else if (command.equals("View") || command.equals("ModifyView")) {
		    MoldPlanBeans beans = new MoldPlanBeans();
		    try {
			planOid = req.getParameter("oid");

			plan = KETEcmHelper.service.getMoldPlan(planOid);
			req.setAttribute("plan", plan);

			if (command.equals("View")) {
			    ArrayList<Hashtable<String, String>> commentList = beans.getUserComments(plan.getScheduleId());
			    req.setAttribute("commentList", commentList);
			    req.setAttribute("prePage", req.getParameter("prePage"));

			    rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewMoldPlan.jsp");
			} else {
			    rd = getServletContext().getRequestDispatcher("/jsp/ecm/ModifyMoldPlan.jsp");
			}

			rd.forward(req, res);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		} else if (command.equals("Completed")) {
		    MoldPlanBeans beans = new MoldPlanBeans();
		    try {
			planOid = req.getParameter("oid");

			plan = KETEcmHelper.service.getMoldPlan(planOid);

			plan.setScheduleStatus("R");
			plan.setProdDwgPlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("prod_plan_date"))));
			plan.setProdDwgActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("prod_actual_date"))));
			plan.setMoldChangePlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("mold_eco_plan_date"))));
			plan.setMoldChangeActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("mold_eco_actual_date"))));
			plan.setStorePlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("store_plan_date"))));
			plan.setStoreActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("store_actual_date"))));
			plan.setWorkPlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("work_plan_date"))));
			plan.setWorkActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("work_actual_date"))));
			plan.setAssemblePlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("ass_plan_date"))));
			plan.setAssembleActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("ass_actual_date"))));
			plan.setTryPlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("try_plan_date"))));
			plan.setTryActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("try_actual_date"))));
			plan.setTestPlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("test_plan_date"))));
			plan.setTestActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("test_actual_date"))));
			plan.setApprovePlanDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("approve_plan_date"))));
			plan.setApproveActualDate(DateUtil.convertDate2(StringUtil.checkNull(req.getParameter("approve_actual_date"))));
			plan.setScheduleStatus(beans.getCurrentPlanStatus(plan));

			PersistenceHelper.manager.modify(plan);

			req.setAttribute("plan", plan);

			ArrayList<Hashtable<String, String>> commentList = beans.getUserComments(plan.getScheduleId());
			req.setAttribute("commentList", commentList);
			req.setAttribute("prePage", req.getParameter("prePage"));

			rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewMoldPlan.jsp");

			rd.forward(req, res);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		} else if (command.equals("Delete")) {
		    planOid = req.getParameter("oid");

		    try {
			isDelete = KETEcmHelper.service.deleteMoldPlan(planOid);

			if (isDelete) {
			    alertNSearch(res, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchMoldPlan.jsp");
			} else {
			    alertNgo(res, "실패하였습니다.", "/plm/servlet/e3ps/MoldPlanServlet", planOid, "D");
			}
		    } catch (WTException e) {
			Kogger.error(getClass(), e);
		    }
		} else if (command.equals("CommentSave")) {
		    planOid = req.getParameter("oid");
		    String scheduleId = req.getParameter("scheduleId");
		    String userId = req.getParameter("currentUserId");
		    String userName = req.getParameter("currentUserName");
		    String userComment = req.getParameter("currentUserComment");
		    String userLineOrder = req.getParameter("currentUserLineOrder");

		    MoldPlanBeans beans = new MoldPlanBeans();

		    boolean isSuccess = false;

		    try {
			isSuccess = beans.savePlanComment(scheduleId, userId, userName, userComment, userLineOrder);

			if (isSuccess) {
			    alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldPlanServlet", planOid, "CN");
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		} else if (command.equals("CommentDelete")) {
		    planOid = req.getParameter("oid");
		    String scheduleId = req.getParameter("scheduleId");
		    String userId = req.getParameter("currentUserId");
		    String userLineOrder = req.getParameter("currentUserLineOrder");

		    MoldPlanBeans beans = new MoldPlanBeans();

		    boolean isSuccess = false;

		    try {
			isSuccess = beans.deleteUserComment(scheduleId, userId, userLineOrder);

			if (isSuccess) {
			    alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldPlanServlet", planOid, "CN");
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}
	    }
	}
    }

    private void excelDown(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;
	int rowCount = 0;

	try {

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    MoldPlanDao dao = new MoldPlanDao(conn);

	    // Form 데이터 받기
	    FormUploader formUploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(formUploader.getFormParameters());

	    paramMap.put("planType", KETParamMapUtil.toString(paramMap.getStringArray("planType")));
	    paramMap.put("measureType", KETParamMapUtil.toString(paramMap.getStringArray("measureType")));
	    paramMap.put("currentProcess", KETParamMapUtil.toString(paramMap.getStringArray("currentProcess")));
	    paramMap.put("status", KETParamMapUtil.toString(paramMap.getStringArray("status")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("moldplanSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("moldplanSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("moldplanSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("moldplanSearchConditionList", conditionList);
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
	    String sFileName = "MoldPlanListExcel_" + ff.format(new Date()) + ".xls";

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
		WritableSheet s1 = workbook.createSheet("개조수정 일정 리스트", 0);

		s1.mergeCells(0, 1, 0, 2);
		s1.mergeCells(1, 1, 1, 2);
		s1.mergeCells(2, 1, 2, 2);
		s1.mergeCells(3, 1, 3, 2);
		s1.mergeCells(4, 1, 4, 2);
		s1.mergeCells(5, 1, 6, 1);
		s1.mergeCells(7, 1, 7, 2);
		s1.mergeCells(8, 1, 8, 2);
		s1.mergeCells(9, 1, 9, 2);
		s1.mergeCells(10, 1, 10, 2);
		s1.mergeCells(11, 1, 11, 2);
		s1.mergeCells(12, 1, 12, 2);
		s1.mergeCells(13, 1, 13, 2);
		s1.mergeCells(14, 1, 14, 2);
		s1.mergeCells(15, 1, 15, 2);
		s1.mergeCells(16, 1, 16, 2);
		s1.mergeCells(17, 1, 25, 1);
		s1.mergeCells(26, 1, 26, 2);
		s1.mergeCells(27, 1, 27, 2);
		s1.mergeCells(28, 1, 32, 1);
		s1.mergeCells(33, 1, 33, 2);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		WritableCellFormat headerFormat = new WritableCellFormat();
		headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		headerFormat.setAlignment(Alignment.CENTRE); // 셀 가운데 정렬
		headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 수직 가운데 정렬;

		Label lr = new Label(0, 1, "No", headerFormat);
		s1.addCell(lr);

		lr = new Label(1, 1, "구분", headerFormat);
		s1.addCell(lr);

		lr = new Label(2, 1, "Die No", headerFormat);
		s1.addCell(lr);

		lr = new Label(3, 1, "부품번호", headerFormat);
		s1.addCell(lr);

		lr = new Label(4, 1, "부품명", headerFormat);
		s1.addCell(lr);

		lr = new Label(5, 1, "등록근거", headerFormat);
		s1.addCell(lr);

		lr = new Label(5, 2, "사유", headerFormat);
		s1.addCell(lr);

		lr = new Label(6, 2, "일자", headerFormat);
		s1.addCell(lr);

		lr = new Label(7, 1, "등록자", headerFormat);
		s1.addCell(lr);

		lr = new Label(8, 1, "등록부서", headerFormat);
		s1.addCell(lr);

		lr = new Label(9, 1, "고객사4M", headerFormat);
		s1.addCell(lr);

		lr = new Label(10, 1, "수정내용", headerFormat);
		s1.addCell(lr);

		lr = new Label(11, 1, "생산처", headerFormat);
		s1.addCell(lr);

		lr = new Label(12, 1, "개발담당부서", headerFormat);
		s1.addCell(lr);

		lr = new Label(13, 1, "제품ECO No", headerFormat);
		s1.addCell(lr);

		lr = new Label(14, 1, "제품ECO 담당", headerFormat);
		s1.addCell(lr);

		lr = new Label(15, 1, "금형ECO No", headerFormat);
		s1.addCell(lr);

		lr = new Label(16, 1, "금형ECO 담당", headerFormat);
		s1.addCell(lr);

		lr = new Label(17, 1, "일정정보", headerFormat);
		s1.addCell(lr);

		lr = new Label(17, 2, "제품도", headerFormat);
		s1.addCell(lr);

		lr = new Label(18, 2, "금형설계", headerFormat);
		s1.addCell(lr);

		lr = new Label(19, 2, "금형입고", headerFormat);
		s1.addCell(lr);

		lr = new Label(20, 2, "금형부품", headerFormat);
		s1.addCell(lr);

		lr = new Label(21, 2, "금형조립", headerFormat);
		s1.addCell(lr);

		lr = new Label(22, 2, "금형Try", headerFormat);
		s1.addCell(lr);

		lr = new Label(23, 2, "제품측정", headerFormat);
		s1.addCell(lr);

		lr = new Label(24, 2, "제품검토협의", headerFormat);
		s1.addCell(lr);

		lr = new Label(25, 2, "4M완료일", headerFormat);
		s1.addCell(lr);

		lr = new Label(26, 1, "단계", headerFormat);
		s1.addCell(lr);

		lr = new Label(27, 1, "상태", headerFormat);
		s1.addCell(lr);

		lr = new Label(28, 1, "진행내용", headerFormat);
		s1.addCell(lr);

		lr = new Label(28, 2, "측정구분", headerFormat);
		s1.addCell(lr);

		lr = new Label(29, 2, "조치", headerFormat);
		s1.addCell(lr);

		lr = new Label(30, 2, "결과", headerFormat);
		s1.addCell(lr);

		lr = new Label(31, 2, "측정일", headerFormat);
		s1.addCell(lr);

		lr = new Label(32, 2, "등록일자", headerFormat);
		s1.addCell(lr);

		lr = new Label(33, 1, "비고", headerFormat);
		s1.addCell(lr);

		int row = 2;

		// 목록 결과
		List<Map<String, Object>> list = dao.searchMoldPlanList(conditionList);
		for (Map<String, Object> moldPlan : list) {
		    row++;

		    // No
		    rowCount++;
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // 구분
		    lr = new Label(1, row, StringUtil.checkNull((String) moldPlan.get("plan_type")), headerFormat);
		    s1.addCell(lr);

		    // Die No
		    lr = new Label(2, row, StringUtil.checkNull((String) moldPlan.get("die_no")), headerFormat);
		    s1.addCell(lr);

		    // Part No
		    lr = new Label(3, row, StringUtil.checkNull((String) moldPlan.get("part_no")), headerFormat);
		    s1.addCell(lr);

		    // Part Name
		    lr = new Label(4, row, StringUtil.checkNull((String) moldPlan.get("part_name")), cellFormat);
		    s1.addCell(lr);

		    // 사유
		    lr = new Label(5, row, StringUtil.checkNull((String) moldPlan.get("reg_basis")), headerFormat);
		    s1.addCell(lr);

		    // 일자
		    lr = new Label(6, row, StringUtil.checkNull((String) moldPlan.get("basis_date")), headerFormat);
		    s1.addCell(lr);

		    // 등록자
		    lr = new Label(7, row, StringUtil.checkNull((String) moldPlan.get("owner_name")), headerFormat);
		    s1.addCell(lr);

		    // 등록부서
		    lr = new Label(8, row, StringUtil.checkNull((String) moldPlan.get("owner_dept")), headerFormat);
		    s1.addCell(lr);

		    // 고객4M
		    lr = new Label(9, row, StringUtil.checkNull((String) moldPlan.get("ATTRIBUTE1")), headerFormat);
		    s1.addCell(lr);

		    // 수정내용
		    lr = new Label(10, row, StringUtil.checkNull((String) moldPlan.get("modify_desc")), cellFormat);
		    s1.addCell(lr);

		    // 생산처
		    lr = new Label(11, row, StringUtil.checkNull((String) moldPlan.get("vendor_name")), headerFormat);
		    s1.addCell(lr);

		    // 개발담당부서
		    lr = new Label(12, row, StringUtil.checkNull((String) moldPlan.get("prod_dept_name")), headerFormat);
		    s1.addCell(lr);

		    // 제품ECO No
		    lr = new Label(13, row, StringUtil.checkNull((String) moldPlan.get("prod_eco_id")), headerFormat);
		    s1.addCell(lr);

		    // 제품ECO 담당
		    lr = new Label(14, row, StringUtil.checkNull((String) moldPlan.get("prod_owner")), headerFormat);
		    s1.addCell(lr);

		    // 금형ECO No
		    lr = new Label(15, row, StringUtil.checkNull((String) moldPlan.get("mold_eco_id")), headerFormat);
		    s1.addCell(lr);

		    // 금형ECO 담당
		    lr = new Label(16, row, StringUtil.checkNull((String) moldPlan.get("mold_owner")), headerFormat);
		    s1.addCell(lr);

		    // 제품도
		    lr = new Label(17, row, StringUtil.changeString((String) moldPlan.get("prod_dwg_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 금형설계
		    lr = new Label(18, row, StringUtil.changeString((String) moldPlan.get("mold_chg_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 금형입고
		    lr = new Label(19, row, StringUtil.changeString((String) moldPlan.get("work_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 금형부품
		    lr = new Label(20, row, StringUtil.changeString((String) moldPlan.get("store_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 금형조립
		    lr = new Label(21, row, StringUtil.changeString((String) moldPlan.get("assembly_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 금형 Try
		    lr = new Label(22, row, StringUtil.changeString((String) moldPlan.get("try_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 제품측정
		    lr = new Label(23, row, StringUtil.changeString((String) moldPlan.get("test_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 제품검토협의
		    lr = new Label(24, row, StringUtil.changeString((String) moldPlan.get("approve_date"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 4M완료일
		    lr = new Label(25, row, StringUtil.changeString((String) moldPlan.get("ATTRIBUTE2"), "()", ""), headerFormat);
		    s1.addCell(lr);

		    // 단계
		    lr = new Label(26, row, StringUtil.checkNull((String) moldPlan.get("cur_proc")), headerFormat);
		    s1.addCell(lr);

		    // 상태
		    lr = new Label(27, row, StringUtil.checkNull((String) moldPlan.get("status")), headerFormat);
		    s1.addCell(lr);

		    // 측정구분
		    lr = new Label(28, row, StringUtil.checkNull((String) moldPlan.get("measure_type")), headerFormat);
		    s1.addCell(lr);

		    // 조치
		    lr = new Label(29, row, StringUtil.checkNull((String) moldPlan.get("fail_action")), headerFormat);
		    s1.addCell(lr);

		    // 결과
		    lr = new Label(30, row, StringUtil.checkNull((String) moldPlan.get("result")), headerFormat);
		    s1.addCell(lr);

		    // 측정일
		    lr = new Label(31, row, StringUtil.checkNull((String) moldPlan.get("measure_date")), headerFormat);
		    s1.addCell(lr);

		    // 측정일
		    lr = new Label(32, row, StringUtil.checkNull((String) moldPlan.get("createstampa2")), headerFormat);
		    s1.addCell(lr);

		    // 비고
		    lr = new Label(33, row, StringUtil.checkNull((String) moldPlan.get("plan_desc")), cellFormat);
		    s1.addCell(lr);
		}

		workbook.write();
		workbook.close();
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
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    protected void alertNgo(HttpServletResponse res, String msg, String url, String oid, String prePage) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='View'>" + "\n <input type='hidden' name='oid' value='" + oid + "'>"
		    + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n </form>"
		    + "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
		    + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    protected void alertNSearch(HttpServletResponse res, String msg, String url) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='Search'>" + "\n </form>" + "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
		    + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
