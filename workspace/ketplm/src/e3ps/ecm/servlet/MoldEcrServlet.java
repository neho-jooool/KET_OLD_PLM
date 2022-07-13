package e3ps.ecm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;

import wt.fc.ReferenceFactory;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcrBeans;
import e3ps.ecm.dao.MoldChangeRequestDao;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldECRIssueLinkVO;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

public class MoldEcrServlet extends CommonServlet {

    Vector                            files               = new Vector();
    private String                    cmd                 = null;        // command
    private String                    oid                 = null;        // ECO OID
    private String                    ecrId               = null;        // ECO ID
    private String                    ecrName             = null;        // 제목
    private String                    devYn               = null;        // 개발/양산구분
    private String                    divisionFlag        = null;        // 사업부구분
    private String                    projectOid          = null;        // 관련 프로젝트
    private String                    requestType         = null;        // 의뢰구분
    private String                    otherReqType        = null;        // 의뢰구분 기타사유
    private String                    changeType          = null;        // 설계변경 유형
    private String                    otherChangeDesc     = null;        // 설계변경유형 기타사유
    private String                    completeReqDate     = null;        // 완료요청일
    private String                    prodVendor          = null;        // 생산처
    private String                    vendorFlag          = null;        // 생산처구분
    private String                    processType         = null;        // 처리구분
    private String                    otherProcessDesc    = null;        // 처리구분설명
    private String                    ecrDesc             = null;        // 설명
    private String                    expectEffect        = null;        // 기대효과

    private String                    contentType         = null;        // ContentType

    private String[]                  relIssueOid         = null;
    private String[]                  relIssueLinkOid     = null;

    private String[]                  relPartOid          = null;
    private String[]                  relPartLinkOid      = null;
    private String[]                  changeReqComment    = null;        // 부품요청내용

    private String                    deleteFileList      = null;        // 첨부파일 삭제목록
    private String                    deleteRelIssueList  = null;        // 관련이슈 삭제목록
    private String                    deleteRelPartList   = null;        // 관련부품 삭제목록

    private String                    page                = null;
    private String                    totalCount          = null;
    private String                    sortColumn          = null;
    private String                    param               = null;
    private String                    perPage             = null;
    private String                    autoSearch          = null;
    private String                    srchpartOid         = null;
    private String                    srchpartNo          = null;
    private String                    srchpartName        = null;
    private String                    srchprojectOid      = null;
    private String                    srchprojectNo       = null;
    private String                    srchprojectName     = null;
    private String                    srchorgName         = null;
    private String                    srchcreatorOid      = null;
    private String                    srchcreatorName     = null;
    private String                    srchecrId           = null;
    private String                    srchdivisionFlag    = null;
    private String                    srchdevYn           = null;
    private String                    srchsancStateFlag   = null;
    private String                    srchecrName         = null;
    private String                    srchprodMoldCls     = null;
    private String                    srchcreateStartDate = null;
    private String                    srchcreateEndDate   = null;

    private String                    prePage             = null;

    // 금형 ECR 확장팩
    private Hashtable<String, String> reqData             = null;

