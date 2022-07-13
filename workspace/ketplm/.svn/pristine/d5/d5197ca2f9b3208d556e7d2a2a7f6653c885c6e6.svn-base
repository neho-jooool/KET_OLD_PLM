package e3ps.bom.command.generalbom;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.InterfaceAIFOperationListener;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Painter;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class GeneralBOMUpdateDialog extends  AbstractAIFDialog implements InterfaceAIFOperationListener
{
    private static final long serialVersionUID = 1L;
    private JTextField gItemCodeTfl = null;        //부품코드
    private JTextField gItemNmTfl = null;        //부품명
    private JTextField cStatusKr = null;            //결재상태한글
    private JTextField cStatus = null;                //결재상태영문
    private JTextField gQuantity = null;            //기준수량
    private JTextField bQuantity = null;            //포장수량
    private JTextField gUnitTfl = null;            //단위

    private JButton gSearchBtn = null;
    private JButton updateBOMBtn = null;
    private JButton cancelBtn = null;

    boolean keyCheck = false;
    boolean isView = false;
    private BtnListener btnListener;

    private JFrame frmDesktop;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel pnl;
    private BOMRegisterDesktop desktop;
    Registry appReg;

    private String aryOwnerData[]= new String[4];
    private String rootNodeItemCode = "";

    boolean isOwnerData = false;
    boolean isAdmin = false;
    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;
    boolean isTop = false;
    BOMSearchUtilDao dao = new BOMSearchUtilDao();

//    private BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

    class BtnListener implements ActionListener, KeyListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("updateBOM")) {
                if(updateBOM()) {
                    cancelBOM();
                } else {
                    frmDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            if(e.getActionCommand().equals("Cancel")) {
                cancelBOM();
            }
        };

        boolean keyCheck = false;

        public void keyPressed(KeyEvent e) {};

        public void keyReleased(KeyEvent e) {
            if ( keyCheck ) {
                JTextField tfl = (JTextField)e.getSource();
                int pos = tfl.getCaretPosition();

                tfl.setCaretPosition(pos);
                keyCheck = false;
            }
        };

        public void keyTyped(KeyEvent e) {
            keyCheck = false;

            char c = e.getKeyChar();
            int cCode = (int)c;

            if ( cCode >= 97 && cCode <= 122 ) {
                keyCheck = true;
            }
        };
    }

    public GeneralBOMUpdateDialog() {
    }

    public GeneralBOMUpdateDialog(JFrame frame, AbstractAIFUIApplication app, String strItemCode, boolean isView) {
        super(frame, true);
        this.rootNodeItemCode = strItemCode;
        this.isView = isView;
        initialize(frame, app, strItemCode);
    }

    private void initialize(JFrame frame, AbstractAIFUIApplication app, String strItemCode) {
        try {
            frmDesktop = frame;
            this.app = app;
            appReg = Registry.getRegistry(app);
            desktop = (BOMRegisterDesktop)frame;
            pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            // 부품번호가 완제품인지 여부 확인
            String strTemp = "";
            if ( BOMBasicInfoPool.getPublicModelName() != null && !BOMBasicInfoPool.getPublicModelName().equals("") )
            {
                strTemp = BOMBasicInfoPool.getPublicModelName().substring(0, 1);
                if ( strTemp.equalsIgnoreCase("0") || strTemp.equalsIgnoreCase("1") || strTemp.equalsIgnoreCase("2") || strTemp.equalsIgnoreCase("3") ||
                     strTemp.equalsIgnoreCase("4") || strTemp.equalsIgnoreCase("5") || strTemp.equalsIgnoreCase("6") || strTemp.equalsIgnoreCase("7") ||
                     strTemp.equalsIgnoreCase("8") || strTemp.equalsIgnoreCase("9") || strTemp.equalsIgnoreCase("K") )
                {
                    isTop = true;
                }
            }

            setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00334")/*최상위 BOM 속성변경*/);
            setResizable(false);

            setUserData();
            setContentInit();
            setData();

            if ( dao.getIsNewBomCode( BOMBasicInfoPool.getPublicModelName() ) || isTop ) {
                bQuantity.requestFocus();
            }
            else
            {
                bQuantity.setEnabled(false);
            }
            run();
        } catch (Throwable ex)     {
            Kogger.error(getClass(), ex);
        }
    }

    private void setContentInit() throws Exception {
        try {
            btnListener = new BtnListener();

            // General Panel /////////////////////////////////////////////////
            JPanel generalPanel = new JPanel();
            generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
            generalPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " BOM ", 0, 0, FontList.defaultFont));

            // 첫번째 라인
            JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel gItemCodeLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
            gItemCodeLbl.setFont(FontList.defaultFont);
            gItemCodeLbl.setEnabled(false);
            panel01.add(gItemCodeLbl);

            gItemCodeTfl = new JTextField();
            gItemCodeTfl.setPreferredSize(new Dimension(200, 21));
            gItemCodeTfl.setFont(FontList.defaultFont);
            gItemCodeTfl.setEnabled(false);
            gItemCodeTfl.addKeyListener(new KeyAdapter() {
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

                        tfl.setCaretPosition(pos);
                        keyCheck = false;
                    }
                }
            });
            panel01.add(gItemCodeTfl);

            gSearchBtn = new JButton(appReg.getImageIcon("searchIcon"));
            gSearchBtn.setActionCommand("GeneralBOMSearch");
            gSearchBtn.setBorder(BorderList.emptyBorder1);
            gSearchBtn.addActionListener(btnListener);
            gSearchBtn.setMargin(new Insets(0,5,0,5));
