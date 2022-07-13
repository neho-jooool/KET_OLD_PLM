package ext.ket.bom.query;

import java.text.Collator;
import java.util.Comparator;

public class KETComparator implements Comparator {

    @Override
    public int compare(Object a, Object b) {
	String x = a.toString();
	String y = b.toString();
	return Collator.getInstance().compare(x, y);
    }

}
