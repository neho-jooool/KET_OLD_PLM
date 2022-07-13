package e3ps.project.trySchdule.beans;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.common.util.DateUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.trySchdule.TrySchdule;

public class TryResultCount {
	
	public static int TOTAL = 0;
	public static int IN_T0 = 1; //사내t0
	public static int OUT_T0 = 2; //사외t0
	public static int IN_TN = 3; //사내 tN차
	public static int OUT_TN = 4; //사외 tN;
	public static int MR = 5; //양산개조;
	public static int ETC = 6; //기타;
	
	
	
	public int in_t0; //사내 t0
	public int out_t0; //사외 t0
	
	public int in_tN; //사내 tN차
	public int out_tN; //사외 tN차
	
	public int mR; //양산개조
	public int etc;//기타
	
	private Hashtable hash = new Hashtable();
	
	
	private Timestamp startTime;
	private Timestamp endTime;
	
	private boolean isPeriod;
	
	public TryResultCount(){
		
	}
	
	public TryResultCount(Timestamp startTime, Timestamp endTime){
		
		this.startTime = startTime;
		this.endTime = endTime;
		isPeriod = true;
		
	}
	
	public void add(Object obj){
		Integer key = null;
		if(obj instanceof E3PSTask){
			
			
			
			E3PSTask task = (E3PSTask)obj;
			
			if(isPeriod){
				
				Timestamp plan = TrySchduleData.getTryPlanDate(task);
				
				if(!(plan.compareTo(startTime) >= 0 && plan.compareTo(endTime) < 0) ){
					return;
				}
				
			}
			
			ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
			
			if(schedule.getExecEndDate() == null){
				return;
				
			}
			
			int ncha = task.getNcha();
			MoldProject project = (MoldProject)task.getProject();
			MoldItemInfo minfo = project.getMoldInfo();
			
			if(minfo != null){
				String making = minfo.getMaking();
				if("사내".equals(making)){
					if(ncha > 0){
						key = new Integer(IN_TN);
						++in_tN;
					}else{
						String tryType = TrySchduleData.getType(task.getTaskName());
						if("양산개조".equals(tryType)){
							key = new Integer(MR);
							++mR;
						}else if ("T0".equals(tryType)){
							key = new Integer(IN_T0);
							++in_t0;
						}
						else{
							key = new Integer(ETC);
							++etc;
						}
						
						
					}
					
				}else{
					if(ncha > 0){
						key = new Integer(OUT_TN);
						++out_tN;
					}else{
						String tryType = TrySchduleData.getType(task.getTaskName());
						if("양산개조".equals(tryType)){
							key = new Integer(MR);
							++mR;
						}else if ("T0".equals(tryType)){
							key = new Integer(OUT_T0);
							++out_t0;
						}
						else{
							key = new Integer(ETC);
							++etc;
						}
						
						
						
					}
				}
			}else{
				if(ncha > 0){
					key = new Integer(IN_TN);
					++in_tN;
				}else{
					
					String tryType = TrySchduleData.getType(task.getTaskName());
					if("양산개조".equals(tryType)){
						key = new Integer(MR);
						++mR;
					}else if ("T0".equals(tryType)){
						key = new Integer(IN_T0);
						++in_t0;
					}
					else{
						key = new Integer(ETC);
						++etc;
					}
					
					
				} 
			}
			
		}else if(obj instanceof TrySchdule){
			
			TrySchdule trySchdule = (TrySchdule)obj;
			
			if(isPeriod){
			
				Timestamp plan = trySchdule.getPlanDate();
				
			    
				if( !(plan.compareTo(startTime) >= 0 && plan.compareTo(endTime) < 0) ){
					return;
				}
			}
			
			if(!trySchdule.isCompleted()){
				return;
			}
			
			String tryType = trySchdule.getTryType();

			if("양산개조".equals(tryType)){
				key = new Integer(MR);
				++mR;
				
			}else{
				key = new Integer(ETC);
				++etc;
			
			}
			
		}
		
		
		Vector data = (Vector)hash.get(key);
		if(data == null){
			data = new Vector();
		}
		data.add(obj);
		hash.put(key, data);
		
	}
	
	public Vector getTotalDatas(){
		Vector data = new Vector();
		Enumeration e = hash.keys();
		while(e.hasMoreElements()){
			
			Object key = e.nextElement();
			Vector vector = (Vector)hash.get(key);
			data.addAll(vector);
		}
		
		return data;
	}
	
	public Vector getData(int type){
		if(type == TOTAL){
			return getTotalDatas();
		}
		
		Vector vector = (Vector)hash.get(new Integer(type));
		if(vector == null){
			vector = new Vector();
		}
		return vector;
	}
	
	public int getTotal(){
		
		return in_t0 + out_t0 + in_tN + out_tN + mR + etc;
	
	}
}
