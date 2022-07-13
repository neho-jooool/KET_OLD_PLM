package e3ps.project.beans;

import java.util.Comparator;

import wt.lifecycle.State;

public class StateComparator implements Comparator{
	
	boolean isAscnt;
	
	public StateComparator(){
	}
	public StateComparator(boolean isAscnt){
		this.isAscnt = isAscnt;
	}
	
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		State state1 = (State)arg0;
		State state2 = (State)arg1;
		
		int result = state1.getOrder() - state2.getOrder();
		if(isAscnt){
			result *= -1;
		}
		return result;
	}

}
