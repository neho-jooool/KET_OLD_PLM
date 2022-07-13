package e3ps.project.trySchdule.beans;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.trySchdule.TrySchdule;
import ext.ket.shared.log.Kogger;


public class TryPlanData {
	
//	public Vector mold_complatedOrtrySet = new Vector();
//	public Vector mold_delayOrtryTodo = new Vector();
//	
//	public Vector press_complatedOrtrySet = new Vector();
//	public Vector press_delayOrtryTodo = new Vector();
	
	public Vector tryPlanDatas = new Vector();
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static final int DELAY = -1;
	public static final int COMPLATED = 1;
	public static final int TRYPLAN = 2;
	public static final int TRYNONPLAN = 3;
	
	
	public boolean isTodayBefore;
	
	public TryPlanData(boolean isTodayBefore){
		
		this.isTodayBefore = isTodayBefore;
	
	}
	
	public void add(E3PSTask task){
		
		tryPlanDatas.add(task);
	}
	
	public void add(TrySchdule trySchdule){
		
		tryPlanDatas.add(trySchdule);
	}
	
//	public Vector getMTotal(){
//		
//		Vector rv = new Vector(mold_complatedOrtrySet);
//		rv.addAll(mold_delayOrtryTodo);
//		return rv;
//		//return mold_complatedOrtrySet.size() + mold_delayOrtryTodo.size();
//		
//	}
//	
//	public Vector getPTotal(){
//		
//		Vector rv = new Vector(press_complatedOrtrySet);
//		rv.addAll(press_delayOrtryTodo);
//		return rv;
//		
//		//return press_complatedOrtrySet.size() + press_delayOrtryTodo.size();
//	}
//	
//	public Vector getMold_ComplatedOrtrySet(){
//		
//		
//		return mold_complatedOrtrySet;
//	}
//	
//	public Vector getPress_ComplatedOrtrySet(){
//		
//		return press_complatedOrtrySet;
//	}
//	
//	public Vector getMold_DelayOrtryTodo(){
//		
//		return mold_delayOrtryTodo;
//	}
//	
//	public Vector getPress_DelayOrtryTodo(){
//		
//		return press_delayOrtryTodo;
//	}
//	
	
	public Hashtable getStateData(){
		
		Vector vector = getTotal();
		
		Hashtable hash = new Hashtable();
		
		for(int i = 0; i < vector.size(); i++){
			
			Object o = vector.get(i);
			int state = -1;
			if(o instanceof E3PSTask){
				
				state = getTryState((E3PSTask)o);
			
			}else{
				state = getTryState((TrySchdule)o);
			}
			
			Integer key = new Integer(state);
			
			Vector datas = (Vector)hash.get(key);
			
			if(datas == null){
				datas = new Vector();
			}
			datas.add(o);
			hash.put(key, datas);
		}
		
		return hash;
	}
	
	
	public static Vector getType(Hashtable hd, int state, String type){
		Vector vector = new Vector();
		
		vector = (Vector)hd.get(new Integer(state));
		
		
		if(vector == null){
			return new Vector();
		}
		
		return getType(vector, type);
		
		
	}
	
	public static Vector getType(Vector datas, String type){
		
		Vector vector = new Vector();
		
		for(int i = 0; i < datas.size(); i++){
			Object o = (Object)datas.get(i);
			MoldProject mproject = null;
			if(o instanceof TrySchdule) {
				TrySchdule trySchdule = (TrySchdule)o;
				E3PSProjectMaster master = trySchdule.getMoldMaster();
				
				try {
					mproject = TrySchduleHelper.getFromMaster(master);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Kogger.error(TryPlanData.class, e);
				}
			
			}else{
				
					
				E3PSTask task = (E3PSTask)o;
				mproject = (MoldProject)task.getProject();
					
				
			}
			
			MoldItemInfo mi = null;
			if(mproject != null){
				mi = mproject.getMoldInfo();
			}
			boolean isMold = false;
			if(mi != null){
				isMold = mi.getItemType().equalsIgnoreCase("Mold");
			}
			
			if("M".equals(type) && isMold){
				vector.add(o);
			}else if("P".equals(type) && !isMold){
				vector.add(o);
			}else if(!("M".equals(type) || "P".equals(type))){
				vector.add(o);
			}
		}
		
		return vector;
	}
	
	public Vector getTotal(){
		
		return tryPlanDatas;
	}
	
	public Vector getData(int state, String type){
		
		if(state == 0){
			return getType(getTotal(), type);
		}
		
		return getType(getStateData(), state, type);
	}
	
//	public Vector getData(String state, String type){
//		
//		if("M".equals(type)){
//			
//			if("D".equals(state)){
//				return getMold_DelayOrtryTodo();
//			}else if("C".equals(state)){
//				return getMold_ComplatedOrtrySet();
//			}else{
//				return getMTotal();
//			}
//			
//		}else if("P".equals(type)){
//			
//			if("D".equals(state)){
//				return getPress_DelayOrtryTodo();
//			}else if("C".equals(state)){
//				return getPress_ComplatedOrtrySet();
//			}else{
//				return getPTotal();
//			}
//			
//		}else{
//			
//			return getTotal();
//		}
//	}
	
	
	public static int getTryState(E3PSTask task){
		
		Calendar today = Calendar.getInstance();
		String todayStr = format.format(today.getTime());
		
		
		ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
		
		String keyDate = "";
		
		if(schedule.getExecStartDate() != null){
			
			keyDate = format.format(schedule.getExecStartDate());
		}else{
			keyDate = format.format(schedule.getPlanStartDate());
		}
	 	
		boolean todayBefore = todayStr.compareTo(keyDate) > 0;
		boolean isToDay = todayStr.compareTo(keyDate) == 0;
		
		if(todayBefore){
			if(schedule.getExecEndDate() != null){
				return COMPLATED;
			}else{
				return DELAY;
			}
		}else if(isToDay){
			if(schedule.getExecEndDate() != null){
				return COMPLATED;
			}
			if(task.isTryPlan()){
				return TRYPLAN;
			}else{
				return TRYNONPLAN;
			}
			
		
		}else{
			if(task.isTryPlan()){
				return TRYPLAN;
			}else{
				return TRYNONPLAN;
			}
		}
		
	}
	
	public static int getTryState(TrySchdule trySchdule){
		
		Calendar today = Calendar.getInstance();
		String todayStr = format.format(today.getTime());
		
		String keyDate = format.format(trySchdule.getPlanDate());
		
		boolean todayBefore = todayStr.compareTo(keyDate) > 0;
		boolean isToDay = todayStr.compareTo(keyDate) == 0;
		
		if(todayBefore){
			if(trySchdule.isCompleted()){
				return COMPLATED;
			}else{
				return DELAY;
			}
		}
		else if(isToDay){
			if(trySchdule.isCompleted()){
				return COMPLATED;
			}
			if(trySchdule.isTryPlan()){
				return TRYPLAN;
			}else{
				return TRYNONPLAN;
			}
		}else{
			if(trySchdule.isTryPlan()){
				return TRYPLAN;
			}else{
				return TRYNONPLAN;
			}
		}
		
	}
	
}
