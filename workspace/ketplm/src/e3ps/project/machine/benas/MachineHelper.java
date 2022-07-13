package e3ps.project.machine.benas;

import java.util.HashMap;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.machine.MoldMachine;


public class MachineHelper implements RemoteAccess{
	
	public static MoldMachine createMachine(HashMap map)throws Exception{
		
		String oid = (String)map.get("oid");
		
		MoldMachine machine = null;
		
		if(oid != null && oid.length() > 0){
			machine = (MoldMachine)CommonUtil.getObject(oid);
		}else{
			machine = MoldMachine.newMoldMachine();
		}
		
		String model = (String)map.get("model");
		String moldType = (String)map.get("moldType");
		String machineMaker = (String)map.get("machineMaker");
		String machineType = (String)map.get("machineType");
		String ton = (String)map.get("ton");
		
		
		machine.setModel(model);
		machine.setMoldType(moldType);
		NumberCode code = (NumberCode)CommonUtil.getObject(machineMaker);
		machine.setMachineMaker(code);
		code = (NumberCode)CommonUtil.getObject(machineType);
		machine.setMachineType(code);
	
		code = (NumberCode)CommonUtil.getObject(ton);
		machine.setTon(code);
		
		machine = (MoldMachine)PersistenceHelper.manager.save(machine);
		
		return machine;
	}

	public static void deleteMachine(String oid)throws Exception{
		
		MoldMachine machine = (MoldMachine)CommonUtil.getObject(oid);
		PersistenceHelper.manager.delete(machine);
	}
	
	public static Vector getTonList(MoldMachine machine)throws Exception{
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		
		int index1 = qs.addClassList(MoldMachine.class, false);
		
		int index2 = qs.addClassList(NumberCode.class, true);
	    
		qs.setDistinct(true);
		
	    SearchCondition sc = new SearchCondition(
	            new ClassAttribute(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id"), "=", new ClassAttribute(
	            		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { index1, index2 });
		
	    long typeId = CommonUtil.getOIDLongValue(machine.getMachineType());
	    qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", machine.getMoldType()) , new int[]{index1});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
	    
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode makerCode = (NumberCode)o[0];
			vector.add(makerCode);
		}
		
		return vector;
	}
	
	public static Vector getMakerList(MoldMachine machine)throws Exception{
		
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		int index1 = qs.addClassList(MoldMachine.class, false);
		int index2 = qs.addClassList(NumberCode.class, true);
		qs.setDistinct(true);
		
		SearchCondition sc = new SearchCondition(
		        new ClassAttribute(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id"), "=", new ClassAttribute(
		        		NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
		sc.setOuterJoin(0);
		qs.appendWhere(sc, new int[] { index1, index2 });
		
		
		long typeId = CommonUtil.getOIDLongValue(machine.getMachineType());
		long tonId = CommonUtil.getOIDLongValue(machine.getTon());
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", machine.getMoldType()) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", tonId) , new int[]{0});
		SearchUtil.setOrderBy(qs, NumberCode.class, index2, NumberCode.NAME , "cname", false);
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			NumberCode typeCode = (NumberCode)o[0];
			vector.add(typeCode);
		}
		
		return vector;
	}
	
	public static Vector getModelList(MoldMachine machine)throws Exception{
		
		Vector vector = new Vector();
		
		QuerySpec qs = new QuerySpec();
		int index1 = qs.addClassList(MoldMachine.class, true);
		long typeId = CommonUtil.getOIDLongValue(machine.getMachineType());
		long tonId = CommonUtil.getOIDLongValue(machine.getTon());
		long makerId = CommonUtil.getOIDLongValue(machine.getMachineMaker());
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", machine.getMoldType()) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", tonId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
		
		SearchUtil.setOrderBy(qs, MoldMachine.class, index1, MoldMachine.MODEL , "cname", false);
		
		QueryResult rs = PersistenceHelper.manager.find(qs);
		
		while(rs.hasMoreElements()){
			Object[] o = (Object[])rs.nextElement();
			MoldMachine data = (MoldMachine)o[0];
			vector.add(data);
		}
		
		return vector;
	}
}
