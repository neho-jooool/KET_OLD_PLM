package e3ps.bom.command.multiplebomeco;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Color;

import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;

public class MultipleBOMECOValidationTableCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;

	public MultipleBOMECOValidationTableCellRenderer()
	{
		super();	
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
        if((row + 1) % 2 == 1 && !isSelected)
		{
            setBackground(ColorList.veryLightGray);
		}
        else
		{
            setBackground(Color.white);
		}

		if(column == 0)
		{
            setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(column == 1)
		{
            setHorizontalAlignment(JLabel.CENTER);
		}
		else
		{
            setHorizontalAlignment(JLabel.LEFT);
		}

		if (column == 1)
		{
			setBackground(ColorList.lightYellowColor);
		}

        if(isSelected)
        {
            setBackground(ColorList.veryDarkBlueColor);
            setForeground(Color.white);
        }
		else
        {
            setForeground(Color.black);
        }

		setFont(FontList.defaultFont);
        setValue(value);         
		return this;
    }

}
