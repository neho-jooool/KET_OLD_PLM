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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
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

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.multiplebomeco.MultipleECOCmd;
import e3ps.bom.command.updatebom.UpdateBOMDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMCodeData;
import e3ps.bom.common.clipboard.BOMCodePool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.clipboard.BOMStateCodeData;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.common.util.UserData;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.DateButton;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMECOQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECABomLink;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class MyBomEcoPanel extends JPanel implements ClipboardOwner
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    BOMRegisterApplicationPanel pnl;
    private TableSorter sorter;
    String orgCode;

    JButton searchBtn, viewBOMBtn, removeBtn, closeBtn;
    JLabel ecoNoLbl, itemCodeLbl, itemNameLbl, reasonLbl, stateLbl, startDateLbl, endDateLbl;
    JTextField ecoNoTfl, itemCodeTfl, itemNameTfl;
    JComboBox reasonCmb, stateCmb, reasonMoldCmb, reasonTypeCmb, reasonTempCmb;
    DateButton startDateBtn, endDateBtn;

    Registry appReg;
    AbstractAIFUIApplication aifapp;

    BOMECOTableData bomDataTbl;

    JTable tblPublic = new JTable();
    JScrollPane spPublic = new JScrollPane();
    JPanel pnlBottom = new JPanel(new FlowLayout());
    JPanel pnlSearch = new JPanel(new FlowLayout());

    int intSelectedRow;
    MyBOMFrame myBomFrame;

    Vector vecPublic = new Vector();
    Vector vecBomEcoStatus = new Vector();
    LinkedHashMap htblStatus = new LinkedHashMap();

    private Hashtable reasonProdHash;
    private Hashtable reasonMoldHash;
    private Hashtable reasonDetailHash;
    private Vector reasonTypeVec = new Vector();
    private Vector reasonProdVec = new Vector();
    private Vector reasonMoldVec = new Vector();

    boolean keyCheck = false;
    boolean isNoSelected = false;

    private Clipboard clipboard;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public MyBomEcoPanel(MyBOMFrame mbf, AbstractAIFUIApplication app, Vector vecResult) {
        this.myBomFrame = mbf;
        this.aifapp = app;
        this.appReg = Registry.getRegistry(aifapp);
        this.orgCode = BOMBasicInfoPool.getOrgCode();

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        vecPublic = vecResult;
        setBomEcoStatusVec();

        reasonProdHash = BOMCodePool.getProdReason();
        reasonMoldHash = BOMCodePool.getMoldReason();
        reasonDetailHash = BOMCodePool.getReasonDetail();

        SortedMap mReason = Collections.synchronizedSortedMap(new TreeMap(reasonProdHash));
        Set sReason = mReason.keySet();

        BOMCodeData codeData = null;

        // 제품, 변경 변경사유 검색조건 구분
        reasonTypeVec.addElement("");
        codeData = new BOMCodeData("P", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00318")/*제품*/);
        reasonTypeVec.addElement(codeData);
        codeData = new BOMCodeData("D", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00099")/*금형*/);
        reasonTypeVec.addElement(codeData);

        // 제품 변경사유
        synchronized(mReason) {
            Iterator i = sReason.iterator();
            reasonProdVec.addElement("");
            while (i.hasNext()) {
                String keyStr = (String)i.next();
                String valStr = (String)mReason.get(keyStr);
                codeData = new BOMCodeData(keyStr, valStr);
                reasonProdVec.addElement(codeData);
            }
        }

        // 금형 변경사유
        mReason = Collections.synchronizedSortedMap(new TreeMap(reasonMoldHash));
        sReason = mReason.keySet();

        synchronized(mReason) {
            Iterator i = sReason.iterator();
            reasonMoldVec.addElement("");
            while (i.hasNext()) {
                String keyStr = (String)i.next();
                String valStr = (String)mReason.get(keyStr);
                codeData = new BOMCodeData(keyStr, valStr);
                reasonMoldVec.addElement(codeData);
            }
        }

        bomDataTbl = new BOMECOTableData(setMyBomEco());

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
        tblPublic.setPreferredScrollableViewportSize(new Dimension(700, 250));
        tblPublic.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPublic.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPublic.getTableHeader().setReorderingAllowed(false);

        sorter = new TableSorter(bomDataTbl);
        tblPublic.setModel(sorter);
        sorter.addMouseListenerToHeaderInTable(tblPublic);
        tblPublic.repaint();

        JTableHeader headerPublic = tblPublic.getTableHeader();
        headerPublic.setUpdateTableInRealTime(false);

        for (int idx = 0; idx < headerPublic.getColumnModel().getColumnCount(); idx++) {
            BOMECOTableRender tRenderer = new BOMECOTableRender();
            TableColumn tableColumn = headerPublic.getColumnModel().getColumn(idx);
            tableColumn.setCellRenderer(tRenderer);
        }

        for (int k = 0; k < BOMECOTableData.clmBOMECOData.length; k++) {
            BOMTableRender tRenderer = new BOMTableRender();
            tRenderer.setHorizontalAlignment(BOMECOTableData.clmBOMECOData[k].intAlignment);
            TableColumn column = new TableColumn(k, BOMECOTableData.clmBOMECOData[k].intWidth, tRenderer, null);

            if (k == 9) {
                column.setCellEditor(new BOMECOTableCellEditor(new JComboBox()));
            }
            tblPublic.addColumn(column);
        }

        TableColumnModel columnModel = tblPublic.getColumnModel();
        TableColumn column = columnModel.getColumn(10);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        spPublic.getViewport().add(tblPublic);
        c.add(spPublic);

        JPanel searchPnl01 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        itemCodeLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
        itemCodeLbl.setPreferredSize(new Dimension(100,21));
        itemCodeLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemCodeLbl.setFont(FontList.defaultFont);
        searchPnl01.add(itemCodeLbl);

        itemCodeTfl = new JTextField(11);
        itemCodeTfl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if (cCode >= 97 && cCode <= 122) {
                    keyCheck = true;
                }else if (cCode == 10) { // 엔터 시 검색수행
                    searchBtn_process();
                }
            }

            public void keyReleased(KeyEvent keyevent) {
                if (keyCheck) {
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
        itemNameLbl.setPreferredSize(new Dimension(100,21));
        itemNameLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemNameLbl.setFont(FontList.defaultFont);
        searchPnl01.add(itemNameLbl);

        itemNameTfl = new JTextField(11);
        itemNameTfl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if (cCode >= 97 && cCode <= 122) {
                    keyCheck = true;
                }else if (cCode == 10) { // 엔터 시 검색수행
                    searchBtn_process();
                }
            }

            public void keyReleased(KeyEvent keyevent) {
                if (keyCheck) {
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

        ecoNoLbl = new JLabel("ECO No : ");
        ecoNoLbl.setPreferredSize(new Dimension(100,21));
        ecoNoLbl.setHorizontalAlignment(JLabel.RIGHT);
        ecoNoLbl.setFont(FontList.defaultFont);
        searchPnl01.add(ecoNoLbl);

        ecoNoTfl = new JTextField(11);
        ecoNoTfl.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                int cCode = (int)c;

                if (cCode >= 97 && cCode <= 122) {
                    keyCheck = true;
                }else if (cCode == 10) { // 엔터 시 검색수행
                    searchBtn_process();
                }
            }

            public void keyReleased(KeyEvent keyevent) {
                if (keyCheck) {
                    JTextField tfl = (JTextField)keyevent.getSource();
                    int pos = tfl.getCaretPosition();

                    String s = tfl.getText();
                    tfl.setText(s.toUpperCase());
                    tfl.setCaretPosition(pos);
                    keyCheck = false;
                }
            }
        });
        searchPnl01.add(ecoNoTfl);

        JPanel searchPnl02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/ + " : ");
        stateLbl.setPreferredSize(new Dimension(100,21));
        stateLbl.setHorizontalAlignment(JLabel.RIGHT);
        stateLbl.setFont(FontList.defaultFont);
        searchPnl02.add(stateLbl);

        stateCmb = new JComboBox(vecBomEcoStatus);
        stateCmb.setSelectedIndex(0);
        stateCmb.setPreferredSize(new Dimension(124,21));
        stateCmb.setBackground(Color.white);
        stateCmb.setFont(FontList.defaultFont);
        searchPnl02.add(stateCmb);


        reasonLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00164")/*변경사유*/ + " : ");
        reasonLbl.setPreferredSize(new Dimension(100,21));
        reasonLbl.setHorizontalAlignment(JLabel.RIGHT);
        reasonLbl.setFont(FontList.defaultFont);
        searchPnl02.add(reasonLbl);

        // 제품, 금형 변경사유 구분
        reasonTypeCmb = new JComboBox(reasonTypeVec);
        reasonTypeCmb.setSelectedIndex(0);
        reasonTypeCmb.setPreferredSize(new Dimension(50,21));
        reasonTypeCmb.setBackground(Color.white);
        reasonTypeCmb.setFont(FontList.defaultFont);

        // 구분 선택에 따라 변경사유 보여줌
        reasonTypeCmb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = "";
                if (reasonTypeCmb.getSelectedItem() != null) {

                    if (reasonTypeCmb.getSelectedItem().equals("")) {        // 아무것도 선택 안한 경우, 뒤의 변경사유도 빈 콤보박스를 보여줌
                        reasonTempCmb.setVisible(true);
                        reasonCmb.setVisible(false);
                        reasonMoldCmb.setVisible(false);
                    } else {
                        BOMCodeData codeData = (BOMCodeData)reasonTypeCmb.getSelectedItem();
                        type = codeData.getCode();        // P: 제품, D: 금형

                        if (PartUtil.isProductType(type)) {    // 제품
                            reasonTempCmb.setVisible(false);
                            reasonCmb.setVisible(true);
                            reasonMoldCmb.setVisible(false);
                        } else if (type.equals("D")) {                    // 금형
                            reasonTempCmb.setVisible(false);
                            reasonCmb.setVisible(false);
                            reasonMoldCmb.setVisible(true);
                        }
                    }
                }
            }
        });

        // 임시 변경사유 (빈 콤보박스형태임)
        reasonTempCmb = new JComboBox();
        reasonTempCmb.setPreferredSize(new Dimension(124,21));
        reasonTempCmb.setBackground(Color.white);
        reasonTempCmb.setFont(FontList.defaultFont);

        // 제품 변경사유
        reasonCmb = new JComboBox(reasonProdVec);
        reasonCmb.setSelectedIndex(0);
        reasonCmb.setPreferredSize(new Dimension(124,21));
        reasonCmb.setBackground(Color.white);
        reasonCmb.setFont(FontList.defaultFont);
        reasonCmb.setVisible(false);

        // 금형 변경사유
        reasonMoldCmb = new JComboBox(reasonMoldVec);
        reasonMoldCmb.setSelectedIndex(0);
        reasonMoldCmb.setPreferredSize(new Dimension(124,21));
        reasonMoldCmb.setBackground(Color.white);
        reasonMoldCmb.setFont(FontList.defaultFont);
        reasonMoldCmb.setVisible(false);

        searchPnl02.add(reasonTypeCmb);
        searchPnl02.add(reasonTempCmb);
        searchPnl02.add(reasonCmb);
        searchPnl02.add(reasonMoldCmb);


        JPanel searchPnl03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00286")/*작성일자*/ + " : ");
        startDateLbl.setPreferredSize(new Dimension(100,21));
        startDateLbl.setHorizontalAlignment(JLabel.RIGHT);
        startDateLbl.setFont(FontList.defaultFont);
        searchPnl03.add(startDateLbl);

        startDateBtn = new DateButton();
        startDateBtn.setText("");
        startDateBtn.setDisplayFormat("yyyy-MM-dd");
        startDateBtn.setBackground(Color.white);
        startDateBtn.setBorder(new BevelBorder(BevelBorder.LOWERED));
        startDateBtn.setPreferredSize(new Dimension(124, 21));
        searchPnl03.add(startDateBtn);

        endDateLbl = new JLabel("~");
        endDateLbl.setFont(FontList.defaultFont);
        searchPnl03.add(endDateLbl);

        endDateBtn = new DateButton();
        endDateBtn.setText("");
        endDateBtn.setDisplayFormat("yyyy-MM-dd");
        endDateBtn.setBackground(Color.white);
        endDateBtn.setBorder(new BevelBorder(BevelBorder.LOWERED));
        endDateBtn.setPreferredSize(new Dimension(124, 21));
        searchPnl03.add(endDateBtn);

        pnlSearch.add(searchPnl01);
        pnlSearch.add(searchPnl02);
        pnlSearch.add(searchPnl03);

        c.add(pnlSearch);

        searchBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00053")/*검색*/, appReg.getImageIcon("searchIcon"));
        searchBtn.setFont(FontList.defaultFont);
        searchBtn.setActionCommand("Search");
        searchBtn.setMargin(new Insets(0, 5, 0, 5));
        pnlBottom.add(searchBtn);

        viewBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00037")/*BOM조회*/, appReg.getImageIcon("assyopenIcon"));
        viewBOMBtn.setActionCommand("View BOM");
        viewBOMBtn.setFont(FontList.defaultFont);
        viewBOMBtn.setMargin(new Insets(0, 5, 0, 5));
        pnlBottom.add(viewBOMBtn);

        // Added by MJOH, 2007-04-18
        // 수주 BOM ECO 삭제 기능 추가
        removeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/, appReg.getImageIcon("removeIcon"));
        removeBtn.setFont(FontList.defaultFont);
        removeBtn.setActionCommand("Remove");
        removeBtn.setMargin(new Insets(0,5,0,5));
