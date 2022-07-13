package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.TemplateProject;
import ext.ket.shared.log.Kogger;

public class E3PSProjectSearchHelper implements wt.method.RemoteAccess, java.io.Serializable {
	
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;	
	public static QueryResult getMainProject(Class pjtClass, int begin, int size) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Class.class, int.class, int.class};
			Object args[] = new Object[]{pjtClass, new Integer(begin), new Integer(size)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getMainProject",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {			
			QuerySpec subQs = new QuerySpec();
			subQs.setDistinct(true);
			int sub_idx = subQs.appendClassList(pjtClass, false);
			
			ClassAttribute pjtno = new ClassAttribute(pjtClass, TemplateProject.PJT_NO);
			pjtno.setFromAlias(new String[]{subQs.getFromClause().getAliasAt(sub_idx)}, 0);
			pjtno.setColumnAlias("pjtNo");
			subQs.appendSelect(pjtno, new int[]{sub_idx}, false);
			
			ClassAttribute pjtname = new ClassAttribute(pjtClass, TemplateProject.PJT_NAME);
			pjtname.setFromAlias(new String[]{subQs.getFromClause().getAliasAt(sub_idx)}, 0);
			pjtname.setColumnAlias("pjtName");
			subQs.appendSelect(pjtname, new int[]{sub_idx}, false);
			
			ClassInfo classinfo = WTIntrospector.getClassInfo(pjtClass);
			DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
			BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();
			String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
			String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();
			
			KeywordExpression ke = new KeywordExpression(subQs.getFromClause().getAliasAt(sub_idx) + "." + objname +
					"||':'||" +
					subQs.getFromClause().getAliasAt(sub_idx) + "." + objid);
			ke.setFromAlias(new String[]{subQs.getFromClause().getAliasAt(sub_idx)}, 0);
			ke.setColumnAlias("pjtOid");
			subQs.appendSelect(ke, new int[]{sub_idx}, false);
			
			
			
			//latest Project
			//setLatestProjectSpec(subQs, pjtClass, sub_idx);
			
			if(subQs.getConditionCount() > 0) 
				subQs.appendAnd();
			subQs.appendWhere(new SearchCondition(pjtClass, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") ),new int[] {sub_idx});
			
			if(subQs.getConditionCount() > 0) 
				subQs.appendAnd();
			subQs.appendWhere(new SearchCondition(pjtClass, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {sub_idx});
			//subQs.appendWhere(new SearchCondition(pjtClass, "workingCopy", SearchCondition.IS_FALSE, Boolean.getBoolean("false") ),new int[] {sub_idx});
			/*
			if(subQs.getConditionCount() > 0) 
				subQs.appendAnd();
			subQs.appendWhere(new SearchCondition(pjtClass, JELProject.PJT_TYPE, 
												SearchCondition.LIKE, 
												Integer.parseInt("2")), new int[]{sub_idx});
			*/
			//My Project
			//setMyProject(subQs, pjtClass, sub_idx);
			
			WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
			/*
			Department dept = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(wtuser);
	        ArrayList childList = null;
	        if(dept != null) {
	        	childList = e3ps.groupware.company.DepartmentHelper.manager.getChildDeptTree(dept);
	        }
	        
	        if(childList == null) {
	        	childList = new ArrayList();
	        }
	        childList.add(0, dept);
	        */
			if(true){
		       
				if(subQs.getConditionCount() > 0)
					subQs.appendAnd();
				subQs.appendOpenParen();//1
				
				//Project 구성원/ View권한(사용자) start.
				Class linkClass = ProjectMemberLink.class;
				int idx_linkClass = subQs.addClassList(linkClass, false);
				
				ClassAttribute ca0 = null;
		        ClassAttribute ca1 = null;
		        SearchCondition sc0 = null;
		        
		        subQs.appendOpenParen();//2
		        ca0 = new ClassAttribute(pjtClass, "thePersistInfo.theObjectIdentifier.id");
		        ca1 = new ClassAttribute(linkClass, "roleAObjectRef.key.id");
		        sc0 = new SearchCondition(ca0, "=", ca1);
		        sc0.setFromIndicies(new int[]{sub_idx, idx_linkClass}, 0);
		        sc0.setOuterJoin(0);
		        subQs.appendWhere(sc0, new int[]{sub_idx, idx_linkClass});
		        subQs.appendAnd();
		        subQs.appendWhere(new SearchCondition(linkClass, "roleBObjectRef.key.id", 
		        					SearchCondition.EQUAL, 
		        					wtuser.getPersistInfo().getObjectIdentifier().getId()), 
		        				new int[] {idx_linkClass} );
		        subQs.appendCloseParen();//2
		        
		        /*
		        subQs.appendOr();
				
				//Project View권한(부서) start.
		        subQs.appendOpenParen();//3
		        Class deptLinkClass = ProjectViewDepartmentLink.class;
		        int idx_deptLink = subQs.addClassList(deptLinkClass, false);
		        ca0 = new ClassAttribute(pjtClass, "thePersistInfo.theObjectIdentifier.id");
		        ca1 = new ClassAttribute(deptLinkClass, "roleAObjectRef.key.id");
		        sc0 = new SearchCondition(ca0, "=", ca1);
		        sc0.setFromIndicies(new int[]{sub_idx, idx_deptLink}, 0);
		        sc0.setOuterJoin(0);
		        subQs.appendWhere(sc0, new int[]{sub_idx, idx_deptLink});
		        
		        subQs.appendAnd();
		        
		        subQs.appendOpenParen();//4
		        for(int i = 0; i < childList.size(); i++) {
		        	if(i > 0)
		        		subQs.appendOr();
		        	
		        	Department s = (Department)childList.get(i);
			        sc0 = new SearchCondition(deptLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL,
			        						s.getPersistInfo().getObjectIdentifier().getId());
			        subQs.appendWhere(sc0, new int[]{idx_deptLink});
		        }
		        subQs.appendCloseParen();//4
		       
		        subQs.appendCloseParen();//3
		        //Project View권한(부서) end.
		        */
		        subQs.appendCloseParen();//1
		        
	        }
			//State Project
			//setStateProject(subQs, pjtClass, sub_idx);
			
			ClassAttribute ca = new ClassAttribute(pjtClass, "thePersistInfo.modifyStamp");
			ca.setColumnAlias("wtsort" + String.valueOf(0));
			subQs.appendSelect(ca, new int[]{sub_idx}, false);
			OrderBy orderby = new OrderBy(ca, true, null);
			subQs.appendOrderBy(orderby, new int[]{sub_idx});	
			
			/*
            if(subQs == null)
				Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> sub query is null");
			else
				Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> sub query : \n" + subQs.toString());
			*/
			
			/*
            StatementBuilder statementbuilder = subQs.getStatementBuilder();
            if(statementbuilder == null)
            	Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> StatementSpec is null");
            else
            	Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> StatementSpec : " + statementbuilder.getClass().getName());
            */
            //ResultBuilder resultbuilder = subQs.getResultBuilder();
            //String as[] = statementbuilder.buildSQL(resultbuilder, null, true, false);
            
            
			SubSelectExpression subfrom =  new SubSelectExpression(subQs);
            subfrom.setFromAlias(new String[]{"PJT0"}, 0);
            /*
            StatementSpec ss = subfrom.getSubSelect();
            if(ss == null)
            	Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> StatementSpec is null");
			else
				Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> StatementSpec : \n" + ss.getStatementBuilder().SQL_STATEMENTS);
            	
            
            if(subfrom == null)
				Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> SubSelectExpression is null");
			else
				Kogger.debug(getClass(), "@@@@@@@@@@>>>>>> SubSelectExpression : \n" + subfrom.toString());
            
            */
            
            QuerySpec main = new QuerySpec();
            main.setAdvancedQueryEnabled(true);
            
            int main_idx = main.appendFrom(subfrom);
            String main_alias = main.getFromClause().getAliasAt(main_idx);
            
            KeywordExpression col = new KeywordExpression(main_alias + "." + "pjtNo");
            col.setFromAlias(new String[]{main.getFromClause().getAliasAt(main_idx)}, 0);
            main.appendSelect(col,false);
            
            col = new KeywordExpression(main_alias + "." + "pjtName");
            col.setFromAlias(new String[]{main.getFromClause().getAliasAt(main_idx)}, 0);
            main.appendSelect(col,false);
            
            col = new KeywordExpression(main_alias + "." + "pjtOid");
            col.setFromAlias(new String[]{main.getFromClause().getAliasAt(main_idx)}, 0);
            main.appendSelect(col,false);
            
            KeywordExpression rownum = KeywordExpression.newKeywordExpression(KeywordExpression.ROWNUM);
            main.appendWhere(new SearchCondition(rownum, ">=", new ConstantExpression(new Integer(begin))));
            main.appendAnd();
            main.appendWhere(new SearchCondition(rownum, "<", new ConstantExpression(new Integer(begin+size))));
            
            qr = PersistenceServerHelper.manager.query(main);

            /*
            String pno = "";//project number
            String pname = "";//project name
            String poid = "";//project oid
           
            BigDecimal bd = null;
            Object obj[] = null;
            while(qr.hasMoreElements()) {
            	obj = (Object[])qr.nextElement();
            	pno = (String)obj[0];
            	pname = (String)obj[1];
            	poid = (String)obj[2];
            	
            	System.out.print(pno + '\t');
            	System.out.print(pname + '\t');
            	Kogger.debug(getClass(), poid);
            }
            */
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
		return qr;
	}
	
	private static void setStateProject(QuerySpec subQs, Class pjtClass, int sub_idx) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{QuerySpec.class, Class.class, int.class};
			Object args[] = new Object[]{subQs,  pjtClass, new Integer(sub_idx)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"setStateProject",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return;
		}
		
//		try {
//			//프로젝트 상태 Field
//			if(subQs.getConditionCount() > 0) subQs.appendAnd();
//			subQs.appendOpenParen();
//			SearchCondition where = new SearchCondition(pjtClass, JELProject.PJT_STATE, SearchCondition.NOT_LIKE, ProjectStateFlag.PROJECT_STATE_READY);
//			subQs.appendWhere(where, new int[]{sub_idx});
//			subQs.appendCloseParen();
//		}
//		catch(Exception e) {
//			Kogger.error(getClass(), e);
//		}
	}

	public static void setMyProject(QuerySpec spec, Class target, int index) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{QuerySpec.class, Class.class, int.class};
			Object args[] = new Object[]{spec,  target, new Integer(index)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"setMyProject",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return;
		}
		
		try {
			if(spec.getConditionCount() > 0)
				spec.appendAnd();
			
			WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
			
			Class memberClass = ProjectMemberLink.class;
			int member_idx = spec.appendClassList(memberClass, false);
			
			ClassAttribute ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
			ClassAttribute ca2 = new ClassAttribute(memberClass, "roleAObjectRef.key.id");
            SearchCondition sc = new SearchCondition(ca1, "=", ca2);
            sc.setFromIndicies(new int[]{index, member_idx}, 0);
            sc.setOuterJoin(0);
            spec.appendWhere(sc, new int[]{index, member_idx});
            
            spec.appendAnd();
            sc = new SearchCondition(memberClass, 
            			"roleBObjectRef.key.id", 
            			SearchCondition.EQUAL, 
            			wtuser.getPersistInfo().getObjectIdentifier().getId());
            spec.appendWhere(sc , new int[]{member_idx});
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
	}
	
	
	public static void setLatestProjectSpec(QuerySpec spec, Class target, int index) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{QuerySpec.class, Class.class, int.class};
			Object args[] = new Object[]{spec, target, new Integer(index)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"setLatestProjectSpec",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return;
		}
		
		try {
			
			
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
	}
	
	public static QueryResult find(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"find",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			qr = PersistenceHelper.manager.find(getJELPjtQuery(map));
		}
		catch(Exception e){
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
		return qr;
	}
	
	public static PagingQueryResult openPagingSession(HashMap map, int start, int size) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class, int.class, int.class};
			Object args[] = new Object[]{map, new Integer(start), new Integer(size)};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"openPagingSession",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			QuerySpec qs = getJELPjtQuery(map);			
			result = openPagingSession(qs, start, size);			
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
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
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			result = PagingSessionHelper.openPagingSession(start, size, spec);
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
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
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (PagingQueryResult)obj;
		}
		PagingQueryResult result = null;
		try {
			result = PagingSessionHelper.fetchPagingSession(start, size, sessionId);
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
		return result;
	}
	
	public static QuerySpec getJELPjtQuery(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getJELPjtQuery",
						"e3ps.project.beans.JELProjectSearchHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(E3PSProjectSearchHelper.class, e);
				throw new WTException(e);
			}
			return (QuerySpec)obj;
		}
		QuerySpec qs = null;
		try {
			String cmd = (String)map.get("cmd");
			String pjtNo = (String)map.get("pjtNo");									//pjtNo(프로젝트 NO)
			String pjtName = (String)map.get("pjtName");								//pjtName(장비상세 명)
			String pjtDesc = (String)map.get("pjtDesc");								//pjtDesc(설명)
			String pjtType = (String)map.get("pjtType");								//pjtType(프로젝트종류)
			
			//JELProject Object Attribute
			String pjtQuantity = (String)map.get("pjtQuantity");						//pjtQuantity(수량)
			String acceptanceStartDate = (String)map.get("acceptanceStartDate");		//acceptanceStartDate(수주일자 - 시작)
			String acceptanceEndDate = (String)map.get("acceptanceEndDate");			//acceptanceEndDate(수주일자 - 끝)
			String deliveredStartDate = (String)map.get("deliveredStartDate");			//deliveredStartDate(납품일자 - 시작)
			String deliveredEndDate = (String)map.get("deliveredEndDate");				//deliveredEndDate(납품일자 - 끝)
			String pjtState = (String)map.get("pjtState");								//pjtState(상태)
			
			//ExtendScheduleData Object Attribute
			String pjtDuration = (String)map.get("pjtDuration");						//pjtDuration(총 기간)
			String planStartStartDate = (String)map.get("planStartStartDate");			//planStartStartDate(계획 시작일자 - 시작)
			String planStartEndDate = (String)map.get("planStartEndDate");				//planStartEndDate(계획 시작일자 - 끝)
			String planEndStartDate = (String)map.get("planEndStartDate");				//planEndStartDate(계획 종료일자 - 시작)
			String planEndEndDate = (String)map.get("planEndEndDate");					//planEndEndDate(계획 종료일자 - 끝)
			
			//Link Object Attribute
			String pjtItem = (String)map.get("pjtItem");								//pjtItem(ITEM 명)
			String pjtSubItem = (String)map.get("pjtSubItem");							//pjtSubItem(NAME 명)
			String pjtSite = (String)map.get("pjtSite");								//pjtSite(SITE)
			String pjtFab = (String)map.get("pjtFab");									//pjtFab(FAB)
			String pjtCustomer = (String)map.get("pjtCustomer");
			
			if(cmd == null)
				cmd = "";
			
			Class target = E3PSProject.class;
			
			qs = new QuerySpec();
			qs.setAdvancedQueryEnabled(true);
			int idx_target = qs.appendClassList(target, true);
			
			SearchCondition sc = null;
			//프로젝트 NO Field
//			if(pjtNo != null && (pjtNo.trim()).length() > 0) {
//				sc = new SearchCondition(target, JELProject.PJT_NO, SearchCondition.LIKE, pjtNo.trim()+"%");
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
//			
//			//장비(상세)명 Field
//			if(pjtName != null && (pjtName.trim()).length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				sc = new SearchCondition(target, JELProject.PJT_NAME, SearchCondition.LIKE, pjtName.trim()+"%");
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
//			
//			//설명 Field
//			if(pjtDesc != null && (pjtDesc.trim()).length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				sc = new SearchCondition(target, JELProject.PJT_DESC, SearchCondition.LIKE, pjtDesc.trim()+"%");
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
//			
//			//프로젝트 종류 Field(0: 영업수주, 1: WET, 2: GAS, 3: 개발)
//			if(pjtType != null && (pjtType.trim()).length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				sc = new SearchCondition(target, JELProject.PJT_TYPE, SearchCondition.EQUAL, Integer.parseInt(pjtType.trim()));
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
			
			//수량 Field
//			if(pjtQuantity != null && (pjtQuantity.trim()).length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				sc = new SearchCondition(target, JELProject.QUANTITY, SearchCondition.EQUAL, pjtQuantity.trim());
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
			
			//수주일자 - 시작 Field
			/*if(acceptanceStartDate != null && (acceptanceStartDate.trim()).length() > 0) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				sc = new SearchCondition(target, JELProject.ACCEPTANCE_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(acceptanceStartDate));
				qs.appendWhere(sc , new int[]{idx_target});
			}*/
			
			//수주일자 - 끝 Field
			/*if(acceptanceEndDate != null && (acceptanceEndDate.trim()).length() > 0) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				sc = new SearchCondition(target, JELProject.ACCEPTANCE_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(acceptanceEndDate));
				qs.appendWhere(sc , new int[]{idx_target});
			}*/
			
			//납품일자 - 시작 Field
			/*if(deliveredStartDate != null && (deliveredStartDate.trim()).length() > 0) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				sc = new SearchCondition(target, JELProject.DELIVERED_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(deliveredStartDate));
				qs.appendWhere(sc , new int[]{idx_target});
			}*/
			
			//납품일자 - 끝 Field
			/*if(deliveredEndDate != null && (deliveredEndDate.trim()).length() > 0) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				sc = new SearchCondition(target, JELProject.DELIVERED_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(deliveredEndDate));
				qs.appendWhere(sc , new int[]{idx_target});
			}*/
			
			
			//프로젝트 상태 Field(ProjectStateHelper 참고)
//			if(pjtState != null && (pjtState.trim()).length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				sc = new SearchCondition(target, JELProject.PJT_STATE, SearchCondition.EQUAL, Integer.parseInt(pjtState.trim()));
//				qs.appendWhere(sc , new int[]{idx_target});
//			}
			
			if( (pjtDuration != null && (pjtDuration.trim()).length() > 0)
					|| (planStartStartDate != null && (planStartStartDate.trim()).length() > 0)
					|| (planStartEndDate != null && (planStartEndDate.trim()).length() > 0)
					|| (planEndStartDate != null && (planEndStartDate.trim()).length() > 0)
					|| (planEndEndDate != null && (planEndEndDate.trim()).length() > 0) ) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				Class schedule = ExtendScheduleData.class;
				int idx_schedule = qs.appendClassList(schedule, false);
				
				ClassAttribute ca1 = null;
	            ClassAttribute ca2 = null;
	            
	            ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
				ca2 = new ClassAttribute(schedule, "thePersistInfo.theObjectIdentifier.id");
				sc = new SearchCondition(ca1, "=", ca2);
				sc.setFromIndicies(new int[]{idx_target, idx_schedule}, 0);
				sc.setOuterJoin(0);
				qs.appendWhere(sc, new int[]{idx_target, idx_schedule});
				
				//총 기간 Field
				if(pjtDuration != null && (pjtDuration.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
			        sc = new SearchCondition(schedule, ExtendScheduleData.DURATION, SearchCondition.EQUAL, Integer.parseInt(pjtDuration));
					qs.appendWhere(sc, new int[]{idx_schedule});
				}
				
				//계획 시작일자 - 시작 Field
				if(planStartStartDate != null && (planStartStartDate.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planStartStartDate));
					qs.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 시작일자 - 끝 Field
				if(planStartEndDate != null && (planStartEndDate.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(planStartEndDate));
					qs.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 종료일자 - 시작 Field
				if(planEndStartDate != null && (planEndStartDate.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil.convertStartDate(planEndStartDate));
					qs.appendWhere(sc , new int[]{idx_schedule});
				}
				
				//계획 종료일자 - 끝 Field
				if(planEndEndDate != null && (planEndEndDate.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, DateUtil.convertStartDate(planEndEndDate));
					qs.appendWhere(sc , new int[]{idx_schedule});
				}
			}
			
			if( (pjtItem != null && (pjtItem.trim()).length() > 0)
					|| (pjtSubItem != null && (pjtSubItem.trim()).length() > 0)
					|| (pjtSite != null && (pjtSite.trim()).length() > 0)
					|| (pjtFab != null && (pjtFab.trim()).length() > 0)
					|| (pjtCustomer != null && (pjtCustomer.trim()).length() > 0) ) {
				
				
				int idx_NumCode = qs.appendClassList(NumberCode.class, false);
				
				ClassAttribute ca1 = null;
	            ClassAttribute ca2 = null;
	            
				//ITEM명 Field
				if(pjtItem != null && (pjtItem.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					qs.appendOpenParen();
					qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "ITEM"), new int[] { idx_NumCode });
					
		            qs.appendAnd();
		            qs.appendWhere(new SearchCondition(NumberCode.class, "code", "=", pjtItem.trim()), new int[] { idx_NumCode });
		            
		            qs.appendAnd();
		            ca1 = new ClassAttribute(target, "itemReference.key.id");
		            ca2 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
		            sc = new SearchCondition(ca1, "=", ca2);
		            sc.setFromIndicies(new int[]{idx_target, idx_NumCode}, 0);
		            sc.setOuterJoin(0);
		            qs.appendWhere(sc, new int[]{idx_target, idx_NumCode});
					qs.appendCloseParen();
				}
				
				//NAME명 Field
				if(pjtSubItem != null && (pjtSubItem.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					qs.appendOpenParen();
					qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "SUBITEM"), new int[] { idx_NumCode });
					
		            qs.appendAnd();
		            qs.appendWhere(new SearchCondition(NumberCode.class, "code", "=", pjtSubItem.trim()), new int[] { idx_NumCode });
		            
		            qs.appendAnd();
		            ca1 = new ClassAttribute(target, "nameReference.key.id");
		            ca2 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
		            sc = new SearchCondition(ca1, "=", ca2);
		            sc.setFromIndicies(new int[]{idx_target, idx_NumCode}, 0);
		            sc.setOuterJoin(0);
		            qs.appendWhere(sc, new int[]{idx_target, idx_NumCode});
					qs.appendCloseParen();
				}
				
				//SITE Field
				if(pjtSite != null && (pjtSite.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					qs.appendOpenParen();
					qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "SITE"), new int[] { idx_NumCode });
					
		            qs.appendAnd();
		            qs.appendWhere(new SearchCondition(NumberCode.class, "code", "=", pjtSite.trim()), new int[] { idx_NumCode });
		            
		            qs.appendAnd();
		            ca1 = new ClassAttribute(target, "siteReference.key.id");
		            ca2 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
		            sc = new SearchCondition(ca1, "=", ca2);
		            sc.setFromIndicies(new int[]{idx_target, idx_NumCode}, 0);
		            sc.setOuterJoin(0);
		            qs.appendWhere(sc, new int[]{idx_target, idx_NumCode});
					qs.appendCloseParen();
				}
				
				//FAB Field
				if(pjtFab != null && (pjtFab.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					qs.appendOpenParen();
					qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "FAB"), new int[] { idx_NumCode });
					
		            qs.appendAnd();
		            qs.appendWhere(new SearchCondition(NumberCode.class, "code", "=", pjtFab.trim()), new int[] { idx_NumCode });
		            
		            qs.appendAnd();
		            ca1 = new ClassAttribute(target, "fabReference.key.id");
		            ca2 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
		            sc = new SearchCondition(ca1, "=", ca2);
		            sc.setFromIndicies(new int[]{idx_target, idx_NumCode}, 0);
		            sc.setOuterJoin(0);
		            qs.appendWhere(sc, new int[]{idx_target, idx_NumCode});
					qs.appendCloseParen();
				}
				
				//고객사 Field
				if(pjtCustomer != null && (pjtCustomer.trim()).length() > 0) {
					if(qs.getConditionCount() > 0)
						qs.appendAnd();
					
					qs.appendOpenParen();
					qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", "CUSTOMER"), new int[] { idx_NumCode });
					
		            qs.appendAnd();
		            qs.appendWhere(new SearchCondition(NumberCode.class, "code", "=", pjtCustomer.trim()), new int[] { idx_NumCode });
		            
		            qs.appendAnd();
		            ca1 = new ClassAttribute(target, "fabReference.key.id");
		            ca2 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");
		            sc = new SearchCondition(ca1, "=", ca2);
		            sc.setFromIndicies(new int[]{idx_target, idx_NumCode}, 0);
		            sc.setOuterJoin(0);
		            qs.appendWhere(sc, new int[]{idx_target, idx_NumCode});
					qs.appendCloseParen();
				}
			}
			
			
			if(cmd.equalsIgnoreCase("myworkProject")) {
				if(qs.getConditionCount() > 0)
					qs.appendAnd();
				
				WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
				
				Class linkClass = ProjectMemberLink.class;
				int idx_linkClass = qs.appendClassList(linkClass, false);
				
				ClassAttribute ca1 = null;
	            ClassAttribute ca2 = null;
	            
	            ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
	            ca2 = new ClassAttribute(linkClass, "roleAObjectRef.key.id");
	            sc = new SearchCondition(ca1, "=", ca2);
	            sc.setFromIndicies(new int[]{idx_target, idx_linkClass}, 0);
	            sc.setOuterJoin(0);
	            qs.appendWhere(sc, new int[]{idx_target, idx_linkClass});
	            
	            qs.appendAnd();
	            sc = new SearchCondition(linkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, wtuser.getPersistInfo().getObjectIdentifier().getId());
				qs.appendWhere(sc , new int[]{idx_linkClass});
			}
			
			//sub query begin ...
			//select max(p.pjthistory) from kctproject p where p.pjtno = pjt.pjtno
			QuerySpec subQs = new QuerySpec();
			subQs.getFromClause().setAliasPrefix("B");
			int subIndex = subQs.addClassList(target, false);
			
