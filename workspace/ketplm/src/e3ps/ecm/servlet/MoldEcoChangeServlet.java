package e3ps.ecm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.Mastered;
import wt.vc.Versioned;
import e3ps.common.content.uploader.FileUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.ECMProperties;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.dao.MoldChangeOrderDao;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldECALinkVO;
import e3ps.ecm.entity.KETMoldECOPartLink;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

public class MoldEcoChangeServlet extends CommonServlet {
    private static final long serialVersionUID      = 8419728326730565994L;

    public String             cmd                   = null;                //command
    public String             oid                   = null;                //ECO OID
    public String             ecoId                 = null;                //ECO ID
    public String             ecoName               = null;                //제목
    public String             devYn                 = null;                //개발/양산구분
    public String             divisionFlag          = null;                //사업부구분
    public String             projectOid            = null;                //관련 프로젝트
    public String             prodVendor            = null;                //생산처
    public String             vendorFlag            = null;                //생산처구분
    public String             changeReason          = null;                //설계변경 사유
    public String             otherReasonDesc       = null;                //설계변경 기타사유
    public String             increaseProdType      = null;                //생산성 향상 유형
    public String             otherIncreaseProdType = null;                //생산성 향상 기타유형
    public String             ecoWorkerId           = null;                //ECO 담당자id
    public String             oldEcoWorkerId        = null;                //조회ECO 담당자id
    public String             contentType           = null;                //ContentType
    public FileUploader       uploader              = null;

    public String[]           relEcrOid             = null;
    public String[]           relEcrLinkOid         = null;
    public String[]           relProdEcoOid         = null;
    public String[]           relProdEcoLinkOid     = null;

    public String[]           relPartOid            = null;
    public String[]           relPartLinkOid        = null;
    public String[]           secureBudgetYn        = null;
    public String[]           expectCost            = null;

    public String[]           relBomOid             = null;
    public String[]           relBomLinkOid         = null;
    public String[]           relEpmOid             = null;
    public String[]           relEpmLinkOid         = null;
    public String[]           relDocOid             = null;
    public String[]           relDocLinkOid         = null;

    public String[]           activityType;                                //활동계획구분(1:도면, 2:BOM, 3:문서)
    public String[]           activityTypeName;                            //활동계획구분명(1:도면, 2:BOM, 3:문서)
    public String[]           relEcaOid;                                   //활동계획oid
    public String[]           activityStatus;                              //활동계획진행상태
    public String[]           activityStatusName;                          //활동계획진행상태명
    public String[]           workerId;                                    //활동계획담당자oid
    public String[]           workerName;                                  //활동계획담당자명
    public String[]           completeDate;                                //활동계획완료일자

    public String[]           relEcaEpmActivityType;                       //활동계획구분(1:도면, 2:BOM, 3:문서)
    public String[]           moldEcaEpmOid;                               //관련활동문서링크oid
    public String[]           relEcaEpmLinkOid;                            //관련활동도면링크oid
    public String[]           relEcaEpmOid;                                //관련활동도면oid
    public String[]           relEcaEpmNo;                                 //관련활동도면번호
    public String[]           relEcaEpmName;                               //관련활동도면명
    public String[]           relEcaEpmPreRev;                             //관련활동도면이전버젼
    public String[]           relEcaEpmAfterRev;                           //관련활동도면이후버젼
    public String[]           relEcaEpmPlanDate;                           //관련활동도면변경예정일
    public String[]           relEcaEpmWorkerOid;                          //활동계획도면/BOM담당자oid
    public String[]           relEcaEpmActivityComment;                    //관련활동도면변경내용
    public String[]           relEcaEpmChangeType;                         //관련활동도면변경유형
    public String[]           oldMoldChangePlanOid;                        //금형변경일정 old oid
    public String[]           newMoldChangePlanOid;                        //금형변경일정 new oid
    public String[]           dieNo;                                       //금형변경일정dieno
    public String[]           scheduleId;                                  //금형변경일정id
    public String[]           relEcaEpmChangeYn;                           //도면변경여부
    public String[]           relEcaEpmDocType;                            //도면구분
    public String[]           relEcaEpmDocReviseYn;                        //도면개정여부
    public String[]           relEcaEpmDocCancelRevYn;                     //도면개정취소여부
    public String[]           relEcaAfterEpmOid;                           //개정후도면oid

    public String[]           relEcaBomActivityType;                       //활동계획구분(1:도면, 2:BOM, 3:문서)
    public String[]           relEcaBomLinkOid;                            //관련활동BOM링크oid
    public String[]           relEcaBomOid;                                //관련활동BOMoid
    public String[]           beforePartOid;                               //Before부품oid
    public String[]           relEcaBomPreRev;                             //관련활동BOM이전버젼
    public String[]           moldEcaBomOid;                               //관련활동BOM링크oid
    public String[]           relEcaBomWorkerOid;                          //활동계획도면/BOM담당자oid
    public String[]           relEcaBomChangeYn;                           //BOM변경여부
    public String[]           relEcaBomNo;                                 //관련활동BOM번호
    public String[]           relEcaBomName;                               //관련활동BOM명
    public String[]           relEcaBomAfterRev;                           //관련활동BOM이후버젼
    public String[]           relEcaBomPlanDate;                           //관련활동BOM변경예정일
    public String[]           relEcaBomActivityComment;                    //관련활동BOM변경내용
    public String[]           relEcaBomReviseYn;                           //BOM개정여부
    public String[]           relEcaBomReviseCancel;                       //BOM 개정취소 여부

    public String[]           relEcaDocActivityType;                       //활동계획구분(1:도면, 2:BOM, 3:문서)
    public String[]           moldEcaDocOid;                               //관련활동문서링크oid
    public String[]           relEcaDocLinkOid;                            //관련활동문서링크oid
    public String[]           relEcaDocOid;                                //관련활동문서oid
    public String[]           relEcaDocPreRev;                             //관련활동문서이전버젼
    public String[]           relEcaDocPlanDate;                           //관련활동문서변경예정일
    public String[]           relEcaDocWorkerOid;                          //활동계획담당자oid
    public String[]           relEcaDocActivityComment;                    //관련활동문서변경내용
    public String[]           relEcaDocAfterRev;                           //관련활동문서이후버젼
    public String[]           targetPartNumber;                            //표준품대상부품번호
    public String[]           relEcaDocChangeType;                         //표준품변경유형

    public String             deleteFileList        = null;                //첨부파일 삭제목록
    public String             deleteRelEcrList      = null;                //연계ECR 삭제목록
    public String             deleteRelProdEcoList  = null;                //연계제품ECO 삭제목록
    public String             deleteRelPartList     = null;                //관련부품 삭제목록
    public String             deleteRelBomList      = null;                //관련BOM 삭제목록
    public String             deleteRelEpmList      = null;                //관련도면 삭제목록
    public String             deleteRelDocList      = null;                //관련문서 삭제목록
    public String             initTab               = null;                //초기텝페이지
    public String             loginUserOid          = null;                //로그인담당자oid
    public String             ecaType               = null;                //(1:도면, 2:BOM, 3:표준품)
    public String             isToDo                = null;
    public String             isCompleteModify      = null;