//            panel01.add(gSearchBtn);

            generalPanel.add(panel01);

            // 두번째 라인
            JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel gDescLbl = new JLabel("             " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
            gDescLbl.setFont(FontList.defaultFont);
            gDescLbl.setEnabled(false);
            panel02.add(gDescLbl);

            gItemNmTfl = new JTextField();
            gItemNmTfl.setPreferredSize(new Dimension(200, 21));
            gItemNmTfl.setEnabled(false);
            gItemNmTfl.setFont(FontList.defaultFont);
            panel02.add(gItemNmTfl);

            generalPanel.add(panel02);

            // 세번째 라인
            JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel gUomLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/ + " : ");
            gUomLbl.setFont(FontList.defaultFont);
            gUomLbl.setEnabled(false);
            panel03.add(gUomLbl);

            cStatusKr = new JTextField();
            cStatusKr.setPreferredSize(new Dimension(200, 21));
            cStatusKr.setEnabled(false);
            cStatusKr.setFont(FontList.defaultFont);

            cStatus = new JTextField();
            cStatus.setPreferredSize(new Dimension(200, 21));
            cStatus.setVisible(false);

            panel03.add(cStatusKr);
            panel03.add(cStatus);

            generalPanel.add(panel03);

            // 네번째 라인
            JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.LEFT));

            String strQtyLabel = "";
            if ( dao.getIsNewBomCode( BOMBasicInfoPool.getPublicModelName() ) || isTop ) {
                strQtyLabel = "          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ";
            } else {
                strQtyLabel = "          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00228")/*소요수량*/ + " : ";
            }
            JLabel gUitLbl = new JLabel(strQtyLabel);
            gUitLbl.setFont(FontList.defaultFont);
            gUitLbl.setEnabled(false);
            panel04.add(gUitLbl);

            gQuantity = new JTextField();
            gQuantity.setPreferredSize(new Dimension(200, 21));
            gQuantity.setEnabled(false);
            gQuantity.setFont(FontList.defaultFont);
            panel04.add(gQuantity);

            generalPanel.add(panel04);

            // 다섯번째 라인
            JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel gStatus = new JLabel("                단위 : ");
            gStatus.setFont(FontList.defaultFont);
            gStatus.setEnabled(false);
            panel05.add(gStatus);

            gUnitTfl = new JTextField();
            gUnitTfl.setPreferredSize(new Dimension(200, 21));
            gUnitTfl.setEnabled(false);
            gUnitTfl.setFont(FontList.defaultFont);
            panel05.add(gUnitTfl);

            generalPanel.add(panel05);

            // 여섯번째 라인
            JPanel panel06 = new JPanel(new FlowLayout(FlowLayout.LEFT));

            String strBoxQtyLabel = "";
            if ( dao.getIsNewBomCode( BOMBasicInfoPool.getPublicModelName() ) || isTop ) {
                strBoxQtyLabel = "          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00345")/*포장수량*/ + " : ";
            } else {
                strBoxQtyLabel = "          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/*기준수량*/ + " : ";
            }
            JLabel bQtyLbl = new JLabel(strBoxQtyLabel);
            bQtyLbl.setFont(FontList.defaultFont);
            panel06.add(bQtyLbl);

            bQuantity = new JTextField(22) {
                private static final long serialVersionUID = 1L;
                public void paint(Graphics g)    {
                    super.paint(g);
                    Painter.paintIsRequired(this, g);
                }
            };
