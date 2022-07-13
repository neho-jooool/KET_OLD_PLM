package ext.ket.edm.stamping.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.FileUtil;
import e3ps.cost.util.StringUtil;
import e3ps.edm.EDMUserData;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMProperties;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistFileTypeLink;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.stamping.jms.StampingQueueSender;
import ext.ket.edm.stamping.oxm.DeployDrawing;
import ext.ket.edm.stamping.oxm.DeployDrawingSub;
import ext.ket.edm.stamping.oxm.DrawingDeployRequest;
import ext.ket.edm.stamping.oxm.OxmMarshaller;
import ext.ket.edm.stamping.oxm.OxmUnmarshaller;
import ext.ket.edm.stamping.oxm.RequestDrawingList;
import ext.ket.edm.stamping.service.internal.StampingDownLoader;
import ext.ket.edm.stamping.service.internal.StampingQueueInputer;
import ext.ket.edm.stamping.service.internal.StampingUploader;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.edm.stamping.util.StampingCadTypeEnum;
import ext.ket.edm.stamping.util.StampingConstants;
import ext.ket.edm.stamping.util.StampingStatusEnum;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.edm.util.DrawingSheetTypeEnum;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardStampingService extends StandardManager implements StampingService {

    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : StandardDrawingDistService
     * @작성자 : tklee
     * @작성일 : 2014. 7. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardStampingService newStandardStampingService() throws WTException {
	StandardStampingService instance = new StandardStampingService();
	instance.initialize();
	return instance;
    }

    // Stamping 처리 시작전에 이미 처리되었는지 체크
    @Override
    public boolean isReceivedAlready(String reqOid) throws Exception {

	boolean isReceivedAlready = true;

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	KETStamping stamping = getStamping(drawingDistReq);
	if (stamping == null) {
	    this.createKETStampingWhenQueueInput(drawingDistReq);
	    isReceivedAlready = false;
	} else {
	    String status = stamping.getStampStatus();
	    if (!StringUtils.isEmpty(status)) {
		StampingStatusEnum stampingStatusEnum = StampingStatusEnum.valueOf(status);
		switch (stampingStatusEnum) {
		case BEFORE:
		    // 작업전
		    isReceivedAlready = false;
		    break;
		case QUEUE:
		    // 작업중
		    isReceivedAlready = false;
		    break;
		case FAIL:
		    // 재작업인 경우
		    isReceivedAlready = false;
		    break;
		case SUCCESS:
		    // 작업완료
		    isReceivedAlready = true;
		    break;
		}
	    }
	}

	return isReceivedAlready;
    }

    // Stamping을 처리하기 위해 xml 및 cad file downlaod 후에 xml fullpath 전달
    @Override
    public String makeXmlDownCadDataByOid(String reqOid) throws Exception {

	KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);

	// get drawing List
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistEpmLink> epmDocLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class, KETDrawingDistRequest.class, drawingDistReq);

	// download CadFile and make Object
	// DrawingDeployRequest drawingDeployRequest = makeXmlObjectNDownLoadCadFile(drawingDistReq, epmDocList, epmDocLinkList);
	DrawingDeployRequest drawingDeployRequest = makeXmlObjectNDownLoadCadFile(drawingDistReq, epmDocLinkList);
	// make xml
	OxmMarshaller oxmMarshaller = new OxmMarshaller();
	String xmlFileFullPath = getXmlFileFullPath(drawingDeployRequest); // stamping root > windchill root로 변경해서 xml 저장
	oxmMarshaller.convertDrawingDeployRequest(xmlFileFullPath, drawingDeployRequest);

	// Stamping Server의 folder 위치에 맞게 변경함.

	String foldWcDefaultPath = EDMProperties.getInstance().getString(StampingConstants.EPM_WC_SERVER_ROOT_FOLD); // "epm.stamping.windchillServer.rootFold"
	String foldStampDefaultPath = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_SERVER_ROOT_FOLD); // "epm.stamping.windchillServer.rootFold"

	xmlFileFullPath = xmlFileFullPath.replace(foldWcDefaultPath, foldStampDefaultPath);

	return xmlFileFullPath;
    }

    // xml 및 cad file downlaod
    private DrawingDeployRequest makeXmlObjectNDownLoadCadFile(KETDrawingDistRequest drawingDistReq, List<KETDrawingDistEpmLink> epmDocLinkList) throws Exception {

	try {

	    Kogger.debug(getClass(), "########################## makeXmlObject ################################");

	    String year = null;
	    String month = null;
	    String day = null;

	    try {

		Date nowDate = Calendar.getInstance().getTime();
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

		year = yearFormat.format(nowDate);
		month = monthFormat.format(nowDate);
		day = dayFormat.format(nowDate);

	    } catch (Exception e) {
		throw e;
	    }

	    // 요청서 No
	    String requestNo = drawingDistReq.getNumber();
	    Kogger.debug(getClass(), "requestNo: " + requestNo);

	    // 배포유형 - Description으로 변경함.
	    String distType = drawingDistReq.getDistType();
	    String distTypeDesc = "";
	    if (StringUtils.isEmpty(distType)) {
	    } else {
		distTypeDesc = CodeHelper.manager.getCodeDescription("DISTTYPE", distType, Locale.KOREA);
		distTypeDesc = StringUtil.checkNull(distTypeDesc);
	    }

	    // Stamping 실행 횟수
	    String executeNo = null;
	    KETStamping stamping = getStamping(drawingDistReq);
	    if (stamping == null) {
		executeNo = "1";
	    } else {
		int executeNoNext = stamping.getExecuteNo() + 1;
		executeNo = String.valueOf(executeNoNext);
	    }

	    // 실행횟수 업데이트
	    updateKETStampingWhenDownload(drawingDistReq, executeNo);

	    // make for xml-object
	    DrawingDeployRequest drawingDeployRequest = new DrawingDeployRequest();
	    // filePath => E:\Stamping\2014\07\23\10001\01
	    drawingDeployRequest.setFileSeperator(StampingConstants.FILE_SEPERATOR);

	    String stampingServerFoldDefaultPath = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_SERVER_ROOT_FOLD); // "epm.stamping.stampinServer.rootFold"
		                                                                                                                           // >
																	   // "E:\\Stamping";
	    Kogger.debug(getClass(), "stampingServerFoldDefaultPath: " + stampingServerFoldDefaultPath);
	    drawingDeployRequest.setFoldDefaultPath(stampingServerFoldDefaultPath);

	    drawingDeployRequest.setFoldYear(year);
	    drawingDeployRequest.setFoldMonth(month);
	    drawingDeployRequest.setFoldDay(day);
	    drawingDeployRequest.setFoldRequestNo(requestNo);
	    drawingDeployRequest.setExecuteNo(executeNo);

	    // TKLEE 승인일처리 테스트 필요
	    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(drawingDistReq);
	    String approvedDate = null;
	    Kogger.debug(getClass(), "### KETWfmApprovalMaster :" + approvalMaster);
	    if (approvalMaster == null) {
		// throw new Exception("### DrawingDeployRequest: KETWfmApprovalMaster is null 결재일 가져오기 에러 ");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		approvedDate = formatter.format(new Date());
		Kogger.debug(getClass(), "### approvedDate SysDate :" + approvedDate);
	    } else {
		if (approvalMaster.getCompletedDate() == null) {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		    approvedDate = formatter.format(new Date());
		    Kogger.debug(getClass(), "### approvedDate SysDate :" + approvedDate);
		} else {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		    approvedDate = formatter.format(approvalMaster.getCompletedDate());
		    Kogger.debug(getClass(), "### approvedDate :" + approvedDate);
		}
	    }

	    // Stamping - PDF, DWG, STEP 등
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    List<KETDrawingDistFileType> distFileTypeList = query.queryForListAByRoleB(KETDrawingDistFileType.class, KETDrawingDistFileTypeLink.class, KETDrawingDistRequest.class, drawingDistReq);

	    boolean isStampingPDF = false;
	    boolean isStampingDWG = false;
	    boolean isStampingSTEP = false;
	    String StampingIgsYN = "N";
	    String StampingJpgYN = "N";
	    String Stamping3DPdfYN = "N";
	    String StampingStepYN = "N";
	    String StampingCatYN = "N";

	    for (KETDrawingDistFileType fileType : distFileTypeList) {

		DrawingDistFileTypeEnum distFileTypeEnum = DrawingDistFileTypeEnum.valueOf(fileType.getDistFileType());

		switch (distFileTypeEnum) {
		case PDF:
		    isStampingPDF = true;
		    break;
		case DWG:
		    isStampingDWG = true;
		    break;
		case STEP:
		    isStampingSTEP = true;
		    break;
		}
	    }

	    // 도면정보
	    RequestDrawingList requestDrawingList = new RequestDrawingList();

	    StampingDownLoader stampingDownLoader = new StampingDownLoader();

	    for (int k = 0; k < epmDocLinkList.size(); k++) {

		Kogger.debug(getClass(), "########################## Drawing ################################");
		Kogger.debug(getClass(), "########################## Start   ################################");

		KETDrawingDistEpmLink epmDocLink = epmDocLinkList.get(k);
		EPMDocument epmDoc = epmDocLink.getDistEpm();
		// KETDrawingDistEpmLink epmDocLink = epmDocLinkList.get(k);
		Kogger.debug(getClass(), "epmDoc: " + epmDoc.getNumber());

		EPMAuthoringAppType cadAppTypeEnum = epmDoc.getAuthoringApplication();
		Kogger.debug(getClass(), "EPMAuthoringAppType: " + cadAppTypeEnum.toString());
		String cadAppType = cadAppTypeEnum.toString(); // PROE
		String cadName = epmDoc.getCADName(); // em450022_pps_assy.drw
		Kogger.debug(getClass(), "cadName: " + epmDoc.getCADName());

		boolean isDWG = cadName.toUpperCase().endsWith(".DWG");
		boolean isNX = "UG".equals(cadAppType) && cadName.toUpperCase().endsWith(".PRT");
		boolean isDRW = "PROE".equals(cadAppType) && cadName.toUpperCase().endsWith(".DRW");

		// TKLEE STEP 변환 - 3D가 올 경우

		boolean isCREO_PRT = "PROE".equals(cadAppType) && (cadName.toUpperCase().endsWith(".PRT") || StringUtils.contains(cadName.toUpperCase(), ".PRT."));
		boolean isCREO_ASM = "PROE".equals(cadAppType) && cadName.toUpperCase().endsWith(".ASM");

		if (!isDWG && !isNX && !isDRW && !isCREO_PRT && !isCREO_ASM) {
		    Kogger.debug(getClass(), "EPMAuthoringAppType: " + cadAppType);
		    Kogger.debug(getClass(), "cadName: " + cadName);
		    // throw new Exception("Stamping에 적합한 CAD 파일이 아닙니다.");
		    continue;
		}

		String cadType = null;
		if (isDWG)
		    cadType = StampingCadTypeEnum.AUTOCAD.toString();
		else if (isNX)
		    cadType = StampingCadTypeEnum.NX.toString();
		else if (isDRW || isCREO_PRT || isCREO_ASM)
		    cadType = StampingCadTypeEnum.CREO.toString();

		DeployDrawing deployDrawing = new DeployDrawing();
		Kogger.debug(getClass(), "Oid: " + epmDoc.getNumber());

		DeployDrawingSub sub = null; // iges,3d_pdf,이미지 변환 기능이 추가됨으로 인한 객체추가 2016.01.11 by 황정태
		String HompageTransfer = drawingDistReq.getBackgroundYn();

		// if ("Y".equals(HompageTransfer)) {

		for (KETDrawingDistFileType fileType : distFileTypeList) {

		    DrawingDistFileTypeEnum distFileTypeEnum = DrawingDistFileTypeEnum.valueOf(fileType.getDistFileType());

		    switch (distFileTypeEnum) {
		    case THREEPDF:
			Stamping3DPdfYN = "Y";
		    case JPG:
			StampingJpgYN = "Y";
		    case IGS:
			StampingIgsYN = "Y";
		    case STEP:
			StampingStepYN = "Y";
		    case CAT:
			StampingCatYN = "Y";
		    }
		}

		if ("Y".equals(HompageTransfer) || "Y".equals(Stamping3DPdfYN) || "Y".equals(StampingJpgYN) || "Y".equals(StampingIgsYN) || "Y".equals(StampingCatYN)) {
		    StampingStepYN = "Y";
		    isStampingSTEP = true;
		}
		System.out.println("Stamping3DPdfYN : " + Stamping3DPdfYN);
		System.out.println("StampingJpgYN : " + StampingJpgYN);
		System.out.println("StampingIgsYN : " + StampingIgsYN);
		System.out.println("StampingStepYN : " + StampingStepYN);
		System.out.println("StampingCatYN : " + StampingCatYN);
		System.out.println("isStampingSTEP : " + isStampingSTEP);

		deployDrawing.setStepRequest(StampingStepYN);
		deployDrawing.setIgesRequest(StampingIgsYN);
		deployDrawing.setPdfRequest(Stamping3DPdfYN);
		deployDrawing.setCatRequest(StampingCatYN);
		if ("Y".equals(StampingJpgYN)) {
		    sub = new DeployDrawingSub();
		    sub.setHeight("800");
		    sub.setWidth("600");
		    sub.setType("jpeg");// bmp, tif, jpeg 중 선택 가능
		    deployDrawing.setImageRequest(sub);
		}
		// } else {
		// deployDrawing.setStepRequest("N");
		// deployDrawing.setIgesRequest("N");
		// deployDrawing.setPdfRequest("N");
		// }

		// STEP 유무 처리
		if (isStampingSTEP && !isDWG && !isDRW) {
		    deployDrawing.setStepRequest("Y");
		} else {
		    deployDrawing.setStepRequest("N");
		}

		deployDrawing.setCadOid(CommonUtil.getOIDString(epmDoc));
		deployDrawing.setCadType(cadType); // CREO
		deployDrawing.setDrawingFileName(cadName);
		deployDrawing.setDrawingNumber(epmDoc.getNumber());
		deployDrawing.setDrawingVersion(ObjectUtil.getVersion(epmDoc)); // epmDoc.getVersionIdentifier().getValue() + "." +
										// epmDoc.getIterationIdentifier().getValue();

		deployDrawing.setStampAprovedDate(approvedDate);

		// 작성 부서 처리

		String createDeptName = null;
		EDMUserData ud = EDMHelper.getEDMUserData(epmDoc);
		if ((ud != null) && (ud.getCreatorDeptEnName() != null)) {
		    createDeptName = ud.getCreatorDeptEnName();
		}

		if (StringUtils.isEmpty(createDeptName)) {
		    createDeptName = "Korea Electric Terminal Co., LTD.";
		}

		String stampingTeamName = createDeptName;

		// if(stampingTeamName == null){
		// stampingTeamName = drawingDistReq.getWriteDeptEnName();
		// }

		// String[] params = new String[] {
		// "PLM TFT"
		// ,"W/H Group"
		// ,"W/H Group"
		// ,"Mold Development Team"
		// ,"Advanced Research Team"
		// ,"Advanced Analysis Team"
		// ,"Product Trial Team 1"
		// ,"Product Development Team 1"
		// ,"Product Development Team 1"
		// ,"Product Development Team 2"
		// ,"Product Development Team 2"
		// ,"Product Development Team 3"
		// ,"Product Development Team 4"
		// ,"Product Development Team 5"
		// ,"Product Development Team 6"
		// ,"R&D Planning Team"
		// ,"Electronic Product Development Team"
		// ,"Electronic Product Development Team"
		// ,"Automotive IT Product Development Team"
		// ,"Module Development Team 1"
		// ,"Module Development Team 2"
		// ,"Module Development Team 1"
		// ,"Module Development Team 2"
		// ,"Module Development Team 3"
		// ,"Product Development Team 1"
		// ,"R&D Planning Team"
		// ,"Connector Development Team 3"
		// ,"Connector Development Team 1"
		// ,"Connector Development Team 2"
		// ,"Connector Development Team 3"
		// ,"Connector Development Division"
		// ,"Die Development Team"
		// ,"Korea Electric Terminal Co., LTD."
		// ,"Connector Development Division"
		// ,"Connector Development Team 1"
		// ,"R&D Planning Team"
		// ,"Cost Engineering Team"
		// ,"Advanced Research Team"
		// ,"Product Development Team 3"
		// ,"Product Development Team 4"
		// ,"Module Development Team 3"
		// ,"Connector Development Team 2"
		// ,"Die Development Team"
		// ,"Mold Development Team"
		// ,"R&D Center"
		// ,"Product Realization Division"
		// ,"Product Development Team 6"
		// ,"Electronic Business Group"
		// ,"Electronic Product Development Team"
		// ,"Mold Development Team(Electronics)"
		// ,"Product Trial Team 1"
		// ,"Connector Development Team 3"
		// ,"Product Trial Team 2"
		// ,"Manufacturing Technology Development Team 1"
		// ,"Manufacturing Technology Development Team 2"
		// ,"Module Development Team 1"
		// ,"Module Development Team 2"
		// ,"Automotive IT Product Development Team"
		// ,"Mold & Die Technology Development Department"
		// ,"Manufacturing Technology Development Department"
		// ,"R&D Planning Division"
		// ,"Connector Development Division"
		// ,"Electronic Vehicle Module Development Division"};

		String limitLineFeed = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_TEAMNAME_LIMIT_LINE_FEED); // "epm.stamping.limitLineFeed"
		int limitLineFeedInt = Integer.parseInt(limitLineFeed);

		if (stampingTeamName == null) {
		    stampingTeamName = "";
		} else {
		    if (stampingTeamName.length() > limitLineFeedInt) {
			stampingTeamName = stampingTeamName.substring(0, limitLineFeedInt) + "\\n" + stampingTeamName.substring(limitLineFeedInt);
		    }
		}
		deployDrawing.setStampTeamName(stampingTeamName);
		// deployDrawing.setStampTeamName(StringUtils.isEmpty(stampingTeamName) ? "R&D CENTER DEVELOPMENT 2 TEAM" :
		// stampingTeamName); // "R&D CENTER DEVELOPMENT 2 TEAM"

		deployDrawing.setStampType(distTypeDesc);

		DrawingSheetTypeEnum sheetType = DrawingSheetTypeEnum.valueOf(epmDocLink.getSheetType());
		deployDrawing.setTransNxSelectedSheet(sheetType.toString());

		String transRequest = null;

		// 도면 변환 유무 세팅
		if (isDWG || isCREO_PRT || isCREO_ASM) {
		    transRequest = StampingConstants.N;
		} else if (isNX) {
		    if (isStampingPDF || isStampingDWG)
			transRequest = StampingConstants.Y;
		    else
			transRequest = StampingConstants.N;
		} else if (isDRW) {
		    if (isStampingPDF || isStampingDWG)
			transRequest = StampingConstants.Y;
		    else
			transRequest = StampingConstants.N;
		}

		deployDrawing.setTransRequest(transRequest);

		// TKLEE STamping 유무 처리
		if (isStampingDWG || isStampingPDF) {
		    if (isDWG || isDRW || StampingConstants.Y.equals(transRequest)) {
			deployDrawing.setStampRequest("Y");
		    } else {
			deployDrawing.setStampRequest("N");
		    }
		} else {
		    deployDrawing.setStampRequest("N");
		}

		if (isDWG) {

		    deployDrawing.setTransThreedCadFileName(StampingConstants.NONE);
		    deployDrawing.setTransThreedCadNo(StampingConstants.NONE);
		    deployDrawing.setTransThreedCadVersion(StampingConstants.NONE);
		    deployDrawing.setTransThreedCadOid(StampingConstants.NONE);

		    // stamping cadFile Download - autocad down drawing dwg
		    stampingDownLoader.downloadDrawing(epmDoc, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));

		} else if (isCREO_PRT || isCREO_ASM) {

		    deployDrawing.setTransThreedCadFileName(epmDoc.getCADName());
		    deployDrawing.setTransThreedCadNo(epmDoc.getNumber());
		    deployDrawing.setTransThreedCadVersion(ObjectUtil.getVersion(epmDoc));
		    deployDrawing.setTransThreedCadOid(CommonUtil.getOIDString(epmDoc));

		    stampingDownLoader.downloadModel(epmDoc, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));

		} else {

		    if (isNX) {

			deployDrawing.setTransThreedCadFileName(deployDrawing.getDrawingFileName());
			deployDrawing.setTransThreedCadNo(deployDrawing.getDrawingNumber());
			deployDrawing.setTransThreedCadVersion(deployDrawing.getDrawingVersion());
			deployDrawing.setTransThreedCadOid(deployDrawing.getCadOid());

			// stamping cadFile Download - nx down model
			stampingDownLoader.downloadModel(epmDoc, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));

		    } else if (isDRW) {

			List<EPMDocument> epmDoc3DList = getEpmDoc3dByDrawing2d(epmDoc, true);

			EPMDocument epmDoc3d = epmDoc3DList.get(0);
			// 최초 0위치에 선택된 3d가 들어가고
			Kogger.debug(getClass(), "epmDoc3d: " + epmDoc3d.getNumber());
			Kogger.debug(getClass(), "3dOid: " + CommonUtil.getOIDString(epmDoc3d));

			deployDrawing.setTransThreedCadFileName(epmDoc3d.getCADName());
			deployDrawing.setTransThreedCadNo(epmDoc3d.getNumber());
			deployDrawing.setTransThreedCadVersion(ObjectUtil.getVersion(epmDoc3d));
			deployDrawing.setTransThreedCadOid(CommonUtil.getOIDString(epmDoc3d));

			// stamping cadFile Download - creo down model and drawing drw
			stampingDownLoader.downloadDrawing(epmDoc, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));
			// related drawing list
			for (int w = 0; w < epmDoc3DList.size(); w++) {
			    if (w == 0) {
				continue;
			    } else {
				EPMDocument _relatedEpmDoc3d = epmDoc3DList.get(w);
				stampingDownLoader.downloadModel(_relatedEpmDoc3d, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));
			    }
			}
			// 3D model List
			stampingDownLoader.downloadModel(epmDoc3d, getDrawingDownFilePath(drawingDeployRequest, epmDoc.getNumber()));
		    }

		}
		requestDrawingList.addDeployDrawing(deployDrawing);
	    }
	    drawingDeployRequest.setRequestDrawingList(requestDrawingList);
	    // Stamping 공통
	    drawingDeployRequest.setStampCompanyName(StampingConstants.COMPANY_NAME);
	    // Stamping 공통
	    drawingDeployRequest.setStampErrFileSuffix(StampingConstants.STAMP_ERROR_FILE_SUFFIX);
	    drawingDeployRequest.setStampFileSuffix(StampingConstants.STAMP_FILE_SUFFIX);
	    drawingDeployRequest.setStampLogFileExt(StampingConstants.STAMP_LOG_FILE_EXT);
	    // Trans 공통
	    drawingDeployRequest.setTransCreoFileSuffix(StampingConstants.TRANS_CREO_FILE_SUFFIX);
	    drawingDeployRequest.setTransNxFileSuffix(StampingConstants.TRANS_NX_FILE_SUFFIX);
	    drawingDeployRequest.setTransErrFileSuffix(StampingConstants.TRANS_ERROR_FILE_SUFFIX);
	    drawingDeployRequest.setTransLogFileExt(StampingConstants.TRANS_LOG_FILE_EXT);

	    return drawingDeployRequest;

	} catch (Exception e) {
	    Kogger.debug(getClass(), e.getMessage() + " : " + e.toString());
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    // drawing down file path
    private String getDrawingDownFilePath(DrawingDeployRequest drawingDeployRequest, String drawingNumber) throws Exception {

	String drawingDownFilePath = getStartFilePath(drawingDeployRequest) + drawingDeployRequest.getFileSeperator() + drawingNumber;
	Kogger.debug(getClass(), " drawingDownFilePath: " + drawingDownFilePath);
	FileUtil.checkDir(drawingDownFilePath);
	return drawingDownFilePath;
    }

    // cad tatal using xml full path
    private String getXmlFileFullPath(DrawingDeployRequest drawingDeployRequest) throws Exception {

	String xmlFileFullPath = getStartFilePath(drawingDeployRequest) + drawingDeployRequest.getFileSeperator() + drawingDeployRequest.getFoldRequestNo() + ".xml";
	Kogger.debug(getClass(), " xmlFileFullPath: " + xmlFileFullPath);
	return xmlFileFullPath;
    }

    // xml file path
    private String getStartFilePath(DrawingDeployRequest drawingDeployRequest) throws Exception {

	String foldDefaultPath = EDMProperties.getInstance().getString(StampingConstants.EPM_WC_SERVER_ROOT_FOLD); // "epm.stamping.windchillServer.rootFold"
	String year = drawingDeployRequest.getFoldYear();
	String month = drawingDeployRequest.getFoldMonth();
	String day = drawingDeployRequest.getFoldDay();
	String requestNo = drawingDeployRequest.getFoldRequestNo();
	String executeNo = drawingDeployRequest.getExecuteNo();

	String SEPER = drawingDeployRequest.getFileSeperator();

	String filePath = foldDefaultPath + SEPER + year + SEPER + month + SEPER + day + SEPER + requestNo + SEPER + executeNo;

	Kogger.debug(getClass(), " StartFilePath: " + filePath);

	FileUtil.checkDir(filePath);

	return filePath;
    }

    // get stamping Object
    private KETStamping getStamping(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StandardStampingService  getStamping() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETStampDistLink> stampingLinkList = query.queryForListLinkByRoleB(KETStampDistLink.class, KETDrawingDistRequest.class, drawingDistReq);

	if (stampingLinkList == null || stampingLinkList.size() == 0)
	    return null;

	KETStamping stamping = null;
	for (KETStampDistLink link : stampingLinkList) {

	    if (stamping == null) {
		stamping = link.getStamp();
		Kogger.debug(getClass(), "stamping:" + stamping);

	    }
	}

	return stamping;
    }

    // get related 3D Doc by Drawing 2D
    private List<EPMDocument> getEpmDoc3dByDrawing2d(EPMDocument epmDoc2d, boolean isNullException) throws Exception {

	List<EPMDocument> retList = new ArrayList<EPMDocument>();

	ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
	List<String> asStored3dModelList = modelStrucUtil.searchAsStoredModelList(CommonUtil.getOIDString(epmDoc2d));

	EPMDocument epmDoc3d = null;

	String cad3dOid = null;
	if (asStored3dModelList.size() == 0) {
	    if (isNullException) {
		throw new Exception("Model 3D from Drawing is empty!!");
	    }

	} else if (asStored3dModelList.size() == 1) {

	    cad3dOid = asStored3dModelList.get(0);
	    epmDoc3d = (EPMDocument) CommonUtil.getObject(cad3dOid);
	    retList.add(epmDoc3d);
	    Kogger.debug(getClass(), "3d :" + cad3dOid);

	} else {

	    String cad2dNumber = epmDoc2d.getNumber();
	    String prefix2dNumber = cad2dNumber.endsWith("_2D") ? cad2dNumber.substring(0, cad2dNumber.length() - 3) : cad2dNumber;
	    Kogger.debug(getClass(), "prefix2dNumber :" + prefix2dNumber);
	    List<EPMDocument> tempList = new ArrayList<EPMDocument>();
	    for (String tempCad3Oid : asStored3dModelList) {

		EPMDocument tempEpmDoc3d = (EPMDocument) CommonUtil.getObject(tempCad3Oid);
		String cad3dNumber = tempEpmDoc3d.getNumber();
		String prefix3dNumber = cad3dNumber.endsWith("_3D") ? cad3dNumber.substring(0, cad3dNumber.length() - 3) : cad3dNumber;
		Kogger.debug(getClass(), "prefix3dNumber :" + prefix3dNumber);

		if (prefix2dNumber.equals(prefix3dNumber)) {

		    cad3dOid = tempCad3Oid;
		    epmDoc3d = tempEpmDoc3d;
		    Kogger.debug(getClass(), "3d :" + cad3dOid);

		} else {

		    tempList.add(tempEpmDoc3d);
		}
	    }

	    if (isNullException) {
		if (epmDoc3d == null)
		    throw new Exception("Model 3D'Drawing can't not found empty!!");
	    }

	    if (epmDoc3d != null) {
		retList.add(epmDoc3d);
	    }
	    retList.addAll(tempList);

	}
	// epmDoc3d

	return retList;
    }

    // 배포요청서의 Stamping 정보 저장
    public boolean saveDrawingDeployRequest(String reqOid, String xmlFileFullPath) throws Exception {

	try {

	    // 먼저 xml Object 생성
	    OxmUnmarshaller oxmUnmarshaller = new OxmUnmarshaller();

	    String stampingDefaultFold = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_SERVER_ROOT_FOLD); // "epm.stamping.stampinServer.rootFold"
																 // >
		                                                                                                                 // "E:\\Stamping";
	    String wcServerDefaultFold = EDMProperties.getInstance().getString(StampingConstants.EPM_WC_SERVER_ROOT_FOLD); // "epm.stamping.windchillServer.rootFold"

	    Kogger.debug(getClass(), "## stampingDefaultFold:" + stampingDefaultFold);
	    Kogger.debug(getClass(), "## wcServerDefaultFold:" + wcServerDefaultFold);

	    xmlFileFullPath = xmlFileFullPath.replace(stampingDefaultFold, wcServerDefaultFold);
	    Kogger.debug(getClass(), "## xmlFileFullPath:" + xmlFileFullPath);

	    DrawingDeployRequest drawingDeployRequest = oxmUnmarshaller.getDrawingDeployRequest(xmlFileFullPath);
	    Kogger.debug(getClass(), "## drawingDeployRequest:" + drawingDeployRequest);

	    // success 정보 저장
	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	    Kogger.debug(getClass(), "## updateKETStampingWithSuccess: start");
	    KETStamping stamping = updateKETStampingWithSuccess(drawingDistReq, xmlFileFullPath);
	    Kogger.debug(getClass(), "## updateKETStampingWithSuccess: end");

	    // 첨부파일 저장 - dwg, pdf, step 파일 저장
	    Kogger.debug(getClass(), "## file save : start");
	    String startFilePath = getStartFilePath(drawingDeployRequest);
	    StampingUploader uploader = new StampingUploader();
	    int uploadedFileCount = uploader.uploadStamping(drawingDistReq, stamping, drawingDeployRequest, startFilePath);
	    Kogger.debug(getClass(), "## file save : end");

	    try {

		// 배포 스템핑 파일 생성완료시 메일 공지 API
		// 1개 이상이 Stamping 되어야 메일이 가도록 수정함.
		if (uploadedFileCount > 0) {

		    KETDrawingDistRequest ketDrawingDistRequest = drawingDistReq;
		    List<WTUser> toUserList = new ArrayList<WTUser>();
		    toUserList.add((WTUser) ketDrawingDistRequest.getCreator().getPrincipal());
		    KETMailHelper.service.sendFormMail("08031", "NoticeMailLine1.html", ketDrawingDistRequest, (WTUser) SessionHelper.manager.getPrincipal(), toUserList);

		}

	    } catch (Exception ste) {
		Kogger.error(getClass(), ste);
	    }

	    return true;

	} catch (Exception e) {
	    Kogger.debug(getClass(), e.getMessage() + " : " + e.toString());
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    // Stamping 객체는 만들면 지우지 않아서 쓰이질 않을 듯...
    private void deleteKETStamping(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StandardStampingService  getStamping() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	QueryResult qr = query.getQueryResultByRoleB(KETStamping.class, KETStampDistLink.class, KETDrawingDistRequest.class, drawingDistReq);

	while (qr.hasMoreElements()) {

	    Object[] obj = (Object[]) qr.nextElement();
	    KETStamping stamping = (KETStamping) obj[0];
	    Kogger.debug(getClass(), "stamping: " + stamping);
	    PersistenceHelper.manager.delete(stamping);

	    KETStampDistLink link = (KETStampDistLink) obj[1];
	    Kogger.debug(getClass(), "link: null");
	    PersistenceHelper.manager.delete(link);
	}
    }

    // Queue 입력시에 Samping 정보 저장 및 상태 update
    @Override
    public void createKETStampingWhenQueueInput(KETDrawingDistRequest drawingDistReq) throws Exception {

	Transaction trx = null;

	try {

	    trx = new Transaction();
	    trx.start();

	    KETStamping stamping = this.getStamping(drawingDistReq);
	    boolean isStampingEmpty = (stamping == null);
	    if (isStampingEmpty) {
		stamping = KETStamping.newKETStamping();
	    }

	    // ======================== edit body start ========================

	    stamping.setStampStatus(StampingStatusEnum.QUEUE.toString());
	    stamping.setErrorType(StringUtils.EMPTY);
	    stamping.setErrorLog(StringUtils.EMPTY);
	    stamping.setCompletedDate(null);
	    stamping.setErrorOccurDate(null);
	    stamping.setQueueInputDate(DateUtil.getCurrentTimestamp());
	    if (isStampingEmpty) {
		stamping.setExecuteNo(0);
	    } else {
		stamping.setExecuteNo(stamping.getExecuteNo() + 1);
	    }
	    stamping.setStampFilePath(StringUtils.EMPTY);

	    // ======================== edit body end ========================

	    if (isStampingEmpty) {

		stamping = (KETStamping) PersistenceHelper.manager.save(stamping);
		KETStampDistLink stampDistLink = KETStampDistLink.newKETStampDistLink(stamping, drawingDistReq);
		stampDistLink = (KETStampDistLink) PersistenceHelper.manager.save(stampDistLink);

	    } else {

		PersistenceServerHelper.update(stamping);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    if (trx != null)
		trx.rollback();
	    throw e;
	}
    }

    // Success 시(Stamping 저장 시점) Stamping Object update
    private KETStamping updateKETStampingWithSuccess(KETDrawingDistRequest drawingDistReq, String xmlFilePath) throws Exception {

	KETStamping stamping = this.getStamping(drawingDistReq);
	if (stamping == null) {

	    stamping = KETStamping.newKETStamping();
	    stamping.setStampStatus(StampingStatusEnum.SUCCESS.toString());
	    // stamping.setErrorType(StringUtils.EMPTY);
	    // stamping.setErrorLog(StringUtils.EMPTY);
	    stamping.setCompletedDate(DateUtil.getCurrentTimestamp());
	    // stamping.setErrorOccurDate(null);
	    // stamping.setQueueInputDate(Date)
	    // stamping.setExecuteNo(int)
	    stamping.setStampFilePath(xmlFilePath);
	    stamping = (KETStamping) PersistenceHelper.manager.save(stamping);

	    KETStampDistLink stampDistLink = KETStampDistLink.newKETStampDistLink(stamping, drawingDistReq);
	    stampDistLink = (KETStampDistLink) PersistenceHelper.manager.save(stampDistLink);

	} else {

	    stamping.setStampStatus(StampingStatusEnum.SUCCESS.toString());
	    stamping.setErrorType(StringUtils.EMPTY);
	    stamping.setErrorLog(StringUtils.EMPTY);
	    stamping.setCompletedDate(DateUtil.getCurrentTimestamp());
	    stamping.setErrorOccurDate(null);
	    // stamping.setQueueInputDate(Date)
	    // stamping.setExecuteNo(int)
	    stamping.setStampFilePath(xmlFilePath);
	    PersistenceServerHelper.update(stamping);
	    stamping = (KETStamping) PersistenceHelper.manager.refresh(stamping);
	}

	return stamping;
    }

    // CAD 파일 다운로드 시점에 Execute No update
    private void updateKETStampingWhenDownload(KETDrawingDistRequest drawingDistReq, String executeNo) throws Exception {

	KETStamping stamping = this.getStamping(drawingDistReq);
	stamping.setExecuteNo(Integer.parseInt(executeNo));
	PersistenceServerHelper.update(stamping);
	stamping = (KETStamping) PersistenceHelper.manager.refresh(stamping);
    }

    // Fail 시 (Stamping 서버 에러 시점) Stamping Object update
    private void updateKETStampingWithFail(KETDrawingDistRequest drawingDistReq, String errorWhere, String errorType, String errorLog, String xmlFilePath) throws Exception {

	KETStamping stamping = this.getStamping(drawingDistReq);
	boolean isStampingEmpty = (stamping == null);
	if (isStampingEmpty) {
	    stamping = KETStamping.newKETStamping();
	}

	// ======================== edit body start ========================
	stamping.setStampStatus(StampingStatusEnum.FAIL.toString());

	// StampingErrorTypeEnum errorTypeEnum = null;
	// try {
	// errorTypeEnum = StampingErrorTypeEnum.valueOf(errorType);
	// } catch (Exception e) {
	// log.error(e);
	// }
	// if (errorTypeEnum == null)
	// stamping.setErrorType(errorType);
	// else
	// stamping.setErrorType(errorTypeEnum.toString());

	stamping.setErrorType(errorType);

	if (errorWhere == null)
	    errorWhere = StringUtils.EMPTY;

	stamping.setErrorLog(errorWhere + ":" + errorLog);
	// stamping.setCompletedDate();
	stamping.setErrorOccurDate(DateUtil.getCurrentTimestamp());
	// stamping.setQueueInputDate(Date)
	// stamping.setExecuteNo(int)
	stamping.setStampFilePath(xmlFilePath);

	// ======================== edit body end ========================

	if (isStampingEmpty) {

	    stamping = (KETStamping) PersistenceHelper.manager.save(stamping);
	    KETStampDistLink stampDistLink = KETStampDistLink.newKETStampDistLink(stamping, drawingDistReq);
	    stampDistLink = (KETStampDistLink) PersistenceHelper.manager.save(stampDistLink);

	} else {

	    PersistenceServerHelper.update(stamping);
	    stamping = (KETStamping) PersistenceHelper.manager.refresh(stamping);
	}
    }

    // Stamping Server fail Save
    @Override
    public boolean saveErrorLog(String reqOid, String errorWhere, String errorType, String errorLog, String xmlFilePath) throws Exception {

	try {

	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	    updateKETStampingWithFail(drawingDistReq, errorWhere, errorType, errorLog, xmlFilePath);

	} catch (Exception e) {
	    Kogger.debug(getClass(), e.getMessage() + " : " + e.toString());
	    throw e;
	}

	return true;
    }

    // 결재는 완료되었지만, Stamping queue에 들어가지 않은 경우 처리
    @Override
    public boolean inputQueueDrawingDistReq() throws Exception {
	// TKLEE Test Shutdown 대비해서
	// 결재완료되었지만, Samping 정보가 없는 경우 or Queue 이지만 12시간 이상 된 경우
	StampingQueueInputer stampingQueueInputer = new StampingQueueInputer();
	List<KETDrawingDistRequest> docList = stampingQueueInputer.getInputQueueDrawingDistReq();

	for (KETDrawingDistRequest req : docList) {

	    String reqOId = CommonUtil.getOIDString(req);
	    String reqNumber = req.getNumber();

	    StampingQueueSender.getInstance().sendMessage(reqNumber, reqOId);
	}

	return false;
    }
}
