package ext.ket.edm.cad2bom.tree;

import java.util.Iterator;
import java.util.Stack;

/**
 * assembly node의 iterator
 */
final public class CompositeIterator implements Iterator<HierarchyComponent> {
    private Stack<Iterator<HierarchyComponent>> stack = new Stack<Iterator<HierarchyComponent>>();

    public CompositeIterator(Iterator<HierarchyComponent> iterator) {
	stack.push(iterator);
    }

    public HierarchyComponent next() {
	if (hasNext()) {
	    Iterator<HierarchyComponent> iterator = stack.peek();
	    HierarchyComponent component = iterator.next();
	    if (component instanceof TreePart) {
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
	    Iterator<HierarchyComponent> iterator = stack.peek();
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
