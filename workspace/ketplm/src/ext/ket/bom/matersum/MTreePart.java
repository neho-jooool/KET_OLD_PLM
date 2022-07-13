package ext.ket.bom.matersum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ext.ket.bom.matersum.dto.MaterSumDTO;
import ext.ket.bom.matersum.util.BigDecimalUtil;
import ext.ket.shared.log.Kogger;

/**
 * assembly Node 의 component
 * 
 * @author Administrator
 * 
 */
public class MTreePart implements MHierarchyComponent, Serializable {

    protected List<MHierarchyComponent> bomComponents = new ArrayList<MHierarchyComponent>();
    private MTreeDTO treeDTO = null;
    private MTreeRootPart root = null;
    // next node create by parent
    private MHierarchyComponent parent = null;
    private int order;
    // next node create by iterator
    private boolean hasSameDepthNextNode;
    private StringBuffer nodeType = new StringBuffer();

    public MTreePart() {
    }

    @Override
    public void add(MHierarchyComponent bomComponent) {
	bomComponent.getTreeDTO().setDepth(treeDTO.getDepth() + 1);
	bomComponent.getTreeDTO().setPartAmountMultiply(treeDTO.getPartAmountMultiply() * bomComponent.getTreeDTO().getTreeAmount());
	bomComponents.add(bomComponent);

	if (MHierarchyComponent.NEXT_NODE_BY_PARENT) {
	    bomComponent.setOrder(bomComponents.size());
	    bomComponent.setParent(this);
	}

	bomComponent.setRoot(root);
	if (treeDTO.getDepth() + 1 > root.getMaxDepth())
	    root.setMaxDepth(treeDTO.getDepth() + 1);
    }

    @Override
    public void remove(MHierarchyComponent bomComponent) {
	bomComponents.remove(bomComponent);
    }

    @Override
    public MHierarchyComponent getChild(int i) {
	return (MHierarchyComponent) bomComponents.get(i);
    }

    @Override
    public Iterator<MHierarchyComponent> getIterator() {
	return bomComponents.iterator();
    }

    @Override
    public void subCalculate() {
	Iterator<MHierarchyComponent> iterator = bomComponents.iterator();
	int bomSubCount = 0;
	int bomTotalCount = 0;
	int bomTotalMultiplyCount = 0;
	while (iterator.hasNext()) {
	    MHierarchyComponent subBomComponent = iterator.next();
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

	Iterator<MHierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    MHierarchyComponent bomComponent = iterator.next();
	    bomComponent.print();
	}

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
	return bomComponents.size();
    }

    @Override
    public void setHasSameDepthNextNode(boolean hasSameDepthNextNode) {
	this.hasSameDepthNextNode = hasSameDepthNextNode;
    }

