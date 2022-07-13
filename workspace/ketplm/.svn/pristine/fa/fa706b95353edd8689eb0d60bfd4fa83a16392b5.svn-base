/**
 * @(#)	ProjectIssueData.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Vector;

import wt.org.WTUser;
import wt.session.SessionHelper;
//import e3ps.common.content.ContentUtil;
import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.cost.util.StringUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectIssue;
import ext.ket.shared.log.Kogger;

public class ProjectIssueData {
    public ProjectIssue issue;

    public String       oid                = "";
    public String       subject            = "";
    public String       issueType          = "";
    public String       question           = "";
    public Timestamp    questionDate;
    public Timestamp    planDoneDate;
    public String       stateProgress      = "";
    public String       stateProgress2     = "";

    public PeopleData   questionUser;
    public PeopleData   managerUser;

    public String       urgency            = "";
    public String       importance         = "";

    public Vector       questionAttacheVec = new Vector();

    public boolean      isQuestionUser     = false;
    public boolean      isManagerUser      = false;
    public boolean      isQuestionAttache  = false;
    public boolean      isCheck            = false;

    public Vector       issueAnswer        = new Vector();

    public E3PSProject  project;
    public E3PSTask     task;
    
    public String aegisStatus = "";

    public ProjectIssueData(ProjectIssue _issue) {
	try {
	    WTUser mine = (WTUser) SessionHelper.manager.getPrincipal();

	    this.issue = _issue;
	    this.oid = CommonUtil.getOIDString(_issue);

	    this.subject = StringUtil.checkNull(_issue.getSubject());
	    this.issueType = _issue.getIssueType().getDisplay(Locale.KOREAN);

	    this.question = (String) _issue.getAnswer();
	    this.questionDate = _issue.getCreateDate();

	    this.planDoneDate = _issue.getPlanDoneDate();

	    this.questionUser = new PeopleData(_issue.getOwner().getPrincipal());
	    this.isQuestionUser = mine.getName().equals(_issue.getOwner().getPrincipal().getName());

	    if (_issue.getManager() != null) this.managerUser = new PeopleData(_issue.getManager().getPrincipal());

	    this.isManagerUser = mine.getName().equals(_issue.getManager().getPrincipal().getName());

	    this.isCheck = _issue.isIsDone();

	    if (_issue.getUrgency() != null) this.urgency = _issue.getUrgency();
	    if (_issue.getImportance() != null) this.importance = _issue.getImportance();

	    this.questionAttacheVec = ContentUtil.getSecondaryContents(_issue);
	    if (this.questionAttacheVec.size() > 0) this.isQuestionAttache = true;

	    this.issueAnswer = ProjectIssueHelper.manager.getIssueAnswer(_issue);

	    this.project = _issue.getProject();
	    this.task = _issue.getTask();
	    this.stateProgress = "";
	    this.stateProgress2 = "";

	    Vector vec2 = ProjectIssueHelper.manager.getIssueAnswer(_issue);
	    if (vec2.size() != 0) {
		for (int i = 0; i < vec2.size(); i++) {
		    ProjectIssueAnswerData aData = (ProjectIssueAnswerData) vec2.get(i);
		    if (!aData.answer.isIsCheck()) {
			if (aData.answer.getPlanDate() != null) {
			    Timestamp plants = aData.answer.getPlanDate();
			    if (DateUtil.getCurrentTimestamp().compareTo(plants) < 0) {
				this.stateProgress = "<font color='red'>지연</font>";
				this.stateProgress2 = "지연";
			    }
			    else {
				this.stateProgress = "진행";
				this.stateProgress2 = "진행";
			    }

			}
			else {
			    this.stateProgress = "진행";
			    this.stateProgress2 = "진행";
			}

		    }
		    else {
			this.stateProgress = "진행";
			this.stateProgress2 = "진행";
		    }
		}
	    }
	    else {
		this.stateProgress = "진행";
		this.stateProgress2 = "진행";
	    }

	    if("Y".equals(_issue.getAegisTrans())){
		
		if("DANGER".equals(_issue.getAegisStatus())){
		    this.aegisStatus = "<b><font color=red>위험</font></b>";
		}
		if("ATTENTION1".equals(_issue.getAegisStatus())){
		    this.aegisStatus = "<b><font color=FFC000>주의1</font></b>";
		}
		if("ATTENTION2".equals(_issue.getAegisStatus())){
		    this.aegisStatus = "<b><font color=#FF5050>주의2</font></b>";
		}
		if("NORMAL".equals(_issue.getAegisStatus())){
		    this.aegisStatus = "<b><font color=#558ED5>정상</font></b>";
		}
	    }


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
