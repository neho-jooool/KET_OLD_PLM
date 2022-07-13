package e3ps.bom.command.bomproperty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.NumberFormat;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import e3ps.bom.command.updatebom.BOMChildItemSearchDialog;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class SubstitutePropertyDialog extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private JFrame desktop;
    private BOMAssyComponent assyComponent;
    public TotalPropertyDialog parent;
    private SubAssyTableModel m_data;
    BtnListener btnListener;

    JPanel tablePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel propertyPanel = new JPanel();
    JScrollPane sp = new JScrollPane();
    JTable subAssyTable = new JTable();

    JLabel itemCodeLbl, descriptionLbl, uitLbl, statusLbl; //, quantityLbl, supplyTypeLbl, startDateLbl, endDateLbl;
    JTextField itemCodeTfl, descriptionTfl, uitTfl, uitDisplayTfl, statusTfl; //, quantityTfl;
    JButton removeBtn, addBtn, searchBtn;

    public boolean isChanged = false;
    private boolean isView = true;

    Registry appReg;

    public SubstitutePropertyDialog(BOMAssyComponent component, Vector subAssyComponent, JFrame desktop, boolean isView, TotalPropertyDialog parent, AbstractAIFUIApplication app) {
        this.assyComponent = component;
        this.desktop = desktop;
        this.isView = isView;
        this.parent = parent;
        this.app = app;

        appReg = Registry.getRegistry(app);

        try {
            jInit();
            setComponentData();
        } catch(Exception ex) {
            Kogger.error(getClass(), ex);
        }
    }

    class TableSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();

            if (lsm.isSelectionEmpty()) {

            } else {
                BOMSubAssyComponent component = m_data.getComponent(subAssyTable.getSelectedRow());

                itemCodeTfl.setText(component.getSubstituteItemCodeStr().trim());
                descriptionTfl.setText(component.getDescStr().trim());
                uitTfl.setText(component.getUitStr().trim());
                uitDisplayTfl.setText(component.getUitDisplayStr().trim());
                statusTfl.setText(component.getStatusKrStr().trim());

                NumberFormat format = NumberFormat.getInstance();
                format.setMinimumFractionDigits(5);

                subAssyTable.tableChanged(new TableModelEvent( m_data, subAssyTable.getSelectedRow(), subAssyTable.getSelectedRow(), TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
                subAssyTable.repaint();
            }
        }
    }

    class BtnListener implements ActionListener {
        private SubstitutePropertyDialog dialog;

        public BtnListener(SubstitutePropertyDialog dialog) {
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Search")) {
                // 입력된 값이 들어있는 모든 BOM List 를 찾아서 선택하도록 한다.
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                allSearchOperation();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            } else if (e.getActionCommand().equals("Add")) {
                if (!isValidate("Add"))
                    return;

                if (assyComponent.getItemCodeStr().trim().equals(itemCodeTfl.getText())) {
                    MessageBox.post(desktop, messageRegistry.getString("sameItem"), "Error", 0);
                    return;
                }

                int row = subAssyTable.getRowCount() -1;
                BOMSubAssyComponent component = new BOMSubAssyComponent();

                component.setSubstituteItemCodeStr(itemCodeTfl.getText());
                component.setDescStr(descriptionTfl.getText().trim());
                component.setUitStr(uitTfl.getText().trim());
                component.setUitDisplayStr(uitDisplayTfl.getText().trim());
                component.setStatusKrStr(statusTfl.getText().trim());
                component.setParentItemCodeStr(assyComponent.getParentItemCodeStr().trim());
                component.setChildItemCodeStr(assyComponent.toString().trim());
                component.setSortOrderStr(assyComponent.getSortOrderStr());

                m_data.insert(component);
                subAssyTable.tableChanged(new TableModelEvent(m_data, row+1, row+1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
                subAssyTable.repaint();
                subAssyTable.requestFocus();
            } else if (e.getActionCommand().equals("Remove")) {
                int rowCount = subAssyTable.getSelectedRowCount();
                if (rowCount == 0) {
                    MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow1"), "Error", 0);
                    m.setModal(true);
                    m.setVisible(true);
                } else    {
                    int []rows = subAssyTable.getSelectedRows();
                    for(int i=rows.length-1; i>=0; i--) {
                        if (m_data.delete(rows[i])) {
                            subAssyTable.tableChanged(new TableModelEvent(m_data, rows[i], rows[i], TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
                            subAssyTable.repaint();
                        }
                    }
                }
            } else    {}
        }
    }

    private boolean isValidate(String code) {
        DBConnectionManager resource = null;
        Connection connection = null;

        try {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.existItemSearch(connection, itemCodeTfl.getText().trim());

            if(dao.getDataCount() != 1) {
                MessageBox m = new MessageBox(desktop, messageRegistry.getString("avaItem"), "Error", 0);
                m.setModal(true);
                m.setVisible(true);
                return false;
            } else {
                KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
                BOMAssyComponent tmpComp = dao.getResultListComponent();
                descriptionTfl.setText(tmpComp.getDescStr());
                uitTfl.setText(tmpComp.getUitStr());
                uitDisplayTfl.setText(bean.getUnitDisplayValue(tmpComp.getUitStr()));
            }

            if (code.equalsIgnoreCase("Add")) {
                for (int i = 0; i < m_data.getRowCount(); i++) {
                    if (itemCodeTfl.getText().trim().equalsIgnoreCase(m_data.getComponent(i).toString().trim())) {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("existItem"), "Error", 0);
                        m.setModal(true);
                        m.setVisible(true);
                        return false;
                    }
                }
            } else if (code.equalsIgnoreCase("Update")) {
                for (int i = 0; i < m_data.getRowCount(); i++)    {
                    if (i != subAssyTable.getSelectedRow() && itemCodeTfl.getText().trim().equalsIgnoreCase(m_data.getComponent(i).toString().trim())) {
                        MessageBox m = new MessageBox(desktop, messageRegistry.getString("existItem"), "Error", 0);
                        m.setModal(true);
                        m.setVisible(true);
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            return false;
        } finally {
            if(resource != null) {
                resource.freeConnection(appReg.getString("plm"), connection);
            }
        }
    }

    public void isChanged() {
        isChanged = true;
    }

    private void setComponentData() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(5);

        Vector subAssyVec = assyComponent.getSubAssyComponent();
        if (subAssyVec == null || subAssyVec.size() == 0) {

        } else    {
            for (int i = 0; i < subAssyVec.size(); i++) {
                m_data.insert((BOMSubAssyComponent)subAssyVec.elementAt(i));
            }
        }
        itemCodeTfl.requestFocus();
    }

    private void allSearchOperation() {

        String partStr = itemCodeTfl.getText().trim();
        // [2011-03-09] 윤도혁J 요구사항 반영 :: 검색조건 미입력 시에도 검색 팝업 화면이 Display 되도록 변경
//        if(partStr == null || partStr.length() == 0) {
//            MessageBox m = new MessageBox(desktop, messageRegistry.getString("input1"), "Error", 0);
//            m.setModal(true);
//            m.setVisible(true);
//            return;
//        }

        try {
            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
            BOMChildItemSearchDialog dlg = new BOMChildItemSearchDialog(desktop, app, partStr, "Sub");
            if(dlg.getOK() == true)    {
                String aryResult [] = new String[8];    // 부품번호, 부품명, 기본단위, 작성자, 작성일자, 결재상태(영어), 결재상태(한글), ?
                aryResult = dlg.getSelectedColumnData();

                itemCodeTfl.setText(aryResult[0]);
                descriptionTfl.setText(aryResult[1]);
                if (aryResult[4] != null && !aryResult[4].equals("") && aryResult[4].equals("%")){
                    uitTfl.setText("KET_PERCENT");
                } else {
                    uitTfl.setText("KET_" + aryResult[4]);
                }
                uitDisplayTfl.setText(bean.getUnitDisplayValue("KET_" + aryResult[4]));
                statusTfl.setText(aryResult[6]);
            }
        } catch(Exception ex){
            Kogger.error(getClass(), ex);
        }
    }

    void jInit() throws Exception {
        this.setLayout(new BorderLayout());

        btnListener = new BtnListener(this);

        //Table Panel
        m_data = new SubAssyTableModel(parent);
        subAssyTable.setAutoCreateColumnsFromModel(false);
        subAssyTable.setModel(m_data);
        subAssyTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        subAssyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int k = 0; k < SubAssyTableModel.cNames.length; k++) {
            TableColumn column = new TableColumn(k);
            subAssyTable.addColumn(column);
        }

        subAssyTable.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        subAssyTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/).setPreferredWidth(150);
        subAssyTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00132")/*대체부품명*/).setPreferredWidth(230);
        subAssyTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/).setPreferredWidth(100);
        subAssyTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/).setPreferredWidth(100);

        sp.getViewport().add(subAssyTable, null);
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00129")/*대체부품*/, 0, 0, FontList.defaultFont));
        tablePanel.setMinimumSize(new Dimension(40, 400));
        tablePanel.setPreferredSize(new Dimension(40, 100));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(sp, null);

        // 대체부품 속성정보 Component
        JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemCodeLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/ + " : ");
        itemCodeLbl.setPreferredSize(new Dimension(130,21));
        itemCodeLbl.setHorizontalAlignment(JLabel.RIGHT);
        itemCodeLbl.setFont(FontList.defaultFont);
        if(isView) {
            itemCodeLbl.setEnabled(false);
        }
        panel01.add(itemCodeLbl);

        itemCodeTfl = new JTextField();
