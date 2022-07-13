package e3ps.project.beans;

import e3ps.common.util.CommonUtil;
import e3ps.project.TemplateProject;

public class TemplateViewButtonAuth {
	
	/* 
	Check Out -> 상태 : 승인됨
	History -> all 
	Template 정보 수정 -> all
	삭제 -> 상태:(작업중, 재작업) 최신 PM , 최신&admin
	Task관리 -> 상태: 작업중   Admin 최신
	산출물 권한 -> 작업중 재작업
	*/
	
	public boolean isCheckOut;  		//	Check Out
	public boolean isHistory;			//	History
	public boolean isTemplateModify;	//	Template 정보 수정 
	public boolean isTemplateDelete;	//  Template 삭제
	public boolean isTask;				//  Task 관리
	public boolean isLatest;			//	최신 버젼
	public boolean isOutput;
	public boolean isAdmin;
	
	private static String INWORK = "INWORK"; 		 //작업중
	private static String REWORK = "REWORK";		 //재작업
	private static String CANCELLED = "CANCELLED"; 	 //취소됨
	private static String STOPED = "STOPED";		 //중지됨
	private static String APPROVED = "APPROVED";   	 //완료됨
	
	
	public TemplateViewButtonAuth(TemplateProject project)throws Exception{
		
		String state = project.getLifeCycleState().toString();
		isAdmin = CommonUtil.isAdmin();
		isLatest = project.isLastest();
		isCheckOut = state.equals(APPROVED) && isLatest && !project.isCheckOut();
		isHistory = true;
		isTemplateModify =  ((state.equals(INWORK) || state.equals(REWORK) || isAdmin && isLatest));
		isTemplateDelete = false;
		isTask = (state.equals(INWORK) || state.equals(REWORK));
		isOutput = (state.equals(INWORK) || state.equals(REWORK) && isLatest);
		
	}
	
}
