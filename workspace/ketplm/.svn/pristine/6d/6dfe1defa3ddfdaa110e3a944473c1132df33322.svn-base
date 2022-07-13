package e3ps.project.beans;


public class ComparatorUtil implements java.util.Comparator {

	/**
	 * @param args
	 */
		
		private boolean isAsc;
		public ComparatorUtil(boolean isAsc){
			this.isAsc = isAsc;
		}
		
		public ComparatorUtil(){
		}
		
		public int compare(Object o1, Object o2) {
				
			ProjectUserData data1 = (ProjectUserData)o1;
		
			ProjectUserData data2 = (ProjectUserData)o2;
		
	
			int result = data1.data.dutycode.compareTo(data2.data.dutycode);
			
			if(result == 0){
				result = data1.name.compareTo(data2.name);
			}
		
			if(isAsc){
				return -1 * result;
			}else{
				return result;
			}
			
		}
		

}