//        itemCodeTfl = new JTextField() {
//            private static final long serialVersionUID = 1L;
//
//            public void paint(Graphics g) {
//                super.paint(g);
//                Painter.paintIsRequired(this, g);
//            };
//        };
        itemCodeTfl.setPreferredSize(new Dimension(150,21));
        if(isView) {
            itemCodeTfl.setEnabled(false);
        }
        itemCodeTfl.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                allSearchOperation();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        itemCodeTfl.addKeyListener(new KeyAdapter() {
            boolean keyCheck = false;
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

                    String s = tfl.getText();
                    tfl.setText(s.toUpperCase());
                    tfl.setCaretPosition(pos);
                    keyCheck = false;
                }
            }
        });
        panel01.add(itemCodeTfl);

        searchBtn = new JButton(appReg.getImageIcon("searchIcon"));
        searchBtn.setBorder(BorderList.emptyBorder1);
        searchBtn.setMargin(new Insets(0, 5, 0, 5));
        searchBtn.setPreferredSize(new Dimension(20,21));
        searchBtn.setActionCommand("Search");
        searchBtn.addActionListener(btnListener);
        if(isView) {
            searchBtn.setEnabled(false);
        }
        panel01.add(searchBtn);


        JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00132")/*대체부품명*/ + " : ");
        descriptionLbl.setPreferredSize(new Dimension(130,21));
        descriptionLbl.setHorizontalAlignment(JLabel.RIGHT);
        descriptionLbl.setFont(FontList.defaultFont);
        descriptionLbl.setEnabled(false);
        panel02.add(descriptionLbl);

        descriptionTfl = new JTextField();
        descriptionTfl.setPreferredSize(new Dimension(410,21));
        descriptionTfl.setFont(FontList.defaultFont);
        descriptionTfl.setEnabled(false);
        panel02.add(descriptionTfl);


        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uitLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/ + " : ");
        uitLbl.setPreferredSize(new Dimension(130,21));
        uitLbl.setHorizontalAlignment(JLabel.RIGHT);
        uitLbl.setFont(FontList.defaultFont);
        uitLbl.setEnabled(false);
        panel03.add(uitLbl);

        uitTfl = new JTextField();
        uitTfl.setPreferredSize(new Dimension(150,21));
        uitTfl.setFont(FontList.defaultFont);
        uitTfl.setVisible(false);

        uitDisplayTfl = new JTextField();
        uitDisplayTfl.setPreferredSize(new Dimension(150,21));
        uitDisplayTfl.setFont(FontList.defaultFont);
        uitDisplayTfl.setEnabled(false);
        panel03.add(uitDisplayTfl);


        statusLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/ + " : ");
        statusLbl.setPreferredSize(new Dimension(100,21));
        statusLbl.setHorizontalAlignment(JLabel.RIGHT);
        statusLbl.setFont(FontList.defaultFont);
        statusLbl.setEnabled(false);
        panel03.add(statusLbl);

        statusTfl = new JTextField();
        statusTfl.setPreferredSize(new Dimension(150,21));
        statusLbl.setFont(FontList.defaultFont);
        statusTfl.setEnabled(false);
        panel03.add(statusTfl);

        propertyPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00130")/*대체부품 속성*/, 0, 0, FontList.defaultFont));
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
//        propertyPanel.setLayout(null);

        propertyPanel.add(panel01);
        propertyPanel.add(panel02);
        propertyPanel.add(panel03);


        //button panel
        addBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00339")/*추가*/, appReg.getImageIcon("addIcon"));
        addBtn.setMargin(new Insets(0, 3, 0, 3));
        addBtn.setFont(FontList.defaultFont);
        addBtn.setActionCommand("Add");
        addBtn.addActionListener(btnListener);
        if(isView) {
            addBtn.setEnabled(false);
        }

