package e3ps.bom.command.updatebom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class BOMChildItemSearchDialog extends AbstractAIFDialog implements ClipboardOwner
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;
    JTable tblList = new JTable();
    private TableSorter sorter;
    private BOMChildItemSearchTableData dataList;
    private Vector resultList = new Vector();
    Registry appReg;
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JFrame desktop;

    private JTextField itemCodeTfl, descTfl;
    private JLabel searchResultLbl;
    private JButton searchBtn, clearBtn, okBtn, closeBtn;

    String itemCodeStr = "";
    String titleStr = "";
    private boolean isOK = false;
    private int intRowCount = -1;
    private String aryResult [] = new String[8];
    private Vector addItemVec = new Vector();     // 다중선택하여 부품을 추가하는 경우
    Vector vecModel = new Vector();

    private Clipboard clipboard;

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Search"))
            {
                searchBtn_process();
            }
            else if (e.getActionCommand().equals("Clear"))
            {
                clearBtn_process();
            }
            else if (e.getActionCommand().equals("OK"))
            {
                okBtn_process();
            }
            else if (e.getActionCommand().equals("Close"))
            {
                disposeScreen();
            }
        }
    }

    public BOMChildItemSearchDialog(JFrame frame, AbstractAIFUIApplication app, String itemCode, String title)
    {
        super(frame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00176")/*부품 검색*/ , true);
        this.desktop = frame;
        this.app = app;
        itemCodeStr = itemCode;
        titleStr = title;
        appReg = Registry.getRegistry(app);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                disposeScreen();
            }
        });

