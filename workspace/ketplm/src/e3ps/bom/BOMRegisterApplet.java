package e3ps.bom;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import netscape.javascript.JSObject;
import wt.method.RemoteMethodServer;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkin.CheckInCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class BOMRegisterApplet extends Applet {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    String initBomOid = "";
    String workType = "";
    String objectType = "";
    String ecoType = "";
    String childItemCode = "";
    String ecoNumber = "";
    String parentItemCodes = "";

    private BOMRegisterDesktop frame = null;
    boolean isStatusOk = false;
    boolean isAllOk = false;

    private boolean isNotPreloading = true;

    AbstractAIFUIApplication app; // shin....
    BOMTreeNode rootNode; // shin...
    BOMRegisterApplicationPanel pnl; // shin....

    public void init() {
	try {
	    String isPreloading = Utility.checkNVL(getParameter("is_preloading"));
	    isNotPreloading = (isPreloading != null && isPreloading.equalsIgnoreCase("Y")) ? false : true;

	    if (isNotPreloading) {

		String userId = Utility.checkNVL(getParameter("userId"));
		String userName = Utility.checkNVL(getParameter("userName"));
		String userDept = Utility.checkNVL(getParameter("userDept"));
		String userEMail = Utility.checkNVL(getParameter("userEMail"));
		String userGroup = Utility.checkNVL(getParameter("adminFlag"));
		String userRole = Utility.checkNVL(getParameter("bomAccessFlag"));

		// [START] [PLM System 1차 고도화] 애플릿 바로 실행시 강제 로그인 처리, 2014-06-10, 김태현
		if (userId.equals("")) {

		    userId = "Administrator";

		    RemoteMethodServer methodServer = RemoteMethodServer.getDefault();
		    if (methodServer.getUserName() == null) {
			methodServer.setUserName("wcadmin");
			methodServer.setPassword("wcadmin");

		    }

		    if (userName.equals(""))
			userName = "관리자";
		    if (userDept.equals(""))
			userDept = "한국단자공업㈜";
		    if (userEMail.equals(""))
			userEMail = "neho@ket.com";
		    if (userGroup.equals(""))
			userGroup = "System Administrator";
		    if (userRole.equals(""))
			userRole = "OWNER";

		}

		String statusStr = Utility.checkNVL(getParameter("statusStr"));
		String bomEcoStatusStr = Utility.checkNVL(getParameter("bomEcoStatusStr"));
		if (statusStr.equals(""))
		    statusStr = ":INWORK,작업중:UNDERREVIEW,검토중:APPROVED,승인완료:REJECTED,반려됨:REWORK,재작업";
		if (bomEcoStatusStr.equals(""))
		    bomEcoStatusStr = ":PLANNING,계획수립:EXCUTEACTIVITY,활동수행:ACTIVITYCOMPLETED,활동완료:UNDERREVIEW,검토중:APPROVED,승인완료:REJECTED,반려됨:REWORK,재작업";
		Kogger.debug(getClass(), "===>> statusStr : " + statusStr);
		Kogger.debug(getClass(), "===>> bomEcoStatusStr : " + bomEcoStatusStr);
		setStatusHash(statusStr);
		setBomEcoStatusHash(bomEcoStatusStr);

		String orgCode = Utility.checkNVL(getParameter("orgCode"));
		String orgName = Utility.checkNVL(getParameter("orgName"));
		if (orgCode.equals(""))
		    orgCode = "000";
		if (orgName.equals(""))
		    orgName = "KET";
		BOMSearchUtilDao bomSearchUtilDao = new BOMSearchUtilDao();
		String orgID = "";
		try {
		    orgID = bomSearchUtilDao.getOrgID(orgCode);
		} catch (Exception e) {
		    // Kogger.error(getClass(), e);
		    // java.sql.SQLSyntaxErrorException: ORA-00942: 테이블 또는 뷰가 존재하지 않습니다
		}
		if (orgID.equals(""))
		    orgID = "0010";

		Kogger.debug(getClass(), "===>> userId : " + userId);
		Kogger.debug(getClass(), "===>> userName : " + userName);
		Kogger.debug(getClass(), "===>> userDept : " + userDept);
		Kogger.debug(getClass(), "===>> userEMail : " + userEMail);
		Kogger.debug(getClass(), "===>> userGroup : " + userGroup);
		Kogger.debug(getClass(), "===>> userRole : " + userRole);
		Kogger.debug(getClass(), "===>> user orgCode : " + orgCode);
		Kogger.debug(getClass(), "===>> user orgName : " + orgName);
		Kogger.debug(getClass(), "===>> user orgID : " + orgID);

		String serverCodebase = Utility.checkNVL(getParameter("wc_server_codebase"));
		if (serverCodebase.equals(""))
		    serverCodebase = "http://plmapdev.ket.com/plm/";
		Kogger.debug(getClass(), "===>> serverCodebase : " + serverCodebase);
		// [END] [PLM System 1차 고도화] 애플릿 바로 실행시 강제 로그인 처리, 2014-06-10, 김태현

		/*
	         * Start [PLM System 1차개선] 내용 : 다국어 처리, 2013. 08. 13, 김무준
	         */
		String language = Utility.checkNVL(getParameter("language"));
		if (language.equals(""))
		    language = "ko";
		KETMessageService.initService(language);
		Kogger.debug(getClass(), "===> KETMessageService service inited. (language=" + language + ")");
		Locale.setDefault(KETMessageService.service.getLocale());
		/*
	         * End [PLM System 1차개선] 내용 : 다국어 처리, 2013. 08. 13, 김무준
	         */

		BOMBasicInfoPool.setAuthHeader("");
		BOMBasicInfoPool.setUserGroup(userGroup);
		BOMBasicInfoPool.setUserId(userId);
		BOMBasicInfoPool.setUserName(userName);
		BOMBasicInfoPool.setUserDept(userDept);

		if (!userGroup.equals("") || !userRole.equals("")) {
		    BOMBasicInfoPool.setIsAdminFlag(true);
		}

		BOMBasicInfoPool.setUserEMail(userEMail);
		BOMBasicInfoPool.setUserRole(userRole);
		BOMBasicInfoPool.setOrgCode(orgCode);
		BOMBasicInfoPool.setOrgName(orgName);
		// Added by MJOH, 2007-01-03
		BOMBasicInfoPool.setOrgId(orgID);
		BOMBasicInfoPool.setServerCodebase(serverCodebase);
	    }

	    isAllOk = true;
	} catch (Exception e) {
	    isAllOk = false;
	    Kogger.error(getClass(), e);
	}
    }

    public void start() {
	super.start();

	if (isNotPreloading) {
	    try {
		initBomOid = Utility.checkNVL(getParameter("oid"));
		workType = Utility.checkNVL(getParameter("workType"));
		objectType = Utility.checkNVL(getParameter("objectType"));
		ecoType = Utility.checkNVL(getParameter("ecoType"));
		childItemCode = Utility.checkNVL(getParameter("childItemCode")); // 일괄변경인 경우, 대상 자부품번호
		ecoNumber = Utility.checkNVL(getParameter("ecoNumber")); // 일괄변경인 경우, ECO Number
		parentItemCodes = Utility.checkNVL(getParameter("parentItemCodes")); // 일괄변경인 경우, 대상 모부품번호 ("," 구분자로 나열되어있음)

		Kogger.debug(getClass(), "===>> initBomOid : " + initBomOid);
		Kogger.debug(getClass(), "===>> workType : " + workType);
		Kogger.debug(getClass(), "===>> obectType : " + objectType);
		Kogger.debug(getClass(), "===>> ecoType : " + ecoType);
		Kogger.debug(getClass(), "===>> childItemCode : " + childItemCode);
		Kogger.debug(getClass(), "===>> ecoNumber : " + ecoNumber);
		Kogger.debug(getClass(), "===>> parentItemCodes : " + parentItemCodes);

		final BOMRegisterDesktop desktop = new BOMRegisterDesktop(initBomOid, workType, objectType, ecoType, this, childItemCode,
		        ecoNumber, parentItemCodes);
		frame = desktop;
		app = desktop.getApp(); // shin...

		desktop.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		desktop.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowevent) {
			try {
			    if (isActive()) {
				int n = JOptionPane.showConfirmDialog(desktop, messageRegistry.getString("closeBom"), "Confirm",
				        JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
				    setAutocheckin();
				    BOMBasicInfoPool.setPublicBOMOid("");

				    // BOM Applet 을 실행시킨 웹브라우저 창을 다는다.
				    JSObject win = (JSObject) JSObject.getWindow(desktop.getParentApplet());
				    // //////////////////////////////////////
				    // Added by MJOH, 2011-04-03
				    // //////////////////////////////////////
				    // if( ecoType != null && ecoType.trim().length() > 0 )
				    // {
				    // win.call("winRefresh", null);
				    // }
				    // //////////////////////////////////////
				    win.call("winClose", null);
				    // //////////////////////////////////////////////////////////////////////////

				    desktop.dispose();
				}
			    } else {
				setAutocheckin();
				// BOM Applet 을 실행시킨 웹브라우저 창을 다는다.
				JSObject win = (JSObject) JSObject.getWindow(desktop.getParentApplet());
				// //////////////////////////////////////
				// Added by MJOH, 2011-04-03
				// //////////////////////////////////////
				// if( ecoType != null && ecoType.trim().length() > 0 )
				// {
				// win.call("winRefresh", null);
				// }
				// //////////////////////////////////////
				win.call("winClose", null);
				// //////////////////////////////////////////////////////////////////////////

				desktop.dispose();
			    }
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }
		});

		if (!isStatusOk) {
		    MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("basicInfoError"), "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    desktop.dispose();
		}
		if (!isAllOk) {
		    MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("serverConnectionError"), "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    desktop.dispose();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
    }

    private void setStatusHash(String statusStr) {
	try {
	    String state = "";
	    int count = 0;
	    LinkedHashMap htblStatus = new LinkedHashMap();

	    if (!statusStr.equals("") || !statusStr.equals("null")) {
		StringTokenizer toc = new StringTokenizer(statusStr, ":");
		count = toc.countTokens();
		if (count > 1) {
		    while (toc.hasMoreTokens()) {
			state = toc.nextToken();
			if (state.length() > 1) {
			    int flag = state.indexOf(",");
			    htblStatus.put(state.substring(0, flag), state.substring(flag + 1));
			}
		    }

		    BOMBasicInfoPool.setStatusHash(htblStatus);
		    isStatusOk = true;
		}
	    }
	} catch (Exception wte) {
	    Kogger.error(getClass(), wte);
	    isStatusOk = false;
	}
    }

    private void setBomEcoStatusHash(String bomEcoStatusStr) {
	try {
	    String state = "";
	    int count = 0;
	    LinkedHashMap htblStatus = new LinkedHashMap();

	    if (!bomEcoStatusStr.equals("") || !bomEcoStatusStr.equals("null")) {
		StringTokenizer toc = new StringTokenizer(bomEcoStatusStr, ":");
		count = toc.countTokens();
		if (count > 1) {
		    while (toc.hasMoreTokens()) {
			state = toc.nextToken();
			if (state.length() > 1) {
			    int flag = state.indexOf(",");
			    htblStatus.put(state.substring(0, flag), state.substring(flag + 1));
			}
		    }

		    BOMBasicInfoPool.setBomEcoStatusHash(htblStatus);
		    isStatusOk = true;
		}
	    }
	} catch (Exception wte) {
	    Kogger.error(getClass(), wte);
	    isStatusOk = false;
	}
    }

    public void openExplore(String url, String target) {
	try {
	    AppletContext ac = getAppletContext();
	    ac.showDocument(new URL(url), target);
	} catch (MalformedURLException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void stop() {
	if (frame != null) {
	    BOMBasicInfoPool.setPublicBOMOid("");
	    frame.dispose();
	}
    }

    public void destroy() {
	if (frame != null) {
	    frame.dispose();
	}
    }

    // shin.........
    private void setAutocheckin() {
	try {
	    BOMCheckOutInDao bcoi = new BOMCheckOutInDao();
	    Vector vccom = new Vector();

	    BOMTreeTableModel model = (BOMTreeTableModel) ((BOMRegisterApplicationPanel) app.getApplicationPanel()).getTreeTableModel();
	    pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	    rootNode = (BOMTreeNode) model.getRootNode();
	    Enumeration rootEnum = rootNode.preorderEnumeration();

	    while (rootEnum.hasMoreElements()) {
		BOMTreeNode treeNode = (BOMTreeNode) rootEnum.nextElement();
		BOMAssyComponent bomComponent = treeNode.getBOMComponent();
		vccom.add(bomComponent.getItemCodeStr());
		bcoi.setCheckIn(bomComponent.getItemCodeStr(), BOMBasicInfoPool.getUserId());
	    }

	    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true
		    : false;
	    // checkIn(vccom, bomGubunFlag);
	    CheckInCmd ci = new CheckInCmd(app.getDesktop(), app);
	    ci.checkIn(vccom, bomGubunFlag);
	} catch (Exception e) {

	}
    }
}
