/**
 * @author : Park Pil Keun (pkpark@lgcns.com)
 * @since  : 2006.09.22
 */
package e3ps.bom.command.multiplebomeco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.bomproperty.TotalPropertyDialog;
import e3ps.bom.command.updatebom.BOMChildItemSearchDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtable.AttributiveCellTableModel;
import e3ps.bom.common.jtable.CellSpan;
import e3ps.bom.common.jtable.ColumnGroup;
import e3ps.bom.common.jtable.GroupableTableColumnModel;
import e3ps.bom.common.jtable.GroupableTableHeader;
import e3ps.bom.common.jtable.MultiSpanCellTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.SearchAppliedProductDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class MultipleECODialog extends  AbstractAIFDialog {

    private static final long serialVersionUID = 1L;

    private JFrame frmDesktop;

    AbstractAIFUIApplication app;
    Registry appReg;
    String orgCode;
    String ecoHeaderNo;
    String parentItems;
    String myId;
    String endWorkingFlag;        //[End Working Flag] 1 : Not Access, 2 : Check-In, 3 : Check-Out, 4 : End-Working
    EndWorkingCmd endWorkingCmd;
//    AbortEndworkingCmd abortEndWorkingCmd;

    //-- Multiple Child Item Information component --//
    String childItemCode;
    String childItemDesc;
    String childItemVersion;
    String childItemStatus;
    String childItemUIT;
    String childItemUITSave;
    String childItemBoxQuantity;
    JTextField childItemCodeTf, descTf;

    Vector childItemSubstituteVector;
    String childItemStartDate;
    String childItemQuantity;
    String childItemCostRollup;
    String childItemSupplyTypeCode;
    String childItemSupplyTypeName;
    String childItemItemSEQ;

    //-- 모부품 --//
    ParentItemTableData parentItemInfo;
    Vector parentItemVec;
    boolean isParentItemCheckOut = false;                        // true : check out 된 상태 , false : check in 된 상태
//    JButton parentItemAddBtn, parentItemRemoveBtn, parentItemCheckOut, parentItemCheckIn, parentItemCancelCheckOut, parentItemReviseBtn;
    JTable parentItemTB;

    //-- 일괄변경 내역 --//
    AttributiveCellTableModel mChangeActivityModel;
    Vector mChangeActivityHeaderTitle = new Vector();
    int[] mChangeActivityHeaderSize = {50, 110, 190, 40, 70, 50, 50, 115, 50};
    int[] mChangeActivityAlign = {JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.LEFT, JLabel.CENTER};
    JButton changeInsertBtn, changeRemoveBtn;
    JComboBox changeActCodeCombo;
    MultiSpanCellTable mChangeActivityTB;

    Vector assyCompVec = new Vector();
    Hashtable ecoTyptHash = new Hashtable<String, String>();
    Hashtable ecoTyptHashSave = new Hashtable<String, String>();
    Hashtable wfmTyptHash = new Hashtable<String, String>();

    BOMSearchUtilDao searchDao  = new BOMSearchUtilDao();

    //-- 하단버튼 --//
    JButton endWorkingBtn, abortEndWorkingBtn, changeValidationBtn, saveBtn, cancelBtn;

    //-- 권한 --//
    boolean isSysAdmin = true;
    boolean isBizAdmin = true;

    boolean doValidation = false;

    String currDay;

    public MultipleECODialog() {}

    public MultipleECODialog(JFrame frame, AbstractAIFUIApplication app, String itemCode, String itemDesc, String parentItems) {
        super(frame, true);

        try {
            this.frmDesktop = frame;
            this.app = app;
            this.appReg = Registry.getRegistry(app);

            this.childItemCode = itemCode;
            this.childItemDesc = itemDesc;
            this.parentItems = parentItems;

            this.orgCode = BOMBasicInfoPool.getOrgCode();
            this.myId = BOMBasicInfoPool.getUserId();
            this.ecoHeaderNo = BOMECOBasicInfoPool.getBomEcoNumber();
            BOMBasicInfoPool.setBomEcoNumber(this.ecoHeaderNo);

            this.endWorkingFlag = searchDao.getEndWorkingFlag(this.ecoHeaderNo, BOMBasicInfoPool.getPublicModelName(), this.myId);
            this.endWorkingCmd = new EndWorkingCmd(this.frmDesktop, this.app);
//            this.abortEndWorkingCmd = new AbortEndworkingCmd(this.frmDesktop, this.app);

            setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00030")/*BOM 일괄변경*/);
            setResizable(true);

            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

            Hashtable itemData = searchDao.getItemData(this.childItemCode, this.childItemDesc, this.orgCode);
            this.childItemVersion = Utility.checkNVL(itemData.get("version"));
            this.childItemStatus = Utility.checkNVL(itemData.get("state"));
            this.childItemUIT = bean.getUnitDisplayValue(Utility.checkNVL(itemData.get("uit")));
            this.childItemUITSave = Utility.checkNVL(itemData.get("uit"));
            this.childItemBoxQuantity = searchDao.getBoxQuantityPartUsageLink(this.childItemCode);

            Calendar cal = Calendar.getInstance();        // 시스템의 현재 날짜
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH) + 1;
            int currDate = cal.get(Calendar.DATE);

            this.currDay = currYear + "-";
            if (currMonth < 10) this.currDay += "0";
            this.currDay += (currMonth + "-");
            if (currDate < 10) this.currDay += "0";
            this.currDay += currDate;

            StringTokenizer token = null;
            String strToekn = "";
            parentItemVec = new Vector();

            token = new StringTokenizer(parentItems, ",");
            while(token.hasMoreElements()) {
                strToekn = token.nextToken();
                parentItemVec.add(strToekn);
            }
Kogger.debug(getClass(), "@@@@@ parentItemVec : " + parentItemVec);

            // eco Type 설정
            ecoTyptHash.put("Add", "추가");
            ecoTyptHash.put("Update", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/);
            ecoTyptHash.put("Remove", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/);

            // eco Type 저장 정보 설정
            ecoTyptHashSave.put("추가", "Add");
            ecoTyptHashSave.put(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/, "Update");
            ecoTyptHashSave.put(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/, "Remove");

            // 결재상태 한글 설정
            wfmTyptHash.put("INWORK", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00299")/*작업중*/);
            wfmTyptHash.put("UNDERREVIEW", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00065")/*검토중*/);
            wfmTyptHash.put("APPROVED", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/);
            wfmTyptHash.put("REJECTED", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00155")/*반려됨*/);
            wfmTyptHash.put("REWORK", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00301")/*재작업*/);

            getData();
            setContentInit();
            setEvent();

            checkOutBtn_process();                // 모부품 자동 체크아웃
            btnStatus();
//            search_parent_process();            // 기본으로 모부품 전체 검색

            run();

        } catch (Throwable ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void setContentInit() {
        // 기본 Panel
        JPanel topPanel = new JPanel();

        ///// 자부품 정보 시작 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel multiChildItemInfoPanel = new JPanel();
        multiChildItemInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00279")/*자부품 정보*/, 0, 0, FontList.defaultFont));
        multiChildItemInfoPanel.setFont(FontList.defaultFont);
        multiChildItemInfoPanel.setPreferredSize(new Dimension(700, 70));
        multiChildItemInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        GridBagConstraints gConst = new GridBagConstraints();
        gConst.insets = new Insets(1,1,1,1);

        JLabel childItemCodeLabel = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00281")/*자부품번호*/ + " :");
        childItemCodeLabel.setPreferredSize(new Dimension(100, 21));
        childItemCodeLabel.setHorizontalAlignment(JLabel.RIGHT);
        childItemCodeLabel.setFont(FontList.defaultFont);
        childItemCodeTf = new JTextField(childItemCode, 20);
        childItemCodeTf.setPreferredSize(new Dimension(180, 21));
        childItemCodeTf.setEnabled(false);

        multiChildItemInfoPanel.add(childItemCodeLabel);
        multiChildItemInfoPanel.add(childItemCodeTf);

        JLabel descLabel = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00280")/*자부품명*/ + " :");
        descLabel.setPreferredSize(new Dimension(100, 21));
        descLabel.setHorizontalAlignment(JLabel.RIGHT);
        descLabel.setFont(FontList.defaultFont);
        descTf = new JTextField(childItemDesc, 20);
        descTf.setPreferredSize(new Dimension(180, 21));
        descTf.setEnabled(false);

        multiChildItemInfoPanel.add(descLabel);
        multiChildItemInfoPanel.add(descTf);
        ///// 자부품 정보 끝 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///// 모부품 시작    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel parentItemPanel = new JPanel();
        parentItemPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00150")/*모부품*/, 0, 0, FontList.defaultFont));
        parentItemPanel.setFont(FontList.defaultFont);
        parentItemPanel.setPreferredSize(new Dimension(700, 150));

        parentItemTB = new JTable();
        parentItemTB.setAutoCreateColumnsFromModel(false);
        parentItemTB.setPreferredScrollableViewportSize(new Dimension(675, 85));
        parentItemTB.setModel(parentItemInfo);
//        parentItemTB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        parentItemTB.getTableHeader().setReorderingAllowed(false);

        for (int i=0; i<parentItemInfo.getColumnCount(); i++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(parentItemInfo.getColAlignment(i));
            TableColumn column = new TableColumn(i, parentItemInfo.getColWidth(i), renderer, null);
            parentItemTB.addColumn(column);
        }

        TableColumnModel columnModel = parentItemTB.getColumnModel();
        for (int i=1; i<2; i++) {
            TableColumn column0 = columnModel.getColumn(columnModel.getColumnCount() - i);
            column0.setWidth(0);
            column0.setMinWidth(0);
            column0.setMaxWidth(0);
            column0.setResizable(false);
        }

        JScrollPane parentItemScroll = new JScrollPane();
        parentItemScroll.setSize(1000, 85);
        parentItemScroll.getViewport().add(parentItemTB);

//        parentItemPanel.add(parentItemBtnPanel);
        parentItemPanel.add(parentItemScroll);
        ///// 모부품 끝  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///// 일괄변경 내역 시작  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel multiChangeActPanel = new JPanel();
        multiChangeActPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00274")/*일괄변경 내역*/, 0, 0, FontList.defaultFont));
        multiChangeActPanel.setFont(FontList.defaultFont);
        multiChangeActPanel.setPreferredSize(new Dimension(700, 185));

        JPanel multiChangeActMenuPanel = new JPanel(new BorderLayout());
        multiChangeActMenuPanel.setPreferredSize(new Dimension(688, 28));
        JPanel multiChangeActLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel multiChangeActRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel changeActCodeLabel = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00163")/*변경구분*/ + " :");
        changeActCodeLabel.setFont(FontList.defaultFont);
        Vector changeActCodeVec = new Vector();
        changeActCodeVec.addElement("추가");
        changeActCodeVec.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/);
        changeActCodeVec.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/);
        changeActCodeCombo = new JComboBox(changeActCodeVec);
        changeActCodeCombo.setBackground(Color.white);
        changeActCodeCombo.setFont(FontList.defaultFont);

        changeInsertBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00339")/*추가*/, appReg.getImageIcon("addIcon"));
        changeInsertBtn.setFont(FontList.defaultFont);
        changeInsertBtn.setMargin(new Insets(0, 5, 0, 5));
        changeInsertBtn.setActionCommand("Insert");

        changeRemoveBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/, appReg.getImageIcon("removeIcon"));
        changeRemoveBtn.setFont(FontList.defaultFont);
        changeRemoveBtn.setMargin(new Insets(0, 5, 0, 5));
        changeRemoveBtn.setActionCommand("Remove");
        changeRemoveBtn.setName("changeRemove");

        multiChangeActLeftPanel.add(changeActCodeLabel);
        multiChangeActLeftPanel.add(changeActCodeCombo);
        multiChangeActLeftPanel.add(changeInsertBtn);

        multiChangeActRightPanel.add(changeRemoveBtn);

        multiChangeActMenuPanel.add(multiChangeActLeftPanel, BorderLayout.WEST);
        multiChangeActMenuPanel.add(multiChangeActRightPanel, BorderLayout.EAST);

        mChangeActivityTB = new MultiSpanCellTable(mChangeActivityModel);
        mChangeActivityTB.setColumnModel(new GroupableTableColumnModel());
        mChangeActivityTB.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)mChangeActivityTB.getColumnModel()));
        mChangeActivityTB.setAutoCreateColumnsFromModel(false);
        mChangeActivityTB.setPreferredScrollableViewportSize(new Dimension(675, 68));
        mChangeActivityTB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mChangeActivityTB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i=0; i<mChangeActivityModel.getColumnCount(); i++) {

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(mChangeActivityAlign[i]);
            TableColumn column = new TableColumn(i, mChangeActivityHeaderSize[i], renderer, null);
            mChangeActivityTB.addColumn(column);
        }

        // Setup Column Groups
        GroupableTableColumnModel cm = (GroupableTableColumnModel)mChangeActivityTB.getColumnModel();

        ColumnGroup itemGroupName = new ColumnGroup(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00161")/*변경 전 자부품*/);
        itemGroupName.add(cm.getColumn(1));
        cm.addColumnGroup(itemGroupName);

        // Cell 병합
        cellSpan();

        JScrollPane mChangeActivityScroll = new JScrollPane();
        mChangeActivityScroll.getViewport().add(mChangeActivityTB);

        multiChangeActPanel.add(multiChangeActMenuPanel);
        multiChangeActPanel.add(mChangeActivityScroll);
        ///// 일괄변경 내역 끝 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///// Button 시작 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        endWorkingBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00288")/*작업완료*/, appReg.getImageIcon("endworkingIcon"));
        endWorkingBtn.setActionCommand("End Working");
        endWorkingBtn.setFont(FontList.defaultFont);
        endWorkingBtn.setMargin(new Insets(0, 5, 0, 5));

//        abortEndWorkingBtn = new JButton("작업재개", appReg.getImageIcon("abortendworkingIcon"));
//        abortEndWorkingBtn.setActionCommand("Abort End Working");
//        abortEndWorkingBtn.setFont(FontList.defaultFont);
//        abortEndWorkingBtn.setMargin(new Insets(0, 5, 0, 5));

        changeValidationBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00032")/*BOM검증*/, appReg.getImageIcon("bomvalidationIcon"));
        changeValidationBtn.setActionCommand("Validation");
        changeValidationBtn.setFont(FontList.defaultFont);
        changeValidationBtn.setMargin(new Insets(0, 5, 0, 5));

        saveBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("savebomIcon"));
        saveBtn.setActionCommand("Save");
        saveBtn.setFont(FontList.defaultFont);
        saveBtn.setMargin(new Insets(0, 5, 0, 5));

        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setActionCommand("Cancel");
        cancelBtn.setFont(FontList.defaultFont);
        cancelBtn.setMargin(new Insets(0, 5, 0, 5));

        btnPanel.add(endWorkingBtn);