Kogger.debug(getClass(), "titleStr : " + titleStr);

        try
        {
            if(titleStr.equals("ReferenceBOM"))
            {
                if(itemCodeStr.equals(""))
                {
                    vecModel = new Vector();
                }
                else
                {
                    vecModel = getSearchRefData(itemCodeStr, "");
                }
            }
            else if(titleStr.equals("CopyChild"))
            {
                vecModel = new Vector();
            }
            else if(titleStr.equals("GeneralBOM") || titleStr.equals("Sub") || titleStr.equals("searchItem") ||  titleStr.equals("Add"))
            {
                vecModel = getSearchData(itemCodeStr, "");
            }
            else
            {
                vecModel = getSearchData("", "");
            }
            dataList = new BOMChildItemSearchTableData(vecModel);

            jInit();

            setSize(650,400);
            setResizable(false);

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);
            itemCodeTfl.requestFocus();
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void jInit() throws Exception
    {
        try
        {
            btnListener = new BtnListener();

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00176")/*부품 검색*/, 0, 0, FontList.defaultFont));
            topPanel.setFont(FontList.defaultFont);

            // 첫번째 검색 라인
            JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel itemCodeLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
            itemCodeLbl.setFont(FontList.defaultFont);
            panel01.add(itemCodeLbl);

            itemCodeTfl = new JTextField(itemCodeStr, 15);
            itemCodeTfl.setText(itemCodeStr.trim().toUpperCase());
            itemCodeTfl.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    searchBtn_process();
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
                    if ( keyCheck ) {
                        JTextField tfl = (JTextField)keyevent.getSource();
                        int pos = tfl.getCaretPosition();

                        String s = tfl.getText();
                        tfl.setText(s.toUpperCase());
                        tfl.setCaretPosition(pos);
                        keyCheck = false;
                    }
                }
            });
            panel01.add(itemCodeTfl);

            JLabel descLbl = new JLabel("               " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
            descLbl.setFont(FontList.defaultFont);
            panel01.add(descLbl);
            descTfl = new JTextField(15);
            descTfl.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    searchBtn_process();
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
            panel01.add(descTfl);
            topPanel.add(panel01);

            // Search / Clear 버튼 라인
            JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/, appReg.getImageIcon("searchitemIcon"));
            searchBtn.setFont(FontList.defaultFont);
            searchBtn.setActionCommand("Search");
            searchBtn.addActionListener(btnListener);
            searchBtn.setMargin(new Insets(0,5,0,5));
            panel02.add(searchBtn);

            clearBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00324")/*지우기*/, appReg.getImageIcon("clearIcon"));
            clearBtn.setFont(FontList.defaultFont);
            clearBtn.setActionCommand("Clear");
            clearBtn.addActionListener(btnListener);
            clearBtn.setMargin(new Insets(0,5,0,5));
            panel02.add(clearBtn);

            topPanel.add(panel02);
            this.getContentPane().add(topPanel, BorderLayout.NORTH);

            // Search Result Panel /////////////////////////////////////////////////
            JPanel centerPanel = new JPanel(new BorderLayout());
            JPanel labelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel cmtLbl = new JLabel("   검색 결과 : ");
            cmtLbl.setFont(FontList.defaultFont);

            searchResultLbl = new JLabel("" + vecModel.size());
            searchResultLbl.setFont(FontList.defaultFont);
            searchResultLbl.setForeground(Color.red);
            JLabel cmt2Lbl = new JLabel(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00051")/*개*/ + "      ");
            cmt2Lbl.setFont(FontList.defaultFont);

            labelPane.add(cmtLbl);
            labelPane.add(searchResultLbl);
            labelPane.add(cmt2Lbl);

            tblList.setAutoCreateColumnsFromModel(false);

            if(titleStr.trim().equals("Add")) {
                tblList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            } else {
                tblList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }

            sorter = new TableSorter(dataList);
            tblList.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblList);
            tblList.repaint();

            for(int k = 0; k < BOMChildItemSearchTableData.clmModelData.length; k++)
            {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(BOMChildItemSearchTableData.clmModelData[k].intAlignment);
                TableColumn column = new TableColumn(k, BOMChildItemSearchTableData.clmModelData[k].intWidth, renderer, null);
                tblList.addColumn(column);

                TableColumnModel columnModel = tblList.getColumnModel();
                TableColumn _column = columnModel.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel = tblList.getTableHeader();
            headerModel.setUpdateTableInRealTime(false);

            JScrollPane ps = new JScrollPane();
            ps.getViewport().add(tblList);

            tblList.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                    StringSelection contents = new StringSelection(tblList.getValueAt(tblList.getSelectedRow(),tblList.getSelectedColumn()).toString());
                    clipboard.setContents(contents, contents);
                }

                public void mouseClicked(MouseEvent e)
                {
                    if(e.getClickCount() >= 2)
                    {
                        intRowCount = tblList.getSelectedRow();
                        aryResult[0] = (String)tblList.getValueAt(intRowCount, 0);
                        aryResult[1] = (String)tblList.getValueAt(intRowCount, 1);
                        aryResult[2] = (String)tblList.getValueAt(intRowCount, 2);
                        aryResult[3] = (String)tblList.getValueAt(intRowCount, 3);
                        aryResult[4] = (String)tblList.getValueAt(intRowCount, 4);
                        aryResult[5] = (String)tblList.getValueAt(intRowCount, 5);
                        aryResult[6] = (String)tblList.getValueAt(intRowCount, 6);
                        aryResult[7] = (String)tblList.getValueAt(intRowCount, 7);

                        isOK = true;

                        // 다중 선택하여 품목을 Add하는 경우
                        addItemVec = new Vector();
                        addItemVec.addElement( aryResult );

                        disposeScreen();
                    }
                }
            });

            tblList.addMouseListener(new MouseAdapter()
            {
                public void mouseReleased(MouseEvent e)
                {
                    intRowCount = tblList.getSelectedRow();
                    aryResult[0] = (String)tblList.getValueAt(intRowCount, 0);
                    aryResult[1] = (String)tblList.getValueAt(intRowCount, 1);
                    aryResult[2] = (String)tblList.getValueAt(intRowCount, 2);
                    aryResult[3] = (String)tblList.getValueAt(intRowCount, 3);
                    aryResult[4] = (String)tblList.getValueAt(intRowCount, 4);
                    aryResult[5] = (String)tblList.getValueAt(intRowCount, 5);
                    aryResult[6] = (String)tblList.getValueAt(intRowCount, 6);
                    aryResult[7] = (String)tblList.getValueAt(intRowCount, 7);
                }
            });

            TableColumnModel columnModel = tblList.getColumnModel();
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

            centerPanel.add(labelPane, BorderLayout.NORTH);
            centerPanel.add(ps, BorderLayout.CENTER);
            this.getContentPane().add(centerPanel, BorderLayout.CENTER);

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00311")/*적용*/, appReg.getImageIcon("okIcon"));
            okBtn.setFont(FontList.defaultFont);
            okBtn.setActionCommand("OK");
            okBtn.addActionListener(btnListener);
            okBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(okBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(closeBtn);

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private Vector getSearchData(String item, String desc)
    {

        Vector vecResult = new Vector();
        try
        {
Kogger.debug(getClass(), ">>> item : " + item);

            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(item.length() == 0 && desc.length() == 0) {
//                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
//                mbox.setModal(true);
//                mbox.setVisible(true);
//
//                itemCodeTfl.requestFocus();
            }
            else
            {
                // 부품번호 검색조건 체크
                if ( !item.equals("") )
                {
                    // 부품명이 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( desc.equals("") || (desc.trim().length() > 0 && desc.trim().length() < 4) )
                    {
                        if ( item.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( item.indexOf("*") == -1 && item.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( item.indexOf("*") > 0 && item.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                // 부품명 검색조건 체크
                if ( !desc.equals("") )
                {
                    // 부픔번호가 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( item.equals("") || (item.trim().length() > 0 && item.trim().length() < 4) )
                    {
                        if ( desc.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( desc.indexOf("*") == -1 && desc.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( desc.indexOf("*") > 0 && desc.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                Hashtable inputParams = new Hashtable();
                inputParams.put("itemCode", item);
                inputParams.put("description", desc);
                inputParams.put("creator", "");
                inputParams.put("createFromDate", "");
                inputParams.put("createToDate", "");
                inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());

                if (titleStr.equals("GeneralBOM"))
                {
                    inputParams.put("bomAllowed", "Y");
                    inputParams.put("bomFlag", "N");
                }
                else
                {
                    inputParams.put("bomAllowed", "Y");
                    inputParams.put("bomFlag", "");
                }

                String viewUnit = "";
                Vector queryResult = new Vector();
                queryResult = KETBomHelper.service.searchItem(inputParams);

                for(int i=0; i<queryResult.size(); i++)
                {
                    Hashtable hasSearchItemResult = new Hashtable();
                    hasSearchItemResult = (Hashtable)queryResult.elementAt(i);

                    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
                    viewUnit = bean.getUnitDisplayValue((String)hasSearchItemResult.get("defaultunit"));

                    vecResult.addElement(new BOMChildItemSearchData(hasSearchItemResult.get("itemCode").toString().trim(),
                                                                        (String)hasSearchItemResult.get("description"),
                                                                        (String)hasSearchItemResult.get("defaultunit"),
                                                                        "", //(String)hasSearchItemResult.get("createdBy"),
                                                                        "", //strCreatedDate,
                                                                        (String)hasSearchItemResult.get("status"),
                                                                        (String)hasSearchItemResult.get("statusKr"),
                                                                        viewUnit,
                                                                        (String)hasSearchItemResult.get("version")
                    ));
                }
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
        return vecResult;
    }

    private Vector getSearchRefData(String item, String desc)
    {

        Vector vecResult = new Vector();
        Vector resultList = new Vector();

        DBConnectionManager resource = null;
        Connection connection = null;
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(item.length() == 0 && desc.length() == 0) {
//                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
//                mbox.setModal(true);
//                mbox.setVisible(true);
//
//                itemCodeTfl.requestFocus();
            }
            else
            {
                // 부품번호 검색조건 체크
                if ( !item.equals("") )
                {
                    // 부품명이 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( desc.equals("") || (desc.trim().length() > 0 && desc.trim().length() < 4) )
                    {
                        if ( item.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( item.indexOf("*") == -1 && item.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( item.indexOf("*") > 0 && item.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                // 부품명 검색조건 체크
                if ( !desc.equals("") )
                {
                    // 부픔번호가 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( item.equals("") || (item.trim().length() > 0 && item.trim().length() < 4) )
                    {
                        if ( desc.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( desc.indexOf("*") == -1 && desc.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( desc.indexOf("*") > 0 && desc.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                resource = DBConnectionManager.getInstance();
                connection = resource.getConnection(appReg.getString("plm"));

                BOMSearchDao dao = new BOMSearchDao();
                dao.searchDownwardList(connection, item, "");

                resultList = dao.getResultListVec();

                String strItemCode = "";
                String strCreatedDate = "";
                String versionStr = "";

                for(int i=0; i<resultList.size(); i++)
                {
                    Hashtable hasSearchItemResult = new Hashtable();
                    Vector itemVec = new Vector();
                    Vector version = new Vector();
                    Hashtable inputParams = new Hashtable();

                    hasSearchItemResult = (Hashtable)resultList.elementAt(i);

                    strItemCode = hasSearchItemResult.get("itemCode").toString().trim();

//                    if(hasSearchItemResult.get("createdDate").toString().length() > 10)
//                    {
//                        strCreatedDate = hasSearchItemResult.get("createdDate").toString().substring(0,10);
//                    }
//                    else
//                    {
//                        strCreatedDate = hasSearchItemResult.get("createdDate").toString();
//                    }

//                    itemVec.addElement(strItemCode);
//                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
//                    inputParams.put("itemCode", itemVec);
//
//                    version = KETBomHelper.service.getItemVersion(inputParams);
//                    versionStr = (String) version.elementAt(0);
//                    if ( versionStr.indexOf("@") > 0 && versionStr.indexOf(".") > 0 ) {
//                        versionStr =  versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("."));
//                    }

                    String viewUnit = "";
                    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
                    viewUnit = bean.getUnitDisplayValue((String)hasSearchItemResult.get("defaultunit"));

                    vecResult.addElement(new BOMChildItemSearchData(strItemCode,
                                                                        (String)hasSearchItemResult.get("description"),
                                                                        (String)hasSearchItemResult.get("defaultunit"),
                                                                        "", //(String)hasSearchItemResult.get("createdBy"),
                                                                        "", //strCreatedDate,
                                                                        (String)hasSearchItemResult.get("status"),
                                                                        (String)hasSearchItemResult.get("statusKr"),
                                                                        viewUnit,
                                                                        (String)hasSearchItemResult.get("version")
                    ));
                }
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        finally
        {
            if(resource != null)
            {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return vecResult;
    }

    private Vector getSearchCopyChildData()
    {
        Vector vecResult = new Vector();
        Vector resultList = new Vector();

        String strItemCode = itemCodeTfl.getText() == null ? "" : itemCodeTfl.getText().trim();
        String strDesc = descTfl.getText() == null ? "" : descTfl.getText().trim();

        DBConnectionManager resource = null;
        Connection connection = null;
        try
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(strItemCode.length() == 0 && strDesc.length() == 0) {
//                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
//                mbox.setModal(true);
//                mbox.setVisible(true);
//
//                itemCodeTfl.requestFocus();
            }
            else
            {
                // 부품번호 검색조건 체크
                if ( !strItemCode.equals("") )
                {
                    // 부품명이 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( strDesc.equals("") || (strDesc.trim().length() > 0 && strDesc.trim().length() < 4) )
                    {
                        if ( strItemCode.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( strItemCode.indexOf("*") == -1 && strItemCode.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( strItemCode.indexOf("*") > 0 && strItemCode.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                // 부품명 검색조건 체크
                if ( !strDesc.equals("") )
                {
                    // 부픔번호가 입력되지 않았거나 4자리 미만인경우 만 체크함
                    if ( strItemCode.equals("") || (strItemCode.trim().length() > 0 && strItemCode.trim().length() < 4) )
                    {
                        if ( strDesc.indexOf("*") == 0 )         // 검색조건 '*'로 시작한 경우
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( strDesc.indexOf("*") == -1 && strDesc.length() < 3 )     // '*'를 입력하지 않은 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;

                        }
                        else if ( strDesc.indexOf("*") > 0 && strDesc.length() < 4 )         // '*'를 입력한 경우 4자리인지 검사
                        {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                            mbox.setModal(true);
                            mbox.setVisible(true);
                            return vecResult;
                        }
                    }
                }

                resource = DBConnectionManager.getInstance();
                connection = resource.getConnection(appReg.getString("plm"));

                BOMSearchDao dao = new BOMSearchDao();
                dao.searchDownwardList(connection, strItemCode, strDesc);

                resultList = dao.getResultListVec();

                String strCreatedDate = "";
                String versionStr = "";

                for(int i=0; i<resultList.size(); i++)
                {
                    Hashtable hasSearchItemResult = new Hashtable();
                    Vector itemVec = new Vector();
                    Vector version = new Vector();
                    Hashtable inputParams = new Hashtable();

                    hasSearchItemResult = (Hashtable)resultList.elementAt(i);

                    strItemCode = hasSearchItemResult.get("itemCode").toString().trim();

//                    if(hasSearchItemResult.get("createdDate").toString().length() > 10)
//                    {
//                        strCreatedDate = hasSearchItemResult.get("createdDate").toString().substring(0,10);
//                    }
//                    else
//                    {
//                        strCreatedDate = hasSearchItemResult.get("createdDate").toString();
//                    }

//                    itemVec.addElement(strItemCode);
//                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
//                    inputParams.put("itemCode", itemVec);
//
//                    version = KETBomHelper.service.getItemVersion(inputParams);
//                    versionStr = (String) version.elementAt(0);
//                    if ( versionStr.indexOf("@") > 0 && versionStr.indexOf(".") > 0 ) {
//                        versionStr =  versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("."));
//                    }

                    String viewUnit = "";
                    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
                    viewUnit = bean.getUnitDisplayValue((String)hasSearchItemResult.get("defaultunit"));

                    vecResult.addElement(new BOMChildItemSearchData(strItemCode,
                                                                        (String)hasSearchItemResult.get("description"),
                                                                        (String)hasSearchItemResult.get("defaultunit"),
                                                                        "", //(String)hasSearchItemResult.get("createdBy"),
                                                                        "", //strCreatedDate,
                                                                        (String)hasSearchItemResult.get("status"),
                                                                        (String)hasSearchItemResult.get("statusKr"),
                                                                        viewUnit,
                                                                        (String)hasSearchItemResult.get("version")
                    ));
                }
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        finally
        {
            if(resource != null)
            {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return vecResult;
    }

    private void searchBtn_process()
    {
        String strItemCode = itemCodeTfl.getText() == null ? "" : itemCodeTfl.getText().trim();
        String strDesc = descTfl.getText() == null ? "" : descTfl.getText().trim();

        if(strItemCode.equals("") && strDesc.equals(""))
        {
            MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);

            itemCodeTfl.requestFocus();
        }
        else
        {
            if(titleStr.equals("ReferenceBOM"))
            {
                vecModel = getSearchRefData(strItemCode, strDesc);
            }
            else if(titleStr.equals("CopyChild"))
            {
                vecModel = getSearchCopyChildData();
            }
            else
            {
                vecModel = getSearchData(strItemCode, strDesc);
            }

            searchResultLbl.setText("" + vecModel.size());

            dataList = new BOMChildItemSearchTableData(vecModel);

            sorter = new TableSorter(dataList);
            tblList.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblList);
            tblList.repaint();
        }
    }

    private void clearBtn_process()
    {
        itemCodeTfl.setText("");
        descTfl.setText("");
    }

    private void okBtn_process()
    {
        if(intRowCount == -1)
        {
            MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        else
        {
            isOK = true;

            // 다중 선택해서 자품목 추가시 사용
            if( titleStr != null && titleStr.equalsIgnoreCase("Add") )
            {
                int selectedRows[] = tblList.getSelectedRows();
                addItemVec = new Vector();

                for( int i = 0; i < selectedRows.length; i++ )
                {
                    String rowData[] = new String[8];

Kogger.debug(getClass(),  "Selected Row Number : " + selectedRows[i] );

                    rowData[0] = (String)tblList.getValueAt( selectedRows[i], 0 );
                    rowData[1] = (String)tblList.getValueAt( selectedRows[i], 1 );
                    rowData[2] = (String)tblList.getValueAt( selectedRows[i], 2 );
                    rowData[3] = (String)tblList.getValueAt( selectedRows[i], 3 );
                    rowData[4] = (String)tblList.getValueAt( selectedRows[i], 4 );
                    rowData[5] = (String)tblList.getValueAt( selectedRows[i], 5 );
                    rowData[6] = (String)tblList.getValueAt( selectedRows[i], 6 );
                    rowData[7] = (String)tblList.getValueAt( selectedRows[i], 7 );

                    addItemVec.addElement( rowData );
                }
            }

            disposeScreen();
        }
    }

    public String[] getSelectedColumnData()
    {
        return aryResult;
    }

    // 다중 선택해서 자품목 추가시 사용
    public Vector getSelectedRows()
    {
        return addItemVec;
    }

    public boolean getOK()
    {
        return isOK;
    }

    private void disposeScreen()
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

    public void lostOwnership(Clipboard clip, Transferable transferable)
    {
    }

}
