package ext.ket.part.base.service.internal;

import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.part.entity.KETPartChangeLog;

public class PartChangeLogHandler {

    public static void changeLog(WTPart wtPart, String propCode, String propName, String beforeValue, String afterValue, String userId
	    , String userName, String deptCode, String deptName) throws Exception{
	
	KETPartChangeLog changeLog = KETPartChangeLog.newKETPartChangeLog();
	changeLog.setAfterValue(afterValue);
	changeLog.setBeforeValue(beforeValue);
	changeLog.setChangeUserId(userId);
	changeLog.setChangeUserName(userName);
	changeLog.setDeptCode(deptCode);
	changeLog.setDeptKrName(deptName);
	changeLog.setPartChangeDate(DateUtil.getCurrentTimestamp());
	changeLog.setPartIda2a2(CommonUtil.getOIDLongValue(wtPart));
	changeLog.setPartIteration(wtPart.getIterationIdentifier().getValue());
	changeLog.setPartName(wtPart.getName());
	changeLog.setPartNumber(wtPart.getNumber());
	changeLog.setPartPropertyCode(propCode);
	changeLog.setPartPropertyName(propName);
	changeLog.setPartRevision(wtPart.getVersionIdentifier().getValue());
	
	PersistenceHelper.manager.save(changeLog);

   /*   
   @GeneratedProperty(name="deptKrName", type=String.class),
   @GeneratedProperty(name="deptCode", type=String.class),
   @GeneratedProperty(name="changeUserId", type=String.class),
   @GeneratedProperty(name="changeUserName", type=String.class),
   @GeneratedProperty(name="partIda2a2", type=long.class),
   @GeneratedProperty(name="partNumber", type=String.class),
   @GeneratedProperty(name="partName", type=String.class),
   @GeneratedProperty(name="partRevision", type=String.class),
   @GeneratedProperty(name="partIteration", type=String.class),
   @GeneratedProperty(name="partPropertyCode", type=String.class),
   @GeneratedProperty(name="partPropertyName", type=String.class),
   @GeneratedProperty(name="partChangeDate", type=Timestamp.class),
   @GeneratedProperty(name="beforeValue", type=String.class,
   @GeneratedProperty(name="afterValue", type=String.class,
   */

    }
    
}
