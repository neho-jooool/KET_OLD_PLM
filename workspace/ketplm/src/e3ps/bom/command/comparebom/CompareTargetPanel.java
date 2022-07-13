package e3ps.bom.command.comparebom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import wt.part.WTPart;
import e3ps.bom.command.bomproperty.TotalPropertyDialog;
import e3ps.bom.command.finditem.FindItemDialog;
import e3ps.bom.common.clipboard.ClipBoardPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeRenderer;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.CommonAssyTreeLoader;
import e3ps.bom.common.jtreetable.DefaultTreeTableCellRenderer;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class CompareTargetPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private CompareFrame parentFrame;
    protected BOMTreeTableModel model;
    protected JTreeTable treeTable;
    private Hashtable displayedColumns;
    private Vector allColumns;
    private Vector bomDataVec;
    private JPopupMenu popupMenu;
    private JPopupMenu tHeaderPopupMenu;
    private JMenuItem findItemMenu, copyMenu, propertyMenu;

    private BOMTreeNode rootNode;
    private BOMAssyComponent rootComponent;

    private AbstractAIFUIApplication app;
    private Registry registry;

    private int levels_to_explode = 20;
    private int mouse_x, mouse_y;

    public CompareTargetPanel(CompareFrame parentFrame)
    {
        super();

        this.parentFrame = parentFrame;
        app = parentFrame.getApplication();
        registry = Registry.getRegistry(app);
        this.rootNode = rootNode;

        try
        {
            setGUIInitialData();
            setGUI();
            setEvent();
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void setGUIInitialData()
    {
        findItemMenu = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00025")/*BOM 내 부품찾기*/, registry.getImageIcon("finditemIcon"));
        copyMenu = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00167")/*복사*/, registry.getImageIcon("copyIcon"));
        propertyMenu = new JMenuItem(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00029")/*BOM 속성변경*/, registry.getImageIcon("bompropertyIcon"));

        findItemMenu.setActionCommand("find");
        copyMenu.setActionCommand("copy");
        propertyMenu.setActionCommand("viewproperty");

        popupMenu = new JPopupMenu();
        popupMenu.add(findItemMenu);
        popupMenu.addSeparator();
        popupMenu.add(copyMenu);
        popupMenu.addSeparator();
        popupMenu.add(propertyMenu);
    }

    private void setGUI()
    {
Kogger.debug(getClass(), "---------------------------------------------------------------------->>>> setGUI()");
        rootComponent = new BOMAssyComponent("Empty", true);
        model = new BOMTreeTableModel(rootComponent);
        treeTable = new JTreeTable(model);

        treeTable.getTree().setCellRenderer(new BOMTreeRenderer(app));

        // 모양 구현.
        treeTable.setShowGrid(false);
        treeTable.setBackground(new Color(240, 240, 240));
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/).setPreferredWidth(200);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/).setPreferredWidth(35);
        treeTable.getColumn("SEQ").setPreferredWidth(35);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/).setPreferredWidth(35);
        treeTable.getColumn("Item Seq").setPreferredWidth(60);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/).setPreferredWidth(160);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/).setPreferredWidth(45);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/).setPreferredWidth(60);
        treeTable.getColumn("Status").setPreferredWidth(1);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/).setPreferredWidth(70);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/).setPreferredWidth(70);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/).setPreferredWidth(70);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/).setPreferredWidth(70);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/).setPreferredWidth(70);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/).setPreferredWidth(60);
        treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/).setPreferredWidth(100);
        treeTable.getColumn("ECO No").setPreferredWidth(70);
        treeTable.getColumn("Check-Out").setPreferredWidth(100);
        treeTable.getColumn("New Flag").setPreferredWidth(70);

        TableColumnModel columnModel = treeTable.getColumnModel();
        TableColumn column = null;
        column = columnModel.getColumn(18);        // Check-Out
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        initColumnModel();

        treeTable.addMouseListener(new TreeTableMouseAdapter());
        treeTable.getTree().add(popupMenu);

        for(int i=1; i<model.getColumnCount(); i++)
        {
            if (model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
            {
                DefaultTreeTableCellRenderer renderer = new DefaultTreeTableCellRenderer();
                renderer.setHorizontalAlignment(JLabel.RIGHT);
                treeTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
            else if (model.getColumnName(i).equalsIgnoreCase("SEQ") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/) ||
                     model.getColumnName(i).equalsIgnoreCase("Item Seq") || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/) ||
                       model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/) || model.getColumnName(i).equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/) )
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

        treeTable.setPreferredScrollableViewportSize(new Dimension(2040, 200));
        treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setBackground(Color.white);

        JTableHeader header = treeTable.getTableHeader();
        header.setUpdateTableInRealTime(false);
        header.setBorder(BorderList.loweredBorder);

        // table header를 mouse 오른쪽 click하면 column 추가/삭제를 위한 popup Menu를 띄운다
        header.addMouseListener(new TreeTableHeaderMouseAdapter());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(header);
        JScrollPane treeTableScroll = new JScrollPane(treeTable);
        treeTableScroll.setBackground(Color.white);
        treeTableScroll.getViewport().setBackground(Color.white);
        add(treeTableScroll);
        setBackground(Color.white);

        treeTable.getTree().setCellRenderer(new BOMTreeRenderer(app));

        updateUI();
    }

    /**
     * Method Name : setEvent()
     * Description :
     */
    private void setEvent()
    {
        ActionListener popupActionListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String actioncommand = e.getActionCommand();
                if (actioncommand.equals("find"))
                {
                    findPNOperation();
                }
                else if (actioncommand.equals("copy"))
                {
                    copyBOMOperation();
                }
                else if (actioncommand.equals("viewproperty"))
                {
                    showPartPropertyDlg(true);
                }
            }
        };

        findItemMenu.addActionListener(popupActionListener);
        copyMenu.addActionListener(popupActionListener);
        propertyMenu.addActionListener(popupActionListener);
    }

    public void openBOMData(BOMAssyComponent rootComponent, Integer depth) throws Exception
    {
        levels_to_explode = depth.intValue();

        this.rootComponent = rootComponent;
        model.getRootNode().setBOMComponent(rootComponent);

Kogger.debug(getClass(), "===>> rootComponent.toString() : " + rootComponent.toString());

        try
        {
            SearchPNOperation searchOp = new SearchPNOperation( model.getRootNode(), rootComponent.toString(), model, treeTable);
            searchOp.executeModal();
        } catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            throw ex;
        }
    }

    public void openBOMData(BOMAssyComponent rootComponent, Vector bomData) throws Exception
    {
        this.bomDataVec = bomData;

        this.rootComponent = rootComponent;
        model.getRootNode().setBOMComponent(rootComponent);

        try
        {
            CommonAssyTreeLoader loader = new CommonAssyTreeLoader(model.getRootNode(), bomDataVec, null, model);
            loader.run();
        } catch (Exception ex)
        {
            Kogger.debug(getClass(), "OpenBOMData 실패:" + ex);
            throw ex;
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

        for (int i = 0; i < childCount; i++)
        {
            childNodes[i] = (BOMTreeNode)rootNode.getChildAt(i);
        }

        for (int i = 0; i < childCount; i++)
        {
            rootNode.removeElement(childNodes[i]);
            childNodes[i] = null;
        }

        model.fireTreeChanged(rootNode);
        treeTable.repaint();
    }

    private void initColumnModel()
    {
        displayedColumns = new Hashtable();
        allColumns = new Vector();

        for (int i = 3; i < treeTable.getColumnCount(); i++)
        {
            allColumns.addElement(treeTable.getColumn(treeTable.getColumnName(i)));
            displayedColumns.put(treeTable.getColumnName(i), treeTable.getColumn(treeTable.getColumnName(i)));
        }
    }

    public void showPartPropertyDlg(boolean isView)
    {
        if (treeTable.getSelectedRowCount() != 1)
        {
            MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("selectOneRow"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
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

        TotalPropertyDialog dlg = null;

        if (bomcomponent.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || bomcomponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE)
        {
            if (PartUtil.isProductType(strType)) { // 선택한 노드의 부품이 제품인 경우
                dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, app.getDesktop(), true, model, true, app, true);
            } else {                                                // 선택한 노드의 부품이 금형인 경우
                dlg = new TotalPropertyDialog((BOMAssyComponent)bomcomponent, app.getDesktop(), true, model, true, app, false);
            }
        } else
        {
            MessageBox.post(messageRegistry.getString("compare9"), "Error", MessageBox.ERROR);
        }
    }

    private void findPNOperation()
    {
        Enumeration enum0 = model.getRootNode().preorderEnumeration();
        int count = 0;
        while (enum0.hasMoreElements())
        {
            enum0.nextElement();
            count++;
        }

        FindItemDialog dialog = new FindItemDialog(app.getDesktop(), app, false, treeTable, model, count);
    }

    private void copyBOMOperation()
    {
        BOMTreeNode[] nodes = getSelectedTreeNode();

        int firstLevel = (((nodes[0]).getBOMComponent()).getLevelInt()).intValue();
        for (int inx = 1; inx < nodes.length; inx++)
        {
            BOMAssyComponent bomassy = (nodes[inx]).getBOMComponent();
            if ((bomassy.getLevelInt()).intValue() < firstLevel)
            {
                MessageBox.post(messageRegistry.getString("checkLevel2"), "Warning", MessageBox.WARNING);
                return;
            }
        }
        ClipBoardPool.addTreeNode(app, nodes, "BOMSearch");
    }

    public BOMTreeNode[] getSelectedTreeNode()
    {
        Hashtable nodeHash = new Hashtable();
        boolean isExist = false;
        if (treeTable.getSelectedRowCount() < 1)
        {
            return null;
        }

        TreePath[] selectedPath = treeTable.getTree().getSelectionPaths();
        Vector selectedNodeV = new Vector();

        for (int inx = 0; inx < selectedPath.length; inx++)
        {
            BOMTreeNode tempnode1 = (BOMTreeNode)selectedPath[inx].getLastPathComponent();
            BOMAssyComponent assy1 = tempnode1.getBOMComponent();
            nodeHash.put(assy1.getSortOrderStr(), assy1.toString());
        }

        for (int i = 0; i < selectedPath.length; i++)
        {
            isExist = false;
            BOMTreeNode tempnode2 = (BOMTreeNode)selectedPath[i].getLastPathComponent();
            TreeNode[] parentNodes = tempnode2.getPath();
            for (int jnx = 1; jnx < (parentNodes.length - 1); jnx++)
            {
                BOMAssyComponent assy2 = ((BOMTreeNode)parentNodes[jnx]).getBOMComponent();
                if (nodeHash.containsKey(assy2.getSortOrderStr()))
                {
                    isExist = true;
                    break;
                }
            }
            if (isExist)
            {
                continue;
            }
            selectedNodeV.add(tempnode2);
        }

        BOMTreeNode[] selectedNode = new BOMTreeNode[selectedNodeV.size()];
        for (int inx = 0; inx < selectedNode.length; inx++)
        {
            selectedNode[inx] = (BOMTreeNode)selectedNodeV.elementAt(inx);
        }
        return selectedNode;
    }

    protected int getSelectedTableColumn()
    {
        TableColumnModel columnModel = treeTable.getColumnModel();
        int selectedColumnIndex = columnModel.getColumnIndexAtX(mouse_x);

        return selectedColumnIndex;
    }

    private void addAllColumnModelToHash(TableColumnModel columnModel)
    {
        for (int i = 0; i < allColumns.size(); i++)
        {
            TableColumn t = (TableColumn)allColumns.elementAt(i);
            String key = (String)t.getHeaderValue();
            columnModel.addColumn(t);

            if (!displayedColumns.containsKey(key))
            {
                displayedColumns.put(key, t);
            }
        }
        treeTable.tableChanged(new TableModelEvent(treeTable.getModel()));
        treeTable.repaint();
    }

    public JTreeTable getTreeTable()
    {
        return treeTable;
    }

    public BOMTreeTableModel getTreeTableModel()
    {
        return model;
    }

    public BOMTreeNode getRootNode()
    {
        return model.getRootNode();
    }

    class TreeTableHeaderMouseAdapter extends MouseAdapter
    {
        public void mouseReleased(MouseEvent ev)
        {
            if (ev.isPopupTrigger())
            {
                mouse_x = ev.getX();
                mouse_y = ev.getY();
                int p_y = CompareTargetPanel.this.getHeight();

                if (p_y - mouse_y < popupMenu.getHeight())
                {
                    tHeaderPopupMenu.show(treeTable, mouse_x, mouse_y - popupMenu.getHeight());
                } else
                {
                    tHeaderPopupMenu.show(treeTable, mouse_x, mouse_y);
                }
            }
        }
    }

    class TreeTableMouseAdapter extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2 && treeTable.getSelectedColumn() != 0)
            {
                showPartPropertyDlg(true);
            }
        }

        public void mouseReleased(MouseEvent ev)
        {
            if (ev.isPopupTrigger())
            {
                int x = ev.getX();
                int y = ev.getY();
                int p_y = CompareTargetPanel.this.getHeight();

                TreePath path = treeTable.getTree().getPathForLocation(x, y);
                if (path != null)
                {
                    TreePath[] selectedPaths = treeTable.getTree().getSelectionPaths();

                    if (selectedPaths == null)
                    {
                        treeTable.getTree().setSelectionPath(path);
                    } else
                    {
                        boolean equalFlag = false;
                        for (int i = 0; i < selectedPaths.length; i++)
                        {
                            if (selectedPaths[i] == path)
                            {
                                equalFlag = true;
                                break;
                            }
                        }
                        if (equalFlag)
                        {
                            treeTable.getTree().setSelectionPaths(selectedPaths);
                        } else
                        {
                            treeTable.getTree().setSelectionPath(path);
                        }
                    }
                    if (p_y - y < popupMenu.getHeight())
                    {
                        popupMenu.show(treeTable, x, y - popupMenu.getHeight());
                    } else
                    {
                        popupMenu.show(treeTable, x, y);
                    }
                }
            }
        }
    }
}
