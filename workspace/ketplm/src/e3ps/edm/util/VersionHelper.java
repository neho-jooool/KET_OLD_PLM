package e3ps.edm.util;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.ConstantExpression;
import wt.query.ExistsExpression;
import wt.query.KeywordExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableExpression;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Mastered;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc.views.View;
import wt.vc.views.ViewManageable;
import wt.vc.views.ViewReference;
import wt.vc.wip.WorkInProgressState;
import wt.vc.wip.Workable;
import ext.ket.shared.log.Kogger;

public class VersionHelper implements wt.method.RemoteAccess, java.io.Serializable {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public static boolean isFirstVersion(Versioned versioned) throws WTException {
		
		if(versioned == null) {
			throw new WTException("Versioned is Null!!!");
		}
		
		Long firstBranchId = null;
		Long branchId0 = Long.valueOf(versioned.getBranchIdentifier());
		
		QueryResult allQr = VersionControlHelper.service.allVersionsOf(versioned.getMaster());
		
		Versioned v = (Versioned)allQr.getObjectVectorIfc().lastElement();
		if(v != null) {
			firstBranchId = Long.valueOf(v.getBranchIdentifier());
		}
		
		return (firstBranchId==null)? true:branchId0.equals(firstBranchId);
	}
	
	public static WTPart getLatestRevisionPart(String number, String viewName) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{String.class, String.class};
			Object args[] = new Object[]{number, viewName};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getLatestRevisionPart",VersionHelper.class.getName(),null,argTypes,args);
			}
			catch(RemoteException e) {
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				throw new WTException(e);
			}
			return (WTPart)obj;
		}
		
		try {
			View view = wt.vc.views.ViewHelper.service.getView(viewName);
			return getLatestRevisionPart(number, view);
		}
		catch(Exception e) {
			throw new WTException(e);
		}
	}
	
	public static WTPart getLatestRevisionPart(String number, View view) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{String.class, View.class};
			Object args[] = new Object[]{number, view};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getLatestRevisionPart",VersionHelper.class.getName(),null,argTypes,args);
			}
			catch(RemoteException e) {
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				throw new WTException(e);
			}
			return (WTPart)obj;
		}
		
		try {
			/*
			 * SELECT 'wt.part.WTPart',A0.branchIditerationInfo 
				FROM WTPart A0,WTPartMaster A0B 
				WHERE (A0.idA3masterReference=A0B.idA2A2) 
					AND (A0B.WTPartNumber = '100011768') 
					AND (A0.idA3view = 2916) AND (A0.latestiterationInfo = 1) 
					AND (A0.statecheckoutInfo NOT IN ('term', 'wrk', 'to del', 'to wrk')) 
					AND NOT (EXISTS (
							SELECT NULL 
							FROM WTPart S0 
							WHERE ((S0.idA3masterReference = A0.idA3masterReference) 
								AND (S0.idA3view = A0.idA3view) 
								AND (S0.statecheckoutInfo NOT IN ('term', 'wrk', 'to del', 'to wrk')) 
								AND (S0.idA3C2iterationInfo = A0.idA2A2)))); 
			 */
			Class cls_part = WTPart.class;
			
			QuerySpec spec = new QuerySpec();
			
			int idx_part = spec.appendClassList(cls_part, false);
			try {
				spec.setDescendantsIncluded(false, idx_part);
			} catch (WTPropertyVetoException localWTPropertyVetoException) {
				throw new WTException(localWTPropertyVetoException);
			}
			
			spec.appendSelect(new ConstantExpression(cls_part.getName()), false);
			spec.appendSelect(new ClassAttribute(cls_part, "iterationInfo.branchId"), new int[]{idx_part}, false);
			
			spec.appendWhere(new SearchCondition(cls_part, "master>number",SearchCondition.EQUAL, number.trim()), new int[]{idx_part});
			spec.appendAnd();
			spec.appendWhere(new SearchCondition(cls_part, "view.key.id", "=", PersistenceHelper.getObjectIdentifier(view).getId()), new int[]{idx_part});
			spec.appendAnd();
			spec.appendWhere(VersionControlHelper.getSearchCondition(cls_part, true), new int[]{idx_part});
			
			appendFilterTerminalsAndWorkingCopies(cls_part, spec, new int[]{idx_part});
			
			spec.appendAnd();
			spec.appendWhere(new NegatedExpression(new ExistsExpression(getSuccessorVersionQuerySpec(cls_part, Boolean.TRUE, spec, idx_part))), null);
			
			ClassAttribute sortCa = null;
			OrderBy orderby = null;
			int sortIdx = 0;
			
			sortCa = new ClassAttribute(cls_part, "versionInfo.identifier.versionSortId");
			//sortCa = new ClassAttribute(partClass, "thePersistInfo.modifyStamp");
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			spec.appendSelect(sortCa, new int[]{idx_part}, false);
			orderby = new OrderBy(sortCa, true, null);
			spec.appendOrderBy(orderby, new int[]{idx_part});
			
			spec.setAdvancedQueryEnabled(true);
			
			QueryResult qr = PersistenceServerHelper.manager.query(spec);
			if(qr.hasMoreElements()) {
				Object obj[] = (Object[])(Object[])qr.nextElement();
				String className = (String)obj[0];//class name
				Long branchId = Long.valueOf(((Number)obj[1]).longValue());//branch id	
				
				ReferenceFactory rf = new ReferenceFactory();
				WTPart part =  (WTPart)rf.getReference("VR:"+className+":"+branchId.longValue()).getObject();
				if(!wt.vc.wip.WorkInProgressHelper.isCheckedOut(part)) {
					return part;
				} else {
					if(wt.vc.wip.WorkInProgressHelper.isWorkingCopy(part)) {
						return part;
					} else {
						return (WTPart)wt.vc.wip.WorkInProgressHelper.service.workingCopyOf(part);
					}
				}
			}
		}
		catch(Exception e) {
			throw new WTException(e);
		}
		return null;
	}
	
	public static boolean isLatestRevision(Versioned versioned) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Versioned.class};
			Object args[] = new Object[]{versioned};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"isLatestRevision",VersionHelper.class.getName(),null,argTypes,args);
			}
			catch(RemoteException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			return ((Boolean)obj).booleanValue();
		}
		try {
			//wt.vc.VersionControlHelper.isLatestIteration(versioned);
			Versioned latestVersioned = getLatestRevision(versioned);
			Long latest = Long.valueOf(latestVersioned.getBranchIdentifier());
			return latest.equals(Long.valueOf(versioned.getBranchIdentifier()));
		}
		catch(Exception e) {
			Kogger.error(VersionHelper.class, e);
		}
		return false;
	}
	
	public static Versioned getLatestRevision(Versioned versioned) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Versioned.class};
			Object args[] = new Object[]{versioned};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getLatestRevision",VersionHelper.class.getName(),null,argTypes,args);
			}
			catch(RemoteException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			return (Versioned)obj;
		}
		try {
			QueryResult qr = (QueryResult)PersistenceServerHelper.manager.query(getLatestVersionQuerySpec(versioned));
			
			//Kogger.debug(getClass(), "==================================");
			//Kogger.debug(getClass(), "=== latest size : " + qr.size());
			//Kogger.debug(getClass(), "==================================");
			if(qr.hasMoreElements()) {
				Object obj[] = (Object[])(Object[])qr.nextElement();
				String className = (String)obj[0];//class name
				//Long viewId = Long.valueOf(((Number)obj[1]).longValue());// view id
				//Long masterId = Long.valueOf(((Number)obj[2]).longValue());//master id
				Long branchId = Long.valueOf(((Number)obj[3]).longValue());//branch id	
				
				ReferenceFactory rf = new ReferenceFactory();
				return (Versioned)rf.getReference("VR:"+className+":"+branchId.longValue()).getObject();
			}
		}
		catch(Exception e) {
			Kogger.error(VersionHelper.class, e);
		}
		return null;
	}
	
	
	public static Versioned getLatestRevision(Class cls, Mastered mastered) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Class.class,Mastered.class};
			Object args[] = new Object[]{cls,mastered};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getLatestRevision",VersionHelper.class.getName(),null,argTypes,args);
			}
			catch(RemoteException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(VersionHelper.class, e);
				throw new WTException(e);
			}
			return (Versioned)obj;
		}
		try {
			QueryResult qr = (QueryResult)PersistenceServerHelper.manager.query(getLatestVersionQuerySpec(cls, mastered));
			
			//Kogger.debug(getClass(), "==================================");
			//Kogger.debug(getClass(), "=== latest size : " + qr.size());
			//Kogger.debug(getClass(), "==================================");
			if(qr.hasMoreElements()) {
				Object obj[] = (Object[])(Object[])qr.nextElement();
				String className = (String)obj[0];//class name
				//Long viewId = Long.valueOf(((Number)obj[1]).longValue());// view id
				//Long masterId = Long.valueOf(((Number)obj[2]).longValue());//master id
				Long branchId = Long.valueOf(((Number)obj[3]).longValue());//branch id	
				
				ReferenceFactory rf = new ReferenceFactory();
				return (Versioned)rf.getReference("VR:"+className+":"+branchId.longValue()).getObject();
			}
		}
		catch(Exception e) {
			Kogger.error(VersionHelper.class, e);
		}
		return null;
	}
	
	
	public static QuerySpec getLatestVersionQuerySpec(Versioned versioned)
		throws QueryException, VersionControlException, WTException, WTPropertyVetoException
	{
		ViewReference viewRef = null;
		if (ViewManageable.class.isAssignableFrom(versioned.getClass())) {
			viewRef = ((ViewManageable)versioned).getView();
		}
		
		return getLatestVersionQuerySpec(versioned, viewRef);
	}
	
	
	public static QuerySpec getLatestVersionQuerySpec(Class cls, Mastered mastered)
		throws QueryException, VersionControlException, WTException, WTPropertyVetoException
	{
		return getLatestVersionQuerySpec(cls, mastered, null);
	}
	
	
	public static QuerySpec getLatestVersionQuerySpec(Class cls, Mastered mastered, ViewReference viewRef) 
		throws QueryException, WTException, VersionControlException, WTPropertyVetoException
	{
		//Class cls = versioned.getClass();
		Long viewId = Long.valueOf((viewRef == null) ? 0L : ((ObjectIdentifier)viewRef.getKey()).getId());
		
		boolean bool = !(viewId.equals(Long.valueOf(0L)));
		
		QuerySpec spec = new QuerySpec();
		
		int i = spec.appendClassList(cls, false);
		int[] arr = { i };
		
		try {
			spec.setDescendantsIncluded(false, i);
		} catch (WTPropertyVetoException localWTPropertyVetoException) {
			throw new WTException(localWTPropertyVetoException);
		}
		
		spec.appendSelect(new ConstantExpression(cls.getName()), false);
		spec.appendSelect(new ConstantExpression(viewId), false);
		spec.appendSelect(new ClassAttribute(cls, "masterReference.key.id"), arr, false);
		spec.appendSelect(new ClassAttribute(cls, "iterationInfo.branchId"), arr, false);
		spec.appendSelect(new ClassAttribute(cls, "versionInfo.identifier.versionSortId"), arr, true);
		
		spec.appendWhere(new SearchCondition(cls, "masterReference.key.id",
				SearchCondition.EQUAL, 
				PersistenceHelper.getObjectIdentifier(mastered).getId()), arr);
		if (bool) {
			spec.appendAnd();
			spec.appendWhere(new SearchCondition(cls, "view.key.id", "=", viewId), arr);
			
		}
		
		appendFilterTerminalsAndWorkingCopies(cls, spec, arr);
		spec.appendAnd();
		spec.appendWhere(VersionControlHelper.getSearchCondition(cls, true), arr);
		spec.appendAnd();		
		spec.appendWhere(new NegatedExpression(new ExistsExpression(getSuccessorVersionQuerySpec(cls, Boolean.valueOf(bool), spec, i))), null);
		
		ClassAttribute sortCa = null;
		OrderBy orderby = null;
		int sortIdx = 0;
		
		sortCa = new ClassAttribute(cls, "versionInfo.identifier.versionSortId");
		//sortCa = new ClassAttribute(partClass, "thePersistInfo.modifyStamp");
		sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
		spec.appendSelect(sortCa, arr, false);
		orderby = new OrderBy(sortCa, true, null);
		spec.appendOrderBy(orderby, arr);
		
		spec.setAdvancedQueryEnabled(true);
		
		//Kogger.debug(getClass(), "*********************************************************");
		//Kogger.debug(getClass(), spec.toString());
		//Kogger.debug(getClass(), "*********************************************************");
		
		return spec;
	}
	
	
	public static QuerySpec getLatestVersionQuerySpec(Versioned versioned, ViewReference viewRef) 
		throws QueryException, WTException, VersionControlException, WTPropertyVetoException
	{
		return getLatestVersionQuerySpec(versioned.getClass(), versioned.getMaster(), viewRef);
	}
	

	public static QuerySpec getSuccessorVersionQuerySpec(Class paramClass, Boolean paramBoolean, QuerySpec pspec, int paramInt)
		throws WTException, QueryException
	{
		QuerySpec lspec = new QuerySpec();
		try {
			lspec.getFromClause().setAliasPrefix("S");
		} catch (WTPropertyVetoException wve) {
			throw new WTException(wve);
		}
		
		int i = lspec.appendClassList(paramClass, false);
		try {
			lspec.setDescendantsIncluded(false, i);
		} catch (WTPropertyVetoException wve) {
			throw new WTException(wve);
		}
		
		int[] arr = { i };
		lspec.appendSelect(KeywordExpression.Keyword.NULL.newKeywordExpression(String.class), arr, false);
		
		TableExpression[] tableArr = { lspec.getFromClause().getTableExpressionAt(i), pspec.getFromClause().getTableExpressionAt(paramInt) };
		String[] aliasArr = { lspec.getFromClause().getAliasAt(i), pspec.getFromClause().getAliasAt(paramInt) };
		
		lspec.appendWhere(new SearchCondition(paramClass, "masterReference.key.id", paramClass, "masterReference.key.id"), tableArr, aliasArr);
		
		if(paramBoolean.booleanValue()) {
			lspec.appendAnd();
			lspec.appendWhere(new SearchCondition(paramClass, "view.key.id", paramClass, "view.key.id"), tableArr, aliasArr);
		}
		
		appendFilterTerminalsAndWorkingCopies(paramClass, lspec, arr);
		
		lspec.appendAnd();
		lspec.appendWhere(new SearchCondition(paramClass, "iterationInfo.predecessor.key.id", paramClass, "thePersistInfo.theObjectIdentifier.id"), tableArr, aliasArr);
		return lspec;
	}
	
	public static void appendFilterTerminalsAndWorkingCopies(Class cls, QuerySpec spec, int[] paramArrayOfInt)
		throws QueryException
	{
		if (!(Workable.class.isAssignableFrom(cls)))
			return;
		
		if(spec.getConditionCount() > 0)
			spec.appendAnd();
		String[] arrayOfString = { WorkInProgressState.TERMINAL.toString(), WorkInProgressState.WORKING.toString(), WorkInProgressState.TO_DELETED.toString(), "to wrk" };
		spec.appendWhere(new SearchCondition(cls, "checkoutInfo.state", arrayOfString, true, true), paramArrayOfInt);
		
	}

}
