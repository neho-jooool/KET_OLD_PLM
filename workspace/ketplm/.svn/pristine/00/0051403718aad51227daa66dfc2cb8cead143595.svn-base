package ext.ket.edm.cad2bom.tree;

import java.util.Iterator;

/**
 * BOM 조작과 구성에 대한 정보를 Interface로 뺀다.
 */
public interface HierarchyComponent {

    public static final boolean NEXT_NODE_BY_PARENT = true;
    public static final int TREE_PART_TYPE = 1;
    public static final int TREE_PART_ITEM_TYPE = 0;

    public void add(HierarchyComponent hComponent);

    public void subCalculate();

    public TreeDTO getTreeDTO();

    public void setTreeDTO(TreeDTO bomCompareDTO);

    public HierarchyComponent getParent();

    // ///////////////////////////////////////////////////
    // Common A, B BOM
    // ///////////////////////////////////////////////////

    public void remove(HierarchyComponent hComponent);

    public HierarchyComponent getChild(int i);

    public abstract Iterator<HierarchyComponent> getIterator();

    public void print();

    // next node create by parent 관련 추가된 메소드
    public void setOrder(int order);

    public void setParent(HierarchyComponent hComponent);

    public int getSubNodeCount();

    // next node create by iterator
    public void setHasSameDepthNextNode(boolean hasSameDepthNextNode);

    public void checkSameDepthNextNode();

    // nextNode Result
    public boolean hasSameDepthNextNode();

    // node expression
    public void addNodeType(String nodeType);

    public String getNodeType();

    // max node 정보 관련
    public void setRoot(TreeRootPart hComponent);

    public TreeRootPart getRoot();

    // ///////////////////////////////////////////////////
    // Check KET CAD2BOM Relation
    // ///////////////////////////////////////////////////
    
    // PART-CAD Mast Relation 할 수 있는지 없는지 체크
    public void checkCanConfigPartCadRel() throws Exception;
    
    // 하위에서 PART-BOM 구성 불가
    public void checkCanConfigSubPartBom() throws Exception;
    
}
