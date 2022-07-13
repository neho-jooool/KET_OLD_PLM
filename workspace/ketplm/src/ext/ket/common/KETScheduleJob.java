package ext.ket.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.enterprise.Managed;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTProperties;
import wt.workflow.work.WorkItem;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcrBeans;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeLink;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.qms.QmsPartInterfaceUtil;
import ext.ket.common.dashboard.service.KETMailReceiveHelper;
import ext.ket.cost.code.sap.ErpProductCostInterface;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.TimerUtil;
import ext.ket.wfm.service.KETWorkflowHelper;
import ext.ket.wfm.service.KETWorkspaceHelper;

public class KETScheduleJob {
    // private static final Logger logger = LogR.getLogger(KETScheduleJob.class.getName());
    // static {
    // boolean verbose = false;
    // String propValue = WVSProperties.getPropertyValue("publish.service.verbose");
    // if ((propValue != null) && propValue.equalsIgnoreCase("TRUE")) {
    // verbose = true;
    // }
    // if (verbose && logger.getLevel() == null) {
    // logger.setLevel(Level.DEBUG);
    // }
    // }

    public static void tempFileDelete(String currentDate, SimpleDateFormat simpleDateFormat, File tempDir) {

	if (tempDir.exists()) {

	    File[] folder_list = tempDir.listFiles(); // 파일리스트 얻어오기

	    for (int j = 0; j < folder_list.length; j++) {

		if (folder_list[j].isFile()) {
		    long lastModified = folder_list[j].lastModified();
		    Date lastModifiedDate = new Date(lastModified);
		    String fileModyDate = simpleDateFormat.format(lastModifiedDate);

		    if (!currentDate.equals(fileModyDate)) {
			folder_list[j].delete(); // 파일 삭제
		    }
		}
	    }
	}
    }

    public static void excuteJob() {

	/**
	 * temp 파일 자동 삭제 start
	 */

	WTProperties wtproperties;

	try {

	    wtproperties = WTProperties.getLocalProperties();
	    String tempdir = wtproperties.getProperty("wt.temp", "");

	    File tempDir = new File(tempdir);
	    String currentDate = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));

	    String pattern = "yyyy-MM-dd";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	    tempFileDelete(currentDate, simpleDateFormat, tempDir);

	    tempDir = new File(tempdir + File.separator + "drm");
	    tempFileDelete(currentDate, simpleDateFormat, tempDir);

	    tempDir = new File(tempdir + File.separator + "drm" + File.separator + "DownloadTemp");
	    tempFileDelete(currentDate, simpleDateFormat, tempDir);

	    tempDir = new File(tempdir + File.separator + "drm" + File.separator + "UploadTemp");
	    tempFileDelete(currentDate, simpleDateFormat, tempDir);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	/**
	 * temp 파일 자동 삭제 end
	 * 
	 * 
	 */

