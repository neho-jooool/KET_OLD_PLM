package e3ps.project.beans;

public class ProjectStateHelper {
	public static final ProjectStateHelper manager = new ProjectStateHelper();
	
	protected ProjectStateHelper() {}
	
	public static final int PROGRESS = 0; 			//진행중
	public static final int HOLD = 1; 				//중지 (HOLD)
	public static final int REVOCATION = 2; 		//취소(REVOCATION)
	public static final int RESTART = 3; 			//재시작(RESTART)
	public static final int DELAY = 4; 				//지연(DELAY)
	public static final int COMPLETE = 5;			//프로젝트 종료 상태
	public static final int READY = 6;				//프로젝트 준비 상태(PM만 보임)
	
	public String getStateName(int roleNum)
	{
		String roleName = null;
		if(roleNum == PROGRESS)
			roleName = "진행중";
		else if(roleNum == HOLD)
			roleName = "중지";
		else if(roleNum == REVOCATION)
			roleName = "취소";
		else if(roleNum == RESTART)
			roleName = "재시작";
		else if(roleNum == DELAY)
			roleName = "지연";
		else if(roleNum == COMPLETE)
			roleName = "종료";
		else if(roleNum == READY)
			roleName = "준비중";
		
		return roleName;
	}
}