//            bQuantity.setPreferredSize(new Dimension(200, 21));
            bQuantity.setFont(FontList.defaultFont);

            if(isView) {
                bQuantity.setEnabled(false);
            }
            panel06.add(bQuantity);

            generalPanel.add(panel06);

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.add(generalPanel);

            this.getContentPane().add(topPanel, BorderLayout.NORTH);

            // Button Panel /////////////////////////////////////////////////
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            updateBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/*저장*/, appReg.getImageIcon("createbomIcon"));
            updateBOMBtn.setFont(FontList.defaultFont);
            updateBOMBtn.setActionCommand("updateBOM");
            updateBOMBtn.addActionListener(btnListener);
            updateBOMBtn.setMargin(new Insets(0,5,0,5));
            if(isView) {
                updateBOMBtn.setEnabled(false);
            }
            btnFlowPanel.add(updateBOMBtn);

            cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            cancelBtn.setFont(FontList.defaultFont);
            cancelBtn.setActionCommand("Cancel");
            cancelBtn.addActionListener(btnListener);
            cancelBtn.setMargin(new Insets(0,5,0,5));
            btnFlowPanel.add(cancelBtn);

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void setUserData() {
        try {
            aryOwnerData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
            aryOwnerData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
            aryOwnerData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
            aryOwnerData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());

            if(BOMBasicInfoPool.isAdmin()){
                isAdmin = true;
            } else {
                isAdmin = false;
            }

            isOwnerData = true;
        } catch(Exception e) {
            isOwnerData = false;
            Kogger.error(getClass(), e);
        }
    }

    private void setData() {

        KETBomHeader header = null;
        KETBomEcoHeader ecoHeader = null;
        KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();

        if (bomGubunFlag) {
            header = query.getBOMHeader( rootNodeItemCode );

            gItemCodeTfl.setText(header.getNewBOMCode());
            gItemNmTfl.setText(header.getDescription());
            cStatusKr.setText(header.getLifeCycleState().getDisplay());
            gQuantity.setText(header.getQuantity());
            gUnitTfl.setText(header.getUnitOfQuantity());
            bQuantity.setText(header.getBoxQuantity());
        } else {
            ecoHeader = query.getBOMEcoHeader( rootNodeItemCode, BOMBasicInfoPool.getBomEcoNumber() );

            gItemCodeTfl.setText(ecoHeader.getEcoItemCode());
            gItemNmTfl.setText(ecoHeader.getDescription());
            cStatusKr.setText(ecoHeader.getLifeCycleState().getDisplay());
            gQuantity.setText(ecoHeader.getQuantity());
            gUnitTfl.setText(query.getUnitDisplayValue(ecoHeader.getUnitOfQuantity()));
            bQuantity.setText(ecoHeader.getBoxQuantity());
        }
    }

    private boolean updateBOM() {

        String oid = "";
        String strBoxQuantity = "";

        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            Hashtable<String, String> inputParams = new Hashtable<String, String>();

            KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();

            if (bQuantity.getText() != null && !bQuantity.getText().equals("")) {
                strBoxQuantity = bQuantity.getText().trim();
            } else {
                MessageBox m = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00231")/*수량 정보를 입력해주십시오.*/, "Error", 0);
                m.setModal(true);
                m.setVisible(true);

                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return false;
            }

            inputParams.put("newBomCode", gItemCodeTfl.getText()==null?"":(gItemCodeTfl.getText().trim()));
            inputParams.put("ecoItemCode", gItemCodeTfl.getText()==null?"":(gItemCodeTfl.getText().trim()));
            inputParams.put("ecoHeaderNo", BOMBasicInfoPool.getBomEcoNumber());
            inputParams.put("boxQuantity", bQuantity.getText()==null?"":bQuantity.getText().trim());

            oid = query.udpateBom(inputParams, bomGubunFlag);

            // 포장수량 변경내용을 화면에 Refresh 해주기 위해 셋팅이 필요함
            if (!strBoxQuantity.equals("")) {
                pnl.mainEditorPane.model.getRootNode().getBOMComponent().setBoxQuantityDbl(Double.parseDouble(strBoxQuantity));
            }

            if(oid==null || oid.length()==0) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return false;
            }

            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return true;
        } catch (Throwable ex) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
            return false;
        }
    }

    private void cancelBOM() {
        try {
            int count = getComponentCount();
            for(int i=0; i<count; i++) {
                Component c = getComponent(i);
                this.remove(c);
                c = null;
            }

            super.dispose();
            System.gc();

            pnl.publicStatusPanel.setStatusBar();
            return;
        } catch (Throwable ex)    {
            Kogger.error(getClass(), ex);
        }
    }

    public static void main(java.lang.String[] args)    {
        try    {
            GeneralBOMDialog aGeneralBOMDialog;
            aGeneralBOMDialog = new GeneralBOMDialog();
            aGeneralBOMDialog.setModal(true);
            aGeneralBOMDialog.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                };
            });
            aGeneralBOMDialog.show();

            Insets insets = aGeneralBOMDialog.getInsets();

            int i = aGeneralBOMDialog.getWidth() + insets.left + insets.right;
            int j = aGeneralBOMDialog.getHeight() + insets.top + insets.bottom;

            aGeneralBOMDialog.setSize(aGeneralBOMDialog.getWidth() + insets.left + insets.right, aGeneralBOMDialog.getHeight() + insets.top + insets.bottom);
            aGeneralBOMDialog.setVisible(true);
        } catch (Throwable exception) {
            Kogger.error(GeneralBOMUpdateDialog.class, exception);
        }
    }

    public void startOperation(String s) {
    }

    public void endOperation() {
    }
}
