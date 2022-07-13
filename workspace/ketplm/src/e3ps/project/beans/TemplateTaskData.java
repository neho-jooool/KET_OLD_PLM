/**
* @(#) TaskUserAccessData.java
* Copyright (c) e3ps. All rights reserverd
* 
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2005. 2. 2..
 *	@author Cho Sung Ok, jerred@e3ps.com
 *	@desc	
 */
package e3ps.project.beans;

import java.sql.Timestamp;

import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.ScheduleData;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TemplateTaskData {
    public TemplateTask task;           // 태스크 객체 
    public ScheduleData schedule;		// 프로젝트 일정 객체
    public TemplateProject project;     // 프로젝트 객체
    public String taskOID = "";         // 태스크 OID
    public String projectOID = "";      // 프로젝트 OID
    
    public int depth = 2;               // 태스크 위치 (2:중분류,3:소분류)
    public String name = "";            // 태스크 명
    public int seq;                     // 태스크 순서
    public String description = "";		// 태스크 설명
    public String completeTypeStr = "";	// 종료 타입
    public boolean checkWorkflow;       // 태스크의 워크프로세서 체크 여부 ( 종료 방식 결정 : false(단순종료) ,  true(결재 종료))

    public int duration;                // 태스크 기간
    public int stdWork;					// 표준공수
    
    public Timestamp createDate;      	// 최초 생성일
    public Timestamp modifyDate;      	// 최종 수정일
    public Department depart = null;	// 부서

	public String optiontype = "";		//필수여부
	public String milestone = "";		//milestone view
	public String tasktype = "";		//task 종류
	public String drvalue = "";			//DR 목표 값
    
    public TemplateTaskData(TemplateTask _task) {
        this.task = _task;
        this.taskOID = CommonUtil.getOIDString(_task);
        this.project = _task.getProject();
        this.schedule = (ScheduleData)_task.getTaskSchedule().getObject();
        this.projectOID = CommonUtil.getOIDString(_task.getProject());
        if ( _task.getParent() != null ) this.depth = 3;
        
        this.name = _task.getTaskName();
        this.duration = this.schedule.getDuration();
        this.stdWork = this.schedule.getStdWork();
        this.seq = _task.getTaskSeq();
        this.description = StringUtil.checkNull((String)_task.getTaskDesc());
        this.createDate = _task.getPersistInfo().getCreateStamp();
        this.modifyDate = _task.getPersistInfo().getModifyStamp();
        this.depart = _task.getDepartment();

		if(_task.isOptionType())
			this.optiontype = "Y";
		else
			this.optiontype = "N";

		if(_task.isMileStone())
			this.milestone = "Y";
		else
			this.milestone = "N";

		this.tasktype = _task.getTaskType();
		
		if(_task.getDrValue() != null)
			this.drvalue = _task.getDrValue();
    }
	
    public String getManagerStr() {
        String returnStr = "";
        QueryResult qr = TaskUserHelper.manager.getMaster(this.task);
            
        try {
            if ( qr.hasMoreElements() ) {
                Object objArr[] = (Object[])qr.nextElement();
                TaskMemberLink link = (TaskMemberLink)objArr[0];
                WTUser master = (WTUser)link.getActor().getPrincipal();             
                returnStr = master.getFullName() + "(" + DepartmentHelper.manager.getDepartmentName(master) + ")";
            }
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        }        
        return returnStr;
    }
	
    public boolean canDelete() {
        QueryResult qr = ProjectTaskHelper.manager.getChild(task);
        if ( qr.hasMoreElements() ) return false;
        
        qr = ProjectOutputHelper.manager.getTaskOutput(task);
        if ( qr.hasMoreElements() ) return false;
        
        qr = TaskUserHelper.manager.getTaskUser(task);
        if ( qr.hasMoreElements() ) return false;
        
        return true;
    }  
}
