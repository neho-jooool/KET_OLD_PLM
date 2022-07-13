/**
 * @(#) MailUtil.java
 * Copyright (c) e3ps. All rights reserverd
 *
 *    @version 1.00
 *    @since jdk 1.4.02
 *    @createdate 2005. 3. 3..
 *    @author Cho Sung Ok, jerred@e3ps.com
 *    @desc
 */

package e3ps.common.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.StreamData;
import wt.content.Streamed;
import wt.fc.LobLocator;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.common.jdf.config.ConfigEx;
import e3ps.common.jdf.config.ConfigExImpl;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.message.KETMessageService;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.TaskDependencyLink;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskDependencyHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.ClassifyPBOUtil;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

public class MailUtil {
    public static final MailUtil manager     = new MailUtil();
    static final boolean         VERBOSE     = ConfigImpl.getInstance().getBoolean("develop.verbose", false);

    static final boolean         testMailYn  = ConfigImpl.getInstance().getBoolean("mail.test.yn", false);
    static final String          testMailAdd = ConfigImpl.getInstance().getString("mail.test.emailAddress");

    protected MailUtil() {
    }

    public static boolean sendMail3(Hashtable hash) throws Exception {
	ConfigExImpl conf = ConfigEx.getInstance("eSolution");
	String host = conf.getString("mail.smtp.host");
	boolean enableMail = conf.getBoolean("e3ps.mail.enable", true);

	if (enableMail) {
	    Kogger.debug(MailUtil.class, "###################################################################### sand start :" + enableMail);

	    HashMap to = (HashMap) hash.get("TO");
	    HashMap from = (HashMap) hash.get("FROM");
	    String subject = (String) hash.get("SUBJECT");
	    //            subject = "PLM 알림 메일입니다";
	    String content = (String) hash.get("CONTENT");

	    try {
		SendMail mail = new SendMail();

		if (from != null) {
		    mail.setFromMailAddress((String) from.get("EMAIL"), (String) from.get("NAME"));
		}

		if (to != null && to.size() > 0) {
		    Object[] objArr = to.keySet().toArray();
		    String emails[];
		    emails = new String[objArr.length];
		    String toname[] = new String[objArr.length];
		    ;

		    for (int i = 0; i < objArr.length; i++) {

			if (testMailYn == true) {
			    emails[i] = testMailAdd;
			    toname[i] = (String) to.get((String) objArr[i]);
			}
			else {
			    emails[i] = (String) objArr[i];
			    //Kogger.debug(getClass(), "To Mail :" + emails[i]);
			    toname[i] = (String) to.get(emails[i]);
			    //Kogger.debug(getClass(), "To name :" + toname);
			}

			if (emails[i].indexOf("@") < 0) {
			    continue;
			}
		    }

		    mail.setToMailAddress(emails, toname);
		}
		else {
		    throw new MailException("받는 사람 설정에러");
		}

		mail.setSubject("[PLM]" + subject);

		String htmlMessage = content;
		String[] fileNames = {};

		if (content != null) {
		    mail.setHtmlAndFile(content, fileNames);
		}
		else {
		    mail.setHtmlAndFile(htmlMessage, fileNames);
		}
		//mail.setHtml(htmlMessage);
		//mail.setText(message);

		/**
		 * @Todo 개인 서버에서 주석처리함.
		 */
		mail.send();  // 메일 전송
		//Kogger.debug(getClass(), "###################################################################### sand complated :" + enableMail);
		return true;
	    } catch (Exception e) {
		throw e;
		//return false;
	    }
	}
	else {
	    return false;
	}
    }

    /**
     * host, sendId, sendPass 설정하기...
     * 
     * @param hash
     * @return
     */
    public static boolean sendMail2(Hashtable hash, String mailTitle) {
	ConfigExImpl conf = ConfigEx.getInstance("eSolution");
	String host = conf.getString("mail.smtp.host");
	boolean enableMail = conf.getBoolean("e3ps.mail.enable", true);

	if (enableMail) {
	    HashMap to = (HashMap) hash.get("TO");
	    HashMap from = (HashMap) hash.get("FROM");
	    //            String subject = (String)hash.get("SUBJECT");
	    //subject = "PLM 알림 메일입니다";
	    String content = (String) hash.get("CONTENT");

	    //Kogger.debug(getClass(),  "==========> MailUtil.java :: sendMail2() :: mailTitle :: " + mailTitle );
	    //Kogger.debug(getClass(),  "==========> MailUtil.java :: sendMail2() :: content :: " + content );

	    try {
		SendMail mail = new SendMail();

		if (from != null) {
		    mail.setFromMailAddress((String) from.get("EMAIL"), (String) from.get("NAME"));
		}

		if (to != null && to.size() > 0) {
		    Object[] objArr = to.keySet().toArray();
		    String emails = "";
		    String toname = "";

		    for (int i = 0; i < objArr.length; i++) {

			if (testMailYn == true) {
			    emails = testMailAdd;
			    toname = (String) to.get((String) objArr[i]);
			}
			else {
			    emails = (String) objArr[i];
			    toname = (String) to.get(emails);
			    Kogger.debug(MailUtil.class, "To Mail :" + emails);
			    Kogger.debug(MailUtil.class, "To name :" + toname);
			}

			if (emails.indexOf("@") < 0) continue;

			mail.setToMailAddress(emails, toname);
		    }
		}
		else {
		    throw new MailException("받는 사람 설정에러");
		}

		mail.setSubject("[PLM]" + mailTitle);

		String htmlMessage = content;
		String[] fileNames = {};

		if (content != null) {
		    mail.setHtmlAndFile(content, fileNames);
		}
		else {
		    mail.setHtmlAndFile(htmlMessage, fileNames);
		}

		//mail.setHtml(htmlMessage);
		//mail.setText(message);

		/**
		 * @Todo 개인 서버에서 주석처리함.
		 */
		mail.send();  // 메일 전송

		return true;
	    } catch (Exception e) {
		//throw e;
		return false;
	    }
	}
	else {
	    return false;
	}
    }

