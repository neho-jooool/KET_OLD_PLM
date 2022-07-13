/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : KETProdEcoServlet.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 29.
 */
package e3ps.ecm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.HolderToContent;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.representation.Representation;
import wt.util.WTException;
import wt.util.WTProperties;

import com.ptc.wpcfg.hadb.ContentDownload;
import com.ptc.wvs.server.util.PublishUtils;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : KETProdEcoServlet 설명 : 작성자 : 오승연 작성일자 : 2010. 10. 29.
 */
public class KETProdEcoServlet extends CommonServlet {

    /*
     * (non-Javadoc)
     * 
     * @see e3ps.common.web.CommonServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	RequestDispatcher rd = null;

	String ALERT_MESSAGE = "";
	String ERROR_MESSAGE = "";

	String command = "";
	String tabName = "";

	Vector attachFiles = null;
	Hashtable<String, String> reqData = new Hashtable<String, String>();
	String[] ecrList = null;

	ArrayList<Hashtable<String, String>> refPartList = new ArrayList<Hashtable<String, String>>();
	String[] partList = null;
	String[] dieNoList = null;
	String[] budgetFlagList = null;
	String[] expectCostList = null;

	Hashtable<String, ArrayList<Hashtable<String, String>>> refObjHash = null;
	ArrayList<Hashtable<String, String>> refEpmDocList = new ArrayList<Hashtable<String, String>>();
	ArrayList<Hashtable<String, String>> refBomList = new ArrayList<Hashtable<String, String>>();
	String[] actTypeList = null;
	String[] refObjLinkList = null;
	String[] refObjList = null;
	String[] refObjVersionList = null;
	String[] refObjAfterVersionList = null;
	String[] ecaWorkerOidList = null;
	String[] massChgYnList = null;
	String[] parentPartList = null;
	String[] ecaPlanDateList = null;
	String[] ecaCommentList = null;
	String[] moldPlanDieNoList = null;
	String[] moldPlanIdList = null;
	String[] epmDocTypeList = null;

	// Die No
	String[] relDieNoEcaList = null;
	String[] expectCostEcaList = null;
	String[] secureBudgetYnEcaList = null;
	String[] budgetEcaList = null;

	Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash = null;
	Hashtable<String, ArrayList<String>> delObjListHash = null;

	ArrayList<Hashtable<String, String>> refDocList = new ArrayList<Hashtable<String, String>>();

	String prePageFlag = "";
	String activityType = "";
	String ecoOid = "";

	// 품질문제
	ArrayList<Hashtable<String, String>> refDqmList = new ArrayList<Hashtable<String, String>>();
	String[] dqmList = null;

	KETProdChangeOrder prodEco = null;
	ProdEcoBeans beans = new ProdEcoBeans();

	String contentType = req.getContentType();

	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {

	    command = paramMap.getString("cmd");
	    tabName = paramMap.getString("tabName");
	    req.setAttribute("tabName", tabName);
	    activityType = paramMap.getString("ecaType");
	    req.setAttribute("ecaType", activityType);

	    if (command.equalsIgnoreCase("Create")) {

		try {

		    Kogger.biz("제품 ECO 등록 시작");

		    reqData.put("eco_name", paramMap.getString("eco_name"));
		    reqData.put("dev_yn", paramMap.getString("dev_yn"));
		    reqData.put("div_flag", paramMap.getString("div_flag"));
		    reqData.put("project_oid", paramMap.getString("project_oid"));
		    reqData.put("domestic_yn", paramMap.getString("domestic_yn"));
		    reqData.put("car_maker", paramMap.getString("car_maker"));
		    reqData.put("car_category", paramMap.getString("car_category"));
		    reqData.put("change_reason", paramMap.getString("changeReason")); // 설계변경 사유
		    reqData.put("other_reason", paramMap.getString("other_reason")); // 기타 Description
		    reqData.put("custom_flag", paramMap.getString("custom_flag")); // 고객확인 구분
		    reqData.put("other_cust_flag", paramMap.getString("other_cust_flag")); // 기타 고객확인 Desc
		    reqData.put("change_fact", paramMap.getString("chg_fact")); // 조정 및 변경사항
		    reqData.put("change_cu_dwg", paramMap.getString("chk_cu")); // CU 도면 변경여부
		    reqData.put("ecoApplyPoint", paramMap.getString("ecoApplyPoint")); // ECO적용시점
		    reqData.put("effective_date", paramMap.getString("effective_date")); // 적용시기
		    reqData.put("inventory_process", paramMap.getString("inv_process")); // 재고처리

		    reqData.put("changeType", paramMap.getString("changeType")); // 설계변경 유형
		    reqData.put("reviewResult", paramMap.getString("reviewResult")); // 검토결과

		    reqData.put("design_guide_yn", paramMap.getString("chk_design_guide")); // 설계가이드 반영
		    reqData.put("design_sheet_yn", paramMap.getString("chk_design_sheet")); // 설계검증체크시트 반영

		    reqData.put("defectDiv", paramMap.getString("defectDiv")); // 불량구분 코드
		    reqData.put("defectType", paramMap.getString("defectType")); // 불량유형 코드
		    reqData.put("defectDivName", paramMap.getString("defectDivName")); // 불량구분
		    reqData.put("defectTypeName", paramMap.getString("defectTypeName")); // 불량유형

		    reqData.put("costChange", paramMap.getString("costChange")); // 원가변동
		    reqData.put("costVariation", paramMap.getString("costVariation")); // 원가증감비율(수주대비)

		    reqData.put("point_yn", paramMap.getString("chk_point")); // 중요포인트 반영/변경
		    reqData.put("spec_changet_yn", paramMap.getString("chk_spec_change")); // 사양변경 식별표기

		    reqData.put("webEditor", paramMap.getString("webEditor")); // 변경 전
		    reqData.put("webEditorText", paramMap.getString("webEditorText")); // 변경 전
		    reqData.put("webEditor1", paramMap.getString("webEditor1")); // 변경 후
		    reqData.put("webEditorText1", paramMap.getString("webEditorText1")); // 변경 후

		    // 프로젝트에서 산출물로 ECO 직접작성
		    // reqData.put("projectOid", paramMap.getString("projectOid")); // 프로젝트 OID
		    reqData.put("projectOutputOid", paramMap.getString("projectOutputOid")); // 프로젝트 - 산출물 관리 OID

		    attachFiles = uploader.getFiles();

		    ecrList = paramMap.getStringArray("relEcrOid");// 관련ECRoid 추가목록

		    partList = paramMap.getStringArray("relPartOid");// 관련부품oid 추가목록
		    dieNoList = paramMap.getStringArray("relDieNo"); // 관련 dieno
		    budgetFlagList = paramMap.getStringArray("secureBudgetYn");// 비용확보여부
		    expectCostList = paramMap.getStringArray("expectCost");// 예상비용
		    refPartList = beans.getRefPartList(partList, dieNoList, expectCostList, budgetFlagList);

		    // 도면,BOM 활동계획
		    actTypeList = paramMap.getStringArray("relEcaActivityType");// 활동계획구분(1:도면, 2:BOM)
		    refObjList = paramMap.getStringArray("relObjOid");// 관련 BOM/EPMDoc Oid
		    refObjVersionList = paramMap.getStringArray("relObjPreRev"); // 관련 도면/BOM 변경전 버전
		    ecaWorkerOidList = paramMap.getStringArray("relEcaWorkerOid"); // 활동 도면/BOM담당자 oid
		    massChgYnList = paramMap.getStringArray("masschange_yn"); // BOM 일괄변경 여부
		    parentPartList = paramMap.getStringArray("parentPart"); // BOM 일괄변경 모부품
		    ecaPlanDateList = paramMap.getStringArray("relEcaPlanDate"); // 관련 활동 변경예정일
		    ecaCommentList = paramMap.getStringArray("relEcaActivityComment");// 관련활동변경내용
		    moldPlanDieNoList = paramMap.getStringArray("dieNo");// 금형 변경일정 Die No
		    moldPlanIdList = paramMap.getStringArray("scheduleId");// 금형변경일정 Id
		    epmDocTypeList = paramMap.getStringArray("relEcaEpmDocType");// 도면 Type

		    // Die No
		    relDieNoEcaList = paramMap.getStringArray("relDieNo_eca");
		    expectCostEcaList = paramMap.getStringArray("expectCost_eca");
		    secureBudgetYnEcaList = paramMap.getStringArray("secureBudgetYn_eca");
		    budgetEcaList = paramMap.getStringArray("budget_eca");

		    /*
	             * refObjHash = beans.getRefObjList(actTypeList, refObjList, refObjVersionList, ecaWorkerOidList, massChgYnList,
	             * parentPartList, ecaPlanDateList, ecaCommentList, moldPlanDieNoList, moldPlanIdList, epmDocTypeList);
	             */

