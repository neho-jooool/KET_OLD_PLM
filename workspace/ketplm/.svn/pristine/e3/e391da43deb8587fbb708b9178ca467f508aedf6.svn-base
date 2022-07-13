package e3ps.project.trySchdule.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.CompositeQuerySpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.material.MoldMaterial;
import e3ps.project.trySchdule.TrySchdule;
import ext.ket.shared.log.Kogger;

public class TrySchduleHelper implements RemoteAccess{
	public static final int MOLD = 2;
	public static final int PRESS = 1;
	public static final int ALL = 0;
	
	public static Object saveTrySchdule(Hashtable hash)throws Exception{
		
		if (!wt.method.RemoteMethodServer.ServerFlag) {
			
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = RemoteMethodServer.getDefault().invoke("saveTrySchdule", TrySchduleHelper.class.getName() , null, argTypes, args);
			return obj;
		}
		
		
		String moldProject = (String)hash.get("moldProject");
		String tryType = (String)hash.get("tryType");
		String material = (String)hash.get("material");
		String property = (String)hash.get("property");
		String thickness = (String)hash.get("thickness");
		String width = (String)hash.get("width");
		String ton = (String)hash.get("ton");
		String quantity = (String)hash.get("quantity");
		String requester = (String)hash.get("requester");
		String planDate = (String)hash.get("planDate");
		String des = (String)hash.get("des");
		String tryPlace = (String)hash.get("tryPlace");
		String oid = (String)hash.get("oid");
		
		TrySchdule trySchdule = null;
		
		if(oid != null){
			
			Object obj = CommonUtil.getObject(oid);
			
			if(obj instanceof TrySchdule){
				trySchdule = (TrySchdule)obj;
			}else if(obj instanceof E3PSTask){
				E3PSTask task = (E3PSTask)obj;
				
				task.setTryPlace(tryPlace);
				task.setTon(ton);
				task.setQuantity(quantity);
				task.setTryDes(des);
				
				if(planDate != null && planDate.length() > 0){
					
					Timestamp stamp = DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd");
					ExtendScheduleData exschdule = (ExtendScheduleData)task.getTaskSchedule().getObject();
					exschdule.setExecStartDate(stamp);	
				}
				
				PersistenceHelper.manager.save(task);
				return task;
			}
			
		}
		if(trySchdule == null){
			
			trySchdule = TrySchdule.newTrySchdule();
			trySchdule.setCreator(SessionHelper.manager.getPrincipalReference());
		
		}
		
		MoldProject project = null;
		if(moldProject != null && moldProject.length() > 0){
			project = (MoldProject)CommonUtil.getObject(moldProject);
			trySchdule.setMoldMaster((E3PSProjectMaster)project.getMaster());
			
		}
		
		trySchdule.setTryType(tryType);
		
		if(material != null && material.length() > 0){
			
			MoldMaterial code = (MoldMaterial)CommonUtil.getObject(material);
			trySchdule.setMaterial(code);
			
		}
		
		if(property != null && property.length() > 0){
			
			NumberCode code = (NumberCode)CommonUtil.getObject(property);
			trySchdule.setProperty(code);
			
		}
		/*
		Kogger.debug(getClass(), "proper = " + property);
		Kogger.debug(getClass(), "thickness = " + thickness);
		Kogger.debug(getClass(), "width = " + width);
		*/
		trySchdule.setThickness(thickness);
		
		trySchdule.setWidth(width);
		
		trySchdule.setTon(ton);
		
		trySchdule.setQuantity(quantity);
		
		if(requester != null && requester.length() > 0){
			
			WTUser user = (WTUser)CommonUtil.getObject(requester);
			trySchdule.setRequester(WTPrincipalReference.newWTPrincipalReference(user));
			
		}else{
			
			trySchdule.setRequester(WTPrincipalReference.newWTPrincipalReference());
		}
		
		if(planDate != null && planDate.length() > 0){
			Timestamp stamp = DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd");
			trySchdule.setPlanDate(stamp);
		}
		
		trySchdule.setTryPlace(tryPlace);
		
		trySchdule.setDes(des);
		
		
		trySchdule = (TrySchdule)PersistenceHelper.manager.save(trySchdule);
		//ProjectHelper.projectTrySendMail(trySchdule, project);
		return trySchdule;
		
	}
	
	
	public static Object simpleSaveTrySchdule(Hashtable hash)throws Exception{
		
		if (!wt.method.RemoteMethodServer.ServerFlag) {
			
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = RemoteMethodServer.getDefault().invoke("simpleSaveTrySchdule", TrySchduleHelper.class.getName() , null, argTypes, args);
			return obj;
		}

		String planDate = (String)hash.get("planDate");
		String oid = (String)hash.get("oid");
		String completed = (String)hash.get("completed");
		
		Object obj = CommonUtil.getObject(oid);
		
		if(obj instanceof TrySchdule){
			TrySchdule trySchdule = (TrySchdule)obj;
			if(planDate != null && planDate.length() > 0){
				Timestamp stamp = DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd");
				
				
				
				String currentPlanDate = DateUtil.getDateString(trySchdule.getPlanDate(), "d");
				if(!planDate.equals(currentPlanDate)){
					trySchdule.setTryPlan(false);
				}
				
				trySchdule.setPlanDate(stamp);
				
				
			}
			if(completed != null && completed.length() > 0){
				trySchdule.setCompleted(true);
			}else{
				trySchdule.setCompleted(false);
			}
			obj = PersistenceHelper.manager.save(trySchdule);
			
			if(completed != null && completed.length() > 0){
				ProjectHelper.projectTrySendMail(trySchdule);
			}
		}else if(obj instanceof E3PSTask){
			E3PSTask task = (E3PSTask)obj;
			
			if(planDate != null && planDate.length() > 0){
				
				Timestamp stamp = DateUtil.getTimestampFormat(planDate, "yyyy-MM-dd");
				ExtendScheduleData exschdule = (ExtendScheduleData)task.getTaskSchedule().getObject();
				
				Timestamp currentDate = TrySchduleData.getTryPlanDate(task);
				
				String currentPlanDate = DateUtil.getDateString(currentDate, "d");
				if(!planDate.equals(currentPlanDate)){
					task.setTryPlan(false);
					PersistenceHelper.manager.save(task);
				}
				
				exschdule.setExecStartDate(stamp);
				PersistenceHelper.manager.save(exschdule);
			}
			
			obj = task;
		}
		
		return obj;
		
	}
	
