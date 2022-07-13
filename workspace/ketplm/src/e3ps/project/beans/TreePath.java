package e3ps.project.beans;

import java.util.Stack;

import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.log.Kogger;

public class TreePath  {
	public String getTreePath(OEMProjectType  type) throws Exception{
		Stack stack = new Stack();
		String pop = "";
		settingPath(stack, type);
		Kogger.debug(getClass(), "stack main=" + stack.size());
		
		while(!stack.empty()){
			pop +="/";
			pop += (String)stack.pop();
			
		}
		
		
		Kogger.debug(getClass(), "pop" + pop);
		return pop;
		
	}
	
	public void settingPath(Stack stack, OEMProjectType prjType){
		String pName = prjType.getName();
		
		stack.push(pName);
		if(pName.equals("ROOT")){
			return;
		}else{
			prjType = (OEMProjectType)prjType.getParent();
			settingPath(stack, prjType);
		}
	}
}