//        updateBtn = new JButton("Update", appReg.getImageIcon("updateIcon"));
//        updateBtn.setMargin(new Insets(0, 3, 0, 3));
//        updateBtn.setFont(FontList.defaultFont);
//        updateBtn.setActionCommand("Update");
//        updateBtn.addActionListener(btnListener);
//        if(isView) {
//            updateBtn.setEnabled(false);
//        }

        removeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00210")/*삭제*/, appReg.getImageIcon("removeIcon"));
        removeBtn.setMargin(new Insets(0, 3, 0, 3));
        removeBtn.setFont(FontList.defaultFont);
        removeBtn.setActionCommand("Remove");
        removeBtn.addActionListener(btnListener);
        if(isView) {
            removeBtn.setEnabled(false);
        }

        buttonPanel.add(addBtn, null);
//        buttonPanel.add(updateBtn, null);
        buttonPanel.add(removeBtn, null);

        this.add(tablePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(propertyPanel, BorderLayout.CENTER);
    }

    public boolean setOK() {
        Vector newSubAssyVector = new Vector();
        try {
            for(int i=0; i<m_data.getRowCount(); i++) {
                newSubAssyVector.addElement(m_data.getComponent(i));
            }
            assyComponent.setSubAssyComponent(newSubAssyVector);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
            return false;
        }
        return true;
    }
}