    // ECN
    private String[]          customCode            = null;
    private String[]          customName            = null;
    private String[]          completeRequestDate   = null;
    private String[]          receiveConfirmedDate  = null;
    private String[]          activityTypeDesc      = null;
    private String[]          activityBackDesc      = null;


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
	processParameter(request, response);
	if (cmd.equals("view") || cmd.equals("updateview") || cmd.equals("popupview")) {
	    view(request, response, cmd);
	}
	else if (cmd.equals("create")) {
	    create(request, response);
	}
	else if (cmd.equals("update") || cmd.equals("reviseEpm")) {
	    update(request, response);
	}
	else if (cmd.equals("complete")) {
	    complete(request, response);
	}
	else if (cmd.equals("delete")) {
	    delete(request, response);
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
	if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
	    uploader = FileUploader.newFileUploader(null, request, response);
	    cmd = StringUtil.checkNull(uploader.getFormParameter("cmd"));
	    oid = StringUtil.checkNull(uploader.getFormParameter("oid"));
	    ecoId = StringUtil.checkNull(uploader.getFormParameter("ecoId"));
	    ecoName = StringUtil.checkNull(uploader.getFormParameter("ecoName"));
	    devYn = StringUtil.checkNull(uploader.getFormParameter("devYn"));
	    divisionFlag = StringUtil.checkNull(uploader.getFormParameter("divisionFlag"));
	    projectOid = StringUtil.checkNull(uploader.getFormParameter("projectOid"));
	    prodVendor = StringUtil.checkNull(uploader.getFormParameter("prodVendor"));
	    vendorFlag = StringUtil.checkNull(uploader.getFormParameter("vendorFlag"));//생산처구분
	    changeReason = StringUtil.checkNull(uploader.getFormParameter("changeReason"));
	    otherReasonDesc = StringUtil.checkNull(uploader.getFormParameter("otherReasonDesc"));
	    increaseProdType = StringUtil.checkNull(uploader.getFormParameter("increaseProdType"));
	    otherIncreaseProdType = StringUtil.checkNull(uploader.getFormParameter("otherIncreaseProdType"));
	    oldEcoWorkerId = StringUtil.checkNull(uploader.getFormParameter("oldEcoWorkerId"));//기존ECO담당자id
	    ecoWorkerId = StringUtil.checkNull(uploader.getFormParameter("ecoWorkerId"));//ECO담당자id

	    isToDo = StringUtil.checkNull(uploader.getFormParameter("isToDo"));
	    isCompleteModify = StringUtil.checkNull(uploader.getFormParameter("isCompleteModify"));

	    deleteFileList = StringUtil.checkNull(uploader.getFormParameter("deleteFileList"));//첨부파일 삭제목록
	    deleteRelEcrList = StringUtil.checkNull(uploader.getFormParameter("deleteRelEcrList"));//연계ECR 삭제목록
	    deleteRelProdEcoList = StringUtil.checkNull(uploader.getFormParameter("deleteRelProdEcoList"));//연계제품ECO 삭제목록
	    deleteRelPartList = StringUtil.checkNull(uploader.getFormParameter("deleteRelPartList"));//관련부품 삭제목록
	    deleteRelBomList = StringUtil.checkNull(uploader.getFormParameter("deleteRelBomList"));//관련BOM 삭제목록
	    deleteRelEpmList = StringUtil.checkNull(uploader.getFormParameter("deleteRelEpmList"));//관련도면 삭제목록
	    deleteRelDocList = StringUtil.checkNull(uploader.getFormParameter("deleteRelDocList"));//관련문서 삭제목록
	    initTab = StringUtil.checkNull(uploader.getFormParameter("initTab"));//초기텝페이지
	    ecaType = StringUtil.checkNull(uploader.getFormParameter("ecaType"));//(1:도면, 2:BOM, 3:표준품, 4:ECN)
	    if ("".equals(ecaType) || "0".equals(ecaType)) {
		ecaType = "1";//1:도면
	    }

	    relEcrOid = uploader.getFormParameters("relEcrOid");//관련ECRoid 추가목록
	    relEcrLinkOid = uploader.getFormParameters("relEcrLinkOid");//관련ECR Linkoid 추가목록
	    relProdEcoOid = uploader.getFormParameters("relProdEcoOid");//관련제품ECOoid 추가목록
	    relProdEcoLinkOid = uploader.getFormParameters("relProdEcoLinkOid");//관련제품ECR Linkoid 추가목록

	    relPartOid = uploader.getFormParameters("relPartOid");//관련부품oid 추가목록
	    relPartLinkOid = uploader.getFormParameters("relPartLinkOid");//관련부품ECOoid 추가목록
	    secureBudgetYn = uploader.getFormParameters("secureBudgetYn");//비용확보여부
	    expectCost = uploader.getFormParameters("expectCost");//예상비용

	    //도면활동계획
	    relEcaEpmActivityType = uploader.getFormParameters("relEcaEpmActivityType");//활동계획구분(1:도면, 2:BOM, 3:문서)
	    moldEcaEpmOid = uploader.getFormParameters("moldEcaEpmOid");//관련활동oid
	    relEcaEpmLinkOid = uploader.getFormParameters("relEcaEpmLinkOid");//관련활동도면링크oid
	    relEcaEpmOid = uploader.getFormParameters("relEcaEpmOid");//관련활동도면oid
	    relEcaEpmPreRev = uploader.getFormParameters("relEcaEpmPreRev");//관련활동도면버전
	    relEcaEpmWorkerOid = uploader.getFormParameters("relEcaEpmWorkerOid");//활동계획도면/BOM담당자oid
	    relEcaEpmPlanDate = uploader.getFormParameters("relEcaEpmPlanDate");//관련활동도면변경예정일
	    relEcaEpmActivityComment = uploader.getFormParameters("relEcaEpmActivityComment");//관련활동도면변경내용
	    relEcaEpmPlanDate = uploader.getFormParameters("relEcaEpmPlanDate");//관련활동도면변경예정일
	    dieNo = uploader.getFormParameters("dieNo");//관련활동문서변경내용
	    scheduleId = uploader.getFormParameters("scheduleId");//관련활동문서변경내용
	    oldMoldChangePlanOid = uploader.getFormParameters("oldMoldChangePlanOid");//금형변경일정 old oid
	    newMoldChangePlanOid = uploader.getFormParameters("newMoldChangePlanOid");//금형변경일정 new oid
	    relEcaEpmAfterRev = uploader.getFormParameters("relEcaEpmAfterRev");//관련활동도면이후버전
	    relEcaEpmChangeType = uploader.getFormParameters("relEcaEpmChangeType");//관련활동도면변경유형
	    relEcaEpmChangeYn = uploader.getFormParameters("relEcaEpmChangeYn");//관련활동도면변경여부
	    relEcaEpmDocType = uploader.getFormParameters("relEcaEpmDocType");//관련활동도면구분
	    relEcaEpmDocReviseYn = uploader.getFormParameters("relEcaEpmDocReviseYn");//도면개정목록
	    relEcaEpmDocCancelRevYn = uploader.getFormParameters("relEcaEpmDocCancelRevYn");//도면개정취소여부
	    relEcaAfterEpmOid = uploader.getFormParameters("relEcaAfterEpmOid");//개정후도면oid

	    //BOM활동계획
	    relEcaBomActivityType = uploader.getFormParameters("relEcaBomActivityType");//활동계획구분(1:도면, 2:BOM, 3:문서)
	    moldEcaBomOid = uploader.getFormParameters("moldEcaBomOid");//관련활동oid
	    relEcaBomLinkOid = uploader.getFormParameters("relEcaBomLinkOid");//관련활동BOM링크oid
	    relEcaBomOid = uploader.getFormParameters("relEcaBomOid");//관련활동BOMoid
	    relEcaBomNo = uploader.getFormParameters("relEcaBomNo");//관련활동BOM번호
	    relEcaBomName = uploader.getFormParameters("relEcaBomName");//관련활동BOM명
	    relEcaBomPreRev = uploader.getFormParameters("relEcaBomPreRev");//관련활동BOM이전버전
	    relEcaBomAfterRev = uploader.getFormParameters("relEcaBomAfterRev");//관련활동BOM이후버전
	    relEcaBomWorkerOid = uploader.getFormParameters("relEcaBomWorkerOid");//활동계획BOM담당자oid
	    relEcaBomPlanDate = uploader.getFormParameters("relEcaBomPlanDate");//관련활동BOM변경예정일
	    relEcaBomActivityComment = uploader.getFormParameters("relEcaBomActivityComment");//관련활동BOM변경내용
	    relEcaBomChangeYn = uploader.getFormParameters("relEcaBomChangeYn");//관련활동BOM변경예정일
	    relEcaBomReviseYn = uploader.getFormParameters("relEcaBomReviseYn");//BOM개정목록
	    relEcaBomReviseCancel = uploader.getFormParameters("relEcaBomReviseCancel");//BOM개정취소목록
	    beforePartOid = uploader.getFormParameters("beforePartOid");//Before부품oid목록

	    //문서활동계획
	    relEcaDocActivityType = uploader.getFormParameters("relEcaDocActivityType");//활동계획구분(1:도면, 2:BOM, 3:문서)
	    moldEcaDocOid = uploader.getFormParameters("moldEcaDocOid");//관련활동oid
	    relEcaDocLinkOid = uploader.getFormParameters("relEcaDocLinkOid");//관련활동문서링크oid
	    relEcaDocOid = uploader.getFormParameters("relEcaDocOid");//관련활동문서oid
	    relEcaDocPreRev = uploader.getFormParameters("relEcaDocPreRev");//관련활동문서버전
	    relEcaDocWorkerOid = uploader.getFormParameters("relEcaDocWorkerOid");//활동계획담당자oid
	    relEcaDocPlanDate = uploader.getFormParameters("relEcaDocPlanDate");//관련활동문서변경예정일
	    relEcaDocActivityComment = uploader.getFormParameters("relEcaDocActivityComment");//관련활동문서변경내용
	    relEcaDocAfterRev = uploader.getFormParameters("relEcaDocAfterRev");//관련활동문서이후버젼
	    targetPartNumber = uploader.getFormParameters("targetPartNumber");//표준품대상부품번호
	    relEcaDocChangeType = uploader.getFormParameters("relEcaDocChangeType");//표준품변경유형


	    // ECN
	    customCode = uploader.getFormParameters("customCode");
	    customName = uploader.getFormParameters("customName");
	    completeRequestDate = uploader.getFormParameters("completeRequestDate");
	    receiveConfirmedDate = uploader.getFormParameters("receiveConfirmedDate");
	    activityTypeDesc = uploader.getFormParameters("activityTypeDesc");
	    activityBackDesc = uploader.getFormParameters("activityBackDesc");

	}
	else {
	    cmd = StringUtil.checkNull(request.getParameter("cmd"));
	    oid = StringUtil.checkNull(request.getParameter("oid"));
	    ecoId = StringUtil.checkNull(request.getParameter("ecoId"));
	    ecoName = StringUtil.checkNull(request.getParameter("ecoName"));
	    devYn = StringUtil.checkNull(request.getParameter("devYn"));
	    divisionFlag = StringUtil.checkNull(request.getParameter("divisionFlag"));
	    projectOid = StringUtil.checkNull(request.getParameter("projectOid"));
	    prodVendor = StringUtil.checkNull(request.getParameter("prodVendor"));
	    changeReason = StringUtil.checkNull(request.getParameter("changeReason"));
	    otherReasonDesc = StringUtil.checkNull(request.getParameter("otherReasonDesc"));
	    increaseProdType = StringUtil.checkNull(request.getParameter("increaseProdType"));
	    otherIncreaseProdType = StringUtil.checkNull(request.getParameter("otherIncreaseProdType"));
	    ecaType = StringUtil.checkNull(request.getParameter("ecaType"));//(1:도면, 2:BOM, 3:표준품)
	    if ("".equals(ecaType) || "0".equals(ecaType)) {
		ecaType = "1";//1:도면
	    }

	    deleteFileList = StringUtil.checkNull(request.getParameter("deleteFileList"));
	    deleteRelEcrList = StringUtil.checkNull(request.getParameter("deleteRelEcrList"));//연계ECR 삭제목록
	    deleteRelProdEcoList = StringUtil.checkNull(request.getParameter("deleteRelProdEcoList"));//연계제품ECO 삭제목록
	    deleteRelPartList = StringUtil.checkNull(request.getParameter("deleteRelPartList"));//관련부품 삭제목록
	    deleteRelBomList = StringUtil.checkNull(request.getParameter("deleteRelBomList"));//관련BOM 삭제목록
	    deleteRelEpmList = StringUtil.checkNull(request.getParameter("deleteRelEpmList"));//관련도면 삭제목록
	    deleteRelDocList = StringUtil.checkNull(request.getParameter("deleteRelDocList"));//관련문서 삭제목록
	    relPartOid = request.getParameterValues("relPartOid");//관련부품oid 추가목록
	    relPartLinkOid = request.getParameterValues("relPartLinkOid");//관련부품ECOoid 추가목록
	    secureBudgetYn = request.getParameterValues("secureBudgetYn");//비용확보여부
	    expectCost = request.getParameterValues("expectCost");//예상비용


	    // ECN
	    customCode = request.getParameterValues("customCode");
	    customName = request.getParameterValues("customName");
	    completeRequestDate = request.getParameterValues("completeRequestDate");
	    receiveConfirmedDate = request.getParameterValues("receiveConfirmedDate");
	    activityTypeDesc = request.getParameterValues("activityTypeDesc");
	    activityBackDesc = request.getParameterValues("activityBackDesc");

	}
	try {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    loginUserOid = (String) CommonUtil.getOIDString(user);
	} catch (WTException e) {
	}
    }

    /**
     * 
     * 함수명 : view 설명 : 금형 ECO를 변경활동을 조회하여 금형 ECO를 변경활동 화면으로 이동한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void view(HttpServletRequest request, HttpServletResponse response, String cmd) throws ServletException, IOException {
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	ketMoldChangeOrderVO.setLoginUserOid(loginUserOid);
	ketMoldChangeOrderVO.setEcaType(ecaType);
	KETMoldChangeOrder ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);//ECO객체조회
	if (ketMoldChangeOrder != null) {
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);//ECO객체 VO세팅
	    ketMoldChangeOrderVO.setTotalCount(1);//조회여부 VO세팅
	    ketMoldChangeOrderVO.setOid(CommonUtil.getOIDLongValue(oid) + "");//ECO oid VO세팅
	    MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();
	    try {
		// 다국어 처리
		KETMessageService messageService = KETMessageService.getMessageService(request);

		//금형ECO 상세조회
		ketMoldChangeOrderVO = moldChangeOrderDao.searchEcoDetail(ketMoldChangeOrderVO, messageService.getLocale().toString());
		//금형ECO 연계ECR 목록조회
		ketMoldChangeOrderVO = moldChangeOrderDao.searchMoldEcoRelEcrList(ketMoldChangeOrderVO);
		//금형ECO 연계 제품ECO 목록조회
		ketMoldChangeOrderVO = moldChangeOrderDao.searchMoldEcoRelProdEcoList(ketMoldChangeOrderVO);

		//금형ECA 상세조회
		ketMoldChangeOrderVO = moldChangeOrderDao.searchMoldEcaAllByWorkerId(ketMoldChangeOrderVO, cmd);
	    } catch (Exception e) {
	    }
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(getKETMoldECOPartLink(ketMoldChangeOrder));
	}
	else {
	    ketMoldChangeOrderVO.setOid(oid);//ECO oid VO세팅
	}
	request.setAttribute("ketMoldChangeOrderVO", ketMoldChangeOrderVO);
	if (cmd.equals("view")) {
	    gotoResult(request, response, "/jsp/ecm/CreateMoldEcoChangeForm.jsp");
	}
	else if (cmd.equals("updateview")) {
	    gotoResult(request, response, "/jsp/ecm/CreateMoldEcoChangeForm.jsp");
	}
	else if (cmd.equals("popupview")) {
	    gotoResult(request, response, "/jsp/ecm/ViewMoldEcoPopupForm.jsp");
	}
    }

    /**
     * 
     * 함수명 : getKETMoldECOPartLink 설명 : 금형 ECO 관련부품 List를 조회한다.
     * 
     * @param ketMoldChangeOrder
     *            : 금형ECO 객체
     * @return ArrayList<KETMoldECOPartLinkVO> : 관련부품 VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private ArrayList<KETMoldECOPartLinkVO> getKETMoldECOPartLink(KETMoldChangeOrder ketMoldChangeOrder) throws ServletException, IOException {
	QueryResult pqr;
	ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = new ArrayList<KETMoldECOPartLinkVO>();//금형ECO관련부품 List
	KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
	try {
	    pqr = PersistenceHelper.manager.navigate(ketMoldChangeOrder, "part", KETMoldECOPartLink.class, false);
	    while (pqr.hasMoreElements()) {
		KETMoldECOPartLink link = (KETMoldECOPartLink) pqr.nextElement();
		ketMoldECOPartLinkVO = new KETMoldECOPartLinkVO();
		ketMoldECOPartLinkVO.setRelPartOid(CommonUtil.getOIDString(link.getPart()));
		ketMoldECOPartLinkVO.setRelPartNumber(link.getPart().getNumber());//관련부품번호
		ketMoldECOPartLinkVO.setRelPartName(link.getPart().getName());//관련부품명
		ketMoldECOPartLinkVO.setRelPartRev(VersionUtil.getMajorVersion(link.getPart()));//관련부품버젼

		ketMoldECOPartLinkVO.setRelPartLinkOid(CommonUtil.getOIDString(link));
		ketMoldECOPartLinkVO.setSecureBudgetYn(link.getSecureBudgetYn());
		if ("Y".equals(link.getSecureBudgetYn())) {
		    ketMoldECOPartLinkVO.setSecureBudgetYnName("확보");
		}
		else {
		    ketMoldECOPartLinkVO.setSecureBudgetYnName("미확보");
		}
		ketMoldECOPartLinkVO.setExpectCost(KETStringUtil.getFormattedNumber(link.getExpectCost(), "###,##0"));
		ketMoldECOPartLinkVOList.add(ketMoldECOPartLinkVO);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return ketMoldECOPartLinkVOList;
    }//end-of-getKETMoldECOPartLink

    /**
     * 
     * 함수명 : create 설명 : 금형 ECO를 신규 저장한다. ECO ID를 채번한다. 연계 ECR VO List를 생성한다. 연계 제품 ECO VO List를 생성한다. 관련부품 VO List를 생성한다. 첨부파일 VO List를
     * 생성한다. ECO 담당자 활동계획 VO를 생성한다. 도면/BOM VO List를 생성한다. 표준품 List VO List를 생성한다. 금형 ECO를 신규 저장한다. 저장이 성공한 경우 금형 ECO 상세조회 화면으로 이동한다.
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
	KETMoldChangeOrder ketMoldChangeOrder = null;
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	try {
	    ketMoldChangeOrder = KETMoldChangeOrder.newKETMoldChangeOrder();

	    MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();
	    ecoId = moldChangeOrderDao.getEcoId();
	    ketMoldChangeOrder.setEcoId(ecoId);
	    ketMoldChangeOrder.setEcoName(ecoName);
	    ketMoldChangeOrder.setDevYn(devYn);//개발/양산구분
	    ketMoldChangeOrder.setDivisionFlag(divisionFlag);//사업부구분
	    ketMoldChangeOrder.setProjectOid(projectOid);//관련 프로젝트
	    ketMoldChangeOrder.setProdVendor(prodVendor);
	    ketMoldChangeOrder.setVendorFlag(vendorFlag);
	    ketMoldChangeOrder.setChangeReason(changeReason);
	    ketMoldChangeOrder.setOtherReasonDesc(otherReasonDesc);
	    ketMoldChangeOrder.setIncreaseProdType(increaseProdType);
	    ketMoldChangeOrder.setOtherIncreaseProdType(otherIncreaseProdType);
	    ketMoldChangeOrder.setEcoWorkerId(ecoWorkerId);//ECO담당자id

	    //작성부서명 조회
	    PeopleData peopleData = new PeopleData();
	    ketMoldChangeOrder.setDeptName(peopleData.departmentName);

	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(createKETMoldEcoEcrLink());//연계 ECR
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(createKETMoldEcoProdEcoLink());//연계 제품ECO
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(createKETMoldECOPartLink());//관련부품

	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List
	    ketMoldECALinkVOList = createKetMoldECALinkVOForEcoWorker(ketMoldECALinkVOList);//ECO담당자 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);//표준품목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);//도면목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForBom(ketMoldECALinkVOList);//BOM목록 VO생성
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);//활동내용 VO 세팅

	    ketMoldChangeOrderVO.setDeleteRelPartList(deleteRelPartList);//삭제대상 관련부품oid

	    @SuppressWarnings("rawtypes")
	    java.util.Vector files = uploader.getFiles();
	    ketMoldChangeOrderVO.setFiles(files);//첨부파일
	    ketMoldChangeOrderVO.setDeleteFileList(deleteFileList);//삭제대상 첨부파일oid

	    ketMoldChangeOrderVO.setIsToDO(isToDo);

	    ketMoldChangeOrderVO = KETEcmHelper.service.createMoldEcoInfo(ketMoldChangeOrderVO);
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoChangeServlet", "view", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()));
	} catch (Exception e1) {
	    alert(response, "저장시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	}
    }//end-of-create

    /**
     * 
     * 함수명 : createKETMoldEcoEcrLink 설명 : 연계 ECR VO List를 생성한다.
     * 
     * @return ArrayList<KETMoldEcoEcrLinkVO> : 연계 ECR VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public ArrayList<KETMoldEcoEcrLinkVO> createKETMoldEcoEcrLink() throws ServletException, IOException {
	ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList = new ArrayList<KETMoldEcoEcrLinkVO>();//금형ECO 연계ECR List
	if (relEcrOid == null) {
	    return ketMoldEcoEcrLinkVOList;
	}
	KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
	int size = relEcrOid.length;
	for (int i = 0; i < size; i++) {
	    ketMoldEcoEcrLinkVO = new KETMoldEcoEcrLinkVO();
	    ketMoldEcoEcrLinkVO.setRelEcrOid(relEcrOid[i]);
	    ketMoldEcoEcrLinkVO.setRelEcrLinkOid(StringUtil.checkNull(relEcrLinkOid[i]));
	    ketMoldEcoEcrLinkVOList.add(ketMoldEcoEcrLinkVO);
	}
	return ketMoldEcoEcrLinkVOList;
    }//end-of-createKETMoldEcoEcrLink

    /**
     * 
     * 함수명 : createKETMoldEcoProdEcoLink 설명 : 연계 제품 ECO VO List를 생성한다.
     * 
     * @return ArrayList<KETMoldEcoEcrLinkVO> ; 연계 제품 ECO VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public ArrayList<KETMoldEcoEcrLinkVO> createKETMoldEcoProdEcoLink() throws ServletException, IOException {
	ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList = new ArrayList<KETMoldEcoEcrLinkVO>();//금형ECO 연계 제품ECO List
	if (relProdEcoOid == null) {
	    return ketMoldEcoProdEcoLinkVOList;
	}
	KETMoldEcoEcrLinkVO ketMoldEcoProdEcoLinkVO = null;
	int size = relProdEcoOid.length;
	for (int i = 0; i < size; i++) {
	    ketMoldEcoProdEcoLinkVO = new KETMoldEcoEcrLinkVO();
	    ketMoldEcoProdEcoLinkVO.setRelEcrOid(relProdEcoOid[i]);
	    ketMoldEcoProdEcoLinkVO.setRelEcrLinkOid(StringUtil.checkNull(relProdEcoLinkOid[i]));
	    ketMoldEcoProdEcoLinkVOList.add(ketMoldEcoProdEcoLinkVO);
	}
	return ketMoldEcoProdEcoLinkVOList;
    }//end-of-createKETMoldEcoProdEcoLink

    /**
     * 
     * 함수명 : createKETMoldECOPartLink 설명 : 관련부품 VO List를 생성한다.
     * 
     * @return ArrayList<KETMoldECOPartLinkVO> : 관련부품 VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public ArrayList<KETMoldECOPartLinkVO> createKETMoldECOPartLink() throws ServletException, IOException {
	ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = new ArrayList<KETMoldECOPartLinkVO>();//금형ECO관련부품 List
	if (relPartOid == null) {
	    return ketMoldECOPartLinkVOList;
	}
	KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
	int size = relPartOid.length;
	for (int i = 0; i < size; i++) {
	    ketMoldECOPartLinkVO = new KETMoldECOPartLinkVO();
	    ketMoldECOPartLinkVO.setRelPartOid(relPartOid[i]);
	    ketMoldECOPartLinkVO.setRelPartLinkOid(StringUtil.checkNull(relPartLinkOid[i]));
	    ketMoldECOPartLinkVO.setSecureBudgetYn(secureBudgetYn[i]);
	    ketMoldECOPartLinkVO.setExpectCost(KETStringUtil.replace(StringUtil.defaultIfEmpty(expectCost[i], "0"), ",", ""));
	    ketMoldECOPartLinkVOList.add(ketMoldECOPartLinkVO);
	}
	return ketMoldECOPartLinkVOList;
    }//end-of-createKETMoldECRPartLink

    /**
     * 
     * 함수명 : createKetMoldECALinkVOForDoc 설명 : 표준품 활동계획 VO를 생성한다.
     * 
     * @param ketMoldECALinkVOList
     *            : 활동계획 Link VO List
     * @return ketMoldECALinkVOList : 활동계획 Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForDoc(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws ServletException, IOException {
	if (relEcaDocOid == null) {
	    return ketMoldECALinkVOList;
	}
	KETMoldECALinkVO ketMoldECALinkVO = null;
	int size = relEcaDocOid.length;
	for (int i = 0; i < size; i++) {
	    ketMoldECALinkVO = new KETMoldECALinkVO();
	    ketMoldECALinkVO.setActivityType(StringUtil.checkNull(relEcaDocActivityType[i]));
	    ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(relEcaDocWorkerOid[i]));//문서담당자oid
	    ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(moldEcaDocOid[i]));//ECAoid
	    ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(relEcaDocLinkOid[i]));//문서linkoid
	    ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(relEcaDocOid[i]));//문서oid
	    ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(relEcaDocPreRev[i]));//관련활동문서이전버전
	    ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(relEcaDocPlanDate[i]));//문서변경예정일
	    ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(relEcaDocActivityComment[i]));//문서변경내용
	    ketMoldECALinkVO.setRelEcaObjectAfterRev(StringUtil.checkNull(relEcaDocAfterRev[i]));//관련활동문서이후버전
	    //ketMoldECALinkVO.setTargetPart(StringUtil.checkNull(targetPartNumber[i]));//표준품대상부품번호
	    //ketMoldECALinkVO.setChangeType(StringUtil.checkNull(relEcaDocChangeType[i]));//표준품변경유형

	    // ECN
	    ketMoldECALinkVO.setCustomName(StringUtil.checkNull(customName[i]));//후속업무
	    ketMoldECALinkVO.setCompleteRequestDate(StringUtil.checkNull(completeRequestDate[i]));//완료요청일
	    ketMoldECALinkVO.setActivityTypeDesc(StringUtil.checkNull(activityTypeDesc[i]));//변경내용
	    ketMoldECALinkVO.setActivityBackDesc(StringUtil.checkNull(activityBackDesc[i]));//변경내용(Todo)


	    ketMoldECALinkVOList.add(ketMoldECALinkVO);
	}
	return ketMoldECALinkVOList;
    }//end-of-createKetMoldECALinkVOForDoc

    /**
     * 
     * 함수명 : createKetMoldECALinkVOForEcoWorker 설명 : ECO 담당자 활동계획 VO를 생성한다.
     * 
     * @param ketMoldECALinkVOList
     *            : 활동계획 Link VO List
     * @return ketMoldECALinkVOList : 활동계획 Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForEcoWorker(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws ServletException, IOException {
	KETMoldECALinkVO ketMoldECALinkVO = new KETMoldECALinkVO();
	ketMoldECALinkVO.setActivityType("1");
	ketMoldECALinkVO.setWorkerId(ecoWorkerId);//ECO담당자oid
	ketMoldECALinkVO.setRelEcaOid("");//ECAoid
	ketMoldECALinkVO.setRelEcaObjectLinkOid("");//문서linkoid
	ketMoldECALinkVO.setRelEcaObjectOid("");//문서oid
	ketMoldECALinkVO.setRelEcaObjectPreRev("");//문서버전
	ketMoldECALinkVO.setRelEcaObjectPlanDate("");//문서변경예정일
	ketMoldECALinkVO.setRelEcaObjectActivityComment("");//문서변경내용
	ketMoldECALinkVOList.add(ketMoldECALinkVO);
	return ketMoldECALinkVOList;
    }//end-of-createKetMoldECALinkVOForDoc

    /**
     * 
     * 함수명 : createKetMoldECALinkVOForEpm 설명 : 도면 활동계획 VO를 생성한다.
     * 
     * @param ketMoldECALinkVOList
     *            : 활동계획 Link VO List
     * @return ketMoldECALinkVOList : 활동계획 Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForEpm(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws Exception {
	if (relEcaEpmOid == null) {
	    return ketMoldECALinkVOList;
	}
	KETMoldECALinkVO ketMoldECALinkVO = null;
	int size = relEcaEpmOid.length;
	String afterRev = "";
	EcmUtil.logging("createKetMoldECALinkVOForEpm started...");
	try {
	    for (int i = 0; i < size; i++) {
		ketMoldECALinkVO = new KETMoldECALinkVO();
		ketMoldECALinkVO.setActivityType(StringUtil.checkNull(relEcaEpmActivityType[i]));
		ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(relEcaEpmWorkerOid[i]));//도면담당자oid
		ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(moldEcaEpmOid[i]));//ECAoid
		ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(relEcaEpmLinkOid[i]));//도면linkoid
		ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(relEcaEpmOid[i]));//도면oid
		ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(relEcaEpmPreRev[i]));//도면이전버전
		ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(relEcaEpmPlanDate[i]));//도면변경예정일
		ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(relEcaEpmActivityComment[i]));//도면변경내용
		ketMoldECALinkVO.setDieNo(StringUtil.checkNull(dieNo[i]));//Die번호
		ketMoldECALinkVO.setScheduleId(StringUtil.checkNull(scheduleId[i]));//일정ID
		ketMoldECALinkVO.setOldMoldChangePlanOid(StringUtil.checkNull(oldMoldChangePlanOid[i]));//금형변경일정 old oid
		ketMoldECALinkVO.setNewMoldChangePlanOid(StringUtil.checkNull(newMoldChangePlanOid[i]));//금형변경일정 new oid

		afterRev = StringUtil.checkNull(relEcaEpmAfterRev[i]);

		ketMoldECALinkVO.setEpmDocReviseYn(StringUtil.checkNull(relEcaEpmDocReviseYn[i]));
		ketMoldECALinkVO.setEpmDocCancelRevYn(StringUtil.checkNull(relEcaEpmDocCancelRevYn[i]));
		ketMoldECALinkVO.setRelEcaAfterEpmOid(StringUtil.checkNull(relEcaAfterEpmOid[i]));
		//                //도면 개정
		//                if("Y".equals(StringUtil.checkNull(relEcaEpmDocReviseYn[i]))) {
		//                    EcmUtil.logging("createKetMoldECALinkVOForEpm:relEcaEpmDocReviseYn[" + i + "]:" + StringUtil.checkNull(relEcaEpmDocReviseYn[i]));
		//                    afterRev = reviseEpm(relEcaEpmOid[i]);
		//
		//                }
		//
		//                //도면 개정 취소
		//                if("Y".equals(StringUtil.checkNull(relEcaEpmDocCancelRevYn[i]))) {
		//                    EcmUtil.logging("createKetMoldECALinkVOForEpm:relEcaEpmDocCancelRevYn[" + i + "]:" + StringUtil.checkNull(relEcaEpmDocCancelRevYn[i]));
		//                    afterRev = cancelRevEpm(relEcaAfterEpmOid[i]);
		//                }

		ketMoldECALinkVO.setRelEcaObjectAfterRev(afterRev);//도면이후버전

		ketMoldECALinkVO.setChangeType(StringUtil.checkNull(relEcaEpmChangeType[i]));//도면변경유형
		ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(relEcaEpmChangeYn[i]));//도면변경여부
		ketMoldECALinkVO.setRelEcaEpmDocType(StringUtil.checkNull(relEcaEpmDocType[i]));//도면구분
		EcmUtil.logging("createKetMoldECALinkVOForEpm:" + ketMoldECALinkVO.getRelEcaObjectOid() + ":" + ketMoldECALinkVO.getRelEcaEpmChangeYn());
		ketMoldECALinkVOList.add(ketMoldECALinkVO);
	    }
	} catch (Exception e1) {
	    Kogger.error(getClass(), e1);
	    throw new Exception(e1);
	}
	return ketMoldECALinkVOList;
    }//end-of-createKetMoldECALinkVOForEpm

    /**
     * 
     * 함수명 : createKetMoldECALinkVOForBom 설명 : BOM 활동계획 VO를 생성한다.
     * 
     * @param ketMoldECALinkVOList
     *            : 활동계획 Link VO List
     * @return ketMoldECALinkVOList : 활동계획 Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     * 
     * @deprecated
     * 
     */
    private ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForBom(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws Exception {
	if (relEcaBomOid == null) {
	    return ketMoldECALinkVOList;
	}
	KETMoldECALinkVO ketMoldECALinkVO = null;
	int size = relEcaBomOid.length;
	String afterRev = "";
	boolean isSuccess = false;
	try {
	    for (int i = 0; i < size; i++) {
		ketMoldECALinkVO = new KETMoldECALinkVO();
		ketMoldECALinkVO.setActivityType(StringUtil.checkNull(relEcaBomActivityType[i]));
		ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(relEcaBomLinkOid[i]));//BOMlinkoid
		ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(relEcaBomOid[i]));//BOMoid
		ketMoldECALinkVO.setRelEcaObjectNo(StringUtil.checkNull(relEcaBomNo[i]));//BOM번호
		ketMoldECALinkVO.setRelEcaObjectName(StringUtil.checkNull(relEcaBomName[i]));//BOM명
		ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(relEcaBomPreRev[i]));//BOM이전버전
		ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(moldEcaBomOid[i]));//ECAoid
		ketMoldECALinkVO.setBeforePartOid(StringUtil.checkNull(beforePartOid[i]));//Before부품oid
		afterRev = StringUtil.checkNull(relEcaBomAfterRev[i]);

		EcmUtil.logging("createKetMoldECALinkVOForBom:relEcaBomReviseYn[" + i + "]:" + StringUtil.checkNull(relEcaBomReviseYn[i]));
		if ("Y".equals(StringUtil.checkNull(relEcaBomReviseYn[i]))) {
		    afterRev = reviseBom(relEcaBomNo[i]);
		}

		if (StringUtil.checkNull(relEcaBomReviseCancel[i]).equals("Y")) {
		    isSuccess = KETEcmHelper.service.cancelReviseBom(relEcaBomNo[i]);

		    if (isSuccess) {
			afterRev = "";
		    }
		}

		ketMoldECALinkVO.setRelEcaObjectAfterRev(afterRev);//BOM이후버전
		Kogger.debug(getClass(), "TEST9");
		ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(relEcaBomWorkerOid[i]));//BOM담당자oid
		Kogger.debug(getClass(), "TEST10");
		ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(relEcaBomChangeYn[i]));//BOM변경여부
		Kogger.debug(getClass(), "TEST11");
		ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(relEcaBomPlanDate[i]));//BOM변경예정일
		Kogger.debug(getClass(), "TEST12");
		ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(relEcaBomActivityComment[i]));//BOM변경내용
		Kogger.debug(getClass(), "TEST13");
		ketMoldECALinkVOList.add(ketMoldECALinkVO);
	    }
	} catch (Exception e1) {
	    Kogger.error(getClass(), e1);
	    throw new Exception(e1);
	}
	return ketMoldECALinkVOList;
    }//end-of-createKetMoldECALinkVOForBom

    /**
     * 
     * 함수명 : alertNgo 설명 : javascript alert 메시지를 보여준 후 이동할 페이지를 호출한다.
     * 
     * @param res
     *            HTTP 응답 객체
     * @param msg
     *            alert으로 보여줄 메시지
     * @param url
     *            이동할 페이지 url
     * @param cmd
     *            명령어
     * @param oid
     *            금형ECO oid 작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    protected void alertNgo(HttpServletResponse res, String msg, String url, String cmd, String oid) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>" + "\n <input type='hidden' name='cmd' value='" + cmd + "'>"
		    + "\n <input type='hidden' name='oid' value='" + oid + "'>" + "\n <input type='hidden' name='initTab' value='" + initTab + "'>" + "\n <input type='hidden' name='ecaType' value='"
		    + ecaType + "'>" + "\n </form>" + "\n <script language=\"javascript\">" + "\n   parent.hideProcessing();" + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();"
		    + "\n </script>";
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
     * 함수명 : update 설명 : 금형 ECO를 변경 저장한다. ECO ID를 채번한다. 연계 ECR VO List를 생성한다. 연계 제품 ECO VO List를 생성한다. 관련부품 VO List를 생성한다. 첨부파일 VO List를
     * 생성한다. ECO 담당자 활동계획 VO를 생성한다. 도면/BOM VO List를 생성한다. 표준품 List VO List를 생성한다. 금형 ECO를 신규 저장한다. 저장이 성공한 경우 금형 ECO 변경활동 화면으로 이동한다.
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
	String ERROR_MESSAGE = "";

	try {

	    /*
	    KETMoldChangeOrder ketMoldChangeOrder = null;
	    KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);

	    if (isToDo != null && isToDo.equalsIgnoreCase("Y")) {

	    // Todo에서 왔을 경우 '기본사항'을 저장하지 않는다.
	    ketMoldChangeOrderVO.setIsToDO(isToDo);
	    ketMoldChangeOrderVO.setIsCompleteModify(isCompleteModify);
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

	    }
	    else {
	    ketMoldChangeOrder.setEcoName(ecoName);
	    ketMoldChangeOrder.setDevYn(devYn);//개발/양산구분
	    ketMoldChangeOrder.setDivisionFlag(divisionFlag);//사업부구분
	    ketMoldChangeOrder.setProjectOid(projectOid);//관련 프로젝트
	    ketMoldChangeOrder.setProdVendor(prodVendor);
	    ketMoldChangeOrder.setVendorFlag(vendorFlag);
	    ketMoldChangeOrder.setChangeReason(changeReason);
	    ketMoldChangeOrder.setOtherReasonDesc(otherReasonDesc);
	    ketMoldChangeOrder.setIncreaseProdType(increaseProdType);
	    ketMoldChangeOrder.setOtherIncreaseProdType(otherIncreaseProdType);
	    ketMoldChangeOrder.setEcoWorkerId(ecoWorkerId);//ECO담당자id
	    ketMoldChangeOrderVO.setOldEcoWorkerId(oldEcoWorkerId);//기존ECO담당자id
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

	    java.util.Vector files = uploader.getFiles();
	    ketMoldChangeOrderVO.setFiles(files);
	    ketMoldChangeOrderVO.setDeleteFileList(deleteFileList);
	    ketMoldChangeOrderVO.setIsToDO(isToDo);
	    ketMoldChangeOrderVO.setIsCompleteModify(isCompleteModify);

	    ketMoldChangeOrderVO.setDeleteRelEcrList(deleteRelEcrList);//삭제대상 연계 ECR
	    ketMoldChangeOrderVO.setDeleteRelProdEcoList(deleteRelProdEcoList);//삭제대상 연계 제품ECO
	    ketMoldChangeOrderVO.setDeleteRelPartList(deleteRelPartList);//삭제대상 관련부품oid

	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(createKETMoldEcoEcrLink());//연계 ECR
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(createKETMoldEcoProdEcoLink());//연계 제품ECO
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(createKETMoldECOPartLink());//관련부품

	    }


	    ketMoldChangeOrderVO.setDeleteRelEpmList(deleteRelEpmList);//삭제대상 관련도면oid
	    ketMoldChangeOrderVO.setDeleteRelBomList(deleteRelBomList);//삭제대상 관련BOMoid
	    ketMoldChangeOrderVO.setDeleteRelDocList(deleteRelDocList);//삭제대상 관련문서oid

	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List
	    ketMoldECALinkVOList = createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);//표준품목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);//도면목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForBom(ketMoldECALinkVOList);//BOM목록 VO생성
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);//변경활동 VO 세팅

	    ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoInfo(ketMoldChangeOrderVO);
	    */

	    KETMoldChangeOrderVO ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoInfo(this);


	    //문서/도면/BOM 자료가 없는 담당자는 삭제한다. 현재 미적용
	    //관련ECA 삭제 제한(ECO 상태가 계획수립인 경우에만 삭제 가능하다.)
	    String ecoStatus = ketMoldChangeOrderVO.getKetMoldChangeOrder().getLifeCycleState().getStringValue();
	    if (ecoStatus.indexOf("PLANNING") > -1 || ecoStatus.indexOf("REWORK") > -1) {
		MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();
		ArrayList deleteRelEcaList = moldChangeOrderDao.getMoldECADeleteList(ketMoldChangeOrderVO);//삭제대상 ECA조회
		ketMoldChangeOrderVO.setDeleteRelEcaList(deleteRelEcaList);
		ArrayList deleteMoldPlanList = moldChangeOrderDao.getMoldPlanDeleteList(ketMoldChangeOrderVO);//삭제대상 금형변경일정조회
		ketMoldChangeOrderVO.setDeleteMoldPlanList(deleteMoldPlanList);
		ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoActivityInfo(ketMoldChangeOrderVO);
	    }

	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoChangeServlet", "updateview", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()));
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

	}

	if (!ERROR_MESSAGE.equals("")) {
	    alert(response, ERROR_MESSAGE);

	}

    }//end-of-update

    /**
     * 
     * 함수명 : complete 설명 : 금형 ECA 작업완료
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void complete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String ALERT_MESSAGE = "";
	String ERROR_MESSAGE = "";

	try {

	    KETMoldChangeOrder eco = (KETMoldChangeOrder) CommonUtil.getObject(oid);

	    Hashtable<String, String> rtnHash = EcmSearchHelper.manager.completeInMoldECO(eco, ecaType);
	    if (rtnHash != null && !rtnHash.isEmpty()) {
		if (rtnHash.get("flag").equals("TRUE")) {

		}
		else {
		    ALERT_MESSAGE = (String) rtnHash.get("msg");

		}

	    }

	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

	}


	if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {

	    // 에러 메세지 처리
	    try {

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter out = response.getWriter();
		StringBuffer rtn_msg = new StringBuffer("");

		rtn_msg.append("\n <script language=\"javascript\">");

		rtn_msg.append("\n   alert(\"" + ERROR_MESSAGE + "\");");

		rtn_msg.append("\n   parent.hideProcessing();");
		rtn_msg.append("\n   parent.enabledAllBtn();");

		rtn_msg.append("\n </script>");

		out.println(rtn_msg);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);

	    }

	}
	else {

	    try {

		String msg = "";
		if (!ALERT_MESSAGE.equals("")) {
		    msg += "\\n" + ALERT_MESSAGE;
		}
		else {
		    msg = "정상적으로 처리되었습니다.";
		}

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter out = response.getWriter();
		StringBuffer rtn_msg = new StringBuffer("");

		rtn_msg.append("\n <script language=\"javascript\">");
		rtn_msg.append("\n   alert(\"" + msg + "\");");

		if (!ALERT_MESSAGE.equals("")) {
		    rtn_msg.append("\n   parent.hideProcessing();");
		    rtn_msg.append("\n   parent.enabledAllBtn();");

		}
		else {
		    rtn_msg.append("\n   try {");

		    /*
		    rtn_msg.append("\n   	parent.parent.opener.location.href = '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr';");
		    rtn_msg.append("\n   	parent.parent.window.close();");
		    */
		    rtn_msg.append("\n   	parent.lfn_feedback_After_Complete();");

		    rtn_msg.append("\n   } catch(e) { alert(e); }");

		}

		rtn_msg.append("\n </script>");

		out.println(rtn_msg);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);

	    }

	}

    }//end-of-delete

    /**
     * 
     * 함수명 : delete 설명 : 금형 ECO 삭제
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
	KETMoldChangeOrder ketMoldChangeOrder = null;
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	try {
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);
	    KETEcmHelper.service.deleteMoldEcoInfo(ketMoldChangeOrderVO);
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchEcoForm.jsp", "search", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()));
	} catch (Exception e1) {
	    alert(response, "삭제시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	}
    }//end-of-delete

    /**
     * 
     * 함수명 : reviseBom 설명 : BOM을 개정한다.
     * 
     * @param reviseBomOid
     *            : 개정 대상 BOM oid
     * @return afterRev : 개정후 버전
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private String reviseBom(String partNo) throws Exception {
	String afterRev = "";

	Connection conn = PlmDBUtil.getConnection();
	ProdEcoBeans beans = new ProdEcoBeans();
	WTPart part = beans.getLastestPart(partNo, conn);
	//WTPart part = (WTPart)WCUtil.getObject(reviseBomOid);

	if (part == null) {
	    return afterRev;
	}

	try {

	    ECMProperties ecmProperties = ECMProperties.getInstance();
	    ReferenceFactory rf = new ReferenceFactory();

	    Versioned versioned = null;
	    versioned = (Versioned) rf.getReference(part).getObject();

	    if (!VersionHelper.isLatestRevision(versioned)) {
		throw new WTException("최신버전이 아닙니다.");
	    }

	    String location = null;
	    String lifecycle = null;
	    if (versioned instanceof WTPart) {
		location = ecmProperties.getString("part.folder");
		lifecycle = ecmProperties.getString("ecm.lifecyle.part");
	    }
	    VersionUtil.doRevise(versioned, null, lifecycle, location, null, null, null);

	    WTPart latest = null;
	    QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered) part.getMaster());
	    if (qr.hasMoreElements()) {
		latest = (WTPart) qr.nextElement();
		Kogger.debug(getClass(), "latest.getVersionIdentifier().getSeries().getValue():" + latest.getVersionIdentifier().getSeries().getValue());
	    }

	    afterRev = latest.getVersionIdentifier().getSeries().getValue();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    PlmDBUtil.close(conn);
	}
	return afterRev;
    }//end-of-reviseEpm

}
