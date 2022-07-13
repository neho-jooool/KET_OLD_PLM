package e3ps.project.beans;

import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import ext.ket.shared.log.Kogger;

/*
 save AS -> 상태:all pm, admin
 Template 저장 -> 상태: all pm, admin
 plm정보 수정 -> 상태: 완료됨 취소됨 외 모든 상태 pm, admin , 최신
 삭제 -> 상태:(작업중, 재작업, ERP전송에러) 최신 PM , 최신&admin
 일정변경 -> 상태:진행중 AT완료 PM , admin, 최신
 차트 -> 상태:all , all
 해당설계변경 -> 상태:all, all
 이력비교 -> 상태:all, all
 결재이력 -> 상태:all, all
 Task관리 -> 상태:작업중 PM, Admin 최신
 중지 -> 상태:진행중 AT완료 PM, admin 최신
 완료 -> 상태:진행중 AT완료 PM, admin 최신
 AT완료 -> 상태:진행중 PM, admin 최신
 중지 취소 -> 상태:중지됨 PM, admin 최신
 취소 -> 상태:진행중 AT완료 PM, admin 최신
 PM변경-> 상태: 완료됨 취소됨 외 모든 상태 pm, admin , 최신
 Role별 담당자 수정 -> 상태: 완료됨 취소됨 외 모든 상태 pm, admin, 최신
 구성원 추가 -> 상태: 완료됨 취소됨 외 모든 상태 pm, admin , 최신
 View 권한 사용자 추가 -> 상태:all PM, Admin, 최신
 View 권한 부서 추가 -> 상태:all PM, Admin, 최신
 */
public class ProjectViewButtonAuth {

    private static String INWORK = "INWORK"; // 작업중
    private static String PMOINWORK = "PMOINWORK"; // 등록중
    private static String DEVASSIGN = "DEVASSIGN"; // 담당지정중
    private static String PROGRESS = "PROGRESS"; // 진행
    private static String WITHDRAWN = "WITHDRAWN"; // 폐기
    private static String CANCELLED = "CANCELLED"; // 취소됨
    private static String STOPED = "STOPED"; // 중지됨
    private static String REJECTED = "REJECTED"; // 반려됨
    private static String APPROVED = "APPROVED"; // 승인완료
    private static String UNDERREVIEW = "UNDERREVIEW"; // 검토중
    private static String COMPLETED = "COMPLETED"; // 완료
    private static String PLANCHANGE = "PLANCHANGE"; // 일정변경
    private static String STOPINWORK = "STOPINWORK"; // 중지검토

    public boolean isPM;
    public boolean isLatest;

    public boolean isProgress;
    public boolean isPMOInWork;
    public boolean isPlanChange;
    public boolean isReWork;
    public boolean isSTOPED;
    public boolean isCanceling;
    public boolean isSTOPINWORK;
    public boolean isPMO;
    public boolean isPMOE;
    public boolean isWorkingCopy;
    public boolean isFirst;
    public boolean isCarBuse;
    public boolean isElectronBuse;
    public boolean is100Complection;
    public boolean isCompleted;
    public boolean isCheckOut;
    public boolean isProjectUser;
    public boolean isCheckOutOrg;

