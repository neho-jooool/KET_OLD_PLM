package e3ps.bom.common;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.common.message.KETMessageService;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;

//////////////////////////////////////////////////////////////////////////
// Table Model
public class ItemTableModel extends AbstractTableModel
{
    public static final ColumnData m_columns[] = {
       /*
        new ColumnData( "Item Code", 120, JLabel.LEFT ),
        new ColumnData( "Description", 200, JLabel.LEFT ),
        new ColumnData( "UOM", 40, JLabel.CENTER ),
        new ColumnData( "User Item Type", 100, JLabel.CENTER),
        new ColumnData( "Supply Type", 100, JLabel.CENTER),
        new ColumnData( "Status", 70, JLabel.CENTER),
        */
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/, 110, JLabel.LEFT ),
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/, 180, JLabel.LEFT ),
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/, 70, JLabel.CENTER ),
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00287")/*작성자*/, 100, JLabel.CENTER),
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00286")/*작성일자*/, 100, JLabel.CENTER),
         new ColumnData( KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/, 70, JLabel.CENTER),
    };

    public static final int COL_ITEM = 0;
    public static final int COL_DESC = 1;
    public static final int COL_UNIT = 2;
    public static final int COL_USER = 3;
    public static final int COL_DATE = 4;
    public static final int COL_STATUS = 5;

   /* public static final int COL_UOM = 2;
    public static final int COL_UIT = 3;
    public static final int COL_SUPPLYTYPE = 4;
    public static final int COL_STATUS = 5;*/

    protected Vector m_vector;

    public ItemTableModel() {
        m_vector = new Vector();
    }

    public int getRowCount() {
        return m_vector==null ? 0 : m_vector.size();
    }

    public int getColumnCount() {
        return m_columns.length;
    }

    public String getColumnName(int column) {
        return m_columns[column].m_title;
    }

    public boolean isCellEditable(int nRow, int nCol) {
        return false;
    }

    public BOMAssyComponent getComponent(int nRow) {
        if (nRow < 0 || nRow>=getRowCount())
            return null;
        return (BOMAssyComponent)m_vector.elementAt(nRow);
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow>=getRowCount())
            return "";

        BOMAssyComponent component = (BOMAssyComponent)m_vector.elementAt(nRow);
        if (component == null)
            return "";

        People people = PeopleHelper.manager.getPeople(component.getUserNmStr());
        String user_name = people.getUser().getFullName();

        switch (nCol) {
            case COL_ITEM: return component.getItemCodeStr();
            case COL_DESC: return component.getDescStr();
            case COL_UNIT: return component.getUitStr();
            case COL_USER: return user_name;
            case COL_DATE: return component.getStartDate();
            case COL_STATUS: return component.getStatusKrStr();
        }
        return "";
    }

    public void insert(BOMAssyComponent component)
    {
        m_vector.addElement(component);
    }

    public boolean delete(int row)
    {
        if (row < 0 || row >= m_vector.size())
            return false;
        m_vector.remove(row);
            return true;
    }
}
