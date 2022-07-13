/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 8. 31.
 */
package e3ps.ews.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.ews.dao.EarlyWarningDao;
import e3ps.ews.dao.PartnerDao;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.service.KETEwsHelper;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningServlet 설명 : 초기유동관리 지정 서블릿 작성자 : 김경희 작성일자 : 2010. 8. 31.
 */
public class EarlyWarningServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getParameter(req, "cmd");

	if (cmd.equals("create")) {
	    // 초기유동관리 지정 생성
	    create(req, res);
	} else if (cmd.equals("update")) {
	    // 초기유동관리 지정 수정
	    update(req, res);
	} else if (cmd.equals("revise")) {
	    // 초기유동관리 지정 개정
	    revise(req, res);
	} else if (cmd.equals("delete")) {
	    // 초기유동관리 지정 삭제
	    delete(req, res);
	} else if (cmd.equals("search")) {
	    // 초기유동관리 검색
	    search(req, res);
	} else if (cmd.equals("excel")) {
	    // 초기유동관리 검색 Excel
	    excel(req, res);
	} else if (cmd.equals("popup")) {
	    // 초기유동관리이력 검색
	    popup(req, res);
	} else if (cmd.equals("searchPart")) {
	    // 제품/부품 검색
	    searchPart(req, res);
	}
    }

    /**
     * 함수명 : create 설명 : 초기유동관리 지정 등록
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 8. 31.
     */
    private void create(HttpServletRequest req, HttpServletResponse res) {
	try {

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "02460");// 저장되었습니다.

	    // 초기유동관리 지정 저장 실행
	    KETEarlyWarning ketEarlyWarning = KETEwsHelper.service.createEarlyWarning(hash, files);

	    if (ketEarlyWarning != null) {
		// 저장 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid=" + CommonUtil.getOIDString(ketEarlyWarning));
	    } else {
		alert(res, "저장 실패!");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : update 설명 : 초기유동관리 지정 수정
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 9. 15.
     */
    private void update(HttpServletRequest req, HttpServletResponse res) {
	try {

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "01947");// 수정되었습니다.

	    // 초기유동관리 지정 수정 실행
	    KETEarlyWarning ketearlywarning = KETEwsHelper.service.updateEarlyWarning(hash, files);

	    if (ketearlywarning != null) {
		// 수정 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid=" + CommonUtil.getOIDString(ketearlywarning));
	    } else {
		alert(res, "수정 실패!");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : revise 설명 : 초기유동관리 지정 개정
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 9. 27.
     */
    private void revise(HttpServletRequest req, HttpServletResponse res) {
	try {

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "03405");// 개정되었습니다.

	    // 초기유동관리 지정 개정 실행
	    KETEarlyWarning ketearlywarning = KETEwsHelper.service.reviseEarlyWarning(hash, files);

	    if (ketearlywarning != null) {
		// 개정 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid=" + CommonUtil.getOIDString(ketearlywarning));
	    } else {
		alert(res, "개정 실패!");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : delete 설명 : 초기유동관리 지정 삭제
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 9. 28.
     */
    private void delete(HttpServletRequest req, HttpServletResponse res) {
	try {

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "01699");// 삭제되었습니다.

	    // 초기유동관리 지정 삭제 실행
	    boolean flag = KETEwsHelper.service.deleteEarlyWarning(hash, files);

	    if (flag) {
		// 삭제 성공 후 화면 이동
		alertNgo(res, msg, "/plm/servlet/e3ps/EarlyWarningServlet?cmd=search");
	    } else {
		alert(res, "삭제 실패!");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : search 설명 : 초기유동관리 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 10. 13.
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {
	// 커넥션
	Connection conn = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String detailInfoUrl = null;

	try {

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    EarlyWarningDao earlyWarningDao = new EarlyWarningDao(conn);

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
	    List<Map<String, Object>> list = earlyWarningDao.ViewEarlyWarningList(hash, conditionList);
	    for (Map<String, Object> earHash : list) {

		String ewsno = (String) earHash.get("ewsno");
		String pjtno = (String) earHash.get("pjtno");
		String pjtname = (String) earHash.get("pjtname");
		String partno = (String) earHash.get("partno");
		String inout = (String) earHash.get("inout");
		String fstcharge = (String) earHash.get("fstcharge");
		String creator = (String) earHash.get("creator");
		String createdate = (String) earHash.get("createdate");
		String step = (String) earHash.get("step");
		String oid = (String) earHash.get("oid");

		detailInfoUrl = "/plm/jsp/ews/ViewEarlyWarning.jsp?oid=" + oid; // 관리번호 링크 상세화면으로 이동

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" EwsNo=&quot;" + ewsno + "&quot;" + " EwsNoOnClick=&quot;javascript:go_to(&apos;" + detailInfoUrl
		        + "&apos;);&quot;" + " EwsNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
		        + "&apos;&gt;&quot; EwsNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" ProjectNo=&quot;" + pjtno + "&quot;");
		strBuffer.append(" ProjectName=&quot;" + pjtname + "&quot;");
		strBuffer.append(" PartNo=&quot;" + partno + "&quot;");
		strBuffer.append(" Inout=&quot;" + inout + "&quot;");
		strBuffer.append(" Fstcharge=&quot;" + fstcharge + "&quot;");
		strBuffer.append(" Creator=&quot;" + creator + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + createdate + "&quot;");
		strBuffer.append(" Step=&quot;" + step + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/ews/SearchEarlyWarning.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : popup 설명 : 초기유동 이력 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 21.
     */
    private void popup(HttpServletRequest req, HttpServletResponse res) {

	// 커넥션
	Connection conn = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    Hashtable hash = uploader.getFormParameters();
	    // 검색 페이지
	    int page = StringUtil.getIntParameter((String) hash.get("page"), 1);
	    // 페이지 당 목록수
	    int perPage = StringUtil.getIntParameter((String) hash.get("perPage"), 10);

	    int startRow = (page - 1) * perPage + 1;
	    int endRow = page * perPage;

	    // 검색 목록 시작&끝 셋팅
	    hash.put("startRow", Integer.toString(startRow));
	    hash.put("endRow", Integer.toString(endRow));
	    hash.put("sort", StringUtil.checkReplaceStr((String) hash.get("sort"), "1 DESC"));
	    hash.put("isExcel", "N");

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    EarlyWarningDao earlyWarningDao = new EarlyWarningDao(conn);

	    // 목록 결과
	    ArrayList list = earlyWarningDao.ViewEarlyWarningList(hash);
	    // 목록 결과 갯수
	    int listCnt = earlyWarningDao.ViewEarlyWarningListCnt(hash);

	    // 페이지 정보
	    PageControl pageControl = new PageControl(page, 10, perPage, listCnt);
	    pageControl.setPostMethod();
	    pageControl.setHref("/plm/servlet/e3ps/EarlyWarningServlet");

	    // 검색조건 셋팅
	    req.setAttribute("condition", hash);
	    // 목록 결과 셋팅
	    req.setAttribute("list", list);
	    // 페이지 정보 셋팅
	    req.setAttribute("control", pageControl);

	    // 검색화면으로 이동
	    gotoResult(req, res, "/jsp/ews/SearchEarlyWarningHistory.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : excel 설명 : 초기유동관리 검색(엑셀)
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 남현승 작성일자 : 2013.07.30
     */
    private void excel(HttpServletRequest req, HttpServletResponse res) {

	// 커넥션
	Connection conn = null;
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();

	try {
	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    EarlyWarningDao earlyWarningDao = new EarlyWarningDao(conn);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecrSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecrSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecrSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ecrSearchConditionList", conditionList);
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
	    String sFileName = "EwsSearchList_" + ff.format(new Date()) + ".xls";

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
		WritableSheet s1 = writebook.createSheet("초기유동관리 목록", 0);

		// 셀의 스타일을 지정
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

		int row = 2;
		int rowCount = 0;

		// 문서제목
		Label lr = new Label(0, 0, "초기유동관리 목록");
		s1.addCell(lr);

		lr = new Label(0, row, "번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(1, row, "초기유동관리 No", cellFormat);
		s1.addCell(lr);

		lr = new Label(2, row, "프로젝트번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(3, row, "부품", cellFormat);
		s1.addCell(lr);

		lr = new Label(4, row, "생산처", cellFormat);
		s1.addCell(lr);

		lr = new Label(5, row, "수행담당자(정)", cellFormat);
		s1.addCell(lr);

		lr = new Label(6, row, "작성자", cellFormat);
		s1.addCell(lr);

		lr = new Label(7, row, "작성일자", cellFormat);
		s1.addCell(lr);

		lr = new Label(8, row, "진행단계", cellFormat);
		s1.addCell(lr);

		// Hashtable<String,String> earlyWarning = new Hashtable<String,String>();

		List<Map<String, Object>> list1 = earlyWarningDao.ViewEarlyWarningList(hash, conditionList);

		for (Map<String, Object> earlyWarning : list1) {

		    row++;
		    rowCount++;

		    // 번호
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // 초기유동관리 No
		    lr = new Label(1, row, StringUtil.checkNull((String) earlyWarning.get("ewsno")), cellFormat);
		    s1.addCell(lr);

		    // 프로젝트번호
		    lr = new Label(2, row, StringUtil.checkNull((String) earlyWarning.get("pjtno")), cellFormat);
		    s1.addCell(lr);

		    // 부품
		    lr = new Label(3, row, StringUtil.checkNull((String) earlyWarning.get("partno")), cellFormat);
		    s1.addCell(lr);

		    // 생산처
		    lr = new Label(4, row, StringUtil.checkNull((String) earlyWarning.get("inout")), cellFormat);
		    s1.addCell(lr);

		    // 수행담당자(정)
		    lr = new Label(5, row, StringUtil.checkNull((String) earlyWarning.get("fstcharge")), cellFormat);
		    s1.addCell(lr);

		    // 작성자
		    lr = new Label(6, row, StringUtil.checkNull((String) earlyWarning.get("creator")), cellFormat);
		    s1.addCell(lr);

		    // 작성일자
		    lr = new Label(7, row, StringUtil.checkNull((String) earlyWarning.get("createdate")), cellFormat);
		    s1.addCell(lr);

		    // 진행단계
		    lr = new Label(8, row, StringUtil.checkNull((String) earlyWarning.get("step")), cellFormat);
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
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * 함수명 : searchPart 설명 : 제품/부품 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 03.
     */
    private void searchPart(HttpServletRequest req, HttpServletResponse res) {

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // 프로젝트 번호
	    String pjtNo = paramMap.getString("pjtNo");

	    // 목록 결과
	    QuerySpec qs = new QuerySpec();
	    QuerySpec specMoldItem = null;
	    SearchCondition sc = null;
	    SearchCondition sc2 = null;
	    int idx = qs.appendClassList(ProductProject.class, true);
	    int idx2 = 0;

	    SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	    qs.appendWhere(sc0, new int[] { idx });

	    if (pjtNo != null && pjtNo.length() > 0) {
		qs.appendAnd();

		sc = new SearchCondition(ProductProject.class, "master>pjtNo", SearchCondition.EQUAL, pjtNo);
		qs.appendWhere(sc, new int[] { idx });
	    }

	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    QueryResult rt = null;

	    PartnerDao partnerDao = null;
	    ProductProject productProject = null;

	    MoldItemInfo miInfo = null;
	    MoldProject moldProject = null;
	    StringBuffer strBuffer = new StringBuffer();

	    int rowCount = 1;
	    while (qr.hasMoreElements()) {
		Object[] po = (Object[]) qr.nextElement();
		productProject = (ProductProject) po[0];

		QuerySpec qs1 = new QuerySpec();

		int idxpi = qs1.appendClassList(ProductInfo.class, true);

		SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=",
		        CommonUtil.getOIDLongValue(productProject));
		qs1.appendWhere(cs, new int[] { idxpi });

		QueryResult qrpi = PersistenceHelper.manager.find(qs1);

		ProductInfo ProInfo = null;

		// 부품
		while (qrpi.hasMoreElements()) {

		    Object[] po1 = (Object[]) qrpi.nextElement();
		    ProInfo = (ProductInfo) po1[0];

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" oid=&quot;" + CommonUtil.getOIDString(ProInfo) + "&quot;");
		    strBuffer.append(" type=&quot;Assy&quot;"); // 제품 부품 구분
		    strBuffer.append(" partNo=&quot;" + StringUtil.checkNull(ProInfo.getPNum()) + "&quot;");// 부품번호
		    strBuffer.append(" partName=&quot;" + StringUtil.checkNull(ProInfo.getPName()) + "&quot;"); // 부품명
		    strBuffer.append(" dieNo=&quot;" + "" + "&quot;");// Die No

		    if (productProject.getPartnerNo() != null && productProject.getPartnerNo().length() > 0) {
			partnerDao = new PartnerDao();

			strBuffer.append(" proTeam=&quot;" + StringUtil.checkNull(productProject.getPartnerNo()) + "&quot;"); // 생산처
			strBuffer.append(" proTeamName=&quot;" + partnerDao.ViewPartnerName(productProject.getPartnerNo()) + "&quot;"); // 생산처명
			Kogger.debug(getClass(), "#### proTeamName1 ==  " + partnerDao.ViewPartnerName(productProject.getPartnerNo()));
		    } else if (productProject.getAssemblyPlace() != null) {

			strBuffer.append(" proTeam=&quot;" + StringUtil.checkNull(productProject.getAssemblyPlace().getCode()) + "&quot;"); // 생산처
			strBuffer.append(" proTeamName=&quot;" + productProject.getAssemblyPlace().getName() + "&quot;"); // 생산처명
			Kogger.debug(getClass(), "#### proTeamName2 ==  " + productProject.getAssemblyPlace().getName());
		    } else {

			strBuffer.append(" proTeamName=&quot" + "" + ";&quot;"); // 생산처
		    }

		    strBuffer.append(" dieProTeam=&quot" + "" + ";&quot;"); // 제작처
		    strBuffer.append("/>");

		}

		// item
		specMoldItem = new QuerySpec();
		idx2 = specMoldItem.addClassList(MoldItemInfo.class, true);
		sc2 = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(productProject));
		specMoldItem.appendWhere(sc2, new int[] { idx2 });
		SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, "thePersistInfo.createStamp", false);

		rt = PersistenceHelper.manager.find(specMoldItem);

		while (rt.hasMoreElements()) {

		    Object[] mo = (Object[]) rt.nextElement();
		    miInfo = (MoldItemInfo) mo[0];

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" oid=&quot;" + CommonUtil.getOIDString(miInfo) + "&quot;");
		    strBuffer.append(" type=&quot;Item&quot;"); // 제품 부품 구분
		    strBuffer.append(" partNo=&quot;" + StringUtil.checkNull(miInfo.getPartNo()) + "&quot;");// 부품번호
		    strBuffer.append(" partName=&quot;" + StringUtil.checkNull(miInfo.getPartName()) + "&quot;"); // 부품명
		    strBuffer.append(" dieNo=&quot;" + StringUtil.checkNull(miInfo.getDieNo()) + "&quot;");// Die No

		    if (miInfo.getPartnerNo() != null && miInfo.getPartnerNo().length() > 0) {
			partnerDao = new PartnerDao();
			strBuffer.append(" proTeam=&quot;" + StringUtil.checkNull(miInfo.getPartnerNo()) + "&quot;"); // 생산처
			strBuffer.append(" proTeamName=&quot;" + StringUtil.checkNull(miInfo.getMaking()).toString() + " / "
			        + StringUtil.checkNull(partnerDao.ViewPartnerName(miInfo.getPartnerNo())) + "&quot;"); // 생산처명

		    } else if (miInfo.getPurchasePlace() != null) {
			strBuffer.append(" proTeam=&quot;" + StringUtil.checkNull(miInfo.getPurchasePlace().getCode()) + "&quot;"); // 생산처
			strBuffer.append(" proTeamName=&quot;" + StringUtil.checkNull(miInfo.getMaking()).toString() + " / "
			        + StringUtil.checkNull(miInfo.getPurchasePlace().getName()) + "&quot;"); // 생산처명
		    } else {
			strBuffer.append(" proTeamName=&quot" + "" + ";&quot;"); // 생산처
		    }

		    moldProject = MoldProjectHelper.getMoldProject(miInfo);
		    if (moldProject != null) {
			strBuffer.append(" dieProTeam=&quot;" + StringUtil.checkNull(moldProject.getOutSourcing()) + "&quot;"); // 제작처
		    } else {
			strBuffer.append(" dieProTeam=&quot;" + "" + "&quot;"); // 제작처
		    }

		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    // 검색화면으로 이동
	    gotoResult(req, res, "/jsp/ews/SelectPartPopup.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
