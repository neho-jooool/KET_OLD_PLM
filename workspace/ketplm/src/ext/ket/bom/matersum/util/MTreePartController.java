package ext.ket.bom.matersum.util;

import java.util.Iterator;

import ext.ket.bom.matersum.MCompositeIterator;
import ext.ket.bom.matersum.MHierarchyComponent;
import ext.ket.bom.matersum.MTreeRootPart;
import ext.ket.bom.matersum.dto.MaterSumDTO;
import ext.ket.shared.log.Kogger;

/**
 * BOM 간의 계산, 비교 등의 Control 역할을 담당한다.
 * 
 * @author Administrator
 * 
 */
final public class MTreePartController {

    MHierarchyComponent allParts;

    boolean isCalculatePartNodeType = false;
    boolean idCalculateSubBomCount = false;

    public MTreePartController(MHierarchyComponent allParts) {
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

	MTreeRootPart root = (MTreeRootPart) allParts;
	int maxDepth = root.getMaxDepth();
	boolean[] levelNodeCheck = new boolean[maxDepth];
	for (boolean tempBool : levelNodeCheck)
	    tempBool = false;

	if (!MHierarchyComponent.NEXT_NODE_BY_PARENT)
	    allParts.checkSameDepthNextNode();

	Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());

	while (iterator.hasNext()) {

	    MHierarchyComponent bomComponent = iterator.next();
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
    
    public void printAllPartsStructure() {

	calculatePartNodeType();
	
	/*
	 * treeArrMid.gif "┝" + 삼각형 : 5 treeNodeMid.gif "├" : 4
	 * 
	 * treeArrBtm.gif "┕" + 삼각형 :3 treeNodeBtm.gif "└" :2
	 * 
	 * treeNodeNull.gif "│" :1 treeNodeEmpty.gif "　" "0
	 */
	Kogger.info(getClass(), "\nKET 중량/재질 PART BOM 정보\n----");
	Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());

	int idx = 1;
	while (iterator.hasNext()) {

	    MHierarchyComponent bomComponent = iterator.next();
	    try {

		MaterSumDTO treeDTO = (MaterSumDTO)bomComponent.getTreeDTO();
		
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
		if(treeDTO.getWtPart() == null){
		    Kogger.info(prefix + treeDTO.getTreeNumber() );
		}else{
		    Kogger.info(prefix + treeDTO.getPartsNo() );
		}

	    } catch (UnsupportedOperationException e) {
		Kogger.error(getClass(), e);
	    }

	    idx++;
	}
	Kogger.debug(getClass(), "\n\n");
    }

    public void printAllPartsDetail() {

	calculatePartNodeType();

	/*
	 * treeArrMid.gif "┝" + 삼각형 : 5 treeNodeMid.gif "├" : 4
	 * 
	 * treeArrBtm.gif "┕" + 삼각형 :3 treeNodeBtm.gif "└" :2
	 * 
	 * treeNodeNull.gif "│" :1 treeNodeEmpty.gif "　" "0
	 */
	System.out.print("\nKET 중량/재질 PART BOM 정보\n----");
	Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());

	int idx = 1;
	while (iterator.hasNext()) {

	    MHierarchyComponent bomComponent = iterator.next();
	    try {

		MaterSumDTO treeDTO = (MaterSumDTO)bomComponent.getTreeDTO();
		
		String prefix = "\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ";
		String prefix2 = "\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ";
		String nodeType = bomComponent.getNodeType();
		for (int k = 0; k < nodeType.length(); k++) {
		    switch (nodeType.charAt(k)) {
		    case '0':
			prefix = prefix + "　";
			prefix2 = prefix2 + "　";
			break;
		    case '1':
			prefix = prefix + "│";
			prefix2 = prefix2 + "　";
			break;
		    case '2':
			prefix = prefix + "ㄴ";
			prefix2 = prefix2 + "　";
			break;
		    case '3':
			prefix = prefix + "┕";
			prefix2 = prefix2 + "　";
			break;
		    case '4':
			prefix = prefix + "├";
			prefix2 = prefix2 + "　";
			break;
		    case '5':
			prefix = prefix + "ㅑ";
			prefix2 = prefix2 + "　";
			break;
		    }
		}
		prefix = prefix + " ";
		prefix2 = prefix2 + "　";
		if(treeDTO.getWtPart() == null){
		    System.out.print(prefix + treeDTO.getTreeNumber() + " / " + treeDTO.getNewTweight() + " / " + treeDTO.getNewWeight() + " / " + treeDTO.getNewSweight()
			    + " / " + treeDTO.getQuantity() +  " / " + treeDTO.getUnit()
			    + " / " + treeDTO.getNewType() + " / " + treeDTO.getNewMaterial() + " / " + treeDTO.getNewMaterial2() );
		    System.out.print(prefix2 + treeDTO.getTreeNumber() + " / " + treeDTO.getPretotalW() + " / " + treeDTO.getPrerawW() + " / " + treeDTO.getPrescrapW() 
			    + " / " + treeDTO.getMaterialType() + " / " + treeDTO.getPreMaterial() +  " / " + treeDTO.getPrematerialDesc()
			    );
		}else{
		    System.out.print(prefix + treeDTO.getPartsNo() + " / " + treeDTO.getNewTweight() + " / " + treeDTO.getNewWeight() + " / " + treeDTO.getNewSweight() 
			    + " / " + treeDTO.getQuantity() +  " / " + treeDTO.getUnit() 
			    + " / " + treeDTO.getNewType() + " / " + treeDTO.getNewMaterial() + " / " + treeDTO.getNewMaterial2() );
		    System.out.print(prefix2 + treeDTO.getPartsNo() + " / " + treeDTO.getPretotalW() + " / " + treeDTO.getPrerawW() + " / " + treeDTO.getPrescrapW() 
			    + " / " + treeDTO.getMaterialType() + " / " + treeDTO.getPreMaterial() +  " / " + treeDTO.getPrematerialDesc()
			    );
		}

	    } catch (UnsupportedOperationException e) {
		Kogger.error(getClass(), e);
	    }

	    idx++;
	}
	System.out.print("\n\n");
    }

    private boolean isUpgvcParent(MHierarchyComponent uvBomComponent, MHierarchyComponent bomComponent) {
	if (bomComponent.getParent() == uvBomComponent) {
	    return true;
	} else {
	    if (bomComponent.getParent() instanceof MTreeRootPart)
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

	Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());

	Kogger.debug(getClass(), "\nKET SubInfo BOM\n----");
	int idx = 1;
	while (iterator.hasNext()) {

	    MHierarchyComponent bomComponent = iterator.next();

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
