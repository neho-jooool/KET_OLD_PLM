package ext.ket.bom.matersum.test;

import java.util.Iterator;

import ext.ket.bom.matersum.MCompositeIterator;
import ext.ket.bom.matersum.MHierarchyComponent;
import ext.ket.bom.matersum.MTreeRootPart;
import ext.ket.shared.log.Kogger;

/**
 * BOM 간의 계산, 비교 등의 Control 역할을 담당한다.
 * @author Administrator
 *
 */
final public class MTreePartControl {

	MHierarchyComponent allParts;

	public MTreePartControl(MHierarchyComponent allParts) {
		this.allParts = allParts;
	}
	
	/**
	 * commonUtil로 가야
	 * @param arg
	 * @param size
	 * @param str
	 * @return
	 */
	public String getReSizeStr(int arg, int size, String str){
		
		int gap;
		Integer temp = (Integer)arg;
		if(size == temp.toString().length())
			return temp.toString();
		else if((gap = size - temp.toString().length()) > 0){
			StringBuffer buffer = new StringBuffer();
			for(int k=0; k<gap; k++){
				buffer.append(str);
			}
			
			return buffer.toString() + temp.toString();
		}else
			return temp.toString();
	}

	public void printParts() {
		Kogger.debug(getClass(), "\nTree PART List\n----");
		allParts.print();
		Kogger.debug(getClass(), "\n\n");
	}
	
	public void setAllParts() {
		
		MTreeRootPart root = (MTreeRootPart)allParts;
		int maxDepth = root.getMaxDepth();
		boolean []levelNodeCheck = new boolean[maxDepth];
		for(boolean tempBool : levelNodeCheck)
			tempBool = false;
		
		if(!MHierarchyComponent.NEXT_NODE_BY_PARENT)
			allParts.checkSameDepthNextNode();
		
		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());
		
		while (iterator.hasNext()) {
			
			MHierarchyComponent bomComponent = iterator.next();
			int depth = bomComponent.getTreeDTO().getDepth();
			try {
				
				for(int k = 0; k < depth; k++){
					
					if(k + 1 == depth){
						if(bomComponent.hasSameDepthNextNode()){
							levelNodeCheck[k] = true;
							if( !bomComponent.getTreeDTO().isLeaf()){
								bomComponent.addNodeType("5");
							}else{
								bomComponent.addNodeType("4");
							}
						}else{
							levelNodeCheck[k] = false;
							if( !bomComponent.getTreeDTO().isLeaf()){
								bomComponent.addNodeType("3");
							}else{
								bomComponent.addNodeType("2");
							}
						}
					}else{
						if(levelNodeCheck[k]){
							bomComponent.addNodeType("1");
						}else{
							bomComponent.addNodeType("0");
						}
					}
				}
				
			} catch (UnsupportedOperationException e) {
				Kogger.error(getClass(), e);
			}
		}
	}
	
	public void printAllParts() {
		
		setAllParts();
		
		/*
		 * 
		 * treeArrMid.gif "┝" + 삼각형 : 5
		 * treeNodeMid.gif "├"       : 4
		 * 
		 * treeArrBtm.gif  "┕" + 삼각형 :3
		 * treeNodeBtm.gif "└"        :2
		 * 
		 * treeNodeNull.gif  "│"      :1
		 * treeNodeEmpty.gif  "　"     "0
		 * 
		 */
		Kogger.debug(getClass(), "\nTree PART BOM\n----");
		MTreeRootPart root = (MTreeRootPart)allParts;
		int maxDepth = root.getMaxDepth();
		boolean []levelNodeCheck = new boolean[maxDepth];
		for(boolean tempBool : levelNodeCheck)
			tempBool = false;
		
		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());
		
		int idx = 1;
		while (iterator.hasNext()) {
			
			MHierarchyComponent bomComponent = iterator.next();
			try {
				
				System.out.print("\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ");
				String nodeType = bomComponent.getNodeType();
				for(int k=0; k<nodeType.length(); k++){
					switch(nodeType.charAt(k)){
						case '0':
							System.out.print("　");
							break;
						case '1':
							System.out.print("│");
							break;
						case '2':
							System.out.print("ㄴ");
							break;
						case '3':
							System.out.print("┕");
							break;
						case '4':
							System.out.print("├");
							break;
						case '5':
							System.out.print("ㅑ");
							break;
					}
				}
				System.out.print(" ");
				System.out.print(bomComponent.getTreeDTO().getTreeNumber() 
						+ " / " + bomComponent.getTreeDTO().getTreeAmount() 
						+ " / " + bomComponent.getTreeDTO().getPartAmountMultiply());
				
			} catch (UnsupportedOperationException e) {
				Kogger.error(getClass(), e);
			}
			
			idx++;
		}
		Kogger.debug(getClass(), "\n\n");
	}
	
	public void setSubCountBom(){
		allParts.subCalculate();
	}
	
	public void printSubCountBom() {
		
		setSubCountBom();

		Iterator<MHierarchyComponent> iterator = new MCompositeIterator(allParts.getIterator());
		
		Kogger.debug(getClass(), "\nTree SubInfo BOM\n----");
		int idx = 1;
		while (iterator.hasNext()) {
			
			MHierarchyComponent bomComponent = iterator.next();
			
			try {
				
				System.out.print("\n" + getReSizeStr(idx, 2, " ") + " " + bomComponent.getTreeDTO().getDepth() + " ");
				for(int k = 0; k < bomComponent.getTreeDTO().getDepth(); k++){
					System.out.print(" ");
				}
				
				System.out.print(bomComponent.getTreeDTO().getTreeNumber() + " / "
						+ bomComponent.getTreeDTO().getTreeAmount() + " / "
						+ bomComponent.getTreeDTO().getPartAmountMultiply() + " / " 
						+ bomComponent.getTreeDTO().getBomSubCount() + " / "
						+ bomComponent.getTreeDTO().getBomTotalCount() + " / "
						+ bomComponent.getTreeDTO().getBomTotalMultiplyCount());
				
			} catch (UnsupportedOperationException e) {
				Kogger.error(getClass(), e);
			}
			
			idx++;
		}
		
		Kogger.debug(getClass(), "\n\n");
	}
	
	
}
