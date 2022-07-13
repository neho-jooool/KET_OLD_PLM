package ext.ket.bom.matersum;

import java.util.Iterator;
import java.util.Stack;

/**
 * assembly node의 iterator
 */
final public class MCompositeIterator implements Iterator<MHierarchyComponent> {
    private Stack<Iterator<MHierarchyComponent>> stack = new Stack<Iterator<MHierarchyComponent>>();

    public MCompositeIterator(Iterator<MHierarchyComponent> iterator) {
	stack.push(iterator);
    }

    public MHierarchyComponent next() {
	if (hasNext()) {
	    Iterator<MHierarchyComponent> iterator = stack.peek();
	    MHierarchyComponent component = iterator.next();
	    if (component instanceof MTreePart) {
		stack.push(component.getIterator());
	    }
	    return component;
	} else {
	    return null;
	}
    }

    public boolean hasNext() {
	if (stack.empty()) {
	    return false;
	} else {
	    Iterator<MHierarchyComponent> iterator = stack.peek();
	    if (!iterator.hasNext()) {
		stack.pop();
		return hasNext();
	    } else {
		return true;
	    }
	}
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }

}