//        btnPanel.add(abortEndWorkingBtn);
        btnPanel.add(changeValidationBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        ///// Button 끝 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(multiChildItemInfoPanel);
        topPanel.add(parentItemPanel);
        topPanel.add(multiChangeActPanel);
        topPanel.add(btnPanel);

        this.getContentPane().add(topPanel);

//        btnStatus();
    }

    // 기본 데이터 가져오기
    private void getData() {
        //---- 모부품 ----//
        Vector itemInfoVec = getParentItem();
        parentItemInfo = new ParentItemTableData(itemInfoVec);
        //---- 모부품 ----//

        //---- 일괄변경 내역 ----//
        mChangeActivityModel = new AttributiveCellTableModel();

        // 테이블 헤더
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00162")/*변경 후 자부품*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/);
        mChangeActivityHeaderTitle.addElement(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/);

        // 테이블 데이타
        Vector mChangeActivity = getChangeActivity();

        mChangeActivityModel.setDataVector(mChangeActivity, mChangeActivityHeaderTitle);
        //---- 일괄변경 내역 ----//
    }

    private void setEvent() {
        // 버튼에 이벤트 달기
        ActionListener actlst = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().trim().equals("Add")) {
//                    addBtn_process();
                } else if (e.getActionCommand().trim().equals("Remove")) {
                    if (e.getSource() instanceof JButton) {
                        String buttonName = ((JButton)e.getSource()).getName();

//                        if (buttonName.equals("parentRemove")) parentRemoveBtn_process();
                        if (buttonName.equals("changeRemove")) changeRemoveBtn_process();
                    }
                } else if (e.getActionCommand().trim().equals("Insert")) {
                    insertBtn_process();
                } else if (e.getActionCommand().trim().equals("Validation")) {
                    validationBtn_process();
                } else if (e.getActionCommand().trim().equals("End Working")) {
                    endWorkingBtn_process();
//                } else if (e.getActionCommand().trim().equals("Abort End Working")) {
//                    abortEndWorkingBtn_process();
                } else if (e.getActionCommand().trim().equals("Save")) {
                    saveBtn_process(true);
                } else if (e.getActionCommand().trim().equals("Cancel")) {
                    cancelBtn_process();
                }
            }
        };

        changeInsertBtn.addActionListener(actlst);
        changeRemoveBtn.addActionListener(actlst);
        changeValidationBtn.addActionListener(actlst);
        endWorkingBtn.addActionListener(actlst);
//        abortEndWorkingBtn.addActionListener(actlst);
        saveBtn.addActionListener(actlst);
        cancelBtn.addActionListener(actlst);

        // 테이블에 mouse 를 double click 했을 때
        mChangeActivityTB.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                 if (e.getClickCount() == 2) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    editChangeActivityTB_process();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