    // 사용하지 않음
    public boolean sendMail(Hashtable hash, String mailTitle) {
	ConfigExImpl conf = ConfigEx.getInstance("eSolution");
	String host = conf.getString("mail.smtp.host");
	boolean enableMail = conf.getBoolean("e3ps.mail.enable", true);
	//Kogger.debug(getClass(), "host Mail : " + host);

	if (enableMail) {
	    HashMap to = (HashMap) hash.get("TO");
	    //            String subject = (String)hash.get("SUBJECT");
	    String content = (String) hash.get("CONTENT");
	    Vector attache = (Vector) hash.get("ATTACHE");

	    try {
		SendMail mail = new SendMail();
		WTUser from = (WTUser) SessionHelper.manager.getPrincipal();

		mail.setFromMailAddress(from.getEMail(), from.getFullName());

		if (to != null && to.size() > 0) {
		    Object[] objArr = to.keySet().toArray();
		    String emails = "";
		    String toname = "";

		    for (int i = 0; i < objArr.length; i++) {
			emails = (String) objArr[i];
			toname = (String) to.get(emails);

			//                        Kogger.debug(getClass(), "To Mail :" + emails);
			//                        Kogger.debug(getClass(), "To name :" + toname);

			if (emails.indexOf("@") < 0) continue;

			mail.setToMailAddress(emails, toname);
		    }
		}
		else {
		    throw new MailException("받는 사람 설정에러");
		}

		mail.setSubject("[PLM]" + mailTitle);

		String message = " Text 메일 메시지 내용 ";
		String htmlMessage = "<html><font color='red'> HTML 메일 메시지 내용</font></html>";
		// String[] fileNames = { "c:/attachFile1.zip","c:/attachFile2.txt"   } ;
		String[] fileNames = {};

		if (content != null) {
		    mail.setHtmlAndFile(content, fileNames);
		}
		else {
		    mail.setHtmlAndFile(htmlMessage, fileNames);
		}

		//mail.setHtml(htmlMessage);
		//mail.setText(message);

		/**
		 * @Todo 개인 서버에서 주석처리함.
		 */
		mail.send();  // 메일 전송

		return true;
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		return false;
	    }
	}
	else {
	    return false;
	}
    }

    private File getFile(ContentHolder contentholder, ApplicationData applicationdata) throws Exception {
	//ContentHolder contentholder = applicationdata.getHolderLink().getContentHolder();
	Streamed streamed = (Streamed) PersistenceHelper.manager.refresh(applicationdata.getStreamData().getObjectId());
	LobLocator loblocator = null;

	if (streamed instanceof StreamData) {
	    applicationdata = (ApplicationData) PersistenceHelper.manager.refresh(applicationdata);
	    streamed = (Streamed) PersistenceHelper.manager.refresh(applicationdata.getStreamData().getObjectId());

	    try {
		loblocator.setObjectIdentifier(((ObjectReference) streamed).getObjectId());
		((StreamData) streamed).setLobLoc(loblocator);
	    } catch (Exception exception) {
		Kogger.error(getClass(), exception);
	    }
	}

	String tempDir = System.getProperty("java.io.tmpdir");
	InputStream in = streamed.retrieveStream();
	File attachfile = new File(tempDir + File.separator + applicationdata.getFileName());        // 파일 저장 위치
	FileOutputStream fileOut = new FileOutputStream(attachfile);
	byte[] buffer = new byte[1024];
	int c;
	while ((c = in.read(buffer)) != -1)
	    fileOut.write(buffer, 0, c);
	fileOut.close();

	return attachfile;
    }

    public static Hashtable prepareMailInfoHash(WTPrincipalReference toUser, String contents) {
	Hashtable retHash = new Hashtable();
	String toEmail = toUser.getEMail();
	String toName = toUser.getFullName();

	HashMap<String, String> toMap = new HashMap<String, String>();
	HashMap<String, String> fromMap = new HashMap<String, String>();

	try {
	    toMap.put(toEmail, toName);
	    WTUser admin = KETObjectUtil.getUser("wcadmin");
	    fromMap.put("EMAIL", admin.getEMail());
	    fromMap.put("NAME", "Administrator");
	    retHash.put("TO", toMap);
	    retHash.put("FROM", fromMap);
	    retHash.put("CONTENT", contents);
	} catch (WTException e) {
	    Kogger.error(MailUtil.class, e);
	}

	return retHash;
    }

    // 변경 BOM에서만 호출됨
    public static Hashtable prepareMailInfoHash(String toName, String toEmail, String contents) {
	Hashtable retHash = new Hashtable();

	HashMap<String, String> toMap = new HashMap<String, String>();
	HashMap<String, String> fromMap = new HashMap<String, String>();

	try {
	    toMap.put(toEmail, toName);
	    WTUser admin = KETObjectUtil.getUser("wcadmin");
	    fromMap.put("EMAIL", admin.getEMail());
	    fromMap.put("NAME", "Administrator");
	    retHash.put("TO", toMap);
	    retHash.put("FROM", fromMap);
	    retHash.put("CONTENT", contents);
	} catch (WTException e) {
	    Kogger.error(MailUtil.class, e);
	}

	return retHash;
    }


