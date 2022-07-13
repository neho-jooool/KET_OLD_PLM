package e3ps.project.beans;

import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.impl.ParentChildLink;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.outputtype.ProjectOutPutType;
import ext.ket.shared.log.Kogger;


public class ProjectOutPutTypeHelper {
	public static ArrayList getList() throws Exception {
		ProjectOutPutType root = getRoot();
        ArrayList list = getList(root);
        return list;
    }
	
	
	public static ProjectOutPutType getRoot() throws Exception{
		ProjectOutPutType root = null;
        try {
            
            QuerySpec qs = new QuerySpec();
            int idx = qs.addClassList(ProjectOutPutType.class, true);
            qs.appendWhere(new SearchCondition(ProjectOutPutType.class, "path", "=", "ROOT"), new int[] { idx });
            SearchUtil.setOrderBy( qs, ProjectOutPutType.class, idx, "code", "code", false);
            QueryResult qr = PersistenceHelper.manager.find(qs);
            
            Object[] obj = null;
            if (!qr.hasMoreElements())
                root = makeRoot();
            else
            	obj = (Object[])qr.nextElement();
            	root = (ProjectOutPutType)obj[0];
        } catch (QueryException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        } catch (WTException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        }
        return root;
    }
	
	public static ProjectOutPutType makeRoot() {
		ProjectOutPutType d = null;
        try {
            d = ProjectOutPutType.newProjectOutPutType();
            d.setName("ROOT");
            d.setPath("ROOT");
            d.setDescription("ROOT");

            d = (ProjectOutPutType) PersistenceHelper.manager.save(d);
        } catch (WTException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        }
        return d;
    }
	
	public static ArrayList getList(ProjectOutPutType code) {
        ArrayList list = new ArrayList();
        try {
            Object[] data = new Object[2];
            data[1] = code; // child
            list.add(data);

            list = makeList(code, list);
        } catch (QueryException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        } catch (WTException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        } catch (Exception e){
            Kogger.error(ProjectOutPutTypeHelper.class, e);
        }

        return list;
    }
	
	private static ArrayList makeList(ProjectOutPutType code, ArrayList list) throws WTException, WTPropertyVetoException,
	    QueryException, WTIntrospectionException, Exception {
		QuerySpec query = new QuerySpec();
		int i = query.addClassList(ProjectOutPutType.class, true);
		int j = query.addClassList(ParentChildLink.class, false);
		
		if(!CommonUtil.isAdmin()){
			if(query.getConditionCount() > 0 ){
				query.appendAnd();
			}
			    query.appendOpenParen();
		        query.appendWhere(new SearchCondition(ProjectOutPutType.class, "isDisabled", SearchCondition.IS_FALSE),new int[] { 0 });
		        query.appendOr();
		        query.appendWhere(new SearchCondition(ProjectOutPutType.class, "isDisabled", SearchCondition.IS_NULL), new int[]{0});
		        query.appendCloseParen();
		}
		
		SearchUtil.setOrderBy( query, ProjectOutPutType.class, 0, "code", "code", false);
		
		QueryResult qr = PersistenceHelper.manager.navigate(code, "child", query);
		
		while (qr.hasMoreElements()) {
			ProjectOutPutType element = (ProjectOutPutType) qr.nextElement();
		    Object[] d = new Object[2];
		    d[0] = code; // parent
		    d[1] = element; // child
		    list.add(d);
		    makeList(element, list);
		}
		return list;
	}

	public static QuerySpec getCodeQuerySpec(ProjectOutPutType partCode) throws Exception {
		
		long loid = CommonUtil.getOIDLongValue(partCode);
		QuerySpec query = new QuerySpec();
		int i = query.addClassList(ProjectOutPutType.class, true);
		query.appendWhere(new SearchCondition(ProjectOutPutType.class,"parentReference.key.id", "=", loid ),new int[] { 0 });
		SearchUtil.setOrderBy( query, ProjectOutPutType.class, 0, "code", "code", false);
		
		return query;
	}

}
