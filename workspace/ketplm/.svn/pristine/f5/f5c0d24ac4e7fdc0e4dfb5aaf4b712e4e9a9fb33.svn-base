package e3ps.project.trySchdule.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSTask;

public class TrySchduleAuth {
	
	public static final String compl = "Try담당";
	public static final String tryPlan = "Try제작관리";
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	TrySchduleData data;
	String userId;
	public TrySchduleAuth(TrySchduleData data)throws Exception{
	    userId = SessionHelper.manager.getPrincipal().getName();
		this.data = data;
	}
	
	
	public static boolean isCreateAuth()throws Exception{
		
		SessionHelper.manager.getPrincipal();
		
		return true;
	}
	
	public static boolean isTryCommit(String viewDate)throws Exception{
		
		Calendar today = Calendar.getInstance();
		String todayStr = format.format(today.getTime());
		
		
		boolean todayBefore = todayStr.compareTo(viewDate) > 0;
		if(todayBefore){
			
			return false;
		}
		return CommonUtil.isMember(compl) || CommonUtil.isAdmin() || CommonUtil.isMember(tryPlan);
	
	}
	
	public boolean isTryCompleter()throws Exception{
		Calendar today = Calendar.getInstance();
		String todayStr = format.format(today.getTime());
		boolean todayOrBefore = todayStr.compareTo(data.dayString) >= 0;
		return todayOrBefore && (CommonUtil.isMember(compl) || CommonUtil.isAdmin());
	}
	
	public boolean isTryPlaner()throws Exception{
		if(data.isCompleted){
			return false;
		}
		
		String creator = "";
		if(data.creator != null){
			creator = data.creator.getName();
		}
		
		if(!data.isTryPlan && creator.equals(userId)){
			return true;
		}
		
		if(CommonUtil.isMember(tryPlan) || CommonUtil.isAdmin()){
			return true;
		}
		
		return false;
	}
	
	public boolean isTryModify()throws Exception{
		//Kogger.debug(getClass(), "data = " + data);
		//Kogger.debug(getClass(), "creator = " + data.creator);
		String creator = "";
		if(data.creator != null){
			creator = data.creator.getName();
		}
			
		boolean returnb = creator.equals(userId) 
						|| CommonUtil.isMember(compl) 
						|| CommonUtil.isMember(tryPlan)
						|| CommonUtil.isAdmin();
		return returnb;
	}
	
	public boolean isTryDelete()throws Exception{
		if(data.schduleData instanceof E3PSTask){
			return false;
		}
		if(data.isCompleted){
			return false;
		}
		
		String creator = "";
		if(data.creator != null){
			creator = data.creator.getName();
		}
		
		if(!data.isTryPlan && creator.equals(userId)){
			return true;
		}
		
		if(CommonUtil.isMember(tryPlan) || CommonUtil.isAdmin()){
			return true;
		}
		
		return false;
	}
	
	public void test(){
		
	}

}
