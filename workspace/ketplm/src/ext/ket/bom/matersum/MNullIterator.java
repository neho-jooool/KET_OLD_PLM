package ext.ket.bom.matersum;

import java.util.Iterator;

/**
 * leaf 노드의 iterator
 * 
 * @author Administrator
 * 
 */
final public class MNullIterator implements Iterator<MHierarchyComponent> {

    public MHierarchyComponent next() {
	return null;
    }

    public boolean hasNext() {
	return false;
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }
}