//---- 여기부터는 실제로 Data 가져오기 ----//
    private Vector getParentItem() {
        Vector parentItem = new Vector();

        Registry registry = null;
        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;
        String sql = "";

        try {
            registry = Registry.getRegistry("e3ps.bom.bom");
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));
            stmt = conn.createStatement();

            for( int inx = 0; inx < this.parentItemVec.size(); inx++ ) {

                sql = " SELECT DISTINCT C.NAME AS DESCRIPTION, B.STATESTATE AS STATE,                                                 \n"
                    + "                        B.VERSIONIDA2VERSIONINFO || '.' || B.ITERATIONIDA2ITERATIONINFO AS VERSION_ALL,     \n"
                    + "                        B.VERSIONIDA2VERSIONINFO  AS VERSION,                                                         \n"
                    + "                        C.DEFAULTUNIT  AS UNIT,                                                                                 \n"
                    + "                           B.STATECHECKOUTINFO, B.IDA2A2 AS OID                                                             \n"
                    + " FROM WTPARTMASTER C, wtpart B                                                                                         \n"
                    + " WHERE C.WTPARTNUMBER = '" + this.parentItemVec.elementAt(inx) + "'                                            \n"
                    + " AND C.IDA2A2 = B.IDA3MASTERREFERENCE                                                                                \n"
                    + " AND B.IDA2A2 = (SELECT MAX(D.IDA2A2)                                                                                    \n"
                    + "                            FROM wtpart D, WTPARTMASTER E                                                                    \n"
                    + "                            WHERE D.IDA3MASTERREFERENCE = E.IDA2A2                                                    \n"
                    + "                            AND    (D.STATECHECKOUTINFO = 'wrk' OR D.STATECHECKOUTINFO = 'c/i')                    \n"
                    + "                            AND    D.LATESTITERATIONINFO = 1                                                                    \n"
                    + "                            AND    E.WTPARTNUMBER = '" + this.parentItemVec.elementAt(inx) + "' )                        \n";

Kogger.debug(getClass(), "@@@@@@@@@@@@@ [getParentItem] sql : " + sql);

                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    parentItem.addElement(new ParentItemData( "",
                                                              Utility.checkNVL(this.parentItemVec.elementAt(inx)),
                                                              Utility.checkNVL(rs.getString("DESCRIPTION")),
                                                              Utility.checkNVL(rs.getString("VERSION")),
                                                              (String) wfmTyptHash.get(Utility.checkNVL(rs.getString("STATE"))),
                                                              "1.000", //strQuantity
                                                              Utility.checkNVL(rs.getString("UNIT")), //strUnit
                                                              Utility.checkNVL(rs.getString("OID")) ));

                    setChildItemData(Utility.checkNVL(this.parentItemVec.elementAt(inx)));
                }
            }

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if (res != null) res.freeConnection(registry.getString("plm"), conn);
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }
        }

        return parentItem;
    }

    /**
     * Multiple Change Activity 정보 가져오기
     *
     * (Parent Item 개수 * Change Activity 개수)만큼 ketbomecocomponent 저장 - DISTINCT로 불러 올 때는 중복 제거
     * 그래서 비교용 parentItemCode를 두고 한개의 parentItemCode에 대한 Activity만 들고 온다.
     */
    private Vector getChangeActivity() {
        Vector changeActivity = new Vector();
        KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

        Registry registry = null;
        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;
        String sql = "";
        String codeSave = "";

        try {
            registry = Registry.getRegistry("e3ps.bom.bom");
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));
            stmt = conn.createStatement();

            sql = " SELECT DISTINCT A.SEQUENCENUMBER, A.ECOCODE AS CODE, A.CHILDITEMCODE,                     \n"
                + "                   B.STATESTATE STATUS,  C.DEFAULTUNIT UIT,                                                        \n"
                + "                   A.BEFORESTARTDATE, A.AFTERSTARTDATE, C.NAME AS DESCRIPTION,                        \n"
                + "             B.VERSIONIDA2VERSIONINFO AS VERSION,                                                         \n"
                + "             A.BEFOREQUANTITY, A.AFTERQUANTITY,                                                            \n"
                + "             A.BEFOREBOXQUANTITY, A.AFTERBOXQUANTITY                                                    \n"
                + " FROM ketbomecocomponent A, WTPARTMASTER C, wtpart B                                                \n"
                + " WHERE A.ECOHEADERNUMBER = '" + this.ecoHeaderNo + "'                                                \n"
                + " AND A.CHILDITEMCODE = C.WTPARTNUMBER                                                                \n"
                + " AND C.IDA2A2 = B.IDA3MASTERREFERENCE                                                                    \n"
                + " AND B.IDA2A2 = (SELECT MAX(D.IDA2A2)                                                                        \n"
                + "                    FROM wtpart D, WTPARTMASTER E                                                                \n"
                + "                    WHERE D.IDA3MASTERREFERENCE = E.IDA2A2                                                \n"
                + "                    AND    (D.STATECHECKOUTINFO = 'wrk' OR D.STATECHECKOUTINFO = 'c/i')                \n"
                + "                    AND    D.LATESTITERATIONINFO = 1                                                                \n"
                + "                    AND    E.WTPARTNUMBER = A.CHILDITEMCODE)                                                \n";

