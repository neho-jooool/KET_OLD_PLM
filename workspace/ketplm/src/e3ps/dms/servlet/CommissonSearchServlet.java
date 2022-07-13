package e3ps.dms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.dao.DevRequestDao;
import e3ps.dms.entity.KETCarYearlyAmount;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETPartCarLink;
import e3ps.dms.entity.KETRequestPartLink;
import e3ps.dms.entity.KETRequestPartList;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.TimerUtil;

/**
 * 클래스명 : CommissonSearchServlet.java 설명 : 개발(검토)의뢰 검색 작성자 : 남현승 작성일자 : 2013.06.27
 */
public class CommissonSearchServlet extends CommonServlet {

    private static final long serialVersionUID = 7345040309190875499L;

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getParameter(req, "command");

	if (command.equals("searchGridData")) {
	    search(req, res, false);
	} else if (command.equals("searchGridPage")) {
	    search(req, res, true);
	} else if (command.equals("excel")) {
	    excel(req, res);
	}
    }

    /**
     * @param hash
     * @return
     * @메소드명 : getDevRequestQuerySpec
     * @작성자 : Jason, Han
     * @작성일 : 2014. 6. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */

    private QuerySpec getDevRequestQuerySpec(KETParamMapUtil hash, TgPagingControl pager) throws Exception {

	QuerySpec spec = null;
	try {
	    spec = new QuerySpec();
	    Class<KETDevelopmentRequest> main_class = KETDevelopmentRequest.class;
	    int main_idx = spec.addClassList(main_class, true);
	    // int main_idx2 = spec.addClassList(WTUser.class, false);
	    //
	    // spec.appendWhere(new SearchCondition(new ClassAttribute(main_class, "iterationInfo.creator.key.id"), SearchCondition.EQUAL,
	    // new ClassAttribute(WTUser.class,
	    // "thePersistInfo.theObjectIdentifier.id")), new int[] { main_idx, main_idx2 });

	    // 의뢰번호
	    String requestNo = (String) hash.get("RequestNo");
	    if (StringUtil.checkString(requestNo)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(
		        new SearchCondition(main_class, KETDevelopmentRequest.NUMBER, "LIKE", StringUtil.changeString(
		                requestNo.toUpperCase(), "*", "%")), new int[] { main_idx });
	    }

	    // 작성일자
	    String predate = (String) hash.get("predate");
	    if (StringUtil.checkString(predate)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "thePersistInfo.createStamp", ">", DateUtil.convertStartDate2(predate)),
		        new int[] { main_idx });
	    }
	    String postdate = (String) hash.get("postdate");
	    if (StringUtil.checkString(postdate)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(postdate)),
		        new int[] { main_idx });
	    }

	    // 의뢰처
	    String requestBuyer = (String) hash.get("RequestBuyer");
	    String requestBuyerOid = "";
	    if (StringUtil.checkString(requestBuyer)) {
		QuerySpec nc_spec = new QuerySpec(NumberCode.class);
		nc_spec.appendWhere(new SearchCondition(NumberCode.class, NumberCode.CODE_TYPE, "=", "SUBCONTRACTOR"), new int[] { 0 });
		nc_spec.appendAnd();
		nc_spec.appendWhere(new SearchCondition(NumberCode.class, NumberCode.NAME, "LIKE", requestBuyer), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) nc_spec);
		if (qr != null && qr.hasMoreElements()) {
		    NumberCode ncode = (NumberCode) qr.nextElement();
		    requestBuyerOid = CommonUtil.getOIDString(ncode);
		}
		if (StringUtil.checkString(requestBuyerOid)) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(main_class, "requestBuyer", "LIKE", "%" + requestBuyerOid + "%"),
			    new int[] { main_idx });
		}
	    }

	    // 구분
	    String developmentStep = hash.getString("DevelopmentStep");

	    if (StringUtil.checkString(developmentStep)) {
		String[] developmentStepArr = developmentStep.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "developmentStep", developmentStepArr, true, false),
		        new int[] { main_idx });
	    }

	    // 결재상태
	    String authorStatus = "";
	    String[] authorStatusArr = {};

	    try {
		authorStatusArr = (String[]) hash.get("authorStatus");
	    } catch (Exception e) {
		authorStatus = (String) hash.get("authorStatus");
		authorStatusArr = authorStatus.split(",");
	    }

	    if (hash.get("authorStatus") != null) {

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "state.state", authorStatusArr, true, false), new int[] { main_idx });
	    }

	    // 사업부구분
	    String divCode = (String) hash.get("divCode");
	    if (!CommonUtil.isAdmin() && StringUtil.checkString(divCode)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendOpenParen();
		spec.appendWhere(new SearchCondition(main_class, "divisionCode", "=", divCode), new int[] { main_idx });
		spec.appendOr();
		spec.appendWhere(
		        new SearchCondition(main_class, "iterationInfo.creator.key.id", "=", CommonUtil
		                .getOIDLongValue((WTUser) SessionHelper.manager.getPrincipal())), new int[] { main_idx });
		// spec.appendWhere(new SearchCondition(main_class, "iterationInfo.creator.key.id", "=",
		// CommonUtil.getOIDLongValue2Str((WTUser)SessionHelper.manager.getPrincipal())), new int[] { main_idx });
		spec.appendCloseParen();
	    }

	    // 제품군
	    String productCategoryCode = (String) hash.get("ProductCategoryCode");
	    if (StringUtil.checkString(productCategoryCode)) {
		String[] productCategoryCodeArr = productCategoryCode.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		for (int i = 0; i < productCategoryCodeArr.length; i++) {
		    spec.appendWhere(new SearchCondition(main_class, "productCategoryCode", "LIKE", "%" + productCategoryCodeArr[i] + "%"),
			    new int[] { main_idx });
		    if (i != productCategoryCodeArr.length - 1) {
			spec.appendOr();
		    }
		}
	    }

	    // 대표차종
	    String repCarType = (String) hash.get("RepCarType");
	    if (StringUtil.checkString(repCarType)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "repCarType", "LIKE", "%" + repCarType + "%"), new int[] { main_idx });
	    }

	    // 의뢰담당자
	    String creator = (String) hash.get("creator");
	    if (StringUtil.checkString(creator)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "iterationInfo.creator.key.id", "=", CommonUtil.getOIDLongValue(creator)),
		        new int[] { main_idx });
	    }

	    // 개발제품명
	    String devProductName = (String) hash.get("DevProductName");
	    if (StringUtil.checkString(devProductName)) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "devProductName", "LIKE", "%" + devProductName + "%"),
		        new int[] { main_idx });
	    }

	    // 최종사용처
	    String lastUsingBuyer = (String) hash.get("LastUsingBuyer");
	    if (StringUtil.checkString(lastUsingBuyer)) {
		QuerySpec nc_spec = new QuerySpec(NumberCode.class);
		nc_spec.appendWhere(new SearchCondition(NumberCode.class, NumberCode.CODE_TYPE, "=", "CUSTOMEREVENT"), new int[] { 0 });
		nc_spec.appendAnd();
		nc_spec.appendWhere(new SearchCondition(NumberCode.class, NumberCode.NAME, "LIKE", lastUsingBuyer), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) nc_spec);
		if (qr != null && qr.hasMoreElements()) {
		    NumberCode ncode = (NumberCode) qr.nextElement();
		    lastUsingBuyer = CommonUtil.getOIDString(ncode);
		}
		if (StringUtil.checkString(lastUsingBuyer)) {
		    if (spec.getConditionCount() > 0)
			spec.appendAnd();
		    spec.appendWhere(new SearchCondition(main_class, "lastUsingBuyer", "LIKE", "%" + lastUsingBuyer + "%"),
			    new int[] { main_idx });
		}
	    }

	    // 개발(검토)유형
	    String devReviewTypeCode = (String) hash.get("HdDevReviewTypeCode");
	    if (StringUtil.checkString(devReviewTypeCode)) {
		String[] devReviewTypeCodeArr = devReviewTypeCode.split(",");
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "devReviewTypeCode", devReviewTypeCodeArr, true, false),
		        new int[] { main_idx });
	    }

	    // 최신 이터레이션
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(new SearchCondition(main_class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE),
		    new int[] { main_idx });

	    // [추가사항] 승인완료인 상태만 검색 되어지게 셋팅
	    String isPopup = (String) hash.get("isPopup");
	    if ("yes".equals(isPopup)) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(main_class, "state.state", "=", "APPROVED"), new int[] { main_idx });
	    }
	    // if (pager != null && !StringUtil.isEmpty(pager.getSortCol())) {
	    // if ("RequestDivision".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, main_class, main_idx, "developmentStep", pager.getSortTypeForWcQuery());
	    // } else if ("RequestNo".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, main_class, main_idx, KETDevelopmentRequest.NUMBER, pager.getSortTypeForWcQuery());
	    // // } else if ("ReviewPjtNo".equals(pager.getSortCol())) {
	    // // SearchUtil.setOrderBy(spec, main_class, main_idx, "master>number", pager.getSortTypeForWcQuery());
	    // // } else if ("DevPjtNo".equals(pager.getSortCol())) {
	    // // SearchUtil.setOrderBy(spec, main_class, main_idx, "master>number", pager.getSortTypeForWcQuery());
	    // } else if ("DevProdName".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, main_class, main_idx, "devProductName", pager.getSortTypeForWcQuery());
	    // // } else if ("RequestBuyer".equals(pager.getSortCol())) {
	    // // SearchUtil.setOrderBy(spec, main_class, main_idx, "master>number", pager.getSortTypeForWcQuery());
	    // } else if ("Creator".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, WTUser.class, main_idx2, "fullName", pager.getSortTypeForWcQuery());
	    // } else if ("CreateDate".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, main_class, main_idx, "thePersistInfo.createStamp", pager.getSortTypeForWcQuery());
	    // } else if ("Step".equals(pager.getSortCol())) {
	    // SearchUtil.setOrderBy(spec, main_class, main_idx, "state.state", pager.getSortTypeForWcQuery());
	    // }
	    // }
	    Kogger.debug(getClass(), "spec >> " + spec);

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return spec;

    }

    /**
     * 함수명 : search 설명 : 개발(검토)의뢰 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 남현승 작성일자 : 2013.06.27
     */
    private void search(HttpServletRequest req, HttpServletResponse res, boolean isPaging) {

	// [START][KET PLM 고도화 프로젝트] 개발검토의뢰서 검색 개선, 2014. 6. 17. Jason, Han
	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;
	String detailInfoUrl = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader.getFormParameters());

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) getDevRequestQuerySpec(hash, null));

	    if (qr != null && qr.hasMoreElements()) {
		while (qr.hasMoreElements()) {
		    Object[] objArr = (Object[]) qr.nextElement();
		    KETDevelopmentRequest d_request = (KETDevelopmentRequest) objArr[0];
		    String developmentStepStr = ("R".equals(d_request.getDevelopmentStep())) ? "검토의뢰" : "개발의뢰";
		    String reviewProjNo = "";
		    String prodProjNo = "";
		    String reviewProOid = "";

		    /**** 검토 프로젝트No 가져오기 Start 2015.04/16 황정태 ****/
		    if ("D".equals(d_request.getDevelopmentStep())) { // 개발의뢰
			reviewProOid = d_request.getProjectOID();
			if (!"".equals(reviewProOid) && !"null".equals(reviewProOid) && reviewProOid != null) {
			    String objs[] = reviewProOid.split(",");
			    for (String target : objs) {
				E3PSProject revProject = (E3PSProject) CommonUtil.getObject(target);
				reviewProjNo += revProject.getPjtNo() + ",";
			    }
			}

			reviewProjNo = StringUtils.removeEnd(reviewProjNo, ",");
		    } else {// 검토의뢰
			QueryResult qrRevpjt = ProjectHelper.getReviewRequestProject(d_request);
			if (qrRevpjt != null && qrRevpjt.size() > 0) {
			    if (qrRevpjt.hasMoreElements()) {
				Object[] objArr2 = (Object[]) qrRevpjt.nextElement();
				E3PSProject proj = (E3PSProject) objArr2[0];
				reviewProjNo = proj.getPjtNo();
			    }
			}
		    }
		    /**** 검토 프로젝트No 가져오기 End ****/

		    /**** 제품 프로젝트No 가져오기 Start 2015.04/16 황정태 ****/
		    QueryResult qrPrj = ProjectHelper.getDevRequestProject(d_request);
		    if (qrPrj != null && qrPrj.size() > 0) {
			if (qrPrj.hasMoreElements()) {
			    Object[] objArr2 = (Object[]) qrPrj.nextElement();
			    E3PSProject proj = (E3PSProject) objArr2[0];
			    if ("D".equals(d_request.getDevelopmentStep()))
				prodProjNo = proj.getPjtNo() + " 외 " + (qrPrj.size() - 1) + "건";
			}
		    }
		    /**** 제품 프로젝트No 가져오기 End ****/

		    String requestBuyerDisplay = "";
		    if (StringUtil.checkString(d_request.getRequestBuyer())) {
			String[] requestBuyerArr = d_request.getRequestBuyer().split(",");
			for (String requestBuyerOidStr : requestBuyerArr) {
			    NumberCode rbCode = (NumberCode) CommonUtil.getObject(requestBuyerOidStr);
			    requestBuyerDisplay += "," + rbCode.getName();
			}
			requestBuyerDisplay = requestBuyerDisplay.substring(1);
		    }

		    detailInfoUrl = "/plm/jsp/dms/ViewDevRequest.jsp?oid=" + CommonUtil.getOIDString(d_request); // 의뢰번호 상세화면으로 이동

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");
		    strBuffer.append(" RowNum=\"" + rowCount++ + "\"");
		    strBuffer.append(" Oid=\"" + CommonUtil.getOIDString(d_request) + "\"");
		    strBuffer.append(" RequestDivision=\"" + developmentStepStr + "\""); // 구분
		    strBuffer.append(" RequestNo=\"" + d_request.getNumber() + "\"" + " RequestNoOnClick=\"openView(&apos;"
			    + CommonUtil.getOIDString(d_request) + "&apos;)\"" + " RequestNoHtmlPrefix=\"&lt;font color=&apos;"
			    + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;\" RequestNoHtmlPostfix=\"&lt;/font&gt;\""); // 의뢰번호
		    strBuffer.append(" ReviewPjtNo=\"" + reviewProjNo + "\""); // 검토
		    strBuffer.append(" DevPjtNo=\"" + prodProjNo + "\""); // 개발
		    strBuffer.append(" DevProdName=\"" + StringUtil.checkNull(d_request.getDevProductName()) + "\""); // 개발 제품명
		    strBuffer.append(" RequestBuyer=\"" + requestBuyerDisplay + "\""); // 의뢰처
		    strBuffer.append(" Creator=\"" + d_request.getCreatorFullName() + "\"");
		    strBuffer.append(" CreateDate=\"" + DateUtil.getDateString(d_request.getPersistInfo().getCreateStamp(), "d") + "\"");
		    strBuffer.append(" Step=\"" + StringUtil.checkNull(d_request.getLifeCycleState().getDisplay()) + "\"");
		    strBuffer.append("/>");
		}
	    }
	    // 검색조건 셋팅
	    req.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    // gotoResult(req, res, "/jsp/dms/SearchDevRequest.jsp");
	    // req.setAttribute("rootCount", String.valueOf(totalCount));
	    req.setAttribute("pagingLength", "1");
	    gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	    // }
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
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
	String divCode = "";

	try {

	    if (CommonUtil.isMember("전자사업부")) {
		divCode = "ER";
	    } else if (CommonUtil.isMember("자동차사업부")) {
		divCode = "CA";
	    } else {
		divCode = "CA";
	    }

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    DevRequestDao devRequestDao = new DevRequestDao(conn);

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
	    String sFileName = "SearchDevRequestList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		TimerUtil timer = new TimerUtil(getServletName());
		timer.setStartPoint("Workbook header create");

		WritableWorkbook workbook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = workbook.createSheet("First Sheet", 0);

		s1.setName("의뢰서 검색목록");

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		Label lr = new jxl.write.Label(0, 0, "의뢰서 검색목록");
		s1.addCell(lr);

		int row = 2;
		int rowCount = 0;
		String rowCnt;

		lr = new jxl.write.Label(0, row, "의뢰번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(1, row, "개발프로젝트번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(2, row, "검토프로젝트번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(3, row, "개발단계", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(4, row, "개발(검토)유형", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(5, row, "제품군", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(6, row, "개발(검토) 제품명", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(7, row, "의뢰부서", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(8, row, "의뢰담당자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(9, row, "의뢰일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(10, row, "상신일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(11, row, "승인일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(12, row, "개발부서", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(13, row, "개발담당자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(14, row, "결재상태", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(15, row, "의뢰처 담당자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(16, row, "최종사용처 담당자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(17, row, "상세내용", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(18, row, "품목수", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(19, row, "의뢰처", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(20, row, "납입처", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(21, row, "최종사용처", cellFormat);
		s1.addCell(lr);

		if (divCode.equals("CA")) {
		    lr = new jxl.write.Label(22, row, "대표차종", cellFormat);
		} else {
		    lr = new jxl.write.Label(22, row, "적용기기", cellFormat);
		}
		s1.addCell(lr);

		lr = new jxl.write.Label(23, row, "U/S", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(24, row, "예상수량(1년차)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(25, row, "예상수량(2년차)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(26, row, "예상수량(3년차)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(27, row, "예상수량(4년차)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(28, row, "예상수량(5년차)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(29, row, "예상수량(6년차~)[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(30, row, "수량합계[천개]", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(31, row, "양산 1차년도", cellFormat);
		s1.addCell(lr);

		if (divCode.equals("CA")) {
		    lr = new jxl.write.Label(32, row, "DR0 요구일정", cellFormat);
		} else {
		    lr = new jxl.write.Label(32, row, "DR1 요구일정", cellFormat);
		}
		s1.addCell(lr);

		lr = new jxl.write.Label(33, row, "제안서 체출일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(34, row, "개발원가 제출일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(35, row, "초도품 제출일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(36, row, "ESIR일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(37, row, "ISIR일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(38, row, "KET 양산일정", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(39, row, "제품Type", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(40, row, "기타 내용", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(41, row, "TabSize", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(42, row, "원자재/부자재", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(43, row, "표면처리", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(44, row, "적용전선", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(45, row, "주요기능", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(46, row, "향후전망", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(47, row, "금형비 감가(판매)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(48, row, "금형비 감가(지급)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(49, row, "금형비 감가(감가기간)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(50, row, "금형비 감가(기타사항)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(51, row, "설비비 감가(판매)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(52, row, "설비비 감가(지급)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(53, row, "설비비 감가(감가기간)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(54, row, "설비비 감가(기타사항)", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(55, row, "Device 사양", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(56, row, "환경규제항목", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(57, row, "기타 요청사항", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(58, row, "추가 요구사항", cellFormat);
		s1.addCell(lr);

		// String s_drOid = null;
		String s_drNo = null;
		String s_developmentStep = null;
		String s_projectNo = null;
		String s_repCarType = null;
		String s_devProductName = null;
		String s_requestBuyer = null;
		String s_devReviewTypeCode = null;
		String s_drState = null;
		String s_drCreator = null;
		String s_drCreateDate = null;
		// String s_partName = null;
		// String s_carTypeCode = null;

		String reqNo = "";
		String reqName = "";
		String reception = "";
		String receptionName = "";
		String creatorName = "";
		String insDate = "";
		String deptName = "";
		String lifeCycleState = "";
		String stateState = "";
		String divisionCode = "";
		String projectOID = "";
		String projectNO = "";

		String requestBuyerManager = "";
		String lastUsingBuyer = "";
		String lastUsingBuyerManager = "";

		String isDRRequest = "";
		String drRequestDate = "";
		String isProposalSubmit = "";
		String proposalSubmitDate = "";
		String isCostSubmit = "";
		String costSubmitDate = "";

		String devReviewTypeEtc = "";
		String devReviewDetailComment = "";

		String productSaleFirstYear = "";

		String initialSampleSummitDate = "";
		String eSIRDate = "";
		String iSIRDate = "";
		String ketMassProductionDate = "";

		String productCategoryCode = "";
		String productTypeCode = "";

		String etcSpecification = "";
		String tabSize = "";
		String materialSubMaterial = "";
		String surfaceTreatmentCode = "";
		String applyedWire = "";
		String primaryFunction = "";
		String outlook = "";

		String moldDepreciationTypeSale = "";
		String moldDepreciationTypeGeneral = "";
		String moldDepreciationTypePayment = "";
		String moldDepreciationTypePeriod = "";
		String moldDepreciationTypeEtcInfo = "";

		String equipDepreciationTypeSale = "";
		String equipDepreciationTypePayment = "";
		String equipDepreciationTypePeriod = "";
		String equipDepreciationTypeEtcInfo = "";

		String deviceSpecification = "";
		String environmentalRegulationItem = "";
		String buyerEtcRequirement = "";
		String salesAdditionalRequirement = "";

		String productCategoryCon = "";
		String moldDepreciationType = "";
		String equipDepreciationType = "";
		String activityName = "";
		String completeDate = "";
		String approvalDate = "";
		String requestDate = "";
		KETDevelopmentRequest dr = null;

		timer.setEndPoint();
		timer.setStartPoint("devRequestDao.ViewDRList Query");
		// 목록 결과
		QueryResult result = PersistenceHelper.manager.find((StatementSpec) getDevRequestQuerySpec(map, null));
		timer.setEndPoint();
		if (result != null) {
		    timer.setStartPoint("Worksheet content create");
		    while (result.hasMoreElements()) {
			Object[] objects = (Object[]) result.nextElement();
			dr = (KETDevelopmentRequest) objects[0];

			// =========결재 요청일,승인일 관련 시작====================//
			// Object obj2 = CommonUtil.getObject(s_drOid);
			KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
			KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
			KETWfmApprovalMaster master = null;
			Object temp = new Object();
			Vector vec = null;
			WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) dr);
			if (targetObj != null)
			    master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
			if (master != null) {

			    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

			    for (int k = 0; k < vec.size() - 1; k++) {
				for (int j = k + 1; j < vec.size(); j++) {
				    history1 = (KETWfmApprovalHistory) vec.get(k);
				    history2 = (KETWfmApprovalHistory) vec.get(k);
				    if (history1.getSeqNum() < history2.getSeqNum()) {
					temp = vec.get(k);
					vec.set(k, vec.get(j));
					vec.set(j, temp);
				    }
				}
			    }
			}
			// =========결재 요청일,승인일 관련 끝====================//

			if (vec != null) {
			    boolean isApprover = true;
			    activityName = "";
			    int iComplet = 0;
			    for (int x = 0; x < vec.size(); x++) {
				KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
				activityName = StringUtil.checkNull(history.getActivityName());
				if (activityName.equals("검토")) {
				    iComplet++;
				}
			    }

			    for (int x = 0; x < vec.size(); x++) {
				KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
				activityName = StringUtil.checkNull(history.getActivityName());

				if (activityName.equals("합의")) {
				    iComplet++;
				}

				if (x == iComplet && isApprover && activityName.equals("검토")) {
				    activityName = "승인";
				    isApprover = false;
				}
				if (history.getCompletedDate() != null)
				    completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				if (activityName.equals("승인"))
				    approvalDate = completeDate;
				if (activityName.equals("요청"))
				    requestDate = completeDate;

			    }
			}

			reqNo = dr.getNumber();
			divisionCode = StringUtil.checkNull(dr.getDivisionCode());
			creatorName = StringUtil.checkNull(dr.getIterationInfo().getCreator().getFullName());
			lifeCycleState = dr.getLifeCycleState().getDisplay();
			insDate = DateUtil.getTimeFormat(dr.getCreateTimestamp(), "yyyy-MM-dd");

			deptName = StringUtil.checkNull(dr.getDeptName());
			requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());

			lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer());
			if (!StringUtil.checkNull(lastUsingBuyer).equals("")) {
			    StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ",");
			    String ct = "";
			    String bName = "";
			    while (st1.hasMoreTokens()) {
				ct = st1.nextToken();
				NumberCode nCode2 = (NumberCode) CommonUtil.getObject(ct);

				if (nCode2 == null) {
				    bName = bName + "," + ct;
				} else {
				    bName = bName + "," + nCode2.getName();
				}
			    }
			    if (!bName.equals(""))
				lastUsingBuyer = bName.substring(1);
			}

			lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());

			isDRRequest = StringUtil.checkNull(dr.getIsDRRequest());
			if (isDRRequest.equals("1")) {
			    drRequestDate = DateUtil.getTimeFormat(dr.getDrRequestDate(), "yyyy-MM-dd");
			} else {
			    drRequestDate = "불필요";
			}

			isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
			if (isProposalSubmit.equals("1")) {
			    proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(), "yyyy-MM-dd");
			} else {
			    proposalSubmitDate = "불필요";
			}

			isCostSubmit = StringUtil.checkNull(dr.getIsCostSubmit());
			if (isCostSubmit.equals("1")) {
			    costSubmitDate = DateUtil.getTimeFormat(dr.getCostSubmitDate(), "yyyy-MM-dd");
			} else {
			    costSubmitDate = "불필요";
			}

			devReviewTypeEtc = StringUtil.checkNull(dr.getDevReviewTypeEtc());
			devReviewDetailComment = StringUtil.checkNull(dr.getDevReviewDetailComment());

			productSaleFirstYear = StringUtil.checkNull(dr.getProductSaleFirstYear());

			initialSampleSummitDate = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(), "yyyy-MM-dd");
			eSIRDate = DateUtil.getTimeFormat(dr.getESIRDate(), "yyyy-MM-dd");
			iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(), "yyyy-MM-dd");
			ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(), "yyyy-MM-dd");

			String nCodeOid = StringUtil.checkNull(dr.getProductCategoryCode());
			String productTypeCodes = StringUtil.checkNull(dr.getProductTypeCode());

			if (nCodeOid != null && nCodeOid.length() > 4 && nCodeOid.substring(0, 4).equals("e3ps")) {
			    NumberCode nCode = (NumberCode) CommonUtil.getObject(nCodeOid);
			    productCategoryCode = nCode.getName();
			    ArrayList carProductTypeCodeList = NumberCodeHelper.getChildNumberCode(nCode);
			    productTypeCode = "";
			    for (int j = 0; j < carProductTypeCodeList.size(); j++) {
				String numCodeOid = ((NumberCode) carProductTypeCodeList.get(j)).getPersistInfo().getObjectIdentifier()
				        .getStringValue();
				String numCodeName = ((NumberCode) carProductTypeCodeList.get(j)).getName();
				if (productTypeCodes.indexOf(numCodeOid) >= 0) {
				    productTypeCode = productTypeCode + " / " + numCodeName;
				}
			    }
			    if (productTypeCode.length() > 3)
				productTypeCode = productTypeCode.substring(3);
			} else {
			    productCategoryCode = "";
			    productTypeCode = "";
			}

			etcSpecification = StringUtil.checkNull(dr.getEtcSpecification());
			tabSize = StringUtil.checkNull(dr.getTabSize());
			materialSubMaterial = StringUtil.checkNull(dr.getMaterialSubMaterial());
			surfaceTreatmentCode = StringUtil.checkNull(dr.getSurfaceTreatmentCode());

			if (surfaceTreatmentCode.equals("0"))
			    surfaceTreatmentCode = "";

			if (!StringUtil.checkNull(surfaceTreatmentCode).equals("")) {

			    NumberCode nCode1 = (NumberCode) CommonUtil.getObject(surfaceTreatmentCode);
			    String bName = "";
			    if (nCode1 == null) {
				bName = surfaceTreatmentCode;
			    } else {
				bName = nCode1.getName();
			    }
			    if (!bName.equals(""))
				surfaceTreatmentCode = bName;
			}

			applyedWire = StringUtil.checkNull(dr.getApplyedWire());
			primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction());
			outlook = StringUtil.checkNull(dr.getOutlook());

			moldDepreciationTypeSale = StringUtil.checkNull(dr.getMoldDepreciationTypeSale());
			moldDepreciationTypeGeneral = StringUtil.checkNull(dr.getMoldDepreciationTypeGeneral());
			moldDepreciationTypePayment = StringUtil.checkNull(dr.getMoldDepreciationTypePayment());
			moldDepreciationTypePeriod = StringUtil.checkNull(dr.getMoldDepreciationTypePeriod());
			moldDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getMoldDepreciationTypeEtcInfo());

			if (!moldDepreciationTypeSale.equals(""))
			    moldDepreciationType = moldDepreciationType + "/판매";
			if (!moldDepreciationTypeGeneral.equals(""))
			    moldDepreciationType = moldDepreciationType + "/일반";
			if (!moldDepreciationTypePayment.equals(""))
			    moldDepreciationType = moldDepreciationType + "/지급";
			if (!moldDepreciationTypePeriod.equals(""))
			    moldDepreciationType = moldDepreciationType + "/감가기간";
			if (!moldDepreciationTypeEtcInfo.equals(""))
			    moldDepreciationType = moldDepreciationType + "/기타:" + moldDepreciationTypeEtcInfo;
			if (moldDepreciationType.equals("") || moldDepreciationType.equals("/"))
			    moldDepreciationType = "";
			else
			    moldDepreciationType = moldDepreciationType.substring(1);

			equipDepreciationTypeSale = StringUtil.checkNull(dr.getEquipDepreciationTypeSale());
			equipDepreciationTypePayment = StringUtil.checkNull(dr.getEquipDepreciationTypePayment());
			equipDepreciationTypePeriod = StringUtil.checkNull(dr.getEquipDepreciationTypePeriod());
			equipDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getEquipDepreciationTypeEtcInfo());

			if (!equipDepreciationTypeSale.equals(""))
			    equipDepreciationType = equipDepreciationType + "/판매";
			if (!equipDepreciationTypePayment.equals(""))
			    equipDepreciationType = equipDepreciationType + "/지급";
			if (!equipDepreciationTypePeriod.equals(""))
			    equipDepreciationType = equipDepreciationType + "/감가기간";
			if (!equipDepreciationTypeEtcInfo.equals(""))
			    equipDepreciationType = equipDepreciationType + "/기타:" + equipDepreciationTypeEtcInfo;
			if (equipDepreciationType.equals("") || equipDepreciationType.equals("/"))
			    equipDepreciationType = "";
			else
			    equipDepreciationType = equipDepreciationType.substring(1);

			deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
			environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem());
			buyerEtcRequirement = StringUtil.checkNull(dr.getBuyerEtcRequirement());
			salesAdditionalRequirement = StringUtil.checkNull(dr.getSalesAdditionalRequirement());

			s_drNo = dr.getNumber();

			s_developmentStep = StringUtil.checkNull(dr.getDevelopmentStep());
			if (s_developmentStep.equals("R")) {
			    s_developmentStep = "검토의뢰";
			} else if (s_developmentStep.equals("D")) {
			    s_developmentStep = "개발의뢰";
			}

			s_projectNo = StringUtil.checkNull(dr.getProjectOID());
			if (!s_projectNo.equals("")) {
			    Object obj = CommonUtil.getObject(s_projectNo);

			    if (obj == null) {
				s_projectNo = "";
			    } else {
				if (obj instanceof E3PSProject) {
				    E3PSProject project = (E3PSProject) obj;
				    s_projectNo = project.getPjtNo();
				} else {
				    s_projectNo = "";
				}
			    }
			}

			s_repCarType = StringUtil.checkNull(dr.getRepCarType());
			s_devProductName = StringUtil.checkNull(dr.getDevProductName());
			s_requestBuyer = StringUtil.checkNull(dr.getRequestBuyer());
			StringTokenizer st = new StringTokenizer(s_requestBuyer, ",");
			String ct = "";
			String bName = "";
			while (st.hasMoreTokens()) {
			    ct = st.nextToken();
			    NumberCode nCode1 = null;
			    if (ct.indexOf("NumberCode") != -1)
				nCode1 = (NumberCode) CommonUtil.getObject(ct);

			    if (nCode1 == null) {
				bName = bName + "," + ct;
			    } else {
				bName = bName + "," + nCode1.getName();
			    }
			}
			s_requestBuyer = bName.substring(1);

			s_devReviewTypeCode = StringUtil.checkNull(dr.getDevReviewTypeCode());
			if (!StringUtil.checkNull(s_devReviewTypeCode).equals("")) {

			    NumberCode nCode1 = null;
			    if (s_devReviewTypeCode.indexOf("NumberCode") != -1)
				nCode1 = (NumberCode) CommonUtil.getObject(s_devReviewTypeCode);
			    bName = "";
			    if (nCode1 == null) {
				bName = s_devReviewTypeCode;
			    } else {
				bName = nCode1.getName();
			    }
			    if (!bName.equals(""))
				s_devReviewTypeCode = bName;
			}
			if (s_devReviewTypeCode.equals("기타"))
			    s_devReviewTypeCode = "기타: " + devReviewTypeEtc;

			s_drState = StringUtil.checkNull(dr.getState().getState().getDisplay());
			s_drCreator = StringUtil.checkNull(dr.getCreator().getFullName());
			s_drCreateDate = StringUtil.checkNull(DateUtil.getDateString(dr.getPersistInfo().getCreateStamp(), "d"));
			// s_partName = StringUtil.checkNull((String) dev.get("partName"));
			// s_carTypeCode = StringUtil.checkNull((String) dev.get("carTypeCode"));

			KETRequestPartList pl = null;
			QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);

			String itemCnt = String.valueOf(r.size());
			String sdName = "";
			double d = 0;
			int y1 = 0;
			int y2 = 0;
			int y3 = 0;
			int y4 = 0;
			int y5 = 0;
			int y6 = 0;
			int sum = 0;
			int usg = 0;

			if (r.hasMoreElements()) {
			    pl = (KETRequestPartList) r.nextElement();
			    String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());

			    NumberCode nCd = null;
			    if (summitDestinationOid.indexOf("NumberCode") != -1)
				nCd = (NumberCode) CommonUtil.getObject(summitDestinationOid);
			    if (nCd == null) {
				sdName = summitDestinationOid;
			    } else {
				sdName = nCd.getName();
			    }

			    KETCarYearlyAmount cy = null;
			    QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

			    if (r1.hasMoreElements()) {
				cy = (KETCarYearlyAmount) r1.nextElement();

				d = cy.getYearAmount1() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y1 = y1 + (int) d;
				d = cy.getYearAmount2() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y2 = y2 + (int) d;
				d = cy.getYearAmount3() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y3 = y3 + (int) d;
				d = cy.getYearAmount4() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y4 = y4 + (int) d;
				d = cy.getYearAmount5() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y5 = y5 + (int) d;
				d = cy.getYearAmount6() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
				y6 = y6 + (int) d;

				if (usg < cy.getApplyedUsage()) {
				    usg = cy.getApplyedUsage().intValue();
				}
			    }
			    sum = y1 + y2 + y3 + y4 + y5 + y6;
			}

			String s_y1 = "";
			String s_y2 = "";
			String s_y3 = "";
			String s_y4 = "";
			String s_y5 = "";
			String s_y6 = "";
			String s_sum = "";
			String s_usg = "";

			s_y1 = String.valueOf(y1);
			s_y2 = String.valueOf(y2);
			s_y3 = String.valueOf(y3);
			s_y4 = String.valueOf(y4);
			s_y5 = String.valueOf(y5);
			s_y6 = String.valueOf(y6);
			s_sum = String.valueOf(sum);
			s_usg = String.valueOf(usg);

			String s_prjCnt = "";
			String s_prjNo = "";
			String s_prjDeptNm = "";
			String s_prjPmName = "";

			QueryResult qrPrj = ProjectHelper.getDevRequestProject(dr);
			Object[] objPrj = null;
			E3PSProject eep = null;

			if (qrPrj.hasMoreElements()) {
			    s_prjCnt = String.valueOf(qrPrj.size() - 1);
			    objPrj = (Object[]) qrPrj.nextElement();
			    eep = (E3PSProject) objPrj[0];
			    s_prjNo = StringUtil.checkNull(eep.getPjtNo());
			    if (qrPrj.size() > 1) {
				s_prjNo = s_prjNo + "외 " + s_prjCnt + "건";
			    }
			    if ("209698255".equals(Long.toString(CommonUtil.getOIDLongValue(dr)))
				    || "133893747".equals(Long.toString(CommonUtil.getOIDLongValue(dr)))) {
				// E3PSProjectData projectData = new E3PSProjectData(eep);

				if (eep instanceof ReviewProject) {
				    String rvOid = CommonUtil.getOIDString(eep);
				    ReviewProject rp = (ReviewProject) CommonUtil.getObject(rvOid);
				    if (rp.getDevDept() != null) {
					s_prjDeptNm = rp.getDevDept().getName();
				    }
				} else {
				    s_prjDeptNm = "";// projectData.department;
				}
				s_prjPmName = "";// projectData.pjtPmName;
			    } else {

				if (eep instanceof ReviewProject) {
				    String rvOid = CommonUtil.getOIDString(eep);
				    ReviewProject rp = (ReviewProject) CommonUtil.getObject(rvOid);
				    if (rp.getDevDept() != null) {
					s_prjDeptNm = rp.getDevDept().getName();
				    }
				} else {
				    QueryResult departmentQr = ProjectUserHelper.manager.getViewDepartmentLink(eep, null);
				    if (departmentQr.hasMoreElements()) {
					Object[] obj = (Object[]) departmentQr.nextElement();
					ProjectViewDepartmentLink link = (ProjectViewDepartmentLink) obj[0];
					s_prjDeptNm = link.getDepartment().getName();
				    }
				}
				s_prjPmName = (ProjectUserHelper.manager.getPM(eep) == null) ? "" : ProjectUserHelper.manager.getPM(eep)
				        .getFullName();
			    }
			}

			if (s_developmentStep.equals("검토의뢰")) {
			    s_projectNo = s_prjNo;
			    s_prjNo = "";
			}

			row++;
			// 의뢰번호
			rowCount++;
			rowCnt = String.valueOf(rowCount);
			lr = new jxl.write.Label(0, row, s_drNo, cellFormat);
			s1.addCell(lr);

			// 개발프로젝트번호
			lr = new jxl.write.Label(1, row, s_prjNo, cellFormat);
			s1.addCell(lr);

			// 검토프로젝트번호
			lr = new jxl.write.Label(2, row, s_projectNo, cellFormat);
			s1.addCell(lr);

			// 개발단계
			lr = new jxl.write.Label(3, row, s_developmentStep, cellFormat);
			s1.addCell(lr);

			// 개발(검토)유형
			lr = new jxl.write.Label(4, row, s_devReviewTypeCode, cellFormat);
			s1.addCell(lr);

			// 제품군
			lr = new jxl.write.Label(5, row, productCategoryCode, cellFormat);
			s1.addCell(lr);

			// 개발(검토) 제품명
			lr = new jxl.write.Label(6, row, s_devProductName, cellFormat);
			s1.addCell(lr);

			// 의뢰부서
			lr = new jxl.write.Label(7, row, deptName, cellFormat);
			s1.addCell(lr);

			// 의뢰담당자
			lr = new jxl.write.Label(8, row, creatorName, cellFormat);
			s1.addCell(lr);

			// 의뢰일자
			lr = new jxl.write.Label(9, row, insDate, cellFormat);
			s1.addCell(lr);

			// 상신일자
			lr = new jxl.write.Label(10, row, requestDate, cellFormat);
			s1.addCell(lr);

			// 승인일자
			lr = new jxl.write.Label(11, row, approvalDate, cellFormat);
			s1.addCell(lr);

			// 개발부서
			lr = new jxl.write.Label(12, row, s_prjDeptNm, cellFormat);
			s1.addCell(lr);

			// 개발담당자
			lr = new jxl.write.Label(13, row, s_prjPmName, cellFormat);
			s1.addCell(lr);

			// 결재상태
			lr = new jxl.write.Label(14, row, lifeCycleState, cellFormat);
			s1.addCell(lr);

			// 의뢰처 담당자
			lr = new jxl.write.Label(15, row, requestBuyerManager, cellFormat);
			s1.addCell(lr);

			// 최종사용처 담당자
			lr = new jxl.write.Label(16, row, lastUsingBuyerManager, cellFormat);
			s1.addCell(lr);

			// 상세내용
			lr = new jxl.write.Label(17, row, devReviewDetailComment, cellFormat);
			s1.addCell(lr);

			// 품목수
			lr = new jxl.write.Label(18, row, itemCnt, cellFormat);
			s1.addCell(lr);

			// 의뢰처
			lr = new jxl.write.Label(19, row, s_requestBuyer, cellFormat);
			s1.addCell(lr);

			// 납입처
			lr = new jxl.write.Label(20, row, sdName, cellFormat);
			s1.addCell(lr);

			// 최종사용처
			lr = new jxl.write.Label(21, row, lastUsingBuyer, cellFormat);
			s1.addCell(lr);

			// 대표차종
			lr = new jxl.write.Label(22, row, s_repCarType, cellFormat);
			s1.addCell(lr);

			// US
			lr = new jxl.write.Label(23, row, s_usg, cellFormat);
			s1.addCell(lr);

			// 예상수량(1년차)[천개]
			lr = new jxl.write.Label(24, row, StringUtil.getDouble(s_y1, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 예상수량(2년차)[천개]
			lr = new jxl.write.Label(25, row, StringUtil.getDouble(s_y2, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 예상수량(3년차)[천개]
			lr = new jxl.write.Label(26, row, StringUtil.getDouble(s_y3, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 예상수량(4년차)[천개]
			lr = new jxl.write.Label(27, row, StringUtil.getDouble(s_y4, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 예상수량(5년차)[천개]
			lr = new jxl.write.Label(28, row, StringUtil.getDouble(s_y5, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 예상수량(6년차~)[천개]
			lr = new jxl.write.Label(29, row, StringUtil.getDouble(s_y6, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 수량합계[천개]
			lr = new jxl.write.Label(30, row, StringUtil.getDouble(s_sum, "", "###,###"), cellFormat);
			s1.addCell(lr);

			// 양산 1차년도
			lr = new jxl.write.Label(31, row, productSaleFirstYear, cellFormat);
			s1.addCell(lr);

			// DR0 요구일정
			lr = new jxl.write.Label(32, row, drRequestDate, cellFormat);
			s1.addCell(lr);

			// 제안서 체출일정
			lr = new jxl.write.Label(33, row, proposalSubmitDate, cellFormat);
			s1.addCell(lr);

			// 개발원가 제출일정
			lr = new jxl.write.Label(34, row, costSubmitDate, cellFormat);
			s1.addCell(lr);

			// 초도품 제출일정
			lr = new jxl.write.Label(35, row, initialSampleSummitDate, cellFormat);
			s1.addCell(lr);

			// ESIR일정
			lr = new jxl.write.Label(36, row, eSIRDate, cellFormat);
			s1.addCell(lr);

			// ISIR일정
			lr = new jxl.write.Label(37, row, iSIRDate, cellFormat);
			s1.addCell(lr);

			// KET 양산일정
			lr = new jxl.write.Label(38, row, ketMassProductionDate, cellFormat);
			s1.addCell(lr);

			// 제품Type
			lr = new jxl.write.Label(39, row, productTypeCode, cellFormat);
			s1.addCell(lr);

			// 기타 내용
			lr = new jxl.write.Label(40, row, etcSpecification, cellFormat);
			s1.addCell(lr);

			// TabSize
			lr = new jxl.write.Label(41, row, tabSize, cellFormat);
			s1.addCell(lr);

			// 원자재/부자재
			lr = new jxl.write.Label(42, row, materialSubMaterial, cellFormat);
			s1.addCell(lr);

			// 표면처리
			lr = new jxl.write.Label(43, row, surfaceTreatmentCode, cellFormat);
			s1.addCell(lr);

			// 적용전선
			lr = new jxl.write.Label(44, row, applyedWire, cellFormat);
			s1.addCell(lr);

			// 주요기능
			lr = new jxl.write.Label(45, row, primaryFunction, cellFormat);
			s1.addCell(lr);

			// 향후전망
			lr = new jxl.write.Label(46, row, outlook, cellFormat);
			s1.addCell(lr);

			// 금형비 감가(판매)
			lr = new jxl.write.Label(47, row, moldDepreciationTypeSale, cellFormat);
			s1.addCell(lr);

			// 금형비 감가(지급)
			lr = new jxl.write.Label(48, row, moldDepreciationTypePayment, cellFormat);
			s1.addCell(lr);

			// 금형비 감가(감가기간)
			lr = new jxl.write.Label(49, row, moldDepreciationTypePeriod, cellFormat);
			s1.addCell(lr);

			// 금형비 감가(기타사항)
			lr = new jxl.write.Label(50, row, moldDepreciationTypeEtcInfo, cellFormat);
			s1.addCell(lr);

			// 설비비 감가(판매)
			lr = new jxl.write.Label(51, row, equipDepreciationTypeSale, cellFormat);
			s1.addCell(lr);

			// 설비비 감가(지급)
			lr = new jxl.write.Label(52, row, equipDepreciationTypePayment, cellFormat);
			s1.addCell(lr);

			// 설비비 감가(감가기간)
			lr = new jxl.write.Label(53, row, equipDepreciationTypePeriod, cellFormat);
			s1.addCell(lr);

			// 설비비 감가(기타사항)
			lr = new jxl.write.Label(54, row, equipDepreciationTypeEtcInfo, cellFormat);
			s1.addCell(lr);

			// Device 사양
			lr = new jxl.write.Label(55, row, deviceSpecification, cellFormat);
			s1.addCell(lr);

			// 환경규제항목
			lr = new jxl.write.Label(56, row, environmentalRegulationItem, cellFormat);
			s1.addCell(lr);

			// 기타 요청사항
			lr = new jxl.write.Label(57, row, buyerEtcRequirement, cellFormat);
			s1.addCell(lr);

			// 추가 요구사항
			lr = new jxl.write.Label(58, row, salesAdditionalRequirement, cellFormat);
			s1.addCell(lr);

		    }
		}
		timer.setEndPoint();
		// List<Map<String, String>> devRequestOids = (List<Map<String, String>>) devRequestDao.ViewDRList(map);

		/*
	         * for (Map<String, String> dev : devRequestOids) { s_drOid = (String) dev.get("oid"); dr = (KETDevelopmentRequest)
	         * CommonUtil.getObject(s_drOid); // =========결재 요청일,승인일 관련 시작====================// Object obj2 =
	         * CommonUtil.getObject(s_drOid); // ReferenceFactory rf = new ReferenceFactory(); KETWfmApprovalHistory history1 = new
	         * KETWfmApprovalHistory(); KETWfmApprovalHistory history2 = new KETWfmApprovalHistory(); KETWfmApprovalMaster master =
	         * null; Object temp = new Object(); Vector vec = null; WTObject targetObj = KETCommonHelper.service.getPBO((WTObject)
	         * CommonUtil.getObject(s_drOid)); if (targetObj != null) master = (KETWfmApprovalMaster)
	         * WorkflowSearchHelper.manager.getMaster(targetObj); if (master != null) { vec =
	         * WorkflowSearchHelper.manager.getApprovalHistory(master); for (int k = 0; k < vec.size() - 1; k++) { for (int j = k + 1; j
	         * < vec.size(); j++) { history1 = (KETWfmApprovalHistory) vec.get(k); history2 = (KETWfmApprovalHistory) vec.get(k); if
	         * (history1.getSeqNum() < history2.getSeqNum()) { temp = vec.get(k); vec.set(k, vec.get(j)); vec.set(j, temp); } } } } //
	         * =========결재 요청일,승인일 관련 끝====================// if (vec != null) { boolean isApprover = true; activityName = ""; int
	         * iComplet = 0; for (int x = 0; x < vec.size(); x++) { KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
	         * activityName = StringUtil.checkNull(history.getActivityName()); if (activityName.equals("검토")) { iComplet++; } } for (int
	         * x = 0; x < vec.size(); x++) { KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x); activityName =
	         * StringUtil.checkNull(history.getActivityName()); if (activityName.equals("합의")) { iComplet++; } if (x == iComplet &&
	         * isApprover && activityName.equals("검토")) { activityName = "승인"; isApprover = false; } if (history.getCompletedDate() !=
	         * null) completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd"); if (activityName.equals("승인"))
	         * approvalDate = completeDate; if (activityName.equals("요청")) requestDate = completeDate; } } reqNo = dr.getNumber();
	         * divisionCode = StringUtil.checkNull(dr.getDivisionCode()); creatorName =
	         * StringUtil.checkNull(dr.getIterationInfo().getCreator().getFullName()); lifeCycleState =
	         * dr.getLifeCycleState().getDisplay(); insDate = DateUtil.getTimeFormat(dr.getCreateTimestamp(), "yyyy-MM-dd"); deptName =
	         * StringUtil.checkNull(dr.getDeptName()); requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());
	         * lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer()); if (!StringUtil.checkNull(lastUsingBuyer).equals("")) {
	         * StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ","); String ct = ""; String bName = ""; while
	         * (st1.hasMoreTokens()) { ct = st1.nextToken(); NumberCode nCode2 = (NumberCode) CommonUtil.getObject(ct); if (nCode2 ==
	         * null) { bName = bName + "," + ct; } else { bName = bName + "," + nCode2.getName(); } } if (!bName.equals(""))
	         * lastUsingBuyer = bName.substring(1); } lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());
	         * isDRRequest = StringUtil.checkNull(dr.getIsDRRequest()); if (isDRRequest.equals("1")) { drRequestDate =
	         * DateUtil.getTimeFormat(dr.getDrRequestDate(), "yyyy-MM-dd"); } else { drRequestDate = "불필요"; } isProposalSubmit =
	         * StringUtil.checkNull(dr.getIsProposalSubmit()); if (isProposalSubmit.equals("1")) { proposalSubmitDate =
	         * DateUtil.getTimeFormat(dr.getProposalSubmitDate(), "yyyy-MM-dd"); } else { proposalSubmitDate = "불필요"; } isCostSubmit =
	         * StringUtil.checkNull(dr.getIsCostSubmit()); if (isCostSubmit.equals("1")) { costSubmitDate =
	         * DateUtil.getTimeFormat(dr.getCostSubmitDate(), "yyyy-MM-dd"); } else { costSubmitDate = "불필요"; } devReviewTypeEtc =
	         * StringUtil.checkNull(dr.getDevReviewTypeEtc()); devReviewDetailComment =
	         * StringUtil.checkNull(dr.getDevReviewDetailComment()); productSaleFirstYear =
	         * StringUtil.checkNull(dr.getProductSaleFirstYear()); initialSampleSummitDate =
	         * DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(), "yyyy-MM-dd"); eSIRDate =
	         * DateUtil.getTimeFormat(dr.getESIRDate(), "yyyy-MM-dd"); iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(),
	         * "yyyy-MM-dd"); ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(), "yyyy-MM-dd"); String
	         * nCodeOid = StringUtil.checkNull(dr.getProductCategoryCode()); String productTypeCodes =
	         * StringUtil.checkNull(dr.getProductTypeCode()); if (nCodeOid.substring(0, 4).equals("e3ps")) { NumberCode nCode =
	         * (NumberCode) CommonUtil.getObject(nCodeOid); productCategoryCode = nCode.getName(); ArrayList carProductTypeCodeList =
	         * NumberCodeHelper.getChildNumberCode(nCode); productTypeCode = ""; for (int j = 0; j < carProductTypeCodeList.size(); j++)
	         * { String numCodeOid = ((NumberCode)
	         * carProductTypeCodeList.get(j)).getPersistInfo().getObjectIdentifier().getStringValue(); String numCodeName =
	         * ((NumberCode) carProductTypeCodeList.get(j)).getName(); if (productTypeCodes.indexOf(numCodeOid) >= 0) { productTypeCode
	         * = productTypeCode + " / " + numCodeName; } } if (productTypeCode.length() > 3) productTypeCode =
	         * productTypeCode.substring(3); } else { productCategoryCode = ""; productTypeCode = ""; } etcSpecification =
	         * StringUtil.checkNull(dr.getEtcSpecification()); tabSize = StringUtil.checkNull(dr.getTabSize()); materialSubMaterial =
	         * StringUtil.checkNull(dr.getMaterialSubMaterial()); surfaceTreatmentCode =
	         * StringUtil.checkNull(dr.getSurfaceTreatmentCode()); if (surfaceTreatmentCode.equals("0")) surfaceTreatmentCode = ""; if
	         * (!StringUtil.checkNull(surfaceTreatmentCode).equals("")) { NumberCode nCode1 = (NumberCode)
	         * CommonUtil.getObject(surfaceTreatmentCode); String bName = ""; if (nCode1 == null) { bName = surfaceTreatmentCode; } else
	         * { bName = nCode1.getName(); } if (!bName.equals("")) surfaceTreatmentCode = bName; } applyedWire =
	         * StringUtil.checkNull(dr.getApplyedWire()); primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction()); outlook =
	         * StringUtil.checkNull(dr.getOutlook()); moldDepreciationTypeSale = StringUtil.checkNull(dr.getMoldDepreciationTypeSale());
	         * moldDepreciationTypeGeneral = StringUtil.checkNull(dr.getMoldDepreciationTypeGeneral()); moldDepreciationTypePayment =
	         * StringUtil.checkNull(dr.getMoldDepreciationTypePayment()); moldDepreciationTypePeriod =
	         * StringUtil.checkNull(dr.getMoldDepreciationTypePeriod()); moldDepreciationTypeEtcInfo =
	         * StringUtil.checkNull(dr.getMoldDepreciationTypeEtcInfo()); if (!moldDepreciationTypeSale.equals("")) moldDepreciationType
	         * = moldDepreciationType + "/판매"; if (!moldDepreciationTypeGeneral.equals("")) moldDepreciationType = moldDepreciationType
	         * + "/일반"; if (!moldDepreciationTypePayment.equals("")) moldDepreciationType = moldDepreciationType + "/지급"; if
	         * (!moldDepreciationTypePeriod.equals("")) moldDepreciationType = moldDepreciationType + "/감가기간"; if
	         * (!moldDepreciationTypeEtcInfo.equals("")) moldDepreciationType = moldDepreciationType + "/기타:" +
	         * moldDepreciationTypeEtcInfo; if (moldDepreciationType.equals("") || moldDepreciationType.equals("/"))
	         * moldDepreciationType = ""; else moldDepreciationType = moldDepreciationType.substring(1); equipDepreciationTypeSale =
	         * StringUtil.checkNull(dr.getEquipDepreciationTypeSale()); equipDepreciationTypePayment =
	         * StringUtil.checkNull(dr.getEquipDepreciationTypePayment()); equipDepreciationTypePeriod =
	         * StringUtil.checkNull(dr.getEquipDepreciationTypePeriod()); equipDepreciationTypeEtcInfo =
	         * StringUtil.checkNull(dr.getEquipDepreciationTypeEtcInfo()); if (!equipDepreciationTypeSale.equals(""))
	         * equipDepreciationType = equipDepreciationType + "/판매"; if (!equipDepreciationTypePayment.equals(""))
	         * equipDepreciationType = equipDepreciationType + "/지급"; if (!equipDepreciationTypePeriod.equals("")) equipDepreciationType
	         * = equipDepreciationType + "/감가기간"; if (!equipDepreciationTypeEtcInfo.equals("")) equipDepreciationType =
	         * equipDepreciationType + "/기타:" + equipDepreciationTypeEtcInfo; if (equipDepreciationType.equals("") ||
	         * equipDepreciationType.equals("/")) equipDepreciationType = ""; else equipDepreciationType =
	         * equipDepreciationType.substring(1); deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
	         * environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem()); buyerEtcRequirement =
	         * StringUtil.checkNull(dr.getBuyerEtcRequirement()); salesAdditionalRequirement =
	         * StringUtil.checkNull(dr.getSalesAdditionalRequirement()); s_drNo = (String) dev.get("drNo"); s_developmentStep =
	         * StringUtil.checkNull((String) dev.get("developmentStep")); if (s_developmentStep.equals("R")) { s_developmentStep =
	         * "검토의뢰"; } else if (s_developmentStep.equals("D")) { s_developmentStep = "개발의뢰"; } s_projectNo =
	         * StringUtil.checkNull((String) dev.get("projectNo")); if (!s_projectNo.equals("")) { Object obj =
	         * CommonUtil.getObject(s_projectNo); if (obj == null) { s_projectNo = ""; } else { if (obj instanceof E3PSProject) {
	         * E3PSProject project = (E3PSProject) obj; s_projectNo = project.getPjtNo(); } else { s_projectNo = ""; } } } s_repCarType
	         * = StringUtil.checkNull((String) dev.get("repCarType")); s_devProductName = StringUtil.checkNull((String)
	         * dev.get("devProductName")); s_requestBuyer = StringUtil.checkNull((String) dev.get("requestBuyer")); StringTokenizer st =
	         * new StringTokenizer(s_requestBuyer, ","); String ct = ""; String bName = ""; while (st.hasMoreTokens()) { ct =
	         * st.nextToken(); NumberCode nCode1 = null; if (ct.indexOf("NumberCode") != -1) nCode1 = (NumberCode)
	         * CommonUtil.getObject(ct); if (nCode1 == null) { bName = bName + "," + ct; } else { bName = bName + "," +
	         * nCode1.getName(); } } s_requestBuyer = bName.substring(1); s_devReviewTypeCode = StringUtil.checkNull((String)
	         * dev.get("devReviewTypeCode")); if (!StringUtil.checkNull(s_devReviewTypeCode).equals("")) { NumberCode nCode1 = null; if
	         * (s_devReviewTypeCode.indexOf("NumberCode") != -1) nCode1 = (NumberCode) CommonUtil.getObject(s_devReviewTypeCode); bName
	         * = ""; if (nCode1 == null) { bName = s_devReviewTypeCode; } else { bName = nCode1.getName(); } if (!bName.equals(""))
	         * s_devReviewTypeCode = bName; } if (s_devReviewTypeCode.equals("기타")) s_devReviewTypeCode = "기타: " + devReviewTypeEtc;
	         * s_drState = StringUtil.checkNull((String) dev.get("drState")); s_drCreator = StringUtil.checkNull((String)
	         * dev.get("drCreator")); s_drCreateDate = StringUtil.checkNull((String) dev.get("drCreateDate")); s_partName =
	         * StringUtil.checkNull((String) dev.get("partName")); s_carTypeCode = StringUtil.checkNull((String)
	         * dev.get("carTypeCode")); KETRequestPartList pl = null; QueryResult r = PersistenceHelper.manager.navigate(dr,
	         * "RequestPart", KETRequestPartLink.class); String itemCnt = String.valueOf(r.size()); String sdName = ""; double d = 0;
	         * int y1 = 0; int y2 = 0; int y3 = 0; int y4 = 0; int y5 = 0; int y6 = 0; int sum = 0; int usg = 0; if
	         * (r.hasMoreElements()) { pl = (KETRequestPartList) r.nextElement(); String summitDestinationOid =
	         * StringUtil.checkNull(pl.getSummitDestination()); NumberCode nCd = null; if (summitDestinationOid.indexOf("NumberCode") !=
	         * -1) nCd = (NumberCode) CommonUtil.getObject(summitDestinationOid); if (nCd == null) { sdName = summitDestinationOid; }
	         * else { sdName = nCd.getName(); } KETCarYearlyAmount cy = null; QueryResult r1 = PersistenceHelper.manager.navigate(pl,
	         * "CarAmoumt", KETPartCarLink.class); if (r1.hasMoreElements()) { cy = (KETCarYearlyAmount) r1.nextElement(); d =
	         * cy.getYearAmount1() * cy.getApplyedUsage() * cy.getOptionRate() / 100; y1 = y1 + (int) d; d = cy.getYearAmount2() *
	         * cy.getApplyedUsage() * cy.getOptionRate() / 100; y2 = y2 + (int) d; d = cy.getYearAmount3() * cy.getApplyedUsage() *
	         * cy.getOptionRate() / 100; y3 = y3 + (int) d; d = cy.getYearAmount4() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
	         * y4 = y4 + (int) d; d = cy.getYearAmount5() * cy.getApplyedUsage() * cy.getOptionRate() / 100; y5 = y5 + (int) d; d =
	         * cy.getYearAmount6() * cy.getApplyedUsage() * cy.getOptionRate() / 100; y6 = y6 + (int) d; if (usg < cy.getApplyedUsage())
	         * { usg = cy.getApplyedUsage().intValue(); } } sum = y1 + y2 + y3 + y4 + y5 + y6; } String s_y1 = ""; String s_y2 = "";
	         * String s_y3 = ""; String s_y4 = ""; String s_y5 = ""; String s_y6 = ""; String s_sum = ""; String s_usg = ""; s_y1 =
	         * String.valueOf(y1); s_y2 = String.valueOf(y2); s_y3 = String.valueOf(y3); s_y4 = String.valueOf(y4); s_y5 =
	         * String.valueOf(y5); s_y6 = String.valueOf(y6); s_sum = String.valueOf(sum); s_usg = String.valueOf(usg); String s_prjCnt
	         * = ""; String s_prjNo = ""; String s_prjDeptNm = ""; String s_prjPmName = ""; QueryResult qrPrj =
	         * ProjectHelper.getDevRequestProject(dr); Object[] objPrj = null; E3PSProject eep = null; if (qrPrj.hasMoreElements()) {
	         * s_prjCnt = String.valueOf(qrPrj.size() - 1); objPrj = (Object[]) qrPrj.nextElement(); eep = (E3PSProject) objPrj[0];
	         * s_prjNo = StringUtil.checkNull(eep.getPjtNo()); if (qrPrj.size() > 1) { s_prjNo = s_prjNo + "외 " + s_prjCnt + "건"; } if
	         * ("209698255".equals(Long.toString(CommonUtil.getOIDLongValue(dr))) ||
	         * "133893747".equals(Long.toString(CommonUtil.getOIDLongValue(dr)))) { // E3PSProjectData projectData = new
	         * E3PSProjectData(eep); if (eep instanceof ReviewProject) { String rvOid = CommonUtil.getOIDString(eep); ReviewProject rp =
	         * (ReviewProject) CommonUtil.getObject(rvOid); if (rp.getDevDept() != null) { s_prjDeptNm = rp.getDevDept().getName(); } }
	         * else { s_prjDeptNm = "";// projectData.department; } s_prjPmName = "";// projectData.pjtPmName; } else { E3PSProjectData
	         * projectData = new E3PSProjectData(eep); if (eep instanceof ReviewProject) { String rvOid = CommonUtil.getOIDString(eep);
	         * ReviewProject rp = (ReviewProject) CommonUtil.getObject(rvOid); if (rp.getDevDept() != null) { s_prjDeptNm =
	         * rp.getDevDept().getName(); } } else { s_prjDeptNm = projectData.department; } s_prjPmName = projectData.pjtPmName; } } if
	         * (s_developmentStep.equals("검토의뢰")) { s_projectNo = s_prjNo; s_prjNo = ""; } row++; // 의뢰번호 rowCount++; rowCnt =
	         * String.valueOf(rowCount); lr = new jxl.write.Label(0, row, s_drNo, cellFormat); s1.addCell(lr); // 개발프로젝트번호 lr = new
	         * jxl.write.Label(1, row, s_prjNo, cellFormat); s1.addCell(lr); // 검토프로젝트번호 lr = new jxl.write.Label(2, row, s_projectNo,
	         * cellFormat); s1.addCell(lr); // 개발단계 lr = new jxl.write.Label(3, row, s_developmentStep, cellFormat); s1.addCell(lr); //
	         * 개발(검토)유형 lr = new jxl.write.Label(4, row, s_devReviewTypeCode, cellFormat); s1.addCell(lr); // 제품군 lr = new
	         * jxl.write.Label(5, row, productCategoryCode, cellFormat); s1.addCell(lr); // 개발(검토) 제품명 lr = new jxl.write.Label(6, row,
	         * s_devProductName, cellFormat); s1.addCell(lr); // 의뢰부서 lr = new jxl.write.Label(7, row, deptName, cellFormat);
	         * s1.addCell(lr); // 의뢰담당자 lr = new jxl.write.Label(8, row, creatorName, cellFormat); s1.addCell(lr); // 의뢰일자 lr = new
	         * jxl.write.Label(9, row, insDate, cellFormat); s1.addCell(lr); // 상신일자 lr = new jxl.write.Label(10, row, requestDate,
	         * cellFormat); s1.addCell(lr); // 승인일자 lr = new jxl.write.Label(11, row, approvalDate, cellFormat); s1.addCell(lr); // 개발부서
	         * lr = new jxl.write.Label(12, row, s_prjDeptNm, cellFormat); s1.addCell(lr); // 개발담당자 lr = new jxl.write.Label(13, row,
	         * s_prjPmName, cellFormat); s1.addCell(lr); // 결재상태 lr = new jxl.write.Label(14, row, lifeCycleState, cellFormat);
	         * s1.addCell(lr); // 의뢰처 담당자 lr = new jxl.write.Label(15, row, requestBuyerManager, cellFormat); s1.addCell(lr); // 최종사용처
	         * 담당자 lr = new jxl.write.Label(16, row, lastUsingBuyerManager, cellFormat); s1.addCell(lr); // 상세내용 lr = new
	         * jxl.write.Label(17, row, devReviewDetailComment, cellFormat); s1.addCell(lr); // 품목수 lr = new jxl.write.Label(18, row,
	         * itemCnt, cellFormat); s1.addCell(lr); // 의뢰처 lr = new jxl.write.Label(19, row, s_requestBuyer, cellFormat);
	         * s1.addCell(lr); // 납입처 lr = new jxl.write.Label(20, row, sdName, cellFormat); s1.addCell(lr); // 최종사용처 lr = new
	         * jxl.write.Label(21, row, lastUsingBuyer, cellFormat); s1.addCell(lr); // 대표차종 lr = new jxl.write.Label(22, row,
	         * s_repCarType, cellFormat); s1.addCell(lr); // US lr = new jxl.write.Label(23, row, s_usg, cellFormat); s1.addCell(lr); //
	         * 예상수량(1년차)[천개] lr = new jxl.write.Label(24, row, StringUtil.getDouble(s_y1, "", "###,###"), cellFormat); s1.addCell(lr);
	         * // 예상수량(2년차)[천개] lr = new jxl.write.Label(25, row, StringUtil.getDouble(s_y2, "", "###,###"), cellFormat);
	         * s1.addCell(lr); // 예상수량(3년차)[천개] lr = new jxl.write.Label(26, row, StringUtil.getDouble(s_y3, "", "###,###"),
	         * cellFormat); s1.addCell(lr); // 예상수량(4년차)[천개] lr = new jxl.write.Label(27, row, StringUtil.getDouble(s_y4, "",
	         * "###,###"), cellFormat); s1.addCell(lr); // 예상수량(5년차)[천개] lr = new jxl.write.Label(28, row, StringUtil.getDouble(s_y5,
	         * "", "###,###"), cellFormat); s1.addCell(lr); // 예상수량(6년차~)[천개] lr = new jxl.write.Label(29, row,
	         * StringUtil.getDouble(s_y6, "", "###,###"), cellFormat); s1.addCell(lr); // 수량합계[천개] lr = new jxl.write.Label(30, row,
	         * StringUtil.getDouble(s_sum, "", "###,###"), cellFormat); s1.addCell(lr); // 양산 1차년도 lr = new jxl.write.Label(31, row,
	         * productSaleFirstYear, cellFormat); s1.addCell(lr); // DR0 요구일정 lr = new jxl.write.Label(32, row, drRequestDate,
	         * cellFormat); s1.addCell(lr); // 제안서 체출일정 lr = new jxl.write.Label(33, row, proposalSubmitDate, cellFormat);
	         * s1.addCell(lr); // 개발원가 제출일정 lr = new jxl.write.Label(34, row, costSubmitDate, cellFormat); s1.addCell(lr); // 초도품 제출일정
	         * lr = new jxl.write.Label(35, row, initialSampleSummitDate, cellFormat); s1.addCell(lr); // ESIR일정 lr = new
	         * jxl.write.Label(36, row, eSIRDate, cellFormat); s1.addCell(lr); // ISIR일정 lr = new jxl.write.Label(37, row, iSIRDate,
	         * cellFormat); s1.addCell(lr); // KET 양산일정 lr = new jxl.write.Label(38, row, ketMassProductionDate, cellFormat);
	         * s1.addCell(lr); // 제품Type lr = new jxl.write.Label(39, row, productTypeCode, cellFormat); s1.addCell(lr); // 기타 내용 lr =
	         * new jxl.write.Label(40, row, etcSpecification, cellFormat); s1.addCell(lr); // TabSize lr = new jxl.write.Label(41, row,
	         * tabSize, cellFormat); s1.addCell(lr); // 원자재/부자재 lr = new jxl.write.Label(42, row, materialSubMaterial, cellFormat);
	         * s1.addCell(lr); // 표면처리 lr = new jxl.write.Label(43, row, surfaceTreatmentCode, cellFormat); s1.addCell(lr); // 적용전선 lr =
	         * new jxl.write.Label(44, row, applyedWire, cellFormat); s1.addCell(lr); // 주요기능 lr = new jxl.write.Label(45, row,
	         * primaryFunction, cellFormat); s1.addCell(lr); // 향후전망 lr = new jxl.write.Label(46, row, outlook, cellFormat);
	         * s1.addCell(lr); // 금형비 감가(판매) lr = new jxl.write.Label(47, row, moldDepreciationTypeSale, cellFormat); s1.addCell(lr); //
	         * 금형비 감가(지급) lr = new jxl.write.Label(48, row, moldDepreciationTypePayment, cellFormat); s1.addCell(lr); // 금형비 감가(감가기간) lr
	         * = new jxl.write.Label(49, row, moldDepreciationTypePeriod, cellFormat); s1.addCell(lr); // 금형비 감가(기타사항) lr = new
	         * jxl.write.Label(50, row, moldDepreciationTypeEtcInfo, cellFormat); s1.addCell(lr); // 설비비 감가(판매) lr = new
	         * jxl.write.Label(51, row, equipDepreciationTypeSale, cellFormat); s1.addCell(lr); // 설비비 감가(지급) lr = new
	         * jxl.write.Label(52, row, equipDepreciationTypePayment, cellFormat); s1.addCell(lr); // 설비비 감가(감가기간) lr = new
	         * jxl.write.Label(53, row, equipDepreciationTypePeriod, cellFormat); s1.addCell(lr); // 설비비 감가(기타사항) lr = new
	         * jxl.write.Label(54, row, equipDepreciationTypeEtcInfo, cellFormat); s1.addCell(lr); // Device 사양 lr = new
	         * jxl.write.Label(55, row, deviceSpecification, cellFormat); s1.addCell(lr); // 환경규제항목 lr = new jxl.write.Label(56, row,
	         * environmentalRegulationItem, cellFormat); s1.addCell(lr); // 기타 요청사항 lr = new jxl.write.Label(57, row,
	         * buyerEtcRequirement, cellFormat); s1.addCell(lr); // 추가 요구사항 lr = new jxl.write.Label(58, row,
	         * salesAdditionalRequirement, cellFormat); s1.addCell(lr); } // for (Map<String, String> dev : devRequestOids) {
	         */
		timer.display();
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
		res.setHeader("Set-Cookie", "fileDownload=true; path=/");
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
}
