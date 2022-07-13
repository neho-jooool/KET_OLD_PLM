package e3ps.bom.command.mybom;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class BOMECOTableData extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    static final public BOMECOColumnData clmBOMECOData[] =
    {
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 120, JLabel.LEFT),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 200, JLabel.LEFT),
        new BOMECOColumnData("ECO No", 90, JLabel.CENTER),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00039")/*ECO 제목*/, 150, JLabel.LEFT),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/, 40, JLabel.CENTER),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00164")/*변경사유*/, 80, JLabel.LEFT),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00287")/*작성자*/, 70, JLabel.CENTER),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00286")/*작성일자*/, 80, JLabel.CENTER),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 80, JLabel.CENTER),
        new BOMECOColumnData(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00088")/*공동작업자*/, 105, JLabel.LEFT),
        new BOMECOColumnData("Oid", 50, JLabel.LEFT)
    };
    protected Vector vecBOMECOData;

    public BOMECOTableData(Vector data) {
        vecBOMECOData = new Vector();
        vecBOMECOData.removeAllElements();
        vecBOMECOData = data;
    }

    public int getRowCount() {
        return vecBOMECOData == null ? 0 : vecBOMECOData.size();
    }

    public int getColumnCount() {
        return clmBOMECOData.length;
    }

    public String getColumnName(int column) {
        return clmBOMECOData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol) {
        if (nCol == 9) return true;
        return false;
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        BOMECOData row = (BOMECOData)vecBOMECOData.elementAt(nRow);

        switch (nCol) {
            case 0: return row.strItemCode;
            case 1: return row.strItemDesc;
            case 2: return row.strEcoNo;
            case 3: return row.strTitle;
            case 4: return row.strEcoType;
            case 5: return row.strReason;
            case 6: return row.strCreatedBy;
            case 7: return row.strCreatedDate;
            case 8: return row.strState;
            case 9: return row.vecCoworker;
            case 10: return row.strOid;
        }
        return "";
    }

}