    public static Hashtable prepareMailInfoHash(WTPrincipalReference toUser, String contents, String fromUserID) {
	Hashtable retHash = new Hashtable();


	Kogger.debug(MailUtil.class, "toUser == " + toUser);
	String toEmail = toUser.getEMail();
	String toName = toUser.getFullName();

	HashMap<String, String> toMap = new HashMap<String, String>();
	HashMap<String, String> fromMap = new HashMap<String, String>();

	try {
	    toMap.put(toEmail, toName);

	    WTUser fromUser = KETObjectUtil.getUser(fromUserID);

	    Kogger.debug(MailUtil.class, "=====> MailUtil.java :: prepareMailInfoHash() :: toUser :: " + toName);

	    if (fromUser != null) {
		fromMap.put("EMAIL", fromUser.getEMail());
		fromMap.put("NAME", fromUser.getFullName());

		Kogger.debug(MailUtil.class, "=====> MailUtil.java :: prepareMailInfoHash() :: fromUser is notNull :: " + fromUser.getFullName());
	    }
	    else {
		fromUser = KETObjectUtil.getUser("wcadmin");

		fromMap.put("EMAIL", fromUser.getEMail());
		fromMap.put("NAME", fromUser.getFullName());

		Kogger.debug(MailUtil.class, "=====> MailUtil.java :: prepareMailInfoHash() :: fromUser is Null :: " + fromUser.getFullName());
	    }

	    retHash.put("TO", toMap);
	    retHash.put("FROM", fromMap);
	    retHash.put("CONTENT", contents);
	} catch (WTException e) {
	    Kogger.error(MailUtil.class, e);
	}

	return retHash;
    }

    //    public static void mailObjMailSendSetting(ApprovalLine nextLine, Object object, HashMap toHash, String title, String msg, String mailType) throws Exception{
    //        mailObjMailSendSetting( nextLine,  object,  toHash, title,  msg,  mailType,false);
    //    }

    //    public static void mailObjMailSendSetting(ApprovalLine nextLine, Object object, HashMap toHash, String title, String msg, String mailType,boolean isWork) throws Exception{
    //
    //        WTUser from = (WTUser)SessionHelper.manager.getPrincipal();
    //
    //        String subject = "";
    //        String number = "";
    //
    //        Kogger.debug(getClass(), "@@ call mailObjMailSendSetting()  title = " + title);
    //        Kogger.debug(getClass(), "@@ call mailObjMailSendSetting()  msg = " + msg);
    //        Kogger.debug(getClass(), "@@ call mailObjMailSendSetting() " + object.toString());
    //        ApprovalData appData = new ApprovalData((Persistable)object);
    //
    //        subject = title;
    //
    //        String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
    //        WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
    //        String host = "http://" + hostName;
    //
    //        String link = host;
    //        if( nextLine != null ) {
    //            link += "/ViewWorkGate.jsp?oid=" + nextLine.getPersistInfo().getObjectIdentifier().toString();
    //        } else {
    //            link += "/ViewWorkGate.jsp?oid=" + ((Persistable)object).getPersistInfo().getObjectIdentifier().toString();
    //        }
    //
    //        Kogger.debug(getClass(), "host = " + host);
    //        Kogger.debug(getClass(), "link = " + link);
    //
    //        //설명
    //        String text = msg;
    //        // setting mail content
    //        String content = getHtmlTemplate(mailType, link, subject, (String)toHash.get("fullName"),
    //                (String)toHash.get("title"), (String)toHash.get("createDate"), (String)toHash.get("creater"), isWork);
    //        Hashtable mailHash = new Hashtable();
    //
    //        //Kogger.debug(getClass(), "content = " + content);
    //        HashMap fromHash = new HashMap();
    //        fromHash.put("EMAIL", from.getEMail());
    //        fromHash.put("NAME", from.getFullName());
    //
    //        mailHash.put("FROM", fromHash);
    //        mailHash.put("TO", toHash);
    //        mailHash.put("SUBJECT", subject);
    //        mailHash.put("CONTENT", content);
    //
    //        EMailScheduler.createProcessItem(mailHash);
    //        //boolean result = com.e3ps.common.mail.MailUtil.manager.sendMail(mailHash);
    //        //Kogger.debug(getClass(), "Mail 발송 : @@@@@@@@@@@@@@@@@@@@@@@@@@");
    //        //Kogger.debug(getClass(), "Mail 발송 : " + result);
    //    }

    public static String getHtmlTemplate(String mailType, String url, String subject, String author, String title, String startDate, String creator) throws Exception {
	return getHtmlTemplate(mailType, url, subject, author, title, startDate, creator, false);
    }

