package ext.ket.bom.matersum.util;

import java.util.ArrayList;
import java.util.List;

import wt.fc.ReferenceFactory;
import wt.fc.WTReference;
import wt.part.WTPart;
import wt.util.WTException;
import ext.ket.bom.matersum.MHierarchyComponent;
import ext.ket.bom.matersum.MTreePart;
import ext.ket.bom.matersum.MTreePartItem;
import ext.ket.bom.matersum.MTreeRootPart;
import ext.ket.bom.matersum.dto.MaterSumDTO;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.shared.log.Kogger;

final public class MTreePartUtil {

    private KETBOMQueryBean bean = new KETBOMQueryBean();
    private List<String> tagsPartList = null; 
    private String[] partNos = null;
    private String[] partsRevs = null;
    private String[] lvls = null;
    
    private ReferenceFactory rf = new ReferenceFactory();

    // Root를 만든다 - Root는 가상의 최상위임
    private MTreeRootPart createKetRootPart(String partNumber, String partName) throws Exception {

	MTreeRootPart ketRootPart = new MTreeRootPart();
	MaterSumDTO bomDto = new MaterSumDTO();
	bomDto.setTreeNumber(partNumber);
	bomDto.setTreeName(partName);
	// bomDTO.setLeaf(false);
	bomDto.setTreeType("ROOT");
	bomDto.setTreeAmount(1L);
	bomDto.setPartAmountMultiply(1L);
	// 최초 depth 지정
	// bomCompareDTO.getTreeDTO().setDepth(0); // 본래 0
	
	bomDto.setPartsNo(null);
	bomDto.setWtPart(null);
	
	//bomDto.setWtPart(prodTopPart);
	ketRootPart.setTreeDTO(bomDto);
	// root 지정

	ketRootPart.setRoot(ketRootPart);
	// set Next Node 방식 지정
	if (!MHierarchyComponent.NEXT_NODE_BY_PARENT)
	    ketRootPart.setHasSameDepthNextNode(false);

	return ketRootPart;
    }
    
    // 하위 Node를 가진 Ass'y를 만든다.
    private MTreePart createKetPart(MaterSumDTO bomDto) {

	MTreePart ketPart = new MTreePart();
	ketPart.setTreeDTO(bomDto);

	return ketPart;
    }

    // 하위 Node가 없는 Component를 만든다.
    private MTreePartItem createKetPartItem(MaterSumDTO bomDto) {

	MTreePartItem ketPartitem = new MTreePartItem();
	ketPartitem.setTreeDTO(bomDto);
	
	//Kogger.debug( getClass(),  "#####" + bomDto.getPartsNo() );

	return ketPartitem;
    }

    // partNos를 level별로 '|'문자를 사이에 붙여서 가져온다. ex) 0|1|4|5
    public List<String> getTagsPartList(){
	
	String[] tempLevelValue = new String[30];

	List<String> partList = new ArrayList<String>();
	
	for(int k = 0; k < partNos.length; k++){
	    
	    String partNo = partNos[k];
	    
	    int level = Integer.parseInt(lvls[k]);
	    
	    String newPartNo = ""; // partsRev
	    
	    for(int j=0; j<=level; j++){
		
		if(j == level){
		    if(newPartNo.equals("")){
			newPartNo = partNo;
		    }else{
			newPartNo = newPartNo + "|" + partNo;
		    }
		}else{
		    if("".equals(newPartNo)){
			newPartNo = tempLevelValue[j];
		    }else{
			newPartNo = newPartNo + "|" + tempLevelValue[j];
		    }
		}
		
	    }
	    
	    if(!newPartNo.endsWith("|")){
		newPartNo = newPartNo + "|";
	    }
	    
//	    Kogger.debug( getClass(), "newPartNo:" + newPartNo);
	    
	    partList.add(newPartNo);
	    
	    tempLevelValue[level] = partNo;
	    
	}
	
	return partList;
    }
    
    
    // 하위 구조 1개 리스트를 가져온다.
    private List<PartInfoTreeDTO> getSubPartStructure(String parentPartNo, int level) throws WTException {

	// 모델 Structure 가져오기
	List<PartInfoTreeDTO> infoList = new ArrayList<PartInfoTreeDTO>();

	String tempPart = parentPartNo + "|";
	if(!tempPart.endsWith("|")){
	    tempPart = tempPart + "|";
	}
	
	for(int k=0; k < tagsPartList.size(); k++){
//	    Kogger.debug( getClass(), "k:" + k);
	    String tempNo = tagsPartList.get(k);
	    
//		Kogger.debug( getClass(), "tempNo=>" + tempNo + "<=");
//		Kogger.debug( getClass(), "tempPart=>" + tempPart + "<=");
//		Kogger.debug( getClass(), "lvls[k]=>" + lvls[k] + "<=");
//		Kogger.debug( getClass(), "String.valueOf(level)=>" + String.valueOf(level) + "<=");
	    
	    if(tempNo.startsWith(tempPart) && String.valueOf(level).equals(lvls[k]) ){
		
//		Kogger.debug( getClass(), "######:" + tempNo + " => partNo:" + partNos[k] + " => tempRev:" + partsRevs[k] );
		PartInfoTreeDTO dto = new PartInfoTreeDTO();
		dto.setPartNo(partNos[k]);
		dto.setPartRev(partsRevs[k]);
		dto.setSortIdx(k);
		
		infoList.add(dto);
	    }
	}
	
	return infoList;
    }
    
