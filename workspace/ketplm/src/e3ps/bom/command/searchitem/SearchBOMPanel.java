package e3ps.bom.command.searchitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
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
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomproperty.TotalPropertyDialog;
import e3ps.bom.command.comparebom.CompareFrame;
import e3ps.bom.command.copy.CopyOperation;
import e3ps.bom.command.documentdetails.DocumentDetailsCmd;
import e3ps.bom.command.drawingdetails.DrawingDetailsCmd;
import e3ps.bom.command.ecodetails.EcoDetailsCmd;
import e3ps.bom.command.mprojectdetails.MProjectDetailsCmd;
import e3ps.bom.command.pprojectdetails.PProjectDetailsCmd;
import e3ps.bom.command.saveexcel.SaveExcelDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeRenderer;
import e3ps.bom.common.jtreetable.BOMTreeSearchLoader;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.DefaultTreeTableCellRenderer;
import e3ps.bom.common.jtreetable.ExpandTreeNode;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class SearchBOMPanel extends AbstractAIFDialog
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame desktop;
    public AbstractAIFUIApplication app;

    public BOMRegisterApplicationPanel parent = null;
    protected BOMTreeTableModel model;
    protected JTreeTable treeTable;
    protected JPopupMenu popupMenu;
    protected JMenuItem copyPopupMenu;
    protected JMenuItem searchBomRelateEcoPopup;
    protected JMenuItem searchBomRelateDocPopup;
    protected JMenuItem searchBomRelateDwgPopup;
    protected JMenuItem searchBomRelatePPjtPopup;
    protected JMenuItem searchBomRelateMPjtPopup;

    private Registry appReg;

    private JLabel versionLbl, levelLbl;
    private JLabel qtyLbl, qtyLbl2, qtyValueLbl;
    private JLabel approveLbl, approveValueLbl;
    private JComboBox versionCmb, levelCmb, optionCmb;
    private JButton levelBtn, copyBtn, saveExcelBtn, compareBOMBtn, closeBtn, searchBtn;

    private JPanel tmpPanel;
    private JSplitPane splitPane;

    public TotalPropertyDialog propertyDlg;
    private BtnListener btnListener;

    public Vector bomDataVec;
    public BOMAssyComponent rootComponent;

    public String headItem = "";
    Vector<TableColumn> allColumns;
    String title = "";
    String titleStr = "";
    String rootItemCodeStr = "";
    String rootItemTypeStr = "";
    Vector versionVec = new Vector();
    String versionStr = "";
    String strRootPartType = "";

    private BOMSearchUtilDao dao = new BOMSearchUtilDao();

    class TreeTableMouseAdapter extends MouseAdapter
    {
        // 테이블 더블 클릭 시 수정화면 팝업 Display
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && treeTable.getSelectedColumn() != 0) {
                showPartPropertyDlg();
            }
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent ev) {
            if (ev.isPopupTrigger()) {
                int x = ev.getX();
                int y = ev.getY();
                int p_y = SearchBOMPanel.this.getHeight();

                TreePath path = treeTable.getTree().getPathForLocation(x, y);
                if (path != null)    {
                    TreePath []selectedPaths = treeTable.getTree().getSelectionPaths();

                    if (selectedPaths == null) {
                        treeTable.getTree().setSelectionPath(path);
                    } else {
                        boolean equalFlag = false;
                        for(int i=0; i<selectedPaths.length; i++)    {
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

                    if ( p_y - y < popupMenu.getHeight())
                        popupMenu.show(treeTable, x, y-popupMenu.getHeight());
                    else
                        popupMenu.show(treeTable, x, y);
                }
            }
        }
    }

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Copy")) {
                copy();
            } else if (e.getActionCommand().equals("Dwg")) {
                dwg_detailsBtn();
            } else if (e.getActionCommand().equals("Doc")) {
                doc_detailsBtn();
            } else if (e.getActionCommand().equals("Eco")) {
                eco_detailsBtn();
            } else if (e.getActionCommand().equals("PPjt")) {
                ppjt_detailsBtn();
            } else if (e.getActionCommand().equals("MPjt")) {
                mpjt_detailsBtn();
            } else if (e.getActionCommand().equals("Compare BOM")) {
                compareBOM();
            } else if (e.getActionCommand().equals("Save Excel")) {
                saveExcel();
            } else if (e.getActionCommand().equals("Close")) {
                close();
            } else if (e.getActionCommand().equals("Search")) {
               // displayVersionBOM();
            } else if (e.getActionCommand().equals("Level")) {
                try {
                    String levelStr = (String)levelCmb.getSelectedItem();
                    Integer levelInt = null;
                    if (levelStr.equalsIgnoreCase("All")) {
                        levelInt = new Integer(20);
                    } else {
                        levelInt = new Integer(levelStr);
                    }

                    int levelNum = levelInt.intValue();
                    if (levelNum <= 0)    {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("checkLevel"), "Error", MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                    } else    {
                        expandTreeTable(levelInt.intValue());
                    }
                } catch (Exception ex) {
                    MessageBox m = new MessageBox(desktop, messageRegistry.getString("checkLevel1"), "Error", MessageBox.ERROR);
                    m.setVisible(true);
                    m.setModal(true);
                }
            }
        }
    }

    public SearchBOMPanel(JFrame frame, AbstractAIFUIApplication app, BOMRegisterApplicationPanel parent, String titleStr, BOMAssyComponent rootComp, Vector bomData, String versionStr)
    {
        //super(frame,  true);
        this.app = app;
        this.parent = parent;
        this.desktop = frame;
        title = titleStr;

Kogger.debug(getClass(), "===>> versionStr : " + versionStr);

        if (versionStr.indexOf(".") > 0) {
            this.versionStr = versionStr.substring(0, versionStr.indexOf("."));
        } else {
            this.versionStr = versionStr;
        }

        setTitle(title);

        rootItemCodeStr = rootComp.toString();
        rootItemTypeStr = rootComp.getIBAPartType();        // 부품 타입(제품, 금형 구분)
Kogger.debug(getClass(), "@@@@@ rootItemTypeStr : " + rootItemTypeStr);

        appReg = Registry.getRegistry(app);

        setVersionVec(rootItemCodeStr);

        BOMBasicInfoPool.setBomSearchGubun(titleStr);
        BOMBasicInfoPool.setBomComponent(rootComp);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        try {
            rootComponent = new BOMAssyComponent("Search Results", true);
            model = new BOMTreeTableModel(rootComponent);
            treeTable = new JTreeTable(model);

            treeTable.getTree().setCellRenderer(new BOMTreeRenderer(app));

            long temptime3 = System.currentTimeMillis();
            openBOMData(rootComp, bomData);
            long temptime4 = System.currentTimeMillis();
Kogger.debug(getClass(), " BOM Display1 : " + (temptime4 - temptime3) + " ms");

//shin...제품, 금형에 따라 컬럼변경.
headItem = String.valueOf(rootComp);

            jInit();
            setBoxQuantity( versionCmb.getSelectedItem().toString() );
            setEcoApprovalDate( versionCmb.getSelectedItem().toString() );
            setSize(1000,750);
            setVisible(true);
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    public void openBOMData(BOMAssyComponent rootComp, Vector bomData)
    {
        try {
            this.bomDataVec = bomData;
            rootComponent = rootComp;
Kogger.debug(getClass(), "@@@@@ rootComp : " + rootComp);
Kogger.debug(getClass(), "@@@@@ bomData : " + bomData);

            model.getRootNode().setBOMComponent(rootComponent);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Thread(new BOMTreeSearchLoader(model.getRootNode(), bomDataVec, treeTable, model)).start();
                }
            });
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    public JTreeTable getTreeTable()
    {
        return treeTable;
    }

    public BOMTreeTableModel getTreeTableModel()
    {
        return model;
    }

    public BOMTreeNode [] getSelectedTreeNode()
    {
        Hashtable<String, String> nodeHash = new Hashtable<String, String>();
        boolean isExist = false;

        if (treeTable.getSelectedRowCount() < 1) {
            return null;
        }

        //선택된 Row 의 node 를 구해서 BOMComponent 를 빼낸다.
        TreePath [] selectedPath = treeTable.getTree().getSelectionPaths();
        Vector<BOMTreeNode> selectedNodeVec = new Vector<BOMTreeNode>();

        for(int inx = 0; inx < selectedPath.length; inx++) {
            BOMTreeNode tempnode1 = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
            BOMAssyComponent assy1 = tempnode1.getBOMComponent();
            nodeHash.put(assy1.getSortOrderStr(), assy1.toString());
        }

        for(int i=0; i<selectedPath.length; i++) {
            isExist = false;
            BOMTreeNode tempnode2 = (BOMTreeNode)selectedPath[i].getLastPathComponent();

            // 선택된 item 의 parent 중 하나라도 이미 선택되어 있다면(Hash Table 에 존재한다면) 중복되어 선택될 필요가 없으므로 vector 에 입력하지 않는다.
            TreeNode [] parentNodes = tempnode2.getPath();
            for (int jnx = 1; jnx < (parentNodes.length - 1); jnx++) {
                BOMAssyComponent assy2 = ((BOMTreeNode)parentNodes[jnx]).getBOMComponent();
                if (nodeHash.containsKey(assy2.getSortOrderStr())) {
                    isExist = true;
                    break;
                }
            }

            if (isExist)    continue;

            selectedNodeVec.add(tempnode2);
        }

        BOMTreeNode []selectedNode = new BOMTreeNode[selectedNodeVec.size()];
        for (int inx = 0; inx < selectedNode.length; inx++)
            selectedNode[inx] = (BOMTreeNode)selectedNodeVec.elementAt(inx);

        return selectedNode;
    }

    public int getTotalDataCount()
    {
        return BOMBasicInfoPool.getPublicTotalDataCount();
    }

    // Table 에서 값을 선택한 후, Property 를 Editing 하기 위해서 Dialog 를 띄워준다.
    public void showPartPropertyDlg()
    {
        if (treeTable.getSelectedRowCount() != 1)
        {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
            m.setModal(true);
            m.setVisible(true);
            return;
        }

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

        if (bomcomponent.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || bomcomponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE )
        {
            int n = JOptionPane.showConfirmDialog(this, messageRegistry.getString("viewProperty"), "Information", JOptionPane.OK_OPTION);
            if( n != 0)
            {
                return;
            }

            if (PartUtil.isProductType(strType)) { // 선택한 노드의 부품이 제품인 경우
                propertyDlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, true, app, true);
            } else {                                                // 선택한 노드의 부품이 금형인 경우
                propertyDlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, desktop, true, model, true, app, false);
            }
        }
        else
        {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectItem"), "Warning", MessageBox.WARNING);
            m.setModal(true);
            m.setVisible(true);
        }
    }

    private void initColumnModel()
    {
        allColumns = new Vector<TableColumn>();

        for(int i=4; i<treeTable.getColumnCount(); i++) {
            allColumns.addElement(treeTable.getColumn(treeTable.getColumnName(i)));
        }
    }

    private void jInit() throws Exception
    {
        try {
            btnListener = new BtnListener();

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setBackground(ColorList.veryLightBora);

            versionLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/ + " : ");
            versionLbl.setFont(FontList.shotKeyFont);
            topPanel.add(versionLbl);

            versionCmb = new JComboBox(versionVec);
            versionCmb.setFont(FontList.shotKeyFont);
            versionCmb.setBackground(Color.white);
//            versionCmb.setSelectedItem(versionStr);
            versionCmb.setSelectedItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/);        // 무조건 초기화면에서는 '최신' 상태가 선택되로록 함
            topPanel.add(versionCmb);

            versionCmb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Kogger.debug(getClass(), "----------->>>>>>>>>>>>>>>>> version selected");
                    displayVersionBOM();
                }});

            levelLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/ + " : ");
            levelLbl.setFont(FontList.shotKeyFont);
            topPanel.add(levelLbl);

            String levelObj[] = {"All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
            levelCmb = new JComboBox(levelObj);
            levelCmb.setBackground(Color.white);
            levelCmb.setFont(FontList.shotKeyFont);
            topPanel.add(levelCmb);

            levelBtn = new JButton(appReg.getImageIcon("levelviewIcon"));
            levelBtn.setMargin(new Insets(0,0,0,0));
            levelBtn.setToolTipText("Level Expand");
            levelBtn.setActionCommand("Level");
            levelBtn.setOpaque(false);
            levelBtn.setBorder(BorderList.emptyBorder1);
            levelBtn.addActionListener(btnListener);
            topPanel.add(levelBtn);

            qtyLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00345")/*포장수량*/ + " : ");
            qtyLbl.setFont(FontList.shotKeyFont);

            qtyLbl2 = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ");
            qtyLbl2.setFont(FontList.shotKeyFont);

            qtyValueLbl = new JLabel();
            qtyValueLbl.setFont(FontList.shotKeyFont);
            qtyValueLbl.setText("None");

            topPanel.add(qtyLbl);
            topPanel.add(qtyLbl2);
            topPanel.add(qtyValueLbl);

            approveLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00165")/*변경일자*/ + " : ");
            approveLbl.setFont(FontList.shotKeyFont);

            approveValueLbl = new JLabel();
            approveValueLbl.setFont(FontList.shotKeyFont);
            approveValueLbl.setText("None");

            topPanel.add(approveLbl);
            topPanel.add(approveValueLbl);

            treeTable.setShowGrid(false);
            treeTable.setBackground(new Color(240,240,240));

            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/).setPreferredWidth(200);        // 부품 번호
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/).setPreferredWidth(35);                // 구분(신규,기존)
            treeTable.getColumn("SEQ").setPreferredWidth(35);                // SEQ
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/).setPreferredWidth(35);                // Level
            treeTable.getColumn("Item Seq").setPreferredWidth(60);            // Item Seq
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/).setPreferredWidth(160);            // 부품명
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/).setPreferredWidth(45);                // 수량
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/).setPreferredWidth(60);            // 기본단위
            treeTable.getColumn("Status").setPreferredWidth(10);                // Status
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/).setPreferredWidth(35);                // 버전
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/).setPreferredWidth(60);            // 결재상태
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/).setPreferredWidth(120);    // 대체부품번호
            treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/*삭제여부*/).setPreferredWidth(60);            // 삭제여부
            treeTable.getColumn("ECO No").setPreferredWidth(50);            // ECO No
            treeTable.getColumn("Check-Out").setPreferredWidth(100);        // Check-Out

            TableColumnModel columnModel = treeTable.getColumnModel();
            TableColumn column = null;

