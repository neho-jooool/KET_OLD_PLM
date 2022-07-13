package e3ps.bom.command.finditem;

import e3ps.bom.common.ColumnData;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.InterfaceAIFOperationListener;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;
import gui.JMouseWheelFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;

public class FindItemDialog extends JMouseWheelFrame
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;

    //private JTextField keywordTfl;
    //private JComboBox findOptionCb;

    private JButton searchBtn, closeBtn;
    private JTable m_table;
    private PartTableModel m_data;
    private JLabel findCountLbl, totalCountLbl, searchResultLbl, statusLbl;

    private AbstractAIFUIApplication app;
    private JTreeTable treeTable;
    private BOMTreeTableModel model;
    private int totalDataCount=0;
    private JFrame desktop;

    private JTextField itemCodeTfl, descTfl;

    private FindItemOperation findOp = null;
    private FindOperationListener findOpLsn = null;

    private boolean keyCheck = false;

    String findStr01 = "";
    String findStr02 = "";

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Search"))
            {
                m_table.clearSelection();
                findOperation(desktop);
            }
            else if (e.getActionCommand().equals("Close"))
            {
                dispose();
            }
        }
    }

    class TableSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

            if (lsm.isSelectionEmpty())
            {
            }
            else
            {
                int rowNum = m_table.getSelectedRow();
                BOMTreeNode selectedNode = m_data.getComponent(rowNum);
                TreePath treepath = new TreePath(selectedNode.getPath());
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
                }
            }
        }
    }

    public FindItemDialog(JFrame frame, AbstractAIFUIApplication app, boolean modal, JTreeTable treeTable, BOMTreeTableModel model, int totalDataCount)
    {
        super();
        this.setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00025")/*BOM 내 부품찾기*/);

        this.app = app;
        this.treeTable = treeTable;
        this.model = model;
        this.totalDataCount = totalDataCount;
        this.desktop = app.getDesktop();

        try
        {
            jInit();
            setTotalDataCount();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }

        setSize(670,380);
        setResizable(false);

        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));
        setLocation(dimCenter.width, dimCenter.height);
        setVisible(true);
    }

    public void setTotalDataCount()
    {
        totalCountLbl.setText("" + totalDataCount);
    }

    public void dispose()
    {
        int count = getComponentCount();
        for(int i=0; i<count; i++)
        {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }


    public void findOperation(Frame desktop)
    {
        if (itemCodeTfl.getText().trim().length() == 0 && descTfl.getText().trim().length() == 0)
        {
            MessageBox m = new MessageBox(messageRegistry.getString("searchCondition"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        findOp = new FindItemOperation();
        findOpLsn = new FindOperationListener();
        findOp.addOperationListener(findOpLsn);

        try
        {
            findOp.executeOperation();
            findOpLsn.endOperation();
        }
        catch(Exception e)
        {
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void setSearchingNum(int i)
    {
        findCountLbl.setText("" + i);
    }

    public void setFoundNum(int foundData)
    {
        searchResultLbl.setText("" + foundData);
    }

    private void jInit()
    {
        Registry appReg = Registry.getRegistry(app);

        btnListener = new BtnListener();
        ///////////////////////////////////////////////////////////////////////
        JPanel topPanel = new JPanel(new BorderLayout());
        // Top Panel
        JPanel topSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ///////////////////////////////////////////////////////////////////////
        JLabel findOptionLbl = new JLabel("        " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
        findOptionLbl.setFont(FontList.defaultFont);
        itemCodeTfl = new JTextField(15);
        itemCodeTfl.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                findOperation(desktop);
            }
        });

        itemCodeTfl.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent keyevent)
            {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if ( cCode >= 97 && cCode <= 122 )
                {
                    keyCheck = true;
                }
            }

            public void keyReleased(KeyEvent keyevent)
            {
                if ( keyCheck )
                {
                    JTextField tfl = (JTextField)keyevent.getSource();
                    int pos = tfl.getCaretPosition();

                    String s = tfl.getText();
                    tfl.setText(s.toUpperCase());
                    tfl.setCaretPosition(pos);
                    keyCheck = false;
                }
            }
        });

        /*
        findOptionCb = new JComboBox()
        {
            public void paint(Graphics g)
            {
                super.paint(g);
                Painter.paintIsRequired(this, g);
            }
        };
        findOptionCb.addItem("Item Code");
        findOptionCb.addItem("Description");
        findOptionCb.addItem("Designator No");
        findOptionCb.setBackground(Color.white);
        findOptionCb.setFont(FontList.defaultFont);
        */
        ///////////////////////////////////////////////////////////////////////

        JLabel keywordLbl = new JLabel("        " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
        keywordLbl.setFont(FontList.defaultFont);
        descTfl = new JTextField(15);
        descTfl.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                findOperation(desktop);
            }
        });

        descTfl.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent keyevent)
            {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if ( cCode >= 97 && cCode <= 122 )
                {
                    keyCheck = true;
                }
            }

            public void keyReleased(KeyEvent keyevent)
            {
                if ( keyCheck )
                {
                    JTextField tfl = (JTextField)keyevent.getSource();
                    int pos = tfl.getCaretPosition();

                    String s = tfl.getText();
                    tfl.setText(s.toUpperCase());
                    tfl.setCaretPosition(pos);
                    keyCheck = false;
                }
            }
        });

        addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {
                itemCodeTfl.requestFocus();
            }
        });

        searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/, appReg.getImageIcon("searchIcon"));
        searchBtn.setMargin(new Insets(0,5,0,5));
        searchBtn.setFont(FontList.defaultFont);
        searchBtn.setActionCommand("Search");
        searchBtn.addActionListener(btnListener);

        topSearchPanel.add(findOptionLbl);
        topSearchPanel.add(itemCodeTfl);
        topSearchPanel.add(new JLabel("  "));

        //////////////////////////////////////////////////////////////////////////
        topSearchPanel.add(keywordLbl);
        topSearchPanel.add(descTfl);
        topSearchPanel.add(new JLabel("  "));
        topSearchPanel.add(searchBtn);

        JPanel topBlankPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(topSearchPanel, BorderLayout.NORTH);
        topPanel.add(topBlankPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);

        ///////////////////////////////////////////////////////////////////////
        // Table, Label
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel labelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel imgLbl = new JLabel(appReg.getImageIcon("searchIcon"));
        findCountLbl = new JLabel("0");
        findCountLbl.setFont(FontList.defaultFont);
        findCountLbl.setForeground(ColorList.darkBlue);
        JLabel slashLbl = new JLabel(" / ");
        totalCountLbl = new JLabel("0");
        totalCountLbl.setFont(FontList.defaultFont);
        totalCountLbl.setForeground(Color.darkGray);

        statusLbl = new JLabel(" Ready! ");
        statusLbl.setFont(FontList.defaultFont);
        statusLbl.setForeground(Color.blue);

        JLabel cmtLbl = new JLabel("   " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00054")/*검색결과*/ + " : ");
        cmtLbl.setFont(FontList.defaultFont);

        searchResultLbl = new JLabel("0");
        searchResultLbl.setFont(FontList.defaultFont);
        searchResultLbl.setForeground(Color.red);
        JLabel cmt2Lbl = new JLabel(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00051")/*개*/ + "      ");
        cmt2Lbl.setFont(FontList.defaultFont);

        labelPane.add(imgLbl);
        labelPane.add(findCountLbl);
        labelPane.add(slashLbl);
        labelPane.add(totalCountLbl);
        labelPane.add(cmtLbl);
        labelPane.add(searchResultLbl);
        labelPane.add(cmt2Lbl);

        m_data = new PartTableModel();
        m_table = new JTable();
        m_table.setAutoCreateColumnsFromModel(false);
        m_table.setModel(m_data);
        m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        m_table.getSelectionModel().addListSelectionListener(new TableSelectionListener());

        for (int k = 0; k < PartTableModel.m_columns.length; k++)
        {
            TableCellRenderer renderer;
            DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
            textRenderer.setHorizontalAlignment(PartTableModel.m_columns[k].m_alignment);
            textRenderer.setFont(FontList.shotKeyFont);
            renderer = textRenderer;

            TableCellEditor editor = null; // Cell editor는 필요없다.

            TableColumn column = new TableColumn(k, PartTableModel.m_columns[k].m_width, renderer, editor);
            m_table.addColumn(column);
        }


        JTableHeader header = m_table.getTableHeader();
        header.setUpdateTableInRealTime(false);

        JScrollPane ps = new JScrollPane();
        ps.setSize(600, 400);
        ps.getViewport().add(m_table);

        centerPanel.add(labelPane, BorderLayout.NORTH);
        centerPanel.add(ps, BorderLayout.CENTER);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);

        ///////////////////////////////////////////////////////////////////////
        // South Pane
        JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        closeBtn = new JButton("  " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/ , appReg.getImageIcon("closeIcon"));
        closeBtn.setFont(FontList.defaultFont);
        closeBtn.setMargin(new Insets(0,15,0,15));
        closeBtn.setActionCommand("Close");
        closeBtn.setDefaultCapable(true);
        closeBtn.addActionListener(btnListener); // setting action listener ... click event
        btnFlowPanel.add(closeBtn);

        this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
    }

    // FindItemOperation을 수행하는 동안의 Listener
    class FindOperationListener implements InterfaceAIFOperationListener
    {
        public void startOperation(String s)
        {
            searchBtn.setEnabled(false);
            closeBtn.setEnabled(false);
            statusLbl.setText(" Searching.... ");
        }

        public void endOperation()
        {
            searchBtn.setEnabled(true);
            closeBtn.setEnabled(true);
            statusLbl.setText(" Ready! ");
        }

        // 생성자.
        public FindOperationListener()
        {
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Find Item Operation class
    class FindItemOperation extends AbstractAIFOperation
    {
        public FindItemOperation()
        {
        }

        public void executeOperation() throws Exception
        {
            //shin....
            boolean likeSearch01 = false;
            boolean likeSearch02 = false;
            findStr01 = itemCodeTfl.getText().trim();
            findStr02 = descTfl.getText().trim();
                if(findStr01.indexOf("*") != -1)
                {
                    findStr01 = findStr01.replace("*", "");
                    likeSearch01 = true;
                }
                if(findStr02.indexOf("*") != -1)
                {
                    findStr02 = findStr02.replace("*", "");
                    likeSearch02 = true;
                }

            m_table.updateUI();
            m_data.removeAll();
            m_table.repaint();

            Enumeration rootEnum = model.getRootNode().preorderEnumeration();
            int i =0;
            int foundData = 0;

            setSearchingNum(i);
            setFoundNum(foundData);

            while (rootEnum.hasMoreElements())
            {
                i++;
                setSearchingNum(i);

                BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();

                BOMAssyComponent bomcomponent = treeNode.getBOMComponent();

                boolean isAdd = false;

                //shin.....
                if( !findStr01.equals("") && !findStr02.equals("") )
                {
                    if(!findStr01.equals(""))
                    {
                        if(likeSearch01)
                        {
                            if(treeNode.toString().indexOf(findStr01) != -1) isAdd = true;
                            else continue;
                        }else{
                            if(findStr01.equals(treeNode.toString())) isAdd = true;
                            else continue;
                        }
                    }
                    if(!findStr02.equals("") && isAdd == true)
                    {
                        if(likeSearch02)
                        {
                            if(bomcomponent.getDescStr().indexOf(findStr02) != -1)    isAdd = true;
                            else isAdd = false;
                        }else{
                            if(findStr02.equals(bomcomponent.getDescStr()))    isAdd = true;
                            else isAdd = false;
                        }
                    }
                }else{
                    if(!findStr01.equals(""))
                    {
                        if(likeSearch01)
                        {
                            if(treeNode.toString().indexOf(findStr01) != -1) isAdd = true;
                            else continue;
                        }else{
                            if(findStr01.equals(treeNode.toString())) isAdd = true;
                            else continue;
                        }
                    }
                    else if(!findStr02.equals(""))
                    {
                        if(likeSearch02)
                        {
                            if(bomcomponent.getDescStr().indexOf(findStr02) != -1)    isAdd = true;
                            else continue;
                        }else{
                            if(findStr02.equals(bomcomponent.getDescStr()))    isAdd = true;
                            else continue;
                        }
                    }
                }
                /////////////////////////////////////////////////////////////////////////////////

                if(isAdd)
                {
                    foundData++;
                    setFoundNum(foundData);

                    m_data.insert(treeNode, i);
                    m_table.tableChanged(new TableModelEvent(m_data, foundData-1, foundData-1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                    m_table.repaint();
                }
            }

            MessageBox m = new MessageBox(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00052", foundData)/*{0} 개의 부품이 검색되었습니다.*/,"INFORMATION", MessageBox.INFORMATION);
            m.setModal(true);
            m.setVisible(true);
            return;
        }
    }
}

//////////////////////////////////////////////////////////////////////////
// Table Model
class PartTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;

    public static final ColumnData m_columns[] =
    {
        new ColumnData( "No", 30, JLabel.CENTER ),
        new ColumnData( "SEQ", 30, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.LEFT ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/, 50, JLabel.RIGHT ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 151, JLabel.LEFT ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/, 100, JLabel.LEFT),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/, 100, JLabel.LEFT),
        new ColumnData( "ECO No", 100, JLabel.LEFT)
    };

    public static final int COL_NO = 0;
    public static final int COL_SEQ = 1;
    public static final int COL_ITEMCODE = 2;
    public static final int COL_LEVEL = 3;
    public static final int COL_DESCRIPTION = 4;
    public static final int COL_PARENTITEMCODE = 5;
    public static final int COL_REPLACE_ITEMCODE = 6;
    public static final int COL_ECONO = 7;

    protected Vector m_vector;
    protected Vector num_vector;

    public PartTableModel()
    {
        m_vector = new Vector();
        num_vector = new Vector();
    }

    public int getRowCount()
    {
        return m_vector==null ? 0 : m_vector.size();
    }

    public int getColumnCount()
    {
        return m_columns.length;
    }

    public String getColumnName(int column)
    {
        return m_columns[column].m_title;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public BOMTreeNode getComponent(int nRow)
    {
        if (nRow < 0 || nRow>=getRowCount())
        {
            return null;
        }
        return (BOMTreeNode)m_vector.elementAt(nRow);
    }

    public int getNumber(int nRow)
    {
        return ((Integer)num_vector.elementAt(nRow)).intValue();
    }

    public void insert(BOMTreeNode node, int num)
    {
        m_vector.addElement(node);
        num_vector.addElement(new Integer(num));
    }

    public void removeAll()
    {
        m_vector.removeAllElements();
        num_vector.removeAllElements();
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow>=getRowCount())
        {
            return "";
        }

        BOMTreeNode node = (BOMTreeNode)m_vector.elementAt(nRow);
        BOMAssyComponent component = (BOMAssyComponent)node.getBOMComponent();

        if (component == null)
        {
            return "";
        }

        if ( component instanceof BOMAssyComponent )
        {
            BOMAssyComponent assy = (BOMAssyComponent)component;

            switch (nCol)
            {
                case COL_NO: return String.valueOf(nRow+1);
                case COL_SEQ: return assy.getSeqInt();
                case COL_ITEMCODE: return (assy.getItemCodeStr().trim());
                case COL_LEVEL:
                    int i= assy.getLevelInt().intValue();
                    String s = "";
                    for(int j=1; j< i; j++)
                        s = s + "*";
                    s = s + i;
                    return s;
                case COL_DESCRIPTION: return assy.getDescStr();
                case COL_PARENTITEMCODE: return assy.getParentItemCodeStr();
                case COL_REPLACE_ITEMCODE:
                    Vector subAssys = assy.getSubAssyComponent();
                    if(subAssys != null && subAssys.size() > 0)
                    {
                        StringBuffer subAssyNos = new StringBuffer();
                        for(int c = 0; c < subAssys.size(); c++)
                        {
                            BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subAssys.elementAt(c);
                            if(subAssyComponent.getSubstituteItemCodeStr() != null && !subAssyComponent.getSubstituteItemCodeStr().trim().equals(""))
                                subAssyNos.append(subAssyComponent.getSubstituteItemCodeStr() + ", ");
                        }
                        String subAssyStr = subAssyNos.toString();
                        subAssyStr = subAssyStr.substring(1,(subAssyStr.length() -2));
                        return subAssyStr;
                    }
                    else
                    {
                        return "";
                    }
                case COL_ECONO: return assy.getEcoNoStr();
            }
        }
        return "";
    }

}
