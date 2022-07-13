package e3ps.ecm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.dao.MoldChangeRequestDao;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.ecm.entity.KETSearchEcoVO;
import ext.ket.shared.log.Kogger;

public class SearchMoldEcrServlet extends CommonServlet {
    private static final long serialVersionUID = 8419728326730565994L;

    private String cmd = null;// command
    private String partOid = null;// PART OID
    private String partNo = null;
    private String projectOid = null;// 관련 프로젝트oid
    private String orgOid = null;// ECR 작성부서 OID
    private String orgName = null;// ECR 작성부서명
    private String creatorOid = null;// ECR 작성자 OID
    private String ecrId = null;// ECR ID
    private String ecrName = null;// ECR 제목
    private String divisionFlag = null;// 사업부구분
    private String createStartDate = null;// 작성시작일자
    private String createEndDate = null;// 작성종료일자
    private String prodMoldCls = null;// 제품/금형
    private String devYn = null;// 개발/양산구분
    private String sancStateFlag = null;// 상태

    /**
     * 
     * 함수명 : doService 설명 : 요청된 파라미터를 처리한 후 command에 따라서 기능을 호출한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	// processParameter(request, response);
	cmd = StringUtil.checkNull(request.getParameter("cmd"));

	if (cmd == null || cmd.equals(""))
	    cmd = "search";

	if (cmd.equals("search")) {
	    search(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcrForm.jsp");
	} else if (cmd.equals("excel")) {
	    excel(request, response);
	} else if (cmd.equals("popupEcrSearch")) {
	    search(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcrPopupForm.jsp");
	}
    }

    /**
     * 
     * 함수명 : processParameter 설명 : 요청된 파라미터를 인스턴스 변수에 저장한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void processParameter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	cmd = StringUtil.checkNull(request.getParameter("cmd"));
	partOid = StringUtil.checkNull(request.getParameter("partOid"));
	partNo = StringUtil.checkNull(request.getParameter("partNo"));
	projectOid = StringUtil.checkNull(request.getParameter("projectOid"));
	orgOid = StringUtil.checkNull(request.getParameter("orgOid"));
	orgName = StringUtil.checkNull(request.getParameter("orgName"));
	creatorOid = StringUtil.checkNull(request.getParameter("creatorOid"));
	ecrId = StringUtil.checkNull(request.getParameter("ecrId"));
	ecrName = StringUtil.checkNull(request.getParameter("ecrName"));
	divisionFlag = StringUtil.checkNull(request.getParameter("divisionFlag"));
	createStartDate = EcmUtil.getServerDateFormat(StringUtil.checkNull(request.getParameter("createStartDate")));
	createEndDate = EcmUtil.getServerDateFormat(StringUtil.checkNull(request.getParameter("createEndDate")));
	if ("".equals(createStartDate) && !"".equals(createEndDate)) {
	    createStartDate = "19000101";
	} else if (!"".equals(createStartDate) && "".equals(createEndDate)) {
	    createEndDate = "99991231";
	}
	prodMoldCls = StringUtil.checkNull(request.getParameter("prodMoldCls"));
	devYn = StringUtil.checkNull(request.getParameter("devYn"));
	sancStateFlag = StringUtil.checkNull(request.getParameter("sancStateFlag"));
    }

    private void excel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	MoldChangeRequestDao dao = new MoldChangeRequestDao();

	StringBuffer strBuffer = new StringBuffer();
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
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
	    String userAgent = request.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    // file path
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";

	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }

	    // file name
	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sFileName = "SearchECRList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);
	    String sortCol1 = "ECR No";
	    String sortCol2 = "ECR 제목";
	    String sortCol5 = "작성자";
	    String sortCol6 = "승인일자";
	    String sortCol7 = "결재상태";

	    try {

		WritableWorkbook writebook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = writebook.createSheet("ECR 목록", 0);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		int row = 2;
		int rowCount = 0;

		// 문서제목
		Label lr = new Label(0, 0, "ECR 목록");
		s1.addCell(lr);

		// 타이틀
		lr = new Label(0, row, "No", cellFormat);
		s1.addCell(lr);

		lr = new Label(1, row, sortCol1, cellFormat);
		s1.addCell(lr);

		lr = new Label(2, row, "연계 ECO No", cellFormat);
		s1.addCell(lr);

		lr = new Label(3, row, "ECR구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(4, row, "단계구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(5, row, "Project No", cellFormat);
		s1.addCell(lr);

		lr = new Label(6, row, "Part No/Die No", cellFormat);
		s1.addCell(lr);

		lr = new Label(7, row, "Part Name", cellFormat);
		s1.addCell(lr);

		lr = new Label(8, row, sortCol2, cellFormat);
		s1.addCell(lr);

		lr = new Label(9, row, "작성부서", cellFormat);
		s1.addCell(lr);

		lr = new Label(10, row, sortCol5, cellFormat);
		s1.addCell(lr);

		lr = new Label(11, row, sortCol6, cellFormat);
		s1.addCell(lr);

		lr = new Label(12, row, "의뢰구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(13, row, "설계변경유형", cellFormat);
		s1.addCell(lr);

		lr = new Label(14, row, "생산처", cellFormat);
		s1.addCell(lr);

		lr = new Label(15, row, "완료요청일", cellFormat);
		s1.addCell(lr);

		lr = new Label(16, row, "처리구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(17, row, sortCol7, cellFormat);
		s1.addCell(lr);

		lr = new Label(18, row, "작성일자", cellFormat);
		s1.addCell(lr);
		
		lr = new Label(19, row, "제기승인", cellFormat);
		s1.addCell(lr);
		
		lr = new Label(20, row, "검토승인", cellFormat);
		s1.addCell(lr);
		
		lr = new Label(21, row, "주관부서", cellFormat);
		s1.addCell(lr);
		
		lr = new Label(22, row, "담당자", cellFormat);
		s1.addCell(lr);

		List<Map<String, Object>> list = dao.searchEcrList(false, hash, conditionList);
		for (Map<String, Object> ecrList : list) {

		    row++;
		    // 번호
		    rowCount++;
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // ECR 번호
		    lr = new Label(1, row, StringUtil.checkNull((String) ecrList.get("EcoId")), cellFormat);
		    s1.addCell(lr);

		    // 연계 ECO No
		    lr = new Label(2, row, StringUtil.checkNull((String) ecrList.get("RelatedECOStr")), cellFormat);
		    s1.addCell(lr);

		    // ECR 구분
		    lr = new Label(3, row, StringUtil.checkNull((String) ecrList.get("ProdMoldClsName")), cellFormat);
		    s1.addCell(lr);

		    // 단계구분
		    lr = new Label(4, row, StringUtil.checkNull((String) ecrList.get("DevFlag")), cellFormat);
		    s1.addCell(lr);

		    // Project No
		    lr = new Label(5, row, StringUtil.checkNull((String) ecrList.get("ProjectNo")), cellFormat);
		    s1.addCell(lr);

		    // Part No/Die No setPartNumber
		    lr = new Label(6, row, StringUtil.checkNull((String) ecrList.get("PartNumber")), cellFormat);
		    s1.addCell(lr);

		    // Part Name
		    lr = new Label(7, row, StringUtil.checkNull((String) ecrList.get("PartName")), cellFormat);
		    s1.addCell(lr);

		    // ECR 제목
		    lr = new Label(8, row, StringUtil.checkNull((String) ecrList.get("EcoName")), cellFormat);
		    s1.addCell(lr);

		    // 작성부서명
		    lr = new Label(9, row, StringUtil.checkNull((String) ecrList.get("CreateDeptName")), cellFormat);
		    s1.addCell(lr);

		    // 작성자
		    lr = new Label(10, row, StringUtil.checkNull((String) ecrList.get("CreatorName")), cellFormat);
		    s1.addCell(lr);

		    // 승인일자
		    lr = new Label(11, row, StringUtil.checkNull((String) ecrList.get("ApproveDate")), cellFormat);
		    s1.addCell(lr);

		    // 의뢰구분
		    lr = new Label(12, row, StringUtil.checkNull((String) ecrList.get("ReqType") + (String) ecrList.get("ReqTypeDesc")),
			    cellFormat);
		    s1.addCell(lr);

		    // 설계변경유형
		    lr = new Label(13, row, StringUtil.checkNull((String) ecrList.get("MoldReqChgType")
			    + (String) ecrList.get("MoldReqChgTypeDesc")), cellFormat);
		    s1.addCell(lr);

		    // 생산처
		    lr = new Label(14, row, StringUtil.checkNull((String) ecrList.get("ProdVendor")), cellFormat);
		    s1.addCell(lr);

		    // 완료요청일
		    lr = new Label(15, row, StringUtil.checkNull((String) ecrList.get("CompleteReqDate")), cellFormat);
		    s1.addCell(lr);

		    // 처리구분
		    lr = new Label(16, row, StringUtil.checkNull((String) ecrList.get("ProcType") + (String) ecrList.get("ProdTypeDesc")),
			    cellFormat);
		    s1.addCell(lr);

		    // 결재상태
		    lr = new Label(17, row, StringUtil.checkNull((String) ecrList.get("SancStateFlag")), cellFormat);
		    s1.addCell(lr);

		    // 작성일자
		    lr = new Label(18, row, StringUtil.checkNull((String) ecrList.get("CreateDate")), cellFormat);
		    s1.addCell(lr);
		    
		    // 제기승인
		    lr = new Label(19, row, StringUtil.checkNull((String) ecrList.get("ApproveDate")), cellFormat);
		    s1.addCell(lr);
		    
		    // 검토승인
		    lr = new Label(20, row, StringUtil.checkNull((String) ecrList.get("ReviewDate")), cellFormat);
		    s1.addCell(lr);
		    
		    //주관부서
		    lr = new Label(21, row, StringUtil.checkNull((String) ecrList.get("departManagement")), cellFormat);
		    s1.addCell(lr);
		    
		    //담당자
		    lr = new Label(22, row, StringUtil.checkNull((String) ecrList.get("departPerson")), cellFormat);
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

		response.setContentLength(ifilesize);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = response.getOutputStream();
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
	}
    }// end-of-excel

    /**
     * 
     * 함수명 : search 설명 : 제품/금형 ECR 자료를 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 남현승 작성일자 : 2013. 8. 6.
     */
    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	StringBuffer strBuffer = new StringBuffer();
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = null;
	int rowCount = 1;