Kogger.debug(getClass(), "@@@@@@@@@@@@ [getChangeActivity] sql : " + sql);

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Vector activityBeforeData = new Vector();
                Vector activityAfterData = new Vector();

                BOMAssyComponent assyComponent1 = new BOMAssyComponent();
                BOMAssyComponent assyComponent2 = new BOMAssyComponent();

                String code = Utility.checkNVL(rs.getString("CODE")).trim();
                String childItemCode = rs.getString("CHILDITEMCODE");
                String parentItemCode = (String)parentItemInfo.getValueAt(0, 0);
                String seqNo = rs.getString("SEQUENCENUMBER");

                // substitute
                Vector beforeSubst = searchDao.getSubstitute(ecoHeaderNo, parentItemCode, childItemCode, seqNo, "before");
                Vector afterSubst = searchDao.getSubstitute(ecoHeaderNo, parentItemCode, childItemCode, seqNo, "after");

                String beforeSubstitute = "";
                String afterSubstitute = "";

                for (int i=0; i<beforeSubst.size(); i++) {
                    BOMSubAssyComponent component = (BOMSubAssyComponent)beforeSubst.elementAt(i);

                    if (beforeSubstitute.length() > 0) beforeSubstitute += ", ";
                    beforeSubstitute += component.getSubstituteItemCodeStr();
                }

                for (int i=0; i<afterSubst.size(); i++) {
                    BOMSubAssyComponent component = (BOMSubAssyComponent)afterSubst.elementAt(i);

                    if (afterSubstitute.length() > 0) afterSubstitute += ", ";
                    afterSubstitute += component.getSubstituteItemCodeStr();
                }

                String strBeforeQuantity = Utility.checkNVL(rs.getString("BEFOREQUANTITY"));
                String strAfterQuantity = Utility.checkNVL(rs.getString("AFTERQUANTITY"));

                Double dblBeforeQuantity = new Double(0);
                if (!strBeforeQuantity.equals("")) dblBeforeQuantity = Double.valueOf(strBeforeQuantity);
                Double dblAfterQuantity = new Double(0);
                if (!strAfterQuantity.equals("")) dblAfterQuantity = Double.valueOf(strAfterQuantity);

                NumberFormat format = NumberFormat.getInstance();
                format.setMinimumFractionDigits(3);

                strBeforeQuantity = format.format(dblBeforeQuantity.doubleValue());
                strAfterQuantity = format.format(dblAfterQuantity.doubleValue());

                String strBeforeBoxQuantity = Utility.checkNVL(rs.getString("BEFOREBOXQUANTITY"));
                String strAfterBoxQuantity = Utility.checkNVL(rs.getString("AFTERBOXQUANTITY"));

                Double dblBeforeBoxQuantity = new Double(0);
                if (!strBeforeBoxQuantity.equals("")) dblBeforeBoxQuantity = Double.valueOf(strBeforeBoxQuantity);
                Double dblAfterBoxQuantity = new Double(0);
                if (!strAfterBoxQuantity.equals("")) dblAfterBoxQuantity = Double.valueOf(strAfterBoxQuantity);

                activityBeforeData.addElement(ecoTyptHash.get(code));        // Display 되는 ECO Code 값 넣기

                if (code.equalsIgnoreCase("Add")) {
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                    activityBeforeData.addElement("");
                } else {
                    activityBeforeData.addElement(childItemCode);
                    activityBeforeData.addElement(rs.getString("DESCRIPTION"));
                    activityBeforeData.addElement(rs.getString("VERSION"));
                    activityBeforeData.addElement((String) wfmTyptHash.get(rs.getString("STATUS"))); // Display 용 결재상태(한글)로 전환
                    activityBeforeData.addElement(strBeforeQuantity);
                    activityBeforeData.addElement(bean.getUnitDisplayValue(rs.getString("UIT")));        // Display 용 단위로 전환
                    activityBeforeData.addElement(beforeSubstitute);
                    activityBeforeData.addElement(strBeforeBoxQuantity);

                    assyComponent1.setParentItemCodeStr(parentItemCode);
                    assyComponent1.setItemCodeStr(childItemCode);
                    assyComponent1.setStartDate(rs.getString("BEFORESTARTDATE"));
                    assyComponent1.setDescStr(rs.getString("DESCRIPTION"));
                    assyComponent1.setStatusStr(rs.getString("STATUS"));                                     // 저장 용 결재상태(영문)
                    assyComponent1.setStatusKrStr((String) wfmTyptHash.get(rs.getString("STATUS")));     // Display 용 결재상태(한글)로 전환
                    assyComponent1.setUitStr(rs.getString("UIT"));                                                // 실제 저장되는 단위
                    assyComponent1.setQuantityDbl(dblBeforeQuantity);
                    assyComponent1.setSubAssyComponent(beforeSubst);
                    assyComponent1.setBoxQuantityDbl(dblBeforeBoxQuantity);
                }

                activityAfterData.addElement("");

                if (!code.equalsIgnoreCase("Remove")) {
                    activityAfterData.addElement(childItemCode);
                    activityAfterData.addElement(rs.getString("DESCRIPTION"));
                    activityAfterData.addElement(rs.getString("VERSION"));
                    activityAfterData.addElement((String) wfmTyptHash.get(rs.getString("STATUS")));  // Display 용 결재상태(한글)로 전환
                    activityAfterData.addElement(strAfterQuantity);
                    activityAfterData.addElement(bean.getUnitDisplayValue(rs.getString("UIT")));         // Display 용 단위로 전환
                    activityAfterData.addElement(afterSubstitute);
                    activityAfterData.addElement(strAfterBoxQuantity);

                    assyComponent2.setParentItemCodeStr(parentItemCode);
                    assyComponent2.setItemCodeStr(childItemCode);
                    assyComponent2.setStartDate(rs.getString("AFTERSTARTDATE"));
                    assyComponent2.setDescStr(rs.getString("DESCRIPTION"));
                    assyComponent2.setStatusStr(rs.getString("STATUS"));                                     // 저장 용 결재상태(영문)
                    assyComponent2.setStatusKrStr((String) wfmTyptHash.get(rs.getString("STATUS")));     // Display 용 결재상태(한글)로 전환
                    assyComponent2.setUitStr(rs.getString("UIT"));                                                // 실제 저장되는 단위
                    assyComponent2.setQuantityDbl(dblAfterQuantity);
                    assyComponent2.setSubAssyComponent(afterSubst);
                    assyComponent2.setBoxQuantityDbl(dblAfterBoxQuantity);
                } else {
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                    activityAfterData.addElement("");
                }

                changeActivity.addElement(activityBeforeData);
                changeActivity.addElement(activityAfterData);

                assyCompVec.addElement(assyComponent1);
                assyCompVec.addElement(assyComponent2);
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if (res != null) res.freeConnection(registry.getString("plm"), conn);
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }
        }

        return changeActivity;
    }

    private boolean doSql(Vector sqlVec) {
        boolean isSuccess = true;

        if (sqlVec != null && sqlVec.size() > 0) {
            Registry registry = null;
            DBConnectionManager res = null;
            Connection conn = null;
            Statement stmt = null;

            try {
                registry = Registry.getRegistry("e3ps.bom.bom");
                res = DBConnectionManager.getInstance();
                conn = res.getConnection(registry.getString("plm"));
                stmt = conn.createStatement();

                for (int i=0; i<sqlVec.size(); i++) {
                    stmt.execute((String)sqlVec.elementAt(i));
                }
            } catch (Exception e) {
                Kogger.error(getClass(), e);
                isSuccess = false;

            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                    if (res != null) res.freeConnection(registry.getString("plm"), conn);
                } catch (Exception e) {
                    Kogger.error(getClass(), e);
                }
            }
        }

        return isSuccess;
    }

    /**
     * 모자관계 속성 정보 가져오기
     * @param    parentItemCode : orgCode를 포함하지 않는다.
     */
    private void setChildItemData(String parentItemCode) {
        this.childItemSubstituteVector = searchDao.getSubstitute(parentItemCode, this.childItemCode);

        Registry registry = null;
        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;
        String sql = "";

        try {
            registry = Registry.getRegistry("e3ps.bom.bom");
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));
            stmt = conn.createStatement();

            sql = " SELECT STARTDATE, QUANTITY, ITEMSEQ                                     \n"
                + " FROM KETPartUsageLink                                                             \n"
                + " WHERE PARENTITEMCODE = '" + parentItemCode + "'                         \n"
                + " AND CHILDITEMCODE = '" + this.childItemCode + "'                         \n"
                + " AND IDA3A5 = (SELECT MAX(IDA3A5)                                             \n"
                     + "                      FROM KETPartUsageLink                                         \n"
                + "                      WHERE PARENTITEMCODE = '" + parentItemCode + "'    \n"
                + "                      AND CHILDITEMCODE = '" + this.childItemCode + "')    \n";

Kogger.debug(getClass(), "@@@@@@@@@@@@ [setChildItemData] sql : " + sql);

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                this.childItemStartDate = Utility.checkNVL(rs.getString("STARTDATE"));
                this.childItemQuantity = Utility.checkNVL(rs.getString("QUANTITY"));
                this.childItemItemSEQ = Utility.checkNVL(rs.getString("ITEMSEQ"));
            }

//            if (this.childItemStartDate.length() > 0) this.childItemStartDate = this.childItemStartDate.substring(0,10);
            if (this.childItemQuantity.equals("")) {
                this.childItemQuantity = "1.000";
            } else {
                Double quantity = new Double(this.childItemQuantity);

                NumberFormat format = NumberFormat.getInstance();
                format.setMinimumFractionDigits(3);

                this.childItemQuantity = format.format(quantity.doubleValue());
            }

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if (res != null) res.freeConnection(registry.getString("plm"), conn);
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }
        }
    }

