package e3ps.bom.command.updatebom;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

import wt.part.WTPart;
import wt.util.WTException;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.MainEditorPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.removebom.RemoveOperationWithoutCommit;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.ExpandTreeNode;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.CheckSameNode;
import e3ps.bom.common.util.CheckSameNodeOnlyPN;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AIFSession;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class UpdateBOMDialog extends AbstractAIFDialog {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BOMTreeNode selectedNode;
    private BOMTreeNode targetNode;
    public MainEditorPanel mainEditorPane;
    private JTreeTable treetable;
    private BOMTreeTableModel model;
    private AbstractAIFUIApplication app;
    private JFrame desktop;

    private ModifyPNOperation modifyOp = null;
    AIFSession session = new AIFSession();
    BOMChildItemSearchDialog searchItemDlg;

    Registry appReg;

    private String dbName = "";
    private String childItemStr = "";
    private String title = "";
    boolean isNewItemCode = false;
    boolean bomGubunFlag = false;
    String userInfo = "";

    // Added by MJOH, 2011-04-07
    String partTypeStr = "";

    public Thread runner = null;

    // shin....
    private BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();
    private BOMCheckOutInDao bcoi = new BOMCheckOutInDao();

    public UpdateBOMDialog() {
    }

    public UpdateBOMDialog(AbstractAIFUIApplication app, BOMTreeNode node, JTreeTable treetable, BOMTreeTableModel model, String title) {
	super(app.getDesktop(), "Update BOM", true);
	this.desktop = app.getDesktop();
	this.app = app;
	this.selectedNode = node;
	this.treetable = treetable;
	this.model = model;
	this.title = title;

	appReg = Registry.getRegistry(app);
	dbName = appReg.getString("plm");

	bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
	userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
	// userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

	partTypeStr = selectedNode.getBOMComponent().getIBAPartType();

	searchItemDlg = new BOMChildItemSearchDialog(desktop, app, "", title);

	if (searchItemDlg.getOK() == true) {
	    // 다중 선택해서 자품목 추가시 사용
	    if (title != null && title.equalsIgnoreCase("Add")) {
		Vector selectedItemVec = searchItemDlg.getSelectedRows();
		String aryResult[] = new String[8];

		for (int i = 0; i < selectedItemVec.size(); i++) {
		    aryResult = (String[]) selectedItemVec.elementAt(i);
		    childItemStr = aryResult[0];

		    try {
			isNewItemCode = isNewItemCode();

			if (isNewItemCode) {
			    insertNewPN();
			} else {
			    insertOldPN();
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}
	    } else {
		String aryResult[] = new String[8];
		aryResult = searchItemDlg.getSelectedColumnData();
		childItemStr = aryResult[0];

		modifyOp = new ModifyPNOperation();
		session.queueOperation(modifyOp);
	    }
	}
    }

    public UpdateBOMDialog(AbstractAIFUIApplication app, BOMTreeNode node, JTreeTable treetable, BOMTreeTableModel model, String title, String itemCode) {
	super(app.getDesktop(), "Update BOM", true);
	this.desktop = app.getDesktop();
	this.app = app;
	this.selectedNode = node;
	this.treetable = treetable;
	this.model = model;
	this.title = title;

	appReg = Registry.getRegistry(app);
	dbName = appReg.getString("plm");

	bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
	userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";
	// userInfo = Utility.checkNVL(BOMBasicInfoPool.getUserDept()) + "/" + Utility.checkNVL(BOMBasicInfoPool.getUserName());

	WTPart wp = null;

	String chkItem = itemCode.substring(0, 3);

	if ((chkItem.equals("H71") || chkItem.equals("H72") || chkItem.equals("H73") || chkItem.equals("H74") || chkItem.equals("H75") || chkItem.equals("H76") || chkItem.equals("H78") || chkItem
	        .equals("H79")) && itemCode.indexOf("A-2") > 0) {
	    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
	    wp = bean.searchItem(itemCode);

	    if (wp == null) {
		MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00368")/* 후도금제품 부품코드가 존재하지 않습니다. */, "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }

	    childItemStr = itemCode;
	    modifyOp = new ModifyPNOperation();
	    session.queueOperation(modifyOp);
	} else if (chkItem.equals("S20")) {
	    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
	    wp = bean.searchItem(itemCode);
	    if (wp == null) {
		MessageBox m = new MessageBox(app.getDesktop(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00247")/* 수지스트랩 부품코드가 존재하지 않습니다. */, "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	    childItemStr = itemCode;
	    modifyOp = new ModifyPNOperation();
	    session.queueOperation(modifyOp);
	} else {
	    searchItemDlg = new BOMChildItemSearchDialog(desktop, app, itemCode, title);

	    if (searchItemDlg.getOK() == true) {
		String aryResult[] = new String[8];
		aryResult = searchItemDlg.getSelectedColumnData();
		childItemStr = aryResult[0];

		modifyOp = new ModifyPNOperation();
		session.queueOperation(modifyOp);
	    }
	}
    }

    private void stopOperation() {
	try {
	    runner.stop();
	} catch (Exception e) {
	    loadWarningMessage(messageRegistry.getString("update"));
	}
    }

    private boolean isNewItemCode() // shin...@@@@@@@@@@@@@@@@ 신규부품인지 OLD인지 체크하는 곳... ??
    {
	DBConnectionManager resource = null;
	Connection connection = null;

	boolean isExistFlag = true;
	Vector resultList = new Vector();
	isNewItemCode = false;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    // Item 이 존재하는지 체크..
	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, childItemStr);

	    long dataCount = dao.getDataCount();

	    if (dataCount == 0) {
		isExistFlag = false;
	    }

	    if (isExistFlag) {
		dao.newItemSearch(connection, childItemStr);

		dataCount = dao.getDataCount();
		resultList = dao.getResultListVec();

		if (dataCount > 0) {
		    isNewItemCode = true;
		}
	    }
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	return isNewItemCode;
    }

    // New Item 을 선택한 상황에서 OK를 누른경우...
    private void insertNewPN() {
	String strVal = "";
	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();

	if (bomGubunFlag) {
	    // 자신의 NewFlag가 NEW이어야만 아래에 새로운 자식을 추가할 수 있다.
	    if (!selectedComponent.getNewFlagStr().equalsIgnoreCase("NEW")) {
		MessageBox m = new MessageBox(desktop, messageRegistry.getString("update1"), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }

	    // [2011-02-28] 추가요구사항 처리 :: BOM 신규 생성시 ERP 에 존재여부 확인
	    try {
		strVal = KETBomHelper.service.getIsBomComponent(selectedComponent.getItemCodeStr());

		if (strVal != null && !strVal.equals("") && strVal.equals("X")) {
		    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00186", selectedComponent.getItemCodeStr())/*
											                                                                                      * 부품번호 ({0}) 는 이미 ERP에
											                                                                                      * BOM이 구성되어있습니다.
											                                                                                      */, "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    return;
		}
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	DBConnectionManager resource = null;
	Connection connection = null;
	BOMSearchDao dao = new BOMSearchDao();

	// New Item 추가 시 BOMComponent / BOMECOComponent 에 존재하는지 체크하여 한군데라도 존재한다면 insert 가 되지 않도록 막음
	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    boolean isUsedItemCode = false;

	    isUsedItemCode = dao.isUsedItemCode(connection, childItemStr);

	    if (isUsedItemCode) {
		MessageBox m = new MessageBox(desktop, messageRegistry.getString("update2"), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 현재 선택된 노드의 자식 중에 지금 insert 하고자 하는 자식과 같은 노드가 있으면 에러 발생
	Object[] childrenNodes = selectedNode.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    // 동일 레벨에 같은 노드가 발견
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00144")/* 동일한 레벨에 같은 노드가 발견되었습니다. \n 부품코드 */+ " : "
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	// 현재 Insert 하고자 하는 node 중에서 부모들(Root까지) 노드와 같은 이름을 가진 노드를 입력하려는 경우.
	BOMTreeNode[] parentsPathNode = selectedNode.getPathNode();
	for (int x = 0; x < parentsPathNode.length; x++) {
	    BOMTreeNode targetParentNode = (BOMTreeNode) parentsPathNode[x];
	    if (targetParentNode.getBOMComponent().getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		// 같은 노드 발견 된 경우.. 에러 발생을 시켜야함.
		MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00142")/* 동일한 노드의 모부품입니다. \n 부품코드 */+ " : "
		        + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	// 현재 BOM Model 내에.. 같은 P/N, 모 P/N 을 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨당.
	CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), selectedComponent);
	Vector sameNodeResult = check.getResultList();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    BOMSaveDao saveDao = new BOMSaveDao();

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		BOMAssyComponent targetComponent2 = targetComponent.createNewComponent();
		targetComponent2.setLevelInt(new Integer(sameAssyComponent.getLevelInt().intValue() + 1));
		targetComponent2.setParentItemCodeStr(sameAssyComponent.getItemCodeStr());
		targetComponent2.setIBAPartType(sameAssyComponent.getIBAPartType());

		int maxSortOrder = 0;
		if (sameNode.getChildCount() == 0) {
		    maxSortOrder = 1;
		    if (sameAssyComponent.getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE)
			sameAssyComponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
		} else {
		    Object[] childNodeArray = selectedNode.getChildren();
		    for (int x = 0; x < childNodeArray.length; x++) {
			BOMTreeNode childNode = (BOMTreeNode) childNodeArray[x];
			String s = ((BOMAssyComponent) childNode.getBOMComponent()).getSortOrderStr();
			String ss = s.substring(s.length() - 4, s.length());
			int sortNum = (new Integer(ss)).intValue();
			if (maxSortOrder < sortNum)
			    maxSortOrder = sortNum;
		    }
		    maxSortOrder++;
		}

		String sortOrder = "";
		if (maxSortOrder < 10)
		    sortOrder = "000" + maxSortOrder;
		else if (maxSortOrder < 100)
		    sortOrder = "00" + maxSortOrder;
		else if (maxSortOrder < 1000)
		    sortOrder = "0" + maxSortOrder;
		else if (maxSortOrder < 10000)
		    sortOrder = "" + maxSortOrder;

		targetComponent2.setSortOrderStr(sameAssyComponent.getSortOrderStr() + sortOrder);

		if (bomGubunFlag) {
		    targetComponent2.setNewFlagStr("NEW");
		    targetComponent2.setSecondMarkStr("NEW");
		}

		String sortOrderStr = "";
		sortOrderStr = targetComponent2.getSortOrderStr().trim();

		if (!sortOrderStr.substring(sortOrderStr.length() - 4).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 4) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 3).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 3) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 2).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 2) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 1).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 1) + 0));

		Vector checkOutItemVec = new Vector();
		checkOutItemVec.addElement(childItemStr);
		Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec.size() > 0) {
		    targetComponent2.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|") + 1));
		}

		targetComponent2.setOpSeqInt(new Integer(1));
		// Added by MJHO, 2011-04-07
		// targetComponent2.setIBAPartType( partTypeStr );

		// [2011-02-21] 임승영D 요구사항 처리 :: 원재료, 스크랩인 경우 수량을 0으로 셋팅함
		if (!targetComponent2.getItemCodeStr().equals("")) {
		    if (targetComponent2.getItemCodeStr().substring(0, 2).equals("R1") || targetComponent2.getItemCodeStr().substring(0, 2).equals("R2")
			    || targetComponent2.getItemCodeStr().substring(0, 2).equals("S1") || targetComponent2.getItemCodeStr().substring(0, 2).equals("S2")) {
			Kogger.debug(getClass(), "@@@@@@ 0.0 으로 셋팅!! ");
			targetComponent2.setQuantityDbl(0.0);
		    }
		}

		targetNode = new BOMTreeNode(sameNode, targetComponent2);
		sameNode.addElement(targetNode);

		if (bomGubunFlag) {
		    // New Item 저장
		    Enumeration enum0 = targetNode.preorderEnumeration();
		    Vector bomVec = new Vector();

		    while (enum0.hasMoreElements()) {
			BOMTreeNode node = (BOMTreeNode) enum0.nextElement();
			BOMAssyComponent ac = (BOMAssyComponent) node.getBOMComponent();
			if (!ac.getItemCodeStr().trim().equals(BOMBasicInfoPool.getPublicModelName().trim())) {
			    bomVec.add(ac);
			}

			// shin...체크아웃.
			if (ac.getNewFlagStr() != null && ac.getNewFlagStr().equals("NEW")) {
			    String versionStr = (ac.getVersionStr()).equals("") ? "0" : ac.getVersionStr();
			    ac.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
			    CheckOutCmd coc = new CheckOutCmd();
			    coc.workingUpdate(true);
			    checkoutDao.setCheckOut(ac.getItemCodeStr(), versionStr, BOMBasicInfoPool.getUserId());
			}
		    }

		    saveDao.saveBomList(connection, BOMBasicInfoPool.getPublicModelName().trim(), bomVec);

		    sameAssyComponent.setFirstMarkStr("NEW");

		    saveDao.addItemUpdate(connection, sameAssyComponent);
		} else {
		    sameAssyComponent.setFirstMarkStr("NEW");
		}

		connection.commit();

		model.fireTreeChanged(sameNode); // 무조건 전체레벨 펼치기 위해 주석처리함

		if (selectedNode == sameNode) {
		    treetable.repaint();
		    TreePath treepath = new TreePath(targetNode.getPath());

		    try {
			treetable.getTree().fireTreeWillExpand(treepath);
			treetable.getTree().scrollPathToVisible(treepath);
			treetable.getTree().fireTreeExpanded(treepath);
			treetable.getTree().setSelectionPath(treepath);

			treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
		    } catch (Exception ex) {
			Kogger.error(getClass(), ex);
		    }
		}

		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel) app.getApplicationPanel();
		BOMTreeTableModel model = (BOMTreeTableModel) bomPanel.getTreeTableModel();
		BOMTreeNode rootNode = model.getRootNode();
		Enumeration enum0 = rootNode.preorderEnumeration();

		BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
		BOMAssyComponent chkComponent = nodes[0].getBOMComponent();
		String selectedItem = String.valueOf(chkComponent);
		String chkItem = selectedItem.substring(0, 3);

		AddBOMCmd ab = new AddBOMCmd(desktop, app);
		if ((chkItem.equals("H71") || chkItem.equals("H72") || chkItem.equals("H73") || chkItem.equals("H74") || chkItem.equals("H75") || chkItem.equals("H76") || chkItem.equals("H78") || chkItem
		        .equals("H79")) && selectedItem.indexOf("-2") > 0 && selectedItem.indexOf("A-2") == -1) {
		    ab.autoCommand(selectedItem.replace("-2", "A-2"));
		} else if (chkItem.equals("R20")) {
		    while (enum0.hasMoreElements()) {
			BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
			BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();
			String itemCd = bomassy.getItemCodeStr();
			int lev = bomassy.getLevelInt();
			if (itemCd.equals(chkComponent.getParentItemCodeStr()) && lev == (chkComponent.getLevelInt() - 1)) {
			    TreePath treepath = new TreePath(allListNode.getPath());
			    try {
				treetable.getTree().fireTreeWillExpand(treepath);
				treetable.getTree().scrollPathToVisible(treepath);
				treetable.getTree().fireTreeExpanded(treepath);
				treetable.getTree().setSelectionPath(treepath);
				treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
			    } catch (Exception ex) {
				Kogger.error(getClass(), ex);
				MessageBox.post(ex);
			    }
			}
		    }

		    ab.autoCommand("S" + selectedItem.substring(1, selectedItem.length()));

		} else if (chkItem.equals("R10")) {
		    while (enum0.hasMoreElements()) {
			BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
			BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();
			String itemCd = bomassy.getItemCodeStr();
			int lev = bomassy.getLevelInt();
			if (itemCd.equals(chkComponent.getParentItemCodeStr()) && lev == (chkComponent.getLevelInt() - 1)) {
			    TreePath treepath = new TreePath(allListNode.getPath());
			    try {
				treetable.getTree().fireTreeWillExpand(treepath);
				treetable.getTree().scrollPathToVisible(treepath);
				treetable.getTree().fireTreeExpanded(treepath);
				treetable.getTree().setSelectionPath(treepath);
				treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
			    } catch (Exception ex) {
				Kogger.error(getClass(), ex);
				MessageBox.post(ex);
			    }
			}
		    }

		    ab.autoCommand("S18*");
		}
	    }

	    // Sequence Number 보정한 후에 Total Data Count를 Setting한다.
	    Enumeration enum0 = model.getRootNode().preorderEnumeration();
	    int realSeqNumber = 1;
	    while (enum0.hasMoreElements()) {
		BOMAssyComponent component = ((BOMTreeNode) enum0.nextElement()).getBOMComponent();
		if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) {
		    BOMAssyComponent assyComponent = (BOMAssyComponent) component;
		    assyComponent.setSeqInt(new Integer(realSeqNumber++));
		}
	    }

	    BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);

	    // 전체레벨 펼치기
	    treetable.getTree().setSelectionRow(0);
	    expandTreeTable(20);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}
    }

    // Old Item 을 선택한 상황에서 OK를 누른경우...
    private void insertOldPN() {
	String strVal = "";
	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();

	if (bomGubunFlag) {
	    if (!selectedComponent.getNewFlagStr().equalsIgnoreCase("NEW")) {
		MessageBox m = new MessageBox(desktop, messageRegistry.getString("update1"), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }

	    // [2011-02-28] 추가요구사항 처리 :: BOM 신규 생성시 ERP 에 존재여부 확인
	    try {
		strVal = KETBomHelper.service.getIsBomComponent(selectedComponent.getItemCodeStr());
		if (strVal != null && !strVal.equals("") && strVal.equals("X")) {
		    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00186", selectedComponent.getItemCodeStr())/*
											                                                                                      * 부품번호 ({0}) 는 이미 ERP에
											                                                                                      * BOM이 구성되어있습니다.
											                                                                                      */, "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    return;
		}
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;
	DBConnectionManager resource = null;
	Connection connection = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);

	    Kogger.debug(getClass(), "===>> targetDataCount : " + targetDataCount);
	    Kogger.debug(getClass(), "===>> targetComponent : " + targetComponent.toString());

	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	Object[] childrenNodes = selectedNode.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    // 형제 노드중에 같은 노드가 발견
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00366")/* 형제 노드중 같은 노드가 발견되었습니다. \n 부품코드 : */
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	// 현재 BOM Model 내에.. 같은 P/N을 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨다.
	CheckSameNodeOnlyPN check = new CheckSameNodeOnlyPN(model.getRootNode().preorderEnumeration(), selectedComponent);
	Vector sameNodeResult = check.getResultList();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    BOMSaveDao saveDao = new BOMSaveDao();

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		BOMAssyComponent targetComponent2 = targetComponent.createNewComponent();
		targetComponent2.setLevelInt(new Integer(sameAssyComponent.getLevelInt().intValue() + 1));
		targetComponent2.setParentItemCodeStr(sameAssyComponent.getItemCodeStr());
		targetComponent2.setIBAPartType(sameAssyComponent.getIBAPartType());

		// Sort Order 를 조정하기 위해서 계산 하는 부분...
		int maxSortOrder = 0;
		if (sameNode.getChildCount() == 0) {
		    maxSortOrder = 1;
		    if (sameAssyComponent.getComponentTypeStr() != BOMAssyComponent.MODEL_TYPE)
			sameAssyComponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
		} else {
		    Object[] childNodeArray = selectedNode.getChildren();

		    for (int x = 0; x < childNodeArray.length; x++) {
			BOMTreeNode childNode = (BOMTreeNode) childNodeArray[x];
			String s = ((BOMAssyComponent) childNode.getBOMComponent()).getSortOrderStr();
			String ss = s.substring(s.length() - 4, s.length());
			int sortNum = (new Integer(ss)).intValue();
			if (maxSortOrder < sortNum)
			    maxSortOrder = sortNum;
		    }
		    maxSortOrder++;
		}

		String sortOrder = "";
		if (maxSortOrder < 10)
		    sortOrder = "000" + maxSortOrder;
		else if (maxSortOrder < 100)
		    sortOrder = "00" + maxSortOrder;
		else if (maxSortOrder < 1000)
		    sortOrder = "0" + maxSortOrder;
		else if (maxSortOrder < 10000)
		    sortOrder = "" + maxSortOrder;

		targetComponent2.setSortOrderStr(sameAssyComponent.getSortOrderStr() + sortOrder);
		targetComponent2.setNewFlagStr("OLD");
		targetComponent2.setSecondMarkStr("NEW");
		targetComponent2.setOpSeqInt(new Integer(1));
		// Addded by MJOH, 2011-04-07
		// targetComponent2.setIBAPartType( partTypeStr );

		// [2011-02-21] 임승영D 요구사항 처리 :: 원재료, 스크랩인 경우 수량을 0으로 셋팅함
		Kogger.debug(getClass(), "@@@@@@ targetComponent2.getItemCodeStr : " + targetComponent2.getItemCodeStr());
		if (!targetComponent2.getItemCodeStr().equals("")) {
		    if (targetComponent2.getItemCodeStr().substring(0, 2).equals("R1") || targetComponent2.getItemCodeStr().substring(0, 2).equals("R2")
			    || targetComponent2.getItemCodeStr().substring(0, 2).equals("S1") || targetComponent2.getItemCodeStr().substring(0, 2).equals("S2")) {
			Kogger.debug(getClass(), "@@@@@@ 0.0 으로 셋팅!! ");
			targetComponent2.setQuantityDbl(0.0);
		    }
		}

		String sortOrderStr = "";
		sortOrderStr = targetComponent2.getSortOrderStr().trim();

		if (!sortOrderStr.substring(sortOrderStr.length() - 4).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 4) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 3).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 3) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 2).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 2) + 0));
		else if (!sortOrderStr.substring(sortOrderStr.length() - 1).toString().equals("0"))
		    targetComponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length() - 1) + 0));

		Vector checkOutItemVec = new Vector();
		checkOutItemVec.addElement(childItemStr);
		Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec.size() > 0) {
		    targetComponent2.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|") + 1));
		}

		targetNode = new BOMTreeNode(sameNode, targetComponent2);
		sameNode.addElement(targetNode);

		if (selectedNode == sameNode) {
		    InsertItemOperation insertOp = new InsertItemOperation(connection, sameNode, targetNode, childItemStr, model, treetable, true);
		    insertOp.executeOperation();
		} else {
		    InsertItemOperation insertOp = new InsertItemOperation(connection, sameNode, targetNode, childItemStr, model, treetable, false);
		    insertOp.executeOperation();
		}

		if (bomGubunFlag) {
		    sameAssyComponent.setFirstMarkStr("NEW");

		    saveDao.addItemUpdate(connection, sameAssyComponent);

		    // shin...체크아웃. 신규일때 만 해야함
		    if (targetComponent2.getNewFlagStr() != null && targetComponent2.getNewFlagStr().equals("NEW")) {
			String versionStr = (targetComponent2.getVersionStr()).equals("") ? "0.0" : targetComponent2.getVersionStr();
			targetComponent2.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
			CheckOutCmd coc = new CheckOutCmd();
			coc.workingUpdate(true);
			checkoutDao.setCheckOut(targetComponent2.getItemCodeStr(), versionStr, BOMBasicInfoPool.getUserId());
		    }
		} else {
		    sameAssyComponent.setFirstMarkStr("NEW");
		}

		connection.commit();
		Kogger.debug(getClass(), "@@@@@@@@@@ 여기까지는 왔니????");

		model.fireTreeChanged(sameNode); // 무조건 전체레벨 펼치기 위해 주석처리함

		if (selectedNode == sameNode) {
		    treetable.repaint();
		    TreePath treepath = new TreePath(targetNode.getPath());

		    try {
			treetable.getTree().fireTreeWillExpand(treepath);
			treetable.getTree().scrollPathToVisible(treepath);
			treetable.getTree().fireTreeExpanded(treepath);
			treetable.getTree().setSelectionPath(treepath);

			treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
		    } catch (Exception ex) {
			Kogger.error(getClass(), ex);
		    }
		}

		BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel) app.getApplicationPanel();
		BOMTreeTableModel model = (BOMTreeTableModel) bomPanel.getTreeTableModel();
		BOMTreeNode rootNode = model.getRootNode();
		Enumeration enum0 = rootNode.preorderEnumeration();

		BOMTreeNode[] nodes = bomPanel.getSelectedTreeNode();
		BOMAssyComponent chkComponent = nodes[0].getBOMComponent();
		String selectedItem = String.valueOf(chkComponent);
		String chkItem = selectedItem.substring(0, 3);
		Kogger.debug(getClass(), "@@@@@ chkItem : " + chkItem);

		AddBOMCmd ab = new AddBOMCmd(desktop, app);
		if ((chkItem.equals("H71") || chkItem.equals("H72") || chkItem.equals("H73") || chkItem.equals("H74") || chkItem.equals("H75") || chkItem.equals("H76") || chkItem.equals("H78") || chkItem
		        .equals("H79")) && selectedItem.indexOf("-2") > 0 && selectedItem.indexOf("A-2") == -1) {
		    ab.autoCommand(selectedItem.replace("-2", "A-2"));
		} else if (chkItem.equals("R20")) {
		    Kogger.debug(getClass(), "@@@@@ selectedItem 1: " + selectedItem);
		    while (enum0.hasMoreElements()) {
			BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
			BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();
			String itemCd = bomassy.getItemCodeStr();
			int lev = bomassy.getLevelInt();
			if (itemCd.equals(chkComponent.getParentItemCodeStr()) && lev == (chkComponent.getLevelInt() - 1)) {
			    TreePath treepath = new TreePath(allListNode.getPath());
			    try {
				treetable.getTree().fireTreeWillExpand(treepath);
				treetable.getTree().scrollPathToVisible(treepath);
				treetable.getTree().fireTreeExpanded(treepath);
				treetable.getTree().setSelectionPath(treepath);
				treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
			    } catch (Exception ex) {
				Kogger.error(getClass(), ex);
				MessageBox.post(ex);
			    }
			}
		    }

		    Kogger.debug(getClass(), "@@@@@ selectedItem 2: " + selectedItem);

		    ab.autoCommand("S" + selectedItem.substring(1, selectedItem.length()));
		} else if (chkItem.equals("R10")) {
		    while (enum0.hasMoreElements()) {
			BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
			BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();
			String itemCd = bomassy.getItemCodeStr();
			int lev = bomassy.getLevelInt();
			if (itemCd.equals(chkComponent.getParentItemCodeStr()) && lev == (chkComponent.getLevelInt() - 1)) {
			    TreePath treepath = new TreePath(allListNode.getPath());
			    try {
				treetable.getTree().fireTreeWillExpand(treepath);
				treetable.getTree().scrollPathToVisible(treepath);
				treetable.getTree().fireTreeExpanded(treepath);
				treetable.getTree().setSelectionPath(treepath);
				treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
			    } catch (Exception ex) {
				Kogger.error(getClass(), ex);
				MessageBox.post(ex);
			    }
			}
		    }

		    ab.autoCommand("S18*");
		}
	    }

	    // Sequence Number 보정한 후에 Total Data Count 를 Setting 한다.
	    Enumeration enum0 = model.getRootNode().preorderEnumeration();
	    int realSeqNumber = 1;
	    while (enum0.hasMoreElements()) {
		BOMAssyComponent component = ((BOMTreeNode) enum0.nextElement()).getBOMComponent();
		if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) {
		    BOMAssyComponent assyComponent = (BOMAssyComponent) component;
		    assyComponent.setSeqInt(new Integer(realSeqNumber++));
		}
	    }

	    BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);

	    // 전체레벨 펼치기
	    treetable.getTree().setSelectionRow(0);
	    expandTreeTable(20);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}
    }

    // Reference BOM 생성
    public void insertReferenceBom(String refItemCode, BOMTreeNode selectedNode, String dbName, boolean flag) {
	String itemCodeStr = refItemCode;

	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();

	long targetDataCount = 0;
	DBConnectionManager resource = null;
	Connection connection = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, itemCodeStr);

	    targetDataCount = dao.getDataCount();

	    Kogger.debug(getClass(), "----->> targetDataCount : " + targetDataCount);
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setModal(true);
	    m.setVisible(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setModal(true);
	    m.setVisible(true);

	    desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	// 현재 BOM 내에 같은 Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨다.
	CheckSameNodeOnlyPN check = new CheckSameNodeOnlyPN(selectedNode.preorderEnumeration(), selectedComponent);
	Vector sameNodeResult = check.getResultList();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		sameAssyComponent.setItemSeqInt(new Integer(10));
		sameAssyComponent.setFirstMarkStr("NEW");

		targetNode = sameNode;

		try {
		    if (flag) {
			if (selectedNode == sameNode) {
			    RefInsertItemOperation insertOp = new RefInsertItemOperation(sameNode, targetNode, itemCodeStr);
			    insertOp.executeOperation();
			} else {
			    RefInsertItemOperation insertOp = new RefInsertItemOperation(sameNode, targetNode, itemCodeStr);
			    insertOp.executeOperation();
			}
		    } else {
			if (selectedNode == sameNode) {
			    BasicInsertItemOperation insertOp = new BasicInsertItemOperation(sameNode, targetNode, itemCodeStr);
			    insertOp.executeOperation();
			} else {
			    BasicInsertItemOperation insertOp = new BasicInsertItemOperation(sameNode, targetNode, itemCodeStr);
			    insertOp.executeOperation();
			}
		    }
		} catch (SQLException sqle) {
		    loadWarningMessage();
		} catch (Exception ex) {
		    Kogger.error(getClass(), ex);
		    try {
			connection.rollback();
		    } catch (Exception dbex) {
		    }

		    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	    connection.commit();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

    }

    // New Item 을 선택한 상황에서 OK를 누른경우...
    private void changeNewPN() {
	BOMAssyComponent deletedComponent = selectedNode.getBOMComponent();

	if (deletedComponent.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update4"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	DBConnectionManager resource = null;
	Connection connection = null;
	BOMSearchDao dao = new BOMSearchDao();

	// New Item 추가 시 BOMComponent / BOMECOComponent 에 존재하는지 체크하여 한군데라도 존재한다면 insert 가 되지 않도록 막음
	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    boolean isUsedItemCode = false;

	    isUsedItemCode = dao.isUsedItemCode(connection, childItemStr);

	    if (isUsedItemCode) {
		MessageBox m = new MessageBox(desktop, messageRegistry.getString("update2"), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	int childCount = selectedNode.getChildCount();

	// 입력한 값의 BOM Assy/Item 이 존재하는지 조사.
	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	BOMTreeNode[] parentsPathNode = selectedNode.getPathNode();
	// 자기 자신부터 ROOT까지의 PATH에 있는 NODE를 반환(ROOT=0, 자기자신은 배열의 length-1)
	BOMTreeNode parentNodeView = parentsPathNode[parentsPathNode.length - 2]; // 상위 노드

	Object[] childrenNodes = parentNodeView.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00144")/* 동일한 레벨에 같은 노드가 발견되었습니다. \n 부품코드 */+ " : "
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	for (int x = 0; x < parentsPathNode.length - 1; x++) {
	    BOMTreeNode targetParentNode = (BOMTreeNode) parentsPathNode[x];
	    if (targetParentNode.getBOMComponent().getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00142")/* 동일한 노드의 모부품입니다. \n 부품코드 */+ " : "
		        + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	Enumeration enumChild = selectedNode.preorderEnumeration();
	while (enumChild.hasMoreElements()) {
	    BOMTreeNode childrenNode = (BOMTreeNode) enumChild.nextElement();
	    if (childrenNode.getBOMComponent().getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00143")/* 동일한 노드의 자부품입니다. \n 부품코드 */+ " : "
		        + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), deletedComponent);
	Vector sameNodeResult = check.getResultList();

	BOMTreeNode changeNode = null;

	Vector removeVec = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    BOMSaveDao saveDao = new BOMSaveDao();

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		if (Utility.checkNVL(sameAssyComponent.getCheckOutStr()).equals(userInfo)) {
		    removeVec.addElement(Utility.checkNVL(sameAssyComponent.getItemCodeStr()));
		}

		BOMAssyComponent targetComponent2 = targetComponent.createNewComponent();

		targetComponent2.setLevelInt(sameAssyComponent.getLevelInt());
		targetComponent2.setSeqInt(sameAssyComponent.getSeqInt());
		targetComponent2.setItemSeqInt(sameAssyComponent.getItemSeqInt());
		targetComponent2.setOpSeqInt(sameAssyComponent.getOpSeqInt());
		targetComponent2.setSortOrderStr(sameAssyComponent.getSortOrderStr());
		targetComponent2.setComponentTypeStr(sameAssyComponent.getComponentTypeStr());
		targetComponent2.setParentItemCodeStr(sameAssyComponent.getParentItemCodeStr());
		targetComponent2.setNewFlagStr("NEW");
		targetComponent2.setSupplyTypeStr(sameAssyComponent.getSupplyTypeStr());
		targetComponent2.setUitStr(sameAssyComponent.getUitStr());
		targetComponent2.setQuantityDbl(sameAssyComponent.getQuantityDbl());
		targetComponent2.setSubAssyComponent(sameAssyComponent.getSubAssyComponent());
		targetComponent2.setDesignatorComponent(sameAssyComponent.getDesignatorComponent());
		// Added by MJOH, 2011-04-07
		targetComponent2.setIBAPartType(sameAssyComponent.getIBAPartType());

		Vector checkOutItemVec = new Vector();
		checkOutItemVec.addElement(childItemStr);
		Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec.size() > 0) {
		    targetComponent2.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|") + 1));
		}

		// 자식 노드의 모 Item Code 를 바꿔준다.
		Object[] childNodes = sameNode.getChildren();
		childNodes = sameNode.getChildren();
		Vector childList = new Vector();
		for (int j = 0; j < childNodes.length; j++) {
		    BOMTreeNode childNode = (BOMTreeNode) childNodes[j];
		    BOMAssyComponent childComponent = childNode.getBOMComponent();
		    childComponent.setParentItemCodeStr(targetComponent2.getItemCodeStr());
		    childComponent.setEcoNoStr(""); // childComponent.setEcoNoStr("");
		    childComponent.setSecondMarkStr("NEW");

		    BOMAssyComponent nextAssyComponent = new BOMAssyComponent("TEMP");
		    if (j + 1 != childNodes.length)
			nextAssyComponent = ((BOMTreeNode) childNodes[j + 1]).getBOMComponent();

		    if (!childComponent.getItemCodeStr().equalsIgnoreCase(nextAssyComponent.getItemCodeStr()) || childComponent.getLevelInt().intValue() != nextAssyComponent.getLevelInt().intValue()) {
			childComponent.setStartDate(Utility.currentDate());
		    }
		    childList.addElement(childComponent);
		}

		// 바로 하위 1레벨 자식노드에 대한 정보를 Update 한다.
		changeNode = sameNode;

		if (bomGubunFlag) {
		    saveDao.changeChildItemUpdate(connection, childList);
		}

		targetComponent2.setSecondMarkStr("NEW");
		if (childNodes.length != 0) {
		    targetComponent2.setFirstMarkStr("NEW");
		    targetComponent2.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
		} else {
		    targetComponent2.setFirstMarkStr("");
		    targetComponent2.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
		}

		if (bomGubunFlag) {
		    saveDao.changeItemUpdate(connection, childItemStr, targetComponent2);
		}

		sameNode.setBOMComponent(targetComponent2);

		Kogger.debug(getClass(), "Change New P/N : " + sameNode.getBOMComponent().toString());
		connection.commit();

		model.fireTreeChanged(sameNode);
		treetable.repaint();

		if (selectedNode == sameNode) {
		    TreePath treepath = new TreePath(sameNode.getPath());
		    try {
			treetable.getTree().fireTreeWillExpand(treepath);
			treetable.getTree().scrollPathToVisible(treepath);
			treetable.getTree().fireTreeExpanded(treepath);
			treetable.getTree().setSelectionPath(treepath);

			treetable.scrollRectToVisible(new Rectangle(0, treetable.getSelectedRow() * treetable.getRowHeight(), 1, treetable.getRowHeight()));
		    } catch (Exception ex) {
			Kogger.error(getClass(), ex);
		    }
		}

		// shin.....교체된 부품 체크아웃 처리
		if (targetComponent2.getNewFlagStr() != null && targetComponent2.getNewFlagStr().equals("NEW")) {
		    String versionStr = (targetComponent2.getVersionStr()).equals("") ? "0.0" : targetComponent2.getVersionStr();
		    targetComponent2.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
		    CheckOutCmd coc = new CheckOutCmd();
		    coc.workingUpdate(true);
		    checkoutDao.setCheckOut(sameNode.getBOMComponent().toString(), versionStr, BOMBasicInfoPool.getUserId());
		}

		// 버튼 활성화 등등..
		BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();
		BOMBasicInfoPool.setPublicCheckOutStatus("2");
		pnl.setCheckStatus(2);
		pnl.setMyStatus(3);
		pnl.setMenuBarEnabled();
		pnl.publicStatusPanel.setStatusBar();

	    }

	    if (removeVec.size() > 0) {
		Hashtable inputParams = new Hashtable();
		inputParams.put("bomOid", Utility.checkNVL(BOMBasicInfoPool.getPublicBOMOid()));
		inputParams.put("flag", "Y");
		inputParams.put("itemCode", removeVec);

		if (bomGubunFlag) {
		    inputParams.put("bomEcoFlag", "N");
		} else {
		    inputParams.put("bomEcoFlag", "Y");
		}

		KETBomHelper.service.setCoworkerCancelCheckOut(inputParams);
	    }

	    // shin....기존부품 Checkout 테이블에서 삭제
	    try {
		bcoi.setCheckIn(deletedComponent.getItemCodeStr(), BOMBasicInfoPool.getUserId());
	    } catch (Exception e) {
		Kogger.debug(getClass(), "###############3 CheckIn Error : " + e);
	    }

	    // 전체레벨 펼치기
	    treetable.getTree().setSelectionRow(0);
	    expandTreeTable(20);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}
    }

    // Old Item 을 선택한 상황에서 OK를 누른경우...
    private void changeOldPN() {
	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();

	// 모델(Root Node)은 Change가 될수 없다.
	BOMAssyComponent deletedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();
	if (deletedComponent.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update5"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 입력한 값의 BOM Assy/Part가 존재하는지 조사.
	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;
	DBConnectionManager resource = null;
	Connection connection = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    // 입력한 P/N의 정보를 가져온다.
	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 현재 선택된 노드의 형제 중에 지금 change 하고자 하는 자식과 같은 노드가 있으면 에러 발생
	BOMTreeNode[] parentsPathNode = selectedNode.getPathNode();
	BOMTreeNode parentNodeView = parentsPathNode[parentsPathNode.length - 2];

	// Replace 의 경우에는 한 Assy밑에 중복된 자 Item Code 가 입력되지 않도록 막는다.
	Object[] childrenNodes = parentNodeView.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00144")/* 동일한 레벨에 같은 노드가 발견되었습니다. \n 부품코드 */+ " : "
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	// 현재 Change 하고자 하는 node 중에서 부모들(Root까지) 노드와 같은 이름을 가진 노드를 입력하려는 경우.
	for (int x = 0; x < parentsPathNode.length - 1; x++) {
	    BOMTreeNode targetParentNode = (BOMTreeNode) parentsPathNode[x];
	    if (targetParentNode.getBOMComponent().getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00142")/* 동일한 노드의 모부품입니다. \n 부품코드 */+ " : "
		        + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	// 현재 BOM Model 내에.. 같은 Item Code, Parent Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨당.
	CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), deletedComponent);
	Vector sameNodeResult = check.getResultList();

	Vector removeVec = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    BOMSaveDao saveDao = new BOMSaveDao();

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);

		// 변경전 Assy 를 담아둔다...
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		if (Utility.checkNVL(sameAssyComponent.getCheckOutStr()).equals(userInfo)) {
		    removeVec.addElement(Utility.checkNVL(sameAssyComponent.getItemCodeStr()));
		}

		BOMTreeNode[] parents = sameNode.getPathNode();
		BOMTreeNode parentNode = parents[parents.length - 2];

		BOMTreeNode[] deleteNodes = new BOMTreeNode[1];
		deleteNodes[0] = sameNode;

		// Target Component를 이용하여 BOMTreeNode를 구성한다.
		BOMAssyComponent targetComponent2 = targetComponent.createNewComponent();
		targetComponent2.setLevelInt(sameAssyComponent.getLevelInt());
		Kogger.debug(getClass(), "###### getSeqInt : " + sameAssyComponent.getSeqInt());
		targetComponent2.setSeqInt(sameAssyComponent.getSeqInt());
		targetComponent2.setItemSeqInt(sameAssyComponent.getItemSeqInt());
		targetComponent2.setOpSeqInt(sameAssyComponent.getOpSeqInt());
		targetComponent2.setSortOrderStr(sameAssyComponent.getSortOrderStr());
		targetComponent2.setParentItemCodeStr(sameAssyComponent.getParentItemCodeStr());
		targetComponent2.setNewFlagStr("OLD");
		targetComponent2.setFirstMarkStr("");
		targetComponent2.setSecondMarkStr(sameAssyComponent.getSecondMarkStr());
		targetComponent2.setSupplyTypeStr(sameAssyComponent.getSupplyTypeStr());
		targetComponent2.setUitStr(sameAssyComponent.getUitStr());
		targetComponent2.setQuantityDbl(sameAssyComponent.getQuantityDbl());
		targetComponent2.setSubAssyComponent(sameAssyComponent.getSubAssyComponent());
		targetComponent2.setDesignatorComponent(sameAssyComponent.getDesignatorComponent());
		targetComponent2.setOpSeqInt(new Integer(1));
		// Added by MJOH, 2011-04-07
		targetComponent2.setIBAPartType(sameAssyComponent.getIBAPartType());

		Vector checkOutItemVec = new Vector();
		checkOutItemVec.addElement(childItemStr);
		Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec.size() > 0) {
		    targetComponent2.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|") + 1));
		}

		targetNode = new BOMTreeNode(parentNode, targetComponent2);

		RemoveOperationWithoutCommit removeOp = new RemoveOperationWithoutCommit(connection, app, deleteNodes, model, treetable);
		removeOp.executeOperation();

		parentNode.addElement(targetNode);

		if (selectedNode == sameNode) {
		    InsertItemOperation insertOp = new InsertItemOperation(connection, parentNode, targetNode, childItemStr, model, treetable, true);
		    insertOp.executeOperation();
		} else {
		    InsertItemOperation insertOp = new InsertItemOperation(connection, parentNode, targetNode, childItemStr, model, treetable, false);
		    insertOp.executeOperation();
		}

		connection.commit();
	    }

	    if (removeVec.size() > 0) {
		Hashtable inputParams = new Hashtable();
		inputParams.put("bomOid", Utility.checkNVL(BOMBasicInfoPool.getPublicBOMOid()));
		inputParams.put("flag", "Y");
		inputParams.put("itemCode", removeVec);

		if (bomGubunFlag) {
		    inputParams.put("bomEcoFlag", "N");
		} else {
		    inputParams.put("bomEcoFlag", "Y");
		}

		KETBomHelper.service.setCoworkerCancelCheckOut(inputParams);
	    }

	    // 전체레벨 펼치기
	    treetable.getTree().setSelectionRow(0);
	    expandTreeTable(20);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}
    }

    // Old Item 을 선택한 상황에서 OK를 누른경우...
    private void changeEcoNewPN() {
	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();

	// 모델(Root Node)은 Change가 될수 없다.
	BOMAssyComponent deletedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();
	if (deletedComponent.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update5"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 입력한 값의 BOM Assy/Part가 존재하는지 조사.
	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;
	DBConnectionManager resource = null;
	Connection connection = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    // 입력한 P/N의 정보를 가져온다.
	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 현재 선택된 노드의 형제 중에 지금 change 하고자 하는 자식과 같은 노드가 있으면 에러 발생
	BOMTreeNode[] parentsPathNode = selectedNode.getPathNode();
	BOMTreeNode parentNodeView = parentsPathNode[parentsPathNode.length - 2];

	// Replace 의 경우에는 한 Assy밑에 중복된 자 Item Code 가 입력되지 않도록 막는다.
	Object[] childrenNodes = parentNodeView.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00144")/* 동일한 레벨에 같은 노드가 발견되었습니다. \n 부품코드 */+ " : "
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	// 현재 Change 하고자 하는 node 중에서 부모들(Root까지) 노드와 같은 이름을 가진 노드를 입력하려는 경우.
	for (int x = 0; x < parentsPathNode.length - 1; x++) {
	    BOMTreeNode targetParentNode = (BOMTreeNode) parentsPathNode[x];
	    if (targetParentNode.getBOMComponent().getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00142")/* 동일한 노드의 모부품입니다. \n 부품코드 */+ " : "
		        + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	// 현재 BOM Model 내에.. 같은 Item Code, Parent Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨당.
	CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), deletedComponent);
	Vector sameNodeResult = check.getResultList();

	Vector removeVec = new Vector();

	try {
	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);

		// 변경전 Assy 를 담아둔다...
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		if (Utility.checkNVL(sameAssyComponent.getCheckOutStr()).equals(userInfo)) {
		    removeVec.addElement(Utility.checkNVL(sameAssyComponent.getItemCodeStr()));
		}

		BOMTreeNode[] parents = sameNode.getPathNode();
		BOMTreeNode parentNode = parents[parents.length - 2];

		BOMTreeNode[] deleteNodes = new BOMTreeNode[1];
		deleteNodes[0] = sameNode;

		// Target Component를 이용하여 BOMTreeNode를 구성한다.
		BOMAssyComponent targetComponent2 = targetComponent.createNewComponent();
		targetComponent2.setLevelInt(sameAssyComponent.getLevelInt());
		targetComponent2.setSeqInt(sameAssyComponent.getSeqInt());
		targetComponent2.setItemSeqInt(sameAssyComponent.getItemSeqInt());
		targetComponent2.setOpSeqInt(sameAssyComponent.getOpSeqInt());
		targetComponent2.setSortOrderStr(sameAssyComponent.getSortOrderStr());
		targetComponent2.setParentItemCodeStr(sameAssyComponent.getParentItemCodeStr());
		targetComponent2.setSupplyTypeStr(sameAssyComponent.getSupplyTypeStr());
		targetComponent2.setUitStr(sameAssyComponent.getUitStr());
		targetComponent2.setQuantityDbl(sameAssyComponent.getQuantityDbl());
		targetComponent2.setSubAssyComponent(sameAssyComponent.getSubAssyComponent());
		targetComponent2.setDesignatorComponent(sameAssyComponent.getDesignatorComponent());
		targetComponent2.setOpSeqInt(new Integer(1));
		// Added by MJOH, 2011-04-07
		targetComponent2.setIBAPartType(sameAssyComponent.getIBAPartType());

		Vector checkOutItemVec = new Vector();
		checkOutItemVec.addElement(childItemStr);
		Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutItemVec);
		if (coworkerVec.size() > 0) {
		    targetComponent2.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|") + 1));
		}

		targetNode = new BOMTreeNode(parentNode, targetComponent2);

		RemoveOperationWithoutCommit removeOp = new RemoveOperationWithoutCommit(connection, app, deleteNodes, model, treetable);
		removeOp.executeOperation();

		parentNode.addElement(targetNode);

		model.fireTreeChanged(parentNode);

		// 전체레벨 펼치기
		treetable.getTree().setSelectionRow(0);
		expandTreeTable(20);

		treetable.repaint();
	    }

	    if (removeVec.size() > 0) {
		Hashtable inputParams = new Hashtable();
		inputParams.put("bomOid", Utility.checkNVL(BOMBasicInfoPool.getPublicBOMOid()));
		inputParams.put("flag", "Y");
		inputParams.put("itemCode", removeVec);

		if (bomGubunFlag) {
		    inputParams.put("bomEcoFlag", "N");
		} else {
		    inputParams.put("bomEcoFlag", "Y");
		}

		KETBomHelper.service.setCoworkerCancelCheckOut(inputParams);
	    }
	} catch (Exception ex) {
	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	}
    }

    private void copyChild() {

	if (selectedNode.getChildren().length > 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update6"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	BOMAssyComponent selectedComponent = (BOMAssyComponent) selectedNode.getBOMComponent();
	if (bomGubunFlag) {
	    if (!selectedComponent.getNewFlagStr().equalsIgnoreCase("NEW")) {
		MessageBox m = new MessageBox(desktop, messageRegistry.getString("update1"), "Error", MessageBox.ERROR);
		m.setVisible(true);
		m.setModal(true);
		return;
	    }
	}

	long targetDataCount = 0;
	BOMAssyComponent targetComponent = null;
	DBConnectionManager resource = null;
	Connection connection = null;

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);

	    // Kogger.debug(getClass(), "@@@@@@@@@@@@@@@childItemStr  : "+childItemStr);

	    // 입력한 P/N의 정보를 가져온다.
	    BOMSearchDao dao = new BOMSearchDao();
	    dao.existItemSearch(connection, childItemStr);

	    targetDataCount = dao.getDataCount();
	    targetComponent = dao.getResultListComponent();
	    // Added by MJOH, 2011-04-07
	    targetComponent.setIBAPartType(partTypeStr);
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    MessageBox m = new MessageBox(desktop, "DB Error : " + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}

	if (targetDataCount == 0) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update3"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 현재 선택된 노드의 자식 중에 지금 insert 하고자 하는 자식과 같은 노드가 있으면 에러 발생
	Object[] childrenNodes = selectedNode.getChildren();
	if (childrenNodes != null) {
	    for (int x = 0; x < childrenNodes.length; x++) {
		BOMAssyComponent childComponent = ((BOMTreeNode) childrenNodes[x]).getBOMComponent();
		if (childComponent.getItemCodeStr().equalsIgnoreCase(targetComponent.getItemCodeStr())) {
		    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00144")/* 동일한 레벨에 같은 노드가 발견되었습니다. \n 부품코드 */+ " : "
			    + targetComponent.getItemCodeStr(), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		}
	    }
	}

	// 현재 BOM Model 내에.. 같은 P/N을 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨당.
	CheckSameNodeOnlyPN check = new CheckSameNodeOnlyPN(model.getRootNode().preorderEnumeration(), selectedComponent);
	Vector sameNodeResult = check.getResultList();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(dbName);
	    BOMSaveDao saveDao = new BOMSaveDao();

	    for (int i = 0; i < sameNodeResult.size(); i++) {
		BOMTreeNode sameNode = (BOMTreeNode) sameNodeResult.elementAt(i);
		BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

		targetNode = sameNode;

		if (selectedNode == sameNode) {
		    CopyChildOperation copyChildOp = new CopyChildOperation(connection, targetNode, childItemStr, model, treetable, true);
		    copyChildOp.executeOperation();
		} else {
		    CopyChildOperation copyChildOp = new CopyChildOperation(connection, targetNode, childItemStr, model, treetable, false);
		    copyChildOp.executeOperation();
		}

		sameAssyComponent.setFirstMarkStr("NEW");

		if (bomGubunFlag) {
		    saveDao.addItemUpdate(connection, sameAssyComponent);

		    connection.commit();
		}
	    }

	    // 전체레벨 펼치기
	    treetable.getTree().setSelectionRow(0);
	    expandTreeTable(20);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);

	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	} finally {
	    if (resource != null) {
		resource.freeConnection(dbName, connection);
	    }
	}
    }

    private void loadWarningMessage() {
	String message = messageRegistry.getString("update7");
	MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
	messagebox.setModal(true);
	messagebox.setVisible(true);

	dispose();
	stopOperation();
    }

    private void loadWarningMessage(String message) {
	MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
	messagebox.setModal(true);
	messagebox.setVisible(true);
    }

    // /////////////////////////////////////////////////////////////////////////
    class ModifyPNOperation extends AbstractAIFOperation {
	public ModifyPNOperation() {
	}

	public void executeOperation() throws Exception {

	    Kogger.debug(getClass(), "title : " + title);

	    if (title.equalsIgnoreCase("Replace")) {
		isNewItemCode = isNewItemCode();

		if (isNewItemCode) {
		    if (bomGubunFlag) {
			changeNewPN();
		    } else {
			changeEcoNewPN();
		    }
		} else {
		    Kogger.debug(getClass(), "@@@@ 여기야??? ");
		    changeOldPN();
		}
	    } else if (title.equalsIgnoreCase("Add")) {
		isNewItemCode = isNewItemCode();
		if (isNewItemCode) {
		    insertNewPN();
		} else {
		    insertOldPN();
		}
	    } else {
		// shin.....@@@@@@ CopyChild 호출...
		isNewItemCode = isNewItemCode();

		if (isNewItemCode) {
		    MessageBox m = new MessageBox(desktop, messageRegistry.getString("update8"), "Error", MessageBox.ERROR);
		    m.setVisible(true);
		    m.setModal(true);
		    return;
		} else {
		    copyChild();
		}
	    }
	}
    }

    public void expandTreeTable(int level) {
	// 선택된 Row 가 있는지 검사.
	if (treetable.getSelectedRowCount() != 1) {
	    MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
	    m.setVisible(true);
	    m.setModal(true);
	    return;
	}

	// 선택된 Row 의 node 를 구해서 BOMComponent를 빼낸다.
	BOMTreeNode selectedNode = (BOMTreeNode) treetable.getTree().getSelectionPath().getLastPathComponent();

	// Thread 로 구현.
	ExpandTreeNode expand = new ExpandTreeNode(selectedNode, level, treetable);
	expand.start();
    }
}
