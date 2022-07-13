package e3ps.bom.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import e3ps.bom.common.util.ColorList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;

public class ItemTableCellRenderer extends DefaultTableCellRenderer 
{
    private AbstractAIFUIApplication app;

    public ItemTableCellRenderer(AbstractAIFUIApplication app) 
	{
        this.app = app;
    }

    /**
	 * Invoked as part of DefaultTableCellRenderers implemention. Sets
	 * the text of the label.
	 */
	public void setValue(Object value) 
	{
	    super.setValue(value);
	}

	/**
	 * Returns this.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
	    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Registry appReg = Registry.getRegistry(app);
        // Table의 홀수 열과 짝수 열에 대한 색상처리..

        if (((row+1) % 2) == 1)
            setBackground(ColorList.veryLightGray);
        else
            setBackground(Color.white);

        if (column == ItemTableModel.COL_ITEM) 
		{
            if (((row+1) % 2) == 1)
                setBackground(ColorList.lightYellowColor);
            setIcon(appReg.getImageIcon("partIcon"));
        }

        if (isSelected)
            setBackground(Color.lightGray);

        // Tooltip 지정
        if (value != null && value.getClass().getName().endsWith("String"))
            setToolTipText(value.toString());

        return this;
	}

	/**
	 * If the row being painted is also being reloaded this will draw
	 * a little indicator.
	 */
	public void paint(Graphics g) 
	{
		super.paint(g);
	}
}
