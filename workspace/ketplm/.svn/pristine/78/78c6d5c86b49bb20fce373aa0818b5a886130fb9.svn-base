package e3ps.project.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.db.DBCPManager;
import e3ps.common.impl.ParentChildLink;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.project.outputtype.OEMType;
//import e3ps.common.db.DBCPManager;
import ext.ket.shared.log.Kogger;

public class OEMTypeHelper {
	
	public static ArrayList getList() throws Exception {
		OEMProjectType root = getRoot();
        ArrayList list = getList(root);
        return list;
    }
	public static ArrayList getList(String oemtype) throws Exception {
		OEMProjectType root = getRoot(oemtype);
        ArrayList list = getList(root);
        return list;
    }
	
	
	public static OEMProjectType getRoot() throws Exception{
		OEMProjectType root = null;
        try {
            QuerySpec qs = new QuerySpec();
            int idx = qs.addClassList(OEMProjectType.class, true);
            qs.appendWhere(new SearchCondition(OEMProjectType.class, "path", "=", "ROOT"), new int[] { idx });
            SearchUtil.setOrderBy( qs, OEMProjectType.class, idx, "code", "code", false);
            QueryResult qr = PersistenceHelper.manager.find(qs);
            Object[] obj = null;
            if (!qr.hasMoreElements())
                root = makeRoot();
            else
            	obj = (Object[])qr.nextElement();
            	root = (OEMProjectType)obj[0];
        } catch (QueryException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTException e) {
            Kogger.error(OEMTypeHelper.class, e);
        }
        
        
        return root;
    }
	
	public static OEMProjectType getRoot(String oemType) throws Exception{
		OEMProjectType root = null;
        try {
            QuerySpec qs = new QuerySpec();
            int idx = qs.addClassList(OEMProjectType.class, true);
            qs.appendWhere(new SearchCondition(OEMProjectType.class, "path", "=", "ROOT"), new int[] { idx });
            qs.appendAnd();
            qs.appendWhere(new SearchCondition(OEMProjectType.class, "oemType", "=", oemType), new int[] { idx });
            SearchUtil.setOrderBy( qs, OEMProjectType.class, idx, "code", "code", false);
            QueryResult qr = PersistenceHelper.manager.find(qs);
            Object[] obj = null;
            if (!qr.hasMoreElements()){
            	//Kogger.debug(OEMTypeHelper.class, "makeRoot<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            	makeRoot(oemType);
            }else{
            	obj = (Object[])qr.nextElement();
            	//Kogger.debug(getClass(), "obj==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + obj);
            	root = (OEMProjectType)obj[0];
            }
        } catch (QueryException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTException e) {
            Kogger.error(OEMTypeHelper.class, e);
        }
        
        
        return root;
    }
	
	public static OEMProjectType makeRoot(String oemType) {
		OEMProjectType d = null;
        try {
            d = OEMProjectType.newOEMProjectType();
            d.setName("ROOT");
            d.setPath("ROOT");
            d.setDescription("ROOT");
            d.setOemType(OEMType.toOEMType(oemType));

            d = (OEMProjectType) PersistenceHelper.manager.save(d);
        } catch (WTException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(OEMTypeHelper.class, e);
        }
        return d;
    }
	
	public static OEMProjectType makeRoot() {
		OEMProjectType d = null;
        try {
            d = OEMProjectType.newOEMProjectType();
            d.setName("ROOT");
            d.setPath("ROOT");
            d.setDescription("ROOT");

            d = (OEMProjectType) PersistenceHelper.manager.save(d);
        } catch (WTException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(OEMTypeHelper.class, e);
        }
        return d;
    }
	
	public static ArrayList getList(OEMProjectType code) {
        ArrayList list = new ArrayList();
        try {
            Object[] data = new Object[2];
            data[1] = code; // child
            list.add(data);

            list = makeList(code, list);
        } catch (QueryException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(OEMTypeHelper.class, e);
        } catch (Exception e){
        	Kogger.error(OEMTypeHelper.class, e);
        }

        return list;
    }
	
	private static ArrayList makeList(OEMProjectType code, ArrayList list) throws WTException, WTPropertyVetoException,
	    QueryException, WTIntrospectionException, Exception {
		QuerySpec query = new QuerySpec();
		int i = query.addClassList(OEMProjectType.class, true);
		int j = query.addClassList(ParentChildLink.class, false);
		
		if(!CommonUtil.isAdmin()){
			if(query.getConditionCount() > 0 ){
				query.appendAnd();
			}
			    query.appendOpenParen();
		        query.appendWhere(new SearchCondition(OEMProjectType.class, "isDisabled", SearchCondition.IS_FALSE),new int[] { 0 });
		        query.appendOr();
		        query.appendWhere(new SearchCondition(OEMProjectType.class, "isDisabled", SearchCondition.IS_NULL), new int[]{0});
		        query.appendCloseParen();
		}
		SearchUtil.setOrderBy(query, OEMProjectType.class, 0, "code", "code", false);
		
		
		QueryResult qr = PersistenceHelper.manager.navigate(code, "child", query);
		
		while (qr.hasMoreElements()) {
			OEMProjectType element = (OEMProjectType) qr.nextElement();
		    Object[] d = new Object[2];
		    d[0] = code; // parent
		    d[1] = element; // child
		    list.add(d);
		    makeList(element, list);
		}
		return list;
	}