//---- 여기부터는 실제로 Event 가 실행되는 method ----//

    /**
     * Multiple change activity table 에 Mouse double click 이 일어났을 때 처리
     * 편집이 가능한 셀인지 아닌지를 판단한다.
     */
    private void editChangeActivityTB_process() {
        boolean isView = false;
        if (!endWorkingFlag.equals("3") || !isParentItemCheckOut) {
            isView = true;
        }

        int selectedRow = mChangeActivityTB.getSelectedRow();
        int selectedCol = mChangeActivityTB.getSelectedColumn();

        boolean isEditable = true;

        String selectedActionCode = "";
        if (selectedRow % 2 == 0) {
            selectedActionCode = (String)mChangeActivityTB.getValueAt(selectedRow, 0);
        } else {
            selectedActionCode = (String)mChangeActivityTB.getValueAt(selectedRow - 1, 0);
        }

        if (selectedActionCode.equals("추가")) {
            if ( selectedRow % 2 == 0) {
                isEditable = false;

            } else if (selectedRow % 2 == 1 && selectedCol == 1 && !isView) {    // 부품번호 컬럼 클릭한 경우 부품검색으로 새로운 부품 추가
///                mChangeActivityTB.getCellEditor().cancelCellEditing();

                BOMChildItemSearchDialog bomItemSearchDialog = new BOMChildItemSearchDialog(frmDesktop, app, "", KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00179")/*부품검색*/);
                String[] itemArray = bomItemSearchDialog.getSelectedColumnData();

                if (itemArray[0] != null && itemArray[0].length() > 0) {
                    Hashtable itemData = searchDao.getItemData(itemArray[0], itemArray[1], this.orgCode);
                    BOMAssyComponent assyComponent = (BOMAssyComponent)assyCompVec.elementAt(selectedRow);

                    // 이미 추가 된 item 인지 check
                    int activitySize = mChangeActivityTB.getRowCount();
                    boolean isAlready = false;
                    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

                    if (this.childItemCode.equals(itemArray[0])) {
                        openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00282")/*자부품을 추가 할 수 없습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00340")/*추가 실패*/);

                    } else {
                        if (activitySize > 0) {
                            for (int i=activitySize-2; i>=0; i-=2) {
                                String actionCode = (String)mChangeActivityTB.getValueAt(i, 0);
                                String itemCode = (String)mChangeActivityTB.getValueAt(i+1, 1);

                                if (actionCode.equals("추가") && itemCode.equals(itemArray[0])) {
                                    isAlready = true;
                                    break;
                                }
                            }
                        }

                        if (!isAlready)
                        {
                            mChangeActivityTB.setValueAt(itemArray[0], selectedRow, 1);                                                                    // childItemCode
                            mChangeActivityTB.setValueAt(itemArray[1], selectedRow, 2);                                                                    // Description
                            mChangeActivityTB.setValueAt(Utility.checkNVL(itemData.get("version")), selectedRow, 3);                                // Version
                            mChangeActivityTB.setValueAt((String) wfmTyptHash.get(Utility.checkNVL(itemData.get("state"))), selectedRow, 4);    // Status
                            mChangeActivityTB.setValueAt("1.000", selectedRow, 5);                                                                        // 수량
                            mChangeActivityTB.setValueAt(bean.getUnitDisplayValue(Utility.checkNVL(itemData.get("uit"))), selectedRow, 6);        // 단위
                            mChangeActivityTB.setValueAt("1.0", selectedRow, 8);                                                                            // 기준수량

                            assyComponent.setParentItemCodeStr((String)parentItemTB.getValueAt(0, 0));
                            assyComponent.setStartDate(this.currDay);
                            assyComponent.setItemCodeStr(itemArray[0]);
                            assyComponent.setDescStr(itemArray[1]);
                            assyComponent.setStatusStr( Utility.checkNVL(itemData.get("state")) );
                            assyComponent.setStatusKrStr( (String) wfmTyptHash.get(Utility.checkNVL(itemData.get("state"))) );
                            assyComponent.setUitStr( Utility.checkNVL(itemData.get("uit")) );
                            assyComponent.setQuantityDbl(new Double("1.000"));
                            assyComponent.setBoxQuantityDbl(new Double("1.0"));

                            assyCompVec.setElementAt(assyComponent, selectedRow);

                        } else {
                            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00174", itemArray[0])/*부품 [{0}] 은 이미 추가되어 있습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00340")/*추가 실패*/);
                        }
                    }
                }

            } else if (selectedRow % 2 == 1 && selectedCol != 1) {            // 부품번호 컬럼이 아닌 경우 속성변경 팝업 화면에서 수량, 단위 정보 가져옴 (변경되든 안되든)
                String itemCode = Utility.checkNVL(mChangeActivityTB.getValueAt(selectedRow, 1));
                String itemDesc = Utility.checkNVL(mChangeActivityTB.getValueAt(selectedRow, 3));

                if (itemCode.equals("") || itemDesc.equals("")) {
                    openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00193")/*부품을 먼저 추가하세요.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00178")/*부품 에러*/);
                } else {
                    TotalPropertyDialog ecoProperty = new TotalPropertyDialog((BOMAssyComponent)assyCompVec.elementAt(selectedRow), frmDesktop, true, null, isView, app, true);

                    if (!isView) {
                        String quantityStr = getQuantityStr(ecoProperty);
                        mChangeActivityTB.setValueAt(quantityStr, selectedRow, 5);

                        String substituteStr = getSubstituteStr(ecoProperty.assyComponent.getSubAssyComponent());
                        mChangeActivityTB.setValueAt(substituteStr, selectedRow, 7);

                        String boxQuantityStr = getBoxQuantityStr(ecoProperty);
                        mChangeActivityTB.setValueAt(boxQuantityStr, selectedRow, 8);
                    }
                }
            }

        } else if (selectedActionCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {
            if (selectedRow % 2 == 0) {
                new TotalPropertyDialog((BOMAssyComponent)assyCompVec.elementAt(selectedRow), frmDesktop, true, null, true, app, true);        // 수정인경우에는 윗줄은 무조건 읽기전용임
            } else {
                TotalPropertyDialog ecoProperty = new TotalPropertyDialog((BOMAssyComponent)assyCompVec.elementAt(selectedRow), frmDesktop, true, null, isView, app, true);

                if (!isView) {
                    String quantityStr = getQuantityStr(ecoProperty);
                    mChangeActivityTB.setValueAt(quantityStr, selectedRow, 5);

                    String substituteStr = getSubstituteStr(ecoProperty.assyComponent.getSubAssyComponent());
                    mChangeActivityTB.setValueAt(substituteStr, selectedRow, 7);

                    String boxQuantityStr = getBoxQuantityStr(ecoProperty);
                    mChangeActivityTB.setValueAt(boxQuantityStr, selectedRow, 8);
                }
            }

        } else if (selectedActionCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/)) {
            if ( selectedRow % 2 == 1) {
                isEditable = false;
            } else {
                new TotalPropertyDialog((BOMAssyComponent)assyCompVec.elementAt(selectedRow), frmDesktop, true, null, true, app, true);        // 수정인경우에는 윗줄은 무조건 읽기전용임
            }
        }

        if (!isEditable) {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00241")/*수정 불가능합니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00261")/*알림*/);
            if (!isView) ///mChangeActivityTB.getCellEditor().cancelCellEditing();
            return;
        }
    }

    // 수량정보 가져오기
    private String getQuantityStr(TotalPropertyDialog ecoProperty) {
        String quantityStr = "";

        Double quantity = ecoProperty.assyComponent.getQuantityDbl();

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        quantityStr = format.format(quantity.doubleValue());

        return quantityStr;
    }

    // 기준수량정보 가져오기
    private String getBoxQuantityStr(TotalPropertyDialog ecoProperty) {

        Double boxQuantity = ecoProperty.assyComponent.getBoxQuantityDbl();

        return boxQuantity + "";
    }

    // 대체부품번호 "," 나열로 조합
    private String getSubstituteStr(Vector substituteComp) {
        String substituteStr = "";

        if (substituteComp != null) {
            for (int i=0; i<substituteComp.size(); i++) {
                BOMSubAssyComponent component = (BOMSubAssyComponent)substituteComp.elementAt(i);

                if (substituteStr.length() > 0) substituteStr += ", ";
                substituteStr += component.getSubstituteItemCodeStr();
            }
        }

        return substituteStr;
    }

    // 메세지 Display
    private void openMessage(String message, String title) {
        MessageBox mbox = new MessageBox(frmDesktop, message, title, 2);
        mbox.setModal(true);
        mbox.setVisible(true);
    }

    /**
     * 기본으로 Search Parent 검색
     */
    private void search_parent_process() {
        Vector tableVec = new Vector();                // table에 들어갈 값 내부에는 ParentItemData가 들어간다.

        try {
            SearchAppliedProductDao searchAppliedProduct = new SearchAppliedProductDao();
            Vector parentItemVec = searchAppliedProduct.searchParentItem(childItemCode, childItemDesc, orgCode);

            if (parentItemVec != null && parentItemVec.size() > 0) {
                Hashtable parentItemData = null;
                String strVer = "";

                for (int i=0; i<parentItemVec.size(); i++) {
                    parentItemData = (Hashtable)parentItemVec.elementAt(i);

                    strVer = (String)parentItemData.get("version");
                    if (strVer != null && !strVer.equals("")) {
                        strVer = strVer.substring(0, strVer.indexOf("."));
                    }
                    tableVec.addElement(new ParentItemData(     "",
                                                                        (String)parentItemData.get("itemCode"),
                                                                        (String)parentItemData.get("description"),
                                                                        strVer,
                                                                        (String)parentItemData.get("statusKr"),
                                                                        (String)(parentItemData.get("quantity") + ""),
                                                                        (String)parentItemData.get("uit"),
                                                                        (String)parentItemData.get("oid")));
                }
            }

            parentItemInfo = new ParentItemTableData(tableVec);
            parentItemTB.setModel(parentItemInfo);
            parentItemTB.repaint();
            repaint();

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    // 체크아웃
    private void checkOutBtn_process() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();
        BOMSearchUtilDao utilDao = new BOMSearchUtilDao();

        // 모부품 상태가 작업중이거나, 재작업인 경우에만 체크아웃함
        if ( !(BOMBasicInfoPool.getPublicBOMStatus().equals("INWORK") ||  BOMBasicInfoPool.getPublicBOMStatus().equals("REWORK")) )
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return;
        }

        if (parentItemTB.getRowCount() == 0) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00151")/*모부품 목록이 없습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/);
        } else {
            parentItemVec = getParentItemCodeFromTB(false);

            try {
                if (parentItemVec != null && parentItemVec.size() > 0) {
                    for (int inx = 0; inx < parentItemVec.size(); inx++) {

                        // 작업완료 상태가 아닌 경우에만 체크아웃 함
                        String strStatus = searchDao.getEndWorkingFlag(ecoHeaderNo, (String)parentItemVec.get(inx), this.myId);
                        if ( strStatus != null && !strStatus.equals("") && !strStatus.equals("4"))
                        {
                            // 체크아웃 처리
                            checkoutDao.setCheckOut( (String)parentItemVec.get(inx), utilDao.getLastestPartVersion((String)parentItemVec.get(inx)),  this.myId );

                            searchDao.updateEndWorkingFlag(this.ecoHeaderNo, (String)parentItemVec.get(inx), this.myId, "checkOut");
//                            this.endWorkingFlag = searchDao.getEndWorkingFlag(this.ecoHeaderNo, BOMBasicInfoPool.getPublicModelName(), this.myId);
                            if (mChangeActivityTB.getRowCount() > 0) saveBtn_process(false);
//                            else saveTemp();

                            this.endWorkingFlag = "3";        // 자동 체크아웃상태이므로
                            isParentItemCheckOut = true;
                        }
                    }
                }
            } catch (Exception e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                Kogger.error(getClass(), e);
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

//            refreshParentItemTB();
            btnStatus();
        }
    }


    // 체크인
    private void checkInBtn_process() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

        // 체크아웃 상태가 아닌경우 그냥 돌아감
        if ( !this.endWorkingFlag.equals("3") )
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return;
        }

        parentItemVec = getParentItemCodeFromTB(false);

        try {
            if (parentItemVec != null && parentItemVec.size() > 0) {
                for (int inx = 0; inx < parentItemVec.size(); inx++) {

                    // 체크인 처리
                    checkoutDao.setCheckIn( (String)parentItemVec.get(inx), this.myId );

                    searchDao.updateEndWorkingFlag(this.ecoHeaderNo, (String)parentItemVec.get(inx), this.myId, "checkIn");
//                        this.endWorkingFlag = searchDao.getEndWorkingFlag(this.ecoHeaderNo, BOMBasicInfoPool.getPublicModelName(), this.myId);
                }
                this.endWorkingFlag =  "2";        // 자동 체크인 이므로
            }
        } catch (Exception e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), e);
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        btnStatus();
    }


    // 버튼상태 설정
    private void btnStatus() {
Kogger.debug(getClass(), "@@@@@@@@ endWorkingFlag : " + endWorkingFlag);
Kogger.debug(getClass(), "@@@@@@@@ isParentItemCheckOut : " + isParentItemCheckOut);

        if (this.endWorkingFlag.equals("")) {

            changeInsertBtn.setEnabled(false);
            changeRemoveBtn.setEnabled(false);

            endWorkingBtn.setEnabled(false);
//            abortEndWorkingBtn.setEnabled(false);
            changeValidationBtn.setEnabled(false);
            saveBtn.setEnabled(false);

        } else {
            // endWorking 이 아니면..
            if (!this.endWorkingFlag.equals("4")) {
                if (this.endWorkingFlag.equals("3") && isParentItemCheckOut) {            // check out 된 상태
                    changeInsertBtn.setEnabled(true);
                    changeRemoveBtn.setEnabled(true);

                    endWorkingBtn.setEnabled(true);        // 항상 자동 체크아웃 상태이므로 작업완료 버튼이 보여야 함
//                    abortEndWorkingBtn.setEnabled(false);
                    changeValidationBtn.setEnabled(true);
                    saveBtn.setEnabled(true);

                } else if (this.endWorkingFlag.equals("1") || (this.endWorkingFlag.equals("2") && !isParentItemCheckOut)) {    // check in 된 상태 (보일 경우 없음)
                    changeInsertBtn.setEnabled(false);
                    changeRemoveBtn.setEnabled(false);

                    endWorkingBtn.setEnabled(true);
//                    abortEndWorkingBtn.setEnabled(false);
                    changeValidationBtn.setEnabled(true);
                    saveBtn.setEnabled(false);
                }

            // end working 상태이면 abort end working 버튼만 보인다.
            } else {
                changeInsertBtn.setEnabled(false);
                changeRemoveBtn.setEnabled(false);

                endWorkingBtn.setEnabled(false);
//                abortEndWorkingBtn.setEnabled(true);
                changeValidationBtn.setEnabled(false);
                saveBtn.setEnabled(false);
            }
        }

        if ((isSysAdmin || isBizAdmin) && (this.endWorkingFlag.equals("1") || (this.endWorkingFlag.equals("2") && !isParentItemCheckOut))) {
            endWorkingBtn.setEnabled(true);
        }
    }

    private Vector getParentItemCodeFromTB()
    {
        Vector itemVec = new Vector();

        for (int i=0; i<parentItemTB.getRowCount(); i++)
        {
            String state = (String)parentItemTB.getValueAt(i, 3);
            if (state.toUpperCase().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/))
            {
                itemVec.addElement(parentItemTB.getValueAt(i, 0));
            }
        }

        return itemVec;
    }

    private Vector getParentItemCodeFromTB(boolean revise) {
        Vector itemVec = new Vector();

        for (int i=0; i<parentItemTB.getRowCount(); i++) {
            String state = (String)parentItemTB.getValueAt(i, 3);
            if (revise && !state.toUpperCase().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/)) continue;

            itemVec.addElement(parentItemTB.getValueAt(i, 0));
        }

        return itemVec;
    }


    /**
     * 일괄변경 내역의 추가 button 을 클릭하면 행을 추가한다.
     */
    private void insertBtn_process() {

        if (parentItemTB.getRowCount() <= 0) {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00153")/*모부품이 존재하지 않습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/);

        } else {
            String actCode = (String)changeActCodeCombo.getSelectedItem();
            int activitySize = mChangeActivityTB.getRowCount();

            boolean isRemove = false;        // 현재 childItem이 삭제 되었는지
            boolean isAdd = false;            // 현재 childItem이 다시 추가 되었는지
            boolean isUpdate = false;            // 현재 childItem이 다시 Update 되었는지
            int removeIndex = -1;
            int addIndex = -1;

            if (activitySize > 0 && (actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/) || actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/))) {
                for (int i=activitySize-2; i>=0; i-=2) {
                    String actionCode = (String)mChangeActivityTB.getValueAt(i, 0);
                    String itemCode = (String)mChangeActivityTB.getValueAt(i+1, 1);

                    if (!isRemove && actionCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/)) {
                        isRemove = true;
                        removeIndex = i;

                    } else if (!isAdd && actionCode.equals("추가") && childItemCode.equals(itemCode)) {
                        isAdd = true;
                        addIndex = i;
                    }
                }

                if (isRemove && removeIndex > addIndex) {
                    openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00175", childItemCode)/*부품 [{0}] 이 삭제되었습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00340")/*추가 실패*/);
                    return;
                }
            }

            if (activitySize > 0 && actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/)) {
                for (int i=activitySize-2; i>=0; i-=2) {
                    String actionCode = (String)mChangeActivityTB.getValueAt(i, 0);

                    if (actionCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {
                        if (JOptionPane.showConfirmDialog(this, "자부품이 수정되었습니다.\n'수정 항목'을 삭제하고, '삭제 항목'을 추가 하시겠습니까?", "선택", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            // Update 삭제
                            Vector mChangeActivity = mChangeActivityModel.getDataVector();

                            mChangeActivity.remove(i+1);
                            mChangeActivity.remove(i);

                            assyCompVec.remove(i+1);
                            assyCompVec.remove(i);

                            mChangeActivityModel.setDataVector(mChangeActivity, mChangeActivityHeaderTitle);

                            // table 새로 그려주기
                            mChangeActivityModel.fireTableRowsInserted(0, mChangeActivityModel.getRowCount() - 1);
                            mChangeActivityTB.clearSelection();

                            // Cell 병합
                            cellSpan();
                        } else {
                            return;
                        }
                    }
                }
            }

            if (activitySize > 0 && actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {
                for (int i=activitySize-2; i>=0; i-=2) {
                    String actionCode = (String)mChangeActivityTB.getValueAt(i, 0);

                    if (!isUpdate && actionCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {
                        isUpdate = true;
                        break;
                    }
                }

                if (isUpdate) {
                    openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00173", childItemCode)/*부품 [{0}] 은 이미 수정되었습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00261")/*알림*/);
                    return;
                }
            }

            Vector mChangeActivity = mChangeActivityModel.getDataVector();

            Vector data11 = new Vector();
            BOMAssyComponent assyComponent1 = new BOMAssyComponent();

            data11.addElement(actCode);
            if (actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/) || actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/)) {
                data11.addElement(this.childItemCode);
                data11.addElement(this.childItemDesc);
                data11.addElement(this.childItemVersion);
                data11.addElement((String) wfmTyptHash.get(this.childItemStatus));                        // 결재상태 (한글)
                data11.addElement(this.childItemQuantity);
                data11.addElement(this.childItemUIT);
                data11.addElement(getSubstituteStr(this.childItemSubstituteVector));
                data11.addElement(this.childItemBoxQuantity);

                assyComponent1.setParentItemCodeStr((String)parentItemTB.getValueAt(0, 0));
                assyComponent1.setItemCodeStr(this.childItemCode);
                assyComponent1.setDescStr(this.childItemDesc);
                assyComponent1.setStatusStr(this.childItemStatus);                                        // 결재상태 (영문)
                assyComponent1.setStatusKrStr((String) wfmTyptHash.get(this.childItemStatus));        // 결재상태 (한글)
                assyComponent1.setUitStr(this.childItemUITSave);                                            // 실제 저장되는 단위 정보
                assyComponent1.setQuantityDbl(new Double(this.childItemQuantity));
                assyComponent1.setSubAssyComponent(this.childItemSubstituteVector);
                assyComponent1.setBoxQuantityDbl(new Double(this.childItemBoxQuantity));

            } else {
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
                data11.addElement("");
            }

            Vector data12 = new Vector();
            BOMAssyComponent assyComponent2 = new BOMAssyComponent();

            data12.addElement("");
            if (actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {
                data12.addElement(this.childItemCode);
                data12.addElement(this.childItemDesc);
                data12.addElement(this.childItemVersion);
                data12.addElement((String) wfmTyptHash.get(this.childItemStatus));                    // 결재상태 (한글)
                data12.addElement(this.childItemQuantity);
                data12.addElement(this.childItemUIT);
                data12.addElement(getSubstituteStr(this.childItemSubstituteVector));
                data12.addElement(this.childItemBoxQuantity);

                assyComponent2.setParentItemCodeStr((String)parentItemTB.getValueAt(0, 0));
                assyComponent2.setItemCodeStr(this.childItemCode);
                assyComponent2.setDescStr(this.childItemDesc);
                assyComponent2.setStatusStr(this.childItemStatus);                                    // 결재상태 (영문)
                assyComponent2.setStatusKrStr((String) wfmTyptHash.get(this.childItemStatus));    // 결재상태 (한글)
                assyComponent2.setQuantityDbl(new Double(this.childItemQuantity));
                assyComponent2.setUitStr(this.childItemUITSave);                                        // 실제 저장되는 단위 정보
                assyComponent2.setSubAssyComponent(this.childItemSubstituteVector);
                assyComponent2.setBoxQuantityDbl(new Double(this.childItemBoxQuantity));
            } else {
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
                data12.addElement("");
            }

            mChangeActivity.addElement(data11);
            mChangeActivity.addElement(data12);

            assyCompVec.addElement(assyComponent1);
            assyCompVec.addElement(assyComponent2);

            mChangeActivityModel.setDataVector(mChangeActivity, mChangeActivityHeaderTitle);

            // table 새로 그려주기
            mChangeActivityModel.fireTableRowsInserted(0, mChangeActivityModel.getRowCount() - 1);
            mChangeActivityTB.clearSelection();

            // Cell 병합
            cellSpan();
        }
    }

    /**
     * 일괄변경 내역의 삭제 button 을 클릭하면 선택된 행을 삭제한다.
     */
    private void changeRemoveBtn_process() {

        int selectedRow = mChangeActivityTB.getSelectedRow();

        if (selectedRow % 2 == 0) selectedRow++;

        if (selectedRow < 0) {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00221")/*선택된 행이 없습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00261")/*알림*/);

        } else if (selectedRow > 0) {
            Vector mChangeActivity = mChangeActivityModel.getDataVector();

            mChangeActivity.remove(selectedRow);
            mChangeActivity.remove(selectedRow - 1);

            assyCompVec.remove(selectedRow);
            assyCompVec.remove(selectedRow - 1);

            mChangeActivityModel.setDataVector(mChangeActivity, mChangeActivityHeaderTitle);

            // table 새로 그려주기
            mChangeActivityModel.fireTableRowsInserted(0, mChangeActivityModel.getRowCount() - 1);
            mChangeActivityTB.clearSelection();

            // Cell 병합
            cellSpan();

            repaint();
        }
    }

    // BOM 검증 버튼 클릭 시
    private void validationBtn_process() {
        if (this.parentItemTB.getRowCount() > 0 && this.mChangeActivityTB.getRowCount() > 0) {
            MultipleBOMECOValidationDialog validationDialog = new MultipleBOMECOValidationDialog(this.frmDesktop, this.app, parentItemTB, mChangeActivityTB, assyCompVec);

            doValidation = true;
            if (!BOMECOBasicInfoPool.getHasErrorInValidation()) endWorkingBtn.setEnabled(true);
        } else {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00059")/*검증할 항목이 존재하지 않습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
        }
    }

    // 작업완료 버튼 클릭 시
    private void endWorkingBtn_process() {
Kogger.debug(getClass(), "@@@@@ BOMECOBasicInfoPool.getHasErrorInValidation() : " + BOMECOBasicInfoPool.getHasErrorInValidation());

        try {
            if (doValidation && !BOMECOBasicInfoPool.getHasErrorInValidation()) {
                this.endWorkingCmd.executeCommand();
            } else {
                openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00023")/*BOM 검증을 먼저 수행하십시오.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00058")/*검증 미수행*/);
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }


    // 저장 버튼 클릭 시
    private void saveBtn_process(boolean validation) {
        boolean passValidation = true;
        if (mChangeActivityTB.getRowCount() > 0) {
            if (validation) passValidation = saveValidation();

            if (passValidation) {
                Vector saveQueryVector = new Vector();

                int parentItemTBCount = parentItemTB.getRowCount();
                int changeActTBCount = mChangeActivityTB.getRowCount();

                for (int i=0; i<parentItemTBCount; i++) {

                    saveQueryVector.addElement("DELETE ketbomecocomponent  WHERE ECOHEADERNUMBER = '" + this.ecoHeaderNo + "'    AND  ECOITEMCODE = '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "'");
                    saveQueryVector.addElement("DELETE ketbomecosubstitute     WHERE ECOHEADERNUMBER = '" + this.ecoHeaderNo + "'    AND    ECOITEMCODE = '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "'");
                    saveQueryVector.addElement("COMMIT");

                    for (int j=0; j<changeActTBCount; j=j+2) {
                        // multiple change activity
                        int rowNum = j;
                        String actCode = Utility.checkNVL( (String)mChangeActivityTB.getValueAt(j, 0) );
                        if (actCode.equals("추가")) rowNum++;

                        String beforeQuantity = Utility.checkNVL(mChangeActivityTB.getValueAt(j, 5)).equals("") ? "0.000" : Utility.checkNVL(mChangeActivityTB.getValueAt(j, 5));
                        String afterQuantity = Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 5)).equals("") ? "0.000" : Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 5));

                        String beforeUnit = Utility.checkNVL(mChangeActivityTB.getValueAt(j, 6));
                        if ( beforeUnit != null && !beforeUnit.equals("") )
                        {
                            beforeUnit = "KET_" + beforeUnit;
                        }
                        String afterUnit = Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 6));
                        if ( afterUnit != null && !afterUnit.equals("") )
                        {
                            afterUnit = "KET_" + afterUnit;
                        }

                        String beforeBoxQuantity = Utility.checkNVL(mChangeActivityTB.getValueAt(j, 8)).equals("") ? "0.0" : Utility.checkNVL(mChangeActivityTB.getValueAt(j, 8));
                        String afterBoxQuantity = Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 8)).equals("") ? "0.0" : Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 8));

                        int itemSeq = (j/2 + 1)*10;
                        String strItemSeq = "";
                        if (actCode.equals("추가")) strItemSeq = String.valueOf(itemSeq);
                        else strItemSeq = childItemItemSEQ;

                        String sql = " INSERT INTO ketbomecocomponent "
                                   + " (ECOHEADERNUMBER, PARENTITEMCODE, SEQUENCENUMBER, ITEMSEQ, "
                                   + " ECOCODE, CHILDITEMCODE, ECOITEMCODE, "
                                   + " BEFOREQUANTITY, AFTERQUANTITY, "
                                   + " BEFOREUNIT, AFTERUNIT, "
                                   + " BEFOREBOXQUANTITY, AFTERBOXQUANTITY "
                                   + " ) "
                                   + " VALUES ("
                                   + "  '" + this.ecoHeaderNo + "', "                                                            // ECOHEADERNUMBER
