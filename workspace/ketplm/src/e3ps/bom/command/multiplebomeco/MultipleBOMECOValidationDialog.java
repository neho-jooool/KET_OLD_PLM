package e3ps.bom.command.multiplebomeco;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtable.MultiSpanCellTable;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class MultipleBOMECOValidationDialog  extends JDialog {

    private static final long serialVersionUID = 1L;
    private AbstractAIFUIApplication app;
    private JFrame desktop;
    Registry appReg;

    DBConnectionManager resource = null;
    Connection connection = null;

    private MultipleBOMECOValidationResultFrame fr;
    private JProgressBar progress;
    public int m_counter = 0;
    public Thread runner = null;
    private Timer timer;
    private final static int TIME_UNIT = 100;
    private int totalDataCount = 0;
    private int errorCount = 0;
    private int warningCount = 0;
    boolean isFlag = false;

    private JLabel titleLbl, totalCntLbl, warningLbl, errorLbl, validationLbl1, validationLbl2, validationLbl3, validationLbl4, validationLbl5, validationLbl6,
                    validationLbl7, validationLbl8, validationLbl9, validationLbl10;
    private JCheckBox  validationChk1, validationChk2, validationChk3, validationChk4, validationChk5, validationChk6, validationChk7,
                    validationChk8, validationChk9, validationChk10;
    private JButton okBtn, cancelBtn;

    private Vector resultVector = new Vector();
    private boolean cancelFlag = false;
    private String strValidationForEnd = BOMECOBasicInfoPool.getValidationForEnd();
    private String modelName = BOMECOBasicInfoPool.getPublicModelName() == null ? "" : BOMECOBasicInfoPool.getPublicModelName().trim();

    private JTable parentItemTB;
    private MultiSpanCellTable changeActivityTB;
    private Vector assyCompVec;

    int parentItemTBRowCnt;
    int changeActTBRowCnt;

    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            progress.setValue(m_counter);

            if (m_counter == 100) {
                setSuccess();
            }
        }
    }

    public MultipleBOMECOValidationDialog(JFrame frame, AbstractAIFUIApplication app, JTable parentItem, MultiSpanCellTable changeActivity, Vector assyCompVec) {
        super(frame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00273")/*일괄변경 BOM 검증*/, true);
        this.app = app;
        this.desktop = frame;

        this.parentItemTB = parentItem;
        this.changeActivityTB = changeActivity;
        this.assyCompVec = assyCompVec;

        this.parentItemTBRowCnt = this.parentItemTB.getRowCount();
        this.changeActTBRowCnt = this.changeActivityTB.getRowCount();


        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                stopOperation();
            }
        });

        try {
            jInit();
            setEvent();

            setSize(566, 390);
            setResizable(false);

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            loadWarningMessage("BOMValidaionDialog Loading Error");
        }
    }

    private void jInit() {
        appReg = Registry.getRegistry(app);

        JPanel contentpane = (JPanel)this.getContentPane();
        contentpane.setLayout(null);
        contentpane.setPreferredSize(new Dimension(650, 370));

        JPanel panel = new JPanel();
        panel.setBounds(new Rectangle(0, 0, 650, 370));
        panel.setLayout(null);

        JPanel progressPanel = new JPanel();
        JPanel contentsPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00259")/*실행*/, appReg.getImageIcon("bomvalidationIcon"));
        okBtn.setFont(FontList.defaultFont);
        okBtn.setMargin(new Insets(0, 5, 0, 5));
        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setFont(FontList.defaultFont);
        cancelBtn.setMargin(new Insets(0, 5, 0, 5));

        progress = new JProgressBar();
        progress.setMinimum(0);
        progress.setMaximum(100);
        progress.setStringPainted(true);

        validationChk1 = new JCheckBox("", true);
//        validationChk2 = new JCheckBox("", true);
        validationChk3 = new JCheckBox("", true);
        validationChk4 = new JCheckBox("", true);
        validationChk5 = new JCheckBox("", true);
        validationChk6 = new JCheckBox("", true);
        validationChk7 = new JCheckBox("", true);
        validationChk8 = new JCheckBox("", true);
        validationChk9 = new JCheckBox("", true);
//        validationChk10 = new JCheckBox("", true);

Kogger.debug(getClass(), "@@@@@ [여기는 멀티 검증!!!!] strValidationForEnd : " + strValidationForEnd);

        if (strValidationForEnd.equals("LAST") || strValidationForEnd.equals("ADMINLAST")) {
            validationChk1.setSelected(true);
//            validationChk2.setSelected(true);
            validationChk3.setSelected(true);
            validationChk4.setSelected(true);
            validationChk5.setSelected(true);
            validationChk6.setSelected(true);
            validationChk7.setSelected(true);
            validationChk8.setSelected(true);
            validationChk9.setSelected(true);
//            validationChk10.setSelected(true);

            validationChk1.setEnabled(false);
//            validationChk2.setEnabled(false);
            validationChk3.setEnabled(false);
            validationChk4.setEnabled(false);
            validationChk5.setEnabled(false);
            validationChk6.setEnabled(false);
            validationChk7.setEnabled(false);
            validationChk8.setEnabled(false);
            validationChk9.setEnabled(false);
//            validationChk10.setEnabled(false);
        }

        titleLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00019")/*BOM 검증*/ + "...");
        titleLbl.setFont(FontList.boldFont);
        totalCntLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00333", 0)/*총 개수 : {0}*/);
        totalCntLbl.setFont(FontList.defaultFont);
        warningLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00081", 0)/*경고 : {0}*/);
        warningLbl.setFont(FontList.defaultFont);
        errorLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00267", 0)/*오류 : {0}*/);
        errorLbl.setFont(FontList.defaultFont);

        validationLbl1 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00004", 1)/*{0} 단계 : BOM 구성에 필요한 부품이 PLM에 채번되어 있는지 체크*/);
        validationLbl1.setFont(FontList.defaultFont);
//        validationLbl2 = new JLabel(" 2 Stage : 단종(Inactive) 상태 Item이 존재하는지 체크");
//        validationLbl2.setFont(FontList.defaultFont);
        validationLbl3 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00012", 2)/*{0} 단계 : 부품코드와 대체부품코드가 동일한지 체크*/);
        validationLbl3.setFont(FontList.defaultFont);
        validationLbl4 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00016", 3)/*{0} 단계 : 필수 속성값(수량, 단위) 이 존재하는지 체크*/);
        validationLbl4.setFont(FontList.defaultFont);
        validationLbl5 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00006", 4)/*{0} 단계 : 모부품의 동일한 레벨에 같은 부품이 사용되었는지 체크*/);
        validationLbl5.setFont(FontList.defaultFont);
        validationLbl6 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00007", 5)/*{0} 단계 : 모부품이 하위 자부품으로 재사용되었는지 체크*/);
        validationLbl6.setFont(FontList.defaultFont);
        validationLbl7 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00013", 6)/*{0} 단계 : 삭제된 부품이 존재하는지 체크*/);
        validationLbl7.setFont(FontList.defaultFont);
        validationLbl8 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00010", 7)/*{0} 단계 : 부품이 체크인 상태인지 체크*/);
        validationLbl8.setFont(FontList.defaultFont);
        validationLbl9 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00008", 8)/*{0} 단계 : 부품에 연결된 도면/문서가 체크인 상태인지 체크*/);
        validationLbl9.setFont(FontList.defaultFont);
//        validationLbl10 = new JLabel(" 9 단계 : 부품에 연결된 도면/문서가 Check-In 상태인지 체크");
//        validationLbl10.setFont(FontList.defaultFont);


        Border border1;

        border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(134, 134, 134));
        this.getContentPane().setLayout(null);

        progressPanel.setBounds(new Rectangle(3, 1, 630, 61));
        progressPanel.setLayout(null);
        contentsPanel.setBorder(border1);
        contentsPanel.setBounds(new Rectangle(6, 65, 547, 240)); //345
        contentsPanel.setLayout(null);
        buttonPanel.setBounds(new Rectangle(3, 300, 574, 49));
        buttonPanel.setLayout(null);
        cancelBtn.setActionCommand("Cancel");
        okBtn.setActionCommand("OK");

        okBtn.setBounds(new Rectangle(190, 20, 75, 26));
        cancelBtn.setBounds(new Rectangle(270, 20, 77, 26));
        if (strValidationForEnd.equals("LAST") || strValidationForEnd.equals("ADMINLAST")) {
            cancelBtn.setEnabled(false);
        }

        validationChk1.setBounds(new Rectangle(12, 6, 20, 23));