//////////////////////////////////////////////////////////////////////////
// Table Model
class SubAssyTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    protected static String[] cNames =  new String[4];
    public void tableModel() {
        cNames[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/;
        cNames[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00132")/*대체부품명*/;
        cNames[2] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/;
        cNames[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/;
    }

    public static final int COL_ITEM = 0;
    public static final int COL_DESC = 1;
    public static final int COL_UIT = 2;
    public static final int COL_STATUS = 3;

    private TotalPropertyDialog parent;

    protected Vector m_vector;
    NumberFormat format;

    public SubAssyTableModel(TotalPropertyDialog parent) {
        tableModel();
        m_vector = new Vector();
        format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(5);
        this.parent = parent;
    }

    public int getRowCount() {
        return m_vector==null ? 0 : m_vector.size();
    }

    public int getColumnCount() {
        return cNames.length;
    }

    public String getColumnName(int column)    {
        return cNames[column];
    }

    public boolean isCellEditable(int nRow, int nCol) {
        return false;
    }

    public BOMSubAssyComponent getComponent(int nRow) {
        if (nRow < 0 || nRow>=getRowCount())
            return null;
        return (BOMSubAssyComponent)m_vector.elementAt(nRow);
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return "";

        BOMSubAssyComponent subAssy = (BOMSubAssyComponent)m_vector.elementAt(nRow);
        if (subAssy == null)
            return "";

        switch (nCol)     {
            case COL_ITEM: return subAssy.getSubstituteItemCodeStr();
            case COL_DESC: return subAssy.getDescStr();
            case COL_UIT: return subAssy.getUitDisplayStr();
            case COL_STATUS: return subAssy.getStatusKrStr();
        }
        return "";
    }

    public void setValueAt(Object value, int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return;
        BOMSubAssyComponent subAssy = (BOMSubAssyComponent)m_vector.elementAt(nRow);
        String svalue = value.toString().trim();

        switch (nCol)     {
            case COL_ITEM:
                subAssy.setSubstituteItemCodeStr(svalue);
                break;
            case COL_DESC:
                subAssy.setDescStr(svalue);
                break;
            case COL_UIT:
                subAssy.setUitDisplayStr(svalue);
                break;
            case COL_STATUS:
//                subAssy.setStatusStr(svalue);
                subAssy.setStatusKrStr(svalue);
                break;
        }
    }

    public void insert(BOMSubAssyComponent component)     {
        m_vector.addElement(component);
    }

    public boolean delete(int row) {
        if (row < 0 || row >= m_vector.size())
            return false;
        m_vector.remove(row);
            return true;
    }
}
