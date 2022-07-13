package ext.ket.shared.mail.service;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import wt.change2.WTChangeRequest2;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfProcess;
import e3ps.common.mail.MailHtmlContentTemplate;
import e3ps.common.mail.SendMail;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETEcrCallLink;
import e3ps.ecm.entity.KETMeetingCall;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ews.entity.EarlyWarningTargetLink;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningTarget;
import e3ps.groupware.board.ImproveBoard;
import e3ps.groupware.board.ImproveBoardData;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectIssueAnswer;
import e3ps.project.TaskDependencyLink;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectIssueData;
import e3ps.project.beans.ProjectIssueHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.ClassifyPBOUtil;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.common.dashboard.entity.KETDashBoardMailLink;
import ext.ket.common.dashboard.entity.KETDashBoardMailSetting;
import ext.ket.common.dashboard.service.KETMailReceiveHelper;
import ext.ket.dashboard.entity.DashBoardDTO;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.shared.dao.SqlMapSessionFactory;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;
import ext.ket.wfm.entity.WorkItemDTO;

/**
 * @클래스명 : StandardKETMailService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 9. 12.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class StandardKETMailService extends StandardManager implements KETMailService, Serializable {

    private static final long serialVersionUID = 5535806128693018565L;

    public static StandardKETMailService newStandardKETMailService() throws WTException {
	StandardKETMailService instance = new StandardKETMailService();
	instance.initialize();
	initProp();
	return instance;
    }

    private static WTProperties wtProperties;
    private static boolean mailEnable = false;
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    static void initProp() {
	try {
	    wtProperties = WTProperties.getLocalProperties();
	    mailEnable = wtProperties.getProperty("wt.mail.enable", false);
	} catch (IOException e) {
	    Kogger.error(StandardKETMailService.class, e);
	}
    }

    @Override
    public void sendFormMail(String type, String templateName, Object pbo, WTUser from, List<WTUser> to) throws Exception {

	if (!mailEnable) {
	    Kogger.debug(getClass(), "sendFormMail : wt.mail.enable properties is false ... Bypass mail sending...");
	    return;
	}
	try {
	    Kogger.debug(getClass(), KETMessageService.getMessageService().getString("e3ps.message.ket_message", type)
		    + " [sendFormMail] start ####################");
	    // 결재메일 발송의 경우, 마지막 검토는 결재요청 메일로 발송되어야 하므로 체크하는 부분
	    WTUser lastApprover = null;
	    if ("08001".equals(type)) {
		lastApprover = WorkflowSearchHelper.manager.getLastUser((Persistable) pbo);
	    }
	    SendMail mail = new SendMail();

	    if (from == null && "08001".equals(type)) {
		if (pbo instanceof KETSalesCSMeetingApproval) {
		    KETSalesCSMeetingApproval salesApp = (KETSalesCSMeetingApproval) pbo;

		    from = (WTUser) ((WTPrincipalReference) salesApp.getCreator()).getPrincipal();
		} else if (pbo instanceof KETSalesProject) {
		    KETSalesProject salesPjt = (KETSalesProject) pbo;

		    from = (WTUser) ((WTPrincipalReference) salesPjt.getCreator()).getPrincipal();
		}

	    }
	    if (from != null) {
		People fromPeople = PeopleHelper.manager.getPeople(from.getName());
		if (fromPeople != null && StringUtil.checkString(fromPeople.getEmail()))
		    mail.setFromMailAddress(fromPeople.getEmail(), fromPeople.getName());
		Kogger.debug(getClass(), "[sendFormMail] from : " + fromPeople.getEmail() + " / " + fromPeople.getName());
	    }
	    String tempNationalCode = "";
	    String content = "";
	    String title = "";
	    for (WTUser toUser : to) {
		People toPeople = PeopleHelper.manager.getPeople(toUser.getName());
		if (toPeople != null && StringUtil.checkString(toPeople.getEmail()) && !toPeople.isIsDisable()) {
		    Kogger.debug(getClass(), "[sendFormMail] to : " + toPeople.getEmail() + " / " + toPeople.getName());
		    mail.setToMailAddress(toPeople.getEmail(), toPeople.getName());
		    // 메일 수신자의 선호언어 설정에 따라 다국어 메일 컨텐트 작성
		    // 위의 사유로 받는 사람 수 만큼 메일 발송
		    // 서버 부하를 최소한으로 감소하기 위해 이전 수신자의 선호언어설정과 다른 경우에만 컨텐트를 작성함.
		    if (!tempNationalCode.equals(toPeople.getNationalCode())) {
			// 결재메일 발송의 경우, 마지막 검토는 결재요청 메일로 발송되어야 하므로 체크하는 부분
			if ("08001".equals(type) && lastApprover != null) {
			    Kogger.debug(getClass(), "[sendFormMail] lastApprover : " + CommonUtil.getOIDString(lastApprover)
				    + " / toUser : " + CommonUtil.getOIDString(toUser));
			    if (CommonUtil.getOIDString(lastApprover).equals(CommonUtil.getOIDString(toUser)))
				type = "08003";
			}
			Locale locale = null;
			if (StringUtil.checkString(toPeople.getNationalCode()))
			    locale = new Locale(toPeople.getNationalCode());
			else
			    locale = Locale.KOREA;
			Hashtable<String, String> contentArgs = getMailContent(type, pbo, locale, toUser);
			content = MailHtmlContentTemplate.getInstance().htmlContent(contentArgs, templateName);
			// Kogger.debug(getClass(), "[sendFormMail] contentArgs : " +
			// contentArgs);
			// Kogger.debug(getClass(), "[sendFormMail] content : " + content);
			title = contentArgs.get("title");
			tempNationalCode = locale.toString();
		    }
		    mail.setSubject(title);
		    String htmlMessage = content;
		    String[] fileNames = {};
		    if (content != null) {
			mail.setHtmlAndFile(content, fileNames);
		    } else {
			mail.setHtmlAndFile(htmlMessage, fileNames);
		    }
		    System.out.println(type + "##### MAIL SEND TO #### : " + toPeople.getEmail() + " / " + toPeople.getName());
		    mail.send();
		    Kogger.debug(getClass(), "[sendFormMail] send............................. ");
		}
	    }
	    Kogger.debug(getClass(), "##################### [sendFormMail] end #####################");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void sendFormMail(String type, String templateName, HashMap<String, Object> map) throws Exception {
	if (!mailEnable) {
	    Kogger.debug(getClass(), "sendFormMail : wt.mail.enable properties is false ... Bypass mail sending...");
	    return;
	}
	try {
	    Kogger.debug(getClass(), KETMessageService.getMessageService().getString("e3ps.message.ket_message", type)
		    + " [sendFormMail] start ####################");
	    Object pbo = map.get("pbo");
	    WTUser from = (WTUser) map.get("from");
	    @SuppressWarnings("unchecked")
	    ArrayList<WTUser> to = (ArrayList<WTUser>) map.get("to");
	    // 결재메일 발송의 경우, 마지막 검토는 결재요청 메일로 발송되어야 하므로 체크하는 부분
	    WTUser lastApprover = null;
	    if ("08001".equals(type)) {
		lastApprover = WorkflowSearchHelper.manager.getLastUser((Persistable) pbo);
	    }
	    SendMail mail = new SendMail();
	    if (from != null) {
		People fromPeople = PeopleHelper.manager.getPeople(from.getName());
		if (fromPeople != null && StringUtil.checkString(fromPeople.getEmail()))
		    mail.setFromMailAddress(fromPeople.getEmail(), fromPeople.getName());
		Kogger.debug(getClass(), "[sendFormMail] from : " + fromPeople.getEmail() + " / " + fromPeople.getName());
	    }
	    String tempNationalCode = "";
	    String content = "";
	    String title = "";
	    for (WTUser toUser : to) {
		People toPeople = PeopleHelper.manager.getPeople(toUser.getName());
		if (toPeople != null && StringUtil.checkString(toPeople.getEmail()) && !toPeople.isIsDisable()) {
		    Kogger.debug(getClass(), "[sendFormMail] to : " + toPeople.getEmail() + " / " + toPeople.getName());
		    mail.setToMailAddress(toPeople.getEmail(), toPeople.getName());
		    // 메일 수신자의 선호언어 설정에 따라 다국어 메일 컨텐트 작성
		    // 위의 사유로 받는 사람 수 만큼 메일 발송
		    // 서버 부하를 최소한으로 감소하기 위해 이전 수신자의 선호언어설정과 다른 경우에만 컨텐트를 작성함.
		    if (!tempNationalCode.equals(toPeople.getNationalCode())) {
			// 결재메일 발송의 경우, 마지막 검토는 결재요청 메일로 발송되어야 하므로 체크하는 부분
			if ("08001".equals(type) && lastApprover != null) {
			    Kogger.debug(getClass(), "[sendFormMail] lastApprover : " + CommonUtil.getOIDString(lastApprover)
				    + " / toUser : " + CommonUtil.getOIDString(toUser));
			    if (CommonUtil.getOIDString(lastApprover).equals(CommonUtil.getOIDString(toUser)))
				type = "08003";
			}
			Locale locale = null;
			if (StringUtil.checkString(toPeople.getNationalCode()))
			    locale = new Locale(toPeople.getNationalCode());
			else
			    locale = Locale.KOREA;
			Hashtable<String, String> contentArgs = getMailContent(type, pbo, locale, toUser, map);
			content = MailHtmlContentTemplate.getInstance().htmlContent(contentArgs, templateName);
			// Kogger.debug(getClass(), "[sendFormMail] contentArgs : " +
			// contentArgs);
			// Kogger.debug(getClass(), "[sendFormMail] content : " + content);
			title = contentArgs.get("title");
			tempNationalCode = locale.toString();
		    }
		    mail.setSubject(title);
		    String htmlMessage = content;
		    String[] fileNames = {};
		    if (content != null) {
			mail.setHtmlAndFile(content, fileNames);
		    } else {
			mail.setHtmlAndFile(htmlMessage, fileNames);
		    }
		    mail.send();
		    Kogger.debug(getClass(), "[sendFormMail] send............................. ");
		}
	    }
	    Kogger.debug(getClass(), "##################### [sendFormMail] end #####################");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private Hashtable<String, String> getMailContent(String type, Object pbo, Locale locale, WTUser toUser, HashMap<String, Object> map)
	    throws Exception {

	KETMessageService messageService = new KETMessageService(locale);
	Hashtable<String, String> contentArgs = new Hashtable<String, String>();
	Hashtable<String, Object> pboHash = ClassifyPBOUtil.extractPBOInfo(pbo);
	String pboOid = "";
	if (pbo instanceof Persistable) {
	    pboOid = CommonUtil.getOIDString((Persistable) pbo);
	}

	String host = wtProperties.getProperty("wt.server.codebase"); // http://plm.ket.com/plm
	String title = "";
	String mailType = "";
	String mailPboType = "";
	String mailPboName = "";
	String titleMsg = "";
	String descriptionMsg = "";
	String projectNo = "";
	String projectName = "";
	String partNo = "";
	String partName = "";
	String dependancyTaskName = "";
	String header1 = "";
	String header2 = "";
	String header3 = "";
	String header4 = "";
	String column1 = "";
	String column2 = "";
	String column3 = "";
	String column4 = "";
	String tableHeader1 = "";
	String tableHeader2 = "";
	String tableHeader3 = "";
	String tableHeader2_1 = "";
	String tableHeader2_2 = "";
	String tableHeader3_1 = "";
	String tableHeader3_2 = "";
	String content = "";
	String subject1 = "";
	String subject2 = "";
	String subject3 = "";
	String subject2Date = "";
	String subject3Date = "";
	String footerMsg = "";
	String goLinkURL = "";
	String footerClickMsg = messageService.getString("e3ps.message.ket_message", "08087");/* 클릭하면 해당 화면이 열립니다. */

	if ("08015".equals(type)) { // CFT변경

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08015");/* CFT변경 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "CFT",
		    messageService.getString("e3ps.message.ket_message", "08116")/* 인원변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08058"/* CFT가 변경되었습니다. */);
	    subject1 = messageService.getString("e3ps.message.ket_message", "08049"/* CFT 변경 사항 */);
	    tableHeader1 = "Role";
	    tableHeader2 = messageService.getString("e3ps.message.ket_message", "05153"/* 변경 전 */);
	    tableHeader3 = messageService.getString("e3ps.message.ket_message", "05154"/* 변경 후 */);
	    content = (String) map.get("content"); // content table
	    subject2 = messageService.getString("e3ps.message.ket_message", "01520"/* 변경일 */);
	    subject2Date = DateUtil.getCurrentDateString("d"); // 변경일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08016".equals(type)) { // 프로젝트일정변경

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08016");/* 프로젝트일정변경 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02348")/* 일정 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08059"/* 일정이 변경되었습니다. */);
	    subject1 = messageService.getString("e3ps.message.ket_message", "08050"/* 일정 변경 사항 */);
	    tableHeader1 = "Task";
	    tableHeader2 = messageService.getString("e3ps.message.ket_message", "05153"/* 변경 전 */);
	    tableHeader3 = messageService.getString("e3ps.message.ket_message", "05154"/* 변경 후 */);
	    tableHeader2_1 = messageService.getString("e3ps.message.ket_message", "07165"/* 계획시작일 */);
	    tableHeader2_2 = messageService.getString("e3ps.message.ket_message", "07166"/* 계획종료일 */);
	    tableHeader3_1 = messageService.getString("e3ps.message.ket_message", "07165"/* 계획시작일 */);
	    tableHeader3_2 = messageService.getString("e3ps.message.ket_message", "07166"/* 계획종료일 */);
	    content = (String) map.get("content"); // content table
	    subject2 = messageService.getString("e3ps.message.ket_message", "01520"/* 변경일 */);
	    subject2Date = DateUtil.getCurrentDateString("d"); // 변경일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08022".equals(type)) { // 위임

	    mailType = messageService.getString("e3ps.message.ket_message", "08022");/* 위임 */
	    mailPboType = (String) pboHash.get("type"); // 유형
	    mailPboName = (String) map.get("mailPboName"); // 작업명
	    subject1 = (String) map.get("subject1"); // 제목
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01205")/* 담당자 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08117"/* 담당자가 변경되었습니다. */);
	    subject2 = messageService.getString("e3ps.message.ket_message", "08091"/* 담당자 변경 사항 */);
	    tableHeader1 = messageService.getString("e3ps.message.ket_message", "05153"/* 변경 전 */);
	    tableHeader2 = messageService.getString("e3ps.message.ket_message", "05154"/* 변경 후 */);
	    column1 = (String) map.get("column1"); // 연구개발4팀 홍길동 책임연구원
	    column2 = (String) map.get("column2"); // 연구개발2팀 이길동 책임연구원
	    subject3 = messageService.getString("e3ps.message.ket_message", "01520"/* 변경일 */);
	    subject3Date = (String) map.get("subject3Date"); // 변경일 2014-05-05
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> <" + subject1 + "> " + titleMsg;

	}
	if ("08109".equals(type)) { // PM변경

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08109");/* PM변경 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "PM",
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08114"/*
						                                          * 프로젝트의 PM이 변경되었습니다.
						                                          */);
	    subject1 = messageService.getString("e3ps.message.ket_message", "08115"/* PM 변경 사항 */);
	    tableHeader1 = messageService.getString("e3ps.message.ket_message", "00969"/* 구분 */);
	    tableHeader2 = messageService.getString("e3ps.message.ket_message", "05153"/* 변경 전 */);
	    tableHeader3 = messageService.getString("e3ps.message.ket_message", "05154"/* 변경 후 */);
	    content = (String) map.get("content"); // content table;
	    subject2 = messageService.getString("e3ps.message.ket_message", "01520"/* 변경일 */);
	    subject2Date = DateUtil.getCurrentDateString("d"); // 변경일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08113"/* 해당 프로젝트를 확인하시기 바랍니다. */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	}

	contentArgs.put("title", title);
	contentArgs.put("host", host);
	contentArgs.put("mailType", mailType);
	contentArgs.put("mailPboType", mailPboType);
	contentArgs.put("mailPboName", mailPboName);
	contentArgs.put("titleMsg", titleMsg);
	contentArgs.put("descriptionMsg", descriptionMsg);

	contentArgs.put("projectNo", projectNo);
	contentArgs.put("projectName", projectName);
	contentArgs.put("partNo", partNo);
	contentArgs.put("partName", partName);
	contentArgs.put("dependancyTaskName", dependancyTaskName);
	contentArgs.put("header1", header1);
	contentArgs.put("header2", header2);
	contentArgs.put("header3", header3);
	contentArgs.put("column1", column1);
	contentArgs.put("column2", column2);
	contentArgs.put("column3", column3);
	contentArgs.put("tableHeader1", tableHeader1);
	contentArgs.put("tableHeader2", tableHeader2);
	contentArgs.put("tableHeader3", tableHeader3);
	contentArgs.put("tableHeader2_1", tableHeader2_1);
	contentArgs.put("tableHeader2_2", tableHeader2_2);
	contentArgs.put("tableHeader3_1", tableHeader3_1);
	contentArgs.put("tableHeader3_2", tableHeader3_2);
	contentArgs.put("content", content);
	contentArgs.put("subject1", subject1);
	contentArgs.put("subject2", subject2);
	contentArgs.put("subject3", subject3);
	contentArgs.put("subject2Date", subject2Date);
	contentArgs.put("subject3Date", subject3Date);
	contentArgs.put("footerMsg", footerMsg);
	contentArgs.put("goLinkURL", goLinkURL);
	contentArgs.put("footerClickMsg", footerClickMsg);

	return contentArgs;
    }

    private Hashtable<String, String> getMailContent(String type, Object pbo, Locale locale, WTUser toUser) throws Exception {

	KETMessageService messageService = new KETMessageService(locale);
	Hashtable<String, String> contentArgs = new Hashtable<String, String>();
	Hashtable<String, Object> pboHash = ClassifyPBOUtil.extractPBOInfo(pbo);

	String pboOid = "";

	if (pbo instanceof Persistable) {
	    pboOid = CommonUtil.getOIDString((Persistable) pbo);
	}

	String host = wtProperties.getProperty("wt.server.codebase"); // http://plm.ket.com/plm
	String title = "";
	String mailType = "";
	String mailPboType = "";
	String mailPboName = "";
	String titleMsg = "";
	String descriptionMsg = "";
	String projectNo = "";
	String projectName = "";
	String partNo = "";
	String partName = "";
	String dependancyTaskName = "";
	String header1 = "";
	String header2 = "";
	String header3 = "";
	String header4 = "";
	String header5 = "";
	String header6 = "";
	String column1 = "";
	String column2 = "";
	String column3 = "";
	String column4 = "";
	String column5 = "";
	String column6 = "";
	String tableHeader1 = "";
	String tableHeader2 = "";
	String tableHeader3 = "";
	String tableHeader2_1 = "";
	String tableHeader2_2 = "";
	String tableHeader3_1 = "";
	String tableHeader3_2 = "";
	String content = "";
	String subject1 = "";
	String subject2 = "";
	String subject3 = "";
	String subject2Date = "";
	String subject3Date = "";
	String footerMsg = "";
	String goLinkURL = "";
	String footerClickMsg = messageService.getString("e3ps.message.ket_message", "08087");/* 클릭하면 해당 화면이 열립니다. */

	if ("08001".equals(type)) { // 검토요청
	    mailType = messageService.getString("e3ps.message.ket_message", "08001");/* 검토요청 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}

	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "00716")/* 검토 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00716")/* 검토 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08002".equals(type)) { // 합의요청

	    mailType = messageService.getString("e3ps.message.ket_message", "08002");/* 합의요청 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "03156")/* 합의 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "03156")/* 합의 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08003".equals(type)) { // 결재요청

	    mailType = messageService.getString("e3ps.message.ket_message", "08003");/* 결재요청 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08004".equals(type)) { // 결재완결

	    mailType = messageService.getString("e3ps.message.ket_message", "08004");/* 결재완결 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00777")/* 결재완료함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08005".equals(type)) { // 수신확인요청

	    mailType = messageService.getString("e3ps.message.ket_message", "08005");/* 수신확인요청 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01931")/* 수신확인 */, ""/*  */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08006".equals(type)) { // 결재참조

	    mailType = messageService.getString("e3ps.message.ket_message", "08006");/* 결재참조 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08047")/* 참조 */, ""/*  */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "02767")/* 참조문서함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08007".equals(type)) { // 결재반려

	    mailType = messageService.getString("e3ps.message.ket_message", "08007");/* 결재반려 */
	    mailPboType = (String) pboHash.get("type");
	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF".equals(template.getName());
		if (isCanWF) {
		    mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
		if (pbo instanceof ProductProject) {
		    ProductProject project = (ProductProject) pbo;
		    if (ProjectHelper.isStopProject(project)) {
			mailPboType = mailPboType + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}
	    } catch (Exception e) {
	    }
	    mailPboName = (String) pboHash.get("title");
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01466")/* 반려 */, ""/*  */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08052"/* 의 {0}가 {1}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "00755")/* 결재 */,
		    messageService.getString("e3ps.message.ket_message", "01466")/* 반려 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    contentArgs.put("approvalLine", getApprovalLine((Persistable) pbo));
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08008".equals(type)) { // Task수행요청

	    E3PSTask task = (E3PSTask) pbo;
	    ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
	    Timestamp tsPlanEndDate = schedule.getPlanEndDate();
	    String planEndDateStr = "";
	    if (tsPlanEndDate != null) {
		planEndDateStr = DateUtil.getTimeFormat(tsPlanEndDate, "yyyy-MM-dd");
	    }
	    mailType = messageService.getString("e3ps.message.ket_message", "08008");/* Task수행요청 */
	    projectNo = task.getProject().getPjtNo();
	    projectName = task.getProject().getPjtName();
	    mailPboName = task.getTaskName();
	    header1 = messageService.getString("e3ps.message.ket_message", "00496"/* Task명 */);
	    column1 = task.getTaskName();
	    header2 = messageService.getString("e3ps.message.ket_message", "00820"/* 계획{0}완료일 */, "");
	    column2 = planEndDateStr;
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08048")/* 수행 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08054"/*
						                                          * Task 수행이 요청되었습니다.
						                                          */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00338"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + KETObjectUtil.getOidString(task);
	    // goLinkURL =
	    // "/plm/jsp/project/ProjectViewFrm.jsp?popup=popup&oid=" +
	    // KETObjectUtil.getOidString(task);
	    title = "[KPLUS][" + mailType + "]" + " <" + projectNo + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08009".equals(type)) { // Task지연

	    E3PSTask task = (E3PSTask) pbo;
	    ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
	    Timestamp tsPlanEndDate = schedule.getPlanEndDate();
	    String planEndDateStr = "";
	    if (tsPlanEndDate != null) {
		planEndDateStr = DateUtil.getTimeFormat(tsPlanEndDate, "yyyy-MM-dd");
	    }
	    mailType = messageService.getString("e3ps.message.ket_message", "08009");/* Task지연 */
	    projectNo = task.getProject().getPjtNo();
	    projectName = task.getProject().getPjtName();
	    mailPboName = task.getTaskName();
	    header1 = messageService.getString("e3ps.message.ket_message", "00496"/* Task명 */);
	    column1 = task.getTaskName();
	    header2 = messageService.getString("e3ps.message.ket_message", "00820"/* 계획{0}완료일 */, "");
	    column2 = planEndDateStr;
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02703")/* 지연 */, ""/*  */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08055"/*
						                                          * Task가 지연중입니다. 작업을 수행하십시오.
						                                          */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00338"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + KETObjectUtil.getOidString(task);
	    title = "[KPLUS][" + mailType + "]" + " <" + projectNo + "> <" + projectName + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08010".equals(type)) { // Task수행요청 선-후행

	    E3PSTask task = (E3PSTask) pbo;
	    E3PSTask dependancyTask = null; // 후행태스크 가져오기
	    // E3PSTaskData dependTaskData = null;

	    QueryResult dependListResult = e3ps.project.beans.TaskDependencyHelper.manager.getDependTaskList1(task);

	    while (dependListResult.hasMoreElements()) {
		TaskDependencyLink dependLink = (TaskDependencyLink) dependListResult.nextElement();
		dependancyTask = (E3PSTask) dependLink.getDependSource();// .getDependTarget();
		// dependTaskData = new E3PSTaskData(dependancyTask);
	    }

	    String planEndDateStr = "";
	    if (dependancyTask != null) {

		ExtendScheduleData schedule = (ExtendScheduleData) dependancyTask.getTaskSchedule().getObject();
		Timestamp tsPlanEndDate = schedule.getPlanEndDate();
		if (tsPlanEndDate != null) {
		    planEndDateStr = DateUtil.getTimeFormat(tsPlanEndDate, "yyyy-MM-dd");
		}
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08010");/* Task수행요청 */
	    dependancyTaskName = (dependancyTask == null) ? "" : dependancyTask.getTaskName(); // 후행태스크명 가져오기
	    projectNo = dependancyTask.getProject().getPjtNo();
	    projectName = dependancyTask.getProject().getPjtName();
	    mailPboName = (task == null) ? "" : task.getTaskName();
	    header1 = messageService.getString("e3ps.message.ket_message", "00496"/* Task명 */);
	    column1 = dependancyTaskName;
	    header2 = messageService.getString("e3ps.message.ket_message", "00820"/* 계획{0}완료일 */, "");
	    column2 = planEndDateStr; // 후행태스크 계획완료일 가져오기
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08048")/* 수행 */,
		    messageService.getString("e3ps.message.ket_message", "02190")/* 요청 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08056"/*
						                                          * 이 완료되었습니다. 다음 Task를 수행하십시오.
						                                          */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00338"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + KETObjectUtil.getOidString(dependancyTask);
	    title = "[KPLUS][" + mailType + "]" + " <" + projectNo + "> <" + dependancyTaskName + "> " + titleMsg;

	} else if ("08011".equals(type)) { // 프로젝트중지

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08011");/* 프로젝트중지 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01760")/* 상태 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08057"/* 프로젝트가 {0}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "02695")/* 중지 */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "02701"/* 중지일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 중지일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08012".equals(type)) { // 프로젝트취소

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08012");/* 프로젝트취소 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01760")/* 상태 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08057"/* 프로젝트가 {0}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "02887")/* 취소 */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "02895"/* 취소일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 취소일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08013".equals(type)) { // 프로젝트완료

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08013");/* 프로젝트완료 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();

	    // 문구 변경 상태변경알림 -> 프로젝트 완료
	    // titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
	    // messageService.getString("e3ps.message.ket_message", "01760")/* 상태 */,
	    // messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);

	    titleMsg = messageService.getString("e3ps.message.ket_message", "03046") + " "
		    + messageService.getString("e3ps.message.ket_message", "02171");/*
					                                             * 프로젝트 완료
					                                             */

	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08057"/* 프로젝트가 {0}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 완료일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08014".equals(type)) { // 프로젝트재시작

	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08014");/* 프로젝트재시작 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01760")/* 상태 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08057"/* 프로젝트가 {0}되었습니다. */,
		    messageService.getString("e3ps.message.ket_message", "02446")/* 재시작 */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "02701"/* 중지일 */);
	    ProjectChangeStop changeStop = getProjectChangeStop(project, "PROJECTSTOPTYPE");
	    column2 = DateUtil.getDateString(changeStop.getChangeDate(), "d"); // 중지일
	    // 가져오기
	    header3 = messageService.getString("e3ps.message.ket_message", "02449"/* 재시작일 */);
	    column3 = DateUtil.getCurrentDateString("d"); // 재시작일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00337"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08017".equals(type)) { // Issue등록

	    ProjectIssue issue = (ProjectIssue) pbo;
	    E3PSProject project = issue.getProject(); // 이슈 연관 프로젝트 가져오기
	    mailType = "CFT요청 등록";/* Issue등록 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "CFT요청 "/* Issue */,
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */);
	    descriptionMsg = "CFT요청이 등록되었습니다.";
	    header1 = "CFT요청";
	    column1 = issue.getSubject(); // 이슈 제목
	    header2 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    PeopleData owner = new PeopleData(issue.getOwner().getPrincipal());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 제기자 가져오기
	    header3 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column3 = DateUtil.getDateString(issue.getPersistInfo().getCreateStamp(), "d"); // 등록일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00335"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08018".equals(type)) { // Issue해결방안등록

	    ProjectIssueAnswer answer = (ProjectIssueAnswer) pbo;
	    ProjectIssue issue = ProjectIssueHelper.manager.getIssue(answer);
	    // ProjectIssue issue = (ProjectIssue) pbo;
	    // ProjectIssueAnswer answer =
	    // ProjectIssueHelper.manager.getIssueAnswer(issue);
	    ProjectIssueData data = new ProjectIssueData(issue);
	    E3PSProject project = data.project; // 이슈 연관 프로젝트 가져오기
	    mailType = "CFT요청 해결방안등록";
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "Issue"/* Issue */,
		    messageService.getString("e3ps.message.ket_message", "03173"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08061"/*
						                                          * Issue의 해결방안이 등록되었습니다.
						                                          */);
	    header1 = "CFT요청";
	    column1 = issue.getSubject(); // 이슈 제목
	    header2 = messageService.getString("e3ps.message.ket_message", "04104"/* 담당자 */);
	    PeopleData charger = new PeopleData(issue.getManager().getPrincipal());
	    column2 = charger.departmentName + " " + charger.name + " " + charger.duty; // 담당자 가져오기
	    header3 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column3 = DateUtil.getDateString(answer.getPersistInfo().getCreateStamp(), "d"); // 등록일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00335"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(issue);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08019".equals(type)) { // Issue완료

	    ProjectIssue issue = (ProjectIssue) pbo;
	    ProjectIssueData data = new ProjectIssueData(issue);
	    E3PSProject project = data.project; // 이슈 연관 프로젝트 가져오기
	    mailType = "CFT요청 완료";
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "CFT요청"/* Issue */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    descriptionMsg = "CFT요청이 완료되었습니다.";
	    header1 = "CFT요청 제목";
	    column1 = issue.getSubject(); // 이슈 제목
	    header2 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    PeopleData owner = new PeopleData(issue.getOwner().getPrincipal());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 제기자 가져오기
	    header3 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column3 = DateUtil.getDateString(data.planDoneDate, "d"); // 완료일
	    // 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00335"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08020".equals(type)) { // ECR담당자지정요청
	    KETChangeRequestExpansion expansion = (KETChangeRequestExpansion) pbo;
	    WTChangeRequest2 wtChangeRequest2 = expansion.getEcr();
	    // 제품 ECR
	    if (wtChangeRequest2 instanceof KETProdChangeRequest) {
		KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
	    }
	    // 금형 ECR
	    else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
		KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목

	    }
	    mailType = messageService.getString("e3ps.message.ket_message", "08020");/* ECR담당자지정요청 */

	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECR",
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08062"/* ECR이 등록되었습니다. {0} */,
		    messageService.getString("e3ps.message.ket_message", "08123"));
	    header1 = messageService.getString("e3ps.message.ket_message", "02431"/* 작성자 */);

	    PeopleData owner = new PeopleData(expansion.getCreator().getPrincipal());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty;// 작성자
	    header2 = messageService.getString("e3ps.message.ket_message", "02428"/* 작성일 */);
	    column2 = DateUtil.getDateString(expansion.getPersistInfo().getCreateStamp(), "d"); // 작성일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(expansion);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08021".equals(type)) { // ECR등록

	    mailType = messageService.getString("e3ps.message.ket_message", "08021");/* ECR등록 */
	    mailPboType = ""; // TODO ECR 번호
	    mailPboName = ""; // TODO ECR 제목
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECR",
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08062"/* ECR이 등록되었습니다. {0} */, ""/*  */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    column1 = ""; // TODO 제기자
	    header2 = messageService.getString("e3ps.message.ket_message", "02521"/* 제기일 */);
	    column2 = ""; // TODO 제기일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = "";
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08023".equals(type)) { // ECR기각
	    WTChangeRequest2 wtChangeRequest2 = (WTChangeRequest2) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08023");/* ECR기각 */

	    // 제품 ECR
	    if (wtChangeRequest2 instanceof KETProdChangeRequest) {
		KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);
	    }
	    // 금형 ECR
	    else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
		KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);

	    }
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECR",
		    messageService.getString("e3ps.message.ket_message", "04101")/* 기각 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08064"/* ECR이 기각되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08092"/* 검토 담당자 */);
	    PeopleData owner = new PeopleData((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty; // 검토 담당자
	    header2 = messageService.getString("e3ps.message.ket_message", "08093"/* 기각일 */);
	    column2 = DateUtil.getDateString(DateUtil.getCurrentTimestamp(), "d"); // 기각일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My ECM");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08024".equals(type)) { // ECR회의참석요청

	    WTChangeRequest2 wtChangeRequest2 = (WTChangeRequest2) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08024");/* ECR회의참석요청 */

	    // 관리자가 아닌 사람도 회의록 요청하는것 같아서 세션 사용자로 바꿈
	    // QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
	    // spec.appendWhere(
	    // new SearchCondition(KETChangeRequestExpansion.class,
	    // "ecrReference.key.id", "=", CommonUtil
	    // .getOIDLongValue((WTChangeRequest2) wtChangeRequest2)), new int[]
	    // { 0 });
	    // QueryResult result = PersistenceHelper.manager.find(spec);
	    // if (result.hasMoreElements()) { // while
	    // (result.hasMoreElements()) {
	    // KETChangeRequestExpansion expansion = (KETChangeRequestExpansion)
	    // result.nextElement();
	    // WTUser charge = (expansion != null) ? expansion.getCharge() :
	    // null;
	    //
	    // PeopleData owner = new PeopleData(charge);
	    // column1 = owner.departmentName + " " + owner.name + " " +
	    // owner.duty;// 요청자
	    //
	    // }

	    PeopleData owner = new PeopleData((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty;// 요청자

	    // 제품 ECR
	    if (wtChangeRequest2 instanceof KETProdChangeRequest) {
		KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
		QueryResult qr = PersistenceHelper.manager.navigate(ecr, "call", KETEcrCallLink.class);
		while (qr.hasMoreElements()) {
		    KETMeetingCall ketMeetingCall = (KETMeetingCall) qr.nextElement();
		    column2 = DateUtil.getDateString(ketMeetingCall.getCallMeetingDate(), "d"); // 회의일시

		}
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);
	    }
	    // 금형 ECR
	    else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
		KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목

		QueryResult qr = PersistenceHelper.manager.navigate(ecr, "call", KETEcrCallLink.class);
		while (qr.hasMoreElements()) {
		    KETMeetingCall ketMeetingCall = (KETMeetingCall) qr.nextElement();
		    column2 = DateUtil.getDateString(ketMeetingCall.getCallMeetingDate(), "d"); // 회의일시
		}

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);

	    }
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECR",
		    messageService.getString("e3ps.message.ket_message", "08094")/* 회의 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08065"/*
						                                          * ECR 검토회의에 참석 요청되었습니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02196"/* 요청자 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "04108"/* 회의일시 */);

	    footerMsg = "";

	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08025".equals(type)) { // ECR회의록지연
	    WTChangeRequest2 wtChangeRequest2 = (WTChangeRequest2) pbo;
	    // 제품 ECR
	    if (wtChangeRequest2 instanceof KETProdChangeRequest) {
		KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);
	    }
	    // 금형 ECR
	    else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
		KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
		mailPboType = ecr.getEcrId(); // ECR 번호
		mailPboName = ecr.getEcrName(); // ECR 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ecr);

	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08025");/* ECR회의록지연 */
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECR",
		    messageService.getString("e3ps.message.ket_message", "08095"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08066"/*
						                                          * ECR 회의록 등록 지연중입니다.
						                                          */);
	    // header1 = messageService.getString("e3ps.message.ket_message", "04108"/* 회의일시 */);
	    // column1 = ""; // 회의일시 삭제
	    footerMsg = "";
	    // goLinkURL = "";
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08026".equals(type)) { // ECO활동수행요청

	    // 제품 ECO
	    if (pbo instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		PeopleData owner = new PeopleData(eco.getCreator().getPrincipal());
		column1 = owner.departmentName + " " + owner.name + " " + owner.duty;// 작성자
		column2 = DateUtil.getDateString(eco.getPersistInfo().getCreateStamp(), "d"); // 작성일
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=&pboOid=" + CommonUtil.getOIDString(eco);
	    }
	    // 금형 ECO
	    else if (pbo instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		PeopleData owner = new PeopleData(eco.getCreator().getPrincipal());
		column1 = owner.departmentName + " " + owner.name + " " + owner.duty;// 작성자
		column2 = DateUtil.getDateString(eco.getPersistInfo().getCreateStamp(), "d"); // 작성일
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);

	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08026");/* ECO활동수행요청 */
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08026")/* ECO활동수행요청 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08067"/*
						                                          * ECO 활동 수행이 요청되었습니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02431"/* 작성자 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02428"/* 작성일 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08027".equals(type)) { // ECN활동수행요청

	    // 제품 ECR
	    if (pbo instanceof KETProdChangeActivity) {
		KETProdChangeActivity eca = (KETProdChangeActivity) pbo;
		KETProdChangeOrder eco = eca.getProdECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		column1 = eca.getCustomName(); // ECN 후속활동명
		column2 = DateUtil.getDateString(eca.getCompleteRequestDate(), "d"); // 완료요청일;
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eca);
	    }
	    // 금형 ECR
	    else if (pbo instanceof KETMoldChangeActivity) {
		KETMoldChangeActivity eca = (KETMoldChangeActivity) pbo;
		KETMoldChangeOrder eco = eca.getMoldECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		column1 = eca.getCustomName(); // ECN 후속활동명
		column2 = DateUtil.getDateString(eca.getCompleteRequestDate(), "d"); // 완료요청일

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eca);

	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08027");/* ECN활동수행요청 */
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECN",
		    messageService.getString("e3ps.message.ket_message", "08096"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08068"/*
						                                          * ECN 후속활동 수행이 요청되었습니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08097"/* ECN 후속활동 */);

	    header2 = messageService.getString("e3ps.message.ket_message", "02178"/* 완료요청일 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08028".equals(type)) { // ECN완료

	    // 제품 ECR
	    if (pbo instanceof KETProdChangeActivity) {
		KETProdChangeActivity eca = (KETProdChangeActivity) pbo;
		KETProdChangeOrder eco = eca.getProdECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		column1 = DateUtil.getDateString(eca.getCompleteDate(), "d"); // 완료일

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);
	    }
	    // 금형 ECR
	    else if (pbo instanceof KETMoldChangeActivity) {
		KETMoldChangeActivity eca = (KETMoldChangeActivity) pbo;
		KETMoldChangeOrder eco = eca.getMoldECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목

		column1 = DateUtil.getDateString(eca.getCompleteDate(), "d"); // 완료일

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);

	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08028");/* ECN완료 */
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08097")/*
					                                          * ECN 후속활동
					                                          */,
		    messageService.getString("e3ps.message.ket_message", "02171")/* 완료 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08069"/*
						                                          * ECN 후속활동이 모두 완료되었습니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08029".equals(type)) { // ECO활동완료

	    // 제품 ECO
	    if (pbo instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);
	    }
	    // 금형 ECO
	    else if (pbo instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);

	    }
	    mailType = messageService.getString("e3ps.message.ket_message", "08029");/* ECO활동완료 */

	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECO",
		    messageService.getString("e3ps.message.ket_message", "03244")/* 활동완료 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08070"/*
						                                          * ECO 활동이 완료되었습니다. 결재를 상신하십시오.
						                                          */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08030".equals(type)) { // ECN지연

	    WTUser ecaCharge = null;
	    String workerId = null;

	    // 제품 ECR
	    if (pbo instanceof KETProdChangeActivity) {
		KETProdChangeActivity eca = (KETProdChangeActivity) pbo;
		KETProdChangeOrder eco = eca.getProdECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		workerId = eca.getWorkerId();
		ecaCharge = (WTUser) CommonUtil.getObject(workerId);

		column1 = eca.getCustomName(); // ECN 후속활동명
		column2 = DateUtil.getDateString(eca.getCompleteRequestDate(), "d"); // 완료요청일
		column3 = ecaCharge.getFullName(); // 담당자

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eca);
	    }
	    // 금형 ECR
	    else if (pbo instanceof KETMoldChangeActivity) {
		KETMoldChangeActivity eca = (KETMoldChangeActivity) pbo;
		KETMoldChangeOrder eco = eca.getMoldECO();
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		workerId = eca.getWorkerId();
		ecaCharge = (WTUser) CommonUtil.getObject(workerId);

		column1 = eca.getCustomName(); // ECN 후속활동명
		column2 = DateUtil.getDateString(eca.getCompleteRequestDate(), "d"); // 완료요청일
		column3 = ecaCharge.getFullName(); // 담당자

		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eca);
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08030");/* ECN지연 */
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08097"),
		    messageService.getString("e3ps.message.ket_message", "02703"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08071"/*
						                                          * ECN 후속활동이 지연중입니다. 활동을 수행하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08097"/* ECN 후속활동 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02178"/* 완료요청일 */);
	    header3 = messageService.getString("e3ps.message.ket_message", "01205"/* 담당자 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08031".equals(type)) { // 배포자료변환완료
	    KETDrawingDistRequest ketDrawingDistRequest = (KETDrawingDistRequest) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08031");/* 배포자료변환완료 */
	    mailPboType = "";
	    mailPboName = ketDrawingDistRequest.getTitle(); // 배포요청서제목
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08098"),
		    messageService.getString("e3ps.message.ket_message", "02171"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08072"/* 변환이 완료되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08099"/* 다운로드 종료일 */);
	    column1 = DateUtil.getDateString(ketDrawingDistRequest.getDownloadExpireDate(), "date"); // 다운로드
	    // 종료일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "08100"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + "<" + mailPboName + "> " + titleMsg;

	} else if ("08032".equals(type)) { // Sample요청
	    KETSamplePart ketSamplePart = (KETSamplePart) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08032");/* Sample요청 */
	    mailPboType = ketSamplePart.getPart().getNumber(); // 부품번호
	    mailPboName = ketSamplePart.getPart().getName(); // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "Sample",
		    messageService.getString("e3ps.message.ket_message", "02190"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08101"/* Sample이 요청되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02196"/* 요청자 */);
	    PeopleData owner = new PeopleData(ketSamplePart.getCreator().getPrincipal());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty; // ECN 요청자
	    header2 = messageService.getString("e3ps.message.ket_message", "08102"/* 요청기한 */);
	    column2 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 요청기한
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08120".equals(type)) { // Sample불출완료
	    KETSamplePart ketSamplePart = (KETSamplePart) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08034");/* Sample불출완료 */
	    mailPboType = ketSamplePart.getPart().getNumber(); // 부품번호
	    mailPboName = ketSamplePart.getPart().getName(); // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08103"),
		    messageService.getString("e3ps.message.ket_message", "02171"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08122"/* Sample이 불출되었습니다 */);
	    header1 = messageService.getString("e3ps.message.ket_message", "01205"/* 담당자 */);
	    PeopleData owner = new PeopleData(ketSamplePart.getUser());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty; // 담당자
	    header2 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column2 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 완료일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08034".equals(type)) { // Sample불출완료
	    KETSamplePart ketSamplePart = (KETSamplePart) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08034");/* Sample불출완료 */
	    mailPboType = ketSamplePart.getPart().getNumber(); // 부품번호
	    mailPboName = ketSamplePart.getPart().getName(); // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08103"),
		    messageService.getString("e3ps.message.ket_message", "02171"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08074"/*
						                                          * Sample 불출되었습니다. 최종 완료 처리를 하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "01205"/* 담당자 */);
	    PeopleData owner = new PeopleData(ketSamplePart.getUser());
	    column1 = owner.departmentName + " " + owner.name + " " + owner.duty; // 담당자
	    header2 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column2 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 완료일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08035".equals(type)) { // 개발품질문제 담당자지정요청

	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) pbo;
	    KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	    mailType = messageService.getString("e3ps.message.ket_message", "08035");/* 개발품질문제 담당자지정요청 */

	    partNo = ketDqmRaise.getPart().getNumber(); // 부품번호
	    partName = ketDqmRaise.getPart().getName(); // 부품명
	    mailPboName = ketDqmHeader.getProblem(); // 품질문제점
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08035"), "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08075"/*
						                                          * 개발품질문제가 등록되었습니다. 담당자를 지정하시기 바랍니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "04630"/* 문제점 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    PeopleData owner = new PeopleData(ketDqmRaise.getCreator().getPrincipal());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 담당자
	    header3 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column3 = DateUtil.getTimeFormat(ketDqmRaise.getCreateTimestamp(), "yyyy-MM-dd"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " + titleMsg;

	} else if ("08036".equals(type)) { // 개발품질문제 검토요청

	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) pbo;
	    KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	    // mailType = messageService.getString("e3ps.message.ket_message", "08036");/* 개발품질문제 검토요청 */
	    mailType = "ISSUE 검토요청";
	    partNo = ketDqmRaise.getPart().getNumber(); // 부품번호
	    partName = ketDqmRaise.getPart().getName(); // 부품명
	    mailPboName = ketDqmHeader.getProblem(); // 품질문제점
	    // titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
	    // messageService.getString("e3ps.message.ket_message", "08036"), "");
	    titleMsg = mailType + " 알림";
	    // descriptionMsg = messageService.getString("e3ps.message.ket_message", "08076"/*개발품질문제 검토가 요청되었습니다.*/);
	    descriptionMsg = "ISSUE 검토가 요청되었습니다.";
	    header1 = messageService.getString("e3ps.message.ket_message", "04630"/* 문제점 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    PeopleData owner = new PeopleData((WTUser) ketDqmRaise.getCreator().getPrincipal());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 담당자
	    header3 = messageService.getString("e3ps.message.ket_message", "08102"/* 요청기한 */);
	    column3 = DateUtil.getTimeFormat(ketDqmRaise.getRequestDate(), "yyyy-MM-dd"); // 요청기한
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " + titleMsg;

	}
	/*
	 * else if ("08037".equals(type)) { // 개발품질문제 담당자변경
	 * 
	 * KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) pbo; KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();
	 * KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	 * 
	 * mailType = messageService.getString("e3ps.message.ket_message", "08037"); 개발품질문제 담당자변경 mailPboType =
	 * ketDqmRaise.getPart().getNumber(); // 부품번호 mailPboName = ketDqmRaise.getPart().getName(); // 부품명 titleMsg =
	 * messageService.getString("e3ps.message.ket_message", "08051" {0} {1} 알림 , messageService.getString("e3ps.message.ket_message",
	 * "08037"), ""); descriptionMsg = messageService.getString("e3ps.message.ket_message", "08077" 개발품질문제 담당자가 변경되었습니다. ); subject1 =
	 * messageService.getString("e3ps.message.ket_message", "04630" 문제점 ); subject2 =
	 * messageService.getString("e3ps.message.ket_message", "08092" 검토 담당자 ); tableHeader1 =
	 * messageService.getString("e3ps.message.ket_message", "05153" 변경 전 ); tableHeader2 =
	 * messageService.getString("e3ps.message.ket_message", "05154" 변경 후 ); content = ""; // content table subject3 =
	 * messageService.getString("e3ps.message.ket_message", "01520" 변경일 ); subject3Date =
	 * DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 변경일 footerMsg =
	 * messageService.getString("e3ps.message.ket_message", "08089" PLM System의 {0}를 확인하시기 바랍니다. , "My Todo"); goLinkURL = host +
	 * "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid; title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " +
	 * titleMsg;
	 * 
	 * }
	 */
	else if ("08038".equals(type)) { // 개발품질문제 검토완료

	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) pbo;
	    KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    KETDqmAction ketDqmAciton = ketDqmHeader.getAction();

	    // mailType = messageService.getString("e3ps.message.ket_message", "08038");/* 개발품질문제 검토완료 */
	    mailType = "ISSUE 검토완료";
	    partNo = ketDqmRaise.getPart().getNumber(); // 부품번호
	    partName = ketDqmRaise.getPart().getName(); // 부품명
	    mailPboName = ketDqmHeader.getProblem(); // 품질문제점
	    // titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
	    // messageService.getString("e3ps.message.ket_message", "08038"), "");
	    titleMsg = mailType + " 알림";
	    // descriptionMsg = messageService.getString("e3ps.message.ket_message", "08078"/*개발품질문제 검토가 완료되었습니다.*/);
	    descriptionMsg = "ISSUE 검토가 완료되었습니다.";
	    header1 = messageService.getString("e3ps.message.ket_message", "04630"/* 문제점 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "08092"/* 검토 담당자 */);
	    PeopleData owner = new PeopleData(ketDqmAciton.getUser());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 검토 담당자
	    header3 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column3 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 완료일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " + titleMsg;

	} else if ("08038-1".equals(type)) { // ISSUE 개선안진행 완료 알림

	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) pbo;
	    KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    KETDqmAction ketDqmAciton = ketDqmHeader.getAction();

	    // mailType = messageService.getString("e3ps.message.ket_message", "08038");/* 개발품질문제 검토완료 */
	    mailType = "ISSUE 개선안진행 완료 <참조>";
	    partNo = ketDqmRaise.getPart().getNumber(); // 부품번호
	    partName = ketDqmRaise.getPart().getName(); // 부품명
	    mailPboName = ketDqmHeader.getProblem(); // 품질문제점
	    // titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
	    // messageService.getString("e3ps.message.ket_message", "08038"), "");
	    titleMsg = "ISSUE 개선안진행 완료 알림";
	    // descriptionMsg = messageService.getString("e3ps.message.ket_message", "08078"/*개발품질문제 검토가 완료되었습니다.*/);
	    descriptionMsg = "ISSUE 개선안진행이 완료되었습니다.";
	    header1 = messageService.getString("e3ps.message.ket_message", "04630"/* 문제점 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "08092"/* 검토 담당자 */);
	    PeopleData owner = new PeopleData(ketDqmAciton.getUser());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 검토 담당자
	    header3 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column3 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 완료일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " + titleMsg;

	} else if ("08039".equals(type)) { // 개발품질문제 종결
	    KETDqmHeader ketDqmHeader = (KETDqmHeader) pbo;
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	    // mailType = messageService.getString("e3ps.message.ket_message", "08039");/* 개발품질문제 종결 */
	    mailType = "ISSUE 종결";
	    partNo = ketDqmRaise.getPart().getNumber(); // 부품번호
	    partName = ketDqmRaise.getPart().getName(); // 부품명
	    mailPboName = ketDqmHeader.getProblem(); // 품질문제점
	    // titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
	    // messageService.getString("e3ps.message.ket_message", "08039"), "");
	    titleMsg = mailType + " 알림";
	    // descriptionMsg = messageService.getString("e3ps.message.ket_message", "08079"/* 개발품질문제가 종결되었습니다. */);
	    descriptionMsg = "ISSUE가 종결되었습니다.";
	    header1 = messageService.getString("e3ps.message.ket_message", "04630"/* 문제점 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "08092"/* 검토 담당자 */);
	    PeopleData owner = new PeopleData(ketDqmClose.getUser());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 검토 담당자
	    header3 = messageService.getString("e3ps.message.ket_message", "02179"/* 완료일 */);
	    column3 = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(), "yyyy-MM-dd"); // 완료일
	    footerMsg = "";
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString((Persistable) ketDqmHeader.getRaise());
	    title = "[KPLUS][" + mailType + "]" + " <" + partNo + "> <" + partName + "> " + titleMsg;

	} else if ("08040".equals(type)) { // 프로그램일정변경

	    KETProgramSchedule schedule = (KETProgramSchedule) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08040");/* 프로그램일정변경 */
	    mailPboType = schedule.getNumber(); // 프로그램번호
	    mailPboName = schedule.getProgramName(); // 프로그램명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "08040"), "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08080"/*
						                                          * 프로그램 일정이 변경되었습니다.
						                                          */);
	    header1 = "PMO";
	    PeopleData data = new PeopleData(toUser);
	    column1 = data.departmentName + " " + data.name + " " + data.duty; // PMO
	    header2 = messageService.getString("e3ps.message.ket_message", "01520"/* 변경일 */);
	    column2 = DateUtil.getDateString(schedule.getPersistInfo().getCreateStamp(), "d"); // 변경일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08124");/* 관련 프로젝트를 확인하시기 바랍니다. */
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(schedule);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08042".equals(type)) { // 초기유동관리지정
	    KETEarlyWarning ew = (KETEarlyWarning) pbo;

	    QueryResult result = null;
	    EarlyWarningTargetLink link = null;
	    KETEarlyWarningTarget ketEarlyWarningTarget = null;
	    String productNo = null;
	    ProductInfo pInfo = null;
	    MoldItemInfo miInfo = null;
	    int count = -1;
	    String ewPartNo = "";
	    String ewpartName = "";
	    ProductProject productProject = null;

	    result = PersistenceHelper.navigate(ew, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE, EarlyWarningTargetLink.class, false);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (EarlyWarningTargetLink) result.nextElement();
		    ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		    productNo = StringUtil.checkNull(ketEarlyWarningTarget.getProductNo());
		    if (CommonUtil.getObject(productNo) instanceof ProductProject) {
			productProject = (ProductProject) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(productProject.getPartNo());
			ewpartName = StringUtil.checkNull(productProject.getPjtName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof MoldItemInfo) {
			miInfo = (MoldItemInfo) CommonUtil.getObject(productNo);

			ewPartNo = StringUtil.checkNull(miInfo.getPartNo());
			ewpartName = StringUtil.checkNull(miInfo.getPartName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof ProductInfo) {
			pInfo = (ProductInfo) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(pInfo.getPNum());
			ewpartName = StringUtil.checkNull(pInfo.getPName());
			count++;
		    }
		}
	    }

	    if (count > 0) {
		ewPartNo = ewPartNo + "외 " + count + "건";
		ewpartName = ewpartName + "외 " + count + "건";
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08042");/* 초기유동관리지정 */
	    mailPboType = ewPartNo; // 부품번호
	    mailPboName = ewpartName; // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02814"), "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08082"/*
						                                          * 초기유동관리가 지정되었습니다. 활동 계획을 수립하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02431"/* 작성자 */);
	    PeopleData peopleData = new PeopleData(ew.getCreator().getPrincipal());
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // 작성자
	    header2 = messageService.getString("e3ps.message.ket_message", "02428"/* 작성일 */);
	    column2 = DateUtil.getDateString(ew.getPersistInfo().getCreateStamp(), "d"); // 작성일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ew);
	    ;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08043".equals(type)) { // 초기유동관리계획완료 (실적보고)
	    KETEarlyWarningPlan ewp = (KETEarlyWarningPlan) pbo;

	    KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewp);

	    QueryResult result = null;
	    EarlyWarningTargetLink link = null;
	    KETEarlyWarningTarget ketEarlyWarningTarget = null;
	    String productNo = null;
	    ProductInfo pInfo = null;
	    MoldItemInfo miInfo = null;
	    int count = -1;
	    String ewPartNo = "";
	    String ewpartName = "";
	    ProductProject productProject = null;

	    result = PersistenceHelper.navigate(ew, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE, EarlyWarningTargetLink.class, false);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (EarlyWarningTargetLink) result.nextElement();
		    ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		    productNo = StringUtil.checkNull(ketEarlyWarningTarget.getProductNo());
		    if (CommonUtil.getObject(productNo) instanceof ProductProject) {
			productProject = (ProductProject) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(productProject.getPartNo());
			ewpartName = StringUtil.checkNull(productProject.getPjtName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof MoldItemInfo) {
			miInfo = (MoldItemInfo) CommonUtil.getObject(productNo);

			ewPartNo = StringUtil.checkNull(miInfo.getPartNo());
			ewpartName = StringUtil.checkNull(miInfo.getPartName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof ProductInfo) {
			pInfo = (ProductInfo) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(pInfo.getPNum());
			ewpartName = StringUtil.checkNull(pInfo.getPName());
			count++;
		    }
		}
	    }

	    if (count > 0) {
		ewPartNo = ewPartNo + "외 " + count + "건";
		ewpartName = ewpartName + "외 " + count + "건";
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08043");/* 초기유동관리계획완료 */
	    mailPboType = ewPartNo; // 부품번호
	    mailPboName = ewpartName; // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02809"),
		    messageService.getString("e3ps.message.ket_message", "08104"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08083"/*
						                                          * 초기유동관리 활동 기간이 만료되었습니다. 실적을 등록하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08107"/* 활동 기간 */);
	    column1 = DateUtil.getDateString(ew.getStartDate(), "d") + " ~ " + DateUtil.getDateString(ew.getEndDate(), "d"); // 활동 기간
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ewp);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08044".equals(type)) { // 초기유동관리계획완료 (해제신청)
	    KETEarlyWarningResult ewr = (KETEarlyWarningResult) pbo;
	    KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewr);

	    QueryResult result = null;
	    EarlyWarningTargetLink link = null;
	    KETEarlyWarningTarget ketEarlyWarningTarget = null;
	    String productNo = null;
	    ProductInfo pInfo = null;
	    MoldItemInfo miInfo = null;
	    int count = -1;
	    String ewPartNo = "";
	    String ewpartName = "";
	    ProductProject productProject = null;

	    result = PersistenceHelper.navigate(ew, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE, EarlyWarningTargetLink.class, false);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (EarlyWarningTargetLink) result.nextElement();
		    ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		    productNo = StringUtil.checkNull(ketEarlyWarningTarget.getProductNo());
		    if (CommonUtil.getObject(productNo) instanceof ProductProject) {
			productProject = (ProductProject) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(productProject.getPartNo());
			ewpartName = StringUtil.checkNull(productProject.getPjtName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof MoldItemInfo) {
			miInfo = (MoldItemInfo) CommonUtil.getObject(productNo);

			ewPartNo = StringUtil.checkNull(miInfo.getPartNo());
			ewpartName = StringUtil.checkNull(miInfo.getPartName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof ProductInfo) {
			pInfo = (ProductInfo) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(pInfo.getPNum());
			ewpartName = StringUtil.checkNull(pInfo.getPName());
			count++;
		    }
		}
	    }

	    if (count > 0) {
		ewPartNo = ewPartNo + "외 " + count + "건";
		ewpartName = ewpartName + "외 " + count + "건";
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08044");/* 초기유동관리계획완료 */
	    mailPboType = ewPartNo; // 부품번호
	    mailPboName = ewpartName; // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02809"),
		    messageService.getString("e3ps.message.ket_message", "08105"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08084"/*
						                                          * 초기유동관리 활동 기간이 만료되었습니다. 해제신청을 수행하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08107"/* 활동 기간 */);
	    column1 = DateUtil.getDateString(ew.getStartDate(), "d") + " ~ " + DateUtil.getDateString(ew.getEndDate(), "d"); // 활동 기간
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ewr);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08045".equals(type)) { // 초기유동관리해제요청
	    KETEarlyWarningEndReq ewe = (KETEarlyWarningEndReq) pbo;

	    KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewe);

	    QueryResult result = null;
	    EarlyWarningTargetLink link = null;
	    KETEarlyWarningTarget ketEarlyWarningTarget = null;
	    String productNo = null;
	    ProductInfo pInfo = null;
	    MoldItemInfo miInfo = null;
	    int count = -1;
	    String ewPartNo = "";
	    String ewpartName = "";
	    ProductProject productProject = null;

	    result = PersistenceHelper.navigate(ew, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE, EarlyWarningTargetLink.class, false);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (EarlyWarningTargetLink) result.nextElement();
		    ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		    productNo = StringUtil.checkNull(ketEarlyWarningTarget.getProductNo());
		    if (CommonUtil.getObject(productNo) instanceof ProductProject) {
			productProject = (ProductProject) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(productProject.getPartNo());
			ewpartName = StringUtil.checkNull(productProject.getPjtName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof MoldItemInfo) {
			miInfo = (MoldItemInfo) CommonUtil.getObject(productNo);

			ewPartNo = StringUtil.checkNull(miInfo.getPartNo());
			ewpartName = StringUtil.checkNull(miInfo.getPartName());
			count++;
		    } else if (CommonUtil.getObject(productNo) instanceof ProductInfo) {
			pInfo = (ProductInfo) CommonUtil.getObject(productNo);
			ewPartNo = StringUtil.checkNull(pInfo.getPNum());
			ewpartName = StringUtil.checkNull(pInfo.getPName());
			count++;
		    }
		}
	    }

	    if (count > 0) {
		ewPartNo = ewPartNo + "외 " + count + "건";
		ewpartName = ewpartName + "외 " + count + "건";
	    }

	    mailType = messageService.getString("e3ps.message.ket_message", "08045");/* 초기유동관리해제요청 */
	    mailPboType = ewPartNo; // 부품번호
	    mailPboName = ewpartName; // 부품명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "02809"),
		    messageService.getString("e3ps.message.ket_message", "08106"));
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08085"/*
						                                          * 초기유동관리 해제신청이 등록되었습니다. 해제판정을 수행하십시오.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08107"/* 활동 기간 */);
	    column1 = DateUtil.getDateString(ew.getStartDate(), "d") + " ~ " + DateUtil.getDateString(ew.getEndDate(), "d"); // 활동 기간
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(ew);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08046".equals(type)) { // 프로젝트등록
	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08046");/* 프로젝트등록 */
	    if (project instanceof MoldProject) {
		MoldProject moldProject = (MoldProject) project;
		mailPboType = moldProject.getMoldInfo().getDieNo(); // 프로젝트번호
		mailPboName = moldProject.getMoldInfo().getPartName(); // 프로젝트명
	    } else {
		mailPboType = project.getPjtNo(); // 프로젝트번호
		mailPboName = project.getPjtName(); // 프로젝트명
	    }
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01760")/* 상태 */,
		    messageService.getString("e3ps.message.ket_message", "01491")/* 변경 */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08086"/* 프로젝트가 등록되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = StringUtil.checkNull(peopleData.people.getName()); // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08088");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(project);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08108".equals(type)) { // 의뢰접수요청
	    KETDevelopmentRequest request = (KETDevelopmentRequest) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08108");/* 의뢰접수요청 */
	    mailPboType = request.getNumber(); // 개발검토의뢰번호
	    mailPboName = request.getDevProductName(); // 개발검토의뢰명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08112"/*
						                                          * 이 등록되어 의뢰 접수가 요청되었습니다.
						                                          */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02431"/* 작성자 */);
	    PeopleData peopleData = new PeopleData(request.getCreator().getPrincipal());
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // 작성자
	    header2 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column2 = DateUtil.getDateString(request.getPersistInfo().getCreateStamp(), "d"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08121".equals(type)) { // 금형프로젝트등록
	    MoldProject moldProject = (MoldProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08121");/* 금형프로젝트등록 */
	    MoldItemInfo info = moldProject.getMoldInfo();
	    if ("구매품".equals(info.getItemType())) {
		mailType = "구매프로젝트등록";
	    }
	    mailPboType = moldProject.getMoldInfo().getDieNo(); // 금형프로젝트번호
	    mailPboName = moldProject.getMoldInfo().getPartName(); // 금형프로젝트명
	    String prodPjtNo = "";
	    String prodPjtname = "";
	    ProductProject prodProject = moldProject.getMoldInfo().getProject();
	    prodPjtNo = prodProject.getPjtNo();
	    prodPjtname = prodProject.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08086"/* 프로젝트가 등록되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "02630"/* 제품프로젝트 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02630"/* 제품프로젝트 */) + " "
		    + messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    header3 = messageService.getString("e3ps.message.ket_message", "01058"/* 금형담당자 */);
	    header4 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column1 = "<" + prodPjtNo + ">" + "<" + prodPjtname + ">";
	    WTUser pm = ProjectUserHelper.manager.getPM(prodProject);
	    PeopleData peopleData = new PeopleData(pm);
	    column2 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty;// 제품프로젝트 PM
	    WTUser m_pm = ProjectUserHelper.manager.getPM(moldProject);
	    PeopleData m_peopleData = new PeopleData(m_pm);
	    column3 = m_peopleData.departmentName + " " + m_peopleData.name + " " + m_peopleData.duty; // 금형프로젝트 PM
	    column4 = DateUtil.getCurrentDateString("d"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08088");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(moldProject);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;
	} else if ("08122".equals(type)) { // 프로젝트등록
	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08046");/* 프로젝트등록 */
	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01310")/* 등록 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08086"/* 프로젝트가 등록되었습니다. */) + " "
		    + messageService.getString("e3ps.message.ket_message", "08119");/*
					                                             * 담당자를 지정하십시오 .
					                                             */
	    header1 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column1 = DateUtil.getCurrentDateString("d"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(project);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;
	} else if ("08123".equals(type)) { // 의뢰접수요청 검토 취소
	    KETDevelopmentRequest request = (KETDevelopmentRequest) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08108");/* 의뢰접수요청 */
	    mailPboType = request.getNumber(); // 개발검토의뢰번호
	    mailPboName = request.getDevProductName(); // 개발검토의뢰명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "00733")/* 검토취소 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08125"/* 이 검토취소 되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "08126"/* 검토취소자 */);
	    PeopleData peopleData = new PeopleData(getReqrecipientUser2(request));
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // 검토취소자
	    header2 = messageService.getString("e3ps.message.ket_message", "08127"/* 검토취소일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 검토취소일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/*
					                                             * PLM System의 {0}를 확인하시기 바랍니다.
					                                             */,
		    messageService.getString("e3ps.message.ket_message", "00777")/* 결재완료함 */);
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;
	} else if ("08124".equals(type)) { // ECO, SAP I/F 실패

	    // 제품 ECO
	    if (pbo instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);
	    }
	    // 금형 ECO
	    else if (pbo instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) pbo;
		mailPboType = eco.getEcoId(); // ECO 번호
		mailPboName = eco.getEcoName(); // ECO 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(eco);

	    }
	    mailType = messageService.getString("e3ps.message.ket_message", "08130");/* SAP I/F 실패 */

	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "ECO",
		    messageService.getString("e3ps.message.ket_message", "08130")/*
					                                          * SAP I / F 실패
					                                          */);
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08131"/*
						                                          * ECO에서 SAP I/F가 실패하였습니다.
						                                          */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08132"/* 관련 ECO를 확인하시기 바랍니다. */);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08128".equals(type)) { // 신규프로젝트
	    E3PSProject project = (E3PSProject) pbo;
	    mailType = messageService.getString("e3ps.message.ket_message", "08128");/* 신규프로젝트 */
	    mailPboType = project.getPjtNo(); // 프로젝트번호
	    mailPboName = project.getPjtName(); // 프로젝트명
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */,
		    messageService.getString("e3ps.message.ket_message", "01795")/* 생성 */, "");
	    descriptionMsg = messageService.getString("e3ps.message.ket_message", "08129"/* 프로젝트가 생성되었습니다. */);
	    header1 = messageService.getString("e3ps.message.ket_message", "00370"/* PM */);
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    PeopleData peopleData = new PeopleData(pm);
	    column1 = peopleData.departmentName + " " + peopleData.name + " " + peopleData.duty; // PM
	    header2 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column2 = DateUtil.getCurrentDateString("d"); // 등록일
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089", "My Project");/* PLM System의 {0}를 확인하시기 바랍니다. */
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(project);
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;
	} else if ("08129".equals(type)) { // 개발품질문제 지연
	    KETDqmHeader dqm = (KETDqmHeader) pbo;
	    mailPboType = dqm.getProblemNo(); // 개발품질문제 번호
	    mailPboName = dqm.getProblem(); // 개발품질문제 내용

	    column1 = dqm.getProblem(); // 개발품질문제 내용
	    KETDqmRaise raise = (KETDqmRaise) dqm.getRaise();
	    column2 = DateUtil.getDateString(raise.getRequestDate(), "d"); // 완료요청일
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(dqm);
	    mailType = "Issue";
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "Issue",
		    messageService.getString("e3ps.message.ket_message", "02703"));
	    descriptionMsg = "Issue 요청기한이 지났습니다. 검토하시기 바랍니다.";
	    header1 = messageService.getString("e3ps.message.ket_message", "09002"/* 문제내용 */);
	    header2 = messageService.getString("e3ps.message.ket_message", "02178"/* 완료요청일 */);
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08133".equals(type)) { // QnA 메일발송
	    ImproveBoard board = (ImproveBoard) pbo;

	    ImproveBoard parent = (ImproveBoard) board.getParent();

	    ImproveBoardData parentdata = new ImproveBoardData(parent, 1);
	    ImproveBoardData childdata = new ImproveBoardData(board, 1);

	    mailPboType = "Q&A";
	    mailPboName = parentdata.getTitle(); // QnA 제목

	    column1 = parentdata.getWriteDate(); // 작성일
	    column2 = (String) parentdata.getWebEditorText(); // Q&A내용
	    column3 = board.getUser().getFullName() + " <" + childdata.getStateStr() + " >"; // 담당자 <상태>
	    column4 = childdata.getWebEditorText().replaceAll(System.getProperty("line.separator"), "<br>"); // Q&A답변 내용

	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(board);
	    mailType = "Q&A상태변경"; /* Q&A상태변경 */
	    titleMsg = "Q&A상태변경 알림";
	    descriptionMsg = "상태가 변경되었습니다.";
	    header1 = "작성일";
	    header2 = "Q&A 내용";
	    header3 = "담당자 / Q&A 상태";
	    header4 = "Q&A 답변 내용";

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "Q&A");
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08134".equals(type)) { // Task 현황 메일 발송

	    KETDashBoardMailSetting mailSet = (KETDashBoardMailSetting) pbo;

	    SqlSessionFactory factory = SqlMapSessionFactory.getSqlSessionFactory();

	    SqlSession session = factory.openSession();
	    try {
		contentArgs.put("taskProgressList", getDashBoardTaskProgressList((Persistable) pbo, session));
		contentArgs.put("taskDelayList", getDashBoardTaskDelayList((Persistable) pbo, session));
		contentArgs.put("taskPilotList", getDashBoardTaskPilotList((Persistable) pbo, session));
	    } catch (Exception e) {
		e.printStackTrace();
		System.out.println("Task 현황 메일 발송 에러!");
	    } finally {
		session.close();
	    }

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c = Calendar.getInstance();

	    String currentDate = formatter.format(c.getTime());

	    c.add(Calendar.DATE, -10);

	    String startDate = formatter.format(c.getTime());

	    c.add(Calendar.DATE, 20);

	    String endDate = formatter.format(c.getTime());

	    contentArgs.put("Date", startDate + " ~ " + endDate);

	    mailPboType = "DashBoard";

	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(mailSet);
	    mailType = " ProjectTask 진행현황";

	    titleMsg = " (" + currentDate + ")";

	    title = "[KPLUS]" + mailType + titleMsg;

	} else if ("08135".equals(type)) {

	    if (pbo instanceof KETSalesProject) {
		KETSalesProject salespjt = (KETSalesProject) pbo;
		mailPboType = salespjt.getProjectNo(); // ECO 번호
		mailPboName = salespjt.getProjectName(); // ECO 제목
		goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(salespjt);
		mailType = "영업 프로젝트카드";/* SAP I/F 실패 */

		titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "영업", "프로젝트카드 변경"/*
								                                                           * SAP I/F 실패
								                                                           */);
		descriptionMsg = salespjt.getBigo().replaceAll(System.getProperty("line.separator"), "<br>");
		footerMsg = "변경내용을 확인하시기 바랍니다.";
		title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	    }
	} else if ("08136".equals(type)) { // Issue 위임

	    ProjectIssue issue = (ProjectIssue) pbo;
	    WTPrincipalReference manager = (WTPrincipalReference) issue.getManager();

	    E3PSProject project = issue.getProject(); // 이슈 연관 프로젝트 가져오기
	    mailType = "Issue " + messageService.getString("e3ps.message.ket_message", "02233");/* Issue 위임 */

	    mailPboType = project.getPjtNo();
	    mailPboName = project.getPjtName();
	    titleMsg = messageService.getString("e3ps.message.ket_message", "08051"/* {0} {1} 알림 */, "Issue"/* Issue */,
		    messageService.getString("e3ps.message.ket_message", "02233")/* 위임 */);
	    // descriptionMsg = messageService.getString("e3ps.message.ket_message", "00038"/* {0}이(가) {1}에게 위임하였습니다.
	    // */,SessionHelper.manager.getPrincipalReference().getFullName(),
	    // manager.getFullName()/* 등록 */);

	    if ("Y".equals(issue.getAegisTrans())) {
		titleMsg = titleMsg + "(AEGIS전송)";
		ProjectIssueAnswer answer = null;

		descriptionMsg = issue.getAegisCutOffComment();

		issue.setAegisCutOffComment(StringUtil.subByteData(issue.getAegisComment().toString(), 4000));// AEGIS 이관시 BLOB 데이터 연동에 어려움이
		                                                                                              // 있어 varchar2데이터로 변환(4000가
		                                                                                              // 넘으면 ... 붙여서 4000byte맞춰서 변환)
		issue = ProjectIssueHelper.manager.modifyProjectIssue(issue);

	    }

	    header1 = messageService.getString("e3ps.message.ket_message", "02311"/* 이슈 제목 */);
	    column1 = issue.getSubject(); // 이슈 제목
	    header2 = messageService.getString("e3ps.message.ket_message", "02523"/* 제기자 */);
	    PeopleData owner = new PeopleData(issue.getOwner().getPrincipal());
	    column2 = owner.departmentName + " " + owner.name + " " + owner.duty; // 제기자 가져오기
	    header3 = messageService.getString("e3ps.message.ket_message", "01335"/* 등록일 */);
	    column3 = DateUtil.getDateString(issue.getPersistInfo().getCreateStamp(), "d"); // 등록일 가져오기
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00335"));
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + pboOid;
	    title = "[KPLUS][" + mailType + "]" + " <" + mailPboType + "> <" + mailPboName + "> " + titleMsg;

	} else if ("08137".equals(type)) { // 결재지연(3일이상) 알람

	    WorkItemDTO dto = (WorkItemDTO) pbo;

	    column1 = dto.getTargetCnt() + "건";
	    mailPboType = "WorkItem";
	    mailPboName = "3일 이상";
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=KETMyWorkDelay";
	    mailType = "결재대기함";
	    titleMsg = "처리 지연 중인 결재 목록을 확인하시기 바랍니다.";

	    header1 = "결재대기";

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */,
		    messageService.getString("e3ps.message.ket_message", "00760")/* 결재대기함 */);
	    title = "[KPLUS][" + mailType + "]" + titleMsg;

	} else if ("08138".equals(type) || "08139".equals(type)) { // 고객요청사항 진행요청

	    System.out.println("요구사항 진행요청 08138");

	    KETIssueTask it = (KETIssueTask) pbo;
	    KETIssueMaster im = it.getIssueMaster();

	    String sendType = "진행";

	    if ("08139".equals(type))
		sendType = "참조";

	    mailType = "요구사항";
	    mailPboType = im.getReqNo();
	    mailPboName = it.getSubject();
	    titleMsg = "[" + mailType + "-" + sendType + "] " + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "상세 요청사항 진행이 요청되었습니다.";
	    header1 = "요청자";
	    column1 = im.getManager().getFullName();
	    header2 = "요청일자";
	    column2 = DateUtil.getCurrentDateString("d");
	    header3 = "요청 완료 일자";
	    column3 = DateUtil.getDateString(it.getRequestDate(), "d");
	    header4 = "요구사항";
	    column4 = StringUtil.checkNull(im.getMainSubject()).replaceAll("\r\n", "<br/>");
	    header5 = "진행요청사항";
	    column5 = it.getSubject();

	    if (StringUtils.isNotEmpty(it.getReStartReason())) {
		header6 = "재시작사유";
		column6 = it.getReStartReason().replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	    }

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "요구사항관리 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(it);
	    title = "[KPLUS][" + mailType + "-" + sendType + "] " + mailPboName + " <" + mailPboType + ">";

	} else if ("08140".equals(type)) { // 상세 요청사항 완료 공지

	    System.out.println("세부 요청사항 완료공지 08140");

	    KETIssueTask it = (KETIssueTask) pbo;
	    KETIssueMaster im = it.getIssueMaster();

	    mailType = "세부요청사항";
	    mailPboType = im.getReqNo();
	    mailPboName = it.getSubject();
	    titleMsg = "[" + mailType + "-완료] " + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "세부 요청사항이 완료되었습니다.";
	    header1 = "요청명";
	    column1 = im.getReqName();
	    header2 = "세부 담당자";
	    column2 = it.getWorker().getFullName();
	    header3 = "세부 요청 완료 일자";
	    column3 = DateUtil.getDateString(it.getRequestDate(), "d");
	    header4 = "요구사항";
	    column4 = StringUtil.checkNull(im.getMainSubject()).replaceAll("\r\n", "<br/>");
	    header5 = "진행요청사항";
	    column5 = it.getSubject();

	    header6 = "진행사항";
	    column6 = it.getWebEditorText().toString();

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "요구사항관리 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(im);
	    title = "[KPLUS][" + mailType + "-완료] " + mailPboName + " <" + mailPboType + ">";

	} else if ("08141".equals(type)) { // 고객요청사항 완료요청

	    System.out.println("고객요구사항 완료요청 08141");

	    KETIssueMaster im = (KETIssueMaster) pbo;

	    mailType = "요구사항";
	    mailPboType = im.getReqNo();
	    mailPboName = im.getReqName();
	    titleMsg = "[" + mailType + "-완료]" + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "모든 상세 요청사항이 완료되었습니다. 요구사항을 완료해 주시기 바랍니다.";
	    header1 = "요청 No";
	    column1 = im.getReqNo();
	    header2 = "요청명";
	    column2 = im.getReqName();
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "요구사항관리 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(im);
	    title = "[KPLUS][" + mailType + "-완료]" + mailPboName + " <" + mailPboType + ">";

	} else if ("08142".equals(type)) { // 검토프로젝트 담당자지정요청

	    E3PSProject project = (E3PSProject) pbo;

	    mailType = messageService.getString("e3ps.message.ket_message", "08041");/* 검토프로젝트 담당자지정요청 */

	    projectNo = project.getPjtNo();
	    projectName = project.getPjtName();

	    mailPboType = projectNo;
	    mailPboName = projectName;

	    titleMsg = "검토프로젝트 PM을 지정하시기 바랍니다";

	    header1 = "프로젝트No";
	    column1 = projectNo;
	    header2 = "프로젝트명";
	    column2 = projectName;
	    descriptionMsg = "검토 프로젝트 PM을 지정해야 프로젝트가 시작됩니다.";
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "My Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=KETMYTODO";
	    title = "[KPLUS][" + mailType + "]" + " <" + projectNo + "> <" + projectName + "> " + titleMsg;

	} else if ("08143".equals(type)) {

	    List<Map<String, String>> lastTargetPjtList = (List<Map<String, String>>) pbo;

	    contentArgs.put("projectList", getPjtMainScheduleList(lastTargetPjtList));

	    contentArgs.put("delayCnt", lastTargetPjtList.get(0).get("delayCnt"));

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c = Calendar.getInstance();

	    String currentDate = formatter.format(c.getTime());

	    mailType = " Project 주요일정 현황";

	    titleMsg = " (" + currentDate + ")";

	    title = "[KPLUS]" + mailType + titleMsg;
	} else if ("08144".equals(type) || "08145".equals(type)) { // 고객투자비 관리 진행요청

	    System.out.println("고객투자비 관리 진행요청 08144");

	    KETInvestTask it = (KETInvestTask) pbo;
	    KETInvestMaster im = it.getInvestMaster();

	    String sendType = "증빙자료요청";

	    if ("08145".equals(type))
		sendType = "참조";

	    mailType = "고객투자비";
	    mailPboType = im.getReqNo();
	    mailPboName = it.getSubject();
	    titleMsg = "[" + mailType + "-" + sendType + "] " + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "고객투자비 회수를 위한 증빙자료 준비가 요청되었습니다.";
	    header1 = "요청자";
	    column1 = im.getManager().getFullName();
	    header2 = "요청일자";
	    column2 = DateUtil.getCurrentDateString("d");
	    header3 = "요청 완료 일자";
	    column3 = DateUtil.getDateString(it.getRequestDate(), "d");
	    header4 = "진행요청사항";
	    column4 = it.getSubject();

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "고객투자비 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(it);
	    title = "[KPLUS][" + mailType + "-" + sendType + "] " + mailPboName + " <" + mailPboType + ">";

	} else if ("08146".equals(type)) { // 상세 요청사항 완료 공지

	    System.out.println("세부 요청사항 완료공지 08146");

	    KETInvestTask it = (KETInvestTask) pbo;
	    KETInvestMaster im = it.getInvestMaster();

	    mailType = "고객투자비";
	    mailPboType = im.getReqNo();
	    mailPboName = it.getSubject();
	    titleMsg = "[" + mailType + "-완료] " + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "고객투자비 회수를 위한 증빙자료 준비가 완료되었습니다.";
	    header1 = "요청명";
	    column1 = im.getReqName();
	    header2 = "세부 담당자";
	    column2 = it.getWorker().getFullName();
	    header3 = "세부 요청 완료 일자";
	    column3 = DateUtil.getDateString(it.getRequestDate(), "d");
	    header4 = "진행요청사항";
	    column4 = it.getSubject();

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "고객투자비 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(im);
	    title = "[KPLUS][" + mailType + "-증빙자료완료] " + mailPboName + " <" + mailPboType + ">";

	} else if ("08147".equals(type)) { // 고객투자비 관리 완료요청

	    System.out.println("고객투자비 관리 완료요청 08147");

	    KETInvestMaster im = (KETInvestMaster) pbo;

	    mailType = "고객투자비";
	    mailPboType = im.getReqNo();
	    mailPboName = im.getReqName();
	    titleMsg = "[" + mailType + "-완료]" + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "모든 증빙자료 요청건이 완료되었습니다.고객투자비 회수진행 바랍니다.";
	    header1 = "투자품의번호 No";
	    column1 = im.getReqNo();
	    header2 = "투자품의명";
	    column2 = im.getReqName();
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "고객투자비 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(im);
	    title = "[KPLUS][" + mailType + "-증빙자료완료]" + mailPboName + " <" + mailPboType + ">";

	} else if ("08148".equals(type)) { // 영업담당자 접수요청

	    KETInvestMaster im = (KETInvestMaster) pbo;

	    mailType = "고객투자비";
	    mailPboType = im.getReqNo();
	    mailPboName = im.getReqName();
	    titleMsg = "[" + mailType + "-접수요청]" + mailPboName + " <" + mailPboType + ">";

	    header1 = "투자품의번호 No";
	    column1 = mailPboType;
	    header2 = "투자품의명";
	    column2 = mailPboName;
	    descriptionMsg = "고객투자비 회수관리 접수요청 되었습니다.";
	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "고객투자비 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=KETMYTODO";
	    title = "[KPLUS][" + mailType + "-접수요청]" + mailPboName + " <" + mailPboType + ">";

	} else if ("08149".equals(type)) { // 상세 요청사항 반려 공지

	    System.out.println("세부 요청사항 반려공지 08149");

	    KETIssueTask it = (KETIssueTask) pbo;
	    KETIssueMaster im = it.getIssueMaster();

	    mailType = "세부요청사항";
	    mailPboType = im.getReqNo();
	    mailPboName = it.getSubject();
	    titleMsg = "[" + mailType + "-반려] " + mailPboName + " <" + mailPboType + ">";
	    descriptionMsg = "세부 요청사항이 반려되었습니다.";
	    header1 = "요청명";
	    column1 = im.getReqName();
	    header2 = "세부 담당자";
	    column2 = it.getWorker().getFullName();
	    header3 = "세부 요청 완료 일자";
	    column3 = DateUtil.getDateString(it.getRequestDate(), "d");
	    header4 = "고객요구사항";
	    column4 = StringUtil.checkNull(im.getMainSubject()).replaceAll("\r\n", "<br/>");
	    header5 = "진행요청사항";
	    column5 = it.getSubject();

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "고객대응관리 Todo");
	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=" + CommonUtil.getOIDString(im);
	    title = "[KPLUS][" + mailType + "-반려] " + mailPboName + " <" + mailPboType + ">";

	} else if ("08150".equals(type)) { // 퇴사예정자 미처리 업무 점검 확인 메일 알람

	    WorkItemDTO dto = (WorkItemDTO) pbo;

	    column1 = "해당 팀에 퇴사예정자가 발생하였습니다.<br/>퇴사 전에 미처리 업무가 정리될 수 있도록 퇴사예정자의 미처리 업무 진행상황을 점검해주시기 바랍니다.<br/>퇴사 이후 처리되지 않은 미처리 업무건에 대해서는 별도 집계하여 경영진에 보고대상이 되므로 각별히 유의하여 주시기 바랍니다.<br/>";
	    mailPboType = "퇴사예정자";

	    mailPboName = dto.getCommand().split(",").length + "명";

	    goLinkURL = host + "/jsp/SSOLogin.jsp?mode=mail&pboOid=KETRetireWorkDelay";
	    mailType = "미처리업무";
	    titleMsg = "퇴사 예정자의 미처리 업무 목록을 검토하시기 바랍니다.";

	    header1 = "미처리업무";

	    header2 = "퇴사예정자";
	    column2 = dto.getCommand();

	    footerMsg = messageService.getString("e3ps.message.ket_message", "08089"/* PLM System의 {0}를 확인하시기 바랍니다. */, "잔여업무현황");
	    title = "[KPLUS][" + mailType + "]" + titleMsg;

	}

	contentArgs.put("title", title);
	contentArgs.put("host", host);
	contentArgs.put("mailType", mailType);
	contentArgs.put("mailPboType", mailPboType);
	contentArgs.put("mailPboName", mailPboName);
	contentArgs.put("titleMsg", titleMsg);
	contentArgs.put("descriptionMsg", descriptionMsg);

	contentArgs.put("projectNo", projectNo);
	contentArgs.put("projectName", projectName);
	contentArgs.put("partNo", partNo);
	contentArgs.put("partName", partName);
	contentArgs.put("dependancyTaskName", dependancyTaskName);
	contentArgs.put("header1", header1);
	contentArgs.put("header2", header2);
	contentArgs.put("header3", header3);
	contentArgs.put("header4", header4);
	contentArgs.put("header5", header5);
	contentArgs.put("header6", header6);
	contentArgs.put("column1", column1);
	contentArgs.put("column2", column2);
	contentArgs.put("column3", column3);
	contentArgs.put("column4", column4);
	contentArgs.put("column5", column5);
	contentArgs.put("column6", column6);
	contentArgs.put("tableHeader1", tableHeader1);
	contentArgs.put("tableHeader2", tableHeader2);
	contentArgs.put("tableHeader3", tableHeader3);
	contentArgs.put("tableHeader2_1", tableHeader2_1);
	contentArgs.put("tableHeader2_2", tableHeader2_2);
	contentArgs.put("tableHeader3_1", tableHeader3_1);
	contentArgs.put("tableHeader3_2", tableHeader3_2);
	contentArgs.put("content", content);
	contentArgs.put("subject1", subject1);
	contentArgs.put("subject2", subject2);
	contentArgs.put("subject3", subject3);
	contentArgs.put("subject2Date", subject2Date);
	contentArgs.put("subject3Date", subject3Date);
	contentArgs.put("footerMsg", footerMsg);
	contentArgs.put("goLinkURL", goLinkURL);
	contentArgs.put("footerClickMsg", footerClickMsg);

	return contentArgs;
    }

    private DashBoardDTO getDashBoardParamObject(String deptCode, String type) {
	DashBoardDTO dashBoardDTO = new DashBoardDTO();
	dashBoardDTO.setDashboardType(type);

	if (type.equals("pilot")) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    Calendar c = Calendar.getInstance();
	    String currentDate = formatter.format(c.getTime());
	    c.add(Calendar.DATE, -10);
	    // c.add(Calendar.YEAR, -100);

	    String startDate = formatter.format(c.getTime());
	    dashBoardDTO.setStartDate(startDate);
	    dashBoardDTO.setCurrentDate(currentDate);
	    c.add(Calendar.DATE, 20);
	    // c.add(Calendar.YEAR, 200);

	    String endDate = formatter.format(c.getTime());
	    dashBoardDTO.setEndDate(endDate);

	    /*
	     * java.util.Calendar cal = java.util.Calendar.getInstance();
	     * 
	     * // 현재 년도, 월, 일 int year = cal.get(cal.YEAR); int month = cal.get(cal.MONTH) + 1; int date = cal.get(cal.DATE);
	     * 
	     * String currentDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date);
	     * dashBoardDTO.setCurrentDate(currentDate);
	     */
	} else if (type.equals("delay")) {

	} else if (type.equals("progress")) {

	}

	dashBoardDTO.setPjtType("product");
	dashBoardDTO.setDeptCode(deptCode);
	// dashBoardDTO.setProcessGb("Pilot"); //원래는 Pilot만 대상이었으나 다 하기로함
	return dashBoardDTO;
    }

    public String Formatting(int size) {
	return String.format("%,d", size);
    }

    private String getDashBoardTaskPilotList(Persistable pbo, SqlSession session) throws Exception {
	KETDashBoardMailSetting mailSet = (KETDashBoardMailSetting) pbo;
	String taskList = "";
	HashMap<String, String> taskCount = null;
	List<Department> DepartmentList = query.queryForListBByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class,
	        Department.class, mailSet);

	int delaySum = 0;
	int processSum = 0;
	int planSum = 0;
	int completeSum = 0;
	int totalSum = 0;

	for (Department depart : DepartmentList) {

	    DashBoardDTO dashBoardDTO = getDashBoardParamObject(depart.getCode(), "pilot");

	    try {
		taskCount = KETMailReceiveHelper.service.getTaskCount(dashBoardDTO, session);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    taskList += "<tr>";
	    taskList += "<td class=\"center\">" + depart.getName() + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskDelay") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskProcess") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskPlan") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskComplete") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskTotal") + "</td>";
	    taskList += "</tr>";

	    delaySum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelay"), ","));
	    processSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProcess"), ","));
	    planSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskPlan"), ","));
	    completeSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskComplete"), ","));
	    totalSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskTotal"), ","));
	}

	taskList += "<tr>";
	taskList += "<td class=\"center\"><strong>계</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(delaySum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(processSum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(planSum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(completeSum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(totalSum) + "</strong></td>";
	taskList += "</tr>";

	return taskList;

    }

    private String getDashBoardTaskDelayList(Persistable pbo, SqlSession session) throws Exception {
	KETDashBoardMailSetting mailSet = (KETDashBoardMailSetting) pbo;
	String taskList = "";
	HashMap<String, String> taskCount = null;
	List<Department> DepartmentList = query.queryForListBByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class,
	        Department.class, mailSet);

	int taskDelay14Sum = 0;
	int taskDelay7Sum = 0;
	int taskDelay3Sum = 0;
	int taskDelay1Sum = 0;
	int taskDelayTotalSum = 0;

	for (Department depart : DepartmentList) {

	    DashBoardDTO dashBoardDTO = getDashBoardParamObject(depart.getCode(), "delay");

	    try {
		taskCount = KETMailReceiveHelper.service.getTaskCount(dashBoardDTO, session);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    taskList += "<tr>";
	    taskList += "<td class=\"center\">" + depart.getName() + "</td>";
	    taskList += "<td class=\"center\"><font color=\"#ff3300\">" + taskCount.get("taskDelay14") + "</font></td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskDelay7") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskDelay3") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskDelay1") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskDelayTotal") + "</td>";
	    taskList += "</tr>";

	    taskDelay14Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelay14"), ","));
	    taskDelay7Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelay7"), ","));
	    taskDelay3Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelay3"), ","));
	    taskDelay1Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelay1"), ","));
	    taskDelayTotalSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskDelayTotal"), ","));

	}

	taskList += "<tr>";
	taskList += "<td class=\"center\"><strong>계</strong></td>";
	taskList += "<td class=\"center\"><font color=\"#ff3300\"><strong>" + Formatting(taskDelay14Sum) + "</strong></font></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskDelay7Sum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskDelay3Sum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskDelay1Sum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskDelayTotalSum) + "</strong></td>";
	taskList += "</tr>";

	return taskList;

    }

    private String getDashBoardTaskProgressList(Persistable pbo, SqlSession session) throws Exception {
	KETDashBoardMailSetting mailSet = (KETDashBoardMailSetting) pbo;
	String taskList = "";
	HashMap<String, String> taskCount = null;
	List<Department> DepartmentList = query.queryForListBByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class,
	        Department.class, mailSet);

	int taskProgress_exceedSum = 0;
	int taskProgress1Sum = 0;
	int taskProgress3Sum = 0;
	int taskProgress7Sum = 0;
	int taskProgress7_oddSum = 0;
	int taskProgressTotalSum = 0;

	for (Department depart : DepartmentList) {
	    DashBoardDTO dashBoardDTO = getDashBoardParamObject(depart.getCode(), "progress");

	    try {
		taskCount = KETMailReceiveHelper.service.getTaskCount(dashBoardDTO, session);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    taskList += "<tr>";
	    taskList += "<td class=\"center\">" + depart.getName() + "</td>";
	    taskList += "<td class=\"center\"><font color=\"#ff3300\">" + taskCount.get("taskProgress_exceed") + "</font></td>";
	    taskList += "<td class=\"center\"><font color=\"#ff3300\">" + taskCount.get("taskProgress1") + "</font></td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskProgress3") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskProgress7") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskProgress7_odd") + "</td>";
	    taskList += "<td class=\"center\">" + taskCount.get("taskProgressTotal") + "</td>";
	    taskList += "</tr>";

	    taskProgress_exceedSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgress_exceed"), ","));
	    taskProgress1Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgress1"), ","));
	    taskProgress3Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgress3"), ","));
	    taskProgress7Sum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgress7"), ","));
	    taskProgress7_oddSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgress7_odd"), ","));
	    taskProgressTotalSum += Integer.parseInt(StringUtils.remove(taskCount.get("taskProgressTotal"), ","));
	}

	taskList += "<tr>";
	taskList += "<td class=\"center\"><strong>계</strong></td>";
	taskList += "<td class=\"center\"><font color=\"#ff3300\"><strong>" + Formatting(taskProgress_exceedSum) + "</strong></font></td>";
	taskList += "<td class=\"center\"><font color=\"#ff3300\"><strong>" + Formatting(taskProgress1Sum) + "</strong></font></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskProgress3Sum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskProgress7Sum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskProgress7_oddSum) + "</strong></td>";
	taskList += "<td class=\"center\"><strong>" + Formatting(taskProgressTotalSum) + "</strong></td>";
	taskList += "</tr>";

	return taskList;

    }

    private String getApprovalLine(Persistable pbo) throws Exception {

	String approvalLine = "";
	KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(appMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);

	ArrayList<KETWfmApprovalHistoryDTO> historyDTOs = new ArrayList<KETWfmApprovalHistoryDTO>();

	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    KETWfmApprovalHistoryDTO historyDTO = new KETWfmApprovalHistoryDTO(history);
	    if (!(KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName) || KETWfmApprovalHistoryDTO.DISTRIBUTE
		    .equals(activityName))) {
		historyDTOs.add(historyDTO);
	    }
	    if (KETWfmApprovalHistoryDTO.REWORK.equals(activityName)) {
		historyDTOs.clear();
		historyDTOs.add(historyDTO);
	    }
	}

	for (KETWfmApprovalHistoryDTO history : historyDTOs) {
	    String activityName = history.getActivityName();
	    if (!history.isCompleted() || !StringUtil.checkString(history.getDecision()))
		continue;
	    if (KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName)
		    || KETWfmApprovalHistoryDTO.DISTRIBUTE.equals(activityName))
		continue;
	    WTUser owner = history.getOwner();
	    PeopleData pData = new PeopleData(owner);
	    if (history.isLast())
		activityName = KETWfmApprovalHistoryDTO.ACCEPT;
	    String date = history.getCompleteDate();// DateUtil.getDateString(history.getCompletedDate(), "d");
	    String comment = history.getComment().replaceAll(System.getProperty("line.separator"), "<br>");// StringUtil.checkReplaceStr(history.getComments(),
		                                                                                           // "&nbsp;");
	    String dateHeader = (KETWfmApprovalHistoryDTO.REQUEST.equals(activityName)) ? "상신일" : "결재일";
	    approvalLine += "<tr>";
	    approvalLine += "<td class=\"subject\" style=\"border-bottom: none\">" + activityName + "</td>";
	    approvalLine += "<td style=\"border-bottom: none\">" + pData.departmentName + " " + pData.name + " " + pData.duty + "</td>";
	    approvalLine += "<td style=\"border-bottom: none\" class=\"subject\">" + dateHeader + "</td>";
	    approvalLine += "<td style=\"border-bottom: none\">" + date + "</td>";
	    approvalLine += "</tr>";
	    approvalLine += "<tr>";
	    approvalLine += "<td colspan=\"4\" align=\"center\" style=\"padding: 10px; border-top: none;\">";
	    approvalLine += "<table style=\"width: 95%; border: 1px solid #b7d1e2\" cellpadding=0>";
	    approvalLine += "<colgroup>";
	    approvalLine += "<col width=\"15%\" />";
	    approvalLine += "<col width=\"85%\" />";
	    approvalLine += "</colgroup>";
	    approvalLine += "<tbody>";
	    approvalLine += "<tr>";
	    approvalLine += "<td align=\"center\" style=\"color: #578aaa\">의견</td>";
	    approvalLine += "<td style=\"padding: 3px text-align:left;\">" + comment + "</td>";
	    approvalLine += "</tr>";
	    approvalLine += "</tbody>";
	    approvalLine += "</table>";
	    approvalLine += "</td>";
	    approvalLine += "</tr>";
	}

	Kogger.debug(getClass(), approvalLine);

	return approvalLine;
    }

    @Override
    public WTUser getLastApprover(Persistable pbo) throws Exception {
	WTUser lastApprover = null;
	KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    if (history.isLast())
		lastApprover = (WTUser) history.getOwner().getObject();
	}
	return lastApprover;
    }

    @Override
    public WTUser getMailFromUser(ObjectReference self, String type) throws Exception {
	WTUser from = null;
	wt.workflow.work.WfAssignedActivity activity = (wt.workflow.work.WfAssignedActivity) self.getObject();
	wt.workflow.engine.WfProcess process = activity.getParentProcess();
	ProcessData processData = process.getContext();
	WTObject primaryBusinessObject = (WTObject) processData.getValue("primaryBusinessObject");
	if ("reviewer".equals(type)) {
	    from = (WTUser) process.getOwnership().getOwner().getObject();
	} else if ("sequential".equals(type) || "parallel".equals(type)) {
	    Object reviewer1 = processData.getValue("reviewer1");
	    if (reviewer1 != null) {
		for (int i = 1; i < 11; i++) {
		    Object reviewer = processData.getValue("reviewer" + i);
		    if (reviewer != null)
			from = (WTUser) reviewer;
		    else
			break;
		}
	    } else {
		from = getRequestUser(primaryBusinessObject);
	    }
	} else if ("submitter".equals(type)) {
	    Object reviewer1 = processData.getValue("reviewer1");
	    Object sequential1 = processData.getValue("sequential1");
	    Object parallel1 = processData.getValue("parallel1");
	    if (reviewer1 != null) {
		for (int i = 1; i < 11; i++) {
		    Object reviewer = processData.getValue("reviewer" + i);
		    if (reviewer != null)
			from = (WTUser) reviewer;
		    else
			break;
		}
	    } else {
		from = getRequestUser(primaryBusinessObject);
	    }
	    if (sequential1 != null) {
		for (int i = 1; i < 11; i++) {
		    Object sequential = processData.getValue("sequential" + i);
		    if (sequential != null)
			from = (WTUser) sequential;
		    else
			break;
		}
	    } else {
		from = getRequestUser(primaryBusinessObject);
	    }
	    if (parallel1 != null) {
		for (int i = 1; i < 11; i++) {
		    Object parallel = processData.getValue("parallel" + i);
		    if (parallel != null)
			from = (WTUser) parallel;
		    else
			break;
		}
	    } else {
		from = getRequestUser(primaryBusinessObject);
	    }
	}
	return from;
    }

    @Override
    public WTUser getRequestUser(WTObject pbo) throws Exception {
	WTUser user = null;
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (!(KETWfmApprovalHistoryDTO.REQUEST.equals(activityName) || KETWfmApprovalHistoryDTO.REWORK.equals(activityName)))
		continue;
	    user = (WTUser) history.getOwner().getObject();
	}
	return user;
    }

    @Override
    public WTUser getRejectUser(WTObject pbo) throws Exception {
	WTUser user = null;
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName)
		    || KETWfmApprovalHistoryDTO.DISTRIBUTE.equals(activityName))
		continue;
	    if (WFMProperties.getInstance().getString("wfm.reject").equals(history.getDecision())) {
		user = (WTUser) history.getOwner().getObject();
	    }
	}
	return user;
    }

    @Override
    public ArrayList<WTUser> getApprovalLineUser(WTObject pbo) throws Exception {
	ArrayList<WTUser> userList = new ArrayList<WTUser>();
	try {
	    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);

	    int pos = 0;
	    for (KETWfmApprovalHistory history : lineVec) {
		String hisName = history.getActivityName();
		if (KETWfmApprovalHistoryDTO.REQUEST.equals(hisName) || KETWfmApprovalHistoryDTO.REWORK.equals(hisName)) {
		    pos = history.getSeqNum();
		}
	    }

	    for (KETWfmApprovalHistory history : lineVec) {
		String activityName = history.getActivityName();
		if (KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName)
		        || KETWfmApprovalHistoryDTO.DISTRIBUTE.equals(activityName)
		        || KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(activityName))
		    continue;
		if (history.isLast())
		    continue;
		if (history.getSeqNum() >= pos) {
		    WTUser owner = (WTUser) history.getOwner().getObject();
		    if (!userList.contains(owner))
			userList.add(owner);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	return userList;
    }

    @Override
    public ArrayList<WTUser> getApprovalLineUser2(WTObject pbo) throws Exception {
	ArrayList<WTUser> userList = new ArrayList<WTUser>();
	try {
	    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);

	    int reqPos = 0;
	    int rejPos = 0;
	    for (KETWfmApprovalHistory history : lineVec) {
		String hisName = history.getActivityName();
		if (KETWfmApprovalHistoryDTO.REQUEST.equals(hisName) || KETWfmApprovalHistoryDTO.REWORK.equals(hisName)) {
		    reqPos = history.getSeqNum();
		}
		if (WFMProperties.getInstance().getString("wfm.reject").equals(history.getDecision())
		        || WFMProperties.getInstance().getString("wfm.cancel").equals(history.getDecision())) {
		    rejPos = history.getSeqNum();
		}
	    }
	    Kogger.debug(getClass(), "getApprovalLineUser2 : ");
	    Kogger.debug(getClass(), "reqPos : " + reqPos);
	    Kogger.debug(getClass(), "rejPos : " + rejPos);

	    for (KETWfmApprovalHistory history : lineVec) {
		String activityName = history.getActivityName();
		if (KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName) || KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName)
		        || KETWfmApprovalHistoryDTO.DISTRIBUTE.equals(activityName)
		        || KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(activityName))
		    continue;
		if (reqPos <= history.getSeqNum() && history.getSeqNum() < rejPos) {
		    WTUser owner = (WTUser) history.getOwner().getObject();
		    if (!userList.contains(owner))
			userList.add(owner);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	return userList;
    }

    @Override
    public ArrayList<WTUser> getReceiverUser(WTObject pbo) throws Exception {
	ArrayList<WTUser> userList = new ArrayList<WTUser>();
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (!KETWfmApprovalHistoryDTO.RECEIVER.equals(activityName))
		continue;
	    WTUser owner = (WTUser) history.getOwner().getObject();
	    if (!userList.contains(owner))
		userList.add(owner);
	}
	return userList;
    }

    @Override
    public ArrayList<WTUser> getReferenceUser(WTObject pbo) throws Exception {
	ArrayList<WTUser> userList = new ArrayList<WTUser>();
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (!KETWfmApprovalHistoryDTO.REFERENCE.equals(activityName))
		continue;
	    WTUser owner = (WTUser) history.getOwner().getObject();
	    if (!userList.contains(owner))
		userList.add(owner);
	}
	return userList;
    }

    @Override
    public ArrayList<WTUser> getReqrecipientUser(WTObject pbo) throws Exception {
	ArrayList<WTUser> userList = new ArrayList<WTUser>();
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (!KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(activityName))
		continue;
	    WTUser owner = (WTUser) history.getOwner().getObject();
	    if (!userList.contains(owner))
		userList.add(owner);
	}
	return userList;
    }

    @Override
    public WTUser getReqrecipientUser2(WTObject pbo) throws Exception {
	WTUser user = null;
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(pbo);
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);
	Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory history : lineVec) {
	    String activityName = history.getActivityName();
	    if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(activityName)) {
		if (history.getCompletedDate() != null)
		    user = (WTUser) history.getOwner().getObject();
	    }
	}
	return user;
    }

    private ProjectChangeStop getProjectChangeStop(E3PSProject project, String changeType) throws Exception {
	QuerySpec qs = null;
	QueryResult qr = null;
	ProjectChangeStop changeStop = null;
	long idLong = CommonUtil.getOIDLongValue(project.getMaster());
	qs = new QuerySpec();
	int idx = qs.addClassList(ProjectChangeStop.class, true);
	qs.appendWhere(new SearchCondition(ProjectChangeStop.class, "pcsMasterReference.key.id", "=", idLong), new int[] { idx });
	SearchUtil.setOrderBy(qs, ProjectChangeStop.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
	qr = PersistenceHelper.manager.find(qs);
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    ProjectChangeStop ps = (ProjectChangeStop) obj[0];
	    if (changeType.equals(ps.getChangeType())) {
		changeStop = ps;
		break;
	    }
	}
	return changeStop;
    }

    private String getSpanStyle(String day) {
	String span = "<span style=color:#00AA00>";
	if ("■".equals(day) || "□".equals(day)) {
	    span = "<span style=color:#FF0000>";
	}
	span += day + "</span>";
	return span;
    }

    private String getPjtMainScheduleList(List<Map<String, String>> list) throws Exception {

	String pjtList = "";

	int cnt = 1;
	String style = "padding:2px 5px 2px 5px;border:2px solid #BBBBBB;color:#000;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;table-layout:fixed;";
	for (Map<String, String> map : list) {
	    String step = StringUtil.checkNull((String) map.get("STEP"));
	    if ("SOP_PLUS".equals(step)) {
		step = "SOP+";
	    }
	    pjtList += "<tr>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + cnt + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + (String) map.get("PJTNO") + "</td>";
	    pjtList += "<td style=\"" + style + "\">" + (String) map.get("PJTNAME") + "</td>";
	    pjtList += "<td style=\"" + style + "\">" + StringUtil.checkNull((String) map.get("CAR_TYPE")) + "</td>";
	    pjtList += "<td style=\"" + style + "\">" + StringUtil.checkNull((String) map.get("PJT_SUBCONTRACTOR")) + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + step + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR1_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR2_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("GT2_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR3_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR4_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("AT_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("GT3_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("PV_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR5_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("FT_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("GT4_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + getSpanStyle(StringUtil.checkNull((String) map.get("DR6_D")))
		    + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + StringUtil.checkNull((String) map.get("ISSUE_CNT")) + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + StringUtil.checkNull((String) map.get("CUS_SOP")) + "</td>";
	    pjtList += "<td style=\"" + style + "text-align:center;\">" + StringUtil.checkNull((String) map.get("CAR_SOP")) + "</td>";
	    pjtList += "</tr>";
	    cnt++;
	}

	return pjtList;

    }

}
