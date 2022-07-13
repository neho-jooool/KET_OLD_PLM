package ext.ket.bom.matersum.test;

import ext.ket.bom.matersum.MHierarchyComponent;
import ext.ket.bom.matersum.MTreeDTO;
import ext.ket.bom.matersum.MTreePart;
import ext.ket.bom.matersum.MTreePartItem;
import ext.ket.bom.matersum.MTreeRootPart;


/**
 * BOM을 생성하고 controller의 로직을 점검한다.
 * @author Administrator
 */
final public class MTreepartTester {
	
	public static MTreeRootPart createMTreeRootPart (String partNumber, String partName){
		MTreeRootPart treeRootPart = new MTreeRootPart();
		MTreeDTO treeDTO = new MTreeDTO();
		treeDTO.setTreeNumber(partNumber);
		treeDTO.setTreeName(partName);
		treeDTO.setLeaf(false);
		treeDTO.setTreeType("ROOT");
		treeDTO.setTreeAmount(1L);
		treeDTO.setPartAmountMultiply(1L);
		treeRootPart.setTreeDTO(treeDTO);
		
		return treeRootPart;
	}
	
	public static MTreePart createMTreePart (String partNumber, String partName, long partAmount){
		return createMTreePart (partNumber, partName, partAmount, "ASSY");
	}
	
	public static MTreePart createMTreePart (String partNumber, String partName, long partAmount, String Type){
		MTreePart treePart = new MTreePart();
		MTreeDTO treeDTO = new MTreeDTO();
		treeDTO.setTreeNumber(partNumber);
		treeDTO.setTreeName(partName);
		treeDTO.setLeaf(false);
		treeDTO.setTreeType(Type);
		treeDTO.setTreeAmount(partAmount);
		treePart.setTreeDTO(treeDTO);
		
		return treePart;
	}
	
