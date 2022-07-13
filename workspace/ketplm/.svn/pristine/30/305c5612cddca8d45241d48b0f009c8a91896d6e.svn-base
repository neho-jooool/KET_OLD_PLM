package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;

import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.TemplateProject;
import e3ps.project.trySchdule.beans.TrySchduleHelper;
import ext.ket.shared.log.Kogger;

public class CreateMold implements wt.method.RemoteAccess, java.io.Serializable {
	
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	public final static String PRESSPM = "Team_PRODUCT05";
	public final static String MOLDDPM = "Team_PRODUCT04";
	
	
	
	
    public static MoldProject createMoldProject(String moldItemOid, String planStartDate, String templateOid) throws Exception{
    	
    	if(!SERVER) {
			Class argTypes[] = new Class[]{String.class, String.class, String.class};
			Object args[] = new Object[]{moldItemOid, planStartDate ,templateOid};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke("createMoldProject","e3ps.project.beans.CreateMold",null,argTypes,args);
			}
			catch(RemoteException e) {
				Kogger.error(CreateMold.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(CreateMold.class, e);
				throw new WTException(e);
			}
			return (MoldProject)obj;
		}
    	
    	MoldProject mold = null;
    	Transaction transaction = new Transaction();
    	
	    try {
	    	transaction.start();
    	
	    	MoldItemInfo info = (MoldItemInfo)CommonUtil.getObject(moldItemOid);
	    	ProductProject productProject = info.getProject();
	    	
			mold = MoldProject.newMoldProject();
			 
		   	String lifecycle = "KET_MOLD_PMS_LC";
		   	   
		   	if(lifecycle == null || lifecycle.equals("")) {
		   	   lifecycle = "LC_Project";
		   	}
		   	
		   	FolderHelper.assignLocation((FolderEntry)mold,  FolderHelper.service.getFolder("/Default/프로젝트",WCUtil.getWTContainerRef()));
			LifeCycleHelper.setLifeCycle(mold, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,WCUtil.getWTContainerRef()));
		   	
			long infoId = info.getPersistInfo().getObjectIdentifier().getId();
			
		
			mold.setPjtNo(info.getDieNo());
			mold.setPjtName(info.getDieNo());
			mold.setPjtType(3);
			//mold.setMoldRank("S");
			mold.setMoldInfo(info);
		    	
			Calendar tempCal = Calendar.getInstance();
			ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
	
			// 1.1 Duration Setting
			if (StringUtil.checkString(planStartDate.trim())){
				tempCal.setTime(DateUtil.parseDateStr(planStartDate));
				schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			}
			// 1.2 ScheduleHistory (0: ????)
			schedule.setScheduleHistory(0);
	
			schedule.setExecWork(0);
	
			schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
	
			mold.setPjtSchedule(ObjectReference.newObjectReference(schedule));
			
			
			boolean isMold = info.getItemType().equalsIgnoreCase("Mold");
			
			String pmRole = PRESSPM;
			
			if(isMold){
				pmRole = MOLDDPM;
			}
			
			Hashtable userH = TrySchduleHelper.getProjectRoleMember(productProject);
			
			WTUser pmUser = (WTUser)userH.get(pmRole);
			
			ProjectUserHelper.manager.setPM(mold, pmUser, 0);
			mold = (MoldProject) PersistenceHelper.manager.save(mold);
	
			TemplateProject templateProject = (TemplateProject) CommonUtil.getObject(templateOid);
			ProjectHelper.manager.copyProjectInfo(mold, templateProject);
			Kogger.debug(CreateMold.class, "copyProjectInfo");
	
			TaskHelper.manager.copyProjectFromTemplate(mold, templateProject);
			
			
			transaction.commit();
	   	    transaction = null;
   	    
	   } catch (Exception e) {
		   Kogger.error(CreateMold.class, e);
		   throw new WTException(e);
	   }finally{
		   if(transaction != null){
			   transaction.rollback();
		   }
	   }
	   return mold;
	}
    
    public static void main(String args[])throws Exception{
    	//createM();
    }
}
