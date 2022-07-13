package e3ps.bom.command.bomvalidation;

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
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.endworking.EndWorkingCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMValidationDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private BOMRegisterApplicationPanel bomPanel;
    private JFrame desktop;
    private BOMTreeTableModel model;
    Registry appReg;

    DBConnectionManager resource = null;
    Connection connection = null;

    private BOMValidationResultFrame fr;
    private JProgressBar progress;
    public int m_counter = 0;
    public Thread runner = null;
    private Timer timer;
    private final static int TIME_UNIT = 100;
    private int totalDataCount = 0;
    private int errorCount = 0;
    private int warningCount = 0;
    boolean isFlag = false;

    private JLabel titleLbl, totalCntLbl, warningLbl, errorLbl, validationLbl1, validationLbl2, validationLbl3, validationLbl4, validationLbl5, validationLbl6, validationLbl7, validationLbl8,
	    validationLbl9, validationLbl10, validationLbl11, validationLbl12, validationLbl13; // , validationLbl14, validationLbl15;
    private JCheckBox validationChk1, validationChk2, validationChk3, validationChk4, validationChk5, validationChk6, validationChk7, validationChk8, validationChk9, validationChk10, validationChk11,
	    validationChk12, validationChk13; // , validationChk14, validationChk15;
    private JButton okBtn, cancelBtn;

    // private String errorCode = "";
    private Vector resultVector = new Vector();
    // private Hashtable supplyhash = new Hashtable();
    private BOMTreeNode rootNode;
    private boolean cancelFlag = false;
    private String strValidationForEnd = BOMBasicInfoPool.getValidationForEnd();
    private String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

    private boolean chkView = true;

    class TimerListener implements ActionListener {
	public void actionPerformed(ActionEvent evt) {
	    progress.setValue(m_counter);

	    if (m_counter == 100) {
		setSuccess();
	    }
	}
    }

    public BOMValidationDialog(JFrame frame, AbstractAIFUIApplication app, boolean chk) {
	super(frame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00019")/* BOM 검증 */, true);
	this.app = app;
	this.desktop = app.getDesktop();
	this.chkView = chk;
	this.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		dispose();
		stopOperation();
	    }
	});

	try {
	    jInit();
	    setEvent();

	    setSize(566, 490);
	    setResizable(false);

	    ScreenCenterer scent = new ScreenCenterer();
	    Dimension dimCenter = new Dimension(scent.getCenterDim(this));
	    setLocation(dimCenter.width, dimCenter.height);
	    setVisible(true);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    loadWarningMessage(messageRegistry.getString("valid3"));
	}
    }

    private void jInit() {
	appReg = Registry.getRegistry(app);

	bomPanel = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	model = (BOMTreeTableModel) bomPanel.getTreeTableModel();

	JPanel contentpane = (JPanel) this.getContentPane();
	contentpane.setLayout(null);
	contentpane.setPreferredSize(new Dimension(650, 470));

	JPanel panel = new JPanel();
	panel.setBounds(new Rectangle(0, 0, 650, 470));
	panel.setLayout(null);

	JPanel progressPanel = new JPanel();
	JPanel contentsPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00259")/* 실행 */, appReg.getImageIcon("bomvalidationIcon"));
	okBtn.setFont(FontList.defaultFont);
	okBtn.setMargin(new Insets(0, 5, 0, 5));
	cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/* 취소 */, appReg.getImageIcon("closeIcon"));
	cancelBtn.setFont(FontList.defaultFont);
	cancelBtn.setMargin(new Insets(0, 5, 0, 5));

	progress = new JProgressBar();
	progress.setMinimum(0);
	progress.setMaximum(100);
	progress.setStringPainted(true);

	validationChk1 = new JCheckBox("", true);
	validationChk2 = new JCheckBox("", true);
	validationChk3 = new JCheckBox("", true);
	validationChk4 = new JCheckBox("", true);
	validationChk5 = new JCheckBox("", true);
	validationChk6 = new JCheckBox("", true);
	validationChk7 = new JCheckBox("", true);
	validationChk8 = new JCheckBox("", true);
	validationChk9 = new JCheckBox("", true);
	validationChk10 = new JCheckBox("", true);
	validationChk11 = new JCheckBox("", true);
	validationChk12 = new JCheckBox("", true);
	validationChk13 = new JCheckBox("", true);

	if (strValidationForEnd.equals("LAST") || strValidationForEnd.equals("ADMINLAST")) {
	    validationChk1.setSelected(true);
	    validationChk2.setSelected(true);
	    validationChk3.setSelected(true);
	    validationChk4.setSelected(true);
	    validationChk5.setSelected(true);
	    validationChk6.setSelected(true);
	    validationChk7.setSelected(true);
	    validationChk8.setSelected(true);
	    validationChk9.setSelected(true);
	    validationChk10.setSelected(true);
	    validationChk11.setSelected(true);
	    validationChk12.setSelected(true);
	    validationChk13.setSelected(true);

	    validationChk1.setEnabled(false);
	    validationChk2.setEnabled(false);
	    validationChk3.setEnabled(false);
	    validationChk4.setEnabled(false);
	    validationChk5.setEnabled(false);
	    validationChk6.setEnabled(false);
	    validationChk7.setEnabled(false);
	    validationChk8.setEnabled(false);
	    validationChk9.setEnabled(false);
	    validationChk10.setEnabled(false);
	    validationChk11.setEnabled(false);
	    validationChk12.setEnabled(false);
	    validationChk13.setEnabled(false);
	}

	titleLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00019")/* BOM 검증 */+ "...");
	titleLbl.setFont(FontList.boldFont);
	totalCntLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00333", 0)/* 총 개수 : {0} */);
	totalCntLbl.setFont(FontList.defaultFont);
	warningLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00081", 0)/* 경고 : {0} */);
	warningLbl.setFont(FontList.defaultFont);
	errorLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00267", 0)/* 오류 : {0} */);
	errorLbl.setFont(FontList.defaultFont);

	validationLbl1 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00004", 1)/* {0} 단계 : BOM 구성에 필요한 부품이 PLM에 채번되어 있는지 체크 */);
	validationLbl1.setFont(FontList.defaultFont);
	validationLbl2 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00012", 2)/* {0} 단계 : 부품코드와 대체부품코드가 동일한지 체크 */);
	validationLbl2.setFont(FontList.defaultFont);
	validationLbl3 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00017", 3)/* {0} 단계 : 필수 속성값(제품 : 수량, 단위 , 금형 : 수량, 재질, 경도(From), 경도(To)) 이 존재하는지 체크 */);
	validationLbl3.setFont(FontList.defaultFont);
	validationLbl4 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00006", 4)/* {0} 단계 : 모부품의 동일한 레벨에 같은 부품이 사용되었는지 체크 */);
	validationLbl4.setFont(FontList.defaultFont);
	validationLbl5 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00007", 5)/* {0} 단계 : 모부품이 하위 자부품으로 재사용되었는지 체크 */);
	validationLbl5.setFont(FontList.defaultFont);
	validationLbl6 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00005", 6)/* {0} 단계 : 동일한 모부품이 서로 다른 구조의 자부품으로 사용되었는지 체크 */);
	validationLbl6.setFont(FontList.defaultFont);
	validationLbl7 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00014", 7)/* {0} 단계 : 신규 이관하려는 BOM 구조가 이미 BOM마스터에 적용되었는지 체크 */);
	validationLbl7.setFont(FontList.defaultFont);
	validationLbl8 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00011", 8)/* {0} 단계 : 부품중에서 ERP로 I/F 되지 않은 신규 부품 존재 여부 체크 */);
	validationLbl8.setFont(FontList.defaultFont);
	validationLbl9 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00010", 9)/* {0} 단계 : 부품이 체크인 상태인지 체크 */);
	validationLbl9.setFont(FontList.defaultFont);
	validationLbl10 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00008", 10)/* {0} 단계 : 부품에 연결된 도면/문서가 체크인 상태인지 체크 */);
	validationLbl10.setFont(FontList.defaultFont);
	validationLbl11 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00009", 11)/* {0} 단계 : 부품에 연결된 비철/수지 원재료 코드 밑에 올바른 스트랩 코드가 존재하는지 체크 */);
	validationLbl11.setFont(FontList.defaultFont);
	validationLbl12 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00015", 12)/* {0} 단계 : 터미널 반제품일때 자부품에 후도금제품코드가 존재하는지 체크 */);
	validationLbl12.setFont(FontList.defaultFont);
	validationLbl13 = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00013", 13)/* {0} 단계 : 삭제된 부품이 존재하는지 체크 */);
	validationLbl13.setFont(FontList.defaultFont);

	Border border1;

	border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(134, 134, 134));
	this.getContentPane().setLayout(null);

	progressPanel.setBounds(new Rectangle(3, 1, 630, 61));
	progressPanel.setLayout(null);
	contentsPanel.setBorder(border1);
	contentsPanel.setBounds(new Rectangle(6, 65, 547, 345));
	contentsPanel.setLayout(null);
	buttonPanel.setBounds(new Rectangle(3, 400, 574, 49));
	buttonPanel.setLayout(null);
	cancelBtn.setActionCommand("Cancel");
	okBtn.setActionCommand("OK");

	okBtn.setBounds(new Rectangle(190, 20, 75, 26));
	cancelBtn.setBounds(new Rectangle(270, 20, 77, 26));
	// if(strValidationForEnd.equals("LAST") || strValidationForEnd.equals("ADMINLAST"))
	// {
	// cancelBtn.setEnabled(false);
	// }

	validationChk1.setBounds(new Rectangle(12, 6, 20, 23));
	validationChk2.setBounds(new Rectangle(12, 30, 20, 23));
	validationChk3.setBounds(new Rectangle(12, 54, 20, 23));
	validationChk4.setBounds(new Rectangle(12, 78, 20, 23));
	validationChk5.setBounds(new Rectangle(12, 102, 20, 23));
	validationChk6.setBounds(new Rectangle(12, 126, 20, 23));
	validationChk7.setBounds(new Rectangle(12, 150, 20, 23));
	validationChk8.setBounds(new Rectangle(12, 174, 20, 23));
	validationChk9.setBounds(new Rectangle(12, 198, 20, 23));
	validationChk10.setBounds(new Rectangle(12, 222, 20, 23));
	validationChk11.setBounds(new Rectangle(12, 246, 20, 23));
	validationChk12.setBounds(new Rectangle(12, 270, 20, 23));
	validationChk13.setBounds(new Rectangle(12, 294, 20, 23));

	validationLbl1.setBounds(new Rectangle(40, 6, 490, 23));
	validationLbl2.setBounds(new Rectangle(40, 30, 490, 23));
	validationLbl3.setBounds(new Rectangle(40, 54, 490, 23));
	validationLbl4.setBounds(new Rectangle(40, 78, 490, 23));
	validationLbl5.setBounds(new Rectangle(40, 102, 490, 23));
	validationLbl6.setBounds(new Rectangle(40, 126, 490, 23));
	validationLbl7.setBounds(new Rectangle(40, 150, 490, 23));
	validationLbl8.setBounds(new Rectangle(40, 174, 490, 23));
	validationLbl9.setBounds(new Rectangle(40, 198, 490, 23));
	validationLbl10.setBounds(new Rectangle(40, 222, 490, 23));
	validationLbl11.setBounds(new Rectangle(40, 246, 490, 23));
	validationLbl12.setBounds(new Rectangle(40, 270, 490, 23));
	validationLbl13.setBounds(new Rectangle(40, 294, 490, 23));

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
	contentsPanel.add(validationChk2, null);
	contentsPanel.add(validationLbl2, null);
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
	contentsPanel.add(validationChk10, null);
	contentsPanel.add(validationLbl10, null);
	contentsPanel.add(validationChk11, null);
	contentsPanel.add(validationLbl11, null);
	contentsPanel.add(validationChk12, null);
	contentsPanel.add(validationLbl12, null);
	contentsPanel.add(validationChk13, null);
	contentsPanel.add(validationLbl13, null);

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

		BOMBasicInfoPool.setHasErrorInValidation(false);

		// 보정결과 list 가 있을때에는 결과를 보여주는 frame 을 띄운다.
		if ((errorCount != 0) || (warningCount != 0)) {
		    BOMBasicInfoPool.setErrorFrame(false); // shin...에러있음.
		    BOMValidationResultFrame fr = new BOMValidationResultFrame(app, resultVector, errorCount, warningCount);
		} else {
		    BOMBasicInfoPool.setErrorFrame(true);
		}

		if (strValidationForEnd.equals("LAST")) {
		    BOMBasicInfoPool.setBomValidationResult(true);

		    if (BOMBasicInfoPool.getHasErrorInValidation()) {
			MessageBox m = new MessageBox(desktop, messageRegistry.getString("valid1"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00021")/* BOM 검증 오류 */,
			        MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
		    } else {
			try {
			    if (chkView) {
				EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
				cmd.executeModal();
			    } else {
				EndWorkingCmd cmd = new EndWorkingCmd(desktop, app, chkView);
				cmd.executeModal();
			    }
			} catch (Exception ex) {
			    Kogger.error(getClass(), ex);
			}
		    }
		} else if (strValidationForEnd.equals("NOTLAST")) {
		    BOMBasicInfoPool.setBomValidationResult(true);

		    Kogger.debug(getClass(), "==============NOTLAST 1================");
		    try {
			if (chkView) {
			    EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
			    cmd.executeModal();
			} else {
			    EndWorkingCmd cmd = new EndWorkingCmd(desktop, app, chkView);
			    cmd.executeModal();
			}
		    } catch (Exception ex) {
			Kogger.error(getClass(), ex);
		    }
		} else if (strValidationForEnd.equals("ADMINLAST")) {
		    BOMBasicInfoPool.setBomValidationResult(true);

		    if (BOMBasicInfoPool.getHasErrorInValidation()) {
			MessageBox m = new MessageBox(desktop, messageRegistry.getString("valid1"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00021")/* BOM 검증 오류 */,
			        MessageBox.ERROR);
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
	rootNode = (BOMTreeNode) model.getRootNode();
	model.fireTreeChanged(rootNode);

	totalDataCount = rootNode.getChildCount();

	for (int i = 0; i < totalDataCount; i++) {
	    // 1 stage : BOM 구성에 필요한 Item 이 PLM에 채번되어 있는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationLbl1.setForeground(Color.red);
	    validationLbl1.setFont(FontList.boldFont);
	    checkIsExistItemPlm(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk1.setSelected(false);
	    if (cancelFlag)
		return;

	    // 2 stage : Item Code 와 Substitute Item Code가 동일한지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk1.setSelected(false);
	    validationLbl1.setForeground(Color.black);
	    validationLbl1.setFont(FontList.defaultFont);
	    validationLbl2.setForeground(Color.red);
	    validationLbl2.setFont(FontList.boldFont);
	    checkSameSubstitute(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk2.setSelected(false);
	    if (cancelFlag)
		return;

	    // 3 Stage : 필수 속성값(단위, 수량) 이 존재하는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk2.setSelected(false);
	    validationLbl2.setForeground(Color.black);
	    validationLbl2.setFont(FontList.defaultFont);
	    validationLbl3.setForeground(Color.red);
	    validationLbl3.setFont(FontList.boldFont);
	    checkExistAttrs(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk3.setSelected(false);
	    if (cancelFlag)
		return;

	    // 4 Stage : Parent Item의 동일한 레벨에 같은 Item이 사용되었는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk3.setSelected(false);
	    validationLbl3.setForeground(Color.black);
	    validationLbl3.setFont(FontList.defaultFont);
	    validationLbl4.setForeground(Color.red);
	    validationLbl4.setFont(FontList.boldFont);
	    checkDupItem(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk4.setSelected(false);
	    if (cancelFlag)
		return;

	    // 5 Stage : Parent Item이 하위 Child Item으로 재사용되었는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk4.setSelected(false);
	    validationLbl4.setForeground(Color.black);
	    validationLbl4.setFont(FontList.defaultFont);
	    validationLbl5.setForeground(Color.red);
	    validationLbl5.setFont(FontList.boldFont);
	    checkDupParentItem(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk5.setSelected(false);
	    if (cancelFlag)
		return;

	    // 6 Stage : 동일한 Parent Item 이 서로 다른 구조의 Child Item으로 사용되었는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk5.setSelected(false);
	    validationLbl5.setForeground(Color.black);
	    validationLbl5.setFont(FontList.defaultFont);
	    validationLbl6.setForeground(Color.red);
	    validationLbl6.setFont(FontList.boldFont);
	    checkCompareStructure(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk6.setSelected(false);
	    if (cancelFlag)
		return;

	    // 7 Stage : 신규 이관하려는 BOM 구조가 이미 BOMMaster에 적용되었는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk6.setSelected(false);
	    validationLbl6.setForeground(Color.black);
	    validationLbl6.setFont(FontList.defaultFont);
	    validationLbl7.setForeground(Color.red);
	    validationLbl7.setFont(FontList.boldFont);
	    checkExistBOMStructure(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk7.setSelected(false);
	    if (cancelFlag)
		return;

	    // 8 Stage : Item중에서 ERP로 I/F 되지 않은 신규 Item 존재 여부 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk7.setSelected(false);
	    validationLbl7.setForeground(Color.black);
	    validationLbl7.setFont(FontList.defaultFont);
	    validationLbl8.setForeground(Color.red);
	    validationLbl8.setFont(FontList.boldFont);
	    checkIsExistItemMaster(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk8.setSelected(false);
	    if (cancelFlag)
		return;

	    // 9 Stage : Item이 Check-In 상태인지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk8.setSelected(false);
	    validationLbl8.setForeground(Color.black);
	    validationLbl8.setFont(FontList.defaultFont);
	    validationLbl9.setForeground(Color.red);
	    validationLbl9.setFont(FontList.boldFont);
	    checkCheckInItem(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk9.setSelected(false);
	    if (cancelFlag)
		return;

	    // 10 Stage : Item에 연결된 도면/문서가 Check-In 상태인지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk9.setSelected(false);
	    validationLbl9.setForeground(Color.black);
	    validationLbl9.setFont(FontList.defaultFont);
	    validationLbl10.setForeground(Color.red);
	    validationLbl10.setFont(FontList.boldFont);
	    checkCheckInDoc(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk10.setSelected(false);
	    if (cancelFlag)
		return;

	    // 11 Stage : Item에 연결된 비철, 수지 원재료 코드 밑에 올바른 스트랩 코드가 달려있는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk10.setSelected(false);
	    validationLbl10.setForeground(Color.black);
	    validationLbl10.setFont(FontList.defaultFont);
	    validationLbl11.setForeground(Color.red);
	    validationLbl11.setFont(FontList.boldFont);
	    checkGenItem(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk11.setSelected(false);
	    if (cancelFlag)
		return;

	    // 12 Stage : 제품코드가 터미널 반제품일때 자부품에 후도금제품코드가 존재하는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk11.setSelected(false);
	    validationLbl11.setForeground(Color.black);
	    validationLbl11.setFont(FontList.defaultFont);
	    validationLbl12.setForeground(Color.red);
	    validationLbl12.setFont(FontList.boldFont);
	    checkAfterPlating(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk12.setSelected(false);
	    if (cancelFlag)
		return;

	    // 13 Stage : 삭제된 부품이 존재하는지 체크
	    timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
	    timer.start();
	    validationChk12.setSelected(false);
	    validationLbl12.setForeground(Color.black);
	    validationLbl12.setFont(FontList.defaultFont);
	    validationLbl13.setForeground(Color.red);
	    validationLbl13.setFont(FontList.boldFont);
	    checkObsoletedItem(rootNode, totalDataCount);
	    setProgress(100);
	    validationChk13.setSelected(false);
	    if (cancelFlag)
		return;
	}

	cancelBtn.setEnabled(false);

	// 결과 list에 에러값이 없으면 메시지를 띄우고 이관이 가능하도록 flag처리를 해준다.
	if (errorCount == 0) {
	    if (!strValidationForEnd.equals("")) {
		BOMBasicInfoPool.setBomValidationResult(true);
		BOMBasicInfoPool.setHasErrorInValidation(false);
	    }

	    if (warningCount == 0) {
		// loadWarningMessage(messageRegistry.getString("valid6"));
		// setVisible(false);

		if (!BOMBasicInfoPool.getHasErrorInValidation() && BOMBasicInfoPool.getBomValidationResult()) {
		    try {
			if (strValidationForEnd.equals("LAST") || strValidationForEnd.equals("NOTLAST")) {
			    Kogger.debug(getClass(), "==============NOTLAST 2================");
			    if (chkView) {
				EndWorkingCmd cmd = new EndWorkingCmd(desktop, app);
				cmd.executeModal();
			    } else {
				EndWorkingCmd cmd = new EndWorkingCmd(desktop, app, chkView);
				cmd.executeModal();
			    }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}

		dispose();

		// shin....위의 주석처리한 메세지 부분을 이쪽으로 옮김..쓰레드와 충돌이 나는지?? 프로세서가 죽음..여기에 두면 괜찮음.
		if (chkView) {
		    loadWarningMessage(messageRegistry.getString("valid6"));
		    setVisible(false);
		}

		BOMBasicInfoPool.setErrorFrame(true); // shin...
	    } else {
		dispose();

		if (chkView) {
		    // Error 가 있을 경우에는 error list 를 보여주는 Dialog 를 띄워준다.
		    BOMBasicInfoPool.setErrorFrame(false); // shin...에러있음.
		    fr = new BOMValidationResultFrame(app, resultVector, errorCount, warningCount);
		    loadWarningMessage(fr, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00022", errorCount, warningCount)/* BOM 검증 완료!\n에러: {0}개, 경고: {1}개 */);
		    setVisible(false);
		}
	    }
	} else {
	    if (!strValidationForEnd.equals("")) {
		if (strValidationForEnd.equals("NOTLAST"))
		    BOMBasicInfoPool.setBomValidationResult(true);
		else
		    BOMBasicInfoPool.setBomValidationResult(false);
		BOMBasicInfoPool.setHasErrorInValidation(true);
	    }

	    dispose();

	    BOMBasicInfoPool.setHasErrorInValidation(true); // shin...에러있음.
	    // Error 가 있을 경우에는 error list 를 보여주는 Dialog 를 띄워준다.
	    BOMBasicInfoPool.setErrorFrame(false); // shin...에러있음.
	    fr = new BOMValidationResultFrame(app, resultVector, errorCount, warningCount);
	    loadWarningMessage(fr, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00022", errorCount, warningCount)/* BOM 검증 완료!\n에러: {0}개, 경고: {1}개 */);
	    setVisible(false);
	}

    }

    // 1 stage : BOM 구성에 필요한 Item 이 PLM에 채번되어 있는지 체크
    private void checkIsExistItemPlm(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk1.isSelected()) {
	    return;
	}

	int progress = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	Vector nodeVec = new Vector();
	Vector subCompsItemCodeVec = new Vector();
	Vector resultList = new Vector();
	Vector substituteVec = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(appReg.getString("plm"));

	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

		nodeVec.add(bomcomponent.getItemCodeStr() == null ? "" : bomcomponent.getItemCodeStr().trim());

		subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
		String substituteItemCodeStr = "";

		for (int inx = 0; inx < subCompsItemCodeVec.size(); inx++) {
		    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) subCompsItemCodeVec.elementAt(inx);
		    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

		    nodeVec.add(substituteItemCodeStr);
		}
	    }

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.IsExistItemPlm(connection, nodeVec);

	    Vector itemVec = dao.getResultListVec();

	    while (enum1.hasMoreElements()) {
		BOMTreeNode selectedNode = (BOMTreeNode) enum1.nextElement();
		BOMAssyComponent cmp = selectedNode.getBOMComponent();

		substituteVec = cmp.getSubAssyComponent();
		String substituteItemCodeStr = "";

		if (!cmp.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
		    BOMAssyComponent parentCmp = ((BOMTreeNode) selectedNode.getParent()).getBOMComponent();

		    if ((parentCmp.getNewFlagStr() == null ? "" : parentCmp.getNewFlagStr()).equals("NEW")) {
			if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
			    resultList.addElement(parentCmp.getItemCodeStr() + "|" + cmp.getItemCodeStr().trim() + "^" + cmp.getSeqInt() + "#Comps");
			}

			for (int inx = 0; inx < substituteVec.size(); inx++) {
			    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) substituteVec.elementAt(inx);
			    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

			    if (itemVec.indexOf(substituteItemCodeStr) == -1) {
				resultList.addElement(parentCmp.getItemCodeStr() + "|" + substituteItemCodeStr + "^" + cmp.getSeqInt() + "#SubComps");
			    }
			}
		    }
		} else {
		    if (modelName.equals(cmp.getItemCodeStr())) {
			if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
			    resultList.addElement("|" + cmp.getItemCodeStr().trim() + "^0" + "#Comps");
			}
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
		v.add(String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1, resultList.elementAt(i).toString().indexOf("#")) + " "));
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
		v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));

		if (String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("#") + 1, resultList.elementAt(i).toString().length())).equals("Comps")) {
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00198")/* 부품코드가 PLM에 존재하지 않습니다. */);
		} else {
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00136")/* 대체부품코드가 PLM에 존재하지 않습니다. */);
		}
		// 보정결과를 출력하게 될 vector에 결과를 Add 한다.
		resultVector.add(v);
		errorCount++;
		setResultList();

		double m1 = (double) ((double) i / (double) resultList.size());
		progress = (int) (m1 * 100);
		setProgress(progress);
	    }
	}
    }

    // 2 Stage : Item Code 와 Substitute Item Code가 동일한지 체크
    private void checkSameSubstitute(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk2.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	String supplyType = "";

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(appReg.getString("plm"));

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.IsSameSubstitute(connection);

	    Vector substituteItemVec = dao.getResultListVec();
	    Vector seqVec = dao.getResultListVec2();

	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();

		if (allListNode.getParent() == null) {
		    continue;
		}

		BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();
		BOMAssyComponent parentCmp = ((BOMTreeNode) allListNode.getParent()).getBOMComponent();

		for (int i = 0; i < substituteItemVec.size(); i++) {
		    if ((parentCmp.getNewFlagStr() == null ? "" : parentCmp.getNewFlagStr().trim()).equals("NEW")) {
			if ((bomassy.toString().equals(substituteItemVec.elementAt(i))) && (bomassy.getSortOrderStr().equals(seqVec.elementAt(i)))) {
			    Vector v = new Vector();
			    v.add(bomassy.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + parentCmp.getItemCodeStr());
			    v.add(" " + substituteItemVec.elementAt(i));
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00284")/* 자부품코드와 대체부품코드가 동일합니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
		    }
		}
		double m1 = (double) ((double) count / (double) totalDataCount);
		progress = (int) (m1 * 100);
		setProgress(progress);
		count++;
	    }
	} catch (SQLException sqle) {
	    Kogger.error(getClass(), sqle);
	    loadWarningMessage();
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    loadWarningMessage("From 4 Stage error : " + ex.toString());
	} finally {
	    if (resource != null)
		resource.freeConnection(appReg.getString("plm"), connection);
	}
    }

    // 3 Stage : 필수 속성값(단위, 수량) 이 존재하는지 체크
    private void checkExistAttrs(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk3.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();

	while (enum0.hasMoreElements()) {
	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();

	    if (allListNode.getParent() == null) {
		continue;
	    }

	    BOMAssyComponent bomparent = ((BOMTreeNode) allListNode.getParent()).getBOMComponent();

	    if ((bomparent.getNewFlagStr()).equals("NEW")) {
		// 수량은 제품,금형 공용
		if (bomassy.getQuantityDbl() == null || (bomassy.getQuantityDbl()).equals("")) {
		    Vector v = new Vector();
		    v.add(bomassy.getSeqInt().toString() + " ");
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		    v.add(" " + bomassy.getParentItemCodeStr());
		    v.add(" " + bomassy.toString());
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00234")/* 수량이 비어있습니다. */);
		    resultVector.add(v);
		    errorCount++;
		    setResultList();
		}

		// [2011-02-21] 임승영D 요구사항 처리 :: 원재료, 스크랩인 경우 수량을 0으로 셋팅하는 걸로 인한 Validation 추가
		// 수량정보가 0인 경우 에러처리
		if (bomassy.getQuantityDbl() == 0) {
		    Vector v = new Vector();
		    v.add(bomassy.getSeqInt().toString() + " ");
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		    v.add(" " + bomassy.getParentItemCodeStr());
		    v.add(" " + bomassy.toString());
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00233", 0)/* 수량이 '{0}'입니다. */);
		    resultVector.add(v);
		    errorCount++;
		    setResultList();
		}

		String headCode = bomparent.getItemCodeStr();
		KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
		String strType = "";
		try {
		    WTPart part = kh.searchItem(headCode);
		    strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType); // 부품유형
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		// shin......ERP에서 체크해서 제품일때 금형일때 if 로 구분해야 한다. @@@@
		if (PartUtil.isProductType(strType)) {
		    // 제품일때
		    if (bomassy.getUitStr() == null || (bomassy.getUitStr()).equals("")) {
			Vector v = new Vector();
			v.add(bomassy.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			v.add(" " + bomassy.getParentItemCodeStr());
			v.add(" " + bomassy.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00127")/* 단위가 비어있습니다. */);
			resultVector.add(v);
			errorCount++;
			setResultList();
		    }
		} else if (strType.equals("D")) {
		    // 금형일때...
		    if (bomassy.getMaterialStr() == null || (bomassy.getMaterialStr()).equals("")) {
			Vector v = new Vector();
			v.add(bomassy.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			v.add(" " + bomassy.getParentItemCodeStr());
			v.add(" " + bomassy.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00305")/* 재질이 비어있습니다. */);
			resultVector.add(v);
			errorCount++;
			setResultList();
		    }
		    if (bomassy.getHardnessFrom() == null || (bomassy.getHardnessFrom()).equals("")) {
			Vector v = new Vector();
			v.add(bomassy.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			v.add(" " + bomassy.getParentItemCodeStr());
			v.add(" " + bomassy.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00083")/* 경도(From)가 비어있습니다. */);
			resultVector.add(v);
			errorCount++;
			setResultList();
		    }
		    if (bomassy.getHardnessTo() == null || (bomassy.getHardnessTo()).equals("")) {
			Vector v = new Vector();
			v.add(bomassy.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			v.add(" " + bomassy.getParentItemCodeStr());
			v.add(" " + bomassy.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00085")/* 경도(To)가 비어있습니다. */);
			resultVector.add(v);
			errorCount++;
			setResultList();
		    }
		} else {
		    Vector v = new Vector();
		    v.add(bomassy.getSeqInt().toString() + " ");
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		    v.add(" " + bomassy.getParentItemCodeStr());
		    v.add(" " + bomassy.toString());
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00262")/* 알수없는 부품타입입니다. */);
		    resultVector.add(v);
		    errorCount++;
		    setResultList();
		}
	    }

	    double m1 = (double) ((double) count / (double) totalDataCount);
	    progress = (int) (m1 * 100);
	    setProgress(progress);
	    count++;
	}
    }

    // 4 Stage : Parent Item의 동일한 레벨에 같은 Item이 사용되었는지 체크
    private void checkDupItem(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk4.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	String model = (rootNode.getBOMComponent()).toString();

	while (enum0.hasMoreElements()) {
	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();

	    if (bomassy.getNewFlagStr().equalsIgnoreCase("NEW") && bomassy.getLevelInt().intValue() != 0) {
		for (int inx = 0; inx < allListNode.getChildCount(); inx++) {
		    for (int jnx = 0; jnx < allListNode.getChildCount(); jnx++) {
			BOMAssyComponent assy1 = ((BOMTreeNode) allListNode.getChildAt(inx)).getBOMComponent();
			BOMAssyComponent assy2 = ((BOMTreeNode) allListNode.getChildAt(jnx)).getBOMComponent();
			if ((assy1.getLevelInt().intValue() - assy2.getLevelInt().intValue() == 0) && assy1.getItemCodeStr().equals(assy2.getItemCodeStr()) && (inx != jnx)) {
			    Vector v = new Vector();
			    v.add(assy1.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + assy1.getParentItemCodeStr());
			    v.add(" " + assy1.toString());
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00140")/* 동일한 assy안에 같은 레벨의 부품이 중복되었습니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
		    }
		}
	    }

	    double m1 = (double) ((double) count / (double) totalDataCount);
	    progress = (int) (m1 * 100);
	    setProgress(progress);
	    count++;
	}
    }

    // 5 Stage : Parent Item이 하위 Child Item으로 재사용되었는지 체크
    private void checkDupParentItem(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk5.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	String model = (rootNode.getBOMComponent()).toString();

	while (enum0.hasMoreElements()) {
	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();

	    if (bomassy.getNewFlagStr().equalsIgnoreCase("NEW")) {
		for (int inx = 0; inx < allListNode.getChildCount(); inx++) {
		    for (int jnx = 0; jnx < allListNode.getChildCount(); jnx++) {
			BOMAssyComponent assy1 = ((BOMTreeNode) allListNode.getChildAt(inx)).getBOMComponent();
			BOMAssyComponent assy2 = ((BOMTreeNode) allListNode.getChildAt(jnx)).getBOMComponent();
			if ((assy1.getLevelInt().intValue() - assy2.getLevelInt().intValue() == 1) && assy1.getItemCodeStr().equals(assy2.getItemCodeStr())) {
			    Vector v = new Vector();
			    v.add(assy1.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + (assy1.getParentItemCodeStr() == null ? "" : assy1.getParentItemCodeStr().trim()));
			    v.add(" " + assy1.toString());
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00141")/* 동일한 assy에 모부품과 자부품이 중복되었습니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
		    }
		}
	    }

	    double m1 = (double) ((double) count / (double) totalDataCount);
	    progress = (int) (m1 * 100);
	    setProgress(progress);
	    count++;
	}
    }

    // 6 Stage : 동일한 Parent Item 이 서로 다른 구조의 Child Item으로 사용되었는지 체크
    private void checkCompareStructure(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk6.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	boolean exist = false;

	while (enum0.hasMoreElements()) {
	    String itemCodeUpStr = "";
	    String itemCodeDnStr = "";
	    Vector itemCodeUpVector = new Vector();
	    Vector itemCodeDnVector = new Vector();
	    Vector substituteUpVector = new Vector();
	    Vector substituteDnVector = new Vector();

	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    BOMAssyComponent bomassy = (BOMAssyComponent) allListNode.getBOMComponent();

	    if ((bomassy.getNewFlagStr()).equals("NEW")) {
		itemCodeUpStr = bomassy.toString();

		// 구성중인 NEW BOM 비교를 위해 vector에 넣는다.
		itemCodeUpVector.addElement(allListNode.getChildren());
		substituteUpVector = bomassy.getSubAssyComponent();

		if (itemCodeDnStr.equals(itemCodeUpStr)) {
		    if (itemCodeDnVector.size() != itemCodeUpVector.size()) {
			Vector v = new Vector();
			v.add(bomassy.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			v.add(" " + (bomassy.getParentItemCodeStr() == null ? "" : bomassy.getParentItemCodeStr().trim()));
			v.add(" " + bomassy.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00283")/* 자부품코드가 다릅니다. */);
			resultVector.add(v);
			errorCount++;
			setResultList();
		    } else {
			StringBuffer upSb = new StringBuffer();
			StringBuffer dnSb = new StringBuffer();
			for (int i = 0; i < itemCodeDnVector.size(); i++) {
			    upSb.append(itemCodeDnVector.elementAt(i));
			}

			for (int i = 0; i < itemCodeDnVector.size(); i++) {
			    dnSb.append(itemCodeUpVector.elementAt(i));
			}

			if (!upSb.equals(dnSb)) {
			    Vector v = new Vector();
			    v.add(bomassy.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + (bomassy.getParentItemCodeStr() == null ? "" : bomassy.getParentItemCodeStr().trim()));
			    v.add(" " + bomassy.toString());
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00283")/* 자부품코드가 다릅니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
			upSb.delete(0, upSb.length() - 1);
			dnSb.delete(0, dnSb.length() - 1);

			if (!upSb.equals(dnSb)) {
			    Vector v = new Vector();
			    v.add(bomassy.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + (bomassy.getParentItemCodeStr() == null ? "" : bomassy.getParentItemCodeStr().trim()));
			    v.add(" " + bomassy.toString());
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00283")/* 자부품코드가 다릅니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
			upSb.delete(0, upSb.length() - 1);
			dnSb.delete(0, dnSb.length() - 1);

			for (int i = 0; i < substituteDnVector.size(); i++) {
			    upSb.append(substituteDnVector.elementAt(i));
			    break;
			}

			for (int i = 0; i < substituteUpVector.size(); i++) {
			    dnSb.append(substituteUpVector.elementAt(i));
			    break;
			}

			if (!upSb.equals(dnSb)) {
			    Vector v = new Vector();
			    v.add(bomassy.getSeqInt().toString() + " ");
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
			    v.add(" " + (bomassy.getParentItemCodeStr() == null ? "" : bomassy.getParentItemCodeStr().trim()));
			    v.add(" " + bomassy.toString());
			    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00137")/* 대체부품코드가 다릅니다. */);
			    resultVector.add(v);
			    errorCount++;
			    setResultList();
			}
			upSb.delete(0, upSb.length() - 1);
			dnSb.delete(0, dnSb.length() - 1);
		    }
		} else {
		    itemCodeUpVector.removeAllElements();
		}
		itemCodeDnStr = itemCodeUpStr;
		itemCodeDnVector = itemCodeUpVector;
		substituteDnVector = substituteUpVector;
	    }

	    double m1 = (double) ((double) count / (double) totalDataCount);
	    progress = (int) (m1 * 100);
	    setProgress(progress);
	    count++;
	}
    }

    // 7 Stage : 신규 이관하려는 BOM 구조가 이미 BOMMaster에 적용되었는지 체크
    private void checkExistBOMStructure(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk7.isSelected())
	    return;

	int progress = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	Vector resultList = new Vector();
	Vector nodeVec = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(appReg.getString("plm"));

	    if (new BOMSearchDao().isModelCheck(connection, modelName)) {
		Vector v = new Vector();
		v.add("0 ");
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		v.add("");
		v.add(" " + modelName);
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00033")/* BOM마스터에 이미 존재하는 부품코드입니다. */);
		resultVector.add(v);
		errorCount++;
	    }

	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

		if (bomcomponent.getNewFlagStr().equalsIgnoreCase("NEW")) {
		    nodeVec.add(bomcomponent.getItemCodeStr() == null ? "" : bomcomponent.getItemCodeStr().trim());
		}
	    }

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.IsExistInERP(connection, nodeVec);

	    Vector itemVec = dao.getResultListVec();

	    while (enum1.hasMoreElements()) {
		BOMTreeNode selectedNode = (BOMTreeNode) enum1.nextElement();
		BOMAssyComponent cmp = selectedNode.getBOMComponent();

		if (selectedNode.getParent() == null) {
		    continue;
		}

		BOMAssyComponent parentCmp = ((BOMTreeNode) selectedNode.getParent()).getBOMComponent();

		int indx = itemVec.indexOf(cmp.getItemCodeStr().trim());

		if (indx > -1) {
		    resultList.addElement(parentCmp.getItemCodeStr().trim() + "|" + cmp.getItemCodeStr().trim() + "^" + cmp.getSeqInt());
		}
	    }

	    if (resultList.size() > 0) {
		for (int i = 0; i < resultList.size(); i++) {
		    Vector v = new Vector();

		    v.add(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1) + " ");
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		    v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
		    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00026")/* BOM 마스터에 존재하는 BOM구조입니다. */);
		    resultVector.add(v);
		    errorCount++;
		    setResultList();

		    double m1 = (double) ((double) i / (double) resultList.size());
		    progress = (int) (m1 * 100);
		    setProgress(progress);
		}
	    }
	} catch (SQLException sqle) {
	    loadWarningMessage();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    loadWarningMessage("From 7 Stage error : " + e.toString());
	} finally {
	    if (resource != null)
		resource.freeConnection(appReg.getString("plm"), connection);
	}

	if (resultList.size() > 0) {
	    for (int i = 0; i < resultList.size(); i++) {
		Vector v = new Vector();
		v.add(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1) + " ");
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
		v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00045")/* ERP에 이미 존재하는 부품코드입니다. */);
		resultVector.add(v);
		errorCount++;
		setResultList();

		double m1 = (double) ((double) i / (double) resultList.size());
		progress = (int) (m1 * 100);
		setProgress(progress);
	    }
	}
    }

    // 8 Stage : Item 중에서 ERP로 I/F 되지 않은 신규 Item 존재 여부 체크
    private void checkIsExistItemMaster(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk8.isSelected())
	    return;

	int progress = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	Vector nodeVec = new Vector();
	Vector subCompsItemCodeVec = new Vector();
	Vector resultList = new Vector();
	Vector substituteVec = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(appReg.getString("plm"));

	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

		nodeVec.add(bomcomponent.getItemCodeStr() == null ? "" : bomcomponent.getItemCodeStr().trim());

		subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
		String substituteItemCodeStr = "";

		for (int inx = 0; inx < subCompsItemCodeVec.size(); inx++) {
		    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) subCompsItemCodeVec.elementAt(inx);
		    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

		    nodeVec.add(substituteItemCodeStr);
		}
	    }

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.IsExistItemMaster(connection, nodeVec);

	    // shin.....기존의 것은 IsExistItemMaster에서 ERP에 안넘어간 값을 가져오는것.. ket 에선 다 넘어갔다고 보구 넘어간 내용가져옴., 아래 처리 변경.
	    Vector itemVec = dao.getResultListVec();

	    while (enum1.hasMoreElements()) {
		BOMTreeNode selectedNode = (BOMTreeNode) enum1.nextElement();
		BOMAssyComponent cmp = selectedNode.getBOMComponent();

		substituteVec = cmp.getSubAssyComponent();
		String substituteItemCodeStr = "";

		if (!cmp.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
		    BOMAssyComponent parentCmp = ((BOMTreeNode) selectedNode.getParent()).getBOMComponent();
		    if ((parentCmp.getNewFlagStr() == null ? "" : parentCmp.getNewFlagStr()).equals("NEW")) {
			if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
			    resultList.addElement(parentCmp.getItemCodeStr() + "|" + cmp.getItemCodeStr().trim() + "^" + cmp.getSeqInt() + "#Comps");
			}

			for (int inx = 0; inx < substituteVec.size(); inx++) {
			    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) substituteVec.elementAt(inx);
			    substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

			    if (itemVec.indexOf(substituteItemCodeStr) == -1) {
				resultList.addElement(parentCmp.getItemCodeStr() + "|" + substituteItemCodeStr + "^" + cmp.getSeqInt() + "#SubComps");
			    }
			}
		    }
		} else {
		    if (modelName.equals(cmp.getItemCodeStr())) {
			if (itemVec.indexOf(cmp.getItemCodeStr().trim()) == -1) {
			    resultList.addElement("|" + cmp.getItemCodeStr().trim() + "^0" + "#Comps");
			}
		    }
		}
	    }
	    // Kogger.debug(getClass(), "########### resultList : "+resultList);
	} catch (SQLException sqle) {
	    Kogger.error(getClass(), sqle);
	    loadWarningMessage();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    loadWarningMessage("From 8 Stage error : " + e.toString());
	} finally {
	    if (resource != null)
		resource.freeConnection(appReg.getString("plm"), connection);
	}

	if (resultList.size() > 0) {
	    for (int i = 0; i < resultList.size(); i++) {
		Vector v = new Vector();
		v.add(String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1, resultList.elementAt(i).toString().indexOf("#")) + " "));
		v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
		v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
		v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));

		if (String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("#") + 1, resultList.elementAt(i).toString().length())).equals("Comps")) {
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00182")/* 부품마스터에 부품코드가 존재하지 않습니다. */);
		} else {
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00181")/* 부품마스터에 대체부품코드가 존재하지 않습니다.. */);
		}
		// 보정결과를 출력하게 될 vector 에 결과를 Add 한다.
		resultVector.add(v);
		warningCount++;
		setResultList();

		double m1 = (double) ((double) i / (double) resultList.size());
		progress = (int) (m1 * 100);
		setProgress(progress);
	    }
	}
    }

    // 9 Stage : Item 이 Check-In 상태인지 체크
    private void checkCheckInItem(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk9.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	Vector nodeVec = new Vector();
	Vector subCompsItemCodeVec = new Vector();
	Vector substituteVec = new Vector();
	Vector coworkerVec = new Vector();
	Vector resultList = new Vector();

	try {
	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

		if (!bomcomponent.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
		    BOMAssyComponent bomparent = ((BOMTreeNode) allListNode.getParent()).getBOMComponent();

		    if ((bomparent.getNewFlagStr() == null ? "" : bomparent.getNewFlagStr().trim()).equals("NEW")) {
			nodeVec.add((bomcomponent.getItemCodeStr() == null ? "" : bomcomponent.getItemCodeStr().trim()));

			subCompsItemCodeVec = bomcomponent.getSubAssyComponent();
			String substituteItemCodeStr = "";

			for (int inx = 0; inx < subCompsItemCodeVec.size(); inx++) {
			    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) subCompsItemCodeVec.elementAt(inx);
			    substituteItemCodeStr = (subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim());

			    nodeVec.add(substituteItemCodeStr);
			}
		    }
		} else {
		    nodeVec.add(BOMBasicInfoPool.getPublicModelName().trim());
		}
	    }

	    Kogger.debug(getClass(), "=========>> nodeVec : " + nodeVec.toString());

	    coworkerVec = KETBomHelper.service.getCheckOuter(nodeVec);

	    while (enum1.hasMoreElements()) {
		BOMTreeNode selectedNode = (BOMTreeNode) enum1.nextElement();
		BOMAssyComponent cmp = selectedNode.getBOMComponent();

		substituteVec = cmp.getSubAssyComponent();
		String substituteItemCodeStr = "";

		for (int i = 0; i < coworkerVec.size(); i++) {
		    if (!cmp.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
			BOMAssyComponent parentCmp = ((BOMTreeNode) selectedNode.getParent()).getBOMComponent();
			if ((parentCmp.getNewFlagStr() == null ? "" : parentCmp.getNewFlagStr()).equals("NEW")) {
			    if ((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(3, coworkerVec.elementAt(i).toString().trim().indexOf("|"))).equals(cmp
				    .getItemCodeStr().trim())) {
				resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim()
				        .substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1))
				        + "$" + parentCmp.getItemCodeStr() + "|" + cmp.getItemCodeStr().trim() + "^" + cmp.getSeqInt() + "#Comps");
			    }

			    for (int inx = 0; inx < substituteVec.size(); inx++) {
				BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) substituteVec.elementAt(inx);
				substituteItemCodeStr = subAssyComponent.getSubstituteItemCodeStr() == null ? "" : subAssyComponent.getSubstituteItemCodeStr().toString().trim();

				if ((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(3, coworkerVec.elementAt(i).toString().trim().indexOf("|")))
				        .equals(substituteItemCodeStr)) {
				    resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim()
					    .substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1))
					    + "$" + parentCmp.getItemCodeStr() + "|" + substituteItemCodeStr + "^" + cmp.getSeqInt() + "#SubComps");
				}
			    }
			}
		    } else {
			if (modelName.equals(cmp.getItemCodeStr())) {
			    if ((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim().substring(3, coworkerVec.elementAt(i).toString().trim().indexOf("|"))).equals(cmp
				    .getItemCodeStr().trim())) {
				resultList.addElement((coworkerVec.elementAt(i) == null ? "" : coworkerVec.elementAt(i).toString().trim()
				        .substring(coworkerVec.elementAt(i).toString().trim().indexOf("|") + 1))
				        + "$" + "|" + cmp.getItemCodeStr().trim() + "^0" + "#Comps");
			    }
			}
		    }
		}
	    }

	    if (resultList.size() > 0) {
		for (int i = 0; i < resultList.size(); i++) {
		    Vector v = new Vector();
		    v.add(String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1, resultList.elementAt(i).toString().indexOf("#")) + " "));
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
		    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("$") + 1, resultList.elementAt(i).toString().indexOf("|")));
		    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));

		    if (String.valueOf(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("#") + 1, resultList.elementAt(i).toString().length())).equals("Comps")) {
			v.add(" "
			        + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00197",
			                resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("$")))/* 부품코드가 {0}에 의해 체크아웃 상태입니다. */);
		    } else {
			v.add(" "
			        + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00135",
			                resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("$")))/* 대체부품코드가 {0}에 의해 체크아웃 상태입니다. */);
		    }
		    // 보정결과를 출력하게 될 vector에 결과를 Add 한다.
		    resultVector.add(v);
		    errorCount++;
		    setResultList();

		    double m1 = (double) ((double) i / (double) resultList.size());
		    progress = (int) (m1 * 100);
		    setProgress(progress);
		}
	    }
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	    loadWarningMessage("From 9 Stage error : " + ex.toString());
	}
    }

    // 10 Stage : Item 에 연결된 도면/문서가 Check-In 상태인지 체크
    private void checkCheckInDoc(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk10.isSelected())
	    return;
    }

    // 11 Stage : Item 에 연결된 비철, 수지 원재료 코드 밑에 올바른 스트랩 코드가 달려있는지 체크
    private void checkGenItem(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk11.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	String model = (rootNode.getBOMComponent()).toString();

	int lev = 0;
	String firstItemFull = "";
	String firstItem = "";
	BOMAssyComponent bomcomponent = null;
	while (enum0.hasMoreElements()) {
	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

	    if (!bomcomponent.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
		BOMAssyComponent bomparent = ((BOMTreeNode) allListNode.getParent()).getBOMComponent();

		if (firstItem.equals("R10")) {
		    String nexItem = bomcomponent.getItemCodeStr().trim();
		    nexItem = nexItem.substring(0, 3);
		    if (!nexItem.equals("S18")) {
			Vector v = new Vector();
			v.add(bomcomponent.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
			v.add(" " + bomcomponent.getParentItemCodeStr());
			v.add(" " + bomcomponent.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00207")/* 비철 원재료코드 아래의 스트랩 코드가 기준과 다릅니다. */);
			resultVector.add(v);
			warningCount++;
			setResultList();
		    }
		} else if (firstItem.equals("R20")) {
		    String nexItem = bomcomponent.getItemCodeStr().trim();
		    String chkItem = "S" + firstItemFull.substring(1, firstItemFull.length());
		    if (!nexItem.equals(chkItem)) {
			Vector v = new Vector();
			v.add(bomcomponent.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
			v.add(" " + bomcomponent.getParentItemCodeStr());
			v.add(" " + bomcomponent.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00246")/* 수지 원재료코드 아래의 스트랩 코드가 기준과 다릅니다. */);
			resultVector.add(v);
			warningCount++;
			setResultList();
		    }
		}

		lev = bomcomponent.getLevelInt().intValue();
		firstItemFull = bomcomponent.getItemCodeStr().trim();
		try {
		    firstItem = firstItemFull.substring(0, 3);
		} catch (Exception e) {
		    firstItem = firstItemFull;
		}
		// Kogger.debug(getClass(), "---------------------------------------->>>> itemconde : "+bomcomponent.getItemCodeStr().trim());
	    }
	    /*
	     * else //head... { Kogger.debug(getClass(), "---------------------------------------->>>> else : "+BOMBasicInfoPool.getPublicModelName().trim());
	     * //nodeVec.add(BOMBasicInfoPool.getPublicModelName().trim()); }
	     */
	}
	// 마지막 부품이 체크할 부품으로 나올때 처리..
	if (firstItem.equals("R10")) {
	    Vector v = new Vector();
	    v.add(bomcomponent.getSeqInt().toString() + " ");
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
	    v.add(" " + bomcomponent.getParentItemCodeStr());
	    v.add(" " + bomcomponent.toString());
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00207")/* 비철 원재료코드 아래의 스트랩 코드가 기준과 다릅니다. */);
	    resultVector.add(v);
	    warningCount++;
	    setResultList();
	} else if (firstItem.equals("R20")) {
	    Vector v = new Vector();
	    v.add(bomcomponent.getSeqInt().toString() + " ");
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
	    v.add(" " + bomcomponent.getParentItemCodeStr());
	    v.add(" " + bomcomponent.toString());
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00246")/* 수지 원재료코드 아래의 스트랩 코드가 기준과 다릅니다. */);
	    resultVector.add(v);
	    warningCount++;
	    setResultList();
	}
    }

    // 12 Stage : 터미널 반제품일때 자부품에 후도금제품코드가 존재하는지 체크.
    private void checkAfterPlating(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk12.isSelected())
	    return;

	int progress = 0;
	int count = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	String model = (rootNode.getBOMComponent()).toString();

	int lev = 0;
	String firstItemFull = "";
	String firstItem = "";
	BOMAssyComponent bomcomponent = null;
	while (enum0.hasMoreElements()) {
	    BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
	    bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

	    if (!bomcomponent.getItemCodeStr().trim().equalsIgnoreCase(modelName)) {
		BOMAssyComponent bomparent = ((BOMTreeNode) allListNode.getParent()).getBOMComponent();

		if ((firstItem.equals("H71") || firstItem.equals("H72") || firstItem.equals("H73") || firstItem.equals("H74") || firstItem.equals("H75") || firstItem.equals("H76")
		        || firstItem.equals("H78") || firstItem.equals("H79"))
		        && firstItemFull.indexOf("-2") > 0 && firstItemFull.indexOf("A-2") == -1) {
		    String nexItem = bomcomponent.getItemCodeStr().trim();
		    String chkItem = firstItemFull.replace("-2", "A-2");
		    if (!nexItem.equals(chkItem)) {
			Vector v = new Vector();
			v.add(bomcomponent.getSeqInt().toString() + " ");
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
			v.add(" " + bomcomponent.getParentItemCodeStr());
			v.add(" " + bomcomponent.toString());
			v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00344")/* 터미널 반제품코드의 자부품이 후도금 제품코드가 아닙니다. */);
			resultVector.add(v);
			warningCount++;
			setResultList();
		    }
		}

		lev = bomcomponent.getLevelInt().intValue();
		firstItemFull = bomcomponent.getItemCodeStr().trim();
		try {
		    firstItem = firstItemFull.substring(0, 3);
		} catch (Exception e) {
		    firstItem = firstItemFull;
		}
		// Kogger.debug(getClass(), "---------------------------------------->>>> itemconde : "+bomcomponent.getItemCodeStr().trim());
	    }
	    /*
	     * else //head... { Kogger.debug(getClass(), "---------------------------------------->>>> else : "+BOMBasicInfoPool.getPublicModelName().trim());
	     * //nodeVec.add(BOMBasicInfoPool.getPublicModelName().trim()); }
	     */
	}
	// 마지막 부품이 체크할 부품으로 나올때 처리..
	if ((firstItem.equals("H71") || firstItem.equals("H72") || firstItem.equals("H73") || firstItem.equals("H74") || firstItem.equals("H75") || firstItem.equals("H76") || firstItem.equals("H78") || firstItem
	        .equals("H79")) && firstItemFull.indexOf("-2") > 0 && firstItemFull.indexOf("A-2") == -1) {
	    Vector v = new Vector();
	    v.add(bomcomponent.getSeqInt().toString() + " ");
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
	    v.add(" " + bomcomponent.getParentItemCodeStr());
	    v.add(" " + bomcomponent.toString());
	    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00344")/* 터미널 반제품코드의 자부품이 후도금 제품코드가 아닙니다. */);
	    resultVector.add(v);
	    warningCount++;
	    setResultList();
	}
    }

    // 13 Stage : 삭제된 부품이 존재하는지 체크
    private void checkObsoletedItem(BOMTreeNode rootNode, int totalDataCount) {
	if (!validationChk13.isSelected())
	    return;

	int progress = 0;
	Enumeration enum0 = rootNode.preorderEnumeration();
	Enumeration enum1 = rootNode.preorderEnumeration();
	Vector nodeVec = new Vector();
	Vector resultList = new Vector();

	try {
	    resource = DBConnectionManager.getInstance();
	    connection = resource.getConnection(appReg.getString("plm"));

	    while (enum0.hasMoreElements()) {
		BOMTreeNode allListNode = (BOMTreeNode) enum0.nextElement();
		BOMAssyComponent bomcomponent = (BOMAssyComponent) allListNode.getBOMComponent();

		if (allListNode.getParent() == null) {
		    continue;
		}

		BOMTreeNode parentNode = (BOMTreeNode) allListNode.getParent();
		BOMAssyComponent bomparent = (BOMAssyComponent) parentNode.getBOMComponent();

		if (bomparent.getNewFlagStr().equalsIgnoreCase("NEW")) {
		    nodeVec.add(bomcomponent.getItemCodeStr() == null ? "" : bomcomponent.getItemCodeStr().trim());
		}
	    }

	    BOMSearchDao dao = new BOMSearchDao();
	    dao.IsObsoletedItem(connection, nodeVec);

	    Vector itemVec = dao.getResultListVec();
	    Vector statusVec = dao.getResultListVec2();

	    while (enum1.hasMoreElements()) {
		BOMTreeNode selectedNode = (BOMTreeNode) enum1.nextElement();
		BOMAssyComponent cmp = selectedNode.getBOMComponent();

		if (selectedNode.getParent() == null) {
		    continue;
		}

		BOMAssyComponent parentCmp = ((BOMTreeNode) selectedNode.getParent()).getBOMComponent();

		int indx = itemVec.indexOf(cmp.getItemCodeStr().trim());

		if (indx > -1) {
		    if (statusVec.get(indx).toString().equalsIgnoreCase("Y")) // 부품 속성 isDeleted가 'Y'이면 삭제된 상태임
		    {
			resultList.addElement(parentCmp.getItemCodeStr().trim() + "|" + cmp.getItemCodeStr().trim() + "^" + cmp.getSeqInt());
		    }
		}
	    }

	    if (resultList.size() > 0) {
		for (int i = 0; i < resultList.size(); i++) {
		    Vector v = new Vector();

		    v.add(resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("^") + 1) + " ");
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00266")/* 오류 */);
		    v.add(" " + resultList.elementAt(i).toString().substring(0, resultList.elementAt(i).toString().indexOf("|")));
		    v.add(" " + resultList.elementAt(i).toString().substring(resultList.elementAt(i).toString().indexOf("|") + 1, resultList.elementAt(i).toString().indexOf("^")));
		    v.add(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00214")/* 삭제된 부품입니다. */);
		    resultVector.add(v);
		    errorCount++;
		    setResultList();

		    double m1 = (double) ((double) i / (double) resultList.size());
		    progress = (int) (m1 * 100);
		    setProgress(progress);
		}
	    }
	} catch (SQLException sqle) {
	    Kogger.error(getClass(), sqle);
	    loadWarningMessage();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    loadWarningMessage("From 13 Stage error : " + e.toString());
	} finally {
	    if (resource != null)
		resource.freeConnection(appReg.getString("plm"), connection);
	}
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
	errorLbl.setText(String.valueOf(errorCount) + " " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00263")/* 에러 */+ ", ");
	warningLbl.setText(String.valueOf(warningCount) + " " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00080")/* 경고 */);
    }

    private void loadWarningMessage() {
	String message = messageRegistry.getString("valid7");

	MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
	messagebox.setVisible(true);
	messagebox.setModal(true);

	dispose();
	stopOperation();
    }

    private void loadWarningMessage(String message) {
	MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
	messagebox.setVisible(true);
	messagebox.setModal(true);
    }

    private void loadWarningMessage(JFrame jFrame, String message) {
	MessageBox messagebox = new MessageBox(jFrame, message, "Warning", MessageBox.WARNING);
	messagebox.setVisible(true);
	messagebox.setModal(true);
    }
}
