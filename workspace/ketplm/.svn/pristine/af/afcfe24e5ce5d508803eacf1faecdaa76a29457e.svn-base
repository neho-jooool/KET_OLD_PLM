package e3ps.lesson.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.Department;
import e3ps.lesson.LessonLearn;
import e3ps.lesson.beans.LessonHelper;
import e3ps.project.ProductProject;
import ext.ket.shared.log.Kogger;

public class LessonServlet extends CommonServlet {

    private static final long serialVersionUID = 8419728326730565994L;

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getParameter(req, "cmd");
	Kogger.debug(getClass(), "cmd : " + cmd);
	if (cmd.equals("search")) {
	    // 습득교훈관리 검색
	    search(req, res);
	} else if (cmd.equals("excel")) {
	    // 습득교훈관리 검색 Excel
	    excel(req, res);
	} else if (cmd.equals("create")) {
	    // 습득교훈관리 등록
	    String oid = create(req, res);
	    if (oid != null) {
		res.sendRedirect("/plm/jsp/lesson/LessonLearnView.jsp?oid=" + oid); // 생성 후 상세조회 페이지로 이동하도록 수정
	    }
	} else if (cmd.equals("modify")) {
	    // 습득교훈관리 수정
	    String oid = modify(req, res);
	    if (oid != null) {
		res.sendRedirect("/plm/jsp/lesson/LessonLearnView.jsp?oid=" + oid); // 수정 후 상세조회 페이지로 이동하도록 수정
	    }
	} else if (cmd.equals("delete")) {
	    delete(req, res);
	    // res.sendRedirect("/plm/servlet/e3ps/LessonLearnServlet?cmd=search");
	}
    }

    private void search(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String detailInfoUrl = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // 데이터 확인
	    // Kogger.debug(getClass(), "#####################################");
	    // Kogger.debug(getClass(), "#####################################");
	    // Kogger.debug(getClass(), "partOid == " + hash.getString("partOid"));
	    // Kogger.debug(getClass(), "gubun == " + hash.getString("gubun"));
	    // Kogger.debug(getClass(), "factory == " + hash.getString("factory"));
	    // Kogger.debug(getClass(), "devDeptOid == " + hash.getString("devDeptOid"));
	    // Kogger.debug(getClass(), "creator == " + hash.getString("creator"));
	    // Kogger.debug(getClass(), "createStartdate == " + hash.getString("createStartdate"));
	    // Kogger.debug(getClass(), "createEnddate == " + hash.getString("createEnddate"));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ewsSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ewsSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ewsSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ewsSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 목록 결과

	    QueryResult rs = LessonHelper.getList(hash);

	    Kogger.debug(getClass(), "rs.size() == " + rs.size());

	    LessonLearn learn = null;
	    WTPart part = null;
	    WTUser user = null;
	    NumberCode nCode = null;
	    Department depart = null;
	    Map<String, Object> parameter = new HashMap<String, Object>();
	    List<Map<String, Object>> numCode = null;
	    while (rs.hasMoreElements()) {
		learn = (LessonLearn) rs.nextElement();

		part = learn.getPart();
		user = learn.getUser();
		depart = learn.getDepartment();

		String partNo = "";
		String partName = "";
		String gubun = "";
		String factory = "";
		String departName = "";
		String creator = "";
		// String createDate = DateUtil.getDateString(learn.getPersistInfo().getCreateStamp(), "d");
		String createDate = DateUtil.getDateString(learn.getInsert_date(), "d");
		String causeGubun = "";
		String improveGubun = "";
		String oid = CommonUtil.getOIDString(learn);

		if (part != null) {
		    partNo = part.getNumber();
		    partName = part.getName();
		} else {
		    if (learn.getPartNo() != null) {
			partNo = learn.getPartNo();
		    }
		    if (learn.getPart_nm() != null) {
			partName = learn.getPart_nm();
		    }

		}

		if (user != null) {
		    creator = user.getFullName();
		}

		nCode = learn.getFactory();

		if (nCode != null) {
		    factory = nCode.getName();
		}

		nCode = learn.getGubun();

		if (nCode != null) {
		    gubun = nCode.getName();
		}

		/*
	         * nCode = learn.getCause_gubun();
	         * 
	         * if(nCode != null){ causeGubun = nCode.getName(); }
	         */
		nCode = null;
		parameter.clear();
		KETMessageService messageService = new KETMessageService();
		parameter.put("locale", messageService.getLocale());
		parameter.put("codeType", "LESSONCOMMON");
		numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

		String[] cause_temp = learn.getCause_gb().split("\\|");

		for (int i = 0; i < cause_temp.length; i++) {
		    for (int j = 0; j < numCode.size(); j++) {
			if (cause_temp[i].equals(numCode.get(j).get("code"))) {
			    if (i == cause_temp.length - 1) {
				causeGubun += numCode.get(j).get("value");
			    } else {
				causeGubun += numCode.get(j).get("value") + ",";
			    }
			}
		    }
		}

		String[] improve_temp = learn.getImprove_gb().split("\\|");

		for (int i = 0; i < improve_temp.length; i++) {
		    for (int j = 0; j < numCode.size(); j++) {
			if (improve_temp[i].equals(numCode.get(j).get("code"))) {
			    if (i == improve_temp.length - 1) {
				improveGubun += numCode.get(j).get("value");
			    } else {
				improveGubun += numCode.get(j).get("value") + ",";
			    }
			}
		    }
		}

		/*
	         * nCode = learn.getImprove_gubun();
	         * 
	         * if(nCode != null){ improveGubun = nCode.getName(); }
	         */

		if (depart != null) {
		    departName = depart.getName();
		}

		detailInfoUrl = "/plm/jsp/lesson/LessonLearnView.jsp?oid=" + oid; // 관리번호 링크 상세화면으로 이동

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" PartNo=&quot;" + partNo + "&quot;" + " PartNoOnClick=&quot;javascript:go_to(&apos;" + detailInfoUrl
		        + "&apos;);&quot;" + " PartNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
		        + "&apos;&gt;&quot; PartNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" PartName=&quot;" + partName + "&quot;");
		strBuffer.append(" CauseGubun=&quot;" + causeGubun + "&quot;");
		strBuffer.append(" ImproveGubun=&quot;" + improveGubun + "&quot;");
		strBuffer.append(" Gubun=&quot;" + gubun + "&quot;");
		strBuffer.append(" Factory=&quot;" + factory + "&quot;");
		strBuffer.append(" Department=&quot;" + departName + "&quot;");
		strBuffer.append(" Creator=&quot;" + creator + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + createDate + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/lesson/SearchLessonLearn.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void excel(HttpServletRequest req, HttpServletResponse res) {

	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ewsSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ewsSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ewsSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ewsSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 목록 결과

	    QueryResult rs = LessonHelper.getList(hash);
	    Map<String, Object> parameter = new HashMap<String, Object>();
	    List<Map<String, Object>> numCode = null;

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
	    String sFileName = "LessonSearchList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		// sheet 생성
		WritableWorkbook writebook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = writebook.createSheet("습득교훈관리 목록", 0);

		// 셀의 스타일을 지정
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

		int row = 2;
		int rowCount = 0;

		// 문서제목
		Label lr = new Label(0, 0, "습득교훈관리 목록");
		s1.addCell(lr);

		lr = new Label(0, row, "번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(1, row, "부품번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(2, row, "부품명", cellFormat);
		s1.addCell(lr);

		lr = new Label(3, row, "Die No", cellFormat);
		s1.addCell(lr);

		lr = new Label(4, row, "부품명(Die)", cellFormat);
		s1.addCell(lr);

		lr = new Label(5, row, "ProjectNo", cellFormat);
		s1.addCell(lr);

		lr = new Label(6, row, "ProjectName", cellFormat);
		s1.addCell(lr);

		lr = new Label(7, row, "설비번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(8, row, "설비명", cellFormat);
		s1.addCell(lr);

		lr = new Label(9, row, "제품구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(10, row, "구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(11, row, "공장", cellFormat);
		s1.addCell(lr);

		lr = new Label(12, row, "부서", cellFormat);
		s1.addCell(lr);

		lr = new Label(13, row, "작성자", cellFormat);
		s1.addCell(lr);

		lr = new Label(14, row, "작성일자", cellFormat);
		s1.addCell(lr);

		lr = new Label(15, row, "발생일자", cellFormat);
		s1.addCell(lr);

		lr = new Label(16, row, "원인구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(17, row, "개선대책구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(18, row, "원인", cellFormat);
		s1.addCell(lr);

		lr = new Label(19, row, "개선대책", cellFormat);
		s1.addCell(lr);

		LessonLearn learn = null;
		WTPart part = null;
		WTPart die = null;
		WTUser user = null;
		NumberCode nCode = null;
		Department depart = null;
		ProductProject project = null;
		while (rs.hasMoreElements()) {
		    learn = (LessonLearn) rs.nextElement();

		    part = learn.getPart();
		    die = learn.getDie();
		    user = learn.getUser();
		    depart = learn.getDepartment();

		    String partNo = "";
		    String partName = "";
		    String Die_No = "";
		    String DieName = "";
		    String equipNo = "";
		    String equipName = "";
		    String gubun = "";
		    String factory = "";
		    String departName = "";
		    String creator = "";
		    String createDate = DateUtil.getDateString(learn.getInsert_date(), "d");
		    String oid = CommonUtil.getOIDString(learn);

		    String ProjectNo = "";
		    String ProjectName = "";
		    String ProductGubun = "";
		    String OccurDate = DateUtil.getDateString(learn.getOccur_date(), "d");
		    String CauseGubun = "";
		    String ImproveGubun = "";
		    String CauseTxt = "";
		    String ImproveTxt = "";

		    if (!"".equals(learn.getProjectOid())) {
			project = (ProductProject) CommonUtil.getObject(learn.getProjectOid());
		    }

		    if (project != null) {
			ProjectNo = project.getPjtNo();
			ProjectName = project.getPjtName();
		    }

		    if (part != null) {
			partNo = part.getNumber();
			partName = part.getName();
		    } else {
			partNo = learn.getPartNo();
			partName = learn.getPart_nm();
		    }

		    if (die != null) {
			Die_No = die.getNumber();
			DieName = die.getName();
		    } else {
			Die_No = learn.getDieNo();
			DieName = learn.getDie_nm();
		    }

		    equipNo = learn.getEquip_no();
		    equipName = learn.getEquip_nm();

		    if (user != null) {
			creator = user.getFullName();
		    }

		    nCode = learn.getFactory();

		    if (nCode != null) {
			factory = nCode.getName();
		    }

		    nCode = learn.getGubun();

		    if (nCode != null) {
			gubun = nCode.getName();
		    }

		    nCode = learn.getProduct_gubun();

		    if (nCode != null) {
			ProductGubun = nCode.getName();
		    }

		    /*
	             * nCode = learn.getCause_gubun();
	             * 
	             * if(nCode != null){ CauseGubun = nCode.getName(); }
	             * 
	             * nCode = learn.getImprove_gubun();
	             * 
	             * if(nCode != null){ ImproveGubun = nCode.getName(); }
	             */

		    nCode = null;
		    parameter.clear();
		    KETMessageService messageService = new KETMessageService();
		    parameter.put("locale", messageService.getLocale());
		    parameter.put("codeType", "LESSONCOMMON");
		    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

		    String[] cause_temp = learn.getCause_gb().split("\\|");

		    for (int i = 0; i < cause_temp.length; i++) {
			for (int j = 0; j < numCode.size(); j++) {
			    if (cause_temp[i].equals(numCode.get(j).get("code"))) {
				if (i == cause_temp.length - 1) {
				    CauseGubun += numCode.get(j).get("value");
				} else {
				    CauseGubun += numCode.get(j).get("value") + ",";
				}
			    }
			}
		    }

		    String[] improve_temp = learn.getImprove_gb().split("\\|");

		    for (int i = 0; i < improve_temp.length; i++) {
			for (int j = 0; j < numCode.size(); j++) {
			    if (improve_temp[i].equals(numCode.get(j).get("code"))) {
				if (i == improve_temp.length - 1) {
				    ImproveGubun += numCode.get(j).get("value");
				} else {
				    ImproveGubun += numCode.get(j).get("value") + ",";
				}
			    }
			}
		    }

		    if (depart != null) {
			departName = depart.getName();
		    }

		    CauseTxt = learn.getCause();
		    ImproveTxt = learn.getImprove();

		    row++;
		    rowCount++;

		    // 번호
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // 부품번호
		    lr = new Label(1, row, StringUtil.checkNull(partNo), cellFormat);
		    s1.addCell(lr);

		    // 부품명
		    lr = new Label(2, row, StringUtil.checkNull(partName), cellFormat);
		    s1.addCell(lr);

		    // 부품번호 die
		    lr = new Label(3, row, StringUtil.checkNull(Die_No), cellFormat);
		    s1.addCell(lr);

		    // 부품명 die
		    lr = new Label(4, row, StringUtil.checkNull(DieName), cellFormat);
		    s1.addCell(lr);

		    // ProjectNo
		    lr = new Label(5, row, StringUtil.checkNull(ProjectNo), cellFormat);
		    s1.addCell(lr);

		    // ProjectName
		    lr = new Label(6, row, StringUtil.checkNull(ProjectName), cellFormat);
		    s1.addCell(lr);

		    // 설비번호
		    lr = new Label(7, row, StringUtil.checkNull(equipNo), cellFormat);
		    s1.addCell(lr);

		    // 설비명
		    lr = new Label(8, row, StringUtil.checkNull(equipName), cellFormat);
		    s1.addCell(lr);

		    // 제품구분
		    lr = new Label(9, row, StringUtil.checkNull(ProductGubun), cellFormat);
		    s1.addCell(lr);

		    // 구분
		    lr = new Label(10, row, StringUtil.checkNull(gubun), cellFormat);
		    s1.addCell(lr);

		    // 공장
		    lr = new Label(11, row, StringUtil.checkNull(factory), cellFormat);
		    s1.addCell(lr);

		    // 부서
		    lr = new Label(12, row, StringUtil.checkNull(departName), cellFormat);
		    s1.addCell(lr);

		    // 작성자
		    lr = new Label(13, row, StringUtil.checkNull(creator), cellFormat);
		    s1.addCell(lr);

		    // 작성일자
		    lr = new Label(14, row, StringUtil.checkNull(createDate), cellFormat);
		    s1.addCell(lr);

		    // 발생일자
		    lr = new Label(15, row, StringUtil.checkNull(OccurDate), cellFormat);
		    s1.addCell(lr);

		    // 원인구분
		    lr = new Label(16, row, StringUtil.checkNull(CauseGubun), cellFormat);
		    s1.addCell(lr);

		    // 개선대책구분
		    lr = new Label(17, row, StringUtil.checkNull(ImproveGubun), cellFormat);
		    s1.addCell(lr);

		    // 원인
		    lr = new Label(18, row, StringUtil.checkNull(CauseTxt), cellFormat);
		    s1.addCell(lr);

		    // 개선대책
		    lr = new Label(19, row, StringUtil.checkNull(ImproveTxt), cellFormat);
		    s1.addCell(lr);

		}

		writebook.write();
		writebook.close();

	    } catch (Exception e) {

		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
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

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public String create(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);

	    String partOid = map.getString("partOid");
	    String gubun = map.getString("gubun");
	    String factory = map.getString("factory");
	    String devDeptOid = map.getString("devDeptOid");

	    String DieOid = map.getString("DieOid");
	    String equipNo = map.getString("equipNo");
	    String equipName = map.getString("equipName");
	    String create_date = map.getString("create_date");
	    String creator = map.getString("creatorOid");

	    String problemComment = map.getString("problemComment");
	    String causeComment = map.getString("causeComment");
	    String improveComment = map.getString("improveComment");

	    String occur_date = map.getString("occur_date");
	    String projectOid = map.getString("projectOid");
	    String prodcut_gubun = map.getString("prodcut_gubun");
	    String cause_gubun = map.getString("cause_gubun");
	    String improve_gubun = map.getString("improve_gubun");

	    String chk_part = map.getString("chk_part");
	    String chk_die = map.getString("chk_die");
	    String partNo = map.getString("partNo");
	    String partName = map.getString("partName");
	    String DieNo = map.getString("DieNo");
	    String DieName = map.getString("DieName");
	    String cause_gubun_check = map.getString("cause_gubun_check");
	    String improve_gubun_check = map.getString("improve_gubun_check");

	    LessonLearn learn = new LessonLearn();

	    if ("1".equals(chk_part)) {
		learn.setPartNo(partNo);
		learn.setPart_nm(partName);
	    }

	    if ("1".equals(chk_die)) {
		learn.setDieNo(DieNo);
		learn.setDie_nm(DieName);
	    }

	    WTPart part = (WTPart) CommonUtil.getObject(partOid);
	    if (part != null) {
		learn.setPart(part);
	    }
	    WTPart die = (WTPart) CommonUtil.getObject(DieOid);
	    if (die != null) {
		learn.setDie(die);
	    }

	    NumberCode nCode = (NumberCode) CommonUtil.getObject(gubun);
	    learn.setGubun(nCode);

	    nCode = (NumberCode) CommonUtil.getObject(factory);
	    learn.setFactory(nCode);

	    nCode = (NumberCode) CommonUtil.getObject(prodcut_gubun);
	    learn.setProduct_gubun(nCode);

	    /*
	     * nCode = (NumberCode)CommonUtil.getObject(cause_gubun); learn.setCause_gubun(nCode);
	     * 
	     * nCode = (NumberCode)CommonUtil.getObject(improve_gubun); learn.setImprove_gubun(nCode);
	     */
	    learn.setCause_gb(cause_gubun_check);
	    learn.setImprove_gb(improve_gubun_check);

	    Department depart = (Department) CommonUtil.getObject(devDeptOid);
	    learn.setDepartment(depart);

	    learn.setProblem(problemComment);
	    learn.setCause(causeComment);
	    learn.setImprove(improveComment);
	    learn.setUser((WTUser) CommonUtil.getObject(creator));
	    // learn.setUser((WTUser)SessionHelper.manager.getPrincipal());

	    learn.setEquip_nm(equipName);
	    learn.setEquip_no(equipNo);
	    learn.setProjectOid(projectOid);
	    try {
		learn.setInsert_date(DateUtil.getTimestampFormat(create_date, "yyyy-MM-dd"));
		learn.setOccur_date(DateUtil.getTimestampFormat(occur_date, "yyyy-MM-dd"));
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    // Kogger.debug(getClass(), "part : "+part);
	    // Kogger.debug(getClass(), "die : "+die);
	    Kogger.debug(getClass(), "gubun : " + gubun);
	    Kogger.debug(getClass(), "factory : " + factory);
	    Kogger.debug(getClass(), "devDeptOid : " + devDeptOid);
	    Kogger.debug(getClass(), "equipNo : " + equipNo);
	    Kogger.debug(getClass(), "equipName : " + equipName);
	    Kogger.debug(getClass(), "create_date : " + create_date);
	    Kogger.debug(getClass(), "creatorOid : " + creator);
	    Kogger.debug(getClass(), "cause_gubun_check : " + cause_gubun_check);
	    Kogger.debug(getClass(), "improve_gubun_check : " + improve_gubun_check);
	    Kogger.debug(getClass(), "partNo : " + partNo);
	    Kogger.debug(getClass(), "DieNo : " + DieNo);

	    learn = (LessonLearn) PersistenceHelper.manager.save(learn);
	    learn = (LessonLearn) E3PSContentHelper.service.attach(learn, uploader.getFiles());

	    oid = CommonUtil.getOIDString(learn);

	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    public String modify(HttpServletRequest req, HttpServletResponse res) {
	String oid = null;

	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    oid = map.getString("oid");
	    String partOid = map.getString("partOid");
	    String gubun = map.getString("gubun");
	    String factory = map.getString("factory");
	    String devDeptOid = map.getString("devDeptOid");
	    String problemComment = map.getString("problemComment");
	    String causeComment = map.getString("causeComment");
	    String improveComment = map.getString("improveComment");

	    String DieOid = map.getString("DieOid");
	    String equipNo = map.getString("equipNo");
	    String equipName = map.getString("equipName");
	    String creatorOid = map.getString("creatorOid");
	    String create_date = map.getString("create_date");
	    String occur_date = map.getString("occur_date");
	    String projectOid = map.getString("projectOid");
	    String prodcut_gubun = map.getString("prodcut_gubun");
	    String cause_gubun = map.getString("cause_gubun");
	    String improve_gubun = map.getString("improve_gubun");

	    String chk_part = map.getString("chk_part");
	    String chk_die = map.getString("chk_die");
	    String partNo = map.getString("partNo");
	    String partName = map.getString("partName");
	    String DieNo = map.getString("DieNo");
	    String DieName = map.getString("DieName");
	    String cause_gubun_check = map.getString("cause_gubun_check");
	    String improve_gubun_check = map.getString("improve_gubun_check");

	    LessonLearn learn = (LessonLearn) CommonUtil.getObject(oid);

	    if ("1".equals(chk_part)) {
		learn.setPartNo(partNo);
		learn.setPart_nm(partName);
	    }

	    if ("1".equals(chk_die)) {
		learn.setDieNo(DieNo);
		learn.setDie_nm(DieName);
	    }

	    WTPart part = (WTPart) CommonUtil.getObject(partOid);
	    learn.setPart(part);

	    NumberCode nCode = (NumberCode) CommonUtil.getObject(gubun);
	    learn.setGubun(nCode);

	    nCode = (NumberCode) CommonUtil.getObject(factory);
	    learn.setFactory(nCode);

	    nCode = (NumberCode) CommonUtil.getObject(prodcut_gubun);
	    learn.setProduct_gubun(nCode);

	    /*
	     * nCode = (NumberCode)CommonUtil.getObject(cause_gubun); learn.setCause_gubun(nCode);
	     * 
	     * nCode = (NumberCode)CommonUtil.getObject(improve_gubun); learn.setImprove_gubun(nCode);
	     */

	    learn.setCause_gb(cause_gubun_check);
	    learn.setImprove_gb(improve_gubun_check);

	    Department depart = (Department) CommonUtil.getObject(devDeptOid);
	    learn.setDepartment(depart);

	    learn.setProblem(problemComment);
	    learn.setCause(causeComment);
	    learn.setImprove(improveComment);

	    part = (WTPart) CommonUtil.getObject(DieOid);
	    learn.setDie(part);
	    learn.setEquip_no(equipNo);
	    learn.setEquip_nm(equipName);
	    learn.setUser((WTUser) CommonUtil.getObject(creatorOid));
	    learn.setProjectOid(projectOid);
	    try {
		learn.setInsert_date(DateUtil.getTimestampFormat(create_date, "yyyy-MM-dd"));
		learn.setOccur_date(DateUtil.getTimestampFormat(occur_date, "yyyy-MM-dd"));
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    learn = (LessonLearn) PersistenceHelper.manager.modify(learn);
	    try {
		learn = (LessonLearn) LessonHelper.updateAttachFiles(learn, map, uploader.getFiles());
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return oid;
    }

    public void delete(HttpServletRequest req, HttpServletResponse res) {

	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	String oid = map.getString("oid");
	LessonLearn lesson = (LessonLearn) CommonUtil.getObject(oid);
	try {
	    PersistenceHelper.manager.delete(lesson);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

}
