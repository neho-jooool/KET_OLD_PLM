package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.Locale;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;

public class TemplateProjectData {
    public TemplateProject tempProject; // 프로젝트 객체
    public ScheduleData tempSchedule; // 프로젝트 일정 객체
    public String tempProjectOID = ""; // 프로젝트 OID
    public String tempName = ""; // 프로젝트 명
    public int tempState; // 프로젝트 상태(ProjectStateFlag 의 PROJECT_STATE_CONTINUE 또는 PROJECT_STATE_COMPLETE 또는 PROJECT_STATE_STOP)
    public String tempDesc = ""; // 프로젝트에 대한 설명
    public int tempDuration; // 프로젝트 기간
    public int tempStdWork; // 프로젝트 표준공수
    public Timestamp tempCreateDate; // 최초 생성일
    public Timestamp tempModifyDate; // 최종 수정일
    public String lifeCycle; // 상태
    public boolean lasttest;
    public int pjtHistory;
    // [START] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
    public int pjtIteration;
    // [END] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee

    public String division = ""; // 사업부
    public String wbsType = ""; // wbs타입

    public TemplateProjectData(TemplateProject _project) {
	this.tempProject = _project;
	if (this.tempProject != null) {
	    this.tempProjectOID = CommonUtil.getOIDString(this.tempProject);
	    this.tempSchedule = (ScheduleData) this.tempProject.getPjtSchedule().getObject();
	    this.tempName = this.tempProject.getPjtName();
	    this.tempDesc = StringUtil.checkNull((String) this.tempProject.getPjtDesc());
	    this.tempDuration = this.tempSchedule.getDuration();
	    this.tempStdWork = this.tempSchedule.getStdWork();
	    this.tempCreateDate = this.tempProject.getMaster().getPersistInfo().getCreateStamp();
	    this.tempModifyDate = this.tempProject.getPersistInfo().getModifyStamp();
	    this.lifeCycle = _project.getLifeCycleState().getDisplay(Locale.KOREA);
	    this.lasttest = _project.isLastest();
	    this.pjtHistory = _project.getPjtHistory();
	    // [START] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee
	    this.pjtIteration = _project.getPjtIteration();
	    // [END] [PLM System 1차개선] Project 이력 Iteration 추가, 2013-07-04, BoLee

	    if (_project instanceof ProductTemplateProject) {
    		this.division = "자동차사업부";
    		this.wbsType = "제품";
	    } else if (_project instanceof MoldTemplateProject) {
    		if (_project.getPjtType() == 3) {
    		    this.division = "자동차사업부";
    		} else {
    		    this.division = "전자사업부";
    		}
    		if("purchase".equals(_project.getMoldType())) {
    		    this.wbsType = "구매품";
    		}else {
    		    this.wbsType = "금형";
    		}
	    } else if (_project instanceof ElectronTemplateProject) {
    		this.division = "전자사업부";
    		this.wbsType = "제품";
	    } else {
    		if (_project.getPjtType() == 0)
    		    this.division = "전자사업부";
    		else
    		    this.division = "자동차사업부";
    		
    		if("work".equals(_project.getMoldType())) {
    		    this.wbsType = "업무";
    		}else {
    		    this.wbsType = "검토";
    		}
	    }
	}
    }
}
