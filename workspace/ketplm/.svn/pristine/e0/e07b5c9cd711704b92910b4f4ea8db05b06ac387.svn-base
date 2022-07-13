package e3ps.bom.command.comparebom.staticcompare;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class StaticCompareResultTableCellRenderer extends DefaultTableCellRenderer
{
	private AbstractAIFUIApplication app;

	private int highlightRow;
	private boolean isHighlight = false;
	private int releasedRow;

	public StaticCompareResultTableCellRenderer(AbstractAIFUIApplication app)
	{
		this.app = app;
	}

	public void setValue(Object value)
	{
		super.setValue(value);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		StaticCompareResultTableModel tmodel = (StaticCompareResultTableModel)table.getModel();
		StaticCompareResultData resultData = tmodel.getComponent(row);

		if (isSelected &&  (row != releasedRow))
		{
			setForeground(Color.white);
			setBackground(ColorList.veryDarkBlueColor);
		}
		else
		{
			if (!resultData.getFirstFlag().equals("") && resultData.getSecondFlag().equals(""))
			{
				setForeground(Color.black);
				setBackground(ColorList.veryLightGray);
			} else if (!resultData.getSecondFlag().equals("") && resultData.getFirstFlag().equals(""))
			{
				setForeground(Color.black);
				setBackground(Color.white);
			} else
			{
				setForeground(Color.black);
				setBackground(ColorList.lightBoraColor);
			}
		}

		if (isHighlight)
		{
			if (row == highlightRow)
			{
				setForeground(Color.white);
				setBackground(ColorList.veryDarkBlueColor);
			}
		}

		setFont(FontList.defaultFont);
		setValue(value);
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


	public void setHighlightRow(int i)
	{
		isHighlight = true;
		highlightRow = i;
	}


	public void releaseHighlightRow(int i)
	{
		releasedRow = i;

	}
}