    @Override
    public void checkSameDepthNextNode() {

	Iterator<MHierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    MHierarchyComponent bomComponent = iterator.next();
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
    public MTreeDTO getTreeDTO() {
	return treeDTO;
    }

    @Override
    public void setTreeDTO(MTreeDTO bomDTO) {
	this.treeDTO = bomDTO;
    }

    // ///////////////////////////////////////////////////
    // Check KET CAD2BOM Relation
    // ///////////////////////////////////////////////////

    @Override 
    public void checkWeightMaterial() throws Exception {
	
	if(getTreeDTO().getDepth() == 0){
	    return;
	}
	
	Kogger.debug(getClass(), "@@@@@@@@@@ part calc:" + ((MaterSumDTO)this.treeDTO).getPartsNo());
	
	int scale = 3;
	
	// 원재료끼리 모음
	// 스크랩끼리 모음
	// 구매품끼리 모음
	// 반제품끼리 모음
	List<MaterSumDTO> rList = new ArrayList<MaterSumDTO>();
	List<MaterSumDTO> sList = new ArrayList<MaterSumDTO>();
	List<MaterSumDTO> pList = new ArrayList<MaterSumDTO>();
	
	List<MaterSumDTO> hList = new ArrayList<MaterSumDTO>();
	
	Iterator<MHierarchyComponent> iterator = bomComponents.iterator();
	while (iterator.hasNext()) {
	    MHierarchyComponent bomComponent = iterator.next();
	    MaterSumDTO dto = (MaterSumDTO)bomComponent.getTreeDTO();
	    String partNo = dto.getPartsNo();
	    if("S".equals(dto.getNodePartType())){ // partNo.startsWith("S")
		sList.add(dto);
	    }else if("R".equals(dto.getNodePartType())){ // partNo.startsWith("R")  if(partNo.startsWith("R1") || partNo.startsWith("R2")){
		// 구매품인지 원재료인지 구분한다.
		if("C".equals(dto.getNodePartClazType())){
		    pList.add(dto);
		}else{
		    rList.add(dto);
		}
		
	    }else if("W".equals(dto.getNodePartType())){ // 상품 - BOM 제외
		// 제외함
	    }else if("H".equals(dto.getNodePartType())){ // 반제품 - 수량이 소수점 오류라도 곱한 후에 소수점 3자리에서 버림
		hList.add(dto);
	    }else if("O".equals(dto.getNodePartType())){ // 기타 - 계산에서 제외
		// 제외함
	    }else if("K".equals(dto.getNodePartType())){ // 포장재 - 계산에서 제외 
		// 제외함
	    }else if("F".equals(dto.getNodePartType())){ // 제품 - 계산에서 제외
		// 제외함
	    }else{
		// Die, Mold 금형 제외
	    }
	}
	
	// 원재료, 스크랩, 구매품 계산
	// 계산식 : 총중량 = 원재료 중량(quantity) + 구매품(R1, R2 로 시작하지 않는 R) 중량(단위가 g이면 quantity / EA면 부품의 중량 * 개수 )
	//      스크랩중량 = 스크랩 중량 절대값
	//      부품중량  = 총중량 - 스크랩중량
	
	// 총중량 처리
	BigDecimal totalW = new BigDecimal("0.000");
	for(MaterSumDTO dto : rList){
	    totalW = BigDecimalUtil.plus(totalW, dto.getQuantity());
	    // 재질도 업데이트
	    if("H".equals(((MaterSumDTO)this.treeDTO).getNodePartType())){
		((MaterSumDTO) this.treeDTO).setNewType(dto.getMaterialType()); // TODO TKLEE 좀 더 조건이 있는지 확인 필요
		((MaterSumDTO) this.treeDTO).setNewMaterial(dto.getPreMaterial());
		((MaterSumDTO)this.treeDTO).setNewMaterial2(dto.getPrematerialDesc());
		// 원재료 Oid 업데이트 - rpartOid
		((MaterSumDTO)this.treeDTO).setrPartOid(dto.getrPartOid());
	    }
	}
	
	// 구매품 중량 정의 필요
	for(MaterSumDTO dto : pList){
	    // g일 경우 kg일 경우 mg일 경우 개수 1로 체크
	    // EA일 경우는 마스터 부품중량 가져와서 수량으로 곱한다.
	    String unit = dto.getUnit();
	    if("EA".equalsIgnoreCase(unit) || "KET_EA".equalsIgnoreCase(unit)){
		// 구매품의 부품마스터의 부품중량정보 * 개수
		BigDecimal tempW = BigDecimalUtil.mutipleWithRoundDown(dto.getPrerawW(), dto.getQuantity(), scale);
		totalW = BigDecimalUtil.plus(totalW, tempW);
	    }else if("G".equalsIgnoreCase(unit) || "KET_G".equalsIgnoreCase(unit)){
		totalW = BigDecimalUtil.plus(totalW, dto.getQuantity());
	    }else if("MG".equalsIgnoreCase(unit) || "KET_MG".equalsIgnoreCase(unit)){
		BigDecimal tempW = BigDecimalUtil.divideWithRoundDown(dto.getQuantity(), new BigDecimal("1000.000"), scale);
		totalW = BigDecimalUtil.plus(totalW, tempW);
	    }else if("KG".equalsIgnoreCase(unit) || "KET_KG".equalsIgnoreCase(unit)){
		BigDecimal tempW = BigDecimalUtil.mutipleWithRoundDown(dto.getQuantity(), new BigDecimal("1000.000"), scale);
		totalW = BigDecimalUtil.plus(totalW, tempW);
	    }
	}
	
	// 스크랩 중량 처리
	BigDecimal scrabW = new BigDecimal("0.000");
	for(MaterSumDTO dto : sList){
	    scrabW = BigDecimalUtil.plus(scrabW, dto.getQuantity());
	}
	
	// 부품중량 처리
	BigDecimal partW = BigDecimalUtil.minus(totalW, scrabW);
	
	// 반제품 계산 
	// 계산식 : 총중량, 스크랩중량, 부품중량 각각 * quantity
	for(MaterSumDTO dto : hList){
	    
	    // 총중량 계속 더하기
	    BigDecimal tempTotalW = BigDecimalUtil.mutipleWithRoundDown(dto.getNewTweight(), dto.getQuantity(), scale);
	    totalW = BigDecimalUtil.plus(totalW, tempTotalW);
	    
	    // 부품중량 계속 더하기
	    BigDecimal tempPartW = BigDecimalUtil.mutipleWithRoundDown(dto.getNewWeight(), dto.getQuantity(), scale);
	    partW = BigDecimalUtil.plus(partW, tempPartW);
	    
	    // 스크랩중량 계속 더하기
	    BigDecimal tempScrabW = BigDecimalUtil.mutipleWithRoundDown(dto.getNewSweight(), dto.getQuantity(), scale);
	    scrabW = BigDecimalUtil.plus(scrabW, tempScrabW);
	}
	
	((MaterSumDTO)this.treeDTO).setNewTweight(totalW);
	((MaterSumDTO)this.treeDTO).setNewWeight(partW);
	((MaterSumDTO)this.treeDTO).setNewSweight(scrabW);
	
	parent.checkWeightMaterial();
	
    }
    
}
