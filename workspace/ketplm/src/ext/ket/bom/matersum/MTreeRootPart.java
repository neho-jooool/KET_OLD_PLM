package ext.ket.bom.matersum;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 최상위 root 노드로서 전체 BOM의 정보(ex, maxNode 등)를 여기서 관리함.
 * 
 * @author Administrator
 * 
 */
final public class MTreeRootPart extends MTreePart implements Serializable {

    private int maxDepth = 0;

    @Override
    public void print() {

	// System.out.print("\n" + getTreeDTO().getDepth() + " ");
	// for(int k = 0; k < getTreeDTO().getDepth(); k++){
	// System.out.print(" ");
	// }
	//
	// System.out.print(getTreeDTO().getPartNumber() + " / " + getTreeDTO().getPartName());

	Iterator<MHierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    MHierarchyComponent bomComponent = iterator.next();
	    bomComponent.print();
	}
    }

    public int getMaxDepth() {
	return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
	this.maxDepth = maxDepth;
    }

}
