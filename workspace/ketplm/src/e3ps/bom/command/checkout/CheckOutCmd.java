package e3ps.bom.command.checkout;

import java.awt.Cursor;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.UserData;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class CheckOutCmd extends AbstractAIFCommand {
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    JFrame desktop;
    BOMRegisterApplicationPanel pnl;
    BOMTreeTableModel model;
    BOMTreeNode rootNode;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    private String aryOwnerData[] = new String[4];
    String bomOid = "";
    String strCurStatus = "";

    Registry appReg;
    boolean bomGubunFlag = false;

    // shin...
    private BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

    public CheckOutCmd() {
	setUserData();
    }

    public CheckOutCmd(JFrame frame, AbstractAIFUIApplication app) {
	this.app = app;
	parent = frame;

	pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	pnl.setCursor(Cursor.getPredefinedCursor(3));

	appReg = Registry.getRegistry(app);

	bomOid = BOMBasicInfoPool.getPublicBOMOid();
	strCurStatus = BOMBasicInfoPool.getPublicBOMStatus();

	setUserData();

	pnl.setCursor(Cursor.getPredefinedCursor(0));
    }

    protected void executeCommand() throws Exception {
	pnl.setCursor(Cursor.getPredefinedCursor(3));

	try {
	    if ((BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("")) {
		bomGubunFlag = true;
	    }

	    BOMTreeNode[] nodes = pnl.getSelectedTreeNode();

	    String userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
	    BOMTreeNode[] selectedNode = pnl.getSelectedNode();

	    if (nodes == null) {
		pnl.setCursor(Cursor.getPredefinedCursor(0));
		MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectRow5"), "Error", MessageBox.ERROR);
		m.setModal(true);
		m.setVisible(true);
		return;
	    } else {

		Kogger.debug(getClass(), "===>> checkOut bomGubunFlag : " + bomGubunFlag);

		// BOM ECO CheckOut 에서 사용하는 로직
		if (!bomGubunFlag) {
		    if (nodes.length != 1) {
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow3"), "Error", MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
		    }

		    BOMAssyComponent selectedComponent = nodes[0].getBOMComponent();
		    if (!selectedComponent.getItemCodeStr().equals(BOMBasicInfoPool.getPublicModelName().trim())) {
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow4"), "Error", MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
		    }
		}

		// Admin or 멤버가 아니면
		if (!isMember(bomGubunFlag) && !(BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.SYS_ADMIN) || BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.BIZ_ADMIN))) {
		    pnl.setCursor(Cursor.getPredefinedCursor(0));
		    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("notCoworker"), "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    return;
		}

		BOMTreeNode[] parents = nodes[0].getPathNode();

		Kogger.debug(getClass(), "================ CheckOutCmd Not RootNode ==================");
		Kogger.debug(getClass(), "===>> selectedNode.length : " + nodes.length);

		for (int inx = 0; inx < selectedNode.length; inx++) {
		    BOMTreeNode node = selectedNode[inx];
		    Object[] childNodes = node.getChildren();

		    BOMAssyComponent sCmp = node.getBOMComponent();

		    if (bomGubunFlag) {
			Kogger.debug(getClass(), "selected Item Code : " + sCmp.getItemCodeStr());

			if (!sCmp.getNewFlagStr().equalsIgnoreCase("NEW")) {
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("checkNewItem"), "Error", MessageBox.ERROR);
			    m.setModal(true);
			    m.setVisible(true);
			    return;
			}

			// 최 상위 Root Node가 아닌 경우
			if (parents.length != 1) {
			    BOMTreeNode parentNode = (BOMTreeNode) node.getParent();
			    BOMAssyComponent passy = parentNode.getBOMComponent();

			    // shin....
			    if (checkoutDao.isItemCheckedOut(passy.getItemCodeStr())) {
				String userInfo2 = checkoutDao.getCheckOuterName(passy.getItemCodeStr()) + "(" + checkoutDao.getCheckOuterEMail(passy.getItemCodeStr()) + ")";
				if (userInfo.equalsIgnoreCase(userInfo2)) {
				    pnl.setCursor(Cursor.getPredefinedCursor(0));
				    MessageBox mbox = new MessageBox(app.getDesktop(),
					    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00169")/* 본인이 모품목을 체크아웃한 상태이므로 체크아웃할 수 없습니다. */, "Error", MessageBox.WARNING);
				    mbox.setModal(true);
				    mbox.setVisible(true);
				    return;
				} else {
				    pnl.setCursor(Cursor.getPredefinedCursor(0));
				    MessageBox mbox = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00154",
					    checkoutDao.getCheckOuterName(passy.getItemCodeStr()))/* 모품목이 다른 사용자[{0}]에 의해 체크아웃된 상태입니다. */, "Error", MessageBox.WARNING);
				    mbox.setModal(true);
				    mbox.setVisible(true);
				    return;
				}
			    }

			    // Kogger.debug(getClass(), "parent Item Code : " + passy.getItemCodeStr());
			    // Kogger.debug(getClass(), "parent getCheckOutStr : " + passy.getCheckOutStr());

			    /*
		             * if(!passy.getCheckOutStr().equals("")) { if(!passy.getCheckOutStr().equalsIgnoreCase(userInfo)) { pnl.setCursor(Cursor.getPredefinedCursor(0)); MessageBox mbox = new
		             * MessageBox(parent, messageRegistry.getString("alreadyCheckOut"), "Error", 0); mbox.setModal(true); mbox.setVisible(true); return; } }
		             */
			}

			Kogger.debug(getClass(), "===>> childNodes.length : " + childNodes.length);

			for (int i = 0; i < childNodes.length; i++) {
			    BOMTreeNode childNode = (BOMTreeNode) childNodes[i];
			    BOMAssyComponent bomassy = childNode.getBOMComponent();

			    // shin....
			    if (checkoutDao.isItemCheckedOut(bomassy.getItemCodeStr())) {
				String userInfo2 = checkoutDao.getCheckOuterName(bomassy.getItemCodeStr()) + "(" + checkoutDao.getCheckOuterEMail(bomassy.getItemCodeStr()) + ")";
				if (userInfo.equalsIgnoreCase(userInfo2)) {
				    pnl.setCursor(Cursor.getPredefinedCursor(0));
				    MessageBox mbox = new MessageBox(app.getDesktop(),
					    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00170")/* 본인이 자품목을 체크아웃한 상태이므로 체크아웃할 수 없습니다. */, "Error", MessageBox.WARNING);
				    mbox.setModal(true);
				    mbox.setVisible(true);
				    return;
				} else {
				    pnl.setCursor(Cursor.getPredefinedCursor(0));
				    MessageBox mbox = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00285",
					    checkoutDao.getCheckOuterName(bomassy.getItemCodeStr()))/* 자품목이 다른 사용자[{0}]에 의해 체크아웃된 상태입니다. */, "Error", MessageBox.WARNING);
				    mbox.setModal(true);
				    mbox.setVisible(true);
				    return;
				}
			    }

			    // Kogger.debug(getClass(), "child Item Code : " + bomassy.getItemCodeStr());
			    /*
		             * if(!bomassy.getCheckOutStr().equals("")) { if(!bomassy.getCheckOutStr().equalsIgnoreCase(userInfo)) { pnl.setCursor(Cursor.getPredefinedCursor(0)); MessageBox mbox = new
		             * MessageBox(parent, messageRegistry.getString("alreadyCheckOut1"), "Error", 0); mbox.setModal(true); mbox.setVisible(true); return; } }
		             */
			}
		    } else {
			if (!sCmp.getCheckOutStr().equals("")) {
			    if (!sCmp.getCheckOutStr().equalsIgnoreCase(userInfo)) {
				pnl.setCursor(Cursor.getPredefinedCursor(0));
				MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("alreadyCheckOut"), "Error", MessageBox.ERROR);
				m.setModal(true);
				m.setVisible(true);
				return;
			    }
			}

		    }
		}
		Kogger.debug(getClass(), "================ CheckOutCmd Not RootNode ==================");

		boolean isCheckOut = false;
		Vector itemCodeVec = new Vector();

		for (int inx = 0; inx < selectedNode.length; inx++) {
		    BOMTreeNode node = selectedNode[inx];
		    BOMAssyComponent bomassy = node.getBOMComponent();

		    itemCodeVec.addElement(bomassy.getItemCodeStr().trim());
		}

		Vector coworkerVec = KETBomHelper.service.getCheckOuter(itemCodeVec);
		String coworkerInfo = "";
		String coworkerItem = "";
		int idx = 0;

		for (int i = 0; i < coworkerVec.size(); i++) {
		    if (!(coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString()).equals("")) {
			idx = coworkerVec.elementAt(i) == null ? 0 : coworkerVec.elementAt(i).toString().indexOf("|");
		    }

		    coworkerItem = coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().substring(0, idx);
		    coworkerInfo = coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().substring(idx + 1);

		    if (coworkerInfo.equals(userInfo)) {
			isCheckOut = true;
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("alreadyCheckOut1"), "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    } else {
			if (!(coworkerInfo).equals("")) {
			    isCheckOut = true;
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("alreadyCheckOut"), "Error", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			}
		    }
		}

		if (!isCheckOut) {
		    if (bomGubunFlag) {
			// 권한 체크 로직
			Vector notAuthItemVec = new Vector();
			notAuthItemVec = KETBomHelper.service.checkAuthority(itemCodeVec);

			Kogger.debug(getClass(), ">>> notAuthItemVec : " + notAuthItemVec.toString());

			if (notAuthItemVec.size() > 0) {
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("checkAuth") + "\nItem Code : " + notAuthItemVec.toString(), "Error", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			}
		    }

		    if (strCurStatus.equalsIgnoreCase("INWORK") || strCurStatus.equalsIgnoreCase("REJECTED")) // 작업중, 재작업 상태인 경우
		    {
			if (!getMyStatus(bomGubunFlag).equalsIgnoreCase("4")) {
			    checkOut(nodes, itemCodeVec, userInfo, bomGubunFlag);
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("successCheckOut"), "Information", 1);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			} else {
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("endWorkingStatus"), "Error", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			}
		    } else if (strCurStatus.equalsIgnoreCase("UNDERREVIEW")) // 검토중
		    {
			if (!BOMBasicInfoPool.isAdmin()) // Admin 아니면
			{
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("underApproveStatus"), "Warning", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			} else // Admin 이면
			{
			    // 아무도 체크아웃 아니면..
			    checkOut(nodes, itemCodeVec, userInfo, bomGubunFlag);
			    pnl.setCursor(Cursor.getPredefinedCursor(0));
			    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("successCheckOut"), "Information", 1);
			    mbox.setModal(true);
			    mbox.setVisible(true);
			    return;
			}
		    } else if (strCurStatus.equalsIgnoreCase("Canceled")) {
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("canceledStatus"), "Warning", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    } else if (strCurStatus.equalsIgnoreCase("APPROVED")) // 승인완료
		    {
			pnl.setCursor(Cursor.getPredefinedCursor(0));
			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedStatus"), "Warning", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		}
	    }
	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	}
    }

    private boolean isMember(boolean flag) {
	String SQL = "";
	boolean memberFlag = false;

	try {
	    connectDB(appReg.getString("plm"));

	    if (flag) {
		SQL = " SELECT newbomcode                                                                                ";
		SQL += " FROM ketbomcoworker                                                                            ";
		SQL += " WHERE newbomcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'        ";
		SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
	    } else {
		SQL = " SELECT ecoheadernumber                                                                            ";
		SQL += " FROM ketbomecocoworker                                                                        ";
		SQL += " WHERE ecoheadernumber = '" + BOMBasicInfoPool.getBomEcoNumber().trim() + "'    ";
		SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
	    }

	    rs = stmt.executeQuery(SQL);

	    if (rs.next())
		memberFlag = true;
	    else
		memberFlag = false;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		closeDB(appReg.getString("plm"));
	    } catch (Exception e) {
	    }
	    return memberFlag;
	}
    }

    private void setUserData() {
	try {
	    aryOwnerData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
	    aryOwnerData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
	    aryOwnerData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());
	    aryOwnerData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	}
    }

    private String getMyStatus(boolean flag) {
	String status = "";
	String SQL = "";
	try {
	    connectDB(appReg.getString("plm"));

	    if (flag) {
		SQL = " SELECT endWorkingFlag                                                                            ";
		SQL += " FROM ketbomcoworker                                                                            ";
		SQL += " WHERE newbomcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'        ";
		SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
	    } else {
		SQL = " SELECT endWorkingFlag                                                                            ";
		SQL += " FROM ketbomecocoworker                                                                        ";
		SQL += " WHERE ecoheadernumber = '" + BOMBasicInfoPool.getBomEcoNumber().trim() + "'    ";
		SQL += " AND ecoitemcode = '" + BOMBasicInfoPool.getPublicModelName().trim() + "'                                                    ";
		SQL += " AND coworkerid = '" + aryOwnerData[0] + "'                                                    ";
	    }

	    rs = stmt.executeQuery(SQL);

	    while (rs.next()) {
		status = rs.getString("endWorkingFlag");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		closeDB(appReg.getString("plm"));
	    } catch (Exception e) {
	    }
	    return status;
	}
    }

    public String workingUpdate(boolean flag) {
	String status = "";
	String SQL = "";
	try {
	    connectDB(appReg.getString("plm"));

	    if (flag) {
		SQL = "UPDATE ketbomcoworker                                                                                            ";
		SQL += "SET endWorkingFlag = '3'                                                                                            ";
		SQL += "WHERE newbomcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName().trim()) + "'    ";
		SQL += "AND coworkerid = '" + aryOwnerData[0] + "'                                                                    ";
		SQL += "AND endWorkingFlag <> '4'                                                                                        ";
	    } else {
		SQL = "UPDATE ketbomecocoworker                                                                                        ";
		SQL += "SET endWorkingFlag = '3'                                                                                            ";
		SQL += "WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber().trim()) + "'    ";
		SQL += "AND ecoitemcode = '" + Utility.checkNVL(BOMBasicInfoPool.getPublicModelName().trim()) + "'        ";
		SQL += "AND coworkerid = '" + aryOwnerData[0] + "'                                                                    ";
		SQL += "AND endWorkingFlag <> '4'                                                                                        ";
	    }

	    stmt.executeUpdate(SQL);

	    conn.commit();
	} catch (Exception e) {
	    conn.rollback();
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		closeDB(appReg.getString("plm"));
	    } catch (Exception e) {
	    }
	    return status;
	}
    }

    // 체크아웃 ( rootNode 의 setCheckOut 포함 안 함 )
    public void checkOut(BOMTreeNode[] nodes, Vector checkOutItemVec, String user, boolean flag) {
	try {
	    pnl.setCursor(Cursor.getPredefinedCursor(3));

	    Hashtable inputParams = new Hashtable();
	    inputParams.put("bomOid", BOMBasicInfoPool.getPublicBOMOid());
	    inputParams.put("itemCode", checkOutItemVec);
	    if (flag) {
		inputParams.put("bomEcoFlag", "N");
	    } else {
		inputParams.put("bomEcoFlag", "Y");
	    }

	    // TODO 기능확인 필요:: 잠시 주석처리함
	    // KETBomHelper.service.setCoworkerCheckOut(inputParams);

	    // TODO 현재 BOM을 체크아웃한 사용자가 있는지 확인 후 진행함
	    BOMCheckOutInDao check = new BOMCheckOutInDao();
	    boolean isCheckOut = check.isItemCheckedOut(BOMBasicInfoPool.getPublicModelName());
	    Kogger.debug(getClass(), "@@@@ isRootCheckOut ?? : " + isCheckOut);

	    if (!isCheckOut) {
		workingUpdate(flag);

		Kogger.debug(getClass(), "=====>> setCoworkerCheckOut call");

		BOMBasicInfoPool.setPublicCheckOutStatus("2");
		pnl.setCheckStatus(2);
		pnl.setMyStatus(3);
		pnl.setMenuBarEnabled();
		pnl.publicStatusPanel.setStatusBar();

		BOMTreeTableModel model = (BOMTreeTableModel) ((BOMRegisterApplicationPanel) app.getApplicationPanel()).getTreeTableModel();
		JTreeTable treeTable = pnl.getTreeTable();

		rootNode = (BOMTreeNode) model.getRootNode();
		Kogger.debug(getClass(), "@@@@@ rootNode : " + rootNode);
		Kogger.debug(getClass(), "@@@@@ ChildCount : " + rootNode.getChildCount());
		Kogger.debug(getClass(), "@@@@@ getChildren : " + rootNode.getChildren().length);

		Enumeration enum0 = rootNode.preorderEnumeration();
		while (enum0.hasMoreElements()) {
		    BOMTreeNode sNode = (BOMTreeNode) enum0.nextElement();
		    BOMAssyComponent selectedComponent = (BOMAssyComponent) sNode.getBOMComponent();

		    for (int j = 0; j < checkOutItemVec.size(); j++) {
			Kogger.debug(getClass(), "@@@@ selectedComponent : " + selectedComponent.getItemCodeStr());
			Kogger.debug(getClass(), "@@@@ checkOutItemVec : " + checkOutItemVec.elementAt(j).toString());
			if ((selectedComponent.getItemCodeStr()).equalsIgnoreCase(checkOutItemVec.elementAt(j).toString())) {
			    selectedComponent.setCheckOutStr(aryOwnerData[1] + "(" + aryOwnerData[3] + ")");
			    // model.fireTreeChanged(sNode);
			}
		    }
		}

	    } else {
		MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00361")/* 현재 다른 공동작업자가 작업중이므로 조회만 가능합니다. */,
		        KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/* 확인 */, 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    }

	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	    MessageBox mbox = new MessageBox(parent, e.toString() + "\n" + messageRegistry.getString("checkInValid"), "Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	    return;
	}
    }

    // 체크아웃 ( rootNode 의 setCheckOut 포함 )
    public void checkOut(BOMTreeNode[] nodes, Vector checkOutItemVec, String user, boolean flag, String rootVersion) {
	try {
	    pnl.setCursor(Cursor.getPredefinedCursor(3));

	    Hashtable inputParams = new Hashtable();
	    inputParams.put("bomOid", BOMBasicInfoPool.getPublicBOMOid());
	    inputParams.put("itemCode", checkOutItemVec);
	    if (flag) {
		inputParams.put("bomEcoFlag", "N");
	    } else {
		inputParams.put("bomEcoFlag", "Y");
	    }

	    // TODO 기능확인 필요:: 잠시 주석처리함
	    // KETBomHelper.service.setCoworkerCheckOut(inputParams);

	    // TODO 현재 BOM을 체크아웃한 사용자가 있는지 확인 후 진행함
	    BOMCheckOutInDao check = new BOMCheckOutInDao();
	    boolean isCheckOut = check.isItemCheckedOut(BOMBasicInfoPool.getPublicModelName());
	    boolean isCheckOutByself = check.isItemCheckedOutCurrentUser(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
	    Kogger.debug(getClass(), "@@@@ isRootCheckOut ?? : " + isCheckOut);

	    if (!isCheckOut || isCheckOutByself) {
		workingUpdate(flag);

		Kogger.debug(getClass(), "=====>> setCoworkerCheckOut call");

		BOMBasicInfoPool.setPublicCheckOutStatus("2");
		pnl.setCheckStatus(2);
		pnl.setMyStatus(3);
		pnl.setMenuBarEnabled();
		pnl.publicStatusPanel.setStatusBar();

		BOMTreeTableModel model = (BOMTreeTableModel) ((BOMRegisterApplicationPanel) app.getApplicationPanel()).getTreeTableModel();
		JTreeTable treeTable = pnl.getTreeTable();

		rootNode = (BOMTreeNode) model.getRootNode();
		Kogger.debug(getClass(), "@@@@@ rootNode : " + rootNode);
		Kogger.debug(getClass(), "@@@@@ ChildCount : " + rootNode.getChildCount());
		Kogger.debug(getClass(), "@@@@@ getChildren : " + rootNode.getChildren().length);

		Enumeration enum0 = rootNode.preorderEnumeration();
		while (enum0.hasMoreElements()) {
		    BOMTreeNode sNode = (BOMTreeNode) enum0.nextElement();
		    BOMAssyComponent selectedComponent = (BOMAssyComponent) sNode.getBOMComponent();

		    for (int j = 0; j < checkOutItemVec.size(); j++) {
			Kogger.debug(getClass(), "@@@@ selectedComponent : " + selectedComponent.getItemCodeStr());
			Kogger.debug(getClass(), "@@@@ checkOutItemVec : " + checkOutItemVec.elementAt(j).toString());
			if ((selectedComponent.getItemCodeStr()).equalsIgnoreCase(checkOutItemVec.elementAt(j).toString())) {
			    selectedComponent.setCheckOutStr(aryOwnerData[1] + "(" + aryOwnerData[3] + ")");
			    // model.fireTreeChanged(sNode); // 주석처리해야 금형 BOM 중복으로 뿌려지는게 해결됨
			}
		    }
		}

		// 최상위 부품 버전 받아야겠다
		checkoutDao.setCheckOut(BOMBasicInfoPool.getPublicModelName(), rootVersion, BOMBasicInfoPool.getUserId());

	    } else {
		// 자신이 체크아웃한 상태인지 확인 후 메세지 보여주기
		isCheckOut = check.isItemCheckedOutCurrentUser(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
		if (!isCheckOutByself) {
		    MessageBox mbox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00361")/* 현재 다른 공동작업자가 작업중이므로 조회만 가능합니다. */,
			    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/* 확인 */, 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		}
	    }

	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	    MessageBox mbox = new MessageBox(parent, e.toString() + "\n" + messageRegistry.getString("checkInValid"), "Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	    return;
	}
    }

    private void connectDB(String dbname) throws ConnectException {
	try {
	    res = DBConnectionManager.getInstance();

	    conn = res.getConnection(dbname);

	    conn.setAutoCommit(false);
	    stmt = conn.createStatement();
	} catch (Exception e) {
	}
    }

    private void closeDB(String dbname) throws ConnectException {
	try {
	    if (rs != null)
		rs.close();
	    stmt.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	    MessageBox mbox = new MessageBox(parent, messageRegistry.getString("dbCloseError"), "Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	    return;
	} finally {
	    if (res != null) {
		res.freeConnection(dbname, conn);
	    }
	}
    }

    // shin...
    public void AutoCheckOut(BOMTreeNode[] nodes, Vector checkOutItemVec, String user, boolean flag) {
	try {
	    pnl.setCursor(Cursor.getPredefinedCursor(3));

	    Hashtable inputParams = new Hashtable();
	    inputParams.put("bomOid", BOMBasicInfoPool.getPublicBOMOid());
	    inputParams.put("itemCode", checkOutItemVec);
	    if (flag) {
		inputParams.put("bomEcoFlag", "N");
	    } else {
		inputParams.put("bomEcoFlag", "Y");
	    }

	    KETBomHelper.service.setCoworkerCheckOut(inputParams);

	    workingUpdate(flag);

	    Kogger.debug(getClass(), "=====>> setCoworkerCheckOut call");

	    BOMBasicInfoPool.setPublicCheckOutStatus("2");
	    pnl.setCheckStatus(2);
	    pnl.setMyStatus(3);
	    pnl.setMenuBarEnabled();
	    pnl.publicStatusPanel.setStatusBar();

	    BOMTreeTableModel model = (BOMTreeTableModel) ((BOMRegisterApplicationPanel) app.getApplicationPanel()).getTreeTableModel();
	    JTreeTable treeTable = pnl.getTreeTable();

	    rootNode = (BOMTreeNode) model.getRootNode();

	    Enumeration enum0 = rootNode.preorderEnumeration();
	    while (enum0.hasMoreElements()) {
		BOMTreeNode sNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent selectedComponent = sNode.getBOMComponent();

		for (int j = 0; j < checkOutItemVec.size(); j++) {
		    if ((selectedComponent.getItemCodeStr()).equalsIgnoreCase(checkOutItemVec.elementAt(j).toString())) {
			selectedComponent.setCheckOutStr(aryOwnerData[1] + "(" + aryOwnerData[3] + ")");
			model.fireTreeChanged(sNode);
		    }
		}
	    }
	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    pnl.setCursor(Cursor.getPredefinedCursor(0));
	    MessageBox mbox = new MessageBox(parent, e.toString() + "\n" + messageRegistry.getString("checkInValid"), "Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	    return;
	}
    }
}
