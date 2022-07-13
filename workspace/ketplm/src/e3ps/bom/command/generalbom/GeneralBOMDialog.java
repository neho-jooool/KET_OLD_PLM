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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.managecoworker.FindUserDialog;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.command.updatebom.BOMChildItemSearchDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.Utility;
import e3ps.bom.common.util.ValidationChecker;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.InterfaceAIFOperationListener;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Painter;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

public class GeneralBOMDialog extends AbstractAIFDialog implements InterfaceAIFOperationListener {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JTextField gItemCodeTfl = null; // 부품번호
    private JTextField gItemNmTfl = null; // 부품명
    private JTextField cStatusKr = null; // 결재상태한글
    private JTextField cStatus = null; // 결재상태영문
    private JTextField gQuantity = null; // 기준수량
    private JTextField bQuantity = null; // 포장수량
    private JTextField gUnitTfl = null; // 단위
    private JTextField gViewUnitTfl = null; // 단위 view

    private JTextField cNameTfl = null; // 공동작업자명
    private JTextField cDeptTfl = null; // 공동작업자부서
    // private JTextArea comment;

    private JButton gSearchBtn = null;
    private JButton AddBtn = null;
    private JButton removeBtn = null;
    private JButton createBOMBtn = null;
    private JButton cancelBtn = null;

    private JTable coworkerTbl = null;

    boolean keyCheck = false;
    private BtnListener btnListener;

    private JFrame frmDesktop;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel pnl;
    private BOMRegisterDesktop desktop;
    Registry appReg;

    CoworkerTableData coworkerData;

    private String aryOwnerData[] = new String[4];
    private String aryCoworkerData[] = new String[4];

    int coworkerTblRowCnt = 0;

    boolean isCoworkerTblSelected = false;
    boolean isOwnerData = false;
    boolean isAdmin = false;

    private BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