	public static void setTryPlan(Vector datas, Hashtable tryPlans)throws Exception{
		
		if (!wt.method.RemoteMethodServer.ServerFlag) {
			
			Class argTypes[] = new Class[]{Vector.class, Hashtable.class};
			Object args[] = new Object[]{datas, tryPlans};
			Object obj = RemoteMethodServer.getDefault().invoke("setTryPlan", TrySchduleHelper.class.getName() , null, argTypes, args);
			return;	
		}
		Transaction transaction = new Transaction();
		   
	    try {
	    	transaction.start();
		   
			for(int i = 0; i < datas.size(); i++){
				
				Persistable obj = (Persistable)datas.get(i);
				String oid = CommonUtil.getOIDString(obj);
				
				boolean isTryPlan = tryPlans.containsKey(oid);
				
				//Kogger.debug(getClass(), "isTryPlan == " + isTryPlan);
				
				if(obj instanceof E3PSTask) {
					
					E3PSTask task = (E3PSTask)obj;
					
					if(isTryPlan != task.isTryPlan()){
						task.setTryPlan(isTryPlan);
						if(isTryPlan){
							ExtendScheduleData sdata = (ExtendScheduleData)task.getTaskSchedule().getObject();
							
							if(sdata.getExecStartDate() == null){
								sdata.setExecStartDate(sdata.getPlanStartDate());
								PersistenceHelper.manager.save(sdata);
							}
						}
						PersistenceHelper.manager.save(task);
					}
				
					
				}else if(obj instanceof TrySchdule){
					
					TrySchdule trySchdule = (TrySchdule)obj;
					//Kogger.debug(getClass(), trySchdule.isTryPlan());
					if(isTryPlan != trySchdule.isTryPlan()){
						
						trySchdule.setTryPlan(isTryPlan);
						PersistenceHelper.manager.save(trySchdule);
					}
				}
				
				
			}
		
			transaction.commit();
	   	    transaction = null;
	   	    
	   } catch (Exception e) {
		   Kogger.error(TrySchduleHelper.class, e);
		   throw new WTException(e);
	   }finally{
		   if(transaction != null){
			   transaction.rollback();
		   }
	   }	
	}
	
