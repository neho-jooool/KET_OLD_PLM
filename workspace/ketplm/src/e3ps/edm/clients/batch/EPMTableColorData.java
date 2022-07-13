package e3ps.edm.clients.batch;
import java.awt.Color;

public class EPMTableColorData {
	
	public Color	m_color;
	public Object m_data;
	public static Color GREEN = new Color(0, 128, 0);
	public static Color RED = Color.red;
	public static Color GRAY = Color.gray;
	public static Color ORANGE = Color.orange;
	
	
	public static int COLOR_RED = 1;//error
	public static int COLOR_GRAY = 2;//skip
	public static int COLOR_ORANGE = 3;//duplication insert

	public EPMTableColorData(Color color, Object data) {
		m_color = color;
		m_data	= data;
	}

	public EPMTableColorData(Object data, int color) {
		m_data = data;
		if(color == 1) {
			m_color = RED;
		} else if(color == 2){
			m_color = GRAY;
		} else if(color == 3){
			m_color = ORANGE;
		}
	}
	
	public String toString() {
		return m_data.toString();
	}
}