    class BtnListener implements ActionListener, KeyListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("GeneralBOMSearch")) {
		generalBOMSearch();
	    }

	    if (e.getActionCommand().equals("Add")) {
		coworkerAdd();
	    }

	    if (e.getActionCommand().equals("Remove")) {
		coworkerRemove();
	    }

	    if (e.getActionCommand().equals("CreateBOM")) {
		if (createBOM()) {
		    cancelBOM();
		    frmDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		} else {
		    frmDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	    }

	    if (e.getActionCommand().equals("Cancel")) {
		cancelBOM();
	    }
	};

	boolean keyCheck = false;

	public void keyPressed(KeyEvent e) {
	};

	public void keyReleased(KeyEvent e) {
	    if (keyCheck) {
		JTextField tfl = (JTextField) e.getSource();
		int pos = tfl.getCaretPosition();

		// String s = tfl.getText();
		// tfl.setText(s.toUpperCase());
		tfl.setCaretPosition(pos);
		keyCheck = false;
	    }
	};

	public void keyTyped(KeyEvent e) {
	    keyCheck = false;

	    char c = e.getKeyChar();
	    int cCode = (int) c;

	    if (cCode >= 97 && cCode <= 122) {
		keyCheck = true;
	    }
	};
    }

    public GeneralBOMDialog() {
    }

    public GeneralBOMDialog(JFrame frame, AbstractAIFUIApplication app) {
	super(frame, true);

	initialize(frame, app);
    }

    private void initialize(JFrame frame, AbstractAIFUIApplication app) {
	try {
	    frmDesktop = frame;
	    this.app = app;
	    appReg = Registry.getRegistry(app);
	    desktop = (BOMRegisterDesktop) frame;
	    pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();

	    setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00277")/* 일반 BOM */);
	    setResizable(false);

	    coworkerData = new CoworkerTableData();

	    setUserData();

	    setCoworkerData();

	    setContentInit();

	    gItemCodeTfl.requestFocus();

	    run();
	} catch (Throwable ex) {
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

	    // 첫번째 검색 라인
	    JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel gItemCodeLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/* 부품번호 */+ " : ");
	    gItemCodeLbl.setFont(FontList.defaultFont);
	    panel01.add(gItemCodeLbl);

	    gItemCodeTfl = new JTextField("", 15) {
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
		    super.paint(g);
		    Painter.paintIsRequired(this, g);
		}
	    };
	    gItemCodeTfl.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    generalBOMSearch();
		}
	    });
	    gItemCodeTfl.setFont(FontList.defaultFont);
	    gItemCodeTfl.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent keyevent) {
		    char c = keyevent.getKeyChar();
		    int cCode = (int) c;

		    if (cCode >= 97 && cCode <= 122) {
			keyCheck = true;
		    }
		}

		public void keyReleased(KeyEvent keyevent) {
		    if (keyCheck) {
			JTextField tfl = (JTextField) keyevent.getSource();
			int pos = tfl.getCaretPosition();

			// String s = tfl.getText();
			// tfl.setText(s.toUpperCase());
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
	    gSearchBtn.setMargin(new Insets(0, 5, 0, 5));
	    panel01.add(gSearchBtn);

	    generalPanel.add(panel01);

	    // 두번째 검색 라인
	    JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel gDescLbl = new JLabel("             " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/* 부품명 */+ " : ");
	    gDescLbl.setFont(FontList.defaultFont);
	    gDescLbl.setEnabled(false);
	    panel02.add(gDescLbl);

	    gItemNmTfl = new JTextField("", 35);
	    gItemNmTfl.setEnabled(false);
	    gItemNmTfl.setFont(FontList.defaultFont);
	    panel02.add(gItemNmTfl);

	    generalPanel.add(panel02);

	    // shin...
	    // 세번째 검색 라인
	    JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel gUomLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/* 결재상태 */+ " : ");
	    gUomLbl.setFont(FontList.defaultFont);
	    gUomLbl.setEnabled(false);
	    panel03.add(gUomLbl);

	    cStatusKr = new JTextField(35);
	    cStatusKr.setEnabled(false);
	    cStatusKr.setFont(FontList.defaultFont);

	    cStatus = new JTextField(35);
	    cStatus.setVisible(false);

	    panel03.add(cStatusKr);
	    panel03.add(cStatus);

	    generalPanel.add(panel03);

	    // 네번째 검색 라인
	    JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel gUitLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00122")/* 기준수량 */+ " : ");
	    gUitLbl.setFont(FontList.defaultFont);
	    gUitLbl.setEnabled(false);
	    panel04.add(gUitLbl);

	    gQuantity = new JTextField(35);
	    gQuantity.setEnabled(false);
	    gQuantity.setFont(FontList.defaultFont);
	    panel04.add(gQuantity);

	    generalPanel.add(panel04);

	    // 다섯번째 검색 라인
	    JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel gStatus = new JLabel("                " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/* 단위 */+ " : ");
	    gStatus.setFont(FontList.defaultFont);
	    gStatus.setEnabled(true);
	    panel05.add(gStatus);

	    gUnitTfl = new JTextField(35);
	    gUnitTfl.setVisible(false);

	    gViewUnitTfl = new JTextField(35);
	    // { private static final long serialVersionUID = 1L;
	    //
	    // public void paint(Graphics g) {
	    // super.paint(g);
	    // Painter.paintIsRequired(this, g);
	    // }
	    // };
	    gViewUnitTfl.setEnabled(false);
	    gViewUnitTfl.setFont(FontList.defaultFont);
	    panel05.add(gViewUnitTfl);
	    panel05.add(gUnitTfl);

	    generalPanel.add(panel05);

	    // 여섯번째 검색 라인
	    JPanel panel06 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel bQtyLbl = new JLabel("          " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00345")/* 포장수량 */+ " : ");
	    bQtyLbl.setFont(FontList.defaultFont);
	    panel06.add(bQtyLbl);

	    bQuantity = new JTextField(35);
	    bQuantity.setFont(FontList.defaultFont);
	    panel06.add(bQuantity);

	    generalPanel.add(panel06);

	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
	    topPanel.add(generalPanel);

	    this.getContentPane().add(topPanel, BorderLayout.NORTH);

	    // Co-Worker Info Panel /////////////////////////////////////////////////
	    JPanel coworkerPanel = new JPanel();
	    coworkerPanel.setLayout(new BoxLayout(coworkerPanel, BoxLayout.Y_AXIS));
	    coworkerPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " 공동 작업자", 0, 0, FontList.defaultFont));
	    coworkerPanel.setFont(FontList.defaultFont);
	    coworkerPanel.setPreferredSize(new Dimension(400, 180));

	    JPanel panel08 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JLabel cNameLbl = new JLabel("    " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/* 이름 */+ " : ");
	    cNameLbl.setFont(FontList.defaultFont);
	    panel08.add(cNameLbl);

	    cNameTfl = new JTextField("", 14);
	    cNameTfl.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    coworkerAdd();
		}
	    });
	    cNameTfl.setFont(FontList.defaultFont);
	    panel08.add(cNameTfl);

	    JLabel cDeptLbl = new JLabel("    " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/* 부서 */+ " : ");
	    cDeptLbl.setFont(FontList.defaultFont);
	    panel08.add(cDeptLbl);

	    cDeptTfl = new JTextField("", 14);
	    cDeptTfl.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    coworkerAdd();
		}
	    });
	    cDeptTfl.setFont(FontList.defaultFont);
	    panel08.add(cDeptTfl);

	    AddBtn = new JButton(appReg.getImageIcon("managecoworkerIcon"));
	    AddBtn.setActionCommand("Add");
	    AddBtn.setToolTipText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00089")/* 공동작업자 검색 */);
	    AddBtn.setBorder(BorderList.emptyBorder1);
	    AddBtn.addActionListener(btnListener);
	    AddBtn.setMargin(new Insets(0, 5, 0, 5));
	    panel08.add(AddBtn);

	    removeBtn = new JButton(appReg.getImageIcon("userremoveIcon"));
	    removeBtn.setActionCommand("Remove");
	    removeBtn.setToolTipText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00090")/* 공동작업자 삭제 */);
	    removeBtn.setBorder(BorderList.emptyBorder1);
	    removeBtn.addActionListener(btnListener);
	    removeBtn.setMargin(new Insets(0, 5, 0, 5));
	    panel08.add(removeBtn);

	    coworkerPanel.add(panel08);

	    // Search Result Panel /////////////////////////////////////////////////
	    coworkerTbl = new JTable();
	    Dimension dimCoWorkerTable = new Dimension(200, 300);
	    coworkerTbl.setAutoCreateColumnsFromModel(false);
	    coworkerTbl.setPreferredScrollableViewportSize(dimCoWorkerTable);

	    coworkerTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    coworkerTbl.setModel(coworkerData);

	    for (int k = 0; k < CoworkerTableData.clmWorkerData.length; k++) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(CoworkerTableData.clmWorkerData[k].intAlignment);
		TableColumn column = new TableColumn(k, CoworkerTableData.clmWorkerData[k].intWidth, renderer, null);
		coworkerTbl.addColumn(column);
	    }

	    JScrollPane ps = new JScrollPane();
	    ps.setColumnHeaderView(coworkerTbl.getTableHeader());
	    ps.getViewport().add(coworkerTbl);

	    coworkerPanel.add(ps);

	    coworkerTbl.addMouseListener(new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
		    isCoworkerTblSelected = true;
		}
	    });

	    TableColumnModel columnModel = coworkerTbl.getColumnModel();
	    TableColumn column = columnModel.getColumn(0);
	    column.setWidth(0);
	    column.setMinWidth(0);
	    column.setMaxWidth(0);
	    column.setResizable(false);

	    this.getContentPane().add(coworkerPanel, BorderLayout.CENTER);

	    // Button Panel /////////////////////////////////////////////////
	    JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	    createBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00306")/* 저장 */, appReg.getImageIcon("createbomIcon"));
	    createBOMBtn.setFont(FontList.defaultFont);
	    createBOMBtn.setActionCommand("CreateBOM");
	    createBOMBtn.addActionListener(btnListener);
	    createBOMBtn.setMargin(new Insets(0, 5, 0, 5));
	    btnFlowPanel.add(createBOMBtn);

	    cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/* 닫기 */, appReg.getImageIcon("closeIcon"));
	    cancelBtn.setFont(FontList.defaultFont);
	    cancelBtn.setActionCommand("Cancel");
	    cancelBtn.addActionListener(btnListener);
	    cancelBtn.setMargin(new Insets(0, 5, 0, 5));
	    btnFlowPanel.add(cancelBtn);

	    this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    private void setUserData() {
	try {
	    aryOwnerData[0] = Utility.checkNVL(BOMBasicInfoPool.getUserId());
	    aryOwnerData[1] = Utility.checkNVL(BOMBasicInfoPool.getUserName());
	    aryOwnerData[2] = Utility.checkNVL(BOMBasicInfoPool.getUserEMail());
	    aryOwnerData[3] = Utility.checkNVL(BOMBasicInfoPool.getUserDept());

	    if (BOMBasicInfoPool.isAdmin()) {
		isAdmin = true;
	    } else {
		isAdmin = false;
	    }

	    isOwnerData = true;
	} catch (Exception e) {
	    isOwnerData = false;
	    Kogger.error(getClass(), e);
	}
    }

    private void setCoworkerData() {
	try {
	    coworkerData.vecWorkerData.removeAllElements();

	    if (isOwnerData == true) {
		coworkerData.vecWorkerData.addElement(new CoworkerData(aryOwnerData[0], aryOwnerData[1], aryOwnerData[2], aryOwnerData[3]));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    MessageBox mbox = new MessageBox(frmDesktop, e.toString(), messageRegistry.getString("bomError"), 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	}
    }

    private void setCoworkerData(String name, String dept) {
	try {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    FindUserDialog dlgFindUser = null;

	    int coworkNameIdx0 = 0;
	    int coworkNameIdx = 0;

	    for (int i = 0; i < coworkerTbl.getColumnCount(); i++) {
		if (coworkerTbl.getColumnName(i).toString().equals("Id")) {
		    coworkNameIdx0 = i;
		}
	    }

	    for (int i = 0; i < coworkerTbl.getColumnCount(); i++) {
		if (coworkerTbl.getColumnName(i).toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/* 이름 */)) {
		    coworkNameIdx = i;
		}
	    }

	    dlgFindUser = new FindUserDialog(cNameTfl.getText(), cDeptTfl.getText(), app, "공동 작업자");

	    if (dlgFindUser.getOK() == true) {
		aryCoworkerData = dlgFindUser.getSelectedColumnData();
		int RowCnt = coworkerTbl.getRowCount();
		boolean isDuplicate = false;
		for (int i = 0; i < RowCnt; i++) {
		    String UserId = (String) coworkerTbl.getValueAt(i, coworkNameIdx0);
		    if (aryCoworkerData[0].equals(UserId)) {
			isDuplicate = true;
		    }
		}

		if (isDuplicate) {
		    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00293", aryCoworkerData[1])/* 작업자 ({0}) 는 이미 존재합니다 */,
			    "Input Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    cNameTfl.setText("");
		    cDeptTfl.setText("");
		    cNameTfl.requestFocus();
		} else {
		    int tableRowCnt = coworkerTbl.getRowCount();
		    coworkerData.vecWorkerData.add(tableRowCnt, new CoworkerData(aryCoworkerData[0], aryCoworkerData[1], aryCoworkerData[2], aryCoworkerData[3]));
		    cNameTfl.setText("");
		    cDeptTfl.setText("");
		    coworkerData.fireTableRowsInserted(0, coworkerData.getRowCount() - 1);
		    coworkerTbl.clearSelection();
		}

		aryCoworkerData[0] = "";
		aryCoworkerData[1] = "";
		aryCoworkerData[2] = "";
		aryCoworkerData[3] = "";
	    }
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	} catch (Exception e) {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    Kogger.error(getClass(), e);
	}
    }

    private void generalBOMSearch() {
	try {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    String itemCodeStr = gItemCodeTfl.getText() == null ? "" : gItemCodeTfl.getText().trim();
	    BOMChildItemSearchDialog searchItemDlg = new BOMChildItemSearchDialog(frmDesktop, app, itemCodeStr, "GeneralBOM");
	    if (searchItemDlg.getOK() == true) {
		String aryResult[] = new String[8];
		aryResult = searchItemDlg.getSelectedColumnData();
		gItemCodeTfl.setText(aryResult[0]);
		gItemNmTfl.setText(aryResult[1]);
		gItemNmTfl.requestFocus();
		cStatusKr.setText(aryResult[6]);
		// cStatus.setText(aryResult[5]);
		cStatus.setText("INWORK");
		gQuantity.setText("1");
		bQuantity.setText("1");
		gViewUnitTfl.setText(aryResult[4]);
		gUnitTfl.setText(aryResult[7]);
	    }

	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	} catch (Throwable ex) {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    Kogger.error(getClass(), ex);
	}
    }

    private void coworkerAdd() {
	try {
	    // [2011-03-10] 윤도혁J 요구사항 반영 :: 공동작업자 추가 시, 검색조건 미입력 허용
	    // ValidationChecker vc = new ValidationChecker();
	    //
	    // boolean isEmpty = false;
	    // if(vc.isEmpty(cNameTfl) && vc.isEmpty(cDeptTfl))
	    // {
	    // isEmpty = true;
	    // }
	    // else
	    // {
	    // isEmpty = false;
	    // }
	    //
	    // if(isEmpty)
	    // {
	    // MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("inputCoworkerInfo"), "Error", 0);
	    // mbox.setModal(true);
	    // mbox.setVisible(true);
	    // cNameTfl.requestFocus();
	    // coworkerTbl.clearSelection();
	    // }
	    // else
	    // {
	    setCoworkerData(cNameTfl.getText().toString().trim(), cDeptTfl.getText().toString().trim());
	    // }
	} catch (Throwable ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    private void coworkerRemove() {
	try {
	    int selRow = coworkerTbl.getSelectedRow();

	    int nameIdx = 0;
	    int emailIdx = 0;

	    for (int i = 0; i < coworkerTbl.getColumnCount(); i++) {
		if (coworkerTbl.getColumnName(i).toString().equals("Name")) {
		    nameIdx = i;
		}
		if (coworkerTbl.getColumnName(i).toString().equals("E-Mail")) {
		    emailIdx = i;
		}
	    }

	    if (isCoworkerTblSelected == false) {
		MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("selectRow11"), "Remove Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
		cNameTfl.setText("");
		cDeptTfl.setText("");
	    } else if (selRow == 0) {
		MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("coworker1"), "Remove Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
		cNameTfl.setText("");
		cDeptTfl.setText("");
	    } else {
		String rowStr = (String) coworkerTbl.getValueAt(selRow, nameIdx) + "(" + (String) coworkerTbl.getValueAt(selRow, emailIdx) + ")";

		int n = JOptionPane.showConfirmDialog(this, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00220")/* 선택된 공동작업자를 삭제하시겠습니까? */+ " " + rowStr,
		        KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00090")/* 공동작업자 삭제 */, JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
		    coworkerData.vecWorkerData.removeElementAt(selRow);
		    coworkerData.fireTableRowsDeleted(0, coworkerData.getRowCount());
		    isCoworkerTblSelected = false;
		    coworkerTbl.clearSelection();
		}
		cNameTfl.setText("");
		cDeptTfl.setText("");
	    }
	} catch (Throwable ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    private boolean createBOM() {
	try {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    String itemCodeStr = gItemCodeTfl.getText() == null ? "" : gItemCodeTfl.getText().trim();
	    boolean isItemCodeEmpty = false;
	    boolean isCoworker = false;
	    boolean isOK = false;

	    ValidationChecker vc = new ValidationChecker();
	    // General BOM 의 Product/Item Code 가 입력되었는지 체크
	    isItemCodeEmpty = vc.isEmpty(gItemCodeTfl);

	    if (isItemCodeEmpty) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("inputItem"), "Input Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
		gItemCodeTfl.requestFocus();
		return false;
	    } else {
		// General BOM 의 최상위 부품번호가 존재하는지 체크
		boolean isItemCodeAvailable = isItemCodeAvailableData(itemCodeStr);

		// modelName 이 Check-Out 상태인지 체크
		boolean isItemCodeCheckOut = isItemCodeCheckOutData(itemCodeStr);

		// Product/Item Code 가 다른 BOM 에 연결되어 있는지 체크
		// shin....사용하지 않음. false로 처리.
		boolean isItemcodeDifferentBOM = false;
		// boolean isItemcodeDifferentBOM = isItemCodeDifferentBOMData(itemCodeStr);

		if (!isItemCodeAvailable) {
		    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00189", itemCodeStr)/* 부품번호 ({0}) 는 존재하지 않습니다. */, "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);

		    gItemCodeTfl.setText("");
		    gItemNmTfl.setText("");
		    cStatusKr.setText("");
		    cStatus.setText("");
		    gQuantity.setText("");
		    gUnitTfl.setText("");
		    gViewUnitTfl.setText("");
		    bQuantity.setText("");
		    gItemCodeTfl.requestFocus();

		    return false;
		} else if (isItemCodeCheckOut) {
		    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00188", itemCodeStr)/* 부품번호 ({0}) 는 이미 체크아웃상태입니다. */, "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);

		    gItemCodeTfl.setText("");
		    gItemNmTfl.setText("");
		    cStatusKr.setText("");
		    cStatus.setText("");
		    gQuantity.setText("");
		    gUnitTfl.setText("");
		    gViewUnitTfl.setText("");
		    bQuantity.setText("");
		    gItemCodeTfl.requestFocus();

		    return false;
		} else if (isItemcodeDifferentBOM) {
		    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		    MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00185", itemCodeStr)/* 부품번호 ({0}) 는 이미 BOM에 존재합니다. */, "Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);

		    gItemCodeTfl.setText("");
		    gItemNmTfl.setText("");
		    cStatusKr.setText("");
		    cStatus.setText("");
		    gQuantity.setText("");
		    gUnitTfl.setText("");
		    gViewUnitTfl.setText("");
		    bQuantity.setText("");
		    gItemCodeTfl.requestFocus();

		    return false;
		} else {
		    int coworkerCnt = coworkerTbl.getRowCount();

		    if (coworkerCnt >= 1) {
			isCoworker = true;
			isOK = true;
		    } else {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("checkCoworker"), "Input Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return false;
		    }

		    if (isOK) {
			// [2011-02-28] 추가요구사항 처리 :: BOM 신규 생성시 ERP 에 존재여부 확인
			String strVal = KETBomHelper.service.getIsBomComponent(itemCodeStr);
			if (strVal != null && !strVal.equals("") && strVal.equals("X")) {
			    isOK = false;

			    pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			    MessageBox mbox = new MessageBox(desktop,
				    KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00186", itemCodeStr)/* 부품번호 ({0}) 는 이미 ERP에 BOM이 구성되어있습니다. */, "Error", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);

			    gItemCodeTfl.setText("");
			    gItemNmTfl.setText("");
			    cStatusKr.setText("");
			    cStatus.setText("");
			    gQuantity.setText("");
			    gUnitTfl.setText("");
			    gViewUnitTfl.setText("");
			    bQuantity.setText("");
			    gItemCodeTfl.requestFocus();

			    return false;
			}

			boolean isDupItemCode = isDupItemCodeData(itemCodeStr);

			if (isDupItemCode) {
			    isOK = false;

			    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00187", itemCodeStr)/* 부품번호 ({0}) 는 이미 PLM에 존재합니다. */,
				    "Error", 0);
			    mbox.setModal(true);
			    mbox.setVisible(true);

			    gItemCodeTfl.setText("");
			    gItemNmTfl.setText("");
			    cStatusKr.setText("");
			    cStatus.setText("");
			    gQuantity.setText("");
			    gUnitTfl.setText("");
			    gViewUnitTfl.setText("");
			    bQuantity.setText("");
			    gItemCodeTfl.requestFocus();

			    return false;
			} else {
			    try {
				if (!insertNewBOMData(itemCodeStr)) {
				    isOK = false;
				    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				    return false;
				}
			    } catch (Exception e) {
				isOK = false;
				Kogger.error(getClass(), e);

				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("bomError"), "Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
				return false;
			    }
			}

			pnl.clearBOMList();

			if (!isDupItemCode) {
			    Vector vecEmpty = new Vector();
			    Vector version = new Vector();
			    Vector<String> itemVec = new Vector<String>();
			    Hashtable inputParams = new Hashtable();

			    vecEmpty.removeAllElements();

			    MyBOMQry qry = new MyBOMQry();

			    String rootItemCode = itemCodeStr;

			    // shin....일반BOM 등록할때 메인화면에 뿌림.
			    BOMAssyComponent rootComponent = new BOMAssyComponent(rootItemCode, true);
			    rootComponent.setLevelInt(new Integer(0));
			    rootComponent.setSeqInt(new Integer(0));
			    rootComponent.setSortOrderStr("0001");
			    rootComponent.setNewFlagStr("NEW");
			    rootComponent.setSecondMarkStr("OLD");
			    rootComponent.setDescStr(gItemNmTfl.getText() == null ? "" : gItemNmTfl.getText().trim());
			    // rootComponent.setUomStr(cStatusKr.getText() == null ? "" : cStatusKr.getText().trim());
			    rootComponent.setQuantityDbl(gQuantity.getText() == null ? Double.parseDouble("1.0000") : Double.parseDouble(String.valueOf(gQuantity.getText()).trim())); // 수량
			    rootComponent.setBoxQuantityDbl(bQuantity.getText().equals("") ? Double.parseDouble("1.0000") : Double.parseDouble(String.valueOf(bQuantity.getText()).trim())); // 포장수량
			    rootComponent.setStatusStr(cStatus.getText() == null ? "" : cStatus.getText().trim()); // 결재상태영문
			    rootComponent.setStatusKrStr(cStatusKr.getText() == null ? "" : cStatusKr.getText().trim()); // 결재상태한글
			    rootComponent.setUitStr(gUnitTfl.getText() == null ? "" : gUnitTfl.getText().trim()); // 기본단위
			    rootComponent.setItemSeqInt(new Integer(10));
			    // Added by MJOH, 2011-04-07
			    rootComponent.setIBAPartType(getIBAPartType(rootItemCode));
			    BOMBasicInfoPool.setBomHeaderPartType(rootComponent.getIBAPartType());

			    // itemVec.addElement(BOMBasicInfoPool.getOrgCode().trim() + rootItemCode);
			    itemVec.addElement(rootItemCode);

			    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
			    inputParams.put("itemCode", itemVec);

			    version = KETBomHelper.service.getItemVersion(inputParams);
			    String versionStr = "";

			    for (int k = 0; k < version.size(); k++) {
				if (version.size() > 0) {
				    versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
				    // shin...
				    if (rootItemCode.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
					rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf(".")));
				    }
				}
			    }

			    // shin....
			    BOMBasicInfoPool.setPublicModelName(rootItemCode);
			    BOMBasicInfoPool.setPublicModelDesc(gItemNmTfl.getText() == null ? "" : gItemNmTfl.getText().trim());
			    BOMBasicInfoPool.setPublicBOMStatus(cStatus.getText() == null ? "" : cStatus.getText().trim());
			    // BOMBasicInfoPool.setPublicModelUom(cStatusKr.getText() == null ? "" : cStatusKr.getText().trim());
			    // BOMBasicInfoPool.setPublicModelStatus(gUnitTfl.getText() == null ? "" : gUnitTfl.getText().trim());
			    String ownerid = Utility.checkNVL(BOMBasicInfoPool.getUserId());
			    BOMBasicInfoPool.setPublicOwnerId(ownerid);

			    // shin....
			    BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(rootItemCode));

			    BOMBasicInfoPool.setPublicBOMStatus("INWORK");
			    BOMBasicInfoPool.setPublicBOMStatusKr("INWORK"); // StatusBar 상태 표시를 위해 셋팅

			    BOMBasicInfoPool.setPublicMyStatus("3");

			    if (isAdmin == true) {
				BOMBasicInfoPool.setIsAdminFlag(true);
			    }
			    // pnl.setCheckStatus(2);
			    // pnl.publicStatusKrPanel.setStatusBar();
			    // shin...체크아웃 상태로
			    // 만듬............................................................................................................................................................................
			    rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
			    CheckOutCmd coc = new CheckOutCmd(frmDesktop, app);
			    BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
			    coc.checkOut(nodes, itemVec, BOMBasicInfoPool.getUserName(), true, versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf(".")));
			    // checkoutDao.setCheckOut( rootItemCode, versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")), BOMBasicInfoPool.getUserId() );

			    Vector vecOpenBOM = new Vector();
			    vecOpenBOM.removeAllElements();

			    rootComponent.setItemSeqInt(new Integer(10));

			    // shin.....
			    vecOpenBOM = qry.getBOMOpen(rootComponent.toString());

			    pnl.openBOMData(rootComponent, vecOpenBOM);
			    pnl.requestFocus();
			    // ..............................................................................................................................
			}
		    }

		    if (isOK) {
			pnl.setMenuBarEnabled();
			cancelBOM();
		    }
		}
	    }

	    KetMainJTreeTable km = new KetMainJTreeTable();
	    km.setGenMain(app);

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
	    for (int i = 0; i < count; i++) {
		Component c = getComponent(i);
		this.remove(c);
		c = null;
	    }

	    super.dispose();
	    System.gc();
	    return;
	} catch (Throwable ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    public boolean isItemCodeAvailableData(String itemCode) {
	boolean isAva = false;
	try {
	    Hashtable inputParams = new Hashtable();
	    inputParams.put("itemCode", (itemCode));
	    inputParams.put("description", "");
	    inputParams.put("creator", "");
	    inputParams.put("createFromDate", "");
	    inputParams.put("createToDate", "");
	    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	    inputParams.put("bomAllowed", "Y");
	    inputParams.put("bomFlag", "N");

	    Vector queryResult = new Vector();
	    queryResult = KETBomHelper.service.searchItem(inputParams);

	    if (queryResult.size() > 0) {
		isAva = true;
	    }

	    if (isAva) {
		Hashtable hasSearchItemResult = new Hashtable();

		hasSearchItemResult = (Hashtable) queryResult.elementAt(0);
		gItemNmTfl.setText((String) hasSearchItemResult.get("description"));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return isAva;
    }

    public String getIBAPartType(String itemCode) {
	String returnStr = "";

	try {
	    // Hashtable inputParams = new Hashtable();
	    // inputParams.put("itemCode", itemCode);
	    // inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	    //
	    // Vector queryResult = new Vector();
	    // queryResult = KETBomHelper.service.searchItem(inputParams);
	    //
	    // if( queryResult.size() > 0)
	    // {
	    // Hashtable resultHash = new Hashtable();
	    // resultHash = (Hashtable)queryResult.elementAt(0);
	    //
	    // returnStr = (String)resultHash.get("type");
	    // }

	    WTPart wt = KETPartHelper.service.getPart(itemCode);
	    returnStr = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return returnStr;
    }

    public boolean isItemCodeCheckOutData(String itemCode) {
	boolean isCheckOut = false;
	try {
	    Vector itemCheckOutVec = new Vector();
	    // shin.....
	    itemCheckOutVec.add(itemCode);

	    Vector queryResult = KETBomHelper.service.getCheckOuter(itemCheckOutVec);
	    Kogger.debug(getClass(), "==========>> isItemCodeCheckOutData : " + queryResult.toString());

	    if (queryResult.size() > 0) {
		isCheckOut = true;
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return isCheckOut;
    }

    public boolean isItemCodeDifferentBOMData(String itemCode) {
	boolean isItemCode = false;

	DBConnectionManager res = null;
	Connection conn = null;

	try {
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(appReg.getString("plm"));

	    BOMSearchDao searchDao = new BOMSearchDao();
	    isItemCode = searchDao.isUsedItemCode(conn, itemCode);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (res != null) {
		res.freeConnection(appReg.getString("plm"), conn);
	    }
	}
	return isItemCode;
    }

    public boolean isDupItemCodeData(String itemCode) throws Exception {
	boolean isDep = false;
	try {
	    Hashtable inputParams = new Hashtable();

	    // shin.....
	    inputParams.put("itemCode", (itemCode));
	    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());

	    String queryResult = KETBomHelper.service.searchDuplicationItem(inputParams);

	    Kogger.debug(getClass(), "searchDuplicationItem  : " + queryResult);

	    if (queryResult.equals("Y")) {
		isDep = true;
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return isDep;
    }

    public boolean insertNewBOMData(String itemCode) throws Exception {
	String SQL = "";
	int coworkIdIdx = 0;
	int coworkNameIdx = 0;
	int coworkDeptIdx = 0;
	int coworkMailIdx = 0;

	for (int i = 0; i < coworkerTbl.getColumnCount(); i++) {
	    if (coworkerTbl.getColumnName(i).toString().equals("ID")) {
		coworkIdIdx = i;
	    }
	    if (coworkerTbl.getColumnName(i).toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/* 이름 */)) {
		coworkNameIdx = i;
	    }
	    if (coworkerTbl.getColumnName(i).toString().equals("E-Mail")) {
		coworkMailIdx = i;
	    }
	    if (coworkerTbl.getColumnName(i).toString().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/* 부서 */)) {
		coworkDeptIdx = i;
	    }
	}

	// shin.....

	if (!setWindChill(itemCode)) {
	    return false;
	}

	DBConnectionManager res = null;
	Connection conn = null;
	Statement stmt = null;

	try {
	    // shin.....
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(appReg.getString("plm"));

	    stmt = conn.createStatement();

	    String itemCd = BOMBasicInfoPool.getPublicModelName();
	    Hashtable inputParams = new Hashtable();
	    Vector itemVec = new Vector();

	    itemVec.add(itemCd);
	    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	    inputParams.put("itemCode", itemVec);

	    Vector version = KETBomHelper.service.getItemVersion(inputParams);
	    String versionStr = "";
	    for (int k = 0; k < version.size(); k++) {
		if (version.size() > 0) {
		    versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
		    // shin...
		    if (itemCd.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
			versionStr = versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf("."));
		    }
		}
	    }

	    SQL = " INSERT INTO ketbomcoworker                                                                                    ";
	    SQL += " (newbomcode, coworkerid, coworkername, coworkeremail, coworkerdept, endworkingflag, bomversion)            ";
	    SQL += " VALUES ( '" + BOMBasicInfoPool.getPublicModelName() + "'                                                ";
	    SQL += ", '" + coworkerTbl.getValueAt(0, coworkIdIdx) + "'                                                                ";
	    SQL += ", '" + coworkerTbl.getValueAt(0, coworkNameIdx) + "'                                                        ";
	    SQL += ", '" + coworkerTbl.getValueAt(0, coworkMailIdx) + "'                                                            ";
	    SQL += ", '" + coworkerTbl.getValueAt(0, coworkDeptIdx) + "'                                                            ";
	    SQL += ", '2' , '" + versionStr + "' )                                                                                                ";

	    stmt.executeUpdate(SQL);

	    for (int i = 1; i < coworkerTbl.getRowCount(); i++) {
		SQL = " INSERT INTO ketbomcoworker                                                                                ";
		SQL += " (newbomcode, coworkerid, coworkername, coworkeremail, coworkerdept, endworkingflag, bomversion)        ";
		SQL += " VALUES ( '" + BOMBasicInfoPool.getPublicModelName() + "'                                            ";
		SQL += ", '" + coworkerTbl.getValueAt(i, coworkIdIdx) + "'                                                            ";
		SQL += ", '" + coworkerTbl.getValueAt(i, coworkNameIdx) + "'                                                        ";
		SQL += ", '" + coworkerTbl.getValueAt(i, coworkMailIdx) + "'                                                        ";
		SQL += ", '" + coworkerTbl.getValueAt(i, coworkDeptIdx) + "'                                                        ";
		SQL += ", '1' , '" + versionStr + "' )                                                                                            ";

		stmt.executeUpdate(SQL);
	    }

	    conn.commit();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return false;
	} finally {
	    try {
		if (stmt != null)
		    stmt.close();
	    } catch (Exception e) {
		MessageBox mbox = new MessageBox(frmDesktop, messageRegistry.getString("dbCloseError"), "Error", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	    } finally {
		if (res != null) {
		    res.freeConnection(appReg.getString("plm"), conn);
		}
	    }
	    return true;
	}
    }

    public boolean setWindChill(String itemCode) {
	try {
	    int coworkerTblRowCnt = coworkerTbl.getRowCount();
	    Hashtable inputParams = new Hashtable();

	    // shin.....
	    Vector itemVec = new Vector();
	    String rootItemCd = gItemCodeTfl.getText() == null ? "" : (gItemCodeTfl.getText().trim());
	    inputParams.put("newBomCode", rootItemCd);
	    inputParams.put("description", gItemNmTfl.getText() == null ? "" : gItemNmTfl.getText().trim());
	    inputParams.put("statusKr", cStatusKr.getText() == null ? "" : cStatusKr.getText().trim());
	    inputParams.put("status", cStatus.getText() == null ? "" : cStatus.getText().trim());
	    inputParams.put("quantity", gQuantity.getText() == null ? "" : String.valueOf(Double.parseDouble(gQuantity.getText().trim())));
	    inputParams.put("boxQuantity", bQuantity.getText().equals("") ? "" : bQuantity.getText().trim());
	    inputParams.put("unit", gUnitTfl.getText() == null ? "" : gUnitTfl.getText().trim());
	    inputParams.put("creatorDept", aryOwnerData[2]);
	    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
	    inputParams.put("newBomCode", gItemCodeTfl.getText() == null ? "" : (gItemCodeTfl.getText().trim()));

	    itemVec.add(rootItemCd);
	    inputParams.put("itemCode", itemVec);

	    Vector version = KETBomHelper.service.getItemVersion(inputParams);
	    String versionStr = "";

	    for (int k = 0; k < version.size(); k++) {
		if (version.size() > 0) {
		    versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
		    // shin...
		    if (rootItemCd.equals(versionStr.substring(0, versionStr.indexOf("@")))) {
			versionStr = versionStr.substring(versionStr.indexOf("@") + 1, versionStr.indexOf("."));
		    }
		}
	    }
	    inputParams.put("versionStr", versionStr);

	    // ..................................................................................................................

	    Vector coworkerVec = new Vector();
	    for (int i = 0; i < coworkerTblRowCnt; i++) {
		coworkerVec.addElement((String) coworkerTbl.getValueAt(i, 0));
	    }
	    inputParams.put("coworkers", coworkerVec);

	    String oid = KETBomHelper.service.createBom(inputParams);

	    if (oid == null || oid.length() == 0) {
		return false;
	    }

	    if (oid != null) {
		Hashtable hasHeader = new Hashtable();
		hasHeader = KETBomHelper.service.getBom(oid);

		// shin.....

		BOMBasicInfoPool.setPublicModelName(itemCode);
		/*
	         * BOMBasicInfoPool.setPublicBOMStatus((String)hasHeader.get("lifeCycleState")); BOMBasicInfoPool.setPublicApproveDate((String)hasHeader.get("publicApproveDate"));
	         * BOMBasicInfoPool.setPublicApproveDept((String)hasHeader.get("publicApproveDept")); BOMBasicInfoPool.setPublicApproveId((String)hasHeader.get("publicApproveID"));
	         * BOMBasicInfoPool.setPublicApproveName((String)hasHeader.get("publicApproveName")); BOMBasicInfoPool.setPublicBasicModelName((String)hasHeader.get("publicBasicModelName"));
	         * BOMBasicInfoPool.setPublicBasicModelDesc((String)hasHeader.get("publicBasicModelDesc")); BOMBasicInfoPool.setPublicOwnerDate((String)hasHeader.get("publicOwnerDate"));
	         * BOMBasicInfoPool.setPublicOwnerDept((String)hasHeader.get("publicOwnerDept")); BOMBasicInfoPool.setPublicOwnerId((String)hasHeader.get("publicOwnerID"));
	         * BOMBasicInfoPool.setPublicOwnerName((String)hasHeader.get("publicOwnerName")); BOMBasicInfoPool.setPublicOwnerEmail((String)hasHeader.get("publicOwnerEmail"));
	         * BOMBasicInfoPool.setPublicModelName((String)hasHeader.get("publicModelName")); BOMBasicInfoPool.setPublicModelDesc((String)hasHeader.get("publicModelDesc"));
	         * BOMBasicInfoPool.setPublicModelUom((String)hasHeader.get("publicModelUom")); BOMBasicInfoPool.setPublicModelUserItemType((String)hasHeader.get("publicModelUserItemType"));
	         * BOMBasicInfoPool.setPublicModelStatus((String)hasHeader.get("publicModelStatus")); BOMBasicInfoPool.setPublicTransFlag((String)hasHeader.get("publicTransFlag"));
	         * BOMBasicInfoPool.setPublicCheckOutStatus((String)hasHeader.get("publicCheckOutStatus"));
	         */
		BOMBasicInfoPool.setPublicBOMOid(oid);
	    }

	    return true;
	} catch (Exception ex) {
	    MessageBox mbox = new MessageBox(frmDesktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00097")/* 관리자에게 문의하세요. */+ " \n\n" + ex.toString(), "BOM System Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	    Kogger.error(getClass(), ex);

	    return false;
	}
    }

    public static void main(java.lang.String[] args) {
	try {
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
	    Kogger.error(GeneralBOMDialog.class, exception);
	}
    }

    public void startOperation(String s) {
    }

    public void endOperation() {
    }

}