    public static String getHtmlTemplate(String mailType, String url, String subject, String author, String title, String startDate, String creator, boolean work) throws Exception {
	/**
	 * type : requestApproval, notify, pressingApproval <@url> <@subject> <@author> <@title> <@startDate> <@creator>
	 */

	String approve = "결재가";
	String approve2 = "결재";

	if (work) {
	    approve = "작업이";
	    approve2 = "작업";
	}

	String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	String host = "http://" + hostName;

	StringBuffer template = new StringBuffer();

	//        template.append(" <!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.0 Transitional//EN' >");
	//        template.append(" <html xmlns='http://www.w3.org/1999/xhtml' >");
	//        template.append(" <head><title>LG PLM MAIL</title>");
	template.append(" <style>");
	template.append(" .bodyScroll {");
	template.append(" SCROLLBAR-FACE-COLOR: #EDEDED;");
	template.append(" SCROLLBAR-SHADOW-COLOR: #FFFFFF;");
	template.append(" SCROLLBAR-HIGHLIGHT-COLOR: #FFFFFF;");
	template.append(" SCROLLBAR-3DLIGHT-COLOR: #B0B0B0;");
	template.append(" SCROLLBAR-DARKSHADOW-COLOR: #B0B0B0;");
	template.append(" SCROLLBAR-TRACK-COLOR: #F1F1F1;");
	template.append(" SCROLLBAR-ARROW-COLOR: #4B4B4B; }");
	template.append(" .style1 {color: #FFFFFF}");
	template.append(" .navi A:link {color:  #000000; FONT-FAMILY:굴림, arial; FONT-SIZE: 9pt;  text-decoration:none; }");
	template.append(" .navi A:visited {color:#000000; FONT-FAMILY:굴림, arial; FONT-SIZE: 9pt;  text-decoration:none;}");
	template.append(" .navi A:active {color: #000000; FONT-FAMILY:굴림, arial; FONT-SIZE: 9pt; text-decoration:none;}");
	template.append(" .navi A:hover { color:#ffffff;FONT-FAMILY:굴림, arial; FONT-SIZE: 9pt; text-decoration:none;}");
	template.append(" </style>");
	template.append(" </head>");
	template.append(" <body onLoad='formload()' class='bodyScroll' topmargin='0' leftmargin='0' rightmargin='0' bottommargin='0' style='FONT-SIZE: 9pt; font-family: '굴림','Dotum','Arial', 'Verdana';' scroll='no'>");
	template.append(" <DIV id='divMailContent' style='overflow-x:auto;width:100%; word-break:break-all; word-wrap:break-word; padding:10px 10px 10px 10px;'>");
	template.append(" <HTML XMLNS='http://www.w3.org/1999/xhtml'>");
	template.append(" <META HTTP-EQUIV='Content-Type' CONTENT='text/html; charset=UTF-8' />");
	template.append(" <STYLE TYPE='text/css'>");
	template.append(" <!--");
	template.append(" .mail_tit {");
	template.append(" font-weight: bold;");
	template.append(" color: #3e7eac;");
	template.append(" background-image: url(http://uapproval.lgchem.com:7010/lgchem/images/urge/bg_tit.gif);");
	template.append(" background-repeat: no-repeat;");
	template.append(" padding-left: 37px;");
	template.append(" font-family:  '굴림', '돋움';");
	template.append(" height: 40px;");
	template.append(" font-size: 12px;");
	template.append(" vertical-align: middle;");
	template.append(" text-align: left;}");
	template.append(" td {    font-family: '굴림', '돋움';");
	template.append(" font-size: 12px;");
	template.append(" line-height: 18px;");
	template.append(" color: #666666; }");
	template.append(" .txt_name { color: #4b95cf;");
	template.append(" font-weight: bold; }");
	template.append(" .txt_gray {color: #969696}");
	template.append(" .txt_white {color: #FFFFFF}");
	template.append(" -->");
	template.append(" </STYLE>");
	//        template.append(" <BODY>");
	template.append(" <TABLE WIDTH='660' BORDER='0' CELLPADDING='1' CELLSPACING='5' BGCOLOR='#f5f5f5'>");
	template.append(" <TR>");
	template.append(" <TD BGCOLOR='#d7d7d7'><TABLE WIDTH='100%' BORDER='0' CELLPADDING='0' CELLSPACING='0'>");
	template.append(" <TR>");
	template.append(" <TD HEIGHT='48' BGCOLOR='#FFFFFF'><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/top_logo.gif' HSPACE='15' /></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD BGCOLOR='#FFFFFF'><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/img_approval.jpg' width='650' height='100' /></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD ALIGN='center' BGCOLOR='#FFFFFF'><TABLE WIDTH='582' BORDER='0' CELLSPACING='0' CELLPADDING='0'>");
	template.append(" <TR>");
	template.append(" <TD HEIGHT='30'>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD CLASS='mail_tit'>" + subject + "</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD style='padding-left:15'><P>안녕하세요. PLM System에서 안내 드립니다.<BR />");

	if (mailType.equals("pressingApproval")) {
	    template.append(" <SPAN CLASS='txt_name'>" + author + " 님, </SPAN> " + approve + " 지연되고 있습니다.");
	    template.append(" <BR> " + approve2 + " 요청에 대한 빠른 처리 바랍니다.<br>감사합니다!</P>");
	}
	else if (mailType.equals("requestApproval")) {
	    template.append(" <SPAN CLASS='txt_name'>" + author + " 님, </SPAN> " + approve + " 요청되었습니다.");
	    template.append(" <BR> " + approve2 + " 요청에 대한 처리 바랍니다.<br>감사합니다!</P>");
	}
	else {
	    template.append(" <SPAN CLASS='txt_name'>" + author + " 님, </SPAN> " + approve + " 완료 되었습니다.");
	    template.append(" <BR> " + approve2 + " 완료에 대한 문서 확인 바랍니다.<br>감사합니다!</P>");
	}

	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/dot_line.gif' WIDTH='582' HEIGHT='1' /></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/bg_01.gif' WIDTH='582' HEIGHT='5'></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD BACKGROUND='http://uapproval.lgchem.com:7010/lgchem/images/urge/bg_02.gif'style='padding:20'><TABLE WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0'>");
	template.append(" <TR>");
	template.append(" <TD width='15' align='center' valign='middle'>");
	template.append(" <img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/bullet_01.gif' alt='' WIDTH='9' HEIGHT='7'></TD>");
	template.append(" <TD HEIGHT='20' valign='middle'> 제목 : " + title + "</TD>");
	template.append("</TD> </TR>");
	template.append(" <TR>");
	template.append(" <TD align='center'><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/bullet_01.gif' alt='' WIDTH='9' HEIGHT='7'></TD>");
	template.append(" <TD HEIGHT='20'> 작성일 : " + startDate + "</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD align='center'><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/bullet_01.gif' alt='' WIDTH='9' HEIGHT='7'></TD>");
	template.append(" <TD HEIGHT='20'> 제출자 : " + creator + "</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD ALIGN='CENTER'>&nbsp;</TD>");
	template.append(" <TD ALIGN='CENTER'><table border='0' cellspacing='0' cellpadding='0'  class=' navi A:link '>");
	template.append(" <tr>");
	template.append(" <td><img src='" + host + "/plm/lgchem/img/mail_btn_01.gif' width='23' height='25'></td>");
	template.append(" <td background='" + host + "/plm/lgchem/img/mail_btn_022.gif'><table width='120' border='0' cellspacing='0' cellpadding='0'>");
	template.append(" <tr>");
	if (mailType.equals("pressingApproval") || mailType.equals("requestApproval")) {
	    template.append(" <td><div align='center'><a href=\"" + url + "\" ><SPAN CLASS='txt_white'> " + approve2 + " 하러가기</SPAN></a></td>");
	}
	else {
	    template.append(" <td><div align='center'><a href=\"" + url + "\" ><SPAN CLASS='txt_white'> 확인 하러가기</SPAN></a></td>");
	}
	template.append(" </tr>");
	template.append(" </table></td>");
	template.append(" <td><img src='" + host + "/plm/lgchem/img/mail_btn_03.gif' width='18' height='25'></td>");
	template.append(" </tr>");
	template.append(" </table></TD>");
	template.append(" </TR>");
	template.append(" </TABLE></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/bg_03.gif' WIDTH='582' HEIGHT='5'></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	if (mailType.equals("pressingApproval")) {
	    template.append(" <TD>* " + approve2 + " 요청일을 포함하여 3일차 01:00이전까지 처리가 되지않을 경우 결재 처리지연 안내메일이 발송됩니다.<BR>");
	}
	template.append(" <SPAN CLASS='txt_gray'>&nbsp;&nbsp;(본 메일은 발신전용이므로 회신시 수신이 불가합니다.) </SPAN></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD HEIGHT='40'>&nbsp;</TD>");
	template.append(" </TR>");
	template.append(" </TABLE></TD>");
	template.append(" </TR>");
	template.append(" <TR>");
	template.append(" <TD HEIGHT='20' ALIGN='center' BGCOLOR='#e9e9e9'><img src='http://uapproval.lgchem.com:7010/lgchem/images/urge/copyright.gif' WIDTH='223' HEIGHT='11' /></TD>");
	template.append(" </TR>");
	template.append(" </TABLE></TD>");
	template.append(" </TR>");
	template.append(" </TABLE>");
	template.append(" <script>fn_Forward('" + url + "');</script>");
	//template.append(" <input type=button value='바로가기'  onclick=\"fn_Forward('" + url + "')\">");
	//        template.append(" </BODY>");
	//        template.append(" </HTML>");
	//        template.append(" </DIV>");
	//        template.append(" </body>");
	//        template.append(" </html>");

	return template.toString();
    }


