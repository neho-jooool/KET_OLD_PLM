package e3ps.ecm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import e3ps.common.content.uploader.FileUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.dao.MoldChangeOrderDao;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldECALinkVO;
import e3ps.ecm.entity.KETMoldECOPartLink;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;

public class MoldEcoServlet extends CommonServlet {
    private static final long serialVersionUID      = 8419728326730565994L;

    private String            cmd                   = null;                //command
    private String            oid                   = null;                //ECO OID
    private String            ecoId                 = null;                //ECO ID
    private String            ecoName               = null;                //제목
    private String            devYn                 = null;                //개발/양산구분
    private String            divisionFlag          = null;                //사업부구분
    private String            projectOid            = null;                //관련 프로젝트
    private String            prodVendor            = null;                //생산처
    private String            vendorFlag            = null;                //생산처구분
    private String            changeReason          = null;                //설계변경 사유
    private String            otherReasonDesc       = null;                //설계변경 기타사유
    private String            increaseProdType      = null;                //생산성 향상 유형
    private String            otherIncreaseProdType = null;                //생산성 향상 기타유형
    private String            ecoWorkerId           = null;                //ECO 담당자id
    private String            oldEcoWorkerId        = null;                //조회ECO 담당자id
    private String            contentType           = null;                //ContentType


    // 변경 전
    private String            webEditor             = "";
    private String            webEditorText         = "";
    // 변경 후
    private String            webEditor1            = "";
    private String            webEditorText1        = "";

    private FileUploader      uploader              = null;

    private String[]          relEcrOid             = null;
    private String[]          relEcrLinkOid         = null;
    private String[]          relProdEcoOid         = null;
    private String[]          relProdEcoLinkOid     = null;

    private String[]          relPartOid            = null;
    private String[]          relPartLinkOid        = null;
    private String[]          secureBudgetYn        = null;
    private String[]          expectCost            = null;

    private String[]          relBomOid             = null;
    private String[]          relBomLinkOid         = null;
    private String[]          relEpmOid             = null;
    private String[]          relEpmLinkOid         = null;
    private String[]          relDocOid             = null;
    private String[]          relDocLinkOid         = null;

    private String[]          activityType;                                //활동계획구분(1:도면, 2:BOM, 3:문서)
    private String[]          activityTypeName;                            //활동계획구분명(1:도면, 2:BOM, 3:문서)
    private String[]          relEcaOid;                                   //활동계획oid
    private String[]          activityStatus;                              //활동계획진행상태
    private String[]          activityStatusName;                          //활동계획진행상태명
    private String[]          workerId;                                    //활동계획담당자oid
    private String[]          workerName;                                  //활동계획담당자명
    private String[]          completeDate;                                //활동계획완료일자

    private String[]          relEcaEpmActivityType;                       //활동계획구분(1:도면, 2:BOM, 3:문서)
    private String[]          moldEcaEpmOid;                               //관련활동문서링크oid
    private String[]          relEcaEpmLinkOid;                            //관련활동도면링크oid
    private String[]          relEcaEpmOid;                                //관련활동도면oid
    private String[]          relEcaEpmNo;                                 //관련활동도면번호
    private String[]          relEcaEpmName;                               //관련활동도면명
    private String[]          relEcaEpmPreRev;                             //관련활동도면이전버젼
    private String[]          relEcaEpmAfterRev;                           //관련활동도면이후버젼
    private String[]          relEcaEpmPlanDate;                           //관련활동도면변경예정일
    private String[]          relEcaEpmWorkerOid;                          //활동계획도면/BOM담당자oid
    private String[]          relEcaEpmActivityComment;                    //관련활동도면변경내용
    private String[]          changeType;                                  //관련활동도면변경유형
    private String[]          oldMoldChangePlanOid;                        //금형변경일정 old oid
    private String[]          newMoldChangePlanOid;                        //금형변경일정 new oid
    private String[]          dieNo;                                       //금형변경일정dieno
    private String[]          scheduleId;                                  //금형변경일정id
    private String[]          relEcaEpmChangeYn;                           //도면변경여부
    private String[]          relEcaEpmDocType;                            //도면구분
    private String[]          beforePartOid;                               //Before부품oid

    private String[]          relEcaDocActivityType;                       //활동계획구분(1:도면, 2:BOM, 3:문서)
    private String[]          moldEcaDocOid;                               //관련활동문서링크oid
    private String[]          relEcaDocLinkOid;                            //관련활동문서링크oid
    private String[]          relEcaDocOid;                                //관련활동문서oid
	                                                                    //    private String[] relEcaDocNo;//관련활동문서번호
    private String[]          relEcaDocPreRev;                             //관련활동문서이전버젼
	                                                                    //    private String[] relEcaDocAfterRev;//관련활동문서이후버젼
    private String[]          relEcaDocPlanDate;                           //관련활동문서변경예정일
    private String[]          relEcaDocWorkerOid;                          //활동계획담당자oid
	                                                                    //    private String[] relEcaDocWorkerName;//활동계획담당자명
    private String[]          relEcaDocActivityComment;                    //관련활동문서변경내용

    private String            deleteFileList        = null;                //첨부파일 삭제목록
    private String            deleteRelEcrList      = null;                //연계ECR 삭제목록
    private String            deleteRelProdEcoList  = null;                //연계제품ECO 삭제목록
    private String            deleteRelPartList     = null;                //관련부품 삭제목록
    private String            deleteRelBomList      = null;                //관련BOM 삭제목록
    private String            deleteRelEpmList      = null;                //관련도면 삭제목록
    private String            deleteRelDocList      = null;                //관련문서 삭제목록

