package e3ps.bom.command.searchappliedproduct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.searchitem.SearchBOMPanel;
import e3ps.bom.command.updatebom.BOMChildItemSearchDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.TableSorter;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.SearchAppliedProductDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class SearchAppliedProductDialog extends AbstractAIFDialog
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    String orgCode;
    private BtnListener btnListener;
    private JTable m_table;
    private SearchAppliedProductModel m_data;
    private TableSorter sorter;
    private Vector resultList;
    private Vector columnNameVec;
    boolean keyCheck = false;

    private AbstractAIFUIApplication app;
    private JFrame desktop;

    private JLabel resultLbl, resultValLbl;
    private JTextField itemCodeTfl, descTfl;
    private JButton searchBtn, searchAppliedProductBtn, viewBOMBtn, closeBtn;

    String itemCodeStr = "";

     private int intRowCount = -1;

    class BtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Search")) {
                searchData();
            } else if (e.getActionCommand().equals("SearchAppliedProducts")) {
                searchAppliedProductData();

            } else if (e.getActionCommand().equals("ViewBOM")) {
                viewBOM();

            } else if (e.getActionCommand().equals("Close")) {
                disposeScreen();
            }
        }
    }

    public SearchAppliedProductDialog(JFrame frame, AbstractAIFUIApplication app) {

        //super(frame, "적용 제품 검색", true);
        super(frame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00312")/*적용 제품 검색*/);
        this.desktop = frame;
        this.app = app;
        this.orgCode = BOMBasicInfoPool.getOrgCode();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disposeScreen();
            }
        });
        validate();
        try {
            jInit();
            setSize(650, 410);
            setResizable(false);

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);

        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void jInit() throws Exception {
        try {
            Registry appReg = Registry.getRegistry(app);

            btnListener = new BtnListener();

            // Search Condition Panel
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
            topPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00312")/*적용 제품 검색*/ + " ", 0, 0, FontList.defaultFont));
            topPanel.setFont(FontList.defaultFont);

            JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel itemCodeLbl = new JLabel("     " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : ");
            itemCodeLbl.setFont(FontList.defaultFont);
            panel01.add(itemCodeLbl);

            itemCodeTfl = new JTextField(itemCodeStr, 15);
            itemCodeTfl.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchData();
                }
            });

            itemCodeTfl.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent keyevent) {
                    char c = keyevent.getKeyChar();
                    int cCode = (int)c;

                    if (cCode >= 97 && cCode <= 122) {
                        keyCheck = true;
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
            panel01.add(itemCodeTfl);

            searchBtn = new JButton(appReg.getImageIcon("searchIcon"));            // item Code 옆의 돗보기 버튼..
            searchBtn.setActionCommand("Search");
            searchBtn.setBorder(BorderList.emptyBorder1);
            searchBtn.addActionListener(btnListener);
            searchBtn.setMargin(new Insets(0, 5, 0, 5));
            panel01.add(searchBtn);

            topPanel.add(panel01);

            JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel descLbl = new JLabel("         " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/ + " : ");
            descLbl.setFont(FontList.defaultFont);
            descLbl.setEnabled(false);
            panel02.add(descLbl);

            descTfl = new JTextField(27);
            descTfl.setEnabled(false);
            panel02.add(descTfl);

            searchAppliedProductBtn = new JButton(" " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00312")/*적용 제품 검색*/, appReg.getImageIcon("searchappliedproductIcon"));
            searchAppliedProductBtn.setFont(FontList.defaultFont);
            searchAppliedProductBtn.setActionCommand("SearchAppliedProducts");
            searchAppliedProductBtn.addActionListener(btnListener);
            searchAppliedProductBtn.setMargin(new Insets(0, 5, 0, 5));
            panel02.add(searchAppliedProductBtn);

            topPanel.add(panel02);

            JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            resultLbl = new JLabel("" + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00054")/*검색결과*/ + " : ");
            resultLbl.setFont(FontList.defaultFont);
            panel03.add(resultLbl);

            resultValLbl = new JLabel("0");
            resultValLbl.setFont(FontList.defaultFont);
            resultValLbl.setForeground(Color.red);
            panel03.add(resultValLbl);

            JLabel result2Lbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00051")/*개*/);
            result2Lbl.setFont(FontList.defaultFont);
            panel03.add(result2Lbl);

            topPanel.add(panel03);

            this.getContentPane().add(topPanel, BorderLayout.NORTH);

            // Search Result Panel
            resultList = new Vector();
            resultValLbl.setText(String.valueOf(resultList.size()));

            columnNameVec = new Vector();
            for (int k = 0; k < SearchAppliedProductModel.m_columns.length; k++) {
                columnNameVec.addElement(SearchAppliedProductModel.m_columns[k].m_title);
            }

            m_table = new JTable();
            m_data = new SearchAppliedProductModel(resultList, columnNameVec);
            sorter = new TableSorter(m_data);
            sorter.addMouseListenerToHeaderInTable(m_table); // ADDED THIS

            m_table.setModel(sorter);
            m_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            m_table.setAutoCreateColumnsFromModel(false);
            m_table.getTableHeader().setReorderingAllowed(false);
            m_table.repaint();

            for (int k = 0; k < SearchAppliedProductModel.m_columns.length; k++) {
                SearchAppliedProductCellRenderer searchItemRenderer = new SearchAppliedProductCellRenderer();
                searchItemRenderer.setHorizontalAlignment(SearchAppliedProductModel.m_columns[k].m_alignment);
                TableCellRenderer renderer = searchItemRenderer;

                TableColumnModel columnModel = m_table.getColumnModel();
                TableColumn column = columnModel.getColumn(k);

                column.setCellRenderer(renderer);
                column.setPreferredWidth(SearchAppliedProductModel.m_columns[k].m_width);
            }

            TableColumnModel columnModel = m_table.getColumnModel();

            TableColumn column0 = columnModel.getColumn(7);
            column0.setWidth(0);
            column0.setMinWidth(0);
            column0.setMaxWidth(0);
            column0.setResizable(false);

            //shin....숨김처리용..
            /*for (int i=1; i<6; i++) {
                if (i == 3 || i == 4) continue;
                TableColumn column0 = columnModel.getColumn(columnModel.getColumnCount() - i);
                column0.setWidth(0);
                column0.setMinWidth(0);
                column0.setMaxWidth(0);
                column0.setResizable(false);
            }*/

            JScrollPane ps = new JScrollPane();
            ps.setSize(500, 330);
            ps.getViewport().add(m_table);

            m_table.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    if (e.getClickCount() == 2)
                    {
                    viewBOM();
                    }
                }
            });
            this.getContentPane().add(ps, BorderLayout.CENTER);

            // Button Panel
            JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            viewBOMBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00031")/*BOM 조회*/, appReg.getImageIcon("bomdetailsIcon"));
            viewBOMBtn.setFont(FontList.defaultFont);
            viewBOMBtn.setActionCommand("ViewBOM");
            viewBOMBtn.addActionListener(btnListener);
            viewBOMBtn.setMargin(new Insets(0, 5, 0, 5));
            btnFlowPanel.add(viewBOMBtn);

            closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
            closeBtn.setFont(FontList.defaultFont);
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(btnListener);
            closeBtn.setMargin(new Insets(0, 5, 0, 5));
            btnFlowPanel.add(closeBtn);

            this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);

        } catch (Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    private void searchData() {
        BOMChildItemSearchDialog bomItemSearchDialog = new BOMChildItemSearchDialog(desktop, app, itemCodeTfl.getText(), "searchItem");
        String[] itemArray = bomItemSearchDialog.getSelectedColumnData();

        if (itemArray[0] != null && itemArray[0].length() > 0) {
            itemCodeTfl.setText(itemArray[0]);
            descTfl.setText(itemArray[1]);
        }
    }

    private void searchAppliedProductData() {
        try {
            if (itemCodeTfl.getText() == null || itemCodeTfl.getText().trim().equals("")) {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("searchApplied"), "Input Error", 2);
                mbox.setModal(true);
                mbox.setVisible(true);

            } else if (descTfl.getText() == null || descTfl.getText().trim().equals("")) {
                MessageBox mbox = new MessageBox(desktop, messageRegistry.getString("searchApplied1"), "Input Error", 2);
                mbox.setModal(true);
                mbox.setVisible(true);

            } else {
                SearchAppliedProductDao searchAppliedProduct = new SearchAppliedProductDao();
                Vector resultVec = searchAppliedProduct.searchAppliedProduct(itemCodeTfl.getText(), descTfl.getText(), orgCode);

                Vector productVec = (Vector)resultVec.elementAt(0);
                Vector parentItemVec = (Vector)resultVec.elementAt(1);

                resultList = new Vector();

                if (productVec != null && productVec.size() > 0 && parentItemVec != null && parentItemVec.size() > 0) {
                    Hashtable productHash = null;
                    Hashtable parentItemHash = null;

                    for (int i=0; i<parentItemVec.size(); i++) {
                        productHash = (Hashtable)productVec.elementAt(i);
                        parentItemHash = (Hashtable)parentItemVec.elementAt(i);

                        SearchAppliedProductData appliedProductData = new SearchAppliedProductData();
                        appliedProductData.setNoStr(String.valueOf(i+1));
                        appliedProductData.setProductStr((String)productHash.get("itemCode"));
                        appliedProductData.setParentItemCodeStr((String)parentItemHash.get("itemCode"));
                        appliedProductData.setDescriptionStr((String)parentItemHash.get("description"));
                        appliedProductData.setStatusKrStr((String)parentItemHash.get("statusKr"));
                        appliedProductData.setQuantityDbl( (Double)parentItemHash.get("quantity") );
                        appliedProductData.setUserItemTypeStr((String)parentItemHash.get("uit"));
                        appliedProductData.setStatusStr((String)parentItemHash.get("status"));

                        resultList.addElement(appliedProductData);
                    }
                }

                resultValLbl.setText(String.valueOf(resultList.size()));

                m_data = new SearchAppliedProductModel(resultList, new Vector());
                sorter = new TableSorter(m_data);
                sorter.addMouseListenerToHeaderInTable(m_table);

                m_table.setModel(sorter);
                m_table.repaint();
                repaint();
            }
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    private void viewBOM()
    {
        String itemStr = "";
        String itemDescStr = "";
        String resultListStr = "";
        Vector versionItemVec = new Vector();
        Vector version = new Vector();
        Hashtable inputParams = new Hashtable();

        try
        {
            if(m_table.getSelectedRow() == -1)
            {
                MessageBox mbox = new MessageBox(this.desktop, messageRegistry.getString("selectRow12"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
Kogger.debug(getClass(), "------------- 정전개 ------------------");

                if(isBomChildExist())
                {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    viewBOMBtn.setEnabled(false);
                    m_table.setEnabled(false);

                    Vector vecOpenBom = new Vector();
                    vecOpenBom.removeAllElements();

                    itemStr = m_table.getValueAt(m_table.getSelectedRow(), 1) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 1).toString().trim();
                    itemDescStr = m_table.getValueAt(m_table.getSelectedRow(), 3) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 3).toString().trim();

                    BOMSearchDao dao = new BOMSearchDao();
                    // 정전개 조회 할 버전을 가져온다
//                    String strVer = dao.getItemVersionList(itemStr);
                    // 정전개 조회
//                    dao.downwardExplosionCurrentBom(itemStr, "", strVer);
                    dao.downwardExplosionCurrentBom(itemStr, "");
                    vecOpenBom = dao.getResultListVec();

                    BOMAssyComponent rootComponent = new BOMAssyComponent(itemStr, true);
                    rootComponent.setLevelInt(new Integer(1));
                    rootComponent.setSeqInt(new Integer(1));
                    rootComponent.setOpSeqInt(new Integer(1));
                    rootComponent.setItemSeqInt(new Integer(10));
                    rootComponent.setDescStr(itemDescStr);
                    rootComponent.setSortOrderStr("0001");
                    rootComponent.setUitStr(m_table.getValueAt(m_table.getSelectedRow(), 6) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 6).toString().trim());
                    rootComponent.setStatusKrStr(m_table.getValueAt(m_table.getSelectedRow(), 4) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 4).toString().trim());
                    rootComponent.setStatusStr(m_table.getValueAt(m_table.getSelectedRow(), 7) == null ? "" : m_table.getValueAt(m_table.getSelectedRow(), 7).toString().trim());

                    versionItemVec.addElement( itemStr);
                    inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
                    inputParams.put("itemCode", versionItemVec);

                    version = KETBomHelper.service.getItemVersion(inputParams);
                    String versionStr = "";

                    for(int k=0; k<version.size(); k++)
                    {
                        if(version.size() > 0)
                        {
                            versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                            if(( itemStr).equals(versionStr.substring(0, versionStr.indexOf("@"))))
                            {
                                rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("(")));
                            }
                        }
                    }

                    setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00312")/*적용 제품 검색*/);

                    SearchBOMPanel searchBom = new SearchBOMPanel(desktop, app, (BOMRegisterApplicationPanel)app.getApplicationPanel(), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/, rootComponent, vecOpenBom, rootComponent.getVersionStr().trim());

                    viewBOMBtn.setEnabled(true);
                    m_table.setEnabled(true);

                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                else
                {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox mbox = new MessageBox(this.desktop, messageRegistry.getString("searchItem1"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                }
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public boolean isBomChildExist()
    {
        Registry appReg = Registry.getRegistry(app);
        boolean isBomChildFlag = false;
        String itemCodeStr = "";
        intRowCount = m_table.getSelectedRow();
        itemCodeStr = (String)m_table.getValueAt(intRowCount, 1);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        DBConnectionManager resource = null;
        Connection connection = null;
        try
        {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.isBomChildExist(connection, itemCodeStr);

            long resultCount = dao.getDataCount();

            if(resultCount > 0)
            {
                isBomChildFlag = true;
            }
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);

            MessageBox mbox = new MessageBox(this.desktop, "DB Error : " + ex.toString(), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        finally
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if(resource != null)
            {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
        return isBomChildFlag;
    }


    private void disposeScreen() {
        int count = getComponentCount();
        for (int i = 0; i < count; i++) {
            Component c = getComponent(i);
            this.remove(c);
            c = null;
        }
        super.dispose();
        System.gc();
    }
}
