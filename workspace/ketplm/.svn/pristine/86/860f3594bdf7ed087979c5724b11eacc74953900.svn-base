package e3ps.access.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.util.WTException;
import wt.vc.Iterated;
import e3ps.access.AccessAuthority;
import e3ps.access.AccessDeptLink;
import e3ps.access.AccessUserLink;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.WTUserPeopleLink;
import ext.ket.shared.log.Kogger;

public class AccessControlHelper implements RemoteAccess, Serializable {
	final static boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	public static boolean saveAccessAuthority(HashMap map)throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"saveAccessAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		
		Transaction trx = new Transaction();
		try {
			trx.start();
			
			Persistable persistable = (Persistable)map.get("object");
			if(persistable == null) {
				return false;
			}
			
			String className = persistable.getClass().getName();
			long objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			boolean isVR = false;			
			if(persistable instanceof Iterated) {
				Iterated iter = (Iterated)persistable;
				objectID = iter.getIterationInfo().getBranchId();
				isVR = true;
			}
			
			String accessName = (String)map.get("accessName");
			if(accessName == null) {
				accessName = "OR:";
				if(isVR) {
					accessName = "VR:";
				}
				accessName += className;
				accessName += String.valueOf(objectID);
			}
			
			//기존에 생성된 AccessAuthority 삭제.
			delete(className, objectID);
			
			AccessAuthority authority = AccessAuthority.newAccessAuthority();
			authority.setAccessName(accessName);
			authority.setObjectID(objectID);
			authority.setObjClassName(className);
			authority.setIsVR(isVR);
			authority = (AccessAuthority)PersistenceHelper.manager.save(authority);
			
			ArrayList users = (ArrayList)map.get("user");
			ArrayList depts = (ArrayList)map.get("dept");
			boolean isFull = map.get("isFull")==null? false:((Boolean)map.get("isFull")).booleanValue();
			boolean isWrite = map.get("isWrite")==null? false:((Boolean)map.get("isWrite")).booleanValue();
			boolean isModify = map.get("isModify")==null? false:((Boolean)map.get("isModify")).booleanValue();
			boolean isDelete = map.get("isDelete")==null? false:((Boolean)map.get("isDelete")).booleanValue();
			boolean isRead = map.get("isRead")==null? false:((Boolean)map.get("isRead")).booleanValue();
			
			if(users != null && users.size() > 0) {
				WTPrincipal principal = null;
				for(int i = 0; i < users.size(); i++) {
					principal = (WTPrincipal)users.get(i);
					addAuthority(authority, principal, isFull, isWrite, isModify, isDelete, isRead);
				}
			}
			
			if(depts != null && depts.size() > 0) {
				Department dept = null;
				for(int i = 0; i < depts.size(); i++) {
					dept = (Department)depts.get(i);
					addAuthority(authority, dept, isFull, isWrite, isModify, isDelete, isRead);
				}
			}
			
			trx.commit();
			trx = null;
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			trx.rollback();
			return false;
		}
		finally {
			if(trx != null) {
				trx = null;
			}
		}
		
