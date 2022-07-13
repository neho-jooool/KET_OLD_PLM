package e3ps.bom.command.multiplebomeco;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;

public class ParentItemTableData extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    static final public ParentItemColumnData parentItemColumnData[] = {
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00150")/*모부품*/, 120, JLabel.CENTER),
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 200, JLabel.LEFT),
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/, 50, JLabel.CENTER),
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 70, JLabel.CENTER),
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/, 50, JLabel.CENTER),
            new ParentItemColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/, 70, JLabel.CENTER),
            new ParentItemColumnData("Oid", 50, JLabel.LEFT)
    };

    protected Vector vecParentItemData;

    public ParentItemTableData(Vector data) {
        vecParentItemData = new Vector();
        vecParentItemData.removeAllElements();
        vecParentItemData = data;
    }

    public int getRowCount() {
        return vecParentItemData == null ? 0 : vecParentItemData.size();
    }

    public int getColumnCount() {
        return parentItemColumnData.length;
    }

    public String getColumnName(int column) {
        return parentItemColumnData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol) {
            return false;
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        ParentItemData row = (ParentItemData)vecParentItemData.elementAt(nRow);
        KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

        switch (nCol) {
        case 0:
            return row.strParentItem;
        case 1:
            return row.strDescription;
        case 2:
            return row.strVersion;
        case 3:
            return row.strState;
        case 4:
            return row.strQuantity;
        case 5:
            return bean.getUnitDisplayValue(row.strUnit);
        case 6:
            return row.strOid;
        }
        return "";
    }

    public void setValueAt(Object aValue, int nRow, int nCol) {
        ParentItemData row = (ParentItemData)vecParentItemData.elementAt(nRow);

        switch (nCol) {
        case 0:
            row.strParentItem = (String)aValue;
            break;
        case 1:
            row.strDescription = (String)aValue;
            break;
        case 2:
            row.strVersion = (String)aValue;
            break;
        case 3:
            row.strState = (String)aValue;
            break;
        case 4:
            row.strQuantity = (String)aValue;
            break;
        case 5:
            row.strUnit = (String)aValue;
            break;
        case 6:
            row.strOid = (String)aValue;
            break;
        }

        vecParentItemData.setElementAt(row, nRow);
    }

    public int getColWidth(int column) {
        return parentItemColumnData[column].intWidth;
    }

    public int getColAlignment(int column) {
        return parentItemColumnData[column].intAlignment;
    }
}
