
package e3ps.project.beans;

import java.sql.Timestamp;
import java.util.Vector;

import wt.org.WTUser;
import wt.session.SessionHelper;
//import e3ps.common.content.ContentUtil;
import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectIssueAnswer;
import ext.ket.shared.log.Kogger;

public class ProjectIssueAnswerData {
	public ProjectIssue issue;
	public ProjectIssueAnswer answer;
    public String oid = "";
	public String answerStr = "";
	public Timestamp answerDate;
	public String HistoryAttr = "";
	
	public PeopleData answerUser;
	public Vector answerAttacheVec = new Vector();

	public boolean isAnswerUser = false;
	public boolean isAnswerAttache = false;
	
	public ProjectIssueAnswerData(ProjectIssueAnswer _answer) {
		try {
		    this.answer = _answer;
//		    this.issue = (ProjectIssue) _answer.getQuestion();
		    this.oid = CommonUtil.getOIDString(_answer);
		    
		    WTUser mine = (WTUser)SessionHelper.manager.getPrincipal();
		    
		    this.HistoryAttr = (String)this.answer.getHistoryAttr()==null?"":(String)this.answer.getHistoryAttr();
			this.answerStr = (String)this.answer.getQuestion()==null?"":(String)this.answer.getQuestion();
			this.answerDate = this.answer.getCreateDate();
			this.answerUser = new PeopleData(this.answer.getOwner().getPrincipal());
			this.isAnswerUser = mine.getName().equals(this.answer.getOwner().getPrincipal().getName());
			this.answerAttacheVec = ContentUtil.getSecondaryContents(this.answer);
			if ( this.answerAttacheVec.size() > 0 ) this.isAnswerAttache = true;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
}