//        validationChk2.setBounds(new Rectangle(12, 30, 20, 23));
//        validationChk3.setBounds(new Rectangle(12, 54, 20, 23));
//        validationChk4.setBounds(new Rectangle(12, 78, 20, 23));
//        validationChk5.setBounds(new Rectangle(12, 102, 20, 23));
//        validationChk6.setBounds(new Rectangle(12, 126, 20, 23));
//        validationChk7.setBounds(new Rectangle(12, 150, 20, 23));
//        validationChk8.setBounds(new Rectangle(12, 174, 20, 23));
//        validationChk9.setBounds(new Rectangle(12, 198, 20, 23));
//        validationChk10.setBounds(new Rectangle(12, 222, 20, 23));

        validationChk3.setBounds(new Rectangle(12, 30, 20, 23));
        validationChk4.setBounds(new Rectangle(12, 54, 20, 23));
        validationChk5.setBounds(new Rectangle(12, 78, 20, 23));
        validationChk6.setBounds(new Rectangle(12, 102, 20, 23));
        validationChk7.setBounds(new Rectangle(12, 126, 20, 23));
        validationChk8.setBounds(new Rectangle(12, 150, 20, 23));
        validationChk9.setBounds(new Rectangle(12, 174, 20, 23));
//        validationChk10.setBounds(new Rectangle(12, 198, 20, 23));

        validationLbl1.setBounds(new Rectangle(40, 6, 490, 23));