    public static Hashtable getMailContent(String type, KETDevelopmentRequest target, String userId) throws Exception {

	/*
	 * [PLM System 1차개선]
	 * 수정내용 : 메일 다국어 적용
	 * 수정일자 : 2013. 7. 21
	 * 수정자 : 오명재
	 */
	WTUser wtUser = OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
	KETMessageService messageService = new KETMessageService(KETMessageService.getUserLocale(wtUser));
	//////////////////////////////////////////////////////////////////////////////////////////////////

	messageService.getString("fileName", "key");

	Hashtable retHash = new Hashtable();
	String hostName = WTProperties.getLocalProperties().getProperty("java.rmi.server.hostname");
	String host = "http://" + hostName;

	String mailTitle = "&nbsp;"; //메일 상단 제목
	String objTitle = "&nbsp;"; //분류
	String creator = null;  //작성자명
	String reqUser = "요청자"; //요청자/반려자
	String reqDate = DateUtil.getDateString(DateUtil.getCurrentTimestamp(), "a");//요청일
	String myPage = "/plm/jsp/dms/ViewDevRequest.jsp?oid=" + CommonUtil.getOIDString(target);
	String goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">"; //링크주소
	String number = ""; // 의뢰번호

	if (creator == null) {
	    creator = ClassifyPBOUtil.extractPBOInfo(target).get("creator").toString();
	}

	if ("D".equals(target.getDevelopmentStep())) {
	    // 개발 의뢰
	    mailTitle = "개발의뢰서"; // 메일 제목 세팅
	}
	else if ("R".equals(target.getDevelopmentStep())) {
	    // 검토 의뢰
	    mailTitle = "검토의뢰서"; // 메일 제목 세팅
	}

	number = target.getNumber();


	retHash.put("number", number);
	retHash.put("mailTitle", mailTitle);
	retHash.put("objTitle", objTitle);
	retHash.put("reqUser", reqUser);
	retHash.put("creator", creator);
	retHash.put("reqDate", reqDate);
	retHash.put("goLinkPage", goLinkPage);
	retHash.put("host", host);
	retHash.put("approvalHistory", getApprovalHistory(CommonUtil.getOIDString(target)));

	return retHash;
    }

