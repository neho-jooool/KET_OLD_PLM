package e3ps.project.moldPart.beans;

import java.sql.Timestamp;
import java.util.Comparator;

import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.trySchdule.beans.TrySchduleData;

public class MoldPartManagerComparator implements Comparator {
	
	public String sortType;
	public boolean dsec;
	
	
	public MoldPartManagerComparator(){	
	}
	
	public MoldPartManagerComparator(boolean dsec){	
		this.dsec = dsec;
	}
	
	public MoldPartManagerComparator(String sortType, String tf){
		this.sortType = sortType;
		
		if(tf != null && tf.length() > 0){
			this.dsec = Boolean.valueOf(tf).booleanValue();
		}
	}
	
	public MoldPartManagerComparator(String sortType, boolean dsec){
		this.sortType = sortType;
		this.dsec = dsec;
	}
	
	public int compare(Object arg0, Object arg1) {
		MoldPartManager data0 = null;
		MoldPartManager data1 = null;
		int result = -1;
		
		if(arg0 instanceof Object[]) {
			Object o[] = (Object[])arg0;
			data0 = (MoldPartManager)o[0];
		}else{
			data0 = (MoldPartManager)arg0;
		}
		
		if(arg1 instanceof Object[]) {
			Object o[] = (Object[])arg1;
			data1 = (MoldPartManager)o[0];
		}else{
			data1 = (MoldPartManager)arg1;
		}
		
		Timestamp stamp0 = data0.getPersistInfo().getCreateStamp();
		Timestamp stamp1 = data1.getPersistInfo().getCreateStamp();
	    result = stamp0.compareTo(stamp1);
		if(dsec){
			result = result * -1;
		}
		
		return result;
	}
	
	
	

}