	String detailInfoUrl = null;
	String detailProjectPopup = null;

	try {
	    MoldChangeRequestDao dao = new MoldChangeRequestDao();

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    hash.put("prodMoldCls", KETParamMapUtil.toString(hash.getStringArray("prodMoldCls")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
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

	    /*
	     * List<Map<String, Object>> list = dao.searchEcrList( false, hash, conditionList );
	     * 
	     * for ( Map<String, Object> ecrVo : list ) { if( ecrVo.get("ProdMoldClsName").equals("제품") ){ detailInfoUrl =
	     * "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid=" + ecrVo.get("Oid"); // }else{ detailInfoUrl =
	     * "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrVo.get("Oid"); //금형 }
	     * 
	     * strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" ); strBuffer.append( " RowNum=&quot;" + rowCount++ +
	     * "&quot;" ); strBuffer.append( " ProdMoldCls=&quot;" + ecrVo.get("ProdMoldClsName") + "&quot;" ); strBuffer.append(
	     * " EcrNo=&quot;" + ecrVo.get("EcoId") + "&quot;" + "EcrNoOnClick=&quot;location.href=&apos;" + detailInfoUrl + "&apos;&quot;"
	     * + " EcrNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()+
	     * "&apos;&gt;&quot; EcrNoHtmlPostfix=&quot;&lt;/font&gt;&quot;"); strBuffer.append( " EcrName=&quot;" +
	     * TreeGridUtil.replaceContentForI(ecrVo.get("EcoName").toString()) + "&quot;" ); strBuffer.append( " DeptName=&quot;" +
	     * ecrVo.get("CreateDeptName") + "&quot;" ); strBuffer.append( " ProjectNo=&quot;" + ecrVo.get("ProjectNo") + "&quot;" +
	     * " ProjectNoOnClick=&quot;javascript:openWindow(&apos;/plm/jsp/project/ProjectViewFrm.jsp?oid=" + ecrVo.get("ProjectOid") +
	     * "&popup=popup&apos;, &apos;&apos;, 1050,800);&quot;" + " ProjectNoHtmlPrefix=&quot;&lt;font color=&apos;" +
	     * PropertiesUtil.getSearchGridLinkColor()+ "&apos;&gt;&quot; ProjectNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
	     * strBuffer.append( " Creator=&quot;" + ecrVo.get("CreatorName") + "&quot;" ); strBuffer.append( " CreateDate=&quot;" +
	     * ecrVo.get("CreateDate") + "&quot;" ); strBuffer.append( " StateAppro=&quot;" + ecrVo.get("SancStateFlag") + "&quot;" );
	     * strBuffer.append( "/>" ); }
	     */
	    strBuffer = dao.searchEcrList2nd(false, hash, conditionList);

	    // 검색조건 셋팅
	    request.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    request.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

    }// end-of-search

    /**
     * 
     * 함수명 : popupEcrSearch 설명 : 금형 ECR 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 남현승 작성일자 : 2013. 8. 5.
     */
    private void popupEcrSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETSearchEcoVO ketSearchEcoVO = new KETSearchEcoVO();
	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	MoldChangeRequestDao dao = new MoldChangeRequestDao();

	StringBuffer strBuffer = new StringBuffer();
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = null;
	String detailInfoUrl = null;
	String cnt = "1";

	try {
	    HttpSession session = request.getSession();
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

	    List<Map<String, Object>> list = dao.searchEcrList(false, hash, conditionList);

	    for (Map<String, Object> ecrVo : list) {

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" Oid=&quot;" + ecrVo.get("Oid") + "&quot;");
		strBuffer.append(" EcrNo=&quot;" + ecrVo.get("EcoId") + "&quot;");
		strBuffer.append(" EcrName=&quot;" + ecrVo.get("EcoName") + "&quot;");
		strBuffer.append(" DeptName=&quot;" + ecrVo.get("CreateDeptName") + "&quot;");
		strBuffer.append(" Creator=&quot;" + ecrVo.get("CreatorName") + "&quot;");
		strBuffer.append(" CompDate=&quot;" + ecrVo.get("CompleteReqDate") + "&quot;");
		strBuffer.append("/>");
	    }

	    // 검색조건 셋팅
	    request.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    request.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(request, response, "/jsp/ecm/SearchEcrPopupForm.jsp");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-popupEcrSearch

}