	Kogger.debug("[Schedule] KETScheduleJob !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

	try {

	    TimerUtil timer = new TimerUtil(KETScheduleJob.class.getName());

	    timer.setStartPoint("KETSchedulejob");

	    Kogger.debug("[Schedule] Excute Date : " + DateUtil.getCurrentDateString("d") + " " + DateUtil.getCurrentDateString("t"));

	    Kogger.debug("[Schedule] Mail 전체 발송 Start");
	    sendMailAlll();
	    Kogger.debug("[Schedule] Mail 전체 발송 End");

	    Kogger.debug("[1 Schedule] KQIS 4M가등록 대상 추가금형정보 인터페이스 Start");
	    try {
		QmsPartInterfaceUtil.manager.qms4mSave();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    Kogger.debug("[1 Schedule] KQIS 4M가등록 대상 추가금형정보 인터페이스 End");

	    Kogger.debug("[2 Schedule] 개발원가 ERP 전송을 위한 집계 및 ERP 전송 Start");
	    ErpProductCostInterface costInterface = new ErpProductCostInterface();
	    try {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		costInterface.costProductHistoryCreate(reqMap);
		costInterface.costProductSendErp(costInterface.getErpTargetList());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    Kogger.debug("[2 Schedule] 개발원가 ERP 전송을 위한 집계 및 ERP 전송 End");

	    timer.setEndPoint();
	    timer.display();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Kogger.debug("[Schedule] KETScheduleJob SCHEDULE Done..!");
	Kogger.info("excuteJob :::::" + KETScheduleJob.class);

    }

    private static void sendMailAlll() {
	boolean goFlag = true;

	Calendar cal = Calendar.getInstance();
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

	if (dayOfWeek == 1 || dayOfWeek == 7) { // 주말에는 메일 발송안한다
	    goFlag = false;
	}

	if (goFlag) {
	    if (dayOfWeek == 2 || dayOfWeek == 5) { // 월요일,목요일 만 발송

		Kogger.debug("[1. Schedule] 프로젝트 주요일정 현황 알람 메일 발송 Start");
		try {
		    KETMailReceiveHelper.service.pjtMainScheduleSendMail();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		Kogger.debug("[1. Schedule] 프로젝트 주요일정 현황 알람 메일 발송 End");

		// *********************2016.05.31 추가**************************//
		// *********************대쉬보드 상황판 메일 발송추가**************************//

		// Kogger.debug("[5. Schedule] 대쉬보드 상황판 메일 발송 Start");
		//
		// List<WTUser> dashBoardUserlist = KETMailReceiveHelper.service.dashboardSendMail();
		//
		// Kogger.debug("[5. Schedule] 대쉬보드 상황판 메일 End");
	    }

	    // *********************2015.10.15 추가**************************//
	    // *********************개발품질문제 지연메일 발송추가**************************//

	    Kogger.debug("[2. Schedule] 개발품질문제 지연메일 발송 Start");
	    try {
		DqmHelper.service.dqmDelayMailSend("1");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    Kogger.debug("[2. Schedule] 개발품질문제 지연메일 발송 End");

	    Kogger.debug("[3. Schedule] 결재지연(3일이상) 알람 메일 발송 Start");
	    try {
		KETWorkspaceHelper.service.sendMailDelayWorkItem(); // 결재지연(3일이상) 알람
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    Kogger.debug("[3. Schedule] 결재지연(3일이상) 알람 메일 발송 End");

	    Kogger.debug("[4. Schedule] 퇴사예정자의 팀장에게 알람 메일 발송 Start");
	    try {
		KETWorkspaceHelper.service.sendMailRetireTargetList(); // 퇴사예정자의 팀장에게 알람메일 발송
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    Kogger.debug("[4. Schedule] 퇴사예정자의 팀장에게 알람 메일 발송 End");

	    if (dayOfWeek == 2 || dayOfWeek == 5) { // 월요일,목요일 만 발송

		Kogger.debug("[5. Schedule] ECN 지연 메일 발송 Start");
		try {
		    ecnSendMail();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		Kogger.debug("[5. Schedule] ECN 지연 메일 발송 End");

		Kogger.debug("[6. Schedule] ECR 회의록 지연 메일 발송 Start");
		try {
		    ecrMeetingSendMail();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		Kogger.debug("[6. Schedule] ECR 회의록 지연 메일 발송 End");
	    }
	}
    }

    private static void ecrMeetingSendMail() throws Exception {
	HashMap<WTChangeRequest2, ArrayList<Managed>> meetingDelayMap = KETEcmHelper.service.getEcrRelatedMeetingDelay();

	Iterator<WTChangeRequest2> meetingDelayKeys = meetingDelayMap.keySet().iterator();
	while (meetingDelayKeys.hasNext()) {
	    WTChangeRequest2 wtChangeRequest2 = meetingDelayKeys.next();

	    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
	    spec.appendWhere(
		    new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil
		            .getOIDLongValue((WTChangeRequest2) wtChangeRequest2)), new int[] { 0 });
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		// 제품, 금형 ECR 확장팩
		KETChangeRequestExpansion expansion = (KETChangeRequestExpansion) result.nextElement();
		WTUser charge = (WTUser) wtChangeRequest2.getCreator().getPrincipal();

		// WTUser charge = (expansion != null) ? expansion.getCharge() : null;
		// 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
		if (charge != null) {
		    List<WTUser> toUserList = new ArrayList<WTUser>();
		    // WTUser member = (WTUser) expansion.getCharge();
		    toUserList.add(charge);
		    PeopleData peopleData = new PeopleData(charge);

		    if (peopleData.isDiable) { // 퇴사자 제외
			continue;
		    }

		    // 2018.07.01 이전의 ecr 회의록 중 eco 연계된 건이 있으면 pass 로직 start
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		    Date approveDate = format.parse(StringUtil.checkNull(EcmUtil.getLastApproveDate(wtChangeRequest2)));
		    Date day = format.parse("2018-07-01");

		    int compare = day.compareTo(approveDate);

		    if (compare > 0) {

			QueryResult ecoQr = null;

			if (wtChangeRequest2 instanceof KETProdChangeRequest) {
			    ecoQr = PersistenceHelper.manager.navigate(wtChangeRequest2, "prodECO", KETProdChangeLink.class);
			} else {
			    continue; // 금형은 지연메일 발송대상에서 제외한다 2018.07.20 연구지원팀 최희락 대리 요청
			    // ecoQr = PersistenceHelper.manager.navigate( wtChangeRequest2, "moldECO", KETMoldChangeLink.class );
			}

			if (ecoQr != null && ecoQr.size() > 0) {
			    continue;
			}

		    }
		    // 2018.07.01 이전의 ecr 회의록 중 eco 연계된 건이 있으면 pass 로직 end

		    ProdEcrBeans beans = new ProdEcrBeans();
		    KETCompetentDepartment competent = beans.getKETCompetentDepartment(CommonUtil.getOIDString(wtChangeRequest2));

		    if (competent != null && "REJECT".equals(competent.getReviewCode())) {// 기각된 ecr pass
			continue;
		    }

		    if (competent != null && "MREQ002".equals(competent.getMeetingCode())) {// 회의 불필요로 표기된 ecr pass
			continue;
		    }

		    // 팀장 추가
		    if (peopleData.department != null) {
			WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
			if (chief == null) {
			    System.out.println("chief is null : " + peopleData.department.getName());
			}
			if (chief != null && !chief.equals(charge)) {
			    toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
			}

		    } else {
			System.out.println("peopleData.department is null : " + charge.getFullName());
		    }
		    // toUserList set해야함
		    KETMailHelper.service.sendFormMail("08025", "NoticeMailLine.html", wtChangeRequest2, KETObjectUtil.getUser("wcadmin"),
			    toUserList);

		}
	    }
	}
    }

    private static void ecnSendMail() throws Exception {
	HashMap<WTChangeOrder2, ArrayList<Managed>> ecnDelayMap = KETEcmHelper.service.getEcoRelatedEcnDelay();

	Iterator<WTChangeOrder2> ecnDelayKeys = ecnDelayMap.keySet().iterator();
	while (ecnDelayKeys.hasNext()) {
	    WTChangeOrder2 wtChangeOrder2 = ecnDelayKeys.next();

	    ArrayList<Managed> ecaManagedList = ecnDelayMap.get(wtChangeOrder2);

	    for (int i = 0; i < ecaManagedList.size(); i++) {
		List<WTUser> toUserList = new ArrayList<WTUser>();
		Managed ecaManaged = ecaManagedList.get(i);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		boolean isSend = true;

		if (ecaManaged.getLifeCycleState().equals(State.toState("REJECTED"))) {// 중지됨 제외
		    isSend = false;
		}

		if (ecaManaged instanceof KETProdChangeActivity && isSend) {
		    KETProdChangeActivity prodCA = (KETProdChangeActivity) ecaManaged;
		    String workerId = prodCA.getWorkerId();
		    if (!workerId.equals("")) {
			WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);

			System.out.println("ID : " + ecaCharge.getName() + " NAME : " + ecaCharge.getFullName() + "OID : "
			        + CommonUtil.getOIDString(prodCA));

			// 2018.01.01 이전 pass 로직 start
			WorkItem item = KETWorkflowHelper.service.getWorkItem(prodCA, ecaCharge);

			if (item == null) {
			    continue;
			}
			Date createDate = format.parse(DateUtil.getTimeFormat(item.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
			Date day = format.parse("2018-01-01");

			int compare = day.compareTo(createDate);

			if (compare > 0) {
			    isSend = false;
			}
			// 2018.01.01 이전 pass 로직 end

			toUserList.add(ecaCharge);

			PeopleData peopleData = new PeopleData(ecaCharge);

			if (peopleData.isDiable) { // 퇴사자 제외
			    isSend = false;
			}

			if (isSend) {
			    // 팀장 추가
			    if (peopleData.department != null) {
				WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
				if (chief == null) {
				    System.out.println("chief is null : " + peopleData.department.getName());
				}
				if (chief != null && !chief.equals(ecaCharge)) {
				    toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
				}

			    } else {
				System.out.println("peopleData.department is null : " + ecaCharge.getFullName());
			    }

			    WTUser toUser = (WTUser) wtChangeOrder2.getCreator().getPrincipal();
			    peopleData = new PeopleData(toUser);

			    if (peopleData.isDiable) { // 보내는 사람이 퇴사자인 경우 관리자 계정으로 보낸다
				toUser = KETObjectUtil.getUser("wcadmin");
			    }

			    KETMailHelper.service.sendFormMail("08030", "NoticeMailLine3.html", ecaManaged, toUser, toUserList);
			}

		    }

		} else if (ecaManaged instanceof KETMoldChangeActivity && isSend) {
		    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) ecaManaged;
		    String workerId = moldCA.getWorkerId();
		    if (!workerId.equals("")) {
			WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);

			// 2018.01.01 이전 pass 로직 start
			WorkItem item = KETWorkflowHelper.service.getWorkItem(moldCA, ecaCharge);

			if (item != null) {
			    Date createDate = format.parse(DateUtil.getTimeFormat(item.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
			    Date day = format.parse("2018-01-01");

			    int compare = day.compareTo(createDate);

			    if (compare > 0) {
				isSend = false;
			    }
			}

			// 2018.01.01 이전 pass 로직 end

			toUserList.add(ecaCharge);

			PeopleData peopleData = new PeopleData(ecaCharge);

			if (peopleData.isDiable) { // 퇴사자 제외
			    isSend = false;
			}

			if (isSend) {
			    // 팀장 추가
			    if (peopleData.department != null) {
				WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
				if (chief == null) {
				    System.out.println("chief is null : " + peopleData.department.getName());
				}
				if (chief != null && !chief.equals(ecaCharge)) {
				    toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
				}

			    } else {
				System.out.println("peopleData.department is null : " + ecaCharge.getFullName());
			    }

			    WTUser toUser = (WTUser) wtChangeOrder2.getCreator().getPrincipal();
			    peopleData = new PeopleData(toUser);

			    if (peopleData.isDiable) { // 보내는 사람이 퇴사자인 경우 관리자 계정으로 보낸다
				toUser = KETObjectUtil.getUser("wcadmin");
			    }

			    KETMailHelper.service.sendFormMail("08030", "NoticeMailLine3.html", ecaManaged, toUser, toUserList);
			}

		    }
		}
	    }
	}
    }

}