    // shkim@e3ps.com 메일 발송을 위한 선 후행 task 정보를 hashtable에 담는다.
    public static Hashtable getMailContent(String type, Object target, String userId, E3PSTask task) throws Exception {
	Hashtable retHash = new Hashtable();
	String hostName = WTProperties.getLocalProperties().getProperty("java.rmi.server.hostname");
	String host = "http://" + hostName;
	//String host = "http://ketplmdev.ket.com";

	String mailTitle = "Project Task 알림 메일"; //메일 상단 제목
	String goLinkPage = "<a href=# target=\"_blank\">";//링크주소
	String myPage = "";

	String pjtNo = ""; // 프로젝트 번호
	String pjtName = ""; // 프로젝트 이름
	String PM = ""; // PM

	String targetTitle = ""; // 선행 task title
	String targetUser = null; // 선행 Task 담당자
	String targetEndDate = ""; // 선행 Task 실제 완료일

	String sourceTitle = ""; // 후행 Task title
	String sourceUser = null; // 후행 Task 담당자
	String sourceStartDate = ""; // 후행 Task 계획 시작일


	if (type.equals("approval")) {
	    E3PSProject project = (E3PSProject) task.getProject();
	    pjtNo = project.getPjtNo();
	    pjtName = project.getPjtName();
	    PM = ProjectUserHelper.manager.getPM(project).getFullName();

	    E3PSTaskData targetData = null;
	    E3PSTaskData sourceData = null;

	    // 선행 값
	    QueryResult qr1 = TaskDependencyHelper.manager.getDependTaskList(task);

	    if (qr1 != null) {
		if (qr1.hasMoreElements()) {
		    TaskDependencyLink link = (TaskDependencyLink) qr1.nextElement();
		    targetData = new E3PSTaskData((E3PSTask) link.getDependTarget());
		}
	    }
	    targetTitle = targetData.taskName;
	    targetUser = targetData.getTaskWTUser().getFullName();
	    targetEndDate = DateUtil.getDateString(targetData.taskExecEndDate, "D");

	    // 후행 값들
	    QueryResult qr2 = TaskDependencyHelper.manager.getDependTaskList(task);

	    if (qr2 != null) {
		if (qr2.hasMoreElements()) {
		    TaskDependencyLink link = (TaskDependencyLink) qr2.nextElement();
		    sourceData = new E3PSTaskData((E3PSTask) link.getDependSource());
		}
	    }
	    sourceTitle = sourceData.taskName;
	    sourceUser = sourceData.getTaskWTUser().getFullName();
	    sourceStartDate = DateUtil.getDateString(targetData.taskPlanStartDate, "D");

	    userId = sourceData.getTaskWTUser().getName();
	    myPage = "/plm/jsp/project/TaskViewFrame.jsp?oid=" + sourceData.e3psTaskOID;
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";


	}

	retHash.put("mailTitle", mailTitle);
	retHash.put("host", host);
	retHash.put("pjtNo", pjtNo);
	retHash.put("goLinkPage", goLinkPage);
	retHash.put("pjtName", pjtName);
	retHash.put("PM", PM);
	retHash.put("targetTitle", targetTitle);
	retHash.put("targetUser", targetUser);
	retHash.put("targetEndDate", targetEndDate);
	retHash.put("sourceTitle", sourceTitle);
	retHash.put("sourceUser", sourceUser);
	retHash.put("sourceStartDate", sourceStartDate);

	return retHash;
    }

