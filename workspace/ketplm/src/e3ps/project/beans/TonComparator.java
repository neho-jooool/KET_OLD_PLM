package e3ps.project.beans;

import e3ps.common.code.NumberCode;

public class TonComparator implements java.util.Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		NumberCode nc1 = null;
		NumberCode nc2 = null;
		if(arg0 instanceof Object[]){
			nc1 = (NumberCode)((Object[])arg0)[0];
			nc2 = (NumberCode)((Object[])arg1)[0];
		}else{
			nc1 = (NumberCode)arg0;
			nc2 = (NumberCode)arg1;
		}
		
		
		
		int value1 = 0;
		int value2 = 0;
		
		try{
			value1 = Integer.parseInt(nc1.getName());
		}catch(Exception ex){
			
		}
		try{
			value2 = Integer.parseInt(nc2.getName());
		}catch(Exception ex){
			
		}
		
		
		return value1 - value2;
	}

}