    /*
     * (non-Javadoc)
     * 
     * @see e3ps.common.web.CommonServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processParameter(request, response);
	if (cmd.equals("view") || cmd.equals("updateview") || cmd.equals("popupview")) {
	    view(request, response);
	}
	else if (cmd.equals("create")) {
	    create(request, response);
	}
	else if (cmd.equals("update")) {
	    update(request, response);
	}
	else if (cmd.equals("cancel")) {
	    cancel(request, response);
	}
	else if (cmd.equals("delete")) {
	    delete(request, response);
	}
	else if (cmd.equals("goMoldEco")) {// 금형ECO작성
	    goMoldEco(request, response);
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
	contentType = request.getContentType();

	FormUploader formUploader = FormUploader.newFormUploader(request, response, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(formUploader.getFormParameters());

	cmd = paramMap.getString("cmd");
	oid = paramMap.getString("oid");
	ecrId = paramMap.getString("ecrId");
	ecrName = paramMap.getString("ecrName");
	devYn = paramMap.getString("devYn");
	divisionFlag = paramMap.getString("divisionFlag");
	projectOid = paramMap.getString("projectOid");
	requestType = paramMap.getString("requestType");
	otherReqType = paramMap.getString("otherReqType");
	changeType = paramMap.getString("changeType");
	otherChangeDesc = paramMap.getString("otherChangeDesc");
	completeReqDate = paramMap.getString("completeReqDate");
	prodVendor = paramMap.getString("prodVendor");
	vendorFlag = paramMap.getString("vendorFlag");// 생산처구분
	processType = paramMap.getString("processType");
	otherProcessDesc = paramMap.getString("otherProcessDesc");
	ecrDesc = paramMap.getString("ecrDesc");
	expectEffect = paramMap.getString("expectEffect");

	deleteFileList = paramMap.getString("deleteFileList");// 첨부파일 삭제목록
	deleteRelIssueList = paramMap.getString("deleteRelIssueList");// 연계이슈 삭제목록
	deleteRelPartList = paramMap.getString("deleteRelPartList");// 관련부품 삭제목록

	relPartOid = paramMap.getStringArray("relPartOid");// 관련부품oid 추가목록
	relPartLinkOid = paramMap.getStringArray("relPartLinkOid");// 관련부품ECOoid 추가목록
	changeReqComment = paramMap.getStringArray("changeReqComment");// 부품요청내용

	relIssueOid = paramMap.getStringArray("relIssueOid");// 관련Issueoid 추가목록
	relIssueLinkOid = paramMap.getStringArray("relIssueLinkOid");// 관련IssueECOoid 추가목록

	page = paramMap.getString("page");
	totalCount = paramMap.getString("totalCount");
	sortColumn = paramMap.getString("sortColumn");
	param = paramMap.getString("param");
	perPage = paramMap.getString("perPage");
	autoSearch = paramMap.getString("autoSearch");
	srchpartOid = paramMap.getString("srchpartOid");
	srchpartNo = paramMap.getString("srchpartNo");
	srchpartName = paramMap.getString("srchpartName");
	srchprojectOid = paramMap.getString("srchprojectOid");
	srchprojectNo = paramMap.getString("srchprojectNo");
	srchprojectName = paramMap.getString("srchprojectName");
	srchorgName = paramMap.getString("srchorgName");
	srchcreatorOid = paramMap.getString("srchcreatorOid");
	srchcreatorName = paramMap.getString("srchcreatorName");
	srchecrId = paramMap.getString("srchecrId");
	srchdivisionFlag = paramMap.getString("srchdivisionFlag");
	srchdevYn = paramMap.getString("srchdevYn");
	srchsancStateFlag = paramMap.getString("srchsancStateFlag");
	srchecrName = paramMap.getString("srchecrName");
	srchprodMoldCls = paramMap.getString("srchprodMoldCls");
	srchcreateStartDate = paramMap.getString("srchcreateStartDate");
	srchcreateEndDate = paramMap.getString("srchcreateEndDate");
	completeReqDate = EcmUtil.getServerDateFormat(completeReqDate);
	files = formUploader.getFiles();

	// 금형 ECR 확장팩
	reqData = new Hashtable<String, String>();
	reqData.put("reviewRequestDate", paramMap.getString("reviewRequestDate"));
	reqData.put("subjectOid", paramMap.getString("subjectOid"));
	reqData.put("subjectCode", paramMap.getString("subjectCode"));
	reqData.put("chargeOid", paramMap.getString("chargeOid"));
	reqData.put("chargeName", paramMap.getString("chargeName"));

	reqData.put("webEditor", paramMap.getString("webEditor"));
	reqData.put("webEditorText", paramMap.getString("webEditorText"));
	reqData.put("webEditor1", paramMap.getString("webEditor1"));
	reqData.put("webEditorText1", paramMap.getString("webEditorText1"));

    }

    /**
     * 
     * 함수명 : view 설명 : 금형 ECR을 상세 조회한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
	MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
	try {

	    KETMoldChangeRequest ketMoldChangeRequest = (KETMoldChangeRequest) KETObjectUtil.getObject(oid);// ECR객체조회

	    if (ketMoldChangeRequest != null) {
	    }

	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);// ECR객체 VO세팅
	    ketMoldChangeRequestVO.setTotalCount(1);// 조회여부 VO세팅
	    ketMoldChangeRequestVO.setOid(CommonUtil.getOIDLongValue(oid) + "");// ECR oid VO세팅
	    // 금형ECO 상세조회
	    ketMoldChangeRequestVO = moldChangeRequestDao.searchEcrDetail(ketMoldChangeRequestVO);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	request.setAttribute("ketMoldChangeRequestVO", ketMoldChangeRequestVO);

	// 금형 ECR에서, '주간보고', '회의록' 조회일 경우
	String ecrOid = null;
	ProdEcrBeans beans = null;
	RequestDispatcher rd = null;

	String tabName = StringUtils.stripToEmpty(request.getParameter("tabName"));
	if (tabName.equals("ecrCompetent") || tabName.equals("ecrMeeting")) {

	    ecrOid = request.getParameter("oid");
	    request.setAttribute("prePage", prePage);
	    beans = new ProdEcrBeans();

	    ReferenceFactory rf = new ReferenceFactory();
	    KETMoldChangeRequest prodEcr = null;
	    try {
		prodEcr = (KETMoldChangeRequest) rf.getReference(ecrOid).getObject();
	    } catch (WTRuntimeException e1) {
		Kogger.error(getClass(), e1);
	    } catch (WTException e1) {
		Kogger.error(getClass(), e1);
	    }
	    request.setAttribute("prodEcr", prodEcr);

	}

	if (cmd.equals("view")) {

	    /*
	     * gotoResult(request, response, "/jsp/ecm/ViewMoldEcrForm.jsp?prePage=" + prePage);
	     */

