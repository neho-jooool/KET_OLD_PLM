package e3ps.bom.command.mybom;

import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class BOMECOTableCellEditor extends DefaultCellEditor  
{
	private static final long serialVersionUID = 1L;

	public BOMECOTableCellEditor() 
	{
		super(new JComboBox());
    }

    public BOMECOTableCellEditor(JCheckBox checkbox) 
	{
		super(checkbox);
    }

    public BOMECOTableCellEditor(JComboBox combobox) 
	{
		super(combobox);
    }
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	{
		if (value == null) return (new JComboBox()) ;
		
		Vector<?> vec = (Vector<?>)value;
		JComboBox combo = new JComboBox();

		for (int i=0; i < vec.size(); i++) 
		{
			JCheckBox tmpCheck = (JCheckBox)(vec.elementAt(i));
			JCheckBox chk = new JCheckBox(tmpCheck.getText(), tmpCheck.isSelected());

			combo.addItem(chk);
		}
		combo.setRenderer(new MyCellRenderer());
		return combo;
	}												 

	class MyCellRenderer extends JCheckBox implements ListCellRenderer 
	{
		private static final long serialVersionUID = 1L;

		public MyCellRenderer() 
		{
		}

		public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
		boolean cellHasFocus)
		{
			if (value == null) return (new JCheckBox()) ;
			
			JCheckBox tmp = (JCheckBox)value;
			setText(tmp.getText());
			tmp.setBackground(tmp.isSelected() ? Color.cyan : Color.white);
			tmp.setForeground(tmp.isSelected() ? Color.black : Color.black);
			tmp.setSelected(tmp.isSelected());
			return tmp;
		}
	}

}
