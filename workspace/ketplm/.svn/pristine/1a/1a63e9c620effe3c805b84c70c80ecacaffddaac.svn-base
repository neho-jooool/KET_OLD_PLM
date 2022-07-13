package e3ps.bom.command.searchitem;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

class SearchItemData
{
    public String itemCodeStr;
    public String descStr;
    public String defaultunitStr;
    public String createdByStr;
    public String createdDateStr;
    public String statusStr;
    public String statusKrStr;
    public String verStr;
    public String unitStr;
    public String partTypeStr;

    public SearchItemData(String itemCode, String desc, String defaultunit, String createdBy, String createdDate, String status, String statusKr, String ver, String unit, String partType)
    {
        itemCodeStr = itemCode;
        descStr = desc;
        defaultunitStr = defaultunit;
        createdByStr = createdBy;
        createdDateStr = createdDate;
        statusStr = status;
        statusKrStr = statusKr;
        verStr = ver;
        unitStr = unit;
        partTypeStr = partType;
    }
}

class SearchItemColumnData
{
    public String  strTitle;
    public int     intWidth;
    public int     intAlignment;

    public SearchItemColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class SearchItemTableData extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
    static final public SearchItemColumnData clmModelData[] =
    {
        new SearchItemColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.LEFT ),
        new SearchItemColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 220, JLabel.LEFT ),
        new SearchItemColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/, 60, JLabel.CENTER ),
        new SearchItemColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 80, JLabel.CENTER ),
        new SearchItemColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/, 70, JLabel.CENTER ),
        new SearchItemColumnData( "unit", 50, JLabel.CENTER ),
        new SearchItemColumnData( "Status", 50, JLabel.LEFT ),
        new SearchItemColumnData( "StatusKr", 50, JLabel.LEFT ),
        new SearchItemColumnData( "partType", 50, JLabel.LEFT )
    };
    protected Vector vecModelData;

    public SearchItemTableData()
    {
    }

    public SearchItemTableData(Vector data)
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
        SearchItemData row = (SearchItemData)vecModelData.elementAt(nRow);

        switch (nCol)
        {
            case 0: return row.itemCodeStr;
            case 1: return row.descStr;
            case 2: return row.verStr;
            case 3: return row.statusKrStr;
            case 4: return row.unitStr;
            case 5: return row.defaultunitStr;
            case 6: return row.statusStr;
            case 7: return row.statusKrStr;
            case 8: return row.partTypeStr;

        }
        return "";
    }

}