    private String            isToDo                = null;
    private String            isCompleteModify      = null;
    private String            prePage               = null;
    private String            hasECA                = null;

    private String            page                  = null;
    private String            totalCount            = null;
    private String            sortColumn            = null;
    private String            param                 = null;
    private String            perPage               = null;
    private String            autoSearch            = null;
    private String            srchpartOid           = null;
    private String            srchpartNo            = null;
    private String            srchpartName          = null;
    private String            srchprojectOid        = null;
    private String            srchprojectNo         = null;
    private String            srchprojectName       = null;
    private String            srchorgName           = null;
    private String            srchcreatorOid        = null;
    private String            srchcreatorName       = null;
    private String            srchecoId             = null;
    private String            srchdivisionFlag      = null;
    private String            srchdevYn             = null;
    private String            srchsancStateFlag     = null;
    private String            srchecoName           = null;
    private String            srchprodMoldCls       = null;
    private String            srchcreateStartDate   = null;
    private String            srchcreateEndDate     = null;


    // 비용확보
    private String[]          relDieNo_eca          = null;
    private String[]          expectCost_eca        = null;
    private String[]          secureBudgetYn_eca    = null;
    private String[]          budget_eca            = null;

    // ECN
    private String[]          customCode            = null;
    private String[]          customName            = null;
    private String[]          completeRequestDate   = null;
    private String[]          receiveConfirmedDate  = null;
    private String[]          activityTypeDesc      = null;
    private String[]          activityBackDesc      = null;


    // 프로젝트에서 산출물로 ECO 직접작성
    // reqData.put("projectOid", paramMap.getString("projectOid")); // 프로젝트 OID
    private String            projectOutputOid      = null;                // 프로젝트 - 산출물 관리 OID


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
	if (cmd.equalsIgnoreCase("view") || cmd.equalsIgnoreCase("updateview") || cmd.equalsIgnoreCase("popupview")) {
	    view(request, response, cmd);
	}
	else if (cmd.equalsIgnoreCase("create")) {
	    create(request, response);
	}
	else if (cmd.equalsIgnoreCase("update")) {
	    update(request, response);
	}
	else if (cmd.equalsIgnoreCase("cancel")) {
	    cancel(request, response);
	}
	// 등록완료
	else if (cmd.equalsIgnoreCase("complete")) {
	    complete(request, response);
	}
	else if (cmd.equalsIgnoreCase("delete")) {
	    delete(request, response);
	}
	// 활동완료 후 저장
	else if (cmd.equalsIgnoreCase("completeModify")) {
	    completeModify(request, response);
	}
	else if (cmd.equalsIgnoreCase("epmExcel") || cmd.equalsIgnoreCase("bomExcel") || cmd.equalsIgnoreCase("stdPartExcel")) {
	    excel(request, response, cmd);
	}
	// 재전송(ERP)
	else if (cmd.equalsIgnoreCase("ResendERP")) {
	    String ALERT_MESSAGE = "";
	    String ERROR_MESSAGE = "";

	    try {
		Kogger.biz("금형 ECO ERP재전송 시작");
		ALERT_MESSAGE = KETEcmHelper.service.resendERP(oid, null);
		KogDbUtil.log("금형ECO ERP재전송", "Success", oid, (String)null, null, null);
		/*
		ReferenceFactory rf = new ReferenceFactory();
		prodEco = (KETProdChangeOrder) rf.getReference(ecoOid).getObject();
		
		State state = State.toState("APPROVED");
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEco, state, true);
		*/
	    } catch (WTException wte) {
		Kogger.biz("금형 ECO ERP재전송 실패");
		Kogger.error(getClass(), wte);

		// 에러 메세지 처리
		ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");
		
		KogDbUtil.log("금형ECO ERP재전송", "Fail", oid, wte, ERROR_MESSAGE, null);

	    } catch (Exception e) {
		Kogger.biz("금형 ECO ERP재전송 실패");
		Kogger.error(getClass(), e);

		// 에러 메세지 처리
		ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");
		
		KogDbUtil.log("금형ECO ERP재전송", "Fail", oid, e, ERROR_MESSAGE, null);

	    }


	    String msg = "정상적으로 처리되었습니다.\\n";
	    if (!ALERT_MESSAGE.equals("")) {
		msg = ALERT_MESSAGE;
	    }
	    if (!ERROR_MESSAGE.equals("")) {
		msg = ERROR_MESSAGE;
	    }
	    alertNgo(response, msg, "/plm/servlet/e3ps/MoldEcoServlet", "view", oid, "view");

	}
	// 활동완료
	else if (cmd.equalsIgnoreCase("ActivityReg")) {
	    String ALERT_MESSAGE = "";
	    String ERROR_MESSAGE = "";

	    try {
		Kogger.biz("금형 ECO 작업완료 시작");
		KETMoldChangeOrder eco = (KETMoldChangeOrder) CommonUtil.getObject(oid);

		KETParamMapUtil paramMap = new KETParamMapUtil();
		// Todo에서의 작업완료시 동작하는 메쏘드를 동작시킨다.
		paramMap.put("activityTypes", new String[] { "1", "2", "3" });
		paramMap.put("isActivityReg", "Y");
		Hashtable<String, String> rtnHash = EcmSearchHelper.manager.completeInMoldECO(eco, paramMap);
		
		KogDbUtil.log("금형ECO 작업완료", "Success", oid, (String)null, null, null);
		
		if (rtnHash != null && !rtnHash.isEmpty()) {
		    if (rtnHash.get("flag").equals("TRUE")) {

		    }
		    else {
			ALERT_MESSAGE = (String) rtnHash.get("msg");

		    }

		}

	    } catch (WTException wte) {
		Kogger.biz("금형 ECO 작업완료 실패");
		Kogger.error(getClass(), wte);

		// 에러 메세지 처리
		ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		KogDbUtil.log("금형ECO 작업완료", "Fail", oid, wte, ERROR_MESSAGE, null);
	    } catch (Exception e) {
		Kogger.biz("금형 ECO 작업완료 실패");
		Kogger.error(getClass(), e);

		// 에러 메세지 처리
		ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		KogDbUtil.log("금형ECO 작업완료", "Fail", oid, e, ERROR_MESSAGE, null);
	    }


	    String msg = "정상적으로 처리되었습니다.\\n";
	    if (!ALERT_MESSAGE.equals("")) {
		msg = ALERT_MESSAGE;
	    }
	    if (!ERROR_MESSAGE.equals("")) {
		msg = ERROR_MESSAGE;
	    }
	    alertNgo(response, msg, "/plm/servlet/e3ps/MoldEcoServlet", "view", oid, "view");

	}
	// 승인완료(디버깅)
	else if (cmd.equalsIgnoreCase("ApprovedReg")) {

	    ReferenceFactory rf = new ReferenceFactory();
	    KETMoldChangeOrder ketMoldChangeOrder;
	    try {
		Kogger.biz("금형 ECO 승인완료(디버깅) 시작");
		ketMoldChangeOrder = (KETMoldChangeOrder) rf.getReference(oid).getObject();

		if (ketMoldChangeOrder.getLifeCycleState().toString().equals("APPROVED")) {
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ketMoldChangeOrder, State.toState("UNDERREVIEW"), true);

		}
		State state = State.toState("APPROVED");

		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ketMoldChangeOrder, state, true);
		
