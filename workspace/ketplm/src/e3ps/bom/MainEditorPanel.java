package e3ps.bom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import wt.part.WTPart;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.bomproperty.BOMPropertyInsertDialog;
import e3ps.bom.command.bomproperty.TotalPropertyDialog;
import e3ps.bom.command.checkin.CheckInCmd;
import e3ps.bom.command.generalbom.GeneralBOMUpdateDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeLoader;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeRenderer;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.DefaultTreeTableCellRenderer;
import e3ps.bom.common.jtreetable.ExpandTreeNode;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.CheckSameNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class MainEditorPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    public JFrame desktop;
    private AbstractAIFUIApplication app;
    public BOMRegisterApplicationPanel parent = null;
    public BOMTreeTableModel model;
    public JTreeTable treeTable;
    public BOMAssyComponent rootComponent;

    Registry appReg;

    DBConnectionManager resource = null;
    Connection connection = null;

    protected JPopupMenu popupMenu;
    protected TreePath reloadPath;
    private JPanel tmpPanel;
    private JSplitPane splitPane;
    public TotalPropertyDialog dlg;
    public GeneralBOMUpdateDialog updateHeader;

    public Vector bomDataVec;
    private Vector allColumns;
    protected int reloadRow;
    protected int reloadCounter;

        class TreeTableMouseAdapter extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