//        validationLbl2.setBounds(new Rectangle(40, 30, 490, 23));
//        validationLbl3.setBounds(new Rectangle(40, 54, 490, 23));
//        validationLbl4.setBounds(new Rectangle(40, 78, 490, 23));
//        validationLbl5.setBounds(new Rectangle(40, 102, 490, 23));
//        validationLbl6.setBounds(new Rectangle(40, 126, 490, 23));
//        validationLbl7.setBounds(new Rectangle(40, 150, 490, 23));
//        validationLbl8.setBounds(new Rectangle(40, 174, 490, 23));
//        validationLbl9.setBounds(new Rectangle(40, 198, 490, 23));
//        validationLbl10.setBounds(new Rectangle(40, 222, 490, 23));

        validationLbl3.setBounds(new Rectangle(40, 30, 490, 23));
        validationLbl4.setBounds(new Rectangle(40, 54, 490, 23));
        validationLbl5.setBounds(new Rectangle(40, 78, 490, 23));
        validationLbl6.setBounds(new Rectangle(40, 102, 490, 23));
        validationLbl7.setBounds(new Rectangle(40, 126, 490, 23));
        validationLbl8.setBounds(new Rectangle(40, 150, 490, 23));
        validationLbl9.setBounds(new Rectangle(40, 174, 490, 23));
//        validationLbl10.setBounds(new Rectangle(40, 198, 490, 23));

        titleLbl.setBounds(new Rectangle(11, 4, 131, 24));
        totalCntLbl.setBounds(new Rectangle(250, 4, 105, 24));
        warningLbl.setBounds(new Rectangle(355, 4, 90, 24));
        errorLbl.setBounds(new Rectangle(445, 4, 90, 24));
        progress.setBounds(new Rectangle(20, 38, 510, 21));

        UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ProgressBar.selectionForeground", Color.white);
        UIManager.put("ProgressBar.foreground", new Color(8, 32, 28));

        progressPanel.add(titleLbl, null);
        progressPanel.add(totalCntLbl, null);
        progressPanel.add(warningLbl, null);
        progressPanel.add(errorLbl, null);
        progressPanel.add(progress);

        contentsPanel.add(validationChk1, null);
        contentsPanel.add(validationLbl1, null);
//        contentsPanel.add(validationChk2, null);
//        contentsPanel.add(validationLbl2, null);
        contentsPanel.add(validationChk3, null);
        contentsPanel.add(validationLbl3, null);
        contentsPanel.add(validationChk4, null);
        contentsPanel.add(validationLbl4, null);
        contentsPanel.add(validationChk5, null);
        contentsPanel.add(validationLbl5, null);
        contentsPanel.add(validationChk6, null);
        contentsPanel.add(validationLbl6, null);
        contentsPanel.add(validationChk7, null);
        contentsPanel.add(validationLbl7, null);
        contentsPanel.add(validationChk8, null);
        contentsPanel.add(validationLbl8, null);
        contentsPanel.add(validationChk9, null);
        contentsPanel.add(validationLbl9, null);
