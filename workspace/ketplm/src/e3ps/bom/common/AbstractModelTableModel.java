package e3ps.bom.common;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractModelTableModel extends AbstractTableModel
{
    protected Vector m_vector;

    protected AbstractModelTableModel(Vector resultList)
    {
        m_vector = resultList;
    }

    public abstract ColumnData[] getColumnData();

    public abstract ColumnData getColumnData(int i);

    public int getRowCount()
    {
        return m_vector != null ? m_vector.size() : 0;
    }

    public int getColumnCount()
    {
        return getColumnData().length;
    }

    public String getColumnName(int column)
    {
        return getColumnData(column).m_title;
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    protected abstract AbstractModelTableData getComponent(int i);

    public abstract Object getValueAt(int i, int j);

    public boolean delete(int row)
    {
        if (row < 0 || row >= m_vector.size())
            return false;
        m_vector.remove(row);
            return true;
    }

}