//        pnlBottom.add(removeBtn);

        closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        closeBtn.setFont(FontList.defaultFont);
        closeBtn.setActionCommand("Close");
        closeBtn.setMargin(new Insets(0, 5, 0, 5));
        pnlBottom.add(closeBtn);
        c.add(pnlBottom);
    }

    private Vector setMyBomEco()
    {
        Vector myBomEcoVec = new Vector();
        String strItemCode = "";
        String strItemType = "";
        String strEcoReason = "";
        String strEcoType = "";

        String strReason = "";
        String strTemp = "";
        StringTokenizer token = null;

        KETBOMECOQueryBean bean = new KETBOMECOQueryBean();

        try {
//            Vector returnVector = KETBomHelper.service.searchBomEcoWorkList();
            ArrayList<Hashtable> rtnEco = bean.searchWorkEcoList();
Kogger.debug(getClass(), "@@@@ returnVector : " + rtnEco);
            Hashtable inputParams = new Hashtable();
            inputParams.put("userId", BOMBasicInfoPool.getUserId());
            inputParams.put("vecItemCode", rtnEco);
            inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());

            Vector vecBomEcoHeader = new Vector();
            vecBomEcoHeader = KETBomHelper.service.myBomEco(inputParams);

            MyBOMQry qry = new MyBOMQry();
Kogger.debug(getClass(), "@@@@@@@@ vecBomEcoHeader : "  +vecBomEcoHeader);
            if (vecBomEcoHeader != null && vecBomEcoHeader.size() > 0) {
                Hashtable ecoData = null;

                for (int i=0; i<vecBomEcoHeader.size(); i++) {
                    ecoData = (Hashtable)vecBomEcoHeader.elementAt(i);
                    Vector vecUsers = new Vector();
                    vecUsers = (Vector)qry.getBOMEcoCoworkerInfo(ecoData.get("itemCode").toString());    // 공동작업자가 BOM 단위이므로 itemCode로 검색해야하지 않나??

                    // 변경사유
                    strItemCode = (String)ecoData.get("itemCode");
                    strReason = (String)ecoData.get("reason");
                    strEcoReason = "";

                    if (strItemCode != null && !strItemCode.equals("")) {
                        strItemType = PartSpecGetter.getPartSpec((WTPart)KETPartHelper.service.getPart(strItemCode), PartSpecEnum.SpPartType);
                        if (PartUtil.isProductType(strItemType)) {    // 제품 변경인 경우

                            if (!strReason.equals("") && strReason.indexOf("|") == -1) {        // 변경사유가 하나인 경우
                                strEcoReason = (String) reasonProdHash.get(strReason);
                            } else {                                                                    // 변경사유가 multi 선택 된 경우

                                token = new StringTokenizer(strReason, "|");
                                while(token.hasMoreElements()){
                                    strTemp = token.nextToken();
                                    strEcoReason += (String) reasonProdHash.get(strTemp) + ",";
                                }
                                strEcoReason = strEcoReason.substring(0, strEcoReason.lastIndexOf(","));
                            }
                        } else {

                            if (!strReason.equals("") && strReason.indexOf("|") == -1) {        // 변경사유가 하나인 경우
                                strEcoReason = (String) reasonMoldHash.get(strReason);
                            } else {                                                                    // 변경사유가 multi 선택 된 경우
                                token = new StringTokenizer(strReason, "|");
                                while(token.hasMoreElements()){
                                    strTemp = token.nextToken();
                                    strEcoReason += (String) reasonMoldHash.get(strTemp) + ",";
                                }
                                strEcoReason = strEcoReason.substring(0, strEcoReason.lastIndexOf(","));
                            }
                        }
                    }

                    // ECO Type
                    strEcoType = (String)ecoData.get("ecoType");
                    if (strEcoType != null && strEcoType.equals("Y")) {
                        strEcoType = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00272")/*일괄*/;
                    } else {
                        strEcoType = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00276")/*일반*/;
                    }
                    myBomEcoVec.addElement(new BOMECOData( strItemCode,
                                                                               (String)ecoData.get("itemDesc"),
                                                                               (ecoData.get("ecoNo") == null ? "" : ecoData.get("ecoNo").toString().trim()),
                                                                               (String)ecoData.get("title"),
                                                                               strEcoType,
                                                                               strEcoReason,
                                                                               (String)ecoData.get("createdBy"),
                                                                               (String)ecoData.get("created"),
                                                                               (String)ecoData.get("state"),
                                                                               vecUsers,            // JCheckBox 형태로 저장 - 참조 : MyBOMQry.java 464
                                                                               (String)ecoData.get("oid") ));
                }
            }
        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
        }

        return myBomEcoVec;
    }

    private void setEvent()     {
        ActionListener actlst = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().trim().equals("Search")) {
                    searchBtn_process();
                } else if (e.getActionCommand().trim().equals("View BOM")) {
                    viewBOMBtn_process();
                    // My BOM 창 닫기(조회할 BOM 미선택 시 제외)
                    if (!isNoSelected)
                        closeBtn_process();
                } else if(e.getActionCommand().equals("Remove"))     {
                    removeBtn_process();
                } else if (e.getActionCommand().trim().equals("Close")) {
                    closeBtn_process();
                }
            }
        };

        searchBtn.addActionListener(actlst);
        viewBOMBtn.addActionListener(actlst);
        removeBtn.addActionListener(actlst);
        closeBtn.addActionListener(actlst);

        // 테이블에 mouse 를 double click 했을 때
        tblPublic.addMouseListener(new MouseAdapter()     {
            public void mousePressed(MouseEvent e)    {
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                StringSelection contents = new StringSelection(tblPublic.getValueAt(tblPublic.getSelectedRow(),tblPublic.getSelectedColumn()).toString());
                clipboard.setContents(contents, contents);

                ///////////////////////////////////////////////////////////////
                removeBtnEnable( tblPublic.getSelectedRow() );
                ///////////////////////////////////////////////////////////////
            }

            public void mouseClicked(MouseEvent e) {
                intSelectedRow = tblPublic.getSelectedRow();

                // Header 더블클릭 시 BOM 조회 수행
                if (e.getClickCount() >= 2) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    viewBOMBtn_process();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    // My BOM 창 닫기(조회할 BOM 미선택 시 제외)
                    if (!isNoSelected)
                        closeBtn_process();
                }
            }
        });
    }

    // Search button
    private void searchBtn_process() {

        String strItemCode = "";
        String strItemType = "";
        String strEcoReason = "";
        String strEcoType = "";

        String strReason = "";
        String strTemp = "";
        StringTokenizer token = null;

        String itemCode = itemCodeTfl.getText();
        String itemName = itemNameTfl.getText();
        String ecoNo = ecoNoTfl.getText();

        String reason = "";
        String reasonType = "";
        if (reasonTypeCmb.getSelectedItem() != null && !reasonTypeCmb.getSelectedItem().equals("")) {
            BOMCodeData codeData = (BOMCodeData)reasonTypeCmb.getSelectedItem();
            reasonType = codeData.getCode();
        }
        if (reasonCmb.isVisible()) {
            if (reasonCmb.getSelectedItem() != null && !reasonCmb.getSelectedItem().equals("")) {
                BOMCodeData codeData = (BOMCodeData)reasonCmb.getSelectedItem();
                reason = codeData.getCode();
            }
        }else if (reasonMoldCmb.isVisible()) {
            if (reasonMoldCmb.getSelectedItem() != null && !reasonMoldCmb.getSelectedItem().equals("")) {
                BOMCodeData codeData = (BOMCodeData)reasonMoldCmb.getSelectedItem();
                reason = codeData.getCode();
            }
        }

Kogger.debug(getClass(), "=======>> reason : " + reason);

        String state = "";
        if (stateCmb.getSelectedItem() != null && !stateCmb.getSelectedItem().equals("")) {
            BOMStateCodeData stateCodeData = (BOMStateCodeData)stateCmb.getSelectedItem();
            state = stateCodeData.getCode();
        }

Kogger.debug(getClass(), "=======>> state : " + state);

        String createdFrom = startDateBtn.getText();
        String createdTo = endDateBtn.getText();

        // 검색조건이 하나도 입력되지 않았을 경우
        if (ecoNo.equals("") && itemCode.equals("") && itemName.equals("") && reason.equals("") && state.equals("") &&
            createdFrom.equals("") && createdTo.equals("")) {

            MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("searchCondition"), "Search Condition Error", 2);
            mbox.setModal(true);
            mbox.setVisible(true);
            return;
        }

        // 부품번호 검색조건 체크
        if ( !itemCode.equals("") ) {
            if ( itemCode.indexOf("*") == 0 ) {        // 검색조건 '*'로 시작한 경우
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;

            } else if ( itemCode.indexOf("*") == -1 && itemCode.length() < 3 ) {    // '*'를 입력하지 않은 경우 4자리인지 검사
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;

            } else if ( itemCode.indexOf("*") > 0 && itemCode.length() < 4 ) {        // '*'를 입력한 경우 4자리인지 검사
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }
        }

        // 부품명 검색조건 체크
        if ( !itemName.equals("") ) {
            if ( itemName.indexOf("*") == 0 ) {        // 검색조건 '*'로 시작한 경우
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;

            } else if ( itemName.indexOf("*") == -1 && itemName.length() < 3 ) {    // '*'를 입력하지 않은 경우 4자리인지 검사
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;

            } else if ( itemName.indexOf("*") > 0 && itemName.length() < 4 ) {        // '*'를 입력한 경우 4자리인지 검사
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00057", "*", 4)/*검색조건은 '{0}'를 포함한 {1}자리 이상입니다.*/, "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }
        }

        // 날짜범위 오류의 경우
        if (createdFrom.length() > 0 && createdTo.length() > 0) {
            int intFrom = Integer.parseInt(replace(createdFrom, "-", ""));
            int intTo = Integer.parseInt(replace(createdTo, "-", ""));

            if ((intTo - intFrom) < 0) {
                MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("date1"), "Date Range Error", 2);
                mbox.setModal(true);
                mbox.setVisible(true);

                endDateBtn.setDate(Utility.stringToDate(""));
                return;
            }
        }

        Hashtable inputParams = new Hashtable();
        if (itemCode != null && !itemCode.equals("")) inputParams.put("itemCode", itemCode.trim());
        if (itemName != null && !itemName.equals("")) inputParams.put("itemName", itemName.trim());
        if (ecoNo != null && !ecoNo.equals("")) inputParams.put("ecoNo", ecoNo.trim());
        if (reasonType != null && !reasonType.equals("")) inputParams.put("reasonType", reasonType.trim());
        if (reason != null && !reason.equals("")) inputParams.put("reason", reason.trim());
        if (state != null && !state.equals("")) inputParams.put("state", state.trim());
        if (createdFrom != null && !createdFrom.equals("")) inputParams.put("createdFrom", createdFrom.trim());
        if (createdTo != null && !createdTo.equals("")) inputParams.put("createdTo", createdTo.trim());

        try {
            Vector returnVector = KETBomHelper.service.searchBomEco(inputParams);    // 검색결과 - 실제로 KETBOMECOQueryBean.searchBomEco()가 돌아간다.
            Vector tableVector = new Vector();                                                    // table 에 들어갈 값 (내부에는 BOMECOData 들이 들어가 있음)
            WTPart wt = null;
//Kogger.debug(getClass(), "@@@@@@@@@@ tableVector : " + tableVector);

            if (returnVector != null && returnVector.size() > 0) {

                Hashtable ecoData = null;
                MyBOMQry qry = new MyBOMQry();

                for (int i=0; i<returnVector.size(); i++) {
                    ecoData = (Hashtable)returnVector.elementAt(i);
                    Vector vecUsers = new Vector();
                    vecUsers = (Vector)qry.getBOMEcoCoworkerInfo(ecoData.get("itemCode").toString());    // 공동작업자가 BOM 단위이므로 itemCode로 검색해야하지 않나??

                    // 변경사유
                    strItemCode = (String)ecoData.get("itemCode");
                    strReason = (String)ecoData.get("reason");
                    strEcoReason = "";
                    wt = KETPartHelper.service.getPart(strItemCode);
                    if (strItemCode != null && !strItemCode.equals("")) {
                        strItemType = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
                        if (PartUtil.isProductType(strItemType)) {    // 제품 변경인 경우

                            if (!strReason.equals("") && strReason.indexOf("|") == -1) {        // 변경사유가 하나인 경우
                                strEcoReason = (String) reasonProdHash.get(strReason);
                            } else {                                                                    // 변경사유가 multi 선택 된 경우

                                token = new StringTokenizer(strReason, "|");
                                while(token.hasMoreElements()){
                                    strTemp = token.nextToken();
                                    strEcoReason += (String) reasonProdHash.get(strTemp) + ",";
                                }
                                strEcoReason = strEcoReason.substring(0, strEcoReason.lastIndexOf(","));
                            }
                        } else {

                            if (!strReason.equals("") && strReason.indexOf("|") == -1) {        // 변경사유가 하나인 경우
                                strEcoReason = (String) reasonMoldHash.get(strReason);
                            } else {                                                                    // 변경사유가 multi 선택 된 경우

                                token = new StringTokenizer(strReason, "|");
                                while(token.hasMoreElements()){
                                    strTemp = token.nextToken();
                                    strEcoReason += (String) reasonMoldHash.get(strTemp) + ",";
                                }
                                strEcoReason = strEcoReason.substring(0, strEcoReason.lastIndexOf(","));
                            }
                        }
                    }

                    // ECO Type
                    strEcoType = (String)ecoData.get("ecoType");
                    if (strEcoType != null && strEcoType.equals("multiple")) {
                        strEcoType = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00272")/*일괄*/;
                    } else {
                        strEcoType = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00276")/*일반*/;
                    }

                    tableVector.addElement(new BOMECOData((String)ecoData.get("itemCode"),
                                                                          (String)ecoData.get("itemDesc"),
                                                                          (ecoData.get("ecoNo") == null ? "" : ecoData.get("ecoNo").toString().trim()),
                                                                          (String)ecoData.get("title"),
                                                                          strEcoType,
                                                                          strEcoReason,
                                                                          (String)ecoData.get("createdBy"),
                                                                          (String)ecoData.get("created"),
                                                                          (String)ecoData.get("state"),
                                                                          vecUsers,
                                                                          (String)ecoData.get("ecoOid")));
                }
            }

            bomDataTbl = new BOMECOTableData(tableVector);
            sorter = new TableSorter(bomDataTbl);
            tblPublic.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(tblPublic);
            tblPublic.repaint();
            repaint();

        } catch (wt.util.WTException wte) {
            Kogger.error(getClass(), wte);
        } 
    }

    // View BOM button
    private void viewBOMBtn_process() {

        if (tblPublic.getSelectedRowCount() <1) {
            isNoSelected = true;
            MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00323")/*조회할 BOM을 선택하십시오.*/, "Error", 2);
            mbox.setModal(true);
            mbox.setVisible(true);
            return;
        }

        String ecoNumber = "";
        String ecoType = "";
        String itemCode = "";
        String itemDesc = "";

        if (tblPublic.getSelectedRow() >= 0) {
            isNoSelected = false;
            ecoNumber = (String)tblPublic.getValueAt(tblPublic.getSelectedRow(), 2);
            ecoType = (String)tblPublic.getValueAt(tblPublic.getSelectedRow(), 4);
            itemCode = (String)tblPublic.getValueAt(tblPublic.getSelectedRow(), 0);
            itemDesc = (String)tblPublic.getValueAt(tblPublic.getSelectedRow(), 1);
        }

        if (ecoType != null && !ecoType.equals("") && ecoType.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00272")/*일괄*/)) {
            try {
                String bomEcoOid = tblPublic.getValueAt(intSelectedRow, 10) == null ? "" : tblPublic.getValueAt(intSelectedRow, 10).toString();
                KETBomEcoHeader ecoHeader = (KETBomEcoHeader) KETObjectUtil.getObject(bomEcoOid);
                KETProdECABomLink link = EcmSearchHelper.manager.getProdEcaBomLink(ecoHeader);

                String parentItemCodes = link.getRelatedParentPartList();         // 모부품 목록

                BOMECOBasicInfoPool.setPublicTransFlag("1");
                BOMECOBasicInfoPool.setECONo(ecoNumber);

                MultipleECOCmd multipleEco = new MultipleECOCmd(myBomFrame, aifapp, itemCode, itemDesc, parentItemCodes);
                multipleEco.executeCommand();

            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }
        } else if (ecoType != null && !ecoType.equals("") && ecoType.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00276")/*일반*/))     {
            viewStandardBOMOpen();
        }
    }

    private void setBOMECOInfoPool(String oid) {

        String strEcoNo = "";
        String strItemCode = "";
        Hashtable header = null;
        Hashtable ecoHash = null;

        KETProdChangeOrder  prodChange = null;
        KETMoldChangeOrder  moldChange = null;

        reasonProdHash = BOMCodePool.getProdReason();
        reasonMoldHash = BOMCodePool.getMoldReason();

        try {
//Kogger.debug(getClass(), "@@@@@@@@@@@ oid : " + oid);
            header = KETBomHelper.service.getBomEco(oid);
            KETBOMECOQueryBean bean = new KETBOMECOQueryBean();
Kogger.debug(getClass(), "@@@@@@@@@@@ header : " + header);
            if (header != null) {

                strItemCode = ((String) header.get("itemCode")).trim();
                strEcoNo = ((String) header.get("ecoNo")).trim();
                ecoHash = bean.getBomEco(strEcoNo, strItemCode);

                BOMECOBasicInfoPool.setPublicBOMStatus(header.get("lifeCycleStateKr") == null ? "" : ((String) header.get("lifeCycleStateKr")).trim());
                BOMECOBasicInfoPool.setPublicOwnerId(header.get("creatorId") == null ? "" : ((String) header.get("creatorId")).trim());
                BOMECOBasicInfoPool.setPublicOwnerDate(header.get("creatorDate") == null ? "" : ((String) header.get("creatorDate")).trim());
                BOMECOBasicInfoPool.setPublicOwnerName(header.get("creatorName") == null ? "" : ((String) header.get("creatorName")).trim());
                BOMECOBasicInfoPool.setPublicModelName(header.get("itemCode") == null ? "" : ((String) header.get("itemCode")).trim());
                BOMECOBasicInfoPool.setPublicTransFlag(header.get("transferflag") == null ? "" : ((String) header.get("transferflag")).trim());
                BOMECOBasicInfoPool.setPublicBOMOid(header.get("headerId") == null ? "" : ((String) header.get("headerId")).trim());
                BOMECOBasicInfoPool.setBomEcoNumber(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
                BOMECOBasicInfoPool.setBomBoxQuantity(header.get("boxQuantity") == null ? "" : ((String) header.get("boxQuantity")).trim());
                BOMECOBasicInfoPool.setBomEcoType(header.get("ecoType") == null ? "" : ((String) header.get("ecoType")).trim());
                BOMECOBasicInfoPool.setECONo(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
                BOMECOBasicInfoPool.setBomEcoReason(ecoHash.get("reason") == null ? "" : ((String) ecoHash.get("reason")).trim());
                BOMECOBasicInfoPool.setBomEcoReasonDetails(ecoHash.get("reasonDetail") == null ? "" : ((String) ecoHash.get("reasonDetail")).trim());
                BOMECOBasicInfoPool.setBomEcoRelatedEcrNo("");
                BOMECOBasicInfoPool.setBomEcoTitle(ecoHash.get("title") == null ? "" : ((String) ecoHash.get("title")).trim());


                BOMBasicInfoPool.setPublicBOMStatus(header.get("lifeCycleState") == null ? "" : ((String) header.get("lifeCycleState")).trim());
                BOMBasicInfoPool.setPublicBOMStatusKr(header.get("lifeCycleStateKr") == null ? "" : ((String) header.get("lifeCycleStateKr")).trim());
                BOMBasicInfoPool.setPublicOwnerId(header.get("creatorId") == null ? "" : ((String) header.get("creatorId")).trim());
                BOMBasicInfoPool.setPublicOwnerDate(header.get("creatorDate") == null ? "" : ((String) header.get("creatorDate")).trim());
                BOMBasicInfoPool.setPublicOwnerName(header.get("creatorName") == null ? "" : ((String) header.get("creatorName")).trim());
                BOMBasicInfoPool.setPublicModelName(header.get("itemCode") == null ? "" : ((String) header.get("itemCode")).trim());
                BOMBasicInfoPool.setPublicTransFlag(header.get("transferflag") == null ? "" : ((String) header.get("transferflag")).trim());
                BOMBasicInfoPool.setPublicBOMOid(header.get("headerId") == null ? "" : ((String) header.get("headerId")).trim());
                BOMBasicInfoPool.setBomEcoNumber(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
                BOMBasicInfoPool.setBomBoxQuantity(header.get("boxQuantity") == null ? "" : ((String) header.get("boxQuantity")).trim());
                BOMBasicInfoPool.setBomEcoType(header.get("ecoType") == null ? "" : ((String) header.get("ecoType")).trim());
                BOMBasicInfoPool.setECONo(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
                BOMBasicInfoPool.setBomEcoReason(ecoHash.get("reason") == null ? "" : ((String) ecoHash.get("reason")).trim());
                BOMBasicInfoPool.setBomEcoReasonDetails(ecoHash.get("reasonDetail") == null ? "" : ((String) ecoHash.get("reasonDetail")).trim());
                BOMBasicInfoPool.setBomEcoRelatedEcrNo("");
                BOMBasicInfoPool.setBomEcoTitle(ecoHash.get("title") == null ? "" : ((String) ecoHash.get("title")).trim());
                BOMBasicInfoPool.setPublicCheckOutStatus(header.get("publicCheckOutStatus") == null ? "" : ((String) header.get("publicCheckOutStatus")).trim());
            }

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void viewStandardBOMOpen() {
        try {
            KetMainJTreeTable km = new KetMainJTreeTable();
            BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

            viewBOMBtn.setEnabled(false);
            tblPublic.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            pnl.clearBOMList();

            String bomEcoOid = tblPublic.getValueAt(intSelectedRow, 10) == null ? "" : tblPublic.getValueAt(intSelectedRow, 10).toString();
//            setBasinInfoPool(bomEcoOid.substring(bomEcoOid.indexOf(":")+1));        // 아래 함수와 같으므로 아래 함수를 호출
            setBOMECOInfoPool(bomEcoOid);

            // root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
            String strRootModelName = tblPublic.getValueAt(intSelectedRow, 0) == null ? "" : tblPublic.getValueAt(intSelectedRow, 0).toString();
            WTPart part = KETPartHelper.service.getPart(strRootModelName);
            String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

            if (PartUtil.isProductType(strType)) {    // 제품인 경우
                km.setGenMain(aifapp);
            } else {                                                // 금형인 경우
                km.setMoldMain(aifapp);
            }

            Vector vecOpenPublicBOM = new Vector();
            vecOpenPublicBOM.removeAllElements();

            MyBOMQry qry = new MyBOMQry();
            vecOpenPublicBOM = qry.getBOMEcoOpen(BOMECOBasicInfoPool.getBomEcoNumber(), strRootModelName);
//Kogger.debug(getClass(), "@@@@@@@@@@@ vecOpenPublicBOM : " + vecOpenPublicBOM);

            Vector version = new Vector();
            Vector itemVec = new Vector();
            Hashtable inputParams = new Hashtable();

            BOMAssyComponent rootComponent = new BOMAssyComponent(BOMBasicInfoPool.getPublicModelName(), true);
            rootComponent.setLevelInt(new Integer(0));
            rootComponent.setSeqInt(new Integer(0));
            rootComponent.setSortOrderStr("0001");
            rootComponent.setNewFlagStr("OLD");
            rootComponent.setEcoHeaderNumberStr(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()));
            // Added by MJOH, 2011-04-07
            rootComponent.setIBAPartType( strType );
            BOMBasicInfoPool.setBomHeaderPartType(strType);

            /********************************************/
            // Root Node 의 Item 정보 Query
            Hashtable itemInputParams = new Hashtable();
            itemInputParams.put("itemCode", BOMBasicInfoPool.getPublicModelName().trim());
            itemInputParams.put("description", "");
            itemInputParams.put("creator", "");
            itemInputParams.put("createFromDate", "");
            itemInputParams.put("createToDate", "");
            itemInputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
            itemInputParams.put("bomAllowed", "");
            itemInputParams.put("bomFlag", "");

            Vector queryResult = new Vector();
            queryResult = KETBomHelper.service.searchItem(itemInputParams);

            String modelDesc = "";
            String modelUit = "";
            String modelStatus = "";
            String modelStatusKr = "";
            String versionStr = "";

            if (queryResult.size() > 0) {
                Hashtable hasSearchItemResult = new Hashtable();

                hasSearchItemResult = (Hashtable)queryResult.elementAt(0);

                modelDesc = (String)hasSearchItemResult.get("description");
                modelUit = (String)hasSearchItemResult.get("defaultunit");
                modelStatus = (String)hasSearchItemResult.get("status");
                modelStatusKr = (String)hasSearchItemResult.get("statusKr");
                versionStr = (String)hasSearchItemResult.get("version");
            }
            /********************************************/

            rootComponent.setDescStr(modelDesc);
            rootComponent.setUitStr(modelUit);
            rootComponent.setStatusStr(modelStatus);
            rootComponent.setStatusKrStr(modelStatusKr);
            if (BOMECOBasicInfoPool.getBomBoxQuantity() != null && !BOMECOBasicInfoPool.getBomBoxQuantity().equals("")) {
                rootComponent.setBoxQuantityDbl(Double.parseDouble(BOMECOBasicInfoPool.getBomBoxQuantity()));    // Box Quantity
            } else {
                rootComponent.setBoxQuantityDbl(1.0);
            }

//            Vector checkOutVec = new Vector();
//            checkOutVec.addElement(BOMBasicInfoPool.getPublicModelName());
//            Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutVec);
//            if (coworkerVec != null && coworkerVec.size() > 0) {
//                rootComponent.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|")+1));
//            }

            // 해당 모부품을 체크아웃한 사용자가 있는지 확인 (있는경우 RootNode 에 셋팅함)
            Hashtable coworker = checkoutDao.getCheckOuterInfo(BOMBasicInfoPool.getPublicModelName());
            if ( coworker != null && coworker.size() > 0 ) {
                if ( BOMBasicInfoPool.getPublicModelName().trim().equals(coworker.get("itemcode")) ) {
                    rootComponent.setCheckOutStr( coworker.get("user_name") + "(" + coworker.get("user_email") + ")" );
                }
            }

            itemVec.addElement(BOMBasicInfoPool.getPublicModelName());

            rootComponent.setVersionStr(versionStr);
            rootComponent.setItemSeqInt(new Integer(10));

            BOMBasicInfoPool.setCoWorkerVec(qry.getBOMEcoCoworkerData(BOMBasicInfoPool.getBomEcoNumber().trim()));
            BOMBasicInfoPool.setPublicMyStatus(qry.getBOMEcoMyStatus(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), Utility.checkNVL(BOMBasicInfoPool.getUserId())));
            BOMBasicInfoPool.setOrgCode(BOMBasicInfoPool.getOrgCode());
            BOMBasicInfoPool.setOrgName(BOMBasicInfoPool.getOrgName());

            if (vecOpenPublicBOM.size() > 0) {
                pnl.openBOMData(rootComponent, vecOpenPublicBOM);
            } else {
                Vector vecOpenBOM = new Vector();
                vecOpenBOM.removeAllElements();
                BOMTreeNode selectedNode = new BOMTreeNode(rootComponent);

                UpdateBOMDialog insertRefBom = new UpdateBOMDialog();
                insertRefBom.insertReferenceBom(BOMBasicInfoPool.getPublicModelName().trim(), selectedNode, appReg.getString("plm"), false);

                vecOpenBOM = qry.getBOMEcoOpen(BOMBasicInfoPool.getBomEcoNumber(), BOMBasicInfoPool.getPublicModelName().trim());

                rootComponent.setOpSeqInt(new Integer(1));
                rootComponent.setItemSeqInt(new Integer(10));
                pnl.openBOMData(rootComponent, vecOpenBOM);
            }

            /////////////////////////////////////////////////////////////////////////////////////
            BOMTreeTableModel model = (BOMTreeTableModel)pnl.getTreeTableModel();
            BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();
//            model.fireTreeChanged(rootNode);
Kogger.debug(getClass(), "========1 rootNode : " + rootNode);
Kogger.debug(getClass(), "========1 ChildCount : " + rootNode.getChildCount());
Kogger.debug(getClass(), "========1 getChildren : " + rootNode.getChildren().length);
            /////////////////////////////////////////////////////////////////////////////////////


            // 작업자의 작업상태가 4(작업완료) 가 아닌 경우에만 자동으로 Check-Out 처리 함
            String strStatus = "";
            BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
            strStatus = utilDao.getEndWorkingFlag(BOMBasicInfoPool.getBomEcoNumber(), BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
            String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();
Kogger.debug(getClass(), "@@@@ strStatus : " + strStatus);
Kogger.debug(getClass(), "@@@@ bomStatus : " + bomStatus);
            if ( (strStatus != null && !strStatus.equals("") && !strStatus.equals("4")) && (bomStatus.equals("INWORK") || bomStatus.equals("REWORK")) ) {

                //shin...체크아웃 상태로 만듬............................................................................................................................................................................
//                rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
                CheckOutCmd coc = new CheckOutCmd(aifapp.getDesktop(), aifapp);
                BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
                coc.checkOut( nodes, itemVec, BOMBasicInfoPool.getUserName(), false, versionStr );
//                checkoutDao.setCheckOut( BOMBasicInfoPool.getPublicModelName(), versionStr, BOMBasicInfoPool.getUserId() );
                // .................................................................................................................................................................................................................................
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//            if ( isCheckOut ) {
//                MessageBox mbox = new MessageBox(myBomFrame, "현재 다른 공동작업자가 작업중이므로 조회만 가능합니다.", "확인", 0);
//                mbox.setModal(true);
//                mbox.setVisible(true);
//            }

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
     * ECO 상태목록 가져오기
     */
    private void setBomEcoStatusVec() {
        htblStatus = BOMBasicInfoPool.getBomEcoStatusHash();

        if (htblStatus != null) {
            LinkedHashMap mStatus = new LinkedHashMap(htblStatus);
            Set sStatus = mStatus.keySet();

            synchronized(mStatus) {
                Iterator i = sStatus.iterator();
                vecBomEcoStatus.addElement("");
                while (i.hasNext()) {
                    String keyStr = (String)i.next();
                    String valStr = (String)mStatus.get(keyStr);
                    BOMStateCodeData codeData = new BOMStateCodeData(keyStr, valStr);
                    vecBomEcoStatus.addElement(codeData);
                }
            }
        }
    }

    /**
     * Remove BOM
     */
    private void removeBtn_process() {
        String ecoItemcode = "";
        String checkOutStatus = "";

        intSelectedRow = tblPublic.getSelectedRow();

        String bomEcoOid = tblPublic.getValueAt(intSelectedRow, 10) == null ? "" : tblPublic.getValueAt(intSelectedRow, 10).toString();
        Hashtable hasHeader = new Hashtable();

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            hasHeader = KETBomHelper.service.getBomEco( bomEcoOid );

            ecoItemcode = (String)hasHeader.get("publicModelName") == null ? "" : hasHeader.get("publicModelName").toString().trim();
            checkOutStatus = (String)hasHeader.get("publicCheckOutStatus") == null ? "" : hasHeader.get("publicCheckOutStatus").toString().trim();

            if (checkOutStatus.equalsIgnoreCase("Y")) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(myBomFrame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00160", ecoItemcode)/*변경 BOM [{}]은 현재 Checked-Out 상태입니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00211")/*삭제 불가능*/, 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } else {
                int n = JOptionPane.showConfirmDialog(this, messageRegistry.getString("remove"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION);

                if ( n == JOptionPane.YES_OPTION ) {
                    boolean flag = false;
//                    boolean flag = KETBomHelper.service.deleteECOProcess( bomEcoOid );  //LSISOrderBOMHelper 클래스에 있는 메소드임

                    if ( flag ) {
                        bomDataTbl.vecBOMECOData.removeElementAt(intSelectedRow);
                        bomDataTbl.fireTableRowsDeleted(0, bomDataTbl.getRowCount());
                        intSelectedRow = -1;
                        tblPublic.clearSelection();
                    } else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("removeError"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00212")/*삭제 실패*/, 0);
                        mbox.setModal(true);
                        mbox.setVisible(true);
                    }

                    /*
                    if(BOMBasicInfoPool.getPublicModelName().equalsIgnoreCase(ecoItemcode))
                    {
                        pnl.clearBOMList();
                    }
                    */
                }
            }
        } catch(Exception e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            Kogger.error(getClass(), e);

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("removeError"), "Remove Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private void removeBtnEnable( int selectedRow )
    {
        if (tblPublic.getSelectedRowCount() <1) {
            MessageBox mbox = new MessageBox(myBomFrame, messageRegistry.getString("selectRow14"), "Select Error", 2);
            mbox.setModal(true);
            mbox.setVisible(true);
            return;
        }

        try    {
            String headerOid            = "";
            String headerState        = "";
            String headerType        = "";
            String headerCreator    = "";

//            boolean typeFlag            = false;
            boolean stateFlag            = false;
            boolean creatorFlag        = false;

            Hashtable headerHash    = new Hashtable();

            if (tblPublic.getSelectedRow() >= 0) {
                headerState        = (String)tblPublic.getValueAt( tblPublic.getSelectedRow(), 8 );
                headerOid        = (String)tblPublic.getValueAt( tblPublic.getSelectedRow(), 10 );
                headerCreator    = (String)tblPublic.getValueAt( tblPublic.getSelectedRow(), 6 );
            }

            if ( headerOid != "" && headerOid.trim().length() != 0 ) {
                headerHash = KETBomHelper.service.getBomEco(headerOid);
                headerType = headerHash.get("publicOrderBOM") == null ? "" : headerHash.get("publicOrderBOM").toString().trim();
            }

            // 수주 BOM ECO만 삭제 가능함.
//            if ( "OrderBOM".equals(headerType) ) {
//                typeFlag = true;
//            }

            // 지정된 상태에서만 삭제 가능함.
            if ( headerState.equalsIgnoreCase("INWORK") || headerState.equalsIgnoreCase("REJECTED") ) {
                stateFlag = true;
            }

            // ECO 등록자, Biz Admin, Sys Admin만 삭제 가능함.
            if( headerCreator.equals(BOMBasicInfoPool.getUserName())
                    || BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.SYS_ADMIN)
                    || BOMBasicInfoPool.getUserGroup().equalsIgnoreCase(UserData.BIZ_ADMIN) )
            {
                creatorFlag = true;
            }

            if ( stateFlag && creatorFlag )    {
                removeBtn.setEnabled(true);
            } else {
                removeBtn.setEnabled(false);
            }
        } catch( Exception e ) {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * Close button
     */
    protected void closeBtn_process(){
        myBomFrame.closeBtn_process();
    }

    private String replace(String line, String oldString, String newString)    {
        for (int index = 0;(index = line.indexOf(oldString, index)) >= 0; index += newString.length())
            line = line.substring(0, index) + newString + line.substring(index + oldString.length());

        return line;
    }

    public void lostOwnership(Clipboard clip, Transferable transferable) {
    }

}
