package ext.ket.edm.cad2bom.tree;

import java.io.Serializable;

import wt.epm.EPMDocument;
import wt.part.WTPart;

public class TreeDTO implements Serializable {

    /**
     * BOM 기본 정보
     */
    // depth
    private int depth = 0;
    // part의 수량 * 상위 수량곱
    private long partAmountMultiply;
    // 부품 BOM 중복 수 => 동일 모자가 존재하는지 여부
    private int multiSonCount = 0;
    // 하위 BOM 수
    private int bomSubCount = 0;
    // Filtered BOM 하위 BOM 수
    private int filteredBomSubCount = 0;
    // 단일 하위 수
    private int bomTotalCount = 0;
    // 단일 하위 수 * 수량
    private int bomTotalMultiplyCount = 0;
    // 하위 존재 여부
    private boolean leaf = false; // default는 false, HierachyComponent add시 TreePartItem만 true로 변경시킨다. 다른 소스는 신경쓰지 않음

    // 상위 부모 아래 다음 노드 여부
    private boolean nextNode = false;
    // 상위 부모 아래 다음 부품 노드 여부
    private boolean nextPartNode = false;
    // CAD3에 해당하는 부품이 있는지? 부품에 해당하는 cad3가 있는지?
    private boolean cadPartRelation = false;

    /**
     * 부품 일반 기본 정보
     */
    private EPMDocument epmDoc3D;
    private WTPart wtPart;
    private String treeNumber;
    private String treeName;
    private String treeType;
    // part의 bom 수량
    private long treeAmount;

    /**
     * 화면 Relation 결과값 파라미터
     */
    // 부품과 CAD는 연관될 수 있는가?
    private boolean canConfigPartCadRel = false;
    // 부품은 BOM을 구성할 수 있는가? /* 중요 : default를 true를 주어야 속도가 개선됨 */
    private boolean canConfigPartBom = true;

    // 기존에 CAD:PART가 동일한 연결을 가지는가?
    private boolean existCadPartRelation = false;
    
    // CAD:PART로 연결할 부품이 다른 CAD와 연결되어 있는가?
    private boolean existAnotherCadWithPartRelation = false;
    
    // CAD:PART로 연결할 도면이 기존에 다른 부품과 연결되어 있는가?
    private boolean existAnotherPartRelation = false;
    
    // CAD:PART로 연결할 부품이 상위Node + 연결한 부품으로 BOOM을 가지고 있는가?
    private boolean existPartBom = false;

    public int getDepth() {
	return depth;
    }

    public void setDepth(int depth) {
	this.depth = depth;
    }

    public long getPartAmountMultiply() {
	return partAmountMultiply;
    }

    public void setPartAmountMultiply(long partAmountMultiply) {
	this.partAmountMultiply = partAmountMultiply;
    }

    public int getMultiSonCount() {
	return multiSonCount;
    }

    public void setMultiSonCount(int multiSonCount) {
	this.multiSonCount = multiSonCount;
    }

    public int getBomSubCount() {
	return bomSubCount;
    }

    public void setBomSubCount(int bomSubCount) {
	this.bomSubCount = bomSubCount;
    }

    public int getBomTotalCount() {
	return bomTotalCount;
    }

    public void setBomTotalCount(int bomTotalCount) {
	this.bomTotalCount = bomTotalCount;
    }

    public int getBomTotalMultiplyCount() {
	return bomTotalMultiplyCount;
    }

    public void setBomTotalMultiplyCount(int bomTotalMultiplyCount) {
	this.bomTotalMultiplyCount = bomTotalMultiplyCount;
    }

    public boolean isNextNode() {
	return nextNode;
    }

    public void setNextNode(boolean nextNode) {
	this.nextNode = nextNode;
    }

    public boolean isNextPartNode() {
	return nextPartNode;
    }

    public void setNextPartNode(boolean nextPartNode) {
	this.nextPartNode = nextPartNode;
    }

    public boolean isCadPartRelation() {
	return cadPartRelation;
    }

    public void setCadPartRelation(boolean cadPartRelation) {
	this.cadPartRelation = cadPartRelation;
    }

    public WTPart getWtPart() {
	return wtPart;
    }

    public void setWtPart(WTPart wtPart) {
	this.wtPart = wtPart;
    }

    public long getTreeAmount() {
	return treeAmount;
    }

    public void setTreeAmount(long treeAmount) {
	this.treeAmount = treeAmount;
    }

    public EPMDocument getEpmDoc3D() {
	return epmDoc3D;
    }

    public void setEpmDoc3D(EPMDocument epmDoc3D) {
	this.epmDoc3D = epmDoc3D;
    }

    public boolean isLeaf() {
	return leaf;
    }

    public void setLeaf(boolean leaf) {
	this.leaf = leaf;
    }

    public int getFilteredBomSubCount() {
	return filteredBomSubCount;
    }

    public void setFilteredBomSubCount(int filteredBomSubCount) {
	this.filteredBomSubCount = filteredBomSubCount;
    }

    public boolean isCanConfigPartCadRel() {
	return canConfigPartCadRel;
    }

    public void setCanConfigPartCadRel(boolean canConfigPartCadRel) {
	this.canConfigPartCadRel = canConfigPartCadRel;
    }

    public boolean isCanConfigPartBom() {
	return canConfigPartBom;
    }

    public void setCanConfigPartBom(boolean canConfigPartBom) {
	this.canConfigPartBom = canConfigPartBom;
    }

    public boolean isExistCadPartRelation() {
	return existCadPartRelation;
    }

    public void setExistCadPartRelation(boolean existCadPartRelation) {
	this.existCadPartRelation = existCadPartRelation;
    }

    public boolean isExistAnotherCadWithPartRelation() {
	return existAnotherCadWithPartRelation;
    }

    public void setExistAnotherCadWithPartRelation(boolean existAnotherCadWithPartRelation) {
	this.existAnotherCadWithPartRelation = existAnotherCadWithPartRelation;
    }

    public boolean isExistAnotherPartRelation() {
	return existAnotherPartRelation;
    }

    public void setExistAnotherPartRelation(boolean existAnotherPartRelation) {
	this.existAnotherPartRelation = existAnotherPartRelation;
    }

    public boolean isExistPartBom() {
	return existPartBom;
    }

    public void setExistPartBom(boolean existPartBom) {
	this.existPartBom = existPartBom;
    }

    public String getTreeNumber() {
        return treeNumber;
    }

    public void setTreeNumber(String treeNumber) {
        this.treeNumber = treeNumber;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }
    
    

}
