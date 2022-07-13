package e3ps.project.trySchdule.beans;

import java.util.Comparator;

import e3ps.project.E3PSTask;
import e3ps.project.trySchdule.TrySchdule;

public class TryPlanComparator implements Comparator {
	
	public String sortType;
	public boolean dsec;
	
	public TryPlanComparator(){	
	}
	
	public TryPlanComparator(String sortType, String tf){
		this.sortType = sortType;
		
		if(tf != null && tf.length() > 0){
			this.dsec = Boolean.valueOf(tf).booleanValue();
		}
	}
	
	public TryPlanComparator(String sortType, boolean dsec){
		this.sortType = sortType;
		this.dsec = dsec;
	}
	
	public int compare(Object arg0, Object arg1) {
		
		Object obj0 = null;
		Object obj1 = null;
		TrySchduleData data0 = null;
		TrySchduleData data1 = null;
		
		if(arg0 instanceof Object[]) {
			Object o[] = (Object[])arg0;
			obj0 = o[0];
		}else{
			obj0 = arg0;
		}
		
		if(arg1 instanceof Object[]) {
			Object o[] = (Object[])arg1;
			obj1 = o[0];
		}else{
			obj1 = arg1;
		}
		
		if(obj0 instanceof E3PSTask){
			
			E3PSTask task = (E3PSTask)obj0;
    		data0 = new TrySchduleData(task);
    	
		}else if(obj0 instanceof TrySchdule){	
    		
			TrySchdule trySchdule = (TrySchdule)obj0;
    		data0 = new TrySchduleData(trySchdule);
    	
		}
		
		if(obj1 instanceof E3PSTask){
    		
			E3PSTask task = (E3PSTask)obj1;
    		data1 = new TrySchduleData(task);
    	
		}else if(obj1 instanceof TrySchdule){
    		
			TrySchdule trySchdule = (TrySchdule)obj1;
    		data1 = new TrySchduleData(trySchdule);
    	}
		
		int result = 0;
		
		if("isTryPlan".equals(sortType)){
			
			Boolean b0 = new Boolean(data0.isTryPlan);
			Boolean b1 = new Boolean(data1.isTryPlan);
			result = b0.compareTo(b1);
			
		}else if("dieNo".equals(sortType)){
			String dieNo0 = "";
			String dieNo1 = "";
			if(data0.dieNo != null){
				dieNo0 = data0.dieNo;
			}
			if(data1.dieNo != null){
				dieNo1 = data1.dieNo;
			}
			result = dieNo0.compareTo(dieNo1);
			
		}else if("partNumber".equals(sortType)){
			
			String partNumber0 = "";
			String partNumber1 = "";
			if(data0.partNumber != null){
				partNumber0 = data0.partNumber;
			}
			if(data1.partNumber != null){
				partNumber1 = data1.partNumber;
			}
			
			result = partNumber0.compareTo(partNumber1);
			
		}else if("tryType".equals(sortType)){
			
			String tryType0 = "";
			String tryType1 = "";
			if(data0.tryType != null){
				tryType0 = data0.tryType;
			}
			if(data1.tryType != null){
				tryType1 = data1.tryType;
			}
			result = tryType0.compareTo(tryType1);
			
		}else if("moldPlanerName".equals(sortType)){
			
			String moldPlanerName0 = "";
			String moldPlanerName1 = "";
			if(data0.moldPlanerName != null){
				moldPlanerName0 = data0.moldPlanerName;
			}
			if(data1.moldPlanerName != null){
				moldPlanerName1 = data1.moldPlanerName;
			}
			result = moldPlanerName0.compareTo(moldPlanerName1);
		
		}else if("moldMakerName".equals(sortType)){
		
			String moldMakerName0 = "";
			String moldMakerName1 = "";
			if(data0.moldMakerName != null){
				moldMakerName0 = data0.moldMakerName;
			}
			if(data1.moldMakerName != null){
				moldMakerName1 = data1.moldMakerName;
			}
			result = moldMakerName0.compareTo(moldMakerName1);
		
		}else if("projectPlanerName".equals(sortType)){
			
			String projectPlanerName0 = "";
			String projectPlanerName1 = "";
			
			if(data0.projectPlanerName != null){
				projectPlanerName0 = data0.projectPlanerName;
			}			
			if(data1.projectPlanerName != null){
				projectPlanerName1 = data1.projectPlanerName;
			}
			
			result = projectPlanerName0.compareTo(projectPlanerName1);
		}
		else if("partName".equals(sortType)){
			
			String partName0 = "";
			String partName1 = "";
			
			if(data0.partName != null){
				partName0 = data0.partName;
			}
			
			if(data1.partName != null){
				partName1 = data1.partName;
			}
			
			result = partName0.compareTo(partName1);
		
		}
		else if("materialName".equals(sortType)){
			
			String materialName0 = "";
			String materialName1 = "";
			
			if(data0.materialName != null){
				materialName0 = data0.materialName;
			}
			
			if(data1.materialName != null){
				materialName1 = data1.materialName;
			}	
			result = materialName0.compareTo(materialName1);
		}
		else if("outsourcingName".equals(sortType)){
			
			String outsourcingName0 = "";
			String outsourcingName1 = "";
			
			if(data0.outsourcingName != null){
				outsourcingName0 = data0.outsourcingName;
			}
			
			if(data1.outsourcingName != null){
				outsourcingName1 = data1.outsourcingName;
			}	
			result = outsourcingName0.compareTo(outsourcingName1);
		}
		
		else if("cavSu".equals(sortType)){
			
			String cavSu0 = "";
			String cavSu1 = "";
			
			if(data0.cavSu != null){
				cavSu0 = data0.cavSu;
			}
			
			if(data1.cavSu != null){
				cavSu1 = data1.cavSu;
			}	
			
			result = cavSu0.compareTo(cavSu1);
		
		}
		else if("ton".equals(sortType)){
			
			String ton0 = "";
			String ton1 = "";
			
			if(data0.ton != null){
				ton0 = data0.ton;
			}
			
			if(data1.ton != null){
				ton1 = data1.ton;
			}
			
			result = ton0.compareTo(ton1);
		}
		
		else if("quantity".equals(sortType)){
			//data0.quantity
			String quantity0 = "";
			String quantity1 = "";
			
			if(data0.quantity != null){
				quantity0 = data0.quantity;
			}
			
			if(data1.ton != null){
				quantity1 = data1.quantity;
			}
			
			result = quantity0.compareTo(quantity1);
		}else{
			String dieNo0 = "";
			String dieNo1 = "";
			if(data0.dieNo != null){
				dieNo0 = data0.dieNo;
			}
			if(data1.dieNo != null){
				dieNo1 = data1.dieNo;
			}
			result = dieNo0.compareTo(dieNo1);
		}
		
		if(dsec){
			result = result * -1;
		}
		
		return result;
	}
	
}
