package e3ps.project.moldPart.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.People;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.moldPart.beans.MoldPartManagerData;
import ext.ket.shared.log.Kogger;

public class MoldPartSearchServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String command = req.getParameter("command");
	Kogger.debug(getClass(), "=====> MoldPartSearchServlet: command=[" + command + "]");

	if ("excelDown".equals(command)) {

	    String strClient = req.getHeader("User-Agent");

	    // Excel file download ...
	    java.util.Date date = new java.util.Date();
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	    String fileName = "MoldPartSearchList_" + formatter.format(date) + ".xls";
	    // String fileName = new String("list".getBytes("euc-kr"), "8859_1");

	    if (strClient.indexOf("MSIE 5.5") > -1) {
		res.setHeader("Content-Disposition", "filename=" + fileName + ".xls;");
	    }

	    else {
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls;");
	    }

	    try {
		// WritableWorkbook workbook = Workbook.createWorkbook(res.getOutputStream());
		WritableWorkbook workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator
		        + fileName));
		WritableSheet sheet = workbook.createSheet("MoldPartList", 1);

		WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

		int row = 0;
		int column = 0;

		String titles[] = new String[] { "No", "Die No", "단계구분", "상세구분", "작성자", "부품공정", "금형설계출도일", "작성일", "완료예정일", "실제완료일", "진행상태" };

		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));

		}

		// [Start] 결과 내 재검색
		/*
	         * QuerySpec spec = getQuerySpec(req, res); // 기존 검색 주석처리
	         */
		FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
		KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
		boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
		List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("MoldPartSearch", searchInResult, req);
		conditionList.add(paramMap);
		QuerySpec spec = getQuerySpecSIR(paramMap, conditionList);
		// [End] 결과 내 재검색

		QueryResult result = PersistenceHelper.manager.find(spec);

		while (result.hasMoreElements()) {
		    ++row;
		    int columnIndex = 0;
		    Object[] obj = (Object[]) result.nextElement();
		    MoldPartManager manager = (MoldPartManager) obj[0];
		    MoldPartManagerData data = new MoldPartManagerData(manager);

		    String dieNumber = data.dieNo;
		    // String cType = data.createType;
		    String mlevelType = data.levelType;
		    String title = data.title;
		    String mcreatorName = data.creator;
		    String pUserName = data.partUser;
		    String moldDrawDate = "";
		    if (manager.getMoldDate() != null) {
			moldDrawDate = DateUtil.getDateString(manager.getMoldDate(), "d");
		    }
		    String createTime = data.createDate;
		    String planTime = data.planDate;

		    if (planTime == null || planTime.length() == 0) {
			planTime = "";
		    }
		    Timestamp stamp = manager.getEndDate();
		    String endDate = "";
		    if (stamp != null) {
			endDate = DateUtil.getDateString(stamp, "d");
		    }

		    String state = data.state;
		    sheet.addCell(new Label(columnIndex++, row, "" + row));
		    sheet.addCell(new Label(columnIndex++, row, dieNumber));
		    sheet.addCell(new Label(columnIndex++, row, mlevelType));
		    sheet.addCell(new Label(columnIndex++, row, title));
		    sheet.addCell(new Label(columnIndex++, row, mcreatorName));
		    sheet.addCell(new Label(columnIndex++, row, pUserName));
		    sheet.addCell(new Label(columnIndex++, row, moldDrawDate));
		    sheet.addCell(new Label(columnIndex++, row, createTime));
		    sheet.addCell(new Label(columnIndex++, row, planTime));
		    sheet.addCell(new Label(columnIndex++, row, endDate));
		    sheet.addCell(new Label(columnIndex++, row, state));
		}

		workbook.write();
		workbook.close();
	    } catch (Exception ex) {
		Kogger.error(getClass(), ex);
	    }

	    File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	    try {
		drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")),
		        req.getRemoteAddr());

		FileInputStream fin = new FileInputStream(drmFile);
		int ifilesize = (int) drmFile.length();
		byte b[] = new byte[ifilesize];

		ServletOutputStream sos = res.getOutputStream();

		fin.read(b);
		sos.write(b, 0, ifilesize);

		sos.flush();
		sos.close();
		fin.close();
	    } catch (Exception wte) {
		Kogger.error(getClass(), wte);
	    }

	} else if ("openSearch".equals(command)) {
	    openSearch(req, res);
	} else if ("search".equals(command)) {
	    search(req, res);
	} else if ("searchGridData".equals(command)) {
	    searchGrid(req, res, false);
	} else if ("searchGridPage".equals(command)) {
	    searchGrid(req, res, true);
	}

    }

    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	return getQuerySpec(map);
    }

    protected QuerySpec getQuerySpec(Map _paramMap) {
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(_paramMap);

	QuerySpec qs = null;
	// String initType = req.getParameter("initType");

	try {

	    String dieNo = paramMap.getString("dieNo");
	    String[] partUserAry = paramMap.getStringArray("partUser");
	    String creator = paramMap.getString("creator");
	    String createDateS = paramMap.getString("createDateS");
	    String createDateE = paramMap.getString("createDateE");
	    String moldDateS = paramMap.getString("moldDateS");
	    String moldDateE = paramMap.getString("moldDateE");
	    String planDateS = paramMap.getString("planDateS");
	    String planDateE = paramMap.getString("planDateE");
	    String createType = paramMap.getString("createType");
	    String[] levelTypeAry = paramMap.getStringArray("levelType");
	    String[] moldStateAry = paramMap.getStringArray("moldState");

	    qs = new QuerySpec();

	    int index1 = qs.addClassList(MoldPartManager.class, true);
	    int index2 = qs.addClassList(MoldItemInfo.class, false);
	    int index3 = qs.addClassList(MoldProject.class, false);

	    SearchCondition sc = new SearchCondition(new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(MoldPartManager.class, "projectReference.key.id"));

	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { index3, index1 });

	    sc = new SearchCondition(new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(MoldProject.class, "moldInfoReference.key.id"));
	    sc.setOuterJoin(0);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index2, index3 });

	    if (StringUtil.checkString(dieNo)) {
		qs.appendAnd();

		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, dieNo, true);
	    }

	    if (partUserAry != null && partUserAry.length > 0) {
		qs.appendAnd();

		String[] userIdAry = new String[partUserAry.length];
		for (int i = 0; i < partUserAry.length; ++i) {
		    userIdAry[i] = String.valueOf(CommonUtil.getOIDLongValue(partUserAry[i]));
		}
		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldPartManager.class, index1, "partUser.key.id", userIdAry, false);
	    }

	    if (StringUtil.checkString(creator)) {
		qs.appendAnd();
		long userId = CommonUtil.getOIDLongValue(creator);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "owner.key.id", "=", userId), new int[] { index1 });
	    }

	    if (StringUtil.checkString(createDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(createDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "thePersistInfo.createStamp", ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(createDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(createDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "thePersistInfo.createStamp", "<=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(moldDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(moldDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_DATE, ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(moldDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(moldDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_DATE, "<=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(planDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(planDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(planDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(planDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, "<=", stamp), new int[] { index1 });
	    }

	    if (moldStateAry != null && moldStateAry.length > 0) {
		Calendar c = Calendar.getInstance();
		String toDayStr = DateUtil.getDateString(c.getTime(), "d");
		Kogger.debug(getClass(), "toDayStr = " + toDayStr);
		c.add(Calendar.DATE, -1);
		String preDate = DateUtil.getDateString(c.getTime(), "d");

		qs.appendAnd();
		qs.appendOpenParen();

		boolean appended = false;
		for (String moldState : moldStateAry) {
		    if (appended == true) {
			qs.appendOr();
		    }

		    if ("진행".equals(moldState)) {
			qs.appendOpenParen();

			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, true), new int[] { index1 });
			qs.appendOr();
			Timestamp stamp = DateUtil.getTimestampFormat(toDayStr, "yyyy-MM-dd");
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, ">=", stamp),
			        new int[] { index1 });
			qs.appendCloseParen();
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, true), new int[] { index1 });

			qs.appendCloseParen();
			if (appended == false)
			    appended = true;
		    } else if ("지연".equals(moldState)) {
			qs.appendOpenParen();

			Timestamp stamp = DateUtil.getTimestampFormat(toDayStr, "yyyy-MM-dd");
			Kogger.debug(getClass(), "stamp = " + stamp);
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, "<", stamp),
			        new int[] { index1 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, true), new int[] { index1 });

			qs.appendCloseParen();
			if (appended == false)
			    appended = true;
		    } else if ("완료".equals(moldState)) {
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, "=", "완료"),
			        new int[] { index1 });

			if (appended == false)
			    appended = true;
		    }
		}

		qs.appendCloseParen();
	    }

	    /*
	     * String createType = map.getString("createType")); String levelType = map.getString("levelType"));
	     */
	    if (StringUtil.checkString(createType)) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.CREATE_TYPE, "=", createType),
		        new int[] { index1 });
	    }

	    if (levelTypeAry != null && levelTypeAry.length > 0) {
		qs.appendAnd();
		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldPartManager.class, index1, MoldPartManager.LEVEL_TYPE, levelTypeAry, false);
	    }

	    String sortKey = paramMap.getString("sortKey");
	    String dsc = paramMap.getString("dsc");
	    boolean ascent = false;

	    if (dsc != null && dsc.length() > 0) {
		ascent = Boolean.valueOf(dsc);
	    }

	    if (StringUtil.checkString(sortKey)) {
		boolean isPeopleJoin = sortKey.equals(MoldPartManager.OWNER) || sortKey.equals(MoldPartManager.PART_USER);
		if (MoldPartManager.DIE_NO.equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, "dineNo", ascent);
		}

		else if (isPeopleJoin) {
		    int index4 = qs.addClassList(People.class, false);

		    sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, sortKey + ".key.id"), "=", new ClassAttribute(
			    People.class, People.USER_REFERENCE + ".key.id"));

		    sc.setOuterJoin(0);
		    qs.appendAnd();
		    qs.appendWhere(sc, new int[] { index1, index4 });

		    SearchUtil.setOrderBy(qs, People.class, index4, People.NAME, "user", ascent);

		    // People.USER_REFERENCE
		} else {

		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, sortKey, "attr", ascent);
		}
	    }

	    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, "thePersistInfo.createStamp", "createStamp", false);

	    Kogger.debug(getClass(), "MoldPartSearchServlet.getQuerySpec == \n" + qs);
	    // Kogger.debug(getClass(), qs);
	    // SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, MoldSubPart.MOLD_NO , "moldNo", false);

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return qs;
    }

    /*
     * 결과내 재검색용(Search In Result)
     */
    protected QuerySpec getQuerySpecSIR(Map _paramMap, List<Map> mapList) {
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(_paramMap);

	QuerySpec qs = null;
	// String initType = req.getParameter("initType");

	try {
	    String dieNo = paramMap.getString("dieNo");
	    String[] partUserAry = paramMap.getStringArray("partUser");
	    String creator = paramMap.getString("creator");
	    String createDateS = paramMap.getString("createDateS");
	    String createDateE = paramMap.getString("createDateE");
	    String moldDateS = paramMap.getString("moldDateS");
	    String moldDateE = paramMap.getString("moldDateE");
	    String planDateS = paramMap.getString("planDateS");
	    String planDateE = paramMap.getString("planDateE");
	    String createType = paramMap.getString("createType");
	    String[] levelTypeAry = paramMap.getStringArray("levelType");
	    String[] moldStateAry = paramMap.getStringArray("moldState");

	    qs = new QuerySpec();

	    int index1 = qs.addClassList(MoldPartManager.class, true);
	    int index2 = qs.addClassList(MoldItemInfo.class, false);
	    int index3 = qs.addClassList(MoldProject.class, false);
	    int index5 = qs.addClassList(WTUser.class, false);

	    SearchCondition sc = new SearchCondition(new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(MoldPartManager.class, "projectReference.key.id"));

	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { index3, index1 });

	    sc = new SearchCondition(new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(MoldProject.class, "moldInfoReference.key.id"));
	    sc.setOuterJoin(0);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index2, index3 });

	    sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, "owner.key.id"), "=", new ClassAttribute(WTUser.class,
		    "thePersistInfo.theObjectIdentifier.id"));
	    sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	    qs.appendAnd();
	    qs.appendWhere(sc, new int[] { index1, index5 });

	    if (StringUtil.checkString(dieNo)) {
		qs.appendAnd();

		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, dieNo, true);
	    }

	    if (partUserAry != null && partUserAry.length > 0 && StringUtil.checkString(partUserAry[0])) {
		qs.appendAnd();

		/****************/
		String partUserOid = partUserAry[0];
		String[] partUserOidAry = partUserOid.split(",");
		/**************/

		String[] userIdAry = new String[partUserOidAry.length];
		for (int i = 0; i < partUserOidAry.length; ++i) {
		    // Kogger.debug(getClass(), "userIdAry[" + i + "]:" + partUserOidAry[i]);
		    if (StringUtil.checkString(partUserOidAry[i])) {
			userIdAry[i] = String.valueOf(CommonUtil.getOIDLongValue(partUserOidAry[i]));
		    }
		}
		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldPartManager.class, index1, "partUser.key.id", userIdAry, false);
	    }

	    if (StringUtil.checkString(creator)) {
		qs.appendAnd();
		long userId = CommonUtil.getOIDLongValue(creator);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "owner.key.id", "=", userId), new int[] { index1 });
	    }

	    if (StringUtil.checkString(createDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(createDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "thePersistInfo.createStamp", ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(createDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(createDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, "thePersistInfo.createStamp", "<=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(moldDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(moldDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_DATE, ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(moldDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(moldDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_DATE, "<=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(planDateS)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertStartDate2(planDateS);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, ">=", stamp), new int[] { index1 });
	    }

	    if (StringUtil.checkString(planDateE)) {
		qs.appendAnd();
		Timestamp stamp = DateUtil.convertEndDate2(planDateE);
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, "<=", stamp), new int[] { index1 });
	    }

	    if (moldStateAry != null && moldStateAry.length > 0) {
		Calendar c = Calendar.getInstance();
		String toDayStr = DateUtil.getDateString(c.getTime(), "d");
		// Kogger.debug(getClass(), "toDayStr = " + toDayStr);
		c.add(Calendar.DATE, -1);
		String preDate = DateUtil.getDateString(c.getTime(), "d");

		qs.appendAnd();
		qs.appendOpenParen();

		boolean appended = false;
		for (String moldState : moldStateAry) {
		    if (appended == true) {
			qs.appendOr();
		    }

		    if ("진행".equals(moldState)) {
			qs.appendOpenParen();

			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, true), new int[] { index1 });
			qs.appendOr();
			Timestamp stamp = DateUtil.getTimestampFormat(toDayStr, "yyyy-MM-dd");
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, ">=", stamp),
			        new int[] { index1 });
			qs.appendCloseParen();
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, true), new int[] { index1 });

			qs.appendCloseParen();
			if (appended == false)
			    appended = true;
		    } else if ("지연".equals(moldState)) {
			qs.appendOpenParen();

			Timestamp stamp = DateUtil.getTimestampFormat(toDayStr, "yyyy-MM-dd");
			// Kogger.debug(getClass(), "stamp = " + stamp);
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.PLAN_DATE, "<", stamp),
			        new int[] { index1 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, true), new int[] { index1 });

			qs.appendCloseParen();
			if (appended == false)
			    appended = true;
		    } else if ("완료".equals(moldState)) {
			qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.MOLD_STATE, "=", "완료"),
			        new int[] { index1 });

			if (appended == false)
			    appended = true;
		    }
		}

		qs.appendCloseParen();
	    }

	    /*
	     * String createType = map.getString("createType")); String levelType = map.getString("levelType"));
	     */
	    if (StringUtil.checkString(createType)) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldPartManager.class, MoldPartManager.CREATE_TYPE, "=", createType),
		        new int[] { index1 });
	    }

	    if (levelTypeAry != null && levelTypeAry.length > 0) {
		qs.appendAnd();
		KETQueryUtil.setQuerySpecForMultiSearch(qs, MoldPartManager.class, index1, MoldPartManager.LEVEL_TYPE, levelTypeAry, false);
	    }

	    String sortKey = paramMap.getString("sortKey");
	    String dsc = paramMap.getString("dsc");
	    String dataHaStr = (String) paramMap.get("TGData");
	    boolean ascent = false;
	    if (dataHaStr != null) {
		String s1 = dataHaStr.substring(dataHaStr.indexOf("Sort=") + 6, dataHaStr.indexOf("SortCols=") - 2); // Sort
		if (s1 != null) {
		    if (s1.startsWith("-")) {
			ascent = true;
		    } else {
			ascent = false;
		    }
		}
		sortKey = s1;
		sortKey = dataHaStr.substring(dataHaStr.indexOf("SortCols=") + 10, dataHaStr.indexOf("SortTypes=") - 2); // SortCols
		if (sortKey != null && sortKey.length() > 0) {
		    sortKey = Character.toLowerCase(sortKey.charAt(0)) + sortKey.substring(1);

		}

		// String sortStr = (String) dataHaMap.get("Sort");
		// String sortColsStr = (String) dataHaMap.get("SortCols");
	    }

	    if (dsc != null && dsc.length() > 0) {
		// ascent = Boolean.valueOf(dsc);
	    }

	    if (StringUtil.checkString(sortKey)) {
		boolean isPeopleJoin = sortKey.equals(MoldPartManager.OWNER) || sortKey.equals(MoldPartManager.PART_USER);
		if (MoldPartManager.DIE_NO.equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldItemInfo.class, index2, MoldItemInfo.DIE_NO, "dieNo", ascent);
		}

		else if (isPeopleJoin) {
		    int index4 = qs.addClassList(People.class, false);

		    sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, sortKey + ".key.id"), "=", new ClassAttribute(
			    People.class, People.USER_REFERENCE + ".key.id"));

		    sc.setOuterJoin(0);
		    qs.appendAnd();
		    qs.appendWhere(sc, new int[] { index1, index4 });

		    SearchUtil.setOrderBy(qs, People.class, index4, People.NAME, "user", ascent);

		    // People.USER_REFERENCE

		} else if ("creator".equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, WTUser.class, index5, WTUser.LAST, "userName", ascent);
		} else if ("createDate".equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, "thePersistInfo.createStamp", "createDate", ascent); // CREATESTAMPA2
		} else if ("title".equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, MoldPartManager.LEVEL_TYPE, "title", ascent);
		} else if ("status".equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, MoldPartManager.MOLD_STATE, "status", ascent);
		} else if ("ecaCompleteDate".equals(sortKey)) {
		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, MoldPartManager.MOLD_STATE, "status", ascent);
		} else {
		    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, sortKey, "attr", ascent);
		}
	    }

	    SearchUtil.setOrderBy(qs, MoldPartManager.class, index1, "thePersistInfo.createStamp", "createStamp", false);

	    Kogger.debug(getClass(), "MoldPartSearchServlet.getQuerySpecSIR == \n" + qs);
	    // Kogger.debug(getClass(), qs);
	    // SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, MoldSubPart.MOLD_NO , "moldNo", false);

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
	return qs;
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

    private void openSearch(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    gotoResult(req, res, "/jsp/project/moldPart/ListMoldPart.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void search(HttpServletRequest req, HttpServletResponse res) {
	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
	    Kogger.debug(getClass(), "MoldPartSearchServlet.search: paramMap=" + paramMap);

	    // [Start] 결과 내 재검색
	    /*
	     * QuerySpec qs = getQuerySpec(paramMap); // 기존 검색 주석처리
	     */
	    boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	    List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("MoldPartSearch", searchInResult, req);
	    conditionList.add(paramMap);
	    QuerySpec qs = getQuerySpecSIR(paramMap, conditionList);
	    // [End] 결과 내 재검색

	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    // Kogger.debug(getClass(), "MoldPartSearchServlet.search: qr=" + qr);

	    TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

	    if (qr != null && qr.size() > 0) {
		int count = 0;
		while (qr.hasMoreElements()) {
		    Object[] obj = (Object[]) qr.nextElement();
		    MoldPartManager manager = (MoldPartManager) obj[0];
		    MoldPartManagerData data = new MoldPartManagerData(manager);

		    String dieNumber = data.dieNo;
		    String cType = data.createType;
		    String mlevelType = data.levelType;
		    String title = data.title;
		    String mcreatorName = data.creator;
		    String pUserName = data.partUser;
		    String createTime = data.createDate;
		    String planTime = data.planDate;

		    String endDate;
		    Timestamp stamp = manager.getEndDate();
		    if (stamp != null) {
			endDate = DateUtil.getDateString(stamp, "d");
		    } else {
			endDate = "";
		    }

		    String moldDrawDate;
		    stamp = manager.getMoldDate();
		    if (stamp != null) {
			moldDrawDate = DateUtil.getDateString(stamp, "d");
		    } else {
			moldDrawDate = "";
		    }

		    if (planTime == null || planTime.length() == 0) {
			planTime = "&nbsp;";
		    }

		    String state = data.state;

		    String projectOid = data.projectOid;
		    String managerOid = data.managerOid;

		    String viewUrl = "javascript:goViewPage('" + projectOid + "','" + managerOid + "');";

		    strbuf.append("<I ");
		    strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
		    strbuf.appendRepl(" DocNo=\"" + (++count) + "\"");
		    strbuf.appendRepl(" DieNo=\"" + dieNumber + "\"" + " DieNoOnClick=\"" + viewUrl + "\""
			    + " DieNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			    + "'>\" DieNoHtmlPostfix=\"</font>\"");
		    strbuf.appendRepl(" LevelType=\"" + mlevelType + "\"");
		    strbuf.appendRepl(" Title=\"").appendContent(title).appendRepl("\"");
		    strbuf.appendRepl(" Creator=\"" + mcreatorName + "\"");
		    strbuf.appendRepl(" PartUser=\"" + pUserName + "\"");
		    strbuf.appendRepl(" MoldDrawDate=\"" + moldDrawDate + "\"");
		    strbuf.appendRepl(" CreateDate=\"" + createTime + "\"");
		    strbuf.appendRepl(" PlanDate=\"" + planTime + "\"");
		    strbuf.appendRepl(" EndDate=\"" + endDate + "\"");
		    strbuf.appendRepl(" Status=\"" + state + "\"");
		    strbuf.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strbuf.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/project/moldPart/ListMoldPart.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public int searchPerformancePagingCount(Map _paramMap, List<Map> conditionList, TgPagingControl pager) throws Exception {

	PageControl pCon = getQuerySpecSIRGrid(_paramMap, conditionList, pager);
	if (pCon != null)
	    return pCon.getTotalCount();
	else
	    return 0;
    }

    public PageControl getQuerySpecSIRGrid(Map _paramMap, List<Map> conditionList, TgPagingControl pager) throws Exception {
	QuerySpec qs = null;
	try {
	    qs = this.getQuerySpecSIR(_paramMap, conditionList);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	String sPage = String.valueOf(pager.getCurrentPageNo() + 1);
	if (sPage.isEmpty())
	    sPage = "0";
	String sPageRowCnt = String.valueOf(pager.getPageSize());
	if (sPageRowCnt.isEmpty())
	    sPageRowCnt = "10";

	int perPage = StringUtil.getIntParameter(sPageRowCnt);
	int formPage = 20;
	int page = StringUtil.getIntParameter(sPage, 0);

	// int performanceTotalCount = searchPerformancePagingCount(hashMap, conditionList, pager);
	// perPage = performanceTotalCount % perPage;
	PageControl pCon = CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs);
	int totalCount = 0;
	if (pCon != null) {
	    totalCount = pCon.getTotalCount();
	}
	// Kogger.debug(getClass(), "first:" + (page * perPage) + ", second:" + totalCount);
	if ((page * perPage) > totalCount)
	    perPage = totalCount % perPage;

	if (perPage == 0)
	    perPage = StringUtil.getIntParameter(sPageRowCnt);

	if (sPage.isEmpty()) {
	    return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs);
	} else {
	    Kogger.debug(getClass(), "qs >> " + qs);
	    return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), qs, new Integer(sPage));
	    // return findRow(new Integer(perPage), new Integer(formPage), qs, page);
	}

    }

    private void searchGrid(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {
	QueryResult qr = null;
	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
	    // Kogger.debug(getClass(), "MoldPartSearchServlet.search: paramMap=" + paramMap);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPaging, paramMap); // -->추가

	    // [Start] 결과 내 재검색
	    /*
	     * QuerySpec qs = getQuerySpec(paramMap); // 기존 검색 주석처리
	     */
	    boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	    List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("MoldPartSearch", searchInResult, req);
	    conditionList.add(paramMap);

	    PageControl pgCon = null;
	    if (isPaging) {

		pgCon = getQuerySpecSIRGrid(paramMap, conditionList, pager);

		qr = pgCon.getResult();
		// QuerySpec qs = getQuerySpecSIRGrid(conditionList, pager);
		// [End] 결과 내 재검색

		// QueryResult qr = PersistenceHelper.manager.find(qs);
		// Kogger.debug(getClass(), "MoldPartSearchServlet.search: qr=" + qr);

		TreeGridStringBuffer strbuf = new TreeGridStringBuffer();

		if (qr != null && qr.size() > 0) {
		    int count = 0;
		    while (qr.hasMoreElements()) {
			Object[] obj = (Object[]) qr.nextElement();
			MoldPartManager manager = (MoldPartManager) obj[0];
			MoldPartManagerData data = new MoldPartManagerData(manager);

			String dieNumber = data.dieNo;
			String cType = data.createType;
			String mlevelType = data.levelType;
			String title = data.title;
			String mcreatorName = data.creator;
			String pUserName = data.partUser;
			String createTime = data.createDate;
			String planTime = data.planDate;

			String endDate;
			Timestamp stamp = manager.getEndDate();
			if (stamp != null) {
			    endDate = DateUtil.getDateString(stamp, "d");
			} else {
			    endDate = "";
			}

			String moldDrawDate;
			stamp = manager.getMoldDate();
			if (stamp != null) {
			    moldDrawDate = DateUtil.getDateString(stamp, "d");
			} else {
			    moldDrawDate = "";
			}

			if (planTime == null || planTime.length() == 0) {
			    // planTime = "&nbsp;";
			    planTime = "";
			}

			String state = data.state;

			String projectOid = data.projectOid;
			String managerOid = data.managerOid;

			String viewUrl = "javascript:goViewPage('" + projectOid + "','" + managerOid + "');";

			// strbuf.append("<I ");
			// strbuf.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
			// strbuf.appendRepl(" DocNo=\"" + (++count) + "\"");
			// strbuf.appendRepl(" DieNo=\"" + dieNumber + "\"" + " DieNoOnClick=\"" + viewUrl + "\""
			// + " DieNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			// + "'>\" DieNoHtmlPostfix=\"</font>\"");
			// strbuf.appendRepl(" LevelType=\"" + mlevelType + "\"");
			// strbuf.appendRepl(" Title=\"").appendContent(title).appendRepl("\"");
			// strbuf.appendRepl(" Creator=\"" + mcreatorName + "\"");
			// strbuf.appendRepl(" PartUser=\"" + pUserName + "\"");
			// strbuf.appendRepl(" MoldDrawDate=\"" + moldDrawDate + "\"");
			// strbuf.appendRepl(" CreateDate=\"" + createTime + "\"");
			// strbuf.appendRepl(" PlanDate=\"" + planTime + "\"");
			// strbuf.appendRepl(" EndDate=\"" + endDate + "\"");
			// strbuf.appendRepl(" Status=\"" + state + "\"");
			// strbuf.append("/>");

			strbuf.append("<I  NoColor=\"2\" CanDelete=\"0\" ");
			strbuf.append(" RowNum=\"\"");
			strbuf.append(" DocNo=\"" + (++count) + "\"");
			strbuf.append(" DieNo=\"" + dieNumber + "\"");
			strbuf.append(" DieNoOnClick=\"" + viewUrl + "\"");
			strbuf.append(" DieNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\"");
			strbuf.append(" DieNoHtmlPostfix=\"</font>\"");
			strbuf.append(" LevelType=\"" + mlevelType + "\"");
			strbuf.append(" Title=\"").appendContent(title).append("\"");
			strbuf.append(" Creator=\"" + mcreatorName + "\"");
			strbuf.append(" PartUser=\"" + pUserName + "\"");
			strbuf.append(" MoldDrawDate=\"" + moldDrawDate + "\"");
			strbuf.append(" CreateDate=\"" + createTime + "\"");
			strbuf.append(" PlanDate=\"" + planTime + "\"");
			strbuf.append(" EndDate=\"" + endDate + "\"");
			strbuf.append(" Status=\"" + state + "\"");
			strbuf.append("/>");

		    }
		}

		req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strbuf.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));

		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    } else {

		int totalCount = searchPerformancePagingCount(paramMap, conditionList, pager);

		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