	public static QuerySpec getCodeQuerySpec(OEMProjectType partCode) throws WTException, WTPropertyVetoException {
		
		long loid = CommonUtil.getOIDLongValue(partCode);
		QuerySpec query = new QuerySpec();
		int idx = query.addClassList(OEMProjectType.class, true);
		query.appendWhere(new SearchCondition(OEMProjectType.class,"parentReference.key.id", "=", loid ),new int[] { idx });
		SearchUtil.setOrderBy(query, OEMProjectType.class, 0, "code", "code", false);
		
		return query;
	}

	
	public static QuerySpec getAddOEMQuerySpec(OEMProjectType oem, int iv) throws WTException {
		
		QuerySpec query = new QuerySpec();
		long loid = CommonUtil.getOIDLongValue(oem);
		int idx = query.addClassList(OEMProjectType.class, true);
		query.appendWhere(new SearchCondition(OEMProjectType.class,"parentReference.key.id", "=", loid ),new int[] { idx });
		query.appendAnd();
		query.appendWhere(new SearchCondition(OEMProjectType.class, OEMProjectType.O_LEVEL, "=", iv),new int[] {idx });
		return query;
	}
	
	public static QuerySpec getTaskSpecfromOEMType(OEMProjectType type, TemplateProject project)throws Exception{
		
		QuerySpec spec = new QuerySpec(TemplateTask.class);
		spec.appendWhere(new SearchCondition(TemplateTask.class, "oemTypeReference.key.id", "=", type.getPersistInfo().getObjectIdentifier().getId()), new int[]{0});
	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", project.getPersistInfo().getObjectIdentifier().getId()), new int[]{0});
	    
		return spec;
		
	}
/*	
	public static OEMPlan getOEMPlan(ProjectManager pm, OEMProjectType oemType) throws Exception{
		OEMPlan op = null;
		QuerySpec opqs = new QuerySpec();
		int op_idx = opqs.addClassList(OEMPlan.class, true);
		
		
		opqs.appendWhere(new SearchCondition(OEMPlan.class, "projectManagerReference.key.id", "=", CommonUtil.getOIDLongValue(pm)), new int[] {op_idx} );
		opqs.appendAnd();
		opqs.appendWhere(new SearchCondition(OEMPlan.class, "oemTypeReference.key.id", "=", CommonUtil.getOIDLongValue(oemType)), new int[] {op_idx} );
		
		QueryResult opqr = PersistenceHelper.manager.find(opqs);
		
		Object[] oo = null;
		if(opqr.hasMoreElements()){
			oo = (Object[])opqr.nextElement();
			op = (OEMPlan)oo[0];
		}
		
		return op;
	}
*/
	
	
	public static ArrayList getOEMTypeTree(OEMProjectType type) throws Exception {
		
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList vec = new ArrayList();
        long rootId = CommonUtil.getOIDLongValue(type);
		try {
			if(con==null) {
				con = DBCPManager.getConnection("plm");
			}
			if(con == null){
				return vec;
			}
			StringBuffer sb = new StringBuffer();
			/*
			select level,idA2A2 from OEMProjectType start with idA2A2 = 2710056 
			connect by prior idA2A2 = idA3parentReference
			order siblings by oLevel;
			  
			  "+"'..'"+"
			 */
			
			sb = new StringBuffer().append("select level, ")
			.append("classnameA2A2, idA2A2")
			.append(" from ")
			.append("OEMProjectType")
			.append(" start with ")
			.append("idA2A2 ")
			.append("= ?  connect by prior ")
			.append("idA2A2")
			.append(" = ")
			.append("idA3parentReference")
			.append(" order siblings by code");
			
			st = con.prepareStatement(sb.toString());
			st.setLong(1, rootId);
			rs = st.executeQuery();
            
			while (rs.next()) {	
				int level = rs.getInt(1);
				String classId = rs.getString(2);
				String classLong = rs.getString(3);
				String classInfo = 	classId+":"+classLong;
				vec.add(classInfo);
			}	
			

		} catch (Exception e) {
			Kogger.error(OEMTypeHelper.class, e);
			
		} finally {
			try { 
				
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(OEMTypeHelper.class, e);
            }
		}
		return vec;
	}
	
