package e3ps.project.beans;

public class DebugingDataComparator implements java.util.Comparator{
	
	private boolean isAsc;
	public DebugingDataComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public DebugingDataComparator(){
	}
	
	public int compare(Object o1, Object o2) {
			
		DebugingData data1 = (DebugingData)o1;
		DebugingData data2 = (DebugingData)o2;
		int result = data1.nCha - data2.nCha;
		
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
		
	}
}