		return true;
	}
	
	public static boolean deleteAccessAuthority(Persistable persistable)throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Persistable.class};
			Object args[] = new Object[]{persistable};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"deleteAccessAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		
		Transaction trx = new Transaction();
		try {
			trx.start();
			String className = persistable.getClass().getName();
			long objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			if(persistable instanceof Iterated) {
				Iterated iter = (Iterated)persistable;
				objectID = iter.getIterationInfo().getBranchId();
			}
			
			delete(className, objectID);			
			trx.commit();
			trx = null;
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			trx.rollback();
			return false;
		}
		finally {
			if(trx != null) {
				trx = null;
			}
		}
		return true;
	}
	
	public static boolean delete(String className, long objectID)throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{String.class, long.class};
			Object args[] = new Object[]{className, new Long(objectID)};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"delete",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		
		try {
			AccessAuthority auth = null;
			QueryResult result = getAccessAuthority(className, objectID);
			
			Object obj[] = null;
			while(result.hasMoreElements()) {
				obj = (Object[])result.nextElement();
				auth = (AccessAuthority)obj[0];
				PersistenceHelper.manager.delete(auth);
			}
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return true;
	}
	
	
	public static AccessUserLink addAuthority(AccessAuthority access, WTPrincipal principal) throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, WTPrincipal.class};
			Object args[] = new Object[]{access, principal};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"addAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return (AccessUserLink)obj;
		}
		
		AccessUserLink link = null;
		try {
			link = addAuthority(access, principal, true, true, true, true, true);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return link;
	}
	
	public static AccessUserLink addAuthority(AccessAuthority access, WTPrincipal principal,
			boolean isFull, boolean isWrite, boolean isModify, boolean isDelete, boolean isRead)
		throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, WTPrincipal.class, 
										boolean.class, boolean.class, boolean.class, 
										boolean.class, boolean.class};
			Object args[] = new Object[]{access, principal, new Boolean(isFull), 
										new Boolean(isWrite), new Boolean(isModify),
										new Boolean(isDelete), new Boolean(isRead)};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"addAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return (AccessUserLink)obj;
		}
		
		AccessUserLink link = null;
		try {
			if(isFull) {
				isWrite = isFull;
				isModify = isFull;
				isDelete = isFull;
				isRead = isFull;
			}
			
			QueryResult qr = getAccessUserLink(access, principal);
			if(qr!= null && qr.hasMoreElements()) {
				Object obj[] = (Object[])qr.nextElement();
				link = (AccessUserLink)obj[0];
			}
			
			if(link == null) {
				link = AccessUserLink.newAccessUserLink(access, principal);
			}

			link.setIsFull(isFull);
			link.setIsWrite(isWrite);
			link.setIsModify(isModify);
			link.setIsDelete(isDelete);
			link.setIsRead(isRead);			
			link = (AccessUserLink)PersistenceHelper.manager.save(link);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return link;
	}
	
	
	public static AccessDeptLink addAuthority(AccessAuthority access, Department department)
		throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, Department.class};
			Object args[] = new Object[]{access, department};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"addAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return (AccessDeptLink)obj;
		}
		
		AccessDeptLink link = null;
		try {
			link = addAuthority(access, department, true, true, true, true, true);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return link;
	}
	
	public static AccessDeptLink addAuthority(AccessAuthority access, Department department,
			boolean isFull, boolean isWrite, boolean isModify, boolean isDelete, boolean isRead)
		throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, Department.class, 
											boolean.class, boolean.class, boolean.class, 
											boolean.class, boolean.class};
			Object args[] = new Object[]{access, department, new Boolean(isFull), 
											new Boolean(isWrite), new Boolean(isModify),
											new Boolean(isDelete), new Boolean(isRead)};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"addAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return (AccessDeptLink)obj;
		}
		
		AccessDeptLink link = null;
		try {
			if(isFull) {
				isWrite = isFull;
				isModify = isFull;
				isDelete = isFull;
				isRead = isFull;
			}
			
			
			QueryResult qr = getAccessDeptLink(access, department);
			if(qr!= null && qr.hasMoreElements()) {
				Object obj[] = (Object[])qr.nextElement();
				link = (AccessDeptLink)obj[0];
			}
			
			if(link == null) {
				link = AccessDeptLink.newAccessDeptLink(access, department);
			}
			
			link.setIsFull(isFull);
			link.setIsWrite(isWrite);
			link.setIsModify(isModify);
			link.setIsDelete(isDelete);
			link.setIsRead(isRead);
			link = (AccessDeptLink)PersistenceHelper.manager.save(link);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return link;
	}
	
	public static boolean isAccessAuthority(Persistable persistable, WTUser wtuser) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Persistable.class, WTUser.class};
			Object args[] = new Object[]{persistable, wtuser};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"isAccessAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		try {
			String className = persistable.getClass().getName();
			long objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			if(persistable instanceof Iterated) {
				Iterated iter = (Iterated)persistable;
				objectID = iter.getIterationInfo().getBranchId();
			}
			
			//WTGroup 가져오기...
			ArrayList wtgroups = new ArrayList();
			Enumeration en = wtuser.parentGroupObjects();
			while(en.hasMoreElements()) {
				wtgroups.add((WTGroup) en.nextElement());
			}
			
			//Department 가져오기
			Department department = null;
			QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", WTUserPeopleLink.class);
            if (qr.hasMoreElements()) {
                People people = (People) qr.nextElement();
                department = people.getDepartment();
            }
            
            ArrayList deptList = new ArrayList();
            if(department != null) {
            	//하위 부서 담기 작업.
            	//DepartmentHelper.manager.getChildDeptTree(department);
            	deptList.add(department);
            }
			
            
            SearchCondition sc = null;
			ClassAttribute ca0 = null;
			ClassAttribute ca1 = null;
			
            //WTUser와 WTGroup
			QuerySpec userQs = new QuerySpec();			
			int auth_idx = userQs.appendClassList(AccessAuthority.class, false);
			int user_idx = userQs.appendClassList(AccessUserLink.class, false);
			
			ca0 = new ClassAttribute(AccessUserLink.class, "thePersistInfo.theObjectIdentifier.id");
			ca0.setColumnAlias("ida2a2");
			userQs.appendSelect(ca0, new int[]{user_idx}, false);
			
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJECT_ID,
									SearchCondition.EQUAL, objectID);
			userQs.appendWhere(sc, new int[]{auth_idx});
			
			userQs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJ_CLASS_NAME,
									SearchCondition.EQUAL, className);
			userQs.appendWhere(sc, new int[]{auth_idx});
			
			userQs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessUserLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{auth_idx, user_idx}, 0);
			sc.setOuterJoin(0);
			userQs.appendWhere(sc, new int[]{auth_idx, user_idx});
			
			userQs.appendAnd();
			
			userQs.appendOpenParen();
			sc = new SearchCondition(AccessUserLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL,
										wtuser.getPersistInfo().getObjectIdentifier().getId());
			userQs.appendWhere(sc, new int[]{user_idx});
			
			if(wtgroups.size() > 0) {
				WTGroup wtgroup = null;
				for(int i = 0; i < wtgroups.size(); i++) {
					wtgroup = (WTGroup)wtgroups.get(i);
					
					userQs.appendOr();
					sc = new SearchCondition(AccessUserLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL,
									wtgroup.getPersistInfo().getObjectIdentifier().getId());
					userQs.appendWhere(sc, new int[]{user_idx});
				}
			}			
			userQs.appendCloseParen();
			
			if(deptList.size() == 0) {
				QueryResult userQr = PersistenceHelper.manager.find(userQs);
				if(userQr.hasMoreElements()) {
					return true;
				}
				return false;
			}
			
            //Department
			QuerySpec deptQs = new QuerySpec();
			int auth_idx1 = deptQs.appendClassList(AccessAuthority.class, false);
			int dept_idx = deptQs.appendClassList(AccessDeptLink.class, false);
			
			ca0 = new ClassAttribute(AccessDeptLink.class, "thePersistInfo.theObjectIdentifier.id");
			ca0.setColumnAlias("ida2a2");
			deptQs.appendSelect(ca0, new int[]{dept_idx}, false);
			
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJECT_ID,
									SearchCondition.EQUAL, objectID);
			deptQs.appendWhere(sc, new int[]{auth_idx1});
			
			deptQs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class, 
								AccessAuthority.OBJ_CLASS_NAME,
								SearchCondition.EQUAL, className);
			deptQs.appendWhere(sc, new int[]{auth_idx1});

			deptQs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessDeptLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{auth_idx1, dept_idx}, 0);
			sc.setOuterJoin(0);
			deptQs.appendWhere(sc, new int[]{auth_idx1, dept_idx});
			
			deptQs.appendAnd();
			deptQs.appendOpenParen();
			if(deptList.size() > 0) {
				department = null;
				for(int i = 0; i < deptList.size(); i++) {
					department = (Department)deptList.get(i);
					
					if(i > 0)
						deptQs.appendOr();
					
					sc = new SearchCondition(AccessDeptLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL,
							department.getPersistInfo().getObjectIdentifier().getId());
					deptQs.appendWhere(sc, new int[]{dept_idx});
				}
			}
			deptQs.appendCloseParen();
			
			CompoundQuerySpec compound = new CompoundQuerySpec();
			compound.setAdvancedQueryEnabled(true);
			compound.setSetOperator(SetOperator.UNION_ALL);
			compound.addComponent(userQs);
			compound.addComponent(deptQs);
			QueryResult allQr = PersistenceHelper.manager.find(compound);
			if(allQr.hasMoreElements()) {
				return true;
			}
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			return false;
		}
		return false;
	}
	
	public static boolean isAccessSetUp(Persistable persistable) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Persistable.class};
			Object args[] = new Object[]{persistable};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"isAccessSetUp",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		
		try {
			String className = persistable.getClass().getName();
			long objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			if(persistable instanceof Iterated) {
				Iterated iter = (Iterated)persistable;
				objectID = iter.getIterationInfo().getBranchId();
			}
			
            SearchCondition sc = null;
			ClassAttribute ca0 = null;
			ClassAttribute ca1 = null;
			
            //WTUser와 WTGroup
			QuerySpec userQs = new QuerySpec();			
			int auth_idx = userQs.appendClassList(AccessAuthority.class, false);
			int user_idx = userQs.appendClassList(AccessUserLink.class, false);
			
			ca0 = new ClassAttribute(AccessUserLink.class, "thePersistInfo.theObjectIdentifier.id");
			ca0.setColumnAlias("ida2a2");
			userQs.appendSelect(ca0, new int[]{user_idx}, false);
			
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJECT_ID,
									SearchCondition.EQUAL, objectID);
			userQs.appendWhere(sc, new int[]{auth_idx});
			
			userQs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJ_CLASS_NAME,
									SearchCondition.EQUAL, className);
			userQs.appendWhere(sc, new int[]{auth_idx});
			
			userQs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessUserLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{auth_idx, user_idx}, 0);
			sc.setOuterJoin(0);
			userQs.appendWhere(sc, new int[]{auth_idx, user_idx});
			
			
            //Department
			QuerySpec deptQs = new QuerySpec();
			int auth_idx1 = deptQs.appendClassList(AccessAuthority.class, false);
			int dept_idx = deptQs.appendClassList(AccessDeptLink.class, false);
			
			ca0 = new ClassAttribute(AccessDeptLink.class, "thePersistInfo.theObjectIdentifier.id");
			ca0.setColumnAlias("ida2a2");
			deptQs.appendSelect(ca0, new int[]{dept_idx}, false);
			
			sc = new SearchCondition(AccessAuthority.class, 
									AccessAuthority.OBJECT_ID,
									SearchCondition.EQUAL, objectID);
			deptQs.appendWhere(sc, new int[]{auth_idx1});
			
			deptQs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class, 
								AccessAuthority.OBJ_CLASS_NAME,
								SearchCondition.EQUAL, className);
			deptQs.appendWhere(sc, new int[]{auth_idx1});

			deptQs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessDeptLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{auth_idx1, dept_idx}, 0);
			sc.setOuterJoin(0);
			deptQs.appendWhere(sc, new int[]{auth_idx1, dept_idx});
			
			
			CompoundQuerySpec compound = new CompoundQuerySpec();
			compound.setAdvancedQueryEnabled(true);
			compound.setSetOperator(SetOperator.UNION_ALL);
			compound.addComponent(userQs);
			compound.addComponent(deptQs);
			QueryResult allQr = PersistenceHelper.manager.find(compound);
			if(allQr.hasMoreElements()) {
				return true;
			}
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			return false;
		}
		return false;
	}
	
	public static QueryResult getAccessAuthority(String className, long objectID) throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{String.class, long.class};
			Object args[] = new Object[]{className, new Long(objectID)};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"getAccessAuthority",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			QuerySpec qs = new QuerySpec();			
			int i = qs.addClassList(AccessAuthority.class, true);
			
			SearchCondition sc = null;
			sc = new SearchCondition(AccessAuthority.class,
									AccessAuthority.OBJ_CLASS_NAME,
									SearchCondition.EQUAL,
									className);
			qs.appendWhere(sc, new int[]{i});
			
			qs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class,
								AccessAuthority.OBJECT_ID,
								SearchCondition.EQUAL,
								objectID);
			qs.appendWhere(sc, new int[]{i});
			
			qr = PersistenceHelper.manager.find(qs);
		/*
			Object obj[] = null;
			while(qr.hasMoreElements()) {
				obj = (Object[])qr.nextElement();
			}
		*/
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return qr;
	}
	
	public static QueryResult getAccessUserLink(AccessAuthority access, WTPrincipal principal)
		throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, WTPrincipal.class};
			Object args[] = new Object[]{access, principal};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"getAccessUserLink",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			QuerySpec qs = new QuerySpec();
			
			int i = qs.addClassList(AccessUserLink.class, true);
			
			SearchCondition sc = new SearchCondition(AccessUserLink.class, "roleAObjectRef.key.id",
											SearchCondition.EQUAL, access.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[]{i});
			
			if(principal != null) {
				qs.appendAnd();
				sc = new SearchCondition(AccessUserLink.class, "roleBObjectRef.key.id",
									SearchCondition.EQUAL, principal.getPersistInfo().getObjectIdentifier().getId());
				qs.appendWhere(sc, new int[]{i});
			}
			
			qr = PersistenceHelper.manager.find(qs);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return qr;
	}
	
	public static QueryResult getAccessDeptLink(AccessAuthority access, Department department)
		throws WTException {
		
		if(!SERVER) {
			Class argTypes[] = new Class[]{AccessAuthority.class, Department.class};
			Object args[] = new Object[]{access, department};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"getAccessDeptLink",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			QuerySpec qs = new QuerySpec();
			
			int i = qs.addClassList(AccessDeptLink.class, true);
			
			SearchCondition sc = new SearchCondition(AccessDeptLink.class, "roleAObjectRef.key.id",
											SearchCondition.EQUAL, access.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[]{i});
			
			if(department != null) {
				qs.appendAnd();
				sc = new SearchCondition(AccessDeptLink.class, "roleBObjectRef.key.id",
									SearchCondition.EQUAL, department.getPersistInfo().getObjectIdentifier().getId());
				qs.appendWhere(sc, new int[]{i});
			}
			
			qr = PersistenceHelper.manager.find(qs);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
			throw new WTException(e);
		}
		return qr;
	}
	
	public static QueryResult getAccessUsers(Persistable persistable) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Persistable.class};
			Object args[] = new Object[]{persistable};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"getAccessUsers",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			String className = persistable.getClass().getName();
			long objectID = 0;
			if(persistable instanceof Iterated) {
				objectID = ((Iterated)persistable).getIterationInfo().getBranchId();
			}
			else {
				objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			}
			
			QuerySpec qs = new QuerySpec();
			int idx_auth = qs.appendClassList(AccessAuthority.class, false);
			int idx_link = qs.appendClassList(AccessUserLink.class, false);
			int idx_user = qs.appendClassList(WTPrincipal.class, true);
			
			SearchCondition sc = null;
			ClassAttribute ca0 = null;
			ClassAttribute ca1 = null;
			sc = new SearchCondition(AccessAuthority.class,
									AccessAuthority.OBJ_CLASS_NAME,
									SearchCondition.EQUAL,
									className);
			qs.appendWhere(sc, new int[]{idx_auth});
			
			qs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class,
								AccessAuthority.OBJECT_ID,
								SearchCondition.EQUAL,
								objectID);
			qs.appendWhere(sc, new int[]{idx_auth});
			
			qs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessUserLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{idx_auth, idx_link}, 0);
			sc.setOuterJoin(0);
			qs.appendWhere(sc, new int[]{idx_auth, idx_link});
			
			qs.appendAnd();
			ca0 = new ClassAttribute(AccessUserLink.class, "roleBObjectRef.key.id");
			ca1 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{idx_link, idx_user}, 0);
			sc.setOuterJoin(0);
			qs.appendWhere(sc, new int[]{idx_link, idx_user});
			
			qr = PersistenceHelper.manager.find(qs);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
		}
		return qr;
	}
	
	
	public static QueryResult getAccessDepts(Persistable persistable) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Persistable.class};
			Object args[] = new Object[]{persistable};
			Object obj = null;
			try {
				obj = RemoteMethodServer.getDefault().invoke(
						"getAccessDepts",
						"e3ps.access.service.AccessControlHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(AccessControlHelper.class, e);
				throw new WTException(e);
			}
			
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			String className = persistable.getClass().getName();
			long objectID = 0;
			if(persistable instanceof Iterated) {
				objectID = ((Iterated)persistable).getIterationInfo().getBranchId();
			}
			else {
				objectID = persistable.getPersistInfo().getObjectIdentifier().getId();
			}
			
			QuerySpec qs = new QuerySpec();
			int idx_auth = qs.appendClassList(AccessAuthority.class, false);
			int idx_link = qs.appendClassList(AccessDeptLink.class, false);
			int idx_dept = qs.appendClassList(Department.class, true);
			
			SearchCondition sc = null;
			ClassAttribute ca0 = null;
			ClassAttribute ca1 = null;
			sc = new SearchCondition(AccessAuthority.class,
									AccessAuthority.OBJ_CLASS_NAME,
									SearchCondition.EQUAL,
									className);
			qs.appendWhere(sc, new int[]{idx_auth});
			
			qs.appendAnd();
			sc = new SearchCondition(AccessAuthority.class,
								AccessAuthority.OBJECT_ID,
								SearchCondition.EQUAL,
								objectID);
			qs.appendWhere(sc, new int[]{idx_auth});
			
			qs.appendAnd();
			ca0 = new ClassAttribute(AccessAuthority.class, "thePersistInfo.theObjectIdentifier.id");
			ca1 = new ClassAttribute(AccessDeptLink.class, "roleAObjectRef.key.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{idx_auth, idx_link}, 0);
			sc.setOuterJoin(0);
			qs.appendWhere(sc, new int[]{idx_auth, idx_link});
			
			qs.appendAnd();
			ca0 = new ClassAttribute(AccessDeptLink.class, "roleBObjectRef.key.id");
			ca1 = new ClassAttribute(Department.class, "thePersistInfo.theObjectIdentifier.id");
			sc = new SearchCondition(ca0, "=", ca1);
			sc.setFromIndicies(new int[]{idx_link, idx_dept}, 0);
			sc.setOuterJoin(0);
			qs.appendWhere(sc, new int[]{idx_link, idx_dept});
			
			qr = PersistenceHelper.manager.find(qs);
		}
		catch(Exception e) {
			Kogger.error(AccessControlHelper.class, e);
		}
		return qr;
	}
}
