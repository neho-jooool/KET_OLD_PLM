package e3ps.project.moldPart.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import wt.org.WTUser;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.MoldProject;
import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.moldPart.MoldSubPart;

public class MoldPartManagerData {
	
	public String dieNo;
	public String moldDate;
	public String totalState;
	public String createType;
	public String planDate;
	public String title;
	public String partUser;
	
	public WTUser partWTUser;
	public WTUser projectPartUser;
	
	public WTUser creatorWTUser;
	public String creator;
	
	public String projectOid;
	public String managerOid;
	
	public String levelType;
	
	public String createDate;
	
	public String state;
	
	
	public SimpleDateFormat sdFormat = new SimpleDateFormat( "yyyy-MM-dd");
	//toDay = sdFormat.format ( Calendar.getInstance ().getTime () );
	
	public MoldPartManagerData(MoldPartManager manager){
		MoldProject project = manager.getProject();
		projectOid = CommonUtil.getOIDString(project);
		managerOid = CommonUtil.getOIDString(manager);
		
		dieNo = project.getMoldInfo().getDieNo();
		Vector vector = manager.getSubParts();
		if(vector != null){
			int total = vector.size();
			int processing = 0;
			int complated = 0;
			for(int i = 0; i < vector.size(); i++){
				MoldSubPart part = (MoldSubPart)vector.get(i);
				String partClass = part.getPartClass();
				
				String actionType = part.getActionType();
				
				if("가공".equals(actionType) || "수정".equals(actionType)){
					continue;
				}
				
				if("도면정리".equals(partClass) || part.getTransferDate() != null){
					complated++;
				}else if(part.getTransferDate() == null){
					processing++;
				}
			}
			total = processing + complated;
			totalState = total + "/" + processing + "/" + complated;
		}
				
		Timestamp stamp = manager.getMoldDate();
		if(stamp != null){
			moldDate = DateUtil.getDateString(stamp, "d");
		}else{
			moldDate = "";
		}
		
		stamp = manager.getPlanDate();
		if(stamp != null){
			planDate = DateUtil.getDateString(stamp, "d");
		}else{
			planDate = "";
		}
		
		
		stamp = manager.getPersistInfo().getCreateStamp();
		if(stamp != null){
			createDate = DateUtil.getDateString(stamp, "d");
		}else{
			createDate = "";
		}
		
		createType = manager.getCreateType();
		
		if(manager.getPartUser() != null){
			partWTUser = (WTUser)manager.getPartUser().getObject();
			partUser = partWTUser.getFullName();
		}
		
		if(manager.getOwner() != null){
			creatorWTUser = (WTUser)manager.getOwner().getObject();
			creator = creatorWTUser.getFullName();
		}
		
		if(partUser == null){
			partUser = "";
		}
		if(creator == null){
			creator = "";
		}
		
		levelType = manager.getLevelType();
		if("금형제작".equals(levelType)){
			if(manager.getCha() == 1){
				title = manager.getLevelType();
			}else{
				title = manager.getLevelType() + " " + manager.getCha() + "차";
			}
		}else{
			if(manager.getSubCha() == 1){
				title = manager.getLevelType() + " " + manager.getCha() + "차";
			}else{
				title = manager.getLevelType() + " " + manager.getCha() + "_"+ (manager.getSubCha() - 1) + "차";
			}
			
		}
		
		String mstate = manager.getMoldState();
		
		
		
		if(mstate != null){
			state = mstate;
		}else{
			state = "진행";
			if(planDate.length() > 0){
				String today = sdFormat.format ( Calendar.getInstance ().getTime () );
				if(today.compareTo(planDate) > 0){
					state = "지연";
				}
			}
		}
		
	}
}
