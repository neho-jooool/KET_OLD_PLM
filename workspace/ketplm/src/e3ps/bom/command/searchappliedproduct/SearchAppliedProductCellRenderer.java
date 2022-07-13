package e3ps.bom.command.searchappliedproduct;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class SearchAppliedProductCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;

	public SearchAppliedProductCellRenderer()
	{
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
        setBackground(Color.white);

        // 선택 Background 지정
        if (isSelected)
		{
            setBackground(Color.lightGray);
		}

        // Tooltip 지정
        if (value != null && value.getClass().getName().endsWith("String"))
		{
            setToolTipText(value.toString());
		}

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