//        contentsPanel.add(validationChk10, null);
//        contentsPanel.add(validationLbl10, null);

        buttonPanel.add(okBtn, null);
        buttonPanel.add(cancelBtn, null);

        panel.add(progressPanel, null);
        panel.add(contentsPanel, null);
        panel.add(buttonPanel, null);

        contentpane.add(panel, null);
    }

    private void setEvent() {
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer != null)
                    timer.stop();
                dispose();
                setVisible(false);
                cancelFlag = true;

                BOMECOBasicInfoPool.setHasErrorInValidation(false);

                // 보정결과 list가 있을때에는 결과를 보여주는 frame을 띄운다.
                if ((errorCount != 0) || (warningCount != 0)) {
                    MultipleBOMECOValidationResultFrame fr = new MultipleBOMECOValidationResultFrame(app, resultVector, errorCount, warningCount);
                }

                if (strValidationForEnd.equals("LAST")) {
                    BOMECOBasicInfoPool.setBomValidationResult(true);

                    if (BOMECOBasicInfoPool.getHasErrorInValidation()) {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("valid1"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00021")/*BOM 검증 오류*/, MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    } else {
                        try {
                            EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
                            cmd.executeModal();
                        } catch (Exception ex) {
                            Kogger.error(getClass(), ex);
                        }
                    }
                } else if (strValidationForEnd.equals("NOTLAST")) {
                    BOMECOBasicInfoPool.setBomValidationResult(true);

                    try {
                        EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
                        cmd.executeModal();
                    } catch (Exception ex) {
                        Kogger.error(getClass(), ex);
                    }
                } else if (strValidationForEnd.equals("ADMINLAST")) {
                    BOMECOBasicInfoPool.setBomValidationResult(true);

                    if (BOMECOBasicInfoPool.getHasErrorInValidation()) {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("valid1"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00021")/*BOM 검증 오류*/, MessageBox.ERROR);
                        m.setVisible(true);
                        m.setModal(true);
                        return;
                    }
                }
                setVisible(false);
            }
        });

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okBtn.setEnabled(false);
                startOperation();
            }
        });
    }

    private void startOperation() {
        try {
            timer = new Timer(TIME_UNIT, new TimerListener());
            timer.start();

            runner = new Thread() {
                public void run() {
                    excuteOperation();
                }
            };
            runner.start();

        } catch (Exception e) {
            loadWarningMessage("Start Operation Error");
        }
    }

    private void stopOperation() {
        try {
            timer = new Timer(TIME_UNIT, new TimerListener());
            timer.stop();

            if (runner != null)
                runner.stop();
        } catch (Exception e) {
            loadWarningMessage("Stop Operation Error");
        }
    }

    // 각 항목별로 Validation 실행
    public void excuteOperation() {

        totalDataCount = this.parentItemTB.getRowCount() + this.changeActivityTB.getRowCount();

        // 1 stage : BOM 구성에 필요한 Item이 PLM에 채번되어 있는지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationLbl1.setForeground(Color.red);
        validationLbl1.setFont(FontList.boldFont);
        checkIsExistItemPlm();
        setProgress(100);
        validationChk1.setSelected(false);
        if (cancelFlag)
            return;

        // 2 stage : 단종(Inactive) 상태 Item이 존재하는지 체크
//        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
//        timer.start();
//        validationChk1.setSelected(false);
//        validationLbl1.setForeground(Color.black);
//        validationLbl1.setFont(FontList.defaultFont);
//        validationLbl2.setForeground(Color.red);
//        validationLbl2.setFont(FontList.boldFont);
//        checkObsoletedItem();
//        setProgress(100);
//        validationChk2.setSelected(false);
//        if (cancelFlag)
//            return;

        // 2 stage : 부품코드와 대체부품 코드가 동일한지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk1.setSelected(false);
        validationLbl1.setForeground(Color.black);
        validationLbl1.setFont(FontList.defaultFont);
        validationLbl3.setForeground(Color.red);
        validationLbl3.setFont(FontList.boldFont);
        checkSameSubstitute();
        setProgress(100);
        validationChk3.setSelected(false);
        if (cancelFlag)
            return;

        // 3 Stage : 필수 속성값(수량, 단위) 이 존재하는지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk3.setSelected(false);
        validationLbl3.setForeground(Color.black);
        validationLbl3.setFont(FontList.defaultFont);
        validationLbl4.setForeground(Color.red);
        validationLbl4.setFont(FontList.boldFont);
        checkExistAttrs();
        setProgress(100);
        validationChk4.setSelected(false);
        if (cancelFlag)
            return;

        // 4 Stage : Parent Item의 동일한 레벨에 같은 Item이 사용되었는지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk4.setSelected(false);
        validationLbl4.setForeground(Color.black);
        validationLbl4.setFont(FontList.defaultFont);
        validationLbl5.setForeground(Color.red);
        validationLbl5.setFont(FontList.boldFont);
        checkDupItem();
        setProgress(100);
        validationChk5.setSelected(false);
        if (cancelFlag)
            return;

        // 5 Stage :  Parent Item이 하위 Child Item으로 재사용되었는지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk5.setSelected(false);
        validationLbl5.setForeground(Color.black);
        validationLbl5.setFont(FontList.defaultFont);
        validationLbl6.setForeground(Color.red);
        validationLbl6.setFont(FontList.boldFont);
        checkDupParentItem();
        setProgress(100);
        validationChk6.setSelected(false);
        if (cancelFlag)
            return;

        // 6 Stage : 삭제된 부품이 존재하는지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk6.setSelected(false);
        validationLbl6.setForeground(Color.black);
        validationLbl6.setFont(FontList.defaultFont);
        validationLbl7.setForeground(Color.red);
        validationLbl7.setFont(FontList.boldFont);
        checkObsoletedItem();
        setProgress(100);
        validationChk7.setSelected(false);
        if (cancelFlag)
            return;

        // 8 Stage : 부품이 Check-In 상태인지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk7.setSelected(false);
        validationLbl7.setForeground(Color.black);
        validationLbl7.setFont(FontList.defaultFont);
        validationLbl8.setForeground(Color.red);
        validationLbl8.setFont(FontList.boldFont);
        checkCheckInItem();
        setProgress(100);
        validationChk8.setSelected(false);
        if (cancelFlag)
            return;

        // 9 Stage : 부품에 연결된 도면/문서가 Check-In 상태인지 체크
        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
        validationChk8.setSelected(false);
        validationLbl8.setForeground(Color.black);
        validationLbl8.setFont(FontList.defaultFont);
        validationLbl9.setForeground(Color.red);
        validationLbl9.setFont(FontList.boldFont);
        checkCheckInDoc();
        setProgress(100);
        validationChk9.setSelected(false);
        if (cancelFlag)
            return;

        // 10 Stage : 부품에 연결된 도면/문서가 Check-In 상태인지 체크
//        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
//        timer.start();
//        validationChk9.setSelected(false);
//        validationLbl9.setForeground(Color.black);
//        validationLbl9.setFont(FontList.defaultFont);
//        validationLbl10.setForeground(Color.red);
//        validationLbl10.setFont(FontList.boldFont);
//        checkCheckInDoc();
//        setProgress(100);
//        validationChk10.setSelected(false);
//        if (cancelFlag)
//            return;

        cancelBtn.setEnabled(false);

        // 결과 list 에 에러값이 없으면 메시지를 띄우고 이관이 가능하도록 flag 처리를 해준다.
        if (errorCount == 0) {
            if (!strValidationForEnd.equals("")) {
                BOMECOBasicInfoPool.setBomValidationResult(true);
                BOMECOBasicInfoPool.setHasErrorInValidation(false);
            }

            if (warningCount == 0) {
//                loadWarningMessage("BOM Validation is completed!\nThere was no error.");
//                setVisible(false);

                if (!BOMECOBasicInfoPool.getHasErrorInValidation() && BOMECOBasicInfoPool.getBomValidationResult()) {
                    try {
                        if (strValidationForEnd.equals("LAST") || strValidationForEnd.equals("NOTLAST")) {
Kogger.debug(getClass(), "==============NOTLAST 2================");
                            EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
                            cmd.executeModal();
                        }
                    } catch (Exception e) {
                        Kogger.error(getClass(), e);
                    }
                }
                dispose();

                //shin....위의 주석처리한 메세지 부분을 이쪽으로 옮김..쓰레드와 충돌이 나는지?? 프로세서가 죽음..여기에 두면 괜찮음.
                loadWarningMessage(messageRegistry.getString("valid6"));
                setVisible(false);

            } else {
                dispose();

                // Error가 있을 경우에는 error list를 보여주는 Dialog를 띄워준다.
                fr = new MultipleBOMECOValidationResultFrame(app, resultVector, errorCount, warningCount);
                loadWarningMessage(fr, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00022", errorCount, warningCount)/*BOM 검증 완료!\n에러: {0}개, 경고: {1}개*/);
                setVisible(false);
            }
        } else {
            if (!strValidationForEnd.equals("")) {
                if (strValidationForEnd.equals("NOTLAST"))
                    BOMECOBasicInfoPool.setBomValidationResult(true);
                else
                    BOMECOBasicInfoPool.setBomValidationResult(false);

                BOMECOBasicInfoPool.setHasErrorInValidation(true);
            }

            dispose();

            // Error가 있을 경우에는 error list를 보여주는 Dialog를 띄워준다.
            fr = new MultipleBOMECOValidationResultFrame(app, resultVector, errorCount, warningCount);
            loadWarningMessage(fr, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00022", errorCount, warningCount)/*BOM 검증 완료!\n에러: {0}개, 경고: {1}개*/);
            setVisible(false);
        }
    }

    // 1 stage : BOM 구성에 필요한 부품이 PLM에 채번되어 있는지 체크
    private void checkIsExistItemPlm() {
        if (!validationChk1.isSelected()) {
            return;
        }

        int progress = 0;

        Vector subCompsItemCodeVec = new Vector();
        Vector nodeVec = new Vector();
        Vector resultList = new Vector();
        Vector substituteVec = new Vector();

        try {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            for (int i=0; i<parentItemTBRowCnt; i++) {
                nodeVec.add((String)parentItemTB.getValueAt(i, 0));
            }

            for (int i=0; i<changeActTBRowCnt; i++) {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)this.assyCompVec.elementAt(i);

                if (bomcomponent.getItemCodeStr().trim().equals("") || bomcomponent.getItemCodeStr().trim().equals("Empty")) continue;

                nodeVec.add(bomcomponent.getItemCodeStr().trim());

                subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
                String substituteItemCodeStr = "";

                for (int inx=0; inx<subCompsItemCodeVec.size(); inx++) {
                    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subCompsItemCodeVec.elementAt(inx);
                    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

                    nodeVec.add(substituteItemCodeStr);
                }
            }

            BOMSearchDao dao = new BOMSearchDao();
            dao.IsExistItemPlm(connection, nodeVec);

            Vector itemVec = dao.getResultListVec();

            for (int i=0; i<parentItemTBRowCnt; i++) {
                if (itemVec.indexOf((String)parentItemTB.getValueAt(i, 0)) == -1)
                    resultList.addElement((String)parentItemTB.getValueAt(i, 0) + "|" + (String)parentItemTB.getValueAt(i, 0) + "@0" + "#Comps"  );
            }
            for (int i=0; i<changeActTBRowCnt; i++) {
                if (Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)).equals("")) continue;

                BOMAssyComponent cmp = (BOMAssyComponent)this.assyCompVec.elementAt(i);

                substituteVec = cmp.getSubAssyComponent();
                String substituteItemCodeStr = "";

                if (!cmp.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
                    if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
                        resultList.addElement(cmp.getItemCodeStr() + "|" + cmp.getItemCodeStr().trim() + "@" + cmp.getSeqInt() + "#Comps");
                    }
                } else {
                    if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
                        resultList.addElement("|" + cmp.getItemCodeStr().trim() + "@0" + "#Comps");
                    }
                }

                for (int inx = 0; inx < substituteVec.size(); inx++) {
                    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)substituteVec.elementAt(inx);
                    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

                    if (itemVec.indexOf(substituteItemCodeStr) == -1) {
                        resultList.addElement(cmp.getItemCodeStr() + "|" + substituteItemCodeStr + "@" + cmp.getSeqInt() + "#SubComps");
                    }
                }
            }
        } catch (SQLException sqle) {
            Kogger.error(getClass(), sqle);
            loadWarningMessage();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            loadWarningMessage("From 1 Stage error : " + e.toString());
        } finally {
            if (resource != null)
                resource.freeConnection(appReg.getString("plm"), connection);
        }

        if (resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                Vector v = new Vector();
                v.add(String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("@") + 1, resultList.elementAt(i).toString().indexOf("#")) + " "));
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
                v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("@")));

                if (String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("#") + 1, resultList.elementAt(i).toString().length())).equals("Comps")) {
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00198")/*부품코드가 PLM에 존재하지 않습니다.*/);
                } else {
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00136")/*대체부품코드가 PLM에 존재하지 않습니다.*/);
                }
                // 보정결과를 출력하게 될 vector에 결과를 Add 한다.
                resultVector.add(v);
                errorCount++;
                setResultList();

                double m1 = (double)((double)i / (double)resultList.size());
                progress = (int)(m1 * 100);
                setProgress(progress);
            }
        }
    }


    // 2 Stage : Item Code 와 Substitute Item Code가 동일한지 체크
    private void checkSameSubstitute() {
        if (!validationChk3.isSelected())
            return;

        int progress = 0;
        int count = 0;

        try {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.IsSameSubstitute(connection);

            Vector subCompsItemCodeVec = new Vector();

            for (int i=0; i<changeActTBRowCnt; i++) {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)this.assyCompVec.elementAt(i);

                if (bomcomponent.getItemCodeStr().trim().equals("") || bomcomponent.getItemCodeStr().trim().equals("Empty")) continue;

                subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
                String substituteItemCodeStr = "";

                for (int inx=0; inx<subCompsItemCodeVec.size(); inx++) {
                    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subCompsItemCodeVec.elementAt(inx);
                    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

                    if (bomcomponent.getItemCodeStr().trim().equals(substituteItemCodeStr)) {
                        Vector v = new Vector();
                        v.add("0");
                        v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                        v.add(" " + bomcomponent.getItemCodeStr().trim());
                        v.add(" " + substituteItemCodeStr);
                        v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00284")/*자부품코드와 대체부품코드가 동일합니다.*/);
                        resultVector.add(v);
                        errorCount++;
                        setResultList();
                    }
                }

                double m1 = (double)((double)count / (double)totalDataCount);
                progress = (int)(m1 * 100);
                setProgress(progress);
                count++;
            }

        } catch (SQLException sqle) {
            Kogger.error(getClass(), sqle);
            loadWarningMessage();
        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
            loadWarningMessage("From 2 Stage error : " + ex.toString());
        } finally {
            if (resource != null)
                resource.freeConnection(appReg.getString("plm"), connection);
        }
    }

    // 3 Stage : 필수 속성값(Quantity, 단위) 이 존재하는지 체크
    private void checkExistAttrs() {
        if (!validationChk4.isSelected())
            return;

        int progress = 0;
        int count = 0;

        String strQuantity = "";
        String strUnit = "";

        for (int i=0; i<changeActTBRowCnt; i++) {
            if (Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)).equals("")) continue;

            strQuantity = Utility.checkNVL(this.changeActivityTB.getValueAt(i, 5));
            strUnit = Utility.checkNVL(this.changeActivityTB.getValueAt(i, 6));

            if (strQuantity.equals("")) {
                Vector v = new Vector();
                v.add("0 ");
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                v.add(" " + Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)));
                v.add(" ");
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00234")/*수량이 비어있습니다.*/);
                resultVector.add(v);
                errorCount++;
                setResultList();
            }

            if (strUnit.equals(""))
            {
                Vector v = new Vector();
                v.add("0 ");
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                v.add(" " + Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)));
                v.add(" ");
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00127")/*단위가 비어있습니다.*/);
                resultVector.add(v);
                errorCount++;
                setResultList();
            }

            double m1 = (double)((double)count / (double)totalDataCount);
            progress = (int)(m1 * 100);
            setProgress(progress);
            count++;
        }
    }

    // 4 Stage : Parent Item 의 동일한 레벨에 같은 Item 이 사용되었는지 체크
    private void checkDupItem()
    {
        if (!validationChk5.isSelected())
            return;

        int progress = 0;
        int count = 0;

        for (int inx=0; inx<changeActTBRowCnt; inx=inx+2) {
            BOMAssyComponent bomcomponent = (BOMAssyComponent)this.assyCompVec.elementAt(inx);

            if (bomcomponent.getItemCodeStr().trim().equals("") || bomcomponent.getItemCodeStr().trim().equals("Empty")) continue;

            for (int jnx=0; jnx<changeActTBRowCnt; jnx=jnx+2) {
                BOMAssyComponent assy2 = (BOMAssyComponent)this.assyCompVec.elementAt(jnx);
                if ( bomcomponent.getItemCodeStr().equals(assy2.getItemCodeStr()) && (inx != jnx) )
                {
                    Vector v = new Vector();
                    v.add(bomcomponent.getSeqInt().toString() + " ");
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                    v.add(" " + bomcomponent.getParentItemCodeStr());
                    v.add(" " + bomcomponent.toString());
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00140")/*동일한 assy안에 같은 레벨의 부품이 중복되었습니다.*/);
                    resultVector.add(v);
                    errorCount++;
                    setResultList();
                }
            }

            double m1 = (double)((double)count / (double)totalDataCount);
            progress =  (int)(m1 * 100);
            setProgress(progress);
            count++;
        }
    }

    // 5 Stage : Parent Item 이 하위 Child Item 으로 재사용되었는지 체크
    private void checkDupParentItem()
    {
        if (!validationChk6.isSelected())
            return;

        int progress = 0;
        int count = 0;

        Vector nodeVec = new Vector();

        for (int i=0; i<parentItemTBRowCnt; i++) {
            nodeVec.add((String)parentItemTB.getValueAt(i, 0));
        }

        for (int i=0; i<changeActTBRowCnt; i++) {
            BOMAssyComponent bomcomponent = (BOMAssyComponent)this.assyCompVec.elementAt(i);

            if (bomcomponent.getItemCodeStr().trim().equals("") || bomcomponent.getItemCodeStr().trim().equals("Empty")) continue;

            if (  nodeVec.indexOf( bomcomponent.getItemCodeStr() )  > 0 )
            {
                Vector v = new Vector();
                v.add(bomcomponent.getSeqInt().toString() + " ");
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                v.add(" " + (bomcomponent.getParentItemCodeStr()==null?"":bomcomponent.getParentItemCodeStr().trim()));
                v.add(" " + bomcomponent.toString());
                v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00141")/*동일한 assy에 모부품과 자부품이 중복되었습니다.*/);
                resultVector.add(v);
                errorCount++;
                setResultList();
            }

            double m1 = (double)((double)count / (double)totalDataCount);
            progress =  (int)(m1 * 100);
            setProgress(progress);
            count++;
        }
    }

    // 6 Stage : 삭제된 부품이 존재하는지 체크
    private void checkObsoletedItem() {
        if (!validationChk7.isSelected())
            return;

        int progress = 0;

        Vector nodeVec = new Vector();
        Vector resultList = new Vector();

        try {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            for (int i=0; i<parentItemTBRowCnt; i++) {
                nodeVec.add((String)parentItemTB.getValueAt(i, 1));
            }

            for (int i=0; i<changeActTBRowCnt; i++) {
                if (Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)).equals("")) continue;

                nodeVec.add(Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)));
            }

            BOMSearchDao dao = new BOMSearchDao();
            dao.IsObsoletedItem(connection, nodeVec);

            Vector itemVec = dao.getResultListVec();
            Vector statusVec = dao.getResultListVec2();

            for (int i=0; i<parentItemTBRowCnt; i++) {
                int indx = itemVec.indexOf((String)parentItemTB.getValueAt(i, 1));

                if (indx > -1) {
                    if (statusVec.get(indx).toString().equalsIgnoreCase("Y")) {
                        resultList.addElement((String)parentItemTB.getValueAt(i, 1) + "|" + (String)parentItemTB.getValueAt(i, 1) + "@0");
                    }
                }
            }

            for (int i=0; i<changeActTBRowCnt; i++) {
                if (Utility.checkNVL(this.changeActivityTB.getValueAt(i, 1)).equals("")) continue;

                BOMAssyComponent cmp = (BOMAssyComponent)this.assyCompVec.elementAt(i);

                int indx = itemVec.indexOf(cmp.getItemCodeStr().trim());

                if (indx > -1) {
                    if (statusVec.get(indx).toString().equalsIgnoreCase("Y")) {
                        resultList.addElement(cmp.getItemCodeStr().trim() + "|" + cmp.getItemCodeStr().trim() + "@" + cmp.getSeqInt());
                    }
                }
            }

            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector v = new Vector();

                    v.add(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("@") + 1) + " ");
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/*오류*/);
                    v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
                    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("@")));
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00214")/*삭제된 부품입니다.*/);
                    resultVector.add(v);
                    errorCount++;
                    setResultList();

                    double m1 = (double)((double)i / (double)resultList.size());
                    progress = (int)(m1 * 100);
                    setProgress(progress);
                }
            }
        } catch (SQLException sqle) {
            Kogger.error(getClass(), sqle);
            loadWarningMessage();
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            loadWarningMessage("From 6 Stage error : " + e.toString());
        } finally {
            if (resource != null)
                resource.freeConnection(appReg.getString("plm"), connection);
        }
    }

    // 7 Stage : 부품이 Check-In 상태인지 체크
    private void checkCheckInItem() {
        if (!validationChk8.isSelected())
            return;

        int progress = 0;

        Vector nodeVec = new Vector();
        Vector subCompsItemCodeVec = new Vector();
        Vector substituteVec = new Vector();
        Vector coworkerVec = new Vector();
        Vector resultList = new Vector();

        try {
            for (int i=0; i<parentItemTBRowCnt; i++) {
                nodeVec.add((String)parentItemTB.getValueAt(i, 1));
            }

            for (int i=0; i<changeActTBRowCnt; i++) {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)this.assyCompVec.elementAt(i);

                if (bomcomponent.getItemCodeStr().trim().equals("") || bomcomponent.getItemCodeStr().trim().equals("Empty")) continue;

                nodeVec.add(bomcomponent.getItemCodeStr().trim());

                subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
                String substituteItemCodeStr = "";

                for (int inx=0; inx<subCompsItemCodeVec.size(); inx++) {
                    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subCompsItemCodeVec.elementAt(inx);
                    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

                    nodeVec.add(substituteItemCodeStr);
                }
            }

            coworkerVec = KETBomHelper.service.getCheckOuter(nodeVec);

            for (int j=0; j<parentItemTBRowCnt; j++) {
                for (int i = 0; i < coworkerVec.size(); i++) {
                    if ((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(0, coworkerVec.elementAt(i).toString().trim().indexOf("|"))).equals((String)parentItemTB.getValueAt(j, 1))) {
                        resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1)) + "$" + "|" + (String)parentItemTB.getValueAt(j, 1) + "@0" + "#Comps");
                    }
                }
            }

            for (int j=0; j<changeActTBRowCnt; j++) {
                BOMAssyComponent cmp = (BOMAssyComponent)this.assyCompVec.elementAt(j);

                if (cmp.getItemCodeStr().trim().equals("") || cmp.getItemCodeStr().trim().equals("Empty")) continue;

                substituteVec = cmp.getSubAssyComponent();
                String substituteItemCodeStr = "";

                for (int inx = 0; inx < substituteVec.size(); inx++) {
                    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)substituteVec.elementAt(inx);
                    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

                    for (int i = 0; i < coworkerVec.size(); i++) {
                        if (inx==0 && (coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(0, coworkerVec.elementAt(i).toString().trim().indexOf("|"))).equals(cmp.getItemCodeStr().trim())) {
                            resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1)) + "$" + cmp.getItemCodeStr() + "|" + cmp.getItemCodeStr().trim() + "@" + cmp.getSeqInt() + "#Comps");
                        }

                        if ((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(0, coworkerVec.elementAt(i).toString().trim().indexOf("|"))).equals(substituteItemCodeStr)) {
                            resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1)) + "$" + cmp.getItemCodeStr() + "|" + substituteItemCodeStr + "@" + cmp.getSeqInt() + "#SubComps");
                        }
                    }
                }
            }

            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector v = new Vector();
                    v.add(String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("@") + 1, resultList.elementAt(i).toString().indexOf("#")) + " "));
                    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/*에러*/);
                    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("$") + 1, resultList.elementAt(i).toString().indexOf("|")));
                    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("@")));

                    if (String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("#") + 1, resultList.elementAt(i).toString().length())).equals("Comps")) {
                        v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00195", resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("$")))/*부품이 {0}에 의해 체크아웃 상태입니다.*/);
                    } else {
                        v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00134", resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("$")))/*대체부품이 {0}에 의해 체크아웃 상태입니다.*/);
                    }
                    // 보정결과를 출력하게 될 vector에 결과를 Add 한다.
                    resultVector.add(v);
                    errorCount++;
                    setResultList();

                    double m1 = (double)((double)i / (double)resultList.size());
                    progress = (int)(m1 * 100);
                    setProgress(progress);
                }
            }
        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
            loadWarningMessage("From 7 Stage error : " + ex.toString());
        }
    }

    // 8 Stage : 부품에 연결된 도면/문서가 Check-In 상태인지 체크
    private void checkCheckInDoc() {
        if (!validationChk9.isSelected())
            return;
    }

    public void stopTimer() {
        if (timer.isRunning())
            timer.stop();
    }

    public void dispose() {
        int count = getComponentCount();
        for (int i = 0; i < count; i++) {
            Component c = getComponent(i);
            this.remove(c);
            c = null;
        }
        super.dispose();
        System.gc();
    }

    public void setProgress(int m) {
        m_counter = m;
    }

    public void setSuccess() {
        stopTimer();
    }

    private void setResultList() {
        errorLbl.setText(String.valueOf(errorCount) + " errors, ");
        warningLbl.setText(String.valueOf(warningCount) + " warnings");
    }

    private void loadWarningMessage() {
        String message = "MultipleBOMECOValidationDialog will be closed because of PLM Connection Error.";

        MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
        messagebox.setVisible(true);
        messagebox.setModal(true);

        dispose();
        stopOperation();
    }

    private void loadWarningMessage(String message) {
        MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }

    private void loadWarningMessage(JDialog dlg, String message) {
        MessageBox messagebox = new MessageBox(app.getDesktop(), message, "Warning", MessageBox.WARNING);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }
}