    // Part Structure를 만든다.
    public MTreeRootPart makePartStructure(String[] partNos, String[] partsWcRevs, String[] lvls ) throws Exception {

	String rootPartNumber = "0";
	String rootPartName = "0";
	MTreeRootPart rootPart = createKetRootPart(rootPartNumber, rootPartName);

	this.partNos = partNos;
	this.partsRevs = partsWcRevs;
	this.lvls = lvls;
	
	List<String> tagsPartList = getTagsPartList();
	
	this.tagsPartList = tagsPartList;
	
	configSourcePartBOM(rootPart);

	return rootPart;
    }
    
    // Part Structure 만들기 시작
    private void configSourcePartBOM(MTreeRootPart rootPart) throws Exception {

	MHierarchyComponent sourceKetPart = null;

	int treeLevel = 1; // 다음 레벨을 가져온다.
	int sortIdx = 0;
	List<PartInfoTreeDTO> subPartStructure = getSubPartStructure(partNos[0], treeLevel);
	MaterSumDTO partSourceDto = setTreeDTO(partNos[0], partsRevs[0], sortIdx, true); // 최상위 부품 정의

	if (subPartStructure.size() > 0) {
	    // partSourceDto.setLeaf(false);
	    sourceKetPart = createKetPart(partSourceDto);
	    rootPart.add(sourceKetPart);
	    configSubPartBOM(treeLevel+1, partNos[0], sourceKetPart,  subPartStructure);

	} else {
	    // partSourceDto.setLeaf(true);
	    sourceKetPart = createKetPartItem(partSourceDto);
	    rootPart.add(sourceKetPart);
	}
    }

    // 하위 BOM의 재귀 호출
    private void configSubPartBOM(int level,
	    String iParentNo, MHierarchyComponent ketParentPart,  List<PartInfoTreeDTO> subPartStructure) throws Exception {

	boolean isRoot = false;
	for (PartInfoTreeDTO dto : subPartStructure) {

	    String partNo = dto.getPartNo();
	    String parentChildNo = iParentNo + "|" + partNo;
	    MaterSumDTO sonDto = setTreeDTO(partNo, dto.getPartRev(), dto.getSortIdx(), isRoot);

	    if (sonDto == null)
		continue;

	    List<PartInfoTreeDTO> tempSubPartStructure = getSubPartStructure(parentChildNo, level);
	    MHierarchyComponent sonKetPart = null;
	    if (tempSubPartStructure.size() > 0) {
		// sonDto.setLeaf(false);
		sonKetPart = createKetPart(sonDto);
		ketParentPart.add(sonKetPart);
		configSubPartBOM(level+1, parentChildNo, sonKetPart, tempSubPartStructure);
	    } else {
		// sonDto.setLeaf(true);
		sonKetPart = createKetPartItem(sonDto);
		ketParentPart.add(sonKetPart);
	    }
	}
    }

    private MaterSumDTO setTreeDTO(String partNo, String partRev, int sortIdx, boolean isStart) throws Exception {

	if (partNo == null)
	    throw new Exception("Tree PartNo Null Point Exception! ");

	MaterSumDTO sonDto = new MaterSumDTO();
	
	// 부품 기본 정보
	String partOid = bean.getPartOid(partNo, partRev);
	Kogger.debug(KETBomPartUtil.class, "partOid==>" + partOid);
	WTPart wtPart = getPart(partOid);

	sonDto.setWtPart(wtPart);
	sonDto.setTreeType("");
	sonDto.setTreeNumber(partNo);
	sonDto.setTreeName(wtPart.getName());
	
	// 미리 셋팅해줌
	sonDto.setPartOid(partOid);
	sonDto.setPartsNo(partNo);
	sonDto.setSortIdx(sortIdx);
	
	sonDto.setTreeAmount(1);
	    
	return sonDto;
    }
    
    public WTPart getPart(String oid) throws WTException {
	WTPart part = null;
	ReferenceFactory referencefactory = new ReferenceFactory();
	WTReference wtreference = referencefactory.getReference(oid);

	if (wtreference != null) {
	    part = (WTPart) wtreference.getObject();
	}
	return part;
    }

    class PartInfoTreeDTO {

	private String partNo;
	private String partRev;
	private int sortIdx;

	public PartInfoTreeDTO() {
	}

	public String getPartNo() {
	    return partNo;
	}

	public void setPartNo(String partNo) {
	    this.partNo = partNo;
	}

	public String getPartRev() {
	    return partRev;
	}

	public void setPartRev(String partRev) {
	    this.partRev = partRev;
	}

	public int getSortIdx() {
	    return sortIdx;
	}

	public void setSortIdx(int sortIdx) {
	    this.sortIdx = sortIdx;
	}
    }
    
    
}