//                                   + "    '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                        // APPLIEDPRODUCTCODE
                                   + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                        // PARENTITEMCODE
                                   + "  '" + itemSeq + "', "                                                                        // SEQUENCENUMBER
                                   + "  '" + strItemSeq + "', "                                                                    // ITEMSEQ
                                   + "  '" + ecoTyptHashSave.get(actCode) + "', "                                            // ECOCODE - Action
                                   + "  '" + Utility.checkNVL(mChangeActivityTB.getValueAt(rowNum, 1)) + "', "        // CHILDITEMCODE
                                   + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                        // ECOITEMCODE
//                                   + "  '" + Utility.checkNVL(mChangeActivityTB.getValueAt(j, 2))   + "', "                // BEFORESTARTDATE
//                                   + "  '" + Utility.checkNVL(mChangeActivityTB.getValueAt(j+1, 2)) + "', "                // AFTERSTARTDATE
                                   + "  "  + beforeQuantity + ", "                                                                // BEFOREQUANTITY
                                   + "  "  + afterQuantity + ", "                                                                 // AFTERQUANTITY
                                   + "  '"  + beforeUnit + "', "                                                                    // BEFOREUNIT
                                   + "  '"  + afterUnit + "', "                                                                     // AFTERUNIT
                                   + "  "  + beforeBoxQuantity + ", "                                                        // BEFOREBOXQUANTITY
                                   + "  "  + afterBoxQuantity                                                                      // AFTERBOXQUANTITY
                                   + ")";

                        saveQueryVector.addElement(sql);

                        // substitute
                        BOMAssyComponent beforeAssyComp = (BOMAssyComponent)assyCompVec.elementAt(j);
                        BOMAssyComponent afterAssyComp = (BOMAssyComponent)assyCompVec.elementAt(j+1);

                        Vector beforeSubstituteComp = beforeAssyComp.getSubAssyComponent();
                        Vector afterSubstituteComp = afterAssyComp.getSubAssyComponent();

                        for (int k=0; k<beforeSubstituteComp.size(); k++) {
                            BOMSubAssyComponent component = (BOMSubAssyComponent)beforeSubstituteComp.elementAt(k);

                            String beforeSubstSql = " INSERT INTO ketbomecosubstitute "
                                                  + " (ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, SUBSTITUTEITEMCODE, ECOITEMCODE, "
                                                  + "  BEFOREQUANTITY, AFTERQUANTITY, "
//                                                  + "  BEFORESTARTDATE, BEFOREENDDATE, "
                                                  + "  SEQUENCENUMBER, ECOCODE)"
                                                  + " VALUES ( "
                                                  + "  '" + this.ecoHeaderNo + "', "                                                                    // ECOHEADERNUMBER
                                                  + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                                // PARENTITEMCODE
                                                  + "  '" + Utility.checkNVL(mChangeActivityTB.getValueAt(rowNum, 1)) + "', "                // CHILDITEMCODE
                                                  + "  '" + component.getSubstituteItemCodeStr() + "', "                                            // SUBSTITUTEITEMCODE
                                                  + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                                // ECOITEMCODE
                                                  + "   " + component.getQuantityDbl() + ", 0, "                                                    // BEFOREQUANTITY, AFTERQUANTITY
//                                                  + "  '" + component.getStartDate() + "', "                                                            // BEFORESTARTDATE
//                                                  + "  '" + component.getEndDate() + "', "                                                            // BEFOREENDDATE
                                                  + "  '" + (j/2 + 1)*10 + "', 'Remove') ";                                                            // SEQUENCENUMBER, ECOCODE - Action

                            saveQueryVector.addElement(beforeSubstSql);
                        }

                        for (int k=0; k<afterSubstituteComp.size(); k++) {
                            BOMSubAssyComponent component = (BOMSubAssyComponent)afterSubstituteComp.elementAt(k);

                            String afterSubstSql  = " INSERT INTO ketbomecosubstitute "
                                                  + " (ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, SUBSTITUTEITEMCODE, ECOITEMCODE, "
                                                  + "  BEFOREQUANTITY, AFTERQUANTITY, "
//                                                  +     "  AFTERSTARTDATE, AFTERENDDATE, "
                                                  + "  SEQUENCENUMBER, ECOCODE)"
                                                  + " VALUES ( "
                                                  + "  '" + this.ecoHeaderNo + "', "                                                                            // ECOHEADERNUMBER
                                                  + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                                        // PARENTITEMCODE
                                                  + "  '" + Utility.checkNVL(mChangeActivityTB.getValueAt(rowNum, 1)) + "', "                        // CHILDITEMCODE
                                                  + "  '" + component.getSubstituteItemCodeStr() + "', "                                                    // SUBSTITUTEITEMCODE
                                                  + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "                                        // ECOITEMCODE
                                                  + "  0, " + component.getQuantityDbl() + ", "                                                            // BEFOREQUANTITY, AFTERQUANTITY
//                                                  + "  '" + component.getStartDate() + "', "                                                                    // AFTERSTARTDATE
//                                                  + "  '" + component.getEndDate() + "', "                                                                    // AFTERENDDATE
                                                  + "  '" + (j/2 + 1)*10 + "', 'Add') ";                                                                            // SEQUENCENUMBER, ECOCODE - Action

                            saveQueryVector.addElement(afterSubstSql);
                        }
                    }
                }

                if (doSql(saveQueryVector)) {
                    if (validation) openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/*저장 성공*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00227")/*성공*/);
                } else {
                    if (validation) openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/*저장 실패*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00258")/*실패*/);
                }
            }
        }
