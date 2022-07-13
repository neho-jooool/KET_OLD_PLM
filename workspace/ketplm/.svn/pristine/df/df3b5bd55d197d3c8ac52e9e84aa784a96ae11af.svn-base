package e3ps.project.beans;

import java.util.Locale;

import wt.project.Role;

public class RoleComparator implements java.util.Comparator {
	
	boolean isAsc;
	boolean isOrder;
	public RoleComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	
	public RoleComparator(){
		this.isAsc = isAsc;
	}
	
	public RoleComparator(boolean isAsc, boolean isOrder){
		this.isAsc = isAsc;
		this.isOrder = isOrder;
	}
	
	public int compare(Object obj, Object obj1){
		
		Role role1 = (Role)obj;
		Role role2 = (Role)obj1;
		
		String roleName_ko1 = role1.getDisplay(Locale.KOREA);
		String roleName_ko2 = role2.getDisplay(Locale.KOREA);
		int result = 0;
		if(isOrder){
			result = role1.getOrder() - role2.getOrder();
		}else{
			result = roleName_ko1.compareTo(roleName_ko2);
		}
		if(isAsc){
			
			result *=  -1 ;
		}
		
		return result;
		
		
	}
}
