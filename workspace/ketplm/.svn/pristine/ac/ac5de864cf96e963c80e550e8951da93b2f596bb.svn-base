package e3ps.project.beans;

import java.util.Comparator;
import java.util.Hashtable;

import e3ps.common.util.CommonUtil;
import e3ps.project.ProjectOutput;

public class ProjectOutPutTypeComparator implements Comparator {

	private Hashtable hash;
	
	public ProjectOutPutTypeComparator(Hashtable hash){
		this.hash = hash;
	}
	
	public int compare(Object arg0, Object arg1) {
		
		ProjectOutput o1 = (ProjectOutput)arg0;
		ProjectOutput o2 = (ProjectOutput)arg1;
	
		String task1Oid = CommonUtil.getOIDString(o1.getTask());
		String task2Oid = CommonUtil.getOIDString(o2.getTask());
		
		Integer integer1 = (Integer)hash.get(task1Oid);
		Integer integer2 = (Integer)hash.get(task2Oid);
		
		return integer1.intValue() - integer2.intValue();
		
	}

}