		    // 품질문제
		    dqmList = paramMap.getStringArray("relDqmOid");
		    int dqmListLength = (dqmList != null) ? dqmList.length : 0;
		    for (int i = 0; i < dqmListLength; i++) {
			String relDqmOid = StringUtils.stripToEmpty(dqmList[i]);
			if (!relDqmOid.equals("")) {
			    Hashtable<String, String> dqmHash = new Hashtable<String, String>();
			    dqmHash.put("relDqmOid", relDqmOid);
			    refDqmList.add(dqmHash);
			}
		    }

		    Hashtable<String, ArrayList<Hashtable<String, String>>> objHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();
		    ArrayList<Hashtable<String, String>> epmDocList = new ArrayList<Hashtable<String, String>>();
		    ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
		    Hashtable<String, String> epmDoc = null;
		    Hashtable<String, String> bom = null;

		    if (actTypeList != null && actTypeList.length > 0) {
			for (int oCnt = 0; oCnt < actTypeList.length; oCnt++) {
			    // 도면
			    if (actTypeList[oCnt].equals("1")) {
				epmDoc = new Hashtable<String, String>();
				if (refObjList != null && refObjList.length > 0)
				    epmDoc.put("epmDocOid", refObjList[oCnt]);
				if (refObjVersionList != null && refObjVersionList.length > 0)
				    epmDoc.put("preVersion", refObjVersionList[oCnt]);
				if (ecaWorkerOidList != null && ecaWorkerOidList.length > 0)
				    epmDoc.put("workerOid", ecaWorkerOidList[oCnt]);
				if (ecaPlanDateList != null && ecaPlanDateList.length > 0)
				    epmDoc.put("planDate", StringUtil.checkNull(ecaPlanDateList[oCnt]));
				if (ecaCommentList != null && ecaCommentList.length > 0)
				    epmDoc.put("comment", StringUtil.checkNull(ecaCommentList[oCnt]));
				if (moldPlanDieNoList != null && moldPlanDieNoList.length > 0)
				    epmDoc.put("dieNo", StringUtil.checkNull(moldPlanDieNoList[oCnt]));
				if (moldPlanIdList != null && moldPlanIdList.length > 0)
				    epmDoc.put("scheduleId", StringUtil.checkNull(moldPlanIdList[oCnt]));
				if (epmDocTypeList != null && epmDocTypeList.length > 0)
				    epmDoc.put("epmDocType", StringUtil.checkNull(epmDocTypeList[oCnt]));

				epmDocList.add(epmDoc);
			    }
			    // BOM
			    else {
				bom = new Hashtable<String, String>();
				if (refObjList != null && refObjList.length > 0)
				    bom.put("partOid", refObjList[oCnt]);
				if (refObjVersionList != null && refObjVersionList.length > 0)
				    bom.put("preVersion", refObjVersionList[oCnt]);
				if (ecaWorkerOidList != null && ecaWorkerOidList.length > 0)
				    bom.put("workerOid", ecaWorkerOidList[oCnt]);
				if (massChgYnList != null && massChgYnList.length > 0)
				    bom.put("massChgYn", massChgYnList[oCnt]);
				if (parentPartList != null && parentPartList.length > 0)
				    bom.put("parentPartList", parentPartList[oCnt]);
				if (ecaPlanDateList != null && ecaPlanDateList.length > 0)
				    bom.put("planDate", StringUtil.checkNull(ecaPlanDateList[oCnt]));
				if (ecaCommentList != null && ecaCommentList.length > 0)
				    bom.put("comment", StringUtil.checkNull(ecaCommentList[oCnt]));
				if (refObjList != null && refObjList.length > 0)
				    bom.put("beforPartOid", refObjList[oCnt]);

				if (relDieNoEcaList != null && relDieNoEcaList.length > 0)
				    bom.put("relDieNoEca", StringUtil.checkNull(relDieNoEcaList[oCnt]));
				if (expectCostEcaList != null && expectCostEcaList.length > 0)
				    bom.put("expectCostEca", StringUtil.checkNull(expectCostEcaList[oCnt]));
				if (secureBudgetYnEcaList != null && secureBudgetYnEcaList.length > 0)
				    bom.put("secureBudgetYnEca", secureBudgetYnEcaList[oCnt]);

				bom.put("linkOid", "");

				bomList.add(bom);
			    }
			}

			objHash.put("refEpmDocList", epmDocList);
			objHash.put("refBomList", bomList);
		    }

		    refEpmDocList = objHash.get("refEpmDocList");
		    refBomList = objHash.get("refBomList");

		    // 문서 활동계획
		    refDocList = beans.getRefDocList(paramMap.getString("doc_list"));

		    reqData.put("eco_id", beans.getNewProdEcoId());
		    ecoOid = KETEcmHelper.service.createProdEco(reqData, ecrList, refPartList, refEpmDocList, refBomList, refDocList,
			    refDqmList, attachFiles);

