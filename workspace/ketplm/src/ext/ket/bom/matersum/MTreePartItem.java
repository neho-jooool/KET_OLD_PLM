package ext.ket.bom.matersum;

import java.io.Serializable;
import java.util.Iterator;

/**
 * leaf Node 의 component
 * 
 * @author Administrator
 * 
 */
final public class MTreePartItem implements MHierarchyComponent, Serializable {

    private MTreeDTO treeDTO = null;
    private MTreeRootPart root = null;
    // next node create by parent
    private MHierarchyComponent parent = null;
    private int order;
    // next node create by iterator
    private boolean hasSameDepthNextNode;
    private StringBuffer nodeType = new StringBuffer();

    private static final Iterator<MHierarchyComponent> nullIterator = new MNullIterator();

    public MTreePartItem() {

    }

    @Override
    public Iterator<MHierarchyComponent> getIterator() {
	return nullIterator;
    }

    @Override
    public void subCalculate() {
    }

    @Override
    public void print() {

	System.out.print("\n" + getTreeDTO().getDepth() + " ");
	for (int k = 0; k < getTreeDTO().getDepth(); k++) {
	    System.out.print(" ");
	}

	System.out.print(getTreeDTO().getTreeNumber() + " / " + getTreeDTO().getTreeAmount() + " / " + getTreeDTO().getPartAmountMultiply());
    }

    @Override
    public void add(MHierarchyComponent menuComponent) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void remove(MHierarchyComponent menuComponent) {
	throw new UnsupportedOperationException();
    }

    @Override
    public MHierarchyComponent getChild(int i) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setRoot(MTreeRootPart hComponent) {
	this.root = hComponent;
    }

    @Override
    public MTreeRootPart getRoot() {
	return root;
    }

    @Override
    public boolean hasSameDepthNextNode() {
	if (MHierarchyComponent.NEXT_NODE_BY_PARENT)
	    return parent.getSubNodeCount() - order > 0;
	else
	    return hasSameDepthNextNode;
    }

    @Override
    public void setParent(MHierarchyComponent hComponent) {
	this.parent = hComponent;
    }

    @Override
    public MHierarchyComponent getParent() {
	return this.parent;
    }

    @Override
    public void setOrder(int order) {
	this.order = order;
    }

    @Override
    public int getSubNodeCount() {
	return 0;
    }

    @Override
    public void setHasSameDepthNextNode(boolean hasSameDepthNextNode) {
	this.hasSameDepthNextNode = hasSameDepthNextNode;
    }

    @Override
    public void checkSameDepthNextNode() {

    }

    @Override
    public void addNodeType(String nodeType) {
	this.nodeType.append(nodeType);

    }

    @Override
    public String getNodeType() {
	return nodeType.toString();
    }

    // @Override
    // public String getNodeTypeImg() {
    // if(nodeType == 6)
    // return "treeArrMid.gif"; // "┝" + 삼각형
    // else if(nodeType == 5)
    // return "treeNodeMid.gif"; // "├"
    // else if(nodeType == 4)
    // return "treeArrBtm.gif"; // "┕" + 삼각형
    // else if(nodeType == 3)
    // return "treeNodeBtm.gif"; // "└"
    // else if(nodeType == 2)
    // return "treeNodeNull.gif"; // "│"
    // else if(nodeType == 1)
    // return "treeNodeEmpty.gif"; // "　"
    // return null;
    // }

    @Override
    public MTreeDTO getTreeDTO() {
	return treeDTO;
    }

    @Override
    public void setTreeDTO(MTreeDTO bomDTO) {
	bomDTO.setLeaf(true);
	this.treeDTO = bomDTO;
    }

    // ///////////////////////////////////////////////////
    // Check KET CAD2BOM Relation
    // ///////////////////////////////////////////////////
    
    @Override 
    public void checkWeightMaterial() throws Exception { 
	parent.checkWeightMaterial();
    }
    
}