	public static OEMProjectType getOEMProjectTypeObj(OEMProjectType parentType, String oemTypeName) {
		OEMProjectType oemType = null;
		
		try
        {
            QuerySpec spec = new QuerySpec();
            Class mainClass = OEMProjectType.class;
            int mainClassPos = spec.addClassList(mainClass, true);
            spec.appendWhere(new SearchCondition(mainClass, "parentReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(parentType)), new int[]{mainClassPos});
            spec.appendAnd();
            spec.appendWhere(new SearchCondition(mainClass, OEMProjectType.NAME, SearchCondition.EQUAL, oemTypeName), new int[]{mainClassPos});
            //Kogger.debug(getClass(), "getOEMProjectTypeObj[SPEC]>>>>> "+spec);
            QueryResult qr = PersistenceHelper.manager.find(spec);
            if (qr.hasMoreElements())
            {
                Object[] obj = (Object[]) qr.nextElement();
                return (OEMProjectType) obj[0];
            }
        }
        catch (QueryException e)
        {
            Kogger.error(OEMTypeHelper.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(OEMTypeHelper.class, e);
        }
		
		return null;
	}
	
	public OEMProjectType getOEMProjectType(String oemType, String code)
    {
        if (code == null) { return null; }
        try
        {
            QuerySpec select = new QuerySpec(OEMProjectType.class);
            select.appendWhere(new SearchCondition(OEMProjectType.class, "oemType", "=", oemType), new int[] { 0 });
            select.appendAnd();
            select.appendWhere(new SearchCondition(OEMProjectType.class, "code", "=", code), new int[] { 0 });
            QueryResult result = PersistenceHelper.manager.find(select);
            while (result.hasMoreElements())
            {
                return (OEMProjectType) result.nextElement();
            }
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }

        return null;
    }
	
	public static QueryResult getOEMProjectTypeName(String oemType, String name)
    {
        if (name == null) { return null; }
        try
        {
            QuerySpec select = new QuerySpec(OEMProjectType.class);
            select.appendWhere(new SearchCondition(OEMProjectType.class, "oemType", "=", oemType), new int[] { 0 });
            select.appendAnd();
            select.appendWhere(new SearchCondition(OEMProjectType.class, "name", "=", name), new int[] { 0 });
            QueryResult result = PersistenceHelper.manager.find(select);
            return result;
        }
        catch (QueryException e)
        {
            Kogger.error(OEMTypeHelper.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(OEMTypeHelper.class, e);
        }
        catch (Exception e)
        {
            Kogger.error(OEMTypeHelper.class, e);
        }

        return null;
    }

	public static ArrayList getChildNumberCode(OEMProjectType oemtype) throws WTException {
		ArrayList list = new ArrayList();
		try {
			if(oemtype == null)
				return list;
			
			
			
			QueryResult qr = PersistenceHelper.manager.find(getCodeQuerySpec(oemtype));
			Object obj[] = null;
			while(qr.hasMoreElements()) {
				obj = (Object[])qr.nextElement();
				list.add((OEMProjectType)obj[0]);
				
			}
		}
		catch(Exception e) {
			Kogger.error(OEMTypeHelper.class, e);
		}
		return list;
	}
	
	public static OEMPlan getOEMPlan(OEMProjectType oemType) throws Exception{
		OEMPlan op = null;
		QuerySpec opqs = new QuerySpec();
		int op_idx = opqs.addClassList(OEMPlan.class, true);
		
		//Kogger.debug(getClass(), CommonUtil.getOIDLongValue(pm) + " : "+ CommonUtil.getOIDLongValue(oemType));
		
		opqs.appendWhere(new SearchCondition(OEMPlan.class, "oemTypeReference.key.id", "=", CommonUtil.getOIDLongValue(oemType)), new int[] {op_idx} );
		
		QueryResult opqr = PersistenceHelper.manager.find(opqs);
		
		Object[] oo = null;
		if(opqr.hasMoreElements()){
			oo = (Object[])opqr.nextElement();
			op = (OEMPlan)oo[0];
		}
		
		return op;
	}

	public static QueryResult getCustomerEvent(OEMProjectType model) throws Exception{
		QueryResult result = null;
		
		if(model != null) {
			QuerySpec spec = new QuerySpec();
			int oemPlan_idx = spec.addClassList(OEMPlan.class, true);
			int modelPlan_idx = spec.addClassList(ModelPlan.class, false);
	
	        ClassAttribute ca3 = new ClassAttribute(OEMPlan.class, "modelPlanReference.key.id");
	        ClassAttribute ca4 = new ClassAttribute(ModelPlan.class, "thePersistInfo.theObjectIdentifier.id");
	        SearchCondition sc2 = new SearchCondition(ca3, "=", ca4);
	        spec.appendWhere(sc2, new int[] {oemPlan_idx, modelPlan_idx});
	        
	        spec.appendAnd();
	
	        spec.appendWhere(new SearchCondition(ModelPlan.class, "carTypeReference.key.id", "=", CommonUtil.getOIDLongValue(model)), new int[] {modelPlan_idx} );
	        
	        SearchUtil.setOrderBy( spec, OEMPlan.class, oemPlan_idx, OEMPlan.OEM_END_DATE, "m_Sort" , true);
			
			result = PersistenceHelper.manager.find(spec);
		}
		
		return result;
	}
}