    /**
     * 
     * @param type
     *            : 결재/수신확인/반려확인(approval), 참조(reference)
     * @param target
     *            : 메일에 정보를 담을 객체 approval=WorkItem / reference=pbo / ewsend=KETEarlyWarning / infFail=KETBomHeader
     * @param userId
     *            : 수신자의 아이디 - 인증시 필요함
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static Hashtable getMailContent(String type, Object target, String userId) throws Exception {
	Hashtable retHash = new Hashtable();
	String hostName = WTProperties.getLocalProperties().getProperty("java.rmi.server.hostname");
	String host = "http://" + hostName;
	//String host = "http://ketplmdev.ket.com";

	String mailTitle = "&nbsp;"; //메일 상단 제목
	String objTitle = "&nbsp;"; //분류
	String creator = null;  //작성자명
	String reqUser = "요청자"; //요청자/반려자
	String reqDate = DateUtil.getDateString(DateUtil.getCurrentTimestamp(), "a");//요청일
	String goLinkPage = "<a href=# target=\"_blank\">";//링크주소
	String myPage = "";
	String bomNumber = "&nbsp;";
	String happenDate = "&nbsp;";
	String pboOid = "";

	if (type.equals("approval")) {
	    WorkItem item = (WorkItem) target;
	    Kogger.debug(MailUtil.class, "TTTT" + target);
	    WfAssignedActivity activity = null;
	    Kogger.debug(MailUtil.class, "+==========" + item);

	    if (item.getSource() != null) activity = (WfAssignedActivity) item.getSource().getObject();

	    Persistable obj = item.getPrimaryBusinessObject().getObject();

	    Kogger.debug(MailUtil.class, "OBJSODJGOJDGO==" + obj);
	    pboOid = obj.toString();

	    String title = ClassifyPBOUtil.extractPBOInfo(obj).get("title").toString();
	    if (obj instanceof E3PSProject) {
		Kogger.debug(MailUtil.class, "프로젝트 일 경우... 등록자 변경");
		People pp = new People();
		KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) obj);
		if (appMaster != null) {
		    pp = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
		}
		if (pp != null) {
		    creator = pp.getName();
		}
	    }

	    if (creator == null) {
		creator = ClassifyPBOUtil.extractPBOInfo(obj).get("creator").toString();
	    }
	    String step = ClassifyPBOUtil.extractPBOInfo(obj).get("step").toString();
	    String taskName = "&nbsp;";
	    //userId = item.getOwnership().getOwner().getName();

	    myPage = "/plm/jsp/wfm/index2.jsp?contSrc=/plm/jsp/wfm/ReviewTask.jsp?oid=" + item.toString();

	    if (!activity.getName().equals("수행담당자")) {
		if ("R".equals(step) && !activity.getName().equals("검토")) {
		    taskName = "검토" + activity.getName();
		}
		else if ("D".equals(step) && !activity.getName().equals("검토")) {
		    taskName = "개발" + activity.getName();
		}
		else {
		    taskName = activity.getName();
		}

		objTitle = "결재요청";

		if (taskName.equals("수신확인")) {
		    objTitle = taskName;
		}
		else if (taskName.equals("반려확인")) {
		    objTitle = taskName;
		    WTPrincipalReference rejUser = WorkflowSearchHelper.manager.getApprovalRejectUser(obj);

		    //if(obj instanceof KETMoldChangeOrder){
		    //mailTitle = taskName + " : 설계변경( " + title + " ) 반려 확인 메일 입니다.)";
		    //}

		    if (obj instanceof KETMoldChangeOrder) {
			mailTitle = taskName + " : 설계변경( " + title + " ) 반려 확인 메일 입니다.)";
		    }

		    if (rejUser != null) creator = rejUser.getFullName();
		    reqUser = "반려자";
		}
	    }
	    else {
		taskName = ClassifyPBOUtil.extractPBOInfo(obj).get("task").toString();
		myPage = "/plm/jsp/wfm/index2.jsp?contSrc=" + ClassifyPBOUtil.getTaskUrl(obj);
		objTitle = "[To-Do 공지]" + title;
	    }

	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	    mailTitle = taskName + " : " + title;
	}
	else if (type.equals("reference")) {
	    String title = ClassifyPBOUtil.extractPBOInfo(target).get("title").toString();
	    if (target instanceof WorkItem) {
		WorkItem item = (WorkItem) target;

		Persistable obj = item.getPrimaryBusinessObject().getObject();
		pboOid = obj.toString();

		if (obj instanceof E3PSProject) {
		    Kogger.debug(MailUtil.class, "프로젝트 참조 메일");
		    People pp = new People();
		    KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) obj);
		    if (appMaster != null) {
			pp = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
		    }
		    if (pp != null) {
			creator = pp.getName();
		    }
		}
	    }
	    if (creator == null) {
		creator = ClassifyPBOUtil.extractPBOInfo(target).get("creator").toString();
	    }
	    objTitle = "참조";
	    mailTitle = "참조 : " + title;

	    myPage = "/plm/jsp/wfm/index2.jsp?contSrc=/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRefDoc";
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	}
	else if (type.equals("approve_reference")) {
	    String title = ClassifyPBOUtil.extractPBOInfo(target).get("title").toString();
	    Kogger.debug(MailUtil.class, "target==" + target);
	    Kogger.debug(MailUtil.class, target instanceof WorkItem);
	    if (target instanceof WorkItem) {
		WorkItem item = (WorkItem) target;

		Persistable obj = item.getPrimaryBusinessObject().getObject();
		pboOid = obj.toString();

		if (obj instanceof E3PSProject) {
		    Kogger.debug(MailUtil.class, "프로젝트 승인 참조 메일");
		    People pp = new People();
		    KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) obj);
		    if (appMaster != null) {
			pp = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
		    }
		    if (pp != null) {
			creator = pp.getName();
		    }
		}
	    }
	    if (creator == null) {
		creator = ClassifyPBOUtil.extractPBOInfo(target).get("creator").toString();
	    }

	    pboOid = ((Persistable) target).getPersistInfo().getObjectIdentifier().getStringValue();
	    objTitle = "승인완료";
	    mailTitle = "승인완료 : " + title;

	    myPage = "/plm/jsp/wfm/index2.jsp?contSrc=/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRefDoc";
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	}
	else if (type.equals("assign")) {
	    E3PSProject project = (E3PSProject) target;
	    pboOid = project.toString();
	    String title = "[" + project.getPjtNo() + "] " + project.getPjtName();
	    creator = project.getCreatorFullName();
	    objTitle = "담당자지정";
	    mailTitle = "프로젝트 담당자지정  : " + title;
	    myPage = "/plm/jsp/wfm/index2.jsp?contSrc=/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	}
	else if (type.equals("ewsend")) {
	    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) target;
	    pboOid = ketEarlyWarning.toString();
	    String title = ketEarlyWarning.getNumber();
	    creator = ((WTUser) CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getFullName();
	    objTitle = "해제판정";
	    mailTitle = "초기유동 해제판정  : " + title;
	    myPage = "/plm/jsp/wfm/index2.jsp?leftSrc=/plm/portal/common/ews_submenu.jsp&contSrc=/plm/jsp/ews/CreateEarlyWarningEnd.jsp?oid=" + CommonUtil.getOIDString(ketEarlyWarning);
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	}
	// ECR - 주관부서 담당자 - Todo 메일
	// 기존의 Todo 메일을 사용하도록 한다.
	/*
	else if (type.equals("ecrCompetent")) {

	    String title = "";
	    // 제품 ECR
	    if (target instanceof KETProdChangeRequest) {
		KETProdChangeRequest ecr = (KETProdChangeRequest) target;

		pboOid = ecr.toString();
		title = ecr.getNumber();
		creator = ecr.getCreatorFullName();
		objTitle = "제품 ECR 검토";
		mailTitle = "제품 ECR 검토  : " + title;

	    }
	    // 금형 ECR
	    else if (target instanceof KETMoldChangeRequest) {
		KETMoldChangeRequest ecr = (KETMoldChangeRequest) target;

		pboOid = ecr.toString();
		title = ecr.getNumber();
		creator = ecr.getCreatorFullName();
		objTitle = "금형 ECR 검토";
		mailTitle = "금형 ECR 검토  : " + title;

	    }

	    myPage = "/plm/jsp/wfm/index2.jsp?contSrc=/plm/servlet/e3ps/ProdEcrServlet?cmd=PopupView&oid=" + pboOid;
	    myPage = URLEncoder.encode(myPage);
	    goLinkPage = "<a href=\"" + host + "/plm/jsp/SSOLogin.jsp?mode=mail&pax_webid=" + userId + "&returnUrl=" + myPage + "\" target=\"_blank\">";
	}
	*/
	else if (type.equals("infFail")) {
	    if (target instanceof KETBomHeader) {
		KETBomHeader bomHeader = (KETBomHeader) target;
		bomNumber = bomHeader.getNewBOMCode();
		if (creator == null) {
		    creator = ClassifyPBOUtil.extractPBOInfo(target).get("creator").toString();
		}
	    }

	    if (target instanceof KETBomEcoHeader) {
		KETBomEcoHeader ecoBom = (KETBomEcoHeader) target;
		bomNumber = ecoBom.getEcoHeaderNumber();
		if (creator == null) {
		    creator = ClassifyPBOUtil.extractPBOInfo(target).get("creator").toString();
		}

	    }


	    mailTitle = "BOM 인터페이스 오류 알림";
	    happenDate = DateUtil.getCurrentDateString("d");
	}

