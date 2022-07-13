package ext.ket.edm.cad2bom.tree.test;

import ext.ket.edm.cad2bom.tree.HierarchyComponent;
import ext.ket.edm.cad2bom.tree.TreeDTO;
import ext.ket.edm.cad2bom.tree.TreePart;
import ext.ket.edm.cad2bom.tree.TreePartItem;
import ext.ket.edm.cad2bom.tree.TreeRootPart;


/**
 * BOM을 생성하고 controller의 로직을 점검한다.
 * @author Administrator
 */
final public class TreepartTester {
	
	public static TreeRootPart createTreeRootPart (String partNumber, String partName){
		TreeRootPart treeRootPart = new TreeRootPart();
		TreeDTO treeDTO = new TreeDTO();
		treeDTO.setTreeNumber(partNumber);
		treeDTO.setTreeName(partName);
		treeDTO.setLeaf(false);
		treeDTO.setTreeType("ROOT");
		treeDTO.setTreeAmount(1L);
		treeDTO.setPartAmountMultiply(1L);
		treeRootPart.setTreeDTO(treeDTO);
		
		return treeRootPart;
	}
	
	public static TreePart createTreePart (String partNumber, String partName, long partAmount){
		return createTreePart (partNumber, partName, partAmount, "ASSY");
	}
	
	public static TreePart createTreePart (String partNumber, String partName, long partAmount, String Type){
		TreePart treePart = new TreePart();
		TreeDTO treeDTO = new TreeDTO();
		treeDTO.setTreeNumber(partNumber);
		treeDTO.setTreeName(partName);
		treeDTO.setLeaf(false);
		treeDTO.setTreeType(Type);
		treeDTO.setTreeAmount(partAmount);
		treePart.setTreeDTO(treeDTO);
		
		return treePart;
	}
	
	public static TreePartItem createTreePartItem (String partNumber, String partName, long partAmount){
		TreePartItem treePartItem = new TreePartItem();
		TreeDTO treeDTO = new TreeDTO();
		treeDTO.setTreeNumber(partNumber);
		treeDTO.setTreeName(partName);
		treeDTO.setLeaf(true);
		treeDTO.setTreeType("COMP");
		treeDTO.setTreeAmount(partAmount);
		treePartItem.setTreeDTO(treeDTO);
		
		return treePartItem;
	}
	
