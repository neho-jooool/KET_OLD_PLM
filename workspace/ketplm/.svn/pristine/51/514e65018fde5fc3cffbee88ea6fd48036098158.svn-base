package ext.ket.edm.cad2bom.tree;

import java.io.Serializable;
import java.util.Iterator;

import wt.lifecycle.State;

/**
 * leaf Node 의 component
 * 
 * @author Administrator
 * 
 */
final public class TreePartItem implements HierarchyComponent, Serializable {

    private TreeDTO treeDTO = null;
    private TreeRootPart root = null;
    // next node create by parent
    private HierarchyComponent parent = null;
    private int order;
    // next node create by iterator
    private boolean hasSameDepthNextNode;
    private StringBuffer nodeType = new StringBuffer();

    private static final Iterator<HierarchyComponent> nullIterator = new NullIterator();

    public TreePartItem() {

    }

    @Override
    public Iterator<HierarchyComponent> getIterator() {
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
    public void add(HierarchyComponent menuComponent) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void remove(HierarchyComponent menuComponent) {
	throw new UnsupportedOperationException();
    }

    @Override
    public HierarchyComponent getChild(int i) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setRoot(TreeRootPart hComponent) {
	this.root = hComponent;
    }

    @Override
    public TreeRootPart getRoot() {
	return root;
    }

    @Override
    public boolean hasSameDepthNextNode() {
	if (HierarchyComponent.NEXT_NODE_BY_PARENT)
	    return parent.getSubNodeCount() - order > 0;
	else
	    return hasSameDepthNextNode;
    }

    @Override
    public void setParent(HierarchyComponent hComponent) {
	this.parent = hComponent;
    }

    @Override
    public HierarchyComponent getParent() {
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
    public TreeDTO getTreeDTO() {
	return treeDTO;
    }

    @Override
    public void setTreeDTO(TreeDTO bomDTO) {
	bomDTO.setLeaf(true);
	this.treeDTO = bomDTO;
    }

    // ///////////////////////////////////////////////////
    // Check KET CAD2BOM Relation
    // ///////////////////////////////////////////////////
    
    @Override 
    public void checkCanConfigPartCadRel() throws Exception { 
	
	// 일도다품, 일품다도일 경우 연결 불가 처리
	if( this.treeDTO.getWtPart() == null || 
	    //!"0".equals(this.treeDTO.getWtPart().getVersionIdentifier().getValue()) ||
	    //State.INWORK != this.treeDTO.getWtPart().getLifeCycleState() || 
	    
	    this.treeDTO.isExistCadPartRelation() 
		
	    // 다품일도, 일품다도는 나중에 생각한다.	
	    //this.treeDTO.isExistAnotherPartRelation() ||  
	    //this.treeDTO.isExistAnotherPartRelation() 
	){
	    this.treeDTO.setCanConfigPartCadRel(false);
	}else{
	    this.treeDTO.setCanConfigPartCadRel(true);
	}
	
    }

    // BOM 구성 가능/불가 판정
    // Released된 것 하위는 구성 불가 > 자기 아래 노드부터 불가
    // Released되지 않았지만 BOM 구성된 것 > 자기 노드 부터 불가
    @Override
    public void checkCanConfigSubPartBom() throws Exception {
	
    }
    
/*
     // 기존에 CAD:PART가 동일한 연결을 가지는가?
    private boolean existCadPartRelation = false;
    
    // CAD:PART로 연결할 부품이 다른 CAD와 연결되어 있는가?
    private boolean existAnotherCadWithPartRelation = false;
    
    // CAD:PART로 연결할 도면이 기존에 다른 부품과 연결되어 있는가?
    private boolean existAnotherPartRelation = false;
    
    // CAD:PART로 연결할 부품이 상위Node + 연결한 부품으로 BOOM을 가지고 있는가?
    private boolean existBom = false;

 */
    
}
