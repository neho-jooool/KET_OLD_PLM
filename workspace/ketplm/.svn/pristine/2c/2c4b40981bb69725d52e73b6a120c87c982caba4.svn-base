package e3ps.edm.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Cabinet;
import wt.folder.Folder;
import wt.folder.Foldered;
import wt.folder.IteratedFolderMemberLink;
import wt.folder.SubFolder;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.ExistsExpression;
import wt.query.FromClause;
import wt.query.KeywordExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableColumn;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;
import wt.vc.views.ViewManageable;
import e3ps.common.folder.FolderUtil;
import ext.ket.shared.log.Kogger;

public class VersionedQueryUtil implements wt.method.RemoteAccess, java.io.Serializable {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	private static final String COLUMN_RNUM = "RNUM";
	private static final String COLUMN_NUMBER = "CLS_NUMBER";
	private static final String COLUMN_NAME = "CLS_NAME";
	private static final String COLUMN_VERSION = "CLS_VERSION";
	private static final String COLUMN_CLASSNAME = "CLS_CLASSNAME";
	private static final String COLUMN_IDA2A2 = "CLS_IDA2A2";
	private static final String COLUMN_MODIFYSTAMP = "CLS_MODIFYSTAMP";
	private static final String COLUMN_CREATESTAMP = "CLS_CREATESTAMP";
	private static final String COLUMN_CREATOR_CN = "CLS_CREATOR_CN";
	private static final String COLUMN_CREATOR = "CLS_CREATOR";	
	private static final String COLUMN_MODIFIER_CN = "CLS_MODIFIER_CN";
	private static final String COLUMN_MODIFIER = "CLS_MODIFIER";
	
	
	public static int totalCount(HashMap map) throws Exception {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"totalCount",
						VersionedQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(VersionedQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(VersionedQueryUtil.class, e);
				throw new WTException(e);
			}
			return ((Integer)obj).intValue();
		}
		