    public ProjectViewButtonAuth(E3PSProject project) throws Exception {
	String state = project.getLifeCycleState().toString();
	Kogger.debug(getClass(), "state = " + state);
	isPM = ProjectUserHelper.manager.isPM(project);
	isLatest = project.isLastest();
	isCheckOut = project.isCheckOut();
	isProjectUser = ProjectUserHelper.manager.isProjectUser(project);
	isProgress = state.equals(PROGRESS);
	isPMOInWork = state.equals(PMOINWORK);
	isPlanChange = state.equals("PLANCHANGE");
	isCanceling = state.equals("CANCELING");
	isSTOPED = state.equals("STOPED");
	isSTOPINWORK = state.equals("STOPINWORK");
	isReWork = state.equals("REWORK");
	isCompleted = state.equals("COMPLETED");
	isPMO = CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO");
	isPMOE = CommonUtil.isMember("전자PMO");
	isWorkingCopy = project.isWorkingCopy();

	String checkOutState = project.getCheckOutState();
	isCheckOutOrg = "c/o".equals(checkOutState);

	// [PLM System 1차개선] 최초 버전 조건 추가(pjtIteration == 0), 2013-08-07, BoLee
	isFirst = project.getPjtHistory() == 0 && project.getPjtIteration() == 0;

	if (project instanceof ReviewProject) {
	    if (project.getPjtType() == 0) {
		isElectronBuse = true;
	    } else if (project.getPjtType() == 1) {
		isCarBuse = true;
	    }
	} else if (project instanceof ProductProject) {
	    if (project.getPjtType() == 4) {
		isElectronBuse = true;
	    } else if (project.getPjtType() == 2) {
		isCarBuse = true;
	    }
	}

	is100Complection = project.getPjtCompletion() == 100;

	//
	// isERPSync = !(state.equals(INWORK) || state.equals(REWORK));
	//
	// isSaveAs = false;//isAdmin || isPM;
	// isTemplateSave = isAdmin || isPM;
	// isPlmInfoModify = (state.equals(INWORK) || state.equals(REWORK) ||
	// state.equals(ERPSENDERROR) || state.equals(PLANWORK) ||
	// state.equals(STOPED) || state.equals(PROGRESS)) && (isAdmin || isPM) && isLatest;
	// isDelete = ((state.equals(INWORK) || state.equals(REWORK) ||
	// state.equals(ERPSENDERROR)) && (isPM) && isLatest)
	// || (isAdmin && isLatest);
	// isChangeSchedule = (state.equals(PROGRESS) || state.equals(PLANWORK)) && (isAdmin || isPM) && isLatest;
	// isChart = false;
	// isReferenceECO = true;
	// isHistoryCompare = true;
	// isProcessHistory = true;
	// isTaskManage = ( state.equals(INWORK) || state.equals(PLANCHANGE) ) && (isAdmin || isPM ) && isLatest;

	// 중지 -> 상태:진행중 AT완료 PM, admin 최신
	// 완료 -> 상태:진행중 AT완료 PM, admin 최신
	// AT완료 -> 상태:진행중 PM, admin 최신
	// 중지 취소 -> 상태:중지됨 PM, admin 최신
	// 취소 -> 상태:진행중 AT완료 PM, admin 최신
	// PM변경-> 상태: 완료됨 취소됨 외 모든 상태 pm, admin , 최신
	// Role별 담당자 수정 -> 상태: 완료됨 취소됨 외 모든 상태 pm, admin, 최신
	// 구성원 추가 -> 상태: 완료됨 취소됨 외 모든 상태 pm, admin , 최신
	// View 권한 사용자 추가 -> 상태:all PM, Admin, 최신
	// View 권한 부서 추가 -> 상태:all PM, Admin, 최신

	// int type = project.getPjtType();
	//
	// isStop = (state.equals(PROGRESS) || state.equals(PLANWORK)) && (isAdmin || isPM) && isLatest;
	// isStopCancle = state.equals(STOPED) && (isAdmin || isPM) && isLatest;
	// isComplete = (state.equals(PROGRESS) || state.equals(PLANWORK)) && (isAdmin || isPM) && isLatest;
	// isAtComplete = state.equals(PROGRESS) && (isAdmin || isPM) && (type != 2) && isLatest;
	// isCancle = (state.equals(PROGRESS) || state.equals(PLANWORK)) && (isAdmin || isPM) && isLatest;
	//
	// boolean isMemberModify = (state.equals(INWORK) || state.equals(REWORK) ||
	// state.equals(ERPSENDERROR) || state.equals(PLANWORK) ||
	// state.equals(STOPED) || state.equals(PROGRESS)) && (isAdmin || isPM) && isLatest;
	//
	// isPMModify = (state.equals(INWORK) || state.equals(REWORK) ||
	// state.equals(ERPSENDERROR) || state.equals(PLANWORK) ||
	// state.equals(STOPED) || state.equals(PROGRESS)) && (isAdmin || isPM) && isLatest;
	//
	// isRoleMemberModify = isMemberModify;
	// isMemberAdd = isMemberModify;
	// isViewUser = isMemberModify;// isAdmin || isPM;
	// isViewDepartment = isMemberModify;// isAdmin || isPM;
    }
}