	public static Hashtable getProjectRoleMember(E3PSProject project){
		
		QueryResult rs = ProjectUserHelper.manager.getQueryForTeamUsers(project, null, ProjectUserHelper.ROLEMEMBER);
		
		Hashtable hash = new Hashtable();
		
		while(rs.hasMoreElements()){
			
			ProjectMemberLink memberLink = (ProjectMemberLink)rs.nextElement();
			String role = memberLink.getPjtRole();
			WTUser user = memberLink.getMember();
			hash.put(role, user);
		}
		
		return hash;
	}
	
	public static QueryResult getTotalDayTry(String day, String key, String value , int type)throws Exception{
		
		CompositeQuerySpec spec = new CompositeQuerySpec();
		spec.addComponent(getDayTask(day, key, value, type));
		spec.addComponent(getDayTrySchedule(day, key, value, type));
		QueryResult rs = PersistenceHelper.manager.find(spec);
		
		return rs;
	}
	
	public static QueryResult getTotalTry(int year, int month, String key, String value)throws Exception{
		
		CompositeQuerySpec spec = new CompositeQuerySpec();
		
		spec.addComponent(getMonthTask(year, month, key, value));
		spec.addComponent(getMonth(year, month, key, value));
		
		QueryResult rs = PersistenceHelper.manager.find(spec);
		
		return rs;
	}
	
	public static QueryResult getWeekTry(Calendar targetDay, int week, String key, String value, int type)throws Exception{
		Calendar startC = (Calendar)targetDay.clone();
		
		Timestamp startDate = new Timestamp(startC.getTimeInMillis());
		
		startC.add(Calendar.DATE, 7 * week + 1); 
		
		Timestamp endDate = new Timestamp(startC.getTimeInMillis());
		
	    CompositeQuerySpec spec = new CompositeQuerySpec(); 
	    spec.addComponent(getTaskQuerySpec(startDate, endDate, key, value, type));
		spec.addComponent(getTrySchduleQuerySpec(startDate, endDate, key, value, type));
		
		QueryResult rs = PersistenceHelper.manager.find(spec);
		
		return rs;
		
	}
	
	public static QueryResult getTaskOrTrySchduleQuerySpec(Timestamp startDate, Timestamp endDate, String key, String value)throws Exception{
		
		CompositeQuerySpec spec = new CompositeQuerySpec(); 
	    spec.addComponent(getTaskQuerySpec(startDate, endDate, key, value, TrySchduleHelper.ALL));
		spec.addComponent(getTrySchduleQuerySpec(startDate, endDate, key, value, TrySchduleHelper.ALL));		
		QueryResult rs = PersistenceHelper.manager.find(spec);
		return rs;
	}
	