//            column = columnModel.getColumn(2);        // SEQ 왜 주석처리했니?
//            column.setWidth(0);
//            column.setMinWidth(0);
//            column.setMaxWidth(0);
//            column.setResizable(false);

            column = columnModel.getColumn(18);        // Check-Out
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            column = columnModel.getColumn(8);        // Status
            column.setWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setResizable(false);

            column = columnModel.getColumn(19);  //new flag
              column.setWidth(0);
              column.setMinWidth(0);
              column.setMaxWidth(0);
              column.setResizable(false);

            KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
                try {
                    WTPart part = kh.searchItem(headItem);
                    strRootPartType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);  //BOMFlag
                } catch (Exception e) {
                    Kogger.error(getClass(), e);
                }
                KetMainJTreeTable km = new KetMainJTreeTable();
                if (PartUtil.isProductType(strRootPartType) )            // 제품인 경우
                {
                    // 제품관련 컬럼 열기
                    column = columnModel.getColumn(15);
                      column.setMinWidth(0);
                      column.setMaxWidth(180);
                      column.setResizable(true);
                      column.setPreferredWidth(120);

                      // 금형관련 칼럼 닫음.
                      column = columnModel.getColumn(9);
                      column.setWidth(0);
                      column.setMinWidth(0);
                      column.setMaxWidth(0);
                      column.setResizable(false);

                      column = columnModel.getColumn(10);
                      column.setWidth(0);
                      column.setMinWidth(0);
                      column.setMaxWidth(0);
                      column.setResizable(false);

                      column = columnModel.getColumn(11);
                      column.setWidth(0);
                      column.setMinWidth(0);
                      column.setMaxWidth(0);
                      column.setResizable(false);

                      column = columnModel.getColumn(12);
                      column.setWidth(0);
                      column.setMinWidth(0);
                      column.setMaxWidth(0);
                      column.setResizable(false);

                }else{                                // 금형인 경우
                    // 제품관련 컬럼 닫기
                    column = columnModel.getColumn(15);
                    column.setWidth(0);
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setResizable(false);

                    // 금형관련 칼럼 열어줌.
                    column = columnModel.getColumn(9);
                    column.setMinWidth(0);
                    column.setMaxWidth(150);
                    column.setResizable(true);
                    column.setPreferredWidth(45);

                    column = columnModel.getColumn(10);
                    column.setMinWidth(0);
                    column.setMaxWidth(150);
                    column.setResizable(true);
                    column.setPreferredWidth(65);

                    column = columnModel.getColumn(11);
                    column.setMinWidth(0);
                    column.setMaxWidth(150);
                    column.setResizable(true);
                    column.setPreferredWidth(55);

                    column = columnModel.getColumn(12);
                    column.setMinWidth(0);
                    column.setMaxWidth(150);
                    column.setResizable(true);
                    column.setPreferredWidth(60);

                    // [2011-03-04] 임승영D 요구사항 반영 :: 금형인 경우 ItemSeq 컬럼 숨기기
                    column = columnModel.getColumn(4);
                    column.setWidth(0);
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setResizable(false);
                }

            // TableColumn 객체를 Hash table 에 모두 저장한다.
            initColumnModel();

            // 마우스가 더블클릭 되는경우 Property 를 고칠수 있도록 한다.
            treeTable.addMouseListener(new TreeTableMouseAdapter());

            // Table Renderer 달아주기..
            for(int i=1; i<model.getColumnCount(); i++) {
                if (model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/)) {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.RIGHT);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                } else if (model.getColumnName(i).equalsIgnoreCase("SEQ") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/) ||
                           model.getColumnName(i).equalsIgnoreCase("Item Seq") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/) ||
                           model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/) || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/) ||
                           model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/*삭제여부*/))    {
                    DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.CENTER);
                    treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
                } else    {
                    treeTable.getColumnModel().getColumn(i).setCellRenderer (new DefaultTreeTableCellRenderer());
                }
            }

            treeTable.setPreferredScrollableViewportSize(new Dimension(2050, 200));
            treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // 오른쪽 마우스 PopUp Menu 달기
            popupMenu = new JPopupMenu();

            copyPopupMenu = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00167")/*복사*/, appReg.getImageIcon("copyIcon"));
            copyPopupMenu.setFont(FontList.defaultFont);
            copyPopupMenu.setActionCommand("Copy");
            copyPopupMenu.addActionListener(btnListener);

            searchBomRelateDwgPopup = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00093")/*관련 도면 조회*/, appReg.getImageIcon("drawingdetailsIcon"));
            searchBomRelateDwgPopup.setFont(FontList.defaultFont);
            searchBomRelateDwgPopup.setActionCommand("Dwg");
            searchBomRelateDwgPopup.addActionListener(btnListener);

            searchBomRelateDocPopup = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00094")/*관련 문서 조회*/, appReg.getImageIcon("documentdetailsIcon"));
            searchBomRelateDocPopup.setFont(FontList.defaultFont);
            searchBomRelateDocPopup.setActionCommand("Doc");
            searchBomRelateDocPopup.addActionListener(btnListener);

            searchBomRelateEcoPopup = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00095")/*관련 설계변경 조회*/, appReg.getImageIcon("bomecodetailsIcon"));
            searchBomRelateEcoPopup.setFont(FontList.defaultFont);
            searchBomRelateEcoPopup.setActionCommand("Eco");
            searchBomRelateEcoPopup.addActionListener(btnListener);

            searchBomRelatePPjtPopup = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00096")/*관련 제품Profile 조회*/, appReg.getImageIcon("documentdetailsIcon"));
            searchBomRelatePPjtPopup.setFont(FontList.defaultFont);
            searchBomRelatePPjtPopup.setActionCommand("PPjt");
            searchBomRelatePPjtPopup.addActionListener(btnListener);

            searchBomRelateMPjtPopup = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00092")/*관련 금형Profile 조회*/, appReg.getImageIcon("documentdetailsIcon"));
            searchBomRelateMPjtPopup.setFont(FontList.defaultFont);
            searchBomRelateMPjtPopup.setActionCommand("MPjt");
            searchBomRelateMPjtPopup.addActionListener(btnListener);

            if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/))
            {
                popupMenu.add(copyPopupMenu);
                popupMenu.addSeparator();
                popupMenu.add(searchBomRelateDocPopup);
                popupMenu.add(searchBomRelateDwgPopup);
                popupMenu.add(searchBomRelateEcoPopup);
                popupMenu.add(searchBomRelatePPjtPopup);
                popupMenu.add(searchBomRelateMPjtPopup);

                if (PartUtil.isProductType(strRootPartType)) {
                    searchBomRelateMPjtPopup.setEnabled(false);
                } else {
                    searchBomRelatePPjtPopup.setEnabled(false);
                }

                treeTable.getTree().add(popupMenu);
            }

            // Tree Table Header 정의
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

            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                copyBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00167")/*복사*/, appReg.getImageIcon("copyIcon"));
                copyBtn.setFont(FontList.defaultFont);
                copyBtn.setActionCommand("Copy");
                copyBtn.addActionListener(btnListener);
                copyBtn.setMargin(new Insets(0,5,0,5));
                btnFlowPanel.add(copyBtn);

                compareBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00027")/*BOM 비교*/, appReg.getImageIcon("comparebomIcon"));
                compareBOMBtn.setFont(FontList.defaultFont);
                compareBOMBtn.setActionCommand("Compare BOM");
                compareBOMBtn.addActionListener(btnListener);
                compareBOMBtn.setMargin(new Insets(0,5,0,5));
                btnFlowPanel.add(compareBOMBtn);
            }

            saveExcelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00264")/*엑셀 저장*/, appReg.getImageIcon("saveexcelIcon"));
            saveExcelBtn.setFont(FontList.defaultFont);
            saveExcelBtn.setActionCommand("Save Excel");
            saveExcelBtn.addActionListener(btnListener);
            saveExcelBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(saveExcelBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(closeBtn);

            this.getContentPane().add(topPanel, BorderLayout.NORTH);
            this.getContentPane().add(splitPane, BorderLayout.CENTER);
            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // TODO BOM 버전별 일자 Display (변경이 발생된 경우에만 보여주며, ECO 객체의 승인일자를 보여준다)
    public void setEcoApprovalDate( String strVersion )
    {
        String strDate = "";

        try {
            // '최신' 버전이 선택된 경우에는 해당 부품의 최신 버전 정보를 가져옴
            if ( strVersion != null && strVersion.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/) )
            {
                strVersion = dao.getLatestEcoVersionApproval( rootItemCodeStr, rootItemTypeStr );
                // ECO 정보가 없는 경우는 설변이 한번도 이루어지지 않은 것이므로, 가장 최신 버전은 0이다
                if ( strVersion != null && strVersion.equals("") )
                {
                    strVersion = "0";
                }
            }

            // 해당 부품, 버전의 ECO 객체 가져오기
            String strEcoHeaderNo = dao.getEcoHeaderNoAtVersion( rootItemCodeStr, strVersion );

            if ( strEcoHeaderNo != null && !strEcoHeaderNo.equals("") )
            {
                if ( PartUtil.isProductType(rootItemTypeStr) )
                {
                    KETProdChangeOrder prodOrder = (KETProdChangeOrder) EcmSearchHelper.manager.getEcoObject( strEcoHeaderNo );
                    strDate = WorkflowSearchHelper.manager.getLastApprovalDate( prodOrder );
                }
                else
                {
                    KETMoldChangeOrder moldOrder = (KETMoldChangeOrder) EcmSearchHelper.manager.getEcoObject( strEcoHeaderNo );
                    strDate = WorkflowSearchHelper.manager.getLastApprovalDate( moldOrder );
                }

                // 해당 ECO 객체의 승인일자 셋팅
                if ( strDate != null && !strDate.equals("") )
                {
                    if ( strDate.length() > 10 )
                    {
                        strDate = strDate.substring(0, 10);
                    }
                    approveValueLbl.setText(strDate);
                }
                else
                {
                    approveValueLbl.setText("None");
                }
            }
            else
            {
                approveValueLbl.setText("None");
            }

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // TODO 포장수량 정보 Display :: 정전개,제품인 경우에만 Display
    public void setBoxQuantity( String strVersion )
    {
        String strTemp = "";
        boolean isTop = false;

        // '최신' 버전이 선택된 경우에는 해당 부품의 최신 버전 정보를 가져옴
        if ( strVersion != null && strVersion.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/) )
        {
            strVersion = dao.getLatestEcoVersionApproval( rootItemCodeStr, rootItemTypeStr );
            // ECO 정보가 없는 경우는 설변이 한번도 이루어지지 않은 것이므로, 가장 최신 버전은 0이다
            if ( strVersion != null && strVersion.equals("") )
            {
                strVersion = "0";
            }
        }

        if ( (rootItemTypeStr != null && !rootItemTypeStr.equals("") && PartUtil.isProductType(rootItemTypeStr)) && (title != null && title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) )
        {
            // 부품번호가 완제품인지 여부 확인
            if ( rootItemCodeStr != null && !rootItemCodeStr.equals("") )
            {
                strTemp = rootItemCodeStr.substring(0, 1);
                if ( strTemp.equalsIgnoreCase("0") || strTemp.equalsIgnoreCase("1") || strTemp.equalsIgnoreCase("2") || strTemp.equalsIgnoreCase("3") ||
                     strTemp.equalsIgnoreCase("4") || strTemp.equalsIgnoreCase("5") || strTemp.equalsIgnoreCase("6") || strTemp.equalsIgnoreCase("7") ||
                     strTemp.equalsIgnoreCase("8") || strTemp.equalsIgnoreCase("9") || strTemp.equalsIgnoreCase("K") )
                {
                    isTop = true;
                }
            }

            if ( isTop )
            {
                qtyLbl.setVisible(true);        // 포장수량
                qtyLbl2.setVisible(false);    // 기준수량
                qtyValueLbl.setVisible(true);
            }
            else {
                qtyLbl.setVisible(false);
                qtyLbl2.setVisible(true);
                qtyValueLbl.setVisible(true);
            }

            String strIda2a2 = dao.getPartOidAtVersion( rootItemCodeStr, strVersion );        // 선택된 버전에 해당하는 부품의 ida2a2 값 가져오기
            String strBoxQty = dao.getBoxQuantityPartUsageLinkAtVersion( rootItemCodeStr, strIda2a2 );        // 해당 버전의 BoxQuantity 값 PartUsageLink에서 가져오기
            Double boxQuantityDbl = new Double( strBoxQty );
            if (boxQuantityDbl != null) {
                qtyValueLbl.setText(boxQuantityDbl + "");
            } else {
                qtyValueLbl.setText("None");
            }
        }
        else
        {
            qtyLbl.setVisible(false);
            qtyLbl2.setVisible(false);
            qtyValueLbl.setVisible(false);
        }
    }

    public void expandTreeTable(int level)
    {
        //선택된 Row 가 있는지 검사.
        if (treeTable.getSelectedRowCount() != 1) {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
            m.setModal(true);
            m.setVisible(true);

            return;
        }

        //선택된 Row 의 node 를 구해서 BOMComponent를 빼낸다.
        BOMTreeNode selectedNode = (BOMTreeNode)treeTable.getTree().getSelectionPath().getLastPathComponent();

        // Thread 로 구현.
        ExpandTreeNode expand = new ExpandTreeNode(selectedNode, level, treeTable);
        expand.start();
    }

    private void displayVersionBOM()
    {
        clearBOMList();

        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

Kogger.debug(getClass(), ">>> versionStr : " + versionStr);
Kogger.debug(getClass(), ">>> versionCmb.getSelectedItem().toString() : " + versionCmb.getSelectedItem().toString());

            String strSelected = versionCmb.getSelectedItem().toString();

            if(BOMBasicInfoPool.getBomSearchGubun().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {

                if( versionCmb.getSelectedItem().toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/) )
                {
                    copyBtn.setVisible(true);
                    copyPopupMenu.setVisible(true);
                    searchBomRelateEcoPopup.setVisible(true);
                    searchBomRelateDocPopup.setVisible(true);
                    searchBomRelateDwgPopup.setVisible(true);
                    if (PartUtil.isProductType(strRootPartType)) {
                        searchBomRelateMPjtPopup.setEnabled(false);
                    } else {
                        searchBomRelatePPjtPopup.setEnabled(false);
                    }
                } else    {
                    copyBtn.setVisible(false);
                    copyPopupMenu.setVisible(false);
                    searchBomRelateEcoPopup.setVisible(false);
                    searchBomRelateDocPopup.setVisible(false);
                    searchBomRelateDwgPopup.setVisible(false);
                    searchBomRelatePPjtPopup.setVisible(false);
                    searchBomRelateMPjtPopup.setVisible(false);
                    popupMenu.setVisible(false);
                }

                Vector vecOpenBom = new Vector();
                vecOpenBom.removeAllElements();

                BOMSearchDao dao = new BOMSearchDao();

                // 최신을 선택한 경우에는 자부품을 포함 한 모든 최신버전 상태를 보여준다.
                if ( strSelected != null && strSelected.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/) ) {
                    dao.downwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString(), "");
                } else {
                    //dao.downwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString(), "", versionCmb.getSelectedItem().toString(), optionStr);
                    dao.downwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString(), "", versionCmb.getSelectedItem().toString());
                }
                vecOpenBom = dao.getResultListVec();
                openBOMData(BOMBasicInfoPool.getBomComponent(), vecOpenBom);

                setBoxQuantity( versionCmb.getSelectedItem().toString() );
                setEcoApprovalDate( versionCmb.getSelectedItem().toString() );

            } else {
                Vector vecOpenBom = new Vector();
                vecOpenBom.removeAllElements();

                BOMSearchDao dao = new BOMSearchDao();

                // 최신을 선택한 경우에는 해당 자부품을 포함 한 모든 최신버전 상태를 보여준다.
                if ( strSelected != null && strSelected.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/) ) {
                    dao.upwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString());
                } else {
                    //dao.upwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString(), versionCmb.getSelectedItem().toString(), optionStr);
                    dao.upwardExplosionCurrentBom(BOMBasicInfoPool.getBomComponent().toString(), versionCmb.getSelectedItem().toString());
                }

                vecOpenBom = dao.getResultListVec();
                openBOMData(BOMBasicInfoPool.getBomComponent(), vecOpenBom);

                setEcoApprovalDate( versionCmb.getSelectedItem().toString() );
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch(Exception ex) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

    private void copy()
    {
        try {
            BOMTreeNode[] nodes = getSelectedTreeNode();

            int firstLevel = (((nodes[0]).getBOMComponent()).getLevelInt()).intValue();
            for (int inx = 1; inx < nodes.length; inx++) {
                BOMAssyComponent bomassy = (nodes[inx]).getBOMComponent();
                if ((bomassy.getLevelInt()).intValue() < firstLevel) {
                    MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("checkLevel2"), "Warning", MessageBox.WARNING);
                    messagebox.setModal(true);
                    messagebox.setVisible(true);
                    return;
                }
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CopyOperation op = new CopyOperation(app, nodes, "BOMSearch");
            op.executeOperation();
            close();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    // 관련도면 클릭 시
    public void dwg_detailsBtn() {
        try {
            DrawingDetailsCmd dwgDetails = new DrawingDetailsCmd(desktop, app, this);
            dwgDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 관련문서 클릭 시
    public void doc_detailsBtn() {
        try {
            DocumentDetailsCmd docDetails = new DocumentDetailsCmd(desktop, app, this);
            docDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 관련 ECO 클릭 시
    public void eco_detailsBtn() {
        try {
            EcoDetailsCmd ecoDetails = new EcoDetailsCmd(desktop, app, this);
            ecoDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 관련 제품 Profile 클릭 시
    public void ppjt_detailsBtn() {
        try {
            PProjectDetailsCmd ppjtDetails = new PProjectDetailsCmd(desktop, app, this);
            ppjtDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 관련 금형 Profile 클릭 시
    public void mpjt_detailsBtn() {
        try {
            MProjectDetailsCmd mpjtDetails = new MProjectDetailsCmd(desktop, app, this);
            mpjtDetails.run();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // BOM 비교
    private void compareBOM()
    {
        try {
            CompareFrame fr = new CompareFrame(app, "BOMSearch", this);
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void saveExcel()
    {
        try {
            SaveExcelDialog dlg = new SaveExcelDialog(desktop, app, model.getRootNode());
        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void setVersionVec(String rootItemCode)
    {
        DBConnectionManager resource = null;
        Connection connection = null;
        try {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.getItemVersionList( connection, rootItemCode, rootItemTypeStr );

            versionVec = dao.getResultListVec();
            versionVec.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00337")/*최신*/);
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        } finally {
            if(resource != null)    {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
    }

    public void clearBOMList()
    {
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
    }

    private void close()
    {
        BOMBasicInfoPool.setBomSearchGubun("");
        BOMBasicInfoPool.setBomComponent(null);
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    public JSplitPane getSplitPane()
    {
        return splitPane;
    }

    public String getSelectedPanel()
    {
        return "BOMSearch";
    }
}
