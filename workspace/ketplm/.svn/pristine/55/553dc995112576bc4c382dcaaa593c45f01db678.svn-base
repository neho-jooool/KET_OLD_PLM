package e3ps.bom.command.updatebom;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class BOMChildItemSearchData
{
    public String itemCodeStr;
    public String descStr;
    public String defaultunitStr;
    public String createdByStr;
    public String createdDateStr;
    public String statusStr;
    public String statusKrStr;
    public String unitStr;
    public String verStr;

    //public BOMChildItemSearchData(String itemCode, String desc, String uom, String uit, String supplyType, String createdBy, String createdDate, String status)
    public BOMChildItemSearchData(String itemCode, String desc, String defaultunit, String createdBy, String createdDate, String status, String statusKr, String unit, String ver)
    {
        itemCodeStr = itemCode;
        descStr = desc;
        defaultunitStr = defaultunit;
        createdByStr = createdBy;
        createdDateStr = createdDate;
        statusStr = status;
        statusKrStr = statusKr;
        unitStr = unit;
        verStr = ver;
    }
}

class BOMChildItemSearchColumnData
{
    public String  strTitle;
    public int     intWidth;
    public int     intAlignment;

    public BOMChildItemSearchColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class BOMChildItemSearchTableData extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    static final public BOMChildItemSearchColumnData clmModelData[] =
    {
        new BOMChildItemSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.LEFT ),
        new BOMChildItemSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 190, JLabel.LEFT ),
        new BOMChildItemSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/, 60, JLabel.CENTER ),
        new BOMChildItemSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 80, JLabel.CENTER ),
        new BOMChildItemSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/, 80, JLabel.CENTER ),
        new BOMChildItemSearchColumnData( "Status", 50, JLabel.LEFT ),
        new BOMChildItemSearchColumnData( "StatusKr", 50, JLabel.LEFT ),
        new BOMChildItemSearchColumnData( "unit", 50, JLabel.LEFT )
    };
    protected Vector vecModelData;

    public BOMChildItemSearchTableData(Vector data)
    {
        vecModelData = new Vector();
        vecModelData.removeAllElements();
        vecModelData = data;
    }

    public int getRowCount()
    {
        return vecModelData == null ? 0 : vecModelData.size();
    }

    public int getColumnCount()
    {
        return clmModelData.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        BOMChildItemSearchData row = (BOMChildItemSearchData)vecModelData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return row.itemCodeStr;
            case 1: return row.descStr;
            case 2: return row.verStr;
            case 3: return row.statusKrStr;
            case 4: return row.unitStr;
            case 5: return row.statusStr;
            case 6: return row.statusKrStr;
            case 7: return row.defaultunitStr;
        }
        return "";
    }

}