	public static QueryResult getTaskOrTrySchduleQuerySpec(String startDate, String endDate, String key, String value)throws Exception{
		
		Timestamp startStamp = DateUtil.getTimestampFormat(startDate, "yyyy-MM-dd");
		Timestamp endStamp = DateUtil.getTimestampFormat(endDate, "yyyy-MM-dd");
		return getTaskOrTrySchduleQuerySpec(startStamp, endStamp, key, value);
		
	}

    
	public static QuerySpec getDayTrySchedule(String day, String key, String value, int type)throws Exception{
		
		QuerySpec spec = new QuerySpec();
		int idx1 = spec.addClassList(TrySchdule.class, true);
		int idx2 = spec.addClassList(MoldProject.class, false);
		int idx3 = spec.addClassList(MoldItemInfo.class, false);
		
		
		ClassAttribute ca0 = new ClassAttribute(TrySchdule.class, TrySchdule.MOLD_MASTER_REFERENCE + ".key.id");
	    ClassAttribute ca1 = new ClassAttribute(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id");
	    
	    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx2}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx2});
	    
	    
	    if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, true),new int[] { idx2});

		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] {idx2});
		
		spec.appendAnd();
	    
	    ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
	    ca1 = new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id");
	    
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx2, idx3}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx2, idx3});
		if(key != null && key.length() > 0){
			
			
		    
		    value = StringUtil.changeString(value, "*", "%");
		    
		    
		    spec.appendAnd();
		    
		    spec.appendWhere(new SearchCondition(MoldItemInfo.class, key, SearchCondition.LIKE, value), new int[]{idx3});
		    if(type > 0){
		    	spec.appendAnd();
			    String moldType = "Mold";
			    if(type == 1){
			    	moldType = "Press";
			    }
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx3});
			    
		    }
		    
		}else{
			if(type > 0){
							    
			    spec.appendAnd();
			    
			    String moldType = "Mold";
			    
			    if(type == 1){
			    	moldType = "Press";
			    }
			    
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx3});
			}
		}
		
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}

		Timestamp date = DateUtil.getTimestampFormat(day, "yyyy-MM-dd");
		
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		
		Timestamp nextDate = new Timestamp(c.getTimeInMillis());
		
		sc = new SearchCondition(TrySchdule.class, TrySchdule.PLAN_DATE, ">=", date);
		
		spec.appendWhere(sc, new int[]{0});
		
		sc = new SearchCondition(TrySchdule.class, TrySchdule.PLAN_DATE, "<", nextDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{0});		

		return spec;
		
	}
	
	
	public static QuerySpec getDayTask(String day, String key, String value, int type)throws Exception{
		
		QuerySpec spec = new QuerySpec();
		
		int idx1 = spec.appendClassList(E3PSTask.class, true);
    	int idx2 = spec.appendClassList(MoldProject.class, false);
        int idx3 = spec.appendClassList(ExtendScheduleData.class, false);
        int idx4 = spec.addClassList(MoldItemInfo.class, false);
		
	    
		ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
		ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id");
	    
		SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx2, idx4}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx2, idx4});
        
        if(key != null && key.length() > 0){
			
			
		    
		    
		   
		    value = StringUtil.changeString(value, "*", "%");
		    
		    spec.appendAnd();
		    
		    spec.appendWhere(new SearchCondition(MoldItemInfo.class, key, SearchCondition.LIKE, value), new int[]{idx4});
		    
		    if(type > 0){
		    	spec.appendAnd();
			    String moldType = "Mold";
			    if(type == 1){
			    	moldType = "Press";
			    }
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx4});
			    
		    }
		    
		}else{
			if(type > 0){
			    
			    spec.appendAnd();
			    
			    String moldType = "Mold";
			    
			    if(type == 1){
			    	moldType = "Press";
			    }
			    
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx4});
			}
		}
        
        
        
        if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
        
    	ca0 = new ClassAttribute(E3PSTask.class, "projectReference.key.id");
	    ca1 = new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id");
	    
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx2}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx2});
		
	    
	    if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
	    
	    ca0 = new ClassAttribute(E3PSTask.class, "taskSchedule.key.id");
	    ca1 = new ClassAttribute(ExtendScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
		
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx3}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx3});
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, true),new int[] { idx2});

		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] {idx2});
		
		
		Timestamp date = DateUtil.getTimestampFormat(day, "yyyy-MM-dd");
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		
		
		Timestamp nextDate = new Timestamp(c.getTimeInMillis());
		
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendOpenParen();
		
		//실제 시작일이 있을때 
		spec.appendOpenParen();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});

		spec.appendAnd();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, ">=", date);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, "<", nextDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{idx3});
		spec.appendCloseParen();
		//실제 시작일이 있을때  끝 
		spec.appendOr();
		
		
		//실제 종료일 있을때 
		
		spec.appendOpenParen();
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, ">=", date);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, "<", nextDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendCloseParen();
		
		//실제 종료일 있을때  끝 

		spec.appendOr();
		
		//실제 시작일이 없을때 
		spec.appendOpenParen();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendAnd();

		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});

		spec.appendAnd();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, ">=", date);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, "<", nextDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{idx3});
		
		
		spec.appendCloseParen();
		//실제 시작일이 없을때 끝 
		
		spec.appendCloseParen();
		
		spec.appendAnd();
		
		spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_TYPE, "=", "Try"), new int[]{idx1});
	
		//Kogger.debug(getClass(), spec);
		return spec;
		
	}
	
	public static QuerySpec getMonthTask(int year, int month, String key, String value)throws Exception{
		
		
		Timestamp startDate = DateUtil.getTimestampFormat( year + "-" + month + "-01", "yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month, 1);
		Timestamp endDate = new Timestamp(calendar.getTimeInMillis());
		return getTaskQuerySpec(startDate, endDate, key, value, TrySchduleHelper.ALL);
		
	}
	
	public static QuerySpec getTaskQuerySpec(Timestamp startDate, Timestamp endDate, String key, String value, int type)throws Exception{
		
		QuerySpec spec = new QuerySpec();
		
		int idx1 = spec.appendClassList(E3PSTask.class, true);
    	int idx2 = spec.appendClassList(MoldProject.class, false);
        int idx3 = spec.appendClassList(ExtendScheduleData.class, false);
        int idx4 = spec.addClassList(MoldItemInfo.class, false);
		
	    
		ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
		ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id");
	    
		SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx2, idx4}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx2, idx4});
        
        if(key != null && key.length() > 0 && value != null && value.length() > 0){
			
			
		    
		    
		   
		    value = StringUtil.changeString(value, "*", "%");
		    
		    spec.appendAnd();
		    
		    spec.appendWhere(new SearchCondition(MoldItemInfo.class, key, SearchCondition.LIKE, value), new int[]{idx4});
		    
		    if(type > 0){
		    	spec.appendAnd();
			    String moldType = "Mold";
			    if(type == 1){
			    	moldType = "Press";
			    }
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx4});
			    
		    }
		    
		}else{
			if(type > 0){
				
			    
			    spec.appendAnd();
			    
			    String moldType = "Mold";
			    
			    if(type == 1){
			    	moldType = "Press";
			    }
			    
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx4});
			}
		}
        
        if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
      
    	ca0 = new ClassAttribute(E3PSTask.class, "projectReference.key.id");
	    ca1 = new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id");
	    
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx2}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx2});
	    
	    if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
	    ca0 = new ClassAttribute(E3PSTask.class, "taskSchedule.key.id");
	    ca1 = new ClassAttribute(ExtendScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
		
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx3}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx3});
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, true),new int[] { idx2});

		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] {idx2});
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendOpenParen();
		
		//실제 시작일이 있을때 
		spec.appendOpenParen();
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendAnd();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, ">=", startDate);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, "<", endDate);
		spec.appendAnd();
		spec.appendWhere(sc, new int[]{idx3});
		spec.appendCloseParen();
		//실제 시작일이 있을때  끝 
		spec.appendOr();
		
		
		//실제 종료일 있을때 
		
		spec.appendOpenParen();
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, ">=", startDate);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, "<", endDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendCloseParen();
		
		//실제 종료일 있을때  끝 

		spec.appendOr();		
		
		
		//실제 시작일이 없을때 
		spec.appendOpenParen();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendAnd();

		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true);
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendAnd();
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, ">=", startDate);
		
		spec.appendWhere(sc, new int[]{idx3});
		
		sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE, "<", endDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{idx3});
		
		spec.appendCloseParen();
		//실제 시작일이 없을때 끝 
		spec.appendCloseParen();
		
		spec.appendAnd();
		
		spec.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_TYPE, "=", "Try"), new int[]{idx1});
		
		//Kogger.debug(getClass(), "spec = " + spec);
		
		return spec;
	}
	
	public static QuerySpec getMonth(int year, int month, String key, String value)throws Exception{
		
		Timestamp startDate = DateUtil.getTimestampFormat( year + "-" + month + "-01", "yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		Timestamp endDate = new Timestamp(calendar.getTimeInMillis());
		
		return getTrySchduleQuerySpec(startDate, endDate, key, value, TrySchduleHelper.ALL) ;
		
	}
	
	public static QuerySpec getTrySchduleQuerySpec(Timestamp startDate, Timestamp endDate, String key, String value, int type)throws Exception{
		QuerySpec spec = new QuerySpec();
		int idx1 = spec.addClassList(TrySchdule.class, true);
		int idx2 = spec.addClassList(MoldProject.class, false);
		int idx3 = spec.addClassList(MoldItemInfo.class, false);
		
		
		ClassAttribute ca0 = new ClassAttribute(TrySchdule.class, TrySchdule.MOLD_MASTER_REFERENCE + ".key.id");
	    ClassAttribute ca1 = new ClassAttribute(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id");
	    
	    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx1, idx2}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx1, idx2});
	    
	    if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, true),new int[] { idx2});

		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}
		
		spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] {idx2});

	    
	    spec.appendAnd();
	    
	    ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
	    ca1 = new ClassAttribute(MoldItemInfo.class, "thePersistInfo.theObjectIdentifier.id");
	    
	    sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[]{idx2, idx3}, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[]{idx2, idx3});
	    
		
		if(key != null && key.length() > 0 && value != null && value.length() > 0){
		    
		    value = StringUtil.changeString(value, "*", "%");
		    
		    spec.appendAnd();
		    
		    spec.appendWhere(new SearchCondition(MoldItemInfo.class, key, SearchCondition.LIKE, value), new int[]{idx3});
		    
		    if(type > 0){
		    	spec.appendAnd();
			    String moldType = "Mold";
			    if(type == 1){
			    	moldType = "Press";
			    }
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx3});
			    
		    }
		    
		}else{
			if(type > 0){
			    
			    spec.appendAnd();
			    
			    String moldType = "Mold";
			    
			    if(type == 1){
			    	moldType = "Press";
			    }
			    
			    spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", moldType), new int[]{idx3});
			}
		}
		
		
		if(spec.getConditionCount() > 0) {
			spec.appendAnd();
		}

		
		sc = new SearchCondition(TrySchdule.class, TrySchdule.PLAN_DATE, ">=", startDate);
		
		spec.appendWhere(sc, new int[]{0});
		
		sc = new SearchCondition(TrySchdule.class, TrySchdule.PLAN_DATE, "<", endDate);
		
		spec.appendAnd();
		
		spec.appendWhere(sc, new int[]{0});
		

		
		return spec;
	}
	
	public static void deleteTrySchdele(Hashtable hash) throws Exception{
		if (!wt.method.RemoteMethodServer.ServerFlag) {
			
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			Object obj = RemoteMethodServer.getDefault().invoke("deleteTrySchdele", TrySchduleHelper.class.getName() , null, argTypes, args);
			return; //obj;
		}
		
		String oid = (String)hash.get("oid");
		
		TrySchdule schdule = (TrySchdule)CommonUtil.getObject(oid);
		
		PersistenceHelper.manager.delete(schdule);
	}
	
	public static MoldProject getFromMaster(E3PSProjectMaster master)throws Exception{
	    
		QuerySpec spec = new QuerySpec(MoldProject.class); 
		long masterId = master.getPersistInfo().getObjectIdentifier().getId();
		spec.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId), new int[]{0});
		
		if(spec.getConditionCount() > 0){
			spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(MoldProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") ),new int[] {0});
		
		if(spec.getConditionCount() > 0){ 
			spec.appendAnd();
		}
		spec.appendWhere(new SearchCondition(MoldProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {0});
        
		QueryResult rs = PersistenceHelper.manager.find(spec);
		MoldProject project = null;
		if(rs.hasMoreElements()){
			project = (MoldProject)rs.nextElement();
		}
		return project;
		
	}
	
	public static void main(String args[])throws Exception{
		String todayStr = "2010/10/31";
		String keyDate = "2010/10/17";
		
		//Kogger.debug(getClass(), todayStr.compareTo(keyDate));
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    
	    Date date = format.parse(todayStr);
	    Timestamp tims = new Timestamp(date.getTime());
		
		//Kogger.debug(getClass(), tims);
		
		Calendar c = Calendar.getInstance();
		c.setTime(tims);
		c.add(Calendar.DATE, 1);
		
		Timestamp tims2 = new Timestamp(c.getTimeInMillis());
		//Kogger.debug(getClass(), tims2);
		
		RemoteMethodServer server = RemoteMethodServer.getDefault();
		server.setPassword("wcadmin");
		server.setUserName("wcadmin");
		Hashtable hash = new Hashtable();		
		hash.put("moldProject", "");
		hash.put("tryType", "Sample");
		hash.put("material", "");
		hash.put("ton", "5");
		hash.put("quantity", "5");
		hash.put("requester", "");
		hash.put("planDate", "2010/10/1");
		hash.put("des", "testDes..");
		
		Kogger.debug(TrySchduleHelper.class, "save....");
		Kogger.debug(TrySchduleHelper.class, "save....");
		saveTrySchdule(hash);
		
		hash = new Hashtable();		
		hash.put("moldProject", "");
		hash.put("tryType", "Sample");
		hash.put("material", "");
		hash.put("ton", "5");
		hash.put("quantity", "5");
		hash.put("requester", "");
		hash.put("planDate", "2010/10/31");
		hash.put("des", "testDes..");
		saveTrySchdule(hash);
		
		
		hash = new Hashtable();		
		hash.put("moldProject", "");
		hash.put("tryType", "Sample");
		hash.put("material", "");
		hash.put("ton", "5");
		hash.put("quantity", "5");
		hash.put("requester", "");
		hash.put("planDate", "2010/11/17");
		hash.put("des", "testDes..");
		saveTrySchdule(hash);
		
		
		hash = new Hashtable();		
		hash.put("moldProject", "");
		hash.put("tryType", "Sample");
		hash.put("material", "");
		hash.put("ton", "5");
		hash.put("quantity", "5");
		hash.put("requester", "");
		hash.put("planDate", "2010/11/18");
		hash.put("des", "testDes..");
		saveTrySchdule(hash);
		
//		CompositeQuerySpec spec = new CompositeQuerySpec();
//		spec.addComponent(getMonthTask(2010, 10, MoldItemInfo.DIE_NO, "fds3**"));
//		//spec.addComponent(getMonth(2010, 10, "", ""));
//		
//		QueryResult rs = PersistenceHelper.manager.find(spec);
//		
//		Kogger.debug(TrySchduleHelper.class, "rs.size() = " + rs.size());
		
		
	}
	
}
