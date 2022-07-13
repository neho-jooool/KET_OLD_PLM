package e3ps.project.moldPart.beans;

import java.sql.Timestamp;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.MoldProject;
import e3ps.project.moldPart.MoldSubPart;
import ext.ket.shared.log.Kogger;

public class MoldSubPartData {
	
	public String oid;
	public String dieNo;
	public String creatorName;
	public String partNumber;
	public String partName;
	public String quantity;
	public String material;
	public String partClass;
	public String partType;
	public String actionType; //조치사항
	public String endDate;
	public String mType;
	public String transferDate;
	public String etc;
	
	public MoldSubPartData(MoldSubPart moldPart){
		oid = CommonUtil.getOIDString(moldPart);
		MoldProject project = moldPart.getManager().getProject();
		dieNo = project.getMoldInfo().getDieNo();
		creatorName = moldPart.getManager().getOwner().getFullName();
		partNumber = moldPart.getMoldNo();
		partName = moldPart.getMoldName();
		quantity = moldPart.getEa();
		material = moldPart.getMaterial();
		partClass = moldPart.getPartClass();
		partType = moldPart.getPartType();
		actionType = moldPart.getActionType();
		Timestamp stamp = moldPart.getEndDate();
		
		if(stamp != null){
			endDate = DateUtil.getDateString(stamp, "d");
		}else{
			endDate = "";
		}
		
		mType = moldPart.getMType();
		
		stamp = moldPart.getTransferDate();
		if(stamp != null){
			transferDate = DateUtil.getDateString(stamp, "d");
		}else{
			transferDate = "";
		}
		
		etc = moldPart.getEtcDesc();
		
	}
	
	public static void main(String args[]){
		Kogger.debug(MoldSubPartData.class, String.valueOf("kkk"));
	}
}
