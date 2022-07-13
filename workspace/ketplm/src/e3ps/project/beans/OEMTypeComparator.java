package e3ps.project.beans;

import java.util.Comparator;
import java.util.Hashtable;

import e3ps.common.util.CommonUtil;
import e3ps.project.TemplateTask;

public class OEMTypeComparator implements Comparator {

	private Hashtable hash;
	
	public OEMTypeComparator(Hashtable hash){
		this.hash = hash;
	}
	
	public int compare(Object arg0, Object arg1) {
		
		String task1Oid = CommonUtil.getOIDString((TemplateTask)arg0);
		String task2Oid = CommonUtil.getOIDString((TemplateTask)arg1);
		
		Integer integer1 = (Integer)hash.get(task1Oid);
		Integer integer2 = (Integer)hash.get(task2Oid);
		
		return integer1.intValue() - integer2.intValue();
		
	}

}
