package ext.ket.edm.cad2bom.tree.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.structure.EPMMemberLink;
import wt.epm.structure.EPMStructureHelper;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.config.LatestConfigSpec;
import ext.ket.edm.cad2bom.service.internal.Cad2BomUtil;
import ext.ket.edm.cad2bom.tree.HierarchyComponent;
import ext.ket.edm.cad2bom.tree.TreePart;
import ext.ket.edm.cad2bom.tree.TreePartItem;
import ext.ket.edm.cad2bom.tree.TreeRootPart;
import ext.ket.edm.cad2bom.tree.dto.Cad2BomDTO;
import ext.ket.shared.log.Kogger;

final public class TreeCadUtil {

    private HashMap<String, Cad2BomDTO> epmPSMap = new HashMap<String, Cad2BomDTO>(); // P:S, SON DTO : ALL, For B
    private HashMap<String, Cad2BomDTO> epmNoMap = new HashMap<String, Cad2BomDTO>(); // P_NO, DTO For B
    private ReferenceFactory rf = new ReferenceFactory();

    // Cad Structure를 만든다.
    public TreeRootPart makeCadStructure(EPMDocument sourceEpmDoc, boolean isMoldSideCad) throws Exception {

	String rootPartNumber = "0";
	String rootPartName = "0";
	TreeRootPart rootPart = createKetRootPart(rootPartNumber, rootPartName, isMoldSideCad, sourceEpmDoc);

	configSourcePartBOM(rootPart, sourceEpmDoc);

	return rootPart;
    }

    // Root를 만든다 - Root는 가상의 최상위임
    private TreeRootPart createKetRootPart(String partNumber, String partName, boolean isMoldSideCad, EPMDocument sourceEpmDoc) throws Exception {

	TreeRootPart ketRootPart = new TreeRootPart();
	Cad2BomDTO bomDto = new Cad2BomDTO();
	bomDto.setTreeNumber(partNumber);
	bomDto.setTreeName(partName);
	// bomDTO.setLeaf(false);
	bomDto.setTreeType("ROOT");
	bomDto.setTreeAmount(1L);
	bomDto.setPartAmountMultiply(1L);
	// 최초 depth 지정
	// bomCompareDTO.getTreeDTO().setDepth(0); // 본래 0
	
	// 조건1 ) 일반 제품[비금형]일 경우에는
	// 조건2 ) 반제품일 경우
	// 상위의 제품(Fert)을 찾아서 넣어준다.
	if(!isMoldSideCad){
	    WTPart prodTopPart = Cad2BomUtil.getProdFertPart(sourceEpmDoc, isMoldSideCad);
	    if (prodTopPart != null) {
		Kogger.debug(getClass(), "#finde prodTopPartNumber:" + prodTopPart.getNumber());
		bomDto.setWtPart(prodTopPart);
	    }
	}
	
	ketRootPart.setTreeDTO(bomDto);
	// root 지정
	ketRootPart.setRoot(ketRootPart);
	// set Next Node 방식 지정
	if (!HierarchyComponent.NEXT_NODE_BY_PARENT)
	    ketRootPart.setHasSameDepthNextNode(false);

	return ketRootPart;
    }

    // 하위 Node를 가진 Ass'y를 만든다.
    private TreePart createKetPart(Cad2BomDTO bomDto) {

	TreePart ketPart = new TreePart();
	ketPart.setTreeDTO(bomDto);

	return ketPart;
    }

    // 하위 Node가 없는 Component를 만든다.
    private TreePartItem createKetPartItem(Cad2BomDTO bomDto) {

	TreePartItem ketPartitem = new TreePartItem();
	ketPartitem.setTreeDTO(bomDto);

	return ketPartitem;
    }

    // 하위 구조 1개 리스트를 가져온다.
    private List<ModelTreeDTO> getSubPartStructure(EPMDocument epm3d) throws WTException {

	// 모델 Structure 가져오기
	List<ModelTreeDTO> epmDocList = new ArrayList<ModelTreeDTO>();
	LatestConfigSpec spec = new LatestConfigSpec();
	QueryResult result = EPMStructureHelper.service.navigateUsesToIteration(epm3d, null, false, spec);

	if (result.size() > 0) {

	    while (result.hasMoreElements()) {

		Persistable[] persist = (Persistable[]) result.nextElement();
		EPMMemberLink memberLink = (EPMMemberLink) persist[0];
		EPMDocument modelDoc = (EPMDocument) persist[1];

		if (!memberLink.isRequired())
		    continue;

		ModelTreeDTO dto = new ModelTreeDTO();
		dto.setEpmDocument(modelDoc);
		dto.setMemberLink(memberLink);

		epmDocList.add(dto);

		Kogger.debug(getClass(), " | " + modelDoc.getNumber() + " | " + modelDoc.getVersionIdentifier().getValue() + "." + modelDoc.getIterationIdentifier().getValue() + " | " + modelDoc.isLatestIteration()
		        + " | " + modelDoc.isLatestIteration() + " | Fi");
	    }
	}

	return epmDocList;
    }

