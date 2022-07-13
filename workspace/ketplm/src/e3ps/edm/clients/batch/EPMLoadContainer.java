package e3ps.edm.clients.batch;

/**
 *  Copyright 1999-2002 Matthew Robinson and Pavel Vorobiev.
 *  All Rights Reserved.
 *
 *  ===================================================
 *  This program contains code from the book "Swing"
 *  2nd Edition by Matthew Robinson and Pavel Vorobiev
 *  http://www.spindoczine.com/sbe
 *  ===================================================
 *
 *  The above paragraph must be included in full, unmodified
 *  and completely intact in the beginning of any source code
 *  file that references, copies or uses (in any way, shape
 *  or form) code contained in this file.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import e3ps.common.content.remote.ContentDownload;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.StringUtil;
import e3ps.load.remote.CachFileUpload;
import e3ps.load.remote.CashFileUploader;

public class EPMLoadContainer extends JPanel {
    public static final String  APP_NAME            = "PLM";

    protected JTable            m_table;
    protected EPMTableModel     m_data;

    protected XTableColumnModel columnModel         = null;

    public JFrame               frame;

    String[]                    fileExtensions      = { "xls" };
    File                        currentDir          = null;
    File                        tempFile;

    ///////////////////////////////////////////////
    // Added by MJOH, 2011-03-17
    // 프로젝트 링크 추가 처리
    String                      projectOid          = "";
    ///////////////////////////////////////////////

    //=========== Label ==============================================
    //사업부
    DefaultComboBoxModel        bizModel;
    JComboBox                   bizCb;

    //도면구분
    DefaultComboBoxModel        dstModel;
    JComboBox                   dstCb;

    //도면유형
    DefaultComboBoxModel        catModel;
    JComboBox                   catCb;

    //CAD종류
    DefaultComboBoxModel        cadAppModel;
    JComboBox                   cadAppCb;

    ///////////////////////////////////////////////
    // Added by MJOH, 2011-03-17
    // 프로젝트 링크 추가 처리
    ///////////////////////////////////////////////
    //프로젝트번호
    JTextField                  projectNoTf;
    JButton                     searchProjectBtn;
    JButton                     deleteProjectBtn;

    //프로젝트명
    JTextField                  projectNameTf;
    ///////////////////////////////////////////////

    //Part List 첨부
    File                        localDir;
    File                        excelFile;

    JTextField                  excelFilePath;

    JButton                     searchFileBtn;
    JButton                     hideBtn;
    JButton                     showBtn;
    JButton                     loadBtn;
    JButton                     refreshBtn;
    JButton                     saveBtn;
    JButton                     closeBtn;

    EPMButtonActionListener     btnAction;

    String                      userId;
    String                      manageType;
    String                      language;

    boolean                     isdrm               = false;

    EPMLoadContext              loadctx;

    final String                closeBtnCmd         = "clickCloseBtn";
    final String                bizCbCmd            = "changeBizCb";
    final String                dstCbCmd            = "changeDstCb";
    final String                catCbCmd            = "changeCatCb";
    final String                cadAppCbCmd         = "changeCadAppCb";
    final String                hideBtnCmd          = "clickHideBtn";
    final String                showBtnCmd          = "clickShowBtn";
    final String                loadBtnCmd          = "clickLoadBtn";
    final String                refreshBtnCmd       = "clickRefreshBtn";
    final String                saveBtnCmd          = "clickSaveBtn";
    final String                searchFileBtnCmd    = "clickSearchFileBtn";

    //////////////////////////////////////////////////////////////////////////////////////////
    // Added by MJOH, 2011-03-17
    // 프로젝트 링크 추가 처리
    //////////////////////////////////////////////////////////////////////////////////////////
    final String                searchProjectBtnCmd = "clickSearchProjectBtn";
    final String                deleteProjectBtnCmd = "clickDeleteProjectBtn";
    //////////////////////////////////////////////////////////////////////////////////////////

    private static Object       lock                = new Object();

    public EPMLoadContainer(String userId, String manageType, boolean isdrm, String language) {
	//super(APP_NAME);

	this.userId = userId;
	this.manageType = manageType;
	this.isdrm = isdrm;

	// [START] [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee
	this.language = StringUtil.checkNull(language);
	KETMessageService.initService(language);
	System.out.println("===> KETMessageService service inited. (language=" + language + ")");
	Locale.setDefault(KETMessageService.service.getLocale());
	// [END] [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee

	//Container cp = this.getContentPane();
	//cp.setLayout(new BorderLayout());
	setLayout(new BorderLayout());

	loadctx = new EPMLoadContext(userId);
	//EPMLoadContext.init();

	Vector bizVec = null;
	try {
	    bizVec = EPMLoadContext.getBizCodes(userId);
	} catch (RemoteException e) {
	    bizVec = new Vector();
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    bizVec = new Vector();
	    e.printStackTrace();
	}

	if ((bizVec == null) || (bizVec.size() == 0)) {
	    bizVec = new Vector();
	    bizVec.add(0, new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));
	}

	bizModel = new DefaultComboBoxModel(bizVec);
	Vector dstVec = null;

	try {
	    dstVec = EPMLoadContext.getDevStageSet(manageType);
	} catch (RemoteException e) {
	    dstVec = new Vector();
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    dstVec = new Vector();
	    e.printStackTrace();
	}

	dstVec.add(0, new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));
	dstModel = new DefaultComboBoxModel(dstVec);

	Vector catVec = new Vector();
	catVec.add(new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));
	catModel = new DefaultComboBoxModel(catVec);

	Vector cadAppVec = new Vector();
	cadAppVec.add(new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));
	cadAppModel = new DefaultComboBoxModel(cadAppVec);

	btnAction = new EPMButtonActionListener();

	//JPopupMenu.setDefaultLightWeightPopupEnabled(false);
	//JMenuBar menuBar = createMenuBar();
	//setJMenuBar(menuBar);

	add(labelPanel(), BorderLayout.NORTH);
	add(contentPanel(), BorderLayout.CENTER);
	add(closePanel(), BorderLayout.SOUTH);
    }

    protected JMenuBar createMenuBar() {
	JMenuItem mItem;
	JMenuBar menuBar = new JMenuBar();

	JMenu mFile = new JMenu("File");

	mItem = new JMenuItem("Exit");
	ActionListener lst = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		quit();
	    }
	};
	mItem.addActionListener(lst);
	mFile.add(mItem);
	menuBar.add(mFile);

	return menuBar;
    }

    protected JPanel closePanel() {
	JPanel p = new JPanel();

	closeBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "02656")/*종료*/);
	closeBtn.addActionListener(btnAction);
	closeBtn.setDefaultCapable(true);
	closeBtn.setMnemonic('g');
	closeBtn.setActionCommand(closeBtnCmd);
	p.add(closeBtn);

	return p;
    }

    protected JPanel contentPanel() {
	JPanel tp = new JPanel(new BorderLayout());
	//lp.setBorder(new EmptyBorder(10, 10, 10, 10));

	tp.setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(), KETMessageService.service.getString("e3ps.message.ket_message", "01378")/*목록*/), new EmptyBorder(1, 1, 1, 1)));

	UIManager.put("Table.focusCellHighlightBorder", new LineBorder(Color.black, 0));

	m_data = new EPMTableModel();

	m_table = new JTable() {
	    //Implement table cell tool tips.
	    public String getToolTipText(MouseEvent e) {
		String tip = null;
		java.awt.Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int realColumnIndex = convertColumnIndexToModel(colIndex);

		if (realColumnIndex == 2) {
		    //TableModel model = getModel();
		    //TableCellData mdata = (TableCellData)model.getValueAt(rowIndex,2);

		    EPMLoadData rdata = (EPMLoadData) m_data.getDatas().get(rowIndex);
		    tip = getToolTipMsg(rdata);
		}
		else {
		    //You can omit this part if you know you don't
		    //have any renderers that supply their own tool
		    //tips.
		    tip = super.getToolTipText(e);
		}

		return tip;
	    }
	};

	m_table.setAutoCreateColumnsFromModel(false);
	m_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	//m_table.setRowHeight(24);

	columnModel = new XTableColumnModel();

	m_table.setColumnModel(columnModel);
	m_table.createDefaultColumnsFromModel();

	m_table.setModel(m_data);

	for (int k = 0; k < m_data.getColumnCount(); k++) {
	    DefaultTableCellRenderer renderer = new EPMTableCellRenderer();
	    renderer.setHorizontalAlignment(EPMTableModel.m_columns[k].m_alignment);
	    TableColumn column = new TableColumn(k, EPMTableModel.m_columns[k].m_width, renderer, null);
	    column.setHeaderRenderer(createDefaultRenderer());	// NEW
	    m_table.addColumn(column);
	}

	JTableHeader header = m_table.getTableHeader();
	header.setUpdateTableInRealTime(true);
	header.addMouseListener(new ColumnListener());
	header.setReorderingAllowed(false);

	JScrollPane ps = new JScrollPane();
	ps.getViewport().setBackground(m_table.getBackground());
	ps.getViewport().add(m_table);

	tp.add(ps, BorderLayout.CENTER);

	return tp;
    }

    protected JPanel labelPanel() {
	JPanel lp = new JPanel(new GridBagLayout());
	//lp.setBorder(new EmptyBorder(10, 10, 10, 10));
	lp.setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(), KETMessageService.service.getString("e3ps.message.ket_message", "01059")/*금형도면*/+ " "
	        + KETMessageService.service.getString("e3ps.message.ket_message", "03406")/*일괄등록*/), new EmptyBorder(20, 15, 10, 15)));

	//top,left,bottom,right
	Insets labInsets = new Insets(10, 0, 0, 0);
	Insets cntInsets = new Insets(10, 10, 0, 0);

	GridBagConstraints c = new GridBagConstraints();
	c.gridheight = 1;
	c.weightx = 0.5;
	c.weighty = 0.5;

	//===== 사업부 =================================================
	JLabel bizLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "01662")/*사업부*/+ " :");
	//bizLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 0;
	c.gridy = 0;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(bizLabel, c);

	//select
	bizCb = new JComboBox(bizModel);
	bizCb.setActionCommand(bizCbCmd);
	bizCb.setRenderer(new EDMComboBoxRenderer());
	bizCb.setPreferredSize(new Dimension(150, 22));
	bizCb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));

	c.gridx = 1;
	c.gridy = 0;
	c.gridwidth = 1;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(bizCb, c);

	//===== 도면구분  =================================================
	JLabel dstLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "01264")/*도면구분*/+ " :");
	//dstLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 2;
	c.gridy = 0;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(dstLabel, c);

	//select
	dstCb = new JComboBox(dstModel);
	dstCb.setActionCommand(dstCbCmd);
	dstCb.setRenderer(new EDMComboBoxRenderer());
	dstCb.setPreferredSize(new Dimension(150, 22));
	dstCb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));
	dstCb.addActionListener(btnAction);

	c.gridx = 3;
	c.gridy = 0;
	c.gridwidth = 1;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(dstCb, c);

	//===== 도면유형 ==================================================
	JLabel catLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "01288")/*도면유형*/+ " :");
	//catLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 0;
	c.gridy = 1;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(catLabel, c);

	//select
	catCb = new JComboBox(catModel);
	catCb.setActionCommand(catCbCmd);
	catCb.setRenderer(new EDMComboBoxRenderer());
	catCb.setPreferredSize(new Dimension(150, 22));
	catCb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));
	catCb.addActionListener(btnAction);

	c.gridx = 1;
	c.gridy = 1;
	c.gridwidth = 1;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(catCb, c);

	//===== CAD종류 ==================================================
	JLabel cadLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "00102")/*CAD종류*/+ " :");
	//cadLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 2;
	c.gridy = 1;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(cadLabel, c);

	//select
	cadAppCb = new JComboBox(cadAppModel);
	cadAppCb.setActionCommand(cadAppCbCmd);
	cadAppCb.setRenderer(new EDMComboBoxRenderer());
	cadAppCb.setPreferredSize(new Dimension(150, 22));
	cadAppCb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));

	c.gridx = 3;
	c.gridy = 1;
	c.gridwidth = 1;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(cadAppCb, c);

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Added by MJOH, 2011-03-17
	// 프로젝트 링크 추가 처리
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//===== 프로젝트번호 ==========================================
	JLabel projectNoLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "03114")/*프로젝트번호*/+ " :");
	//partListLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 0;
	c.gridy = 2;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(projectNoLabel, c);

	projectNoTf = new JTextField();
	projectNoTf.setPreferredSize(new Dimension(96, 22));
	projectNoTf.setEditable(true);
	projectNoTf.setBackground(Color.LIGHT_GRAY);

	searchProjectBtn = new JButton("", getImageIcon("/e3ps/edm/clients/batch/images/EDM_Search.gif"));
	searchProjectBtn.setActionCommand(searchProjectBtnCmd);
	searchProjectBtn.setPreferredSize(new Dimension(22, 22));
	searchProjectBtn.addActionListener(btnAction);

	deleteProjectBtn = new JButton("", getImageIcon("/e3ps/edm/clients/batch/images/EDM_Remove.gif"));
	deleteProjectBtn.setActionCommand(deleteProjectBtnCmd);
	deleteProjectBtn.setPreferredSize(new Dimension(22, 22));
	deleteProjectBtn.addActionListener(btnAction);

	JPanel pjtPanel = new JPanel(new BorderLayout(5, 0));
	pjtPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

	JPanel pjtBtnPanel = new JPanel(new BorderLayout(5, 0));
	pjtBtnPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

	pjtBtnPanel.add(searchProjectBtn, BorderLayout.WEST);
	pjtBtnPanel.add(deleteProjectBtn, BorderLayout.EAST);

	pjtPanel.add(projectNoTf, BorderLayout.CENTER);
	pjtPanel.add(pjtBtnPanel, BorderLayout.LINE_END);

	c.gridx = 1;
	c.gridy = 2;
	c.gridwidth = 1;
	c.insets = cntInsets; //new Insets(10, 10, 0, 0);
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(pjtPanel, c);

	//===== 프로젝트명 ============================================
	JLabel projectNameLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/+ " :");
	//partListLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 2;
	c.gridy = 2;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(projectNameLabel, c);

	projectNameTf = new JTextField();
	projectNameTf.setPreferredSize(new Dimension(150, 22));
	projectNameTf.setEditable(true);
	projectNameTf.setBackground(Color.LIGHT_GRAY);

	c.gridx = 3;
	c.gridy = 2;
	c.gridwidth = 1;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(projectNameTf, c);
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//===== Part List 첨부 ===========================================
	JLabel partListLabel = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "00351")/*PartList*/+ " "
	        + KETMessageService.service.getString("e3ps.message.ket_message", "02794")/*첨부*/+ " :");
	//partListLabel.setPreferredSize(new Dimension(100, 30));

	c.gridx = 0;
	c.gridy = 3;
	c.gridwidth = 1;
	c.insets = labInsets;
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.NONE;
	lp.add(partListLabel, c);

	JPanel pp0 = new JPanel(new BorderLayout(5, 0));
	pp0.setBorder(new EmptyBorder(0, 0, 0, 0));

	excelFilePath = new JTextField();
	excelFilePath.setPreferredSize(new Dimension(569, 22));
	excelFilePath.setEditable(false);
	excelFilePath.setBackground(Color.white);

	searchFileBtn = new JButton("Search");
	searchFileBtn.setActionCommand(searchFileBtnCmd);
	searchFileBtn.setPreferredSize(new Dimension(80, 22));
	searchFileBtn.addActionListener(btnAction);

	pp0.add(excelFilePath, BorderLayout.CENTER);
	pp0.add(searchFileBtn, BorderLayout.LINE_END);

	c.gridx = 1;
	c.gridy = 3;
	c.gridwidth = 3;
	c.insets = cntInsets;
	c.anchor = GridBagConstraints.WEST;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(pp0, c);

	//===== Load/Refresh/Save ===========================================
	JPanel pp = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 0));
	pp.setBorder(new EmptyBorder(0, 0, 0, 0));

	hideBtn = new JButton("Hide");
	hideBtn.setActionCommand(hideBtnCmd);
	hideBtn.setPreferredSize(new Dimension(80, 22));
	hideBtn.addActionListener(btnAction);

	showBtn = new JButton("Show");
	showBtn.setActionCommand(showBtnCmd);
	showBtn.setPreferredSize(new Dimension(80, 22));
	showBtn.addActionListener(btnAction);
	showBtn.setVisible(false);

	loadBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "03407")/*체크*/);
	loadBtn.setActionCommand(loadBtnCmd);
	loadBtn.setPreferredSize(new Dimension(80, 22));
	loadBtn.addActionListener(btnAction);

	refreshBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "01768")/*새로고침*/);
	refreshBtn.setActionCommand(refreshBtnCmd);
	refreshBtn.setPreferredSize(new Dimension(80, 22));
	refreshBtn.addActionListener(btnAction);

	saveBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "02453")/*저장*/);
	saveBtn.setActionCommand(saveBtnCmd);
	saveBtn.setPreferredSize(new Dimension(80, 22));
	saveBtn.addActionListener(btnAction);
	//saveBtn.setEnabled(false);

	pp.add(hideBtn);
	pp.add(showBtn);
	pp.add(loadBtn);
	//pp.add(refreshBtn);
	pp.add(saveBtn);

	c.gridx = 0;
	c.gridy = 4;
	c.gridwidth = 4;
	c.insets = new Insets(10, 0, 0, -5);
	c.anchor = GridBagConstraints.LINE_END;
	c.fill = GridBagConstraints.HORIZONTAL;
	lp.add(pp, c);

	return lp;
    }

    public void showCategory(EPMCodeData dstData, boolean onFocus) {
	//m_lblImg.setIcon(car.getIcon());
	if (catCb.getItemCount() > 0) {
	    catCb.removeAllItems();
	}

	Vector v = null;
	if (dstData.m_value.trim().length() > 0) {
	    try {
		v = EPMLoadContext.getCADCatsSet(manageType, dstData.m_value);
	    } catch (RemoteException e) {
		v = null;
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		v = null;
		e.printStackTrace();
	    }
	}

	if (v == null) {
	    v = new Vector();
	}

	v.add(0, new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));
	/*
	for (int k=0; k<v.size(); k++)
		catCb.addItem(v.elementAt(k));
	*/
	catModel = new DefaultComboBoxModel(v);
	catCb.setModel(catModel);

	if (onFocus) {
	    catCb.grabFocus();
	}

	showCADApp((EPMCodeData) catCb.getModel().getSelectedItem(), false);
    }

    public void showCADApp(EPMCodeData catData, boolean onFocus) {
	//m_lblImg.setIcon(car.getIcon());
	if (cadAppCb.getItemCount() > 0) {
	    cadAppCb.removeAllItems();
	}

	Vector v = null;

	if (catData.m_value.trim().length() > 0) {
	    try {
		v = EPMLoadContext.getCADAppTypeSet(catData.m_value);
	    } catch (RemoteException e) {
		v = null;
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		v = null;
		e.printStackTrace();
	    }
	}

	if (v == null) {
	    v = new Vector();
	}

	v.add(0, new EPMCodeData("", "", KETMessageService.service.getString("e3ps.message.ket_message", "01802")/*선택*/, ""));

	/*
	Vector v = new Vector();
	for (int k=0; k<v.size(); k++)
		cadAppCb.addItem(v.elementAt(k));
	*/

	cadAppModel = new DefaultComboBoxModel(v);
	cadAppCb.setModel(cadAppModel);

	if (onFocus) {
	    cadAppCb.grabFocus();
	}

	saveBtn.setEnabled(false);
    }

    // NEW
    protected TableCellRenderer createDefaultRenderer() {
	DefaultTableCellRenderer label = new DefaultTableCellRenderer() {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (table != null) {
		    JTableHeader header = table.getTableHeader();

		    if (header != null) {
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
		    }
		}

		setText((value == null) ? "" : value.toString());
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return this;
	    }
	};

	label.setHorizontalAlignment(JLabel.CENTER);
	return label;
    }


    private void loadFile(final String type, final String bizOid, final String devStage, final String category, final String cadAppType, final File file, final String dir) {
	if (file == null || !file.exists() || file.isDirectory()) {
	    return;
	}

	synchronized (lock) {
	    new Thread(new Runnable() {
		final HeavyProgressBar loadthread = new HeavyProgressBar(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "03408")/*엑셀 데이터 체크 중 입니다.*/, true, 1,
		                                          100, 100);

		public void run() {
		    //progressbar run
		    loadthread.go();

		    try {
			Thread.sleep(500);
		    } catch (InterruptedException e) {
		    }

		    String errMsg = "";
		    try {
			reset(type, bizOid, devStage, category, cadAppType, file, dir);
			//SwingUtilities.invokeAndWait( new Runnable() {
			//	public void run() { reset(type, bizOid, devStage, category, cadAppType, file, dir); }
			//});
		    } catch (Exception ex) {
			ex.printStackTrace();
			errMsg = ex.toString();
		    } finally {
			if (loadthread != null) {
			    loadthread.setStop();
			}

			try {
			    Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			loadthread.showMessageDialog((errMsg.length() == 0) ? KETMessageService.service.getString("e3ps.message.ket_message", "03409")/*체크 완료 했습니다.*/: KETMessageService.service
			        .getString("e3ps.message.ket_message", "03410")/*체크 중 에러가 발생했습니다.\n*/+ errMsg);
		    }
		    /*
		    JOptionPane.showMessageDialog(EPMLoadContainer.this,
		    		(errMsg.length() == 0)? "체크 완료 했습니다.":errMsg, "확인",
		    		JOptionPane.INFORMATION_MESSAGE);
		    */
		}
	    }).start();
	}
    }

    private void reset(String type, String bizOid, String devStage, String category, String cadAppType, File file, String dir) throws Exception {
	synchronized (lock) {
	    InputStream in = null;
	    Workbook wb = null;
	    /*
	    try {
	    	in = new FileInputStream(file);
	    	wb = Workbook.getWorkbook(in);
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(this,
	    		e.getLocalizedMessage(), "경고",
	    		JOptionPane.WARNING_MESSAGE);
	    	e.printStackTrace();
	    	return;
	    } finally {
	    	if(in != null) {
	    		try {
	    			in.close();
	    		} catch (IOException e) {}
	    		in = null;
	    	}
	    }
	    */

	    String dirString = null;
	    try {
		//CashFileUploader uploader = new CashFileUploader("wcadmin");
		CashFileUploader uploader = new CashFileUploader(userId);
		uploader.addUploadFile(file, isdrm);
		uploader.excute();

		String folder = uploader.getFolder();
		dirString = CachFileUpload.TEMPDIR + "/" + folder;
		String drmPath = dirString + "/" + file.getName();

		ContentDownload down = new ContentDownload();
		down.addContentStream(drmPath);
		down.execute();

		in = down.getInputStream();
		wb = Workbook.getWorkbook(in);

		Sheet[] sheets = wb.getSheets();
		Sheet sheet = sheets[0];

		m_data.removeAll();

		int rows = sheet.getRows();
		Cell columns[] = sheets[0].getRow(0);

		m_table.tableChanged(new TableModelEvent(m_data, -1));

		HashMap dupMap = new HashMap();
		boolean isUpload = true;

		Cell ckeys[] = sheet.getRow(0);
		for (int i = 1; i < rows; i++) {
		    //System.out.println("current row : " + i);
		    Cell cell[] = sheet.getRow(i);

		    EPMLoadData data = addRow(cell, i + 1, type, bizOid, devStage, category, cadAppType, dir);
		    if (data == null) {
			continue;
		    }

		    if (data.number.trim().length() > 0) {
			if (!dupMap.containsKey(data.number.trim())) {
			    dupMap.put(data.number.trim(), "");
			}
			else {
			    data.isAlreadyGone = true;
			    data.isValidate = false;
			}
		    }

		    if (isUpload) {
			isUpload = data.isValidate;
		    }

		    m_data.addRow(data);
		}

		if (wb != null) {
		    wb.close();
		    wb = null;
		}

		saveBtn.setEnabled(isUpload);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e);
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (IOException e) {
		    }

		    in = null;
		}

		if (wb != null) {
		    wb.close();
		    wb = null;
		}

		if ((dirString != null) && (dirString.trim().length() > 0)) {
		    boolean bRetval = loadctx.deleteLoadFile(dirString, userId);
		    System.out.println("excel file delete : " + bRetval);
		}
	    }
	}
    }

    private void doLoad(final File file, final String dir) {
	if (file == null || !file.exists() || file.isDirectory()) {
	    return;
	}

	final Vector v = new Vector();
	Vector datas = m_data.getDatas();

	for (int i = 0; i < datas.size(); i++) {
	    EPMLoadData data = (EPMLoadData) datas.get(i);

	    if ((data.isSkipRow) || !(data.isValidate)) {
		continue;
	    }

	    v.add(data);
	}

	if (v.size() == 0) {
	    JOptionPane.showMessageDialog(this, KETMessageService.service.getString("e3ps.message.ket_message", "03411")/*저장할 수 있는 도면이 없습니다.*/,
		    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.INFORMATION_MESSAGE);
	    return;
	}

	synchronized (lock) {
	    Thread runner = new Thread() {
		final HeavyProgressBar loadthread = new HeavyProgressBar(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "03412")/*도면 저장 중입니다.*/, true, 1, 100,
		                                          100);

		public void run() {
		    loadthread.go();

		    try {
			Thread.sleep(500);
		    } catch (InterruptedException e) {
		    }


		    String errMsg = "";
		    try {
			load(v, dir);
			//SwingUtilities.invokeLater( new Runnable() {
			//	public void run() { load(v, dir); }
			//});
		    } catch (Exception ex) {
			ex.printStackTrace();
			errMsg = ex.getLocalizedMessage();
		    } finally {
			if (loadthread != null) {
			    loadthread.setStop();
			}

			try {
			    Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			loadthread.showMessageDialog((errMsg.length() == 0) ? KETMessageService.service.getString("e3ps.message.ket_message", "03413")/*저장 완료했습니다.*/: KETMessageService.service
			        .getString("e3ps.message.ket_message", "03414")/*저장 중 에러가 발생했습니다.\n*/+ errMsg);
		    }
		    /*
		    JOptionPane.showMessageDialog(EPMLoadContainer.this,
		    		"저장 완료 했습니다.", "확인",
		    		JOptionPane.INFORMATION_MESSAGE); */
		}
	    };

	    runner.start();
	}
    }

    private String load(Vector datas, String dir) throws Exception {
	synchronized (lock) {
	    String dirString = "";
	    try {
		//CashFileUploader uploader = new CashFileUploader("wcadmin");
		CashFileUploader uploader = new CashFileUploader(userId);
		EPMLoadData data = null;

		for (int i = 0; i < datas.size(); i++) {
		    data = (EPMLoadData) datas.get(i);

		    if (!(data.iswgm)) {
			File file0 = new File(dir + "\\" + data.fileName);

			if (!file0.exists()) {
			    continue;
			}

			uploader.addUploadFile(file0, false);
		    }
		}

		uploader.excute();

		String folder = uploader.getFolder();
		dirString = CachFileUpload.TEMPDIR + "/" + folder;

		return EPMLoadContext.load(userId, datas, dirString);
	    } catch (Exception e) {
		throw new Exception(e);
	    } finally {
		saveBtn.setEnabled(false);

		if ((dirString != null) && (dirString.trim().length() > 0)) {
		    boolean bRetval = loadctx.deleteLoadFile(dirString, userId);
		    System.out.println("load file delete : " + bRetval);
		}
	    }
	}
    }

    private EPMLoadData addRow(Cell[] cell, int rownum, String manageType, String bizOid, String devStage, String category, String cadAppType, String dir) throws Exception {
	EPMLoadData data = new EPMLoadData(cell, String.valueOf(rownum));

	if (data.m_moldPartNumber.trim().length() == 0) {
	    return null;
	}

	data.manageType = manageType;
	data.bizOid = bizOid;
	data.devStage = devStage;
	data.category = category;
	data.cadAppType = cadAppType;
	///////////////////////////////////////////////
	// Added by MJOH, 2011-03-17
	// 프로젝트 링크 추가 처리
	///////////////////////////////////////////////
	data.projectOid = projectOid;
	///////////////////////////////////////////////

	data.number = convNumber(data.m_moldPartNumber, category, cadAppType);
	data.name = data.m_moldPartDesc;

	if (data.isSkipRow) {
	    data.isValidate = true;
	}
	else {
	    if (data.isEmptyField) {
		data.isValidate = false;
	    }
	    else {
		data.fileName = getFileName(data.m_moldPartNumber, cadAppType);
		data.cadName = data.fileName;

		data.iswgm = isWGM(data.manageType, data.cadAppType);

		if (!(data.iswgm)) {
		    File chkFile = new File(dir + "\\" + data.fileName);

		    if (!chkFile.exists()) {
			data.isFileNotFound = true;
		    }
		}

		data = (EPMLoadData) EPMLoadContext.validate(data);

		if (data.iswgm) {
		    if (!(data.isExist)) {
			data.isValidate = false;
		    }
		}
		else {
		    if ((data.isExist)) {
			data.isValidate = false;
		    }

		    if ((data.isFileNotFound)) {
			data.isValidate = false;
		    }

		    if ((data.isCADName)) {
			data.isValidate = false;
		    }
		}

		if ((data.isAppTypeDiff)) {
		    data.isValidate = false;
		}
	    }
	}

	return data;
    }

    private String convNumber(String pnumber, String category, String cadAppType) throws Exception {
	if ((pnumber == null) || (pnumber.trim().length() == 0)) {
	    return "";
	}

	String dnumber = "";

	try {
	    /*
	    if("MOLD_DRAWING".equals(manageType)) {
	    	if("UG".equals(cadAppType)) {
	    		return pnumber+"_PRT";
	    	} else if("ACAD".equals(cadAppType)) {
	    		return pnumber+"_DWG";
	    	} else if("EXCESS".equals(cadAppType)) {
	    		return pnumber+"_PLS";
	    	}
	    }
	    */
	    dnumber = loadctx.convNumber(pnumber, category, cadAppType);
	    //return EDMProperties.getInstance().getConvertedNumber(pnumber, category, cadAppType);
	} catch (RemoteException e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	    throw new Exception(e);
	}

	return dnumber;
    }

    private String getFileName(String number, String cadAppType) {
	String fileName = number;

	if ("UG".equals(cadAppType.toUpperCase())) {
	    fileName += ".prt";
	}
	else if ("ACAD".equals(cadAppType.toUpperCase())) {
	    fileName += ".dwg";
	}
	else if ("EXCESS".equals(cadAppType.toUpperCase())) {
	    fileName += ".pls";
	}
	else {
	    fileName += "";
	}

	return fileName;
    }

    private boolean isWGM(String manageType, String cadAppType) {
	if ("MOLD_DRAWING".equals(manageType)) {
	    if ("UG".equals(cadAppType)) {
		return true;
	    }
	}

	return false;
    }

    public void showError(Exception ex, String message) {
	ex.printStackTrace();
	JOptionPane.showMessageDialog(this, message, APP_NAME, JOptionPane.WARNING_MESSAGE);
    }

    public String getToolTipMsg(EPMLoadData data) {
	if (data.isValidate || data.isSkipRow) {
	    return null;
	}

	StringBuffer sb = new StringBuffer();
	sb.append("<html>").append("*** " + KETMessageService.service.getString("e3ps.message.ket_message", "01275")/*도면번호*/+ " : <b>" + data.number + "</b>");

	if (data.isEmptyField) {
	    sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03415")/*도면등록에 필요한 입력값이 없습니다.*/);
	}
	else {
	    if (data.iswgm) {
		if (!(data.isExist)) {
		    sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03416")/*모델 데이터를 등록하시기 바랍니다.*/);
		}
	    }
	    else {
		if ((data.isExist)) {
		    sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03417")/*동일한 도면번호로 등록된 도면이 있습니다.*/);
		}

		if ((data.isFileNotFound)) {
		    sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03418")/*CAD 파일이 존재하지 않습니다.*/+ "(" + data.fileName + ")");
		}

		if ((data.isCADName)) {
		    sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03419")/*동일한 CAD 파일 명으로 등록된 도면이 있습니다.*/+ "(" + data.fileName + ")");
		}
	    }

	    if ((data.isAppTypeDiff)) {
		sb.append("<br>*** " + KETMessageService.service.getString("e3ps.message.ket_message", "03420")/*이미 등록된 도면의 CAD 종류가 다릅니다.*/);
	    }
	}

	sb.append("</html>");

	return sb.toString();
    }

    public void fileChooser() {
	JFileChooser chooser = new JFileChooser();

	if (currentDir != null) {
	    chooser.setCurrentDirectory(currentDir);
	}

	if (fileExtensions != null) {
	    chooser.addChoosableFileFilter(new EPMFileFilter(fileExtensions, "Excel Files"));
	    chooser.setAcceptAllFileFilterUsed(false);
	}

	chooser.setFileHidingEnabled(false);
	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	chooser.setMultiSelectionEnabled(false);
	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
	chooser.setDialogTitle("Search");

	if (chooser.showOpenDialog(EPMLoadContainer.this) == JFileChooser.APPROVE_OPTION) {
	    tempFile = chooser.getSelectedFile();
	    String currentFilePath = tempFile.toString();
	    String clientLocalDir = currentFilePath.substring(0, currentFilePath.lastIndexOf("\\"));
	    currentDir = new File(clientLocalDir);
	    excelFilePath.setText(currentFilePath);
	}
    }

    public void hideShowColumn() {
	for (int k = 0; k < m_data.getColumnCount(); k++) {
	    if (EPMTableModel.m_columns[k].m_isHideshow) {
		TableColumn column = columnModel.getColumnByModelIndex(k);
		boolean visible = columnModel.isColumnVisible(column);
		columnModel.setColumnVisible(column, !visible);
	    }
	}
    }

    private void quit() {
	//if (quitConfirmed(this)) {
	System.exit(0);
	//}
    }

    private boolean quitConfirmed(JFrame frame) {
	String s1 = KETMessageService.service.getString("e3ps.message.ket_message", "02656")/*종료*/;//"Quit";
	String s2 = KETMessageService.service.getString("e3ps.message.ket_message", "02887")/*취소*/;//"Cancel";
	Object[] options = { s1, s2 };

	int n = JOptionPane.showOptionDialog(frame, KETMessageService.service.getString("e3ps.message.ket_message", "03421")/*종료하시겠습니까?*/,
	        KETMessageService.service.getString("e3ps.message.ket_message", "03422")/*종료확인*/, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, s1);

	if (n == JOptionPane.YES_OPTION) {
	    return true;
	}
	else {
	    return false;
	}
    }


    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event dispatch thread.
     */
    private static void createAndShowGUI(String user, String type, boolean isdrm, String language) {
	//Use the Java look and feel.
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    /* UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); */
	    /* UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); */
	} catch (Exception e) {
	}

	//Make sure we have nice window decorations.
	JFrame.setDefaultLookAndFeelDecorated(true);
	JDialog.setDefaultLookAndFeelDecorated(true);

	//Create and set up the window.
	final JFrame frame = new JFrame(APP_NAME);
	//EPMLoadContainer frame = new EPMLoadContainer(user, type);
	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	EPMLoadContainer p = new EPMLoadContainer(user, type, isdrm, language);
	frame.setContentPane(p);

	frame.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});

	//Display the window.
	//frame.setLocation(300,150);
	frame.setSize(800, 600);
	frame.pack();
	frame.setLocationRelativeTo(null); //center it
	frame.setVisible(true);
	frame.setResizable(false);
    }

    public static void invokeLater(final String userId, final String type, final boolean isdrm, final String language) {
	//Schedule a job for the event dispatch thread:
	//creating and showing this application's GUI.

	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		try {
		    wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
		    methodServer.setAuthenticator(AuthHandler.getMethodAuthenticator(userId));

		    //Turn off metal's use of bold fonts
		    //UIManager.put("swing.boldMetal", Boolean.FALSE);
		    createAndShowGUI(userId, type, isdrm, language);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    //The standard main method.
    public static void main(String[] args) {
	String userId = "wcadmin";
	String type = "MOLD_DRAWING";
	String language = "ko";
	boolean isdrm = false;

	for (int i = 0; i < args.length; i++) {
	    String s1 = args[i];
	    //System.out.println("main s1 : " + s1);

	    if (s1.startsWith("userId")) {
		userId = s1.substring(s1.indexOf('=') + 1);
		continue;
	    }

	    if (s1.startsWith("manageType")) {
		type = s1.substring(s1.indexOf('=') + 1);
		continue;
	    }

	    if (s1.startsWith("drm")) {
		isdrm = Boolean.getBoolean(s1.substring(s1.indexOf('=') + 1));
		continue;
	    }

	    if (s1.startsWith("language")) {
		language = s1.substring(s1.indexOf('=') + 1);
		continue;
	    }
	}

	/*
		try {
			MethodAuthenticator ma = AuthHandler.getMethodAuthenticator(userId);

	    	wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
	    	methodServer.setAuthenticator(ma);

	    	//ma.setServer(methodServer);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}

	try {
			String str3 = Authentication.getUserName();
			System.out.println("user name : " + str3);

			WTUser user = (WTUser)SessionHelper.getPrincipal();
			System.out.println("user oid : " + user.getPersistInfo().getObjectIdentifier().getStringValue());

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (WTException e) {
			e.printStackTrace();
		}
		*/
	//ServiceProviderHelper.setRemoteClientFlag(true);

	invokeLater(userId, type, isdrm, language);
    }

    public class EDMItemListener implements ItemListener {
	public void itemStateChanged(ItemEvent ie) {
	    String s = (String) ie.getItem();
	    System.out.println(s);
	}
    }

    public class EPMButtonActionListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    Object eventObejct = event.getSource();
	    String actionCommand = event.getActionCommand();

	    if (bizCb == eventObejct) {

	    }
	    else if (dstCb == eventObejct) {
		EPMCodeData dstData = (EPMCodeData) dstCb.getModel().getSelectedItem();
		if (dstData != null) {
		    showCategory(dstData, true);
		}
	    }
	    else if (catCb == eventObejct) {
		EPMCodeData catData = (EPMCodeData) catCb.getModel().getSelectedItem();
		if (catData != null) {
		    showCADApp(catData, true);
		}
	    }
	    else if (searchFileBtn == eventObejct) {
		fileChooser();
	    }
	    else if (loadBtn == eventObejct) {
		EPMCodeData bizData = (EPMCodeData) bizCb.getModel().getSelectedItem();
		if ((bizData == null) || (bizData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01666")/*사업부를 선택하세요*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData dstData = (EPMCodeData) dstCb.getModel().getSelectedItem();
		if ((dstData == null) || (dstData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01265")/*도면구분을 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData catData = (EPMCodeData) catCb.getModel().getSelectedItem();
		if ((catData == null) || (catData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01290")/*도면유형을 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData cadAppData = (EPMCodeData) cadAppCb.getModel().getSelectedItem();
		if ((cadAppData == null) || (cadAppData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "00101")/*CAD 종류를 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		if ((excelFilePath.getText() == null) || (excelFilePath.getText().trim().length() == 0)) {
		    JOptionPane
			    .showMessageDialog(
			            EPMLoadContainer.this,
			            KETMessageService.service.getString("e3ps.message.ket_message", "02794")/*PartList*/+ " "
			                    + KETMessageService.service.getString("e3ps.message.ket_message", "03423")/*첨부파일을 입력하시기 바랍니다*/,
			            KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}
		/*
		openFile(manageType, bizData.m_oid, dstData.m_value, catData.m_value,
				cadAppData.m_value,
				excelFile);
		*/

		excelFile = tempFile;
		localDir = currentDir;
		//excelFile = new File(excelFilePath.getText());
		loadFile(manageType, bizData.m_oid, dstData.m_value, catData.m_value, cadAppData.m_value, excelFile, localDir.getAbsolutePath());

		//JOptionPane.showMessageDialog(EPMLoadContainer.this,
		//		"Load 완료 했습니다.", "확인",
		//		JOptionPane.INFORMATION_MESSAGE);
	    }
	    else if (refreshBtn == eventObejct) {
		JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "03424")/*Load 완료했습니다.*/,
		        KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.INFORMATION_MESSAGE);
	    }
	    else if (saveBtn == eventObejct) {
		EPMCodeData bizData = (EPMCodeData) bizCb.getModel().getSelectedItem();
		if ((bizData == null) || (bizData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01666")/*사업부를 선택하세요*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData dstData = (EPMCodeData) dstCb.getModel().getSelectedItem();
		if ((dstData == null) || (dstData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01265")/*도면구분을 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData catData = (EPMCodeData) catCb.getModel().getSelectedItem();
		if ((catData == null) || (catData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "01290")/*도면유형을 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		EPMCodeData cadAppData = (EPMCodeData) cadAppCb.getModel().getSelectedItem();
		if ((cadAppData == null) || (cadAppData.m_value.trim().length() == 0)) {
		    JOptionPane.showMessageDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "00101")/*CAD 종류를 선택하시기 바랍니다*/,
			    KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}

		if ((excelFilePath.getText() == null) || (excelFilePath.getText().trim().length() == 0)) {
		    JOptionPane
			    .showMessageDialog(
			            EPMLoadContainer.this,
			            KETMessageService.service.getString("e3ps.message.ket_message", "02794")/*PartList*/+ " "
			                    + KETMessageService.service.getString("e3ps.message.ket_message", "03423")/*첨부파일을 입력하시기 바랍니다*/,
			            KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.WARNING_MESSAGE);
		    return;
		}
		/*
		openFile(manageType, bizData.m_oid, dstData.m_value, catData.m_value,
				cadAppData.m_value,
				excelFile);
		*/

		excelFile = tempFile;
		localDir = currentDir;
		//excelFile = new File(excelFilePath.getText());
		doLoad(excelFile, localDir.getAbsolutePath());
	    }
	    else if (hideBtn == eventObejct) {
		hideShowColumn();

		hideBtn.setVisible(false);
		showBtn.setVisible(true);
	    }
	    else if (showBtn == eventObejct) {
		hideShowColumn();

		showBtn.setVisible(false);
		hideBtn.setVisible(true);
	    }
	    else if (closeBtn == eventObejct) {
		if (JOptionPane.showConfirmDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "03425")/*작업을 종료하시겠습니까?*/,
		        KETMessageService.service.getString("e3ps.message.ket_message", "03426")/*작업종료*/, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) quit();

		//System.exit(0);
	    }
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Added by MJOH, 2011-03-17
	    // 프로젝트 링크 추가 처리
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    else if (searchProjectBtn == eventObejct) {
		searchProjectInfo();
	    }
	    else if (deleteProjectBtn == eventObejct) {
		if (JOptionPane.showConfirmDialog(EPMLoadContainer.this, KETMessageService.service.getString("e3ps.message.ket_message", "03427")/*프로젝트번호와 프로젝트명을 삭제하시겠습니까?*/
		, KETMessageService.service.getString("e3ps.message.ket_message", "03428")/*프로젝트 정보 삭제*/
		, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    projectNoTf.setText("");
		    projectNameTf.setText("");
		    projectOid = "";
		}
	    }
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
    }

    private void searchProjectInfo() {
	EPMCodeData bizData = (EPMCodeData) bizCb.getModel().getSelectedItem();

	EDMProjectSearchDialog searchProjectDlg = new EDMProjectSearchDialog(bizData.getValue(), KETMessageService.service.getString("e3ps.message.ket_message", "03059")/*프로젝트 검색*/, userId, language);

	try {
	    if (searchProjectDlg.getOK() == true) {
		String aryResult[] = new String[6];
		aryResult = searchProjectDlg.getSelectedColumnData();

		projectNoTf.setText(aryResult[0]);
		projectNameTf.setText(aryResult[1]);
		projectOid = aryResult[5];
	    }
	} catch (Throwable ex) {
	    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    ex.printStackTrace();
	}
    }

    public ImageIcon getImageIcon(String s) {
	ImageIcon imageicon = null;
	java.net.URL url = null;

	if (s == null) return null;

	url = getClass().getResource(s);

	if (url != null) imageicon = new ImageIcon(url);

	return imageicon;
    }

    // NEW
    class ColumnListener extends MouseAdapter {
	public void mouseClicked(MouseEvent e) {
	    TableColumnModel colModel = m_table.getColumnModel();
	    int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
	    int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();

	    if (modelIndex < 0) {
		return;
	    }

	    if (m_data.m_sortCol == modelIndex) {
		m_data.m_sortAsc = !m_data.m_sortAsc;
	    }
	    else {
		m_data.m_sortCol = modelIndex;
	    }

	    for (int i = 0; i < m_data.getColumnCount(); i++) {
		TableColumn column = colModel.getColumn(i);
		int index = column.getModelIndex();
		JLabel renderer = (JLabel) column.getHeaderRenderer();
		renderer.setIcon(m_data.getColumnIcon(index));
	    }

	    m_table.getTableHeader().repaint();

	    m_data.sortData();
	    m_table.tableChanged(new TableModelEvent(m_data));
	    m_table.repaint();
	}
    }
}