		KogDbUtil.log("금형ECO 승인완료", "Success", oid, (String)null, null, null);

	    } catch (WTRuntimeException e) {
		Kogger.biz("금형 ECO 승인완료(디버깅) 실패");
		Kogger.error(getClass(), e);
		KogDbUtil.log("금형ECO 승인완료", "Fail", oid, e, null, null);
	    } catch (WTException e) {
		Kogger.biz("금형 ECO 승인완료(디버깅) 실패");
		Kogger.error(getClass(), e);
		KogDbUtil.log("금형ECO 승인완료", "Fail", oid, e, null, null);
	    }

	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", oid, "view");
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


	    // 변경 전
	    this.webEditor = StringUtil.checkNull(uploader.getFormParameter("webEditor"));
	    this.webEditorText = StringUtil.checkNull(uploader.getFormParameter("webEditorText"));
	    // 변경 후
	    this.webEditor1 = StringUtil.checkNull(uploader.getFormParameter("webEditor1"));
	    this.webEditorText1 = StringUtil.checkNull(uploader.getFormParameter("webEditorText1"));


	    isToDo = StringUtil.checkNull(uploader.getFormParameter("isToDo"));//To-Do 여부
	    isCompleteModify = StringUtil.checkReplaceStr(uploader.getFormParameter("isCompleteModify"), "N");//활동완료 후 수정여부
	    prePage = StringUtil.checkNull(uploader.getFormParameter("prePage"));//PrePage
	    hasECA = StringUtil.checkNull(uploader.getFormParameter("hasECA"));//PrePage

	    deleteFileList = StringUtil.checkNull(uploader.getFormParameter("deleteFileList"));//첨부파일 삭제목록
	    deleteRelEcrList = StringUtil.checkNull(uploader.getFormParameter("deleteRelEcrList"));//연계ECR 삭제목록
	    deleteRelProdEcoList = StringUtil.checkNull(uploader.getFormParameter("deleteRelProdEcoList"));//연계제품ECO 삭제목록
	    deleteRelPartList = StringUtil.checkNull(uploader.getFormParameter("deleteRelPartList"));//관련부품 삭제목록
	    deleteRelBomList = StringUtil.checkNull(uploader.getFormParameter("deleteRelBomList"));//관련BOM 삭제목록
	    deleteRelEpmList = StringUtil.checkNull(uploader.getFormParameter("deleteRelEpmList"));//관련도면 삭제목록
	    deleteRelDocList = StringUtil.checkNull(uploader.getFormParameter("deleteRelDocList"));//관련문서 삭제목록

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
	    relEcaEpmNo = uploader.getFormParameters("relEcaEpmNo");//관련활동도면번호
	    relEcaEpmName = uploader.getFormParameters("relEcaEpmName");//관련활동도면명
	    relEcaEpmPreRev = uploader.getFormParameters("relEcaEpmPreRev");//관련활동도면버전
	    relEcaEpmWorkerOid = uploader.getFormParameters("relEcaEpmWorkerOid");//활동계획도면/BOM담당자oid
	    relEcaEpmPlanDate = uploader.getFormParameters("relEcaEpmPlanDate");//관련활동도면변경예정일
	    relEcaEpmActivityComment = uploader.getFormParameters("relEcaEpmActivityComment");//관련활동도면변경내용
	    relEcaEpmPlanDate = uploader.getFormParameters("relEcaEpmPlanDate");//관련활동도면변경예정일
	    dieNo = uploader.getFormParameters("dieNo");//관련활동문서변경내용
	    scheduleId = uploader.getFormParameters("scheduleId");//관련활동문서변경내용
	    oldMoldChangePlanOid = uploader.getFormParameters("oldMoldChangePlanOid");//금형변경일정 old oid
	    newMoldChangePlanOid = uploader.getFormParameters("newMoldChangePlanOid");//금형변경일정 new oid
	    relEcaEpmChangeYn = uploader.getFormParameters("relEcaEpmChangeYn");//도면변경여부
	    relEcaEpmDocType = uploader.getFormParameters("relEcaEpmDocType");//도면구분
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

	    page = StringUtil.checkNull(uploader.getFormParameter("page"));
	    totalCount = StringUtil.checkNull(uploader.getFormParameter("totalCount"));
	    sortColumn = StringUtil.checkNull(uploader.getFormParameter("sortColumn"));
	    param = StringUtil.checkNull(uploader.getFormParameter("param"));
	    perPage = StringUtil.checkNull(uploader.getFormParameter("perPage"));
	    autoSearch = StringUtil.checkNull(uploader.getFormParameter("autoSearch"));
	    srchpartOid = StringUtil.checkNull(uploader.getFormParameter("srchpartOid"));
	    srchpartNo = StringUtil.checkNull(uploader.getFormParameter("srchpartNo"));
	    srchpartName = StringUtil.checkNull(uploader.getFormParameter("srchpartName"));
	    srchprojectOid = StringUtil.checkNull(uploader.getFormParameter("srchprojectOid"));
	    srchprojectNo = StringUtil.checkNull(uploader.getFormParameter("srchprojectNo"));
	    srchprojectName = StringUtil.checkNull(uploader.getFormParameter("srchprojectName"));
	    srchorgName = StringUtil.checkNull(uploader.getFormParameter("srchorgName"));
	    srchcreatorOid = StringUtil.checkNull(uploader.getFormParameter("srchcreatorOid"));
	    srchcreatorName = StringUtil.checkNull(uploader.getFormParameter("srchcreatorName"));
	    srchecoId = StringUtil.checkNull(uploader.getFormParameter("srchecoId"));
	    srchdivisionFlag = StringUtil.checkNull(uploader.getFormParameter("srchdivisionFlag"));
	    srchdevYn = StringUtil.checkNull(uploader.getFormParameter("srchdevYn"));
	    srchsancStateFlag = StringUtil.checkNull(uploader.getFormParameter("srchsancStateFlag"));
	    srchecoName = StringUtil.checkNull(uploader.getFormParameter("srchecoName"));
	    srchprodMoldCls = StringUtil.checkNull(uploader.getFormParameter("srchprodMoldCls"));
	    srchcreateStartDate = StringUtil.checkNull(uploader.getFormParameter("srchcreateStartDate"));
	    srchcreateEndDate = StringUtil.checkNull(uploader.getFormParameter("srchcreateEndDate"));

	    // 비용확보
	    relDieNo_eca = uploader.getFormParameters("relDieNo_eca");
	    expectCost_eca = uploader.getFormParameters("expectCost_eca");
	    secureBudgetYn_eca = uploader.getFormParameters("secureBudgetYn_eca");
	    budget_eca = uploader.getFormParameters("budget_eca");

	    // ECN
	    customCode = uploader.getFormParameters("customCode");
	    customName = uploader.getFormParameters("customName");
	    completeRequestDate = uploader.getFormParameters("completeRequestDate");
	    receiveConfirmedDate = uploader.getFormParameters("receiveConfirmedDate");
	    activityTypeDesc = uploader.getFormParameters("activityTypeDesc");
	    activityBackDesc = uploader.getFormParameters("activityBackDesc");

	    // 프로젝트에서 산출물로 ECO 직접작성
	    projectOutputOid = StringUtil.checkNull(uploader.getFormParameter("projectOutputOid"));	// 프로젝트 - 산출물 관리 OID

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

	    page = ParamUtil.getParameter(request, "page");
	    totalCount = ParamUtil.getParameter(request, "totalCount");
	    sortColumn = ParamUtil.getParameter(request, "sortColumn");
	    param = ParamUtil.getParameter(request, "param");
	    perPage = ParamUtil.getParameter(request, "perPage");
	    autoSearch = ParamUtil.getParameter(request, "autoSearch");
	    srchpartOid = ParamUtil.getParameter(request, "srchpartOid");
	    srchpartNo = ParamUtil.getParameter(request, "srchpartNo");
	    srchpartName = ParamUtil.getParameter(request, "srchpartName");
	    srchprojectOid = ParamUtil.getParameter(request, "srchprojectOid");
	    srchprojectNo = ParamUtil.getParameter(request, "srchprojectNo");
	    srchprojectName = ParamUtil.getParameter(request, "srchprojectName");
	    srchorgName = ParamUtil.getParameter(request, "srchorgName");
	    srchcreatorOid = ParamUtil.getParameter(request, "srchcreatorOid");
	    srchcreatorName = ParamUtil.getParameter(request, "srchcreatorName");
	    srchecoId = ParamUtil.getParameter(request, "srchecoId");
	    srchdivisionFlag = ParamUtil.getParameter(request, "srchdivisionFlag");
	    srchdevYn = ParamUtil.getParameter(request, "srchdevYn");
	    srchsancStateFlag = ParamUtil.getParameter(request, "srchsancStateFlag");
	    srchecoName = ParamUtil.getParameter(request, "srchecoName");
	    srchprodMoldCls = ParamUtil.getParameter(request, "srchprodMoldCls");
	    srchcreateStartDate = ParamUtil.getParameter(request, "srchcreateStartDate");
	    srchcreateEndDate = ParamUtil.getParameter(request, "srchcreateEndDate");

	    // 비용확보
	    relDieNo_eca = request.getParameterValues("relDieNo_eca");
	    expectCost_eca = request.getParameterValues("expectCost_eca");
	    secureBudgetYn_eca = request.getParameterValues("secureBudgetYn_eca");
	    budget_eca = request.getParameterValues("budget_eca");

	    // ECN
	    customCode = request.getParameterValues("customCode");
	    customName = request.getParameterValues("customName");
	    completeRequestDate = request.getParameterValues("completeRequestDate");
	    receiveConfirmedDate = request.getParameterValues("receiveConfirmedDate");
	    activityTypeDesc = request.getParameterValues("activityTypeDesc");
	    activityBackDesc = request.getParameterValues("activityBackDesc");

	    // 프로젝트에서 산출물로 ECO 직접작성
	    projectOutputOid = StringUtil.checkNull(request.getParameter("projectOutputOid"));	// 프로젝트 - 산출물 관리 OID


	    prePage = StringUtil.checkNull(request.getParameter("prePage"));//PrePage
	}
    }

    /**
     * 
     * 함수명 : view 설명 : 금형 ECO를 상세 조회한다.
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
	KETMoldChangeOrder ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);//ECO객체조회
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
	    ketMoldChangeOrderVO = moldChangeOrderDao.searchMoldEcaAll(ketMoldChangeOrderVO, cmd);
	} catch (Exception e) {
	}
	ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(getKETMoldECOPartLink(ketMoldChangeOrder));
	request.setAttribute("ketMoldChangeOrderVO", ketMoldChangeOrderVO);
	if (cmd.equalsIgnoreCase("view")) {
	    gotoResult(request, response, "/jsp/ecm/ViewMoldEcoForm.jsp?prePage=" + prePage);
	}
	else if (cmd.equalsIgnoreCase("updateview")) {
	    gotoResult(request, response, "/jsp/ecm/CreateMoldEcoForm.jsp");
	}
	else if (cmd.equalsIgnoreCase("popupview")) {
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
	    Kogger.biz("금형 ECO 등록 시작");
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


	    // 변경 전
	    ketMoldChangeOrder.setWebEditor(this.webEditor);
	    ketMoldChangeOrder.setWebEditorText(this.webEditorText);
	    // 변경 후
	    ketMoldChangeOrder.setWebEditor1(this.webEditor1);
	    ketMoldChangeOrder.setWebEditorText1(this.webEditorText1);


	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);


	    // 프로젝트에서 산출물로 ECO 직접작성
	    // reqData.put("projectOid", paramMap.getString("projectOid")); // 프로젝트 OID
	    ketMoldChangeOrderVO.setProjectOutputOid(this.projectOutputOid); // 프로젝트 - 산출물 관리 OID


	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(createKETMoldEcoEcrLink());//연계 ECR
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(createKETMoldEcoProdEcoLink());//연계 제품ECO
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(createKETMoldECOPartLink());//관련부품

	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List

	    //            if( relEcaEpmOid == null && relEcaDocOid == null )
	    //            {
	    //                ketMoldECALinkVOList = createKetMoldECALinkVOForEcoWorker(ketMoldECALinkVOList);//ECO담당자 VO생성
	    //            }

	    ketMoldECALinkVOList = createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);//표준품목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);//도면/BOM목록 VO생성
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);//표준품목록 VO생성
	    ketMoldChangeOrderVO.setDeleteRelPartList(deleteRelPartList);//삭제대상 관련부품oid

	    @SuppressWarnings("rawtypes")
	    java.util.Vector files = uploader.getFiles();
	    ketMoldChangeOrderVO.setFiles(files);//첨부파일
	    ketMoldChangeOrderVO.setDeleteFileList(deleteFileList);//삭제대상 첨부파일oid
	    ketMoldChangeOrderVO.setIsToDO(isToDo);
	    ketMoldChangeOrderVO.setIsCompleteModify(isCompleteModify);

	    ketMoldChangeOrderVO = KETEcmHelper.service.createMoldEcoInfo(ketMoldChangeOrderVO);
	    
	    KogDbUtil.log("금형ECO 등록", "Success", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), "", ecoName, null);
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), "Create");
	    
	} catch (Exception e1) {
	    alert(response, "저장시 오류가 발생하였습니다.");
	    KogDbUtil.log("금형ECO 등록", "Fail", (String)null, e1, null, null);
	    Kogger.biz("금형 ECO 등록 실패");
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
    private ArrayList<KETMoldEcoEcrLinkVO> createKETMoldEcoEcrLink() throws ServletException, IOException {
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
    private ArrayList<KETMoldEcoEcrLinkVO> createKETMoldEcoProdEcoLink() throws ServletException, IOException {
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
    private ArrayList<KETMoldECOPartLinkVO> createKETMoldECOPartLink() throws ServletException, IOException {
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
    private ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForDoc(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws ServletException, IOException {
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
	    ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(relEcaDocPreRev[i]));//문서버전
	    ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(relEcaDocPlanDate[i]));//문서변경예정일
	    ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(relEcaDocActivityComment[i]));//문서변경내용

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
	ketMoldECALinkVO.setActivityType("1");//도면
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
     * 함수명 : createKetMoldECALinkVOForEpm 설명 : 도면/BOM 활동계획 VO를 생성한다.
     * 
     * @param ketMoldECALinkVOList
     *            : 활동계획 Link VO List
     * @return ketMoldECALinkVOList : 활동계획 Link VO List
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForEpm(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList) throws ServletException, IOException {
	if (relEcaEpmOid == null) {
	    return ketMoldECALinkVOList;
	}
	KETMoldECALinkVO ketMoldECALinkVO = null;
	int size = relEcaEpmOid.length;
	for (int i = 0; i < size; i++) {
	    ketMoldECALinkVO = new KETMoldECALinkVO();
	    ketMoldECALinkVO.setActivityType(StringUtil.checkNull(relEcaEpmActivityType[i]));
	    ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(relEcaEpmWorkerOid[i]));//도면담당자oid
	    ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(moldEcaEpmOid[i]));//ECAoid
	    ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(relEcaEpmLinkOid[i]));//도면linkoid
	    ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(relEcaEpmOid[i]));//도면oid
	    ketMoldECALinkVO.setRelEcaObjectNo(StringUtil.checkNull(relEcaEpmNo[i]));//도면번호
	    ketMoldECALinkVO.setRelEcaObjectName(StringUtil.checkNull(relEcaEpmName[i]));//도면명
	    ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(relEcaEpmPreRev[i]));//도면버전
	    ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(relEcaEpmPlanDate[i]));//도면변경예정일
	    ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(relEcaEpmActivityComment[i]));//도면변경내용
	    ketMoldECALinkVO.setBeforePartOid(StringUtil.checkNull(beforePartOid[i]));//Before부품oid
	    ketMoldECALinkVO.setScheduleId(StringUtil.checkNull(scheduleId[i]));//일정ID
	    ketMoldECALinkVO.setOldMoldChangePlanOid(StringUtil.checkNull(oldMoldChangePlanOid[i]));//금형변경일정 old oid
	    ketMoldECALinkVO.setNewMoldChangePlanOid(StringUtil.checkNull(newMoldChangePlanOid[i]));//금형변경일정 new oid
	    ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(relEcaEpmChangeYn[i]));//도면변경여부
	    ketMoldECALinkVO.setRelEcaEpmDocType(StringUtil.checkNull(relEcaEpmDocType[i]));//도면구분

	    // 비용확보
	    ketMoldECALinkVO.setRelated_die_no(StringUtil.checkNull(relDieNo_eca[i]));
	    ketMoldECALinkVO.setExpect_cost(StringUtil.checkNull(expectCost_eca[i]));
	    ketMoldECALinkVO.setSecure_budget_yn(StringUtil.checkNull(secureBudgetYn_eca[i]));


	    ketMoldECALinkVOList.add(ketMoldECALinkVO);
	}
	return ketMoldECALinkVOList;
    }//end-of-createKetMoldECALinkVOForEpm

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
    protected void alertNgo(HttpServletResponse res, String msg, String url, String cmd, String oid, String prePage) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>" + "\n <input type='hidden' name='cmd' value='" + cmd + "'>"
		    + "\n <input type='hidden' name='oid' value='" + oid + "'>" + "\n <input type='hidden' name='page' value='" + page + "'>" + "\n <input type='hidden' name='totalCount' value='"
		    + totalCount + "'>" + "\n <input type='hidden' name='sortColumn' value='" + sortColumn + "'>" + "\n <input type='hidden' name='param' value='" + param + "'>"
		    + "\n <input type='hidden' name='perPage' value='" + perPage + "'>" + "\n <input type='hidden' name='autoSearch' value='" + autoSearch + "'>"
		    + "\n <input type='hidden' name='partOid' value='" + srchpartOid + "'>" + "\n <input type='hidden' name='partNo' value='" + srchpartNo + "'>"
		    + "\n <input type='hidden' name='partName' value='" + srchpartName + "'>" + "\n <input type='hidden' name='projectOid' value='" + srchprojectOid + "'>"
		    + "\n <input type='hidden' name='projectNo' value='" + srchprojectNo + "'>" + "\n <input type='hidden' name='projectName' value='" + srchprojectName + "'>"
		    + "\n <input type='hidden' name='orgName' value='" + srchorgName + "'>" + "\n <input type='hidden' name='creatorOid' value='" + srchcreatorOid + "'>"
		    + "\n <input type='hidden' name='creatorName' value='" + srchcreatorName + "'>" + "\n <input type='hidden' name='ecoId' value='" + srchecoId + "'>"
		    + "\n <input type='hidden' name='divisionFlag' value='" + srchdivisionFlag + "'>" + "\n <input type='hidden' name='devYn' value='" + srchdevYn + "'>"
		    + "\n <input type='hidden' name='sancStateFlag' value='" + srchsancStateFlag + "'>" + "\n <input type='hidden' name='ecoName' value='" + srchecoName + "'>"
		    + "\n <input type='hidden' name='prodMoldCls' value='" + srchprodMoldCls + "'>" + "\n <input type='hidden' name='createStartDate' value='" + srchcreateStartDate + "'>"
		    + "\n <input type='hidden' name='createEndDate' value='" + srchcreateEndDate + "'>" + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n </form>"
		    + "\n <script language=\"javascript\">" + "\n   parent.hideProcessing();";
	    if (!"cancel".equalsIgnoreCase(this.cmd)) {
		rtn_msg += "\n   alert(\"" + msg + "\");";
	    }

	    rtn_msg += "\n   try { parent.opener.goTaskPage(); } catch(e) {} ";

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
     * 함수명 : update 설명 : 금형 ECO를 변경 저장한다. ECO ID를 채번한다. 연계 ECR VO List를 생성한다. 연계 제품 ECO VO List를 생성한다. 관련부품 VO List를 생성한다. 첨부파일 VO List를
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
    @SuppressWarnings({ "rawtypes" })
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String ERROR_MESSAGE = "";


	KETMoldChangeOrder ketMoldChangeOrder = null;
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	try {
	    Kogger.biz("금형 ECO 수정 시작");
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);
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


	    // 변경 전
	    ketMoldChangeOrder.setWebEditor(this.webEditor);
	    ketMoldChangeOrder.setWebEditorText(this.webEditorText);
	    // 변경 후
	    ketMoldChangeOrder.setWebEditor1(this.webEditor1);
	    ketMoldChangeOrder.setWebEditorText1(this.webEditorText1);


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

	    ketMoldChangeOrderVO.setDeleteRelEpmList(deleteRelEpmList);//삭제대상 관련도면oid
	    ketMoldChangeOrderVO.setDeleteRelBomList(deleteRelEpmList);//삭제대상 관련BOMoid
	    ketMoldChangeOrderVO.setDeleteRelDocList(deleteRelDocList);//삭제대상 관련문서oid

	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(createKETMoldEcoEcrLink());//연계 ECR
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(createKETMoldEcoProdEcoLink());//연계 제품ECO
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(createKETMoldECOPartLink());//관련부품

	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List
	    //            if( relEcaEpmOid == null && relEcaDocOid == null )
	    //            {
	    //                ketMoldECALinkVOList = createKetMoldECALinkVOForEcoWorker(ketMoldECALinkVOList);//ECO담당자 VO생성
	    //            }
	    ketMoldECALinkVOList = createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);//표준품목록 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);//도면/BOM목록 VO생성
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);//도면/BOM/표준품목록 VO생성

	    ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoInfo(ketMoldChangeOrderVO);


	    //문서/도면/BOM 자료가 없는 담당자는 삭제한다. 단, ECO담당자는 유지해야 한다.
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
	    
	    KogDbUtil.log("금형ECO 수정", "Success", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), (String)null, null, null);

	} catch (WTException wte) {
	    Kogger.biz("금형 ECO 수정 실패");
	    Kogger.error(getClass(), wte);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");
	    
	    KogDbUtil.log("금형ECO 수정", "Fail", oid, wte, ERROR_MESSAGE, null);

	} catch (Exception e) {
	    Kogger.biz("금형 ECO 수정 실패");
	    Kogger.error(getClass(), e);

	    // 에러 메세지 처리
	    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
	    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");
	    
	    KogDbUtil.log("금형ECO 수정", "Fail", oid, e, ERROR_MESSAGE, null);
	}


	if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {

	    // 에러 메세지 처리
	    try {

		response.setContentType("text/html;charset=KSC5601");

		PrintWriter out = response.getWriter();
		StringBuffer rtn_msg = new StringBuffer("");

		rtn_msg.append("\n <script language=\"javascript\">");

		rtn_msg.append("\n   parent.lfn_feedbackAfterSave(\"" + ERROR_MESSAGE + "\");");

		rtn_msg.append("\n </script>");

		out.println(rtn_msg);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);

	    }

	}
	else {
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), "Update");

	}

    }//end-of-update

    /**
     * 
     * 함수명 : cancel 설명 : 금형 ECO 등록/수정 화면내 취소 버튼 클릭시 호출된다. 금형 ECO 상세조회 화면으로 분기한다.(금형ECO등록/수정 -> 금형 ECO 상세조회)
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
	alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", oid, prePage);
    }//end-of-cancel

    /**
     * 
     * 함수명 : complete 설명 : 금형 ECO 상세조회 등록완료 버튼 클릭시 호출된다. 금형 ECO 결재 상태를 EXCUTEACTIVITY=활동수행으로 변경한다. 금형 ECO와 관련된 모든 ECA 결재상태를 INWORK=작업중으로
     * 변경한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    //등록완료
    private void complete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeOrder ketMoldChangeOrder = null;
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();

	try {
	    
	    Kogger.biz("금형 ECO 등록완료 시작");
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);


	    /*
	    ECA 없이 등록완료를 할 경우 도면으로 ECO담당자를 담당자로 하여 빈 껍데기를 만들고 있다. 왜?
	    */
	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List
	    if (hasECA.equals("N")) {
		ecoWorkerId = ketMoldChangeOrder.getEcoWorkerId();
		ketMoldECALinkVOList = createKetMoldECALinkVOForEcoWorker(ketMoldECALinkVOList);//ECO담당자 VO생성
	    }

	    oid = KETEcmHelper.service.completeRegistMoldEco(ketMoldChangeOrder, ketMoldECALinkVOList);
	    
	    KogDbUtil.log("금형ECO 등록완료","Success", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), (String)null, null, null);
	    
	    prePage = "Complete";
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), prePage);
	    
	} catch (Exception e1) {
	    
	    Kogger.biz("금형 ECO 등록완료 실패");
	    alert(response, "등록완료 처리시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	    KogDbUtil.log("금형ECO 등록완료","Fail", oid, e1, null, null);
	}
	
    }//end-of-complete

    /**
     * 
     * 함수명 : delete 설명 : 금형 ECO를 삭제한다.
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
	    Kogger.biz("금형 ECO 삭제 시작");
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);
	    ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);
	    KETEcmHelper.service.deleteMoldEcoInfo(ketMoldChangeOrderVO);

	    KogDbUtil.log("금형ECO 삭제", "Success", oid, (String)null, null, null);
	    /*
	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchEcoForm.jsp", "search", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), "Delete");
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
		Kogger.biz("금형 ECO 삭제시 오류");
		Kogger.error(getClass(), e);

	    }


	} catch (Exception e1) {
	    Kogger.biz("금형 ECO 삭제 실패");
	    alert(response, "삭제시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	    KogDbUtil.log("금형ECO 삭제", "Fail", oid, e1, null, null);
	}
    }//end-of-delete

    @SuppressWarnings({ "rawtypes" })
    private void completeModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETMoldChangeOrder ketMoldChangeOrder = null;
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	try {
	    Kogger.biz("금형 ECO 활동 완료 후 저장");
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);
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


	    // 변경 전
	    ketMoldChangeOrder.setWebEditor(this.webEditor);
	    ketMoldChangeOrder.setWebEditorText(this.webEditorText);
	    // 변경 후
	    ketMoldChangeOrder.setWebEditor1(this.webEditor1);
	    ketMoldChangeOrder.setWebEditorText1(this.webEditorText1);


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

	    //            ketMoldChangeOrderVO.setDeleteRelEpmList(deleteRelEpmList);//삭제대상 관련도면oid
	    //            ketMoldChangeOrderVO.setDeleteRelBomList(deleteRelEpmList);//삭제대상 관련BOMoid
	    //            ketMoldChangeOrderVO.setDeleteRelDocList(deleteRelDocList);//삭제대상 관련문서oid

	    ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(createKETMoldEcoEcrLink());//연계 ECR
	    ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(createKETMoldEcoProdEcoLink());//연계 제품ECO
	    ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(createKETMoldECOPartLink());//관련부품


	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();//금형ECA List
	    //            ketMoldECALinkVOList = createKetMoldECALinkVOForEcoWorker(ketMoldECALinkVOList);//ECO담당자 VO생성
	    ketMoldECALinkVOList = createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);//표준품목록 VO생성
	    //            ketMoldECALinkVOList = createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);//도면/BOM목록 VO생성
	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);//도면/BOM/표준품목록 VO생성

	    ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoInfo(ketMoldChangeOrderVO);

	    KogDbUtil.log("금형ECO 활동완료 후 저장", "Success", oid, (String)null, null, null);
	    //            //문서/도면/BOM 자료가 없는 담당자는 삭제한다. 단, ECO담당자는 유지해야 한다.
	    //            //관련ECA 삭제 제한(ECO 상태가 계획수립인 경우에만 삭제 가능하다.)
	    //            if(ketMoldChangeOrderVO.getKetMoldChangeOrder().getLifeCycleState().getStringValue().indexOf("PLANNING") > -1) {
	    //                MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();
	    //                ArrayList deleteRelEcaList = moldChangeOrderDao.getMoldECADeleteList(ketMoldChangeOrderVO);//삭제대상 ECA조회
	    //                ketMoldChangeOrderVO.setDeleteRelEcaList(deleteRelEcaList);
	    //                ArrayList deleteMoldPlanList = moldChangeOrderDao.getMoldPlanDeleteList(ketMoldChangeOrderVO);//삭제대상 금형변경일정조회
	    //                ketMoldChangeOrderVO.setDeleteMoldPlanList(deleteMoldPlanList);
	    //                ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoActivityInfo(ketMoldChangeOrderVO);
	    //            }

	    alertNgo(response, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/MoldEcoServlet", "view", CommonUtil.getOIDString(ketMoldChangeOrderVO.getKetMoldChangeOrder()), "CompleteModify");
	} catch (Exception e1) {
	    Kogger.biz("금형 ECO 활동완료 후 저장 실패");
	    alert(response, "저장시 오류가 발생하였습니다.");
	    Kogger.error(getClass(), e1);
	    KogDbUtil.log("금형ECO 활동완료 후 저장 실패", "Success", oid, e1, null, null);
	}
    }//end-of-update

    private void excel(HttpServletRequest request, HttpServletResponse response, String cmd) throws ServletException, IOException {
	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();
	KETMoldChangeOrder ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(oid);//ECO객체조회
	ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);//ECO객체 VO세팅
	ketMoldChangeOrderVO.setTotalCount(1);//조회여부 VO세팅
	ketMoldChangeOrderVO.setOid(CommonUtil.getOIDLongValue(oid) + "");//ECO oid VO세팅
	MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();
	try {
	    //금형ECA 상세조회
	    ketMoldChangeOrderVO = moldChangeOrderDao.searchMoldEcaAll(ketMoldChangeOrderVO, cmd);
	    ketMoldChangeOrderVO = moldChangeOrderDao.getStdPartList(ketMoldChangeOrderVO, oid);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	request.setAttribute("data", ketMoldChangeOrderVO);

	if (cmd.equalsIgnoreCase("epmExcel")) {
	    gotoResult(request, response, "/jsp/ecm/ExcelMoldEcoEpmDocList.jsp");
	}
	else if (cmd.equalsIgnoreCase("bomExcel")) {
	    gotoResult(request, response, "/jsp/ecm/ExcelMoldEcoBomList.jsp");
	}
	else if (cmd.equalsIgnoreCase("stdPartExcel")) {
	    gotoResult(request, response, "/jsp/ecm/ExcelMoldEcoStdPartList.jsp");
	}
    }

}
