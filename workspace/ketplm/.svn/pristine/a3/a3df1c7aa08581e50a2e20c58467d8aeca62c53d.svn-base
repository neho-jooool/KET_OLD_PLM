package e3ps.edm.clients.batch;
import java.util.Comparator;

public class EPMTableComparator implements Comparator {

	protected int		 m_sortCol;
	protected boolean m_sortAsc;

	public EPMTableComparator(int sortCol, boolean sortAsc) {
		m_sortCol = sortCol;
		m_sortAsc = sortAsc;
	}

	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof EPMLoadData) || !(o2 instanceof EPMLoadData))
			return 0;
		EPMLoadData s1 = (EPMLoadData)o1;
		EPMLoadData s2 = (EPMLoadData)o2;
		int result = 0;
		double d1, d2;
		

		if (!m_sortAsc)
			result = -result;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj instanceof EPMTableComparator) {
			EPMTableComparator compObj = (EPMTableComparator)obj;
			return (compObj.m_sortCol==m_sortCol) &&
				(compObj.m_sortAsc==m_sortAsc);
		}
		return false;
	}
}
