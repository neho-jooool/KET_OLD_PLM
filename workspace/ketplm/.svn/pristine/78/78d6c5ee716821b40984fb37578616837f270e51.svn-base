package ext.ket.edm.cad2bom.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wt.lifecycle.State;

/**
 * assembly Node 의 component
 * 
 * @author Administrator
 * 
 */
public class TreePart implements HierarchyComponent, Serializable {

    protected List<HierarchyComponent> bomComponents = new ArrayList<HierarchyComponent>();
    private TreeDTO treeDTO = null;
    private TreeRootPart root = null;
    // next node create by parent
    private HierarchyComponent parent = null;
    private int order;
    // next node create by iterator
    private boolean hasSameDepthNextNode;
    private StringBuffer nodeType = new StringBuffer();

    public TreePart() {
    }

    @Override
    public void add(HierarchyComponent bomComponent) {
	bomComponent.getTreeDTO().setDepth(treeDTO.getDepth() + 1);
	bomComponent.getTreeDTO().setPartAmountMultiply(treeDTO.getPartAmountMultiply() * bomComponent.getTreeDTO().getTreeAmount());
	bomComponents.add(bomComponent);

	if (HierarchyComponent.NEXT_NODE_BY_PARENT) {
	    bomComponent.setOrder(bomComponents.size());
	    bomComponent.setParent(this);
	}

	bomComponent.setRoot(root);
	if (treeDTO.getDepth() + 1 > root.getMaxDepth())
	    root.setMaxDepth(treeDTO.getDepth() + 1);
    }

    @Override
    public void remove(HierarchyComponent bomComponent) {
	bomComponents.remove(bomComponent);
    }

    @Override
    public HierarchyComponent getChild(int i) {
	return (HierarchyComponent) bomComponents.get(i);
    }

    @Override
    public Iterator<HierarchyComponent> getIterator() {
	return bomComponents.iterator();
    }

    @Override
    public void subCalculate() {
	Iterator<HierarchyComponent> iterator = bomComponents.iterator();
	int bomSubCount = 0;
	int bomTotalCount = 0;
	int bomTotalMultiplyCount = 0;
	while (iterator.hasNext()) {
	    HierarchyComponent subBomComponent = iterator.next();
	    subBomComponent.subCalculate();
	    bomTotalCount = bomTotalCount + subBomComponent.getTreeDTO().getBomTotalCount() + 1;
	    if (subBomComponent.getTreeDTO().getBomTotalMultiplyCount() == 0)
		bomTotalMultiplyCount = bomTotalMultiplyCount + (int) subBomComponent.getTreeDTO().getTreeAmount();
	    else
		bomTotalMultiplyCount = bomTotalMultiplyCount + subBomComponent.getTreeDTO().getBomTotalMultiplyCount() + (int) subBomComponent.getTreeDTO().getTreeAmount();
	}

	bomSubCount = bomSubCount + bomComponents.size();
	treeDTO.setBomSubCount(bomSubCount);
	treeDTO.setBomTotalCount(bomTotalCount);
	// calculator 수량 0인 Zero Node 처리
	// if(bomCompareDTO.getPartAmount() == 0D ){
	// bomTotalMultiplyCount = bomTotalMultiplyCount;
	// }else{
	bomTotalMultiplyCount = bomTotalMultiplyCount * (int) treeDTO.getTreeAmount();
	// }
	treeDTO.setBomTotalMultiplyCount(bomTotalMultiplyCount);
    }

    @Override
    public void print() {

	System.out.print("\n" + getTreeDTO().getDepth() + " ");
	for (int k = 0; k < getTreeDTO().getDepth(); k++) {
	    System.out.print(" ");
	}

	System.out.print(getTreeDTO().getTreeNumber() + " / " + getTreeDTO().getTreeAmount() + " / " + getTreeDTO().getPartAmountMultiply());

	Iterator<HierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    HierarchyComponent bomComponent = iterator.next();
	    bomComponent.print();
	}

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
	return bomComponents.size();
    }

    @Override
    public void setHasSameDepthNextNode(boolean hasSameDepthNextNode) {
	this.hasSameDepthNextNode = hasSameDepthNextNode;
    }

    @Override
    public void checkSameDepthNextNode() {

	Iterator<HierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    HierarchyComponent bomComponent = iterator.next();
	    bomComponent.setHasSameDepthNextNode(iterator.hasNext());
	    bomComponent.checkSameDepthNextNode();
	}
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
	
	// 하위 BOM 구성 불가
	Iterator<HierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    HierarchyComponent bomComponent = iterator.next();
	    bomComponent.getTreeDTO().setCanConfigPartBom(false);
	    bomComponent.checkCanConfigSubPartBom();
	}
    }
}
