// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableMap.java

package e3ps.bom.common.util;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableMap extends AbstractTableModel implements TableModelListener
{
    protected TableModel model;

    public TableModel getModel()
    {
        return model;
    }

    public void setModel(TableModel model)
    {
        this.model = model;
        model.addTableModelListener(this);
    }

    public Object getValueAt(int aRow, int aColumn)
    {
        return model.getValueAt(aRow, aColumn);
    }

    public void setValueAt(Object aValue, int aRow, int aColumn)
    {
        model.setValueAt(aValue, aRow, aColumn);
    }

    public int getRowCount()
    {
        return model != null ? model.getRowCount() : 0;
    }

    public int getColumnCount()
    {
        return model != null ? model.getColumnCount() : 0;
    }

    public String getColumnName(int aColumn)
    {
        return model.getColumnName(aColumn);
    }

    public Class getColumnClass(int aColumn)
    {
        return model.getColumnClass(aColumn);
    }

    public boolean isCellEditable(int row, int column)
    {
        return model.isCellEditable(row, column);
    }

    public void tableChanged(TableModelEvent e)
    {
        fireTableChanged(e);
    }

}