//			ClassAttribute pjthistory = new ClassAttribute(target, JELProject.PJT_HISTORY);
//			SQLFunction maxFunction = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, pjthistory);
//			subQs.appendSelect(maxFunction, new int[]{subIndex}, false);
//			
//			//where p.pjtno = pjt.pjtno
//			TableExpression[] tables = new TableExpression[2];
//			String[] aliases = new String[2];
//			//select
//			tables[0] = qs.getFromClause().getTableExpressionAt(idx_target);
//			aliases[0] = qs.getFromClause().getAliasAt(idx_target);
//			//subselect
//			tables[1] = subQs.getFromClause().getTableExpressionAt(subIndex);
//			aliases[1] = subQs.getFromClause().getAliasAt(subIndex);
//			SearchCondition correlatedJoin = new SearchCondition(target, JELProject.PJT_NO,
//					target,JELProject.PJT_NO);
//			subQs.appendWhere(correlatedJoin, tables, aliases);
//			//end ...
//			
//			if(qs.getConditionCount() > 0)
//				qs.appendAnd();
//			
//			ClassAttribute history = new ClassAttribute(target, JELProject.PJT_HISTORY);
//			sc = new SearchCondition(history, SearchCondition.EQUAL, new SubSelectExpression(subQs));
//			qs.appendWhere(sc, new int[] { idx_target });
			
			ClassAttribute ca = new ClassAttribute(target, "thePersistInfo.createStamp");
			ca.setColumnAlias("wtsort" + String.valueOf(0));
			qs.appendSelect(ca, new int[]{idx_target}, false);
			OrderBy orderby = new OrderBy(ca, false, null);
			qs.appendOrderBy(orderby, new int[]{idx_target});		
			
			Kogger.debug(E3PSProjectSearchHelper.class, "JELProjectSearchHelper[QuerySpec]>>> \n"+qs);
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
		return qs;
	}
	
	public static ArrayList getProjectCode(HashMap map) throws WTException {
		ArrayList result = new ArrayList();
		try {
			String code = (String)map.get("code");
			String name = (String)map.get("name");
			
			if(code == null) {
				code = "";
			}
			
			if(name == null) {
				name = "";
			}
			
			Class target = E3PSProject.class;
			
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(target, false);
			
//			ClassAttribute pjtno = new ClassAttribute(target, JELProject.PJT_NO);
//			pjtno.setFromAlias(new String[]{qs.getFromClause().getAliasAt(idx)}, 0);
//			pjtno.setColumnAlias("pjtNo");
//			qs.appendSelect(pjtno, new int[]{idx}, false);
//			
//			ClassAttribute pjtname = new ClassAttribute(target, JELProject.PJT_NAME);
//			pjtname.setFromAlias(new String[]{qs.getFromClause().getAliasAt(idx)}, 0);
//			pjtname.setColumnAlias("pjtName");
//			qs.appendSelect(pjtname, new int[]{idx}, false);
//			
//			SearchCondition sc = null;
//			sc = new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, true);
//			qs.appendWhere(sc, new int[]{idx});
//
//			qs.appendAnd();
//			sc = new SearchCondition(target, JELProject.CHECK_OUT_STATE, SearchCondition.NOT_EQUAL, "c/o");
//			qs.appendWhere(sc, new int[]{idx});

//			if(name.length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//
//				sc = new SearchCondition(target, "master>pjtName", SearchCondition.LIKE, name + "%");
//				qs.appendWhere(sc, new int[]{idx});
//			}
//
//			if(code.length() > 0) {
//				if(qs.getConditionCount() > 0)
//					qs.appendAnd();
//				
//				if( !(code.startsWith("J") ||
//						code.startsWith("R"))) {
//					qs.appendOpenParen();
//					
//					sc = new SearchCondition(target, "master>pjtNo", SearchCondition.LIKE, "J-" + code + "%");
//					qs.appendWhere(sc, new int[]{idx});
//					
//					qs.appendOr();
//					sc = new SearchCondition(target, "master>pjtNo", SearchCondition.LIKE, "R-"  + code + "%");
//					qs.appendWhere(sc, new int[]{idx});
//					qs.appendCloseParen();
//				}
//				else {
//					sc = new SearchCondition(target, "master>pjtNo", SearchCondition.LIKE, code + "%");
//					qs.appendWhere(sc, new int[]{idx});
//				}
//			}

			ClassAttribute ca = new ClassAttribute(target, "master>pjtNo");
			ca.setColumnAlias("wtsort"+String.valueOf(0));
			qs.appendSelect(ca, new int[]{idx}, false);
			OrderBy orderby = new OrderBy(ca, false, null);
			qs.appendOrderBy(orderby, new int[]{idx});
			
			QueryResult qr = PersistenceHelper.manager.find(qs);
			
			String r_code = "";
			String r_name = "";
			String strArr[] = null;
			
			Object obj[] = null;
			while(qr.hasMoreElements()) {
				obj = (Object[])qr.nextElement();
				r_code = (String)obj[0];
				r_name = (String)obj[1];
				
				strArr = new String[2];
				strArr[0] = r_code;
				strArr[1] = r_name;
				result.add(strArr);
			}
		}
		catch(Exception e) {
			Kogger.error(E3PSProjectSearchHelper.class, e);
		}
		return result;
	}
	
	public static QueryResult getProductProject(String partNo)throws Exception{
		
		QuerySpec qs = new QuerySpec();
		int index1 = qs.addClassList(ProductProject.class, true);
		
        qs.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") ),new int[] {index1});
		
		if(qs.getConditionCount() > 0) 
			qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {index1});
        
		if(qs.getConditionCount() > 0) 
			qs.appendAnd();
		qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.PART_NO, SearchCondition.EQUAL, partNo),new int[] {index1});

        SearchUtil.setOrderBy(qs, ProductProject.class, index1, "thePersistInfo.modifyStamp", "modifyStamp", true);
        
        return PersistenceHelper.manager.find(qs);
	}

}
