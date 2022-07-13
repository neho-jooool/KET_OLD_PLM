package e3ps.bom.command.mybom;

import java.awt.Color;
import java.awt.Container;
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.managecoworker.FindUserDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMStateCodeData;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.common.util.UserData;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.DateButton;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class MyBomPanel extends JPanel implements ClipboardOwner
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    BOMRegisterApplicationPanel pnl;
    private BOMRegisterDesktop desktop;
    private TableSorter sorter;

    JButton searchBtn, viewBOMBtn, removeBtn, closeBtn, serachUserBtn, delUserBtn;
    JLabel itemCodeLbl, itemNameLbl, stateLbl, startDateLbl, endDateLbl, creatorLbl;
    JTextField itemCodeTfl, itemNameTfl, creatorTfl;
    JComboBox stateCmb;
    DateButton startDateBtn, endDateBtn;

    Registry appReg;
    AbstractAIFUIApplication aifapp;

    BOMTableData bomDataTbl;

    Dimension dimLabel = new Dimension(150, 20);

    JTable tblPublic = new JTable();
    Dimension dimPublicTable = new Dimension(700, 250);
    JScrollPane spPublic = new JScrollPane();
    JPanel pnlBottom = new JPanel(new FlowLayout());
    JPanel pnlSearch = new JPanel(new FlowLayout());

    int intSelectedRow;
    MyBOMFrame myBomFrame;

    Vector vecPublic = new Vector();
    Vector vecStatus = new Vector();
    LinkedHashMap htblStatus = new LinkedHashMap();

    boolean isAdmin = false;
    boolean isNoSelected = false;
    WTUser loginUser;
    String createdId = "";

    private Clipboard clipboard;

    boolean keyCheck = false;

    public MyBomPanel(MyBOMFrame mbf, AbstractAIFUIApplication app, Vector vecResult) {
        myBomFrame = mbf;
        aifapp = app;
        appReg = Registry.getRegistry(aifapp);

        // 삭제 버튼 감추기 위해 필요한 변수들
        try {
            isAdmin = CommonUtil.isAdmin();
            loginUser = (WTUser)SessionHelper.manager.getPrincipal();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }

        setStatusVec();

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        vecPublic = vecResult;

        bomDataTbl = new BOMTableData(vecPublic);

        setGUI();
        setEvent();
    }

    private void setGUI() {
        Container c = this;
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        pnlBottom.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        pnlSearch.setLayout(new BoxLayout(pnlSearch, BoxLayout.Y_AXIS));
        pnlSearch.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00056")/*검색조건*/, 0, 0, FontList.defaultFont));

        tblPublic.setAutoCreateColumnsFromModel(false);
        tblPublic.setPreferredScrollableViewportSize(dimPublicTable);
        tblPublic.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPublic.setAutoResizeMode(new JTable().AUTO_RESIZE_OFF);

        sorter = new TableSorter(bomDataTbl);
        tblPublic.setModel(sorter);
        sorter.addMouseListenerToHeaderInTable(tblPublic);
        tblPublic.repaint();
        tblPublic.getTableHeader().setReorderingAllowed(false);

        JTableHeader headerPublic = tblPublic.getTableHeader();
        headerPublic.setUpdateTableInRealTime(false);

        for (int idx=0; idx < headerPublic.getColumnModel().getColumnCount(); idx++) {
            BOMTableRender tRenderer = new BOMTableRender();
            TableColumn tableColumn = headerPublic.getColumnModel().getColumn(idx);
            tableColumn.setCellRenderer(tRenderer);
        }

        for (int k = 0; k < BOMTableData.clmBOMData.length; k++)     {
            BOMTableRender tRenderer = new BOMTableRender();
            tRenderer.setHorizontalAlignment(BOMTableData.clmBOMData[k].intAlignment);
            TableColumn column = new TableColumn(k, BOMTableData.clmBOMData[k].intWidth, tRenderer, null);
            if (k == 5) {
                column.setCellEditor(new BOMTableCellEditor(new JComboBox()));
            }
            tblPublic.addColumn(column);
        }

        // oid 정보 숨기기
        TableColumnModel columnModel = tblPublic.getColumnModel();
        TableColumn column = columnModel.getColumn(6);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        spPublic.getViewport().add(tblPublic);
        c.add(spPublic);

        JPanel searchPnl01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemCodeLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
        itemCodeLbl.setPreferredSize(new Dimension(85,21));
        itemCodeLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemCodeLbl.setFont(FontList.defaultFont);
        searchPnl01.add(itemCodeLbl);

        itemCodeTfl = new JTextField(11);
        itemCodeTfl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if ( cCode >= 97 && cCode <= 122 ) {
                    keyCheck = true;
                }else if (cCode == 10) { // 엔터 시 검색수행
                    searchBtn_process();
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
        searchPnl01.add(itemCodeTfl);

        itemNameLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
        itemNameLbl.setPreferredSize(new Dimension(88,21));
        itemNameLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemNameLbl.setFont(FontList.defaultFont);
        searchPnl01.add(itemNameLbl);

        itemNameTfl = new JTextField(11);
        itemNameTfl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if ( cCode >= 97 && cCode <= 122 ) {
                    keyCheck = true;
                }else if (cCode == 10) { // 엔터 시 검색수행
                    searchBtn_process();
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
        searchPnl01.add(itemNameTfl);

        creatorLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00287")/*작성자*/ + " : ");
        creatorLbl.setPreferredSize(new Dimension(80,21));
        creatorLbl.setHorizontalAlignment(JLabel.RIGHT);
        creatorLbl.setFont(FontList.defaultFont);
        searchPnl01.add(creatorLbl);

        creatorTfl = new JTextField(11);
        creatorTfl.setEnabled(false);
        searchPnl01.add(creatorTfl);

        serachUserBtn = new JButton(appReg.getImageIcon("managecoworkerIcon"));
        serachUserBtn.setMargin(new Insets(0, 0, 0, 0));
        serachUserBtn.setBorder(BorderList.emptyBorder1);
        serachUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionevent) {
                searchUser();
            }
         });
        searchPnl01.add(serachUserBtn);

        delUserBtn = new JButton(appReg.getImageIcon("userremoveIcon"));
        delUserBtn.setMargin(new Insets(0, 0, 0, 0));
        delUserBtn.setBorder(BorderList.emptyBorder1);
        delUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionevent) {
                creatorTfl.setText("");
                createdId = "";
            }
         });
        searchPnl01.add(delUserBtn);

        JPanel searchPnl02 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        stateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/ + " : ");
        stateLbl.setPreferredSize(new Dimension(85,21));
        stateLbl.setHorizontalAlignment(JLabel.RIGHT);
        stateLbl.setFont(FontList.defaultFont);
        searchPnl02.add(stateLbl);

        stateCmb = new JComboBox(vecStatus);
        stateCmb.setSelectedIndex(0);
        stateCmb.setPreferredSize(new Dimension(124,21));
        stateCmb.setBackground(Color.white);
        stateCmb.setFont(FontList.defaultFont);
        searchPnl02.add(stateCmb);

        startDateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00286")/*작성일자*/ + " : ");
        startDateLbl.setPreferredSize(new Dimension(88,21));
        startDateLbl.setHorizontalAlignment(JLabel.RIGHT);
        startDateLbl.setFont(FontList.defaultFont);
        searchPnl02.add(startDateLbl);

        startDateBtn = new DateButton(null);
        startDateBtn.setText("");
        startDateBtn.setDisplayFormat("yyyy-MM-dd");
        startDateBtn.setBackground(Color.white);
        startDateBtn.setBorder(new BevelBorder(BevelBorder.LOWERED));
        startDateBtn.setPreferredSize(new Dimension(124, 21));
        searchPnl02.add(startDateBtn);

        endDateLbl = new JLabel("~");
        endDateLbl.setFont(FontList.defaultFont);
        searchPnl02.add(endDateLbl);

        endDateBtn = new DateButton(null);
        endDateBtn.setText("");
        endDateBtn.setDisplayFormat("yyyy-MM-dd");
        endDateBtn.setBackground(Color.white);
        endDateBtn.setBorder(new BevelBorder(BevelBorder.LOWERED));
        endDateBtn.setPreferredSize(new Dimension(124, 21));
        searchPnl02.add(endDateBtn);

        pnlSearch.add(searchPnl01);
        pnlSearch.add(searchPnl02);

        c.add(pnlSearch);

        searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/, appReg.getImageIcon("searchIcon"));
        searchBtn.setFont(FontList.defaultFont);
        searchBtn.setActionCommand("Search");
        searchBtn.setMargin(new Insets(0,5,0,5));
        pnlBottom.add(searchBtn);

        viewBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00037")/*BOM조회*/, appReg.getImageIcon("assyopenIcon"));
        viewBOMBtn.setFont(FontList.defaultFont);
        viewBOMBtn.setActionCommand("ViewBOM");
        viewBOMBtn.setMargin(new Insets(0,5,0,5));
        pnlBottom.add(viewBOMBtn);

        removeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/, appReg.getImageIcon("removeIcon"));
        removeBtn.setFont(FontList.defaultFont);
        removeBtn.setActionCommand("Remove");
        removeBtn.setMargin(new Insets(0,5,0,5));
        pnlBottom.add(removeBtn);

        closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        closeBtn.setFont(FontList.defaultFont);
        closeBtn.setActionCommand("Close");
        closeBtn.setMargin(new Insets(0,5,0,5));
        pnlBottom.add(closeBtn);
        c.add(pnlBottom);
    }

    private void setEvent() {
        ActionListener actlst = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Search")) {
                    searchBtn_process();
                } else if(e.getActionCommand().equals("ViewBOM")) {
                    viewBOMBtn_process();
                    // My BOM 창 닫기(조회할 BOM 미선택 시 제외)
                    if (!isNoSelected)
                        closeBtn_process();
                } else if(e.getActionCommand().equals("Remove")) {
                    removeBtn_process();
                } else if(e.getActionCommand().equals("Close")) {
                    closeBtn_process();
                }
            }
        };

        searchBtn.addActionListener(actlst);
        viewBOMBtn.addActionListener(actlst);
        removeBtn.addActionListener(actlst);
        closeBtn.addActionListener(actlst);

        tblPublic.addMouseListener(new MouseAdapter()    {
            public void mousePressed(MouseEvent e)    {
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                StringSelection contents = new StringSelection(tblPublic.getValueAt(tblPublic.getSelectedRow(),tblPublic.getSelectedColumn()).toString());
                clipboard.setContents(contents, contents);
            }

            public void mouseClicked(MouseEvent e) {
                // 선택한 Header 의 작성자이거나 Admin 인 경우에만 삭제 버튼 활성화
                if (e.getClickCount() == 1) {
                    intSelectedRow = tblPublic.getSelectedRow();
                    String strOwner = (String) tblPublic.getValueAt(intSelectedRow, 2);
                    if (strOwner != null && strOwner.trim().length() > 0){
                        if (strOwner.trim().equals(loginUser.getFullName()) || isAdmin) {
                            removeBtn.setVisible(true);
                        }else {
                            removeBtn.setVisible(false);
                        }
                    }
                }
                // Header 더블클릭 시 BOM 조회 수행
                if (e.getClickCount() == 2) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    intSelectedRow = tblPublic.getSelectedRow();
                    viewBOMBtn_process();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    // My BOM 창 닫기(조회할 BOM 미선택 시 제외)
                    if (!isNoSelected)
                        closeBtn_process();
                }
            }
        });
    }

    private void setStatusVec() {
        htblStatus = BOMBasicInfoPool.getStatusHash();

        LinkedHashMap mStatus = new LinkedHashMap(htblStatus);
        Set sStatus = mStatus.keySet();

        synchronized(mStatus) {
            Iterator i = sStatus.iterator();
            vecStatus.addElement("");
            while (i.hasNext()) {
                String keyStr = (String)i.next();
                String valStr = (String)mStatus.get(keyStr);
                BOMStateCodeData codeData = new BOMStateCodeData(keyStr, valStr);
                vecStatus.addElement(codeData);
            }
        }
    }

    // 사용자 검색
    private void searchUser(){

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        FindUserDialog dlgFindUser = null;

        dlgFindUser = new FindUserDialog("", "", aifapp, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00208")/*사용자 검색*/);

        String aryCreatorData[] = new String[4];

        if(dlgFindUser.getOK() == true) {
            aryCreatorData = dlgFindUser.getSelectedColumnData();
            creatorTfl.setText(aryCreatorData[1].trim());
            createdId = aryCreatorData[0].trim();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * Search BOM
     */
    private void searchBtn_process() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            tblPublic.removeAll();

            String strFrom = "";
            String strTo = "";
            String itemCodeStr = "";
            String itemNameStr = "";

            strFrom = Utility.dateToString(startDateBtn.getDate());
            strTo = Utility.dateToString(endDateBtn.getDate());
            String fromyy = Utility.dateToString(startDateBtn.getDate(), "yyyyMMdd");
            String toyy = Utility.dateToString(endDateBtn.getDate(), "yyyyMMdd");

            // 날짜범위 오류의 경우
            if (fromyy.length() > 0 && toyy.length() > 0) {
                int intFrom = Integer.parseInt(fromyy);
                int intTo = Integer.parseInt(toyy);

                if ((intTo - intFrom) < 0) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("date1"), "Date Range Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);

                    endDateBtn.setDate(Utility.stringToDate(""));
                    return;
                }
            }

            String stateStr = "";
            if (stateCmb.getSelectedItem() != null && !stateCmb.getSelectedItem().equals("")) {
                BOMStateCodeData stateCodeData = (BOMStateCodeData)stateCmb.getSelectedItem();
                stateStr = stateCodeData.getCode();
            }

            itemCodeStr = itemCodeTfl.getText();
            itemNameStr = itemNameTfl.getText();

Kogger.debug(getClass(), "====>> stateStr : " + stateStr);
Kogger.debug(getClass(), "====>> itemCodeStr : " + itemCodeStr);
Kogger.debug(getClass(), "====>> itemNameStr : " + itemNameStr);
Kogger.debug(getClass(), "====>> createdId : " + createdId);

            // 부품번호 검색조건 체크
            if ( !itemCodeStr.equals("") ) {
                if ( itemCodeStr.indexOf("*") == 0 ) {        // 검색조건 '*'로 시작한 경우
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;

                } else if ( itemCodeStr.indexOf("*") == -1 && itemCodeStr.length() < 3 ) {    // '*'를 입력하지 않은 경우 4자리인지 검사
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;

                } else if ( itemCodeStr.indexOf("*") > 0 && itemCodeStr.length() < 4 ) {        // '*'를 입력한 경우 4자리인지 검사
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }
            }

            // 부품명 검색조건 체크
            if ( !itemNameStr.equals("") ) {
                if ( itemNameStr.indexOf("*") == 0 ) {        // 검색조건 '*'로 시작한 경우
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;

                } else if ( itemNameStr.indexOf("*") == -1 && itemNameStr.length() < 3 ) {    // '*'를 입력하지 않은 경우 4자리인지 검사
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;

                } else if ( itemNameStr.indexOf("*") > 0 && itemNameStr.length() < 4 ) {        // '*'를 입력한 경우 4자리인지 검사
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }
            }

            Hashtable inputParams = new Hashtable();
            inputParams.put("itemCode", itemCodeStr);
            inputParams.put("itemName", itemNameStr);
            inputParams.put("bomState", stateStr);
            inputParams.put("created", createdId);
            inputParams.put("createdDateFrom", strFrom);
            inputParams.put("createdDateTo", strTo);
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
            inputParams.put("operation", "BOM");

            Vector vecSearchBomHeader = new Vector();

            vecSearchBomHeader = KETBomHelper.service.searchBom(inputParams);

            Vector vecSearch = new Vector();
            String strCreatedDate = "";

            MyBOMQry qry = new MyBOMQry();

            if (vecSearchBomHeader.size() > 0) {
                for (int i=0; i < vecSearchBomHeader.size(); i++) {
                    Hashtable hasBomHeader = new Hashtable();
                    Vector vecUsers = new Vector();

                    hasBomHeader = (Hashtable)vecSearchBomHeader.elementAt(i);

                    if (hasBomHeader.get("createdDate").toString().length() > 10) {
                        strCreatedDate = hasBomHeader.get("createdDate").toString().substring(0,10);
                    } else {
                        strCreatedDate = hasBomHeader.get("createdDate").toString();
                    }
                    vecUsers = (Vector)qry.getCoworkerInfo((String)hasBomHeader.get("itemCode"));

                    vecSearch.addElement( new BOMData( hasBomHeader.get("itemCode").toString().trim(),
                                                                    (String)hasBomHeader.get("description"),
                                                                    (String)hasBomHeader.get("createdBy"),
                                                                    strCreatedDate,
                                                                    (String)hasBomHeader.get("bomState"),
                                                                    vecUsers,
                                                                    (String)hasBomHeader.get("bomOid")
                    ));
                }
            }

            bomDataTbl = new BOMTableData(vecSearch);
            sorter = new TableSorter(bomDataTbl);
            tblPublic.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblPublic);
            tblPublic.repaint();
            repaint();
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
              setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * View BOM(BOM OPEN)
     */
    private void viewBOMBtn_process()     {
        try {
            KetMainJTreeTable km = new KetMainJTreeTable();
            BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

            intSelectedRow = tblPublic.getSelectedRow();

            // [2011-03-09] 윤도혁J 요구사항 반영 :: 부품선택없이 BOM조회 클릭시 경고 Popup 필요 - 현재 창 사라짐
            if (intSelectedRow == -1) {
                isNoSelected = true;
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00323")/*조회할 BOM을 선택하십시오.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            } else {
                isNoSelected = false;
            }

            viewBOMBtn.setEnabled(false);
            tblPublic.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            pnl.clearBOMList();
            pnl.repaint();

            String bomOid = tblPublic.getValueAt(intSelectedRow, 6) == null ? "" : tblPublic.getValueAt(intSelectedRow, 6).toString();
            Hashtable hasHeader = new Hashtable();
            hasHeader = KETBomHelper.service.getBom(bomOid);
Kogger.debug(getClass(), "@@@@@@@ hasHeader [My BOM] : " + hasHeader);

            BOMBasicInfoPool.setPublicBOMStatus((String)hasHeader.get("lifeCycleState"));
            BOMBasicInfoPool.setPublicBOMStatusKr((String)hasHeader.get("lifeCycleStateKr"));
            BOMBasicInfoPool.setPublicApproveDate((String)hasHeader.get("publicApproveDate"));
            BOMBasicInfoPool.setPublicApproveDept((String)hasHeader.get("publicApproveDept"));
            BOMBasicInfoPool.setPublicApproveId((String)hasHeader.get("publicApproveID"));
            BOMBasicInfoPool.setPublicApproveName((String)hasHeader.get("publicApproveName"));
            BOMBasicInfoPool.setPublicBasicModelName((String)hasHeader.get("publicBasicModelName"));
            BOMBasicInfoPool.setPublicBasicModelDesc((String)hasHeader.get("publicBasicModelDesc"));
            BOMBasicInfoPool.setPublicOwnerDate((String)hasHeader.get("publicOwnerDate"));
            BOMBasicInfoPool.setPublicOwnerDept((String)hasHeader.get("publicOwnerDept"));
            BOMBasicInfoPool.setPublicOwnerId((String)hasHeader.get("publicOwnerID"));
            BOMBasicInfoPool.setPublicOwnerName((String)hasHeader.get("publicOwnerName"));
            BOMBasicInfoPool.setPublicOwnerEmail((String)hasHeader.get("publicOwnerEmail"));
            BOMBasicInfoPool.setPublicModelName((String)hasHeader.get("publicModelName"));
            BOMBasicInfoPool.setPublicModelDesc((String)hasHeader.get("publicModelDesc"));
            BOMBasicInfoPool.setPublicModelUom((String)hasHeader.get("publicModelUom"));
            BOMBasicInfoPool.setPublicModelStatus((String)hasHeader.get("publicModelStatus"));
            BOMBasicInfoPool.setPublicModelStatusKr((String)hasHeader.get("publicModelStatusKr"));
            BOMBasicInfoPool.setPublicTransFlag((String)hasHeader.get("publicTransFlag"));
            BOMBasicInfoPool.setPublicCheckOutStatus((String)hasHeader.get("publicCheckOutStatus"));
            BOMBasicInfoPool.setPublicBOMOid(bomOid);
            BOMBasicInfoPool.setUserRole("Owner");
            BOMBasicInfoPool.setBomEcoType("");
            BOMBasicInfoPool.setBomEcoNumber("");
            BOMBasicInfoPool.setBomHeaderPartType(Utility.checkNVL((String)hasHeader.get("bomHeaderType")));
            BOMBasicInfoPool.setBomBoxQuantity(Utility.checkNVL((String)hasHeader.get("bomBoxQuantity")));

            // root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
            String strRootModelName = BOMBasicInfoPool.getPublicModelName();
//            WTPart part = KETPartHelper.service.getPart(strRootModelName);
            String strType = Utility.checkNVL( (String)hasHeader.get("bomHeaderType") );

            if (PartUtil.isProductType(strType)) {    // 제품인 경우
                km.setGenMain(aifapp);
            } else {                                                // 금형인 경우
                km.setMoldMain(aifapp);
            }

            Vector vecOpenPublicBOM = new Vector();
            vecOpenPublicBOM.removeAllElements();

            MyBOMQry qry = new MyBOMQry();
            vecOpenPublicBOM = qry.getBOMOpen(BOMBasicInfoPool.getPublicModelName());

            Vector version = new Vector();
            Vector itemVec = new Vector();
            Hashtable inputParams = new Hashtable();

            BOMAssyComponent rootComponent = new BOMAssyComponent(BOMBasicInfoPool.getPublicModelName(), true);
            rootComponent.setLevelInt(new Integer(0));
            rootComponent.setSeqInt(new Integer(0));
            rootComponent.setItemSeqInt(new Integer(10));
            rootComponent.setOpSeqInt(new Integer(1));
            rootComponent.setSortOrderStr("0001");
            rootComponent.setNewFlagStr( Utility.checkNVL((String)hasHeader.get("BOMFlag")) );
            rootComponent.setDescStr(BOMBasicInfoPool.getPublicModelDesc());            // 부품명
            rootComponent.setUitStr(BOMBasicInfoPool.getPublicModelUom());                // 기본단위
            rootComponent.setStatusStr(BOMBasicInfoPool.getPublicModelStatus());        // 부품상태 영문
            rootComponent.setStatusKrStr(BOMBasicInfoPool.getPublicModelStatusKr());    // 부품상태 한글
            if (BOMBasicInfoPool.getBomBoxQuantity() != null && !BOMBasicInfoPool.getBomBoxQuantity().equals("")) {
                rootComponent.setBoxQuantityDbl(Double.parseDouble(BOMBasicInfoPool.getBomBoxQuantity()));    // Box Quantity
            } else {
                rootComponent.setBoxQuantityDbl(1.0);
            }
            // Added by MJOH, 2011-04-07
            rootComponent.setIBAPartType( strType );

            Kogger.debug(getClass(), "====>> myBOM BOMBasicInfoPool.getPublicModelName() : " + BOMBasicInfoPool.getPublicModelName());

//            Vector checkOutVec = new Vector();
//            Vector coworkerVec = new Vector();
//            checkOutVec.addElement(BOMBasicInfoPool.getPublicModelName());
//            coworkerVec = KETBomHelper.service.getCheckOuter(checkOutVec);
//
//Kogger.debug(getClass(), "====>> coworkerVec.toString() : " + coworkerVec.toString());
//Kogger.debug(getClass(), "====>> coworkerVec : " + coworkerVec.size());
//            if (coworkerVec != null && coworkerVec.size() > 0) {
//                if (BOMBasicInfoPool.getPublicModelName().trim().equals(coworkerVec.elementAt(0).toString().substring(0, coworkerVec.elementAt(0).toString().indexOf("|")))) {
//                    rootComponent.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|")+1));
//                }
//            }

            // 해당 모부품을 체크아웃한 사용자가 있는지 확인 (있는경우 RootNode 에 셋팅함)
            Hashtable coworker = checkoutDao.getCheckOuterInfo(BOMBasicInfoPool.getPublicModelName());
            if ( coworker != null && coworker.size() > 0 ) {
                if ( BOMBasicInfoPool.getPublicModelName().trim().equals(coworker.get("itemcode")) ) {
                    rootComponent.setCheckOutStr( coworker.get("user_name") + "(" + coworker.get("user_email") + ")" );
                }
            }

            itemVec.addElement(BOMBasicInfoPool.getPublicModelName());
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
            inputParams.put("itemCode", itemVec);

            version = KETBomHelper.service.getItemVersion(inputParams);
            String versionStr = "";

            for (int k=0; k<version.size(); k++) {
                if (version.size() > 0) {
                    versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                    if (BOMBasicInfoPool.getPublicModelName().equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                        rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                    }
                }
            }

            BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(BOMBasicInfoPool.getPublicModelName().trim()));
            BOMBasicInfoPool.setPublicMyStatus(qry.getMyStatus(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId()));
            BOMBasicInfoPool.setOrgCode(BOMBasicInfoPool.getOrgCode());
            BOMBasicInfoPool.setOrgName(BOMBasicInfoPool.getOrgName());

            pnl.openBOMData(rootComponent, vecOpenPublicBOM);

            /////////////////////////////////////////////////////////////////////////////////////
            BOMTreeTableModel model = (BOMTreeTableModel)pnl.getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();
//            model.fireTreeChanged(rootNode);
Kogger.debug(getClass(), "========1 rootNode : " + rootNode);
Kogger.debug(getClass(), "========1 ChildCount : " + rootNode.getChildCount());
Kogger.debug(getClass(), "========1 getChildren : " + rootNode.getChildren().length);
            /////////////////////////////////////////////////////////////////////////////////////


            // 체크아웃할 부품 정보 담기 (신규 부품인 경우에만 넣는다) :: TODO BOM 상태가 INWORK, REWORK 인 경우에만 담는다
            Object obj = null;
            BOMAssyComponent component = null;
            String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();
            if ( !bomStatus.equals("") && (bomStatus.equals("INWORK") || bomStatus.equals("REWORK")) ) {
                for (int inx = 0; inx < vecOpenPublicBOM.size(); inx++) {
                    obj = (Object) vecOpenPublicBOM.get(inx);
                    if (obj instanceof BOMAssyComponent) {
                        component = (BOMAssyComponent) obj;
                        if (!component.getNewFlagStr().equals("") && component.getNewFlagStr().equals("NEW")) {
                            itemVec.add(component.getItemCodeStr());
                        }
                    }
                }
            }
Kogger.debug(getClass(), "@@@@@ chechkOutItemVec : " + itemVec);

            // 작업자의 작업상태가 4(작업완료) 가 아니고 BOM 상태가 작업중(재작업중) 경우에만 자동으로 Check-Out 처리 함
            String strStatus = "";
            BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
            strStatus = utilDao.getEndWorkingFlagNew(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());

            if ( ( strStatus != null && !strStatus.equals("") && !strStatus.equals("4") ) && ( bomStatus.equals("INWORK") || bomStatus.equals("REWORK") ) ){

                //shin...체크아웃 상태로 만듬............................................................................................................................................................................
//                rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
                CheckOutCmd coc = new CheckOutCmd(aifapp.getDesktop(), aifapp);
                BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
                coc.checkOut( nodes, itemVec, BOMBasicInfoPool.getUserName(), true, versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")) );
//                checkoutDao.setCheckOut( BOMBasicInfoPool.getPublicModelName(), versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")), BOMBasicInfoPool.getUserId() );
                // .................................................................................................................................................................................................................................
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            viewBOMBtn.setEnabled(true);
            tblPublic.setEnabled(true);

            pnl.requestFocus();
        } catch(Exception e) {
            Kogger.error(getClass(), e);
            viewBOMBtn.setEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Remove BOM
     */
    private void removeBtn_process() {
        String bomOid = "";

        intSelectedRow = tblPublic.getSelectedRow();
        bomOid = tblPublic.getValueAt(intSelectedRow, 6) == null ? "" : tblPublic.getValueAt(intSelectedRow, 6).toString();
        Hashtable hasHeader = new Hashtable();
        String strModelName = "";
        String strCheckOutStatus = "";
        String strLifeCycleState = "";

Kogger.debug(getClass(), "bomOid : " + bomOid);

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            hasHeader = KETBomHelper.service.getBom(bomOid);

            strModelName = (String)hasHeader.get("publicModelName") == null ? "" : hasHeader.get("publicModelName").toString().trim();
            strCheckOutStatus = (String)hasHeader.get("publicCheckOutStatus") == null ? "" : hasHeader.get("publicCheckOutStatus").toString().trim();
            strLifeCycleState = (String)hasHeader.get("lifeCycleState") == null ? "" : hasHeader.get("lifeCycleState").toString().trim();
        } catch(Exception e) {
            Kogger.error(getClass(), e);

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("removeError"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00213")/*삭제 에러*/, 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }

Kogger.debug(getClass(), "strModelName : " + strModelName);
Kogger.debug(getClass(), "strCheckOutStatus : " + strCheckOutStatus);

        MyBOMQry qry = new MyBOMQry();

        try {
            if (BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.SYS_ADMIN) || BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.BIZ_ADMIN))     {
                if (strLifeCycleState.equalsIgnoreCase("APPROVED")) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00002", strModelName)/*[{0}] 은 결재완료 상태로 삭제 불가능합니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }

                if (strCheckOutStatus.equalsIgnoreCase("2")) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00003", strModelName)/*[{0}] 은 다른 사용자가 작업중이므로 삭제 불가능합니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                } else {
                    int n = JOptionPane.showConfirmDialog(this, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00316")/*정말 삭제하시겠습니까?*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION);
                    if (n==JOptionPane.YES_OPTION) {
                        qry.removeBOM(bomOid, strModelName);
                        bomDataTbl.vecBOMData.removeElementAt(intSelectedRow);
                        bomDataTbl.fireTableRowsDeleted(0, bomDataTbl.getRowCount());
                        intSelectedRow = -1;
                        tblPublic.clearSelection();

                        if (BOMBasicInfoPool.getPublicModelName().equalsIgnoreCase(strModelName)) {
                            pnl.clearBOMList();
                        }
                    }
                }
            }
            // owner의 소유일 경우
            else if(isMyBOM(BOMBasicInfoPool.getUserName()) == true) {
                if (!(strLifeCycleState.equalsIgnoreCase("INWORK") | strLifeCycleState.equalsIgnoreCase("REJECTED") | strLifeCycleState.equalsIgnoreCase("REWORK"))) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00001", strModelName)/*[{0}] 은 결재 진행중 입니다. 작업중인 BOM 만 삭제 가능합니다..\n삭제가 필요하신 경우 관리자에게 문의하세요..*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                    return;
                }

                if (strCheckOutStatus.equalsIgnoreCase("2")) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00003", strModelName)/*[{0}] 은 다른 사용자가 작업중이므로 삭제 불가능합니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                } else {
                    int n = JOptionPane.showConfirmDialog(this, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00316")/*정말 삭제하시겠습니까?*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION);
                    if (n==JOptionPane.YES_OPTION) {
                        qry.removeBOM(bomOid, strModelName);
                        bomDataTbl.vecBOMData.removeElementAt(intSelectedRow);
                        bomDataTbl.fireTableRowsDeleted(0, bomDataTbl.getRowCount());
                        intSelectedRow = -1;
                        tblPublic.clearSelection();

                        if (BOMBasicInfoPool.getPublicModelName().equalsIgnoreCase(strModelName))    {
                            pnl.clearBOMList();
                        }
                      }
                }
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("owner"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch(Exception e) {
            Kogger.error(getClass(), e);

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("removeError"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00213")/*삭제 에러*/, 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
    }

    /**
     * My BOM Close
     */
    protected void closeBtn_process() {
        myBomFrame.closeBtn_process();
    }

    private boolean isMyBOM(String name) {
        String strUid = "";

        strUid = (String)tblPublic.getValueAt(intSelectedRow, 2);

        if (strUid.equalsIgnoreCase(name))    return true;
        else                                        return false;
    }

    public void lostOwnership(Clipboard clip, Transferable transferable)     {
    }

}