		    KogDbUtil.log("제품ECO 등록", "Success", ecoOid, (String) null, null, null);
		} catch (WTException wte) {
		    Kogger.biz("제품 ECO 등록 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 등록", "Fail", (String) ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO 등록 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 등록", "Fail", (String) ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";

		}

		Kogger.biz("제품 ECO 등록 종료");

	    }
	    // 저장
	    else if (command.equalsIgnoreCase("Modify")) {
		ecoOid = paramMap.getString("oid");
		prePageFlag = paramMap.getString("prePage");

		try {

		    Kogger.biz("제품 ECO 수정 저장 시작");
		    attachFiles = uploader.getFiles();

		    ALERT_MESSAGE = KETEcmHelper.service.modifyProdEco(ecoOid, paramMap, attachFiles);

		    KogDbUtil.log("제품ECO 수정", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECO 수정 저장 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 수정", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO 수정 저장 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 수정", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO 수정 저장 종료");

	    } else if (command.equalsIgnoreCase("ModifyNotOwner")) {

		Kogger.biz("제품 ECO ModifyNotOwner(ECN TO-DO 저장) 시작");

		ecoOid = paramMap.getString("oid");
		prePageFlag = paramMap.getString("prePage");

		reqData.put("delBomList", paramMap.getString("deleteRelBomList"));
		reqData.put("delEpmDocList", paramMap.getString("deleteRelEpmList"));
		reqData.put("deleteRelDocList", paramMap.getString("deleteRelDocList"));

		// 도면,BOM 활동계획
		actTypeList = paramMap.getStringArray("relEcaActivityType");// 활동계획구분(1:도면, 2:BOM)
		refObjLinkList = paramMap.getStringArray("relEcaLinkOid"); // 관련Link Oid
		refObjList = paramMap.getStringArray("relObjOid");// 관련 BOM/EPMDoc Oid
		refObjVersionList = paramMap.getStringArray("relObjPreRev"); // 관련 도면/BOM 변경전 버전
		refObjAfterVersionList = paramMap.getStringArray("relObjAfterRev"); // 관련 도면/BOM/문서 변경후 버전
		ecaWorkerOidList = paramMap.getStringArray("relEcaWorkerOid"); // 활동 도면/BOM담당자 oid
		massChgYnList = paramMap.getStringArray("masschange_yn"); // BOM 일괄변경 여부
		parentPartList = paramMap.getStringArray("parentPart"); // BOM 일괄변경 모부품
		ecaPlanDateList = paramMap.getStringArray("relEcaPlanDate"); // 관련 활동 변경예정일
		ecaCommentList = paramMap.getStringArray("relEcaActivityComment");// 관련활동변경내용
		moldPlanDieNoList = paramMap.getStringArray("dieNo");// 금형 변경일정 Die No
		moldPlanIdList = paramMap.getStringArray("scheduleId");// 금형변경일정 Id
		epmDocTypeList = paramMap.getStringArray("relEcaEpmDocType");// 도면 Type
		refObjHash = beans.getAddRefObjList(actTypeList, refObjLinkList, refObjList, refObjVersionList, refObjAfterVersionList,
		        ecaWorkerOidList, massChgYnList, parentPartList, ecaPlanDateList, ecaCommentList, moldPlanDieNoList,
		        moldPlanIdList, epmDocTypeList);

		refEpmDocList = refObjHash.get("refEpmDocList");
		refBomList = refObjHash.get("refBomList");
		refDocList = refObjHash.get("refDocList");
		// 문서 활동계획
		refDocList = beans.getRefDocList(paramMap.getString("doc_list"));

		// 추가해야 할 Object List Hashtable에 추가
		addObjListHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();

		if (refEpmDocList != null && activityType.equals("1")) {
		    addObjListHash.put("refEpmDocList", refEpmDocList);
		}
		if (refBomList != null && activityType.equals("2")) {
		    addObjListHash.put("refBomList", refBomList);
		}
		if (refDocList != null && (activityType.equals("3") || activityType.equals("4"))) {
		    addObjListHash.put("refDocList", refDocList);
		}

		try {
		    ReferenceFactory rf = new ReferenceFactory();
		    prodEco = (KETProdChangeOrder) rf.getReference(ecoOid).getObject();

		    // 제거해야할 Object List Hashtable에 추가
		    delObjListHash = beans.getAllDelObjLinkOidListHash(KETObjectUtil.getIda2a2(prodEco), reqData.get("delBomList"),
			    reqData.get("delEpmDocList"), reqData.get("deleteRelDocList"), false);

		    /*
	             * ecoOid = KETEcmHelper.service.modifyProdEcoNotOwner(ecoOid, addObjListHash, delObjListHash, activityType);
	             */
		    ALERT_MESSAGE = KETEcmHelper.service.modifyProdEcoNotOwner2(ecoOid, addObjListHash, delObjListHash, activityType,
			    paramMap);

		    KogDbUtil.log("제품ECO ModifyNotOwner(ECN TO-DO 저장)", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECO ModifyNotOwner(ECN TO-DO 저장) 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO ModifyNotOwner(ECN TO-DO 저장)", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO ModifyNotOwner(ECN TO-DO 저장) 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO ModifyNotOwner(ECN TO-DO 저장)", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";

		}

		Kogger.biz("제품 ECO ModifyNotOwner(ECN TO-DO 저장) 종료");

	    }
	    // 작업완료 : TO-DO에서 작업완료 버튼 누름
	    else if (command.equalsIgnoreCase("Complete")) {

		Kogger.biz("제품 ECO TO-DO 작업완료 시작");

		ecoOid = paramMap.getString("oid");
		String type = "";
		try {

		    // 1412 이전 ECO일 경우 유효성 검증은 하지 아니한다.
		    boolean isOldEco = false;
		    KETProdChangeOrder eco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
		    String ecoId = eco.getEcoId();
		    if (Integer.parseInt(ecoId.substring(4, 8)) < 1412) {
			isOldEco = true;
		    }
		    type = paramMap.getString("activityType");

		    if (!isOldEco && !"4".equals(type)) {// ECN작업활동은 검증안함 2017.06.23 by 황정태
			ALERT_MESSAGE = KETEcmHelper.service.validateBeforeCompleteReg(ecoOid, paramMap);
			if (!"".equals(ALERT_MESSAGE)) {
			    throw new WTException(ALERT_MESSAGE);
			}
		    }
		    ALERT_MESSAGE = KETEcmHelper.service.completeInProdEco(ecoOid, paramMap);

		    if ("4".equals(type) && StringUtils.isNotEmpty(ALERT_MESSAGE)) {
			ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
			ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("\"", "\\\\\"");
		    }

		    KogDbUtil.log("제품ECO TO-DO 작업완료", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECO TO-DO 작업완료 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO TO-DO 작업완료", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO TO-DO 작업완료 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO TO-DO 작업완료", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO TO-DO 작업완료 종료");

	    }
	    // 도면 개정
	    else if (command.equalsIgnoreCase("ReviseEpmDoc")) {

		Kogger.biz("제품 ECO 도면 개정 시작");

		ecoOid = paramMap.getString("oid");

		refObjList = paramMap.getStringArray("relObjOid");// 관련 EPMDoc Oid

		String[] refEpmDocLinkList = null;
		refEpmDocLinkList = paramMap.getStringArray("relEcaLinkOid");// 관련 EPMDoc LinkOid

		String[] reviseChkList = null;
		reviseChkList = paramMap.getStringArray("relEcaEpmDocReviseYn"); // 개정 여부 flag list

		epmDocTypeList = paramMap.getStringArray("relEcaEpmDocType"); // 개정 여부 flag list

		try {
		    ecoOid = KETEcmHelper.service.reviseEpmDocInProdEco(ecoOid, refEpmDocLinkList, refObjList, reviseChkList,
			    epmDocTypeList);

		    KogDbUtil.log("제품ECO 도면개정", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECO 도면 개정 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 도면개정", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO 도면 개정 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 도면개정", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO 도면 개정 종료");
	    }
	    // 도면 개정취소
	    else if (command.equalsIgnoreCase("CancelReviseEpmDoc")) {

		Kogger.biz("제품 ECO 도면 개정 취소 시작");

		ecoOid = paramMap.getString("oid");

		refObjList = paramMap.getStringArray("relObjOid");// 관련 EPMDoc Oid

		String[] refEpmDocLinkList = null;
		refEpmDocLinkList = paramMap.getStringArray("relEcaLinkOid");// 관련 EPMDoc LinkOid

		String[] cancelChkList = null;
		cancelChkList = paramMap.getStringArray("relEcaEpmDocCancelRevYn"); // 개정 여부 flag list

		try {

		    ecoOid = KETEcmHelper.service.cancelReviseEpmDocInProdEco(ecoOid, refEpmDocLinkList, refObjList, cancelChkList);

		    KogDbUtil.log("제품ECO 도면개정취소", "Success", ecoOid, (String) null, null, null);

		} catch (WTException e) {
		    Kogger.biz("제품 ECO 도면 개정 취소 실패");
		    Kogger.error(getClass(), e);
		    KogDbUtil.log("제품ECO 도면개정취소", "Fail", ecoOid, e, null, null);
		}

		Kogger.biz("제품 ECO 도면 개정 취소 종료");

	    }
	    // 도면 변경
	    else if (command.equalsIgnoreCase("EpmDocChanged")) {

		Kogger.biz("제품 ECO 도면 변경 시작");

		ecoOid = paramMap.getString("oid");

		String[] refEpmDocLinkList = null;
		refEpmDocLinkList = paramMap.getStringArray("relEcaLinkOid");// 관련 EPMDoc LinkOid

		String[] chgFlagList = null;
		chgFlagList = paramMap.getStringArray("relEcaEpmChangeYn"); // Change 여부

		try {

		    ecoOid = KETEcmHelper.service.changeEpmDoc(ecoOid, refEpmDocLinkList, chgFlagList);

		    KogDbUtil.log("제품ECO 도면변경", "Success", ecoOid, (String) null, null, null);

		} catch (WTException e) {

		    Kogger.biz("제품 ECO 도면 변경 실패");
		    Kogger.error(getClass(), e);
		    KogDbUtil.log("제품ECO 도면변경", "Fail", ecoOid, e, null, null);
		}

		Kogger.biz("제품 ECO 도면 변경 종료");

	    }
	    // BOM 개정
	    else if (command.equalsIgnoreCase("ReviseBom")) {

		Kogger.biz("제품 ECO 부품(BOM) 개정 시작");

		ecoOid = paramMap.getString("oid");

		/*
	         * String partNo = paramMap.getString("bomPartNo");// 관련 BOM Oid String bomLinkOid = paramMap.getString("bomLinkOid");// 관련
	         * EPMDoc LinkOid
	         * 
	         * String bomReviseYn = paramMap.getString("bomReviseYn"); // 개정 여부 flag list String bomMassChgYn =
	         * paramMap.getString("bomMassChange"); // 일괄개정 flag list String parentPartListStr =
	         * paramMap.getString("relatedParentPart"); // 관련모부품 list
	         */
		try {
		    /*
	             * ecoOid = KETEcmHelper.service.reviseBomInProdEco(ecoOid, bomLinkOid, partNo, bomReviseYn, bomMassChgYn,
	             * parentPartListStr);
	             */
		    ALERT_MESSAGE = KETEcmHelper.service.reviseBomInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 부품(BOM) 개정", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("제품 ECO 부품(BOM) 개정 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 부품(BOM) 개정", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {

		    Kogger.biz("제품 ECO 부품(BOM) 개정 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 부품(BOM) 개정", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO 부품(BOM) 개정 종료");

	    }
	    // BOM 개정취소
	    else if (command.equalsIgnoreCase("CancelReviseBom")) {

		Kogger.biz("제품 ECO 부품(BOM) 개정 취소 시작");

		ecoOid = paramMap.getString("oid");

		/*
	         * String partNo = paramMap.getString("bomPartNo");// 관련 BOM Oid String bomLinkOid = paramMap.getString("bomLinkOid");// 관련
	         * EPMDoc LinkOid
	         * 
	         * String bomReviseYn = paramMap.getString("bomReviseYn"); // 개정 여부 flag list String bomMassChgYn =
	         * paramMap.getString("bomMassChange"); // 일괄개정 flag list String parentPartListStr =
	         * paramMap.getString("relatedParentPart"); // 관련모부품 list
	         */
		try {
		    /*
	             * ecoOid = KETEcmHelper.service.cancelReviseBomInProdEco(ecoOid, bomLinkOid, partNo, bomReviseYn, bomMassChgYn,
	             * parentPartListStr);
	             */
		    ALERT_MESSAGE = KETEcmHelper.service.cancelReviseBomInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 부품(BOM) 개정취소", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("제품 ECO 부품(BOM) 개정 취소 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 부품(BOM) 개정취소", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {

		    Kogger.biz("제품 ECO 부품(BOM) 개정 취소 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 부품(BOM) 개정취소", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO 부품(BOM) 개정 취소 종료");

	    } else if (command.equalsIgnoreCase("CompleteModify")) { // 사용안함

		Kogger.biz("제품 ECO CompleteModify(기본정보수정??) 시작");

		ecoOid = paramMap.getString("oid");
		prePageFlag = paramMap.getString("prePage");

		reqData.put("eco_oid", ecoOid);
		reqData.put("eco_name", paramMap.getString("eco_name"));
		reqData.put("dev_yn", paramMap.getString("dev_yn"));
		reqData.put("div_flag", paramMap.getString("div_flag"));
		reqData.put("project_oid", paramMap.getString("project_oid"));
		reqData.put("domestic_yn", paramMap.getString("domestic_yn"));
		reqData.put("car_maker", paramMap.getString("car_maker"));
		reqData.put("car_category", paramMap.getString("car_category"));
		reqData.put("change_reason", paramMap.getString("changeReason")); // 설계변경 사유
		reqData.put("other_reason", paramMap.getString("other_reason")); // 기타 Description
		reqData.put("custom_flag", paramMap.getString("custom_flag")); // 고객확인 구분
		reqData.put("other_cust_flag", paramMap.getString("other_cust_flag")); // 기타 고객확인 Desc
		reqData.put("change_fact", paramMap.getString("chg_fact")); // 조정 및 변경사항
		reqData.put("change_cu_dwg", paramMap.getString("chk_cu")); // CU 도면 변경여부
		reqData.put("ecoApplyPoint", paramMap.getString("ecoApplyPoint")); // ECO적용시점
		reqData.put("effective_date", paramMap.getString("effective_date")); // 적용시기
		reqData.put("inventory_process", paramMap.getString("inv_process")); // 재고처리
		reqData.put("delFileList", paramMap.getString("deleteFileList"));

		reqData.put("activityType", activityType);

		attachFiles = uploader.getFiles();

		ecrList = paramMap.getStringArray("relEcrOid");// 관련ECRoid 추가목록

		partList = paramMap.getStringArray("relPartOid");// 관련부품oid 추가목록
		dieNoList = paramMap.getStringArray("relDieNo"); // 관련 dieno
		budgetFlagList = paramMap.getStringArray("secureBudgetYn");// 비용확보여부
		expectCostList = paramMap.getStringArray("expectCost");// 예상비용
		refPartList = beans.getRefPartList(partList, dieNoList, expectCostList, budgetFlagList);

		try {
		    prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);
		    delObjListHash = beans.getAllDelObjLinkOidListHash(KETObjectUtil.getIda2a2(prodEco), reqData.get("delBomList"),
			    reqData.get("delEpmDocList"), reqData.get("deleteRelDocList"), false);
		    ecoOid = KETEcmHelper.service.modifyProdEcoBasic(reqData, ecrList, refPartList, delObjListHash, attachFiles);

		    KogDbUtil.log("제품ECO CompleteModify(기본정보수정??)", "Success", ecoOid, (String) null, null, null);

		} catch (Exception e) {

		    Kogger.biz("제품 ECO CompleteModify(기본정보수정??) 실패");
		    Kogger.error(getClass(), e);
		    KogDbUtil.log("제품ECO CompleteModify(기본정보수정??)", "Fail", ecoOid, e, null, null);
		}

		Kogger.biz("제품 ECO CompleteModify(기본정보수정??) 끝");

	    }
	    // 문서 개정
	    else if (command.equalsIgnoreCase("ReviseRelDoc")) {

		Kogger.biz("제품 ECN 문서 개정 시작");

		ecoOid = paramMap.getString("oid");

		try {

		    ALERT_MESSAGE = KETEcmHelper.service.reviseRelDocInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 문서개정", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("제품 ECN 문서 개정 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 문서개정", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECN 문서 개정 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 문서개정", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";

		}

		Kogger.biz("제품 ECN 문서 개정 종료");
	    }
	    // 문서 개정 취소
	    else if (command.equalsIgnoreCase("CancelReviseRelDoc")) {

		Kogger.biz("제품 ECN 문서 개정 취소 시작");

		ecoOid = paramMap.getString("oid");

		try {

		    ALERT_MESSAGE = KETEcmHelper.service.cancelReviseRelDocInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 문서개정취소", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("제품 ECN 문서 개정 취소 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 문서개정취소", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {

		    Kogger.biz("제품 ECN 문서 개정 취소 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 문서개정취소", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECN 문서 개정 취소 종료");

	    }
	    // 수신확인 : 사용안함
	    else if (command.equalsIgnoreCase("receiveConfirm")) {

		Kogger.biz("제품 ECO TODO 수신확인 시작");

		ecoOid = paramMap.getString("oid");

		try {
		    ALERT_MESSAGE = KETEcmHelper.service.receiveConfirmInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO TODO 수신확인", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECO TODO 수신확인 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO TODO 수신확인", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("제품 ECO TODO 수신확인 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO TODO 수신확인", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("제품 ECO TODO 수신확인 종료");
	    }

	    if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {

		// 에러 메세지 처리
		try {

		    res.setContentType("text/html;charset=KSC5601");

		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    rtn_msg.append("\n <script language=\"javascript\">");
		    if (ERROR_MESSAGE.indexOf("01651") != -1) {
			ERROR_MESSAGE = ERROR_MESSAGE.substring(0, ERROR_MESSAGE.lastIndexOf("01651"));
			rtn_msg.append("\n   	parent.lfn_feedback_reStart('" + ERROR_MESSAGE + "');");
		    } else {
			rtn_msg.append("\n   alert(\"" + ERROR_MESSAGE + "\");");
		    }

		    rtn_msg.append("\n   parent.hideProcessing();");
		    rtn_msg.append("\n   parent.enabledAllBtn();");

		    rtn_msg.append("\n </script>");

		    Kogger.biz("제품 ECO 에러메시지 종료");

		    out.println(rtn_msg);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    } else {

		Kogger.debug(getClass(), "ecoOid=======================>" + ecoOid);
		if (ecoOid != null && ecoOid.length() > 0) {

		    String msg = "";
		    if (!ALERT_MESSAGE.equals("")) {
			msg += "\\n" + ALERT_MESSAGE;
		    } else {
			msg = "정상적으로 처리되었습니다.";
		    }

		    if (command.equalsIgnoreCase("Complete")) {

			// alertGo(res, "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr", "정상적으로 처리되었습니다.");

			try {

			    // res.setContentType("text/html;charset=KSC5601");
			    //
			    // PrintWriter out = res.getWriter();
			    // StringBuffer rtn_msg = new StringBuffer("");
			    //
			    // rtn_msg.append("\n <script language=\"javascript\">");
			    // rtn_msg.append("\n   alert(\"" + msg + "\");");
			    //
			    // // rtn_msg.append("\n   parent.hideProcessing();");
			    // rtn_msg.append("\n   try {");
			    //
			    // /*
			    // * rtn_msg.append(
			    // * "\n   	parent.parent.opener.location.href = '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr';");
			    // * rtn_msg.append("\n   	parent.parent.window.close();");
			    // */
			    // rtn_msg.append("\n   	parent.lfn_feedback_After_Complete();");
			    //
			    // rtn_msg.append("\n   } catch(e) { alert(e); }");
			    // rtn_msg.append("\n </script>");
			    //
			    // out.println(rtn_msg);

			    res.setContentType("text/html;charset=KSC5601");
			    PrintWriter out = res.getWriter();
			    String rtn_msg = "";
			    rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='/plm/servlet/e3ps/ProdEcoServlet'>"
				    + "\n <input type='hidden' name='cmd' value='UpdateView'>"
				    + "\n <input type='hidden' name='oid' value='" + ecoOid + "'>"
				    + "\n <input type='hidden' name='ecaType' value='" + activityType + "'>"
				    + "\n <input type='hidden' name='preAction' value='" + "'>"
				    + "\n <input type='hidden' name='tabName' value='" + tabName + "'>"
				    + "\n </form>"
				    + "\n <script language=\"javascript\">"
				    // + "\n   parent.hideProcessing();"
				    // + "\n   parent.enabledAllBtn();"
				    + "\n   alert(\"" + msg + "\");"
				    + "\n   parent.opener.location.reload(); \n  document.frmProc.submit();" + "\n </script>";
			    out.println(rtn_msg);

			} catch (Exception e) {
			    Kogger.error(getClass(), e);

			}

		    } else if (command.equalsIgnoreCase("ReviseEpmDoc") || command.equalsIgnoreCase("CancelReviseEpmDoc")
			    || command.equalsIgnoreCase("EpmDocChanged") || command.equalsIgnoreCase("ReviseBom")
			    || command.equalsIgnoreCase("CancelReviseBom") || command.equalsIgnoreCase("ReviseRelDoc")
			    || command.equalsIgnoreCase("CancelReviseRelDoc") || command.equalsIgnoreCase("receiveConfirm")) {
			alertNToDo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, activityType, "R", tabName);
		    } else {
			if (prePageFlag.equalsIgnoreCase("ToDo")) {
			    alertNToDo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, activityType, "U", tabName);
			} else {
			    alertNgo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "CN", tabName);
			}
		    }
		} else {

		    String msg = "작업을 실패하였습니다.";
		    if (!ERROR_MESSAGE.equals("")) {
			msg += "\\n" + ERROR_MESSAGE;
		    }

		    histroyBack(res, msg);
		}

	    }

	} else {

	    command = StringUtil.checkNull(req.getParameter("cmd"));
	    tabName = StringUtil.checkNull(req.getParameter("tabName"));
	    req.setAttribute("tabName", tabName);

	    if (command.equalsIgnoreCase("View4EcoId")) {

		// Kogger.biz("제품 ECO View4EcoId 시작");
		String ecoId = req.getParameter("ecoId");
		if (!"".equals(ecoId) && ecoId.indexOf("-") == -1 && ecoId.length() == 10) {
		    // KETbomEdior.jsp에서요청인경우 특수문자 - 를 공백으로 변환해서 온다.viewPart.jsp가 ie버전이 9라서 Tab으로 삽입된 KETbomEdior.jsp에서 정상적으로 팝업요청이 안되기때문에..
		    ecoId = ecoId.substring(0, 3) + "-" + ecoId.substring(3, 7) + "-" + ecoId.substring(7, 10);
		}
		WTChangeOrder2 eco = beans.getEcoByNo(ecoId);

		String oid = CommonUtil.getOIDString(eco);
		if (eco instanceof KETProdChangeOrder) {
		    rd = getServletContext().getRequestDispatcher("/servlet/e3ps/ProdEcoServlet?cmd=View&oid=" + oid);
		    rd.forward(req, res);
		} else {
		    rd = getServletContext().getRequestDispatcher("/servlet/e3ps/MoldEcoServlet?cmd=view&oid=" + oid);
		    rd.forward(req, res);
		}

	    } else if (command.equalsIgnoreCase("ViewEcnWeightHistory")) {

		// Kogger.biz("제품 ECO ViewEcnWeightHistory 시작");
		ecoOid = req.getParameter("oid");
		req.setAttribute("oid", ecoOid);

		rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ViewEcnWeightHistory.jsp");
		rd.forward(req, res);

	    } else if (command.equalsIgnoreCase("View") || command.equalsIgnoreCase("PrintView") || command.equalsIgnoreCase("ModifyView")
		    || command.equalsIgnoreCase("PopupView") || command.equalsIgnoreCase("CompleteReg")
		    || command.equalsIgnoreCase("UpdateView") || command.equalsIgnoreCase("CompleteModifyView")
		    || command.equalsIgnoreCase("ApprovedReg")) {

		boolean isCompletedCompleteRegStart = false;

		try {
		    // 다국어 처리
		    KETMessageService messageService = KETMessageService.getMessageService(req);

		    ecoOid = req.getParameter("oid");

		    ReferenceFactory rf = new ReferenceFactory();
		    prodEco = (KETProdChangeOrder) rf.getReference(ecoOid).getObject();

		    Hashtable<String, Object> ecoHash = beans.getProdEco(prodEco, command, messageService.getLocale().toString());

		    req.setAttribute("prodEco", prodEco);
		    req.setAttribute("ecoHash", ecoHash);

		    if (command.equalsIgnoreCase("View")) {
			req.setAttribute("prePage", req.getParameter("prePage"));
			rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewProdEcoForm.jsp");
			rd.forward(req, res);
		    } else if (command.equalsIgnoreCase("PrintView")) {
			req.setAttribute("prePage", req.getParameter("prePage"));
			rd = getServletContext().getRequestDispatcher("/jsp/ecm/printProdEcoForm.jsp");
			rd.forward(req, res);
		    } else if (command.equalsIgnoreCase("ModifyView") || command.equalsIgnoreCase("CompleteModifyView")) {
			req.setAttribute("prePage", "View");
			rd = getServletContext().getRequestDispatcher("/jsp/ecm/ModifyProdEcoForm.jsp");
			rd.forward(req, res);
		    } else if (command.equalsIgnoreCase("PopupView")) {
			// rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewProdEcoFormPopup.jsp");
			rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewProdEcoForm.jsp");

			rd.forward(req, res);
		    } else if (command.equalsIgnoreCase("CompleteReg")) {

			Kogger.biz("제품 ECO 등록완료 CompleteReg 시작");
			isCompletedCompleteRegStart = true;

			State state = State.toState("EXCUTEACTIVITY"); // 활동수행

			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEco, state, true);

			KogDbUtil.log("제품ECO 등록완료 CompleteReg 종료", "Success", ecoOid, (String) null, null, null);
			Kogger.biz("제품 ECO 등록완료 CompleteReg 종료");
			alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "RN", tabName);

		    } else if (command.equalsIgnoreCase("UpdateView")) {
			String ecaType = req.getParameter("ecaType");
			req.setAttribute("ecaType", ecaType);
			req.setAttribute("prePage", req.getParameter("ToDo"));

			rd = getServletContext().getRequestDispatcher("/jsp/ecm/ToDoProdEcoForm.jsp");
			rd.forward(req, res);
		    }
		    // 디버깅
		    else if (command.equalsIgnoreCase("ApprovedReg")) {

			Kogger.biz("제품 ECO 승인(Admin)버튼 시작");

			State state = null;
			// 승인(Test)
			if (command.equalsIgnoreCase("ApprovedReg")) {
			    if (prodEco.getLifeCycleState().toString().equals("APPROVED")) {
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEco, State.toState("UNDERREVIEW"), true);

			    }
			    state = State.toState("APPROVED");
			}

			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEco, state, true);
			KogDbUtil.log("제품ECO '승인완료' 상태 변경", "Success", ecoOid, (String) null, null, null);
			Kogger.biz("제품 ECO 승인(Admin)버튼 종료");
			alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "RN", tabName);

		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);

		    if (isCompletedCompleteRegStart) {
			KogDbUtil.log("제품 ECO 등록완료 CompleteReg", "Fail", ecoOid, (String) null, null, null);
		    }

		    if (command.equalsIgnoreCase("CompleteReg")) {
			histroyBack(res, "등록완료에 실패하였습니다.");
		    }
		}
	    }
	    // 활동완료
	    else if (command.equalsIgnoreCase("ActivityReg")) {

		Kogger.biz("ECO 활동완료 ActivityReg 시작");
		ecoOid = req.getParameter("oid");
		try {

		    // Todo에서의 작업완료시 동작하는 메쏘드를 동작시킨다.
		    paramMap.put("ecaTypes", new String[] { "1", "2" });
		    paramMap.put("isActivityReg", "Y");
		    ALERT_MESSAGE = KETEcmHelper.service.completeInProdEco(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 활동완료", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("ECO 활동완료 ActivityReg 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 활동완료", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {

		    Kogger.biz("ECO 활동완료 ActivityReg 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 활동완료", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("ECO 활동완료 ActivityReg 종료");

		String msg = "정상적으로 처리되었습니다.\\n";
		if (!ALERT_MESSAGE.equals("")) {
		    msg = ALERT_MESSAGE;
		}
		if (!ERROR_MESSAGE.equals("")) {
		    msg = "작업을 실패하였습니다.\\n" + ERROR_MESSAGE;
		}

		alertNgo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "RN", tabName);

	    }
	    // 삭제
	    else if (command.equalsIgnoreCase("Delete")) {

		Kogger.biz("제품 ECO 삭제 시작");
		ecoOid = req.getParameter("oid");
		boolean isDelete = false;

		try {
		    isDelete = KETEcmHelper.service.deleteProdEco(ecoOid);

		    KogDbUtil.log("제품ECO 삭제", "Success", ecoOid, (String) null, null, null);

		} catch (WTException e) {

		    Kogger.biz("제품 ECO 삭제 실패");
		    Kogger.error(getClass(), e);
		    KogDbUtil.log("제품ECO 삭제", "Fail", ecoOid, e, null, null);
		}

		Kogger.biz("제품 ECO 삭제 종료");

		if (isDelete) {

		    /*
	             * alertNSearch(res, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchEcoForm.jsp" );
	             */

		    try {

			res.setContentType("text/html;charset=KSC5601");

			PrintWriter out = res.getWriter();
			StringBuffer rtn_msg = new StringBuffer("");

			rtn_msg.append("\n <script language=\"javascript\">");

			String msg = "정상적으로 처리되었습니다.";
			rtn_msg.append("\n   alert(\"" + msg + "\");");

			// rtn_msg.append("\n   parent.hideProcessing();");
			rtn_msg.append("\n   try {");

			rtn_msg.append("\n   	parent.parent.opener.feedbackAfterPopup('doReSearching');");
			rtn_msg.append("\n   	parent.parent.window.close();");

			// rtn_msg.append("\n   } catch(e) { alert(e); }");
			rtn_msg.append("\n   } catch(e) { }");
			rtn_msg.append("\n </script>");

			out.println(rtn_msg);

		    } catch (Exception e) {
			Kogger.error(getClass(), e);

		    }

		} else {
		    histroyBack(res, "삭제를 실패하였습니다.");
		}
	    } else if (command.equalsIgnoreCase("CreateMoldEco")) {

		Kogger.biz("금형 ECO 생성 by 제품 ECO 시작");
		ecoOid = req.getParameter("oid");
		KETMoldChangeOrderVO moldEcoVo = null;
		try {
		    moldEcoVo = beans.getBasicMoldEcoInfo(ecoOid);

		    KogDbUtil.log("제품ECO 금형ECO생성", "Success", ecoOid, (String) null, null, null);
		} catch (Exception e) {
		    Kogger.biz("금형 ECO 생성 by 제품 ECO 실패");
		    Kogger.error(getClass(), e);
		    KogDbUtil.log("제품ECO 금형ECO생성", "Fail", ecoOid, e, null, null);
		}
		Kogger.biz("금형 ECO 생성 by 제품 ECO 종료");

		req.setAttribute("ketMoldChangeOrderVO", moldEcoVo);
		rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateMoldEcoForm.jsp?from=prod");
		rd.forward(req, res);
	    }
	    // ECN 중지
	    else if (command.equalsIgnoreCase("stopEcn")) {

		Kogger.biz("제품 ECN 중지버튼 시작");
		String[] ecnOids = paramMap.getStringArray("ecnOid");

		String ecnOidInfo = "";

		try {

		    if (ecnOids != null) {
			for (String str : ecnOids) {
			    ecnOidInfo = ecnOidInfo + " / " + str;
			}
		    }

		    ALERT_MESSAGE = KETEcmHelper.service.stopEcn(paramMap);

		    KogDbUtil.log("제품ECN 중지", "Success", (String) null, (String) null, ecnOidInfo, null);

		} catch (WTException wte) {
		    Kogger.biz("제품 ECN 중지버튼 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECN 중지", "Fail", (String) null, wte, ERROR_MESSAGE + " > " + ecnOidInfo, null);

		} catch (Exception e) {
		    Kogger.biz("제품 ECN 중지버튼 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECN 중지", "Fail", (String) null, e, ERROR_MESSAGE + " > " + ecnOidInfo, null);

		}

		Kogger.biz("제품 ECN 중지버튼 종료");

		try {
		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter out = res.getWriter();
		    String rtn_msg = "";

		    String msg = "정상적으로 처리되었습니다.\\n";
		    if (!ALERT_MESSAGE.equals("")) {
			msg = ALERT_MESSAGE;
		    }
		    if (!ERROR_MESSAGE.equals("")) {
			msg = "작업을 실패하였습니다.\\n" + ERROR_MESSAGE;
		    }

		    rtn_msg = "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
			    + "\n   alert(\"" + msg + "\");" + "\n   parent.lfn_feedbackAfterStopEcn();" + "\n </script>";
		    out.println(rtn_msg);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    }
	    // ECN 재시작
	    else if (command.equalsIgnoreCase("restartEcn")) {

		Kogger.biz("제품 ECN 재시작버튼 시작");
		String[] ecnOids = paramMap.getStringArray("ecnOid");
		String ecnOidInfo = "";

		try {

		    if (ecnOids != null) {
			for (String str : ecnOids) {
			    ecnOidInfo = ecnOidInfo + " / " + str;
			}
		    }
		    ALERT_MESSAGE = KETEcmHelper.service.restartEcn(paramMap);

		    KogDbUtil.log("제품ECN 재시작", "Success", (String) null, (String) null, ecnOidInfo, null);

		} catch (WTException wte) {

		    Kogger.biz("제품 ECN 재시작버튼 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECN 재시작", "Fail", (String) null, wte, ERROR_MESSAGE + " > " + ecnOidInfo, null);

		} catch (Exception e) {

		    Kogger.biz("제품 ECN 재시작버튼 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECN 재시작", "Fail", (String) null, e, ERROR_MESSAGE + " > " + ecnOidInfo, null);

		}

		Kogger.biz("제품 ECN 재시작버튼 종료");

		try {
		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter out = res.getWriter();
		    String rtn_msg = "";

		    String msg = "정상적으로 처리되었습니다.\\n";
		    if (!ALERT_MESSAGE.equals("")) {
			msg = ALERT_MESSAGE;
		    }
		    if (!ERROR_MESSAGE.equals("")) {
			msg = "작업을 실패하였습니다.\\n" + ERROR_MESSAGE;
		    }

		    rtn_msg = "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
			    + "\n   alert(\"" + msg + "\");" + "\n   parent.lfn_feedbackAfterRestartEcn();" + "\n </script>";
		    out.println(rtn_msg);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    }
	    // 재전송(ERP)
	    else if (command.equalsIgnoreCase("ResendERP")) {

		Kogger.biz("ECO 재전송(ERP) 시작");

		ecoOid = paramMap.getString("oid");

		try {
		    ALERT_MESSAGE = KETEcmHelper.service.resendERP(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 재전송(ERP)", "Success", ecoOid, (String) null, null, null);
		    /*
	             * ReferenceFactory rf = new ReferenceFactory(); prodEco = (KETProdChangeOrder) rf.getReference(ecoOid).getObject();
	             * 
	             * State state = State.toState("APPROVED"); LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEco, state,
	             * true);
	             */
		} catch (WTException wte) {
		    Kogger.biz("ECO 재전송(ERP) 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 재전송(ERP)", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {
		    Kogger.biz("ECO 재전송(ERP) 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 재전송(ERP)", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";

		}

		Kogger.biz("ECO 재전송(ERP) 종료");

		String msg = "정상적으로 처리되었습니다.\\n";
		if (!ALERT_MESSAGE.equals("")) {
		    msg = ALERT_MESSAGE;
		}
		if (!ERROR_MESSAGE.equals("")) {
		    msg = ERROR_MESSAGE;
		}
		alertNgo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "RN", tabName);

	    }
	    // 썸네일 가져오기
	    else if (command.equalsIgnoreCase("thumbview")) {
		String epmOid = paramMap.getString("oid");

		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
		Kogger.debug(getClass(), "epmOid is " + epmOid);
		Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");

		EPMDocument epmDocument = (EPMDocument) CommonUtil.getObject(epmOid);

		OutputStream out = res.getOutputStream();
		InputStream is = null;
		boolean isDefaultImage = false;
		try {
		    EPMDocument epm = (EPMDocument) ContentHelper.service.getContents(epmDocument);
		    ApplicationData ad = null;

		    Representation representation = PublishUtils.getRepresentation(epm);

		    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
		    Kogger.debug(getClass(), "representation is " + ((representation != null) ? representation.toString() : null));
		    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");

		    if (representation != null) {
			representation = (Representation) ContentHelper.service.getContents(representation);
			ad = ContentHelper.service.getThumbnailSmall(representation);
		    }

		    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
		    Kogger.debug(getClass(), "ad is " + ((ad != null) ? ad.toString() : null));
		    Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");

		    if (ad != null) {

			ad.setHolderLink(getHolderToContent(representation, ad));
			ContentDownload down = new ContentDownload();
			down.addContentStream(ad);
			down.execute();

			is = down.getInputStream();

		    } else {

			isDefaultImage = true;

			WTProperties prop = WTProperties.getServerProperties();
			String wthome = prop.getProperty("wt.home");

			String filePath = wthome + "\\codebase\\extcore\\jsp\\bom\\img\\part.gif";
			File file = new File(filePath);
			is = new FileInputStream(file);

			String mimeType = ".gif";
			byte[] bytes = new byte[1024];
			int bytesRead;

			res.setContentType(mimeType);

			while ((bytesRead = is.read(bytes)) != -1) {
			    out.write(bytes, 0, bytesRead);
			}

			is.close();
			out.close();
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		if (is != null && !isDefaultImage) {
		    byte[] buf = new byte[1024];
		    int count = 0;
		    while ((count = is.read(buf)) >= 0) {
			out.write(buf, 0, count);
		    }
		    out.close();
		    is.close();

		} else {
		    // Do nothing..!!
		}

	    }
	    // 등록완료전 유효성 체크
	    else if (command.equalsIgnoreCase("validateBeforeCompleteReg")) {

		Kogger.biz("ECO 등록완료버튼 유효성 체크 시작");

		ecoOid = paramMap.getString("oid");

		boolean isPass = false;
		try {
		    ALERT_MESSAGE = KETEcmHelper.service.validateBeforeCompleteReg(ecoOid, paramMap);

		    KogDbUtil.log("제품ECO 등록완료버튼 유효성 체크", "Success", ecoOid, (String) null, null, null);

		    if (ALERT_MESSAGE.equals(""))
			isPass = true;

		} catch (WTException wte) {
		    Kogger.biz("ECO 등록완료버튼 유효성 체크 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 등록완료버튼 유효성 체크", "Fail", ecoOid, wte, ERROR_MESSAGE, null);

		} catch (Exception e) {
		    Kogger.biz("ECO 등록완료버튼 유효성 체크 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 등록완료버튼 유효성 체크", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		}

		Kogger.biz("ECO 등록완료버튼 유효성 체크 종료");

		// 에러 메세지 처리
		try {

		    String msg = "";
		    if (!ALERT_MESSAGE.equals("")) {
			ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\\\\n)", "<br>");
			msg = ALERT_MESSAGE;
		    }
		    if (!ERROR_MESSAGE.equals("")) {
			msg = ERROR_MESSAGE;
		    }

		    res.setContentType("text/html;charset=KSC5601");

		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    rtn_msg.append("\n <script language=\"javascript\">");

		    rtn_msg.append("\n   parent.lfn_feedbackAfterValidateBeforeCompleteReg(" + isPass + ", '" + msg + "');");

		    rtn_msg.append("\n </script>");

		    out.println(rtn_msg);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);

		}

	    }
	    // 활동완료전 유효성 체크(초도배포때만 보인다.)
	    else if (command.equalsIgnoreCase("validateBeforeActivityReg")) {

		Kogger.biz("ECO 활동완료버튼 유효성 체크(초도) 시작");

		ecoOid = paramMap.getString("oid");

		boolean isPass = false;
		try {
		    ALERT_MESSAGE = KETEcmHelper.service.validateBeforeActivityReg(ecoOid, paramMap);
		    if (ALERT_MESSAGE.equals("")) {
			ALERT_MESSAGE = KETEcmHelper.service.validateBeforeRequestApprove(ecoOid, paramMap);

			isPass = true;
		    }

		    KogDbUtil.log("제품ECO 활동완료버튼 유효성 체크(초도)", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("ECO 활동완료버튼 유효성 체크(초도) 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 활동완료버튼 유효성 체크(초도)", "Fail", ecoOid, wte, ERROR_MESSAGE, null);

		} catch (Exception e) {
		    Kogger.biz("ECO 활동완료버튼 유효성 체크(초도) 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 활동완료버튼 유효성 체크(초도)", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		}

		Kogger.biz("ECO 활동완료버튼 유효성 체크(초도) 종료");

		// 에러 메세지 처리
		try {

		    String msg = "";
		    if (!ALERT_MESSAGE.equals("")) {
			ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\\\\n)", "<br>");
			msg = ALERT_MESSAGE;
		    }
		    if (!ERROR_MESSAGE.equals("")) {
			msg = ERROR_MESSAGE;
		    }

		    res.setContentType("text/html;charset=KSC5601");

		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    rtn_msg.append("\n <script language=\"javascript\">");

		    rtn_msg.append("\n   parent.lfn_feedbackAfterValidateBeforeActivityReg(" + isPass + ", '" + msg + "');");

		    rtn_msg.append("\n </script>");

		    out.println(rtn_msg);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);

		}

	    }
	    // 결재상신전 유효성 체크
	    else if (command.equalsIgnoreCase("validateBeforeRequestApprove")) {

		Kogger.biz("ECO 결재상신버튼 유효성 체크 시작");

		ecoOid = paramMap.getString("oid");

		boolean isPass = false;
		try {
		    ALERT_MESSAGE = KETEcmHelper.service.validateBeforeRequestApprove(ecoOid, paramMap);
		    isPass = true;

		    KogDbUtil.log("제품ECO 결재상신버튼 유효성 체크", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {
		    Kogger.biz("ECO 결재상신버튼 유효성 체크 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 결재상신버튼 유효성 체크", "Fail", ecoOid, wte, ERROR_MESSAGE, null);

		} catch (Exception e) {

		    Kogger.biz("ECO 결재상신버튼 유효성 체크 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 결재상신버튼 유효성 체크", "Fail", ecoOid, e, ERROR_MESSAGE, null);

		}

		Kogger.biz("ECO 결재상신버튼 유효성 체크 종료");

		// 에러 메세지 처리
		try {

		    String msg = "";
		    if (!ALERT_MESSAGE.equals("")) {
			ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\\\\n)", "<br>");
			msg = ALERT_MESSAGE;
		    }
		    if (!ERROR_MESSAGE.equals("")) {
			msg = ERROR_MESSAGE;
		    }

		    res.setContentType("text/html;charset=KSC5601");

		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    rtn_msg.append("\n <script language=\"javascript\">");

		    rtn_msg.append("\n   parent.lfn_feedbackAfterValidateBeforeRequestApprove2(" + isPass + ", '" + msg + "');");

		    rtn_msg.append("\n </script>");

		    out.println(rtn_msg);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);

		}

	    }

	    // ECO초기화
	    else if (command.equalsIgnoreCase("initEco")) {

		Kogger.biz("ECO 초기화 시작");
		ecoOid = req.getParameter("oid");
		try {

		    ALERT_MESSAGE = KETEcmHelper.service.initEco(ecoOid);

		    KogDbUtil.log("제품ECO 초기화", "Success", ecoOid, (String) null, null, null);

		} catch (WTException wte) {

		    Kogger.biz("ECO 초기화 실패");
		    Kogger.error(getClass(), wte);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 초기화", "Fail", ecoOid, wte, ERROR_MESSAGE, null);
		    ecoOid = "";

		} catch (Exception e) {

		    Kogger.biz("ECO 초기화 initEco 실패");
		    Kogger.error(getClass(), e);

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    KogDbUtil.log("제품ECO 초기화", "Fail", ecoOid, e, ERROR_MESSAGE, null);
		    ecoOid = "";
		}

		Kogger.biz("ECO 초기화 initEco 종료");

		String msg = "정상적으로 처리되었습니다.\\n";
		if (!ALERT_MESSAGE.equals("")) {
		    msg = ALERT_MESSAGE;
		}
		if (!ERROR_MESSAGE.equals("")) {
		    msg = "작업을 실패하였습니다.\\n" + ERROR_MESSAGE;
		}

		alertNgo(res, msg, "/plm/servlet/e3ps/ProdEcoServlet", ecoOid, "RN", tabName);

	    }

	}
    }

    private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception {
	QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
	spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo()
	        .getObjectIdentifier().getId()));

	QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem", spec, false); // (pp,
	                                                                                                            // "theContentItem",
	                                                                                                            // wt.content.HolderToContent.class,
	                                                                                                            // false);

	HolderToContent holdertocontent = (HolderToContent) queryresult.nextElement();

	return holdertocontent;
    }

    protected void alertNgo(HttpServletResponse res, String msg, String url, String oid, String prePage, String tabName) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='View'>" + "\n <input type='hidden' name='oid' value='" + oid + "'>"
		    + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n "
		    + "\n <input type='hidden' name='tabName' value='" + tabName + "'>"
		    + "\n </form>"
		    + "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
		    + "\n   alert(\"" + msg + "\");" + "\n  try { parent.opener.goTaskPage(); } catch(e) {} \n  document.frmProc.submit();"
		    + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    protected void alertNToDo(HttpServletResponse res, String msg, String url, String oid, String flag, String updateFlag, String tabName) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='UpdateView'>" + "\n <input type='hidden' name='oid' value='" + oid + "'>"
		    + "\n <input type='hidden' name='ecaType' value='" + flag + "'>" + "\n <input type='hidden' name='preAction' value='"
		    + updateFlag + "'>" + "\n <input type='hidden' name='tabName' value='" + tabName + "'>" + "\n </form>"
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

    public void histroyBack(HttpServletResponse res, String msg) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");" + "\n   history.back();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

    }

    public void alertGo(HttpServletResponse res, String url, String msg) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>" + "\n </form>"
		    + "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();"
		    + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
