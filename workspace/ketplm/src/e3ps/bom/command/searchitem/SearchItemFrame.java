package e3ps.bom.command.searchitem;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.managecoworker.FindUserDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class SearchItemFrame extends JFrame implements Runnable, ClipboardOwner
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;
    private JTable m_table = new JTable();
    private SearchItemTableData m_data;
    private TableSorter sorter;
    private Vector resultList = new Vector();
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JFrame desktop;
    Registry appReg;

    private JTextField itemCodeTfl, descTfl;
    private JLabel searchResultLbl;
    private JButton searchBtn, clearBtn, viewBOMBtn, closeBtn;
    private JRadioButton downwardExplosionRdo, upwardExplosionRdo;

    private Clipboard clipboard;

    String title = "";
    private int intRowCount = -1;
    String titleTmp = "";

    class BtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("UserSearch")){
                userSearchData();
            } else if (e.getActionCommand().equals("Search")) {
                searchData();
            } else if (e.getActionCommand().equals("Clear")) {
                clearData();
            } else if (e.getActionCommand().equals("View BOM"))     {
                viewBOM();
            } else if (e.getActionCommand().equals("Close")) {
                disposeScreen();
            }
        }
    }

    public SearchItemFrame(JFrame frame, AbstractAIFUIApplication app, String title)
    {
        super(title);

        desktop = frame;
        this.app = app;
        this.title = title;

        appReg = Registry.getRegistry(app);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disposeScreen();
            }
        });

        setTitle(title);
        setSize(650,450);
        setResizable(false);

        run();
    }

    public void run()
    {
        try {
            validate();

            jInit();

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void jInit() throws Exception
    {
        try {
            Registry appReg = Registry.getRegistry(app);

            btnListener = new BtnListener();

            // Search Condition Panel /////////////////////////////////////////////////
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/, 0, 0, FontList.defaultFont));
            topPanel.setFont(FontList.defaultFont);

            // 첫번째 검색 라인
            JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel itemCodeLbl = new JLabel("           " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
            itemCodeLbl.setFont(FontList.defaultFont);
            panel01.add(itemCodeLbl);

            itemCodeTfl = new JTextField(15);
            itemCodeTfl.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchData();
                }
            });

            itemCodeTfl.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent keyevent) {
                    char c = keyevent.getKeyChar();
                    int cCode = (int)c;

                    if ( cCode >= 97 && cCode <= 122 ) {
                        keyCheck = true;
                    }
                }
                public void keyReleased(KeyEvent keyevent) {
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

            JLabel descLbl = new JLabel("           " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
            descLbl.setFont(FontList.defaultFont);
            panel01.add(descLbl);

            descTfl = new JTextField(15);
            descTfl.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchData();
                }
            });

            descTfl.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent keyevent) {
                    char c = keyevent.getKeyChar();
                    int cCode = (int)c;

                    if ( cCode >= 97 && cCode <= 122 ) {
                        keyCheck = true;
                    }
                }
                public void keyReleased(KeyEvent keyevent) {
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
            panel01.add(descTfl);
            topPanel.add(panel01);

            // 검색 / 초기화 버튼 라인
            JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/, appReg.getImageIcon("searchitemIcon"));
            searchBtn.setFont(FontList.defaultFont);
            searchBtn.setActionCommand("Search");
            searchBtn.addActionListener(btnListener);
            searchBtn.setMargin(new Insets(0,5,0,5));
            panel03.add(searchBtn);

            clearBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00332")/*초기화*/, appReg.getImageIcon("clearIcon"));
            clearBtn.setFont(FontList.defaultFont);
            clearBtn.setActionCommand("Clear");
            clearBtn.addActionListener(btnListener);
            clearBtn.setMargin(new Insets(0,5,0,5));
            panel03.add(clearBtn);

            topPanel.add(panel03);
            this.getContentPane().add(topPanel, BorderLayout.NORTH);

            // Search Result Panel /////////////////////////////////////////////////

            JPanel centerPanel = new JPanel(new BorderLayout());
            JPanel labelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel cmtLbl = new JLabel("   " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00054")/*검색결과*/ + " : ");
            cmtLbl.setFont(FontList.defaultFont);

            searchResultLbl = new JLabel("0");
            searchResultLbl.setFont(FontList.defaultFont);
            searchResultLbl.setForeground(Color.red);
            JLabel cmt2Lbl = new JLabel(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00051")/*개*/ + "      ");
            cmt2Lbl.setFont(FontList.defaultFont);

            labelPane.add(cmtLbl);
            labelPane.add(searchResultLbl);
            labelPane.add(cmt2Lbl);

            m_data = new SearchItemTableData();
            m_table.setAutoCreateColumnsFromModel(false);
            m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            sorter = new TableSorter(m_data);
            m_table.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(m_table);
            m_table.repaint();

            for(int k = 0; k < SearchItemTableData.clmModelData.length; k++) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SearchItemTableData.clmModelData[k].intAlignment);
                TableColumn column = new TableColumn(k, SearchItemTableData.clmModelData[k].intWidth, renderer, null);
                m_table.addColumn(column);

                TableColumnModel columnModel = m_table.getColumnModel();
                TableColumn _column = columnModel.getColumn(k);
                _column.setCellRenderer(renderer);
            }

            JTableHeader headerModel = m_table.getTableHeader();
            headerModel.setUpdateTableInRealTime(false);

            JScrollPane ps = new JScrollPane();
            ps.getViewport().add(m_table);

            m_table.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                    StringSelection contents = new StringSelection(m_table.getValueAt(m_table.getSelectedRow(),m_table.getSelectedColumn()).toString());
                    clipboard.setContents(contents, contents);
                }

                public void mouseClicked(MouseEvent e) {
                    String itemStr = "";
                    String itemDescStr = "";

                    Vector<String> versionItemVec = new Vector<String>();
                    Vector version = new Vector();
                    Hashtable inputParams = new Hashtable();

                    if(e.getClickCount() >= 2) {
                        if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/) || downwardExplosionRdo.isSelected()) {

                            try {
                                if(isBomChildExist())    {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                                    viewBOMBtn.setEnabled(false);
                                    m_table.setEnabled(false);

                                    Vector vecOpenBom = new Vector();
                                    vecOpenBom.removeAllElements();

                                    itemStr = m_table.getValueAt(m_table.getSelectedRow(), 0) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 0).toString().trim();
                                    itemDescStr = m_table.getValueAt(m_table.getSelectedRow(), 1) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 1).toString().trim();

                                    BOMSearchDao dao = new BOMSearchDao();
                                    // 정전개 조회 할 버전을 가져온다
//                                    String strVer = dao.getItemVersionList(itemStr);
                                    // 정전개 조회
//                                    dao.downwardExplosionCurrentBom(itemStr, "", strVer);
                                    dao.downwardExplosionCurrentBom(itemStr, "");
                                    vecOpenBom = dao.getResultListVec();

                                    BOMAssyComponent rootComponent = new BOMAssyComponent(itemStr, true);
                                    rootComponent.setLevelInt(new Integer(1));
                                    rootComponent.setSeqInt(new Integer(1));
                                    rootComponent.setOpSeqInt(new Integer(1));
                                    rootComponent.setItemSeqInt(new Integer(10));
                                    rootComponent.setDescStr(itemDescStr);
                                    rootComponent.setSortOrderStr("0001");
                                    rootComponent.setUitStr(m_table.getValueAt(m_table.getSelectedRow(), 5) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 5).toString().trim());
                                    rootComponent.setStatusStr(m_table.getValueAt(m_table.getSelectedRow(), 6) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 6).toString().trim());
                                    rootComponent.setStatusKrStr(m_table.getValueAt(m_table.getSelectedRow(), 7) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 7).toString().trim());
                                    rootComponent.setIBAPartType(m_table.getValueAt(m_table.getSelectedRow(), 8) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 8).toString().trim());

                                    versionItemVec.addElement( itemStr);
                                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                                    inputParams.put("itemCode", versionItemVec);

                                    version = KETBomHelper.service.getItemVersion(inputParams);
                                    String versionStr = "";

                                    for(int k=0; k<version.size(); k++) {
                                        if(version.size() > 0) {
                                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                                            if(( itemStr).equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                                                rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                                            }
                                        }
                                    }

                                    if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && downwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/;
                                    } else if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && upwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/)) {
                                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/;
                                    }

                                    SearchBOMPanel searchBom = new SearchBOMPanel(desktop, app, (BOMRegisterApplicationPanel)app.getApplicationPanel(), titleTmp, rootComponent, vecOpenBom, rootComponent.getVersionStr().trim());

                                    viewBOMBtn.setEnabled(true);
                                    m_table.setEnabled(true);

                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                } else    {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                    MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("searchItem1"), "Error", 0);
                                    mbox.setModal(true);
                                    mbox.setVisible(true);
                                }
                            } catch (Exception ex) {
                                Kogger.error(getClass(), ex);
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        } else if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/) || upwardExplosionRdo.isSelected()) {
                            try {

                                if(isBomParentExist()) {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                                    viewBOMBtn.setEnabled(false);
                                    m_table.setEnabled(false);

                                    Vector vecOpenBom = new Vector();
                                    vecOpenBom.removeAllElements();

                                    itemStr = m_table.getValueAt(m_table.getSelectedRow(), 0) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 0).toString().trim();
                                    itemDescStr = m_table.getValueAt(m_table.getSelectedRow(), 1) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 1).toString().trim();

                                    BOMSearchDao dao = new BOMSearchDao();
                                    dao.upwardExplosionCurrentBom(itemStr);

                                    vecOpenBom = dao.getResultListVec();

                                    BOMAssyComponent rootComponent = new BOMAssyComponent(itemStr, true);
                                    rootComponent.setLevelInt(new Integer(1));
                                    rootComponent.setSeqInt(new Integer(1));
                                    rootComponent.setItemSeqInt(new Integer(10));
                                    rootComponent.setOpSeqInt(new Integer(1));
                                    rootComponent.setDescStr(itemDescStr);
                                    rootComponent.setSortOrderStr("0001");
                                    rootComponent.setUitStr(m_table.getValueAt(m_table.getSelectedRow(), 5) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 5).toString().trim());
                                    rootComponent.setStatusStr(m_table.getValueAt(m_table.getSelectedRow(), 6) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 6).toString().trim());
                                    rootComponent.setStatusKrStr(m_table.getValueAt(m_table.getSelectedRow(), 7) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 7).toString().trim());
                                    rootComponent.setIBAPartType(m_table.getValueAt(m_table.getSelectedRow(), 8) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 8).toString().trim());

                                    versionItemVec.addElement( itemStr);
                                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                                    inputParams.put("itemCode", versionItemVec);

                                    version = KETBomHelper.service.getItemVersion(inputParams);
                                    String versionStr = "";

                                    for(int k=0; k<version.size(); k++) {
                                        if(version.size() > 0) {
                                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                                            if(( itemStr).equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                                                rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                                            }
                                        }
                                    }

                                    if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && downwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/;
                                    } else if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && upwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/)) {
                                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/;
                                    }

                                    SearchBOMPanel searchBom = new SearchBOMPanel(desktop, app, (BOMRegisterApplicationPanel)app.getApplicationPanel(), titleTmp, rootComponent, vecOpenBom, rootComponent.getVersionStr().trim());

                                    viewBOMBtn.setEnabled(true);
                                    m_table.setEnabled(true);

                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                } else     {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                    MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("searchItem1"), "Error", 0);
                                    mbox.setModal(true);
                                    mbox.setVisible(true);
                                }
                            } catch(Exception ex) {
                                Kogger.error(getClass(), ex);
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                    }
                }
            });

            // 불필요한 컬럼 숨기기
            TableColumnModel columnModel = m_table.getColumnModel();
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

            centerPanel.add(labelPane, BorderLayout.NORTH);
            centerPanel.add(ps, BorderLayout.CENTER);
            this.getContentPane().add(centerPanel, BorderLayout.CENTER);

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            downwardExplosionRdo = new JRadioButton();
            downwardExplosionRdo.setText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/);
            downwardExplosionRdo.setFont(FontList.defaultFont);
            upwardExplosionRdo = new JRadioButton();
            upwardExplosionRdo.setText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/);
            upwardExplosionRdo.setFont(FontList.defaultFont);
            ButtonGroup rdoGroup = new ButtonGroup();
            rdoGroup.add(downwardExplosionRdo);
            rdoGroup.add(upwardExplosionRdo);

            btnFlowPanel.add(downwardExplosionRdo);
            btnFlowPanel.add(upwardExplosionRdo);

            viewBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00031")/*BOM 조회*/, appReg.getImageIcon("bomdetailsIcon"));
            viewBOMBtn.setFont(FontList.defaultFont);
            viewBOMBtn.setActionCommand("View BOM");
            viewBOMBtn.addActionListener(btnListener);
            viewBOMBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(viewBOMBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(closeBtn);

            if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/)) {
                downwardExplosionRdo.setSelected(true);
            } else if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                downwardExplosionRdo.setSelected(true);
                downwardExplosionRdo.setEnabled(false);
                upwardExplosionRdo.setEnabled(false);
            } else if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/)) {
                downwardExplosionRdo.setEnabled(false);
                upwardExplosionRdo.setSelected(true);
                upwardExplosionRdo.setEnabled(false);
            } else {
                downwardExplosionRdo.setVisible(false);
                upwardExplosionRdo.setVisible(false);
                viewBOMBtn.setVisible(false);
            }

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void userSearchData()
    {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            FindUserDialog dlgFindUser = null;

            //shin...
            //dlgFindUser = new FindUserDialog((createdByTfl.getText() == null ? "" : createdByTfl.getText().trim()), "", app, "Search User");
            dlgFindUser = new FindUserDialog("", "", app, "Search User");

            String aryCreatorData[] = new String[4];

            if(dlgFindUser.getOK() == true) {
                aryCreatorData = dlgFindUser.getSelectedColumnData();

                //shin...
                //createdByTfl.setText(aryCreatorData[1].trim());
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception e) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), e);
        }
    }

    private void searchData()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String strItemCode = itemCodeTfl.getText() == null ? "" : itemCodeTfl.getText().trim();
        String strDesc = descTfl.getText() == null ? "" : descTfl.getText().trim();

        if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/)) {

            if(strItemCode.equals("") && strDesc.equals("") ) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(this.desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00346")/*하나 이상의 검색조건을 입력하세요.*/, "Input Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
        }

        Vector vecModel = new Vector();

        //shin...
//         if(title.equals("부품검색")) {
             vecModel = getSearchItem(strItemCode, strDesc);
//        } else {
//            vecModel = getSearchData(strItemCode, strDesc);
//        }

        searchResultLbl.setText("" + vecModel.size());

        m_data = new SearchItemTableData(vecModel);

        sorter = new TableSorter(m_data);
        m_table.setModel(sorter);
        sorter.addMouseListenerToHeaderInTable(m_table);
        m_table.repaint();

        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    // 부품 검색
    private Vector getSearchItem(String item, String desc)
    {
        Vector vecResult = new Vector();
        try {

Kogger.debug(getClass(), "============ getSearchItem() ===============");
Kogger.debug(getClass(), ">>> item : " + item);
Kogger.debug(getClass(), ">>> BOMBasicInfoPool.getOrgCode().trim() : " + BOMBasicInfoPool.getOrgCode().trim());

            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(item.equals("") && desc.equals(""))
            {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);

                itemCodeTfl.requestFocus();
            } else {
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
                inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/)) {
                    inputParams.put("bomAllowed", "");
                    inputParams.put("bomFlag", "");
                } else {
                    inputParams.put("bomAllowed", "");
                    inputParams.put("bomFlag", "Y");
                }

                Vector queryResult = new Vector();
                queryResult = KETBomHelper.service.searchItem(inputParams);

                KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

                String strItemCode = "";
                for(int i=0; i<queryResult.size(); i++) {
                    Hashtable hasSearchItemResult = new Hashtable();
                    hasSearchItemResult = (Hashtable)queryResult.elementAt(i);
//Kogger.debug(getClass(), "@@@@ hasSearchItemResult  " + hasSearchItemResult);
                    strItemCode = hasSearchItemResult.get("itemCode").toString().trim();

                    vecResult.addElement(new SearchItemData(strItemCode,
                                                                        (String)hasSearchItemResult.get("description"),
                                                                        (String)hasSearchItemResult.get("defaultunit"),
                                                                        "", //(String)hasSearchItemResult.get("createdBy"),
                                                                        "", //strCreatedDate,
                                                                        (String)hasSearchItemResult.get("status"),
                                                                        (String)hasSearchItemResult.get("statusKr"),
                                                                        (String)hasSearchItemResult.get("version"),
                                                                        bean.getUnitDisplayValue((String)hasSearchItemResult.get("defaultunit")),
                                                                        (String)hasSearchItemResult.get("typeValue")
                    ));
                }
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);

            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        return vecResult;
    }

    private Vector getSearchData(String item, String desc)
    {

        Vector vecResult = new Vector();
        Vector resultList = new Vector();

        DBConnectionManager resource = null;
        Connection connection = null;
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(item.equals("") && desc.equals(""))
            {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("childItem"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);

                itemCodeTfl.requestFocus();
            } else {
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
                KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

                if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/) || downwardExplosionRdo.isSelected()) {
                    dao.searchDownwardList(connection, item, desc);
                } else if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/) || upwardExplosionRdo.isSelected()) {
                    dao.searchUpwardList(connection, item, desc);
                }

                resultList = dao.getResultListVec();

                String strItemCode = "";
                String strCreatedDate = "";
                String versionStr = "";

                for(int i=0; i<resultList.size(); i++) {
                    Hashtable hasSearchItemResult = new Hashtable();
                    Vector itemVec = new Vector();
                    Vector version = new Vector();
                    Hashtable inputParams = new Hashtable();

                    hasSearchItemResult = (Hashtable)resultList.elementAt(i);

                    strItemCode = hasSearchItemResult.get("itemCode").toString().trim();

                    if(hasSearchItemResult.get("createdDate").toString().length() > 10) {
                        strCreatedDate = hasSearchItemResult.get("createdDate").toString().substring(0,10);
                    } else {
                        strCreatedDate = hasSearchItemResult.get("createdDate").toString();
                    }

                    itemVec.addElement(strItemCode);
                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    inputParams.put("itemCode", itemVec);

                    version = KETBomHelper.service.getItemVersion(inputParams);
                    versionStr = (String) version.elementAt(0);
                    if ( versionStr.indexOf("@") > 0 && versionStr.indexOf(".") > 0 ) {
                        versionStr =  versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("."));
                    }

                    vecResult.addElement(new SearchItemData(hasSearchItemResult.get("itemCode").toString().trim(),
                                                                        (String)hasSearchItemResult.get("description"),
                                                                        (String)hasSearchItemResult.get("defaultunit"),
                                                                        (String)hasSearchItemResult.get("createdBy"),
                                                                        strCreatedDate,
                                                                        (String)hasSearchItemResult.get("status"),
                                                                        (String)hasSearchItemResult.get("statusKr"),
                                                                        versionStr,
                                                                        bean.getUnitDisplayValue((String)hasSearchItemResult.get("defaultunit")),
                                                                        (String)hasSearchItemResult.get("typeValue")
                    ));
                }
            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        } finally {
            if(resource != null) {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return vecResult;
    }

    private void clearData()
    {
        itemCodeTfl.setText("");
        descTfl.setText("");
    }

    private void viewBOM()
    {
        String itemStr = "";
        String itemDescStr = "";
        Vector versionItemVec = new Vector();
        Vector version = new Vector();
        Hashtable inputParams = new Hashtable();

        try    {

            if(m_table.getSelectedRow() == -1) {
                MessageBox mbox = new MessageBox(this.desktop, messageRegistry.getString("selectRow12"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }

            if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/) || downwardExplosionRdo.isSelected()) {
Kogger.debug(getClass(), "------------- 정전개 ------------------");

                if(isBomChildExist()) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    viewBOMBtn.setEnabled(false);
                    m_table.setEnabled(false);

                    Vector vecOpenBom = new Vector();
                    vecOpenBom.removeAllElements();

                    itemStr = m_table.getValueAt(m_table.getSelectedRow(), 0) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 0).toString().trim();
                    itemDescStr = m_table.getValueAt(m_table.getSelectedRow(), 1) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 1).toString().trim();

                    BOMSearchDao dao = new BOMSearchDao();
                    // 정전개 조회 할 버전을 가져온다
//                    String strVer = dao.getItemVersionList(itemStr);
                    // 정전개 조회
//                    dao.downwardExplosionCurrentBom(itemStr, "", strVer);
                    dao.downwardExplosionCurrentBom(itemStr, "");
                    vecOpenBom = dao.getResultListVec();
Kogger.debug(getClass(), "@@@@@ vecOpenBom [SearchItemFrame] : " + vecOpenBom);

                    BOMAssyComponent rootComponent = new BOMAssyComponent(itemStr, true);
                    rootComponent.setLevelInt(new Integer(1));
                    rootComponent.setSeqInt(new Integer(0));
                    rootComponent.setOpSeqInt(new Integer(1));
                    rootComponent.setItemSeqInt(new Integer(10));
                    rootComponent.setDescStr(itemDescStr);
                    rootComponent.setSortOrderStr("0001");
                    rootComponent.setUitStr(m_table.getValueAt(m_table.getSelectedRow(), 5) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 5).toString().trim());
                    rootComponent.setStatusStr(m_table.getValueAt(m_table.getSelectedRow(), 6) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 6).toString().trim());
                    rootComponent.setStatusKrStr(m_table.getValueAt(m_table.getSelectedRow(), 7) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 7).toString().trim());
                    rootComponent.setIBAPartType(m_table.getValueAt(m_table.getSelectedRow(), 8) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 8).toString().trim());

                    versionItemVec.addElement( itemStr );
                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    inputParams.put("itemCode", versionItemVec);

                    version = KETBomHelper.service.getItemVersion(inputParams);
                    String versionStr = "";

                    for(int k=0; k<version.size(); k++) {
                        if(version.size() > 0) {
                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                            if(( itemStr).equals(versionStr.substring(0, versionStr.indexOf("@"))))
                            {
                                rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                            }
                        }
                    }

                    if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && downwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/;
                    } else if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && upwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/)) {
                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/;
                    }

                    setTitle(titleTmp);

                    SearchBOMPanel searchBom = new SearchBOMPanel(desktop, app, (BOMRegisterApplicationPanel)app.getApplicationPanel(), titleTmp, rootComponent, vecOpenBom, rootComponent.getVersionStr().trim());

                    viewBOMBtn.setEnabled(true);
                    m_table.setEnabled(true);

                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else    {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(this.desktop, messageRegistry.getString("searchItem1"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
            } else if(title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/) || upwardExplosionRdo.isSelected()) {
Kogger.debug(getClass(), "------------- 역전개 ------------------");
                if(isBomParentExist()) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    viewBOMBtn.setEnabled(false);
                    m_table.setEnabled(false);

                    Vector vecOpenBom = new Vector();
                    vecOpenBom.removeAllElements();

                    itemStr = m_table.getValueAt(m_table.getSelectedRow(), 0) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 0).toString().trim();
                    itemDescStr = m_table.getValueAt(m_table.getSelectedRow(), 1) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 1).toString().trim();

                    BOMSearchDao dao = new BOMSearchDao();
                    dao.upwardExplosionCurrentBom(itemStr);

                    vecOpenBom = dao.getResultListVec();

                    BOMAssyComponent rootComponent = new BOMAssyComponent(itemStr, true);
                    rootComponent.setLevelInt(new Integer(1));
                    rootComponent.setSeqInt(new Integer(1));
                    rootComponent.setOpSeqInt(new Integer(1));
                    rootComponent.setItemSeqInt(new Integer(10));
                    rootComponent.setDescStr(itemDescStr);
                    rootComponent.setSortOrderStr("0001");
                    rootComponent.setUitStr(m_table.getValueAt(m_table.getSelectedRow(), 5) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 5).toString().trim());
                    rootComponent.setStatusStr(m_table.getValueAt(m_table.getSelectedRow(), 6) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 6).toString().trim());
                    rootComponent.setStatusKrStr(m_table.getValueAt(m_table.getSelectedRow(), 7) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 7).toString().trim());
                    rootComponent.setIBAPartType(m_table.getValueAt(m_table.getSelectedRow(), 8) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 8).toString().trim());

                    versionItemVec.addElement( itemStr);
                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    inputParams.put("itemCode", versionItemVec);

                    version = KETBomHelper.service.getItemVersion(inputParams);
                    String versionStr = "";

                    for(int k=0; k<version.size(); k++) {
                        if(version.size() > 0)     {
                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                            if(( itemStr).equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                                rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                            }
                        }
                    }

                    if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && downwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/)) {
                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/;
                    } else if((title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/) && upwardExplosionRdo.isSelected()) || title.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/)) {
                        titleTmp = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/;
                    }

                    SearchBOMPanel searchBom = new SearchBOMPanel(desktop, app, (BOMRegisterApplicationPanel)app.getApplicationPanel(), titleTmp, rootComponent, vecOpenBom, rootComponent.getVersionStr().trim());

                    viewBOMBtn.setEnabled(true);
                    m_table.setEnabled(true);

                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else    {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(this.desktop, messageRegistry.getString("searchItem"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
            }
        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void disposeScreen()
    {
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    public boolean isBomChildExist()
    {
        boolean isBomChildFlag = false;
        String itemCodeStr = "";
        intRowCount = m_table.getSelectedRow();
        itemCodeStr = (String)m_table.getValueAt(intRowCount, 0);

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DBConnectionManager resource = null;
        Connection connection = null;
        try    {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.isBomChildExist(connection, itemCodeStr);

            long resultCount = dao.getDataCount();

            if(resultCount > 0) {
                isBomChildFlag = true;
            }
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        } finally {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if(resource != null) {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return isBomChildFlag;
    }

    public boolean isBomParentExist()
    {
        boolean isBomParentFlag = false;
        String itemCodeStr = "";
        intRowCount = m_table.getSelectedRow();
        itemCodeStr = (String)m_table.getValueAt(intRowCount, 0);

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DBConnectionManager resource = null;
        Connection connection = null;
        try {

            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.isBomParentExist(connection, itemCodeStr);

            long resultCount = dao.getDataCount();

            if(resultCount > 0) {
                isBomParentFlag = true;
            }
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        } finally {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if(resource != null) {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return isBomParentFlag;
    }

    public void lostOwnership(Clipboard clip, Transferable transferable)  //shin...이게 없으면 에러가 남
    {
    }
}