	public static MTreePartItem createMTreePartItem (String partNumber, String partName, long partAmount){
		MTreePartItem treePartItem = new MTreePartItem();
		MTreeDTO treeDTO = new MTreeDTO();
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
		
		MTreeRootPart aRootMTreeParts = createMTreeRootPart ("0", "0 TEST");
		// 최초 depth 지정
		aRootMTreeParts.getTreeDTO().setDepth(0);
		// 최초 root 지정
		aRootMTreeParts.setRoot(aRootMTreeParts);
		// 최초 nextNode 지정
		if(!MHierarchyComponent.NEXT_NODE_BY_PARENT)
			aRootMTreeParts.setHasSameDepthNextNode(false);
		
		MTreePart aFscParts = createMTreePart ("DMU0001", "DMU TEST", 1L, "FSC");
		
		aRootMTreeParts.add(aFscParts);
		
		MTreePart aModuleMTreeParts1 = createMTreePart ("MOD0001", "MOD1 TEST", 1L, "MODULE");
		MTreePart aModuleMTreeParts2 = createMTreePart ("MOD0002", "MOD2 TEST", 1L, "MODULE");
		MTreePart aModuleMTreeParts3 = createMTreePart ("MOD0003", "MOD3 TEST", 1L, "MODULE");
		
		aFscParts.add(aModuleMTreeParts1);
		aFscParts.add(aModuleMTreeParts2);
		aFscParts.add(aModuleMTreeParts3);
		
		MTreePart aUvMTreeParts1 = createMTreePart ("UV0001", "UV1 TEST", 1L, "UPGVC");
		MTreePart aUvMTreeParts2 = createMTreePart ("UV0002", "UV2 TEST", 1L, "UPGVC");
		MTreePart aUvMTreeParts3 = createMTreePart ("UV0003", "UV3 TEST", 1L, "UPGVC");
		
		aModuleMTreeParts1.add(aUvMTreeParts1);
		aModuleMTreeParts2.add(aUvMTreeParts2);
		aModuleMTreeParts3.add(aUvMTreeParts3);

		MTreePart aAssyMTreeParts11 = createMTreePart ("ASSY0011", "ASSY1-1 TEST", 2L);
		MTreePartItem aCompMTreeParts12 = createMTreePartItem ("COMP0012", "COMP1-2 TEST", 3L);
		MTreePart aAssyMTreeParts22 = createMTreePart ("ASSY0022", "ASSY2-2 TEST", 2L);
		MTreePart aAssyMTreeParts23 = createMTreePart ("ASSY0023", "ASSY2-3 TEST", 2L);
		MTreePartItem aCompMTreeParts24 = createMTreePartItem ("COMP0024", "COMP2-4 TEST", 3L);
		MTreePart aAssyMTreeParts3 = createMTreePart ("ASSY0003", "ASSY3 TEST", 2L);
		
		aUvMTreeParts1.add(aAssyMTreeParts11);//
		aUvMTreeParts1.add(aCompMTreeParts12);
		aUvMTreeParts2.add(aAssyMTreeParts22);//
		aUvMTreeParts2.add(aAssyMTreeParts23);//
		aUvMTreeParts2.add(aCompMTreeParts24);
		aUvMTreeParts3.add(aAssyMTreeParts3);//
		
		MTreePartItem aCompMTreeParts111 = createMTreePartItem ("COMP0111", "COMP111 TEST", 2L);
		MTreePart aAssyMTreeParts222 = createMTreePart ("ASSY0222", "ASSY222 TEST", 2L);
		MTreePartItem aCompMTreeParts223 = createMTreePartItem ("COMP0223", "COMP223 TEST", 5L);
		MTreePart aAssyMTreeParts233 = createMTreePart ("ASSY0233", "ASSY233 TEST", 4L);
		MTreePartItem aCompMTreeParts234 = createMTreePartItem ("COMP0234", "COMP234 TEST", 3L);
		MTreePartItem aCompMTreeParts32 = createMTreePartItem ("COMP0032", "COMP32 TEST", 7L);
		MTreePartItem aCompMTreeParts33 = createMTreePartItem ("COMP0033", "COMP33 TEST", 2L);
		
		aAssyMTreeParts11.add(aCompMTreeParts111);
		aAssyMTreeParts22.add(aAssyMTreeParts222);//
		aAssyMTreeParts22.add(aCompMTreeParts223);
		aAssyMTreeParts23.add(aAssyMTreeParts233);//
		aAssyMTreeParts23.add(aCompMTreeParts234);
		aAssyMTreeParts3.add(aCompMTreeParts32);
		aAssyMTreeParts3.add(aCompMTreeParts33);
		
		MTreePartItem aCompMTreeParts2222 = createMTreePartItem ("COMP2222", "COMP2222 TEST", 2L);
		MTreePartItem aCompMTreeParts2333 = createMTreePartItem ("COMP2333", "COMP2333 TEST", 3L);
		MTreePartItem aCompMTreeParts2334 = createMTreePartItem ("COMP2334", "COMP2334 TEST", 5L);
		MTreePartItem aCompMTreeParts2336 = createMTreePartItem ("COMP2336", "COMP2336 TEST", 3L);
		
		aAssyMTreeParts222.add(aCompMTreeParts2222);
		aAssyMTreeParts233.add(aCompMTreeParts2333);
		aAssyMTreeParts233.add(aCompMTreeParts2334);
		aAssyMTreeParts233.add(aCompMTreeParts2336);
		
		/////////////////////////////////////////////////////
		// target parts
		/////////////////////////////////////////////////////
		MTreeRootPart bRootMTreeParts = createMTreeRootPart ("0", "0 TEST");
		// 최초 depth 지정
		bRootMTreeParts.getTreeDTO().setDepth(0);
		// 최초 root 지정
		bRootMTreeParts.setRoot(bRootMTreeParts);
		// 최초 nextNode 지정
		if(!MHierarchyComponent.NEXT_NODE_BY_PARENT)
			bRootMTreeParts.setHasSameDepthNextNode(false);
		
		MTreePart bFscParts = createMTreePart ("DMU0001", "DMU TEST", 1L, "FSC");
		
		bRootMTreeParts.add(bFscParts);
		
		MTreePart bModuleMTreeParts1 = createMTreePart ("MOD0001", "MOD1 TEST", 1L, "MODULE");
		MTreePart bModuleMTreeParts2 = createMTreePart ("MOD0002", "MOD2 TEST", 1L, "MODULE");
		MTreePart bModuleMTreeParts3 = createMTreePart ("MOD0003", "MOD3 TEST", 1L, "MODULE");
		
		bFscParts.add(bModuleMTreeParts1);
		bFscParts.add(bModuleMTreeParts2);
		bFscParts.add(bModuleMTreeParts3);
		
		MTreePart bUvMTreeParts1 = createMTreePart ("UV0001", "UV1 TEST", 1L, "UPGVC");
		MTreePart bUvMTreeParts2 = createMTreePart ("UV0002", "UV2 TEST", 1L, "UPGVC");
		MTreePart bUvMTreeParts3 = createMTreePart ("UV0003", "UV3 TEST", 1L, "UPGVC");
		
		bModuleMTreeParts1.add(bUvMTreeParts1);
		bModuleMTreeParts2.add(bUvMTreeParts2);
		bModuleMTreeParts3.add(bUvMTreeParts3);

		MTreePart bAssyMTreeParts11 = createMTreePart ("ASSY0011", "ASSY1-1 TEST", 2L);
		MTreePartItem bCompMTreeParts12 = createMTreePartItem ("COMP0012", "COMP1-2 TEST", 3L);
		MTreePart bAssyMTreeParts22 = createMTreePart ("ASSY0022", "ASSY2-2 TEST", 0L);
		MTreePart bAssyMTreeParts23 = createMTreePart ("ASSY0023", "ASSY2-3 TEST", 0L);
		MTreePartItem bCompMTreeParts24 = createMTreePartItem ("COMP0024", "COMP2-4 TEST", 3L);
		MTreePart bAssyMTreeParts3 = createMTreePart ("ASSY0003", "ASSY3 TEST", 2L);
		
		bUvMTreeParts1.add(bAssyMTreeParts11);//
		bUvMTreeParts1.add(bCompMTreeParts12);
		bUvMTreeParts2.add(bAssyMTreeParts22);//
		bUvMTreeParts2.add(bAssyMTreeParts23);//
		bUvMTreeParts2.add(bCompMTreeParts24);
		bUvMTreeParts3.add(bAssyMTreeParts3);//
		
		MTreePartItem bCompMTreeParts111 = createMTreePartItem ("COMP0111", "COMP111 TEST", 2L);
		MTreePart bAssyMTreeParts222 = createMTreePart ("ASSY0222", "ASSY222 TEST", 2L);
		MTreePartItem bCompMTreeParts223 = createMTreePartItem ("COMP0223", "COMP223 TEST", 5L);
		MTreePart bAssyMTreeParts233 = createMTreePart ("ASSY0233", "ASSY233 TEST", 0L);
		MTreePartItem bCompMTreeParts234 = createMTreePartItem ("COMP0234", "COMP234 TEST", 3L);
		MTreePartItem bCompMTreeParts32 = createMTreePartItem ("COMP0032", "COMP32 TEST", 7L);
		MTreePartItem bCompMTreeParts33 = createMTreePartItem ("COMP0033", "COMP33 TEST", 2L);
		
		bAssyMTreeParts11.add(bCompMTreeParts111);
		bAssyMTreeParts22.add(bAssyMTreeParts222);//
		bAssyMTreeParts22.add(bCompMTreeParts223);
		bAssyMTreeParts23.add(bAssyMTreeParts233);//
		bAssyMTreeParts23.add(bCompMTreeParts234);
		bAssyMTreeParts3.add(bCompMTreeParts32);
		bAssyMTreeParts3.add(bCompMTreeParts33);
		
		MTreePartItem bCompMTreeParts2222 = createMTreePartItem ("COMP2222", "COMP2222 TEST", 2L);
		MTreePartItem bCompMTreeParts2333 = createMTreePartItem ("COMP2333", "COMP2333 TEST", 0L);
		MTreePartItem bCompMTreeParts2334 = createMTreePartItem ("COMP2334", "COMP2334 TEST", 0L);
		MTreePartItem bCompMTreeParts2335 = createMTreePartItem ("COMP2335", "COMP2335 TEST", 2L);
		
		bAssyMTreeParts222.add(bCompMTreeParts2222);
		bAssyMTreeParts233.add(bCompMTreeParts2333);
		bAssyMTreeParts233.add(bCompMTreeParts2334);
		bAssyMTreeParts233.add(bCompMTreeParts2335);

		MTreePartControl treePartController = new MTreePartControl(bRootMTreeParts);

		treePartController.printParts();
		treePartController.printAllParts();
		treePartController.printSubCountBom();
		
//		long endTime = System.currentTimeMillis();
//		Kogger.debug(getClass(), "completed by spent Time :" + Long.toString(endTime-startTime) + "ms");
		
	}
	
}