		QueryResult qr = null;
		try {
            QuerySpec spec = new QuerySpec();
            
            int idx_main = spec.appendFrom(new SubSelectExpression(getQuerySpec(map)));
            
            KeywordExpression rnumCol = new KeywordExpression("count(*)");
            rnumCol.setFromAlias(new String[]{spec.getFromClause().getAliasAt(idx_main)}, 0);
            spec.appendSelect(rnumCol,false);
            
            spec.setAdvancedQueryEnabled(true);
            qr = PersistenceServerHelper.manager.query(spec);
            
            if(qr.hasMoreElements()) {
            	Object[] oo = (Object[])qr.nextElement();
            	BigDecimal bd = (BigDecimal)oo[0];
            	return bd.intValue();
            }
		}
		catch(Exception e) {
			Kogger.error(VersionedQueryUtil.class, e);
		}
		return 0;
		
	}
	
	public static QueryResult search(HashMap map) throws Exception {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"search",
						VersionedQueryUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(VersionedQueryUtil.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(VersionedQueryUtil.class, e);
				throw new WTException(e);
			}
			return (QueryResult)obj;
		}
		
		QueryResult qr = null;
		try {
			long stime = System.currentTimeMillis();
			
			Integer start = (Integer)map.get("start");
			Integer end = null;//(Integer)map.get("end");
			Integer size = (Integer)map.get("size");
			
			String className = trimToEmpty((String)map.get("className"));
			Class cls = Class.forName(className);
			
			if(start == null) {
				start = new Integer(1);
			}
			
			if(size == null) {
				size = new Integer(15);
			}
			
			if(end == null) {
				end = new Integer(start.intValue()+size.intValue());
				//end = new Integer(100);
			}
			
			
			QuerySpec spec = new QuerySpec();	
			spec.setAdvancedQueryEnabled(true);
			
	        int idx = spec.appendFrom(new SubSelectExpression(getQuerySpec(map)));
	        String alias = spec.getFromClause().getAliasAt(idx);
	        
	        
	        KeywordExpression ke = KeywordExpression.newKeywordExpression(KeywordExpression.ROWNUM);
			ke.setColumnAlias(COLUMN_RNUM);
			spec.appendSelect(ke, false);
			
			spec.appendSelect(new TableColumn(alias, COLUMN_NUMBER),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_NAME),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_VERSION),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_CLASSNAME),false);			
			spec.appendSelect(new TableColumn(alias, COLUMN_IDA2A2),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_CREATESTAMP),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_MODIFYSTAMP),false);
			
			spec.appendSelect(new TableColumn(alias, COLUMN_CREATOR_CN),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_CREATOR),false);
			
			spec.appendSelect(new TableColumn(alias, COLUMN_MODIFIER_CN),false);
			spec.appendSelect(new TableColumn(alias, COLUMN_MODIFIER),false);
			
			spec.appendOrderBy(new OrderBy(new TableColumn(alias, COLUMN_NUMBER),true,null), new int[]{0});
			
			
            QuerySpec spec0 = new QuerySpec();           
            
            int idx0 = spec0.appendFrom(new SubSelectExpression(spec));
            String alias0 = spec0.getFromClause().getAliasAt(idx);
            
            
            TableColumn rnumCol = new TableColumn(alias0, COLUMN_RNUM);
            TableColumn colNumber = new TableColumn(alias0, COLUMN_NUMBER);
            TableColumn colName = new TableColumn(alias0, COLUMN_NAME);
            TableColumn colVersion = new TableColumn(alias0, COLUMN_VERSION);
            TableColumn colClassname = new TableColumn(alias0, COLUMN_CLASSNAME);
            TableColumn colOID = new TableColumn(alias0, COLUMN_IDA2A2);
            TableColumn colCreateStamp = new TableColumn(alias0, COLUMN_CREATESTAMP);
            TableColumn colModifyStamp = new TableColumn(alias0, COLUMN_MODIFYSTAMP);
            
            TableColumn colCreatorCN = new TableColumn(alias0, COLUMN_CREATOR_CN);
            TableColumn colCreator = new TableColumn(alias0, COLUMN_CREATOR);
            
            TableColumn colModifierCN = new TableColumn(alias0, COLUMN_MODIFIER_CN);
            TableColumn colModifier = new TableColumn(alias0, COLUMN_MODIFIER);
            
            //'dd mm yyyy hh24:mi:ss'
            
            spec0.appendSelect(rnumCol,false);   
            spec0.appendSelect(colNumber,false);
            spec0.appendSelect(colName,false);
            spec0.appendSelect(colVersion,false);
            spec0.appendSelect(new KeywordExpression(colClassname+"||':'||"+colOID),false);
            spec0.appendSelect(colCreateStamp,false);
            spec0.appendSelect(colModifyStamp,false);
            spec0.appendSelect(new KeywordExpression(colCreatorCN+"||':'||"+colCreator),false);
            spec0.appendSelect(new KeywordExpression(colModifierCN+"||':'||"+colModifier),false);
            
            
            spec0.appendWhere(new SearchCondition(rnumCol, ">=", new ConstantExpression(start)), new int[]{idx0});
            spec0.appendAnd();
            spec0.appendWhere(new SearchCondition(rnumCol, "<", new ConstantExpression(end)), new int[]{idx0});
            
            spec0.setAdvancedQueryEnabled(true);           
            
            //Kogger.debug(getClass(), "===================================");
            //Kogger.debug(getClass(), spec0.toString());
            
            
            
            
            qr = PersistenceServerHelper.manager.query(spec0);
            
            long etime = System.currentTimeMillis();
            //Kogger.debug(getClass(), "search time : " + (etime-stime)/1000);
           
		}
		catch(Exception e) {
			Kogger.error(VersionedQueryUtil.class, e);
		}
		return qr;
	}
	
	public static QuerySpec getQuerySpec(HashMap map) throws Exception {
		QuerySpec spec = null;
		try {
			String number = trimToEmpty((String)map.get("number"));
			String name = trimToEmpty((String)map.get("name"));
			String viewName = trimToEmpty((String)map.get("view"));
			String className = trimToEmpty((String)map.get("className"));
			
			
			String latest = trimToEmpty((String)map.get("latest"));
			if(latest.length() == 0) {
				latest = String.valueOf(true);
			}
			
			View view = null;
			if(viewName.length() > 0) {
				view = ViewHelper.service.getView(viewName);
			}
			
			
			Class cls_iter = Class.forName(className);
			Class cls_master = WTIntrospector.getClassInfo(cls_iter).getOtherSideRole("master").getValidClass();
			
			spec = new QuerySpec();
			//spec.getFromClause().setAliasPrefix("S");
			
			spec.setAdvancedQueryEnabled(true);
			spec.setDescendantQuery(false);
			
			int idx_m = spec.appendClassList(cls_master, false);
			int idx_cls = spec.appendClassList(cls_iter, false);
			
			//addHintPk(cls, spec, true);
			
			//    /*+ NO_USE_HASH_AGGREGATION*/
			spec.appendHint("NO_USE_HASH_AGGREGATION");
			
			
			//	/*+ RULE*/
			//spec.appendHint("RULE");
			
			
			
			KeywordExpression ke = null;
			ClassAttribute ca = null;
			ClassAttribute ca1 = null;
			/*
				ke = KeywordExpression.newKeywordExpression(KeywordExpression.ROWNUM);
				ke.setColumnAlias(COLUMN_RNUM);
				spec.appendSelect(ke, false);
			*/
			ca = new ClassAttribute(cls_master, "number");
			ca.setColumnAlias(COLUMN_NUMBER);
			spec.appendSelect(ca, new int[]{idx_m}, false);
			
			ca1 = new ClassAttribute(cls_master, "name");
			ca1.setColumnAlias(COLUMN_NAME);
			spec.appendSelect(ca1, new int[]{idx_m}, false);
			
			ca = new ClassAttribute(cls_iter, "versionInfo.identifier.versionId");
			ca.setColumnAlias(COLUMN_VERSION);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.theObjectIdentifier.classname");
			ca.setColumnAlias(COLUMN_CLASSNAME);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.theObjectIdentifier.id");
			ca.setColumnAlias(COLUMN_IDA2A2);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.createStamp");
			ca.setColumnAlias(COLUMN_CREATESTAMP);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.modifyStamp");
			ca.setColumnAlias(COLUMN_MODIFYSTAMP);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.creator.key.classname");
			ca.setColumnAlias(COLUMN_CREATOR_CN);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.creator.key.id");
			ca.setColumnAlias(COLUMN_CREATOR);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.modifier.key.classname");
			ca.setColumnAlias(COLUMN_MODIFIER_CN);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.modifier.key.id");
			ca.setColumnAlias(COLUMN_MODIFIER);
			spec.appendSelect(ca, new int[]{idx_cls}, false);
			
			
			
			SQLFunction upper = null;
			ColumnExpression ce = null;
			SearchCondition sc = null;
			
			if(number.length() > 0) {
				ca = new ClassAttribute(cls_master, "number");
				upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				ce = ConstantExpression.newExpression(number.trim().toUpperCase()+"%");
				sc = new SearchCondition(upper, SearchCondition.LIKE, ce );
				
				spec.appendWhere(sc, new int[]{idx_m});
			}
			
			
			if(name.length() > 0) {
				if(spec.getConditionCount() > 0) { spec.appendAnd(); }
				
				ca = new ClassAttribute(cls_master, "name");
				upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				ce = ConstantExpression.newExpression("%" + name.trim().toUpperCase()+"%");
				sc = new SearchCondition(upper, SearchCondition.LIKE, ce );
				
				spec.appendWhere(sc, new int[]{idx_m});
			}
			
			if(spec.getConditionCount() > 0) { spec.appendAnd(); }
			spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_iter, "masterReference.key.id"), new int[] { idx_m, idx_cls });
			
			if(spec.getConditionCount() > 0) { spec.appendAnd(); }
			
			spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_iter, true),new int[]{idx_cls});
			
			VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_iter, spec, new int[]{idx_cls});
			
			if ( "wt.part.WTPart".equals(cls_iter.getName()) ) {
				if(view != null) {
					if(spec.getConditionCount() > 0) { spec.appendAnd(); }
					spec.appendWhere(new SearchCondition(cls_iter, "view.key.id","=", view.getPersistInfo().getObjectIdentifier().getId() ), new int[] { idx_cls });
				}
			}
			
			if ( "wt.epm.EPMDocument".equals(cls_iter.getName())) {
				
				String[] oas = (String[])map.get("ownerApplication");
				String[] aas = (String[])map.get("authoringApplication");
				
				
				if(oas != null) {
					if(spec.getConditionCount() > 0) { spec.appendAnd(); }
					sc = new SearchCondition(cls_master, "ownerApplication",oas, true);
					spec.appendWhere(sc, new int[]{idx_m});
				}
				
				if(aas != null) {
					if(spec.getConditionCount() > 0) { spec.appendAnd(); }
					sc = new SearchCondition(cls_master, "authoringApplication",aas, true);
					spec.appendWhere(sc, new int[]{idx_m});
				}
			}
			
			if(Foldered.class.isAssignableFrom(cls_iter)) {//if(FolderEntry.class.isAssignableFrom(cls_iter)) {
				String folderOid = trimToEmpty((String)map.get("folderOid"));
				if(folderOid.length() > 0) {
					Folder folder = (Folder)(new ReferenceFactory()).getReference(folderOid).getObject();
					
					if(folder instanceof SubFolder) {
						if(spec.getConditionCount() > 0) { spec.appendAnd(); }
						
						Vector folders = new Vector();
						folders.add(folder);
						FolderUtil.getSubFolderList(folder, folders);
						
						int f_idx = spec.appendClassList(IteratedFolderMemberLink.class, false);
						
						spec.appendOpenParen();
						
						for(int k = 0; k < folders.size(); k++) {
							Folder sf = (Folder)folders.get(k);
							if(k > 0) { spec.appendOr(); }
							spec.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleAObjectRef.key.id",
									SearchCondition.EQUAL, sf.getPersistInfo().getObjectIdentifier().getId()), new int[]{f_idx});
						}
						spec.appendCloseParen();

						spec.appendAnd();
						
						sc = new SearchCondition(new ClassAttribute(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId"),
										"=", new ClassAttribute(cls_iter, "iterationInfo.branchId"));
						sc.setFromIndicies(new int[]{f_idx, idx_cls}, 0);
						sc.setOuterJoin(0);
						spec.appendWhere(sc, new int[]{f_idx, idx_cls});
						
					} else if(folder instanceof Cabinet) {
						if(spec.getConditionCount() > 0) { spec.appendAnd(); }
						
						sc = new SearchCondition(cls_iter, "folderingInfo.cabinet.key.id","=", folder.getPersistInfo().getObjectIdentifier().getId());
						spec.appendWhere(sc, new int[] { idx_cls });
					}
				}
			}
			
			if(WTContained.class.isAssignableFrom(cls_iter)) {
				String containerOid = trimToEmpty((String)map.get("containerOid"));
				
				if(containerOid.length() > 0) {
					ReferenceFactory rf = new ReferenceFactory();
					WTContainer container = (WTContainer)rf.getReference(containerOid).getObject();
					
					if(spec.getConditionCount() > 0) { spec.appendAnd(); }
					
					sc = new SearchCondition(cls_iter, "containerReference.key.id","=", container.getPersistInfo().getObjectIdentifier().getId());
					spec.appendWhere(sc, new int[] { idx_cls });
				}
			}
			
			//상태 조건.
			if(LifeCycleManaged.class.isAssignableFrom(cls_iter)) {
				String state = trimToEmpty((String)map.get("state"));
				if(state.length() > 0) {
					if(spec.getConditionCount() > 0) { spec.appendAnd(); }
					sc = new SearchCondition(cls_iter, "state.state",SearchCondition.EQUAL,State.toState(state));
					spec.appendWhere(sc, new int[]{idx_cls});
				}
			}
			
			
			if(Boolean.parseBoolean(latest)) {
				if(spec.getConditionCount() > 0) { spec.appendAnd(); }
				
				Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_iter));
				spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_iter, paramBoolean, spec, idx_cls))), null);
			}
			
			
			//group by ..... begin
			ca = new ClassAttribute(cls_master, "number");
			spec.appendGroupBy(ca, new int[]{idx_m}, false);
			
			ca = new ClassAttribute(cls_master, "name");
			spec.appendGroupBy(ca, new int[]{idx_m}, false);
			
			ca = new ClassAttribute(cls_iter, "versionInfo.identifier.versionId");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.theObjectIdentifier.classname");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.theObjectIdentifier.id");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.createStamp");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			
			ca = new ClassAttribute(cls_iter, "thePersistInfo.modifyStamp");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.creator.key.classname");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.creator.key.id");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.modifier.key.classname");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			ca = new ClassAttribute(cls_iter, "iterationInfo.modifier.key.id");
			spec.appendGroupBy(ca, new int[]{idx_cls}, false);
			
			// spec.appendGroupBy(KeywordExpression.newKeywordExpression(KeywordExpression.ROWNUM), new int[]{idx_cls}, false);
			//group by ..... end
			
			
			ClassAttribute sortCa = null;
			OrderBy orderby = null;
			int sortIdx = 0;
			
			//sortCa = new ClassAttribute(m, "number");
			sortCa = new ClassAttribute(cls_iter, "thePersistInfo.modifyStamp");
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			spec.appendSelect(sortCa, new int[]{idx_cls}, true);
			
			orderby = new OrderBy(sortCa,true,null);
			spec.appendOrderBy(orderby, new int[]{idx_cls});
			
		}
		catch(Exception e) {
			Kogger.error(VersionedQueryUtil.class, e);
		}
		return spec;
	}
	
	public static void addHintPk(Class cls, QuerySpec spec, boolean desc) throws Exception {
		try {
			String str = cls.getName();
	  		String indexname = str.substring(str.lastIndexOf(46) + 1);
	  		String param = " INDEX( ";
	  		if(desc) {
	  			param = " INDEX_DESC( ";
	  		}
	  		FromClause localFromClause = spec.getFromClause();

	  		StringBuffer sb = new StringBuffer(param);
	  		sb.append(localFromClause.getAliasAt(localFromClause.getPosition(cls)));
	  		sb.append(' ');
	  		sb.append("PK_"+indexname.toUpperCase());
	  		sb.append(") ");
	  		spec.appendHint(sb.toString());
		}
		catch(Exception e) {
			Kogger.error(VersionedQueryUtil.class, e);
		}
	}
	
	public static void setSelectRownum(QuerySpec spec) throws Exception {
		try {
			KeywordExpression ke = KeywordExpression.newKeywordExpression(KeywordExpression.ROWNUM);
			ke.setColumnAlias("RNUM");
			spec.appendSelect(ke, false);
		}
		catch(Exception e) {
			Kogger.error(VersionedQueryUtil.class, e);
		}
	}
	
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static boolean isTrimToEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    public static String trimToEmpty(String str) {
        return !isTrimToEmpty(str)? str.trim() : "";
    }
    
    public static String defaultIfEmpty(String str, String defaultStr) {
        return isTrimToEmpty(str) ? defaultStr : str;
    }
	

}