	    // 주관부서
	    if (tabName.equals("ecrCompetent")) {

		KETCompetentDepartment competent = null;
		;
		try {
		    competent = beans.getKETCompetentDepartment(ecrOid);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		request.setAttribute("competent", competent);

		rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ViewEcrCompetent.jsp");

	    }
	    // 회의록
	    else if (tabName.equals("ecrMeeting")) {

		KETMeetingMinutes meeting = null;
		try {
		    meeting = beans.getKETMeetingMinutes(ecrOid);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
		request.setAttribute("meeting", meeting);

		rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ViewEcrMeeting.jsp");

	    }
	    // 기본사항
	    else {
		rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewMoldEcrForm.jsp?prePage=" + prePage);

	    }

	}
	else if (cmd.equals("updateview")) {

	    /*
	     * gotoResult(request, response, "/jsp/ecm/CreateMoldEcrForm.jsp");
	     */

	    rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateMoldEcrForm.jsp");

	}
	else if (cmd.equals("popupview")) {

	    /*
	     * gotoResult(request, response, detailInfoUrl);
	     */

	    String detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + oid; // 금형
	    URLCodec urlCodec = new URLCodec();
	    try {
		detailInfoUrl = "/jsp/ecm/reform/ViewEcrForm.jsp?redirectURL=" + urlCodec.encode(detailInfoUrl);
	    } catch (EncoderException e) {
		throw new ServletException(e);
	    }

	    rd = getServletContext().getRequestDispatcher(detailInfoUrl);

	}

	rd.forward(request, response);

    }

    /**
     * 
     * 함수명 : create 설명 : 금형 ECR을 신규 저장한다. ECR ID를 채번한다. 관련 ISSUE VO List를 생성한다. 관련부품 VO List를 생성한다. 첨부파일 VO List를 생성한다. 금형 ECR을 신규 저장한다. 저장이
     * 성공한 경우 금형 ECR 상세조회 화면으로 이동한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeRequest ketMoldChangeRequest = null;
	KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
	try {
	    ketMoldChangeRequest = KETMoldChangeRequest.newKETMoldChangeRequest();

	    MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
	    ecrId = moldChangeRequestDao.getEcrId();
	    ketMoldChangeRequest.setEcrId(ecrId);
	    ketMoldChangeRequest.setEcrName(ecrName);
	    ketMoldChangeRequest.setDevYn(devYn);// 개발/양산구분
	    ketMoldChangeRequest.setDivisionFlag(divisionFlag);// 사업부구분
	    ketMoldChangeRequest.setProjectOid(projectOid);// 관련 프로젝트
	    ketMoldChangeRequest.setRequestType(requestType);
	    ketMoldChangeRequest.setOtherReqType(otherReqType);
	    ketMoldChangeRequest.setChangeType(changeType);
	    ketMoldChangeRequest.setOtherChangeDesc(otherChangeDesc);
	    ketMoldChangeRequest.setCompleteReqDate(completeReqDate);
	    ketMoldChangeRequest.setProdVendor(prodVendor);
	    ketMoldChangeRequest.setVendorFlag(vendorFlag);
	    ketMoldChangeRequest.setProcessType(processType);
	    ketMoldChangeRequest.setOtherProcessDesc(otherProcessDesc);
	    ketMoldChangeRequest.setEcrDesc(ecrDesc);
	    ketMoldChangeRequest.setExpectEffect(expectEffect);

	    // 작성부서명 조회
	    PeopleData peopleData = new PeopleData();
	    ketMoldChangeRequest.setDeptName(peopleData.departmentName);

	    // [START] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현
	    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
	    ketMoldChangeRequest.setName(ketMoldChangeRequest.getEcrName());
	    // [END] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현

	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);

	    ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = createKETMoldECRPartLink();
	    if (ketMoldECOPartLinkVOList != null) {
		ketMoldChangeRequestVO.setKetMoldECOPartLinkVOList(ketMoldECOPartLinkVOList);// 관련부품
	    }
	    ketMoldChangeRequestVO.setDeleteRelPartList(deleteRelPartList);// 삭제대상 관련부품oid

	    ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = createKETMoldECRIssueLink();
	    if (ketMoldECRIssueLinkVOList != null) {
		ketMoldChangeRequestVO.setKetMoldECRIssueLinkVOList(ketMoldECRIssueLinkVOList);// 관련Issue
	    }
	    ketMoldChangeRequestVO.setDeleteRelIssueList(deleteRelIssueList);// 삭제대상 관련Issueoid

	    ketMoldChangeRequestVO.setFiles(files);// 첨부파일
	    ketMoldChangeRequestVO.setDeleteFileList(deleteFileList);// 삭제대상 첨부파일oid

	    // 금형 ECR 확장팩
	    ketMoldChangeRequestVO.setReqData(reqData);

	    ketMoldChangeRequestVO = KETEcmHelper.service.createMoldEcrInfo(ketMoldChangeRequestVO);

	    /*
	     * alertNgo(request, response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcrServlet", "view",
	     * CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()), "Create");
	     */