	//        Kogger.debug(getClass(), "[mailTitle] :" +mailTitle);
	//        Kogger.debug(getClass(), "[objTitle] :" +objTitle);
	//        Kogger.debug(getClass(), "[reqUser] :" +reqUser);
	//        Kogger.debug(getClass(), "[creator] :" +creator);
	//        Kogger.debug(getClass(), "[reqDate] :" +reqDate);
	//        Kogger.debug(getClass(), "[goLinkPage] :" +goLinkPage);
	//        Kogger.debug(getClass(), "[bomNumber] :" +bomNumber);
	//        Kogger.debug(getClass(), "[happenDate] :" +happenDate);
	//        Kogger.debug(getClass(), "[host] :" +host);

	Kogger.debug(MailUtil.class, "mailTitle====" + mailTitle);

	retHash.put("mailTitle", mailTitle);
	retHash.put("objTitle", objTitle);
	retHash.put("reqUser", reqUser);
	retHash.put("creator", creator);
	retHash.put("reqDate", reqDate);
	retHash.put("goLinkPage", goLinkPage);
	retHash.put("bomNumber", bomNumber);
	retHash.put("happenDate", happenDate);
	retHash.put("host", host);
	if (!"참조".equals(objTitle)) {
	    retHash.put("approvalHistory", getApprovalHistory(pboOid));
	}

	return retHash;
    }

    @SuppressWarnings("unchecked")
    public static String getApprovalHistory(String pboOid) {
	Kogger.debug(MailUtil.class, "타켓 ===" + pboOid.toString());
	StringBuffer sb = new StringBuffer();
	KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
	KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
	KETWfmApprovalMaster master = null;
	Object temp = new Object();
	Vector vec = null;
	WTObject targetObj;

	try {
	    targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(pboOid));
	    if (targetObj != null) master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    Kogger.debug(MailUtil.class, "mass=" + master);
	    if (master != null) {

		vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

		for (int i = 0; i < vec.size() - 1; i++) {
		    for (int j = i + 1; j < vec.size(); j++) {
			history1 = (KETWfmApprovalHistory) vec.get(i);
			history2 = (KETWfmApprovalHistory) vec.get(j);
			if (history1.getSeqNum() > history2.getSeqNum()) {
			    temp = vec.get(i);
			    vec.set(i, vec.get(j));
			    vec.set(j, temp);
			}
		    }
		}
	    }

	    Kogger.debug(MailUtil.class, "vec=[" + vec);

	    if (vec != null) {
		boolean isApprover = true;
		String activityName = "&nbsp;";
		int complet = 0;
		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			complet++;
		    }
		}

		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    activityName = StringUtil.checkNull(history.getActivityName());

		    if (activityName.equals("합의")) {
			complet++;
		    }

		    if ((complet == i) && isApprover && activityName.equals("검토")) {
			activityName = "승인";
			isApprover = false;
		    }
		    if ("수신".equals(activityName)) {
			break;
		    }

		    People people = PeopleHelper.manager.getPeople(history.getOwner().getName());
		    String completeDate = "&nbsp;";
		    if (history.getCompletedDate() != null) completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd HH:mm:ss");

		    String comments = ParamUtil.checkStrParameter(history.getComments(), "&nbsp;");
		    String owner = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
		    if (history.getDelegate() != null) owner = owner + "(" + history.getDelegate() + ")";


		    //Kogger.debug(getClass(), "complet ::: " + complet + " iiii ::: " + i + " activityName ::: " + activityName + " owner ::: " + owner);

		    /**
		     * ☞ 담당자 : 이충원(인사팀), 결재일 : 2011-06-30 14:33 의 견 : ☞ 결재자1 : 김성철(인사팀), 결재일 : 2011-06-30 15:29 의 견 : ☞ 결재자2 : 하창남(경영기획본부),
		     * 결재일 : 2011-06-30 15:53 의 견 :
		     * */
		    sb.append("☞ ");
		    sb.append(history.getSeqNum());
		    sb.append(" ");
		    sb.append(activityName);
		    sb.append(" : ");
		    sb.append(owner);
		    sb.append("(");
		    sb.append(ParamUtil.checkStrParameter(people.getDuty(), "&nbsp;"));

		    sb.append("), 결재일 : ");
		    sb.append(completeDate);
		    sb.append("<br>   의     견 : ");
		    sb.append(comments);
		    sb.append("<br>");
		}
	    }
	    else {
		sb.append("<br>☞ 결재 이력이 없습니다.");
	    }

	    Kogger.debug(MailUtil.class, "sb=" + sb.toString());
	} catch (WTException e) {
	    Kogger.error(MailUtil.class, e);
	}

	return sb.toString();
    }
}
