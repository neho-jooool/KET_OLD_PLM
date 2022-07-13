package e3ps.bom.command.loadexcelbom;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.common.message.KETMessageService;

public class LoadExcelBOMData
{
    public String numStr;
    public String itemCdStr;
    public String itemNmStr;
    public String versionStr;
    public String quantityStr;
    public String unitStr;
    public String statusStr;

    public String dienoStr;
    public String meterStr;
    public String hardnessFromStr;
    public String hardnessToStr;
    public String desionDateStr;

    public LoadExcelBOMData(String num, String itemCd, String itemNm, String version, String quantity ,String unit, String status, String dieno, String meter, String hardnessFrom, String hardnessTo, String desionDate)
    {
        numStr = num;
        itemCdStr = itemCd;
        itemNmStr = itemNm;
        versionStr = version;
        quantityStr = quantity;
        unitStr = unit;
        statusStr = status;

        dienoStr = dieno;
        meterStr = meter;
        hardnessFromStr = hardnessFrom;
        hardnessToStr = hardnessTo;
        desionDateStr = desionDate;

    }
}

class LoadExcelBOMSearchColumnData
{
    public String  strTitle;
    public int     intWidth;
    public int     intAlignment;

    public LoadExcelBOMSearchColumnData(String title, int width, int alignment)
    {
        strTitle = title;
        intWidth = width;
        intAlignment = alignment;
    }
}

class extItemSchData extends AbstractTableModel
{
    static final public LoadExcelBOMSearchColumnData clmModelData[] =
    {
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.LEFT ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 200, JLabel.LEFT ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00219")/*상태*/, 1, JLabel.CENTER ),

        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00102")/*금형번호*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00086")/*경도From*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00087")/*경도To*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/, 1, JLabel.CENTER ),

    };
    protected Vector vecWorkerData;

    public extItemSchData(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
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
        LoadExcelBOMData row = (LoadExcelBOMData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
          case 0: return row.numStr;
        case 1: return row.itemCdStr;
        case 2: return row.itemNmStr;
        case 3: return row.versionStr;
        case 4: return row.quantityStr;
        case 5: return row.unitStr;
        case 6: return row.statusStr;

        case 7: return row.dienoStr;
        case 8: return row.meterStr;
        case 9: return row.hardnessFromStr;
        case 10: return row.hardnessToStr;
        case 11: return row.desionDateStr;
        }
        return "";
    }

}


class newItemSchData extends AbstractTableModel
{
    static final public LoadExcelBOMSearchColumnData clmModelData2[] =
    {
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00248")/*순서*/, 30, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.LEFT ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 200, JLabel.LEFT ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00219")/*상태*/, 1, JLabel.CENTER ),

        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00102")/*금형번호*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00086")/*경도From*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00087")/*경도To*/, 1, JLabel.CENTER ),
        new LoadExcelBOMSearchColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/, 1, JLabel.CENTER ),
    };
    protected Vector vecWorkerData;

    public newItemSchData(Vector data)
    {
        vecWorkerData = new Vector();
        vecWorkerData.removeAllElements();
        vecWorkerData = data;
    }

    public int getRowCount()
    {
        return vecWorkerData == null ? 0 : vecWorkerData.size();
    }

    public int getColumnCount()
    {
        return clmModelData2.length;
    }

    public String getColumnName(int column)
    {
        return clmModelData2[column].strTitle;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        LoadExcelBOMData row = (LoadExcelBOMData)vecWorkerData.elementAt(nRow);

        switch (nCol)
        {
        case 0: return row.numStr;
        case 1: return row.itemCdStr;
        case 2: return row.itemNmStr;
        case 3: return row.versionStr;
        case 4: return row.quantityStr;
        case 5: return row.unitStr;
        case 6: return row.statusStr;
        case 7: return row.dienoStr;
        case 8: return row.meterStr;
        case 9: return row.hardnessFromStr;
        case 10: return row.hardnessToStr;
        case 11: return row.desionDateStr;
        }
        return "";
    }

}

