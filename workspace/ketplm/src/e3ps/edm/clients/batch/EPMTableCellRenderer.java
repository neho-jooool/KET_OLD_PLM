package e3ps.edm.clients.batch;
import javax.swing.table.DefaultTableCellRenderer;

public class EPMTableCellRenderer extends DefaultTableCellRenderer {

	public void setValue(Object value) {
		if(value instanceof EPMTableCellData) {
			EPMTableCellData cvalue = (EPMTableCellData)value;
			if(cvalue.m_color != null) {
				setForeground(cvalue.m_color.m_color);
				//setBackground(cvalue.m_color.m_color);
			}
			
			if(cvalue.m_symbol != null) {
				setIcon(cvalue.m_symbol.m_icon);
			}
			setText(cvalue.m_data.toString());
			//setText(cvalue.m_data.toString());
		}
		else if (value instanceof EPMTableColorData) {
			EPMTableColorData cvalue = (EPMTableColorData)value;
			setForeground(cvalue.m_color);
			//setBackground(cvalue.m_color);
			setText(cvalue.m_data.toString());
		}
		else if (value instanceof EPMTableIconData) {
			EPMTableIconData ivalue = (EPMTableIconData)value;
			setIcon(ivalue.m_icon);
			setText(ivalue.m_data.toString());
		}
		else
			super.setValue(value);
	}
}
