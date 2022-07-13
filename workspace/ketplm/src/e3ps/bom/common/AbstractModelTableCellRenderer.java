package e3ps.bom.common;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public abstract class AbstractModelTableCellRenderer extends DefaultTableCellRenderer
{
    private AbstractAIFUIApplication app;

    public AbstractModelTableCellRenderer(AbstractAIFUIApplication app)
    {
        this.app = app;
    }

    public void setValue(Object value)
    {
        super.setValue(value);
    }

    public abstract Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1, int i, int j);

    protected void getSuperTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }

}