//            if (e.getClickCount() == 2 && treeTable.getSelectedColumn() != 0)    {
            if( e.getClickCount() == 2 )    {
                showPartPropertyDlg(parent.isPublicView());
            }
        }

        public void mouseReleased(MouseEvent ev) {
            if (ev.isPopupTrigger())    {
                int x = ev.getX();
                int y = ev.getY();
                int p_y = MainEditorPanel.this.getHeight();

                TreePath path = treeTable.getTree().getPathForLocation(x, y);
                if (path != null) {
                    TreePath []selectedPaths = treeTable.getTree().getSelectionPaths();

                    if (selectedPaths == null) {
                        treeTable.getTree().setSelectionPath(path);
                    } else {
                        boolean equalFlag = false;
                        for(int i=0; i<selectedPaths.length; i++) {
                            if (selectedPaths[i] == path) {
                                equalFlag = true;
                                break;
                            }
                        }
                        if (equalFlag) {
                            treeTable.getTree().setSelectionPaths(selectedPaths);
                        } else {
                            treeTable.getTree().setSelectionPath(path);
                        }
                    }
                    if ( p_y - y < popupMenu.getHeight()) {
                        popupMenu.show(treeTable, x, y-popupMenu.getHeight());
                    } else {
                        popupMenu.show(treeTable, x, y);
                    }
                }
            }
        }
    }

    String string = "";

    public MainEditorPanel(JFrame frame) {
        this(null, null);
        this.desktop = frame;
    }

    ////////////////////////////////////////////////////////////////////////////
    // 생성자
    ////////////////////////////////////////////////////////////////////////////
    public MainEditorPanel(AbstractAIFUIApplication app, BOMRegisterApplicationPanel parent) {
        this.app = app;
        this.parent = parent;
        this.desktop = app.getDesktop();
        appReg = Registry.getRegistry(app);

        try    {
            rootComponent = new BOMAssyComponent("Empty", true);
            model = new BOMTreeTableModel(rootComponent);
            treeTable = new JTreeTable(model);

            jInit();

            reloadRow = -1;

            treeTable.getTree().setCellRenderer(new BOMTreeRenderer(app));
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    public void openBOMData(BOMAssyComponent rootComp, Vector bomData) {
        this.bomDataVec = bomData;

        //shin....
        BOMBasicInfoPool.setPublicModelName(rootComp.getItemCodeStr());

        BOMBasicInfoPool.setPublicTotalDataCount(bomData.size()+1);

        rootComponent = rootComp;
        model.getRootNode().setBOMComponent(rootComponent);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Thread(new BOMTreeLoader( model.getRootNode(), bomDataVec, treeTable, model)).start();
            }
        });
    }

    public JTreeTable getTreeTable() {
        return treeTable;
    }

    public BOMTreeTableModel getTreeTableModel()    {
        return model;
    }

    public BOMTreeNode [] getSelectedTreeNode() {
        Hashtable nodeHash = new Hashtable();
        boolean isExist = false;

        if (treeTable.getSelectedRowCount() < 1) {
            return null;
        }

        //선택된 Row의 node를 구해서 BOMComponent를 빼낸다.
        TreePath [] selectedPath = treeTable.getTree().getSelectionPaths();
        Vector selectedNodeV = new Vector();

        BOMTreeNode tempnode1 = null;
        BOMAssyComponent assy1 = null;
        for(int inx = 0; inx < selectedPath.length; inx++) {
            tempnode1 = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
            assy1 = tempnode1.getBOMComponent();
            nodeHash.put(assy1.getSortOrderStr(), assy1.toString());
        }

        TreeNode [] parentNodes = null;
        BOMAssyComponent assy2 = null;
        for(int i=0; i<selectedPath.length; i++) {
            isExist = false;
            BOMTreeNode tempnode2 = (BOMTreeNode)selectedPath[i].getLastPathComponent();
            parentNodes = tempnode2.getPath();
            for (int jnx = 1; jnx < (parentNodes.length - 1); jnx++) {
                assy2 = ((BOMTreeNode)parentNodes[jnx]).getBOMComponent();
                if (nodeHash.containsKey(assy2.getSortOrderStr())) {
                    isExist = true;
                    break;
                }
            }
            if (isExist)    {
                continue;
            }

            selectedNodeV.add(tempnode2);
        }

        BOMTreeNode []selectedNode = new BOMTreeNode[selectedNodeV.size()];
        for (int inx = 0; inx < selectedNode.length; inx++) {
            selectedNode[inx] = (BOMTreeNode)selectedNodeV.elementAt(inx);
        }
        return selectedNode;
    }

    public BOMTreeNode [] getSelectedNode() {
        if(treeTable.getTree().getSelectionPaths() == null)    {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow3"), "Error", MessageBox.WARNING);
            m.setVisible(true);
            m.setModal(true);
            return null;
        }

        TreePath [] selectedPath = treeTable.getTree().getSelectionPaths();

        BOMTreeNode []selectedNode = new BOMTreeNode[selectedPath.length];

        for(int inx = 0; inx < selectedPath.length; inx++) {
            selectedNode[inx] = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
        }
        return selectedNode;
    }

    public int getTotalDataCount() {
        return BOMBasicInfoPool.getPublicTotalDataCount();  // root node 또한 포함.
    }

    // 현재 열려있는 BOM Panel의 List를 Clear하고, Empty로 세팅 (체크인 포함)
    public void clearBOMList() {
        //add by shin...자동 체크인...
        setAutocheckin();

        bomDataVec = new Vector();
        BOMBasicInfoPool.setPublicTotalDataCount(0); // Root Node 포함.
        BOMBasicInfoPool.setWorkItemOid("");
        BOMBasicInfoPool.setPublicModelName("Empty");
        BOMBasicInfoPool.setCoWorkerVec(new Vector());
        BOMBasicInfoPool.setPublicOwnerId("");
        BOMBasicInfoPool.setPublicBOMStatus("None");
        BOMBasicInfoPool.setPublicMyStatus("1");
        BOMBasicInfoPool.setBomEcoNumber("");
        BOMBasicInfoPool.setBomEcoType("");

        BOMTreeNode rootNode = model.getRootNode();
        int childCount = rootNode.getChildCount();

        rootComponent = new BOMAssyComponent("Empty", true);
        rootNode.setBOMComponent(rootComponent);

        BOMTreeNode childNodes[] = new BOMTreeNode[childCount];

        for(int i=0; i<childCount; i++) {
            childNodes[i] = (BOMTreeNode)rootNode.getChildAt(i);
        }

        for(int i=0; i<childCount; i++) {
            rootNode.removeElement(childNodes[i]);
            childNodes[i] = null;
        }

        model.fireTreeChanged(rootNode);  // Event 발생
        treeTable.repaint();

       //add by shin.....데이타 뿐만 아니라 컬럼까지 초기화 한다.
        treeTable.setAutoCreateColumnsFromModel(true);
        treeTable.createDefaultColumnsFromModel();
        try
        {
            rejInit();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            treeTable.setAutoCreateColumnsFromModel(false);
        }
    }

    // 자동체크인 없이 BOM 에디터 화면을 Clear 한다
    public void clearBOMListNotCheckIn() {
        bomDataVec = new Vector();

        BOMTreeNode rootNode = model.getRootNode();
        int childCount = rootNode.getChildCount();

        rootComponent = new BOMAssyComponent("Empty", true);
        rootNode.setBOMComponent(rootComponent);

        BOMTreeNode childNodes[] = new BOMTreeNode[childCount];

        for(int i=0; i<childCount; i++) {
            childNodes[i] = (BOMTreeNode)rootNode.getChildAt(i);
        }

        for(int i=0; i<childCount; i++) {
            rootNode.removeElement(childNodes[i]);
            childNodes[i] = null;
        }

        model.fireTreeChanged(rootNode);  // Event 발생
        treeTable.repaint();

       //add by shin.....데이타 뿐만 아니라 컬럼까지 초기화 한다.
        treeTable.setAutoCreateColumnsFromModel(true);
        treeTable.createDefaultColumnsFromModel();
        try
        {
            rejInit();
        }
        catch( Exception e )
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            treeTable.setAutoCreateColumnsFromModel(false);
        }
    }

    private void setAutocheckin()
    {
        BOMRegisterApplicationPanel pnl;
        BOMTreeNode rootNode;

        try{
            BOMCheckOutInDao bcoi = new BOMCheckOutInDao();
            Vector vccom = new Vector();

            BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
            pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            rootNode = (BOMTreeNode)model.getRootNode();
            Enumeration rootEnum = rootNode.preorderEnumeration();

            BOMTreeNode treeNode = null;
            BOMAssyComponent bomComponent = null;
            while (rootEnum.hasMoreElements())
            {
                treeNode = (BOMTreeNode)rootEnum.nextElement();
                bomComponent = treeNode.getBOMComponent();
                vccom.add(bomComponent.getItemCodeStr());
                bcoi.setCheckIn( bomComponent.getItemCodeStr(), BOMBasicInfoPool.getUserId() );
            }

            boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false ;
            CheckInCmd ci = new CheckInCmd(desktop, app);
            ci.checkIn(vccom, bomGubunFlag);
        }catch(Exception e){

        }

    }

    // Table 에서 값을 선택한 후, Property 를 Editing 하기 위해서 Dialog 를 띄워준다.
    // 호출 : TreeTable에서 더블클릭하는 경우.
    // 호출 : 메뉴중 BOM 속성변경을 선택하는 경우.
    public void showPartPropertyDlg(boolean isView) {
        boolean isView_c = isView;

        // 선택된 Row 가 있는지 검사.
        if (treeTable.getSelectedRowCount() != 1) {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }
        boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

        //선택된 Row 의 node 를 구해서 BOMComponent를 빼낸다.
        BOMTreeNode selectedNode = (BOMTreeNode)treeTable.getTree().getSelectionPath().getLastPathComponent();

        BOMAssyComponent bomcomponent = selectedNode.getBOMComponent();

        // 선택된 노드의 부품 타입을 가져옴 (제품인지 금형인지)
        String strType = "";
        try {
            String strItemCode = bomcomponent.getItemCodeStr();
            WTPart part = KETPartHelper.service.getPart(strItemCode);
            strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        if (bomcomponent.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || bomcomponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE ) {

            BOMTreeNode [] parentsPathNode = selectedNode.getPathNode();
            BOMTreeNode parentNodeView = parentsPathNode[parentsPathNode.length -2];

            // 조회 모드가 아닌 경우 ( isView = false )
            if (!isView_c) {
                String userId = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";

                if(bomGubunFlag) { // 신규 BOM 수정
                    if (!parentNodeView.getBOMComponent().getNewFlagStr().equalsIgnoreCase("NEW") && !parentNodeView.getBOMComponent().getCheckOutStr().equalsIgnoreCase(userId))    {
                        isView_c = true;
                    } else if (!parentNodeView.getBOMComponent().getNewFlagStr().equalsIgnoreCase("NEW") && parentNodeView.getBOMComponent().getCheckOutStr().equalsIgnoreCase(userId)) {
                        isView_c = true;
                    } else if (parentNodeView.getBOMComponent().getNewFlagStr().equalsIgnoreCase("NEW") && !parentNodeView.getBOMComponent().getCheckOutStr().equalsIgnoreCase(userId)) {
                        isView_c = true;
                    }
                } else {                // 변경 BOM 수정
                    if (!parentNodeView.getBOMComponent().getCheckOutStr().equalsIgnoreCase(userId)) {
                        isView_c = true;
                    }
                }
            }

            boolean flag = false;

            if(isView_c) {
                int n = JOptionPane.showConfirmDialog(this, messageRegistry.getString("viewProperty"), "Information", JOptionPane.OK_OPTION);
                if( n != 0) {
                    return;
                }
                desktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                if (PartUtil.isProductType(strType)) { // 선택한 노드의 부품이 제품인 경우
                    dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, true, app, true);
                } else {                                                // 선택한 노드의 부품이 금형인 경우
                    dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, true, app, false);
                }
                flag = dlg.getOKvalue();
                desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                desktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                if (PartUtil.isProductType(strType)) { // 선택한 노드의 부품이 제품인 경우
                    dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, isView_c, app, true);
                } else {                                                // 선택한 노드의 부품이 금형인 경우
                    dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, isView_c, app, false);
                }
                flag = dlg.getOKvalue();
                desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            if(flag) {
                CheckSameNode check = new CheckSameNode(model.getRootNode().preorderEnumeration(), selectedNode.getBOMComponent());
                Vector sameNodeResult = check.getResultList();

                resource = DBConnectionManager.getInstance();
                connection = resource.getConnection(appReg.getString("plm"));
                BOMSaveDao saveDao = new BOMSaveDao();

                try    {
                    BOMTreeNode sameNode = null;

                    for (int i=0; i<sameNodeResult.size(); i++) {
                        sameNode = (BOMTreeNode)sameNodeResult.elementAt(i);
                        sameNode.setBOMComponent(dlg.getChangedAssyComponent().createNewComponent());

                        if(bomGubunFlag) {        // 신규 BOM 수정
                            saveDao.changeBomProperty(connection, dlg.getChangedAssyComponent());
                        }
                    }

                    model.fireTreeChanged(sameNode);
                    treeTable.repaint();

                    if (selectedNode == sameNode) {
                        TreePath treepath = new TreePath(sameNode.getPath());
                        try {
                            treeTable.getTree().fireTreeWillExpand(treepath);
                            treeTable.getTree().scrollPathToVisible(treepath);
                            treeTable.getTree().fireTreeExpanded(treepath);
                            treeTable.getTree().setSelectionPath(treepath);

                            treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                        } catch (Exception ex) {}
                    }
                    connection.commit();
                } catch (Exception ex) {
                    Kogger.error(getClass(), ex);

                    try {
                        connection.rollback();
                    } catch (Exception dbex){}

                    MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
                    m.setVisible(true);
                    m.setModal(true);
                } finally {
                    if(resource != null) {
                        resource.freeConnection(appReg.getString("plm"), connection);
                    }
                }
            }
        } else    { // 최상위 부품을 선택 한 경우  -> KETBomHeader 속성정보 수정 Dialog 팝업 호출

            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if (PartUtil.isProductType(strType)) { // 선택한 노드의 부품이 제품인 경우
                updateHeader = new GeneralBOMUpdateDialog(desktop, app, bomcomponent.getItemCodeStr(), isView_c);
            }
            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    // 신규 금형부품 등록 팝업 화면 오픈
    public void showPartPropertyInsertDlg(){

        BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();

        // 하나의 Row 만 선택하여 신규 부품 추가 가능함 (선택한 Row 하위에 신규 부품 생성 되므로)
        if (treeTable.getSelectedRowCount() == 1) {
            BOMPropertyInsertDialog dialog = new BOMPropertyInsertDialog(desktop, true, app, parent, rootComponent, treeTable);
            dialog.doLayout();
        } else {
            MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00225")/*선택한 항목의 하위에 신규 금형부품이 추가됩니다.\n하나의 항목을 선택해주십시오.*/, "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }
    }

    // Status Panel 에서 Tree 를 expand 할려는 경우.
    public void expandTreeTable(int level) {
        //선택된 Row 가 있는지 검사.
        if (treeTable.getSelectedRowCount() != 1) {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

        //선택된 Row 의 node 를 구해서 BOMComponent를 빼낸다.
        BOMTreeNode selectedNode = (BOMTreeNode)treeTable.getTree().getSelectionPath().getLastPathComponent();

        // Thread 로 구현.
        ExpandTreeNode expand = new ExpandTreeNode(selectedNode, level, treeTable);
        expand.start();
    }

    private void initColumnModel() {
        allColumns = new Vector();

        for(int i=4; i<treeTable.getColumnCount(); i++) {
            allColumns.addElement(treeTable.getColumn(treeTable.getColumnName(i)));
        }
    }

     private void jInit() throws Exception {
        try {
            treeTable.setShowGrid(false);
            treeTable.setBackground(new Color(240,240,240));

            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/).setPreferredWidth(200);        //Item Code
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/).setPreferredWidth(35);
            treeTable.getColumn("SEQ").setPreferredWidth(35);                //SEQ
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/).setPreferredWidth(35);                //Level
            treeTable.getColumn("Item Seq").setPreferredWidth(60);            //Item Seq
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/).setPreferredWidth(160);            //Description
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/).setPreferredWidth(45);                //Quantity
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/).setPreferredWidth(60);            //Unit
            treeTable.getColumn("Status").setPreferredWidth(10);                //Status(영문)
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/).setPreferredWidth(35);                //Version
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/).setPreferredWidth(60);            //Status(한글)
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/).setPreferredWidth(120);    //Substitute No
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/*삭제여부*/).setPreferredWidth(60);            //삭제여부
            treeTable.getColumn("ECO No").setPreferredWidth(50);            //ECO No
            treeTable.getColumn("Check-Out").setPreferredWidth(100);

            TableColumnModel columnModel = treeTable.getColumnModel();

            //shin... main list
            // Default 로 숨기는 컬럼 숨기기
            TableColumn column8 = columnModel.getColumn(8);
            column8.setWidth(0);
            column8.setMinWidth(0);
            column8.setMaxWidth(0);
            column8.setResizable(false);

            TableColumn column19 = columnModel.getColumn(19);
            column19.setWidth(0);
            column19.setMinWidth(0);
            column19.setMaxWidth(0);
            column19.setResizable(false);

            // 금형관련 컬럼 숨기기
            TableColumn column9 = columnModel.getColumn(9);
            column9.setWidth(0);
            column9.setMinWidth(0);
            column9.setMaxWidth(0);
            column9.setResizable(false);

            TableColumn column10 = columnModel.getColumn(10);
            column10.setWidth(0);
            column10.setMinWidth(0);
            column10.setMaxWidth(0);
            column10.setResizable(false);

            TableColumn column11 = columnModel.getColumn(11);
            column11.setWidth(0);
            column11.setMinWidth(0);
            column11.setMaxWidth(0);
            column11.setResizable(false);

            TableColumn column12 = columnModel.getColumn(12);
            column12.setWidth(0);
            column12.setMinWidth(0);
            column12.setMaxWidth(0);
            column12.setResizable(false);

            // TableColumn 객체를 hashtable 에 모두 저장한다.
            initColumnModel();

            // 마우스가 더블클릭 되는경우 Property 를 고칠수 있도록 한다.
            treeTable.addMouseListener(new TreeTableMouseAdapter());

            // Table Renderer 달아주기..
            for(int i=1; i<model.getColumnCount(); i++) {
                if (model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/)) {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.RIGHT);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
                else if (model.getColumnName(i).equalsIgnoreCase("Check-Out") || model.getColumnName(i).equalsIgnoreCase("SEQ") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/)
                        || model.getColumnName(i).equalsIgnoreCase("Item Seq") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/))
                {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.CENTER);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
                else
                {
                    treeTable.getColumnModel().getColumn(i).setCellRenderer (new DefaultTreeTableCellRenderer());
                }
            }

            treeTable.setPreferredScrollableViewportSize(new Dimension(2000, 200));
            treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Popup Menu 달기
            BOMRegisterApplicationMenuBar menuBar = (BOMRegisterApplicationMenuBar)app.getApplicationMenuBar();

            popupMenu = new JPopupMenu();

            popupMenu.add(menuBar.cutPopupMenu);
            popupMenu.add(menuBar.pastePopupMenu);

            popupMenu.add(menuBar.addPopupMenu);
            popupMenu.add(menuBar.replacePopupMenu);
            popupMenu.add(menuBar.copyChildPopupMenu);

            popupMenu.add(menuBar.removePopupMenu);
            popupMenu.add(menuBar.bomPropertyPopupMenu);
            popupMenu.add(menuBar.bomPropertyInsertPopupMenu);
            popupMenu.addSeparator();
            popupMenu.add(menuBar.drawingDetailsPopupMenu);
            popupMenu.add(menuBar.documentDetailsPopupMenu);
            popupMenu.add(menuBar.bomEcoDetailsPopupMenu);
            popupMenu.add(menuBar.pProjectDetailsPopupMenu);
            popupMenu.add(menuBar.mProjectDetailsPopupMenu);
            popupMenu.addSeparator();
            popupMenu.add(menuBar.searchAppliedProductPopupMenu);

            treeTable.getTree().add(popupMenu);

            JTableHeader header = treeTable.getTableHeader();
            header.setUpdateTableInRealTime(false);
            header.setBorder(BorderList.loweredBorder);

            JPanel tablePane = new JPanel();
            tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.Y_AXIS));
            tablePane.add(header);
            JScrollPane treeTableScroll = new JScrollPane(treeTable);
            treeTableScroll.getViewport().setBackground(Color.white);
            treeTableScroll.setBackground(Color.white);
            tablePane.add(treeTableScroll);
            tablePane.setBackground(Color.white);

            tmpPanel = new JPanel();
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setDividerSize(8);
            splitPane.setContinuousLayout(true);
            splitPane.setOneTouchExpandable(true);
            splitPane.setResizeWeight(1.0d);
            splitPane.setLeftComponent(tablePane);
            splitPane.setRightComponent(tmpPanel);

            this.setLayout(new BorderLayout(0, 0));
            this.add(splitPane, BorderLayout.CENTER);
            this.validate();
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

     private void rejInit() throws Exception {
        try {
            treeTable.setShowGrid(false);
            treeTable.setBackground(new Color(240,240,240));

            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/).setPreferredWidth(200);        //Item Code
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/).setPreferredWidth(35);
            treeTable.getColumn("SEQ").setPreferredWidth(35);                //SEQ
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/).setPreferredWidth(35);                //Level
            treeTable.getColumn("Item Seq").setPreferredWidth(60);            //Item Seq
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/).setPreferredWidth(160);            //Description
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/).setPreferredWidth(45);                //Quantity
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/).setPreferredWidth(60);            //Unit
            treeTable.getColumn("Status").setPreferredWidth(10);                //Status(영문)
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/).setPreferredWidth(35);                //Version
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/).setPreferredWidth(60);            //Status(한글)
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/).setPreferredWidth(120);    //Substitute No
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/*삭제여부*/).setPreferredWidth(60);            //삭제여부
            treeTable.getColumn("ECO No").setPreferredWidth(50);            //ECO No
            treeTable.getColumn("Check-Out").setPreferredWidth(100);

            // TableColumn 객체를 hashtable 에 모두 저장한다.
            initColumnModel();

            // Table Renderer 달아주기..
            for(int i=1; i<model.getColumnCount(); i++) {
                if (model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/)) {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.RIGHT);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
                else if (model.getColumnName(i).equalsIgnoreCase("Check-Out") || model.getColumnName(i).equalsIgnoreCase("SEQ") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/)
                        || model.getColumnName(i).equalsIgnoreCase("Item Seq") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/) || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/*삭제여부*/))
                {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.CENTER);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
                else
                {
                    treeTable.getColumnModel().getColumn(i).setCellRenderer (new DefaultTreeTableCellRenderer());
                }
            }

            treeTable.setPreferredScrollableViewportSize(new Dimension(2000, 200));
            treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            this.setLayout(new BorderLayout(0, 0));
            this.add(splitPane, BorderLayout.CENTER);
            this.validate();
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}