    // Cad Structure 만들기 시작
    private void configSourcePartBOM(TreeRootPart rootPart, EPMDocument sourceEpmDoc) throws Exception {

	HierarchyComponent sourceKetPart = null;

	List<ModelTreeDTO> subPartStructure = getSubPartStructure(sourceEpmDoc);

	if (subPartStructure.size() > 0) {
	    Cad2BomDTO partSourceDto = setTreeDTO(sourceEpmDoc, null, (EPMDocumentMaster) sourceEpmDoc.getMaster(), true);
	    // partSourceDto.setLeaf(false);
	    sourceKetPart = createKetPart(partSourceDto);
	    rootPart.add(sourceKetPart);
	    configSubPartBOM(sourceKetPart, sourceEpmDoc, sourceKetPart.getTreeDTO().getTreeNumber(), subPartStructure);

	} else {

	    Cad2BomDTO partSourceDto = setTreeDTO(sourceEpmDoc, null, (EPMDocumentMaster) sourceEpmDoc.getMaster(), true);
	    // partSourceDto.setLeaf(true);
	    sourceKetPart = createKetPartItem(partSourceDto);
	    rootPart.add(sourceKetPart);
	}
    }

    // 하위 BOM의 재귀 호출
    private void configSubPartBOM(HierarchyComponent ketParentPart, EPMDocument parentEpm, String iParentNo, List<ModelTreeDTO> subModelStructure) throws Exception {

	for (ModelTreeDTO dto : subModelStructure) {

	    EPMMemberLink epmMemberLink = dto.getMemberLink();
	    EPMDocument epmDocument = dto.getEpmDocument();
	    EPMDocumentMaster epmDocMaster = (EPMDocumentMaster) dto.getEpmDocument().getMaster();
	    String parentChildNo = iParentNo + ":" + epmDocMaster.getNumber();

	    if (epmPSMap.containsKey(parentChildNo)) {
		
		Cad2BomDTO tempDto = epmPSMap.get(parentChildNo);
		// 중요 - CAD Structure 상의 하위 개수를 추가한다.
		tempDto.setTreeAmount(tempDto.getTreeAmount() + 1L);

	    } else {

		Cad2BomDTO sonDto = setTreeDTO(epmDocument, epmMemberLink, epmDocMaster, false);

		if (sonDto == null)
		    continue;

		if (!epmNoMap.containsKey(sonDto.getTreeNumber())) {
		    epmNoMap.put(sonDto.getTreeNumber(), sonDto);
		}

		epmPSMap.put(parentChildNo, sonDto);

		List<ModelTreeDTO> tempSubPartStructure = getSubPartStructure(sonDto.getEpmDoc3D());
		HierarchyComponent sonKetPart = null;
		if (tempSubPartStructure.size() > 0) {
		    // sonDto.setLeaf(false);
		    sonKetPart = createKetPart(sonDto);
		    ketParentPart.add(sonKetPart);
		    configSubPartBOM(sonKetPart, sonDto.getEpmDoc3D(), parentChildNo, tempSubPartStructure);
		} else {
		    // sonDto.setLeaf(true);
		    sonKetPart = createKetPartItem(sonDto);
		    ketParentPart.add(sonKetPart);
		}
	    }
	}
    }

    private Cad2BomDTO setTreeDTO(EPMDocument currentPart, EPMMemberLink epmMemberLink, EPMDocumentMaster epmDocMast, boolean isRoot) throws Exception {

	if (currentPart == null)
	    throw new Exception("Tree EPMDocument Null Point Exception! ");

	Cad2BomDTO sonDto = new Cad2BomDTO();
	// 부품 기본 정보
	sonDto.setEpmDoc3D(currentPart);
	sonDto.setTreeType("");
	sonDto.setTreeNumber(currentPart.getNumber());
	sonDto.setTreeName(currentPart.getName());

	if (epmMemberLink == null) {
	    if (isRoot)
		sonDto.setTreeAmount(1); // 최초 root 아래에 부품이 붙을 경우에 1개를 세팅해 준다.
	    else
		sonDto.setTreeAmount(0);
	} else
	    sonDto.setTreeAmount((long) epmMemberLink.getQuantity().getAmount());

	return sonDto;
    }

    class ModelTreeDTO {

	private EPMMemberLink memberLink;
	private EPMDocument epmDocument;

	public ModelTreeDTO() {
	}

	public ModelTreeDTO(EPMMemberLink memberLink, EPMDocument epmDocument) {
	}

	public EPMMemberLink getMemberLink() {
	    return memberLink;
	}

	public void setMemberLink(EPMMemberLink memberLink) {
	    this.memberLink = memberLink;
	}

	public EPMDocument getEpmDocument() {
	    return epmDocument;
	}

	public void setEpmDocument(EPMDocument epmDocument) {
	    this.epmDocument = epmDocument;
	}

    }

}
