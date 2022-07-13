package e3ps.bom.command.searchappliedproduct;

import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import e3ps.bom.common.ColumnData;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;

//////////////////////////////////////////////////////////////////////////
// Table Model
public class SearchAppliedProductModel extends DefaultTableModel
{
    private static final long serialVersionUID = 1L;

    public static final ColumnData m_columns[] =
    {
        new ColumnData( "No", 30, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 100, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/, 100, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 190, JLabel.LEFT ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 80, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/, 70, JLabel.CENTER ),
        new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/, 70, JLabel.CENTER ),
        new ColumnData( "Status", 60, JLabel.CENTER )

    };

    public static final int COL_NO = 0;
    public static final int COL_PRODUCT = 1;
    public static final int COL_PITEMCODE = 2;
    public static final int COL_DESCRIPTION = 3;
    public static final int COL_STATUSKR = 4;
    public static final int COL_QUANTITY = 5;
    public static final int COL_UIT = 6;
    public static final int COL_STATUS = 7;

    protected Vector m_vector;
    protected Vector columnNameVec;

    public SearchAppliedProductModel(Vector data, Vector columnNameVec)
    {
        m_vector = data;
        this.columnNameVec = columnNameVec;
    }

    public int getRowCount()
    {
        return m_vector==null ? 0 : m_vector.size();
    }

    public int getColumnCount()
    {
        return m_columns.length;
    }

    public String getColumnName(int column)
    {
        return m_columns[column].m_title;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public SearchAppliedProductData getComponent(int nRow)
    {
        if (nRow < 0 || nRow>=getRowCount())
            return null;
        return (SearchAppliedProductData)m_vector.elementAt(nRow);
    }

    public Object getValueAt(int nRow, int nCol)
    {
        KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        if (nRow < 0 || nRow>=getRowCount())
        {
            return "";
        }

        SearchAppliedProductData component = (SearchAppliedProductData)m_vector.elementAt(nRow);
        if (component == null)
        {
            return "";
        }

        switch (nCol)
        {
        case COL_NO: return component.getNoStr();
        case COL_PRODUCT: return component.getProductStr();
        case COL_PITEMCODE: return component.getParentItemCodeStr();
        case COL_DESCRIPTION: return component.getDescriptionStr();
        case COL_STATUSKR: return component.getStatusKrStr();
        case COL_QUANTITY: return format.format(component.getQuantityDbl());
        case COL_UIT: return bean.getUnitDisplayValue(component.getUserItemTypeStr());
        case COL_STATUS: return component.getStatusStr();

        }
        return "";
    }

    public void insert(SearchAppliedProductData data)
    {
        m_vector.addElement(data);
    }

    public void delete(int row)
    {
        m_vector.removeElementAt(row);
    }

    public void deleteAll()
    {
        m_vector.removeAllElements();
    }
}
