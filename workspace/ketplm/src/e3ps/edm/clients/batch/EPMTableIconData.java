package e3ps.edm.clients.batch;
import javax.swing.ImageIcon;

public class EPMTableIconData {

	public ImageIcon	m_icon;
	public Object m_data;

	public EPMTableIconData(ImageIcon icon, Object data) {
		m_icon = icon;
		m_data = data;
	}

	
	public String toString() {
		return m_data.toString();
	}
}
