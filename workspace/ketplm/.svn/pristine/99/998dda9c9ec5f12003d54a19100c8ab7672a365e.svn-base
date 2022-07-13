package ext.ket.bom.matersum;

import java.util.Iterator;

/**
 * BOM 조작과 구성에 대한 정보를 Interface로 뺀다.
 */
public interface MHierarchyComponent {

    public static final boolean NEXT_NODE_BY_PARENT = true;
    public static final int TREE_PART_TYPE = 1;
    public static final int TREE_PART_ITEM_TYPE = 0;

    public void add(MHierarchyComponent hComponent);

    public void subCalculate();

    public MTreeDTO getTreeDTO();

    public void setTreeDTO(MTreeDTO bomCompareDTO);

    public MHierarchyComponent getParent();

    // ///////////////////////////////////////////////////
    // Common A, B BOM
    // ///////////////////////////////////////////////////

    public void remove(MHierarchyComponent hComponent);

    public MHierarchyComponent getChild(int i);

    public abstract Iterator<MHierarchyComponent> getIterator();

    public void print();

    // next node create by parent 관련 추가된 메소드
    public void setOrder(int order);

    public void setParent(MHierarchyComponent hComponent);

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
    public void setRoot(MTreeRootPart hComponent);

    public MTreeRootPart getRoot();

    // ///////////////////////////////////////////////////
    // Check KET CAD2BOM Relation
    // ///////////////////////////////////////////////////
    
    // 상위의 BOM에서 계산하도록 요청함.
    public void checkWeightMaterial() throws Exception;
    
    
}