	public static void main(String args[]) {

		long startTime = System.currentTimeMillis();
		
		TreeRootPart aRootTreeParts = createTreeRootPart ("0", "0 TEST");
		// 최초 depth 지정
		aRootTreeParts.getTreeDTO().setDepth(0);
		// 최초 root 지정
		aRootTreeParts.setRoot(aRootTreeParts);
		// 최초 nextNode 지정
		if(!HierarchyComponent.NEXT_NODE_BY_PARENT)
			aRootTreeParts.setHasSameDepthNextNode(false);
		
		TreePart aFscParts = createTreePart ("DMU0001", "DMU TEST", 1L, "FSC");
		
		aRootTreeParts.add(aFscParts);
		
		TreePart aModuleTreeParts1 = createTreePart ("MOD0001", "MOD1 TEST", 1L, "MODULE");
		TreePart aModuleTreeParts2 = createTreePart ("MOD0002", "MOD2 TEST", 1L, "MODULE");
		TreePart aModuleTreeParts3 = createTreePart ("MOD0003", "MOD3 TEST", 1L, "MODULE");
		
		aFscParts.add(aModuleTreeParts1);
		aFscParts.add(aModuleTreeParts2);
		aFscParts.add(aModuleTreeParts3);
		
		TreePart aUvTreeParts1 = createTreePart ("UV0001", "UV1 TEST", 1L, "UPGVC");
		TreePart aUvTreeParts2 = createTreePart ("UV0002", "UV2 TEST", 1L, "UPGVC");
		TreePart aUvTreeParts3 = createTreePart ("UV0003", "UV3 TEST", 1L, "UPGVC");
		
		aModuleTreeParts1.add(aUvTreeParts1);
		aModuleTreeParts2.add(aUvTreeParts2);
		aModuleTreeParts3.add(aUvTreeParts3);

		TreePart aAssyTreeParts11 = createTreePart ("ASSY0011", "ASSY1-1 TEST", 2L);
		TreePartItem aCompTreeParts12 = createTreePartItem ("COMP0012", "COMP1-2 TEST", 3L);
		TreePart aAssyTreeParts22 = createTreePart ("ASSY0022", "ASSY2-2 TEST", 2L);
		TreePart aAssyTreeParts23 = createTreePart ("ASSY0023", "ASSY2-3 TEST", 2L);
		TreePartItem aCompTreeParts24 = createTreePartItem ("COMP0024", "COMP2-4 TEST", 3L);
		TreePart aAssyTreeParts3 = createTreePart ("ASSY0003", "ASSY3 TEST", 2L);
		
		aUvTreeParts1.add(aAssyTreeParts11);//
		aUvTreeParts1.add(aCompTreeParts12);
		aUvTreeParts2.add(aAssyTreeParts22);//
		aUvTreeParts2.add(aAssyTreeParts23);//
		aUvTreeParts2.add(aCompTreeParts24);
		aUvTreeParts3.add(aAssyTreeParts3);//
		
		TreePartItem aCompTreeParts111 = createTreePartItem ("COMP0111", "COMP111 TEST", 2L);
		TreePart aAssyTreeParts222 = createTreePart ("ASSY0222", "ASSY222 TEST", 2L);
		TreePartItem aCompTreeParts223 = createTreePartItem ("COMP0223", "COMP223 TEST", 5L);
		TreePart aAssyTreeParts233 = createTreePart ("ASSY0233", "ASSY233 TEST", 4L);
		TreePartItem aCompTreeParts234 = createTreePartItem ("COMP0234", "COMP234 TEST", 3L);
		TreePartItem aCompTreeParts32 = createTreePartItem ("COMP0032", "COMP32 TEST", 7L);
		TreePartItem aCompTreeParts33 = createTreePartItem ("COMP0033", "COMP33 TEST", 2L);
		
		aAssyTreeParts11.add(aCompTreeParts111);
		aAssyTreeParts22.add(aAssyTreeParts222);//
		aAssyTreeParts22.add(aCompTreeParts223);
		aAssyTreeParts23.add(aAssyTreeParts233);//
		aAssyTreeParts23.add(aCompTreeParts234);
		aAssyTreeParts3.add(aCompTreeParts32);
		aAssyTreeParts3.add(aCompTreeParts33);
		
		TreePartItem aCompTreeParts2222 = createTreePartItem ("COMP2222", "COMP2222 TEST", 2L);
		TreePartItem aCompTreeParts2333 = createTreePartItem ("COMP2333", "COMP2333 TEST", 3L);
		TreePartItem aCompTreeParts2334 = createTreePartItem ("COMP2334", "COMP2334 TEST", 5L);
		TreePartItem aCompTreeParts2336 = createTreePartItem ("COMP2336", "COMP2336 TEST", 3L);
		
		aAssyTreeParts222.add(aCompTreeParts2222);
		aAssyTreeParts233.add(aCompTreeParts2333);
		aAssyTreeParts233.add(aCompTreeParts2334);
		aAssyTreeParts233.add(aCompTreeParts2336);
		
		/////////////////////////////////////////////////////
		// target parts
		/////////////////////////////////////////////////////
		TreeRootPart bRootTreeParts = createTreeRootPart ("0", "0 TEST");
		// 최초 depth 지정
		bRootTreeParts.getTreeDTO().setDepth(0);
		// 최초 root 지정
		bRootTreeParts.setRoot(bRootTreeParts);
		// 최초 nextNode 지정
		if(!HierarchyComponent.NEXT_NODE_BY_PARENT)
			bRootTreeParts.setHasSameDepthNextNode(false);
		
		TreePart bFscParts = createTreePart ("DMU0001", "DMU TEST", 1L, "FSC");
		
		bRootTreeParts.add(bFscParts);
		
		TreePart bModuleTreeParts1 = createTreePart ("MOD0001", "MOD1 TEST", 1L, "MODULE");
		TreePart bModuleTreeParts2 = createTreePart ("MOD0002", "MOD2 TEST", 1L, "MODULE");
		TreePart bModuleTreeParts3 = createTreePart ("MOD0003", "MOD3 TEST", 1L, "MODULE");
		
		bFscParts.add(bModuleTreeParts1);
		bFscParts.add(bModuleTreeParts2);
		bFscParts.add(bModuleTreeParts3);
		
		TreePart bUvTreeParts1 = createTreePart ("UV0001", "UV1 TEST", 1L, "UPGVC");
		TreePart bUvTreeParts2 = createTreePart ("UV0002", "UV2 TEST", 1L, "UPGVC");
		TreePart bUvTreeParts3 = createTreePart ("UV0003", "UV3 TEST", 1L, "UPGVC");
		
		bModuleTreeParts1.add(bUvTreeParts1);
		bModuleTreeParts2.add(bUvTreeParts2);
		bModuleTreeParts3.add(bUvTreeParts3);

		TreePart bAssyTreeParts11 = createTreePart ("ASSY0011", "ASSY1-1 TEST", 2L);
		TreePartItem bCompTreeParts12 = createTreePartItem ("COMP0012", "COMP1-2 TEST", 3L);
		TreePart bAssyTreeParts22 = createTreePart ("ASSY0022", "ASSY2-2 TEST", 0L);
		TreePart bAssyTreeParts23 = createTreePart ("ASSY0023", "ASSY2-3 TEST", 0L);
		TreePartItem bCompTreeParts24 = createTreePartItem ("COMP0024", "COMP2-4 TEST", 3L);
		TreePart bAssyTreeParts3 = createTreePart ("ASSY0003", "ASSY3 TEST", 2L);
		
		bUvTreeParts1.add(bAssyTreeParts11);//
		bUvTreeParts1.add(bCompTreeParts12);
		bUvTreeParts2.add(bAssyTreeParts22);//
		bUvTreeParts2.add(bAssyTreeParts23);//
		bUvTreeParts2.add(bCompTreeParts24);
		bUvTreeParts3.add(bAssyTreeParts3);//
		
		TreePartItem bCompTreeParts111 = createTreePartItem ("COMP0111", "COMP111 TEST", 2L);
		TreePart bAssyTreeParts222 = createTreePart ("ASSY0222", "ASSY222 TEST", 2L);
		TreePartItem bCompTreeParts223 = createTreePartItem ("COMP0223", "COMP223 TEST", 5L);
		TreePart bAssyTreeParts233 = createTreePart ("ASSY0233", "ASSY233 TEST", 0L);
		TreePartItem bCompTreeParts234 = createTreePartItem ("COMP0234", "COMP234 TEST", 3L);
		TreePartItem bCompTreeParts32 = createTreePartItem ("COMP0032", "COMP32 TEST", 7L);
		TreePartItem bCompTreeParts33 = createTreePartItem ("COMP0033", "COMP33 TEST", 2L);
		
		bAssyTreeParts11.add(bCompTreeParts111);
		bAssyTreeParts22.add(bAssyTreeParts222);//
		bAssyTreeParts22.add(bCompTreeParts223);
		bAssyTreeParts23.add(bAssyTreeParts233);//
		bAssyTreeParts23.add(bCompTreeParts234);
		bAssyTreeParts3.add(bCompTreeParts32);
		bAssyTreeParts3.add(bCompTreeParts33);
		
		TreePartItem bCompTreeParts2222 = createTreePartItem ("COMP2222", "COMP2222 TEST", 2L);
		TreePartItem bCompTreeParts2333 = createTreePartItem ("COMP2333", "COMP2333 TEST", 0L);
		TreePartItem bCompTreeParts2334 = createTreePartItem ("COMP2334", "COMP2334 TEST", 0L);
		TreePartItem bCompTreeParts2335 = createTreePartItem ("COMP2335", "COMP2335 TEST", 2L);
		
		bAssyTreeParts222.add(bCompTreeParts2222);
		bAssyTreeParts233.add(bCompTreeParts2333);
		bAssyTreeParts233.add(bCompTreeParts2334);
		bAssyTreeParts233.add(bCompTreeParts2335);

		TreePartControl treePartController = new TreePartControl(bRootTreeParts);

		treePartController.printParts();
		treePartController.printAllParts();
		treePartController.printSubCountBom();
		
//		long endTime = System.currentTimeMillis();
//		Kogger.debug(getClass(), "completed by spent Time :" + Long.toString(endTime-startTime) + "ms");
		
	}
	
}
