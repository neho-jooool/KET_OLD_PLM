package e3ps.bom.command.mybom;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class BOMTableData extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    static final public BOMColumnData clmBOMData[] =
    {
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 140, JLabel.LEFT),
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 250, JLabel.LEFT),
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00287")/*작성자*/, 70, JLabel.CENTER),
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00286")/*작성일자*/, 80, JLabel.CENTER),
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 70, JLabel.CENTER),
        new BOMColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00088")/*공동작업자*/, 105, JLabel.LEFT),
        new BOMColumnData("Oid", 50, JLabel.LEFT)
    };
    protected Vector vecBOMData;

    public BOMTableData(Vector data) {
        vecBOMData = new Vector();
        vecBOMData.removeAllElements();
        vecBOMData = data;
    }

    public int getRowCount() {
        return vecBOMData == null ? 0 : vecBOMData.size();
    }

    public int getColumnCount() {
        return clmBOMData.length;
    }

    public String getColumnName(int column) {
        return clmBOMData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol) {
        if (nCol == 5) return true;
        else return false;
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        BOMData row = (BOMData)vecBOMData.elementAt(nRow);

        switch (nCol) {
            case 0: return row.strItemCode;
            case 1: return row.strDescription;
            case 2: return row.strCreatedBy;
            case 3: return row.strCreatedDate;
            case 4: return row.strState;
            case 5: return row.vecCoworker;
            case 6: return row.strOid;
        }
        return "";
    }

}