	    try {

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter out = response.getWriter();
		StringBuffer rtn_msg = new StringBuffer("");

		String url = "/plm/jsp/ecm/reform/ViewEcrForm.jsp";
		rtn_msg.append("\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>");

		String detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()); //
		URLCodec urlCodec = new URLCodec();
		detailInfoUrl = urlCodec.encode(detailInfoUrl);
		rtn_msg.append("\n   <input type='hidden' name='redirectURL' value='" + detailInfoUrl + "'>");

		rtn_msg.append("\n </form>");
		rtn_msg.append("\n <script language=\"javascript\">");

		String msg = "정상적으로 처리되었습니다.";
		rtn_msg.append("\n   alert(\"" + msg + "\");");

		// rtn_msg.append("\n   parent.hideProcessing();");
		rtn_msg.append("\n   document.frmProc.submit();");
		rtn_msg.append("\n </script>");

		out.println(rtn_msg);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);

	    }

	} catch (Exception e1) {
	    alert(response, "저장시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	}
    }// end-of-create

    /**
     * 
     * 함수명 : createKETMoldECRPartLink 설명 : 관련부품 VO List를 생성한다.
     * 
     * @return ArrayList<KETMoldECOPartLinkVO> : 관련부품 VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private ArrayList<KETMoldECOPartLinkVO> createKETMoldECRPartLink() throws ServletException, IOException {
	ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = new ArrayList<KETMoldECOPartLinkVO>();// 금형ECO관련부품 List
	KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;

	if (relPartOid != null && relPartOid.length > 0) {
	    for (int i = 0; i < relPartOid.length; i++) {
		ketMoldECOPartLinkVO = new KETMoldECOPartLinkVO();
		ketMoldECOPartLinkVO.setRelPartOid(relPartOid[i]);
		if (relPartLinkOid != null && relPartLinkOid.length > 0) {
		    ketMoldECOPartLinkVO.setRelPartLinkOid(relPartLinkOid[i]);
		}
		else {
		    ketMoldECOPartLinkVO.setRelPartLinkOid("");
		}
		if (changeReqComment != null && changeReqComment.length > 0) {
		    ketMoldECOPartLinkVO.setChangeReqComment(changeReqComment[i]);
		}
		else {
		    ketMoldECOPartLinkVO.setChangeReqComment("");
		}
		ketMoldECOPartLinkVOList.add(ketMoldECOPartLinkVO);
	    }
	}
	return ketMoldECOPartLinkVOList;
    }// end-of-createKETMoldECRPartLink

    /**
     * 
     * 함수명 : createKETMoldECRIssueLink 설명 : 관련 Issue VO를 생성한다.
     * 
     * @return ArrayList<KETMoldECRIssueLinkVO> : 관련 Issue Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private ArrayList<KETMoldECRIssueLinkVO> createKETMoldECRIssueLink() throws ServletException, IOException {
	ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = new ArrayList<KETMoldECRIssueLinkVO>();// 금형ECR관련Issue List
	KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;

	if (relIssueOid != null && relIssueOid.length > 0) {
	    for (int i = 0; i < relIssueOid.length; i++) {
		ketMoldECRIssueLinkVO = new KETMoldECRIssueLinkVO();
		ketMoldECRIssueLinkVO.setRelIssueOid(relIssueOid[i]);
		if (relIssueLinkOid != null && relIssueLinkOid.length > 0) {
		    ketMoldECRIssueLinkVO.setRelIssueLinkOid(relIssueLinkOid[i]);
		}
		else {
		    ketMoldECRIssueLinkVO.setRelIssueLinkOid("");
		}
		ketMoldECRIssueLinkVOList.add(ketMoldECRIssueLinkVO);
	    }
	}
	return ketMoldECRIssueLinkVOList;
    }// end-of-createKETMoldECRIssueLink

    protected void alertNgo(HttpServletRequest request, HttpServletResponse res, String msg, String url, String cmd, String oid, String prePage) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>" + "\n <input type='hidden' name='cmd' value='" + cmd + "'>"
		    + "\n <input type='hidden' name='oid' value='" + oid + "'>" + "\n <input type='hidden' name='page' value='" + page + "'>" + "\n <input type='hidden' name='totalCount' value='"
		    + totalCount + "'>" + "\n <input type='hidden' name='sortColumn' value='" + sortColumn + "'>" + "\n <input type='hidden' name='param' value='" + param + "'>"
		    + "\n <input type='hidden' name='perPage' value='" + perPage + "'>" + "\n <input type='hidden' name='autoSearch' value='" + autoSearch + "'>"
		    + "\n <input type='hidden' name='partOid' value='" + srchpartOid + "'>" + "\n <input type='hidden' name='partNo' value='" + srchpartNo + "'>"
		    + "\n <input type='hidden' name='partName' value='" + srchpartName + "'>" + "\n <input type='hidden' name='projectOid' value='" + srchprojectOid + "'>"
		    + "\n <input type='hidden' name='projectNo' value='" + srchprojectNo + "'>" + "\n <input type='hidden' name='projectName' value='" + srchprojectName + "'>"
		    + "\n <input type='hidden' name='orgName' value='" + srchorgName + "'>" + "\n <input type='hidden' name='creatorOid' value='" + srchcreatorOid + "'>"
		    + "\n <input type='hidden' name='creatorName' value='" + srchcreatorName + "'>" + "\n <input type='hidden' name='ecrId' value='" + srchecrId + "'>"
		    + "\n <input type='hidden' name='divisionFlag' value='" + srchdivisionFlag + "'>" + "\n <input type='hidden' name='devYn' value='" + srchdevYn + "'>"
		    + "\n <input type='hidden' name='sancStateFlag' value='" + srchsancStateFlag + "'>" + "\n <input type='hidden' name='ecrName' value='" + srchecrName + "'>"
		    + "\n <input type='hidden' name='prodMoldCls' value='" + srchprodMoldCls + "'>" + "\n <input type='hidden' name='createStartDate' value='" + srchcreateStartDate + "'>"
		    + "\n <input type='hidden' name='createEndDate' value='" + srchcreateEndDate + "'>" + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n </form>"
		    + "\n <script language=\"javascript\">" + "\n   parent.hideProcessing();";
	    if (!"cancel".equals(this.cmd)) {
		rtn_msg += "\n   alert(\"" + msg + "\");";
	    }
	    rtn_msg += "\n   document.frmProc.submit();";
	    rtn_msg += "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * <pre>
     * javascript alert 메시지를 보여준다.
     * </pre>
     * 
     * @param msg
     *            alert으로 보여줄 메시지
     * @param res
     *            PrintWriter 얻기위해
     */
    protected void alert(HttpServletResponse res, String msg) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <script language=\"javascript\">" + "\n   parent.hideProcessing();" + "\n   parent.enabledAllBtn();" + "\n   alert(\"" + msg + "\");" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 
     * 함수명 : update 설명 : 금형 ECR을 변경 저장한다. ECR ID를 채번한다. 관련 ISSUE VO List를 생성한다. 관련부품 VO List를 생성한다. 첨부파일 VO List를 생성한다. 금형 ECR을 변경 저장한다. 저장이
     * 성공한 경우 금형 ECR 상세조회 화면으로 이동한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    @SuppressWarnings({ "rawtypes" })
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeRequest ketMoldChangeRequest = null;
	KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
	try {
	    ketMoldChangeRequest = (KETMoldChangeRequest) CommonUtil.getObject(oid);
	    ketMoldChangeRequest.setEcrName(ecrName);
	    ketMoldChangeRequest.setDevYn(devYn);// 개발/양산구분
	    ketMoldChangeRequest.setDivisionFlag(divisionFlag);// 사업부구분
	    ketMoldChangeRequest.setProjectOid(projectOid);// 관련 프로젝트
	    ketMoldChangeRequest.setRequestType(requestType);
	    ketMoldChangeRequest.setOtherReqType(otherReqType);
	    ketMoldChangeRequest.setChangeType(changeType);
	    ketMoldChangeRequest.setOtherChangeDesc(otherChangeDesc);
	    ketMoldChangeRequest.setCompleteReqDate(completeReqDate);
	    ketMoldChangeRequest.setProdVendor(prodVendor);
	    ketMoldChangeRequest.setVendorFlag(vendorFlag);
	    ketMoldChangeRequest.setProcessType(processType);
	    ketMoldChangeRequest.setOtherProcessDesc(otherProcessDesc);
	    ketMoldChangeRequest.setEcrDesc(ecrDesc);
	    ketMoldChangeRequest.setExpectEffect(expectEffect);
	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);

	    ketMoldChangeRequestVO.setFiles(files);
	    ketMoldChangeRequestVO.setDeleteFileList(deleteFileList);
	    ketMoldChangeRequestVO.setKetMoldECOPartLinkVOList(createKETMoldECRPartLink());// 관련부품
	    ketMoldChangeRequestVO.setDeleteRelPartList(deleteRelPartList);// 삭제대상 관련부품oid

	    ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = createKETMoldECRIssueLink();
	    if (ketMoldECRIssueLinkVOList != null) {
		ketMoldChangeRequestVO.setKetMoldECRIssueLinkVOList(ketMoldECRIssueLinkVOList);// 관련Issue
	    }
	    ketMoldChangeRequestVO.setDeleteRelIssueList(deleteRelIssueList);// 삭제대상 관련Issueoid

	    // 금형 ECR 확장팩
	    ketMoldChangeRequestVO.setReqData(reqData);

	    ketMoldChangeRequestVO = KETEcmHelper.service.updateMoldEcrInfo(ketMoldChangeRequestVO);

	    /*
	     * alertNgo(request, response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcrServlet", "view",
	     * CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()), "Update");
	     */

	    String url = "/plm/servlet/e3ps/MoldEcrServlet";
	    String oid = CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest());
	    String msg = "정상적으로 처리되었습니다.";

	    try {
		response.setContentType("text/html;charset=KSC5601");
		PrintWriter out = response.getWriter();
		/*
		String rtn_msg = "";
		rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>" + "\n <input type='hidden' name='cmd' value='view'>"
		        + "\n <input type='hidden' name='oid' value='" + oid + "'>" + "\n <input type='hidden' name='page' value='" + page + "'>" + "\n <input type='hidden' name='totalCount' value='"
		        + totalCount + "'>" + "\n <input type='hidden' name='sortColumn' value='" + sortColumn + "'>" + "\n <input type='hidden' name='param' value='" + param + "'>"
		        + "\n <input type='hidden' name='perPage' value='" + perPage + "'>" + "\n <input type='hidden' name='autoSearch' value='" + autoSearch + "'>"
		        + "\n <input type='hidden' name='partOid' value='" + srchpartOid + "'>" + "\n <input type='hidden' name='partNo' value='" + srchpartNo + "'>"
		        + "\n <input type='hidden' name='partName' value='" + srchpartName + "'>" + "\n <input type='hidden' name='projectOid' value='" + srchprojectOid + "'>"
		        + "\n <input type='hidden' name='projectNo' value='" + srchprojectNo + "'>" + "\n <input type='hidden' name='projectName' value='" + srchprojectName + "'>"
		        + "\n <input type='hidden' name='orgName' value='" + srchorgName + "'>" + "\n <input type='hidden' name='creatorOid' value='" + srchcreatorOid + "'>"
		        + "\n <input type='hidden' name='creatorName' value='" + srchcreatorName + "'>" + "\n <input type='hidden' name='ecrId' value='" + srchecrId + "'>"
		        + "\n <input type='hidden' name='divisionFlag' value='" + srchdivisionFlag + "'>" + "\n <input type='hidden' name='devYn' value='" + srchdevYn + "'>"
		        + "\n <input type='hidden' name='sancStateFlag' value='" + srchsancStateFlag + "'>" + "\n <input type='hidden' name='ecrName' value='" + srchecrName + "'>"
		        + "\n <input type='hidden' name='prodMoldCls' value='" + srchprodMoldCls + "'>" + "\n <input type='hidden' name='createStartDate' value='" + srchcreateStartDate + "'>"
		        + "\n <input type='hidden' name='createEndDate' value='" + srchcreateEndDate + "'>" + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n </form>"
		        + "\n <script language=\"javascript\">" + "\n   parent.hideProcessing();";
		if (!"cancel".equals(this.cmd)) {
		    rtn_msg += "\n   alert(\"" + msg + "\");";
		}
		rtn_msg += "\n   document.frmProc.submit();";
		rtn_msg += "\n </script>";
		*/

		StringBuffer rtn_msg = new StringBuffer();
		rtn_msg.append("\n <script language=\"javascript\">");

		rtn_msg.append("\n   alert(\"" + msg + "\");");

		// rtn_msg.append("\n   parent.hideProcessing();");
		rtn_msg.append("\n   parent.lfn_reloadWhole();");
		rtn_msg.append("\n </script>");

		out.println(rtn_msg);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	} catch (Exception e1) {
	    alert(response, "저장시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	}
    }// end-of-update

    /**
     * 
     * 함수명 : cancel 설명 : 금형 ECR 등록/수정 화면내 취소 버튼 클릭시 호출된다. 금형 ECR 상세조회 화면으로 분기한다.(금형ECR등록/수정 -> 금형 ECR 상세조회)
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void cancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	alertNgo(request, response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcrServlet", "view", oid, "Cancel");
    }// end-of-cancel

    /**
     * 
     * 함수명 : delete 설명 : 금형 ECR을 삭제한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeRequest ketMoldChangeRequest = null;
	KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
	try {
	    ketMoldChangeRequest = (KETMoldChangeRequest) CommonUtil.getObject(oid);
	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);
	    KETEcmHelper.service.deleteMoldEcrInfo(ketMoldChangeRequestVO);

	    /*
	     * alertNgo(request, response, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchEcrForm.jsp", "search",
	     * CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()), "Delete");
	     */

	    try {

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter out = response.getWriter();
		StringBuffer rtn_msg = new StringBuffer("");

		rtn_msg.append("\n <script language=\"javascript\">");

		String msg = "정상적으로 처리되었습니다.";
		rtn_msg.append("\n   alert(\"" + msg + "\");");

		// rtn_msg.append("\n   parent.hideProcessing();");
		rtn_msg.append("\n   try {");

		rtn_msg.append("\n   	parent.parent.opener.feedbackAfterPopup('doReSearching');");
		rtn_msg.append("\n   	parent.parent.window.close();");

		rtn_msg.append("\n   } catch(e) { alert(e); }");
		rtn_msg.append("\n </script>");

		out.println(rtn_msg);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);

	    }

	} catch (Exception e1) {
	    alert(response, "삭제시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	}
    }// end-of-delete

    /**
     * 
     * 함수명 : goMoldEco 설명 : 금형 ECO 등록/수정 화면으로 이동한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void goMoldEco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
	KETMoldChangeRequest ketMoldChangeRequest = (KETMoldChangeRequest) CommonUtil.getObject(oid);// ECR객체조회
	ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);// ECR객체 VO세팅
	ketMoldChangeRequestVO.setTotalCount(1);// 조회여부 VO세팅
	ketMoldChangeRequestVO.setOid(CommonUtil.getOIDLongValue(oid) + "");// ECR oid VO세팅

	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	KETMoldChangeOrder ketMoldChangeOrder = new KETMoldChangeOrder();
	MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
	try {
	    // 금형ECR 상세조회
	    ketMoldChangeRequestVO = moldChangeRequestDao.searchEcrDetail(ketMoldChangeRequestVO);

	    ketMoldChangeOrder.setEcoName(ketMoldChangeRequest.getEcrName());
	    ketMoldChangeOrder.setDevYn(ketMoldChangeRequest.getDevYn());// 개발/양산구분
	    ketMoldChangeOrder.setDivisionFlag(ketMoldChangeRequest.getDivisionFlag());// 사업부구분
	    ketMoldChangeOrder.setDivisionFlag(ketMoldChangeRequest.getDivisionFlag());// 사업부구분
	    ketMoldChangeOrder.setProjectOid(ketMoldChangeRequest.getProjectOid());// 프로젝트oid
	    ketMoldChangeOrderVO.setProjectNo(ketMoldChangeRequestVO.getProjectNo());// 프로젝트번호
	    ketMoldChangeOrderVO.setProjectName(ketMoldChangeRequestVO.getProjectName());// 프로젝트명
	    ketMoldChangeOrder.setProdVendor(ketMoldChangeRequest.getProdVendor());// 생산처
	    ketMoldChangeOrder.setVendorFlag(ketMoldChangeRequest.getVendorFlag());// 생산처구분
	    ketMoldChangeOrderVO.setProdVendorName(ketMoldChangeRequestVO.getProdVendorName());// 생산처명

	    // 연계ECR
	    ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoLinkVOList = new ArrayList<KETMoldEcoEcrLinkVO>();// 연계ECR상세 List
	    KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = new KETMoldEcoEcrLinkVO();
	    ketMoldEcoEcrLinkVO.setRelEcrLinkOid("");// 연계ECR링크oid
	    ketMoldEcoEcrLinkVO.setRelEcrOid(oid);// 연계ECRoid
	    ketMoldEcoEcrLinkVO.setRelEcrId(ketMoldChangeRequest.getEcrId());// 연계ECRID
	    ketMoldEcoEcrLinkVO.setRelEcrName(ketMoldChangeRequest.getEcrName());
	    ketMoldEcoEcrLinkVO.setCreateDeptName(ketMoldChangeRequestVO.getOrgName());// 작성부서명
	    ketMoldEcoEcrLinkVO.setCreatorName(ketMoldChangeRequestVO.getCreatorName());// 작성자명
	    ketMoldEcoEcrLinkVO.setApproveDate(ketMoldChangeRequestVO.getApprovalDate());// 승인일자
	    ketMoldEcoLinkVOList.add(ketMoldEcoEcrLinkVO);
	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(ketMoldEcoLinkVOList);

	    // 관련부품
	    ArrayList<KETMoldECOPartLinkVO> ketMoldEcrPartLinkVOList = ketMoldChangeRequestVO.getKetMoldECOPartLinkVOList();
	    ArrayList<KETMoldECOPartLinkVO> ketMoldEcoPartLinkVOList = new ArrayList<KETMoldECOPartLinkVO>();
	    KETMoldECOPartLinkVO ketMoldEcoPartLinkVO = null;
	    KETMoldECOPartLinkVO ketMoldEcrPartLinkVO = null;
	    int size = ketMoldEcrPartLinkVOList.size();
	    for (int i = 0; i < size; i++) {
		ketMoldEcoPartLinkVO = new KETMoldECOPartLinkVO();
		ketMoldEcrPartLinkVO = ketMoldEcrPartLinkVOList.get(i);
		ketMoldEcoPartLinkVO.setRelPartOid(ketMoldEcrPartLinkVO.getRelPartOid());
		ketMoldEcoPartLinkVO.setRelPartNumber(ketMoldEcrPartLinkVO.getRelPartNumber());// 관련부품번호
		ketMoldEcoPartLinkVO.setRelPartName(ketMoldEcrPartLinkVO.getRelPartName());// 관련부품명
		ketMoldEcoPartLinkVO.setRelPartRev(ketMoldEcrPartLinkVO.getRelPartRev());// 관련부품버젼
		ketMoldEcoPartLinkVO.setChangeReqComment("");// 요청내용
		ketMoldEcoPartLinkVO.setRelPartLinkOid("");// 관련부품linkoid
		ketMoldEcoPartLinkVO.setSecureBudgetYn("N");
		ketMoldEcoPartLinkVO.setSecureBudgetYnName("미확보");
		ketMoldEcoPartLinkVO.setExpectCost(KETStringUtil.getFormattedNumber("0", "###,##0"));
		ketMoldEcoPartLinkVOList.add(ketMoldEcoPartLinkVO);
	    }
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(ketMoldEcoPartLinkVOList);
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);
	    ketMoldChangeOrderVO.setOid("");// ECO oid VO세팅
	    ketMoldChangeOrderVO.setTotalCount(1);

	} catch (Exception e) {
	}
	request.setAttribute("ketMoldChangeOrderVO", ketMoldChangeOrderVO);
	gotoResult(request, response, "/jsp/ecm/CreateMoldEcoForm.jsp?from=ecr");
    }// end-of-goMoldEco

}
