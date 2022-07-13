package ext.ket.edm.cad2bom.tree.util;

import java.util.Iterator;

import ext.ket.edm.cad2bom.tree.CompositeIterator;
import ext.ket.edm.cad2bom.tree.HierarchyComponent;
import ext.ket.edm.cad2bom.tree.TreeRootPart;
import ext.ket.shared.log.Kogger;

/**
 * BOM 간의 계산, 비교 등의 Control 역할을 담당한다.
 * 
 * @author Administrator
 * 
 */
final public class TreePartController {

    HierarchyComponent allParts;

    boolean isCalculatePartNodeType = false;
    boolean idCalculateSubBomCount = false;

    public TreePartController(HierarchyComponent allParts) {
	this.allParts = allParts;
    }

    /**
     * commonUtil로 가야
     * 
     * @param arg
     * @param size
     * @param str
     * @return
     */
    public String getReSizeStr(int arg, int size, String str) {

	int gap;
	Integer temp = (Integer) arg;
	if (size == temp.toString().length())
	    return temp.toString();
	else if ((gap = size - temp.toString().length()) > 0) {
	    StringBuffer buffer = new StringBuffer();
	    for (int k = 0; k < gap; k++) {
		buffer.append(str);
	    }

	    return buffer.toString() + temp.toString();
	} else
	    return temp.toString();
    }

    public void printParts() {
	Kogger.debug(getClass(), "\nKET PART List\n----");
	allParts.print();
	Kogger.debug(getClass(), "\n\n");
    }

    public void calculatePartNodeType() {

	if (isCalculatePartNodeType)
	    return;

	TreeRootPart root = (TreeRootPart) allParts;
	int maxDepth = root.getMaxDepth();
	boolean[] levelNodeCheck = new boolean[maxDepth];
	for (boolean tempBool : levelNodeCheck)
	    tempBool = false;

	if (!HierarchyComponent.NEXT_NODE_BY_PARENT)
	    allParts.checkSameDepthNextNode();

	Iterator<HierarchyComponent> iterator = new CompositeIterator(allParts.getIterator());

	while (iterator.hasNext()) {

	    HierarchyComponent bomComponent = iterator.next();
	    int depth = bomComponent.getTreeDTO().getDepth();
	    try {

		for (int k = 0; k < depth; k++) {

		    if (k + 1 == depth) {
			if (bomComponent.hasSameDepthNextNode()) {
			    levelNodeCheck[k] = true;
			    if (!bomComponent.getTreeDTO().isLeaf()) {
				bomComponent.addNodeType("5");
			    } else {
				bomComponent.addNodeType("4");
			    }
			} else {
			    levelNodeCheck[k] = false;
			    if (!bomComponent.getTreeDTO().isLeaf()) {
				bomComponent.addNodeType("3");
			    } else {
				bomComponent.addNodeType("2");
			    }
			}
		    } else {
			if (levelNodeCheck[k]) {
			    bomComponent.addNodeType("1");
			} else {
			    bomComponent.addNodeType("0");
			}
		    }
		}

	    } catch (UnsupportedOperationException e) {
		Kogger.error(getClass(), e);
	    }
	}

	isCalculatePartNodeType = true;
    }

    public void printAllParts() {

	calculatePartNodeType();

	/*
	 * treeArrMid.gif "┝" + 삼각형 : 5 treeNodeMid.gif "├" : 4
	 * 
	 * treeArrBtm.gif "┕" + 삼각형 :3 treeNodeBtm.gif "└" :2
	 * 
	 * treeNodeNull.gif "│" :1 treeNodeEmpty.gif "　" "0
	 */
	Kogger.debug(getClass(), "\nKET PART BOM\n----");

	Iterator<HierarchyComponent> iterator = new CompositeIterator(allParts.getIterator());

	int idx = 1;
	while (iterator.hasNext()) {

	    HierarchyComponent bomComponent = iterator.next();
	    try {

		String prefix = "\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ";
		String nodeType = bomComponent.getNodeType();
		for (int k = 0; k < nodeType.length(); k++) {
		    switch (nodeType.charAt(k)) {
		    case '0':
			prefix = prefix + "　";
			break;
		    case '1':
			prefix = prefix + "│";
			break;
		    case '2':
			prefix = prefix + "ㄴ";
			break;
		    case '3':
			prefix = prefix + "┕";
			break;
		    case '4':
			prefix = prefix + "├";
			break;
		    case '5':
			prefix = prefix + "ㅑ";
			break;
		    }
		}
		prefix = prefix + " ";
		if(bomComponent.getTreeDTO().getWtPart() == null){
		System.out.println(prefix + bomComponent.getTreeDTO().getTreeNumber() + " / " + bomComponent.getTreeDTO().getTreeAmount() + " / " 
			       + "      " + " / " + bomComponent.getTreeDTO().isCanConfigPartCadRel() + " / " + bomComponent.getTreeDTO().isCanConfigPartBom());
		}else{
		System.out.println(prefix + bomComponent.getTreeDTO().getTreeNumber() + " / " + bomComponent.getTreeDTO().getTreeAmount() + " / " 
			       + bomComponent.getTreeDTO().getWtPart().getNumber() + " / " + bomComponent.getTreeDTO().isCanConfigPartCadRel() + " / " + bomComponent.getTreeDTO().isCanConfigPartBom());
		}
//		System.out.print(bomComponent.getTreeDTO().getTreeNumber() + " / " + bomComponent.getTreeDTO().getTreeAmount() + " / " + bomComponent.getTreeDTO().getPartAmountMultiply());

	    } catch (UnsupportedOperationException e) {
		Kogger.error(getClass(), e);
	    }

	    idx++;
	}
	Kogger.debug(getClass(), "\n\n");
    }

    private boolean isUpgvcParent(HierarchyComponent uvBomComponent, HierarchyComponent bomComponent) {
	if (bomComponent.getParent() == uvBomComponent) {
	    return true;
	} else {
	    if (bomComponent.getParent() instanceof TreeRootPart)
		return false;
	    else
		return isUpgvcParent(uvBomComponent, bomComponent.getParent());
	}
    }

    public void calculateSubBomCount() {
	if (idCalculateSubBomCount)
	    return;

	allParts.subCalculate();
	idCalculateSubBomCount = true;
    }

    public void printSubCountBom() {

	calculateSubBomCount();

	Iterator<HierarchyComponent> iterator = new CompositeIterator(allParts.getIterator());

	Kogger.debug(getClass(), "\nKET SubInfo BOM\n----");
	int idx = 1;
	while (iterator.hasNext()) {

	    HierarchyComponent bomComponent = iterator.next();

	    try {

		System.out.print("\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ");
		for (int k = 0; k < bomComponent.getTreeDTO().getDepth(); k++) {
		    System.out.print(" ");
		}

		System.out.print(bomComponent.getTreeDTO().getTreeNumber() + " / " + bomComponent.getTreeDTO().getTreeAmount() + " / " + bomComponent.getTreeDTO().getPartAmountMultiply() + " / "
		        + bomComponent.getTreeDTO().getBomSubCount() + " / " + bomComponent.getTreeDTO().getBomTotalCount() + " / " + bomComponent.getTreeDTO().getBomTotalMultiplyCount());

	    } catch (UnsupportedOperationException e) {
		Kogger.error(getClass(), e);
	    }

	    idx++;
	}

	Kogger.debug(getClass(), "\n\n");
    }

}
