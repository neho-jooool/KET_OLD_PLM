package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectViewDepartmentLink;
import ext.ket.shared.log.Kogger;

public class ProjectQueryUtil implements wt.method.RemoteAccess, java.io.Serializable {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;	
	
	public static QueryResult find(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"find",
						ProjectQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			qr = PersistenceHelper.manager.find(getPJTQuerySpec(map));
		}
		catch(Exception e){
			Kogger.error(ProjectQueryUtil.class, e);
		}
		return qr;
	}

	////////////////////////////////////////////////////////////////////////////// 
	/// openPagingSession
	////////////////////////////////////////////////////////////////////////////// 
	
	public static PagingQueryResult openPagingSession(HashMap map, int start, int size) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class, int.class, int.class};
			Object args[] = new Object[]{map, new Integer(start), new Integer(size)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"openPagingSession",
						ProjectQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			QuerySpec qs = getPJTQuerySpec(map);	
			result = openPagingSession(qs, start, size);			
		}
		catch(Exception e) {
			Kogger.error(ProjectQueryUtil.class, e);
		}
		return result;
	}
	
	public static PagingQueryResult openPagingSession(QuerySpec spec, int start, int size) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{QuerySpec.class, int.class, int.class};
			Object args[] = new Object[]{spec, new Integer(start), new Integer(size)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"openPagingSession",
						ProjectQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			result = PagingSessionHelper.openPagingSession(start, size, spec);
		
		}
		catch(Exception e) {
			Kogger.error(ProjectQueryUtil.class, e);
		}
		return result;
	}
	
	public static PagingQueryResult fetchPagingSession(int start, int size, long sessionId) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{int.class, int.class, long.class};
			Object args[] = new Object[]{new Integer(start), new Integer(size), new Long(sessionId)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"fetchPagingSession",
						ProjectQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProjectQueryUtil.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			result = PagingSessionHelper.fetchPagingSession(start, size, sessionId);
		}
		catch(Exception e) {
			Kogger.error(ProjectQueryUtil.class, e);
		}
		return result;
	}
	
	
	
	public static QuerySpec getPJTQuerySpec(HashMap map) throws WTException {
		QuerySpec spec = null;
		try {
	        spec = new QuerySpec();
	        spec.setAdvancedQueryEnabled(true);
			Class main_target = E3PSProject.class;
			int main_idx = spec.appendClassList(main_target, true);
			
			SubSelectExpression subfrom = new SubSelectExpression(getDistinctSpec(map));
	        subfrom.setFromAlias(new String[]{"B0"}, 0);
            int sub_pjt_index = spec.appendFrom(subfrom);
            
            SearchCondition sc = new SearchCondition(
            		new ClassAttribute(main_target, "thePersistInfo.theObjectIdentifier.id"),
            		"=",
            		new KeywordExpression(spec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
            sc.setFromIndicies(new int[]{main_idx,sub_pjt_index},0);
            sc.setOuterJoin(0);
            spec.appendWhere(sc, new int[]{main_idx,sub_pjt_index});
            
            SQLFunction sqlfunction = null;
            ClassAttribute sortCa = null;
			OrderBy orderby = null;
			int sortIdx = 0;
			
			
//			sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER,
//					new ClassAttribute(main_target, JELProject.PJT_NO));
			sqlfunction.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			//sortCa = new ClassAttribute(main_target, JELProject.PJT_NAME);
			//sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			
			
			spec.appendSelect(sqlfunction, new int[]{main_idx}, false);
			orderby = new OrderBy(sqlfunction, true, null);
			spec.appendOrderBy(orderby, new int[]{main_idx});
		}
		catch (QueryException e)
        {
            Kogger.error(ProjectQueryUtil.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(ProjectQueryUtil.class, e);
        }
        catch (Exception e)
        {
            Kogger.error(ProjectQueryUtil.class, e);
        }
        
        //Kogger.debug(getClass(), "project Search spec ===>"+mainSpec);
        return spec;
	}
	
	public static QuerySpec getDistinctSpec(HashMap map) throws WTException {		
		QuerySpec spec = null;
		try {
			spec = new QuerySpec();
			spec.setAdvancedQueryEnabled(true);
			spec.setDistinct(true);
			
			Class cls_pjt = E3PSProject.class;
			int idx_pjt = spec.addClassList(cls_pjt, false);
			
			/*
			 * **********************************************************************************************
			 * select
			 * **********************************************************************************************
			 */
			ClassAttribute ca = new ClassAttribute(cls_pjt,
					"thePersistInfo.theObjectIdentifier.id");
	    	ca.setColumnAlias("projectId");
	    	spec.appendSelect(ca, false);
	    	
	    	/*
	    	 * *********************************************************************************************
	    	 * where
	    	 * *********************************************************************************************
	    	 */
			spec.appendWhere(
					new SearchCondition(cls_pjt, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
					new int[] { idx_pjt});
			spec.appendAnd();
			spec.appendWhere(
					new SearchCondition(cls_pjt, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
					new int[] {idx_pjt});
			
			//프로젝트 종류 Field
			String pjtType = StringUtil.trimToEmpty( (String)map.get("pjtType") );
			if(pjtType.length() > 0) {
				spec.appendAnd();
//				spec.appendWhere(
//						new SearchCondition(cls_pjt, JELProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt(pjtType)),
//						new int[]{idx_pjt});
			}
			
			//프로젝트 NO Field
//			setUpperNLikeCondition(spec, cls_pjt, idx_pjt, JELProject.PJT_NO, 
//					StringUtil.trimToEmpty( (String)map.get("pjtNo") ) );
			//프로젝트 명 Field
//			setUpperNLikeCondition(spec, cls_pjt, idx_pjt, JELProject.PJT_NAME, 
//					StringUtil.trimToEmpty( (String)map.get("pjtName") ) );
			
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "modelReference.key.id", "MODELCODE", 
					StringUtil.trimToEmpty( (String)map.get("modelcode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "divisionReference.key.id", "DIVISIONCODE", 
					StringUtil.trimToEmpty( (String)map.get("divisioncode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "levelReference.key.id", "LEVELCODE", 
					StringUtil.trimToEmpty( (String)map.get("levelcode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "productReference.key.id", "PRODUCTCODE", 
					StringUtil.trimToEmpty( (String)map.get("productcode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "customerReference.key.id", "CUSTOMERCODE", 
					StringUtil.trimToEmpty( (String)map.get("customercode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "devcompanyReference.key.id", "DEVCOMPANYCODE", 
					StringUtil.trimToEmpty( (String)map.get("devcompanycode") ) );
			setNumberCodeQuery(spec, cls_pjt, idx_pjt, "makecompanyReference.key.id", "MAKECOMPANYCODE", 
					StringUtil.trimToEmpty( (String)map.get("makecompanycode") ) );
			
			
			//프로젝트 상태 Field
			Object psObj = map.get("pjtState");//pjtState(상태);
			ArrayList psArr = new ArrayList();		
			if(psObj != null) {
				if(psObj instanceof String) {
					String pjtState = (String)psObj;
					if((pjtState.trim()).length() > 0) {
						psArr.add(pjtState);
					}
				}
				else if(psObj instanceof Vector) {
					Vector psVec = (Vector)psObj;
					for(int i = 0; i < psVec.size(); i++) {
						String pjtState = (String)psVec.get(i);
						if((pjtState.trim()).length() > 0) {
							psArr.add(pjtState);
						}
					}
				}
				else if(psObj instanceof String[]) {
					String[] psArry = (String[])psObj;
					for(int i = 0; i < psArry.length; i++) {
						String pjtState = psArry[i];
						if((pjtState.trim()).length() > 0) {
							psArr.add(pjtState);
						}
					}
				}
				else if(psObj instanceof ArrayList) {
					ArrayList pa = (ArrayList)psObj;
					for(int i = 0; i < pa.size(); i++) {
						String pjtState = (String)pa.get(i);
						if((pjtState.trim()).length() > 0) {
							psArr.add(pjtState);
						}
					}
				}
			}
			if(psArr.size() > 0) {
				spec.appendAnd();
						
				spec.appendOpenParen();
				for(int i = 0; i < psArr.size(); i++) {
					if(i > 0) {
						spec.appendOr();
					}
					spec.appendWhere(
							new SearchCondition(cls_pjt, "state.state" , SearchCondition.EQUAL, 
									((String)psArr.get(i)).trim()), 
							new int[]{idx_pjt});
				}
				spec.appendCloseParen();
			}
			
			//계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
			String planStartStartDate = StringUtil.trimToEmpty( (String)map.get("planStartStartDate") );				//planStartStartDate(계획 시작일자 - 시작)
			String planStartEndDate = StringUtil.trimToEmpty( (String)map.get("planStartEndDate") );					//planStartEndDate(계획 시작일자 - 끝)
			String planEndStartDate = StringUtil.trimToEmpty( (String)map.get("planEndStartDate") );					//planEndStartDate(계획 종료일자 - 시작)
			String planEndEndDate = StringUtil.trimToEmpty( (String)map.get("planEndEndDate") );						//planEndEndDate(계획 종료일자 - 끝)
			
			if( (planStartStartDate != null && planStartStartDate.length() > 0)
					|| (planStartEndDate != null && planStartEndDate.length() > 0)
					|| (planEndStartDate != null && planEndStartDate.length() > 0)
					|| (planEndEndDate != null && planEndEndDate.length() > 0)) {
				spec.appendAnd();
				
				Class schedule = ExtendScheduleData.class;
				int idx_schedule = spec.appendClassList(schedule, false);
				
				SearchCondition sc = new SearchCondition(
						new ClassAttribute(cls_pjt, "pjtSchedule.key.id"), 
						"=", 
						new ClassAttribute(schedule, "thePersistInfo.theObjectIdentifier.id"));
				sc.setFromIndicies(new int[]{idx_pjt, idx_schedule}, 0);
				sc.setOuterJoin(0);
				spec.appendWhere(sc, new int[]{idx_pjt, idx_schedule});
				
				//계획 시작일자 - 시작 Field
				if(planStartStartDate != null && planStartStartDate.length() > 0) {
					spec.appendAnd();
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, 
							SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planStartStartDate));
					spec.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 시작일자 - 끝 Field
				if(planStartEndDate != null && planStartEndDate.length() > 0) {
					spec.appendAnd();
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, 
							SearchCondition.LESS_THAN, DateUtil.convertStartDate(planStartEndDate));
					spec.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 종료일자 - 시작 Field
				if(planEndStartDate != null && planEndStartDate.length() > 0) {
					spec.appendAnd();				
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, 
							SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planEndStartDate));
					spec.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 종료일자 - 끝 Field
				if(planEndEndDate != null && planEndEndDate.length() > 0) {
					spec.appendAnd();				
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, 
							SearchCondition.LESS_THAN, DateUtil.convertStartDate(planEndEndDate));
					spec.appendWhere(sc , new int[]{idx_schedule});
				}
			}
			
			//PM
			String pmOid = StringUtil.trimToEmpty( (String)map.get("pmOid") );	//PM
			if(pmOid.length() > 0) {
				WTUser pm = (WTUser)CommonUtil.getObject(pmOid);
				if(pm != null) {
					setPMSpec(spec, cls_pjt, idx_pjt, pm);
				}
			}
			
			String checkAccess = StringUtil.trimToEmpty( (String)map.get("checkAccess") );
			if(checkAccess.length()==0) {
				checkAccess = "true";
			}
			
			if("true".equals(checkAccess.toLowerCase())) {
				spec.appendAnd();
				setAccessAuthSpec(spec, cls_pjt, idx_pjt);	
			}
			
		}
		catch(Exception e) {
			Kogger.error(ProjectQueryUtil.class, e);
		}
		
		return spec;
	}
	
	
	private static void setAccessAuthSpec(QuerySpec spec, Class target, int idx_target) throws Exception {
		WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
        Department dept = DepartmentHelper.manager.getDepartment(wtuser);
        ArrayList childList = null;
        if(dept != null)
        	childList = DepartmentHelper.manager.getChildDeptTree(dept);
        if(childList == null)
        	childList = new ArrayList();
        if(dept != null)
        	childList.add(0, dept);
        
		
		spec.appendOpenParen();//1
		
		//Project 구성원/ View권한(사용자) start.
		Class linkClass = ProjectMemberLink.class;
		int idx_linkClass = spec.appendClassList(linkClass, false);
        SearchCondition sc0 = null;
        
		spec.appendOpenParen();//2
        sc0 = new SearchCondition(
        		new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id"),
        		"=", 
        		new ClassAttribute(linkClass, "roleAObjectRef.key.id")
        	);
        sc0.setFromIndicies(new int[]{idx_target, idx_linkClass}, 0);
        sc0.setOuterJoin(0);
        spec.appendWhere(sc0, new int[]{idx_target, idx_linkClass});
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(linkClass, "roleBObjectRef.key.id", 
        					SearchCondition.EQUAL, CommonUtil.getOIDLongValue(wtuser)),
        				new int[] {idx_linkClass} );
		spec.appendCloseParen();//2
		
		//Project View권한(부서) start.
		if(childList.size() > 0) {
			spec.appendOr();			
	        spec.appendOpenParen();//3
	        Class deptLinkClass = ProjectViewDepartmentLink.class;
	        int idx_deptLink = spec.appendClassList(deptLinkClass, false);
	        sc0 = new SearchCondition(
	        		new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id"), 
	        		"=", 
	        		new ClassAttribute(deptLinkClass, "roleAObjectRef.key.id")
	        	);
	        sc0.setFromIndicies(new int[]{idx_target, idx_deptLink}, 0);
	        sc0.setOuterJoin(0);
	        spec.appendWhere(sc0, new int[]{idx_target, idx_deptLink});
	        
	        spec.appendAnd();
	        
	        spec.appendOpenParen();//4
	        for(int i = 0; i < childList.size(); i++) {
	        	if(i > 0)
	        		spec.appendOr();
	        	
	        	Department s = (Department)childList.get(i);
		        sc0 = new SearchCondition(
		        		deptLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL,
		        		s.getPersistInfo().getObjectIdentifier().getId());
		        spec.appendWhere(sc0, new int[]{idx_deptLink});
	        }
	        spec.appendCloseParen();//4
	        spec.appendCloseParen();//3
		}
        //Project View권한(부서) end.
        spec.appendCloseParen();//1
	}
	
	private static void setPMSpec(QuerySpec spec, Class target, int idx, WTUser pm)
		throws WTException, WTPropertyVetoException
	{
		if(pm == null)
			return;
		
		if(spec.getConditionCount() > 0)
			spec.appendAnd();
		
		Class class_pm = ProjectMemberLink.class;
		int idx_pm = spec.appendClassList(class_pm, false);
		
		SearchCondition sc = null;
		sc = new SearchCondition(
				new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id"),
				"=",
				new ClassAttribute(class_pm, "roleAObjectRef.key.id"));
		sc.setFromIndicies(new int[]{idx, idx_pm}, 0);
		sc.setOuterJoin(0);
		spec.appendWhere(sc, new int[]{idx, idx_pm});
		
		spec.appendAnd();
		
		spec.appendWhere(
				new SearchCondition(class_pm,ProjectMemberLink.PJT_MEMBER_TYPE,
						SearchCondition.EQUAL, ProjectUserHelper.manager.PM),
				new int[]{idx_pm});
		
		spec.appendAnd();
		
		spec.appendWhere(
				new SearchCondition(class_pm,"roleBObjectRef.key.id",
						SearchCondition.EQUAL, pm.getPersistInfo().getObjectIdentifier().getId()),
				new int[]{idx_pm});
	}
	
	private static void setNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value)
		throws WTException
	{
		if((value == null) || (value.trim().length() == 0)) {
			return;
		}
		SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
	}
	
	public static void setUpperNLikeCondition(QuerySpec spec, Class target, int index, String column, String value )
		throws WTException
	{		
		if( (value == null) || (value.trim().length() == 0) ) {
			return;
		}
		
		if(!spec.isAdvancedQueryEnabled()) {
			spec.setAdvancedQueryEnabled(true);
		}
		
		if(spec.getConditionCount() > 0)
    		spec.appendAnd();
    	
    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER,
    			new ClassAttribute(target, column));
		SearchCondition sc = new SearchCondition(sqlfunction, SearchCondition.LIKE,
				ConstantExpression.newExpression("%" + value.trim().toUpperCase()+"%") );
		spec.appendWhere(sc, new int[] {index });		
	}
	
}
