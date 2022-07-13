package e3ps.project.material.beans;

import java.util.HashMap;
import java.util.Vector;

import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.material.MoldMaterial;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;

public class MaterialHelper implements RemoteAccess{
	
	public static MoldMaterial createMaterial(HashMap map)throws Exception{
		
		String oid = (String)map.get("oid");
		
		MoldMaterial material = null;
		
		if(oid != null && oid.length() > 0){
			material = (MoldMaterial)CommonUtil.getObject(oid);
		}else{
			material = MoldMaterial.newMoldMaterial();
		}
			
		String type = (String)map.get("material");
		String grade = (String)map.get("grade");
		
		material.setMaterial(type);
		material.setGrade(grade);
		
		String materialMakerOid = (String)map.get("materialMaker");
		String materialTypeOid = (String)map.get("materialType");
		
		NumberCode materialMaker = (NumberCode)CommonUtil.getObject(materialMakerOid);
		NumberCode materialType = (NumberCode)CommonUtil.getObject(materialTypeOid);
		
		material.setMaterialMaker(materialMaker);
		material.setMaterialType(materialType);
		
		material =(MoldMaterial)PersistenceHelper.manager.save(material);
		
			
	   return material;
	}
	
	public static void deleteMaterial(String oid)throws Exception{
		
		MoldMaterial material = (MoldMaterial)CommonUtil.getObject(oid);
		PersistenceHelper.manager.delete(material);
	}
	
	public static Vector getMakerList(MoldMaterial material)throws Exception{
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		
		int index1 = qs.addClassList(MoldMaterial.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
	    
		qs.setDistinct(true);
		
	    SearchCondition sc = new SearchCondition(
	            new ClassAttribute(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id"), "=", new ClassAttribute(
	            		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { index1, index2 });
		
	    qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material.getMaterial()) , new int[]{index1});
		
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
	    
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode makerCode = (NumberCode)o[0];
			vector.add(makerCode);
		}
		
		return vector;
	}
	
	public static Vector getTypeList(MoldMaterial material)throws Exception{
		
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		int index1 = qs.addClassList(MoldMaterial.class, false);
		int index2 = qs.addClassList(NumberCode.class, true);
		qs.setDistinct(true);
		
		SearchCondition sc = new SearchCondition(
		        new ClassAttribute(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id"), "=", new ClassAttribute(
		        		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
		sc.setOuterJoin(0);
		qs.appendWhere(sc, new int[] { index1, index2 });
		
		
		
		long makerId = CommonUtil.getOIDLongValue(material.getMaterialMaker());
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material.getMaterial()) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode typeCode = (NumberCode)o[0];
			vector.add(typeCode);
		}
		
		return vector;
	}
	
	public static Vector getGradeList(MoldMaterial material)throws Exception{
		
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		int index1 = qs.addClassList(MoldMaterial.class, true);
		long makerId = CommonUtil.getOIDLongValue(material.getMaterialMaker());
		long typeId = CommonUtil.getOIDLongValue(material.getMaterialType());
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material.getMaterial()) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		
		SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, MoldMaterial.GRADE , "cname", false);
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			MoldMaterial data = (MoldMaterial)o[0];
			vector.add(data);
		}
		
		return vector;
	}
	
}
