package e3ps.bom.command.comparebom.staticcompare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.comparebom.AbstractComparePanel;
import e3ps.bom.command.comparebom.AbstractCompareResultTableModel;
import e3ps.bom.command.comparebom.CompareFrame;
import e3ps.bom.command.comparebom.CompareProgressDialog;
import e3ps.bom.command.searchitem.SearchBOMPanel;
import e3ps.bom.common.ItemTableData;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMDesignatorComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class StaticComparePanel extends AbstractComparePanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JSplitPane staticSplit;
    private JPanel staticConditionPanel, staticConditionPanel2, staticConditionPanel3, conditionTmpPanel, resultTmpPanel;
    private JScrollPane staticConditionScroll, staticResultScroll;
    private JButton sourceSearchBtn, targetSearchBtn, compareBtn, clearBtn;
    private JLabel sourceItemCodeLbl, targetItemCodeLbl, compareOptionLbl, levelLbl, checkAttrsLbl, codeLbl;
    private JTextField sourceItemCodeTfl, targetItemCodeTfl;
    private JComboBox levelCmb, compareOptionCmb;
    private JCheckBox quantityChk, supplyTypeChk;
    private CompareProgressDialog progress;
    private Timer timer;
    private Registry appReg;

    private Vector depthVector, statusVector, categoryVector;
    private Vector resultVector = new Vector();
    private JTable staticResultTable;

    private ItemTableData targetComponent = null;
    AbstractCompareResultTableModel staticResultTableModel = null;
    SearchBOMPanel searchPanel;

    private final int DEPTH = 20;

    private final String PROGRAM_ID = "StaticComparePanel";
    private final String DATASET_NAME = "StaticCompareResult";

    private String strType = "";
    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String panelStr = "";

    public StaticComparePanel(CompareFrame parentFrame, AbstractAIFUIApplication app, String panelStr, SearchBOMPanel searchPanel) throws Exception
    {
        super(parentFrame, app);

        try
        {
            this.panelStr = panelStr;
Kogger.debug(getClass(), "---------------------------------->>>>>    panelStr : "+panelStr);
            this.searchPanel = searchPanel;
            appReg = Registry.getRegistry(app);
            setGUIInitialData();
            setGUI();
            setInitialData();
            setEvent();

            //add by shin.....
            String soucerItem =  sourceItemCodeTfl.getText();
            KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
            WTPart part = kh.searchItem(soucerItem);
            strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);  //BOMFlag

            if(PartUtil.isProductType(strType))
            {
                setGenTable();
            }else{
                setMoldTable();
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private void setGUIInitialData()
    {
        depthVector = new Vector(20);
        for (int inx = 1; inx <= DEPTH; inx++)
        {
            depthVector.add(new Integer(inx));
        }

        categoryVector = new Vector();
        categoryVector.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00318")/*제품*/);
        categoryVector.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00099")/*금형*/);
    }

    private void setGUI() throws Exception
    {
        setLayout(new BorderLayout());

        staticSplit = new JSplitPane();
        staticSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        staticSplit.setDividerSize(1);

        conditionTmpPanel = new JPanel(new GridLayout(3,1));
        conditionTmpPanel.setPreferredSize(new Dimension(950, 50));
        resultTmpPanel = new JPanel(new BorderLayout());

        staticConditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staticConditionPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staticConditionPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        sourceItemCodeLbl = new JLabel("  A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00172")/*부품*/ + " : ");
        sourceItemCodeLbl.setFont(FontList.defaultFont);
        targetItemCodeLbl = new JLabel("  B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00172")/*부품*/ + " : ");
        targetItemCodeLbl.setFont(FontList.defaultFont);

        sourceItemCodeTfl = new JTextField(17);
        sourceItemCodeTfl.setActionCommand("Source Search");
        sourceItemCodeTfl.setEditable(false);
        sourceItemCodeTfl.setBackground(ColorList.veryLightGray);

        sourceSearchBtn = new JButton(appReg.getImageIcon("searchIcon"));
        sourceSearchBtn.setActionCommand("Source Search Button");
        sourceSearchBtn.setFont(FontList.defaultFont);
        sourceSearchBtn.setBorder(BorderList.emptyBorder1);
        sourceSearchBtn.setMargin(new Insets(0,5,0,5));

        targetItemCodeTfl = new JTextField(17);
        targetItemCodeTfl.setActionCommand("Target Search");

        targetSearchBtn = new JButton(appReg.getImageIcon("searchIcon"));
        targetSearchBtn.setActionCommand("Target Search Button");
        targetSearchBtn.setFont(FontList.defaultFont);
        targetSearchBtn.setBorder(BorderList.emptyBorder1);
        targetSearchBtn.setMargin(new Insets(0,5,0,5));

        //compareOptionLbl = new JLabel("            구분 : ");
        //compareOptionLbl.setFont(FontList.defaultFont);
        levelLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00313")/*전개 레벨*/ + " : ");
        levelLbl.setFont(FontList.defaultFont);

        //compareOptionCmb = new JComboBox(categoryVector);
        //compareOptionCmb.setFont(FontList.defaultFont);
        //compareOptionCmb.setSelectedItem("Item Code");
        //compareOptionCmb.setBackground(Color.white);

        /*checkAttrsLbl = new JLabel("          Check Attributes : ");
        checkAttrsLbl.setFont(FontList.defaultFont);
        quantityChk = new JCheckBox("Quantity", true);
        quantityChk.setFont(FontList.defaultFont);
        supplyTypeChk = new JCheckBox("Supply Type", true);
        supplyTypeChk.setFont(FontList.defaultFont);*/

        levelCmb = new JComboBox(depthVector);
        levelCmb.setFont(FontList.defaultFont);
        levelCmb.setBackground(Color.white);

        compareBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00203")/*비교*/, appReg.getImageIcon("comparebomIcon"));
        compareBtn.setActionCommand("Compare");
        compareBtn.setFont(FontList.defaultFont);
        compareBtn.setMargin(new Insets(0,5,0,5));
        clearBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00332")/*초기화*/, appReg.getImageIcon("clearIcon"));
        clearBtn.setActionCommand("Clear");
        clearBtn.setFont(FontList.defaultFont);
        clearBtn.setMargin(new Insets(0,5,0,5));

        staticResultTable = new JTable();
        staticResultTable.setAutoCreateColumnsFromModel(false);

        try
        {
            staticResultTableModel = new StaticCompareResultTableModel(this);
        } catch (Exception ex)
        {
            throw ex;
        }
        staticResultTable.setModel(staticResultTableModel);
        staticResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int inx = 0; inx < staticResultTableModel.cNames.length; inx++)
        {
            StaticCompareResultTableCellRenderer renderer = new StaticCompareResultTableCellRenderer(app);
            renderer.setHorizontalAlignment(JLabel.LEFT);
            TableCellEditor editor = null;
            int width = 0;

            /*if(staticResultTableModel.getColumnName(inx).equals("Code"))
            {
                width = 50;
            }
            else*/

            if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/) ||
                        staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/))
            {
                width = 90;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/))
            {
                width = 100;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
            {
                width = 45;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/))
            {
                width = 45;
            }

            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/))
            {
                width = 60;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/))
            {
                width = 60;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/))
            {
                width = 60;
            }
            else if(staticResultTableModel.getColumnName(inx).equals("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/) || staticResultTableModel.getColumnName(inx).equals("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/))
            {
                width = 60;
            }

            TableColumn column = new TableColumn(inx, width, renderer, editor);
            staticResultTable.addColumn(column);


        }

        staticResultTable.setCellSelectionEnabled(false);
        staticResultTable.setColumnSelectionAllowed(false);
        staticResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        staticResultScroll = new JScrollPane();
        conditionTmpPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00205")/*비교 조건*/, 0, 0, FontList.defaultFont));
        staticResultScroll.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00204")/*비교 결과*/, 0, 0, FontList.defaultFont));

        JPanel codePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //codeLbl = new JLabel("Comparison Code : N(Item difference), Q(Quantity difference), S(SupplyType difference)");
        codeLbl = new JLabel("");
        codeLbl.setFont(FontList.defaultFont);
        codePanel.add(codeLbl);

        staticConditionPanel.add(sourceItemCodeLbl, null);
        staticConditionPanel.add(sourceItemCodeTfl, null);
        //staticConditionPanel.add(sourceSearchBtn, null);

        staticConditionPanel.add(targetItemCodeLbl, null);
        staticConditionPanel.add(targetItemCodeTfl, null);
        staticConditionPanel.add(targetSearchBtn, null);

        //staticConditionPanel.add(compareOptionLbl, null);
        //staticConditionPanel.add(compareOptionCmb, null);
        staticConditionPanel.add(levelLbl, null);
        staticConditionPanel.add(levelCmb, null);

        //staticConditionPanel2.add(checkAttrsLbl, null);
        //staticConditionPanel2.add(quantityChk, null);
        //staticConditionPanel2.add(supplyTypeChk, null);
        staticConditionPanel3.add(compareBtn, null);
        staticConditionPanel3.add(clearBtn, null);

        conditionTmpPanel.add(staticConditionPanel, null);
        //conditionTmpPanel.add(staticConditionPanel2, null);
        conditionTmpPanel.add(staticConditionPanel3, null);

        staticResultScroll.getViewport().add(staticResultTable, null);

        resultTmpPanel.add(staticResultScroll, BorderLayout.CENTER);
        resultTmpPanel.add(codePanel, BorderLayout.SOUTH);

        add(staticSplit, BorderLayout.CENTER);
        staticSplit.add(conditionTmpPanel, JSplitPane.TOP);
        staticSplit.add(resultTmpPanel, JSplitPane.BOTTOM);
        staticSplit.setDividerLocation(105);
    }

    /**
     * Method Name : setInitialData()
     * Description : UI에 initial Data를 setting한다
     */
    private void setInitialData()
    {
//Kogger.debug(getClass(), "--------------------------------------------******************************************>>>>>> setInitialData() ");
        if(panelStr.equals(""))
        {
            BOMRegisterApplicationPanel workspacePanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();
            BOMAssyComponent rootComponent = rootNode.getBOMComponent();
            String rootComponentType = "";

            if (!rootComponent.toString().equals("Empty"))
            {
                rootComponentType = rootComponent.getComponentTypeStr();

                if (workspacePanel.getSelectedTreeNode() == null)
                {
                    if (rootComponentType == BOMAssyComponent.ASSY_TYPE)
                    {
                        sourceNode = (BOMTreeNode)((BOMTreeNode)model.getRootNode()).getChildAt(0);
                    }
                    else if (rootComponentType == BOMAssyComponent.MODEL_TYPE)
                    {
                        sourceNode = model.getRootNode();
                    }
                }
                else if ((workspacePanel.getSelectedTreeNode()).length > 1)
                {
                    MessageBox.post(messageRegistry.getString("compare10"), "Invalid selection", MessageBox.WARNING);
                    return;
                }
                else if ((workspacePanel.getSelectedTreeNode()).length == 1)
                {
                    BOMTreeNode[] selectedNode = workspacePanel.getSelectedTreeNode();
                    BOMAssyComponent selectedComponent = selectedNode[0].getBOMComponent();

                    if ((selectedComponent == rootComponent) && (rootComponentType == BOMAssyComponent.ASSY_TYPE))
                    {
                        sourceNode = (BOMTreeNode)selectedNode[0].getChildAt(0);
                    }
                    else
                    {
                        sourceNode = selectedNode[0];
                    }
                }

                sourceItemCodeTfl.setText(sourceNode.toString());
            }

            levelCmb.setSelectedIndex(levelCmb.getModel().getSize() - 1);
        }
        else
        {
            BOMTreeTableModel model = (BOMTreeTableModel)searchPanel.getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();
            BOMAssyComponent rootComponent = rootNode.getBOMComponent();
            String rootComponentType = "";

//Kogger.debug(getClass(), "---->> search Panel root Item Code : " + rootComponent.toString());

            if (!rootComponent.toString().equals("Empty"))
            {
                rootComponentType = rootComponent.getComponentTypeStr();

                if (searchPanel.getSelectedTreeNode() == null)
                {
                    if (rootComponentType == BOMAssyComponent.ASSY_TYPE)
                    {
                        sourceNode = (BOMTreeNode)((BOMTreeNode)model.getRootNode()).getChildAt(0);
                    }
                    else if (rootComponentType == BOMAssyComponent.MODEL_TYPE)
                    {
                        sourceNode = model.getRootNode();
                    }
                }
                else if ((searchPanel.getSelectedTreeNode()).length > 1)
                {
                    MessageBox.post(messageRegistry.getString("compare10"), "Invalid selection", MessageBox.WARNING);
                    return;
                }
                else if ((searchPanel.getSelectedTreeNode()).length == 1)
                {
                    BOMTreeNode[] selectedNode = searchPanel.getSelectedTreeNode();
                    BOMAssyComponent selectedComponent = selectedNode[0].getBOMComponent();

                    if ((selectedComponent == rootComponent) && (rootComponentType == BOMAssyComponent.ASSY_TYPE))
                    {
                        sourceNode = (BOMTreeNode)selectedNode[0].getChildAt(0);
                    }
                    else
                    {
                        sourceNode = selectedNode[0];
                    }
                }

                sourceItemCodeTfl.setText(sourceNode.toString());
            }

            levelCmb.setSelectedIndex(levelCmb.getModel().getSize() - 1);
        }
    }

    private void setEvent()
    {
        ActionListener actionlistener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String actioncommand = e.getActionCommand();

                if (actioncommand.equals("Target Search") || actioncommand.equals("Target Search Button"))
                {
                    BOMAssyComponent selectedComponent = itemSearchOperation(targetItemCodeTfl.getText());
                    if (selectedComponent != null)
                    {
                        targetItemCodeTfl.setText(Utility.checkNVL(selectedComponent.getItemCodeStr()));
                    }
                }
                else if (actioncommand.equals("Source Search") || actioncommand.equals("Source Search Button"))
                {
                    if (!panelStr.equals("BOMSearch"))
                    {
                        MessageBox.post(parentFrame, messageRegistry.getString("compare11"), "Invalid action", MessageBox.WARNING);
                        return;
                    }
                    else
                    {
                        BOMAssyComponent selectedComponent = itemSearchOperation(sourceItemCodeTfl.getText());

                        if (selectedComponent != null)
                        {
                            sourceItemCodeTfl.setText(Utility.checkNVL(selectedComponent.getItemCodeStr()));

                            try
                            {
                                parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                changeSourceItemCode(selectedComponent, searchPanel);
                            }
                            catch (Exception ex)
                            {
                                Kogger.error(getClass(), ex);
                            }
                            finally
                            {
                                parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                    }
                }
                else if (actioncommand.equals("Compare"))
                {
                    try
                    {
                        parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                        // User가 입력한 모델이 valid한지 체크한다
                        if (!isValidItemCode(sourceItemCodeTfl.getText(), targetItemCodeTfl.getText()))
                        {
                            return;
                        }

                        if (targetComponent == null)
                        {
                            MessageBox.post(parentFrame, messageRegistry.getString("compare12"), "Target Item Code Error", MessageBox.WARNING);
                            return;
                        }

                        targetItemCodeTfl.setText(Utility.checkNVL(targetComponent.getItemCode()));

                        //add by shin.... 제품,금형인지 가져온다.
                        String targetItem =  targetItemCodeTfl.getText();
                        String soucerType = "";
                        String targetType = "";

                        KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
                        WTPart targetPart = kh.searchItem(targetItem);
                        targetType = PartSpecGetter.getPartSpec(targetPart, PartSpecEnum.SpPartType); // PartType

                        soucerType = strType.equals("D")?"M":strType;
                        targetType = targetType.equals("D")?"M":targetType;
                        // 비교하려는 item 이 소스item 이랑 parttype 이 다르면 오류메세지.
                        if(!soucerType.equals(targetType))
                        {
                            MessageBox.post(parentFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00018")/*A부품과 B부품의 타입이 서로 다릅니다.*/, "Error", MessageBox.WARNING);
                            return;
                        }

                        progress = new CompareProgressDialog(parentFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00206")/*비교대상 BOM을 로딩중입니다 ...*/);
                        progress.run();

                        Thread runner = new Thread()
                        {
                            public void run()
                            {
                                compareOperation();
                            }
                        };
                        runner.start();
                    }
                    catch (Exception ex)
                    {
                        Kogger.error(getClass(), ex);
                        MessageBox.post(ex);
                    }
                    finally
                    {
                        parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                else if (actioncommand.equals("Clear"))
                {
                    clearOperation();
                }
            }
        };

        KeyAdapter keyadapter = new KeyAdapter()
        {
            public void keyTyped(KeyEvent keyevent)
            {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if (cCode >= 97 && cCode <= 122)
                {
                    keyCheck = true;
                }
            }

            public void keyReleased(KeyEvent keyevent)
            {
                if (keyCheck)
                {
                    JTextField tfl = (JTextField)keyevent.getSource();
                    int pos = tfl.getCaretPosition();

                    String s = tfl.getText();
                    tfl.setText(s.toUpperCase());
                    tfl.setCaretPosition(pos);
                    keyCheck = false;
                }
            }
        };

        sourceItemCodeTfl.addActionListener(actionlistener);
        sourceSearchBtn.addActionListener(actionlistener);
        targetItemCodeTfl.addActionListener(actionlistener);
        targetSearchBtn.addActionListener(actionlistener);
        compareBtn.addActionListener(actionlistener);
        clearBtn.addActionListener(actionlistener);
        targetItemCodeTfl.addKeyListener(keyadapter);
        sourceItemCodeTfl.addKeyListener(keyadapter);

        MouseAdapter mouseAdapter = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int selectedRow = staticResultTable.getSelectedRow();

                if (selectedRow < 0)
                {
                    return;
                }

                for (int inx = 0; inx < staticResultTable.getColumnCount(); inx++)
                {
                    StaticCompareResultTableCellRenderer renderer = (StaticCompareResultTableCellRenderer)staticResultTable.getCellRenderer(0, inx);
                    renderer.releaseHighlightRow(parentFrame.getStaticCurrentRow());
                    renderer.setHighlightRow(selectedRow);
                }
                staticResultTable.updateUI();
                parentFrame.setStaticCurrentRow(selectedRow);
                setFocusWorkspacePanel(((StaticCompareResultTableModel)staticResultTable.getModel()).getComponent(selectedRow));
            }
        };
        staticResultTable.addMouseListener(mouseAdapter);
    }

    public void setGenTable()
    {
        TableColumnModel columnModel = staticResultTable.getColumnModel();
        TableColumn column = columnModel.getColumn(5);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(6);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(7);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(8);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(14);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(15);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(16);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(17);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(120);

        column = columnModel.getColumn(1);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(120);

        column = columnModel.getColumn(2);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(140);

        column = columnModel.getColumn(9);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(120);

        column = columnModel.getColumn(10);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(120);

        column = columnModel.getColumn(11);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(140);

        column = columnModel.getColumn(4);
        column.setMinWidth(0);
        column.setMaxWidth(100);
        column.setResizable(true);
        column.setPreferredWidth(45);

        column = columnModel.getColumn(13);
        column.setMinWidth(0);
        column.setMaxWidth(100);
        column.setResizable(true);
        column.setPreferredWidth(45);
    }
    public void setMoldTable()
    {
        TableColumnModel columnModel = staticResultTable.getColumnModel();
        TableColumn column = columnModel.getColumn(4);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(13);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        column = columnModel.getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(90);

        column = columnModel.getColumn(1);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(90);

        column = columnModel.getColumn(2);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(100);

        column = columnModel.getColumn(9);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(90);

        column = columnModel.getColumn(10);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(90);

        column = columnModel.getColumn(11);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(100);

        column = columnModel.getColumn(5);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(6);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(7);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(8);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(70);

        column = columnModel.getColumn(14);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(15);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(16);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(60);

        column = columnModel.getColumn(17);
        column.setMinWidth(0);
        column.setMaxWidth(200);
        column.setResizable(true);
        column.setPreferredWidth(70);
    }

    public void setFocusWorkspacePanel(StaticCompareResultData resultData)
    {

Kogger.debug(getClass(), "@@@@ resultData : " + resultData);
Kogger.debug(getClass(), "@@@@ getFirstFlag : " + resultData.getFirstFlag());
Kogger.debug(getClass(), "@@@@ getSecondFlag : " + resultData.getSecondFlag());
Kogger.debug(getClass(), "@@@@ searchPanel : " + searchPanel);

        if (!resultData.getFirstFlag().equals(""))
        {
            JTreeTable treeTable =  null;
            BOMTreeTableModel model = null;
            BOMTreeNode rootNode = null;
            BOMAssyComponent sourceComponent = resultData.getSourceAssyComponent();

            // BOM 조회 화면에 포커싱 처리
            if ( searchPanel != null ) {

                treeTable = searchPanel.getTreeTable();
                model = (BOMTreeTableModel)searchPanel.getTreeTableModel();
                rootNode = model.getRootNode();
                Enumeration enum1 = rootNode.preorderEnumeration();

                while (enum1.hasMoreElements())
                {
                    BOMTreeNode allListNode = (BOMTreeNode)enum1.nextElement();
                    BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();
                    if (bomassy.getSeqInt().intValue() == sourceComponent.getSeqInt().intValue())
                    {
                        TreePath treepath = new TreePath(allListNode.getPath());
                        try
                        {
                            treeTable.getTree().fireTreeWillExpand(treepath);
                            treeTable.getTree().scrollPathToVisible(treepath);
                            treeTable.getTree().fireTreeExpanded(treepath);
                            treeTable.getTree().setSelectionPath(treepath);
                            treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                        }
                        catch (Exception ex)
                        {
                            Kogger.error(getClass(), ex);
                            MessageBox.post(ex);
                        }
                    }
                }
            }

            // 메인 에디터 화면에 포커싱 처리
            BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
            treeTable = bomPanel.getTreeTable();
            model = (BOMTreeTableModel)bomPanel.getTreeTableModel();
            rootNode = model.getRootNode();
            Enumeration enum0 = rootNode.preorderEnumeration();

            while (enum0.hasMoreElements())
            {
                BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
                BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();
                if (bomassy.getSeqInt().intValue() == sourceComponent.getSeqInt().intValue())
                {
                    TreePath treepath = new TreePath(allListNode.getPath());
                    try
                    {
                        treeTable.getTree().fireTreeWillExpand(treepath);
                        treeTable.getTree().scrollPathToVisible(treepath);
                        treeTable.getTree().fireTreeExpanded(treepath);
                        treeTable.getTree().setSelectionPath(treepath);
                        treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                    }
                    catch (Exception ex)
                    {
                        Kogger.error(getClass(), ex);
                        MessageBox.post(ex);
                    }
                }
            }
        }

        if (!resultData.getSecondFlag().equals(""))
        {
            BOMAssyComponent targetComponent = resultData.getTargetAssyComponent();

            JTreeTable treeTable = compareTargetPanel.getTreeTable();

            BOMTreeTableModel model = (BOMTreeTableModel)compareTargetPanel.getTreeTableModel();
            BOMTreeNode rootNode = model.getRootNode();
            Enumeration enum0 = rootNode.preorderEnumeration();

            while (enum0.hasMoreElements())
            {
                BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
                BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();
                if (bomassy.getSeqInt().intValue() == targetComponent.getSeqInt().intValue())
                {
                    TreePath treepath = new TreePath(allListNode.getPath());
                    try
                    {
                        treeTable.getTree().fireTreeWillExpand(treepath);
                        treeTable.getTree().scrollPathToVisible(treepath);
                        treeTable.getTree().fireTreeExpanded(treepath);
                        treeTable.getTree().setSelectionPath(treepath);
                        treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                    } catch (Exception ex)
                    {
                        Kogger.error(getClass(), ex);
                        MessageBox.post(ex);
                    }
                }
            }
        }
    }

    private void compareOperation()
    {
Kogger.debug(getClass(), "-------------------------->>>>>>>>>>>>>>>  비교시작!!! ");
        try
        {
            int rowCount = staticResultTable.getRowCount();
            for (int inx = 0; inx < rowCount; inx++)
            {
                ((StaticCompareResultTableModel)staticResultTable.getModel()).delete(0);
            }
            staticResultTable.updateUI();

            resultVector.removeAllElements();

            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            if (compareTargetPanel == null || !(Utility.checkNVL(targetComponent.getItemCode())).equals(Utility.checkNVL(compareTargetPanel.getRootNode().toString())))
            {
                if(panelStr.equals(""))
                {
                    Kogger.debug(getClass(), "-------------------------->>>>>>>>>>>>>>>  111 ");
                    loadTargetBOMTree(Utility.checkNVL(targetComponent.getItemCode()));
                }
                else
                {
                    Kogger.debug(getClass(), "-------------------------->>>>>>>>>>>>>>>  222 ");
                    loadTargetBOMTree(Utility.checkNVL(targetComponent.getItemCode()), searchPanel);
                }

                //shin...제품, 금형에 따라 변경한다.
                KetMainJTreeTable kj = new KetMainJTreeTable();
                JTreeTable cTreeTable = compareTargetPanel.getTreeTable();
                if(PartUtil.isProductType(strType))
                {
                    kj.setGenCompairMain(cTreeTable);
                }else{
                    kj.setMoldCompairMain(cTreeTable);
                }
            }

            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            compareByItem();


            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            progress.setMessage(messageRegistry.getString("compare14"));
            sortResultVector();

            parentFrame.setCompareComplete(true);
            if (resultVector.size() == 0)
            {
                progress.closeOperation();
                MessageBox.post(messageRegistry.getString("compare15"), "no difference", MessageBox.INFORMATION);
                return;
            }

            for (int inx = 0; inx < resultVector.size(); inx++)
            {
                ((StaticCompareResultTableModel)staticResultTable.getModel()).insert((StaticCompareResultData)resultVector.elementAt(inx));
            }
            staticResultTable.updateUI();

            setFirstRowFocusing();
            progress.complete();
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            MessageBox.post(ex);
            return;
        }
    }

    //노드를 담아서 비교로직을 호출.
    private void compareByItem() throws Exception
    {
        progress.setMessage(messageRegistry.getString("compare16"));
        try
        {
            Vector sourcePartVector = getItemVector(sourceNode);

//Kogger.debug(getClass(), "===>> sourcePartVector size : " + sourcePartVector.size());

            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            Vector targetPartVector = getItemVector(targetNode);

//Kogger.debug(getClass(), "===>> targetPartVector size : " + targetPartVector.size());

            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            compareSourceToTarget_Item(sourcePartVector, targetPartVector);

            if (cancelFlag)
            {
                clearOperation();
                return;
            }

            compareTargetToSource_Item(targetPartVector);
        }
        catch (Exception ex)
        {
//Kogger.debug(getClass(), "###############################===>> throw ex ");
            throw ex;
        }
    }

    private void setFirstRowFocusing()
    {
        for (int inx = 0; inx < staticResultTable.getColumnCount(); inx++)
        {
            StaticCompareResultTableCellRenderer renderer = (StaticCompareResultTableCellRenderer)staticResultTable.getCellRenderer(0, inx);
            renderer.setHighlightRow(0);
            staticResultTable.scrollRectToVisible(new Rectangle(0, 0, 1, staticResultTable.getRowHeight()));
        }
        staticResultTable.updateUI();
        parentFrame.setStaticCurrentRow(0);
        setFocusWorkspacePanel(((StaticCompareResultTableModel)staticResultTable.getModel()).getComponent(0));
    }

    private void sortResultVector()
    {
        int resultCount = resultVector.size();

        for (int inx = 0; inx < resultCount - 1; inx++)
        {
            for (int jnx = inx + 1; jnx < resultCount; jnx++)
            {
                StaticCompareResultData firstData = (StaticCompareResultData)resultVector.elementAt(inx);
                StaticCompareResultData secondData = (StaticCompareResultData)resultVector.elementAt(jnx);
                if (!secondData.getDesignatorNo().equals("") && firstData.getDesignatorNo().compareTo(secondData.getDesignatorNo()) > 0)
                {
                    resultVector.setElementAt(secondData, inx);
                    resultVector.setElementAt(firstData, jnx);
                } else if (firstData.getDesignatorNo().compareTo(secondData.getDesignatorNo()) == 0)
                {
                    if (firstData.getItemCode().compareTo(secondData.getItemCode()) > 0)
                    {
                        resultVector.setElementAt(secondData, inx);
                        resultVector.setElementAt(firstData, jnx);
                    }
                }
            }
        }
    }

    private void compareSourceToTarget_Designator(Vector sourceVector, Vector targetVector)
    {
        // target Vector가 '0'일 경우에는 source Vector의 모든 회로번호를 'N'코드로 비교 결과에 추가해야 한다
        if (targetVector.size() == 0)
        {
            while (sourceVector.size() > 0)
            {
                BOMDesignatorComponent sourceComponent = (BOMDesignatorComponent)sourceVector.elementAt(0);
                BOMAssyComponent sourceChild = sourceComponent.getAssyComponent();

                StaticCompareResultData resultData = new StaticCompareResultData();
                resultData.setFirstFlag("◎");
                resultData.setCode(NOT_EXIST_CODE);
                resultData.setDesignatorNo(sourceComponent.getDesignatorNoStr());
                resultData.setItemCode(sourceChild.getItemCodeStr());
                resultData.setSourceAssyComponent(sourceChild);
                resultData.setSourceDesignatorComponent(sourceComponent);

                resultVector.add(resultData);

                sourceVector.removeElementAt(0);
            }
            return;
        }

        // Parent Item Code, Child Item Code, Designator No 가 동시에 같은 것이 우선이므로 먼저 걸러낸다
        for (int jnx = 0; jnx < sourceVector.size(); )
        {
            boolean isExist = false;

            BOMDesignatorComponent sourceComponent = (BOMDesignatorComponent)sourceVector.elementAt(jnx);
            BOMAssyComponent sourceChild = sourceComponent.getAssyComponent();

            for (int inx = 0; inx < targetVector.size(); inx++)
            {
                BOMDesignatorComponent targetComponent = (BOMDesignatorComponent)targetVector.elementAt(inx);
                BOMAssyComponent targetChild = targetComponent.getAssyComponent();

                // source Item Code 의 Designator No 와 Target Item Code 의 Designator No 를 비교해서 Designator No 와 Child Item Code, Parent Item Code 가 같으면 각 Designator  정보를 비교한다.
                if (sourceComponent.getDesignatorNoStr().equals(targetComponent.getDesignatorNoStr())
                    && sourceChild.getItemCodeStr().equals(targetChild.getItemCodeStr())
                    && sourceChild.getParentItemCodeStr().equals(targetChild.getParentItemCodeStr()))
                {
                    compareProperty_Designator(sourceComponent, targetComponent);
                    sourceVector.removeElementAt(jnx);
                    targetVector.removeElementAt(inx);
                    isExist = true;
                    break;
                }
            }

            if (!isExist)
            {
                jnx++;
            }
        }

        while (sourceVector.size() > 0)
        {
            boolean isExist = false;

            BOMDesignatorComponent sourceComponent = (BOMDesignatorComponent)sourceVector.elementAt(0);
            BOMAssyComponent sourceChild = sourceComponent.getAssyComponent();

            for (int inx = 0; inx < targetVector.size(); inx++)
            {
                BOMDesignatorComponent targetComponent = (BOMDesignatorComponent)targetVector.elementAt(inx);
                BOMAssyComponent targetChild = targetComponent.getAssyComponent();

                if (sourceComponent.getDesignatorNoStr().equals(targetComponent.getDesignatorNoStr())
                    && sourceChild.getItemCodeStr().equals(targetChild.getItemCodeStr()))
                {
                    compareProperty_Designator(sourceComponent, targetComponent);
                    sourceVector.removeElementAt(0);
                    targetVector.removeElementAt(inx);
                    isExist = true;
                    break;
                }
            }

            if (!isExist)
            {
                StaticCompareResultData resultData = new StaticCompareResultData();
                resultData.setFirstFlag("◎");
                resultData.setCode(NOT_EXIST_CODE);
                resultData.setDesignatorNo(sourceComponent.getDesignatorNoStr());
                resultData.setItemCode(sourceChild.getItemCodeStr());
                resultData.setSourceAssyComponent(sourceChild);
                resultData.setSourceDesignatorComponent(sourceComponent);

                resultVector.add(resultData);

                sourceVector.removeElementAt(0);
            }
        }
    }

    //여기서 아이템을 비교하는듯....
    private void compareSourceToTarget_Item(Vector sourceVector, Vector targetVector) throws Exception
    {
        if (targetVector.size() == 0)
        {
            while (sourceVector.size() > 0)
            {
                BOMAssyComponent sourceComponent = (BOMAssyComponent)sourceVector.elementAt(0);

                StaticCompareResultData resultData = new StaticCompareResultData();
                resultData.setFirstFlag("◎");
                resultData.setCode(NOT_EXIST_CODE);
                resultData.setItemCode(sourceComponent.getItemCodeStr());
                resultData.setSourceAssyComponent(sourceComponent);

                resultVector.add(resultData);

                sourceVector.removeElementAt(0);
            }
            return;
        }

        for (int jnx = 0; jnx < sourceVector.size(); )
        {
            boolean isExist = false;

            BOMAssyComponent sourceComponent = (BOMAssyComponent)sourceVector.elementAt(jnx);

            for (int inx = 0; inx < targetVector.size(); inx++)
            {
                BOMAssyComponent targetComponent = (BOMAssyComponent)targetVector.elementAt(inx);

                if (sourceComponent.getItemCodeStr().equals(targetComponent.getItemCodeStr()) && sourceComponent.getParentItemCodeStr().equals(targetComponent.getParentItemCodeStr()))
                {
                    compareProperty_Item(sourceComponent, targetComponent);
                    sourceVector.removeElementAt(jnx);
                    targetVector.removeElementAt(inx);
                    isExist = true;
                    break;
                }
            }

            if (!isExist)
            {
                jnx++;
            }
        }

        while (sourceVector.size() > 0)
        {
            boolean isExist = false;
            BOMAssyComponent sourceComponent = (BOMAssyComponent)sourceVector.elementAt(0);

            if (targetVector.size() == 0)
            {
                StaticCompareResultData resultData = new StaticCompareResultData();
                resultData.setFirstFlag("◎");
                resultData.setCode(NOT_EXIST_CODE);
                resultData.setItemCode(sourceComponent.getItemCodeStr());
                resultData.setSourceAssyComponent(sourceComponent);

                resultVector.add(resultData);

                sourceVector.removeElementAt(0);
                continue;
            }

            for (int inx = 0; inx < targetVector.size(); inx++)
            {
                BOMAssyComponent targetComponent = (BOMAssyComponent)targetVector.elementAt(inx);

                if (sourceComponent.getItemCodeStr().equals(targetComponent.getItemCodeStr()))
                {
                    compareProperty_Item(sourceComponent, targetComponent);
                    sourceVector.removeElementAt(0);
                    targetVector.removeElementAt(inx);
                    isExist = true;
                    break;
                }
            }

            if (!isExist)
            {
                StaticCompareResultData resultData = new StaticCompareResultData();
                resultData.setFirstFlag("◎");
                resultData.setCode(NOT_EXIST_CODE);
                resultData.setItemCode(sourceComponent.getItemCodeStr());
                resultData.setSourceAssyComponent(sourceComponent);

                resultVector.add(resultData);

                sourceVector.removeElementAt(0);
            }
        }
    }

    private void compareTargetToSource_Designator(Vector targetVector)
    {
        while (targetVector.size() > 0)
        {
            BOMDesignatorComponent targetComponent = (BOMDesignatorComponent)targetVector.elementAt(0);
            BOMAssyComponent targetChild = targetComponent.getAssyComponent();

            StaticCompareResultData resultData = new StaticCompareResultData();
            resultData.setSecondFlag("◎");
            resultData.setCode(NOT_EXIST_CODE);
            resultData.setDesignatorNo(targetComponent.getDesignatorNoStr());
            resultData.setItemCode(targetChild.getItemCodeStr());
            resultData.setTargetAssyComponent(targetChild);
            resultData.setTargetDesignatorComponent(targetComponent);

            resultVector.add(resultData);

            targetVector.removeElementAt(0);
        }
    }

    private void compareTargetToSource_Item(Vector targetVector)
    {
        while (targetVector.size() > 0)
        {
            BOMAssyComponent targetComponent = (BOMAssyComponent)targetVector.elementAt(0);
            StaticCompareResultData resultData = new StaticCompareResultData();
            resultData.setSecondFlag("◎");
            resultData.setCode(NOT_EXIST_CODE);
            resultData.setItemCode(targetComponent.getItemCodeStr());
            resultData.setTargetAssyComponent(targetComponent);

            resultVector.add(resultData);

            targetVector.removeElementAt(0);
        }
    }

    private void compareProperty_Designator(BOMDesignatorComponent sourceComponent, BOMDesignatorComponent targetComponent)
    {
        BOMAssyComponent sourceChild = sourceComponent.getAssyComponent();
        BOMAssyComponent targetChild = targetComponent.getAssyComponent();

        StaticCompareResultData resultData = new StaticCompareResultData();
        resultData.setFirstFlag("◎");
        resultData.setSecondFlag("◎");
        resultData.setDesignatorNo(sourceComponent.getDesignatorNoStr());
        resultData.setItemCode(sourceChild.getItemCodeStr());
        resultData.setSourceAssyComponent(sourceChild);
        resultData.setTargetAssyComponent(targetChild);

        if (sourceComponent.getQuantityDbl().doubleValue() != targetComponent.getQuantityDbl().doubleValue())
        {
//            if (quantityChk.isSelected())
//            {
                resultData.setCode(QTY_UNLIKE_CODE);
                resultVector.add(resultData);
//            }
        }
    }

    private void compareProperty_Item(BOMAssyComponent sourceComponent, BOMAssyComponent targetComponent)
    {
        StaticCompareResultData resultData = new StaticCompareResultData();
        resultData.setFirstFlag("◎");
        resultData.setSecondFlag("◎");
        resultData.setItemCode(sourceComponent.getItemCodeStr());
        resultData.setSourceAssyComponent(sourceComponent);
        resultData.setTargetAssyComponent(targetComponent);

        if (sourceComponent.getQuantityDbl().doubleValue() != targetComponent.getQuantityDbl().doubleValue())
        {
//            if (quantityChk.isSelected())
//            {
                resultData.setCode(QTY_UNLIKE_CODE);
                resultVector.add(resultData);
//            }
        }
        else if (!sourceComponent.getSupplyTypeStr().equals(targetComponent.getSupplyTypeStr()))
        {
            if (supplyTypeChk.isSelected())
            {
                resultData.setCode(STYPE_UNLIKE_CODE);
                resultVector.add(resultData);
            }
        }
    }

    private Vector getItemVector(BOMTreeNode treeNode) throws Exception
    {
//Kogger.debug(getClass(), "----------------------------------->>> getItemVector(BOMTreeNode treeNode) ");

        int rootLevel = treeNode.getBOMComponent().getLevelInt().intValue();
        int selectedCompareLevel = ((Integer)levelCmb.getSelectedItem()).intValue();

        Enumeration enum0 = treeNode.preorderEnumeration();
        enum0.nextElement();
        Vector partVector = new Vector();

        while (enum0.hasMoreElements())
        {
            BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();

            BOMTreeNode parentNode = (BOMTreeNode)allListNode.getParent();
            BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();
            int bomassyLevel = bomassy.getLevelInt().intValue();

            if (parentNode == null)
            {
                continue;
            }

            //Kogger.debug(getClass(), "----------------------------------->>> bomassyLevel - rootLevel : "+(bomassyLevel - rootLevel) );
            //Kogger.debug(getClass(), "----------------------------------->>> selectedCompareLevel :  "+selectedCompareLevel);
            if ((bomassyLevel - rootLevel) <= selectedCompareLevel)
            {
                //Kogger.debug(getClass(), "----------------------------------->>> partVector.add(bomassy); ");
                partVector.add(bomassy);
            }
        }
        return partVector;
    }

    public void addColumnOperation(Vector columnVector, int selectedColumnIndex)
    {
        int columnCount = staticResultTable.getColumnCount();

        for (int inx = 0; inx < columnVector.size(); inx++)
        {
            String selectedValue = (String)columnVector.elementAt(inx);
            StaticCompareResultTableCellRenderer renderer = new StaticCompareResultTableCellRenderer(app);
            renderer.setHorizontalAlignment(JLabel.LEFT);

            TableColumnModel columnModel = staticResultTable.getColumnModel();
            TableColumn column = columnModel.getColumn(inx);
            staticResultTable.addColumn(column);
            staticResultTable.moveColumn(staticResultTable.getColumnCount() - 1, selectedColumnIndex + inx);
        }
    }

    public void clearOperation()
    {
        targetItemCodeTfl.setText("");

        //compareOptionCmb.setName("제품");
        //compareOptionCmb.setSelectedIndex(0);
        //String selectedCompareOptionStr = (String)compareOptionCmb.getSelectedItem();
        levelCmb.setSelectedIndex(levelCmb.getModel().getSize() - 1);

        int rowCount = staticResultTable.getRowCount();
        for (int inx = 0; inx < rowCount; inx++)
        {
            ((StaticCompareResultTableModel)staticResultTable.getModel()).delete(0);
        }
        staticResultTable.updateUI();

        if (compareTargetPanel != null)
        {
            compareTargetPanel.clearBOMList();
        }
    }

    public void setTargetComponent(ItemTableData targetComponent)
    {
        this.targetComponent = targetComponent;
    }

    public JTable getResultTable()
    {
        return staticResultTable;
    }

    public void setCancelFlag(boolean cancelFlag)
    {
        this.cancelFlag = cancelFlag;
    }

    public String getProgramID()
    {
        return PROGRAM_ID;
    }

    public String getDatasetName()
    {
        return DATASET_NAME;
    }

    public JScrollPane getResultScroll()
    {
        return staticResultScroll;
    }

    public String getSourceItemCode()
    {
        return sourceItemCodeTfl.getText().trim();
    }

    public String getTargetItemCode()
    {
        return targetItemCodeTfl.getText().trim();
    }

    private void connectDB(String dbname) throws ConnectException
    {
        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(dbname);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void closeDB(String dbname) throws ConnectException
    {
        try
        {
            if(rs != null)
                rs.close();
            stmt.close();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            if(res != null)
            {
                res.freeConnection(dbname, conn);
            }
        }
    }

}
