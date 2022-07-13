package e3ps.edm.clients.batch;


public class EPMTableCellData {
	
	public EPMTableIconData	m_symbol;
	public EPMTableColorData m_color;
	
	public Object m_data;
	
	public EPMTableCellData(Object m_data, int i) {
		this.m_data = m_data;
		this.m_color = new EPMTableColorData(m_data, i);
	}	
	
	public String toString() {
		return m_data.toString();
	}
}
