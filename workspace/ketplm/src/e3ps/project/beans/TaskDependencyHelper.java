package e3ps.project.beans;

import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TaskDependencyHelper implements RemoteAccess{
    public static final TaskDependencyHelper manager = new TaskDependencyHelper();
    private TaskDependencyHelper() {}
    
    public void setDependTask(TemplateTask dependSource, TemplateTask dependTarget) {

			try {

				if(isAlreadyDependencyLink(dependSource, dependTarget)){
					return;
				}
				TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(dependSource, dependTarget);
				PersistenceHelper.manager.save(link);
				
				if ( dependSource.getProject() instanceof E3PSProject ) {
					ProjectScheduleHelper.manager.post_modify_Schedule((E3PSTask)dependSource);
				} else {  
					//Kogger.debug(getClass(), "$$$$$$$$$$$$$$$$$$$$$$$$$$");
					ProjectScheduleHelper.manager.post_modify_template_duration(dependSource);
				}
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
//	    }
	}
    
    public static boolean isAlreadyDependencyLink(TemplateTask dependSource, TemplateTask dependTarget)throws Exception{
    	
    	QuerySpec spec = new QuerySpec(TemplateTask.class, TaskDependencyLink.class);
    	
    	spec.appendWhere(new SearchCondition(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id", "=", PersistenceHelper.getObjectIdentifier(dependTarget).getId()), new int[]{0});
    	
    	QueryResult rs = PersistenceHelper.manager.navigate(dependSource, TaskDependencyLink.DEPEND_TARGET_ROLE, spec);
    	
    	return rs.hasMoreElements();
    	
    }
	    
    public void deleteTaskDependency(TemplateTask task) {
		try {
	        QueryResult qr = getDependTaskList(task);
	        if(qr != null) {
	        	while ( qr.hasMoreElements() ) {
					TaskDependencyLink link = (TaskDependencyLink)qr.nextElement();
					PersistenceHelper.manager.delete(link);
		        }
	        }
	        
/*
	        if(task.getProject() instanceof JELProject) {
	        	ProjectScheduleHelper.manager.post_modify_Schedule((JELTask)task);
	        } else {
	        	ProjectScheduleHelper.manager.post_modify_template_duration(task);
	        }
	        */
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}		
    }
    
	public void deleteDependTask(TemplateTask dependSource, TaskDependencyLink link) {
	    try {
			PersistenceHelper.manager.delete(link);
		    //OLD
//			 if ( dependSource.getProject() instanceof TemplateProject ) {
//			     ProjectScheduleHelper.manager.checkAllDuration(dependSource);
//			} else {
//			    ProjectScheduleHelper.manager.checkAllSchedule((JELTask)dependSource);  
//			}
			
			//NEW
//			if(dependSource.getProject() instanceof JELProject) {
//				ProjectScheduleHelper.manager.post_modify_Schedule((JELTask)dependSource);
//			} else {
//				ProjectScheduleHelper.manager.post_modify_template_duration(dependSource);
//			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}	    
	}
	
	public TaskDependencyLink getTaskDependencyLink(TemplateTask dependSource, TemplateTask dependTarget) {
		TaskDependencyLink link = null;
		try {
			QuerySpec spec = new QuerySpec();
			Class taskDependencyLinkClass = TaskDependencyLink.class;        
		    int taskDependencyLinkClassPos = spec.addClassList(taskDependencyLinkClass,true);
		    spec.appendWhere(new SearchCondition(taskDependencyLinkClass,"roleAObjectRef.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(dependSource)),taskDependencyLinkClassPos);
		    spec.appendAnd();
		    spec.appendWhere(new SearchCondition(taskDependencyLinkClass,"roleBObjectRef.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(dependTarget)),taskDependencyLinkClassPos);
		    QueryResult qr = PersistenceHelper.manager.find(spec);
		    
		    if(qr != null) {
			    if ( qr.hasMoreElements() ) {
			        Object[] objArr = (Object[])qr.nextElement();
			        link = (TaskDependencyLink)objArr[0];
			    }
		    }
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	    return link;
	}
	
	public boolean isDependencyLink(TemplateTask dependSource, TemplateTask dependTarget) {
		try {
			QuerySpec spec = new QuerySpec();
		
		    Class taskDependencyLinkClass = TaskDependencyLink.class;        
		    int taskDependencyLinkClassPos = spec.addClassList(taskDependencyLinkClass,true);
		    spec.appendWhere(new SearchCondition(taskDependencyLinkClass,"roleBObjectRef.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(dependSource)),taskDependencyLinkClassPos);
		    spec.appendAnd();
		    spec.appendWhere(new SearchCondition(taskDependencyLinkClass,"roleAObjectRef.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(dependTarget)),taskDependencyLinkClassPos);
		    
		    QueryResult qr = PersistenceHelper.manager.find(spec);
		    	 
		    if(qr != null) {
			    if ( qr.hasMoreElements() ) {
			    	return true;
		    	}
		    }
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	    return false;
	}
	
	public QueryResult getDependTaskList(TemplateTask task) {
		QueryResult result = new QueryResult();
	    try {
	    	result = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_TARGET_ROLE, TaskDependencyLink.class, false);
	    	
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}
	
	/**
	 * 후행 TASK
	 * @param task
	 * @return
	 */
	public QueryResult getDependTaskList1(TemplateTask task) {
	    try {
	    	QueryResult result = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_SOURCE_ROLE, TaskDependencyLink.class, false);
	    	if(result != null && result.size() > 0) {
	    		return result;
	    	}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}
	
	// 동일 부모하에 있는 Task 들의 종속 리스트를 빼오는 쿼리..
	// 현재 동일 부모 조건이 안먹히고 있음...
	// 나중에 체크 할것..
	public QueryResult getSameDepthDependTaskList(TemplateTask task) {
	    int depth = 2;
	    if ( task.getParent() != null ) depth = 3;
	    
		try {
			QuerySpec spec = new QuerySpec();
			Class taskDependencyLinkClass = TaskDependencyLink.class;
		    Class taskClass = TemplateTask.class;
		    int taskDependencyLinkClassPos = spec.addClassList(taskDependencyLinkClass,true);
		    int taskClassPos = spec.addClassList(taskClass,false);
		    
		    spec.appendJoin(taskDependencyLinkClassPos, TaskDependencyLink.DEPEND_SOURCE_ROLE, taskClassPos);
		    
		    if ( depth == 2 ) {
		        spec.appendWhere(new SearchCondition(taskClass, "parentReference.key.id", SearchCondition.EQUAL, (long)0), taskClassPos);
		    } else if ( depth == 3 ) {
		        spec.appendWhere(new SearchCondition(taskClass, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(task.getParent())), taskClassPos);
		    }
		    
		    spec.appendAnd();
		    spec.appendWhere(new SearchCondition(taskDependencyLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(task)), taskDependencyLinkClassPos);
		    QueryResult result = PersistenceHelper.manager.find(spec);
		    if(result != null && result.size() > 0) {
		    	return result;
		    }
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}
	
	
	/**
	 * 선행태스크로 등록 가능한 태스크의 목록을 리턴한다.
	 * @param _task 
	 * @return
	 * @throws WTException 
	 */
	public ArrayList getCanDependTaskList(TemplateTask _task) {// throws WTException {
//		if(!SERVER) {
//			Class argTypes[] = new Class[]{TemplateTask.class};
//			Object args[] = new Object[]{_task};
//			Object obj = null;
//			try {
//				obj = wt.method.RemoteMethodServer.getDefault().invoke(
//						"getCanDependTaskList",
//						"e3ps.project.beans.TaskDependencyHelper",
//						null,
//						argTypes,
//						args);
//			}
//			catch(RemoteException e) {
//				Kogger.error(getClass(), e);
//				throw new WTException(e);
//			}
//			catch(InvocationTargetException e) {
//				Kogger.error(getClass(), e);
//				throw new WTException(e);
//			}
//			return (ArrayList) obj;
//		}
		
		
		/**
		 * [QuerySpec]
		 * select * from jeltask 
		 * where ida3b4 = '158122' 
		 * 		 and ida3parentreference = ( select ida2a2 
		 * 								 	 from jeltask 
		 * 								 	 where ida3parentreference = '0'
		 * 									   	   and ida3b4 = '158122' 
		 * 										   and taskseq < ( select taskseq 
		 * 														   from jeltask 
		 * 													   	   where ida3b4 = '158122' 
		 * 															     and ida2a2 = ( select ida3parentreference 
		 * 																				from jeltask 
		 * 																				where ida2a2 = '158160'
		 * 																					  and ida3b4 = '158122'
		 * 																			   )
		 * 														  )
		 * 									)
		 * 
		 * ClassAttribute ca = new ClassAttribute(mpartClass, MPart.CD_ITEM);
		 * ca.setColumnAlias("wtsort" + String.valueOf(0));
		 * qs.appendSelect(ca, new int[]{index}, false);
		 * 
		 * if(cd_item != null && cd_item.length() > 0) {
		 * 		sc = new SearchCondition(mpartClass, MPart.CD_ITEM, SearchCondition.EQUAL, cd_item);
		 * 		qs.appendWhere(sc, new int[]{index});
		 * }
		 * 
		 * ClassAttribute history = new ClassAttribute(pjtClass, JELProject.PJT_HISTORY);
		 * sc = new SearchCondition(history, SearchCondition.EQUAL, new SubSelectExpression(subQs));
		 * qs.appendWhere(sc, new int[] { index });
		 */
		
		//NEW
//		ArrayList list = new ArrayList();
//		try {
//			QuerySpec spec = new QuerySpec();
//			spec.setAdvancedQueryEnabled(true);
//			
//			Class taskClass = TemplateTask.class;
//			int idx_taskClass = spec.addClassList(taskClass, true);
//			
//			//Main Query
//			//select * from jeltask where ida3b4 = '158122' and ida3parentreference = [Sub Query]
//			SearchUtil.appendEQUAL(spec, taskClass, "projectReference.key.id", CommonUtil.getOIDLongValue(_task.getProject()), idx_taskClass);
//			
//			//Sub Query
//			// select ida2a2 from jeltask where ida3parentreference = '0' and ida3b4 = '158122' and taskseq < 2
//			QuerySpec subSpec = new QuerySpec();
//			
//			subSpec.getFromClause().setAliasPrefix("B");
//			int idx_subSpec = subSpec.addClassList(taskClass, false);
//			
//			SearchUtil.appendEQUAL(subSpec, taskClass, "parentReference.key.id", (long) 0, idx_taskClass); 
//			SearchUtil.appendEQUAL(subSpec, taskClass, "projectReference.key.id", CommonUtil.getOIDLongValue(_task.getProject()), idx_subSpec);
//			SearchCondition subWhere = new SearchCondition(taskClass, "taskSeq", SearchCondition.EQUAL, ((TemplateTask)_task.getParent()).getTaskSeq());
//			subSpec.appendWhere(subWhere, new int[]{idx_subSpec});
//			
//			ClassAttribute parent = new ClassAttribute(taskClass, "projectReference.key.id");
//			SearchCondition mainWhere = new SearchCondition(parent, SearchCondition.EQUAL, new SubSelectExpression(subSpec));
//			if(spec.getConditionCount() > 0) spec.appendAnd();
//			spec.appendWhere(mainWhere, new int[]{idx_taskClass});
//			
//			Kogger.debug(getClass(), "QuerySpec>>> \n"+spec);
//			QueryResult qr = PersistenceHelper.manager.find(spec);
//		    int count = 0;
//		    if(qr != null) {
//			    while ( qr.hasMoreElements() ) {
//			        Object[] o = (Object[])qr.nextElement();
//			        TemplateTask task = (TemplateTask)o[0];
//			        Kogger.debug(getClass(), "Task>>> "+task.getTaskName());
//			        list.add(task);
//			    }
//		    }
//		} catch (QueryException e) {
//			Kogger.error(getClass(), e);
//		} catch (WTPropertyVetoException e) {
//			Kogger.error(getClass(), e);
//		} catch (WTException e) {
//			Kogger.error(getClass(), e);
//		}
		
		
	    int taskSeq = _task.getTaskSeq();
			
		//OLD
	    ArrayList list = new ArrayList();
		try {
			QuerySpec spec = new QuerySpec();
			Class taskClass = TemplateTask.class;        
		    int taskClassPos = spec.addClassList(taskClass, true);   
		    spec.appendWhere(new SearchCondition(taskClass,"projectReference.key.id",SearchCondition.EQUAL, CommonUtil.getOIDLongValue(_task.getProject())),taskClassPos);        
		    spec.appendAnd();     
		    
		    if ( _task.getParent() == null ) {
		        spec.appendWhere(new SearchCondition(taskClass, "parentReference.key.id", SearchCondition.EQUAL,(long)0),taskClassPos);
		    } else {
		        spec.appendWhere(new SearchCondition(taskClass, "parentReference.key.id", SearchCondition.EQUAL,CommonUtil.getOIDLongValue(_task.getParent())),taskClassPos);
		    }
		
		    spec.appendAnd();		    
		    spec.appendWhere(new SearchCondition(taskClass,"taskSeq", SearchCondition.LESS_THAN, taskSeq),taskClassPos);
		        	
		    SearchUtil.setOrderBy(spec,taskClass,taskClassPos,TemplateTask.TASK_SEQ,"taskSeq",false);
		    QueryResult qr = PersistenceHelper.manager.find(spec);
		    int count = 0;
		    if(qr != null) {
			    while ( qr.hasMoreElements() ) {
			        Object[] o = (Object[])qr.nextElement();
			        TemplateTask task = (TemplateTask)o[0];
			        list.add(task);
			    }
		    }
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}
	    return list;        
	} 
	
	/**
	 * * 템플릿 태스크의 선행태스크 정보를 타켓 태스크에 복사한다.
	 * 
	 * @param totalTask	기생성된 태스크 정보  
	 * @param task			타켓 태스크
	 * @param template	원본 템플릿 태스크 
	 */
	public void copyTaskDependencyInfo(Hashtable totalTask,TemplateTask task,TemplateTask template) {
	    QueryResult dependList = getDependTaskList(template);
	    if(dependList != null) {
		    while ( dependList.hasMoreElements() ) {
				TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
				TemplateTask dependTemplateTask = link.getDependTarget();
				String dependTemplateTaskOID = CommonUtil.getOIDString(dependTemplateTask);
				if ( totalTask.containsKey(dependTemplateTaskOID) ) {
					TemplateTask dependTask = (TemplateTask)totalTask.get(dependTemplateTaskOID);
				    copyDependTask(task,dependTask);
				}			
		    }
	    }
	}
	
	private void copyDependTask(TemplateTask dependSource,TemplateTask dependTarget) {
	    if ( isDependencyLink(dependSource,dependTarget) ) return;
	    else {
			try {
				TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(dependSource,dependTarget);
				PersistenceHelper.manager.save(link);
			} catch (WTException e) {
				Kogger.error(getClass(), e);
			}
	    }
	}

	public void updateCopyTaskDependencyInfo(Hashtable totalTask, E3PSTask newChildTask, E3PSTask childOldTask) {
		QueryResult dependList = getDependTaskList(childOldTask);
	    if(dependList != null) {
		    while ( dependList.hasMoreElements() ) {
				TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
				E3PSTask dependTemplateTask = (E3PSTask) link.getDependTarget();
				String dependTemplateTaskOID = CommonUtil.getOIDString(dependTemplateTask);
				if ( totalTask.containsKey(dependTemplateTaskOID) ) {
					TemplateTask dependTask = (TemplateTask)totalTask.get(dependTemplateTaskOID);
				    copyDependTask(newChildTask, dependTask);
				}			
		    }
	    }
	}
}