//        else saveTemp();
    }

    // 저장 시 Validation
    private boolean saveValidation() {
        boolean isValidate = false;

        if (mChangeActivityTB.getCellEditor() != null)
            mChangeActivityTB.getCellEditor().stopCellEditing();    // 일괄변경 내역의 Cell Edit 작업을 중지

        boolean isEmptyItemCode = false;                            // Add 일때는 ItemCode를 수동으로 선택해야한다. 따라서 Add 일때 itemCode가 비어 있는지 check
        boolean isEmptyQty = false;                                    // Quantity 가 비었는지 체크
        boolean isInvalidQty = false;                                    // Quantity 가 숫자형식인지 체크

        if (mChangeActivityTB.getRowCount() > 0) {
            for (int i=0; i<mChangeActivityTB.getRowCount(); i = i+2) {
                String actCode = Utility.checkNVL(mChangeActivityTB.getValueAt(i, 0));
                String beforeQty = Utility.checkNVL(mChangeActivityTB.getValueAt(i, 5));
                String afterQty = Utility.checkNVL(mChangeActivityTB.getValueAt(i+1, 5));

                if (actCode.equals("추가")) {
                    if (Utility.checkNVL(mChangeActivityTB.getValueAt(i+1, 1)).equals("")) {
                        isEmptyItemCode = true;
                        break;
                    }

                    if (afterQty.equals("")) {
                        isEmptyQty = true;
                        break;
                    }

                    if (!validateQty(afterQty)) {
                        isInvalidQty = true;
                        break;
                    }

                } else if (actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/)) {

                    if (beforeQty.equals("")) {
                        isEmptyQty = true;
                        break;
                    }

                    if (!validateQty(beforeQty)) {
                        isInvalidQty = true;
                        break;
                    }

                } else if (actCode.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00240")/*수정*/)) {

                    if (beforeQty.equals("") || afterQty.equals("")) {
                        isEmptyQty = true;
                        break;
                    }

                    if (!validateQty(beforeQty) || !validateQty(afterQty)) {
                        isInvalidQty = true;
                        break;
                    }
                }
            }

            if (!isEmptyItemCode &&  !isEmptyQty && !isInvalidQty) {
                isValidate = true;

            } else if (isEmptyItemCode) {
                openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00190")/*부품번호가 존재하지 않는 행이 있습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
            } else if (isEmptyQty) {
                openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00235")/*수량이 존재하지 않는 행이 있습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
            } else if (isInvalidQty) {
                openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00300")/*잘못된 수량이 입력된 행이 있습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
            }

        } else {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00275")/*일괄변경 내역이 존재하지 않습니다.*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
        }

        return isValidate;
    }

    private void saveTemp() {
        Vector saveQueryVector = new Vector();
        int parentItemTBCount = parentItemTB.getRowCount();

        for (int i=0; i<parentItemTBCount; i++) {

            saveQueryVector.addElement("DELETE ketbomecocomponent  WHERE ECOHEADERNUMBER = '" + this.ecoHeaderNo + "'    AND  ECOITEMCODE = '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "'");
            saveQueryVector.addElement("DELETE ketbomecosubstitute     WHERE ECOHEADERNUMBER = '" + this.ecoHeaderNo + "'    AND    ECOITEMCODE = '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "'");
            saveQueryVector.addElement("COMMIT");

            String sql = " INSERT INTO ketbomecocomponent "
//                       + " (ECOHEADERNUMBER, APPLIEDPRODUCTCODE, PARENTITEMCODE, CHILDITEMCODE, ITEMSEQ) "
                       + " (ECOHEADERNUMBER, PARENTITEMCODE, CHILDITEMCODE, ECOITEMCODE,  ITEMSEQ, ECOCODE) "
                       + " VALUES (    "
                       + "  '" + this.ecoHeaderNo + "', "                                        // ECOHEADERNUMBER
//                       + "    '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "',     "    // APPLIEDPRODUCTCODE
                       + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "    // PARENTITEMCODE
                       + "  '" + Utility.checkNVL(childItemCode) + "', "                        // CHILDITEMCODE
                       + "  '" + Utility.checkNVL(parentItemTB.getValueAt(i, 0)) + "', "    // ECOITEMCODE
                       + "  '0'  "                                                                    // ITEMSEQ
                       + "  'Update'  "                                                            // ECOCODE
                       + ")";
Kogger.debug(getClass(), "@@@ sql : " + sql);
            saveQueryVector.addElement(sql);
        }

        if (doSql(saveQueryVector)) {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/*저장 성공*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00227")/*성공*/);
        } else {
            openMessage(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/*저장 실패*/, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00258")/*실패*/);
        }
    }


    /**
     * 입력된 숫자형식을 판단
     * @param qty        String 형식의 숫자
     * @param isNumber    숫자형식여부
     */
    private boolean validateQty(String qty) {
        String number = "1234567890.";

        boolean isNumber = true;                    // day가 숫자또는 - 로 구성되어 있는지 (yyyy-MM-dd)

        if (qty.indexOf(".") < 0 || qty.indexOf(".") == qty.lastIndexOf(".")) {
            for (int i=0; i<qty.length(); i++) {
                String cha = qty.substring(i, i+1);

                if (number.indexOf(cha) < 0) {
                    isNumber = false;
                    break;
                }
            }
        } else {
            isNumber = false;
        }

        return isNumber;
    }

    private void cancelBtn_process() {

        // 자동 체크인
        checkInBtn_process();

        super.dispose();
    }

    private void cellSpan() {
        // Cell 병합
        CellSpan cellAtt =(CellSpan)mChangeActivityModel.getCellAttribute();
        for (int i=0; i<mChangeActivityModel.getRowCount(); i++) {
            if (i%2 == 1) continue;

            int[] columns = {0};
            int[] rows    = {i, i+1};

            cellAtt.combine(rows, columns);
        }
    }
}
