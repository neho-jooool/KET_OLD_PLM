package e3ps.bom.command.bomproperty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Painter;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.DateUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMPropertyInsertDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private BtnListener btnListener;
    private JButton okBtn, cancelBtn;

    private AbstractAIFUIApplication app;
    private BOMAssyComponent rootComponent;
    private BOMRegisterApplicationPanel pnl;
    private JTreeTable treeTable;

    private JLabel itemCodeLbl, descriptionLbl, quantityLbl;
    private JTextField itemCodeTfl, descriptionTfl, quantityTfl;
    private JLabel materialLbl, hardnessFromLbl, hardnessToLbl, designDateLbl;
    private JTextField materialTfl, hardnessFromTfl, hardnessToTfl, designDateTfl;

    private JFrame desktop;
    private JPanel centerPanel2;
    private Registry appReg;

    private Hashtable<String, String> hash;
    private Hashtable<String, String> hashSelected;
    private String newSortSeq;
    private String newItemSeq;
    private boolean isRootSelected = false;
    private BOMAssyComponent selectedComponent;

    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
    String ecoNumber = BOMBasicInfoPool.getBomEcoNumber();

    class BtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("OK")) {
                if (dataValidate()) {
                    setOK();
                    disposeScreen();
                }
            } else {
                disposeScreen();
            }
        }
    }

    public BOMPropertyInsertDialog(JFrame desktop, boolean modal, AbstractAIFUIApplication app, BOMRegisterApplicationPanel pnl,
                                            BOMAssyComponent rootComponent, JTreeTable treeTable) {
        super(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00256")/*신규 부품 추가(금형)*/ , modal);
        this.desktop = desktop;
        this.app = app;
        this.appReg = Registry.getRegistry(app);
        this.pnl = pnl;
        this.rootComponent = rootComponent;
        this.treeTable = treeTable;

        // 선택된 노드 가져오기
        BOMTreeNode tempnode1 = (BOMTreeNode) treeTable.getTree().getSelectionPath().getLastPathComponent();    // 선택된 Row 의 node 를 구해서 BOMComponent를 빼낸다.
        selectedComponent = tempnode1.getBOMComponent();

        // 선택한 노드가 Root Node 인지 체크
        if(selectedComponent.getItemCodeStr().equals(rootComponent.getItemCodeStr()))
            isRootSelected = true;

        // 선택한 노드가 Root Node 이면 최하위에 부품을 추가함
        if (isRootSelected) {
            if (bomGubunFlag) {        // 신규
                hashSelected = BOMSearchUtilDao.getLastNodeItemSeq(rootComponent.getItemCodeStr());            // Root Node 하위의 ItemSeq 의 Max 값을 조회해옴
                newSortSeq = Integer.parseInt((String) hashSelected.get("itemseq")) + 10 + "";
            } else {                        // 변경
                hashSelected = BOMSearchUtilDao.getLastNodeItemSeqEco(rootComponent.getEcoHeaderNumberStr(), rootComponent.getItemCodeStr());// Root Node 하위의 ItemSeq 의 Max 값을 조회해옴
                newSortSeq = ((String) hashSelected.get("seq")).replace("0001","");
                newItemSeq = Integer.parseInt((String) hashSelected.get("itemseq")) + 10 + "";
            }
        } else {
            if (bomGubunFlag) {        // 신규
                newSortSeq = (selectedComponent.getItemSeqInt() + 10) + "";
            } else {                        // 변경
                hashSelected = BOMSearchUtilDao.getLastNodeItemSeqEco(rootComponent.getEcoHeaderNumberStr(), rootComponent.getItemCodeStr());// Root Node 하위의 ItemSeq 의 Max 값을 조회해옴
                newSortSeq = (selectedComponent.getSortOrderStr()).replace("0001","");
                newItemSeq = Integer.parseInt((String) hashSelected.get("itemseq")) + 10 + "";
            }
        }
Kogger.debug(getClass(), "@@@@@@@@@@@@@@   newSortSeq : " + newSortSeq);
Kogger.debug(getClass(), "@@@@@@@@@@@@@@   newItemSeq : " + newItemSeq);

        // 팝업 화면의 "X" 를 눌러 닫으려 할 경우 Action
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmAutoSave();
            }
        });

        try {
            jInit();
            this.setSize(660,350);        // 팝업 전체 사이즈 지정

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            this.setLocation(dimCenter.width, dimCenter.height);
            this.setVisible(true);
        } catch(Exception e)    {
            Kogger.error(getClass(), e);
        }
    }

    public void disposeScreen() {
        int count = getComponentCount();
        for(int i=0; i<count; i++) {
              Component c = getComponent(i);
              this.remove(c);
              c = null;
        }
        super.dispose();
        System.gc();
      }

    public boolean setOK() {
        try {
            hash = new Hashtable<String, String>();
            Hashtable<String, String> hashRsltPart = null;

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            hash.put("dieno", rootComponent.getItemCodeStr());            // 금형 번호 (Die No)
            hash.put("number", itemCodeTfl.getText().trim());                // 금형 부품번호
            hash.put("name", descriptionTfl.getText().trim());                // 부품명
            hash.put("unit", "EA");                                                // 기본단위 설정 (ERP는 대문자 'EA'임)
            hash.put("partType", "DIEM");                                        // 금형 부품 타입('M')으로 설정
            hash.put("quantity", quantityTfl.getText().trim());                    // 수량
            hash.put("material", materialTfl.getText().trim());                    // 재질
            hash.put("hardnessFrom", hardnessFromTfl.getText().trim());    // 경도(From)
            hash.put("hardnessTo", hardnessToTfl.getText().trim());        // 경도(To)
            hash.put("designDate", designDateTfl.getText().trim());            // 설계일자

            // 1. 신규 금형부품 생성 (ERP)
            Vector<String> vecResult =  KETPartHelper.service.createSapPart(hash);
            String strSuccess = vecResult.elementAt(0);

            if (strSuccess != null && strSuccess.equals("S")) {        // ERP 전송이 성공한 경우에만 PLM에 신규 금형부품 생성

                // 2. 신규 금형부품 생성 (PLM)
                hashRsltPart = KETPartHelper.service.create(hash);
                if (hashRsltPart == null) {
                    MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00255")/*신규 금형 부품 PLM 생성에 실패하였습니다.  관리자에게 문의하세요.*/, "Error", 0);
                    m.setModal(true);
                    m.setVisible(true);
                    Kogger.debug(getClass(), "########  [PLM] Mold Part Create Fail !! ########");
                }

                // 3. 금형 BOM 신규 자부품 추가
                createComponent();
            }else {
                MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00254")/*신규 금형 부품 ERP 전송에 실패하였습니다.  관리자에게 문의하세요.*/, "Error", 0);
                m.setModal(true);
                m.setVisible(true);
                Kogger.debug(getClass(), "######## [ERP Interface Fail] Mold Part Create ########");
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            pnl.requestFocus();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean dataValidate() {

        try {
//            char value;
            Double checkDbl = new Double(quantityTfl.getText().trim());
//            int cnt = quantityTfl.getText().length();

            if(checkDbl.doubleValue() == 0) {
                MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00232", 0)/*수량은 '{0}'이 될 수 없습니다.*/, "Error", 0);
                m.setModal(true);
                m.setVisible(true);

                return false;
            }

//윤도혁 주임 요구사항(수량에 (-) 입력 가능하도록)으로 주석처리함 [2011-01-17]
//            for (int inx = 0; inx < cnt; inx++) {
//                value = quantityTfl.getText().charAt(inx);
//
//                if ((value == '-')) {
//                    MessageBox m = new MessageBox(desktop, messageRegistry.getString("quantityFormatError"), "Error", 0);
//                    m.setModal(true);
//                    m.setVisible(true);
//
//                    return false;
//                }
//            }
        } catch (NumberFormatException e) {
            quantityTfl.setCaretPosition(0);
            quantityTfl.selectAll();
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("quantityFormatError"), "Error", 0);
            m.setModal(true);
            m.setVisible(true);

            return false;
        }
        return true;
    }

    private void createComponent(){

        String versionStr ="";
        String sortOrder = "";
        String subItemCode= "";
        String subItemDesc= "";
        String subItemStatus= "";
        String subItemStatusKr="";
        String subItemUnit= "";
        String subItemVersion= "";
        // Added by MJOH, 2011-04-03
        String partTypeStr = "";

        Vector version;
        Vector itemVec;
        Hashtable inputParams = new Hashtable();
        Vector subAssyVec = new Vector();
        subAssyVec.removeAllElements();

        BOMAssyComponent assyComponent = null;
        BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();
        KetMainJTreeTable km = new KetMainJTreeTable();

        pnl.clearBOMList();    // 이전의 BOM 목록을 모두 지움
//        pnl.mainEditorPane.clearBOMListNotCheckIn();


        // root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
        String strRootModelName = rootComponent.getItemCodeStr();
        WTPart part = null;
        String strType = "";
        try {
            part = KETPartHelper.service.getPart(strRootModelName);
            strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
        } catch (Exception e1) {
            Kogger.error(getClass(), e1);
        }

        if (PartUtil.isProductType(strType)) {    // 제품인 경우
            km.setGenMain(app);
        } else {                                                // 금형인 경우
            km.setMoldMain(app);
        }

        itemVec = new Vector();
        itemVec.addElement(strRootModelName);

        // 화면 Refresh
        Vector vecOpenPublicBOM = new Vector();
        vecOpenPublicBOM.removeAllElements();

        // 신규로 생성할 부품의 Item Seq 생성
        // 선택한 부품의 다음 위치로 지정함, 선택한 부품이 없거나 Root Node 인 경우에는 제일 하위로 추가한다
        int tempSeq = 0;
        if (bomGubunFlag) {    // 신규
            tempSeq = Integer.parseInt(newSortSeq.substring(0, newSortSeq.length()-1));
        } else {
            tempSeq = Integer.parseInt(newSortSeq) + 2;
        }
        if (tempSeq < 10)
            sortOrder = "000" + tempSeq;
        else if (tempSeq < 100)
            sortOrder = "00" + tempSeq;
        else if (tempSeq < 1000)
            sortOrder = "0" + tempSeq;
        else if (tempSeq < 10000)
            sortOrder = newSortSeq;

        subItemCode = itemCodeTfl.getText().trim();
        try
        {
            Vector queryResult = new Vector();
            Hashtable inputHash = new Hashtable();
            Hashtable hasSearchItemResult = new Hashtable();

            inputHash.put("itemCode", subItemCode);
            inputHash.put("orgCode", BOMBasicInfoPool.getOrgCode());
            queryResult = KETBomHelper.service.searchItem(inputHash);

            hasSearchItemResult = (Hashtable)queryResult.elementAt(0);

            subItemDesc = (String)hasSearchItemResult.get("description");
            subItemStatus = (String)hasSearchItemResult.get("status");
            subItemStatusKr = (String)hasSearchItemResult.get("statusKr");
            subItemUnit = (String)hasSearchItemResult.get("defaultunit");
            subItemVersion = (String)hasSearchItemResult.get("version") + "." + (String)hasSearchItemResult.get("iteration");
            // Added by MJOH, 2011-04-03
            partTypeStr = (String)hasSearchItemResult.get("typeValue");
        }
        catch(Exception e)
        {
            subItemDesc = "";
            subItemStatus = "";
            subItemStatusKr = "";
            subItemUnit = "";
            subItemVersion = "";
            Kogger.debug(getClass(), "Excel Item Search Error 002-"+newSortSeq);
        }

        //assyComponent = new BOMAssyComponent(subItemCode,true);  //shin...이걸 쓰면 마지막이 최상위껄루 나옴.
        assyComponent = new BOMAssyComponent(subItemCode);

        assyComponent.setParentItemCodeStr(rootComponent.getItemCodeStr());
        assyComponent.setItemCodeStr(itemCodeTfl.getText().trim());
        assyComponent.setSortOrderStr("0001"+sortOrder);
        assyComponent.setDescStr(subItemDesc);
        assyComponent.setQuantityDbl(Double.parseDouble(quantityTfl.getText().trim())) ;  //수량
        assyComponent.setLevelInt(new Integer(1));
        assyComponent.setSeqInt(new Integer(0));
        if (bomGubunFlag) {        // 신규
            assyComponent.setItemSeqInt(new Integer(newSortSeq));
        } else {                        // 변경
            assyComponent.setItemSeqInt(new Integer(newItemSeq));
        }
        assyComponent.setNewFlagStr("NEW");
        assyComponent.setSecondMarkStr("NEW");
        assyComponent.setStartDate(Utility.currentDate());
        assyComponent.setStatusStr(subItemStatus);                              // 결재상태
        assyComponent.setStatusKrStr(subItemStatusKr);                          // 결재상태 한글
        assyComponent.setUitStr(subItemUnit);                                     // 기본단위
        assyComponent.setMaterialStr(materialTfl.getText().trim());             // 재질
        assyComponent.setHardnessFrom(hardnessFromTfl.getText().trim());// 경도(From)
        assyComponent.setHardnessTo(hardnessToTfl.getText().trim());         // 경도(To)
        assyComponent.setDesignDate(designDateTfl.getText().trim());         // 설계일자
        assyComponent.setEcoCodeStr("Add");                                    // Eco Code :: 신규이므로 "Add" 로 셋팅함
        assyComponent.setBoxQuantityDbl(new Double(1.0));                    // 포장수량 정보 기본셋팅 (1.0)
        // Added by MJOH, 2011-04-03
        assyComponent.setIBAPartType( partTypeStr );

        try {
            itemVec.addElement(subItemCode);
            assyComponent.setVersionStr(subItemVersion); // 버전
            subAssyVec.addElement(assyComponent);

Kogger.debug(getClass(), "@@@@@@@@ BOMECOBasicInfoPool.getBomEcoNumber() : " + BOMECOBasicInfoPool.getBomEcoNumber());

            // Root Node 가 아닌 부품 선택 시, 자부품 등록 이전에 선택한 부품 하위의 부품들 ItemSeq 조정 (+10씩)
            if (!isRootSelected) {
                if (bomGubunFlag) {    // 신규
                    BOMSearchUtilDao.updateItemSeqNew(rootComponent.getItemCodeStr(), selectedComponent.getSortOrderStr());
                } else {                    // 변경
                    BOMSearchUtilDao.updateItemSeqECO(BOMECOBasicInfoPool.getBomEcoNumber(), rootComponent.getItemCodeStr(), selectedComponent.getSortOrderStr());
                }
            }

            // 자부품 등록
            if (bomGubunFlag) {    // 신규
                componentBOM(rootComponent.getItemCodeStr(), subAssyVec);
            } else {                    // 변경
                componentBOMEco(BOMECOBasicInfoPool.getBomEcoNumber(), subAssyVec);
            }

            MyBOMQry qry = new MyBOMQry();
            if (bomGubunFlag) {    // 신규
                vecOpenPublicBOM = qry.getBOMOpen(rootComponent.getItemCodeStr());
            } else {                    // 변경
                vecOpenPublicBOM = qry.getBOMEcoOpen(BOMECOBasicInfoPool.getBomEcoNumber(), rootComponent.getItemCodeStr());
            }

            // BOM 열기
            pnl.openBOMData(rootComponent, vecOpenPublicBOM);

            // 작업자의 작업상태가 4(작업완료) 가 아닌 경우에만 자동으로 Check-Out 처리 함
            String strStatus = "";
            BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
            String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();
            String bomStatusKr = BOMBasicInfoPool.getPublicBOMStatusKr();

            if (bomGubunFlag) {    // 신규
                strStatus = utilDao.getEndWorkingFlagNew(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
            } else {                    // 변경
                strStatus = utilDao.getEndWorkingFlag(BOMECOBasicInfoPool.getBomEcoNumber(), BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
            }

Kogger.debug(getClass(), "@@@@ strStatus : " + strStatus);
Kogger.debug(getClass(), "@@@@ bomStatus : " + bomStatus);
Kogger.debug(getClass(), "@@@@ bomStatusKr : " + bomStatusKr);

            if ( (strStatus != null && !strStatus.equals("") && !strStatus.equals("4")) &&
                 (  bomStatusKr.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00299")/*작업중*/) || bomStatusKr.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00301")/*재작업*/) || bomStatus.equals("INWORK") || bomStatus.equals("REWORK") ) ) {

                if ( bomStatusKr.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00299")/*작업중*/) || bomStatus.equals("INWORK") ) {
                    BOMBasicInfoPool.setPublicBOMStatus("INWORK");
                } else if ( bomStatusKr.equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00301")/*재작업*/) || bomStatus.equals("REWORK") ) {
                    BOMBasicInfoPool.setPublicBOMStatus("REWORK");
                }
Kogger.debug(getClass(), "@@@@ 여기 들어왔니?? ");
                // Panel 초기화(체크인 포함) 하면서 BOMBasicInfoPool 의 ECO Number 정보가 사라지므로 다시 셋팅해준다
                BOMBasicInfoPool.setBomEcoNumber(ecoNumber);

                //shin...체크아웃 상태로 만듬............................................................................................................................................................................
//                rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
                CheckOutCmd coc = new CheckOutCmd(app.getDesktop(), app);
                BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
                coc.checkOut( nodes, itemVec, BOMBasicInfoPool.getUserName(), false, rootComponent.getVersionStr() );
//                checkoutDao.setCheckOut( BOMBasicInfoPool.getPublicModelName(), subItemVersion, BOMBasicInfoPool.getUserId() );
                // .................................................................................................................................................................................................................................
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void confirmAutoSave() {
        if (JOptionPane.showConfirmDialog(this, messageRegistry.getString("save"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (dataValidate()) {
                setOK();
            }
        }
        disposeScreen();
        setVisible(false);
    }

    protected AbstractAIFUIApplication getApplication() {
        return app;
    }

    private void jInit() {
        Registry appReg = Registry.getRegistry(app);

        JPanel totalPanel = new JPanel(new BorderLayout());

        JPanel propertyPanel01 = new JPanel();
        propertyPanel01.setLayout(new BoxLayout(propertyPanel01, BoxLayout.Y_AXIS));
        propertyPanel01.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00119")/*기본정보*/, 0, 0, FontList.defaultFont));

        // 부품번호 Panel
        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemCodeLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
        itemCodeLbl.setPreferredSize(new Dimension(100,21));
        itemCodeLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemCodeLbl.setFont(FontList.defaultFont);
        panel01.add(itemCodeLbl);

        itemCodeTfl = new JTextField(17);
        itemCodeTfl.setPreferredSize(new Dimension(150, 21));
        itemCodeTfl.setFont(FontList.defaultFont);
        panel01.add(itemCodeTfl);

        propertyPanel01.add(panel01);

        // 부품명 Panel
        JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
        descriptionLbl.setPreferredSize(new Dimension(100,21));
        descriptionLbl.setHorizontalAlignment(JLabel.RIGHT);
        descriptionLbl.setFont(FontList.defaultFont);
        panel02.add(descriptionLbl);

        descriptionTfl = new JTextField();
        descriptionTfl.setPreferredSize(new Dimension(425, 21));
        descriptionTfl.setFont(FontList.defaultFont);
        panel02.add(descriptionTfl);

        propertyPanel01.add(panel02);

        JPanel propertyPanel02 = new JPanel();
        propertyPanel02.setLayout(new BoxLayout(propertyPanel02, BoxLayout.Y_AXIS));
        propertyPanel02.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00218")/*상세정보*/, 0, 0, FontList.defaultFont));

        // 금형 :: 기준수량 / 재질
        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ");
        quantityLbl.setPreferredSize(new Dimension(100,21));
        quantityLbl.setHorizontalAlignment(JLabel.RIGHT);
        quantityLbl.setFont(FontList.defaultFont);
        panel03.add(quantityLbl);

        quantityTfl = new JTextField(17) {
            private static final long serialVersionUID = 1L;
            public void paint(Graphics g)    {
                super.paint(g);
                Painter.paintIsRequired(this, g);
            }
        };
        quantityTfl.setPreferredSize(new Dimension(150, 21));
        quantityTfl.setFont(FontList.defaultFont);
        quantityTfl.addKeyListener(new KeyListener() {
            // 숫자만 입력 가능하도록 함(소수점, "-" 포함)
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if ( ! (Character.isDigit(c) || Character.toString(c).equals(".") || Character.toString(c).equals("-")) ) {
                    e.consume();
                    return;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
        });
        panel03.add(quantityTfl);

        materialLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/ + " : ");
        materialLbl.setPreferredSize(new Dimension(100,21));
        materialLbl.setHorizontalAlignment(JLabel.RIGHT);
        materialLbl.setFont(FontList.defaultFont);
        panel03.add(materialLbl);

        materialTfl = new JTextField(17);
        materialTfl.setPreferredSize(new Dimension(150, 21));
        materialTfl.setFont(FontList.defaultFont);
        panel03.add(materialTfl);

        propertyPanel02.add(panel03);

        // 금형 :: 경도(From) / 경도(To)
        JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        hardnessFromLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/ + " : ");
        hardnessFromLbl.setPreferredSize(new Dimension(100,21));
        hardnessFromLbl.setHorizontalAlignment(JLabel.RIGHT);
        hardnessFromLbl.setFont(FontList.defaultFont);
        panel04.add(hardnessFromLbl);

        hardnessFromTfl = new JTextField(17);
        hardnessFromTfl.setPreferredSize(new Dimension(150, 21));
        hardnessFromTfl.setFont(FontList.defaultFont);
        hardnessFromTfl.addKeyListener(new KeyListener() {
            // 숫자만 입력 가능하도록 함
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
        });
        panel04.add(hardnessFromTfl);

        hardnessToLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/ + " : ");
        hardnessToLbl.setPreferredSize(new Dimension(100,21));
        hardnessToLbl.setHorizontalAlignment(JLabel.RIGHT);
        hardnessToLbl.setFont(FontList.defaultFont);
        panel04.add(hardnessToLbl);

        hardnessToTfl = new JTextField(17);
        hardnessToTfl.setPreferredSize(new Dimension(150, 21));
        hardnessToTfl.setFont(FontList.defaultFont);
        hardnessToTfl.addKeyListener(new KeyListener() {
            // 숫자만 입력 가능하도록 함
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
        });
        panel04.add(hardnessToTfl);

        propertyPanel02.add(panel04);

        // 금형 :: 설계일자 / ECO No Panel
        JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        designDateLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/ + " : ");
        designDateLbl.setPreferredSize(new Dimension(100,21));
        designDateLbl.setHorizontalAlignment(JLabel.RIGHT);
        designDateLbl.setFont(FontList.defaultFont);
        designDateLbl.setEnabled(false);
        panel05.add(designDateLbl);

        designDateTfl = new JTextField(17);
        designDateTfl.setPreferredSize(new Dimension(150, 21));
        designDateTfl.setFont(FontList.defaultFont);
        designDateTfl.setEnabled(false);
//        designDateTfl.setText(DateUtil.getCurrentDateString("d"));
        designDateTfl.setText(DateUtil.getToDay("yyyy-MM-dd"));
        panel05.add(designDateTfl);

        propertyPanel02.add(panel05);

        totalPanel.add(propertyPanel01, BorderLayout.NORTH);
        totalPanel.add(propertyPanel02, BorderLayout.CENTER);

//        this.add(totalPanel, BorderLayout.NORTH);

        centerPanel2 = new JPanel();
        centerPanel2.setFont(FontList.defaultFont);
        centerPanel2.add(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00028")/*BOM 속성*/, totalPanel);

        this.getContentPane().add(new JScrollPane(centerPanel2), BorderLayout.CENTER);

        // 하단부 버튼 패널
        JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnListener = new BtnListener();

        okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("okIcon"));
        okBtn.setMargin(new Insets(0,5,0,5));
        okBtn.setActionCommand("OK");
        okBtn.setDefaultCapable(true);
        okBtn.setFont(FontList.defaultFont);
        okBtn.addActionListener(btnListener);
        btnFlowPanel.add(okBtn);

        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setActionCommand("Cancel");
        cancelBtn.addActionListener(btnListener);
        cancelBtn.setMargin(new Insets(0,5,0,5));
        cancelBtn.setFont(FontList.defaultFont);
        btnFlowPanel.add(cancelBtn);

        this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
    }

    // 자부품 등록 :: 신규 BOM
    private void componentBOM(String rootItemCd, Vector bomVec)
    {
        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;

        try    {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));
            stmt = conn.createStatement();

            BOMSaveDao saveDao = new BOMSaveDao();
            saveDao.saveExcelBomList(conn, rootItemCd, bomVec);
            conn.commit();

        } catch(Exception e)     {
            Kogger.error(getClass(), e);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(Exception e) {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null)
                {
                    res.freeConnection(appReg.getString("plm"), conn);
                }
            }
        }
    }

    // 자부품 등록 :: 변경 BOM
    private void componentBOMEco(String ecoHeaderNo, Vector bomVec)
    {
        try    {
            BOMSaveDao saveDao = new BOMSaveDao();
            saveDao.saveBomEco(ecoHeaderNo, bomVec);

        } catch(Exception e)     {
            Kogger.error(getClass(), e);
        }
    }
}
